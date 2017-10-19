package com.sinog2c.service.impl.officeAssistant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinog2c.dao.api.officeAssistant.TbuserNoticeMapper;
import com.sinog2c.dao.api.user.UserGrantDetailMapper;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.user.UserGrantDetail;
import com.sinog2c.service.api.officeAssistant.UserGrantService;

@Service("userGrantService")
public class UserGrantServiceImpl implements UserGrantService {

	@Resource
	private UserGrantDetailMapper detailMapper;
	@Resource
	private TbuserNoticeMapper noticeMapper;
	@Override
	@Transactional
	public int add(TbuserNotice notice, SystemUser operator) {
		//  FIXME 如果用户对另一个用户多次新增授权,可能有BUG. 先不管.
		// 1. 添加授权detail记录
		// 2. 添加记录本身
		
		if(null == notice || null ==operator){
			return -1;
		}

		//
		if(null == notice || null ==operator){
			return -1;
		}


		// 2. 添加记录本身
		int rows = noticeMapper.insertSelective(notice);
		
		// 1. 先删除老的授权记录
		String userid =notice.getUsername();
		String opid = operator.getUserid();
		notice.setOpid(opid);

		// 删除用户opid授权给另一个用户userid的所有无效记录 含撤销和过期两种情况
//		detailMapper.deleteDisableGrants(opid,userid); 
		
		Date currentTime = new Date();
		Date distime = notice.getEndtime();
		// Date starttime = notice.getStarttime(); //  一个BUG,开始时间是当前时间
		//
		// 1. 添加新的Detail授权记录
		List<UserGrantDetail> details = notice.getGrantDetails();
		
		if (null != details) {
			Iterator<UserGrantDetail> iteratorD = details.iterator();
			while (iteratorD.hasNext()) {
				UserGrantDetail userGrantDetail = (UserGrantDetail) iteratorD.next();
				if(null == userGrantDetail){
					continue;
				}
				//
				String resid = userGrantDetail.getResid();
				userGrantDetail.setOpid(opid);
				userGrantDetail.setUserid(userid);
				userGrantDetail.setOptime(currentTime);
				userGrantDetail.setDistime(distime);
				userGrantDetail.setName(getResourceName(operator, resid)); // 根据用户来判断
				userGrantDetail.setPresid(getPResourceId(operator, resid)); // 根据用户来判断
				userGrantDetail.setNoticeid(notice.getNoticeid());//把noticeid的值插入到 权限对应表中
				//
				// !!! 详细记录,是新增而不是更新
				detailMapper.insertSelective(userGrantDetail);
				detailMapper.insertHisSelective(userGrantDetail);
			}
		}
		return rows;
	}

	@Override
	@Transactional
	public int update(TbuserNotice notice, SystemUser operator) {
		// 1. 先删除老的授权记录
		// 2. 插入新的授权detail记录
		// 3. 更新记录本身
		if(null == notice || null ==operator){
			return -1;
		}

		// 1. 先删除老的授权记录
		String userid =notice.getUsername();
		String opid = operator.getUserid();
		notice.setOpid(opid);

		//根据noticeid主键即可删除 当前修改的权限
		detailMapper.deleteUserGrants(notice.getNoticeid());
		//删除用户opid授权给另一个用户userid的所有无效记录
//		detailMapper.deleteDisableGrants(opid,userid); 
		
		Date currentTime = new Date();
		Date distime = notice.getEndtime();
		//2. 插入新的记录
		List<UserGrantDetail> details = notice.getGrantDetails();
		if (null != details) {
			Iterator<UserGrantDetail> iteratorD = details.iterator();
			while (iteratorD.hasNext()) {
				UserGrantDetail userGrantDetail = (UserGrantDetail) iteratorD.next();
				if(null == userGrantDetail){
					continue;
				}
				//
				String resid = userGrantDetail.getResid();
				userGrantDetail.setOpid(opid);
				userGrantDetail.setUserid(userid);
				userGrantDetail.setOptime(currentTime);
				userGrantDetail.setDistime(distime);
				userGrantDetail.setName(getResourceName(operator, resid)); // 根据用户来判断
				userGrantDetail.setPresid(getPResourceId(operator, resid)); // 根据用户来判断
				userGrantDetail.setNoticeid(notice.getNoticeid());//事件id
				//
				// !!! 详细记录,是新增而不是更新
				detailMapper.insertSelective(userGrantDetail);
				detailMapper.insertHisSelective(userGrantDetail);
			}
		}
		// 3. 更新记录本身
		int rows = noticeMapper.update(notice);
		return rows;
	}
	

	private String getPResourceId(SystemUser operator, String resid){
		//
		if(null == operator || null ==resid){
			return null;
		}
		//
		List<SystemResource> allResources = operator.getAllResources();
		if(null != allResources){
			Iterator<SystemResource> iteratorR = allResources.iterator();
			while (iteratorR.hasNext()) {
				SystemResource systemResource = (SystemResource) iteratorR.next();
				if(null != systemResource){
					String id = systemResource.getResid();
					String presid = systemResource.getPresid();
					if(resid.equalsIgnoreCase(id)){
						return presid; // 找到了,返回
					}
				}
			}
		}
		//
		return "-1";
	}
	
	private String getResourceName(SystemUser operator, String resid){
		//
		if(null == operator || null ==resid){
			return null;
		}
		//
		List<SystemResource> allResources = operator.getAllResources();
		if(null != allResources){
			Iterator<SystemResource> iteratorR = allResources.iterator();
			while (iteratorR.hasNext()) {
				SystemResource systemResource = (SystemResource) iteratorR.next();
				if(null != systemResource){
					String id = systemResource.getResid();
					String name = systemResource.getName();
					if(resid.equalsIgnoreCase(id)){
						return name; // 找到了,返回
					}
				}
			}
		}
		//
		return "资源_" + resid ;
	}

	@Override
	@Transactional
	@SuppressWarnings("all")
	public int delete(int noticeid, SystemUser operator) {
		//获取选中 当前数据的 的详细信息
		int rows = 0;
		try {
			TbuserNotice notice = noticeMapper.selectNoticeByNoticeId(noticeid);
			//
			if(null == notice || null == operator){
				return -1;
			}
			// 1. 删除授权detail记录
			// 被授权人
			String userid =notice.getUsername();
			String opid = operator.getUserid();
			//
			UserGrantDetail detail = new UserGrantDetail();
			detail.setUserid(userid);
			detail.setOpid(opid);
//			rows = detailMapper.delete(detail);
			//上面删除操作，只是用在授权信息表中。所以可以放心注释掉...
			//上面注释掉以后，那么久可以安全的执行下面的操作了，哈哈哈...
			//修改人：mushuhong
			Map map = new HashMap();
			map.put("userid", userid);
			map.put("opid", opid);
			map.put("noticeid", notice.getNoticeid());
			int row1 = detailMapper.updateStateByNoticeid(map);
			int row2 = detailMapper.updateEndtimeByNoticeid(map);
			rows = row1+row2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 2. 删除记录本身
		//int rows = noticeMapper.deleteNoticeByid(noticeid);
		return rows;
	}

	@Override
	public TbuserNotice getByUseridAndOpid(String userid, SystemUser operator) {
		//
		if(null == userid || null == operator){
			return null;
		}

		String opid = operator.getUserid();
		// 1. 获取用户对应的记录,
		TbuserNotice notice = new TbuserNotice(); // 去get
		
		int type = 1;
		List<TbuserNotice> notices = noticeMapper.selectAll(type);
		if(null != notices){
			Iterator<TbuserNotice> iteratorN = notices.iterator();
			while (iteratorN.hasNext()) {
				TbuserNotice tbuserNotice = (TbuserNotice) iteratorN.next();
				if(null == tbuserNotice){
					continue;
				}
				String username = tbuserNotice.getUsername();
				String opidN = tbuserNotice.getOpid();
				if(userid.equalsIgnoreCase(username) && opid.equalsIgnoreCase(opidN)){
					// 符合
					notice = tbuserNotice;
					break; // 不再继续
				}
			}
		}
		// 2. 获取对应的细节记录
		List<UserGrantDetail> details = listGrantByUseridAndOpid(userid, operator,0);
		//
		notice.setGrantDetails(details);
		//
		// throw new RuntimeException(LogUtil.currentMethodName()+"没有实现");
		//
		return notice;
	}
	
	@Override
	public TbuserNotice getByNoticeid(int noticeid, SystemUser operator) {


		// 1. 获取用户对应的记录,
		TbuserNotice notice = noticeMapper.selectNoticeByNoticeId(noticeid);
		if(null != notice){
			//
			String userid = notice.getUsername();
			// 2. 获取对应的细节记录
			
			List<UserGrantDetail> details = listGrantByUseridAndOpid(userid, operator,noticeid);
			//
			notice.setGrantDetails(details);
		}
		//
		return notice;
	}
	
	@Override
	public List<UserGrantDetail> listGrantByUseridAndOpid(String userid, SystemUser operator,int noticeid) {
		if(null == userid || null == operator){
			return null;
		}
		String opid = operator.getUserid();
		
		List<UserGrantDetail> result = detailMapper.listByUseridOpid(userid, opid,noticeid);
		return result;
	}

	@Override
	public List<TbuserNotice> selectGrantList(int type, String userid, String key, int pageIndex, int pageSize, String sortField, String sortOrder) {
		// 1. 获取用户对应的记录,不需要获取细节记录
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		List<TbuserNotice> notices = noticeMapper.selectGrantList(type, userid, key, start, end, sortField, sortOrder);
		return notices;
	}
	@Override
	public int selectGrantCount(int type, String userid, String key){
		return noticeMapper.selectGrantCount(type, userid, key);
	};
	
	@Override
	public List<TbuserNotice> selectGrantList_bs(int type, String userid, String key, int pageIndex, int pageSize, String sortField, String sortOrder) {
		// 1. 获取用户对应的记录,不需要获取细节记录
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		List<TbuserNotice> notices = noticeMapper.selectGrantList_bs(type, userid, key, start, end, sortField, sortOrder);
		
		return notices;
	}
	@Override
	public int selectGrantCount_bs(int type, String userid, String key){
		return noticeMapper.selectGrantCount(type, userid, key);
	}

	@SuppressWarnings("all")
	public String checkXZUserBSGrant(HttpServletRequest request, SystemUser user) {
		String showuserid = user.getUserid();//当前登录用id
		String bsquserid = request.getParameter("userid");//被授权用户的id
		//返回结果
		String value = "";
		try {
			Map map = new HashMap();
			map.put("showuserid", showuserid);
			map.put("bsquserid", bsquserid);
			List<Map> listMaps = noticeMapper.checkXZUserBSGrant(map);
			if (listMaps != null) {
				for (Map map2 : listMaps) {
					value += map2.get("RESID")+",";
				}
				//去掉最后一个 字符
				if (value.length() > 0) {
					value = value.substring(0, value.length()-1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	@SuppressWarnings("all")
	public String queryMinGrantByNoticeids(Map  map) {
		String value ="";
		int count = 1;
		try {
			List<Map> list = noticeMapper.queryMinGrantByNoticeids(map);
			if (list != null && list.size() > 0) {
				for (Map map2 : list) {
					value += count+"->:"+map2.get("REMARK").toString().replace("&nbsp", "")+"\n";
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	@SuppressWarnings("all")
	public String checkXZUserGrantids(HttpServletRequest request,
			SystemUser user) {
//		String showuserid = user.getUserid();//当前登录用id
//		String bsquserid = request.getParameter("userid");//被授权用户的id
		String noticeid = request.getAttribute("noticeid").toString();//选择需要修改的数据id
		//返回结果
		String value = "";
		try {
			Map map = new HashMap();
//			map.put("showuserid", showuserid);
//			map.put("bsquserid", bsquserid);
			map.put("noticeid", noticeid);
			List<Map> listMaps = noticeMapper.checkXZUserGrantids(map);
			if (listMaps != null) {
				for (Map map2 : listMaps) {
					value += map2.get("RESID")+",";
				}
				//去掉最后一个 字符
				if (value.length() > 0) {
					value = value.substring(0, value.length()-1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	};
}
