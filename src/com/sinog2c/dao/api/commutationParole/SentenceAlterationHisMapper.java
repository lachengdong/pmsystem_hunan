package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sinog2c.model.commutationParole.SentenceAlterationHis;

@Component("sentenceAlterationHisMapper")
public interface SentenceAlterationHisMapper {
    int insert(SentenceAlterationHis record);

    int insertSelective(SentenceAlterationHis record);
    
    int countSentenceChangesOfCrimid(Map map);
    
    List<Map> getSentenceChangesOfCrimid(Map map);
    
    Map getSingleSentenceChangeOfCrim(Map params);
    
    int countJiFenBuLuOfCrimid(Map<String,Object> map);
    
    List<Map<String,Object>> getJiFenBuLuOfCrimid(Map<String,Object> map);
}