package com.sinog2c.dao.api.commutationParole;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.UvFlowDoc;


public interface UvFlowDocMapper {

	List<UvFlowDoc> selectFlowDocByKeys(@Param("psid") Integer psid, @Param("departid") String departid, @Param("yearCaseNumberSql") String yearCaseNumberSql,@Param("anjianleixing") String anjianleixing, @Param("xingqileixing") String xingqileixing);

   
}