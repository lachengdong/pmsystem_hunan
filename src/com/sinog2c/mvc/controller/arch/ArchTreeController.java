package com.sinog2c.mvc.controller.arch;

import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.arch.ArchBox;
import com.sinog2c.model.arch.ArchFile;
import com.sinog2c.model.arch.ArchTree;
import com.sinog2c.model.attachment.Attachment;
import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.LogManage;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.controller.file.UploadFileUtils;
import com.sinog2c.service.api.arch.ArchTreeService;
import com.sinog2c.service.api.attachment.AttachmentService;
import com.sinog2c.util.common.RequestUtil;

@Controller
@RequestMapping("/arch")
public class ArchTreeController {

	@Resource
	private ArchTreeService archTreeService;
	
	@Resource
	private AttachmentService attachmentService;

	/**
	 * 进入卷库管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toArchTreePage", method = RequestMethod.GET)
	public ModelAndView toArchTreePage(HttpServletRequest request) {
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("arch/archtreeManage");
		String folders = JSON.toJSONString(this.archTreeService
				.getAllArchFolder());
		mav.addObject("path", path);
		mav.addObject("folders", folders);
		return mav;
	}

	/**
	 * 进入卷库选择器页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toArchFolderSelectorPage", method = RequestMethod.GET)
	public ModelAndView toArchFolderSelectorPage(HttpServletRequest request) {
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("arch/archFolderSelector");
		String folders = JSON.toJSONString(this.archTreeService
				.getAllArchFolder());
		mav.addObject("path", path);
		mav.addObject("folders", folders);
		return mav;
	}

	/**
	 * 编辑卷库信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/editArchfolderInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<ArchTree> editArchfolderInfo(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		JSONMessage<ArchTree> result = new JSONMessage<ArchTree>();
		ArchTree folder = JSON.parseObject(request.getParameter("data"),
				ArchTree.class);
		Long id = 0L;
		if (folder.getAction().equals("new")) {
			folder.setCreateUser(user.getUserid());
			id = this.archTreeService.add(folder);
			LogManage.getInstance().AddLog(folder, LogManage.getInstance().ADD,
					request);
			result.setInfo("添加成功！");
		} else {
			this.archTreeService.update(folder);
			id = folder.getId();
			LogManage.getInstance().AddLog(folder,
					LogManage.getInstance().UPDATE, request);
			result.setInfo("修改成功！");
		}
		result.setSuccess();
		result.setData(this.archTreeService.getAllArchFolder());
		result.setKey(id);
		return result;
	}

	/**
	 * 删除卷库信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/ajax/deleteArchfolderInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<ArchTree> deleteArchfolderInfo(
			HttpServletRequest request,
			@RequestParam(value = "id", required = true) Long id) {
		JSONMessage<ArchTree> result = new JSONMessage<ArchTree>();
		this.archTreeService.delete(id);
		result.setSuccess();
		result.setInfo("删除成功！");
		result.setData(this.archTreeService.getAllArchFolder());
		return result;
	}

	/**
	 * 卷盒编辑
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/editArchboxInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<ArchBox> editArchboxInfo(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		JSONMessage<ArchBox> result = new JSONMessage<ArchBox>();
		ArchBox folder = JSON.parseObject(request.getParameter("data"),
				ArchBox.class);
		Long id = 0L;
		if (folder.getAction().equals("new")) {
			folder.setCreateUser(user.getUserid());
			folder.setOrgId(user.getDepartid());
			this.archTreeService.addArchBox(folder);
			LogManage.getInstance().AddLog(folder, LogManage.getInstance().ADD,
					request);
			result.setInfo("添加成功！");
		} else {
			this.archTreeService.updateArchBox(folder);
			id = folder.getId();
			LogManage.getInstance().AddLog(folder,
					LogManage.getInstance().UPDATE, request);
			result.setInfo("修改成功！");
		}
		result.setSuccess();
		result.setKey(id);
		return result;
	}

	/**
	 * 卷盒归档
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/doArchiveArchbox.json", method = RequestMethod.POST)
	@ResponseBody
	public int doArchiveArchbox(HttpServletRequest request) {

		ArchBox archbox = JSON.parseObject(request.getParameter("data"),
				ArchBox.class);
		return this.archTreeService.archiveArchBox(archbox);
	}

	/**
	 * 获取卷盒信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getArchboxInfos")
	@ResponseBody
	public JSONMessage<ArchBox> getArchboxInfos(HttpServletRequest request) {
		return this.archTreeService.getarchboxbycondition(RequestUtil
				.parseParamMapForPagination(request));
	}

	/**
	 * 获取所用卷盒信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/getAllArchBoxInfos.json", method = RequestMethod.GET)
	@ResponseBody
	public List<ArchBox> getAllArchBoxInfos(HttpServletRequest request) {
		return this.archTreeService.getAllArchBoxInfos();
	}

	/**
	 * 删除卷盒信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteArchBox")
	@ResponseBody
	public int deleteArchBox(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Long id) {
		return this.archTreeService.deleteArchBox(id);
	}

	
	/**
	 * 档案文件信息修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/editArchFileInfo.json", method = RequestMethod.POST)
	public ModelAndView editArchFileInfo(DefaultMultipartHttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		JSONMessage<ArchFile> result = new JSONMessage<ArchFile>();
		ModelAndView mav = new ModelAndView("email/email_sendresult");		
		try {
			ArchFile archfile = RequestUtil.getObjectFromRequest(request,
					ArchFile.class);
			
			List<MultipartFile> files = request.getFiles("arch_attachment");
			
			// 创建 读取 properties文件
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("jyconfig.properties");
			Properties p = new Properties();
			p.load(in);
			// 根据filePath获取对应的值
			String tpath = p.getProperty("attachment");
			String path1 = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext().getRealPath(tpath);
			Attachment attachment = new Attachment();
			
			String[] config = OAParameter.attachmentsConfig.get("archive");
			attachment.setModel(config[1]);
			attachment.setAttribute(config[3]);
			attachment.setModule(config[0]);
			attachment.setPath(config[2]);
			
			List<Attachment_item> fileList = new ArrayList<Attachment_item>();
			
			for (int i = 0; i < files.size(); i++) {
				MultipartFile file = files.get(i);
				if (file != null && file.getSize() > 01) {
					String temp = java.util.UUID.randomUUID().toString()
							.replace("-", "")
							+ ".TD";
					String path2 = String.format("%s/%s/%s", path1, config[0],
							attachment.getPath());
					UploadFileUtils.uploadFile(file, path2, temp);
					
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
				}
			}
			attachment.setList(fileList);
			
			if (archfile.getAction().equals("new")) {
				archfile.setUserId(user.getUserid());
				this.archTreeService.addArchFile(archfile,attachment);
				LogManage.getInstance().AddLog(archfile,
						LogManage.getInstance().ADD, request);
				result.setInfo("添加成功！");
			} else {
				this.archTreeService.updateArchFile(archfile);
				LogManage.getInstance().AddLog(archfile,
						LogManage.getInstance().UPDATE, request);
				result.setInfo("修改成功！");
			}
			result.setSuccess();
			String path = request.getContextPath();
			mav.addObject("path", path);
			mav.addObject("result", result);
		} catch (Exception e) {
			result.setInfo("操作失败！");
			mav.addObject("result", result);
		}
		return mav;

	}

	/**
	 * 获取档案文件信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getArchFileInfos")
	@ResponseBody
	public JSONMessage<ArchFile> getArchFileInfos(HttpServletRequest request) {
		return this.archTreeService.getarchfilebycondition(RequestUtil
				.parseParamMapForPagination(request));
	}

	/**
	 * 删除档案文件
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteArchFile")
	@ResponseBody
	public int deleteArchFile(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Long id) {
		return this.archTreeService.deleteArchFile(id);
	}

	/**
	 * 从卷盒中移出档案文件
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/moveoutArchFile")
	@ResponseBody
	public int moveoutArchFile(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Long id) {
		ArchFile file = new ArchFile();
		file.setId(id);
		file.setBoxId(0L);
		return this.archTreeService.updateArchFile(file);
	}
	
	@RequestMapping(value = "gotoArvhFileViewPage", method = RequestMethod.GET)
	public ModelAndView gotoArvhFileViewPage(
			HttpServletRequest request,			
			@RequestParam(value = "id", required = false, defaultValue = "0") long id
			)
			throws Exception {		
		ModelAndView mav = new ModelAndView("arch/archFileView");
		String path = request.getContextPath();
		ArchFile file =this.archTreeService.getArchFileByid(id);
		
		String realPath = ContextLoader.getCurrentWebApplicationContext()
				.getServletContext().getRealPath("/");
		String[] config = OAParameter.attachmentsConfig.get("archive");
		List<Attachment_item2> attachList = this.attachmentService
				.getattachmentlistBymodel(id, config[1],
						realPath);		
		mav.addObject("archfile", file);
		mav.addObject("attachList", attachList);		
		mav.addObject("path", path);
		return mav;	
	}

}
