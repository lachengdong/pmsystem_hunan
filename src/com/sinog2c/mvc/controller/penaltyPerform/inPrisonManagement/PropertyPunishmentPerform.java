package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.Propertypunishmentperform;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.PropertypunishmentperformService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.DateUtil;
import flexjson.JSONDeserializer;

@Controller
@RequestMapping("/basicInfo")
public class PropertyPunishmentPerform extends ControllerBase {
	@Resource
	public SystemLogService logService;
	@Autowired
	private PropertypunishmentperformService propertypunishmentperformService;
	@Resource
	protected FlowBaseService flowBaseService;
	
	
	/**
	 *方法描述：跳转到罪犯处理页面 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toPropertyPage.page")
	public ModelAndView toBasicInfoChoosePage(HttpServletRequest request) throws Exception {
		String menuid = request.getParameter("menuid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		return new ModelAndView(
				"penaltyPerform/inPrisonManagement/PropertyPunishmentPerformChoose");
	}
	/**
	 * 获取罪犯列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPropertyPunishmentPerformList.json")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<TbprisonerBaseinfo> data = new ArrayList<TbprisonerBaseinfo>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = propertypunishmentperformService.findByPropertyCount(map);
	    	data= propertypunishmentperformService.findByProperty(map);
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	@RequestMapping("/toProDetailListPage.page")
	public ModelAndView toProDetailListPage(HttpServletRequest request) throws Exception {
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		request.setAttribute("crimid", crimid);
		request.setAttribute("name",URLDecoder.decode(name,"UTF-8"));
		return new ModelAndView(
		"penaltyPerform/inPrisonManagement/ProDetailListPage");
	}
	@RequestMapping(value = "/ajaxGetProDetailList")
	@ResponseBody
	public Object ajaxGetProDetailList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<TbprisonerBaseinfo> data = new ArrayList<TbprisonerBaseinfo>();
		try {
			//取得参数
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("crimid", crimid);
	    	int count = propertypunishmentperformService.findByPropertyDetailCount(map);
	    	data= propertypunishmentperformService.findByPropertyDetail(map);
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/saveProDetail" })
	@ResponseBody
	public String saveMergeReference(HttpServletRequest request){
		String result = "success";
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				Propertypunishmentperform propertypunishmentperform = new Propertypunishmentperform();
				propertypunishmentperform.setCrimid(crimid);
				propertypunishmentperform.setName(name);
				propertypunishmentperform.setRemark1((String)row.get("REMARK1"));
				propertypunishmentperform.setFajin(Long.parseLong(row.get("FAJIN").toString()));
				propertypunishmentperform.setLxrlb((String)row.get("LXRLB"));
				propertypunishmentperform.setLxrname((String)row.get("LXRNAME"));
				propertypunishmentperform.setRemark2((String)row.get("REMARK2"));
				if(row.get("LXDATE")!=null&&!"".equals(row.get("LXDATE"))){
					propertypunishmentperform.setLxdate(DateUtil.StringParseDate(row.get("LXDATE").toString().substring(0, 10),"yyyy-MM-dd"));
				}
				if("added".equals((String)row.get("_state"))){
					int propertyid = propertypunishmentperformService.findMaxid();
					propertypunishmentperform.setPropertyid(propertyid);
					try {
						propertypunishmentperformService.insert(propertypunishmentperform);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
					}
				}else if("modified".equals((String)row.get("_state"))){
					int propertyid = (Integer)row.get("PROPERTYID");
					propertypunishmentperform.setPropertyid(propertyid);
					propertypunishmentperformService.updateByPrimaryKey(propertypunishmentperform);
				}
				else if("removed".equals((String)row.get("_state"))){
					String propertyid = (String)row.get("PROPERTYID");
					propertypunishmentperformService.deleteByPrimaryKey(propertyid);
				}
			}
		}
		return result;
	}
	
	public Object Decode(String json) {
		if (null==json||"".equals(json)) return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		Object obj = deserializer.deserialize(json);
		if(obj != null && obj.getClass() == String.class){
			return Decode(obj.toString());
		}
		return obj;
	}	
	public long getIntValue(Object o) {
		long returnValue = -1;
		if(null != o){
			try {
				if(o instanceof Long){
					returnValue = (Long)o;
				}
				if(o instanceof String){
					if(!"".equals(o.toString())){
						returnValue = Long.valueOf(o.toString());
					}
				}
			} catch (NumberFormatException e) {
				returnValue = -1;
			}
		}
		return returnValue;
	}
}
