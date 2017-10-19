package com.sinog2c.service.impl.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.common.CommonSQLMapper;
import com.sinog2c.dao.api.solution.FormSolutionDetailMapper;
import com.sinog2c.dao.api.solution.FormSolutionMapper;
import com.sinog2c.dao.api.solution.FormSqlGroupMapper;
import com.sinog2c.model.solution.FormSolution;
import com.sinog2c.model.solution.FormSolutionDetail;
import com.sinog2c.model.solution.FormSqlGroup;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.solution.LexerParser;
import com.sinog2c.util.solution.SolutionJDBCHelper;
import com.sinog2c.util.solution.VariableToken;

/**
 * 通用SQL查询方案服务实现
 */
@Service("commonSQLSolutionService")
public class CommonSQLSolutionServiceImpl extends ServiceImplBase implements CommonSQLSolutionService {

	// 自动装载FactoryBean
	@Autowired
	private SqlSessionFactoryBean factoryBean;
	@Autowired
	private CommonSQLMapper sqlMapper;
	@Autowired
	private FormSolutionMapper solutionMapper;
	@Autowired
	private FormSolutionDetailMapper solutionDetailMapper;
	@Autowired
	private FormSqlGroupMapper sqlGroupMapper;
	//@Autowired
	//private TbsysDocumentMapper sysDocMapper;

	//
	private static final int SQL_OPERATION_SELECT = 0;
	private static final int SQL_OPERATION_UPDATE = 1;
	private static final int SQL_OPERATION_INSERT = 2;
	private static final int SQL_OPERATION_DELETE = 3;
	private static final int SQL_OPERATION_ERROR = -1;
	//
	private static final int TYPE_QUERY = 0;
	private static final int TYPE_SAVE = 1;
	private static final int TYPE_DEL = 2;
	private static final int TYPE_LIST = 3;
	private static final int TYPE_FLOW = 4;

	public CommonSQLSolutionServiceImpl() {
		super();
		DEBUG_MODE = true;
		LexerParser.DEBUG_MODE = DEBUG_MODE;
	}

	/**
	 * query里面也许会有更新操作,所以需要事务控制
	 */
	@Override
	@Transactional
	public Map<String, Object> query(Map<String, Object> params, SystemUser user) {
		//
		Connection connection = null;
		Map<String, Object> result = null;
		Integer detailtype = TYPE_QUERY;
		try{

			//
			connection = getConnection();
			result =  _query(params, user,detailtype, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	/**
	 * query里面也许会有更新操作,所以需要事务控制
	 */
	@Override
	@Transactional
	public Map<String, Object> query(Map<String, Object> params, SystemUser user, Connection connection) {
		//
		Map<String, Object> result = null;
		Integer detailtype = TYPE_QUERY;
		try{
			//
			result =  _query(params, user,detailtype, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	@Override
	public Map<String, Object> flow(Map<String, Object> params, SystemUser user) {
		//
		Connection connection = null;
		Map<String, Object> result = null;
		Integer detailtype = TYPE_FLOW;
		try{
			//
			connection = getConnection();
			result =  _query(params, user,detailtype, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> list(Map<String, Object> params, SystemUser user) {
		//
		Connection connection = null;
		Map<String, Object> result = null;
		Integer detailtype = TYPE_LIST;
		try{
			//
			connection = getConnection();
			result =  _query(params, user,detailtype, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	@Override
	@Transactional
	public Map<String, Object> list(Map<String, Object> params, SystemUser user, Connection connection) {
		//
		Map<String, Object> result = null;
		Integer detailtype = TYPE_LIST;
		try{
			//
			result =  _query(params, user,detailtype, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> _query(Map<String, Object> params, SystemUser user, Integer detailtype, Connection connection) {
		// 1. 查找出查询方案
		// 2. 查出方案细节
		// 3. 根据方案细节查出SQL组列表
		// 3.1 执行某些校验和判断
		// 4. 拼SQL，按顺序执行
		// 5. 完成后返回
		//
		Map<String, Object> result = generateResponseTemMap();
		//
		if (null == params || params.isEmpty()) {
			debug("params 为空: " + params);
			return result;
		}
		//
		Map<String, Object> paramMap = wrapCommonParams(user, params);

		// 1. 查询方案
		// solutionid = "121";
		FormSolution solution = getFormSolutionByparamMap(paramMap);
		if (null == solution) {
			return result;
		}

		// 2. 查询方案细节
		//Integer detailtype = TYPE_QUERY;
		FormSolutionDetail solutionDetail = getFormSolutionDetail(solution, detailtype);
		if (null == solutionDetail) {
			return result;
		}
		//
		// 查询方案id
		// 3. 根据方案细节, 获取SQL组
		List<FormSqlGroup> sqlGroupList = getFormSqlGroupList(solutionDetail);
		if (null == sqlGroupList || sqlGroupList.isEmpty()) {
			return result;
		}
		// 按 FormSqlGroup 类型排序
		Collections.sort(sqlGroupList);
		//debug("将paramMap的Key转换为全小写");
		paramMap = MapUtil.convertKey2LowerCase(paramMap);
		// 迭代执行
		Iterator<FormSqlGroup> iteratorG = sqlGroupList.iterator();
		while (iteratorG.hasNext()) {
			FormSqlGroup formSqlGroup = (FormSqlGroup) iteratorG.next();
			//

			String resultkey = formSqlGroup.getResultkey();
			String sqlgroupname = formSqlGroup.getSqlgroupname();
			Integer signflag = formSqlGroup.getSignflag();
			// 执行SQL组
			Map<String, Object> resultMap = executeSQLGroup(formSqlGroup, paramMap, connection);
			if (null == resultMap || resultMap.isEmpty()) {
				debug("resultMap 为空: " + resultMap + "; sqlgroupname=" + sqlgroupname);
				continue;
			}
			//
			// 需要对 options 进行特殊处理
			if(null != signflag && signflag.equals(1)){
				resultkey = "options";
			}
			//
			// Object groupResult = resultMap.get(resultkey);
			// 要取出内部的Map出来合并
			Object oldValue = result.get(resultkey);
			Object newValue = resultMap.get(resultkey);
			//
			if (oldValue instanceof Map<?, ?> && newValue instanceof Map<?, ?>) { // 如果返回单个Map
				// 
				Map<String, Object> oldMap = (Map<String, Object>) oldValue;
				Map<String, Object> newMap = (Map<String, Object>) newValue;
				newMap.putAll(oldMap); // 如果有老的Map,即多次请求使用同一个Key,合并
				//  还有一种情况, root.name 时怎么处理. 
			} else if (oldValue instanceof List<?>) {
				// 如果多个List,具有同一个KEY, 出错了? 
				//List<?> oldMapList = (List<?>) oldValue;
				//oldMapList.add(null);
				//
				//result.put(resultkey, oldMapList);
			} else {
				// 如果 main SQL组执行成功
			}
			//
			result.putAll(resultMap);
			//
			Object _testsqlResult = resultMap.get("testsqlResult");
			Object _mainsqlResult = resultMap.get("mainsqlResult");
			Object _standbysqlResult = resultMap.get("standbysqlResult");
			//
			boolean testsqlResult = false;
			boolean mainsqlResult = false;
			boolean standbysqlResult = false;
			//
			if("1".equals(""+_testsqlResult)){
				testsqlResult = true;
			}
			if("1".equals(""+_mainsqlResult)){
				mainsqlResult = true;
			}
			if("1".equals(""+_standbysqlResult)){
				standbysqlResult = standbysqlResult || true;
			}

			//
			Integer startorder = formSqlGroup.getStartorder();
			Integer ismaingroup = formSqlGroup.getIsmaingroup();
			Integer sqlgrouptype = formSqlGroup.getSqlgrouptype();
			//
			if(testsqlResult && mainsqlResult){
				Integer ZERO = 0;
				Integer ONE = 1;
				// query
				if(ONE.equals(startorder)
						&& ONE.equals(ismaingroup)
						&& ZERO.equals(sqlgrouptype)
						){
					// 不执行
					debug("主SQL组检测成功,不执行余下的SQL组");
					break; //
				}
			}
		}
		//
		debug("查询结果result.size()= " + result.size()); 
		return result;
	}


	@Override
	@Transactional
	public Map<String, Object> save(Map<String, Object> params, SystemUser user) {
		//
		Connection connection = null;
		Map<String, Object> result = null;
		try{

			//
			connection = getConnection();
			result =  _save(params, user, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	@Override
	@Transactional
	public Map<String, Object> save(Map<String, Object> params, SystemUser user, Connection connection) {
		//
		Map<String, Object> result = null;
		try{
			//
			result =  _save(params, user, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	/**
	 * 批量保存 (暂时 不支持临时查询，主SQL或备用SQL不充许是select类型的sql,  sql中的参数不能含有 @@{key} )
	 * @author YangZR	2016-08-13
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> batchSave(List<Map<String, Object>> paramList, SystemUser user){
		
		Connection connection = null;
		Map<String, Object> result = null;
		try{

			//
			connection = getConnection();
			result =  _batchSave(paramList, user, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
		
	}
	
	/**
	 * 批量保存 (暂时 不支持临时查询，主SQL或备用SQL不充许是select类型的sql)
	 * @author YangZR	2016-08-13
	 * @param params
	 * @param user
	 * @return
	 */
	public Map<String, Object> batchSave(List<Map<String, Object>> paramList, SystemUser user, Connection connection){
		//
		Map<String, Object> result = null;
		try{
			//
			result =  _batchSave(paramList, user, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _save(Map<String, Object> params, SystemUser user, Connection connection) {
		// 1. 查找出查询方案
		// 2. 查出方案细节
		// 3. 根据方案细节查出SQL组列表
		// 3.1 执行某些校验和判断
		// 4. 拼SQL，按顺序执行
		// 5. 完成后返回
		//
		Map<String, Object> result = generateResponseTemMap();
		//
		if (null == params || params.isEmpty()) {
			debug("params 为空: " + params);
			return result;
		}
		//
		Map<String, Object> paramMap = wrapCommonParams(user, params);

		// 1. 查询方案
		// solutionid = "121";
		FormSolution solution = getFormSolutionByparamMap(paramMap);
		if (null == solution) {
			return result;
		}

		// 2. 查询方案细节
		Integer detailtype = TYPE_SAVE;
		FormSolutionDetail solutionDetail = getFormSolutionDetail(solution, detailtype);
		if (null == solutionDetail) {
			return result;
		}
		//
		// 查询方案id
		// 3. 根据方案细节, 获取SQL组
		List<FormSqlGroup> sqlGroupList = getFormSqlGroupList(solutionDetail);
		if (null == sqlGroupList || sqlGroupList.isEmpty()) {
			return result;
		}
		// 按 FormSqlGroup 类型排序
		Collections.sort(sqlGroupList);
		//debug("将paramMap的Key转换为全小写");
		paramMap = MapUtil.convertKey2LowerCase(paramMap);
		// 迭代执行
		Iterator<FormSqlGroup> iteratorG = sqlGroupList.iterator();
		while (iteratorG.hasNext()) {
			FormSqlGroup formSqlGroup = (FormSqlGroup) iteratorG.next();
			//
			String resultkey = formSqlGroup.getResultkey();
			String sqlgroupname = formSqlGroup.getSqlgroupname();
			// 执行SQL组
			Map<String, Object> resultMap = executeSQLGroup(formSqlGroup, paramMap, connection);
			if (null == resultMap || resultMap.isEmpty()) {
				debug("resultMap 为空: " + resultMap + "; sqlgroupname=" + sqlgroupname);
				continue;
			}
			//
			// Object groupResult = resultMap.get(resultkey);
			// 要取出内部的Map出来合并
			Object oldValue = result.get(resultkey);
			Object newValue = resultMap.get(resultkey);
			if (oldValue instanceof Map<?, ?> && newValue instanceof Map<?, ?>) { // 如果返回单个Map
				// 
				Map<String, Object> oldMap = (Map<String, Object>) oldValue;
				Map<String, Object> newMap = (Map<String, Object>) newValue;
				newMap.putAll(oldMap); // 如果有老的Map,即多次请求使用同一个Key,合并
				// 还有一种情况, root.name 时怎么处理. 
			} else if (oldValue instanceof List<?>) {
				// 如果多个List,具有同一个KEY, 出错了?
				//List<?> oldMapList = (List<?>) oldValue;
				//oldMapList.add(null);
				//
				//result.put(resultkey, oldMapList);
			} else {
				// 不进行特殊处理
			}
			//
			result.putAll(resultMap);
		}
		//
		debug("查询结果result.size()= " + result.size());
		return result;
	}
	
	
	private Map<String, Object> _batchSave(List<Map<String, Object>> paramList, SystemUser user, Connection connection) {
		//
		Map<String, Object> result = generateResponseTemMap();
		//
		if (null == paramList || paramList.isEmpty()) {
			debug("params 为空: " + paramList);
			return result;
		}
		//
		Map<String, Object> oneMap = paramList.get(0);//选择其中一个Map，从其中获取solutionid

		// 1. 查询方案
		// solutionid = "121";
		FormSolution solution = getFormSolutionByparamMap(oneMap);
		if (null == solution) {
			return result;
		}

		// 2. 查询方案细节
		Integer detailtype = TYPE_SAVE;
		FormSolutionDetail solutionDetail = getFormSolutionDetail(solution, detailtype);
		if (null == solutionDetail) {
			return result;
		}
		//
		// 查询方案id
		// 3. 根据方案细节, 获取SQL组
		List<FormSqlGroup> sqlGroupList = getFormSqlGroupList(solutionDetail);
		if (null == sqlGroupList || sqlGroupList.isEmpty()) {
			return result;
		}
		// 按 FormSqlGroup 类型排序
		Collections.sort(sqlGroupList);
		//
		paramList = MapUtil.convertKeyList2LowerCase(paramList);
		// 迭代执行
		Iterator<FormSqlGroup> iteratorG = sqlGroupList.iterator();
		while (iteratorG.hasNext()) {
			FormSqlGroup formSqlGroup = (FormSqlGroup) iteratorG.next();
			//
//			String resultkey = formSqlGroup.getResultkey();
			String sqlgroupname = formSqlGroup.getSqlgroupname();
			debug("执行数据方案ID："+formSqlGroup.getSolutionid()+"，方案细节ID："+formSqlGroup.getDetailid()+"，sqlgroupname=" + sqlgroupname);
			// 执行SQL组
			Map<String, Object> resultMap = executeSQLGroup4BatchSave(user, formSqlGroup, paramList, connection);
			if (null == resultMap || resultMap.isEmpty()) {
				debug("resultMap 为空: " + resultMap + "; sqlgroupname=" + sqlgroupname);
				continue;
			}
			//
			result.putAll(resultMap);
		}
		//垃圾回收
		System.gc();
		//
		return result;
	}
	

	@Override
	@Transactional
	public Map<String, Object> delete(Map<String, Object> params, SystemUser user) {

		//
		Connection connection = null;
		Map<String, Object> result = null;
		try{

			//
			connection = getConnection();
			result =  _delete(params, user, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	@Override
	@Transactional
	public Map<String, Object> delete(Map<String, Object> params, SystemUser user, Connection connection) {
		//
		Map<String, Object> result = null;
		try{
			//
			result =  _delete(params, user, connection);
		} finally {
			//closeConnection(connection);
		}
		//
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> _delete(Map<String, Object> params, SystemUser user, Connection connection) {
		// 1. 查找出查询方案
		// 2. 查出方案细节
		// 3. 根据方案细节查出SQL组列表
		// 3.1 执行某些校验和判断
		// 4. 拼SQL，按顺序执行
		// 5. 完成后返回
		//
		Map<String, Object> result = generateResponseTemMap();
		//
		if (null == params || params.isEmpty()) {
			debug("params 为空: " + params);
			return result;
		}
		//
		Map<String, Object> paramMap = wrapCommonParams(user, params);

		// 1. 查询方案
		// solutionid = "121";
		FormSolution solution = getFormSolutionByparamMap(paramMap);
		if (null == solution) {
			return result;
		}

		// 2. 查询方案细节
		Integer detailtype = TYPE_DEL;
		FormSolutionDetail solutionDetail = getFormSolutionDetail(solution, detailtype);
		if (null == solutionDetail) {
			return result;
		}
		//
		// 查询方案id
		// 3. 根据方案细节, 获取SQL组
		List<FormSqlGroup> sqlGroupList = getFormSqlGroupList(solutionDetail);
		if (null == sqlGroupList || sqlGroupList.isEmpty()) {
			return result;
		}
		// 按 FormSqlGroup 类型排序
		Collections.sort(sqlGroupList);
		//debug("将paramMap的Key转换为全小写");
		paramMap = MapUtil.convertKey2LowerCase(paramMap);
		// 迭代执行
		Iterator<FormSqlGroup> iteratorG = sqlGroupList.iterator();
		while (iteratorG.hasNext()) {
			FormSqlGroup formSqlGroup = (FormSqlGroup) iteratorG.next();
			//
			String resultkey = formSqlGroup.getResultkey();
			String sqlgroupname = formSqlGroup.getSqlgroupname();
			// 执行SQL组
			Map<String, Object> resultMap = executeSQLGroup(formSqlGroup, paramMap, connection);
			if (null == resultMap || resultMap.isEmpty()) {
				debug("resultMap 为空: " + resultMap + "; sqlgroupname=" + sqlgroupname);
				continue;
			}
			//
			// Object groupResult = resultMap.get(resultkey);
			Object OldValue = result.get(resultkey);
			if (OldValue instanceof Map<?, ?>) {
				// 
				Map<String, Object> oldMap = (Map<String, Object>) OldValue;
				resultMap.putAll(oldMap);
			}
			//
			// result.put(resultkey, resultMap);
			result.putAll(resultMap);
		}
		//
		debug("尚未完全处理delete!!!");
		debug("查询结果result= " + result);
		return result;
	}

	private List<FormSqlGroup> getFormSqlGroupList(FormSolutionDetail solutionDetail) {
		List<FormSqlGroup> sqlGroupList = null;
		if (null == solutionDetail || null == solutionDetail.getSolutionid()) {
			return sqlGroupList;
		}
		//
		// 查询方案id
		String solutionid = solutionDetail.getSolutionid();
		String detailid = solutionDetail.getDetailid();
		// 根据方案细节, 获取SQL组
		Map<String, Object> condition2 = new HashMap<String, Object>();
		condition2.put("solutionid", solutionid);
		condition2.put("detailid", detailid);
		condition2.put("sortField", "STARTORDER");
		condition2.put("sortOrder", "asc");
		sqlGroupList = sqlGroupMapper.listByCondition(condition2);

		if (null == sqlGroupList || sqlGroupList.isEmpty()) {
			// 没有结果
			String message = ("sqlGroupList 为空: " + sqlGroupList + "; solutionid=" + solutionid + "; detailid=" + detailid);
			debug(message);
			// throw new RuntimeException(message);
		} else {
			debug("获得SQL组: sqlGroupList.size(): " + sqlGroupList.size());
		}
		return sqlGroupList;
	}

	private FormSolutionDetail getFormSolutionDetail(FormSolution solution, Integer detailtype) {
		FormSolutionDetail solutionDetail = null;
		//
		if (null == solution || null == detailtype || null == solution.getSolutionid()) {
			return solutionDetail;
		}
		// 查询方案id
		String solutionid = solution.getSolutionid();
		Map<String, Object> condition1 = new HashMap<String, Object>();
		condition1.put("detailtype", detailtype);
		condition1.put("solutionid", solutionid);
		condition1.put("sortField", "DETAILTYPE");
		condition1.put("sortOrder", "asc");
		List<FormSolutionDetail> solutionDetails = solutionDetailMapper.listByCondition(condition1);
		if (null == solutionDetails || solutionDetails.isEmpty()) {
			// 没有结果
			String message = ("solutionDetails 为空: " + solutionDetails + "; solutionid=" + solutionid + "; detailtype=" + detailtype);
			debug(message);
			// throw new RuntimeException(message);
		} else {
			//
			solutionDetail = solutionDetails.get(0);
		}
		//
		return solutionDetail;
	}

	private FormSolution getFormSolutionByparamMap(Map<String, Object> paramMap) {
		// 查询方案
		String solutionid = StringNumberUtil.returnString2(paramMap.get("solutionid"));
		FormSolution solution = solutionMapper.selectById(solutionid);
		if (null == solution) {
			String message = ("solution 为空: " + solution + "; solutionid=" + solutionid);
			debug(message);
			// throw new RuntimeException(message);
		}
		return solution;
	}

	/**
	 * 执行SQL组<br/>
	 * 1. 如果没有 testsql,则直接执行 mainsql. 2. 有 testsql,则执行 3. testsql执行有结果, 则执行
	 * mainsql, 否则执行 standbysql 4. delsql 是用来判断, 如果有主键,但是其他某个关键字段为空的,则执行,依赖参数映射
	 * 
	 * @param formSqlGroup
	 * @param paramMap
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> executeSQLGroup(FormSqlGroup sqlGroup, Map<String, Object> paramMap, Connection connection) {
		// 0. 数据校验
		// 1. 取出对应的SQL
		// 2. 判断循环标志
		// 3. 拼SQL
		// 4. 判断SQL类型
		// 5. 权限判断、合法性判断;
		// 5.0 除insert,select外, 必须有where; where 后面必须有参数,不能为空
		// 5.1可能涉及括号,子查询
		// 6. 执行SQL. 一部分SQL吃掉异常
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (null == sqlGroup) {
			debug("formSqlGroup 为空: " + sqlGroup);
			return resultMap;
		}
		//
		Integer startorder = sqlGroup.getStartorder();
		Integer ismaingroup = sqlGroup.getIsmaingroup();
		//Integer cycleflag = sqlGroup.getCycleflag();
		//Integer cycletimes = sqlGroup.getInt1();
		//Integer signflag = sqlGroup.getSignflag();
		//Integer sqlgrouptype = sqlGroup.getSqlgrouptype();

		//String sqlgroupname = sqlGroup.getSqlgroupname();
		//String remark = sqlGroup.getRemark();
		String resultkey = sqlGroup.getResultkey();

		String testsql = sqlGroup.getTestsql();
		String mainsql = sqlGroup.getMainsql();
		String standbysql = sqlGroup.getStandbysql();
		String delsql = sqlGroup.getDelsql();
		String text2 = sqlGroup.getText2();
		//
		// 校验
		boolean hasTestsql = false;
		boolean hasMainsql = false;
		boolean hasStandbysql = false;
		boolean hasDelsql = false;

		boolean testsqlResult = false;
		boolean mainsqlResult = false;
		boolean standbysqlResult = false;

		if (StringNumberUtil.notEmpty(testsql)) {
			hasTestsql = true;
		}
		if (StringNumberUtil.notEmpty(mainsql)) {
			hasMainsql = true;
		}
		if (StringNumberUtil.notEmpty(standbysql)) {
			hasStandbysql = true;
		}
		if (StringNumberUtil.notEmpty(delsql)) {
			hasDelsql = true;
		}
		//
		if(StringNumberUtil.notEmpty(text2)){
			// 解析,封装附加参数
			text2 = LexerParser.parseTemplate2SQLWithStringParam(text2, paramMap);
			sqlGroup.setText2(text2);
		}
		
		// 1. 执行测试SQL
		if (hasTestsql) {
			debug("检测到 testsql,尝试解析.");
			// testsql 必须是 select 类型
			int sqlOperationType = parseSqlOperationType(testsql);
			if (SQL_OPERATION_SELECT != sqlOperationType) {
				debug("testsql 类型必须是查询: " + testsql);
				return resultMap;
			}
			// 
			// 解析,封装参数
			String sql = LexerParser.parseTemplate2SQLWithStringParam(testsql, paramMap);
			// 执行 testsql
			String textTemplate = testsql;
			// 执行
			Map<String, Object> map = executeSQL(textTemplate, paramMap,connection);
			// 只取select
			Object select = map.get("select");
			if (null != select) {
				List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) select;
				if (null != resultMapList && !resultMapList.isEmpty()) {
					//
					testsqlResult = true;
					int listSize = resultMapList.size();
					// 拦截补丁
					Map<String, Object> obj1 = resultMapList.get(0);
					// 如果有对象,如果 count(*) 不是0的情况,那就算执行成功
					int keySize = obj1.size();
					if (listSize > 1) {
						// 多条记录,认为测试通过
						testsqlResult = true;
					} else if (keySize < 1) {
						// 失败,空对象Map,什么都没获取到
						testsqlResult = false;
					} else if (1 == keySize) {
						// 只获取了一个字段,认为是 count,如果为0,则认为测试为假
						// 虽然有坑,但是判断语句请不要 select 0
						Object value1 = MapUtil.getFirstValue(obj1);
						//
						int count = StringNumberUtil.parseInt(value1, -1);
						if (0 == count) {
							testsqlResult = false;
						}
					}

					resultMap.put("testsqlResult", testsqlResult? 1:0);
					//
					debug("testsql执行完成: -----------------\n" + sql +"\n---------------------------");
					debug("testsql执行结果: " + obj1 + ";解析为: " + testsqlResult);
				}
			}
		}
		// 2. 两者相等,则执行 mainSQL
		if ((hasTestsql == testsqlResult) && hasMainsql) {
			debug("检测到 mainsql,尝试解析执行");
			// 解析,封装参数
			//String sql = LexerParser.parseTemplate2SQLWithStringParam(mainsql, paramMap);
			// 执行 mainsql
			String textTemplate = mainsql;
			// 执行
			Map<String, Object> map = executeSQL(textTemplate, paramMap,connection);
			//
			int sqlOperationType = parseSqlOperationType(mainsql);
			if (SQL_OPERATION_SELECT == sqlOperationType) {
				// 只取select
				Object select = map.get("select");
				if (null != select) {
					List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) select;

					boolean status = processMainStandbySelectResult(resultMap, resultMapList, sqlGroup, paramMap);
					if(status){
						mainsqlResult = true;
						// 只要有一个对象,那就算执行成功
						debug("mainsql执行成功: -----------------\n" + mainsql +"\n---------------------------");
						debug("mainsql执行结果,resultMapList.size(): " + resultMapList.size());
					}
				}
			} else if (SQL_OPERATION_INSERT == sqlOperationType || SQL_OPERATION_UPDATE == sqlOperationType) {
				// 插入或更新.
				if("".length() > 5 && null != ismaingroup && 1 == ismaingroup && 1 == startorder){
					// 如果是主SQL组.并且是插入或者更新,并且 startorder=1, 则属于表单. 走特殊通道.
					
					//
					mainsqlResult = processSysDocUpdate(sqlGroup ,paramMap);
				} else {
					// 普通SQL
					// 执行
					Object update = map.get("update");
					Object insert = map.get("insert");
					//
					if(null != update && "1".equals(""+update)){
						mainsqlResult = true;
					} else if(null != insert && "1".equals(""+insert)){
						mainsqlResult = true;
					}
				}
				//
			} else if (SQL_OPERATION_DELETE == sqlOperationType) {
				// 插入或更新.
				
				// 普通SQL
				// 执行
				Object update = map.get("update");
				Object insert = map.get("insert");
				Object delete = map.get("delete");
				//
				if(null != update && "1".equals(""+update)){
					mainsqlResult = true;
				} else if(null != insert && "1".equals(""+insert)){
					mainsqlResult = true;
				} else if(null != delete && "1".equals(""+delete)){
					mainsqlResult = true;
				}
				//
			} else {
				debug("其他类型的执行结果还需要封装");
			}

			resultMap.put("mainsqlResult", mainsqlResult? 1:0);
		}
		// 3. testsql 执行出错,或者没有 mainsql, 或者 mainsql 执行出错, 则执行 standbysql
		if ((!hasMainsql || (hasTestsql != testsqlResult) || !mainsqlResult) && hasStandbysql) { // 有备用SQL
			debug("检测到 standbysql,尝试解析执行");
			// 解析,封装参数
			//String sql = LexerParser.parseTemplate2SQLWithStringParam(standbysql, paramMap);
			// 执行 standbysql
			String textTemplate = standbysql;
			// 执行
			Map<String, Object> map = executeSQL(textTemplate, paramMap,connection);
			//
			int sqlOperationType = parseSqlOperationType(standbysql);
			if (SQL_OPERATION_SELECT == sqlOperationType) {
				// 只取select
				Object select = map.get("select");
				if (null != select) {
					List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) select;

					boolean status = processMainStandbySelectResult(resultMap, resultMapList, sqlGroup, paramMap);
					if(status){
						standbysqlResult = true;
						debug("standbysql执行成功: -----------------\n" + standbysql +"\n---------------------------");
					}
					// 只要有一个对象,那就算执行成功
					debug("standbysql执行结果,resultMapList.size(): " + resultMapList.size());
				}
			} else if (SQL_OPERATION_INSERT == sqlOperationType || SQL_OPERATION_UPDATE == sqlOperationType) {
					// 插入或更新.
					if("".length() > 5 && null != ismaingroup && 1 == ismaingroup && 1 == startorder){
						// 如果是主SQL组.并且是插入或者更新,并且 startorder=1, 则属于表单. 走特殊通道.
						standbysqlResult = processSysDocInsert(sqlGroup ,paramMap);
					} else {

						// else {
						// 普通SQL
						Object update = map.get("update");
						Object insert = map.get("insert");
						//
						if(null != update && "1".equals(""+update)){
							standbysqlResult = true;
						} else if(null != insert && "1".equals(""+insert)){
							standbysqlResult = true;
						}
					}
			} else {
					debug("其他类型的执行结果还需要封装");
			}
			//
			resultMap.put("standbysqlResult", standbysqlResult? 1:0);
		}

		//
		if (mainsqlResult || standbysqlResult) {
			resultMap.put("_status", 1);
			// 
		} else {
			resultMap.put("_status", 0);
		}
		//
		if (hasDelsql) {
			debug("检测到delsql: " + delsql + "; 暂时不予处理!!!!!!");
		}
		//
		if (null != ismaingroup && ismaingroup > 0) {
			// 主SQL,处理表单二进制的

			debug("ismaingroup:" + ismaingroup + " ; 未处理!");
			//
		}
		// 如果resultkey中有点,则对resultMap进行处理
		// 组装 ... 封装到 root 下.
		resultMap = processResultKeyDot(resultMap, resultkey);
		//
		return resultMap;
	}
	
	
	
	private Map<String, Object> executeSQLGroup4BatchSave(SystemUser user, FormSqlGroup sqlGroup, List<Map<String,Object>> paramList, Connection connection) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (null == sqlGroup) {
			debug("formSqlGroup 为空: " + sqlGroup);
			return resultMap;
		}
		//
//		Integer startorder = sqlGroup.getStartorder();
//		String resultkey = sqlGroup.getResultkey();

		String testsql = sqlGroup.getTestsql();
		String mainsql = sqlGroup.getMainsql();
		String standbysql = sqlGroup.getStandbysql();
		//
		// 校验
		boolean hasTestsql = false;
		boolean hasMainsql = false;
		boolean hasStandbysql = false;

		boolean testsqlResult = false;
//		boolean mainsqlResult = false;
//		boolean standbysqlResult = false;

		if (StringNumberUtil.notEmpty(testsql)) {
			hasTestsql = true;
		}
		if (StringNumberUtil.notEmpty(mainsql)) {
			hasMainsql = true;
		}
		if (StringNumberUtil.notEmpty(standbysql)) {
			hasStandbysql = true;
		}
		
		if (hasTestsql) {// testsql 必须是 select 类型
			int sqlOperationType = parseSqlOperationType(testsql);
			if (SQL_OPERATION_SELECT != sqlOperationType) {
				debug("testsql 类型必须是查询: " + testsql);
				return resultMap;
			}
		}
		
		int mainsqlOperationType = parseSqlOperationType(mainsql);
		if (SQL_OPERATION_SELECT == mainsqlOperationType) {
			debug("mainsql 类型不能是查询SQL: " + mainsql);
			return resultMap;
		}
		
		//
		int standbysqlOperationType = parseSqlOperationType(standbysql);
		if (SQL_OPERATION_SELECT == standbysqlOperationType) {
			debug("standbysql 类型不能是查询SQL: " + standbysql);
			return resultMap;
		}
		
		List<VariableToken> testVariableToken = null;
		List<VariableToken> mainVariableToken = null;
		List<VariableToken> standbyVariableToken = null;
		
		ResultSet resultSet = null;
		PreparedStatement testStatement = null;
		PreparedStatement mainStatement = null;
		PreparedStatement standbyStatement = null;
		
		//
		if(hasTestsql){
			if(false == validateSqlSyntax4BatchSave(testsql,"testsql")){
				return resultMap;
			}
			testVariableToken = LexerParser.parseVariables2Token(testsql);
		}
		
		if (hasMainsql) {
			if(!checkSecurity(mainsql)){
				debug("安全检查失败!mainsql=" + mainsql);
				return resultMap;
			}
			if(false == validateSqlSyntax4BatchSave(mainsql,"mainsql")){
				return resultMap;
			}
			mainVariableToken = LexerParser.parseVariables2Token(mainsql);
		}
		
		if (hasStandbysql) {
			if(!checkSecurity(standbysql)){
				debug("安全检查失败!standbysql=" + standbysql);
				return resultMap;
			}
			if(false == validateSqlSyntax4BatchSave(standbysql,"standbysql")){
				return resultMap;
			}
			standbyVariableToken = LexerParser.parseVariables2Token(standbysql);
		}
		
		try {
			connection.setAutoCommit(false);//设置后，在jdbc批量执行时，可提高效率
			//
			if (hasTestsql) {
				testStatement = SolutionJDBCHelper.getPreparedStatement4BatchSave(connection, testsql, testVariableToken);
			}
			if (hasMainsql) {
				mainStatement = SolutionJDBCHelper.getPreparedStatement4BatchSave(connection, mainsql, mainVariableToken);
			}
			if (hasStandbysql) {
				standbyStatement = SolutionJDBCHelper.getPreparedStatement4BatchSave(connection, standbysql, standbyVariableToken);
			}
			int count = 0;
			int commitCount = 0;
			Map<String, Object> commonParamMap = wrapCommonParams(user);
			//
			for(Map<String,Object> paramMap : paramList){
				count ++;
				//初始化布尔值
				testsqlResult = false;
				paramMap.putAll(commonParamMap);
				
				if (hasTestsql) {
//					debug("检测到 testsql,尝试解析.");
//					String textTemplate = testsql;
//					Map<String, Object> map = executeSQL(textTemplate, paramMap,connection);
					SolutionJDBCHelper.injectStatementParamMap(testStatement, testVariableToken, paramMap);
					
					resultSet = testStatement.executeQuery();
					List<Map<String, Object>> testResultList = SolutionJDBCHelper.parseResultSetToMapList(resultSet);
					
					testsqlResult = getExecuteTestStatus(testResultList);
					SolutionJDBCHelper.close(resultSet);
				}
				
				// 2. 两者相等,则执行 mainSQL
				if ((hasTestsql == testsqlResult) && hasMainsql) {
					SolutionJDBCHelper.injectStatementParamMap(mainStatement, mainVariableToken, paramMap);
					mainStatement.addBatch();
				}
				
				// 3. testsql 执行出错,或者没有 mainsql, 或者 mainsql 执行出错, 则执行 standbysql
				if ((!hasMainsql || (hasTestsql != testsqlResult)) && hasStandbysql) {//备用SQL
					SolutionJDBCHelper.injectStatementParamMap(standbyStatement, standbyVariableToken, paramMap);
					standbyStatement.addBatch();
				}
				
				if(count%20 == 0){//每20次JDBC执行executeBatch
					if(null != mainStatement){
						mainStatement.executeBatch();
					}
					if(null != standbyStatement){
						standbyStatement.executeBatch();
					}
				}
				
				if(count%100 == 0){//每100次 提交一下
					connection.commit();//事务提交
					commitCount ++;
					debug("第"+commitCount+"次提交");
				}
			}
			
			if(null != mainStatement){
				mainStatement.executeBatch();
			}
			if(null != standbyStatement){
				standbyStatement.executeBatch();
			}
			
			connection.commit();//事务提交
			commitCount++;
			debug("第"+commitCount+"次提交");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			SolutionJDBCHelper.close(resultSet);
			SolutionJDBCHelper.close(testStatement);
			SolutionJDBCHelper.close(mainStatement);
			SolutionJDBCHelper.close(standbyStatement);
		}

		//
		resultMap.put("_status", 1);
		//
		return resultMap;
	}
	
	
	private boolean validateSqlSyntax4BatchSave(String sql, String sqlType){
		if(LexerParser.isSqlContain2At(sql)){
			debug(sqlType + "中不能含有两 个@@，  "+sqlType+"=" + sql);
			return false;
		}
		
		if(LexerParser.isSqlContainIfRegex(sql) || LexerParser.isSqlContainIfTag(sql)){
			debug(sqlType + "中不能含有IF标签，  "+sqlType+"=" + sql);
			return false;
		}
		
		if(LexerParser.isSqlContainForeachTag(sql)){
			debug(sqlType + "中不能含有Foreach标签，  "+sqlType+"=" + sql);
			return false;
		}
		
		if(LexerParser.isSqlContainChooseWhenTag(sql)){
			debug(sqlType + "中不能含有Choose When标签，  "+sqlType+"=" + sql);
			return false;
		}
		
		return true;
	}
	
	private boolean getExecuteTestStatus(List<Map<String, Object>> resultMapList){
		boolean testsqlResult = false;
		
		if (null != resultMapList && !resultMapList.isEmpty()) {
			//
			testsqlResult = true;
			int listSize = resultMapList.size();
			// 拦截补丁
			Map<String, Object> obj1 = resultMapList.get(0);
			// 如果有对象,如果 count(*) 不是0的情况,那就算执行成功
			int keySize = obj1.size();
			if (listSize > 1) {
				// 多条记录,认为测试通过
				testsqlResult = true;
			} else if (keySize < 1) {
				// 失败,空对象Map,什么都没获取到
				testsqlResult = false;
			} else if (1 == keySize) {
				// 只获取了一个字段,认为是 count,如果为0,则认为测试为假
				// 虽然有坑,但是判断语句请不要 select 0
				Object value1 = MapUtil.getFirstValue(obj1);
				//
				int count = StringNumberUtil.parseInt(value1, -1);
				if (0 == count) {
					testsqlResult = false;
				}
			}
		}
		
		return testsqlResult;
	}
	
	
	/**
	 * 处理主数据与备用查询SQL
	 * @param resultMap 最终返回给上一层的Map
	 * @param resultMapList SQL查询得到的List
	 * @param sqlGroup 查询SQL组对象
	 * @return 成功状态
	 */
	private boolean processMainStandbySelectResult(
			Map<String, Object> resultMap, List<Map<String, Object>> resultMapList, FormSqlGroup sqlGroup, Map<String, Object> paramMap){
		
		boolean successStatus = false;
		//

		//
		Integer startorder = sqlGroup.getStartorder();
		Integer ismaingroup = sqlGroup.getIsmaingroup();
		Integer cycleflag = sqlGroup.getCycleflag();
		Integer cycletimes = sqlGroup.getInt1();
		Integer signflag = sqlGroup.getSignflag();
		//Integer sqlgrouptype = sqlGroup.getSqlgrouptype();

		//String sqlgroupname = sqlGroup.getSqlgroupname();
		//String remark = sqlGroup.getRemark();
		String resultkey = sqlGroup.getResultkey();

		
		if (null != resultMapList && !resultMapList.isEmpty()) {
			//
			Map<String, Object> obj1 = resultMapList.get(0);
			//			
			if ((1 == startorder) && (1 == ismaingroup)) { // 主记录
				//
				resultMap.put(resultkey, obj1); // 单条记录
			} else if (null != signflag && 2 == signflag) { // 临时.
				// 临时. 需要加到参数列表
				paramMap.putAll(obj1);
			}  else if (null != signflag && 1 == signflag) { // 下拉...
				// 下拉处理，需要在循环处理之前
				processSelectOptions(resultMap, resultkey, resultMapList, cycleflag, cycletimes);
			} else if (null != signflag && 3 == signflag) { // 多条记录
				// 列表
				resultMap.put(resultkey, resultMapList); // 
			} else if (null != cycleflag && 1 == cycleflag) { // 循环标志. 
				//
				// 处理循环情况, 多条记录
				Map<String, Object> mapC = processCycleRecord(resultMapList);
				// 
				resultMap.put(resultkey, mapC); // 多条记录
			} else if (resultkey.contains(".")) { // 包含句点 .
				//
				resultMap.put(resultkey, resultMapList); // 
			} else {
				resultMap.put(resultkey, obj1); // 认为是单条记录
			}
			//
			successStatus = true;
		}
		//
		return successStatus;
	}
	
	
	
	private boolean processSysDocInsert(FormSqlGroup sqlGroup, Map<String, Object> paramMap) {
		//
		Map<String, String> keyMap = new HashMap<String, String>();
		//
		//String appendCondition = sqlGroup.getText2();
		keyMap.put("_userid", "userid");
		keyMap.put("_prisonid", "departid");
		//keyMap.put("_appendCondition", appendCondition);
		//
		paramMap = MapUtil.convertKey2Another(paramMap, keyMap);
		//
		paramMap.put("introduction", "测试保存的表单");
		//
		// 处理 processSysDocInsert 
		int rows = 0;//sysDocMapper.insertByMap(paramMap);
		return 1 == rows;
	}

	private boolean processSysDocUpdate(FormSqlGroup sqlGroup, Map<String, Object> paramMap) {
		//
		Map<String, String> keyMap = new HashMap<String, String>();
		//
		String appendCondition = sqlGroup.getText2();
		keyMap.put("_userid", "userid");
		keyMap.put("_prisonid", "departid");
		keyMap.put("_appendCondition", appendCondition);
		//
		paramMap = MapUtil.convertKey2Another(paramMap, keyMap);
		//
		// 处理SysDocUpdate
		int rows = 0;//sysDocMapper.updateByMap(paramMap);
		//
		return 1 == rows;
	}

	// 处理下拉选项
	private void processSelectOptions(Map<String, Object> resultMap, String resultkey,
			List<Map<String, Object>> resultMapList, Integer cycleflag, Integer cycletimes) {
		if(null == resultMap || null == resultkey || null == resultMapList){
			return;
		}
		Map<String, Object> optionMap = parseSelectOptions(resultkey, resultMapList);//, cycleflag, cycletimes);
		//
		String optionsKey = "options";
		resultMap.put(optionsKey, optionMap);
		//
		if(null == cycleflag || 0==cycleflag){
			return;
		}
		// 处理循环...下拉选项
		if(null == cycletimes || cycletimes < 1){
			cycletimes = 10;
		}
		//
		Object value = MapUtil.getFirstValue(optionMap);// optionMap.get(resultkey);
		//
		for (int timesNo = 0; timesNo < cycletimes; timesNo++) {
			String key = resultkey + "" + timesNo;
			optionMap.put(key, value);
		}
	}
	private Map<String, Object> parseSelectOptions(String resultkey, List<Map<String, Object>> resultMapList){//, Integer cycleflag, Integer cycletimes){
		Map<String, Object> optionMap = new HashMap<String, Object>();
		// 数据校验
		if(null == resultkey || null == resultMapList){
			return optionMap;
		}
		// 转为小写
		resultMapList = MapUtil.convertKeyList2LowerCase(resultMapList);
		String resultStr = "";
		//
		Iterator<Map<String, Object>> iteratorM = resultMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, Object> codeMap = (Map<String, Object>) iteratorM.next();
			// 默认都是 syscode
			// [[bj]]北京||[[hb]]河北
			Object scearch = codeMap.get("scearch");
			Object name = codeMap.get("name");
			//
			String value = ("[[" + StringNumberUtil.toString(scearch) + "]]" + StringNumberUtil.toString(name));
			//
			resultStr += value;
			if(iteratorM.hasNext()){
				// 还有下一个元素
				resultStr += "||";
			}
		}
		// 循环option在外层进行处理
		optionMap.put(resultkey, resultStr);
		
		return optionMap;
	}

	private Map<String, Object> processResultKeyDot(Map<String, Object> resultMap, String resultkey) {
		if(null == resultMap || null == resultkey || !resultkey.contains(".")){
			//
			return resultMap;
		}
		// 拆分
		String[] keys = resultkey.split("\\.");
		if(null == keys || keys.length < 2){
			// 不存在,或者只有1个
			return resultMap;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(resultMap);
		// 最终的结果值
		Object value = resultMap.get(resultkey);
		// 外层包装器,临时替换
		Map<String, Object> mapWrap = null;
		//
		for (int i = (keys.length-1); i >= 0; i--) {
			String key = keys[i];
			//
			if(i > 0){
				Map<String, Object>  currentMap = new HashMap<String, Object>();
				//
				if(null == mapWrap){
					currentMap.put(key, value); // 最底层
				} else {
					currentMap.put(key, mapWrap);
				}
				//  切换外层包装
				mapWrap = currentMap;
			}
			//
			if(0 == i){
				// 顶层
				map.put(key, mapWrap);
			}
		}
		//
		return map;
	}

	private Map<String, Object> processCycleRecord(List<Map<String, Object>> resultMapList) {
		//
		Map<String, Object> mapC = new HashMap<String, Object>();
		//
		if(null == resultMapList || resultMapList.isEmpty()){
			return mapC;
		}
		
		// 外层,遍历 List
		Iterator<Map<String, Object>> iteratorM = resultMapList.iterator();
		for(int cycleNo = 0;iteratorM.hasNext();cycleNo ++) {
			Map<String, Object> mapR = (Map<String, Object>) iteratorM.next();
			if(null == mapR || mapR.isEmpty()){
				continue;
			}
			//
			Set<String> keySet = mapR.keySet();
			List<String> keysList = new ArrayList<String>(keySet);
			// 内层,实际上是遍历 单个Map的Key
			Iterator<String> iteratorK = keysList.iterator();
			while (iteratorK.hasNext()) {
				String key = (String) iteratorK.next();
				Object value = mapR.get(key);
				// 
				// 遍历,key加 n
				key = key + "" + cycleNo;
				//
				mapC.put(key, value);
			}
		}
		//
		return mapC;
	}

	private boolean checkSecurity(String sql) {
		if (StringNumberUtil.isEmpty(sql)) {
			return false;
		}
		//
		sql = sql.trim().toLowerCase();
		// 操作类型
		int sqlOperationType = parseSqlOperationType(sql);
		//
		// String regex = "\\w+"; // 需要跨行模式?
		//
		if (SQL_OPERATION_SELECT == sqlOperationType) {
			// 简单校验

		} else if (SQL_OPERATION_UPDATE == sqlOperationType) {
			if (!sql.contains("where")) {
				return false;
			}
		} else if (SQL_OPERATION_INSERT == sqlOperationType) {
		} else if (SQL_OPERATION_DELETE == sqlOperationType) {
			if (!sql.contains("where")) {
				return false;
			}
		} else {
		}
		//
		return true;
	}
	
	public Map<String, Object> executeSQL_OLD(String sql) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringNumberUtil.isEmpty(sql)) {
			return map;
		}
		if (!checkSecurity(sql)) {
			debug("安全检查失败!sql=" + sql);
			return map;
		}
		//
		Map<String, String> sqlMap = wrapSql2Map(sql);
		// 操作类型
		int sqlOperationType = parseSqlOperationType(sql);
		if (SQL_OPERATION_SELECT == sqlOperationType) {
			// 选择类型
			List<Map<String, Object>> resultMapList = sqlMapper.select(sqlMap);
			//
			resultMapList = processClobBlob(resultMapList);
			//
			map.put("select", resultMapList);
		} else if (SQL_OPERATION_UPDATE == sqlOperationType) {
			int rows = sqlMapper.update(sqlMap);
			map.put("update", rows);
		} else if (SQL_OPERATION_INSERT == sqlOperationType) {
			int rows = sqlMapper.insert(sqlMap);
			map.put("insert", rows);
		} else if (SQL_OPERATION_DELETE == sqlOperationType) {
			int rows = sqlMapper.delete(sqlMap);
			map.put("delete", rows);
		} else {
			debug("sql类型不识别:" + sqlOperationType);
		}
		//
		return map;
	}
	

	private Map<String, Object> executeSQL(String textTemplate, Map<String, Object> paramMap, Connection connection) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringNumberUtil.isEmpty(textTemplate)) {
			return map;
		}
		if (!checkSecurity(textTemplate)) {
			debug("安全检查失败!textTemplate=" + textTemplate);
			return map;
		}
		//
		//Map<String, String> sqlMap = wrapSql2Map(textTemplate);
		//
		
		// 操作类型
		int sqlOperationType = parseSqlOperationType(textTemplate);
		if (SQL_OPERATION_SELECT == sqlOperationType) {
				List<Map<String, Object>> resultMapList = SolutionJDBCHelper.executeQuery(connection, textTemplate, paramMap);
				//
				resultMapList = processClobBlob(resultMapList);
				//
				resultMapList = MapUtil.convertKeyList2LowerCase(resultMapList);
				map.put("select", resultMapList);
		} else if (SQL_OPERATION_UPDATE == sqlOperationType) {
				int rows = SolutionJDBCHelper.executeUpdate(connection, textTemplate, paramMap);
				map.put("update", rows);
		} else if (SQL_OPERATION_INSERT == sqlOperationType) {
				int rows = SolutionJDBCHelper.executeUpdate(connection, textTemplate, paramMap);
				map.put("insert", rows);
		} else if (SQL_OPERATION_DELETE == sqlOperationType) {
				int rows = SolutionJDBCHelper.executeUpdate(connection, textTemplate, paramMap);
				map.put("delete", rows);
		} else {
			debug("sql类型不识别:" + sqlOperationType);
		}
		//
		return map;
	}
	
	
	private List<Map<String, Object>> processClobBlob(List<Map<String, Object>> resultMapList) {
		if (null == resultMapList || resultMapList.isEmpty()) {
			return resultMapList;
		}
		//
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 迭代List
		Iterator<Map<String, Object>> iteratorM = resultMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<java.lang.String, java.lang.Object> map = (Map<java.lang.String, java.lang.Object>) iteratorM.next();
			//
			map = processClobBlob(map);
			//
			if (null != map) {
				list.add(map);
			}
		}
		return list;
	}

	private Map<String, Object> processClobBlob(Map<String, Object> map) {
		if (null == map || map.isEmpty()) {
			return map;
		}
		// 迭代Map
		Set<String> keySet = map.keySet();
		List<String> keys = new ArrayList<String>(keySet);
		Iterator<String> iteratorK = keys.iterator();
		//
		Map<String, Object> mapNew = new HashMap<String, Object>();
		mapNew.putAll(map);
		//
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			Object value = map.get(key);
			//
			if (value instanceof Clob) {
				Clob clob = (Clob) value;
				debug("探测到 clob: key="+key+";value="+clob);
				//
				try {
					int len = (int) clob.length();
					String clobStr = clob.getSubString(1L, len);
					//
					mapNew.put(key, clobStr);
					debug("将 clob.getSubString转换为 String; clobStr.length()="+clobStr.length());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//
			} else if(value instanceof Blob){
				//
				Blob blob = (Blob) value;
				debug("探测到 blob: key="+key+";value="+blob);
				//
				int len;
				try {
					len = (int) blob.length();
					byte[] bytes = blob.getBytes(1L, len);
					//
					mapNew.put(key, bytes);
					debug("将 blob.getBytes转换为 byte[]; length="+ bytes.length);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//
		return mapNew;
	}

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[8*1024];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	public Map<String, Object> generateResponseTemMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		//
		map.put("_status", 0);
		map.put("_message", "执行出错");
		map.put("root", null);
		map.put("options", null);
		// 如果有其他信息
		return map;
	}

	/**
	 * 封装的常规变量-值
	 * 
	 * @param user
	 * @return
	 */
	public static Map<String, Object> wrapCommonParams(SystemUser user) {
		// 需要封装的常规变量-值
		// 可以根据需要增加,但必须以下划线开头,而前端传回的参数,不允许以下划线开头.
		SimpleDateFormat timeFmt_yMdHmsS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//
		String userid = user.getUserid();
		String username = user.getName();
		String orgid = user.getOrgid();
		String prisonid = user.getDepartid();
		String departid = user.getDepartid();
		Date now = new Date();
		String timestampstr = timeFmt_yMdHmsS.format(now);
		
		//
		Map<String, Object> commonParamMap = new HashMap<String, Object>();
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		//
		commonParamMap.put("_userid", userid); // 用户ID
		commonParamMap.put("_username", username); // 用户名
		commonParamMap.put("_orgid", orgid); // 用户所在单位ID
		commonParamMap.put("_prisonid", prisonid); // 用户所在监狱ID
		commonParamMap.put("_departid", departid); // 用户所在单位ID
		commonParamMap.put("_now", now); // 当前日期时间,类型Date
		commonParamMap.put("_timestampstr", timestampstr);// 当前时间戳字符串
		commonParamMap.put("_provinceid", provincecode);

		//
		return commonParamMap;
	}

	public static Map<String, Object> wrapCommonParams(SystemUser user, Map<String, Object> params) {
		//
		Map<String, Object> commonParamMap = wrapCommonParams(user);
		// 合并参数Map
		commonParamMap = mergeMap(commonParamMap, params);
		//
		return commonParamMap;
	}

	/**
	 * 合并Map
	 * 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static Map<String, Object> mergeMap(Map<String, Object> map1, Map<String, Object> map2) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//
		if (null != map1) {
			resultMap.putAll(map1);
		}
		if (null != map2) {
			resultMap.putAll(map2);
		}
		//
		return resultMap;
	}

	/**
	 * 包装SQL text为Map
	 * 
	 * @param sql
	 * @return
	 */
	public static Map<String, String> wrapSql2Map(String sql) {
		Map<String, String> sqlMap = new HashMap<String, String>();
		sqlMap.put("sql", sql);
		return sqlMap;
	}

	/**
	 * 解析SQL操作的类型，判断sql语句是哪种类型（select、insert、update、delete），如果都不是往回ERROR
	 * 
	 * @param sqlText
	 * @return
	 */
	public static int parseSqlOperationType(String sqlText) {
		//
		if (null == sqlText) {
			return SQL_OPERATION_ERROR;
		}
		sqlText = sqlText.trim();
		// 最多拆分出2个单元;这里只要第一个即可判断
		String[] tokenizes = sqlText.split("\\s", 2);
		//
		if (null == tokenizes || tokenizes.length < 2) {
			return SQL_OPERATION_ERROR;
		}
		// 获取第一个
		String SQLoperator = tokenizes[0];
		SQLoperator = SQLoperator.trim();
		//
		if (SQLoperator.equalsIgnoreCase("select")) {
			return SQL_OPERATION_SELECT;
		} else if (SQLoperator.equalsIgnoreCase("insert")) {
			return SQL_OPERATION_INSERT;
		} else if (SQLoperator.equalsIgnoreCase("update")) {
			return SQL_OPERATION_UPDATE;
		} else if (SQLoperator.equalsIgnoreCase("delete")) {
			return SQL_OPERATION_DELETE;
		} else {
			//
			return SQL_OPERATION_ERROR;
		}
	}
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			// 获取 Factory
			SqlSessionFactory sessionFactory = factoryBean.getObject();
			// SqlSession的实例不能共享使用
			SqlSession session = sessionFactory.openSession();
			// 不准泄露以及共享这个 conn
			// 只能在特殊方法内部使用
			conn = session.getConnection();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}
	
//
//	private void closeConnection(Connection conn) {
//		SolutionJDBCHelper.close(conn);
//	}

}
