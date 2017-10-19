package com.sinog2c.service.impl.khjc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.khjc.KhjcCodeMapper;
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.khjc.KhjccriminalmonthscoresdMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.khjc.KhjcCode;
import com.sinog2c.model.khjc.KhjcCriminalScoreSd;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.khjc.KhjcTbflowDeliver;
import com.sinog2c.model.khjc.KhjcTbflowDeliverKey;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.PublicCrimInfoService;
import com.sinog2c.service.api.khjc.PublicOrgInfoService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("publicCrimInfoService")
public class PublicCrimInfoServiceImpl extends ControllerBase implements PublicCrimInfoService{
	@Resource
	private PublicOrgInfoService publicOrgInfoService;
	@Resource
	private PrisonerService prisonerService;
	
	/**
	 * 获取罪犯基本信息
	 * @param map
	 * yanqutai
	 */
	public Map getCriminalBasicInfo(Map map,String criminalid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		TbprisonerBaseinfo basicinfo = prisonerService.getBasicInfoByCrimid(criminalid);
		if(null != basicinfo){
			String depname = publicOrgInfoService.getOrgName(basicinfo.getDepartid());//罪犯所在监区
			map.put("crimid", basicinfo.getCrimid());//编号
			//罪犯所在监狱
			map.put("crimname",basicinfo.getName());//姓名
			map.put("crimage","1");//罪犯年龄
			map.put("crimorigin",basicinfo.getOrigin());//罪犯籍贯
			map.put("crimnaction",basicinfo.getNation());//罪犯民族
			map.put("crimsex",basicinfo.getGender());//罪犯性别
			map.put("crimeducation",basicinfo.getEducation());//文化程度
			map.put("crimdep", depname);//罪犯所在部门
			map.put("crimbirthday",basicinfo.getBirthday() == null ?"":sdf.format(basicinfo.getBirthday()));//出生日期
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
			String yuanpannian = crimeinfo.getPunishmentyear() != null?crimeinfo.getPunishmentyear()+"年":"";
			String yuanpanyue = crimeinfo.getPunishmentmonth() != null?crimeinfo.getPunishmentmonth()+"个月":"";
			String yuanpanri = crimeinfo.getPunishmentday() != null?crimeinfo.getPunishmentday()+"天":"";
			map.put("yuanpanxingzhong",crimeinfo.getPunishmenttype());//原判刑种
			map.put("yuanpanxingqi1", yuanpannian+yuanpanyue+yuanpanri);//原判刑期1,格式：xx年xx个月xx天
			map.put("yuanpanxingqi2", crimeinfo.getPunishmenttype()+yuanpannian+yuanpanyue+yuanpanri);//原判刑期1,格式：有期徒刑xx年xx个月xx天
			if(null != crimeinfo.getSentencestime()){
				xingqiqiri = sdf.format(crimeinfo.getSentencestime());
			}
			if(null != crimeinfo.getSentenceetime()){
				xingqizhiri = sdf.format(crimeinfo.getSentenceetime());
			}
			map.put("yuanpanxingqiqizhi", "自"+xingqiqiri+"起至"+xingqizhiri+"止");//刑期起止
		}
		return map;
	}
}
