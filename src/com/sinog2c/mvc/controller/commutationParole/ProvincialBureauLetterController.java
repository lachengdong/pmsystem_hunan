package com.sinog2c.mvc.controller.commutationParole;

import java.net.URLDecoder;
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
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 省局送卷函
 * @author liuxin
 * 2014-7-28 10:39:48
 */
@Controller
@RequestMapping("/ProvincialBureauLetter")
public class ProvincialBureauLetterController extends ControllerBase {
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private PrisonerService prisonerService;
	
	
	/**
	 * 罪犯处理页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/provincialBureauLetterChoose.page")
	public ModelAndView provincialBureauLetterChoose(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/provincialBureauLetterChoose");
	}
//	/**
//	 * 获取所有罪犯列表
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getProvincialBureauLetterChoose")
//	@ResponseBody
//	public Object getProvincialBureauLetterChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			key = URLDecoder.decode(key,"UTF-8");
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");//取得模板编号
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));     
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("tempid", tempid);
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
	 * 跳转省局送卷函列表页面
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/provincialBureauLetter.page")
	public ModelAndView provincialBureauLetter(HttpServletRequest request){
		String tempid = request.getParameter("tempid");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("menuid", menuid);
		request.setAttribute("name", name);
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		returnResourceMap(request);
		return new ModelAndView("commutationParole/provincialBureauLetterList");
	}
	
	/**
	 * 方法描述：获取省局送卷函列表数据
	 * 2014-7-29
	 * @author kexz
	 */
	@RequestMapping("/getProvincialBureauLetterInfoList.json")
	@ResponseBody
	public HashMap<String, Object> getProvincialBureauLetterInfoList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, null, tempid,crimid,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(null, tempid,null, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * @author kexz
	 *显示省局送卷函新增表单
	 * 2014-7-29
	 */
	@RequestMapping("/provincialBureauLetterAdd.page")
	public ModelAndView provincialBureauLetterAdd(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		returnResourceMap(request);//查询资源里的按钮
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);//基本信息
		TbprisonerBaseCrime tbprisonerBaseCrime =prisonerService.getCrimeByCrimid(crimid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null,departid);//获取序号
		HashMap<String, Object> map=new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date date=new Date();
		map.put("text1",year);
		map.put("text2",xuhao);
		map.put("text3",tbprisonerBaseCrime.getCaseorgshort());
		map.put("text4",tbprisonerBaseCrime.getJudgmentname());
		map.put("cbiname",baseInfo.getName());
		map.put("text5",sdf.format(date));
		map.put("text10",org.getName()+template.getTempname());//拼接标题
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/provincialBureauLetterAdd");
	}
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveprovincialBureauLetter.json")
	@ResponseBody
	public int saveprovincialBureauLetter(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		String yearnum = "";
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbsysDocument document = new TbsysDocument();
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			Map<String,String> map = new HashMap();
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map)list.get(0);
				yearnum = map.get("text1")+"年第"+map.get("text2")+"号"+map.get("cbiname");
			}
		}
		String introduction = org.getName()+yearnum+template.getTempname();
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(user.getDepartid());
			document.setCrimid(crimid);
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.SJSONGJUANHAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.SJSONGJUANHAN+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.SJSONGJUANHAN+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else{
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.SJSONGJUANHAN+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.SJSONGJUANHAN+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.SJSONGJUANHAN+LogCommon.EDIT);
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
	 * 查看、修改省局送卷函时，根据文书Id查询出大字段回显
	 * @author kexz 
	 * 2014-7-29
	 */
	@RequestMapping(value = "/toprovincialBureauLetterEdit.page")
	public ModelAndView toprovincialBureauLetterEdit(HttpServletRequest request) {
		returnResourceMap(request);//查询资源里的按钮
		HashMap map = new HashMap();
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/provincialBureauLetterAdd");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/deleteprovincialBureauLetter.json")
	@ResponseBody
	public int deleteInfo(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.SJSONGJUANHAN+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.SJSONGJUANHAN+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.SJSONGJUANHAN+LogCommon.DEL);
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
