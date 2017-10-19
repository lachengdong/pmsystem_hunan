package com.sinog2c.mvc.controller.outexecute;

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
import com.gkzx.common.GkzxCommon;
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
 * 
 * 暂予监外执行罪犯移交证明书
 * @author hzl
 *
 */
@Controller
public class ZYJWZXZFYJZMSController extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	
	@RequestMapping("/toZyjwzxzfyjzmsChoosePage")
	public ModelAndView choosePage(HttpServletRequest request) throws Exception {
			request.setAttribute("menuid", request.getParameter("menuid"));
			return new ModelAndView("chooseCriminal/zyjwzxzfyjzmsChoose");
	}
	
	@RequestMapping("/toZyjwzxzfyjzmsListPage")
	public ModelAndView toZyjwzxzfyjzmsListPage(HttpServletRequest request) throws Exception {
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid=request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String idname=request.getParameter("idname");
		String id=request.getParameter("id");
		String menuid=request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("outexecute/zyjwzxzfyjzmsListPage");
	}
	
	
//	@RequestMapping("/getZyjwzxzfyjzmsChoose")
//	@ResponseBody
//	public Object getZyjwzxzfyjzmsPage(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 显示表单
	 */
	@RequestMapping("/zyjwzxzfyjzmsAdd")
	public ModelAndView zyjwzxzfyjzmsAdd(HttpServletRequest request, HttpServletResponse response) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(!StringNumberUtil.isNullOrEmpty(docid)){
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null,null);
			if (document != null) {
				docconent.add(document.getContent());
			}
			request.setAttribute("docid", docid);
		}else{
			SystemUser user = getLoginUser(request);
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
			TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map<String,Object> map = new HashMap<String,Object>();
			if(tbprisonerBaseinfo!=null){
				map.put("cbiname",tbprisonerBaseinfo.getName());
				map.put("xingbie",tbprisonerBaseinfo.getGender());
				map.put("cbibirthday",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
				map.put("minzu",tbprisonerBaseinfo.getNation());
				map.put("jiatingzhuzhi",tbprisonerBaseinfo.getFamilyaddress());
			}
			if(tbprisonerBaseCrime!=null){
				map.put("zuiming", tbprisonerBaseCrime.getCauseaction());// 罪名
				map.put("zhuxing", tbprisonerBaseCrime.getPunishmenttype());// 终审判决刑种GB022
				map.put("zhuxing1", tbprisonerBaseCrime.getRemark());// 终审判决刑种GB022
				String fujiaxing = tbprisonerBaseCrime.getLosepoweryear()+","+tbprisonerBaseCrime.getLosepowermonth()+","+tbprisonerBaseCrime.getLosepowereday();
				fujiaxing = StringNumberUtil.getBoquan(fujiaxing);
				if(!StringNumberUtil.isNullOrEmpty(fujiaxing)){
					fujiaxing = GkzxCommon.LOSEPOWER_BDZZ+fujiaxing;
				}
				String forfeit = tbprisonerBaseCrime.getForfeit();
				if(!StringNumberUtil.isNullOrEmpty(forfeit)&&!"0".equals(forfeit)){
					fujiaxing += GkzxCommon.FAJIN+forfeit;
				}
				String xxqqr = DateUtil.dateFormat(tbprisonerBaseCrime.getSentencestime(), "yyyy.MM.dd");
				String xxqzr  ="";
				String xxqqz = "";
				xxqqz = xxqqr;
				if(tbprisonerBaseCrime.getSentenceetime()!=null){
					xxqzr = DateUtil.dateFormat(tbprisonerBaseCrime.getSentenceetime(), "yyyy.MM.dd");
					xxqqz = ("自"+xxqqr+"至"+xxqzr+"止");
				}
				map.put("xingqiqizhi", xxqqz);
				map.put("fujiaxing", tbprisonerBaseCrime.getSanremark());//判决日期
				map.put("panjueriqi", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getJudgedate()));//判决日期
				map.put("yuanpanxingqiqiri", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime()));//判决日期
				map.put("yuanpanxingqizhiri", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));//判决日期
				SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
				org = systemOrganizationService.getByOrganizationId(org.getPorgid());//根据用户所在单位的上级部门id获取监狱局名称
				map.put("text10",org.getName());
				map.put("fayuan", tbprisonerBaseCrime.getJudgmentname());//判决日期
			}
			map.put("crimid", crimid);
			request.setAttribute("crimid",crimid);
			request.setAttribute("tempid",tempid);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/ZYJWZXZFYJZMS");
	}
	
	@RequestMapping(value = "/saveZyjwzxzfyjzms")
	@ResponseBody
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator")==null?"":request.getParameter("operator");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+tbprisonerBaseinfo.getName()+LogCommon.ZYJWZXZFYJZMS);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXZFYJZMS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZYJWZXZFYJZMS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXZFYJZMS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXZFYJZMS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZYJWZXZFYJZMS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXZFYJZMS+LogCommon.EDIT);
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
	 * 获取列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getZyjwzxzfyjzmsList")
	@ResponseBody
	public HashMap<String, Object> getZyjwzxzfyjzmsList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
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
	 * 删除
	 * 
	 */
	@RequestMapping(value = "/deleteZyjwzxzfyjzms")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ZYJWZXZFYJZMS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.ZYJWZXZFYJZMS+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ZYJWZXZFYJZMS+LogCommon.DEL);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}	
}
