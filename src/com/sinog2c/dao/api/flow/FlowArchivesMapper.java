package com.sinog2c.dao.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.UvFlow;

public interface FlowArchivesMapper {
	/**
     * 插入表数据
     * @param record
     * @return
     */
    int insert(FlowArchives record);
    /**
     * 插入表数据
     * @param record
     * @return
     */
    int insertSelective(FlowArchives record);
    /**
     * 更新表数据
     * @param record
     * @return
     */
    int update(FlowArchives record);
    /**
     * 根据主键删除数据
     * @param id
     * @return
     */
    int delete(String id);
    /**
     * 根据归档ID删除数据
     * @param id
     * @return
     */
    int removeByArchid(@Param("archiveid")String archiveid);
    /**
     * 查询表所有数据
     * @return
     */
    List<FlowArchives> selectAll();
    /**
     * 查询无大字段的表所有数据
     * @return
     */
    List<FlowArchives> selectAllWithOutClob();
    /**
     * 查询表数据的条数
     * @param record
     * @return
     */
    int countAll();
    /**
     * 查询序列 获取归档ID
     * @param record
     * @return
     */
    String getArchiveid(String departid);
    /**
     * 根据流程ID查询数据 
     * @param record
     * @return
     */
    FlowArchives findByFlowid(String flowid);
    /**
     * 根据档案ID查询数据
     * @param record
     * @return
     */
    FlowArchives findByArchiveid(String archiveid);
    /**
     * 查询罪犯电子档案信息
     * @param map
     * @return
     */
    FlowArchives getArchivesData(Map map);
    
    /**
     * 查询需要归档的流程文件
     * @param personid
     * @return
     */
    List<HashMap<Object, Object>> selectAllByArchDoc(@Param("personid")String personid);
    /**
     * 查询需要归档的非流程文件
     * @param personid
     * @return
     */
    List<HashMap<Object, Object>> selectAllByArchDocNoFlow(Map<String,Object> map);
    
    String findArchivesById(Map map);
    /*
     * 判断档案是否归档,返回档案id
     */
    String judgeArchivesWhetherArchiving(@Param("personid")String personid,@Param("codeid")String codeid);
    
    String getDocconent(Map map);
    List<HashMap<String, String>> getOneArchive(Map<String,String> map);
    
    void updateDecryptByArchiveid(Map<String,String> map);
    
    public List<Map<String,Object>> getFlowarchiveidByFlowid(@Param("flowids")String flowids);

}