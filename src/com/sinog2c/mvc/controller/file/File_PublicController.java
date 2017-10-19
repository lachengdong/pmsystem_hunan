package com.sinog2c.mvc.controller.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.model.file.FileFolderPublic;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.LogManage;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.controller.common.CommonController;
import com.sinog2c.service.api.attachment.AttachmentService;
import com.sinog2c.service.api.file.FileFolder_PulicService;
import com.sinog2c.util.common.RequestUtil;

@Controller
@RequestMapping("/pubfile")
public class File_PublicController {
	
	@Resource
	private FileFolder_PulicService fileFolder_PulicService;

	@Resource
	private AttachmentService attachmentService;
	
	

	/**
	 * 进入公共文件柜目录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toPublicFileFolder", method = RequestMethod.GET)
	public ModelAndView toPublicFileFolder(
			HttpServletRequest request,@RequestParam(value = "menuid", required = false, defaultValue = "") String menuid) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("file/file_public");
		String folders = JSON.toJSONString(this.fileFolder_PulicService
				.getfolderbyUseridWithNew(user.getUserid()));
		String topBtns=CommonController.GetTopBtns(user, menuid, "1");
		String tabsBtns=CommonController.GetTabsBtns(user, menuid);
		String gridContextMenu=CommonController.GetGridContextMenu(user, menuid);		
		String gridColBtns=CommonController.GetGridColBtns(user,menuid);
		
		mav.addObject("topBtns", topBtns);
		mav.addObject("tabsBtns", tabsBtns);
		mav.addObject("gridContextMenu", gridContextMenu);
		mav.addObject("gridColBtns", gridColBtns);
		mav.addObject("path", path);
		mav.addObject("folders", folders);		
		return mav;
	}
	
	/**
	 * 进入公共文件柜目录编辑页面
	 * 
	 * @param request
	 * @param action
	 * @return
	 */
	@RequestMapping(value = "toEditeFileFolderView", method = RequestMethod.GET)
	public ModelAndView toEditeFileFolderView(
			HttpServletRequest request,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("file/addPubfilefolder");
		String folders = JSON.toJSONString(this.fileFolder_PulicService
				.getfolderbyUserid(user.getUserid()));
		mav.addObject("path", path);
		mav.addObject("folders", folders);
		mav.addObject("action", action);
		return mav;
	}
	
	
	/**
	 * 公共文件柜文件夹新增
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/editFilefolderInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<FileFolderPublic> editFilefolderInfo(
			HttpServletRequest request,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action) {
		SystemUser user = ControllerBase.getLoginUser(request);
		JSONMessage<FileFolderPublic> result = new JSONMessage<FileFolderPublic>();
		FileFolderPublic folder = JSON.parseObject(
				request.getParameter("data"), FileFolderPublic.class);
		List<FileFolderPublic> list = new ArrayList<FileFolderPublic>();
		if (action.equals("new")) {
			folder.setCreateUser(user.getUserid());
			folder = this.fileFolder_PulicService.Add(folder);
			LogManage.getInstance().AddLog(folder, LogManage.getInstance().ADD,
					request);
			result.setInfo("添加成功！");
		} else {
			this.fileFolder_PulicService.update(folder);
			LogManage.getInstance().AddLog(folder,
					LogManage.getInstance().UPDATE, request);
			result.setInfo("修改成功！");
		}
		result.setSuccess();
		list.add(folder);
		result.setData(list);
		return result;
	}
	
	/**
	 * 根据用户ID获取公共文件柜目录信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileFolders")
	@ResponseBody
	public List<FileFolderPublic> getFileFolders(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		return this.fileFolder_PulicService.getfolderbyUserid(user
				.getUserid());
	}
	
	
	/**
	 * 获取个人文件柜中文件列表（指定目录）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPublicFiles")
	@ResponseBody
	public JSONMessage<Attachment_item2> getPublicFiles(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);
		map.put("model", OAParameter.attachmentsConfig.get("publicFolder")[1]);
		String realPath = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath("/");
		JSONMessage<Attachment_item2> files = this.attachmentService
				.getBymodelwithpagination(map, realPath);
		return files;
	}

	@RequestMapping(value = "/deleteFileFolder", method = RequestMethod.POST)
	@ResponseBody
	public int deleteFileFolder(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id) throws IOException {
		String ppath =this.getAttachmentParentPath();
		int a= this.fileFolder_PulicService.delete(id, OAParameter.attachmentsConfig.get("publicFolder")[1],ppath);
		return a;

	}
	
	private String getAttachmentParentPath() throws IOException {
		// 创建 读取 properties文件
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jyconfig.properties");
		Properties p = new Properties();
		p.load(in);
		// 根据filePath获取对应的值
		String tpath = p.getProperty("attachment");
		String path = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath(tpath);
		return path;
	}
	
}
