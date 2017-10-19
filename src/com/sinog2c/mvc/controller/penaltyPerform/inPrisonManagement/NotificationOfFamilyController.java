package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 *家属通知
 * 2014-7-17
 */
@Controller
@RequestMapping(value = "/notificationOfFamily")
public class NotificationOfFamilyController extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PrisonerService prisonerService;
	
	
	/**
	 * 跳转到通知家属的罪犯选择页面
	 * @author liuchaoyang
	 * 2014-7-30 20:37:45
	 * @return 
	 */
	@RequestMapping(value = "/toNotificationOfFamily.page")
	public ModelAndView notificationOfFamily(HttpServletRequest request) {
		//资源对象
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/notificationOfFamilyChoose");
	}
	/**
	 * 获取罪犯列表
	 * @author liuchaoyang
	 * 2014-7-30 20:37:45
	 */
	@RequestMapping(value = "/getNotificationOfFamilyList")
	@ResponseBody
	public Object getNotificationOfFamilyList(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);                     
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("userid", getLoginUser(request).getUserid());//给map传userid为了mapper中id为findWithFlow使用
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_tzjssp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);	
		}catch (Exception e) {
		}
		return resultmap;
	}
	/**
	 * 方法描述：
	 * 如果流程编号不为空，则根据流程编号idnumber查询大字段，
	 * 根据表单Id到模板信息表查询大字段，根据罪犯编号查询出表单需要显示的数据，返回到表单显示页面 
	 * @author liuchaoyang 2014-7-30 21:37:45
	 */
	@RequestMapping("/disposeNotificationOfFamily.page")
	public ModelAndView disposeNotificationOfFamily(HttpServletRequest request){
		try{
			String crimid = request.getParameter("crimid");
			String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
			SystemUser user = getLoginUser(request);//获取当前登录的用户
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
			if(idnumber != null && !"".equals(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid", idnumber);
			} else{
				String tempid = "ZFRJTZS";
				request.setAttribute("tempid", tempid);
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
				String xuhao = flowBaseService.getFlowXuHao(year, "doc_tzjssp", null, departid);
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
				String isprimarycontact = "1";//是否主联系人，1：是
				TbprisonerSocialRelation social = prisonerService.findByCrimidRelation(crimid, isprimarycontact);//查询罪犯社会关系
				map.put("text1",year);
				map.put("text3", xuhao);//获取序号
				map.put("text13", user.getName());
				map.put("cbiname", info.getName());
				map.put("cbihomeaddress", info.getFamilyaddress());
				map.put("text31", DateUtil.dateFormatForAip(new Date()));
				if(org!=null){
					map.put("text4",org.getName()+template.getTempname());//取部门名称、表单名称拼接为标题
					map.put("text2", org.getShortname());		
					map.put("text26", org.getName());
					map.put("text6", org.getAddress());
					map.put("text7", org.getBusline());
					map.put("text8", org.getPostcode());
					map.put("text9", org.getExttel());
					map.put("email", org.getEmail());
				}
				if(crime!=null){
					map.put("anyouhuizong", crime.getMaincase());
					map.put("sddiscribe", crime.getOrgname1());
					if(!StringNumberUtil.isNullOrEmpty(crime.getOrgname2())){
						map.put("sddiscribe", crime.getOrgname2());}
					map.put("cjicourtname", crime.getJudgmentname());
					map.put("text36", DateUtil.dateFormatForAip(crime.getInprisondate()));
					map.put("text37", DateUtil.dateFormatForAip(crime.getInprisondate()));
					map.put("xingzhong", crime.getPunishmenttype());
					String zhuxing = crime.getPunishmentyear()+","+crime.getPunishmentmonth()+","+crime.getPunishmentday();
					zhuxing = StringNumberUtil.getXingqi(crime.getPunishmenttype(), zhuxing);
					map.put("zhuxing", zhuxing);//主刑，包含刑种、刑期
					if(!StringNumberUtil.isNullOrEmpty(zhuxing)){
						String xingqi = zhuxing.toString().replace(crime.getPunishmenttype(), "");
						map.put("xingqi", xingqi);//刑期
					}
				}
				if(social!=null){
					map.put("text10", social.getName());
					map.put("text34", social.getRelationship());
				}
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}    
			request.setAttribute("orgid", crime.getOrgid1());
			request.setAttribute("applyid", crimid);
			request.setAttribute("applyname",info.getName());
			request.setAttribute("flowdefid", "doc_tzjssp");
			//资源对象
			returnResourceMap(request); 
			request.setAttribute("formcontent", docconent.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("penaltyPerform/inPrisonManagement/notificationOfFamily");
	}

}
