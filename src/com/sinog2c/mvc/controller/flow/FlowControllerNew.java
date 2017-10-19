package com.sinog2c.mvc.controller.flow;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.file.UploadFileUtils;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemResourceService;

/**
 * 登录相关的控制器,类似于 Struts中的Action<br/>
 * @ Controller 声明这是一个处理器类 由 servlet-context.xml 中定义,自动扫描, 非线程安全, 单例<br/>
 * 如果要在方法间共享变量，请使用 ThreadLocal<br/>
 */
@Controller
@RequestMapping("/flowNew")
public class FlowControllerNew extends FlowControllerBase {

	@Autowired
	public SystemResourceService systemResourceService;
	@Resource
	private SystemModelService systemModelService;

	// /TODO:需要修改完善
	/**
	 * 输入页面,只处理 GET 进入公用的Flow节点页面只有
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toFlowNodeView.page", method = RequestMethod.GET)
	public ModelAndView getFlowNodeViewWithTemp(HttpServletRequest request,
			@RequestParam("menuid") String menuid,
			@RequestParam("templetid") String templetid,
			@RequestParam("flowdefid") String flowdefid

	) {

		String path = request.getContextPath();
		String topstr = "";
		JSONArray docconent = new JSONArray();

		SystemUser user = getLoginUser(request);
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				String url = res.getSrurl();
				String name = res.getName();
				String resid = res.getResid();
				if (name.contains("_")) {
					name = name.split("_")[0];
				}
				// 顶部按钮
				if (res.getRestypeid() == 12) {
					topstr += "<a class=\"mini-button\" id=\"" + resid
							+ "\" iconCls=\"" + res.getShowico()
							+ "\" plain=\"true\" onclick=\"" + url + "\">"
							+ name + "</a>";
				}
			}
		}
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(
				templetid, departid);
		// 获取页面页面form的模板文件内容
		if (template != null) {
			docconent.add(template.getContent());
		}

		ModelAndView mav = new ModelAndView("system/flowcommon");
		// /TODO:需根据查询方案加页面初始化信息
		mav.addObject("path", path);
		mav.addObject("formcontent", docconent.toString());
		mav.addObject("topTools", topstr);
		mav.addObject("resid", menuid);
		mav.addObject("flowdefid", flowdefid);
		mav.addObject("tempid", templetid);

		return mav;
	}

	@RequestMapping(value = "/toFlowNodeAudit.page", method = RequestMethod.GET)
	public ModelAndView getFlowAudit(HttpServletRequest request,
			@RequestParam("flowdefid") String flowdefid

	) {

		ModelAndView mav = new ModelAndView("system/pflowaudit");
		mav.addObject("flowdefid", flowdefid);

		return mav;
	}

	@RequestMapping(value = "/toFileUpload.page", method = RequestMethod.GET)
	public ModelAndView toFileUpload(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("common/fileUpload");
		String path = request.getContextPath();
		mav.addObject("path", path);
		return mav;
	}
	
	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(DefaultMultipartHttpServletRequest request) {
		//String result = "success";
		short status = 1;
	
		SystemUser user = getLoginUser(request);
		//String folderid = request.getParameter("folderid");
		String saveName="";
		String userid = getLoginUser(request).getUserid();
		CommonsMultipartFile file = (CommonsMultipartFile) request.getFile("file");
	
		//创建 读取 properties文件
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("jyconfig.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			//根据filePath获取对应的值
			String tpath = p.getProperty("filePath");
			String path = request.getRealPath(tpath);
			String showName = file.getOriginalFilename();
			//文件重新命名 用户名 + 文件夹id + 原名 + 时间 时分秒(防止重复名称)
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			saveName = userid+  format.format(date) + showName;
		
			UploadFileUtils.uploadFile(file, path, saveName);
			

			SystemLog log = new SystemLog();
			log.setLogtype("文件管理操作");
			log.setOpaction("上传");
			log.setOpcontent("上传文件" + showName);
			log.setOrgid(user.getUserid());
			log.setRemark("上传文件操作");
			log.setStatus(status);
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return "<script language='javascript'>alert('ddd')</script>;";
	}


}
