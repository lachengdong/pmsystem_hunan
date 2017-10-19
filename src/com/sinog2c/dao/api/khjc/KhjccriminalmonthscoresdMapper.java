package com.sinog2c.dao.api.khjc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.khjc.Khjccriminalmonthscoresd;

public interface KhjccriminalmonthscoresdMapper {
    int deleteByPrimaryKey(String id);

    int insert(Khjccriminalmonthscoresd record);

    int insertSelective(Khjccriminalmonthscoresd record);
    
    int insertByMap(Map<Object,Object> map);

    Khjccriminalmonthscoresd selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Khjccriminalmonthscoresd record);

    int updateByPrimaryKey(Khjccriminalmonthscoresd record);
    
    int updateByMap(Map<Object,Object> map);

    //获取考核月报表信息
    List<HashMap<Object,Object>> getCriminalForMonthCheck(HashMap<Object,Object> map);
	//获取考核月报表信息总数
	public int countForMonthCheck(HashMap<Object,Object> map);
	
	List<Map> searchScoreByCrimid(Map map);
}