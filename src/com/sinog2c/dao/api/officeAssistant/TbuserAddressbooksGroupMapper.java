package com.sinog2c.dao.api.officeAssistant;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sinog2c.model.officeAssistant.TbuserAddressbooksGroup;

/**
 * 联系人分组
 * 
 * @author kexz 2014-7-17 10:38:46
 */
@Component("tbuserAddressbooksGroupMapper")
public interface TbuserAddressbooksGroupMapper {
	/**
	 * 添加分组（非空插入）
	 * 
	 * @author kexz 2014-7-17 10:38:46
	 */
	int insertSelective(TbuserAddressbooksGroup record);

	/**
	 * 添加分组
	 * 
	 * @author kexz 2014-7-17 10:38:46
	 */
	int addGroup(TbuserAddressbooksGroup record);

	/**
	 * 根据分组编号更新分组信息
	 * 
	 * @author kexz 2014-7-17 10:38:46
	 */
	int updateGroup(TbuserAddressbooksGroup record);

	/**
	 * 根据分组编号删除分组信息
	 * 
	 * @author kexz 2014-7-17 10:38:46
	 */
	int deleteGroup(String groupid);

	/**
	 * 根据分组编号查询分组信息
	 * 
	 * @author kexz 2014-7-17 10:38:46
	 */
	HashMap selectByGroupid(String groupid);

	/**
	 * 查询公共通讯簿及当前用户的个人通讯簿
	 * 
	 * @author kexz 2014-7-17 10:38:46
	 */
	List<HashMap> selectAll(String userid);
	/**
	 * 根据userid查询下拉列表中显示的分组
	 * 
	 * @author kexz 2014-7-21 10:38:46
	 */
	List<HashMap> selectByUserid(String uesrid); 
}