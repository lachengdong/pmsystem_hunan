package com.sinog2c.mvc.controller.penaltyPerform;

import java.text.ParseException;
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

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.Tbxfoldilldisability;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserRoleWrapper;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.TbxfoldilldisabilityService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * @author kexz
 *老病残信息补录
 * 2014-7-18
 */
@Controller
public class OldResidualInformationCollection extends ControllerBase{
	@Resource
	public SystemLogService logService;
	@Autowired
	private TbxfoldilldisabilityService xfoldilldisabilityService;
	@Resource
	private  ChooseCriminalService chooseCriminalService;
	
	
	/**
	 * 跳转老病残信息补录罪犯处理页面
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/oldResidualInformationCollectionChoose")
	public ModelAndView medicalParoleNoticeToCourt(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/oldResidualInformationCollectionChoose");
	}
	/**
	 * 获得罪犯处理列表数据
	 * @param request
	 * @param response
	 * author wangxf
	 */
	@RequestMapping(value = "/getoldResidualInformationCollectionChoose")
	@ResponseBody
	public Object getoldResidualInformationCollectionChoose(HttpServletRequest request){
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		Map<String, Object> map = this.parseParamMap(request);
	    map=this.parsePageInfoOfMap(map);
	    
		map.put("orgid", getLoginUser(request).getOrgid());
    	int count = chooseCriminalService.countAllByCondition(map);
    	List<Map<String,Object>> data= chooseCriminalService.getBasicInfoList(map);
    	
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	/**
	 * 信息列表页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/oldResidualInformationCollection")
	public ModelAndView oldResidualInformationCollection(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);
		List<UserRoleWrapper> rolelist = user.getRolelist();
		String roleid="";
		for (int i = 0; i<rolelist.size();  i++) {
			if ("1000000".equals(rolelist.get(i).getRoleid().toString())) {//刑罚科管理员
				roleid=rolelist.get(i).getRoleid().toString();
			}else if ("10013".equals(rolelist.get(i).getRoleid().toString())){//管教
				roleid=rolelist.get(i).getRoleid().toString();
			}
		}
		
		
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
		
//判断是否可以操作修改、删除、撤销和新增功能（本功能绑定罪犯减刑假释审批流程，只有在流程管教级别才能编辑，其他级别只能有查看功能）
		List<Map<String,Object>> controlsymbol=xfoldilldisabilityService.getcontrolsymbolByCrimid(crimid);//查询案件状态

		if (controlsymbol!=null&&!controlsymbol.isEmpty()) {//不为空说明在办案
			Map<String,Object> removestatus = controlsymbol.get(0);
			
			if ("10013".equals(roleid)) {//判断登录用户是否是管教，是就有允许管教操纵
				if ("0".equals(removestatus.get("CURRNODEID"))) {//判断案件办理流程是否在管教级别，在就允许全部操作
					request.setAttribute("removestatus","2");//允许管教操作
				} else{
					request.setAttribute("removestatus","0");//只允许查看
				}
			}else{//其他角色只允许查看
				request.setAttribute("removestatus", "0");
			}
				
		}else{//如果为空说明没有在办案
			if ("10013".equals(roleid)) {//判断登录用户是否是管教，是就有允许管教操纵
				request.setAttribute("removestatus","2");
			}else if ("1000000".equals(roleid)){//判断登录用户是否是刑罚科管理员，是就允许全部操作
				request.setAttribute("removestatus", "1");
			}else{//其他角色只允许查看
				request.setAttribute("removestatus", "0");
			}
		}
		
		
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/oldResidualInformationCollection.jsp"));
	}
	/**
	 * 罪犯老病残信息加载
	 * @param request
	 * @return
	 */
	@RequestMapping("/getoldResidualInformationCollectionList")
	@ResponseBody
	public Object getoldResidualInformationCollectionList(HttpServletRequest request){
		
		Map<String,Object> paramap = this.parseParamMap(request);
		paramap = this.parsePageInfoOfMap(paramap);
		paramap.put("departid", getLoginUser(request).getOrgid());
    	
    	
		List<Map<String,Object>> list=xfoldilldisabilityService.findByCrimid(paramap);
		int count = xfoldilldisabilityService.findByCrimidCount(paramap);
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		resultmap.put("data", list);
		resultmap.put("total", count);
		return resultmap;
	}

	/**
	 * 新增、修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOldResidualInformationCollection")
	public ModelAndView addOldResidualInformationCollection(HttpServletRequest request){
		String menuid=request.getParameter("menuid");
		String crimid=request.getParameter("crimid");
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		String peratetype=request.getParameter("peratetype");
		request.setAttribute("peratetype", peratetype);
		return new ModelAndView("penaltyPerform/AddoldResidualInformationCollection");
	}
	
	/**
	 * 新增老病残信息补录
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/saveOldResidualInformationCollection")
	@ResponseBody
	public int saveOldResidualInformationCollection(HttpServletRequest request) throws ParseException{
		
		int countnum = 0;//保存结果：0、失败，1、成功
		SystemUser user = getLoginUser(request);
		//
		String json = request.getParameter("data");
		Map<String,Object> dataMap = MapUtil.parseJSONArray2Map(json);
		
		String crimid = StringNumberUtil.getStrFromMap("crimid", dataMap);//罪犯编号
		String oidtype = StringNumberUtil.getStrFromMap("oidtype", dataMap);//种类
		String confirmtime = StringNumberUtil.getStrFromMap("confirmtime", dataMap);//认定时间
		
		String oldtype = StringNumberUtil.getStrFromMap("oldtype", dataMap);//老犯类别
		String sicktype = StringNumberUtil.getStrFromMap("sicktype", dataMap);//病犯类别
		String disabilitytype = StringNumberUtil.getStrFromMap("disabilitytype", dataMap);//残犯类别
		//
		StringBuilder sb = new StringBuilder();
		if(StringNumberUtil.notEmpty(oldtype)){
			sb.append("1@").append(oldtype).append("；");
		}
		if(StringNumberUtil.notEmpty(sicktype)){
			sb.append("2@").append(sicktype).append("；");
		}
		if(StringNumberUtil.notEmpty(disabilitytype)){
			sb.append("3@").append(disabilitytype).append("；");
		}
		String osdValue = sb.toString();
		if(StringNumberUtil.notEmpty(osdValue)){
			osdValue = StringNumberUtil.removeLastStr(osdValue, "；");
		}
		//
		
		String oidtypedetail = StringNumberUtil.getStrFromMap("oidtypedetail", dataMap);//审批种类明细
		String description = StringNumberUtil.getStrFromMap("description", dataMap);//健康情况概述
		String remark = StringNumberUtil.getStrFromMap("remark", dataMap);//备注
		
		String operator = StringNumberUtil.getStrFromMap("operator", dataMap);//操作：新增、修改
		SimpleDateFormat format = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
		
		String id = StringNumberUtil.getStrFromMap("id", dataMap);//残犯类别
		
		
		Tbxfoldilldisability tbxfoldilldisability=new Tbxfoldilldisability();
		tbxfoldilldisability.setConfirmtime(format.parse(confirmtime));	
		tbxfoldilldisability.setCrimid(crimid);//罪犯id
		tbxfoldilldisability.setDepartid(user.getDepartid());
		tbxfoldilldisability.setOidtype(oidtype);
		
		tbxfoldilldisability.setDisabilitytype(osdValue);
		
		tbxfoldilldisability.setOidtypedetail(oidtypedetail);
		tbxfoldilldisability.setDescription(description);
		tbxfoldilldisability.setRemark(remark);
		//
		tbxfoldilldisability.setOpid(user.getUserid());
		tbxfoldilldisability.setOptime(new Date());
		
		if("new".equals(operator)){
			
			tbxfoldilldisability.setTstatus("1");//默认为有效
			countnum = xfoldilldisabilityService.insertSelective(tbxfoldilldisability);
			
		}else if("edit".equals(operator)){
			
			tbxfoldilldisability.setId(id);
			
			countnum = xfoldilldisabilityService.updateByPrimaryKeySelective(tbxfoldilldisability);
		}
		
//		xfoldilldisabilityService.updateBaseCrimeAndSentenceAlteration(map);//在tbprisoner_base_crime 和 tbxf_sentencealteration 同步老病残信息
		xfoldilldisabilityService.tongbuBaseCrimeAndSentenceAlteration(crimid);
		
		return countnum;
	}
	
	
	/**
	 * 删除老病残信息补录信息
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/deleteOldResidualInformationCollection")
	@ResponseBody
	public int deleteOldResidualInformationCollection(HttpServletRequest request) throws ParseException{
		
		int countnum = 0;//保存结果：0、失败，1、成功
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		Map<String,Object> paramap = this.parseParamMap(request);
		countnum = xfoldilldisabilityService.deleteByPrimaryKey(paramap);
		
		String crimid=request.getParameter("crimid");
		xfoldilldisabilityService.tongbuBaseCrimeAndSentenceAlteration(crimid);
		
//		xfoldilldisabilityService.deleteBaseCrimeAndSentenceAlteration(map);
		return countnum;
	}
	/**
	 * 判断老病残信息补录信息是否有重复的数据
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/ISOldResidualInformationCollection")
	@ResponseBody
	public int ISOldResidualInformationCollection(HttpServletRequest request) throws ParseException{
		int countnum = 0;//保存结果：0、失败，1、成功
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String crimid=request.getParameter("crimid");
		String disabilitytype=request.getParameter("disabilitytype");
		Map<String, String> map = new HashMap<String, String>();
		map.put("disabilitytype", disabilitytype);
		map.put("crimid", crimid);
		countnum = xfoldilldisabilityService.checkByPrimaryKey(map);
		return countnum;
	}
	
	/**
	 * 老病残补录状态撤销，tstatus状态：0无效，1有效，2撤销
	 */
	@RequestMapping("/updateOldIllDisabilityStatus")
	@ResponseBody
	public int revokeOldIllDisabilityStatus(HttpServletRequest request) throws ParseException{
		int countnum = 0;//保存结果：0、失败，1、成功
		
		Map<String, Object> map = this.parseParamMap(request);
		countnum =xfoldilldisabilityService.updateRevokeOldIllDisabilityStatus(map);
		
		String crimid = StringNumberUtil.getStrFromMap("crimid", map);
		xfoldilldisabilityService.tongbuBaseCrimeAndSentenceAlteration(crimid);
		
		return countnum;
	}
	/**
	 * 跳转老病残信息补录状态修改页面
	 */
	@RequestMapping("/toChangeOldIllDisability.action")
	public ModelAndView toChangeOldIllDisability(HttpServletRequest request){
		Map<String, Object> map = this.parseParamMap(request);
		request.setAttribute("crimid", map.get("crimid"));
		request.setAttribute("id", map.get("id"));
		return new ModelAndView("penaltyPerform/RevokeoldResidualInformationCollection");
	}
	

}
