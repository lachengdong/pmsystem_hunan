package com.sinog2c.mvc.controller.system;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.common.OAParameter;
import com.sinog2c.model.system.ResourceType;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.exception.LoginFailureException;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/resource")
public class SystemResourceController extends ControllerBase{

	@Resource				
	SystemResourceService systemResourceService;
	@Resource
	public SystemLogService logService;
	/**
	 * 显示资源
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/resourceManage")
	public ModelAndView resourceManage(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/res/resourcemanage");

		return mav;
	}
	
	@RequestMapping("/resourceManageType")
	public ModelAndView publicMainTypeAction(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/res/resourcemanagetype");

		return mav;
	}

	@RequestMapping(value = {"/getsingleresource.page"})
	public ModelAndView getSingleResourcePage(HttpServletRequest request, HttpServletResponse response) {
		
		// 登录用户
		SystemUser user = getLoginUser(request);
		//
		if(null == user){
			throw new LoginFailureException("请登录.");
		}
		//
		//
		//List<SystemResource> resources = systemResourceService.selectAll();
		
		// 过滤14的类型
		//resources = filterWithout(resources, ResourceType.TYPE_ID_REPORT);
		
		//
		//

		// 权宜
		ModelAndView mav = new ModelAndView("system/res/getsingleresource");

		//mav.addObject("resources", resources);
		return mav;
	}
	
	
	@RequestMapping(value = {"/ajax/list.json"})
	@ResponseBody
	public Object listAllResource(HttpServletRequest request, HttpServletResponse response) {
		
		// 登录用户
		SystemUser user = getLoginUser(request);
		//
		if(null == user){
			throw new LoginFailureException("请登录.");
		}
		//
		String userId = user.getUserid();
		//
		List<SystemResource> resources = null;
		
		// 后门,如果有什么资源赋权出问题,则  root 可以直接看到
		if("root".equals(userId)){
			// root 获取所有资源
			resources = systemResourceService.selectAll();
		} else {
			// 理论上,普通管理员不能新建资源,或者新建以后即给自己的角色赋权.
			// 取得管理员被授权的资源,管理员可以将该资源授权给其他用户...
			// 目前遇到一些问题,创建资源时,需要将资源赋给当前角色, 
			// 但是当前用户可能有多个角色,所以应该是根据创建者ID来进行拥有者赋权.
			// 管理员有普通用户的资源合不合理? 没权限就看不见,合不合理?
			//resources = systemResourceService.getAllResourceByUser(user);
			// 直接获取所有
			resources = systemResourceService.selectAll();
		}
		// 过滤14的类型
		//resources = filterWithout(resources, ResourceType.TYPE_ID_REPORT);
		
		// 总数
		int total = resources.size();
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setData(resources);
		
		//
		return message.getData();
	}

	@RequestMapping(value = {"/ajax/listUserResource.json"})
	@ResponseBody
	public Object listUserResource(HttpServletRequest request, HttpServletResponse response) {
		
		// 登录用户
		SystemUser user = getLoginUser(request);
		//
		if(null == user){
			throw new LoginFailureException("请登录.");
		}
		//
		String userId = user.getUserid();
		//
		List<SystemResource> resources = null;
		
		// 后门,如果有什么资源赋权出问题,则  root 可以直接看到
		if("root".equals(userId)){
			// root 获取所有资源
			resources = systemResourceService.selectAll();
		} else {
			// 理论上,普通管理员不能新建资源,或者新建以后即给自己的角色赋权.
			// 取得管理员被授权的资源,管理员可以将该资源授权给其他用户...
			// 目前遇到一些问题,创建资源时,需要将资源赋给当前角色, 
			// 但是当前用户可能有多个角色,所以应该是根据创建者ID来进行拥有者赋权.
			// 管理员有普通用户的资源合不合理? 没权限就看不见,合不合理?
			resources = systemResourceService.getAllResourceByUser(user);
		}
		// 过滤14的类型,不显示
		resources = filterWithout(resources, ResourceType.TYPE_ID_REPORT);
		
		// 总数
		int total = resources.size();
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setData(resources);
		
		//
		return message.getData();
	}
	
	//
	public List<SystemResource> filterWithout(List<SystemResource> resources, final int restypeid){
		if(null == resources){
			return null;
		}
		List<SystemResource> result = new ArrayList<SystemResource>();
		//
		Iterator<SystemResource> iteratorR = resources.iterator();
		while (iteratorR.hasNext()) {
			SystemResource systemResource = (SystemResource) iteratorR.next();
			if(null != systemResource){
				int type = systemResource.getRestypeid();
				if(restypeid == type){
					// 不加到新List中
				} else {
					result.add(systemResource);
				}
			}
		}
		//
		return result;
	}

	/**
	 * 下一个id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getnextid")
	@ResponseBody
	public Object getNextId(HttpServletRequest request, HttpServletResponse response) {
		
	
		// 调用业务方法
		int nextid = 0;
		
		try{
			nextid = systemResourceService.getNextId();
		} catch (Exception e) {
		}
		
		if(nextid < 1){
			try{
				nextid = systemResourceService.getNextId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 提示成功
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		if(nextid > 1){
			message.setSuccess();
			//
			//DecimalFormat format = new DecimalFormat("00000");
			message.addMeta("nextid", nextid);
		}
		// 
		
		//
		return message;
	}
	

	@RequestMapping(value = "/ajax/getresourcetype.json")
	@ResponseBody
	public Object getResType(HttpServletRequest request, HttpServletResponse response) {
		//
		List<ResourceType> resourceTypes = systemResourceService.listAllResourceType();
		//
		
		JSONMessage message = JSONMessage.newMessage();
		message.setData(resourceTypes);
		//
		return message.getData();
	}
	
	@RequestMapping(value = "/ajax/add")
	@ResponseBody
	public Object addResource(HttpServletRequest request, HttpServletResponse response) {
		return updateResource(request, response);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/update")
	@ResponseBody
	public Object updateResource(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		//
		String presid = request.getParameter("presid");
		String resid = request.getParameter("resid");
		//String orgid = request.getParameter("orgid");
		String name = request.getParameter("name");
		String srurl = request.getParameter("srurl");
		//
		String ismenuStr = request.getParameter("ismenu");
		String ismenuleafStr = request.getParameter("ismenuleaf");
		String isresourcesleafStr = request.getParameter("isresourcesleaf");
		String snStr = request.getParameter("sn");
		String restypeidStr = request.getParameter("restypeid");
		String remark = request.getParameter("remark");
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		//
    	int ismenu = StringNumberUtil.parseShort(ismenuStr, 0);
    	int ismenuleaf = StringNumberUtil.parseShort(ismenuleafStr, 0);
    	int isresourcesleaf = StringNumberUtil.parseShort(isresourcesleafStr, 0);
    	int sn = StringNumberUtil.parseShort(snStr, 0);
    	int restypeid = StringNumberUtil.parseShort(restypeidStr, 0);
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";

		//
		Map<String, Object> paraMap = parseParamMap(request);
		// 
		if (!StringNumberUtil.notEmpty(resid)) {
			inputCheckOK = false;
			inputCheckMessage = "resid不正确";
		}
		if (!StringNumberUtil.notEmpty(presid)) {
			inputCheckOK = false;
			inputCheckMessage = "presid不正确";
		}
		if (!StringNumberUtil.notEmpty(name)) {
			inputCheckOK = false;
			inputCheckMessage = "name不正确";
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		boolean updateResource = false;
		if(inputCheckOK){
			//
			SystemResource resExist = systemResourceService.getByResourceId(resid);
	    	int rows = 0; 
			if(null == resExist){
				updateResource = false;// 新包装对象
				SystemResource resNew = new SystemResource();
		    	//
		    	if(null == presid){
		    		presid = "0";
		    	}
		    	resNew.setResid(resid);
		    	resNew.setPresid(presid);
		    	resNew.setName(name);
		    	resNew.setIsmenu(ismenu);				// 菜单
		    	resNew.setIsmenuleaf(ismenuleaf);		// 菜单叶子
		    	resNew.setIsresourcesleaf(isresourcesleaf);	// 资源叶子
		    	resNew.setSrurl(srurl);				// 菜单的链接
		    	resNew.setOpid(operator.getUserid());  // 操作员
		    	resNew.setSn(sn);
		    	resNew.setRestypeid(restypeid);
		    	resNew.setRemark(remark);
		    	// 添加
				//rows = systemResourceService.add(resNew, operator); // 新增
				rows = systemResourceService.insertByMap(paraMap, operator); //  TODO 可能有问题
			} else if(islocalnew > 0){
				updateResource = true;
				rows = 0;
			}  else {
				updateResource = true;
				// 旧的资源
				resExist.setName(name);
		    	resExist.setPresid(presid);
		    	resExist.setName(name);
		    	resExist.setIsmenu(ismenu);				// 菜单
		    	resExist.setIsmenuleaf(ismenuleaf);		// 菜单叶子
		    	resExist.setIsresourcesleaf(isresourcesleaf);	// 资源叶子
		    	resExist.setSrurl(srurl);				// 菜单的链接
		    	resExist.setSn(sn);
		    	resExist.setRestypeid(restypeid);
		    	resExist.setRemark(remark);
		    	// 提交请求
				//rows = systemResourceService.update(resExist, operator);
		    	paraMap = new HashMap<String, Object>();
				Enumeration<String> paraNames = request.getParameterNames();
				while (paraNames.hasMoreElements()) {
					String paraName = (String) paraNames.nextElement();
					String paraValue = request.getParameter(paraName);
					if(StringNumberUtil.notEmpty(paraValue)){
						//
						paraMap.put(paraName, paraValue);
					} else {
						paraMap.put(paraName, paraValue);
					}
				}
				
				rows = systemResourceService.updateByMap(paraMap, operator); // TODO 可能有问题
			}
			//// 更新
			if( 1== rows){
				if(updateResource){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("添加成功!");
				}
				message.setSuccess();
			} else {
				if(islocalnew > 0 && updateResource){
					message.setInfo("资源ID已存在!");
				}else if (updateResource) {
					message.setInfo("修改失败!");
				} else {
					message.setInfo("添加失败!");
				}
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("资源操作");
		log.setOpaction("新增/修改");
		log.setOpcontent("新增/修改资源,resid="+resid+",name="+name);
		log.setOrgid(operator.getOrgid());
		log.setRemark(message.getInfo());
		log.setLoginip(IPUtil.getClientIP(request));
		log.setStatus((short)message.getStatus());
		try{
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}	
	@RequestMapping(value = "/ajax/delete")
	@ResponseBody
	public Object deleteResource(HttpServletRequest request, HttpServletResponse response) {

		SystemUser operator = getLoginUser(request);
		// 获取参数
		String resid = request.getParameter("resid");
		JSONMessage message = JSONMessage.newMessage();

		String info = "resid错误";
		int rows = 0;
		if(StringNumberUtil.notEmpty(resid)){
			// 调用业务方法
			rows = systemResourceService.delete(resid, operator);
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
		log.setLogtype("资源操作");
		log.setOpaction("删除");
		log.setOpcontent("删除资源,resid="+resid);
		log.setOrgid("sys");
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
	

	@RequestMapping(value = "/ajax/copy.json")
	@ResponseBody
	public Object copyTo(HttpServletRequest request, HttpServletResponse response) {

		SystemUser operator = getLoginUser(request);
		// 获取参数
		String fromresid = getParameterString(request, "fromresid", "");
		String toresid = getParameterString(request, "toresid", "");
		int onlysub = getParameterInt(request, "onlysub", 0);
		
		JSONMessage message = JSONMessage.newMessage();

		String info = "resid错误";
		int result = 0;
		if(StringNumberUtil.isEmpty(fromresid) || StringNumberUtil.isEmpty(toresid)){
			info = "参数错误";
		} else {
			// 调用业务方法
			result = systemResourceService.copyTo(fromresid, toresid, onlysub, operator);
		}
		//
		if(result > 0){
			// 提示成功
			message.setSuccess();
			info = "拷贝成功! 资源数量: " + result;
		} else {
			info = "操作失败";
		}
		message.setInfo(info);

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("资源拷贝");
		log.setOpaction("拷贝");
		log.setOpcontent("拷贝资源,fromresid="+fromresid+"; toresid="+toresid);
		log.setOrgid(operator.getOrgid());
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
	
	@RequestMapping(value = "/getSysResource.json")
	@ResponseBody
	public Object getResource(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		SystemResource sysResource = systemResourceService.getByResourceId(menuid);
		return sysResource;
	}
	
	
	
	@RequestMapping(value = "/getResouceTypeList")
	@ResponseBody
	public HashMap<String, Object> getResouceTypeList(HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		Map map = new HashMap();
		map.put("start", pageIndex * pageSize + 1);
		map.put("end", (pageIndex +1)* pageSize);
		map.put("key", key);
		// 查询出所有数据集合
		List<Map> list=new ArrayList();
		list= systemResourceService.getResouceType(map);
		// 查询数据总数
		int count = systemResourceService.getCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	} 
	
	@RequestMapping(value = "/insertResouceManage")
	@ResponseBody
	public Object insertResouceManage(HttpServletRequest request){
		Object obj = systemResourceService.insertResouceManage(request);
		return obj;
	} 
	
	@RequestMapping(value = "/updateResouceManage")
	@ResponseBody
	public Object updateResouceManage(HttpServletRequest request){
		Object obj = systemResourceService.updateResouceManage(request);
		return obj;
	} 
	@RequestMapping(value = "/deleteResouceById")
	@ResponseBody
	public Object deleteResouceById(HttpServletRequest request){
		Object obj = systemResourceService.deleteResouceManage(request);
		return obj;
	} 
	@RequestMapping(value = { "/ajax/listAllMenuWithFlowBtun.json" })
	@ResponseBody
	public List<SystemResource> listAllMenuWithFlowBtun(HttpServletRequest request) {
		return systemResourceService.selectMenubyrestypeid("1", OAParameter.FLOWBUTTON);
	}
	/**
	 * 查找对应的资源列表（待办事项用）
	 * yanqutai
	 * @return
	 */
	@RequestMapping("/ajaxSearchResourceByFlowdefid.json")
	@ResponseBody
	public Object ajaxSearchResourceByFlowdefid(HttpServletRequest request){
		//获取当前用户
		SystemUser user = getLoginUser(request);
		//参数
		String flowdefid = request.getParameter("flowdefid");//流程草稿ID
		String resid = request.getParameter("resid");//菜单id
		Map map = systemResourceService.ajaxSearchResourceByFlowdefid(flowdefid,resid,user);
		return map;
	}
}
