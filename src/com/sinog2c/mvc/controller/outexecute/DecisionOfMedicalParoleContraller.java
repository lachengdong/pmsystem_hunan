package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
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
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/***
 * 监外执行-省局文书-保外就医决定书
 * @author liyufeng
 *
 */
@Controller
public class DecisionOfMedicalParoleContraller extends ControllerBase{
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
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	
	/**
	 * 跳转保外就医决定书罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/decisionOfMedicalParoleChoose")
	public ModelAndView decisionOfMedicalParoleCriminalChoose(HttpServletRequest request){
		returnResourceMap(request);
		String provincecode = null;
    	Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		if(jypro!=null){
			provincecode = jypro.getProperty("provincecode");
		}
		if(GkzxCommon.PROVINCE_SHANXI.equals(provincecode)){
			return new ModelAndView("chooseCriminal/decisionOfBWJYChoose");
		}else{
			return new ModelAndView("chooseCriminal/decisionOfMedicalParoleChoose");
		}
	}
//	/***
//	 * 获得列表页面数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getdecisionOfMedicalParoleChooseList")
//	@ResponseBody
//	public Object getdecisionOfMedicalParoleChooseList(HttpServletRequest request){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String condition = prisonerService.getTheQueryCondition("12284");
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
//			//map.put("departId", getLoginUser(request).getOrgid());
//			map.put("condition", condition);
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
	/***
	 * 跳转到保外就医决定书表单页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/showdecisionOfMedicalParole")
	public ModelAndView showdecisionOfMedicalParole(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];//罪犯编号就从数组里取出第一个罪犯编号
		}
		request.setAttribute("crimid", crimid);
		String tempid = "TJBWJYJDS";
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			HashMap<String, Object> map = new HashMap<String, Object>();
			String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			String xuhao = tbsysDocumentService.getXuHao(year, tempid,null, departid);//获取序号
	        if(info!=null){
	          map.put("name", info.getName());
	          map.put("name1", info.getName());
	          map.put("gender", info.getGender());
	          Date date = new Date();
	          map.put("opdate", DateUtil.dateFormatForAip(date));
	          map.put("newdate", DateUtil.dateFormatForAip(date));
	          map.put("address", info.getFamilyaddress());
	        }
	        if(crime!=null){
	        	map.put("zuiming", crime.getCauseaction());
	        	map.put("yuanpanxingqi", crime.getRemark());
	        	if(crime.getSentencestime()!=null){
	        	   map.put("xqqr", DateUtil.dateFormatForAip(crime.getSentencestime()));
	        	}
	        	if(crime.getSentenceetime()!=null){
	        	   map.put("xqzr", DateUtil.dateFormatForAip(crime.getSentenceetime()));
	        	}
	        	 map.put("jianyu", crime.getOrgname1());
	        }
			map.put("year",year);
			map.put("xuhao", xuhao);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		
		return new ModelAndView("aip/showdecisionOfMedicalParole");
	}
	/**
	 * 修改/保存大字段
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/savedecisionOfMedicalParole")
	@ResponseBody  
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			String crimid = request.getParameter("crimid");
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(crimid+LogCommon.TJBAOWAIJIUYIJUEDINGSHU);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.TJBAOWAIJIUYIJUEDINGSHU+LogCommon.ADD);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.TJBAOWAIJIUYIJUEDINGSHU+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.TJBAOWAIJIUYIJUEDINGSHU+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.TJBAOWAIJIUYIJUEDINGSHU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.TJBAOWAIJIUYIJUEDINGSHU+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.TJBAOWAIJIUYIJUEDINGSHU+LogCommon.EDIT+LogCommon.EVENT);
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
	 * 此方法描述的是：跳转决定书模板页面
	 * @param   name  
	 * @Exception 异常对象
	 * @author: 张广良   
	 * @version: Apr 1, 2015 11:26:56 PM
	 */
	@RequestMapping("/toDecisionPage.page")
	public ModelAndView toDecisionPage(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		String menuid = request.getParameter("menuid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid", menuid);
		return new ModelAndView("penaltyPerform/decisionPage_sx");
	}
	/**
	 * 此方法描述的是：通过系统模板ID获取相关内容，并返回页面
	 * @param   name  
	 * @Exception 异常对象
	 * @author: 张广良   
	 * @version: Apr 1, 2015 11:12:57 PM
	 */
	@RequestMapping(value = "/getDecisionInfo.json")
	@ResponseBody
	public Object getDecisionInfo(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		String tempid = request.getParameter("tempid");
		SystemUser user = getLoginUser(request);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (null != template) {
			String findid = String.valueOf(template.getFindid());
			String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
			infosql = systemResourceService.whereSql(user, infosql, request);
			String result = template.getContent();
			if (StringNumberUtil.notEmpty(infosql)) {
				infosql = infosql.replace("CHR(10)", "\r\n");
				List<Map<String, Object>> list = systemModelService.getDocumentContentList(infosql);
				if (null != list && list.size() > 0) {
					result = MapUtil.replaceBracketContent(result, list.get(0));
					result = result.replaceAll("\"", "＂");// 把双引号替换成全角的双引号
				}
			}
			map.put("annexcontent", result);
		}
		map.put("tempid", tempid);
		return map;
	}
}
