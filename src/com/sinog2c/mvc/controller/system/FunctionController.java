package com.sinog2c.mvc.controller.system;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.dao.api.system.FunctionDeclarationMapper;
import com.sinog2c.model.system.FunctionDeclaration;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
/*
 * 功能说明模块
 */
@Controller
public class FunctionController extends ControllerBase {

	@Autowired
	private FunctionDeclarationMapper functiondeclarationmapper;
	@RequestMapping("toFunctionDescPage")
	public ModelAndView toFunctionDescPage(HttpServletRequest request, HttpServletResponse response){
		String resid = request.getParameter("resid");
		request.setAttribute("resid",resid);
		
		return new ModelAndView("system/functionDesc");
	}
	
	@RequestMapping("saveFunctionData")
	@ResponseBody
	public int saveFunctionData(HttpServletRequest request,HttpServletResponse response){
		String  resid=request.getParameter("resid");
		String data=request.getParameter("data");
		SystemUser user=getLoginUser(request);
		String userid=user.getUserid();
		FunctionDeclaration functiondeclaration=new FunctionDeclaration();
		int num=0;
		if(null!=resid){
			num=functiondeclarationmapper.getById(resid);
		}
		functiondeclaration.setContent(data.toString());
		functiondeclaration.setMenuid(resid);
		functiondeclaration.setOpid(userid);
		functiondeclaration.setTitle("");
		BigDecimal bigDecimal = new BigDecimal(1);
		functiondeclaration.setSn(bigDecimal);
		functiondeclaration.setOptime(new Date());
		int nums=0;
		if(num>0)
			nums=functiondeclarationmapper.updateByPrimaryKeyWithBLOBs(functiondeclaration);
		else
		nums=functiondeclarationmapper.insert(functiondeclaration);
		return nums;
	}
	@RequestMapping("getData")
	@ResponseBody
	public String getData(HttpServletRequest request,HttpServletResponse response){
		String resid=request.getParameter("resid");
		FunctionDeclaration functiondeclaration = new FunctionDeclaration();
		String str="";
		if(null!=resid){
			functiondeclaration=functiondeclarationmapper.selectByPrimaryKey(resid);
			if(functiondeclaration==null) {
				str = "sucess";
			} else {
				str = functiondeclaration.getContent();
			}
		}
		return str;
	}
	
	/**
	 * 页面帮助按钮查看操作说明AIP
	 */
	@RequestMapping("viewFunction")
	
	public ModelAndView viewFunction(HttpServletRequest request){
		String menuid=request.getParameter("menuid");
		Map<String,Object> map = new HashMap<String,Object>();
		JSONArray json=new JSONArray();
		if(null!=menuid&&!"".equals(menuid)){
		String content=functiondeclarationmapper.getFunctionContent(menuid);
		if(null!=content&&!"".equals(content)){
		json.add(content.toString());
		request.setAttribute("formcontent", json.toString());
		request.setAttribute("menuid",menuid);
		}
		}
		return new ModelAndView("system/viewfunction");
	}
	@RequestMapping("getFunction")
	@ResponseBody
	public int getFunction(HttpServletRequest request){
		int count=0;
		String menuid=request.getParameter("menuid");
		count=functiondeclarationmapper.getFunction(menuid);
		return count;
	}
	/**
	 * 删除说明文档
	 */
	@RequestMapping("removeDocumentData")
	@ResponseBody
	public String removeDocumentData(HttpServletRequest request){
		String str="error";
		String resid=request.getParameter("resid");
		int num=functiondeclarationmapper.findByMenuid(resid);
		if(num==0){
			str="no";		
		}else{
			functiondeclarationmapper.removeDocument(resid);
			str="ok";
		}
			return str;
	}
	
}
