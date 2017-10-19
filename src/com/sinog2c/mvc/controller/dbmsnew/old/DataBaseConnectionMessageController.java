package com.sinog2c.mvc.controller.dbmsnew.old;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
 
    /**  
	 * 项目名称：jyzngzpt
	 * 类名称：DataBaseConnectionMessageAaction
     * 此类描述的是：   数据库管理
     * @author: 李祺亮  
     * @version: Nov 26, 2012 11:43:31 AM    
     */   
@Controller
@RequestMapping("/dbms")    
public class DataBaseConnectionMessageController extends ControllerBase{
	@Resource
	public DbmsDatabaseNewService dbmsDatabaseNewService;
	
//	DataBaseConnectionMssageBean bean=new DataBaseConnectionMssageBean();
//	public DataBaseConnectionMssageBean getModel() {
//		
//		return bean;
//	}
//    private HttpServletRequest request ;
//	public void setServletRequest(HttpServletRequest req) {
//		this.request=req;
//	}
//	private DataBaseConnectionMessageImpl connectionModel;
//	public void setConnectionModel(DataBaseConnectionMessageImpl connectionModel) {
//		this.connectionModel = connectionModel;
//	}
//	private CommonTemplateUtil commonTempletUtil;
//	public void setCommonTempletUtil(CommonTemplateUtil commonTempletUtil) {
//		this.commonTempletUtil = commonTempletUtil;
//	}
//	 
//	    /**   
//	     * 此方法描述的是：获取到数据库管理列表
//		 * @param   name  
//		 * @param  @return 
//		 * @Exception 异常对象
//	     * @author: 李祺亮   
//	     * @version: Nov 26, 2012 11:43:54 AM   
//	     */   
//	    
	@RequestMapping(value = "/getConnectionMessageList.action")
	@ResponseBody
	public Object getConnectionMessageList(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
		String departid = "";
		if(user!=null){
			departid = user.getDepartid();
		}
		Map<String, Object> resultmap = new HashMap<String,Object>();
		//Map<String,Object>  data = new HashMap<String,Object> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("sdid", departid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			
	    	int count = dbmsDatabaseNewService.allCount(map);
	    	List<Map> data = null;//dbmsDatabaseNewService.getConnectionMessageForPage(map);
	    	if(data!=null &&!data.isEmpty()){
	    		
	    	}
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
}

