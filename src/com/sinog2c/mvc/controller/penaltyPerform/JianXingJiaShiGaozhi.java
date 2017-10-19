package com.sinog2c.mvc.controller.penaltyPerform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
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

/**
 * @author kexz
 *假释管理告知书
 * 2014-7-17
 */

@Controller
public class JianXingJiaShiGaozhi extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	
	/**
	 * 方法描述：跳转到假释管理告知书罪犯处理页面
	 * @author kexz
	 * 2014-8-13
	 */	
	@RequestMapping("jianXingJiaShiGaozhi")
	public ModelAndView jianXingJiaShiGaozhi(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/chooseCriminal/CommutationAndParoleNotice.jsp"));
	}
	
//	/*
//	 * 方法描述：获取罪犯处理页面列表页数据
//	 */
//	@RequestMapping("/getJianxingList")
//	@ResponseBody
//	public Object getJianxingList(HttpServletRequest request){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//			map.put("tempid", "SZXF_JSGLSXGZS");
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 假释管理告知书进表单
	 * @author kexz
	 * 2014-8-13
	 */
	@RequestMapping("jianXingJiaShiGaozhiHandle")
	public ModelAndView jianXingJiaShiGaozhiHandle(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];//罪犯编号就从数组里取出第一个罪犯编号
		}
		request.setAttribute("crimid", crimid);
		String menuid=request.getParameter("menuid");
		request.setAttribute("menuid",menuid);
		String tempid ="SZXF_JSGLSXGZS";//获取对应表单打印中表单编号
		request.setAttribute("tempid",tempid);
		//json数组
		JSONArray docconent = new JSONArray();
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if(document != null){
			docconent.add(document.getContent()); 
			request.setAttribute("docid", document.getDocid());
		}else{
			SystemUser user = getLoginUser(request);//获取当前登录的用户
			String deptid=user.getDepartid();//根据根据登录用户去获取对应部门id
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);//根据表单编号获取表单
			String xuhao = tbsysDocumentService.getXuHao(template.getTempname(), tempid, null,deptid);//获取序号
			SystemOrganization org=systemOrganizationService.getByOrganizationId(deptid);//通过部门id去找组织机构的信息
			TbprisonerBaseinfo tbinfo=prisonerService.getBasicInfoByCrimid(crimid);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cjicourtname",org.getFullname());//取出组织机构的名字赋值给表单上
			map.put("cbiname", tbinfo.getName());
			map.put("cbigendername",tbinfo.getGender());
			map.put("text13",user.getName());
			map.put("cjcaseidnumber",xuhao);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy");//注意这里的日期转换
			Date date=new Date();
			map.put("cjcaseidcorpbrief",sdf.format(date));
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
			map.put("inscribedate",sdf1.format(date));
			if (template != null) {
				docconent.add(template.getContent());
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		returnResourceMap(request);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/jianXingJiaShiGaozhi.jsp"));
	}
	
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-8-13
	 */
	@RequestMapping("saveGaoZhiShu")
	@ResponseBody
	public int saveGaoZhiShu(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			document.setDepartid(user.getDepartid());
			document.setCrimid(crimid);
			document.setTempid(tempid);
			document.setContent(content);
			document.setIntroduction(crimid+LogCommon.JSGLSXGZS);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JSGLSXGZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JSGLSXGZS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JSGLSXGZS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JSGLSXGZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.JSGLSXGZS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JSGLSXGZS+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		log.setStatus(status);
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
}
