package com.sinog2c.service.api.commutationParole;

import javax.servlet.http.HttpServletRequest;


public interface QualificationScreeningService {

	public void qualificationScreening(String departid, String orgid, HttpServletRequest request);
	
	public void qualificateCommuteData(String departid, String orgid, HttpServletRequest request);
}
