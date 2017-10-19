package com.sinog2c.service.impl.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.common.CommonSQLMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.common.CommonService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired(required=true)
	private CommonSQLMapper commonSQLMapper;
	
	/**
	 * 描述：组装系统模板：系统模板&系统模板数据
	 * @author YangZR	2015-03-28
	 */
	public String assembleSysTemplateData(Map<String,Object> paramMap,SystemUser user){
		
		//1、查询出系统模板
		//2、组装系统模板

		//
		String sysTemplate = "";
		Map<String,Object> templateMap = solutionService.query(paramMap, user);
		Map<String,Object> listMap = solutionService.list(paramMap, user);
		//
		Map<String,Object> rootMap = (Map<String,Object>)templateMap.get("root");
		if(null != templateMap.get("form")){
			Map<String,Object> formMap = (Map<String,Object>)templateMap.get("form");
			if(null != formMap && null != formMap.get("doc_content")){
				sysTemplate = formMap.get("doc_content").toString();
			}
		}
		
		//此方法后面再处理优化
		/*
		Object unionMap = templateMap.get("union");
		if(null != unionMap){
			Set<String> set = unionMap.keySet();
			for(String key : set){
				if(! (unionMap.get(key) instanceof List<?>)){
					unionMap.remove(key);
				}
			}
			set = unionMap.keySet();
			for(String key : set){
				String mkey = "";
				String mValue = "";
				String split = "";
				List<Map<String, Object>> unionList = (List<Map<String, Object>>)unionMap.get(key);
				for(Map<String,Object> temMap : unionList){
					split = temMap.get("split").toString();
					temMap.remove("split");
					Set<String> tSet = temMap.keySet();
					for(String tString : tSet){
						mkey = tString;
						mValue += temMap.get(mkey).toString()+split;
					}
				}
				mValue = StringNumberUtil.removeLastStr(mValue, split);
				rootMap.put(mkey, mValue);
			}
		}
		//
		*/
		//
		if (sysTemplate != null){
			//将系统模板中的中括号内容替换成map的相关的数据(不包括用大括号{}引的数据)
			sysTemplate = MapUtil.replaceBKContent(sysTemplate, rootMap);
			//
			//在查询方案中配了多个list的结果存放在listMap中，就要判断当前sysTemplateMiddle应该用哪个list做循环的字符串
			//从listMap中获取key的集合Set，并对其排序，得到list
			Set<String> set = listMap.keySet();
			List<String> keyList = StringNumberUtil.sortSet(set);
			//将不是List集合的数据清掉
			for(String key : keyList){
				if(! (listMap.get(key) instanceof List<?>)){
					listMap.remove(key);
				}
			}
			//
			set = listMap.keySet();
			keyList = StringNumberUtil.sortSet(set);
			//
			int index = 0;//用于keyList中的索引
			
			//处理用大括号{}引的数据：大括号{}引的数据需要循环。
			while(sysTemplate.indexOf("{") != -1 && sysTemplate.indexOf("}") != -1){
				int start = sysTemplate.indexOf("{");
				int end = sysTemplate.indexOf("}");
				String sysTemplateHead = sysTemplate.substring(0,start);
				String sysTemplateMiddle = sysTemplate.substring(start+1, end);
				String sysTemplateTail = sysTemplate.substring(end+1);
				
				StringBuffer sysTemplateMiddleBuf = new StringBuffer(); 
				//sysTemplateMiddle需要循环

				
//				String key = keyList.get(index);
				String key = "list"+index;
				Object listValue = listMap.get(key);
				index ++;
				//
				if(listValue instanceof List<?>){
					List<Map<String, Object>> dataList = (List<Map<String, Object>>)listValue;
					if(null!=dataList && dataList.size()>0){
						//sysTemplateMiddle 循环的字符串
						for(Map<String,Object> dataMap : dataList){
							sysTemplateMiddleBuf.append(MapUtil.replaceBKContent(sysTemplateMiddle, dataMap)).append("\n");
						}
					}
				}
				//
				sysTemplateMiddle = sysTemplateMiddleBuf.toString();
				if(sysTemplateMiddle.endsWith("\n")){
					sysTemplateMiddle = StringNumberUtil.removeLastStr(sysTemplateMiddle,"\n");
				}
				sysTemplate = sysTemplateHead + sysTemplateMiddle + sysTemplateTail;
			}
			
			
			//去掉中括号中没有被替换掉的内容
			sysTemplate = StringNumberUtil.removeBracketContent(sysTemplate);
			//系统模板特殊符号数据处理
			sysTemplate = StringNumberUtil.dealCharForSysTemplate(sysTemplate);
		}
		
		return sysTemplate;
	}
		
	
	@Override
	public Map<String, Object> getTableColumn(Map<String, Object> map) {
		return MapUtil.convertKey2LowerCase(commonSQLMapper.getTableColumn(map));
	}

	@Override
	public void alterTableRenameColumn(Map<String, Object> map) {
		commonSQLMapper.alterTableRenameColumn(map);
	}

	@Override
	public void alterTableAddColumn(Map<String, Object> map) {
		commonSQLMapper.alterTableAddColumn(map);
	}

	@Override
	public void alterTableModifyColumn(Map<String, Object> map) {
		commonSQLMapper.alterTableModifyColumn(map);
	}

	@Override
	public void alterTableDropColumn(Map<String, Object> map) {
		commonSQLMapper.alterTableDropColumn(map);
	}
}
