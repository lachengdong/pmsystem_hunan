package com.sinog2c.dao.api.khjc;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.sinog2c.model.khjc.TkhMonthlysummary;

@Component("tkhMonthlysummaryMapper")
public interface TkhMonthlysummaryMapper {
    int deleteByPrimaryKey(Integer monthid);

    int insert(TkhMonthlysummary record);

    int insertSelective(TkhMonthlysummary record);

    TkhMonthlysummary selectByPrimaryKey(Integer monthid);

    int updateByPrimaryKeySelective(TkhMonthlysummary record);

    int updateByPrimaryKey(TkhMonthlysummary record);
    
    List<Map<String,Object>> getRewardMonthlySummaryList(Map<String,Object> map);
    
    int getRewardMonthlySummaryCount(Map<String,Object> map);
}