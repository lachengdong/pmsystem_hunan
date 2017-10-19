package com.sinog2c.mvc.controller.dbmsnew;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.dbmsnew.TaskBean;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.dbmsnew.DbmsDaochuListInfoService;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
import com.sinog2c.service.api.dbmsnew.DbmsFileCopyService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.util.common.DocZip;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 数据交换使用的数据库连接配置信息管理
 */
@Controller
@RequestMapping("/dbms")
public class DataImportController extends ControllerBase {

	@Autowired
	private DbmsDatabaseNewService dbmsDatabaseNewService;

	@Autowired
	private DbmsNewDataExportService dataExportService;
	
	@Autowired
	private DbmsDaochuListInfoService dbmsDaochuListInfoService;
	
	@Autowired
	private DbmsFileCopyService dbmsFileCopyService;
	
	@Autowired
	private DataTransferService dataTransferService;
	/**
	 * 文件解压页面
	 */
	@RequestMapping("/fileunzipmanage.page")
	public String fileunzipmanage(){
		return "dbms/fileunzipmanage";
	}
	/**
	 * 文件导入页面
	 */
	@RequestMapping("/fileimportmanage.page")
	public String fileimportmanage(){
		return "dbms/fileimportmanage";
	}
	
	/**
	 * 数据导入,页面
	 */
	@RequestMapping(value = "/dataimportmanage.page")
	public String dataimportmanage() {
		return "dbms/dataimportmanage";
	}
	
	/**
	 * 描述：跳转至数据导入页面
	 * @author YangZR	2015-06-02
	 */
	@RequestMapping(value = "/toPublicDataImportPage.page")
	public String toDataImportPage() {
		return "dbms/publicDataImportPage";
	}
	/**
	 * 数据导入——列表
	 */	
	@RequestMapping(value = "/ajax/listdataimport.json")
	@ResponseBody
	public Object showDatabaseInfo(HttpServletRequest request) {
		//SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		HashMap<String, Object> map = new HashMap<String, Object>();
		//map.put("sdid", departid);
		List<Map<String, Object>> databaseList = dbmsDatabaseNewService.listDBConnMapByPage(map);
		
		//
		int total = 0;
		if(null != databaseList){
			total = databaseList.size();
		}
		JSONMessage message = new JSONMessage();
		message.setData(databaseList);
		message.setTotal(total);
		
		return message;
	}
	
	/**
	 * 添加导入请求
	 */	
	@RequestMapping(value = "/importdatafile.action", method = RequestMethod.POST)
	//@ResponseBody
	public Object importXMLFileData (HttpServletRequest request, 
			@RequestParam("ddcid") String ddcid, @RequestParam("uploadfile") MultipartFile uploadfile ) {
		//SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		// 上传文件
		//
		//Map<String, Object> paraMap = parseParamMap(request);

		boolean validOK = true;
		String validError = "输入错误";
		
		JSONMessage message = new JSONMessage();
		String info = "添加失败";
		// ddcid
		//String ddcid = (String)paraMap.get("ddcid");
		//String operate = "daochu";
		//String operatetype = GkzxCommon.FAYUAN;
		// 还有其他参数
		//
		if(StringNumberUtil.isEmpty(ddcid)){
			validOK = false;
			validError = "参数错误: ddcid";
		}
		// 文件是否为空
		if (uploadfile.isEmpty()) {
			validOK = false;
			validError = "没有上传文件";
		}
		//
		if(validOK){
			//
			//bean.setOperate(operate);
			// 如果提交法院
			// bean.setOperatetype(operatetype);
			Date current = new Date();

        	String originalFilename = uploadfile.getOriginalFilename();
        	String uploadPath = "/uploadXML/"; // 根据参数获取 TODO
        	String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(current);
        	String yearMonStr = new SimpleDateFormat("yyyyMM").format(current);
        	String dayStr = new SimpleDateFormat("dd").format(current);

        	// 当日路径
        	String curPath = uploadPath + yearMonStr + "/" + dayStr + "/";
        	// 文件路径
			String upFileNameNew = curPath + timestamp + "_" +originalFilename; //
			// 解压路径. 如果需要解压
			String unzipPath = curPath + timestamp +"/";
			
			try {
				//
				File path = new File(curPath);
				path.mkdirs();
				uploadfile.transferTo(new File(upFileNameNew));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//
			String logInfo = 
					"上传文件:" + uploadfile.getName() 
					+", 客户端filename: " + uploadfile.getOriginalFilename()
					+", 大小为: " + uploadfile.getSize();
			logInfo.toString();
			System.out.println(logInfo);
			//
			// 额外查询条件
			// 是否只执行插入
			String basePathfile = upFileNameNew;
			// zip 文件,解压缩
			if(null != upFileNameNew && upFileNameNew.trim().endsWith(".zip")){
				//
				String zipFileName = upFileNameNew;
				//
				try {
					// 解压
					DocZip.unzip(zipFileName, unzipPath);
					// 没出错,则设置值
					basePathfile = unzipPath;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//
			String insertonly = (String)request.getParameter("insertonly");
			// 
			//
			// 添加Bean
			DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
			bean.setBasePathfile(basePathfile);
			bean.setDdcid(ddcid);
			bean.setInsertonly(insertonly);
			//
			TaskBean taskBean = new TaskBean();
			boolean success = dataExportService.addDataImportRequest(bean, taskBean);
			//
			if(success) {
				info = "添加成功";
				message.setSuccess();

				String uuid = (String)request.getParameter("taskuuid");
				taskBeanMap.put(uuid, taskBean);
			}
			//
			message.setInfo(info);
		} else {
			message.setInfo(validError);
		}
		// 换成页面
		//
		//return message;

		return "dbms/dataimportsuccess";
	}


    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("name") String name,
        @RequestParam("file") MultipartFile file) {
    	//
    	//System.out.println("文件上传");
        //MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数
        if (!file.isEmpty()) {
            try {
				//byte[] bytes = file.getBytes();
				//InputStream inputStream = file.getInputStream();
            	//
            	String originalFilename = file.getOriginalFilename();
				file.transferTo(new File("/"+originalFilename + "_" + System.currentTimeMillis()));
				//
				System.out.println(
						"上传文件:" + file.getName() 
						+", 客户端filename: " + file.getOriginalFilename()
						+", 大小为: " + file.getSize()
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
            // store the bytes somewhere
           //在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹
           return "redirect:uploadSuccess";
       } else {
           return "redirect:uploadFailure";
       }
    }

    /**
     * 获取压缩文件列表
     */
    @RequestMapping("/getZipListInfoData.json")
	@ResponseBody
	public Object getZipListInfoData(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			//获取分页数据
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			//往map放值
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("start", String.valueOf(start));
			paramMap.put("end", String.valueOf(end));
			//
			int count = dbmsDaochuListInfoService.countZipDbmsDaochuListInfoData();// 根据map传参获取总条数
			data = dbmsDaochuListInfoService.getZipDbmsDaochuListInfoData(paramMap);
			resultmap.put("data", data);
			resultmap.put("total", count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
    
    
	/**
	 * 刷新
	 */	
	@RequestMapping("/refresh.json")
	@ResponseBody
	public Object refresh(HttpServletRequest request) {
		boolean rtnResult=false;
		JSONMessage message = new JSONMessage();
		String info = "刷新失败";
		//执行刷新操作
		this.setSessionAttribute(request, "percent", "数据处理中...");
		try{
			dbmsFileCopyService.archivesWatch(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		rtnResult=true;
		if(rtnResult){
			info = "刷新成功";
			message.setSuccess();
		}
		message.setInfo(info);
		this.setSessionAttribute(request, "percent", "100%");
		return message;
	}
	
	/**
	 * 批量文件解压
	 */	
	@RequestMapping("/batchUnzip.json")
	@ResponseBody
	public Object batchUnzip(HttpServletRequest request) {
		this.setSessionAttribute(request, "percent", "正在解压中...");
		Map<String, String> map = parseParamMapString(request);
		//获取传递过来的fileids
		String fileid = map.get("fileid");
		JSONMessage message = new JSONMessage();
		String info = "解压失败";
		//执行解压操作
		boolean rtnResult = dataExportService.batchUnzip(request,fileid);
		if(rtnResult){
			info = "解压成功";
			message.setSuccess();
		}
		message.setInfo(info);
		this.setSessionAttribute(request, "percent", "100%");
		return message;
	}
    /**
     * 获取解压文件列表
     */
    @RequestMapping("/getUnZipListInfoData.json")
	@ResponseBody
	public Object getUnZipListInfoData(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			//获取分页数据
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? "": Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			//往map放值
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("start", String.valueOf(start));
			paramMap.put("end", String.valueOf(end));
			//
			int count = dbmsDaochuListInfoService.countUnZipDbmsDaochuListInfoData();// 根据map传参获取总条数
			data = dbmsDaochuListInfoService.getUnZipDbmsDaochuListInfoData(paramMap);
			resultmap.put("data", data);
			resultmap.put("total", count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	/**
	 * 批量文件导入
	 */	
	@RequestMapping("/batchImport.json")
	@ResponseBody
	public Object batchImport(HttpServletRequest request) {
		this.setSessionAttribute(request, "percent", "数据导入中...");
		
		SystemUser user = this.getLoginUser(request);
		Map<String, String> map = parseParamMapString(request);
		JSONMessage message = new JSONMessage();
		String info = "导入失败";
		boolean rtnResult=false;
		//获取传递过来的fileids
		String fileid = map.get("fileid");
		String filePathIds[] = fileid.split(",");//切分fileids
		int count = 0;
		for (int i = 0; i < filePathIds.length; i++) {//遍历fileids，根据fileid获取解压文件的路径
			count ++;
			this.setSessionAttribute(request, "percent", "正在导入第" + count + "个包，共" + filePathIds.length + "个包");
		 
			String filePathId = filePathIds[i];
			DbmsDaochuListInfo daochuInfo = dbmsDaochuListInfoService.selectByPrimaryKey(filePathId);
			String zipfilepath = daochuInfo.getFilepath();
			zipfilepath=zipfilepath.substring(1);
			zipfilepath=GetAbsolutePath.getAbsolutePath(zipfilepath+File.separator);
			//执行导入
			rtnResult=dataTransferService.dataImport(request,zipfilepath, user);
			if(rtnResult){//导入成功之后更新Text7为已导入
			daochuInfo=new DbmsDaochuListInfo();
			daochuInfo.setCreatedate(new Date());
			daochuInfo.setFileid(filePathId);
			daochuInfo.setText7("1");
			dbmsDaochuListInfoService.updateByPrimaryKeySelective(daochuInfo);//更新状态
			}
		}//执行导入操作
		if(rtnResult){
			info = "导入成功";
			message.setSuccess();
		}
		
		this.setSessionAttribute(request, "percent", "100%");
		message.setInfo(info);
		return message;
	}
}
