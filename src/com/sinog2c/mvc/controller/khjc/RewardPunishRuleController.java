package com.sinog2c.mvc.controller.khjc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.sinog2c.model.khjc.TkhRewardpunishrule;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.khjc.RewardPunishPointsService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.JsonUtil;


@Controller
public class RewardPunishRuleController extends ControllerBase {
	@Autowired
	private RewardPunishPointsService rewardPunishPointsService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private SystemLogService logService;
	
	@RequestMapping(value = "/toRewardPunishRuleListPage")
	public ModelAndView RewardPunishRule(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/khjc/zxjf/rewardPunishRuleListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	@RequestMapping(value = "/ajaxGetRewardPunishRuleList")
	@ResponseBody
	public Object ajaxGetRewardPunishRuleList(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		SystemOrganization provinceOrg = systemOrganizationService.getProvinceCode(user.getOrgid());
		
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String key = request.getParameter("key");
		
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		conditionMap.put("porgid", provinceOrg.getOrgid());
		conditionMap.put("start", start);
		conditionMap.put("end", end);
		conditionMap.put("sortField", sortField);
		conditionMap.put("sortOrder", sortOrder);
		conditionMap.put("key", key);
		
		int total = rewardPunishPointsService.getRewardPunishRuleCount(conditionMap);
		List<Map<String,Object>> list = rewardPunishPointsService.getRewardPunishRuleList(conditionMap);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	@RequestMapping(value = "/toRewardPunishRuleAddPage")
	public ModelAndView toRewardPunishRuleAddPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/khjc/zxjf/rewardPunishRuleAddPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	@RequestMapping(value = { "/saveRewardPunishRule" })
	@ResponseBody
	public String saveRewardPunishRule(HttpServletRequest request){
		String result = "success";
		short status = 1;
		String operatetype = request.getParameter("operatetype")==null?"":request.getParameter("operatetype");
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList) JsonUtil.Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				String ruleid = (String)row.get("ruleid");
				String introduction = (String)row.get("introduction");
				int isprovisions = Integer.parseInt(row.get("isprovisions").toString());
				String searchcode = (String)row.get("searchcode");
				String rulepid = (String)row.get("rulepid");
				String content = (String)row.get("content");
				SystemOrganization provinceOrg = systemOrganizationService.getProvinceCode(user.getOrgid());
				
				TkhRewardpunishrule model = new TkhRewardpunishrule();
				model.setRuleid(ruleid);
				model.setRulepid(rulepid);
				model.setPorgid(provinceOrg.getOrgid());
				model.setIntroduction(introduction);
				model.setContent(content);
				model.setSearchcode(searchcode);
				model.setIsleaf((short)0);
				model.setIsprovisions((short)isprovisions);
				model.setOpid(user.getUserid());

				if("new".equals(operatetype)){
					try {
						rewardPunishPointsService.insertRewardPunishRule(model);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("考核条款添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加考核条款");
					log.setOrgid(user.getUserid());
					log.setRemark("添加考核条款");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("edit".equals(operatetype)){
					try {
						rewardPunishPointsService.updateIsleafVal(model);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("考核条款修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改考核条款,ruleid="+ruleid);
					log.setOrgid(user.getUserid());
					log.setRemark("修改考核条款");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/toRewardPunishRuleEditPage")
	public ModelAndView toRewardPunishRuleEditPage(HttpServletRequest request) {
		String ruleid = request.getParameter("punid");
		TkhRewardpunishrule model = rewardPunishPointsService.getRewardPunishRuleByPrimaryKey(ruleid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ruleid", ruleid);
		map.put("introduction", model.getIntroduction());
		map.put("isprovisions", model.getIsprovisions());
		map.put("searchcode", model.getSearchcode());
		map.put("rulepid", model.getRulepid());
		map.put("content", model.getContent());
		View view = new InternalResourceView("WEB-INF/JSP/khjc/zxjf/rewardPunishRuleAddPage.jsp");
		ModelAndView mav = new ModelAndView(view,"record",map);
		return mav;
	}	


}
