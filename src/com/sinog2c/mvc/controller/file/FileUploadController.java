package com.sinog2c.mvc.controller.file;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.RequestUtil;


//import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/fileUploadDown")
public class FileUploadController {

	@Resource
	public SystemLogService logService;

	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	public ModelAndView uploadFile(DefaultMultipartHttpServletRequest request) {
		short status = 1;
		SystemUser user = ControllerBase.getLoginUser(request);
		String saveName = "";
		CommonsMultipartFile file = (CommonsMultipartFile) request
				.getFile("file");

		// 创建 读取 properties文件
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jyconfig.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			// 根据filePath获取对应的值
			String tpath = p.getProperty("filePath");
			String path = ContextLoader.getCurrentWebApplicationContext()
					.getServletContext().getRealPath(tpath);
			String showName = file.getOriginalFilename();
			// 文件重新命名 用户名 + 文件夹id + 原名 + 时间 时分秒(防止重复名称)			
			saveName = String.format("%s_%s_%s", user.getUserid(),
					java.util.UUID.randomUUID().toString().replace("-", ""),
					showName);

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
		ModelAndView mav = new ModelAndView("cdoc/uploadResult");
		String path = request.getContextPath();
		mav.addObject("path", path);
		mav.addObject("fileName", saveName);
		return mav;

	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "fileDownLoad")
	public String downLoadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String str = request.getParameter("realName");
		String storeName = java.net.URLDecoder.decode(str, "UTF-8");
		try {
			new UploadFileUtils().download(request, response, storeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 此处无法 返回到 发送下载链接的页面，否者将会报错(错误原因，不太清楚)
		// return "officeAssistant/fileManage";
		return null;
	}

	@RequestMapping(value = "imgfileUpload", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView imgfileUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		// short status = 1;
		// SystemUser user = UserProfile.getInstance().getLoginUser(request);
		// String saveName="";
		// MultipartRequest multi=request.getf
		// Iterator<String> filesk= request.getFileNames(); //
		// String key;
		// while (filesk.hasNext()) {
		// key = (String) filesk.next();
		// System.out.println(key);
		//
		// }
		// CommonsMultipartFile file = (CommonsMultipartFile)
		// request.getFile("Fdata");
		// 创建 读取 properties文件
		// InputStream in =
		// this.getClass().getClassLoader().getResourceAsStream("jyconfig.properties");
		// InputStream is = file.getInputStream();
		// byte[] buffer = new BASE64Decoder().decodeBuffer(is);
		// byte[] buffer_code=new byte(file);
		// String sss =new sun.misc.BASE64Encoder().encode(file.getBytes());
		CommonsMultipartFile file = (CommonsMultipartFile) request
				.getFile("file");
		String prefix = java.util.UUID.randomUUID().toString().replace("-", "");
		byte[] buffer_code = file.getBytes();
		String content = file.getContentType();
		RequestUtil.setSessionAttribute(request, prefix + "_vehicleImage",
				buffer_code);
		RequestUtil.setSessionAttribute(request, prefix + "_contenttype",
				content);
		ModelAndView mav = new ModelAndView("cdoc/uploadResult");
		String path = request.getContextPath();
		mav.addObject("path", path);
		mav.addObject("fileName", prefix);
		return mav;
	}

	@RequestMapping(value = "getPicture")
	public void getPicture(HttpServletRequest request,
			@RequestParam("fileName") String fileName,
			HttpServletResponse response) throws Exception {
		Object temp = RequestUtil.getSessionAttribute(request,
				String.format("%s_contenttype", fileName));
		if (temp != null) {
			byte[] buffer_code = (byte[]) RequestUtil.getSessionAttribute(
					request, String.format("%s_vehicleImage", fileName));
			OutputStream os = response.getOutputStream();
			// response.setContentType("image/jpeg");
			response.setContentType(temp.toString());
			os.write(buffer_code);
			os.flush();
		}

		// InputStream in =
		// this.getClass().getClassLoader().getResourceAsStream("jyconfig.properties");
		// Properties p = new Properties();
		// p.load(in);
		// String tpath = p.getProperty("filePath");
		// /String path1 = ContextLoader.getCurrentWebApplicationContext()
		// .getServletContext().getRealPath(tpath);

		// request.getContentType();
		// File is = new File(path1,"1.jpg");

		// RequestUtil.setSessionAttribute(request, "myimg", buffer_code);
		// RequestUtil.setSessionAttribute(request, "mycontent", content);

	}

}
