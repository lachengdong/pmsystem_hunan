package com.sinog2c.util.dbms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

//import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * 全局数据库连接管理
 * @author zhangxf10
 */
public class DBManager {
	private final static Logger logger=Logger.getLogger(DBManager.class.getName());
	
	private final static ThreadLocal<Connection> connsThreadLocal = new ThreadLocal<Connection>();

	private static DataSource dataSource;
	
	private DBManager(){
	}
	
	/**
	 * 初始化连接池
	 * @param props
	 * @param show_sql
	 */
	public final static void initDataSource(Properties dbProperties) {
		try {
			if(dbProperties == null) {
				dbProperties = new Properties();
				dbProperties.load(DBManager.class.getResourceAsStream("db.properties"));
			}
			
			Properties connPoolPropties = new Properties();
			for (Object key : dbProperties.keySet()) {
				String skey = (String) key;
				if (skey.startsWith("jdbc.")) {
					String name = skey.substring(5);
					connPoolPropties.put(name,dbProperties.getProperty(skey));
				}
			}
			dataSource = (DataSource)Class.forName(connPoolPropties.getProperty("datasource")).newInstance();
			if (dataSource.getClass().getName().indexOf("c3p0") > 0) {
				System.setProperty("com.mchange.v2.c3p0.management.ManagementCoordinator","com.mchange.v2.c3p0.management.NullManagementCoordinator");
			}
			logger.info("Using DataSource:"+dataSource.getClass().getName());
			//BeanUtils.populate(dataSource,connPoolPropties);
			
			Connection conn = getConnection();
			DatabaseMetaData mdm = conn.getMetaData();
			logger.info("Connected to " + mdm.getDatabaseProductName() + " " + mdm.getDatabaseProductVersion());
			closeConnection();
		} catch (Exception e) {
			logger.error("DBManager初始化失败",e);
		}
	}
	
	/**
	 * 关闭DataSource
	 *
	 */
	public final static void closeDataSource() {
		try {
			dataSource.getClass().getMethod("close").invoke(dataSource);
		}catch (Exception e) {
			logger.error("关闭DataSource失败!!!",e);
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 * @throws  
	 * @throws SQLException
	 */
	public final static Connection getConnection(){
		return getConnection(false);
	}
	
	public final static Connection getConnection(boolean autoCommit){
		Connection conn = connsThreadLocal.get();
		try{
			if (conn == null||conn.isClosed()) {
				conn = dataSource.getConnection();
				conn.setAutoCommit(autoCommit);
				connsThreadLocal.set(conn);
			}
		}catch(SQLException e){
			logger.error("获取数据库连接失败!!!",e);
		}
		return conn;
	}
	
	/**
	 * 关闭连接
	 */
	public final static void closeConnection() {
		Connection conn = connsThreadLocal.get();
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("关闭数据库连接失败!!! ", e);
		}
		connsThreadLocal.set(null);
	}
}