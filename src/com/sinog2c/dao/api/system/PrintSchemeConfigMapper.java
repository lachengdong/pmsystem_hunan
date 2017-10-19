package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.PrintSchemeConfig;
import com.sinog2c.model.system.PrintSchemeConfigKey;

public interface PrintSchemeConfigMapper {
    int deleteByPrimaryKey(PrintSchemeConfigKey key);

    int insert(PrintSchemeConfig record);

    int insertSelective(PrintSchemeConfig record);

    PrintSchemeConfig selectByPrimaryKey(PrintSchemeConfigKey key);

    int updateByPrimaryKeySelective(PrintSchemeConfig record);

    int updateByPrimaryKey(PrintSchemeConfig record);

	int queryPrintSchemeConfigCount(@Param("psid") int psid,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);

	List<Map<String, String>> getPrintSchemeConfigPageList(@Param("start") int start, @Param("end") int end, @Param("psid") int psid,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);

	List<PrintSchemeConfig> selectConfigsByPrintSchemeKey(Integer psid);

}