package com.sinog2c.dao.api.supervise;

import com.sinog2c.model.supervise.SVTask;

public interface SVTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SVTask record);

    int insertSelective(SVTask record);

    SVTask selectByPrimaryKey(Long id);
    
    SVTask selectbyflowid(String  flowid);    

    int updateByPrimaryKeySelective(SVTask record);

    int updateByPrimaryKey(SVTask record);
}