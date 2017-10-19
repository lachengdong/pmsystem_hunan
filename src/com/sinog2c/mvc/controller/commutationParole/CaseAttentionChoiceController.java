package com.sinog2c.mvc.controller.commutationParole;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.commutationParole.TbcourtCaseAttentionChoice;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.CaseAttentionChoiceService;
import com.sinog2c.service.api.system.SystemLogService;

import flexjson.JSONDeserializer;


@Controller
@RequestMapping("/commutationParole")
public class CaseAttentionChoiceController extends ControllerBase {
	
	@Resource
	private CaseAttentionChoiceService caseAttentionChoiceService;
	
	@Resource
	private SystemLogService logService;
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toCaseAttentionChoiceListPage.action")
	public ModelAndView toCaseAttentionChoiceListPage(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav = null;
		mav = new ModelAndView("commutationParole/caseAttentionChoiceListPage");
		return mav;
	}
	
	/**
	 * 方案列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetCaseAttentionChoiceList.action")
	@ResponseBody
	public Object ajaxGetCaseAttentionChoiceList(HttpServletRequest request){
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String key = request.getParameter("key");
		int total = caseAttentionChoiceService.getCount(key);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = "OPTIME";
		String sortOrder = "desc";
		List<HashMap> list = decodeCaseAttentionChoice(caseAttentionChoiceService.getCaseAttentionChoiceList(key, sortField, sortOrder, start, end));
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 删除数据
	 */
	@RequestMapping(value = "/deleteCaseAttentionChoice")
	@ResponseBody
	public String deleteCaseAttentionChoice(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		if(null!=ids&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				try {
					int cacid = Integer.valueOf(ids[i]);
					caseAttentionChoiceService.deleteCaseAttentionChoice(cacid);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "error";
					status = 0;
				}
				SystemLog log = new SystemLog();
				log.setLogtype("案件查看方案删除操作");
				log.setOpaction("删除");
				log.setOpcontent("删除案件查看方案,CACID= "+ids[i]);
				log.setOrgid(user.getUserid());
				log.setRemark("删除案件查看方案");
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
	 * 编辑页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toCaseAttentionChoiceAddPage.action")
	public ModelAndView toCaseAttentionChoiceAddPage(HttpServletRequest request) {
		String cacidStr = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		if(null!=cacidStr&&!"".equals(cacidStr)){
			int cacid = Integer.parseInt(cacidStr);
			TbcourtCaseAttentionChoice record = caseAttentionChoiceService.getCaseAttentionChoiceById(cacid);
			if(null!=record){
				map.put("cacid", cacid);
				map.put("sn", record.getSn());
				map.put("name", record.getName());
				map.put("characteristic", record.getCharacteristic());
				map.put("crimetype", record.getCrimetype());
				map.put("inprison", record.getInprison());
				map.put("dutylevel", record.getDutylevel());
				map.put("court", record.getCourt());
				map.put("receivestart", record.getReceivestart());
				map.put("receiveend", record.getReceiveend());
				map.put("crimename", record.getCrimename());
				map.put("originalsentence", record.getOriginalsentence());
				map.put("opid", record.getOpid());
				map.put("optime", record.getOptime());
			}
		}
		ModelAndView mav = new ModelAndView("commutationParole/caseAttentionChoiceAddPage","record",map);
		return mav;
	}
	/**
	 * 保存数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/saveCaseAttentionChoice" })
	@ResponseBody
	public String saveCaseAttentionChoice(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String operatetype = request.getParameter("operatetype")==null?"":request.getParameter("operatetype");
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				int sn = (Integer)row.get("sn");
				String name = (String)row.get("name")==null?"":(String)row.get("name");
				String characteristic = (String)row.get("characteristic")==null?"":(String)row.get("characteristic");
				String crimetype = (String)row.get("crimetype")==null?"":(String)row.get("crimetype");
				String inprison = (String)row.get("inprison")==null?"":(String)row.get("inprison");
				String dutylevel = (String)row.get("dutylevel")==null?"":(String)row.get("dutylevel");
				String court = (String)row.get("court")==null?"":(String)row.get("court");
				String receivestartStr = (String)row.get("receivestart")==null?"":(String)row.get("receivestart");
				String receiveendStr = (String)row.get("receiveend")==null?"":(String)row.get("receiveend");
				String crimename = (String)row.get("crimename")==null?"":(String)row.get("crimename");
				String originalsentence = (String)row.get("originalsentence")==null?"":(String)row.get("originalsentence");
				TbcourtCaseAttentionChoice tbcourtCaseAttentionChoice = new TbcourtCaseAttentionChoice();
				tbcourtCaseAttentionChoice.setSn((short)sn);
				tbcourtCaseAttentionChoice.setName(name);
				tbcourtCaseAttentionChoice.setCharacteristic(characteristic);
				tbcourtCaseAttentionChoice.setCrimetype(crimetype);
				tbcourtCaseAttentionChoice.setCourt(court);
				tbcourtCaseAttentionChoice.setCrimename(crimename);
				tbcourtCaseAttentionChoice.setDutylevel(dutylevel);
				tbcourtCaseAttentionChoice.setInprison(inprison);
				tbcourtCaseAttentionChoice.setOpid(user.getUserid());
				tbcourtCaseAttentionChoice.setOriginalsentence(originalsentence);
				try {
					tbcourtCaseAttentionChoice.setReceiveend(new SimpleDateFormat("yyyy-MM-dd").parse(receivestartStr));
					tbcourtCaseAttentionChoice.setReceivestart(new SimpleDateFormat("yyyy-MM-dd").parse(receiveendStr));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//保存数据
				if("new".equals(operatetype)){
					try {
						caseAttentionChoiceService.insertCaseAttentionChoice(tbcourtCaseAttentionChoice);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("案件查看方案照添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加案件查看方案");
					log.setOrgid(user.getUserid());
					log.setRemark("添加案件查看方案");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("edit".equals(operatetype)){
					Integer cacid = Integer.valueOf(request.getParameter("cacid"));
					tbcourtCaseAttentionChoice.setCacid(cacid);
					try {
						caseAttentionChoiceService.updateCaseAttentionChoice(tbcourtCaseAttentionChoice);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("案件查看方案修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改案件查看方案,CACID="+cacid);
					log.setOrgid(user.getUserid());
					log.setRemark("修改案件查看方案");
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
	 * 获取code数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxGetCodeData.action" })
	@ResponseBody
	public void ajaxGetCodeData(HttpServletRequest request, HttpServletResponse response){
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			String codeType=request.getParameter("codeType");
			List<HashMap<String, String>> list = caseAttentionChoiceService.ajaxGetCodeData(codeType);
			String ajaxMainGetShuJuStr = "[";
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					if(i != list.size()-1){
						ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+list.get(i).get("ID")+"\""+",\"text\":"+"\""+list.get(i).get("TEXT")+"\""+",\"pid\":\""+list.get(i).get("PID")+"\"},";
					}else{
						ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+list.get(i).get("ID")+"\""+",\"text\":"+"\""+list.get(i).get("TEXT")+"\""+",\"pid\":\""+list.get(i).get("PID")+"\"}";
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
	 * 获取部门数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/ajaxGetDepartData.action" })
	@ResponseBody
	public void ajaxGetDepartData(HttpServletRequest request, HttpServletResponse response){
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			String unitLevel=request.getParameter("unitLevel");
			String topOrgid = request.getParameter("topOrgid");
			List<HashMap<String, String>> list = caseAttentionChoiceService.ajaxGetDepartData(unitLevel,topOrgid);
			String ajaxMainGetShuJuStr = "[";
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					if(i != list.size()-1){
						ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+list.get(i).get("ID")+"\""+",\"text\":"+"\""+list.get(i).get("TEXT")+"\""+",\"pid\":\""+list.get(i).get("PID")+"\"},";
					}else{
						ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+list.get(i).get("ID")+"\""+",\"text\":"+"\""+list.get(i).get("TEXT")+"\""+",\"pid\":\""+list.get(i).get("PID")+"\"}";
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
	public List<HashMap> decodeCaseAttentionChoice(List<HashMap> list){
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				//从严从宽
				String[] characteristicArr = ((String)list.get(i).get("CHARACTERISTIC")==null?"":(String)list.get(i).get("CHARACTERISTIC")).split(",");
				if(characteristicArr!=null&&characteristicArr.length>0){
					String characteristic = "";
					for(int j=0;j<characteristicArr.length;j++){
						if(j != characteristicArr.length-1){
							if(characteristicArr[j]!=null&&!"".equals(characteristicArr[j])){
								characteristic += caseAttentionChoiceService.getNameByCodeId("GK1101",characteristicArr[j])+",";
							}
						}else{
							if(characteristicArr[j]!=null&&!"".equals(characteristicArr[j])){
								characteristic += caseAttentionChoiceService.getNameByCodeId("GK1101",characteristicArr[j]);
							}
						}
					}
					list.get(i).put("CHARACTERISTIC", characteristic);
				}
				//罪名
				String[] crimetypeArr = ((String)list.get(i).get("CRIMETYPE")==null?"":(String)list.get(i).get("CRIMETYPE")).split(",");
				if(crimetypeArr!=null&&crimetypeArr.length>0){
					String crimetype = "";
					for(int j=0;j<crimetypeArr.length;j++){
						if(j != crimetypeArr.length-1){
							if(crimetypeArr[j]!=null&&!"".equals(crimetypeArr[j])){
								crimetype += caseAttentionChoiceService.getNameByCodeId("GB075",crimetypeArr[j])+",";
							}
						}else{
							if(crimetypeArr[j]!=null&&!"".equals(crimetypeArr[j])){
								crimetype += caseAttentionChoiceService.getNameByCodeId("GB075",crimetypeArr[j]);
							}
						}
					}
					list.get(i).put("CRIMETYPE", crimetype);
				}
				//所在监狱
				String[] inprisonArr = ((String)list.get(i).get("INPRISON")==null?"":(String)list.get(i).get("INPRISON")).split(",");
				if(inprisonArr!=null&&inprisonArr.length>0){
					String inprison = "";
					for(int j=0;j<inprisonArr.length;j++){
						if(j != inprisonArr.length-1){
							if(inprisonArr[j]!=null&&!"".equals(inprisonArr[j])){
								inprison += caseAttentionChoiceService.getNameByOrgId(inprisonArr[j])+",";
							}
						}else{
							if(inprisonArr[j]!=null&&!"".equals(inprisonArr[j])){
								inprison += caseAttentionChoiceService.getNameByOrgId(inprisonArr[j]);
							}
						}
					}
					list.get(i).put("INPRISON", inprison);
				}
				//职务级别
				String[] dutylevelArr = ((String)list.get(i).get("DUTYLEVEL")==null?"":(String)list.get(i).get("DUTYLEVEL")).split(",");
				if(dutylevelArr!=null&&dutylevelArr.length>0){
					String dutylevel = "";
					for(int j=0;j<dutylevelArr.length;j++){
						if(j != dutylevelArr.length-1){
							if(dutylevelArr[j]!=null&&!"".equals(dutylevelArr[j])){
								dutylevel += caseAttentionChoiceService.getNameByCodeId("GB018",dutylevelArr[j])+",";
							}
						}else{
							if(dutylevelArr[j]!=null&&!"".equals(dutylevelArr[j])){
								dutylevel += caseAttentionChoiceService.getNameByCodeId("GB018",dutylevelArr[j]);
							}
						}
					}
					list.get(i).put("DUTYLEVEL", dutylevel);
				}
				//办案法院
				String[] courtStr = ((String)list.get(i).get("COURT")==null?"":(String)list.get(i).get("COURT")).split(",");
				if(courtStr!=null&&courtStr.length>0){
					String court = "";
					for(int j=0;j<courtStr.length;j++){
						if(j != courtStr.length-1){
							if(courtStr[j]!=null&&!"".equals(courtStr[j])){
								court += caseAttentionChoiceService.getNameByCodeId("GK1102",courtStr[j])+",";
							}
						}else{
							if(courtStr[j]!=null&&!"".equals(courtStr[j])){
								court += caseAttentionChoiceService.getNameByCodeId("GK1102",courtStr[j]);
							}
						}
					}
					list.get(i).put("COURT", court);
				}
				//原判刑期
				String[] originalsentenceStr = ((String)list.get(i).get("ORIGINALSENTENCE")==null?"":(String)list.get(i).get("ORIGINALSENTENCE")).split(",");
				if(originalsentenceStr!=null&&originalsentenceStr.length>0){
					String originalsentence = "";
					for(int j=0;j<originalsentenceStr.length;j++){
						if(j != originalsentenceStr.length-1){
							if(originalsentenceStr[j]!=null&&!"".equals(originalsentenceStr[j])){
								originalsentence += caseAttentionChoiceService.getNameBySenId(originalsentenceStr[j])+",";
							}
						}else{
							if(originalsentenceStr[j]!=null&&!"".equals(originalsentenceStr[j])){
								originalsentence += caseAttentionChoiceService.getNameBySenId(originalsentenceStr[j]);
							}
						}
					}
					list.get(i).put("ORIGINALSENTENCE", originalsentence);
				}
				
			}
			return list;
		}
		return null;
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
}
