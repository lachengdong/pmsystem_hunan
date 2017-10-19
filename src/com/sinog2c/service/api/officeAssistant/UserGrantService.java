package com.sinog2c.service.api.officeAssistant;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.user.UserGrantDetail;

/**
 * 用户授权服务
 */
public interface UserGrantService {

	/**
	 * 添加授权记录以及授权信息
	 * @param notice 授权记录,内部含有授权详细信息
	 * @param operator 操作员
	 */
	public int add(TbuserNotice notice, SystemUser operator);

	/**
	 * 修改授权记录以及授权信息
	 * @param notice 授权记录,内部含有授权详细信息
	 * @param operator 操作员
	 * @return
	 */
	public int update(TbuserNotice notice, SystemUser operator);

	/**
	 * 删除授权记录以及授权信息
	 * @param noticeid 授权记录ID
	 * @param operator 操作员
	 * @return
	 */
	public int delete(int noticeid, SystemUser operator);

	/**
	 * 根据用户取得所有授权给别人的记录
	 * @param operator
	 * @param pageIndex 页码,0开始
	 * @param pageSize 每页数量
	 * @return
	 */
	public List<TbuserNotice> selectGrantList(int type, String userid, String key, int pageIndex, int pageSize, String sortField, String sortOrder);
	public int selectGrantCount(int type, String userid, String key);

	/**
	 * 方法描述：根据当前用户查询出被授 权限内容
	 * @param operator
	 * @param pageIndex 页码,0开始
	 * @param pageSize 每页数量
	 * @return
	 */
	public List<TbuserNotice> selectGrantList_bs(int type, String userid, String key, int pageIndex, int pageSize, String sortField, String sortOrder);
	public int selectGrantCount_bs(int type, String userid, String key);

	/**
	 * 根据用户取得授权给 userid的记录
	 * @param userid 被授权的用户ID
	 * @param operator 授权的用户
	 * @return
	 */
	public TbuserNotice getByUseridAndOpid(String userid, SystemUser operator);
	/**
	 * 根据通知ID获取
	 * @param noticeid
	 * @return
	 */
	public TbuserNotice getByNoticeid(int noticeid, SystemUser operator);
	/**
	 * 根据用户取得授权给 userid的Grant记录
	 * @param userid
	 * @param operator
	 * @return
	 */
	public List<UserGrantDetail> listGrantByUseridAndOpid(String userid, SystemUser operator,int noticeid);
	
	/**
	 * 方法描述：查询被授权用户已经被当前用户 授过并且未过期的权限
	 * @author:mushuhong
	 * @version:2015年1月14日17:17:19
	 */
	public String checkXZUserBSGrant(HttpServletRequest request,SystemUser user);
	
	/**
	 * 方法描述：查询出 权限 的 顺序进行组织
	 * @author :mushuhong
	 * @version:2015年1月15日15:11:24
	 */
	
	public String  queryMinGrantByNoticeids(Map map);
	
	/**
	 * 方法描述：修改的时候，不能添加已经有的权限(出去手动授权的权限)
	 * @author:mushuhong
	 * @version:2015年1月14日17:17:19
	 */
	public String checkXZUserGrantids(HttpServletRequest request,SystemUser user);
}