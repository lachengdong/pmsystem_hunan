package com.sinog2c.ws.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.ZipUtil;

/**
 * 将文件打包成ZIP压缩文件
 * @author 
 * @since 2012-3-1 15:47
 */
public final class FileToZip {
	
	private FileToZip() {
		
	}
	
	/**
	 * 将存放在sourceFilePath目录下的源文件,打包成fileName名称的ZIP文件,并存放到zipFilePath。
	 * @param sourceFilePath 待压缩的文件路径
	 * @param zipFilePath	 压缩后存放路径
	 * @param fileName		 压缩后文件的名称
	 * @return flag      
	 * 批量打包文件
	 */
	public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName) {
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		if(sourceFile.exists() == false) {
			System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath + " 不存在. <<<<<<");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if(zipFile.exists()) {
					System.out.println(">>>>>> " + zipFilePath + " 目录下存在名字为：" + fileName + ".zip" + " 打包文件. <<<<<<");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if(null == sourceFiles || sourceFiles.length < 1) {
						System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath + " 里面不存在文件,无需压缩. <<<<<<");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024*10];
						for(int i=0;i<sourceFiles.length;i++) {
							// 创建ZIP实体,并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis,1024*10);
							int read = 0;
							while((read=bis.read(bufs, 0, 1024*10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
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
					if(null != zos) zos.close();
					if(null != zos) fos.close();
					if(null != bis) bis.close();
					if(null != bis) fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		
		return flag;
	}
	
	/**
	 * 压缩文件或文件夹
	 * 调用这个方法需要自己在后面指定压缩名+zip
	 * @param srcPathName
	 *            需要压缩的文件或文件夹(如：E:/test E:/test/test.txt)
	 * @param ZipFile
	 *            压缩后的文件存放地址(如：E:/test.zip E:/test.rar)
	 *            将指定文件夹下的文件打包
	 */
	public static void compress(String srcPathName, String ZipFile,String filename) {
		File srcdir = new File(srcPathName);
		File zipfile=new File(ZipFile+"/"+filename+".zip");
		//File zipfile = new File(ZipFile);
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
	 * 压缩文件或文件夹
	 * 
	 * @param srcPathName
	 *            需要压缩的文件或文件夹(如：E:/test E:/test/test.txt)
	 * @param ZipFile
	 *            压缩后的文件存放地址(如：E:/test.zip E:/test.rar)
	 */
	/*public static void compress(String srcPathName, String ZipFile) {
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
	}*/
	
	/**
	 * 将文件打包成ZIP压缩文件,main方法测试
	 * sourceFilePath：是指定需要压缩的包，把需要压缩的文件放入其中，可以压缩多个文件,
	 * 文件夹下面不能有文件
	 * zipFilePath：生成压缩文件的路径
	 * fileName：生成压缩包的名字
	 * @param args
	 */
	public static void main(String[] args) {
		String sourceFilePath = "e:/zip";
		String zipFilePath = "e:/test/zip";
		String fileName = "lp20120301";
		boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
		if(flag) {
			System.out.println(">>>>>> 文件打包成功. <<<<<<");
		} else {
			System.out.println(">>>>>> 文件打包失败. <<<<<<");
		}
	}
	/**
	 * 将要发送的文件以广西提供的压缩格式压缩成一个总的zip文件，
	 * 该方法要指定源文件的路径，要以规定文件格式存
	 */
	/*public static void FileTotalZip(){
		//压缩暂予监外执行的各个阶段的罪犯信息
				String zypath03A="E:\\测试\\文件源路径\\zyjwzx\\0903A";  //传一个参数，阶段名称0903A
				String zydpath03="E:\\company\\邮件\\测试\\zyjwzx";
				String filename031="0903A";  //阶段编号需要当作参数给	
				String zypath03C="E:\\测试\\文件源路径\\zyjwzx\\0903C";  //传一个参数，阶段名称0903A
				String filename032="0903C";  //阶段编号需要当作参数给	
				String zypath03E="E:\\测试\\文件源路径\\zyjwzx\\0903E";  //传一个参数，阶段名称0903A
				String filename033="0903E";  //阶段编号需要当作参数给	
				FileToZip.compress(zypath03A, zydpath03,filename031);
				FileToZip.compress(zypath03C, zydpath03,filename032);
				FileToZip.compress(zypath03E, zydpath03,filename033);
				//压缩减刑的
				String zypath01A="E:\\测试\\文件源路径\\tqjx\\0901A";
				String zypath01C="E:\\测试\\文件源路径\\tqjx\\0901C";
				String zypath01E="E:\\测试\\文件源路径\\tqjx\\0901E";
				String zydpath01="E:\\company\\邮件\\测试\\tqjx";  //压缩路径
				String filename011="0901A";
				String filename012="0901C";
				String filename013="0901E";
				FileToZip.compress(zypath01A, zydpath01, filename011);
				FileToZip.compress(zypath01C, zydpath01, filename012);
				FileToZip.compress(zypath01E, zydpath01, filename013);
				//压缩假释的
				String zypath02A="E:\\测试\\文件源路径\\tqjs\\0902A";
				String zypath02C="E:\\测试\\文件源路径\\tqjs\\0902C";
				String zypath02E="E:\\测试\\文件源路径\\tqjs\\0902E";
				String zydpath02="E:\\company\\邮件\\测试\\tqjs";  //压缩路径
				String filename021="0901A";
				String filename022="0901C";
				String filename023="0901E";
				FileToZip.compress(zypath02A, zydpath02, filename021);
				FileToZip.compress(zypath02C, zydpath02, filename022);
				FileToZip.compress(zypath02E, zydpath02, filename023);
				//压缩罪犯基本信息
				String zfxxspath="E:\\测试\\文件源路径\\监狱编号_zfxx";
				String zfxxdpath="E:\\company\\邮件\\测试";
				String zfxxfilename="监狱编号_zfxx";
				FileToZip.fileToZip(zfxxspath, zfxxdpath, zfxxfilename);
				//将所有信息压缩成一个总的压缩包
				String dpath="E:\\测试\\压缩文件路径";
				String name="XXX监狱发送";
				FileToZip.compress(zfxxdpath, dpath, name);
	}*/
	/**
	 * 打包基本信息
	 * @param type  是什么类型审批  减刑假释还是暂予监外执行
	 * @param jdbh 阶段编号
	 * @param pici   批次
	 * @param jybh 监狱编号
	 * @param spath  院文件存放的主路径
	 * @param dpath 压缩路径
	 * @return 生成压缩包的全路径+压缩包的名字.zip
	 * @return
	 */
	/*public static String ZipToJBXX(String type,String jdbh,String pici,String jybh,String spath,String dpath){
		//批量打包犯人信息
		String fname=jybh+"_zfxx";
		String zfxxpath=spath+pici+"\\"+fname; //罪犯信息的原路径
		//得到系统时间
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(c.getTime());
		//创建临时目录
		File f1=new File(dpath+date+"_"+jybh+"_"+pici+"_"+type);
		f1.mkdir();
		//压缩路径
		String lspath=dpath+date+"_"+jybh+"_"+pici+"_"+type;
		String ysname=date+"_"+jybh+"_"+pici+"_"+type+"_"+jdbh;
		fileToZip(zfxxpath, lspath, ysname);
		//备份
		compress(zfxxpath,dpath,ysname);
		//删除临时目录文件
		//FileUtil.deleteFolder(lspath);
		return lspath+"\\"+ysname+".zip";
}*/ 	
	/**
	 * 打包基本信息
	 * @param type  是什么类型审批  减刑假释还是暂予监外执行
	 * @param jdbh 阶段编号
	 * @param pici   批次
	 * @param jybh 监狱编号
	 * @param spath  院文件存放的主路径
	 * @param dpath 压缩路径
	 * @return 生成压缩包的全路径+压缩包的名字.zip
	 * @return
	 */
	public static String ZipToJBXX(String spath,String dpath,String jybh){
		//得到罪犯的基本信息所在目录
		String zfxxpath=spath;
		//创建罪犯信息压缩目录
		File f1=new File(dpath+"zfxx");
		String zfxxZipPath=dpath+"zfxx";
		f1.mkdir();
		//得到系统时间
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(c.getTime());
		String name=date+"_"+jybh+"_zfxx";
		fileToZip(zfxxpath, zfxxZipPath, name);
		//备份
		compress(zfxxpath,dpath,name);
		
		return zfxxZipPath+"\\"+name+".zip";
}
	/**
	 * 打包批次信息
	 * @param type  是什么类型审批  减刑假释还是暂予监外执行
	 * @param jdbh 阶段编号
	 * @param pici   批次
	 * @param jybh 监狱编号
	 * @param spath  院文件存放的主路径
	 * @param dpath 压缩路径
	 * @return 生成压缩包的全路径+压缩包的名字.zip
	 * @return
	 */
	public static String ZipToPCXX(String type,String jdbh,String pici,String jybh,String spath,String dpath){
		//打包批次信息
		String pcxxpath=spath+pici+"\\"+type+"\\"+jdbh;  //批次信息原路径
		//得到系统时间
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(c.getTime());
		//创建临时目录
		File f1=new File(dpath+"wsxx");
		f1.mkdir();
		//压缩路径
		String lspath=dpath+"wsxx";
		String ysname=date+"_"+jybh+"_"+pici+"_"+type+"_"+jdbh;
		fileToZip(pcxxpath, lspath, ysname);
		//备份
		compress(pcxxpath,dpath,ysname);
		//删除临时目录文件
		//FileUtil.deleteFolder(lspath);
		return lspath+"\\"+ysname+".zip";
} 
	/**
	 * 将某一阶段的减刑假释（暂予监外执行）的犯人信息打包
	 * @param type  是什么类型审批  减刑假释还是暂予监外执行
	 * @param jdbh 阶段编号
	 * @param pici   批次
	 * @param jybh 监狱编号
	 * @param spath  院文件存放的主路径
	 * @param dpath 压缩路径
	 * @return 生成压缩包的全路径+压缩包的名字.zip
	 * 
	 */
	public static String ZipToSend(String type,String jdbh,String pici,String jybh,String spath,String dpath){
				//批量打包犯人信息
				String fname=jybh+"_zfxx";
				String zfxxpath=spath+pici+"\\"+fname; //罪犯信息的原路径
				//创建主目录
				File f1=new File(dpath);
				f1.mkdir();
				//创建临时目录
				File ff=new File(dpath+pici);
				String lspath=dpath+pici;    //临时路径，存放临时压缩文件，罪犯基本信息的压缩路径
				ff.mkdir();
				fileToZip(zfxxpath, lspath, fname);
				//打包批次信息
				String pcxxpath=spath+pici+"\\"+type;  //批次信息原路径
				File file = new File(lspath+"\\"+type);
				file.mkdirs();
				String pcdpath=lspath+"\\"+type;     //压缩批次信息的目标路径
				compress(pcxxpath, pcdpath, jdbh);
				//将上述数据打成一个压缩文件  压缩路径
				//得到系统时间
				Calendar c = Calendar.getInstance();
				 c.setTimeInMillis(new Date().getTime());
				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				 String date = dateFormat.format(c.getTime());
				String ysname=date+"_"+pici+"_"+type+"_"+jdbh;
				//放路径
				String p=dpath;    //这是具体存放要发送的压缩包的路径
				File f=new File(p);
				f.mkdir();
				compress(dpath+pici,p, ysname);
				//备份
				String bfpath="D:\\demo";
				compress(dpath,bfpath,ysname);
				//删除临时目录文件
				//FileUtil.deleteFolder(lspath);
				return p+ysname+".zip";
	} 
}
