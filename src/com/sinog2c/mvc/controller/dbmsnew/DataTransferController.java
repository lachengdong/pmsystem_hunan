package com.sinog2c.mvc.controller.dbmsnew;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.util.common.GetAbsolutePath;

@Controller
public class DataTransferController extends ControllerBase{
	
	@Autowired
	private DataTransferService dataTransferService;
	@Autowired
	private DbmsNewDataExportService dbmsNewDataExportService;
	
	/**
	 * @描述：新版 数据导出
	 * @author YangZR 
	 * @data  2015-05-29
	 */	
	@RequestMapping(value = "/dataExport.json")
	@ResponseBody
	public Object dataExport(HttpServletRequest request){
		
		Map<String,Object> result = null;
		final SystemUser user = getLoginUser(request);
		final Map<String, Object> paramap = parseParamMap(request);
		
		try {
			
			result = dataTransferService.dataExport(request,paramap, user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return result;
	}
	
	
	/**
	 * 数据导入
	 * @author YangZR 2015-05-29
	 */	
	@RequestMapping(value = "/dataImport.json", method = RequestMethod.POST)
	@ResponseBody
	public Object dataImport (HttpServletRequest request,  @RequestParam("uploadfile") MultipartFile uploadfile ){
		JSONMessage message = new JSONMessage();
		SystemUser user = this.getLoginUser(request);
		
		this.setSessionAttribute(request, "percent", "数据上传中...");
		boolean valid = true;
		String info = "导入成功！";
		//
		String originalFilename = "";
		if (uploadfile.isEmpty()) {
			valid = false;
			info = "没有上传文件，导入失败";
		}else{
			originalFilename = uploadfile.getOriginalFilename().trim();
			if(! originalFilename.endsWith(".zip")){
				valid = false;
				info = "上传文件格式不对，不是zip文件";
			}
		}
		//
		if(valid){
			String rootpath = GetAbsolutePath.getAbsolutePath("");
			//上传文件的目录
			String uploaddata = rootpath + GkzxCommon.UPLOADDATA;
			File uploaddataDir = new File(uploaddata);
			if(!uploaddataDir.exists()){
				uploaddataDir.mkdir();
			}
			
			// 解压文件到目标目录
			String unzipdata = rootpath + GkzxCommon.UNZIPDATA_PATH;
			File unzipdataDir = new File(unzipdata);
			if(! unzipdataDir.exists()){
				unzipdataDir.mkdir();
			}
			
			String zipFilePath = uploaddata + File.separator + originalFilename;//上传后zip文件的路径，包括文件名
			String filenameNoSuffix = originalFilename.substring(0,originalFilename.lastIndexOf(".zip"));//去掉后缀的文件名
			try {
				//上传文件所在的目录
				request.getSession().setAttribute("percent", "文件上传中...");
				uploadfile.transferTo(new File(zipFilePath));
				
				//解压
				request.getSession().setAttribute("percent", "压缩包解压中...");
				dbmsNewDataExportService.unzipFile(filenameNoSuffix,zipFilePath,unzipdata);
				//解压完成后删除zip包
				new File(zipFilePath).delete();
				//
				request.getSession().setAttribute("percent", "数据导入中...");
				boolean success = dataTransferService.dataImport(request,unzipdata + File.separator + filenameNoSuffix, user);
				//导入完成后，删除解压缩的包
				new File(unzipdata + File.separator + filenameNoSuffix).delete();
				//
				message.setSuccess();
				
			} catch (Exception e) {
				e.printStackTrace();
				valid = false;
				info = "导入失败！";
			}
		}
		message.setInfo(info);
		request.getSession().setAttribute("percent", "100%");
		return message;
	}
	
}
