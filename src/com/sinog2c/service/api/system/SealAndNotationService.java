package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemUser;

public interface SealAndNotationService {
	
	/**
	 * 描述：通过符合的案件号得到草稿ID: flowdraftid
	 * @author YangZR	2015-04-01
	 * @param	casetype：案件类型		year：案件年份		casenos：案件号 26
	 * @return	flowdraftid
	 */
	public String getFlowdraftidByCaseNo(String casetype,String year,String caseno, SystemUser user, SignatureScheme signatureScheme,String caseid, String xingqileixing);
	
	//签章(撤销签章)步骤
	
	//查询签章方案
	public List<SignatureScheme> getSignatureSchemesByUser(Map<String,Object> map);
	
	//选择合话的签章方案
	
	//通过签章方案，过滤出不符合签章的案件号caseno，将不符合的案件号返回，并得到符合的casenos。
	//不符合的caseno过滤标准：
	//a. 不存在大字段	notExistClobCaseNos
	//b.存在大字段，但不是该部门审批	notFitOrgCaseNos
	//c.是该部门审批，但未在本级审批	notThisLevelCaseNos
	//d.返回符合的案件号	fitCaseNos
	/**
	 * 解析案件号
	 */
	public Map parseCaseNos(String casetype,String year,String casenos, SystemUser user, SignatureScheme signatureScheme, String caseid);
	
	
	//通过符合的案件号casenos，获得flowdraftids
	/**
	 * 描述：通过符合的案件号得到草稿ID: flowdraftids
	 * @author YangZR	2015-03-11
	 */
	public String getFlowdraftidsByCaseNos(String casetype,String year,String casenos, SystemUser user, SignatureScheme signatureScheme, String codeid, String xingqileixing);
	
	
	//将合适的flowdraftids传回界面，取得第一个flowdraftid
	
	//通过flowdraftid，及选择的签章方案，通过查询方案，查询出需要签章的大字段
	//如果要求判断签章进程，此时不符合签章(撤销签章)进程的要返回说明。
	public List<Map<String,Object>> getSealData(String flowdraftid, SignatureScheme signatureScheme, Integer sealOrRevoke, SystemUser user, Map<String,Object> paramap,String sealdate);
		
	//某查询方案可能查询一些额外数据：如监狱意见，签章流水表等
	
	//将查询的数据（可能多个大字段）传至页面
	
	//有些大字段需要加入一些数据（如省局审核表加入监狱意见等），大字段盖章(撤销签章); 
	
	//将大字段（可能多个）保存
	/**
	 * 描述：盖章或批注后，通过查询方案保存大字段信息
	 */
	public List<Map<String,Object>> saveDataAfterSeal(List<Map<String,Object>> list, SystemUser user);
	
	
	
	//递归的签章(撤销签章)，直到结束
	
	
	/**
	 * 描述：判断某个案件是否符合签章规则
	 * 
	 */
	public Object singleCheckSeal(Map<String,Object> paramap, SystemUser user);
	
}
