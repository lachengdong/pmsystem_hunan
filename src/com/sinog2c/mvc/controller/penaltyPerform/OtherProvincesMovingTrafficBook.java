package com.sinog2c.mvc.controller.penaltyPerform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

/**
 * @author kexz
 *外省保外移交通知书
 * 2014-7-17
 */

@Controller
public class OtherProvincesMovingTrafficBook extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	/**
	 * 跳转到罪犯处理页面
	 */
	@RequestMapping("/toOtherProvincesForTransfer")
	public ModelAndView toOtherProvincesForTransfer(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
			return new ModelAndView("chooseCriminal/otherProvincesForTransferChoose");
	}
//	/**
//	 * 得到罪犯处理信息
//	 */
//	@RequestMapping("/getOtherProvincesForTransferList")
//	@ResponseBody
//	public Object getOtherProvincesForTransferList(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			String condition = prisonerService.getTheQueryCondition("10157");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			String tempid="GDSJ_JWZX_WSYJTZ";
//			map.put("key", key);
//			map.put("tempid", tempid);
//			//map.put("departId", getLoginUser(request).getOrgid());
//			map.put("condition", condition);
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	/**
	 * 处理、批量处理数据
	 */
	@RequestMapping("/otherProvincesForTransferAdd")
	public ModelAndView otherProvincesForTransferAdd(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];//罪犯编号就从数组里取出第一个罪犯编号
		}
		request.setAttribute("crimid", crimid);
		String tempid ="GDSJ_JWZX_WSYJTZ";
		request.setAttribute("tempid",tempid);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid,null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			Date date = new Date();
			HashMap<String, Object> map = new HashMap<String, Object>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat year=new SimpleDateFormat("yyyy");
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
			String xuhao = tbsysDocumentService.getXuHao(null, tempid, crimid, departid);
			TbprisonerBaseinfo tbinfo=prisonerService.getBasicInfoByCrimid(crimid);//罪犯基本信息表
			TbprisonerBaseCrime crime=prisonerService.getCrimeByCrimid(crimid);//犯人犯罪处罚信息表
			map.put("cbiname", tbinfo.getName()==null?"":tbinfo.getName());
			map.put("cbigendername",tbinfo.getGender()==null?"":tbinfo.getGender());
			map.put("szjy", org.getName());
			map.put("text1", year.format(date));
			map.put("text2", xuhao);
			map.put("text8", sdf.format(date));
			if(crime!=null){
				map.put("zhuxing", crime.getMaincase());
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/OtherProvincesMovingTrafficBookAdd");
	}
	
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveOtherProvincesForTransfer")
	@ResponseBody
	public int saveOtherProvincesForTransfer(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String introduction = crimid+LogCommon.WSBWYJTZS;
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(docid==null || "".equals(docid)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WSBWYJTZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.WSBWYJTZS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WSBWYJTZS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WSBWYJTZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.WSBWYJTZS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WSBWYJTZS+LogCommon.EDIT);
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
