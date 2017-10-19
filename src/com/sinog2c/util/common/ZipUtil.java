package com.sinog2c.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.unzip.UnzipUtil;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {
	
	
	/**
	 * @Description: 随机生成密码--uuid
	 * @param
	 * @return String  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午06:24:14
	 * @Version 3.1
	 */
	public static String randomPasswd(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	
	/**
	 * @Description: 文件标准压缩，不需要设置密码
	 * @param	fileList 文件List
	 * 			zipFilePath 压缩后，zip包保存的全路径   如：c:\\ZipTest\\test1.zip
	 * @return void  
	 * @throws ZipException 
	 * @author 杨祖榕
	 * @date 2016-6-4  下午12:41:47
	 * @Version 3.1
	 */
	public static void zipFileList(ArrayList<File> fileList,String zipFilePath){
		
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			zipFile.addFiles(fileList, parameters);	
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("文件压缩失败！");
		}
		
	}

	
	
	/**
	 * @Description: 文件标准压缩，需要设置密码
	 * @param	fileList 文件List
	 * 			zipFilePath 压缩后，zip包保存的全路径   如：c:\\ZipTest\\test1.zip
	 * 			password 压缩密码
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:02:11
	 * @Version 3.1
	 */
	public static void zipFileList(ArrayList<File> fileList,String zipFilePath,String password){
		
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
			// Set password
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);	
			parameters.setPassword(password);
			
			zipFile.addFiles(fileList, parameters);	
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("文件压缩失败！");
		}
			
	}

	
	
	/**
	 * @Description: 文件标准压缩，需要设置密码，用AES加密
	 * @param	fileList 文件List
	 * 			zipFilePath 压缩后，zip包保存的全路径   如：c:\\ZipTest\\test1.zip
	 * 			password 压缩密码
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:06:03
	 * @Version 3.1
	 */
	public static void zipFileListAES(ArrayList<File> fileList,String zipFilePath,String password) {
		
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
			// Set password
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setPassword(password);
			
			zipFile.addFiles(fileList, parameters);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("文件压缩失败！");
		}
		
	}
	
	
	/**
	 * @Description: 对文件夹及文件夹中的所有文件进行压缩
	 * @param	folderPath	被压缩的文件夹
	 * 			zipFilePath	压缩后文件路径
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:10:31
	 * @Version 3.1
	 */
	public static void zipFolder(String folderPath,String zipFilePath,String password) {
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();		
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);		
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			// Set password
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setPassword(password);
			
			zipFile.addFolder(folderPath, parameters);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("文件夹压缩失败！");
		}
		
	}
	
	/**
	 * @Description: 对目录中的所有文件及文件夹进行压缩
	 * @param
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年6月4日  下午11:25:39
	 * @Version 3.1
	 */
	public static void zipAllUnderFolder(String folderPath,String zipFilePath,String password) {
		File[] fileArr = new File(folderPath).listFiles();
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<File> dirList = new ArrayList<File>();
		for(File file : fileArr){
			if(file.isDirectory()){
				dirList.add(file);
			}else{
				fileList.add(file);
			}
		}
		
		zipFileListAES(fileList,zipFilePath,password);
		
		for(File file : dirList){
			zipFolder(file.getAbsolutePath(),zipFilePath,password);
		}
		
	}
	
	
	/**
	 * @Description: 将文件压缩到指定文件夹中
	 * @param	fileList 文件List
	 * 			zipFilePath 压缩后，zip包保存的全路径   如：c:\\ZipTest\\test1.zip
	 * 			rootFolder 压缩文件中的某目录	如：test2/
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:13:03
	 * @Version 3.1
	 */
	private static void addFileList2Zip(ArrayList<File> fileList,String zipFilePath,String rootFolder) {
		
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			parameters.setRootFolderInZip(rootFolder);
			
			zipFile.addFiles(fileList, parameters);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("将文件压缩到指定文件夹失败！");
		}
		
	}
	
	
	/**
	 * @Description: 往ZIP中添加文件
	 * @param	fileList 文件List
	 * 			zipFilePath 压缩后，zip包保存的全路径   如：c:\\ZipTest\\test1.zip
	 * 			rootFolder 压缩文件中的某目录	如：test2/
	 * @return void  
	 * @throws ZipException FileNotFoundException 
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:24:41
	 * @Version 3.1
	 */
	public static void addFile2Zip(File file,String zipFilePath) {
		InputStream is = null;
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			String filename = file.getName();
			parameters.setFileNameInZip(filename);
			parameters.setSourceExternalStream(true);
			
			is = new FileInputStream(file);
			zipFile.addStream(is, parameters);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("往ZIP中添加文件失败！");
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("往ZIP中添加文件失败！");
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * @Description: 添加文件夹到压缩包中
	 * 				需要添加的文件夹必须存在，否则抛出异常：ZipException: input folder does not exist
	 * 				 如果已经存在同名文件则会出现一个文件的时候会出现一个问题，程序会生成一个临时包并去修改之前存在的同名压缩文件，
	 * 				最后修改不成功且会抛出异常：ZipException: cannot rename modified zip file最后只留下一个临时包，
	 * 				 建议在生成的时候添加判断 避免出现这种错误
	 * 
	 * @param		addFolderPath 要往zip文件添加的文件夹
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午12:12:24
	 * @Version 3.1
	 */
	public void addFolder2Zip(String addFolderPath, String zipFilePath) {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
          
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            
            zipFile.addFolder(addFolderPath, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
            throw new RuntimeException("添加文件夹到压缩包失败！");
        }
    } 

	
	/**
	 * @Description: 分割压缩文件
	 * @param	fileList 文件List
	 * 			zipFilePath 压缩后，zip包保存的全路径   如：c:\\ZipTest\\test1.zip
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:28:17
	 * @Version 3.1
	 */
	private static void zipFileListAndSplit(ArrayList<File> fileList,String zipFilePath) {
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
			// SplitLenth has to be greater than 65536 bytes
			// zipFile.createZipFileFromFolder()
			zipFile.createZipFile(fileList, parameters, true, 65536);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("分割压缩文件失败！");
		}

	}

	/**
	 * @Description: 创建ZIP流
	 * @param
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:32:02
	 * @Version 3.1
	 */
	private static void zipStream(ArrayList<File> fileList,String zipFilePath,String passowrd) {
		ZipOutputStream outputStream = null;
		InputStream inputStream = null;
		
		try{
			outputStream = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)));

			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			// Set password
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setPassword(passowrd);
			
			for (int i = 0; i < fileList.size(); i++) {
				File file = (File)fileList.get(i);
				outputStream.putNextEntry(file,parameters);
				
				if (file.isDirectory()) {
					outputStream.closeEntry();
					continue;
				}
				
				inputStream = new FileInputStream(file);
				byte[] readBuff = new byte[4096];
				int readLen = -1;
				while ((readLen = inputStream.read(readBuff)) != -1) {
					outputStream.write(readBuff, 0, readLen);
				}
				
				outputStream.closeEntry();
				outputStream.finish();
			}
			
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException();
		}catch (ZipException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	
	/**
	 * @Description: 从ZIP中删除文件
	 * @param
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:42:51
	 * @Version 3.1
	 */
	public static void removeFile(String zipFilePath, String filename) {
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			//删除指定文件
			zipFile.removeFile(filename);
			//删除第一个文件
			if (zipFile.getFileHeaders() != null && zipFile.getFileHeaders().size() > 0) {
				zipFile.removeFile((FileHeader)zipFile.getFileHeaders().get(0));
			} else {
				System.out.println("This cannot be demonstrated as zip file does not have any files left");
			}
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("从ZIP中删除文件失败！");
		}
	}

	/**
	 * @Description: 获取ZIP中文件一览
	 * @param
	 * @return void  
	 * @throws ZipException 
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:44:40
	 * @Version 3.1
	 */
	private static List<Map<String,Object>> getZipDetail(String zipFilePath){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
			
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = fileHeaderList.get(i);
				Map<String,Object> map = new HashMap<String,Object>();
				String fileDetails = fileHeader.getFileName();
				String name = fileHeader.getFileName();
				long compressedSize = fileHeader.getCompressedSize();
				long uncompressedSize = fileHeader.getUncompressedSize();
				long crc = fileHeader.getCrc32();
				
				map.put("fileDetails", fileDetails);
				map.put("name", name);
				map.put("compressedSize", compressedSize);
				map.put("uncompressedSize", uncompressedSize);
				map.put("crc", crc);
				
//				System.out.println("****File Details for: " + fileDetails + "*****");
//				System.out.println("Name: " + fileHeader.getFileName());
//				System.out.println("Compressed Size: " + fileHeader.getCompressedSize());
//				System.out.println("Uncompressed Size: " + uncompressedSize);
//				System.out.println("CRC: " + crc);
//				System.out.println("************************************************************");
			}
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("获取ZIP中文件一览失败！");
		}
		return result;

	}

	
	/**
	 * @Description: 解压此压缩文件
	 * @param	zipFilePath 压缩文件的路径
	 * 			descPath 解压文件的目录路径		c:\\ZipTest1
	 * @return void  
	 * @throws ZipException 
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:56:13
	 * @Version 3.1
	 */
	private static void extractZipFile(String zipFilePath, String descPath) {
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);		
			zipFile.extractAll(descPath);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("解压此压缩文件失败！");
		}
	}
	
	
	
	/**
	 * @Description: 压缩文件中的文件解压到指定目录中去
	 * @param	zipFilePath 压缩文件的路径
	 * 			descPath 解压文件的目录路径		c:\\ZipTest2\\
	 * 			password 解压密码
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午01:56:13
	 * @Version 3.1
	 */
	public static void extractZipFile(String zipFilePath, String descPath, String password){
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);	
			if(StringNumberUtil.notEmpty(password)){
				if (zipFile.isEncrypted()) {
					zipFile.setPassword(password);
				}
			}
			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = fileHeaderList.get(i);
				zipFile.extractFile(fileHeader, descPath);
			}
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("压缩文件中的文件解压到指定目录失败！");
		}
	}
	
	
	/**
	 * @Description: 解压所有文件到流
	 * @param
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午02:08:03
	 * @Version 3.1
	 */
	private static void unzip2Stream(String zipFilePath, String descPath, String password){
		ZipInputStream is = null;
		OutputStream os = null;
		//
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			if(StringNumberUtil.notEmpty(password)){
				if (zipFile.isEncrypted()) {
					zipFile.setPassword(password);
				}
			}
			
			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = fileHeaderList.get(i);
				if (fileHeader != null) {
					String outFilePath = new StringBuilder(descPath).append(File.separator).append(fileHeader.getFileName()).toString();
//					String outFilePath = "c:\\ZipTest3\\" + System.getProperty("file.separator") + fileHeader.getFileName();
					File outFile = new File(outFilePath);
					
					if (fileHeader.isDirectory()) {
						outFile.mkdirs();
						continue;
					}
					
					File parentDir = outFile.getParentFile();
					if (!parentDir.exists()) {
						parentDir.mkdirs();
					}
					
					is = zipFile.getInputStream(fileHeader);
					os = new FileOutputStream(outFile);
					
					int readLen = -1;
					byte[] buff = new byte[4096];
					while ((readLen = is.read(buff)) != -1) {
						os.write(buff, 0, readLen);
					}
					UnzipUtil.applyFileAttributes(fileHeader, outFile);
//					System.out.println("Done extracting: " + fileHeader.getFileName());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @Description: 解压单个文件
	 * @param	zipFilePath 压缩文件的路径
	 * 			descPath 解压文件的目录路径		c:\\ZipTest2\\
	 * 			password 加密密码
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016-6-4  下午02:17:19
	 * @Version 3.1
	 */
	public static void unzipSingleFile(String zipFilePath, String descPath, String filename, String password) {
		
		try{
			ZipFile zipFile = new ZipFile(zipFilePath);
			if(StringNumberUtil.notEmpty(password)){
				if (zipFile.isEncrypted()) {
					zipFile.setPassword(password);
				}
			}
			zipFile.extractFile(filename, descPath);
		}catch(ZipException e){
			e.printStackTrace();
			throw new RuntimeException("解压单个文件失败！");
		}

	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		ArrayList fileList = new ArrayList();
		fileList.add(new File("E:\\新建文件夹\\绘图1.eddx"));
		fileList.add(new File("E:\\新建文件夹\\新建 Microsoft Office Excel 工作表.xlsx"));
		fileList.add(new File("E:\\新建文件夹\\新建 Microsoft Office Visio 绘图.vsd"));
		fileList.add(new File("E:\\新建文件夹\\新建 Microsoft Office Word 文档.docx"));
		
		String zipFilePath = "E:\\压缩后路径\\你好222.zip";
		String password = "072a24c4536c44d3b07f3e75d7977638";
//		String password = randomPasswd();
//		System.out.println("密码: " + password);
		String folderPath = "F:\\数据库入门";
		String descPath = "E:\\压缩后路径";
		
//		zipFileList(fileList,zipFilePath);
//		zipFileList(fileList,zipFilePath,password);
//		zipFileListAES(fileList,zipFilePath,password);
//		zipFileListAndSplit(fileList,zipFilePath);
		
//		zipFolder(folderPath,zipFilePath,password);
		extractZipFile(zipFilePath, descPath, password);
		
	}

}
