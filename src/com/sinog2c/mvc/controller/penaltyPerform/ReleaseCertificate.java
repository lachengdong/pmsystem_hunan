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
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 *释放证明书
 * 2014-7-18
 */
@Controller
public class ReleaseCertificate extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;

	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	
	
	/**
	 * 方法描述：跳转到释放证明书罪犯选择页面
	 * 
	 * @author liuchaoyang 2014-7-26 18:52:45
	 */
	@RequestMapping("/releaseCertificate")
	public ModelAndView releaseCertificate(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/releaseCertificateChoose");
	}
	/**
	 * 方法描述：跳转到释放证明书表单页面
	 * 
	 * @author liuchaoyang 2014-7-26 18:52:45
	 */
	@RequestMapping("/disposeReleaseCertificate")
	public ModelAndView disposeReleaseCertificate(HttpServletRequest request){
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
		request.setAttribute("crimid", crimid);
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		TbxfSentenceAlteration tbxfSentenceAlteration = tbxfSentencealterationService.selectByPrimaryKey(crimid);
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			String tempid = "XFZX_SFZMS";
			request.setAttribute("tempid", tempid);
			SystemUser user = getLoginUser(request);
			String departid=user.getDepartid();//根据用户Id获取所在部门Id
			HashMap<String, Object> map = new HashMap<String, Object>();
			String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			String xuhao = flowBaseService.getFlowXuHao(year, "doc_zfsfzmsp", null, user.getDepartid());//获取序号
			map.put("year",year);
			map.put("xuhao", xuhao);
			map.put("crcitransactor", user.getName());
			map.put("crciinkdate", DateUtil.dateFormatForAip(new Date()));
			map.put("text30", DateUtil.dateFormatForAip(new Date()));
			map.put("text31", DateUtil.dateFormatForAip(new Date()));
			map.put("text32", DateUtil.dateFormatForAip(new Date()));
			map.put("departname", user.getPrison().getShortname());
			map.put("cbiname", info.getName());
			map.put("cbigendername", info.getGender());       
			map.put("cbibirthday", DateUtil.dateFormatForAip(info.getBirthday()));
			String str1=info.getBirtharea()==null?"" : info.getBirtharea();
			String str2=info.getRegisteraddress()==null?"":info.getRegisteraddress();
			map.put("cbiresidenceaddress", str1+str2);
			if(crime!=null){
				map.put("cjicourtname",crime.getJudgmentname());
				map.put("anyouhuizong", crime.getMaincase());
				map.put("cjiimprisontypename",crime.getPunishmenttype());
				map.put("xingqi",crime.getRemark());
				map.put("cjibegindate", DateUtil.dateFormatForAip(crime.getSentencestime()));
				map.put("cjienddate", DateUtil.dateFormatForAip(crime.getSentenceetime()));
				String fujiaxing = crime.getLosepoweryear()+","+crime.getLosepowermonth()+","+crime.getLosepowereday();
				fujiaxing = StringNumberUtil.getBoquan(fujiaxing);
				if(!StringNumberUtil.isNullOrEmpty(fujiaxing)){
					fujiaxing = GkzxCommon.LOSEPOWER_BDZZ+fujiaxing;
				}
				String forfeit = crime.getForfeit();
				if(!StringNumberUtil.isNullOrEmpty(forfeit)&&!"0".equals(forfeit)){
					fujiaxing += GkzxCommon.FAJIN+forfeit;
				}
				String xingqi = crime.getPunishmentyear()+","+crime.getPunishmentmonth()+","+crime.getPunishmentday();
				map.put("zhuxing", StringNumberUtil.getXingqi(crime.getPunishmenttype(), xingqi));
				map.put("fujiaxing", fujiaxing);
				map.put("pancairiqi", DateUtil.dateFormatForAip(crime.getJudgedate()));
				map.put("text6262", DateUtil.dateFormatForAip(crime.getJudgedate()));
				map.put("ctpoppcoutprisonaddress1", DateUtil.dateFormatForAip(crime.getJudgedate()));
			}
			if(!StringNumberUtil.isNullOrEmpty(tbxfSentenceAlteration)) {
				String sql = "select GETDISOFTWODATE(crime.Inprisondate, sen.courtchangeto) as zxxq from tbprisoner_base_crime crime,TBXF_SENTENCEALTERATION sen where crime.crimid=sen.crimid and crime.crimid="+crimid;
				Map zxxq = tbxfSentencealterationService.selectTbxfMapBySql(sql);
				if(!StringNumberUtil.isNullOrEmpty(crime.getInprisondate())&&!StringNumberUtil.isNullOrEmpty(tbxfSentenceAlteration.getCourtchangeto()))
					map.put("ctpcoppsidentificationdate",   zxxq.get("zxxq"));
				map.put("prisonterm", tbxfSentenceAlteration.getSentencechageinfo());
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("flowdefid", "doc_zfsfzmsp");
		request.setAttribute("orgid", crime.getOrgid1());
		request.setAttribute("menuid", menuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname",info.getName());
		request.setAttribute("flowdraftid",idnumber);
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/releaseCertificate");
	}
	/**
	 * 获取释放证明书罪犯选择数据
	 * @author liuchaoyang
	 * 2014-7-29 15:37:45
	 */
	@RequestMapping(value = "/getReleaseCertificateList")
	@ResponseBody
	public Object getReleaseCertificateList(HttpServletRequest request,
			HttpServletResponse response){
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
	    	map.put("userid", getLoginUser(request).getUserid());//给map传userid为了mapper中id为findWithFlow使用
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_zfsfzmsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
}
