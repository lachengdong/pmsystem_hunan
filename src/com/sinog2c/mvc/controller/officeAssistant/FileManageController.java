package com.sinog2c.mvc.controller.officeAssistant;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.model.officeAssistant.TbuserFileinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.controller.file.UploadFileUtils;
import com.sinog2c.service.api.officeAssistant.FileManageService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.MapUtil;

/**
 * 文件管理
 */
@Controller
@RequestMapping("/file")
public class FileManageController extends ControllerBase {

	@Autowired
	private FileManageService fileManageService;
	@Autowired
	public SystemLogService logService;

	// 打开页面
	@RequestMapping(value = "/manage.page")
	public ModelAndView FileManage(HttpServletRequest request) {
		String size = request.getParameter("size") == null ? "100" : request.getParameter("size");
		request.setAttribute("size", size);//文件大小改为在资源里配置，默认100M
		return new ModelAndView("officeAssistant/fileManage");
	}

	// 获取Tree
	@RequestMapping(value = "/ajaxAllFile")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getData(HttpServletRequest request) {
		String userId = getLoginUser(request).getUserid();
		Map<String, String> mm = new HashMap<String, String>();
		mm.put("filetype", "file");
		mm.put("opid", userId);
		List<Map> list = fileManageService.selectTree(mm);
		list = MapUtil.turnKeyToLowerCaseOfList(list);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Comparable> map = (HashMap<String, Comparable>) list.get(i);
				String fileType = map.get("filetype") == null ? "" : map.get("filetype").toString().toLowerCase();
				if ("file".equals(fileType)) {
					map.put("expanded", false);
					map.put("isLeaf", false);
					map.put("asyncLoad", false);
				}

				if (map.get("pfileid").toString().equals("-1")) {
					if (map.get("ishared").toString().equals("0")) {
						map.put("pfileid", "-2");
					} else {
						map.put("pfileid", "-3");
					}
				}
			}

		}

		HashMap<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("fileid", "-2");
		pmap.put("virtualname", "个人文件夹");
		pmap.put("pfileid", "-1");
		pmap.put("expanded", false);
		pmap.put("isLeaf", false);
		pmap.put("asyncLoad", false);
		list.add(pmap);

		HashMap<String, Object> pmap1 = new HashMap<String, Object>();
		pmap1.put("fileid", "-3");
		pmap1.put("virtualname", "共享文件夹");
		pmap1.put("pfileid", "-1");
		pmap1.put("expanded", false);
		pmap1.put("isLeaf", false);
		pmap1.put("asyncLoad", false);
		list.add(pmap1);
		return list;
	}

	@RequestMapping(value = "/GetFile")
	@ResponseBody
	public TbuserFileinfo getFile(String id) {
		TbuserFileinfo tbuserFileinfo = fileManageService.getfileInfo(id);
		return tbuserFileinfo;
	}

	// 新增修改文件夹弹框
	@RequestMapping(value = "/openFolderInsert")
	public ModelAndView addFile(HttpServletRequest request) {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		TbuserFileinfo file = fileManageService.getfileInfo(id);
		request.setAttribute("file", file);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("file", file);
		map.put("type", type);
		if(type.equals("add")){
			return new ModelAndView("officeAssistant/newFolderInsert",map);
		}else{
			return new ModelAndView(
					"officeAssistant/editFolder",map);
		}
	}

	// 展示数据
	@RequestMapping(value = "/ajaxFileByFolderIdList")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getFilelist(HttpServletRequest request) {
        //获取所选择的文件夹的id
		int fileId = Integer.parseInt(request.getParameter("id"));
		//页码：pageIndex,条数：pageSize/pageIndex
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		
		//转化计算
		int pageindex = Integer.parseInt(pageIndex);
		int pagesize = Integer.parseInt(pageSize);
		
		int endsize = pagesize*(pageindex+1);
		int startsize = pagesize*pageindex;
		//封装参数 进行分页查询
		Map<String, Comparable> mapparameter = new HashMap<String, Comparable>();
		mapparameter.put("start", startsize);
		mapparameter.put("end", endsize);
		mapparameter.put("fileId", fileId);
		mapparameter.put("file", "file");
		
		//根据条件查询出对应的结果
		
//		List<Map> list = fileManageService.selectData(fileId, "file");
		//查询出 当前页 的数据
		List<Map> listMap = fileManageService.selectFileData(mapparameter);
		//查询出 当前文件夹中所有的数据
		int count = fileManageService.selectFileDataCount(mapparameter);
		
		listMap = MapUtil.turnKeyToLowerCaseOfList(listMap);
		for (Map map : listMap) {
			map.put("optime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
					.format(map.get("optime")));
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("total", count);
		maps.put("data", listMap);
		return listMap;
	}
	/**
	 * 删除文件夹/文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteFileByIdList")
	@ResponseBody
	@SuppressWarnings("all")
	public int deleteFile(HttpServletRequest request) {
		String result = "success";
		short status = 1;
		int count = 0;
		SystemUser user = getLoginUser(request);
        String filedoc = request.getParameter("filedoc");
		String strid = request.getParameter("id");
		if(strid==null||"".equals(strid)){
			return count;
		}
		String[] ids = strid.split(",");
		List<String> idList = Arrays.asList(ids);
		//获取文件上传的路径啦，这样就可以删除啦
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("jyconfig.properties");
		Properties properties = new Properties();
		String path= "";
		try {
			properties.load(in);
			String tpath = properties.getProperty("filePath");
			path = request.getSession().getServletContext().getRealPath(tpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/**
		 *删除文件夹的时候，直接修改为删除状态即可 啦
		 */
		if ("file".equals(filedoc)) {
			count = fileManageService.updateFileIeaf(idList.get(0));
		}
		//如果选择 文件，那么直接删除文件即可啦
		if("doc".equals(filedoc)){
			List<Map> listMaps = fileManageService.selectFileName(idList);
			listMaps = MapUtil.turnKeyToLowerCaseOfList(listMaps);
			for (Map map : listMaps) {
				String realname = map.get("realname").toString();
				String filepath = path+"\\"+realname;
				//通过路径 读取文件
				File file = new File(filepath);
				//执行 删除 操作 
				file.delete();
				System.out.println("哈哈，文件-->"+realname+"已经被删除啦!");
			}
			count = fileManageService.deleteFile(idList);
		}
		
		SystemLog log = new SystemLog();
		log.setLogtype("文件管理操作");
		log.setOpaction("删除");
		log.setOpcontent("删除文件fileid=" + strid);
		log.setOrgid(user.getUserid());
		log.setRemark("文件删除操作");
		log.setStatus(status);
		try {
			//关闭流
			in.close();
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}

		return count;
	}
    /**
     * 方法描述：文件上传之前判断文件大小
     * @throws UnsupportedEncodingException 
     * 
     */
	@RequestMapping(value="judgeFileSize")
	@ResponseBody
	public int judgeFileSize(HttpServletRequest request){
		//获取 选择的文件相关的内容
		String filename = request.getParameter("filename");
		try {
			filename = java.net.URLDecoder.decode(filename, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		File file = new File(filename);
		
		//获取文件的大小
		long filesize = file.length();
		//把文件 由字节转化为M(兆)啦
		int fsize = (int)Math.floor(filesize/1024/1024);
		if (filesize > 100*1024*1024) {
			fsize += 1;
			System.out.println("文件："+file.getName()+",内容大于100M啦。。");
		}
		return fsize;
	}
	/**
	 * 文件上传
	 * 
	 * @author huzl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	public String uploadFile(DefaultMultipartHttpServletRequest request) {
		String result = "success";
		short status = 1;
		String biaosh = request.getParameter("biaoshi");
		System.out.println(biaosh);
		SystemUser user = getLoginUser(request);
		String folderid = request.getParameter("folderid");
		int pid = Integer.parseInt(folderid);
		String userid = getLoginUser(request).getUserid();
		CommonsMultipartFile file = (CommonsMultipartFile) request.getFile("file");
		/*
		if (file.isEmpty()) {
			result = "error";
			return result;
		}
		*/
		
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
			String saveName = userid + folderid +  format.format(date) + showName;
			
			String fileType = saveName.substring(saveName.lastIndexOf(".") + 1);
			UploadFileUtils.uploadFile(file, path, saveName);
			short left = fileManageService.getfileInfo(folderid)
					.getIshared();
			TbuserFileinfo tbfile = new TbuserFileinfo();
			tbfile.setVirtualname(showName);
			tbfile.setFiletype(fileType);
			tbfile.setPfileid(pid);
			tbfile.setRealname(saveName);
			tbfile.setIshared(left);
			tbfile.setIsleaf((short) 1);
			tbfile.setOpid(user.getUserid());
			tbfile.setOpname(user.getName());
			int count = fileManageService.insertSelective(tbfile);
			if (!(count > 0)) {
				result = "error";
				status = 0;
			}

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
		return "officeAssistant/fileManage";
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
		//此处无法 返回到 发送下载链接的页面，否者将会报错(错误原因，不太清楚)
//		return "officeAssistant/fileManage";
		return null;
	}


	// 新增文件夹保存
	@RequestMapping(value = "/savaNewFolder")
	@ResponseBody
	public String saveFile1(HttpServletRequest request) {
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		TbuserFileinfo file = new TbuserFileinfo();
		String virtualname = request.getParameter("virtualname")==null?"":request.getParameter("virtualname").trim();
		String ppfileid= request.getParameter("pfileid");
		//标识：new or  edit
		String newedit = request.getParameter("newedit");
		
		String fileid = request.getParameter("fileid");//主键
		//判断是 公共的还是个人的文件夹
		String grorgg = request.getParameter("grorgg");
		short ishared = 0;
		if (grorgg != null && !"".equals(grorgg)) {
			if("-3".equals(grorgg)){
				ishared =1;
			}
		}
		
		String remark = request.getParameter("remark");
		String filetype = request.getParameter("filetype")==null?"file":request.getParameter("filetype");
		
		file.setPfileid(Integer.parseInt(ppfileid));
		file.setVirtualname(virtualname);
		file.setFiletype(filetype);
		file.setIshared(ishared);
		file.setRemark(remark);
		file.setRealname(virtualname);
		file.setOpid(user.getUserid());
		file.setOptime(new Date());
		file.setOpname(user.getName());
		int count = 0;
		if ("new".equals(newedit)) {
			count = fileManageService.insertSelective(file);
		}else if("edit".equals(newedit)){
			count = fileManageService.updateFile(Integer.parseInt(fileid),virtualname,remark);
		}
		if (!(count > 0)) {
			result = "error";
			status = 0;
		}

		SystemLog log = new SystemLog();
		log.setLogtype("文件管理操作");
		log.setOpaction("新增");
		log.setOpcontent("新增文件" + virtualname);
		log.setOrgid(user.getUserid());
		log.setRemark("新增文件操作");
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}

		return result;
	}
    /**
     * 方法描述：修改获取对应的 内容
     * version :2014年10月23日11:30:39
     */
	@RequestMapping(value="selectFolderByFolderId")
	@ResponseBody
	@SuppressWarnings("all")
	public Object selectFolderByFolderId(HttpServletRequest request){
		Object object = null;
		Map<String, Object> map = null;
		try {
			List<Map> listMap = fileManageService.selectFolderByFolderId(request);
			//把map集合中的key值 转化为小写
			listMap = MapUtil.turnKeyToLowerCaseOfList(listMap);
			if (listMap.size() > 0) {
				map = listMap.get(0);
				object = new com.alibaba.fastjson.JSONObject(map).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 方法描述：判断文件夹是公共文件夹 还是个人文件夹
	 * @version 2014年10月23日16:05:48
	 */
	@RequestMapping(value="judgeIsharedByFileid")
	@ResponseBody
	@SuppressWarnings("all")
	public Object judgeIsharedByFileid(HttpServletRequest request){
		Object object = null;
		try {
			List<Map> list = fileManageService.judgeIsharedByFileid(request);
			if (list.size() > 0) {
				Map map = list.get(0);
				object=map.get("ISHARED");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
