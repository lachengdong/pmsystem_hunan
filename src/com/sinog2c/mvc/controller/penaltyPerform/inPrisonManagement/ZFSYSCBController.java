package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

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
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 类描述：新犯入监
 * @author liuchaoyang
 * 下午03:25:23
 */
@Controller
public class ZFSYSCBController extends ControllerBase{
	@Resource
	private PrisonerService prisonerService;
	@Resource
	public SystemLogService logService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Resource
	public SystemOrganizationService orgService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	private SentenceAlterationService sentenceAlterationService;
	
	
	/**
	 * 方法描述：跳转到新犯入监列表
	 * @author kexz
	 * @return
	 */
	@RequestMapping(value = "/ZFSYSCBList")
	public ModelAndView ZFSYSCBList(HttpServletRequest request) {
		//资源对象
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/ZFSYSCB");
	}
	
	/**
	 * 方法描述：查询表中所有符合条件的数据并按条件分页
	 * 
	 * @author liuchaoyang 2014-7-29 17:11:45
	 */
	@RequestMapping(value = "/getZFSYSPBList")
	@ResponseBody
	public HashMap<String, Object> getZFSYSPBList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		SystemUser user = getLoginUser(request);;//获取当前用户所在部门id
		String tempid = request.getParameter("tempid");//模版编号
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		paramap.put("orgid", user.getOrgid());
		paramap.put("tempid", tempid);
		paramap.put("key", key);
		paramap.put("sortField", sortField);
		paramap.put("sortOrder", sortOrder);
		paramap.put("start", start);
		paramap.put("end", end);
		
		// 查询出所有数据集合
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList2(paramap);
		// 查询数据总数
		int count = tbsysDocumentService.getCount2(paramap);
		result.put("total", count);
		result.put("data", list);
		return result;
	}

	/**
	 * 表单新增、修改或查看
	 * @param request
	 * @return
	 */
	@RequestMapping("/ZFSYSCBAdd")
	public ModelAndView ZFSYSCBAdd(HttpServletRequest request){
		this.returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		Map<String, Object> selectmap = new HashMap<String, Object>();
		if(StringNumberUtil.notEmpty(docid)){
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
			if (document != null) {
				docconent.add(document.getContent());
			}
			request.setAttribute("formcontent", docconent.toString());
			returnResourceMap(request);
		}else{
			SystemUser user = getLoginUser(request);//获取当前登录的用户
			String departid=user.getDepartid();//根据用户Id获取所在部门Id
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			String crimid = prisonerService.getCrimid(departid);
			SystemOrganization org = orgService.getPrisonarealevelByPorgId(departid);
			//如果有入监监区，默认显示第一个；没有入监监区，显示所有监区列表
			if(org!=null){
			    map.put("jianqu", org.getName());
			}else{
				selectmap.put("jianqu", orgService.getcodeValue(user));//监区选择下拉列表
			}
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			map.put("danganhao", crimid.substring(crimid.length()-6, crimid.length()));//档案号可编辑
			map.put("bianhao",crimid);//罪犯编号可编辑
			map.put("text35", user.getName());
			map.put("CheckBox", 127);
			map.put("text36", DateUtil.dateFormatForAip(new Date()));
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/ZFSYSCBAdd");
	}
	/**
	 * 新增、修改时将收押审查表保存到大字段
	 * @author liuchaoyang
	 * 2014-7-29 17:11:45
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveZFSYSCB")
	@ResponseBody
	public String saveZFSYSCB(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		String result = null;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		String introduction = request.getParameter("introduction")==null?"":request.getParameter("introduction");
		Map<String,String> map = new HashMap();
		if (noteinfo != null) {
			ArrayList list = (ArrayList) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map)list.get(0);
			}
		}
		//如果没有入监监区，根据选中的监区名称查询监区ID。再根据选中的监区判断是否是分监区，如果是分监区查询出父级部门ID
		String orgid1 = map.get("orgid1");
		String orgid2 = map.get("orgid1");
		String orgname1 = map.get("jianqu");
		String orgname2 = map.get("jianqu");
		if(StringNumberUtil.isNullOrEmpty(orgid1)){
			Map tempmap=new HashMap();
			tempmap.put("departname", orgname1);
			tempmap.put("departid", user.getDepartid());
			SystemOrganization org = orgService.getDepartidByName(tempmap);
			if(!StringNumberUtil.isNullOrEmpty(org)){
				orgid1 = orgid2 =org.getOrgid();
				if("11".equals(org.getUnitlevel())){
					orgid1 = orgService.getOrginfoByOrgid(org.getPorgid()).getOrgid();
					orgname1 = orgService.getOrginfoByOrgid(org.getPorgid()).getName();
				}
			}
		}
		TbsysDocument document = new TbsysDocument();
		document.setDepartid(orgid2);
		document.setCrimid(map.get("bianhao"));
		document.setIntroduction(introduction);
		document.setContent(content);
		document.setOpid(user.getUserid());
		document.setOptime(date);
		
		TbprisonerBaseinfo info = new TbprisonerBaseinfo();
		info.setCrimid(map.get("bianhao"));
		info.setName(map.get("name"));
		info.setDepartid(user.getDepartid());
		info.setOpid(user.getUserid());
		info.setOptime(date);
		
		TbprisonerBaseCrime crime = new TbprisonerBaseCrime();
		crime.setOrgname1(orgname1);
		crime.setOrgname2(orgname2);
		//String jailname = orgService.getOrginfoByOrgid(user.getDepartid()).getName();
		crime.setDetainprison(user.getPrison().getName());
		crime.setFileno(map.get("danganhao"));
		crime.setCrimid(map.get("bianhao"));
		crime.setOrgid(user.getDepartid());
		crime.setOrgid1(orgid1);
		crime.setOrgid2(orgid2);
		crime.setOpid(user.getUserid());
		crime.setDetainstatus("在押");
		crime.setOptime(date);
		
		if(!StringNumberUtil.notEmpty(docid)){
			document.setTempid(tempid);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.XINFANRUJIAN+LogCommon.ADD);
			log.setRemark(LogCommon.XINFANRUJIAN+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
			//符合收监要求，并且有罪犯编号向基本信息表、处罚信息表添加一条数据
			if(StringNumberUtil.notEmpty(map.get("bianhao"))){
				prisonerService.insertSelective(info);
				countnum = prisonerService.insertTbprisonerBaseCrime(crime);
			}
		}else {
			document.setDocid(Integer.parseInt(docid));
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.XINFANRUJIAN+LogCommon.EDIT);
			log.setRemark(LogCommon.XINFANRUJIAN+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
			//符合收监要求，并且有罪犯编号修改基本信息表
			if(StringNumberUtil.notEmpty(map.get("bianhao"))){
				prisonerService.updateTbprisonerBaseCrime(crime);
				countnum = prisonerService.updateByPrimaryKeySelective(info);
			}else{
				//不符合收监要求，删除罪犯信息
				prisonerService.deleteByPrimaryKey(crimid);
				prisonerService.deleteByCrimid(crimid);
			}
		}
		if(countnum == 1) {
			status = 1;//如果新增或修改成功，日志记录成功
			prisonerService.callSentncechangByCrimid(map.get("bianhao"));
		}
		log.setStatus(status);
		log.setOrgid(user.getUserid());
		log.setLogtype(LogCommon.XINFANRUJIAN+LogCommon.OPERATE);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
	/*
	 * 验证罪犯编号、档案号是否重复
	 */
	@RequestMapping(value="/checkCrimidAndFileno")
	@ResponseBody
	public Object checkCrimidAndFileno(HttpServletRequest request){
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String crimid = request.getParameter("crimid");
		String fileno = request.getParameter("fileno");
		String flowdefid = request.getParameter("flowdefid");		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departId", user.getDepartid());
		map.put("applyid", crimid);
		map.put("flowdefid", flowdefid);
		List<Map<String,Object>> list = flowBaseService.getJHZSData(map);//判断基本信息有没有保存
		if(list.size()>0){
			return "save";//基本信息已保存
		}
		
		if(!StringNumberUtil.isNullOrEmpty(crimid)){//判断罪犯编号是否已存在
			map = new HashMap<String, Object>();
			map.put("departId", user.getDepartid());
			map.put("crimid", crimid);
			int  count = prisonerService.checkCrimidAndFileno(map);
			if(count>0){
				return "crimid";
			}
		}
		
		if(!StringNumberUtil.isNullOrEmpty(fileno)){//判断档案号是否已存在
			map = new HashMap<String, Object>();
			map.put("departId", user.getDepartid());
			map.put("fileno", fileno);
			int  count = prisonerService.checkCrimidAndFileno(map);
			if(count>0){
				return "fileno";
			}
		}
		return "ok";
	}

	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/deleteZFSYSPB")
	@ResponseBody
	public Object deleteNewPrisoner(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String name = "";
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String docid = request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		String[] ids = crimid.split(",");
    	String[] docids = docid.split(",");
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0;i<docids.length;i++){
			map = new HashMap<String,Object>();
			map.put("departId", user.getDepartid());
			map.put("applyid", ids[i]);
			map.put("flowdefid", "doc_zfrjdjsp");
			List<Map<String,Object>> list = flowBaseService.getJHZSData(map);
		    if(list.size()==0){
				result = tbsysDocumentService.deleteTbsysDocument(docids[i]);
				log.setLogtype(LogCommon.XINFANRUJIAN+LogCommon.OPERATE);
				log.setOpaction(LogCommon.DEL);
				log.setOpcontent(LogCommon.XINFANRUJIAN+LogCommon.DEL);
				log.setOrgid(user.getUserid());
				log.setRemark(LogCommon.XINFANRUJIAN+LogCommon.DEL);
				if(result == 1 && StringNumberUtil.notEmpty(crimid)){
					status = 1;//如果新增或修改成功，日志记录成功,并删除罪犯信息
					prisonerService.deleteByCrimid(ids[i]);
					prisonerService.deleteByPrimaryKey(ids[i]);
					sentenceAlterationService.deleteByPrimaryKey(ids[i]);//刑期变动信息表
				}
				log.setStatus(status);
				try {
					logService.add(log, user);
				} catch (Exception e) {
					// 吃掉异常
				}
			}else{
				Map<String,Object> base = MapUtil.trimKeyValue(list.get(0));
				name += base.get("APPLYNAME")+"、";
				if(!StringNumberUtil.isNullOrEmpty(name)){
					name = name.substring(0, name.length()-1);
					map = new HashMap<String,Object>();
					map.put("name", name);
				}
			}
	    }
		return map;
	}	

}

