package com.sinog2c.mvc.controller.oa.officemanage;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.util.property.SimpleExcelWrite;
import com.sinog2c.model.oa.OaBgyp;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.oa.OaBgypService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 办公用品领取记录类
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("officeManage")
public class officeYPController extends ControllerBase {
	
	@Autowired
	private OaBgypService bgypService;
	private JsonUtil jsonUtil;
	

	/**
	 * 办公用品领用记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/office.action")
	public ModelAndView officemanage(HttpServletRequest request,
			HttpServletResponse response) {
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("oa/officeManage/office");
		mav.addObject("path", path);
		return mav;
	}
	
	/**
	 * 加载页面数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getBgypList.json")
	@ResponseBody
	public Object getBgList(HttpServletRequest request) throws Exception{
		//定义返回结果
		Map<String, Object> resultmap=new HashMap<String, Object>();
		//定义查询参数
		List<Map> data=new ArrayList<Map>();
		Map paramMap = parseParamMap(request);
		String key = StringNumberUtil.returnString2(paramMap.get("key"));
		String modeltype = StringNumberUtil.returnString2(paramMap.get("wpname"));
		key = URLDecoder.decode(key,"UTF-8");
		data=bgypService.getBgypList(key);
		return data;
	}
	
	/**
	 * @return 操作数据（新增、修改、删除）
	 */
	@RequestMapping(value = "/saveDatasBg.json")
	@ResponseBody
	public int saveDatasBg(HttpServletRequest request){
		bgypService.saveBgyp(request);
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
            cell.setCellValue("物品名称");
            cell = row.createCell(2);
            cell.setCellValue("领用数量");
            cell = row.createCell(3);
            cell.setCellValue("领用人签字");
            cell = row.createCell(4);
            cell.setCellValue("领用日期");
            cell = row.createCell(5);
            cell.setCellValue("备注");
            for (int i = 0; i < list.size(); i++)
            {
            	HashMap map = (HashMap) list.get(i);
                row = sheet.createRow((int)i+1);//创建一行
                row.createCell(0).setCellValue(i+1);
                row.createCell(1).setCellValue(map.get("wpname") != null?map.get("wpname").toString():"");
                row.createCell(2).setCellValue(map.get("lysl") != null?map.get("lysl").toString():"");
                row.createCell(3).setCellValue(map.get("lyrsign") != null?map.get("lyrsign").toString():"");
                row.createCell(4).setCellValue(map.get("lyrq") != null?map.get("lyrq").toString():"");
                row.createCell(5).setCellValue(map.get("remark") != null?map.get("remark").toString():"");
            }
            fOut = response.getOutputStream();
            workbook.write(fOut);
                fOut.flush();
                fOut.close();
            session.setAttribute("state", "open");
        System.out.println("文件生成...");
        return "";
	}
}
