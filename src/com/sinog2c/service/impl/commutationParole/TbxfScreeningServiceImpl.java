package com.sinog2c.service.impl.commutationParole;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.commutationParole.TbxfCrimetypesmappingMapper;
import com.sinog2c.dao.api.commutationParole.TbxfScreeningMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.dao.api.system.TbsysCodeMapper;
import com.sinog2c.model.commutationParole.TbxfCrimetypesmapping;
import com.sinog2c.model.commutationParole.TbxfScreening;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.TbxfScreeningService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("tbxfScreeningService")
public class TbxfScreeningServiceImpl extends ServiceImplBase implements TbxfScreeningService {
	@Resource
	private TbxfScreeningMapper tbxfScreening;
	@Resource
	private TbsysCodeMapper tbsysCode;
	@Resource
	private TbxfCrimetypesmappingMapper tbxfCrimetypesmappingMapper;
	@Resource
	private TbprisonerBaseCrimeMapper baseCrimeMapper;
	@Autowired
	private FlowBaseMapper flowBaseMapper;
	@Override
	public void deleteAll(String departid) {
		tbxfScreening.deleteAll(departid);
	}

	@Override
	public int deleteByPrimaryKey(String crimid) {
		
		return tbxfScreening.deleteByPrimaryKey(crimid);
	}

	@Override
	public int getLiAnCount(Map map) {
		return tbxfScreening.getLiAnCount(map);
	}

	@Override
	public List<Map> getLiAnList(Map map) {
		return tbxfScreening.getLiAnList(map);
	}

	@Override
	public int insert(TbxfScreening record) {
		return tbxfScreening.insert(record);
	}

	@Override
	public int insertSelective(TbxfScreening record) {
		return tbxfScreening.insertSelective(record);
	}

	@Override
	public List<TbxfScreening> screeningList(String departid) {
		return tbxfScreening.screeningList(departid);
	}

	@Override
	public TbxfScreening selectByPrimaryKey(String crimid) {
		return tbxfScreening.selectByPrimaryKey(crimid);
	}

	@Override
	public int updateByPrimaryKey(TbxfScreening record) {
		return tbxfScreening.updateByPrimaryKey(record);
	}

	@Override
	@Transactional
	public int saveData(Map<String,Object> parmMap) {
		int rows = 0;
		TbprisonerBaseCrime basecrime = new  TbprisonerBaseCrime();
		String ptf= StringNumberUtil.returnString2(parmMap.get("ptf"));
		String typeid = StringNumberUtil.returnString2(parmMap.get("typeid"));
		String codeid= StringNumberUtil.returnString2(parmMap.get("codeid"));
    	String crimid= StringNumberUtil.returnString2(parmMap.get("crimid"));
    	try{
    		if(StringNumberUtil.notEmpty(codeid)){
        		String[] codeids = codeid.split(",");
        		if(codeids!=null && codeids.length>0){
        			String importantcriminal = GkzxCommon.ZERO;
        			String qitareport = GkzxCommon.ZERO;
        			String isforeign = GkzxCommon.ZERO;
        			String underworld = GkzxCommon.ZERO;
        			String postcrime = GkzxCommon.ZERO;
        			
        			for(String obj:codeids){
        				if(GkzxCommon.ONE.equals(obj)){
        					importantcriminal = GkzxCommon.ONE;
        				}else if(GkzxCommon.TWO.equals(obj)){
        					qitareport = GkzxCommon.ONE;
        				}else if(GkzxCommon.THREE.equals(obj)){
        					isforeign = GkzxCommon.ONE;
        				}else if(GkzxCommon.FOUR.equals(obj)){
        					
        				}else if(GkzxCommon.SEVEN.equals(obj)){
        					underworld = GkzxCommon.ONE;
        				}else if(GkzxCommon.EIGHT.equals(obj)){
        					postcrime = GkzxCommon.ONE;
        				}else if(GkzxCommon.NINE.equals(obj)){
        					
        				}
        			}
        			basecrime.setImportantcriminal(importantcriminal);
        			basecrime.setQitareport(qitareport);
        			basecrime.setIsforeign(isforeign);
        			basecrime.setUnderworld(underworld);
        			basecrime.setPostcrime(postcrime);
        			basecrime.setCasenature(codeid);
        			basecrime.setCrimid(crimid);
        			basecrime.setSanclassstatus(GkzxCommon.ZERO);
        		}
        		rows = baseCrimeMapper.updateByPrimaryKeySelective(basecrime);
        		
        	}
    		
        	//三类犯类型
    		TbxfCrimetypesmapping  mapping = new TbxfCrimetypesmapping();
    		mapping.setCrimid(crimid);
    		mapping.setTypeid(Integer.valueOf(typeid));
    		
    		if(GkzxCommon.TWO.equalsIgnoreCase(ptf)){
    			TbxfCrimetypesmapping  crimetypesmapping = tbxfCrimetypesmappingMapper.selectByPrimaryKey(mapping);
    			
    			if(crimetypesmapping == null){
    				rows = tbxfCrimetypesmappingMapper.insert(mapping);
    			}
    			//
    		}else if(GkzxCommon.ONE.equalsIgnoreCase(ptf)){
    			rows = tbxfCrimetypesmappingMapper.deleteByPrimaryKey(mapping);
    		}
    	}catch(Exception e){
    		rows =-1;
    		e.printStackTrace();
    	}
		return rows;
	}
	
	@Override
	public int updateByPrimaryKeySelective(TbxfScreening record) {
		return tbxfScreening.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateIsdeclare(List<String> list) {
		return tbxfScreening.updateIsdeclare(list);
	}
	
	@Override
	public int updateSpecialCrim(Map map) {
		return tbxfScreening.updateSpecialCrim(map);
	}
	
	@Override
	@Transactional
	public Object updateDataAfterPrisonerLiAn(Map map){
		int isdeclare = 0;//  1已申报未立案  0 未申报  2已立案
		Object result = -1;
		//判断是否已立案
		isdeclare = flowBaseMapper.validateRecord(map);
		if(isdeclare == 1){
			tbxfScreening.updateSpecialCrim(map);
			tbxfScreening.updateDataAfterPrisonerLiAn(map);
		}else{
			result = GkzxCommon.ONE;
		}
		return result ;
	}
	/**
	 * 得到案件性质的列表
	 */
	@Override
	public List<Map> getAnJianXingZhiList(Map map) {
		
		return tbxfScreening.getAnJianXingZhiList(map);
	}
	@Override
	public int getAnjianXingZhiCount(Map map) {
		
		return tbxfScreening.getAnjianXingZhiCount(map);
	}
	@Override
	public TbsysCode getAnJianName(Map map){
		return tbsysCode.getAnJianName(map);
	}
	
	
	/**
	 * 通用删除功能
	 */
	@Override
	@Transactional
	public JSONMessage dealSpecialCase(Map<String, String> map, SystemUser user){
		JSONMessage message = JSONMessage.newMessage();
		try{
			map.put("userid", user.getUserid());
			tbxfScreening.dealSpecialCase(map);
			
			message.setSuccess();
			message.setInfo("操作成功！");
		}catch(Exception e){
			e.printStackTrace();
			message.setInfo("操作失败！");
			throw new RuntimeException();
		}
		
		return message;
	}
	
	
	
	public int getCount(Map<String, Object> parmMap) {
		return baseCrimeMapper.getCount(parmMap);
	}

	@Override
	public List<Map> getSentenceAlterationList(
			Map<String, Object> parmMap) {
		return MapUtil.turnKeyToLowerCaseOfList(baseCrimeMapper.getSentenceAlterationList(parmMap));
	}

}


