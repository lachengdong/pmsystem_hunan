package com.sinog2c.service.impl.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.flow.TbflowDefineMapper;
import com.sinog2c.dao.api.user.UserGrantDetailMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowDefine;
import com.sinog2c.model.flow.TbflowDefineWithBLOBs;
import com.sinog2c.model.user.UserGrantDetail;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.DateUtils;
import com.sinog2c.util.common.MapUtil;

@Service("flowDeliverService")
public class FlowDeliverServiceImpl extends ServiceImplBase implements FlowDeliverService{

	@Autowired
    private FlowDeliverMapper flowDeliverMapper;
	
	@Autowired
	TbflowDefineMapper tbflowDefineMapper;
	
	@Autowired
	UserGrantDetailMapper tbUser_resMapper;

	public FlowDeliverMapper getFlowDeliverMapper() {
		return flowDeliverMapper;
	}

	public void setFlowDeliverMapper(FlowDeliverMapper flowDeliverMapper) {
		this.flowDeliverMapper = flowDeliverMapper;
	}

	public int countAll() {
        return this.flowDeliverMapper.countAll();
    }

	 public FlowDeliver findByCondition(String resid,String departid) {
		 return this.flowDeliverMapper.findByCondition(resid,departid);
	 }
	@Transactional
    public int insert(FlowDeliver flowDeliver) {
        return this.flowDeliverMapper.insert(flowDeliver);
    }

    public List<FlowDeliver> selectAll() {
        return this.flowDeliverMapper.selectAll();
    }
    /**
	 * 根据流程自定义ID查询数据
	 * @param resid
	 * @return
	 */
    public List<FlowDeliver> findByFlowdefid(Map<String, String> map){
    	return this.flowDeliverMapper.findByflowdefid(map);
    }
    
    /**
     * 查询进程数据
     */
    public List<Map<String,Object>> getCaseProcess(Map<String, String> map){
    	return MapUtil.convertKeyList2LowerCase(flowDeliverMapper.getCaseProcess(map));
    }

    /**
	 * 根据部门ID查询数据
	 * @param departid
	 * @return
	 */
	public List<FlowDeliver> findByDepartid(String departid) {
		return this.flowDeliverMapper.findByDepartid(departid);
	}

	@Override
	public String findSnodeidByDnodeid(Map<String, String> map) {
		return flowDeliverMapper.findSnodeidByDnodeid(map);
	}

	@Override
	public List<Map<String, Object>> getFyJinCheng(Map map) {
		return flowDeliverMapper.getFyJinCheng(map);
	}

	//start add by blue_lv 2015-07-15

	@Override
	public int getNextId() {
		return tbflowDefineMapper.getNextId();
	}

	@Override
	public int addNewFlowDefine(TbflowDefineWithBLOBs flowDefine,
			List<FlowDeliver> deList) {
		int count = tbflowDefineMapper.insertSelective(flowDefine);
		for (int i = 0; i < deList.size(); i++) {
			count += flowDeliverMapper.insertSelective(deList.get(i));
		}
		return count;
	}

	@Override
	public int updateFlowDefine(TbflowDefineWithBLOBs flowDefine,
			List<FlowDeliver> deList) {
		int count = tbflowDefineMapper.updateByPrimaryKeySelective(flowDefine);		
		if(count==0)
		{
			tbflowDefineMapper.insertSelective(flowDefine);
		}

		flowDeliverMapper.deleteByflowdefid(flowDefine.getFlowdefid());
		for (int i = 0; i < deList.size(); i++) {
			FlowDeliver deliver=deList.get(i);
			deliver.setDepartid(flowDefine.getDepartid());
			count += flowDeliverMapper.insertSelective(deliver);
		}
		tbUser_resMapper.deleteByPresid(flowDefine.getFlowdefid());
		String opid = flowDefine.getOpid();
		Date distime = DateUtils.addYears(new Date(), 10);
		HashMap<String, String> map = new HashMap<String, String>();

		String applicant = "";
		for (FlowDeliver deliver1 : deList) {
			if (deliver1.getSnodeid().equalsIgnoreCase("0")) {
				applicant = deliver1.getAssigneer();
				break;
			}
		}
		for (FlowDeliver deliver1 : deList) {
			String assigner = deliver1.getAssigneer();
			if (assigner.isEmpty()) {
				assigner = applicant;
			}
			if (assigner.isEmpty()) {
				continue;
			}
			String[] users = assigner.split(";");
			String sNode = deliver1.getDnodeid();
			for (FlowDeliver deliver : deList) {
				if (!sNode.equalsIgnoreCase(deliver.getSnodeid()))
					continue;
				if (deliver.getResid() == null || deliver.getResid().isEmpty())
					continue;
				for (String usrid : users) {
					String tempkey = String.format("%s%s", usrid,
							deliver.getResid());
					if (map.containsKey(tempkey))
						continue;
					map.put(tempkey, tempkey);
					UserGrantDetail userRes = new UserGrantDetail();
					userRes.setDistime(distime);
					userRes.setOpid(opid);
					userRes.setPresid(flowDefine.getFlowdefid());
					userRes.setResid(deliver.getResid());
					userRes.setUserid(usrid);
					userRes.setName(deliver.getExplain());
					this.tbUser_resMapper.insertSelective(userRes);
				}
			}
		}
		return count;
	}

	@Override
	public TbflowDefineWithBLOBs selectByPrimaryKey(String flowdefid) {
		return tbflowDefineMapper.selectByPrimaryKey(flowdefid);
	}

	@Override
	@Transactional
	public int deleteFlowDefinebyID(String flowDefineid) {
		int count = flowDeliverMapper.deleteByflowdefid(flowDefineid);
		return count+=tbflowDefineMapper.deleteByPrimaryKey(flowDefineid);
	}

	@Override
	public JSONMessage<TbflowDefine> getFlowDeliversBydep(
			Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());

		if (rowCount < 0) {
			rowCount = tbflowDefineMapper.getFlowCountBydepid(map);
		}

		return new JSONMessage<TbflowDefine>(
				tbflowDefineMapper.findFlowByDepartid(map), rowCount);
	}

	@Override
	public List<FlowDeliver> getflowNodelistbyflowdefineid(String flowdefid) {
		return this.flowDeliverMapper.getflowNodelistbyflowdefineid(flowdefid);
	}

	@Override
	public JSONMessage<TbflowDefine> getFlowByTypeAndDept(
			Map<String, Object> map) {
		List<TbflowDefine> list = tbflowDefineMapper.findFlowByTypeandDept(map);
		return new JSONMessage<TbflowDefine>(list, list.size());
	}
	//end add by blue_lv 2015-07-15
	
	@Override
	public String getFlowCurrnodeidOfDataGrid(Map<String,Object> map){
		return flowDeliverMapper.getFlowCurrnodeidOfDataGrid(map);
	}
}