package com.sinog2c.mvc.controller.penaltyPerform;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbxfLaboreducation;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationHisService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.penaltyPerform.TbxfLaboreducationServices;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 类描述：三课教育
 * @author liuchaoyang
 * 下午07:39:03
 */
@Controller
@RequestMapping(value = "/threeCourses")
public class TheThreeCoursesEducation extends ControllerBase{
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Resource
	private SentenceAlterationHisService sentenceAlterationHisService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private TbxfLaboreducationServices tbxfLaboreducationServices;
	
	/**
	 * 跳转到三课教育的罪犯选择页面
	 * @author liuchaoyang
	 * 2014-7-30 20:37:45
	 * @return 
	 */
	@RequestMapping(value = "/toTheThreeCoursesEducation.page")
	public ModelAndView toTheThreeCoursesEducation(HttpServletRequest request) {
		//资源对象
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/theThreeCoursesEducationChoose");
	}
//	/**
//	 * 获取罪犯列表            
//	 * @author liuchaoyang
//	 * 2014-7-30 20:37:45
//	 */
//	@RequestMapping(value = "/getThreeCoursesEducationChoose.json")
//	@ResponseBody
//	public Object getThreeCoursesEducationChoose(HttpServletRequest request){
//		 Map<String, Object> resultmap = new HashMap<String,Object>();
//	        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//	        try {
//	            //取得参数
//	            String key = request.getParameter("key")==null?"":request.getParameter("key");
//	            key = URLDecoder.decode(key,"UTF-8");
//	            String tempid=request.getParameter("tempid");
//	            //分页
//	            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//	            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//	            //字段排序
//	            String sortField = request.getParameter("sortField");
//	            String sortOrder = request.getParameter("sortOrder");
//	            int start = pageIndex * pageSize + 1;
//	            int end = start + pageSize -1;
//	            Map<String,Object> map = new HashMap<String,Object>();  
//	            map.put("key", key);
//	            map.put("tempid", tempid);
//	            map.put("orgid", getLoginUser(request).getOrgid());
//	            map.put("sortField", sortField);
//	            map.put("sortOrder", sortOrder);
//	            map.put("start", String.valueOf(start));
//	            map.put("end",String.valueOf(end));
//	            int count = chooseCriminalService.countAllByCondition(map);
//	            data= chooseCriminalService.getBasicInfoList(map);
//	            resultmap.put("data", data);
//	            resultmap.put("total", count);
//	        }catch (Exception e) {
//	        }
//	        
//	        return resultmap;
//	}
	/**
	 * 方法描述：
	 * 如果流程编号不为空，则根据流程编号idnumber查询大字段，
	 * 根据表单Id到模板信息表查询大字段，根据罪犯编号查询出表单需要显示的数据，返回到表单显示页面 
	 * @author liuchaoyang 2014-7-30 21:37:45
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addThreeCoursesEducation.page")
	public ModelAndView addThreeCoursesEducation(HttpServletRequest request){
		try{
			String menuid=request.getParameter("menuid");
			String crimid = request.getParameter("crimid");
			String flowdraftid = request.getParameter("flowdraftid")==null?"":request.getParameter("flowdraftid");//流程草稿ID
			JSONArray docconent = new JSONArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			if(!StringNumberUtil.isNullOrEmpty(flowdraftid)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(flowdraftid);
				docconent.add(docConent);
				request.setAttribute("flowdraftid", flowdraftid);
			}else{
				String tempid = "SH_SKJYQKTJB";
				request.setAttribute("tempid", tempid);
				HashMap<String, Object> map = new HashMap<String, Object>();
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				map.put("crimid", crimid);
				map.put("name", info.getName());
				if(!StringNumberUtil.isNullOrEmpty(baseCrime)){
					map.put("inprisondate", DateUtil.dateFormatForAip(baseCrime.getInprisondate()));//入监时间
					map.put("aliasno", baseCrime.getAliasno());//番号
				}
				
				String criminalInfo = systemTemplateService.getSystemTemplateByCondition("10302",null).getContent();
				String querySql =  "select f_formatdate(h.examineend) 考核止日,"+
		        " f_formatdate(case when a.predate is not null then a.predate + 1 else a.inpersion end) 考核起日 from tbxf_sentencealteration a,"+
		         "(select * from (select rownum as xuhao, f.* from (select * from tbxf_commuteparole_batch e  where e.departid ='" + departid + "' order by e.curyear desc, e.batch desc) f) g where g.xuhao = 1) h  where a.jailid = h.departid and a.crimid='"+ crimid+"'";
				Map contMap = tbxfSentencealterationService.selectTbxfMapBySql(querySql);
				criminalInfo = MapUtil.replaceBracketContent(criminalInfo, contMap);
				criminalInfo = MapUtil.formatFormString(criminalInfo);
				map.put("jqyj", "    " + criminalInfo);
				map.put("nowdate", DateUtil.dateFormatForAip(new Date()));
				map.put("jyname", user.getOrganization().getFullname());
				int year = Integer.parseInt(sdf.format(new Date()));
				for (int i = 0; i < 4; i++) map.put("year"+i, year+i-3);
//				map.put("crimid", crimid);
//				map.put("sortField", "caseno");
//				map.put("sortOrder", "desc");
//				List<Map> list = sentenceAlterationHisService.getSentenceChangesOfCrimid(map);
//				if(list.size()>0){
//					 HashMap<String, Object> yearmap = (HashMap) list.get(0);
//					int year = Integer.parseInt(yearmap.get("courtyear").toString()==null?"":sdf.format(new Date()));
//					for (int i = 0; i < 4; i++) map.put("year"+i, year+i);
//				}		
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}    
			//所有下拉列表显示
//			Map<String, Object> selectmap = new HashMap<String, Object>();
//			String zzjy = systemCodeService.getcodeValue("GKLU1");//政治教育
//			for (int i = 0; i < 4; i++) selectmap.put("zzjy"+i, zzjy);
//			String whjy = systemCodeService.getcodeValue("GKLU2");//文化教育
//			for (int i = 0; i < 4; i++) selectmap.put("whjy"+i, whjy);
//			String jsjy = systemCodeService.getcodeValue("GKLU3");//技术教育
//			for (int i = 0; i < 4; i++) selectmap.put("jsjy"+i, jsjy);
			
			request.setAttribute("orgid", baseCrime.getOrgid1());
			request.setAttribute("applyid", crimid);
			request.setAttribute("applyname",info.getName());
			request.setAttribute("flowdefid", "doc_skjytjb");
			request.setAttribute("crimid", crimid);
			request.setAttribute("menuid", menuid);
			
//			request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());//显示到表单上面
			//资源对象
			returnResourceMap(request); 
			request.setAttribute("formcontent", docconent.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("aip/addThreeCoursesEducation");
	}

	@RequestMapping("/theThreeCoursesPages.page")
	public ModelAndView theThreeCoursesPages(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String idname = request.getParameter("idname");
			request.setAttribute("idname", idname);
			String[] idnames = idname.split(",");
			crimid = ids ==null?"":ids[0];
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("outexecute/prisonDocuments/shanghai/theThreeCoursesPages");
	}
	
	/**
	 * 单个罪犯列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getThreeCoursesBy.json")
	@ResponseBody
	public HashMap<String, Object> getThreeCoursesBy(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
        HashMap<String, Object> result = new HashMap<String, Object>();
        String deptId=user.getDepartid();//获取当前登录的用户
        String applyid=request.getParameter("crimid")==null? "":request.getParameter("crimid");//申请人id
        //分页
        int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
        int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
        //字段排序
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        int start = pageIndex * pageSize + 1;
        int end = start + pageSize -1;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("departId", deptId);
        map.put("applyid", applyid);
        map.put("flowdefid", "doc_skjytjb");
        map.put("sortField", sortField);
        map.put("sortOrder", sortOrder);
        map.put("start", String.valueOf(start));
        map.put("end",String.valueOf(end));   
        List<Map> list = flowBaseService.findByMapCondition(map);
        int count = flowBaseService.countAllByCondition(map);
        result.put("total", count);
        result.put("data", list);
        return result;        
	}
	/**
	 *方法描述：保存劳动教育信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveEducationCoursesBy.json")
	@ResponseBody
	public String saveEducationCoursesBy(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		SystemUser user = getLoginUser(request);
		if (noteinfo != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map<String, String>)list.get(0);
				String otherwise = map.get("otherwise")==null?"":map.get("otherwise");
				if(crimid==null) crimid=map.get("criminalid");
				for(int i = 0;i<4;i++){
					String laborid = map.get("laborid"+i)==null?"":map.get("laborid"+i);
					String year = map.get("year"+i)==null?"":map.get("year"+i);
					String zzjy = map.get("zzjy"+i)==null?"":map.get("zzjy"+i);
					String whjy = map.get("whjy"+i)==null?"":map.get("whjy"+i);
					String jsjy = map.get("jsjy"+i)==null?"":map.get("jsjy"+i);
					String remark = map.get("remark")==null?"":map.get("remark");
					
					if (StringNumberUtil.isNullOrEmpty(laborid)) {
						TbxfLaboreducation laboreducation = tbxfLaboreducationServices.findLaboreducationBycrimid(crimid,year,tempid);
						if (!StringNumberUtil.isNullOrEmpty(laboreducation))laborid = laboreducation.getLaborid();
					}
					
					if(StringNumberUtil.notEmpty(crimid) && StringNumberUtil.notEmpty(tempid)
						&& (StringNumberUtil.notEmpty(zzjy)||StringNumberUtil.notEmpty(whjy)||StringNumberUtil.notEmpty(jsjy))){
						TbxfLaboreducation laboreducation = new TbxfLaboreducation();
						laboreducation.setCrimid(crimid);
						laboreducation.setTempid(tempid);
						laboreducation.setYear(year);
						laboreducation.setFraction1(zzjy);
						laboreducation.setFraction2(whjy);
						laboreducation.setFraction3(jsjy);
						laboreducation.setOtherwise(otherwise);
						laboreducation.setRemark(remark);
						laboreducation.setOptime(new Date());
						laboreducation.setOptid(user.getUserid());
						if(!StringNumberUtil.isNullOrEmpty(laborid)){
							result.put("laborid"+i, laborid);
							laboreducation.setLaborid(laborid);
							tbxfLaboreducationServices.updateLaboreducation(laboreducation);
						}else{
							laborid = tbxfLaboreducationServices.getLaborid();
							result.put("laborid"+i, laborid);
							laboreducation.setLaborid(laborid);
							tbxfLaboreducationServices.insertLaboreducation(laboreducation);
						}
					}else{
						tbxfLaboreducationServices.deleteByPrimaryKey(laborid);
					}
				}
			}
			return GkzxCommon.RESULT_SUCCESS;
		}
		return GkzxCommon.RESULT_ERROR;
	}

}
