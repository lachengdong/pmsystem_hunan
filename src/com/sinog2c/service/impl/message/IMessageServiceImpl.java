package com.sinog2c.service.impl.message;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.model.chat.iMessage;
import com.sinog2c.service.api.message.IMessageService;

@Service("iMessageServiceImpl")
public class IMessageServiceImpl implements IMessageService {

	@Autowired
	private com.sinog2c.dao.api.chat.iMessageMapper iMessageMapper;
	
	@Override
	public List<iMessage> getUserUnreadMsg(String createUser, String toUid) {
		List<iMessage> list= this.iMessageMapper.getUserUnreadMsg(createUser, toUid);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(iMessage imsg: list){
			imsg.setTime(sdf.format(imsg.getCreateTime())); //TODO:此处只做简单的时间处理，需修改			
		}			
		return list;
	}

}
