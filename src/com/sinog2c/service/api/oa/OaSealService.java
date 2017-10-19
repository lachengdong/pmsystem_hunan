package com.sinog2c.service.api.oa;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.oa.OAyz;

public interface OaSealService {

	int deleteByPrimaryKey(String yzid);

    int insert(OAyz record);

    int insertSelective(OAyz record);

    OAyz selectByPrimaryKey(String yzid);

    int updateByPrimaryKeySelective(OAyz record);

    int updateByPrimaryKey(OAyz record);
    
    List<Map> getYzList(Map map);
    
    void saveOAyz(HttpServletRequest request);
    
}
