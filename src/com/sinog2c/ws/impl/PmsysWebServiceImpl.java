package com.sinog2c.ws.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.common.CommonSQLMapper;
import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.impl.dbmsnew.DbmsNewDataExportServiceImpl;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.ws.IPmsysWebService;

@Service
@WebService(endpointInterface="com.sinog2c.ws.IPmsysWebService")
public class PmsysWebServiceImpl implements IPmsysWebService {
	
//	@Resource
//	private TbprisonerBaseInfoService tbprisonerBaseInfoService = null;
//	@Resource
//	private DbmsNewDataExportService dbmsNewDataExportService = null;
//	@Autowired
//	private ParseDataSchemaAndXmlImpl parseDataSchemaAndXmlService;
//	@Autowired
//	private DbmsDatasChemeNewMapper dbmsDatasChemeNewMapper;
//	@Autowired
//	private TbuserNoticeMapper TbuserNoticeMapper;
//	@Autowired
//	private UserNoticeService userNoticeService;
//	@Autowired
//	private TbuserUserNoticeMapper tbuserUserNoticeMapper;
//	@Autowired
//	private UserOrgWrapperMapper userorgwrappermapper;

	@Autowired
	DataTransferService dataTransferService;
	@Autowired
	private CommonSQLMapper commonSQLMapper;

	
	@Override
	public String wsServiceReceive(String paramStr) {
		
		Object obj = JSONUtils.parse(paramStr);
		if(obj instanceof Map<?,?>){
			Map<String,Object> paramap = (HashMap<String,Object>)obj;
			
			//businesstype：业务类型	1  通过查询方案交换数据（如：监狱与省局），2 通过老版数据交换方案交换数据（狱务公开）
			 // 					3  通过调用服务端的存储过程（狱务公开）
			String businesstype = StringNumberUtil.returnString2(paramap.get("businesstype"));
			if("1".equals(businesstype)){
				return dataExchangeWithSolution(paramap);
			}else if("2".equals(businesstype)){
				ywgkByDataScheme(paramap);
			}else if("3".equals(businesstype)){
				ywgkByProcedure(paramap);
			}else{
				throw new RuntimeException("数据传输有误！");
			}
			return null;
			
		}else{
			throw new RuntimeException("数据传输有误！");
		}
		
	}


	private String dataExchangeWithSolution(Map<String,Object> paramap){
		//
		SystemUser user = new SystemUser();
		String departid = paramap.get("departid").toString();
		user.setDepartid(departid);
		user.setUserid("sys"); 
		SystemOrganization organization = new SystemOrganization();
		organization.setOrgid(departid);
		user.setOrganization(organization);
		user.setName("sys");
		
		String remoteDepartid = paramap.get("remoteDepartid").toString();//由webservice客户端的单位id
//		String isArch = paramap.get("isArch").toString(); // 是否传递电子档案; isArch = 1 表示传递
		String isArch = null;//电子档案不再需要从这里传递，而是通过cwrsync等文件同步工具实现传递
		String crimids = paramap.get("crimids").toString(); // 传递哪些罪犯的电子档案
		String type = "1";
		//
		dataTransferService.dataExchange(type,paramap, user, remoteDepartid, crimids, isArch);
		//垃圾回收
		System.gc();
		//
		return "1";
		
//		//采用多线程实现
//		new Thread(new DataExchangeRunnable(paramStr,dataTransferService)).start();
//		//
//		return "1";
		
	}
	
	private void ywgkByProcedure(Map<String,Object> paramap) {
		
		Map<String,String> callSql = new HashMap<String,String>();
		//
		Set<String> keySet = paramap.keySet();
		for(String key : keySet){
			String value = StringNumberUtil.returnString2(paramap.get(key));
			callSql.put(key, value);
		}
		commonSQLMapper.call(callSql);
	}
	
	
	
	private void ywgkByDataScheme(Map<String,Object> paramap) {
		String ddcid = StringNumberUtil.returnString2(paramap.get("ddcid"));
		DbmsNewDataExportServiceImpl dataExport = new DbmsNewDataExportServiceImpl();
		DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
		bean.setDdcid(ddcid);
		bean.setInsertonly(GkzxCommon.ONE);
		bean.setCondition((String)paramap.get("condition"));
		bean.setHiddencon(null);
		dataExport.prisonDataExchange(bean);
	}
	
	
//	@Override
//	public List<TbprisonerBaseinfo> getTbprisonerBaseInfoByCrimid(String inputData) {
//		
//		List<TbprisonerBaseinfo> list = new ArrayList<TbprisonerBaseinfo>();
//		TbprisonerBaseinfo tbi = new TbprisonerBaseinfo();
//		tbi.setName("aaaaaa");
//		list.add(tbi);
//		
//		return list;
//	}
	
//	public String caseDataExchange(String ddcid, String condition){
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("ddcid", ddcid);
//		map.put("autoruninsertonly", 0);
//		map.put("autoruncondition", condition);
//		
//		dbmsNewDataExportService.caseDataExchange(null,null,null, map);
//		
//		return "1";
//	}

//	@Override
//	public String caseDataExchange_sx(String str) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("ddcid", str);
//		map.put("autoruninsertonly", 0);
//		dbmsNewDataExportService.caseDataExchange(null,null,null, map);
//		return "1";
//	}

//	@Override
//	public String caseDataExchange_sxsj(String str) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("ddcid", str);
//		map.put("autoruninsertonly", 0);
//		map.put("status", "sys");
//		dbmsNewDataExportService.caseDataExchange(null,null,null, map);
//		//执行成功后  给科室用户发送通知
//		SendInfoMessage();
//		return "1";
//	}
	
	
//	/***
//	 * 用户通知：省局回复
//	 */
//	public void SendInfoMessage(){
//		int noticeid=TbuserNoticeMapper.selectNoticeid();
//		TbuserNotice notice = new TbuserNotice();
//		notice.setNoticeid(noticeid);
//		//notice.setText1("");
//		notice.setMessagetype(3);
//		notice.setTitle(GkzxCommon.SHENGJUHUIFUTITLE);
//		notice.setContent(GkzxCommon.SHENGJUHUIFUCONTENT);
//		notice.setUsername("刑罚执行科用户");
//		notice.setEndtime(new Date());
//		notice.setStarttime(new Date());
//		notice.setState(0);
//		notice.setOptime(new Date());
//		notice.setOpid("sysauto");
//		userNoticeService.insertDataToUserNotice(notice);
//		
//		Map map = new HashMap();
//		map.put("servicesinfo", "servicesinfo");//角色信息表remark字段值servicesinfo，根据此职判断是否给此角色下用户发送信息
//		List<Map<String,Object>> list=userorgwrappermapper.getUserByMap(map);
//		for(Map<String,Object> m:list){
//			String userid =(String)	m.get("USERID");
//			TbuserUserNotice usernotice=new TbuserUserNotice();
//			usernotice.setNoticeid(noticeid);//通知id
//			usernotice.setOpid("sysauto");
//			usernotice.setOptime(new Date());
//			usernotice.setUserid(userid);//用户id
//			tbuserUserNoticeMapper.insert(usernotice);
//		}
//	}
	
	
}
