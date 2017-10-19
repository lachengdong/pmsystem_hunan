package com.sinog2c.service.impl.oa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.oa.OaAppRegMapper;
import com.sinog2c.model.oa.OaAppReg;
import com.sinog2c.service.api.oa.OaAppRegService;
import com.sinog2c.service.api.system.SystemUserService;
/**
 * @author 肖岩
 * date 2015/08/31
 * function 请假单的业务处理实现类
 * */
@Service("oaAppRegService")
public class OaAppRegServiceImpl implements OaAppRegService{
	@Autowired
	private OaAppRegMapper oaAppRegMapper;
	@Autowired
	private SystemUserService systemuserService;
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 新增方法，在流程执行完成后进行保存
	 * oasickleave 请假申请实体类
	 * */
	@Override
	public int save(OaAppReg oaappreg){
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		oaappreg.setUuid(uuid);
		int i = oaAppRegMapper.insertSelective(oaappreg);
		return i;
	}
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询请假单信息列表方法实现类
	 * map 请求参数
	 * */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List getAppRegListInfo(HttpServletRequest request) {
		Map<String,Object> paramap = new HashMap<String,Object>();
		String tempid = request.getParameter("tempid");//模版编号
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		paramap.put("tempid", tempid);
		paramap.put("key", key);
		paramap.put("sortField", sortField);
		paramap.put("sortOrder", sortOrder);
		paramap.put("start", start);
		paramap.put("end", end);
		List list = this.oaAppRegMapper.getAppRegListByCondition(paramap);
		return list;
	}
	
	/**
	 * @author 肖岩
     * date 2015/09/09
	 * 按id查看请假单信息实现方法
	 * request 请求参数
	 * */
	@Override
	public int getCount(HttpServletRequest request) {
		String key = request.getParameter("key");
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("key", key);
		return this.oaAppRegMapper.getAppRegCount(paramap);
	}
	/**
	 * @author 肖岩
     * date 2015/09/09
	 * 按id查看应聘人员登记表详细信息实现方法
	 * uuid uuid
	 * */
	public OaAppReg getAppRegInfoByUuid(String uuid) {
		OaAppReg oaappreg = oaAppRegMapper.getAppRegInfoByUuid(uuid);
		return oaappreg;
	}
	
	/**
	 * @author 肖岩
     * date 2015/09/09
	 * 按uuid删除应聘人员登记表实现方法
	 * uuid uuid
	 * */
	@Override
	public int deleteAppReg(String uuids) {
		String[] uuid = uuids.split(",");
		int a =0;
		for (int i = 0; i < uuid.length; i++) {
			a  = a+oaAppRegMapper.deleteAppReg(uuid[i]);
		}
		return a;
	}
	
	/**
	 * @author 肖岩
     * date 2015/09/09
	 * 按uuid更新应聘人员登记表实现方法
	 * oaappreg 应聘人员登记表实体
	 * */
	@Override
	public int update(OaAppReg oaappreg) {
		return oaAppRegMapper.updateAppReg(oaappreg);
	}
}
