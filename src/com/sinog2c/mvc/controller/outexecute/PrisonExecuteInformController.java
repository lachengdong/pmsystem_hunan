package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;

/**
 * 功能描述：暂予监外执行告知书
 * @author liuchaoyang
 * 下午08:30:02
 */
@Controller
public class PrisonExecuteInformController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private  ChooseCriminalService  chooseCriminalService;
	
	/**
	 * 跳转到罪犯处理页面
	 * @return
	 */
	@RequestMapping("/prisonExecuteInformChoose")
	public ModelAndView chooseCriminal(){
		return new ModelAndView("chooseCriminal/PrisonExecuteInformNotifyChoose");
  }
	
	
	/**
	 * 方法描述：选择罪犯后跳转到数据列表
	 * 
	 * @author liuchaoyang 2014-7-29 20:11:45
	 */
	@RequestMapping("/prisonExecuteInformNotify")
	public ModelAndView prisonExecuteDecisionNotify(HttpServletRequest request){
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
		return new ModelAndView("outexecute/PrisonExecuteInformNotifyList");
	}
	
	
	
//	 //获取罪犯列表
//	@RequestMapping("/getPrisonExecuteInformNotifyChoose")
//	@ResponseBody
//	public Object getPrisonExecuteInformNotifyChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
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
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//			
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
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
	
	@RequestMapping(value = "/getPrisonExecuteInformNotifyList")
	@ResponseBody
	public HashMap<String, Object> getTbsysDocumentList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String departid =getLoginUser(request).getDepartid();
		String key = request.getParameter("key");
		String sortField = request.getParameter("optime");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid, departid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, departid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 方法描述：新增显示数据
	 * 
	 * @author liuchaoyang 2014-7-26 18:52:45
	 */
	@RequestMapping("/getPrisonExecuteInformNotifyFrom")
	public ModelAndView getPrisonExecuteDecisionNotifyFrom(HttpServletRequest request){
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		String tempid = "JWZX_ZYJWZXGZS";
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		HashMap<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);
		map.put("cbiname", info.getName());
		map.put("text11", sdf.format(new Date()));
		//读取 jyconfig 内容
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA)==null?"":jyconfig.getProperty(GkzxCommon.NINGXIA);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		if(!"".equals(provincecode)&&"4400".equals(provincecode)){
			map.put("text10", GkzxCommon.GDS);
		}
		
		//查询监狱名称
		String deparName = systemModelService.getDepartNameByDepartid(deptid);
		//查询模板内容
		TbsysTemplate tempMap = systemModelService.getTemplateAndDepartid("NX_ZYJWZX_GZS", deptid);
		/**
		 * 表单节点text12,text13,text14,text15,text16,text17,text18属于宁夏特有
		 * ，请不要给这个节点赋值（@author mushuhong）
		 */
		
		if (ningxia.contains(deptid)) {
			map.put("text12", GkzxCommon.NX_ZUIFAN+""+info.getName());
			Date datetime = new Date();
			String contentString = tempMap.getContent();
			contentString = contentString.replace(GkzxCommon.NX_SHENGFEN, user.getAddress());
			contentString = contentString.replace(GkzxCommon.NX_NOWDATE, (datetime.getYear()+1900)+GkzxCommon.NX_YEAR+(1+datetime.getMonth())+GkzxCommon.NX_MONTH+datetime.getDate()+GkzxCommon.NX_DAY);
			map.put("text13", "   "+contentString);
			map.put("text14", deparName+"("+GkzxCommon.NX_GONGZHANG+")");
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/addPrisonExecuteInformNotify");
	}

	/**
	 * 查看、修改暂予监外执行告知书时，根据文书Id查询出大字段回显
	 * @author liuchaoyang
	 * 2014-7-28  14:50:45
	 */
	@RequestMapping(value = "/getPrisonExecuteInformNotify")
	public ModelAndView getPrisonExecuteDecisionNotify(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		String menuid=request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/addPrisonExecuteInformNotify");
	}
	
	
	/**
	 * 新增、修改时将暂予监外执行告知书保存到大字段
	 * @author liuchaoyang
	 * 2014-7-29 15:37:45
	 */
	@RequestMapping(value = "/savePrisonExecuteInformNotify")
	@ResponseBody
	public int savePrisonExecuteDecisionNotify(HttpServletRequest request){
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
		String introduction = LogCommon.ZYJWZXGZS;//这个地方先这样写，以后再拼接字符串
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+info.getName()+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(introduction+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(introduction+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.EDIT+LogCommon.EVENT);
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
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-28  14:50:45
	 */
	@RequestMapping(value = "/deletePrisonExecuteInformNotify")
	@ResponseBody
	public int deletePrisonExecuteDecisionNotify(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String introduction=LogCommon.ZYJWZXGZS;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(introduction+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(introduction+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(introduction+LogCommon.DEL+LogCommon.EVENT);
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
