package com.sinog2c.mvc.webchat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.gkzx.common.OAParameter;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;



public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("Before Handshake");

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			HttpServletRequest servletRequest = serverRequest
					.getServletRequest();

			SystemUser user = ControllerBase.getLoginUser(
					servletRequest);
			if (user != null) {
				// 使用userid区分WebSocketHandler，以便定向发送消息
				attributes.put(OAParameter.WEBSOCKET_USERNAME,
						user.getName());
				attributes.put(OAParameter.WEBSOCKET_USERID,
						user.getUserid());
			}
		}

		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		System.out.println("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
