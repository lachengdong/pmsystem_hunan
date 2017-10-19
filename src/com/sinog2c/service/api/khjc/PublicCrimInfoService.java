package com.sinog2c.service.api.khjc;

import java.util.Map;

//罪犯相关操作方法
public interface PublicCrimInfoService {
	//获取罪犯基本信息
	public Map getCriminalBasicInfo(Map map,String criminalid);
	
	//获取罪犯判裁信息
	public Map getCriminalBasicCrimeInfo(Map map,String criminalid);
}
