package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowArchivesCode;

public interface FlowArchivesCodeService {
    /**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowArchivesCode record);
    /**
	 * 根据主键查询数据
	 * @param codeid
	 * @param departid
	 * @return
	 */
    public FlowArchivesCode selectByPrimaryKey(String codeid,String departid);
    /**
     * 根据参数查询数据
     * @param tempid
     * @param departid
     * @return
     */
    public FlowArchivesCode selectByTempid(String tempid,String departid);
    /**
     *查询数据
     * @return
    */
    public List<FlowArchivesCode> selectAllByDepid(Map map);
    
    public List<FlowArchivesCode> selectAllByDepidByType(Map map);
    
    public List<FlowArchivesCode> selectAllByMap(Map map);
    
    public int selectAllByMapCount(Map map);
    
    public int findCodeByMap(Map map);
    
    public int deleteCodeByMap(Map map);
    
    public int updateCode(FlowArchivesCode record);
}