package com.sinog2c.mvc.controller.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysQueryplan;
import com.sinog2c.model.system.TbsysQueryplansql;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.QuerySchemeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;

/**
 * 查询方案
 * 
 * @author liuxin
 * 
 */
@Controller
public class QuerySchemeController extends ControllerBase{
	@Autowired
	private QuerySchemeService querySchemeService;
	@Resource
	SystemResourceService systemResourceService;
	@Resource
	public SystemLogService logService;
	@Resource
	private SystemOrganizationService systemorganizationservice;
	/**
	 * 跳转查询方案页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toQuerySchemePage")
	public ModelAndView toQuerySchemePage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/system/queryScheme.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 查询所有的查询方案
	 * 
	 * @author liuxin
	 * @param request
	 * @return List
	 */
	@RequestMapping(value = "/ajaxShecmeAll")
	@ResponseBody
	public Object ajaxShecmeAll(HttpServletRequest request){
		String key = request.getParameter("key");
		List<Map> list = querySchemeService.ajaxShecmeAll(key);
		return list;
	}
	
	/**
	 * 跳转新增方案页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toNewSchemePage")
	public ModelAndView toNewSchemePage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/system/newQueryScheme.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	@RequestMapping(value = "/toResourceTreePage")
	public ModelAndView toResourceTreePage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/system/selectTreeResource.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 新增查询方案信息
	 * 
	 * @author liuxin
	 * @param request
	 * @return int
	 */
	@RequestMapping(value = "/saveQueryScheme")
	@ResponseBody
	public int saveQueryScheme(HttpServletRequest request) {
		int result = 0;
		short status = 1;
		TbsysQueryplan queryplan = new TbsysQueryplan();
		String json = request.getParameter("data");
		
		String type = request.getParameter("operate");
		String schemeid = request.getParameter("schemeid");
		String fschemeid = request.getParameter("fschemeid");
		if("".equals(fschemeid)){
			fschemeid = "-1";
		}
		SystemUser user = getLoginUser(request);
		try {
			Map<String, String> map = new HashMap<String, String>();
			Object data = json.substring(2, json.length() - 2);
			String string = data.toString().replace("\"", "");
			String[] strings = string.split(",");
			for (int i = 0; i < strings.length; i++) {
				String[] strs = strings[i].split(":");
				if (strs.length == 1) {
					map.put(strs[0], "");
				} else {
					map.put(strs[0], strs[1]);
				}
			}

			if(("new").equals(type)){
				queryplan.setPplanid(Integer.parseInt(fschemeid));
				queryplan.setName(map.get("name"));
				queryplan.setResid(map.get("resid"));
				queryplan.setDelflag("0");
				queryplan.setDatarelation(map.get("datarelation"));
				queryplan.setOpid(user.getUserid());
				queryplan.setReportortemp(map.get("reportortemp"));
			}else if(("edit").equals(type)){
				queryplan.setPlanid(Integer.parseInt(schemeid));
				queryplan.setPplanid(Integer.parseInt(fschemeid));
				queryplan.setName(map.get("name"));
				queryplan.setResid(map.get("resid"));
				queryplan.setDelflag("0");
				queryplan.setDatarelation(map.get("datarelation"));
				queryplan.setReportortemp(map.get("reportortemp"));
				queryplan.setOpid(user.getUserid());		
			}
			result += this.querySchemeService.saveQueryScheme(type, queryplan);

		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
		}
		if(("new").equals(type)){
			SystemLog log = new SystemLog();
			log.setLogtype("查询方案操作");
			log.setOpaction("新增");
			log.setOpcontent("查询方案新增");
			log.setOrgid(user.getUserid());
			log.setRemark("查询方案事件");
			log.setStatus(status);
			try {
				logService.add(log, user);
			} catch (Exception e) {
				// 吃掉异常
			}
		}else if(("edit").equals(type)){
			SystemLog log = new SystemLog();
			log.setLogtype("查询方案操作");
			log.setOpaction("修改");
			log.setOpcontent("查询方案修改");
			log.setOrgid(user.getUserid());
			log.setRemark("查询方案事件");
			log.setStatus(status);
			try {
				logService.add(log, user);
			} catch (Exception e) {
				// 吃掉异常
			}		
		}
		
		return result;
	}
	/**
	 * 根据查询方案id查sql
	 * 
	 * @author liuxin
	 * @param request
	 * @return TbsysQueryplansql
	 */
	@RequestMapping(value = "/getSchemeSqlByPlanId")
	@ResponseBody
	public TbsysQueryplansql getSchemeSqlByPlanId(HttpServletRequest request) {
		Integer planid = Integer.parseInt(request.getParameter("planid"));
		TbsysQueryplansql queryplansql = querySchemeService.getSchemeSqlByPlanId(planid);
		return queryplansql;
	}
	/**
	 * 保存查询方案SQL
	 * 
	 * @author liuxin
	 * @param request
	 * @return int
	 */
	@RequestMapping(value = "/saveQuerySchemeSql")
	@ResponseBody
	public int saveQuerySchemeSql(HttpServletRequest request) {
		int result = 0;
		short status = 1;
		String type = "";
		SystemUser user = getLoginUser(request);
		try {
			Integer planid = Integer.parseInt(request.getParameter("planid"));
			String caozuo = request.getParameter("caozuo");
			String sql = request.getParameter("sql");
			
			TbsysQueryplansql queryplansql = querySchemeService.getSchemeSqlByPlanId(planid);
			
			if(queryplansql == null){
				type = "insert";
				queryplansql = new TbsysQueryplansql();
				queryplansql.setPlanid(planid);
				queryplansql.setOpsign(Short.parseShort(caozuo));
				queryplansql.setSqltext(sql);
				queryplansql.setDelflag("0");
				queryplansql.setRemark("");		
				queryplansql.setOpid(user.getUserid());
			}else{
				type = "update";
				queryplansql.setOpsign(Short.parseShort(caozuo));
				queryplansql.setSqltext(sql);
				queryplansql.setOpid(user.getUserid());
			}

			result += this.querySchemeService.saveQuerySchemeSql(type, queryplansql);
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
		}
		
		if("insert".equals(type)){
			SystemLog log = new SystemLog();
			log.setLogtype("查询方案SQL操作");
			log.setOpaction("新增");
			log.setOpcontent("查询方案SQL新增");
			log.setOrgid(user.getUserid());
			log.setRemark("查询方案SQL事件");
			log.setStatus(status);
			try {
				logService.add(log, user);
			} catch (Exception e) {
				// 吃掉异常
			}
		}else{
			SystemLog log = new SystemLog();
			log.setLogtype("查询方案SQL修改");
			log.setOpaction("修改");
			log.setOpcontent("查询方案SQL修改");
			log.setOrgid(user.getUserid());
			log.setRemark("查询方案SQL事件");
			log.setStatus(status);
			try {
				logService.add(log, user);
			} catch (Exception e) {
				// 吃掉异常
			}
		}
		return result;
	}
	/**
	 * 删除查询方案
	 * 
	 * @author liuxin
	 * @param request
	 * @return int
	 */
	@RequestMapping(value = "/delQueryScheme")
	@ResponseBody
	public int delQueryScheme(HttpServletRequest request) {
		int result = 0;
		short status = 1;
		SystemUser user = getLoginUser(request);
		
		try {
			Integer planid = Integer.parseInt(request.getParameter("planid"));
			TbsysQueryplan queryplan = new TbsysQueryplan();
			TbsysQueryplansql queryplansql = new TbsysQueryplansql();
			
			queryplan.setPlanid(planid);
			queryplan.setDelflag("1");
			queryplan.setOpid(user.getUserid());
			
			queryplansql.setPlanid(planid);
			queryplansql.setDelflag("1");
			queryplansql.setOpid(user.getUserid());
			
			result += this.querySchemeService.delQueryScheme(queryplan, queryplansql);

		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
		}
		SystemLog log = new SystemLog();
		log.setLogtype("查询方案删除");
		log.setOpaction("删除");
		log.setOpcontent("查询方案删除");
		log.setOrgid(user.getUserid());
		log.setRemark("查询方案事件");
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
	/**
	 * 根据资源ID查找资源名称
	 * 
	 * @author liuxin
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/getResourceNameById")
	@ResponseBody
	public String getResourceNameById(HttpServletRequest request) {
		String resid = request.getParameter("resid");
		SystemResource systemResource = this.systemResourceService.getByResourceId(resid);
		String name = systemResource.getName(); System.out.println(name);
		return name;
	}
	/**
	 * 方法描述：查询方案 通过sql得到对应的 结果
	 * @author mushuhong
	 * @version 14-8-19
	 */
	@RequestMapping(value = "/getReducePenaltyByAnJianHao")
	public ModelAndView getReducePenaltyByAnJianHao(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		String type = request.getParameter("type");
		String doctype=request.getParameter("doctype");//资源url传值
		request.setAttribute("doctype", doctype);
		String reportname=request.getParameter("name");
		if(null==reportname||"".equals(reportname)){
			SystemResource resExist = systemResourceService.getByResourceId(menuid);
			reportname=resExist.getName();
		}
		request.setAttribute("reportname", reportname);
		String toolbar = request.getParameter("toolbar");//用于在jsp页面是否屏避 toolbar    0：屏避
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		if(StringNumberUtil.notEmpty(menuid)){
			request.setAttribute("menuid", menuid);
		}
		String casenos = request.getParameter("casenos");
		if(StringNumberUtil.notEmpty(casenos)){
			request.setAttribute("casenos", casenos);
		}
		SystemUser user = getLoginUser(request);
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		ModelAndView mav = null;
		String lv = user.getOrganization().getUnitlevel();
		//issave用来区分会议记录和预览打印页面的存盘按钮
		int issave=1;
		request.setAttribute("issave",issave);
		if(lv!=null && ("6".equals(lv) || "7".equals(lv))){//法院的
//			mav = new ModelAndView("court/yulandayincourt");
			mav = new ModelAndView("court/commRMreportPrinter");
		}else{
			if(GkzxCommon.ONE.equals(type)) {
				mav = new ModelAndView("report/yulandayin_exp");
			}else {
				mav = new ModelAndView("report/yulandayin");
			}
		}
		return mav;
	}
	
	/**
	 * 方法描述：跳转至建议书报表预览页
	 */
	@RequestMapping(value = "/toPreviewSuggestReport")
	public ModelAndView toPreviewSuggestReport(HttpServletRequest request){
		
//		String toolbar = request.getParameter("toolbar");//用于在jsp页面是否屏避 toolbar    0：屏避
//		if(StringNumberUtil.notEmpty(toolbar)){
//			request.setAttribute("toolbar", toolbar);
//		}
//		String casenos = request.getParameter("casenos");
//		if(StringNumberUtil.notEmpty(casenos)){
//			request.setAttribute("casenos", casenos);
//		}
		SystemUser user = getLoginUser(request);
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		ModelAndView mav = new ModelAndView("report/suggestReport");
		return mav;
	}
	
	@RequestMapping(value = "/getReportBigData")
	@ResponseBody
	public Object getReportBigData(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
//		request.setAttribute("mydata", engine.dedaoReportData());
//		ModelAndView mav = new ModelAndView("report/suggestReport");
//		return mav;
		return engine.dedaoReportData();
	}

	/**
	 * 方法描述：查询出所有的reoport资源
	 * @author mushuhong
	 * @version 2014年9月17日10:57:49
	 */
	@RequestMapping(value ="/getAllReportResources")
	@ResponseBody
	public Object getAllReportResources(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		List<Map> listMaps= this.systemResourceService.getAllReportResources(request,user);
		//key 大小写 转化
//		listMaps = MapUtil.turnKeyToLowerCaseOfList(listMaps);
		JSONMessage message = JSONMessage.newMessage();
		message.setData(listMaps);
		return listMaps;
	}
	
	/**
	 * 
	 * 案件审查意见
	 */
	@RequestMapping(value = "/getyjReport")
	public ModelAndView getyjReport(HttpServletRequest request){
		String toolbar = request.getParameter("toolbar");//用于在jsp页面是否屏避 toolbar    0：屏避
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		List list =getJQ(request);
		request.setAttribute("signatureSchemes", list);
		ModelAndView mav = new ModelAndView("aip/anjianshencha");
		return mav;
	}
	public  List getJQ(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String type=request.getParameter("type");
		Map maptemp=new HashMap();
		maptemp.put("userid", user.getUserid());
		//maptemp.put("departid", user.getDepartid());
		maptemp.put("departid", user.getOrganization().getOrgid());
		//用户所在部门的级别
		String dep=systemorganizationservice.getDep(maptemp);
		Map map=new HashMap();
		if(null!=type&&"all".equals(type)){
			dep="3";
		}
		map.put("progid", user.getDepartid());
		if("5".equals(dep)||"3".equals(dep)){
			map.put("unitlevel", "4");
		}else if("4".equals(dep)){
			map.put("orgid", user.getOrgid());
		}
		List<Map> list=systemorganizationservice.getDepartById(map);
		List list2 = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map maps=list.get(i);
			list2.add(maps);
		}
		return list2;
	}
	@RequestMapping(value = "/toqueryQualification")
	@ResponseBody
	public Object toqueryQualification(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		return this.systemResourceService.queryQualificationDataRmEngine("", user, request);
	}
	/**
	 * 方法描述：进入监区案件呈报表 页面
	 * @author mushuhong
	 * @version 2014年12月23日11:02:07
	 * 
	 */
	@RequestMapping(value = "/jqCaseTypeChengBaobiao")
	public ModelAndView jqCaseTypeChengBaobiao(HttpServletRequest request){
		
		//根据 菜单 编号，查询出对应的方案
		
		this.systemResourceService.jqCaseTypeChengBaobiao(request,getLoginUser(request));
		
		return new ModelAndView("commutationParole/jqCaseTypeChengBaobiaoPage");
	}
	/**
	 * 方法描述：进入减刑假释呈报表列表页 分监区办理
	 * @author mushuhong
	 * @version 2015年1月7日10:48:36
	 */
	@RequestMapping(value = "/goJxjsCPBInfo")
	public ModelAndView goJxjsCPBInfo(HttpServletRequest request){
		
		//查询出 可以操作的 内容 
		systemResourceService.getCaoZuoResource(request,getLoginUser(request));
		
		return new ModelAndView("chooseCriminal/goJxjsCPBInfo");
	}
	/**
	 * 方法描述：进入减刑假释呈报表列表页 分监区以上办理
	 * @author mushuhong
	 * @version 2015年1月7日10:48:36
	 */
	@RequestMapping(value = "/goJxjsCPBInfo_yzk")
	public ModelAndView goJxjsCPBInfo_yzk(HttpServletRequest request){
		
		
		
		return new ModelAndView("chooseCriminal/goJxjsCPBInfo_yzk");
	}
	/**
	 * 方法描述：监区案件呈报榜
	 * @author mushuhong
	 * @version 2014年12月22日15:35:48
	 */
	@RequestMapping(value="/jqCaseTypeChengBaoBangPage")
	@ResponseBody
	public Object jqCaseTypeChengBaoBangPage(HttpServletRequest request){
		
		
		
		return systemResourceService.jqCaseTypeChengBaoBangPage(request,getLoginUser(request));
	}
	/**
	 * 方法描述：导出xls
	 * @author :mushuhong
	 * @version 2014年12月23日13:48:42
	 * 
	 */
	@RequestMapping(value = "/exportXls")
	@ResponseBody
	@SuppressWarnings("all")
	public void exportXls(HttpServletRequest request,HttpServletResponse response){
		SystemUser user = getLoginUser(request);
		String orgname = user.getOrganization().getFullname();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		HSSFWorkbook wb = new HSSFWorkbook();//创建一个工作文本
		HSSFSheet sheet = wb.createSheet();//创建一个张表格
		
		//创建单元格的样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		//指定单元格 居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//指定单元格 垂直居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//内容显示 不完全 自动换行
		cellStyle.setWrapText(true);
		
		//设置单元格的字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontName("宋体");//字体
		font.setFontHeight((short)200);//设置行高
		cellStyle.setFont(font);//把属性设置到xls上面
		
		//设置第一行的内容
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		
		//定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString("宁夏回族自治区石嘴山监狱办理罪犯减刑（假释）案件呈报榜公示"));
		
		List<Map> dataMap = (List<Map>)systemResourceService.exportXlsService(request,getLoginUser(request));
		
//		Map map = (Map)systemResourceService.jqCaseTypeChengBaoBangPage(request,getLoginUser(request));
		List<Map> listMaps = dataMap;
		
		int ii = 0;
		for (Map map2 : listMaps) {
			//第一行是大标题，那么每一列的说明就是冲第二列开始的。故：sheet.createRow(1)
			map2 = sortMapByKey(map2);
			row = sheet.createRow(1);
			Iterator iterator = map2.keySet().iterator();
			while(iterator.hasNext()){
				Object key = iterator.next();
				Object value = map2.get(key);//结果和列明 用@拼接，如：内容||'@罪犯姓名'
				String[] values = value.toString().split("@");
				//values[1],都是 导出 xls列名称
				row.createCell(ii++).setCellValue(values[1]);
			}
			//跳出的原因，只取Map<key,value>，list中每一个map的key都是相同的。
			break;
		}
		int count = 2;
		for (Map map2 : listMaps) {
			map2 = sortMapByKey(map2);
			row = sheet.createRow(count++);
			Iterator iterator = map2.keySet().iterator();
			int jj = 0;
			while(iterator.hasNext()){
				Object obj = iterator.next();
				Object valueObj = map2.get(obj);
				String[] valueObjs = valueObj.toString().split("@");
				row.createCell(jj++).setCellValue(valueObjs[0]);
			}
		}
		try {
			String nameString = user.getAddress()+user.getOrganization().getFullname()+".xls";
//			String filename = request.getSession().getServletContext().getRealPath("sealfile\\"+nameString);
//			FileOutputStream file = new FileOutputStream(new File(filename));
//			wb.write(file);
//			file.close();
//			file.flush();
			
			OutputStream out = response.getOutputStream();
			 
//		    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("111", "UTF-8"));
//		    response.setContentType("application/msexcel;charset=UTF-8");
//		    wb.write(out);
//            out.close();
//			FileOutputStream file = new FileOutputStream(new File("c:\\呈报榜.xls"));
//			wb.write(file);
//			file.close();
//			file.flush();
//			System.out.println(request.getSession().getServletContext().getRealPath("d:\\"));
			//request.getSession().getServletContext().getRealPath();
//			FileInputStream is = new FileInputStream(new File("c:\\呈报榜.xls"));
//			FileOutputStream out = new FileOutputStream(new File("e:\\1111111111111111111.xls"));
//			byte[] b = new byte[1024]; 
//			int len = 0;
//		    while((len = is.read(b))!=-1){
//                out.write(b);
//          }
//			out.close();
//			is.close();
            response.reset();			
            response.setContentType("bin");			
            response.addHeader("Content-Disposition","attachment; filename=" + new String(nameString.getBytes("gbk"), "iso8859-1"));
            
            wb.write(out);
            out.close();//关闭流
            out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static java.util.Map<String, String> sortMapByKey(java.util.Map<String, String> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        java.util.Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }
}
//实现 Comparator 进行排序
class MapKeyComparator implements Comparator<String>{  
    public int compare(String str1, String str2) {  
        return str1.compareTo(str2);  
    } 
}