package com.sinog2c.mvc.controller.commutationParole.threeTypesOfCrime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;

/**
 * 三类犯认定
 * 
 * @author hzl
 * 
 */
@Controller
@RequestMapping("/threeOfCrimAffirm")
public class ThreeOfCrimAffirmController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	/**
	 * 方法描述：跳转到罪犯处理页面
	 */
	@RequestMapping("/toThreeOfCrimAffirmChoose.page")
	public ModelAndView toChooseCriminalPage(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		//根据单位的id进行判断
		MapUtil mapUtil = new MapUtil(); 
		Object object = mapUtil.initProperties(request);
		if (object.toString().contains(user.getDepartid())) {
			request.setAttribute("departid", user.getDepartid());
		}
		return new ModelAndView("commutationParole/threeTypesOfCrime/threeOfCrimAffirmChoose");
	}
	
	/**
	 * 方法描述：跳转到罪犯处理页面
	 * @version 2014年11月17日21:07:06 (山西阳泉)
	 */
	@RequestMapping("/toThreeOfCrimAffirmChoose_sx.page")
	public ModelAndView toThreeOfCrimAffirmChoose_sx(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		//根据单位的id进行判断
		MapUtil mapUtil = new MapUtil();
		Object object = mapUtil.initProperties(request);
		if (object.toString().contains(user.getDepartid())) {
			request.setAttribute("departid", user.getDepartid());
		}
		return new ModelAndView("commutationParole/threeTypesOfCrime/threeOfCrimAffirmChoose_sx");
	}
	/**
	 * 方法描述：跳转到 老病残审批列表页面
	 * @author mushuhong
	 * @version 2014年12月18日16:45:18
	 */
	@RequestMapping("/oldBingChanShenPiBiao.page")
	public ModelAndView oldBingChanShenPiBiao_nx(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		//根据单位的id进行判断
		MapUtil mapUtil = new MapUtil();
		Object object = mapUtil.initProperties(request);
		if (object.toString().contains(user.getDepartid())) {
			request.setAttribute("departid", user.getDepartid());
		}
		return new ModelAndView("commutationParole/threeTypesOfCrime/oldBingChanShenPiBiao");
	}
	/**
	 *  方法描述：获取罪犯列表
	 * 
	 */
	@RequestMapping("/getThreeOfCrimAffirmList.json")
	@ResponseBody
	public Object getThreeOfCrimAffirmList(HttpServletRequest request,
			HttpServletResponse response){
		returnResourceMap(request);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String orgId = getLoginUser(request).getOrgid();
			String userid=getLoginUser(request).getUserid();
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("userid", userid);
			map.put("departId", orgId);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			
	    	//根据map传参获取总条数
	    	int count = chooseCriminalService.countAllByCondition(map);
	    	map.put("flowdefid", "other_slzfrdsp");
	    	//根据map传参获取罪犯列表
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		
		return resultmap;
	}
	
	/**
	 * 方法描述：获取三类犯人的列表
	 * @version 2014年11月18日08:31:39
	 */
	@RequestMapping(value="/getThreeOfCrimAffirmList_sx.json")
	@ResponseBody
	public Object getThreeOfCrimAffirmList_sx(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		Map maps = this.chooseCriminalService.getThreeOfCrimAffirmList_sx(request,user);
		return maps;
	}
	
	
	/**
	 * 方法描述：认定三类罪犯以后 修改三类罪犯字段的状态
	 * @version 2014年11月17日20:30:01
	 */
	@RequestMapping(value="/threeCrimeAffirm")
	@ResponseBody
	public int threeCrimeAffirm(HttpServletRequest request){
		int count = this.chooseCriminalService.threeCrimeAffirmService(request);
		return count;
	}
	/**
	 * 选择罪犯后的三类犯认定表单
	 * 
	 */
	@RequestMapping("/toThreeOfCrimAffirmPage.page")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		try{
			String crimid = request.getParameter("crimid");
			String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程编号
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
			JSONArray docconent = new JSONArray();
			TbprisonerBaseinfo tbprisonerBaseinfo =prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime =prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			if(idnumber != null && !"".equals(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid",idnumber);
			}else{
				String tempid = "TJ_SLZFRDSPB";//天津三类犯
				request.setAttribute("tempid", tempid);
				HashMap<String, Object> map = new HashMap<String, Object>();
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
				if(tbprisonerBaseinfo!=null){
					map.put("criminalid",crimid);
					map.put("cbiname", tbprisonerBaseinfo.getName());
					map.put("cbigendername", tbprisonerBaseinfo.getGender());
					map.put("cbibirthday",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
					map.put("cbinationname", tbprisonerBaseinfo.getNation());
					map.put("caieducation", tbprisonerBaseinfo.getEducation());
				}
				if(org!=null){
					map.put("sddiscribe", org.getName());
				}
				if(tbprisonerBaseCrime!=null){
					map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
					map.put("cjiimprisontypename", tbprisonerBaseCrime.getPunishmenttype());
					String zhuxing = "";
					if(tbprisonerBaseCrime.getPunishmentyear()>0){
						zhuxing = zhuxing + tbprisonerBaseCrime.getPunishmentyear()+"年";
					}
					if(tbprisonerBaseCrime.getPunishmentmonth()>0){
						zhuxing = zhuxing + tbprisonerBaseCrime.getPunishmentmonth()+"个月";
					}
					if(tbprisonerBaseCrime.getPunishmentday()>0){
						zhuxing = zhuxing + tbprisonerBaseCrime.getPunishmentday()+"天";
					}
					if(tbprisonerBaseCrime.getPunishmentyear()==9995) {
						zhuxing = GkzxCommon.XINGQI_WUQI_ZH;
					}
					if(tbprisonerBaseCrime.getPunishmentyear()==9996) {
						zhuxing = GkzxCommon.XINGQI_SIHUAN_ZH;
					}
					map.put("zhuxing",zhuxing);
					map.put("cjibegindate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime()));
					map.put("cjienddate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));
					request.setAttribute("orgid",tbprisonerBaseCrime.getOrgid1());
				}
				request.setAttribute("tempid", tempid);
				request.setAttribute("crimid", crimid);
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}    
			
			Map<String, Object> selectmap = new HashMap<String, Object>();
			selectmap.put("crimclass", "[[JRZP]]金融诈骗||[[HSHXZ]]黑社会性质||[[ZWFZ]]职务犯罪||[[QT]]其他");
			request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
			request.setAttribute("applyid", crimid);
			request.setAttribute("flowdefid", "other_slzfrdsp");
			request.setAttribute("applyname",tbprisonerBaseinfo.getName());
			request.setAttribute("orgid", tbprisonerBaseCrime.getOrgid1());
			//资源对象
			returnResourceMap(request); 
			request.setAttribute("formcontent", docconent.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return new ModelAndView("commutationParole/threeTypesOfCrime/threeOfCrimAffirm");
	}
	
	/**
	 * 选择罪犯，进入老病残审批表页面
	 * @author mushuhong
	 * @version 2014年12月19日09:36:07
	 */
	@RequestMapping("/oldBingChanSPB_nx.page")
	public ModelAndView oldBingChanSPB_nx(HttpServletRequest request) {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String crimid = request.getParameter("crimid");
			String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程编号
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
			JSONArray docconent = new JSONArray();
			TbprisonerBaseinfo tbprisonerBaseinfo =prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime =prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			Map sentenceMap = prisonerService.selectSentenceById(crimid);
			if(idnumber != null && !"".equals(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid",idnumber);
			}else{
				String tempid = "JXJS_LBCSHB";//宁夏老病残
				request.setAttribute("tempid", tempid);
				HashMap<String, Object> map = new HashMap<String, Object>();
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
				//罪犯基本信息表
				if(tbprisonerBaseinfo!=null){
					map.put("criminalid",crimid);
					map.put("cbiname", tbprisonerBaseinfo.getName());
					map.put("cbigendername", tbprisonerBaseinfo.getGender());
					map.put("cbibirthday",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
					map.put("cbinationname", tbprisonerBaseinfo.getNation());
					map.put("caieducation", tbprisonerBaseinfo.getEducation());
					map.put("text2", tbprisonerBaseinfo.getCrimid());//罪犯编号
					map.put("text3", tbprisonerBaseinfo.getName());//罪犯姓名
					map.put("text4", tbprisonerBaseinfo.getGender());//性别
					map.put("text5", tbprisonerBaseinfo.getNation());//民族
					map.put("text6", DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));//出生日期
				}
				if(org!=null){
					map.put("sddiscribe", org.getName());
				}
				//罪处罚信息表
				if(tbprisonerBaseCrime!=null){
					map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
					map.put("cjiimprisontypename", tbprisonerBaseCrime.getPunishmenttype());
					map.put("text1", user.getAddress()+""+tbprisonerBaseCrime.getDetainprison()+""+GkzxCommon.NX_LBCSPB);//省份+单位名称+文书名称
					map.put("text7", tbprisonerBaseCrime.getMaincase());//罪名
					map.put("text8", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getInprisondate()));//入监日期
					map.put("text9", tbprisonerBaseCrime.getFirsttrialsum());//判裁信息
					map.put("text10", GkzxCommon.NX_ZI+format.format(tbprisonerBaseCrime.getSentencestime())+GkzxCommon.NX_QI+"\\r\\n"+GkzxCommon.NX_ZHIO+format.format(tbprisonerBaseCrime.getSentenceetime())+GkzxCommon.NX_ZHIT);//刑期起止日
					map.put("text11", tbprisonerBaseCrime.getSanremark());//附加刑=剥夺政治权利+罚金
					map.put("text13", "    "+tbprisonerBaseCrime.getCrimeface());//犯罪事实
					
					
					
					String zhuxing = "";
					if(tbprisonerBaseCrime.getPunishmentyear()>0){
						zhuxing = zhuxing + tbprisonerBaseCrime.getPunishmentyear()+"年";
					}
					if(tbprisonerBaseCrime.getPunishmentmonth()>0){
						zhuxing = zhuxing + tbprisonerBaseCrime.getPunishmentmonth()+"个月";
					}
					if(tbprisonerBaseCrime.getPunishmentday()>0){
						zhuxing = zhuxing + tbprisonerBaseCrime.getPunishmentday()+"天";
					}
					if(tbprisonerBaseCrime.getPunishmentyear()==9995) {
						zhuxing = GkzxCommon.XINGQI_WUQI_ZH;
					}
					if(tbprisonerBaseCrime.getPunishmentyear()==9996) {
						zhuxing = GkzxCommon.XINGQI_SIHUAN_ZH;
					}
					map.put("zhuxing",zhuxing);
					map.put("cjibegindate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime()));
					map.put("cjienddate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));
					request.setAttribute("orgid",tbprisonerBaseCrime.getOrgid1());
				}
				//刑期变动表
				if (sentenceMap != null) {
					map.put("text12", "    "+sentenceMap.get("SENTENCECHAGEINFO")==null?"":sentenceMap.get("SENTENCECHAGEINFO"));//刑期变动
					
				}
				request.setAttribute("tempid", tempid);
				request.setAttribute("crimid", crimid);
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}    
			
			Map<String, Object> selectmap = new HashMap<String, Object>();
			selectmap.put("crimclass", "[[JRZP]]金融诈骗||[[HSHXZ]]黑社会性质||[[ZWFZ]]职务犯罪||[[QT]]其他");
			request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
			request.setAttribute("applyid", crimid);
			request.setAttribute("flowdefid", "other_slzfrdsp");
			request.setAttribute("applyname",tbprisonerBaseinfo.getName());
			request.setAttribute("orgid", tbprisonerBaseCrime.getOrgid1());
			//资源对象
			returnResourceMap(request); 
			request.setAttribute("formcontent", docconent.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return new ModelAndView("commutationParole/threeTypesOfCrime/oldOfCrimAffirm");
	}
	
	/**
	 * 方法描述：打开三类罪犯 认定说明
	 * @version 2014年11月25日14:21:59
	 */
	@RequestMapping(value="threeClassCriminalShuoMing")
	@SuppressWarnings("all")
	public ModelAndView threeClassCriminalShuoMing(HttpServletRequest request){
		
		try {
			String tempid = request.getParameter("tempid");
			SystemUser user = getLoginUser(request);
			String departid = user.getDepartid();
			Map map = new HashMap();
			map.put("tempid", tempid);
			map.put("departid", departid);
			Map map2 = systemModelService.threeClassCriminalShuoMing(map);
			if (map2.size() > 0) {
				JSONArray docconent = new JSONArray();
				docconent.add(map2.get("CONTENT"));
				//Clob clob =(Clob) map2.get("CONTENT");
				//String content = clob.getSubString(1,(int)clob.length());
				request.setAttribute("formcontent", docconent.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ModelAndView("aip/threeClassCriminalPage_sx");
	}
		
}
