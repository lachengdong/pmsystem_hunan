package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.TbxfScreeningService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
/**
 * 资格筛查
 * 
 * @author hzl
 * 
 */
@Controller
public class QualificationScreeningController extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private TbxfScreeningService tbxfScreeningService;
	/**
	 * 跳转列表页
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCommutationParolePage")
	public ModelAndView toTabsPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/commutationparolelistpage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 获取表单数据
	 * @param request
	 * @param response
	 * @return
	 * @author wangxf
	 */
	@RequestMapping("/commutationparolelist")
	public ModelAndView commutationparolelist(HttpServletRequest request, HttpServletResponse response) {
		
		
		String crimid=request.getParameter("criminalid");
		String menuid=request.getParameter("menuid");
		String tempid=request.getParameter("tempid");
		
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		
		
		//获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		//通过部门id去找所在单位名称
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid,user.getDepartid());
		
		String xuhao = tbsysDocumentService.getXuHao(null, tempid, null, null);
		
		int count = tbsysDocumentService.getCount("", tempid, crimid, "");
		if(count == 0){
			Map<String,Object> map = new HashMap<String,Object>();
			if(org!=null){
				map.put("text1", org.getName());
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			map.put("text2", df.format(new Date()));
			map.put("criminalid", crimid);
			String name = "";
			TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime baseCrime= prisonerService.getCrimeByCrimid(crimid);
			try {
				name=baseinfo.getName();
				String birthday=DateUtil.dateFormat(baseinfo.getBirthday());
				map.put("cbiname", name);	
				map.put("cbibirthday", birthday);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(baseinfo!=null){
				map.put("cbination", baseinfo.getNation());
				map.put("cbihomeaddress", baseinfo.getFamilyaddress());
				map.put("caieducation", baseinfo.getEducation());
				map.put("aiformervocation", baseinfo.getVocation());
				map.put("yuanpanxingqi", baseinfo.getRank());
				map.put("", baseCrime.getStolenmoney());
			}
			if(baseCrime!=null){
				String inprisondate=DateUtil.dateFormat( baseCrime.getInprisondate());
				map.put("criofficiallyplacedate",inprisondate);
				map.put("anyouhuizong", baseCrime.getCharge());
				
			}	
			if (template != null) {
				docconent.add(template.getContent());
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
			request.setAttribute("operator", GkzxCommon.NEW);
		}else{
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
			if (document != null) {
				docconent.add(document.getContent());
				request.setAttribute("docid", document.getDocid());
			}
			request.setAttribute("operator", GkzxCommon.EDIT);
		}

		
		//转json
		
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
	
		ModelAndView mav = new ModelAndView("aip/resourcecrud");

		return mav;
	}

	

}
