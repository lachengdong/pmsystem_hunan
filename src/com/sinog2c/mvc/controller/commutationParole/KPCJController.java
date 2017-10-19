package com.sinog2c.mvc.controller.commutationParole;

import java.net.URLDecoder;
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

import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbxfKpcj;
import com.sinog2c.model.prisoner.ZPUBLICYZJC;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.prisoner.TBXFKPCJService;

import flexjson.JSONDeserializer;
@Controller
public class KPCJController extends ControllerBase {
	@Resource
	private TBXFKPCJService tbxfKpcjService;
	@RequestMapping("/toKPCJCrimPage.page")
	public ModelAndView toBasicInfoChoosePage(HttpServletRequest request) throws Exception {
		String menuid = request.getParameter("menuid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		return new ModelAndView(
				"penaltyPerform/inPrisonManagement/KPCJchoose");
	}
	
	@RequestMapping("/toKPCJDetailListPage.page")
	public ModelAndView toProDetailListPage(HttpServletRequest request) throws Exception {
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		request.setAttribute("crimid", crimid);
		request.setAttribute("name",URLDecoder.decode(name,"UTF-8"));
		return new ModelAndView(
		"penaltyPerform/inPrisonManagement/KPCJDetailListPage");
	}
	
	@RequestMapping(value = "/ajaxGetKPCJDetailList")
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
	    	int count = tbxfKpcjService.findByKPCJDetailCount(map);
	    	data= tbxfKpcjService.findByKPCJDetail(map);
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	@RequestMapping(value = { "/saveKPCJDetail" })
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
				TbxfKpcj tbxfKpcj = new TbxfKpcj();
				tbxfKpcj.setCrimid(crimid);
				tbxfKpcj.setName(name);
				tbxfKpcj.setBldate(new Date());
				tbxfKpcj.setBlrname(user.getName());
				tbxfKpcj.setKpcj((String)row.get("kpcj"));
				tbxfKpcj.setRemark1((String)row.get("remark1"));
//				propertypunishmentperform.setFajin(getIntValue(row.get("FAJIN")));
//				propertypunishmentperform.setLxrlb((String)row.get("LXRLB"));
//				propertypunishmentperform.setLxrname((String)row.get("LXRNAME"));
//				propertypunishmentperform.setRemark2((String)row.get("REMARK2"));
				if(row.get("kpdate")!=null&&!"".equals(row.get("kpdate"))){
					tbxfKpcj.setKpdate((String)row.get("kpdate"));
				}
				if("added".equals((String)row.get("_state"))){
					String propertyid = tbxfKpcjService.selectKPCJMaxidByCrimid();
					tbxfKpcj.setPropertyid(propertyid);
					try {
						tbxfKpcjService.insertSelective(tbxfKpcj);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
					}
				}else if("modified".equals((String)row.get("_state"))){
					String propertyid = (String)row.get("propertyid");
					tbxfKpcj.setPropertyid(propertyid);
					tbxfKpcj.setCrimid(crimid);
					tbxfKpcjService.updateByPrimaryKey(tbxfKpcj);
				}
				else if("removed".equals((String)row.get("_state"))){
					String propertyid = (String)row.get("propertyid");
					tbxfKpcjService.deleteByPrimaryKey(propertyid);
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
