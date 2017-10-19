package com.sinog2c.util.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/***
 * 操作压缩文件
 * 
 * @author <a href="mailto:tangjunfeng52099@gmail.com">tangjunfeng</a>
 * 
 */
public class DocZip {

	/**
	 * 压缩文件或文件夹
	 * 
	 * @param srcPathName
	 *            需要压缩的文件或文件夹(如：E:/test E:/test/test.txt)
	 * @param ZipFile
	 *            压缩后的文件存放地址(如：E:/test.zip E:/test.rar)
	 */
	public static void compress(String srcPathName, String ZipFile) {
		File srcdir = new File(srcPathName);
		File zipfile = new File(ZipFile);
		// 需要压缩的文件或文件夹是否存在
		if (!srcdir.exists()) {
			throw new RuntimeException(srcPathName + "不存在！");
		} else {
			if (!zipfile.exists()) {
				Project prj = new Project();
				Zip zip = new Zip();
				zip.setProject(prj);
				zip.setDestFile(zipfile);
				FileSet fileSet = new FileSet();
				fileSet.setProject(prj);
				fileSet.setDir(srcdir);
				zip.addFileset(fileSet);// 添加文件或文件夹
				zip.execute();// 进行压缩
			} else {
				throw new RuntimeException(zipfile + "已经存在！");
			}
		}
	}


	/**
	 * 复制单个文件
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
//			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	* 进行压缩包的解压
	* @param zipFileName
	*     需要解压的文件地址(如：E:/a.zip)
	* @param unzipPath
	*     解压后的文件地址(如:E:/)
	* @throws Exception
	*/
	public static void unzip(String zipFileName, String unzipPath) throws Exception {  
	        try {  
	            (new File(unzipPath)).mkdirs();
	            File f = new File(zipFileName);  
	            ZipFile zipFile = new ZipFile(zipFileName);  
	            if((!f.exists()) && (f.length() <= 0)) {  
	                throw new Exception("要解压的文件不存在!");  
	            }  
	            String strPath, gbkPath, strtemp;  
	            File tempFile = new File(unzipPath);  
	            strPath = tempFile.getAbsolutePath();  
	            Enumeration e = zipFile.getEntries();  
	            while(e.hasMoreElements()){  
	                ZipEntry zipEnt = (ZipEntry) e.nextElement();  
	                gbkPath=zipEnt.getName();  
	                if(zipEnt.isDirectory()){  
	                    strtemp = strPath + File.separator + gbkPath;  
	                    File dir = new File(strtemp);  
	                    dir.mkdirs();  
	                    continue;  
	                } else {  
	                    //读写文件  
	                    InputStream is = zipFile.getInputStream(zipEnt);  
	                    BufferedInputStream bis = new BufferedInputStream(is);  
	                    gbkPath=zipEnt.getName();  
	                    strtemp = strPath + File.separator + gbkPath;  
	                    //建目录  
	                    String strsubdir = gbkPath;  
	                    for(int i = 0; i < strsubdir.length(); i++) {  
	                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {  
	                            String temp = strPath + File.separator + strsubdir.substring(0, i);  
	                            File subdir = new File(temp);  
	                            if(!subdir.exists())  
	                            subdir.mkdir();  
	                        }  
	                    }  
	                    FileOutputStream fos = new FileOutputStream(strtemp);  
	                    BufferedOutputStream bos = new BufferedOutputStream(fos);  
	                    int c;  
	                    while((c = bis.read()) != -1) {  
	                        bos.write((byte) c);  
	                    }
	                    bis.close();
	                    is.close();
	                    bos.flush();
	                    bos.close();  
	                    fos.flush();
	                    fos.close();  
	                }  
	            }
	            zipFile.close();
	        } catch(Exception e) {  
	            throw new Exception("文件已被非法篡改！"); 
	        }
	    }
	
	public static void main(String[] args) {
		String docPath = "C:/Program Files/Genuitec/Tomcat 6.0/webapps/jyzngzpt/dataxml/importxml/DBMS_DATAS_CHEME_NEW@1information20101026160049.rar";
		String docName = "C:/Program Files/Genuitec/Tomcat 6.0/webapps/jyzngzpt/dataxml/importxml/DBMS_DATAS_CHEME_NEW@1information20101026160049";
		String name="DBMS_CODELIST@100information20101026160049.xml";
		String a=name.substring(name.lastIndexOf("@")+1,name.lastIndexOf("information"));
//		System.out.println(a);
	}
}
