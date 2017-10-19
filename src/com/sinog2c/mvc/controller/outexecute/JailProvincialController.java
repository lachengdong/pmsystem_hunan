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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
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
import com.sinog2c.util.common.DateUtil;

/**
 * 类描述：审查报告（报省局）
 * @author liuchaoyang
 * 下午07:58:54
 */
@Controller
public class JailProvincialController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;

	/**
	 * @author kexz
	 *跳转审查报告(报省局)罪犯列表页面
	 * 2014-8-15
	 */
	@RequestMapping(value = "jailProvincialChoose")
	public ModelAndView jailProvincialChoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/jailReviewProvincialChoose");
	}
	
//	/**
//	 * 方法描述：获取列表页面(报省局)数据
//	 * 2014-8-12
//	 * @author kexz
//	 */
//	@RequestMapping(value="/getJailProvincialChoose")
//	@ResponseBody
//	public HashMap<String, Object> getJailProvincialChoose(HttpServletRequest request) {
//		HashMap<String, Object> resultmap = new HashMap<String, Object>();
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
	 * @author kexz
	 *跳转到数据列表页面(报省局)
	 * 2014-8-15
	 */
	@RequestMapping(value = "/toJailProvincial")
	public ModelAndView toJailProvincial(HttpServletRequest request) {
		String name = request.getParameter("name")==null?"":request.getParameter("name");
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
	    return new ModelAndView("outexecute/jailReviewProvincial");
	}

	/**
	 * 方法描述：获取列表页面(报省局)数据
	 * 2014-8-12
	 * @author kexz
	 */
	@RequestMapping(value="/getJailReviewProvinciaList")
	@ResponseBody
	public HashMap<String, Object> getJailReviewProvinciaList(
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
	 * 方法描述：新增审查报告（报省局）
	 * @author kexz
	 * 2014-8-12
	 */
	@RequestMapping("jailReviewProvincialAdd")
	public ModelAndView jailReviewProvincialAdd(HttpServletRequest request){
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		String tempid = request.getParameter("tempid");
		String menuid=request.getParameter("menuid");
		String crimid=request.getParameter("crimid");
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, departid);
		
		String delflg = systemModelService.getRecommendationContent("9688", user, request).replaceAll("\n", "");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("text1",year);
		map.put("text2",xuhao);
		map.put("text11patch",user.getPrison().getFullname());
		map.put("text12",user.getPrisonShortName());
		map.put("delflg",delflg);
		map.put("text7",DateUtil.dateFormatForAip(new Date()));
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("menuid",menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("name",baseinfo.getName());
		request.setAttribute("crimid",crimid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/jailReviewProvincialAdd");
	}

	/**
	 * 查看、修改时，根据文书Id查询出大字段回显(省局)
	 * @author kexz 
	 * 2014-8-12
	 */
	@RequestMapping(value="/editJailReviewProvincia")
	public ModelAndView editJailReviewProvincia(HttpServletRequest request) {
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
		return new ModelAndView("aip/jailReviewProvincialAdd");
	}

	/**
	 * 根据文书Id删除数据(省局)
	 * @author kexz
	 * 2014-8-12
	 */
	@RequestMapping(value="deleteJailReviewProvincia")
	@ResponseBody
	public int deleteJailReviewProvincia(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JWZXSCBG_SHENGJU+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.JWZXSCBG_SHENGJU+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JWZXSCBG_SHENGJU+LogCommon.DEL);
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
	 * 修改/保存大字段(省局)
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value="saveJailReviewProvincia")
	@ResponseBody
	public int saveJailReviewProvincia(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		Date date = new Date(); 
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
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
			document.setIntroduction("罪犯"+baseinfo.getName()+LogCommon.JWZXSCBG_SHENGJU);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		return countnum;
	}
}
