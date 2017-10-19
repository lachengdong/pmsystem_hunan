package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;


/***
 * 监外执行-监狱文书-罪犯保外就医审核委员会会议记录
 * @author liyufeng
 *
 */
@Controller
public class ZFBWJYSHWYHJLContraller extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	/**
	 * 跳转罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/ZFBWJYSHWYHJLChoose")
	public ModelAndView ZFBWJYSHWYHJLCriminalChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/ZFBWJYSHWYHJLChoose");
	}
	/***
	 * 获得列表页面数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getZFBWJYSHWYHJLChooseList")
	@ResponseBody
	public Object getZFBWJYSHWYHJLChooseList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getDepartid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "other_zfljtzsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
	/***
	 * 跳转到表单页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/showZFBWJYSHWYHJL")
	public ModelAndView showZFBWJYSHWYHJL(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];//罪犯编号就从数组里取出第一个罪犯编号
		}
		request.setAttribute("crimid", crimid);
		String tempid = "ZFBWJYSHWYHHYJL";
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		String xuhao = tbsysDocumentService.getXuHao(year, tempid,null, departid);//获取序号
		TbxfSentenceAlteration tb = sentenceAlterationService.selectByPrimaryKey(crimid);
		if(info!=null){
			map.put("xingming", info.getName());
			map.put("xingbie", info.getGender());
			if(info.getBirthday()!=null){
			map.put("csdate", DateUtil.dateFormatForAip(info.getBirthday()));
			}
			map.put("address", info.getFamilyaddress());
		}
		if(crime!=null){
			map.put("zuiming", crime.getCauseaction());
			map.put("ypxq", crime.getRemark());
			if(crime.getSentenceetime()==null){
				map.put("xqqz", "");
			}else{
			map.put("xqqz", crime.getSentencestime()+"至"+crime.getSentenceetime());
			}
			map.put("rjdate", DateUtil.dateFormatForAip(crime.getInprisondate()));
		}
		if(tb!=null){
			String str = tb.getJailinfo()==null?"":tb.getJailinfo();
			map.put("xqbg", str);
		}
		map.put("year",year);
		map.put("xuhao", xuhao);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		String orgid = crime.getOrgid1();
		request.setAttribute("tempid", tempid);
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname", info.getName());
        request.setAttribute("orgid1",orgid);
        request.setAttribute("flowdefid", "other_zfljtzsp");
		returnResourceMap(request);
		return new ModelAndView("aip/showZFBWJYSHWYHJL");
	}
}

