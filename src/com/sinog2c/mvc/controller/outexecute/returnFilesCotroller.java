package com.sinog2c.mvc.controller.outexecute;

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
import org.springframework.web.servlet.view.InternalResourceView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 省局退补档案
 * 
 * @author wangxf
 * GDSJ_JWZX_SJTBDA
 */
@Controller
public class returnFilesCotroller extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Resource
	protected FlowBaseService flowBaseService;
	
	@RequestMapping(value="/returnFilesChoose")
	public ModelAndView returnFilesChoose(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/chooseCriminal/returnFilesChoose.jsp"));
	}
	
	//列表
	@RequestMapping(value="/getreturnFilesList")
	@ResponseBody
	public Object getreturnFilesList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String condition = prisonerService.getTheQueryCondition("10160");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			//map.put("departId", getLoginUser(request).getOrgid());
			map.put("condition", condition);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_sjtbdasp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	/**
	 * @param request
	 * @author kxz
	 * 2014-08-20
	 */
	@RequestMapping(value = "returnFiles")
	public ModelAndView chushiHandle(HttpServletRequest request) {
		returnResourceMap(request);
		try{
			String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
			String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程编号
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
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
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			if(!StringNumberUtil.isNullOrEmpty(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid",idnumber);
			}else{
				String tempid ="GDSJ_JWZX_SJTBDA";//获取对应表单打印中表单编号
				request.setAttribute("tempid", tempid);
				HashMap<String, Object> map = new HashMap<String, Object>();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				String departid=user.getDepartid();//根据用户Id获取所在部门Id
				String xuhao ="";
				SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				if(crime!=null){
					map.put("text1",crime.getFileno());//档案号
				}
				if(org!=null){
					map.put("szjy",org.getName());
				}
				map.put("text2",year);
				map.put("text3",xuhao);//获取序号
				map.put("cbiname",info.getName());
				map.put("text7",user.getName());
				map.put("text14",sdf.format(new Date()));
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}    
			request.setAttribute("applyid", crimid);
			request.setAttribute("menuid", menuid);
			request.setAttribute("applyname",info.getName());
			request.setAttribute("orgid",crime.getOrgid1());
			request.setAttribute("flowdefid", "doc_sjtbdasp");
			returnResourceMap(request); 
			request.setAttribute("formcontent", docconent.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/returnFiles.jsp"));
	}
}
