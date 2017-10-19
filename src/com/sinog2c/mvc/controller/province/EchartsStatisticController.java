package com.sinog2c.mvc.controller.province;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.service.api.province.EchartsStatisticCaseInfoService;
import com.sinog2c.util.common.DistrictUtil;

/**
 * 获取echarts报表数据Controller
 * @author zmxiong
 * @Date 2017-06-16
 */
@Controller
@RequestMapping("/statistic")
public class EchartsStatisticController {
	
	@Autowired
	private EchartsStatisticCaseInfoService echartsStatisticCaseInfoService;
	private static final  Logger logger=LoggerFactory.getLogger(EchartsStatisticController.class);
	
	@RequestMapping("/toCaseInfoEcharts.page")
	public String toCaseInfoStatistic(Model model){
		String provinceCode=echartsStatisticCaseInfoService.getProvinceCode();
		String provinceName=DistrictUtil.getProvinceName(provinceCode);
		String provincePinYin=DistrictUtil.getProvincePinYin(provinceCode);
		model.addAttribute("provinceCode", provinceCode);
		model.addAttribute("provinceName", provinceName);
		model.addAttribute("provincePinYin", provincePinYin);
		
		return "statistic/province_statistic_echarts";
	}
	
	@RequestMapping("/toCaseDetailInfo.page")
	public String toCaseDetailInfo(HttpServletRequest request){
		String cityName=request.getParameter("cityName");
		String orgid=request.getParameter("orgid");
		String queryStartDate=request.getParameter("queryStartDate");
		String queryEndDate=request.getParameter("queryEndDate");
		String dataIndex=request.getParameter("dataIndex");
		String name=request.getParameter("name");
		String type=request.getParameter("type");
		String jylx=request.getParameter("jylx");
		request.setAttribute("type", type);
		request.setAttribute("dataIndex", dataIndex);
		request.setAttribute("name", name);
		request.setAttribute("cityName", cityName);//城市名称
		request.setAttribute("orgid", orgid);//监狱的orgid
		request.setAttribute("jylx", jylx);//羁押类型
		request.setAttribute("queryStartDate", queryStartDate);
		request.setAttribute("queryEndDate", queryEndDate);
		return "statistic/province_statistic_echarts_detail";
	}
	
	
	@RequestMapping("/getProvinceCaseCountInfo.json")
	@ResponseBody
	public List<Map<String,Object>> getProvinceCaseCountInfo(HttpServletRequest request ,HttpServletResponse response){
		List<Map<String,Object>>rtnList=new ArrayList<Map<String,Object>>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String queryStartDate=request.getParameter("queryStartDate");//查询起始时间
		String queryEndDate=request.getParameter("queryEndDate");//查询结束时间
		String jylx=request.getParameter("jylx");//羁押类型  0在押  1释放
		paraMap.put("jylx", jylx);
		if("全部".equals(jylx.trim())){
			jylx="";
		}
		paraMap.put("jylx", jylx);
		paraMap.put("queryStartDate", queryStartDate);
		paraMap.put("queryEndDate", queryEndDate);
		rtnList=echartsStatisticCaseInfoService.getProvinceCaseCount(paraMap);
		return rtnList;
	}
	
	@RequestMapping("/getSanLeiCaseCountInfo.json")
	@ResponseBody
	public List<Map<String,Object>> getSanLeiCaseCountInfo(HttpServletRequest request ,HttpServletResponse response){
		List<Map<String,Object>>rtnList=new ArrayList<Map<String,Object>>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String queryStartDate=request.getParameter("queryStartDate");//查询起始时间
		String queryEndDate=request.getParameter("queryEndDate");//查询结束时间
		String cityName=request.getParameter("cityName");//查询城市编号
		String orgid=request.getParameter("orgid");
		String jylx=request.getParameter("jylx");//羁押类型  
		if("全部".equals(jylx.trim())){
			jylx="";
		}
		paraMap.put("jylx", jylx);
		paraMap.put("queryStartDate", queryStartDate);
		paraMap.put("queryEndDate", queryEndDate);
		paraMap.put("cityName", cityName);
		paraMap.put("jailOrgid", orgid);
		rtnList=echartsStatisticCaseInfoService.getSanLeiCaseCount(paraMap);
		return rtnList;
	}
	
	
	@RequestMapping("/getAnYouCaseCountInfo.json")
	@ResponseBody
	public List<Integer> getAnYouCaseCountInfo(HttpServletRequest request ,HttpServletResponse response){
		
		List<Integer> rtnList=new ArrayList<>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String queryStartDate=request.getParameter("queryStartDate");//查询起始时间
		String queryEndDate=request.getParameter("queryEndDate");//查询结束时间
		String cityName=request.getParameter("cityName");//查询城市编号
		String orgid=request.getParameter("orgid");
		String jylx=request.getParameter("jylx");//羁押类型 
		if("全部".equals(jylx.trim())){
			jylx="";
		}
		paraMap.put("jylx", jylx);
		paraMap.put("queryStartDate", queryStartDate);
		paraMap.put("queryEndDate", queryEndDate);
		paraMap.put("cityName", cityName);
		paraMap.put("jailOrgid", orgid);
		rtnList= echartsStatisticCaseInfoService.getAYCount(paraMap);
		return rtnList;
	}
	
	
	@RequestMapping("/getCaseTypeCountInfo.json")
	@ResponseBody
	public List<Map<String,Object>> getCaseTypeCountInfo(HttpServletRequest request ,HttpServletResponse response){
		List<Map<String,Object>>rtnList=new ArrayList<Map<String,Object>>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String queryStartDate=request.getParameter("queryStartDate");//查询起始时间
		String queryEndDate=request.getParameter("queryEndDate");//查询结束时间
		String cityName=request.getParameter("cityName");//查询城市编号
		String orgid=request.getParameter("orgid");
		String jylx=request.getParameter("jylx");//羁押类型 
		if("全部".equals(jylx.trim())){
			jylx="";
		}
		paraMap.put("jylx", jylx);
		paraMap.put("queryStartDate", queryStartDate);
		paraMap.put("queryEndDate", queryEndDate);
		paraMap.put("cityName", cityName);
		paraMap.put("jailOrgid", orgid);
		rtnList=echartsStatisticCaseInfoService.getCaseTypeCount(paraMap);
		return rtnList;
	}
	
	
	@RequestMapping("/getJXJSCaseCountInfo.json")
	@ResponseBody
	public List<Integer> getJXJSCountInfo(HttpServletRequest request ,HttpServletResponse response){
		List<Integer>rtnList=new ArrayList<Integer>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String queryStartDate=request.getParameter("queryStartDate");//查询起始时间
		String queryEndDate=request.getParameter("queryEndDate");//查询结束时间
		String cityName=request.getParameter("cityName");//查询城市编号
		String orgid=request.getParameter("orgid");
		String jylx=request.getParameter("jylx");//羁押类型  
		if("全部".equals(jylx.trim())){
			jylx="";
		}
		paraMap.put("jylx", jylx);
		paraMap.put("queryStartDate", queryStartDate);
		paraMap.put("queryEndDate", queryEndDate);
		paraMap.put("cityName", cityName);
		paraMap.put("jailOrgid", orgid);
		rtnList=echartsStatisticCaseInfoService.getJXJSCount(paraMap);
		return rtnList;
	}
	
	
	/**
	 * 根据图表查询案件详情信息
	 */
	@RequestMapping("/getCaseDetailInfo.json")
	@ResponseBody
	public Map<String,Object>getCaseDetailInfo(HttpServletRequest request){
		Map<String,Object>rtnMap=new HashMap<String,Object>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String type=request.getParameter("type");
		Integer dataIndex=request.getParameter("dataIndex")!=null&&!"".equals(request.getParameter("dataIndex"))?Integer.parseInt((String)request.getParameter("dataIndex")):-1;
		String name=request.getParameter("name");
		String cityName=request.getParameter("cityName");
		String orgid=request.getParameter("orgid");
		String pageIndex=request.getParameter("pageIndex");//分页当前页数
		String pageSize=request.getParameter("pageSize");//分页页面条数
		String key=request.getParameter("key");
		String sortField=request.getParameter("sortField");//排序字段
		String sortOrder=request.getParameter("sortOrder");//排序方向
		String queryStartDate=request.getParameter("queryStartDate");//查询起始时间
		String queryEndDate=request.getParameter("queryEndDate");//查询结束时间
		int _pageIndex=pageIndex!=null?Integer.parseInt(pageIndex):0;
		int _pageSize=pageSize!=null?Integer.parseInt(pageSize):0;
		int start=_pageIndex*_pageSize+1;
		int end=(_pageIndex+1)*_pageSize;
		String jylx=request.getParameter("jylx");//羁押类型
		if("全部".equals(jylx.trim())){
			jylx="";
		}
		paraMap.put("jylx", jylx);
		paraMap.put("queryStartDate", queryStartDate);
		paraMap.put("queryEndDate", queryEndDate);
		paraMap.put("key", key);
		paraMap.put("pageIndex", pageIndex);
		paraMap.put("pageSize", pageSize);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		paraMap.put("dataIndex", dataIndex);
		paraMap.put("name", name);
		paraMap.put("cityName", cityName);
		paraMap.put("jailOrgid", orgid);
		paraMap.put("start", start);
		paraMap.put("end", end);
		if((type!=null&&type!="")){
			if("sanLei".equals(type)){
				rtnMap=echartsStatisticCaseInfoService.getSanLeiGridData(paraMap);
				
			}else if("caseType".equals(type)){
				rtnMap=echartsStatisticCaseInfoService.getCaseTypeGridData(paraMap);
			
			}
			else if("anYou".equals(type)){
				rtnMap=echartsStatisticCaseInfoService.getAnYouGridData(paraMap);
			}
			else if("jxjs".equals(type)){
				rtnMap=echartsStatisticCaseInfoService.getJXJSGridData(paraMap);
				
			}
			
		}else{
			logger.error("查询类型不能为空！");;
		}
		return rtnMap;
	}
	
	
	@RequestMapping("/getJailPageDataGrid.json")
	@ResponseBody
	public Map<String,Object>getJailPageDataGrid(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object>rtnMap=new HashMap<String,Object>();
		Map<String,Object>paraMap=new HashMap<String,Object>();
		String cityName=request.getParameter("cityName");
		String pageIndex=request.getParameter("pageIndex");//分页当前页数
		String pageSize=request.getParameter("pageSize");//分页页面条数
		int _pageIndex=pageIndex!=null?Integer.parseInt(pageIndex):0;
		int _pageSize=pageSize!=null?Integer.parseInt(pageSize):0;
		int start=_pageIndex*_pageSize+1;
		int end=(_pageIndex+1)*_pageSize;
		String key=request.getParameter("key");
		String sortField=request.getParameter("sortField");//排序字段
		String sortOrder=request.getParameter("sortOrder");//排序方向
		paraMap.put("pageIndex", pageIndex);
		paraMap.put("pageSize", pageSize);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		paraMap.put("cityName", cityName);
		paraMap.put("key", key);
		paraMap.put("start", start);
		paraMap.put("end", end);
		rtnMap=echartsStatisticCaseInfoService.getJailGridData(paraMap);
		return rtnMap;
	}
	
	

}
