package com.sinog2c.dao.api.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


/**
 * 通用SQL映射器. <br/>
 * 注意: 请勿随意修改此类,需要修改必须与上级领导协商.
 * @since 2014-11-08
 * @author 任富飞
 */
@Repository
public interface CommonSQLMapper {

	/**
	 * 传入 insert 语句SQL,返回值为影响的行数
	 * @param sql 参数为Map,其中key必须为 "sql", 对应的value为 "insert ...."
	 * @return
	 */
	public int insert(Map<String,String> sql);

	/**
	 * 传入 delete 语句SQL,返回值为影响的行数
	 * @param sql 参数为Map,其中key必须为 "sql", 对应的value为 "delete ...."
	 * @return
	 */
	public int delete(Map<String,String> sql);
	
	/**
	 * 传入 select 语句SQL,返回值为影响的行数
	 * @param sql 参数为Map,其中key必须为 "sql", 对应的value为 "update ...."
	 * @return
	 */
	public int update(Map<String,String> sql);

	/**
	 * 传入 select 语句SQL,返回对应的Map-List
	 * @param sql 参数为Map,其中key必须为 "sql", 对应的value为 "select ...."
	 * @return
	 */
	public List<Map<String, Object>> select(Map<String,String> sql);
	/**
	 * 传入 调用存储过程SQL
	 * @param sql 参数为Map,其中key必须为 "sql", 对应的value为 "call ...."
	 * @return
	 */
	public void call(Map<String,String> sql);
	
	
	public Map<String, Object> getTableColumn(Map<String, Object> map);
	
	public void alterTableRenameColumn(Map<String, Object> map);
	
	public void alterTableAddColumn(Map<String, Object> map);
	
	public void alterTableModifyColumn(Map<String, Object> map);
	
	public void alterTableDropColumn(Map<String, Object> map);
	
}
