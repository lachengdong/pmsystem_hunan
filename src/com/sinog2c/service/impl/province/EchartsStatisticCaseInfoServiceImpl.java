package com.sinog2c.service.impl.province;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.province.EchartsStatisticCaseInfoMapper;
import com.sinog2c.dao.api.system.TbsysCodeMapper;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.service.api.province.EchartsStatisticCaseInfoService;
import com.sinog2c.util.common.MapUtil;

/**
 * @author zmxiong
 *@Date 2017-06-16
 */

@Service
public class EchartsStatisticCaseInfoServiceImpl implements EchartsStatisticCaseInfoService{
	private static final  Logger logger=LoggerFactory.getLogger(EchartsStatisticCaseInfoServiceImpl.class);
	@Autowired
	private EchartsStatisticCaseInfoMapper echartsStatisticCaseInfoMapper;
	@Autowired
	private TbsysCodeMapper codeMapper;
	
	
	
	
	/**
	 * 获取全省各个城市案件数
	 */
	
	@Override
	public List<Map<String, Object>> getProvinceCaseCount(Map<String, Object> paraMap) {
		List<Map<String, Object>> provinceCaseCountInfo=new ArrayList<Map<String, Object>>();
		Map<String, Object> cityMap=null;
		String provinceCode=getProvinceCode();//获取省份编号
		paraMap.put("provinceCode", provinceCode);
		List<TbsysCode>cityList=getCodeInfoListByParentCode(provinceCode);//根据省份编号，获取所有市级信息
		TbsysCode city=null;
		if(cityList.size()>0){
			for(int k=0;k<cityList.size();k++){
				cityMap=new HashMap<String, Object>();
				city=cityList.get(k);
				paraMap.put("cityName", city.getName());
				int cityCaseCount=echartsStatisticCaseInfoMapper.getCaseCountByCityName(paraMap);
				cityMap.put("name", city.getName());
				cityMap.put("value", cityCaseCount);
				provinceCaseCountInfo.add(cityMap);
			}
		}else{
			logger.error("根据省份编号["+provinceCode+"]"+",无法查出相应市的信息！");
		}

		return provinceCaseCountInfo;
	}
	
	

	@Override
	public List<Map<String, Object>> getSanLeiCaseCount(
			Map<String, Object> paraMap) {
		List<Map<String, Object>> sanLeiCaseCountInfo=new ArrayList<Map<String, Object>>();
		String provinceCode=getProvinceCode();//获取省份编号
		int jinRongCount=0;
		int zhiWuCount=0;
		int sheHeiCount=0;
		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			
			paraMap.put("provinceCode", provinceCode);
			logger.debug("获取金融犯数量");
			paraMap.put("sanLeiType", "jinRong");
			jinRongCount=echartsStatisticCaseInfoMapper.getSanLeiCaseCount(paraMap);
	
			
			logger.debug("获取职务犯数量");
			paraMap.put("sanLeiType", "zhiWu");
			zhiWuCount=echartsStatisticCaseInfoMapper.getSanLeiCaseCount(paraMap);

			
			logger.debug("获取涉黑犯数量");
			paraMap.put("sanLeiType", "sheHei");
			sheHeiCount=echartsStatisticCaseInfoMapper.getSanLeiCaseCount(paraMap);

		}else {//cityName 不为空，代表获取某个市的的统计数据
			if(paraMap.get("cityName") instanceof String){
				String cityName=(String)paraMap.get("cityName");
				String _jailOrgid=(String)paraMap.get("jailOrgid");
				if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
					paraMap.put("sanLeiType", "jinRong");
					jinRongCount=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
					
					paraMap.put("sanLeiType", "zhiWu");
					zhiWuCount=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
					
					paraMap.put("sanLeiType", "sheHei");
					sheHeiCount=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
					
				}else{
					List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
					if(jails.size()>0){
						for(SystemOrganization jail:jails){
							String jailOrgid=jail.getOrgid();
							paraMap.put("jailOrgid", jailOrgid);
							
							paraMap.put("sanLeiType", "jinRong");
							int jailJinRongCount=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
							jinRongCount+=jailJinRongCount;
							
							paraMap.put("sanLeiType", "zhiWu");
							int jailZhiWuCount=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
							zhiWuCount+=jailZhiWuCount;
							
							paraMap.put("sanLeiType", "sheHei");
							int jailSheHeiCount=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
							sheHeiCount+=jailSheHeiCount;
						}
					}
				}


			}
			
		}
		Map<String, Object> jinRongMap=new HashMap<String, Object>();
		jinRongMap.put("name", "金融犯");
		jinRongMap.put("value", jinRongCount);
		sanLeiCaseCountInfo.add(jinRongMap);
		
		Map<String, Object> zhiWuMap=new HashMap<String, Object>();
		zhiWuMap.put("name", "职务犯");
		zhiWuMap.put("value", zhiWuCount);
		sanLeiCaseCountInfo.add(zhiWuMap);
		
		Map<String, Object> sheHeiMap=new HashMap<String, Object>();
		sheHeiMap.put("name", "涉黑犯");
		sheHeiMap.put("value", sheHeiCount);
		sanLeiCaseCountInfo.add(sheHeiMap);
		
		return sanLeiCaseCountInfo;
	}

	@Override
	public List<Map<String, Object>> getCaseTypeCount(
			Map<String, Object> paraMap) {
		List<Map<String, Object>>caseTypeCountInfo=new ArrayList<Map<String, Object>>();
		String provinceCode=getProvinceCode();//获取省份编号
		int YBCount=0;
		int ZDCount=0;

		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			
			logger.debug("获取一般刑事犯数量");
			paraMap.put("caseType", "YB");
			YBCount=echartsStatisticCaseInfoMapper.getCaseTypeCount(paraMap);
			
			logger.debug("获取重大刑事犯数量");
			paraMap.put("caseType", "ZD");
			ZDCount=echartsStatisticCaseInfoMapper.getCaseTypeCount(paraMap);
			
	
		}else{//cityName 不为空，代表获取某个市的的统计数据
			if(paraMap.get("cityName") instanceof String){
				String cityName=(String)paraMap.get("cityName");
				String _jailOrgid=(String)paraMap.get("jailOrgid");
				if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
					paraMap.put("caseType", "YB");
					YBCount=echartsStatisticCaseInfoMapper.getJailCaseTypeCount(paraMap);
					
					paraMap.put("caseType", "ZD");
					ZDCount=echartsStatisticCaseInfoMapper.getJailCaseTypeCount(paraMap);
					
				}else{
					List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
					for(SystemOrganization jail:jails){
						String jailOrgid=jail.getOrgid();
						paraMap.put("jailOrgid", jailOrgid);
						
						paraMap.put("caseType", "YB");
						int jailYBCount=echartsStatisticCaseInfoMapper.getJailCaseTypeCount(paraMap);
						YBCount+=jailYBCount;
						
						paraMap.put("caseType", "ZD");
						int jailZDCount=echartsStatisticCaseInfoMapper.getJailCaseTypeCount(paraMap);
						ZDCount+=jailZDCount;
					}
				}
			}
			
		}

		Map<String, Object> YBMap=new HashMap<String, Object>();
		Map<String, Object> ZDMap=new HashMap<String, Object>();
		
		YBMap.put("name", "一般刑事犯");
		YBMap.put("value", YBCount);
		caseTypeCountInfo.add(YBMap);
		
		ZDMap.put("name", "重大刑事犯");
		ZDMap.put("value", ZDCount);
		caseTypeCountInfo.add(ZDMap);
		
		return caseTypeCountInfo;
	}

	@Override
	public List<Integer> getJXJSCount(Map<String, Object> paraMap) {
		List<Integer>  retnList=new ArrayList<Integer>();
		String provinceCode=getProvinceCode();//获取省份编号
		int jxCount=0;
		int jsCount=0;
		int bwCount=0;

		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			
			paraMap.put("jxjstaType", "jx");
			jxCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
			
			paraMap.put("jxjstaType", "js");
			jsCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
			
			paraMap.put("jxjstaType", "bw");
			bwCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
			
		}else{
			if(paraMap.get("cityName") instanceof String){
				String cityName=(String)paraMap.get("cityName");
				String _jailOrgid=(String)paraMap.get("jailOrgid");
				if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
					paraMap.put("jxjstaType", "jx");
					jxCount=echartsStatisticCaseInfoMapper.getJailJXJSCount(paraMap);
					
					paraMap.put("jxjstaType", "js");
					jsCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
					
					paraMap.put("jxjstaType", "bujx");
					bwCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
				}else{
					List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
					for(SystemOrganization jail:jails){
						String jailOrgid=jail.getOrgid();
						paraMap.put("jailOrgid", jailOrgid);
						
						paraMap.put("jxjstaType", "jx");
						int jailjxCount=echartsStatisticCaseInfoMapper.getJailJXJSCount(paraMap);
						jxCount+=jailjxCount;
						
						paraMap.put("jxjstaType", "js");
						int jailjsCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
						jsCount+=jailjsCount;
						
						paraMap.put("jxjstaType", "bujx");
						int jailbwCount=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
						bwCount+=jailbwCount;
						}
					}
				}
			
		}
		logger.debug("获取减刑案件数量");
		retnList.add(jxCount);
		
		logger.debug("获取假释案件数量");
		retnList.add(jsCount);
		
		logger.debug("获取保外案件数量");
		retnList.add(bwCount);
		
		
		return retnList;
	}
	
	@Override
	public List<Integer> getAYCount(Map<String, Object> paraMap) {
		List<Integer> rtnList=new ArrayList<Integer>();
		String provinceCode=getProvinceCode();//获取省份编号
		int srCaseCount=0;
		int zpCaseCount=0;
		int daoQieCount=0;
		int fhCaseCount=0;
		int qiangjiejCaseCount=0;
		int qiangJianCaseCount=0;
		int fmdpCaseCount=0;
		int gmrkCaseCount=0;
		int ffmmqzCaseCount=0;
		int ksdcCaseCount=0;
		int bjCaseCount=0;
		int zzmyCaseCount=0;
		int shCaseCount=0;
		
		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			paraMap.put("anyou", "shaRen");
			srCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "zhaPian");
			zpCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "daoQie");
			daoQieCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "fangHuo");
			fhCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "qiangJie");
			qiangjiejCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "qiangJian");
			qiangJianCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "fanMaiDuPin");
			fmdpCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "guaiMaiRenKou");
			gmrkCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "feiFaMaiMaiQiangZhi");
			ffmmqzCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "kaiSheDuChang");
			ksdcCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "bangJia");
			bjCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "zuZhiMaiYin");
			zzmyCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
			paraMap.put("anyou", "shouHui");
			shCaseCount=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
			
		}else{
			if(paraMap.get("cityName") instanceof String){
				String cityName=(String)paraMap.get("cityName");
				String _jailOrgid=(String)paraMap.get("jailOrgid");
				if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
					paraMap.put("anyou", "shaRen");
					srCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "zhaPian");
					zpCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "daoQie");
					daoQieCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "fangHuo");
					fhCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "qiangJie");
					qiangjiejCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "qiangJian");
					qiangJianCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "fanMaiDuPin");
					fmdpCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "guaiMaiRenKou");
					gmrkCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "feiFaMaiMaiQiangZhi");
					ffmmqzCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "kaiSheDuChang");
					ksdcCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "bangJia");
					bjCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "zuZhiMaiYin");
					zzmyCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
					
					paraMap.put("anyou", "shouHui");
					shCaseCount=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
				}else{
					List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
					for(SystemOrganization jail:jails){
						String jailOrgid=jail.getOrgid();
						paraMap.put("jailOrgid", jailOrgid);
						paraMap.put("anyou", "shaRen");
						srCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "zhaPian");
						zpCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "daoQie");
						daoQieCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "fangHuo");
						fhCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "qiangJie");
						qiangjiejCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "qiangJian");
						qiangJianCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "fanMaiDuPin");
						fmdpCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "guaiMaiRenKou");
						gmrkCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "feiFaMaiMaiQiangZhi");
						ffmmqzCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "kaiSheDuChang");
						ksdcCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "bangJia");
						bjCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "zuZhiMaiYin");
						zzmyCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						
						paraMap.put("anyou", "shouHui");
						shCaseCount+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
						}
				}

				}
		}
		
		logger.debug("获取案由是故意杀人案件数量");
		rtnList.add(srCaseCount);
		
		logger.debug("获取案由是诈骗案件数量");
		rtnList.add(zpCaseCount);
		
		logger.debug("获取案由是盗窃案件数量");
		rtnList.add(daoQieCount);
		
		logger.debug("获取案由是放火案件数量");
		rtnList.add(fhCaseCount);
		
		logger.debug("获取案由是抢劫案件数量");
		rtnList.add(qiangjiejCaseCount);
		
		logger.debug("获取案由是强奸案件数量");
		rtnList.add(qiangJianCaseCount);
		
		
		logger.debug("获取案由是贩卖毒品案件数量");
		rtnList.add(fmdpCaseCount);
		
		logger.debug("获取案由是拐卖人口案件数量");
		rtnList.add(gmrkCaseCount);
		
		logger.debug("获取案由是非法买卖枪支案件数量");
		rtnList.add(ffmmqzCaseCount);
		
		logger.debug("获取案由是开设赌场案件数量");
		rtnList.add(ksdcCaseCount);
		
		logger.debug("获取案由是绑架案件数量");
		rtnList.add(bjCaseCount);
		
		logger.debug("获取案由是组织卖淫案件数量");
		rtnList.add(zzmyCaseCount);
		
		logger.debug("获取案由是受贿案件数量");
		rtnList.add(shCaseCount);
		return rtnList;
	}

	@Override
	public Map<String, Object> getSanLeiGridData(Map<String, Object> paraMap) {
		Map<String, Object> retnMap=new HashMap<String, Object>();
		List<Map<String,Object>>dataList=new ArrayList<Map<String,Object>>();
		int count=0;
		String provinceCode=getProvinceCode();//获取省份编号
		if("金融犯".equals((String)paraMap.get("name"))){
			paraMap.put("sanLeiType", "jinRong");
		}else if("职务犯".equals((String)paraMap.get("name"))){
			paraMap.put("sanLeiType", "zhiWu");
		}else if("涉黑犯".equals((String)paraMap.get("name"))){
			paraMap.put("sanLeiType", "sheHei");
		}
		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			dataList=echartsStatisticCaseInfoMapper.getSanLeiCaseList(paraMap);
			count=echartsStatisticCaseInfoMapper.getSanLeiCaseCount(paraMap);
			
		}else{//不为空，代表获取某个市的的统计数据
			String cityName=(String)paraMap.get("cityName");
			String _jailOrgid=(String)paraMap.get("jailOrgid");
			if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
				dataList=echartsStatisticCaseInfoMapper.getJailSanLeiCaseList(paraMap);
				count=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
			}else{
				List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
				for(SystemOrganization jail:jails){
					String jailOrgid=jail.getOrgid();
					paraMap.put("jailOrgid", jailOrgid);
					dataList.addAll(echartsStatisticCaseInfoMapper.getJailSanLeiCaseList(paraMap));
					count+=echartsStatisticCaseInfoMapper.getJailSanLeiCaseCount(paraMap);
				}
			}
		}

		retnMap.put("data", MapUtil.convertKeyList2LowerCase(dataList));
		retnMap.put("total", count);
		return retnMap;
	}

	@Override
	public Map<String, Object> getCaseTypeGridData(Map<String, Object> paraMap) {
		Map<String, Object> retnMap=new HashMap<String, Object>();
		List<Map<String,Object>>dataList=new ArrayList<Map<String,Object>>();
		int count=0;
		String provinceCode=getProvinceCode();//获取省份编号
		if("一般刑事犯".equals((String)paraMap.get("name"))){
			paraMap.put("caseType", "YB");
		}else if("重大刑事犯".equals((String)paraMap.get("name"))){
			paraMap.put("caseType", "ZD");
		}
		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			dataList=echartsStatisticCaseInfoMapper.getCaseTypeList(paraMap);
			count=echartsStatisticCaseInfoMapper.getCaseTypeCount(paraMap);
		}else{//cityName 不为空，代表获取某个市的的统计数据
			String cityName=(String)paraMap.get("cityName");
			String _jailOrgid=(String)paraMap.get("jailOrgid");
			if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
				dataList=echartsStatisticCaseInfoMapper.getJailCaseTypeList(paraMap);
				count=echartsStatisticCaseInfoMapper.getJailCaseTypeCount(paraMap);
			}else{
				List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
				for(SystemOrganization jail:jails){
					String jailOrgid=jail.getOrgid();
					paraMap.put("jailOrgid", jailOrgid);
					dataList.addAll(echartsStatisticCaseInfoMapper.getJailCaseTypeList(paraMap));
					count+=echartsStatisticCaseInfoMapper.getJailCaseTypeCount(paraMap);
				}
			}
		}
		retnMap.put("data", MapUtil.convertKeyList2LowerCase(dataList));
		retnMap.put("total", count);
		return retnMap;
	}

	@Override
	public Map<String, Object> getAnYouGridData(Map<String, Object> paraMap) {
		Map<String, Object> retnMap=new HashMap<String, Object>();
		List<Map<String,Object>>dataList=new ArrayList<Map<String,Object>>();
		int count=0;
		String provinceCode=getProvinceCode();//获取省份编号
		Integer dataIndex=(Integer)paraMap.get("dataIndex");
		if(dataIndex==0){
			logger.debug("获取案由是故意杀人案件");
			paraMap.put("anyou", "shaRen");
		}else if(dataIndex==1){
			logger.debug("获取案由是诈骗案件");
			paraMap.put("anyou", "zhaPian");
		}else if(dataIndex==2){
			logger.debug("获取案由是盗窃案件");
			paraMap.put("anyou", "daoQie");
		}else if(dataIndex==3){
			logger.debug("获取案由是放火案件");
			paraMap.put("anyou", "fangHuo");
		}else if(dataIndex==4){
			logger.debug("获取案由是抢劫案件");
			paraMap.put("anyou", "qingJie");
		}else if(dataIndex==5){
			logger.debug("获取案由是强奸案件");
			paraMap.put("anyou", "qiangJian");
		}else if(dataIndex==6){
			logger.debug("获取案由是贩卖毒品案件");
			paraMap.put("anyou", "fanMaiDuPin");
		}else if(dataIndex==7){
			logger.debug("获取案由是拐卖人口案件");
			paraMap.put("anyou", "guaiMaiRenKou");
		}else if(dataIndex==8){
			logger.debug("获取案由是非法买卖枪支案件");
			paraMap.put("anyou", "feiFaMaiMaiQiangZhi");
		}else if(dataIndex==9){
			logger.debug("获取案由是开设赌场案件");
			paraMap.put("anyou", "kaiSheDuChang");
		}else if(dataIndex==10){
			logger.debug("获取案由是绑架案件");
			paraMap.put("anyou", "bangJia");
		}else if(dataIndex==11){
			logger.debug("获取案由是组织卖淫案件");
			paraMap.put("anyou", "zuZhiMaiYin");
		}else if(dataIndex==12){
			logger.debug("获取案由是受贿案件");
			paraMap.put("anyou", "shouHui");
		}
		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			dataList=echartsStatisticCaseInfoMapper.getAYCaseList(paraMap);
			count=echartsStatisticCaseInfoMapper.getAYCount(paraMap);
		}else{
			String cityName=(String)paraMap.get("cityName");
			String _jailOrgid=(String)paraMap.get("jailOrgid");
			if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
				dataList=echartsStatisticCaseInfoMapper.getJailAYCaseList(paraMap);
				count=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
			}else{
				List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
				for(SystemOrganization jail:jails){
					String jailOrgid=jail.getOrgid();
					paraMap.put("jailOrgid", jailOrgid);
					dataList.addAll(echartsStatisticCaseInfoMapper.getJailAYCaseList(paraMap));
					count+=echartsStatisticCaseInfoMapper.getJailAYCount(paraMap);
				}
			}
		}

		retnMap.put("data", MapUtil.convertKeyList2LowerCase(dataList));
		retnMap.put("total", count);
		return retnMap;
	}

	@Override
	public Map<String, Object> getJXJSGridData(Map<String, Object> paraMap) {
		Map<String, Object> retnMap=new HashMap<String, Object>();
		List<Map<String,Object>>dataList=new ArrayList<Map<String,Object>>();
		int count=0;
		String provinceCode=getProvinceCode();//获取省份编号
		Integer dataIndex=(Integer)paraMap.get("dataIndex");
		if(dataIndex==0){
			paraMap.put("jxjstaType", "jx");
		}else if(dataIndex==1){
			paraMap.put("jxjstaType", "js");
		}else if(dataIndex==2){
			paraMap.put("jxjstaType", "bw");
		}
		if(paraMap.get("cityName")==null||"".equals((String)paraMap.get("cityName"))){//cityName为空，代表获取全省的统计数据
			paraMap.put("provinceCode", provinceCode);
			dataList=echartsStatisticCaseInfoMapper.getJXJSList(paraMap);
			count=echartsStatisticCaseInfoMapper.getJXJSCount(paraMap);
		}else{
			String cityName=(String)paraMap.get("cityName");
			String _jailOrgid=(String)paraMap.get("jailOrgid");
			if(_jailOrgid!=null&&!"".equals(_jailOrgid)){
				dataList=echartsStatisticCaseInfoMapper.getJailJXJSList(paraMap);
				count=echartsStatisticCaseInfoMapper.getJailJXJSCount(paraMap);
			}else{
				List<SystemOrganization> jails=echartsStatisticCaseInfoMapper.getOrganiztionsByCityName(provinceCode,cityName);
				for(SystemOrganization jail:jails){
					String jailOrgid=jail.getOrgid();
					paraMap.put("jailOrgid", jailOrgid);
					dataList.addAll(echartsStatisticCaseInfoMapper.getJailJXJSList(paraMap));
					count+=echartsStatisticCaseInfoMapper.getJailJXJSCount(paraMap);
				}
			}
		}
		retnMap.put("data", MapUtil.convertKeyList2LowerCase(dataList));
		retnMap.put("total", count);
		return retnMap;
	}


/**
 * 根据省编号，得出所有的市信息
 */
	@Override
	public List<TbsysCode> getCodeInfoListByParentCode(String parentCode) {
		List<TbsysCode>codeList=new ArrayList<TbsysCode>();
		Map paraMap=new HashMap();
		paraMap.put("pcodeid", parentCode);
		codeList=codeMapper.getCodesByMap(paraMap);
		return codeList;
	}
	
	
	public  String getProvinceCode(){
		String provinceCode=null;
		Properties properties=new GetProperty().bornProp("jyconfig","");
		provinceCode=properties.getProperty("provincecode");
		if(provinceCode!=null&&!"".equals(provinceCode.trim())){
			provinceCode=provinceCode+"00";
		}else{
			logger.error("jyconfig配置文件中，省编号（provincecode）未配置！");
			return null;
		}
		return provinceCode;
	}


	@Override
	public Map<String, Object> getJailGridData(Map<String, Object> paraMap) {
		Map<String, Object> rtnMap=new HashMap<String, Object>();
		String provinceCode=getProvinceCode();
		paraMap.put("provinceCode", provinceCode);
		List<SystemOrganization> list=echartsStatisticCaseInfoMapper.getOrganiztionsByPageList(paraMap);
		int count=echartsStatisticCaseInfoMapper.getOrganiztionsByPageCount(paraMap);
		rtnMap.put("data", list);
		rtnMap.put("total", count);
		return rtnMap;
	}
}
