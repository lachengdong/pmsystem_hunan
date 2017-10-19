package com.sinog2c.mvc.controller.commutationParole;

import java.io.UnsupportedEncodingException;
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
import com.gkzx.common.LogCommon;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 
 * 公示情况记录
 * 
 */
@Controller
@RequestMapping("jxjs")
public class PublicRecords extends ControllerBase {
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	private FlowBaseService flowBaseService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private FlowDeliverService flowDeliverService;
	@Autowired
	private UvFlowService uvFlowService;
	
	
	@RequestMapping("/toCrimChoose.page")
	public ModelAndView toChooseCriminalPage(HttpServletRequest request) {
		String menuid = request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid", menuid);
		return new ModelAndView("commutationParole/publicRecordsChoose");
	}
	@RequestMapping(value="/getDeptInfo")
	@ResponseBody
	public Object getDeptInfo(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		List<SystemOrganization> data=systemOrganizationService.getDeptInfo(user.getOrgid());
		JSONMessage message=new JSONMessage();
		message.setData(data);
		return message;
	}
	
	/**
	 * 方法描述：省局查看监狱下拉列表(包括最小部门-->监区)
	 * @author:mushuhong
	 * @version:2015年2月3日16:56:29
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSjDeptInfo")
	@ResponseBody
	public Object getSjDeptInfo(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		List<Map> dataList = systemOrganizationService.getSjDeptInfo(user.getDepartid(),user);
		return dataList;
	}
	
	
	//获取相应监区的数据
	@RequestMapping(value = "/getPublicRecordsList.json")
	@ResponseBody
	public Object getPublicRecordsList(HttpServletRequest request){
		SystemUser user = getLoginUser(request);	
		//获取当前登录的用户
		String departid=user.getDepartid();
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid"); 
		String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid"); 
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid"); 
		String key = request.getParameter("key")==null?"":request.getParameter("key"); 
		try {
			key = java.net.URLDecoder.decode(key , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		if(!"".equals(orgid) && null!=orgid){
			departid = orgid;
		}
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, crimid, departid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, crimid, departid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	
	
	//跳转到新增、修改页面
	@RequestMapping("/toPublicRecords.page")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		String tempid = request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String fatherMenuid = request.getParameter("fatherMenuid")==null?"":request.getParameter("fatherMenuid");
		JSONArray docconent = new JSONArray();
		if(StringNumberUtil.isNullOrEmpty(docid)){
			String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid");
			request.setAttribute("orgid",orgid);
			String deptId = getLoginUser(request).getDepartid();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptId);
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if("JXJSGSQKJL".equals(tempid)){//减刑、假释
				map.put("nodeid", "N0004");
				map.put("flowdefid", "other_zfjyjxjssp");
			}else if("ZFZYJWZX_BWJY_GSQKJL".equals(tempid)){//保外   
				map.put("nodeid", "N0005");
				map.put("flowdefid", "other_jybwjycbsp");
			}
			map.put("orgid", orgid);
			List<Map<String, Object>> list = flowBaseService.getPublicRecordsData(map);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (list != null && !list.isEmpty()) {
				String s = "";
				for (Map<String, Object> m : list) {
					s += (String) m.get("NAME") + "、";
				}
				s = s.substring(0, s.length() - 1);
				dataMap.put("bgsr", s);
			}else{
				dataMap.put("bgsr", " ");
			}
			SystemOrganization org = systemOrganizationService.getByOrganizationId(orgid);
			dataMap.put("jianqu", org.getName());
			dataMap.put("xfzxk", getLoginUser(request).getName());
			dataMap.put("nowdate", DateUtil.dateFormatForAip(new Date()));
			request.setAttribute("formDatajson", new JSONObject(dataMap).toString());
		}else{
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}
		request.setAttribute("tempid",tempid);
		request.setAttribute("fatherMenuid", fatherMenuid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("commutationParole/publicRecords");
	}
	//保存数据
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/savePublicRecords.json")
	@ResponseBody
	public int savePublicRecords(HttpServletRequest request){
		int countnum = 0;
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String logname = "";
		String publicdate = "";
		String introduction = "";
		String batchid = "";
		TbsysDocument document = new TbsysDocument();
		String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid");
		if(!StringNumberUtil.isNullOrEmpty(orgid)){
			SystemOrganization org = systemOrganizationService.getByOrganizationId(orgid);
			introduction = org.getFullname();
			request.setAttribute("orgid", orgid);
		}
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map<String, String>)list.get(0);
				publicdate = map.get("startdate")+"||"+map.get("enddate");
			}	
		}
		if("JXJSGSQKJL".equals(tempid)){//减刑、假释
			logname = LogCommon.JXJSGSQKJL;
			HashMap map = systemModelService.getCuryearBatch(user.getDepartid());
			if(map!=null){
				map = (HashMap) MapUtil.turnKeyToLowerCase(map);
				String curyear = map.get("curyear").toString();
				String batch = map.get("batch").toString();
				batchid = map.get("batchid").toString();
				introduction += curyear+GkzxCommon.NX_YEAR+GkzxCommon.LISTYEAR+batch+GkzxCommon.PICI+LogCommon.JXJSGSQKJL;
			}
		}else if("ZFZYJWZX_BWJY_GSQKJL".equals(tempid)){//保外   
			logname = LogCommon.BWJYGSQKJL;
			introduction += LogCommon.BWJYGSQKJL;
		}
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setText1(publicdate);
			document.setCrimid(batchid);
			document.setDepartid(orgid);
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(logname+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(logname+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(logname+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setText1(publicdate);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(logname+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(logname+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(logname+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if(countnum == 1) status = 1;
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
		}
		return countnum;
	}
	/**
     * 根据文书Id删除数据
     * @author liuchaoyang
     * 2014-7-24 17:11:45
     */
    @RequestMapping(value = "/deletePublicRecords.json")
    @ResponseBody
    public int deletePublicRecords(HttpServletRequest request) {
        int result = 0;
        short status = 0;
        String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
        SystemUser user = getLoginUser(request);
        SystemLog log = new SystemLog();
        log.setOrgid(user.getUserid());
        log.setOpaction(LogCommon.DEL);
        log.setLogtype(LogCommon.BWJYGSQKJL+LogCommon.BWJYGSQKJL+LogCommon.OPERATE);
        log.setOpcontent(LogCommon.BWJYGSQKJL+LogCommon.BWJYGSQKJL+docid+LogCommon.DEL);
        log.setRemark(LogCommon.BWJYGSQKJL+LogCommon.BWJYGSQKJL+LogCommon.DEL);
        if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
        log.setStatus(status);
        try {
            logService.add(log, user);
        } catch (Exception e) {
            // 吃掉异常
        }
        return result;
    }    
	//获取对应的进程信息
	@RequestMapping(value = "/ajaxGetProcess.json")
	@ResponseBody
	public Object ajaxGetProcess(HttpServletRequest request){
		String flowdefid = request.getParameter("flowdefid");
		String deptId = getLoginUser(request).getDepartid();
		String type=request.getParameter("type");
		if(null != type&&!"".equals(type)&&"sj".equals(type)){
			deptId="1209";
		}
		String unitlevel=getLoginUser(request).getOrganization().getUnitlevel();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", flowdefid);
		map.put("departid", deptId);
		map.put("tempstate", "0,1");
		map.put("sortField", "snodeid");
		map.put("sortOrder", "asc");
		map.put("text3", unitlevel);
		if("6".equals(unitlevel)||"7".equals(unitlevel)){
			map.put("text3", unitlevel);
		}
		List<FlowDeliver> deliverlist = flowDeliverService.findByFlowdefid(map);
		String[] str ={"-1","1"};
		for(String m:str){
			FlowDeliver deliver = new FlowDeliver();
			String text3 = GkzxCommon.NOPASSLOANSTATUS;
			if(GkzxCommon.ONE.equalsIgnoreCase(m)){
				text3 = GkzxCommon.PASSLOANSTATUS;
			}
			deliver.setSnodeid(m);
			deliver.setText3(text3);
			deliverlist.add(deliver);
		}
		
		JSONMessage message = JSONMessage.newMessage();
		message.setData(deliverlist);
		return message.getData();
	}
	
	//获取对应的进程信息
	@RequestMapping(value = "/ajaxGetCaseProcess.json")
	@ResponseBody
	public Object ajaxGetCaseProcess(HttpServletRequest request){
		
		String flowdefid = request.getParameter("flowdefid");
		String departid = getLoginUser(request).getDepartid();
		Map<String, String> paramap = new HashMap<String, String>();
		paramap.put("flowdefid", flowdefid);
		paramap.put("departid", departid);
		
		List<Map<String,Object>> list = flowDeliverService.getCaseProcess(paramap);
		Map<String,Object> passMap = new HashMap<String,Object>();
		passMap.put("id", 1);
		passMap.put("text", GkzxCommon.PASSLOANSTATUS);
		
		Map<String,Object> noPassMap = new HashMap<String,Object>();
		noPassMap.put("id", -1);
		noPassMap.put("text", GkzxCommon.NOPASSLOANSTATUS);
		
		if(null == list){
			list = new ArrayList<Map<String,Object>>();
		}
		list.add(passMap);
		list.add(noPassMap);
		
		return list;
	}
	
	/**
	 * 获取tbsys_code表法院进程信息
	 * @return
	 */
	@RequestMapping("/getFYjincheng")
	@ResponseBody
	public Object getFYjincheng(){
		
		String strtemp=" or ";
		String sql="";
		for(int i=0;i<13;i++){
			int temp=16960;
			if(i==12){
				strtemp="";
			}
			temp+=i;
			sql+=" noid='"+temp+"'"+strtemp;
		}
		Map map=new HashMap();
		map.put("sql", sql);
		List<Map<String,Object>> data=flowDeliverService.getFyJinCheng(map);
		return data;
	}
	/**
	 * 全程留痕
	 * @return
	 */
	@RequestMapping("/selectFlowForScar.json")
	@ResponseBody
	public Object selectFlowForScar(HttpServletRequest request){
		Map<String, Object> paraMap = parseParamMap(request);//参数集合
		List<Map> data= uvFlowService.selectFlowForScar(paraMap);
		return data;
	}
}
