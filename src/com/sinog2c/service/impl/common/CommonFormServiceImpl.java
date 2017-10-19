package com.sinog2c.service.impl.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysFormDetails;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonFormService;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysFormDetailsService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 通用公共表单服务接口
 */
@Service("commonFormService")
public class CommonFormServiceImpl extends ServiceImplBase implements CommonFormService{

	@Autowired
	CommonSQLSolutionService commonSQLSolutionService;
	@Autowired
	private FlowArchivesService flowArchivesService;
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	public TbsysFormDetailsService tbsysFormDetailsService;
	
	/**
	 * 通用删除功能
	 */
	@Override
	@Transactional
	public JSONMessage commonRemoveDate(Map<String, Object> map, SystemUser user){
		
		JSONMessage message = JSONMessage.newMessage();
		
		try{
			Object solutionid = map.get("solutionid");
			if(StringNumberUtil.isEmpty(solutionid)){
				throw new RuntimeException();
			}
			commonSQLSolutionService.delete(map, user);
			message.setSuccess();
			message.setInfo("操作成功！");
		}catch(Exception e){
			e.printStackTrace();
			message.setInfo("操作失败！");
			throw new RuntimeException();
		}
		
		return message;
	}
	
	
	@Override
	public JSONArray parseFormDataOfSolution(Map resultMap){
		
		Object aipFileStr = "";
		JSONArray docconent = new JSONArray();
		
		Object formObj = resultMap.get("form"); 
		if(null != formObj && formObj instanceof Map){
			Map<String,Object> form = (Map<String,Object>)formObj;
			if(null != form && null != form.get("doc_content")){
				aipFileStr = form.get("doc_content");
				if(StringNumberUtil.notEmpty(aipFileStr)){
					docconent.add(aipFileStr);
				}
			}
		}else if(null != formObj && formObj instanceof List<?>){
			List<Map<String,Object>> aipFileList = (List<Map<String,Object>>)formObj;
			if(null != aipFileList && aipFileList.size()>0){
				for(Map temMap : aipFileList){
					if(null != temMap && StringNumberUtil.notEmpty(temMap.get("doc_content"))){
						docconent.add(temMap.get("doc_content"));
					}
				}
			}
		}
		
		//如果为电子档案
		Object archObj = resultMap.get("arch");
		if(null != archObj && archObj instanceof Map){
			Map<String,Object> arch = (Map<String,Object>)archObj;
			if(null != arch && null != arch.get("archiveid")){
				String archiveid = arch.get("archiveid").toString();
				aipFileStr = flowArchivesService.getArchiveDocconentByArchiveid(archiveid); //archiveid
				if(StringNumberUtil.notEmpty(aipFileStr)){
					docconent.add(aipFileStr);
				}
			}
		}else if(null != archObj && archObj instanceof List<?>){

			List<Map<String,Object>> archFileList = (List<Map<String,Object>>)archObj;
			if(null != archFileList && archFileList.size()>0){
				for(Map temMap : archFileList){
					if(null != temMap){
						String archiveid = temMap.get("archiveid").toString();
						String temAipFileStr = flowArchivesService.getArchiveDocconentByArchiveid(archiveid); //archiveid
						if(StringNumberUtil.notEmpty(temAipFileStr)){
							docconent.add(temAipFileStr);
						}
					}
				}
				
			}
		}
		
		return docconent;
	}

	/**
	 * 更新时间
	 */
	@Override
	public JSONMessage updateDate(Map<String, Object> map, SystemUser user) {
		JSONMessage message = JSONMessage.newMessage();
		
		try{
			Object solutionid = map.get("solutionid");
			if(StringNumberUtil.isEmpty(solutionid)){
				throw new RuntimeException();
			}
			commonSQLSolutionService.save(map, user);
			message.setSuccess();
			message.setInfo("操作成功！");
		}catch(Exception e){
			e.printStackTrace();
			message.setInfo("操作失败！");
			throw new RuntimeException();
		}
		
		return message;
	}
	
	
	@Override
	public Map<String,Object> assembleFormData(JSONArray docconent,Map<String,Object> paramMap,SystemUser user,String tempid){
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Object aipFileStr = "";//表单大字段
		String departid=user.getDepartid();
		
		//查询表单数据
		Map<String,Object> resultMap = solutionService.query(paramMap, user);
		Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
		
		docconent = parseFormDataOfSolution(resultMap);
		String optype = paramMap.get("optype") == null ? "" : paramMap.get("optype").toString().trim();
		if((null ==docconent || docconent.size() <=0) && !"solution".equals(optype)){
			//查询表单模板
			if(StringNumberUtil.notEmpty(tempid)){
				HashMap map1 = new HashMap();
				map1.put("tempid", tempid);
				map1.put("orgid", user.getOrgid());
				map1.put("departid", user.getDepartid());
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(map1);
				if (template != null){
					aipFileStr = template.getContent();
					if(StringNumberUtil.notEmpty(aipFileStr)){
						docconent.add(aipFileStr);
					}
				}
				
			}
		}
		
		if(docconent.size()>1){
			returnMap.put("ismultipage", "0");//用于控制多页显示的标志
		}
		
		//如果有挂系统模板，组装模板 
		if(null == map){
			map = new HashMap();
		}
		
		if(StringNumberUtil.notEmpty(tempid)){
			map = paramForm4FormDetails(map,tempid,user);
		}
		
		//
		if(null!=map){
			map = StringNumberUtil.dealMapForForm(map);
			returnMap.put("formDatajson", new JSONObject(map).toString());
		}

		//
		Object _options = (Map<String,Object>)resultMap.get("options");
		if(null != _options && _options instanceof Map){
			Map<String,Object> options = (Map<String,Object>)_options;
			options = MapUtil.convertKey2LowerCase(options);
			returnMap.put("selectDatajson", new JSONObject(options).toString());
		}
		
		returnMap.put("docconent", docconent);
		
		return returnMap;
		
	}
	
	
	/**
	 * 表单中的系统模板的组装	YangZR 2016.08.02
	 * @param map
	 * @param tempid
	 * @param user
	 * @return
	 */
	public Map<String,Object> paramForm4FormDetails(Map<String,Object> map,String tempid,SystemUser user){
		Map<String,Object> param4FormDetails = new HashMap<String,Object>();
		param4FormDetails.put("type", 0);// 类型（0：系统模板，1：弹出框）
		param4FormDetails.put("tempid", tempid);
		param4FormDetails.put("departid", user.getDepartid());
		param4FormDetails.put("orgid", user.getOrgid());
		//根据类型、表单ID、单位编号查找，找部门编号最小的那个
		List<TbsysFormDetails> result = tbsysFormDetailsService.getFormDetails(param4FormDetails);
		if(null!=result && result.size()>0){
			for(TbsysFormDetails tbsysFormDetails : result){
				if(StringNumberUtil.notEmpty(tbsysFormDetails.getSystempid())){
					HashMap map1 = new HashMap();
					map1.put("tempid", tbsysFormDetails.getSystempid());
					map1.put("orgid", user.getOrgid());
					map1.put("departid", user.getDepartid());
					TbsysTemplate sysTemplate = systemModelService.getTemplateAndDepartid(map1);
					if(null!=sysTemplate && StringNumberUtil.notEmpty(sysTemplate.getContent())){
						String content = sysTemplate.getContent();
						content = MapUtil.replaceBracketContent(content, map);//将系统模板中的中括号内容替换成map的相关的数据
						content = StringNumberUtil.dealCharForForm(content);
						map.put(tbsysFormDetails.getNodeid(), content);//将组装好的系统模板 放入map
					}
				}
			}
		}
		
		return map;
	}
	
	
//	@Override
//	public Map<String,Object> assembleFormData(String docconentStr,JSONArray docconent,Map<String,Object> paramMap,SystemUser user,String tempid){
//		
//		Map<String,Object> returnMap = new HashMap<String,Object>();
//		
//		Object aipFileStr = "";//表单大字段
//		String departid=user.getDepartid();
//		
//		//查询表单数据
//		Map<String,Object> resultMap = solutionService.query(paramMap, user);
//		Map<String,Object> map = (Map<String,Object>)resultMap.get("root");
//		
//		docconent = parseFormDataOfSolution(resultMap);
//		String optype = paramMap.get("optype") == null ? "" : paramMap.get("optype").toString().trim();
//		if((null ==docconent || docconent.size() <=0) && !"solution".equals(optype)){
//			if(null != docconentStr && docconentStr.length() > 0){
//				docconent.add(docconentStr);
//			}else{
//				//查询表单模板
//				if(StringNumberUtil.notEmpty(tempid)){
//					TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
//					if (template != null){
//						aipFileStr = template.getContent();
//						if(StringNumberUtil.notEmpty(aipFileStr)){
//							docconent.add(aipFileStr);
//						}
//					}
//					
//				}
//			}
//			
//		}
//		
//		if(docconent.size()>1){
//			returnMap.put("ismultipage", "0");//用于控制多页显示的标志
//		}
//		
//		//如果有挂系统模板，组装模板 
//		if(null == map){
//			map = new HashMap();
//		}
//		
//		if(StringNumberUtil.notEmpty(tempid)){
//			Map<String,Object> param4FormDetails = new HashMap<String,Object>();
//			param4FormDetails.put("type", 0);// 类型（0：系统模板，1：弹出框）
//			param4FormDetails.put("tempid", tempid);
//			param4FormDetails.put("departid", departid);
//			List<TbsysFormDetails> result = tbsysFormDetailsService.getFormDetails(param4FormDetails);
//			//如果当前监狱找不到相应的流程信息，则找jyconfig中对应省份的流程信息
//			if(result.size() <= 0){
//				Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//				if (jypro != null) {
//					param4FormDetails.put("departid", jypro.getProperty("provincecode"));
//				}
//				result = tbsysFormDetailsService.getFormDetails(param4FormDetails);
//			}
//			if(null!=result && result.size()>0){
//				for(TbsysFormDetails tbsysFormDetails : result){
//					if(StringNumberUtil.notEmpty(tbsysFormDetails.getSystempid())){
//						TbsysTemplate sysTemplate = systemModelService.getTemplateAndDepartid(tbsysFormDetails.getSystempid(), departid);
//						if(null!=sysTemplate && StringNumberUtil.notEmpty(sysTemplate.getContent())){
//							String content = sysTemplate.getContent();
//							content = MapUtil.replaceBracketContent(content, map);//将系统模板中的中括号内容替换成map的相关的数据
//							content = StringNumberUtil.dealCharForForm(content);
//							map.put(tbsysFormDetails.getNodeid(), content);//将组装好的系统模板 放入map
//						}
//					}
//				}
//			}
//			
//		}
//		
//		//
//		if(null!=map){
//			map = StringNumberUtil.dealMapForForm(map);
//			returnMap.put("formDatajson", new JSONObject(map).toString());
//		}
//
//		//
//		Object _options = (Map<String,Object>)resultMap.get("options");
//		if(null != _options && _options instanceof Map){
//			Map<String,Object> options = (Map<String,Object>)_options;
//			options = MapUtil.convertKey2LowerCase(options);
//			returnMap.put("selectDatajson", new JSONObject(options).toString());
//		}
//		
//		returnMap.put("docconent", docconent);
//		
//		return returnMap;
//		
//	}
	

}
