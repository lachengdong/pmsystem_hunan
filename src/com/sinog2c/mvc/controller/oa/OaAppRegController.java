package com.sinog2c.mvc.controller.oa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.sinog2c.model.oa.OaAppReg;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.oa.OaAppRegService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/appreg")
public class OaAppRegController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private OaAppRegService oaAppRegService;
	
	/**
	 * 方法描述：跳转到应聘人员登记列表
	 * @author xiaoyan 2015-09-08 
	 * @return
	 */
	@RequestMapping(value = "/appRegList")
	public ModelAndView appRegList(HttpServletRequest request) {
		//资源对象
		returnResourceMap(request);
		return new ModelAndView("oa/hr/appreg");
	}
	/**
	 * 方法描述：查询表中所有符合条件的数据并按条件分页
	 * 
	 * @author xiaoyan 2015-09-08 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getAppRegList.json")
	@ResponseBody
	public Object getAppRegList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 查询出所有数据集合
		List<OaAppReg> list = oaAppRegService.getAppRegListInfo(request);
		// 查询数据总数
		int count = oaAppRegService.getCount(request);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 方法描述：删除选中的数据
	 * 
	 * @author xiaoyan 2015-09-08 
	 */
	@RequestMapping(value="/deleteAppReg")
	@ResponseBody
	public int deleteAppReg(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		int i = oaAppRegService.deleteAppReg(uuid);
		return i;
	}
	
	/**
	 * 表单新增、修改或查看
	 * @author xiaoyan 2015-09-08 
	 * @param request
	 * @return
	 */
	@RequestMapping("/appRegAdd")
	public ModelAndView appRegAdd(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String uuid = request.getParameter("uuid");
		String type = request.getParameter("type");
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		if (uuid==null) {//新增
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			docconent.add(template.getContent());
			request.setAttribute("type", "1");
		}
		if(uuid!=null&&!"".equals(uuid)){//修改  ，查看
			OaAppReg oaappreg = oaAppRegService.getAppRegInfoByUuid(uuid);
			if (oaappreg!=null){
				String formcontent = oaappreg.getContent().toString();
				docconent.add(formcontent);
			}
			if("xiugai".equals(type)){
				request.setAttribute("uuid", uuid);
				request.setAttribute("type", "1");
			}
		}
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("oa/hr/appregAdd");
	}
	/**
	 * 新增、修改时将登记表表保存到数据库
	 * 
	 * @author xiaoyan 2015-09-08 
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@RequestMapping(value = "/saveAppReg")
	@ResponseBody
	public int saveAppReg(HttpServletRequest request){
		int result=0;
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String uuid = request.getParameter("uuid")==null?"":request.getParameter("uuid");
		String ypjobs = request.getParameter("ypjobs")==null?"":request.getParameter("ypjobs");
		String ypname = request.getParameter("ypname")==null?"":request.getParameter("ypname");
		String tbdate = request.getParameter("tbdate")==null?"":request.getParameter("tbdate");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		Map<String,String> map = new HashMap();
		OaAppReg oaappreg = new OaAppReg();
		if(!StringNumberUtil.notEmpty(uuid)){
			//保存方法
			oaappreg.setYpname(ypname);
			oaappreg.setYpjobs(ypjobs);
			oaappreg.setContent(content);
			oaappreg.setTbdate(tbdate);
			oaappreg.setOpid(user.getUserid());
			result = oaAppRegService.save(oaappreg);
		}else {//修改
			oaappreg.setUuid(uuid);
			oaappreg.setYpname(ypname);
			oaappreg.setYpjobs(ypjobs);
			oaappreg.setTbdate(tbdate);
			oaappreg.setContent(content);
			oaappreg.setOpid(user.getUserid());
			result = oaAppRegService.update(oaappreg);
		}
		
		return result;
	} 
}
