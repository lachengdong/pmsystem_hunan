package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.system.SystemOrganizationMapper;
import com.sinog2c.dao.api.system.TbsysCodeMapper;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.service.api.common.CommonFormService;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.PrintBatchService;
import com.sinog2c.service.api.system.SignatureSchemeService;
@Service
public class PrintBatchServiceImpl implements PrintBatchService {

	@Autowired
	private TbsysCodeMapper tbsysCodeMapper;
	@Autowired
	private SystemOrganizationMapper organizationMapper = null;
	@Autowired
	private SignatureSchemeService signatureSchemeService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private CommonSQLSolutionService solutionService;
	@Autowired
	private CommonFormService commonFormService;
	
	
	
	@Override
	public List<TbsysCode> getPrintBatchCaseType(HttpServletRequest request) {
		List<TbsysCode> list = null;
		String qtype = request.getParameter("qtype");
		list = tbsysCodeMapper.getCodeByCodeType(GkzxCommon.CASETYPE_CODE,qtype);
		return list;
	}
	
	@Override
	public SystemOrganization getByOrganizationId(String orgid) {
		SystemOrganization org = organizationMapper.getByOrgId(orgid);
		return org;
	}

	
	
	@Override
	public List<Map<String, Object>> getPrintData(String flowdraftid, SignatureScheme signatureScheme, SystemUser user) {
		
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		Integer signid = signatureScheme.getSignid();
		Map paramap = new HashMap();
		paramap.put("psignid", signid);
		List<SignatureScheme> signSchemeListTemp = signatureSchemeService.getSignatureSchemesByPsignid(paramap);
		List<SignatureScheme> signatureSchemeList = new ArrayList<SignatureScheme>();
		signatureSchemeList.add(signatureScheme);
		signatureSchemeList.addAll(signSchemeListTemp);
		
		for(SignatureScheme sg: signatureSchemeList){
			resultList.add(getPrintDate(flowdraftid, sg, user));
		}
		
		return resultList;
		
	}
	
	/**
	 * 返回Map{aipFileStr:aipFileStr, formdata:formdata, flowdraftid:flowdraftid, 
	 * 					tempid:tempid,flowdefid:flowdefid, signtype:signtype, signname:signname..... }
	 * @author YangZR	2015-05-04
	 */
	public Map<String,Object> getPrintDate(String flowdraftid, SignatureScheme signatureScheme,SystemUser user){
		Map<String,Object> result = new HashMap<String,Object>();
		//
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("flowdraftid", flowdraftid);
		//
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramap);
		paramap.put("applyid", fb.getApplyid());
		paramap.put("applyname", fb.getApplyname());
		//
		paramap.put("tempid", signatureScheme.getTempid());
		paramap.put("flowdefid", signatureScheme.getFlowdefid());
		paramap.put("solutionid", signatureScheme.getSolutionid());
		paramap.put("signtype", signatureScheme.getSigntype());//打印材料（如监狱减刑审核表）
		paramap.put("signname", signatureScheme.getName());
		result.putAll(paramap);
		//
		Map<String,Object> resultMap = solutionService.query(paramap, user);
		
		JSONArray docconent = commonFormService.parseFormDataOfSolution(resultMap);
		
//		if(docconent.size()>1){
//			result.put("ismultipage", "0");
//		}
		result.put("aipFileStr", docconent);
		
		Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
		if(null!=map){
			result.put("formdata", new JSONObject(map));
		}
		
		return result;
		
	}
	
}
