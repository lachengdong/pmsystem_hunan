package com.sinog2c.mvc.controller.publicMain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysQueryplansql;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.QuerySchemeService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.MapUtil;



@Controller
public class PublicMainController extends ControllerBase {

	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private QuerySchemeService querySchemeService;
	@Resource
	private SystemCodeService systemCodeService;
	
	@RequestMapping(value = "/publicChooseCriminalPage")
	public ModelAndView toChooseCriminalPage(HttpServletRequest request) {
		String action = request.getParameter("action");//需要跳转的action
		if(action==null){
			action = "publicMainController";
		}
		String resid = request.getParameter("resid");//资源编号
		String tempid = request.getParameter("tempid");//模板编号
		request.setAttribute("resid", resid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("action", action);
		returnResourceMap(request);
		return new ModelAndView("main/publicChooseCriminal");
	}
	
	@RequestMapping(value = "/publicMainController")
	public ModelAndView PublicMainAip(HttpServletRequest request) {
		// 获取罪犯编号，模板编号
		String planid = request.getParameter("planid");
		String menuid = request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		//数据库查询结果临时存储
		Map<String, Object> map = new HashMap<String, Object>();
		//页面展示数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//处理下拉框的值
		Map<String, Object> selectmap = new HashMap<String, Object>();
		
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		
		JSONArray docconent = new JSONArray();
		List<TbsysQueryplansql> sqls = null;
		if (template != null) {
			docconent.add(template.getContent());
			HashMap temp = new HashMap();
			temp.put("planid", template.getFindid());
			temp.put("opsign", "1");
			sqls = querySchemeService.getSchemeSqlListByPlanId(temp);
		}
		String sql = "";
		if(sqls != null){
			for(TbsysQueryplansql tbsysQueryplansql:sqls){
				sql = tbsysQueryplansql.getSqltext();
				if(sql.startsWith("@query")){
					sql = sql.substring(6).trim();
			        resultMap.put(sql.split("@")[0].toLowerCase(), systemModelService.getRecommendationContent(sql.split("@")[1],user,request));
				}else if(sql.startsWith("@list")){
					sql = sql.substring(5).trim();
					selectmap.put(sql.split("@")[0].toLowerCase(), systemCodeService.getcodeValue(sql.split("@")[1]));
				}else{
					if(sql.contains("@crimid")){   //罪犯编号
						sql = sql.replaceAll("@crimid", "'"+request.getParameter("crimid")+"'");
					}
					if(sql != null && !"".equals(sql)){
						map = systemModelService.getDocumentContent(sql);
					}
						map = MapUtil.turnKeyToLowerCase(map);
						resultMap.putAll(map);
				}
				
			}
		}
		//表单
		request.setAttribute("formcontent", docconent.toString());
		//表单数据
		request.setAttribute("formDatajson", new JSONObject(resultMap).toString());
		//下拉框
		request.setAttribute("selectDatajson",new JSONObject(selectmap).toString());
		
		return new ModelAndView("aip/publicMainAip");
	}
	
}
