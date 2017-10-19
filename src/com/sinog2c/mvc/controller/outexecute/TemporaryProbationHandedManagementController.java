package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
/**
 * 暂予监外执行移交管理
 * @author liuxin
 * 2014-7-28 17:47:23
 */
@Controller
public class TemporaryProbationHandedManagementController extends ControllerBase {
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	/**
	 *
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/temporaryProbationHandedManagement")
	public ModelAndView medicalParoleNoticeToCourt(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("chooseCriminal/temporaryProbationHandedManagementChoose");
		returnResourceMap(request);
		return mav;
	}
	
//	/**
//	 * 获取罪犯列表
//	 * 
//	 * @author liuxin
//	 * @param request
//	 * @return resultmap
//	 */
//	@RequestMapping(value = "/getTemporaryProbationHandedManagementBasicInfoList")
//	@ResponseBody
//	public Object getTemporaryProbationHandedManagementBasicInfoList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			String condition = prisonerService.getTheQueryCondition("10159");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			String tempid = "GDSJ_JWZX_JWZXYJGL";
//			map.put("tempid", tempid);
//			map.put("key", key);
//			//map.put("departId", getLoginUser(request).getOrgid());
//			map.put("condition", condition);
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//			
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	
	/**
	 * 跳转暂予监外执行移交管理新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "temporaryProbationHandedManagementAdd")
	public ModelAndView toTemporaryProbationHandedManagementAdd(HttpServletRequest request){
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		ModelAndView mav =  new ModelAndView("aip/temporaryProbationHandedManagementAdd");
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String tempid = "GDSJ_JWZX_JWZXYJGL";
		request.setAttribute("tempid", tempid);
		//request.getParameter("tempid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//根据罪犯id获取罪犯信息
			TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime baseCrime =  prisonerService.getCrimeByCrimid(crimid);
			//通过部门id去找所在单位名称
			SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
			String xuhao = tbsysDocumentService.getXuHao(null, tempid, null, null);
			Date birthday = baseinfo.getBirthday();			
			//int age = this.getAge(birthday);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
			int count = tbsysDocumentService.getCount("", tempid, crimid, "");
			if(count == 0){
				//姓名
				map.put("cbiname", baseinfo.getName());
				//性别
				map.put("cbigendername", baseinfo.getGender());
				//年龄
				//map.put("age", String.valueOf(age));
				//捕前住址
				map.put("cbihomeaddress", baseinfo.getAddressdetail());
				map.put("text1", formatter3.format(new Date()));
				map.put("text2", xuhao);
				map.put("text6", formatter.format(new Date()));
				map.put("text8", org.getShortname());
				if(baseCrime != null){
					//罪名
					map.put("anyouhuizong", baseCrime.getCauseaction());
					//判决日期
					map.put("cjijudgedate", DateUtil.dateFormatForAip(baseCrime.getJudgedate()));
					//判决法院
					map.put("cjicourtname", baseCrime.getCourttype());//有问题
					//主刑
					String punishmenttype = baseCrime.getPunishmenttype();
					if("死缓".equals(punishmenttype)){
						map.put("zhuxing", "死刑缓期两年执行" );
					}else if("无期徒刑".equals(punishmenttype)){
						map.put("zhuxing", "无期徒刑");
					}if("有期徒刑".equals(punishmenttype)){
						map.put("zhuxing","有期徒刑" + baseCrime.getPunishmentyear() + "年");//(月日)
					}
					//刑期止日
					map.put("cjienddate", DateUtil.dateFormatForAip(baseCrime.getSentenceetime()));//
					//刑期变动
//					map.put("xingqibiandong", ""); //刑期变动表
					
					if (template != null) {
						docconent.add(template.getContent());
					}
					request.setAttribute("formDatajson", new JSONObject(map).toString());
					request.setAttribute("temp", GkzxCommon.NEW);
				}
				
				if (template != null) {
					docconent.add(template.getContent());
				}
				
			}else{
				TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
				if (document != null) {
					docconent.add(document.getContent());
					request.setAttribute("docid", document.getDocid());
				}
				request.setAttribute("temp", GkzxCommon.EDIT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//转json
		returnResourceMap(request);
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		
		return mav;
	}
	/**
	 * 修改/保存大字段
	 * @author liuxin
	 */
	@RequestMapping(value = "/saveTemporaryProbationHandedManagement")
	@ResponseBody
	public int saveTemporaryProbationHandedManagement(HttpServletRequest request){
		TbprisonerBaseinfo tbprisonerBaseinfo=new TbprisonerBaseinfo(); 
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		//获取当前登录的用户
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String departid=user.getDepartid();
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("temp")==null?"":request.getParameter("temp");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		//通过部门id去找组织机构的信息
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		String xuhao = tbsysDocumentService.getXuHao(template.getTempname(), tempid, null,departid)==null?"":tbsysDocumentService.getXuHao(template.getTempname(), tempid, null,departid);//获取序号
		String introduction = year+org.getName()+template.getTempname()+xuhao+"号文件";//文书简介
		if("new".equals(operator)){
			String crimid = request.getParameter("crimid");
			String name = tbprisonerBaseinfo.getName();
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+name+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXYJGL+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZYJWZXYJGL+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXYJGL+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXYJGL+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZYJWZXYJGL+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXYJGL+LogCommon.EDIT+LogCommon.EVENT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
	
	
	private static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;//注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }
}
