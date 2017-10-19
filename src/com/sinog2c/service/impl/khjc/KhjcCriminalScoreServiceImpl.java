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
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.khjc.KhjccriminalmonthscoresdMapper;
import com.sinog2c.model.khjc.KhjcCriminalScoreSd;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.khjc.KhjcTbflowDeliver;
import com.sinog2c.model.khjc.KhjcTbflowDeliverKey;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.PublicCrimInfoService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("khjcCriminalService")
public class KhjcCriminalScoreServiceImpl extends ControllerBase implements KhjcCriminalScoreService{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PublicCrimInfoService publicCrimInfoService;
	@Resource
	private KhjcPublicService khjcPublicService;
	@Autowired
	private KhjcCriminalScoreSdMapper khjcCriminalScoreSdMapper;
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	@Autowired
	private KhjcTbflowDeliverMapper khjcTbflowDeliverMapper;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	@Override
	public void getInfo(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String templetid = request.getParameter("templetid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");//罪犯编号
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(templetid, deptid);
		Map<String,Object> map = new HashMap<String,Object>();
		//有罪犯编号就取罪犯相关的所有信息
		if(!"".equals(crimid)){
			map = publicCrimInfoService.getCriminalBasicCrimeInfo(map, crimid);
			map = publicCrimInfoService.getCriminalBasicInfo(map, crimid);
		}
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("templetid", templetid);
		//request.setAttribute("name",map.get("criname"));
		//request.setAttribute("name",map.get("orgid1"));
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
	}

	/**
	 * 保存罪犯计分信息
	 * yanqutai
	 */
	public String saveCriminalScoreInfo(HttpServletRequest request) {
//		//operateType,tempid,flowid
//		String operateType = request.getParameter("operateType");
//		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");//表单数据信息
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String insertorupdate = "update";
//		SystemUser user = getLoginUser(request);//当前登陆用户对象
//		String userid = "";
//		if (noteinfo != null) {
//			try {
//					String type = "";
//					Map<String,String> map = this.getNoteinfoMap(noteinfo);
//					//保存附件表信息，并返回附件表的主键
//				    Map docmap = khjcPublicService.saveOrUpdateKhjcFlowBaseDoc(operateType,"criminalid", map, request);
//				    //如果附件表保存成功，才保存主表的信息
//				    if("success".equals(docmap.get("state"))){
//				    	//用附件表的主键ID作为数据表的主键，方便后面删除的操作
//				    	KhjcCriminalScoreSd pojo = null;
//				    	if(docmap.get("ID")!=null){
//				    		pojo = khjcCriminalScoreSdMapper.selectByPrimaryKey(docmap.get("ID").toString());
//				    		if(null == pojo){
//				    			pojo = new KhjcCriminalScoreSd();//主表对象
//				    			insertorupdate = "insert";
//				    		}
//				    	}
//				    	if(map.get("templetid") != null){
//							//根据templetid判断奖惩类型（1、专项奖分 2、专项扣分 3、注销分）
//							if(map.get("templetid").indexOf("KHJC_ZFZXJF_") > -1){
//								type = "1";
//							}else if(map.get("templetid").indexOf("") > -1){
//								type = "2";
//							}else if(map.get("templetid").indexOf("") > -1){
//								type = "3";
//							}else{
//								type = "0";
//							}
//						}
//						//保存主表
//						pojo.setJiangchengleixing(type);//奖惩类型（1、专项奖分 2、专项扣分 3、注销分）
//						pojo.setJiangchengleibie(map.get("jianglileixing"));//奖惩类别
//						pojo.setFenshu(map.get("fenshu"));//分数
//						if(user!=null){
//							userid = user.getUserid();
//							pojo.setUpdatemender(userid);//更新人
//							pojo.setJianyu(user.getDepartid());//所在监狱
//						}
//						
//						pojo.setState("1");//状态（1、有效  2、失效）
//						pojo.setName(map.get("crimname"));//罪犯姓名
//						pojo.setDelflag("0");//删除状态（0、有效  1、删除）
//						pojo.setUpdatetime(new Date());//更新时间
//						if("insert".equals(insertorupdate)){
//							pojo.setId(docmap.get("ID").toString());//主键
//							pojo.setCreatemender(userid);//创建人
//							pojo.setCreatetime(new Date());//创建时间
//							pojo.setCrimid(map.get("crimid"));
//							TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(map.get("crimid"));
//							if(null != crime){
//								pojo.setFenjianqu(crime.getOrgid1());//分监区
//								pojo.setJianqu(crime.getOrgid2());//监区
//							}
//							khjcCriminalScoreSdMapper.insert(pojo);
//						}else{
//							pojo.setId(map.get("id"));
//							khjcCriminalScoreSdMapper.updateByPrimaryKeySelective(pojo);
//						}
//				    }
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return null;
	}

	/**
	 * 更新罪犯计分信息
	 * yanqutai
	 */
	public String updateCriminalScoreInfo(HttpServletRequest request) {
		return null;
	}
	

}
