package com.sinog2c.service.api.prisoner;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;

import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.system.SystemUser;


/**
 * 刑期变动
 * @author Administrator
 *
 */
public interface TbxfSentencealterationService {
	List selectTbxfs(Map<String,Object> map);
	int allCount(Map<String,Object> map);
	Map<String,Object> selectTbxfByCrimid(String crimid);
	
	/**
	 * 案件撤销
	 * @param ids
	 * @param menuid
	 * @return 案件撤销成功返success
	 */
	String withdrawalCasesOfJail(String flowdraftids,String crimids);
	/**
	 * 案件退回
	 * @param ids
	 * @param menuid
	 * @return 案件撤销成功返success
	 */
	String backCasesOfJail(String flowdraftids,String crimids);
		
	int getLiAnApproveCount(Map map);
	
	List<Map> getLiAnApproveList(Map map);
	
	int getLiAnCount(Map map);
	
	List<Map> getLiAnList(Map map);
	
	public String getCrimiNameByCrimid(String crimid);
	
	
	public int provinceLiAnCount(Map map);
	
	public  List<Map> provinceLiAnList(Map map);
	
	public int countCourtLiAnList(Map map);
	
	public  List<Map> getCourtLiAnList(Map map);
	
	public int countCourtReportLiAnList(Map map);
	
	public  List<Map> getCourtReportLiAnList(Map map);
	
	public int countCourtCheckLiAnList(Map map);
	
	public  List<Map> getCourtCheckLiAnList(Map map);
	
	public List getJailList(Map map);
	
	public List getJailListUnderCourt(Map map);
	
	public int countOfficeShenHeList(Map map);
	
	public List<Map> officeShenHeList(Map map);
	List<Map<String,Object>> selectTbxfs2(Map<String,Object> map);
	

	List<Map<String,Object>> imselectTbxfs2(Map<String,Object> map);
	
	List<Map<String,Object>> selectTbxfs3(Map<String,Object> map);
	
//	List<Map<String,Object>> selectTbxfs2_sx(Map<String,Object> map);
	
	int selectTbxfs2Count(Map<String,Object> map);
	

	int imselectTbxfs2Count(Map<String,Object> map);
	
	int selectTbxfs3Count(Map<String,Object> map);
	
//	int selectTbxfs2Count_sx(Map<String,Object> map);
    /**
     * 省局案件查看
     */
	List<Map> getSJCaseList(Map map);
	int getSJCaseCount(Map map);
    /**
     * 保外就医案件查看
     */
    List<Map> getByjyCaseList(Map map);
    int getByjyCaseCount(Map map);
	
	Map selectTbxfMapBySql(String sql);
	
	TbxfSentenceAlteration selectByPrimaryKey(String crimid);
	
	List<TbxfSentenceAlteration> sentenceAlterationList(Map map);
	
	List<String> departidlist();
	
	List<Map> getAreaidList(Map map);
	
	public Map getCourtCaseBaseInfoOfCrimid(Map map);
	
	List<Map> getBWCaseDataInfo(Map map);
	
	int getBWCaseDataInfoCount(Map map);
	
	/**
	 * 方法描述：查询出案件查看内容
	 * @param crimid
	 * @return
	 */
	Object bwjyLookCaseDataInfo(HttpServletRequest request,SystemUser user);
	
	/**
	 * 方法描述：撤销保外就医案件内容
	 * @author：mushuhong
	 * @version：2015年1月21日10:25:53
	 * @param crimid
	 * @return
	 */
	Object cheXiaoBwjyCase(HttpServletRequest request,SystemUser user);
	
	Map<String,Object> getThreeOfCrimBaseinfo(String crimid);
	
	List<Map<String,Object>> getPunishmentChangeinfo(String crimid);
	
	List<Map> getCourtLocalCaseData(Map map);
	
	int getCourtLocalCaseDataCount(Map map);
	/**
	 * 上海特有：查询现余刑
	 */
	Map<String,Object> getNowYuXing(Map<String, Object> map);
	
	public Map getParoleByCrimid(String crimid,String flowdraftid,SystemUser user,HttpServletRequest request);
	
	public Map<String,Object> getCommutationaccordByCrimid(Map<String,Object> map);
	
	public String getGaizaobiaoxian(String crimid,String id,SystemUser user,HttpServletRequest request);
	Map<String, Object> getCourtFuyiInfo(Map paraMap);
	
	/**
	 * 方法描述：河北保外案件查看
	 */
	public int judgeBwCaseExistSave(HttpServletRequest request,SystemUser user);
	int countCourtJianduLiAnList(Map<String, Object> map);
	List<Map> getCourtJianduLiAnList(Map<String, Object> map);
	
	
	
    int getBaobeiLiAnCount(Map map);
	
	List<Map> getBaobeiLiAnList(Map map);
	
	Map getCrimidFlowData(String flowdraftid);
	
	int addSanLeiFanBaoBei(Map resourceMap);
	
	
	int updateSanLeiFanBaoBei(Map resourceMap);
	
}
