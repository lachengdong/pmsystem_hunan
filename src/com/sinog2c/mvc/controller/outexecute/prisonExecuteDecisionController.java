package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
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
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 功能描述：暂予监外执行决定书
 * @author liuchaoyang
 * 下午08:30:02
 */
@Controller
public class prisonExecuteDecisionController extends ControllerBase{
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
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	
	//打开罪犯处理列表页
	@RequestMapping("/prisonExecuteDecisionNotifyChoose")
	public ModelAndView prisonExecuteDecisionNotifyChoose(){
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/chooseCriminal/prisonExecuteDecisionNotifyChoose.jsp"));
	}
//	 //获取罪犯列表
//	@RequestMapping("/getprisonExecuteDecisionNotifyChoose")
//	@ResponseBody
//	public Object getprisonExecuteDecisionNotifyChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String condition = prisonerService.getTheQueryCondition("10158");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			String tempid = "JWZX_ZYJWZXJDS";
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("tempid", tempid);
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
	/**
	 * 方法描述：打开暂予监外执行决定书表单
	 * 
	 * @author liuchaoyang 2014-7-29 20:11:45
	 */
	@RequestMapping("/prisonExecuteDecisionNotify")
	public ModelAndView prisonExecuteDecisionNotify(HttpServletRequest request){
		String crimid = request.getParameter("crimid");//罪犯编号
		//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为罪犯编号
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		String tempid = "JWZX_ZYJWZXJDS";
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		JSONArray docconent = new JSONArray();
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if(document!=null && !"".equals(document)){
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			HashMap<String, Object> map = new HashMap<String, Object>();
			SystemUser user = getLoginUser(request);//获取当前登录的用户
			String departid=user.getDepartid();//根据用户Id获取所在部门Id
			String year = new SimpleDateFormat("yyyy").format(new Date());
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, null);
			TbxfSentenceAlteration tbxfSentenceAlteration =tbxfSentencealterationService.selectByPrimaryKey(crimid);
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);			
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
			map.put("text1", year);
			map.put("text2", xuhao);
			map.put("szjy", org.getName());
			map.put("jianyu1", org.getName());
			map.put("cbiname", info.getName());
			map.put("minzu", info.getNation());
			map.put("cbigendername", info.getGender());
			map.put("age", DateUtil.getAge(info.getBirthday()));
			map.put("cbibirthday", DateUtil.dateFormatForAip(info.getBirthday()));
			map.put("cbihomeaddress", info.getFamilyaddress());
			map.put("anyouhuizong", crime.getCauseaction());
			map.put("cjijudgedate", DateUtil.dateFormatForAip(crime.getInprisondate()));
			map.put("cjicourtname", crime.getJudgmentname());
			map.put("zhuxing", crime.getRemark());
			map.put("fujiaxing", crime.getSanremark());
			map.put("cjienddate", DateUtil.dateFormatForAip(crime.getSentenceetime()));
			map.put("text8", DateUtil.dateFormatForAip(new Date()));
			String xxqqr = DateUtil.dateFormat(tbxfSentenceAlteration.getCourtchangefrom(), "yyyy.MM.dd");
			String xxqzr  ="";
			String xxqqz = "";
			xxqqz = xxqqr;
			if(tbxfSentenceAlteration.getCourtchangefrom()!=null){
				xxqzr = DateUtil.dateFormat(tbxfSentenceAlteration.getCourtchangeto(), "yyyy.MM.dd");
				xxqqz = ("自"+xxqqr+"至"+xxqzr+"止");
			}
			map.put("xianxingqiqizhi", xxqqz);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/addPrisonExecuteDecisionNotify");
	}
	/**
	 * 新增、修改时将暂予监外执行决定书保存到大字段
	 * @author liuchaoyang
	 * 2014-7-29 15:37:45
	 */
	@RequestMapping(value = "/savePrisonExecuteDecisionNotify")
	@ResponseBody
	public int savePrisonExecuteDecisionNotify(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		String introduction = "";
		SystemLog log = new SystemLog();
		
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			Map<String,String> map = new HashMap();
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map)list.get(0);
				introduction = map.get("cbiname")+LogCommon.ZYJWZXJDS+map.get("text1")+"年第"+map.get("text2")+"号";
			}
		}
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXJDS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZYJWZXJDS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXJDS+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else{
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXJDS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZYJWZXJDS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXJDS+LogCommon.EDIT+LogCommon.EVENT);
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
