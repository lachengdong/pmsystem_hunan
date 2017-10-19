//Controller必须放到com.sinog2c.mvc.controller的子包中
package com.sinog2c.mvc.controller.system;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 默认配置,MVC控制器; // 注释请根据需要编写,要求简洁,准确
 */
@Controller					// @Controller 注解,所有的MVC控制器类,都需要加上
@RequestMapping("/config")	// 配置统一的二级URL映射路径,本类下都生效;  如: /pmsys/config/xxxxx.xxx
public class ConfigController extends ControllerBase{	// 需要继承 ControllerBase
	
	// 使用Spring自动注入功能,引入需要的服务层对象
	@Autowired
	private SystemConfigService configService;
	@Autowired
	private SystemLogService logService;
	

	/**
	 * 显示管理页面
	 */
	// 配置映射路径; 具体的URI由类上的@RequestMapping值加上方法上的@RequestMapping值.
	// 返回页面,则返回值类型为 ModelAndView; 映射路径建议以 .page 结尾
	// URL形如: http://locaohost:8080/pmsys/config/manage.page?1=1
	// request 和  response由Spring自动传入
	@RequestMapping("/manage.page") 	
	public ModelAndView manageConfigBean(HttpServletRequest request, HttpServletResponse response) {
		// 注入权限值;父类的方法
		returnResourceMap(request);
		// 模型和视图.
		// viewName: "system/config/manage", 根据MVC配置,将被转换为内部被保护的jsp页面
		// 具体的视图 = 前缀 + viewName + 后缀 
		// 例如: "/WEB-INF/JSP/" + "system/config/manage" + ".jsp"
		ModelAndView mav = new ModelAndView("system/config/configmanage");
		// 返回该对象
		return mav;
	}

	@RequestMapping("/systemconfig.page") 	
	public ModelAndView systemConfigPage(HttpServletRequest request, HttpServletResponse response) {
		//SystemUser user = getLoginUser(request);
		String catagory = SystemConfigBean.CATAGORY_SYSTEM;
		// 模型和视图.
		ModelAndView mav = new ModelAndView("system/config/systemconfig");
		//
		mav.addObject("catagory", catagory);
		// 返回该对象
		return mav;
	}
	
	@RequestMapping("/jyconfig.page") 	
	public ModelAndView jyConfigPage(HttpServletRequest request, HttpServletResponse response) {
		//
		SystemUser user = getLoginUser(request);
		String catagory = SystemConfigBean.CATAGORY_JYCONFIG;
		String departid = user.getDepartid();
		// 模型和视图.
		ModelAndView mav = new ModelAndView("system/config/jyconfig");
		//
		mav.addObject("catagory", catagory);
		mav.addObject("departid", departid);
		// 返回该对象
		return mav;
	}

	// 返回 JSON数据,建议以 .json 结尾. 最好是加上 /ajax/路径
	// @ResponseBody 就是告诉MVC，不用解析为JSP页面,返回内容(Body)由方法自己决定好了
	// 返回值 Object,根据配置,会被FastJson解析为JSON字符串赶回给客户端浏览器
	// listConfigBeanByPage 名字可以是任意名字,但最好有一定的意义,人类可读.
	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listConfigBeanByPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// 获取参数
		// 页码, 0 开始
		int pageIndex = getParameterInt(request, "pageIndex", 0);
		// 每页显示条数
		int pageSize  = getParameterInt(request, "pageSize", 20);
		// 排序列名
		String sortField = getParameterString(request, "sortField", "NAME");
		// asc, desc
		String sortOrder = getParameterString(request, "sortOrder", "asc");
		// 搜索
		String key = request.getParameter("key");
		

		//// 总数
		int total = 0;
		// 分页得到的数据;
		List<SystemConfigBean> beans = null;
		if(StringNumberUtil.notEmpty(key)){
			// 搜索. 这是搜索的GET转码
			key= URLDecoder.decode(key,"UTF-8");
			System.out.println("key="+key);
			total = configService.countBySearch(key);
			// 分页得到的数据
			beans = configService.search(key, pageIndex, pageSize, sortField, sortOrder);		
		} else {
			total = configService.countAll();
			// 分页得到的数据
			beans = configService.listAllByPage(pageIndex, pageSize, sortField, sortOrder);
		}
		// 封装对象,MINIUI要求有 total 和  data这两个部分
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setSuccess();
		message.setData(beans);
		//
		return message;
	}
	@RequestMapping(value = "/ajax/jyconfiglist.json")
	@ResponseBody
	public Object listJYConfigByPage(HttpServletRequest request, HttpServletResponse response) {
		//
		String catagory = SystemConfigBean.CATAGORY_JYCONFIG;
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		//
		return listConfigByPageAndCatagory(request, catagory,departid);
	}
	

	@RequestMapping(value = "/ajax/systemconfiglist.json")
	@ResponseBody
	public Object listSYSConfigByPage(HttpServletRequest request, HttpServletResponse response) {
		//
		String catagory = SystemConfigBean.CATAGORY_SYSTEM;
		//
		return listConfigByPageAndCatagory(request, catagory,null);
	}
	
	// 
	private Object listConfigByPageAndCatagory(HttpServletRequest request, String catagory, String departid) {
		//
		SystemUser user = getLoginUser(request);
		// 获取参数
		Map<String,Object> paramMap= parseParamMap(request);
		paramMap.put("catagory", catagory);
		paramMap.put("departid", departid);

		//// 总数
		int total = configService.countByCondition(paramMap, user);
		// 分页得到的数据;
		List<SystemConfigBean> beans = configService.listByCondition(paramMap, user);
		
		// 封装对象,MINIUI要求有 total 和  data这两个部分
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setSuccess();
		message.setData(beans);
		//
		return message;
	}

	@RequestMapping(value = {"/ajax/update.json", "/ajax/add.json"})
	@ResponseBody
	public Object ajaxAddOrUpdateConfig(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		// 是否新增
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		//
		int id = getParameterInt(request, "id", 0);
		int status = getParameterInt(request, "status", 0);
		String catagory = request.getParameter("catagory");
		String departid = request.getParameter("departid");
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		String describe = request.getParameter("describe");

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";
		

		if (StringNumberUtil.isEmpty(name)) {
			inputCheckOK = false;
			inputCheckMessage = "配置项名字不能为空";
		}

		if (islocalnew > 0 && id > 0) {
			inputCheckOK = false;
			inputCheckMessage = "非法请求!";
		}
		
		// 新包装对象
		SystemConfigBean bean = new SystemConfigBean();
		bean.setId(id);
		bean.setCatagory(catagory);
		bean.setName(name);
		bean.setValue(value);
		bean.setStatus(status);
		bean.setDescribe(describe);
		if (StringNumberUtil.notEmpty(departid)) {
			bean.setDepartid(departid);
		}
		
		//
		int rows = 0;
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		if(inputCheckOK){

			// 新增; 则走新增的方法
			if(0 == id || islocalnew > 0){
				rows = configService.add(bean, operator);
			} else {
				rows = configService.update(bean, operator);
			}
			//
			if( 1== rows){
				if(id > 0){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("新增成功!");
				}
				message.setSuccess();
			} else {
				if(id > 0){
					message.setInfo("修改失败!");
				} else {
					message.setInfo("新增失败!");
				}
			}
		} else {
			message.setInfo(inputCheckMessage);
		}
		//
		SystemLog log = new SystemLog();
		log.setLogtype("默认配置");
		log.setOpaction("新增/修改");
		log.setOpcontent(message.getInfo());
		log.setRemark(message.getInfo());
		logService.add(log, operator);
		
		//
		return message;
	}
}
