package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.SignatureFlowMapper;
import com.sinog2c.model.system.SignatureFlow;
import com.sinog2c.service.api.system.SignatureFlowService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

@Service("signatureFlowService")
public class SignatureFlowServiceImpl extends ServiceImplBase implements SignatureFlowService {
	@Autowired
	private SignatureFlowMapper signatureFlowMapper;
	
	@Override
	public Map<String, Object> getLastSignatureFlow(Map<String, Object> map) {
		return MapUtil.convertKey2LowerCase(signatureFlowMapper.getLastSignatureFlow(map));
	}
	
	@Override
	public SignatureFlow findLastFlowNodeSignatureFlow(Map<String, Object> map){
		return signatureFlowMapper.findLastFlowNodeSignatureFlow(map);
	}
	
	@Override
	public List<SignatureFlow> findLastFlowNodeSignatureFlows(Map<String, Object> map){
		return signatureFlowMapper.findLastFlowNodeSignatureFlows(map);
	}

	@Override
	public int insertMapSelective(Map<String, Object> map) {
		return signatureFlowMapper.insertMapSelective(map);
	}
	
	/**
	 * @author YangZR		2016-01-08
	 * @describe		操作流程流水表，保存当前表单的签章流水
	 * @param map
	 */
	@Override
	public void operateSignatureFlow(Map<String,Object> map, String operatetype, String currnodeid, Object requaresealnum){
		
		int count = 0;
		
		map.put("operatetype", operatetype);
		map.put("currnodeid", currnodeid);
		map.put("requaresealnum", requaresealnum);
		
		SignatureFlow sf = findLastFlowNodeSignatureFlow(map);
		if(null == sf){
//			map.put("lastnodeid", "0");
//			map.put("lastsealnum", 0);
		}else{
			map.put("lastnodeid", sf.getCurrnodeid());
			map.put("lastsealnum", sf.getSealnum());
		}
		
		count = insertMapSelective(map);
		
		if(count<=0){
			throw new RuntimeException();
		}
		
	}

}
