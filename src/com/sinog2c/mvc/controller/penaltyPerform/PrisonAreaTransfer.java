package com.sinog2c.mvc.controller.penaltyPerform;

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
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.LogCommon;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * @author wangxf
 *监区调动
 * 2014-7-18
 */
@Controller
public class PrisonAreaTransfer extends ControllerBase{
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private PrisonerService prisonerService;
	@Resource
	public SystemLogService logService;
	@Resource
	public SystemOrganizationService orgService;
	
	/**
	 * 主页面
	 * @return
	 */
	@RequestMapping("/prisonAreaTransfer")
	public ModelAndView prisonAreaTransfer(){
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/prisonAreaTransfer.jsp"));
	}
	
	/**
	 * 获取罪犯列表
	 * 
	 * @author wangxf
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getPrisonAreaTransferCriminalList")
	@ResponseBody
	public Object getPrisonAreaTransferCriminalList(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		
		String orgid=request.getParameter("orgid1");
		if(StringNumberUtil.isEmpty(orgid)){
			orgid = getLoginUser(request).getOrgid();
		}
		map.put("orgid", orgid);
		
    	int count = chooseCriminalService.countAllByCondition(map);
    	List<Map<String,Object>> data = chooseCriminalService.getBasicInfoList(map);
    	
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/prisonTransfer")
	@ResponseBody
	public Object prisonTransfer(HttpServletRequest request,
			HttpServletResponse response){
		SystemUser user = getLoginUser(request);
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		TbprisonerBaseCrime basecrime=new TbprisonerBaseCrime();
		TbxfSentenceAlteration sentence = new TbxfSentenceAlteration();
		String strids=request.getParameter("crimid");
		String[] ids=strids.split(",");
		
		String orgid1 = request.getParameter("depid");
        String orgid2 = request.getParameter("depid");
        String orgname1 = "";
        String orgname2 = "";
        if(!StringNumberUtil.isNullOrEmpty(orgid1)){
            Map tempmap=new HashMap();
            tempmap.put("orgid", orgid2);
            tempmap.put("departid", user.getDepartid());
            SystemOrganization org = orgService.getDepartidByName(tempmap);
            if(!StringNumberUtil.isNullOrEmpty(org)){
                orgid1 = orgid2 = org.getOrgid();
                if("11".equals(org.getUnitlevel())){
                    orgid1 = orgService.getOrginfoByOrgid(org.getPorgid()).getOrgid();
                    orgname1 = orgService.getOrginfoByOrgid(org.getPorgid()).getName();
                    orgname2 = orgService.getOrginfoByOrgid(org.getOrgid()).getName();
                }else{
                	orgname1 = orgname2 = orgService.getOrginfoByOrgid(org.getOrgid()).getName();
                }
            }
        }
				
		for(int i=0;i<ids.length;i++){
			basecrime.setCrimid(ids[i]);
			basecrime.setOrgid1(orgid1);
			basecrime.setOrgid2(orgid2);
			basecrime.setOrgname1(orgname1);
			basecrime.setOrgname2(orgname2);
			sentence.setCrimid(ids[i]);
			sentence.setAreaid(orgid2);
			sentence.setAreaname(orgname2);
			countnum = prisonerService.updateTbprisonerBaseCrime(basecrime);
			if(countnum == 1){
				countnum = prisonerService.updateTbxfSentenceAlteration(sentence);
			}
		}
		
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JQDD+LogCommon.OPERATE);
		log.setOpaction(LogCommon.EDIT);
		log.setOpcontent(LogCommon.JQDD+LogCommon.EDIT);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JQDD+LogCommon.EDIT);
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}	
	
		return "success";
	}
	
}

