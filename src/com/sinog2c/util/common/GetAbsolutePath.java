package com.sinog2c.util.common;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class GetAbsolutePath {

	/**
	 * 此方法描述的是：参数为项目跟路径开始的url
	 * 
	 * @param name
	 *            /WEB-INF/classes/com/gkzx/dbmsnew/applicationContext-actions.xml
	 * @param @return
	 * @Exception 异常对象
	 * @author: 李祺亮
	 * @version: 2013-6-27 下午05:34:22
	 */

	public static String getAbsolutePath(String append) {
		if (append == null) {
			append = "";
		}
		if (append == null) {
			append = "";
		}
		GetAbsolutePath.class.getClassLoader().getResource("/");
		String classPath = GetAbsolutePath.class.getClassLoader().getResource("/").getPath();
		try {
			classPath = java.net.URLDecoder.decode(classPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes")) + "/";
			//rootPath = rootPath.replaceAll("/", "\\");
			//append = append.replaceAll("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes")) + "\\";
			//rootPath = classPath.replaceAll("\\", "/");
			//append = append.replaceAll("\\", "/");
		}
		return rootPath + append;
	}
	/**
	 * 此方法描述的是：参数为项目跟路径开始的url
	 * 
	 * @param strPath
	 *            D:\\CriminalArchives
	 * @param append
	 *            /CriminalArchives\6102\4413025542\272014282015841456102.txt
	 * @param @return
	 *           D:\\CriminalArchives\CriminalArchives\6102\4413025542\272014282015841456102.txt
	 * @Exception 异常对象
	 * @author: 李祺亮
	 * @version: 2013-6-27 下午05:34:22
	 */
	
	public static String getAbsolutePathAppend(String strPath, String append) {
		if (strPath == null) {
			strPath = "";
		}
		if (append == null) {
			append = "";
		}
		GetAbsolutePath.class.getClassLoader().getResource("/");
		String classPath = GetAbsolutePath.class.getClassLoader().getResource("/").getPath();
		try {
			classPath = java.net.URLDecoder.decode(classPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
			rootPath = classPath.substring(1, classPath.indexOf("/webapps")) ;
			rootPath = rootPath.replace("/", "\\");
			append = append.replace("/", "\\");
			strPath = strPath.replace("/", "\\");
//			rootPath = strPath + 
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes")) ;
			rootPath = classPath.substring(0, classPath.indexOf("/webapps"));
			rootPath = classPath.replace("\\", "/");
			append = append.replace("\\", "/");
			strPath = strPath.replace("\\", "/");
		}
		if (StringNumberUtil.isNullOrEmpty(strPath)) {
			strPath = rootPath;
		}
		return strPath + File.separator + append;
	}
	
	/**
	 * 返回项目的路径	YangZR		2015-11-06
	 * @param request
	 * @return
	 */
	public static String getProjectPath(HttpServletRequest request){
		String url = request.getSession().getServletContext().getRealPath("");
		return url;
	}

}
