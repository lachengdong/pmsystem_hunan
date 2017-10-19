package com.sinog2c.mvc.controller.penaltyPerform;

import java.util.ArrayList;
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
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;

/**
 * @author kexz
 *批量出监
 * 2014-7-17
 */
@Controller
public class BatchOutPrison extends ControllerBase{
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	@Autowired
	private PrisonerService prisonerService;
	@Resource
	public SystemLogService logService;
	
	@RequestMapping("/batchOutPrison")
	public ModelAndView batchOutPrison(){
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/batchOutPrison.jsp"));
	}
	
	/**
	 * @author kexz
	 *批量出监 ---初始化加载时获取刑期变动的羁押状态为'待出监'的罪犯列表
	 * 2014-9-11
	 */
	@RequestMapping(value = "/getOutPrisonCriminalList")
	@ResponseBody
	public Object getOutPrisonCriminalList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String key2 = request.getParameter("key2")==null?"":request.getParameter("key2");
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			SystemUser user = getLoginUser(request);
			
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("key2", key2);
			map.put("departid", user.getDepartid());
			map.put("orgid", user.getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	//根据map传参获取总条数
	    	int count = sentenceAlterationService.SelectWaitOutPrisonListCount(map);
	    	//根据map传参获取罪犯列表
	    	data=sentenceAlterationService.SelectWaitOutPrisonList(map);
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		
		return resultmap;
	}
	
	/**
	 * 
	* @author kexz
	*拟定出监
	* 2014-9-11
	 */
	@RequestMapping(value = "/outPrison")
	@ResponseBody
	public Object outPrison(HttpServletRequest request,
			HttpServletResponse response){
		SystemUser user = getLoginUser(request);
		int countnum = 0;//保存结果：0、失败，1、成功
		short statu = 0;
		TbprisonerBaseCrime basecrime=new TbprisonerBaseCrime();
		String strids=request.getParameter("crimid");
		String[] ids=strids.split(",");
		for(int i=0;i<ids.length;i++){
			basecrime.setCrimid(ids[i]);
			basecrime.setDetainstatus(GkzxCommon.SHIFANG);
			countnum = prisonerService.updateTbprisonerBaseCrime(basecrime);
		}
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.PLCJ+LogCommon.OPERATE);
		log.setOpaction(LogCommon.EDIT);
		log.setOpcontent(LogCommon.PLCJ+LogCommon.EDIT);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.PLCJ+LogCommon.EDIT);
	if(countnum == 1) statu = 1;//如果新增或修改成功，日志记录成功
	log.setStatus(statu);
	try {
		logService.add(log, user);
	} catch (Exception e) {
		// 吃掉异常
	}	
		return "success";
	}
	
	/**
	 * 获取当前最新批次
	 * 2015-10-27
	 * 
	 * */
	@RequestMapping(value="getCurrentBatch.json")
	@ResponseBody
	public Object getCurrentBatch(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();//获取部门id
		 Map map = prisonerService.getCurrentBatch(departid);
		 String batch = map.get("batch").toString();
	 return batch;	
	}
	
}
