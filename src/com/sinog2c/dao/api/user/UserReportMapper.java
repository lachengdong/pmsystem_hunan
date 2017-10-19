package com.sinog2c.dao.api.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.user.UserReport;

public interface UserReportMapper {
    int deleteByPrimaryKey(String rid);

    int insert(UserReport record);

    int insertSelective(UserReport record);

    UserReport selectByPrimaryKey(String rid);

    int updateByPrimaryKeySelective(UserReport record);

    int updateByPrimaryKey(UserReport record);
    
    int queryPreviewReportCount(@Param("type") String type,@Param("name") String name,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
    //根据类型 查询报表记录数
    int queryBaobiaoCount();
    List<UserReport> getUserReportByType(Map map);
    
    List<UserReport> getUserReportPageList(@Param("start") int start, @Param("end") int end);
    
    int queryPreviewResReportCount(@Param("userid") String userid);
    List<Map<String,String>> getReportManagePageList(@Param("start") int start, @Param("end") int end,@Param("type") String type,@Param("name") String name,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);

	List<Map> selectAllReport();
	
	int queryPreviewResReportGonggaoCount(Map map);
	
	List<UserReport> getUserReportGonggaoByType(Map map);

	List<UserReport> getTemplateSchemeList(@Param("userid")String userid);

}