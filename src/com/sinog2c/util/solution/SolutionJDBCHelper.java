package com.sinog2c.util.solution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.sinog2c.util.solution.processor.api.ISolutionTypeProcessor;

public class SolutionJDBCHelper {
	
	private static final Object DEFAULT_PROCESSOR_KEY = "default_type";

	public static String solutiontypeBundleName = "solutiontype";
	
	private static boolean hasInitMapping = false;
	private static Map<String, ISolutionTypeProcessor> processorMap = new HashMap<String, ISolutionTypeProcessor>();

	
	
	public static List<Map<String, Object>> executeQuery(
			Connection connection, String sqlTemplate, Map<String, Object> paramMap){
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> result = null;
		//
		try {
			statement = SolutionJDBCHelper.createPreparedStatement(connection, sqlTemplate, paramMap);
			resultSet = statement.executeQuery();
			result = SolutionJDBCHelper.parseResultSetToMapList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			SolutionJDBCHelper.close(resultSet);
			SolutionJDBCHelper.close(statement);
		}
		return result;
	}

	public static int executeUpdate(
			Connection connection, String sqlTemplate, Map<String, Object> paramMap){
		PreparedStatement statement = null;
		int result = 0;
		//
		try {
			statement = createPreparedStatement(connection, sqlTemplate, paramMap);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SolutionJDBCHelper.close(statement);
		}
		return result;
	}
	
	/**
	 * 创建可以直接执行的PreparedStatement
	 * @param connection
	 * @param sqlTemplate
	 * @param paramMap
	 * @return
	 */
	public static PreparedStatement createPreparedStatement(
			Connection connection, String sqlTemplate, Map<String, Object> paramMap) {
		//
		PreparedStatement statement = null;
		//
		if(null == connection || null == sqlTemplate){
			return statement;
		}

		LexerParser.debug("sqlTemplate:\n"+sqlTemplate);
		
		//类Mybatis配制规则			start    -------------create by YangZR	2016-03-31
		//类Mybatis的foreach标签的配制规则
		sqlTemplate = LexerParser.parseSqlTemplate4ForeachTag(sqlTemplate, paramMap);
		//类Mybatis的choose when标签的配制规则
		sqlTemplate = LexerParser.parseSqlTemplate4ChooseWhenTag(sqlTemplate, paramMap);
		//类Mybatis的If标签的配制规则
		sqlTemplate = LexerParser.parseSqlTemplate4IfTag(sqlTemplate, paramMap);
		//类Mybatis配制规则			end
		
		// 处理 if 情况
		sqlTemplate = LexerParser.parseTemplate2TemplateByIf(sqlTemplate, paramMap);
		// 根据2个@,解析处理直接替换的情况
		sqlTemplate = LexerParser.parseTemplateBy2At(sqlTemplate, paramMap);
		//
		List<VariableToken> varTokenList = LexerParser.parseVariables2Token(sqlTemplate);
		//
		// 把所有参数替换为 "?"
		String sql = replaceWithQuestionByToken(sqlTemplate, varTokenList);
		//
		LexerParser.debug("sql:\n"+sql);
		// 准备语句
		statement = prepareStatement(connection, sql);
		// 执行参数注入
		injectStatementParamMap(statement, varTokenList, paramMap);
		//
		return statement;
	}
	
	
	public static PreparedStatement getPreparedStatement4BatchSave(Connection connection, String sqlTemplate, List<VariableToken> varTokenList){
		// 把所有参数替换为 "?"
		String sql = replaceWithQuestionByToken(sqlTemplate, varTokenList);
		//
		return prepareStatement(connection, sql);
	}
	
	
	/**
	 * 注入PreparedStatement参数
	 * @param statement
	 * @param varTokenList 解析出的
	 */
	public static void injectStatementParamMap(PreparedStatement statement, List<VariableToken> varTokenList,  Map<String, Object> paramMap) {
		//
		if(null == statement 
				|| null == varTokenList || varTokenList.isEmpty()
				|| null == paramMap || paramMap.isEmpty()){
			// 没有参数,不需要注入,直接返回
			return ;
		}
		// 参数值Map, 转换为小写
		paramMap = convertKey2LowerCase(paramMap);
		//
		Iterator<VariableToken> iteratorV = varTokenList.iterator();
		while (iteratorV.hasNext()) {
			VariableToken varToken = (VariableToken) iteratorV.next();
			if(null == varToken){
				continue;
			}
			// 解析到的变量
			String jdbcType = varToken.getJdbcType();
			String paramName = varToken.getName();
			int order = varToken.getOrder();
			//
			if(null == paramName || paramName.trim().isEmpty()){
				continue;
			}
			paramName = paramName.trim().toLowerCase();
			// 参数值
			Object paramValue = paramMap.get(paramName);
			// 处理语句值注入
			boolean status = processPreparedStatementValueSetting(statement, order, jdbcType, paramValue);
			if(!status){
				// 抛出异常?
				// throw new RuntimeException("类型处理器返回false");
			}
		}
		//
	}

	private static boolean processPreparedStatementValueSetting(
			PreparedStatement statement, int order, String jdbcType,Object paramValue) {
		//
		boolean status = false;
		// 采用配置的方式, 根据需要加载
		initSolutionTypeProcessor(solutiontypeBundleName);
		//
		if(null == jdbcType || jdbcType.trim().isEmpty()){
			return status;
		}
		//
		jdbcType = jdbcType.trim().toLowerCase();
		ISolutionTypeProcessor processor = processorMap.get(jdbcType);

		// 找不到类型处理器
		if(null == processor){
			processor = processorMap.get(DEFAULT_PROCESSOR_KEY);
		}
		// 执行处理
		if(null != processor){
			status = processor.processSolutionType(statement, order, jdbcType, paramValue);
		}
		
		return status;
	}

	public static void initSolutionTypeProcessor(String solutiontypeFilebaseName) {
		if(hasInitMapping){
			return;
		}
		//
		Map<String, String> mappings = parseBundle2Map(solutiontypeFilebaseName);
		//
		Set<String> keySet = mappings.keySet();
		//
		Iterator<String> iteratorK = keySet.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			String className = mappings.get(key);

			if(null == key || key.trim().isEmpty()){
				continue;
			}
			key = key.trim().toLowerCase();
			// 初始化类. 获取对象
			ISolutionTypeProcessor processor = getInstance(className);
			if(null != processor){
				processorMap.put(key, processor);
			}
		}
		//
		hasInitMapping = true;
	}
	
	private static ISolutionTypeProcessor getInstance(String className) {
		//
		ISolutionTypeProcessor processor = null;
		if(null == className || className.trim().isEmpty()){
			return processor;
		}
		//
		className = className.trim();
		//
		try {
			Class<?> clazz = Class.forName(className);
			//
			Class<?>[] interfaces = clazz.getInterfaces();
			//
			boolean isISolutionTypeProcessor = false;
			//
			// 判断是否实现了该接口
			for (int i = 0; i < interfaces.length; i++) {
				Class<?> apiClass = interfaces[i];
				 if(ISolutionTypeProcessor.class.equals(apiClass)){
					 isISolutionTypeProcessor = true;
					 break;
				 }
			}
			//
			if(!isISolutionTypeProcessor){
				return processor;
			}
			//
			Object instance = clazz.newInstance();
			// 对象强转
			if(instance instanceof ISolutionTypeProcessor){
				processor = (ISolutionTypeProcessor) instance;
			}
			//
		} catch (Exception e) {
			//throw new RuntimeException("初始化解析类[" + className + "]时出现问题.\n", e);
			System.err.println("初始化解析类[" + className + "]时出现问题(程序继续运行): ");
			e.printStackTrace();
		}
		
		//
		return processor;
	}

	public static Map<String, String> parseBundle2Map(String baseName){
		Map<String, String> values = new HashMap<String, String>();
		//
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		if(null == bundle){
			return values;
		}
		//
		Enumeration<String> keys =  bundle.getKeys();
		//
		if(null == keys){
			return values;
		}
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = bundle.getString(key);
			//
			if(null == key || key.trim().isEmpty()){
				continue;
			}
			values.put(key, value);
		}
		//
		return values;
	}

	/**
	 * 将List中的Key转换为小写
	 * @param list 返回新对象
	 * @return
	 */
	public static List<Map<String, Object>> convertKeyList2LowerCase(List<Map<String, Object>> list){
		if(null==list) {
			return null;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//
		Iterator<Map<String, Object>> iteratorL = list.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			//
			Map<String, Object> result = convertKey2LowerCase(map);
			if(null != result){
				resultList.add(result);
			}
		}
		//
		return resultList;
	}
	/**
	 * 转换单个map,将key转换为小写. 
	 * @param map 返回新对象
	 * @return
	 */
	public static Map<String, Object> convertKey2LowerCase(Map<String, Object> map){
		if(null==map) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		//
		Set<String> keys  = map.keySet();
		//
		Iterator<String> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			Object value = map.get(key);
			if(null == key){
				continue;
			}
			//
			String keyL = key.toLowerCase();
			result.put(keyL, value);
		}
		return result;
	}
	

	public static PreparedStatement prepareStatement(Connection connection, String sql) {
		PreparedStatement statement = null;
		//
		if(null == connection || null == sql){
			return statement;
		}
		//
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new RuntimeException("创建语句出错, sql:\n "+ sql + "\n", e);
		}
		//
		return statement;
	}

	/**
	 * 用问号(?)替换SQL模板中的参数位置
	 * @param sqlTemplate
	 * @param varTokenList
	 * @return
	 */
	public static String replaceWithQuestionByToken(String sqlTemplate, List<VariableToken> varTokenList) {
		String sql = sqlTemplate;
		//
		if(null == sqlTemplate || null == varTokenList || varTokenList.isEmpty()){
			// 没有参数
			return sql;
		}
		//
		String question = "?";
		//
		Iterator<VariableToken> iteratorV = varTokenList.iterator();
		while (iteratorV.hasNext()) {
			VariableToken varToken = (VariableToken) iteratorV.next();
			if(null == varToken){
				continue;
			}
			String wraptext = varToken.getWraptext();
			// 不使用正则. 
			// 也不需要替换第一个. 全部替换即可
			sql = sql.replace(wraptext, question);
		}
		//
		
		return sql;
	}
	

	/**
	 * 将一个未处理的ResultSet解析为Map列表.
	 * 
	 * @param rs
	 * @return
	 */
	public static List<Map<String, Object>> parseResultSetToMapList(ResultSet rs) {
		//
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		//
		if (null == rs) {
			return null;
		}
		//
		try {
			while (rs.next()) {
				// 
				Map<String, Object> map = parseResultSetToMap(rs);
				//
				if (null != map) {
					result.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		//
		return result;
	}

	/**
	 * 解析ResultSet的单条记录,不进行 ResultSet 的next移动处理
	 * 
	 * @param rs
	 * @return
	 */
	private static Map<String, Object> parseResultSetToMap(ResultSet rs) {
		//
		if (null == rs) {
			return null;
		}
		//
		Map<String, Object> map = new HashMap<String, Object>();
		//
		try {
			ResultSetMetaData meta = rs.getMetaData();
			//
			int colNum = meta.getColumnCount();
			//
			for (int i = 1; i <= colNum; i++) {
				// 列名
				String name = meta.getColumnLabel(i); // i+1
				Object value = rs.getObject(i);
				// 加入属性
				map.put(name, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		//
		return map;
	}
	

	public static void close(ResultSet resultSet){
		//
		if(null != resultSet){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(PreparedStatement statement){
		//
		if(null != statement){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Connection connection){
		//
		if(null != connection){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
