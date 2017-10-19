package com.sinog2c.mvc.controller.system;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.ZpublicDaMtxx;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.PrintBatchService;
import com.sinog2c.service.api.system.SealAndNotationService;
import com.sinog2c.service.api.system.SignatureFlowService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbViewMaintainService;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


@Controller
@RequestMapping("/sign")
public class SignatureSchemeController extends ControllerBase{
	@Resource
	SignatureSchemeService signService;
	@Resource
	SystemModelService templateService;
	@Resource
	public SystemLogService logService;
	@Resource
	FlowDeliverMapper flowDeliverMapper;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbViewMaintainService tbViewMaintainService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private SignatureSchemeService signatureSchemeService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private SealAndNotationService sealAndNotationService;
	@Resource
	private PrintBatchService printBatchService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private SignatureFlowService signatureFlowService;
	
	
	/**
	 * 显示签章方案
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage.page")
	public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/sign/signmanage");

		return mav;
	}
	@RequestMapping("/addsign.page")
	public ModelAndView addsign(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = null;
		SystemUser user = getLoginUser(request);
		String lv = user.getOrganization().getUnitlevel();
		if(lv!=null && ("6".equals(lv) || "7".equals(lv))){//法院的
			mav = new ModelAndView("system/sign/courtSignEdit");
		}else{
			mav = new ModelAndView("system/sign/signedit");
		}
		return mav;
	}
	@RequestMapping("/editsign.page")
	public ModelAndView editsign(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = null;
		SystemUser user = getLoginUser(request);
		String lv = user.getOrganization().getUnitlevel();
		if(lv!=null && ("6".equals(lv) || "7".equals(lv))){//法院的
			mav = new ModelAndView("system/sign/courtSignEdit");
		}else{
			mav = new ModelAndView("system/sign/signedit");
		}
		return mav;
	}
	@RequestMapping(value = "/getsigntype.page")
	public ModelAndView getsigntypePage(HttpServletRequest request, HttpServletResponse response) {
		//
		ModelAndView mav = new ModelAndView("system/sign/showTemplateTree");
		return mav;
	}
	/**
	 * 方法描述:进入 选择权限的页面
	 * @return
	 */
	@RequestMapping(value = "/getShowSourcesContent.page")
	public ModelAndView getShowSourcesContent(){
		ModelAndView mav = new ModelAndView("system/sign/showSourcesContent");

		return mav;
	}
	
	@RequestMapping(value = "/getSignatureSchemeById.json")
	@ResponseBody
	public Object getSignatureSchemeById(HttpServletRequest request){
		return null;
	}
	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listSignatureSchemeByPage(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("key");
		String orgid  = request.getParameter("orgid");//如果选择部门时获取orgid，选择表单时获取orgid@tempid
		String tempid = "";
		if(!StringNumberUtil.isNullOrEmpty(orgid) && orgid.contains("@")){
			String[] orgids = orgid.split("@");
			orgid = orgids[0];
			tempid = orgids[1];
		}
		String pageIndexStr = request.getParameter("pageIndex");// 页码, 0 开始
		String pageSizeStr = request.getParameter("pageSize");// 每页显示条数
		String sortField = request.getParameter("sortField");// 排序列名
		String sortOrder = request.getParameter("sortOrder");// asc, desc
		int pageIndex = StringNumberUtil.parseInt(pageIndexStr, 0);
		int pageSize = StringNumberUtil.parseInt(pageSizeStr, 20);
		
		SystemUser user = getLoginUser(request);
		if(null == user){
			return null;
		}
		SystemOrganization org = user.getOrganization();
		if(null == org){
			return null;
		}
		
		
		int total = signService.countAll(key,orgid,tempid);
		List<SignatureScheme> schemes = signService.listAllByPage(pageIndex, pageSize, sortField, sortOrder,key,orgid,tempid);// 取得所有数据
		
		JSONMessage message = JSONMessage.newMessage();
		message.setSuccess();
		message.setTotal(total); 
		message.setData(schemes);
		
		return message;
	}
	/**
	 * 获取签章类型,列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getsigntype.json")
	@ResponseBody
	public Object getSignType(HttpServletRequest request, HttpServletResponse response) {
		//
		List<TbsysTemplate> list = templateService.listAll();
		//
		int total = list.size();
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setSuccess();
		message.setTotal(total);
		message.setData(list);
		
		return message.getData(); 
	}
	/**
	 * 获取签章类型,列表 法院用
	 * liuyi
	 */
	@RequestMapping(value = "/ajax/getCourtsigntype.json")
	@ResponseBody
	public Object getCourtsigntype(HttpServletRequest request, HttpServletResponse response) {
		JSONMessage message = JSONMessage.newMessage();
		SystemUser user = getLoginUser(request);
		List<Map<String,Object>> list = flowDeliverMapper.getFlowDeliverTreeByDepartid(user.getDepartid());
		list = MapUtil.convertKeyList2LowerCase(list);
		int total = list.size();
		message.setSuccess();
		message.setTotal(total);
		message.setData(list);
		return message.getData(); 
	}
	/**
	 * 方法描述：获取所有的资源内容
	 * @version 2014年9月11日17:12:42
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getResourceMapObject.json")
	@ResponseBody
	public Object getResourceMapObject(){
		List<Map> listMaps = templateService.getResourceMapObject();
		
		listMaps = MapUtil.turnKeyToLowerCaseOfList(listMaps);
		int total = listMaps.size();
		
		JSONMessage message = JSONMessage.newMessage();
		message.setSuccess();
		message.setTotal(total);
		message.setData(listMaps);
		return listMaps;
	}
	
	@RequestMapping(value = "/ajax/select.json")
	@ResponseBody
	public Object selectSignatureScheme(HttpServletRequest request) {
		//查询所选 的签章方案信息
		String id = request.getParameter("signatureid");
		Map  signSchemeMap =  signService.getMapById(Integer.parseInt(id));
		//判断是否是监狱长的签章方案，所以数据库中签章方案的字段name要规范
		if(signSchemeMap !=null){
			if( StringNumberUtil.notEmpty(signSchemeMap.get("text2"))){
				signSchemeMap.put("otherSealFlowdefid", signSchemeMap.get("text2"));
			}
		}
		
		String casenums = request.getParameter("casenums");
		//----------------------------------------------------------------------------------------------
		Object isnotcheckprogressObj = signSchemeMap.get("isnotcheckprogress");
		if(StringNumberUtil.notEmpty(isnotcheckprogressObj)&&isnotcheckprogressObj.toString().equals("0")){
			
			//根据当前所提供的案件号，查询出是否存在大字段的案件号
			String year = request.getParameter("caseyear");
			String casetype = request.getParameter("casetype");
			casenums = StringNumberUtil.formatCaseNo(casenums,year);
			List<String> preCasenumsList = StringNumberUtil.formatString2List(casenums, ",");
			
			
			SystemUser user = getLoginUser(request);
			String departid = user.getDepartid();
			
			Map paramap = new HashMap();
			paramap.put("casenums", casenums);
			paramap.put("flowdefid", signSchemeMap.get("flowdefid"));
			paramap.put("tempid", signSchemeMap.get("tempid"));
			paramap.put("departid", departid);
			if(StringNumberUtil.notEmpty(casetype)){
				if(casetype.equals("jx")){
					paramap.put("casetype", GkzxCommon.CASE_TYPE_JXJS);
				}else if(casetype.equals("js")){
					paramap.put("casetype", GkzxCommon.CASE_TYPE_JS);
				}else if(casetype.equals("cbz")){
					paramap.put("casetype", GkzxCommon.CASE_TYPE_CBZ);
				}else if(casetype.equals("xbz")){
					paramap.put("casetype", GkzxCommon.CASE_TYPE_XBZ);
				}
			}
			//存在大字段的案件号
			List<Map> casenumList = flowBaseOtherService.selectCpdnumbersByCpdnumber(paramap);
			
			//将[{casenums:abc},{casenums:edf},..]转化为[abc,edf,...],同时得到abc,edf,...的字符串
			casenums = "";
			List<String> fboCasenumList = new ArrayList<String>();
			if(null!=casenumList&&casenumList.size()>0){
				for(Map map:casenumList){
					String casenum = map.get("casenums").toString();
					fboCasenumList.add(casenum);
					casenums += casenum+",";
				}
				casenums = casenums.substring(0, casenums.length()-1);//得到存在大字段的案件号
			}
			paramap.put("casenums", casenums);
			
			//preCasenumsList 中去掉那些存在的案件号，得到不存在的案件号
			for (int i = 0; i < fboCasenumList.size(); i++) {
				 if(preCasenumsList.containsAll(fboCasenumList)){
					 preCasenumsList.removeAll(fboCasenumList);
				 }
			}
			
			//将不存在的案件号传回页面
			if(preCasenumsList.size()>0){
				String notExitCasenums = "";
				for(String temp : preCasenumsList){
					notExitCasenums += temp.substring(4)+",";
				}
				if(notExitCasenums.indexOf(",")>-1){
					notExitCasenums = notExitCasenums.substring(0,notExitCasenums.length()-1);
					signSchemeMap.put("notExitCasenums", notExitCasenums);
				}
			}
			
			//根据当前所提供的案件号，查询出是否是当前审批人审批的案件号
			SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
			paramap.put("provinceid", so.getPorgid());
			Map condi = tbViewMaintainService.selectByCondition(paramap);
			if(null!=condi){
				//provinceid和nodeid用于当某个省份的监狱减刑假释审批是，某一级节点的一些特殊处理时传的参数
				Object provinceid = condi.get("provinceid");
				Object flowdefidObj = condi.get("flowdefid");
				Object nodeid = condi.get("nodeid");
				Object days = condi.get("days");
				if(StringNumberUtil.notEmpty(provinceid)&&StringNumberUtil.notEmpty(nodeid)&&StringNumberUtil.notEmpty(days)){
					paramap.put("provinceid", provinceid);
					paramap.put("nodeid", nodeid);
					paramap.put("days", days);
					paramap.put("flowdefidConn", flowdefidObj);
		    	}
			}
			
			paramap.put("suid",user.getUserid());
			String orgid = user.getOrganization().getOrgid();
			paramap.put("orgid", orgid);
			//获取该用户拥有的按钮资源id
			Object buttonstr = this.returnButtonResourceByUser(user,paramap.get("flowdefid").toString());
			paramap.put("connsql", buttonstr);
			//当前审批人审批的案件。
			List<Map> currApprovalCasenumMapList = null;
			if(StringNumberUtil.notEmpty(casenums)){
				currApprovalCasenumMapList = uvFlowService.getCasenumsOfCommuteByCondition(paramap);
			}
	    	// 得到当前审批人审批的案件List,及字符串,并将其返回页面
			if(null!=currApprovalCasenumMapList&&currApprovalCasenumMapList.size()>0){
				List<String> currApprovalCasenumList = new ArrayList<String>();
				String currApprovalCasenums = "";
				for(Map map : currApprovalCasenumMapList){
					String casenum = map.get("casenums").toString();
					currApprovalCasenumList.add(casenum);
					currApprovalCasenums += casenum.substring(4) + ",";
				}
				if(currApprovalCasenums.indexOf(",")>-1){
					currApprovalCasenums = currApprovalCasenums.substring(0,currApprovalCasenums.length()-1);
					signSchemeMap.put("currApprovalCasenums", currApprovalCasenums);
				}
				
				//得到那些存在大字段但不是当前审批人审批的案件号
				if(fboCasenumList.size()>0&&currApprovalCasenumList.size()>0){
					for (int i = 0; i < currApprovalCasenumList.size(); i++) {
						 if(fboCasenumList.containsAll(currApprovalCasenumList)){
							 fboCasenumList.removeAll(currApprovalCasenumList);
						 }
					}
					
					if(fboCasenumList.size()>0){
						String notCurrApprovalCasenums = "";
						for(String temp : fboCasenumList){
							notCurrApprovalCasenums += temp.substring(4)+",";
						}
						if(notCurrApprovalCasenums.indexOf(",")>-1){
							notCurrApprovalCasenums = notCurrApprovalCasenums.substring(0,notCurrApprovalCasenums.length()-1);
							signSchemeMap.put("notCurrApprovalCasenums", notCurrApprovalCasenums);
						}
					}
				}
			}else{
				signSchemeMap.put("notCurrApprovalCasenums", casenums);
			}
		}else{
			signSchemeMap.put("currApprovalCasenums", casenums);
		}
		
		//---------------------------------------------------------------------------------------------
		return signSchemeMap;
	}
	
	@RequestMapping(value = "/ajax/select_nx.json")
	@ResponseBody
	public Object selectSignatureScheme_nx(HttpServletRequest request) {
		String id = request.getParameter("signatureid");
		Map  signSchemeMap =  signService.getMapById(Integer.parseInt(id));
		//判断是否是监狱长的签章方案，所以数据库中签章方案的字段name要规范
		if(signSchemeMap !=null){
			if( StringNumberUtil.notEmpty(signSchemeMap.get("name"))){
				String sigSchemeName = signSchemeMap.get("name").toString();
				if(StringNumberUtil.notEmpty(sigSchemeName)&&sigSchemeName.indexOf(GkzxCommon.WARDEN)>-1){
					signSchemeMap.put("warden", "yes");
				}
			}
		}
		return signSchemeMap;
	}
	@RequestMapping(value = "/ajax/add.json")
	@ResponseBody
	public Object addSignatureScheme(HttpServletRequest request, HttpServletResponse response) {
		return updateSignatureScheme(request, response); 
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/update.json")
	@ResponseBody
	public Object updateSignatureScheme(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		String oporgid = operator.getOrganization().getOrgid();
		//
		String signidStr = request.getParameter("signid");
		String orgid = request.getParameter("orgid");
		String name = request.getParameter("name");
		int signid = StringNumberUtil.parseInt(signidStr, 0);
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";

		//
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		while (paraNames.hasMoreElements()) {
			String paraName = paraNames.nextElement();
			String paraValue = request.getParameter(paraName);
			if(StringNumberUtil.notEmpty(paraValue)){
				//
				paraMap.put(paraName, paraValue);
			}
		}
    	
		// 
		if (StringNumberUtil.notEmpty(orgid)) {
			orgid = oporgid;
			paraMap.put("orgid", orgid);
		}
		if (!StringNumberUtil.notEmpty(name)) {
			inputCheckOK = false;
			inputCheckMessage = "name不正确";
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		boolean updateSignatureScheme = false;
		if(inputCheckOK){
			//
			SignatureScheme signExist = null;
			if (StringNumberUtil.notEmpty(signidStr)) {
				signExist = signService.getById(signid);
			}
	    	int rows = 0;
			if(null == signExist){
				updateSignatureScheme = false;// 新包装对象
		    	// 添加
				rows = signService.insertByMap(paraMap, operator); //  可能有问题
			} else {
				updateSignatureScheme = true;
				// 旧的签章方案
		    	// 提交请求
				rows = signService.updateByMap(paraMap, operator); // 可能有问题
			}
			//// 更新
			if( 1== rows){
				if(updateSignatureScheme){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("添加成功!");
				}
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("签章方案操作");
		log.setOpaction("新增/修改");
		log.setOpcontent("新增/修改签章方案,orgid="+orgid+",name="+name);
		log.setOrgid(oporgid);
		log.setRemark(message.getInfo());
		log.setStatus((short)message.getStatus());
		try{
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}	
	@RequestMapping(value = "/ajax/delete.json")
	@ResponseBody
	public Object deleteSignatureScheme(HttpServletRequest request, HttpServletResponse response) {

		SystemUser user = getLoginUser(request);
		// 获取参数
		String signidStr = request.getParameter("signid");
		
		int signid = StringNumberUtil.parseInt(signidStr, 0);
		JSONMessage message = JSONMessage.newMessage();

		String info = "orgid错误";
		int rows = 0;
		if(StringNumberUtil.notEmpty(signidStr)){
			// 调用业务方法
			rows = signService.delete(signid, null, null);
		} else {
			
		}
		if(1 == rows){
			// 提示成功
			message.setSuccess();
			info = "删除成功";
		} else {
			info = "删除失败";
		}		
		message.setInfo(info);

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("签章方案操作");
		log.setOpaction("删除");
		log.setOpcontent("删除签章方案,signid="+signid);
		log.setOrgid(user.getOrgid());
		log.setRemark(message.getInfo());
		log.setStatus((short)message.getStatus());
		try{
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}
	@RequestMapping("/getSignByDepart.json")
    @ResponseBody
    public Object getSignByDepart(HttpServletRequest request){
		List<Map>  signature = signService.getSignByDepart();
        return signature;
    }
	//
	@RequestMapping("/copySignAture.json")
    @ResponseBody
    public String copySignAture(HttpServletRequest request){
        String result="error";
        String tempid = "";
        String toid = request.getParameter("toid");
        String fromid = request.getParameter("fromid");//如果选择部门时获取orgid，选择表单时获取orgid@tempid
        if(!StringNumberUtil.isNullOrEmpty(fromid) && fromid.contains("@")){
            String[] orgids = fromid.split("@");
            fromid = orgids[0];
            tempid = orgids[1];
        }
        int temp = signService.getCountByDepartid(fromid,tempid);
        signService.delete(null, toid, tempid);//删除被复制单位的相关数据
        if(temp>0){
            result="success";
            signService.copySignByDepartid(fromid,toid,tempid);
        }
        return result;
    }
	
	
	/**
	 * 进入批量签章页面
	 * @author 	YangZR		2015-04-01
	 */
	@SuppressWarnings(value={"deprecation","unchecked"})
	@RequestMapping(value = "/toBatchSealPage.page")
	public ModelAndView batchSignature(HttpServletRequest request) {
		HashMap paramap = new HashMap();
		
		SystemUser user = getLoginUser(request);
		SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());

//		paramap.put("scearch", "JXJS");
		paramap.put("codetype", "GK7799");
		paramap.put("orgid", user.getDepartid());
		String codeids = request.getParameter("codeids");
		if(StringNumberUtil.notEmpty(codeids)){
			codeids = StringNumberUtil.formatString(codeids, ",");
			paramap.put("codeids", codeids);
		}
		List<TbsysCode> caseTypeList = systemCodeService.getCodesByMap(paramap);
		request.setAttribute("caseTypeList", caseTypeList);
		
		paramap.put("codetype", "GK8899");
		List<TbsysCode> casechgList = systemCodeService.getCodesByMap(paramap);
		request.setAttribute("casechgList", casechgList);
		
//		// 签章日期
//		String sealdate =  new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//		String sealdatestr = sealdate.substring(0,4).toString()+"年";
//		if(Integer.parseInt(sealdate.substring(5,7).toString()) >= 10){
//			sealdatestr = sealdatestr+sealdate.substring(5,7)+"月";
//		}else{
//			sealdatestr = sealdatestr+sealdate.substring(6,7)+"月";
//		}
//		if(Integer.parseInt(sealdate.substring(8,10).toString()) >= 10){
//			sealdatestr = sealdatestr+sealdate.substring(8,10)+"日";
//		}else{
//			sealdatestr = sealdatestr+sealdate.substring(9,10)+"日";
//		}
//		request.setAttribute("sealdate", sealdatestr);
		
		// 签章日期
		String sealdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		request.setAttribute("sealdate", sealdate);
		
		Calendar calendar = Calendar.getInstance();//获取当前年
	    int currYear = calendar.get(Calendar.YEAR);
		request.setAttribute("currYear", currYear);
		
		// 单位简称
		request.setAttribute("orgShortname", org.getShortname());
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
        String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
        request.setAttribute("provincecode", provincecode);
		// 签章方案
		paramap.put("userid", user.getUserid());
		paramap.put("departid", user.getDepartid());
		paramap.put("restypeid", "16");//签章方案
		List<SignatureScheme> signatureSchemes = signatureSchemeService.getSignatureSchemesByUser(paramap);
		request.setAttribute("signatureSchemes", signatureSchemes);
		
		return new ModelAndView("system/batchSealPage");
		
	}
	
	/**
	 * 描述：获取某用户的签章方案
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserSignatureScheme.json")
	@ResponseBody
	public Object getSignatureScheme(HttpServletRequest request) {
		Map<String,Object> paramap = this.parseParamMap(request);
		SystemUser user = getLoginUser(request);
		
		// 签章方案
		paramap.put("userid", user.getUserid());
		paramap.put("departid", user.getDepartid());
		paramap.put("restypeid", "16");//签章方案
		List<SignatureScheme> signatureSchemes = signatureSchemeService.getSignatureSchemesByUser(paramap);
		return signatureSchemes;
		
	}
	
	
	@RequestMapping(value = "/toSelectSignatureSchemPage.page")
	public ModelAndView toSelectSignatureSchemPage(HttpServletRequest request) throws Exception{
		
		String signatureScheme = request.getParameter("signatureScheme");
		if(null != signatureScheme){
			//signatureScheme = URLDecoder.decode(signatureScheme, "UTF-8");
			request.setAttribute("signatureScheme", signatureScheme);
		}
//		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String provincecode = (jyconfig.getProperty("provincecode") == null ? "" : jyconfig.getProperty("provincecode"));
		return new ModelAndView("aip/khjc/selectSignatureSchemPage");
		
	}
	
	
	
	@RequestMapping("/getAllCrimid.json")
	@ResponseBody
	public Object getAllCrimid(HttpServletRequest request){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<TbprisonerBaseinfo>  list = flowBaseService.getAllCrimid();
		List<String> crimList = new ArrayList<String>();
		for(TbprisonerBaseinfo info:list){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("crimid", info.getCrimid());
			result.add(map);
			crimList.add(info.getCrimid());
		}
		return result;
	}
	
	
	@RequestMapping("/copyPic.json")
	@ResponseBody
	public Object copyPic(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		//String aipFileStr = request.getParameter("aipFileStr");

		String image = request.getParameter("image");//.replace("%25255C", "/");  //转图片路径
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String picPath = jyconfig.getProperty(GkzxCommon.CRIMINALPHOTO_ABSOLUTEPATH) == null ? 
				"": jyconfig.getProperty(GkzxCommon.CRIMINALPHOTO_ABSOLUTEPATH);
		//String picPath = request.getSession().getServletContext().getRealPath("/");
		
		File file = new File(image);	
		if (!new File(picPath).exists()) {
			new File(picPath).mkdirs();
		}
		
		File desFile = new File(picPath+crimid+"_1.jpg");
		File sfile = new File(image);
    	if (sfile == null) {
    		return null;
    	}		
		try {
			FileUtil.copyFile(file, desFile);//拷贝文件
		    file = new File(image);   
		    // 路径为文件且不为空则进行删除   
		    if (file.isFile() && file.exists()) {   
		        file.delete();   
		    }   
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map map = new HashMap();
		map.put("bh", crimid);
		map.put("mtbm", "11");
		map.put("mtlb", "1");
		//查询数据库中是否有记录
		Object obj = prisonerService.getHeadPicture(map);
		
		ZpublicDaMtxx record = new ZpublicDaMtxx();
		
		//初始化媒体对象
		record.setBh(crimid);
		record.setMtbm("11");
		record.setMtlb("1");
		record.setNr(picPath+crimid+"_1.jpg");
		record.setSdid(user.getDepartid());
		
		if(obj == null){
			int nextID = prisonerService.getNextID();
			record.setId(""+nextID);
			//插入罪犯半身照的存储路径到库中
			prisonerService.insertPrisonerPicture(record);
		}
		else{
			//更新罪犯半身照信息
			prisonerService.updatePrisonerPicture(record);
		}
    	return sfile;
	}

	/**
	 * 判断罪犯照片是否存在，如果存在不再获取大字段，不存在获取大字段
	 * @param request
	 * @return
	 */
	@RequestMapping("/getContent.json")
	@ResponseBody
	public Object getContent(HttpServletRequest request) throws IOException{
		String crimid = request.getParameter("crimid");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String picPath = jyconfig.getProperty(GkzxCommon.CRIMINALPHOTO_ABSOLUTEPATH) == null ? 
				"": jyconfig.getProperty(GkzxCommon.CRIMINALPHOTO_ABSOLUTEPATH);
		File file=new File(picPath+crimid+"_1.jpg");    
		if(!file.exists()){    
		    return flowBaseService.getAnnexcontent(crimid);    
		}  
		return null; 
	}

	
	/**
	 * 解析案件号
	 * @author YangZR	2015-04-01
	 * @param request
	 * @return map {notExistClobCaseNos:不存在大字段的案件号
	 * 						 notFitOrgCaseNos:不符合部门过滤条件的案件号
	 * 						 notThisLevelCaseNos:不在本级审批的案件号
	 * 						 fitCaseNos:符合条件的案件号
	 * 						}
	 * 				返回的案件号不包括年
	 */
	@RequestMapping("/getSealableCasesInfo.json")
	@ResponseBody
	public Object getSealableCasesInfo(HttpServletRequest request){
		String signid = request.getParameter("signid");
		if(StringNumberUtil.isEmpty(signid)){
			return null;
		}
		SystemUser user = this.getLoginUser(request);
		String casetype = request.getParameter("casetype");
		String year = request.getParameter("year");
		String casenos = request.getParameter("casenos");
		String caseid = request.getParameter("caseid"); //案件类型id
		SignatureScheme signatureScheme = signatureSchemeService.getById(Integer.parseInt(signid));
		
		return sealAndNotationService.parseCaseNos(casetype,year,casenos, user, signatureScheme, caseid);
		
	}
	
	
	@RequestMapping("/getSealData.json")
	@ResponseBody
	public List<Map<String,Object>> getSealData(HttpServletRequest request){
		//
		String signid = request.getParameter("signid");
		SignatureScheme signatureScheme = signatureSchemeService.getById(Integer.parseInt(signid));
		//
		String sealOrRevokeStr = request.getParameter("sealOrRevoke");
		if(StringNumberUtil.isEmpty(sealOrRevokeStr)){
			sealOrRevokeStr = "1";
		}
		Integer sealOrRevoke = Integer.parseInt(sealOrRevokeStr);
		SystemUser user = this.getLoginUser(request);
		//
		String casetype = request.getParameter("casetype");
		String caseid = request.getParameter("caseid");
		String year = request.getParameter("year");
		String caseno = request.getParameter("caseno");
		String sealdate = request.getParameter("sealdate");
		String flowdraftid = request.getParameter("flowdraftid");
		String xingqileixing = request.getParameter("xingqileixing");
		String type = request.getParameter("type");
		
		if(StringNumberUtil.notEmpty(caseno)&&StringNumberUtil.notEmpty(year)){
			flowdraftid = sealAndNotationService.getFlowdraftidByCaseNo(casetype, year, caseno, user, signatureScheme, caseid, xingqileixing);
		}
		if(StringNumberUtil.isEmpty(flowdraftid)){
			return null;
		}
		
		// 批量打印、批量导出的执行方法
		if(StringNumberUtil.notEmpty(type) && ("print".equals(type.trim()) || "export".equals(type.trim()) ) ){
			return printBatchService.getPrintData(flowdraftid,signatureScheme, user);
		}
		//
		Map<String,Object> paramap = this.parseParamMap(request);
		String reportPath=request.getRealPath("").replace("\\", "/")+"/RMreportReport/"; //报表要用
		paramap.put("reportPath", reportPath);
		
		return sealAndNotationService.getSealData(flowdraftid, signatureScheme, sealOrRevoke, user, paramap, sealdate);
		
	}
	
	
	@RequestMapping("/saveDataAfterSeal.json")
	@ResponseBody
	public Object saveDataAfterSeal(HttpServletRequest request){
		List<Map<String,Object>> result = null;
		SystemUser user = this.getLoginUser(request);
		String data = request.getParameter("data");
		Object obj = JsonUtil.Decode(data);
		if(obj instanceof List<?>){
			List<Map<String,Object>> list = (ArrayList<Map<String,Object>>)obj;
			result = sealAndNotationService.saveDataAfterSeal(list, user);
		}
		return result;
		
	}
	
	@RequestMapping("/singleCheckSeal.json")
	@ResponseBody
	public Object singleCheckSeal(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = this.parseParamMap(request);
		return sealAndNotationService.singleCheckSeal(paramap, user);
		
	}
	
	/**
	 * 检查签章规则
	 * @author YangZR
	 * @Date  2015-05-09
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkSeal.json")
	@ResponseBody
	public Object checkSeal(HttpServletRequest request){
		String flowdraftids = request.getParameter("flowdraftids");
		Map paramap = new HashMap();
		paramap.put("flowdraftids", flowdraftids);
		paramap.put("notFitInfo", "不符合签章的案件");
		//后面在做处理
		
		return paramap;
		
	}
	
	/**
	 * 获取进行此资源对应的流程操作时需要有多少个批注、签章
	 * @resid
	 * @return  批注个数@签章个数   2@0
	 */
	@RequestMapping(value="/getSignatureScheme.json")
	@ResponseBody
	public String getSignatureScheme(HttpServletRequest request,String resid){
		return signatureSchemeService.getSignatureSchemeByResid(resid);
	}
	
	/**
	 * 将表单上的批注、签章个数以批注个数@签章个数的格式存在tbflow_base表的text8字段上
	 * 同时更新签章流水表
	 * @resid
	 * @return     
	 */
	@RequestMapping(value="/saveSignatureSchemeToFlowBase.json")
	@ResponseBody
	public Integer saveSignatureSchemeToFlowBase(HttpServletRequest request,String tempid,String flowdraftid,String signatureNum,String sealNum){
		Map<String,Object> paramap = new HashMap<String, Object>();
		paramap.put("flowdraftid", flowdraftid);
		paramap.put("tempid", tempid);
		paramap.put("notationnum", signatureNum);
		paramap.put("sealnum", sealNum);
		signatureFlowService.insertMapSelective(paramap);
		return flowBaseService.updateTextByflowdraftid(signatureNum+"@"+sealNum, flowdraftid);
	}
}
