package com.sinog2c.mvc.controller.commutationParole;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.model.commutationParole.TbxfMergeReference;
import com.sinog2c.model.commutationParole.TbxfPunishmentRang;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.CommutationReferenceService;
import com.sinog2c.service.api.commutationParole.MergeReferenceService;
import com.sinog2c.service.api.commutationParole.PunishmentRangService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.QualifierUtil;
import com.sinog2c.util.common.StringNumberUtil;

import flexjson.JSONDeserializer;
/**
 * 减刑幅度
 *
 */
@Controller
public class PunishmentRangController extends ControllerBase {
	
	@Resource
	private PunishmentRangService punishmentRangService;
	
	@Resource
	private SystemLogService logService;
	
	@Resource
	private CommutationReferenceService commutationReferenceService;
	
	@Resource
	private MergeReferenceService mergeReferenceService;
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toPunishmentRangListPage")
	public ModelAndView toPunishmentRangListPage(HttpServletRequest request) {
		returnResourceMap(request);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String screeningapprove = jyconfig.getProperty(GkzxCommon.SCREENINGAPPROVE);
		if(!StringNumberUtil.isNullOrEmpty(screeningapprove) && screeningapprove.equals(GkzxCommon.ONE)) {
			request.setAttribute("screeningapprove", "1");
		}
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/punishmentRangListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 减刑幅度列表
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetPunishmentRangList")
	@ResponseBody
	public Object ajaxGetPunishmentRangList(HttpServletRequest request){
		String departid = getLoginUser(request).getDepartid();
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String key = request.getParameter("key");
		int total = punishmentRangService.getCount(departid, key);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
//		String sortField = "PUNID";
//		String sortOrder = "asc";
		List<HashMap> list = punishmentRangService.getPunishmentRangList(departid, key, sortField, sortOrder, start, end);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "/toPunishmentRangAddPage")
	public ModelAndView toPunishmentRangAddPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/punishmentRangAddPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 删除数据
	 */
	@RequestMapping(value = "/deletePunishmentRang")
	@ResponseBody
	public String deletePunishmentRang(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		if(null!=ids&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				try {
					int punid = Integer.valueOf(ids[i]);
					punishmentRangService.deletePunishmentRang(punid);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					result = "error";
					status = 0;
				}
				SystemLog log = new SystemLog();
				log.setLogtype("减刑幅度参照删除操作");
				log.setOpaction("删除");
				log.setOpcontent("删除减刑幅度参照,punid= "+ids[i]);
				log.setOrgid(user.getUserid());
				log.setRemark("删除减刑幅度参照");
				log.setStatus(status);
				try {
					logService.add(log,user);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 下拉列表数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxSelectData" })
	@ResponseBody
	public void ajaxSelectData(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			String text = request.getParameter("text")==null?"":request.getParameter("text");
			String tableName = request.getParameter("table")==null?"":request.getParameter("table");
			List<HashMap> list = punishmentRangService.ajaxSelectData(id, text, tableName);
			String ajaxMainGetShuJuStr = "[";
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					if(i != list.size()-1){
						ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+list.get(i).get("ID")+"\""+",\"text\":"+"\""+list.get(i).get("TEXT")+"\"},";
					}else{
						ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+list.get(i).get("ID")+"\""+",\"text\":"+"\""+list.get(i).get("TEXT")+"\"}";
					}
				}
			}
			ajaxMainGetShuJuStr += "]";
			writer.write("" + ajaxMainGetShuJuStr);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下拉列表数据,剩余刑期
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxSelectPrisonerSentence" })
	@ResponseBody
	public void ajaxSelectPrisonerSentence(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/plain;charset=utf-8");
		try {
			SystemUser user = getLoginUser(request);
			PrintWriter writer = response.getWriter();
			List<HashMap> list = punishmentRangService
					.ajaxSelectPrisonerSentence(user.getDepartid());
			String ajaxMainGetShuJuStr = "[";
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i != list.size() - 1) {
						ajaxMainGetShuJuStr += "{" + "\"id\":" + "\"" + list.get(i).get("ID") + "\"" + ",\"text\":" + "\"" + list.get(i).get("TEXT") + "\"" + ",\"remark\":" + "\"" + list.get(i).get("REMARK") + "\"},";
					} else {
						ajaxMainGetShuJuStr += "{" + "\"id\":" + "\"" + list.get(i).get("ID") + "\"" + ",\"text\":" + "\"" + list.get(i).get("TEXT") + "\"" + ",\"remark\":" + "\"" + list.get(i).get("REMARK") + "\"}";
					}
				}
			}
			ajaxMainGetShuJuStr += "]";
			writer.write("" + ajaxMainGetShuJuStr);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存数据
	 * @return
	 */
	@RequestMapping(value = { "/savePunishmentRang" })
	@ResponseBody
	public String savePunishmentRang(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String operatetype = request.getParameter("operatetype")==null?"":request.getParameter("operatetype");
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String screeningapprove = jyconfig.getProperty(GkzxCommon.SCREENINGAPPROVE);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				//获取页面数据,空值默认为-1
				HashMap row = (HashMap)rows.get(i);
				int typeid = changeStringToInt((String)row.get("typeid"));
				int sn = changeStringToInt(String.valueOf(row.get("sn")));
				int phid = changeStringToInt((String)row.get("phid"));
				int syear = changeStringToInt((String)row.get("syear"));
				int eyear = changeStringToInt((String)row.get("eyear"));
				int type = changeStringToInt((String)row.get("type"));
				int gender = changeStringToInt((String)row.get("gender"));
				int senid = changeStringToInt((String)row.get("senid"));
				int sentenceType = changeStringToInt((String)row.get("sentenceType"));
				int originalstart = changeStringToInt((String)row.get("originalstart"));
				int originalend = changeStringToInt((String)row.get("originalend"));
				int sentencestart = changeStringToInt((String)row.get("sentencestart"));
				int sentenceend = changeStringToInt((String)row.get("sentenceend"));
				int startperiod = changeStringToInt((String)row.get("startperiod"));
				int intervalperiod = changeStringToInt((String)row.get("intervalperiod"));
				int endperiod = changeStringToInt((String)row.get("endperiod"));
				int endintervalperiod = changeStringToInt((String)row.get("endintervalperiod"));
				double intervalperiodscale = changeStringToDouble((String)row.get("intervalperiodscale"));
				double executionsentencescale = changeStringToDouble((String)row.get("executionsentencescale"));
				int executionsentencesstart = changeStringToInt((String)row.get("executionsentencesstart"));
				int executionsentencesend = changeStringToInt((String)row.get("executionsentencesend"));
				int nowsentencetype = changeStringToInt((String)row.get("nowsentencetype"));
				int rangelimit = changeStringToInt((String)row.get("rangelimit"));
				int executesentencelimit = changeStringToInt((String)row.get("executesentencelimit"));
				int firstcommutation = changeStringToInt((String)row.get("firstcommutation"));
				int alreadycommutation = changeStringToInt((String)row.get("alreadycommutation"));
				double executesentencescalelimit = changeStringToDouble((String)row.get("executesentencescalelimit"));
				//构造model
				TbxfPunishmentRang tbxfPunishmentRang = new TbxfPunishmentRang();
				tbxfPunishmentRang.setTypeid(typeid);
				tbxfPunishmentRang.setSn(sn);
				tbxfPunishmentRang.setPhid(phid);
				tbxfPunishmentRang.setSyear((short)syear);
				tbxfPunishmentRang.setEyear((short)eyear);
				tbxfPunishmentRang.setType((short)type);
				tbxfPunishmentRang.setGender((short)gender);
				tbxfPunishmentRang.setSenid(senid);
				tbxfPunishmentRang.setSentenceType(sentenceType);
				tbxfPunishmentRang.setIntervalperiodscale(intervalperiodscale);
				tbxfPunishmentRang.setExecutionsentencescale(executionsentencescale);
				tbxfPunishmentRang.setExecutionsentencesstart(executionsentencesstart);
				tbxfPunishmentRang.setExecutionsentencesend(executionsentencesend);
				tbxfPunishmentRang.setNowsentencetype(nowsentencetype);
				tbxfPunishmentRang.setRangelimit(rangelimit);
				if(!StringNumberUtil.isNullOrEmpty(screeningapprove) && screeningapprove.equals(GkzxCommon.ONE)) {
					tbxfPunishmentRang.setApprovetype((short)1);
				} else {
					tbxfPunishmentRang.setApprovetype((short)0);
				}
				
				if(nowsentencetype==1) {
					tbxfPunishmentRang.setSentencestart(sentencestart);
					tbxfPunishmentRang.setSentenceend(sentenceend);
				} else if(nowsentencetype==2) {
					tbxfPunishmentRang.setSentencestart(-2);
					tbxfPunishmentRang.setSentenceend(-2);
				} else if(nowsentencetype==3) {
					tbxfPunishmentRang.setSentencestart(-3);
					tbxfPunishmentRang.setSentenceend(-3);
				} else {
					tbxfPunishmentRang.setSentencestart(sentencestart);
					tbxfPunishmentRang.setSentenceend(sentenceend);
				}
				
				if(sentenceType==1) {
					tbxfPunishmentRang.setOriginalstart(originalstart);
					tbxfPunishmentRang.setOriginalend(originalend);
				} else if(sentenceType==2) {
					tbxfPunishmentRang.setOriginalstart(-2);
					tbxfPunishmentRang.setOriginalend(-2);
				} else if(sentenceType==3) {
					tbxfPunishmentRang.setOriginalstart(-3);
					tbxfPunishmentRang.setOriginalend(-3);
				} else {
					tbxfPunishmentRang.setOriginalstart(originalstart);
					tbxfPunishmentRang.setOriginalend(originalend);
				}
				tbxfPunishmentRang.setStartperiod(startperiod);
				tbxfPunishmentRang.setIntervalperiod(intervalperiod);
				tbxfPunishmentRang.setEndperiod(endperiod);
				tbxfPunishmentRang.setEndintervalperiod(endintervalperiod);
				tbxfPunishmentRang.setDelflag((short)0);
				tbxfPunishmentRang.setOpid(user==null?"-1":user.getUserid());
				tbxfPunishmentRang.setOptime(new Date());
				tbxfPunishmentRang.setDepartid(user.getDepartid());
				tbxfPunishmentRang.setExecutesentencelimit(executesentencelimit);
				tbxfPunishmentRang.setExecutesentencescalelimit(executesentencescalelimit);
				tbxfPunishmentRang.setFirstcommutation(firstcommutation);
				tbxfPunishmentRang.setAlreadycommutation(alreadycommutation);
				//保存数据
				if("new".equals(operatetype)){
					try {
						punishmentRangService.insertPunishmentRang(tbxfPunishmentRang);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("减刑幅度参照添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加减刑幅度参照");
					log.setOrgid(user.getUserid());
					log.setRemark("添加减刑幅度参照");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("edit".equals(operatetype)){
					int punid = Integer.valueOf(request.getParameter("punid"));
					tbxfPunishmentRang.setPunid(punid);
					try {
						punishmentRangService.updatePunishmentRang(tbxfPunishmentRang);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("减刑幅度参照修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改减刑幅度参照,punid="+punid);
					log.setOrgid(user.getUserid());
					log.setRemark("修改减刑幅度参照");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 跳转至修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toPunishmentRangEditPage")
	public ModelAndView toPunishmentRangEditPage(HttpServletRequest request) {
		int punid = Integer.parseInt(request.getParameter("punid"));
		TbxfPunishmentRang tbxfPunishmentRang = punishmentRangService.getPunishmentRangById(punid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("punid", punid);
		map.put("sn", tbxfPunishmentRang.getSn());
		map.put("typeid", tbxfPunishmentRang.getTypeid());
		map.put("phid", tbxfPunishmentRang.getPhid());
		map.put("syear", tbxfPunishmentRang.getSyear()==-1?"":tbxfPunishmentRang.getSyear());
		map.put("eyear", tbxfPunishmentRang.getEyear()==-1?"":tbxfPunishmentRang.getEyear());
		map.put("type", tbxfPunishmentRang.getType());
		map.put("gender", tbxfPunishmentRang.getGender());
		map.put("senid", tbxfPunishmentRang.getSenid());
		map.put("sentenceType", tbxfPunishmentRang.getSentenceType());
		map.put("executionsentencesstart", tbxfPunishmentRang.getExecutionsentencesstart());
		map.put("executionsentencesend", tbxfPunishmentRang.getExecutionsentencesend());
		map.put("firstcommutation", tbxfPunishmentRang.getFirstcommutation());
		map.put("alreadycommutation", tbxfPunishmentRang.getAlreadycommutation());
		if(tbxfPunishmentRang.getOriginalstart()==-1 || tbxfPunishmentRang.getOriginalstart()==-2 || tbxfPunishmentRang.getOriginalstart()==-3) {
			map.put("originalstart", "");
		} else {
			map.put("originalstart", tbxfPunishmentRang.getOriginalstart());
		}
		if(tbxfPunishmentRang.getOriginalend()==-1 || tbxfPunishmentRang.getOriginalend()==-2 || tbxfPunishmentRang.getOriginalend()==-3) {
			map.put("originalend", "");
		} else {
			map.put("originalend", tbxfPunishmentRang.getOriginalend());
		}
		if(tbxfPunishmentRang.getExecutionsentencesstart()==-1) {
			map.put("executionsentencesstart", "");
		}
		if(tbxfPunishmentRang.getExecutionsentencesend()==-1) {
			map.put("executionsentencesend", "");
		}
		
		if(tbxfPunishmentRang.getSentencestart()==-1 || tbxfPunishmentRang.getSentencestart()==-2 || tbxfPunishmentRang.getSentencestart()==-3) {
			map.put("sentencestart", "");
		} else {
			map.put("sentencestart", tbxfPunishmentRang.getSentencestart());
		}
		if(tbxfPunishmentRang.getSentenceend()==-1 || tbxfPunishmentRang.getSentenceend()==-2 || tbxfPunishmentRang.getSentenceend()==-3) {
			map.put("sentenceend", "");
		} else {
			map.put("sentenceend", tbxfPunishmentRang.getSentenceend());
		}
		
		map.put("nowsentencetype", tbxfPunishmentRang.getNowsentencetype());
		map.put("startperiod", tbxfPunishmentRang.getStartperiod()==-1?"":tbxfPunishmentRang.getStartperiod());
		map.put("endperiod", tbxfPunishmentRang.getEndperiod()==-1?"":tbxfPunishmentRang.getEndperiod());
		map.put("intervalperiod", tbxfPunishmentRang.getIntervalperiod()==-1?"":tbxfPunishmentRang.getIntervalperiod());
		map.put("endintervalperiod", tbxfPunishmentRang.getEndintervalperiod()==-1?"":tbxfPunishmentRang.getEndintervalperiod());
		map.put("delflag", tbxfPunishmentRang.getDelflag());
		map.put("intervalperiodscale", tbxfPunishmentRang.getIntervalperiodscale()==0.0?"":tbxfPunishmentRang.getIntervalperiodscale());
		map.put("executionsentencescale", tbxfPunishmentRang.getExecutionsentencescale()==0.0?"":tbxfPunishmentRang.getExecutionsentencescale());
		map.put("rangelimit", tbxfPunishmentRang.getRangelimit()==-1?"":tbxfPunishmentRang.getRangelimit());
		map.put("executesentencelimit", tbxfPunishmentRang.getExecutesentencelimit()==-1?"":tbxfPunishmentRang.getExecutesentencelimit());
		map.put("executesentencescalelimit", tbxfPunishmentRang.getExecutesentencescalelimit()==0.0?"":tbxfPunishmentRang.getExecutesentencescalelimit());

		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/punishmentRangAddPage.jsp");
		mav = new ModelAndView(view,"tbxfPunishmentRang",map);
		return mav;
	}
	
	@RequestMapping(value = "/ajaxCheckSentenceTime")
	@ResponseBody
	public Object ajaxCheckSentenceTime(HttpServletRequest request){
		int senid = Integer.valueOf(request.getParameter("senid"));
		return punishmentRangService.getPrisonersentenceById(senid);
	}
	
	/**
	 * 将json转化为ArrayList
	 * @param json
	 * @return
	 */
	public Object Decode(String json) {
		if (null==json||"".equals(json)) return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		Object obj = deserializer.deserialize(json);
		if(obj != null && obj.getClass() == String.class){
			return Decode(obj.toString());
		}
		return obj;
	}
	
	
	/**
	 * 将String转化为int，如果为null或者"",则返回-1
	 * @param str
	 * @return
	 */
	public int changeStringToInt(String str) {
		int returnValue = 0;
		if(null==str || "".equals(str)){
			returnValue = -1;
		}else{
			try {
				returnValue = Integer.valueOf(str);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("不能将\""+str+"\"转化为int类型！");
			}
		}
		return returnValue;
	}
	
	public double changeStringToDouble(String str) {
		double returnValue = 0;
		if(null!=str && !"".equals(str)){
			try {
				returnValue = Double.valueOf(str);
			} catch (NumberFormatException e) {
				System.out.println("不能将\""+str+"\"转化为double类型！");
			}
		}
		return returnValue;
	}
	
	/**
	 * 跳转至方案导入页面
	 * @return
	 */
	@RequestMapping(value = "/toImportschemePage")
	public ModelAndView toImportschemePage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/importschemePage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 获取模板类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetschemedepart")
	@ResponseBody
	public List<HashMap<String,Object>> ajaxGetschemedepart(HttpServletRequest request){
		String departid = getLoginUser(request).getOrgid();
		List<HashMap<String,Object>> list = punishmentRangService.getSchemeDepart(departid);
		return list;
	}
	
	@RequestMapping(value = "/importschemeByorgid")
	@ResponseBody
	@Transactional
	public Object importschemeByorgid(HttpServletRequest request){
		String result = "success";
		String departid = request.getParameter("departid");
		String newdepartid = getLoginUser(request).getDepartid();
		if(departid.equals(newdepartid)) {
			return result;
		}
		List<TbxfPunishmentRang> rangList = punishmentRangService.getrangdataBydepartid(departid);
		Map<String,Integer> punidMap = new HashMap<String,Integer>();
		List<TbxfPunishmentRang> oldrangList = punishmentRangService.getrangdataBydepartid(newdepartid);
		for(int i=0; i<oldrangList.size(); i++) {
			punishmentRangService.deletePunishmentRang(oldrangList.get(i).getPunid());
		}
		for(int i=0; i<rangList.size(); i++) {
			//复制从宽从严
			Map<String,Object> map = new HashMap<String,Object>();
			int punid = punishmentRangService.getPunid();
			TbxfPunishmentRang record = rangList.get(i);
			punidMap.put(record.getPunid().toString(), punid);
			map.put("punid", punid);
			map.put("oldpunid", record.getPunid().toString());
			record.setPunid(punid);
			record.setDepartid(newdepartid);
			punishmentRangService.insertPunishmentRangAuto(record);
			punishmentRangService.insertTbxfWideandthinscheme(map);
		}
		List<TbxfCommutationReference> refList = punishmentRangService.getCommutationReferenceListData(departid);
		Map<String,Integer> refidMap = new HashMap<String,Integer>();
		for(int i=0; i<refList.size(); i++) {
			int refid = punishmentRangService.getPunid();
			TbxfCommutationReference record = refList.get(i);
			refidMap.put(refList.get(i).getRefid().toString(), refid);
			record.setRefid(refid);
			record.setPunid(punidMap.get(record.getPunid().toString()));
			commutationReferenceService.insertAuto(record);
		}
		
		List<TbxfMergeReference> mergeList = punishmentRangService.getMergeListData(departid);
		for(int i=0; i<mergeList.size(); i++) {
			int mergeid = punishmentRangService.getPunid();
			TbxfMergeReference record = mergeList.get(i);
			record.setMergeid(mergeid);
			record.setRefid(refidMap.get(record.getRefid().toString()));
			mergeReferenceService.insertMergeReferenceAuto(record);
		}
		return result;
	}
	
	@RequestMapping(value = "/updateApproveType")
	@ResponseBody
	public String updateApproveType(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("id");
		TbxfPunishmentRang record = new TbxfPunishmentRang();
		try {
			int punid = Integer.valueOf(id);
			record.setPunid(punid);
			record.setApprovetype((short)1);
			punishmentRangService.updateApproveType(record);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = "error";
			status = 0;
		}
		SystemLog log = new SystemLog();
		log.setLogtype("减刑幅度参照生效操作");
		log.setOpaction("生效");
		log.setOpcontent("生效减刑幅度参照,punid= "+id);
		log.setOrgid(user.getUserid());
		log.setRemark("生效减刑幅度参照");
		log.setStatus(status);
		try {
			logService.add(log,user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 跳转减刑类型描述列表页面
	 * @author zhenghui
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toSentenceTypePage.page")
	public ModelAndView toSentenceTypePage(HttpServletRequest request){
		returnResourceMap(request);
		ModelAndView mav = null;
		mav = new ModelAndView("commutationParole/toSentenceTypePage");
		return mav;
	}
	
	/**
	 * 获取减刑类型描述列表数据
	 * @author zhenghui
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxSentenceType.json")
	@ResponseBody
	public Object ajaxSentenceType(HttpServletRequest request){
		Map map = new HashMap();
		HashMap<String, Object> result = new HashMap<String, Object>();
		SystemUser user = getLoginUser(request);
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String key = request.getParameter("key");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		map.put("start", start);
		map.put("end", end);
		map.put("key", key);
		map.put("departid", user.getDepartid());
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		
		int count = punishmentRangService.countSentenc(map);
		List<Map> list = punishmentRangService.selectSentenc(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 跳转至减刑类型描述维护页面
	 * @author zhenghui
	 * @return
	 */
	@RequestMapping(value = "/modifySentenceTypePage.page")
	public ModelAndView modifySentenceTypePage() {
		ModelAndView mav = null;
		mav = new ModelAndView("commutationParole/modifySentenceTypePage");
		return mav;
	}
	/**
	 * 减刑类型描述新增、修改保存
	 * @author zhenghui
	 * @return
	 */
	@RequestMapping(value = "/ajaxBySentenceType.json")
	@ResponseBody
	public Object ajaxBySentenceType(HttpServletRequest request){
		JSONMessage message = JSONMessage.newMessage();
		String senid = request.getParameter("senid");
		Map map = punishmentRangService.getSentencInfo(Integer.valueOf(senid));
		return map;
	}
	/**
	 * 减刑类型描述新增、修改保存
	 * @author zhenghui
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxBySaveSentence.json")
	@ResponseBody
	public Object ajaxBySaveSentence(HttpServletRequest request){
		int result = 0;
		short status = 1;
		SystemUser user = getLoginUser(request);
		String resid = request.getParameter("resid");
		String data = request.getParameter("data");
		String optype = request.getParameter("optype");
		try {
			if(!StringNumberUtil.isNullOrEmpty(data)){
				ArrayList rows = (ArrayList)Decode(data);
				HashMap map = (HashMap)rows.get(0);
				map.put("departid", user.getDepartid());
				map.put("opid", user.getUserid());
				result = punishmentRangService.ajaxBySaveSentence(map,optype);
			}
			//
		} catch (NumberFormatException e) {
			e.printStackTrace();
			status = 0;
		}
		SystemLog log = new SystemLog();
		log.setLogtype("减刑类型描述插入/修改操作");
		log.setOpaction("插入/修改");
		log.setOpcontent("减刑类型描述 resid:"+resid);
		log.setOrgid(user.getUserid());
		log.setRemark("减刑类型描述");
		log.setStatus(status);
		try {
			logService.add(log,user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 减刑类型描述删除
	 * @author zhenghui
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxByRemoveSentence.json")
	@ResponseBody
	public Object ajaxByRemoveSentence(HttpServletRequest request){
		int result = 0;
		short status = 1;
		SystemUser user = getLoginUser(request);
		String resid = request.getParameter("resid");
		String senid = request.getParameter("senid");
		try {
			if(!StringNumberUtil.isNullOrEmpty(senid)){
				result = punishmentRangService.ajaxByRemoveSentence(senid);
			}
			//
		} catch (NumberFormatException e) {
			e.printStackTrace();
			status = 0;
		}
		SystemLog log = new SystemLog();
		log.setLogtype("减刑类型描述删除操作");
		log.setOpaction("删除");
		log.setOpcontent("减刑类型描述 resid:"+resid);
		log.setOrgid(user.getUserid());
		log.setRemark("减刑类型描述");
		log.setStatus(status);
		try {
			logService.add(log,user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 减刑从严从宽 树
	 * @author zhenghui
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxByWideandthins.json")
	@ResponseBody
	public Object ajaxByWideandthins(HttpServletRequest request){
		List<Map> result = punishmentRangService.ajaxByWideandthins();
		return result;
	}
	
	/**
	 * 减刑从严从宽 树
	 * @author zhenghui
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxCreateWideandthins.action")
	@ResponseBody
	public Object ajaxCreateWideandthins(HttpServletRequest request){
		int result = 0;
		result = punishmentRangService.ajaxCreateWideandthins(request);
		return result;
	}
	
	/**
	 * @Package com.sinog2c.mvc.controller.commutationParole 
	 * @Description: 幅度减刑（同意）
	 * @date 2015-5-5 上午10:54:29 
	 * @version V3.0
	 */
	@RequestMapping(value = "/editPunishmentRang.json")
	@ResponseBody
	public Object editPunishmentRang(HttpServletRequest request){
		String punid = request.getParameter("id");
		int result = 0;
		if(!StringNumberUtil.isNullOrEmpty(punid)){
			TbxfPunishmentRang record = new TbxfPunishmentRang();
			record.setPunid(Integer.valueOf(punid));
			record.setApprovetype(Short.valueOf("1"));
			result = punishmentRangService.editPunishmentRang(record);
			
		}
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 跳转列表页
	 */
	@RequestMapping(value = "/toQualifierItemPage.page")
	public ModelAndView qualifierItemPage(HttpServletRequest request) {
		
		Map<String,Object> paramap = this.parseParamMap(request);
		this.addMap2Attribute(paramap, request);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/operateQualifierItemPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 跳转列表页
	 */
	@RequestMapping(value = "/toQualifierSetPage.page")
	public ModelAndView qualifierSetPage(HttpServletRequest request) {
		
		Map<String,Object> paramap = this.parseParamMap(request);
		this.addMap2Attribute(paramap, request);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/qualifierSetPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	
	
	@RequestMapping(value = "/getQualifierItem.json")
	@ResponseBody
	public Object getQualifierItem(HttpServletRequest request) {
		JSONMessage message = JSONMessage.newMessage();
		Map<String,Object> paramap = this.parseParamMap(request);
		SystemUser user = this.getLoginUser(request);
		paramap.put("departid", user.getDepartid());
//		paramap.put("itemlevel", 1);
		// 取得所有数据
		List<Map<String,Object>> items = punishmentRangService.getQualifierItem(paramap);
		message.setData(items);
		
		return message.getData();
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/operateQualifierPage.page")
	public ModelAndView operateQualifierPage(HttpServletRequest request) {
		
		Map<String,Object> paramap = this.parseParamMap(request);
		this.addMap2Attribute(paramap, request);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/operateQualifierPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/removeQualifierSet.json" })
	@ResponseBody
	public Object removeQualifierSet(HttpServletRequest request){
		//
		Map<String,Object> paramap = this.parseParamMap(request);
		int row = punishmentRangService.removeQualifierSet(paramap);
		
		return row;
	}
	
	/**
	 * 保存数据
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/saveQualifierSet.json" })
	@ResponseBody
	public Object saveQualifierSet(HttpServletRequest request){
		//
		String operatetype = request.getParameter("operatetype");
		Map<String,Object> paramap = this.parseParamMap(request);
		SystemUser user = getLoginUser(request);
		paramap.put("departid", user.getDepartid());
		int row = punishmentRangService.saveQualifierSet(operatetype,paramap);
		
		return row;
	}
	
	/**
	 * 保存数据
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/saveQualifierItem.json" })
	@ResponseBody
	public Object saveQualifierItem(HttpServletRequest request){
		//
		String operatetype = request.getParameter("operatetype");
		Map<String,Object> paramap = this.parseParamMap(request);
		SystemUser user = getLoginUser(request);
		paramap.put("departid", user.getDepartid());
		int row = punishmentRangService.saveQualifierItem(operatetype,paramap);
		
		return row;
	}
	
	/**
	 * 测试表达式数据
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/validateExpression.json" })
	@ResponseBody
	public Object validateExpression(HttpServletRequest request){
		Object result = null;
		//
		Map<String,Object> paramap = this.parseParamMap(request);
		paramap = MapUtil.valueStr2Num(paramap);
		String expression = StringNumberUtil.getStrFromMap("expression", paramap);
		expression = StringNumberUtil.removeFirstStr(expression, "&&");
		
		List<Variable> variables = QualifierUtil.assembleVariable(paramap);
		
		try{
			result = ExpressionEvaluator.evaluate(expression, variables);
		}catch(Exception e){
			return 0;
		}
		
		if(result instanceof Boolean){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/removeQualifierItem.json" })
	@ResponseBody
	public Object removeQualifierItem(HttpServletRequest request){
		//
		Map<String,Object> paramap = this.parseParamMap(request);
		int row = punishmentRangService.removeQualifierItem(paramap);
		
		return row;
	}
}
