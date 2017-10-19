package com.sinog2c.service.api.khjc;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;

public interface KhjcCriminalScoreService {
	//获取表单相关的内容
	void getInfo(HttpServletRequest request);
	
	//保存罪犯计分相关的方法
	String saveCriminalScoreInfo(HttpServletRequest request);
	
	//更新罪犯计分相关的方法
	String updateCriminalScoreInfo(HttpServletRequest request);
	
}
