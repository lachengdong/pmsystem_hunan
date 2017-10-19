package com.sinog2c.mvc.controller.penaltyPerform;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;

/**
 * 获取罪犯基本信息
 * @author kexz
 * 2014-7-17
 */
@Controller
public class CriminalInfoAction extends ControllerBase{
	@Resource
	private TbprisonerBaseCrimeMapper tbprisonerbasecrimemapper;
	@Resource
	private SystemOrganizationService systemorganizationservice;

	/**
	 * 主页面快速检索
	 * @return
	 */
	@RequestMapping("/gotofastCriminalJianSuo")
	public ModelAndView gotofastCriminalJianSuo(HttpServletRequest request){
		 
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		
		String sdid=request.getParameter("sdid");
		request.setAttribute("sdid", sdid);
		String search=request.getParameter("search");
		request.setAttribute("search", search);
		
		String zaiyacombo1=request.getParameter("zaiyacombo1");
		request.setAttribute("zaiyacombo1", zaiyacombo1);
		String key=request.getParameter("key");
		if(null!=key&&!"".equals(key)){
			try {
				key = URLDecoder.decode(key,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("key", key);
		return new ModelAndView("/main/fastCriminalJianSuo");
	}
	
	/**
	 * sz_main.jsp快速检索页面数据显示
	 */
	@RequestMapping("/getFastCrimid")
	@ResponseBody
	public Object getFastCrimid(HttpServletRequest request,HttpServletResponse response){
		SystemUser user=getLoginUser(request);
		String key=request.getParameter("key");
		try {
			key = URLDecoder.decode(key,"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String sdid=request.getParameter("sdid");
		String zaiyacombo=request.getParameter("zaiyacombo1");
		switch(zaiyacombo){
			case "1":zaiyacombo=GkzxCommon.ZAIYA; break;
			case "2":zaiyacombo=GkzxCommon.SIWANG; break;
			case "3":zaiyacombo=GkzxCommon.DIAOCHU; break;
			case "4":zaiyacombo=GkzxCommon.SHIFANG; break;
			case "5":zaiyacombo=GkzxCommon.DAISHOUJIAN; break;
			case "6":zaiyacombo=GkzxCommon.JIASHI; break;
			case "7":zaiyacombo=GkzxCommon.JIEHUIZAISHEN; break;
			case "8":zaiyacombo=GkzxCommon.BWJY; break;
		}
			
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map map=new HashMap();
		map.put("key",key);
		map.put("sdid", sdid);
		map.put("orgid", user.getOrgid());
		map.put("zaiyacombo", zaiyacombo);
		map.put("start", start);
		map.put("end", end);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);

		List<Map> list=tbprisonerbasecrimemapper.getCriminInfor(map);
		int count=tbprisonerbasecrimemapper.getCriminInforCount(map);
		Map<String,Object> json=new HashMap<String,Object>();
		List<Object> lists=new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map maps=list.get(i);
			maps.put("crimid", maps.get("CRIMID"));
			maps.put("name", maps.get("NAME"));
			maps.put("age", maps.get("AGE"));
			maps.put("gender", maps.get("GENDER"));
			maps.put("registeraddressdetail", maps.get("REGISTERADDRESSDETAIL"));
			maps.put("detainstatus", maps.get("DETAINSTATUS"));
			maps.put("maincase", maps.get("MAINCASE"));
			maps.put("orgname", maps.get("ORGNAME"));
			lists.add(maps);
		}
		json.put("data", lists);
		json.put("total", count);
		PrintWriter writer;
		String result="";
		try {
			result = new JSONObject(json).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			writer = response.getWriter();
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
