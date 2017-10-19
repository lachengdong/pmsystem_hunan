package com.sinog2c.service.impl.solution;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinog2c.dao.api.solution.FormSolutionDetailMapper;
import com.sinog2c.dao.api.system.SystemLogMapper;
import com.sinog2c.model.solution.FormSolutionDetail;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.solution.FormSolutionDetailService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("formSolutionDetailService")
public class FormSolutionDetailServiceImpl extends ServiceImplBase implements  FormSolutionDetailService{
	@Autowired
	private SystemLogMapper systemLogMapper;
	@Autowired
	FormSolutionDetailMapper formSolutionDetailMapper;
	
	@Override
	public List<FormSolutionDetail> getTreeDataOfSolutionDetail(Map<String,Object> map){
		return formSolutionDetailMapper.getTreeDataOfSolutionDetail(map);
	}

	@Override
	public FormSolutionDetail getSingleSolutionDetail(Map<String,Object> map){
		return formSolutionDetailMapper.selectByCombinationId(map);
	}

	@Override
	@Transactional
	public int saveSolutionDetail(Map<String,Object> map,SystemUser su) {
		Object operateObj = map.get("operate");
		SystemLog log = new SystemLog();
		log.setOpid(su.getUserid());
		log.setOpname(su.getName());
		log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
		log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
		log.setLogtype("查询方案操作");
		log.setOpaction("方案细节新增/修改操作"+operateObj.toString());
		log.setOpcontent("方案细节信息新增/修改更新,detailid="+map.get("detailid")+";detailname="+map.get("detailname"));
		log.setOrgid(su.getOrgid());
		log.setStatus(1);
		log.setRemark("操作成功!");
		systemLogMapper.insertSelective(log);
		if(StringNumberUtil.notEmpty(operateObj)){
			if("new".equals(operateObj.toString())){
				map.put("createmender", su.getUserid());
				return formSolutionDetailMapper.insertByMap(map);
			}else if("edit".equals(operateObj.toString())){
				map.put("updatemender", su.getUserid());
				return formSolutionDetailMapper.updateByMap(map);
			}
		}
		return 0;
	}
	
	@Override
	@Transactional
	public int deleteSolutionDetail(String detailid,SystemUser operator){
		SystemLog log = new SystemLog();
        log.setOpid(operator.getUserid());
        log.setOpname(operator.getName());
        log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
        log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
        log.setLogtype("查询方案操作");
        log.setOpaction("方案细节删除操作");
        log.setOpcontent("方案细节删除操作,detailid="+detailid);
        log.setOrgid(operator.getOrgid());
        log.setStatus(1);
        log.setRemark("操作成功!");
        systemLogMapper.insertSelective(log);
		return formSolutionDetailMapper.deleteById(detailid);
	}

}
