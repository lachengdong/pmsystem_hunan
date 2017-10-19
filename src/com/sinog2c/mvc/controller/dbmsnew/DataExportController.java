package com.sinog2c.mvc.controller.dbmsnew;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.dbmsnew.TaskBean;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DbmsDaochuListInfoService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 数据导出
 */
@Controller
@RequestMapping("/dbms")
public class DataExportController extends ControllerBase {

	@Autowired
	private DbmsDaochuListInfoService daochuListInfoService;
	@Autowired
	private DbmsNewDataExportService dataExportService;
	@Autowired
	private FlowArchivesService flowArchivesService;
	@Autowired
	private FlowBaseService flowBaseService;
	/**
	 * 数据导出页面
	 */
	@RequestMapping(value = "/dataexportmanage.page")
	public String dataExportManage() {
		return "dbms/dataexportmanage";
	}
	
	@RequestMapping(value = "/dataexportadd.page")
	public String dataExportAdd() {
		return "dbms/dataexportadd";
	}
	
	/*********************
	 * @ 跳转数据方案tabs主列表页
	 * 
	 * @author lxf 2014-8-12 12:32:59
	 *********************/
	@RequestMapping(value = "/toDbmsTabPage.action")
	public String toDbmsTabPage() {
		return "dbms/decryptListPage";
	}	
	
	/**
	 * 档案解密页面
	 * 
	 * @return
	 */
	@RequestMapping("/todecryptarchives.action")
	public Object toImportArchives(HttpServletRequest request) {
		String applyids = request.getParameter("applyids");
		request.setAttribute("applyids", applyids);
		//资源对象
		ModelAndView mav;
		mav= new ModelAndView("dbms/decryptarchives");
		
		return mav;
	}
	
	/**
	 * 档案解密页面
	 * 
	 * @return
	 */
	@RequestMapping("/getOneArchive.action")
	@ResponseBody
	public Object getOneArchive(HttpServletRequest request){
		String applyids = request.getParameter("applyids");
		request.setAttribute("applyids", applyids);
		String archiveid = request.getParameter("archiveid");
		request.setAttribute("archiveid", archiveid);
		Object obj = flowArchivesService.getOneArchive(applyids,archiveid);
		return obj;
	}
	
	/**
	 * 下载列表
	 */	
	@RequestMapping(value = "/ajax/listdataexport.json")
	@ResponseBody
	public Object listDataExport(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		map.put("sdid", departid);
		
		//
		JSONMessage message = new JSONMessage();
		int total = daochuListInfoService.countByCondition(map);
		message.setTotal(total);
		
		if(total > 0){
			List<DbmsDaochuListInfo> downloadList =  daochuListInfoService.listByCondition(map);
			message.setData(downloadList);
			message.setSuccess();
		}
		
		return message;
	}

	/**
	 * 删除记录
	 */	
	@RequestMapping(value = "/ajax/deleteDataExport.json")
	@ResponseBody
	public Object deleteDataExport(HttpServletRequest request) {
		Map<String, String> map = parseParamMapString(request);
		//
		String fileid = map.get("fileid");
		String filepath = GetAbsolutePath.getAbsolutePath("") + map.get("filepath").substring(1);
		//
		JSONMessage message = new JSONMessage();
		String info = "删除失败";
		int total = daochuListInfoService.deleteDataExport(fileid,filepath);
		
		message.setTotal(total);
		if(total > 0){
			info = "删除成功";
			message.setSuccess();
		}
		message.setInfo(info);
		return message;
	}
	
	/**
	 * 提交到法院
	 */	
	@RequestMapping(value = "/ajax/dataSubmitToFrontMachine.json")
	@ResponseBody
	public Object dataSubmitToFrontMachine(HttpServletRequest request) {
		Map<String, String> map = parseParamMapString(request);
		String fileids = map.get("fileid");
		//
		JSONMessage message = new JSONMessage();
		String info = "提交失败";
		this.setSessionAttribute(request, "percent", "数据处理中...");
		boolean rtnResult = dataExportService.dataSubmitToFrontMachine(request,fileids);
		
		if(rtnResult){
			info = "提交成功";
			message.setSuccess();
		}
		message.setInfo(info);
		return message;
	}
	
	
	/**
	 * 获取前置机数据并组包
	 */	
	@RequestMapping(value = "/ajax/getPackageAndMergeZip.json")
	@ResponseBody
	public Object getPackageAndMergeZip(HttpServletRequest request) {
		//
		JSONMessage message = new JSONMessage();
		this.setSessionAttribute(request, "percent", "数据处理中...");
		SystemUser user = this.getLoginUser(request);
		String result = dataExportService.getPackageAndMergeZip(user,request);
		
		this.setSessionAttribute(request, "percent", "100%");
		
		message.setInfo(result);
		return message;
	}
	
	/**
	 * 添加导出记录
	 */	
	@RequestMapping(value = "/ajax/adddataexport.json")
	@ResponseBody
	public Object addDataExport(HttpServletRequest request) {
		//SystemUser user = getLoginUser(request);//获取当前登录的用户
		//String departid=user.getDepartid();//根据用户Id获取所在部门Id
		
		Map<String, Object> paraMap = parseParamMap(request);
		boolean validOK = true;
		String validError = "输入错误";
		
		JSONMessage message = new JSONMessage();
		String info = "执行失败";
		// ddcid
		String ddcid = (String)paraMap.get("ddcid");
		String queryuuid = (String)paraMap.get("queryuuid");
		String conditionMessage = (String)paraMap.get("conditionmessage");
		String depart = (String)paraMap.get("depart");
		String chkpackageper = (String)paraMap.get("chkpackageper");
		String operate = "daochu";
		//String operatetype = GkzxCommon.FAYUAN;
		// 还有其他参数
		//
		if(StringNumberUtil.isEmpty(ddcid)){
			validOK = false;
			validError = "参数错误: ddcid";
		}
		//
		if(validOK){
			final DbmsNewDataExportBean bean = new DbmsNewDataExportBean();
			bean.setDdcid(ddcid);
			bean.setOperate(operate);
			bean.setQueryuuid(queryuuid);
//			bean.setHiddencon(conditionMessage);
			bean.setCondition(conditionMessage);
			bean.setDepart(depart);
			bean.setPackageper(chkpackageper);
			// 如果提交法院
			// bean.setOperatetype(operatetype);
			final TaskBean taskBean = new TaskBean();
			//
			boolean success = true;
			//boolean success = dataExportService.addDataExportRequest(bean, taskBean);
			taskBeanMap.put(queryuuid, taskBean);
			//改动
			new Thread(new Runnable() {
				@Override
				public void run() {
					 executeDataInterchange(bean,taskBean);
				}
			},"[手动执行全部数据导出线程]").start();
			try {
			} catch (Exception e) {
				success = false;
				info = e.getMessage();
			}
			//
			if(success){
				info = "已添加数据导出作业";
				message.setSuccess();
			}
			//
			message.setInfo(info);
		} else {
			message.setInfo(validError);
		}
		//
		return message;
	}
	// 
	private void executeDataInterchange(DbmsNewDataExportBean bean, TaskBean taskBean){
		DbmsNewDataExportService dataExport = dataExportService.getNewInstance();
		String info = "添加失败";
		try {
			//
			taskBean.setStatus(TaskBean.STATUS_INIT);
			//
			boolean success = dataExport.addDataExportRequest(bean,taskBean);
			try {
				if(success){
					taskBean.setStatus(TaskBean.STATUS_SUCCESS);
				} else {
					taskBean.setStatus(TaskBean.STATUS_FAILURE);
				}
			} catch (Exception e) {
					
			}
				
		} catch (Exception e) {
		}
	};
	/**
	 * 导出记录列表显示
	 */	
	@RequestMapping(value = "/ajax/ajaxExportDataList.json")
	@ResponseBody
	public Object ajaxExportDataList(HttpServletRequest request) {
		
		Map<String, Object> paraMap = parseParamMap(request);
		
		// 获取列表数据
		List<Map> list = new ArrayList<Map>();
		list = flowBaseService.ajaxExportDataList(paraMap);
		//
		return list;
	}
	

	/**
	 * 描述：跳转至新版数据导出页面
	 * @author YangZR	2015-12-30
	 */
	@RequestMapping(value = "/toPublicDataExportPage.page")
	public String toPublicDataExportPage(){
		return "dbms/publicDataExportPage";
	}
	
}
