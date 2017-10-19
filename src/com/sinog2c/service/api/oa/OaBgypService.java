package com.sinog2c.service.api.oa;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.oa.OaBgyp;

/**
 * 办公用品领用记录
 * @author admin
 *
 */
public interface OaBgypService {

	void saveBgyp(HttpServletRequest request);
	List<Map> getBgypList(String string);
	
	int deletByid(String bgypid);
}
