package com.sinog2c.mvc.controller.khjc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.system.SystemOrganizationMapper;
import com.sinog2c.dao.api.system.SystemRoleMapper;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseDocService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.KhjcTbflowBaseDocService;
import com.sinog2c.service.api.khjc.KhjcTbflowBaseDocSlaveService;
import com.sinog2c.service.api.khjc.PublicBaseDocService;
import com.sinog2c.service.api.khjc.PublicCrimInfoService;
import com.sinog2c.service.api.khjc.PublicOrgInfoService;
import com.sinog2c.service.api.khjc.PublicToolMethodService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 考核奖惩相关
 * @author yanqutai
 */
@Controller
public class KhjcPublicController extends ControllerBase {
	@Autowired
	private KhjcTbflowBaseDocService khjcTbflowBaseDocService;
	@Autowired
	private SystemRoleMapper systemRoleMapper;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PublicOrgInfoService publicOrgInfoService;
	@Autowired
	private KhjcPublicService khjcPublicService;
	@Autowired
	private PublicToolMethodService publicToolMethodService;
	@Autowired
	private PublicBaseDocService publicBaseDocService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private KhjcCriminalScoreService khjcCriminalService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PublicCrimInfoService publicCrimInfoService;
	@Autowired
	private KhjcTbflowBaseDocSlaveService khjcTbflowBaseDocSlaveService;
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired(required=true)
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemOrganizationMapper organizationMapper;
	
	//批量操作(提交，退回，拒绝)
	@RequestMapping(value = "/khjcPiLiangCaoZuo")
	@ResponseBody
	public Object khjcPiLiangCaoZuo(HttpServletRequest request){
		//批量操作成功返回success
		String result = khjcPublicService.piLiangCaoZuo(request);
		return result;
	}
	
	
	//根据docid获取附件表大字段
	@RequestMapping(value = "/getDocContentByDocid")
	@ResponseBody
	public Map getDocContentByDocid(HttpServletRequest request){
		String docid = request.getParameter("docid");
		Map map = new HashMap();
		map.put("doccontent",publicBaseDocService.getDocContentByDocid(docid));
		return map;
	}
	
	//根据singaid获取签章方案
	@RequestMapping(value = "/getSingaBySingaid")
	@ResponseBody
	public Map getSingaBySingaid(HttpServletRequest request){
		String singaid = request.getParameter("singaid");
		Map map = new HashMap();
		map.put("signatureinfor",publicToolMethodService.getSingaBySingaid(singaid));
		return map;
	}
	
	
	//根据singaid获取签章方案
	@RequestMapping(value = "/updateDocByDocid")
	@ResponseBody
	public String updateDocByDocid(HttpServletRequest request){
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String doccontent = request.getParameter("doccontent")==null?"":request.getParameter("doccontent");
		SystemUser user = getLoginUser(request);
		String returnValue = publicBaseDocService.updateKhjcFlowBaseDocContentBydocid(docid, doccontent, user);
		return returnValue;
	}
	
	/*
	 * 根据代码类型查找相应的代码
	 */
	@RequestMapping("/ajaxKhjcGetCode.json")
	@ResponseBody
	public Object ajaxKhjcGetCode(HttpServletRequest request){
		return publicToolMethodService.ajaxKhjcGetCode(request);
	}
	
	/**
	 * 批量审批
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toKhjcPiLiangShenPiTabs")
	public ModelAndView toKhjcPiLiangShenPiTabs(HttpServletRequest request) {
		//crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+findid;
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String flowdefid = request.getParameter("flowdefid");
		String tempid=  request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String provinceid=  request.getParameter("provinceid");
		String nodeid = request.getParameter("nodeid");
		String days = request.getParameter("days");
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		
		//如果是山西 或者宁夏的 进行另外一个打开审核表的类中
		String shanxi = jyconfig.getProperty(GkzxCommon.SHAN_XI)==null ?"":jyconfig.getProperty(GkzxCommon.SHAN_XI);
		if (shanxi.contains(getLoginUser(request).getDepartid())) {
			request.setAttribute("shanxi", "1");
		}
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		if(StringNumberUtil.notEmpty(id)){//如果罪犯编号等信息不为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];//罪犯编号就从数组里取出第一个罪犯编号
			orgid = temArr[1];
			flowdraftid = temArr[2];
			flowid = temArr[3];
			if(!StringNumberUtil.notEmpty(flowid)||"flowidnull".equals(flowid.trim())||"undefined".equals(flowid.trim())){
				flowid = "";
			}
			
			lastnodeid = temArr[4];
		}
		
		ModelAndView mav = null;
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("closetype", closetype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("ischeckseal", ischeckseal);
		if(StringNumberUtil.notEmpty(provinceid)){
			request.setAttribute("provinceid", provinceid);
		}
		
		if(StringNumberUtil.notEmpty(nodeid)){
			request.setAttribute("nodeid", nodeid);
		}
		
		if(StringNumberUtil.notEmpty(days)){
			request.setAttribute("days", days);
		}
		View view = null;
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String userdepartid = user.getDepartid();
		view = new InternalResourceView("WEB-INF/JSP/aip/kaoHeJiangChengShenPi.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * 根据用户所处部门级别获取部门信息（选择监区）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getShenPiTempletIdList" })
	@ResponseBody
	public Object getShenPiTempletIdList(HttpServletRequest request, HttpServletResponse response) {
		List list2 = new ArrayList();	
//		List<KhjcTbflowBaseDoc> list = khjcCriminalService.getCriminalKhjcBaseDocByNodeid(pageIndex, pageSize, key, tempid, crimid ,null, sortField, sortOrder,nodeid);
		return list2;
	}
	
	/**
	 * 跳转查询页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/publicBaseDocPage")
	public ModelAndView publicBaseDocPage(HttpServletRequest request){
		returnResourceMap(request);
		String templetid = request.getParameter("templetid")==null?"":request.getParameter("templetid");
		String departid = request.getParameter("departid")==null?"":request.getParameter("departid");
		request.setAttribute("templetid", templetid);
		request.setAttribute("departid", departid);
		return new ModelAndView("khjc/publicBaseDocPage");
	}
	
	
	/**
	 * 根据templetid查询相关的附件信息
	 * yanqutai
	 * @author 
	 */
	@RequestMapping(value="/getBaseDocByTempletID")
	@ResponseBody
	public HashMap<String, Object> getBaseDocByTempletID(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map paraMap = new HashMap();
		
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");//排序字段
		String sortOrder = request.getParameter("sortOrder");//排序方式
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		
		String applyid = request.getParameter("applyid");//罪犯编号
		if(StringNumberUtil.notEmpty(applyid)){
			paraMap.put("applyid", applyid);
		}
		
		String flowdefid = request.getParameter("flowdefid");//流程定义ID
		if(StringNumberUtil.notEmpty(flowdefid)){
			paraMap.put("flowdefid", flowdefid);
		}
		
		String tempid = request.getParameter("tempid");//表单ID
		if(StringNumberUtil.notEmpty(tempid)){
			paraMap.put("tempid", tempid);
		}
		
		String key = request.getParameter("key");//其他条件参数
		if(StringNumberUtil.notEmpty(key)){
			paraMap.put("key", key);
		}
		
		List<Map> list = publicBaseDocService.getBaseDocByCondition(paraMap);
		int count = publicBaseDocService.countBaseDocByCondition(paraMap);
		
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 跳转罪犯考核奖惩相关的表单的新增页面
	 * @author yanqutai
	 * @return ModelAndView
	 */
	@RequestMapping(value="baseDocContentAdd.page")
	public ModelAndView baseDocContentAdd(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String templetid = request.getParameter("templetid");
		String crimid = request.getParameter("crimid");//罪犯编号
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(templetid, deptid);
		Map<String,Object> map = new HashMap<String,Object>();
		//有罪犯编号就取罪犯相关的所有信息
		if(StringNumberUtil.notEmpty(crimid)){
			map = publicCrimInfoService.getCriminalBasicCrimeInfo(map, crimid);
			map = publicCrimInfoService.getCriminalBasicInfo(map, crimid);
		}
		if (template != null){
			docconent.add(template.getContent());
		}
		request.setAttribute("templetid", templetid);
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		
		return new ModelAndView("aip/khjc/baseDocContentAdd1");
	}
	
	
	/**
	 * 跳转罪犯考核奖惩相关的表单的新增页面
	 * @author yanqutai
	 * @return ModelAndView
	 */
	@RequestMapping(value="choosePermission")
	public ModelAndView choosePermission(HttpServletRequest request){
		request.setAttribute("permissionid", request.getParameter("permissionid"));
		return new ModelAndView("khjc/choosePermission");
	}
	
	
	/*
	 * 查询所有的角色
	 */
	@RequestMapping("/ajaxGetPermissionName")
	@ResponseBody
	public Object ajaxGetPermissionName(HttpServletRequest request){
		return systemRoleMapper.selectAll();
	}
	
	/**
	 * 查看、修改罪犯调查评估委托函时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value="khjcFlowBaseDocLook")
	public ModelAndView khjcFlowBaseDocLook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		KhjcTbflowBaseDoc document = khjcTbflowBaseDocService.selectByPrimaryKey(docid);
		if (document != null) {
			docconent.add(document.getDocconent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/khjc/zhuanXiangJiangFenAdd");
	}
	
	/**
	 * 跳转表单的新增页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="baseDocAdd.page")
	public ModelAndView baseDocAdd(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String templetid = request.getParameter("templetid");
		String crimid = request.getParameter("crimid");//罪犯编号
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(templetid, deptid);
		Map<String,Object> map = new HashMap<String,Object>();
		//有罪犯编号就取罪犯相关的所有信息
		if(StringNumberUtil.notEmpty(crimid)){
			map = publicCrimInfoService.getCriminalBasicCrimeInfo(map, crimid);
			map = publicCrimInfoService.getCriminalBasicInfo(map, crimid);
		}
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("templetid", templetid);
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		
		
		return new ModelAndView("aip/khjc/baseDocAdd");
	}
	
	
	
	/**
	 * 跳转公共罪犯选择页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/criminalChoosePageForSD")
	public ModelAndView criminalChoosePageForSD(HttpServletRequest request){
		returnResourceMap(request);
		String templetid = request.getParameter("templetid");
		request.setAttribute("templetid",templetid);
		//查询罪犯的条件，choosecriminal (all.所有在押 userdep.当前用户所在部门 3.特殊部门)
		request.setAttribute("choosecriminal", request.getParameter("choosecriminal"));
		//控制选择完罪犯跳转页面的状态值
		request.setAttribute("state", request.getParameter("state"));
		return new ModelAndView("khjc/criminalChoosePageForSD");
	}
	
	
	/**
	 * 跳转考核奖惩罪犯专项奖分页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/publicCriminalBaseDoc")
	public ModelAndView publicCriminalBaseDoc(HttpServletRequest request){
		
		
		String ids = request.getParameter("ids");
		String indexFlag = request.getParameter("indexFlag");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String optype = request.getParameter("optype");
		String menuid = request.getParameter("menuid");
		
		if(StringNumberUtil.notEmpty(ids)){
			String[] idArr = ids.split(",");
			int index = 0;
			if(StringNumberUtil.notEmpty(indexFlag)){
				index = Integer.parseInt(indexFlag);
			}
			if(StringNumberUtil.notEmpty(idArr[index])){
				String[] idTemp = idArr[index].split("@");
				if("add".equals(optype)){
					String applyid = idTemp[0];
					request.setAttribute("applyid", idTemp[0]);
					request.setAttribute("applyname", idTemp[1]);
				}else{
					request.setAttribute("flowdraftid", idTemp[0]);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("flowdraftid", idTemp[0]);
					FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(map);
					request.setAttribute("applyid", fb.getApplyid());
					request.setAttribute("applyname", fb.getApplyname());
				}
			}
		}
		
		
		// 获取用户
		SystemUser user = getLoginUser(request);
		// 
		if(StringNumberUtil.isEmpty(flowdefid)){
			flowdefid = GkzxCommon.DEFAULT_FLOWDEFID;//默认系统流程
		}
		
		// 将所有参数封装为JS字符串
		Map<String,String> paraMap = parseParamMapString(request);
//		Object jsonParamObject = JSONObject.toJSON(jsParaMap);
		request.setAttribute("paraMap", paraMap);

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
		
		//
		returnResourceMap(request);
		
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("flowdefid",flowdefid);
		return new ModelAndView("khjc/publicCriminalBaseDoc");
	}
	
	
	/**
	 * 获取部门信息(根据部门和级别)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxGetOrginfoByLevel" })
	@ResponseBody
	public Object ajaxGetOrginfoByLevel(HttpServletRequest request, HttpServletResponse response) {
			String unitlevel=request.getParameter("unitlevel");
			SystemUser user = getLoginUser(request);
			List<Map<String, Object>> list=organizationMapper.getOrgMapByLevel(unitlevel,user.getDepartid());
			List<Map> list2 = new ArrayList<Map>();
			for(int i=0;i<list.size();i++){
				Map maps=(Map)list.get(i);
				Map m=new HashMap();
				m.put("name", maps.get("NAME"));
				m.put("orgid", maps.get("ORGID"));
				list2.add(m);
			}
			return list2;
	}
}
