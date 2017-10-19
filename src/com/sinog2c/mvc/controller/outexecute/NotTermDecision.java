package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * 不计入刑期决定书
 * @author hzl
 *
 */
@Controller
public class NotTermDecision extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private  ChooseCriminalService  chooseCriminalService;
	
    @RequestMapping("/nottermdecisionChoose")
	public ModelAndView chooseCriminal(HttpServletRequest request){
    	request.setAttribute("menuid", request.getParameter("menuid"));
	  return new ModelAndView("chooseCriminal/nottermdecisionChoose");
    }
    
//	@RequestMapping("/getPrisonersList")
//	@ResponseBody
//	public Object getPrisonersList(HttpServletRequest request) throws Exception {
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
//		
//		return resultmap;
//	}
	
	/**
	 * 方法描述：跳转到列表页
	 * 
	 * @author
	 */
	@RequestMapping("/tonottermdecisionDoc")
	public ModelAndView inPrisonNotice(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
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
		returnResourceMap(request);
		return new ModelAndView("outexecute/nottermdecisionDocList");
	}
	
	/**
	 * 方法描述：列表页面获取数据
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/getnottermdecisionDocList")
	@ResponseBody
	public HashMap<String, Object> getnottermdecisionDocList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String departid =getLoginUser(request).getDepartid();
		String key = request.getParameter("key");
		String sortField = request.getParameter("optime");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid, departid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, departid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 修改/保存大字段
	 * @author 
	 */
	@RequestMapping(value = "/savenottermdecision")
	@ResponseBody
	public int savenottermdecision(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		Date date = new Date(); 
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		SystemLog log = new SystemLog();
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			String tempid = "SDXF_BWJYQJBJRXQJDS";
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
			String crimid = request.getParameter("crimid");
			String introduction = year+GkzxCommon.YEAR+template.getTempname();//文书简介
			TbprisonerBaseinfo baseinfo=prisonerService.getBasicInfoByCrimid(crimid); 
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(baseinfo.getName()+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BJRXQJDS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BJRXQJDS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BJRXQJDS+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BJRXQJDS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BJRXQJDS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BJRXQJDS+LogCommon.EDIT+LogCommon.EVENT);

			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		return countnum;
	}
	
	/**
	 * 新增页面 
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/shownottermdecisionForm")
	public ModelAndView shownottermdecisionForm(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime =prisonerService.getCrimeByCrimid(crimid);
		String tempid = request.getParameter("tempid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String xuhao = tbsysDocumentService.getXuHao(year+"", tempid, crimid, deptid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("text1", user.getPrison().getFullname());
		map.put("text5", user.getPrisonShortName());
		map.put("text11",year);
		map.put("anjianhao",xuhao);
		map.put("cbiname",tbprisonerBaseinfo.getName());
		map.put("cbigender",tbprisonerBaseinfo.getGender());
		map.put("cbibirthday",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
		map.put("text20",tbprisonerBaseinfo.getNation());
		map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());	
		if(tbprisonerBaseCrime!=null){
			map.put("anyouhuizong",tbprisonerBaseCrime.getMaincase());
			map.put("nowcjibegindate",DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime()));
			map.put("nowcjienddate",DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));
			map.put("shoujianyuanyin",DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));
		}
		map.put("crimid", crimid);
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/shownottermdecisionForm");
	}
	
	/**
	 * 查看、修改不计入刑期决定书时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/editorlooknottermdecision")
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
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/shownottermdecisionForm");
	}
	/**
	 * 根据文书Id删除数据
	 * @author 
	 */
	@RequestMapping(value = "/deletenottermdecision")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String introduction=LogCommon.BJRXQJDS;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(introduction+LogCommon.DEL);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(introduction+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(introduction+LogCommon.DEL+LogCommon.EVENT);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		log.setLogtype("不计入刑期决定书");
		return result;
	}	
	
}
