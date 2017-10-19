package com.sinog2c.mvc.controller.prisoner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sinog2c.util.common.StringNumberUtil;


/**
 *减刑假释>监狱文书> 假释告知书
 * @author wangxf
 *
 */
@Controller
public class paroleInformController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private PrisonerService prisonerService;
	
	//跳转到罪犯处理页面
	@RequestMapping(value = "paroleInforchoose")
	public ModelAndView paroleInforChoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/paroleInformChoose");
	}
//	/**
//	 * 获取罪犯列表页数据
//	 * @author kexz
//	 * @return
//	 */
//	@RequestMapping("/getParoleInforList")
//	@ResponseBody
//	public Object getJxList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			String tempid = request.getParameter("tempid");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("tempid", tempid);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	/**
	 * 假释告知书新增页面
	 * @author kexz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("paroleInform")
	public  ModelAndView paroleInform(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		request.setAttribute("crimid", crimid);
		SystemUser user = getLoginUser(request);
		String departId = user.getDepartid();
		String menuid=request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		JSONArray docconent = new JSONArray();
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (!StringNumberUtil.isNullOrEmpty(document)) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			DateFormat format = new SimpleDateFormat("yyyy");
			String year = format.format(new Date());
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);
			String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, departId);
			SystemOrganization org = systemOrganizationService.getByOrganizationId(departId);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departId);
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map map = new HashMap();
			map.put("text1", "假释");
			map.put("text10", xuhao);
			map.put("cbiname", info.getName());
			map.put("text6", org.getShortname());
			map.put("text7", format.format(new Date()));
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("menuid",menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/paroleInformAdd");
	} 
	/**
	 * 保存
	 * @author kexz
	 * @return
	 */
	@RequestMapping("saveparoleInform")
	@ResponseBody
	public int saveparoleInform(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		String yearnum = "";
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		TbsysDocument document = new TbsysDocument();
		String crimid = request.getParameter("crimid");
		String noteinfo = request.getParameter("noteinfo");//获取大字段
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());//根据表单编号获取表单
		if (noteinfo != null) {
			Map<String,String> map = new HashMap();
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map)list.get(0);
				yearnum = map.get("text7")+"年第"+map.get("text10")+"号";
			}
		}
		String introduction = crimid+yearnum;
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction+template.getTempname());
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JIASHIGAOZHISHU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JIASHIGAOZHISHU+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JIASHIGAOZHISHU+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else{
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JIASHIGAOZHISHU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.JIASHIGAOZHISHU+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JIASHIGAOZHISHU+LogCommon.EDIT);
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
