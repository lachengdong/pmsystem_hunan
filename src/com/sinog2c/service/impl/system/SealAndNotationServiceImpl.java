package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.system.SealAndNotationService;
import com.sinog2c.service.api.system.SignatureFlowService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.TbViewMaintainService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;

/**
 * 描述：签章和批注
 * @author YangZR
 * @date 2015-3-11 
 */
@Service("sealAndNotationService")
public class SealAndNotationServiceImpl extends ServiceImplBase implements SealAndNotationService{
	
	@Autowired
	private SignatureSchemeService signatureSchemeService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbViewMaintainService tbViewMaintainService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private SignatureFlowService signatureFlowService;
	@Autowired
	private CommonSQLSolutionService solutionService;



	/**
	 * 查询签章方案
	 * @author YangZR	2015-03-30
	 */
	@Override
	public List<SignatureScheme> getSignatureSchemesByUser( Map<String, Object> map) {
		return signatureSchemeService.getSignatureSchemesByUser(map);
	}
	
	/**
	 * 描述：通过符合的案件号得到草稿ID: flowdraftids
	 * @author YangZR	2015-03-11
	 * @param	casetype：案件类型		year：案件年份		casenos：案件号 1,3,5-10
	 * @return	
	 */
	@Override
	@SuppressWarnings("all")
	public String getFlowdraftidsByCaseNos(String casetype,String year,String casenos, SystemUser user, SignatureScheme signatureScheme,String caseid,String xingqileixing){
		
		String casenums = StringNumberUtil.formatCaseNo(casenos,year);
		List<String> casenumsList = StringNumberUtil.formatString2List(casenums, ",");
		casenums = StringNumberUtil.formatString(casenums, ",");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		Map paramap = new HashMap();
		paramap.put("casenums", casenums);
		paramap.put("flowdefid", signatureScheme.getFlowdefid());
		paramap.put("departid", user.getDepartid());
		paramap.put("orgid", user.getOrgid());
		paramap.put("casetype", casetype);
		paramap.put("caseid", caseid);
		paramap.put("provincecode", provincecode);
		paramap.put("xingqileixing", xingqileixing);
		//paramap = parseCaseType(casetype, paramap, provincecode);
		
		StringBuffer flowdraftidSB = new StringBuffer();
		List<Map<String,Object>> casenumList = flowBaseService.getFlowdraftidsByCondition(paramap);
		for(Map<String,Object> map : casenumList){
			flowdraftidSB.append(map.get("flowdraftid").toString()).append(",");
		}
		String flowdraftids = flowdraftidSB.toString();
		flowdraftids = StringNumberUtil.removeLastStr(flowdraftids, ",");
		
		return flowdraftids;
		
	}
	
	/**
	 * 描述：通过符合的案件号得到草稿ID: flowdraftid
	 * @author YangZR	2015-04-01
	 * @param	casetype：案件类型		year：案件年份		casenos：案件号 26
	 * @return	flowdraftid
	 */
	@Override
	public String getFlowdraftidByCaseNo(String casetype,String year,String caseno, SystemUser user, SignatureScheme signatureScheme, String caseid, String xingqileixing){
		return getFlowdraftidsByCaseNos(casetype,year,caseno, user, signatureScheme, caseid, xingqileixing);
	}
	
	
	/**
	 * 将201511,201515,201523...的案件号List 解析为11,15,23...的字符串
	 * @param casenumList
	 * @return
	 */
	private String parseCaseYearNum2CaseNum(List<String> casenumList){
		String casenos = "";
		if(null != casenumList && casenumList.size()>0){
			for(String temp : casenumList){
				casenos += temp.substring(4)+",";
			}
			if(casenos.endsWith(",")){
				casenos = StringNumberUtil.removeLastStr(casenos, ",");
			}
		}
		return casenos;
	}
	
	
	/**
	 * 解析案件号
	 */
	@Override
	public Map parseCaseNos(String casetype,String year,String casenos, SystemUser user, SignatureScheme signatureScheme,String caseid){
		
		Map<String,String> resultMap = new HashMap<String,String>();
		
		String notExistClobCaseNos = "";//不存在大字段的案件号
		String notFitOrgCaseNos = ""; //存在大字段，但不是该部门审批的案件号
		String notThisLevelCaseNos = ""; //是该部门审批，但未在本级审批的案件号
//		String notFitSignRuleCaseNos = ""; //存在大字段，也在本级审批，且要判断签章进程，此时不符合签章(撤销签章)进程的案件号。
		String fitCaseNos = "";//符合的案件号：存在大字段、该部门审批、在本级审批
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		//组装参数查询条件
		Map<String, Object> paramap = new HashMap();
		paramap.put("flowdefid", signatureScheme.getFlowdefid());
		paramap.put("tempid", signatureScheme.getTempid());
		paramap.put("departid", user.getDepartid());
		paramap.put("orgid", user.getOrgid());
		paramap.put("casetype", casetype);
		
//		paramap = parseCaseType(casetype, paramap, provincecode);
		
		String preCasenums = StringNumberUtil.formatCaseNo(casenos,year);//页面传递过来的案件号，解析为 20141,20143,20145,20146...等
		
		
		//根据当前所提供的案件号，查询出是否存在大字段的案件号
		String existClobCasenums = "";//此案件号带年份
		Integer contentfilter = signatureScheme.getContentfilter();//contentfilter : 判断文书是否存在，0：不判断，1：判断
		if(null != contentfilter && 1==contentfilter){
			
			List<String> notExistClobCasenumList = null;
			List<String> existClobCasenumList = null;
			String sealtype = signatureScheme.getSealtype();
			if(StringNumberUtil.isEmpty(sealtype) || "0".equals(sealtype)){
				List<List<String>> IsExistClobCasenumsResult = parseIsExistClobCasenums(paramap, preCasenums);
				if(null !=IsExistClobCasenumsResult && IsExistClobCasenumsResult.size()==2){
					notExistClobCasenumList = IsExistClobCasenumsResult.get(0);
					existClobCasenumList = IsExistClobCasenumsResult.get(1);
				}
				notExistClobCaseNos = parseCaseYearNum2CaseNum(notExistClobCasenumList);//案件号去掉带年份
			}else{
				existClobCasenumList = StringNumberUtil.formatString2List(preCasenums, ",");
			}
			
			existClobCasenums = StringNumberUtil.formatList2String(existClobCasenumList,",");
			
		}else{
			existClobCasenums = preCasenums;
		}
		
		
//		String sealtype = signatureScheme.getSealtype();
//		if(StringNumberUtil.isEmpty(sealtype) || "0".equals(sealtype)){
//			List<List<String>> IsExistClobCasenumsResult = parseIsExistClobCasenums(paramap, preCasenums);
//			if(null !=IsExistClobCasenumsResult && IsExistClobCasenumsResult.size()==2){
//				notExistClobCasenumList = IsExistClobCasenumsResult.get(0);
//				existClobCasenumList = IsExistClobCasenumsResult.get(1);
//			}
//			notExistClobCaseNos = parseCasenums2Casenos(notExistClobCasenumList);
//		}else{
//			existClobCasenumList = StringNumberUtil.formatString2List(preCasenums, ",");
//		}
		
		//存在大字段，但不是该部门审批
//		String existClobCasenums = StringNumberUtil.formatList2String(existClobCasenumList,",");
		
		
		
		//存在大字段，但不是该部门审批
		List<List<String>> IsFitOrgCasenumsResult = parseIsFitOrgClobCasenums(paramap,existClobCasenums);
		List<String> notFitOrgCasenumList = null;
		List<String> fitOrgCasenumList = null;
		if(null!=IsFitOrgCasenumsResult && IsFitOrgCasenumsResult.size()==2){
			notFitOrgCasenumList = IsFitOrgCasenumsResult.get(0);
			fitOrgCasenumList = IsFitOrgCasenumsResult.get(1);//fitOrgCasenumList为即存在大字段，又符合部门的案件号
		}
		notFitOrgCaseNos = parseCasenums2Casenos(notFitOrgCasenumList);
		
		
		
		//是该部门审批，但未在本级审批
		String fitOrgCasenums = StringNumberUtil.formatList2String(fitOrgCasenumList,",");
		List<List<String>> IsThisLevelCasenumsResult = parseIsThisLevelCasenums(paramap, fitOrgCasenums , user, signatureScheme.getFlowdefid());
		List<String> notThisLevelCasenumList = null;
		List<String> thisLevelCasenumList = null;
		if(null!=IsThisLevelCasenumsResult && IsThisLevelCasenumsResult.size()==2){
			notThisLevelCasenumList = IsThisLevelCasenumsResult.get(0);
			thisLevelCasenumList = IsThisLevelCasenumsResult.get(1);//即存在大字段，又符合部门，也符合当前级别审批的的案件号
		}
		notThisLevelCaseNos = parseCasenums2Casenos(notThisLevelCasenumList);
		
		//存在大字段，也在本级审批，当要求判断签章进程时，解析是否符合签章(撤销签章)进程。在thisLevelCasenumList中取出
		//不在此处理
		
		//
		fitCaseNos = parseCasenums2Casenos(thisLevelCasenumList);
		
		resultMap.put("notExistClobCaseNos", notExistClobCaseNos);
		resultMap.put("notFitOrgCaseNos", notFitOrgCaseNos);
		resultMap.put("notThisLevelCaseNos", notThisLevelCaseNos);
		resultMap.put("fitCaseNos", fitCaseNos);
		
		return resultMap;
	}
	
	
	private Map<String, Object> parseCaseType(String casetype, Map<String, Object> paramap, String provincecode){
		if(StringNumberUtil.notEmpty(casetype)){
//			if(casetype.equals("jx")||casetype.equals("sjjx")){
//				paramap.put("casetype", 0); //GkzxCommon.CASE_TYPE_JXJS
//			}else if(casetype.equals("js")){ 
//				paramap.put("casetype", 1); //GkzxCommon.CASE_TYPE_JS
//			}else if(casetype.equals("cbz")){
//				paramap.put("casetype", 2);//GkzxCommon.CASE_TYPE_CBZ
//			}else if(casetype.equals("xbz")){
//				paramap.put("casetype", 3); //GkzxCommon.CASE_TYPE_XBZ
//			}else if(casetype.equals("xzz")){//广东刑执字
//				//如果为刑执字, casetype设为100
//				paramap.put("casetype", 100);
//				paramap.put("provincecode", provincecode);
//			}
		}
		return paramap;
	}

	/**
	 * 案件号中，查询出不存在大字段的案件号
	 * @author YangZR	2015-03-30
	 * @param	paramap查询的参数
	 * @return	返回List<List<String>>	get(0):不符合，get(1)：符合
	 */
	public List<List<String>>  parseIsExistClobCasenums(Map<String, Object> paramap, String casenums){
		if(StringNumberUtil.isEmpty(casenums)){
			return null;
		}
		String casenumsCopy = new String(casenums);
		casenumsCopy = StringNumberUtil.formatCasenums(casenumsCopy);
		paramap.put("casenums", casenumsCopy);
		List<String> casenumList = StringNumberUtil.formatString2List(casenums, ",");
		List<Map> existClobCasenumListMap = flowBaseOtherService.selectCpdnumbersByCpdnumber(paramap);//存在大字段的案件号
		List<String> existClobCasenumList = new ArrayList<String>(); //存放 存在大字段的案件号
		if(null!=existClobCasenumListMap && existClobCasenumListMap.size()>0){
			for(Map map : existClobCasenumListMap){
				String casenum = map.get("casenums").toString();
				existClobCasenumList.add(casenum);
			}
		}
		//casenumList 中去掉那些存在的案件号，得到不存在的案件号
		casenumList.removeAll(existClobCasenumList);//此时：casenumList为不存在大字段的案件号
		
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(casenumList);
		list.add(existClobCasenumList);
		return list;
	}
	
	/**
	 * 解析案件号是否符合当前部门
	 * @param paramap
	 * @param casenums
	 * @return	返回List<List<String>>	get(0):不符合，get(1)：符合
	 */
	public List<List<String>> parseIsFitOrgClobCasenums(Map paramap, String casenums){
		if(StringNumberUtil.isEmpty(casenums)){
			return null;
		}
		String casenumsCopy = new String(casenums);
		casenumsCopy = StringNumberUtil.formatCasenums(casenumsCopy);
		paramap.put("casenums", casenumsCopy);
//		paramap.put("casenums", casenums);
		List<String> casenumList = StringNumberUtil.formatString2List(casenums, ",");
		List<Map<String,Object>> fitOrgCasenumListMap= flowBaseService.getCasenumsByCondition(paramap);
		List<String> fitOrgCasenumList = new ArrayList<String>();//符合当前用户的部门所属的案件号 List
		if(null!=fitOrgCasenumListMap && fitOrgCasenumListMap.size()>0){
			for(Map map : fitOrgCasenumListMap){
				String casenum = map.get("casenum").toString();
				fitOrgCasenumList.add(casenum);
			}
		}
		//casenumList  去掉符合当前用户的部门所属的案件号，得到不符合的案件号
		casenumList.removeAll(fitOrgCasenumList);//此时：casenumList为不符合当前用户的部门所属的案件号
		
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(casenumList);
		list.add(fitOrgCasenumList);
		
		return list;
	}
	
	
	/**
	 * 解析案件号是否流转至当前用户审批
	 * @param paramap
	 * @param casenums
	 * @return	返回List<List<String>>	get(0):不符合，get(1)：符合
	 */
	public List<List<String>> parseIsThisLevelCasenums(Map paramap, String casenums , SystemUser user, String flowdefid){
		if(StringNumberUtil.isEmpty(casenums)){
			return null;
		}
		SystemOrganization so = systemOrganizationService.getByOrganizationId(user.getDepartid());
		paramap.put("provinceid", so.getPorgid());
		Map condi = tbViewMaintainService.selectByCondition(paramap);
		if(null!=condi){
			//provinceid和nodeid用于当某个省份的监狱减刑假释审批是，某一级节点的一些特殊处理时传的参数
			Object provinceid = condi.get("provinceid");
			Object flowdefidObj = condi.get("flowdefid");
			Object nodeid = condi.get("nodeid");
			Object days = condi.get("days");
			if(StringNumberUtil.notEmpty(provinceid)&&StringNumberUtil.notEmpty(nodeid)&&StringNumberUtil.notEmpty(days)){
				paramap.put("provinceid", provinceid);
				paramap.put("nodeid", nodeid);
				paramap.put("days", days);
				paramap.put("flowdefidConn", flowdefidObj);
	    	}
		}
		//获取该用户拥有的按钮资源id
		Object buttonstr = systemResourceService.returnButtonResourceByUser(user, flowdefid);
//		String buttonstr = systemResourceService.returnUserResourceByCondition(user, flowdefid);//如果上面调用的方法被改了，请注释上面一行，并打开当前行。
		paramap.put("connsql", buttonstr);
		paramap.put("suid",user.getUserid());
		
		//案件流转至当前级别时，案件审批人审批的案件号。
//		paramap.put("casenums", casenums);
		String casenumsCopy = new String(casenums);
		casenumsCopy = StringNumberUtil.formatCasenums(casenumsCopy);
		paramap.put("casenums", casenumsCopy);
		List<String> casenumList = StringNumberUtil.formatString2List(casenums, ",");
		List<Map> thisLevelCasenumListMap = uvFlowService.getCasenumsOfCommuteByCondition(paramap);
		List<String> thisLevelCasenumList = new ArrayList<String>();
		if(null!=thisLevelCasenumListMap && thisLevelCasenumListMap.size()>0){
			for(Map map : thisLevelCasenumListMap){
				String casenum = map.get("casenums").toString();
				thisLevelCasenumList.add(casenum);
			}
		}
		
		//casenumList  去掉流转至当前用户下审批的案件号
		casenumList.removeAll(thisLevelCasenumList);//此时：casenumList为未流转至当前用户下审批的案件号
		
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(casenumList);
		list.add(thisLevelCasenumList);
		
		return list;
	}
	
	/**
	 * 	@获取签章条件
	 * @author YangZR	2015-03-31
	 * 返回值List中的Map：如果符合签章，则返回Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
	 * 																			flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid,  signtype:signtype, signname:signname,
	 * 																			sealtype:sealtype
	 * 																		}
	 * 			  				 如果不符合签章，则返回Map{sealable:0, signtype:signtype,  signname:signname, status:status}
	 */
	@Override
	public List<Map<String,Object>> getSealData(String flowdraftid, SignatureScheme signatureScheme, Integer sealOrRevoke, SystemUser user, Map<String,Object> paramap,String sealdate){
		
//		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String ischeckseal = StringNumberUtil.returnString2(jyconfig.getProperty("ischeckseal"));
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Integer signid = signatureScheme.getSignid();
		paramap.put("psignid", signid);
		paramap.put("sealdate",sealdate);
		List<SignatureScheme> signSchemeListTemp = signatureSchemeService.getSignatureSchemesByPsignid(paramap);
		List<SignatureScheme> signatureSchemeList = new ArrayList<SignatureScheme>();
		signatureSchemeList.add(signatureScheme);
		signatureSchemeList.addAll(signSchemeListTemp);
		
		String ischeckseal = getIsCheckSeal(user);
		
		if(StringNumberUtil.notEmpty(ischeckseal)&&ischeckseal.equals("1")){
			for(SignatureScheme sg: signatureSchemeList){
				String sealtype = sg.getSealtype();
				if(StringNumberUtil.isEmpty(sealtype) || "0".equals(sealtype)){
					
					if(1 == sg.getIsseal()){
						//需要检查签章
						result.add(getCheckSealInfo(flowdraftid, sg, sealOrRevoke , user,paramap));
					}else{
						//不需要检查签章
						result.add(notCheckSealInfo(flowdraftid, sg, sealOrRevoke , user,paramap));
					}
					
				}else if("1".equals(sealtype)){
					result.add(reportSealInfo(flowdraftid, sg, sealOrRevoke , user, paramap));
				}
				
			}
		}else{
			//不需要检查签章
			for(SignatureScheme sg: signatureSchemeList){
				String sealtype = sg.getSealtype();
				if(StringNumberUtil.isEmpty(sealtype) || "0".equals(sealtype)){
					result.add(notCheckSealInfo(flowdraftid, sg, sealOrRevoke , user,paramap));
				}else if("1".equals(sealtype)){
					result.add(reportSealInfo(flowdraftid, sg, sealOrRevoke , user, paramap));
				}
			}
		}
		
		return result;
	}
	
	public String getIsCheckSeal(SystemUser user){
		Map<String,Object> para4cfgMap = new HashMap<String,Object>();
		para4cfgMap.put("departid", user.getDepartid());
		para4cfgMap.put("name", "ischeckseal");
		SystemConfigBean systemConfigBean = systemConfigService.getConfigByMap(para4cfgMap);
		return systemConfigBean!=null?systemConfigBean.getValue():GkzxCommon.ZERO;
	}
	
	
	/**
	 * @在需要检查签章进程的情况下，获取签章数据
	 * @author YangZR	2015-03-31
	 * @param sealOrRevoke    1:签章，２:撤销签章
	 * @return
	 * 如果符合签章(撤销签章)，则返回Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
	 * 															flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid,signtype:signtype, signname:signname,sealtype:sealtype}
	 * 	如果不符合签章(撤销签章)，则返回Map{sealable:0, signtype:signtype,  signname:signname, status:status}
	 */
	public Map<String,Object> getCheckSealInfo(String flowdraftid, SignatureScheme signatureScheme,Integer sealOrRevoke , SystemUser user,Map<String,Object> fmap){
		
		//通过签章流水表，查询当前文书的盖章情况
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("flowdraftid", flowdraftid);
		paramap.put("sealdate", fmap.get("sealdate")==null?"":fmap.get("sealdate").toString());
		//
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
		paramap.put("applyid", fb.getApplyid());
		paramap.put("applyname", fb.getApplyname());
		//
		paramap.put("flowdefid", signatureScheme.getFlowdefid());
		paramap.put("tempid", signatureScheme.getTempid());
		Map<String,Object> signatureFlowMap = signatureFlowService.getLastSignatureFlow(paramap);
		
		Integer realSeal = signatureFlowMap==null?0:Integer.parseInt(signatureFlowMap.get("sealnum").toString());
		//
		Map<String,Object> sealCondi = null;
		String docconent = flowBaseOtherService.getDocconentByConditionTwo(paramap);
		if(StringNumberUtil.isEmpty(docconent)){
			sealCondi = new HashMap<String,Object>();
			sealCondi.put("sealable", 0);
			sealCondi.put("signtype", signatureScheme.getSigntype());//签章文书（如监狱减刑审核表）
			sealCondi.put("signname", signatureScheme.getName());//签章类型（如监狱长公章）
			sealCondi.put("status", "文书不存在！");
			return sealCondi;
		}
		
		if(1 == sealOrRevoke){
			//签章时，当前章数必须小于maxSeal;
			Integer maxSeal = signatureScheme.getSeal();
			sealCondi = sealCheck(realSeal,maxSeal,null,sealOrRevoke);
		}else if(2 == sealOrRevoke){
			//撤章时，当前章数必须大于minSeal;
			//获取上一个签章进程
			SignatureScheme preSignatureScheme = signatureSchemeService.getPreSignatureScheme(signatureScheme);
			Integer minSeal = preSignatureScheme==null? 0 : preSignatureScheme.getSeal();
			sealCondi = sealCheck(realSeal,null,minSeal,sealOrRevoke);
		}
		
		if(null != sealCondi){
			Integer  sealable = (Integer)sealCondi.get("sealable");
			if(1 == sealable){
				sealCondi.put("flowdraftid", flowdraftid);
				sealCondi.put("tempid", signatureScheme.getTempid());
				sealCondi.put("flowdefid", signatureScheme.getFlowdefid());
				sealCondi.put("solutionid", signatureScheme.getSolutionid());
				sealCondi.put("content", signatureScheme.getContent());
				sealCondi.put("scheme", signatureScheme.getScheme());
				sealCondi.put("signtype", signatureScheme.getSigntype());//签章文书（如监狱减刑审核表）
				sealCondi.put("signname", signatureScheme.getName());//签章类型（如监狱长公章）
				sealCondi.put("protectnode", signatureScheme.getProtectnode());//数字签名
				sealCondi.put("sealtype", signatureScheme.getSealtype());//
				
				Map<String,Object> resultMap = solutionService.query(sealCondi, user);
				Map<String,Object> form = (Map<String,Object>)resultMap.get("form");
				Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
				
				sealCondi.put("aipFileStr", form.get("doc_content"));
				if(null != map){
					map = StringNumberUtil.dealMapForForm(map);
					sealCondi.put("formdata", new JSONObject(map).toString());
				}
				
			}else{
				sealCondi.put("signtype", signatureScheme.getSigntype());//签章文书（如监狱减刑审核表）
				sealCondi.put("signname", signatureScheme.getName());//签章类型（如监狱长公章）
				sealCondi.put("status", "不符合签章规则！");
			}
			return sealCondi;
		}
		
		return null;
	}
	
	/**
	 * 检查签章情况
	 * @author YangZR	2015-03-31
	 * @param realSeal：现大字段的签章数
	 * @param maxSeal：本大字段在本级最多签章数（针对盖章）
	 * @param minSeal：本大字段在本级最少签章数（针对撤章）
	 * @param sealOrRevoke    1:签章，２:撤销签章
	 * @return Map {sealable:sealable, status:status}
	 */
	public Map<String,Object> sealCheck(Integer realSeal, Integer maxSeal, Integer minSeal, Integer sealOrRevoke){
		Map<String,Object> result = new HashMap<String,Object>();
		if(1 == sealOrRevoke){
			if(realSeal < maxSeal){
				result.put("sealable", 1);
			}else{
				result.put("sealable", 0);
				result.put("status", "已签章，不能再签！");
			}
		}else if(2 == sealOrRevoke){
			if(realSeal > minSeal){
				result.put("sealable", 1);
			}else{
				result.put("sealable", 0);
				result.put("status", "已撤销签章，不能重复撤销！");
			}
		}
		return result;
	}
	
	/**
	 * 不需要检查签章条件时，
	 * 返回Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
	 * 				　flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid, signtype:signtype, signname:signname}
	 * @author YangZR	2015-03-31
	 */
	public Map<String,Object> notCheckSealInfo(String flowdraftid, SignatureScheme signatureScheme,Integer sealOrRevoke , SystemUser user,Map<String,Object> fmap){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("sealable", 1);
		//
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("flowdraftid", flowdraftid);
		paramap.put("sealdate", fmap.get("sealdate")==null?"":fmap.get("sealdate").toString());
		//
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
		paramap.put("applyid", fb.getApplyid());
		paramap.put("applyname", fb.getApplyname());
		//
		paramap.put("tempid", signatureScheme.getTempid());
		paramap.put("flowdefid", signatureScheme.getFlowdefid());
		paramap.put("solutionid", signatureScheme.getSolutionid());
		paramap.put("content", signatureScheme.getContent());
		paramap.put("scheme", signatureScheme.getScheme());
		paramap.put("signtype", signatureScheme.getSigntype());//签章文书（如监狱减刑审核表）
		paramap.put("signname", signatureScheme.getName());//签章类型（如监狱长公章）
		paramap.put("protectnode", signatureScheme.getProtectnode());//数字签名
		paramap.put("sealtype", signatureScheme.getSealtype());//
		result.putAll(paramap);
		//
		Map<String,Object> resultMap = solutionService.query(paramap, user);
		Map<String,Object> form = (Map<String,Object>)resultMap.get("form");
		Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
		if(null != form){
			result.put("aipFileStr", form.get("doc_content"));
		}
		if(null!=map){
			map = StringNumberUtil.dealMapForForm(map);
			result.put("formdata", new JSONObject(map));
		}
		
		
		return result;
	}
	
	
	/**
	 * 报表的签章条件时，
	 * 返回Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
	 * 				　flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid, signtype:signtype, signname:signname}
	 * @author YangZR	2015-03-31
	 */
	public Map<String,Object> reportSealInfo(String flowdraftid, SignatureScheme signatureScheme,Integer sealOrRevoke , SystemUser user, Map<String,Object> fmap){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("sealable", 1);
		//
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("flowdraftid", flowdraftid);
		//
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
		paramap.put("applyid", fb.getApplyid());
		paramap.put("applyname", fb.getApplyname());
		paramap.put("sealdate",fmap.get("sealdate")==null?"":fmap.get("sealdate").toString());
		//
		paramap.put("tempid", signatureScheme.getTempid());
		paramap.put("flowdefid", signatureScheme.getFlowdefid());
		paramap.put("solutionid", signatureScheme.getSolutionid());
		paramap.put("content", signatureScheme.getContent());
		paramap.put("scheme", signatureScheme.getScheme());
		paramap.put("signtype", signatureScheme.getSigntype());//签章文书（如监狱减刑审核表）
		paramap.put("signname", signatureScheme.getName());//签章类型（如监狱长公章）
		paramap.put("protectnode", signatureScheme.getProtectnode());//数字签名
		paramap.put("sealtype", signatureScheme.getSealtype());//
		result.putAll(paramap);
		
		Map<String,Object> resultMap = solutionService.query(paramap, user);
		
		Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
		if(null!=map){
			map = StringNumberUtil.dealMapForForm(map);
			result.put("formdata", new JSONObject(map));
		}
		
		RMEngine engine=this.systemResourceService.getReportEngin(paramap, user,resultMap);
		//
		String reportPath=fmap.get("reportPath").toString();
		String reportName = signatureScheme.getText3();
		engine.SetReportFile(reportPath + reportName + ".rmf", 1);
		//
		
		String reportdata = engine.dedaoReportData();
		result.put("reportdata", reportdata);
		
		return result;
	}
	
	
	/**
	 * 将201511,201515,201523...的案件号List 解析为11,15,23...的字符串
	 * @param casenumList
	 * @return
	 */
	private String parseCasenums2Casenos(List<String> casenumList){
		String casenos = "";
		if(null != casenumList && casenumList.size()>0){
			for(String temp : casenumList){
				casenos += temp.substring(4)+",";
			}
			if(casenos.endsWith(",")){
				casenos = StringNumberUtil.removeLastStr(casenos, ",");
			}
		}
		return casenos;
	}
	
	
	/**
	 * 描述：盖章后，通过查询方案保存大字段信息
	 * return List<Map>	  Map{year:year,casetype:casetype,caseno:caseno,
	 * 										signtype:signtype,signname:signname}
	 */
	@Override
	@Transactional
	public List<Map<String,Object>> saveDataAfterSeal(List<Map<String,Object>> list, SystemUser user){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> paramap: list){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("signtype", paramap.get("signtype").toString());//签章文书（如监狱减刑审核表）
			map.put("signname", paramap.get("signname").toString());//签章类型（如监狱长公章）
			//将key为aipFileStr转为查询方案操作大字段的统一用的doc_content
			paramap.put("doc_content", paramap.get("aipFileStr"));
			paramap.remove("aipFileStr");
			solutionService.save(paramap, user);
			map.put("flowdraftid", paramap.get("flowdraftid").toString());//flowdraftid
			String text8 = flowBaseService.getTextByflowdraftid(map);
			if(StringNumberUtil.notEmpty(text8)){
				String notationSeal[] = text8.split("@");
				try {
					if (StringNumberUtil.parseInt(notationSeal[1], 0)<StringNumberUtil.parseInt(paramap.get("sealnum").toString(), 0)){
						//保存flowbase表的签章个数text8
						flowBaseService.updateTextByflowdraftid(
								paramap.get("notationnum").toString()+"@"+paramap.get("sealnum").toString(),
								paramap.get("flowdraftid").toString());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			//签章流水
			int count = signatureFlowService.insertMapSelective(paramap);
			if(count<=0){
				throw new RuntimeException();
			}
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 描述：判断某个案件是否符合签章规则
	 * 
	 */
	@Override
	public Object singleCheckSeal(Map<String,Object> paramap,SystemUser user){
		 JSONMessage message = JSONMessage.newMessage();
		 String ischeckseal = getIsCheckSeal(user);
		 //如果系统不需要检查签章进程
		 if(StringNumberUtil.isEmpty(ischeckseal)||ischeckseal.trim().equals("0")){
			 message.setSuccess();
			 return message;
		 }
		 //通过flowdefid, tempid, userid 查询签章方案
		 paramap.put("userid", user.getUserid());
		 paramap.put("departid", user.getDepartid());
		 SignatureScheme signatureScheme = signatureSchemeService.getSignatureSchemeByCondition(paramap);
		 //如果没有配签章方案，或者签章方案是否验证签章不为1的，说明不需要控制签章进程
		if(null == signatureScheme || signatureScheme.getIsseal()<1){
			message.setSuccess();
			return message;
		}
		//提交才需要验证签章个数，退回和不予办理暂时不验证
		String operateType = paramap.get("operateType").toString();
		if(!"yes".equals(operateType)){
			message.setSuccess();
			return message;
		}
		//获取当前页面的签章个数，和当前用户所拥有的签章方案最大的一条进行比对，例如监区长有签字和公章，按照最大的进行验证
		int seal = paramap.get("seal")==null ? 0 : Integer.parseInt(paramap.get("seal").toString());
		//根据 flowdraftid 找到该案件的applyid是属于哪个部门，用于区分部门级别
		String orglevel = "";//部门级别
		List<Map> orginfolist = systemOrganizationService.getOrginfoByFlowdraftid(paramap);
		if(orginfolist.size() > 0){
			Map map = orginfolist.get(0);
			orglevel = (String) map.get("UNITLEVEL");
		}
		//根据部门级别找到当前用户应该判断几个签章
		String returnvalue = checkSeal(orglevel,signatureScheme,seal);
		if("error".equals(returnvalue)){
			message.setErrorcode("2");//代表验证不通过
			message.setInfo("表单需要有"+signatureScheme.getSeal()+"个签章才可以提交！当前有"+seal+"个。");
			message.setSuccess();
			return message;
		}
		
		String flowdraftid = paramap.get("flowdraftid").toString();
		paramap.put("psignid", signatureScheme.getSignid());
		List<SignatureScheme> signatureSchemeList = signatureSchemeService.getSignatureSchemesByPsignid(paramap);
		
		for(SignatureScheme sg: signatureSchemeList){
			if(1 == sg.getIsseal()){
				//需要检查签章
				checkSeal(message,sg,flowdraftid,operateType);
			}
		}
		return message;
	}
	 
	 /**
	  * 验证签章进程
	  * 
	  * */
	 public String checkSeal(String orglevel,SignatureScheme signatureScheme,int seal){
		 String returnvalue = "error";
		 int sinseal = signatureScheme.getSeal();//该签章方案需要有几个签章
		 //如果是分监区级别的验证数量+1
		 if("4".equals(orglevel)){
			 sinseal = sinseal+1;
		 }
		 if(seal == sinseal){
			 returnvalue = "success";
		 }
		 return returnvalue;
	 }
	 
	 public void checkSeal(JSONMessage message, SignatureScheme signatureScheme,String flowdraftid,String operateType){
		 
	 }
	
}
