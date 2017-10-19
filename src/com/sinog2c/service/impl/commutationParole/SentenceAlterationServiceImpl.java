package com.sinog2c.service.impl.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommutationReferenceMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.statistical.StatisticalPunishmentOtherMapper;
import com.sinog2c.dao.api.statistical.StatisticalPunishmentParoleMapper;
import com.sinog2c.dao.api.statistical.StatisticalPunishmentReleaseMapper;
import com.sinog2c.dao.api.system.ResourceTypeMapper;
import com.sinog2c.dao.api.system.TbsysDocumentMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;


@Service("sentenceAlterationService")
public class SentenceAlterationServiceImpl implements SentenceAlterationService {
	@Resource
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;
	@Resource
	private TbxfCommutationReferenceMapper tbxfCommutationReferenceMapper;
	@Resource
	private StatisticalPunishmentOtherMapper statisticalPunishmentOtherMapper;
	@Resource
	private StatisticalPunishmentParoleMapper statisticalPunishmentParoleMapper;
	@Resource
	private StatisticalPunishmentReleaseMapper statisticalPunishmentReleaseMapper;
	@Resource
	private TbsysDocumentMapper sysDocumentMapper;
	@Resource
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Resource
	private ResourceTypeMapper resourceTypeMapper;
	@Resource
	private SystemResourceService resourceService;

	
	
	@Override
	public int getCount(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return tbxfSentenceAlterationMapper.getCount(map);
	}

	@Override
	public List<Map<String,Object>> getSentenceAlterationList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return tbxfSentenceAlterationMapper.getSentenceAlterationList(map);
	}

	@Override
	public List<HashMap<Object, Object>> getGuidangCrimeBaseInfo(
			HashMap<Object, Object> map) {
		return this.tbxfSentenceAlterationMapper.getGuidangCrimeBaseInfo(map);
	}

	public int countByGuidangCrimeBaseInfo(HashMap<Object, Object> map) {
		return this.tbxfSentenceAlterationMapper
				.countByGuidangCrimeBaseInfo(map);
	}

	@Override
	public TbxfSentenceAlteration selectByPrimaryKey(String crimid) {
		return tbxfSentenceAlterationMapper.selectByPrimaryKey(crimid);
	}

	@Override
	public Map getSuggestDocumentInfoOfCrim(Map param) {
		return MapUtil.turnKeyToLowerCase(tbxfSentenceAlterationMapper
				.getSuggestDocumentInfoOfCrim(param));
	}

	@Override
	public void autoUpdateSentenceChangeData() {
		tbxfSentenceAlterationMapper.autoUpdateSentenceChangeData();
	}
	
	@Override
	@Transactional
	public void autoUpdateLiGongData(String provincecode){
		if("1300".equals(provincecode)){
			//查询执行的条件，如果是集中布署，则执行，如果是分散布署，则必须是监狱单位执行。
			String status = "";
			Map statusMap = MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.isLiGongExecute());
			if(null != statusMap && null != statusMap.get("status")){
				status = statusMap.get("status").toString();
			}
			//
			if(StringNumberUtil.notEmpty(status) && "1".equals(status.trim())){
				
				List<Map<String,Object>> liGongList = MapUtil.convertKeyList2LowerCase(tbxfSentenceAlterationMapper.getLiGongData());
				if(null !=liGongList && liGongList.size()>0){
					tbxfSentenceAlterationMapper.updateLiGongData(liGongList);
					
					tbxfSentenceAlterationMapper.updateHaseDealStatus(liGongList);
					
				}
			}
			
		}

	}
	

	@Override
	public List<TbxfCommutationReference> getReferenceList(String departid) {
		int sanlei = tbxfCommutationReferenceMapper.getSanleiCount(departid);
		return tbxfCommutationReferenceMapper
				.getReferenceList(departid, sanlei);
	}

	@Override
	public int countSuggestionHandleList(Map map) {
		return tbxfSentenceAlterationMapper.countSuggestionHandleList(map);
	}

	@Override
	public List<Map> findSuggestionHandleList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper
				.findSuggestionHandleList(map));
	}

	@Override
	public List<Map> SelectWaitOutPrisonList(Map map) {
		return tbxfSentenceAlterationMapper.SelectWaitOutPrisonList(map);
	}

	@Override
	public int SelectWaitOutPrisonListCount(Map map) {
		return tbxfSentenceAlterationMapper.SelectWaitOutPrisonListCount(map);
	}

	@Override
	public List<TbxfCommutationReference> getRefids(Map<String, Object> map) {
		return tbxfCommutationReferenceMapper.getRefids(map);
	}

	/**
	 * 获取刑罚统计数据信息
	 */
	@Override
	public List<HashMap<Object, Object>> getStatisticalPunishment(
			HashMap<Object, Object> map) {
		return tbxfSentenceAlterationMapper.getStatisticalPunishment(map);
	}

	@Transactional
	public int saveStatisticalPunishment(HttpServletRequest request,
			SystemUser user) {
		int rows = 0;
		String yeartypestr = "";

		// 取得参数
		String docid = request.getParameter("docid");//
		String tempid = request.getParameter("tempid");// 表单id
		String crimid = request.getParameter("crimid");//
		String content = request.getParameter("content");//
		String introduction = request.getParameter("introduction");//
		String data = request.getParameter("data");// 获取表单页面的数据

		Map map = MapUtil.getDataByAip(data);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATEFORMAT);
		int year = c.get(c.YEAR);// 得到年
		int month = c.get(Calendar.MONTH);// 得到年
		if (month > 6) {
			yeartypestr = year + GkzxCommon.TWO;
		} else {
			yeartypestr = year + GkzxCommon.ONE;
		}

		String yeartype = (String) map.get("yeartype");
		String optime = (String) map.get("optime");
		optime = optime.replace(GkzxCommon.YEAR, "-").replace(GkzxCommon.MONTH,
				"-").replace(GkzxCommon.DAYR, "");
		try {
			map.put("optime", sdf.parse(optime));
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbsysDocument document = new TbsysDocument();
		document.setContent(content);
		document.setDepartid(user.getDepartid());
		document.setIntroduction(introduction);
		document.setOpid(user.getUserid());
		document.setTempid(tempid);
		document.setOptime(new Date());

		// 保存文书
		if (yeartypestr.equals(crimid)) {
			document.setDocid(Integer.valueOf(docid));
			rows = sysDocumentMapper.updateTbsysDocument(document);// 修改保存文书
		} else {
			document.setCrimid(yeartypestr);
			rows = sysDocumentMapper.insertSelective(document);// 新增保存文书
		}

		// 保存刑罚统计数据
		if (yeartypestr.equals(yeartype)) {
			rows = statisticalPunishmentParoleMapper.updateByMap(map);
			rows = statisticalPunishmentReleaseMapper.updateByMap(map);
			rows = statisticalPunishmentOtherMapper.updateByMap(map);
		} else {
			rows = statisticalPunishmentParoleMapper.insertByMap(map);
			rows = statisticalPunishmentReleaseMapper.insertByMap(map);
			rows = statisticalPunishmentOtherMapper.insertByMap(map);
		}

		return rows;
	}

	@Override
	public void statisticalReport(HttpServletRequest request, SystemUser user) {
		String yeartype = "";
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String departId = user.getDepartid();
		JSONArray docconent = new JSONArray();
		Calendar c = Calendar.getInstance();

		int year = c.get(c.YEAR);// 得到年
		int month = c.get(Calendar.MONTH);// 得到年
		if (month > 6) {
			yeartype = year + GkzxCommon.TWO;
		} else {
			yeartype = year + GkzxCommon.ONE;
		}

		// 获取保存的文书
		Map docMap = new HashMap();
		docMap.put("tempid", tempid);
		docMap.put("departid", departId);
		docMap.put("crimid", yeartype);
		TbsysDocument document = sysDocumentMapper
				.getTbsysDocumentByMap(docMap);

		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("crimid", document.getCrimid());
			request.setAttribute("docid", document.getDocid());
		} else {
			
			Map<String,Object> paramap = new HashMap<String,Object>();
			paramap.put("tempid", tempid);
			paramap.put("departid", departId);
			//
			TbsysTemplate template = tbsysTemplateMapper.getTemplateDetail(paramap);
			if (template != null) {
				docconent.add(template.getContent());
			}
			//
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tongjiren", user.getName());
			map.put("optime", DateUtil.dateFormatForAip(new Date()));

			// 存放参数的map
			HashMap<Object, Object> paramMap = new HashMap<Object, Object>();
			paramMap.put("departid", user.getDepartid());
			paramMap.put("yeartype", yeartype);

			// 查询刑罚统计信息
			List<HashMap<Object, Object>> list = tbxfSentenceAlterationMapper
					.getStatisticalPunishment(paramMap);
			if (list != null && list.size() > 0) {
				HashMap<Object, Object> tempMap = list.get(0);
				Set<Object> set = tempMap.keySet();
				for (Object m : set) {
					map.put(((String) m).toLowerCase(), tempMap.get(m));
					if (((String) m).toLowerCase().equals("optime")) {
						map.put("optime", DateUtil.dateFormatForAip(tempMap
								.get(m)));
					}
				}
			}
			request
					.setAttribute("formDatajson", new JSONObject(map)
							.toString());
		}

		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("formcontent", docconent.toString());
	}

	/**
	 *根据crimi更改TBXF_SENTENCEALTERATION表中的JAILTO字段从1到2
	 */
	@Override
	public void autoUpdateSentenceChangeJailto(String jailto,String crimid) {
		tbxfSentenceAlterationMapper.autoUpdateSentenceChangeJailto(jailto,crimid);
	}

	// @Override
	// public List<TbxfCommutationReference> getReferenceList_sx(String
	// departid) {
	// int sanlei = tbxfCommutationReferenceMapper.getSanleiCount(departid);
	// return tbxfCommutationReferenceMapper.getReferenceList(departid, sanlei);
	// }
	@Override
	public int getCount_nx(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return tbxfSentenceAlterationMapper.getCount_nx(map);
	}

	@Override
	public List<HashMap> getSentenceAlterationList_nx(
			HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return tbxfSentenceAlterationMapper.getSentenceAlterationList_nx(map);
	}
	@Override
	public HashMap ajaxtongji1(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongji1(map));
	}
	@Override
	public HashMap ajaxtongji2(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongji2(map));
	}
	@Override
	public HashMap ajaxtongji3(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongji3(map));
	}
	@Override
	public HashMap ajaxtongji4(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongji4(map));
	}

	@Override
	public HashMap ajaxtongji5(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongji5(map));
	}
	@Override
	public HashMap ajaxtongji6(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongji6(map));
	}
	@Override
	public HashMap ajaxtongjisan1(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongjisan1(map));
	}
	@Override
	public HashMap ajaxtongjisan2(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongjisan2(map));
	}
	@Override
	public HashMap ajaxtongjisan3(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongjisan3(map));
	}
	@Override
	public HashMap ajaxtongjisan4(Map map) {
		return (HashMap) MapUtil.convertKey2LowerCase(tbxfSentenceAlterationMapper.ajaxtongjisan4(map));
	};

	@SuppressWarnings("unchecked")
	public Object getReportData(HttpServletRequest request) {
		String reportsql = "";
		String months = "";
		List list = new ArrayList();
		String orgid = request.getParameter("orgid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		List<List<Map>> listMapData = new ArrayList<List<Map>>();
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.STATISTICALREPORTSQL, null);
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String database =  jypro.getProperty("pmis.Database");
		if (StringNumberUtil.isNullOrEmpty(database)) {
			database = GkzxCommon.DATABASE_ORACLE;
			months = " and a.month = ";
		}
		if (database.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)){
			database = GkzxCommon.DATABASE_ORACLE;
			months = " and a.month = ";
		}else if (database.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)){
			database = GkzxCommon.DATABASE_DAMENG;
			months = " and a.\"month\" = ";
		} 
		database = database.toLowerCase();
		for(int i=0;i<11;i++){
			reportsql = jyconfig.getProperty(database+"StatisticsReportSql"+i);
			if(reportsql.indexOf("@orgid")>0) {
				reportsql = reportsql.replace("@orgid", "'" + orgid + "'");
				request.setAttribute("orgid", orgid);
			}
			if(reportsql.indexOf("@year")>0) {
				reportsql = reportsql.replace("@year", "'" + year + "'");
			}
			if(reportsql.indexOf("@month")>0) {    
				if(month!=null&&!"".equals(month)){
						reportsql = reportsql.replace("@month",months+month);//达梦数据库要对month加"",否则报错
				}else{
					reportsql = reportsql.replace("@month"," ");
				}
			}
			Map mapSql = new HashMap();
			mapSql.put("sql", reportsql);
			List<Map> listData =   resourceTypeMapper.getReportDataByPlanSql(mapSql);
			List<Map> lowerListData = MapUtil.turnKeyToLowerCaseOfList(listData);
			listMapData.add(lowerListData);
		}
		RMEngine engine = resourceService.getRmEngineByRid(listMapData, listMapData, request);
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("reportstr", engine.dedaoReportData());
		list.add(map1);
		return list;
	}
	
	@Override
	public int deleteByPrimaryKey(String crimid) {
		return tbxfSentenceAlterationMapper.deleteByPrimaryKey(crimid);
	};
	
	@Override
	public List<Map> SelectOutPrisonList(Map map) {
		return tbxfSentenceAlterationMapper.SelectOutPrisonList(map);
	}

	@Override
	public int SelectOutPrisonListCount(Map map) {
		return tbxfSentenceAlterationMapper.SelectOutPrisonListCount(map);
	}


}
