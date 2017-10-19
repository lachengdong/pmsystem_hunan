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

import com.alibaba.fastjson.JSON;
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


/***
 * 监外执行-监狱文书-病残鉴定表
 * @author liyufeng
 *
 */
@Controller
public class IdentificationOfDisabledController extends ControllerBase{
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
	 * 跳转病残鉴定表罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/identificationOfDisabledChoose")
	public ModelAndView identificationOfDisabledCriminalChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/identificationOfDisabledChoose");
	}
//	/***
//	 * 获得列表页面数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getidentificationOfDisabledChooseList")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("tempid", tempid);
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
//		
//		return resultmap;
//	}
	/***
	 * 跳转到病残鉴定表表单页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/showidentificationOfDisabled")
	public ModelAndView showidentificationOfDisabled(HttpServletRequest request){
		returnResourceMap(request);
		String menuid = request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];//罪犯编号就从数组里取出第一个罪犯编号
		}
		request.setAttribute("crimid", crimid);
		String tempid = "ZFBCJDB"; 
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			HashMap<String, Object> map = new HashMap<String, Object>();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
	        if(info!=null){
	        	map.put("name", info.getName());
	        	map.put("xinbie", info.getGender());
	        	map.put("crimid", crimid);
	        	map.put("mingzu", info.getNation());
	        	if(info.getBirthday()!=null)map.put("csdate", DateUtil.dateFormatForAip(info.getBirthday()));
	        	map.put("whcd", info.getEducation());
	        	map.put("jtzz", info.getFamilyaddress());
	        }
	        if(crime!=null){
	        	map.put("zuiming", crime.getCauseaction());
	        	map.put("xz", crime.getPunishmenttype());
	        	map.put("xq", crime.getRemark());
	        	map.put("fj", crime.getSanremark());
	        }
	        request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("menuid", menuid);
		return new ModelAndView("aip/showidentificationOfDisabled");
	}
	/**
	 * 修改/保存大字段
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/saveidentificationOfDisabled")
	@ResponseBody  
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		String bcqk="",bcjdyj="",yhbs="";
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		TbsysDocument document = new TbsysDocument();
		String noteinfo=request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
		if(null!=list&&list.size()>0){
			Map<String,String> map=(Map) list.get(0);
			bcqk=map.get("bcqk");
			bcjdyj=map.get("bcjdyj");
		}
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+crimid+LogCommon.BINGCANJIANDINGBIAO);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			document.setText1(bcqk+bcjdyj);
			log.setLogtype(LogCommon.BINGCANJIANDINGBIAO+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BINGCANJIANDINGBIAO+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BINGCANJIANDINGBIAO+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			document.setText1(bcqk+bcjdyj);
			log.setLogtype(LogCommon.BINGCANJIANDINGBIAO+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BINGCANJIANDINGBIAO+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BINGCANJIANDINGBIAO+LogCommon.EDIT+LogCommon.EVENT);
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
