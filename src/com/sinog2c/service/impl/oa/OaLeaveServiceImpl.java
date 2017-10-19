package com.sinog2c.service.impl.oa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.oa.OaLeaveMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.oa.OaLeave;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.oa.OaLeaveService;
import com.sinog2c.service.api.system.SystemUserService;
/**
 * @author 肖岩
 * date 2015/08/31
 * function 请假单的业务处理实现类
 * */
@Service("oaLeaveService")
public class OaLeaveServiceImpl implements OaLeaveService{
	@Autowired
	private OaLeaveMapper oaLeaveMapper;
	@Autowired
	private SystemUserService systemuserService;
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 新增方法，在流程执行完成后进行保存
	 * oasickleave 请假申请实体类
	 * */
	@Override
	public int save(OaLeave oaleave){
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		oaleave.setUuid(uuid);
		SystemUser user = systemuserService.getByUserId(oaleave.getProposerid());
		oaleave.setDepartment(user.getDepartid());
		int i = oaLeaveMapper.insertSelective(oaleave);
		return i;
	}
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询请假单信息列表方法实现类
	 * map 请求参数
	 * */
	@Override
	public JSONMessage<OaLeave> getLeaveListInfo(
			Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());
		if (rowCount < 0) {
			rowCount = this.oaLeaveMapper.getCountLeaveByCondition(map);
		}
		List<OaLeave> list = this.oaLeaveMapper.getLeaveListByCondition(map);
		return new JSONMessage<OaLeave>(list, rowCount);
	}
	public OaLeave getLeaveInfoById(String uuid) {
		OaLeave oaleave = new OaLeave();
		List<OaLeave> oaLeaveList = this.oaLeaveMapper.getLeaveListById(uuid);
		if(oaLeaveList!=null&&oaLeaveList.size()>0){
			 oaleave = oaLeaveList.get(0);
		}
		return oaleave;
	}
	/**
	 * @author 肖岩
     * date 2015/09/01
	 * 按id查看请假单信息实现方法
	 * uuid uuid
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getLeaveInfo(String uuid) {
		OaLeave oaleave = new OaLeave();
		List<OaLeave> oaLeaveList = this.oaLeaveMapper.getLeaveListById(uuid);
		if(oaLeaveList!=null&&oaLeaveList.size()>0){
			 oaleave = oaLeaveList.get(0);
			List<SystemUser> userList = systemuserService.getAllByPage(0, 999);
			if (userList!=null&&userList.size()>0) {
				for (SystemUser user:userList){
					if(user.getUserid().equals(oaleave.getProposerid())){
						oaleave.setProposername(user.getName());
						oaleave.setJobs(user.getDuty());
					}
					if(user.getUserid().equals(oaleave.getDepmanapproverid())){
						oaleave.setDepmanapprovername(user.getName());
					}
					if(user.getUserid().equals(oaleave.getPersonnelapproverid())){
						oaleave.setPersonnelapprovername(user.getName());
					}
					if(user.getUserid().equals(oaleave.getPartmanapproverid())){
						oaleave.setPartmanapprovername(user.getName());
					}
					if(user.getUserid().equals(oaleave.getGenmanapproverid())){
						oaleave.setGenmanapprovername(user.getName());
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("name",oaleave.getProposername());
		map.put("proposerid",oaleave.getProposerid());
		map.put("jobs",oaleave.getJobs()!=null?oaleave.getJobs():"");
		map.put("leavetype",oaleave.getLeavetype());
		map.put("leaveway",oaleave.getLeaveway());
		map.put("reason",oaleave.getReason());
		map.put("zjldsign",oaleave.getZjldsign());
		map.put("prodate",oaleave.getProdate());
		map.put("leavedate1", oaleave.getLeavedate1());
		map.put("leavedate2", oaleave.getLeavedate2());
		map.put("leavetime1", oaleave.getLeavetime1()!=null?oaleave.getLeavetime1():"");
		map.put("leavetime2", oaleave.getLeavetime2()!=null?oaleave.getLeavetime2():"");
		map.put("days", oaleave.getDays()!=null?oaleave.getDays():"");
		map.put("hours", oaleave.getHours()!=null?oaleave.getHours():"");
		
		map.put("depmanname",oaleave.getDepmanapprovername()!=null?oaleave.getDepmanapprovername():"");
		map.put("depmanadvice",oaleave.getDepmanadvice()!=null?oaleave.getDepmanadvice():"");
		map.put("depmandate",oaleave.getDepmandate()!=null?oaleave.getDepmandate():"");
		map.put("personnelname",oaleave.getPersonnelapprovername()!=null?oaleave.getPersonnelapprovername():"");
		map.put("personneladvice",oaleave.getPersonneladvice()!=null?oaleave.getPersonneladvice():"");
		map.put("personneldate",oaleave.getPersonneldate()!=null?oaleave.getPersonneldate():"");
		map.put("partmanname",oaleave.getPartmanapprovername()!=null?oaleave.getPartmanapprovername():"");
		map.put("partmanadvice",oaleave.getPartmanadvice()!=null?oaleave.getPartmanadvice():"");
		map.put("partmandate",oaleave.getPartmandate()!=null?oaleave.getPartmandate():"");
		map.put("genmanname",oaleave.getGenmanapprovername()!=null?oaleave.getGenmanapprovername():"");
		map.put("genmanadvice",oaleave.getGenmanadvice()!=null?oaleave.getGenmanadvice():"");
		map.put("genmandate",oaleave.getGenmandate()!=null?oaleave.getGenmandate():"");
		return map;
	}
}
