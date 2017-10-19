package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.TbsysCode;

@Repository
public interface TbsysCodeMapper {
    int deleteByPrimaryKey(Integer noid);

    int insert(TbsysCode record);

    int insertSelective(TbsysCode record);

    TbsysCode selectByPrimaryKey(Integer noid);

    int updateByPrimaryKeySelective(TbsysCode record);

    int updateByPrimaryKey(TbsysCode record);
    
    /**
     * 这命名明显不合理,内部实现很乱
     * @param codeType
     * @return
     */
    @Deprecated
    List<TbsysCode> selectValueByCodeType(String codeType);
    
    List<TbsysCode> selectValueByCodeType1(String codeType);
    
    
    String getCodeValueByCodeTypeAndCodeId(Map map);
    
    String getCodeValueByStrings(@Param("codetype")String codetype,@Param("codeid")String codeid);
    

    /**
     * 根据代码类别获取code列表<br/>
     * 代码按照： 代码类型(codetype),代码编号(codeid)来进行区分
     * @param codetype
     * @return
     */
    public List<TbsysCode> listByCodetype(String codetype);
    
    public List<Map> selectValueByMap(Map map);
    
    
    public List<Map> listByMap(Map map);
    
    public Map getDataByMap(Map map);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<TbsysCode> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<TbsysCode> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);

	List<TbsysCode> getCodeByCodeType(@Param("pcodeid") String pcodeid,@Param("scearch") String scearch);
	
	List<TbsysCode> getCodesByMap(Map map);

	Object selectValueByCodeTypeAndCodeid(Map map);
	
	TbsysCode getAnJianName(Map map);
	
	List<Map> getCodeByCodeTypeMapper(@Param("codetype")String codetype,
			                          @Param("codeid")String codeid,
			                          @Param("pcodeid")String pcodeid,
			                          @Param("orgid")String orgid);
	
	List<Map> getCodeByCodeMapper(@Param("codetype")String codetype,
            @Param("codeid")String codeid,
            @Param("pcodeid")String pcodeid,
            @Param("orgid")String orgid,
            @Param("scearch")String scearch);

	List<Map>  getCaseTypeMap();
	
	List<Map> getAllSFSelectImpl(Map map);
	
	Map getSJCodeidByXjCodeid(@Param("codeid")String codeid,
			                  @Param("type")String type);
	
	List<Map> getSanLeiType();
}