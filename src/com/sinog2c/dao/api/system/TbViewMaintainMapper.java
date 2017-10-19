package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.TbViewMaintain;
import com.sinog2c.model.system.TbViewMaintainExample;

@Repository
public interface TbViewMaintainMapper {
    int deleteByPrimaryKey(String id);

    int insert(TbViewMaintain record);

    int insertSelective(TbViewMaintain record);

    TbViewMaintain selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbViewMaintain record, @Param("example") TbViewMaintainExample example);

    int updateByExample(@Param("record") TbViewMaintain record, @Param("example") TbViewMaintainExample example);

    int updateByPrimaryKeySelective(TbViewMaintain record);

    int updateByPrimaryKey(TbViewMaintain record);
    
    Map selectByCondition(Map map);
    
    List<Map> selectByProvinceid(Map map);
    
}