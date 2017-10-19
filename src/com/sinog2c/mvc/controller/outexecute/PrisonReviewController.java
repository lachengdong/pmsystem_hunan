package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;

/**
 * 监区评议
 * 
 * @author
 * 
 */
@Controller
public class PrisonReviewController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;

	/**
	 * 新增页面
	 * 
	 * @author hzl
	 * @throws Exception
	 *             模板编号：BWJY_ZYJWZXSPB
	 */
	@RequestMapping(value = "/showPrisonAreaCouncilAddForm")
	public ModelAndView showAddFrom(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		String crimid = request.getParameter("crimid");
		TbprisonerBaseinfo tbprisonerBaseinfo = prisonerService
				.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService
				.getCrimeByCrimid(crimid);
		String tempid = "BWJY_ZYJWZXSPB";

		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null,
				tempid, crimid,deptid);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		} else {
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
			Map<String, Object> map = new HashMap<String, Object>();
			SystemOrganization org = systemOrganizationService
					.getByOrganizationId(deptid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (org != null) {
				map.put("text23", org.getFullname());
			}
			if (tbprisonerBaseinfo != null) {
				map.put("cbiname", tbprisonerBaseinfo.getName());
				map.put("cbigendername", tbprisonerBaseinfo.getGender());
				map.put("cbination", tbprisonerBaseinfo.getNation());
				map.put("cbibirthday", sdf.format(tbprisonerBaseinfo
						.getBirthday()));
				map.put("cbinativenamedetail", tbprisonerBaseinfo
						.getRegisteraddressdetail());
				map.put("text22", tbprisonerBaseinfo.getAddressdetail());

			}

			if (tbprisonerBaseCrime != null) {
				String y = tbprisonerBaseCrime.getLosepoweryear().toString();
				String m = tbprisonerBaseCrime.getLosepowermonth().toString();
				String d = tbprisonerBaseCrime.getLosepowereday().toString();
				String fa = tbprisonerBaseCrime.getForfeit();
				String boz = "剥夺政治权利";
				String fajin = "";
				if (fa != null) {
					fajin = "处罚金" + fa + "元";
				}
				if (y != null && !("".equals(y))) {
					boz += y + "年";
				}
				if (m != null && !("".equals(m))) {
					boz += y + "月";
				}
				if (d != null && !("".equals(d))) {
					boz += d + "天";
				}
				if (!"".equals(fajin)) {
					map.put("fujiaxing", fajin);
				}
				if (!("剥夺政治权利".equals(boz))) {
					boz += fajin;
					map.put("fujiaxing", boz);
				}
				map.put("anyouhuizong", tbprisonerBaseCrime.getCharge());
				map.put("cjicourtname", tbprisonerBaseCrime.getCourttype());
				map.put("zhuxing", tbprisonerBaseCrime.getPunishmentyear()
						+ "年" + tbprisonerBaseCrime.getPunishmentmonth() + "个月"
						+ tbprisonerBaseCrime.getPunishmentday() + "天");
				map.put("prisonterm", "");// 刑期变动情况
				map.put("xingqiqizhi", "自"
						+ sdf.format(tbprisonerBaseCrime.getSentencestime())
						+ "至"
						+ sdf.format(tbprisonerBaseCrime.getSentenceetime()));
				map.put("opccasetype", "");// 保外类别
				map.put("fanzuishishi", "");// 主要犯罪事实
				map.put("opcchangebehava", "");// 改造表现

			}
			map.put("crimid", crimid);
			request.setAttribute("crimid", crimid);
			request.setAttribute("tempid", tempid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			request
					.setAttribute("formDatajson", new JSONObject(map)
							.toString());

		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView(
				"/WEB-INF/JSP/aip/addPrisonAreaCouncil.jsp"));
	}

	

}
