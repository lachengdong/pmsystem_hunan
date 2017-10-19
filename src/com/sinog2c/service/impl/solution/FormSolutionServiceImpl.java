package com.sinog2c.service.impl.solution;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.solution.FormSolutionDetailMapper;
import com.sinog2c.dao.api.solution.FormSolutionMapper;
import com.sinog2c.dao.api.solution.FormSqlGroupMapper;
import com.sinog2c.dao.api.system.SystemLogMapper;
import com.sinog2c.model.solution.FormSolution;
import com.sinog2c.model.solution.FormSolutionDetail;
import com.sinog2c.model.solution.FormSqlGroup;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.solution.FormSolutionService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("formSolutionService")
public class FormSolutionServiceImpl extends ServiceImplBase implements  FormSolutionService{
	@Autowired
	private SystemLogMapper systemLogMapper;
	@Autowired
	private FormSolutionMapper formSolutionMapper;
	@Autowired
	private FormSolutionDetailMapper solutionDetailMapper;
	@Autowired
	private FormSqlGroupMapper sqlGroupMapper;
	/*
	public List<Map> getTreeDataOfSolution(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(formSolutionMapper.getTreeDataOfSolution(map));
	}
*/

	@Override
	@Transactional
	public int add(FormSolution solution, SystemUser operator) {
		int result = formSolutionMapper.insertSelective(solution);
		return result;
	}

	@Override
	@Transactional
	public int add(Map<String, Object> map, SystemUser operator) {
		int result = formSolutionMapper.insertByMap(map);
		return result;
	}

	@Override
	public int countAll() {
		int result = formSolutionMapper.countAll();
		return result;
	}

	@Override
	public int countByCondition(Map<String, Object> map) {
		int result = formSolutionMapper.countByCondition(map);
		return result;
	}

	@Override
	@Transactional
	public int delete(FormSolution solution, SystemUser operator) {
		String solutionid = solution.getSolutionid();
		return delete(solutionid, operator);
	}

	@Override
	@Transactional
	public int delete(String solutionid, SystemUser operator){
		int result = formSolutionMapper.deleteById(solutionid);
		SystemLog log = new SystemLog();
        log.setOpid(operator.getUserid());
        log.setOpname(operator.getName());
        log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
        log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
        log.setLogtype("查询方案操作");
        log.setOpaction("查询方案删除操作");
        log.setOpcontent("查询方案信息删除,solutionid="+solutionid);
        log.setOrgid(operator.getOrgid());
        log.setStatus(1);
        log.setRemark("操作成功!");
        systemLogMapper.insertSelective(log);
		return result;
	}

	@Override
	public FormSolution getById(String solutionid) {
		FormSolution result = formSolutionMapper.selectById(solutionid);
		return result;
	}

	@Override
	public List<FormSolution> listAll() {
		List<FormSolution> result = formSolutionMapper.listAll();
		return result;
	}

	@Override
	public List<FormSolution> listByCondition(Map<String, Object> map) {
		List<FormSolution> result = formSolutionMapper.listByCondition(map);
		return result;
	}

	@Override
	public List<Map<String, Object>> listMapByPage(Map<String, Object> map) {
		List<Map<String, Object>> result = formSolutionMapper.listMapByPage(map);
		return result;
	}
	@Override
	public List<FormSolution> getTreeDataOfSolution(Map<String, Object> map) {
		List<FormSolution> result =  formSolutionMapper.getTreeDataOfSolution(map);
		return result;
	}

	@Override
	@Transactional
	public int update(Map<String, Object> map, SystemUser operator) {
		int result = formSolutionMapper.updateByMap(map);
		return result;
	}

	@Override
	@Transactional
	public int update(FormSolution solution, SystemUser operator) {
		int result = formSolutionMapper.updateByIdSelective(solution);
		return result;
	}

	@Override
	@Transactional
	public int saveSolutionScheme(Map<String, Object> map, SystemUser operator) {
		Object operateObj = map.get("operate");
		SystemLog log = new SystemLog();
        log.setOpid(operator.getUserid());
        log.setOpname(operator.getName());
        log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
        log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
        log.setLogtype("查询方案操作");
        log.setOpaction("查询方案新增/修改操作");
        log.setOpcontent("查询方案信息新增/修改操作,solutionid="+map.get("solutionid")+";solutionname="+map.get("solutionname"));
        log.setOrgid(operator.getOrgid());
        log.setStatus(1);
        log.setRemark("操作成功!");
		systemLogMapper.insertSelective(log);

		if(StringNumberUtil.notEmpty(operateObj)){
			if("new".equals(operateObj.toString())){
				map.put("createmender", operator.getUserid());
				return formSolutionMapper.insertByMap(map);
			}else if("edit".equals(operateObj.toString())){
				map.put("updatemender", operator.getUserid());
				return formSolutionMapper.updateByMap(map);
			}
		}
		return 0;
	}
	
	/**
	 * 拷贝方案
	 * @param map
	 * @param operator
	 * @return
	 */
	@Override
	@Transactional
	public boolean copySolution(Map<String, Object> map, SystemUser operator) {
		//
		boolean result = false;
		//
		if(null == operator){
			return result;
		}
		// 要拷贝的方案
		String fromid = StringNumberUtil.toString(map.get("fromid"));
		// 拷贝到的父方案(目录)ID,可为空
		String topid = StringNumberUtil.toString(map.get("topid"));
		// 新的方案名字
		String toname = StringNumberUtil.toString(map.get("toname"));
		//
		try {
			result = copySingleSolution(fromid, topid, toname, operator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SystemLog log = new SystemLog();
        log.setOpid(operator.getUserid());
        log.setOpname(operator.getName());
        log.setLoginmac("客户端IP:"+IPUtil.getLocalIP());
        log.setLoginip("客户端IP:" + IPUtil.getLocalIP());
        log.setLogtype("查询方案操作");
        log.setOpaction("查询方案拷贝操作");
        log.setOpcontent("查询方案拷贝操作,源方案ID ="+fromid+";拷贝到="+topid+";新方案名="+toname);
        log.setOrgid(operator.getOrgid());
        log.setStatus(1);
        log.setRemark("操作成功!");
        systemLogMapper.insertSelective(log);
		return result;
	}
	// 拷贝单个方案
	private boolean copySingleSolution(String fromid, String topid, String toname, SystemUser operator) throws Exception {
		boolean result = false;
		//
		if(StringNumberUtil.isEmpty(fromid)){
			return result;
		} else {
			fromid = fromid.trim();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("solutionid", fromid);
		// 1. 获取源方案
		FormSolution fromSolution = getById(fromid);
		if(null == fromSolution){
			return result;
		}
		// 2. 获取方案细节组
		List<FormSolutionDetail> detailList = solutionDetailMapper.listByCondition(map);
		
		// 3. 一次性获取SQL组
		List<FormSqlGroup> oldGroupList = sqlGroupMapper.listByCondition(map);
		
		
		// 4. 遍历,归类 ? 不需要这一步。。。 
		
		//
		// 5. 拷贝. 获取ID
		// 5.1 拷贝方案
		FormSolution newSolution = cloneSolution(fromSolution, fromid, topid, toname, operator);
		if(null == newSolution){
			return result;
		}
		
		// 5.2 拷贝细节
		List<FormSolutionDetail> newDetailList = new ArrayList<FormSolutionDetail>();
		List<FormSqlGroup> newSQLGroupList = new ArrayList<FormSqlGroup>();
		//
		Iterator<FormSolutionDetail> iteratorD = detailList.iterator();
		while (iteratorD.hasNext()) {
			FormSolutionDetail oldDetail = (FormSolutionDetail) iteratorD.next();
			//
			FormSolutionDetail newDetail = cloneDetail(oldDetail, newSolution, operator);
			//
			if(null != newDetail){
				newDetailList.add(newDetail);
			}
			//
			// 5.2.1 拷贝细节的SQL组
			List<FormSqlGroup> currentSQLList = cloneSQLGroups(oldGroupList, oldDetail, newDetail, operator);
			//
			if(null != currentSQLList){
				newSQLGroupList.addAll(currentSQLList);
			}
		}
		
		// 6. 保存
		// 6.1 保存方案
		int rowf = formSolutionMapper.insert(newSolution);
		if(rowf < 1){
			return result;
		}
		// 6.2 保存细节
		Iterator<FormSolutionDetail> iteratorDN = newDetailList.iterator();
		while (iteratorDN.hasNext()) {
			FormSolutionDetail newDetail = (FormSolutionDetail) iteratorDN.next();
			if(null != newDetail){
				solutionDetailMapper.insert(newDetail);
			}
		}
		// 6.3 保存细节的SQL
		Iterator<FormSqlGroup> iteratorG = newSQLGroupList.iterator();
		while (iteratorG.hasNext()) {
			FormSqlGroup newSqlGroup = (FormSqlGroup) iteratorG.next();
			if(null != newSqlGroup){
				sqlGroupMapper.insert(newSqlGroup);
			}
		}
		// 如果不抛异常,则认为拷贝成功
		result = true;
		
		// 返回结果
		return result;
	}


	// 克隆方案对象
	private FormSolution cloneSolution(FormSolution fromSolution,
			String fromid, String topid, String toname, SystemUser operator) {
		//
		if(null == operator){
			return null;
		}
		if(null == fromSolution){
			return null;
		}
		//
		FormSolution newSolution = null;
		try {
			newSolution = fromSolution.clone();
			//
			String newid = getNextSolutionUniformId();
			//
			newSolution.setSolutionid(newid);
			newSolution.setSolutionpid(topid);
			if(StringNumberUtil.notEmpty(toname)){
				newSolution.setSolutionname(toname);
			}
			//
			String userid = operator.getUserid();
			newSolution.setCreatemender(userid);
			newSolution.setUpdatemender(userid);
			newSolution.setCreatetime(new Date());
			newSolution.setUpdatetime(new Date());
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return newSolution;
	}

	// 克隆细节对象
	private FormSolutionDetail cloneDetail(FormSolutionDetail oldDetail,
			FormSolution newSolution, SystemUser operator) {
		//
		if(null == operator){
			return null;
		}
		if(null == newSolution){
			return null;
		}
		if(null == oldDetail){
			return null;
		}
		//
		FormSolutionDetail newDetail = null;
		try {
			newDetail = oldDetail.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//
		String solutionid = newSolution.getSolutionid();
		String newid = getNextSolutionUniformId();
		//
		newDetail.setSolutionid(solutionid);
		newDetail.setDetailid(newid);
		//
		String userid = operator.getUserid();
		newDetail.setCreatemender(userid);
		newDetail.setUpdatemender(userid);
		newDetail.setCreatetime(new Date());
		newDetail.setUpdatetime(new Date());
		//
		return newDetail;
	}
	

	// 克隆 oldDetail 对应的 FormSqlGroup
	private List<FormSqlGroup> cloneSQLGroups(List<FormSqlGroup> oldGroupList,
			FormSolutionDetail oldDetail, FormSolutionDetail newDetail,
			SystemUser operator) {
		//
		if(null == operator){
			return null;
		}
		if(null == oldGroupList){
			return null;
		}
		if(null == oldDetail){
			return null;
		}
		if(null == newDetail){
			return null;
		}
		//
		String solutionid = newDetail.getSolutionid();
		String detailid = newDetail.getDetailid();
		String oldDetailid = oldDetail.getDetailid();
		String userid = operator.getUserid();
		//
		List<FormSqlGroup> currentSQLList = new ArrayList<FormSqlGroup>();
		//
		Iterator<FormSqlGroup> iteratorO = oldGroupList.iterator();
		while (iteratorO.hasNext()) {
			FormSqlGroup oldSqlGroup = (FormSqlGroup) iteratorO.next();
			//
			String detailG = oldSqlGroup.getDetailid();
			//
			if(null != detailG && detailG.equalsIgnoreCase(oldDetailid)){
			} else {
				// 不符合,下一个
				continue;
			}
			//
			try {
				FormSqlGroup sqlGroupN = oldSqlGroup.clone();
				//
				String newid = getNextSolutionUniformId();
				//
				sqlGroupN.setSolutionid(solutionid);
				sqlGroupN.setDetailid(detailid);
				sqlGroupN.setSqlgroupid(newid);
				//
				sqlGroupN.setCreatemender(userid);
				sqlGroupN.setUpdatemender(userid);
				sqlGroupN.setCreatetime(new Date());
				sqlGroupN.setUpdatetime(new Date());
				//
				currentSQLList.add(sqlGroupN);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			//
		}
		
		//
		return currentSQLList;
	}
	
	// 获取下一个ID， 方案，细节，SQL组统一ID
	private String getNextSolutionUniformId() {
		//
		int random = new Random().nextInt();
		int id = formSolutionMapper.getNextIdNoCache(random);
		return ""+id;
	}
	
	@Override
	public List<FormSolution> getSolutionSchemeTreeBySolutionpname(Map<String, Object> map) {
		List<FormSolution> result =  formSolutionMapper.getSolutionSchemeTreeBySolutionpname(map);
		return result;
	}
}
