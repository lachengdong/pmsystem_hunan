package com.sinog2c.mvc.controller.outexecute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 案件查看
 * @author wangxf
 *
 */
@Controller
public class CaseSeeController extends ControllerBase {

	@Resource
	private TbxfSentencealterationService tbxfSentencealterationService ;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	@RequestMapping(value="caseSee")
	public ModelAndView chushiHandle(HttpServletRequest request){
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		SystemUser user=getLoginUser(request);
		String flowdefid="";
		String tempid="";
		String docid="";
		String jysdocid="";
		String jxstempid="";
		String unitlevel=user.getOrganization().getUnitlevel();
		if(StringNumberUtil.notEmpty(unitlevel)&&"2".equals(unitlevel)){//此处只做了省局监狱判断，2.0版本如果不同做省份判断
			flowdefid="other_sjbwjysp";
			tempid="BWJY_SJZYJWZXSPB";
			if("6100".equals(provincecode)){
				tempid="SX_SJBWJYSPB";
			}
			docid="103001";
			jysdocid="104001";
			jxstempid="9706report";
		}else{
			flowdefid="other_jybwjycbsp";
			tempid="ZFABWJYSPB"; //审批表表单id
			docid="103001";//审批表档案code
			jysdocid="104001";//建议书档案code不知道，暂定这一个
			jxstempid="9706report";//建议书表单id
			if("6100".equals(provincecode)){
				docid="103";//审批表档案code陕西
				jxstempid="jailbaowaireport";//建议书表单id
			}
		}
		request.setAttribute("docid", docid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("jysdocid", jysdocid);
		request.setAttribute("jxstempid", jxstempid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("unitlevel", unitlevel);
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/outexecute/caseSee.jsp"));
	}
	
	@RequestMapping(value="bwjyLookCase")
	public ModelAndView bwjyLookCase(){
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/outexecute/bwjyLookCase.jsp"));
	}
	/**
	 * @方法描述：保外就医 案件查看
	 * @author zgl
	 * @version 2014-9-21 下午03:35:21
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getJwzxCaseList.action")
	@ResponseBody
	public Object getJwzxCaseList(HttpServletRequest request) {
		
		JSONMessage message = JSONMessage.newMessage();
		Map<String, Object> map = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map>();
		try {
			String nodeid = "";
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String processid = request.getParameter("processid")==null?"":request.getParameter("processid");
			String depid = request.getParameter("depid")==null?"":request.getParameter("depid");
			String typeid = request.getParameter("typeid")==null?"":request.getParameter("typeid");
			
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? 0:Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? 0:Integer.parseInt(request.getParameter("pageSize"))); 
			
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String orgid = this.getLoginUser(request).getDepartid();
			
			if("".equals(typeid)||"1".equals(typeid)){
				map.put("flowdefid", "other_jybwjycbsp");
			}else{
				map.put("flowdefid", "other_jybwjyxbsp");
			}
			if(!"".equals(depid)){
				map.put("orgid", depid);
			}else{
				map.put("orgid", orgid);
			}
			if(!"".equals(processid)){
				if("0".equals(processid)){
					nodeid = "立案审批中";
				}else if("2".equals(processid)){
					nodeid = "科室审批中";
					map.put("flowdefid", "other_jybwjycbsp");
				}else if("3".equals(processid)){
					nodeid = "评审会审批中";
					map.put("flowdefid", "other_jybwjyxbsp");
				}else if("N0003".equals(processid)){
					nodeid = "监狱审批中";
				}else if("1".equals(processid)){
					nodeid = "已通过";
				}else {
					nodeid = "未通过";
				}
			}
			
			map.put("key", key);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("typeid", typeid);
	    	map.put("nodeid", nodeid);
			
			int total = tbxfSentencealterationService.getByjyCaseCount(map);
			data = MapUtil.turnKeyToLowerCaseOfList(tbxfSentencealterationService.getByjyCaseList(map));
			
			List<Map<String,Object>> returnData = new ArrayList<Map<String,Object>> ();//页面显示list
	    	for(Map<String,Object> map3:data){
	    		Map<String,Object> map2 = new HashMap<String,Object>();
	    		String text = (String)map3.get("text6");
	    		String shortName = systemOrganizationService.getByOrganizationId(getLoginUser(request).getDepartid()).getShortname();
	    		map2.putAll(map3);
	    		if(text!=null){
	    			String flowdefid = (String)map3.get("flowdefid");
	    			String nianhao = text.substring(0,4);
	    			String anjianhao = text.substring(4);
	    			if("other_jybwjycbsp".equals(flowdefid)){
	    				map2.put("anjianhao", "("+nianhao+")"+shortName+"初保字"+anjianhao+"号");
	    			}else{
	    				map2.put("anjianhao", "("+nianhao+")"+shortName+"续保字"+anjianhao+"号");
	    			}
	    		}else{
	    			map2.put("anjianhao",map3.get("anjianhao"));
	    		}
//	    		map2.put("nianhao", map3.get("nianhao"));
//	    		map2.put("crimid", map3.get("crimid"));
//	    		map2.put("name", map3.get("name"));
//	    		map2.put("areaname", map3.get("areaname"));
//	    		map2.put("jincheng", map3.get("jincheng"));
//	    		map2.put("nodeid", map3.get("nodeid"));
	    		returnData.add(map2);
	    	}
			
			message.setTotal(total);
			message.setData(returnData);
		}catch (Exception e) {
			//e.printStackTrace();
		}
		return message;
	}
	/**
	 * 方法描述：监外执行案件查看(宁夏)
	 * @author  mushuhong
	 * @version 2014年12月15日09:52:22
	 */
	@RequestMapping(value="/bwjyLookCaseDataInfo")
	@ResponseBody
	@SuppressWarnings("all")
	public Object bwjyLookCaseDataInfo(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Object object = tbxfSentencealterationService.bwjyLookCaseDataInfo(request,user);
		return object;
	}
	/**
	 * 方法描述：保外就医 案件撤销
	 * @author:mushuhong
	 * @version:2015年1月21日10:24:01
	 */
	@RequestMapping(value="/cheXiaoBwjyCase")
	@ResponseBody
	public Object cheXiaoBwjyCase(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Object object = tbxfSentencealterationService.cheXiaoBwjyCase(request,user);
		return  null;
	}
	/**
	 * 监外执行案件查看
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBWCaseDataInfo")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getBWCaseDataInfo(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String unitlevel=user.getOrganization().getUnitlevel();
		String key = request.getParameter("key");
		String departid=request.getParameter("depid");
		String typeid=request.getParameter("typeid");
		String anjiantype=request.getParameter("anjiantype");
		String punishmenttype=request.getParameter("punishmenttype");
		
		if(null==departid||"".equals(departid)){
			departid=user.getOrgid();
		}
		String processid=request.getParameter("processid");
		String processid2=request.getParameter("processid2");
		String birthstarttime=request.getParameter("birthstarttime");
		String birthendtime=request.getParameter("birthendtime");
		
		String caidingstarttime=request.getParameter("caidingstarttime");
		String caidingendtime=request.getParameter("caidingendtime");
		
		String lianstarttime=request.getParameter("lianstarttime");
		String lianendtime=request.getParameter("lianendtime");
		
		String flowdefid=request.getParameter("flowdefid");

		String jyorsj=request.getParameter("jyorsj");
		if(StringNumberUtil.notEmpty(jyorsj)&&"jy".equals(jyorsj)){
			flowdefid="other_zfjyjxjssp";
		}
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? 0:Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? 0:Integer.parseInt(request.getParameter("pageSize"))); 
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		JSONMessage message = JSONMessage.newMessage();
		Map<String, Comparable> map=new HashMap<String, Comparable>();
		if("1".equals(punishmenttype)){
			punishmenttype="";
			map.put("tempstr", "youqi");
		}
		if(StringNumberUtil.notEmpty(unitlevel)&&"2".equals(unitlevel)){//此处只做了省局监狱判断
			map.put("orgidsj", departid);
		}else{
			map.put("orgid", departid);
		}
		map.put("key", key);
		map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
		map.put("departid", departid);
		map.put("processid", processid);
		map.put("processid2", processid2);
		map.put("flowid", flowdefid);
		map.put("changetype", anjiantype);
		map.put("start", start);
		map.put("end", end);
		map.put("punishmenttype", punishmenttype);
		map.put("birthstarttime", birthstarttime);
		map.put("birthendtime", birthendtime);
		map.put("caidingstarttime", caidingstarttime);
		map.put("caidingendtime", caidingendtime);
		map.put("lianendtime", lianendtime);
		map.put("lianstarttime", lianstarttime);
		map.put("typeid", typeid);
		List<Map> data=new ArrayList<Map>();
		data= MapUtil.turnKeyToLowerCaseOfList(tbxfSentencealterationService.getBWCaseDataInfo(map));
		int total=tbxfSentencealterationService.getBWCaseDataInfoCount(map);
		message.setData(data);
		message.setTotal(total);
		return message;
	}

}
