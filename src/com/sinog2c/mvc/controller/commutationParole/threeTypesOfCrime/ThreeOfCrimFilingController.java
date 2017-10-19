package com.sinog2c.mvc.controller.commutationParole.threeTypesOfCrime;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.mvc.controller.base.ControllerBase;

/**
 * 三类犯报备
 * @author hzl
 * 
 */
@Controller
@RequestMapping(value = "/threeOfCrimFiling")
public class ThreeOfCrimFilingController extends ControllerBase {
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toThreeOfCrimFilingPage.page")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("commutationParole/threeTypesOfCrime/ThreeOfCrimFiling");
	}
}
