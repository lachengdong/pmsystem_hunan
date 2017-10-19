package com.sinog2c.service.impl.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommutationReferenceMapper;
import com.sinog2c.dao.api.system.TbsysCodeMapper;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sun.xml.internal.fastinfoset.sax.Properties;

@Service("systemCodeService")
public class SystemCodeServiceImpl extends ServiceImplBase implements
		SystemCodeService {
	@Autowired
	private TbsysCodeMapper tbsysCodeMapper;
	@Autowired
	private TbxfCommutationReferenceMapper tbxfCommutationReferenceMapper;

	@Override
	public String getcodeValue(String codeType) {
		List<TbsysCode> codeList = tbsysCodeMapper.selectValueByCodeType(codeType);
		String tempStr = "";
		if (!codeList.isEmpty() && codeList != null) {
			for (TbsysCode code : codeList) {
				tempStr += ("[[" + code.getScearch() + "]]" + code.getName() + "||");
			}
			tempStr = tempStr.substring(0, tempStr.length() - 2);
		}
		return tempStr;
	}
	
	@Override
	public List<TbsysCode> selectValueByCodeType(String codeType){
		return tbsysCodeMapper.selectValueByCodeType(codeType);
	}

	
	
	public List<Map> selectValueByMap(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbsysCodeMapper.selectValueByMap(map));
	}

	/**
	 * 功能描述：通过一个Code类型获取Code值
	 * 
	 */
	@Override
	public List ajaxGetcodeValueForOpinion(String codeType) {
		return tbsysCodeMapper.selectValueByCodeType(codeType);
	}

	@Override
	public List ajaxGetcodeValueForOpinion1(String codeType) {
		return tbsysCodeMapper.selectValueByCodeType1(codeType);
	}
	/**
	 * 部门 list 下拉
	 */
	@Override
	public String getCodeByOrgList(List<SystemOrganization> oList) {
		String tempStr = "";
		if (!oList.isEmpty() && oList != null) {
			for (SystemOrganization org : oList) {
				tempStr += ("[[" + org.getOrgid() + "]]" + org.getName() + "||");
			}
			tempStr = tempStr.substring(0, tempStr.length() - 2);
		}
		return tempStr;
	}
	
	public String getCodeValueByCodeTypeAndCodeId(Map map){
		return tbsysCodeMapper.getCodeValueByCodeTypeAndCodeId(map);
	}
	
	public String getCodeValueByStrings(String codetype,String codeid){
		return tbsysCodeMapper.getCodeValueByStrings(codetype,codeid);
	}


	@Override
	public List<TbsysCode> listByCodetype(String codetype) {
		List<TbsysCode> result = tbsysCodeMapper.listByCodetype(codetype);
		return result;
	}
	
	@Override
	public List<Map> listByMap(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbsysCodeMapper.listByMap(map));
	}
	
	@Override
	public Map getDataByMap(Map map){
		return MapUtil.turnKeyToLowerCase(tbsysCodeMapper.getDataByMap(map));
	}
	
	@Override
	public int countAll() {
		int num = tbsysCodeMapper.countAll();
		return num;
	}
	@Override
	public List<TbsysCode> listAll() {
		List<TbsysCode> result = tbsysCodeMapper.listAll();
		return result;
	}

	@Override
	public int countByCondition(Map<String, Object> map) {
		int num = tbsysCodeMapper.countByCondition(map);
		return num;
	}
	
	@Override
	public List<TbsysCode> listByCondition(Map<String, Object> map) {
		map = processMapPage(map);
		List<TbsysCode> result = tbsysCodeMapper.listByCondition(map);
		return result;
	}
	
	@Override
	public List<TbsysCode> listAllByCondition(Map<String, Object> map) {
		List<TbsysCode> result = tbsysCodeMapper.listByCondition(map);
		return result;
	}
	
	@Override
	public int insertByMap(Map<String, Object> map) {
		int num = tbsysCodeMapper.insertByMap(map);
		return num;
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
		int num = tbsysCodeMapper.updateByMap(map);
		return num;
	}

	public List<HashMap<Object, Object>> getAmplitudeData(String crimid) {
		return tbxfCommutationReferenceMapper.getAmplitudeData(crimid);
	}



	@Override
	public Object selectValueByCodeTypeAndCodeid(Map map) {
		return tbsysCodeMapper.selectValueByCodeTypeAndCodeid(map);
	}

	@Override
	public List<TbsysCode> getCodesByMap(Map map){
		return tbsysCodeMapper.getCodesByMap(map);
	}

	@Override
	public List<Map> getCaseTypeMap() {
		return MapUtil.turnKeyToLowerCaseOfList(tbsysCodeMapper.getCaseTypeMap());
	}

	@Override
	public List<Map> getCodeByCodeType(String codeType,String codeid,String pcodeid) {
		
		return MapUtil.turnKeyToLowerCaseOfList(tbsysCodeMapper.getCodeByCodeTypeMapper(codeType,codeid,pcodeid,null));
	}

	@SuppressWarnings("all")
	public List<Map> getCodeByCode(HttpServletRequest request,SystemUser user) {
		//查询出自己的当前部门对应code
		String codetype = request.getParameter("codeType");
		String departid = user.getDepartid();
		String scearch = request.getParameter("scearch");
		java.util.Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");//获取省份代码
		
		List<Map> listMaps = tbsysCodeMapper.getCodeByCodeMapper(codetype, null, null,departid,scearch);
		if (listMaps.size() > 0) {
			return MapUtil.turnKeyToLowerCaseOfList(listMaps);
		}else{
			listMaps = tbsysCodeMapper.getCodeByCodeMapper(codetype, null, null,provincecode,scearch);
			return MapUtil.turnKeyToLowerCaseOfList(listMaps);
		}
	}
	
	@SuppressWarnings("all")
	public List<Map> getAllSFSelectImpl(HttpServletRequest request,SystemUser user){
		
		String codetype = request.getParameter("codetype");//code类型
		String pcodeid = request.getParameter("pcodeid");//父级code代码
		
		Map map = new HashMap();
		map.put("codetype", codetype);
		map.put("pcodeid", pcodeid);
		
		List<Map> listMaps = tbsysCodeMapper.getAllSFSelectImpl(map);
		return MapUtil.turnKeyToLowerCaseOfList(listMaps);
	}

	@Override
	public Map getSJCodeidByXjCodeid(String codeid,String type) {
		//通过下级查询上级的codeid
		Map map = tbsysCodeMapper.getSJCodeidByXjCodeid(codeid,type);
		return map;
	}

	@Override
	public List<Map> getSanLeiType(HttpServletRequest request) {
		
		List<Map> listMaps = tbsysCodeMapper.getSanLeiType();
		return MapUtil.turnKeyToLowerCaseOfList(listMaps);
	}
}
