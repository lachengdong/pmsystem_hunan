package com.sinog2c.mvc.controller.outexecute;

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
import org.springframework.web.servlet.view.InternalResourceView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 生活不能自理鉴定书
 * @author wangxf
 *
 */
@Controller
public class NotLifeDiagnoseBookController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private TbxfSentencealterationService tbxfSentencealterationService;

	/**
	 * 生活不能自理罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/notLifeDiagnoseBookChoose")
	public  ModelAndView notLifeDiagnoseBookChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/notLifeDiagnoseBookChoose");
	}

	/*
	 * 生活不能自理数据列表
	 */
	@RequestMapping("/notLifeDiagnoseBook")
	public ModelAndView notLifeDiagnoseBook(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
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
		return new ModelAndView("outexecute/notLifeDiagnoseBook");
	}
	/**
	 * w文档列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/notLifeDiagnoseBookChooseCrim")
	@ResponseBody
	public Object notLifeDiagnoseBookChooseCrim(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			SystemUser user = getLoginUser(request);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("departId", user.getDepartid());
			map.put("applyid", crimid);
			map.put("key", key);
			map.put("flowdefid", "doc_bwshbnzlsp");
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));  
	    	List<Map> list = flowBaseService.findByMapCondition(map);
			int count = flowBaseService.countAllByCondition(map);
	    	resultmap.put("data", list);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	/**
	 * 跳转到添加表单页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "notLifeDiagnoseBookAdd")
	public ModelAndView gestationDiagnoseBookAdd(HttpServletRequest request) {
		returnResourceMap(request);
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		SystemUser user = getLoginUser(request);
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (template != null) {
			docconent.add(template.getContent());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		TbprisonerBaseinfo  baseInfo = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		if(baseInfo!=null){
			map.put("cbiname", baseInfo.getName());
			map.put("xingbie", baseInfo.getGender());
			map.put("minzu", baseInfo.getNation());
			map.put("cbibirthday", DateUtil.dateFormatForAip(baseInfo.getBirthday()));
			map.put("jiatingzhuzhi", baseInfo.getFamilyaddress());
			map.put("text19", user.getPrison().getCity());
		}
		if(baseCrime!=null){
			String yuan=baseCrime.getRemark();
			String st=DateUtil.dateFormat(baseCrime.getSentenceetime());
			String et=DateUtil.dateFormat(baseCrime.getSentencestime());
			
			map.put("zuiming",baseCrime.getCauseaction());
			map.put("fujiaxing", baseCrime.getSanremark());
			map.put("zuiming",baseCrime.getMaincase());
			map.put("yuanpanxingzhong", baseCrime.getPunishmenttype());
			map.put("yuanpanxingqi", yuan);
			if(st!=null&&et!=null){
				map.put("yuanpanxingqiqizhi", st+GkzxCommon.NX_ZHIO+et+GkzxCommon.NX_ZHIT);
			}
		}
		if(null!=tbxfMap){
			String xianxingqiqizhi = "";
			String prisonterm = "";
			if (!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
				prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString().replace("，。", "。").replace("，；", "；"));
			}
			Object courtchangefromObj = tbxfMap.get("COURTCHANGEFROM");
			Object courtchangetoObj = tbxfMap.get("COURTCHANGETO");
			if(null!=courtchangefromObj){
				xianxingqiqizhi +=GkzxCommon.NX_ZI+DateUtil.dateFormat(courtchangefromObj)+GkzxCommon.NX_QI;
			}
			if(null!=courtchangetoObj){
				xianxingqiqizhi +=GkzxCommon.NX_ZHIO+DateUtil.dateFormat(courtchangetoObj)+GkzxCommon.NX_ZHIT;
			}
			map.put("xianxingqiqizhi", xianxingqiqizhi);
			map.put("xingqibiandong", prisonterm);
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname",baseInfo.getName());
		request.setAttribute("orgid",baseCrime.getOrgid1());
		request.setAttribute("flowdefid", "doc_bwshbnzlsp");
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());		
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/notLifeDiagnoseBookAdd.jsp"));
	}
	/**
	 * 修改/查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toNotLifeDiagnoseBook")
	public ModelAndView toregistrationLook(HttpServletRequest request) {
		String menuid=request.getParameter("menuid");
		
		String applyid=request.getParameter("applyid");
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(applyid);
		TbprisonerBaseCrime baseCrime =prisonerService.getCrimeByCrimid(applyid);
		if(baseCrime!=null){
			request.setAttribute("orgid", baseCrime.getOrgid1());
		}
		JSONArray docconent = new JSONArray();		
		String flowdraftid=request.getParameter("flowdraftid");	
		String baseDoc= flowBaseService.getDocConentByFlowdraftId(flowdraftid);
		if (baseDoc != null) {
			docconent.add(baseDoc);
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", applyid);
		request.setAttribute("applyid", applyid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("applyname", baseinfo.getName());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/notLifeDiagnoseBookAdd");
	}
	
}
