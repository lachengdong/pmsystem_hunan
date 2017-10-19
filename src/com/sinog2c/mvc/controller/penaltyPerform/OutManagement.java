package com.sinog2c.mvc.controller.penaltyPerform;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import com.sinog2c.mvc.controller.base.ControllerBase;

/**
 * @author kexz
 *出监管理
 * 2014-7-17
 */
@Controller
public class OutManagement extends ControllerBase{
	@RequestMapping("/outManagement")
	public ModelAndView outManagement(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		String name = request.getParameter("name");
		try {
			name=new String(name.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/outManagement.jsp"));
	}
}
