
package com.sinog2c.mvc.controller.penaltyPerform;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

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

/**
 * @author kexz
 *执行期满考察
 * 2014-7-17
 */
@Controller
public class Completion extends ControllerBase{
	

	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private SystemLogService logService;
	@RequestMapping("/completionChoose")
	public  ModelAndView completionChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/chooseCriminal/completionChoose.jsp"));
	}
	@RequestMapping("/completion")
	public ModelAndView completion(HttpServletRequest request){
		String tempid=request.getParameter("tempid");
		String docid =request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		String menuid =request.getParameter("menuid");
		try {
			name=new String(name.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("tempid",tempid);
		request.setAttribute("docid", docid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/completion.jsp"));
	}
//	/*
//	 * 获取罪犯信息并排序
//	 */
//	@RequestMapping("/getCompletion")
//	@ResponseBody
//	public Object getbasicInfoPage(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String tempid=request.getParameter("tempid")==null?"":request.getParameter("tempid");
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
//			map.put("tempid",tempid);
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//			
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
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
	
	@RequestMapping("/completionAdd")
	public ModelAndView completionAdd(HttpServletRequest request){
		returnResourceMap(request);
		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		JSONArray docconent = new JSONArray();
		String docid =request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String menuid =request.getParameter("menuid");
		//根据罪犯id获取基本信息
		TbprisonerBaseinfo baseinfo =prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);

		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		// 通过部门id去找所在单位名称
		SystemOrganization org = systemOrganizationService.getByOrganizationId(departid);
		Map<String,Object> map=new HashMap<String,Object>();
		if(docid!=null){
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid,
					null, null,null);
			docconent.add(document.getContent());
		}else{
			if(baseinfo!=null){
				if(baseinfo.getGender()!=null){
				map.put("cbigendername", baseinfo.getGender());
				}
				if(baseinfo.getBirthday()!=null){
				map.put("cbibirthday", sdf.format(baseinfo.getBirthday()));
				}
			}
			if(baseCrime!=null){
				String yuan=baseCrime.getRemark();
				map.put("zhuxing", yuan);
				map.put("anyouhuizong", baseCrime.getCauseaction());
				
			}
			
			map.put("cbiname",baseinfo.getName());
			map.put("text1", org.getName());
			
			
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("docid", docid);
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("formcontent", docconent.toString());
		
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/completionAdd.jsp"));
	}
	/**
	 * 修改/保存大字段
	 * @author liyufeng
	 */
	@RequestMapping(value = "/saveCompletion")
	@ResponseBody
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		String introduction=LogCommon.ZHIXINGQIMANKAOCHA;
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
			document.setIntroduction("编号"+crimid+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.EDIT+LogCommon.EVENT);
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
	@RequestMapping(value = "/getCompletionTableList")
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
	 * 根据文书Id删除数据
	 * @author liyufeng
	 */
	@RequestMapping(value = "/deleteCompletion")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ZHIXINGQIMANKAOCHA+LogCommon.DEL+LogCommon.EVENT);
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
