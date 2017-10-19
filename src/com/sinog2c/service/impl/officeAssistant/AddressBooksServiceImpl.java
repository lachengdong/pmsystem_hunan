package com.sinog2c.service.impl.officeAssistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.officeAssistant.TbuserAddressbooksGroupMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserAddressbooksMapper;
import com.sinog2c.model.officeAssistant.TbuserAddressbooks;
import com.sinog2c.model.officeAssistant.TbuserAddressbooksGroup;
import com.sinog2c.service.api.officeAssistant.AddressbooksService;
import com.sinog2c.service.impl.base.ServiceImplBase;

/**
 * 通讯簿
 * 
 * @author kexz 2014-7-17 10:51:48
 */

@Service("addressbooksService")
public class AddressBooksServiceImpl extends ServiceImplBase implements AddressbooksService {

	@Autowired
	private TbuserAddressbooksGroupMapper tbuserAddressbooksGroupMapper;
	@Autowired
	private TbuserAddressbooksMapper tbuserAddressbooksMapper;

	/**
	 * 新增分组
	 * 
	 * @author kexz 2014-7-17 11:30:16
	 */
	@Override
	public int addGroup(TbuserAddressbooksGroup record) {
		return tbuserAddressbooksGroupMapper.addGroup(record);
	}

	/**
	 * 删除分组
	 * 
	 * @author kexz 2014-7-17 11:31:08
	 */
	@Override
	public int deleteGroup(String groupId) {
		return tbuserAddressbooksGroupMapper.deleteGroup(groupId);
	}

	/**
	 * 修改分组
	 * 
	 * @author kexz 2014-7-17 11:32:13
	 */
	@Override
	public int updateGroup(TbuserAddressbooksGroup record) {
		return tbuserAddressbooksGroupMapper.updateGroup(record);
	}

	/**
	 * 查询所有分组
	 * 
	 * @author kexz 2014-7-17 10:53:43
	 */
	@Override
	public List<HashMap> ajaxPersionAll(String userId) {
		return tbuserAddressbooksGroupMapper.selectAll(userId);
	}

	/**
	 * 根据groupid查询分组信息
	 * 
	 * @author kexz 2014-7-17 11:33:52
	 */
	@Override
	public HashMap ajaxPersionGroupAllByGroupid(String groupid) {
		return tbuserAddressbooksGroupMapper.selectByGroupid(groupid);
	}

	/**
	 * 新增联系人
	 * 
	 * @author kexz 2014-7-17 11:34:32
	 */
	@Override
	public int addPerson(TbuserAddressbooks record) {
		return tbuserAddressbooksMapper.addPerson(record);
	}

	/**
	 * 删除联系人（根据联系人id单条删除）
	 * 
	 * @author kexz 2014-7-17 11:35:31
	 */
	@Override
	public int deletePersonByLinManid(String LinkManId) {
		return tbuserAddressbooksMapper.deletePersonByLinManid(LinkManId);
	}
    /**
     * 判断是否有权限操作
     */
	public List<Map> whetherPermissions(Map map){
		return tbuserAddressbooksMapper.whetherPermissions(map);
	}
	/**
	 * 删除联系人（根据分组id成组删除）
	 * 
	 * @author kexz 2014-7-17 11:36:58
	 */
	@Override
	public int deletePersonByGroupId(String id) {
		return tbuserAddressbooksMapper.deletePersonByGroupId(id);
	}

	/**
	 * 修改联系人
	 * 
	 * @author kexz 2014-7-17 11:40:51
	 */
	@Override
	public int updatePerson(TbuserAddressbooks record) {
		return tbuserAddressbooksMapper.updatePerson(record);
	}

	/**
	 *通讯簿 查询所有联系人
	 * 
	 * @author kexz 2014-7-17 11:37:55
	 */
	@Override
	public List<HashMap> ajaxPersion(List groupIds) {
		return tbuserAddressbooksMapper.selectAll(groupIds);
	}

	/**
	 * 根据联系人Id查询联系人详细信息
	 * 
	 * @author kexz 2014-7-10
	 */
	@Override
	public HashMap ajaxPersionByLinkManId(String LinkManId) {
		return tbuserAddressbooksMapper.selectPersonByLinkManId(LinkManId);
	}

	/**
	 * 根据userid查询下拉列表中显示的分组
	 * 
	 * @author kexz 2014-7-21
	 */
	@Override
	public List<HashMap> ajaxGroupByUserId(String userid) {
		return tbuserAddressbooksGroupMapper.selectByUserid(userid);
	}
}
