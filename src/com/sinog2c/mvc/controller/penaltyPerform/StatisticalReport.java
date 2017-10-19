package com.sinog2c.mvc.controller.penaltyPerform;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * @author kexz
 *刑法执行工作统计报表
 * 2014-7-17
 */
@Controller
public class StatisticalReport extends ControllerBase {
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	
	
	@RequestMapping("/statisticalReport")
	public ModelAndView statisticalReport(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		sentenceAlterationService.statisticalReport(request,user);
		return new ModelAndView("penaltyPerform/statisticalReport");
	}
	/**
	 * @author zhenghui
	 *保存刑罚统计报表
	 * 2014-7-18
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveStatisticalPunishment.json")
	@ResponseBody
	public int saveStatisticalPunishment(HttpServletRequest request){
		int resultval = -1;
		SystemUser user = getLoginUser(request);
		resultval = sentenceAlterationService.saveStatisticalPunishment(request,user);
		return resultval;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/statisticsOfPenaltyExecution.page")
	public ModelAndView statisticsOfPenaltyExecution(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		SystemUser systemUser = (SystemUser) getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		TbsysTemplate template = systemModelService.getTemplateAndDepartid("SZXF_XFZXGZTJBB", systemUser.getDepartid());//获取表单
		if (template != null) docconent.add(template.getContent());
		Calendar a=Calendar.getInstance();
		Map<String,Object> map = new HashMap<String,Object>();
		SystemOrganization org = systemUser.getOrganization();
		request.setAttribute("userId", systemUser.getUserid());
		request.setAttribute("orgId", org.getOrgid());
		request.setAttribute("unitlevel", org.getUnitlevel());
		//查询表单数据需要的参数： 年份、月份、orgid(部门id)
		int year = a.get(Calendar.YEAR);
		int month = a.get(Calendar.MONTH)+1;
		Map mm = new HashMap();
		mm.put("orgid", org.getOrgid());
		mm.put("month", month);
		mm.put("year", year);
		mm.put("unitlevel", org.getUnitlevel());
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String database =  jypro.getProperty("pmis.Database");
		if (StringNumberUtil.isNullOrEmpty(database)) mm.put("database", GkzxCommon.DATABASE_ORACLE.toLowerCase());
		if (database.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)) mm.put("database", GkzxCommon.DATABASE_ORACLE.toLowerCase());
		else if (database.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)) mm.put("database", GkzxCommon.DATABASE_DAMENG.toLowerCase());
		mm = MapUtil.convertValue2Str(mm);//转小写
		HashMap listTJ1 = sentenceAlterationService.ajaxtongji1(mm);
		HashMap listTJ2 = sentenceAlterationService.ajaxtongji2(mm);
		HashMap listTJ3 = sentenceAlterationService.ajaxtongji3(mm);
		HashMap listTJ4 = sentenceAlterationService.ajaxtongji4(mm);
		HashMap listTJ5 = sentenceAlterationService.ajaxtongji5(mm);
		HashMap listTJ6 = sentenceAlterationService.ajaxtongji6(mm);
		HashMap listsTJ1 = sentenceAlterationService.ajaxtongjisan1(mm);
		HashMap listsTJ2 = sentenceAlterationService.ajaxtongjisan2(mm);
		HashMap listsTJ3 = sentenceAlterationService.ajaxtongjisan3(mm);
		HashMap listsTJ4 = sentenceAlterationService.ajaxtongjisan4(mm);
		if(listTJ1!=null) map.putAll(listTJ1);
		if(listTJ2!=null) map.putAll(listTJ2);
		if(listTJ3!=null) map.putAll(listTJ3);	
		if(listTJ4!=null) map.putAll(listTJ4);	
		if(listTJ5!=null) map.putAll(listTJ5);	
		if(listTJ6!=null) map.putAll(listTJ6);	
		if(listsTJ1!=null) map.putAll(listsTJ1);	
		if(listsTJ2!=null) map.putAll(listsTJ2);	
		if(listsTJ3!=null) map.putAll(listsTJ3);
		if(listsTJ4!=null) map.putAll(listsTJ4);	
		
		map.put("tongjiren", systemUser.getName());
		map.put("phone", systemUser.getMobile());
		map.put("date", DateUtil.dateFormatForAip(new Date()));
		map.put("Department", systemUser.getOrganization().getName());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		return new ModelAndView("penaltyPerform/statisticalReport");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "saveTwoDimensionCode.json")
	@ResponseBody
	public Object saveTwoDimensionCode(HttpServletRequest request){
		String qrCode = "";
		HashMap fruit = null;
		try {
			qrCode = java.net.URLDecoder.decode(request.getParameter("qrCode")+"@ATA","UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] values = qrCode.split("@");
		String date = DateUtil.dateFormatForAip(DateUtil.StringParseDate(values[2]));
		//表单数据处理
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String database =  jypro.getProperty("pmis.Database");
		if (StringNumberUtil.isNullOrEmpty(database)) database = GkzxCommon.DATABASE_ORACLE;
		if (database.equalsIgnoreCase(GkzxCommon.DATABASE_ORACLE)) database = GkzxCommon.DATABASE_ORACLE;
		else if (database.equalsIgnoreCase(GkzxCommon.DATABASE_DAMENG)) database = GkzxCommon.DATABASE_DAMENG;
		database = database.toLowerCase();
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.STATISTICALREPORTSQL, null);
		for(int i=0;i<10;i++){		
			String value = "";
			String formsql = jyconfig.getProperty(database+"StatisticsFormSql"+i);
			formsql = formsql.replace("@provinceid", "'" + values[1] + "'");//替换省份ID
			formsql = formsql.replace("@year", "'" + date.substring(0, 4) + "'");//替换年份
			formsql = formsql.replace("@month", "'" + date.substring(5, 6) + "'");//替换月份
			String[] formsqls = formsql.split(";");
			int start = Integer.parseInt(formsqls[1]);//数组开始位置
			int end = Integer.parseInt(formsqls[2]);//数组结束位置	
			HashMap arethere = systemModelService.getDocumentContent(formsqls[0]);//查询本次记录是否存在
			if(!StringNumberUtil.isNullOrEmpty(arethere)){
				String updatesql = formsqls[3];
				updatesql = updatesql.replace("@date", "to_date("+date+",'yyyyMMdd')");//替换填表时间
				updatesql = updatesql.replace("@tjuser", "'" + values[5] + "'");//替换统计人
				updatesql = updatesql.replace("@shuser", "'" + values[6] + "'");//替换审核人
				updatesql = updatesql.replace("@phone", "'" + values[7] + "'");//替换统计人电话
				for(int j=start;j<=end;j++){
					if(StringNumberUtil.isNullOrEmpty(values[j])){
						 updatesql  = updatesql.replace("@"+j, "0");

					}else{
						 updatesql  = updatesql.replace("@"+j, "'" + values[j] + "'");
					}
				}
				fruit = systemModelService.getDocumentContent(updatesql);//修改保存
			}else{
				if(formsqls[4].length()>1){//判断是否需要生成序列
					HashMap map =  systemModelService.getDocumentContent(formsqls[4]);//新增保存
					//id = id.substring(0, id.indexOf("."));
					value = "'"+map.get("id")+"','"+formsqls[5]+"',";//加上类别
				}
				for(int j=start;j<=end;j++){
					if(StringNumberUtil.isNullOrEmpty(values[j])){
						value += ("'0',");
					}else{
						value += ("'"+values[j]+"',");
					}
				}
				//新增公用数据：省份provinceid、时间tianbaoshijian、年份year、月份month、统计人tongjiren、审核人shenheren、电话phone 
				String publicvalue = "'"+values[1]+"',to_date("+date+",'yyyyMMdd'),'"+Integer.parseInt(date.substring(0, 4))+"','"
						+Integer.parseInt(date.substring(5, 6))+"','"+values[5]+"','"+values[6]+"','"+values[7]+"',";
				value = publicvalue+value.substring(0, value.lastIndexOf(","));//把公用数据拼接上
				fruit = systemModelService.getDocumentContent(formsqls[6].replace("@value", value));
			}
		}
		return fruit;
	}
	
	
	//跳转到统计报表页面
	@RequestMapping(value = "/theReportPageJump.page")
	public ModelAndView theReportPageJump(HttpServletRequest request){
		String orgid = request.getParameter("orgid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String menuid = request.getParameter("menuid");
		request.setAttribute("orgid",orgid);
		request.setAttribute("year",year);
		request.setAttribute("month",month);
		request.setAttribute("menuid",menuid);
		return new ModelAndView("report/xfzxgztjreport");
	}
	@RequestMapping(value = "/getReportDataJson")
	@ResponseBody
	public Object getReportDataJson(HttpServletRequest request){
		Object reportValue=this.sentenceAlterationService.getReportData(request);
		return reportValue;
	}
}
