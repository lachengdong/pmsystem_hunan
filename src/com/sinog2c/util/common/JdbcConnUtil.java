package com.sinog2c.util.common;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;


public final class JdbcConnUtil {
//	private static Logger logger = Logger.getLogger("log4j") ;//log4j
	/**
	 * 得到连接对象
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			Properties conpro = new GetProperty().bornProp(GkzxCommon.CONNECTION, null);
			Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String database =  jypro.getProperty("pmis.Database");
			if (StringNumberUtil.isNullOrEmpty(database)) {
				database = GkzxCommon.DATABASE_ORACLE;
			}
			if (database.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection(conpro.getProperty("databaseurl"),conpro.getProperty("databaseusername"),conpro.getProperty("databaseuserpassword"));
			} else if (database.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)) {
				Class.forName("dm.jdbc.driver.DmDriver");
				conn=DriverManager.getConnection(conpro.getProperty("databaseurl"),conpro.getProperty("databaseusername"),conpro.getProperty("databaseuserpassword"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭指定的资源
	 */
	public static void closeResource(Connection conn, Statement stmt, ResultSet rs) {
		
		if(rs!=null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stmt!=null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭指定的资源
	 */
	public static void closeReader(Reader reader) {
		
		if(reader!=null) {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 关闭ResultSet rs
	public static void close(ResultSet rs) {
		if (null != rs) {
			try {
				boolean hasClosed = rs.isClosed();
				if (false == hasClosed) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// 关闭
	public static void close(Statement statement) {
		if (null != statement) {
			try {
				boolean hasClosed = statement.isClosed();
				if (false == hasClosed) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 关闭
	public static void close(Connection conn) {
		if (null != conn) {
			try {
				boolean hasClosed = conn.isClosed();
				if (false == hasClosed) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static boolean callProcedure(Connection con,String proName, String param) {
		CallableStatement cs = null;
		boolean flag = false;
		String sql  = null;
		try{
			sql = "{call " + proName + "}";
			cs = con.prepareCall(sql);
			if(param!=null && !"".equals(param)){
				cs.setString(1, param);
			}
			flag = cs.execute();
		}catch (Exception e) {
//			logger.error("调用存储过程失败！名称为：" + proName + " ; 参数为：" + param);
			e.printStackTrace();
		}finally{
			closeResource(null, cs, null);
			StringNumberUtil.setObjectNull(sql);
		}
		return flag;
	}
	
//	public static Long executeQueryCountForPage(Connection conn, String sql) {
//		Long num = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ps = conn.prepareStatement(sql);
//			rs = ps.executeQuery();
//			if(rs.next()){
//				BigDecimal	maxid=rs.getBigDecimal("s");
//				if(null!=maxid&&!"".equals(maxid.toString())){
//					num = maxid.longValue();
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				ps.close();
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return num;
//	}
	
	
    /**   
     * 此方法描述的是：将rs转换为ArrayList
     */
	public static ArrayList<Map<String,Object>> ResultSetToList(ResultSet rs) throws Exception {
		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> rowData;
		if (rs != null) {
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				rowData = new HashMap<String,Object>(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					Object v = rs.getObject(i);
	
					if (v != null
							&& (v.getClass() == Date.class || v.getClass() == java.sql.Date.class)) {
						Timestamp ts = rs.getTimestamp(i);
						v = new java.util.Date(ts.getTime());
						// v = ts;
					} else if (v != null
							&& v.getClass() == oracle.sql.CLOB.class) {
						v = DataBaseDateTransfer.clob2String((Clob) v);
					}
					rowData.put(md.getColumnName(i).toLowerCase(), v);
				}
				list.add(rowData);
			}
		}
		return list;
	} 
	
	/**
	 * Commit
	 */
	public static void SetAutoCommitConn(Connection conn, boolean trueOrFalse) {
		if(conn!=null) {
			try {
				conn.setAutoCommit(trueOrFalse);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Commit
	 */
	public static void CommitConn(Connection conn) {
		if(conn!=null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * rollback
	 */
	public static void RollbackConn(Connection conn) {
		if(conn!=null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    /**   
     * 此方法描述的是：得到数据库连接对象
	 * @param   name  
	 * @param  @return 
	 * @Exception 异常对象
     * @author: 李祺亮   
     * @version: 2013-6-27 下午06:34:22   
     */   
    
	public static Connection getConnection(DbmsDatabaseNew dataBase){
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String hotbackup = (jyconfig.getProperty("hotbackup")== null?"":jyconfig.getProperty("hotbackup"));
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		Connection conn=null;
		if("MySQL".equals(dataBase.getDatabasetype())){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://"+dataBase.getDdip()+":"+dataBase.getPort()+"/"+dataBase.getDatabasename(),dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
		}else if("Oracle".equals(dataBase.getDatabasetype())){
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				if("1".equals(hotbackup)&&"6100".equals(provincecode)){
					conn=DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = "
					+ dataBase.getDdip() +")(PORT ="+ dataBase.getPort() +")))(CONNECT_DATA = (SERVICE_NAME ="+dataBase.getDatabasename()+
					")(FAILOVER_MODE = (TYPE = SELECT)(METHOD = BASIC)(RETRIES = 180)(DELAY = 5))))",dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
				}else{
					conn=DriverManager.getConnection("jdbc:oracle:thin:@"+dataBase.getDdip()+":"+dataBase.getPort()+":"+dataBase.getDatabasename(),dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
					}
				} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("Microsoft SQL Server".equals(dataBase.getDatabasetype())){
			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:jtds:sqlserver://"+dataBase.getDdip()+":"+dataBase.getPort()+"/"+dataBase.getDatabasename()+";tds=8.0;lastupdatecount=true",dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("Microsoft SQL Server 2005".equals(dataBase.getDatabasetype())){
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String dbURL = "jdbc:sqlserver://"+dataBase.getDdip()+":"+dataBase.getPort()+"; DatabaseName="+dataBase.getDatabasename()+"";
				conn=DriverManager.getConnection(dbURL,dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("DB2".equals(dataBase.getDatabasetype())){
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
			    conn=DriverManager.getConnection("jdbc:db2://"+dataBase.getDdip()+":"+dataBase.getPort()+"/"+dataBase.getDatabasename(),dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("Informix".equals(dataBase.getDatabasetype())){
			try{
				Class.forName("com.informix.jdbc.IfxDriver");
				conn=DriverManager.getConnection("jdbc:informix-sqli://"+dataBase.getDdip()+":"+dataBase.getPort()+"/"+dataBase.getDatabasename()+":INFORMIXSERVER=myserver;user="+dataBase.getDatabaseuser()+";password="+dataBase.getDatabasepassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("Sybase".equals(dataBase.getDatabasetype())){
			try{
				Class.forName("com.sybase.jdbc.sybDriver");
				conn=DriverManager.getConnection("jdbc:sybase:Tds:"+dataBase.getDdip()+":"+dataBase.getPort()+"/"+dataBase.getDatabasename(),dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if ("Dameng".equalsIgnoreCase(dataBase.getDatabasetype())) {
			try{
				Class.forName("dm.jdbc.driver.DmDriver");
				conn=DriverManager.getConnection("jdbc:dm://"+dataBase.getDdip()+":"+dataBase.getPort()+"/"+dataBase.getDatabasename(),dataBase.getDatabaseuser(),dataBase.getDatabasepassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public Object addPreparedStatement(PreparedStatement stmt,String colname,String coltype,int index,Object value) {
		 Object returnValue = value;
		 ByteArrayInputStream byteInStream = null;
		 Reader clobReader = null;
		 try{
			 if(GkzxCommon.VARCHAR2.equals(coltype) || GkzxCommon.CHAR.equals(coltype)){
				 if(null==value||"".equals(value)){
					 stmt.setString(index, "");
					 returnValue = "";
				 }
				 else{
					 String temp=DataBaseDateTransfer.ToString(value);
					stmt.setString(index, temp);
					returnValue = temp;
				 }
			 }
			 else if(GkzxCommon.NUMBER.equals(coltype)){
				 if(null==value||"".equals(value)||"''".equals(value)){
					 stmt.setNull(index, Types.NULL);
					 returnValue = Types.NULL;
				 }
				 else{
					 BigDecimal temp=DataBaseDateTransfer.ToBigDecimal(value);
					 stmt.setBigDecimal(index, temp);
					 returnValue = temp;
				 }
			 }
			 else if(GkzxCommon.FLOAT.equals(coltype)){
				 if(null==value||"".equals(value)){
					 value=0f;
				 }
				 Float temp=DataBaseDateTransfer.ToFloat(value);
				 stmt.setFloat(index, temp);
				 returnValue = temp;
			 }
			 else if(GkzxCommon.DATE.equals(coltype)){
				 if(null==value||"".equals(value)){
					 stmt.setNull(index, Types.TIMESTAMP);
					 returnValue = Types.TIMESTAMP;
				 }
				 else{
					 Timestamp temp=DataBaseDateTransfer.ToDate(value);
						stmt.setTimestamp(index, temp);
						returnValue = temp;
				 }
			 }
			 else if(GkzxCommon.Clob.equalsIgnoreCase(coltype)){
				 clobReader = new StringReader(value.toString());//大字段转换
				 stmt.setCharacterStream(index, clobReader, value.toString().length());//大字段
			 }
			 else if(GkzxCommon.Blob.equalsIgnoreCase(coltype)){
				 byteInStream = new ByteArrayInputStream(value.toString().getBytes());
				 stmt.setBinaryStream(index, byteInStream, value.toString().length());//大字段
			 }
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		} finally {
			try {
				if (byteInStream != null){
					byteInStream.close();
				}
				if (clobReader != null){
					clobReader.close();
				}
			} catch (Exception e2) {
			}
		}
		
		 return  returnValue;
	 }

}
