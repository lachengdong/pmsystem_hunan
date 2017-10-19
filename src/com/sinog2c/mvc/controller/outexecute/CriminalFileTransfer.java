package com.sinog2c.mvc.controller.outexecute;

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
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
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
import com.sinog2c.util.common.DateUtil;


/**
 * 罪犯暂予监外执行档案转递函
 *
 */
@Controller
public class CriminalFileTransfer extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	
	
	/**
	 *跳转 点击菜单中选项 （上海）罪犯暂予监外执行档案转递函   跳转选择罪犯页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="criminalFileTransfer")
	public ModelAndView criminalFileTransfer(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/criminalFileTransfer");
	}
	
//	/**
//	 * 获取罪犯信息列表（上海暂予监外执行档案转递函）
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/getCriminalFileTransferChoose")
//	@ResponseBody
//	public Object getCriminalFileTransferChoose(HttpServletRequest request) {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			//String condition = prisonerService.getTheQueryCondition("10150");
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
//			//map.put("departId", getLoginUser(request).getOrgid());
//			//map.put("condition", condition);
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			map.put("start", String.valueOf(start));
//			map.put("end",String.valueOf(end));
//			int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//			data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//			resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 点击处理后跳转另一个页面（上海暂予监外执行档案转递函）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="criminalFileTransferToProcess")
	public ModelAndView criminalFileTransferToProcess(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid=request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String idname=request.getParameter("idname");
		String id=request.getParameter("id");
		String menuid=request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("outexecute/criminalFileTransferToProcess");
	}
	
	@RequestMapping(value = "/getCriminalFileTransferTableList")
	@ResponseBody
	public HashMap<String, Object> getTbsysDocumentList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String deptid = getLoginUser(request).getDepartid();
		String key = request.getParameter("key");
		String sortField = request.getParameter("optime");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid, deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, crimid,deptid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 新增页面 
	 * 
	 * @author liyufeng
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addCriminalFileTransferForm")
	public ModelAndView showAddForm(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String crimid = request.getParameter("crimid");
		String menuid = request.getParameter("menuid");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime= prisonerService.getCrimeByCrimid(crimid);
		String tempid = "JWZX_ZFDAZDH";
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(tbprisonerBaseinfo!=null){
			map.put("cbiname",tbprisonerBaseinfo.getName());//姓名
			map.put("xingbie",tbprisonerBaseinfo.getGender());//性别
			map.put("cbibirthday",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
		}
		if (tbprisonerBaseCrime != null){
			map.put("zuiming", tbprisonerBaseCrime.getCauseaction());//罪名
			map.put("zhuxing",tbprisonerBaseCrime.getPunishmenttype());
			if(tbprisonerBaseCrime.getSentencestime()!=null&&tbprisonerBaseCrime.getSentenceetime()!=null){
			map.put("yuanpanxingqiqizhi", sdf.format(tbprisonerBaseCrime.getSentencestime())+"至"+sdf.format(tbprisonerBaseCrime.getSentenceetime()));// 刑期起止日
			}
		}
		map.put("crimid", crimid);
		map.put("text13", user.getOrganization().getName());
		map.put("text16", tbprisonerBaseinfo.getName());
		String neirong = systemModelService.getRecommendationContent("9996000", user, request);
		map.put("content",neirong);
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("menuid",menuid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		returnResourceMap(request);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addCriminalFileTransfer");
	}
	/**
	 * 修改/保存大字段
	 * @author liyufeng
	 */
	@RequestMapping(value = "/saveCriminalFileTransfer")
	@ResponseBody
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			String crimid = request.getParameter("crimid");
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(LogCommon.ZUIFANDANGANZHUANDIHAN);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.EDIT);
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
	/**
	 * 根据文书Id删除数据
	 * @author liyufeng
	 */
	@RequestMapping(value = "/deleteCriminalFileTransfer")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ZUIFANDANGANZHUANDIHAN+LogCommon.DEL);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
	/**
	 * 查看、修改暂予监外执行保证书时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/editorlookCriminalFileTransfer")
	public ModelAndView editorlook(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String docid = request.getParameter("docid");
		request.setAttribute("menuid", menuid);
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		returnResourceMap(request);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addCriminalFileTransfer");
	}
}
