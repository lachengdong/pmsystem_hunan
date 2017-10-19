package com.sinog2c.mvc.webchat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.chat.iMessage;

public class EchoHandler extends TextWebSocketHandler {

	@Autowired
	private com.sinog2c.dao.api.chat.iMessageMapper iMessageMapper;

	public static final HashMap<String, WebSocketSession> onlineUsers;
	static {
		onlineUsers = new HashMap<String, WebSocketSession>();
	}

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		super.handleTextMessage(session, message);

		String msgContent = message.getPayload();
		iMessage imsg = JSON.parseObject(msgContent, iMessage.class);

		String userid = session.getAttributes()
				.get(OAParameter.WEBSOCKET_USERID).toString();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sendTime = new Date();
		imsg.setTime(sdf.format(sendTime));
		imsg.setCreateTime(sendTime);

		if (!imsg.getToUid().equalsIgnoreCase(imsg.getCreateUser())) {  //只保存不是自己发给自己的消息
			this.doHandleMsg(imsg);  //由于ie8下socket client端断线，但服务器端仍处在连线状态，所以在此处直接保存数据，以丢数据
		}
		
		if (imsg.getMsgType() == 2) {
			TextMessage msg = new TextMessage(JSON.toJSONString(imsg));
			this.sendMessageToUser(imsg.getToUid(), msg);
			if (imsg.getToUid().equalsIgnoreCase(userid))
				return;
			// 给自己发一次
			this.sendMessageToUser(userid, msg);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		String userid = session.getAttributes()
				.get(OAParameter.WEBSOCKET_USERID).toString();
		String userName = session.getAttributes()
				.get(OAParameter.WEBSOCKET_USERNAME).toString();
		if (onlineUsers.containsKey(userid)) {
			WebSocketSession old = onlineUsers.get(userid);
			if (old.isOpen()) {
				old.close();
			}
			onlineUsers.remove(userid);
			onlineUsers.put(userid, session);
		} else {
			onlineUsers.put(userid, session);
		}
		// 向所有在线用户发登录消息
		iMessage imsg = new iMessage();
		imsg.setMsgType((short) 1); // 1:登录消息
		imsg.setCreateUser(userid);
		imsg.setCreateusername(userName);
		TextMessage msg = new TextMessage(JSON.toJSONString(imsg));
		this.sendMessageToAllUsers(msg);
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userid, TextMessage message) {
		if (onlineUsers.containsKey(userid)) {
			WebSocketSession user = onlineUsers.get(userid);
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 */
	public void sendMessageToAllUsers(TextMessage message) {
		Iterator<String> iterator = onlineUsers.keySet().iterator();
		while (iterator.hasNext()) {
			WebSocketSession user = onlineUsers.get(iterator.next());
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("websocket connection closed......");
		String userid = session.getAttributes()
				.get(OAParameter.WEBSOCKET_USERID).toString();		
		onlineUsers.remove(userid);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		System.out.println("websocket connection closed......");
		
		Object userid = session.getAttributes()
				.get(OAParameter.WEBSOCKET_USERID);	
		if(userid!=null)
			onlineUsers.remove(userid.toString());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 处理消息   MsgType：1:登录消息;2:一般消息；3:更新已读标志；4:断开连接
	 * @param imsg
	 * @throws IOException 
	 */
	private void doHandleMsg(iMessage imsg) throws IOException {
		if (imsg.getMsgType() == 2) // 一般消息，保存数据库
		{
			this.iMessageMapper.insertSelective(imsg);
		} else if (imsg.getMsgType() == 3) // 更新已读标志
		{
			imsg.setUnread((short) 0);
			this.iMessageMapper.updateReadflagByUid(imsg);
		}
		else if(imsg.getMsgType()==4)
		{
			WebSocketSession session = onlineUsers.get(imsg.getCreateUser());
			if(session!=null&&session.isOpen())
			{
				session.close();				
			}
			onlineUsers.remove(imsg.getCreateUser());
			
		}		

	}

}
