package com.sinog2c.mvc.controller.oa;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.oa.OaSealService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;


@Controller
@RequestMapping("oaseal")
public class OaSealController extends ControllerBase{
	@Resource
	private OaSealService oasealService;
	private JsonUtil jsonUtil;
	
	/**
	 * 印章管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/seallist.action")
	public ModelAndView addmanage(HttpServletRequest request,
			HttpServletResponse response) {
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("loan/sealList");
		mav.addObject("path", path);
		return mav;
	}
	
	/**
	 * 加载页面数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getYzList.json")
	@ResponseBody
	public Object getYzList(HttpServletRequest request) throws Exception{
		//定义返回结果
		Map<String, Object> resultmap=new HashMap<String, Object>();
		//定义查询参数
		List<Map> data=new ArrayList<Map>();
		Map paramMap = parseParamMap(request);
		String key = StringNumberUtil.returnString2(paramMap.get("key"));
		String modeltype = StringNumberUtil.returnString2(paramMap.get("yzmc"));
		key = URLDecoder.decode(key,"UTF-8");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("yzmc", key);	
		data=oasealService.getYzList(map);
		return data;
	}
	
	/**
	 * @return 操作数据（新增、修改、删除）
	 */
	@RequestMapping(value = "/saveDatasYz.json")
	@ResponseBody
	public int saveDatasYz(HttpServletRequest request){
		oasealService.saveOAyz(request);
		return  1;
	}
	
	@RequestMapping("/exportExcel")
	@ResponseBody
	public String exportExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String data = request.getParameter("data");
		List list = (List) this.jsonUtil.Decode(data);
		HttpSession session = request.getSession();
        session.setAttribute("state", null);
        // 生成提示信息，
        response.setContentType("application/vnd.ms-excel");
        String codedFileName = null;
        OutputStream fOut = null;
            // 进行转码，使其支持中文文件名
            codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // response.addHeader("Content-Disposition", "attachment;   filename=" + codedFileName + ".xls");
            // 产生工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            //产生工作表对象
            HSSFSheet sheet = workbook.createSheet();
            HSSFRow row = sheet.createRow((int)0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("印章名称");
            cell = row.createCell(2);
            cell.setCellValue("印章印模");
            cell = row.createCell(3);
            cell.setCellValue("确认状态");
            cell = row.createCell(4);
            cell.setCellValue("接收日期");
            cell = row.createCell(5);
            cell.setCellValue("保管人");
            for (int i = 0; i < list.size(); i++)
            {
            	HashMap map = (HashMap) list.get(i);
                row = sheet.createRow((int)i+1);//创建一行
                row.createCell(0).setCellValue(i+1);
                row.createCell(1).setCellValue(map.get("yzmc") != null?map.get("yzmc").toString():"");
                row.createCell(2).setCellValue(map.get("yzym") != null?map.get("yzym").toString():"");
                row.createCell(3).setCellValue(map.get("qrzt") != null?("1".equals(map.get("qrzt").toString())?
                		"完好":("2".equals(map.get("qrzt").toString())?"破损":"丢失")):"");
                row.createCell(4).setCellValue(map.get("jsrq") != null?map.get("jsrq").toString():"");
                row.createCell(5).setCellValue(map.get("bgr") != null?map.get("bgr").toString():"");
            }
            fOut = response.getOutputStream();
            workbook.write(fOut);
                fOut.flush();
                fOut.close();
            session.setAttribute("state", "open");
        System.out.println("文件生成...");
        return "";
	}

	@RequestMapping("/exportExcelcheck")
	@ResponseBody
	public void check(HttpServletRequest request, HttpServletResponse response) {
		try {
			if ("open".equals(request.getSession().getAttribute("state"))) {
				request.getSession().setAttribute("state", null);
				response.getWriter().write("true");
				response.getWriter().flush();
			} else {
				response.getWriter().write("false");
				response.getWriter().flush();
			}
		} catch (IOException e) {
		}
	}
}
