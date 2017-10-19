package com.sinog2c.service.impl.khjc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.KhjcJiYaoMingXiMapper;
import com.sinog2c.model.khjc.KhjcJiYaoMingXi;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcJiYaoMingXiService;
import com.sinog2c.service.api.khjc.PublicBaseDocService;
import com.sinog2c.service.api.khjc.PublicToolMethodService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("khjcJiYaoMingXiService")
public class KhjcJiYaoMingXiServiceImpl extends ControllerBase implements KhjcJiYaoMingXiService{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PublicBaseDocService PublicBaseDocService;
	@Resource
	private PublicToolMethodService publicToolMethodService;
	@Autowired
	private KhjcJiYaoMingXiMapper khjcJiYaoMingXiMapper;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	
	/**
	 * 删除纪要明细/政策法规
	 */
	public String deleteJiYaoMingXiInfo(String id) {
		khjcJiYaoMingXiMapper.deleteByPrimaryKey(id);
		return null;
	}
	
	
	/**
	 * 获取纪要明细/政策法规列表
	 */
	public List<KhjcTbflowBaseDoc> getJiYaoMingXiDoc(int pageIndex,
			int pageSize, String key, String tempid, String crimid,
			String departid, String sortField, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 保存纪要明细/政策法规
	 */
	@Override
	public String saveJiYaoMingXiInfo(HttpServletRequest request,SystemUser user) {
		String returnValue = "保存成功！";
		String content = request.getParameter("content")==null?"":request.getParameter("content");//附件名称
		String templetid = request.getParameter("templetid")==null?"":request.getParameter("templetid");//类型
		String permissionid = request.getParameter("permissionid")==null?"":request.getParameter("permissionid");//发送至
		String doccontent = request.getParameter("doccontent")==null?"":request.getParameter("doccontent");//大字段
		Map<String,String> map = new HashMap<String, String>();
		
		//保存附件表信息，并返回附件表的主键
		String returnId = "";
		KhjcJiYaoMingXi docpojo = new KhjcJiYaoMingXi();//附件表对象
		if(null != content && !"".equals(content)){
			Map<String,String> map2 = new HashMap<String, String>();
			map.put("content", content);
			map.put("templetid", templetid);
			map.put("doccontent", doccontent);
			//保存附件表
			map2 = PublicBaseDocService.saveKhjcFlowBaseDoc(map, user);
			//如果附件表保存成功，则获取附件表的主键作为主表的主键进行保存
			if("success".equals(map2.get("state"))){
				//保存主表
				docpojo.setId(map2.get("id"));//主键
				docpojo.setSendto(permissionid);//发送给
				khjcJiYaoMingXiMapper.insert(docpojo);//插入操作
			}
		}else{
			returnValue = "未生成表单大字段信息！";
		}
		
		return returnValue;
	}
	
}
