package com.sinog2c.mvc.controller.penaltyPerform;

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
 * @author kexz
 *不计入刑期建议书
 * 2014-7-28
 */
@Controller
public class NotIncludedSentenceProposal  extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;

	
	/**
	 * @author kexz
	 *不计入刑期建议书
	 * 2014-8-9
	 */
	@RequestMapping("/zyjwzxqjbjrxqjysChoose")
	public ModelAndView zyjwzxqjbjrxqjysChoose(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		return new ModelAndView("chooseCriminal/zyjwzxqjbjrxqjysChoose");
	}
	
	
//	/**
//	 * @author kexz
//	 *不计入刑期建议书
//	 * 2014-8-9
//	 */
//	@RequestMapping("/getZyjwzxqjbjrxqjys")
//	@ResponseBody
//	public Object getzyjw(HttpServletRequest request) throws Exception {
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
//			Map<String,Object> map = new HashMap<String,Object>();
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
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}

	
	/**
	 * @author kexz
	 *不计入刑期建议书
	 * 2014-8-9
	 */
	@RequestMapping(value="zyjwzxqjbjrxqjys")
	public ModelAndView zyjwzxqjbjrxqjys(HttpServletRequest request){
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
		return new ModelAndView("penaltyPerform/zyjwzxqjbjrxqjys");
	}
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-9
	 * @author kexz
	 */
	@RequestMapping("/getzyjwList")
	@ResponseBody
	public HashMap<String, Object> getzyjwList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		returnResourceMap(request);
		String tempid =request.getParameter("tempid");
		String key = request.getParameter("key");
		String crimid = request.getParameter("crimid");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize"))); 
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	

	
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-8-9
	 */
	@RequestMapping(value = "/savezyjwzxqjbjrxqjys")
	@ResponseBody
	public int savezyjwzxqjbjrxqjys(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		//获取当前登录的用户
		//根据根据登录用户去获取对应部门id
		String departid=user.getDepartid();
		//根据表单编号获取表单
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			String introduction = template.getTempname();//文书简介
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BJRXQJYS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BJRXQJYS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BJRXQJYS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BJRXQJYS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BJRXQJYS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BJRXQJYS+LogCommon.EDIT);
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
	 * 新增、修改、查看
	 * @author kexz
	 * 2014-8-9
	 */
	
	@RequestMapping("/zyjwzxqjbjrxqjysadd")
	public ModelAndView zyjwzxqjbjrxqjysadd(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");//大字段主键
		
		if(!StringNumberUtil.isNullOrEmpty(docid)){
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
			if (document != null) {
				docconent.add(document.getContent());
			}
			request.setAttribute("docid", docid);
		}else{
			String crimid = request.getParameter("crimid");
			String operator=request.getParameter("operator");
		
			String tempid =request.getParameter("tempid");
			SystemUser user = getLoginUser(request);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			TbprisonerBaseinfo tbinfo=prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime baseCrime =prisonerService.getCrimeByCrimid(crimid);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("cbiname",tbinfo.getName()==null?"":tbinfo.getName());
			map.put("xingbie", tbinfo.getGender());
			map.put("cbibirthday",DateUtil.dateFormatForAip(tbinfo.getBirthday()));
			map.put("minzu", tbinfo.getNation());
			map.put("jiatingzhuzhi",tbinfo.getFamilyaddress());	
			if(baseCrime!=null){
				map.put("zuiming",baseCrime.getMaincase() );
				map.put("panjueriqi",DateUtil.dateFormatForAip(baseCrime.getJudgedate()));
				String fujiaxing = baseCrime.getLosepoweryear()+","+baseCrime.getLosepowermonth()+","+baseCrime.getLosepowereday();
				fujiaxing = StringNumberUtil.getBoquan(fujiaxing);
				if(!StringNumberUtil.isNullOrEmpty(fujiaxing)){
					fujiaxing = GkzxCommon.LOSEPOWER_BDZZ+fujiaxing;
				}
				String forfeit = baseCrime.getForfeit();
				if(!StringNumberUtil.isNullOrEmpty(forfeit)&&!"0".equals(forfeit)){
					fujiaxing += GkzxCommon.FAJIN+forfeit;
				}
				String xingqi = baseCrime.getPunishmentyear()+","+baseCrime.getPunishmentmonth()+","+baseCrime.getPunishmentday();
				map.put("zhuxing", StringNumberUtil.getXingqi(baseCrime.getPunishmenttype(), xingqi));
				map.put("zhuxing1", baseCrime.getRemark());
				map.put("fujiaxing", fujiaxing);
				String xxqqr = DateUtil.dateFormat(baseCrime.getSentencestime(), "yyyy.MM.dd");
				String xxqzr  ="";
				String xxqqz = "";
				xxqqz = xxqqr;
				if(baseCrime.getSentenceetime()!=null){
					xxqzr = DateUtil.dateFormat(baseCrime.getSentenceetime(), "yyyy.MM.dd");
					xxqqz = ("自"+xxqqr+"至"+xxqzr+"止");
				}
				map.put("xingqiqizhi", xxqqz);
				map.put("yuanpanxingqiqiri",DateUtil.dateFormatForAip(baseCrime.getSentencestime()) );
				map.put("cjienddate",DateUtil.dateFormatForAip(baseCrime.getSentenceetime()) );
				map.put("fayuan",baseCrime.getJudgmentname());
				SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
				org = systemOrganizationService.getByOrganizationId(org.getPorgid());//根据用户所在单位的上级部门id获取监狱局名称
				map.put("text13",org.getName());
				map.put("text15",user.getOrganization().getName());
			}
			if (template != null) {
				docconent.add(template.getContent());
			}
			request.setAttribute("crimid", crimid);
			request.setAttribute("operator",operator);
			request.setAttribute("tempid", tempid);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/zyjwzxqjbjrxqjysadd");
	}
	

	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-8-9
	 */
	@RequestMapping(value = "/deletezyjw")
	@ResponseBody
	public int deletezyjw(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.BJRXQJYS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.BJRXQJYS+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.BJRXQJYS+LogCommon.DEL);
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
