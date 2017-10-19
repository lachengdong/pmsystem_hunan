package com.sinog2c.ws;

import javax.jws.WebService;


@WebService
public interface IPmsysWebService {
	
	public String wsServiceReceive(String paramStr);

//	public List<TbprisonerBaseinfo> getTbprisonerBaseInfoByCrimid(String inputData);
	
//	public String caseDataExchange(String ddcid, String condition);
	
//	public String caseDataExchange_sx(String str);
//
//	public String caseDataExchange_sxsj(String str);
	
//	/**
//	 * 描述：根据存储过程实时同步数据
//	 * @param departid
//	 * @param callSql
//	 * */
//	public Object ywgkByProcedure(Map<String,String> callSql);
//	
//	/**
//	 * 描述:根据查询方案实时同步数据
//	 * @param ddccid 数据方案ID
//	 * @param paramMap
//	 * */
//	public Object ywgkByDataScheme(String ddccid,Map<String,Object> paramMap);
	
}

