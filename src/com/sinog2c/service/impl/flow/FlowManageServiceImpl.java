package com.sinog2c.service.impl.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.flow.TbFlowPersonConfigMapper;
import com.sinog2c.dao.api.system.SystemUserMapper;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbFlowPersonConfig;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowManageService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 流程管理
 * @author wangxf
 *
 */
@Service("flowManageService")
public class FlowManageServiceImpl extends ServiceImplBase implements FlowManageService {

	@Autowired
	FlowDeliverMapper flowDeliverMapper;
	@Autowired
	TbFlowPersonConfigMapper tbFlowPersonConfigMapper;
	@Autowired
	SystemUserMapper systemUserMapper;
	
	/**
	 * 点击树节点返回相应内容
	 * @param map
	 * @return
	 */
	@Override
	public List<FlowDeliver> findByflowdefid(Map<String, String> map) {
		return flowDeliverMapper.findByflowdefid(map);
	}

	/**
	 * 添加新流程
	 * @param record
	 * @return
	 */
	@Override
	@Transactional
	public int insert(FlowDeliver record) {
		return flowDeliverMapper.insert(record);
	}

	/**
	 * 获取流程树
	 * @param snodeid
	 * @return 
	 */
	@Override
	public List<HashMap> selectTree(String snodeid) {
		// TODO Auto-generated method stub
		return flowDeliverMapper.selectTree( snodeid);
	}

	@Override
	public List<FlowDeliver> findByDepartid(String str) {
		return flowDeliverMapper.findByDepartid(str);
	}

	@Override
	public List<FlowDeliver> findFlowByDepartid(Map map) {
		return flowDeliverMapper.findFlowByDepartid(map);
	}

	@Override
	public int flowManageServicenum(Map map) {
		return flowDeliverMapper.flowManageServicenum(map);
	}

	@Override
	public void beginCopy1(Map map) {
		flowDeliverMapper.beginCopy1(map);
	}

	@Override
	public int getFlowconfByDepartid(Map map) {
		return flowDeliverMapper.getFlowconfByDepartid(map);
	}

	@Override
	public void beginCopy2(Map map) {
		flowDeliverMapper.beginCopy2(map);
		
	}

	@Override
	public List<FlowDeliver> getFlowById(Map map) {
		return flowDeliverMapper.getFlowById(map);
	}

	@Override
	public int getFlowByIdCount(Map map) {
		return flowDeliverMapper.getFlowByIdCount(map);
	}

	@Override
	public void updateFlowInfo(Map map) {
		flowDeliverMapper.updateFlowInfo(map);
	}

	@Override
	public void createSequences() {
		flowDeliverMapper.createSequences();
	}

	@Override
	public void removeFlow(Map map) {
		flowDeliverMapper.removeFlow(map);
	}

	@Override
	public void createOrgOrg(Map map) {
		flowDeliverMapper.createOrgOrg(map);
	}

	@Override
	public void delteOrgByid(String str) {
		 flowDeliverMapper.delteOrgByid(str);
	}

	@Override
	public List getOrgInfo(Map map) {
		return flowDeliverMapper.getOrgInfo(map);
	}

	@Override
	public int getOrgInfoCount(Map map) {
		return flowDeliverMapper.getOrgInfoCount(map);
	}

	@Override
	public void removeOrgOrg(Map map) {
		flowDeliverMapper.removeOrgOrg(map);
	}

	@Override
	public int findDepartidCount(Map map) {
		return flowDeliverMapper.findDepartidCount(map);
	}

	@Override
	public int getXingwenCount(Map map) {
		return flowDeliverMapper.getXingwenCount(map);
	}

	@Override
	public List<Map> getXingwenData(Map map) {
		return flowDeliverMapper.getXingwenData(map);
	}

	@Override
	public List getXingWenDepart() {
		return MapUtil.turnKeyToLowerCaseOfList(flowDeliverMapper.getXingWenDepart());
	}

	@Override
	public String CopyXingWen(Map map) {
		return flowDeliverMapper.CopyXingWen(map);
	}

	@Override
	public int deleXingWen(Map map) {
		return flowDeliverMapper.deleXingWen(map);
	}

	@Override
	public int selectXingWenCount(Map map) {
		return flowDeliverMapper.selectXingWenCount(map);
	}

	@Override
	public FlowDeliver setValue(String str){
		return flowDeliverMapper.setValue(str);
	}

	@Override
	public int findCountFlowid(Map map) {
		return flowDeliverMapper.findCountFlowid(map);
	}

	@Override
	public void addFlowToFlowconf(Map map) {
		 flowDeliverMapper.addFlowToFlowconf(map);
	}

	@Override
	public int getDeliverCount(Map map) {
		return flowDeliverMapper.getDeliverCount(map);
	}
	
	@Override
	public List<Map<String,Object>> getChengBanPersons(Map map) {
		return MapUtil.convertKeyList2LowerCase(tbFlowPersonConfigMapper.getChengBanPersons(map));
	}
	
	@Override
	public List<Map<String,Object>> getNextApprovePersons(Map<String,Object> map) {
		String nextApprovePersons = tbFlowPersonConfigMapper.getNextApprovePersons(map);
		nextApprovePersons = StringNumberUtil.formatString(nextApprovePersons, ",");
		map.put("userids", nextApprovePersons);
		return MapUtil.convertKeyList2LowerCase(systemUserMapper.getApprovePersons(map));
	}

	@Override
	public Map<String,Object> getFlowDeliverType(Map<String,Object> map){
		return MapUtil.convertKey2LowerCase(tbFlowPersonConfigMapper.getFlowDeliverType(map));
	}
	
	@Override
	public Map<String,Object> getFlowDeliverNodeName(Map<String,Object> map){
		return MapUtil.convertKey2LowerCase(tbFlowPersonConfigMapper.getFlowDeliverNodeName(map));
	}
	
	@Override
	public Map<String,Object> getCurrApprovePerson(String userid){
		SystemUser user = systemUserMapper.getByUserId(userid);
		if(null != user){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("id", userid);
			result.put("text", user.getName());
			return result;
		}
		return null;
	}
	
	@Override
	public Map<String,Object> getDuserApprovePersons(Map<String,Object> paramap){
		List<Map<String,Object>> userList = MapUtil.convertKeyList2LowerCase(systemUserMapper.getApprovePersons(paramap));
		if(null != userList){
			Map<String,Object> result = new HashMap<String,Object>();
			StringBuffer usernamesSB = new StringBuffer(); 
			for(Map temp : userList){
				usernamesSB.append(temp.get("name")).append(",");
			}
			String usernames = usernamesSB.toString();
			usernames = StringNumberUtil.removeLastStr(usernames, ",");
			result.put("id", paramap.get("userids").toString());
			result.put("text", usernames);
			return result;
		}
		return null;
	}
	
	@Override
	public List<Map<String,Object>> ajaxGetFlowDeliverNodeid(Map<String,Object> map){
		return MapUtil.convertKeyList2LowerCase(tbFlowPersonConfigMapper.ajaxGetFlowDeliverNodeid(map));
	}
	
	@Override
	@Transactional
	public Object operateFlowPersonConfig(List list, SystemUser user){
		JSONMessage message =JSONMessage.newMessage();
		int row = 0;
		try{
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = (HashMap<String,Object>)list.get(i);
				TbFlowPersonConfig config = new TbFlowPersonConfig();
				
				config.setId(map.get("id").toString());
				config.setDepartid(user.getDepartid());
				config.setFlowdefid(map.get("flowdefid").toString());
				config.setNodeid(map.get("nodeid").toString());
				config.setUserid(map.get("userid").toString());
				config.setDuserid(map.get("duserid").toString());
				config.setOrderby(Integer.parseInt(map.get("orderby").toString()));
				
				config.setOpid(user.getUserid());
				config.setOptime(new Date());
				
				if("added".equals((String)map.get("_state"))){
					row = tbFlowPersonConfigMapper.insertSelective(config);
				}else if("modified".equals((String)map.get("_state"))){
					row = tbFlowPersonConfigMapper.updateByPrimaryKeySelective(config);
				}else if("removed".equals((String)map.get("_state"))){
					row = tbFlowPersonConfigMapper.deleteByPrimaryKey(config);
				}
				
				if(row !=1){
					throw new Exception("数据库操作失败！");
				}
			}
			message.setSuccess();
			message.setInfo("操作成功！");
		}catch(Exception e){
			e.printStackTrace();
			message.setInfo("操作失败！");
			throw new RuntimeException();
		}
		return message;
	}
	
	@Override
	public List<Map<String,Object>> getFlowPersonConfigs(Map<String,Object> map){
		return MapUtil.convertKeyList2LowerCase(tbFlowPersonConfigMapper.getFlowPersonConfigs(map));
	}
	
	@Override
	public int countFlowPersonConfigs(Map<String,Object> map){
		return tbFlowPersonConfigMapper.countFlowPersonConfigs(map);
	}

}
