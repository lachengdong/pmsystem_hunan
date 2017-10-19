package com.sinog2c.mvc.controller.commutationParole;

import java.io.PrintWriter;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowArchivesCode;
import com.sinog2c.model.flow.FlowArchivesExist;
import com.sinog2c.model.flow.FlowBaseArchives;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.quartz.UpdateSentenceChangeJobTask;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.flow.FlowArchivesCodeService;
import com.sinog2c.service.api.flow.FlowArchivesExistService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseArchivesService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowOtherFlowService;
import com.sinog2c.service.api.flow.FlowService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 批量归档
 * @author hzl
 *
 */
@Controller
@RequestMapping("/case")
public class BatchFilingController extends ControllerBase {
	@Resource
	private SystemLogService logService;
	@Resource
	private FlowService flowService;
	@Resource
	private FlowArchivesService flowArchivesService;
	@Resource
	private FlowArchivesExistService flowArchivesExistService;
	@Resource
	private FlowOtherFlowService flowOtherFlowService;
	@Resource
	private SystemTemplateService systemTemplateService;
	@Resource
	private FlowBaseArchivesService flowBaseArchivesService;
	@Resource
	private FlowArchivesCodeService flowArchivesCodeService;
	@Resource
	private SentenceAlterationService sentenceAlterationService;
	@Resource
	private FlowBaseOtherService flowBaseOtherService;
	
	/**
	 * 跳转批量归档列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/toBatchFiling.page")
	public ModelAndView toTab1Page(HttpServletRequest request) {
		//获取参数
		UpdateSentenceChangeJobTask task = new UpdateSentenceChangeJobTask();
		task.doBiz();//批量归档之前先调用UpdateSentenceChangeJobTask类，执行一下存储过程P_UPDATESENTENCECHANGEDATA
		String modetype =request.getParameter("modetype");
		ModelAndView mav = new ModelAndView("commutationParole/batchFiling");
		if (!StringNumberUtil.isEmpty(modetype)) {
			mav = new ModelAndView("commutationParole/batchFilingJw");
		}
		return mav;
	}
	
	
	
	/**
	 * 显示封皮信息
	 * 
	 * @author zh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/batchmakecover.action")
	@ResponseBody
	public Object batchMakeCover(HttpServletRequest request) {
		//用户对象
		SystemUser user = getLoginUser(request);
		SystemTemplate systemTemplate = new SystemTemplate();
		
		//定义变量
		Map<String, Object> resultmap = new HashMap<String,Object>();
		HashMap<Object, Object> tempmap = new HashMap<Object,Object>();
		
		//获取参数
		String tempid = request.getParameter("tempid");
		String personid = request.getParameter("personid");
		tempmap.put("crimid", personid);
		String modetype =request.getParameter("modetype");
		if (StringNumberUtil.isEmpty(modetype)) {
			tempmap.put("flowdefid","other_zfjyjxjssp");
		} else {
			tempmap.put("flowdefid","other_jybwjycbsp");
		}
		try{
			
			//根据归档人 制作 封面  返回大字段 参数 tempid departid
			List<HashMap<Object, Object>> list = sentenceAlterationService.getGuidangCrimeBaseInfo(tempmap);
			
			HashMap<Object,Object> map = list.get(0);
			HashMap<String, Object> jsonmap = new HashMap<String, Object>();
			Set<Object> set = map.keySet();
			for(Object m:set){
				jsonmap.put(((String) m).toLowerCase(), map.get(m));
			}
			jsonmap.put("jingbanren", user.getName());
			jsonmap.put("chengbanren", user.getName());//承办人
			jsonmap.put("lijuandate", DateUtil.dateFormatForAip(map.get("CTIME")));
			jsonmap.put("riqi", DateUtil.dateFormatForAip(map.get("CTIME")));//立卷日期
			jsonmap.put("deaprtname", map.get("JIANY"));
			jsonmap.put("jianyu", map.get("JIANY"));//监狱名称
			jsonmap.put("name", map.get("CNAME"));//罪犯姓名
			jsonmap.put("hao", map.get("ANJUANHAO"));//号
			jsonmap.put("niandu", map.get("NIANDU"));//年度
			jsonmap.put("zi", map.get("ZI"));//字
			jsonmap.put("jiancheng", map.get("JIANYUSHORTNAME"));//监狱简称
			
			
			String[] tempids = tempid.split(",");
			if(GkzxCommon.XINGQI_WUQI.equals(map.get("NOWPUNISHMENTYEAR"))||
			   GkzxCommon.XINGQI_SIHUAN.equals(map.get("NOWPUNISHMENTYEAR"))){
				tempid = tempids[1];//死缓、无期取表单ID  TJSJYGLJ2
			}else{
				tempid = tempids[0];//有期取表单ID  TJSJYGLJ
			}
			systemTemplate = systemTemplateService.selectByTempid(tempid,user.getDepartid());
			if(!StringNumberUtil.notEmpty(systemTemplate)){
				systemTemplate = systemTemplateService.selectByTempid(tempids[0],user.getDepartid());
			}
			if(StringNumberUtil.notEmpty(systemTemplate)){
				resultmap.put("formcontent", systemTemplate.getContent());
			}
			resultmap.put("formDatajson", new JSONObject(jsonmap).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultmap;
	}
	
	/**
	 * 结案
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/windUpCase")
	@ResponseBody
	public Object windUpCase(HttpServletRequest request){
		FlowArchivesExist flowArchivesExist = new FlowArchivesExist();
		Date now = new Date();
		int rows=0;
		String flowdraftid = request.getParameter("flowdraftid");
		flowArchivesExist.setFlowdraftid(flowdraftid);
		flowArchivesExist.setStarttime(now);
		flowArchivesExist.setEndtime(now);
		rows = flowArchivesExistService.insertSelective(flowArchivesExist);
		return rows;
	}
	/**
	 * 批量制作封皮
	 * 
	 * @author zh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/ajaxmakecover.action")
	@ResponseBody
	public Object ajaxMakeCover(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		FlowArchivesCode archivescode = new FlowArchivesCode();
		FlowArchivesExist flowArchivesExist = new FlowArchivesExist();
		
		//定义变量
		int rows = 0;
		String[] consql;
		Date now = new Date();
		HashMap<String,String> tempidmap = new HashMap<String,String>();
		HashMap<Object,FlowArchivesCode> codemap = new HashMap<Object,FlowArchivesCode>();
		
		//获取参数
		String resid =request.getParameter("resid");
		String tempid =request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String contempid =request.getParameter("contempid");
		String docconent = request.getParameter("docconent");
		String personid = request.getParameter("personid");
		String personname = request.getParameter("personname");
		String flowdraftid = request.getParameter("flowdraftid");
		
		try{
			
			if(contempid!=null && !"".equals(contempid)){
				
				//获取模板信息
				List<SystemTemplate> templetlist = systemTemplateService.selectAllByDepartid(user.getDepartid());
				if(templetlist!=null && templetlist.size()>0){
					for(SystemTemplate temp:templetlist){
						tempidmap.put(temp.getTempid(), temp.getContent());
					}
				}
				Map map = new HashMap<String, String>();
				map.put("departid",user.getDepartid());
				//获取档案类别信息
				List<FlowArchivesCode> list = flowArchivesCodeService.selectAllByDepid(map);
				
				if(list!=null && list.size()>0){
					for(FlowArchivesCode code:list){
						codemap.put(code.getTempid(), code);
					}
				}
				
				//封皮档案信息
				if(codemap.containsKey(tempid)){
					archivescode = codemap.get(tempid);
					//新增电子档案信息
					rows = flowArchivesService.insertArchtive(resid, flowdraftid, personid, personname, docconent, user, archivescode);
					
					//插入
					flowArchivesExist.setFlowdraftid(flowdraftid);
					flowArchivesExist.setStarttime(now);
					flowArchivesExist.setEndtime(now);
					rows = flowArchivesExistService.insertSelective(flowArchivesExist);
				}
				
				//夹纸档案信息
				consql = contempid.split(",");
				if(consql!=null && consql.length>0){
					for(String m:consql){
						//查询电子档案类型编码
						//tempid ===>>> m
						if(codemap.containsKey(m) && tempidmap.containsKey(m)){
							docconent = tempidmap.get(m);
							archivescode = codemap.get(m);
							//新增电子档案信息
							rows = flowArchivesService.insertArchtive(resid, flowdraftid, personid, personname, docconent, user, archivescode);
							// 日志
							if( 1== rows){
								message.setInfo("添加成功!");
								message.setSuccess();
							}else{
								message.setInfo("操作失败!");
							}
							
							log.setLogtype("批量制作封皮操作");
							log.setOpaction("制作封皮");
							log.setOpcontent("封皮信息,resid="+resid);
							log.setOrgid("arch");
							log.setRemark("添加成功!");
							log.setStatus((short)message.getStatus());
							logService.add(log, user);	
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * 批量案件归档
	 * 
	 * @author zh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/ajaxmakearchive.action")
	@ResponseBody
	public Object ajaxMakeArchive(HttpServletRequest request) {
		Object result = -1;
		String tempid = "";
		String modetype =request.getParameter("modetype");
		String flowdraftid = request.getParameter("flowdraftid");
		try{
			int count = 1;
			if (StringNumberUtil.isEmpty(modetype)) {
				tempid="jailcommutereport";
			} else {
				tempid="jailbaowaireport";
			}
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
			if(!"1300".equals(provincecode) || StringNumberUtil.isEmpty(modetype)) //河北保外不检查建议书
				
			count = flowOtherFlowService.isExistJys(flowdraftid,tempid);//检查建议书是否保存
			if(count>0){
				result = this.ajaxPublicMakeArchive( request);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 批量案件归档
	 * 
	 * @author YangZR
	 * @Date 2014-05-12
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/ajaxCheckCaseFiling.json")
	@ResponseBody
	public Object ajaxCheckCaseFiling(HttpServletRequest request){
		
		String filetype = request.getParameter("filetype");//归档类型
		String flowdraftids = request.getParameter("flowdraftids");
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", user.getDepartid());
		paramap.put("checkclob", 1);
		paramap.put("filetype", filetype);
		paramap.put("flowdraftids", flowdraftids);
		//判断某个文书是否存在，不存在则报此案件返回给客户显示，并跳过此flowdrafrtid,
		return flowBaseOtherService.checkCaseFilingInfo(paramap, user);
		
	}
	
	
	/**
	 * 获取需要组装的归档大字段
	 * 
	 * @author YangZR
	 * @Date 2014-05-12
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCaseFilingAssembleData.json")
	@ResponseBody
	public Object getCaseFilingAssembleData(HttpServletRequest request){
		
		String filetype = request.getParameter("filetype");//归档类型
		String flowdraftid = request.getParameter("flowdraftid");
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", user.getDepartid());
		paramap.put("isassemble", 1);
		paramap.put("filetype", filetype);
		paramap.put("flowdraftid", flowdraftid);
		//返回组装的数据到前台
		return flowBaseOtherService.getCaseFilingAssembleData(paramap, user);
		
	}
	
	
	/**
	 * 将组装的数据传回后台(如果有的话)，并开始归档
	 * 
	 * @author YangZR
	 * @Date 2014-05-12
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/caseFileWithData.json")
	@ResponseBody
	public Object caseFileWithData(HttpServletRequest request){
		List<Map<String,Object>> assembleDataList = null;
		
		SystemUser user = this.getLoginUser(request);
		String data = request.getParameter("data");
		Object obj = JsonUtil.Decode(data);
		if(obj instanceof List<?>){
			//组装后的数据
			assembleDataList = (ArrayList<Map<String,Object>>)obj;
		}
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		String flowdraftid = request.getParameter("flowdraftid");
		String filetype = request.getParameter("filetype");
		paramap.put("departid", user.getDepartid());
		paramap.put("isassemble", 0);
		paramap.put("flowdraftid", flowdraftid);
		paramap.put("filetype", filetype);
		
		return flowBaseOtherService.caseFileWithData(paramap, assembleDataList,user);
		
	}


	/**
	 * 批量归档
	 * 
	 * @author zh
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/ajaxguidangcrimebaseinfo.json")
	@ResponseBody
	public void ajaxGuidangCrimeBaseInfo(HttpServletRequest request,HttpServletResponse response) {
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		// 用户对象
		SystemUser user = getLoginUser(request);
		map.put("departid", user.getOrganization().getOrgid());
		String modetype =request.getParameter("modetype");
		if (StringNumberUtil.isEmpty(modetype)) {
			map.put("flowdefid","other_zfjyjxjssp");
		} else {
			map.put("flowdefid","other_jybwjycbsp");
		}
		ajaxPublicGuiDangCrimeBaseInfo(request,response,map);
	}
	
	/**
	 * 添加档案信息
	 * 
	 * @author zh
	 * @param resid
	 * @param docconent
	 * @param user
	 */
	public JSONMessage insertArchtive(String resid,String flowdraftid,String personid,String personname,String docconent,SystemUser user,FlowArchivesCode code){
		//用户对象
		SystemLog log = new SystemLog();
		FlowArchives flowArchives = new FlowArchives();
		FlowBaseArchives flowBaseArchives = new FlowBaseArchives();
		JSONMessage message = JSONMessage.newMessage();
		
		//定义变量
		int rows = 0;
		String orgid = "";
		String flowid = "";
		String departid = "";
		Date now = new Date();
		String rank = GkzxCommon.ONE;//涉密程度
		String flowdefid = "arch_zfdajdsp";//档案流程自定义ID
		
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.YEARFORMAT);
		
		if(user!=null){
			orgid = user.getOrganization().getOrgid();
			departid = user.getDepartid();
		}
		
		try{
			//获取归档ID
			String archiveid = flowArchivesService.getArchiveid(departid);
			
			if(!StringNumberUtil.isNullOrEmpty(archiveid)){
				String tempflowval = flowService.getFlowid(orgid,departid,flowdefid,1);
				//判断该单位是否存在流程
				if(tempflowval.length()>0){
					String[] flowval = tempflowval.split(",");
					flowid = flowval[0];
				}
				//保持档案大字段
				flowArchives.setArchiveid(archiveid);
				flowArchives.setDepartid(departid);
				flowArchives.setDocconent(docconent);
				flowArchives.setFlowid(flowid);
				flowArchives.setOpid(user.getUserid());
				flowArchives.setOptime(now);
				flowArchives.setSn(archiveid);
				flowArchives.setText1(flowdraftid);
				rows = flowArchivesService.insertSelective(flowArchives);
				
				String docyear = sdf.format(now);
				String name = code.getName();//档案名称
				String isattached = String.valueOf(code.getIspositive());//是否正副卷
//				String isattached = String.valueOf("0");//是否正副卷
				String docid = code.getCodeid();//档案类型
				String classifcation = code.getPcodeid()==null?"":code.getPcodeid();//档案类别
				if(GkzxCommon.ONE.equals(isattached)){
					rank = GkzxCommon.THREE;
				}
				//保存历史档案
				flowBaseArchives.setPersonid(personid);
				flowBaseArchives.setPersonname(personname);
				flowBaseArchives.setArchiveid(archiveid);
				flowBaseArchives.setDepartid(departid);
				flowBaseArchives.setDocyear(Integer.parseInt(docyear));
				flowBaseArchives.setClassification(classifcation);
				flowBaseArchives.setDocid(docid);
				flowBaseArchives.setName(name);
				flowBaseArchives.setIsattached(Short.valueOf(isattached));
				flowBaseArchives.setOpid(user.getUserid());
				flowBaseArchives.setOptime(now);
				flowBaseArchives.setRetention(Short.valueOf("10"));
				flowBaseArchives.setType(Short.valueOf(GkzxCommon.ONE));
				flowBaseArchives.setRank(Short.valueOf(rank));
				
				rows = flowBaseArchivesService.insert(flowBaseArchives);
				
				// 日志
				if( 1== rows){
					message.setInfo("添加成功!");
					message.setSuccess();
				}else{
					message.setInfo("操作失败!");
				}
				
				log.setLogtype("流程流转操作");
				log.setOpaction("新增");
				log.setOpcontent("新增流转信息,resid="+resid);
				log.setOrgid("flow");
				log.setRemark("添加成功!");
				log.setStatus((short)message.getStatus());
				logService.add(log, user);	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	
	
	
	/**
	 * 省局跳转批量归档列表页
	 * 
	 * @author zl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/toBatchFilingSj.page")
	public ModelAndView toTab1PageSj(HttpServletRequest request) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		//获取参数
		String modetype =request.getParameter("modetype");
		ModelAndView mav = new ModelAndView("commutationParole/batchFilingSj");
		if (!StringNumberUtil.isEmpty(modetype)) {
			mav = new ModelAndView("commutationParole/batchFilingSjJw");
		}
		return mav;
	}
	//省局批量归档
	@RequestMapping(value = "/ajaxmakearchivesj.action")
	@ResponseBody
	public Object ajaxmakearchivesj(HttpServletRequest request) {
		Object result = -1;
		try{
			 result = this.ajaxPublicMakeArchive( request);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	//省局批量归档
	@RequestMapping(value = "/ajaxguidangcrimebasesjinfo.json")
	@ResponseBody
	public void ajaxguidangcrimebasesjinfo(HttpServletRequest request,HttpServletResponse response) {
		HashMap<Object,Object> map = new HashMap<Object,Object>();
		// 用户对象
		SystemUser user = getLoginUser(request);
		String modetype =request.getParameter("modetype");
		if (StringNumberUtil.isEmpty(modetype)) {
			map.put("flowdefid","other_sjjxjssp");
		} else {
			map.put("flowdefid","other_sjbwjysp");
		}
		map.put("unitlevel", user.getOrganization().getUnitlevel());
		map.put("departid", user.getOrganization().getOrgid());
		ajaxPublicGuiDangCrimeBaseInfo(request,response,map);
	}


	
	
	
	
	/*
	 * 查询批量归档数据公用方法
	 */
	@SuppressWarnings("unchecked")
	public void ajaxPublicGuiDangCrimeBaseInfo(HttpServletRequest request,HttpServletResponse response,HashMap map) {
		Map<String, Object> resultmap = new HashMap<String,Object>();
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			
	    	
	    	//获取总条数
	    	int count = sentenceAlterationService.countByGuidangCrimeBaseInfo(map);
	    	List<HashMap<Object, Object>> list= sentenceAlterationService.getGuidangCrimeBaseInfo(map);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap<Object,Object> listmap = list.get(i);
					HashMap<Object,Object> tempmap = new HashMap<Object,Object>();
					for(Object obj:listmap.keySet()){
						tempmap.put(((String)obj).toLowerCase(), listmap.get(obj));
					}
					data.add(tempmap);
				}
			}
			resultmap.put("data", data);
			resultmap.put("total", count);

			String result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 监狱、省局批量案件归档抽取公共方法
	 * 
	 */
	public Object ajaxPublicMakeArchive(HttpServletRequest request) {
		//用户对象
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		FlowArchivesExist flowArchivesExist = new FlowArchivesExist();
		
		//定义变量
		int rows = 0;
		Object result = 0;
		String docconent = "";
		Date now = new Date();
		
		//获取参数
		String resid =request.getParameter("resid");
		String personid = request.getParameter("personid");
		String personname = request.getParameter("personname");
		String flowdraftid = request.getParameter("flowdraftid");
		String archtempid =StringNumberUtil.getDefaultStringOnNull(request.getParameter("archtempid"), "");

		List<FlowArchivesCode> list = new ArrayList<FlowArchivesCode>();
		Map<Object, Object> contentmap = new HashMap<Object,Object>();
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("personid", personid);
		paramMap.put("archtempid", StringNumberUtil.formatString(archtempid, ","));	
		
		try{
			Map map1 = new HashMap<String, String>();
			map1.put("departid",user.getDepartid());
			//获取需归档的档案类型
			list = flowArchivesCodeService.selectAllByDepid(map1);
		
			//获取要归档的大字段
			List<HashMap<Object, Object>>  maplist = flowArchivesService.selectAllByArchDoc(personid);
			if(maplist!=null && maplist.size()>0){
				for(int i=0;i<maplist.size();i++){
					HashMap<Object,Object> map = maplist.get(i);
					Clob content = (Clob) map.get("DOCCONENT");
					if(content!=null && !"".equals(content)){
						contentmap.put(map.get("TEMPID"), content.getSubString(1, (int)content.length()));
					}
				}
			}
			if (!StringNumberUtil.isNullOrEmpty(archtempid)) {
				//获取要归档的非流程大字段
				maplist = flowArchivesService.selectAllByArchDocNoFlow(paramMap);
				if(maplist!=null && maplist.size()>0){
					for(int i=0;i<maplist.size();i++){
						HashMap<Object,Object> map = maplist.get(i);
						Clob content = (Clob) map.get("DOCCONENT");
						if(content!=null && !"".equals(content)){
							contentmap.put(map.get("TEMPID"), content.getSubString(1, (int)content.length()));
						}
					}
				}
			}
			
			
			if(list!=null && list.size()>0){
				for(FlowArchivesCode archivescode:list){
					String tempid = archivescode.getTempid();
					if(tempid!=null && !"".equals(tempid)){
						if(contentmap.containsKey(tempid)){
							docconent = (String) contentmap.get(tempid);
							if(docconent!=null && !"".equals(docconent)){
								rows = flowArchivesService.insertArchtive(resid,flowdraftid,personid, personname, docconent, user, archivescode);
								// 日志
								if( 1== rows){
									message.setInfo("添加成功!");
									message.setSuccess();
								}else{
									message.setInfo("操作失败!");
								}
								log.setLogtype("批量归档操作");
								log.setOpaction("归档");
								log.setOpcontent("归档信息,resid="+resid);
								log.setOrgid("arch");
								log.setRemark("添加成功!");
								log.setStatus((short)message.getStatus());
								logService.add(log, user);	
							}
						}
					}
				}
				//更新
				flowArchivesExist.setState(1);
				flowArchivesExist.setEndtime(now);
				flowArchivesExist.setFlowdraftid(flowdraftid);
				flowArchivesExistService.updateByExample(flowArchivesExist);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
