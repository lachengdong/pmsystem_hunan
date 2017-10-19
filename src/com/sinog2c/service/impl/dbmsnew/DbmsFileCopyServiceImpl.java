package com.sinog2c.service.impl.dbmsnew;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.dbmsnew.DbmsDaochuListInfoMapper;
import com.sinog2c.dao.api.dbmsnew.DbmsFileCopyMapper;
import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.model.dbmsnew.DbmsFileCopy;
import com.sinog2c.service.api.dbmsnew.DbmsFileCopyService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.JdbcConnUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sun.jmx.snmp.Timestamp;

@Service("dbmsFileCopyService")  
public class DbmsFileCopyServiceImpl extends ServiceImplBase implements DbmsFileCopyService {

	@Autowired
	private DbmsFileCopyMapper dbmsFileCopyMapper;
	@Autowired
	private DbmsDaochuListInfoMapper daochuListInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(String sdid) {
		int num = dbmsFileCopyMapper.deleteByPrimaryKey(sdid);
		return num;
	}

	@Override
	public int insert(DbmsFileCopy record) {
		int num = dbmsFileCopyMapper.insert(record);
		return num;
	}

	@Override
	public int insertSelective(DbmsFileCopy record) {
		int num = dbmsFileCopyMapper.insertSelective(record);
		return num;
	}

	@Override
	public DbmsFileCopy selectByPrimaryKey(String sdid) {
		DbmsFileCopy dbmsFileCopy = dbmsFileCopyMapper.selectByPrimaryKey(sdid);
		return dbmsFileCopy;
	}

	@Override
	public int updateByPrimaryKeySelective(DbmsFileCopy record) {
		int num = dbmsFileCopyMapper.updateByPrimaryKeySelective(record);
		return num;
	}

	@Override
	public int updateByPrimaryKey(DbmsFileCopy record) {
		int num = dbmsFileCopyMapper.updateByPrimaryKey(record);
		return num;
	}
	
	   private static int BUFFER_SIZE = 10000;//缓冲区大小  
		  
	    
	    /** 
	     * 将HTTP资源另存为文件 
	     * 
	     * @param destUrl String 
	     * @param fileName String 
	     * @throws Exception 
	     */  
	    public void saveToFile(String destUrl, String fileName) throws IOException {  
	    	System.out.println("saveToFile start!"); 
	    	FileOutputStream fos = null;  
	        BufferedInputStream bis = null;  
	        URLConnection urlconnection=null;
	        HttpURLConnection httpUrl = null;  
	        URL url = null;  
	        byte[] buf = new byte[BUFFER_SIZE];  
	        int size = 0;  
	        //建立文件  
	        File file = new File(fileName);  
	        if(!file.exists()) {  //已存在不再次复制
	        	(new File(file.getParent())).mkdirs();
	        }
	        //建立链接  
	        System.out.println(destUrl + " URLConnection start!"); 
	        url = new URL(destUrl);  
	        urlconnection = (URLConnection) url.openConnection();  
	        httpUrl=(HttpURLConnection) urlconnection;
	        httpUrl.setConnectTimeout(10000);
	        httpUrl.setReadTimeout(10000);
	        httpUrl.setUseCaches(true);
	        httpUrl.setRequestProperty("Content-type", "application/x-java-serialized-object"); 
	        //httpUrl.setRequestMethod("post");
	        httpUrl.setDoInput(true);
	        httpUrl.setDoOutput(true);
	        //连接指定的资源  
	        httpUrl.connect();  
	        //获取网络输入流  
	        System.out.println("httpUrl.getInputStream start!"); 
	        bis = new BufferedInputStream(httpUrl.getInputStream());  

	        fos = new FileOutputStream(fileName);  
	  
	        //保存文件  
	        while ((size = bis.read(buf)) != -1) { 
	            fos.write(buf, 0, size);  
	            fos.flush();
	        }
	        fos.close();  
	        bis.close(); 
	        httpUrl.disconnect();  
	        System.out.println("saveToFile end!");
	    }  
	
		/**
		 * main 单元测试方法
		 */
		public static void main(String[] args) {
			SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMddHHmmss");
			File   f   =   new   File("D:\\criminalarchives\\CriminalArchives\\1209\\1209004032\\301914285692593791209.txt"); 
//			long   modify =   f.lastModified(); //   修改时间 
//			System.out.println(new Timestamp(modify).toString());
			
			   Date filetime = new Date(f.lastModified());
			    System.out.println(sdff.format(filetime));

			}
	    
	/**
	 * @Package com.sinog2c.service.impl.dbmsnew 
	 * @Description: TODO
	 * @author lxf  档案更改监视
	 * @see 1、向TBFLOW_ARCHIVES_WATCH里插入tbflow_archives新增的档案。
	 *      2、省局已经接收的档案标记成本地档案无变化。
	 *      3、监狱新增的档案时间录入
	 *      4、检查省局已经接收的档案是否变更
	 * @date 2015-4-9 上午11:14:33 
	 * @version V3.0
	 */
	public void archivesWatch(){
		SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMddHHmmss");
		Connection con = null;
		
		String provinceCode = StringNumberUtil.getJyConfigValue(GkzxCommon.PROVINCECODE, "");
		PreparedStatement localps = null;
		ResultSet localrs = null;
		String docconent = "";
		String archiveid = "";
		String strUpdateTime = "";
		boolean hasChanged = false;
		ArrayList<Map<String, Object>> archivesList = new ArrayList<Map<String, Object>>();
		String basesql = "";
		try {
			con = JdbcConnUtil.getConn();
		
			//插入差分数据
			basesql = " INSERT INTO TBFLOW_ARCHIVES_WATCH x (x.crimid,x.archiveid,x.localdepartid,x.localchanged,x.remotedepartid,x.opid,x.optime,localorremotefile) " + "\n" +
			" select tt.personid,t.archiveid,b.orgid,1," + provinceCode + ",'sys',sysdate,1 from tbflow_archives t,tbflow_base_archives tt ,Tbprisoner_Base_Crime b " + "\n" +
			" where t.archiveid=tt.archiveid and tt.personid = b.crimid and t.int1=1 " + "\n" +
			" and not exists(SELECT 'X' FROM TBFLOW_ARCHIVES_WATCH S WHERE S.crimid=Tt.personid and S.archiveid=Tt.archiveid) ";
			
			localps = con.prepareStatement(basesql);
			localps.executeUpdate();
			JdbcConnUtil.closeResource(null, localps, null);
			
			//更新已处理的数据
			basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.localchanged = 0 where x.remotechanged=2 and x.remotedonetime = x.localchangedtime";
			
			localps = con.prepareStatement(basesql);
			localps.executeUpdate();
			JdbcConnUtil.closeResource(null, localps, null);
			
			
			//更新时间为空的填写时间
			basesql = "select tt.archiveid, t.docconent,x.localchangedtime  from TBFLOW_ARCHIVES t, TBFLOW_BASE_ARCHIVES tt,TBFLOW_ARCHIVES_WATCH x where t.int1='1' and t.archiveid=tt.archiveid and x.archiveid=t.archiveid and x.localchangedtime is null ";
			
			localps = con.prepareStatement(basesql);
			localrs = localps.executeQuery();
			archivesList = JdbcConnUtil.ResultSetToList(localrs);
			JdbcConnUtil.closeResource(null, localps, localrs);
			
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String desPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
			
			//TBFLOW_ARCHIVES_WATCH表里的监狱ID的更新时间
			basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.localchangedtime = ? where x.archiveid= ? ";
			
			localps = con.prepareStatement(basesql);
			for(int i=0;i<archivesList.size();i++){
				Map<String, Object> map = archivesList.get(i);
				
				docconent = StringNumberUtil.getStringByMap(map, "docconent");
				archiveid = StringNumberUtil.getStringByMap(map, "archiveid");
				if (StringNumberUtil.isNullOrEmpty(docconent)){
					continue;
				} 
				
				String targetPath = GetAbsolutePath.getAbsolutePathAppend(desPath, docconent);
				
				File   f   =   new   File(targetPath); 
				long   modify =   f.lastModified(); //   修改时间 
				String strModTime = sdff.format(new Date(modify)); 
				localps.setString(1, strModTime);
				localps.setString(2, archiveid);
				localps.addBatch();
				hasChanged = true;
			}
			if (hasChanged) {
				localps.executeBatch();
			}
			
			JdbcConnUtil.closeResource(null, localps, localrs);
			
			
			//获得标记为未变化的数据
			basesql = "select tt.archiveid, t.docconent,x.localchangedtime as changedtime from TBFLOW_ARCHIVES t, TBFLOW_BASE_ARCHIVES tt,TBFLOW_ARCHIVES_WATCH x where t.int1='1' and t.archiveid=tt.archiveid and x.archiveid=t.archiveid and x.localchanged=0 ";
			
			localps = con.prepareStatement(basesql);
			localrs = localps.executeQuery();
			archivesList = JdbcConnUtil.ResultSetToList(localrs);
			JdbcConnUtil.closeResource(null, localps, localrs);
			
			//检查标记为未变化的文件是否真的未变化，并更新实际变化的数据
			basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.localchanged=1, x.localchangedtime = ? where x.archiveid= ? ";
			hasChanged = false;
			localps = con.prepareStatement(basesql);
			for(int i=0;i<archivesList.size();i++){
				Map<String, Object> map = archivesList.get(i);
				
				docconent = StringNumberUtil.getStringByMap(map, "docconent");
				archiveid = StringNumberUtil.getStringByMap(map, "archiveid");
				strUpdateTime = StringNumberUtil.getStringByMap(map, "changedtime");
				if (StringNumberUtil.isNullOrEmpty(docconent)){
					continue;
				} 
				
				String targetPath = GetAbsolutePath.getAbsolutePathAppend(desPath, docconent);
				
				File   f   =   new   File(targetPath); 
				long   modify =   f.lastModified(); //   修改时间 
				String strModTime = sdff.format(new Date(modify)); 
				
				//文件时间变更
				if (strModTime.equals(strUpdateTime)) {
					continue;
				}
				localps.setString(1, strModTime);
				localps.setString(2, archiveid);
				localps.addBatch();
				hasChanged = true;
			}
			if (hasChanged) {
				localps.executeBatch();
			}
			
			JdbcConnUtil.closeResource(null, localps, localrs);
			
			//更新已处理的数据
			basesql = " update tbflow_archives t set t.personid= (select personid from tbflow_base_archives m where t.archiveid=m.archiveid) where t.personid is null ";
			localps = con.prepareStatement(basesql);
			localps.executeUpdate();
			JdbcConnUtil.closeResource(null, localps, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.closeResource(con, localps, localrs);
		}		
		
	}
	
	/**
	 * @Package com.sinog2c.service.impl.dbmsnew 
	 * @Description: TODO
	 * @author liuli  分离档案表到目录
	 * @date 2015-4-9 上午11:14:33 
	 * @version V3.0
	 */
	public void archivesOutputToFile(){
		Connection con = null;
		PreparedStatement localps = null;
		PreparedStatement updateps = null;
		ResultSet localrs = null;
		String docconent = "";
		String departid = "";
		String personid = "";
		String archiveid = "";
		List<Map<String,Object>> dataAll = new ArrayList<Map<String,Object>>();
		ArrayList<Map<String, Object>> archivesList = new ArrayList<Map<String, Object>>();
		String basesql = "select t.archiveid from TBFLOW_ARCHIVES t  where (t.int1 =0 or t.int1 is null) and " + "\n" +
						"not exists(select 'x'  from Tbflow_Base_Archives tt,TBFLOW_ARCHIVESCODE c " + "\n" +
						"where t.archiveid=tt.archiveid and tt.docid = c.codeid and c.tempid in ('JXJS_JXJSSHB','CQAJPSB','jailcommutereport'))";
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		try {
			con = JdbcConnUtil.getConn();
			localps = con.prepareStatement(basesql);
			localrs = localps.executeQuery();
			dataAll = JdbcConnUtil.ResultSetToList(localrs);
			JdbcConnUtil.closeResource(null, localps, localrs);
		
			basesql = "select t.docconent,t.departid,b.applyid as personid from TBFLOW_ARCHIVES t , TBFLOW_BASE b where t.flowid=b.flowid and  t.archiveid=?";
			localps = con.prepareStatement(basesql);
			String udatesql = "update TBFLOW_ARCHIVES t set t.docconent=? , t.int1=1  where   t.archiveid=?";
			updateps = con.prepareStatement(udatesql);
			for(int i=0;i<dataAll.size();i++){
				Map<String, Object> map = dataAll.get(i);
				
				archiveid = StringNumberUtil.getStringByMap(map, "archiveid");
				if (StringNumberUtil.isNullOrEmpty(archiveid)){
					continue;
				} 
				localps.setString(1, archiveid);
				localrs = localps.executeQuery();
				archivesList= JdbcConnUtil.ResultSetToList(localrs);
				JdbcConnUtil.closeResource(null, null, localrs);
				if (archivesList.size() > 0 ) {
					map = archivesList.get(0);
					docconent = StringNumberUtil.getStringByMap(map, "docconent");
					personid = StringNumberUtil.getStringByMap(map, "personid");
					departid = StringNumberUtil.getStringByMap(map, "departid");
				} else {
					continue;
				}
				
				try {
					//save to file
					//相对路径
					String relativePath = GkzxCommon.SINGLESEPALINE + GkzxCommon.CRIMINALARCHIVES_PATH + File.separator + departid 
													+ File.separator + personid + File.separator + archiveid + ".txt";					
					//绝对路径
					String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, relativePath);
					FileUtil.saveToFile(docconent, targetPath, GkzxCommon.encoding);
					//更新 TBFLOW_ARCHIVES set t.docconent=path , t.int1=1
					updateps.setString(1, relativePath);
					updateps.setString(2, archiveid);
					updateps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.closeResource(con, updateps, localrs);
			JdbcConnUtil.closeResource(con, localps, localrs);
		}		
		
	}
	
	/**
	 * @Package com.sinog2c.service.impl.dbmsnew 
	 * @Description: TODO 文件传输
	 * @date 2015-4-15 下午04:47:28 
	 * @version V3.0
	 */
	public void filesTransfer(){
		
	}
	/**
	 * 将uploaddata目录下的文件移动到zipdata,并在DBMS_DAOCHU_LIST_INFO插入记录
	 */
	@Override	
	public void archivesWatch(HttpServletRequest request) {
		try {
			String sourceDir=GetAbsolutePath.getAbsolutePath(GkzxCommon.UPLOADDATA+File.separator);
			File sourceFile = new File(sourceDir);
			File[] templistFiles = sourceFile.listFiles();
			List<File> listFiles = new ArrayList<File>();
			for(File file : templistFiles){
				if(file.getName().endsWith(".zip")){
					listFiles.add(file);
				}
			}
			
			if(null == listFiles || listFiles.isEmpty()){
				return;
			}
			
			String targetparentDir = GetAbsolutePath.getAbsolutePath(GkzxCommon.ZIPDATA_PATH);
			File targetparentDirFile = new File(targetparentDir);
			if(! targetparentDirFile.exists()){
				targetparentDirFile.mkdir();
			}
			
			int count = 0;
			for (File srcfile : listFiles) {//遍历uploaddata目录下的文件
				count ++;
				setSessionAttribute(request,"percent","正在处理第" + count + "个文件，共"+listFiles.size() +"个文件");
				//
				String filename = srcfile.getName();
				String targetpath = targetparentDir + File.separator + filename;
				File targetfile=new File(targetpath);
				//拷贝文件
				FileUtil.copyFile(srcfile, targetfile);
				//插入表
				DbmsDaochuListInfo ddli=new DbmsDaochuListInfo();
				ddli.setFileid(StringNumberUtil.getUUID());
				ddli.setCreatedate(new Date());
				ddli.setFilename(filename);
				ddli.setFilerealname(filename);
				ddli.setFilepath(File.separator+GkzxCommon.ZIPDATA_PATH+File.separator+filename);
				ddli.setText6("0");//标识0：压缩文件  1：解压文件
				ddli.setText7("0");//标识0：未处理      1：已处理
				daochuListInfoMapper.insertSelective(ddli);
				//删除文件
				FileUtil.deleteFile(srcfile.getPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void setSessionAttribute(HttpServletRequest request,String key,String content){
		if(null != request){
			request.getSession(true).setAttribute(key, content);
		}
	}
}
