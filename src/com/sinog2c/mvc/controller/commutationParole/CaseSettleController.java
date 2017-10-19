package com.sinog2c.mvc.controller.commutationParole;

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

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 案件整理
 * 
 * @author hzl
 * 
 */
@Controller
@RequestMapping(value = "/commutationParole")
public class CaseSettleController extends ControllerBase {
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemCodeService systemCodeService;
	
	@Autowired
	private CommuteParoleBatchService commuteParoleBatchService;
	
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/tocaseSettlePage")
	public ModelAndView toListPage(HttpServletRequest request) {
		
		SystemUser user = this.getLoginUser(request);
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", user.getDepartid());
		Map<String,Object> map = commuteParoleBatchService.getCommuteParoleBatchInfo(paramap);
		
		if(null != map && map.size() > 0){
			request.setAttribute("caseyear", StringNumberUtil.getStrFromMap("curyear", map));
			request.setAttribute("batch", StringNumberUtil.getStrFromMap("batch", map));
		}
		
		
		String caseyear = DateUtil.dateFormat(new Date(), "yyyy");
		request.setAttribute("caseyear", caseyear);
		
		java.util.Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		request.setAttribute("provincecode", jyconfig.get("provincecode"));
		return new ModelAndView("commutationParole/caseSettleListPage");
	}
	
	
	/**
	 * 重要罪犯列表页
	 * 
	 * @author mushuhong
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/tozycaseSettlePage")
	public ModelAndView tozyListPage(HttpServletRequest request) {
		String year = DateUtil.dateFormat(new Date(), "yyyy");
		request.setAttribute("year", year);
		return new ModelAndView("commutationParole/zycaseSettleListPage");
	}
	
	
	/**
	 * 跳转 普通罪犯和重要罪犯tab页
	 * 
	 * @author mushuhong
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/caseSettleListTab")
	public ModelAndView caseSettleListTab(HttpServletRequest request) {
		String year = DateUtil.dateFormat(new Date(), "yyyy");
		request.setAttribute("year", year);
		return new ModelAndView("commutationParole/caseSettleListTab");
	}
	
	/**
	 * 跳转列表页
	 * 
	 * @author mushuhong
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/caseSettleListPage_nx")
	public ModelAndView caseSettleListPage_nx(HttpServletRequest request) {
		String year = DateUtil.dateFormat(new Date(), "yyyy");
		request.setAttribute("year", year);
		return new ModelAndView("commutationParole/caseSettleListPage_nx");
	}
	
	@RequestMapping(value = "/getCaseTypeMap.action")
	@ResponseBody
	public Object getCaseTypeMap(HttpServletRequest request) {
		String codetype = request.getParameter("codetype")==null?"":request.getParameter("codetype");
		HashMap paramap = new HashMap();
		paramap.put("codetype",codetype);
		List<TbsysCode> caseTypeList = systemCodeService.getCodesByMap(paramap);
		return caseTypeList;
	}
	/**
	 * 获取表单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetAnnexcontent.json")
	@ResponseBody
	public Object ajaxGetAnnexcontent(HttpServletRequest request){
		//Map<String,String> resultMap = new HashMap<String,String>();
		Map<String,Object> map = new HashMap<String,Object>();
		String flowdraftid = request.getParameter("flowdraftid")==null?"":request.getParameter("flowdraftid");
		String casenumber = request.getParameter("parolenumber")==null?"":request.getParameter("parolenumber");
		String im = request.getParameter("im")==null?"":request.getParameter("im");
		String casetype = request.getParameter("casetype")==null?"":request.getParameter("casetype");
		//String parolenumber = request.getParameter("text6")==null?"":request.getParameter("text6");
		//parolenumber = parolenumber.substring(0,parolenumber.lastIndexOf("第")+1)+casenumber+"号";
		map.put("flowdraftid", flowdraftid);
		//String aipFileStr = flowBaseOtherService.getDocconentByFlowdraftid(map);
		//resultMap.put("aipFileStr", aipFileStr);
		//resultMap.put("parolenumber", parolenumber);
		map.put("casenumber", casenumber);
		map.put("im", im);
		map.put("casetype",casetype);
		
		return flowBaseService.updateCaseNumberByFlowdraftid(map);
	}
	/**
	 * 更新表单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveAnnexcontent.json")
	@ResponseBody
	public Object ajaxSaveAnnexcontent(HttpServletRequest request){
		int result = 0;
		Map<String,String> map = new HashMap<String,String>();
		String flowdraftid = request.getParameter("flowdraftid")==null?"":request.getParameter("flowdraftid");
		String aipFileStr = request.getParameter("aipFileStr")==null?"":request.getParameter("aipFileStr");
		
		map.put("flowdraftid", flowdraftid);
		
		String otherId = flowBaseOtherService.getOtheridByFlowdraftid(map);
		if(otherId!=null&&!otherId.equals("")){
			FlowBaseOther other = new FlowBaseOther();
			other.setOtherid(otherId);
			other.setDocconent(aipFileStr);
		//	other.setText7(casetype);
			result = flowBaseOtherService.updateMeetContextByOhterid(other);
		}
		return result;
	}
	
	/**
	 * 判断案件号是否重复
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ajaxGetRepeatAnHao.action")
	@ResponseBody
	public int  ajaxGetRepeatAnHao(HttpServletRequest request){
		int result=1;
		Map<String, Object> map = new HashMap<String, Object>();
		String parolenumber = request.getParameter("parolenumber") == null? "" : request.getParameter("parolenumber");
		String curyear = request.getParameter("curyear") == null ? "" : request.getParameter("curyear");
		String  flowdefid = request.getParameter("flowdefid") == null ? "" : request.getParameter("flowdefid");
		String int1 = request.getParameter("int1") == null ? "" : request.getParameter("int1");
		map.put("parolenumber", parolenumber);
		map.put("curyear", curyear);
		map.put("flowdefid", flowdefid);
		map.put("int1", int1);
		String jieguo = flowBaseService.ajaxGetRepeatAnHao(map);
		if (jieguo!=null && !"".equals(jieguo)) {
			result=0;
		}
		return result;
	}
}
