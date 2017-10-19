package com.sinog2c.util.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * 项目名称：pmsystem
 * 类描述：FTP客户端对FTP服务器端的文件、目录的相关操作
 * 作者：杨祖榕
 * 创建时间：2016-6-2 下午04:13:24
 * 版本号：V3.1
 */
public class FtpUtil {
	
	private static Logger logger = Logger.getLogger(FtpUtil.class);
	
	/**
	 * @Description: 获取FTP的链接
	 * @param
	 * @return FTPClient
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-2  上午10:51:40
	 * @Version 3.1
	 */
	public static FTPClient getFTPClient(String url, int port, String username,String password){
		//
		FTPClient ftp = new FTPClient();// 创建FTPClient对象
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftp.setControlEncoding("GBK");
		ftp.configure(ftpClientConfig);
		
		try{
			// 连接FTP服务器	如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			//登录ftp
			ftp.login(username, password);
			
			// 设置传输协议
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			ftp.setBufferSize(2048);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return ftp;
	}
	
	public static boolean isLoginSuccess(FTPClient ftp) throws IOException{
		if(null == ftp){
			return false;
		}
		int reply = ftp.getReplyCode();//看返回的值是不是230，如果是，表示登陆成功
		// 以2开头的返回值就会为真
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return false;
		}
		return true;
	}
	
	
	private static void closeInputStream(InputStream input){
		if(null != input){
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void closeOutputStream(OutputStream output){
		if(null != output){
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeFTPClient(FTPClient ftp){
		if (null !=ftp && ftp.isConnected() ) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传程序方法
	 * ftppath :FTP服务器主目录的相对目录	格式如：\aaa
	 * localFilepath : 要上传文件的全路径	格式如：D:\bbb.zip
	 */
	public static boolean uploadFile(FTPClient ftp,String ftppath,String localFilepath){
		
		boolean success = false;
		BufferedInputStream input = null;
		try {
			File localFile = new File(localFilepath);
			String localFileName = localFile.getName();
			input = new BufferedInputStream(new FileInputStream(localFile));
			//
			ftp.changeWorkingDirectory(ftppath);//FTP服务器主目录的相对目录
//			FTPFile[] fs = ftp.listFiles(); //得到目录的相应文件列表
//			//
//			localFileName = FtpUtil.changeName(localFileName, fs);
//			localFileName = new String(localFileName.getBytes("GBK"),"ISO-8859-1");
//			ftppath = new String(ftppath.getBytes("GBK"), "ISO-8859-1");
//			ftp.changeWorkingDirectory(ftppath);// 转到指定上传目录
//			//
//			ftp.setFileType(FTP.BINARY_FILE_TYPE);// 如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
			ftp.storeFile(localFileName, input);
			//
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeInputStream(input);
		}
		return success;
	}
	
	
	/***
	 * @上传文件夹
	 * @param localDir 当地文件夹	如：D:\aaa	
	 * @param ftppath :FTP服务器主目录的相对目录	如：\bbb
	*/
	public static void uploadDirectory(FTPClient ftp,String localDir,String ftppath) {
		File localDirFile = new File(localDir);
		if(localDirFile.isFile()){
			return;
		}
		//
		try {
			ftppath = new StringBuilder(ftppath).append(File.separator).append(localDirFile.getName()).toString();
//			ftppath = new String(ftppath.getBytes("GBK"), "ISO-8859-1");
			ftp.makeDirectory(ftppath);
//			ftppath = new String(ftppath.getBytes("ISO-8859-1"), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		File[] fileList = localDirFile.listFiles();
		for (File file : fileList) {
			String localFilepath = file.getAbsolutePath();
			if (file.isFile()) {
				uploadFile(ftp,ftppath,localFilepath);
			}else if(file.isDirectory()){
				uploadDirectory(ftp, localFilepath,ftppath);
			}
		}
	}
	

	/**
	 * 删除程序
	 * ftppath :FTP服务器主目录的相对目录	如：\aaa
	 * filename ：要删除的文件名	如：abc.zip
	 */
	public static boolean deleteFile(FTPClient ftp,String ftppath, String filename){
		boolean success = false;// 初始表示上传失败
		try {
//			filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
//			ftppath = new String(ftppath.getBytes("GBK"), "ISO-8859-1");
			ftp.changeWorkingDirectory(ftppath);// 转到指定上传目录
			ftp.deleteFile(filename);
			//
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * @Description: 从FTP服务器某目录下载文件
	 * @param	ftppath：FTP服务器主目录的相对目录	如：\aaa
	 * 			fileName：要下载的文件名			如：\abc.zip
	 * 			localDirPath：下载后要保存的目录	如：D:\tt
	 * @author 杨祖榕
	 * @date 2016-6-2  下午01:14:59
	 * @Version 3.1
	 */
	public static boolean downloadFile(FTPClient ftp,String ftppath, String fileName,String localDirPath){
		
		boolean success = false;
		String strFilePath = localDirPath + File.separator + fileName;
		BufferedOutputStream outStream = null;
		
		try {
//			strFilePath = new String(strFilePath.getBytes("GBK"),"ISO-8859-1");
			outStream = new BufferedOutputStream(new FileOutputStream(strFilePath));
			//
//			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.changeWorkingDirectory(ftppath);//FTP服务器主目录的相对目录
			//
			ftp.retrieveFile(fileName, outStream);//将文件保存到输出流outputStream中
			outStream.flush();
			//
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeOutputStream(outStream);
		}
		return success;
	}
	
	
	/**
	 * @Description: 从FTP服务器下载文件夹
	 * @param	remoteDirPath：要下载的目录	格式如下：\downloadDir
	 * 			localDirPath：下载后要保存的目录	格式如下：D:\\saveDir
	 * @author 杨祖榕
	 * @date 2016-6-2  下午01:14:59
	 * @Version 3.1
	 */
	public static boolean downLoadDirectory(FTPClient ftp,String remoteDirPath,String localDirPath) {
		try {
			String fileName = new File(remoteDirPath).getName();
			localDirPath = localDirPath + File.separator + fileName;
			File localDir = new File(localDirPath);
			if(! localDir.exists()){
				localDir.mkdir();
			}
			//
			FTPFile[] ftpFileList = ftp.listFiles(remoteDirPath);
			for (FTPFile ftpFile : ftpFileList) {
				String ftpFileName = ftpFile.getName();
				if(".".equals(ftpFileName) || "..".equals(ftpFileName)){
					continue;
				}
				if(ftpFile.isDirectory()){
					downLoadDirectory(ftp, remoteDirPath + File.separator + ftpFileName, localDirPath);
				}else{
					downloadFile(ftp,remoteDirPath, ftpFileName,localDirPath);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	

	/**
	 * @Description: 判断FTP服务器某目录下是否存在某文件
	 * @param	fileName：文件名
	 * 			fs：FTP某目录下的文件列表
	 * @return boolean  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-2  下午01:14:59
	 * @Version 3.1
	 */
	public static boolean isFileExist(String fileName, FTPFile[] fs) {
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				return true; // 如果存在返回 正确信号
			}
		}
		return false; // 如果不存在返回错误信号
	}

	/**
	 * @Description: FTP服务器某目录下，如果存在某文件名，则生成新文件名，否则返回原文件名
	 * @param	filename：文件名
	 * 			fs：FTP服务器某目录下文件列表
	 * @return 存在某文件名，则生成新文件名filename[1...n]，否则返回原文件名 filename
	 * @author 杨祖榕
	 * @date 2016-6-2  下午01:16:49
	 * @Version 3.1
	 */
	public static String changeName(String filename, FTPFile[] fs) {
		int n = 0;
		StringBuffer sb = new StringBuffer("");
		sb = sb.append(filename);
		while (isFileExist(sb.toString(), fs)) {
			n++;
			String a = "[" + n + "]";
			int b = sb.lastIndexOf(".");// 最后一次出现小数点的位置
			int c = sb.lastIndexOf("[");// 最后一次"["出现的位置
			if (c < 0) {
				c = b;
			}
			StringBuffer name = new StringBuffer(sb.substring(0, c));// 文件的名字
			StringBuffer suffix = new StringBuffer(sb.substring(b + 1));// 后缀的名称
			sb = name.append(a).append(".").append(suffix);
		}
		return sb.toString();
	}
	

	public static void main(String[] args) throws Exception {
		
		String url = "192.168.1.166";
		int port = 8821;
		String username = "recv";
		String password = "recv_admin";
		String ftppath = File.separator + "减刑假释";//FTP服务器主目录的相对目录
		
		String localFilepath = "F:\\新建文件夹";//要上传的目录
//		String localFilepath = "F:\\减刑假释报送法院数据2016053112134719.zip";//要上传的文件的路径
//		String filename = new File(localFilepath).getName();
		
		FTPClient ftp = getFTPClient(url, port, username,password);
		//如果登录不成功，返回false;
		if(! isLoginSuccess(ftp)){
			System.out.println("创建FTPClient失败");
			return;
		}
		
		
		long time1 = System.currentTimeMillis();
//		Runnable runnable=new Runnable(){
//            public void run(){
//            	long time1 = System.currentTimeMillis();
//            	long time2 = 0L;
//                while(true){
//                    try{
//                        Thread.sleep(10000);
//                        time2 = System.currentTimeMillis();
//                        long timeS = (time2 - time1)/1000;
//                        int hour = Integer.parseInt(timeS/60/60+"");
//                        int minute = Integer.parseInt((timeS - hour*60*60)/60+"");
//                        int sec = Integer.parseInt((timeS - hour*60*60 - minute*60)+"");
//                        System.out.println("用时：" + hour + "小时" + minute + "分钟" + sec + "秒");
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                    
//               }
//            }
//        };
//        Thread thread=new Thread(runnable);
//        thread.start();

    
//        uploadFile(ftp,ftppath, localFilepath);
        
		
//		String filename = "减刑假释报送法院数据20160531163317467.zip";
//		deleteFile(ftp,ftppath,filename);
		
		uploadDirectory(ftp,localFilepath,ftppath);

//		String localDir = "D:\\";
//		String fileName = "绘图1.eddx";
//		downloadFile(ftp,ftppath, fileName,localDir);
		
//		String localDirPath = "F:\\下载";
//		String remoteDirPath = "\\减刑假释";
//		downLoadDirectory(ftp,remoteDirPath,localDirPath);
		
		
		
		long time2 = System.currentTimeMillis();
	    long timeS = (time2 - time1)/1000;
	    int hour = Integer.parseInt(timeS/60/60+"");
	    int minute = Integer.parseInt((timeS - hour*60*60)/60+"");
	    int sec = Integer.parseInt((timeS - hour*60*60 - minute*60)+"");
	    System.out.println("用时：" + hour + "小时" + minute + "分钟" + sec + "秒");
		
		closeFTPClient(ftp);
	}

}
