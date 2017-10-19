package com.sinog2c.service.impl.dbmsnew;

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
import java.net.InetSocketAddress;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.net.ftp.FtpClient;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.dbmsnew.DbmsDaochuListInfoMapper;
import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;
import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.dbmsnew.ShuJuTempTable;
import com.sinog2c.model.dbmsnew.TaskBean;
import com.sinog2c.model.dbmsnew.TempConBean;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.Base64Util;
import com.sinog2c.util.common.DESUtil;
import com.sinog2c.util.common.DataBaseDateTransfer;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.FtpUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.JdbcConnUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.common.TxtUtil;
import com.sinog2c.util.common.ZipUtil;
import com.sinog2c.util.dbms.DocZipUtil;

/**
 * 数据交换服务;<br/>
 * SCOPE_PROTOTYPE 是每次从Spring获取都会得到一个新生成的对象.
 */
/**
 * 郑重提醒,因为有共享变量,所以Service实现只能每次New
 */
@Service("dbmsNewDataExportService")
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DbmsNewDataExportServiceImpl extends ServiceImplBase implements DbmsNewDataExportService {

	@Autowired
	private DbmsDaochuListInfoMapper daochuListInfoMapper;
	private static final Logger logger = LoggerFactory.getLogger(DbmsNewDataExportServiceImpl.class);

	/**
	 * 国科政信,标准日期时间格式
	 */
	private static SimpleDateFormat sinog2cDateFormater = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
	private static SimpleDateFormat dateFormaterTimestamp = new SimpleDateFormat(GkzxCommon.DATETIMEFORMATNOSPLIT);

	// 缓存
	private static Properties _jyconfig = null;
	/**
	 * 这个是什么?
	 */
	private static String strJgxt = "jgxt";
	/**
	 * 添加列的数组?
	 */
	private List<String> addcolumnslst = new ArrayList<String>();


	public DbmsNewDataExportServiceImpl getNewInstance() {
		DbmsNewDataExportServiceImpl dbmsNewDataExportService = new DbmsNewDataExportServiceImpl();
		//
		dbmsNewDataExportService.daochuListInfoMapper = this.daochuListInfoMapper;
		return dbmsNewDataExportService;
	}
	/**
	 * main 单元测试方法
	 */
	public static void main(String[] args) {
		DbmsNewDataExportServiceImpl dbmsNewDataExportService = new DbmsNewDataExportServiceImpl();
		dbmsNewDataExportService.autoPrisonDataExchange("3102");
	}

	/**
	 * 根据配置文件/以及方案配置信息,自动执行监狱数据交换
	 */
	public void autoPrisonDataExchange() {
		ArrayList<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		String provincecode = getJyConfigValue("provincecode", "");
		String tianjinconde = getJyConfigValue(GkzxCommon.TIANJIN_CODE, "");
		try {
			// 该监狱对应的方案
			String strSql = " select o.orgid from TBSYS_ORGINFO o "
						  + " where o.orgid = '" + provincecode+ "' "
						  + " or o.porgid = '" + provincecode+  "' "
						  + " order by o.orgid asc ";
			objList = parseSQL2List(strSql);
		} catch (Exception e) {
			// 抛出异常,不继续往下执行
			RuntimeException ex = new RuntimeException("获取监狱组织机构信息出错!", e);
			throw ex;
		}
		//
		if ((!StringNumberUtil.belongTo(provincecode, tianjinconde))) {
			// 循环省内单位交换数据
			for (int i = 0; i < objList.size(); i++) {
				Map<String, Object> map =  objList.get(i);
				String sdid = StringNumberUtil.getStringByMap(map, "orgid");
				autoPrisonDataExchange(sdid); // 
			}
		} else {
			autoPrisonDataExchange(provincecode);
		}
	}

	/**
	 * 自动执行数据交换,是一个包装器
	 * @param departid 部门ID,是监狱ID或者省局ID
	 * @return
	 */
	public void autoCaseDataExchange() {
		log( new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + "案件数据实时同步开始....");
		
		ArrayList<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		try {
			// 该监狱对应的方案(int1=1是实时交换方案，int2=1代表处理中)超过12小时的处理中数据会重新处理
			String strSql = "select * from DBMS_DATAS_CHEME_NEW t,TBSYS_ORGINFO o "
						  + " where ((t.int1 = 1 and t.int2 != 1) or (t.int1 = 1  and t.int2 = 1 and sysdate > f_isdate(t.remark1,'yyyymmddhh24miss') +0.5)) "
						  + " and t.sdid = o.orgid "
						  + " order by t.sdid,t.ddcid";
			objList = parseSQL2List(strSql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 未获取到值...
		Connection conn =  null;
		PreparedStatement localps = null;
		ResultSet localrs = null;
		// TODO 应该使用迭代.
		for (int i = 0; i < objList.size(); i++) {
			Map<String, Object> map = objList.get(i);
			caseDataExchange(conn,localps,localrs,map);
			
			// 调用存储过程分发数据
			try {
				conn =  JdbcConnUtil.getConn();
				log("开始调用数据交换存储过程。。。");	
				boolean rest = callInterchangeProcedure(conn, StringNumberUtil.getStringByMap(map, "sdid"),"");
				log("数据交换存储过程执行" + (rest ? "成功" : "失败"));
			} catch (Exception e) {
				log("调用存储过程失败!");
				e.printStackTrace();
			} finally {
				JdbcConnUtil.close(conn);
				conn = null;
			}
		}
		log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + "案件数据实时同步结束....");
	}
	
	@Override
	public void caseDataExchange(Connection conn,PreparedStatement localps,ResultSet localrs,Map<String,Object> map){
		try {
			conn =  JdbcConnUtil.getConn();
			String basesql = " update DBMS_DATAS_CHEME_NEW x  set x.int2 = 1,x.remark1=to_char(sysdate,'yyyymmddhh24miss') where x.ddcid=?";
			localps = conn.prepareStatement(basesql);
			localps.setString(1, StringNumberUtil.getStringByMap(map, "ddcid"));
			localps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.closeResource(conn, localps, localrs);
		}
		// 每次生成一个
		DbmsNewDataExportServiceImpl dataExport = new DbmsNewDataExportServiceImpl();
		DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
		bean.setDdcid(StringNumberUtil.getStringByMap(map, "ddcid"));
		bean.setInsertonly(StringNumberUtil.getStringByMap(map, "autoruninsertonly"));
		bean.setCondition(StringNumberUtil.getStringByMap(map, "autoruncondition"));
		bean.setHiddencon(null);

		dataExport.prisonDataExchange(bean);
		log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + " 方案:" + StringNumberUtil.getStringByMap(map, "ddcid") + "数据交换完毕！");
		// 清理内存
		//
		bean = null;
		dataExport = null;
		//
		System.gc();
		try {
			conn =  JdbcConnUtil.getConn();
			String basesql = " update DBMS_DATAS_CHEME_NEW x  set x.int2 = 0,x.remark1=to_char(sysdate,'yyyymmddhh24miss') where x.ddcid=? ";
			localps = conn.prepareStatement(basesql);
			localps.setString(1, StringNumberUtil.getStringByMap(map, "ddcid"));
			localps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.closeResource(conn, localps, localrs);
		}
		
	}

	
	/**
	 * 自动执行数据交换,是一个包装器
	 * @param departid 部门ID,是监狱ID或者省局ID
	 * @return
	 */
	public boolean autoPrisonDataExchange(String departid) {
		log("部门ID为" + departid + "," + "数据实时同步开始....");
		boolean isProvinceCheme = false;
		if (StringNumberUtil.isEmpty(departid)) {
			log("部门ID为空，未进行数据交换！");
			log("数据实时同步结束....");
			// 这里需要返回
			return isProvinceCheme;
		}
		//
		String provincecode = getJyConfigValue("provincecode", "");
		
		ArrayList<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		try {
			// 该监狱对应的方案
			String strSql = "select * from DBMS_DATAS_CHEME_NEW t,TBSYS_ORGINFO o "
						  + " where t.AUTORUNORDER is not null "
						  + " and t.sdid = o.orgid "
					//	  + " and o.unitlevel = '3' "
						  + " and t.sdid = '" + departid + "'"
						  + " order by t.sdid, to_number(t.AUTORUNORDER) asc";
			objList = parseSQL2List(strSql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 未获取到值...
		if (objList.isEmpty()) {
			try {
				// 该监狱对应的方案
				String strSql = "select * from DBMS_DATAS_CHEME_NEW t,TBSYS_ORGINFO o "
							  + " where t.AUTORUNORDER is not null"
							  + " and t.sdid = o.orgid"
							  + " and o.unitlevel = '2'"
							  + " and t.sdid = '" + provincecode + "'"
							  + " order by t.sdid, to_number(t.AUTORUNORDER) asc";
				objList = parseSQL2List(strSql);
				if (objList.size() > 0) {
					isProvinceCheme = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// TODO 应该使用迭代.
		for (int i = 0; i < objList.size(); i++) {
			Map<String, Object> map = objList.get(i);
			// 每次生成一个
			DbmsNewDataExportServiceImpl dataExport = new DbmsNewDataExportServiceImpl();
			DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
			bean.setDdcid(StringNumberUtil.getStringByMap(map, "ddcid"));
			bean.setInsertonly(StringNumberUtil.getStringByMap(map, "autoruninsertonly"));
			bean.setCondition(StringNumberUtil.getStringByMap(map, "autoruncondition"));
			bean.setHiddencon(null);

			dataExport.prisonDataExchange(bean);
			log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + "部门ID:" + departid + ",方案:" + StringNumberUtil.getStringByMap(map, "ddcid") + "数据交换完毕！");
			// 清理内存
			//
			bean = null;
			dataExport = null;
			//
			System.gc();
		}
		log("同步数据条数:" + objList.size());
		log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + "数据实时同步结束....");
		String tianjinconde = getJyConfigValue(GkzxCommon.TIANJIN_CODE, "");
		if ((objList.size() == 0)) {
			// 未执行数据交换时返回
			return isProvinceCheme;
		}

//		if (provincecode.equals(departid) && (!StringNumberUtil.belongTo(departid, tianjinconde))) {
//			// 省份代码的时候，不执行存储过程
//			return isProvinceCheme;
//		}

		// 执行逻辑:
		// 取出数据List，
		// 然后针对每一条记录,执行一次交换

		// 调用存储过程分发数据
		Connection conn =  null;
		try {
			conn =  JdbcConnUtil.getConn();
			log("开始调用数据交换存储过程。。。");	
			boolean rest = callInterchangeProcedure(conn, departid);
			log("数据交换存储过程执行" + (rest ? "成功" : "失败"));
		} catch (Exception e) {
			log("调用存储过程失败!");
			e.printStackTrace();
		} finally {
			JdbcConnUtil.close(conn);
			conn = null;
		}
		return isProvinceCheme;
	}

	public String prisonDataExchange(DbmsNewDataExportBean bean) {
		TaskBean taskBean = new TaskBean(); // 这个 taskBean 要不要暂存到什么地方?
		return prisonDataExchange(bean, taskBean);
	}
	/**
	 * 数据交换方法,依赖于当前对象的一些实例变量. 参见本类开始的地方.
	 */
	@Override
	public String prisonDataExchange(DbmsNewDataExportBean bean, TaskBean taskBean) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);

		// TaskBean taskBean = new TaskBean();
		// boolean hasXinqiBiandong = false;
		// boolean hasTonganfan = false;
		// boolean caipanxinxi = false;
		String suid = GkzxCommon.USER_ADMIN;
		String interchange = "500";
		String strInterchange = jyconfig.getProperty("interchange") == null ? "500" : jyconfig
				.getProperty("interchange");
		String addcolumns = jyconfig.getProperty("addcolumns") == null ? "XXXX@YYYY@ZZZZ" : jyconfig
				.getProperty("addcolumns");
		String otherxfzb = jyconfig.getProperty("otherxfzb") == null ? "vi_interface_XFBD" : jyconfig
				.getProperty("otherxfzb");
		strJgxt = (jyconfig.getProperty("jgxt") == null ? "jgxt" : jyconfig.getProperty("jgxt"));
		String[] addcolumnsArray = new String[] {};
		String batchexchange = jyconfig.getProperty("batchexchange") == null ? GkzxCommon.ONE : jyconfig
				.getProperty("batchexchange");

		// 方案的部门id
		String strSdid = "";
		if (!StringNumberUtil.isNullOrEmpty(addcolumns)) {
			addcolumnsArray = addcolumns.split("@");
			addcolumnslst = Arrays.asList(addcolumnsArray);
		}
		if (!StringNumberUtil.isNullOrEmpty(strInterchange)) {
			interchange = strInterchange;
		}

		taskBean.setStatus(TaskBean.STATUS_INIT);
		if (null != bean.getDdcid() && !"".equals(bean.getDdcid())) {
			Connection daoruconn = null;
			Connection localconn = null;
			Connection conn = null;
			PreparedStatement localps = null;
			ResultSet localrs = null;
			try {
				localconn = JdbcConnUtil.getConn();
				String insertOnly = "0";
				if (null != bean.getInsertonly() && !"".equals(bean.getInsertonly())) {
					insertOnly = bean.getInsertonly();
				}
				SimpleDateFormat sdf66 = new SimpleDateFormat("yyyyMMdd");
				int fk = Integer.valueOf(sdf66.format(new Date())) * 10;
				Map<String, List<DbmsCodeChemeNew>> daimamap = new HashMap<String, List<DbmsCodeChemeNew>>();// 代码替换map
				String tobaseString = "select * from DBMS_DATABASE_NEW a where a.ddid in (select todatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"
						+ bean.getDdcid() + "')";

				localps = localconn.prepareStatement(tobaseString);
				localrs = localps.executeQuery();
				List<Map<String,Object>> dataAll = JdbcConnUtil.ResultSetToList(localrs);
				ArrayList<String> criminalidList = new ArrayList<String>();
				DbmsDatabaseNew tobsseDatabaseNew = null;
				if (dataAll.size() > 0) {
					HashMap map = (HashMap) dataAll.get(0);
					tobsseDatabaseNew = parseDatabaseNew(map);
				}
				JdbcConnUtil.closeResource(null, localps, localrs);

				String daimasql = "select * from DBMS_CODE_TYPE_NEW a where 1=1";
				localps = localconn.prepareStatement(daimasql);
				localrs = localps.executeQuery();
				dataAll = JdbcConnUtil.ResultSetToList(localrs);
				JdbcConnUtil.closeResource(null, localps, localrs);
				PreparedStatement localps2 = null;
				ResultSet localrs2 = null;

				for (int i = 0; i < dataAll.size(); i++) {
					HashMap map = (HashMap) dataAll.get(i);
					String daimamingxisql = "select * from DBMS_CODE_CHEME_NEW a where a.codetype='"
							+ StringNumberUtil.getStringByMap(map, "codetypeid") + "'";

					localps2 = localconn.prepareStatement(daimamingxisql);
					localrs2 = localps2.executeQuery();
					List<DbmsCodeChemeNew> daimamingxilist = getCodeChemeNew(JdbcConnUtil.ResultSetToList(localrs2));
					JdbcConnUtil.closeResource(null, localps2, localrs2);
					daimamap.put(StringNumberUtil.getStringByMap(map, "codetypeid"), daimamingxilist);
				}
				String chaxuncon = "";
				if (null != bean.getCondition() && !"".equals(bean.getCondition())) {
					chaxuncon = bean.getCondition();
				} else if (null != bean.getHiddencon() && !"".equals(bean.getHiddencon())) {
					chaxuncon = bean.getHiddencon();
				}
				//chaxuncon = " bh = '6120000002' ";

				daoruconn = JdbcConnUtil.getConnection(tobsseDatabaseNew);
				// daoruconn.setAutoCommit(false);
				// 判断方案操作数据库类型
				String basesql = "select * from DBMS_DATABASE_NEW a where a.ddid in (select fromdatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"
						+ bean.getDdcid() + "')";
				localps = localconn.prepareStatement(basesql);
				localrs = localps.executeQuery();
				dataAll = JdbcConnUtil.ResultSetToList(localrs);

				String basetype = "";
				DbmsDatabaseNew bs = null;

				if (dataAll.size() > 0) {
					HashMap map = (HashMap) dataAll.get(0);
					bs = parseDatabaseNew(map);
					basetype = bs.getDatabasetype();
					strSdid = bs.getSdid();
				}
				JdbcConnUtil.closeResource(null, localps, localrs);

				bean.setBasetype(basetype);
				
				//2015/4/6导出方案判断开始
				basesql = "select * from DBMS_FILE_COPY a where a.ddcid='"
					+ bean.getDdcid() + "' and FILETYPE = 1";
				localps = localconn.prepareStatement(basesql);
				localrs = localps.executeQuery();
				dataAll = JdbcConnUtil.ResultSetToList(localrs);
				
				//是否需要导出档案
				boolean hasOutputArchives = false;				
				if (dataAll.size() > 0) {
					hasOutputArchives = true;
				}
				JdbcConnUtil.closeResource(null, localps, localrs);
				
				//2015/4/6导出方案判断结束
				HashMap<String, Integer> mapTableRecordCnt = new HashMap<String, Integer>();
				String maintablesql = "";
				String maincon = "";
				
				String biaosql = "select * from DBMS_DATAS_TABLE_NEW a where a.ddcid='" + bean.getDdcid()
						+ "' and a.TOTABLE is not null order by  a.ddtismaintable,ddtorder";
				localps = localconn.prepareStatement(biaosql);
				localrs = localps.executeQuery();
				dataAll = JdbcConnUtil.ResultSetToList(localrs);
				List<DbmsDatasTableNew> biaolist = getDatasTableNew(dataAll);
				JdbcConnUtil.closeResource(null, localps, localrs);
				// 进度条用
				int sum = 0;
				int tem = 0;

				conn = JdbcConnUtil.getConnection(bs);

				// 计算条数开始

				for (Object biaoobj : biaolist) {
					DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
					// 查询所有列
					String chaxunsql = " count(*) ";
					String daochusql = "";
					if ("0".equals(thetable.getDdtismaintable())) {
						maincon = maincon + " a where 1=1";
						// 额外查询条件追加开始 2014-1-20
						String addconditionString = thetable.getAddcondition();
						if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
							maincon = maincon + "  and a." + addconditionString;
						}
						// 额外查询条件追加结束2014-1-20
						if (!"".equals(chaxuncon)) {
							maincon = maincon + " and " + chaxuncon;
						}
						maintablesql = "select " + chaxunsql + " from " + thetable.getTablename() + maincon;
						daochusql = maintablesql;
					} else {
						daochusql = "select " + chaxunsql + " from " + thetable.getTablename() + " b where 1=1 ";
						// 额外查询条件追加开始 2014-1-20
						String addconditionString = thetable.getAddcondition();
						if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
							daochusql = daochusql + " and b." + addconditionString;
						}
						// 额外查询条件追加结束 2014-1-20

						// 其他表根据数据关系生成查询条件
						if (null != thetable.getShujuguanxi() && !"".equals(thetable.getShujuguanxi())) {
							String[] temparr = thetable.getShujuguanxi().split(",");
							String tempcon = "";
							for (String str : temparr) {
								String[] temp2 = str.split("=");
								if ("".equals(tempcon)) {
									tempcon = "a." + temp2[0] + "=b." + temp2[1];
								} else {
									tempcon = " and a." + temp2[0] + "=b." + temp2[1];
								}
							}

							if (!"".equals(tempcon)) {
								int indexFrom = maintablesql.indexOf("from");
								String strReplaceMaintablesql = "select 'X' " + maintablesql.substring(indexFrom);
								daochusql = daochusql + " and exists (" + strReplaceMaintablesql + " and " + tempcon
										+ " )";
							}
						}
					}
					if (!StringNumberUtil.isNullOrEmpty(daochusql)) {
						int intTableRecordCnt = this.getSumCount(conn, daochusql);
						sum += intTableRecordCnt;
						mapTableRecordCnt.put(thetable.getTablename(), Integer.valueOf(intTableRecordCnt));
					}
				}

				// 计算条数结束
				taskBean.setStatus(TaskBean.STATUS_START);
				taskBean.setSum(sum);

				maintablesql = "";
				maincon = "";
				for (Object biaoobj : biaolist) {
					DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
					// 查询所有列
					String chaxunsql = "";
					String daochusql = "";
					// String
					// liesql="from DbmsDatasChemeDetailNew a where a.id.ddcid='"+bean.getDdcid()+"' and a.ddtid='"+thetable.getId().getDdtid()+"'";
					// List<Object> lielist=baseDao.queryListObjectAll(liesql);
					String liesql = "select * from DBMS_DATAS_CHEME_DETAIL_NEW a where a.ddcid='" + bean.getDdcid()
							+ "' and a.ddtid='" + thetable.getDdtid() + "' order by a.dcdifpkey asc,a.dcdid asc ";
					localps = localconn.prepareStatement(liesql);
					localrs = localps.executeQuery();
					dataAll = JdbcConnUtil.ResultSetToList(localrs);
					List<DbmsDatasChemeDetailNew> lielist = getDatasChemeDetailNew(dataAll);
					JdbcConnUtil.closeResource(null, localps, localrs);

					boolean isFirst = true;
					String strAsc = "";
					String strDesc = "";
					String strKeyCon = "";
					String strColumn = "";
					ArrayList<String> lstColumnsArrayList = new ArrayList<String>();
					String strorgBianhao = "";
					// GB002:410600:河南省鹤壁市
					HashMap<String, HashMap<String, TbsysCode>> mapCodeScidRemarkHashMap = new HashMap<String, HashMap<String, TbsysCode>>();
					ArrayList<String> strCodeList = new ArrayList<String>();
					// 原字段名->代码code:关联字段名：单位类型；如：dbmx->GB002:dbbh:100000016
					HashMap<String, ArrayList<String>> mapColumnType = new HashMap<String, ArrayList<String>>();
					for (Object obj : lielist) {
						DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
						strColumn = detail.getDcdfromcolumns();
						if (strColumn != null && !"".equals(strColumn)) {
							if (strColumn.indexOf("@") > -1) {
								// 该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
								String[] strTmpColumnStrings = strColumn.split("@");
								strColumn = strTmpColumnStrings[strTmpColumnStrings.length - 1].trim();
								if (strTmpColumnStrings.length == 3) {
									ArrayList<String> lstTypeColumnDanweiArrayList = new ArrayList<String>();
									lstTypeColumnDanweiArrayList.add(strTmpColumnStrings[1].trim());
									strCodeList.add(strTmpColumnStrings[1].trim());
									strorgBianhao = strTmpColumnStrings[0];
									if (strorgBianhao.indexOf(":") > 0) {
										// 1000001:bh
										String[] strDanweiFromColumn = strorgBianhao.split(":");
										strorgBianhao = strDanweiFromColumn[1];
										lstTypeColumnDanweiArrayList.add(strorgBianhao);
										lstTypeColumnDanweiArrayList.add(strDanweiFromColumn[0]);
									} else {
										// bh
										lstTypeColumnDanweiArrayList.add(strorgBianhao);
									}
									// 如：dbmx->GB002:dbbh:100000016或者//如：dbmx->GB002:dbbh
									mapColumnType.put(strColumn, lstTypeColumnDanweiArrayList);
								}
							}

							if (lstColumnsArrayList.indexOf(strColumn) < 0) {
								lstColumnsArrayList.add(strColumn);
								// 常量的字段名
								if (addcolumnslst.indexOf(strColumn) > -1) {
									if ("".equals(chaxunsql)) {
										chaxunsql = "'" + strColumn + "' as " + strColumn;
									} else {
										chaxunsql = chaxunsql + "," + "'" + strColumn + "' as " + strColumn;
									}
								} else {
									if ("".equals(chaxunsql)) {
										chaxunsql = strColumn;
									} else {
										chaxunsql = chaxunsql + "," + strColumn;
									}
								}

								// 如果是主键
								if ("1".equals(detail.getDcdifpkey())) {
									int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
									if (intIndex > -1) {
										strColumn = strColumn.toLowerCase().substring(intIndex + 4).trim();
									}
									if (isFirst) {
										strAsc = strColumn + " ASC";
										strDesc = strColumn + " DESC";
										if (basetype.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)||basetype.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)) {
											strKeyCon = "ltrim(rtrim(" + strColumn + ")) is not null ";
										} else {
											strKeyCon = "ltrim(rtrim(" + strColumn + ")) is not null and len(ltrim(rtrim(" + strColumn + "))) >= 1 ";
										}
										isFirst = false;
									} else {
										strAsc = strAsc + ", " + strColumn + " ASC";
										strDesc = strDesc + ", " + strColumn + " DESC";
										if (basetype.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)||basetype.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)) {
											strKeyCon = strKeyCon + " and ltrim(rtrim(" + strColumn + "))  is not null ";
										} else {
											strKeyCon = strKeyCon + " and ltrim(rtrim(" + strColumn + ")) is not null and len(ltrim(rtrim(" + strColumn + "))) >= 1 ";
										}
									}
								}
							}
						}
					}
					mapCodeScidRemarkHashMap = getLocalDbCodeScidRemark(localconn, strCodeList);
					if ("0".equals(thetable.getDdtismaintable())) {
						maincon = maincon + " a where 1=1";
						// 额外查询条件追加开始 2014-1-20
						String addconditionString = thetable.getAddcondition();
						if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
							maincon = maincon + "  and a." + addconditionString;
						}
						// 额外查询条件追加结束2014-1-20
						if (!"".equals(chaxuncon)) {
							maincon = maincon + " and " + chaxuncon;
						}
						// maintablesql="select "+chaxunsql+" from "+thetable.getTablename()+maincon;
						maintablesql = chaxunsql + " from " + thetable.getTablename() + maincon;
						daochusql = maintablesql;
					} else {
						daochusql = chaxunsql + " from " + thetable.getTablename() + " b where 1=1 ";
						// 额外查询条件追加开始 2014-1-20
						String addconditionString = thetable.getAddcondition();
						if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
							daochusql = daochusql + " and b." + addconditionString;
						}
						// 额外查询条件追加结束 2014-1-20
						// 其他表根据数据关系生成查询条件
						if (null != thetable.getShujuguanxi() && !"".equals(thetable.getShujuguanxi())) {
							String[] temparr = thetable.getShujuguanxi().split(",");
							String tempcon = "";
							for (String str : temparr) {
								String[] temp2 = str.split("=");
								if ("".equals(tempcon)) {
									tempcon = "a." + temp2[0] + "=b." + temp2[1];
								} else {
									tempcon = " and a." + temp2[0] + "=b." + temp2[1];
								}
							}
							// daochusql="select "+chaxunsql+" from "+thetable.getTablename()+" b where 1=1 ";

							if (!"".equals(tempcon)) {
								daochusql = daochusql + " and exists ( select " + maintablesql + " and " + tempcon
										+ " )";
							}
						}
					}
					if (!"".equals(daochusql)) {
						ResultSet rs = null;
						// Connection conn=null;
						Statement stmt = null;
						PreparedStatement prestmtPreparedStatement1 = null;
						PreparedStatement prestmtPreparedStatement2 = null;
						String strFromTable = thetable.getTablename();
						int recordCnt = 0;
						if (mapTableRecordCnt.get(strFromTable) != null) {
							recordCnt = mapTableRecordCnt.get(strFromTable).intValue();
						}
						for (int i = 0; i < recordCnt;) {

							try {
								stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
								String onedaochusql = "select top " + interchange + " * from (select top "
										+ String.valueOf(i + Integer.parseInt(interchange)) + " " + daochusql
										+ " and " + strKeyCon + " order by " + strAsc + " ) x order by " + strDesc;
								if (basetype.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)||basetype.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)) {
									onedaochusql = "select * from (select rownum no, " + daochusql + " and " + strKeyCon + ") where no > "
											+ String.valueOf(i) + " and no <= "
											+ String.valueOf(i + Integer.parseInt(interchange));
								}

								// 查询数据是否存在开始///////////////////////////////////////////////////////
								//源表主键信息
								ArrayList<String> keyFromColumnList = new ArrayList<String>();
								//目的表主键信息
								ArrayList<String> keyToColumnList = new ArrayList<String>();
								//源表主键类型信息
								ArrayList<String> keyFromColumnTypeList = new ArrayList<String>();
								//目的主键类型信息
								ArrayList<String> keyToColumnTypeList = new ArrayList<String>();
								//主键值信息
								ArrayList<String> valueList = new ArrayList<String>();
								//主键及值数组
								ArrayList<String> keyValueArrayList = new ArrayList<String>();
								//批处理运行过程中保存主键及值数组
								ArrayList<String> runKeyValueList = new ArrayList<String>();
								rs = stmt.executeQuery(onedaochusql);
//								if (GkzxCommon.ONE.equals(insertOnly)) {
									String strSelectColumn = "";
									String strSelectWhere = "";
									boolean isFirstSelect = true;
									while (rs.next()) {
										// 遍历获取数据,构造主键串
										keyFromColumnList = new ArrayList<String>();
										keyToColumnList = new ArrayList<String>();
										keyFromColumnTypeList = new ArrayList<String>();
										keyToColumnTypeList = new ArrayList<String>();
										valueList = new ArrayList<String>();
										// 原有column对应值
										String wheresqlString = "";
										for (Object obj : lielist) {
											// 开始进行数据交换
											DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
											// 如果是主键
											if ("1".equals(detail.getDcdifpkey())) {
												strColumn = detail.getDcdfromcolumns();
												int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
												if (intIndex > -1) {
													strColumn = strColumn.toLowerCase().substring(intIndex + 4).trim();
												}
												if (strColumn != null && !"".equals(strColumn)) {
													if (strColumn.indexOf("@") > -1) {
														// 该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
														String[] strTmpColumnStrings = strColumn.split("@");
														strColumn = strTmpColumnStrings[strTmpColumnStrings.length - 1]
																.trim();
													}
												}
												String ttvalue = this.getcontentvalue(basetype, detail
														.getDcdfromcloumnstype(), strColumn, rs);

												if (ttvalue != null && !"".equals(ttvalue)) {
													ttvalue = ttvalue.trim();
												}
												if (null == ttvalue) {
													ttvalue = "";
												}
												String keyValue = "";
												if (!StringNumberUtil.isNullOrEmpty(ttvalue)) {
													keyValue = ttvalue;
												} else if (!StringNumberUtil.isNullOrEmpty(detail
														.getDcdtocloumnsdefaultvalue())) {
													keyValue = getDefaultValue(suid, "", detail, strSdid);
												} else {
													keyValue = ttvalue;
												}
												keyFromColumnList.add(StringNumberUtil.isNullOrEmpty(strColumn.toLowerCase())?detail.getDcdtocolumns().toLowerCase():strColumn.toLowerCase());
												keyToColumnList.add(detail.getDcdtocolumns().toLowerCase());
												keyFromColumnTypeList.add(StringNumberUtil.isNullOrEmpty(detail.getDcdfromcloumnstype())?detail.getDcdtocolumnstype():detail.getDcdfromcloumnstype());
												keyToColumnTypeList.add(detail.getDcdtocolumnstype());
												if(detail.getDcdfromcloumnstype().equalsIgnoreCase("datetime") ||
												   detail.getDcdfromcloumnstype().equalsIgnoreCase("DATE")){
													valueList.add(keyValue.replace("-", "").replace(" ", "").replace(":", ""));
												} else {
													valueList.add(keyValue);
												}
												// 循环第一次的时候，获得查询列。
												if (isFirstSelect) {
														if ("Microsoft SQL Server".equals(tobsseDatabaseNew
																.getDatabasetype())) {
															if ("datetime".equalsIgnoreCase(detail
																	.getDcdtocolumnstype())) {
																strSelectColumn = ("".equals(strSelectColumn)?"":strSelectColumn + ", ") + "replace(replace(replace(convert(varchar," 
																        + detail.getDcdtocolumns() + ", 120),'-',''),' ',''),':','') as " + detail.getDcdtocolumns() + " ";
															} else {
																strSelectColumn = ("".equals(strSelectColumn)?"":strSelectColumn + ", ") + detail.getDcdtocolumns() + " ";
															}
														}
														if ("Oracle".equals(tobsseDatabaseNew.getDatabasetype())||"Dameng".equals(tobsseDatabaseNew.getDatabasetype())) {
															if ("DATE".equals(detail.getDcdtocolumnstype())) {
																strSelectColumn = ("".equals(strSelectColumn)?"":strSelectColumn + ", ") + " to_char("
																		+ detail.getDcdtocolumns() + ",'yyyyMMddHH24miss') as "
																		+ detail.getDcdtocolumns() + " ";
															} else {
																strSelectColumn = ("".equals(strSelectColumn)?"":strSelectColumn + ", ") + detail.getDcdtocolumns() + " ";
															}
														}
												}
													if ("Microsoft SQL Server".equals(tobsseDatabaseNew
															.getDatabasetype())) {
														if (!StringNumberUtil.isNullOrEmpty(ttvalue)) {
															if ("datetime".equalsIgnoreCase(detail
																	.getDcdtocolumnstype())) {
																ttvalue = ttvalue.replace("-", "").replace(" ", "").replace(":", "");
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + "replace(replace(replace(convert(varchar," + detail.getDcdtocolumns() + ", 120),'-',''),' ',''),':','')"  + " = "
																		+ ttvalue + " ";
															} else {
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + detail.getDcdtocolumns() + " = "
																		+ ttvalue + " ";
															}
														} else if (!StringNumberUtil.isNullOrEmpty(detail
																.getDcdtocloumnsdefaultvalue())) {
															if ("datetime".equalsIgnoreCase(detail
																	.getDcdtocolumnstype())) {
																ttvalue = ttvalue.replace("-", "").replace(" ", "").replace(":", "");
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + "replace(replace(replace(convert(varchar," + detail.getDcdtocolumns() + ", 120),'-',''),' ',''),':','')"  + " = "
																		+ detail.getDcdtocloumnsdefaultvalue() + " ";
															} else {
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + detail.getDcdtocolumns() + " = "
																		+ detail.getDcdtocloumnsdefaultvalue() + " ";
															}
														}
													}
													if ("Oracle".equals(tobsseDatabaseNew.getDatabasetype())||"Dameng".equals(tobsseDatabaseNew.getDatabasetype())) {
														if (!StringNumberUtil.isNullOrEmpty(ttvalue)) {
															if ("DATE".equals(detail.getDcdtocolumnstype())) {
																// 变更2014-1-20
																ttvalue = ttvalue.replace("-", "").replace(" ", "").replace(":", "");
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + " to_char(" + detail.getDcdtocolumns()
																		+ ",'yyyyMMddHH24miss') = '" + ttvalue + "' ";
															} else {
																// 变更2014-1-20
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + detail.getDcdtocolumns() + " = '"
																		+ ttvalue + "' ";
															}
														} else if (!StringNumberUtil.isNullOrEmpty(detail
																.getDcdtocloumnsdefaultvalue())) {
															if ("DATE".equals(detail.getDcdtocolumnstype())) {
																// 变更2014-1-20
																ttvalue = ttvalue.replace("-", "").replace(" ", "").replace(":", "");
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + " to_char(" + detail.getDcdtocolumns()
																		+ ",'yyyyMMddHH24miss') = '"
																		+ detail.getDcdtocloumnsdefaultvalue() + "' ";
															} else {
																// 变更2014-1-20
																wheresqlString = ("".equals(wheresqlString)?"":wheresqlString + " and ") + detail.getDcdtocolumns() + " = '"
																		+ detail.getDcdtocloumnsdefaultvalue() + "' ";
															}

														}
													}
											}
											if (!StringNumberUtil.isNullOrEmpty(wheresqlString)) {
												wheresqlString = wheresqlString.replace(":sdid", strSdid).replace(":nowdate", sinog2cDateFormater.format(new Date())).replace(":suid", suid);
											}
											//2015/4/6档案交换时的罪犯编号的获得开始
											if(hasOutputArchives&&"0".equals(thetable.getDdtismaintable())){
												strColumn = detail.getDcdfromcolumns();
												if (!GkzxCommon.CRIMID.equalsIgnoreCase(strColumn)&&!strColumn.toUpperCase().contains(GkzxCommon.CRIMID_AS)) {
													continue;
												}
												int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
												if (intIndex > -1) {
													strColumn = strColumn.toLowerCase().substring(intIndex + 4).trim();
												}
												if (strColumn != null && !"".equals(strColumn)) {
													if (strColumn.indexOf("@") > -1) {
														// 该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
														String[] strTmpColumnStrings = strColumn.split("@");
														strColumn = strTmpColumnStrings[strTmpColumnStrings.length - 1]
																.trim();
													}
												}
												String ttvalue = this.getcontentvalue(basetype, detail
														.getDcdfromcloumnstype(), strColumn, rs);

												if (ttvalue != null && !"".equals(ttvalue)) {
													ttvalue = ttvalue.trim();
												}
												if (null == ttvalue) {
													ttvalue = "";
												}
												String keyValue = "";
												if (!StringNumberUtil.isNullOrEmpty(ttvalue)&&(criminalidList.indexOf(ttvalue)<0)) {
													criminalidList.add(ttvalue);
												} 
											}
											//2015/4/6档案交换时的罪犯编号的获得结束
										}

										String strKeyValueString = "";
										for (int t = 0; t < valueList.size(); t++) {
											strKeyValueString += valueList.get(t);

										}
										if (keyValueArrayList.indexOf(strKeyValueString) < 0) {
											keyValueArrayList.add(strKeyValueString);
										}
										isFirstSelect = false;
										// 构造查询条件
										if ("".equals(strSelectWhere)) {
											if (!StringNumberUtil.isNullOrEmpty(wheresqlString)) {
												strSelectWhere = " ( " + wheresqlString + ")";
											}
										} else {
											if (!StringNumberUtil.isNullOrEmpty(wheresqlString)) {
												strSelectWhere = strSelectWhere + " or " + " ( " + wheresqlString + ")";
											}
										}

									}
									
									//获取本地的主键数据
									String sql = "select " + strSelectColumn + " from " + thetable.getTotable();
									if (!StringNumberUtil.isNullOrEmpty(strSelectWhere)) {
										sql += " where " + strSelectWhere.replace(":sdid", strSdid).replace(":nowdate", sinog2cDateFormater.format(new Date())).replace(":suid", suid);
									}
									try {
										localps = daoruconn.prepareStatement(sql);
										localrs = localps.executeQuery();
										ArrayList<Map<String, Object>> objList = JdbcConnUtil.ResultSetToList(localrs);
										JdbcConnUtil.closeResource(null, localps, localrs);

										for (int m = 0; m < objList.size(); m++) {
											HashMap map = (HashMap) objList.get(m);
											String strKeyValueString = "";
											for (int t = 0; t < keyToColumnList.size(); t++) {
												String keyType = keyToColumnTypeList.get(t);
												if(keyType.equalsIgnoreCase("datetime") ||
												   keyType.equalsIgnoreCase("DATE")){
													strKeyValueString += map.get(keyToColumnList.get(t).replace("-", "").replace(" ", "").replace(":", ""));
												} else {
													strKeyValueString += map.get(keyToColumnList.get(t));
												}

											}
											int n = keyValueArrayList.indexOf(strKeyValueString);
											if (n > -1) {
												//移除本地已经存在的数据
												keyValueArrayList.remove(n);
											}
										}

									} catch (Exception e) {
										e.printStackTrace();
									} finally {
										JdbcConnUtil.closeResource(null, localps, localrs);
									}
//								}
								// 查询数据是否存在结束///////////////////////////////////////////////////////
								boolean isLocalExists = false;
								// 遍历插入数据
								String insertaa = "insert into " + thetable.getTotable() + " (";
								String updateaa = "update " + thetable.getTotable() + " set ";
								String updatesqlString = "";
								String wheresqlString = "";
								String liearr = "";
								String zhiarr = "";
								int zhujianindex = 1;
								int orgzhujianindex = zhujianindex;
								for (Object obj : lielist) {
									DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
									String daovalue = "?";
									// 如果是主键
									if ("1".equals(detail.getDcdifpkey())) {
										if ("".equals(wheresqlString)) {
											wheresqlString = detail.getDcdtocolumns() + " = " + daovalue + " ";
										} else {
											wheresqlString = wheresqlString + " and " + detail.getDcdtocolumns()
													+ " = " + daovalue + " ";
										}
									} else {
										zhujianindex++;
										if ("".equals(updatesqlString)) {
											updatesqlString = detail.getDcdtocolumns() + " = " + daovalue + " ";
										} else {
											updatesqlString = updatesqlString + " , " + detail.getDcdtocolumns()
													+ " = " + daovalue + " ";
										}
									}
									if ("".equals(zhiarr)) {
										zhiarr = daovalue;
									} else {
										zhiarr = zhiarr + "," + daovalue + "";
									}
									if ("".equals(liearr)) {
										liearr = detail.getDcdtocolumns();
									} else {
										liearr = liearr + "," + detail.getDcdtocolumns() + "";
									}
								}
								orgzhujianindex = zhujianindex;
								insertaa = insertaa + liearr + " ) values (" + zhiarr + ")";
								updateaa = updateaa + updatesqlString + " where " + wheresqlString;
								prestmtPreparedStatement1 = daoruconn.prepareStatement(insertaa);
								prestmtPreparedStatement2 = daoruconn.prepareStatement(updateaa);								
								rs.beforeFirst();
								String strKeyColumns = "";
								runKeyValueList = new ArrayList<String>();
								taskBean.setStatus(TaskBean.STATUS_DOING);
								while (rs.next()) {
									isLocalExists = false;
									tem++;
									if (tem > sum) {
										tem = sum;
									}
									taskBean.setCounter(tem);
									
									// if(this.session!=null){
									// this.session.put("taskbean", taskbean);
									// }
									// 遍历获取数据
									zhujianindex = orgzhujianindex;
									Map<String, String> valuemapMap = new HashMap<String, String>();
									Map<String, Object> keyValueMap = new HashMap<String, Object>();
									// 原有column对应值
									Map<String, String> orgValuemapMap = new HashMap<String, String>();
									for (Object obj : lielist) {
										// 开始进行数据交换
										DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
										strColumn = detail.getDcdfromcolumns();
										int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
										if (intIndex > -1) {
											strColumn = strColumn.toLowerCase().substring(intIndex + 4).trim();
										}										
										if (strColumn != null && !"".equals(strColumn)) {
											if (strColumn.indexOf("@") > -1) {
												// 该column的格式为：1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx
												String[] strTmpColumnStrings = strColumn.split("@");
												strColumn = strTmpColumnStrings[strTmpColumnStrings.length - 1].trim();
											}
										}
										String ttvalue = this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(),
												strColumn, rs);

										if (ttvalue != null && !"".equals(ttvalue)) {
											ttvalue = ttvalue.trim();
										}
										if (null == ttvalue) {
											ttvalue = "";
										}
										valuemapMap.put(detail.getDcdtocolumns().toLowerCase(), ttvalue);
										if (StringNumberUtil.isNullOrEmpty(strColumn)){
											strColumn =detail.getDcdtocolumns().toLowerCase();
										}
										if ("1".equals(detail.getDcdifpkey())) {
											keyValueMap.put(strColumn.toLowerCase(), ttvalue);
											if (!StringNumberUtil.isNullOrEmpty(ttvalue)) {
												keyValueMap.put(strColumn.toLowerCase(), ttvalue);
												orgValuemapMap.put(strColumn.toLowerCase(), ttvalue);
											} else if (!StringNumberUtil.isNullOrEmpty(detail
													.getDcdtocloumnsdefaultvalue())) {
												keyValueMap.put(strColumn.toLowerCase(), getDefaultValue(suid, "", detail, strSdid));
												orgValuemapMap.put(strColumn.toLowerCase(), getDefaultValue(suid, "", detail, strSdid));
											} else {
												keyValueMap.put(strColumn.toLowerCase(), ttvalue);
												orgValuemapMap.put(strColumn.toLowerCase(), ttvalue);
											}
										}
									}
									// 本地是否已经存在的判断
//									if (GkzxCommon.ONE.equals(insertOnly)) {
										String strKeyValueString = "";
										for (int t = 0; t < keyFromColumnList.size(); t++) {
											String keyType = keyFromColumnTypeList.get(t);
											if(keyType.equalsIgnoreCase("datetime") ||
											   keyType.equalsIgnoreCase("DATE")){
												strKeyValueString += ((String)keyValueMap.get(keyFromColumnList.get(t))).replace("-", "").replace(" ", "").replace(":", "");
											} else {
												strKeyValueString += keyValueMap.get(keyFromColumnList.get(t));
											}

										}
										if (keyValueArrayList.indexOf(strKeyValueString) < 0) {
											isLocalExists = true;
										} else {
											if (runKeyValueList.indexOf(strKeyValueString) < 0) {
												//设为本地存在后，加入到运行过程中存在的list里，以解决主键冲突的问题
												runKeyValueList.add(strKeyValueString);
											} else {
												//假如在运行list里存在，代表已经执行了插入动作，直接执行下一条，以解决主键冲突的问题
												continue;
											}
										}
//									}

									// //更新语句必须带条件,否则不能进行更新
									int insertindex = 1;
									int updateindex = 1;
//									strKeyColumns = "";
									for (Object obj : lielist) {
										DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
										String daovalue = valuemapMap.get(detail.getDcdtocolumns().toLowerCase());
										if (null == daovalue || "".equals(daovalue)) {
											daovalue = "";
										}
										daovalue = this.jiexiValue(suid, daovalue, detail, fk, valuemapMap, null,
												daimamap, tobsseDatabaseNew, "jiaohuan", mapColumnType,
												mapCodeScidRemarkHashMap, orgValuemapMap, strSdid);
										daovalue = geshizhuanhuan(tobsseDatabaseNew.getDatabasetype(), detail
												.getDcdtocolumnstype(), daovalue);
										if (!isLocalExists) {
											if ("1".equals(detail.getDcdifpkey())) {
												strKeyColumns = ("".equals(strKeyColumns)?"":strKeyColumns + ",") + detail.getDcdtocolumns().toLowerCase() + "=" + daovalue;
											}
											//本地不存在时，执行插入动作
											addPreparedStatement(prestmtPreparedStatement1, detail.getDcdtocolumns()
													.toLowerCase(), detail.getDcdtocolumnstype(), insertindex, daovalue);
											insertindex++;
										} else {
											//本地存在时，可更新时执行更新动作
											if (!GkzxCommon.ONE.equals(insertOnly)) {
												if ("1".equals(detail.getDcdifpkey())) {
													addPreparedStatement(prestmtPreparedStatement2, detail
															.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(),
															zhujianindex, daovalue);
													zhujianindex++;
													strKeyColumns = ("".equals(strKeyColumns)?"":strKeyColumns + ",") + detail.getDcdtocolumns().toLowerCase() + "=" + daovalue;
												} else {
													addPreparedStatement(prestmtPreparedStatement2, detail
															.getDcdtocolumns().toLowerCase(), detail.getDcdtocolumnstype(),
															updateindex, daovalue);
													updateindex++;
												}
											}
										}
									}
									if (batchexchange.equals(GkzxCommon.ONE)) {
										if (!isLocalExists) {
											//本地不存在时，执行插入动作
											prestmtPreparedStatement1.addBatch();
										} else {
											//本地存在时，可更新时执行更新动作
											if (!GkzxCommon.ONE.equals(insertOnly)) {
												prestmtPreparedStatement2.addBatch();
											}
										}
									}  else {
										try {
											if (!isLocalExists) {
												//本地不存在时，执行插入动作
												prestmtPreparedStatement1.executeUpdate();
											} else {
												//本地存在时，可更新时执行更新动作
												if (!GkzxCommon.ONE.equals(insertOnly)) {
													prestmtPreparedStatement2.executeUpdate();
												}
											}
										} catch (Exception e) {
											String strErrString = "主键:" + strKeyColumns + ",数据交换出错信息:" + e.getMessage();
											logger.error(strErrString);
											System.out.println(strErrString);
										} finally {
										}
									}
								}
								if (batchexchange.equals(GkzxCommon.ONE)) {
									try {
										
											prestmtPreparedStatement1.executeBatch();
											prestmtPreparedStatement2.executeBatch();
									} catch (Exception e) {
										String strErrString = "主键:" + strKeyColumns + ",数据交换出错信息:" + e.getMessage();
										logger.error(strErrString);
										System.out.println(strErrString);
									} finally {
										if (null != prestmtPreparedStatement2) {
											prestmtPreparedStatement2.clearBatch();
											prestmtPreparedStatement2.close();
											prestmtPreparedStatement2 = null;
										}
										if (null != prestmtPreparedStatement1) {
											prestmtPreparedStatement1.clearBatch();
											prestmtPreparedStatement1.close();
											prestmtPreparedStatement1 = null;
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								try {
									if (rs != null) {
										rs.close();
									}
									if (stmt != null) {
										stmt.close();
									}
									if (null != prestmtPreparedStatement2) {
										prestmtPreparedStatement2.clearBatch();
										prestmtPreparedStatement2.close();
										prestmtPreparedStatement2 = null;
									}
									if (null != prestmtPreparedStatement1) {
										prestmtPreparedStatement1.clearBatch();
										prestmtPreparedStatement1.close();
										prestmtPreparedStatement1 = null;
									}
								} catch (Exception e2) {
								}
							}
							System.out.println("数据方案ID：" + bean.getDdcid() + "正在交换，到现在（" + new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + "）已经交换了" + i + "条。");
							i = i + Integer.parseInt(interchange);
						}
					}
				}
				if (hasOutputArchives) {
					outPutArchives(criminalidList,localconn,bean.getDdcid());
				}
				taskBean.setStatus(TaskBean.STATUS_SUCCESS);
			} catch (Exception e) {
				taskBean.setStatus(TaskBean.STATUS_FAILURE);
				e.printStackTrace();
			} finally {
				JdbcConnUtil.closeResource(localconn, localps, localrs);
				JdbcConnUtil.closeResource(conn, null, null);
			}
		}
		return null;
	};

	@Override
	public void outPutArchives(List<String> criminalidList,Connection localconn,String strDdcId) {
		DbmsFileCopyServiceImpl fileCopyDAO = new DbmsFileCopyServiceImpl();
//    	UrlDownload oInstance = new UrlDownload();
    	GetAbsolutePath absolutePath=new GetAbsolutePath();
//    	JsonTool jsonTool = new JsonTool();
		PreparedStatement localps = null;
		ResultSet localrs = null;
		String jgxtaddress = "";
		String localaddress = "";
		String addressid = "";
		String ddcid = "";
		String filetype = "";
		String localdepartid = "";
		String remotedepartid = "";
		String updateflg = "";
		String docconent = "";
		SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMddHHmmss");
		ArrayList<String> filePath = new ArrayList<String>();
		ArrayList<String> archiveids = new ArrayList<String>();
		List<Map<String,Object>> dataAll = new ArrayList<Map<String,Object>>();
		String basesql = "select * from DBMS_FILE_COPY a where a.ddcid='"
			+ strDdcId + "' and FILETYPE = 1";
		try {
			localps = localconn.prepareStatement(basesql);
			localrs = localps.executeQuery();
			dataAll = JdbcConnUtil.ResultSetToList(localrs);
			
			if (dataAll.size() > 0) {
				HashMap map = (HashMap) dataAll.get(0);
				jgxtaddress = StringNumberUtil.getStringByMap(map, "jgxtaddress");
				localaddress = StringNumberUtil.getStringByMap(map, "localaddress");
				addressid = StringNumberUtil.getStringByMap(map, "addressid");
				ddcid = StringNumberUtil.getStringByMap(map, "ddcid");
				filetype = StringNumberUtil.getStringByMap(map, "filetype");
				localdepartid = StringNumberUtil.getStringByMap(map, "localdepartid");
				remotedepartid = StringNumberUtil.getStringByMap(map, "remotedepartid");
				updateflg = StringNumberUtil.getStringByMap(map, "updateflg");
			}
		} catch (Exception e) {
			
		} finally {
			JdbcConnUtil.closeResource(null, localps, localrs);
		}
		
		//绝对路径
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		
	    for(int j=0;j<criminalidList.size()&&(dataAll.size() > 0);){
			try{
				//导出时路径
				int y = 0;
				String strcrimids= "";
				for (y = 0; y < 500 && y < criminalidList.size() - j; y++){
					if (y == 0) {
						strcrimids = strcrimids + " '" + criminalidList.get(j + y) + "' ";
					} else {
						strcrimids = strcrimids + ", '" + criminalidList.get(j + y) + "' ";
					}
				}
				j = j+y;
				
				if (!StringNumberUtil.isNullOrEmpty(strcrimids)){
					strcrimids = "(" + strcrimids + ")";
				} else {
					continue;
				}
				
				if(!StringNumberUtil.isNullOrEmpty(updateflg)) {
					//TBFLOW_ARCHIVES_WATCH的更新标记；1：更新监狱 2：更新省局
					//基于上面的解释，1:取省局的变化数据；2：取监狱的数据
					if (GkzxCommon.ONE.equals(updateflg)) {
						//检查TBFLOW_ARCHIVES_WATCH表里的省局的数据是否已经变化
						basesql = "select t.archiveid,a.docconent from TBFLOW_ARCHIVES_WATCH t,TBFLOW_ARCHIVES a where remotechanged=1 and a.archiveid=t.archiveid and a.int1 = 1 and t.filetype = 1 and t.crimid in " + strcrimids;
					} else if (GkzxCommon.TWO.equals(updateflg)){
						//检查TBFLOW_ARCHIVES_WATCH表里的监狱的数据是否已经变化
						basesql = "select t.archiveid,a.docconent from TBFLOW_ARCHIVES_WATCH t,TBFLOW_ARCHIVES a where t.localchanged=1 and a.archiveid=t.archiveid and a.int1 = 1 and t.filetype = 1 and t.crimid in " + strcrimids;
					}
					
					try {
						localps = localconn.prepareStatement(basesql);
						localrs = localps.executeQuery();
						dataAll = JdbcConnUtil.ResultSetToList(localrs);
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						JdbcConnUtil.closeResource(null, localps, localrs);
					}
					for (int x = 0; x < dataAll.size();x++) {
						HashMap map = (HashMap) dataAll.get(x);
						docconent = StringNumberUtil.getStringByMap(map, "docconent");
						if (!StringNumberUtil.isNullOrEmpty(docconent)){
							filePath.add(docconent);
							archiveids.add(StringNumberUtil.getStringByMap(map, "archiveid"));
						}
					}
					
				} else {
					//未配置
					continue;
				}
				
				
				//TBFLOW_ARCHIVES_WATCH的更新标记；1：更新监狱 2：更新省局
				//文件已经获取，标记为已处理
				if (GkzxCommon.ONE.equals(updateflg)) {
					//TBFLOW_ARCHIVES_WATCH表里的监狱的更新时间
					basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.localchanged=2, x.localchangedtime = ?,x.localdonetime=x.remotechangedtime where x.archiveid= ? ";
				} else if (GkzxCommon.TWO.equals(updateflg)){
					//TBFLOW_ARCHIVES_WATCH表里的省局的更新时间
					basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.remotechanged=2, x.remotechangedtime = ?,x.remotedonetime = localchangedtime where x.archiveid= ? ";
				}
				
				boolean hasChanged = false;
				localps = localconn.prepareStatement(basesql);
				for (int z = 0; z < filePath.size(); z++) {
					String relativePath = filePath.get(z);
					relativePath = relativePath.replace("\\", "/");
						String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, relativePath);
					
//					FileUtil.saveToFile(docconent, targetPath, GkzxCommon.encoding);
					
//					String savePath = absolutePath.getAbsolutePathAppend(GkzxCommon.SINGLESEPALINE + list.get(i).getLocaladdress() +GkzxCommon.SINGLESEPALINE+criminalidList.get(j)+type);
					//System.out.println("--------------------"+savePath+"---------------------");
					
					try {
						fileCopyDAO.saveToFile(jgxtaddress+GkzxCommon.SINGLESEPALINE+relativePath, targetPath);
						
						File   f   =   new   File(targetPath); 
						long   modify =   f.lastModified(); //   修改时间 
						String strModTime = sdff.format(new Date(modify)); 
						localps.setString(1, strModTime);
						localps.setString(2, archiveids.get(z));
						localps.addBatch();
						hasChanged = true;
					} catch (Exception e) {
					}
				}
				
				if (hasChanged) {
					localps.executeBatch();
				}
				
				JdbcConnUtil.closeResource(null, localps, localrs);
			}catch(Exception e){
				e.printStackTrace();
			} finally {
				JdbcConnUtil.closeResource(null, localps, localrs);
			}
		}	
	}
	
	
	@Override
	public void newOutPutArchives(List<String> criminalidList, Connection localconn,String strDdcId, String remoteDepartid){
		DbmsFileCopyServiceImpl fileCopyDAO = new DbmsFileCopyServiceImpl();
//    	UrlDownload oInstance = new UrlDownload();
    	GetAbsolutePath absolutePath=new GetAbsolutePath();
//    	JsonTool jsonTool = new JsonTool();
		PreparedStatement localps = null;
		ResultSet localrs = null;
		String jgxtaddress = "";
		String localaddress = "";
		String addressid = "";
		String ddcid = "";
		String filetype = "";
		String localdepartid = "";
		String remotedepartid = "";
		String updateflg = "";
		String docconent = "";
		SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMddHHmmss");
		ArrayList<String> filePath = new ArrayList<String>();
		ArrayList<String> archiveids = new ArrayList<String>();
		List<Map<String,Object>> dataAll = new ArrayList<Map<String,Object>>();
		String basesql = "select * from DBMS_FILE_COPY a where a.ddcid='"
			+ strDdcId + "' and FILETYPE = 1 and REMOTEDEPARTID='"+remoteDepartid+"'";
		try {
			localps = localconn.prepareStatement(basesql);
			localrs = localps.executeQuery();
			dataAll = JdbcConnUtil.ResultSetToList(localrs);
			
			if (dataAll.size() > 0) {
				HashMap map = (HashMap) dataAll.get(0);
				jgxtaddress = StringNumberUtil.getStringByMap(map, "jgxtaddress");
				localaddress = StringNumberUtil.getStringByMap(map, "localaddress");
				addressid = StringNumberUtil.getStringByMap(map, "addressid");
				ddcid = StringNumberUtil.getStringByMap(map, "ddcid");
				filetype = StringNumberUtil.getStringByMap(map, "filetype");
				localdepartid = StringNumberUtil.getStringByMap(map, "localdepartid");
				remotedepartid = StringNumberUtil.getStringByMap(map, "remotedepartid");
				updateflg = StringNumberUtil.getStringByMap(map, "updateflg");
			}
		} catch (Exception e) {
			
		} finally {
			JdbcConnUtil.closeResource(null, localps, localrs);
		}
		
		//绝对路径
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		
	    for(int j=0;j<criminalidList.size()&&(dataAll.size() > 0);){
			try{
				//导出时路径
				int y = 0;
				String strcrimids= "";
				for (y = 0; y < 500 && y < criminalidList.size() - j; y++){
					if (y == 0) {
						strcrimids = strcrimids + " '" + criminalidList.get(j + y) + "' ";
					} else {
						strcrimids = strcrimids + ", '" + criminalidList.get(j + y) + "' ";
					}
				}
				j = j+y;
				
				if (!StringNumberUtil.isNullOrEmpty(strcrimids)){
					strcrimids = "(" + strcrimids + ")";
				} else {
					continue;
				}
				
				if(!StringNumberUtil.isNullOrEmpty(updateflg)) {
					//TBFLOW_ARCHIVES_WATCH的更新标记；1：更新监狱 2：更新省局
					//基于上面的解释，1:取省局的变化数据；2：取监狱的数据
					if (GkzxCommon.ONE.equals(updateflg)) {
						//检查TBFLOW_ARCHIVES_WATCH表里的省局的数据是否已经变化
						basesql = "select t.archiveid,a.docconent from TBFLOW_ARCHIVES_WATCH t,TBFLOW_ARCHIVES a where remotechanged=1 and a.archiveid=t.archiveid and a.int1 = 1 and t.filetype = 1 and t.crimid in " + strcrimids;
					} else if (GkzxCommon.TWO.equals(updateflg)){
						//检查TBFLOW_ARCHIVES_WATCH表里的监狱的数据是否已经变化
						basesql = "select t.archiveid,a.docconent from TBFLOW_ARCHIVES_WATCH t,TBFLOW_ARCHIVES a where t.localchanged=1 and a.archiveid=t.archiveid and a.int1 = 1 and t.filetype = 1 and t.crimid in " + strcrimids;
					}
					System.out.println("取得档案的SQL：" + basesql);
					try {
						localps = localconn.prepareStatement(basesql);
						localrs = localps.executeQuery();
						dataAll = JdbcConnUtil.ResultSetToList(localrs);
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						JdbcConnUtil.closeResource(null, localps, localrs);
					}
					for (int x = 0; x < dataAll.size();x++) {
						HashMap map = (HashMap) dataAll.get(x);
						docconent = StringNumberUtil.getStringByMap(map, "docconent");
						if (!StringNumberUtil.isNullOrEmpty(docconent)){
							System.out.println("档案的路径：" + docconent);
							filePath.add(docconent);
							archiveids.add(StringNumberUtil.getStringByMap(map, "archiveid"));
						}
					}
					
				} else {
					//未配置
					continue;
				}
				
				
				//TBFLOW_ARCHIVES_WATCH的更新标记；1：更新监狱 2：更新省局
				//文件已经获取，标记为已处理
				if (GkzxCommon.ONE.equals(updateflg)) {
					//TBFLOW_ARCHIVES_WATCH表里的监狱的更新时间
					basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.localchanged=2, x.localchangedtime = ?,x.localdonetime=x.remotechangedtime where x.archiveid= ? ";
				} else if (GkzxCommon.TWO.equals(updateflg)){
					//TBFLOW_ARCHIVES_WATCH表里的省局的更新时间
					basesql = " update TBFLOW_ARCHIVES_WATCH x  set x.remotechanged=2, x.remotechangedtime = ?,x.remotedonetime = localchangedtime where x.archiveid= ? ";
				}
				
				boolean hasChanged = false;
				localps = localconn.prepareStatement(basesql);
				System.out.println("档案路径的个数：" + filePath.size());
				for (int z = 0; z < filePath.size(); z++) {
					String relativePath = filePath.get(z);
					relativePath = relativePath.replace("\\", "/");
						String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, relativePath);
					
//					FileUtil.saveToFile(docconent, targetPath, GkzxCommon.encoding);
					
//					String savePath = absolutePath.getAbsolutePathAppend(GkzxCommon.SINGLESEPALINE + list.get(i).getLocaladdress() +GkzxCommon.SINGLESEPALINE+criminalidList.get(j)+type);
					//System.out.println("--------------------"+savePath+"---------------------");
					
					try {
						System.out.println("档案源路径URL：" + jgxtaddress+GkzxCommon.SINGLESEPALINE+relativePath);
						fileCopyDAO.saveToFile(jgxtaddress+GkzxCommon.SINGLESEPALINE+relativePath, targetPath);
						
						File   f   =   new   File(targetPath); 
						long   modify =   f.lastModified(); //   修改时间 
						String strModTime = sdff.format(new Date(modify)); 
						localps.setString(1, strModTime);
						localps.setString(2, archiveids.get(z));
						localps.addBatch();
						hasChanged = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (hasChanged) {
					localps.executeBatch();
				}
				
				JdbcConnUtil.closeResource(null, localps, localrs);
			}catch(Exception e){
				e.printStackTrace();
			} finally {
				JdbcConnUtil.closeResource(null, localps, localrs);
			}
		}	
	}
	
	/**
	 * //远程服务器图片地址格式（以/结尾）"http://192.168.0.122:9001/newszpmis/scripts/";
	 * //图片保存位置格式（相对路径） "ann/";
	 * @param str  单位编码
	 * @param type  文件类型
	 * szjy_picture/4405008383_11.jpg
	 */
//	public void copy(String str,String type){
//		DbmsFileCopyServiceImpl fileCopyDAO = new DbmsFileCopyServiceImpl();
////    	UrlDownload oInstance = new UrlDownload();
//    	GetAbsolutePath absolutePath=new GetAbsolutePath();
////    	JsonTool jsonTool = new JsonTool();
//		ArrayList<FileCopy> list=fileCopyDAO.pathlist(str);
//		for(int i=0;i<list.size();i++){
//			String sdidString = jsonTool.getSdidString(list.get(i).getSdid());
//			ArrayList<String> criminalidList = fileCopyDAO.getcriminalidList(sdidString);
//		    for(int j=0;j<criminalidList.size();j++){
//    			try{
//    				//导出时路径
//    				String savePath = absolutePath.getAbsolutePath(GkzxCommon.SINGLESEPALINE + list.get(i).getLocaladdress() +GkzxCommon.SINGLESEPALINE+criminalidList.get(j)+type);
//    				//System.out.println("--------------------"+savePath+"---------------------");
//    				oInstance.saveToFile(list.get(i).getJgxtaddress()+GkzxCommon.SINGLESEPALINE+criminalidList.get(j)+type, savePath);
//    			}catch(Exception e){
//    				e.printStackTrace();
//    			}
//    		}	
//		}
//	}
	@Override
	public boolean addDataExportRequest(DbmsNewDataExportBean bean, TaskBean taskBean) {
		if (null == bean) {
			return false;
		}
		//
		boolean result = _addDataExportRequest(bean, taskBean);
		//
		return result;
	}

	/**
	 * 查询导出数据 <br/>
	 * 这里在原先的基础上加入了导出数据是的进度计算
	 */

	private boolean _addDataExportRequest(DbmsNewDataExportBean bean, TaskBean taskBean) {
		// 防御编程
		if(null == bean || null == taskBean){
			return false;
		}
		String ddcid = bean.getDdcid();
		String password = bean.getPassword();
		if (StringNumberUtil.isEmpty(ddcid)) {
			return false;
		}
		// 对应的变量
		int sum = 0;
		int tem = 0;
		String basePath = "";
		String fname = "";
		String ddcname = "";
		boolean isPackageper = false;// 分包?打包?
		List<Object> criminalidList = new ArrayList<Object>();
		String fileNameN = "";
		String fileRealName = "";
		String filepath = "";

		String type = bean.getOperatetype();
		TransformerHandler handler = null;
		OutputStream outStream = null;
		SAXTransformerFactory fac = null;
		String maintablesql = "";

		String chaxuncon = "";
		if (null != bean.getCondition() && !"".equals(bean.getCondition())) {
			chaxuncon = bean.getCondition();
		} else if (null != bean.getHiddencon() && !"".equals(bean.getHiddencon())) {
			chaxuncon = bean.getHiddencon();
		}
		String strSdid = "";
		Connection conn = null;
		Connection localconn = null;
		PreparedStatement localps = null;
		ResultSet localrs = null;
		String exportnotableheader = getJyConfigValue("exportnotableheader", "");
		try {
			localconn = JdbcConnUtil.getConn();
			List<String> titlelist = new ArrayList<String>();
			List<String> valuelist = new ArrayList<String>();
			// //判断方案操作数据库类型
			String basesql = "select * from DBMS_DATABASE_NEW a where a.ddid in (select fromdatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='"
					+ bean.getDdcid() + "')";
			localps = localconn.prepareStatement(basesql);
			localrs = localps.executeQuery();
			ArrayList dataAll = JdbcConnUtil.ResultSetToList(localrs);

			String basetype = "";
			DbmsDatabaseNew bs = null;
			
			if (dataAll.size() > 0) {
				HashMap map = (HashMap) dataAll.get(0);
				bs = parseDatabaseNew(map);
				basetype = bs.getDatabasetype();
				strSdid = bs.getSdid();
			}
			JdbcConnUtil.closeResource(null, localps, localrs);
			conn = JdbcConnUtil.getConnection(bs);

			Object sdidobj = null;
			/**
			 * 获取导出方案名 2013-11-23
			 */


			String sql = "select * from DBMS_DATAS_CHEME_NEW a where a.ddcid ='" + bean.getDdcid() + "'";
			localps = localconn.prepareStatement(sql);
			localrs = localps.executeQuery();
			ArrayList fangan = JdbcConnUtil.ResultSetToList(localrs);
			JdbcConnUtil.closeResource(null, localps, localrs);
			if (fangan.size() > 0) {
				HashMap map = (HashMap) fangan.get(0);
				ddcname = StringNumberUtil.getStringByMap(map, "ddcname");
			}

			bean.setBasetype(basetype);
			String daochusql = "";
			int totalnum = 0;
			if (!StringNumberUtil.isNullOrEmpty(bean.getPackageper()) && (GkzxCommon.ONE.equals(bean.getPackageper()))) {
				isPackageper = true;
			}
			boolean hasCriminalId = false;

			String biaosql = "select * from DBMS_DATAS_TABLE_NEW a where a.ddcid='" + bean.getDdcid()
					+ "' and a.ddtismaintable='0'";
			// 构造主表查询条件开始
			localps = localconn.prepareStatement(biaosql);
			localrs = localps.executeQuery();
			dataAll = JdbcConnUtil.ResultSetToList(localrs);
			List<DbmsDatasTableNew> biaolist = getDatasTableNew(dataAll);
			JdbcConnUtil.closeResource(null, localps, localrs);

			String COLUMN_NAME = "";
			for (Object biaoobj : biaolist) {
				DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
				String value = "";
				DatabaseMetaData dbmd = conn.getMetaData();
				// 获得字段信息
//				ResultSet rsColumns = null;
//				if ("Oracle".equals(basetype)||"Dameng".equals(basetype))
//					rsColumns = dbmd.getColumns(null, bs.getDatabaseuser().toUpperCase(), thetable.getTablename()
//							.toUpperCase(), null);
//				else
//					rsColumns = dbmd.getColumns(null, null, thetable.getTablename().toUpperCase(), null);
//
//				while (rsColumns.next()) {
//					try {
//						String COLUMN_NAME = rsColumns.getString("COLUMN_NAME");
//						if (GkzxCommon.CRIMID.equalsIgnoreCase(COLUMN_NAME)||COLUMN_NAME.toUpperCase().contains(GkzxCommon.CRIMID_AS)) {
//							hasCriminalId = true;
//							break;
//						}
//					} catch (Exception e) {
//					}
//				}
				//2015年4月5日 罪犯编号的判断方式变更开始
				String ddtid = thetable.getDdtid();
				// 查询所有列
				String liesql = " select * from DBMS_DATAS_CHEME_DETAIL_NEW a "
							  + " where a.ddcid='" + ddcid + "' "
							  + " and a.ddtid='" + ddtid + "'";
				//
				List<Map<String,Object>> lieDataList = parseSQL2List(liesql, localconn);
				List<DbmsDatasChemeDetailNew> lielist = getDatasChemeDetailNew(lieDataList);
				//
				
				if(null != lielist && lielist.size() > 0){
					for (int y=0;y<lielist.size();y++) {
						DbmsDatasChemeDetailNew dbChemeDetailNew = (DbmsDatasChemeDetailNew)lielist.get(y);
						try {
							COLUMN_NAME = dbChemeDetailNew.getDcdfromcolumns();
							if (GkzxCommon.CRIMID.equalsIgnoreCase(COLUMN_NAME)||COLUMN_NAME.toUpperCase().contains(GkzxCommon.CRIMID_AS)) {
								hasCriminalId = true;
								break;
							}
						} catch (Exception e) {
						}
					}
				}
				//2015年4月5日 罪犯编号的判断方式变更结束
				
				daochusql = thetable.getDaochusql();
				if ("0".equals(thetable.getDdtismaintable())) {
					daochusql = daochusql + " a where 1=1";
					// 额外查询条件追加开始 2013-11-19
					String addconditionString = thetable.getAddcondition();
					if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
						daochusql = daochusql + "  and a." + addconditionString;
					}
					// 额外查询条件追加结束2013-11-19
					if (!"".equals(chaxuncon)) {
						daochusql = daochusql + " and " + chaxuncon;
					}
//					if (hasCriminalId) {
//						daochusql = daochusql
//								+ " and a.crimid in ( select crimid from TBPRISONER_BASEINFO t where 1= 1 "
//								+ " )";
//					}
					// maintablesql=thetable.getDaochusql();
					maintablesql = daochusql;
					if (bean.getConditionMessage() != null && !bean.getConditionMessage().equals("")) {
						String[] conditionVal = bean.getConditionMessage().split(";");
						for (String condition : conditionVal) {
							daochusql = daochusql + " and a." + condition;
						}
					}
				}
			}
			// 构造主表查询条件结束
			// 分包导出查询开始2013-12-22
			if (hasCriminalId) {
				int intIndex = maintablesql.toLowerCase().indexOf("from");
				String strPackagePerSql = "select  " + COLUMN_NAME + "  " + maintablesql.substring(intIndex);
				strPackagePerSql = "select  tbc.crimid,tbc.orgid  from Tbprisoner_Base_Crime tbc where tbc.crimid in (  " + strPackagePerSql + ")";
				criminalidList = getCriminalidLst(conn, strPackagePerSql);

			}

			Date now = new Date();
			fname = getCurrentTimestampStr();
			// 导出时路径,得到项目绝对路径
			basePath = GetAbsolutePath.getAbsolutePath(GkzxCommon.SINGLESEPALINE + GkzxCommon.PATH + ddcname);
			basePath += fname + GkzxCommon.SINGLESEPALINE;
			//basepPath类似：D:\Program Files\Apache Software Foundation\Tomcat 6.0\webapps\pmsys\DownExcel\减刑案件导出方案20150128210909759\
			File file = new File(basePath);
			if (!file.exists()) {
				file.mkdirs();// 不是 makedir
			}
			fileRealName = ddcname + fname + ".zip";
			filepath = GkzxCommon.SINGLESEPALINE + GkzxCommon.PATH + fileRealName;

			boolean isFirstExp = true;
			String strKeepMaintablesql = maintablesql;
			// 分包导出查询结束2013-12-22
			for (int ppx = 0;(isPackageper &&  ppx < criminalidList.size() )|| isFirstExp; ppx++) {
				// 通过isFirstExp和criminalidList控制导出至少一次。
				isFirstExp = false;
				sum = 0;
				maintablesql = strKeepMaintablesql;
				String appendCriminalidCondition = "";
				// 分包导出时的罪犯编号
				if (isPackageper && criminalidList.size() > 0) {
					HashMap criminalidMap = (HashMap) criminalidList.get(ppx);
					appendCriminalidCondition = criminalidMap.get("crimid") == null ? "" : criminalidMap.get(
							"crimid").toString();
				}

				if (!StringNumberUtil.isNullOrEmpty(appendCriminalidCondition)) {
					maintablesql += " and a.crimid = '" + appendCriminalidCondition + "'";
				}
				//fname = String.valueOf(now.getTime()) + ".xml";
				fname = getCurrentTimestampStr() + ".xml";
				fileNameN = basePath + appendCriminalidCondition + "_" + fname;

				if ("daochu".equals(bean.getOperate())) {

					biaosql = "select * from DBMS_DATAS_TABLE_NEW a where a.ddcid='" + bean.getDdcid()
							+ "' order by a.ddtorder";
					// 构造主表查询条件开始
					localps = localconn.prepareStatement(biaosql);
					localrs = localps.executeQuery();
					dataAll = JdbcConnUtil.ResultSetToList(localrs);
					biaolist = getDatasTableNew(dataAll);
					JdbcConnUtil.closeResource(null, localps, localrs);

					fac = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
					handler = fac.newTransformerHandler();
					Transformer transformer = handler.getTransformer();
					transformer.setOutputProperty(OutputKeys.ENCODING, GkzxCommon.encoding);
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
					file = new File(fileNameN);
					if (!file.exists()) {
						file.createNewFile();
					}
					outStream = new FileOutputStream(fileNameN);
					handler.setResult(new StreamResult(outStream));
					String value = "";
					// 写入数据
					handler.startElement("", "", "dcfangan", null);
					handler.startElement("", "", "dcfanganddcid", null);
					value = bean.getDdcid();
					handler.characters(value.toCharArray(), 0, value.length());
					handler.endElement("", "", "dcfanganddcid");
				}
				localps = localconn.prepareStatement(biaosql);
				localrs = localps.executeQuery();
				dataAll = JdbcConnUtil.ResultSetToList(localrs);
				biaolist = getDatasTableNew(dataAll);
				JdbcConnUtil.closeResource(null, localps, localrs);

				// 导出条数追加2013-12-4开始
				for (Object biaoobj : biaolist) {
					DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
					daochusql = thetable.getDaochusql();
					if ("0".equals(thetable.getDdtismaintable())) {
						daochusql = maintablesql;
					} else {
						// 其他表根据数据关系生成查询条件
						if (null != thetable.getShujuguanxi() && !"".equals(thetable.getShujuguanxi())) {
							String[] temparr = thetable.getShujuguanxi().split(",");
							String tempcon = "";
							for (String str : temparr) {
								String[] temp2 = str.split("=");
								if ("".equals(tempcon)) {
									tempcon = "a." + temp2[0] + "=b." + temp2[1];
								} else {
									tempcon += " and a." + temp2[0] + "=b." + temp2[1];
								}
							}
							daochusql = daochusql + " b where 1=1";
							// 额外查询条件追加开始 2013-11-19
							String addconditionString = thetable.getAddcondition();
							if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
								daochusql = daochusql + " and b." + addconditionString;
							}
							// 额外查询条件追加结束 2013-11-19
							if (!"".equals(tempcon)) {
								// daochusql=daochusql+" and exists ("+maintablesql+" a where "+tempcon;
								daochusql = daochusql + " and exists (" + maintablesql + " and " + tempcon;
								if (!"".equals(chaxuncon)) {
									daochusql = daochusql + " and " + chaxuncon + ") ";
								} else {
									daochusql = daochusql + " ) ";
								}
							}
						}
					}
					if (!"".equals(daochusql)) {
						ResultSet rs = null;
						Statement stmt = null;

						try {
							stmt = conn.createStatement();
							String countsqlString = "select count(*) as aa from (" + daochusql + ") dd";
							sum += this.getSumCount(conn, countsqlString);

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (null != rs) {
								try {
									rs.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
							if (null != stmt) {
								try {
									stmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				 taskBean.setSum(sum);
				if ("daochu".equals(bean.getOperate())) {
					handler.startElement("", "", "dctotal", null);
					handler.characters(String.valueOf(sum).toCharArray(), 0, String.valueOf(sum).length());
					handler.endElement("", "", "dctotal");
				}
				// 导出条数追加2013-12-4结束

				for (Object biaoobj : biaolist) {
					DbmsDatasTableNew thetable = (DbmsDatasTableNew) biaoobj;
					String value = "";
					DatabaseMetaData dbmd = conn.getMetaData();
					// //获得字段信息
					if ("daochu".equals(bean.getOperate())) {
						// 写入表信息
						handler.startElement("", "", "dctable", null);
						handler.startElement("", "", "dctableddtid", null);
						value = thetable.getDdtid();
						handler.characters(value.toCharArray(), 0, value.length());
						handler.endElement("", "", "dctableddtid");

						handler.startElement("", "", "dctablename", null);
						value = thetable.getTablename();
						handler.characters(value.toCharArray(), 0, value.length());
						handler.endElement("", "", "dctablename");

						handler.startElement("", "", "dctabledescrition", null);
						value = thetable.getDescrition() == null ? "" : thetable.getDescrition();
						handler.characters(value.toCharArray(), 0, value.length());
						handler.endElement("", "", "dctabledescrition");

						handler.startElement("", "", "dctableddtismaintable", null);
						value = thetable.getDdtismaintable() == null ? "" : thetable.getDdtismaintable();
						handler.characters(value.toCharArray(), 0, value.length());
						handler.endElement("", "", "dctableddtismaintable");
					}
					// 查询所有列
					// String
					String liesql = "select * from DBMS_DATAS_CHEME_DETAIL_NEW a where a.ddcid='" + bean.getDdcid()
							+ "' and a.ddtid='" + thetable.getDdtid() + "' order by a.dcdifpkey asc,a.dcdid asc ";
					localps = localconn.prepareStatement(liesql);
					localrs = localps.executeQuery();
					dataAll = JdbcConnUtil.ResultSetToList(localrs);
					List<DbmsDatasChemeDetailNew> lielist = getDatasChemeDetailNew(dataAll);
					JdbcConnUtil.closeResource(null, localps, localrs);

					List<DbmsDatasChemeDetailNew> mylist = new ArrayList<DbmsDatasChemeDetailNew>();
					for (Object obj : lielist) {
						DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
						titlelist.add(detail.getDcdfromcolumnsscribe());
						mylist.add(detail);
						if (!exportnotableheader.equals(GkzxCommon.ONE)){
							if ("daochu".equals(bean.getOperate())) {
								handler.startElement("", "", "liexinxi", null);
								handler.startElement("", "", "liemingzi", null);
								///2015年4月6日，导出项目有别名时的处理开始
								String strColumn = detail.getDcdfromcolumns();
								int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
								if (intIndex > -1) {
									strColumn = strColumn.toLowerCase().substring(0,intIndex).trim();
								}										
								
	//							value = detail.getDcdfromcolumns();
								value = strColumn;
								///2015年4月6日，导出项目有别名时的处理结束
								
								value = value == null ? "" : value;
								handler.characters(value.toCharArray(), 0, value.length());
								handler.endElement("", "", "liemingzi");
	
								handler.startElement("", "", "lieshuoming", null);
								value = detail.getDcdfromcolumnsscribe();
								value = value == null ? "" : value;
								handler.characters(value.toCharArray(), 0, value.length());
								handler.endElement("", "", "lieshuoming");
	
								handler.startElement("", "", "lietype", null);
								value = detail.getDcdfromcloumnstype();
								value = value == null ? "" : value;
								handler.characters(value.toCharArray(), 0, value.length());
								handler.endElement("", "", "lietype");
	
								handler.endElement("", "", "liexinxi");
							}
						}
					}
					bean.setLielist(mylist);
					daochusql = thetable.getDaochusql();
					if ("0".equals(thetable.getDdtismaintable())) {
						// 前面已经构造，故直接赋值
						daochusql = maintablesql;
					} else {
						// 其他表根据数据关系生成查询条件
						if (null != thetable.getShujuguanxi() && !"".equals(thetable.getShujuguanxi())) {
							String[] temparr = thetable.getShujuguanxi().split(",");
							String tempcon = "";
							for (String str : temparr) {
								String[] temp2 = str.split("=");
								if ("".equals(tempcon)) {
									tempcon = "a." + temp2[0] + "=b." + temp2[1];
								} else {
									tempcon += " and a." + temp2[0] + "=b." + temp2[1];
								}
							}
							daochusql = daochusql + " b where 1=1";
							// 额外查询条件追加开始 2013-11-19
							String addconditionString = thetable.getAddcondition();
							if (!StringNumberUtil.isNullOrEmpty(addconditionString)) {
								daochusql = daochusql + " and b." + addconditionString;
							}
							// 额外查询条件追加结束 2013-11-19
							if (!"".equals(tempcon)) {
								daochusql = daochusql + " and exists (" + maintablesql + " and " + tempcon;
								if (!"".equals(chaxuncon)) {
									daochusql = daochusql + " and " + chaxuncon + ") ";
								} else {
									daochusql = daochusql + " ) ";
								}
							}
						}
					}
					if (!"".equals(daochusql)) {
						ResultSet rs = null;
						Statement stmt = null;
						try {
							stmt = conn.createStatement();
							String countsqlString = "select count(*) as aa from (" + daochusql + ") dd";
							// 如果不是导出 则查询时只查询前10条
							if (!"daochu".equals(bean.getOperate())) {
								if ("Microsoft SQL Server".equals(bs.getDatabasetype())
										|| "Microsoft SQL Server 2005".equals(bs.getDatabasetype())) {
									String[] arrStrings = daochusql.split("select");
									String aaaaa = "select top 100 ";
									for (int m = 1; m < arrStrings.length; m++) {
										aaaaa = aaaaa + arrStrings[m];
									}
									daochusql = aaaaa;
								}
								if ("Oracle".equals(bs.getDatabasetype())||"Dameng".equals(bs.getDatabasetype())) {
									daochusql = daochusql + " and rownum<100";
								}
							}
							rs = stmt.executeQuery(daochusql);
							while (rs.next()) {
								// 每条数据
								if ("daochu".equals(bean.getOperate())) {
									handler.startElement("", "", "dchang", null);
								}
								for (Object obj : lielist) {
									DbmsDatasChemeDetailNew detail = (DbmsDatasChemeDetailNew) obj;
									/////////
									String strColumn = detail.getDcdfromcolumns();
									String strXmlColumnName = strColumn;
									int intIndex = strColumn.toLowerCase().lastIndexOf(GkzxCommon.AS);
									if (intIndex > -1) {
										strXmlColumnName = strColumn.toLowerCase().substring(0,intIndex).trim();
										strColumn = strColumn.toLowerCase().substring(intIndex + 4).trim();
									}										
									String ttvalue = this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(),
											strColumn, rs);

									////////
//									String ttvalue = this.getcontentvalue(basetype, detail.getDcdfromcloumnstype(),
//											detail.getDcdfromcolumns(), rs);
									if (null == ttvalue) {
										ttvalue = "";
									}
									valuelist.add(ttvalue);
									// 写入列信息
									if ("daochu".equals(bean.getOperate())) {
										// 写入查询出的数据
										handler.startElement("", "", strXmlColumnName.equals(strColumn)?strColumn.toLowerCase():strXmlColumnName.toLowerCase(), null);
										value = ttvalue;
										handler.characters(value.toCharArray(), 0, value.length());
										handler.endElement("", "", strXmlColumnName.equals(strColumn)?strColumn.toLowerCase():strXmlColumnName.toLowerCase());
									}
								}

								tem++;
								taskBean.setCounter(tem);
								//							    
								//								
								// if(this.session != null){
								// this.session.put("taskbean", taskBean);
								// }

								if ("daochu".equals(bean.getOperate())) {
									handler.endElement("", "", "dchang");
								}
							}
							rs.close();
							stmt.close();
							stmt = conn.createStatement();
							rs = stmt.executeQuery(countsqlString);
							while (rs.next()) {
								totalnum = rs.getInt("aa");
							}
							rs.close();
							stmt.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (null != rs) {
								try {
									rs.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
							if (null != stmt) {
								try {
									stmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}
					if ("daochu".equals(bean.getOperate())) {
						handler.endElement("", "", "dctable");
					}
				}
				// 写入完成
				if ("daochu".equals(bean.getOperate())) {
					handler.endElement("", "", "dcfangan");
					handler.endDocument();
				}
				bean.setTitlelist(titlelist);
				bean.setTotalnum(totalnum);
				bean.setValuelist(valuelist);
			}
//			String srcPath = GetAbsolutePath.getAbsolutePath(GkzxCommon.SINGLESEPALINE + GkzxCommon.CRIMINALARCHIVES_PATH);
			
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
			String srcPath = GetAbsolutePath.getAbsolutePathAppend(strPath, GkzxCommon.CRIMINALARCHIVES_PATH);
			if (!StringNumberUtil.isNullOrEmpty(jyconfig.get(GkzxCommon.OUTPUTARCHIVESPDF))&&GkzxCommon.ONE.equals(jyconfig.get(GkzxCommon.OUTPUTARCHIVESPDF))){
				strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVESPDF_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVESPDF_ABSOLUTEPATH);
				srcPath = GetAbsolutePath.getAbsolutePathAppend(strPath, GkzxCommon.CRIMINALARCHIVES_PATH);
			}
			copyCriminalArchive(srcPath,basePath + GkzxCommon.CRIMINALARCHIVES_PATH,criminalidList);
			String zipfilepath = basePath.substring(0, basePath.lastIndexOf(GkzxCommon.SINGLESEPALINE)) + ".zip";
			DocZipUtil.compress(basePath, zipfilepath);
			if (GkzxCommon.FAYUAN.equals(type)) {
				dataSubmitToFrontMachine(null,zipfilepath);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			JdbcConnUtil.closeResource(localconn, localps, localrs);
			if (null != outStream) {
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		DbmsDaochuListInfo ddli = new DbmsDaochuListInfo();
		// 使用查询的UUID
		String queryuuid = bean.getQueryuuid();
		if (null != queryuuid && queryuuid.length() > 30) {
			queryuuid = queryuuid.substring(0, 30);
		}
		if (null == queryuuid || queryuuid.length() < 10) {
			queryuuid = UUID.randomUUID().toString();
		}
		//
		String fileid = queryuuid;
		ddli.setCreatedate(new Date());
		ddli.setFileid(fileid);
		ddli.setSchemeid(bean.getDdcid());
		String basicdep = "";
		try {
			// basicdep = (String)session.get("basicdep");
		} catch (Exception e) {
		}
		ddli.setSdid(strSdid);
		ddli.setFilename(ddcname);
		ddli.setFilepath(filepath);
		ddli.setFilerealname(fileRealName);
		int row = 0;
		try {
			row = daochuListInfoMapper.insertSelective(ddli);
			// baseDao.insertObject(ddli);
		} catch (Exception e) {
		}
		bean.setHiddencon(bean.getCondition());
		bean.setCondition("");
		if (row > 0) {
			return true;
		} else {
			// 考虑 return false;
		}
		return true;
	}


	public void copyCriminalArchive(String srcPath,String desPath,List<Object> criminalidList){
		for(int i = 0; i < criminalidList.size(); i++){
			HashMap criminalidMap = (HashMap) criminalidList.get(i);
			String desArchPath = desPath + File.separator + criminalidMap.get("orgid") + File.separator + criminalidMap.get("crimid");
			String srcArchPath = srcPath + File.separator + criminalidMap.get("orgid") + File.separator + criminalidMap.get("crimid");
			try {
				FileUtil.batchCopyFile(srcArchPath, desArchPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @Description: 将zip压缩包上传至前置机
	 */
	public boolean dataSubmitToFrontMachine(HttpServletRequest request,String fileids){
		 boolean rtnResult = true;
		 //
		 GetProperty promodel = new GetProperty();
		 Properties pro = promodel.bornProp(GkzxCommon.DATABASETYPE, null);
		 //
		 String ip = pro.getProperty("courtip");
		 int port = Integer.valueOf(pro.getProperty("courtport"));
		 String username = pro.getProperty("courtusername");
		 String password = pro.getProperty("courtpassword");
//		 String filefolder = pro.getProperty("courtfolder");//前置机上的FTP目录
//		 String xmlfolder = pro.getProperty("courtxmlfolder");
		 //
		 String rootpath = GetAbsolutePath.getAbsolutePath("");
		 String beforeSplitZipFilepath = rootpath + GkzxCommon.BEFOREDATA;
		 //
		 File beforeSplitZipFilepathDir = new File(beforeSplitZipFilepath);
		 if(! beforeSplitZipFilepathDir.exists()){
			 beforeSplitZipFilepathDir.mkdir();
		 }
		 //
		 //获取ftp客户端的连
		 FTPClient ftp = FtpUtil.getFTPClient(ip, port, username,password);
		 try {
			if(! FtpUtil.isLoginSuccess(ftp)){
				throw new RuntimeException("创建FTPClient失败!");
			}
		 } catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("创建FTPClient失败!");
		 }
		 
		 //
		 int count = 0;
		 String[] fileidArr = fileids.split(",");
		 for (String fileid : fileidArr){
			 count ++;
			 DbmsDaochuListInfo daochuInfo = daochuListInfoMapper.selectByPrimaryKey(fileid);
			 
			 //zip文件的路径
			 String zipFilepath = daochuInfo.getFilepath();
			 String filerealname = daochuInfo.getFilerealname();
			 zipFilepath = rootpath + zipFilepath.substring(1);//要被split的文件的绝对路径
			 String filedir = filerealname.substring(0,filerealname.lastIndexOf(".zip"));//如：减刑假释报送法院数据20160531163317467
			 String splitZipFileParentDirPath =  beforeSplitZipFilepath + File.separator + filedir;
			 File splitZipFileParentDirPathFile = new File(splitZipFileParentDirPath);
			 if(!splitZipFileParentDirPathFile.exists()){
				 splitZipFileParentDirPathFile.mkdir();//生成目录
			 }
			 
			 setSessionAttribute(request, "percent", "正在切割第"+count+"个包，共"+fileidArr.length+"个包");
			 //文件切割
			 try {
				DocZipUtil.splitFile(zipFilepath, splitZipFileParentDirPath, 2*1024*1024);//文件切割，不超过2M
			 }catch (Exception e) {
				e.printStackTrace();
			 }
			 //
			 setSessionAttribute(request, "percent", "正在上传第"+count+"个包到服务器，共"+fileidArr.length+"个包");
			 //将目录上传至FTP服务器
			 FtpUtil.uploadDirectory(ftp,splitZipFileParentDirPath,GkzxCommon.BEFOREDATA);
			 //上传完成后删除目录
			 try{
				FileUtils.deleteDirectory(splitZipFileParentDirPathFile);
			 }catch (IOException e) {
				e.printStackTrace();
			 }
		 }
		 
		 //关闭FtpClient
		 FtpUtil.closeFTPClient(ftp);
		 setSessionAttribute(request,"percent","100%");
		 
		 return rtnResult;
	}
	
	private void setSessionAttribute(HttpServletRequest request, String key, String value){
		if(null != request){
			request.getSession(true).setAttribute(key, value);
		}
	}
	
	
	
	/**
	 * @Description: 获取前置机数据并组包
	 * 
	 */
	@Override
	public String getPackageAndMergeZip(SystemUser user, HttpServletRequest request){
		String result = "操作成功！";
		
		String departid = user.getDepartid();
		
		GetProperty promodel = new GetProperty();
		Properties pro = promodel.bornProp(GkzxCommon.DATABASETYPE, null);
		String ftpserver_datapath = pro.getProperty("ftpserver_datapath");
		
		//
		String rootpath = GetAbsolutePath.getAbsolutePath("");
		String uploadDataFilepath = rootpath + GkzxCommon.UPLOADDATA;
		File uploadDataDir = new File(uploadDataFilepath);
		if(! uploadDataDir.exists()){
			uploadDataDir.mkdir();
		}
		FTPClient ftp = getFTPClient();
		
		List<String> getFileList = null;
		String receivedRecord = uploadDataFilepath + File.separator + "received.d";
		try {
			//列出ftp服务器中的目录
			List<String> fileList = new ArrayList<String>();
//			FTPFile[] ftpDirs = ftp.listFiles(GkzxCommon.BEFOREDATA);
			FTPFile[] ftpFiles = ftp.listFiles(ftpserver_datapath);
			for(FTPFile ftpFile : ftpFiles){
				if(ftpFile.isFile()){
					String filename = ftpFile.getName();
					if(! ".".equals(filename) && ! "..".equals(filename)){//过滤掉当前目录和上级目录名
						
						String[] tempArr = filename.split("_");
						if(tempArr[0].equals(departid)){
							fileList.add(ftpFile.getName());//只获取发送给本单位的数据包
						}
					}
				}
				
			}

			File receivedRecordFile = new File(receivedRecord);
			if(! receivedRecordFile.exists()){
				getFileList = fileList;
			}else{
				//已经从ftp获取的目录数据
				List<String> haveReceivedFileList = TxtUtil.readTxtFileByLine(receivedRecord, GkzxCommon.encoding);
				getFileList = filterHaveReceivedDir(fileList,haveReceivedFileList);
			}
			
			
			if(null != getFileList && ! getFileList.isEmpty()){
				int count = 0;
				for(String filename : getFileList){
					count ++;
					//从FTP服务器下载
					this.setSessionAttribute(request, "percent", "正在下载第"+count+"个包，一共有"+getFileList.size()+"个包");
					FtpUtil.downloadFile(ftp,ftpserver_datapath, filename,uploadDataFilepath);
					//已完成的操作写一条记录
					TxtUtil.writeAppend(receivedRecord, filename, GkzxCommon.encoding);
				}
			}else{
				result = "没有新的数据！";
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			result = "数据获取失败！";
		}finally{
			//关闭FtpClient
			FtpUtil.closeFTPClient(ftp);
		}
		
		return result;
	}
	
	private List<String> filterHaveReceivedDir(List<String> dirList,List<String> haveReceivedDirList){
		List<String> result = new ArrayList<String>();
		for(String dir : dirList){
			result.add(dir);
			for(String haveReceivedDir : haveReceivedDirList){
				if(dir.equals(haveReceivedDir)){
					result.remove(dir);
					continue;
				}
			}
		}
		return result;
	}
	
	
	private FTPClient getFTPClient(){
		//
		GetProperty promodel = new GetProperty();
		Properties pro = promodel.bornProp(GkzxCommon.DATABASETYPE, null);
		//
		String ip = pro.getProperty("courtip");
		int port = Integer.valueOf(pro.getProperty("courtport"));
		String username = pro.getProperty("courtusername");
		String password = pro.getProperty("courtpassword");
		
		//获取ftp客户端的连
		FTPClient ftp = FtpUtil.getFTPClient(ip, port, username,password);
		try {
			if(! FtpUtil.isLoginSuccess(ftp)){
				throw new RuntimeException("创建FTPClient失败!");
			}
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("创建FTPClient失败!");
		}
		
		return ftp;
	}
	
	
	
	// 调用数据交换的存储过程,内部根据配置信息自己判断
	public boolean callInterchangeProcedure(Connection conn, String sdid) {
		if (null == conn) {
			return false;
		}
		// 1. 获取参数,决定调用的存储过程
		// 默认 天津
		String i_orgid = sdid;
		String procedureName = "CALL_TIANJIN_PROC";
		// 根据配置参数修改
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);

		String datainterProcedureName = jyconfig.getProperty("datainterProcedureName");
		// String provincecode = jyconfig.getProperty("provincecode");
		//
		if (StringNumberUtil.notEmpty(datainterProcedureName)) {
			procedureName = datainterProcedureName.trim();
		}
		// if(StringNumberUtil.notEmpty(provincecode)){
		// i_orgid = provincecode.trim();
		// }
		//
		String sql = "{call " + procedureName + "(?)}";
		log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + ";sql=" + sql);

		// 2. 用我们自己的连接调用存储过程
		CallableStatement statement = null;
		try {
			statement = conn.prepareCall(sql);
			statement.setString(1, i_orgid);
			// statement.registerOutParameter(2, Types.INTEGER); // 注册输出参数, 如果有
			//
			// int out = statement.getInt(2); // 获取输出参数
			//
			// boolean result =
			statement.execute();
			return true;
		} catch (SQLException e) {
			log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + ";调用存储过程失败,sql=" + sql
					+ " ;i_orgid=" + i_orgid);
			e.printStackTrace();
		} finally {
			JdbcConnUtil.close(statement);
		}
		//
		return false;
	}

	// 调用数据交换的存储过程,内部根据配置信息自己判断
	public boolean callInterchangeProcedure(Connection conn, String sdid, String strDefaultProc) {
		if (null == conn) {
			return false;
		}
		// 1. 获取参数,决定调用的存储过程
		// 默认 天津
		String i_orgid = sdid;
		String procedureName = "PUBLIC_TBFLOW_CASE";
		// 根据配置参数修改
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);

		String datainterProcedureName = jyconfig.getProperty("datainterreatimeProcedureName");
		// String provincecode = jyconfig.getProperty("provincecode");
		//
		if (StringNumberUtil.notEmpty(datainterProcedureName)) {
			procedureName = datainterProcedureName.trim();
		}
		if (StringNumberUtil.notEmpty(strDefaultProc)) {
			procedureName = strDefaultProc;
		}
		// if(StringNumberUtil.notEmpty(provincecode)){
		// i_orgid = provincecode.trim();
		// }
		//
		String sql = "{call " + procedureName + "(?)}";
		log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + ";sql=" + sql);

		// 2. 用我们自己的连接调用存储过程
		CallableStatement statement = null;
		try {
			statement = conn.prepareCall(sql);
			statement.setString(1, i_orgid);
			// statement.registerOutParameter(2, Types.INTEGER); // 注册输出参数, 如果有
			//
			// int out = statement.getInt(2); // 获取输出参数
			//
			// boolean result =
			statement.execute();
			return true;
		} catch (SQLException e) {
			log(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()) + ";调用存储过程失败,sql=" + sql
					+ " ;i_orgid=" + i_orgid);
			e.printStackTrace();
		} finally {
			JdbcConnUtil.close(statement);
		}
		//
		return false;
	}
	
	private List<DbmsCodeChemeNew> getCodeChemeNew(List<Map<String, Object>> arrayList) {
		List<DbmsCodeChemeNew> newObjectList = new ArrayList<DbmsCodeChemeNew>();
		for (int i = 0; i < arrayList.size(); i++) {
			HashMap map = (HashMap) arrayList.get(i);
			DbmsCodeChemeNew dbmsCodeChemeNew = new DbmsCodeChemeNew();
			// DbmsCodeChemeNewId id = new DbmsCodeChemeNewId();
			// id.setCodetype(StringNumberUtil.getStringByMap(map, "codetype"));
			// id.setDccid(StringNumberUtil.getStringByMap(map, "dccid"));
			// dbmsCodeChemeNew.setId(id);
			// DbmsCodeTypeNew codeTypeNew = new DbmsCodeTypeNew();
			dbmsCodeChemeNew.setCodeid(StringNumberUtil.getStringByMap(map, "codeid"));
			dbmsCodeChemeNew.setCodecontent(StringNumberUtil.getStringByMap(map, "codecontent"));
			dbmsCodeChemeNew.setTargetid(StringNumberUtil.getStringByMap(map, "targetid"));
			newObjectList.add(dbmsCodeChemeNew);
		}
		return newObjectList;
	}

	private List<DbmsDatasTableNew> getDatasTableNew(List<Map<String,Object>> mapList) {
		List<DbmsDatasTableNew> newObjectList = new ArrayList<DbmsDatasTableNew>();
		for (int i = 0; i < mapList.size(); i++) {
			Map<String,Object> map = mapList.get(i);
			DbmsDatasTableNew dbmsDatasTableNew = new DbmsDatasTableNew();
			dbmsDatasTableNew.setDdcid(StringNumberUtil.getStringByMap(map, "ddcid"));
			dbmsDatasTableNew.setDdtid(StringNumberUtil.getStringByMap(map, "ddtid"));
			dbmsDatasTableNew.setTablename(StringNumberUtil.getStringByMap(map, "tablename"));
			dbmsDatasTableNew.setDescrition(StringNumberUtil.getStringByMap(map, "descrition"));
			dbmsDatasTableNew.setDdtorder(StringNumberUtil.getIntegerByMap(map, "ddtorder"));
			dbmsDatasTableNew.setDdtisscreen(StringNumberUtil.getStringByMap(map, "ddtisscreen"));
			dbmsDatasTableNew.setDdtismaintable(StringNumberUtil.getStringByMap(map, "ddtismaintable"));
			dbmsDatasTableNew.setDdtjoinedfields(StringNumberUtil.getStringByMap(map, "ddtjoinedfields"));
			dbmsDatasTableNew.setShujuguanxi(StringNumberUtil.getStringByMap(map, "shujuguanxi"));
			dbmsDatasTableNew.setDaochusql(StringNumberUtil.getStringByMap(map, "daochusql"));
			dbmsDatasTableNew.setTotable(StringNumberUtil.getStringByMap(map, "totable"));
			dbmsDatasTableNew.setAddcondition(StringNumberUtil.getStringByMap(map, "addcondition"));

			newObjectList.add(dbmsDatasTableNew);
		}
		return newObjectList;
	}

	private List<DbmsDatasChemeDetailNew> getDatasChemeDetailNew(List<Map<String,Object>> objList) {
		List<DbmsDatasChemeDetailNew> newObjectList = new ArrayList<DbmsDatasChemeDetailNew>();
		for (int i = 0; i < objList.size(); i++) {
			HashMap map = (HashMap) objList.get(i);
			DbmsDatasChemeDetailNew dbmsDatasChemeDetailNew = new DbmsDatasChemeDetailNew();
			dbmsDatasChemeDetailNew.setDcdid(StringNumberUtil.getStringByMap(map, "dcdid"));
			dbmsDatasChemeDetailNew.setDdcid(StringNumberUtil.getStringByMap(map, "ddcid"));
			dbmsDatasChemeDetailNew.setDdtid(StringNumberUtil.getStringByMap(map, "ddtid"));
			dbmsDatasChemeDetailNew.setDcdfromcolumns(StringNumberUtil.getStringByMap(map, "dcdfromcolumns"));
			dbmsDatasChemeDetailNew.setDcdfromcolumnsscribe(StringNumberUtil
					.getStringByMap(map, "dcdfromcolumnsscribe"));
			dbmsDatasChemeDetailNew.setDcdfromcloumnstype(StringNumberUtil.getStringByMap(map, "dcdfromcloumnstype"));
			dbmsDatasChemeDetailNew.setDcdfromcloumnssize(StringNumberUtil.getLongByMap(map, "dcdfromcloumnssize"));
			dbmsDatasChemeDetailNew.setDcdfromcloumnsdefaultvalue(StringNumberUtil.getStringByMap(map,
					"dcdfromcloumnsdefaultvalue"));
			dbmsDatasChemeDetailNew.setDcdtotableid(StringNumberUtil.getStringByMap(map, "dcdtotableid"));
			dbmsDatasChemeDetailNew.setDcdtocolumns(StringNumberUtil.getStringByMap(map, "dcdtocolumns"));
			dbmsDatasChemeDetailNew.setDcdtocolumnsscribe(StringNumberUtil.getStringByMap(map, "dcdtocolumnsscribe"));
			dbmsDatasChemeDetailNew.setDcdtocolumnstype(StringNumberUtil.getStringByMap(map, "dcdtocolumnstype"));
			dbmsDatasChemeDetailNew.setDcdtocolumnssize(StringNumberUtil.getLongByMap(map, "dcdtocolumnssize"));
			dbmsDatasChemeDetailNew.setDcdtocolumnspoint(StringNumberUtil.getIntegerByMap(map, "dcdtocolumnspoint"));
			dbmsDatasChemeDetailNew
					.setDcdfromcolumnspoint(StringNumberUtil.getIntegerByMap(map, "dcdfromcolumnspoint"));
			dbmsDatasChemeDetailNew.setDcdifpkey(StringNumberUtil.getStringByMap(map, "dcdifpkey"));
			dbmsDatasChemeDetailNew.setDcdorder(StringNumberUtil.getDoubleByMap(map, "dcdorder"));
			dbmsDatasChemeDetailNew.setDcdpkgenerator(StringNumberUtil.getStringByMap(map, "dcdpkgenerator"));
			dbmsDatasChemeDetailNew.setDcdcodetype(StringNumberUtil.getStringByMap(map, "dcdcodetype"));
			dbmsDatasChemeDetailNew.setDcdtocloumnsdefaultvalue(StringNumberUtil.getStringByMap(map,
					"dcdtocloumnsdefaultvalue"));
			dbmsDatasChemeDetailNew.setJsoncolumns(StringNumberUtil.getStringByMap(map, "jsoncolumns"));
			newObjectList.add(dbmsDatasChemeDetailNew);
		}
		return newObjectList;
	}

	private int getSumCount(Connection con, String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.closeResource(null, ps, rs);
		}
		return count;
	}

	private List getCriminalidLst(Connection con, String sql) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List objList = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			objList = JdbcConnUtil.ResultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.closeResource(null, ps, rs);
		}
		return objList;
	}

	private HashMap<String, HashMap<String, TbsysCode>> getLocalDbCodeScidRemark(Connection conn,
			ArrayList<String> strCodeList) {
		HashMap<String, HashMap<String, TbsysCode>> rtnMapCodeScidRemark = new HashMap<String, HashMap<String, TbsysCode>>();
		PreparedStatement localps = null;
		ResultSet localrs = null;
		String strCodeTypeIds = "";
		for (int i = 0; i < strCodeList.size(); i++) {
			if (i == 0) {
				strCodeTypeIds = "'" + strCodeList.get(i) + "'";
			} else {
				strCodeTypeIds += "," + "'" + strCodeList.get(i) + "'";
			}
		}

		if (!StringNumberUtil.isNullOrEmpty(strCodeTypeIds)) {
			if (strCodeTypeIds.indexOf("#") > -1) {
				strCodeTypeIds = strCodeTypeIds.replaceAll("#", "");
			}
			// String
			// sql="from TbsecurityCode a where a.id.tbsecurityCodeType.sctid in ("+strCodeTypeIds+") order by a.id.tbsecurityCodeType.sctid, nlssort(a.sccontent,'NLS_SORT=SCHINESE_PINYIN_M')";
			// List<Object> objList=baseDao.queryListObjectAll(sql);
			String sql = "select * from TBSYS_CODE a where CODETYPE in (" + strCodeTypeIds
					+ ") order by CODETYPE, nlssort(a.name,'NLS_SORT=SCHINESE_PINYIN_M')";
			try {
				localps = conn.prepareStatement(sql);
				localrs = localps.executeQuery();
				ArrayList<Map<String, Object>> objList = JdbcConnUtil.ResultSetToList(localrs);
				JdbcConnUtil.closeResource(null, localps, localrs);

				Integer intOldCodeType = 0;
				Integer intNewCodeType = 0;
				HashMap<String, TbsysCode> mapScidRemarkHashMap = new HashMap<String, TbsysCode>();
				for (int i = 0; i < objList.size(); i++) {
					Map<String, Object> map = objList.get(i);
					TbsysCode tbCode = parseTbsysCode(map);
					if (i == 0) {
						intNewCodeType = tbCode.getNoid();
						intOldCodeType = intNewCodeType;
					}
					intNewCodeType = tbCode.getNoid();
					if (intOldCodeType.equals(intNewCodeType)) {
						mapScidRemarkHashMap.put(tbCode.getNoid().toString(), tbCode);
					} else {
						rtnMapCodeScidRemark.put(intOldCodeType.toString(), mapScidRemarkHashMap);
						mapScidRemarkHashMap = new HashMap<String, TbsysCode>();
						mapScidRemarkHashMap.put(tbCode.getNoid().toString(), tbCode);
						intOldCodeType = intNewCodeType;
					}
				}

				if (!StringNumberUtil.isNullOrEmpty(intNewCodeType)) {
					rtnMapCodeScidRemark.put(intNewCodeType.toString(), mapScidRemarkHashMap);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcConnUtil.closeResource(null, localps, localrs);
			}
		}

		return rtnMapCodeScidRemark;
	}


	private String getcontentvalue(String basetype, String dcdfromcloumnstype, String dcdfromcolumns, ResultSet rs) {
		String value = "";
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null != dcdfromcolumns && !"".equals(dcdfromcolumns)) {
			int intIndex = dcdfromcolumns.toLowerCase().lastIndexOf(GkzxCommon.AS);
			if (intIndex > -1) {
				dcdfromcolumns = dcdfromcolumns.toLowerCase().substring(intIndex + 4).trim();
			}
			try {
				if ("Microsoft SQL Server".equals(basetype) || "Microsoft SQL Server 2005".equals(basetype)) {
					if ("varchar".equals(dcdfromcloumnstype) || "char".equals(dcdfromcloumnstype)
							|| "nchar".equals(dcdfromcloumnstype) || "nvarchar".equals(dcdfromcloumnstype)
							|| "text".equals(dcdfromcloumnstype) || "ntext".equals(dcdfromcloumnstype)
							|| "uniqueidentifier".equals(dcdfromcloumnstype) || "timestamp".equals(dcdfromcloumnstype)) {
						value = rs.getString(dcdfromcolumns);
						if (value != null) {
							// value=value.replaceAll("“", "&#34;");
							// value=value.replaceAll("”", "&#34;");
							value = value.trim().replaceAll("\"", "“").replaceAll("\"", "”").replaceAll("'", "‘")
									.replaceAll("'", "’");// .replaceAll("\\\r\\\n",
															// "\\r\\n").replaceAll("\\r\\n",
															// "\\r\\n").replaceAll("\r\n",
															// "\\r\\n").replaceAll("\r",
															// "\\r\\n").replaceAll("\n",
															// "\\r\\n");
//							if(value.getBytes(GkzxCommon.encoding).length > 4000) {
//								byte desBuf[] = new byte[4000-12];
//								InputStream inStreamDoc = null;
//								inStreamDoc = new ByteArrayInputStream( value.getBytes(GkzxCommon.encoding));
//								inStreamDoc.read(desBuf, 0, 4000-12);
//								value = new String(desBuf);
//								if (null != inStreamDoc) {
//									inStreamDoc.close();
//									inStreamDoc = null;
//								}
//							}
						}
					}
					if ("datetime".equalsIgnoreCase(dcdfromcloumnstype)) {
						java.sql.Timestamp zhi = rs.getTimestamp(dcdfromcolumns);
						if (null != zhi) {
							value = sinog2cDateFormater.format(zhi);
						}
					}
					if ("int".equals(dcdfromcloumnstype) || "int identity".equals(dcdfromcloumnstype)
							|| "tinyint".equals(dcdfromcloumnstype) || "smallint".equals(dcdfromcloumnstype)) {
						Integer temp = rs.getInt(dcdfromcolumns);
						if (null != temp) {
							value = String.valueOf(temp);
						}
					}
					if ("bit".equals(dcdfromcloumnstype)) {
						Integer zhi = rs.getInt(dcdfromcolumns);
						if (null != zhi) {
							value = String.valueOf(zhi);
						}
					}
					if ("bigint".equals(dcdfromcloumnstype)) {
						Long zhi = rs.getLong(dcdfromcolumns);
						if (null != zhi) {
							value = String.valueOf(zhi);
						}
					}
					if ("float".equals(dcdfromcloumnstype)) {
						Double zhi = rs.getDouble(dcdfromcolumns);
						if (null != zhi) {
							value = String.valueOf(zhi);
						}
					}
					if ("decimal".equals(dcdfromcloumnstype) || "money".equals(dcdfromcloumnstype)
							|| "smallmoney".equals(dcdfromcloumnstype) || "numeric".equals(dcdfromcloumnstype)) {
						BigDecimal zhi = rs.getBigDecimal(dcdfromcolumns);
						if (null != zhi) {
							value = String.valueOf(zhi);
						}
					}
					if ("real".equals(dcdfromcloumnstype)) {
						Float zhi = rs.getFloat(dcdfromcolumns);
						if (null != zhi) {
							value = String.valueOf(zhi);
						}
					}
					if ("image".equals(dcdfromcloumnstype) || "varbinary".equals(dcdfromcloumnstype)) {
						// value=rs.getString(dcdfromcolumns);
						InputStream inStreamDoc = null;
						try {
							Blob blob = rs.getBlob(dcdfromcolumns);//
							if (blob == null) {
								value = "";
							} else {
								inStreamDoc = blob.getBinaryStream();
								byte[] tempDoc = new byte[(int) blob.length()];
								inStreamDoc.read(tempDoc);
								value = encodeBase64(tempDoc);
								if (value != null) {
									value = value.replace("\r\n", "");
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						} finally {
							if (null != inStreamDoc) {
								inStreamDoc.close();
								inStreamDoc = null;
							}
						}
					}
					// if("smalldatetime".equals(dcdfromcloumnstype)||"datetime".equals(dcdfromcloumnstype)||"timestamp".equals(dcdfromcloumnstype)){
					// Timestamp zhi=rs.getTimestamp(dcdfromcolumns);
					// if(null!=zhi){
					// java.sql.Date sqlDate = new java.sql.Date(zhi.getTime());
					// value=sdf.format(sqlDate);
					// }
					// }
				}
				if ("Oracle".equals(basetype)||"Dameng".equals(basetype)) {
					if ("DATE".equals(dcdfromcloumnstype)) {
						java.sql.Timestamp zhi = rs.getTimestamp(dcdfromcolumns);
						if (null != zhi) {
							value = sinog2cDateFormater.format(zhi);
						}
					}
					if ("VARCHAR2".equals(dcdfromcloumnstype)) {
						value = rs.getString(dcdfromcolumns);
						if (value != null) {
							// value=value.replaceAll("“", "&#34;");
							// value=value.replaceAll("”", "&#34;");
							value = value.trim().replaceAll("\"", "“").replaceAll("\"", "”").replaceAll("'", "‘")
									.replaceAll("'", "’");// .replaceAll("\\\r\\\n",
															// "\\r\\n").replaceAll("\\r\\n",
															// "\\r\\n").replaceAll("\r\n",
															// "\\r\\n").replaceAll("\r",
															// "\\r\\n").replaceAll("\n",
															// "\\r\\n");
							if(value.getBytes(GkzxCommon.encoding).length > 4000) {
								byte desBuf[] = new byte[4000-12];
								InputStream inStreamDoc = null;
								inStreamDoc = new ByteArrayInputStream( value.getBytes(GkzxCommon.encoding));
								inStreamDoc.read(desBuf, 0, 4000-12);
								value = new String(desBuf);
								if (null != inStreamDoc) {
									inStreamDoc.close();
									inStreamDoc = null;
								}
							}
						}
					
					}
					if ("CHAR".equals(dcdfromcloumnstype)) {
						value = rs.getString(dcdfromcolumns);
					}
					if ("NUMBER".equals(dcdfromcloumnstype)) {
						BigDecimal temp = rs.getBigDecimal(dcdfromcolumns);
						if (null != temp) {
							value = String.valueOf(temp);
						}
					}
					if ("FLOAT".equals(dcdfromcloumnstype)) {
						BigDecimal temp = rs.getBigDecimal(dcdfromcolumns);
						if (null != temp) {
							value = String.valueOf(temp);
						}
					}
					if ("CLOB".equals(dcdfromcloumnstype)) {
						Reader inStreamDoc = null;
						try {
							Clob clob = rs.getClob(dcdfromcolumns);// 
							if (clob == null) {
								value = "";
							} else {
								inStreamDoc = clob.getCharacterStream();
								char[] tempDoc = new char[(int) clob.length()];
								inStreamDoc.read(tempDoc);
								value = new String(tempDoc);
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						} finally {
							if (null != inStreamDoc) {
								inStreamDoc.close();
								inStreamDoc = null;
							}
						}

					}
					if ("BLOB".equals(dcdfromcloumnstype)) {
						InputStream inStreamDoc = null;
						try {
							Blob blob = rs.getBlob(dcdfromcolumns);// 
							if (blob == null) {
								value = "";
							} else {
								inStreamDoc = blob.getBinaryStream();
								byte[] tempDoc = new byte[(int) blob.length()];
								inStreamDoc.read(tempDoc);
								value = encodeBase64(tempDoc);
								if (value != null) {
									value = value.replace("\r\n", "");
								}
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						} finally {
							if (null != inStreamDoc) {
								inStreamDoc.close();
								inStreamDoc = null;
							}
						}
						
					}
					if ("INTEGER".equals(dcdfromcloumnstype)) {
						BigDecimal temp = rs.getBigDecimal(dcdfromcolumns);
						if (null != temp) {
							value = String.valueOf(temp);
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null != value) {
				// value=value.replace("\r\n", "&#13;&#10;");
			}
		}
		return value;
	}

	/**
	 * 此方法描述的是：对查到的数据，根据数据类型进行解析
	 * 
	 * @param suid
	 *            ; daovalue:导入值;
	 * @param @return
	 * @Exception 异常对象
	 * @author: 李祺亮
	 * @version: 2013-7-12 上午11:40:22
	 */

	private String getDefaultValue(String suid, String daovalue, DbmsDatasChemeDetailNew c1, 
			 String sdid) {
		// 如果值为空 替换为默认值
		if (null == daovalue || "".equals(daovalue)) {
			if (null != c1.getDcdtocloumnsdefaultvalue() && !"".equals(c1.getDcdtocloumnsdefaultvalue())) {
				if (":nowdate".equals(c1.getDcdtocloumnsdefaultvalue())) {
					daovalue = sinog2cDateFormater.format(new Date());
				} else if (":suid".equals(c1.getDcdtocloumnsdefaultvalue())) {
					daovalue = suid;
				} else if (":sdid".equals(c1.getDcdtocloumnsdefaultvalue())) {
					daovalue = sdid;
				} else {
					daovalue = c1.getDcdtocloumnsdefaultvalue();
				}
			}
		}
		return daovalue;
	}
	/**
	 * 此方法描述的是：对查到的数据，根据数据类型进行解析
	 * 
	 * @param suid
	 *            ; daovalue:导入值;
	 *            c1:方案映射关系表;fk:yyyymmdd*10;valuemapMap:改行的所有的值;dchang
	 *            :未知;daimamap:代码映射
	 * @param tobs
	 *            :数据库管理表;jiexitype:解析类型（jiaohuan:daoru）;mapColumnType:字段及组合字段信息（
	 *            dbmx->GB002:dbqh:单位ID(可无)）
	 * @param mapCodeScidRemarkHashMap
	 *            ：code类型->scid:备注;orgValuemapMap:导入column->导入值
	 * @param @return
	 * @Exception 异常对象
	 * @author: 李祺亮
	 * @version: 2013-7-12 上午11:40:22
	 */

	private String jiexiValue(String suid, String daovalue, DbmsDatasChemeDetailNew c1, int fk,
			Map<String, String> valuemapMap, Element dchang, Map<String, List<DbmsCodeChemeNew>> daimamap, DbmsDatabaseNew tobs,
			String jiexitype, HashMap<String, ArrayList<String>> mapColumnType,
			HashMap<String, HashMap<String, TbsysCode>> mapCodeScidRemarkHashMap, Map<String, String> orgValuemapMap,
			String sdid) {

		String strFromColumn = c1.getDcdfromcolumns();
		if (!StringNumberUtil.isNullOrEmpty(strFromColumn)) {
			if (strFromColumn.indexOf("@") > -1) {
				// 该column的格式为：1000001:dbqh@[GB002]@dbmx；1000001:dbqh@GB002@dbmx；或者为：dbqh@GB002@dbmx；dbqh@[GB002]@dbmx
				String[] strTmpColumnStrings = strFromColumn.split("@");
				strFromColumn = strTmpColumnStrings[strTmpColumnStrings.length - 1].trim();
				// 常量表字段时，字段值置空
				if (addcolumnslst.indexOf(strFromColumn) > -1) {
					daovalue = "";
				}
				if (mapColumnType.containsKey(strFromColumn)) {
					// dbmx->GB002:dbqh:单位ID(可无)
					ArrayList<String> lstCodeTypeArrayList = mapColumnType.get(strFromColumn);
					HashMap<String, TbsysCode> mapScidRemarkHashMap = mapCodeScidRemarkHashMap.get(lstCodeTypeArrayList
							.get(0).replaceAll("#", ""));
					if (mapScidRemarkHashMap == null) {
						mapScidRemarkHashMap = new HashMap<String, TbsysCode>();
					}
					// dbmx->GB002:dbqh或者dbmx->[GB002]:dbqh
					if (lstCodeTypeArrayList.size() == 2) {
						String strFromColumnValue = orgValuemapMap.get(lstCodeTypeArrayList.get(1).toLowerCase());
						boolean getRemark = true;
						if (lstCodeTypeArrayList.get(0).indexOf("#") > -1) {
							getRemark = false;
						}
						if (!StringNumberUtil.isNullOrEmpty(strFromColumnValue)) {
							if (strJgxt.equalsIgnoreCase(tobs.getDatabasename())) {
								if (!mapScidRemarkHashMap.containsKey((strFromColumnValue))) {
									daovalue = daovalue.replaceAll(strFromColumnValue + GkzxCommon.PAUSE_MARK, "");
								} else {
									if (getRemark) {
										daovalue = daovalue.replaceAll(mapScidRemarkHashMap.get(strFromColumnValue)
												.getRemark(), "");
									} else {
										daovalue = daovalue.replaceAll(mapScidRemarkHashMap.get(strFromColumnValue)
												.getName(), "");
									}
								}
							} else {
								if (!mapScidRemarkHashMap.containsKey((strFromColumnValue))) {
									daovalue = strFromColumnValue + GkzxCommon.PAUSE_MARK + daovalue;
								} else {
									if (getRemark) {
										daovalue = mapScidRemarkHashMap.get(strFromColumnValue).getRemark() + daovalue;
									} else {
										daovalue = mapScidRemarkHashMap.get(strFromColumnValue).getName() + daovalue;
									}
								}
							}
						}
					} else if (lstCodeTypeArrayList.size() == 3) {// dbmx->GB002:dbqh:单位ID
						boolean getRemark = true;
						if (lstCodeTypeArrayList.get(0).indexOf("#") > -1) {
							getRemark = false;
						}

						String strFromColumnValue = orgValuemapMap.get(lstCodeTypeArrayList.get(1).toLowerCase());
						// GB002:dbqh:1000017
						List<DbmsCodeChemeNew> damamingxilist = daimamap.get(lstCodeTypeArrayList.get(2));
						if (null != damamingxilist && damamingxilist.size() > 0) {
							for (Object obj22 : damamingxilist) {
								DbmsCodeChemeNew code = (DbmsCodeChemeNew) obj22;
								if (strJgxt.equalsIgnoreCase(tobs.getDatabasename())) {
									if (null != code && strFromColumnValue.equals(code.getTargetid())) {
										if (mapScidRemarkHashMap.containsKey((code.getTargetid()))) {
											if (getRemark) {
												daovalue = daovalue.replaceAll(mapScidRemarkHashMap.get(
														code.getTargetid()).getRemark(), "");
											} else {
												daovalue = daovalue.replaceAll(mapScidRemarkHashMap.get(
														code.getTargetid()).getName(), "");
											}
										}
										break;
									}
								} else {
									if (null != code && strFromColumnValue.equals(code.getCodeid())) {
										if (!StringNumberUtil.isNullOrEmpty(code.getTargetid())
												&& mapScidRemarkHashMap.containsKey((code.getTargetid()))) {
											if (getRemark) {
												daovalue = mapScidRemarkHashMap.get(code.getTargetid()).getRemark()
														+ daovalue;
											} else {
												daovalue = mapScidRemarkHashMap.get(code.getTargetid()).getName()
														+ daovalue;
											}
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		// 如果值为空 替换为默认值
		if (null == daovalue || "".equals(daovalue)) {
			if (null != c1.getDcdtocloumnsdefaultvalue() && !"".equals(c1.getDcdtocloumnsdefaultvalue())) {
				if (":nowdate".equals(c1.getDcdtocloumnsdefaultvalue())) {
					// SimpleDateFormat sdf222=new
					// SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
					daovalue = sinog2cDateFormater.format(new Date());
				} else if (":suid".equals(c1.getDcdtocloumnsdefaultvalue())) {
					daovalue = suid;
				} else if (":sdid".equals(c1.getDcdtocloumnsdefaultvalue())) {
					daovalue = sdid;
				} else {
					daovalue = c1.getDcdtocloumnsdefaultvalue();
				}
			}
			if ("auto".equals(c1.getDcdpkgenerator())) {
				daovalue = String.valueOf(fk);
				fk++;
			}
		}
		if (null == daovalue || "".equals(daovalue)) {
		} else {
			if (null != c1.getDcdcodetype() && !"".equals(c1.getDcdcodetype())
					&& !"undefined".equals(c1.getDcdcodetype())) {
				if ("0000002".equals(c1.getDcdcodetype())) {
					if (daovalue.length() == 8) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat sdf222 = new SimpleDateFormat("yyyy-MM-dd");
						Date temp = null;
						;
						try {
							temp = sdf.parse(daovalue);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						daovalue = sdf222.format(temp);
					} else {
						daovalue = "";
					}
				}
				// 判定刑种
				else if ("0000012".equals(c1.getDcdcodetype())) {
					if ("9995".equals(daovalue)) {
						daovalue = "2";
					} else if ("9996".equals(daovalue)) {
						daovalue = "3";
					} else {
						daovalue = "1";
					}
				}
				// 判定刑期年
				else if ("0000013".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() == 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(0, 2), '0');
					} else if (null != daovalue && daovalue.length() < 6) {
						// 9990,9995,9996
						daovalue = StringNumberUtil.removeFrontExpression(daovalue, '0');
					} else {
						daovalue = "";
					}
				}
				// 判定刑期月
				else if ("0000014".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() == 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(2, 4), '0');
					} else {
						daovalue = "";
					}
				}
				// 判定刑期天
				else if ("0000015".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() == 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(4, 6), '0');
					} else {
						daovalue = "";
					}
				}
				// 判定剥权年
				else if ("0000018".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() == 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(0, 2), '0');
					} else if (null != daovalue && daovalue.length() <= 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(0, 2), '0');
					} else {
						daovalue = "";
					}
				}
				// 判定剥权月
				else if ("0000019".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() == 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(2, 4), '0');
					} else {
						daovalue = "";
					}
				}
				// 判定剥权天
				else if ("0000020".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() == 6) {
						daovalue = StringNumberUtil.removeFrontExpression(daovalue.substring(4, 6), '0');
					} else {
						daovalue = "";
					}
				}
				// //截取案件号
				else if ("0000017".equals(c1.getDcdcodetype())) {
					// if(null!=daovalue&&daovalue.length()>4 &&
					// daovalue.length()==10){
					// daovalue=daovalue.substring(daovalue.length()-3,daovalue.length());
					// }else{
					// daovalue="";
					// }
					if (null != daovalue) {
						Pattern pattern = Pattern.compile("[0-9]*");
						if (pattern.matcher(daovalue).matches()) {// 判断是否数字
							if (daovalue.length() > 4 && (daovalue.length() >= 7)) {
								daovalue = daovalue.substring(daovalue.length() - 4, daovalue.length());
							} else if (daovalue.length() == 4) {
								daovalue = daovalue.substring(2, daovalue.length());
							}
						}
					} else {
						daovalue = "";
					}
				}
				// 截取案件年
				else if ("0000016".equals(c1.getDcdcodetype())) {
					if (null != daovalue && daovalue.length() > 4 && daovalue.length() == 10) {
						daovalue = daovalue.substring(0, 4);
					} else {
						daovalue = "";
					}
				}
				// 四史之吸毒史、四涉之涉毒
				else if ("0000040".equals(c1.getDcdcodetype()) || "0000046".equals(c1.getDcdcodetype())) {
					if (!StringNumberUtil.isNullOrEmpty(daovalue)) {
						if (daovalue.indexOf("1") > -1) {
							daovalue = "1";
						} else {
							daovalue = "";
						}
					} else {
						daovalue = "";
					}
				}
				// 四史之脱逃史、四涉之涉枪
				else if ("0000041".equals(c1.getDcdcodetype()) || "0000047".equals(c1.getDcdcodetype())) {
					if (!StringNumberUtil.isNullOrEmpty(daovalue)) {
						if (daovalue.indexOf("2") > -1) {
							daovalue = "1";
						} else {
							daovalue = "";
						}
					} else {
						daovalue = "";
					}
				}
				// 四史之自杀史、四涉之涉黑
				else if ("0000042".equals(c1.getDcdcodetype()) || "0000048".equals(c1.getDcdcodetype())) {
					if (!StringNumberUtil.isNullOrEmpty(daovalue)) {
						if (daovalue.indexOf("3") > -1) {
							daovalue = "1";
						} else {
							daovalue = "";
						}
					} else {
						daovalue = "";
					}
				}
				// 四史之袭警史、四涉之涉恶
				else if ("0000043".equals(c1.getDcdcodetype()) || "0000049".equals(c1.getDcdcodetype())) {
					if (!StringNumberUtil.isNullOrEmpty(daovalue)) {
						if (daovalue.indexOf("4") > -1) {
							daovalue = "1";
						} else {
							daovalue = "";
						}
					} else {
						daovalue = "";
					}
				}
				// 负1转为空
				else if ("1100000".equals(c1.getDcdcodetype())) {
					if (!StringNumberUtil.isNullOrEmpty(daovalue)) {
						if (daovalue.equals(GkzxCommon.MINUS_ONE)) {
							daovalue = "";
						}
					} else {
						daovalue = "";
					}
				}
				// 拼凑案件号
				else if ("1000014".equals(c1.getDcdcodetype())) {
					String PCSM = valuemapMap.get("cjcasesort").toLowerCase();
					String PCZH = valuemapMap.get("cjicourtnumber").toLowerCase();
					if (null != PCZH && !"".equals(PCZH)) {
						String value1 = "";
						if (null != PCZH && PCZH.length() > 4) {
							value1 = "(" + PCZH.substring(0, 4) + ")";
						} else {
							value1 = "";
						}
						String value2 = "";
						if (null != PCZH && PCZH.length() > 4) {
							value2 = "第" + PCZH.substring(PCZH.length() - 3, PCZH.length()) + "号";
						} else {
							value2 = "";
						}
						daovalue = value1 + PCSM + value2;
					}
				}
				// 1000015拼音
				else if ("1000015".equals(c1.getDcdcodetype())) {
					String cbiname = valuemapMap.get("cbiname").toLowerCase();
					daovalue = "";
					if (null != cbiname && !"".equals(cbiname)) {
						try {
							HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
							pinyinFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
							pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
							cbiname = StringNumberUtil.toPinYin(cbiname);
							for (int j = 0; j < cbiname.length(); j++) {
								char[] cha = cbiname.substring(j, j + 1).toCharArray();
								for (int i = 0; i < cha.length; i++) {
									// 简体中文
									if (java.lang.Character.toString(cha[i]).matches("[\\u4E00-\\u9FA5]+$")) {
										try {
											daovalue += PinyinHelper.toHanyuPinyinStringArray(cha[0], pinyinFormat)[0]
													.charAt(0);
										} catch (Exception e) {
										}
									} else {
										daovalue += cha[i];
									}
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
				// 档卡刑期
				else if ("1000001".equals(c1.getDcdcodetype())) {
					daovalue = "";
					String CJIIMPRISONTYPE = "";
					if (jiexitype.equals("jiaohuan")) {
						CJIIMPRISONTYPE = valuemapMap.get("CJIIMPRISONTYPE").toLowerCase();
						// ((Element)dchang.elementIterator("CJIIMPRISONTYPE".toLowerCase()).next()).getText();
					} else {
						CJIIMPRISONTYPE = ((Element) dchang.elementIterator("CJIIMPRISONTYPE".toLowerCase()).next())
								.getText();
					}

					if ("2".equals(CJIIMPRISONTYPE)) {
						daovalue = "9995";
					} else if ("".equals(CJIIMPRISONTYPE)) {
						daovalue = "9996";
					} else {
						String CJIIMPRISONYEAR = "";
						String CJIIMPRISONMONTH = "";
						String CJIIMPRISONDAY = "";
						if (jiexitype.equals("jiaohuan")) {
							CJIIMPRISONYEAR = valuemapMap.get("CJIIMPRISONYEAR").toLowerCase();
							CJIIMPRISONMONTH = valuemapMap.get("CJIIMPRISONMONTH").toLowerCase();
							CJIIMPRISONDAY = valuemapMap.get("CJIIMPRISONDAY").toLowerCase();
						} else {
							CJIIMPRISONYEAR = ((Element) dchang.elementIterator("CJIIMPRISONYEAR".toLowerCase()).next())
									.getText();
							CJIIMPRISONMONTH = ((Element) dchang.elementIterator("CJIIMPRISONMONTH".toLowerCase())
									.next()).getText();
							CJIIMPRISONDAY = ((Element) dchang.elementIterator("CJIIMPRISONDAY".toLowerCase()).next())
									.getText();
						}
						if (null != CJIIMPRISONYEAR && !"".equals(CJIIMPRISONYEAR) && CJIIMPRISONYEAR.length() == 2) {
							daovalue = daovalue + CJIIMPRISONYEAR;
						} else if (null != CJIIMPRISONYEAR && !"".equals(CJIIMPRISONYEAR)
								&& CJIIMPRISONYEAR.length() == 1) {
							daovalue = daovalue + "0" + CJIIMPRISONYEAR;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CJIIMPRISONMONTH && !"".equals(CJIIMPRISONMONTH) && CJIIMPRISONMONTH.length() == 2) {
							daovalue = daovalue + CJIIMPRISONMONTH;
						} else if (null != CJIIMPRISONMONTH && !"".equals(CJIIMPRISONMONTH)
								&& CJIIMPRISONMONTH.length() == 1) {
							daovalue = daovalue + "0" + CJIIMPRISONMONTH;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CJIIMPRISONDAY && !"".equals(CJIIMPRISONDAY) && CJIIMPRISONDAY.length() == 2) {
							daovalue = daovalue + CJIIMPRISONDAY;
						} else if (null != CJIIMPRISONDAY && !"".equals(CJIIMPRISONDAY) && CJIIMPRISONDAY.length() == 1) {
							daovalue = daovalue + "0" + CJIIMPRISONDAY;
						} else {
							daovalue = daovalue + "00";
						}
					}
				}
				// 党卡附加刑
				else if ("1000002".equals(c1.getDcdcodetype())) {
					daovalue = "";
					String CJIDISFRANCHISEYEAR = "";
					if (jiexitype.equals("jiaohuan")) {
						CJIDISFRANCHISEYEAR = valuemapMap.get("CJIDISFRANCHISEYEAR").toLowerCase();
					} else {
						CJIDISFRANCHISEYEAR = ((Element) dchang.elementIterator("CJIDISFRANCHISEYEAR".toLowerCase())
								.next()).getText();
					}
					if ("97".equals(CJIDISFRANCHISEYEAR)) {
						daovalue = "97";
					} else {
						String CJIDISFRANCHISEMONTH = "";
						String CJIDISFRANCHISEDAY = "";
						if (jiexitype.equals("jiaohuan")) {
							CJIDISFRANCHISEMONTH = valuemapMap.get("CJIDISFRANCHISEMONTH").toLowerCase();
							CJIDISFRANCHISEDAY = valuemapMap.get("CJIDISFRANCHISEDAY").toLowerCase();
						} else {
							CJIDISFRANCHISEMONTH = ((Element) dchang.elementIterator(
									"CJIDISFRANCHISEMONTH".toLowerCase()).next()).getText();
							CJIDISFRANCHISEDAY = ((Element) dchang.elementIterator("CJIDISFRANCHISEDAY".toLowerCase())
									.next()).getText();
						}
						if (null != CJIDISFRANCHISEYEAR && !"".equals(CJIDISFRANCHISEYEAR)
								&& CJIDISFRANCHISEYEAR.length() == 2) {
							daovalue = daovalue + CJIDISFRANCHISEYEAR;
						} else if (null != CJIDISFRANCHISEYEAR && !"".equals(CJIDISFRANCHISEYEAR)
								&& CJIDISFRANCHISEYEAR.length() == 1) {
							daovalue = daovalue + "0" + CJIDISFRANCHISEYEAR;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CJIDISFRANCHISEMONTH && !"".equals(CJIDISFRANCHISEMONTH)
								&& CJIDISFRANCHISEMONTH.length() == 2) {
							daovalue = daovalue + CJIDISFRANCHISEMONTH;
						} else if (null != CJIDISFRANCHISEMONTH && !"".equals(CJIDISFRANCHISEMONTH)
								&& CJIDISFRANCHISEMONTH.length() == 1) {
							daovalue = daovalue + "0" + CJIDISFRANCHISEMONTH;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CJIDISFRANCHISEDAY && !"".equals(CJIDISFRANCHISEDAY)
								&& CJIDISFRANCHISEDAY.length() == 2) {
							daovalue = daovalue + CJIDISFRANCHISEDAY;
						} else if (null != CJIDISFRANCHISEDAY && !"".equals(CJIDISFRANCHISEDAY)
								&& CJIDISFRANCHISEDAY.length() == 1) {
							daovalue = daovalue + "0" + CJIDISFRANCHISEDAY;
						} else {
							daovalue = daovalue + "00";
						}
					}
				}
				// 党卡案件号
				else if ("1000003".equals(c1.getDcdcodetype())) {
					daovalue = "";
					String CJICOURTCITY = "";
					String CJICOURTNAME = "";
					String CJCASESORT = "";
					String CJCASEIDYEAR = "";
					String CJCASEIDNUMBER = "";
					if (jiexitype.equals("jiaohuan")) {
						CJICOURTCITY = valuemapMap.get("CJICOURTCITY").toLowerCase();
						CJICOURTNAME = valuemapMap.get("CJICOURTNAME").toLowerCase();
						CJCASESORT = valuemapMap.get("CJCASESORT").toLowerCase();
						CJCASEIDYEAR = valuemapMap.get("CJCASEIDYEAR").toLowerCase();
						CJCASEIDNUMBER = valuemapMap.get("CJCASEIDNUMBER").toLowerCase();
					} else {
						CJICOURTCITY = ((Element) dchang.elementIterator("CJICOURTCITY".toLowerCase()).next())
								.getText();
						CJICOURTNAME = ((Element) dchang.elementIterator("CJICOURTNAME".toLowerCase()).next())
								.getText();
						CJCASESORT = ((Element) dchang.elementIterator("CJCASESORT".toLowerCase()).next()).getText();
						CJCASEIDYEAR = ((Element) dchang.elementIterator("CJCASEIDYEAR".toLowerCase()).next())
								.getText();
						CJCASEIDNUMBER = ((Element) dchang.elementIterator("CJCASEIDNUMBER".toLowerCase()).next())
								.getText();
					}
					daovalue = CJICOURTCITY + CJICOURTNAME + CJCASESORT + CJCASEIDYEAR;
					if (null != CJCASEIDNUMBER && !"".equals(CJCASEIDNUMBER) && CJCASEIDNUMBER.length() == 3) {
						daovalue = daovalue + CJCASEIDNUMBER;
					} else if (null != CJCASEIDNUMBER && !"".equals(CJCASEIDNUMBER) && CJCASEIDNUMBER.length() == 2) {
						daovalue = daovalue + "0" + CJCASEIDNUMBER;
					} else if (null != CJCASEIDNUMBER && !"".equals(CJCASEIDNUMBER) && CJCASEIDNUMBER.length() == 1) {
						daovalue = daovalue + "00" + CJCASEIDNUMBER;
					}
				}
				// 党卡日期
				else if ("1000004".equals(c1.getDcdcodetype())) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat sdf222 = new SimpleDateFormat("yyyy-MM-dd");
					if (null != daovalue && !"".equals(daovalue)) {
						try {
							Date tempdate = sdf222.parse(daovalue);
							daovalue = sdf.format(tempdate);
						} catch (Exception e3) {
							daovalue = "";
							e3.printStackTrace();
						}
					}
				}
				// 党卡案件简号
				else if ("1000005".equals(c1.getDcdcodetype())) {
					daovalue = "";
					String CJCASEIDYEAR = "";
					String CJCASEIDNUMBER = "";
					if (jiexitype.equals("jiaohuan")) {
						CJCASEIDYEAR = valuemapMap.get("CJCASEIDYEAR").toLowerCase();
						CJCASEIDNUMBER = valuemapMap.get("CJCASEIDNUMBER").toLowerCase();
					} else {
						CJCASEIDYEAR = ((Element) dchang.elementIterator("CJCASEIDYEAR".toLowerCase()).next())
								.getText();
						CJCASEIDNUMBER = ((Element) dchang.elementIterator("CJCASEIDNUMBER".toLowerCase()).next())
								.getText();
					}
					daovalue = daovalue + CJCASEIDYEAR;
					if (null != CJCASEIDNUMBER && !"".equals(CJCASEIDNUMBER) && CJCASEIDNUMBER.length() == 3) {
						daovalue = daovalue + CJCASEIDNUMBER;
					} else if (null != CJCASEIDNUMBER && !"".equals(CJCASEIDNUMBER) && CJCASEIDNUMBER.length() == 2) {
						daovalue = daovalue + "0" + CJCASEIDNUMBER;
					} else if (null != CJCASEIDNUMBER && !"".equals(CJCASEIDNUMBER) && CJCASEIDNUMBER.length() == 1) {
						daovalue = daovalue + "00" + CJCASEIDNUMBER;
					}
				}
				// 党卡拼音
				else if ("1000006".equals(c1.getDcdcodetype())) {
					daovalue = "";
					String CBINAME = "";
					if (jiexitype.equals("jiaohuan")) {
						CBINAME = valuemapMap.get("CBINAME").toLowerCase();
					} else {
						CBINAME = ((Element) dchang.elementIterator("CBINAME".toLowerCase()).next()).getText();
					}
					if (null != CBINAME && !"".equals(CBINAME)) {
						daovalue = StringNumberUtil.toPinYin(CBINAME);
					}
				}
				// 党卡刑期变动附加刑
				else if ("1000010".equals(c1.getDcdcodetype())) {
					String CITCHANGEDISFRANCHISETO = "";
					if (jiexitype.equals("jiaohuan")) {
						CITCHANGEDISFRANCHISETO = valuemapMap.get("CITCHANGEDISFRANCHISETO").toLowerCase();
					} else {
						CITCHANGEDISFRANCHISETO = ((Element) dchang.elementIterator(
								"CITCHANGEDISFRANCHISETO".toLowerCase()).next()).getText();
					}
					if ("97".equals(CITCHANGEDISFRANCHISETO)) {
						daovalue = "97";
					} else {
						if (null != daovalue && !"".equals(daovalue)) {
							if (daovalue.length() == 1) {
								daovalue = "0" + daovalue + "0000";
							} else if (daovalue.length() == 2) {
								daovalue = daovalue + "0000";
							} else {
								daovalue = "000000";
							}
						}
					}
				}
				// 党卡刑期变动刑期
				else if ("1000011".equals(c1.getDcdcodetype())) {
					String CITCHANGEYEARTO = "";
					if (jiexitype.equals("jiaohuan")) {
						CITCHANGEYEARTO = valuemapMap.get("CITCHANGEYEARTO").toLowerCase();
					} else {
						CITCHANGEYEARTO = ((Element) dchang.elementIterator("CITCHANGEYEARTO".toLowerCase()).next())
								.getText();
					}
					if (!"9995".equals(CITCHANGEYEARTO) && !"9996".equals(CITCHANGEYEARTO)) {
						String CITCHANGEMONTHTO = "";
						String CITCHANGEDAYTO = "";
						if (jiexitype.equals("jiaohuan")) {
							CITCHANGEMONTHTO = valuemapMap.get("CITCHANGEMONTHTO").toLowerCase();
							CITCHANGEDAYTO = valuemapMap.get("CITCHANGEDAYTO").toLowerCase();
						} else {
							CITCHANGEMONTHTO = ((Element) dchang.elementIterator("CITCHANGEMONTHTO".toLowerCase())
									.next()).getText();
							CITCHANGEDAYTO = ((Element) dchang.elementIterator("CITCHANGEDAYTO".toLowerCase()).next())
									.getText();
						}
						daovalue = "";
						if (null != CITCHANGEYEARTO && !"".equals(CITCHANGEYEARTO) && CITCHANGEYEARTO.length() == 2) {
							daovalue = daovalue + CITCHANGEYEARTO;
						} else if (null != CITCHANGEYEARTO && !"".equals(CITCHANGEYEARTO)
								&& CITCHANGEYEARTO.length() == 1) {
							daovalue = daovalue + "0" + CITCHANGEYEARTO;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CITCHANGEMONTHTO && !"".equals(CITCHANGEMONTHTO) && CITCHANGEMONTHTO.length() == 2) {
							daovalue = daovalue + CITCHANGEMONTHTO;
						} else if (null != CITCHANGEMONTHTO && !"".equals(CITCHANGEMONTHTO)
								&& CITCHANGEMONTHTO.length() == 1) {
							daovalue = daovalue + "0" + CITCHANGEMONTHTO;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CITCHANGEDAYTO && !"".equals(CITCHANGEDAYTO) && CITCHANGEDAYTO.length() == 2) {
							daovalue = daovalue + CITCHANGEDAYTO;
						} else if (null != CITCHANGEDAYTO && !"".equals(CITCHANGEDAYTO) && CITCHANGEDAYTO.length() == 1) {
							daovalue = daovalue + "0" + CITCHANGEDAYTO;
						} else {
							daovalue = daovalue + "00";
						}

					}
				}
				// 党卡刑期变动幅度
				else if ("1000012".equals(c1.getDcdcodetype())) {
					String CITEXTENTYEAR = "";
					if (jiexitype.equals("jiaohuan")) {
						CITEXTENTYEAR = valuemapMap.get("CITEXTENTYEAR").toLowerCase();
					} else {
						CITEXTENTYEAR = ((Element) dchang.elementIterator("CITEXTENTYEAR".toLowerCase()).next())
								.getText();
					}
					if (!"9995".equals(CITEXTENTYEAR) && !"9996".equals(CITEXTENTYEAR)) {
						String CITEXTENTMONTH = "";
						String CITEXTENTDAY = "";
						if (jiexitype.equals("jiaohuan")) {
							CITEXTENTMONTH = valuemapMap.get("CITEXTENTMONTH").toLowerCase();
							CITEXTENTDAY = valuemapMap.get("CITEXTENTDAY").toLowerCase();
						} else {
							CITEXTENTMONTH = ((Element) dchang.elementIterator("CITEXTENTMONTH".toLowerCase()).next())
									.getText();
							CITEXTENTDAY = ((Element) dchang.elementIterator("CITEXTENTDAY".toLowerCase()).next())
									.getText();
						}
						daovalue = "";
						if (null != CITEXTENTYEAR && !"".equals(CITEXTENTYEAR) && CITEXTENTYEAR.length() == 2) {
							daovalue = daovalue + CITEXTENTYEAR;
						} else if (null != CITEXTENTYEAR && !"".equals(CITEXTENTYEAR) && CITEXTENTYEAR.length() == 1) {
							daovalue = daovalue + "0" + CITEXTENTYEAR;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CITEXTENTMONTH && !"".equals(CITEXTENTMONTH) && CITEXTENTMONTH.length() == 2) {
							daovalue = daovalue + CITEXTENTMONTH;
						} else if (null != CITEXTENTMONTH && !"".equals(CITEXTENTMONTH) && CITEXTENTMONTH.length() == 1) {
							daovalue = daovalue + "0" + CITEXTENTMONTH;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CITEXTENTDAY && !"".equals(CITEXTENTDAY) && CITEXTENTDAY.length() == 2) {
							daovalue = daovalue + CITEXTENTDAY;
						} else if (null != CITEXTENTDAY && !"".equals(CITEXTENTDAY) && CITEXTENTDAY.length() == 1) {
							daovalue = daovalue + "0" + CITEXTENTDAY;
						} else {
							daovalue = daovalue + "00";
						}

					}
				}
				// 罪名获取刑期
				else if ("1000013".equals(c1.getDcdcodetype())) {
					String CJAIIMPRISONYEAR = "";
					if (jiexitype.equals("jiaohuan")) {
						CJAIIMPRISONYEAR = valuemapMap.get("CJAIIMPRISONYEAR").toLowerCase();
					} else {
						CJAIIMPRISONYEAR = ((Element) dchang.elementIterator("CJAIIMPRISONYEAR".toLowerCase()).next())
								.getText();
					}
					if (!"9995".equals(CJAIIMPRISONYEAR) && !"9996".equals(CJAIIMPRISONYEAR)) {
						String CJAIIMPRISONMONTH = "";
						String CJAIIMPRISONDAY = "";
						if (jiexitype.equals("jiaohuan")) {
							CJAIIMPRISONMONTH = valuemapMap.get("CJAIIMPRISONMONTH").toLowerCase();
							CJAIIMPRISONDAY = valuemapMap.get("CJAIIMPRISONDAY").toLowerCase();
						} else {
							CJAIIMPRISONMONTH = ((Element) dchang.elementIterator("CJAIIMPRISONMONTH".toLowerCase())
									.next()).getText();
							CJAIIMPRISONDAY = ((Element) dchang.elementIterator("CJAIIMPRISONDAY".toLowerCase()).next())
									.getText();
						}
						daovalue = "";
						if (null != CJAIIMPRISONYEAR && !"".equals(CJAIIMPRISONYEAR) && CJAIIMPRISONYEAR.length() == 2) {
							daovalue = daovalue + CJAIIMPRISONYEAR;
						} else if (null != CJAIIMPRISONYEAR && !"".equals(CJAIIMPRISONYEAR)
								&& CJAIIMPRISONYEAR.length() == 1) {
							daovalue = daovalue + "0" + CJAIIMPRISONYEAR;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CJAIIMPRISONMONTH && !"".equals(CJAIIMPRISONMONTH)
								&& CJAIIMPRISONMONTH.length() == 2) {
							daovalue = daovalue + CJAIIMPRISONMONTH;
						} else if (null != CJAIIMPRISONMONTH && !"".equals(CJAIIMPRISONMONTH)
								&& CJAIIMPRISONMONTH.length() == 1) {
							daovalue = daovalue + "0" + CJAIIMPRISONMONTH;
						} else {
							daovalue = daovalue + "00";
						}

						if (null != CJAIIMPRISONDAY && !"".equals(CJAIIMPRISONDAY) && CJAIIMPRISONDAY.length() == 2) {
							daovalue = daovalue + CJAIIMPRISONDAY;
						} else if (null != CJAIIMPRISONDAY && !"".equals(CJAIIMPRISONDAY)
								&& CJAIIMPRISONDAY.length() == 1) {
							daovalue = daovalue + "0" + CJAIIMPRISONDAY;
						} else {
							daovalue = daovalue + "00";
						}

					}
				}
				// 累犯
				else if ("1000019".equals(c1.getDcdcodetype())) {
					String CAIIFLEIFAN = "";
					if (jiexitype.equals("jiaohuan")) {
						CAIIFLEIFAN = valuemapMap.get("CAIIFLEIFAN".toLowerCase());
					} else {
						CAIIFLEIFAN = ((Element) dchang.elementIterator("CAIIFLEIFAN".toLowerCase()).next()).getText();
					}
					if ("2".equals(CAIIFLEIFAN)) {
						daovalue = "1";
					} else {
						daovalue = "0";
					}
				}
				// 惯犯
				else if ("1000020".equals(c1.getDcdcodetype())) {
					String CAIIFGUANFAN = "";
					if (jiexitype.equals("jiaohuan")) {
						CAIIFGUANFAN = valuemapMap.get("CAIIFGUANFAN".toLowerCase());
					} else {
						CAIIFGUANFAN = ((Element) dchang.elementIterator("CAIIFGUANFAN".toLowerCase()).next())
								.getText();
					}
					if ("1".equals(CAIIFGUANFAN)) {
						daovalue = "1";
					} else {
						daovalue = "0";
					}
				} else {
					List<DbmsCodeChemeNew> damamingxilist = daimamap.get(c1.getDcdcodetype());
					if (null != damamingxilist && damamingxilist.size() > 0) {
						DbmsCodeChemeNew code = null;
						for (Object obj22 : damamingxilist) {
							code = (DbmsCodeChemeNew) obj22;
							if (strJgxt.equalsIgnoreCase(tobs.getDatabasename())) {
								if (null != code && daovalue.equals(code.getTargetid())) {
									daovalue = code.getCodeid();
									break;
								}
							} else {
								if (null != code && daovalue.equals(code.getCodeid())) {
									daovalue = code.getTargetid();
									break;
								}
							}
						}
					}
				}
			}
		}
		return daovalue;
	}

	private static TbsysCode parseTbsysCode(Map<String, Object> map) {
		TbsysCode tbsysCode = new TbsysCode();
		tbsysCode.setCodetype(StringNumberUtil.getStringByMap(map, "codetype"));
		tbsysCode.setCodeid(StringNumberUtil.getStringByMap(map, "codeid"));
		tbsysCode.setNoid(StringNumberUtil.getIntegerByMap(map, "noid"));
		tbsysCode.setName(StringNumberUtil.getStringByMap(map, "name"));
		tbsysCode.setSn(StringNumberUtil.getIntegerByMap(map, "sn"));
		tbsysCode.setScearch(StringNumberUtil.getStringByMap(map, "scearch"));
		tbsysCode.setPcodeid(StringNumberUtil.getStringByMap(map, "pcodeid"));
		tbsysCode.setRemark(StringNumberUtil.getStringByMap(map, "remark"));
		return tbsysCode;
	}
	private static String geshizhuanhuan(String basetype, String dcdfromcloumnstype, String value) {
		// if(null!=value){
		// value=value.replace("'", "''");
		// if(value.length()>1000){
		// value=value.substring(0, 1000);
		// }
		// }
		// try{
		// if("Microsoft SQL Server".equals(basetype)){
		// if("DATE".equals(dcdfromcloumnstype)){
		//					
		// }
		// }
		// if("Oracle".equals(basetype)){
		// if("DATE".equals(dcdfromcloumnstype)){
		// value="to_date('"+value+"','yyyy-MM-dd')";
		// }
		// }
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		return value;
	}

	public static Object addPreparedStatement(PreparedStatement stmt, String colname, String coltype, int index, Object value) {
		Object returnValue = value;
		ByteArrayInputStream byteInStream = null;
		Reader clobReader = null;
		try {
			if (GkzxCommon.VARCHAR2.equalsIgnoreCase(coltype) || GkzxCommon.CHAR.equalsIgnoreCase(coltype) || GkzxCommon.VARCHAR.equalsIgnoreCase(coltype)
					|| GkzxCommon.TEXT.equalsIgnoreCase(coltype)) {
				if (null == value || "".equals(value)) {
					stmt.setString(index, "");
					returnValue = "";
				} else {
					String temp = DataBaseDateTransfer.ToString(value);
					stmt.setString(index, temp);
					returnValue = temp;
				}
			} else if (GkzxCommon.NUMBER.equalsIgnoreCase(coltype)||GkzxCommon.NUMERIC.equalsIgnoreCase(coltype)||GkzxCommon.INT.equalsIgnoreCase(coltype)
					||GkzxCommon.SMALLINT.equalsIgnoreCase(coltype)||GkzxCommon.DECIMAL.equalsIgnoreCase(coltype)) {
				if (null == value || "".equals(value) || "''".equals(value)) {
					stmt.setNull(index, Types.NULL);
					returnValue = Types.NULL;
				} else {
					BigDecimal temp = DataBaseDateTransfer.ToBigDecimal(value);
					stmt.setBigDecimal(index, temp);
					returnValue = temp;
				}
			} else if (GkzxCommon.FLOAT.equalsIgnoreCase(coltype) || GkzxCommon.MONEY.equalsIgnoreCase(coltype)) {
				if (null == value || "".equals(value)) {
					value = 0f;
				}
				Float temp = DataBaseDateTransfer.ToFloat(value);
				stmt.setFloat(index, temp);
				returnValue = temp;
			} else if (GkzxCommon.DATE.equalsIgnoreCase(coltype)||GkzxCommon.DATETIME.equalsIgnoreCase(coltype)) {
				if (null == value || "".equals(value)) {
					stmt.setNull(index, Types.TIMESTAMP);
					returnValue = Types.TIMESTAMP;
				} else {
					Timestamp temp = DataBaseDateTransfer.ToDate(value);
					stmt.setTimestamp(index, temp);
					returnValue = temp;
				}
			} else if (GkzxCommon.Clob.equalsIgnoreCase(coltype)) {
				clobReader = new StringReader(value.toString());// 大字段转换
				stmt.setCharacterStream(index, clobReader, value.toString().length());// 大字段
			} else if (GkzxCommon.Blob.equalsIgnoreCase(coltype)) {
				byteInStream = new ByteArrayInputStream(value.toString().getBytes());
				stmt.setBinaryStream(index, byteInStream, value.toString().length());// 大字段
			} else {
				// 报错
				throw new RuntimeException("不识别的coltype:"+coltype);
			}
		} catch (Exception e) {
			throw new RuntimeException("addPreparedStatement出错:", e);
		} finally {
			try {
				if (byteInStream != null) {
					byteInStream.close();
				}
//				if (clobReader != null) {
//					clobReader.close();
//				}
			} catch (Exception e2) {
			}
		}

		return returnValue;
	}



	@Override
	public boolean addDataImportRequest(DbmsNewDataExportBean bean,TaskBean taskBean) {
		if (null == bean) {
			return false;
		}
		//
		boolean result = _addDataImportRequest(bean, taskBean);
		//
		return result;
	}

	/**
	 * 此方法描述的是：导入数据
	 * @param bean 参数Bean
	 * @param taskBean 进度回显
	 * @return
	 */
	private boolean _addDataImportRequest(DbmsNewDataExportBean bean,TaskBean taskBean) {
		if (null == bean || null == taskBean) {
			return false;
		}
		// 0.1 获取方案ID
		String ddcid = bean.getDdcid();
		if (StringNumberUtil.isEmpty(ddcid)) {
			taskBean.setStatus(TaskBean.STATUS_FAILURE);
			return false;
		}
		// 
		// 0.2 是否只执行插入...
		String insertOnly = bean.getInsertonly();
		if(StringNumberUtil.isEmpty(insertOnly)){
			insertOnly = "1";
		}
		// 0.3 设置开始
		taskBean.setStatus(TaskBean.STATUS_START);
		
		Connection localconn = JdbcConnUtil.getConn(); // 本地连接		
		try {
			// $$$$$$$$$$$$$$$$$$$
			// 1.1 查询代码类型
			String codetypesql = "select * from DBMS_CODE_TYPE_NEW a where 1=1";
			List<Map<String,Object>> codeTypeList = parseSQL2List(codetypesql, localconn);
			
			// 代码替换map
			Map<String, List<DbmsCodeChemeNew>> codeChemeMap = new HashMap<String, List<DbmsCodeChemeNew>>();
			
			for (int i = 0; i < codeTypeList.size(); i++) {
				Map<String,Object> codeTypeMap = codeTypeList.get(i);
				String codetypeid = StringNumberUtil.getStringByMap(codeTypeMap, "codetypeid");
				String codeMappingSQL = " select * from DBMS_CODE_CHEME_NEW a " +
										" where a.codetype='" + codetypeid + "'";
				//
				List<Map<String,Object>> codeMappingList = parseSQL2List(codeMappingSQL, localconn);
				//
				List<DbmsCodeChemeNew> codeChemeList = getCodeChemeNew(codeMappingList);
				codeChemeMap.put(codetypeid, codeChemeList);
			}
			// $$$$$$$$$$$$$$$$$$$
			// 1.2 根据方案编号获取导入库的相关信息
			String tobasesql = " select * from DBMS_DATABASE_NEW a"
							 + " where a.ddid in (" 
							 + "  select todatabaseid from DBMS_DATAS_CHEME_NEW c "
							 + "   where c.ddcid='" + ddcid + "'"
							 + " )";
			//
			ArrayList<Map<String,Object>> tobaselist = parseSQL2List(tobasesql, localconn);

			DbmsDatabaseNew toDataBase = null;
			if (tobaselist.size() > 0) {
				Map<String,Object> map = tobaselist.get(0);
				toDataBase = parseDatabaseNew(map);
			} else {
				taskBean.setStatus(TaskBean.STATUS_FAILURE);
				return false;
			}

			// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			// 1.3  根据方案id,查出对应的表
			//
			String tablesql = "select * from DBMS_DATAS_TABLE_NEW a "
						   + " where a.ddcid='" + ddcid + "'"
						   + " order by  a.ddtismaintable,a.ddtorder";
			//
			List<Map<String,Object>> tableList = parseSQL2List(tablesql, localconn);
			List<DbmsDatasTableNew> biaolist = getDatasTableNew(tableList);
			// 1.4 将 table 与对应的 column 封装到一起
			List<ShuJuTempTable> shujutablelist = new ArrayList<ShuJuTempTable>();
			// 
			for (DbmsDatasTableNew thetable : biaolist) {
				String ddtid = thetable.getDdtid();
				// 查询所有列
				String liesql = " select * from DBMS_DATAS_CHEME_DETAIL_NEW a "
							  + " where a.ddcid='" + ddcid + "' "
							  + " and a.dcdtotableid='" + ddtid + "'";
				//
				List<Map<String,Object>> lieDataList = parseSQL2List(liesql, localconn);
				List<DbmsDatasChemeDetailNew> lielist = getDatasChemeDetailNew(lieDataList);
				//
				if(null == lielist || lielist.isEmpty()){
					log("没有查到detail信息:liesql="+liesql);
					continue; // 没有列
				}
				ShuJuTempTable po = new ShuJuTempTable();
				po.setThetable(thetable);
				po.setLielist(lielist);
				shujutablelist.add(po);
			}
			// 2.0  分包导入开始
			File dirFile = new File(bean.getBasePathfile());
			// 如果dir对应的文件/目录不存在，则退出
			if (!dirFile.exists()) {
				taskBean.setStatus(TaskBean.STATUS_FAILURE);
				return false;
			} else {
				taskBean.setStatus(TaskBean.STATUS_DOING);
			}
			// 列出目录内文件列表
			File[] files = new File[0];
			if (dirFile.isDirectory()) {
				files = dirFile.listFiles();
			}
			// 强制执行一次
			boolean forceExecuteOnce = true;
			//
			for (int ii = 0; ii < files.length || forceExecuteOnce; ii++) {
				forceExecuteOnce = false;
				// 本次要处理的XML文件
				File xmlFile = null;
				//
				if (dirFile.isFile()) {
					//
					xmlFile = dirFile;
				} else  if (null !=files[ii] && files[ii].isFile()) {
					// 将此文件设置到 bean 中
					// bean.setBasePathfile(files[ii].getAbsolutePath());
					xmlFile = files[ii];
				} else  if (null !=files[ii] && files[ii].isDirectory()) {
					// 将此文件设置到 bean 中
					// bean.setBasePathfile(files[ii].getAbsolutePath());
					String strPath  = files[ii].getPath();
					int intIndex = strPath.lastIndexOf(File.separator);
					String strTmpPath = strPath.substring(intIndex + 1);
					String srcPath = GetAbsolutePath.getAbsolutePath(strPath);
//					String desPath = GetAbsolutePath.getAbsolutePath(GkzxCommon.SINGLESEPALINE + GkzxCommon.CRIMINALARCHIVES_PATH);
					
					Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
					String desPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
					desPath = GetAbsolutePath.getAbsolutePathAppend(desPath, GkzxCommon.CRIMINALARCHIVES_PATH);
					
					if(strTmpPath.equals(GkzxCommon.CRIMINALARCHIVES_PATH)) {
						try {
							FileUtil.batchCopyFile(strPath, desPath);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					continue;
				} else {
					// 其他情况,则跳过此文件
					continue;
				}
				// 分包导入结束2013-12-23
				//xmlFile = new File(bean.getBasePathfile());

				if (null == xmlFile || !xmlFile.exists()) {
					// 不存在,跳过记录
					log("xmlFile文件不存在!");
					continue;
				}
				// 2.1 开始解析文件
				// 
				// 本次的临时条件列表, 不知道哪里在使用
				List<TempConBean> conlist = new ArrayList<TempConBean>();
				//
				String chaxuncon = bean.getCondition();
				if(StringNumberUtil.isEmpty(chaxuncon)){
					chaxuncon = bean.getHiddencon();
				}
				// 有查询条件
				if(StringNumberUtil.notEmpty(chaxuncon)){
					String[] conarr = chaxuncon.split(",");
					//
					for (String constr : conarr) {
						// 0, coluname,1运算符 , 2查询值
						String[] tempvalue = constr.split(",");
						if (tempvalue.length == 3) {
							TempConBean conbean = new TempConBean();
							conbean.setColname(tempvalue[0]);
							conbean.setYunsuanfu(tempvalue[1]);
							conbean.setChaxunvalue(tempvalue[2]);
							conlist.add(conbean);
						}
					}
				}
				//
				Connection toDBconn = null;		 // 远端连接
				PreparedStatement insertPrestat = null;
				PreparedStatement updatePrestat = null;
				try {
					// 解析
					SAXReader saxReader = new SAXReader();
					// 获取文档DOM对象
					Document document = saxReader.read(xmlFile);
					// 获取根元素
					Element root = document.getRootElement();
					// 记录总条数
					int recordSum = 0;

					try {
						Element dctotal = root.element("dctotal");
						if (StringNumberUtil.notEmpty(dctotal)) {
							recordSum = Integer.parseInt(dctotal.getTextTrim());
							// 设置总条数, 好像不太对,多个文件怎么办?
							taskBean.setSum(recordSum);
						}
					} catch (Exception e) {
						// 记录非法...
					}
					// 获取toDB连接
					toDBconn = JdbcConnUtil.getConnection(toDataBase);
					// 开启事务
					toDBconn.setAutoCommit(false);
					// 遍历各个表
					for (Iterator<Element> iter = root.elementIterator("dctable"); iter.hasNext();) {
						//
						Element dctableelement = (Element) iter.next();
						// 表名
						//String dctablename = ((Element) dctableelement.elementIterator("dctablename").next()).getText();
						//
						String dctablename = dctableelement.elementTextTrim("dctablename");
						if(null != dctablename){
							dctablename = dctablename.trim().toUpperCase();
						} else {
							continue; //没有表名,忽略
						}
						// 取出当前记录对应的表
						DbmsDatasTableNew currentTableBean = null;
						List<DbmsDatasChemeDetailNew> detailBeanList = null;
						for (ShuJuTempTable pp : shujutablelist) {
							// 判断导入方案的原表与导出的数据表相同
							DbmsDatasTableNew table = pp.getThetable();
							List<DbmsDatasChemeDetailNew> detailList = pp.getLielist();
							//
							String tableName = table.getTablename();
							if(StringNumberUtil.isEmpty(tableName)){
								continue;
							} else {
								tableName = tableName.trim().toUpperCase();
							}
							//if (dctablename.equals(table.getDdtjoinedfields().toUpperCase())) {
							if (dctablename.equals(tableName)) {
								currentTableBean = table;
								detailBeanList = detailList;
								break;
							}
						}

						if (null == currentTableBean) {
							// 表不存在
							log("currentTableBean="+currentTableBean);
							continue;
						}
						//
						String currentTableName = currentTableBean.getTablename();
						// 表名为空
						if (StringNumberUtil.isEmpty(currentTableName) ) {
							log("currentTableName="+currentTableName);
							continue;
						}
						// 列为空
						if (null == detailBeanList || detailBeanList.isEmpty()) {
							log("detailBeanList="+detailBeanList);
							continue;
						}
						// 对应的数据行
						for (Iterator<Element> shujuiter = dctableelement.elementIterator("dchang"); shujuiter.hasNext();) {
							// 行 Element
							Element dchang = (Element) shujuiter.next();
							// 拼SQL
							// insert语句
							String insertLineSQL = "insert into " + currentTableName + " (";
							// insert字段Str
							String insertFieldsStr = "";
							// insert值Str
							String insertValuesStr = "";
							
							// update语句
							String updateLineSQL = "update " + currentTableName + " set ";
							// update部分
							String updateSetsString = "";
							// where 子句, update才有
							String whereClauseString = "";
							// 主键索引, update的where在后面, so ... 
							int zhujianindex = 1;
							
							// 问号
							String wenhao = "?";
							
							for (DbmsDatasChemeDetailNew detail : detailBeanList) {
								// 导入列
								String dcdtocolumns = detail.getDcdtocolumns();
								if(StringNumberUtil.isEmpty(dcdtocolumns)){
									continue;
								} else {
									dcdtocolumns = dcdtocolumns.trim().toUpperCase();
								}
								
								//  insert 字段部分
								if ("".equals(insertFieldsStr)) {
									insertFieldsStr += dcdtocolumns;
								} else {
									insertFieldsStr += "," + dcdtocolumns;
								}
								// insert 值部分
								if ("".equals(insertValuesStr)) {
									insertValuesStr += wenhao;
								} else {
									insertValuesStr += "," + wenhao + "";
								}
								
								// update ...
								// 如果是主键, 则作为 where语句的条件
								if ("1".equals(detail.getDcdifpkey())) {
									if ("".equals(whereClauseString)) {
										whereClauseString += dcdtocolumns + " = " + wenhao + " ";
									} else {
										whereClauseString += " and " + dcdtocolumns + " = " + wenhao + " ";
									}
								} else {
									zhujianindex++;
									if ("".equals(updateSetsString)) {
										updateSetsString += dcdtocolumns + " = " + wenhao + " ";
									} else {
										updateSetsString += " , " + dcdtocolumns + " = " + wenhao + " ";
									}
								}
							}
							//
							insertLineSQL = insertLineSQL + insertFieldsStr + " ) values (" + insertValuesStr + ")";
							updateLineSQL = updateLineSQL + updateSetsString + " where " + whereClauseString;
							insertPrestat = toDBconn.prepareStatement(insertLineSQL);
							updatePrestat = toDBconn.prepareStatement(updateLineSQL);
							// 跟新语句必须带条件,否则不能进行更新
							int insertindex = 1;
							int updateindex = 1;
							// 是否有主键,没有主键则不执行 update
							boolean hasPK = false;
							// /未使用
							HashMap<String, ArrayList<String>> mapColumnType = new HashMap<String, ArrayList<String>>();
							HashMap<String, HashMap<String, TbsysCode>> mapCodeScidRemarkHashMap = new HashMap<String, HashMap<String, TbsysCode>>();
							Map<String, String> orgValuemapMap = new HashMap<String, String>();
							// /未使用

							for (DbmsDatasChemeDetailNew detailBean : detailBeanList) {
								String fromcolumnsname = detailBean.getDcdfromcolumns();
								String tocolumnsname = detailBean.getDcdtocolumns();
								String tocolumnstype = detailBean.getDcdtocolumnstype();
								//
								if(StringNumberUtil.notEmpty(fromcolumnsname)){
									fromcolumnsname = fromcolumnsname.trim().toLowerCase();
								} else {
									fromcolumnsname = "";
								}
								if(StringNumberUtil.notEmpty(tocolumnsname)){
									tocolumnsname = tocolumnsname.trim().toLowerCase();
								} else {
									tocolumnsname = "";
								}
								if(StringNumberUtil.notEmpty(tocolumnstype)){
									tocolumnstype = tocolumnstype.trim().toLowerCase();
								} else {
									tocolumnstype = "";
								}
								
								
								String myvalueString = "";
								if (null != fromcolumnsname && !"".equals(fromcolumnsname)) {
									fromcolumnsname = fromcolumnsname.toLowerCase();
									if (dchang.elementIterator(fromcolumnsname).hasNext()) {
										myvalueString = ((Element) dchang.elementIterator(fromcolumnsname)
												.next()).getText();
									}
								}
								int fk = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())) * 10;
								String suid = "";
								myvalueString = this.jiexiValue(suid, myvalueString, detailBean, fk, null, dchang,
										codeChemeMap, toDataBase, "daoru", mapColumnType, mapCodeScidRemarkHashMap,
										orgValuemapMap, "");
								// 数据插入时的格式转换
								myvalueString = geshizhuanhuan(toDataBase.getDatabasetype(), tocolumnstype, myvalueString);
								// 添加insert语句值
								addPreparedStatement(insertPrestat, tocolumnsname, tocolumnstype, insertindex,
										myvalueString);
								insertindex++;
								// 添加 update语句值
								if ("1".equals(detailBean.getDcdifpkey())) {
									addPreparedStatement(updatePrestat, tocolumnsname, tocolumnstype,
											zhujianindex, myvalueString);
									zhujianindex++;
									hasPK = true; // 有主键
								} else {
									addPreparedStatement(updatePrestat, tocolumnsname, tocolumnstype,
											updateindex, myvalueString);
									updateindex++;
								}
							}
							//
							try {
								if (GkzxCommon.ONE.equals(insertOnly)) {
									// 新增导入
									insertPrestat.executeUpdate();
								} else {
									int result = 0;
									// 先尝试更新
									if(hasPK){
										result = updatePrestat.executeUpdate();
									} else {
										//
										log("没有主键,不执行: "+updateLineSQL);
									}
									
									if (0 == result) {
										insertPrestat.executeUpdate();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								JdbcConnUtil.close(updatePrestat);
								JdbcConnUtil.close(insertPrestat);
							}
							// count++;
							// taskBean.setCounter(count);
						}
					}
					// 每个文件提交一次
					toDBconn.commit();
				} catch (Exception e) {
					try {
						toDBconn.rollback();
					} catch (Exception e2) {
					}
					e.printStackTrace();
				} finally {
					// toDBconn.setAutoCommit(true);
					JdbcConnUtil .close(toDBconn);
				}
			} // end of for:file
			return true; // 只有这里返回 true
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnUtil.close(localconn);
		}
		return false;
	} // end of _addDataImportRequest()


	// 将Map解析为DbmsDatabaseNew对象
	public static DbmsDatabaseNew parseDatabaseNew(Map<String, Object> map) {
		DbmsDatabaseNew dbmsDatabaseNew = new DbmsDatabaseNew();
		dbmsDatabaseNew.setDdid(StringNumberUtil.getStringByMap(map, "ddid"));
		dbmsDatabaseNew.setDdname(StringNumberUtil.getStringByMap(map, "ddname"));
		dbmsDatabaseNew.setDdip(StringNumberUtil.getStringByMap(map, "ddip"));
		dbmsDatabaseNew.setDatabasename(StringNumberUtil.getStringByMap(map, "databasename"));
		dbmsDatabaseNew.setDdorg(StringNumberUtil.getStringByMap(map, "ddorg"));
		dbmsDatabaseNew.setDatabaseuser(StringNumberUtil.getStringByMap(map, "databaseuser"));
		dbmsDatabaseNew.setDatabasepassword(StringNumberUtil.getStringByMap(map, "databasepassword"));
		dbmsDatabaseNew.setPort(StringNumberUtil.getShortByMap(map, "port"));
		dbmsDatabaseNew.setDatabasetype(StringNumberUtil.getStringByMap(map, "databasetype"));
		dbmsDatabaseNew.setSdid(StringNumberUtil.getStringByMap(map, "sdid"));
		return dbmsDatabaseNew;
	}
	/**
	 * 获取 jyconfig 的值
	 * 依赖 {@link GkzxCommon.DATABASETYPE} 值
	 * @param name
	 * @return
	 */
	public static String getJyConfigValue(String name){
		return getJyConfigValue(name, null);
	}
	/**
	 * 
	 * @param name 配置参数名
	 * @param defVal 默认值
	 * @return
	 */
	public static String getJyConfigValue(String name, String defVal){
		if(null == name || name.trim().isEmpty()){
			return defVal;
		}
		//
		if(null == _jyconfig){
			_jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(null == _jyconfig){
				return defVal;
			}
		}
		String provincecode = _jyconfig. getProperty(name);
		if(null == provincecode){
			provincecode = defVal;
		}
		return provincecode.trim();
	}

	public ArrayList<Map<String, Object>> parseSQL2List(String sql){
		Connection conn = JdbcConnUtil.getConn();
		ArrayList<Map<String, Object>>  objList =  parseSQL2List(sql, conn);
		JdbcConnUtil.close(conn);
		return objList;
	}
	public ArrayList<Map<String, Object>> parseSQL2List(String sql, Connection conn){
		if(null == sql || null == conn){
			return null;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();
		//
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			objList = JdbcConnUtil.ResultSetToList(rs);
		}  catch (Exception e) {
			e.printStackTrace();
		} finally{
			JdbcConnUtil.close(rs);
			JdbcConnUtil.close(ps);
			// conn 不负责关闭,谁创建的谁自己关
		}

		return objList;
	}
	
	/**
	 * 日志方法
	 */
	public void log(Object info){
		log(info, 0);
	}
	public void error(Object info){
		log(info, 4);
	}
	public void log(Object info, int level){
		//
		String currentDateStr = getCurrentDateStr();
		//
		if(level > 3){
			// 添加到在线日志中
			// ...
			
			// 先实现为系统错误输出
			System.err.println(currentDateStr + ":"+ info);
		} else {
			// 先实现为系统输出
			System.out.println(currentDateStr + ":"+ info);
		}
	}
	public String getCurrentDateStr(){
		String currentDateStr = sinog2cDateFormater.format(new Date());
		return currentDateStr;
	}
	public String getCurrentTimestampStr(){
		String currentDateStr = dateFormaterTimestamp.format(new Date());
		return currentDateStr;
	}
	
	public static String encodeBase64(byte[] buffer) throws Exception {
		return new BASE64Encoder().encode(buffer);
	}

	public static byte[] decoderBase64(String base64Code) throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		return buffer;
	}

//	 public static String encodeBase64File(String path) throws Exception {
//		  File file = new File(path);;
//		  FileInputStream inputFile = new FileInputStream(file);
//		  byte[] buffer = new byte[(int) file.length()];
//		  inputFile.read(buffer);
//		  inputFile.close();
//		  return new BASE64Encoder().encode(buffer);
//	
//		 }
//	 public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
//		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
//		FileOutputStream out = new FileOutputStream(targetPath);
//		out.write(buffer);
//		out.close();
//
//	}
//	 public static void decoderBase64FileTxt(String base64Code, String targetPath) throws Exception {
//		BufferedWriter output = new BufferedWriter(new FileWriter(targetPath));
//		output.write(base64Code);
//		output.close();
//
//	}

//	public static void test(){
//		PreparedStatement prestmtPreparedStatement1 = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		String insertaa = "insert into ZPUBLIC_DA_MTXX(id,bh,nr) values(?,?,?)";
//		Connection daoruconn = null;
//		Connection conn = null;
//		daoruconn = JdbcConnUtil.getConn();
//		try {
//			
//			ByteArrayInputStream byteInStream = null;
//			String value = encodeBase64File("f:/a.jpg");
//			
//			//$$$$$$$$$$$$$$$$$$$$$$$$$
//			String biaosql = "select * from DBMS_DATABASE_NEW a where a.ddid in (select fromdatabaseid from DBMS_DATAS_CHEME_NEW c where c.ddcid='20141112160540SWP')";
//			PreparedStatement localps = daoruconn.prepareStatement(biaosql);
//			ResultSet localrs = localps.executeQuery();
//			List<Map<String,Object>>  dataAll = JdbcConnUtil.ResultSetToList(localrs);
//			JdbcConnUtil.closeResource(null, localps, localrs);
//			DbmsDatabaseNew bs = null;
//			if (dataAll.size() > 0) {
//				HashMap map = (HashMap) dataAll.get(0);
//				bs = parseDatabaseNew(map);
//			}
//			conn = JdbcConnUtil.getConnection(bs);
//			
//			//$$$$$$$$$$$$$$$$$$$$$$$$$
//////			String value ="sssss";
////			byteInStream = new ByteArrayInputStream(value.toString().getBytes());
////			prestmtPreparedStatement1.setString(1, "12");
////			prestmtPreparedStatement1.setString(2, "12222");
////			prestmtPreparedStatement1.setBinaryStream(3, byteInStream, value.toString().length());// 大字段
////
////				// 新增导入
////				prestmtPreparedStatement1.executeUpdate();
//				insertaa="select nr from DA_MTXX where bh='1372008572' ";
//
//				value = "";
//				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//						ResultSet.CONCUR_READ_ONLY);
//				rs = stmt.executeQuery(insertaa);
//				try {
//				InputStream inStreamDoc = null;
//					if (rs.next()) {
//						Blob blob = rs.getBlob("nr");// 
//						if (blob == null) {
//							value = "";
//						} else {
//							inStreamDoc = blob.getBinaryStream();
//							byte[] tempDoc = new byte[(int) blob.length()];
//							inStreamDoc.read(tempDoc);
//							value = encodeBase64(tempDoc);
//							value = value.replace("\r\n", "");
//							
//						}
//					}
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				} finally {
//				}
//				
//				insertaa = "update  ZPUBLIC_DA_MTXX set bh=?,nr=? where id=?";
//				prestmtPreparedStatement1 = daoruconn.prepareStatement(insertaa);
//				prestmtPreparedStatement1.setString(1, "12222");
//				StringReader clobReader = new StringReader(value);// 大字段转换
//				prestmtPreparedStatement1.setCharacterStream(2, clobReader, value.length());// 大字段
//				prestmtPreparedStatement1.setString(3, "12");
//				prestmtPreparedStatement1.executeUpdate();
////				try {
////					if (rs.next()) {
////						Reader inStreamDoc = null;
////						try {
////							Clob clob = rs.getClob("nr");// 
////							if (clob == null) {
////								value = "";
////							} else {
////								inStreamDoc = clob.getCharacterStream();
////								char[] tempDoc = new char[(int) clob.length()];
////								inStreamDoc.read(tempDoc);
////								value = new String(tempDoc);
////							}
////						} catch (Exception e2) {
////							e2.printStackTrace();
////						} finally {
////							if (null != inStreamDoc) {
////								inStreamDoc.close();
////								inStreamDoc = null;
////							}
////						}
////					}
////				} catch (Exception e2) {
////					e2.printStackTrace();
////				} finally {
//////					if (null != inStreamDoc) {
//////						inStreamDoc.close();
//////						inStreamDoc = null;
//////					}
////				}
//				decoderBase64FileTxt(value,"f:/b.txt");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			// logger.error(MessageFormat.format(e.getMessage(),
//			// "jianyushujujiaohuanAction"));
//			// 日志
//			SystemLog log = new SystemLog();
//			log.setLogtype("数据交换");
//			log.setOpaction("jianyushujujiaohuanAction");
//			log.setOpcontent("数据交换");
//			log.setOrgid("sys");
//			log.setRemark(e.getMessage());
//			log.setStatus(0);
//			try {
//				// logService.add(log, null);
//			} catch (Exception e1) {
//			}
//		}
//	}
	
	private FtpClient ftpClient = null;
	/**
	 * JDK1.7下的链接服务器
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 * @param path
	 */
	public void connectServer(String ip, int port, String user, String password, String path) {  
	    try {
	        /* ******连接服务器的两种方法*******/  

	        //第一种方法  
	        //ftpClient = new FtpClient();
	        //ftpClient.openServer(ip, port); 
	    	
	        //第二种方法  
    		ftpClient = FtpClient.create();
            ftpClient.connect(new InetSocketAddress(ip, port));
            ftpClient.login(user, password.toCharArray());

	        // 设置成2进制传输
	        if (StringNumberUtil.notEmpty(path)){
	            //把远程系统上的目录切换到参数path所指定的目录  
	        	ftpClient.changeDirectory(path);
	        }  
	        ftpClient.setBinaryType();  
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }  
	}
	
	
	/**
	 * @Description: 上传至前置机
	 */
	public boolean upload(String localFile, String remoteFile) {
	    OutputStream os = null;
	    FileInputStream is = null;
	    boolean rtnResult = true;
	    try {
	        //将远程文件加入输出流中  
	        os = ftpClient.putFileStream(remoteFile);
	        //获取本地文件的输入流  
	        File file_in = new File(localFile);  
	        is = new FileInputStream(file_in);
	        //创建一个缓冲区
	        byte[] bytes = new byte[10240];
	        int c;
	        while ((c = is.read(bytes)) != -1) {
	            os.write(bytes, 0, c);
	        }
	    } catch (Exception ex) {
	    	rtnResult = false;
	    	ex.printStackTrace();
	    } finally{
	        try {  
	            if(is != null){
	                is.close();
	            }
                if(os != null){
                    os.close();
                }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }  
	    return rtnResult;
	}
	
	
	public void closeConnect() {
	    try {
	        ftpClient.close();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	public boolean localSaveFileData(DbmsNewDataExportBean bean,Map nodeMap) {
		TaskBean taskBean = new TaskBean();
		if (null == bean ) {
			return false;
		}
		// 0.1 获取方案ID
		String ddcid = bean.getDdcid();
		if (StringNumberUtil.isEmpty(ddcid)) {
			taskBean.setStatus(TaskBean.STATUS_FAILURE);
			return false;
		}
		// 
		// 0.2 是否只执行插入...
		String insertOnly = bean.getInsertonly();
		if(StringNumberUtil.isEmpty(insertOnly)){
			insertOnly = "1";
		}
		// 0.3 设置开始
		taskBean.setStatus(TaskBean.STATUS_START);
		Connection localconn = JdbcConnUtil.getConn(); // 本地连接
		
		// $$$$$$$$$$$$$$$$$$$
		// 1.1 查询代码类型
		String codetypesql = "select * from DBMS_CODE_TYPE_NEW a where 1=1";
		List<Map<String,Object>> codeTypeList = parseSQL2List(codetypesql, localconn);
		
		// 代码替换map
		Map<String, List<DbmsCodeChemeNew>> codeChemeMap = new HashMap<String, List<DbmsCodeChemeNew>>();
		
//		for (int i = 0; i < codeTypeList.size(); i++) {
//			Map<String,Object> codeTypeMap = codeTypeList.get(i);
//			String codetypeid = StringNumberUtil.getStringByMap(codeTypeMap, "codetypeid");
//			String codeMappingSQL = " select * from DBMS_CODE_CHEME_NEW a " +
//									" where a.codetype='" + codetypeid + "'";
//			//
//			List<Map<String,Object>> codeMappingList = parseSQL2List(codeMappingSQL, localconn);
//			//
//			List<DbmsCodeChemeNew> codeChemeList = getCodeChemeNew(codeMappingList);
//			codeChemeMap.put(codetypeid, codeChemeList);
//		}
		
		
		
		// $$$$$$$$$$$$$$$$$$$
		// 1.2 根据方案编号获取导入库的相关信息
		String tobasesql = " select * from DBMS_DATABASE_NEW a"
						 + " where a.ddid in (" 
						 + "  select todatabaseid from DBMS_DATAS_CHEME_NEW c "
						 + "   where c.ddcid='" + ddcid + "'"
						 + " )";
		//
		ArrayList<Map<String,Object>> tobaselist = parseSQL2List(tobasesql, localconn);

		DbmsDatabaseNew toDataBase = null;
		Connection toDBconn = null;		 // 远端连接
		if (tobaselist.size() > 0) {
			Map<String,Object> map = tobaselist.get(0);
			toDataBase = parseDatabaseNew(map);
		} else {
			taskBean.setStatus(TaskBean.STATUS_FAILURE);
			return false;
		}
		// 获取toDB连接
		toDBconn = JdbcConnUtil.getConnection(toDataBase);
		// 开启事务
		try {
			toDBconn.setAutoCommit(false);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		// 1.3  根据方案id,查出对应的表
		//
		String tablesql = "select * from DBMS_DATAS_TABLE_NEW a "
					   + " where a.ddcid='" + ddcid + "'"
					   + " order by  a.ddtismaintable,a.ddtorder";
		//
		List<Map<String,Object>> tableList = parseSQL2List(tablesql, localconn);
		List<DbmsDatasTableNew> biaolist = getDatasTableNew(tableList);
		// 1.4 将 table 与对应的 column 封装到一起
		List<ShuJuTempTable> shujutablelist = new ArrayList<ShuJuTempTable>();
		// 
		for (DbmsDatasTableNew thetable : biaolist) {
			String ddtid = thetable.getDdtid();
			// 查询所有列
			String liesql = " select * from DBMS_DATAS_CHEME_DETAIL_NEW a "
						  + " where a.ddcid='" + ddcid + "' "
						  + " and a.dcdtotableid='" + ddtid + "'";
			//
			List<Map<String,Object>> lieDataList = parseSQL2List(liesql, localconn);
			List<DbmsDatasChemeDetailNew> lielist = getDatasChemeDetailNew(lieDataList);
			//
			if(null == lielist || lielist.isEmpty()){
				log("没有查到detail信息:liesql="+liesql);
				continue; // 没有列
			}
			ShuJuTempTable po = new ShuJuTempTable();
			po.setThetable(thetable);
			po.setLielist(lielist);
			shujutablelist.add(po);
		}
		PreparedStatement insertPrestat = null;
		PreparedStatement updatePrestat = null;
		try {			 
			 // 取出当前记录对应的表
			 String tableName = "";
			 DbmsDatasTableNew table = null;
			 List<DbmsDatasChemeDetailNew> detailList = null;
			 for(ShuJuTempTable pp : shujutablelist){
				 table = pp.getThetable();
				 detailList = pp.getLielist();
				 if(!StringNumberUtil.isNullOrEmpty(table)){
					 tableName = table.getTablename();
				 }
			 }	 
			// 拼SQL
			// insert语句
			String insertLineSQL = "insert into " + tableName + " (";
			// insert字段Str
			String insertFieldsStr = "";
			// insert值Str
			String insertValuesStr = "";
			
			// update语句
			String updateLineSQL = "update " + tableName + " set ";
			// update部分
			String updateSetsString = "";
			// where 子句, update才有
			String whereClauseString = "";
			// 主键索引, update的where在后面, so ... 
			int zhujianindex = 1;
			
			// 问号
			String wenhao = "?";
								
			for (DbmsDatasChemeDetailNew detail : detailList) {
				// 导入列
				String dcdtocolumns = detail.getDcdtocolumns();
				if(StringNumberUtil.isEmpty(dcdtocolumns)){
					continue;
				} else {
					dcdtocolumns = dcdtocolumns.trim().toUpperCase();
				}
				
				//  insert 字段部分
				if ("".equals(insertFieldsStr)) {
					insertFieldsStr += dcdtocolumns;
				} else {
					insertFieldsStr += "," + dcdtocolumns;
				}
				// insert 值部分
				if ("".equals(insertValuesStr)) {
					insertValuesStr += wenhao;
				} else {
					insertValuesStr += "," + wenhao + "";
				}
									
				// update ...
				// 如果是主键, 则作为 where语句的条件
				if ("1".equals(detail.getDcdifpkey())) {
					if ("".equals(whereClauseString)) {
						whereClauseString += dcdtocolumns + " = " + wenhao + " ";
					} else {
						whereClauseString += " and " + dcdtocolumns + " = " + wenhao + " ";
					}
				} else {
					zhujianindex++;
					if ("".equals(updateSetsString)) {
						updateSetsString += dcdtocolumns + " = " + wenhao + " ";
					} else {
						updateSetsString += " , " + dcdtocolumns + " = " + wenhao + " ";
					}
				}
			}
			//
			insertLineSQL = insertLineSQL + insertFieldsStr + " ) values (" + insertValuesStr + ")";
			updateLineSQL = updateLineSQL + updateSetsString + " where " + whereClauseString;
			insertPrestat = toDBconn.prepareStatement(insertLineSQL);
			updatePrestat = toDBconn.prepareStatement(updateLineSQL);
			// 跟新语句必须带条件,否则不能进行更新
			int insertindex = 1;
			int updateindex = 1;
			// 是否有主键,没有主键则不执行 update
			boolean hasPK = false;
			// /未使用
			HashMap<String, ArrayList<String>> mapColumnType = new HashMap<String, ArrayList<String>>();
			HashMap<String, HashMap<String, TbsysCode>> mapCodeScidRemarkHashMap = new HashMap<String, HashMap<String, TbsysCode>>();
			Map<String, String> orgValuemapMap = new HashMap<String, String>();
			// /未使用

			for (DbmsDatasChemeDetailNew detailBean : detailList) {
				String fromcolumnsname = detailBean.getDcdfromcolumns();
				String tocolumnsname = detailBean.getDcdtocolumns();
				String tocolumnstype = detailBean.getDcdtocolumnstype();
				//
				if(StringNumberUtil.notEmpty(fromcolumnsname)){
					fromcolumnsname = fromcolumnsname.trim().toLowerCase();
				} else {
					fromcolumnsname = "";
				}
				if(StringNumberUtil.notEmpty(tocolumnsname)){
					tocolumnsname = tocolumnsname.trim().toLowerCase();
				} else {
					tocolumnsname = "";
				}
				if(StringNumberUtil.notEmpty(tocolumnstype)){
					tocolumnstype = tocolumnstype.trim().toLowerCase();
				} else {
					tocolumnstype = "";
				}					
				String myvalueString = "";
				if (null != fromcolumnsname && !"".equals(fromcolumnsname)) {
					fromcolumnsname = fromcolumnsname.toLowerCase();
					if(!StringNumberUtil.isNullOrEmpty(nodeMap.get(fromcolumnsname))){
						myvalueString = nodeMap.get(fromcolumnsname).toString();
					}
				}		
				int fk = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())) * 10;
				String suid = "";
//				myvalueString = this.jiexiValue(suid, myvalueString, detailBean, fk, null, null,
//						codeChemeMap, toDataBase, "daoru", mapColumnType, mapCodeScidRemarkHashMap,
//						orgValuemapMap, "");
				// 数据插入时的格式转换
				myvalueString = geshizhuanhuan(toDataBase.getDatabasetype(), tocolumnstype, myvalueString);
				// 添加insert语句值
				addPreparedStatement(insertPrestat, tocolumnsname, tocolumnstype, insertindex,
						myvalueString);
				insertindex++;
				// 添加 update语句值
				if ("1".equals(detailBean.getDcdifpkey())) {
					addPreparedStatement(updatePrestat, tocolumnsname, tocolumnstype,
							zhujianindex, myvalueString);
					zhujianindex++;
					hasPK = true; // 有主键
				} else {
					addPreparedStatement(updatePrestat, tocolumnsname, tocolumnstype,
							updateindex, myvalueString);
					updateindex++;
				}
			}
			//
			try {
				if (GkzxCommon.ONE.equals(insertOnly)) {
					// 新增导入
					insertPrestat.executeUpdate();
				} else {
					int result = 0;
					// 先尝试更新
					if(hasPK){
						result = updatePrestat.executeUpdate();
					} else {
						//
						log("没有主键,不执行: "+updateLineSQL);
					}
					
					if (0 == result) {
						insertPrestat.executeUpdate();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcConnUtil.close(updatePrestat);
				JdbcConnUtil.close(insertPrestat);
			}
			toDBconn.commit();
			} catch (Exception e) {
				try {
					toDBconn.rollback();
				} catch (Exception e1) {
				}
				e.printStackTrace();
			}finally {
				// toDBconn.setAutoCommit(true);
				JdbcConnUtil .close(toDBconn);
			}
		return false;
	}
	
	/*
	 * 批量解压
	 */
	@Override
	public boolean batchUnzip(HttpServletRequest request,String fileid) {
		//unzipdata绝对路径
		String unzipdataPath=GetAbsolutePath.getAbsolutePath(GkzxCommon.UNZIPDATA_PATH+File.separator);
		boolean rtnResult = false;
		try {
			String filePathIds[] = fileid.split(",");//切分fileids
			int count = 0;
		    for (int i = 0; i < filePathIds.length; i++) {//遍历fileids，根据fileid获取压缩文件的相对路径
		    	count ++;
		    	request.getSession(true).setAttribute("percent", "正在解压第" + count + "个文件，共" + filePathIds.length + "个文件");
		    	String filePathId = filePathIds[i];
				DbmsDaochuListInfo daochuInfo = daochuListInfoMapper.selectByPrimaryKey(filePathId);
				String zipfilepath = daochuInfo.getFilepath();
				zipfilepath=zipfilepath.substring(1);
				zipfilepath=GetAbsolutePath.getAbsolutePath(zipfilepath+File.separator);
				String fileRealname = daochuInfo.getFilerealname();
				fileRealname=fileRealname.substring(0, fileRealname.indexOf(".zip"));
				//解压后的文件地址 
				String unzipPath=GetAbsolutePath.getAbsolutePath(GkzxCommon.UNZIPDATA_PATH+File.separator+fileRealname);
				File file_ = new File(unzipPath);
				if (!file_.exists()) {
					file_.mkdirs();
			    }
				//解压缩文件
				unzipFile(fileRealname,zipfilepath,unzipPath);
//				DocZip.unzip(zipfilepath, unzipPath);//解压
				
				daochuInfo=new DbmsDaochuListInfo();
				daochuInfo.setCreatedate(new Date());
				daochuInfo.setFileid(filePathId);
				daochuInfo.setText7("1");
				daochuListInfoMapper.updateByPrimaryKeySelective(daochuInfo);//更新状态为已解压
				
				DbmsDaochuListInfo ddli = new DbmsDaochuListInfo();
				ddli.setFileid(UUID.randomUUID().toString());
				ddli.setCreatedate(new Date());
				ddli.setFilename(fileRealname);
				ddli.setFilerealname(fileRealname);
				//
				String path = file_.getPath();
				path= path.substring(path.indexOf("\\"+GkzxCommon.UNZIPDATA_PATH));
				ddli.setFilepath(path);
				//
				ddli.setText6("1");//标识0：压缩文件  1：解压文件
				ddli.setText7("0");//标识0：未处理      1：已处理
				daochuListInfoMapper.insertSelective(ddli);
		    }
		    rtnResult=true; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rtnResult;
	}
	
	
	
	/**
	 * @Description: 解压文件到指定目录(文件被加密)
	 * @param	filenameNoSuffix 解压的文件名，不包括后缀
	 * 			zipFilePath		解压的文件路径
	 * 			unzipdata		解压后目录路径
	 * @return 
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年6月7日  下午5:14:23
	 * @Version 3.1
	 */
	public void unzipFile(String filenameNoSuffix,String zipFilePath,String unzipdata) throws IOException{
		
		//解压
		//先从压缩文件中提取密码
		String encfileStr = filenameNoSuffix + GkzxCommon.ENCRYPT_FILE_SUFFIX;
		ZipUtil.unzipSingleFile(zipFilePath, unzipdata, encfileStr, null);
		ZipUtil.removeFile(zipFilePath, encfileStr);
		//
		String passwd = "";
		File encFile = new File(unzipdata + File.separator + encfileStr);
		if(encFile.exists()){
			Base64Util.decode(encFile);
			List<Map<String,String>> tempList = TxtUtil.readTxtFile(unzipdata + File.separator + encfileStr, GkzxCommon.encoding);
			Map<String,String> map = tempList.get(0);
			passwd = DESUtil.decrypt(map.get("passwd"));
		}
		//用密码进行解密解压
		File unzipFileDir = new File(unzipdata + File.separator + filenameNoSuffix);
		if(! unzipFileDir.exists()){
			unzipFileDir.mkdir();
		}
		ZipUtil.extractZipFile(zipFilePath, unzipdata + File.separator + filenameNoSuffix, passwd);
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
