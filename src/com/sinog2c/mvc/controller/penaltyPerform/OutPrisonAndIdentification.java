package com.sinog2c.mvc.controller.penaltyPerform;

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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 出监鉴定
 * @author hzl
 *
 */
@Controller
public class OutPrisonAndIdentification extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	/*
	 * 方法描述：跳转到罪犯处理页面
	 */
	@RequestMapping(value = "/toOutPrisonAndIdentificationChoose")
	public ModelAndView toOutPrisonAndIdentificationChoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/chooseCriminal/outPrisonAndIdentificationChoose.jsp"));
	}
	/*
	 * 方法描述：获取罪犯处理数据列表
	 */
	@RequestMapping(value = "/getOutPrisonAndIdentificationChoose")
	@ResponseBody
	public Object getOutPrisonAndIdentificationChoose(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			//出监鉴定加查询条件
			String condition = prisonerService.getTheQueryCondition("10211");
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("condition", condition);
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("userid", getLoginUser(request).getUserid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("userid",getLoginUser(request).getUserid());//给map传userid为了mapper中id为findWithFlow使用
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "other_zfcjjdsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	/*
	 * 方法描述：跳转到表单页面，并显示数据
	 */
	@RequestMapping("/showOutPrisonAndIdentification")
	public ModelAndView showOutPrisonAndIdentification(HttpServletRequest request){
		try{
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
			returnResourceMap(request);
			String tempid = "XFZX_CJQD";//出监鉴定表
			request.setAttribute("tempid", tempid);
			JSONArray docconent = new JSONArray();
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
			if(idnumber==null||"".equals(idnumber)){
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				HashMap<String, Object> map = new HashMap<String, Object>();
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				Map<String, Object>  tbMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				if(info!=null){
					map.put("cbiname", info.getName());
					map.put("text2", info.getName());
					map.put("cbitruename", info.getUsedname());
					map.put("cbigendername", info.getGender());
					map.put("cbinationname", info.getNation());
					map.put("cbibirthday", DateUtil.dateFormatForAip(info.getBirthday()));
					map.put("cbihomeaddress", info.getFamilyaddress());
					map.put("cbiresidenceaddress", info.getRegisteraddressdetail());
					map.put("caieducation", info.getEducation());
					map.put("cbispeciality", info.getSpecialist());
				}
				// 家庭成员、主要社会关系
	            List<TbprisonerSocialRelation> relationList = prisonerService.findRelationBycrimid(crimid);
	            StringBuffer relation = new StringBuffer();
	            if (!relationList.isEmpty() && relationList != null) {
	                for (int i = 0; i < relationList.size(); i++) {
	                    TbprisonerSocialRelation tsr = relationList.get(i);
	                    relation.append("姓名："+tsr.getName());
	                    relation.append("，关系：");
	                    if(StringNumberUtil.notEmpty(tsr.getRelationship())){
	                    	relation.append(tsr.getRelationship());
	                    }else{
	                    	relation.append("不详");
	                    }
	                    if(StringNumberUtil.notEmpty(tsr.getVocation())){
	                    	relation.append("，职业："+tsr.getVocation());
	                    }
	                    if(StringNumberUtil.notEmpty(tsr.getPolitical())){
	                    	relation.append("，政治面貌："+tsr.getPolitical());
	                    }
	                    if(i < relationList.size()-1){
	                    	relation.append(";");
	                    }
	                }
	                relation = relation.append("。");
	            }
	            map.put("relation", relation);
	            //个人简历
	            List<TbprisonerResume> resumeList = prisonerService.findByCrimidResume(crimid);
	    		String resume = "";
	    		if (!resumeList.isEmpty() && resumeList != null) {
	    			for (int i = 0; i < resumeList.size(); i++) {
	    				TbprisonerResume tr = resumeList.get(i);
	    				resume += "从"+tr.getBegindate()+"起，";
	    				resume += "至"+tr.getEnddate()+"止，";
	    				resume += "所在单位："+tr.getOrgname()+"，";
	    				resume += "职业："+tr.getVocation()+"。";
	    				resume +="\\r\\n";
	    			}
	    		}
	    		map.put("resume", resume);
				if(tbprisonerBaseCrime!=null){
					map.put("anyouhuizong", tbprisonerBaseCrime.getMaincase());
					map.put("cjicourtname", tbprisonerBaseCrime.getJudgmentname());
					map.put("cjicourtnumber", tbprisonerBaseCrime.getCaseno());//判决书号
					map.put("zhuxing", tbprisonerBaseCrime.getRemark());
					map.put("fujiaxing", tbprisonerBaseCrime.getSanremark());
					map.put("cjibegindate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime()));
					map.put("cjienddate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));
					map.put("fanzuishishi", tbprisonerBaseCrime.getCrimeface());
					request.setAttribute("orgname1",tbprisonerBaseCrime.getOrgid1());
				}
				if(tbMap!=null){
					map.put("prisonterm", tbMap.get("SENTENCECHAGEINFO"));
					if(tbMap.get("REWARDINFO")==null){
						if(tbMap.get("PUNISHINFO")==null){
							map.put("cisrewardcircs","");
						}else{
							map.put("cisrewardcircs","惩罚情况:"+tbMap.get("PUNISHINFO"));
						}
					}else{
						if(tbMap.get("PUNISHINFO")==null){
							map.put("cisrewardcircs","奖励情况:"+tbMap.get("REWARDINFO"));
						}else{
							map.put("cisrewardcircs","奖励情况:"+tbMap.get("REWARDINFO")+";惩罚情况:"+tbMap.get("PUNISHINFO"));
						}
					}
				}
				map.put("gaizaobiaoxian", "");
				
				String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
				
				map.put("departid", jailname);
				map.put("text1", DateUtil.dateFormatForAip(new Date()));
				Map<String, Object> selectmap = new HashMap<String, Object>();
				selectmap.put("xianeducation", systemCodeService.getcodeValue("GB007"));
				request.setAttribute("formDatajson", new JSONObject(map).toString());
				request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
			}else{
				String baseDoc= flowBaseService.getDocConentByFlowdraftId(idnumber);
				if (baseDoc != null) {
					docconent.add(baseDoc);
				}
			}
			request.setAttribute("flowdefid", "other_zfcjjdsp");
			request.setAttribute("flowdraftid", idnumber);
			request.setAttribute("formcontent", docconent.toString());
			request.setAttribute("applyname",info.getName());
			request.setAttribute("applyid",crimid);
			request.setAttribute("orgid",tbprisonerBaseCrime.getOrgid1());
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/outPrisonAndIdentificationAdd.jsp"));
	}
}
