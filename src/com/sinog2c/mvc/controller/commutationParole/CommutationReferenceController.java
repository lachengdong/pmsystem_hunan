package com.sinog2c.mvc.controller.commutationParole;

import java.util.ArrayList;
import java.util.Date;
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
import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.model.commutationParole.TbxfPunishmentRang;
import com.sinog2c.model.commutationParole.TbxfWideandthinscheme;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.CommutationReferenceService;
import com.sinog2c.service.api.commutationParole.PunishmentRangService;
import com.sinog2c.service.api.commutationParole.WideAndThinschemeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.StringNumberUtil;

import flexjson.JSONDeserializer;
/**
 * 减刑参照
 *
 */
@Controller
public class CommutationReferenceController extends ControllerBase {
	
	@Resource
	private CommutationReferenceService commutationReferenceService;
	
	@Resource
	private PunishmentRangService punishmentRangService;
	
	@Resource
	private SystemLogService logService;
	
	@Resource
	private WideAndThinschemeService wideAndThinschemeService;
	
	/**
	 * 跳转列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toCommutationReferenceListPage")
	public ModelAndView toCommutationReferenceListPage(HttpServletRequest request){
		String punid = request.getParameter("punid");
		TbxfPunishmentRang tbxfPunishmentRang =  punishmentRangService.getPunishmentRangById(Integer.valueOf(punid));
		HashMap map = punishmentRangService.getPrisonersentenceById(tbxfPunishmentRang.getSenid());
		map.put("punid", punid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/commutationReferenceListPage.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}
	
	/**
	 * 获取列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetCommutationReferenceList")
	@ResponseBody
	public Object ajaxGetCommutationReferenceList(HttpServletRequest request){
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String key = request.getParameter("key");
		String punid = request.getParameter("punid");
		int total = commutationReferenceService.getCount(punid,key);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = "t.REFID";
		String sortOrder = "asc";
		List<HashMap> list = commutationReferenceService.getCommutationReferenceList(punid, key, sortField, sortOrder, start, end);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "/toCommutationReferenceAddPage")
	public ModelAndView toCommutationReferenceAddPage(HttpServletRequest request) {
		String punid = request.getParameter("punid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("punid",punid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/commutationReferenceAddPage.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}
	
	/**
	 * 保存数据
	 */
	@RequestMapping(value = { "/saveCommutationReference" })
	@ResponseBody
	public String saveCommutationReference(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String operatetype = request.getParameter("operatetype")==null?"":request.getParameter("operatetype");
		int punid = Integer.valueOf(request.getParameter("punid"));
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				String name = (String)row.get("name");
				int isinterval = Integer.valueOf((String)row.get("isinterval"));
				String remark = (String)row.get("remark");
				int lowestnum = -1;
				int highestnum = -1;
				int suggestnum = -1;
				int reflevel = -1;
				String boquannum = "";
				if(GkzxCommon.ZERO.equals(remark)) {
					lowestnum = Integer.valueOf((String)row.get("lowestnum"));
					highestnum = Integer.valueOf((String)row.get("highestnum"));
					suggestnum = Integer.valueOf((String)row.get("suggestnum"));
					reflevel = Integer.valueOf((String)row.get("reflevel"));
					boquannum = (String)row.get("boquannum");
				}
				TbxfCommutationReference tbxfCommutationReference = new TbxfCommutationReference();
				tbxfCommutationReference.setName(name);
				tbxfCommutationReference.setLowestnum((short)lowestnum);
				tbxfCommutationReference.setHighestnum((short)highestnum);
				tbxfCommutationReference.setSuggestnum((short)suggestnum);
				tbxfCommutationReference.setIsinterval((short)isinterval);
				tbxfCommutationReference.setRemark(remark);
				tbxfCommutationReference.setOpid(user.getUserid());
				tbxfCommutationReference.setReflevel(reflevel);
				tbxfCommutationReference.setBoquannum(boquannum);
				if("new".equals(operatetype)){
					tbxfCommutationReference.setPunid(punid);
					try {
						commutationReferenceService.insert(tbxfCommutationReference);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖惩明细添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加奖惩明细参照");
					log.setOrgid(user.getUserid());
					log.setRemark("添加奖惩明细");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("edit".equals(operatetype)){
					int refid = Integer.valueOf(request.getParameter("refid"));
					tbxfCommutationReference.setPunid(punid);
					tbxfCommutationReference.setRefid(refid);
					tbxfCommutationReference.setOptime(new Date());
					try {
						commutationReferenceService.updateByPrimaryKey(tbxfCommutationReference);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖惩明细修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改奖惩明细");
					log.setOrgid(user.getUserid());
					log.setRemark("修改奖惩明细");
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
	 * 修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toCommutationReferenceEditPage")
	public ModelAndView toCommutationReferenceEditPage(HttpServletRequest request) {
		int refid = Integer.parseInt(request.getParameter("refid"));
		TbxfCommutationReference tbxfCommutationReference = commutationReferenceService.selectByPrimaryKey(refid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("refid",tbxfCommutationReference.getRefid());
		map.put("punid",tbxfCommutationReference.getPunid());
		map.put("name",tbxfCommutationReference.getName());
		map.put("isinterval",tbxfCommutationReference.getIsinterval());
		map.put("remark",tbxfCommutationReference.getRemark());
		if(tbxfCommutationReference.getRemark().equals(GkzxCommon.ZERO)) {
			map.put("lowestnum",tbxfCommutationReference.getLowestnum());
			map.put("highestnum",tbxfCommutationReference.getHighestnum());
			map.put("suggestnum",tbxfCommutationReference.getSuggestnum());
			map.put("reflevel",tbxfCommutationReference.getReflevel());
			map.put("boquannum", tbxfCommutationReference.getBoquannum());
		}
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/commutationReferenceAddPage.jsp");
		mav = new ModelAndView(view,"record",map);
		return mav;
	}
	
	@RequestMapping(value = "/deleteCommutationReference")
	@ResponseBody
	public String deleteCommutationReference(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		if(null!=ids&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				try {
					int refid = Integer.valueOf(ids[i]);
					commutationReferenceService.deleteCommutationReference(refid);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "error";
					status = 0;
				}
				SystemLog log = new SystemLog();
				log.setLogtype("奖惩明细删除操作");
				log.setOpaction("删除");
				log.setOpcontent("删除奖惩明细,refid= "+ids[i]);
				log.setOrgid(user.getUserid());
				log.setRemark("删除奖惩明细");
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
	
	@RequestMapping(value = "/toWideAndThinschemeListPage")
	public ModelAndView toWideAndThinschemeListPage(HttpServletRequest request){
		String punid = request.getParameter("punid");
		TbxfPunishmentRang tbxfPunishmentRang =  punishmentRangService.getPunishmentRangById(Integer.valueOf(punid));
		HashMap map = punishmentRangService.getPrisonersentenceById(tbxfPunishmentRang.getSenid());
		map.put("punid", punid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/wideAndThinschemeListPage.jsp");
		mav = new ModelAndView(view, "record", map);
		return mav;
	}

	@RequestMapping(value = "/ajaxGetWideAndThinschemeList")
	@ResponseBody
	public Object ajaxGetWideAndThinschemeList(HttpServletRequest request){
		Map map = new HashMap();
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String punid = request.getParameter("punid");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = "t.criminaltype";
		String sortOrder = "asc";
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("punid", punid);
		map.put("start", start);
		map.put("end", end);
		List<HashMap> list = commutationReferenceService.getWideAndThinschemeList(map);
		int total = list.size();
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	@RequestMapping(value = "/toWideAndThinschemeAddPage")
	public ModelAndView toWideAndThinschemeAddPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/wideAndThinschemeEditPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	@RequestMapping(value = { "/saveWideAndThinscheme" })
	@ResponseBody
	public String saveWideAndThinscheme(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String operatetype = request.getParameter("operatetype")==null?"":request.getParameter("operatetype");
		int punid = Integer.valueOf((String)request.getParameter("punid"));
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				//获取页面数据,空值默认为-1
				HashMap row = (HashMap)rows.get(i);
				int schemetype = changeStringToInt((String)row.get("schemetype"));
				int criminaltype = changeStringToInt((String)row.get("criminaltype"));
				int lssexecutionsentences = changeStringToInt((String)row.get("lssexecutionsentences"));
				int rangeend = changeStringToInt((String)row.get("rangeend"));
				int rangestart = changeStringToInt((String)row.get("rangestart"));
				int lssstartperiod = changeStringToInt((String)row.get("lssstartperiod"));
				int lssintervalperiod = changeStringToInt((String)row.get("lssintervalperiod"));
				int int1 = changeStringToInt((String)row.get("int1"));
				String remark = StringNumberUtil.returnString2(row.get("remark"));

				//构造model
				TbxfWideandthinscheme record = new TbxfWideandthinscheme();
				record.setSchemetype(schemetype);
				record.setPunid(punid);
				record.setCriminaltype(criminaltype);
				record.setLssexecutionsentences(lssexecutionsentences);
				record.setRangeend(rangeend);
				record.setRangestart(rangestart);
				record.setLssstartperiod(lssstartperiod);
				record.setLssintervalperiod(lssintervalperiod);
				record.setInt1(int1);
				record.setRemark(remark);
				
				//保存数据
				if("new".equals(operatetype)){
					try {
						wideAndThinschemeService.insertWideAndThinscheme(record);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("从宽从严添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加从宽从严");
					log.setOrgid(user.getUserid());
					log.setRemark("添加从宽从严");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("edit".equals(operatetype)){
					try {
						int lssid = Integer.valueOf((String)request.getParameter("lssid"));
						record.setLssid(lssid);
						wideAndThinschemeService.updateWideAndThinscheme(record);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("从宽从严修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改从宽从严,punid="+punid);
					log.setOrgid(user.getUserid());
					log.setRemark("修改从宽从严");
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

	@RequestMapping(value = "/toWideAndThinschemeEditPage")
	public ModelAndView toWideAndThinschemeEditPage(HttpServletRequest request) {
		int lssid = Integer.parseInt(request.getParameter("lssid"));
		TbxfWideandthinscheme record = wideAndThinschemeService.getWideAndThinschemeBylssid(lssid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schemetype",record.getSchemetype());
		map.put("punid",record.getPunid());
		map.put("criminaltype",record.getCriminaltype());
		map.put("lssintervalperiod",record.getLssintervalperiod()==0?"":record.getLssintervalperiod());
		map.put("lssstartperiod",record.getLssstartperiod()==0?"":record.getLssstartperiod());
		map.put("rangestart",record.getRangestart()==0?"":record.getRangestart());
		map.put("rangeend",record.getRangeend()==0?"":record.getRangeend());
		map.put("lssexecutionsentences",record.getLssexecutionsentences()==0?"":record.getLssexecutionsentences());
		map.put("int1",record.getInt1()==0?"":record.getInt1());
		map.put("remark",record.getRemark());
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/wideAndThinschemeEditPage.jsp");
		mav = new ModelAndView(view,"record",map);
		return mav;
	}

	@RequestMapping(value = "/deleteWideAndThinscheme")
	@ResponseBody
	public String deleteWideAndThinscheme(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		int lssid = Integer.valueOf(request.getParameter("lssid"));
		try {
			wideAndThinschemeService.delWideAndThinschemeBylssid(lssid);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = "error";
			status = 0;
		}
		SystemLog log = new SystemLog();
		log.setLogtype("从宽从严删除操作");
		log.setOpaction("删除");
		log.setOpcontent("删除从宽从严,lssid= "+lssid);
		log.setOrgid(user.getUserid());
		log.setRemark("删除从宽从严");
		log.setStatus(status);
		try {
			logService.add(log,user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int changeStringToInt(String str) {
		int returnValue = 0;
		if(null==str || "".equals(str)){
			returnValue = 0;
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
}
