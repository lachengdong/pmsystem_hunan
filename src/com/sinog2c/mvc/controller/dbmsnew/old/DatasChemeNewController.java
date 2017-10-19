package com.sinog2c.mvc.controller.dbmsnew.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeNewService;

/**  
 * 项目名称：pmsys
 * 类名称：AjaxDbmsNewController
 * 此类描述的是：   数据导入导出Ajax
 * @author lxf 2014-8-12 12:32:59   
 */ 
@Controller
@RequestMapping("/dbms")
public class DatasChemeNewController extends ControllerBase{

	@Autowired
	private DbmsDatasChemeNewService dbmsDatasChemeNewService;
	
	
}
