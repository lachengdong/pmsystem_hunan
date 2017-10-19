package com.sinog2c.mvc.controller.common;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.OAParameter;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.config.SpringContextHolder;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysFormDetails;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonFormService;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.common.CommonService;
import com.sinog2c.service.api.common.SpecialService;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeNewService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseDocService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.flow.FlowOtherFlowService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.prisoner.TbprisonerBaseInfoService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.TbsysFormDetailsService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;
import com.sinog2c.ws.IWebServiceClient;

/**
 * 通用表单处理控制器
 */
@Controller
public class CommonController extends ControllerBase{

	@Resource
	public SystemLogService logService;
	@Autowired
	private CommuteParoleBatchService commuteParoleBatchService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired(required=true)
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired(required=true)
	private FlowBaseDocService flowBaseDocService;
	@Autowired(required=true)
	private FlowArchivesService flowArchivesService;
	@Autowired
	private TbprisonerBaseInfoService tbprisonerBaseInfoService;
	@Autowired
	private KhjcPublicService khjcPublicService;
	@Autowired
	private FlowDeliverService flowDeliverService;
	@Autowired
	private CommonFormService commonFormService;
	@Autowired
	private FlowOtherFlowService flowOtherFlowService;
	@Autowired
	private DbmsDatasChemeNewService dbmsDatasChemeNewService;
	@Autowired
	private IWebServiceClient iWebServiceClient;
	@Autowired
	public TbsysFormDetailsService tbsysFormDetailsService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private UvFlowService uvFlowService;
	private JsonUtil jsonTool;

	
	/**
	 * 跳转公共罪犯选择页面
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/criminalChoosePage1")
	public ModelAndView criminalChoosePage(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		returnResourceMap(request);
		
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String menuid = getParameterString(request, "menuid", "");
		
		String currnodeid = getFlowCurrnodeidOfDataGrid(menuid, flowdefid, user);
		
		request.setAttribute("currnodeid", currnodeid);
		request.setAttribute("flowdefid",flowdefid);
		request.setAttribute("tempid",tempid);
		return new ModelAndView("system/criminalChoosePage");
	}
	
	/**
	 * 跳转待办事项审批页面
	 * @author YangZR 	2015-02-28
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toPublicTodoListPage")
	public ModelAndView toPublicTodoListPage(HttpServletRequest request){
		returnResourceMap(request);
		SystemUser user = this.getLoginUser(request);
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String notinnodeid = request.getParameter("notinnodeid");
		String menuid = request.getParameter("menuid");
		String unlockbtn = this.getParameterString(request, "unlockbtn", "");
		String currnodeid = getFlowCurrnodeidOfDataGrid(menuid, flowdefid, user);
		request.setAttribute("currnodeid", currnodeid);
		
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("notinnodeid", notinnodeid);
		request.setAttribute("unlockbtn", unlockbtn);
		return new ModelAndView("system/publicTodoListPage");
	}
	
	/**
	 * 方法描述：公共列表页面 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toPublicListPage.page")
	public String toPublicListPage(HttpServletRequest request) throws Exception {
		
		assemblePublicListPageData(request);
		//
		return ("common/publicListPage");
	}
	
	
	/**
	 * 跳转案件查看列表页
	 * 
	 * @author YangZR
	 * @Date 2015-05-10
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCheckCasePage.page")
	public ModelAndView toCheckCasePage(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		
		//增加一个参数noexpand控制跳转不同的案件查看的页面（不带全程留痕的案件查看页面）
		String noexpand = request.getParameter("noexpand")==null?"":request.getParameter("noexpand");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		request.setAttribute("provincecode",provincecode);
		request.setAttribute("departid",user.getDepartid());
		
		assemblePublicListPageData(request);
		//
		String orgid=user.getOrgid();
		
		Map departMap = new HashMap<>();
		departMap.put("departid",user.getDepartid());
		Map temMap = commuteParoleBatchService.getCommuteParoleBatchInfo(departMap);
		String batchid="1";
		if(temMap !=null&&temMap.size()>0){
			batchid = temMap.get("batch").toString();
		}
		
		request.setAttribute("batchid", batchid);
		
		if("yes".equals(noexpand)){
			return new ModelAndView("common/checkCasePages");
		}else{
		    return new ModelAndView("common/checkCasePage");
		}
	}
	
	/**
	 * 跳转减刑假释基本信息申请审批页面
	 * 
	 * @author YangZR
	 * @Date 2015-09-22
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toJxjsBaseInfoApprovePage.page")
	public ModelAndView toJxjsBaseInfoApprovePage(HttpServletRequest request) {
		
		assemblePublicListPageData(request);
		//
		return new ModelAndView("common/toJxjsBaseInfoApprovePage");
		
	}
	
	/**
	 * 跳转立案页面列表页
	 * 
	 * @author YangZR
	 * @Date 2015-05-10
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCaseLiAnPage.page")
	public ModelAndView toCaseLiAnPage(HttpServletRequest request) {
		
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String casetype = "";
		String scearch = "";
		if(StringNumberUtil.notEmpty(flowdefid)){
			flowdefid = flowdefid.trim();
			if("other_zfjyjxjssp".equals(flowdefid)){
				casetype = GkzxCommon.CASE_TYPE_JXJS_FY;
				scearch = "JXJS";
			}else if("other_jybwjycbsp".equals(flowdefid) && !"1400".equals(departid)){
				casetype = GkzxCommon.GDBWTYPE;
				scearch = "BWJY";
			}else if("other_jybwjycbsp".equals(flowdefid) && "1400".equals(departid)){
				casetype = GkzxCommon.SXJBWTYPE;
			}
			//lyg:
			else if ("other_zfbwjysjhydjb".equals(flowdefid)){
				casetype = GkzxCommon.GDBWRJ;
			}
		}
		
		Map paramMap = new HashMap();
		paramMap.put("departid", departid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("curyear", curyear);
		String casenumber = flowBaseService.getSJMaxCaseNumForGD(paramMap);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("casetype", casetype);
		request.setAttribute("scearch",scearch);
		//
		assemblePublicListPageData(request);
		//
		return new ModelAndView("common/caseLiAnPage");
		
	}
	
	
	public void assemblePublicListPageData(HttpServletRequest request){
		
		returnResourceMap(request);
		// 获取用户
		SystemUser user = getLoginUser(request);
		Map<String, ? extends Object> paraMap = super.parseParamMap(request);
		super.addMap2Attribute(paraMap, request);
		
		String pageSize = getParameterString(request, "pageSize", "");
		if(StringNumberUtil.isEmpty(pageSize)){
			pageSize = "20";
		}
		request.setAttribute("pageSize", pageSize);
		// 
		String menuid = getParameterString(request, "menuid", "");
		//
		String flowdefid = getParameterString(request, "flowdefid", "");
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		request.setAttribute("flowdefid", flowdefid);
		// 将所有参数封装为JS字符串
		Map<String,String> paramMap = parseParamMapString(request);
		request.setAttribute("paramMap", paramMap);
//		Object jsonParamObject = JSONObject.toJSON(jsParaMap);
//		request.setAttribute("jsonParamObject", jsonParamObject);
		
		String currnodeid = getFlowCurrnodeidOfDataGrid(menuid, flowdefid, user);
		request.setAttribute("currnodeid", currnodeid);
		
		//获取资源信息. Grid信息
		int gridtypeid = 19;
		List<SystemResource> gridList =systemResourceService.listByMenuid(user, menuid, gridtypeid);
		//
		if(null != gridList && !gridList.isEmpty()){
			SystemResource grid1 = gridList.get(0);
			//
			if(null != grid1){
				//
				request.setAttribute("grid1", grid1);
				//
				int gridcoltypeid = 20;
				String gridid_1 = grid1.getResid();

				List<SystemResource> grid1ColList =systemResourceService.listByMenuid(user, gridid_1, gridcoltypeid);
				request.setAttribute("grid1ColList", grid1ColList);
			}
		}
		
	}
	
	private String getFlowCurrnodeidOfDataGrid(String resid, String flowdefid, SystemUser user){
		Map paraMap = new HashMap<String,Object>();
		paraMap.put("presid", resid);
		paraMap.put("flowdefid", flowdefid);
		paraMap.put("departid", user.getDepartid());
		paraMap.put("userid", user.getUserid());
		String currnodeid = flowDeliverService.getFlowCurrnodeidOfDataGrid(paraMap);
		if(StringNumberUtil.isEmpty(currnodeid)){
			currnodeid = GkzxCommon.RESULT_ZERO;
		}
		return currnodeid;
	}

	/**
	 * 跳转表单的打开页面
	 * @author YangZR 2015-03-01
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="baseDocOperate.page")
	public ModelAndView baseDocOperate(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		returnResourceMap(request);
		
		JSONArray docconent = new JSONArray();
		String optype = request.getParameter("optype")==null?"":request.getParameter("optype");//optype 如果为add , check , edit 
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String solutionid = request.getParameter("solutionid");
		String flowdraftid = request.getParameter("flowdraftid");
		String applyid = request.getParameter("applyid");
		String noeditnode = request.getParameter("noeditnode");
		Map<String,Object> paramMap = super.parseParamMap(request);
		
		FlowBase fb = null;
		if(StringNumberUtil.notEmpty(flowdraftid)){
			paramMap.put("flowdraftid", flowdraftid);
			fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
			paramMap.put("currnodeid", fb.getCurrnodeid());
			flowdefid = fb.getFlowdefid();
			if(StringNumberUtil.isEmpty(applyid)){
				applyid = fb.getApplyid();
			}
		}
		//
		paramMap.put("applyid", applyid);
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		//
		if(StringNumberUtil.notEmpty(tempid)){
			paramMap.put("tempid", tempid);
		}
		paramMap.put("flowdefid", flowdefid);
		//
		String currnodeid = uvFlowService.getCurrnodeidByFlowdraftid(flowdraftid);
		request.setAttribute("currnodeid", currnodeid);
		//
		if("add".equals(optype.trim())){
			//点击新增按钮的打开表单页面，查询表单模板及通过查询方案查询表单的数据
			docconent = assembleFormData(request,docconent,paramMap,user,tempid);
		}else if("edit".equals(optype.trim()) || "solution".equals(optype.trim())){
			//点击修改按钮的打开表单页面，查询表单大字段，如果表单大字段为空，则查询表单模板及通过查询方案查询表单的数据
			//
			String docconentStr = getFormData(tempid,flowdefid,flowdraftid);
			if(StringNumberUtil.notEmpty(docconentStr)){
				docconent.add(docconentStr);
			}else{
				//不存在大字段时，获取表单模板，及通过查询方案获取表单上的数据
				docconent = assembleFormData(request,docconent,paramMap,user,tempid);
			}
		}else{
			//就只获取大字段
			String docconentStr = getFormData(tempid,flowdefid,flowdraftid);
			if(StringNumberUtil.notEmpty(docconentStr)){
				docconent.add(docconentStr);
			}
		}
		
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("username", user.getName());
		request.setAttribute("flowdefid", flowdefid);
		if(StringNumberUtil.notEmpty(flowdraftid)){
			request.setAttribute("flowdraftid", flowdraftid);
		}
		if(StringNumberUtil.notEmpty(applyid)){
			request.setAttribute("applyid", applyid);
		}
		if(StringNumberUtil.notEmpty(noeditnode)){
			request.setAttribute("noeditnode", noeditnode);
		}
		request.setAttribute("solutionid", solutionid);
		return new ModelAndView("aip/khjc/baseDocAdd");
	}
	
	
	/**
	 * 跳转报表的打开页面
	 * @author YangZR 2015-03-01
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="baseReportOperate.page")
	public ModelAndView baseReportOperate(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		returnResourceMap(request);
		//
		String optype = request.getParameter("optype")==null ? "" : request.getParameter("optype");//optype 如果为add , check , edit 
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String objid = request.getParameter("objid");
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		String solutionid = request.getParameter("solutionid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowdraftids = request.getParameter("flowdraftids");
		String applyid = request.getParameter("applyid");
		
		Map<String,Object> paramMap = super.parseParamMap(request);
		paramMap.put("flowdraftids", flowdraftids);
		if(StringNumberUtil.notEmpty(flowdraftid)){
			paramMap.put("flowdraftid", flowdraftid);
			if(StringNumberUtil.isEmpty(applyid)){
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
				if(null != fb){
					paramMap.put("applyid", fb.getApplyid());
				}
			}
		}
		paramMap.put("objid", objid);
		//
		String menuid = request.getParameter("menuid");
		SystemResource systemResource = systemResourceService.getByResourceId(menuid);
		if(StringNumberUtil.isEmpty(solutionid)){
			solutionid = systemResource.getQuerysql();
			paramMap.put("solutionid", solutionid);
		}
		if(StringNumberUtil.isEmpty(tempid)){
			tempid = systemResource.getFormid();
			paramMap.put("tempid", tempid);
		}
		//
		SystemUser user = getLoginUser(request);
		
		if("add".equals(optype.trim())){
			//点击新增按钮的打开报表页面，组装报表
			assembleReportData(request,paramMap,user,menuid);
		}else if("edit".equals(optype.trim())){
			//点击修改按钮的打开报表页面，查询报表大字段，如果报表大字段为空，组装报表
			//
			String docconentStr = getFormData(tempid,flowdefid,flowdraftid);
			if(StringNumberUtil.notEmpty(docconentStr)){
				docconent.add(docconentStr);
				request.setAttribute("formcontent", docconent.toString());
			}else{
				//通过flowdraftid 获取applyid
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
				if(null != fb){
					paramMap.put("applyid", fb.getApplyid());
				}
				
				//不存在大字段时，组装报表
				assembleReportData(request,paramMap,user,menuid);
			}
			
		}else{
			//就只获取大字段
			String docconentStr = getFormData(tempid,flowdefid,flowdraftid);
			if(StringNumberUtil.notEmpty(docconentStr)){
				docconent.add(docconentStr);
				request.setAttribute("formcontent", docconent.toString());
			}
		}
		
		String currnodeid = uvFlowService.getCurrnodeidByFlowdraftid(flowdraftid);
		request.setAttribute("currnodeid", currnodeid);
		
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", flowdefid);
		if(StringNumberUtil.notEmpty(flowdraftid)){
			request.setAttribute("flowdraftid", flowdraftid);
		}
		request.setAttribute("solutionid", solutionid);
		request.setAttribute("objid", objid);
		return new ModelAndView("aip/khjc/baseDocAdd");
		
	}
	
	
	/**
	 * 描述：组装表单：表单模板&表单数据
	 * @author YangZR
	 */
	public JSONArray assembleFormData(HttpServletRequest request,JSONArray docconent,Map<String,Object> paramMap,SystemUser user,String tempid){
		
		Map<String,Object> dataMap = commonFormService.assembleFormData(docconent,paramMap, user,tempid);
		if(null != dataMap){
			
			if(null != dataMap.get("docconent") && dataMap.get("docconent") instanceof JSONArray){
				docconent = (JSONArray)dataMap.get("docconent");
			}
			
			if(null != dataMap.get("ismultipage")){
				request.setAttribute("ismultipage", "0");//用于控制多页显示的标志
			}
			
			//
			if(null != dataMap.get("formDatajson")){
				request.setAttribute("formDatajson",  dataMap.get("formDatajson"));
			}

			//
			if(null != dataMap.get("selectDatajson")){
				request.setAttribute("selectDatajson", dataMap.get("selectDatajson"));
			}
		}
		
		return docconent;
	}
	

//	private Map<String,Object> assembleFormData(JSONArray docconent,Map<String,Object> paramMap,SystemUser user,String tempid){
//		
//		Map<String,Object> returnMap = new HashMap<String,Object>();
//		
//		Object aipFileStr = "";//表单大字段
//		String departid=user.getDepartid();
//		
//		//查询表单数据
//		Map<String,Object> resultMap = solutionService.query(paramMap, user);
//		Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
//		
//		docconent = commonFormService.parseFormDataOfSolution(resultMap);
//		String optype = paramMap.get("optype") == null ? "" : paramMap.get("optype").toString().trim();
//		if((null ==docconent || docconent.size() <=0) && !"solution".equals(optype)){
//			//查询表单模板
//			if(StringNumberUtil.notEmpty(tempid)){
//				
//				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
//				if (template != null){
//					aipFileStr = template.getContent();
//					if(StringNumberUtil.notEmpty(aipFileStr)){
//						docconent.add(aipFileStr);
//					}
//				}
//				
//			}
//			
//		}
//		
//		if(docconent.size()>1){
//			returnMap.put("ismultipage", "0");//用于控制多页显示的标志
//		}
//		
//		//如果有挂系统模板，组装模板 
//		if(null == map){
//			map = new HashMap();
//		}
//		
//		if(StringNumberUtil.notEmpty(tempid)){
//			Map<String,Object> param4FormDetails = new HashMap<String,Object>();
//			param4FormDetails.put("type", 0);// 类型（0：系统模板，1：弹出框）
//			param4FormDetails.put("tempid", tempid);
//			param4FormDetails.put("departid", departid);
//			List<TbsysFormDetails> result = tbsysFormDetailsService.getFormDetails(param4FormDetails);
//			//如果当前监狱找不到相应的流程信息，则找jyconfig中对应省份的流程信息
//			if(result.size() <= 0){
//				Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//				if (jypro != null) {
//					param4FormDetails.put("departid", jypro.getProperty("provincecode"));
//				}
//				result = tbsysFormDetailsService.getFormDetails(param4FormDetails);
//			}
//			if(null!=result && result.size()>0){
//				for(TbsysFormDetails tbsysFormDetails : result){
//					if(StringNumberUtil.notEmpty(tbsysFormDetails.getSystempid())){
//						TbsysTemplate sysTemplate = systemModelService.getTemplateAndDepartid(tbsysFormDetails.getSystempid(), departid);
//						if(null!=sysTemplate && StringNumberUtil.notEmpty(sysTemplate.getContent())){
//							String content = sysTemplate.getContent();
//							content = MapUtil.replaceBracketContent(content, map);//将系统模板中的中括号内容替换成map的相关的数据
//							content = StringNumberUtil.dealCharForForm(content);
//							map.put(tbsysFormDetails.getNodeid(), content);//将组装好的系统模板 放入map
//						}
//					}
//				}
//			}
//			
//		}
//		
//		//
//		if(null!=map){
//			map = StringNumberUtil.dealMapForForm(map);
//			returnMap.put("formDatajson", new JSONObject(map).toString());
//		}
//
//		//
//		Object _options = (Map<String,Object>)resultMap.get("options");
//		if(null != _options && _options instanceof Map){
//			Map<String,Object> options = (Map<String,Object>)_options;
//			options = MapUtil.convertKey2LowerCase(options);
//			returnMap.put("selectDatajson", new JSONObject(options).toString());
//		}
//		
//		returnMap.put("docconent", docconent);
//		
//		return returnMap;
//		
//	}
	
	/**
	 * 描述：获取大字段
	 * @author YangZR 2015-03-01
	 * @param flowdefid　流程定义id
	 * @param templetid   表单模板id
	 * @param flowdraftid 流程草稿id
	 */
	private String getFormData(String tempid,String flowdefid,String flowdraftid){
		if(StringNumberUtil.isEmpty(flowdraftid) || StringNumberUtil.isEmpty(tempid)){
			return null;
		}
		String docconent = "";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tempid", tempid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("flowdraftid", flowdraftid);
		if (flowdefid != null){
			if(flowdefid.contains("other_")){
				//获取表单大字段
				FlowBaseOther fbo = flowBaseOtherService.getLastDocconentByFlowdraftid(paramMap);
				if(null!=fbo){
					docconent = fbo.getDocconent();
				}
			}else if(flowdefid.contains("doc_")){
				//获取普通审批流程的表单大字段
				FlowBaseDoc fbd = flowBaseDocService.findLastDocByflowdraftid(paramMap);
				if(null!=fbd){
					docconent = fbd.getDocconent();
				}
			}else if(flowdefid.contains("arch_")){
				//获取审批电子档案的表单大字段
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
				if(null!=fb&&StringNumberUtil.notEmpty(fb.getFlowid())){
					docconent = flowArchivesService.getArchiveDocconentByFlowid(fb.getFlowid());
				}
			}
		}
		
		return docconent;
	}
	
	
	/**
	 * 描述：组装报表
	 * @author YangZR	2015-03-30
	 *
	 */
	public void assembleReportData(HttpServletRequest request,Map<String,Object> paramMap,SystemUser user,String menuid){
		//
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap = solutionService.query(paramMap, user);
		RMEngine engine=this.systemResourceService.getReportEngin(paramMap, user, resultMap);
		//
		String reportPath=request.getRealPath("").replace("\\", "/")+"/RMreportReport/";
		String reportName = systemResourceService.getReportNameByMenuid(menuid);
		engine.SetReportFile(reportPath + reportName + ".rmf", 1);
		//
		String reportdata = engine.dedaoReportData();
		request.setAttribute("reportdata", reportdata);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reportdata", reportdata);//用于导出预览
		map.put("engine", engine);//用于导出预览
		setSessionAttribute4Report(request, map);
//		request.getSession().setAttribute("reportdata", reportdata); 
//		request.getSession().setAttribute("engine", engine);
		
		request.setAttribute("flag", "report");//标志为报表
		
	}
	
	/**
	 * 为导出预览作准备
	 * @param request
	 * @param map
	 */
	private void setSessionAttribute4Report(HttpServletRequest request, Map<String,Object> map){
		Set<String> keySet = map.keySet();
		for(String key : keySet){
			request.getSession().setAttribute(key, map.get(key));
		}
	}
	
	/**
	 * 描述：跳转到系统模板应用的界面，类似建议书、会议记录等
	 * @author YangZR
	 * @Date	2015-03-28
	 */
	@RequestMapping(value = "/toSysTemplate.page")
	public ModelAndView toSysTemplate(HttpServletRequest request){
		 returnResourceMap(request);
		 super.requestParam2Attribute(request);
		 //
		 String tempid = request.getParameter("tempid");
		 String solutionid = request.getParameter("solutionid");
		 if(StringNumberUtil.isEmpty(tempid)||StringNumberUtil.isEmpty(solutionid)){
			 String menuid = request.getParameter("menuid");
			 SystemResource sysResource = systemResourceService.getByResourceId(menuid);
			 if(StringNumberUtil.isEmpty(tempid)&&StringNumberUtil.notEmpty(sysResource.getFormid())){
				 tempid = sysResource.getFormid();
			 }
			 if(StringNumberUtil.isEmpty(solutionid)&&StringNumberUtil.notEmpty(sysResource.getQuerysql())){
				 solutionid = sysResource.getQuerysql();
			 }
		 }
		 request.setAttribute("tempid", tempid);
		 request.setAttribute("solutionid", solutionid);
		 
		return new ModelAndView("common/sysTemplate");
	}
	
	@RequestMapping("/publicFormsList.page")
	public ModelAndView publicFormsList(HttpServletRequest request){
		returnResourceMap(request);
		SystemUser user = this.getLoginUser(request);
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		
		String menuid = getParameterString(request, "menuid", "");
		String currnodeid = getFlowCurrnodeidOfDataGrid(menuid, flowdefid, user);
		request.setAttribute("currnodeid", currnodeid);
				
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", flowdefid);
		return new ModelAndView("common/publicFormsList");
	}	
	
	
	@RequestMapping(value = "/toFatherTabPage")
	public Object toFatherTabPage(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		String menuid = request.getParameter("menuid");
		String objid = request.getParameter("objid");
		//
		String ids = request.getParameter("ids")==null?"":request.getParameter("ids").trim();
		String tempIds = request.getParameter("tempIds");//针对add按钮的弹框按钮，无法传递ids，另做处理
		if(StringNumberUtil.notEmpty(tempIds)){
			String[] tempIdArr = tempIds.split("@");
			for(String temp : tempIdArr){
				ids += "&"+temp;
			}
		}
		request.setAttribute("ids",ids);
		
		
		//针对已办事项，打开Tab页时不能做操作的处理
		String noOperate = request.getParameter("noOperate");
		//
		Map paraMap = new HashMap();
		paraMap.put("userid", user.getUserid());
		paraMap.put("restypeid", 17);
		paraMap.put("presid", menuid);
		List<SystemResource> tabsList = systemResourceService.getResourcesByConditions(paraMap);
		List<Map<String,String>> tabList = new ArrayList<Map<String,String>>();
		//
		if(null != tabsList && tabsList.size()>0){
			for(SystemResource tab : tabsList){
				Map<String,String> tabMap = new HashMap<String,String>();
				tabMap.put("id", tab.getResid());
				tabMap.put("name", tab.getResid());
				tabMap.put("title", tab.getName());
				//
				String url = tab.getSrurl()==null?"":tab.getSrurl();
				StringBuilder sb = new StringBuilder(url);
				if(url.indexOf("?") < 0){
					sb.append("?1=1");
				}
				if(StringNumberUtil.notEmpty(tab.getResid()) && (StringNumberUtil.isEmpty(noOperate) || ! "1".equals(noOperate.trim()) )){
					sb.append("&menuid=").append(tab.getResid());
				}
				if(StringNumberUtil.notEmpty(objid)){
					sb.append("&objid=").append(objid);
				}
				tabMap.put("url", sb.toString());
				
				//
				if(StringNumberUtil.notEmpty(tab.getText1())){
					String[] appendArr = tab.getText1().trim().split(" ");
					for(String append : appendArr){
						if(StringNumberUtil.notEmpty(append)){
							String[] keyValue = append.trim().split("=");
							if(2 == keyValue.length){
								tabMap.put(keyValue[0].trim(), keyValue[1].trim());
							}
						}
					}
				}
				
				tabList.add(tabMap);
			}
			
			String  tabListJson = JSONUtils.toJSONString(tabList);
			request.setAttribute("tabListJson",tabListJson);
		}
		
		request.setAttribute("flowMenuid", menuid);//与流程有关的资源ID
		return "common/commonFatherTabPage";
	}
	
	
	@RequestMapping(value = "/toYwgkFatherTabPage")
	public Object toYwgkFatherTabPage(HttpServletRequest request){
		returnResourceMap(request);
		String ids = request.getParameter("ids");
		String optype = request.getParameter("optype");
		String indexFlag = request.getParameter("indexFlag");
		String flowdraftid = "";
		String applyid = "";
		if(StringNumberUtil.notEmpty(optype)){
			request.setAttribute("optype", optype);
		}
		
		String menuid = request.getParameter("menuid");
		if(StringNumberUtil.notEmpty(ids)){
			String[] idArr = ids.split(",");
			int index = 0;
			if(StringNumberUtil.notEmpty(indexFlag)){
				index = Integer.parseInt(indexFlag);
			}
			if(StringNumberUtil.notEmpty(idArr[index])){
				String[] idTemp = idArr[index].split("@");
				//如果是新打开的表单页面，则传入查询表单数据的applyid
				Map<String,Object> paramMap = new HashMap<String,Object>();
				flowdraftid = idTemp[0];
				paramMap.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
				request.setAttribute("flowdraftid", flowdraftid);
				applyid = fb.getApplyid();
				request.setAttribute("applyid", applyid);
				request.setAttribute("applyname", fb.getApplyname());
				request.setAttribute("modelname", idTemp[2]);
				menuid = idTemp[1];
			}
		}
		
		SystemUser user = getLoginUser(request);
		Map paraMap = new HashMap();
		paraMap.put("userid", user.getUserid());
		paraMap.put("restypeid", 17);
		paraMap.put("presid", menuid);
		
//		List<SystemResource> tabsList = systemResourceService.getResourcesByConditions(paraMap);
		//针对已办事项，打开Tab页时不能做操作的处理
		String noOperate = request.getParameter("noOperate");
		
		
		request.setAttribute("flowMenuid", menuid);//与流程有关的资源ID	
		return "common/transaction";
	}
	
	
	@RequestMapping(value = "/getSysTemplateData.json")
	@ResponseBody
	public Object getSysTemplateData(HttpServletRequest request) throws Exception{
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//
		Map<String,Object> paramMap = super.parseParamMap(request);
		SystemUser user = getLoginUser(request);
		//
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		String optype = request.getParameter("optype");
		//
		String annexcontent = "";//系统模板数据
		//
//		if(StringNumberUtil.isEmpty(paramMap.get("applyid"))){
//			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
//			String applyid = fb.getApplyid();
//			paramMap.put("applyid", applyid);
//		}
		//
		if(null!=optype&&optype.equals("add")){
			annexcontent = commonService.assembleSysTemplateData(paramMap,user);
			
		}else if(null!=optype&&optype.equals("edit")){
			annexcontent = getFormData(tempid,flowdefid,flowdraftid);
			if(StringNumberUtil.isEmpty(annexcontent)){
				annexcontent = commonService.assembleSysTemplateData(paramMap,user);
			}
		}else{
			annexcontent = getFormData(tempid,flowdefid,flowdraftid);
		}
		
		//
		resultMap.put("annexcontent", annexcontent);
		//
		return resultMap;
		
	}
	
	
	
	/**
	 * 方法描述：跳转至报表预览页
	 * @author YangZR 2015-03-29
	 */
	@RequestMapping(value = "/toPreviewReport.page")
	public ModelAndView toPreviewReport(HttpServletRequest request){
		this.returnResourceMap(request);
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramMap = this.parseParamMap(request);
		//
		if(StringNumberUtil.isEmpty(paramMap.get("flowdefid"))){
			paramMap.put("flowdefid", GkzxCommon.DEFAULT_FLOWDEFID);
		}
		//menuid为预览按钮传的菜单id，该menuid与报表关联，同时该menuid对应的资源存了solutionid
		String menuid = request.getParameter("menuid");
		SystemResource systemResource = systemResourceService.getByResourceId(menuid);
		String solutionid = systemResource.getQuerysql();
		paramMap.put("solutionid", solutionid);
		//
		assembleReportData(request, paramMap, user, menuid);
		String tempid = request.getParameter("tempid");
		//模板存报表时，tempid+report
		tempid = tempid+"report";
		request.setAttribute("tempid", tempid);
		request.setAttribute("solutionid", solutionid);
		request.setAttribute("flowdraftid", paramMap.get("flowdraftid"));
		//
		return new ModelAndView("aip/khjc/baseDocAdd");
	}
	
	/**
	 * 公共保存方法
	 * yanqutai
	 */
	@RequestMapping("/ajaxSaveBaseDoc")
	@ResponseBody
	public Object ajaxSaveBaseDoc(HttpServletRequest request){
		SystemUser user = getLoginUser(request);//当前登陆用户对象
		Map<String,Object> map = this.parseParamMap(request);
		String _ip = IPUtil.getClientIP(request);//客户端IP
		map.put("_ip", _ip);
		return khjcPublicService.saveBaseDoc(map,user);
	}
	
	
	/**
	 * 描述：判断是否存在必要文书
	 * @author YangZR	2015-06-28
	 */
	@RequestMapping("/isPapersMaked.json")
	@ResponseBody
	public Object isPapersMaked(HttpServletRequest request){
		SystemUser user = getLoginUser(request);//当前登陆用户对象
		Map<String,Object> map = super.parseParamMap(request);
		return khjcPublicService.isPapersMaked(map,user);
	}
	
	/**
	 * 描述：公共删除按钮
	 * @author YangZr 2015.03.19
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removeDate.json")
	@ResponseBody
	public Object commonRemoveDate(HttpServletRequest request){

		JSONMessage message;
		//创建参数Map
		Map<String,Object> map = this.parseParamMap(request);
		
		String flowdraftids = request.getParameter("flowdraftids");
		String flowdraftid = flowdraftids;
		String menuid = request.getParameter("menuid");
		
		//通过menuid获取solutionid
		String solutionid = "";
		SystemResource SystemResource = systemResourceService.getByResourceId(menuid);
		if(null != SystemResource){
			solutionid = SystemResource.getQuerysql();
		}
		
		map.put("flowdraftid", flowdraftid);
		map.put("flowdraftids", flowdraftids);
		map.put("solutionid", solutionid);
		map.put("menuid", menuid);
		
		// 获取用户
		SystemUser user = getLoginUser(request);
		
		//调用业务层方法，并返回结果
		message = commonFormService.commonRemoveDate(map,user);
		
		return message;

	}
	
	@RequestMapping("/toFormPopUpBox")
	public String toFormPopUpBox(HttpServletRequest request){
		String tojsp = request.getParameter("tojsp");
		return tojsp;
//		return "common/formPopUpBox";
	}
	
	/**
	 *方法描述：获取公共列表数据 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPublicListData.json")
	@ResponseBody
	public Object getPublicListData(HttpServletRequest request) throws Exception {
		//
		// 获取用户
		SystemUser user = getLoginUser(request);
		
		//
		Map<String,Object> paramMap = this.parseParamMap(request);
		//
		Set<String> paramSet = paramMap.keySet();
		for(String key : paramSet){
			String value = paramMap.get(key)==null?"":paramMap.get(key).toString();
			value = URLDecoder.decode(value,"UTF-8");
			paramMap.put(key, value);
		}
		//
		
		paramMap = ServiceImplBase.processMapPage(paramMap);

		String params = request.getParameter("params");
		Map<String,Object> paramsMap = MapUtil.getDataByAip(params);
		//
		paramMap.putAll(paramsMap);
		//
		List<?> data = new ArrayList<Object>();
		int total = 0;
		
		this.isFjqUser(paramMap, user);//map --> key=isfjquser  value=1 则为分监区用户
		
		try{
			Map<String,Object> resultMap = solutionService.list(paramMap, user);
			//
			Object count = resultMap.get("count");
			Object list = resultMap.get("list");
			//
			if(count instanceof Map<?,?>){
				//
				Map<String, Object> countMap = (Map<String, Object>) count;
				count = MapUtil.getFirstValue(countMap);
			}
			//
			if(StringNumberUtil.isLong(""+count)){
				total = StringNumberUtil.parseInt(count, 0);
			}
			//
			if(list instanceof List<?>){
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)list;
				data = MapUtil.convertKeyList2LowerCase(dataList);
			}
			//
			if(0 == total && !data.isEmpty()){
				total = data.size();
			}
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//
		JSONMessage message = JSONMessage.newMessage();
		message.setSuccess();
		message.setData(data);
		message.setTotal(total);
		return message;
	}
	

	@RequestMapping(value = "/saveformdata.json")
	@ResponseBody
	public Object saveFormData(HttpServletRequest request){

		// 获取用户
		SystemUser user = getLoginUser(request);
		
		Map<String, ? extends Object> paraMap = super.parseParamMap(request);
		super.addMap2Attribute(paraMap, request);
		
		//组合参数Map: paramMap
		String solutionid = request.getParameter("solutionid");//查询方案Id
		String tempid = request.getParameter("tempid");//表单模板Id
		String crimid = request.getParameter("crimid");//crimid
		String flowdefid = request.getParameter("flowdefid");//流程定义ID
		String params = request.getParameter("params");
		String formclob = request.getParameter("formclob");//表单大字段
		String noteinfo = request.getParameter("noteinfo");//表单各域的值
		
		Map<String, Object> paramMap = MapUtil.parseJSONArray2Map(params);
		paramMap.put("solutionid", solutionid);
		paramMap.put("tempid", tempid);
		paramMap.put("crimid", crimid);
		paramMap.put("flowdefid", flowdefid);
		Map<String,Object> formmap = MapUtil.parseJSONArray2Map(noteinfo);
		formmap.put("content", formclob);
		//paramMap.put("formmap", formmap);
		paramMap.putAll(formmap);
		
		super.DEBUG_MODE = true;
		debug("paramMap="+paramMap.toString());
		//paramMap : {solution:solution, tempid:tempid, formmap:{formclob:formclob, key1:value1, ...}}
		Map<String, Object> result = solutionService.save(paramMap, user);
		Object _status = result.get("_status");
		int status = 0;
		if(_status instanceof Integer){
			//
			status = (Integer)_status;
		} else if(_status instanceof String){
			//
			status = StringNumberUtil.parseInt(_status, 0);
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		message.setStatus(status);
		//
		if(1 == status || result.size() > 0){
			message.setInfo("操作成功");
			message.setSuccess();
		} else {
			message.setInfo("保存失败");
		}
		//
		return message;
	}
	
	
	@RequestMapping("/ajaxGetFormDetails.json")
	@ResponseBody
	public Object ajaxGetFormDetails(HttpServletRequest request){
		//tempid:tempid,nodeid:nodeid,type:type
		SystemUser user = getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		
		//先按照当前用户所在监狱查询
		map.put("departid", user.getDepartid());
		map.put("orgid", user.getOrgid());
		List<TbsysFormDetails> result = tbsysFormDetailsService.getFormDetails(map);
		//当前监狱没有则从当前省份查找
		if(result.size() < 1){
			map.put("departid", provincecode);
			result = tbsysFormDetailsService.getFormDetails(map);
		}
		
		//当前省份没有则从全国通用中查找  0
		if(result.size() < 1){
			map.put("departid", "0");
			result = tbsysFormDetailsService.getFormDetails(map);
		}
		
		if(null != result && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	
	@RequestMapping("/ajaxGetDocconent.json")
	@ResponseBody
	public Object getDocconent(HttpServletRequest request){
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String flowdraftid = request.getParameter("flowdraftid");
		return getFormData(tempid, flowdefid, flowdraftid);
	}
	
	@RequestMapping("/reloadTemplate.json")
	@ResponseBody
	public Object reloadTemplate(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Map<String,Object> paramMap = this.parseParamMap(request);
		String tempid = paramMap.get("tempid").toString();
		JSONArray docconent = new JSONArray();
		String optype = paramMap.get("optype")==null?"":paramMap.get("optype").toString();
		
		if(optype.equals("add")){
			return commonFormService.assembleFormData(docconent,paramMap,user,tempid);
		}else{
			String flowdefid = paramMap.get("flowdefid")==null?"":paramMap.get("flowdefid").toString();
			String flowdraftid = paramMap.get("flowdraftid")==null?"":paramMap.get("flowdraftid").toString();
			
			String docconentStr = getFormData(tempid,flowdefid,flowdraftid);
			if(StringNumberUtil.notEmpty(docconentStr)){
				docconent.add(docconentStr);
			}
			Map<String,Object> returnMap = new HashMap<String,Object>();
			returnMap.put("docconent", docconent);
			return returnMap;
		}
		
	}
	
	@RequestMapping("/ajaxGetMaterials.json")
	@ResponseBody
	public Object getMaterials(HttpServletRequest request){
		Map paramap = this.parseParamMap(request);
		SystemUser user = this.getLoginUser(request);

		JSONArray docconent = new JSONArray();
		docconent = assembleFormData(request,docconent,paramap,user,null);
		return docconent;
	}
	
	
	/**
	 * 方法描述：判断本部门本用户是否保存过会议记录toFatherTabPage
	 * @author YangZR
	 * @date 2015-05-16
	 */
	@RequestMapping("/judgeExistMeetRecordByUser.json")
	@ResponseBody
	public Object judgeExistMeetRecordByUser(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		String menuid = request.getParameter("menuid");
		
		SystemResource systemResource = systemResourceService.getByResourceId(menuid);
		String tempid = systemResource.getFormid();
		
//		String tempid = request.getParameter("tempid");
		paramap.put("tempid", tempid);
		paramap.put("userid", user.getUserid());
		paramap.put("orgid", user.getOrgid());
		paramap.put("departid", user.getDepartid());
		return flowOtherFlowService.judgeExistMeetRecordByUser(paramap);
		
	}
	
	/**
	 * 材料列表
	 * @autor YangZR 2015-05-24
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toMaterialList.page")
	public ModelAndView toMeetRecord(HttpServletRequest request){
		// 模型和视图.
		Map map = super.parseParamMap(request);
		super.addMap2Attribute(map, request);
		
		String menuid = request.getParameter("menuid");
		SystemResource systemResource = systemResourceService.getByResourceId(menuid);
		if(null != systemResource){
			request.setAttribute("title", systemResource.getName());
		}
		
		ModelAndView mav = new ModelAndView("common/materialListPage");
		return mav;
	}
	
//	/**
//	 * 方法描述  手动数据交换
//	 * @author YangZR
//	 * @date 2015-05-31
//	 */
//	@RequestMapping("/caseDataExchange.json")
//	@ResponseBody
//	public Object caseDataExchange(HttpServletRequest request){
//		SystemUser user = getLoginUser(request);
//		String ddcid = request.getParameter("ddcid");
//		String crimids = request.getParameter("crimids");
//		List<String> crimidList = StringNumberUtil.formatString2List(crimids, ",");
//		String condition = "(" + StringNumberUtil.formatList2String(crimidList, " or ") + ")";
//		//
//		Map<String,Object> paramap = new HashMap<String,Object>();
//		paramap.put("ddcid", ddcid);
//		Map map = dbmsDatasChemeNewService.getRemoteIp(paramap);
//		if(null == map){
//			return null;
//		}
//		String ip = map.get("ip")==null?"":map.get("ip").toString();//这个会有问题：项目与数据库必须在同一个服务器上
//		if(StringNumberUtil.isEmpty(ip)){
//			return null;
//		}
//		String port = "8080";//应从数据库查询
//		//
//		Object result = iWebServiceClient.wsCaseDataExchange(ip, port, ddcid, condition);
//		return result;
//	}
	
	
	/**
	 * 描述：更新兑现时间
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateDate.json")
	@ResponseBody
	public Object updateDate(HttpServletRequest request){
		JSONMessage message;
		//创建参数Map
		Map<String,Object> map = this.parseParamMap(request);
		
		String flowdraftids = request.getParameter("flowdraftids");
		String flowdraftid = flowdraftids;
		String menuid = request.getParameter("menuid");
		String duixiandate = request.getParameter("duixiandate");
		
		//通过menuid获取solutionid
		String solutionid = "";
		SystemResource SystemResource = systemResourceService.getByResourceId(menuid);
		if(null != SystemResource){
			solutionid = SystemResource.getQuerysql();
		}
		
		map.put("flowdraftid", flowdraftid);
		map.put("flowdraftids", flowdraftids);
		map.put("solutionid", solutionid);
		map.put("menuid", menuid);
		map.put("duixiandate", duixiandate);
		
		// 获取用户
		SystemUser user = getLoginUser(request);
		
		//调用业务层方法，并返回结果
		message = commonFormService.updateDate(map,user);
		
		return message;

	}
	
	/**
	 * 描述：更新撤销时间、原因
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateChexiao.page")
	@ResponseBody
	public ModelAndView updateChexiao(HttpServletRequest request){
//		JSONMessage message;
		//创建参数Map
		Map<String,Object> map = this.parseParamMap(request);
		
		String flowdraftids = request.getParameter("flowdraftids");
		String flowdraftid = flowdraftids;
		String menuid = request.getParameter("menuid");
		
		//通过menuid获取solutionid
		String solutionid = "";
		SystemResource SystemResource = systemResourceService.getByResourceId(menuid);
		if(null != SystemResource){
			solutionid = SystemResource.getQuerysql();
		}
		
		map.put("flowdraftid", flowdraftid);
		map.put("flowdraftids", flowdraftids);
		map.put("solutionid", solutionid);
		map.put("menuid", menuid);
		
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/common/editCommon.jsp");
		mav = new ModelAndView(view,"record",map);
		return mav;
		
//		return message;

	}
	
//	/**
//	 * 监狱调取省局services
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/caseDataExchange_sx.json")
//	@ResponseBody
//	public Object caseDataExchangesx (HttpServletRequest request){
//		dbmsDatasChemeNewService.callProcedures();
//		SystemUser user = getLoginUser(request);
//		String departid = user.getDepartid();
//		Map<String,Object> map = new HashMap<String,Object>();
//		//根据部门编号查询services地址及端口信息
//		Map temp = dbmsDatasChemeNewService.selectServicesByDeparit(departid);
//		
//		if(null!=temp&&!"".equals(temp)){
//			map.put("ddcid", temp.get("ddcid"));
//			map.put("autoruninsertonly", 0);
//			map.put("autoruncondition", "");
//			map.put("port", temp.get("port"));
//			map.put("ip", temp.get("address"));
//			Object result = iWebServiceClient.wsCaseDataExchange_sx(map);
//		}
//		System.out.println("^^操作完成…!");
//		return "1";
//	}
	
//	/**
//	 * 省局调取监狱services
//	 * 
//	 * 省局调取时，先查询省局办理完成的案件，取得罪犯编号，在获取部门编号（监狱id），
//	 * 根据部门来查询services表中对应地址和端口，调取相应监狱的services服务
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/caseDataExchange_sxsj.json")
//	@ResponseBody
//	public Object caseDataExchangesxsj(HttpServletRequest request){
//		dbmsDatasChemeNewService.callProcedures();
//		Map<String,Object> temp = new HashMap<String,Object>();
//		List templist = new ArrayList();
//		List<Map<String,Object>> list=flowBaseService.findUvFlowData();
//		if(list.size()>0&&!StringNumberUtil.isNullOrEmpty(list)){
//			for(int i=0;i<list.size();i++){
//				Map map = list.get(i);
//				String departid = (String)map.get("orgid");
//				templist.add(departid);
//			}
//		}
//		for(int j=0;j<templist.size();j++){
//			Map temp2 = dbmsDatasChemeNewService.selectServicesByDeparit(templist.get(j).toString());
//			if(null!=temp2&&!"".equals(temp2)){
//				temp.put("ddcid", temp2.get("ddcid"));
//				temp.put("autoruninsertonly", 0);
//				temp.put("autoruncondition", "");
//				temp.put("port", temp2.get("port"));
//				temp.put("ip", temp2.get("address"));
//				Object result = iWebServiceClient.wsCaseDataExchange_sxsj(temp);
//			}
//		}
//		return "1";
//	}
	
	
	
	@RequestMapping("/webServiceSender.json")
	@ResponseBody
	public Object webServiceSender(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = this.parseParamMap(request);
		
		return iWebServiceClient.webServiceSender(user, paramap,request);
	}
	
	/**
	 * 描述：区分出流程继续走或直接结束的flowdraftids
	 * @author YangZR	2015-10-16
	 * @return Map{forward: 流程继续走的flowdraftids, 	end: 流程结束的flowdraftids}
	 */
	@RequestMapping("/distinguishFlowForwardOrEnd.json")
	@ResponseBody
	public Object distinguishFlowForwardOrEnd(HttpServletRequest request){
		Map<String,Object> paramap = this.parseParamMap(request);
		return flowBaseService.distinguishFlowForwardOrEnd(paramap);
	}
	
	/**
	 * 描述：区分出报局审核和不报局审核的flowdraftids
	 * @author YangZR	2015-06-24
	 * @return 返回{sendtoprovince: 报局审核的flowdraftids, endinjail: 不报局审核的flowdraftids}
	 */
	@RequestMapping("/distinguishSendToProvince.json")
	@ResponseBody
	public Object distinguishSendToProvince(HttpServletRequest request){
		Map<String,Object> paramap = this.parseParamMap(request);
		String flowdraftids = paramap.get("flowdraftids") ==null?"":paramap.get("flowdraftids").toString();
		if(StringNumberUtil.notEmpty(flowdraftids)){
			if(!flowdraftids.endsWith("'")){
				flowdraftids = StringNumberUtil.formatString(paramap.get("flowdraftids").toString(), ",");
				paramap.put("flowdraftids", flowdraftids);
			}
		}
		return flowBaseService.distinguishSendToProvince(paramap);
	}
	
	/**
	 * 预览导出
	 */
	@RequestMapping(value = "/toYulanExportPage")
	public ModelAndView toYulanExportPage(HttpServletRequest request){
		String reportdata = (String) request.getSession().getAttribute("reportdata");
		RMEngine engine = (RMEngine) request.getSession().getAttribute("engine");
		request.setAttribute("mydata", reportdata);
		request.setAttribute("engine", engine);
		request.setAttribute("reportName", "");
		ModelAndView mav = new ModelAndView("court/commRMreportPrinter");
		return mav;
	}
	
	
	
	/**
	 * 获取用户具有权限的页面顶端按钮
	 * 
	 * @param user
	 * @param menuid
	 * @param type
	 * @return
	 */
	public static String GetTopBtns(SystemUser user, String menuid, String type) {
		SystemResourceService systemResourceService = SpringContextHolder
				.getBean("systemResourceService", SystemResourceService.class);
		String topstr = "";
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid, OAParameter.FORMTOPBUTTON);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				if (type.equals("0"))
					topstr += setBtnscript(res);
				else
					topstr += setBtnscript2(res);
			}
		}
		return topstr;
	}

	public static List<SystemResource> GetResourceByType(SystemUser user,
			String menuid, int resType) {
		SystemResourceService systemResourceService = SpringContextHolder
				.getBean("systemResourceService", SystemResourceService.class);
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid, resType);
		List<SystemResource> temp = new ArrayList<SystemResource>();
		for (SystemResource res : reslist) {
			SystemResource restemp = new SystemResource();
			restemp.setResid(res.getResid());
			restemp.setPresid(res.getPresid());
			restemp.setName(res.getName().replace("\'", "\\'"));
			if (res.getSrurl() != null)
				restemp.setSrurl(res.getSrurl().replace("\'", "\\'"));
			restemp.setFormid(res.getFormid());
			restemp.setIsmenu(res.getIsmenu());
			restemp.setQuerysql(res.getQuerysql());
			restemp.setShowico(res.getShowico());
			temp.add(restemp);
		}
		return temp;
	}

	/**
	 * 获取用户具有权限的tabs页签顶部按钮
	 * 
	 * @param user
	 * @param menuid
	 * @return
	 */
	public static String GetTabsBtns(SystemUser user, String menuid) {
		SystemResourceService systemResourceService = SpringContextHolder
				.getBean("systemResourceService", SystemResourceService.class);
		String topstr = "";
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid, OAParameter.TABSBUTTON);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				topstr += setTabsBtnscript(res);

			}
		}
		return topstr;
	}

	/**
	 * 获取用户具有权限的grid右键菜单
	 * 
	 * @param user
	 * @param menuid
	 * @return
	 */
	public static String GetGridContextMenu(SystemUser user, String menuid) {
		SystemResourceService systemResourceService = SpringContextHolder
				.getBean("systemResourceService", SystemResourceService.class);
		String topstr = "";
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid, OAParameter.GRIDCONTEXTMENU);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				topstr += setGridContextMenuscript(res);
			}
		}
		return topstr;
	}

	/**
	 * 获取用户具有权限的grid列中按钮
	 * 
	 * @param user
	 * @param menuid
	 * @return
	 */
	public static String GetGridColBtns(SystemUser user, String menuid) {
		SystemResourceService systemResourceService = SpringContextHolder
				.getBean("systemResourceService", SystemResourceService.class);
		String topstr = "";
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid, OAParameter.GRIDBUTTON);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				topstr += setGridGridColBtnsscript(res);
			}
		}
		return topstr;
	}

	private static String setGridGridColBtnsscript(SystemResource res) {
		String btnStr = "";
		String url = res.getSrurl();
		String name = res.getName();
		String resid = res.getResid();
		String ico = res.getShowico();
		String title = res.getTitle();
		if (title == null)
			title = "";
		if (url == null)
			url = "";
		if (ico == null)
			ico = "";
		if (name.contains("_")) {
			name = name.split("_")[0];
		}
		url = url.replace("\'", "\\'");
		String format = "<a data-original-title=\"%s\" id=\"%s\" class=\"%s\" title=\"%s\" href=\"%s\"></a>&nbsp;";
		btnStr = String.format(format, name, resid, ico, name, url);
		return btnStr;
	}

	private static String setGridContextMenuscript(SystemResource res) {
		String btnStr = "";
		String url = res.getSrurl();
		String name = res.getName();
		// String resid = res.getResid();
		String ico = res.getShowico();
		String title = res.getTitle();
		if (title == null)
			title = "";
		if (url == null)
			url = "";
		if (ico == null)
			ico = "";
		if (name.contains("_")) {
			name = name.split("_")[0];
		}

		String format = "<li name=\"%s\" iconCls=\"%s\" onclick=\"%s\" style=\"cursor: pointer;\">%s</li>";

		if (name.equals("mseparator")) {
			btnStr = "<li class=\"separator\" name=\"mseparator\" ></li>";
		} else {
			btnStr = String.format(format, name, ico, url, title);
		}
		return btnStr;
	}

	private static String setTabsBtnscript(SystemResource res) {
		String btnStr = "";
		String url = res.getSrurl();
		String name = res.getName();
		String resid = res.getResid();
		String ico = res.getShowico();
		if (ico == null)
			ico = "";
		if (name.contains("_")) {
			name = name.split("_")[0];
		}
		String format = "<li onclick=\"%s\"><a id=\"%s\"><i class=\"%s\"></i> &nbsp;%s</a></li>";
		btnStr = String.format(format, url, resid, ico, name);
		return btnStr;
	}

	private static String setBtnscript2(SystemResource res) {
		String className = res.getOpenico();
		String btnStr = "";
		String url = res.getSrurl();
		String name = res.getName();
		String resid = res.getResid();
		String ico = res.getShowico();
		if (className == null)
			className = "";
		if (ico == null)
			ico = "";
		if (name.contains("_")) {
			name = name.split("_")[0];
		}
		String format = "<BUTTON class=\"%s\" style=\"margin-right:3px\" id=\"%s\" onClick=\"%s\"><i class=\"%s\"></i> %s</BUTTON>";
		btnStr = String.format(format, className, resid, url, ico, name);
		return btnStr;
	}

	private static String setBtnscript(SystemResource res) {
		String btnStr = "";
		String url = res.getSrurl();
		String name = res.getName();
		String resid = res.getResid();
		if (name.contains("_")) {
			name = name.split("_")[0];
		}
		String ico = "";
		if (res.getShowico() != null) {
			ico = res.getShowico();
			btnStr += "<a class=\"mini-button\" id=\"" + resid
					+ "\" iconCls=\"" + ico + "\" plain=\"true\" onclick=\""
					+ url + "\">" + name + "</a> ";
		} else {
			btnStr += "<a class=\"mini-button\" id=\"" + resid
					+ "\" iconCls=\"" + ico + "\" plain=\"false\" onclick=\""
					+ url + "\">" + name + "</a> ";
		}
		return btnStr;
	}
	//end add by blue_lv 2015-07-14
	
	
	
	
	
	@RequestMapping(value = "/toJusticeDocumentsPage.page")
	public ModelAndView toJusticeDocumentsPage(HttpServletRequest request) {
		String courttype = request.getParameter("courttype"); 
		String flowdefid = request.getParameter("flowdefid"); 
		String tempid = request.getParameter("tempid"); 
		String resid = request.getParameter("resid"); 

		request.setAttribute("courttype", courttype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("resid", resid);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/common/justiceDocuments.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	@RequestMapping(value = "/toSuggestionDocument.page")
	public ModelAndView toSuggestionDocument(HttpServletRequest request) {
		
		returnResourceMap(request);
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		
		SystemUser su = getLoginUser(request);
//		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String casetype = request.getParameter("casetype");
		String menuid = request.getParameter("menuid");
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		
//		String casetype="";
		
//		Map paraMap = new HashMap();
//		paraMap.put("casetype", casetype);
//		Map temMap =  systemCodeService.getDataByMap(paraMap);
//		if(null!=temMap){
//			casetype = temMap.get("name")==null?"":temMap.get("name").toString();
//		}
		
		
//		request.setAttribute("courttype", courttype);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
        String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
        request.setAttribute("provincecode", provincecode);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("casetype", casetype);
		request.setAttribute("shortname", shortname);
		request.setAttribute("curyear", curyear);
		request.setAttribute("flowdefid", flowdefid);
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/common/suggestionDocument.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	
	@RequestMapping(value = "/getReportData.json")
	@ResponseBody
	public Object getReportData(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramMap = this.parseParamMap(request);
		//
//		if(StringNumberUtil.isEmpty(paramMap.get("flowdefid"))){
//			paramMap.put("flowdefid", GkzxCommon.DEFAULT_FLOWDEFID);
//		}
		//menuid为预览按钮传的菜单id，该menuid与报表关联，同时该menuid对应的资源存了solutionid
		String menuid = request.getParameter("menuid");
		SystemResource systemResource = systemResourceService.getByResourceId(menuid);
		String solutionid = systemResource.getQuerysql();
		paramMap.put("solutionid", solutionid);
		//
		
		if(StringNumberUtil.notEmpty(paramMap.get("flowdraftid")) && StringNumberUtil.isEmpty(paramMap.get("applyid"))){
			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
			paramMap.put("applyid", fb.getApplyid());
		}
		
		Map<String,Object> resultMap = solutionService.query(paramMap, user);
		RMEngine engine=this.systemResourceService.getReportEngin(paramMap, user, resultMap);
		//
		String reportPath=request.getRealPath("").replace("\\", "/")+"/RMreportReport/";
		String reportName = systemResourceService.getReportNameByMenuid(menuid);
		engine.SetReportFile(reportPath + reportName + ".rmf", 1);
		//
		String reportdata = engine.dedaoReportData();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reportdata", reportdata);//用于导出预览
		map.put("engine", engine);//用于导出预览
		setSessionAttribute4Report(request, map);
		
		return reportdata;
	}
	
	
	
	@RequestMapping(value = "/filterFlowdraftidOfMakedPage.json")
	@ResponseBody
	public Object filterFlowdraftidOfMakedPage(HttpServletRequest request){
		
		//flowdraftids:flowdraftids,tempid:tempid
		Map<String,Object> paramap = this.parseParamMap(request);
		Object result = flowOtherFlowService.filterFlowdraftidOfMakedPage(paramap);
		return result.toString();
		
	}
	
	/*
	 * 跳转到历史会议记录页面
	 * yanqutai
	 */
	@RequestMapping(value = "/toMeetListPage.page")
	public ModelAndView toMeetListPage(HttpServletRequest request) {
		assemblePublicListPageData(request);
		//
		return new ModelAndView("meeting/meetListPage");
		
	}
	
	
	/**
	 * 狱政管理工作月报表
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("/ajaxSaveYZGLnDoc")
	@ResponseBody
	public Object addYZGLData(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Map<String,Object> map = super.parseParamMap(request);
		map.put("username", user.getName());
		map.put("userid", user.getUserid());
		map.put("departid", user.getDepartid());
		String operateType = map.get("operateType").toString();
		ArrayList tempData = (ArrayList)jsonTool.Decode(map.get("noteinfo").toString()); //获取表单节点数据
		specialService.addYZGLData(tempData, map);
		
		return null;
		
		
	}
	
	/**
	 * 跳转报备立案页面（省局）
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toBaobeiCaseLiAnPage.page")
	public ModelAndView toBaobeiCaseLiAnPage(HttpServletRequest request) {
		
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String casetype = "";
		if(StringNumberUtil.notEmpty(flowdefid)){
			flowdefid = flowdefid.trim();
			
			casetype = "刑备字";
		
		}
		
		Map paramMap = new HashMap();
		paramMap.put("departid", departid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("curyear", curyear);
		String casenumber = flowBaseService.getSJMaxCaseNumForGD(paramMap);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("casetype", casetype);
		//
		assemblePublicListPageData(request);
		//
		return new ModelAndView("common/caseLiAnPage");
		
	}
	/*
	 * 跳转减刑假释公示记录页面
	 * 
	 */
	@RequestMapping(value = "/publicityListPage.page")
	public ModelAndView toPublicityListPage(HttpServletRequest request) {
		assemblePublicListPageData(request);
        String pageName = request.getParameter("pageName");
		return new ModelAndView("meeting/"+pageName);
		
	}
	
	
	
	/**
	 * 查询流程被锁定的案件
	 * @param request
	 * @return
	 */
	@RequestMapping("/validateFlowCondition.json")
	@ResponseBody
	public Object validateFlowCondition(HttpServletRequest request){
//		SystemUser user = getLoginUser(request);
		String flowdraftids = request.getParameter("flowdraftids");
		String locktype = request.getParameter("locktype");
		if(StringNumberUtil.notEmpty(flowdraftids) && StringNumberUtil.notEmpty(locktype)){
			flowdraftids = StringNumberUtil.formatString(flowdraftids.trim(), ",");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowdraftids", flowdraftids);
			map.put("flowlocked", "1");//
			map.put("locktype", locktype);
			return flowBaseService.validateFlowCondition(map);
		}
		
		return null;
		
	}
	
	
	/**
	 * 描述：查询罪犯所在部门级别：分监区或是直属分监区
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrgLevelByFlowdraftid.json")
	@ResponseBody
	public Object getOrgLevelByFlowdraftid(HttpServletRequest request){
		Object result = null;
		Map<String,Object> paramap = this.parseParamMap(request);
		result = flowBaseService.getOrgLevelByFlowdraftid(paramap);
		return result;
	}
	
	/**
	 * 根据案件类型选择省局承办人
	 */
	@RequestMapping("/CaseTypeReturn.json")
	@ResponseBody
	public List<Map> CaseTypeReturn(HttpServletRequest request){
		
		List<Map> data = new ArrayList<Map> ();
		data=flowBaseService.getCaseTypeFromShengju();
		return data;
	}
	
	
	/**
	 * 描述：区分出在省局处长暂停流程和继续走流程的flowdraftids
	 * @author YangZR	2015-06-24
	 * @return 返回{keepGoing: 无期死缓继续走流程的flowdraftids, endinSJCZ:重要罪犯三类犯在省局处长暂停流程的flowdraftids}
	 */
	@RequestMapping("/distinguishFinishAtSJCZ.json")
	@ResponseBody
	public Object distinguishFinishAtSJCZ(HttpServletRequest request){
		Map<String,Object> paramap = this.parseParamMap(request);
		String flowdraftids = paramap.get("flowdraftids") ==null?"":paramap.get("flowdraftids").toString();
		if(StringNumberUtil.notEmpty(flowdraftids)){
			if(!flowdraftids.endsWith("'")){
				flowdraftids = StringNumberUtil.formatString(paramap.get("flowdraftids").toString(), ",");
				paramap.put("flowdraftids", flowdraftids);
			}
		}
		return flowBaseService.distinguishFinishAtSJCZ(paramap);
	}
	
}