package com.sinog2c.mvc.controller.attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.attachment.Attachment_item;
import com.sinog2c.model.attachment.Attachment_item2;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.controller.file.UploadFileUtils;
import com.sinog2c.service.api.attachment.AttachmentService;
import com.sinog2c.util.dbms.DocZipUtil;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/attachment")
public class AttachmentCotroller {

	@Resource
	private AttachmentService attachmentService;

	/**
	 * 附件下载
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @throws Exception
	 */
	@RequestMapping(value = "code/**/fileDownLoad.action")
	public void downLoadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// code：文件真实名&文件contentype&文件相对path
		Pattern pattern = Pattern.compile("code/(.*?)/fileDownLoad");
		Matcher matcher = pattern.matcher(request.getRequestURI());
		matcher.find();
		String code = matcher.group(1);
		String[] temp = new String(new BASE64Decoder().decodeBuffer(code),
				"utf-8").split("&");
		response.setCharacterEncoding("utf-8");
		response.setContentType(temp[1]);
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ new String(temp[0].getBytes("gbk"), "iso8859-1"));		
		String path =this.getAttachmentParentPath();
		try {
			File file = new File(path + "\\" + temp[2]);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			os.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 个人文件柜--批量下载
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "batchdownload.action")
	public void batchdownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {			
		List<Attachment_item2> list=JSON.parseArray(request.getParameter("batchfiles"), Attachment_item2.class);
		String path =this.getAttachmentParentPath();
		for(Attachment_item2 item:list)
		{
			String[] temp = new String(new BASE64Decoder().decodeBuffer(item.getCode()),
					"utf-8").split("&");
			File file = new File(path + "\\" + temp[2]);			
			FileUtils.getFileUtils().copyFile(file, new File(path+File.separator+"temp"+File.separator+item.getFileName()));
		}
		SystemUser user=ControllerBase.getLoginUser(request);
		
		String ZipFileName=String.format("%s%stemp%s%s-%s.zip", path,File.separator,File.separator,java.util.UUID.randomUUID().toString()
				.replace("-", ""),user.getUserid());
		DocZipUtil.compress(path+File.separator+"temp",ZipFileName);		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/x-zip");
		response.setHeader("Content-Disposition", "attachment;fileName="
			+ new String(("个人文件柜-"+user.getUserid()+".zip").getBytes("gbk"), "iso8859-1"));	
		File file = new File(ZipFileName);
		InputStream inputStream= new FileInputStream(file);
		try {			
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			os.close();			
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		finally
		{
			inputStream.close();			
			for(Attachment_item2 item:list)
			{				
				FileUtils.delete(new File(path+File.separator+"temp"+File.separator+item.getFileName()));
			}	
			FileUtils.delete(file);
			FileUtils.delete(new File(path+File.separator+"temp"));			
		}
	}

	/**
	 * 修改附件名称
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/updateAttachmentItem.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<String> updateAttachmentItem(HttpServletRequest request) {
		// SystemUser user = UserProfile.getInstance().getLoginUser(request);
		JSONMessage<String> result = new JSONMessage<String>();
		Attachment_item item = JSON.parseObject(request.getParameter("data"),
				Attachment_item.class);
		this.attachmentService.updateByPrimaryKeySelective(item);
		result.setSuccess();
		result.setInfo("修改成功！");
		return result;
	}

	/**
	 * 修改附件内容
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "code/**/uploadDoc")
	@ResponseBody
	public String uploadDoc(DefaultMultipartHttpServletRequest request)
			throws Exception {
		CommonsMultipartFile file = (CommonsMultipartFile) request
				.getFile("Attachment[doc]");
		try {
			Pattern pattern = Pattern.compile("code/(.*?)/uploadDoc");
			Matcher matcher = pattern.matcher(request.getRequestURI());
			matcher.find();
			String code = matcher.group(1);
			String[] temp = new String(new BASE64Decoder().decodeBuffer(code),
					"utf-8").split("&");			
			String path =this.getAttachmentParentPath();
			File file1 = new File(path + "\\" + temp[2]);
			if (file1.exists())
				FileUtils.delete(file1);
			UploadFileUtils.uploadFile(file, path, temp[2]);
			return String.format("{code:'OK',msg:'%s/%s'}", path, temp[2]);
		} catch (Exception e) {
			throw e;
		}

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

	/**
	 * 删除指定附件
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removeattachment.action", method = RequestMethod.POST)
	@ResponseBody
	public int removeattachment(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id) {
		return this.attachmentService.deleteByPrimaryKey(id);

	}
	
	/**
	 * 批量删除附件
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/removeattachments.action", method = RequestMethod.POST)
	@ResponseBody
	public int removeattachments(HttpServletRequest request) throws IOException {	
		List<Attachment_item2> list=JSON.parseArray(request.getParameter("data"), Attachment_item2.class);
		String path =this.getAttachmentParentPath();
		for(Attachment_item2 item:list)
		{
			String[] temp = new String(new BASE64Decoder().decodeBuffer(item.getCode()),
					"utf-8").split("&");	
			item.setFullPath(path + "\\" + temp[2]);	
		}		
		return this.attachmentService.batchRemove(list);
	}
	
	
	/**
	 * 阅读、编辑附件
	 * @param request
	 * @param response
	 * @param code
	 * @param isRead
	 * @throws Exception
	 */
	@RequestMapping(value = "/code/{code}/downLoadForReadorEdit")
	public void downLoadForReadorEdit(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "code") String code,
			@RequestParam(value = "isRead", required = false, defaultValue = "1") String isRead)
			throws Exception {
		code = code.replace("******", "/");
		String[] temp = new String(new BASE64Decoder().decodeBuffer(code),
				"utf-8").split("&");
		response.setCharacterEncoding("utf-8");
		response.setContentType(temp[1]);		
		String path = this.getAttachmentParentPath();
		try {
			File file = new File(path + "\\" + temp[2]);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			os.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据model、pk删除附件信息
	 * @param request
	 * @param pk
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/deletebyModelandPK.action", method = RequestMethod.POST)
	@ResponseBody
	public int deletebyModelandPK(HttpServletRequest request,
			@RequestParam(value = "pk", required = false) Long pk,
			@RequestParam(value = "model", required = true) String model) throws IOException {
		String ppath =this.getAttachmentParentPath();
		return this.attachmentService.deletebyModelandPK(pk, model,ppath);

	}

}
