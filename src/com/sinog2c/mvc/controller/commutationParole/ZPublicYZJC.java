package com.sinog2c.mvc.controller.commutationParole;

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
import com.sinog2c.model.prisoner.ZPUBLICYZJC;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.ZPUBLICYZJCService;
import flexjson.JSONDeserializer;

@Controller
public class ZPublicYZJC extends ControllerBase {
	@Autowired
	private ZPUBLICYZJCService zPUBLICYZJCService;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	
	@RequestMapping("/toYZJCCrimPage.page")
	public ModelAndView toBasicInfoChoosePage(HttpServletRequest request) throws Exception {
		String menuid = request.getParameter("menuid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		return new ModelAndView(
				"penaltyPerform/inPrisonManagement/YZJCchoose");
	}
	
//	@RequestMapping(value="getYZJCCriminalList")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request,
//			HttpServletResponse response){
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
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	@RequestMapping("/toYZJCDetailListPage.page")
	public ModelAndView toProDetailListPage(HttpServletRequest request) throws Exception {
		String bh = request.getParameter("bh");
		String name = request.getParameter("name");
		request.setAttribute("bh", bh);
		request.setAttribute("name",URLDecoder.decode(name,"UTF-8"));
		return new ModelAndView(
		"penaltyPerform/inPrisonManagement/YZJCDetailListPage");
	}
	@RequestMapping(value = "/ajaxGetYZJCDetailList")
	@ResponseBody
	public Object ajaxGetProDetailList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<TbprisonerBaseinfo> data = new ArrayList<TbprisonerBaseinfo>();
		try {
			//取得参数
			String bh = request.getParameter("bh")==null?"":request.getParameter("bh");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bh", bh);
	    	int count = zPUBLICYZJCService.findByYZJCDetailCount(map);
	    	data= zPUBLICYZJCService.findByYZJCDetail(map);
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	@RequestMapping(value = { "/saveYZJCDetail" })
	@ResponseBody
	public String saveMergeReference(HttpServletRequest request){
		String result = "success";
		SystemUser user = getLoginUser(request);
		String bh = request.getParameter("bh");
		String name = request.getParameter("name");
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				ZPUBLICYZJC zPUBLICYZJC = new ZPUBLICYZJC();
				zPUBLICYZJC.setBh(bh);
				zPUBLICYZJC.setSdid(user.getPrisonid());
				zPUBLICYZJC.setJclb((String)row.get("jclb"));
				zPUBLICYZJC.setJcjs((String)row.get("jcjs"));
//				propertypunishmentperform.setFajin(getIntValue(row.get("FAJIN")));
//				propertypunishmentperform.setLxrlb((String)row.get("LXRLB"));
//				propertypunishmentperform.setLxrname((String)row.get("LXRNAME"));
//				propertypunishmentperform.setRemark2((String)row.get("REMARK2"));
				if(row.get("pzrq")!=null&&!"".equals(row.get("pzrq"))){
					zPUBLICYZJC.setPzrq(row.get("pzrq").toString());
				}
				if("added".equals((String)row.get("_state"))){
					String id = zPUBLICYZJCService.selectYZJCMaxidByCrimid();
					zPUBLICYZJC.setId(id);
					try {
						zPUBLICYZJCService.insertSelective(zPUBLICYZJC);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
					}
				}else if("modified".equals((String)row.get("_state"))){
					String id = (String)row.get("id");
					zPUBLICYZJC.setId(id);
					zPUBLICYZJC.setBh(bh);
					zPUBLICYZJCService.updateByPrimaryKey(zPUBLICYZJC);
				}
				else if("removed".equals((String)row.get("_state"))){
					String id = (String)row.get("id");
					zPUBLICYZJCService.deleteByPrimaryKey(id);
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
