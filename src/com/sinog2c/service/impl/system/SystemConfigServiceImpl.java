package com.sinog2c.service.impl.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.SystemConfigBeanMapper;
import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;

@Service("systemConfigService")
public class SystemConfigServiceImpl extends ServiceImplBase implements SystemConfigService {

	@Resource
	private SystemConfigBeanMapper systemConfigMapper;
	
	@Override
	public int add(SystemConfigBean config, SystemUser operator) {
		if(null == config){
			return -1;
		}
		String departid = config.getDepartid();
		//
		if(null != operator){
			String opid = operator.getUserid();
			config.setOpid(opid);

			if (StringNumberUtil.isEmpty(departid)) {
				departid = operator.getDepartid();
				config.setDepartid(departid);
			}
		}
		//
		int result = systemConfigMapper.insertSelective(config);
		return result;
	}

	@Override
	public int delete(SystemConfigBean bean, SystemUser operator) {
		if(null == bean || null == operator){
			return 0;
		}
		int id = bean.getId();
		//
		int result = systemConfigMapper.deleteById(id);
		return result;
	}

	@Override
	public int update(SystemConfigBean bean, SystemUser operator) {
		if(null == bean || null ==operator){
			return 0;
		}
		//
		int result = systemConfigMapper.updateSelective(bean);
		return result;
	}
	
	@Override
	public int updateByMap(Map<String, Object> paraMap, SystemUser operator) {
		if(null == paraMap || null ==operator){
			return 0;
		}
		//
		int result = systemConfigMapper.updateByMap(paraMap);
		return result;
	}

	@Override
	public SystemConfigBean getById(int id) {
		SystemConfigBean systemConfig = systemConfigMapper.getById(id);
		return systemConfig;
	}
	
	@Override
	public int countAll() {
		int result = systemConfigMapper.countAll();
		return result;
	}
	
	@Override
	public List<SystemConfigBean> listAll() {
		List<SystemConfigBean> result = systemConfigMapper.listAll();
		return result;
	}


	@Override
	public List<SystemConfigBean> listAllByPage(int pageIndex, int pageSize, String sortField, String sortOrder) {

		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		if(null == sortField || sortField.trim().length() < 1){
			sortField = "NAME";
		}
		if(null == sortOrder || sortOrder.trim().length() < 1){
			sortOrder = "asc";
		}

		List<SystemConfigBean> result = systemConfigMapper.listAllByPage(start, end, sortField, sortOrder);
		return result;
	}


	@Override
	public int countBySearch(String key) {
		int result = systemConfigMapper.countBySearch(key);
		return result;
	}
	@Override
	public List<SystemConfigBean> search(String key, int pageIndex, int pageSize, String sortField, String sortOrder) {
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		if(null == key){
			//key = "";
		}

		if(null == sortField || sortField.trim().length() < 1){
			sortField = "NAME";
		}
		if(null == sortOrder || sortOrder.trim().length() < 1){
			sortOrder = "asc";
		}

		List<SystemConfigBean> result = systemConfigMapper.search(key, start, end, sortField, sortOrder);
		return result;
	}
	
	@Override
	public int countByCondition(Map<String, Object> paramMap, SystemUser user) {
		if(null == paramMap || null == user){
			return 0;
		}
		// 封装 paramMap数据
		paramMap = super.processMapPage(paramMap);
		//
		int result = systemConfigMapper.countByCondition(paramMap);
		//
		return result;
	}

	@Override
	public List<SystemConfigBean> listByCondition(Map<String, Object> paramMap, SystemUser user) {

		if(null == paramMap || null == user){
			return null;
		}
		// 封装 paramMap数据
		paramMap = super.processMapPage(paramMap);
		paramMap = processMapPage(paramMap);
		//
		List<SystemConfigBean> result = systemConfigMapper.listByCondition(paramMap);
		//
		return result;
	}

	// 监狱配置缓存
	private Map<String, List<SystemConfigBean>> JYConfig_CACHE = new HashMap<String, List<SystemConfigBean>>();
	@Override
	public List<SystemConfigBean> listJYConfig(SystemUser user) {
		List<SystemConfigBean> result = null;
		if(null == user){
			return result;
		}
		String departid = user.getDepartid();
		// TODO 应该怎么处理过期?
		result = JYConfig_CACHE.get(departid);
		if(null != result && !result.isEmpty()){
			return result;
		}
		//
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("catagory", SystemConfigBean.CATAGORY_JYCONFIG);
		paramMap.put("departid", departid);
		result = systemConfigMapper.listByCondition(paramMap);
		if(null != result && !result.isEmpty()){
			JYConfig_CACHE.put(departid, result);
		}
		//
		return result;
	}
	// 系统配置缓存
	private List<SystemConfigBean> SYSConfig_CACHE = null;
	@Override
	public List<SystemConfigBean> listSYSConfig() {
		// 缓存
		List<SystemConfigBean> result = SYSConfig_CACHE;
		if(null != result && !result.isEmpty()){
			return result;
		}
		// TODO 应该怎么处理过期?
		//
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("catagory", SystemConfigBean.CATAGORY_SYSTEM);
		result = systemConfigMapper.listByCondition(paramMap);

		if(null != result && !result.isEmpty()){
			SYSConfig_CACHE = result;
		}
		//
		return result;
	}
	@Override
	public SystemConfigBean getJYConfig(String name, SystemUser user) {
		List<SystemConfigBean> list = listJYConfig(user);
		return searchByName(list, name);
	}
	
	/**
     * 查询单个配置参数
     * @param name
     * @param user
     * @return
     */
    public SystemConfigBean getConfigByMap(Map<String,Object> map){
    	return systemConfigMapper.getConfigByMap(map);
    }
	
	@Override
	public SystemConfigBean getSYSConfig(String name) {
		List<SystemConfigBean> list = listSYSConfig();
		return searchByName(list, name);
	}
	
	private SystemConfigBean searchByName(List<SystemConfigBean> list, String name){
		SystemConfigBean result = null;
		if(StringNumberUtil.isEmpty(name)){
			return result;
		}
		if(null == list || list.isEmpty()){
			return result;
		}
		//
		Iterator<SystemConfigBean> iteratorC = list.iterator();
		while (iteratorC.hasNext()) {
			SystemConfigBean systemConfigBean = (SystemConfigBean) iteratorC.next();
			if(null == systemConfigBean){
				continue;
			}
			//
			String configName = systemConfigBean.getName();
			if(name == configName){
				result = systemConfigBean;
				break;
			}
		}
		return result;
	}

}
