package com.sinog2c.util.common;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class DocUtils {
	private Configuration configuration = null;  
    
    public DocUtils(){  
        configuration = new Configuration();  
        configuration.setDefaultEncoding("UTF-8");  
    }  
    /* action中这样写
    public String generateWord(){
  		 DocUtils test = new DocUtils(); 
		 String templatePath=request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/template/";
		 String zipPath=request.getSession().getServletContext().getRealPath("")+"\\templateZip\\";
		 String generateFilePath = request.getSession().getServletContext().getRealPath("").replace("\\", "/")+"/templateGenerateFile/";
		 
		 Map<String,Object> dataMap= this.getdata();//去关联查询方案。
	     String returnPath = test.createWord(templatePath,generateFilePath,"caidingshu",dataMap); //模版路径；生成文件路径；模版名称；数据； 
	     String zipFilePath = test.fileToZip(returnPath,zipPath, Math.random()*10000+"");//待压缩文件路径;打包后zip文件的存放路径;zip文件名
	     File file = new File(zipFilePath);
	     test.exportFile(response, file);
  	}
  	*/
   /**参数说明：
    * templatePath: 模版路径。generateFilePath：生成文件的路径。templateName：模版名称。dataMap：需要替换的数据
    * 返回值为：生成文件存放的文件夹的路径。
    */
    public String createWord(String templatePath, String generateFilePath,String templateName,Map<String,Object> dataMap){  
        try {
			configuration.setDirectoryForTemplateLoading(new File(templatePath));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        Template t=null;  
        try {  
            t = configuration.getTemplate(templateName+".ftl","utf-8"); //文件名  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        File outFile = new File(generateFilePath+"/outFilessa"+Math.random()*10000+".doc");  
        String returnPath =  outFile.getParentFile().getPath();
        Writer out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8")); 
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
           
        try {  
            t.process(dataMap, out); 
            
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{
        	//关闭流
            try {
				out.flush();
				out.close();	
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return returnPath;
    }  
  
    
    /**
     * 文件压缩
     * sourceFilePath：要压缩的文件路径。zipFilePath：压缩称zip后需要存入的路径。
     * 返回值：zip文件存放的绝对路径。
     */
    public String fileToZip(String sourceFilePath, String zipFilePath,
			String fileName) {
		String zipFilePathWithName = "";
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		if (sourceFile.exists() == false) {
			System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath
					+ " 不存在. <<<<<<");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(">>>>>> " + zipFilePath + " 目录下存在名字为："
							+ fileName + ".zip" + " 打包文件. <<<<<<");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath
								+ " 里面不存在文件,无需压缩. <<<<<<");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体,并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i]
									.getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
							bis.close();
							fis.close();//此处不关闭流 后边删除文件提示占用。
						}
						zipFilePathWithName = zipFile.getAbsolutePath();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != fis)
						fis.close();
					if (null != zos)
						zos.close();
					if (null != fos)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return zipFilePathWithName;
	}
    
    /**
     * 导出文件下载
     * file：需要下载的文件
     */
    public void exportFile(HttpServletResponse response, File file) {
		OutputStream out = null;
		InputStream in = null;
		try {
			String filename = URLEncoder.encode(file.getName(), "UTF-8");
			response.setContentType("application/force-download");
			response.setHeader("Location", filename);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename);
			out = response.getOutputStream();
			in = new FileInputStream(file.getPath());
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) != -1)
				out.write(buffer, 0, i);

		} catch (IOException e) {
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
				}
		}
	}
    
    /**
     * 遍历所给路径下的文件及子文件夹下的文件
     * @param filePath
     */
    public List<Map<String,String>> getWenshuZipList(String filePath){
    	List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
    	LinkedList<File> list = new LinkedList<File>();
    	File dir = new File(filePath);
    	File[] files = dir.listFiles();
    	File temp;
    	for(File file : files){
    		if(file.isDirectory()){//是文件夹
    			list.add(file);
    		}else{
    			Map<String,String> map = new HashMap<String, String>();
    			map.put("filename", file.getName());
    			map.put("filepath", file.getAbsolutePath());
    			fileList.add(map);
    		}
    	}
    	while(!list.isEmpty()){
    		temp = (File)list.removeFirst();
    		if(temp.isDirectory()){
    			files = temp.listFiles();
    			if(files == null){
    				continue;
    			}
    			for(File file : files){
    				if(file.isDirectory()){//是文件夹
    	    			list.add(file);
    	    		}else{
    	    			Map<String,String> map = new HashMap<String, String>();
    	    			map.put("filename", file.getName());
    	    			map.put("filepath", file.getAbsolutePath());
    	    			fileList.add(map);
    	    		}
    			}
    		}else{
    			Map<String,String> map = new HashMap<String, String>();
    			map.put("filename", temp.getName());
    			map.put("filepath", temp.getAbsolutePath());
    			fileList.add(map);
    		}
    	}
    	return fileList;
    }
    /**
     * 删除文件夹下的所有文件
     */
    public void deleteFile(String sPath) {
    	sPath = sPath.substring(0, sPath.length()-1);
    	sPath = sPath.replaceAll("/", "\\\\\\\\");
        File dirfile = new File(sPath);
        File[] files = dirfile.listFiles();
        for(File file : files){
        	if (file.isFile() && file.exists()) {  
                boolean deleteflag = file.delete();  
            }
        }
    }
    /**
     * 获取ftl模版文件list
     * @param templatePath 模版文件的路径
     * @return
     */
	public List<Map<String, String>> getTemplateList(String templatePath) {
    	List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
    	LinkedList<File> list = new LinkedList<File>();
    	File dir = new File(templatePath);
    	File[] files = dir.listFiles();
    	File temp;
    	for(File file : files){
    		if(file.isDirectory()){//是文件夹
    			list.add(file);
    		}else{
    			Map<String,String> map = new HashMap<String, String>();
    			map.put("filename", file.getName());
    			map.put("filepath", file.getAbsolutePath());
    			fileList.add(map);
    		}
    	}
    	while(!list.isEmpty()){
    		temp = (File)list.removeFirst();
    		if(temp.isDirectory()){
    			files = temp.listFiles();
    			if(files == null){
    				continue;
    			}
    			for(File file : files){
    				if(file.isDirectory()){//是文件夹
    	    			list.add(file);
    	    		}else{
    	    			Map<String,String> map = new HashMap<String, String>();
    	    			map.put("filename", file.getName());
    	    			map.put("filepath", file.getAbsolutePath());
    	    			fileList.add(map);
    	    		}
    			}
    		}else{
    			Map<String,String> map = new HashMap<String, String>();
    			map.put("filename", temp.getName());
    			map.put("filepath", temp.getAbsolutePath());
    			fileList.add(map);
    		}
    	}
    	return fileList;
    }  
	
	
}
