package com.sinog2c.service.impl.prisoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.prisoner.TbxfoldilldisabilityMapper;
import com.sinog2c.model.prisoner.Tbxfoldilldisability;
import com.sinog2c.model.prisoner.TbxfoldilldisabilityKey;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.prisoner.TbxfoldilldisabilityService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("TbxfoldilldisabilityService")
public class TbxfoldilldisabilityServiceImpl extends ServiceImplBase implements TbxfoldilldisabilityService {

	@Autowired
	private TbxfoldilldisabilityMapper xfoldilldisabilityMapper;
	/**
	 *  根据key 删除单条
	 * @param key(根据罪犯id 老病残种类oidtypedetail  老病残种类明细oidtypedetail)
	 * @return
	 * @author wangxf
	 */
	@Override
	public int deleteByPrimaryKey(Map<String, Object> map) {
		
		return xfoldilldisabilityMapper.deleteByPrimaryKey(map);
	}

	 /**
     * 只更新非空字段
     * @param record
     * @return
     * @author wangxf
     */
	@Override
	public int insertSelective(Tbxfoldilldisability record) {
		// TODO Auto-generated method stub
		return xfoldilldisabilityMapper.insertSelective(record);
	}

	 /**
     * 根据key查询
     * @param key
     * @return
     * @author wangxf
     */
	@Override
	public Tbxfoldilldisability selectByPrimaryKey(TbxfoldilldisabilityKey key) {
		// TODO Auto-generated method stub
		return xfoldilldisabilityMapper.selectByPrimaryKey(key);
	}
	/**
     * 根据key更新全部字段
     * @param record
     * @return
     * @author wangxf
     */
	@Override
	public int updateByPrimaryKeySelective(Tbxfoldilldisability tbxfoldilldisability) {
		
		// TODO Auto-generated method stub
		return xfoldilldisabilityMapper.updateByPrimaryKeySelective(tbxfoldilldisability);
	}

	
	@Override
	public List<Map<String, Object>> findByCrimid(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return MapUtil.convertKeyList2LowerCase(xfoldilldisabilityMapper.findByCrimid(map));
		
	}

	/* (non-Javadoc)
	 * @see com.sinog2c.service.api.prisoner.TbxfoldilldisabilityService#findByCrimidCount(java.util.Map)
	 */
	@Override
	public int findByCrimidCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return xfoldilldisabilityMapper.findByCrimidCount(map);
	}

    public int checkByPrimaryKey(Map<String, String> map) {
		
		
		// TODO Auto-generated method stub
		return xfoldilldisabilityMapper.checkByPrimaryKey(map);
	}
	

    /**
     * 在新增或者修改老病残的同时同步老病残情况到tbprisoner_base_crime(OIDTYPE存为0,1)和tbxf_sentencealteration表(oidtypedetail)
     */
    public Map<String,String> updateBaseCrimeAndSentenceAlteration(Map<String,String> map){

    	Map<String,String> map2=new HashMap<String,String>();
    	
    	map2.put("CRIMID", map.get("crimid"));
    	
    	if(map.get("oidtype").equals("老")){
    		
    		map2.put("OLDPRISONER", "1");
    		
    		map2.put("OLDTYPE", map.get("oidtypedetail"));
    		
    		xfoldilldisabilityMapper.updatetbprisonerbasecrime(map2);
    		
    		xfoldilldisabilityMapper.updatetbxfsentencealteration(map2);
    		
    	}else if(map.get("oidtype").equals("病")){
    		
    		map2.put("ILLPRISONER", "1");
    		
    		map2.put("ILLTYPE", map.get("oidtypedetail"));
    		
    		xfoldilldisabilityMapper.updatetbprisonerbasecrime(map2);
    		
    		xfoldilldisabilityMapper.updatetbxfsentencealteration(map2);
    		
    	}else if(map.get("oidtype").equals("残")){
    		
    		map2.put("DEFORMITYPRISONER", "1");
    		
    		map2.put("DISABILITYPE", map.get("oidtypedetail"));
    		
    		xfoldilldisabilityMapper.updatetbprisonerbasecrime(map2);
    		
    		xfoldilldisabilityMapper.updatetbxfsentencealteration(map2);
    		
    	}
    	
    	return null;
    };
    
    
    /**
     * 删除老病残的同时同步老
     * 病残情况到tbprisoner_base_crime(OIDTYPE存为0,1)和tbxf_sentencealteration表(oidtypedetail)
     */
    public Map<String,String> deleteBaseCrimeAndSentenceAlteration(Map<String,String> map){
    	
    	map.put("CRIMID", map.get("crimid"));
    	
    	if(map.get("oidtype").equals("老")){
    		
    		map.put("OLDPRISONER", "0");
    		
    		map.put("OLDTYPE", "");
    		
    		map.put("ISOLD", "0");
    		
    		xfoldilldisabilityMapper.updatetbprisonerbasecrime(map);
    		
    		xfoldilldisabilityMapper.updatetbxfsentencealteration(map);
    		
    	}else if(map.get("oidtype").equals("病")){
    		
    		map.put("ILLPRISONER", "0");
    		
    		map.put("ILLTYPE", "");
    		
    		map.put("ISILL", "0");
    		
    		xfoldilldisabilityMapper.updatetbprisonerbasecrime(map);
    		
    		xfoldilldisabilityMapper.updatetbxfsentencealteration(map);
    		
    	}else if(map.get("oidtype").equals("残")){
    		
    		map.put("DEFORMITYPRISONER", "0");
    		
    		map.put("DISABILITYPE", "");
    		
    		map.put("ISDISABILITY", "0");
    		
    		xfoldilldisabilityMapper.updatetbprisonerbasecrime(map);
    		
    		xfoldilldisabilityMapper.updatetbxfsentencealteration(map);
    		
    	}
    	
    	return null;
    }
    /**
     * 修改老病残补录状态：0无效，1有效，2撤销，同时保存撤销时间和撤销原因
     */
	@Override
	public int updateRevokeOldIllDisabilityStatus(Map<String, Object> map) {
		return  xfoldilldisabilityMapper.updateOldIllDisabilityStatus(map);
	}
	/**
     * 新增和修改同步更新tbprisoner_base_crime和tbxf_sentencealteration（表新方法）
     */
	@Override
	public int tongbuBaseCrimeAndSentenceAlteration(String crimid) {
		
		Map<String,Object> info = MapUtil.convertKey2LowerCase(xfoldilldisabilityMapper.getOldSickDisableInfoByCrimid(crimid));
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("crimid", crimid);

		
		String oldtype = StringNumberUtil.getStrFromMap("oldtype", info);
		String olddetail = StringNumberUtil.getStrFromMap("olddetail", info);
		paramap.put("laofan", 0);
		paramap.put("laofantype", "");
		paramap.put("laofandetail", "");
		if(StringNumberUtil.notEmpty(oldtype)){
			paramap.put("laofan", 1);
			if(oldtype.indexOf("一级老犯") > -1){
				paramap.put("laofantype", "一级老犯");
			}else if(oldtype.indexOf("二级老犯") > -1){
				paramap.put("laofantype", "二级老犯");
			}else{
				paramap.put("laofantype", "老犯");
			}
			if(StringNumberUtil.notEmpty(olddetail)){
				String[] tempArr = olddetail.split("；");
				for(int i=0; i< tempArr.length; i++){
					String[] temp2Arr = tempArr[i].split("@");
					if(temp2Arr[0].equals("1")){
						paramap.put("laofandetail", temp2Arr[1]);
						break;
					}
				}
			}
		}
		
		
		
		
		String illtype = StringNumberUtil.getStrFromMap("illtype", info);
		String illdetail = StringNumberUtil.getStrFromMap("illdetail", info);
		paramap.put("binfan", 0);
		paramap.put("binfantype", "");
		paramap.put("binfandetail", "");
		if(StringNumberUtil.notEmpty(illtype)){
			paramap.put("binfan", 1);
			if(illtype.indexOf("一级病犯") > -1){
				paramap.put("binfantype", "一级病犯");
			}else if(illtype.indexOf("二级病犯") > -1){
				paramap.put("binfantype", "二级病犯");
			}else{
				paramap.put("binfantype", "病犯");
			}
			if(StringNumberUtil.notEmpty(illdetail)){
				String[] tempArr = illdetail.split("；");
				for(int i=0; i< tempArr.length; i++){
					String[] temp2Arr = tempArr[i].split("@");
					if(temp2Arr[0].equals("2")){
						paramap.put("binfandetail", temp2Arr[1]);
						break;
					}
				}
			}
		}
		
		
		String disabilitytype = StringNumberUtil.getStrFromMap("disabilitytype", info);
		String disabilitydetail = StringNumberUtil.getStrFromMap("disabilitydetail", info);
		paramap.put("canfan", 0);
		paramap.put("canfantype", "");
		paramap.put("canfandetail", "");
		if(StringNumberUtil.notEmpty(disabilitytype)){
			paramap.put("canfan", 1);
			if(disabilitytype.indexOf("一级残犯") > -1){
				paramap.put("canfantype", "一级残犯");
			}else if(disabilitytype.indexOf("二级残犯") > -1){
				paramap.put("canfantype", "二级残犯");
			}else{
				paramap.put("canfantype", "残犯");
			}
			if(StringNumberUtil.notEmpty(disabilitydetail)){
				String[] tempArr = disabilitydetail.split("；");
				for(int i=0; i< tempArr.length; i++){
					String[] temp2Arr = tempArr[i].split("@");
					if(temp2Arr[0].equals("3")){
						paramap.put("canfandetail", temp2Arr[1]);
						break;
					}
				}
			}
		}
		
		
		
		//更新tbxf_sentencealteration
		xfoldilldisabilityMapper.updateSentenceOldIllDisability(paramap);
		
		//更新tbprisoner_base_crime
		xfoldilldisabilityMapper.updateBaseCrimeOldIllDisability(paramap);
		
		return 1;
		
//		return xfoldilldisabilityMapper.tongbuBaseCrimeAndSentenceAlteration(map);
	}

	@Override
	public List<Map<String, Object>> getcontrolsymbolByCrimid(String crimid) {
		return xfoldilldisabilityMapper.getcontrolsymbolByCrimid(crimid);
	}


}
