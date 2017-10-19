package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
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
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 纠正违法通知书
 * @author liuxin
 * 2014-7-28 09:36:20
 */
@Controller
public class CorrectIllegalNoticeController extends ControllerBase{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	
	
	/**
	 * 跳转纠正违法通知书列表页
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/correctIllegalNotice")
	public ModelAndView toCorrectIllegalNoticePage(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("commutationParole/correctIllegalNotice");
	}
	/**
	 * 方法描述：获取列表页面数据
	 * @author 
	 */
	@RequestMapping("/getCorrectIllegalNoticeTableList")
	@ResponseBody
	public HashMap<String, Object> getTbsysDocumentList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);	
		//获取当前登录的用户
		String deptid=user.getDepartid();
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		String sortField = request.getParameter("optime");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, "",deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, "", deptid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 跳转纠正违法通知书新增页面
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/correctIllegalNoticeAdd")
	public ModelAndView toCorrectIllegalNoticeAdd(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String departid=getLoginUser(request).getDepartid();//根据用户Id获取所在部门Id
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org = systemOrganizationService.getByOrganizationId(departid);//通过部门id去找所在
		String city = org.getCity()==null?"":org.getCity();
		
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String year = formatter.format(date);
		String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, departid);
		map.put("text11patch", city + "人民检察院");
		map.put("text12", org.getShortname());
		map.put("year", year);
		map.put("xuhao", xuhao);
		map.put("text10patch", city + "检察院监所检察处");
		map.put("lrsj", DateUtil.dateFormatForAip(date));
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		return new ModelAndView("aip/correctIllegalNoticeAdd");
	}
	/**
	 * 修改/保存大字段
	 * @author liuxin
	 */
	@RequestMapping(value = "/saveCorrectIllegalNotice")
	@ResponseBody
	public int saveCorrectIllegalNotice(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		String yearnum = "";
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String departid=user.getDepartid();//获取当前登录的用户
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		SystemOrganization org = systemOrganizationService.getByOrganizationId(departid);//通过部门id去找所在单位
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);//根据表单编号获取表单
		Map<String,String> map = new HashMap();
		ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
		if(list!=null && list.size()>0){
			map = (Map)list.get(0);
			yearnum = map.get("year")+"年第"+map.get("xuhao")+"号发往"+map.get("fwdw");
		}
		String introduction = org.getName()+yearnum+template.getTempname();
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(org.getOrgid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JIUZHENGWEIFATZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JIUZHENGWEIFATZS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JIUZHENGWEIFATZS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else{
			document.setDocid(Integer.parseInt(docid));
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JIUZHENGWEIFATZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JIUZHENGWEIFATZS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JIUZHENGWEIFATZS+LogCommon.ADD);
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
	/**
	 * 查看、修改纠正违法通知书时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/correctIllegalNoticeLook")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null,"");
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/correctIllegalNoticeAdd");
	}
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/correctIllegalNoticeDelete")
	@ResponseBody
	public int correctIllegalNoticeDelete(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JIUZHENGWEIFATZS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.EDIT);
		log.setOpcontent(LogCommon.JIUZHENGWEIFATZS+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JIUZHENGWEIFATZS+LogCommon.DEL);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
}
