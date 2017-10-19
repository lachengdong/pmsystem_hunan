package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.system.SystemOrganizationMapper;
import com.sinog2c.dao.api.system.SystemUserMapper;
import com.sinog2c.dao.api.system.UserOrgWrapperMapper;
import com.sinog2c.dao.api.system.UserRoleWrapperMapper;
import com.sinog2c.dao.api.user.UserCertificateMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserOrgWrapper;
import com.sinog2c.model.system.UserRoleWrapper;
import com.sinog2c.model.user.UserCertificate;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.PasswordUtil;

@Service("systemUserService")
public class SystemUserServiceImpl extends ServiceImplBase implements SystemUserService{


	@Autowired
    private SystemUserMapper systemUserMapper;
	@Autowired
    private SystemUserMapper userQianShouMapper;
	@Autowired
	private UserRoleWrapperMapper userRoleWrapperMapper;
	@Autowired
	private UserOrgWrapperMapper userOrgWrapperMapper;
	@Autowired
	private SystemOrganizationMapper organizationMapper;
	@Autowired
	private UserCertificateMapper certificateMapper;
	
	@Override
	@Transactional
    public int addUser(SystemUser user, SystemUser operator) {
    	//
    	// 操作员不为空
    	if(null == user || null == operator){
    		return -1;
    	}
    	String userid = user.getUserid();
    	// 如果存在组织机构信息
    	SystemOrganization organization = user.getOrganization();
    	UserOrgWrapper wrapper = null;
    	if(null != organization){
    		wrapper = new UserOrgWrapper();
    		wrapper.setOrgid(organization.getOrgid());
    		wrapper.setUserid(userid);
    	}
		String opid = operator.getUserid();
		//String opid = user.getOpid();
		//
		if(null == opid || opid.length() < 1){
		} else {
			// 设置操作员ID
			user.setOpid(opid);
	    	if(null != wrapper){
	    		wrapper.setOpid(opid);
	    	}
		}
    	// 设置 departid
    	SystemOrganization department = operator.getOrganization();
    	if(null != department){
    		String departid = null;
    		int isadmin = user.getInt1();
    		if(1 == isadmin){
        		// 是管理员, 设置为用户自身的单位
    			departid = user.getOrgid();
    		}
    		//
    		if(null == departid){
        		departid = department.getOrgid();
    		}
    		//
        	// 不是管理员
        	user.setDepartid(departid);
    	}
    	// 增加用户的组织机构信息
    	if(null != wrapper){
    		//System.out.println("wrapper.getOpid()="+wrapper.getOpid());
    		// 修正多条记录的问题?
    		// 在删除用户时处理
    		//
    		userOrgWrapperMapper.insertSelective(wrapper);
    	}
    	// 增加用户
    	int count = systemUserMapper.insertSelective(user);
    	//
        return count;
    }
	
	@Override
	@Transactional
	public int updateSelfInfo(SystemUser user, SystemUser operator) {
		if(null == user){
			return -1;
		}
		if(null != operator){
			String userid = user.getUserid();
			String operatorid = operator.getUserid();
			if(false == userid.equals( operatorid )){
				return -1;
			}
		}
		//
		return this.systemUserMapper.updateSelfInfo(user);
	}
	@Override
	@Transactional
	public int updateUserInfo(SystemUser user, SystemUser operator) {
    	// 操作员不为空
    	if(null == user || null == operator){
    		return -1;
    	}
    	String userid = user.getUserid();
    	// 如果存在组织机构信息
    	SystemOrganization organization = user.getOrganization();
    	UserOrgWrapper wrapper = null;
    	if(null != organization){
    		wrapper = new UserOrgWrapper();
    		wrapper.setOrgid(organization.getOrgid());
    		wrapper.setUserid(userid);
    	}
		String opid = operator.getUserid();
		//
		if(null == opid || opid.length() < 1){
		} else {
			// 设置操作员ID
			user.setOpid(opid);
	    	if(null != wrapper){
	    		wrapper.setOpid(opid);
	    	}
		}
    	// 设置 departid
    	SystemOrganization department = operator.getOrganization();
    	if(null != department){
    		String departid = user.getDepartid();
    		Integer isadmin = user.getInt1();
    		if(null == isadmin){
    			// do nothing
    		} else if(1 == isadmin){
        		// 是管理员, 设置为用户自身的单位
    			departid = user.getOrgid();
    		} else{
            	// 不是管理员,不修改
        		// departid = department.getOrgid();
    		}
    		//
        	user.setDepartid(departid);
    	}
		// 如果存在组织机构信息
    	// 修改用户的组织机构信息
    	if(null != wrapper){
    		//System.out.println("wrapper.getOpid()="+wrapper.getOpid());
    		// userOrgWrapperMapper.insertSelective(wrapper);
    		// TODO 先不修改用户的部门
    	}
		//
		return this.systemUserMapper.updateUserInfo(user);
	}
	
	@Override
	@Transactional
	public int deleteToHistory(SystemUser user, SystemUser operator) {
		// 0. 因为删除用户不经常使用,所以不需要在乎效率
		String userid = user.getUserid();
		// 1. 更新数据
		int uprow = updateUserInfo(user, operator);
		if(uprow > 1){
			throw new RuntimeException("删除失败,uprow="+uprow);
		} else if(uprow < 1){
			return uprow;
		}
		// 2. 拷贝到历史记录
		int copyrow = this.systemUserMapper.copyToHistory(user);
		if(copyrow > 1){
			throw new RuntimeException("删除失败,copyrow="+copyrow);
		} else if(copyrow < 1){
			return copyrow;
		}
		// 3. 删除用户记录
		int delrow = this.systemUserMapper.deleteOnly(user);
		if(delrow > 1){
			throw new RuntimeException("删除失败,delrow="+delrow);
		} else if(delrow < 1){
			return delrow;
		}
		// 4. 删除对应的授权记录. 部门信息. 角色信息
		UserOrgWrapper wrapper = new UserOrgWrapper();
		wrapper.setUserid(userid);
		userOrgWrapperMapper.delete(wrapper);
		//删除用户对应角色信息
		userOrgWrapperMapper.deleterole(wrapper);
		return delrow;
	}
	
	@Override
	@Transactional
	public int bindUserCert(UserCertificate cert, SystemUser user) {
		if(null == user || null==cert){
			return -1;
		}
		// 设置信息
		String userid = user.getUserid();
		cert.setUserid(userid);
		cert.setOpid(userid);
		// 1. 先查询旧有证书.
		// 2. 删除旧有证书
		// 3. 添加新证书
		// 4. 预留一个BUG，即一个用户可以绑定多个UKey. 有钱就行 ^_^
		
		// 1.
		String certsn = cert.getCertsn();
		UserCertificate oldCert = getCertByCertsn(certsn);
		if(null != oldCert){
			// 2.
			certificateMapper.delete(certsn);
		}
		// 3.
		int row = certificateMapper.insertSelective(cert);
		
		//
		return row;
	}

	@Override
    public SystemUser getByUserId(String userId){
    	if(null == userId){
    		return null;
    	}
    	SystemUser systemUser = systemUserMapper.getByUserId(userId);
    	if(null != systemUser){
    		// TODO 单条数据,先执行2个SQL，等待后期优化
    		SystemOrganization organization = organizationMapper.getByUserid(userId);
    		systemUser.setOrganization(organization);
    		//
    		SystemOrganization prison = retrievePrison(organization, 0);
			
			// 设置监狱,如果有
			systemUser.setPrison(prison);
			//用户的角色
			List<UserRoleWrapper> lstRoles = userRoleWrapperMapper.getAllByUserid(userId);
			systemUser.setRolelist(lstRoles);
			
			List<String> lstRemark = systemUserMapper.getCurrentUserCourtRole(userId);
			systemUser.setRoleremarklist(lstRemark);
    	}
    	//
    	return systemUser;
    }
	// 理论上，从数据库获取监狱信息
	private SystemOrganization retrievePrison(SystemOrganization organization, int dep){
		
		// 拦截
		if(null == organization){
			return null;
		}
		// 本来应该使用SQL获取的。。。。。。。。
		// 判断传入的组织机构级别
		String unitlevelO = organization.getUnitlevel();

		// 监狱级别,直接返回
		String nameO = organization.getName();
		if(null == nameO){ nameO = "";}
		if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelO)){
			// 为了保险,以监狱结尾的单位
			if(nameO.trim().endsWith("监狱")){
				return organization;
			}
		}
		
		//  司法部,省局,高院,中院、高检察院、中检察院、公安
		if(SystemOrganization.UNITLEVEL_SIFABU.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_PROVINCE.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_H_COURT.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_M_COURT.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_H_PROC.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_M_PROC.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_PublicSecurity.equals(unitlevelO)
			){
			//  如果是检察院/省局/司法部/之类/则返回 null
			return null;
		}
		
		// 拦截 分监区、监区、其他
		// 如果是监区,分监区之类,则最多向上遍历3次,获取
		if( SystemOrganization.UNITLEVEL_P_AREA.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_OTHER.equals(unitlevelO)
				|| SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelO)
			){
			// 获取父元素
			String porgid = organization.getPorgid();
			//
			SystemOrganization parent = organizationMapper.getByOrgId(porgid);
			//
			SystemOrganization prison = retrievePrison(parent, dep++);
			if(null != prison) {
				return prison;
			}
		}
		
		//
		
		// 二次判断 监狱级别
		if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelO)){
			return organization;
		}
		return null;
	}
	

	@Override
    public UserCertificate getCertByUserId(String userid){
    	List<UserCertificate> result = listCertByUserId(userid);
    	//
    	if(null != result && !result.isEmpty()){
    		//
    		Iterator<UserCertificate> iteratorC = result.iterator();
    		while (iteratorC.hasNext()) {
				UserCertificate certificate = (UserCertificate) iteratorC.next();
				if(null != certificate){
					return certificate;
				}
			}
    	}
    	//
    	return null;
    }
	@Override
	public List<UserCertificate> listCertByUserId(String userid){
		if(null == userid) {
			return null;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		List<UserCertificate> result = certificateMapper.listByUserId(map);
		//
		return result;
	}
	
	@Override
	public UserCertificate getCertByCertsn(String certsn) {
		List<UserCertificate> certificateList = certificateMapper.getByCertsn(certsn);
		if(null != certificateList && certificateList.size() > 0){
			return certificateList.get(0);
		}
		return null;
	}
	
	@Override
	public SystemUser getUserByCert(String certsn, String certdata) {
		if(null == certsn){
			return null;
		}
		//
		UserCertificate certificate = getCertByCertsn(certsn);
		if(null != certificate){
			String userId = certificate.getUserid();
			String certsnEx = certificate.getCertsn();
			String certdataEx = certificate.getCertsn();
			// 相等
			if(certsn.equalsIgnoreCase(certsnEx) && null != certdata && certdata.equalsIgnoreCase(certdataEx)){
				// 认证成功,去获取用户
			} else {
				//return null; // XXX 暂时不处理
			}
			//
			if(null != userId){
				SystemUser user = getByUserId(userId);
				//
				return user;
			}
		}
		return null;
	}
	
	@Override
    public int countAll(){
    	return this.systemUserMapper.countAll();
    }
	@Override
	public List<SystemUser> getAllByPage(int pageIndex,	int pageSize){
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		List<SystemUser> list =  systemUserMapper.getAllByPage(start, end);
		return list;
	}
	
	@Override
	public int countBySearch(String key) {
		if(null == key){
			key = "";
		}
    	return systemUserMapper.countBySearch(key);
	}
	
	@Override
	public List<SystemUser> searchByPage(String key, int pageIndex, int pageSize) {
		if(null == key){
			key = "";
		}
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		

		List<SystemUser> list =  systemUserMapper.searchByPage(key, start, end);
		return list;
	}
	
	@Override
	public int countByOrg(SystemOrganization org) {
		if(null == org){
			return 0;
		}
		String orgid = org.getOrgid();
		int count = systemUserMapper.countByOrgid(orgid);
		return count;
	}
	
	@Override
	public List<SystemUser> getPageByOrg(SystemOrganization org, int pageIndex, int pageSize) {
		if(null == org){
			return null;
		}
		String orgid = org.getOrgid();
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		

		List<SystemUser> list =  null;
		if(null != orgid){
			list = systemUserMapper.getPageByOrgid(orgid, start, end);
			// 这里有问题,部门不对
			//org = organizationMapper.getByOrgId(orgid);
		} else {
			list = new ArrayList<SystemUser>();
		}
		Iterator<SystemUser> iteratorU = list.iterator();
		while (iteratorU.hasNext()) {
			SystemUser systemUser = (SystemUser) iteratorU.next();
			//
			systemUser.setPassword(null);
		}
		//
		return list;
	}
	
	
	@Override
	public int countByOrgKey(SystemOrganization org, String key) {
		if(null == org){
			return 0;
		}
		String orgid = org.getOrgid();
		int count = systemUserMapper.countByOrgidAndKey(orgid, key);
		return count;
	}
	
	@Override
	public List<SystemUser> getPageByOrgKey(SystemOrganization org, String key, int pageIndex, int pageSize) {
		if(null == org){
			return null;
		}
		String orgid = org.getOrgid();
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		

		List<SystemUser> list =  systemUserMapper.getPageByOrgidAndKey(orgid, key, start, end);
		return list;
	}
	
	@Override
	public int countByOrgs(List<SystemOrganization> orgs) {
		if(null == orgs || orgs.isEmpty()){
			return 0;
		}
		String orgids = "";
		Iterator<SystemOrganization> iteratorO = orgs.iterator();
		//
		int i = 0;
		while ( iteratorO.hasNext()) {
			SystemOrganization org = (SystemOrganization) iteratorO.next();
			if(null == org){
				continue;
			}
			String orgid = org.getOrgid();
			if(null == orgid || orgid.trim().length() < 1){
				continue;
			}
			if(0 != i){
				orgids += ",";
			}
			orgids += orgid;
			i = i+1;
		}
		if(null == orgids || orgids.trim().length() < 1){
			return 0;
		}
		int count = systemUserMapper.countByOrgids(orgids);
		return count;
	}
	
	
	@Override
	public List<SystemUser> getPageByOrgs(List<SystemOrganization> orgs, int pageIndex, int pageSize) {

		if(null == orgs || orgs.isEmpty()){
			return null;
		}
		String orgids = "";
		Iterator<SystemOrganization> iteratorO = orgs.iterator();
		//
		int i = 0;
		while ( iteratorO.hasNext()) {
			SystemOrganization org = (SystemOrganization) iteratorO.next();
			if(null == org){
				continue;
			}
			String orgid = org.getOrgid();
			if(null == orgid || orgid.trim().length() < 1){
				continue;
			}
			if(0 != i){
				orgids += ",";
			}
			orgids += orgid;
			i = i+1;
		}
		if(null == orgids || orgids.trim().length() < 1){
			return null;
		}

		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		//
		List<SystemUser> list =  systemUserMapper.getPageByOrgids(orgids, start, end);
		return list;
	}
	
	@Override
	@Transactional
	public boolean updateUserOrg(UserOrgWrapper userOrgWrapper, SystemUser operator) {
		//
		if(null == userOrgWrapper){
			return false;
		}
		//
		String userid = userOrgWrapper.getUserid();
		String orgid = userOrgWrapper.getOrgid();
		if(null == userid || null == orgid){
			return false;
		}
		//
		userid = userid.trim();
		orgid = orgid.trim();
		//
		List<UserOrgWrapper> list = userOrgWrapperMapper.getAllByUserid(userid);
		Iterator<UserOrgWrapper> iteratorW = list.iterator();
		boolean exists = false;
		while (iteratorW.hasNext()) {
			UserOrgWrapper nwrapper = (UserOrgWrapper) iteratorW.next();
			if(null == nwrapper){
				continue;
			}
			//
			String norgid = nwrapper.getOrgid();
			if(null == norgid){
				continue;
			}
			// 已存在
			if(norgid.trim().equals(orgid)){
				exists = true;
				continue;
			} else {
				// 删除旧的
				userOrgWrapperMapper.delete(nwrapper);
			}
		}
		if(!exists){
			// 新增
			userOrgWrapperMapper.insertSelective(userOrgWrapper);
		}
		return false;
	}
	
	@Override
	@Transactional
	public List<SystemUser> getUsersByOrgid(Map<String,Object> map) {
		return systemUserMapper.getUsersByOrgid(map);
	}
	
	@Override
	public List<Map> getUsersByDepartid(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(systemUserMapper.getUsersByDepartid(map));
	}
	
	@Override
	public List<Map> getCurrDepartUsersByDepartid(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(systemUserMapper.getCurrDepartUsersByDepartid(map));
	}
	
	@Override
	@Transactional
	public List<SystemUser> getNextShenPiRenList(String orgid,String userid) {
		List<String> list = systemUserMapper.getCurrentUserCourtRole(userid);//获取当前用户的角色
		String temprole = null;
		if(list!=null){
			for(int i=0,n=list.size(); i<n; i++){
				String s = list.get(i);
				if(i==0){
					if("gyspy".equals(s)){//高院审判员
						temprole = " r.remark = 'gyspz' ";
						continue;
					}else if("gyspz".equals(s)){//高院审判长
						temprole = " r.remark = 'gyftz' ";
						continue;
					}else if("gyftz".equals(s)){//高院副厅长
						temprole = " r.remark = 'gyztz' ";
						continue;
					}else if("gyztz".equals(s)){//高院正厅长
						temprole = " r.remark = 'gyfyz' ";
						continue;
					}else if("gyfyz".equals(s)){//高院副院长
						temprole = " r.remark = 'gyzyz' ";
						continue;
					}else{
						temprole = " 1!=1 ";
						continue;
					}
				}else{
					if("gyspy".equals(s)){//高院审判员
						temprole += " or r.remark = 'gyspz' ";
						continue;
					}else if("gyspz".equals(s)){//高院审判长
						temprole += " or r.remark = 'gyftz' ";
						continue;
					}else if("gyftz".equals(s)){//高院副厅长
						temprole += " or r.remark = 'gyztz' ";
						continue;
					}else if("gyztz".equals(s)){//高院正厅长
						temprole += " or r.remark = 'gyfyz' ";
						continue;
					}else if("gyfyz".equals(s)){//高院副院长
						temprole += " or r.remark = 'gyzyz' ";
						continue;
					}else{
						continue;
					}
				}
			}
		}
		return systemUserMapper.getNextShenPiRenList(orgid,temprole);
	}
	
	//start add by blue_lv 2015-07-14
	@Override
	public JSONMessage<SystemUser> getPageByOrgidAndKey2(Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());

		if (rowCount < 0) {
			rowCount = this.systemUserMapper.getcountbyOrgidAndKey(map);
		}
		List<SystemUser> list = this.systemUserMapper
				.getPageByOrgidAndKey2(map);
		return new JSONMessage<SystemUser>(list, rowCount);
	}
	
	@Override
	public int updateByPrimaryKeySelective(SystemUser record) {
		return this.systemUserMapper.updateByPrimaryKeySelective(record);
	}
	//end add by blue_lv 2015-07-14
	
	@Override
	public List<Map<String, Object>> getKeYuanList(Map<String, Object> map) {
		return systemUserMapper.getKeYuanList(map);
	}
	/*
	 * 核对关机密码
	 */
	public Object getUserPassword(Map<String, Object> paramMap){
		boolean loginSuccess = false;// 是否登录成功
		Object returnval = GkzxCommon.RESULT_SUCCESS;
		String password = (String)paramMap.get("password");
		String usepassword = GkzxCommon.NULL,localhostip = GkzxCommon.NULL;
		
		try {  
			/*输入的关机密码
			 *正确时调关机命令 Runtime.getRuntime().exec("shutdown -s");
			 *不正确时返回值前台提示密码错误 GkzxCommon.RESULT_ERROR
			*/
			String ywgk_debug = (String)paramMap.get("ywgk_debug");
			localhostip = (String)paramMap.get("ip");
			List<SystemUser> userlist = (List<SystemUser>)systemUserMapper.getUserPassword(localhostip);
			if(userlist!=null){
				for(int i=0;i<userlist.size();i++){
					SystemUser systemuser = userlist.get(i);
					if(systemuser!=null){
						usepassword = systemuser.getPassword();
						loginSuccess = PasswordUtil.passwordCheck(password,usepassword);
						if(loginSuccess){
							break;
						}
					}
				}
			}
			
			//调试模式
			if(GkzxCommon.ONE.equals(ywgk_debug)){
				loginSuccess = true;
			}
			
			if (!loginSuccess) {
				// 密码错误
				returnval = GkzxCommon.RESULT_ERROR;
			}else{
//				Runtime.getRuntime().exec("shutdown -s");
			}
		} catch(Exception e) {  
			returnval = GkzxCommon.RESULT_ERROR;
			System.out.println("发生异常！");  
		}
		return returnval;
	}

	@Override
	public Object saveOrUpdateQianShou(Map<String, Object> paramMap) {
		Object returnval = GkzxCommon.RESULT_SUCCESS;
		try {
			int s = userQianShouMapper.saveOrUpdateQianShou(paramMap);
			if(s>0){
				returnval = GkzxCommon.RESULT_SUCCESS;
			}else {
				returnval = GkzxCommon.RESULT_ERROR;
			}
		} catch(Exception e) {
			returnval = GkzxCommon.RESULT_ERROR;
			System.out.println("发生异常！");  
		}
		return returnval;
	}

	@Override
	public List<Map<String, Object>> getDepartNameList(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int jiechukey(String certno) {
		// TODO Auto-generated method stub
		return 0;
	}
}