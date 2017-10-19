package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


@Service("systemModelService")
public class SystemModelServiceImpl extends ServiceImplBase implements SystemModelService{
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;

	/**
	 * 获取所有数量
	 * @return 所有模板的数量
	 */
	public int countAll(){
		int count = tbsysTemplateMapper.countAll();
		return count;
	}
	
	/**
	 * 
	 * @return 所有模板
	 */
	public List<TbsysTemplate> listAll(){
		List<TbsysTemplate> list = tbsysTemplateMapper.listAll();
		return list;
	}
	/**
	 * 获取当前页面数据（分页）
	 * 
	 * @author liucy 2014-7-17 10:12:26
	 */
	@Override
	public List<TbsysTemplate> getTemplateList(Map map) {
		return tbsysTemplateMapper.getTemplateList(map);
	}

	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * 
	 * @author liucy 2014-7-17 10:12:26
	 */
	@Override
	public int getCount(Map map) {
		return tbsysTemplateMapper.getCount(map);
	}
	
	@Override
	public TbsysTemplate getTemplateAndDepartid(String tempid, String departid){
		
		if(StringNumberUtil.notEmpty(tempid)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tempid", tempid);
			map.put("departid", departid);
			return tbsysTemplateMapper.getTemplateDetail(map);
		}
		return null;
		
	}

	/**
	 * 通过模板id获取模板信息
	 * 
	 * @author liucy 2014-7-17 10:12:26
	 */
	@Override
	public TbsysTemplate getTemplateAndDepartid(Map<String,Object> map){
		if(StringNumberUtil.notEmpty(map)){
			return tbsysTemplateMapper.getTemplateDetail(map);
		}
		return null;
		
	}
	
	@Override
	public TbsysTemplate getTemplateGongshi(String tempid, String departid) {
		TbsysTemplate tbsysTemplate = tbsysTemplateMapper.getTemplateGongshi(tempid,departid);
		if(tbsysTemplate==null){
			SystemOrganization systemOrganization = systemOrganizationService.getByOrganizationId(departid);
			departid = systemOrganization.getPorgid();
			tbsysTemplate = tbsysTemplateMapper.getTemplateGongshi(tempid,departid);
			if(tbsysTemplate==null){
				tbsysTemplate = tbsysTemplateMapper.getTemplateGongshi(tempid,"0");
			}
		}
		return tbsysTemplate;
	}

	/**
	 * 新增保存模板信息
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	public int saveTemplateDetail (TbsysTemplate template){
		return tbsysTemplateMapper.insertSelective(template);
	}
	/**
	 * 修改保存模板信息
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	public int updateTemplateDetail(TbsysTemplate template){
		return tbsysTemplateMapper.updateTemplateDetail(template);
	}

	/**
	 * 删除模板信息
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	@Override
	public int deleteTemplateDetail(String tempid,String departid) {
		return tbsysTemplateMapper.deleteTemplateDetail(tempid,departid);
	}

	
	/**
	 * 根据模版编号查询出文书内容、查询方案。根据查询方案id查询数据，返回到某一个字段显示出来
	 * @author liuchaoyang
	 * 2014-8-04  20:26:45
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getRecommendationContent(String tempid,SystemUser user,HttpServletRequest request){
		String content = "";//系统模版文书内容
		if(tempid == null){ 
			tempid = request.getParameter("tempid");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tempid", tempid);
        map.put("departid", user.getDepartid());
		TbsysTemplate template = this.getTemplateAndDepartid(map);
		if(template != null){
			content = template.getContent();
			String findid = Integer.toString(template.getFindid());
			List<String> nameList = this.getNameListByText(content);
			String plansql = tbsysTemplateMapper.getSqlText(findid);//Sql文
			String sql = systemResourceService.whereSql(user, plansql, request);
			HashMap datamap = this.getDocumentContent(sql);
			if(datamap != null){
				for(int i=0; i<nameList.size(); i++){
					String con = "\\[" + nameList.get(i) + "\\]";
					String value = datamap.get(nameList.get(i)) == null ? "" : datamap.get(nameList.get(i)).toString();
					if(datamap.containsKey(nameList.get(i))){
						content = content.replaceAll(con, value);
					}
				}
			}
		}
		//sqlList循环结束
		//未查到替换的[]替换为空
		List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
		Pattern pattern = Pattern.compile("\\[(.*?)\\]");//获取中括号之间的内容
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
        	Map<String, String> map1 = new HashMap<String, String>();
        	map1.put("fieldname", matcher.group(1));
        	tempList.add(map1);
        }
        for(int i=0; i<tempList.size(); i++){
        	String con = "\\[" + ((Map)tempList.get(i)).get("fieldname") + "\\]";
        	content = content.replaceAll(con, " ");
        }
        if(content != null && !"".equals(content)){
        	content = content.replaceAll("\\r\\n", "\n");
        	content = content.replaceAll("\"", "＂");//把双引号替换成全角的双引号。
		}
		return content;
	}
	

	/**
	 * 根据文书内容获取要替换的字段集合
	 * @author liuchaoyang
	 * 2014-8-04  20:26:45
	 */
	private List<String> getNameListByText(String content) {
		List<String> nameList = new ArrayList<String>();
		if(content != null && !"".equals(content)) {
	        Pattern pattern = Pattern.compile("\\[(.*?)\\]");//获取中括号之间的内容
	        Matcher matcher = pattern.matcher(content);
	        while(matcher.find()){
	            nameList.add(matcher.group(1));
	        }
		}
		return nameList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public HashMap getDocumentContent(String sql) {
		HashMap map = new HashMap();
		map.put("sql", sql);
		return tbsysTemplateMapper.getDocumentContent(map);
	}
	public List<Map<String,Object>> getDocumentContentList(String sql) {
		HashMap map = new HashMap();
		map.put("sql", sql);
		return tbsysTemplateMapper.getDocumentContentList(map);
	}
	public HashMap getCuryearBatch(String departid){
		return tbsysTemplateMapper.getCuryearBatch(departid);
	}

	@Override
	public List<Map> getResourceMapObject() {
		
		return tbsysTemplateMapper.getResourceMapObject();
	}
	
	public List<HashMap> ajaxGettemptype(String codetype) {
		return tbsysTemplateMapper.ajaxGettemptype(codetype);
	}

	@Override
	public int updateTemplateDetail2(Map<String, Object> map) {
		return tbsysTemplateMapper.updateTemplateDetail2(map);
	}

	@Override
	public TbsysTemplate getTemplateByTempAndDept(Map<String, Object> map) {
		return tbsysTemplateMapper.getTemplateByTempAndDept(map);
	}

	@Override
	public Map threeClassCriminalShuoMing(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ajaxGetTempid() {
		return tbsysTemplateMapper.ajaxGetTempid();
	}

	@Override
	public String getDepartNameByDepartid(String departid) {
		// TODO Auto-generated method stub
		return tbsysTemplateMapper.getDepartNameByDepartid(departid);
	}

	@Override
	public List<Map<String,Object>> getCourtSystemModelList(Map map) {
		return MapUtil.convertKeyList2LowerCase(tbsysTemplateMapper.getCourtSystemModelList(map));
	}

	@Override
	public int getCourtSystemModelCount(Map map) {
		return tbsysTemplateMapper.getCourtSystemModelCount(map);
	}

	@Override
	public TbsysTemplate getCourtTemplateById(Map<String, Object> map) {
		return tbsysTemplateMapper.getCourtTemplateById(map);
	}

	@Override
	public String getCourtUserTemplateById(String userid, String tempid) {
		return tbsysTemplateMapper.getCourtUserTemplateById( userid,  tempid);
	}

	@Override
	public void insertCourtUserTemplate(Map<String, Object> map) {
		tbsysTemplateMapper.insertCourtUserTemplate(map);		
	}

	@Override
	public void updateCourtUserTemplateById(Map<String, Object> map) {
		tbsysTemplateMapper.updateCourtUserTemplateById(map);		
	}

	@Override
	public Map<String, Object> getCourtUserTemplateTextById(String tempid, String userid) {
		return tbsysTemplateMapper.getCourtUserTemplateTextById( tempid,  userid);
	}

	@Override
	public int deleteCourtModelDetail(String tempid, String departid) {
		tbsysTemplateMapper.deleteTemplateDetail(tempid,departid);
		return tbsysTemplateMapper.deleteCourtUserModelByTempid(tempid);
	}

	@Override
	public int getLiAnApproveCount(Map<String, Object> parmMap) {
		Map<String, Object> listMap = new HashMap<String, Object>();
		 String columnsData =  (String) parmMap.get("columnsData");
		 String queryWhereData =  (String) parmMap.get("queryWhereData");
		 //添加显示列 集合
	     if(columnsData!=null && !"".equals(columnsData)){
	    	Map map = (Map)JsonUtil.Decode(columnsData);
	    	listMap.putAll(map);
	     }
	     //添加条件列集合
	     if(queryWhereData!=null && !"".equals(queryWhereData)){
	    	 Map map  =  (Map) JsonUtil.Decode(queryWhereData);
	    	 listMap.putAll(map);
	     }
		return tbsysTemplateMapper.getLiAnApproveCount(listMap);
	}

	@Override
	public List<Map> getLiAnApproveList(Map<String, Object> parmMap) {
		Map<String, Object> listMap = new HashMap<String, Object>();
		 String columnsData =  (String) parmMap.get("columnsData");
		 String queryWhereData =  (String) parmMap.get("queryWhereData");
		 //添加显示列 集合
	     if(columnsData!=null && !"".equals(columnsData)){
	    	 Map map = (Map)JsonUtil.Decode(columnsData);
		    	listMap.putAll(map);
	     }
	     //添加条件列集合
	     if(queryWhereData!=null && !"".equals(queryWhereData)){
	    	 Map map  =  (Map) JsonUtil.Decode(queryWhereData);
	    	 listMap.putAll(map);
	     }
	     listMap.put("start", parmMap.get("start"));
	     listMap.put("end", parmMap.get("end"));
		return MapUtil.turnKeyToLowerCaseOfList(tbsysTemplateMapper.getLiAnApproveList(listMap));
	}

	//start add by blue_lv 2015-07-15
	@Override
	public List<TbsysTemplate> listAllByType(String othertemplatetype) {
		return this.tbsysTemplateMapper.listAllByType(othertemplatetype);
	}
	//end add by blue_lv 2015-07-15
}
