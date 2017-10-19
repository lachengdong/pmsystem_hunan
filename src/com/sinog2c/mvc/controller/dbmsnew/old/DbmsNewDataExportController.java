package com.sinog2c.mvc.controller.dbmsnew.old;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;
import com.sinog2c.model.dbmsnew.DbmsCodeTypeNew;
import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.mvc.controller.base.ControllerBase;

 
    
 
    /**  
	 * 项目名称：pmisystem
	 * 类名称：DbmsNewDataExportAction
     * 此类描述的是：    数据导出action
     * @author: 李祺亮  
     * @version: 2013-6-26 下午08:00:14    
     */   
    
public class DbmsNewDataExportController extends ControllerBase {
//	private  Logger logger = Logger.getLogger(this.getClass().getName()) ;//log4j
//	private static final long serialVersionUID = 1L;
//	public void prepare() throws Exception {}
//	private HttpServletRequest request;
//	protected Map session ; 
//	public void setServletRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//	private DataBaseDateTransfer dataBaseDateTransfer;
//	public void setDataBaseDateTransfer(DataBaseDateTransfer dataBaseDateTransfer){
//		this.dataBaseDateTransfer=dataBaseDateTransfer;
//	}
//	private DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
//	public DbmsNewDataExportBean getBean() {
//		return bean;
//	}
//
//	public void setBean(DbmsNewDataExportBean bean) {
//		this.bean = bean;
//	}
//
//	private DbmsDaochuListInfoBean daochuBean = new DbmsDaochuListInfoBean();
//	
//	public DbmsDaochuListInfoBean getDaochuBean() {
//		return daochuBean;
//	}
//
//	public void setDaochuBean(DbmsDaochuListInfoBean daochuBean) {
//		this.daochuBean = daochuBean;
//	}
//
//	@Override
//	public DbmsNewDataExportBean getModel() {
//		// TODO Auto-generated method stub
//		return bean;
//	}
//	private DbmsNewDataExportSchemeManagerImpl dbmsNewDateExportSchemeManager;
//
//	public DbmsNewDataExportSchemeManagerImpl getDbmsNewDateExportSchemeManager() {
//		return dbmsNewDateExportSchemeManager;
//	}
//	private DbmsNewDataImportService dbmsNewDataImportService;
//
//	public DbmsNewDataImportService getDbmsNewDataImportService() {
//		return dbmsNewDataImportService;
//	}
//
//	public void setDbmsNewDataImportService(
//			DbmsNewDataImportService dbmsNewDataImportService) {
//		this.dbmsNewDataImportService = dbmsNewDataImportService;
//	}
//	public void setDbmsNewDateExportSchemeManager(
//			DbmsNewDataExportSchemeManagerImpl dbmsNewDateExportSchemeManager) {
//		this.dbmsNewDateExportSchemeManager = dbmsNewDateExportSchemeManager;
//	}
//	private HttpServletResponse response;
//	public void setServletResponse(HttpServletResponse rep) {
//		this.response = rep;
//		
//	}
//	
//	private BaseDao baseDao;
//
//	public void setBaseDao(BaseDao baseDao) {
//		this.baseDao = baseDao;
//	}
//
//	public BaseDao getBaseDao() {
//		return baseDao;
//	}
//	private JsonTool jsonTool;
//	
//	public JsonTool getJsonTool() {
//		return jsonTool;
//	}
//
//	public void setJsonTool(JsonTool jsonTool) {
//		this.jsonTool = jsonTool;
//	}
//	Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//	private String strJgxt = (jyconfig.getProperty("jgxt")== null?"jgxt":jyconfig.getProperty("jgxt"));
//	
//	SimpleDateFormat m_sdf=new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
//	List<String> addcolumnslst = new ArrayList<String>();
//	
//	/**   
//	     * 此方法描述的是：导出XML文件
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:02:07   
//	     */   
//	    
//	public String readXml() {
////		DbmsDatasChemeNew datascheme = getDatasChemeNewInfo(bean.getDdcid());
////		if (datascheme != null) {
////			DbmsDatabaseNew database = getDataBase(datascheme.getFromdatabaseid());
////			if (database != null) {
////				Connection conn = dbmsNewDateExportSchemeManager.getConnection(database);
////				if (conn != null) {
////					List<DbmsDatasTableNew> tables=dbmsNewDateExportSchemeManager.getTableList(datascheme.getDdcid());
////					if(null!=tables&&0<tables.size()){
////						for(DbmsDatasTableNew table:tables){
////							//List<DbmsDatasChemeDetailNew> columns=dbmsNewDateExportSchemeManager.getColumns(table.getId().getDbmsDatasChemeNew().getDdcid(), table.getId().getDdtid());
////						}
////					}
////				}
////			}
////		}
//		return null;
//	}
//
//	 
//	
//	 
//	    /**   
//	     * 此方法描述的是：数据导入方案列表
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:02:37   
//	     */   
//	    
//	public String getDatasChemeNew(){
//		String basicdep = "";
//		try {
//			basicdep = (String)request.getSession().getAttribute("basicdep");
//		} catch (Exception e) {
//		}
//		List<DbmsDatasChemeNew> datas=dbmsNewDateExportSchemeManager.getDatasChemeNewInfolist(basicdep);
//		ActionContext.getContext().put("datascheme", datas);
//		return SUCCESS;
//	}
//	
//	//得到项目绝对路径
//	private GetAbsolutePath absolutePath=new GetAbsolutePath();
//	//得到XML文件的路径
//	//private String basePath = absolutePath.getAbsolutePath("com/gkzx/dbmsnew/dbconfig");
//	private String basePath="";
//	//private String xmlPaht=absolutePath.getAbsolutePath("dataxml/exportxml");
//	private String xmlPaht="";
//	
//	 
//	 
//	    /**   
//	     * 此方法描述的是：查询导出数据
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:03:15  
//	     * @modified 2013-11-20 by 袁德民
//	     * 这里在原先的基础上加入了导出数据是的进度计算
//	     */   
//	    
//	public String getTableInfo2(){
//		TaskBean taskBean = new TaskBean();
//	    int sum = 0;
//	    int tem = 0;
//	    String basePath = "";
//	    String fname = "";
//	    String ddcname = "";
//	    boolean isPackageper = false;
//	    List<Object> criminalidList= new ArrayList<Object>();
//		String fileNameN = "";
//		String fileRealName = "";
//		String filepath = "";
//		String rentemp=SUCCESS;
//		if (null == bean.getDdcid() || "".equals(bean.getDdcid())){
//			return rentemp;
//		}
//		if(this.session != null){
//			this.session.put("taskbean", taskBean);
//		}
//		String type = bean.getOperatetype();
//		if (null != bean.getDdcid() && !"".equals(bean.getDdcid())){
//			TransformerHandler handler = null;
//		    OutputStream outStream = null;
//		    SAXTransformerFactory fac=null;
//		    String maintablesql="";
////		    String basePath = absolutePath.getAbsolutePath("/WEB-INF/classes/com/gkzx/dbmsnew/applicationContext-actions.xml");
//		    
////		    Date now=new Date();
//		    
////		    String fname="DBMS_DATABASE_"+now.getTime()+".xml";
////		    fname = now.getTime() + ".xml";
//			
//			String chaxuncon="";
//		    if(null!=bean.getCondition()&&!"".equals(bean.getCondition())){
//		    	chaxuncon=bean.getCondition();
//			}
//			else if(null!=bean.getHiddencon()&&!"".equals(bean.getHiddencon())){
//				chaxuncon=bean.getHiddencon();
//			}
//		    Connection conn=null;
//			try{
//				List<String> titlelist=new ArrayList<String>();
//				List<String> valuelist=new ArrayList<String>();
//				//判断方案操作数据库类型
//				String basesql="from DbmsDatabaseNew a where a.ddid in (select fromdatabaseid from DbmsDatasChemeNew c where c.ddcid='"+bean.getDdcid()+"')";
//				List<Object> baselist=baseDao.queryListObjectAll(basesql);
//				String basetype="";
//				DbmsDatabaseNew bs=new DbmsDatabaseNew();
//				if(null!=baselist&&baselist.size()>0){
//					bs=(DbmsDatabaseNew)baselist.get(0);
//					basetype=bs.getDatabasetype();
//				}
//				conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//				
//				//Object sdidobj=request.getSession().getAttribute("sdid");
//				Object sdidobj = null;
//				if(this.session != null)
//				sdidobj = this.session.get("sdid");
//				String strSdisString = "";
//				if(null!=sdidobj){
//					strSdisString = jsonTool.getFaYuanAndSdidString(conn,(String)sdidobj);
//					if (!StringUtil.isNullOrEmpty(strSdisString)) {
//						String[] sdidArr = strSdisString.split(",");
//						for(int k = 0; k < sdidArr.length; k++){
//							if (k == 0) {
//								strSdisString = " and ( t.depid = '" + sdidArr[k] + "'";
//							} else {
//								strSdisString += " or " + " t.depid = '" + sdidArr[k] + "'";
//							}
//						}
//						
//						if (sdidArr.length > 0) {
//							strSdisString += " ) ";
//						}
//					}
//				}
//				
//				/**
//				 * 获取导出方案名
//				 * 2013-11-23
//				 */
//				
//				String sql = "from DbmsDatasChemeNew a where a.ddcid = '"+ bean.getDdcid()+"' ";
//				List<Object> fangan = baseDao.queryListObjectAll(sql);
//				if(fangan.size()>0){
//					DbmsDatasChemeNew ddc = new DbmsDatasChemeNew();
//					ddc = (DbmsDatasChemeNew)fangan.get(0);
//					ddcname = ddc.getDdcname();
//				}
//				
//				bean.setBasetype(basetype);
//				String daochusql="";
//				int totalnum=0;
//				if(!StringUtil.isNullOrEmpty(bean.getPackageper()) && (GkzxCommon.ONE.equals(bean.getPackageper()))){
//					isPackageper = true;
//				}				
//				boolean hasCriminalId = false;
//				String biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtismaintable='0'";
//				//构造主表查询条件开始
//				List<Object> biaolist=baseDao.queryListObjectAll(biaosql);
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					String value="";
//					DatabaseMetaData dbmd=conn.getMetaData();
//					//获得字段信息
//					ResultSet rsColumns=null;
//					if("Oracle".equals(basetype))
//						rsColumns = dbmd.getColumns(null, bs.getDatabaseuser().toUpperCase(), thetable.getTablename().toUpperCase(),null);
//					else
//						rsColumns = dbmd.getColumns(null, null, thetable.getTablename().toUpperCase(),null);
//					
//					while (rsColumns.next()) 
//					{
//						try {
//							String COLUMN_NAME=rsColumns.getString("COLUMN_NAME");
//							if (GkzxCommon.CRIMINALID.equalsIgnoreCase(COLUMN_NAME)){
//								hasCriminalId = true;
//							}
//						} catch (Exception e) {
//						}
//					}
//					daochusql=thetable.getDaochusql();
//					if("0".equals(thetable.getDdtismaintable())){
//						daochusql=daochusql+" a where 1=1";
//						//额外查询条件追加开始 2013-11-19
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							daochusql=daochusql+"  and a." + addconditionString;
//						}
//						//额外查询条件追加结束2013-11-19
//						if(!"".equals(chaxuncon)){
//							daochusql=daochusql+" and "+chaxuncon;
//						}
//						if (hasCriminalId) {
//							daochusql = daochusql + " and a.criminalid in ( select criminalid from tbcriminal_basic_info t where 1= 1 " + strSdisString + " )";
//						}
////						maintablesql=thetable.getDaochusql();
//						maintablesql=daochusql;
//					}
//				}
//				//构造主表查询条件结束
//				//分包导出查询开始2013-12-22
//				if (isPackageper && hasCriminalId){
//					int intIndex = maintablesql.toLowerCase().indexOf("from");
//					String strPackagePerSql = "select criminalid " + maintablesql.substring(intIndex);
//					criminalidList = getCriminalidLst(conn, strPackagePerSql);
//					
//				}
//				
//			    Date now=new Date();
//			    fname = String.valueOf(now.getTime()) ;
//			    //导出时路径
//				basePath = absolutePath.getAbsolutePath(GkzxCommon.SINGLESEPALINE + GkzxCommon.PATH + ddcname);
//				basePath +=  fname + GkzxCommon.SINGLESEPALINE;
//				File file = new File(basePath);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				fileRealName = ddcname  + fname + ".zip";
//				filepath = GkzxCommon.SINGLESEPALINE + GkzxCommon.PATH  + fileRealName;
//				
//				boolean isFirstExp = true;
//				String strKeepMaintablesql = maintablesql;
//				//分包导出查询结束2013-12-22
//			for (int ppx = 0; ppx < criminalidList.size() || isFirstExp; ppx++){
//				//通过isFirstExp和criminalidList控制导出至少一次。
//				isFirstExp = false;
//				maintablesql = strKeepMaintablesql;
//				String appendCriminalidCondition = "";
//				//分包导出时的罪犯编号
//				if (criminalidList.size() > 0){
//					HashMap criminalidMap = (HashMap)criminalidList.get(ppx);
//					appendCriminalidCondition = criminalidMap.get("criminalid")==null?"":criminalidMap.get("criminalid").toString();
//				}
//				
//				if (!StringUtil.isNullOrEmpty(appendCriminalidCondition)){
//					maintablesql  += " and a.criminalid = '"  + appendCriminalidCondition + "'";
//				}
//				fname = String.valueOf(now.getTime()) + ".xml";
//				fileNameN = basePath + appendCriminalidCondition + "_" + fname;
//				
//				if("daochu".equals(bean.getOperate())){
////					biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' order by a.ddtismaintable";
//					biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' order by a.ddtorder";
//					fac = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
//					handler = fac.newTransformerHandler();
//					Transformer transformer = handler.getTransformer();
//			        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//			        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//					file = new File(fileNameN);
//					if (!file.exists()) {
//						file.createNewFile();
//					}
//			        outStream = new FileOutputStream(fileNameN);
//			        handler.setResult(new StreamResult(outStream));
//			        String value="";
//			        //写入数据
//			        handler.startElement("", "", "dcfangan", null);
//			        handler.startElement("", "", "dcfanganddcid", null);
//			        value=bean.getDdcid();
//			        handler.characters(value.toCharArray(), 0, value.length());
//		            handler.endElement("", "", "dcfanganddcid");
//				}
//				biaolist=baseDao.queryListObjectAll(biaosql);
//				
//				//导出条数追加2013-12-4开始
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					daochusql=thetable.getDaochusql();
//					if("0".equals(thetable.getDdtismaintable())){
//						daochusql = maintablesql;
//					}else{
//						//其他表根据数据关系生成查询条件
//						if(null!=thetable.getShujuguanxi()&&!"".equals(thetable.getShujuguanxi())){
//							String[]  temparr=thetable.getShujuguanxi().split(",");
//							String tempcon="";
//							for(String str:temparr){
//								String[] temp2=str.split("=");
//								if("".equals(tempcon)){
//									tempcon="a."+temp2[0]+"=b."+temp2[1];
//								}else{
//									tempcon+=" and a."+temp2[0]+"=b."+temp2[1];
//								}
//							}
//							daochusql=daochusql+" b where 1=1";
//							//额外查询条件追加开始 2013-11-19
//							String addconditionString = thetable.getAddcondition();
//							if (!StringUtil.isNullOrEmpty(addconditionString)) {
//								daochusql=daochusql+" and b." + addconditionString;
//							}
//							//额外查询条件追加结束 2013-11-19
//							if(!"".equals(tempcon)){
////								daochusql=daochusql+" and exists ("+maintablesql+" a where "+tempcon;
//								daochusql=daochusql+" and exists ("+maintablesql+" and "+tempcon;
//								if(!"".equals(chaxuncon)){
//									daochusql=daochusql+" and "+chaxuncon+") ";
//								}else{
//									daochusql=daochusql+" ) ";
//								}
//							}
//						}
//					}
//					if(!"".equals(daochusql)){
//						ResultSet rs=null;
//						Statement stmt=null;
//						
//						try {
//							stmt = conn.createStatement(); 
//							String countsqlString="select count(*) as aa from ("+daochusql+") dd";
//							sum += this.getSumCount(conn, countsqlString);
//							
//						}catch (Exception  e) {
//							e.printStackTrace();
//						}finally{
//							if(null!=rs){
//								try {
//									rs.close();
//								} catch (SQLException e) {
//									e.printStackTrace();
//								}
//							}
//							if(null!=stmt){
//								try {
//									stmt.close();
//								} catch (SQLException e) {
//									e.printStackTrace();
//								}
//							}
//						}
//					}
//				}
//				taskBean.setSum(sum);
//				if("daochu".equals(bean.getOperate())){
//					handler.startElement("", "", "dctotal", null);
//			        handler.characters(String.valueOf(sum).toCharArray(), 0, String.valueOf(sum).length());
//		            handler.endElement("", "", "dctotal");
//				}
//				//导出条数追加2013-12-4结束
//				
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					String value="";
//					DatabaseMetaData dbmd=conn.getMetaData();
////					//获得字段信息
////					ResultSet rsColumns=null;
////					if("Oracle".equals(basetype))
////						rsColumns = dbmd.getColumns(null, bs.getDatabaseuser().toUpperCase(), thetable.getTablename().toUpperCase(),null);
////					else
////						rsColumns = dbmd.getColumns(null, null, thetable.getTablename().toUpperCase(),null);
////					boolean hasCriminalId = false;
////					while (rsColumns.next()) 
////					{
////						try {
////							String COLUMN_NAME=rsColumns.getString("COLUMN_NAME");
////							if (GkzxCommon.CRIMINALID.equalsIgnoreCase(COLUMN_NAME)){
////								hasCriminalId = true;
////							}
////						} catch (Exception e) {
////						}
////					}
//					if("daochu".equals(bean.getOperate())){
//						//写入表信息
//						handler.startElement("", "", "dctable", null);
//						handler.startElement("", "", "dctableddtid", null);
//			        	value=thetable.getId().getDdtid();
//			        	handler.characters(value.toCharArray(), 0, value.length());
//			        	handler.endElement("", "", "dctableddtid");
//			        	
//			        	handler.startElement("", "", "dctablename", null);
//			        	value=thetable.getTablename();
//			        	handler.characters(value.toCharArray(), 0, value.length());
//			            handler.endElement("", "", "dctablename");
//			            
//			            handler.startElement("", "", "dctabledescrition", null);
//			        	value=thetable.getDescrition()==null?"":thetable.getDescrition();
//			        	handler.characters(value.toCharArray(), 0, value.length());
//			            handler.endElement("", "", "dctabledescrition");
//			            
//			            handler.startElement("", "", "dctableddtismaintable", null);
//			        	value=thetable.getDdtismaintable()==null?"":thetable.getDdtismaintable();
//			        	handler.characters(value.toCharArray(), 0, value.length());
//			            handler.endElement("", "", "dctableddtismaintable");
//					}
//					//查询所有列
//					String liesql="from DbmsDatasChemeDetailNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"'";
//					List<Object> lielist=baseDao.queryListObjectAll(liesql);
//					List<DbmsDatasChemeDetailNew> mylist=new ArrayList<DbmsDatasChemeDetailNew>();
//					for(Object obj:lielist){
//						DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//						titlelist.add(detail.getDcdfromcolumnsscribe());
//						mylist.add(detail);
//						if("daochu".equals(bean.getOperate())){
//							handler.startElement("", "", "liexinxi", null);
//							handler.startElement("", "", "liemingzi", null);
//				        	value=detail.getDcdfromcolumns();
//				        	value = value==null?"":value; 
//				        	handler.characters(value.toCharArray(), 0, value.length());
//				        	handler.endElement("", "", "liemingzi");
//				        	
//				        	handler.startElement("", "", "lieshuoming", null);
//				        	value=detail.getDcdfromcolumnsscribe();
//				        	value = value==null?"":value; 
//				        	handler.characters(value.toCharArray(), 0, value.length());
//				        	handler.endElement("", "", "lieshuoming");
//				        	
//				        	handler.startElement("", "", "lietype", null);
//				        	value=detail.getDcdfromcloumnstype();
//				        	value = value==null?"":value; 
//				        	handler.characters(value.toCharArray(), 0, value.length());
//				        	handler.endElement("", "", "lietype");
//				        	
//				            handler.endElement("", "", "liexinxi");
//						}
//					}
//					bean.setLielist(mylist);
//					daochusql=thetable.getDaochusql();
//					if("0".equals(thetable.getDdtismaintable())){
//						//前面已经构造，故删除开始--2013/11/18
////						daochusql=daochusql+" a where 1=1";
////						if(!"".equals(chaxuncon)){
////							daochusql=daochusql+" and "+chaxuncon;
////						}
////						if (hasCriminalId) {
////							daochusql = daochusql + " and a.criminalid in ( select criminalid from tbcriminal_basic_info t where 1= 1 " + strSdisString + " )";
////						}
//////						maintablesql=thetable.getDaochusql();
////						maintablesql=daochusql;
//						////前面已经构造，故删除结束--2013/11/18
//						daochusql = maintablesql;
//					}else{
//						//其他表根据数据关系生成查询条件
//						if(null!=thetable.getShujuguanxi()&&!"".equals(thetable.getShujuguanxi())){
//							String[]  temparr=thetable.getShujuguanxi().split(",");
//							String tempcon="";
//							for(String str:temparr){
//								String[] temp2=str.split("=");
//								if("".equals(tempcon)){
//									tempcon="a."+temp2[0]+"=b."+temp2[1];
//								}else{
//									tempcon+=" and a."+temp2[0]+"=b."+temp2[1];
//								}
//							}
//							daochusql=daochusql+" b where 1=1";
//							//额外查询条件追加开始 2013-11-19
//							String addconditionString = thetable.getAddcondition();
//							if (!StringUtil.isNullOrEmpty(addconditionString)) {
//								daochusql=daochusql+" and b." + addconditionString;
//							}
//							//额外查询条件追加结束 2013-11-19
//							if(!"".equals(tempcon)){
////								daochusql=daochusql+" and exists ("+maintablesql+" a where "+tempcon;
//								daochusql=daochusql+" and exists ("+maintablesql+" and "+tempcon;
//								if(!"".equals(chaxuncon)){
//									daochusql=daochusql+" and "+chaxuncon+") ";
//								}else{
//									daochusql=daochusql+" ) ";
//								}
//							}
//						}
//					}
//					if(!"".equals(daochusql)){
//						ResultSet rs=null;
////						Connection conn=null;
//						Statement stmt=null;
//						try {
////							conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//							stmt = conn.createStatement(); 
//							String countsqlString="select count(*) as aa from ("+daochusql+") dd";
//							//如果不是导出 则查询时只查询前10条
//							if(!"daochu".equals(bean.getOperate())){
//								if("Microsoft SQL Server".equals(bs.getDatabasetype()) || "Microsoft SQL Server 2005".equals(bs.getDatabasetype())){
//									String[] arrStrings=daochusql.split("select");
//									String  aaaaa="select top 100 ";
//									for (int m=1;m<arrStrings.length;m++){
//										aaaaa=aaaaa+arrStrings[m];
//									}
//									daochusql=aaaaa;
//								}
//								if("Oracle".equals(bs.getDatabasetype())){
//									daochusql=daochusql+" and rownum<100";
//								}
//							}
//							rs = stmt.executeQuery(daochusql); 
//							while (rs.next()) {
//								//每条数据
//								if("daochu".equals(bean.getOperate())){
//									handler.startElement("", "", "dchang", null);
//								}
//								for(Object obj:lielist){
//									DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//									String ttvalue=this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(), detail.getDcdfromcolumns(), rs);
//									if(null==ttvalue){
//										ttvalue="";
//									}
//									valuelist.add(ttvalue);
//									//写入列信息
//									if("daochu".equals(bean.getOperate())){
//										//写入查询出的数据
//										handler.startElement("", "", detail.getDcdfromcolumns().toLowerCase(), null);
//										value=ttvalue;
//							        	handler.characters(value.toCharArray(), 0, value.length());
//							        	handler.endElement("", "", detail.getDcdfromcolumns().toLowerCase());
//									}
//								}
//								
//								tem++;
//							    taskBean.setCounter(tem);
//							    
//								
//								if(this.session != null){
//									this.session.put("taskbean", taskBean);
//								}
//								
//								if("daochu".equals(bean.getOperate())){
//									handler.endElement("", "", "dchang");
//								}
//							}
//							rs.close();
//							stmt.close();
//							stmt = conn.createStatement();
//							rs=stmt.executeQuery(countsqlString);
//							while (rs.next()) {
//								totalnum=rs.getInt("aa");     
//							}
//							rs.close();
//							stmt.close();
//						} catch (Exception  e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}finally{
//							if(null!=rs){
//								try {
//									rs.close();
//								} catch (SQLException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//							if(null!=stmt){
//								try {
//									stmt.close();
//								} catch (SQLException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
////							if(null!=conn){
////								try {
////									conn.close();
////								} catch (SQLException e) {
////									// TODO Auto-generated catch block
////									e.printStackTrace();
////								}
////							}
//						}
//					}
//					if("daochu".equals(bean.getOperate())){
//						handler.endElement("", "","dctable");
//					}
//				}
//				//写入完成
//				if("daochu".equals(bean.getOperate())){
//					handler.endElement("", "", "dcfangan");
//			        handler.endDocument();
//				}
//				bean.setTitlelist(titlelist);
//				bean.setTotalnum(totalnum);
//				bean.setValuelist(valuelist);
//			}
//				String zipfilepath = basePath.substring(0,basePath.lastIndexOf(GkzxCommon.SINGLESEPALINE)) + ".zip";
//				DocZipUtil.compress(basePath, zipfilepath);
//				if (type.equals(GkzxCommon.FAYUAN)) {
//					this.dbmsNewDateExportSchemeManager.commitToCourt(zipfilepath);
//				}
//			}catch(Exception e1){
//				e1.printStackTrace();
//			}finally{
//				if(null!=conn){
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//		    	if(null!=outStream){
//		    		try {
//						outStream.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		    	}
//		    }
//			if("daochu".equals(bean.getOperate())){
//				/*File file = new File(fileNameN);
//			    byte[] fileContent = null;
//			    InputStream is = null;
//				try {
//					is = new FileInputStream(file);
//					fileContent = new byte[is.available()];
//					is.read(fileContent,0,fileContent.length);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					if(null!=is){
//						try {
//							is.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//				OutputStream outputStream=null;
//				try {
//					response.setContentType("application/octec-stream");
//					response.setHeader("Content-Disposition", "attachment;filename="+ new String((fname).getBytes(), "ISO-8859-1"));
//					outputStream = response.getOutputStream();
//					outputStream.write(fileContent, 0, fileContent.length);
//					outputStream.flush();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					try {
//						if(null!=outputStream){
//							outputStream.close();
//						}
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					outputStream = null;
//				}
//				File f=new File(fileNameN);
//				if(f.exists()){
//					f.delete();
//				}*/
//			}
//		}
//		DbmsDaochuListInfo ddli = new DbmsDaochuListInfo();
//		String fileid = UUID.randomUUID().toString();
//		ddli.setCreatedate(new Date());
//		ddli.setFileid(fileid);
//		ddli.setSchemeid(bean.getDdcid());
//		String basicdep = "";
//		try {
//			basicdep = (String)session.get("basicdep");
//		} catch (Exception e) {
//		}
//		ddli.setSdid(basicdep);
//		ddli.setFilename(ddcname);
//		ddli.setFilepath(filepath);
//		ddli.setFilerealname(fileRealName);
//		baseDao.insertObject(ddli);	
//		bean.setHiddencon(bean.getCondition());
//		bean.setCondition("");
//		return rentemp;
//	}
//	private String getcontentvalue(String basetype,String dcdfromcloumnstype,String dcdfromcolumns,ResultSet rs){
//		String value="";
////		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if(null!=dcdfromcolumns&&!"".equals(dcdfromcolumns)){
//			int intIndex = dcdfromcolumns.toLowerCase().lastIndexOf(GkzxCommon.AS);
//			if (intIndex > -1) {
//				dcdfromcolumns = dcdfromcolumns.toLowerCase().substring(intIndex + 4);
//			}
//			try{
//				if("Microsoft SQL Server".equals(basetype) || "Microsoft SQL Server 2005".equals(basetype)){
//					if("varchar".equals(dcdfromcloumnstype)||"char".equals(dcdfromcloumnstype)||"nchar".equals(dcdfromcloumnstype)||"nvarchar".equals(dcdfromcloumnstype)||"text".equals(dcdfromcloumnstype)||"ntext".equals(dcdfromcloumnstype)||"uniqueidentifier".equals(dcdfromcloumnstype)||"timestamp".equals(dcdfromcloumnstype)){
//						value=rs.getString(dcdfromcolumns);
//						if (value != null){
//						//value=value.replaceAll("“", "&#34;");
//						//value=value.replaceAll("”", "&#34;");
//						value=value.trim().replaceAll("\"", "“").replaceAll("\"", "”").replaceAll("'", "‘").replaceAll("'", "’").replaceAll("\\\r\\\n", "&#13;&#10;").replaceAll("\\r\\n", "&#13;&#10;").replaceAll("\r\n", "&#13;&#10;").replaceAll("\r", "&#13;&#10;").replaceAll("\n", "&#13;&#10;");
////						value=value.;
////						value=value.;
////						value=value.;
////						value=value.;
////						value=value.;
////						value=value.;
////						value=value.;
////						value=value.;
//						}
//					}
//					if("datetime".equalsIgnoreCase(dcdfromcloumnstype)){
//						java.sql.Timestamp zhi=rs.getTimestamp(dcdfromcolumns);
//						if(null!=zhi){
//							value=m_sdf.format(zhi);
//						}
//					}
//					if("int".equals(dcdfromcloumnstype)||"int identity".equals(dcdfromcloumnstype)||"tinyint".equals(dcdfromcloumnstype)||"smallint".equals(dcdfromcloumnstype)){
//						Integer  temp=rs.getInt(dcdfromcolumns);
//						if(null!=temp){
//							value=String.valueOf(temp);
//						}
//					}
//					if("bit".equals(dcdfromcloumnstype)){
//						Integer  zhi=rs.getInt(dcdfromcolumns);
//						if(null!=zhi){
//							value=String.valueOf(zhi);
//						}
//					}
//					if("bigint".equals(dcdfromcloumnstype)){
//						Long  zhi=rs.getLong(dcdfromcolumns);
//						if(null!=zhi){
//							value=String.valueOf(zhi);
//						}
//					}
//					if("float".equals(dcdfromcloumnstype)){
//						Double   zhi=rs.getDouble(dcdfromcolumns);
//						if(null!=zhi){
//							value=String.valueOf(zhi);
//						}
//					}
//					if("decimal".equals(dcdfromcloumnstype)||"money".equals(dcdfromcloumnstype)||"smallmoney".equals(dcdfromcloumnstype)||"numeric".equals(dcdfromcloumnstype)){
//						BigDecimal   zhi=rs.getBigDecimal(dcdfromcolumns);
//						if(null!=zhi){
//							value=String.valueOf(zhi);
//						}
//					}
//					if("real".equals(dcdfromcloumnstype)){
//						Float   zhi=rs.getFloat(dcdfromcolumns);
//						if(null!=zhi){
//							value=String.valueOf(zhi);
//						}
//					}
//					if ("image".equals(dcdfromcloumnstype)||"varbinary".equals(dcdfromcloumnstype)) {
////						value=rs.getString(dcdfromcolumns);
//						InputStream inStreamDoc = null;
//						try {
//							Blob blob = rs.getBlob(dcdfromcolumns);//
//							if (blob == null) {
//								value = "";
//							} else {
//								inStreamDoc = blob.getBinaryStream();
//								byte[] tempDoc = new byte[(int) blob.length()];
//								inStreamDoc.read(tempDoc);
//								value = new String(tempDoc);
//							}
//						} catch (Exception e2) {
//							e2.printStackTrace();
//						} finally {
//							if (null != inStreamDoc) {
//								inStreamDoc.close();
//								inStreamDoc = null;
//							}
//						}
//					}
////					if("smalldatetime".equals(dcdfromcloumnstype)||"datetime".equals(dcdfromcloumnstype)||"timestamp".equals(dcdfromcloumnstype)){
////						Timestamp    zhi=rs.getTimestamp(dcdfromcolumns);
////						if(null!=zhi){
////							java.sql.Date sqlDate = new java.sql.Date(zhi.getTime());
////							value=sdf.format(sqlDate);
////						}
////					}
//				}
//				if("Oracle".equals(basetype)){
//					if("DATE".equals(dcdfromcloumnstype)){
//						java.sql.Timestamp zhi=rs.getTimestamp(dcdfromcolumns);
//						if(null!=zhi){
//							value=m_sdf.format(zhi);
//						}
//					}
//					if("VARCHAR2".equals(dcdfromcloumnstype)){
//						value=rs.getString(dcdfromcolumns);
//					}
//					if("CHAR".equals(dcdfromcloumnstype)){
//						value=rs.getString(dcdfromcolumns);
//					}
//					if("NUMBER".equals(dcdfromcloumnstype)){
//						Long temp=rs.getLong(dcdfromcolumns);
//						if(null!=temp){
//							value=String.valueOf(temp);
//						}
//					}
//					if("FLOAT".equals(dcdfromcloumnstype)){
//						BigDecimal temp=rs.getBigDecimal(dcdfromcolumns);
//						if(null!=temp){
//							value=String.valueOf(temp);
//						}
//					}
//					if("CLOB".equals(dcdfromcloumnstype)){
//						Reader inStreamDoc =null;
//						try{
//							Clob clob = rs.getClob(dcdfromcolumns);// 
//							if(clob == null) {   
//								value="";  
//							} 
//							else{
//								inStreamDoc = clob.getCharacterStream();   
//								char[] tempDoc = new char[(int) clob.length()];   
//								inStreamDoc.read(tempDoc);   
//								value=new String(tempDoc);   
//							}
//						}catch(Exception e2){
//							e2.printStackTrace();
//						}finally{
//							if(null!=inStreamDoc){
//								inStreamDoc.close();
//								inStreamDoc =null;
//							}
//						}
//
//					}
//					if("INTEGER".equals(dcdfromcloumnstype)){
//						BigDecimal temp=rs.getBigDecimal(dcdfromcolumns);
//						if(null!=temp){
//							value=String.valueOf(temp);
//						}
//					}
//					
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			if(null!=value){
//				//value=value.replace("\r\n", "&#13;&#10;");
//			}
//		}
//		return value;
//	}
//	
//	
//	 /**   
//    * 此方法描述的是：单独的查询导出数据（getTableInfo是相同的代码，这里只是原样复制）
//	 * @param   name  
//	 * @param  @return 
//	 * @Exception 异常对象
//    * @author: 袁德民   
//    * @version: 2013-11-20 下午09:04:15   
//    */   
//
//	public String queryTableInfo() {
//		String rentemp = SUCCESS;
//		if (null != bean.getDdcid() && !"".equals(bean.getDdcid())) {
//			TransformerHandler handler = null;
//			OutputStream outStream = null;
//			SAXTransformerFactory fac = null;
//			String maintablesql = "";
//			String basePath = absolutePath
//					.getAbsolutePath("/WEB-INF/classes/com/gkzx/dbmsnew/applicationContext-actions.xml");
//			Date now = new Date();
//			String fname = "DBMS_DATABASE_" + now.getTime() + ".xml";
//			String fileNameN = basePath + fname;
//
//			String chaxuncon = "";
//			if (null != bean.getCondition() && !"".equals(bean.getCondition())) {
//				chaxuncon = bean.getCondition();
//			} else if (null != bean.getHiddencon()
//					&& !"".equals(bean.getHiddencon())) {
//				chaxuncon = bean.getHiddencon();
//			}
//			Connection conn = null;
//			try {
//				List<String> titlelist = new ArrayList<String>();
//				List<String> valuelist = new ArrayList<String>();
//				// 判断方案操作数据库类型
//				String basesql = "from DbmsDatabaseNew a where a.ddid in (select fromdatabaseid from DbmsDatasChemeNew c where c.ddcid='"
//						+ bean.getDdcid() + "')";
//				List<Object> baselist = baseDao.queryListObjectAll(basesql);
//				String basetype = "";
//				DbmsDatabaseNew bs = new DbmsDatabaseNew();
//				if (null != baselist && baselist.size() > 0) {
//					bs = (DbmsDatabaseNew) baselist.get(0);
//					basetype = bs.getDatabasetype();
//				}
//				conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//
//				Object sdidobj = request.getSession().getAttribute("sdid");
//				String strSdisString = "";
//				if (null != sdidobj) {
//					strSdisString = jsonTool.getFaYuanAndSdidString(conn,
//							(String) sdidobj);
//					if (!StringUtil.isNullOrEmpty(strSdisString)) {
//						String[] sdidArr = strSdisString.split(",");
//						for (int k = 0; k < sdidArr.length; k++) {
//							if (k == 0) {
//								strSdisString = " and ( t.depid = '"
//										+ sdidArr[k] + "'";
//							} else {
//								strSdisString += " or " + " t.depid = '"
//										+ sdidArr[k] + "'";
//							}
//						}
//
//						if (sdidArr.length > 0) {
//							strSdisString += " ) ";
//						}
//					}
//				}
//
//				bean.setBasetype(basetype);
//				String daochusql = "";
//				int totalnum = 0;
//				String biaosql = "from DbmsDatasTableNew a where a.id.ddcid='"
//						+ bean.getDdcid() + "' and a.ddtismaintable='0'";
//				// 构造主表查询条件开始
//				List<Object> biaolist = baseDao.queryListObjectAll(biaosql);
//				for (Object biaoobj : biaolist) {
//					DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
//					String value = "";
//					DatabaseMetaData dbmd = conn.getMetaData();
//					// 获得字段信息
//					ResultSet rsColumns = null;
//					if ("Oracle".equals(basetype))
//						rsColumns = dbmd.getColumns(null, bs.getDatabaseuser()
//								.toUpperCase(), thetable.getTablename()
//								.toUpperCase(), null);
//					else
//						rsColumns = dbmd.getColumns(null, null, thetable
//								.getTablename().toUpperCase(), null);
//					boolean hasCriminalId = false;
//					while (rsColumns.next()) {
//						try {
//							String COLUMN_NAME = rsColumns
//									.getString("COLUMN_NAME");
//							if (GkzxCommon.CRIMINALID
//									.equalsIgnoreCase(COLUMN_NAME)) {
//								hasCriminalId = true;
//							}
//						} catch (Exception e) {
//						}
//					}
//					daochusql = thetable.getDaochusql();
//					if ("0".equals(thetable.getDdtismaintable())) {
//						daochusql = daochusql + " a where 1=1";
//						// 额外查询条件追加开始 2013-11-19
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							daochusql = daochusql + "  and a."
//									+ addconditionString;
//						}
//						// 额外查询条件追加结束2013-11-19
//						if (!"".equals(chaxuncon)) {
//							daochusql = daochusql + " and " + chaxuncon;
//						}
//						if (hasCriminalId) {
//							daochusql = daochusql
//									+ " and a.criminalid in ( select criminalid from tbcriminal_basic_info t where 1= 1 "
//									+ strSdisString + " )";
//						}
//						// maintablesql=thetable.getDaochusql();
//						maintablesql = daochusql;
//					}
//				}
//				// 构造主表查询条件结束
//				if ("daochu".equals(bean.getOperate())) {
//					// biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' order by a.ddtismaintable";
//					biaosql = "from DbmsDatasTableNew a where a.id.ddcid='"
//							+ bean.getDdcid() + "' order by a.ddtorder";
//					fac = (SAXTransformerFactory) SAXTransformerFactory
//							.newInstance();
//					handler = fac.newTransformerHandler();
//					Transformer transformer = handler.getTransformer();
//					transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//					transformer.setOutputProperty(
//							OutputKeys.OMIT_XML_DECLARATION, "no");
//					File file = new File(fileNameN);
//					if (!file.exists()) {
//						file.createNewFile();
//					}
//					outStream = new FileOutputStream(fileNameN);
//					handler.setResult(new StreamResult(outStream));
//					String value = "";
//					// 写入数据
//					handler.startElement("", "", "dcfangan", null);
//					handler.startElement("", "", "dcfanganddcid", null);
//					value = bean.getDdcid();
//					handler.characters(value.toCharArray(), 0, value.length());
//					handler.endElement("", "", "dcfanganddcid");
//				}
//				biaolist = baseDao.queryListObjectAll(biaosql);
//				for (Object biaoobj : biaolist) {
//					DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
//					String value = "";
//					DatabaseMetaData dbmd = conn.getMetaData();
//					// 获得字段信息
//					ResultSet rsColumns = null;
//					if ("Oracle".equals(basetype))
//						rsColumns = dbmd.getColumns(null, bs.getDatabaseuser()
//								.toUpperCase(), thetable.getTablename()
//								.toUpperCase(), null);
//					else
//						rsColumns = dbmd.getColumns(null, null, thetable
//								.getTablename().toUpperCase(), null);
//					boolean hasCriminalId = false;
//					while (rsColumns.next()) {
//						try {
//							String COLUMN_NAME = rsColumns
//									.getString("COLUMN_NAME");
//							if (GkzxCommon.CRIMINALID
//									.equalsIgnoreCase(COLUMN_NAME)) {
//								hasCriminalId = true;
//							}
//						} catch (Exception e) {
//						}
//					}
//					if ("daochu".equals(bean.getOperate())) {
//						// 写入表信息
//						handler.startElement("", "", "dctable", null);
//						handler.startElement("", "", "dctableddtid", null);
//						value = thetable.getId().getDdtid();
//						handler.characters(value.toCharArray(), 0, value
//								.length());
//						handler.endElement("", "", "dctableddtid");
//
//						handler.startElement("", "", "dctablename", null);
//						value = thetable.getTablename();
//						handler.characters(value.toCharArray(), 0, value
//								.length());
//						handler.endElement("", "", "dctablename");
//
//						handler.startElement("", "", "dctabledescrition", null);
//						value = thetable.getDescrition() == null ? ""
//								: thetable.getDescrition();
//						handler.characters(value.toCharArray(), 0, value
//								.length());
//						handler.endElement("", "", "dctabledescrition");
//
//						handler.startElement("", "", "dctableddtismaintable",
//								null);
//						value = thetable.getDdtismaintable() == null ? ""
//								: thetable.getDdtismaintable();
//						handler.characters(value.toCharArray(), 0, value
//								.length());
//						handler.endElement("", "", "dctableddtismaintable");
//					}
//					// 查询所有列
//					String liesql = "from DbmsDatasChemeDetailNew a where a.id.ddcid='"
//							+ bean.getDdcid()
//							+ "' and a.ddtid='"
//							+ thetable.getId().getDdtid() + "'";
//					List<Object> lielist = baseDao.queryListObjectAll(liesql);
//					List<DbmsDatasChemeDetailNew> mylist = new ArrayList<DbmsDatasChemeDetailNew>();
//					for (Object obj : lielist) {
//						DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
//						titlelist.add(detail.getDcdfromcolumnsscribe());
//						mylist.add(detail);
//						if ("daochu".equals(bean.getOperate())) {
//							handler.startElement("", "", "liexinxi", null);
//							handler.startElement("", "", "liemingzi", null);
//							value = detail.getDcdfromcolumns();
//							handler.characters(value.toCharArray(), 0, value
//									.length());
//							handler.endElement("", "", "liemingzi");
//
//							handler.startElement("", "", "lieshuoming", null);
//							value = detail.getDcdfromcolumnsscribe();
//							handler.characters(value.toCharArray(), 0, value
//									.length());
//							handler.endElement("", "", "lieshuoming");
//
//							handler.startElement("", "", "lietype", null);
//							value = detail.getDcdfromcloumnstype();
//							handler.characters(value.toCharArray(), 0, value
//									.length());
//							handler.endElement("", "", "lietype");
//
//							handler.endElement("", "", "liexinxi");
//						}
//					}
//					bean.setLielist(mylist);
//					daochusql = thetable.getDaochusql();
//					if ("0".equals(thetable.getDdtismaintable())) {
//						// 前面已经构造，故删除开始--2013/11/18
//						// daochusql=daochusql+" a where 1=1";
//						// if(!"".equals(chaxuncon)){
//						// daochusql=daochusql+" and "+chaxuncon;
//						// }
//						// if (hasCriminalId) {
//						// daochusql = daochusql +
//						// " and a.criminalid in ( select criminalid from tbcriminal_basic_info t where 1= 1 "
//						// + strSdisString + " )";
//						// }
//						// // maintablesql=thetable.getDaochusql();
//						// maintablesql=daochusql;
//						// //前面已经构造，故删除结束--2013/11/18
//						daochusql = maintablesql;
//					} else {
//						// 其他表根据数据关系生成查询条件
//						if (null != thetable.getShujuguanxi()
//								&& !"".equals(thetable.getShujuguanxi())) {
//							String[] temparr = thetable.getShujuguanxi().split(
//									",");
//							String tempcon = "";
//							for (String str : temparr) {
//								String[] temp2 = str.split("=");
//								if ("".equals(tempcon)) {
//									tempcon = "a." + temp2[0] + "=b."
//											+ temp2[1];
//								} else {
//									tempcon += " and a." + temp2[0] + "=b."
//											+ temp2[1];
//								}
//							}
//							daochusql = daochusql + " b where 1=1";
//							// 额外查询条件追加开始 2013-11-19
//							String addconditionString = thetable
//									.getAddcondition();
//							if (!StringUtil.isNullOrEmpty(addconditionString)) {
//								daochusql = daochusql + " and b."
//										+ addconditionString;
//							}
//							// 额外查询条件追加结束 2013-11-19
//							if (!"".equals(tempcon)) {
//								// daochusql=daochusql+" and exists ("+maintablesql+" a where "+tempcon;
//								daochusql = daochusql + " and exists ("
//										+ maintablesql + " and " + tempcon;
//								if (!"".equals(chaxuncon)) {
//									daochusql = daochusql + " and " + chaxuncon
//											+ ") ";
//								} else {
//									daochusql = daochusql + " ) ";
//								}
//							}
//						}
//					}
//					if (!"".equals(daochusql)) {
//						ResultSet rs = null;
//						// Connection conn=null;
//						Statement stmt = null;
//						try {
//							// conn =
//							// dbmsNewDateExportSchemeManager.getConnection(bs);
//							stmt = conn.createStatement();
//							String countsqlString = "select count(*) as aa from ("
//									+ daochusql + ") dd";
//							// 如果不是导出 则查询时只查询前10条
//							if (!"daochu".equals(bean.getOperate())) {
//								if ("Microsoft SQL Server".equals(bs
//										.getDatabasetype())
//										|| "Microsoft SQL Server 2005"
//												.equals(bs.getDatabasetype())) {
//									String[] arrStrings = daochusql
//											.split("select");
//									String aaaaa = "select top 100 ";
//									for (int m = 1; m < arrStrings.length; m++) {
//										aaaaa = aaaaa + arrStrings[m];
//									}
//									daochusql = aaaaa;
//								}
//								if ("Oracle".equals(bs.getDatabasetype())) {
//									daochusql = daochusql + " and rownum<100";
//								}
//							}
//							rs = stmt.executeQuery(daochusql);
//							while (rs.next()) {
//								// 每条数据
//								if ("daochu".equals(bean.getOperate())) {
//									handler
//											.startElement("", "", "dchang",
//													null);
//								}
//								for (Object obj : lielist) {
//									DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
//									String ttvalue = this.getcontentvalue(
//											basetype, detail
//													.getDcdfromcloumnstype(),
//											detail.getDcdfromcolumns(), rs);
//									if (null == ttvalue) {
//										ttvalue = "";
//									}
//									valuelist.add(ttvalue);
//									// 写入列信息
//									if ("daochu".equals(bean.getOperate())) {
//										// 写入查询出的数据
//										handler.startElement("", "", detail
//												.getDcdfromcolumns()
//												.toLowerCase(), null);
//										value = ttvalue;
//										handler.characters(value.toCharArray(),
//												0, value.length());
//										handler.endElement("", "", detail
//												.getDcdfromcolumns()
//												.toLowerCase());
//									}
//								}
//								if ("daochu".equals(bean.getOperate())) {
//									handler.endElement("", "", "dchang");
//								}
//							}
//							rs.close();
//							stmt.close();
//							stmt = conn.createStatement();
//							rs = stmt.executeQuery(countsqlString);
//							while (rs.next()) {
//								totalnum = rs.getInt("aa");
//							}
//							rs.close();
//							stmt.close();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} finally {
//							if (null != rs) {
//								try {
//									rs.close();
//								} catch (SQLException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//							if (null != stmt) {
//								try {
//									stmt.close();
//								} catch (SQLException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//							// if(null!=conn){
//							// try {
//							// conn.close();
//							// } catch (SQLException e) {
//							// // TODO Auto-generated catch block
//							// e.printStackTrace();
//							// }
//							// }
//						}
//					}
//					if ("daochu".equals(bean.getOperate())) {
//						handler.endElement("", "", "dctable");
//					}
//				}
//				// 写入完成
//				if ("daochu".equals(bean.getOperate())) {
//					handler.endElement("", "", "dcfangan");
//					handler.endDocument();
//				}
//				bean.setTitlelist(titlelist);
//				bean.setTotalnum(totalnum);
//				bean.setValuelist(valuelist);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}finally{
//				if(null!=conn){
//					try {
//						conn.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//		    	if(null!=outStream){
//		    		try {
//						outStream.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		    	}
//		    }
//			if("daochu".equals(bean.getOperate())){
//				File file = new File(fileNameN);
//			    byte[] fileContent = null;
//			    InputStream is = null;
//				try {
//					is = new FileInputStream(file);
//					fileContent = new byte[is.available()];
//					is.read(fileContent,0,fileContent.length);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					if(null!=is){
//						try {
//							is.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//				OutputStream outputStream=null;
//				try {
//					response.setContentType("application/octec-stream");
//					response.setHeader("Content-Disposition", "attachment;filename="+ new String((fname).getBytes(), "ISO-8859-1"));
//					outputStream = response.getOutputStream();
//					outputStream.write(fileContent, 0, fileContent.length);
//					outputStream.flush();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					try {
//						if(null!=outputStream){
//							outputStream.close();
//						}
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					outputStream = null;
//				}
//				File f=new File(fileNameN);
//				if(f.exists()){
//					f.delete();
//				}
//			}
//		}
//		bean.setHiddencon(bean.getCondition());
//		bean.setCondition("");
//		if("daochu".equals(bean.getOperate())){
//			return null;
//		}else{
//			return rentemp; 
//		}
//	}
//	
//	public String condition;
//	 
//	    /**   
//	     * 此方法描述的是：得到数据
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:05:23   
//	     */   
//	    
//	public String getInformation(){
//		String tion=null;
//		String ddcid=null;
//		ddcid=request.getSession().getAttribute("cid").toString();
//		DbmsDatasChemeNew cheme=dbmsNewDateExportSchemeManager.getDatasChemeNewInfo(ddcid);
//		DbmsDatabaseNew database=dbmsNewDateExportSchemeManager.getDataBaseNewInfo(cheme.getFromdatabaseid());
//		Connection conn=dbmsNewDateExportSchemeManager.getConnection(database);
//		List<String> str=null;//列实质列名
//		List<Object[]> objs=null;//值集合
//		List<String> coumlusComm=null;//列描述
//		if(null!=condition&&!"".equals(condition)){
//			//条件分开
//			String[] single=condition.split(";");
//			for(int i=0;i<single.length;i++){
//				//得到单个条件
//				String[] sing=single[i].split(",");
//				if(sing.length<=3){
//					if(tion==null){
//						tion=sing[0];
//					}else{
//						tion+=" and "+sing[0];
//					}
//					tion+=replaceLog(sing[1],sing[2]);
//				}else{
//				}
//			}
//		}
//		if(conn!=null){
//			Document document = Dom4jXMLUtil.inits(basePath,ddcid+"_Query");
//			Element root=document.getRootElement();
//			Element sql=root.element("SQL");
//			String SQL=sql.getText();
//			if(null!=tion&&!"".equals(tion)){
//				SQL+=" where "+tion;
//			}
//			Element coumlus=root.element("SYLLABLE");
//			Iterator<Element> it=coumlus.elementIterator();
//			str=new ArrayList<String>();
//			coumlusComm=new ArrayList<String>();
//			while(it.hasNext()){
//				Element coumlu=it.next();
//				coumlusComm.add(coumlu.attributeValue("COMM"));
//				str.add(coumlu.attributeValue("COLUMNSNAME"));
//			}
//			Statement st=null;
//			ResultSet rs=null;
//			List<Map> list=null;
//			try {
//				st=conn.createStatement();
//				rs=st.executeQuery(SQL);
//				objs=replaceResultSet(rs,str);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}finally{
//				try {
//					rs.close();
//					st.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			request.setAttribute("coumlusComm", coumlusComm);
//			request.setAttribute("coulmList", str);
//			request.setAttribute("valueList", objs);
//		}
//		String docName="ViewQuery@"+ddcid+"information"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		request.setAttribute("path", xmlPaht);
//		request.setAttribute("docName", docName);
//		return SUCCESS;
//	}
//	
//	 
//	    /**   
//	     * 此方法描述的是：替换逻辑符号 (不包括介于)
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:06:41   
//	     */   
//	    
//	private String replaceLog(String log,Object value){
//		String don=null;
//		String sqlValue=null;
//		//如果是时间格式就转换
//		if(value.toString().split("-").length>1){
//			sqlValue="to_date('"+value+"','yyyy-MM-dd')";
//		}else{
//			sqlValue=value.toString();
//		}
//		if(log.equals("eqeq")){
//			don=" = '"+sqlValue+"'";
//		}else if(log.equals("!eq")){
//			don=" != '"+sqlValue+"'";
//		}else if(log.equals("1gt")){
//			don=" > "+sqlValue;
//		}else if(log.equals("1lt")){
//			don=" < "+sqlValue;
//		}else if(log.equals("1gteq")){
//			don=" >= "+sqlValue;
//		}else if(log.equals("1lteq")){
//			don=" <= "+sqlValue;
//		}else if(log.equals("like")){
//			don=" like '%"+sqlValue+"%'";
//		}
//		return don;
//	}
//	
//	 
//	    /**   
//	     * 此方法描述的是：将ResultSet 转换成 List
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:07:38   
//	     */   
//	    
//	private List<Object[]> replaceResultSet(ResultSet rs,List<String> cname) throws SQLException{
//		List<Object[]> list = new ArrayList<Object[]>();   
//		while(rs.next()){
//			Object[] obj=new Object[cname.size()];
//			for(int i=0;i<cname.size();i++){
//				obj[i]=rs.getObject(cname.get(i));
//			}
//			list.add(obj);
//		}
//		return list;
//	}
//	    /**   
//	     * 此方法描述的是：  得到表信息
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:07:18   
//	     */   
//	    
//	private List<String> getTableInfo(Connection conn,String SQL){
//		List<String> str=new ArrayList<String>();
//		Statement st= null;
//		ResultSet rs= null;
//		try {
//			st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//			rs=st.executeQuery(SQL);
//			ResultSetMetaData rsmd=rs.getMetaData();
//			for(int i=1;i<=rsmd.getColumnCount();i++){
//				String name=rsmd.getColumnName(i);
//				str.add(name);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtils.closeResource(null, st, rs);
//		}
//		return str;
//	}
//	
//	/**文件路径*/
//	public String docPath;
//	/**文件名称*/
//	public String docName;
//	
//	 
//	    /**   
//	     * 此方法描述的是：文件压缩
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:08:07   
//	     */   
//	    
//	public String compressDocXML(){
//		//将视图XML加入导出数据文件夹中
//		String chemeName = docName.substring(docName.lastIndexOf("@") + 1,docName.lastIndexOf("information"));//
//		String docXMLName=chemeName+"_Query.xml";
//		String docXMLPath=basePath+"/"+docXMLName;
//		String newXMLPath=docPath+"/"+docXMLName;
//		//文件Copy
//		DocZipUtil.copyFile(docXMLPath,newXMLPath);
//		Document document=Dom4jXMLUtil.inits(newXMLPath);//得到文件的document对象
//		Element root=document.getRootElement();
//		Element codes=root.element("CODES");//得到代码集合节点
//		Iterator<Element> codeList=codes.elementIterator();
//		DbmsDatasChemeNew cheme=dbmsNewDateExportSchemeManager.getDatasChemeNewInfo(chemeName);//得到数据方案对象
//		//根据数据方案对象的导出数据库ID得到数据库管理对象，根据得到的数据库管理对象得到该对象的connection对象
//		Connection conn=dbmsNewDateExportSchemeManager.getConnection(dbmsNewDateExportSchemeManager.getDataBaseNewInfo(cheme.getFromdatabaseid()));
//		Statement st=null;
//		ResultSet rs=null;
//		while(codeList.hasNext()){
//			Element code=codeList.next();//得到一个代码节点
//			try {
//				st=conn.createStatement();
//				rs=st.executeQuery(code.getText());//根据sql语句得到一堆数据
//				Document codeDoc=Dom4jXMLUtil.inits(docPath, code.getName()+"@codesList@"+chemeName+"code");//新建的代码文件名称为     代码名称@information@方案ID
//				Element codeRoot=codeDoc.getRootElement();
//				while(rs.next()){
//					Element codeing=codeRoot.addElement("CODES");
//					codeing.addText(rs.getString(1));
//				}
//				Dom4jXMLUtil.overRideXml(docPath+"/"+code.getName()+"@codesList@"+chemeName+"code.xml", codeDoc);//导出代码文件
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}finally{
//				try {
//					rs.close();
//					st.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		String path=docPath+"/"+docName+".rar";
//		DocZipUtil.compress(docPath,path);
////		File f=new File(path);
////		File[] files=f.listFiles();
////		if(null!=files&&0<files.length){
////			for(int i=0;i<files.length;i++){
////				File file=new File(files[i].toString());
////				file.delete();
////			}
////			f.delete();
////		}else{
////			f.delete();
////		}
////		request.setAttribute("docPath", path);
////		request.setAttribute("doc", docPath);
//		//文件压缩
//		DocZipUtil.docDown(response, path);
//		File f=new File(docPath);
//		f.delete();
//		File[] files=f.listFiles();
//		for(int i=0;i<files.length;i++){
//			File file=new File(files[i].toString());
//			file.delete();
//		}
//		String end=new SimpleDateFormat("HH:mm:ss").format(new Date());
//		return SUCCESS;
//	}
//	
//	 
//	    /**   
//	     * 此方法描述的是：文件下载
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:09:59   
//	     */   
//	    
//	public String docDownXML(){
//		DocZipUtil.docDown(response, docPath);
//		File f=new File(docName);
//		File[] files=f.listFiles();
//		if(null!=files&&0<files.length){
//			for(int i=0;i<files.length;i++){
//				File file=new File(files[i].toString());
//				file.delete();
//			}
//		}
//		return SUCCESS;
//	}
//	
//	 
//	    
//	 
//	    /**   
//	     * 此方法描述的是：进入数据导出页面
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:10:12   
//	     */   
//	    
//	public String admissionImport() {
//		String basicdep = "";
//		try {
//			basicdep = (String)request.getSession().getAttribute("basicdep");
//		} catch (Exception e) {
//		}
//		List<DbmsDatasChemeNew> datas = dbmsNewDataImportService.getDatasChemeNewInfolist(basicdep);
//		bean.setDataslist(datas);
//		return SUCCESS;
//	}
//	 
//	    
//	 
//	    /**   
//	     * 此方法描述的是：保存上传的数据文件并且预览导入数据
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:10:23   
//	     */   
//	    
//	public String baocunandyulanshuju(){
//		String importBasePath = absolutePath.getAbsolutePath("/JSP/importxml/");//
//		String basePathfile = importBasePath + bean.getUploadFileName();
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//		
//		File aa=new File(basePathfile);
//		if(aa.exists()){
//			aa.delete();
//		}
//		try {
//			fos = new FileOutputStream(basePathfile);
//			fis = new FileInputStream(bean.getUpload());
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//			fos.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			if(null!=fos){
//				try {
//					fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if(null!=fis){
//				try {
//					fis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		String strUnZipPathString = basePathfile;
//		if(basePathfile.toLowerCase().indexOf(".zip") > -1) {
//			strUnZipPathString = basePathfile.substring(0, basePathfile.toLowerCase().indexOf(".zip")) ;
//			try {
//				DocZipUtil.unzip(basePathfile, strUnZipPathString);
//		        File dirFile = new File(strUnZipPathString);
//		        //如果dir对应的文件不存在，或者不是一个目录，则退出
//		        if (!dirFile.exists() || !dirFile.isDirectory()) {
//		            return SUCCESS;
//		        }
//		        File[] files = dirFile.listFiles();
//		        for (int i = 0; i < files.length; i++) {
//		            if (files[i].isFile()) {
//		            	basePathfile = files[i].getAbsolutePath();
//		                break;
//		            } 
//		        }
//			} catch (Exception e) {
//			}
//		}
//		//分析数据
//		File xmlfile=new File(basePathfile);
//		bean.setBasePathfile(strUnZipPathString);
//		if(xmlfile.exists()){
//			SAXReader saxReader =null;
//			List<String> titlelist=new ArrayList<String>();//列说明
//			List<String> valuelist=new ArrayList<String>();//值list
//			List<DbmsDatasChemeDetailNew> mylist=new ArrayList<DbmsDatasChemeDetailNew>();
//			List<TempConBean> conlist=new ArrayList<TempConBean>();
//			String chaxuncon="";
//		    if(null!=bean.getCondition()&&!"".equals(bean.getCondition())){
//		    	chaxuncon=bean.getCondition();
//			}
//			else if(null!=bean.getHiddencon()&&!"".equals(bean.getHiddencon())){
//				chaxuncon=bean.getHiddencon();
//			}
//		    if(null!=chaxuncon&&!"".equals(chaxuncon)){
//				 String[] conarr=chaxuncon.split(",");
//				 for(String constr:conarr){
//					 String[] tempvalue=constr.split(",");//0 coluname,1运算符 2查询值
//					 if(tempvalue.length==3){
//						 TempConBean conbean=new TempConBean();
//						 conbean.setColname(tempvalue[0]);
//						 conbean.setYunsuanfu(tempvalue[1]);
//						 conbean.setChaxunvalue(tempvalue[2]);
//						 conlist.add(conbean);
//					 }
//				 }
//			 }
//			try{
//				List<ShuJuTempTable>  shujutablelist=new ArrayList<ShuJuTempTable>();
//     			String biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' order by  a.ddtismaintable,a.ddtorder";
//     			List<Object> biaolist=baseDao.queryListObjectAll(biaosql);
//     			for(Object biaoobj:biaolist){
//     				ShuJuTempTable po=new ShuJuTempTable();
//     				DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//     				po.setThetable(thetable);
//     				//查询所有列
//     				String liesql="from DbmsDatasChemeDetailNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"'";
//     				List<Object> lielist=baseDao.queryListObjectAll(liesql);
//     				po.setLielist(lielist);
//     				shujutablelist.add(po);
//     			}
//				saxReader = new SAXReader();
//				Document document = saxReader.read(xmlfile);
//				Element root = document.getRootElement();
//				for ( Iterator iter = root.elementIterator("dctable"); iter.hasNext(); ) {
//		             Element dctableelement = (Element) iter.next();//table
//		           //遍历列信息
//		             //是否主表
//		             String zhubiao="";
//		             if(dctableelement.elementIterator("dctableddtismaintable").hasNext()){
//		            	 zhubiao=((Element)dctableelement.elementIterator("dctableddtismaintable").next()).getText();
//		             }
//		             String dctablename="";
//		             if(dctableelement.elementIterator("dctablename").hasNext()){
//		            	 dctablename=((Element)dctableelement.elementIterator("dctablename").next()).getText();
//		             }
//		             if("0".equals(zhubiao)){
//		            	 for(Iterator lieiter = dctableelement.elementIterator("liexinxi");lieiter.hasNext();){
//			            	 Element liexinxi = (Element) lieiter.next();
//			            	 DbmsDatasChemeDetailNew mylie=new DbmsDatasChemeDetailNew();
//	            			 for ( Iterator liexinxiiter = liexinxi.elementIterator(); liexinxiiter.hasNext(); ) {
//	            				 Element lie = (Element) liexinxiiter.next();
//	            				 if("liemingzi".equals(lie.getName())){
//	            					 mylie.setDcdfromcolumns(lie.getText());
//	            				 }
//	            				 if("lieshuoming".equals(lie.getName())){
//	            					 mylie.setDcdfromcolumnsscribe(lie.getText());
//	            					 titlelist.add(lie.getText()); 
//	            				 }
//	            				 if("lietype".equals(lie.getName())){
//	            					 mylie.setDcdfromcloumnstype(lie.getText());
//	            				 }
//	            			 }
//	            			 mylist.add(mylie);
//			             }
//		            	//遍历数据
//		            	 for(Iterator lieiter = dctableelement.elementIterator("dchang");lieiter.hasNext();){
//		            		 Element dchang = (Element) lieiter.next();
//		            		 for(Object obj1:mylist){
//	            				DbmsDatasChemeDetailNew c1=(DbmsDatasChemeDetailNew)obj1;
//	            				String fromliename=c1.getDcdfromcolumns();
//	            				String daovalue="";
//	            				if(null!=fromliename&&!"".equals(fromliename)){
//	            					fromliename=fromliename.toLowerCase();
//	            					if(dchang.elementIterator(fromliename).hasNext()){
//		            					daovalue=((Element)dchang.elementIterator(fromliename).next()).getText();
//		            				}
//	            					if(this.yanzhengshuju(fromliename, daovalue, conlist)){
//	            						valuelist.add(daovalue);
//	            					}
//	            				}
//	            				
//		            		}
//		            	 }
//		             }
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			int totalnum=0;
//			if(valuelist.size()>0&&titlelist.size()>0){
//				totalnum=(int)(valuelist.size()/titlelist.size());
//			}
//			//重新生成valuelist  只取前10条
//			int begin=0;
//			int end=10*titlelist.size();
//			List<String> newvalueList=new ArrayList<String>();
//			while (begin<end&&begin<valuelist.size()) {
//				newvalueList.add(valuelist.get(begin));
//				begin++;
//			}
//			bean.setTitlelist(titlelist);
//			bean.setTotalnum(totalnum);
//			bean.setLielist(mylist);
//			bean.setTitlelist(titlelist);
//			bean.setValuelist(newvalueList);
//		}
//		return SUCCESS;
//	}
//	 
//	    
//	 
//	    /**   
//	     * 此方法描述的是：导入数据
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-6-26 下午08:11:38   
//	     */   
//	    
//	public String daorushujudaorushuju(){
//		TaskBean taskBean = new TaskBean();
//		int sum = 0;
//		int count = 0;
//		if(this.session != null){
//			this.session.put("taskbean", taskBean);
//		}
//		//根据方案编号获取导入信息
//		if(null!=bean.getDdcid()&&!"".equals(bean.getDdcid())){
//			String insertOnly = "1";
//			if(null!=bean.getInsertonly()&&!"".equals(bean.getInsertonly())){
//				insertOnly=bean.getInsertonly();
//			}
//			//判断方案操作数据库类型
//			String daimasql="from DbmsCodeTypeNew a where 1=1";
//			SimpleDateFormat sdf66=new SimpleDateFormat("yyyyMMdd");
////			String suid=(String)request.getSession().getAttribute("suid");
//			String suid = "";
//			if(this.session != null)
//		    suid = (String)this.session.get("suid");
//			int fk=Integer.valueOf(sdf66.format(new Date()))*10;
//			Map<String,List<Object>> daimamap=new HashMap<String,List<Object>>();//代码替换map
//			List<Object> daimalist=baseDao.queryListObjectAll(daimasql);
//			for(Object daimaobj:daimalist){
//				DbmsCodeTypeNew daima=(DbmsCodeTypeNew)daimaobj;
//				String daimamingxisql="from DbmsCodeChemeNew a where a.dbmsCodeTypeNew.codetypeid='"+daima.getCodetypeid()+"'";
//				List<Object> daimamingxilist=baseDao.queryListObjectAll(daimamingxisql);
//				daimamap.put(daima.getCodetypeid(), daimamingxilist);
//			}
//			String tobasesql="from DbmsDatabaseNew a where a.ddid in (select todatabaseid from DbmsDatasChemeNew c where c.ddcid='"+bean.getDdcid()+"')";
//			List<Object> tobaselist=baseDao.queryListObjectAll(tobasesql);
//			DbmsDatabaseNew tobs=null;
//			String basetype = "Oracle";
//			if(null!=tobaselist&&tobaselist.size()>0){
//				tobs=(DbmsDatabaseNew)tobaselist.get(0);
//				basetype = tobs.getDatabasetype();
//			}
//			List<ShuJuTempTable>  shujutablelist=new ArrayList<ShuJuTempTable>();
//			String biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' order by  a.ddtismaintable,a.ddtorder";
//			List<Object> biaolist=baseDao.queryListObjectAll(biaosql);
//			for(Object biaoobj:biaolist){
//				ShuJuTempTable po=new ShuJuTempTable();
//				DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//				po.setThetable(thetable);
//				//查询所有列
//				String liesql="from DbmsDatasChemeDetailNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.dcdtotableid='"+thetable.getId().getDdtid()+"'";
//				List<Object> lielist=baseDao.queryListObjectAll(liesql);
//				po.setLielist(lielist);
//				shujutablelist.add(po);
//			}
//			//分包导入开始2013-12-23
//			File dirFile = new File(bean.getBasePathfile());
//			//如果dir对应的文件不存在，或者不是一个目录，则退出
//			if (!dirFile.exists()) {
//				return SUCCESS;
//			}
//			File[] files = new File[0];
//			if( dirFile.isDirectory()) {
//				files = dirFile.listFiles();
//			}
//			boolean isFirstImp =true;
//			for (int ii = 0; ii < files.length || isFirstImp; ii++) {
//				isFirstImp = false;
//				if (!dirFile.isFile() && files[ii].isFile()) {
//					bean.setBasePathfile(files[ii].getAbsolutePath());
//				} else if(dirFile.isFile()){
//				} else {
//					continue;
//				}
//			//分包导入结束2013-12-23
////			if(null!=bean.getBasePathfile()&&!"".equals(bean.getBasePathfile())){
//				File xmlfile=new File(bean.getBasePathfile());
//				if(xmlfile.exists()){
//					SAXReader saxReader =null;
//					List<TempConBean> conlist=new ArrayList<TempConBean>();
//					String chaxuncon="";
//				    if(null!=bean.getCondition()&&!"".equals(bean.getCondition())){
//				    	chaxuncon=bean.getCondition();
//					}
//					else if(null!=bean.getHiddencon()&&!"".equals(bean.getHiddencon())){
//						chaxuncon=bean.getHiddencon();
//					}
//				    if(null!=chaxuncon&&!"".equals(chaxuncon)){
//						 String[] conarr=chaxuncon.split(",");
//						 for(String constr:conarr){
//							 String[] tempvalue=constr.split(",");//0 coluname,1运算符 2查询值
//							 if(tempvalue.length==3){
//								 TempConBean conbean=new TempConBean();
//								 conbean.setColname(tempvalue[0]);
//								 conbean.setYunsuanfu(tempvalue[1]);
//								 conbean.setChaxunvalue(tempvalue[2]);
//								 conlist.add(conbean);
//							 }
//						 }
//					 }
//				    Connection conn=null;
//				    PreparedStatement prestmtPreparedStatement1=null;
//					PreparedStatement prestmtPreparedStatement2=null;
//				    try{
//						saxReader = new SAXReader();
//						Document document = saxReader.read(xmlfile);
//						Element root = document.getRootElement();
//						
//						try {
//							Element dctotal = root.element("dctotal");
//							if (!StringUtil.isNullOrEmpty(dctotal)){
//								sum = Integer.parseInt(dctotal.getTextTrim());
//							} 
//						} catch (Exception e) {								
//						}
//						taskBean.setSum(sum);
//						conn = dbmsNewDateExportSchemeManager.getConnection(tobs);
//						conn.setAutoCommit(false);  
//						for ( Iterator iter = root.elementIterator("dctable"); iter.hasNext(); ) {
//				             Element dctableelement = (Element) iter.next();
//				             String dctablename=((Element)dctableelement.elementIterator("dctablename").next()).getText();
//				             dctablename=dctablename.toUpperCase();
//			            	 DbmsDatasTableNew aatable=null;
//					         List<Object> aalist=null;
//			            	 for(ShuJuTempTable pp:shujutablelist){
//			            		 //判断导入方案的原表与导出的数据表相同
//			            		 if(dctablename.equals(pp.getThetable().getDdtjoinedfields().toUpperCase())){
//			            			 aatable=pp.getThetable();
//			            			 aalist=pp.getLielist();
//			            			 break;
//			            		 }
//			            	 }
//			            	 if(null!=aatable){
//			            		 if(null==aatable.getTablename()||"".equals(aatable.getTablename())){
//			            			 continue;
//			            		 }
//			            		 if(null!=aalist){
//				            		 for ( Iterator shujuiter = dctableelement.elementIterator("dchang"); shujuiter.hasNext(); ) {
//				            			Element dchang = (Element) shujuiter.next();
//				            			String insertaa="insert into "+aatable.getTablename()+" (";
//				            			String updateaa="update "+aatable.getTablename()+" set ";
//				            			String updatesqlString="";
//				            			String wheresqlString="";
//					            		String liearr="";
//					            		String zhiarr="";
//										int zhujianindex=1;
//					            		for(Object obj1:aalist){
//				            				DbmsDatasChemeDetailNew c1=(DbmsDatasChemeDetailNew)obj1;
//				            				if("".equals(liearr)){
//			            						liearr=c1.getDcdtocolumns().toUpperCase();
//			            					}else{
//			            						liearr=liearr+","+c1.getDcdtocolumns().toUpperCase();
//			            					}
//				            				
//				            				String daovalue="?";
//				            				//如果是主键
//				            				if("1".equals(c1.getDcdifpkey())){
//				            					if("".equals(wheresqlString)){
//				            						wheresqlString=c1.getDcdtocolumns()+" = "+daovalue+" ";
//				            					}else{
//				            						wheresqlString=wheresqlString+" and "+c1.getDcdtocolumns()+" = "+daovalue+" ";
//				            					}
//				            				}
//				            				else{
//				            					zhujianindex++;
//				            					if("".equals(updatesqlString)){
//				            						updatesqlString=c1.getDcdtocolumns()+" = "+daovalue+" ";
//				            					}else{
//				            						updatesqlString=updatesqlString+" , "+c1.getDcdtocolumns()+" = "+daovalue+" ";
//				            					}
//				            				}
//				            				if("".equals(zhiarr)){
//			            						zhiarr=daovalue;
//			            					}else{
//			            						zhiarr=zhiarr+","+daovalue+"";
//			            					}
//				            			}
//					            		insertaa=insertaa+liearr+" ) values ("+zhiarr+")";
//					            		updateaa=updateaa+updatesqlString+" where "+wheresqlString;
//					            		prestmtPreparedStatement1=conn.prepareStatement(insertaa);
//					            		prestmtPreparedStatement2=conn.prepareStatement(updateaa);
//					            		//跟新语句必须带条件,否则不能进行更新
//					            		int insertindex=1;
//					            		int updateindex=1;
//					            		///未使用
//					            		HashMap<String,ArrayList<String>> mapColumnType = new HashMap<String,ArrayList<String>>();
//					            		HashMap<String, HashMap<String, TbsecurityCode>> mapCodeScidRemarkHashMap = new HashMap<String, HashMap<String, TbsecurityCode>>();
//					        			Map<String, String> orgValuemapMap = new HashMap<String, String>();
//					        			///未使用
//					        			
//					            		for(Object obj1:aalist){
//				            				DbmsDatasChemeDetailNew c1=(DbmsDatasChemeDetailNew)obj1;
//				            				String fromliename=c1.getDcdfromcolumns();
//				            				String myvalueString="";
//				            				if(null!=fromliename&&!"".equals(fromliename)){
//				            					fromliename=fromliename.toLowerCase();
//				            					if(dchang.elementIterator(fromliename).hasNext()){
//				            						myvalueString=((Element)dchang.elementIterator(fromliename).next()).getText();
//					            				}
//				            				}
//				            				myvalueString=this.jiexiValue(suid,myvalueString, c1, fk, null, dchang, daimamap, tobs,"daoru",mapColumnType,mapCodeScidRemarkHashMap,orgValuemapMap);
//				            				//数据插入时的格式转换
//				            				myvalueString=this.geshizhuanhuan(tobs.getDatabasetype(), c1.getDcdtocolumnstype(), myvalueString);
//				            				this.addPreparedStatement(prestmtPreparedStatement1, c1.getDcdtocolumns().toLowerCase(), c1.getDcdtocolumnstype(), insertindex, myvalueString);
//											insertindex++;
//											if("1".equals(c1.getDcdifpkey())){
//												this.addPreparedStatement(prestmtPreparedStatement2, c1.getDcdtocolumns().toLowerCase(), c1.getDcdtocolumnstype(), zhujianindex, myvalueString);
//												zhujianindex++;
//											}
//				            				else{
//				            					this.addPreparedStatement(prestmtPreparedStatement2, c1.getDcdtocolumns().toLowerCase(), c1.getDcdtocolumnstype(), updateindex, myvalueString);
//				            					updateindex++;
//				            				}
//				            			}
//					            		try{
//											try {
//					            				if(GkzxCommon.ONE.equals(insertOnly)) {
//					            					//新增导入
//					            					prestmtPreparedStatement1.executeUpdate();
//					            				} else {
//					            					int result=prestmtPreparedStatement2.executeUpdate();
//													if (result==0) {
//														prestmtPreparedStatement1.executeUpdate();
//													}
//					            				}
//											} catch (Exception e) {
//												e.printStackTrace();
//											}
//					            		}catch (Exception e) {
//											// TODO: handle exception
//					            			e.printStackTrace();
//										}finally{
//											if(null!=prestmtPreparedStatement2){
//												prestmtPreparedStatement2.close();
//												prestmtPreparedStatement2 = null;
//											}
//											if(null!=prestmtPreparedStatement1){
//												prestmtPreparedStatement1.close();
//												prestmtPreparedStatement1 = null;
//											}
//										}
//										count++;
//										taskBean.setCounter(count);
//										if(this.session != null){
//											this.session.put("taskbean", taskBean);
//										}
//				            		 }
//			            		 }
//			            	 }
//				         }
//						conn.commit();
//					}catch(Exception e){
//						try {
//							conn.rollback();
//						} catch (Exception e2) {
//						}
//						e.printStackTrace();
//					}finally{
//						if(null!=conn){
//							try {
//								conn.setAutoCommit(true);
//								conn.close();
//							} catch (SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			}
//		} 
//		return SUCCESS;
//	}
//	
//	
//	 
//	    /**   
//	     * 此方法描述的是：查询数据交换列表的数据
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-7-11 上午10:00:13   
//	     */   
//	    
//	public String chaxunshujujiaohuanAction(){
//		String addcolumns = jyconfig.getProperty("addcolumns");
//		String[] addcolumnsArray = new String[]{};
//		
//		if(!StringUtil.isNullOrEmpty(addcolumns)){
//			addcolumnsArray = addcolumns.split("@"); 
//			addcolumnslst = Arrays.asList(addcolumnsArray);
//		}
//		if(null!=bean.getDdcid()&&!"".equals(bean.getDdcid())){
//			String chaxuncon="";
//		    if(null!=bean.getCondition()&&!"".equals(bean.getCondition())){
//		    	chaxuncon=bean.getCondition();
//			}
//			else if(null!=bean.getHiddencon()&&!"".equals(bean.getHiddencon())){
//				chaxuncon=bean.getHiddencon();
//			}
//		    List<DbmsDatasChemeDetailNew> mylist=new ArrayList<DbmsDatasChemeDetailNew>();
//			List<String> titlelist=new ArrayList<String>();
//			List<String> valuelist=new ArrayList<String>();
//			int totalnum=0;
//			//判断方案操作数据库类型
//			String basesql="from DbmsDatabaseNew a where a.ddid in (select fromdatabaseid from DbmsDatasChemeNew c where c.ddcid='"+bean.getDdcid()+"')";
//			List<Object> baselist=baseDao.queryListObjectAll(basesql);
//			String basetype="";
//			DbmsDatabaseNew bs=null;
//			if(null!=baselist&&baselist.size()>0){
//				bs=(DbmsDatabaseNew)baselist.get(0);
//				basetype=bs.getDatabasetype();
//			}
//			try{
//				String chaxunsql="";
////				String biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtismaintable='0'";
//				String biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.id.ddtid like '%EXP' order by  a.ddtismaintable";
//				List<Object> biaolist=baseDao.queryListObjectAll(biaosql);
//				if(null!=biaolist&&biaolist.size()>0){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaolist.get(0);
//					String liesql="from DbmsDatasChemeDetailNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"'";
//					List<Object> lielist=baseDao.queryListObjectAll(liesql);
//					String strColumn = "";
//					for(Object obj:lielist){
//						DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//						titlelist.add(detail.getDcdfromcolumnsscribe());
//						strColumn = detail.getDcdfromcolumns();
//						if (strColumn != null && !"".equals(strColumn)){
//							if (strColumn.indexOf("@") > -1){
//								String[] strTmpColumnStrings = strColumn.split("@");
//								strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1];
//							} else {
//								mylist.add(detail);
//							}
//							//常量的字段名
//							if (addcolumnslst.indexOf(strColumn) > -1){
//								if("".equals(chaxunsql)){
//									chaxunsql="'" + strColumn + "' as " + strColumn;
//								}else{
//									chaxunsql=chaxunsql+","+ "'" + strColumn + "' as " + strColumn;
//								}
//							} else {
//								if("".equals(chaxunsql)){
//									chaxunsql=strColumn;
//								}else{
//									chaxunsql=chaxunsql+","+strColumn;
//								}
//							}
//						}
//					}
//					String countString="select count(*) as aa from "+thetable.getTablename()+" a where 1=1 ";
//					if("Microsoft SQL Server".equals(bs.getDatabasetype())){
//						chaxunsql=" top 100 "+chaxunsql;
//					}
//					if("Microsoft SQL Server 2005".equals(bs.getDatabasetype())){
//						//chaxunsql="  "+chaxunsql;
//						chaxunsql=" top 100 "+chaxunsql;
//					}
//					chaxunsql="select "+chaxunsql+" from "+thetable.getTablename()+" a where 1=1 ";
//					//额外查询条件追加开始 2014-1-20
//					String addconditionString = thetable.getAddcondition();
//					if (!StringUtil.isNullOrEmpty(addconditionString)) {
//						countString=countString+" and "+addconditionString;
//						chaxunsql=chaxunsql+" and " + addconditionString;
//					}
//					String strddctype = getDdctypedByDdcid(bean.getDdcid());
//					if (strddctype.equals(GkzxCommon.DBMS_IMEXPORT_JD)){
//						Connection localconn = null;
//						try {
//							localconn = JDBCUtils.getConn();
//							HashMap<String, Map> ddtidUpdTimeMap = judishujujiaoUpdTime(localconn, bean.getDdcid());
//							if (ddtidUpdTimeMap.containsKey(thetable.getId().getDdtid())){
//								Map ddcexptimeMap = ddtidUpdTimeMap.get(thetable.getId().getDdtid());
//								Date ddcexptime = (Date)ddcexptimeMap.get("ddcexptime");
//								//取一天前的数据，防止有遗漏
//								ddcexptime  =  CalendarUtil.getSpecifiedDayBefore(ddcexptime);
//								SimpleDateFormat sdf=new SimpleDateFormat(GkzxCommon.DATETIMEFORMATNOSPLIT2);
//								chaxunsql=chaxunsql+"  and to_char(a.updatetime,'yyyyMMddHH24mmss') > '" + sdf.format(ddcexptime) + "'";
//								countString=countString+"  and to_char(a.updatetime,'yyyyMMddHH24mmss') > '" + sdf.format(ddcexptime) + "'";
//							}
//						} catch (Exception e) {
//						} finally {
//							JDBCUtils.closeResource(localconn, null, null);
//						}
//					}
//					//额外查询条件追加结束 2013-1-20
//					if(null!=chaxuncon&&!"".equals(chaxuncon)){
//						countString=countString+" and "+chaxuncon;
//						chaxunsql=chaxunsql+" and "+chaxuncon;
//					}
//					if("Oracle".equals(bs.getDatabasetype())){
//						chaxunsql=chaxunsql+" and rownum<10";
//					}
//					ResultSet rs=null;
//					Connection conn=null;
//					Statement stmt=null;
//					try {
//						conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//						stmt = conn.createStatement(); 
//						rs = stmt.executeQuery(chaxunsql); 
//						while (rs.next()) {
//							for(Object obj:lielist){
//								DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//								strColumn = detail.getDcdfromcolumns();
//								if (strColumn != null && !"".equals(strColumn)){
//									if (strColumn.indexOf("@") > -1){
//										String[] strTmpColumnStrings = strColumn.split("@");
//										strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1];
//									}
//								}
//								String ttvalue=this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(), strColumn, rs);
//								if(null==ttvalue){
//									ttvalue="";
//								}
//								valuelist.add(ttvalue);
//							}
//						}
//						rs.close();
//						rs=stmt.executeQuery(countString);
//						while (rs.next()) {
//							totalnum=rs.getInt("aa");  
//						}
//						rs.close();
//						stmt.close();
//					}catch (Exception e) {
//						e.printStackTrace();
//						// TODO: handle exception
//					}finally{
//						if(null!=rs){
//							try {
//								rs.close();
//							} catch (SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						if(null!=stmt){
//							try {
//								stmt.close();
//							} catch (SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						if(null!=conn){
//							try {
//								conn.close();
//							} catch (SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			bean.setLielist(mylist);
//			bean.setTotalnum(totalnum);
//			bean.setTitlelist(titlelist);
//			bean.setValuelist(valuelist);
//		}
//		return SUCCESS;
//	}
//	 
//	    /**   
//	     * 此方法描述的是：进行数据交换
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-7-11 上午09:59:33   
//	     */   
//	    
//	public String jianyushujujiaohuanAction(){
//		TaskBean taskbean = new TaskBean();
//		boolean hasXinqiBiandong = false;
//		boolean hasTonganfan = false;
//		boolean caipanxinxi = false;
//		String suid= GkzxCommon.USER_ADMIN;
//		String interchange = "500";
//		String strInterchange = jyconfig.getProperty("interchange");
//		String addcolumns = jyconfig.getProperty("addcolumns");
//		String otherxfzb = jyconfig.getProperty("otherxfzb");
//		String[] addcolumnsArray = new String[]{};
//
//		//判断监管系统的计分考核交换 
//		boolean bolJfkh = false;
//		//判断有查询条件
//		boolean bolHasCond = false;
//		//罪犯编号存放list
//		ArrayList<String> lstBh = new ArrayList<String>();
//		//方案的部门id
//		String strSdid = "";
//		if(!StringUtil.isNullOrEmpty(addcolumns)){
//			addcolumnsArray = addcolumns.split("@"); 
//			addcolumnslst = Arrays.asList(addcolumnsArray);
//		}
//		if (!StringUtil.isNullOrEmpty(strInterchange)){
//			interchange = strInterchange;
//		}
////		if(request!=null){
////		HttpSession httpsession = (HttpSession)ActionContext.getContext().getSession();
////
////			if(httpsession!=null){
////				httpsession.setAttribute("taskbean", taskbean);
////				Object objSuid=httpsession.getAttribute("suid");
////				if (objSuid != null) {
////					suid=objSuid.toString();
////				}
////			}else{
//				if(this.session!=null){
//					this.session.put("taskbean", taskbean);
//					Object objSuid=this.session.get("suid");
//					if (objSuid != null) {
//						suid=objSuid.toString();
//					}
//				}
////			}
////		}
//		if(null!=bean.getDdcid()&&!"".equals(bean.getDdcid())){
//		    Connection daoruconn=null;
//		    Connection localconn=null;
//		    Connection conn=null;
//			PreparedStatement localps = null;
//			ResultSet localrs = null;
//		    try{
//		    	localconn = JDBCUtils.getConn();
//		    	String insertOnly = "1";
//		    	if(null!=bean.getInsertonly()&&!"".equals(bean.getInsertonly())){
//		    		insertOnly=bean.getInsertonly();
//		    	}
//		    	SimpleDateFormat sdf66=new SimpleDateFormat("yyyyMMdd");
//		    	int fk=Integer.valueOf(sdf66.format(new Date()))*10;
//		    	Map<String,List<Object>> daimamap=new HashMap<String,List<Object>>();//代码替换map
////		    	List<PreparedStatement> insertlist=new ArrayList<PreparedStatement>();//
////		    	List<PreparedStatement> updateList =new ArrayList<PreparedStatement>();
////		    	
////		    	String tobaseString="from DbmsDatabaseNew a where a.ddid in (select todatabaseid from DbmsDatasChemeNew c where c.ddcid='"+bean.getDdcid()+"')";
////		    	List<Object> baselist33=baseDao.queryListObjectAll(tobaseString);
//		    	String tobaseString="select * from DBMS_DATABASE_NEW a where a.ddid in (select todatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"+bean.getDdcid()+"')";
////		    	List<Object> baselist33=baseDao.queryListObjectAll(tobaseString);
//		    	
//		    	localps = localconn.prepareStatement(tobaseString);
//		    	localrs = localps.executeQuery();
//				ArrayList dataAll = jsonTool.ResultSetToList(localrs);
//				
//		    	DbmsDatabaseNew tobsseDatabaseNew=null;
//		    	if(dataAll.size()>0) {
//					HashMap map =(HashMap)dataAll.get(0);
//		    		tobsseDatabaseNew= getDatabaseNew(map);
//		    	}
//		    	JDBCUtils.closeResource(null, localps, localrs);
//		    	
////		    	String daimasql="from DbmsCodeTypeNew a where 1=1";
////		    	List<Object> daimalist=baseDao.queryListObjectAll(daimasql);
//		    	String daimasql="select * from DBMS_CODE_TYPE_NEW a where 1=1";
//		    	localps = localconn.prepareStatement(daimasql);
//		    	localrs = localps.executeQuery();
//				dataAll = jsonTool.ResultSetToList(localrs);
//				JDBCUtils.closeResource(null, localps, localrs);
//				PreparedStatement localps2 = null;
//				ResultSet localrs2 = null;
////				for(Object daimaobj:daimalist){
////					DbmsCodeTypeNew daima=(DbmsCodeTypeNew)daimaobj;
////					String daimamingxisql="from DbmsCodeChemeNew a where a.dbmsCodeTypeNew.codetypeid='"+daima.getCodetypeid()+"'";
////					List<Object> daimamingxilist=baseDao.queryListObjectAll(daimamingxisql);
////					daimamap.put(daima.getCodetypeid(), daimamingxilist);
////				}
//				for(int i = 0; i < dataAll.size(); i++) {
//					HashMap map =(HashMap)dataAll.get(i);
////					DbmsCodeTypeNew daima = getCodeTypeNew(map);
//		    		String daimamingxisql="select * from DBMS_CODE_CHEME_NEW a where a.codetype='"+getStringByMap(map,"codetypeid")+"'";
//		    		
//		    		localps2 = localconn.prepareStatement(daimamingxisql);
//			    	localrs2 = localps2.executeQuery();
//			    	List<Object> daimamingxilist = getCodeChemeNew(jsonTool.ResultSetToList(localrs2));
//			    	JDBCUtils.closeResource(null, localps2, localrs2);
//		    		daimamap.put(getStringByMap(map,"codetypeid"), daimamingxilist);
//				}
//		    	String chaxuncon="";
//		    	if(null!=bean.getCondition()&&!"".equals(bean.getCondition())){
//		    		chaxuncon=bean.getCondition();
//		    		bolHasCond = true;
//		    	}
//		    	else if(null!=bean.getHiddencon()&&!"".equals(bean.getHiddencon())){
//		    		chaxuncon=bean.getHiddencon();
//		    		bolHasCond = true;
//		    	}
//		    	
//		    	daoruconn = dbmsNewDateExportSchemeManager.getConnection(tobsseDatabaseNew);
////				daoruconn.setAutoCommit(false);  
//				//判断方案操作数据库类型
////				String basesql="from DbmsDatabaseNew a where a.ddid in (select fromdatabaseid from DbmsDatasChemeNew c where c.ddcid='"+bean.getDdcid()+"')";
////				List<Object> baselist=baseDao.queryListObjectAll(basesql);
//				String basesql="select * from DBMS_DATABASE_NEW a where a.ddid in (select fromdatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"+bean.getDdcid()+"')";
////				List<Object> baselist=baseDao.queryListObjectAll(basesql);
//				localps = localconn.prepareStatement(basesql);
//		    	localrs = localps.executeQuery();
//				dataAll = jsonTool.ResultSetToList(localrs);
//		    	
//				String basetype="";
//				DbmsDatabaseNew bs=null;
////				if(null!=baselist&&baselist.size()>0){
////					bs=(DbmsDatabaseNew)baselist.get(0);
////					basetype=bs.getDatabasetype();
////				}
//				
//		    	if(dataAll.size()>0) {
//					HashMap map =(HashMap)dataAll.get(0);
//		    		bs= getDatabaseNew(map);
//		    		basetype=bs.getDatabasetype();
//		    		strSdid = bs.getSdid(); 
//		    	}
//		    	JDBCUtils.closeResource(null, localps, localrs);
//				
//				bean.setBasetype(basetype);
//				
//				HashMap<String, Integer> mapTableRecordCnt = new HashMap<String, Integer>();
//				String maintablesql="";
//				String maincon="";
////				String biaosql="from DbmsDatasTableNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.id.ddtid like '%EXP' order by  a.ddtismaintable";
////				List<Object> biaolist=baseDao.queryListObjectAll(biaosql);
//				String biaosql="select * from DBMS_DATAS_TABLE_NEW a where a.ddcid='"+bean.getDdcid()+"' and a.ddtid like '%EXP' order by  a.ddtismaintable,ddtorder";
//				localps = localconn.prepareStatement(biaosql);
//		    	localrs = localps.executeQuery();
//				dataAll = jsonTool.ResultSetToList(localrs);
//				List<Object> biaolist=getDatasTableNew(dataAll);
//				JDBCUtils.closeResource(null, localps, localrs);
//				//进度条用
//				int sum = 0;
//				int tem = 0;
//				
//				conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//				
//				//计算条数开始
//				
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					if(GkzxCommon.TONGANFAN_TABLE.equalsIgnoreCase(thetable.getTotable())) {
//						hasTonganfan = true;
//						continue;
//					}
//					if (GkzxCommon.XINGQIBIANDONG_TABLE.equalsIgnoreCase(thetable.getTotable())) {
//						hasXinqiBiandong = true;
//					}
//					
//					if (GkzxCommon.XFZB.equalsIgnoreCase(thetable.getTablename()) ||
//						otherxfzb.equalsIgnoreCase(thetable.getTablename())) {
//						caipanxinxi=true;
//					}
//					if (GkzxCommon.TBCRIMINAL_JGXT_KHJF.equalsIgnoreCase(thetable.getTotable())||
//						GkzxCommon.TBCRIMINAL_JGXT_YZJC.equalsIgnoreCase(thetable.getTotable())) {
//						//计分考核设为true
//						bolJfkh=true;
//					}
//					//查询所有列
//					String chaxunsql=" count(*) ";
//					String daochusql="";
//					if("0".equals(thetable.getDdtismaintable())){
//						maincon=maincon+" a where 1=1";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							maincon=maincon+"  and a." + addconditionString;
//						}
//						//额外查询条件追加结束2014-1-20
//						if(!"".equals(chaxuncon)){
//							maincon=maincon+" and "+chaxuncon;
//						}
//						maintablesql="select "+chaxunsql+" from "+thetable.getTablename()+maincon;
//						daochusql=maintablesql;
//					}else{
//						daochusql="select "+chaxunsql+" from "+thetable.getTablename()+" b where 1=1 ";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							daochusql=daochusql+" and b." + addconditionString;
//						}
//						//额外查询条件追加结束 2014-1-20
//						
//						//其他表根据数据关系生成查询条件
//						if(null!=thetable.getShujuguanxi()&&!"".equals(thetable.getShujuguanxi())){
//							String[]  temparr=thetable.getShujuguanxi().split(",");
//							String tempcon="";
//							for(String str:temparr){
//								String[] temp2=str.split("=");
//								if("".equals(tempcon)){
//									tempcon="a."+temp2[0]+"=b."+temp2[1];
//								}else{
//									tempcon=" and a."+temp2[0]+"=b."+temp2[1];
//								}
//							}
//							
//							if(!"".equals(tempcon)){
//								int indexFrom = maintablesql.indexOf("from");
//								String strReplaceMaintablesql = "select 'X' " + maintablesql.substring(indexFrom);
//								daochusql=daochusql+" and exists ("+strReplaceMaintablesql+" and "+tempcon+" )";
//							}
//						}
//					}
//					if (!StringUtil.isNullOrEmpty(daochusql)) {
//						int intTableRecordCnt = this.getSumCount(conn, daochusql);
//						sum += intTableRecordCnt;
//						mapTableRecordCnt.put(thetable.getTablename(), Integer.valueOf(intTableRecordCnt));
//					}
//				}				
//				
//				//计算条数结束
//				
//				taskbean.setSum(sum);
////				if(request!=null){
////					if(httpsession!=null){
////						httpsession.setAttribute("taskbean", taskbean);
////					}else{
//						if(this.session!=null){
//							this.session.put("taskbean", taskbean);
//						}
////					}
////				}
//				
//				maintablesql="";
//				maincon="";
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					//查询所有列
//					String chaxunsql="";
//					String daochusql="";
////					String liesql="from DbmsDatasChemeDetailNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"'";
////					List<Object> lielist=baseDao.queryListObjectAll(liesql);
//					String liesql="select * from DBMS_DATAS_CHEME_DETAIL_NEW a where a.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"' order by a.dcdifpkey asc ";
//					localps = localconn.prepareStatement(liesql);
//			    	localrs = localps.executeQuery();
//					dataAll = jsonTool.ResultSetToList(localrs);
//					List<Object> lielist=getDatasChemeDetailNew(dataAll);
//					JDBCUtils.closeResource(null, localps, localrs);
//					
//					boolean isFirst = true;
//					String strAsc = "";
//					String strDesc = "";
//					String strColumn = "";
//					ArrayList<String> lstColumnsArrayList = new ArrayList<String>();
//					String strorgBianhao = "";
//					//GB002:410600:河南省鹤壁市
//					HashMap<String, HashMap<String, TbsecurityCode>> mapCodeScidRemarkHashMap = new HashMap<String, HashMap<String, TbsecurityCode>>();
//					ArrayList<String> strCodeList = new ArrayList<String>();
//					//原字段名->代码code:关联字段名：单位类型；如：dbmx->GB002:dbbh:100000016
//					HashMap<String,ArrayList<String>> mapColumnType = new HashMap<String,ArrayList<String>>();
//					for(Object obj:lielist){
//						DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
////						if(null!=detail.getDcdfromcolumns()&&!"".equals(detail.getDcdfromcolumns())){
////							if("".equals(chaxunsql)){
////								chaxunsql=detail.getDcdfromcolumns();
////							}else{
////								chaxunsql=chaxunsql+","+detail.getDcdfromcolumns();
////							}
////						}
//
//						strColumn = detail.getDcdfromcolumns();
//						if (strColumn != null && !"".equals(strColumn)){
//							if (strColumn.indexOf("@") > -1){
//								//该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
//								String[] strTmpColumnStrings = strColumn.split("@");
//								strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//								if (strTmpColumnStrings.length == 3){
//									ArrayList<String> lstTypeColumnDanweiArrayList = new ArrayList<String>();
//									lstTypeColumnDanweiArrayList.add(strTmpColumnStrings[1].trim());
//									strCodeList.add(strTmpColumnStrings[1].trim());
//									strorgBianhao = strTmpColumnStrings[0];
//									if (strorgBianhao.indexOf(":") > 0) {
//										//1000001:bh
//										String[] strDanweiFromColumn = strorgBianhao.split(":");
//										strorgBianhao = strDanweiFromColumn[1];
//										lstTypeColumnDanweiArrayList.add(strorgBianhao);
//										lstTypeColumnDanweiArrayList.add(strDanweiFromColumn[0]);
//									} else {
//										//bh
//										lstTypeColumnDanweiArrayList.add(strorgBianhao);
//									}
//									//如：dbmx->GB002:dbbh:100000016或者//如：dbmx->GB002:dbbh
//									mapColumnType.put(strColumn, lstTypeColumnDanweiArrayList);
//								}
//							}
//							
//							if (lstColumnsArrayList.indexOf(strColumn) < 0){
//								lstColumnsArrayList.add(strColumn);
//								//常量的字段名
//								if (addcolumnslst.indexOf(strColumn) > -1){
//									if("".equals(chaxunsql)){
//										chaxunsql="'" + strColumn + "' as " + strColumn;
//									}else{
//										chaxunsql=chaxunsql+","+ "'" + strColumn + "' as " + strColumn;
//									}
//								} else {
//									if("".equals(chaxunsql)){
//										chaxunsql=strColumn;
//									}else{
//										chaxunsql=chaxunsql+","+strColumn;
//									}
//								}
//							
//								//如果是主键
//								if("1".equals(detail.getDcdifpkey())){
//									int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
//									if (intIndex > -1) {
//										strColumn = strColumn.toLowerCase().substring(0, intIndex);
//									}
//									if (isFirst) {
//										strAsc = strColumn + " ASC";
//										strDesc = strColumn + " DESC";
//										isFirst = false;
//									} else {
//										strAsc = strAsc + ", " + strColumn + " ASC";
//										strDesc = strDesc + ", " + strColumn + " DESC";
//									}
//								}
//							}
//						}
//					}
//					mapCodeScidRemarkHashMap= getLocalDbCodeScidRemark(localconn, strCodeList);
//					if("0".equals(thetable.getDdtismaintable())){
//						maincon=maincon+" a where 1=1";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							maincon=maincon+"  and a." + addconditionString;
//						}
//						//额外查询条件追加结束2014-1-20
//						if(GkzxCommon.HNFANGANPCID.equals(bean.getDdcid())){
//							chaxuncon = " a.bh in(select bh from  da_jbxx where zybz!='3') and a.bdlb='01' ";
//						}
////						else{
////							chaxuncon = " a.bh in(select bh from  da_jbxx where zybz!='3') ";
////						}
//						if(!"".equals(chaxuncon)){
//							maincon=maincon+" and "+chaxuncon;
//						}
////						maintablesql="select "+chaxunsql+" from "+thetable.getTablename()+maincon;
//						maintablesql=chaxunsql+" from "+thetable.getTablename()+maincon;
//						daochusql=maintablesql;
//					}else{
//						daochusql=chaxunsql+" from "+thetable.getTablename()+" b where 1=1 ";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							daochusql=daochusql+" and b." + addconditionString;
//						}
//						//额外查询条件追加结束 2014-1-20
//						//其他表根据数据关系生成查询条件
//						if(null!=thetable.getShujuguanxi()&&!"".equals(thetable.getShujuguanxi())){
//							String[]  temparr=thetable.getShujuguanxi().split(",");
//							String tempcon="";
//							for(String str:temparr){
//								String[] temp2=str.split("=");
//								if("".equals(tempcon)){
//									tempcon="a."+temp2[0]+"=b."+temp2[1];
//								}else{
//									tempcon=" and a."+temp2[0]+"=b."+temp2[1];
//								}
//							}
////							daochusql="select "+chaxunsql+" from "+thetable.getTablename()+" b where 1=1 ";
//							
//							if(!"".equals(tempcon)){
//								daochusql=daochusql+" and exists ( select "+maintablesql+" and "+tempcon+" )";
//							}
//						}
//					}
//					if(!"".equals(daochusql)){
//						ResultSet rs=null;
////						Connection conn=null;
//						Statement stmt=null;
//						PreparedStatement prestmtPreparedStatement1=null;
//						PreparedStatement prestmtPreparedStatement2=null;
//						String strFromTable = thetable.getTablename();
//						int recordCnt = 0;
//						if (mapTableRecordCnt.get(strFromTable)!=null){
//							recordCnt = mapTableRecordCnt.get(strFromTable).intValue();
//						}
//						for(int i = 0; i < recordCnt;) {
//							
//							try {
//	//							conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//								stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
//								String onedaochusql = "select top " + interchange + " * from (select top " + String.valueOf(i+Integer.parseInt(interchange)) + " " + daochusql + 
//													  " order by " + strAsc + " ) x order by " + strDesc;
//								if(basetype.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)){
//									onedaochusql = "select * from (select rownum no, " + daochusql + ") where no > " + String.valueOf(i) + " and no <= " + String.valueOf(i+Integer.parseInt(interchange));
//								}
//								
//								//查询数据是否存在开始///////////////////////////////////////////////////////
////								ArrayList<HashMap<String, Object>> newkeyValueArrayList = new ArrayList<HashMap<String, Object>>();
//								ArrayList<String> keyValueArrayList = new ArrayList<String>();
//								rs = stmt.executeQuery(onedaochusql); 
//								if(GkzxCommon.ONE.equals(insertOnly)) {
//									String strSelectColumn = "";
//									String strSelectWhere = "";
//									boolean isFirstSelect = true;
////									ArrayList<String> columnList = new ArrayList<String>();
//									while (rs.next()) {
//										//遍历获取数据
//										int zhujianindex=1;
//										HashMap<String, Object> pKeyvaluemapMap=new HashMap<String, Object>();
//										//原有column对应值
////										Map<String, String> orgValuemapMap=new HashMap<String, String>();
//										String wheresqlString="";
//										for(Object obj:lielist){
//											//开始进行数据交换
//											DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//											//如果是主键
//				            				if("1".equals(detail.getDcdifpkey())){
//				            					strColumn = detail.getDcdfromcolumns();
//				            					if (strColumn != null && !"".equals(strColumn)){
//				            						if (strColumn.indexOf("@") > -1){
//				            							//该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
//				            							String[] strTmpColumnStrings = strColumn.split("@");
//				            							strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//				            						}
//				            					}
//				            					String ttvalue=this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(), strColumn, rs);
//				            					
//				            					if (ttvalue !=null && !"".equals(ttvalue)){
//				            						ttvalue = ttvalue.trim();
//				            					}
//				            					if(null==ttvalue){
//				            						ttvalue="";
//				            					}
//				            					if (!StringUtil.isNullOrEmpty(ttvalue)) {
//				            						pKeyvaluemapMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//				            					} else if(!StringUtil.isNullOrEmpty(detail.getDcdtocloumnsdefaultvalue())){
//				            						pKeyvaluemapMap.put(detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocloumnsdefaultvalue());
//				            					} else {
//				            						pKeyvaluemapMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//				            					}
////				            					columnList.add(detail.getDcdtocolumns().toLowerCase());
////				            					if(!StringUtil.isNullOrEmpty(strColumn)) {
////				            						orgValuemapMap.put(strColumn.toLowerCase(), ttvalue);
////				            					}
//				            					//循环第一次的时候，获得查询列。
//				            					if (isFirstSelect) {
//				            						if("".equals(strSelectColumn)){
//				            							
//				            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//					            								strSelectColumn = detail.getDcdtocolumns() + " ";
//					            							} else {
//					            								strSelectColumn = detail.getDcdtocolumns() + " ";
//					            							}
//					            						}
//					            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("DATE".equals(detail.getDcdtocolumnstype())){
//					            								strSelectColumn= " to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') as "+ detail.getDcdtocolumns() + " ";
//					            							}else {
//					            								strSelectColumn = detail.getDcdtocolumns() + " ";
//					            							}
//					            						}
//				            						}else{
//				            							
//				            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//					            								strSelectColumn = strSelectColumn + ", " + detail.getDcdtocolumns();
//					            							} else {
//					            								strSelectColumn = strSelectColumn + ", " + detail.getDcdtocolumns();
//					            							}
//					            						}
//					            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("DATE".equals(detail.getDcdtocolumnstype())){
//					            								strSelectColumn=strSelectColumn + ", " + " to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') as "+ detail.getDcdtocolumns() + " ";
//					            							}else {
//					            								strSelectColumn = strSelectColumn + ", " + detail.getDcdtocolumns();
//					            							}
//					            						}
//				            						}
//				            					}
//				            					if("".equals(wheresqlString)){
//			            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())){
//			            								if (!StringUtil.isNullOrEmpty(ttvalue)) {
//					            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//					            								wheresqlString= detail.getDcdtocolumns()+" = "+ttvalue+" ";
//					            							} else {
//					            								wheresqlString= detail.getDcdtocolumns()+" = "+ttvalue+" ";
//					            							}
//			            								} else if(!StringUtil.isNullOrEmpty(detail.getDcdtocloumnsdefaultvalue())){
//			            									if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//					            								wheresqlString= detail.getDcdtocolumns()+" = "+detail.getDcdtocloumnsdefaultvalue()+" ";
//					            							} else {
//					            								wheresqlString= detail.getDcdtocolumns()+" = "+detail.getDcdtocloumnsdefaultvalue()+" ";
//					            							}
//			            								}
//				            						}
//				            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())){
//				            							if (!StringUtil.isNullOrEmpty(ttvalue)) {
//					            							if("DATE".equals(detail.getDcdtocolumnstype())){
//					            								//变更2014-1-20
//					            								wheresqlString= " to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') = '"+ttvalue+"' ";
//					            							}else {
//					            								//变更2014-1-20
//					            								wheresqlString= detail.getDcdtocolumns()+" = '"+ttvalue+"' ";
//					            							}
//				            							} else if(!StringUtil.isNullOrEmpty(detail.getDcdtocloumnsdefaultvalue())){
//				            								if("DATE".equals(detail.getDcdtocolumnstype())){
//				            									//变更2014-1-20
//				            									wheresqlString= " to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') = '"+detail.getDcdtocloumnsdefaultvalue()+"' ";
//				            								}else {
//				            									//变更2014-1-20
//				            									wheresqlString= detail.getDcdtocolumns()+" = '"+detail.getDcdtocloumnsdefaultvalue()+"' ";
//				            								}
//				            								
//				            							}
//				            						}
//				            					}else{
//			            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())){
//			            								if (!StringUtil.isNullOrEmpty(ttvalue)) {
//			            									if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//			            										wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = "+ttvalue+" ";
//			            									} else {
//			            										wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = "+ttvalue+" ";
//			            									}
//			            								} else if(!StringUtil.isNullOrEmpty(detail.getDcdtocloumnsdefaultvalue())){
//			            									if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//			            										wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = "+detail.getDcdtocloumnsdefaultvalue()+" ";
//			            									} else {
//			            										wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = "+detail.getDcdtocloumnsdefaultvalue()+" ";
//			            									}
//			            									
//			            								}
//				            						}
//				            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())){
//				            							if (!StringUtil.isNullOrEmpty(ttvalue)) {
//					            							if("DATE".equals(detail.getDcdtocolumnstype())){
//					            								//变更2014-1-20
//					            								wheresqlString=wheresqlString+" and "+" to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') = '"+ttvalue+"' ";
//					            							} else {
//																wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = '"+ttvalue+"' ";
//					            							} 
//				            							} else if(!StringUtil.isNullOrEmpty(detail.getDcdtocloumnsdefaultvalue())){
//				            								if("DATE".equals(detail.getDcdtocolumnstype())){
//				            									//变更2014-1-20
//				            									wheresqlString=wheresqlString+" and "+" to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') = '"+detail.getDcdtocloumnsdefaultvalue()+"' ";
//				            								} else {
//				            									wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = '"+detail.getDcdtocloumnsdefaultvalue()+"' ";
//				            								} 
//				            								
//				            							}
//				            						}
//				            					}
//				            				}
//										}
////										Object[] key = valuemapMap.keySet().toArray(); 
////										Arrays.sort(key); 
//										List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
//												pKeyvaluemapMap.entrySet());
//
//										// 排序
//										Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//											public int compare(Map.Entry<String, Object> o1,
//													Map.Entry<String, Object> o2) {
//												return (o1.getKey()).toString().compareTo(o2.getKey());
//											}
//										});
//										
//										String strKeyValueString = "";
//										for (int t = 0; t < infoIds.size(); t++) {
//											Entry<String,Object> ent=infoIds.get(t);
//											strKeyValueString += ent.getValue();
//											
//										}
//										if (keyValueArrayList.indexOf(strKeyValueString) < 0) {
//											keyValueArrayList.add(strKeyValueString);
//										}
//										isFirstSelect = false;
//										//构造查询条件
//										if("".equals(strSelectWhere)){
//											if (!StringUtil.isNullOrEmpty(wheresqlString)) {
//												strSelectWhere=" ( " + wheresqlString + ")";
//											}
//		            					}else{
//		            						if (!StringUtil.isNullOrEmpty(wheresqlString)) {
//		            							strSelectWhere=strSelectWhere+" or "+ " ( " + wheresqlString + ")";
//		            						}
//		            					}
//										
//									}
////									JDBCUtils.closeResource(null, stmt, rs);
//									String sql="select " + strSelectColumn + " from " + thetable.getTotable() ;
//									if (!StringUtil.isNullOrEmpty(strSelectWhere)) {
//										sql += " where " + strSelectWhere;
//									}
//									try {
//										localps = daoruconn.prepareStatement(sql);
//										localrs = localps.executeQuery();
//										List<Object> objList = jsonTool.ResultSetToList(localrs);
//										JDBCUtils.closeResource(null, localps, localrs);
//
//										for (int m = 0; m < objList.size(); m++) {
//											HashMap map = (HashMap)objList.get(m);
////											Object[] key = map.keySet().toArray(); 
////											Arrays.sort(key); 
//											List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
//													map.entrySet());
//
//											// 排序
//											Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//												public int compare(Map.Entry<String, Object> o1,
//														Map.Entry<String, Object> o2) {
//													return (o1.getKey()).toString().compareTo(o2.getKey());
//												}
//											});
//											String strKeyValueString = "";
//											for (int t = 0; t < infoIds.size(); t++) {
//												Entry<String,Object> ent=infoIds.get(t);
//												strKeyValueString += ent.getValue();
//												
//											}
//											int n = keyValueArrayList.indexOf(strKeyValueString);
//											if (n > -1) {
//												keyValueArrayList.remove(n);
//											}
//										}
//									
//									} catch (Exception e) {
//										e.printStackTrace();
//									} finally {
//										JDBCUtils.closeResource(null, localps, localrs);
//									}
//								}
//								//查询数据是否存在结束///////////////////////////////////////////////////////
////								stmt = conn.createStatement(); 
////								rs = stmt.executeQuery(onedaochusql); 
//								rs.beforeFirst();
//								while (rs.next()) {
//									tem++;
//									taskbean.setCounter(tem);
//									if(this.session!=null){
//										this.session.put("taskbean", taskbean);
//									}
//									//遍历获取数据
//									int zhujianindex=1;
//									Map<String, String> valuemapMap=new HashMap<String, String>();
//									Map<String, Object> keyValueMap=new HashMap<String, Object>();
//									//原有column对应值
//									Map<String, String> orgValuemapMap=new HashMap<String, String>();
//									for(Object obj:lielist){
//										//开始进行数据交换
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										strColumn = detail.getDcdfromcolumns();
//										if (strColumn != null && !"".equals(strColumn)){
//											if (strColumn.indexOf("@") > -1){
//												//该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
//												String[] strTmpColumnStrings = strColumn.split("@");
//												strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//											}
//										}
//										String ttvalue=this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(), strColumn, rs);
//										
//										if (ttvalue !=null && !"".equals(ttvalue)){
//											ttvalue = ttvalue.trim();
//										}
//										if(null==ttvalue){
//											ttvalue="";
//										}
//										valuemapMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//										if("1".equals(detail.getDcdifpkey())){
//											keyValueMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//											if (!StringUtil.isNullOrEmpty(ttvalue)) {
//												keyValueMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//			            					} else if(!StringUtil.isNullOrEmpty(detail.getDcdtocloumnsdefaultvalue())){
//			            						keyValueMap.put(detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocloumnsdefaultvalue());
//			            					} else {
//			            						keyValueMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//			            					}
//										}
//										if(!StringUtil.isNullOrEmpty(strColumn)) {
//											orgValuemapMap.put(strColumn.toLowerCase(), ttvalue);
//										}
//									}
//									//新增的时候才进行判断
//									if(GkzxCommon.ONE.equals(insertOnly)) {
////										Object[] key = keyValueMap.keySet().toArray(); 
////										Arrays.sort(key); 
//										List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
//												keyValueMap.entrySet());
//
//										// 排序
//										Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//											public int compare(Map.Entry<String, Object> o1,
//													Map.Entry<String, Object> o2) {
//												return (o1.getKey()).toString().compareTo(o2.getKey());
//											}
//										});
//										String strKeyValueString = "";
//										for (int t = 0; t < infoIds.size(); t++) {
//											Entry<String,Object> ent=infoIds.get(t);
//											strKeyValueString += ent.getValue();
//											
//										}
//										if (keyValueArrayList.indexOf(strKeyValueString) < 0){
//											continue;
//										}
//									}
//									//遍历插入数据
//									String insertaa="insert into "+thetable.getTotable()+" (";
//			            			String updateaa="update "+thetable.getTotable()+" set ";
//			            			String updatesqlString="";
//			            			String wheresqlString="";
//				            		String liearr="";
//				            		String zhiarr="";
//									for(Object obj:lielist){
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										String daovalue="?";
//										//如果是主键
//			            				if("1".equals(detail.getDcdifpkey())){
//			            					if("".equals(wheresqlString)){
//			            						wheresqlString=detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}else{
//			            						wheresqlString=wheresqlString+" and "+detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}
//			            				}
//			            				else{
//			            					zhujianindex++;
//			            					if("".equals(updatesqlString)){
//			            						updatesqlString=detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}else{
//			            						updatesqlString=updatesqlString+" , "+detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}
//			            				}
//			            				if("".equals(zhiarr)){
//		            						zhiarr=daovalue;
//		            					}else{
//		            						zhiarr=zhiarr+","+daovalue+"";
//		            					}
//			            				if("".equals(liearr)){
//			            					liearr=detail.getDcdtocolumns();
//		            					}else{
//		            						liearr=liearr+","+detail.getDcdtocolumns()+"";
//		            					}
//									}
//									insertaa=insertaa+liearr+" ) values ("+zhiarr+")";
//				            		updateaa=updateaa+updatesqlString+" where "+wheresqlString;
//				            		prestmtPreparedStatement1=daoruconn.prepareStatement(insertaa);
//				            		prestmtPreparedStatement2=daoruconn.prepareStatement(updateaa);
////				            		//更新语句必须带条件,否则不能进行更新
////				            		if(!"".equals(wheresqlString)){
////				            			updateList.add(prestmtPreparedStatement2);
////				            		}else{
////				            			updateList.add(null);
////				            		}
////				            		insertlist.add(prestmtPreparedStatement1);
//				            		int insertindex=1;
//				            		for(Object obj:lielist){
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										String daovalue=valuemapMap.get(detail.getDcdtocolumns().toLowerCase());
//										if(null==daovalue||"".equals(daovalue)){
//											daovalue="";
//										}
//										daovalue=this.jiexiValue(suid,daovalue, detail, fk, valuemapMap, null, daimamap, tobsseDatabaseNew, "jiaohuan",mapColumnType,mapCodeScidRemarkHashMap,orgValuemapMap);
//										daovalue=this.geshizhuanhuan(tobsseDatabaseNew.getDatabasetype(), detail.getDcdtocolumnstype(), daovalue);
//										this.addPreparedStatement(prestmtPreparedStatement1, detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(), insertindex, daovalue);
//										insertindex++;
//										//计分考核并且有查询条件的情况下
//										if (bolJfkh && bolHasCond) {
//											//如果是主表，把罪犯编号放到list里
//											if("0".equals(thetable.getDdtismaintable())&&GkzxCommon.JGXT_BH.equals(detail.getDcdfromcolumns())){
//												lstBh.add(daovalue);
//											}
//										}
//				            		}
//				            		int updateindex=1;
//				            		for(Object obj:lielist){
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										String daovalue=valuemapMap.get(detail.getDcdtocolumns().toLowerCase());
//										if(null==daovalue||"".equals(daovalue)){
//											daovalue="";
//										}
//										daovalue=this.jiexiValue(suid,daovalue, detail, fk, valuemapMap, null, daimamap, tobsseDatabaseNew, "jiaohuan",mapColumnType,mapCodeScidRemarkHashMap,orgValuemapMap);
//										daovalue=this.geshizhuanhuan(tobsseDatabaseNew.getDatabasetype(), detail.getDcdtocolumnstype(), daovalue);
//										if("1".equals(detail.getDcdifpkey())){
//											this.addPreparedStatement(prestmtPreparedStatement2, detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(), zhujianindex, daovalue);
//											zhujianindex++;
//										}
//			            				else{
//			            					this.addPreparedStatement(prestmtPreparedStatement2, detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(), updateindex, daovalue);
//			            					updateindex++;
//			            				}
//									}
//				            		try{
//				            			try {
//				            				if(GkzxCommon.ONE.equals(insertOnly)) {
//				            					//新增导入
//				            					prestmtPreparedStatement1.executeUpdate();
//				            				} else {
//				            					int result=prestmtPreparedStatement2.executeUpdate();
//				            					if (result==0) {
//				            						prestmtPreparedStatement1.executeUpdate();
//				            					}
//				            				}
//										} catch (Exception e) {
//											e.printStackTrace();
//											logger.error(MessageFormat.format(e.getMessage(), "jianyushujujiaohuanAction"));
//										}
//				            		}catch (Exception e) {
//				            			e.printStackTrace();
//				            			logger.error(MessageFormat.format(e.getMessage(), "jianyushujujiaohuanAction"));
//									}finally{
//										if(null!=prestmtPreparedStatement2){
//											prestmtPreparedStatement2.close();
//											prestmtPreparedStatement2 = null;
//										}
//										if(null!=prestmtPreparedStatement1){
//											prestmtPreparedStatement1.close();
//											prestmtPreparedStatement1=null;
//										}
//									}
//								}
//							}catch (Exception e) {
//								e.printStackTrace();
//							} finally {
//								try {
//									if (rs != null){
//										rs.close();
//									}
//									if (stmt != null){
//										stmt.close();
//									}
//								} catch (Exception e2) {
//								}
//							}
//							i = i + Integer.parseInt(interchange);
//						}
//					}
//				}
//				
////				if (hasXinqiBiandong){
////					xingqibiandong(daoruconn);
////				}
//				
////				if(hasTonganfan) {
////					this.tonganfan(conn,daoruconn);
////					this.qitaxinxi(conn,daoruconn);
////				}
//				
////				if(caipanxinxi) {
////					this.caipanxinxi(conn,daoruconn);
////				}
////				if (bolJfkh) {
////					//页面上输入条件并且bh有值的时候才处理
////					if (bolHasCond && lstBh.size() > 0) {
////						SzNightBatch snb = new SzNightBatch(strSdid, lstBh);
////						snb.daoruYnjc();
////					//页面上没有输入条件的时候才处理
////					} else if (!bolHasCond) {
////						SzNightBatch snb = new SzNightBatch(strSdid, lstBh);
////						snb.daoruYnjc();
////					}
////				}
////				ManageAssistInfo ma = new ManageAssistInfo();
////				ma.startWork();
////				daoruconn.commit();
//			}catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				JDBCUtils.closeResource(localconn, localps, localrs);
//				JDBCUtils.closeResource(conn, null, null);
//				if(null!=daoruconn){
//					try {
////						daoruconn.setAutoCommit(true);
//						daoruconn.close();
//						getQitaAnyou(bean.getDdcid());
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		return SUCCESS;
//	}
//	
//	private void caipanxinxi(Connection dcconn, Connection drconn) {
//		ArrayList<String> criminalList = new ArrayList<String>();
//		ArrayList<HashMap<String, String>> valueList = new ArrayList<HashMap<String,String>>();
////		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
//		String date = m_sdf.format(new Date());
//		String mender = GkzxCommon.USER_ADMIN;
//		PreparedStatement ps = null;
//		PreparedStatement psAnex = null;
//		ResultSet rs = null;
//		String provincecode = jyconfig.getProperty("provincecode");
//		if(drconn != null) {
//			String getCriminalSql = "select t.criminalid from tbcriminal_basic_info t where not exists " + 
//			"  (select 'X' from tbpublic_approved_file_annex f where t.criminalid = f.criminalid and f.xuhao = '1' and f.antype = '5')";
//			try {
//				ps = drconn.prepareStatement(getCriminalSql);
//				rs = ps.executeQuery();
//				while(rs.next()) {
//					criminalList.add(rs.getString("criminalid"));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				JDBCUtils.closeResource(null, ps, rs);
//			}
//		}
//		if(dcconn != null) {
//			String selectSql = "select a.bh,replace((select mc from pub_bmk c where lb='1C' and c.bm=a.ysqh),' ','')+a.ysmx+'于'+ " +
//			"replace(replace(substring(b.pcrq,1,4)+'年'+substring(b.pcrq,5,2)+'月'+substring(b.pcrq,7,2)+'日','年0','年'),'月0','月')+'作出('+" +
//			"substring(a.ysxh,1,4)+')'+a.yssm+'第'+" +
//			"case substring(substring(a.ysxh,5,6),1,5) when '00000' then substring(substring(a.ysxh,5,6),6,1) else " +
//			"case substring(substring(a.ysxh,5,6),1,4) when '0000' then substring(substring(a.ysxh,5,6),5,2) else " +
//			"case substring(substring(a.ysxh,5,6),1,3) when '000' then substring(substring(a.ysxh,5,6),4,3) else " +
//			"case substring(substring(a.ysxh,5,6),1,2) when '00' then substring(substring(a.ysxh,5,6),3,4) else substring(a.ysxh,6,6) end end end end" +
//			"+'号刑事判决，以被告人'+a.xm+'犯'+(select mc from pub_bmk c where c.lb='1A' and c.bm=a.zm)+'罪，判处'+" +
//			"case b.xq when '9995' then '无期徒刑'+case b.bznx when '000000' then '' else " +
//			"case b.bznx when '97' then '，剥夺政治权利终身' else " +
//			"'，剥夺政治权利'+replace(replace(replace(substring(b.bznx,1,2)+'年'+substring(b.bznx,3,2)+'个月','00年',''),'00个月',''),'年0','年') end end + " +
//			"case a.fj when '' then '。' else '，并处罚金人民币'+a.fj+'元。' end else " +
//			"case b.xq when '9996' then '死刑缓期两年执行'+ case b.bznx when '000000' then '' else " +
//			"case b.bznx when '97' then '，剥夺政治权利终身' else " +
//			"'，剥夺政治权利'+replace(replace(replace(substring(b.bznx,1,2)+'年'+substring(b.bznx,3,2)+'个月','00年',''),'00个月',''),'年0','年') end end + " +
//			"case a.fj when '' then '。' else '，并处罚金人民币'+a.fj+'元。' end else " +
//			"'有期徒刑'+case substring(replace(replace(substring(b.xq,1,2)+'年'+substring(b.xq,3,2)+'个月','00年',''),'00个月',''),1,1) when '0' then " +
//			"substring(replace(replace(substring(b.xq,1,2)+'年'+substring(b.xq,3,2)+'个月','00年',''),'00个月',''),2,10) " +
//			"else replace(replace(substring(b.xq,1,2)+'年'+substring(b.xq,3,2)+'个月','00年',''),'00个月','') end + " +
//			"case b.bznx when '000000' then '' else " +
//			"case b.bznx when '97' then '，剥夺政治权利终身' else " +
//			"'，剥夺政治权利'+replace(replace(replace(substring(b.bznx,1,2)+'年'+substring(b.bznx,3,2)+'个月','00年',''),'00个月',''),'年0','年') end end + " +
//			"case a.fj when '' then '。' else '，并处罚金人民币'+a.fj+'元。' end end end+" +
//			"case a.ss when '本人上诉' then '宣判后，被告人不服，提出上诉。'+" +
//			"replace((select mc from pub_bmk c where lb='1C' and c.bm=a.zsqh),' ','')+a.zsmx+'经过二审审理，于'+ " +
//			"replace(replace(substring(b.pcrq,1,4)+'年'+substring(b.pcrq,5,2)+'月'+substring(b.pcrq,7,2)+'日','年0','年'),'月0','月')+'作出('+" +
//			"substring(a.zsxh,1,4)+')'+a.zssm+'第'+" +
//			"case substring(substring(a.zsxh,5,6),1,5) when '00000' then substring(substring(a.zsxh,5,6),6,1) else " +
//			"case substring(substring(a.zsxh,5,6),1,4) when '0000' then substring(substring(a.zsxh,5,6),5,2) else " +
//			"case substring(substring(a.zsxh,5,6),1,3) when '000' then substring(substring(a.zsxh,5,6),4,3) else " +
//			"case substring(substring(a.zsxh,5,6),1,2) when '00' then substring(substring(a.zsxh,5,6),3,4) else substring(a.zsxh,6,6) end end end end +" +
//			"'号刑事裁定，对其维持原判。判决生效后，交付执行。'else case a.ss when '同案上诉' then '宣判后，同案被告人不服，提出上诉。'+" +
//			"replace((select mc from pub_bmk c where lb='1C' and c.bm=a.zsqh),' ','')+a.zsmx+'经过二审审理，于'+ " +
//			"replace(replace(substring(b.pcrq,1,4)+'年'+substring(b.pcrq,5,2)+'月'+substring(b.pcrq,7,2)+'日','年0','年'),'月0','月')+'作出('+" +
//			"substring(a.zsxh,1,4)+')'+a.zssm+'第'+" +
//			"case substring(substring(a.zsxh,5,6),1,5) when '00000' then substring(substring(a.zsxh,5,6),6,1) else " +
//			"case substring(substring(a.zsxh,5,6),1,4) when '0000' then substring(substring(a.zsxh,5,6),5,2) else " +
//			"case substring(substring(a.zsxh,5,6),1,3) when '000' then substring(substring(a.zsxh,5,6),4,3) else " +
//			"case substring(substring(a.zsxh,5,6),1,2) when '00' then substring(substring(a.zsxh,5,6),3,4) else substring(a.zsxh,6,6) end end end end +" +
//			"'号刑事裁定，对其维持原判。判决生效后，交付执行。' else '' end end as cpxx ";
//			if (GkzxCommon.PROVINCE_TIANJIN.equals(provincecode)) {
//				selectSql += 
//					"from vi_interface_zfxx a left join vi_interface_XFBD b on a.bh=b.bh and b.bdlb='01' where 1=1 and a.zybz != '3'";
//			} else {
//				selectSql += 
//					"from da_jbxx a left join xfzb b on a.bh=b.bh and b.bdlb='01' where 1=1 and a.zybz != '3'";
//			}
//			try {
//				ps = dcconn.prepareStatement(selectSql);
//				rs = ps.executeQuery();
//				while(rs.next()) {
//					HashMap<String, String> map = new HashMap<String, String>();
//					map.put("bh", rs.getString("bh"));
//					map.put("cpxx", rs.getString("cpxx")==null?"":rs.getString("cpxx").replaceAll("年0", "年").replaceAll("利0", "利"));
//					valueList.add(map);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				JDBCUtils.closeResource(null, ps, rs);
//			}
//		}
//		
//		if(valueList.size()>0 && criminalList.size() > 0) {
//			String insertAnexSql = "insert into tbpublic_approved_file_annex t  ( t.annexname,t.criminalid,t.xuhao,t.antype,t.zhujianstr,t.createtime,t.createmender,t.updatetime,t.updatemender )" + " \n" +
//			"select t1.annexname,t1.criminalid,t1.xuhao,t1.antype,t1.zhujianstr,t1.createtime,t1.createmender,t1.updatetime,t1.updatemender" + " \n" +
//			"from TBPUBLIC_APPROVED_FILE_ANNEX_A t1" + " \n" +
//			"where" + " \n" +
//			"t1.criminalid = ?" + " \n" +
//			"and t1.xuhao = ?" + " \n" +
//			"and t1.antype = ?" + " \n";
//			String insertSql = "insert into tbpublic_approved_file_annex_a t(t.annexcontent,t.annexname,t.criminalid,t.xuhao,t.antype,t.zhujianstr,t.createtime,t.createmender,t.updatetime,t.updatemender)" +
//					"values(?,?,?,?,?,?,to_date(?,?),?,to_date(?,?),?)";
//			for(Object object : valueList) {
//				HashMap<String,String> map = (HashMap<String, String>) object;
//				if(criminalList.indexOf(map.get("bh"))>-1) {
//					try {
//						ps = drconn.prepareStatement(insertSql);
//						ps.setString(1, map.get("cpxx"));
//						ps.setString(2, GkzxCommon.FYCP_ANNEX_NAME);
//						ps.setString(3, map.get("bh"));
//						ps.setString(4, GkzxCommon.ONE);
//						ps.setString(5, GkzxCommon.FIVE);
//						ps.setString(6, map.get("bh"));
//						ps.setString(7, date);
//						ps.setString(8, GkzxCommon.DATETIME24FORMAT);
//						ps.setString(9, mender);
//						ps.setString(10, date);
//						ps.setString(11, GkzxCommon.DATETIME24FORMAT);
//						ps.setString(12, date);
//						ps.executeUpdate();
//						
//						psAnex = drconn.prepareStatement(insertAnexSql);
//						psAnex.setString(1, map.get("bh"));
//						psAnex.setString(2, GkzxCommon.ONE);
//						psAnex.setString(3, GkzxCommon.FIVE);
//						psAnex.executeUpdate();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					} finally {
//						JDBCUtils.closeResource(null, ps, null);
//						JDBCUtils.closeResource(null, psAnex, null);
//					}
//				}
//			}
//		}
//	}
//	
//	private void xingqibiandong(Connection con){
//		PreparedStatement ps = null;
//		//ResultSet rs = null;
//		String sql = "";
//		//判裁刑期幅度
//		int count = 0;
//		try{
//			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITEXTENT = '无期' where (citextentyear = '9995' or CITCHANGEYEARTO = '9995') and mm.CITEXTENT is null";
//			ps = con.prepareStatement(sql);
//			ps.executeUpdate();
//			JDBCUtils.closeResource(null, ps, null);
//			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITEXTENT = '有期' where citextentyear = '9990'  and CITCHANGEYEARTO||CITCHANGEMONTHTO||CITCHANGEMONTHTO is  null and mm.CITEXTENT is null";
//			ps = con.prepareStatement(sql);
//			ps.executeUpdate();
//			JDBCUtils.closeResource(null, ps, null);
//			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITCHANGETERMTO = '无期' where CITCHANGEYEARTO = '9995' and mm.CITCHANGETERMTO is null";
//			ps = con.prepareStatement(sql);
//			ps.executeUpdate();
//			JDBCUtils.closeResource(null, ps, null);
//			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITCHANGETERMTO = '有期' where CITCHANGEYEARTO = '9990' and mm.CITCHANGETERMTO is null";
//			ps = con.prepareStatement(sql);
//			ps.executeUpdate();
//			JDBCUtils.closeResource(null, ps, null);
////			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITEXTENT = '有期' where citextentyear = '9990' and CITCHANGEYEARTO||CITCHANGEMONTHTO||CITCHANGEMONTHTO is not null";
////			ps = con.prepareStatement(sql);
////			ps.executeUpdate();
////			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITEXTENT = decode(CITEXTENTYEAR,'','',CITEXTENTYEAR||'年')||decode(CITEXTENTMONTH,'','','又'||CITEXTENTMONTH||'个月')||decode(CITEXTENTDAY,'','',CITEXTENTDAY||'天')  where citextentyear not in( '9990','9995')";
////			ps = con.prepareStatement(sql);
////			ps.executeUpdate();
//			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITEXTENT = " + "\n" +
//				" decode(CITEXTENTYEAR||CITEXTENTMONTH||CITEXTENTDAY,'','',decode(CITEXTENTYEAR,'','','-1','',CITEXTENTYEAR||'年')||decode(CITEXTENTMONTH,'','','-1','',decode(CITEXTENTYEAR,'',CITEXTENTMONTH||'个月','-1',CITEXTENTMONTH||'个月','又'||CITEXTENTMONTH||'个月'))||decode(CITEXTENTDAY,'','','-1','',CITEXTENTDAY||'天')) " + "\n" +
//				" where ((citextentyear is null) or (citextentyear not in( '9995'))) and  mm.CITEXTENT is null";
//			ps = con.prepareStatement(sql);
//			ps.executeUpdate();
//			JDBCUtils.closeResource(null, ps, null);
//			sql = "update TBYZH_CHANGE_IMPRISON_TERM mm set mm.CITCHANGETERMTO = " + "\n" +
//				" decode(CITCHANGEYEARTO||CITCHANGEMONTHTO||CITCHANGEDAYTO,'','',decode(CITCHANGEYEARTO,'','','-1','',CITCHANGEYEARTO||'年')||decode(CITCHANGEMONTHTO,'','','-1','',decode(CITCHANGEYEARTO,'',CITCHANGEMONTHTO||'个月','-1',CITCHANGEMONTHTO||'个月','又'||CITCHANGEMONTHTO||'个月'))||decode(CITCHANGEDAYTO,'','','-1','',CITCHANGEDAYTO||'天')) " + "\n" +
//				" where ((CITCHANGEYEARTO is null) or (CITCHANGEYEARTO not in( '9995'))) and mm.CITCHANGETERMTO is null";
//			ps = con.prepareStatement(sql);
//			ps.executeUpdate();
//			JDBCUtils.closeResource(null, ps, null);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			JDBCUtils.closeResource(null, ps, null);
//		}
//	}
//	
//	private void tonganfan(Connection dcconn,Connection drconn){
//		ArrayList<String> criminalList = new ArrayList<String>();
////		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
//		String date = m_sdf.format(new Date());
//		String mender = GkzxCommon.USER_ADMIN;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		if(drconn != null) {
//			String getCriminalSql = "select t.criminalid from tbcriminal_basic_info t where not exists(select 'X' from tbcriminal_tonganfan f where t.criminalid = f.criminalid )";
//			try {
//				ps = drconn.prepareStatement(getCriminalSql);
//				rs = ps.executeQuery();
//				while(rs.next()) {
//					criminalList.add(rs.getString("criminalid"));
//				}
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				JDBCUtils.closeResource(null, ps, rs);
//			}
//		}
//		if(dcconn != null) {
//			String getTahSql = "select a.bh,a.tah,a.xm,(select mc from pub_bmk c where c.bm=a.xb and c.lb='1I') as xb," +
//					"substring(a.csrq,1,4)+'-'+substring(a.csrq,5,2)+'-'+substring(a.csrq,7,2) as csrq," +
//					"(select c.mc from pub_bmk c where c.lb = '1F' and c.bm=a.bqzy) as bqzy," +
//					"(select c.mc from pub_bmk c where c.lb = '1A' and c.bm=a.zm) as zm," +
//					"case substring(replace(replace(substring(a.xq,1,2)+'年'+substring(a.xq,3,2)+'个月','00个月',''),'年0','年'),1,1) when '0' then " +
//					"substring(replace(replace(substring(a.xq,1,2)+'年'+substring(a.xq,3,2)+'个月','00个月',''),'年0','年'),2,100) else " +
//					"replace(replace(substring(a.xq,1,2)+'年'+substring(a.xq,3,2)+'个月','00个月',''),'年0','年') end as xq," +
//					"replace((select c.mc from pub_bmk c where c.lb = '1C' and c.bm=a.jtqh) + jtmx,' ','') as jtzz " +
//					"from da_jbxx a " +
//					"where  a.zybz != '3' and (select count(b.tah) from da_jbxx b where a.tah=b.tah)>1 order by a.tah";
//			ArrayList<HashMap<String, String>> valueList = new ArrayList<HashMap<String,String>>();
//			try {
//				ps = dcconn.prepareStatement(getTahSql);
//				rs = ps.executeQuery();
//				while(rs.next()) {
//					HashMap<String,String> map = new HashMap<String,String>();
//					map.put("bh", rs.getString("bh"));
//					map.put("tah", rs.getString("tah"));
//					map.put("xm", rs.getString("xm"));
//					map.put("xb", rs.getString("xb"));
//					map.put("csrq", rs.getString("csrq"));
//					map.put("bqzy", rs.getString("bqzy"));
//					map.put("zm", rs.getString("zm"));
//					map.put("xq", rs.getString("xq"));
//					map.put("jtzz", rs.getString("jtzz"));
//					valueList.add(map);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				JDBCUtils.closeResource(null, ps, rs);
//			}
//			
//			if(valueList.size()>0) {
//				for(int i=0; i<valueList.size(); i++) {
//					HashMap<String,String> map = valueList.get(i);
//					ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String,String>>();
//					newList.add(map);
//					for(int j=i+1; j<valueList.size(); j++) {
//						HashMap<String,String> newMap = valueList.get(j);
//						if(map.get("tah").equals(newMap.get("tah"))) {
//							newList.add(newMap);
//						} else {
//							i = j-1;
//							break;
//						}
//					}
//					for(int k=0; k<newList.size(); k++) {
//						HashMap<String,String> valueMap = newList.get(k);
//						if(criminalList.indexOf(valueMap.get("bh"))<0) {
//							continue;
//						}
//						int xuhao = 1;
//						for(int l=0; l<newList.size(); l++) {
//							if(k!=l) {
//								String insertSql = "insert into tbcriminal_tonganfan t(t.cbiname,t.cbisex,t.cbibirthday,t.cbizhiye,t.cbizuiming,t.cbixingqi,t.cbiaddress,t.criminalid,t.criminalidother,t.createtime,t.createmender,t.updatetime,t.updatemender,t.xuhao)" + 
//                                                   "values(?,?,to_date(?,?),?,?,?,?,?,?,to_date(?,?),?,to_date(?,?),?,?)";
//								HashMap<String,String> insertMap = newList.get(l);
//								try {
//									ps = drconn.prepareStatement(insertSql);
//									ps.setString(1, insertMap.get("xm"));
//									ps.setString(2, insertMap.get("xb"));
//									ps.setString(3, insertMap.get("csrq"));
//									ps.setString(4, GkzxCommon.DATEFORMAT);
//									ps.setString(5, insertMap.get("bqzy"));
//									ps.setString(6, insertMap.get("zm"));
//									ps.setString(7, insertMap.get("xq"));
//									ps.setString(8, insertMap.get("jtzz"));
//									ps.setString(9, valueMap.get("bh"));
//									ps.setString(10, insertMap.get("bh"));
//									ps.setString(11, date);
//									ps.setString(12, GkzxCommon.DATETIME24FORMAT);
//									ps.setString(13, mender);
//									ps.setString(14, date);
//									ps.setString(15, GkzxCommon.DATETIME24FORMAT);
//									ps.setString(16, mender);
//									ps.setString(17, String.valueOf(xuhao));
//									ps.executeUpdate();
//								} catch (SQLException e) {
////									e.printStackTrace();
//								} finally {
//									JDBCUtils.closeResource(null, ps, null);
//								}
//								xuhao++;
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	private void qitaxinxi(Connection dcconn, Connection drconn) {
//		String selectSql = "select a.bh,b.bhm,c.ky,g.mspjqk,i.tc,g.zybz from da_jbxx a left join da_bhm b on a.bh=b.bh left join da_tzzb c on a.bh=c.bh " +
//				"left join da_bz g on a.bh=g.bh left join da_tc i on a.bh=i.bh where 1=1 and a.zybz != '3' and " +
//				"(b.id = (select max(e.id) from da_bhm e where e.bh=a.bh) or b.bh is null) and " +
//				"(c.id = (select max(f.id) from da_tzzb f where f.bh=a.bh) or c.bh is null) and " +
//				"(g.id = (select max(h.id) from da_bz h where h.bh=a.bh) or g.bh is null) and (i.id = (select max(j.id) from da_tc j where j.bh=a.bh) or i.bh is null)";
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ArrayList<String> criminalList = new ArrayList<String>();
//		ArrayList<HashMap<String, String>> valueList = new ArrayList<HashMap<String,String>>();
//		if(drconn != null) {
//			String getCriminalSql = "select t.criminalid from tbcriminal_basic_info t";
//			try {
//				ps = drconn.prepareStatement(getCriminalSql);
//				rs = ps.executeQuery();
//				while(rs.next()) {
//					criminalList.add(rs.getString("criminalid"));
//				}
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally{
//				JDBCUtils.closeResource(null, ps, rs);
//			}
//		}
//		if(dcconn != null) {
//			try {
//				ps = dcconn.prepareStatement(selectSql);
//				rs = ps.executeQuery();
//				HashMap<String, String> map = null;
//				while(rs.next()) {
//					map = new HashMap<String,String>();
//					map.put("bh", rs.getString("bh"));
//					map.put("bhm", rs.getString("bhm"));
//					map.put("ky", rs.getString("ky"));
//					map.put("mspjqk", rs.getString("mspjqk"));
//					map.put("tc", rs.getString("tc"));
//					map.put("zybz", rs.getString("zybz"));
//					valueList.add(map);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				JDBCUtils.closeResource(null, ps, rs);
//			}
//		}
//		if(valueList.size()>0) {
//			String updateSql = "update tbcriminal_basic_info t set t.cbitruename=?,t.cbispeak=?,t.cjotherjudge=?,t.cbispeciality=?, t.danganhao=?,t.sanleifan=? where t.criminalid=?";
//			HashMap<String, String> map  = null;
//			for(Object object : valueList) {
//				map = (HashMap<String, String>) object;
//				if(criminalList.indexOf(map.get("bh")) > -1) {
//					try {
//						ps = drconn.prepareStatement(updateSql);
//						ps.setString(1, map.get("bhm"));
//						ps.setString(2, map.get("ky"));
//						ps.setString(3, map.get("mspjqk"));
//						ps.setString(4, map.get("tc"));
//						if(!StringUtil.isNullOrEmpty(map.get("bh"))){
//							ps.setString(5, map.get("bh").substring(5, 10));
//						}else{
//							ps.setString(5, "");
//						}
//						//三类罪犯
//						TbcriminalBasicInfo tbcriminalBasicInfo = (TbcriminalBasicInfo)this.baseDao.getObject(TbcriminalBasicInfo.class, map.get("bh"));
//						if(!StringUtil.isNullOrEmpty(tbcriminalBasicInfo.getSanleifan())){
//							ps.setString(6, tbcriminalBasicInfo.getSanleifan());
//						}else{
//							if(!StringUtil.isNullOrEmpty(map.get("zybz"))&&map.get("zybz").contains("三类")){
//								ps.setString(6, "0");
//							}else{
//								ps.setString(6, "");
//							}
//						}
//						ps.setString(7, map.get("bh"));
//						ps.executeUpdate();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					} finally {
//						JDBCUtils.closeResource(null, ps, null);
//					}
//				}
//			}
//		}
//	}
//	
//	private int getSumCount(Connection con, String sql){
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		int count = 0;
//		try{
//			ps = con.prepareStatement(sql);
//			rs = ps.executeQuery();
//			while(rs.next()){
//				count = rs.getInt(1);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			JDBCUtils.closeResource(null, ps, rs);
//		}
//		return count;
//	}
//	private List<Object> getCriminalidLst(Connection con, String sql){
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		List<Object> objList = null;
//		try{
//			ps = con.prepareStatement(sql);
//			rs = ps.executeQuery();
//			objList = jsonTool.ResultSetToList(rs);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			JDBCUtils.closeResource(null, ps, rs);
//		}
//		return objList;
//	}
//	private HashMap<String, HashMap<String, TbsecurityCode>> getLocalDbCodeScidRemark(Connection conn, ArrayList<String> strCodeList){
//		HashMap<String, HashMap<String, TbsecurityCode>> rtnMapCodeScidRemark = new HashMap<String, HashMap<String, TbsecurityCode>>();
//		PreparedStatement localps = null;
//		ResultSet localrs = null;
//		String strCodeTypeIds = "";
//		for (int i = 0; i < strCodeList.size(); i++) {
//			if (i == 0) {
//				strCodeTypeIds = "'" + strCodeList.get(i) + "'";
//			} else {
//				strCodeTypeIds  += "," + "'" + strCodeList.get(i) + "'";
//			}
//		}
//		
//		if (!StringUtil.isNullOrEmpty(strCodeTypeIds)) {
//			if (strCodeTypeIds.indexOf("#")>-1){
//				strCodeTypeIds = strCodeTypeIds.replaceAll("#", "");
//			}
////			String sql="from TbsecurityCode a where a.id.tbsecurityCodeType.sctid in ("+strCodeTypeIds+") order by a.id.tbsecurityCodeType.sctid, nlssort(a.sccontent,'NLS_SORT=SCHINESE_PINYIN_M')";
////			List<Object> objList=baseDao.queryListObjectAll(sql);
//			String sql="select * from TBSECURITY_CODE a where a.sctid in ("+strCodeTypeIds+") order by a.sctid, nlssort(a.sccontent,'NLS_SORT=SCHINESE_PINYIN_M')";
//			try {
//				localps = conn.prepareStatement(sql);
//				localrs = localps.executeQuery();
//				List<Object> objList = jsonTool.ResultSetToList(localrs);
//				JDBCUtils.closeResource(null, localps, localrs);
//
//				String strOldCodeTypeString = "";
//				String strNewCodeTypeString = "";
//				HashMap<String, TbsecurityCode> mapScidRemarkHashMap = new HashMap<String, TbsecurityCode>();
//				for (int i = 0; i < objList.size(); i++) {
//					HashMap map = (HashMap)objList.get(i);
//					TbsecurityCode tbCode = getTbsecurityCode(map);
//					if (i == 0) {
//						strNewCodeTypeString = tbCode.getId().getTbsecurityCodeType().getSctid();
//						strOldCodeTypeString = strNewCodeTypeString;
//					}
//					strNewCodeTypeString = tbCode.getId().getTbsecurityCodeType().getSctid();
//					if(strOldCodeTypeString.equals(strNewCodeTypeString)) {
//						mapScidRemarkHashMap.put(tbCode.getId().getScid(), tbCode);
//					} else {
//						rtnMapCodeScidRemark.put(strOldCodeTypeString, mapScidRemarkHashMap);
//						mapScidRemarkHashMap = new HashMap<String, TbsecurityCode>();
//						mapScidRemarkHashMap.put(tbCode.getId().getScid(), tbCode);
//						strOldCodeTypeString = strNewCodeTypeString;
//					}
//				}
//				
//				if (!StringUtil.isNullOrEmpty(strNewCodeTypeString)){
//					rtnMapCodeScidRemark.put(strNewCodeTypeString, mapScidRemarkHashMap);
//				}
//			
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				JDBCUtils.closeResource(null, localps, localrs);
//			}
//		}
//		
//		return rtnMapCodeScidRemark;
//	}
//	private String geshizhuanhuan(String basetype,String dcdfromcloumnstype,String value){
////		if(null!=value){
////			value=value.replace("'", "''");
////			if(value.length()>1000){
////				value=value.substring(0, 1000);
////			}
////		}
////		try{
////			if("Microsoft SQL Server".equals(basetype)){
////				if("DATE".equals(dcdfromcloumnstype)){
////					
////				}
////			}
////			if("Oracle".equals(basetype)){
////				if("DATE".equals(dcdfromcloumnstype)){
////					value="to_date('"+value+"','yyyy-MM-dd')";
////				}
////			}
////		}catch(Exception e){
////			e.printStackTrace();
////		}
//		return value;
//	}
//	private boolean yanzhengshuju(String name,String value,List<TempConBean> conlist){
//		boolean chaxunflag=false;
//		 if(null==conlist||conlist.size()==0){
//			 chaxunflag=true;
//		 }
//		 for(TempConBean conbean:conlist){
//			 if(conbean.getColname().equals(name)){
//				 if("=".equals(conbean.getYunsuanfu())){
//					 if(conbean.getYunsuanfu().equals(value)){
//						 chaxunflag=false;
//					 }
//				 }
//				 if("<>".equals(conbean.getYunsuanfu())){
//					 if(!conbean.getYunsuanfu().equals(name)){
//						 chaxunflag=false;
//					 }
//				 }
//				 if("like".equals(conbean.getYunsuanfu())){
//					 if(conbean.getYunsuanfu().contains(name)){
//						 chaxunflag=false;
//					 }
//				 }
//				 if(">=".equals(conbean.getYunsuanfu())){
//					 int m=conbean.getYunsuanfu().compareTo(name);
//					 if(m>=0){
//						 chaxunflag=false;
//					 }
//				 }
//				 if(">".equals(conbean.getYunsuanfu())){
//					 int m=conbean.getYunsuanfu().compareTo(name);
//					 if(m>0){
//						 chaxunflag=false;
//					 }
//				 }
//				 if("<=".equals(conbean.getYunsuanfu())){
//					 int m=conbean.getYunsuanfu().compareTo(name);
//					 if(m<=0){
//						 chaxunflag=false;
//					 }
//				 }
//				 if("<".equals(conbean.getYunsuanfu())){
//					 int m=conbean.getYunsuanfu().compareTo(name);
//					 if(m<0){
//						 chaxunflag=false;
//					 }
//				 }
//			 }
//		 }
//		return chaxunflag;
//	}
//	
//	 
//	    /**   
//	     * 此方法描述的是：对查到的数据，根据数据类型进行解析
//		 * @param   suid; daovalue:导入值; c1:方案映射关系表;fk:yyyymmdd*10;valuemapMap:改行的所有的值;dchang:未知;daimamap:代码映射
//		 * @param   tobs:数据库管理表;jiexitype:解析类型（jiaohuan:daoru）;mapColumnType:字段及组合字段信息（dbmx->GB002:dbqh:单位ID(可无)）
//		 * @param   mapCodeScidRemarkHashMap：code类型->scid:备注;orgValuemapMap:导入column->导入值
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: 2013-7-12 上午11:40:22   
//	     */   
//	    
//	private String  jiexiValue(String suid,String daovalue,DbmsDatasChemeDetailNew c1,int fk,Map<String, String> valuemapMap,
//			Element dchang,Map<String,List<Object>> daimamap,DbmsDatabaseNew tobs,String jiexitype, 
//			HashMap<String,ArrayList<String>> mapColumnType, HashMap<String, HashMap<String, TbsecurityCode>> mapCodeScidRemarkHashMap,
//			Map<String, String> orgValuemapMap){
//		
//		String strFromColumn = c1.getDcdfromcolumns();
//		if (!StringUtil.isNullOrEmpty(strFromColumn)){
//			if (strFromColumn.indexOf("@") > -1){
//				//该column的格式为：1000001:dbqh@[GB002]@dbmx；1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx；dbqh@[GB002]@dbmx
//				String[] strTmpColumnStrings = strFromColumn.split("@");
//				strFromColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//				//常量表字段时，字段值置空
//				if (addcolumnslst.indexOf(strFromColumn) > -1) {
//					daovalue = "";
//				}
//				if (mapColumnType.containsKey(strFromColumn)){
//					//dbmx->GB002:dbqh:单位ID(可无)
//					ArrayList<String> lstCodeTypeArrayList = mapColumnType.get(strFromColumn);
//					HashMap<String, TbsecurityCode> mapScidRemarkHashMap = mapCodeScidRemarkHashMap.get(lstCodeTypeArrayList.get(0).replaceAll("#", ""));
//					if (mapScidRemarkHashMap == null){
//						mapScidRemarkHashMap = new HashMap<String, TbsecurityCode>();
//					}
//					//dbmx->GB002:dbqh或者dbmx->[GB002]:dbqh
//					if (lstCodeTypeArrayList.size() == 2){
//						String strFromColumnValue = orgValuemapMap.get(lstCodeTypeArrayList.get(1).toLowerCase());
//						boolean getRemark = true;
//						if (lstCodeTypeArrayList.get(0).indexOf("#")>-1){
//							getRemark = false;
//						}
//						if (!StringUtil.isNullOrEmpty(strFromColumnValue)) {
//							if(strJgxt.equalsIgnoreCase(tobs.getDatabasename())){
//								if (!mapScidRemarkHashMap.containsKey((strFromColumnValue))) {
//									daovalue = daovalue.replaceAll(strFromColumnValue + GkzxCommon.PAUSE_MARK, "");
//								} else {
//									if (getRemark) {
//										daovalue = daovalue.replaceAll(mapScidRemarkHashMap.get(strFromColumnValue).getRemark(), "");
//									} else {
//										daovalue = daovalue.replaceAll(mapScidRemarkHashMap.get(strFromColumnValue).getSccontent(), "");
//									}
//								}
//							} else {
//								if (!mapScidRemarkHashMap.containsKey((strFromColumnValue))) {
//									daovalue = strFromColumnValue + GkzxCommon.PAUSE_MARK + daovalue;
//								} else {
//									if (getRemark) {
//										daovalue = mapScidRemarkHashMap.get(strFromColumnValue).getRemark() + daovalue;
//									} else {
//										daovalue = mapScidRemarkHashMap.get(strFromColumnValue).getSccontent() + daovalue;
//									}
//								}
//							}
//						}
//					} else if (lstCodeTypeArrayList.size() == 3){//dbmx->GB002:dbqh:单位ID
//						boolean getRemark = true;
//						if (lstCodeTypeArrayList.get(0).indexOf("#")>-1){
//							getRemark = false;
//						}
//						
//						String strFromColumnValue = orgValuemapMap.get(lstCodeTypeArrayList.get(1).toLowerCase());
//						//GB002:dbqh:1000017
//						List<Object> damamingxilist=daimamap.get(lstCodeTypeArrayList.get(2));
//						if(null!=damamingxilist&&damamingxilist.size()>0){
//							for(Object obj22:damamingxilist){
//								DbmsCodeChemeNew code=(DbmsCodeChemeNew)obj22;
//		            			if(strJgxt.equalsIgnoreCase(tobs.getDatabasename())){
//		            				if(null!=code&&strFromColumnValue.equals(code.getTargetid())){
//		            					if (mapScidRemarkHashMap.containsKey((code.getTargetid()))) {
//		            						if (getRemark) {
//		            							daovalue=daovalue.replaceAll(mapScidRemarkHashMap.get(code.getTargetid()).getRemark(), "");
//		            						} else {
//		            							daovalue=daovalue.replaceAll(mapScidRemarkHashMap.get(code.getTargetid()).getSccontent(), "");
//		            						}
//		            					} 
//		            					break;
//		            				}
//		            			}else{
//									if(null!=code&&strFromColumnValue.equals(code.getCodeid())){
//										if (!StringUtil.isNullOrEmpty(code.getTargetid())&&mapScidRemarkHashMap.containsKey((code.getTargetid()))) {
//											if (getRemark) {
//												daovalue = mapScidRemarkHashMap.get(code.getTargetid()).getRemark() + daovalue;
//											} else {
//												daovalue = mapScidRemarkHashMap.get(code.getTargetid()).getSccontent() + daovalue;
//											}
//										}
//										break;
//									}
//		            			}
//							}
//						}
//					}
//				}
//			}
//		}
//		//如果值为空 替换为默认值
//		if(null==daovalue||"".equals(daovalue)){
//			if(null!=c1.getDcdtocloumnsdefaultvalue()&&!"".equals(c1.getDcdtocloumnsdefaultvalue())){
//				if(":nowdate".equals(c1.getDcdtocloumnsdefaultvalue())){
////					SimpleDateFormat sdf222=new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
//					daovalue=m_sdf.format(new Date());
//				}
//				else if (":suid".equals(c1.getDcdtocloumnsdefaultvalue())){
//					daovalue=suid;
//				}
//				else{
//					daovalue=c1.getDcdtocloumnsdefaultvalue();
//				}
//			}
//			if("auto".equals(c1.getDcdpkgenerator())){
//				daovalue=String.valueOf(fk);
//				fk++;
//			}
//		}
//		if(null==daovalue||"".equals(daovalue)){
//		}else{
//			if(null!=c1.getDcdcodetype()&&!"".equals(c1.getDcdcodetype())&&!"undefined".equals(c1.getDcdcodetype())){
//				if("0000002".equals(c1.getDcdcodetype())){
//					if(daovalue.length()==8){
//						SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//						SimpleDateFormat sdf222=new SimpleDateFormat("yyyy-MM-dd");
//						Date temp=null;;
//						try {
//							temp = sdf.parse(daovalue);
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						daovalue=sdf222.format(temp);
//					}else{
//						daovalue="";
//					}
//				}
//				//判定刑种
//				else if("0000012".equals(c1.getDcdcodetype())){
//					if("9995".equals(daovalue)){
//						daovalue="2";
//					}
//					else if("9996".equals(daovalue)){
//						daovalue="3";
//					}else{
//						daovalue="1";
//					}
//				}
//				//判定刑期年
//				else if("0000013".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()==6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(0,2), '0');
//					} else if(null!=daovalue&&daovalue.length()<6){
//						//9990,9995,9996
//						daovalue=StringUtil.removeFrontExpression(daovalue, '0');
//					} else {
//						daovalue="";
//					}
//				}
//				//判定刑期月
//				else if("0000014".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()==6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(2,4), '0');
//					}else{
//						daovalue="";
//					}
//				}
//				//判定刑期天
//				else if("0000015".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()==6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(4,6), '0');
//					}else{
//						daovalue="";
//					}
//				}
//				//判定剥权年
//				else if("0000018".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()==6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(0,2), '0');
//					}else if(null!=daovalue&&daovalue.length()<=6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(0,2), '0');
//					}else{
//						daovalue="";
//					}
//				}
//				//判定剥权月
//				else if("0000019".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()==6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(2,4), '0');
//					}else{
//						daovalue="";
//					}
//				}
//				//判定剥权天
//				else if("0000020".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()==6){
//						daovalue=StringUtil.removeFrontExpression(daovalue.substring(4,6), '0');
//					}else{
//						daovalue="";
//					}
//				}
//				////截取案件号
//				else if("0000017".equals(c1.getDcdcodetype())){
////					if(null!=daovalue&&daovalue.length()>4 && daovalue.length()==10){
////						daovalue=daovalue.substring(daovalue.length()-3,daovalue.length());
////					}else{
////						daovalue="";
////					}
//					if(null!=daovalue){
//						Pattern pattern = Pattern.compile("[0-9]*"); 
//					    if(pattern.matcher(daovalue).matches()){//判断是否数字
//					    	if(daovalue.length()>4&&(daovalue.length()>=7)){
//								daovalue=daovalue.substring(daovalue.length()-4,daovalue.length());
//							}else if(daovalue.length()==4){
//								daovalue = daovalue.substring(2,daovalue.length());
//							}
//					    }
//					}else{
//						daovalue="";
//					}
//				}
//				//截取案件年
//				else if("0000016".equals(c1.getDcdcodetype())){
//					if(null!=daovalue&&daovalue.length()>4 && daovalue.length()==10){
//						daovalue=daovalue.substring(0,4);
//					}else{
//						daovalue="";
//					}
//				}
//				//四史之吸毒史、四涉之涉毒
//				else if("0000040".equals(c1.getDcdcodetype())||"0000046".equals(c1.getDcdcodetype())){
//					if(!StringUtil.isNullOrEmpty(daovalue)){
//						if(daovalue.indexOf("1") > -1) {
//							daovalue= "1";
//						} else {
//							daovalue="";
//						}
//					}else{
//						daovalue="";
//					}
//				}
//				//四史之脱逃史、四涉之涉枪
//				else if("0000041".equals(c1.getDcdcodetype())||"0000047".equals(c1.getDcdcodetype())){
//					if(!StringUtil.isNullOrEmpty(daovalue)){
//						if(daovalue.indexOf("2") > -1) {
//							daovalue= "1";
//						} else {
//							daovalue="";
//						}
//					}else{
//						daovalue="";
//					}
//				}
//				//四史之自杀史、四涉之涉黑
//				else if("0000042".equals(c1.getDcdcodetype())||"0000048".equals(c1.getDcdcodetype())){
//					if(!StringUtil.isNullOrEmpty(daovalue)){
//						if(daovalue.indexOf("3") > -1) {
//							daovalue= "1";
//						} else {
//							daovalue="";
//						}
//					}else{
//						daovalue="";
//					}
//				}
//				//四史之袭警史、四涉之涉恶
//				else if("0000043".equals(c1.getDcdcodetype())||"0000049".equals(c1.getDcdcodetype())){
//					if(!StringUtil.isNullOrEmpty(daovalue)){
//						if(daovalue.indexOf("4") > -1) {
//							daovalue= "1";
//						} else {
//							daovalue="";
//						}
//					}else{
//						daovalue="";
//					}
//				}
//				// 负1转为空
//				else if("1100000".equals(c1.getDcdcodetype())){
//					if(!StringUtil.isNullOrEmpty(daovalue)){
//						if(daovalue.equals(GkzxCommon.MINUS_ONE)) {
//							daovalue= "";
//						} 
//					}else{
//						daovalue="";
//					}
//				}
//				//拼凑案件号
//				else if("1000014".equals(c1.getDcdcodetype())){
//					String PCSM=valuemapMap.get("cjcasesort").toLowerCase();
//					String PCZH=valuemapMap.get("cjicourtnumber").toLowerCase();
//					if(null!=PCZH&&!"".equals(PCZH)){
//						String value1="";
//						if(null!=PCZH&&PCZH.length()>4 ){
//							value1="("+PCZH.substring(0,4)+")";
//						}else{
//							value1="";
//						}
//						String value2="";
//						if(null!=PCZH&&PCZH.length()>4 ){
//							value2="第"+PCZH.substring(PCZH.length()-3,PCZH.length())+"号";
//						}else{
//							value2="";
//						}
//						daovalue=value1+PCSM+value2;
//					}
//				}
//				//1000015拼音
//				else if("1000015".equals(c1.getDcdcodetype())){
//					String cbiname=valuemapMap.get("cbiname").toLowerCase();
//					daovalue="";
//					if(null!=cbiname&&!"".equals(cbiname)){
//						try {
//							HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat(); 
//							pinyinFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//							pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//							cbiname = zhuanpingying(cbiname);
//							for(int j=0; j<cbiname.length(); j++) {
//								char[] cha = cbiname.substring(j,j+1).toCharArray();
//								for(int i=0;i<cha.length;i++){
//									//简体中文
//									if(java.lang.Character.toString(cha[i]).matches("[\\u4E00-\\u9FA5]+$")){
//										try {
//											daovalue += PinyinHelper.toHanyuPinyinStringArray(cha[0], pinyinFormat)[0].charAt(0);
//										} catch (Exception e) {
//										}
//									}else{
//										daovalue+=cha[i];
//									}
//								}
//							}
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
//					}
//				}
//				//档卡刑期
//				else if("1000001".equals(c1.getDcdcodetype())){
//					daovalue="";
//					String CJIIMPRISONTYPE="";
//					if(jiexitype.equals("jiaohuan")){
//						CJIIMPRISONTYPE=valuemapMap.get("CJIIMPRISONTYPE").toLowerCase();
//							//((Element)dchang.elementIterator("CJIIMPRISONTYPE".toLowerCase()).next()).getText();
//					}else{
//						CJIIMPRISONTYPE=((Element)dchang.elementIterator("CJIIMPRISONTYPE".toLowerCase()).next()).getText();
//					}
//					
//					if("2".equals(CJIIMPRISONTYPE)){
//						daovalue="9995";
//					}
//					else if("".equals(CJIIMPRISONTYPE)){
//						daovalue="9996";
//					}else{
//						String CJIIMPRISONYEAR="";
//						String CJIIMPRISONMONTH="";
//						String CJIIMPRISONDAY="";
//						if(jiexitype.equals("jiaohuan")){
//							CJIIMPRISONYEAR=valuemapMap.get("CJIIMPRISONYEAR").toLowerCase();
//							CJIIMPRISONMONTH=valuemapMap.get("CJIIMPRISONMONTH").toLowerCase();
//							CJIIMPRISONDAY=valuemapMap.get("CJIIMPRISONDAY").toLowerCase();
//						}else{
//							CJIIMPRISONYEAR=((Element)dchang.elementIterator("CJIIMPRISONYEAR".toLowerCase()).next()).getText();
//							CJIIMPRISONMONTH=((Element)dchang.elementIterator("CJIIMPRISONMONTH".toLowerCase()).next()).getText();
//							CJIIMPRISONDAY=((Element)dchang.elementIterator("CJIIMPRISONDAY".toLowerCase()).next()).getText();
//						}
//						if(null!=CJIIMPRISONYEAR&&!"".equals(CJIIMPRISONYEAR)&&CJIIMPRISONYEAR.length()==2){
//							daovalue=daovalue+CJIIMPRISONYEAR;
//						}else if(null!=CJIIMPRISONYEAR&&!"".equals(CJIIMPRISONYEAR)&&CJIIMPRISONYEAR.length()==1){
//							daovalue=daovalue+"0"+CJIIMPRISONYEAR;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CJIIMPRISONMONTH&&!"".equals(CJIIMPRISONMONTH)&&CJIIMPRISONMONTH.length()==2){
//							daovalue=daovalue+CJIIMPRISONMONTH;
//						}else if(null!=CJIIMPRISONMONTH&&!"".equals(CJIIMPRISONMONTH)&&CJIIMPRISONMONTH.length()==1){
//							daovalue=daovalue+"0"+CJIIMPRISONMONTH;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CJIIMPRISONDAY&&!"".equals(CJIIMPRISONDAY)&&CJIIMPRISONDAY.length()==2){
//							daovalue=daovalue+CJIIMPRISONDAY;
//						}else if(null!=CJIIMPRISONDAY&&!"".equals(CJIIMPRISONDAY)&&CJIIMPRISONDAY.length()==1){
//							daovalue=daovalue+"0"+CJIIMPRISONDAY;
//						}else{
//							daovalue=daovalue+"00";
//						}
//					}
//				}
//				//党卡附加刑
//				else if("1000002".equals(c1.getDcdcodetype())){
//					daovalue="";
//					String CJIDISFRANCHISEYEAR="";
//					if(jiexitype.equals("jiaohuan")){
//						CJIDISFRANCHISEYEAR=valuemapMap.get("CJIDISFRANCHISEYEAR").toLowerCase();
//					}else{
//						CJIDISFRANCHISEYEAR=((Element)dchang.elementIterator("CJIDISFRANCHISEYEAR".toLowerCase()).next()).getText();
//					}
//					if("97".equals(CJIDISFRANCHISEYEAR)){
//						daovalue="97";
//					}
//					else{
//						String CJIDISFRANCHISEMONTH="";
//						String CJIDISFRANCHISEDAY="";
//						if(jiexitype.equals("jiaohuan")){
//							CJIDISFRANCHISEMONTH=valuemapMap.get("CJIDISFRANCHISEMONTH").toLowerCase();
//							CJIDISFRANCHISEDAY=valuemapMap.get("CJIDISFRANCHISEDAY").toLowerCase();
//						}else{
//							CJIDISFRANCHISEMONTH=((Element)dchang.elementIterator("CJIDISFRANCHISEMONTH".toLowerCase()).next()).getText();
//							CJIDISFRANCHISEDAY=((Element)dchang.elementIterator("CJIDISFRANCHISEDAY".toLowerCase()).next()).getText();
//						}
//						if(null!=CJIDISFRANCHISEYEAR&&!"".equals(CJIDISFRANCHISEYEAR)&&CJIDISFRANCHISEYEAR.length()==2){
//							daovalue=daovalue+CJIDISFRANCHISEYEAR;
//						}else if(null!=CJIDISFRANCHISEYEAR&&!"".equals(CJIDISFRANCHISEYEAR)&&CJIDISFRANCHISEYEAR.length()==1){
//							daovalue=daovalue+"0"+CJIDISFRANCHISEYEAR;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CJIDISFRANCHISEMONTH&&!"".equals(CJIDISFRANCHISEMONTH)&&CJIDISFRANCHISEMONTH.length()==2){
//							daovalue=daovalue+CJIDISFRANCHISEMONTH;
//						}else if(null!=CJIDISFRANCHISEMONTH&&!"".equals(CJIDISFRANCHISEMONTH)&&CJIDISFRANCHISEMONTH.length()==1){
//							daovalue=daovalue+"0"+CJIDISFRANCHISEMONTH;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CJIDISFRANCHISEDAY&&!"".equals(CJIDISFRANCHISEDAY)&&CJIDISFRANCHISEDAY.length()==2){
//							daovalue=daovalue+CJIDISFRANCHISEDAY;
//						}else if(null!=CJIDISFRANCHISEDAY&&!"".equals(CJIDISFRANCHISEDAY)&&CJIDISFRANCHISEDAY.length()==1){
//							daovalue=daovalue+"0"+CJIDISFRANCHISEDAY;
//						}else{
//							daovalue=daovalue+"00";
//						}
//					}
//				}
//				//党卡案件号
//				else if("1000003".equals(c1.getDcdcodetype())){
//					daovalue="";
//					String CJICOURTCITY="";
//					String CJICOURTNAME="";
//					String CJCASESORT="";
//					String CJCASEIDYEAR="";
//					String CJCASEIDNUMBER="";
//					if(jiexitype.equals("jiaohuan")){
//						CJICOURTCITY=valuemapMap.get("CJICOURTCITY").toLowerCase();
//						CJICOURTNAME=valuemapMap.get("CJICOURTNAME").toLowerCase();
//						CJCASESORT=valuemapMap.get("CJCASESORT").toLowerCase();
//						CJCASEIDYEAR=valuemapMap.get("CJCASEIDYEAR").toLowerCase();
//						CJCASEIDNUMBER=valuemapMap.get("CJCASEIDNUMBER").toLowerCase();
//					}else{
//						CJICOURTCITY=((Element)dchang.elementIterator("CJICOURTCITY".toLowerCase()).next()).getText();
//						CJICOURTNAME=((Element)dchang.elementIterator("CJICOURTNAME".toLowerCase()).next()).getText();
//						CJCASESORT=((Element)dchang.elementIterator("CJCASESORT".toLowerCase()).next()).getText();
//						CJCASEIDYEAR=((Element)dchang.elementIterator("CJCASEIDYEAR".toLowerCase()).next()).getText();
//						CJCASEIDNUMBER=((Element)dchang.elementIterator("CJCASEIDNUMBER".toLowerCase()).next()).getText();
//					}
//					daovalue=CJICOURTCITY+CJICOURTNAME+CJCASESORT+CJCASEIDYEAR;
//					if(null!=CJCASEIDNUMBER&&!"".equals(CJCASEIDNUMBER)&&CJCASEIDNUMBER.length()==3){
//						daovalue=daovalue+CJCASEIDNUMBER;
//					}else if(null!=CJCASEIDNUMBER&&!"".equals(CJCASEIDNUMBER)&&CJCASEIDNUMBER.length()==2){
//						daovalue=daovalue+"0"+CJCASEIDNUMBER;
//					}
//					else if(null!=CJCASEIDNUMBER&&!"".equals(CJCASEIDNUMBER)&&CJCASEIDNUMBER.length()==1){
//						daovalue=daovalue+"00"+CJCASEIDNUMBER;
//					}
//				}
//				//党卡日期
//				else if("1000004".equals(c1.getDcdcodetype())){
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//					SimpleDateFormat sdf222=new SimpleDateFormat("yyyy-MM-dd");
//					if(null!=daovalue&&!"".equals(daovalue)){
//						try{
//							Date tempdate=sdf222.parse(daovalue);
//							daovalue=sdf.format(tempdate);
//						}catch(Exception e3){
//							daovalue="";
//							e3.printStackTrace();
//						}
//					}
//				}
//				//党卡案件简号
//				else if("1000005".equals(c1.getDcdcodetype())){
//					daovalue="";
//					String CJCASEIDYEAR="";
//					String CJCASEIDNUMBER="";
//					if(jiexitype.equals("jiaohuan")){
//						CJCASEIDYEAR=valuemapMap.get("CJCASEIDYEAR").toLowerCase();
//						CJCASEIDNUMBER=valuemapMap.get("CJCASEIDNUMBER").toLowerCase();
//					}else{
//						CJCASEIDYEAR=((Element)dchang.elementIterator("CJCASEIDYEAR".toLowerCase()).next()).getText();
//						CJCASEIDNUMBER=((Element)dchang.elementIterator("CJCASEIDNUMBER".toLowerCase()).next()).getText();
//					}
//					daovalue=daovalue+CJCASEIDYEAR;
//					if(null!=CJCASEIDNUMBER&&!"".equals(CJCASEIDNUMBER)&&CJCASEIDNUMBER.length()==3){
//						daovalue=daovalue+CJCASEIDNUMBER;
//					}else if(null!=CJCASEIDNUMBER&&!"".equals(CJCASEIDNUMBER)&&CJCASEIDNUMBER.length()==2){
//						daovalue=daovalue+"0"+CJCASEIDNUMBER;
//					}
//					else if(null!=CJCASEIDNUMBER&&!"".equals(CJCASEIDNUMBER)&&CJCASEIDNUMBER.length()==1){
//						daovalue=daovalue+"00"+CJCASEIDNUMBER;
//					}
//				}
//				//党卡拼音
//				else if("1000006".equals(c1.getDcdcodetype())){
//					daovalue="";
//					String CBINAME="";
//					if(jiexitype.equals("jiaohuan")){
//						CBINAME=valuemapMap.get("CBINAME").toLowerCase();
//					}else{
//						CBINAME=((Element)dchang.elementIterator("CBINAME".toLowerCase()).next()).getText();
//					}
//					if(null!=CBINAME&&!"".equals(CBINAME)){
//						PinYinZhuanHua zhuanhua=new PinYinZhuanHua();
//						daovalue=zhuanhua.zhuanhua(CBINAME);
//					}
//				}
//				//党卡刑期变动附加刑
//				else if("1000010".equals(c1.getDcdcodetype())){
//					String CITCHANGEDISFRANCHISETO="";
//					if(jiexitype.equals("jiaohuan")){
//						CITCHANGEDISFRANCHISETO=valuemapMap.get("CITCHANGEDISFRANCHISETO").toLowerCase();
//					}else{
//						CITCHANGEDISFRANCHISETO=((Element)dchang.elementIterator("CITCHANGEDISFRANCHISETO".toLowerCase()).next()).getText();
//					}
//					if("97".equals(CITCHANGEDISFRANCHISETO)){
//						daovalue="97";
//					}else{
//						if(null!=daovalue&&!"".equals(daovalue)){
//							if(daovalue.length()==1){
//								daovalue="0"+daovalue+"0000";
//							}
//							else if(daovalue.length()==2){
//								daovalue=daovalue+"0000";
//							}else{
//								daovalue="000000";
//							}
//						}
//					}
//				}
//				//党卡刑期变动刑期
//				else if("1000011".equals(c1.getDcdcodetype())){
//					String CITCHANGEYEARTO="";
//					if(jiexitype.equals("jiaohuan")){
//						CITCHANGEYEARTO=valuemapMap.get("CITCHANGEYEARTO").toLowerCase();
//					}else{
//						CITCHANGEYEARTO=((Element)dchang.elementIterator("CITCHANGEYEARTO".toLowerCase()).next()).getText();
//					}
//					if(!"9995".equals(CITCHANGEYEARTO)&&!"9996".equals(CITCHANGEYEARTO)){
//						String CITCHANGEMONTHTO="";
//						String CITCHANGEDAYTO="";
//						if(jiexitype.equals("jiaohuan")){
//							CITCHANGEMONTHTO=valuemapMap.get("CITCHANGEMONTHTO").toLowerCase();
//							CITCHANGEDAYTO=valuemapMap.get("CITCHANGEDAYTO").toLowerCase();
//						}else{
//							CITCHANGEMONTHTO=((Element)dchang.elementIterator("CITCHANGEMONTHTO".toLowerCase()).next()).getText();
//							CITCHANGEDAYTO=((Element)dchang.elementIterator("CITCHANGEDAYTO".toLowerCase()).next()).getText();
//						}
//						daovalue="";
//						if(null!=CITCHANGEYEARTO&&!"".equals(CITCHANGEYEARTO)&&CITCHANGEYEARTO.length()==2){
//							daovalue=daovalue+CITCHANGEYEARTO;
//						}else if(null!=CITCHANGEYEARTO&&!"".equals(CITCHANGEYEARTO)&&CITCHANGEYEARTO.length()==1){
//							daovalue=daovalue+"0"+CITCHANGEYEARTO;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CITCHANGEMONTHTO&&!"".equals(CITCHANGEMONTHTO)&&CITCHANGEMONTHTO.length()==2){
//							daovalue=daovalue+CITCHANGEMONTHTO;
//						}else if(null!=CITCHANGEMONTHTO&&!"".equals(CITCHANGEMONTHTO)&&CITCHANGEMONTHTO.length()==1){
//							daovalue=daovalue+"0"+CITCHANGEMONTHTO;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CITCHANGEDAYTO&&!"".equals(CITCHANGEDAYTO)&&CITCHANGEDAYTO.length()==2){
//							daovalue=daovalue+CITCHANGEDAYTO;
//						}else if(null!=CITCHANGEDAYTO&&!"".equals(CITCHANGEDAYTO)&&CITCHANGEDAYTO.length()==1){
//							daovalue=daovalue+"0"+CITCHANGEDAYTO;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//					}
//				}
//				//党卡刑期变动幅度
//				else if("1000012".equals(c1.getDcdcodetype())){
//					String CITEXTENTYEAR="";
//					if(jiexitype.equals("jiaohuan")){
//						CITEXTENTYEAR=valuemapMap.get("CITEXTENTYEAR").toLowerCase();
//					}else{
//						CITEXTENTYEAR=((Element)dchang.elementIterator("CITEXTENTYEAR".toLowerCase()).next()).getText();
//					}
//					if(!"9995".equals(CITEXTENTYEAR)&&!"9996".equals(CITEXTENTYEAR)){
//						String CITEXTENTMONTH="";
//						String CITEXTENTDAY="";
//						if(jiexitype.equals("jiaohuan")){
//							CITEXTENTMONTH=valuemapMap.get("CITEXTENTMONTH").toLowerCase();
//							CITEXTENTDAY=valuemapMap.get("CITEXTENTDAY").toLowerCase();
//						}else{
//							CITEXTENTMONTH=((Element)dchang.elementIterator("CITEXTENTMONTH".toLowerCase()).next()).getText();
//							CITEXTENTDAY=((Element)dchang.elementIterator("CITEXTENTDAY".toLowerCase()).next()).getText();
//						}
//						daovalue="";
//						if(null!=CITEXTENTYEAR&&!"".equals(CITEXTENTYEAR)&&CITEXTENTYEAR.length()==2){
//							daovalue=daovalue+CITEXTENTYEAR;
//						}else if(null!=CITEXTENTYEAR&&!"".equals(CITEXTENTYEAR)&&CITEXTENTYEAR.length()==1){
//							daovalue=daovalue+"0"+CITEXTENTYEAR;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CITEXTENTMONTH&&!"".equals(CITEXTENTMONTH)&&CITEXTENTMONTH.length()==2){
//							daovalue=daovalue+CITEXTENTMONTH;
//						}else if(null!=CITEXTENTMONTH&&!"".equals(CITEXTENTMONTH)&&CITEXTENTMONTH.length()==1){
//							daovalue=daovalue+"0"+CITEXTENTMONTH;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CITEXTENTDAY&&!"".equals(CITEXTENTDAY)&&CITEXTENTDAY.length()==2){
//							daovalue=daovalue+CITEXTENTDAY;
//						}else if(null!=CITEXTENTDAY&&!"".equals(CITEXTENTDAY)&&CITEXTENTDAY.length()==1){
//							daovalue=daovalue+"0"+CITEXTENTDAY;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//					}
//				}
//				//罪名获取刑期
//				else if("1000013".equals(c1.getDcdcodetype())){
//					String CJAIIMPRISONYEAR="";
//					if(jiexitype.equals("jiaohuan")){
//						CJAIIMPRISONYEAR=valuemapMap.get("CJAIIMPRISONYEAR").toLowerCase();
//					}else{
//						CJAIIMPRISONYEAR=((Element)dchang.elementIterator("CJAIIMPRISONYEAR".toLowerCase()).next()).getText();
//					}
//					if(!"9995".equals(CJAIIMPRISONYEAR)&&!"9996".equals(CJAIIMPRISONYEAR)){
//						String CJAIIMPRISONMONTH="";
//						String CJAIIMPRISONDAY="";
//						if(jiexitype.equals("jiaohuan")){
//							CJAIIMPRISONMONTH=valuemapMap.get("CJAIIMPRISONMONTH").toLowerCase();
//							CJAIIMPRISONDAY=valuemapMap.get("CJAIIMPRISONDAY").toLowerCase();
//						}else{
//							CJAIIMPRISONMONTH=((Element)dchang.elementIterator("CJAIIMPRISONMONTH".toLowerCase()).next()).getText();
//							CJAIIMPRISONDAY=((Element)dchang.elementIterator("CJAIIMPRISONDAY".toLowerCase()).next()).getText();
//						}
//						daovalue="";
//						if(null!=CJAIIMPRISONYEAR&&!"".equals(CJAIIMPRISONYEAR)&&CJAIIMPRISONYEAR.length()==2){
//							daovalue=daovalue+CJAIIMPRISONYEAR;
//						}else if(null!=CJAIIMPRISONYEAR&&!"".equals(CJAIIMPRISONYEAR)&&CJAIIMPRISONYEAR.length()==1){
//							daovalue=daovalue+"0"+CJAIIMPRISONYEAR;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CJAIIMPRISONMONTH&&!"".equals(CJAIIMPRISONMONTH)&&CJAIIMPRISONMONTH.length()==2){
//							daovalue=daovalue+CJAIIMPRISONMONTH;
//						}else if(null!=CJAIIMPRISONMONTH&&!"".equals(CJAIIMPRISONMONTH)&&CJAIIMPRISONMONTH.length()==1){
//							daovalue=daovalue+"0"+CJAIIMPRISONMONTH;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//						if(null!=CJAIIMPRISONDAY&&!"".equals(CJAIIMPRISONDAY)&&CJAIIMPRISONDAY.length()==2){
//							daovalue=daovalue+CJAIIMPRISONDAY;
//						}else if(null!=CJAIIMPRISONDAY&&!"".equals(CJAIIMPRISONDAY)&&CJAIIMPRISONDAY.length()==1){
//							daovalue=daovalue+"0"+CJAIIMPRISONDAY;
//						}else{
//							daovalue=daovalue+"00";
//						}
//						
//					}
//				}
//				//累犯
//				else if("1000019".equals(c1.getDcdcodetype())){
//					String CAIIFLEIFAN="";
//					if(jiexitype.equals("jiaohuan")){
//						CAIIFLEIFAN=valuemapMap.get("CAIIFLEIFAN".toLowerCase());
//					}else{
//						CAIIFLEIFAN=((Element)dchang.elementIterator("CAIIFLEIFAN".toLowerCase()).next()).getText();
//					}
//					if("2".equals(CAIIFLEIFAN)){
//						daovalue="1";
//					}else{
//						daovalue="0";
//					}
//				}
//				//惯犯
//				else if("1000020".equals(c1.getDcdcodetype())){
//					String CAIIFGUANFAN="";
//					if(jiexitype.equals("jiaohuan")){
//						CAIIFGUANFAN=valuemapMap.get("CAIIFGUANFAN".toLowerCase());
//					}else{
//						CAIIFGUANFAN=((Element)dchang.elementIterator("CAIIFGUANFAN".toLowerCase()).next()).getText();
//					}
//					if("1".equals(CAIIFGUANFAN)){
//						daovalue="1";
//					}else{
//						daovalue="0";
//					}
//				}
//				else {
//					List<Object> damamingxilist=daimamap.get(c1.getDcdcodetype());
//					if(null!=damamingxilist&&damamingxilist.size()>0){
//						DbmsCodeChemeNew code= null;
//						for(Object obj22:damamingxilist){
//							code=(DbmsCodeChemeNew)obj22;
//							if(strJgxt.equalsIgnoreCase(tobs.getDatabasename())){
//								if(null!=code&&daovalue.equals(code.getTargetid())){
//    								daovalue=code.getCodeid();
//    								break;
//    							}
//							}else{
//								if(null!=code&&daovalue.equals(code.getCodeid())){
//    								daovalue=code.getTargetid();
//    								break;
//    							}
//							}
//						}
//					}
//				}
//			}
//		}
//		return daovalue;
//	}
//	public Object addPreparedStatement(PreparedStatement stmt,String colname,String coltype,int index,Object value) {
//		 Object returnValue = value;
//		 ByteArrayInputStream byteInStream = null;
//		 Reader clobReader = null;
//		 try{
//			 if(GkzxCommon.VARCHAR2.equals(coltype) || GkzxCommon.CHAR.equals(coltype)){
//				 if(null==value||"".equals(value)){
//					 stmt.setString(index, "");
//					 returnValue = "";
//				 }
//				 else{
//					 String temp=dataBaseDateTransfer.ToString(value);
//					stmt.setString(index, temp);
//					returnValue = temp;
//				 }
//			 }
//			 else if(GkzxCommon.NUMBER.equals(coltype)){
//				 if(null==value||"".equals(value)||"''".equals(value)){
//					 stmt.setNull(index, Types.NULL);
//					 returnValue = Types.NULL;
//				 }
//				 else{
//					 BigDecimal temp=dataBaseDateTransfer.ToBigDecimal(value);
//					 stmt.setBigDecimal(index, temp);
//					 returnValue = temp;
//				 }
//			 }
//			 else if(GkzxCommon.FLOAT.equals(coltype)){
//				 if(null==value||"".equals(value)){
//					 value=0f;
//				 }
//				 Float temp=dataBaseDateTransfer.ToFloat(value);
//				 stmt.setFloat(index, temp);
//				 returnValue = temp;
//			 }
//			 else if(GkzxCommon.DATE.equals(coltype)){
//				 if(null==value||"".equals(value)){
//					 stmt.setNull(index, Types.TIMESTAMP);
//					 returnValue = Types.TIMESTAMP;
//				 }
//				 else{
//					 Timestamp temp=dataBaseDateTransfer.ToDate(value);
//						stmt.setTimestamp(index, temp);
//						returnValue = temp;
//				 }
//			 }
//			 else if(GkzxCommon.Clob.equalsIgnoreCase(coltype)){
//				 clobReader = new StringReader(value.toString());//大字段转换
//				 stmt.setCharacterStream(index, clobReader, value.toString().length());//大字段
//			 }
//			 else if(GkzxCommon.Blob.equalsIgnoreCase(coltype)){
//				 byteInStream = new ByteArrayInputStream(value.toString().getBytes());
//				 stmt.setBinaryStream(index, byteInStream, value.toString().length());//大字段
//			 }
//		 }catch (Exception e) {
//			// TODO: handle exception
//			 e.printStackTrace();
//		} finally {
//			try {
//				if (byteInStream != null){
//					byteInStream.close();
//				}
//				if (clobReader != null){
//					clobReader.close();
//				}
//			} catch (Exception e2) {
//			}
//		}
//		
//		 return  returnValue;
//	 }
//
//	public void setSession(Map session) {
//		this.session = session;
//	}
//	
//	//转拼音 特殊字符 简单处理 
//	public String zhuanpingying(String pingyingname){
//		String value ="";
//		if(!StringUtil.isNullOrEmpty(pingyingname)){
//			value = pingyingname.replace("。", "").replaceAll("·", "").replace("(", "").replace(")", "").replace("）", "").replace("（", "");
//		}
//		return value;
//	}
//	private DbmsDatabaseNew getDatabaseNew(HashMap map){
//		DbmsDatabaseNew dbmsDatabaseNew = new DbmsDatabaseNew();
//		dbmsDatabaseNew.setDdid(getStringByMap(map, "ddid"));
//		dbmsDatabaseNew.setDdname(getStringByMap(map,"ddname"));
//		dbmsDatabaseNew.setDdip(getStringByMap(map,"ddip"));
//		dbmsDatabaseNew.setDatabasename(getStringByMap(map,"databasename"));
//		dbmsDatabaseNew.setDdorg(getStringByMap(map,"ddorg"));
//		dbmsDatabaseNew.setDatabaseuser(getStringByMap(map,"databaseuser"));
//		dbmsDatabaseNew.setDatabasepassword(getStringByMap(map,"databasepassword"));
//		dbmsDatabaseNew.setPort(getShortByMap(map,"port"));
//		dbmsDatabaseNew.setDatabasetype(getStringByMap(map,"databasetype"));
//		dbmsDatabaseNew.setSdid(getStringByMap(map,"sdid"));
//		return dbmsDatabaseNew;
//	}
//	
//	private TbsecurityCode getTbsecurityCode(HashMap map){
//		TbsecurityCode dbmsTbsecurityCode = new TbsecurityCode();
//		TbsecurityCodeId id = new TbsecurityCodeId();
//		TbsecurityCodeType tbsecurityCodeType = new TbsecurityCodeType();
//		tbsecurityCodeType.setSctid(getStringByMap(map, "sctid"));
//		id.setScid(getStringByMap(map, "scid"));
//		id.setTbsecurityCodeType(tbsecurityCodeType);
//		dbmsTbsecurityCode.setId(id);
//		dbmsTbsecurityCode.setSccontent(getStringByMap(map, "sccontent"));
//		dbmsTbsecurityCode.setScorderby(getDoubleByMap(map, "scorderby"));
//		dbmsTbsecurityCode.setScearch(getStringByMap(map, "scearch"));
//		dbmsTbsecurityCode.setScparentid(getStringByMap(map, "scparentid"));
//		dbmsTbsecurityCode.setRemark(getStringByMap(map, "remark"));
//		return dbmsTbsecurityCode;
//	}
//	
//	private List<Object> getCodeChemeNew(List<Object> objList){
//		List<Object> newObjectList = new ArrayList<Object>();
//		for(int i = 0; i < objList.size(); i++) {
//			HashMap map = (HashMap)objList.get(i);
//			DbmsCodeChemeNew dbmsCodeChemeNew = new DbmsCodeChemeNew();
////			DbmsCodeChemeNewId id = new DbmsCodeChemeNewId();
////			id.setCodetype(getStringByMap(map, "codetype"));
////			id.setDccid(getStringByMap(map, "dccid"));
////			dbmsCodeChemeNew.setId(id);
////			DbmsCodeTypeNew codeTypeNew = new DbmsCodeTypeNew();
//			dbmsCodeChemeNew.setCodeid(getStringByMap(map, "codeid"));
//			dbmsCodeChemeNew.setCodecontent(getStringByMap(map, "codecontent"));
//			dbmsCodeChemeNew.setTargetid(getStringByMap(map, "targetid"));
//			newObjectList.add(dbmsCodeChemeNew);
//		}
//		return newObjectList;
//	}
//	
//	private List<Object> getDatasTableNew(List<Object> objList){
//		List<Object> newObjectList = new ArrayList<Object>();
//		for(int i = 0; i < objList.size(); i++) {
//			HashMap map = (HashMap)objList.get(i);
//			DbmsDatasTableNew dbmsDatasTableNew = new DbmsDatasTableNew();
//			DbmsDatasTableNewId id = new DbmsDatasTableNewId(); 
//			id.setDdcid(getStringByMap(map, "ddcid"));
//			id.setDdtid(getStringByMap(map, "ddtid"));
//			dbmsDatasTableNew.setId(id);
//			dbmsDatasTableNew.setTablename(getStringByMap(map, "tablename"));
//			dbmsDatasTableNew.setDescrition(getStringByMap(map, "descrition"));
//			dbmsDatasTableNew.setDdtorder(getShortByMap(map, "ddtorder"));
//			dbmsDatasTableNew.setDdtisscreen(getStringByMap(map, "ddtisscreen"));
//			dbmsDatasTableNew.setDdtismaintable(getStringByMap(map, "ddtismaintable"));
//			dbmsDatasTableNew.setDdtjoinedfields(getStringByMap(map, "ddtjoinedfields"));
//			dbmsDatasTableNew.setShujuguanxi(getStringByMap(map, "shujuguanxi"));
//			dbmsDatasTableNew.setDaochusql(getStringByMap(map, "daochusql"));
//			dbmsDatasTableNew.setTotable(getStringByMap(map, "totable"));
//			dbmsDatasTableNew.setAddcondition(getStringByMap(map, "addcondition"));
//			
//			newObjectList.add(dbmsDatasTableNew);
//		}
//		return newObjectList;
//	}
//	
//	private List<Object> getDatasChemeDetailNew(List<Object> objList){
//		List<Object> newObjectList = new ArrayList<Object>();
//		for(int i = 0; i < objList.size(); i++) {
//			HashMap map = (HashMap)objList.get(i);
//			DbmsDatasChemeDetailNew dbmsDatasChemeDetailNew = new DbmsDatasChemeDetailNew();
//			DbmsDatasChemeDetailNewId id = new DbmsDatasChemeDetailNewId();
//			id.setDcdid(getStringByMap(map, "dcdid"));
//			id.setDdcid(getStringByMap(map, "ddcid"));
//			dbmsDatasChemeDetailNew.setId(id);
//			dbmsDatasChemeDetailNew.setDdtid(getStringByMap(map, "ddtid"));
//			dbmsDatasChemeDetailNew.setDcdfromcolumns(getStringByMap(map, "dcdfromcolumns"));
//			dbmsDatasChemeDetailNew.setDcdfromcolumnsscribe(getStringByMap(map, "dcdfromcolumnsscribe"));
//			dbmsDatasChemeDetailNew.setDcdfromcloumnstype(getStringByMap(map, "dcdfromcloumnstype"));
//			dbmsDatasChemeDetailNew.setDcdfromcloumnssize(getShortByMap(map, "dcdfromcloumnssize"));
//			dbmsDatasChemeDetailNew.setDcdfromcloumnsdefaultvalue(getStringByMap(map, "dcdfromcloumnsdefaultvalue"));
//			dbmsDatasChemeDetailNew.setDcdtotableid(getStringByMap(map, "dcdtotableid"));
//			dbmsDatasChemeDetailNew.setDcdtocolumns(getStringByMap(map, "dcdtocolumns"));
//			dbmsDatasChemeDetailNew.setDcdtocolumnsscribe(getStringByMap(map, "dcdtocolumnsscribe"));
//			dbmsDatasChemeDetailNew.setDcdtocolumnstype(getStringByMap(map, "dcdtocolumnstype"));
//			dbmsDatasChemeDetailNew.setDcdtocolumnssize(getShortByMap(map, "dcdtocolumnssize"));
//			dbmsDatasChemeDetailNew.setDcdtocolumnspoint(getShortByMap(map, "dcdtocolumnspoint"));
//			dbmsDatasChemeDetailNew.setDcdfromcolumnspoint(getShortByMap(map, "dcdfromcolumnspoint"));
//			dbmsDatasChemeDetailNew.setDcdifpkey(getStringByMap(map, "dcdifpkey"));
//			dbmsDatasChemeDetailNew.setDcdorder(getDoubleByMap(map, "dcdorder"));
//			dbmsDatasChemeDetailNew.setDcdpkgenerator(getStringByMap(map, "dcdpkgenerator"));
//			dbmsDatasChemeDetailNew.setDcdcodetype(getStringByMap(map, "dcdcodetype"));
//			dbmsDatasChemeDetailNew.setDcdtocloumnsdefaultvalue(getStringByMap(map, "dcdtocloumnsdefaultvalue"));
//			dbmsDatasChemeDetailNew.setJsoncolumns(getStringByMap(map, "jsoncolumns"));
//			newObjectList.add(dbmsDatasChemeDetailNew);
//		}
//		return newObjectList;
//	}
//	
//	public String getStringByMap(HashMap map,String field)
//	 {
//		 if(null==map.get(field)||"".equals(map.get(field).toString()))
//		 {
//			 return "";
//		 }
//		return map.get(field).toString();	 
//	 }
//	
//	 //get int 
//	 @SuppressWarnings("unchecked")
//	 public short getShortByMap(HashMap map,String field)
//	 {
//		 if(null==map.get(field)||"".equals(map.get(field).toString()))
//		 {
//			 return 0;
//		 }
//		return Short.valueOf( map.get(field).toString() );	 
//	 }
//	 //get int 
//	 @SuppressWarnings("unchecked")
//	 public Double getDoubleByMap(HashMap map,String field)
//	 {
//		 if(null==map.get(field)||"".equals(map.get(field).toString()))
//		 {
//			 return new Double(0);
//		 }
//		 return new Double(((BigDecimal) map.get(field)).doubleValue());	 
//	 }
//	 //把map中date 转换成相应格式 
//	 @SuppressWarnings("unchecked")
//	public Date getDate(HashMap map,String field,boolean istrue)
//	 {
//		 if(null==map.get(field)||"".equals(map.get(field).toString()))
//		 {
//			 return null;
//		 }
//		 else 
//		 {
//			 String format=GkzxCommon.DATEFORMAT;
//			 if (istrue)
//			{
//				format=GkzxCommon.DATETIMEFORMAT;
//			}
////			 SimpleDateFormat sdf=new SimpleDateFormat(format);//
//			 Date dt=(Date)map.get(field);
//			 return dt;
//		 }
//	 }
//	 /*
//	  * 描述：罪犯多个罪名
//	  * zhenghui
//	  */
//	 public void getQitaAnyou(String ddcid){
//		if(GkzxCommon.HNFANGANZMID.equals(ddcid)){
//			//其他罪名
//			System.out.println(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date())+"开始导入其他罪名...");
//			Map<String,String> mapstr = new HashMap<String,String>();
//			String qitaanyousql ="SELECT a.criminalid, a.cjaiid,code.sccontent AS qitaanyou FROM TBCRIMINAL_JUDGE_ACCUSAL_INFO a ,(SELECT * FROM TBSECURITY_CODE  WHERE sctid ='GB021') code  WHERE a.CECASE = code.scid(+) AND  a.cjaiid <> 1 "; 
//			 Connection conn = null;
//			 PreparedStatement ps = null;
//			 PreparedStatement ps1 = null;
//			 conn = JDBCUtils.getConn();
//			 ResultSet rst = null;
//			 try{
//				ps = conn.prepareStatement(qitaanyousql);
//			    rst = ps.executeQuery();
//				ArrayList dataAll = jsonTool.ResultSetToList(rst);
//				if(dataAll.size()>0) {
//					for(Object obj:dataAll){
//						Map map = (Map)obj;
//						String criminalid = StringUtil.returnString((String)map.get("criminalid"));
//						String cjaiid = StringUtil.returnString((String)map.get("cjaiid"));
//						String qitaanyou = StringUtil.returnString((String)map.get("qitaanyou"));
//						if(mapstr.containsKey(criminalid)){
//							qitaanyou =mapstr.get(criminalid)+"、"+qitaanyou;
//						}
//						mapstr.put(criminalid, qitaanyou);
//					}
//					Set set = mapstr.keySet();
//					if(0<set.size()){
//						for(Object s:set){
//							String qitaanyoustr = mapstr.get(s);
//							String sql = "update Tbcriminal_Basic_Info  set qitaanyou ='"+qitaanyoustr+"' where criminalid='"+s.toString()+"' ";
//							ps1 = conn.prepareStatement(sql);
//							int resultCnt = ps1.executeUpdate();
//							if (resultCnt <= 0) {
//				 				throw new Exception(sql);
//				 			}
//							JDBCUtils.closeResource(null, ps1, null);
//						}
//					}
//				}
//				System.out.println(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date())+"导入其他罪名结束！");
//			 }catch(Exception e){
//				 e.printStackTrace();
//			 }finally{
//				JDBCUtils.closeResource(null, ps1, null);
//				JDBCUtils.closeResource(conn, ps, rst);
//			}
//		}
//	}
//	//---
//	 
//		
//	    /**   
//	     * 此方法描述的是：局地进行数据交换的更新时间的取得
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: lxf   
//	     * @version: 2014-1-13 上午09:59:33   
//	     */  
//		public String getDdctypedByDdcid(String ddcid){
//			String ddctype = GkzxCommon.DBMS_IMEXPORT;
//			Map<String,String> mapstr = new HashMap<String,String>();
//			String ddcSql ="SELECT DDCTYPE  FROM DBMS_DATAS_CHEME_NEW a WHERE A.DDCID = '" + bean.getDdcid() + "'"; 
//			 Connection conn = null;
//			 PreparedStatement ps = null;
//			 ResultSet rst = null;
//			 try{
//				 conn = JDBCUtils.getConn();
//				ps = conn.prepareStatement(ddcSql);
//			    rst = ps.executeQuery();
//				ArrayList dataAll = jsonTool.ResultSetToList(rst);
//				if(dataAll.size()>0) {
//					Map map = (Map)dataAll.get(0);
//					ddctype = StringUtil.returnString((String)map.get("ddctype"));
//				}
//			 }catch(Exception e){
//				 e.printStackTrace();
//			 }finally{
//				JDBCUtils.closeResource(conn, ps, rst);
//			}
//			return ddctype;
//		}
//		public String shujujiaohuanAction(){
//			if(null!=bean.getDdcid()&&!"".equals(bean.getDdcid())){
//				String ddctype = getDdctypedByDdcid(bean.getDdcid());
//				if (ddctype.equals(GkzxCommon.DBMS_IMEXPORT)) {
//					jianyushujujiaohuanAction();
//				}else if (ddctype.equals(GkzxCommon.DBMS_IMEXPORT_JD)) {
//					judishujujiaohuanAction();
//				}
//			}
//			return SUCCESS;
//		}	
//		
//	    /**   
//	     * 此方法描述的是：局地进行数据交换的更新时间的取得
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: lxf   
//	     * @version: 2014-1-13 上午09:59:33   
//	     */   
//		public HashMap<String, Map> judishujujiaoUpdTime(Connection conn, String dcdid){
//			HashMap<String, Map> updTimeMap = new HashMap<String, Map>();
//			
//			String ddtid = "";
//			PreparedStatement ps = null;
//			ResultSet rst = null;
////			SimpleDateFormat sdf=new SimpleDateFormat(GkzxCommon.DATETIMEFORMATNOSPLIT2);
//			try {
//				String ddcSql ="SELECT DDTID, DDCEXPTIME  FROM DBMS_DATAS_UPDATE a WHERE A.DDCID = '" + dcdid + "'"; 
//				try {
//					ps = conn.prepareStatement(ddcSql);
//					rst = ps.executeQuery();
//					ArrayList dataAll = jsonTool.ResultSetToList(rst);
//					for( int i = 0; i < dataAll.size(); i++) {
//						Map map = (Map)dataAll.get(i);
//						ddtid = (String)map.get("ddtid");
//						if (!StringUtil.isNullOrEmpty(ddtid) && !updTimeMap.containsKey(ddtid)){
//							updTimeMap.put(ddtid, map);
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} catch (Exception e) {
//			} finally {
//				JDBCUtils.closeResource(null, ps, rst);
//			}
//		
//			return updTimeMap;
//		}
//		/**   
//		 * 此方法描述的是：保存局地进行数据交换的更新时间
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//		 * @author: lxf   
//		 * @version: 2014-1-20 上午09:59:33   
//		 */   
//		public boolean judishujujiaoSaveUpdTime(Connection conn, String ddcid, String ddtid, Date udpDate, String suid){
//			boolean isSucess = false;
//			
//			PreparedStatement ps = null;
//			SimpleDateFormat sdf=new SimpleDateFormat(GkzxCommon.DATETIMEFORMATNOSPLIT2);
//			try {
//				String udpddcSql ="UPDATE DBMS_DATAS_UPDATE A SET DDCEXPTIME = ?, UPDATETIME = ?, UPDATEMENDER = ? WHERE  A.DDCID = ? and  DDTID = ?"; 
//				String insertddcSql ="INSERT INTO DBMS_DATAS_UPDATE (DDCID,DDTID,DDCEXPTIME,UPDATEMENDER,UPDATETIME,CREATEMENDER,CREATETIME,REMARK,TORDERBY,DELFLG) " + 
//									 " VALUES (?,?,?,?,?,?,?,'','0','0' )"; 
//				try {
//					ps = conn.prepareStatement(udpddcSql);
//					ps.setTimestamp(1, new java.sql.Timestamp(udpDate.getTime()));
//					ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
//					ps.setString(3, suid);
//					ps.setString(4, ddcid);
//					ps.setString(5, ddtid);
//					int intCnt = ps.executeUpdate();
//					JDBCUtils.closeResource(null, ps, null);
//					if (intCnt < 1){
//						ps = conn.prepareStatement(insertddcSql);
//						ps.setString(1, ddcid);
//						ps.setString(2, ddtid);
//						ps.setTimestamp(3, new java.sql.Timestamp(udpDate.getTime()));
//						ps.setString(4, suid);
//						ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
//						ps.setString(6, suid);
//						ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
//						intCnt = ps.executeUpdate();
//						if (intCnt > 0) {
//							isSucess = true;
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} catch (Exception e) {
//			} finally {
//				JDBCUtils.closeResource(null, ps, null);
//			}
//			
//			return isSucess;
//		}
//		
//	    /**   
//	     * 此方法描述的是：局地进行数据交换
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: lxf   
//	     * @version: 2014-1-13 上午09:59:33   
//	     */   
//	    
//	public String judishujujiaohuanAction(){
//		TaskBean taskbean = new TaskBean();
//		boolean hasXinqiBiandong = false;
//		boolean hasLob = false;
//		String suid= GkzxCommon.USER_ADMIN;
//		String interchange = "500";
//		String orgInterchange = "500";
//		String strInterchange = jyconfig.getProperty("interchange");
//		if (!StringUtil.isNullOrEmpty(strInterchange)){
//			interchange = strInterchange;
//		}
//		orgInterchange = interchange;
//		if(this.session!=null){
//			this.session.put("taskbean", taskbean);
//			Object objSuid=this.session.get("suid");
//			if (objSuid != null) {
//				suid=objSuid.toString();
//			}
//		}
//		if(null!=bean.getDdcid()&&!"".equals(bean.getDdcid())){
//		    Connection daoruconn=null;
//		    Connection localconn=null;
//		    Connection conn=null;
//			PreparedStatement localps = null;
//			ResultSet localrs = null;
//			SimpleDateFormat sdf=new SimpleDateFormat(GkzxCommon.DATETIMEFORMATNOSPLIT2);
//		    try{
//		    	localconn = JDBCUtils.getConn();
//		    	HashMap<String, Map> ddtidUpdTimeMap = judishujujiaoUpdTime(localconn, bean.getDdcid());
//		    	String insertOnly = "1";
//		    	if(null!=bean.getInsertonly()&&!"".equals(bean.getInsertonly())){
//		    		insertOnly=bean.getInsertonly();
//		    	}
//		    	SimpleDateFormat sdf66=new SimpleDateFormat("yyyyMMdd");
//		    	int fk=Integer.valueOf(sdf66.format(new Date()))*10;
//		    	Map<String,List<Object>> daimamap=new HashMap<String,List<Object>>();//代码替换map
////		    	List<PreparedStatement> insertlist=new ArrayList<PreparedStatement>();//
////		    	List<PreparedStatement> updateList =new ArrayList<PreparedStatement>();
//		    	
//		    	String tobaseString="select * from DBMS_DATABASE_NEW a where a.ddid in (select todatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"+bean.getDdcid()+"')";
//		    	
//		    	localps = localconn.prepareStatement(tobaseString);
//		    	localrs = localps.executeQuery();
//				ArrayList dataAll = jsonTool.ResultSetToList(localrs);
//				
//		    	DbmsDatabaseNew tobsseDatabaseNew=null;
//		    	if(dataAll.size()>0) {
//					HashMap map =(HashMap)dataAll.get(0);
//		    		tobsseDatabaseNew= getDatabaseNew(map);
//		    	}
//		    	JDBCUtils.closeResource(null, localps, localrs);
//		    	
//		    	String daimasql="select * from DBMS_CODE_TYPE_NEW a where 1=1";
//		    	localps = localconn.prepareStatement(daimasql);
//		    	localrs = localps.executeQuery();
//				dataAll = jsonTool.ResultSetToList(localrs);
//				JDBCUtils.closeResource(null, localps, localrs);
//				PreparedStatement localps2 = null;
//				ResultSet localrs2 = null;
//				for(int i = 0; i < dataAll.size(); i++) {
//					HashMap map =(HashMap)dataAll.get(i);
//		    		String daimamingxisql="select * from DBMS_CODE_CHEME_NEW a where a.codetype='"+getStringByMap(map,"codetypeid")+"'";
//		    		
//		    		localps2 = localconn.prepareStatement(daimamingxisql);
//			    	localrs2 = localps2.executeQuery();
//			    	List<Object> daimamingxilist = getCodeChemeNew(jsonTool.ResultSetToList(localrs2));
//			    	JDBCUtils.closeResource(null, localps2, localrs2);
//		    		daimamap.put(getStringByMap(map,"codetypeid"), daimamingxilist);
//				}
//		    	String chaxuncon="";
//		    	if(null!=bean.getCondition()&&!"".equals(bean.getCondition())){
//		    		chaxuncon=bean.getCondition();
//		    	}
//		    	else if(null!=bean.getHiddencon()&&!"".equals(bean.getHiddencon())){
//		    		chaxuncon=bean.getHiddencon();
//		    	}
//		    	
//		    	daoruconn = dbmsNewDateExportSchemeManager.getConnection(tobsseDatabaseNew);
////				daoruconn.setAutoCommit(false);  
//				//判断方案操作数据库类型
//				String basesql="select * from DBMS_DATABASE_NEW a where a.ddid in (select fromdatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"+bean.getDdcid()+"')";
////				List<Object> baselist=baseDao.queryListObjectAll(basesql);
//				localps = localconn.prepareStatement(basesql);
//		    	localrs = localps.executeQuery();
//				dataAll = jsonTool.ResultSetToList(localrs);
//		    	
//				String basetype="";
//				DbmsDatabaseNew bs=null;
//				
//		    	if(dataAll.size()>0) {
//					HashMap map =(HashMap)dataAll.get(0);
//		    		bs= getDatabaseNew(map);
//		    		basetype=bs.getDatabasetype();
//		    	}
//		    	JDBCUtils.closeResource(null, localps, localrs);
//				
//				bean.setBasetype(basetype);
//				
//				HashMap<String, Integer> mapTableRecordCnt = new HashMap<String, Integer>();
//				String maintablesql="";
//				String maincon="";
//				String biaosql="select * from DBMS_DATAS_TABLE_NEW a where a.ddcid='"+bean.getDdcid()+"' and a.ddtid like '%EXP' order by  a.ddtismaintable,ddtorder";
//				localps = localconn.prepareStatement(biaosql);
//		    	localrs = localps.executeQuery();
//				dataAll = jsonTool.ResultSetToList(localrs);
//				List<Object> biaolist=getDatasTableNew(dataAll);
//				JDBCUtils.closeResource(null, localps, localrs);
//				//进度条用
//				int sum = 0;
//				int tem = 0;
//				
//				conn = dbmsNewDateExportSchemeManager.getConnection(bs);
//				
//				//计算条数开始
//				
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					if (GkzxCommon.XINGQIBIANDONG_TABLE.equalsIgnoreCase(thetable.getTotable())) {
//						hasXinqiBiandong = true;
//					}
//					//查询所有列
//					String chaxunsql=" count(*) ";
//					String daochusql="";
//					if("0".equals(thetable.getDdtismaintable())){
//						maincon=maincon+" a where 1=1";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							maincon=maincon+"  and a." + addconditionString;
//						}
//						if (ddtidUpdTimeMap.containsKey(thetable.getId().getDdtid())){
//							Map ddcexptimeMap = ddtidUpdTimeMap.get(thetable.getId().getDdtid());
//							Date ddcexptime = (Date)ddcexptimeMap.get("ddcexptime");
//							//取一天前的数据，防止有遗漏
//							ddcexptime  =  CalendarUtil.getSpecifiedDayBefore(ddcexptime);
//							maincon=maincon+"  and to_char(a.updatetime,'yyyyMMddHH24mmss') > '" + sdf.format(ddcexptime) + "'";
//						}
//						//额外查询条件追加结束2014-1-20
//						if(!"".equals(chaxuncon)){
//							maincon=maincon+" and "+chaxuncon;
//						}
//						maintablesql="select "+chaxunsql+" from "+thetable.getTablename()+maincon;
//						daochusql=maintablesql;
//					}else{
//						daochusql="select "+chaxunsql+" from "+thetable.getTablename()+" b where 1=1 ";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							daochusql=daochusql+" and b." + addconditionString;
//						}
//						if (ddtidUpdTimeMap.containsKey(thetable.getId().getDdtid())){
//							Map ddcexptimeMap = ddtidUpdTimeMap.get(thetable.getId().getDdtid());
//							Date ddcexptime = (Date)ddcexptimeMap.get("ddcexptime");
//							//取一天前的数据，防止有遗漏
//							ddcexptime  =  CalendarUtil.getSpecifiedDayBefore(ddcexptime);
//							daochusql=daochusql+"  and to_char(b.updatetime,'yyyyMMddHH24mmss') > '" + sdf.format(ddcexptime) + "'";
//						}
//						//额外查询条件追加结束 2014-1-20
//						
//						//其他表根据数据关系生成查询条件
//						if(null!=thetable.getShujuguanxi()&&!"".equals(thetable.getShujuguanxi())){
//							String[]  temparr=thetable.getShujuguanxi().split(",");
//							String tempcon="";
//							for(String str:temparr){
//								String[] temp2=str.split("=");
//								if("".equals(tempcon)){
//									tempcon="a."+temp2[0]+"=b."+temp2[1];
//								}else{
//									tempcon=" and a."+temp2[0]+"=b."+temp2[1];
//								}
//							}
//							
//							if(!"".equals(tempcon)){
//								int indexFrom = maintablesql.indexOf("from");
//								String strReplaceMaintablesql = "select 'X' " + maintablesql.substring(indexFrom);
//								daochusql=daochusql+" and exists ("+strReplaceMaintablesql+" and "+tempcon+" )";
//							}
//						}
//					}
//					if (!StringUtil.isNullOrEmpty(daochusql)) {
//						int intTableRecordCnt = this.getSumCount(conn, daochusql);
//						sum += intTableRecordCnt;
//						mapTableRecordCnt.put(thetable.getTablename(), Integer.valueOf(intTableRecordCnt));
//					}
//				}				
//				
//				//计算条数结束
//				
//				taskbean.setSum(sum);
//				if(this.session!=null){
//					this.session.put("taskbean", taskbean);
//				}
//				
//				maintablesql="";
//				maincon="";
//				Date udpDate = new Date();
//				for(Object biaoobj:biaolist){
//					DbmsDatasTableNew thetable=(DbmsDatasTableNew)biaoobj;
//					//查询所有列
//					String chaxunsql="";
//					String daochusql="";
//					String liesql="select * from DBMS_DATAS_CHEME_DETAIL_NEW a where a.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"'";
//					localps = localconn.prepareStatement(liesql);
//			    	localrs = localps.executeQuery();
//					dataAll = jsonTool.ResultSetToList(localrs);
//					List<Object> lielist=getDatasChemeDetailNew(dataAll);
//					JDBCUtils.closeResource(null, localps, localrs);
//					
//					boolean isFirst = true;
//					String strAsc = "";
//					String strDesc = "";
//					String strColumn = "";
//					ArrayList<String> lstColumnsArrayList = new ArrayList<String>();
//					String strorgBianhao = "";
//					//GB002:410600:河南省鹤壁市
//					HashMap<String, HashMap<String, TbsecurityCode>> mapCodeScidRemarkHashMap = new HashMap<String, HashMap<String, TbsecurityCode>>();
//					ArrayList<String> strCodeList = new ArrayList<String>();
//					//原字段名->代码code:关联字段名：单位类型；如：dbmx->GB002:dbbh:100000016
//					HashMap<String,ArrayList<String>> mapColumnType = new HashMap<String,ArrayList<String>>();
//					hasLob = false;
//					interchange = orgInterchange;
//					for(Object obj:lielist){
//						DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//						String strColumnType = detail.getDcdfromcloumnstype();
////						if (!hasLob && (GkzxCommon.Clob.equalsIgnoreCase(strColumnType) || GkzxCommon.Blob.equalsIgnoreCase(strColumnType))) {
////							hasLob = true;
////							interchange = "1";
////						}
//						strColumn = detail.getDcdfromcolumns();
//						if (strColumn != null && !"".equals(strColumn)){
//							if (strColumn.indexOf("@") > -1){
//								//该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
//								String[] strTmpColumnStrings = strColumn.split("@");
//								strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//								if (strTmpColumnStrings.length == 3){
//									ArrayList<String> lstTypeColumnDanweiArrayList = new ArrayList<String>();
//									lstTypeColumnDanweiArrayList.add(strTmpColumnStrings[1].trim());
//									strCodeList.add(strTmpColumnStrings[1].trim());
//									strorgBianhao = strTmpColumnStrings[0];
//									if (strorgBianhao.indexOf(":") > 0) {
//										//1000001:bh
//										String[] strDanweiFromColumn = strorgBianhao.split(":");
//										strorgBianhao = strDanweiFromColumn[1];
//										lstTypeColumnDanweiArrayList.add(strorgBianhao);
//										lstTypeColumnDanweiArrayList.add(strDanweiFromColumn[0]);
//									} else {
//										//bh
//										lstTypeColumnDanweiArrayList.add(strorgBianhao);
//									}
//									//如：dbmx->GB002:dbbh:100000016或者//如：dbmx->GB002:dbbh
//									mapColumnType.put(strColumn, lstTypeColumnDanweiArrayList);
//								}
//							}
//							
//							if (lstColumnsArrayList.indexOf(strColumn) < 0){
//								lstColumnsArrayList.add(strColumn);
//								if("".equals(chaxunsql)){
//									chaxunsql=strColumn;
//								}else{
//									chaxunsql=chaxunsql+","+strColumn;
//								}
//							
//								//如果是主键
//								if("1".equals(detail.getDcdifpkey())){
//									int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
//									if (intIndex > -1) {
//										strColumn = strColumn.toLowerCase().substring(0, intIndex);
//									}
//									if (isFirst) {
//										strAsc = strColumn + " ASC";
//										strDesc = strColumn + " DESC";
//										isFirst = false;
//									} else {
//										strAsc = strAsc + ", " + strColumn + " ASC";
//										strDesc = strDesc + ", " + strColumn + " DESC";
//									}
//								}
//							}
//						}
//					}
//					mapCodeScidRemarkHashMap= getLocalDbCodeScidRemark(localconn, strCodeList);
//					if("0".equals(thetable.getDdtismaintable())){
//						maincon=maincon+" a where 1=1";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							maincon=maincon+"  and a." + addconditionString;
//						}
//						if (ddtidUpdTimeMap.containsKey(thetable.getId().getDdtid())){
//							Map ddcexptimeMap = ddtidUpdTimeMap.get(thetable.getId().getDdtid());
//							Date ddcexptime = (Date)ddcexptimeMap.get("ddcexptime");
//							//取一天前的数据，防止有遗漏
//							ddcexptime  =  CalendarUtil.getSpecifiedDayBefore(ddcexptime);
//							maincon=maincon+"  and to_char(a.updatetime,'yyyyMMddHH24mmss') > '" + sdf.format(ddcexptime) + "'";
//						}
//						//额外查询条件追加结束2014-1-20
//						if(GkzxCommon.HNFANGANPCID.equals(bean.getDdcid())){
//							chaxuncon = " a.bh in(select bh from  da_jbxx where zybz!='3') and a.bdlb='01' ";
//						}
//						if(!"".equals(chaxuncon)){
//							maincon=maincon+" and "+chaxuncon;
//						}
//						maintablesql=chaxunsql+" from "+thetable.getTablename()+maincon;
//						daochusql=maintablesql;
//					}else{
//						daochusql=chaxunsql+" from "+thetable.getTablename()+" b where 1=1 ";
//						//额外查询条件追加开始 2014-1-20
//						String addconditionString = thetable.getAddcondition();
//						if (!StringUtil.isNullOrEmpty(addconditionString)) {
//							daochusql=daochusql+" and b." + addconditionString;
//						}
//						if (ddtidUpdTimeMap.containsKey(thetable.getId().getDdtid())){
//							Map ddcexptimeMap = ddtidUpdTimeMap.get(thetable.getId().getDdtid());
//							Date ddcexptime = (Date)ddcexptimeMap.get("ddcexptime");
//							//取一天前的数据，防止有遗漏
//							ddcexptime  =  CalendarUtil.getSpecifiedDayBefore(ddcexptime);
//							daochusql=daochusql+"  and to_char(b.updatetime,'yyyyMMddHH24mmss') > '" + sdf.format(ddcexptime) + "'";
//						}
//						//额外查询条件追加结束 2014-1-20
//						//其他表根据数据关系生成查询条件
//						if(null!=thetable.getShujuguanxi()&&!"".equals(thetable.getShujuguanxi())){
//							String[]  temparr=thetable.getShujuguanxi().split(",");
//							String tempcon="";
//							for(String str:temparr){
//								String[] temp2=str.split("=");
//								if("".equals(tempcon)){
//									tempcon="a."+temp2[0]+"=b."+temp2[1];
//								}else{
//									tempcon=" and a."+temp2[0]+"=b."+temp2[1];
//								}
//							}
//							
//							if(!"".equals(tempcon)){
//								daochusql=daochusql+" and exists ( select "+maintablesql+" and "+tempcon+" )";
//							}
//						}
//					}
//					if(!"".equals(daochusql)){
//						ResultSet rs=null;
//						Statement stmt=null;
//						PreparedStatement prestmtPreparedStatement1=null;
//						PreparedStatement prestmtPreparedStatement2=null;
//						String strFromTable = thetable.getTablename();
//						int recordCnt = 0;
//						if (mapTableRecordCnt.get(strFromTable)!=null){
//							recordCnt = mapTableRecordCnt.get(strFromTable).intValue();
//						}
//						for(int i = 0; i < recordCnt;) {
//							
//							try {
//								stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
//								String onedaochusql = "select top " + interchange + " * from (select top " + String.valueOf(i+Integer.parseInt(interchange)) + " " + daochusql + 
//													  " order by " + strAsc + " ) x order by " + strDesc;
//								if(basetype.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)){
//									onedaochusql = "select * from (select rownum no, " + daochusql + ") where no > " + String.valueOf(i) + " and no <= " + String.valueOf(i+Integer.parseInt(interchange));
//								}
//								
//								//查询数据是否存在开始///////////////////////////////////////////////////////
//								ArrayList<String> keyValueArrayList = new ArrayList<String>();
//								rs = stmt.executeQuery(onedaochusql); 
//								if(GkzxCommon.ONE.equals(insertOnly)) {
//									String strSelectColumn = "";
//									String strSelectWhere = "";
//									boolean isFirstSelect = true;
//									while (rs.next()) {
//										//遍历获取数据
//										int zhujianindex=1;
//										HashMap<String, Object> valuemapMap=new HashMap<String, Object>();
//										//原有column对应值
//										String wheresqlString="";
//										for(Object obj:lielist){
//											//开始进行数据交换
//											DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//											//如果是主键
//				            				if("1".equals(detail.getDcdifpkey())){
//				            					strColumn = detail.getDcdfromcolumns();
//				            					if (strColumn != null && !"".equals(strColumn)){
//				            						if (strColumn.indexOf("@") > -1){
//				            							//该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
//				            							String[] strTmpColumnStrings = strColumn.split("@");
//				            							strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//				            						}
//				            					}
//				            					String ttvalue=this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(), strColumn, rs);
//				            					
//				            					if (ttvalue !=null && !"".equals(ttvalue)){
//				            						ttvalue = ttvalue.trim();
//				            					}
//				            					if(null==ttvalue){
//				            						ttvalue="";
//				            					}
//				            					valuemapMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//				            					//循环第一次的时候，获得查询列。
//				            					if (isFirstSelect) {
//				            						if("".equals(strSelectColumn)){
//				            							
//				            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//					            								strSelectColumn = detail.getDcdtocolumns() + " ";
//					            							} else {
//					            								strSelectColumn = detail.getDcdtocolumns() + " ";
//					            							}
//					            						}
//					            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("DATE".equals(detail.getDcdtocolumnstype())){
//					            								strSelectColumn= " to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') "+" ";
//					            							}else {
//					            								strSelectColumn = detail.getDcdtocolumns() + " ";
//					            							}
//					            						}
//				            						}else{
//				            							
//				            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//					            								strSelectColumn = strSelectColumn + ", " + detail.getDcdtocolumns();
//					            							} else {
//					            								strSelectColumn = strSelectColumn + ", " + detail.getDcdtocolumns();
//					            							}
//					            						}
//					            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())){
//					            							if("DATE".equals(detail.getDcdtocolumnstype())){
//					            								strSelectColumn=strSelectColumn + ", " + " to_char(" + detail.getDcdtocolumns()+",'yyyyMMdd') as "+ detail.getDcdtocolumns() + " ";
//					            							}else {
//					            								strSelectColumn = strSelectColumn + ", " + detail.getDcdtocolumns();
//					            							}
//					            						}
//				            						}
//				            					}
//				            					if("".equals(wheresqlString)){
//			            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())&&!StringUtil.isNullOrEmpty(ttvalue)){
//				            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//				            								wheresqlString= detail.getDcdtocolumns()+" = "+ttvalue+" ";
//				            							} else {
//				            								wheresqlString= detail.getDcdtocolumns()+" = "+ttvalue+" ";
//				            							}
//				            						}
//				            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())&&!StringUtil.isNullOrEmpty(ttvalue)){
//				            							if("DATE".equals(detail.getDcdtocolumnstype())){
//				            								//变更2014-1-20
//				            								wheresqlString= " to_char(" + detail.getDcdtocolumns()+",'yyyy-MM-dd HH24:mm:ss') = '"+ttvalue+"' ";
//				            							}else {
//				            								//变更2014-1-20
//				            								wheresqlString= detail.getDcdtocolumns()+" = '"+ttvalue+"' ";
//				            							}
//				            						}
//				            					}else{
//			            							if("Microsoft SQL Server".equals(tobsseDatabaseNew.getDatabasetype())&&!StringUtil.isNullOrEmpty(ttvalue)){
//				            							if("datetime".equalsIgnoreCase(detail.getDcdtocolumnstype())){
//				            								wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = "+ttvalue+" ";
//				            							} else {
//				            								wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = "+ttvalue+" ";
//				            							}
//				            						}
//				            						if("Oracle".equals(tobsseDatabaseNew.getDatabasetype())&&!StringUtil.isNullOrEmpty(ttvalue)){
//				            							if("DATE".equals(detail.getDcdtocolumnstype())){
//				            								//变更2014-1-20
//				            								wheresqlString=wheresqlString+" and "+" to_char(" + detail.getDcdtocolumns()+",'yyyy-MM-dd HH24:mm:ss') = '"+ttvalue+"' ";
//				            							} else {
//				            								//变更2014-1-20
//				            								wheresqlString=wheresqlString+" and "+ detail.getDcdtocolumns()+" = '"+ttvalue+"' ";
//				            							}
//				            						}
//				            					}
//				            				}
//										}
//										List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
//												valuemapMap.entrySet());
//
//										// 排序
//										Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//											public int compare(Map.Entry<String, Object> o1,
//													Map.Entry<String, Object> o2) {
//												return (o1.getKey()).toString().compareTo(o2.getKey());
//											}
//										});
//										
//										String strKeyValueString = "";
//										for (int t = 0; t < infoIds.size(); t++) {
//											Entry<String,Object> ent=infoIds.get(t);
//											strKeyValueString += ent.getValue();
//											
//										}
//										if (keyValueArrayList.indexOf(strKeyValueString) < 0) {
//											keyValueArrayList.add(strKeyValueString);
//										}
//										isFirstSelect = false;
//										//构造查询条件
//										if("".equals(strSelectWhere)){
//											if (!StringUtil.isNullOrEmpty(wheresqlString)) {
//												strSelectWhere=" ( " + wheresqlString + ")";
//											}
//		            					}else{
//		            						if (!StringUtil.isNullOrEmpty(wheresqlString)) {
//		            							strSelectWhere=strSelectWhere+" or "+ " ( " + wheresqlString + ")";
//		            						}
//		            					}
//										
//									}
//									String sql="select " + strSelectColumn + " from " + thetable.getTotable() ;
//									if (!StringUtil.isNullOrEmpty(strSelectWhere)) {
//										sql += " where " + strSelectWhere;
//									}
//									try {
//										localps = daoruconn.prepareStatement(sql);
//										localrs = localps.executeQuery();
//										List<Object> objList = jsonTool.ResultSetToList(localrs);
//										JDBCUtils.closeResource(null, localps, localrs);
//
//										for (int m = 0; m < objList.size(); m++) {
//											HashMap map = (HashMap)objList.get(m);
//											List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
//													map.entrySet());
//
//											// 排序
//											Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//												public int compare(Map.Entry<String, Object> o1,
//														Map.Entry<String, Object> o2) {
//													return (o1.getKey()).toString().compareTo(o2.getKey());
//												}
//											});
//											String strKeyValueString = "";
//											for (int t = 0; t < infoIds.size(); t++) {
//												Entry<String,Object> ent=infoIds.get(t);
//												strKeyValueString += ent.getValue();
//												
//											}
//											int n = keyValueArrayList.indexOf(strKeyValueString);
//											if (n > -1) {
//												keyValueArrayList.remove(n);
//											}
//										}
//									
//									} catch (Exception e) {
//										System.out.println(sql);
//										e.printStackTrace();
//									} finally {
//										JDBCUtils.closeResource(null, localps, localrs);
//									}
//								}
//								//查询数据是否存在结束///////////////////////////////////////////////////////
//								rs.beforeFirst();
//								while (rs.next()) {
//									tem++;
//									taskbean.setCounter(tem);
//									if(this.session!=null){
//										this.session.put("taskbean", taskbean);
//									}
//									//遍历获取数据
//									int zhujianindex=1;
//									Map<String, String> valuemapMap=new HashMap<String, String>();
//									Map<String, Object> keyValueMap=new HashMap<String, Object>();
//									//原有column对应值
//									Map<String, String> orgValuemapMap=new HashMap<String, String>();
//									for(Object obj:lielist){
//										//开始进行数据交换
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										strColumn = detail.getDcdfromcolumns();
//										if (strColumn != null && !"".equals(strColumn)){
//											if (strColumn.indexOf("@") > -1){
//												//该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
//												String[] strTmpColumnStrings = strColumn.split("@");
//												strColumn = strTmpColumnStrings[strTmpColumnStrings.length -1].trim();
//											}
//										}
//										String ttvalue=this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(), strColumn, rs);
//										
//										if (ttvalue !=null && !"".equals(ttvalue)){
//											ttvalue = ttvalue.trim();
//										}
//										if(null==ttvalue){
//											ttvalue="";
//										}
//										valuemapMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//										if("1".equals(detail.getDcdifpkey())){
//											keyValueMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
//										}
//										if(!StringUtil.isNullOrEmpty(strColumn)) {
//											orgValuemapMap.put(strColumn.toLowerCase(), ttvalue);
//										}
//									}
//									//新增的时候才进行判断
//									if(GkzxCommon.ONE.equals(insertOnly)) {
//										List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
//												keyValueMap.entrySet());
//
//										// 排序
//										Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//											public int compare(Map.Entry<String, Object> o1,
//													Map.Entry<String, Object> o2) {
//												return (o1.getKey()).toString().compareTo(o2.getKey());
//											}
//										});
//										String strKeyValueString = "";
//										for (int t = 0; t < infoIds.size(); t++) {
//											Entry<String,Object> ent=infoIds.get(t);
//											strKeyValueString += ent.getValue();
//											
//										}
//										if (keyValueArrayList.indexOf(strKeyValueString) < 0){
//											continue;
//										}
//									}
//									//遍历插入数据
//									String insertaa="insert into "+thetable.getTotable()+" (";
//			            			String updateaa="update "+thetable.getTotable()+" set ";
//			            			String updatesqlString="";
//			            			String wheresqlString="";
//				            		String liearr="";
//				            		String zhiarr="";
//				            	
//									for(Object obj:lielist){
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										String daovalue="?";
//										//如果是主键
//			            				if("1".equals(detail.getDcdifpkey())){
//			            					if("".equals(wheresqlString)){
//			            						wheresqlString=detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}else{
//			            						wheresqlString=wheresqlString+" and "+detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}
//			            				}
//			            				else{
//			            					zhujianindex++;
//			            					if("".equals(updatesqlString)){
//			            						updatesqlString=detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}else{
//			            						updatesqlString=updatesqlString+" , "+detail.getDcdtocolumns()+" = "+daovalue+" ";
//			            					}
//			            				}
//			            				if("".equals(zhiarr)){
//		            						zhiarr=daovalue;
//		            					}else{
//		            						zhiarr=zhiarr+","+daovalue+"";
//		            					}
//			            				if("".equals(liearr)){
//			            					liearr=detail.getDcdtocolumns();
//		            					}else{
//		            						liearr=liearr+","+detail.getDcdtocolumns()+"";
//		            					}
//									}
//									insertaa=insertaa+liearr+" ) values ("+zhiarr+")";
//				            		updateaa=updateaa+updatesqlString+" where "+wheresqlString;
//				            		prestmtPreparedStatement1=daoruconn.prepareStatement(insertaa);
//				            		prestmtPreparedStatement2=daoruconn.prepareStatement(updateaa);
////				            		//更新语句必须带条件,否则不能进行更新
////				            		if(!"".equals(wheresqlString)){
////				            			updateList.add(prestmtPreparedStatement2);
////				            		}else{
////				            			updateList.add(null);
////				            		}
////				            		insertlist.add(prestmtPreparedStatement1);
//				            		int insertindex=1;
//				            		Reader clobReader = null;
//				            		for(Object obj:lielist){
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										String daovalue=valuemapMap.get(detail.getDcdtocolumns().toLowerCase());
//										if(null==daovalue||"".equals(daovalue)){
//											daovalue="";
//										}
//										daovalue=this.jiexiValue(suid,daovalue, detail, fk, valuemapMap, null, daimamap, tobsseDatabaseNew, "jiaohuan",mapColumnType,mapCodeScidRemarkHashMap,orgValuemapMap);
//										daovalue=this.geshizhuanhuan(tobsseDatabaseNew.getDatabasetype(), detail.getDcdtocolumnstype(), daovalue);
//										//clob类型在此处set值是由于addPreparedStatement方法set值后在prestmtPreparedStatement.executeupdate前把Reader关闭了.
//										if(GkzxCommon.Clob.equalsIgnoreCase(detail.getDcdtocolumnstype())){
//						       		    	clobReader = new StringReader(daovalue.toString());
//						    				prestmtPreparedStatement1.setCharacterStream(insertindex, clobReader, daovalue.toString().length());//大字段
//										} else {
//											this.addPreparedStatement(prestmtPreparedStatement1, detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(), insertindex, daovalue);
//										}
//										insertindex++;
//				            		}
//				            		int updateindex=1;
//				            		for(Object obj:lielist){
//										DbmsDatasChemeDetailNew detail=(DbmsDatasChemeDetailNew)obj;
//										String daovalue=valuemapMap.get(detail.getDcdtocolumns().toLowerCase());
//										if(null==daovalue||"".equals(daovalue)){
//											daovalue="";
//										}
//										daovalue=this.jiexiValue(suid,daovalue, detail, fk, valuemapMap, null, daimamap, tobsseDatabaseNew, "jiaohuan",mapColumnType,mapCodeScidRemarkHashMap,orgValuemapMap);
//										daovalue=this.geshizhuanhuan(tobsseDatabaseNew.getDatabasetype(), detail.getDcdtocolumnstype(), daovalue);
//										if("1".equals(detail.getDcdifpkey())){
//							       		    if(GkzxCommon.Clob.equalsIgnoreCase(detail.getDcdtocolumnstype())){
//							       		    	clobReader = new StringReader(daovalue.toString());
//							    				prestmtPreparedStatement2.setCharacterStream(zhujianindex, clobReader, daovalue.toString().length());
//							    			} else {
//							    				this.addPreparedStatement(prestmtPreparedStatement2, detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(), zhujianindex, daovalue);
//							    			}
//											zhujianindex++;
//										}
//			            				else{
//			            					if(GkzxCommon.Clob.equalsIgnoreCase(detail.getDcdtocolumnstype())){
//			            						clobReader = new StringReader(daovalue.toString());
//			            						prestmtPreparedStatement2.setCharacterStream(updateindex, clobReader, daovalue.toString().length());//大字段
//			            					} else {
//			            						this.addPreparedStatement(prestmtPreparedStatement2, detail.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(), updateindex, daovalue);
//			            					}
//							    			updateindex++;
//			            				}
//									}
//				            		try{
//				            			try {
//				            				if(GkzxCommon.ONE.equals(insertOnly)) {
//				            					//新增导入
//				            					prestmtPreparedStatement1.executeUpdate();
//				            				} else {
//				            					int result=prestmtPreparedStatement2.executeUpdate();
//				            					if (result==0) {
//				            						prestmtPreparedStatement1.executeUpdate();
//				            					}
//				            				}
//				            				if(clobReader!=null) {
//				            					clobReader.close();
//				            				}
//										} catch (Exception e) {
//											e.printStackTrace();
//											logger.error(MessageFormat.format(e.getMessage(), "judishujujiaohuanAction"));
//										}
//				            		}catch (Exception e) {
//				            			e.printStackTrace();
//				            			logger.error(MessageFormat.format(e.getMessage(), "judishujujiaohuanAction"));
//									}finally{
//										if(null!=prestmtPreparedStatement2){
//											prestmtPreparedStatement2.close();
//											prestmtPreparedStatement2 = null;
//										}
//										if(null!=prestmtPreparedStatement1){
//											prestmtPreparedStatement1.close();
//											prestmtPreparedStatement1 = null;
//										}
//										Runtime run = Runtime.getRuntime();
//										long max = run.maxMemory();
//										long total = run.totalMemory();
//										long free = run.freeMemory();
//										long usable = max - total + free;
////										System.out.println("最大内存 = " + max / 1024 / 1024);
////										System.out.println("最大可用内存 = " + usable / 1024	/ 1024);
////										System.out.println("最大可用内存占比例 = "+ new BigDecimal(usable * 100).divide(
////														new BigDecimal(max), 2,	BigDecimal.ROUND_HALF_UP).floatValue() + "%");
//									}
//								}
//							}catch (Exception e) {
//								e.printStackTrace();
//							} finally {
////								try {
////									if (rs != null){
////										rs.close();
////									}
////									if (stmt != null){
////										stmt.close();
////									}
////								} catch (Exception e2) {
////								}
//								JDBCUtils.closeResource(null, stmt, rs);
//							}
//							i = i + Integer.parseInt(interchange);
//						}
//						judishujujiaoSaveUpdTime(localconn, bean.getDdcid(), thetable.getId().getDdtid(), udpDate, suid);
//					}
//				}
//				
//				if (hasXinqiBiandong){
//					xingqibiandong(daoruconn);
//				}
////				daoruconn.commit();
//			}catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				JDBCUtils.closeResource(localconn, localps, localrs);
//				JDBCUtils.closeResource(conn, null, null);
//				if(null!=daoruconn){
//					try {
////						daoruconn.setAutoCommit(true);
//						daoruconn.close();
//						getQitaAnyou(bean.getDdcid());
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		return SUCCESS;
//	}	

}
