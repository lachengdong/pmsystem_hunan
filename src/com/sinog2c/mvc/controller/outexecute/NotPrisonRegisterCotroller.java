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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSON;
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
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 不记入刑期登记
 * @author wangxf
 *
 */
@Controller
public class NotPrisonRegisterCotroller extends ControllerBase{
	@Autowired
	private PrisonerService prisonerService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	
	
	//跳转到罪犯处理页面
	@RequestMapping(value="notPrisonRegisterchoose")
	public ModelAndView notPrisonRegisterchoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/chooseCriminal/notPrisonRegisterchoose.jsp"));
	}
//	/**
//	 *方法描述：获取罪犯列表数据 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getNotPrisonRegisterChoose")
//	@ResponseBody
//	public Object getNotPrisonRegisterChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String tempid = "GDSJ_JWZX_BJRXQ";
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			String condition = prisonerService.getTheQueryCondition("10155");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
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
	
	//表单新增页面
	@RequestMapping(value = "notprisonregisterAdd")
	public ModelAndView notPrisonRegisterAdd(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		String tempid = "GDSJ_JWZX_BJRXQ";
		JSONArray docconent = new JSONArray();
		TbsysDocument document= tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if(document!=null){
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			SystemUser user = getLoginUser(request);// 获取当前登录的用户
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getUserid());
			if(template!=null){
				docconent.add(template.getContent());
			}
			TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);;
			SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
			String year = new SimpleDateFormat("yyyy").format(new Date());
			String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, user.getDepartid());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text1", xuhao);
			map.put("text2", DateUtil.dateFormatForAip(new Date()));
			map.put("szjy", org.getName());
			map.put("cbiname", info.getName());
			map.put("huji", info.getOrigin());
			map.put("age", DateUtil.getAge(info.getBirthday()));
			map.put("cbihomeaddress", info.getFamilyaddress());
			if(baseCrime!=null ){
				map.put("zhuxing", baseCrime.getMaincase());
				map.put("zuiming", baseCrime.getCharge());
				
				map.put("xqqr", DateUtil.dateFormatForAip(baseCrime.getSentencestime()));
				map.put("xxqzr",DateUtil.dateFormatForAip( baseCrime.getSentenceetime()));
			}
			Map<String, Object> tmap=tbxfSentencealterationService.selectTbxfByCrimid(crimid);
			if(tmap!=null){
				map.put("lcjxmx", tmap.get("SENTENCECHAGEINFO"));
			}
			map.put("bwqk", "");
			map.put("tgqk", "");
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		returnResourceMap(request);	
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("formcontent", docconent.toString());		
		return new ModelAndView(new InternalResourceView(
				"/WEB-INF/JSP/aip/notPrisonRegisterAdd.jsp"));
	}
	
	/**
	 * 新增、修改时将不记入刑期登记保存到大字段
	 * @author 
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/saveNotPrisonRegister")
	@ResponseBody
	public int saveNotPrisonRegister(HttpServletRequest request){
		
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String introduction = "";
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map<String, String>)list.get(0);
				introduction = map.get("cbiname")+LogCommon.BUJIRUXINGQIDIAODONG+map.get("text2");
			}
		}
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BUJIRUXINGQIDIAODONG+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BUJIRUXINGQIDIAODONG+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BUJIRUXINGQIDIAODONG+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else{
			document.setDocid(Integer.parseInt(docid));
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BUJIRUXINGQIDIAODONG+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BUJIRUXINGQIDIAODONG+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BUJIRUXINGQIDIAODONG+LogCommon.EDIT);
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
