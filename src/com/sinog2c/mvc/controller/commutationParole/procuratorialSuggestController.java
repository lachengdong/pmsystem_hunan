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
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 类描述：检察建议书
 * @author liuchaoyang
 * 下午06:20:34
 */
@Controller
public class procuratorialSuggestController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	/**
	 * 跳转 检察建议书列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/procuratorialSuggest")
	public ModelAndView procuratorialSuggest(HttpServletRequest request) {
		returnResourceMap(request);//资源对象
		return new ModelAndView("commutationParole/procuratorialSuggest");
	}
	
	/**
	 * 方法描述：查询表中所有符合条件的数据并按条件分页
	 * 
	 * @author liuchaoyang 2014-7-29 17:11:45
	 */
	@RequestMapping(value = "/getProcuratorialSuggestList")
	@ResponseBody
	public HashMap<String, Object> getProcuratorialSuggestList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String departid =getLoginUser(request).getDepartid();//获取当前用户所在部门id
		String tempid = request.getParameter("tempid");//模版编号
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		// 查询出所有数据集合
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, null, departid, sortField, sortOrder);
		// 查询数据总数
		int count = tbsysDocumentService.getCount(key, tempid, null, departid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 新增、修改时将检察建议书保存到大字段
	 * @author liuchaoyang
	 * 2014-7-29 17:11:45
	 */
	@RequestMapping(value = "/saveProcuratorialSuggest")
	@ResponseBody
	public int saveProcuratorialSuggest(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		String yearnum = "";
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		TbsysDocument document = new TbsysDocument();
		String noteinfo = request.getParameter("noteinfo");//获取大字段
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String crimid=request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位名称
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);//根据表单编号获取表单
		if (noteinfo != null) {
			Map<String,String> map = new HashMap();
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map)list.get(0);
				yearnum = map.get("year")+"年第"+map.get("xuhao")+"号发往"+map.get("fwdw");
			}
		}
		String introduction = org.getName()+yearnum+template.getTempname();
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JIACHAJIANYISHU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JIACHAJIANYISHU+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JIACHAJIANYISHU+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setIntroduction(introduction);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JIACHAJIANYISHU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JIACHAJIANYISHU+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JIACHAJIANYISHU+LogCommon.ADD);
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
	 * 查看、修改检察建议书时，根据文书Id查询出大字段回显
	 * @author liuchaoyang
	 * 2014-7-29  11:06:45
	 */
	@RequestMapping(value = "/getProcuratorialSuggest")
	public ModelAndView getProcuratorialSuggest(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/addProcuratorialSuggest");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/deleteProcuratorialSuggest")
	@ResponseBody
	public int deleteProcuratorialSuggest(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JIACHAJIANYISHU+LogCommon.OPERATE);
		log.setOpaction(LogCommon.EDIT);
		log.setOpcontent(LogCommon.JIACHAJIANYISHU+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JIACHAJIANYISHU+LogCommon.DEL);
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
	 * 方法描述：根据表单Id到模板信息表查询大字段，查询出表单需要显示的数据，显示到新增页面 
	 * 
	 * @author liuchaoyang 2014-7-29 17:11:45
	 */
	@RequestMapping(value = "/getProcuratorialSuggestFrom")
	public ModelAndView getProcuratorialSuggestFrom(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, departid);//获取序号	
		map.put("year",year);
		map.put("xuhao", xuhao);
		map.put("lrsj", sdf.format(new Date()));
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addProcuratorialSuggest");
	}

}
