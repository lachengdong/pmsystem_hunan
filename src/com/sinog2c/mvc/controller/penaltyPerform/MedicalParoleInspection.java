package com.sinog2c.mvc.controller.penaltyPerform;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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

/**
 * @author kexz
 *保外就医考察
 * 2014-7-17
 */
@Controller
public class MedicalParoleInspection extends ControllerBase{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	/**
	 * 跳转保外就医考察罪犯列表页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="/medicalParoleInspection")
	public ModelAndView medicalParoleInspection(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("chooseCriminal/medicalParoleInspectionChoose");
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
//	@RequestMapping(value = "/getMedicalParoleInspectionBasicInfoList")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String tempid=request.getParameter("tempid");
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
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	map.put("tempid", tempid);
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
	/**
	 * 跳转保外就医考察页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/medicalParoleInspectionList")
	public ModelAndView toMedicalParoleInspection(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("penaltyPerform/medicalParoleInspection");
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		String tempid = "BWJY_JYTQZYJWZXKC";
		try {
			name=new String(name.getBytes("UTF-8"),"UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("tempid", tempid);
		return mav;
	}
	/**
	 * 根据罪犯编号获取保外就医考察列表
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/getMedicalParoleInspectionByCrimid")
	@ResponseBody
	public HashMap<String, Object> getMedicalParoleInspectionByCrimid(HttpServletRequest request) {
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
	 * 跳转保外就医考察新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/medicalParoleInspectionAdd")
	public ModelAndView toMedicalParoleInspectionAdd(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("aip/medicalParoleInspectionAdd");
		returnResourceMap(request);
		String docid =request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		JSONArray docconent = new JSONArray();
		
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime =  prisonerService.getCrimeByCrimid(crimid);
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日");
		
		map.put("text1", org.getName() + "提请罪犯暂予监外执行考察表");
		//姓名
		map.put("cbiname", baseinfo.getName());
		//性别
		map.put("cbigendername", baseinfo.getGender());
		//性别
		map.put("cbinationname", baseinfo.getNation());
		if(docid!=null){
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid,
					null, null,null);
			docconent.add(document.getContent());
		}
		//出生日期
		if(baseinfo.getBirthday()!=null){
			String dateString = formatter.format(baseinfo.getBirthday());
			map.put("cbibirthdaystring", dateString);
		}
		//家庭住址
		map.put("cbihomeaddress", baseinfo.getFamilyaddress());
		if(baseCrime != null){
			//罪名
			map.put("anyouhuizong", baseCrime.getCauseaction());
			//原判刑罚
			map.put("zhuxing",  baseCrime.getRemark());
			//刑期起止
			Date startTime = baseCrime.getSentencestime();
			Date endTime = baseCrime.getSentenceetime();
			if(startTime!=null&&endTime!=null){
				String xingqiqizhi = "自" + formatter2.format(startTime) + "起至" + formatter2.format(endTime) + "止";
				map.put("xingqiqizhi", xingqiqizhi);//?哪张表,应该是最后一次变更后的
			}
			//刑期变动
//			map.put("nowcjibegindate", ""); //刑期变动表
//			map.put("nowcjienddate", ""); //刑期变动表
			//原判法院	
			map.put("cjicourtname", baseCrime.getJudgmentname()+ baseCrime.getJudgmentshort());//有问题
			
		}
		
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		String applyvalue = crimid + "," + baseinfo.getName();
		request.setAttribute("docid", docid);
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("orgid",baseCrime.getOrgid1());
		request.setAttribute("applyvalue",applyvalue);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		
		return mav;
	}
	/**
	 * 查看、修改保外就医考察时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/medicalParoleInspectionLook")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav =  new ModelAndView("aip/medicalParoleInspectionAdd");
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null,null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return mav;
	}
	
	@RequestMapping(value = "/saveMedicalParoleInspection")
	@ResponseBody
	public int saveMedicalParoleInspection(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String introduction =LogCommon.CJGL_BWJYSCB;
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
			log.setOpcontent(introduction+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.ADD);
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
			log.setRemark(introduction+LogCommon.EDIT);
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
//删除	
	@RequestMapping(value = "/medicalParoleInspectionDelete")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		SystemUser user = getLoginUser(request);
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.CJGL_BWJYSCB+LogCommon.DEL);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.CJGL_BWJYSCB+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.CJGL_BWJYSCB+LogCommon.DEL+LogCommon.EVENT);
		
		return result;
	}
}
