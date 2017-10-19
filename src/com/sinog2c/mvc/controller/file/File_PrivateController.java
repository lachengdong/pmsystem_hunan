package com.sinog2c.mvc.controller.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.model.file.FileFolderPrivate;
import com.sinog2c.model.file.FileFolderShare;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.LogManage;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.attachment.AttachmentService;
import com.sinog2c.service.api.file.FileFolder_PrivateService;
import com.sinog2c.util.common.RequestUtil;

@Controller
@RequestMapping("/file")
public class File_PrivateController {
	@Resource
	private FileFolder_PrivateService fileFolder_PrivateService;

	@Resource
	private AttachmentService attachmentService;

	private String[] allowtypeOthers = { "gif", "jpeg", "png", "jpg", "tif",
			"bmp", "html", "htm", "doc", "xls", "txt", "zip", "rar", "pdf",
			"cll", "aip", "docx", "xlsx", "exe", "xml", "dat", "dll", "chm",
			"pptx", "ppt", "" };

	/**
	 * 进入个人文件柜页面
	 * 
	 * @param request
	 * @param box
	 * @return
	 */
	@RequestMapping(value = "toPrivateFileFolder", method = RequestMethod.GET)
	public ModelAndView toPrivateFileFolder(
			HttpServletRequest request,
			@RequestParam(value = "box", required = false, defaultValue = "0") String box) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("file/file_private");
		String folders = JSON.toJSONString(this.fileFolder_PrivateService
				.getfolderbyUseridWithNew(user.getUserid()));

		String sharedfolders = JSON.toJSONString(this.fileFolder_PrivateService
				.getSharedFolderByUserId(user.getUserid()));

		mav.addObject("path", path);
		mav.addObject("folders", folders);
		mav.addObject("sharedfolders", sharedfolders);

		return mav;
	}

	/**
	 * 根据用户ID获取个人文件柜目录信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileFolders")
	@ResponseBody
	public List<FileFolderPrivate> getFileFolders(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		return this.fileFolder_PrivateService.getfolderbyUserid(user
				.getUserid());
	}

	/**
	 * 进入个人文件柜目录编辑页面
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
		ModelAndView mav = new ModelAndView("file/addfilefolder");
		String folders = JSON.toJSONString(this.fileFolder_PrivateService
				.getfolderbyUserid(user.getUserid()));
		mav.addObject("path", path);
		mav.addObject("folders", folders);
		mav.addObject("action", action);
		return mav;
	}

	/**
	 * 进入文件阅读、编辑页面
	 * 
	 * @param request
	 * @param action
	 * @return
	 */
	@RequestMapping(value = "/code/**/toFileReadWritePage", method = RequestMethod.GET)
	public ModelAndView toFileReadWritePage(
			HttpServletRequest request,
			@RequestParam(value = "action", required = false, defaultValue = "view") String action,
			@RequestParam(value = "extname", required = false, defaultValue = "doc") String extname) {

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		Pattern pattern = Pattern.compile("code/(.*?)/toFileReadWritePage");
		Matcher matcher = pattern.matcher(request.getRequestURI());
		matcher.find();
		String code = matcher.group(1);
		ModelAndView mav = new ModelAndView("file/file_readwrite");
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("action", action);
		mav.addObject("code", code);
		mav.addObject("extname", extname);
		return mav;
	}

	/**
	 * 个人文件柜文件夹新增
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/editFilefolderInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<FileFolderPrivate> editFilefolderInfo(
			HttpServletRequest request,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action) {
		SystemUser user = ControllerBase.getLoginUser(request);
		JSONMessage<FileFolderPrivate> result = new JSONMessage<FileFolderPrivate>();
		FileFolderPrivate folder = JSON.parseObject(
				request.getParameter("data"), FileFolderPrivate.class);
		List<FileFolderPrivate> list = new ArrayList<FileFolderPrivate>();
		if (action.equals("new")) {
			folder.setCreateUser(user.getUserid());
			folder = this.fileFolder_PrivateService.Add(folder);
			LogManage.getInstance().AddLog(folder, LogManage.getInstance().ADD,
					request);
			result.setInfo("添加成功！");
		} else {
			this.fileFolder_PrivateService.update(folder);
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
	 * 个人文件柜共享信息设置
	 * 
	 * @param request
	 * @param action
	 * @return
	 */
	@RequestMapping(value = "/ajax/setsharedfolderInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public int setsharedfolderInfo(
			HttpServletRequest request,
			@RequestParam(value = "action", required = false, defaultValue = "edit") String action,
			@RequestParam(value = "folderId", required = false, defaultValue = "0") Long folderId) {

		SystemUser user = ControllerBase.getLoginUser(request);
		if (action.equals("remove")) {
			return this.fileFolder_PrivateService
					.deleteSharedfolderInfo(folderId);
		} else {
			FileFolderShare sharedfolder = JSON.parseObject(
					request.getParameter("data"), FileFolderShare.class);
			return this.fileFolder_PrivateService.setsharedfolderInfo(
					sharedfolder, user);
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param id
	 * @param fileName
	 * @param attachtype
	 * @return
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView uploadFile(
			DefaultMultipartHttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "attachtype", required = true) String attachtype) {

		ModelAndView mav = new ModelAndView("file/uploadResult");
		String path = request.getContextPath();
		mav.addObject("path", path);

		// validTypeByName

		JSONMessage<Attachment_item> result = new JSONMessage<Attachment_item>();
		SystemUser user = ControllerBase.getLoginUser(request);
		List<MultipartFile> files = request.getFiles(fileName);
		// 创建 读取 properties文件
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jyconfig.properties");
		Properties p = new Properties();

		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			result.setErrorcode("0001");
			result.setInfo(e.getMessage());
			mav.addObject("result", JSON.toJSON(result));
			return mav;
		}
		// 根据filePath获取对应的值
		String tpath = p.getProperty("attachment");
		String path1 = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath(tpath);
		Attachment attachment = new Attachment();

		String[] config = OAParameter.attachmentsConfig.get(attachtype);
		attachment.setModel(config[1]);
		attachment.setAttribute(config[3]);
		attachment.setModule(config[0]);
		if (attachtype.equalsIgnoreCase("privateFolder")) {
			attachment.setPath(String.format("%s/%s", config[2],
					user.getUserid()));
		} else {
			attachment.setPath(String.format("%s", config[2]));
		}
		attachment.setPk(id);
		List<Attachment_item> fileList = new ArrayList<Attachment_item>();
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			if (file != null && file.getSize() > 01) {
				String temp = java.util.UUID.randomUUID().toString()
						.replace("-", "")
						+ ".TD";
				String path2 = String.format("%s/%s/%s", path1, config[0],
						attachment.getPath());

				Attachment_item item = new Attachment_item();
				item.setCreateUser(user.getUserid());
				item.setFileContentype(file.getContentType());
				item.setFileExtName(UploadFileUtils.getType(file
						.getOriginalFilename()));
				item.setFileId(temp);
				item.setFileName(file.getOriginalFilename());
				item.setFileSize(file.getSize());
				String fullpath = String.format("%s/%s", path2, temp);
				fullpath = fullpath.replace("/", "\\");
				item.setFullPath(fullpath);
				fileList.add(item);
				String infoF = "";
				if (this.attachmentService.getAttachmentBypkandFilename(id,
						attachment.getModel(), file.getOriginalFilename()) == null) {
					if (UploadFileUtils.validTypeByName(
							file.getOriginalFilename(), this.allowtypeOthers)) {
						UploadFileUtils.uploadFile(file, path2, temp);
						infoF = "<tr class=\"template-download fade in\"><td class=\"name\"><span>%s</span></td><td class=\"size\"><span>%s KB</span></td> <td class=\"error\"><span class=\"label label-success\">成功</span> 文件上传成功！</td></tr>";

					} else {
						result.setErrorcode("0002");
						infoF = "<tr class=\"template-download fade in\"><td class=\"name\"><span>%s</span></td><td class=\"size\"><span>%s KB</span></td> <td class=\"error\"><span class=\"label label-danger\">错误</span> 上传失败，文件格式不在允许的范围内！</td></tr>";
					}
				} else {
					result.setErrorcode("0003");
					infoF = "<tr class=\"template-download fade in\"><td class=\"name\"><span>%s</span></td><td class=\"size\"><span>%s KB</span></td> <td class=\"error\"><span class=\"label label-danger\">错误</span> 上传失败，文件已经存成！</td></tr>";

				}
				result.setInfo(String.format(infoF, item.getFileName(),
						String.format("%s", item.getFileSize() / 1024)));

			}
		}

		if (result.getErrorcode().equals("0000")) {
			attachment.setList(fileList);
			this.attachmentService.addAttachment(attachment);
			result.setSuccess();
		}
		result.setData(fileList);
		mav.addObject("result", result.getInfo());
		return mav;

	}

	/**
	 * 获取个人文件柜中文件列表（指定目录）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPrivateFiles")
	@ResponseBody
	public JSONMessage<Attachment_item2> getPrivateFiles(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);
		map.put("model", OAParameter.attachmentsConfig.get("privateFolder")[1]);
		String realPath = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath("/");
		JSONMessage<Attachment_item2> files = this.attachmentService
				.getBymodelwithpagination(map, realPath);
		return files;
	}

	/**
	 * 获取个人文件柜中的共享文件夹
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSharedfolderInfos", method = RequestMethod.POST)
	@ResponseBody
	public List<FileFolderShare> getSharedfolderInfos(
			HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
		return this.fileFolder_PrivateService.getFolderShareInfosByFolderID(id);
	}
	
	/**
	 * 删除个人文件柜指定目录
	 * @param request
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteFileFolder", method = RequestMethod.POST)
	@ResponseBody
	public int deleteFileFolder(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id) throws IOException {
		String ppath =this.getAttachmentParentPath();
		return this.fileFolder_PrivateService.delete(id, OAParameter.attachmentsConfig.get("privateFolder")[1],ppath);

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
