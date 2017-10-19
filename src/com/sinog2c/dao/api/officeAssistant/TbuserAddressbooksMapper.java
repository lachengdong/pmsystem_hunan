package com.sinog2c.dao.api.officeAssistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.sinog2c.model.officeAssistant.TbuserAddressbooks;

/**
 * 通讯簿-联系人
 * 
 * @author kexz 2014-7-17 11:20:20
 */
@Component("tbuserAddressbooksMapper")
public interface TbuserAddressbooksMapper {
	/**
	 * 新增联系人
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	int addPerson(TbuserAddressbooks record);

	/**
	 * 新增联系人（非空字段）
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	int insertSelective(TbuserAddressbooks record);

	/**
	 * 通过联系人id删除联系人
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	int deletePersonByLinManid(String LinkManId);
	/**
	 * 方法描述：查询 当前用户是否可以编辑 公共通讯薄
	 * @param map
	 * @return
	 */
    List whetherPermissions(Map map);
	/**
	 * 通过分组id删除对应组的联系人（删除分组时要删除此组的所有人）
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	int deletePersonByGroupId(String groupId);

	/**
	 * 更新联系人
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	int updatePerson(TbuserAddressbooks record);

	/**
	 * 查询公共通讯簿和当前用户的个人通讯簿中的联系人
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	List<HashMap> selectAll(List groupIds);

	/**
	 * 查询联系人详细信息
	 * 
	 * @author kexz 2014-7-17 11:20:20
	 */
	HashMap selectPersonByLinkManId(String LinkManId);
}