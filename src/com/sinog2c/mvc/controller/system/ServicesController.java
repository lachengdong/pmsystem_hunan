package com.sinog2c.mvc.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.dao.api.dbmsnew.TbsysServicesMapper;
import com.sinog2c.model.dbmsnew.TbsysServices;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
@Controller
@RequestMapping("/services")
public class ServicesController extends ControllerBase {
	
	@Autowired
	private TbsysServicesMapper tbsysServicesMapper;
	
	
	/**
	 * services管理页面
	 * @param reqeust
	 * @return
	 */
	@RequestMapping("/services.page")
	public ModelAndView servicesManage(HttpServletRequest reqeust){
		ModelAndView mav = new ModelAndView("system/services/servicesManage");
		return mav;
	}
	
	/**
	 * services数据列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getServicesData.json")
	@ResponseBody
	public Object getServicesData(HttpServletRequest request){
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int start = pageIndex * pageSize + 1;
		int end = (pageIndex +1)* pageSize;
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String, Object> resultmap = new HashMap<String,Object>();
		SystemUser user = getLoginUser(request);
		map.put("departid", user.getDepartid());
		map.put("start", start);
		map.put("end", end);
		List<Map> map1 = MapUtil.turnKeyToLowerCaseOfList(tbsysServicesMapper.getAllServies(map));
		int total = map1.size();
		resultmap.put("data", map1);
		resultmap.put("total", total);
		return resultmap;
		
	}
	
	@RequestMapping("/saveOrupdate.action")
	@ResponseBody
	public int saveData(HttpServletRequest request){
		int total=-2;
		SystemUser user = getLoginUser(request);
		TbsysServices services = new TbsysServices();
		String departid = request.getParameter("departid");
		String address = request.getParameter("address");
		String port = request.getParameter("port");
		String ddcid = request.getParameter("ddcid");
		String remark = request.getParameter("remark");
		String text1 = request.getParameter("text1");
		
		services.setPort(port);
		services.setDepartid(departid);
		services.setDdcid(ddcid);
		services.setAddress(address);
		services.setRemark(remark);
		services.setText1(text1);
		services.setCreateuser(user.getUserid());
		services.setCreatedate(new Date());
		TbsysServices t = tbsysServicesMapper.selectByPrimaryKey(departid);
		if(StringNumberUtil.isNullOrEmpty(t)){
			total = tbsysServicesMapper.insert(services);
		}else{
			total = tbsysServicesMapper.updateByPrimaryKey(services);
		}
		
		return total;
	}
	
	@RequestMapping("/removeData.action")
	@ResponseBody
	public int removeData(HttpServletRequest request){
		String departid = request.getParameter("departid");
		int num = tbsysServicesMapper.deleteByPrimaryKey(departid);
		return num;
	}
}