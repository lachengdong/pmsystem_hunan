package com.sinog2c.mvc.controller.penaltyPerform;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;

/**
 * @author kexz
 *罪犯死亡
 * 2014-7-18
 */
@Controller
@RequestMapping("/criminalDeath")
public class CriminalDeathController extends ControllerBase{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Resource
	protected FlowBaseService flowBaseService;
	
	
	/**
	 * 跳转罪犯死亡选择罪犯页面
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/criminalDeath.page")
	public ModelAndView criminalDeath(HttpServletRequest request){
		//资源对象
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/criminalDeathChoose");
	}
	/**
	 * 获取罪犯列表
	 * @author liuxin
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getCriminalDeathBasicInfoList.json")
	@ResponseBody
	public Object getCriminalDeathBasicInfoList(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("userid", getLoginUser(request).getUserid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);                     
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "other_zfswsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
	/** 
	 *显示罪犯死亡新增表单
	 * @author liuxin
	 * 2014-7-25
	 */
	@RequestMapping("/criminalDeathAdd.page")
	public ModelAndView criminalDeathAdd(HttpServletRequest request){
		try{
			String crimid = request.getParameter("crimid");
			String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
			//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
			if(crimid==null || "".equals(crimid)){
				String id = request.getParameter("id");
				request.setAttribute("id", id);
				String[] ids = id.split(",");
				crimid = ids[0];
				//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
				ids = crimid.split("@");
				crimid = ids[0];
				if(ids.length>1)idnumber = ids[1];
			}
			JSONArray docconent = new JSONArray();
			String menuid = request.getParameter("menuid");
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			if(idnumber != null && !"".equals(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid", idnumber);
			}else{
				String tempid = "YZH_ZFSW";
				request.setAttribute("tempid", tempid);
				HashMap<String, Object> map = new HashMap<String, Object>();
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
				if (template != null) docconent.add(template.getContent());
				String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
				String xuhao = flowBaseService.getFlowXuHao(year, "other_zfswsp", null, user.getDepartid());
				map.put("cdmmarkyear",year);
				map.put("cdmmarktype","");//字号
				map.put("cdmmarkid",xuhao);//序号
				map.put("criminalid",crimid);
				map.put("cbiname",info.getName());
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}  
			request.setAttribute("orgid", crime.getOrgid1());
			request.setAttribute("crimid", crimid);
			request.setAttribute("applyid", crimid);
			request.setAttribute("menuid", menuid);
			request.setAttribute("applyname",info.getName());
			Map<String, Object> selectmap = new HashMap<String, Object>();
			String codeValue = systemCodeService.getcodeValue("GKM1002");
			selectmap.put("cdmdeathtype", systemCodeService.getcodeValue("GB032"));
			selectmap.put("cdminformcourt", codeValue);
			selectmap.put("cdminformprocuratorate", codeValue);
			selectmap.put("cdminformfamily", codeValue);
			request.setAttribute("flowdefid", "other_zfswsp");
			request.setAttribute("formcontent", docconent.toString());
			request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		//资源对象
		returnResourceMap(request); 
		return new ModelAndView("aip/criminalDeathAdd");
	}

}
