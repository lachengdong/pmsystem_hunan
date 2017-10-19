package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.text.SimpleDateFormat;
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
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 *物品保管
 * 2014-7-17
 */
@Controller
@RequestMapping("/deposit")
public class DepositController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	
	/**
	 * 罪犯处理页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/depositChoose.page")
	public ModelAndView toDepositPage(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/depositChoose");
	}
//	/**
//	 * 获取所有罪犯列表
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getDepositChoose")
//	@ResponseBody
//	public Object getDepositChoose(HttpServletRequest request) throws Exception {
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
	 * @author kexz
	 * 处理、查看、修改物品保管跳转页面并显示表单
	 * 2014-7-29
	 */
	@RequestMapping("/depositAdd.page")
	public ModelAndView depositAdd(HttpServletRequest request){
		returnResourceMap(request);
		String id = request.getParameter("id");
		String indexFlagStr = request.getParameter("indexFlag");
		int indexFlag = StringNumberUtil.notEmpty(indexFlagStr) ? Integer.parseInt(indexFlagStr) : 0;
		request.setAttribute("indexFlag", indexFlag);
		
		request.setAttribute("id", id);
		String[] ids = id.split(",");
		String crimid = ids[indexFlag];
		
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据根据登录用户去获取对应部门id
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String year = sdf.format(new Date());
			String xuhao = tbsysDocumentService.getXuHao(year, tempid, null,departid);//获取序号
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("sdcourtcheck",org.getFullname());
			request.setAttribute("crimid", crimid);
			map.put("departid",org.getName()+template.getTempname());//拼接部门
			map.put("criminalid",crimid);
			map.put("text1",DateUtil.dateFormatForAip(new Date()));
			map.put("year",year);
			map.put("year2",year);
			map.put("xuhao",xuhao);
			map.put("xuhao2",xuhao);
			map.put("text144","保管");
			map.put("text145","保管");
			request.setAttribute("year", year);
			request.setAttribute("formDatajson", new JSONObject(map).toString());//转json
		}
		request.setAttribute("formcontent", docconent.toString());
		
		return new ModelAndView("penaltyPerform/inPrisonManagement/depositAdd");
	}
	
	
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveDeposit")
	@ResponseBody
	public int saveDeposit(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String year = request.getParameter("year")==null?"":request.getParameter("year");
		if(StringNumberUtil.isEmpty(year)){
			year = new SimpleDateFormat("yyyy").format(date);
		}
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String departid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(departid);
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(year);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			document.setText1(template.getTempname());
			
			log.setLogtype(LogCommon.WUPINBAOGUAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.WUPINBAOGUAN+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WUPINBAOGUAN+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WUPINBAOGUAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.WUPINBAOGUAN+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WUPINBAOGUAN+LogCommon.EDIT);
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
