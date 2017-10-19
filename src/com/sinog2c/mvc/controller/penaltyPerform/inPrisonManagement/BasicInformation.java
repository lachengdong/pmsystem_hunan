package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbdataSentchageMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.commutationParole.TbdataSentchageKey;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerAccompliceKey;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.prisoner.ZpublicDaMtxx;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.swetake.util.Qrcode;

/**
 * @author 基本信息 2014-7-17
 */
@Controller
@RequestMapping("/basicInfo")
@SuppressWarnings("all")
public class BasicInformation extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private TbdataSentchageService tbdataSentchageService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private TbprisonerBaseinfoMapper tbprisonerbaseinfomapper;
	@Autowired
	private TbprisonerBaseCrimeMapper tbprisonerbasecrimemapper;
	@Autowired
	private TbxfSentenceAlterationMapper tbxfsentencealterationmapper;
	@Autowired
	private TbdataSentchageMapper tbdataSentchageMapper;
	
	
//	@Autowired
//	private TbcourtFlowBaseInfoMapper tbcourtFlowBaseInfoMapper;
	
	private static int DEFAULT_WIDTH;
	private static int UNIT_WIDTH = 10;
	
	private String _crid = "";
	 
	private String content = "http://102187.wap.wei800.com/PrisonService/Prisoner/SearchResult.aspx?n=";
	
	String[] xingqicode = {"GK056","GK057","GK058"};//减刑幅度年月日code类型拼接成数组
	String[] xingqinum = {"punishmentyear","punishmentmonth","punishmentday"};//减刑幅度年月日code类型拼接成数组
	String[] CheckBoxIsguanfanContent = {"未成人","累犯","惯犯","限制减刑","重点关注","媒体关注","特管对象","法轮功","其他邪教"};//惯犯对应内容
	String[] CheckBoxsisheContent = {"涉毒","涉枪","涉黑","涉恶"};//四涉对应内容
	String[] CheckBoxsishiContent = {"吸毒史","脱逃史","自杀史","袭警史"};//四史对应内容
	String[][] CheckBoxContents = {CheckBoxIsguanfanContent,CheckBoxsisheContent,CheckBoxsishiContent};//所有复选框的内容拼成数组
	
	String[] CheckBoxIsguanfan = {"Isminor","Isrecidivism","Recidivist","Isremission","Focus","Mediaattention","Specialcontrol","Falungong","Qitaxiejiao"};//惯犯对应字段
	String[] CheckBoxsishe = {"Drugs","Gun","Underworld","Wickedness"};//四涉对应字段
    String[] CheckBoxsishi = {"Takedrugs","Escapes","Suicide","Assaultepolice"};//四史对应字段
    String[][] CheckBoxs = {CheckBoxIsguanfan,CheckBoxsishe,CheckBoxsishi};//所有复选框的节点名称拼成数组
    String[] CheckBox = {"CheckBoxIsguanfan","CheckBoxsishe","CheckBoxsishi"};//所有复选框的节点名称拼成数组
    
    
	/**
	 *方法描述：跳转到罪犯处理页面 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toBasicInfoChoosePage.page")
	public ModelAndView toBasicInfoChoosePage(HttpServletRequest request) throws Exception {
		String menuid = request.getParameter("menuid");
		String  _criminalid= request.getParameter("_criminalid")==null?"undefined":request.getParameter("_criminalid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		
		if (!"undefined".equals(_criminalid)) {
			request.setAttribute("_criminalid", _criminalid);
			
		}
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/basicInfoChoose");
	}
	
	@RequestMapping(value="/basicInfoChoosebulu.page")
	public ModelAndView toBasicInfoChoosePageBulu(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		returnResourceMap(request);
		return new ModelAndView(
		"penaltyPerform/inPrisonManagement/basicInfoChoosebulu");
	}

	/**
	 * 基本信息补录列表页，不关联流程等其他信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getBasicInfoChoosebulu.json")
	@ResponseBody			
	public Object getBasicInfoChoosebulu(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		map.put("orgid", getLoginUser(request).getOrgid());
		
    	int count = chooseCriminalService.countCrimNumByParaMap(map);//根据map传参获取总条数
    	data= chooseCriminalService.getCrimInfoByParaMap(map);//根据map传参获取罪犯列表
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	/**
	 *方法描述：获取罪犯列表数据 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBasicInfoChoose.json")
	@ResponseBody
	public Object getBasicInfoChoose(HttpServletRequest request) throws Exception {
		
		Map<String, Object> result = new HashMap<String,Object>();
		SystemUser user = this.getLoginUser(request);
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		
		map.put("departId", getLoginUser(request).getOrgid());
		
    	map.put("flowdefid", "doc_zfrjdjsp");
    	map.put("userid", user.getUserid());//给map传userid为了mapper中id为findWithFlow使用
    	
    	int count = chooseCriminalService.countFindWithFlow(map);//根据map传参获取总条数
    	List<Map<String,Object>> data = chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
    	
    	result.put("data", data);
    	result.put("total", count);
		
		return result;
	}
	
	/**
	 *方法描述：根据idnumber判断是否处理过 ，如果处理过将大字段显示出来
	 *   ，没被处理过显示一个新的表单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicInformation.page")
	public ModelAndView basicInformation(HttpServletRequest request) throws Exception {
		
		//快速检索传值
//		String sdid=request.getParameter("sdid");
//		request.setAttribute("sdid", sdid);
//		String zaiyacombo1=request.getParameter("zaiyacombo1");
//		request.setAttribute("zaiyacombo1", zaiyacombo1);
//		String key=request.getParameter("key");
//		if(null!=key&&!"".equals(key)){
//			try {
//				key = URLDecoder.decode(key,"UTF-8");
//				request.setAttribute("key", key);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
		
		//
		String idnumber = request.getParameter("idnumber");//等价于flowdraftid
		String crimid = request.getParameter("crimid");
		if(StringNumberUtil.isEmpty(crimid)){
			crimid = request.getParameter("applyid");
			
			if(StringNumberUtil.isEmpty(crimid)){
				String flowdraftid = request.getParameter("flowdraftid");
				if(StringNumberUtil.notEmpty(flowdraftid)){
					Map<String,Object> paramap = new HashMap<String,Object>();
					paramap.put("flowdraftid", flowdraftid);
					FlowBase fb = flowBaseService.getFlowBaseByMap(paramap);
					crimid = fb.getApplyid();
				}
			}
		}
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		
		//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		//
		JSONArray docconent = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>> jianxinginfoinfoList = prisonerService.getCriminalInfo(crimid);
		Map jianxinginfo = new HashMap<>();
		if (!jianxinginfoinfoList.isEmpty() && jianxinginfoinfoList != null) {
			jianxinginfo = jianxinginfoinfoList.get(0);
			jianxinginfo = StringNumberUtil.dealMapForForm(jianxinginfo);
		}
		
		
		//获取表单上的数据，封装于Map中
		map = dealBasicInfoAip(map, jianxinginfo);
		
		//复选框数据处理
//		map = dealBasicInfoCheckBox(map, baseCrime);
		
		//个人简历、家庭成员、主要社会关系、同案犯
		map = dealCriminalAttachInfo(map, crimid);
		//处理照片
		map = dealCriminalPicture(request, map, crimid, jyconfig);
		
		
		SystemUser user = getLoginUser(request);
		String username=user.getName();
		request.setAttribute("username", username);
		
		//
		String orgid=user.getOrgid();
		String unitlevel=flowBaseService.getUnitlevel(orgid);
		FlowBase flowBase = flowBaseService.getTianBiaoRen(crimid);
		String tianbiaoren = "";
		String shenheren = "";
		if(StringNumberUtil.notEmpty(flowBase)){
			tianbiaoren = flowBase.getText29();
			shenheren = flowBase.getText30();
		}
		if(StringNumberUtil.notEmpty(unitlevel) && "5".equals(unitlevel)){
			map.put("tianbiaoren",tianbiaoren);
			map.put("shenheren", username);
		}else{
			map.put("tianbiaoren", username);
			map.put("shenheren", shenheren);
		}
		
		if("".equals(map.get("tianbiaoren"))){
			map.put("tianbiaoren", user.getName());
		}
		
		
		
		//所有下拉列表显示
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap = dealBasicInfoSelectMap(selectmap);
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		
		
//			List<Map<String, Object>> excecutedatalist =tbdataSentchageMapper.selectExecuteDateBycrimid(crimid);
//			Map<String,Object> excecutedatemap=new HashMap<String,Object>();
//			excecutedatemap=excecutedatalist.get(0);
		
		//EXECUTIONDATE
//		map.put("sjxfzxjgzr",excecutedatemap.get("EXECUTIONDATEAIP"));//获取刑法变动表里的执行日期
		
		
		String tempid = tempid = "SDXF_BASE_RJDJB";//request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		//处理大字段
//		if(StringNumberUtil.notEmpty(idnumber)){
//			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
//			docconent.add(docConent);
//			request.setAttribute("flowdraftid", idnumber);
//		}else{
//		}
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());//获取表单
		if (template != null) docconent.add(template.getContent());
		//
		
		//
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		String closetype = request.getParameter("closetype");
		if(StringNumberUtil.notEmpty(closetype)){
			request.setAttribute("closetype", closetype);
		}
		//
		String fathermenuid = request.getParameter("fathermenuid");
		if(StringNumberUtil.notEmpty(fathermenuid)){
			request.setAttribute("fathermenuid", fathermenuid);
		}
		
		
		//
		if(StringNumberUtil.notEmpty(idnumber)){
			request.setAttribute("flowdraftid", idnumber);
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyname",jianxinginfo.get("CRIMNAME"));
		request.setAttribute("flowdefid", "doc_zfrjdjsp");
		request.setAttribute("orgid",jianxinginfo.get("ORGID1"));
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		
		return new ModelAndView("penaltyPerform/inPrisonManagement/addBasicInformation");
	}
	
	/**
	 * 下拉选择框处理
	 * @param selectmap
	 * @return
	 */
	private Map<String, Object> dealBasicInfoSelectMap(Map<String, Object> selectmap){
		
		//基本信息国籍 不让选择就直接让录入
		//selectmap.put("cbinativeplaceaddress", systemCodeService.getcodeValue("GB002"));
		String cbigender = systemCodeService.getcodeValue("GB003");
		selectmap.put("cbigender", cbigender);
		for (int i = 0; i < 6; i++) {
			selectmap.put("sex"+i, cbigender);
		}
		
		selectmap.put("caiformermarriage", systemCodeService.getcodeValue("GB004"));
		selectmap.put("cbination", systemCodeService.getcodeValue("GB006"));
		selectmap.put("caieducation", systemCodeService.getcodeValue("GB007"));
		//selectmap.put("xingba", systemCodeService.getcodeValue("GKXBLB"));
		
		String political = systemCodeService.getcodeValue("GB008");
		selectmap.put("caipoliticalappearance", political);
		for (int i = 0; i < 10; i++) {
			selectmap.put("political"+i, political);
		}
		
		selectmap.put("cbiresidenttype", systemCodeService.getcodeValue("GB010"));
		selectmap.put("caiformerlevel", systemCodeService.getcodeValue("GB018"));
		selectmap.put("cjiimprisontype", systemCodeService.getcodeValue("GB022"));
		selectmap.put("caiflowtype", systemCodeService.getcodeValue("GB025"));
		selectmap.put("criofficiallyplacetype", systemCodeService.getcodeValue("GB031"));
		selectmap.put("cjicriminalsort", systemCodeService.getcodeValue("GB036"));
		
		String relationship = systemCodeService.getcodeValue("GK004");
		for (int i = 0; i < 7; i++) {
			selectmap.put("relationship"+i, relationship);
		}
		
		selectmap.put("aiformervocation", systemCodeService.getcodeValue("GK041"));  
		selectmap.put("aiformerduty", systemCodeService.getcodeValue("GK043"));
		selectmap.put("cjappeal", systemCodeService.getcodeValue("GK046"));
		selectmap.put("cjidisfranchiseyear", systemCodeService.getcodeValue("GK059"));
		
		return selectmap;
	}
	
	
	private Map dealBasicInfoCheckBox(Map map, TbprisonerBaseCrime baseCrime) throws Exception{
		//复选框数据处理
		for(int j=0;j<CheckBoxs.length;j++){
			int value = 0;//所选中的复选框2进制转换值之和
			for(int i=0;i<CheckBoxs[j].length;i++){
				Field[] fields = baseCrime.getClass().getDeclaredFields();//获取baseCrime类的所有get方法
	        	for (Field field : fields) {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), baseCrime.getClass());
					Method getMethod = pd.getReadMethod();
					String method = getMethod.toString();
					if (method.contains(CheckBoxs[j][i])) {
						Object obj= getMethod.invoke(baseCrime);
						if(null!=obj  && ("1".equals(obj.toString()) || "是".equals(obj.toString()))){ 
							int ivalue = 1<<i;//所选中的复选框第i个的2进制转换值
							value += ivalue;
						 }
					}
				}
				map.put(CheckBox[j], value);
			}
		}
		
		return map;
	}
	
	private Map dealCriminalPicture(HttpServletRequest request, Map map, String crimid, Properties jyconfig) throws Exception{
		
		//半身照
		String imgtype = "_1.jpg";//照片类型，1表示半身照
		String image = request.getParameter("image");//.replace("%25255C", "/");  //转图片路径
		String picPath = jyconfig.getProperty(GkzxCommon.CRIMINALPHOTO_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALPHOTO_ABSOLUTEPATH);
		String path = picPath + crimid + imgtype;
		//
		File file = new File(path);
		if(file.exists()){
			String criminalPicBase64Str = FileUtil.encodeBase64File(path);
			map.put("picrjdjimg", criminalPicBase64Str);
		}else{
			Map<String,Object> mtxxMap = new HashMap<String,Object>();
			mtxxMap.put("bh", crimid);
			mtxxMap.put("mtbm", "11");
			mtxxMap.put("mtlb", "1");
			ZpublicDaMtxx baseMtxx = prisonerService.getHeadPicture(mtxxMap); 
			if(baseMtxx != null) {
				if(baseMtxx.getNr().endsWith(".jpg"))
				    map.put("picrjdjimg", baseMtxx.getNr().replaceAll("\\\\", "/"));
				else
					map.put("picrjdjimg", baseMtxx.getNr());
			}
		}
		
		return map;
	}
	
	
	private Map dealCriminalAttachInfo(Map map, String crimid){
		
		List<TbprisonerResume> resumeList = prisonerService.findByCrimidResume(crimid);//个人简历
		List<TbprisonerSocialRelation> relationList = prisonerService.findRelationBycrimid(crimid);// 家庭成员、主要社会关系
		List<TbprisonerAccomplice> accompliceList = prisonerService.selectAccompliceByCrimid(crimid);//同案犯
		
		//个人简历
		if (resumeList != null && !resumeList.isEmpty()) {
			for (int i = 0; i < resumeList.size(); i++) {
				TbprisonerResume tr = resumeList.get(i);
				map.put("resumeid"+i, tr.getResumeid());
				map.put("begindate"+i, tr.getBegindate());
				map.put("enddate"+i, tr.getEnddate());
				map.put("vocation"+i, tr.getVocation());// 职业
				map.put("orgname"+i, tr.getOrgname());// 所在单位
			}
		}
		
		
		// 家庭成员、主要社会关系
		if (relationList != null && !relationList.isEmpty()) {
			for (int i = 0; i < relationList.size(); i++) {
				TbprisonerSocialRelation tsr = relationList.get(i);
				map.put("srid"+i, tsr.getSrid());
				map.put("relationship"+i, tsr.getRelationship());
				map.put("name"+i, tsr.getName());
				map.put("political"+i, tsr.getPolitical());
				map.put("homeaddress"+i, tsr.getHomeaddress());
				map.put("phone"+i, tsr.getPhone());
				
				map.put("birthday"+i, DateUtil.dateFormatForAip(DateUtil.StringParseDate(tsr.getBirthday())));
				map.put("srorgname"+i, tsr.getPaperid()==null?"":tsr.getPaperid());
			}
		}
		
		
		//同案犯
		if (accompliceList != null && !accompliceList.isEmpty()) {
			for (int i = 0; i < accompliceList.size(); i++) {
				TbprisonerAccomplice ta = accompliceList.get(i);
				map.put("accid"+i, ta.getAccid());
				map.put("acname"+i, ta.getName());
				map.put("sex"+i, ta.getSex());
				map.put("acbirthday"+i, DateUtil.dateFormatForAip(ta.getBirthday()));
				map.put("acvocation"+i, ta.getVocation());
				map.put("sentence"+i, ta.getSentence());
				map.put("address"+i, ta.getAddress());
			}
		}
		
		
		return map;
		
	}
	
	
	/**
	 * 获取表单上的数据，封装于Map中
	 * @param map
	 * @param jianxinginfo
	 * @return
	 * @throws Exception
	 */
	private Map dealBasicInfoAip(Map<String, Object> map, Map jianxinginfo) throws Exception{
		
		map.put("depid",jianxinginfo.get("ORGNAME1"));//部门
		map.put("criminalid", jianxinginfo.get("CRIMID"));// 罪犯编号
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
		
		map.put("cbiidentitycard",jianxinginfo.get("SHENFENZHENGHAO"));// 身份证号
		map.put("fazhengjiguan",jianxinginfo.get("FAZHENGJIGUAN"));// 发证机关
		
		map.put("aiformerduty", jianxinginfo.get("BUQIANZHIWU"));// 捕前职务
		map.put("caiformerlevel", jianxinginfo.get("ZHIWUJIBIE"));// 职务级别
		
		map.put("cbinativeplaceaddress",jianxinginfo.get("JIGUAN"));//籍贯
		map.put("cbiresidenceaddress",jianxinginfo.get("HUJISUOZAIDI"));// 户籍所在地						
		map.put("cbiresidenttype", jianxinginfo.get("HUKOULEIXING"));// 户口类型
		
		map.put("cbihomeaddress",jianxinginfo.get("JIATINGZHUZHI"));// 家庭住址
		map.put("sjxfzxjgzr", jianxinginfo.get("EXECUTIONDATEAIP"));// 获取刑法变动表里的执行日期
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
		
		map.put("court_name",jianxinginfo.get("COURTNAME"));// 终审判决判决法院（行政区划名称）
		map.put("court_title",jianxinginfo.get("COURTTITLE"));// 终审判决判决法院（法院称谓）
		map.put("court_code",jianxinginfo.get("COURTAREA"));// 判决法院地区code
		map.put("caseno1", jianxinginfo.get("COURTYEAR"));
		map.put("caseno2", jianxinginfo.get("COURTSHORT"));
		map.put("caseno3", jianxinginfo.get("COURTSN"));
		
		
		map.put("zhuanyou",jianxinginfo.get("CAUSEACTION"));// 罪名（主案由）
		map.put("qitaanyou",jianxinginfo.get("QITAZUIMING"));// （其他案由）
		map.put("fanzuishijian",jianxinginfo.get("FANZUISHIJIAN2"));// 犯罪时间
		map.put("xingba",jianxinginfo.get("XINGBA"));//是否刑八
		
		map.put("cjiimprisontype",jianxinginfo.get("YUANPANXINGZHONG"));// 刑罚种类
		map.put("zhuxing", jianxinginfo.get("YUANPANXINGQI"));//刑期
		map.put("zhuxing0", jianxinginfo.get("ORIGINALYEAR"));//原判刑期年
		map.put("zhuxing1", jianxinginfo.get("ORIGINALMONTH"));//原判刑期月
		map.put("zhuxing2", jianxinginfo.get("ORIGINALDAY"));//原判刑期天
		map.put("cjidisfranchiseyear", jianxinginfo.get("YUANPANBOQUAN"));// 剥夺年限
	
		map.put("cjibegindate",jianxinginfo.get("YUANPANXINGQIQIRI2"));// 刑期起日
		map.put("cjienddate", jianxinginfo.get("YUANPANXINGQIZHIRI2"));// 刑期止日
		
		if(StringNumberUtil.isEmpty(jianxinginfo.get("YUANFAJIN"))){
			map.put("cjimulct", "/");// 罚金
		}else{
			map.put("cjimulct",jianxinginfo.get("YUANFAJIN"));// 罚金
		}
		map.put("cjmoneydisgorged", StringNumberUtil.isEmpty(jianxinginfo.get("YUANPANZHUIZANG"))?"/":jianxinginfo.get("YUANPANZHUIZANG"));// 追缴赃款
		
		//责令退赔
		map.put("tuipei", StringNumberUtil.isEmpty(jianxinginfo.get("ZELINGTUIPEI")) ? "/" : jianxinginfo.get("ZELINGTUIPEI"));// 责令退赔
		
		map.put("cjisequestrateproperty", StringNumberUtil.isEmpty(jianxinginfo.get("YUANMOSHOU")) ? "/" : jianxinginfo.get("YUANMOSHOU"));// 没收财产
		
		map.put("cjotherjudge", StringNumberUtil.isEmpty(jianxinginfo.get("YUANMINPEI")) ? "/" : jianxinginfo.get("YUANMINPEI"));// 民事赔偿
		map.put("cjappeal",jianxinginfo.get("SHANGSUQINGKUANG"));// 上诉情况
		
		map.put("caioldpunish",jianxinginfo.get("QKQK"));// 曾受过处罚
		
//		map.put("fayuancaipan",jianxinginfo.get("FAYUANPANCAI")==null?"":jianxinginfo.get("FAYUANPANCAI").toString().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));//法院判裁
		map.put("fayuancaipan",jianxinginfo.get("FAYUANPANCAI"));//法院判裁
		
//		if(null!=jianxinginfo.get("FANZUISHISHI")){
//			Clob clob = (Clob)jianxinginfo.get("FANZUISHISHI");
//			String fanzuishishi = clob.getSubString(1, (int)clob.length());
//			map.put("fanzuishishi",fanzuishishi.replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));// 犯罪事实
//		}
		map.put("fanzuishishi",jianxinginfo.get("FANZUISHISHI"));// 犯罪事实
		
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
		
		return map;
	}
	
	
	/**
	 * 方法描述：输入减刑幅度年月日codeId获取值,如果有一个codeId不存在直接返回null
	 */
	@RequestMapping(value ="/toXingQi.json")
	@ResponseBody
	public String toXingQi(HttpServletRequest request) throws Exception {
		String result = "";
		String codeids = request.getParameter("codeid");
		for(int i=0;i<xingqicode.length;i++){
			 int codeid = Integer.parseInt(codeids.substring(i*2, i*2+2));//循环截取输入的值作为年月日的codeid
			 if(codeid!=0){
				 String name = systemCodeService.getCodeValueByStrings(xingqicode[i],Integer.toString(codeid));
				 if(name!=null && !"".equals(name)){
					 result += name; 
				 }else{
					 return null;
				 }
			 }
		}
		return result;
	}
	
	
	/**
	 *方法描述：保存罪犯基本信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCrimInfo.json")
	@ResponseBody
	public Object saveCrimInfo(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		String selectid = request.getParameter("selectid")==null?"":request.getParameter("selectid");

		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		
		SystemUser user = getLoginUser(request);
		Date date = new Date();
		if (noteinfo != null) {
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map)list.get(0);
				if(!StringNumberUtil.isNullOrEmpty(selectid)){
					ArrayList selectlist = (ArrayList) JSON.parseArray(selectid, Object.class);
					Map<String,String> selectmap = (Map)selectlist.get(0);
					map.putAll(selectmap);
				}
				
				map = StringNumberUtil.dealMapForForm(map);
				
				if(crimid==null) crimid=map.get("criminalid");
				String tianbiaoren = map.get("tianbiaoren");
				String shenheren = map.get("shenheren");
				//基本信息保存             
				Map<String,Object> hashMap  = new HashMap<String,Object>();
				hashMap.put("crimid", crimid);
				hashMap.put("name",  map.get("cbiname"));
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
				
				
				//发证机关
				
				//逮捕机关
				hashMap.put("countryarea",  map.get("cbinativeplaceaddress"));
				hashMap.put("originplaceaddress",  map.get("cbinativenamedetail"));
				hashMap.put("residencetype",  map.get("cbiresidenttype"));
				
				String caioldpunish = map.get("caioldpunish").replaceAll("\"", "“").replaceAll(":", "：").replaceAll("'", "’");
				hashMap.put("rewardpunish", caioldpunish);
				//hashMap.put("rewardpunish", map.get("caioldpunish"));
				
				hashMap.put("accent",  map.get("cbispeak"));
				hashMap.put("opid",  user.getUserid());
				//hashMap.put("accent",  date);
				prisonerService.updateByCrimid(hashMap);
				//处罚信息表
    			Map<String,Object> baseCrimeMap  = new HashMap<String,Object>();

				//发证机关
				baseCrimeMap.put("issuingauthority", map.get("fazhengjiguan"));
    			
    			
				baseCrimeMap.put("crimid", crimid);

				String fanzuishishi = map.get("fanzuishishi").replaceAll("\"", "“").replaceAll(":", "：").replaceAll("'", "’");
				baseCrimeMap.put("crimeface", fanzuishishi);
				//baseCrimeMap.put("crimeface", map.get("fanzuishishi"));
				
				baseCrimeMap.put("crimedate", DateUtil.StringParseDate(map.get("fanzuishijian")));
				baseCrimeMap.put("detaindate",  DateUtil.StringParseDate(map.get("caidetaindate")));
				baseCrimeMap.put("transferreddate",  DateUtil.StringParseDate(map.get("diaoruriqi")));//调入日期保存
				
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
				
				//---------------------------------------责令退赔
				baseCrimeMap.put("returncompenstation", map.get("tuipei"));
				
				baseCrimeMap.put("judgedate", DateUtil.StringParseDate(map.get("cjijudgedate")));
				baseCrimeMap.put("watchhouse", map.get("criofficiallydepartment"));
				baseCrimeMap.put("detaintype", map.get("criofficiallyplacetype"));
				baseCrimeMap.put("fileno", map.get("danganhao"));
				baseCrimeMap.put("othercase", map.get("qitaanyou"));
				baseCrimeMap.put("maincase", map.get("zhuanyou"));
				baseCrimeMap.put("causeaction", map.get("zhuanyou"));
				baseCrimeMap.put("punishmenttype", map.get("cjiimprisontype"));
				//baseCrimeMap.put("causeaction", map.get("zhuanyou")+" "+map.get("qitaanyou"));    
				
				String fayuancaipan = map.get("fayuancaipan").replaceAll("\"", "“").replaceAll(":", "：").replaceAll("'", "’");
				baseCrimeMap.put("firsttrialsum",  fayuancaipan);
				//baseCrimeMap.put("firsttrialsum",  map.get("fayuancaipan"));
				
				baseCrimeMap.put("opid",  user.getUserid());
				baseCrimeMap.put("optime",  date);
				baseCrimeMap.put("prison", map.get("prison"));
				
				baseCrimeMap.put("punishmentyear", map.get("zhuxing0"));
				baseCrimeMap.put("punishmentmonth", map.get("zhuxing1"));
				baseCrimeMap.put("punishmentday", map.get("zhuxing2"));
				
				//--------------------------处理  并罚情况代码
				String xingba = map.get("xingba");
				baseCrimeMap.put("punishmenteight", xingba);
				//三类犯
				if (map.get("sanleicode") != null
						&& !"".equals(map.get("sanleicode"))) {
					if (map.get("sanleicode").toString().contains("J")) {// 金融犯
						baseCrimeMap.put("undermine", "1");
					} else {
						baseCrimeMap.put("undermine", "0");
					}
					if (map.get("sanleicode").toString().contains("h")) {// 涉黑罪犯
						baseCrimeMap.put("underworld", "1");
					} else {
						baseCrimeMap.put("underworld", "0");
					}
					if (map.get("sanleicode").toString().contains("z")) {// 职务罪犯
						baseCrimeMap.put("postcrime", "1");
					} else {
						baseCrimeMap.put("postcrime", "0");
					}
				} else {

					baseCrimeMap.put("undermine", "0");
					baseCrimeMap.put("postcrime", "0");
					baseCrimeMap.put("underworld", "0");
				}

				baseCrimeMap.put("sanclassstatus",map.get("sanleicode"));
				baseCrimeMap.put("sanremark", map.get("rendingyiju"));
				baseCrimeMap.put("casenature", map.get("zhongyaozuifanleixing"));
				baseCrimeMap.put("caseothertype", map.get("othertype"));
				
				List<TbsysCode> boZhenList = systemCodeService.selectValueByCodeType("GK059");//剥政年限类型
				String boZhenStr = map.get("cjidisfranchiseyear");
				if(StringNumberUtil.notEmpty(boZhenStr)){
					for(TbsysCode tc : boZhenList){
						if(tc.getName().equals(boZhenStr)){
							baseCrimeMap.put("losepoweryear", tc.getCodeid());
						}
					}
				}
				
//				selectmap.put("cjidisfranchiseyear", systemCodeService.selectValueByCodeType("GK059"));
				
				//复选框数据保存
//				for(int x=0;x<CheckBox.length;x++){
//					String value = map.get(CheckBox[x])==null?"":map.get(CheckBox[x]);
//					if(value != null){
//						for(int i=0;i<CheckBoxContents[x].length;i++){
//							if (value.contains(CheckBoxContents[x][i])){
//								baseCrimeMap.put((CheckBoxs[x][i]).toLowerCase(), "1");
//							}else {
//								baseCrimeMap.put((CheckBoxs[x][i]).toLowerCase(), "0");
//							}
//						}
//					}
//				}
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
				
				//修改TBDATA_SENTCHAGE的原判信息，由于原判信息全部在刑罚变动表中维护，所以现在关闭，
//				saveDataSentchageData(crimid, map.get("court_name"), map.get("court_code"), baseCrimeMap);
				
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
						}else{
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
				return GkzxCommon.FYSAVEDATA;
			}
			return GkzxCommon.RESULT_SUCCESS+ new JSONObject(result).toString();
		}
		return GkzxCommon.RESULT_ERROR;
	}
	
	
	private void saveDataSentchageData(String crimid, String courtname, String courtCode, Map<String,Object> baseCrimeMap){
		
		//更新表TBDATA_SENTCHAGE中的数据。
		TbdataSentchageKey dataSentchageKey = new TbdataSentchageKey();
		dataSentchageKey.setCrimid(crimid);
		dataSentchageKey.setCourtsanction(DateUtil.dateFormatForAip(baseCrimeMap.get("judgedate")));
		TbdataSentchage dataSentchage = tbdataSentchageMapper.selectByPrimaryKey(dataSentchageKey);
		if(null != dataSentchage){
			assembleDataSentchage(dataSentchage, crimid, courtname, courtCode, baseCrimeMap);
			
			tbdataSentchageMapper.updateByPrimaryKeySelective(dataSentchage);
		}else{
			dataSentchage = new TbdataSentchage();
			
			assembleDataSentchage(dataSentchage, crimid, courtname, courtCode, baseCrimeMap);
			dataSentchage.setCourtsanction(DateUtil.dateFormatForAip(baseCrimeMap.get("judgedate")));//法院判裁日期
			dataSentchage.setCourtchange(dataSentchage.getSentence());//法院变动幅度
			
			tbdataSentchageMapper.insert(dataSentchage);
		}
		
		
	}
	
	
	private TbdataSentchage assembleDataSentchage(TbdataSentchage dataSentchage, String crimid, String courtname, String courtCode, Map<String,Object> baseCrimeMap){
		
		dataSentchage.setCrimid(crimid);
		
		dataSentchage.setCategory("1");//1代表原判
		dataSentchage.setCourtname(courtname);//法院判裁法院（行政区划名称）
		dataSentchage.setCourttitle(StringNumberUtil.getStrFromMap("judgmentshort", baseCrimeMap));//法院判裁法院
		
		
		dataSentchage.setCourtyear( StringNumberUtil.getStrFromMap("caseyear", baseCrimeMap));//法院判裁案号（年度)
		dataSentchage.setCourtsn( StringNumberUtil.getStrFromMap("caseno", baseCrimeMap));//法院判裁案号（序号）
		dataSentchage.setCourtshort( StringNumberUtil.getStrFromMap("caseorgshort", baseCrimeMap));//法院判裁案号（法院简称及字号）
		
		String punishmentyear = StringNumberUtil.getStrFromMap("punishmentyear", baseCrimeMap);
		String sentence = "";
		if("9996".equals(punishmentyear) || "9995".equals(punishmentyear)){
			sentence = punishmentyear;
		}else{
			if(punishmentyear.length() ==1){
				punishmentyear = "0" + punishmentyear;
			}
			String punishmentmonth = StringNumberUtil.getStrFromMap("punishmentmonth", baseCrimeMap);
			if(punishmentmonth.length() ==1){
				punishmentmonth = "0" + punishmentmonth;
			}
			String punishmentday = StringNumberUtil.getStrFromMap("punishmentday", baseCrimeMap);
			if(punishmentday.length() == 1){
				punishmentday = "0" + punishmentday;
			}
			
			sentence = punishmentyear + punishmentmonth + punishmentday;
		}
		dataSentchage.setSentence(sentence);//刑期
		
		dataSentchage.setCourtchangefrom(DateUtil.dateFormatForAip( baseCrimeMap.get("sentencestime") ));//刑期起日
		dataSentchage.setCourtchangeto(DateUtil.dateFormatForAip( baseCrimeMap.get("sentenceetime") ));//法院刑期止日
		dataSentchage.setCourtarea(courtCode);//判裁地区
		
		dataSentchage.setLosepower(StringNumberUtil.getStrFromMap("losepoweryear", baseCrimeMap));//剥夺政治权利
		
		dataSentchage.setExectime( DateUtil.dateFormatForAip( baseCrimeMap.get("executiondate") ) );//执行日期
		
		//财产刑判项无须同步到刑罚变动表中去
//		dataSentchage.setFine(StringNumberUtil.getStrFromMap("forfeit", baseCrimeMap));//罚金金额 
//		dataSentchage.setCivilfine( StringNumberUtil.getStrFromMap("compensation", baseCrimeMap));//民事赔偿金额 
//		dataSentchage.setExpropriationinfo(StringNumberUtil.getStrFromMap("forfeitureproperty", baseCrimeMap));//没收财产情况  
		
		dataSentchage.setCharge(StringNumberUtil.getStrFromMap("causeaction", baseCrimeMap));
		
		return dataSentchage;
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
	 * @方法描述：保存社会关系信息
	 * @author mushuhong
	 * @version 2014-9-28 下午02:23:24
	 */
	@SuppressWarnings("all")
	@RequestMapping(value="/querySocialRelation")
	private void querySocialRelation(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		List<Map> listMaps = prisonerService.querySocialRelation(crimid);//通过罪犯编号查询出 对应的图片大字段
		try {
			int i = 0;
			for (Map map : listMaps) {
				Object qrcode = map.get("QRCODE")==null?"":map.get("QRCODE");
				if (!"".equals(qrcode)) {
					Clob clob = (Clob)qrcode;
					String docconent = clob.getSubString(1, (int)clob.length());
					byte[] imgbyte = (byte[])DatatypeConverter.parseBase64Binary(docconent);//base64转化为byte
					saveToimg(imgbyte,i);
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方法描述：把二进制流转化为图片
	 * 
	 * @param imgByte
	 */
	private void saveToimg(byte[] imgByte,int png){
		InputStream in = new ByteArrayInputStream(imgByte);
	    String imgPath = "c:\\";
	    String imgName = "textimg"+png+".png";
        File file=new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] b = new byte[1024];
	        int nRead = 0;
	        while ((nRead = in.read(b)) != -1) {
	            fos.write(b, 0, nRead);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
		        fos.flush();
		        fos.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	
//	/**
//	 * 保存法院立案表罪犯基本信息
//	 * 不能保存\\r\\n 或\r\n入库。
//	 * @param request
//	 */
//	@RequestMapping("/saveFYLABCrimidInfo")
//	@ResponseBody
//	public void saveFYLABCrimidInfo(HttpServletRequest request){
//		try{
//		String data = request.getParameter("data");//获取表单页面的数据
//		String crimid=request.getParameter("applyid");
//		String tempid = request.getParameter("tempid");//表单ID
//		String flowdraftid=request.getParameter("flowdraftid");
//		if(null!=tempid&&"XFZX_FYLAB".equals(tempid)&&null!=crimid){
//			TbprisonerBaseinfo baseinfo=tbprisonerbaseinfomapper.selectByPrimaryKey(crimid);
//			TbprisonerBaseCrime basecrime=tbprisonerbasecrimemapper.selectPrisonerById(crimid);
//			TbxfSentenceAlteration alteration=tbxfsentencealterationmapper.selectByPrimaryKey(crimid);
//			Map map2=new HashMap();
//			map2.put("flowdraftid", flowdraftid);
//			FlowBase flowBase=flowBaseService.getFlowBaseByMap(map2);
//				if(null!=data){
//					ArrayList rows = (ArrayList) JSON.parseArray(data, Object.class);
//				//法院办案时，获取表单内容，更新到对应表
//					if(rows!=null && rows.size()>0){
//						Map map=new HashMap();
//						map=(Map)rows.get(0);
//						String usedname=(String)map.get("usedname")==null?"":(String)map.get("usedname");//别名
//						String name=(String)map.get("name")==null?"":(String)map.get("name");//姓名
//						String gender=(String)map.get("gender")==null?"":(String)map.get("gender");//性别
//						String cbibirthday=(String)map.get("cbibirthday")==null?"":(String)map.get("cbibirthday");//出生日期
//						String nation=(String)map.get("nation")==null?"":(String)map.get("nation");//民族
//						String grade=(String)map.get("grade")==null?"":(String)map.get("grade");//捕前级别
//						String duty=(String)map.get("duty")==null?"":(String)map.get("duty");//捕前职务
//						String education=(String)map.get("education")==null?"":(String)map.get("education");//文化
//						String origin=(String)map.get("origin")==null?"":(String)map.get("origin");//国籍/户籍
//						String guojia=(String)map.get("guojia")==null?"":(String)map.get("guojia");//国家
//						String addressdetail=(String)map.get("addressdetail")==null?"":(String)map.get("addressdetail");//住所地
//						String arrestauthority=(String)map.get("arrestauthority")==null?"":(String)map.get("arrestauthority");//逮捕机关
//						String arrestdate=(String)map.get("arrestdate")==null?"":(String)map.get("arrestdate");//逮捕日期
//						String detaindate=(String)map.get("detaindate")==null?"":(String)map.get("detaindate");//羁押日期
//						String judgmentname=(String)map.get("judgmentname")==null?"":(String)map.get("judgmentname");//终审法院
//						String caseno=(String)map.get("caseno")==null?"":(String)map.get("caseno");//终审案件号
//						String judgedate=(String)map.get("judgedate")==null?"":(String)map.get("judgedate");//裁判日期
//						String maincase=(String)map.get("maincase")==null?"":(String)map.get("maincase");//主案由
//						String othercase=(String)map.get("othercase")==null?"":(String)map.get("othercase");//其他案由
//						String punishmenttype=(String)map.get("punishmenttype")==null?"":(String)map.get("punishmenttype");//刑罚
//						String punishmentyear=(String)map.get("punishmentyear")==null?"":(String)map.get("punishmentyear");//刑期
//						String losepoweryear=(String)map.get("losepoweryear")==null?"":(String)map.get("losepoweryear");//剥权年限
//						String firsttrialsum=(String)map.get("firsttrialsum")==null?"":(String)map.get("firsttrialsum");//法院判裁经过
//						String sentencechageinfo=(String)map.get("sentencechageinfo")==null?"":(String)map.get("sentencechageinfo");//刑期变动信息
//						String lastpunishmentyear=(String)map.get("lastpunishmentyear")==null?"":(String)map.get("lastpunishmentyear");//裁判余刑 年  月  日
//						String islimit=(String)map.get("RadioBoxislimit")==null?"":(String)map.get("RadioBoxislimit");//是否限制减刑
//						String stayexecution=(String)map.get("stayexecution")==null?"":(String)map.get("stayexecution");//死缓起算日
//						String noperiod=(String)map.get("noperiod")==null?"":(String)map.get("noperiod");//无期徒刑起算日
//						String exnoperiod=(String)map.get("exnoperiod")==null?"":(String)map.get("exnoperiod");//无期徒刑执行刑期
//						String sentencestart=(String)map.get("sentencestart")==null?"":(String)map.get("sentencestart");//刑期起日
//						String registeraddressdetail=(String)map.get("registeraddressdetail")==null?"":(String)map.get("registeraddressdetail");
//						String causeaction=(String)map.get("causeaction")==null?"":(String)map.get("causeaction");//案由汇总
//						String inprisondate=(String)map.get("inprisondate")==null?"":(String)map.get("inprisondate");//入监日期
//						String executiondate=(String)map.get("executiondate")==null?"":(String)map.get("executiondate");
//						String sentenceetime=(String)map.get("sentenceetime")==null?"":(String)map.get("sentenceetime");
//						String crimeface=(String)map.get("crimeface")==null?"":(String)map.get("crimeface");//主要犯罪事实
//						String sentencechageinfoshort=(String)map.get("sentencechageinfoshort")==null?"":(String)map.get("sentencechageinfoshort");//刑期变动简述
//						String redrugs=(String)map.get("redrugs")==null?"":(String)map.get("redrugs");//
//						String nowpunishmentyear=(String)map.get("nowpunishmentyear")==null?"":(String)map.get("nowpunishmentyear");//目前余刑
//						String delivery=(String)map.get("delivery")==null?"":(String)map.get("delivery");//交付日期
//						String fristsubmit=(String)map.get("fristsubmit")==null?"":(String)map.get("fristsubmit");//首次提请减刑日期
//						String commutationdate=(String)map.get("commutationdate")==null?"":(String)map.get("commutationdate");//减刑起始日期
//						String predate=(String)map.get("predate")==null?"":(String)map.get("predate");//上次减刑裁定日期
//						String thisdate=(String)map.get("thisdate")==null?"":(String)map.get("thisdate");//本次减刑提起日期
//						String intervalyear=(String)map.get("intervalyear")==null?"":(String)map.get("intervalyear");//减刑间隔
//						String fine=(String)map.get("fine")==null?"":(String)map.get("fine");//裁判罚金
//						String sumfine=(String)map.get("sumfine")==null?"":(String)map.get("sumfine");//原已交罚金
//						String thisfine=(String)map.get("thisfine")==null?"":(String)map.get("thisfine");//本次已交罚金
//						String recovered=(String)map.get("recovered")==null?"":(String)map.get("recovered");//追缴赃款
//						String sumrecovered=(String)map.get("sumrecovered")==null?"":(String)map.get("sumrecovered");//原已追缴赃款
//						String thisrecovered=(String)map.get("thisrecovered")==null?"":(String)map.get("thisrecovered");//本次已缴赃款
//						String expropriation=(String)map.get("expropriation")==null?"":(String)map.get("expropriation");//没收财产
//						String sumexpropriation=(String)map.get("sumexpropriation")==null?"":(String)map.get("sumexpropriation");//原已没收
//						String thisexpropriation=(String)map.get("thisexpropriation")==null?"":(String)map.get("thisexpropriation");//本次没收
//						String civil=(String)map.get("civil")==null?"":(String)map.get("civil");//民事赔偿
//						String sumcivil=(String)map.get("sumcivil")==null?"":(String)map.get("sumcivil");//原已赔偿
//						String thiscivil=(String)map.get("thiscivil")==null?"":(String)map.get("thiscivil");//本次赔偿
//						String income=(String)map.get("income")==null?"":(String)map.get("income");//期内收入
//						String pay=(String)map.get("pay")==null?"":(String)map.get("pay");//支出
//						String overplus=(String)map.get("overplus")==null?"":(String)map.get("overplus");//剩余
//						String jailinfo=(String)map.get("jailinfo")==null?"":(String)map.get("jailinfo");//监狱意见
//						String administration=(String)map.get("administration")==null?"":(String)map.get("administration");//监狱局意见
//						String prosecutor=(String)map.get("prosecutor")==null?"":(String)map.get("prosecutor");//检察机关出庭意见
//						String jailname=(String)map.get("jailname")==null?"":(String)map.get("jailname");//监狱名称
//						String rewardstart=(String)map.get("rewardstart")==null?"":(String)map.get("rewardstart");//减刑所用奖励开始日期
//						String rewardend=(String)map.get("rewardend")==null?"":(String)map.get("rewardend");//减刑所用奖励截止日期
//			//			String rewardinfoandpunishinfo=(String)map.get("rewardinfoandpunishinfo")==null?"":(String)map.get("rewardinfoandpunishinfo");//服刑表现
//						String detainprison=(String)map.get("detainprison")==null?"":(String)map.get("detainprison");//收押监狱
//						
//						//获取表单复选框的值
//						String checkboxslzf=(String)map.get("CheckBoxslzf")==null?"":(String)map.get("CheckBoxslzf");//三类罪犯
//						String checkboxcyqj=(String)map.get("CheckBoxcyqj")==null?"":(String)map.get("CheckBoxcyqj");//从严情节
//						String checkboxqtqj=(String)map.get("CheckBoxqtqj")==null?"":(String)map.get("CheckBoxqtqj");//其他情节
//						String checkboxckqj=(String)map.get("CheckBoxckqj")==null?"":(String)map.get("CheckBoxckqj");//从宽情节
//						
//						//减刑年月日
//						String jxjs_2=map.get("jxjs_2").equals("")||map.get("jxjs_2")==null?"0":(String)map.get("jxjs_2");//减刑年
//						String jxjs_3=map.get("jxjs_3").equals("")||map.get("jxjs_3")==null?"0":(String)map.get("jxjs_3");//减刑月
//						String jxjs_4=map.get("jxjs_4").equals("")||map.get("jxjs_4")==null?"0":(String)map.get("jxjs_4");//减刑天
//						
//						flowBase.setInt2(Integer.parseInt(jxjs_2));
//						flowBase.setInt3(Integer.parseInt(jxjs_3));
//						flowBase.setInt4(Integer.parseInt(jxjs_4));
//			//			alteration.setPunishmentyear(jxjs_2);//现行期取这 这里不能更新
//			//			alteration.setPunishmentmonth(jxjs_3);
//			//			alteration.setPunishmentday(jxjs_4);
//						String jxjs_8=map.get("jxjs_8").equals("")||map.get("jxjs_8")==null?"0":(String)map.get("jxjs_8");//理由
//						TbcourtFlowBaseInfo tfbi = new TbcourtFlowBaseInfo();
//						tfbi.setFlowdraftid(flowdraftid);
//						tfbi.setLiyou(jxjs_8);
//						tbcourtFlowBaseInfoMapper.updateCourtFlowBaseInfoById(tfbi);
//						
//						String heyiriqi = flowBase.getText15();
//						if(heyiriqi!=null){
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//							try {
//								Date heyiDate = sdf.parse(heyiriqi);
//								alteration.setCourtchangefrom(heyiDate);//合议日期即刑期起日
//								//下面计算刑期止日
//								Calendar c1 = Calendar.getInstance();
//								c1.setTime(heyiDate);
//								if(flowBase.getInt2()!=null && flowBase.getInt2()<1000){
//									c1.add(Calendar.YEAR, flowBase.getInt2());
//									c1.add(Calendar.MONDAY, flowBase.getInt3());
//									c1.add(Calendar.DATE, flowBase.getInt4());
//									c1.add(Calendar.DAY_OF_MONTH, -1);//表述是含头含尾的，所以减一天。
//									java.util.Date changeto = c1.getTime();
//									alteration.setCourtchangeto(changeto);
//								}
//							} catch (ParseException e) {
//								e.printStackTrace();
//							}
//						}
//						//三类犯
//						basecrime.setPostcrime("0");
//						basecrime.setUndermine("0");
//						basecrime.setUnderworld("0");
//						if(!"".equals(checkboxslzf)){
//							String []str;
//							str=checkboxslzf.split(";");
//							for(int ii=0;ii<str.length;ii++){
//								if(GkzxCommon.ZWFZ.equals(str[ii])){
//									basecrime.setPostcrime("1");
//								}else if(GkzxCommon.JRFZ.equals(str[ii])){
//									basecrime.setUndermine("1");
//								}else if(GkzxCommon.SHFZ.equals(str[ii])){
//									basecrime.setUnderworld("1");
//								}
//							}
//						}
//						//从严情节
//						basecrime.setIsrecidivism("0");
//						basecrime.setRedrugs("0");
//						basecrime.setCrimes("0");
//						basecrime.setOverfourcrimes("0");
//						basecrime.setSeriousviolations("0");
//						basecrime.setCombinedpunishment("0");
//						basecrime.setMajorcriminal("0");
//						basecrime.setStrictlyplot("0");
//						if(!"".equals(checkboxcyqj)){
//							String []str;
//							str=checkboxcyqj.split(";");
//							for(int ii=0;ii<str.length;ii++){
//								if(GkzxCommon.LEIF.equals(str[ii])){
//									basecrime.setIsrecidivism("1");
//								}else if(GkzxCommon.DPZF.equals(str[ii])){
//									basecrime.setRedrugs("1");
//								}else if(GkzxCommon.DCFZ.equals(str[ii])){
//									basecrime.setCrimes("1");
//								}else if(GkzxCommon.FSZYS.equals(str[ii])){
//									basecrime.setOverfourcrimes("1");
//								}else if(GkzxCommon.YZWJ.equals(str[ii])){
//									basecrime.setSeriousviolations("1");
//								}else if(GkzxCommon.SZBFWNYS.equals(str[ii])){
//									basecrime.setCombinedpunishment("1");
//								}else if(GkzxCommon.ZDXSF.equals(str[ii])){
//									basecrime.setMajorcriminal("1");
//								}else if(GkzxCommon.CYQT.equals(str[ii])){
//									basecrime.setStrictlyplot("1");
//								}
//							}
//						}
//						//从宽情节
//						basecrime.setIsminor("0");
//						basecrime.setOldprisoner("0");
//						basecrime.setIllprisoner("0");
//						basecrime.setDeformityprisoner("0");
//						basecrime.setWideplots("0");
//						if(!"".equals(checkboxckqj)){
//							String []str;
//							str=checkboxckqj.split(";");
//							for(int ii=0;ii<str.length;ii++){
//								if(GkzxCommon.WCNF.equals(str[ii])){
//									basecrime.setIsminor("1");
//								}else if(GkzxCommon.LAOF.equals(str[ii])){
//									basecrime.setOldprisoner("1");
//								}else if(GkzxCommon.BF.equals(str[ii])){
//									basecrime.setIllprisoner("1");
//								}else if(GkzxCommon.CF.equals(str[ii])){
//									basecrime.setDeformityprisoner("1");
//								}else if(GkzxCommon.CKQT.equals(str[ii])){
//									basecrime.setWideplots("1");
//								}
//							}
//						}
//						//其他情节
//						basecrime.setFocus("0");
//						basecrime.setMediaattention("0");
//						basecrime.setTakedrugs("0");
//						basecrime.setSuicide("0");
//						basecrime.setEscapes("0");
//						basecrime.setAssaultepolice("0");
//						basecrime.setDeclarationproperty("0");
//						if(!"".equals(checkboxqtqj)){
//							String []str;
//							str=checkboxqtqj.split(";");
//							for(int ii=0;ii<str.length;ii++){
//								if(GkzxCommon.ZDGZ.equals(str[ii])){
//									basecrime.setFocus("1");
//								}else if(GkzxCommon.MTGZ.equals(str[ii])){
//									basecrime.setMediaattention("1");
//								}else if(GkzxCommon.XDS.equals(str[ii])){
//									basecrime.setTakedrugs("1");
//								}else if(GkzxCommon.ZSS.equals(str[ii])){
//									basecrime.setSuicide("1");
//								}else if(GkzxCommon.TTS.endsWith(str[ii])){
//									basecrime.setEscapes("1");
//								}else if(GkzxCommon.XJS.equals(str[ii])){
//									basecrime.setAssaultepolice("1");
//								}else if(GkzxCommon.SBCC.equals(str[ii])){
//									basecrime.setDeclarationproperty("1");
//								}
//							}
//						}
//						SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
//						SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
//						
//						baseinfo.setCrimid(crimid);
//						baseinfo.setName(name);
//						baseinfo.setGender(gender);
//						baseinfo.setUsedname(usedname);
//						try {
//							if(!"".equals(cbibirthday))
//							baseinfo.setBirthday(sdf.parse(cbibirthday));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						baseinfo.setNation(nation);
//						baseinfo.setGrade(grade);
//						baseinfo.setDuty(duty);
//						baseinfo.setEducation(education);
//						baseinfo.setOrigin(origin);
//						baseinfo.setCountryarea(guojia);
//						baseinfo.setAddressdetail(addressdetail);
//						baseinfo.setRegisteraddressdetail(registeraddressdetail);
//						
//						basecrime.setCrimid(crimid);
//						basecrime.setDetainprison(detainprison);
//						basecrime.setArrestauthority(arrestauthority);
//						try {
//							if(!"".equals(arrestdate))
//							basecrime.setArrestdate(sdf.parse(arrestdate));
//							if(!"".equals(detaindate))
//								basecrime.setDetaindate(sdf.parse(detaindate));
//							if(!"".equals(inprisondate))
//								basecrime.setInprisondate(sdf.parse(inprisondate));
//							if(!"".equals(judgedate))
//								basecrime.setJudgedate(sdf.parse(judgedate));
//							if(!"".equals(executiondate))
//								basecrime.setExecutiondate(sdf.parse(executiondate));
//							if(!"".equals(executiondate))
//								basecrime.setSentenceetime(sdf.parse(sentenceetime));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						basecrime.setJudgmentname(judgmentname);
//			//			basecrime.setCaseno(caseno);
//						basecrime.setMaincase(maincase);
//						basecrime.setOthercase(othercase);
//						basecrime.setPunishmenttype(punishmenttype);
//						//刑期和剥夺年限仍未保存
//						basecrime.setFirsttrialsum(firsttrialsum);//法院判裁经过
//						basecrime.setCrimeface(crimeface);//主要犯罪事实
//						if(StringNumberUtil.notEmpty(guojia) && !"中国".equals(guojia)){//外国国籍
//							basecrime.setIsforeign("1");
//						}else{
//							basecrime.setIsforeign("0");
//						}
//						
//						alteration.setCrimid(crimid);
//						alteration.setName(name);
//						alteration.setGender(gender);
//						try {
//							if(!"".equals(cbibirthday))
//								alteration.setBrithday(sdf.parse(cbibirthday));
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						alteration.setAdministration(administration);
//						alteration.setProsecutor(prosecutor);
//						if(!"".equals(jailname))
//						alteration.setJailname(jailname);
//						if(!"".equals(pay))
//						alteration.setPay(Double.parseDouble(pay));
//						if(!"".equals(overplus))
//						alteration.setOverplus(Double.parseDouble(overplus));
//						alteration.setJailinfo(jailinfo);
//			//			alteration.setPunishinfo(rewardinfoandpunishinfo);//服刑表现 
//						try {
//							if(!"".equals(rewardstart))
//								alteration.setRewardstart(sdf.parse(rewardstart));
//							if(!"".equals(rewardend))
//								alteration.setRewardend(sdf.parse(rewardend));
//							if(!"".equals(delivery))
//								alteration.setDelivery(sdf.parse(delivery));
//							if(!"".equals(fristsubmit))
//								alteration.setFristsubmit(sdf.parse(fristsubmit));
//							if(!"".equals(commutationdate))
//								if(commutationdate.contains("年"))
//									alteration.setCommutationdate(sdf.parse(commutationdate));
//								else
//									alteration.setCommutationdate(sdf2.parse(commutationdate));
//							if(!"".equals(predate)){
//								alteration.setPredate(sdf.parse(predate));
//							}
//							if(!"".equals(thisdate)){
//								alteration.setThisdate(sdf.parse(thisdate));
//							}
//							if(noperiod!=null&&!"".equals(noperiod)){//无期起算日
//								alteration.setNoperiod(sdf.parse(noperiod));
//							}else{
//								alteration.setNoperiod(null);
//							}
//							if(stayexecution!=null&&!"".equals(stayexecution)){//死缓起算日
//								Date stayexecutionDate = sdf.parse(stayexecution);
//								alteration.setStayexecution(stayexecutionDate);
//								if(lastpunishmentyear!=null){//死缓起算日不为空时。计算无期起算日
//									Calendar c2 = Calendar.getInstance();
//									c2.setTime(stayexecutionDate);
//									c2.add(Calendar.YEAR, 2);
//									alteration.setNoperiod(c2.getTime());
//								}
//							}else{
//								alteration.setStayexecution(null);
//							}
//							if(exnoperiod!=null&&!"".equals(exnoperiod)){
//								alteration.setExnoperiod(exnoperiod);
//							}else{
//								alteration.setExnoperiod(null);
//							}
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//						
//						//alteration.setIntervalyear(intervalyear);
//						if(!"".equals(fine))
//						alteration.setFine(Double.parseDouble(fine));
//						if(!"".equals(sumfine))
//						alteration.setSumfine(Double.parseDouble(sumfine));
//						if(!"".equals(thisfine))
//						alteration.setThisfine(Double.parseDouble(thisfine));
//						if(!"".equals(recovered))
//						alteration.setRecovered(Double.parseDouble(recovered));
//						if(!"".equals(sumrecovered))
//						alteration.setSumrecovered(Double.parseDouble(sumrecovered));
//						if(!"".equals(thisrecovered))
//						alteration.setThisrecovered(Double.parseDouble(thisrecovered));
//						if(!"".equals(expropriation))
//						alteration.setExpropriation(Double.parseDouble(expropriation));
//						alteration.setSentencechageinfo(sentencechageinfo);
//						if(!"".equals(islimit)&& islimit!= null){
//							if("否".equals(islimit)){
//								alteration.setIslimit((short)0);
//							}else if("是".equals(islimit)){
//								alteration.setIslimit((short)1);
//							}
//						}
//						//alteration.setNowpunishmentyear(nowpunishmentyear);
//						if(!"".equals(sumexpropriation))
//						alteration.setSumexpropriation(Double.parseDouble(sumexpropriation));
//						if(!"".equals(thisexpropriation))
//						alteration.setThisexpropriation(Double.parseDouble(thisexpropriation));
//						if(!"".equals(civil))
//						alteration.setCivil(Double.parseDouble(civil));
//						if(!"".equals(sumcivil))
//						alteration.setSumcivil(Double.parseDouble(sumcivil));
//						if(!"".equals(thiscivil))
//						alteration.setThiscivil(Double.parseDouble(thiscivil));
//						if(!"".equals(income))
//						alteration.setIncome(Double.parseDouble(income));
//						
//						prisonerService.insertOrUpdateCourtFlowInfo(request,map);
//					}//--
//				}
//				tbprisonerbaseinfomapper.updateByPrimaryKeySelective(baseinfo);
//				tbprisonerbasecrimemapper.updateByPrimaryKeySelective(basecrime);
//				tbxfsentencealterationmapper.updateByPrimaryKeySelective(alteration);
//				flowBaseService.update(flowBase);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	/**
	 * 方法描述：判裁信息查看修改
	 * @version 2014年11月10日17:29:54
	 */
	@RequestMapping(value="goPanCaiInfo")
	public ModelAndView goPanCaiInfo(){
		return new ModelAndView("/chooseCriminal/goPanCaiInfo");
	}
    /**
     * 方法描述：查询 当前单位 罪犯列表
     * @author 
     * @version 2014年11月10日17:43:39
     */
	@RequestMapping(value="/queryPanCaiInfo")
	@ResponseBody
	@SuppressWarnings("all")
	public Object queryPanCaiInfo(HttpServletRequest request){
		List<Map> list = new ArrayList<Map>();
		SystemUser user = getLoginUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//获取数据
			list = prisonerService.queryPanCaiInfoService(request, user);
			list = MapUtil.turnKeyToLowerCaseOfList(list);
			int count = prisonerService.queryPanCaiInfoCounts(request, user);
			map.put("data", list);
			map.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 方法描述：减刑假释呈批表（查询呈批表内容在页面显示）
	 * 查询本监区办理中的所有的罪犯信息
	 * @author mushuhong
	 * @version:2015年1月7日09:35:42
	 */
	@RequestMapping(value="/queryChengPiBiaoContent")
	@ResponseBody
	public Object queryChengPiBiaoContent(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Object object = prisonerService.queryChengPiBiaoContent(request, user);
	    return object;
	}
	/**
	 * 方法描述：减刑假释呈批表（查询呈批表内容在页面显示）
	 * 查询本监区办理中的所有的罪犯信息
	 * @author mushuhong
	 * @version:2015年1月7日09:35:42
	 */
	@RequestMapping(value="/queryChengPiBiaoContent_yzk")
	@ResponseBody
	public Object queryChengPiBiaoContent_yzk(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Object object = prisonerService.queryChengPiBiaoContent_yzk(request, user);
	    return object;
	}
	/**
	 * 方法描述：选中单个罪犯 获取罪犯相对应的信息
	 * @author mushuhong
	 * @version 2015年1月8日08:54:48
	 */
	@RequestMapping(value="/getCriminalCPBInfo")
	@ResponseBody
	public Object getCriminalCPBInfo(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Object object = prisonerService.getCriminalCPBInfo(request,user);
		return object;
	}
	/**
	 * 方法描述：保存减刑假释呈批表的内容
	 * @author mushuhong
	 * @version 2015年1月8日12:48:54
	 */
	@RequestMapping(value="/saveJxJsCPBContent")
	@ResponseBody
	public String saveJxJsCPBContent(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String value = prisonerService.saveJxJsCPBContent(request, user);
		return value;
	}
	/**
	 * 方法描述：批量提交 呈批表内容
	 * @author mushuhong
	 * @version 2015年1月9日14:53:11
	 */
	@RequestMapping(value="/submitJxJsCPBCaseInfo")
	@ResponseBody
	public String submitJxJsCPBCaseInfo(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String value = prisonerService.submitJxJsCPBCaseInfo(request,user);
		return value;
	}
	/**
	 * 方法描述：转到判决详细信息页面
	 * @version 2014年11月11日11:17:22
	 */
	@RequestMapping(value="/goDanGePanCaiInfoView")
	public ModelAndView goDanGePanCaiInfoView(HttpServletRequest request){
		
		String crimid = request.getParameter("crimid");
		
		String criname = request.getParameter("name");
		try {
			criname = URLDecoder.decode(criname, "utf-8");
			
			request.setAttribute("crimid", crimid);
			request.setAttribute("criname", criname);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("/chooseCriminal/goDanGePanCaiInfo");
		return view;
	}
	
	/**
	 * 方法描述：查询详细信息内容
	 * @version 2014年11月11日11:37:19
	 */
	@RequestMapping(value="/queryTbdataSentchage")
	@ResponseBody
	public Object queryTbdataSentchage(HttpServletRequest request){
		
		//data 
		Map<String,Object> listMaps = new HashMap<String,Object>();
		
		listMaps = prisonerService.queryTbdataSentchageImpl(request);
		return listMaps;
	}
	/**
	 * 方法描述：保存对应的信息
	 * @version 2014年11月11日13:54:07
	 */
	@RequestMapping(value="/saveTbdataSentchageInfo")
	public ModelAndView saveTbdataSentchageInfo(HttpServletRequest request){
		
		String crimid = request.getParameter("crimid");
		String state = request.getParameter("state");
		String criname = request.getParameter("criname");
		String category = request.getParameter("category");
		String courtsanction = request.getParameter("courtsanction");
		try {
			criname = URLDecoder.decode(criname, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("/chooseCriminal/newOrEditTbDataSenchange");
		
		request.setAttribute("crimid", crimid);
		request.setAttribute("state", state);
		request.setAttribute("criname", criname);
		request.setAttribute("category", category);
		request.setAttribute("courtsanction", courtsanction);
		return view;
	}
	/**
	 * 方法描述：保存数据
	 * @version:2014年11月11日17:11:57
	 */
	@RequestMapping(value="/saveChangeData")
	@ResponseBody
	public String saveChangeData(HttpServletRequest request){
		Object count = "0";
		try {
			count = prisonerService.saveChangeData(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count.toString();
	}
	/**
	 * 方法描述：判断一审和二审对应的是否已经有数据
	 * @author 
	 * @version
	 */
	@RequestMapping(value = "/judgeIsExistYiShenAndErShen")
	@ResponseBody
	public int judgeIsExistYiShenAndErShen(HttpServletRequest request){
		Object count = "0";
		try {
			List<Map> list = this.prisonerService.judgeIsExistYiShenAndErShen(request);
			if (list.size() > 0) {
				count = ""+list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(count.toString());
	}
	/**
	 * 方法描述：获取省市县 的内容
	 * @author 
	 * @version 2014年11月12日13:11:18
	 */
	@RequestMapping(value="/getCodeNameByCodeType")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getCodeNameByCodeType(HttpServletRequest request){
		List<Map> list = new ArrayList<Map>();
		try {
			list = (List<Map>)prisonerService.getCodeNameByCodeTypeService(request);
			list = MapUtil.turnKeyToLowerCaseOfList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 方法描述：修改数据
	 * @version 2014年11月13日13:37:43
	 */
	@RequestMapping(value="/queryChangeInfo")
	@ResponseBody
	@SuppressWarnings("all")
	public Object queryChangeInfo(HttpServletRequest request){
		Map map = this.prisonerService.queryChangeInfo(request);
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			map.put(key,map.get(key));
			
		}
		return map;
	}
	/**
	 * 方法描述：删除对应的 裁定信息
	 * @version 2014年11月15日10:54:00
	 * 
	 */
	@RequestMapping(value="/deleteCase")
	public int deleteCase(HttpServletRequest request){
//		int count = this.prisonerService.deleteCaseService(request);
		return 0;
	}
	
	@RequestMapping(value="/addNewCrimidPage.action")
	public ModelAndView getAddNewCrimidPage(HttpServletRequest request){
		
		request.setAttribute("flowdefid", "doc_zfrjdjsp");
		request.setAttribute("tempid", "SDXF_BASE_RJDJB");
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/addBasicInformation");
	}
	
	
}
