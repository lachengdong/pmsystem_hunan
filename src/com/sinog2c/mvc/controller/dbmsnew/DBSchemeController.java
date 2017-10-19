package com.sinog2c.mvc.controller.dbmsnew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNewKey;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeDetailNewService;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeNewService;
import com.sinog2c.service.api.dbmsnew.DbmsDatasTableNewService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.dbms.DBMSMetaUtil;

import flexjson.JSONDeserializer;

/**
 * 数据交换使用的数据库连接配置信息管理
 */
@Controller
@RequestMapping("/dbms")
public class DBSchemeController extends ControllerBase {

	@Autowired
	private DbmsDatasChemeNewService datasChemeNewService;
	@Autowired
	private DbmsDatabaseNewService databaseNewService;
	@Autowired
	private DbmsDatasTableNewService tableNewService;
	@Autowired
	private DbmsDatasChemeDetailNewService detailNewService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;

	/**
	 * 数据方案管理,页面
	 */
	@RequestMapping(value = "/dbschememanage.page")
	public String dbschememanage() {
		return "dbms/dbschememanage";
	}

	/**
	 * 添加配置方案的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dbschemeadd.page")
	public String dbschemeaddPage(HttpServletRequest request) {
		String viewName = "dbms/dbschemeaddinterchange";
		//
		String ddctype = request.getParameter("ddctype");
		//
		if ("1".equals(ddctype)) {
			viewName = "dbms/dbschemeaddexp"; // 导出
		} else if ("0".equals(ddctype)) {
			viewName = "dbms/dbschemeaddimp"; // 导入
		} else {
			viewName = "dbms/dbschemeaddinterchange"; // 交换
		}
		//
		return viewName;
	}
	/**
	 * 拷贝交换方案
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/copyswapscheme.page")
	public String copyswapschemePage(HttpServletRequest request) {
		String viewName = "dbms/dbschemeaddinterchange";
		//
		String ddctype = request.getParameter("ddctype");
		//
		if ("1".equals(ddctype)) {
			viewName = "dbms/dbschemeaddexp"; // 导出
		} else if ("0".equals(ddctype)) {
			viewName = "dbms/dbschemeaddimp"; // 导入
		} else {
			viewName = "dbms/copyswapscheme"; // 交换
		}
		//
		return viewName;
	}

	/**
	 * 方案配置修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dbschemeedit.page")
	public String dbschemeeditPage(HttpServletRequest request) {
		String viewName = "dbms/dbschemeEditPage";
		String ddcid = request.getParameter("ddcid");
		String ddctype = request.getParameter("ddctype");
		request.setAttribute("ddcid", ddcid);
		request.setAttribute("ddctype", ddctype);
		return viewName;
	}
	// 导出页面弹出选择列
	@RequestMapping(value = "/selectcolumn.page")
	public String selectcolumnExportPage(HttpServletRequest request) {
		String viewName = "dbms/selectcolumnexport";
		return viewName;
	}

	// 导如页面弹出选择列
	@RequestMapping(value = "/selectcolumnimport.page")
	public String selectcolumnImportPage(HttpServletRequest request) {
		String viewName = "dbms/selectcolumnimport";
		return viewName;
	}

	// 交换页面弹出选择列
	@RequestMapping(value = "/selectcolumninterchange.page")
	public String selectcolumninterchange(HttpServletRequest request) {
		//
		// String tablename = request.getParameter("tablename");
		// String databaseid = request.getParameter("databaseid");
		//
		//
		String viewName = "dbms/selectcolumninterchange";
		return viewName;
	}

	/**
	 * 数据方案管理——列表,json
	 */
	@RequestMapping(value = "/ajax/listdbscheme.json")
	@ResponseBody
	public Object listDBScheme(HttpServletRequest request) {
		SystemUser operator = getLoginUser(request);// 获取当前登录的用户
		 String departid=operator.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		boolean isCopyMode = false;
		if(!StringNumberUtil.isNullOrEmpty(map.get("iscopymode")) && map.get("iscopymode").toString().equals(GkzxCommon.ONE)){
			isCopyMode = true;
		}
		 map.put("sdid", departid);
		int total = datasChemeNewService.countByCondition(map, operator,isCopyMode);
		String lv = operator.getOrganization().getUnitlevel();
		if(lv!=null && ("6".equals(lv)||"7".equals(lv))){
			map.put("departid", operator.getDepartid());
		}
		List<DbmsDatasChemeNew> databaseList = datasChemeNewService.listByCondition(map, operator,isCopyMode);

		JSONMessage message = new JSONMessage();
		message.setData(databaseList);
		message.setTotal(total);

		return message;
	}

	/**
	 * 列出数据表,json
	 */
	@RequestMapping(value = "/ajax/listdbtables.json")
	@ResponseBody
	public Object listDBTables(HttpServletRequest request) {
		// 获取参数, 导出数据库配置信息的ID
		String fromdatabaseid = getParameter(request, "fromdatabaseid");
		// 获取导出方案ID
		String ddcexpscheme = getParameter(request, "ddcexpscheme");

		// 根据导出方案
		if (StringNumberUtil.notEmpty(ddcexpscheme)) {
			return listDBTablesByDdcexpscheme(request);
		}
		// 根据 fromdatabaseid
		if (StringNumberUtil.notEmpty(fromdatabaseid)) {
			return listDBTablesByFromdatabaseid(request);
		}
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		message.setTotal(total);

		return message;
	}

	public Object listDBTablesByFromdatabaseid(HttpServletRequest request) {
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		// 获取参数, 导出数据库配置信息的ID
		String fromdatabaseid = getParameter(request, "fromdatabaseid");
		String onlydata = getParameter(request, "onlydata");
		// 根据ID获取对应的配置信息
		DbmsDatabaseNew databaseNew = databaseNewService.selectByPrimaryKey(fromdatabaseid);
		if (null != databaseNew) {
			//
			String databasetype = databaseNew.getDatabasetype();
			String ip = databaseNew.getDdip();
			String port = "" + databaseNew.getPort();
			String dbname = databaseNew.getDatabasename();
			String username = databaseNew.getDatabaseuser();
			String password = databaseNew.getDatabasepassword();
			//
			List<Map<String, Object>> tables = DBMSMetaUtil.listTables(databasetype, ip, port, dbname, username,
					password);
			//
			tables = MapUtil.convertKeyList2LowerCase(tables);
			//
			message.setData(tables);
			if (null != tables) {
				total = tables.size();
				message.setTotal(total);
				message.setSuccess();
			}
		}
		if("1".equals(onlydata)){
			return message.getData();
		}
		return message;
	}

	public Object listDBTablesByDdcexpscheme(HttpServletRequest request) {
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		// 获取导出方案ID
		// String ddcexpscheme = getParameter(request, "ddcexpscheme");
		// 根据方案ID获取对应的表
		Map<String, Object> map = parseParamMap(request);
		//
		List<DbmsDatasTableNew> tables = tableNewService.listByCondition(map);
		//
		//
		message.setData(tables);
		if (null != tables) {
			total = tables.size();
			message.setSuccess();
		}
		message.setTotal(total);

		return message;
	}

	//

	/**
	 * 列出数据导出、数据表的列,json
	 */
	@RequestMapping(value = "/ajax/listtablecolumns.json")
	@ResponseBody
	public Object listTableColumns(HttpServletRequest request) {
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		// 获取参数, 导出数据库配置信息的ID
		String tablename = getParameter(request, "tablename");
		String databaseid = getParameter(request, "databaseid");

		// 根据ID获取对应的列表信息
		List<Map<String, Object>> columns = jdbcGetTableColumns(tablename, databaseid);
		//
		columns = MapUtil.convertKeyList2LowerCase(columns);
		columns = MapUtil.trimListKeyValue(columns);
		// 增加列名转换功能
		Map<String, String> keyMap = generateToTableKeyMap();
		columns = MapUtil.convertKey2Another(columns, keyMap);
		//
		message.setData(columns);
		if (null != columns) {
			total = columns.size();
		}
		message.setTotal(total);

		return message;
	}


	/**
	 * 列出数据交换、导入数据表的列,json
	 */
	@RequestMapping(value = "/ajax/listtotablecolumns.json")
	@ResponseBody
	public Object listSwapToTableColumns(HttpServletRequest request) {
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		// 获取参数, 导出数据库配置信息的ID
		String tablename = getParameter(request, "tablename");
		String databaseid = getParameter(request, "databaseid");
		// 根据ID获取对应的配置信息
		List<Map<String, Object>> columns = jdbcGetTableColumns(tablename, databaseid);
		//
		columns = MapUtil.convertKeyList2LowerCase(columns);
		columns = MapUtil.trimListKeyValue(columns);
		// 增加列名转换功能
		Map<String, String> keyMap = generateToTableKeyMap();
		columns = MapUtil.convertKey2Another(columns, keyMap);
		// 设置默认值
		Map<String, Object> valueMap = generateInterchangeValueMap();
		columns = MapUtil.setDefaultValue(columns, valueMap);
		//
		message.setData(columns);
		if (null != columns) {
			total = columns.size();
			message.setSuccess();
		}
		message.setTotal(total);

		return message;
	}

	/**
	 * 列出数据交换、导出数据表的列,json
	 */
	@RequestMapping(value = "/ajax/listfromtablecolumns.json")
	@ResponseBody
	public Object listSwapFromTableColumns(HttpServletRequest request) {
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		// 获取参数, 导出数据库配置信息的ID
		String tablename = getParameter(request, "tablename");
		String databaseid = getParameter(request, "databaseid");
		String ddcexpscheme = getParameter(request, "ddcexpscheme");
		if(StringNumberUtil.isEmpty(databaseid)){
			databaseid = parseDatabaseidFromddcexpscheme(ddcexpscheme);
		}
		// 根据ID获取对应的配置信息
		List<Map<String, Object>> columns = jdbcGetTableColumns(tablename, databaseid);
		//
		columns = MapUtil.convertKeyList2LowerCase(columns);
		columns = MapUtil.trimListKeyValue(columns);
		// 增加列名转换功能
		Map<String, String> keyMap = generateFromTableKeyMap();
		columns = MapUtil.convertKey2Another(columns, keyMap);
		// 设置默认值
		Map<String, Object> valueMap = generateInterchangeValueMap();
		columns = MapUtil.setDefaultValue(columns, valueMap);
		//
		message.setData(columns);
		if (null != columns) {
			total = columns.size();
			message.setSuccess();
		}
		message.setTotal(total);

		return message;
	}

	@RequestMapping(value = "/ajax/listimpfromtablecolumns.json")
	@ResponseBody
	public Object listImpFromTableColumns(HttpServletRequest request) {
		//
		int total = 0;
		JSONMessage message = new JSONMessage();
		// 获取参数, 导出数据库配置信息的ID
		String tablename = getParameter(request, "tablename");
		String databaseid = getParameter(request, "databaseid");
		String ddcexpscheme = getParameter(request, "ddcexpscheme");
		if(StringNumberUtil.isEmpty(databaseid)){
			databaseid = parseDatabaseidFromddcexpscheme(ddcexpscheme);
		}		
		// 根据ID获取对应的配置信息
		List<Map<String, Object>> columns = jdbcGetTableColumns(tablename, databaseid);
		//
		columns = MapUtil.convertKeyList2LowerCase(columns);
		columns = MapUtil.trimListKeyValue(columns);
		// 增加列名转换功能
		Map<String, String> keyMap = generateFromTableKeyMap();
		columns = MapUtil.convertKey2Another(columns, keyMap);
		// 设置默认值
		Map<String, Object> valueMap = generateInterchangeValueMap();
		columns = MapUtil.setDefaultValue(columns, valueMap);
		//
		message.setData(columns);
		if (null != columns) {
			total = columns.size();
			message.setSuccess();
		}
		message.setTotal(total);
		
		// .....................................
		// 按理说,这里应该从 detail中取数据出来
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ddcid", ddcexpscheme);
		map.put("ddcid", ddcexpscheme);
		// .....................................
		
		detailNewService.listByCondition(map);
		

		return message;
	}
	
	private String parseDatabaseidFromddcexpscheme(String ddcexpscheme) {
		//
		DbmsDatasChemeNew chemeNew = datasChemeNewService.getById(ddcexpscheme);
		if(null != chemeNew){
			return chemeNew.getFromdatabaseid();
		}
		return null;
	}

	/**
	 * 返回原生的 columns
	 * 
	 * @param request
	 * @return
	 */
	private List<Map<String, Object>> jdbcGetTableColumns(String tablename, String databaseid) {
		List<Map<String, Object>> columns = null;
		//
		// 根据ID获取对应的列表信息
		DbmsDatabaseNew databaseNew = databaseNewService.selectByPrimaryKey(databaseid);
		if (null != databaseNew) {
			//
			String databasetype = databaseNew.getDatabasetype();
			String ip = databaseNew.getDdip();
			String port = "" + databaseNew.getPort();
			String dbname = databaseNew.getDatabasename();
			String username = databaseNew.getDatabaseuser();
			String password = databaseNew.getDatabasepassword();
			//
			columns = DBMSMetaUtil.listColumns(databasetype, ip, port, dbname, username, password, tablename);
			//
		}
		//
		return columns;
	}

	private Map<String, Object> generateInterchangeValueMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		//
		map.put("dcdtocolumnspoint", 0);
		//
		return map;
	}

	private Map<String, String> generateToTableKeyMap() {
		Map<String, String> keyMap = new HashMap<String, String>();
		//
		keyMap.put("column_name", "dcdtocolumns");// 列名
		keyMap.put("remarks", "dcdtocolumnsscribe");// 描述
		keyMap.put("type_name", "dcdtocolumnstype");// 列类型
		keyMap.put("column_size", "dcdtocolumnssize");// 字段长度
		//keyMap.put("nullable", "nullable");// 可为NULL
		//keyMap.put("", "");
		keyMap.put("dcdtocloumnsdefaultvalue", "dcdtocloumnsdefaultvalue");
		keyMap.put("dcdtocolumnspoint", "dcdtocolumnspoint");
		keyMap.put("dcdcodetype", "dcdcodetype");
		keyMap.put("dcdifpkey", "dcdifpkey");
		keyMap.put("dcdcodetype", "dcdcodetype");
		keyMap.put("dcdpkgenerator", "dcdpkgenerator");
		keyMap.put("pkgenerator", "pkgenerator");
		//
		return keyMap;
	}
	private Map<String, String> generateFromTableKeyMap() {
		Map<String, String> keyMap = new HashMap<String, String>();
		//
		keyMap.put("column_name", "dcdfromcolumns");// 列名
		keyMap.put("remarks", "dcdfromcolumnsscribe");// 描述
		keyMap.put("type_name", "dcdfromcloumnstype");// 列类型
		keyMap.put("column_size", "dcdfromcloumnssize");// 字段长度
		//keyMap.put("nullable", "nullable");// 可为NULL
		keyMap.put("dcdfromcolumnspoint", "dcdfromcolumnspoint");
		//
		return keyMap;
	}

	
	// 解析表对象
	private DbmsDatasTableNew parseDbmsDatasTableNew(JSONObject jsObject) {
		if (null == jsObject) {
			return null;
		}
		//
		String tablename = StringNumberUtil.returnString2(jsObject.get("tablename"));
		String descrition = StringNumberUtil.returnString2(jsObject.get("descrition"));
		String totable = StringNumberUtil.returnString2(jsObject.get("totable"));
		String todescription = StringNumberUtil.returnString2(jsObject.get("todescription"));
		
		String ddtismaintable = StringNumberUtil.returnString2(jsObject.get("ddtismaintable"));
		String shujuguanxi = StringNumberUtil.returnString2(jsObject.get("shujuguanxi"));
		String addcondition = StringNumberUtil.returnString2(jsObject.get("addcondition"));
		String daochusql = StringNumberUtil.returnString2(jsObject.get("daochusql"));
		String ddtorderStr = StringNumberUtil.returnString2(jsObject.get("ddtorder"));
		//
		DbmsDatasTableNew tableNew = new DbmsDatasTableNew();
		tableNew.setTablename(tablename);
		tableNew.setDescrition(descrition);
		tableNew.setTotable(totable);
		if(StringNumberUtil.isEmpty(descrition)){
			tableNew.setDescrition(todescription);
		}

		tableNew.setDdtismaintable(ddtismaintable);
		tableNew.setShujuguanxi(shujuguanxi);
		tableNew.setAddcondition(addcondition);
		tableNew.setDaochusql(daochusql);
		tableNew.setDdtorder(StringNumberUtil.getIntByString(ddtorderStr));
		//
		return tableNew;
	}
	
	// 解析表对象
	private DbmsDatasTableNew parseDbmsDatasTableTo(JSONObject jsObject) {
		if (null == jsObject) {
			return null;
		}
		//
		//String tablename = StringNumberUtil.returnString2(jsObject.get("tablename"));
		String descrition = StringNumberUtil.returnString2(jsObject.get("descrition"));
		String totable = StringNumberUtil.returnString2(jsObject.get("totable"));
		String todescription = StringNumberUtil.returnString2(jsObject.get("todescription"));
		
		String ddtismaintable = "1";//StringNumberUtil.returnString2(jsObject.get("ddtismaintable"));
		String shujuguanxi = StringNumberUtil.returnString2(jsObject.get("shujuguanxi"));
		String addcondition = StringNumberUtil.returnString2(jsObject.get("addcondition"));
		String daochusql = StringNumberUtil.returnString2(jsObject.get("daochusql"));
		//
		DbmsDatasTableNew tableTo = new DbmsDatasTableNew();
		tableTo.setTablename(totable);
		tableTo.setDescrition(todescription);
		//tableTo.setTotable(totable);
		if(StringNumberUtil.isEmpty(todescription)){
			tableTo.setDescrition(descrition);
		}

		tableTo.setDdtismaintable(ddtismaintable); // to表不能为主表
		tableTo.setShujuguanxi(shujuguanxi);
		tableTo.setAddcondition(addcondition);
		tableTo.setDaochusql(daochusql);
		//
		return tableTo;
	}
	// 解析细节
	private DbmsDatasChemeDetailNew parseDbmsDatasChemeDetailNew(JSONObject col, DbmsDatasTableNew tableNew,
			DbmsDatasTableNew tableTo, DbmsDatasChemeNew schemeNew) {
		if (null == col) {
			return null;
		}
		if (null == tableNew) {
			return null;
		}
		if (null == schemeNew) {
			return null;
		}
		//
		String ddcid = schemeNew.getDdcid();
		String ddtid = tableNew.getDdtid();
		//
		String dcdtocolumns = StringNumberUtil.returnString2(col.get("dcdtocolumns"));
		String dcdtocolumnsscribe = StringNumberUtil.returnString2(col.get("dcdtocolumnsscribe"));
		String dcdtocolumnstype = StringNumberUtil.returnString2(col.get("dcdtocolumnstype"));
		String dcdtocolumnssizeS = StringNumberUtil.returnString2(col.get("dcdtocolumnssize"));
		String dcdtocloumnsdefaultvalue = StringNumberUtil.returnString2(col.get("dcdtocloumnsdefaultvalue"));
		
		String dcdtocolumnspointS = StringNumberUtil.returnString2(col.get("dcdtocolumnspoint"));
		String dcdcodetype = StringNumberUtil.returnString2(col.get("dcdcodetype"));
		String dcdifpkey = StringNumberUtil.returnString2(col.get("dcdifpkey"));
		String dcdpkgenerator = StringNumberUtil.returnString2(col.get("dcdpkgenerator"));
		
		String dcdfromcolumns = StringNumberUtil.returnString2(col.get("dcdfromcolumns"));
		String dcdfromcolumnsscribe = StringNumberUtil.returnString2(col.get("dcdfromcolumnsscribe"));
		String dcdfromcloumnstype = StringNumberUtil.returnString2(col.get("dcdfromcloumnstype"));
		String dcdfromcloumnssizeS = StringNumberUtil.returnString2(col.get("dcdfromcloumnssize"));
		String dcdfromcolumnspointS = StringNumberUtil.returnString2(col.get("dcdfromcolumnspoint"));
		//
		Long dcdtocolumnssize = StringNumberUtil.parseLong(dcdtocolumnssizeS, 0L);
		Long dcdfromcloumnssize = StringNumberUtil.parseLong(dcdfromcloumnssizeS, 0L);
		int dcdtocolumnspoint = StringNumberUtil.parseInt(dcdtocolumnspointS, 0);
		int dcdfromcolumnspoint = StringNumberUtil.parseInt(dcdfromcolumnspointS, 0);
		// 新对象,方案细节
		DbmsDatasChemeDetailNew chemeDetailNew = new DbmsDatasChemeDetailNew();
		chemeDetailNew.setDdcid(ddcid);
		chemeDetailNew.setDdtid(ddtid);
		//
		chemeDetailNew.setDcdtocolumns(dcdtocolumns);
		chemeDetailNew.setDcdtocolumnsscribe(dcdtocolumnsscribe);
		chemeDetailNew.setDcdtocolumnstype(dcdtocolumnstype);
		chemeDetailNew.setDcdtocolumnssize(dcdtocolumnssize);
		chemeDetailNew.setDcdtocloumnsdefaultvalue(dcdtocloumnsdefaultvalue);
		
		chemeDetailNew.setDcdtocolumnspoint(dcdtocolumnspoint);
		chemeDetailNew.setDcdcodetype(dcdcodetype);
		chemeDetailNew.setDcdifpkey(dcdifpkey);
		chemeDetailNew.setDcdpkgenerator(dcdpkgenerator);
		
		chemeDetailNew.setDcdfromcolumns(dcdfromcolumns);
		chemeDetailNew.setDcdfromcolumnsscribe(dcdfromcolumnsscribe);
		chemeDetailNew.setDcdfromcloumnstype(dcdfromcloumnstype);
		chemeDetailNew.setDcdfromcloumnssize(dcdfromcloumnssize);// 字段长度
		chemeDetailNew.setDcdfromcolumnspoint(dcdfromcolumnspoint);
		// 
		if (null != tableTo) {
			// 如果有To表
			String dcdtotableid = tableTo.getDdtid();
			chemeDetailNew.setDcdtotableid(dcdtotableid);
		}
		
		//
		return chemeDetailNew;
	}

	// 解析细节
	private DbmsDatasChemeDetailNew parseDbmsDatasChemeDetailNew(Map<String, Object> colMap,
			DbmsDatasTableNew tableNew, DbmsDatasTableNew tableTo, DbmsDatasChemeNew schemeNew) {
		if (null == colMap) {
			return null;
		}
		if (null == tableNew) {
			return null;
		}
		if (null == schemeNew) {
			return null;
		}
		//
		String ddcid = schemeNew.getDdcid();
		//
		String ddtid = tableNew.getDdtid();
		//
		String dcdfromcolumns = StringNumberUtil.returnString2(colMap.get("column_name"));
		String dcdfromcolumnsscribe = StringNumberUtil.returnString2(colMap.get("remarks"));
		String dcdfromcloumnstype = StringNumberUtil.returnString2(colMap.get("type_name"));
		//
		Object column_size = colMap.get("column_size");
		Long dcdfromcloumnssize = StringNumberUtil.parseLong(StringNumberUtil.returnString2(column_size), 0L);
		// 新对象,方案细节
		DbmsDatasChemeDetailNew chemeDetailNew = new DbmsDatasChemeDetailNew();
		chemeDetailNew.setDdcid(ddcid);
		chemeDetailNew.setDdtid(ddtid);
		//
		chemeDetailNew.setDcdfromcolumns(dcdfromcolumns);
		chemeDetailNew.setDcdfromcolumnsscribe(dcdfromcolumnsscribe);
		chemeDetailNew.setDcdfromcloumnstype(dcdfromcloumnstype);
		chemeDetailNew.setDcdfromcloumnssize(dcdfromcloumnssize);
		//
		return chemeDetailNew;
	}

	/**
	 * 添加数据导出方案,返回值为 json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/adddbschemeexp.json")
	@ResponseBody
	public Object adddbschemeexp(HttpServletRequest request) {
		SystemUser operator = getLoginUser(request);// 获取当前登录的用户
		// String userid = operator.getUserid();
		// String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, String> map = parseParamMapString(request);
		//
		String ddcid = map.get("ddcid");
		String fromdatabaseid = map.get("fromdatabaseid");
		String ddcname = map.get("ddcname");
		String ddctype = map.get("ddctype");
		String tablelistS = map.get("tablelist");
		//
		boolean validOK = true;
		String validError = "输入错误";
		//
		if (StringNumberUtil.isEmpty(fromdatabaseid)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddcname)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddctype)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(tablelistS)) {
			validOK = false;
		}

		JSONMessage message = new JSONMessage();
		if (!validOK) {
			message.setInfo(validError);
			return message;
		}
		//
		String dateStr = Long.toHexString(System.currentTimeMillis());

		// 方案对应的表
		List<DbmsDatasTableNew> tableList = new ArrayList<DbmsDatasTableNew>();
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = new ArrayList<DbmsDatasChemeDetailNew>();
		//
		// FIXME
		DbmsDatasChemeNew schemeNew = new DbmsDatasChemeNew();
		//
		schemeNew.setDdcid(ddcid);
		schemeNew.setDdcname(ddcname);
		schemeNew.setDdctype(ddctype);
		schemeNew.setFromdatabaseid(fromdatabaseid);
		schemeNew.setTableList(tableList);
		schemeNew.setDetailList(detailList);
		//
		JSONArray array = JSONArray.parseArray(tablelistS);
		//
		int lenT = array.size();
		int dnum = 1;
		for (int i = 0; i < lenT; i++) {
			// 
			Object o = array.get(i);
			if (!(o instanceof JSONObject)) {
				continue;// 不要嵌套太深
			}
			JSONObject jsObject = (JSONObject) o;
			//
			String ddtid = dateStr + "EXP" + (dnum++);
			DbmsDatasTableNew tableNew = parseDbmsDatasTableNew(jsObject);
			if (null != tableNew) {
				// 塞值
				tableNew.setDdcid(ddcid);
				tableNew.setDdtid(ddtid);
				// 加入列表
				tableList.add(tableNew);
			} else {
				// 下一次循环
				continue; // 不要嵌套太深
			}
			//
			Object _columns = jsObject.get("columns");
			// 是否选中了列
			boolean hasColumns = false;
			if (_columns instanceof JSONArray) {
				JSONArray columns = (JSONArray) _columns;
				//
				ListIterator<Object> iteratorC = columns.listIterator();
				while (iteratorC.hasNext()) {
					Object object = (Object) iteratorC.next();
					if (object instanceof JSONObject) {
						JSONObject col = (JSONObject) object;
						// 单列,细节
						DbmsDatasChemeDetailNew chemeDetailNew = parseDbmsDatasChemeDetailNew(col, tableNew, null, schemeNew);
						if (null != chemeDetailNew) {
							//
							String dcdid = dateStr + (dnum++);
							chemeDetailNew.setDcdid(dcdid);
							//
							detailList.add(chemeDetailNew);
							// 标志位
							hasColumns = true;
						}
					}
				}
			}
			// 所有列.没选中,select *,则从工具类中获取
			if (hasColumns) {
				continue; // 不要嵌套太深了
			}
			// 根据ID获取对应的连接配置信息
			DbmsDatabaseNew databaseNew = databaseNewService.selectByPrimaryKey(fromdatabaseid);
			if (null == databaseNew) {
				continue; // 不要嵌套太深了
			}
			//
			String tablename = tableNew.getTablename();
			String databasetype = databaseNew.getDatabasetype();
			String ip = databaseNew.getDdip();
			String port = "" + databaseNew.getPort();
			String dbname = databaseNew.getDatabasename();
			String username = databaseNew.getDatabaseuser();
			String password = databaseNew.getDatabasepassword();
			List<Map<String, Object>> columns = DBMSMetaUtil.listColumns(databasetype, ip, port, dbname, username,
					password, tablename);
			//
			columns = MapUtil.convertKeyList2LowerCase(columns);
			columns = MapUtil.trimListKeyValue(columns);
			//
			if (null != columns) {
				Iterator<Map<String, Object>> iteratorM = columns.iterator();
				while (iteratorM.hasNext()) {
					Map<String, Object> colMap = (Map<String, Object>) iteratorM.next();
					// 然后，组装对象
					DbmsDatasChemeDetailNew chemeDetailNew = parseDbmsDatasChemeDetailNew(colMap, tableNew, null, schemeNew);
					if (null != chemeDetailNew) {
						//
						String dcdid = dateStr + (dnum++);
						chemeDetailNew.setDcdid(dcdid);
						//
						detailList.add(chemeDetailNew);
					}
				}
			}
		}

		// 遍历数据表,根据信息获取对应的列,详细数据. 注意校验
		// 数据交换的一部分信息需要根据JSON传递过来,比如 ID编码不一致的地方
		// FIXME 请添加细节

		//
		int status = datasChemeNewService.add(schemeNew, operator);
		//
		// int islocalnew = getParameterInt(request, "islocalnew", 0);

		message.setStatus(status);
		message.setInfo(status > 0 ? "保存成功" : "保存失败");

		return message;
	}

	/**
	 * 添加数据导入方案,返回值为 json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/adddbschemeimp.json")
	@ResponseBody
	public Object adddbschemeimp(HttpServletRequest request) {
		SystemUser operator = getLoginUser(request);// 获取当前登录的用户
		
		Map<String, String> map = parseParamMapString(request);
		//
		String ddcid = map.get("ddcid");
		String ddcname = map.get("ddcname");
		String ddctype = map.get("ddctype");
		String todatabaseid = map.get("todatabaseid");
		String ddcexpscheme = map.get("ddcexpscheme");
		String tablelistS = map.get("tablelist");
		//
		boolean validOK = true;
		String validError = "输入错误";
		if (StringNumberUtil.isEmpty(ddcname)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddctype)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(tablelistS)) {
			validOK = false;
		}
		//
		if (StringNumberUtil.isEmpty(todatabaseid)) {
			validOK = false;
		} else {
			// 根据ID获取对应的连接配置信息
			DbmsDatabaseNew databaseTo = databaseNewService.selectByPrimaryKey(todatabaseid);
			if (null == databaseTo) {
				validOK = false;
			}
		}
		//
		if (StringNumberUtil.isEmpty(ddcexpscheme)) {
			validOK = false;
		} else{
			//
			DbmsDatasChemeNew schemeOLD = datasChemeNewService.getById(ddcexpscheme);
			if(null == schemeOLD){
				// 提示以及处理
				validOK = false;
				validError = ("导出方案不存在!");
			}
		}
		//
		JSONMessage message = new JSONMessage();
		if (!validOK) {
			message.setInfo(validError);
			return message;
		}
		
		//
		String dateStr = Long.toHexString(System.currentTimeMillis());

		//
		//Map<String, Object> conMap = new HashMap<String, Object>();
		//conMap.put("ddcid", ddcexpscheme);
		// 方案对应的表
		List<DbmsDatasTableNew> tableList = new ArrayList<DbmsDatasTableNew>();
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = new ArrayList<DbmsDatasChemeDetailNew>();
		//
		DbmsDatasChemeNew schemeNew = new DbmsDatasChemeNew();
		//
		schemeNew.setDdcid(ddcid);
		schemeNew.setDdcname(ddcname);
		schemeNew.setDdctype(ddctype);
		schemeNew.setTodatabaseid(todatabaseid);
		schemeNew.setDdcexpscheme(ddcexpscheme);
		schemeNew.setTableList(tableList);
		schemeNew.setDetailList(detailList);
		//
		JSONArray array = JSONArray.parseArray(tablelistS);
		//
		int lenT = array.size();
		int dnum = 1;
		int tnum = 1;
		for (int i = 0; i < lenT; i++) {
			// 
			Object o = array.get(i);
			if (!(o instanceof JSONObject)) {
				continue;// 不要嵌套太深
			}
			JSONObject jsObject = (JSONObject) o;
			// 添加 From 表信息
			String ddtid = dateStr + (tnum++) + "EXP";
			DbmsDatasTableNew tableNew = parseDbmsDatasTableNew(jsObject);
			if (null != tableNew) {
				// 塞值
				tableNew.setDdcid(ddcid);
				tableNew.setDdtid(ddtid);
				// 加入列表
				tableList.add(tableNew);
			} else {
				// 下一次循环
				continue; // 不要嵌套太深
			}
			// ... To 表信息
			// 2. 数据交换
			// 1. 数据导出
			// 0. 数据导入
			if("0".equals(ddctype)){
				// 本方法就是专门数据导入的...
			}
			String ddtidTo = dateStr + (tnum++) + "IMP";// to-id
			DbmsDatasTableNew tableTo = parseDbmsDatasTableTo(jsObject);
			if (null != tableTo) {
				// 塞值
				tableTo.setDdcid(ddcid);
				tableTo.setDdtid(ddtidTo);
				// 设置为不是主表
				tableTo.setDdtismaintable("");
				// 加入列表
				tableList.add(tableTo);
			} else {
				// 下一次循环
				continue; // 不要嵌套太深
			}
			//
			Object _columns = jsObject.get("columns");
			// 是否选中了列
			boolean hasColumns = false;
			if (_columns instanceof JSONArray) {
				JSONArray columns = (JSONArray) _columns;
				//
				ListIterator<Object> iteratorC = columns.listIterator();
				while (iteratorC.hasNext()) {
					Object object = (Object) iteratorC.next();
					if (object instanceof JSONObject) {
						JSONObject col = (JSONObject) object;
						// 单列,细节
						DbmsDatasChemeDetailNew chemeDetailNew = parseDbmsDatasChemeDetailNew(col, tableNew, tableTo, schemeNew);
						if (null != chemeDetailNew) {
							//
							String dcdid = dateStr + (dnum++);
							chemeDetailNew.setDcdid(dcdid);
							//
							detailList.add(chemeDetailNew);
							// 标志位
							hasColumns = true;
						}
					}
				}
			}
			// 所有列.没选中,select *,则从工具类中获取
			if (hasColumns) {
				continue; // 不要嵌套太深了
			}
		}

		//
		int status = datasChemeNewService.add(schemeNew, operator);
		//
		// int islocalnew = getParameterInt(request, "islocalnew", 0);

		message.setStatus(status);
		message.setInfo(status > 0 ? "保存成功" : "保存失败");

		return message;
	}


	/**
	 * 添加数据交换方案,返回值为 json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/adddbschemeinterchange.json")
	@ResponseBody
	public Object adddbschemeinterchange(HttpServletRequest request) {
		SystemUser operator = getLoginUser(request);// 获取当前登录的用户
		// String userid = operator.getUserid();
		// String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, String> map = parseParamMapString(request);
		//
		String ddcid = map.get("ddcid");
		String fromdatabaseid = map.get("fromdatabaseid");
		String todatabaseid = map.get("todatabaseid");
		String ddcname = map.get("ddcname");
		String ddctype = map.get("ddctype");
		String tablelistS = map.get("tablelist");
		//
		boolean validOK = true;
		String validError = "输入错误";
		//
		if (StringNumberUtil.isEmpty(fromdatabaseid)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(todatabaseid)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddcname)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddctype)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(tablelistS)) {
			validOK = false;
		}

		JSONMessage message = new JSONMessage();
		if (!validOK) {
			message.setInfo(validError);
			return message;
		}
		//
		String dateStr = Long.toHexString(System.currentTimeMillis());

		// 方案对应的表
		List<DbmsDatasTableNew> tableList = new ArrayList<DbmsDatasTableNew>();
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = new ArrayList<DbmsDatasChemeDetailNew>();
		//
		// FIXME
		DbmsDatasChemeNew schemeNew = new DbmsDatasChemeNew();
		//
		schemeNew.setDdcid(ddcid);
		schemeNew.setDdcname(ddcname);
		schemeNew.setDdctype(ddctype);
		schemeNew.setFromdatabaseid(fromdatabaseid);
		schemeNew.setTodatabaseid(todatabaseid);
		schemeNew.setTableList(tableList);
		schemeNew.setDetailList(detailList);
		//
		JSONArray array = JSONArray.parseArray(tablelistS);
		//
		int lenT = array.size();
		int tnum = 1;
		int dnum = 1;
		for (int i = 0; i < lenT; i++) {
			// 
			Object o = array.get(i);
			if (!(o instanceof JSONObject)) {
				continue;// 不要嵌套太深
			}
			JSONObject jsObject = (JSONObject) o;
			// 添加 From 表信息
			String ddtid = dateStr + "EXP" + (tnum++);
			DbmsDatasTableNew tableNew = parseDbmsDatasTableNew(jsObject);
			if (null != tableNew) {
				// 塞值
				tableNew.setDdcid(ddcid);
				tableNew.setDdtid(ddtid);
				// 加入列表
				tableList.add(tableNew);
			} else {
				// 下一次循环
				continue; // 不要嵌套太深
			}
			// ... To 表信息
			// 2. 数据交换
			// 1. 数据导出
			// 0. 数据导入
			if("2".equals(ddctype)){
				// 本方法就是专门数据交换的...
			}
			String ddtidTo = dateStr + "IMP" + (tnum++);// to-id
			DbmsDatasTableNew tableTo = parseDbmsDatasTableTo(jsObject);
			if (null != tableTo) {
				// 塞值
				tableTo.setDdcid(ddcid);
				tableTo.setDdtid(ddtidTo);
				// 设置为不是主表
				tableTo.setDdtismaintable("");
				// 加入列表
				tableList.add(tableTo);
			} else {
				// 下一次循环
				continue; // 不要嵌套太深
			}
			//
			Object _columns = jsObject.get("columns");
			// 是否选中了列
			boolean hasColumns = false;
			if (_columns instanceof JSONArray) {
				JSONArray columns = (JSONArray) _columns;
				//
				ListIterator<Object> iteratorC = columns.listIterator();
				while (iteratorC.hasNext()) {
					Object object = (Object) iteratorC.next();
					if (object instanceof JSONObject) {
						JSONObject col = (JSONObject) object;
						// 单列,细节
						DbmsDatasChemeDetailNew chemeDetailNew = parseDbmsDatasChemeDetailNew(col, tableNew, tableTo, schemeNew);
						if (null != chemeDetailNew) {
							//
							String dcdid = dateStr + (dnum++);
							chemeDetailNew.setDcdid(dcdid);
							//
							detailList.add(chemeDetailNew);
							// 标志位
							hasColumns = true;
						}
					}
				}
			}
			// 所有列.没选中,select *,则从工具类中获取
			if (hasColumns) {
				continue; // 不要嵌套太深了
			}
			// 根据ID获取对应的连接配置信息
			DbmsDatabaseNew databaseNew = databaseNewService.selectByPrimaryKey(fromdatabaseid);
			if (null == databaseNew) {
				continue; // 不要嵌套太深了
			}
			//
			String tablename = tableNew.getTablename();
			String databasetype = databaseNew.getDatabasetype();
			String ip = databaseNew.getDdip();
			String port = "" + databaseNew.getPort();
			String dbname = databaseNew.getDatabasename();
			String username = databaseNew.getDatabaseuser();
			String password = databaseNew.getDatabasepassword();
			List<Map<String, Object>> columns = DBMSMetaUtil.listColumns(databasetype, ip, port, dbname, username,
					password, tablename);
			//
			columns = MapUtil.convertKeyList2LowerCase(columns);
			columns = MapUtil.trimListKeyValue(columns);
			//
			if (null != columns) {
				Iterator<Map<String, Object>> iteratorM = columns.iterator();
				while (iteratorM.hasNext()) {
					Map<String, Object> colMap = (Map<String, Object>) iteratorM.next();
					// 然后，组装对象
					DbmsDatasChemeDetailNew chemeDetailNew = parseDbmsDatasChemeDetailNew(colMap, tableNew, tableTo, schemeNew);
					if (null != chemeDetailNew) {
						//
						String dcdid = dateStr + (dnum++);
						chemeDetailNew.setDcdid(dcdid);
						//
						detailList.add(chemeDetailNew);
					}
				}
			}
		}

		// 遍历数据表,根据信息获取对应的列,详细数据. 注意校验
		// 数据交换的一部分信息需要根据JSON传递过来,比如 ID编码不一致的地方
		// FIXME 请添加细节

		//
		int status = datasChemeNewService.add(schemeNew, operator);
		//
		// int islocalnew = getParameterInt(request, "islocalnew", 0);

		message.setStatus(status);
		message.setInfo(status > 0 ? "保存成功" : "保存失败");

		return message;
	}
	

	/**
	 * 拷贝交换方案
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/copyswapscheme.json")
	@ResponseBody
	public Object copyswapscheme(HttpServletRequest request) {
		SystemUser operator = getLoginUser(request);// 获取当前登录的用户
		
		Map<String, String> map = parseParamMapString(request);
		//
		String ddcid = map.get("ddcid");
		String fromdatabaseid = map.get("fromdatabaseid");
		String todatabaseid = map.get("todatabaseid");
		String ddcname = map.get("ddcname");
		String ddctype = map.get("ddctype");
		String sourcescheme = map.get("sourcescheme");
		String sdid = map.get("sdid");
		//
		boolean validOK = true;
		String validError = "输入错误";
		//
		if (StringNumberUtil.isEmpty(fromdatabaseid)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(todatabaseid)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddcname)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(ddctype)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(sourcescheme)) {
			validOK = false;
		}
		if (StringNumberUtil.isEmpty(sdid)) {
			validOK = false;
		}

		JSONMessage message = new JSONMessage();
		if (!validOK) {
			message.setInfo(validError);
			return message;
		}
		//
		Map<String, Object> conMap = new HashMap<String, Object>();
		conMap.put("ddcid", sourcescheme);
		//
		DbmsDatasChemeNew schemeOLD = datasChemeNewService.getById(sourcescheme);
		if(null == schemeOLD){
			// 提示以及处理
			message.setInfo("源方案不存在!");
			return message;
		}
		// 方案对应的表
		List<DbmsDatasTableNew> tableList = tableNewService.listByCondition(conMap);
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = detailNewService.listByCondition(conMap);
		//
		// FIXME
		DbmsDatasChemeNew schemeNew = new DbmsDatasChemeNew();
		//
		schemeNew.setSdid(sdid); // 所属机构
		schemeNew.setDdcid(ddcid);
		schemeNew.setDdcname(ddcname);
		schemeNew.setDdctype(ddctype);
		schemeNew.setFromdatabaseid(fromdatabaseid);
		schemeNew.setTodatabaseid(todatabaseid);
		schemeNew.setTableList(tableList);
		schemeNew.setDetailList(detailList);
		
		// 将对应的数据取出来，修改
		// 处理拷贝数据...
		processCopyData(schemeNew);
		
		//
		int status = datasChemeNewService.add(schemeNew, operator);
		//
		// int islocalnew = getParameterInt(request, "islocalnew", 0);

		message.setStatus(status);
		message.setInfo(status > 0 ? "保存成功" : "保存失败");

		return message;
	}

	private void processCopyData(DbmsDatasChemeNew schemeNew) {
		if(null == schemeNew){
			return;
		}
		//
		String ddcid = schemeNew.getDdcid();
		//
		String dateStr = Long.toHexString(System.currentTimeMillis());
		//
		int tno = 1;
		int dno = 1;
		// 旧的ID关系
		Map<String, DbmsDatasTableNew> oldTableIdMap = new HashMap<String, DbmsDatasTableNew>();
		// 方案对应的表
		List<DbmsDatasTableNew> tableList = schemeNew.getTableList();
		if(null != tableList){
			//
			Iterator<DbmsDatasTableNew> iteratorT = tableList.iterator();
			while (iteratorT.hasNext()) {
				DbmsDatasTableNew tableNew = (DbmsDatasTableNew) iteratorT.next();
				if(null != tableNew){
					tableNew.setDdcid(ddcid); // 修改方案ID
					// 
					String ddtid = trim(tableNew.getDdtid());
					oldTableIdMap.put(ddtid, tableNew); // 加入映射
					// 修改DDTID 为新的
					String ddtidN = dateStr;
					ddtidN = ddtidN.replaceAll("EXP", "");
					ddtidN = ddtidN.replaceAll("IMP", "");
					ddtidN = ddtidN+ (tno++) + getEXP_IMP(ddtid);
					tableNew.setDdtid(ddtidN);
				}
			}
		}
		
		//
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = schemeNew.getDetailList();
		//
		if(null != detailList){
			Iterator<DbmsDatasChemeDetailNew> iteratorD = detailList.iterator();
			while (iteratorD.hasNext()) {
				DbmsDatasChemeDetailNew detailNew = (DbmsDatasChemeDetailNew) iteratorD.next();
				if(null != detailNew){
					//
					detailNew.setDdcid(ddcid);// 修改方案ID
					
					//
					String ddtidOLD = trim(detailNew.getDdtid());
					String totableidOLD = trim(detailNew.getDcdtotableid());
					//
					DbmsDatasTableNew tableNew = oldTableIdMap.get(ddtidOLD);
					DbmsDatasTableNew tableTo = oldTableIdMap.get(totableidOLD);
					if(null != tableNew){
						//
						String ddtid = trim(tableNew.getDdtid());
						detailNew.setDdtid(ddtid); // 设置新的FROM表ID
					}
					if(null != tableTo){
						//
						String dcdtotableid = trim(tableTo.getDdtid());
						detailNew.setDcdtotableid(dcdtotableid); // 设置新的TO表ID
					}
					//
					// 修改 dcdid 为新的
					//String dcdid = detailNew.getDcdid();
					String dcdidN = dateStr;
					dcdidN = dcdidN.replaceAll("EXP", "");
					dcdidN = dcdidN.replaceAll("IMP", "");
					dcdidN = dcdidN.replaceAll("ITC", "");
					dcdidN = dcdidN.replaceAll("SWP", "");
					dcdidN = dcdidN+ (dno++) + getEXP_IMP(ddtidOLD);
					// 修改DDTID 为新的
					detailNew.setDcdid(dcdidN);

				}
			}
			
		}
	}

	private void processImpData(DbmsDatasChemeNew schemeNew) {
		if(null == schemeNew){
			return;
		}
		//
		String dateStr = Long.toHexString(System.currentTimeMillis());
		String ddcid = schemeNew.getDdcid();
		//
		int tno = 1;
		int dno = 1;
		// 旧的ID关系
		Map<String, DbmsDatasTableNew> oldTableIdMap = new HashMap<String, DbmsDatasTableNew>();
		// 方案对应的表
		List<DbmsDatasTableNew> tableList = schemeNew.getTableList();
		if(null != tableList){
			//
			Iterator<DbmsDatasTableNew> iteratorT = tableList.iterator();
			while (iteratorT.hasNext()) {
				DbmsDatasTableNew tableNew = (DbmsDatasTableNew) iteratorT.next();
				if(null != tableNew){
					tableNew.setDdcid(ddcid); // 修改方案ID
					// 
					String ddtid = trim(tableNew.getDdtid());
					oldTableIdMap.put(ddtid, tableNew); // 加入映射
					// 修改DDTID 为新的
					String ddtidN = ddcid;
					ddtidN = ddtidN.replaceAll("EXP", "");
					ddtidN = ddtidN.replaceAll("IMP", "");
					ddtidN = ddtidN.replaceAll("ITC", "");
					ddtidN = ddtidN.replaceAll("SWP", "");
					ddtidN = dateStr +(tno++) + getEXP_IMP(ddtid);
					tableNew.setDdtid(ddtidN);
					// 切换导入导出表
					String toTableN = tableNew.getTablename();
					//
					tableNew.setTotable(toTableN);
				}
			}
		}
		
		//
		// 方案对应的细节
		List<DbmsDatasChemeDetailNew> detailList = schemeNew.getDetailList();
		//
		if(null != detailList){
			Iterator<DbmsDatasChemeDetailNew> iteratorD = detailList.iterator();
			while (iteratorD.hasNext()) {
				DbmsDatasChemeDetailNew detailNew = (DbmsDatasChemeDetailNew) iteratorD.next();
				if(null != detailNew){
					//
					detailNew.setDdcid(ddcid);// 修改方案ID
					
					//
					String ddtidOLD = trim(detailNew.getDdtid());
					//String totableidOLD = trim(detailNew.getDcdtotableid());
					//
					DbmsDatasTableNew tableNew = oldTableIdMap.get(ddtidOLD);
					//DbmsDatasTableNew tableTo = oldTableIdMap.get(totableidOLD);
					if(null != tableNew){
						//
						String ddtid = trim(tableNew.getDdtid());
						detailNew.setDdtid(ddtid); // 设置新的FROM表ID
						detailNew.setDcdtotableid(ddtid); // 设置新的To表ID
					}
					//if(null != tableTo){
						//
						//String dcdtotableid = trim(tableTo.getDdtid());
						//detailNew.setDcdtotableid(dcdtotableid); // 设置新的TO表ID
					//}
					//
					// 修改 dcdid 为新的
					String dcdid = detailNew.getDcdid();
					String dcdidN = ddcid + getEXP_IMP(dcdid) +(dno++);
					dcdidN = dcdidN.replaceAll("EXP", "");
					dcdidN = dcdidN.replaceAll("IMP", "");
					dcdidN = dcdidN.replaceAll("ITC", "");
					dcdidN = dcdidN.replaceAll("SWP", "");
					dcdidN = dateStr +(dno++) + getEXP_IMP(dcdid) ;
					detailNew.setDcdid(dcdidN);
					
					// 切换导出列为导入列
					String toColumnNameN = detailNew.getDcdfromcolumns();
					String dcdtocolumnsscribeN = detailNew.getDcdfromcolumnsscribe();
					String dcdtocolumnstypeN = detailNew.getDcdfromcloumnstype();
					Long dcdtocolumnssizeN = detailNew.getDcdfromcloumnssize();
					Integer dcdtocolumnspointN = detailNew.getDcdfromcolumnspoint();
					
					//
					detailNew.setDcdtocolumns(toColumnNameN);
					detailNew.setDcdtocolumnsscribe(dcdtocolumnsscribeN);
					detailNew.setDcdtocolumnstype(dcdtocolumnstypeN);
					detailNew.setDcdtocolumnssize(dcdtocolumnssizeN);
					detailNew.setDcdtocolumnspoint(dcdtocolumnspointN);
				}
			}
			
		}
	}

	private String getEXP_IMP(String ddtid) {
		if(null != ddtid){
			if(ddtid.trim().contains("EXP") || ddtid.trim().contains("exp")){
				return "EXP";
			}
			if(ddtid.trim().contains("IMP") || ddtid.trim().contains("imp")){
				return "IMP";
			}
		}
		return "EXP";
	}

	private String trim(String ddtid) {
		if(null != ddtid){
			return ddtid.trim();
		}
		return "";
	}

	/**
	 * 保存数据方案,返回值为 json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/updatedbscheme.json")
	@ResponseBody
	public Object updateDbscheme(HttpServletRequest request) {
		SystemUser operator = getLoginUser(request);// 获取当前登录的用户
		// String userid = operator.getUserid();
		// String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Map<String, Object> map = parseParamMap(request);
		//

		JSONMessage message = new JSONMessage();

		//
		int status = datasChemeNewService.updateByMap(map, operator);
		//
		// int islocalnew = getParameterInt(request, "islocalnew", 0);

		message.setStatus(status);
		message.setInfo(status > 0 ? "保存成功" : "保存失败");

		return message;
	}

	// String ddcid

	/**
	 * 保存数据方案,返回值为 json
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajax/deletedbscheme.json")
	@ResponseBody
	public Object deleteDbscheme(HttpServletRequest request) {
		// SystemUser operator = getLoginUser(request);//获取当前登录的用户
		// String userid = operator.getUserid();
		// String departid=user.getDepartid();//根据用户Id获取所在部门Id
		// Map<String, Object> map = parseParamMap(request);
		//
		String ddcid = request.getParameter("ddcid");
		JSONMessage message = new JSONMessage();

		//
		int status = datasChemeNewService.deleteByPrimaryKey(ddcid);
		//
		// int islocalnew = getParameterInt(request, "islocalnew", 0);

		message.setStatus(status);
		message.setInfo(status > 0 ? "删除成功" : "删除失败");

		return message;
	}
	

	/**
	 * 主表列下拉
	 */	
	@RequestMapping(value = "/ajax/ajaxGetcolumnByddcid.json")
	@ResponseBody
	public Object ajaxGetcolumnByddcid(HttpServletRequest request) {
		String ddcid = request.getParameter("ddcid");
		List<DbmsDatasChemeDetailNew> columnList = detailNewService.getcolumnByddcid(ddcid);
		JSONMessage message = new JSONMessage();
		message.setData(columnList);
		return message;
	}
	
	@RequestMapping(value = "/ajax/ajaxGetExportdepart.json")
	@ResponseBody
	public Object ajaxGetExportdepart(HttpServletRequest request) {
		List<HashMap<String,Object>> departList = systemOrganizationService.getExportdepart(getLoginUser(request).getDepartid());
		return departList;
	}

	@RequestMapping(value = "/ajax/getShcemeTables.json")
	@ResponseBody
	public Object getShcemeTables(HttpServletRequest request) {
		JSONMessage message = new JSONMessage();
		String ddcid = request.getParameter("ddcid");
		String ddctype = request.getParameter("ddctype");
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		conditionMap.put("ddcid", ddcid);
		conditionMap.put("ddctype", ddctype);
		List<Map> tableList = tableNewService.getShcemeTables(conditionMap);
		tableList = MapUtil.turnKeyToLowerCaseOfList(tableList);
		message.setTotal(tableList.size());
		message.setData(tableList);
		return message;
	}
	
	@RequestMapping(value = "/editSchemetablecolumn.page")
	public String editSchemetablecolumn(HttpServletRequest request) {
		String viewName = "dbms/editSchemetablecolumn";
		String ddcid = request.getParameter("ddcid");
		//根据方案编号 查找源数据库编号
		List<Map<String, Object>> list = datasChemeNewService.selectByPrimaryKey(ddcid);
		String fromdatabaseid = (String) list.get(0).get("fromdatabaseid");
		
		String ddtid = request.getParameter("ddtid");
		String tablename = request.getParameter("tablename");
		String totable = request.getParameter("totable");
		request.setAttribute("ddcid", ddcid);
		request.setAttribute("ddtid", ddtid);
		request.setAttribute("tablename", tablename);
		request.setAttribute("totable", totable);
		request.setAttribute("databaseid", fromdatabaseid);
		return viewName;
	}
	
	@RequestMapping(value = "/ajax/getSchemetablecolumn.json")
	@ResponseBody
	public Object getSchemetablecolumn(HttpServletRequest request) {
		JSONMessage message = new JSONMessage();
		String ddcid = request.getParameter("ddcid");
		String ddtid = request.getParameter("ddtid");
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		conditionMap.put("ddcid", ddcid);
		conditionMap.put("ddtid", ddtid);
		List<Map> tablecolumnList = tableNewService.getSchemetablecolumn(conditionMap);
		tablecolumnList = MapUtil.turnKeyToLowerCaseOfList(tablecolumnList);
		message.setTotal(tablecolumnList.size());
		message.setData(tablecolumnList);
		return message;
	}

	@RequestMapping(value = "saveSchemetablecolumn.json")
	@ResponseBody
	public String saveSchemetablecolumn(HttpServletRequest request){
		String result = "success";
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				Map row = (HashMap)rows.get(i);
				if("modified".equals((String)row.get("_state"))){
					try {
						tableNewService.updateSchemeTablecolumn(row);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
					}
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "saveSchemetable.json")
	@ResponseBody
	public String saveSchemetable(HttpServletRequest request){
		String result = "success";
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				Map row = (HashMap)rows.get(i);
				if("modified".equals((String)row.get("_state"))){
					try {
						tableNewService.saveSchemetable(row);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
					}
				} else if("removed".equals((String)row.get("_state"))) {
					try {
						DbmsDatasTableNewKey dbmsDatasTableNewKey = new DbmsDatasTableNewKey();
						dbmsDatasTableNewKey.setDdcid(row.get("ddcid").toString());
						dbmsDatasTableNewKey.setDdtid(row.get("ddtid").toString());
						tableNewService.deleteByPrimaryKey(dbmsDatasTableNewKey);
					} catch (Exception e) {
						e.printStackTrace();
						result = "error";
					}					
				}
			}
		}
		return result;
	}
	
	/**
	 * 将json转化为ArrayList
	 * @param json
	 * @return
	 */
	public Object Decode(String json) {
		if (null==json||"".equals(json)) return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		Object obj = deserializer.deserialize(json);
		if(obj != null && obj.getClass() == String.class){
			return Decode(obj.toString());
		}
		return obj;
	}
	
}
