package com.sinog2c.mvc.controller.commutationParole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.commutationParole.TbdataSentchageKey;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

/*
*  河北判裁信息补录
*/
@Controller
public class SentenceInfoManageController extends ControllerBase{
	@Resource
	private TbdataSentchageService tbdataSentchageService;
	@Resource
	private SystemCodeService systemCodeService;
	@Resource
	public SystemLogService logService;
	
	@RequestMapping(value = "sentenceInfoManageCriminalChoose")
	public ModelAndView DoJSWTHPage(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/sentenceInfoManageCriminalChoose");
	}
	
	@RequestMapping(value = "/toSentenceInfoListPage")
	public ModelAndView toSentenceInfoListPage(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("crimid", crimid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/hebei/sentenceInfoListPage.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}
	
	@RequestMapping(value = "/ajaxGetSentenceInfoList")
	@ResponseBody
	public Object ajaxGetSentenceInfoList(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("crimid", crimid);
		int total = tbdataSentchageService.getOrgSentenceInfoCount(parameterMap);
		List<Map> list = tbdataSentchageService.getOrgSentenceInfoList(parameterMap);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	@RequestMapping(value = "/toSentenceInfoAddPage")
	public ModelAndView toSentenceInfoAddPage(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crimid",crimid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/hebei/sentenceInfoAddPage.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}

	@RequestMapping(value = "/toMaincaseSelectTreePage")
	public ModelAndView toMaincaseSelectTreePage(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/hebei/maincaseSelectTreeWindow.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}
	
	@RequestMapping(value = "/toAreaSelectTreePage")
	public ModelAndView toAreaSelectTreePage(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/hebei/areaSelectTreeWindow.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}
	
	@RequestMapping(value = "/ajaxAreaSelectData.action")
	@ResponseBody
	public Object ajaxAreaSelectData(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		List<TbsysCode> list = systemCodeService.listByCodetype(codetype);
		return list;
	}
	
	@RequestMapping(value = { "/saveSentenceInfo.action" })
	@ResponseBody
	public String saveSentenceInfo(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String crimid = request.getParameter("crimid");
		String operatetype = request.getParameter("operatetype");
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)JsonUtil.Decode(json);
		TbdataSentchage record = new TbdataSentchage();
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				record.setCourtname((String)row.get("courtname"));
				record.setCourttitle((String)row.get("courttitle"));
				String sentenceyear = (String)row.get("sentenceyear")==""?"":iscontains((String)row.get("sentenceyear"));
				String sentencemonth = (String)row.get("sentencemonth")==""?"":iscontains((String)row.get("sentencemonth"));
				String sentenceday = (String)row.get("sentenceday")==""?"":iscontains((String)row.get("sentenceday"));
				record.setSentence(getYearMonthday(sentenceyear,sentencemonth,sentenceday));	
				String losepoweryear = (String)row.get("losepoweryear")==""?"":iscontains((String)row.get("losepoweryear"));
				String losepowermonth = (String)row.get("losepowermonth")==""?"":iscontains((String)row.get("losepowermonth"));
				String losepowerday = (String)row.get("losepowerday")==""?"":iscontains((String)row.get("losepowerday"));
				record.setLosepower(getYearMonthday(losepoweryear,losepowermonth,losepowerday));
				record.setSentence(getYearMonthday(sentenceyear,sentencemonth,sentenceday));
				//record.setLosepower(getYearMonthday(losepoweryear,losepowermonth,losepowerday));
				record.setRemark((String)row.get("remark"));
				record.setFine(row.get("fine")==null?"":row.get("fine").toString()); 
				record.setExpropriationinfo(row.get("expropriationinfo")==null?"":row.get("expropriationinfo").toString());
				record.setCourtsn((String)row.get("courtsn"));
				record.setCourtyear((String)row.get("courtyear"));
				record.setCourtshort((String)row.get("courtshort"));
				record.setCategory(GkzxCommon.ONE);
				record.setCrimid(crimid);
				try {
					record.setCourtsanction(DateUtil.dateFormatForAip(row.get("courtsanction")));
					SystemLog log = new SystemLog();
					if(operatetype.equals("new")) {
						tbdataSentchageService.insertSelective(record);
						log.setLogtype("一审判决新增操作");
						log.setOpaction("新增");
						log.setOpcontent("新增一审判决信息,crimid="+crimid);
						log.setOrgid(user.getUserid());
						log.setRemark("新增一审判决");
						log.setStatus(status);
					} else {
						tbdataSentchageService.updateByExampleSelective(record);
						log.setLogtype("一审判决修改操作");
						log.setOpaction("修改");
						log.setOpcontent("修改一审判决信息,crimid=" + crimid);
						log.setOrgid(user.getUserid());
						log.setRemark("修改一审判决");
						log.setStatus(status);
					}
					logService.add(log,user);
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					status = 0;
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/toSentenceInfoEditPage")
	public ModelAndView toSentenceInfoEditPage(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String courtsanctiondate = request.getParameter("courtsanctiondate");
		TbdataSentchage record = new TbdataSentchage();
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("crimid", crimid);
		paramap.put("courtsanction", courtsanctiondate);
		record = tbdataSentchageService.selectDataByPk(paramap);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crimid", crimid);
		map.put("courtname", record.getCourtname());
		map.put("courttitle", record.getCourttitle());
		map.put("courtsanction", record.getCourtsanction());
		map.put("courtyear", record.getCourtyear());
		map.put("courtsn", record.getCourtsn());
		map.put("courtshort", record.getCourtshort());
		String sentence = record.getSentence();
		if(!StringNumberUtil.isNullOrEmpty(sentence)) {
			if(sentence.length()>=6) {
				map.put("sentenceyear", (sentence.substring(0, sentence.indexOf("年")+1)).length()==0?"":(sentence.substring(0, sentence.indexOf("年")+1)).toString());
				map.put("sentencemonth", (sentence.substring(sentence.indexOf("年")+1, sentence.indexOf("月")+1)).length()==0?"":(sentence.substring(sentence.indexOf("年")+1, sentence.indexOf("月")+1)).toString());
				map.put("sentenceday", (sentence.substring(sentence.indexOf("月")+1, sentence.length())).length()==0?"":(sentence.substring(sentence.indexOf("月")+1, sentence.length())).toString());
			}else {
				map.put("sentenceyear", record.getSentence());
			}
		}
		String losepower = record.getLosepower();
		if(!StringNumberUtil.isNullOrEmpty(losepower)) {
			if(losepower.length()>2) {
				map.put("losepoweryear", (losepower.substring(0, losepower.indexOf("年")+1)).length()==0?"":(losepower.substring(0, losepower.indexOf("年")+1)).toString());
				map.put("losepowermonth", (losepower.substring(losepower.indexOf("年")+1, losepower.indexOf("月")+1)).length()==0?"":(losepower.substring(losepower.indexOf("年")+1, losepower.indexOf("月")+1)).toString());
				map.put("losepowerday", (losepower.substring(losepower.indexOf("月")+1, losepower.length())).length()==0?"":(losepower.substring(losepower.indexOf("月")+1, losepower.length())).toString());
			} else {
				map.put("losepoweryear", losepower);
			}
		}
		map.put("remark", record.getRemark());
		map.put("fine", record.getFine());
		map.put("expropriationinfo", record.getExpropriationinfo());
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/hebei/sentenceInfoAddPage.jsp");
		mav = new ModelAndView(view,"record",map);
		return mav;
	}

	@RequestMapping(value = "/deleteSentenceInfo")
	@ResponseBody
	public String deleteSentenceInfo(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		String courtsanction = request.getParameter("courtsanctiondate");
		TbdataSentchageKey key = new TbdataSentchageKey();
		key.setCrimid(crimid);
		key.setCourtsanction(courtsanction);
		try {
			tbdataSentchageService.deleteSentenceInfo(key);
			SystemLog log = new SystemLog();
			log.setLogtype("一审判决删除操作");
			log.setOpaction("一审判决删除");
			log.setOpcontent("删除一审判决,罪犯编号= "+crimid);
			log.setOrgid(user.getUserid());
			log.setRemark("删除一审判决");
			log.setStatus(status);
			logService.add(log,user);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = "error";
			status = 0;
		}
		return result;
	}
	
	private String getYearMonthday(String year,String month,String day) {
		String ymd = "";
		if(!StringNumberUtil.isNullOrEmpty(year)) {
			if(year.length()==4) {
				ymd = year;
				return ymd;
			} else if(year.length()==1){
				ymd = GkzxCommon.ZERO + year;
			} else if(year.equals(GkzxCommon.LOSEPOWER_97)) {
				ymd = year;
				return ymd;
			} else {
				ymd = year;
			}
		} else {
			ymd = "00";
		}
		
		if(!StringNumberUtil.isNullOrEmpty(month)) {
			if(month.length()==1) {
				ymd = ymd + GkzxCommon.ZERO + month;
			} else {
				ymd = ymd + month;
			}
		} else {
			ymd = ymd + "00";
		}
		
		if(!StringNumberUtil.isNullOrEmpty(day)) {
			if(day.length()==1) {
				ymd = ymd + GkzxCommon.ZERO + day;
			} else {
				ymd = ymd + day;
			}
		} else {
			ymd = ymd + "00";
		}
		return ymd;
	}
	public String  iscontains(String beforestring){
		String afterstr="";
		if(beforestring.length()>0&&beforestring.contains("年")){
			afterstr=beforestring.replace("年", "");
		}else if(beforestring.length()>0&&beforestring.equals("无期徒刑")){
			afterstr=beforestring.replace("无期徒刑", "9995");
		}else if(beforestring.length()>0&&beforestring.equals("死缓")){
			afterstr=beforestring.replace("死缓", "9996");
		}else if(beforestring.length()>0&&beforestring.equals("终身")){
			afterstr=beforestring.replace("终身", "97");
		}else if(beforestring.length()>0&&beforestring.contains("个月")){
			afterstr=beforestring.replace("个月", "");
		}else if(beforestring.length()>0&&beforestring.contains("日")){
			afterstr=beforestring.replace("日", "");
		}else if(beforestring.length()>0&&beforestring.contains("天")){
			afterstr=beforestring.replace("天", "");
		}else	afterstr=beforestring;
		return afterstr.trim();
	}

}
