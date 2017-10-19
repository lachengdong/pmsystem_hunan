package com.sinog2c.mvc.controller.commutationParole;

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
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 减刑假释 案件检查意见
 * @author hzl
 */
@Controller
public class CaseCheckOpinionController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	
	
	/**
	 * 跳转 案件检查意见列表页
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCaseCheckOpinionPage")
	public ModelAndView toCaseCheckOpinionPage(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		return new ModelAndView("commutationParole/caseCheckOpinion");
	}
	
	/**
	 * 方法描述：获取列表页面数据
	 * 2014-7-31
	 * @author kexz
	 */
	@RequestMapping("/gettoCheckOpinionPageList")
	@ResponseBody
	public HashMap<String, Object> gettoCheckOpinionPageList(HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		SystemUser user = getLoginUser(request);
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,null, user.getDepartid(), sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, null,user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 方法描述：跳转到案件检查意见新增页面
	 * @author kexz
	 * 2014-7-25
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toCheckOpinionPageAdd")
	public ModelAndView toCheckOpinionPageAdd(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		String tempid = request.getParameter("tempid");//获取对应表单打印中表单编号
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据根据登录用户去获取对应部门id
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);//根据表单编号获取表单
		Map map=new HashMap();
		HashMap picimap = systemModelService.getCuryearBatch(departid);
		if(!StringNumberUtil.isNullOrEmpty(picimap)){
			picimap = (HashMap) MapUtil.turnKeyToLowerCase(picimap);
			String curyear = picimap.get("curyear").toString();
			String batch = picimap.get("batch").toString();
			map.put("listyear",curyear);
			map.put("listtime",batch);
			map.put("jianyuname",user.getOrganization().getFullname()+":");
			map.put("name",user.getOrganization().getCity());
			map.put("text2", curyear + "年度第" + batch + "批次减刑假释案件检察意见");
			map.put("text3", user.getOrganization().getText1() + "：");
			map.put("text5", user.getOrganization().getText2() + "监所监察处");
		}
		SimpleDateFormat sidf=new SimpleDateFormat("yyyyMMdd");
		map.put("lrsj", sidf.format(new Date()));
		map.put("text1", user.getOrganization().getText2());
		map.put("text6", sidf.format(new Date()));
		JSONArray docconent = new JSONArray();
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/toCheckOpinionPageAdd");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/deletetoCheckOpinionPage")
	@ResponseBody
	public int deletetoCheckOpinionPage(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.OPERATE);
		log.setOpaction(LogCommon.EDIT);
		log.setOpcontent(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.DEL);
		if(result == 1) status = 1;//如果删除成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}	

	/**
	 * 查看、修改案件检查意见时，根据文书Id查询出大字段回显
	 * @author kexz 
	 * 2014-7-29
	 */
	@RequestMapping(value = "/editcaseCheckOpinion")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/toCheckOpinionPageAdd");
	}
	
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveTocaseCheck")
	@ResponseBody
	public int saveTocaseCheck(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		String pici = "";
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			if (noteinfo != null) {
				Map<String,String> map = new HashMap();
				ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
				if(list!=null && list.size()>0){
					map = (Map)list.get(0);
//					pici = map.get("listyear")+"年第"+map.get("listtime")+"批";
					pici = map.get("listyear")+"年";
				}
			}
			String introduction = org.getName()+pici+template.getTempname();//文书简介
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ANJIANJIANCHAYIJIAN+LogCommon.EDIT);
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
	
}
