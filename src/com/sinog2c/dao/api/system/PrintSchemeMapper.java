package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.UvFlowDoc;
import com.sinog2c.model.system.PrintScheme;

public interface PrintSchemeMapper {
    int deleteByPrimaryKey(Integer psid);

    int insert(PrintScheme record);

    int insertSelective(PrintScheme record);

    PrintScheme selectByPrimaryKey(Integer psid);

    int updateByPrimaryKeySelective(PrintScheme record);

    int updateByPrimaryKey(PrintScheme record);

	int queryPrintSchemeCount(@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);

	List<Map<String, String>> getPrintSchemePageList(@Param("start") int start, @Param("end") int end,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);

	List<PrintScheme> getPrintSchemeComboBox(String _parameter);

}