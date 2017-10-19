package com.sinog2c.dao.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SystemResource;

@Repository("systemResourceMapper")
public interface SystemResourceMapper {

    public int insertSelective(SystemResource resource);
    
	public int insertByMap(Map<String, Object> map);
    //
    public int insertToHistory(SystemResource resource);
    
    public int delete(SystemResource resource);

    public int update(SystemResource resource);

	public int updateByMap(Map<String, Object> map);
    /**
     * 获取自增长的 resid
     * @return
     */
    public int getNextId();
    /**
     * 获取下一个ID,无缓存
     * @return
     */
    public int getNextIdNoCache(
			@Param("random")
    		int random );
    
    /**
     * 查询所有
     * @return
     */
    public List<SystemResource> selectAll();
    /**
     * 获取所有菜单
     * @return
     */
    public List<SystemResource> getAllMenu();
    
    
    // 可能后期还需要修改
    // 按页面显示
    public List<SystemResource> getAllByPage(
			@Param("start")
    		int start, 
			@Param("end")
    		int end);
    //
    public SystemResource getByResourceId(@Param("resid")String resid);
    //
    int countAll();
    /**
	 * 根据 pid取得直接子元素资源列表
	 * 
	 * @param parentId
	 * @return
	 */
    List<SystemResource> listByResourcePid(String parentId) ;
    

    /**
     * 根据用户ID查询资源
     * @param userid 用户ID
     * @param restypeid 资源类型, 如果查询所有,请传入 0
     * @return
     */
    public List<SystemResource> getResourcesByUserid(
			@Param("userid")
    		String userid, 
			@Param("restypeid")
    		int restypeid);
    
    /**
     * 根据用户ID查询资源
     * @param userid 用户ID
     * @param restypeid 资源类型, 如果查询所有,请传入 0
     * @param presid 父资源ID
     * @return
     */
    public List<SystemResource> getResourcesByConditions(Map map);
    
    /**
     * 根据资源类型查询资源
     * @param restypeid
     * @return
     */
    public List<SystemResource> selectResourcesByType(@Param("restypeid") int restypeid);
    /*
	 * 方法描述：根据资源Id获取罪犯处理页面的查询条件
	 */
    public String getTheQueryCondition(@Param("resid") String resid);
    
    public List<Map> getResourcesByMap(Map map);
    
    public List<Map> getUserInfoByMap(Map map);

    /**
     * 递归获取所有子资源
     * @param fromresid
     * @return
     */
	public List<SystemResource> listAllByPid(@Param("presid") String presid);
	List<SystemResource> selectMenubyrestypeid(@Param("ismenu") String ismenu,@Param("restypeid") int restypeid);
	public List<Map> getResourcesByFlowdefid(@Param("flowdefid") String flowdefid,@Param("resid") String resid,@Param("userid") String userid);
	
	public List<Map> lockSignNode(@Param("userid")String userid,
			                      @Param("flowdefid")String flowdefid,
			                      @Param("departid")String departid);
	
    
}