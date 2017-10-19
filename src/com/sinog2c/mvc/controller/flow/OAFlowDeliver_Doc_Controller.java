package com.sinog2c.mvc.controller.flow;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.flow.TbflowInstance;
import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.Tbsys_doctemplate;
import com.sinog2c.model.system.Tbsys_doctemplateWithBLOBs;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.controller.file.UploadFileUtils;
import com.sinog2c.service.api.flow.FlowInstanceManagerService;
import com.sinog2c.service.api.flow.TbsysDocTemplateService;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.RequestUtil;

@Controller
@RequestMapping("/docdeliver")
public class OAFlowDeliver_Doc_Controller {

	@Resource
	FlowInstanceManagerService flowInstanceManagerService;
	@Resource
	TbsysDocTemplateService tbsysDocTemplateService;
	@Resource
	private SystemLogService systemLogService;
	@Resource
	private SystemConfigService systemConfigService;

	/**
	 * 进入公文正文页签
	 * 
	 * @param request
	 * @param doctempid
	 * @param flowid
	 * @param action
	 * @param taskid
	 * @return
	 */
	@RequestMapping(value = "/todocContent.page", method = RequestMethod.GET)
	public ModelAndView todocContentView(
			HttpServletRequest request,
			@RequestParam(value = "doctempid", defaultValue = "", required = false) String doctempid,
			@RequestParam(value = "flowid", defaultValue = "", required = false) String flowid,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action,
			@RequestParam(value = "taskid", required = false, defaultValue = "") String taskid) {
		ModelAndView mav = new ModelAndView("cdoc/docftplSelector");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("flowid", flowid);
		SystemUser user = ControllerBase.getLoginUser(request);
		mav.addObject("userName", user.getName());
		mav.addObject("orgName", user.getOrganization().getName());
		mav.addObject("action", action);

		if (action.equalsIgnoreCase("new")) {
			if (!doctempid.isEmpty()) {
				mav.setViewName("cdoc/flowInD_docCnt");
				mav.addObject("doctempid", doctempid);
				return mav;
			}
			List<Tbsys_doctemplateWithBLOBs> list = this.tbsysDocTemplateService
					.getDocTemplateBydeptAndModule(user.getDepartid(),
							"docsend");
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setThumbnail2(
						new sun.misc.BASE64Encoder().encode(list.get(i)
								.getThumbnail()));
			}

			RequestUtil.setSessionAttribute(request, user.getUserid()
					+ "_doctempList", list);
			mav.addObject("doctemplist", list);
		} else {
			TbflowInstance instance = this.flowInstanceManagerService
					.getflwoInstancebyidforAudit(flowid, user.getUserid());
			String doccntfileName = instance.getDoccontenfilename();

			if (doccntfileName == null)
				doccntfileName = "";

			if (doccntfileName.isEmpty()
					&& instance.getCnodestate().equalsIgnoreCase(
							OAParameter.STATE_Draft)) {
				List<Tbsys_doctemplateWithBLOBs> list = this.tbsysDocTemplateService
						.getDocTemplateBydeptAndModule(user.getDepartid(),
								"docsend");
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setThumbnail2(
							new sun.misc.BASE64Encoder().encode(list.get(i)
									.getThumbnail()));
				}
				RequestUtil.setSessionAttribute(request, user.getUserid()
						+ "_doctempList", list);
				mav.addObject("doctemplist", list);
				return mav;
			}

			mav.setViewName("cdoc/flowInD_docCnt");
			mav.addObject("doccntfileName", instance.getDoccontenfilename());
		}

		return mav;
	}

	/**
	 * 进入公文版式正文页签
	 * 
	 * @param request
	 * @param menuid
	 * @param templetid
	 * @param flowdefid
	 * @param type
	 * @param attachjs
	 * @param action
	 * @param flowid
	 * @param taskid
	 * @return
	 */
	@RequestMapping(value = "/toflowInD_docAip.page", method = RequestMethod.GET)
	public ModelAndView toflowInD_docAip(
			HttpServletRequest request,
			@RequestParam("menuid") String menuid,
			@RequestParam("templetid") String templetid,
			@RequestParam("flowdefid") String flowdefid,
			@RequestParam("type") String type,
			@RequestParam(value = "docCnName", required = false, defaultValue = "") String docCnName,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action,
			@RequestParam(value = "flowid", required = false, defaultValue = "") String flowid,
			@RequestParam(value = "taskid", required = false, defaultValue = "") String taskid) {

		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		ModelAndView mav = new ModelAndView("cdoc/flowInD_docAip");

		if (!action.equalsIgnoreCase("new")) {
			TbflowInstance instance = this.flowInstanceManagerService
					.getflwoInstancebyidforAudit(flowid, user.getUserid());
			docCnName = instance.getDocaipcontentfilename();
			if (docCnName == null)
				docCnName = "";
		}
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("resid", menuid);
		mav.addObject("flowdefid", flowdefid);
		mav.addObject("templetid", templetid);
		mav.addObject("flowid", flowid);
		mav.addObject("taskid", taskid);
		mav.addObject("action", action);
		mav.addObject("userName", user.getName());
		mav.addObject("orgName", user.getOrganization().getName());
		mav.addObject("type", type);
		mav.addObject("docCnName", docCnName);

		return mav;
	}

	/**
	 * 上传版式正文
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/uploadAipDoc")
	@ResponseBody
	public String uploadAipDoc(DefaultMultipartHttpServletRequest request)
			throws Exception {
		SystemUser user = ControllerBase.getLoginUser(request);
		CommonsMultipartFile file = (CommonsMultipartFile) request
				.getFile("Attachment[aip][]");

		String flowid = request.getParameter("data");
		String filetype = request.getParameter("filetype");
		String saveName = "";
		String tpath = "";
		// 创建 读取 properties文件
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jyconfig.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			// 根据filePath获取对应的值
			tpath = p.getProperty("docaippath");
			if (filetype.equalsIgnoreCase("doc")) {
				tpath = p.getProperty("doccpath");
			}
			String path = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext().getRealPath(tpath);
			String suffix = UploadFileUtils.getType(file.getOriginalFilename());

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			saveName = String.format("%s_%s_%s.%s", flowid, user.getUserid(),
					format.format(date), suffix);
			UploadFileUtils.uploadFile(file, path, saveName);
			SystemLog log = new SystemLog();
			log.setLogtype("文件管理操作");
			log.setOpaction("上传");
			log.setOpcontent("上传文件:" + saveName);
			log.setOrgid(user.getUserid());
			log.setRemark("上传文件操作");
			log.setStatus(1);
			systemLogService.add(log, user);
		} catch (Exception e) {
			throw e;
		}
		return String.format("{code:'OK',msg:'%s/%s'}", tpath, saveName);
	}

	/**
	 * 进入套红模板选择页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toGetRedTemplates.page", method = RequestMethod.GET)
	public ModelAndView toGetRedTemplates(HttpServletRequest request) {

		SystemUser user = ControllerBase.getLoginUser(request);
		List<Tbsys_doctemplate> list = this.tbsysDocTemplateService
				.getDocTemplateBydeptAndModule2(user.getDepartid(), "ringred");
		ModelAndView mav = new ModelAndView("cdoc/docRedftplSelector");
		mav.addObject("docTemplates", JSON.toJSONString(list));
		return mav;
	}

	/**
	 * 进入公文字号设定弹出页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/todocNumSet.page", method = RequestMethod.GET)
	public ModelAndView todocNumSet(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("cdoc/docNumSet");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String num0 = "", num1 = "";
		int num2 = 1;
		map.put("departid", user.getDepartid());
		map.put("name", "doc_num");
		map.put("catagory", "DOC_SEND");
		SystemConfigBean config = this.systemConfigService.getConfigByMap(map);
		if (config == null) {
			num0 = user.getOrganization().getName();
			SimpleDateFormat smf = new SimpleDateFormat("yyyy");
			num1 = smf.format(new Date());			
			config = new SystemConfigBean();
			config.setName("doc_num");
			config.setCatagory("DOC_SEND");
			config.setValue(String.format("%s;%s;%s", num0, num1, num2));
			config.setDescribe("公文字号");
			this.systemConfigService.add(config, user);
		} else {
			String[] values = config.getValue().split(";");
			num0 = values[0];
			num1 = values[1];
			num2 = Integer.parseInt(values[2])+1;			
		}
		mav.addObject("num0", num0);
		mav.addObject("num1", num1);
		mav.addObject("num2", num2);
		mav.addObject("config", config);
		mav.addObject("path", path);
		return mav;
	}

	@RequestMapping(value = "/ajax/saveDocNum.json",method = RequestMethod.POST)
	@ResponseBody
	public int saveDocNum(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		SystemConfigBean config =JSON.parseObject(request.getParameter("data"), SystemConfigBean.class);
		int result=this.systemConfigService.update(config, user);
		if(result==0)
		{
			this.systemConfigService.add(config, user);
		}		
		return 1;
	}

}
