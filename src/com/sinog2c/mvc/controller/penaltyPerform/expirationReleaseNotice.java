package com.sinog2c.mvc.controller.penaltyPerform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 *刑满释放人员通知书
 * 2014-7-17
 */
@Controller
public class expirationReleaseNotice extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	
	
	/**
	 * 方法描述：
	 * 跳转到刑满释放通知书罪犯选择页面
	 * @author liuchaoyang 2014-8-8 10:32:45
	 */
	@RequestMapping("/expirationReleaseNotice")
	public ModelAndView expirationReleaseNotices (HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/expirationReleaseNoticeChoose");
	}
	/**
	 * 方法描述：
	 * 获取刑满释放通知书罪犯列表
	 * @author liuchaoyang 2014-8-8 10:32:45
	 */
	@RequestMapping("/getExpirationReleaseNoticeList")
	@ResponseBody
	public Object getExpirationReleaseNoticeList(HttpServletRequest request,
			HttpServletResponse response){
		returnResourceMap(request);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("userid", getLoginUser(request).getUserid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_xmsfrytzssp");
	    	map.put("userid", getLoginUser(request).getUserid());//给map传userid为了mapper中id为findWithFlow使用
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
	/**
	 * 方法描述：
	 * 
	 * 根据表单Id到模板信息表查询大字段，查询出表单需要显示的数据，显示到新增页面 
	 * 
	 * @author liuchaoyang 2014-7-26 14:32:45
	 */
	@RequestMapping("/disposeExpirationReleaseNotice")
	public ModelAndView disposeExpirationReleaseNotice(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
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
		HashMap<String, Object> map = new HashMap<String, Object>();
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber!=null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			String tempid = "ZFZX_SFTZS";
			request.setAttribute("tempid", tempid);
			String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
			SystemUser user = getLoginUser(request);//获取当前登录的用户
			String departid=user.getDepartid();//根据用户Id获取所在部门Id
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			String xuhao = flowBaseService.getFlowXuHao(year, "doc_xmsfrytzssp", null, user.getDepartid());//获取序号
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
			TbxfSentenceAlteration tbxfSentenceAlteration = tbxfSentencealterationService.selectByPrimaryKey(crimid);
			map.put("year",year);
			map.put("xuhao", xuhao);
			map.put("text213", user.getName());
			map.put("text3", DateUtil.dateFormatForAip(new Date()));
			map.put("departname", org.getShortname());	
			map.put("text23", org.getName());
			map.put("text32", org.getAddress());
			map.put("text25", org.getPostcode());
			map.put("depname", org.getShortname());
			map.put("cbinativename", info.getCountryarea());
			map.put("cbiname", info.getName());
			map.put("gender", info.getGender());
			map.put("yuanfuji", info.getRegisteraddressdetail());
			
			map.put("birthday", DateUtil.dateFormatForAip(info.getBirthday()));
			if(crime!=null){
				map.put("anyouhuizong", crime.getMaincase());
			    map.put("text308", crime.getMaincase());
			    map.put("text286C", crime.getArrestauthority());
			    map.put("text29A10", crime.getArrestauthorityshort());
			    map.put("anyouhuizong", crime.getCauseaction());
			    map.put("judgmentname", crime.getJudgmentname());
			    map.put("inprisondate", DateUtil.dateFormatForAip(crime.getInprisondate()));
			    String fujiaxing = crime.getLosepoweryear()+","+crime.getLosepowermonth()+","+crime.getLosepowereday();
				fujiaxing = StringNumberUtil.getBoquan(fujiaxing);
				if(!StringNumberUtil.isNullOrEmpty(fujiaxing)){
					fujiaxing = GkzxCommon.LOSEPOWER_BDZZ+fujiaxing;
					map.put("text33", fujiaxing);
					map.put("text34", fujiaxing);
				}
				String forfeit = crime.getForfeit();
				if(!StringNumberUtil.isNullOrEmpty(forfeit)&&!"0".equals(forfeit)){
					fujiaxing += GkzxCommon.FAJIN+forfeit;
				}
				map.put("fujiaxing", fujiaxing);
				String xingqi = crime.getPunishmentyear()+","+crime.getPunishmentmonth()+","+crime.getPunishmentday();
				map.put("zhuxing", StringNumberUtil.getXingqi(crime.getPunishmenttype(), xingqi));
				
				map.put("xingqiqiri", DateUtil.dateFormatForAip(crime.getSentencestime()));
				map.put("xingqizhiri", DateUtil.dateFormatForAip(crime.getSentenceetime()));
			}
			if(!StringNumberUtil.isNullOrEmpty(tbxfSentenceAlteration)) {
				map.put("prisonterm", tbxfSentenceAlteration.getSentencechageinfo());
			}
		}
		request.setAttribute("orgid", crime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname",info.getName());
		request.setAttribute("flowdefid", "doc_xmsfrytzssp");
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("menuid", menuid);
		return new ModelAndView("aip/expirationReleaseNotice");
	}
	
}
