package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.ResourceType;

@Repository("resourceTypeMapper")
public interface ResourceTypeMapper {
    public int insert(ResourceType record);

    public int insertSelective(ResourceType record);
    
    /**
     * 列出所有ResourceType
     * @return
     */
    public List<ResourceType> listAll();
    public List<Map> getReducePenaltyData();
    /**
     * 方法描述:查询出所有的字段类型
     * @author mushuhong
     * @version 2014年8月20日00:02:11
     * @return
     */
    public List<Map> getColumnDataType();
    /**
     * 方法描述：通过id得到 报表名称
     * @author mushuhong
     * @version 2014年8月20日14:57:53
     */
    public List<Map> getReportNameBySn(String resid);
    
    /**
     * 方法描述：通过资源编号，查询出对应的所有的sql
     * @author 
     * @Version 2015年4月29日17:37:02
     */
    public List<Map> getPlanSqlByResidNew(String resid);
    
    /**
     * 方法描述：通过资源编号，查询出对应的所有的sql
     * @author mushuhong
     * @Version 2014年8月20日17:37:02
     */
    public List<Map> getPlanSqlByResid(String resid);
    
    /**
     * 方法描述：通过sql语句 查询出对应的内容
     * @author mushuhong
     * @version
     */
    public List<Map> getReportDataByPlanSql(Map sql);
    
    public List<Map> getAllReportResourcesMapper(String userid);
    
    public List<Map> getResouceType(Map map);
    
  //  public List<ResourceType> getResouceType(Map map);

	public int getCount(Map map);
  
    public  int updateByPrimaryKeySelective(ResourceType record);
   
    public  int deleteByPrimaryKey(String rid);
    
    public int jqCaseTypeChengBaoBangPageCount(@Param("planSql")String planSql);
    
    public List<Map> jqCaseTypeChengBaoBangPageData(@Param("planSql")String planSql);
    
    public Map jqCaseTypeChengBaobiao(@Param("menuid")String menuid);
    
    public Map jqCaseTypeChengBaobiaoSql(@Param("planSql")String planSql);
    
    public List<Map> exportXlsServiceMapper(@Param("planSql")String planSql);
    
    public List<Map> listMapsTemplateByMenuid(@Param("menuid")String menuid,
    		                                  @Param("reportortemp")int reportortemp);
    
    public Map getCaoZuoResource(Map map);
}