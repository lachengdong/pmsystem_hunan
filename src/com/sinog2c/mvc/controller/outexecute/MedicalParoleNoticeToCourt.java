package com.sinog2c.mvc.controller.outexecute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 保外就医通知书（报法院）
 * @author hzl
 * 2014-7-28 14:44:28
 */
@Controller
public class MedicalParoleNoticeToCourt extends ControllerBase{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	
	/**
	 * 跳转保外就医通知书（报法院）页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/medicalParoleNoticeToCourt")
	public ModelAndView medicalParoleNoticeToCourt(HttpServletRequest request){
		String systempid=request.getParameter("systempid");
		request.setAttribute("systempid", systempid);
		ModelAndView mav =  new ModelAndView("chooseCriminal/medicalParoleNoticeToCourtchoose");
		returnResourceMap(request);
		return mav;
	}
//	/**
//	 * 获取罪犯列表
//	 * 
//	 * @author liuxin
//	 * @param request
//	 * @return resultmap
//	 */
//	@RequestMapping(value = "/getMedicalParoleNoticeToCourtBasicInfoList")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("tempid", "SZ_BWJY_TZS");
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//			
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	/**
	 * 跳转保外就医通知书（报法院）新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/medicalParoleNoticeToCourtAdd")
	public ModelAndView toMedicalParoleNoticeToCourtAdd(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("aip/medicalParoleNoticeToCourtAdd");
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];//罪犯编号就从数组里取出第一个罪犯编号
		}
		request.setAttribute("crimid", crimid);
		String tempid = "SZ_BWJY_TZS"; 
		String systempid=request.getParameter("systempid");//系统模板ID
		if(null!=systempid&&!"".equals(systempid)){
			request.setAttribute("systempid", systempid);
		}
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			String result="";
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(null!=systempid&&!"".equals(systempid)){
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					systempid, departid); 
			if (null != template) {
				String findid = String.valueOf(template.getFindid());
				String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
				infosql = systemResourceService.whereSql(user, infosql, request);
				result = template.getContent();
				if (StringNumberUtil.notEmpty(infosql)) {
					infosql = infosql.replace("CHR(10)", "\r\n");
					List<Map<String, Object>> list = systemModelService
							.getDocumentContentList(infosql);
					if (null != list && list.size() > 0) {
						result = MapUtil.replaceBracketContent(result, list
								.get(0));
						result = result.replaceAll("\"", "＂");// 把双引号替换成全角的双引号
						result=result.replace("\r\n", "\\r\\n");
						result=result.replace("\n", "\\n");
					}
				}
			}
			}
			map.put("text1", result);
			map.put("opcnumber", "");
			TbsysTemplate template2 = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template2 != null) {
				docconent.add(template2.getContent());
			}
	        request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return mav;
	}
	/**
	 * 修改/保存大字段
	 * @author liuxin
	 */
	@RequestMapping(value = "/saveMedicalParoleNoticeToCourt")
	@ResponseBody
	public int saveMedicalParoleNoticeToCourt(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String introduction = "保外就医通知书(报法院)";//文书简介
		if("new".equals(operator)){
			String name =info.getName();
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+name+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(introduction+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(introduction+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.EDIT+LogCommon.EVENT);
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
