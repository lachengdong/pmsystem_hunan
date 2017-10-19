package com.sinog2c.service.api.message;

import java.util.List;

import com.sinog2c.model.chat.iMessage;

public interface IMessageService {
	
	 List<iMessage> getUserUnreadMsg(String createUser,String toUid);

}
