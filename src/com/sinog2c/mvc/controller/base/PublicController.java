package com.sinog2c.mvc.controller.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.gkzx.util.property.SimpleExcelWrite;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * Spring MVC控制器的基类, 对Session访问提供统一方法,<br/>
 * 子类应该使用基类提供的方法,以方便今后的集群部署[届时只需要修改此类中的实现即可]。
 */
@Controller
@RequestMapping("/public")
public class PublicController extends ControllerBase{
	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private FlowDeliverService flowDeliverService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService sysDocumentService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	/**
	 * 根据资源父ID查询子资源对应的
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getresidlist.action")
	@ResponseBody
	public Object getResourceList(HttpServletRequest request,HttpServletResponse response){
		String resid = request.getParameter("resid");
		List<Object> list = new ArrayList<Object>();
		try{
			//用户对象
			SystemUser user = getLoginUser(request);
			//资源对象
			List<SystemResource> reslist = systemResourceService.getAllResourceByUser(user);
			if(reslist!=null){
				for(SystemResource res:reslist){
					Integer ismenu = res.getIsmenu();
					String presid = res.getPresid();
					Integer restypeid = res.getRestypeid();
					if(ismenu == 0){
						String strval = "";
						//对应的表单资源
						if(presid.equals(resid)){
							strval = res.getResid()+"@"+res.getName()+"@"+restypeid+"@"+res.getSrurl();
						}else{//控件资源
							if(restypeid == 15 || restypeid == 16){
								strval = res.getResid()+"@"+res.getName()+"@"+restypeid+"@"+res.getSrurl();
							}
						}
						if(!"".equals(strval)){
							list.add(strval);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//
		return list;
	}
	/**
	 * 获取表单根据权限控制可编辑的节点
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ajaxeditattributelist.json")
	@ResponseBody
	public Object ajaxEditAttributeList(HttpServletRequest request){
		//用户对象
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		String nodeid = StringNumberUtil.returnString2(request.getParameter("nodeid"));
		String flowdefid = StringNumberUtil.returnString2(request.getParameter("flowdefid"));
		String flowdraftid = request.getParameter("flowdraftid");
		List<String> list = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		
		try{
			map.put("departid", departid);
			map.put("id", flowdefid);
			map.put("snodeid", nodeid);
			if(!nodeid.equals("0")&&flowdraftid!=null&&!flowdraftid.equals("")){
				map.put("flowdraftid", flowdraftid);
				//获取当前流程状态 0保存 
				String state = uvFlowService.getStateByFlowdraftid(map);
				//状态为0（保存的时候）取该节点的上级节点
				if(state.equals("0")){
					nodeid = flowDeliverService.findSnodeidByDnodeid(map);
				}
			}
			map.remove("snodeid");
			map.put("snodeid", nodeid);
			//审批表单  需打开的可编辑节点
			List<FlowDeliver> deliverlist = flowDeliverService.findByFlowdefid(map);
			//如果当前监狱找不到相应的流程信息，则找jyconfig中对应省份的流程信息
			if(deliverlist.size() <= 0){
				Properties jypro = new GetProperty().bornProp(
						GkzxCommon.DATABASETYPE, null);
				if (jypro != null) {
					map.put("departid", jypro.getProperty("provincecode"));
				}
				deliverlist = flowDeliverService.findByFlowdefid(map);
			}
			//text1 有问题，控制不住 节点是否审批
			for(FlowDeliver deliver:deliverlist){
				String colname1 = deliver.getText1();//审批的意见节点
				String colname2 = deliver.getText2();
				
				if(colname1!=null && !"".equals(colname1)){
					if(list.indexOf(colname1)== -1){
						list.add(colname1);
					}
				}
				
				//流程审批时需要编辑的节点
				if(colname2 !=null && colname2.length()>0){
					if(colname2.contains(",")){
						String[] _arr = colname2.split(",");
						for(String co:_arr){
							if(co!=null && !"".equals(co)){
								if(list.indexOf(co) == -1){
									list.add(co);
								}
							}
						}
					}else{
						if(list.indexOf(colname2)== -1){
							list.add(colname2);
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//
		return list;
	}
	
	/**
	 * 描述：获取表单可否编辑的节点
	 * @author YangZR	2015-03-04
	 * @return 表单可否编辑的节点
	 */
	@RequestMapping(value = "/ajaxGetIsEditableNode.json")
	@ResponseBody
	public Object ajaxGetIsEditableNode(HttpServletRequest request){
		Set<String> set = new HashSet<String>();
		
		//用户对象
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		
		if(StringNumberUtil.notEmpty(flowdefid)){
			//flowdefid 非空	表单涉及流程
			//TBFLOW_DELIVER表中查询出可编辑的表单节点
			String nodeid = "0";
			Map<String,String> map = new HashMap<String, String>();
			map.put("departid", departid);
			//查询当前审批的流程节点
			String flowdraftid = request.getParameter("flowdraftid");
			if(StringNumberUtil.notEmpty(flowdraftid)){
				map.put("flowdraftid", flowdraftid);
				nodeid = uvFlowService.getNodeidByFlowdraftid(map);
			}
			
			map.put("id", flowdefid);
			map.put("snodeid", nodeid);
			
			//审批表单  打开可编辑节点
			List<FlowDeliver> flowDeliverList = flowDeliverService.findByFlowdefid(map);
			//如果当前监狱找不到相应的流程信息，则找jyconfig中对应省份的流程信息
			if(flowDeliverList.size() <= 0){
				Properties jypro = new GetProperty().bornProp(
						GkzxCommon.DATABASETYPE, null);
				if (jypro != null) {
					map.put("departid", jypro.getProperty("provincecode"));
				}
				flowDeliverList = flowDeliverService.findByFlowdefid(map);
			}
			if(null!=flowDeliverList&&flowDeliverList.size()>0){
				for(FlowDeliver deliver:flowDeliverList){
					String text1 = deliver.getText1();//审批的意见节点
					String text2 = deliver.getText2();//可编辑节点
					if(StringNumberUtil.notEmpty(text1)){
						set.add(text1);
					}
					//流程审批时需要编辑的节点
					if(StringNumberUtil.notEmpty(text2)){
						String[] nodeEditArr = text2.split(",");
						if(null!=nodeEditArr && nodeEditArr.length>0){
							for(String nodeEdit : nodeEditArr){
								set.add(nodeEdit);
							}
						}
					}
				}
			}
			//打开在表单配制中，配制可编辑的节点
			Set<String> otherSet = getTemplateControleNode(tempid, departid, 1);
			if(null != otherSet && otherSet.size()>0){
				set.addAll(otherSet);
			}
		}else{
			//flowdefid 为空		表单没有涉及流程
			//查询表TBSYS_TEMPLATE，根据tempid,departid在表中查出不可编辑的表单节点
			Set<String> otherSet = getTemplateControleNode(tempid, departid, 2);
			if(null != otherSet && otherSet.size()>0){
				set.addAll(otherSet);
			}
		}
		return set;
	}
	
	/**
	 * 方法描述：获取需要锁定的节点
	 * @author:mushuhong
	 * @version:2015年9月17日13:46:03
	 */
	@RequestMapping(value="/lockSignNode.json")
	@SuppressWarnings("all")
	public void lockSignNode(HttpServletRequest request,HttpServletResponse response){
		//获取 模板id
		String flowdefid = request.getParameter("flowdefid");
		SystemUser user = getLoginUser(request);
		List<Map> list = systemResourceService.lockSignNode(user,flowdefid);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (list.size() > 0) {
				out.write(list.get(0).get("PROTECTNODE")==null?"":list.get(0).get("PROTECTNODE").toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			out.close();
		}
	}
	
	/**
	 * @autor YangZR   2015-05-23
	 * @param tempid 表单ID
	 * @param departid 部门ID
	 * @param editOrNot ：1:得到可编辑节点，2:得到不可编辑节点
	 * @return
	 */
	private Set<String> getTemplateControleNode(String tempid, String departid, int editOrNot){
		Set<String> set = new HashSet<String>();
		SystemTemplate  systemTemplate= systemTemplateService.selectByTempid(tempid, departid);
		if(null!=systemTemplate){
			String controlNodes = "";
			if(1==editOrNot){
				controlNodes = systemTemplate.getRemark();	//Remark存储表单可编辑的节点，
																					//由于现不可加字段，表中又没有备用字段，不得已用remark
			}else{
				controlNodes = systemTemplate.getUneditedfields();
			}
			
			if(StringNumberUtil.notEmpty(controlNodes)){
				String[] controlNodeArr = controlNodes.split(",");
				for(String controlField : controlNodeArr){
					set.add(controlField.trim());
				}
			}
		}
		
		return set;
	}
	
	/**
	 * 获取表单根据权限控制可编辑的节点
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ajaxControlEditNode.json")
	@ResponseBody
	public Object ajaxControlEditNode(HttpServletRequest request){
		//
		List<Object> list = new ArrayList<Object>();
		try{
			String departid = StringNumberUtil.returnString2(request.getParameter("departid"));
			String resnodeid = StringNumberUtil.returnString2(request.getParameter("resnodeid"));
			String flowdefid = StringNumberUtil.returnString2(request.getParameter("flowdefid"));
			String tempid = StringNumberUtil.returnString2(request.getParameter("tempid"));
			list = uvFlowService.ajaxNotEditAttributeList(departid,flowdefid,resnodeid,tempid);
		}catch(Exception e){
			e.printStackTrace();
		}
		//
		return list;
	}
	/**
	 * 根据权限控制表单节点的编辑
	 * 
	 * @return
	 */                      
	@RequestMapping(value = "/ajaxnoteditattributelist.json")
	@ResponseBody
	public Object ajaxNotEditAttributeList(HttpServletRequest request){
		//用户对象
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		String nodeid = "";
		String flowdefid = request.getParameter("flowdefid");
		List<String> list = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		
		try{
			
			//审批表单  所有与权限相关的节点
			map.put("id", flowdefid);
			map.put("snodeid", nodeid);
			map.put("departid", departid);
			List<FlowDeliver> deliverlist = flowDeliverService.findByFlowdefid(map);
			for(FlowDeliver deliver:deliverlist){
				String colname1 = deliver.getText1();//审批的意见节点
				String colname2 = deliver.getText2();
				
				if(colname1!=null && !"".equals(colname1)){
					if(list.indexOf(colname1)== -1){
						list.add(colname1);
					}
				}
				
				if(colname2 !=null && colname2.length()>0){
					if(colname2.contains(",")){
						String[] _arr = colname2.split(",");
						for(String co:_arr){
							if(co!=null && !"".equals(co)){
								if(list.indexOf(co) == -1){
									list.add(co);
								}
							}
						}
					}else{
						if(list.indexOf(colname2)== -1){
							list.add(colname2);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取表单不可编辑的节点
	 * 
	 * @return
	 */                      
	@RequestMapping(value = "/ajaxnoteditlist.json")
	@ResponseBody
	public Object ajaxNotEditList(HttpServletRequest request){
		//用户对象
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		String tempid = request.getParameter("tempid");
		List<String> list = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		
		try{
			String uneditedfields = "";
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				uneditedfields = template.getUneditedfields();//审批的意见节点
			}
				
				if(uneditedfields !=null && uneditedfields.length()>0){
					if(uneditedfields.contains(",")){
						String[] _arr = uneditedfields.split(",");
						for(String co:_arr){
							if(co!=null && !"".equals(co)){
								if(list.indexOf(co) == -1){
									list.add(co);
								}
							}
						}
					}else{
						if(list.indexOf(uneditedfields)== -1){
							list.add(uneditedfields);
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 保存文书表单
	 * 
	 * @return
	 */                      
	@RequestMapping(value = "/ajaxSaveDocument.json")
	@ResponseBody
	public int ajaxSaveDocument(HttpServletRequest request){
		int resultval = 0;
		//用户对象
		SystemUser user = getLoginUser(request);
		resultval = sysDocumentService.ajaxSaveDocument(request,user);
		return resultval;
	}
	/**
	 * 根据不能用户获取不同快捷方式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/shortcut")
	@ResponseBody
	public Object shortcut(HttpServletRequest request){
		 returnResourceMap(request);
		SystemUser user=getLoginUser(request);
		List<SystemResource> list=systemResourceService.getAllResourceByUser(user);
		List<SystemResource> list2=new ArrayList<SystemResource>();
		for(int i=0;i<list.size();i++){
			if((Integer)list.get(i).getRestypeid()==22){
				list2.add(list.get(i));
			}
		}
		return list2;
	}
	/**
	 * 统计查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/statisticalQuery.page")
	public Object statisticalQuery(HttpServletRequest request){
		//定义变量
		Map<String,Object> parmMap = parseParamMap(request);
		String returnval = "statisticalquery/searchone";
		ModelAndView mav = new ModelAndView(returnval,"parmMap", parmMap);
		return mav;
	}
	/**
	 * 统计查询跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toStatisticalQuery.action")
	public Object toStatisticalQuery(HttpServletRequest request){
		//定义变量
		String returnval = "statisticalquery/searchtwo";
		Map<String,Object> parmMap = parseParamMap(request);
		ModelAndView mav = new ModelAndView(returnval,"parmMap", parmMap);
		return mav;
	}
	/**
	 * 统计查询列表显示页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listStatisticalQuery.action")
	public Object listStatisticalQuery(HttpServletRequest request){
		//定义变量
		String returnval = "statisticalquery/searchthree";
		Map<String,Object> parmMap = parseParamMap(request);
		ModelAndView mav = new ModelAndView(returnval,"parmMap", parmMap);
		return mav;
	}
	/**
	 * 统计查询显示列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxListData.json")
	@ResponseBody
	public Object ajaxListData(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		SystemUser user = getLoginUser(request);
		Map<String,Object> parmMap = parseParamMap(request);
		int pageIndex =(Integer)(parmMap.get("pageIndex")==null? "":Integer.parseInt((String)parmMap.get("pageIndex")));
		int pageSize = (Integer)(parmMap.get("pageSize")==null? "":Integer.parseInt((String)parmMap.get("pageSize")));        
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		parmMap.put("start", start);
		parmMap.put("end",end);
		parmMap.put("orgid", user.getOrgid());
    	int count = systemModelService.getLiAnApproveCount(parmMap);
    	List data = systemModelService.getLiAnApproveList(parmMap);
    	
    	resultmap.put("data", data);
		resultmap.put("total", count);
		return resultmap;
	}
	@RequestMapping(value = "/exportExcel.action")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> parmMap = parseParamMap(request);
	        SimpleExcelWrite sw = new SimpleExcelWrite();
	        sw.createExcel(request,response,parmMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//start add by blue_lv 2015-07-10
	/**
	 * 输入页面,只处理 GET
	 * 
	 * @return
	 */
	@RequestMapping(value = "/topage.action", method = RequestMethod.GET)
	public ModelAndView getView(HttpServletRequest request,
			@RequestParam("viewName") String viewName) {
		ModelAndView mav = new ModelAndView(viewName);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		SystemUser user =ControllerBase.getLoginUser(request);// 获取当前登录的用户		
		mav.addObject("path", path);	
		mav.addObject("basePath", basePath);		
		mav.addObject("onlineUser", user.getName());
		mav.addObject("orgId", user.getDepartid());
		mav.addObject("orgName", user.getOrganization().getName());
		return mav;
	}
	
	@RequestMapping(value = "/toOrgSelectorPage.page", method = RequestMethod.GET)
	public ModelAndView toOrgSelectorPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("common/orgSelector");
		String path = request.getContextPath();
		List<SystemOrganization> list=this.systemOrganizationService.getAllOrginInfo();			
		mav.addObject("path", path);	
		mav.addObject("orgInfos", JSON.toJSONString(list));	
		return mav;
	}
	//end add by blue_lv 2015-07-12
}
