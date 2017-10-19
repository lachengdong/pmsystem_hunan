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
import com.sinog2c.dao.api.system.SystemOrganizationMapper;
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

@Service("PublicOrgInfoService")
public class PublicOrgInfoServiceImpl extends ControllerBase implements PublicOrgInfoService{
	@Resource
	private SystemModelService systemModelService;
	@Autowired
	private KhjcCriminalScoreSdMapper khjcCriminalScoreSdMapper;
	@Autowired
	private SystemOrganizationMapper systemOrganizationMapper;
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	@Autowired
	private KhjcTbflowDeliverMapper khjcTbflowDeliverMapper;
	@Autowired
	private KhjcCodeMapper khjcCodeMapper;
	@Autowired
	private SignatureSchemeMapper signatureSchemeMapper;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Override
	
	/**
	 * 获取部门名称
	 * @param map
	 * yanqutai
	 * @return
	 */
	public String getOrgName(String depid){
		String returnValue = "";
		SystemOrganization userorg = systemOrganizationService.getByOrganizationId(depid);
		if(null != userorg){
			returnValue = userorg.getName();
		}
		return returnValue;
	}
	
	/**
	 * 得到查询部门拼接串
	 */
	public String getOrgStr(String type,HttpServletRequest request){
		String departstr = "";
		SystemUser user = getLoginUser(request);
		
		if("userdep".equals(type)){
			//用户当前所在监区
		}else if("basicdep".equals(type)){
			//用户当前所在监狱
			departstr = "'"+user.getDepartid()+"'";
			
		}else if("userprovince".equals(type)){
			//用户当前所在省份
		}
		return departstr;
	}

}
