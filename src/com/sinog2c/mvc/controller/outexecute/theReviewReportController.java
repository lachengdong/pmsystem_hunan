package com.sinog2c.mvc.controller.outexecute;

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
import com.gkzx.common.LogCommon;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 类描述：省局审查报告
 * @author liuchaoyang
 * 下午02:18:21
 */
@Controller
public class theReviewReportController  extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemOrganizationService orgService;
	@Resource
	public SystemLogService logService;
	@Resource
	private TbxfSentencealterationService tbxfSentencealterationService ;
	
	
	/**
	 * @author kexz
	 *方法描述：跳转到省局审查报告数据列表页
	 * 2014-8-15
	 */
	@RequestMapping(value = "theReviewReportList")
	public ModelAndView theReviewReportList(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("outexecute/theReviewReportList");
	}
	
	/**
	 * 方法描述：列表页面(省局)
	 * 2014-8-12
	 * @author kexz
	 */
	@RequestMapping(value="/getTheReviewReportList")
	@ResponseBody
	public HashMap<String, Object> getTheReviewReportList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,null,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,null, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * @author kexz
	 *(省局)
	 * 2014-8-12
	 */
	@RequestMapping(value="toTheReviewReportAdd")
	public ModelAndView toTheReviewReportAdd(HttpServletRequest request){
		returnResourceMap(request);//查询资源里的按钮
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String menuid=request.getParameter("menuid");
		Map<String,Object> talmap=new HashMap<String,Object>();
		talmap.put("orgid", user.getOrgid());
		talmap.put("nodiid", "1");
		int total = tbxfSentencealterationService.getByjyCaseCount(talmap);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org = orgService.getByOrganizationId(departid);
		//org = orgService.getByOrganizationId(org.getPorgid());//根据部门id查询省局名称,
		String contentext = systemModelService.getRecommendationContent("9721", user, request).replaceAll("\n", "");//9721省局审查报告模板id
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("shengju",org.getName());
		map.put("text1","");
		map.put("text2", total);
		map.put("contentext", contentext);
		map.put("text3",DateUtil.dateFormatForAip(new Date()));
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("menuid",menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addTheReviewReport");
	}

	/**
	 * 查看、修改时，根据文书Id查询出大字段回显
	 * @author kexz 
	 * 2014-8-12
	 */
	@RequestMapping(value="editTheReviewReport")
	public ModelAndView editTheReviewReport(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
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
		return new ModelAndView("aip/addTheReviewReport");
	}

	/**
	 * 修改/保存大字段
	 * @author 
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value="/saveTheReviewReport")
	@ResponseBody
	public int saveTheReviewReport(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String introduction = "";
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (template != null) introduction = template.getTempname();
		if (noteinfo != null) {
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map)list.get(0);
				introduction += map.get("text1")+map.get("text2")+"件";
			}		
		}
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JWZXSCBG_SHENGJU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JWZXSCBG_SHENGJU+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JWZXSCBG_SHENGJU+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JWZXSCBG_SHENGJU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.JWZXSCBG_SHENGJU+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.SHENGJUSHENCHABAOGAO+LogCommon.EDIT);
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
