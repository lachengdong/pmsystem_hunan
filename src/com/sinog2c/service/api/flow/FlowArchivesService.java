package com.sinog2c.service.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowArchivesCode;
import com.sinog2c.model.system.SystemUser;

public interface FlowArchivesService {
	/**
	 * 插入表数据
	 * @return
	 */
//    public int insert(FlowArchives flowArchives);
    /**
     * 插入表数据
     * @return
     */
    public int insertSelective(FlowArchives flowArchives);
    /**
	 * 根据归档id删除表数据
	 * @param id
	 * @return
	 */
    public int delete(String id);
    /**
	 * 返回表数据的总数
	 * @return
	 */
    public int countAll();
    /**
     * 查询序列 获取归档ID
     * @param record
     * @return
     */
    public String getArchiveid(String departid);
    /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
    public List<FlowArchives> selectAll();
    /**
	 * 查询表数据返回无大字段的对象集合
	 * @return
	 */
    List<FlowArchives> selectAllWithOutClob();
    /**
	 * 根据流程ID查询数据
	 * @param flowid
	 * @return
	 */
    public FlowArchives findByFlowid(String flowid);
    /**
	 * 根据归档ID查询数据
	 * @param archiveid
	 * @return
	 */
    public FlowArchives findByArchiveid(String archiveid);
    
    /**
     * 查询需要归档的流程文件
     * @return
     */
    public List<HashMap<Object, Object>> selectAllByArchDoc(String personid);
    /**
     * 查询需要归档的非流程文件
     * @return
     */
    public List<HashMap<Object, Object>> selectAllByArchDocNoFlow(Map<String,Object> map);
    /**
     * 添加档案信息
     * @return
     */
    public int insertArchtive(String resid,String flowdraftid,String personid,String personname,String docconent,SystemUser user,FlowArchivesCode code);
    /**
     * 更新罪犯电子档案信息
     */
    public int updateArchtive(String flowid,String flowdraftid,String commenttext,String docconent,SystemUser user);
    
    public String getArchiveDocconentByFlowid(String flowid);
    
    public String getArchiveDocconentByArchiveid(String archiveid);
    
    public FlowArchives getArchivesData(Map map);
    
    public int fileCaseData(Map<String, Object> caseFileDataMap,SystemUser user);
    
    public String getDocconent(Map map);
    
	/**
	 * 获得档案
	 * @param bean
	 * @return
	 */
	public Object getOneArchive(String applyids, String archiveid);
	
	public List<Map<String,Object>> getFlowarchiveidByFlowid(String flowids);
}