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
import com.sinog2c.dao.api.khjc.BgzlXfzxgzjlMapper;
import com.sinog2c.dao.api.khjc.BgzlXfzxsqjlMapper;
import com.sinog2c.dao.api.khjc.KhjcCodeMapper;
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.khjc.KhjccriminalmonthscoresdMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.khjc.BgzlXfzxgzjl;
import com.sinog2c.model.khjc.BgzlXfzxsqjl;
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
import com.sinog2c.service.api.khjc.PublicOtherService;
import com.sinog2c.service.api.khjc.PublicToolMethodService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("publicOtherService")
public class PublicOtherServiceImpl extends ControllerBase implements PublicOtherService{
	@Autowired
	private BgzlXfzxgzjlMapper bgzlXfzxgzjlMapper;
	@Autowired
	private BgzlXfzxsqjlMapper bgzlXfzxsqjlMapper;
	@Resource
	private PublicToolMethodService publicToolMethodServic;
	
	
	//办公助理-刑罚执行工作记录(删除)
	public String deleteZfzxgzjlByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//办公助理-刑罚执行工作记录(保存)
	public String saveZfzxgzjl(Map map, HttpServletRequest request) {
		
		BgzlXfzxgzjl pojo = new BgzlXfzxgzjl();
		pojo.setId(map.get("id")==null?"":map.get("id").toString());//主键
		pojo.setShijian(map.get("shijian")==null?"":map.get("shijian").toString());//时间
		pojo.setBumen(map.get("bumen")==null?"":map.get("bumen").toString());//部门
		pojo.setYewu(map.get("yewu")==null?"":map.get("yewu").toString());//业务
		pojo.setBanliqingkuang(map.get("banliqingkuang")==null?"":map.get("banliqingkuang").toString());//办理情况
		pojo.setCunzaiwenti(map.get("shijian")==null?"":map.get("shijian").toString());//存在问题
		pojo.setBeizhu(map.get("beizhu")==null?"":map.get("beizhu").toString());//备注
		
		bgzlXfzxgzjlMapper.insert(pojo);
		return null;
	}
	//办公助理-刑罚执行工作记录(更新)
	public String updateZfzxgzjl(Map map, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//办公助理-刑罚执行工作记录(保存)
	public String saveZfzxsqjl(Map map, HttpServletRequest request) {
		BgzlXfzxsqjl pojo = new BgzlXfzxsqjl();
		pojo.setId(map.get("id")==null?"":map.get("id").toString());//主键
		pojo.setShijian(map.get("shijian")==null?"":map.get("shijian").toString());//时间
		pojo.setShouquanren(map.get("shouquanren")==null?"":map.get("shouquanren").toString());//授权人
		pojo.setBeishouquanren(map.get("beishouquanren")==null?"":map.get("beishouquanren").toString());//授权人
		pojo.setYewu(map.get("yewu")==null?"":map.get("yewu").toString());//业务
		pojo.setBanliqingkuang(map.get("banliqingkuang")==null?"":map.get("banliqingkuang").toString());//办理情况
		pojo.setCunzaiwenti(map.get("shijian")==null?"":map.get("shijian").toString());//存在问题
		pojo.setBeizhu(map.get("beizhu")==null?"":map.get("beizhu").toString());//备注
		bgzlXfzxsqjlMapper.insert(pojo);
		return null;
	}
	
	
}
