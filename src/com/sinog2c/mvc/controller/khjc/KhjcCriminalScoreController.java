package com.sinog2c.mvc.controller.khjc;
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

import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.PublicBaseDocService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
/**
 * 考核奖惩相关
 * @author yanqutai
 */
@Controller
public class KhjcCriminalScoreController extends ControllerBase {
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private PublicBaseDocService publicBaseDocService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private KhjcCriminalScoreService khjcCriminalService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	/**
	 * 跳转考核奖惩相关公共罪犯选择页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/khjcCriminalChoosePage")
	public ModelAndView khjcCriminalChoosePage(HttpServletRequest request){
		returnResourceMap(request);
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid",tempid);
		//查询罪犯的条件，choosecriminal (all.所有在押 userdep.当前用户所在部门 3.特殊部门)
		request.setAttribute("choosecriminal", request.getParameter("choosecriminal"));
		
		//控制选择完罪犯跳转页面的状态值
		request.setAttribute("state", request.getParameter("state"));
		
		return new ModelAndView("khjc/khjcCriminalChoosePage");
	}
	
	/**
	 * 获取罪犯列表
	 * 
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value="getCriminalBasicInfoForKHJC")
	@ResponseBody
	public Object getCriminalBasicInfoForKHJC(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		map.put("orgid", getLoginUser(request).getOrgid());
		//
    	int count = chooseCriminalService.countCrimNumByParaMap(map);
    	List<Map<String, Object>> data= chooseCriminalService.getCrimInfoByParaMap(map);
    	//
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	
	
	/**
	 * 跳转考核奖惩罪犯专项奖分页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/khjcListPage.page")
	public ModelAndView khjcZhuanXiangJiangFenPage(HttpServletRequest request){
		returnResourceMap(request);
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String fathermenuid = request.getParameter("fathermenuid")==null?"":request.getParameter("fathermenuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("fathermenuid", fathermenuid);
		return new ModelAndView("khjc/publicMainPage");
	}
	
	/**
	 * 跳转考核奖惩审批页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/khjcCrimScoreShenPiPage")
	public ModelAndView khjcCrimScoreShenPiPage(HttpServletRequest request){
		returnResourceMap(request);
		String nodeid = request.getParameter("nodeid")==null?"":request.getParameter("nodeid");//流程节点ID
		request.setAttribute("nodeid", nodeid);
		return new ModelAndView("khjc/khjcCrimScoreShenPiPage");
	}
	
	/**
	 * 跳转罪犯考核奖惩相关的表单的新增页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="zhuanXiangJiangFenAdd.page")
	public ModelAndView zhuanXiangJiangFenAdd(HttpServletRequest request){
		khjcCriminalService.getInfo(request);
		return new ModelAndView("aip/khjc/zhuanXiangJiangFenAdd");
	}
	
	/**
	 * 跳转弹出框（参数codeType）页面
	 * @author yanqutai
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="codeTypeChoosePage")
	public ModelAndView codeTypeChoosePage(HttpServletRequest request){
		request.setAttribute("codeType",request.getParameter("codeType"));
		return new ModelAndView("khjc/codeTypeChoosePage");
	}
	
	/**
	 * 考核奖惩数据保存
	 * yanqutai
	 */
	@RequestMapping(value = "/saveCriminalScoreInfo")
	@ResponseBody
	public String saveCriminalScoreInfo(HttpServletRequest request){
		khjcCriminalService.saveCriminalScoreInfo(request);
		return "";
	}
	
	/**
	 * 根据templetid查询相关的附件信息
	 * yanqutai
	 * @author 
	 */
	@RequestMapping(value="getCriminalKhjcBaseDoc")
	@ResponseBody
	public HashMap<String, Object> getCriminalKhjcBaseDoc(HttpServletRequest request) {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map paraMap =  new HashMap();
		
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		
		paraMap.put("crimid", crimid);
		paraMap.put("tempcondition", tempid);
		paraMap.put("key", key);
		
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");//排序字段
		String sortOrder = request.getParameter("sortOrder");//排序方式
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		
		List<Map> list = publicBaseDocService.getBaseDocByCondition(paraMap);
		int count = publicBaseDocService.countBaseDocByCondition(paraMap);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	
	/**
	 * 根据templetid查询相关的附件信息
	 * yanqutai
	 * @author 
	 */
	@RequestMapping(value="getCriminalKhjcBaseDocByNodeid")
	@ResponseBody
	public HashMap<String, Object> getCriminalKhjcBaseDocByNodeid(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map paraMap = new HashMap();
		
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		String nodeid = request.getParameter("nodeid");
		paraMap.put("crimid", crimid);
		paraMap.put("tempcondition", tempid);
		paraMap.put("key", key);
		paraMap.put("nodeid", nodeid);
		
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");//排序字段
		String sortOrder = request.getParameter("sortOrder");//排序方式
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		
		List<Map> list = publicBaseDocService.getBaseDocByCondition(paraMap);
		int count = publicBaseDocService.countBaseDocByCondition(paraMap);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
}
