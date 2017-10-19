package com.sinog2c.service.api.khjc;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;

public interface KhjcTbflowBaseDocService{
	
	KhjcTbflowBaseDoc selectByPrimaryKey(String docid);
	
	KhjcTbflowBaseDoc selectByCondition(String docid,String templetid);
	
	
}
