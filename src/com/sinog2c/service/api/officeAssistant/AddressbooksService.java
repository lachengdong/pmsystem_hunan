package com.sinog2c.service.api.officeAssistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.officeAssistant.TbuserAddressbooks;
import com.sinog2c.model.officeAssistant.TbuserAddressbooksGroup;

/**
 * 通讯簿
 * 
 * @author kexz 2014-7-17 10:54:32
 */
public interface AddressbooksService {
	/**
	 * 新增分组
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int addGroup(TbuserAddressbooksGroup record);

	/**
	 * 删除分组
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int deleteGroup(String gropuId);

	/**
	 * 更新分组
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int updateGroup(TbuserAddressbooksGroup record);

	/**
	 * 查询公共通讯簿和当前用户的个人通讯簿
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public List<HashMap> ajaxPersionAll(String userId);

	/**
	 * 查询分组
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public HashMap ajaxPersionGroupAllByGroupid(String groupid);

	/**
	 * 新增联系人
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int addPerson(TbuserAddressbooks record);

	/**
	 * 通过联系人id删除联系人（单个删除联系人）
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int deletePersonByLinManid(String LinkManId);
	
	
	/**
	 * 方法描述：判断用户是否有权限
	 */
	public List whetherPermissions(Map map);

	/**
	 * 通过分组id删除联系人（删除分组时需要删除该组下的所有联系人）
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int deletePersonByGroupId(String id);

	/**
	 * 更新联系人信息
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public int updatePerson(TbuserAddressbooks record);

	/**
	 * 查询当前用户可见联系人信息
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public List<HashMap> ajaxPersion(List groupIds);

	/**
	 * 查询联系人详细信息
	 * 
	 * @author kexz 2014-7-17 16:07:53
	 */
	public HashMap ajaxPersionByLinkManId(String LinkManId);
	/**
	 * 根据userid查询下拉列表中的分组显示
	 * 
	 * @author kexz 2014-7-21 
	 */
	public List<HashMap> ajaxGroupByUserId(String userid);
}
