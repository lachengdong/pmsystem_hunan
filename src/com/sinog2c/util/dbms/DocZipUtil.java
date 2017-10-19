package com.sinog2c.util.dbms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/***
 * 操作压缩文件
 * 
 * @author <a href="mailto:tangjunfeng52099@gmail.com">tangjunfeng</a>
 * 
 */
public class DocZipUtil {

	/**
	 * 压缩文件或文件夹
	 * @author 栾学峰
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
	 * 文件下载
	 * @param response
	 * @param fileNameForDownload	文件绝对路径
	 */
	public static void docDown(HttpServletResponse response,String fileNameForDownload) {
		byte[] fileblob = new byte[1024];
		File f = new File(fileNameForDownload);// 得到文件对象
		BufferedInputStream buff = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			buff = new BufferedInputStream(fs);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (response == null) {
			//response = ServletActionContext.getResponse();
		}
		long k = 0;
		// 得到文件带后缀的名称，截取绝对路径
		String name = fileNameForDownload.substring(fileNameForDownload.lastIndexOf("/") + 1, fileNameForDownload.length());
		try {
			response.setContentType("application/octec-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(name.getBytes(), "ISO-8859-1"));
			OutputStream outputStream;
				outputStream = response.getOutputStream();
			while (k < f.length()) {
				int j = buff.read(fileblob, 0, 1024);
				k += j;
				outputStream.write(fileblob, 0, j);
			}
			outputStream.flush();
			outputStream.close();
			outputStream = null;
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	* @author 栾学峰
	* @param zipFileName
	*     需要解压的文件地址(如：E:/a.zip)
	* @param extPlace
	*     解压后的文件地址(如:E:/)
	* @throws Exception
	*/
	public static void unzip(String zipFileName, String extPlace) throws Exception {  
	        try {  
	            (new File(extPlace)).mkdirs();
	            File f = new File(zipFileName);  
	            ZipFile zipFile = new ZipFile(zipFileName);  
	            if((!f.exists()) && (f.length() <= 0)) {  
	                throw new Exception("要解压的文件不存在!");  
	            }  
	            String strPath, gbkPath, strtemp;  
	            File tempFile = new File(extPlace);  
	            strPath = tempFile.getAbsolutePath();  
	            Enumeration e =null;
	            //e = zipFile.getEntries();  
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
	
	
	
	
	/**
     * 压缩文件分割
     * @author 栾学峰  		editBy YangZR (2016-06-02)
     * file：文件。
     * size：长度。
     */
    public static void splitFile(String fromFile,int size) throws Exception{
    	File file= new File (fromFile);
        if(size<=0){
            size = 1024;
        }
        if(!file.isFile()){
            throw new Exception("file not exists"+file.getAbsolutePath());
        }
        String filename = file.getAbsolutePath();
        File filetmp = new File(filename+"_"+0+".zip");
        if(filetmp.isFile()){
            throw new Exception("file exists"+filetmp.getAbsolutePath());
        }
        
        byte[] buf = new byte[1024*20*1024];//长度要给够
        FileInputStream fis = new FileInputStream(file);
        int readsize = 0;
        int pos = 0;
        int k = 0;
        int m = -1;
        File fileout = null;
        FileOutputStream fos = null;
        while((readsize = fis.read(buf, 0, buf.length))>0){
            
            if(k!=m)
            {
                if(fos!=null){
                    fos.close();
                    fos = null;
                }
                m = k;
                fileout = new File(filename+"_"+k+".zip");
                fos = new FileOutputStream(fileout);
            }
            fos.write(buf,0,readsize);
            fos.flush();
            pos += readsize;
            if(pos>size*(k+1)){
                k++;
            }
        }
        if(fos!=null){
            fos.close();
            fos = null;
        }
        fis.close();
    }
    
    
    /**
     * 压缩文件分割
     * @author 栾学峰  		editBy YangZR (2016-06-02)
     * file：文件。
     * size：长度。
     */
    public static void splitFile(String preFilepath,String descDirPath, int size) throws Exception{
    	File preFile = new File (preFilepath);
        if(size<=0){
            size = 1024;
        }
        if(!preFile.isFile()){
            throw new Exception("file not exists"+preFile.getAbsolutePath());
        }
        String filename = preFile.getName();
        filename = filename.substring(0, filename.lastIndexOf(".zip"));
        String splitFilePath = descDirPath + File.separator + filename;
        
        File filetmp = new File(splitFilePath+"_"+0+".zip");
        if(filetmp.isFile()){
            throw new Exception("file exists"+filetmp.getAbsolutePath());
        }
        
        byte[] buf = new byte[1024*1024];//长度要给够
        FileInputStream fis = new FileInputStream(preFile);
        int readsize = 0;
        int pos = 0;
        int k = 0;
        int m = -1;
        File fileout = null;
        FileOutputStream fos = null;
        while((readsize = fis.read(buf, 0, buf.length))>0){
            
            if(k!=m)
            {
                if(fos!=null){
                    fos.close();
                    fos = null;
                }
                m = k;
                fileout = new File(splitFilePath+"_"+k+".zip");
                fos = new FileOutputStream(fileout);
            }
            fos.write(buf,0,readsize);
            fos.flush();
            pos += readsize;
            if(pos>=size*(k+1)){
                k++;
            }
        }
        if(fos!=null){
            fos.close();
            fos = null;
        }
        fis.close();
    }
    
    
    /**
     * @Description: 合并文件
     * @author 栾学峰  		editBy YangZR (2016-06-02)
     * @param	combinationFolderPath 要合并的文件所在的目录
     * 			uploadDataPath		      合并后保存的位置
     */
    public static void combination(String combinationFolderPath,String uploadDataPath){
    	File fileDir= new File (combinationFolderPath);
        String filename = fileDir.getName();//合并后的文件名(不包含后缀)
        String filepath = uploadDataPath + File.separator + filename + ".zip";//合并后的文件全路径（包括文件名）
         
        PrintStream out = null;
        BufferedInputStream in = null;
        byte[] buf = new byte[1024*20*1024];//读取的长度
        int k = 0;
        try{
        	out = new PrintStream(filepath);
        	while(true){
            	String tempFilename = new StringBuilder(combinationFolderPath).append(File.separator)
            			.append(filename).append("_").append(k).append(".zip").toString();
            	File tempFile = new File(tempFilename);
            	if(tempFile.exists()){
            		in = new BufferedInputStream(new FileInputStream(tempFile));
            		int readsize = 0;
                	while((readsize = in.read(buf, 0, buf.length))>0){
                		out.write(buf,0,readsize);
                		out.flush();
                    }
                	if(in!=null){
                		in.close();
                		in = null;
                    }
                    k++;
            	}else{
            		break;
            	}
            }
        }catch(IOException e){
        	e.printStackTrace();
        }finally{
        	if(null != out){
            	out.close();
            	out = null;
            }
        }
    }
	
	public static void main(String[] args) {
//		String docPath = "C:/Program Files/Genuitec/Tomcat 6.0/webapps/jyzngzpt/dataxml/importxml/DBMS_DATAS_CHEME_NEW@1information20101026160049.rar";
//		String docName = "C:/Program Files/Genuitec/Tomcat 6.0/webapps/jyzngzpt/dataxml/importxml/DBMS_DATAS_CHEME_NEW@1information20101026160049";
//		String name="DBMS_CODELIST@100information20101026160049.xml";
//		String a=name.substring(name.lastIndexOf("@")+1,name.lastIndexOf("information"));
//		System.out.println(a);
		
		String combinationFolderPath = "G:\\apache-tomcat-8.0.24\\webapps\\pmsystem\\beforedata\\减刑假释报送法院数据20160603143929354";
		String uploadDataPath = "E:\\新建文件夹";
		combination(combinationFolderPath,uploadDataPath);
		//
	}
}
