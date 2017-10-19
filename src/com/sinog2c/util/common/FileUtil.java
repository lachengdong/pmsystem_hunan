package com.sinog2c.util.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import srvSeal.PdfAutoSeal;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 文件工具类
 * @author liuyi
 * @version 2014-09-03 10:58:50
 */
@SuppressWarnings("unchecked")
public class FileUtil {
	
	private FileUtil(){}
	/**
	   * 列出目录中的所有内容，包括其子目录中的内容。
	   * @param file 要列出的目录
	   * @param filter 过滤器
	   * @return 目录内容的文件数组。
	   */
	public static File[] listAll(File file, javax.swing.filechooser.FileFilter filter) {
		    ArrayList list = new ArrayList();
		    File[] files;
		    if (!file.exists() || file.isFile()) {
		      return null;
		    }
		    list(list, file, filter);
		    files = new File[list.size()];
		    list.toArray(files);
		    return files;
	  }
	  /**
	   * 将目录中的内容添加到列表。
	   * @param list 文件列表
	   * @param filter 过滤器
	   * @param file 目录
	   */
	  private static void list(ArrayList list, File file, javax.swing.filechooser.FileFilter filter) {
		    if (filter.accept(file)) {
		    	list.add(file);
		      if (file.isFile()) {
		        return;
		      }
		    }
		    if (file.isDirectory()) {
		      File files[] = file.listFiles();
		      for (int i = 0; i < files.length; i++) {
		        list(list, files[i], filter);
		      }
		    }
	  }
	  
		/**   
		 * 此方法描述的是：
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
		 * @author: 栾学峰 
		 * @version: 2015-1-29 下午01:54:50   
		 */

	    public static void main(String args[]) throws IOException {
	        // 源文件夹
	        String url1 = "F:\\test\\infile0";
	        // 目标文件夹
	        String url2 = "F:\\test\\outfile0";
	        String base64Code = "aaaaa";
	        try {
	        	url2 = url2 + File.separator + "bb.txt";
//				saveToFile(base64Code, url2,"utf-8");
	        	copyDirectioryAipToPdf("D:\\Workspaces\\TestOracle1\\aip", "D:\\Workspaces\\TestOracle1\\pdf");
			} catch (Exception e) {
				e.printStackTrace();
			}
//	        batchCopyFile(url1,url2);
	    }
	    public static void batchCopyFileAipToPdf(String srcFilePath,String desFilePath) throws IOException {
	    	// 创建目标文件夹
	    	File desFile = new File(desFilePath);
	    	if (!desFile.exists()) {
	    		desFile.mkdirs();
	    	}
	    	// 获取源文件夹当前下的文件或目录
	    	File[] file = (new File(srcFilePath)).listFiles();
	    	if (file == null) {
	    		return;
	    	}
	    	for (int i = 0; i < file.length; i++) {
	    		if (file[i].isFile()) {
	    			// 复制文件
	    			String type = file[i].getName().substring(file[i].getName().lastIndexOf(".") + 1);
	    			desFilePath += File.separator;
	                String strFileName = file[i].getName();
	                int intIndex = strFileName.indexOf(".");
	                String strPdfName = strFileName + ".pdf";
	                if (intIndex > -1) {
	                	strPdfName = strFileName.substring(0,intIndex) + ".pdf";
	                }
	    			if (type.equalsIgnoreCase("txt")){// 转码处理
//	                    copyFile(file[i], new File(url2 + file[i].getName()), "utf-8", "gbk");
	    				copyFileAipToPdf(file[i], new File(desFilePath + strPdfName));
	    			} else {
	    				copyFileAipToPdf(file[i], new File(desFilePath + strPdfName));
	    			}
	    		}
	    		if (file[i].isDirectory()) {
	    			// 复制目录
	    			String sourceDir = srcFilePath + File.separator + file[i].getName();
	    			String targetDir = desFilePath + File.separator + file[i].getName();
	    			copyDirectioryAipToPdf(sourceDir, targetDir);
	    		}
	    	}
	    }
	    public static void batchCopyFile(String srcFilePath,String desFilePath) throws IOException {
	    	// 创建目标文件夹
	    	File desFile = new File(desFilePath);
	    	if (!desFile.exists()) {
	    		desFile.mkdirs();
	    	}
	    	// 获取源文件夹当前下的文件或目录
	    	File[] file = (new File(srcFilePath)).listFiles();
	    	if (file == null) {
	    		return;
	    	}
	    	for (int i = 0; i < file.length; i++) {
	    		if (file[i].isFile()) {
	    			// 复制文件
	    			String type = file[i].getName().substring(file[i].getName().lastIndexOf(".") + 1);
	    			
	    			desFilePath += File.separator;
	    			if (type.equalsIgnoreCase("txt")){// 转码处理
//	                    copyFile(file[i], new File(url2 + file[i].getName()), "utf-8", "gbk");
	    				copyFile(file[i], new File(desFilePath + file[i].getName()));
	    			} else {
	    				copyFile(file[i], new File(desFilePath + file[i].getName()));
	    			}
	    		}
	    		if (file[i].isDirectory()) {
	    			// 复制目录
	    			String sourceDir = srcFilePath + File.separator + file[i].getName();
	    			String targetDir = desFilePath + File.separator + file[i].getName();
	    			copyDirectiory(sourceDir, targetDir);
	    		}
	    	}
	    }

	    // 复制文件
	    public static void copyFileAipToPdf(File sourceFile, File targetFile) throws IOException {
	        BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        try {
	        	String strCotents = readFromFile(sourceFile.getPath(),"utf-8");
	  		    PdfAutoSeal.aipAutoPdf("STRDATA:"+strCotents,targetFile.getPath());
	        } catch(Exception e){
	        	e.printStackTrace();
	        }
	    }
	    
	    // 复制文件
	    public static void copyFile(File sourceFile, File targetFile) throws IOException {
	        BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        try {
	            // 新建文件输入流并对它进行缓冲
	            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

	            // 新建文件输出流并对它进行缓冲
	            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

	            // 缓冲数组
	            byte[] b = new byte[1024 * 5];
	            int len;
	            while ((len = inBuff.read(b)) != -1) {
	                outBuff.write(b, 0, len);
	            }
	            // 刷新此缓冲的输出流
	            outBuff.flush();
	        } finally {
	            // 关闭流
	            if (inBuff != null)
	                inBuff.close();
	            if (outBuff != null)
	                outBuff.close();
	        }
	    }

	    // 复制文件夹
	    public static void copyDirectioryAipToPdf(String sourceDir, String targetDir) throws IOException {
	        // 新建目标目录
	    	File desFile = new File(targetDir);
	    	if (!desFile.exists()) {
	    		desFile.mkdirs();
	    	}
	        // 获取源文件夹当前下的文件或目录
	        File[] file = (new File(sourceDir)).listFiles();
	        for (int i = 0; i < file.length; i++) {
	            if (file[i].isFile()) {
	                // 源文件
	                File sourceFile = file[i];
	                String strFileName = file[i].getName();
	                int intIndex = strFileName.indexOf(".");
	                String strPdfName = strFileName + ".pdf";
	                if (intIndex > -1) {
	                	strPdfName = strFileName.substring(0,intIndex) + ".pdf";
	                }
	                // 目标文件
	                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + strPdfName);
	                copyFileAipToPdf(sourceFile, targetFile);
	            }
	            if (file[i].isDirectory()) {
	                // 准备复制的源文件夹
	                String dir1 = sourceDir + File.separator + file[i].getName();
	                // 准备复制的目标文件夹
	                String dir2 = targetDir + File.separator + file[i].getName();
	                copyDirectioryAipToPdf(dir1, dir2);
	            }
	        }
	    }
	    
	    // 复制文件夹
	    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
	        // 新建目标目录
	    	File desFile = new File(targetDir);
	    	if (!desFile.exists()) {
	    		desFile.mkdirs();
	    	}
	        // 获取源文件夹当前下的文件或目录
	        File[] file = (new File(sourceDir)).listFiles();
	        for (int i = 0; i < file.length; i++) {
	            if (file[i].isFile()) {
	                // 源文件
	                File sourceFile = file[i];
	                // 目标文件
	                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
	                copyFile(sourceFile, targetFile);
	            }
	            if (file[i].isDirectory()) {
	                // 准备复制的源文件夹
	                String dir1 = sourceDir + File.separator + file[i].getName();
	                // 准备复制的目标文件夹
	                String dir2 = targetDir + File.separator + file[i].getName();
	                copyDirectiory(dir1, dir2);
	            }
	        }
	    }

	    /**
	     * 
	     * @param srcFileName
	     * @param destFileName
	     * @param srcCoding
	     * @param destCoding
	     * @throws IOException
	     */
//	    public static void copyFile(File srcFileName, File destFileName, String srcCoding, String destCoding) throws IOException {// 把文件转换为GBK文件
//	        BufferedReader br = null;
//	        BufferedWriter bw = null;
//	        try {
//	            br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileName), srcCoding));
//	            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFileName), destCoding));
//	            char[] cbuf = new char[1024 * 5];
//	            int len = cbuf.length;
//	            int off = 0;
//	            int ret = 0;
//	            while ((ret = br.read(cbuf, off, len)) > 0) {
//	                off += ret;
//	                len -= ret;
//	            }
//	            bw.write(cbuf, 0, off);
//	            bw.flush();
//	        } finally {
//	            if (br != null)
//	                br.close();
//	            if (bw != null)
//	                bw.close();
//	        }
//	    }

	    /**
	     * 
	     * @param filepath
	     * @throws IOException
	     */
	    public static void del(String filepath) throws IOException {
	        File f = new File(filepath);// 定义文件路径
	        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
	            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
	                f.delete();
	            } else {// 若有则把文件放进数组，并判断是否有下级目录
	                File delFile[] = f.listFiles();
	                int i = f.listFiles().length;
	                for (int j = 0; j < i; j++) {
	                    if (delFile[j].isDirectory()) {
	                        del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
	                    }
	                    delFile[j].delete();// 删除文件
	                }
	            }
	        }
	    }
	    
		/**
		  * 将文件转成base64 字符串
		  * @param path文件路径
		  * @return  * 
		  * @throws Exception
		  */

		public static String encodeBase64File(String path) throws Exception {
			File file = new File(path);
			return encodeBase64File(file);
		}
		
		
		/**
		  * 将文件转成base64 字符串
		  * @param path文件路径
		  * @return  * 
		  * @throws Exception
		  */

		public static String encodeBase64File(File file) throws Exception {
			if(file.exists()){
				FileInputStream inputFile = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				inputFile.read(buffer);
				inputFile.close();
				return new BASE64Encoder().encode(buffer).replaceAll("\r|\n", "");
			}
			
			return null;
	
		}
		
	
		/**
		 * 将base64字符解码保存文件
		 * 
		 * @param base64Code
		 * @param targetPath
		 * @throws Exception
		 */
	
		public static void decoderBase64File(String base64Code, String targetPath)
				throws Exception {
			byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
			FileOutputStream out = new FileOutputStream(targetPath);
			out.write(buffer);
			out.close();
	
		}
		
		
		/**
		 * 将字符串保存文件
		 * 
		 * @param base64Code
		 * @param tath
		 * @throws Exception
		 */
		
		public static void saveToFile(String strContent, String targetPath,String destCoding)
		throws Exception {
	        PrintWriter pw = null;
	        try {
		    	File desFile = new File(targetPath);
		    	(new File(desFile.getParent())).mkdirs();
				pw = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(desFile), destCoding));
				pw.println(strContent);
		
				pw.flush();
				pw.close();
	        } finally {
	            if (pw != null)
	            	pw.close();
	        }
		}
		
		/**
		  * 将文件转成 字符串
		  * @param path文件路径
		  * @return  * 
		  * @throws Exception
		  */

		public static String readFromFile(String path,String srcCoding) throws Exception {
			StringBuffer sb = new StringBuffer();
			File inFile = new File(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(inFile), srcCoding));
			String strContent = null;
			while ((strContent = reader.readLine()) != null) {
				sb.append(strContent);
			}
			if (reader != null) {
				reader.close();
			}
			
			return sb.toString();
		}
		
		
	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * @param sPath 要删除的目录或文件
	 *	@return 删除成功返回 true，否则返回 false。
	 */
	public static boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}
	
	/** 
	* 删除单个文件 
	* @param sPath 被删除文件的文件名 
	* @return 单个文件删除成功返回true，否则返回false 
	*/ 
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	
	/** 
	* 删除目录（文件夹）以及目录下的文件 
	* @param sPath 被删除目录的文件路径 
	* @return 目录删除成功返回true，否则返回false 
	*/ 
	public static boolean deleteDirectory(String sPath) {
		boolean flag = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
   /**
    * 
    * <p>Title:</p>
    * @Description: 删除指定文件夹下所有文件
    * @param
    * @return boolean  
    * @throws
    * @author 刘晨
    * @date 2016年5月31日  下午2:57:56
    * @Version 3.1
    */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
				flag = true;
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	* 验证路径是否合法
	* @param sPath 为路径字符串
	* @return 正确路径返回true，否则返回false 
	*/ 
	public static boolean validateFilePath(String sPath) {
		// 验证字符串是否为正确路径名的正则表达式
		String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
		// 通过 sPath.matches(matches) 方法的返回值判断是否正确
		return sPath.matches(matches);

	}

	
	public static String getFileUrl(String url,String servletPath,String fileName,String uploadDirName){
		String fileUrl = "";
		fileUrl = url.substring(0,url.indexOf(servletPath))+"/"+uploadDirName+"/"+fileName;
		return fileUrl;
	}
	/**
     * 方法描述：获取二进制文件流
     * @param file
     * @return
     */
	public static byte[] getBuffer(String imgPath){
		//读取生成的图片 保存到数据库中
		File file = new File(imgPath);
		InputStream is = null;
		
		byte[] buffer = null;
		try{
			is = new FileInputStream(file);
			buffer = new byte[is.available()];
			is.read(buffer, 0, buffer.length);
		}catch(Exception e1){
			e1.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}

	public static void writeToFile(String fileName, String fileContent){
		writeToFile(new File(fileName), fileContent);
	}

	public static void writeToFile(File file, String fileContent){
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(fileContent);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	   /**
	    * 将XML文件转为String字符串
	    * @author liuyi
	    * @return String
	    * @throws Exception
	    * @version 2016年3月19日 21:55:32
	    */
	   public static String getXmlStringByXmlFile(File xmlFile)throws Exception{
		   SAXReader reader = new SAXReader();
		   org.dom4j.Document document = (org.dom4j.Document) reader.read(xmlFile); 
		   String xmlStr = document.asXML();
		   return xmlStr;
	   }
	   
	   /**
	    * 文件内容字符串
	    * @author liuyi
	    * @return String
	    * @throws Exception
	    * @version 2016年3月19日 21:55:32
	    */
	   public static String getFileContentByFilePath(String filePath)throws Exception{
		   StringBuffer sb = new StringBuffer();
	        readToBuffer(sb, filePath);
	        return sb.toString();
	   }
	   
	   /**
	     * 将文本文件中的内容读入到buffer中
	     * @param buffer buffer
	     * @param filePath 文件路径
	     */
	    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
	        InputStream is = new FileInputStream(filePath);
	        String line; // 用来保存每行读取的内容
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	        line = reader.readLine(); // 读取第一行
	        while (line != null) { // 如果 line 为空说明读完了
	            buffer.append(line); // 将读到的内容添加到 buffer 中
	            buffer.append("\n"); // 添加换行符
	            line = reader.readLine(); // 读取下一行
	        }
	        reader.close();
	        is.close();
	    }
	   
	   /**
	    * 将String字符串转为XML对象
	    * @author liuyi
	    * @return String
	    * @throws Exception
	    * @version 2016年3月19日 21:55:32
	    */
	   public static org.dom4j.Document createXmlFileByXmlStr(String xmlStr)throws Exception{
		   org.dom4j.Document document = DocumentHelper.parseText(xmlStr); 
		   return document;
	   }
	   
	   /**
	    * 判断是否存在此路径，若不存在根据路径创建文件夹
	    * @param path
	    * @return
	    */
	   public static boolean createDirIfNotExist(String path){
		   boolean flag = false;
		   File file = new File(path);
	    	if (!file .exists()  && !file .isDirectory()) {
	    		file.mkdirs();
	    		flag = true;
	    	}else{
	    		flag = true;
	    	}
	    	return flag;
	   }
	   
	   
	   
	   /**
	     * 遍历目录下及其子目录下的所有文件、目录，不包括dirPath目录
	     * @param dirPath 目录全路径
	     */
	    public static ArrayList<File> listFile(File dirPath){
	    	ArrayList<File> fileList = new ArrayList<File>();
	    	File[] files = dirPath.listFiles();
	    	for(File file : files){
	    		fileList.add(file);
	    		if(file.isDirectory()){
	    			fileList.addAll(listFile(file));
	    		}
	    	}
	    	
	    	return fileList;
	    }
}
