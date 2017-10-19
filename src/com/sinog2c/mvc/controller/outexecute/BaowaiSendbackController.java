package com.sinog2c.mvc.controller.outexecute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
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
 * 保外遣送
 * @author wangxf
 *
 */
@Controller
public class BaowaiSendbackController extends ControllerBase {
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private TbsysDocumentService sysDocumentService;
	@Resource
	public SystemLogService logService;
	
	/**
	 * 跳转保外遣送选择罪犯页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/baowaiSendback")
	public ModelAndView sentenceChange(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/baowaiSendbackChoose");
	}
	
//	/**
//	 * 获取罪犯列表
//	 * 
//	 * @author liuxin
//	 * @param request
//	 * @return resultmap
//	 */
//	@RequestMapping(value = "/getBaowaiSendbackBasicInfoList")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
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
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	/**
	 * 跳转保外遣送选列表页面
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="/baowaiSendbackList")
	public ModelAndView toBaowaiSendback(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
        String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
        String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
        String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
        if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
            String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
            request.setAttribute("id", id);
            String[] ids = id == null ? null:id.split(",");
            String[] idnames = idname == null?null:idname.split(",");
            crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
            name = idnames == null?"":idnames[0];
        }
        request.setAttribute("idname", idname);
        request.setAttribute("crimid", crimid);
        request.setAttribute("name", name);
        request.setAttribute("tempid", tempid);
        request.setAttribute("menuid", menuid);
		return new ModelAndView("outexecute/baowaiSendback");
		
	}
	/**
	 * 根据罪犯编号获取列表
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/getBaowaiSendbackByCrimid")
	@ResponseBody
	public HashMap<String, Object> getBowaiSendbackByCrimid(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = sysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, crimid ,null, sortField, sortOrder);
		int count = sysDocumentService.getCount(key, tempid, crimid, null);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 跳转刑期保外遣送新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="/baowaiSendbackAdd")
	public ModelAndView toBaowaiSendbackAdd(HttpServletRequest request){
		returnResourceMap(request);
		returnResourceMap(request);
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		try {
			TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);//根据罪犯id获取罪犯信息
			TbprisonerBaseCrime baseCrime =  prisonerService.getCrimeByCrimid(crimid);
			map.put("criminalid", crimid);
			map.put("cbiname", baseinfo.getName());
			map.put("cbigendername", baseinfo.getGender());
			map.put("age", DateUtil.getAge(baseinfo.getBirthday()));
//			map.put("cbinativenamedetail", baseinfo.getOriginplacearea());ORIGINPLACEADDRESS
			map.put("cbinativenamedetail", baseinfo.getOriginplaceaddress());
			map.put("cbihomeaddress", baseinfo.getAddressdetail());
			if(baseCrime != null){
				//入监日期   
				map.put("criofficiallyplacedate", DateUtil.dateFormatForAip(baseCrime.getInprisondate()));
				//原判刑期					
				String punishmenttype = baseCrime.getPunishmenttype();
				if("死缓".equals(punishmenttype)){
					map.put("yuanpanxingqi", "死刑缓期两年执行" );
				}else if("无期徒刑".equals(punishmenttype)){
					map.put("yuanpanxingqi", "无期徒刑");
				}if("有期徒刑".equals(punishmenttype)){
					map.put("yuanpanxingqi","有期徒刑" + baseCrime.getPunishmentyear() + "年");//(月日)
				}
				//已执行刑期
				map.put("zxxq","");
				//刑期起止
				String xingqiqizhi = "自" + DateUtil.dateFormat(baseCrime.getSentencestime()) + "起至" + DateUtil.dateFormat(baseCrime.getSentenceetime())+ "止";
				map.put("xianxingqiqizhi", xingqiqizhi);//?哪张表,应该是最后一次变更后的
				//刑期变动
				map.put("jxmx", ""); //刑期变动表
			}
		} catch (Exception e) {
		}
		
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		return new ModelAndView("aip/baowaiSendbackAdd");
	}
	/**
	 * 修改/保存大字段
	 * @author liuxin
	 */
	@RequestMapping(value = "/saveBaowaiSendback")
	@ResponseBody
	public int saveBaowaiSendback(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		if(GkzxCommon.NEW.equals(operator)){
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(name+LogCommon.BAOWAIQIANSONG);
			document.setCrimid(crimid);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BAOWAIQIANSONG+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BAOWAIQIANSONG+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BAOWAIQIANSONG+LogCommon.ADD);
			countnum = sysDocumentService.saveTbsysDocument(document);
		}else if(GkzxCommon.EDIT.equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setDepartid(user.getDepartid());
			document.setCrimid(crimid);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BAOWAIQIANSONG+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BAOWAIQIANSONG+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BAOWAIQIANSONG+LogCommon.EDIT);
			countnum = sysDocumentService.updateTbsysDocument(document);
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
	 * 查看、修改刑期变动时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/baowaiSendbackLook")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		request.setAttribute("docid", docid);
		request.setAttribute("crimid", crimid);
		TbsysDocument document = sysDocumentService.getTbsysDocument(docid, null, null,null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/baowaiSendbackAdd");
	}
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/baowaiSendbackDelete")
	@ResponseBody
	public int baowaiSendbackDelete(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = sysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.BAOWAIQIANSONG+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.BAOWAIQIANSONG+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.BAOWAIQIANSONG+LogCommon.DEL);
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
