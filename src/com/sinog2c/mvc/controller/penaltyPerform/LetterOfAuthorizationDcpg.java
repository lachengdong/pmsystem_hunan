package com.sinog2c.mvc.controller.penaltyPerform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 调查评估委托函（假释）
 * @author liuchaoyang
 * 下午06:44:12
 */
@Controller
@RequestMapping("/letterDcpg")
public class LetterOfAuthorizationDcpg extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private PrisonerService prisonerService;
	
	/**
	 * 罪犯选择页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/letterOfAuthorizationDcpg.page")
	public ModelAndView letterOfAuthorizationDcpg(HttpServletRequest request) throws Exception {
		return new ModelAndView("chooseCriminal/letterOfAuthorizationDcpgChoose");
	}
//	/**
//	 * 获取所有罪犯列表
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getTheMedicalParoleApprovalChooseChoose.json")
//	@ResponseBody
//	public Object getTheMedicalParoleApprovalChooseChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");//取得模板编号
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));     
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("tempid", tempid);
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 方法描述：新增数据
	 */
	@RequestMapping("/letterOfAuthorization.page")
	public ModelAndView letterOfAuthorization(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据根据登录用户去获取对应部门id
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);//基本信息
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);//处罚信息
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
			Map<String, Object> map=new HashMap<String, Object>();
			
			map.put("name",baseInfo.getName());
			map.put("gender",baseInfo.getGender());
			map.put("idnumber",baseInfo.getIdnumber());
			map.put("fujidi", baseInfo.getOrigin());
			map.put("juzhudi", baseInfo.getFamilyaddress());
			map.put("birthday",DateUtil.dateFormatForAip(baseInfo.getBirthday()));
			if(!StringNumberUtil.isNullOrEmpty(baseCrime))
			map.put("anyou",baseCrime.getMaincase());
			map.put("depname",user.getOrganization().getName());
			map.put("depusername",user.getOrganization().getExtcontacts());
			map.put("depphone",user.getOrganization().getExttel());
			map.put("depfax",user.getOrganization().getFax());
			request.setAttribute("crimid", crimid);
			request.setAttribute("formDatajson", new JSONObject(map).toString());//转json
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addLetterOfAuthorizationDcpg");
	}
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveLetterOfAuthorizationDcpg.json")
	@ResponseBody
	public int saveLetterOfAuthorizationDcpg(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String departid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(departid);
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(template.getTempname());
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WUPINBAOGUAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.PINGGUDIAOCHAWEITUOHAN+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.PINGGUDIAOCHAWEITUOHAN+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.PINGGUDIAOCHAWEITUOHAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.PINGGUDIAOCHAWEITUOHAN+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.PINGGUDIAOCHAWEITUOHAN+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}

}
