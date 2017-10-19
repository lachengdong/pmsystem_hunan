package com.sinog2c.mvc.controller.release;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.release.ReleaseService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
public class ReleaseController extends ControllerBase{
	@Autowired
	private ReleaseService releaseService;
	
	@RequestMapping("/toOutPrisonPage.page")
	public ModelAndView toOutPrisonPage(HttpServletRequest request){
		String ids = request.getParameter("ids");
		if(StringNumberUtil.notEmpty(ids)){
			String[] arr =ids.split("@");
			request.setAttribute("crimid", arr[0]);
			
			
		}
		return new ModelAndView("release/newOutPrison");
		
	}
	@RequestMapping("/ajaxCodeShuJu.json")
	@ResponseBody
	public Object ajaxCodeShuJu(HttpServletRequest request){
		String codetype = request.getParameter("codetype");
		List<Map> codeData = new ArrayList<Map>();
		Map map = new HashMap();
		if(StringNumberUtil.notEmpty(codetype)){
			map.put("codetype", codetype);
			codeData = releaseService.ajaxCodeShuJu(map);
			
		}
		JSONMessage message = JSONMessage.newMessage();
		message.setData(codeData);
		return message.getData();
		
	}
	
	@RequestMapping("/saveOutPrison")
	@ResponseBody
	public void saveOutPrison(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		String flag = this.getOutPrison(crimid);
		if(flag == "0"){
			String data = request.getParameter("data");
			List<Map<String, String>> listMap = (List<Map<String, String>>) JsonUtil.Decode(data);
			Map<String, String> map = new HashMap<String, String>();
			if(listMap.size()>0){
				map = listMap.get(0);
				map.put("crimid", crimid);
				//map.put("createtime", sdf.format(new Date()));
				map.put("createmender", user.getUserid());
				releaseService.saveOutPrison(map);
			}
		}
	}
	@RequestMapping("/getOutPrison.json")
	@ResponseBody
	public String getOutPrison(String crimid){
		//String crimid = request.getParameter("crimid");
		List<Map> list = releaseService.getOutPrison(crimid);
		String flag = "0";
		if(list.size()>0){
			flag = "1";
		}
		
		return flag;
	}
}
