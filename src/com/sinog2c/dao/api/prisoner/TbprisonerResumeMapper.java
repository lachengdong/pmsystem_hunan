package com.sinog2c.dao.api.prisoner;


import java.util.List;
import java.util.Map;

import com.sinog2c.model.prisoner.TbprisonerResume;

/**
 * 罪犯简历表
 * @author wangxf
 *
 */
public interface TbprisonerResumeMapper {
	
    int deleteByPrimaryKey(Integer resumeid);

    int insertSelective(TbprisonerResume record);

    TbprisonerResume selectByPrimaryKey(Integer resumeid);

    int updateByPrimaryKeySelective(TbprisonerResume record);
    
    //根据罪犯id返回罪犯简历
    List<TbprisonerResume> findBycrimid(String crimid);
    
    String getResumeid();
    
    List<Map> getJianXingBaseInfo(String crimid);
    
    List<Map<String,Object>> getCriminalInfo(String crimid);
    
}