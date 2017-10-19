package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.FunctionDeclaration;
import com.sinog2c.model.system.FunctionDeclarationExample;
import org.apache.ibatis.annotations.Param;

public interface FunctionDeclarationMapper {
    int deleteByPrimaryKey(String menuid);

    int insert(FunctionDeclaration record);

    int insertSelective(FunctionDeclaration record);

    FunctionDeclaration selectByPrimaryKey(String menuid);

    int updateByExampleSelective(@Param("record") FunctionDeclaration record, @Param("example") FunctionDeclarationExample example);

    int updateByExampleWithBLOBs(@Param("record") FunctionDeclaration record, @Param("example") FunctionDeclarationExample example);

    int updateByExample(@Param("record") FunctionDeclaration record, @Param("example") FunctionDeclarationExample example);

    int updateByPrimaryKeySelective(FunctionDeclaration record);

    int updateByPrimaryKeyWithBLOBs(FunctionDeclaration record);

    int updateByPrimaryKey(FunctionDeclaration record);
    
    int getById(String id);
    
    void removeDocument(String menuid);
    
    int findByMenuid(String str);
    
    String getFunctionContent(String str);
    
    int getFunction(String str);
    
    List<Map> getExcelDetailByID(Map map);
    
    List<Map> getExcelSettingByID(Map map);
    
    int insertTableByCondition(Map map);
    
    int updateTableByCondition(Map map);
    
    List<Map> getExcelTypeByDepartid(Map map);
    
    List<Map> getCodeIdByCodeType(Map map);
    
    List<Map> getExcelAllCode(Map map);
    
    List<Map> getAllRepeatColumn(Map map);
    
    void callSql(@Param("callsql")String callsql);
    
}