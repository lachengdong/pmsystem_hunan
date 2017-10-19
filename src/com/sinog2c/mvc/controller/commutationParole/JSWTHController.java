package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 类描述：委托函（假释）
 * @author hzl
 */
@Controller
public class JSWTHController extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	
	/**
	 * 方法描述：跳转到罪犯处理列表页面
	 * @author kexz
	 * 2014-8-12
	 */
	@RequestMapping(value = "DoJSWTHPage")
	public ModelAndView DoJSWTHPage(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/jswthcriminalchoose");
	}
	
	/**
	 * 方法描述：跳转到罪犯委托函（假释）列表页
	 * @author kexz
	 * 2014-8-12
	 */
	@RequestMapping(value = "toJSWTHPage")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		returnResourceMap(request);
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid=request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String idname=request.getParameter("idname");
		String id=request.getParameter("id");
		String menuid=request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("commutationParole/jsWTHList");
	}
	
//	/**
//	 * @author kexz
//	 * 方法描述：获取罪犯处理数据
//	 * 2014-8-12
//	 */
//	@RequestMapping(value="getJSWTHChoose")
//	@ResponseBody
//	public Object getJSWTHChoose(HttpServletRequest request)throws Exception {
//		Map<String, Object> resultmap = new HashMap<String, Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key") == null ? "" : request.getParameter("key");
//			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ?"": Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer) (request.getParameter("pageSize") == null ? "": Integer.parseInt(request.getParameter("pageSize")));
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize - 1;
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//			map.put("tempid", "SZXF_JSWTH");
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			map.put("start", String.valueOf(start));
//			map.put("end", String.valueOf(end));
//			int count = chooseCriminalService.countAllByCondition(map);
//			data = chooseCriminalService.getBasicInfoList(map);
//			resultmap.put("data", data);
//			resultmap.put("total", count);
//
//		} catch (Exception e) {
//		}
//		return resultmap;
//	}

	/**
	 * 方法描述：跳转到罪犯委托函（假释）新增页面
	 */
	@RequestMapping(value="toJSWTH")
	public ModelAndView manageUser(HttpServletRequest request,
			HttpServletResponse response) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String tempId = "SZXF_JSWTH";
		//根据罪犯id获取罪犯信息
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbsysDocument tbsysDocument = tbsysDocumentService.getTbsysDocument(null, tempId, crimid, null);
		if (tbsysDocument != null) {
			docconent.add(tbsysDocument.getContent());
			request.setAttribute("docid", tbsysDocument.getDocid());
		} else {
			//根据根据登录用户去获取对应部门id
			String departid=user.getDepartid();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempId, departid);
			Map<String, Object> map = new HashMap<String, Object>();
		
			SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
			map.put("cbiname", baseinfo.getName());
			map.put("cbinativenamedetail", org.getFullname());
			if (template != null) {
				docconent.add(template.getContent());
			}
			// 转json
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempId);
		request.setAttribute("name",baseinfo.getName());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("menuid",menuid);
		return new ModelAndView("aip/JSWTH");
	}
	
	
	/**
	 * @author kexz
	 *显示委托函新增表单
	 * 2014-8-12
	 */
	@RequestMapping(value="toJSWTHAdd")
	public ModelAndView toJSWTHAdd(HttpServletRequest request){
		returnResourceMap(request);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String hebeicode = jyconfig.getProperty(GkzxCommon.HEBEI_CODE);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		String tempid = request.getParameter("tempid");//获取对应表单打印中表单编号
		String menuid=request.getParameter("menuid");
		String crimid=request.getParameter("crimid");
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);//根据罪犯id获取罪犯信息
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);//根据表单编号获取表单
		if (template != null) {
			docconent.add(template.getContent());
		}
		SystemOrganization org=systemOrganizationService.getByOrganizationId(deptid);//通过部门id去找组织机构的信息
		Map<String, Object> map=new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		map.put("inscribedate",sdf.format(new Date()));
		map.put("cbiname",baseinfo.getName());
		map.put("cbinativenamedetail", org.getCity());
		if(true) { //StringNumberUtil.belongTo(deptid, hebeicode)
			map.put("text1", org.getFullname() + "\\r\\n" + "关于对拟假释罪犯" + baseinfo.getName() + "进行调查评估的委托函");
			String criminalInfo = systemTemplateService.getSystemTemplateByCondition("10267",null).getContent();
			String querySql = "select a.name 姓名,a.gender 性别,getage(a.birthday) 年龄,a.education 文化程度,a.familyaddress 家庭住址,a.registeraddressdetail 户籍," +
				"f_formatdate(b.judgedate) 判决日期,b.maincase 罪名,b.judgmentname 判决法院," +
				"case when b.punishmenttype='有期徒刑' then b.punishmenttype||f_formatymd(b.punishmentyear,b.punishmentmonth,b.punishmentday,0) else b.punishmenttype end 刑期," +
				"f_formatymd(c.nowpunishmentyear,c.nowpunishmentmonth,c.nowpunishmentday,0) 现余刑 " +
				"from tbprisoner_baseinfo     a," +
				"tbprisoner_base_crime   b," +
				"tbxf_sentencealteration c " +
				"where a.crimid=b.crimid and a.crimid=c.crimid and a.crimid='" + crimid + "'";
			Map contMap = tbxfSentencealterationService.selectTbxfMapBySql(querySql);
			criminalInfo = MapUtil.replaceBracketContent(criminalInfo, contMap);
			criminalInfo = MapUtil.formatFormString(criminalInfo);
			map.put("text2", criminalInfo.replaceAll("\n", "\\\\r\\\\n"));
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("name",baseinfo.getName());
		request.setAttribute("crimid",crimid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/JSWTH.jsp"));
	}
	
	/**
	 * 查看、修改委托函（假释）时，根据文书Id查询出大字段回显
	 * @author kexz 
	 * 2014-8-12
	 */
	@RequestMapping(value="editJSWTH")
	public ModelAndView editJSWTH(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		HashMap map = new HashMap();
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/JSWTH");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-8-12
	 */
	@RequestMapping(value="deleteJSWTH")
	@ResponseBody
	public int deleteJSWTH(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.WEITUOHAN_JIASHI+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.WEITUOHAN_JIASHI+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.WEITUOHAN_JIASHI+LogCommon.DEL);
		log.setStatus(status);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}	
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-12
	 * @author kexz
	 */
	@RequestMapping(value="getJSWTHList")
	@ResponseBody
	public HashMap<String, Object> getJSWTHList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String key = request.getParameter("key");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	
	/**
	 * 修改/保存大字段
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value="saveJSWTH")
	@ResponseBody
	public int saveJSWTH(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("temp");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		//根据罪犯id获取罪犯信息
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(baseinfo.getName()+LogCommon.WEITUOHAN_JIASHI);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WEITUOHAN_JIASHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.WEITUOHAN_JIASHI+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WEITUOHAN_JIASHI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WEITUOHAN_JIASHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.WEITUOHAN_JIASHI+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WEITUOHAN_JIASHI+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		log.setStatus(status);
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
	
}
