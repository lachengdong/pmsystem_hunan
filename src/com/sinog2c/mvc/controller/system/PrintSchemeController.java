package com.sinog2c.mvc.controller.system;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.ResourceType;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.exception.LoginFailureException;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.PrintSchemeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/print")
public class PrintSchemeController extends ControllerBase{

	@Resource
	SystemResourceService systemResourceService;
	@Resource
	PrintSchemeService printSchemeService;
	@Resource
	public SystemLogService logService;
	/**
	 * 显示打印方案[模板],资源
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage.page")
	public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/print/manage");

		return mav;
	}
	
	@RequestMapping(value = "/ajax/list.json")
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
		resources = filterOnly(resources, ResourceType.TYPE_ID_REPORT);
		
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
	public List<SystemResource> filterOnly(List<SystemResource> resources, final int restypeid){
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
				// 屏蔽100这个ID
				String resid = systemResource.getResid();
				final String ID_100 = "100";
				if(ID_100.equals(resid)){
					continue;
				} else
				if(restypeid == type){
					// 加到新List中
					result.add(systemResource);
				} else {
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
	@RequestMapping(value = "/ajax/getnextid.json")
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
		return message;
	}
	

	@RequestMapping(value = "/ajax/getschemetype.json")
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
	
	@RequestMapping(value = "/ajax/add.json")
	@ResponseBody
	public Object addResource(HttpServletRequest request, HttpServletResponse response) {
		return updateResource(request, response);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/update.json")
	@ResponseBody
	public Object updateResource(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		//
		String presid = request.getParameter("presid");
		String resid = request.getParameter("resid");
		String name = request.getParameter("name");
		//
		String snStr = request.getParameter("sn");
		String restypeidStr = request.getParameter("restypeid");
		String remark = request.getParameter("remark");
		//
    	int sn = StringNumberUtil.parseShort(snStr, 0);
    	int restypeid = StringNumberUtil.parseShort(restypeidStr, 0);
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";

		//
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		while (paraNames.hasMoreElements()) {
			String paraName = (String) paraNames.nextElement();
			String paraValue = request.getParameter(paraName);
			if(StringNumberUtil.notEmpty(paraValue)){
				//
				paraMap.put(paraName, paraValue);
			}
		}
    	
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
		    	resNew.setOpid(operator.getUserid());  // 操作员
		    	resNew.setSn(sn);
		    	resNew.setRestypeid(restypeid);
		    	resNew.setRemark(remark);
		    	// 添加
				//rows = systemResourceService.add(resNew, operator); // 新增
				rows = systemResourceService.insertByMap(paraMap, operator); 
			} else {
				updateResource = true;
				// 旧的资源
				resExist.setName(name);
		    	resExist.setPresid(presid);
		    	resExist.setName(name);
		    	resExist.setSn(sn);
		    	resExist.setRestypeid(restypeid);
		    	resExist.setRemark(remark);
		    	// 提交请求
				//rows = systemResourceService.update(resExist, operator);
				rows = systemResourceService.updateByMap(paraMap, operator); 
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
				message.setInfo("操作失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("资源操作");
		log.setOpaction("新增/修改");
		log.setOpcontent("新增/修改资源,resid="+resid+",name="+name);
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
	
	
	/**
	 *  跳向打印方案页面
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping("/toPrintSchemeManagePage")
	public ModelAndView toPrintSchemeManagePage(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav = new ModelAndView("system/print/printSchemeManage");
		return mav;
	}
	
	/**
	 * 获取打印方案列表
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/getPrintSchemeList")
	@ResponseBody
	public Object getPrintSchemeList(HttpServletRequest request){
		Object obj = printSchemeService.getPrintSchemeList(request);
		return obj;
	} 
	
	/**
	 * 根据打印方案id获取单个方案
	  * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/getPrintSchemeById")
	@ResponseBody
	public Object getPrintSchemeById(HttpServletRequest request){
		Object obj = printSchemeService.getPrintSchemeById(request);
		return obj;
	} 

	/**
	 * 插入打印方案记录
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/insertPrintScheme")
	@ResponseBody
	public Object insertPrintScheme(HttpServletRequest request){
		Object obj = printSchemeService.insertPrintScheme(request);
		return obj;
	} 
	
	/**
	 * 更新打印方案记录
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/updatePrintScheme")
	@ResponseBody
	public Object updatePrintScheme(HttpServletRequest request){
		Object obj = printSchemeService.updatePrintScheme(request);
		return obj;
	} 
	
	/**
	 * 删除打印方案记录
	  * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/deletePrintSchemeById")
	@ResponseBody
	public Object deletePrintSchemeById(HttpServletRequest request){
		Object obj = printSchemeService.deletePrintSchemeById(request);
		return obj;
	} 
	
	/**
	 * 批量删除打印方案记录
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/deleteBatchPrintSchemeByIds")
	@ResponseBody
	public Object deleteBatchPrintSchemeByIds(HttpServletRequest request){
		Object obj = printSchemeService.deleteBatchPrintSchemeByIds(request);
		return obj;
	} 
	
	/**
	 *  跳向打印方案配置页面
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping("/toPrintSchemeManageConfigPage")
	public ModelAndView toPrintSchemeManageConfigPage(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav = new ModelAndView("system/print/printSchemeManageConfig");
		return mav;
	}
	

	/**
	 * 根据打印方案id获取方案配置列表
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/getPrintSchemeConfigList")
	@ResponseBody
	public Object getPrintSchemeConfigList(HttpServletRequest request){
		Object obj = printSchemeService.getPrintSchemeConfigList(request);
		return obj;
	} 
	
	/**
	 * 根据打印方案id和配置id获取单个方案配置
	  * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/getPrintSchemeConfigById")
	@ResponseBody
	public Object getPrintSchemeConfigById(HttpServletRequest request){
		Object obj = printSchemeService.getPrintSchemeConfigById(request);
		return obj;
	} 
	
	/**
	 * 插入打印方案配置记录
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/insertPrintSchemeConfig")
	@ResponseBody
	public Object insertPrintSchemeConfig(HttpServletRequest request){
		Object obj = printSchemeService.insertPrintSchemeConfig(request);
		return obj;
	} 
	
	/**
	 * 更新打印方案配置记录
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/updatePrintSchemeConfig")
	@ResponseBody
	public Object updatePrintSchemeConfig(HttpServletRequest request){
		Object obj = printSchemeService.updatePrintSchemeConfig(request);
		return obj;
	} 
	
	/**
	 * 删除打印方案配置记录
	  * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/deletePrintSchemeConfigById")
	@ResponseBody
	public Object deletePrintSchemeConfigById(HttpServletRequest request){
		Object obj = printSchemeService.deletePrintSchemeConfigById(request);
		return obj;
	} 
	
	/**
	 * 批量删除打印方案配置记录
	 * @author liuyi
	 * 2014年8月23日 21:26:11
	 */
	@RequestMapping(value = "/deleteBatchPrintSchemeConfigByIds")
	@ResponseBody
	public Object deleteBatchPrintSchemeConfigByIds(HttpServletRequest request){
		Object obj = printSchemeService.deleteBatchPrintSchemeConfigByIds(request);
		return obj;
	}
	
	/**
	 * 根据打印方案配置类型获取表单或者报表下拉菜单数据
	 * @author liuyi
	 * 2014年8月24日 22:41:31
	 */
	@RequestMapping(value = "/getBiaoDanOrReportByType")
	@ResponseBody
	public Object getBiaoDanOrReportByType(HttpServletRequest request){
		Object obj = printSchemeService.getBiaoDanOrReportByType(request);
		return obj;
	}
	
	/**
	 * 根据资源类型获取资源
	 * @author liuyi
	 * 2014年8月24日 22:41:31
	 */
	@RequestMapping(value = "/selectResourcesByType")
	@ResponseBody
	public Object selectResourcesByType(HttpServletRequest request){
		Object obj = printSchemeService.selectResourcesByType(request);
		return obj;
	}
}
