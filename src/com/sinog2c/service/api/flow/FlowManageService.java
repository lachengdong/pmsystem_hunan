package com.sinog2c.service.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowSetup;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
/**
 * 流程管理
 * @author wangxf
 *
 */
public interface FlowManageService {

	/**
	 * 获取流程树
	 * @param snodeid
	 * @return 
	 */
	public List<HashMap> selectTree(String snodeid);
	/**
	 * 点击树节点返回相应内容
	 * @param map
	 * @return
	 */
	public List<FlowDeliver> findByflowdefid(Map<String, String> map);
	/**
	 * 添加新流程
	 * @param record
	 * @return
	 */
	public int insert(FlowDeliver record);
	
	public List<FlowDeliver> findByDepartid(String str);
	
	public List<FlowDeliver> findFlowByDepartid(Map map);
	
	public int flowManageServicenum(Map map);
	
	public void beginCopy1(Map map);
	
	public void beginCopy2(Map map);

	public int getFlowconfByDepartid(Map map);
	
	public List<FlowDeliver> getFlowById(Map map);
	
	public int getFlowByIdCount(Map map);
	
	public void updateFlowInfo(Map map);
	
	public void createSequences();
	
	public void removeFlow(Map map);
	
	public void createOrgOrg(Map map);
	
	public void delteOrgByid(String str);
	
	public List getOrgInfo(Map map);
	
	public int getOrgInfoCount(Map map);
	
	public void removeOrgOrg(Map map);
	
	public int findDepartidCount(Map map);
	
	public List<Map> getXingwenData(Map map);
	
	public int getXingwenCount(Map map);
	
	public List getXingWenDepart();
	
	int selectXingWenCount(Map map);
	
	int deleXingWen(Map map);
	
	String CopyXingWen(Map map);
	
	FlowDeliver setValue(String str);
	
	int findCountFlowid(Map map);
	
	void addFlowToFlowconf(Map map);
	
	int getDeliverCount(Map map);
    
    public List<Map<String,Object>> getChengBanPersons(Map map);
    
    public List<Map<String,Object>> getNextApprovePersons(Map<String,Object> map);
	
	public Map<String,Object> getFlowDeliverType(Map<String,Object> map);
	
	public Map<String,Object> getFlowDeliverNodeName(Map<String,Object> map);
	
	public Map<String,Object> getCurrApprovePerson(String userid);
	
	public Map<String,Object> getDuserApprovePersons(Map<String,Object> paramap);
	
	public List<Map<String,Object>> ajaxGetFlowDeliverNodeid(Map<String,Object> map);
	
	public Object operateFlowPersonConfig(List list, SystemUser user);
	
	public List<Map<String,Object>> getFlowPersonConfigs(Map<String,Object> map);
	
	public int countFlowPersonConfigs(Map<String,Object> map);
	
}
