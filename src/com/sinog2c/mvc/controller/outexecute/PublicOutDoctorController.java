package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
 * 保外就医公示
 * @author admin
 *
 */
@Controller
public class PublicOutDoctorController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	
	/**
	 * 方法描述：跳转到罪犯处理列表页
	 * @author liuchaoyang
	 * 2014-7-28  14:50:45
	 */
	@RequestMapping("/publicOutDoctorChoose")
	public ModelAndView publicOutDoctorChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/publicOutDoctorChoose");
	}
	
//	/**
//	 *方法描述：获取罪犯列表数据 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/publicOutDoctorChooseList")
//	@ResponseBody
//	public Object publicOutDoctorChooseList(HttpServletRequest request) throws Exception {
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
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));   
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	
	
	
	/**
	 * 方法描述：跳转到列表页显
	 * @author liuchaoyang
	 * 2014-7-28  14:50:45
	 */
	@RequestMapping(value="publicOutDoctor")
	public ModelAndView chushiHandle(HttpServletRequest request){
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
		returnResourceMap(request);
		return new ModelAndView("outexecute/publicOutDoctor");
	}
	/**
	 * 方法描述：查询表中所有符合条件的数据并按条件分页
	 * 
	 * @author liuchaoyang 2014-7-30 10:11:45
	 */
	@RequestMapping(value = "/getPublicOutDoctorList")
	@ResponseBody
	public HashMap<String, Object> getPublicOutDoctorList(HttpServletRequest request) {
		returnResourceMap(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid");//获取选中的罪犯编号
		String tempid = request.getParameter("tempid");//模版编号
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, crimid, null, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, crimid, null);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 查看、修改保外就医公示时，根据文书Id查询出大字段回显
	 * @author liuchaoyang
	 * 2014-7-28  14:50:45
	 */
	@RequestMapping(value = "/getPublicOutDoctor")
	public ModelAndView getPublicOutDoctor(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addPublicOutDoctor");
	}
	
	/**
	 * 方法描述：根据表单Id到模板信息表查询大字段，查询出表单需要显示的数据，显示到新增页面 
	 * 
	 * @author liuchaoyang 2014-7-28  14:50:45
	 */
	@RequestMapping(value = "/getPublicOutDoctorFrom")
	public ModelAndView getPublicOutDoctorFrom(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String crimid = request.getParameter("crimid");//罪犯编号
		request.setAttribute("crimid", crimid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位名称
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		
		map.put("cbiname", info.getName());
		map.put("criminalid", info.getCrimid());
		map.put("cbigendername", info.getGender());
		map.put("cbinationname", info.getNation());
		map.put("age", DateUtil.getAge(info.getBirthday()));//年龄不会算，先放着
		map.put("cbinativenamedetail", info.getOrigin());
		if(crime!=null){
			map.put("anyouhuizong", crime.getCauseaction());
			map.put("yuanpanxingqi", crime.getRemark());
			map.put("inprisondate", DateUtil.dateFormatForAip(crime.getInprisondate()));
		}
		map.put("text1",org.getName());
		map.put("text10","");//取省局名称，先留着，待定
		map.put("text11", sdf.format(new Date()));	
		map.put("text12", sdf.format(new Date()));	
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addPublicOutDoctor");
	}
	/**
	 * 新增、修改时将保外就医公示保存到大字段
	 * @author liuchaoyang
	 * 2014-7-29 15:37:45
	 */
	@RequestMapping(value = "/savePublicOutDoctorFrom")
	@ResponseBody
	public int savePublicOutDoctorFrom(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator")==null?"":request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			document.setDepartid(user.getDepartid());
			document.setCrimid(crimid);
			document.setTempid(tempid);
			document.setIntroduction(LogCommon.BAOWAIJIUYIGONGSHI);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BAOWAIJIUYIGONGSHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BAOWAIJIUYIGONGSHI+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BAOWAIJIUYIGONGSHI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BAOWAIJIUYIGONGSHI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BAOWAIJIUYIGONGSHI+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BAOWAIJIUYIGONGSHI+LogCommon.EDIT);
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
