package com.sinog2c.dao.api.criminalexam;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.criminalexam.TbyzCheckForWork;
import com.sinog2c.model.criminalexam.TbyzCheckForWorkExample;
import com.sinog2c.model.criminalexam.TbyzCheckForWorkKey;
import org.apache.ibatis.annotations.Param;

public interface TbyzCheckForWorkMapper {
    int deleteByPrimaryKey(TbyzCheckForWorkKey key);

    int insert(TbyzCheckForWork record);

    int insertSelective(TbyzCheckForWork record);

    TbyzCheckForWork selectByPrimaryKey(TbyzCheckForWorkKey key);

    int updateByExampleSelective(@Param("record") TbyzCheckForWork record, @Param("example") TbyzCheckForWorkExample example);

    int updateByExample(@Param("record") TbyzCheckForWork record, @Param("example") TbyzCheckForWorkExample example);

    int updateByPrimaryKeySelective(TbyzCheckForWork record);

    int updateByPrimaryKey(TbyzCheckForWork record);
    
    List<TbyzCheckForWork> selectCheckForWork(@Param("start") int start, @Param("end") int end);
    
    List<Map> selectCheckForWorkMap(Map map);
    
    int selectCheckForWorkCount(Map map);
    
    String workSetAddOrUpdate(Map map);
    
    String  workAddOrUpdate(Map map);
    
    List<TbyzCheckForWork> workSetSelect(Map map);
    
    List<Map> getInfobyDepartAndMonth(@Param("orgid") String orgid, @Param("yeardate") String yeardate);
    
    int updateSelective(TbyzCheckForWork record);
    
    String workSetDanGeUpdate(Map map);
    
    int workSetDanGeAdd(Map map);
    
    int insertSelective(Map map);
    
    int updateSelective(Map map);
    
    int updateWorkData(Map map);
    
    int selectByCrimid(Map map);
}