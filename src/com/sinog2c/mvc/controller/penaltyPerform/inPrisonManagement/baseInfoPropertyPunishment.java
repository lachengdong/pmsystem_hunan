package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerAccompliceKey;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.prisoner.ZpublicDaMtxx;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.swetake.util.Qrcode;


/*
 * 基本信息财产刑、三类犯管理、调狱信息、监舍、床位号补录
 */
@Controller
@RequestMapping("/property")
@SuppressWarnings("all")
public class baseInfoPropertyPunishment extends ControllerBase{
	
	@Autowired
	private SystemModelService systemModelService;
	
	@Resource
	protected FlowBaseService flowBaseService;
	
	@Resource
	public SystemLogService logService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemCodeService systemCodeService;
	@Resource
	private SystemOrganizationService systemorganizationservice;
	
	@Resource
	private TbdataSentchageService sentchageService;
	
	private String content = "http://102187.wap.wei800.com/PrisonService/Prisoner/SearchResult.aspx?n=";
	
	private static int DEFAULT_WIDTH;
	private static int UNIT_WIDTH = 10;
	
	private String _crid = "";
	
	String[] xingqicode = {"GK056","GK057","GK058"};//减刑幅度年月日code类型拼接成数组
	String[] xingqinum = {"punishmentyear","punishmentmonth","punishmentday"};//减刑幅度年月日code类型拼接成数组
	String[] CheckBoxIsguanfan = {"Isminor","Isrecidivism","Recidivist","Isremission","Focus","Mediaattention","Specialcontrol","Falungong","Qitaxiejiao"};//惯犯对应字段
	String[] CheckBoxsishe = {"Drugs","Gun","Underworld","Wickedness"};//四涉对应字段
    String[] CheckBoxsishi = {"Takedrugs","Escapes","Suicide","Assaultepolice"};//四史对应字段
	String[][] CheckBoxs = {CheckBoxIsguanfan,CheckBoxsishe,CheckBoxsishi};//所有复选框的节点名称拼成数组
	String[] CheckBox = {"CheckBoxIsguanfan","CheckBoxsishe","CheckBoxsishi"};//所有复选框的节点名称拼成数组
	
	/*
	 * 跳转到财产刑、三类犯管理、调狱信息信息补录列表页
	 */
	@RequestMapping(value = "propertyPunishmentCriminalChoose.page")
	public ModelAndView propertyPunishmentCriminalChoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/propertyPunishmentChoose");
	}
	/*
	 * 获取财产刑、三类犯管理、调狱信息信息补录列表页数据
	 */
	@RequestMapping("/ajaxGetPropertyPunishmentChoose.json")
	@ResponseBody
	public Object ajaxGetPropertyPunishmentChoose(HttpServletRequest request) throws Exception {
		
		SystemUser user = this.getLoginUser(request);
		
		JSONMessage message = JSONMessage.newMessage();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		map.put("orgid", user.getOrgid());
		
    	int count = prisonerService.ajaxGetPropertyPunishmentCount(map);//根据map传参获取总条数
    	List<Map<String,Object>> list = prisonerService.ajaxGetPropertyPunishmentChoose(map);//根据map传参获取罪犯列表
    	
    	message.setTotal(count);
		message.setData(list);
		
		return message;
	}
	
	
	//保存修改信息  
	@RequestMapping(value = { "/saveBaseCrime.json" })
	@ResponseBody
	@Transactional
	public Object saveBaseCrime(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String crimid = request.getParameter("crimid");
		String opaction = request.getParameter("opaction");
		SystemUser user = getLoginUser(request);
		String type = request.getParameter("type");
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)JsonUtil.Decode(json);
		TbprisonerBaseCrime record = new TbprisonerBaseCrime();
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				//获取页面数据,空值默认为-1
				HashMap row = (HashMap)rows.get(i);
				record.setExpropriation(row.get("expropriation")==null?"":row.get("expropriation").toString());
				record.setFulfilcompensation(row.get("fulfilcompensation")==null?"":row.get("fulfilcompensation").toString());
				record.setReturnloot(row.get("returnloot")==null?"":row.get("returnloot").toString());
				record.setStolenmoney(row.get("stolenmoney")==null?"":row.get("stolenmoney").toString());
				record.setCompensation(row.get("compensation")==null?"":row.get("compensation").toString());
				record.setPayment(row.get("payment")==null?"":row.get("payment").toString());
				record.setForfeit(row.get("forfeit")==null?"":row.get("forfeit").toString());//罚金
				record.setForfeitureproperty(row.get("forfeitureproperty")==null?"":row.get("forfeitureproperty").toString());
				record.setSanclassstatus(row.get("sanclassstatus")==null?"":row.get("sanclassstatus").toString());//三类罪犯
				record.setSanremark(row.get("sanremark")==null?"":row.get("sanremark").toString());//三类罪犯说明
				record.setWhereto(row.get("whereto")==null?"":row.get("whereto").toString());
				record.setCasenature(row.get("importantstatus")==null?"":row.get("importantstatus").toString());//重要罪犯
				record.setCaseothertype(row.get("otherstatus")==null?"":row.get("otherstatus").toString());//其他罪犯类型
				if(row.get("org1")!=null){
					record.setOrgid1(row.get("org1")==null?"":row.get("org1").toString());
					record.setOrgname1(systemorganizationservice.getOrginfoByOrgid(row.get("org1").toString()).getName());
				}
				if(row.get("org2")!=null){
					record.setOrgid2(row.get("org2")==null?"":row.get("org2").toString());
					record.setOrgname2(systemorganizationservice.getOrginfoByOrgid(row.get("org2").toString()).getName());
				}
				
				//原工作单位
				String pprovince = row.get("pprovince")==null?"":row.get("pprovince").toString();
				//原职务
				String duty = row.get("duty")==null?"":row.get("duty").toString();
				Map map = new HashMap();
				map.put("pprovince", pprovince);
				map.put("duty", duty);
				map.put("crimid", row.get("crimid"));
				
				record.setCrimid(crimid);
				String punishmenteight = row.get("teight")==null?"":row.get("teight").toString();
				try {
					//处理刑八罪犯
					if ("true".equals(punishmenteight)) {
						record.setPunishmenteight("1");
					}else if("false".equals(punishmenteight)){
						record.setPunishmenteight("0");
					}
					//重要罪犯选择为空的时候 处理
					if ("important".equals(type)) {
						if (row.get("importantstatus")==null || "".equals(row.get("importantstatus"))) {
							record.setCasenature(" ");
						}
					}
					//其他类型的时候处理
					if ("other".equals(type)) {
						if (row.get("otherstatus")==null || "".equals(row.get("otherstatus"))) {
							record.setCaseothertype(" ");
						}
					}
					/**
					 * 由于调用通用sql语句 sql中判断如果值为空或者是为null将不做任何修改，那么该字段将无法滞空
					 */
					if ("three".equals(type)) {
						if (row.get("sanclassstatus") != null && !"".equals(row.get("sanclassstatus"))) {
							if (row.get("sanclassstatus").toString().contains("J")) {//金融犯
								record.setUndermine("1");
							}else{
								record.setUndermine("0");
							}
							if (row.get("sanclassstatus").toString().contains("h")) {//涉黑罪犯
								record.setUnderworld("1");					
							}else{
								record.setUnderworld("0");
							}
							if (row.get("sanclassstatus").toString().contains("z")) {//职务罪犯
								record.setPostcrime("1");
							}else{
								record.setPostcrime("0");
							}
						}else{
							record.setSanclassstatus("0");
							record.setUndermine("0");
							record.setUnderworld("0");
							record.setPostcrime("0");
						}
					}
					
					prisonerService.updateTbprisonerBaseCrime(record);
					prisonerService.updatePprioveAndduty(map);
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					status = 0;
				}
				SystemLog log = new SystemLog();
				log.setLogtype(opaction+"修改操作");
				log.setOpaction("修改");
				log.setOpcontent("修改"+opaction+"信息,crimid="+crimid);
				log.setOrgid(user.getUserid());
				log.setRemark("修改"+opaction);
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
	
	/*
	 * 批量修改罪犯监舍号、床位号
	 */
	@RequestMapping(value = { "/savePrisonBerth.json" })
	@ResponseBody
	public Object savePrisonBerth(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		String prisons = request.getParameter("prisons");
		ArrayList rows = (ArrayList)JsonUtil.Decode(json);
		TbprisonerBaseCrime record = new TbprisonerBaseCrime();
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				//获取页面数据,空值默认为-1
				HashMap row = (HashMap)rows.get(i);
				String prison = (row.get("prison")==null?"":row.get("prison").toString());
				if(!StringNumberUtil.isNullOrEmpty(prisons)){
					prison = prisons;
				}
				record.setPrison(prison);
				record.setBerth((row.get("berth")==null?"":row.get("berth").toString()));
				record.setChargeclass((row.get("chargeclass")==null?"":row.get("chargeclass").toString()));
				record.setCrimid((row.get("crimid")==null?"":row.get("crimid").toString()));
				try {
					prisonerService.updateTbprisonerBaseCrime(record);
				} catch (Exception e) {
					e.printStackTrace();
					result = "error";
					status = 0;
				}
				SystemLog log = new SystemLog();
				log.setLogtype("监舍床位号修改操作");
				log.setOpaction("修改");
				log.setOpcontent("修改监舍床位号信息,crimid="+row.get("crimid"));
				log.setOrgid(user.getUserid());
				log.setRemark("修改监舍床位号");
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
	 *方法描述：新增补录信息页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicInformationbl.page")
	public ModelAndView basicInformationbl(HttpServletRequest request)throws Exception {
		//快速检索传值
		Map jianxinginfo = new HashMap<>();
		String sdid=request.getParameter("sdid");
		request.setAttribute("sdid", sdid);
		String zaiyacombo1=request.getParameter("zaiyacombo1");
		request.setAttribute("zaiyacombo1", zaiyacombo1);
		String key=request.getParameter("key");
		if(null!=key&&!"".equals(key)){
			try {
				key = URLDecoder.decode(key,"UTF-8");
				request.setAttribute("key", key);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String idnumber = request.getParameter("idnumber");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		
		//request.setAttribute("flowdraftid", idnumber);
		JSONArray docconent = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
			
		SystemUser user = getLoginUser(request);
		String username=user.getName();
		request.setAttribute("username", username);
		
		String tempid = "SDXF_BASE_RJDJB"; //罪犯基本信息表单
			
		//基本信息表的数据处理
		map.put("depid",user.getOrganization().getName());//部门
		map.put("criminalid", "");// 罪犯编号
		map.put("danganhao",jianxinginfo.get("FILENO"));// 档案号
		
		map.put("cbiname", jianxinginfo.get("CRIMNAME"));//姓名
		map.put("cbitruename", jianxinginfo.get("USEDNAME"));// 别名
		map.put("cbigender", jianxinginfo.get("CRIMSEX"));// 性别
		
		map.put("cbibirthday",jianxinginfo.get("CHUSHENGRIQI2"));//出生日期
		map.put("cbination", jianxinginfo.get("MINZU"));// 民族
		map.put("caiformermarriage",jianxinginfo.get("MARITALSTATUS"));// 婚姻状况	
		
		map.put("caieducation",jianxinginfo.get("WENHUACHENGDU"));//文化程度
		map.put("caipoliticalappearance",jianxinginfo.get("ZHENGZHIMIANMAO"));// 捕前面貌
		map.put("aiformervocation",jianxinginfo.get("BUQIANZHIYE"));// 捕前职业code
		
		map.put("aiformerduty", jianxinginfo.get("BUQIANZHIWU"));// 捕前职务
		map.put("caiformerlevel", jianxinginfo.get("ZHIWUJIBIE"));// 职务级别
		
		map.put("cbiidentitycard",jianxinginfo.get("SHENFENZHENGHAO"));// 身份证号
		map.put("fazhengjiguan",jianxinginfo.get("FAZHENGJIGUAN"));// 发证机关
		
		map.put("cbinativeplaceaddress",jianxinginfo.get("JIGUAN"));//籍贯
		map.put("cbiresidenceaddress",jianxinginfo.get("HUJISUOZAIDI"));// 户籍所在地						
		map.put("cbiresidenttype", jianxinginfo.get("HUKOULEIXING"));// 户口类型
		
		map.put("cbihomeaddress",jianxinginfo.get("JIATINGZHUZHI"));// 家庭住址
		map.put("cbispeak",jianxinginfo.get("KOUYIN"));// 口音
		
		map.put("criofficiallydepartment",jianxinginfo.get("JIAOFUJIGUAN"));// 交付机关
		map.put("cbispeciality",jianxinginfo.get("TECHANG"));// 特长
		
		map.put("criofficiallyplacetype",jianxinginfo.get("SHOUYALEIXING"));// 收押类型
		map.put("criofficiallyplacedate",jianxinginfo.get("RUJIANRIQI2"));// 入监日期
		map.put("cjicriminalsort",jianxinginfo.get("ZUIFANLEIBIE"));//罪犯类别
		
		map.put("caiflowtype",jianxinginfo.get("LIUCUANLEIBIE"));// 流窜类别
		map.put("cairecordnumber",jianxinginfo.get("QIANKECISHU"));// 前科次数
		map.put("caicommentnumber",jianxinginfo.get("TUANHUORENSHU"));// 团伙人数
		
		map.put("caidetaindate", jianxinginfo.get("JIYARIQI2"));// 羁押日期
		map.put("caiarrestoffice",jianxinginfo.get("DAIBUJIGUAN"));// 逮捕机关
		map.put("caiarrestdate", jianxinginfo.get("DAIBURIQI2"));// 逮捕日期
		
		map.put("cjicourtname", jianxinginfo.get("YUANPANFAYUAN"));// 判决机关
		map.put("cjicourtnumber",jianxinginfo.get("YUANPANZIHAO") );// 判决书号
		map.put("cjijudgedate",jianxinginfo.get("YUANPANPANCAIRIQI2"));// 判决日期
		
		map.put("zhuanyou",jianxinginfo.get("CAUSEACTION"));// 罪名（主案由）
		map.put("qitaanyou",jianxinginfo.get("QITAZUIMING"));// （其他案由）
		map.put("fanzuishijian",jianxinginfo.get("FANZUISHIJIAN2"));// 犯罪时间
		map.put("xingba",jianxinginfo.get("XINGBA"));//是否刑八
		
		map.put("cjiimprisontype",jianxinginfo.get("YUANPANXINGZHONG"));// 刑罚种类
		map.put("zhuxing", jianxinginfo.get("YUANPANXINGQI"));//刑期
		map.put("cjidisfranchiseyear", jianxinginfo.get("YUANPANBOQUAN"));// 剥夺年限
	
		map.put("cjibegindate",jianxinginfo.get("YUANPANXINGQIQIRI2"));// 刑期起日
		map.put("cjienddate", jianxinginfo.get("YUANPANXINGQIZHIRI2"));// 刑期止日
		//刑期折抵
			
		if("/".equals(jianxinginfo.get("YUANFAJIN")) || jianxinginfo.get("YUANFAJIN")==null){
			map.put("cjimulct", "/");// 罚金
		}else{
			map.put("cjimulct",jianxinginfo.get("YUANFAJIN"));// 罚金
		}
		map.put("cjmoneydisgorged", jianxinginfo.get("YUANPANZHUIZANG")==null?"/":jianxinginfo.get("YUANPANZHUIZANG"));// 追缴赃款
		map.put("cjisequestrateproperty",jianxinginfo.get("YUANMOSHOU")==null?"/":jianxinginfo.get("YUANMOSHOU"));// 没收财产
		
		map.put("cjotherjudge",jianxinginfo.get("YUANMINPEI")==null?"/":jianxinginfo.get("YUANMINPEI"));// 民事赔偿
		map.put("cjappeal",jianxinginfo.get("SHANGSUQINGKUANG"));// 上诉情况
		
		map.put("caioldpunish",jianxinginfo.get("QKQK"));// 曾受过处罚
		
		map.put("fayuancaipan",jianxinginfo.get("FAYUANPANCAI")==null?"":jianxinginfo.get("FAYUANPANCAI").toString().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));//法院判裁
		if(null!=jianxinginfo.get("FANZUISHISHI")){
			Clob clob = (Clob)jianxinginfo.get("FANZUISHISHI");
			String fanzuishishi = clob.getSubString(1, (int)clob.length());
			map.put("fanzuishishi",fanzuishishi.replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));// 犯罪事实
		}
		//三类罪犯
		String zhiwufan = jianxinginfo.get("ZHIWUFAN")==null?"":jianxinginfo.get("ZHIWUFAN").toString();
		String jinrong = jianxinginfo.get("JINRONGFAN")==null?"":jianxinginfo.get("JINRONGFAN").toString();
		String shehei = jianxinginfo.get("SHEHEIFAN")==null?"":jianxinginfo.get("SHEHEIFAN").toString();
		String sanleifanleixing = "";
		if("1".equals(zhiwufan)){
			sanleifanleixing+="、职务犯罪";
		}
		if("1".equals(jinrong)){
			sanleifanleixing+="、金融犯罪";
		}
		if("1".equals(shehei)){
			sanleifanleixing+="、黑社会性质组织犯罪 ";
		}
		if(!"".equals(sanleifanleixing)){
			map.put("sanleifanleixing",sanleifanleixing.substring(1));// 三类犯类型
		}
		map.put("sanleicode",jianxinginfo.get("SANLEICODE"));//三类犯code
		map.put("yuangongzuodanwei",jianxinginfo.get("YUANGONGZUODANWEI"));// 原工作单位
		map.put("buqianzhiwu",jianxinginfo.get("BUQIANZHIWU"));// 原职务
		map.put("rendingyiju",jianxinginfo.get("RENDINGYIJU"));// 认定依据
		
		map.put("zhongyaozuifanleixing",jianxinginfo.get("ZHONGYAOZUIFANLEIXING"));// 重要罪犯类型
		map.put("othertype",jianxinginfo.get("QITAZUIFANLEIXING"));// 其他罪犯类型
		
		request.setAttribute("tempid", tempid);
		
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());//获取表单
		if (template != null) docconent.add(template.getContent());
		
		//把大字段当做模板
		if(!StringNumberUtil.isNullOrEmpty(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			if (template != null) docconent.add(template.getContent());
		}
			
		//半身照
		Map<String,Object> mtxxMap = new HashMap<String,Object>();
		mtxxMap.put("mtbm", "11");
		mtxxMap.put("mtlb", "1");
		ZpublicDaMtxx baseMtxx = prisonerService.getHeadPicture(mtxxMap); 
		if(baseMtxx != null) {
			map.put("picrjdjimg", baseMtxx.getNr());
		}
			
		//所有下拉列表显示
		Map<String, Object> selectmap = new HashMap<String, Object>();
		//----性别
		String cbigender = systemCodeService.getcodeValue("GB003");
		selectmap.put("cbigender", cbigender);
		for (int i = 0; i < 6; i++) selectmap.put("sex"+i, cbigender);
		
		selectmap.put("caiformermarriage", systemCodeService.getcodeValue("GB004"));//婚姻状态
		selectmap.put("cbination", systemCodeService.getcodeValue("GB006"));//民族
		selectmap.put("caieducation", systemCodeService.getcodeValue("GB007"));//文化程度
		selectmap.put("xingba", systemCodeService.getcodeValue("GKXBLB"));//并罚情况
		//----政治面貌
		String political = systemCodeService.getcodeValue("GB008");
		selectmap.put("caipoliticalappearance", political);
		for (int i = 0; i < 10; i++) selectmap.put("political"+i, political);
		
		selectmap.put("cbiresidenttype", systemCodeService.getcodeValue("GB010"));//户口性质
		selectmap.put("caiformerlevel", systemCodeService.getcodeValue("GB018"));//
		selectmap.put("cjiimprisontype", systemCodeService.getcodeValue("GB022"));//有期徒刑
		selectmap.put("caiflowtype", systemCodeService.getcodeValue("GB025"));//省内流窜
		selectmap.put("criofficiallyplacetype", systemCodeService.getcodeValue("GB031"));//新收押
		selectmap.put("cjicriminalsort", systemCodeService.getcodeValue("GB036"));//一般刑事犯
		//----婚姻状况
		String relationship = systemCodeService.getcodeValue("GK004");
		for (int i = 0; i < 7; i++) selectmap.put("relationship"+i, relationship);
		
		selectmap.put("aiformervocation", systemCodeService.getcodeValue("GK041"));//职业  
		selectmap.put("aiformerduty", systemCodeService.getcodeValue("GK043"));//
		selectmap.put("cjappeal", systemCodeService.getcodeValue("GK046"));//
		selectmap.put("cjidisfranchiseyear", systemCodeService.getcodeValue("GK059"));//
		//----单位列表
		List<SystemOrganization> orgList = null;
		if("4".equals(user.getOrganization().getUnitlevel())){//监区
			orgList = new ArrayList();
			SystemOrganization code = new SystemOrganization();
			code.setOrgid(user.getOrgid());
			code.setName(user.getOrganization().getName());
			orgList.add(code);
		}else{
			orgList = systemorganizationservice.getByPorgId(user.getDepartid());
		}
		String tempStr = "";
		if (!orgList.isEmpty() && orgList != null) {
			for (SystemOrganization code : orgList) {
				tempStr += ("[[" + code.getOrgid() + "]]" + code.getName() + "||");
			}
			tempStr = tempStr.substring(0, tempStr.length() - 2);
		}
		selectmap.put("depid", tempStr);
		
		
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());

		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		String closetype = request.getParameter("closetype");
		if(StringNumberUtil.notEmpty(closetype)){
			request.setAttribute("closetype", closetype);
		}
		String fathermenuid = request.getParameter("fathermenuid");
		if(StringNumberUtil.notEmpty(fathermenuid)){
			request.setAttribute("fathermenuid", fathermenuid);
		}
		request.setAttribute("tempid", tempid); //罪犯基本信息表单
		request.setAttribute("applyname",jianxinginfo.get("CRIMNAME"));
		request.setAttribute("flowdefid", "doc_zfrjdjsp");
		request.setAttribute("orgid",jianxinginfo.get("ORGID1"));
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/addBasicInformationbl");
	}
	
	/**
	 *方法描述：新增罪犯补录信息保存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCrimInfol.json")
	@ResponseBody
	public Object addCrimInfo(HttpServletRequest request) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		String selectid = request.getParameter("selectid")==null?"":request.getParameter("selectid");
		//罪犯姓名
		String criminame = "";
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		
		SystemUser user = getLoginUser(request);
		Date date = new Date();
		
		//noteinfo = null;
		
		if (noteinfo != null) {
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			SystemOrganization org = new SystemOrganization();
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map)list.get(0);
				if(!StringNumberUtil.isNullOrEmpty(selectid)){
					ArrayList selectlist = (ArrayList) JSON.parseArray(selectid, Object.class);
					Map<String,String> selectmap = (Map)selectlist.get(0);
					map.putAll(selectmap);
				}
				
				map = StringNumberUtil.dealMapForForm(map);
				
				if(crimid==null || "".equals(crimid)) crimid=map.get("criminalid");
				String tianbiaoren = map.get("tianbiaoren");  //经办人
				String shenheren = map.get("shenheren"); //审核人
				//基本信息保存             
				Map<String,Object> hashMap  = new HashMap<String,Object>();
				hashMap.put("crimid", crimid);
				hashMap.put("orgid", user.getDepartid());
				hashMap.put("name",  map.get("cbiname"));
				criminame = map.get("cbiname");
				
				String departname = map.get("depid");
				//-----根据 部门id 和 机构名称 查询 机构信息
				Map orgMap = new HashMap();
				orgMap.put("departname", departname);
				orgMap.put("departid",user.getDepartid());
				org = systemorganizationservice.getDepartidByName(orgMap);
				
				hashMap.put("orgname1", departname);
				hashMap.put("orgname2", departname);
				
				hashMap.put("detainprison", systemorganizationservice.getByOrganizationId(user.getDepartid()).getName());
				
				hashMap.put("usedname", map.get("cbitruename"));
				hashMap.put("idnumber", map.get("cbiidentitycard"));
				hashMap.put("gender", map.get("cbigender"));
				hashMap.put("birthday", DateUtil.StringParseDate(map.get("cbibirthday")));
				hashMap.put("nation",  map.get("cbination"));
				hashMap.put("politicalstatus",  map.get("caipoliticalappearance"));
				hashMap.put("education",  map.get("caieducation"));
				hashMap.put("maritalstatus",  map.get("caiformermarriage"));
				hashMap.put("vocation",  map.get("aiformervocation"));
				hashMap.put("specialist",  map.get("cbispeciality"));
				hashMap.put("grade",  map.get("caiformerlevel"));
				hashMap.put("duty",  map.get("buqianzhiwu"));
				hashMap.put("pprovince",  map.get("yuangongzuodanwei"));
				hashMap.put("detainstatus", "在押");
				
				//---------------------------------------责令退赔
				hashMap.put("returncompenstation", map.get("tuipei"));
				
				//籍贯
				hashMap.put("origin",map.get("cbinativeplaceaddress"));
				hashMap.put("origin_code", map.get("jgdzaddress2"));
				
				//户籍地
				hashMap.put("registeraddressdetail",map.get("cbiresidenceaddress"));
				hashMap.put("registerarea", map.get("hjdzaddress2"));
				hashMap.put("registeraddress", map.get("hjdzaddress3"));
				
                //家庭住址
				hashMap.put("familyaddress",map.get("cbihomeaddress"));
				hashMap.put("addresscode", map.get("dqxjaddress"));//codeid
				hashMap.put("familyjcaddress", map.get("xxdzaddress"));//具体地址
				
				hashMap.put("countryarea",  map.get("cbinativeplaceaddress"));
				hashMap.put("originplaceaddress",  map.get("cbinativenamedetail"));
				hashMap.put("residencetype",  map.get("cbiresidenttype"));
				
				String caioldpunish = map.get("caioldpunish").replaceAll("\"", "“").replaceAll(":", "：").replaceAll("'", "’");
				hashMap.put("rewardpunish", caioldpunish);
				hashMap.put("accent",  map.get("cbispeak"));
				hashMap.put("orgid1", org.getOrgid());
				hashMap.put("orgid2", org.getOrgid());
				hashMap.put("opid",  user.getUserid());
				
				
				TbprisonerBaseinfo baseinfo = new TbprisonerBaseinfo();
				
				baseinfo.setCrimid(crimid);//罪犯编号
				baseinfo.setDepartid(user.getDepartid());//组织机构ID
				baseinfo.setName(map.get("cbiname"));//姓名
				baseinfo.setUsedname(map.get("cbitruename"));//真实姓名
				baseinfo.setIdnumber(map.get("cbiidentitycard"));//身份证号
				baseinfo.setGender(map.get("cbigender"));//性别
				baseinfo.setBirthday(DateUtil.StringParseDate(map.get("cbibirthday")));
				baseinfo.setNation(map.get("cbination"));//民族
				baseinfo.setPoliticalstatus(map.get("caipoliticalappearance"));//捕前政治面貌
				baseinfo.setEducation(map.get("caieducation"));//捕前文化程度
				baseinfo.setMaritalstatus(map.get("caiformermarriage"));//捕前婚姻状况
				baseinfo.setVocation(map.get("aiformervocation"));//捕前职业
				baseinfo.setOrigin(map.get("cbinativeplaceaddress"));//捕前籍贯
				baseinfo.setPprovince(map.get("yuangongzuodanwei"));//捕前工作单位
				baseinfo.setDuty(map.get("buqianzhiwu"));//原职务
				baseinfo.setRewardpunish(caioldpunish);//曾受何种处罚

				baseinfo.setRegisteraddressdetail(map.get("cbiresidenceaddress"));//户籍地址
				baseinfo.setRegisterarea(map.get("hjdzaddress2"));
				baseinfo.setRegisteraddress(map.get("hjdzaddress3"));
				
				baseinfo.setAddressarea(map.get("cbihomeaddress"));//家庭住址
				baseinfo.setAddresscode(map.get("dqxjaddress"));
				baseinfo.setAddressdetail(map.get("xxdzaddress"));
				
				baseinfo.setOpid(user.getUserid());//操作人id
				
				int count = prisonerService.findCountByCrimid(crimid);
				if(count != 0)
					return "{\"msg\":'0'}";
				//----------插入罪犯基本信息--------------
				prisonerService.insertTbprisonerBaseCrime(hashMap);
				
				//----------插入犯人基本信息表-------------
				prisonerService.insertSelective(baseinfo);
				
				//----------插入罪犯刑罚变动信息
				TbdataSentchage sentchage = new TbdataSentchage();
				
				sentchage.setCrimid(crimid);
				sentchage.setCategory("1");//默认原判
				sentchage.setCourtsanction("".equals(map.get("cjijudgedate")) ? DateUtil.dateFormatForAip(new Date())
								: DateUtil.dateFormat(DateUtil.StringParseDate(
												map.get("cjijudgedate"),"yyyy年mm月dd日"), "yyyymmdd"));// 判裁日期
				
				sentchageService.insertSelective(sentchage);
				
				//----------更新刑期变动信息表TBXF_SENTENCEALTERATION表数据
				Map callMap = new HashMap();
				callMap.put("orgid", user.getDepartid());
				callMap.put("crimid", crimid);
				prisonerService.callXFsentencealteration(callMap);
				
				//处罚信息表
    			Map<String,Object> baseCrimeMap  = new HashMap<String,Object>();

				//发证机关
				baseCrimeMap.put("issuingauthority", map.get("fazhengjiguan"));
    			
				baseCrimeMap.put("crimid", crimid);
				
				String fanzuishishi = map.get("fanzuishishi").replaceAll("\"", "“").replaceAll(":", "：").replaceAll("'", "’");
				baseCrimeMap.put("crimeface", fanzuishishi);
				baseCrimeMap.put("crimedate", DateUtil.StringParseDate(map.get("fanzuishijian")));
				baseCrimeMap.put("detaindate",  DateUtil.StringParseDate(map.get("caidetaindate")));
				
				//逮捕机关
				baseCrimeMap.put("arrestauthority", map.get("caiarrestoffice"));//逮捕机关全称
				baseCrimeMap.put("arrestauthorityshort", map.get("jfaddress3"));//逮捕机关具体名称
				baseCrimeMap.put("arrestauthority_code", map.get("jfaddress2"));//逮捕机关所在地codeid
				
				baseCrimeMap.put("arrestdate", DateUtil.StringParseDate(map.get("caiarrestdate")));
				baseCrimeMap.put("pedigreenum", map.get("cairecordnumber"));
				baseCrimeMap.put("jointcrimes", map.get("caicommentnumber"));
				baseCrimeMap.put("inprisondate", DateUtil.StringParseDate(map.get("criofficiallyplacedate")));
				baseCrimeMap.put("appeal", map.get("cjappeal"));
				baseCrimeMap.put("judgmentname", map.get("cjicourtname"));
				baseCrimeMap.put("judgmentshort", map.get("court_title"));
				baseCrimeMap.put("caseno", map.get("caseno3"));
				baseCrimeMap.put("caseyear", map.get("caseno1"));

				if(StringNumberUtil.notEmpty(map.get("caseno2"))){
					String caseno2 = map.get("caseno2");
					String diWord = caseno2.substring(caseno2.length()-1);
					if(diWord != "第"){
						caseno2 += "第";
					}
					baseCrimeMap.put("caseorgshort", caseno2);
				}
				baseCrimeMap.put("sentencestime", DateUtil.StringParseDate(map.get("cjibegindate")));
				baseCrimeMap.put("sentenceetime", DateUtil.StringParseDate(map.get("cjienddate")));
				
				baseCrimeMap.put("executiondate", DateUtil.StringParseDate(map.get("sjxfzxjgzr")));//执行日期/交送刑罚执行机关之日
				
				baseCrimeMap.put("forfeit", map.get("cjimulct"));
				baseCrimeMap.put("forfeitureproperty", map.get("cjisequestrateproperty"));
				baseCrimeMap.put("compensation", map.get("cjotherjudge"));
				baseCrimeMap.put("criminaltype", map.get("cjicriminalsort"));
				baseCrimeMap.put("fleetype", map.get("caiflowtype"));
				baseCrimeMap.put("stolenmoney", map.get("cjmoneydisgorged"));
				baseCrimeMap.put("judgedate", DateUtil.StringParseDate(map.get("cjijudgedate")));
				baseCrimeMap.put("watchhouse", map.get("criofficiallydepartment"));
				baseCrimeMap.put("detaintype", map.get("criofficiallyplacetype"));
				baseCrimeMap.put("fileno", map.get("danganhao"));
				baseCrimeMap.put("othercase", map.get("qitaanyou"));
				baseCrimeMap.put("maincase", map.get("zhuanyou"));
				baseCrimeMap.put("punishmenttype", map.get("cjiimprisontype"));
				//baseCrimeMap.put("causeaction", map.get("zhuanyou")+" "+map.get("qitaanyou"));    
				baseCrimeMap.put("causeaction", map.get("zhuanyou"));
				
				String fayuancaipan = map.get("fayuancaipan").replaceAll("\"", "“").replaceAll(":", "：").replaceAll("'", "’");
				baseCrimeMap.put("firsttrialsum",  fayuancaipan);
				baseCrimeMap.put("opid",  user.getUserid());
				baseCrimeMap.put("optime",  date);
				baseCrimeMap.put("prison", map.get("prison"));
				
				baseCrimeMap.put("punishmentyear", map.get("zhuxing0"));
				baseCrimeMap.put("punishmentmonth", map.get("zhuxing1"));
				baseCrimeMap.put("punishmentday", map.get("zhuxing2"));
				//三类犯
				if (map.get("sanleicode") != null && !"".equals(map.get("sanleicode"))) {
					if (map.get("sanleicode").toString().contains("J")) {//金融犯
						baseCrimeMap.put("undermine","1");
					}else{
						baseCrimeMap.put("undermine","0");
					}
					if (map.get("sanleicode").toString().contains("h")) {//涉黑罪犯
						baseCrimeMap.put("underworld", "1");		
					}else{
						baseCrimeMap.put("underworld", "0");	
					}
					if (map.get("sanleicode").toString().contains("z")) {//职务罪犯
						baseCrimeMap.put("postcrime","1");
					}else{
						baseCrimeMap.put("postcrime","0");
					}
				}else{
					baseCrimeMap.put("undermine","0");
					baseCrimeMap.put("postcrime","0");
					baseCrimeMap.put("underworld", "0");	
				}

				baseCrimeMap.put("sanclassstatus",map.get("sanleicode"));
				baseCrimeMap.put("sanremark", map.get("rendingyiju"));
				baseCrimeMap.put("casenature", map.get("zhongyaozuifanleixing"));
				baseCrimeMap.put("caseothertype", map.get("othertype"));
				
				baseCrimeMap.put("punishmenteight", "适用".equals(map.get("xingba"))?1:0);
				
				List<TbsysCode> boZhenList = systemCodeService.selectValueByCodeType("GK059");//剥政年限类型
				String boZhenStr = map.get("cjidisfranchiseyear");
				if(StringNumberUtil.notEmpty(boZhenStr)){
					for(TbsysCode tc : boZhenList){
						if(tc.getName().equals(boZhenStr)){
							baseCrimeMap.put("losepoweryear", tc.getCodeid());
						}
					}
				}
				
				//刑期保存   
				String zhuxingnum = map.get("zhuxingnum");
				if(!"".equals(zhuxingnum)){
					if("9995、9996、9997".contains(zhuxingnum)){//无期、死缓、死刑
						baseCrimeMap.put(xingqinum[0], zhuxingnum);
					}else{//有期
						for(int i=0;i<xingqicode.length;i++){
							 int codeid = Integer.parseInt(zhuxingnum.substring(i*2, i*2+2));//循环截取输入的值作为年月日的codeid
							 baseCrimeMap.put(xingqinum[i], codeid);
						}
					}
				}
				prisonerService.updateBaseCrimeByCrimid(baseCrimeMap);
				String tempid = request.getParameter("tempid");
				//根据表单Id判断是否保存个人简历；家庭成员、主要社会关系；同案犯
				//根据表单上的数据值判断，只要有一个编辑区域有值就进行新增、修改
				if("SDXF_BASE_RJDJB".equals(tempid)){
					//个人简历
					for(int i = 0;i<9;i++){
						String resumeid = map.get("resumeid"+i)==null?"":map.get("resumeid"+i);
						String begindate = map.get("begindate"+i)==null?"":map.get("begindate"+i);
						String enddate = map.get("enddate"+i)==null?"":map.get("enddate"+i);
						String vocation = map.get("vocation"+i)==null?"":map.get("vocation"+i);
						String orgname = map.get("orgname"+i)==null?"":map.get("orgname"+i);
						if(StringNumberUtil.notEmpty(begindate)||StringNumberUtil.notEmpty(enddate)||
						   StringNumberUtil.notEmpty(vocation)||StringNumberUtil.notEmpty(orgname)){
							TbprisonerResume resume = new TbprisonerResume();
							resume.setCrimid(crimid);
							resume.setBegindate(begindate);
							resume.setEnddate(enddate);
							resume.setVocation(vocation);
							resume.setOrgname(orgname);
							resume.setOpid(user.getUserid());
							resume.setOptime(date);
							if(StringNumberUtil.notEmpty(resumeid)){
								result.put("resumeid"+i, resumeid);
								resume.setResumeid(Integer.parseInt(resumeid));
								prisonerService.updateCrimidResume(resume);
							}else{
								resumeid = prisonerService.getResumeid();
								result.put("resumeid"+i, resumeid);
								resume.setResumeid(Integer.parseInt(resumeid));
								prisonerService.insertCrimidResume(resume);
							}
						} else{
							//删除简历
							if(StringNumberUtil.notEmpty(resumeid)){
								prisonerService.deleteCrimidResume(Integer.parseInt(resumeid));
							}
						}
					}
					
					// 家庭成员、主要社会关系
					for(int i = 0;i<7;i++){
						String srid = map.get("srid"+i)==null?"":map.get("srid"+i);
						String relationship = map.get("relationship"+i)==null?"":map.get("relationship"+i);
						String name = map.get("name"+i)==null?"":map.get("name"+i);
						String birthday = map.get("birthday"+i)==null?"":map.get("birthday"+i);
						String political = map.get("political"+i)==null?"":map.get("political"+i);
						//所在单位 修改为身份证号
						String paperid = map.get("srorgname"+i)==null?"":map.get("srorgname"+i);
						String homeaddress = map.get("homeaddress"+i)==null?"":map.get("homeaddress"+i);
						String phone = map.get("phone"+i)==null?"":map.get("phone"+i);
						if(StringNumberUtil.notEmpty(relationship)||
						   StringNumberUtil.notEmpty(name)||StringNumberUtil.notEmpty(birthday)||
						   StringNumberUtil.notEmpty(political)||StringNumberUtil.notEmpty(paperid)||
						   StringNumberUtil.notEmpty(homeaddress)||StringNumberUtil.notEmpty(phone)){
							TbprisonerSocialRelation social = new TbprisonerSocialRelation();
							social.setCrimid(crimid);
							social.setName(name);
							social.setRelationship(relationship);
							social.setBirthday(birthday);
							social.setPolitical(political);
//							social.setOrgname(srorgname);
							social.setPaperid(paperid);
							social.setHomeaddress(homeaddress);
							social.setPhone(phone);
							social.setOpid(user.getUserid());
							social.setOptime(date);
							//文件临时保存到本地(生成二维码以后再删除)
//							String FilePath = jyconfig.getProperty("qrcode");
							String FilePath = GetAbsolutePath.getProjectPath(request);//项目在服务器上的路径
							FilePath += "download\\qrcode.png";//用一个临时文件可能也有问题（如：多用户某时间点，同时对要生成二维码）
							
							if (StringNumberUtil.notEmpty(paperid) && StringNumberUtil.personidValidation(paperid)) {
								//生成二维码(罪犯编号、身份证号、路径、循环次数)
								generateQrcode(crimid,paperid,FilePath);
								//读取文件转为 二进制码
								byte[] bytes = FileUtil.getBuffer(FilePath);
								//把二进制的字符流通过base64进行转化保存到数据库中
								String qrcode = DatatypeConverter.printBase64Binary(bytes);
								//图片保存到数据库中
								social.setQrcode(qrcode);
								social.setCxcode(_crid);
							}
							
							if(StringNumberUtil.notEmpty(srid)){
								result.put("srid"+i, srid);
								social.setSrid(Integer.parseInt(srid));
								prisonerService.updateTbprisonerSocialRelation(social);
							}else{
								srid = prisonerService.getSrid();
								result.put("srid"+i, srid);
								social.setSrid(Integer.parseInt(srid));
								prisonerService.insertTbprisonerSocialRelation(social);
							}
						}else{
							if(StringNumberUtil.notEmpty(srid)){
								prisonerService.deleteTbprisonerSocialRelation(Integer.parseInt(srid));
							}
						}
					}
					
					//同案犯
					for(int i = 0;i<6;i++){
						String accid = map.get("accid"+i)==null?"":map.get("accid"+i);
						String acname = map.get("acname"+i)==null?"":map.get("acname"+i);
						String sex = map.get("sex"+i)==null?"":map.get("sex"+i);
						String acbirthday = map.get("acbirthday"+i)==null?"":map.get("acbirthday"+i);
						String acvocation = map.get("acvocation"+i)==null?"":map.get("acvocation"+i);
						String sentence = map.get("sentence"+i)==null?"":map.get("sentence"+i);
						String address = map.get("address"+i)==null?"":map.get("address"+i);
						if(StringNumberUtil.notEmpty(acname)&&StringNumberUtil.notEmpty(sex)
								&&StringNumberUtil.notEmpty(acvocation)&&
						   StringNumberUtil.notEmpty(sentence)&&StringNumberUtil.notEmpty(sentence)&&
						   StringNumberUtil.notEmpty(address)){
							TbprisonerAccomplice tacc = new TbprisonerAccomplice();
							tacc.setCrimid(crimid);
							tacc.setName(acname);
							tacc.setSex(sex);
							tacc.setBirthday(DateUtil.StringParseDate(acbirthday));
							tacc.setVocation(acvocation);
							tacc.setSentence(sentence);
							tacc.setAddress(address);
							tacc.setOpid(user.getUserid());
							tacc.setOptime(date);
							tacc.setCrimname(acname);
							tacc.setRemark(acname);
							if(StringNumberUtil.notEmpty(accid)&&StringNumberUtil.notEmpty(crimid)){
								result.put("accid"+i, accid);
								tacc.setAccid(accid);
								prisonerService.updateTbprisonerAccomplice(tacc);
							}else{
								accid=crimid+i;
								result.put("accid"+i, crimid+i);
								tacc.setAccid(accid);
								if(StringNumberUtil.notEmpty(acname)&&StringNumberUtil.notEmpty(crimid)){
									prisonerService.insertTbprisonerAccomplice(tacc);
								}
							}
						}else{
							if(StringNumberUtil.notEmpty(accid)&&StringNumberUtil.notEmpty(crimid)){
								TbprisonerAccompliceKey key = new TbprisonerAccompliceKey();
								key.setAccid(accid);
								key.setCrimid(crimid);
								prisonerService.deleteTbprisonerAccomplice(key);
							}
						}
					}
				}
			}
			
			String unitlevel=user.getOrganization().getUnitlevel();
			if("6".equals(unitlevel)||"7".equals(unitlevel)){
				return "{\"msg\":'fysuccess'}";
			}
			//GkzxCommon.RESULT_SUCCESS+ new JSONObject(result).toString()
			
			StringBuffer message = new StringBuffer("{\"msg\":'success',\"applyid\":'");
			message.append(crimid).append("',\"applyname\":\"").append(criminame);
			message.append("\",\"selectid\":").append(new JSONObject(result).toString());
			message.append(",'orgid':'" + org.getOrgid() + "'}");
			return  message.toString();
		}
		//return "{\"msg\":'success'}";
		return GkzxCommon.RESULT_ERROR;
	}
	
	//生成二维码
	public void generateQrcode(String crimid, String card,String FilePath){
		List<String> list = new ArrayList<String>();
		String remark = "";
		String pinyin = "";
		try {
			if(!"".equals(card)){
				card = card.substring(0, card.length()-1);
				//把中文姓名转化为 拼音
				Map maps = prisonerService.generatePYByCrimid(crimid);
				if (maps.size() > 0) {
					pinyin = maps.get("pyname").toString();
				}
				
				//获取罪犯姓名拼音前两个字符
				String num1 = crimid.substring(6);//从罪犯编号截取字符串
				String num2 = card.substring(8, 14);//从家属身份证号截取字符串
				
				for (int i = 0; i < num1.length(); i++) {
					list.add(num1.substring(i, i + 1));
				}
				
				for (int i = 0; i < num2.length(); i++) {
					list.add(num2.substring(i, i + 1));
				}
				Collections.shuffle(list);
				remark = pinyin + turnListToString(list);
				_crid = remark;
				
				createImg(remark,FilePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public void createImg(String remark,String FilePath) {		
		
		//String qrcode2=tbprisonerSocialRelation.getQrcode();
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');/* L','M','Q','H' */
		qrcode.setQrcodeEncodeMode('B');/* "N","A" or other */
		qrcode.setQrcodeVersion(3);/* 0-20 */
		String testString = remark;
		byte[] buff = null;
		try {
			buff = testString.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		boolean[][] bRect = qrcode.calQrcode(buff);
		DEFAULT_WIDTH = bRect.length * UNIT_WIDTH;

		BufferedImage bi = new BufferedImage(DEFAULT_WIDTH, DEFAULT_WIDTH,
				BufferedImage.TYPE_INT_RGB);
		int unitWidth = DEFAULT_WIDTH / bRect.length;

		// createGraphics
		Graphics2D g = bi.createGraphics();

		// set background
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, DEFAULT_WIDTH, DEFAULT_WIDTH);
		g.setColor(Color.BLACK);

		if (buff.length > 0 && buff.length < 123) {
			for (int i = 0; i < bRect.length; i++) {
				for (int j = 0; j < bRect.length; j++) {
					if (bRect[j][i]) {
						g.fillRect(j * UNIT_WIDTH, i * UNIT_WIDTH,UNIT_WIDTH - 1, UNIT_WIDTH - 1);
					}
				}
			}
		}

		g.dispose();
		bi.flush();
		File f = new File(FilePath);
		try {
			ImageIO.write(bi, "png", f);
			//调用生成二维码
			encoderQRCode(FilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码拼接微官网路径
	 */
	public void encoderQRCode(String imgPath) {
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);
			//微官网地址：如http://
			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage bufImg = new BufferedImage(140, 140,BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = "+ contentBytes.length + " not in [ 0,120 ]. ");
			}
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(imgPath);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", imgFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述：list集合转为字符串
	 * @param list
	 * @return
	 */
	public static String turnListToString(List list) {
		String listString = "";
		for (int i = 0; i < list.size(); i++) {
			listString = listString + list.get(i);
		}
		return listString;
	}
}
