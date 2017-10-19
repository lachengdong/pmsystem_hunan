package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.model.flow.TbflowInstanceDoc;


@Component("flowBaseDocMapper")
public interface FlowBaseDocMapper {
    int insert(FlowBaseDoc record);
    
    int delete(String id);
    
    List<FlowBaseDoc> selectAll();
   
    int countAll();
   
    FlowBaseDoc findById(String id);
    
    int updateByCondition(FlowBaseDoc flowBaseDoc);
    
    FlowBaseDoc findDocByflowdraftid(String flowdraftid);
    
    FlowBaseDoc findLastDocByflowdraftid(Map<String,Object> map);

	FlowBaseDoc findDocByflowdraftid2(@Param("flowdefid")String flowdefid, @Param("tempid")String tempid, @Param("crimid")String crimid);
	
	//start add by blue_lv 2015-07-14
	int deleteByPrimaryKey(String baseid);  

    int insertSelective(TbflowInstanceDoc record);

    TbflowInstanceDoc selectByPrimaryKey(String baseid);

    int updateByPrimaryKeySelective(TbflowInstanceDoc record);

    int updateByPrimaryKeyWithBLOBs(TbflowInstanceDoc record);

    int updateByPrimaryKey(TbflowInstanceDoc record);
    
    int deleteByflowid(String flowid);
    //end add by blue_lv 2015-07-14
    
}