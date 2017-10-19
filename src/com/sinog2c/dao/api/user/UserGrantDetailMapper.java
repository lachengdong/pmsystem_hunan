package com.sinog2c.dao.api.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.user.UserGrantDetail;

@Repository
public interface UserGrantDetailMapper {
	
	/**
	 * 插入记录
	 * @param record
	 * @return
	 */
    public int insert(UserGrantDetail record);
    
    /**
     * 插入指定数据
     * @param record
     * @return
     */
    public int insertSelective(UserGrantDetail record);

    /**
     * 根据主键值更新唯一记录.要求全部值都具备
     * @param record
     * @return
     */
    public int update(UserGrantDetail record);
    
    /**
     * 根据主键值更新唯一记录.要求全部值都具备
     * @param record
     * @return
     */
    public int updateSelective(UserGrantDetail record);

    /**
     * 根据主键的三个字段来删除数据.<br/>
     * 注意: 至少需要2个键才合理. <br/>
     * 如: 用户ID, OPID 为删除用户赋予另一个用户的所有权限<br/>
     * 如: 用户ID, OPID,资源ID 为删除单条权限记录<br/>
     * @param key
     * @return
     */
	public int delete(UserGrantDetail key);
	
    /**
     * 根据主键获取单条记录.要求3个主键值都具备
     * @param key
     * @return
     */
    public UserGrantDetail getByKey(UserGrantDetail key);
    
    /**
     * 所有记录的数量
     * @return
     */
	public int countAll();
	
	/**
	 * 列出所有记录
	 * @return
	 */
    public List<UserGrantDetail> listAll();
    
    /**
     * 分页获取记录
     * @param start
     * @param end
     * @return
     */
    public List<UserGrantDetail> listAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end,
			@Param("sortField")
    		String sortField,
			@Param("sortOrder")
    		String sortOrder
    		);
    
    /**
     * 获取 opid 赋予 userid 的记录
     * @param userid
     * @param opid
     * @return
     */
    public List<UserGrantDetail> listByUseridOpid(
			@Param("userid")
    		String userid, 
			@Param("opid")
			String opid,
	        @Param("noticeid")
	        int noticeid
    );
                  
    
    /**
     * 获取 opid 赋予 userid 的记录
     * @param userid
     * @param opid
     * @return
     */
    public List<UserGrantDetail> listByUserid(
			@Param("userid")
    		String userid);
    
    /**
     * 方法描述：修改掉TBUSER_NOTICE（用户通知）结束日期并且修改状态
     * @author:mushuhong
     * @version:2015年1月16日15:32:41
     */
    public int updateStateByNoticeid(Map map);
    
    /**
     * 方法描述：修改授权信息表（TBUSER_USERRES）
     * @author:mushuhong
     * @version:2015年1月16日15:36:59
     */
    public int updateEndtimeByNoticeid(Map map);
    
    /**
     * 方法描述：修改之前删除对应的权限，把需要的内容重新插入数据库
     * @author :mushuhong
     * @version:2015年1月19日14:14:41
     */
    public void deleteUserGrants(@Param("noticeid")int noticeid);
    /**
     * 删除用户授权关系(细节)表中的无效记录
     */
//    public void deleteDisableGrants(@Param("opid")String opid,@Param("userid")String userid);
    /**
     * 插入指定数据至his表
     * @param record
     * @return
     */
    public int insertHisSelective(UserGrantDetail record);
    /**
     * 方法描述：根据presid删除相应的用户权限信息
     * @param presid
     * @return
     * @author :blue
     * @version:2015年4月24日14:1:33
     */
    int deleteByPresid(String presid);
}