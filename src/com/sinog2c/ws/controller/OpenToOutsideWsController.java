package com.sinog2c.ws.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.courtjoint.TbdaraPrisonerMapper;
import com.sinog2c.dao.api.courtjoint.TbdataArchivesMapper;
import com.sinog2c.dao.api.courtjoint.TbxfBackReplenishMapper;
import com.sinog2c.dao.api.courtjoint.TbxfOpenCourtMapper;
import com.sinog2c.dao.api.courtjoint.TbxfPutonrecordMapper;
import com.sinog2c.dao.api.courtjoint.TbxfRulingMapper;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.quartz.GetDataFromCourt;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.ws.service.OpenToOutsideService;
import com.sinog2c.ws.service.impl.OpenToOutsideServiceImpl;

/**
 * webService 数据交换controller
 * 
 */
@Lazy
@Controller
@RequestMapping(value="courtJoint")
public class OpenToOutsideWsController extends ControllerBase {

	@Resource
	SystemOrganizationService systemOrganizationService;
	
	@Resource
	TbxfBackReplenishMapper tbxfBackReplenishMapper;
	
	@Resource
	TbxfPutonrecordMapper tbxfPutonrecordMapper;
	
	@Resource
	TbxfOpenCourtMapper tbxfOpenCourtMapper;
	
	@Resource
	TbxfRulingMapper tbxfRulingMapper;
	
	@Resource
	TbdaraPrisonerMapper tbdaraPrisonerMapper;
	
	@Resource
	TbdataArchivesMapper tbdataArchivesMapper;

	@Resource
	OpenToOutsideService openToOutsideService;
	
	@Resource
	GetDataFromCourt getDataFromCourt;
		
    @RequestMapping(value = "/baoSongCourtListPage.page")
    public ModelAndView baoSongJcyListPage(HttpServletRequest request) {
        
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
        
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
        String shortname = so.getShortname();
        
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("shortname", shortname);
        request.setAttribute("curyear", curyear);
        request.setAttribute("departid", departid);
        ModelAndView mav = new ModelAndView("court/baoSongCourtPage");
        return mav;
    }
    
    /**
     * 方法描述：发送数据到法院 列表页面 数据
     * @throws Exception 
     */
    @RequestMapping(value = "/getCourtCaseHandleList")
    @ResponseBody
    public Object getJcyCaseHandleList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        if(su==null){
        	
        }
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
  
            request.getSession().removeAttribute("initPageIndex");
            request.getSession().removeAttribute("initPageSize");
            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
            String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
            String picibianhao = request.getParameter("picibianhao")==null?"":request.getParameter("picibianhao");
            String flowdefid = request.getParameter("flowdefid")==null?"":request.getParameter("flowdefid");
            String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid");
            String crimname = request.getParameter("crimname")==null?"":request.getParameter("crimname");
            int start = pageIndex * pageSize + 1;
            int end = start + pageSize -1;
            Date date = new Date();
            if("".equals(curyear)){
                curyear = DateUtil.dateFormat(date, "yyyy");
            } 
            if("".equals(picibianhao)){
            	List piciList = (List) this.getPiciSlecter(request, response);
            	if(piciList!=null&&piciList.size()>0){
            		Map map = (Map) piciList.get(0);
            		picibianhao= String.valueOf(map.get("picibianhao"));
            	}
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("curyear", curyear);
            map.put("picibianhao", picibianhao);
            map.put("departid", orgid);
            map.put("crimname", crimname);
            //map.put("departid", "4309");
            map.put("flowdefid", flowdefid);
            map.put("start", String.valueOf(start));
            map.put("end",String.valueOf(end));
            int count = openToOutsideService.countCourtCaseHandleList(map);
            data= openToOutsideService.getCourtCaseHandleList(map);
            resultmap.put("data", data);
            resultmap.put("total", count);
            return resultmap;
    }
    
  
    //把信息发送给法院
    @RequestMapping(value = "/sendToCourtJXJS.action")
    @ResponseBody
    public Object sendToCourtJXJS(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	 SystemUser su = getLoginUser(request);
         String orgid = su.getOrgid();
         String name = su.getName();
         String crimids = request.getParameter("crimids");
         String picibianhao = request.getParameter("picibianhao");
         String curyear = request.getParameter("curyear");   
         String courtid = request.getParameter("courtid");
         String[] crimidArray= crimids.split(",");
         Map<Object, Object> map = new HashMap<>();
         map.put("crimidArray",crimidArray);
         map.put("orgid", orgid);
         map.put("picibianhao", picibianhao);
         map.put("curyear", curyear);
         map.put("courtid", courtid);
         map.put("name", name);
         String result = openToOutsideService.sendCaseData(map);
    	//获取需要的数据
    	return 1;
    }   
    
  //接收法院的信息
    @RequestMapping(value = "/SendReceiptToCourt.action")
    public void SendReceiptToCourt(HttpServletRequest request,
            HttpServletResponse response) {
    	 SystemUser su = getLoginUser(request);
         String orgid = su.getOrgid();
    	 //String orgid = "4309";
         String crimids = request.getParameter("crimids");
         String picibianhao = request.getParameter("picibianhao");
         String curyear = request.getParameter("curyear");   
         String[] crimidArray= crimids.split(",");
         Map<Object, Object> map = new HashMap<>();
         map.put("crimidArray",crimidArray);
         map.put("orgid", orgid);
         map.put("picibianhao", picibianhao);
         map.put("curyear", curyear);
    	 openToOutsideService.sendReceiptToCourttest(map);  	
    }
    
    //接收法院的信息
    @RequestMapping(value = "/SendReceiptToCourttest.action")
    public void SendReceiptToCourttest(HttpServletRequest request,
            HttpServletResponse response) {
    	 SystemUser su = getLoginUser(request);
         String orgid = su.getOrgid();
    	 //String orgid = "4309";
         String crimids = request.getParameter("crimids");
         String picibianhao = request.getParameter("picibianhao");
         String curyear = request.getParameter("curyear");   
         String[] crimidArray= crimids.split(",");
         Map<Object, Object> map = new HashMap<>();
         map.put("crimidArray",crimidArray);
         map.put("orgid", orgid);
         map.put("picibianhao", picibianhao);
         map.put("curyear", curyear);
    	 openToOutsideService.sendReceiptToCourttest(map);
    	
    }
    
    //接收法院的信息
    @RequestMapping(value = "/unzipinsert.action")
    public void unzipinsert(HttpServletRequest request,
            HttpServletResponse response) {
    	String filepath="C:\\Users\\Administrator\\Desktop\\新建文件夹";
		String fileName="4333_2400_1601_2c9981c600000000015d5f31aadf0000_1601D_1321354567454sdd.zip";
		openToOutsideService.getCaseData(filepath,fileName,"1601D");
		fileName="4333_2400_1601_2c9981c600000000015d5f31aadf0000_1601E_1321354567454ee.zip";
		openToOutsideService.getCaseData(filepath,fileName,"1601E");
		fileName="4333_2400_1601_2c9981c600000000015d728f179d001f_1601B_1321354567454ssddd.zip";
		openToOutsideService.getCaseData(filepath,fileName,"1601F");
		fileName="4333_2400_1601_2c9981c600000000015d728f179d001f_1601B_1321354567454ssddd.zip";
		openToOutsideService.getCaseData(filepath,fileName,"1601B");
    	
    }
    
    
    
    
    /* * 获取批次下拉框信息
     * **/
	@RequestMapping(value = "/getPiciSlecter.action")
    @ResponseBody
    public Object getPiciSlecter(HttpServletRequest request,
            HttpServletResponse response) {
		 	Date date = new Date();
	        String curyear = DateUtil.dateFormat(date, "yyyy");
	        String orgid = request.getParameter("orgid")==null?"":request.getParameter("orgid");
	        Map map = new HashMap<>();
	        map.put("curyear", curyear);
	        map.put("orgid", orgid);
	        List<Map> list = openToOutsideService.getPiciInfo(map);
	        return list;
    }
	
	 
    /* * 获取监狱下拉框信息
     * **/
	@RequestMapping(value = "/getPrison.action")
    @ResponseBody
    public Object getPrison(HttpServletRequest request,
            HttpServletResponse response,String departid) {
        Date date = new Date();
        Map map = new HashMap<>();
        map.put("departid", departid);
        List<Map> list = openToOutsideService.getPrisonInfo(map);
        return list;
    }
    
	
     /** 省局获取法院下拉框信息
     * **/
	@RequestMapping(value = "/getCourt.action")
    @ResponseBody
    public Object getCourt(HttpServletRequest request,
            HttpServletResponse response) {
		SystemUser su = getLoginUser(request);
        String orgid = su.getOrgid();
        Map map = new HashMap<>();
        map.put("departid", orgid);
        List<Map> list = openToOutsideService.getCourt(map);
        return list;
    }
	
	
     /** 监狱获取法院下拉框信息
     * **/
	@RequestMapping(value = "/getCourtP.action")
    @ResponseBody
    public Object getCourtP(HttpServletRequest request,
            HttpServletResponse response) {
		SystemUser su = getLoginUser(request);
        String orgid = su.getOrgid();
        Map map = new HashMap<>();  
        map.put("departid", orgid);
        List<Map> list = openToOutsideService.getCourtP(map);
        return list;
    }
	
    //把信息发送给法院
    @RequestMapping(value = "/CourtListPage.page")
    public void jieshouCourtListPage(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	 returnResourceMap(request);
         SystemUser su = getLoginUser(request);
         Date date = new Date();
         String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
         String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
         
         if("".equals(curyear)){
             curyear = DateUtil.dateFormat(date, "yyyy");
         }
         String departid = su.getDepartid();
         SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
         String shortname = so.getShortname();
         
         String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
         String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
         if(StringNumberUtil.notEmpty(initPageIndexStr)){
             request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
         }
         if(StringNumberUtil.notEmpty(initPageSizeStr)){
             request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
         }
         request.setAttribute("shortname", shortname);
         request.setAttribute("curyear", curyear);

         ModelAndView mav = new ModelAndView("court/baoSongCourtPage");
    }
    
    @RequestMapping(value = "/backReplenishListPage.page")
    public ModelAndView backReplenishListPage(HttpServletRequest request) {
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
        String shortname = so.getShortname();
        
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("shortname", shortname);
        request.setAttribute("curyear", curyear);
        ModelAndView mav = new ModelAndView("court/backReplenishPage");
        return mav;
    }
    
    /**
     * 方法描述：发送数据到法院 列表页面 数据
     * @throws Exception 
     */
    @RequestMapping(value = "/getBackReplenishList.action")
    @ResponseBody
    public Object getBackReplenishList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        if(su==null){
        	
        }
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
  
            request.getSession().removeAttribute("initPageIndex");
            request.getSession().removeAttribute("initPageSize");
            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
            String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
            String picibianhao = request.getParameter("picibianhao")==null?"":request.getParameter("picibianhao");
            String crimname = request.getParameter("crimname")==null?"":request.getParameter("crimname");
            int start = pageIndex * pageSize + 1;
            int end = start + pageSize -1;
            Date date = new Date();
            if("".equals(curyear)){
                curyear = DateUtil.dateFormat(date, "yyyy");
            } 
            if("".equals(picibianhao)){
            	List piciList = (List) this.getPiciSlecter(request, response);
            	if(piciList!=null&&piciList.size()>0){
            		Map map = (Map) piciList.get(0);
            		picibianhao= String.valueOf(map.get("picibianhao"));
            	}
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("batchid", picibianhao);
            map.put("crimname", crimname);
            map.put("start", String.valueOf(start));
            map.put("end",String.valueOf(end));
            int count = tbxfBackReplenishMapper.countByCrimidAndBatchid(map);
            data= tbxfBackReplenishMapper.findByCrimidAndBatchid(map);
            resultmap.put("data", data);
            resultmap.put("total", count);
            return resultmap;
    }
    
    @RequestMapping(value = "/putonrecordListPage.page")
    public ModelAndView putonrecordListPage(HttpServletRequest request) {
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
        String shortname = so.getShortname();
        
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("shortname", shortname);
        request.setAttribute("curyear", curyear);
        ModelAndView mav = new ModelAndView("court/putonrecordListPage");
        return mav;
    }
    
    /**
     * 方法描述：立案
     * @throws Exception 
     */
    @RequestMapping(value = "/getPutonrecordList.action")
    @ResponseBody
    public Object getPutonrecordList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        if(su==null){
        	
        }
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
  
            request.getSession().removeAttribute("initPageIndex");
            request.getSession().removeAttribute("initPageSize");
            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
            String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
            String picibianhao = request.getParameter("picibianhao")==null?"":request.getParameter("picibianhao");
            String crimname = request.getParameter("crimname")==null?"":request.getParameter("crimname");
            int start = pageIndex * pageSize + 1;
            int end = start + pageSize -1;
            Date date = new Date();
            if("".equals(curyear)){
                curyear = DateUtil.dateFormat(date, "yyyy");
            } 
            if("".equals(picibianhao)){
            	List piciList = (List) this.getPiciSlecter(request, response);
            	if(piciList!=null&&piciList.size()>0){
            		Map map = (Map) piciList.get(0);
            		picibianhao= String.valueOf(map.get("picibianhao"));
            	}
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("batchid", picibianhao);
            map.put("crimname", crimname);
            map.put("start", String.valueOf(start));
            map.put("end",String.valueOf(end));
            int count = tbxfPutonrecordMapper.countByCrimidAndBatchid(map);
            data= tbxfPutonrecordMapper.findByCrimidAndBatchid(map);
            resultmap.put("data", data);
            resultmap.put("total", count);
            return resultmap;
    }
    
    @RequestMapping(value = "/opencourtListPage.page")
    public ModelAndView opencourtListPage(HttpServletRequest request) {
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
        String shortname = so.getShortname();
        
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("shortname", shortname);
        request.setAttribute("curyear", curyear);
        ModelAndView mav = new ModelAndView("court/opencourtPage");
        return mav;
    }
    
    /**
     * 方法描述：发送数据到法院 列表页面 数据
     * @throws Exception 
     */
    @RequestMapping(value = "/getOpencourtList.action")
    @ResponseBody
    public Object getOpencourtList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        if(su==null){
        	
        }
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
  
            request.getSession().removeAttribute("initPageIndex");
            request.getSession().removeAttribute("initPageSize");
            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
            String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
            String picibianhao = request.getParameter("picibianhao")==null?"":request.getParameter("picibianhao");
            String crimname = request.getParameter("crimname")==null?"":request.getParameter("crimname");
            int start = pageIndex * pageSize + 1;
            int end = start + pageSize -1;
            Date date = new Date();
            if("".equals(curyear)){
                curyear = DateUtil.dateFormat(date, "yyyy");
            } 
            if("".equals(picibianhao)){
            	List piciList = (List) this.getPiciSlecter(request, response);
            	if(piciList!=null&&piciList.size()>0){
            		Map map = (Map) piciList.get(0);
            		picibianhao= String.valueOf(map.get("picibianhao"));
            	}
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("batchid", picibianhao);
            map.put("crimname", crimname);
            map.put("start", String.valueOf(start));
            map.put("end",String.valueOf(end));
            int count = tbxfOpenCourtMapper.countByCrimidAndBatchid(map);
            data= tbxfOpenCourtMapper.findByCrimidAndBatchid(map);
            resultmap.put("data", data);
            resultmap.put("total", count);
            return resultmap;
    }
    
    @RequestMapping(value = "/rulingListPage.page")
    public ModelAndView rulingListPage(HttpServletRequest request) {
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
        String shortname = so.getShortname();
        
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("shortname", shortname);
        request.setAttribute("curyear", curyear);
        ModelAndView mav = new ModelAndView("court/rulingPage");
        return mav;
    }
    
    /**
     * 方法描述：发送数据到法院 列表页面 数据
     * @throws Exception 
     */
    @RequestMapping(value = "/getRulingList.action")
    @ResponseBody
    public Object getRulingList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        if(su==null){
        	
        }
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
  
            request.getSession().removeAttribute("initPageIndex");
            request.getSession().removeAttribute("initPageSize");
            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
            String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
            String picibianhao = request.getParameter("picibianhao")==null?"":request.getParameter("picibianhao");
            String crimname = request.getParameter("crimname")==null?"":request.getParameter("crimname");
            int start = pageIndex * pageSize + 1;
            int end = start + pageSize -1;
            Date date = new Date();
            if("".equals(curyear)){
                curyear = DateUtil.dateFormat(date, "yyyy");
            } 
            if("".equals(picibianhao)){
            	List piciList = (List) this.getPiciSlecter(request, response);
            	if(piciList!=null&&piciList.size()>0){
            		Map map = (Map) piciList.get(0);
            		picibianhao= String.valueOf(map.get("picibianhao"));
            	}
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("batchid", picibianhao);
            map.put("crimname", crimname);
            map.put("start", String.valueOf(start));
            map.put("end",String.valueOf(end));
            int count = tbxfRulingMapper.countByCrimidAndBatchid(map);
            data= tbxfRulingMapper.findByCrimidAndBatchid(map);
            resultmap.put("data", data);
            resultmap.put("total", count);
            return resultmap;
    }
    
    /**
     * 方法描述：裁定信息详情
     * @throws Exception 
     */
    @RequestMapping(value = "/getRulingDetailList.action")
    @ResponseBody
    public Object getRulingDetailList(HttpServletRequest request,HttpServletResponse response,String crimid,String picibianhao) throws Exception{
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("batchid", picibianhao);
        map.put("crimid", crimid);
        int count = tbxfRulingMapper.countByCrimidAndBatchid(map);
        data= tbxfRulingMapper.findByCrimidAndBatchid(map);
        resultmap.put("data", data);
        resultmap.put("total", count);
        return resultmap;
    }
    
    @RequestMapping(value = "/deliveryToCourtPage.page")
    public ModelAndView deliveryToCourtListPage(HttpServletRequest request) {
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag");
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
        String shortname = so.getShortname();
        
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("shortname", shortname);
        request.setAttribute("curyear", curyear);
        ModelAndView mav = new ModelAndView("court/deliveryToCourt");
        return mav;
    }
    
    /**
     * 方法描述：发送数据到法院 列表页面 数据
     * @throws Exception 
     */
    @RequestMapping(value = "/deliveryToCourtList.action")
    @ResponseBody
    public Object receipToCourtList(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        if(su==null){
        	
        }
        Map<String, Object> resultmap = new HashMap<String,Object>();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>> ();
  
            request.getSession().removeAttribute("initPageIndex");
            request.getSession().removeAttribute("initPageSize");
            int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
            int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
            String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
            String picibianhao = request.getParameter("picibianhao")==null?"":request.getParameter("picibianhao");
            String crimname = request.getParameter("crimname")==null?"":request.getParameter("crimname");
            int start = pageIndex * pageSize + 1;
            int end = start + pageSize -1;
            Date date = new Date();
            if("".equals(curyear)){
                curyear = DateUtil.dateFormat(date, "yyyy");
            } 
            if("".equals(picibianhao)){
            	List piciList = (List) this.getPiciSlecter(request, response);
            	if(piciList!=null&&piciList.size()>0){
            		Map map = (Map) piciList.get(0);
            		picibianhao= String.valueOf(map.get("picibianhao"));
            	}
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("batchid", picibianhao);
            map.put("crimname", crimname);
            map.put("start", String.valueOf(start));
            map.put("end",String.valueOf(end));
            int count = tbdaraPrisonerMapper.countByCrimidAndBatchid(map);
            data= tbdaraPrisonerMapper.findByCrimidAndBatchid(map);
            resultmap.put("data", data);
            resultmap.put("total", count);
            return resultmap;
    }
    
    /**
     * 查看文书
     */
    @RequestMapping("seeWrit.action")
    public ModelAndView seeWrit(String crimid,String batchid,String type){
    	Map<String, String> map = new HashMap<String, String>();
    	if(type.equals("1")){
    		type="出庭通知";
    	}else if (type.equals("2")){
    		type="刑事裁定";
    	}else if (type.equals("3")){
    		type="起诉";
    	}else if (type.equals("4")){
    		type="判决";
    	}else if (type.equals("5")){
    		type="执行通知";
    	}else if (type.equals("6")){
    		type="结案登记";
    	}else if (type.equals("7")){
    		type="送达回证";
    	}
    	map.put("archivesname", type);
    	map.put("crimid", crimid);
    	map.put("batchid", batchid);
    	String archivespath = tbdataArchivesMapper.findByCrimidAndArchivesname(map);
		Properties jyconfig = new GetProperty().bornProp("courtJointPrisonconfig", null);
    	return new ModelAndView("court/opencourtWrit").addObject("archivespath", archivespath);
    }
    
    /**
     * 上传送达回证
     */
    @RequestMapping("uploadDeliveryWrit.action")
    public String uploadDeliveryWrit(@RequestParam(value = "file", required = false) MultipartFile file,String crimid,String batchid){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("crimid", crimid);
    	map.put("batchid", batchid);
		Properties jyconfig = new GetProperty().bornProp("courtJointPrisonconfig", null);
		String jxjsxml = jyconfig.getProperty("jxjsxml");
		String folderPath = jxjsxml+File.separator+batchid+File.separator+crimid+File.separator+"给法院的送达回证"+File.separator+"13.送达回证";
		FileUtil.createDirIfNotExist(folderPath);
		String filePath = folderPath+File.separator+file.getOriginalFilename();
		File writfile = new File(filePath);
		if(!writfile.exists()){
			try {
				writfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			
			// 获取输出流
			OutputStream os = new FileOutputStream(writfile);
			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			InputStream is = file.getInputStream();
			int temp;
			// 一个一个字节的读取并写入
			while ((temp = is.read()) != (-1)) {
				os.write(temp);
			}
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "1";
    }
    
    //省局页面
    @RequestMapping(value = "/baoSongCourtListPageSJ.page")
    public ModelAndView baoSongJcyListPageSJ(HttpServletRequest request) {    
        returnResourceMap(request);
        SystemUser su = getLoginUser(request);
        Date date = new Date();
        String curyear = request.getParameter("curyear")==null?"":request.getParameter("curyear");
        String flag = request.getParameter("flag")==null?"":request.getParameter("flag"); 
        if("".equals(curyear)){
            curyear = DateUtil.dateFormat(date, "yyyy");
        }
        String departid = su.getDepartid();
        String initPageIndexStr = (String) request.getSession().getAttribute("initPageIndex");
        String initPageSizeStr = (String) request.getSession().getAttribute("initPageSize");
        if(StringNumberUtil.notEmpty(initPageIndexStr)){
            request.setAttribute("initPageIndex", Integer.parseInt(initPageIndexStr));
        }
        if(StringNumberUtil.notEmpty(initPageSizeStr)){
            request.setAttribute("initPageSize", Integer.parseInt(initPageSizeStr));
        }
        request.setAttribute("curyear", curyear);
        request.setAttribute("departid", departid);
        ModelAndView mav = new ModelAndView("court/baoSongCourtPageSJ");
        return mav;
    }
    
    //查看退回补充材料-清除数据
    @RequestMapping(value = "/deleteData.action")
    @ResponseBody
    public String deleteData(HttpServletRequest request,String picibianhao,String crimid) {    
    	return openToOutsideService.deleteData(picibianhao,crimid);
    }
    
    //向法院获取退回补充材料信息
    @RequestMapping(value = "/getBackDataFromCourt.action")
    public void getBackDataFromCourt() {    
    	getDataFromCourt.doBiz("1601B");
    }
    
    //向法院获取立案信息
    @RequestMapping(value = "/getPutOnDataFromCourt.action")
    public void getPutOnDataFromCourt() {    
    	getDataFromCourt.doBiz("1601D");
    }
    
    //向法院获取退开庭信息
    @RequestMapping(value = "/getOpenCourtDataFromCourt.action")
    public void getOpenCourtDataFromCourt() {    
    	getDataFromCourt.doBiz("1601E");
    }
    
    //向法院获取退回补充材料信息
    @RequestMapping(value = "/getRulingDataFromCourt.action")
    public void getDataFromCourt() {    
    	getDataFromCourt.doBiz("1601F");
    }
    
    
    public static void main(String[] args) {
    	String localChartSet = System.getProperty("file.encoding");   
        System.out.println("localChartSet>>>>"+localChartSet);  
    		File file = new File("C:\\Users\\Administrator\\Desktop\\减刑假释对接.docx");
            byte[] by = new byte[(int) file.length()];
            try {
                InputStream is = new FileInputStream(file);
                ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
                byte[] bb = new byte[2048];
                int ch;
                ch = is.read(bb);
                while (ch != -1) {
                    bytestream.write(bb, 0, ch);
                    ch = is.read(bb);
                }
                by = bytestream.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            for (byte b : by) {
				System.out.println(b);
			}
			
        }
	

}
