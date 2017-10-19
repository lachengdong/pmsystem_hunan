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
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;

/***
 * 保外就医罪犯收监执行审查表
 *
 */
@Controller
public class BaowaijiuyiController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	/**
	 * 跳转保外就医罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/BaowaijiuyiChoose")
	public ModelAndView baowaijiuyiCriminalChoose(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		return new ModelAndView("chooseCriminal/baowaijiuyiChoose");
	}
	/***
	 * 获得列表页面数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getBaowaijiuyiChooseList")
	@ResponseBody
	public Object getBaowaijiuyiChooseList(HttpServletRequest request,
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
			map.put("departId", getLoginUser(request).getOrgid());
			map.put("userid", getLoginUser(request).getUserid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_bwjyzfsjzx");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	/***
	 * 跳转到保外就医表单页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/showBaowaijiuyi")
	public ModelAndView showBaowaijiuyi(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		request.setAttribute("crimid", crimid);
		String tempid = "TJBWJY";
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
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
				map.put("name", info.getName());
				Date birthday = info.getBirthday(); 
				String age = DateUtil.getAge(birthday);
				map.put("age",age);
				map.put("gender", info.getGender());
			    String str2=info.getRegisteraddressdetail()==null?"":info.getRegisteraddressdetail();
			    map.put("huji",str2);
			    String str3=info.getFamilyaddress()==null?"":info.getFamilyaddress();
		    map.put("address", str3);
			}
			if(crime!=null){
				map.put("zuiming", crime.getCauseaction());
				map.put("fujiaxing", crime.getSanremark());
				map.put("xingqi", crime.getRemark());
				map.put("start",DateUtil.dateFormatForAip(crime.getSentencestime()));
				map.put("end",DateUtil.dateFormatForAip(crime.getSentenceetime()));
			}
			if(tb!=null){
				map.put("xingqibiangeng", tb.getSentencechageinfo());
				map.put("guanyadanwei", tb.getAreaname());
			}
			map.put("year",year);
			map.put("xuhao", xuhao);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname", info.getName());
        request.setAttribute("orgid",crime.getOrgid1());
        request.setAttribute("flowdefid", "doc_bwjyzfsjzx");
        request.setAttribute("menuid", menuid);
		return new ModelAndView("aip/showBaowaijiuyi");
	}
}
