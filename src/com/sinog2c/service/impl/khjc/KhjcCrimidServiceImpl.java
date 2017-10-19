package com.sinog2c.service.impl.khjc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcCrimidService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("khjcCrimidService")
public class KhjcCrimidServiceImpl extends ControllerBase implements KhjcCrimidService{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	@Override
	public void getCrimidInfo(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String menuid=request.getParameter("menuid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		Map<String,Object> map = new HashMap<String,Object>();
		map = this.getCriminalBasicCrimeInfo(map, crimid);//判裁信息
		map = this.getCriminalBasicInfo(map, crimid);//基本信息
		
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid",menuid);
		request.setAttribute("name",map.get("criname"));
		//request.setAttribute("name",map.get("orgid1"));
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
	}

	/**
	 * 获取罪犯基本信息
	 * @param map
	 * yanqutai
	 * @return
	 */
	public Map getCriminalBasicInfo(Map map,String criminalid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//yyyy年MM月dd日这种格式在表单文本框为日期时显示不出来
		TbprisonerBaseinfo basicinfo = prisonerService.getBasicInfoByCrimid(criminalid);
		if(null != basicinfo){
			map = this.getOrgInfo(map,"crimdep",basicinfo.getDepartid());//罪犯所在监区
			map.put("crimid", basicinfo.getCrimid());//编号
			//罪犯所在监狱
			map.put("crimname",basicinfo.getName());//姓名
			//罪犯年龄
			map.put("crimorigin",basicinfo.getOrigin());//罪犯籍贯
			map.put("crimnaction",basicinfo.getNation());//罪犯民族
			map.put("crimsex",basicinfo.getGender());//罪犯性别
			map.put("crimeducation",basicinfo.getEducation());//文化程度
			map.put("crimbirthday",basicinfo.getBirthday() == null ?"":sdf.format(basicinfo.getBirthday()));//出生日期
			map.put("nowdate", sdf.format(new Date()));//当前日期
		}
		return map;
	}
	/**
	 * 获取罪犯判裁信息
	 * @param map
	 * yanqutai
	 * @return
	 */
	public Map getCriminalBasicCrimeInfo(Map map,String criminalid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String xingqiqiri = "";//刑期起日
		String xingqizhiri = "";//刑期止日
		String xingqiqizhi="";//刑期起止，自xxxx年xx月xx日至xxxx年xx月xx日
		TbprisonerBaseCrime crimeinfo = prisonerService.getCrimeByCrimid(criminalid);//判裁信息
		if(null != crimeinfo){
			map.put("crimcauseaction",crimeinfo.getCauseaction()+"罪");//罪犯罪名
			String yuanpannian = crimeinfo.getPunishmentyear()>0?crimeinfo.getPunishmentyear()+"年":"";
			String yuanpanyue = crimeinfo.getPunishmentmonth()>0?crimeinfo.getPunishmentmonth()+"个月":"";
			String yuanpanri = crimeinfo.getPunishmentday()>0?crimeinfo.getPunishmentday()+"天":"";
			map.put("yuanpanxingzhong",crimeinfo.getPunishmenttype());//原判刑种
			map.put("yuanpanxingqi1", yuanpannian+yuanpanyue+yuanpanri);//原判刑期1,格式：xx年xx个月xx天
			map.put("yuanpanxingqi2", crimeinfo.getPunishmenttype()+yuanpannian+yuanpanyue+yuanpanri);//原判刑期1,格式：有期徒刑xx年xx个月xx天
			
			xingqiqiri = sdf.format(crimeinfo.getSentencestime());
			xingqizhiri = sdf.format(crimeinfo.getSentenceetime());
			map.put("xingqiqizhi", "自"+xingqiqiri+"起至"+xingqizhiri+"止");//刑期起止
		}
		return map;
	}
	
	/**
	 * 获取部门信息
	 * @param map
	 * yanqutai
	 * @return
	 */
	public Map getOrgInfo(Map map,String strName,String depid){
		SystemOrganization userorg = systemOrganizationService.getByOrganizationId(depid);
		if(null != userorg){
			map.put(strName,userorg.getName());
		}
		return map;
	}
}
