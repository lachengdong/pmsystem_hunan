package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.system.SystemOrganizationMapper;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("systemOrganizationService")
public class SystemOrganizationServiceImpl extends ServiceImplBase implements SystemOrganizationService {

			@Autowired
			private SystemOrganizationMapper organizationMapper = null;
			@Autowired
			private SystemCodeService systemcodeservice ;
			public SystemOrganizationMapper getSystemOrganizationMapper() {
				return organizationMapper;
			}
			public void setSystemOrganizationMapper(
					SystemOrganizationMapper organizationMapper) {
				this.organizationMapper = organizationMapper;
			}

			@Override
			@Transactional
			public int add(SystemOrganization org, SystemUser operator){

				if(null == org){
					return 0;
				}
				int rows = organizationMapper.insertSelective(org);
				return rows;
			}

			@Override
			@Transactional
			public int insertWithId(SystemOrganization org, SystemUser operator) {
				if(null == org){
					return 0;
				}
				int rows = organizationMapper.insertSelective(org);
				return rows;
			}
			@Override
			@Transactional
			public int insertByMap(Map<String, Object> map, SystemUser operator) {
				if(null == map){
					return 0;
				}
				if(null != operator){
					map.put("opid", operator.getUserid());//
				}
				Object orgid = map.get("orgid");
				
				if(null == orgid){ // map必须进行数据校验
					return 0;
				}
				//
				Object sn = map.get("sn");
				sn = StringNumberUtil.parseShort(""+sn, 9999);
				map.put("sn", sn);
				//
				//
				int rows = organizationMapper.insertByMap(map);
				return rows;
			}


			@Override
			@Transactional
			public int update(SystemOrganization org, SystemUser operator) {
				if(null == org){
					return 0;
				}
				int orgult = organizationMapper.update(org);
				return orgult;
			}
			@Override
			@Transactional
			public int updateByMap(Map<String, Object> map, SystemUser operator) {
				if(null == map){
					return 0;
				}
				int rows = organizationMapper.updateByMap(map);
				return rows;
			}


			@Override
			@Transactional
			public int delete(String orgId, SystemUser operator) {
				if(null == orgId){
					return 0;
				}
				// 权限和日志,都可以在这里做...
				// 考虑到删除业务使用量不大, 不必考虑封装数据库SQL
				// 提取旧元素, 开始事务, 插入历史表, 删除现有记录, 提交事务
				//
				SystemOrganization org = getByOrganizationId(orgId);
				if(null == org){
					return 0;
				}
				// 系统原生的不允许删除,需要删除,请联系 DBA
				int opflg = org.getOpflag();
				if(1 != opflg){
					return -1;
				}
				// 如果不是叶子节点, 并且有子元素, 则需要拒绝删除 // 或者提供批量删除的接口
				//String orgid = org.getResid();
				
				// 开始事务
				// ...
				// 插入历史表
				int hisrows = organizationMapper.insertToHistory(org);
				if(1 == hisrows){
					// 删除资源表的记录
					int orgrows = organizationMapper.delete(org);
					if(1 == orgrows){
						// 记录日志
						// ...
						// 提交事务
						return 1;
					} else {
						throw new RuntimeException("删除记录失败!orgId="+orgId);
					}
				}
				//
				return 0;
			}
			@Override
			public SystemOrganization getByOrganizationId(String orgid) {
				if(null == orgid){
					return null;
				}
				SystemOrganization org = organizationMapper.getByOrgId(orgid);
				//
				return org;
			}

			@Override
			public int countAll() {
				return organizationMapper.countAll();
			}
			@Override
			public List<SystemOrganization> listByOrganizationPid(String porgid,String unitlevel) {
				if(null == porgid){
					return null;
				}
				List<SystemOrganization> organizations = organizationMapper.listByPorgid(porgid,unitlevel);
				return organizations;
			}
			
			@Override
			public List<SystemOrganization> listByLevel(String unitlevel) {
				if(null == unitlevel){
					return null;
				}
				List<SystemOrganization> organizations = organizationMapper.listByLevel(unitlevel);
				//
				List<SystemOrganization> result = new ArrayList<SystemOrganization>();
				//
				if(null != organizations){
					result.addAll(organizations);
					// 添加 -1
					SystemOrganization unsetOrg = new SystemOrganization();
					unsetOrg.setOrgid("-1");
					unsetOrg.setName("--未设置--");
					//
					result.add(unsetOrg);
				}
				
				return result;
			}
			
			@Override
			public List<Map<String,Object>> listMapByLevel(String unitlevel,String porgid) {
				if(null == unitlevel){
					return null;
				}
				List<Map<String,Object>> organizations = MapUtil.convertKeyList2LowerCase(organizationMapper.getOrgMapByLevel(unitlevel,porgid));
				//
				List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
				//
				if(null != organizations){
					result.addAll(organizations);
					// 添加 -1
//					Map<String,Object> unsetOrg = new HashMap<String,Object>();
//					unsetOrg.put("orgid", "-1");
//					unsetOrg.put("name", "--未设置--");
//					//
//					result.add(unsetOrg);
				}
				
				return result;
			}

			@Override
			public List<SystemOrganization> selectAll() {
				List<SystemOrganization> list =  organizationMapper.selectAll();
				return list;
			}
			@Override
			public List<SystemOrganization> getAllByPage(int pageIndex,	int pageSize){
				// 计算页码
				int start = pageIndex * pageSize + 1;
				int end = start + pageSize -1;

				List<SystemOrganization> list =  organizationMapper.getAllByPage(start, end);
				return list;
			}
			@Override
			public List<HashMap> getOrgWithUserByOrgId(HashMap map) {
				
				return organizationMapper.getOrgWithUserByOrgId(map);
			}
			
			@Override
			public List<Map> getOrganizationByuserid(Map map) {
				return MapUtil.turnKeyToLowerCaseOfList(organizationMapper.getOrganizationByuserid(map));
			}
			
			@Override
			public List<SystemOrganization> getByPorgId(String porgid) {
				
				return organizationMapper.getByPorgId(porgid);
			}
			
			@Override
			public List<SystemOrganization> getSonPrisonByPorgId(String porgid) {
				return organizationMapper.getSonPrisonByPorgId(porgid);
			}
			@Override
			public List<SystemOrganization> getGrandSonPrisonByPorgId(
					String porgid) {
				return organizationMapper.getGrandSonPrisonByPorgId(porgid);
			}
			@Override
			public SystemOrganization getPrisonarealevelByPorgId(String porgid){
				return organizationMapper.getPrisonarealevelByPorgId(porgid);
			}
			@Override
			public List<SystemOrganization> selectJianQuByPorgid(String porgid,String key) {
				return organizationMapper.selectJianQuByPorgid(porgid,key);
			}
			@Override
			public List<Map> getDepartById(Map map) {
				return organizationMapper.getDepartById(map);
			}
			@Override
			public List<Map> getCrimidStatus(String str) {
				return organizationMapper.getCrimidStatus(str);
			}
			@Override
            public String getDep(Map map) {
                return organizationMapper.getDep(map);
            }
			@Override
			public List<HashMap<String,Object>> getExportdepart(String departid) {
				return organizationMapper.getExportdepart(departid);
			}
			@Override
			public SystemOrganization getProvinceCode(String orgid) {
				return organizationMapper.getProvinceCode(orgid);
			}
			@Override
			public String getNameByid(String str) {
				return organizationMapper.getNameByid(str);
			}
			@Override
			public List<SystemOrganization> getDeptInfo(String str) {
				 return organizationMapper.getDeptInfo(str);
			}
			@Override
			public int getNumByid(Map map) {
				return organizationMapper.getNumByid(map);
			}
			@Override
			public int getNumByDepartid(String userdepartid,String porgid) {
				return organizationMapper.getNumByDepartid(userdepartid,porgid);
			}
			@Override
			public SystemOrganization getOrginfoByOrgid(String orgid) {
				return organizationMapper.getOrginfoByOrgid(orgid);
			}
			@SuppressWarnings("all")
			public List<Map> getSjDeptInfo(String orgid, SystemUser user) {
				List listMaps = new ArrayList();
				try {
					Map map = new HashMap();
					map.put("orgid", orgid);
					listMaps = organizationMapper.getSjDeptInfo(map);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return listMaps;
			}
			
			@Override
			public List<Map> getCodeListByCodeType(Map map) {
				return organizationMapper.getCodeListByCodeType(map);
			}
			@SuppressWarnings("all")
			@Override
			public String getcodeValue(SystemUser user) {
				String value="";
				try{
					Map map=new HashMap();
					map.put("orgid", user.getDepartid());
					map.put("unitlevel", "4");//监区
					map.put("funitlevel", "11");//分监区
					List<SystemOrganization> list=organizationMapper.listByLevelByMap(map);
					value = systemcodeservice.getCodeByOrgList(list);
				}catch(Exception e){
					e.printStackTrace();
				}
				return value;
			}
			@Override
			public SystemOrganization getDepartidByName(Map map) {
				return organizationMapper.getDepartidByName(map);
			}
			
			//start add by blue_lv 2015-07-12
			@Override
			public List<SystemOrganization> getAllOrginfoAndmember() {
				return this.organizationMapper.getAllOrginfoAndmember();
			}

			@Override
			public List<SystemOrganization> getAllOrginInfo() {
				return this.organizationMapper.getAllOrginInfo();
			}
			//end add by blue_lv 2015-07-12
			
			
			@Override
			public List<Map> getOrginfoByFlowdraftid(Map<String, Object> map) {
				return organizationMapper.getOrginfoByFlowdraftid(map);
			}
			
			@Override
			public SystemOrganization getOrginfoByOrgnameAndDepartid(Map<String,Object> map){
				return organizationMapper.getOrginfoByOrgnameAndDepartid(map);
			}
			@Override
			public List<SystemOrganization> getJailidByProvinceid(
					Map<String, Object> paramap) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Map> getOrganizationByPorgid(Map map) {
				return MapUtil.turnKeyToLowerCaseOfList(organizationMapper.getOrganizationByPorgid(map));
			}
			
		}
