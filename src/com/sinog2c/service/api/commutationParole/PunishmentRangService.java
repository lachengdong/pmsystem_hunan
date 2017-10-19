package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.model.commutationParole.TbxfMergeReference;
import com.sinog2c.model.commutationParole.TbxfPrisonerSentence;
import com.sinog2c.model.commutationParole.TbxfPunishmentRang;

public interface PunishmentRangService {
	
	
	/**
	 * 查询符合条件的数据总条数
	 * @param key
	 * @return
	 */
	public int getCount(String departid, String key);
	
	/**
	 * 分页显示列表数据
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	public List<HashMap> getPunishmentRangList(String departid, String key, String sortField, String sortOrder, int start, int end );
	
	/**
	 * 删除数据
	 * @return
	 */
	public int deletePunishmentRang(int punids);
	
	/**
	 * 获取下拉菜单数据
	 * @param id
	 * @param text
	 * @param tableName
	 * @return
	 */
	public List<HashMap> ajaxSelectData(String id, String text, String tableName);
	
	/**
	 * 获取下拉菜单数据,余刑
	 * @return
	 */
	public List<HashMap> ajaxSelectPrisonerSentence(String departid);
	
	/**
	 * 添加数据
	 * @param record
	 * @return
	 */
	public int insertPunishmentRang(TbxfPunishmentRang record);
	
	/**
	 * 修改数据
	 * @param record
	 * @return
	 */
	public int updatePunishmentRang(TbxfPunishmentRang record);
	
	/**
	 * 根据id获取数据
	 * @param punid
	 * @return
	 */
	public TbxfPunishmentRang getPunishmentRangById(Integer punid);
	
	/**
	 * 根据id获取数据
	 * @param senid
	 * @return
	 */
	public HashMap getPrisonersentenceById(Integer senid);
	
	public List<HashMap<String,Object>>getSchemeDepart(String depart);
	
	int getPunid();
	
	List<TbxfPunishmentRang> getrangdataBydepartid(String depart);
	
	List<TbxfCommutationReference> getCommutationReferenceListData(String departid);
	
	List<TbxfMergeReference> getMergeListData(String departid);
	
	int insertPunishmentRangAuto(TbxfPunishmentRang record);
	int insertTbxfWideandthinscheme(Map map);
	
	int updateApproveType(TbxfPunishmentRang record);
	
	public int countSentenc(Map map);
	
	public List<Map> selectSentenc(Map map);
	
	public Map getSentencInfo(Integer senid);
	
	public int ajaxBySaveSentence(Map map,String optype);
	
	public int ajaxByRemoveSentence(String senid);
	
	public List<Map> ajaxByWideandthins();
	
	public int ajaxCreateWideandthins(HttpServletRequest request);
	
	public int editPunishmentRang(TbxfPunishmentRang record);
	
	public List<Map<String,Object>> getQualifierItem(Map<String,Object> map);
	
	public int removeQualifierSet(Map<String,Object> paramap);
	
	public int saveQualifierSet(String operatetype,Map<String,Object> paramap);
	
	public int saveQualifierItem(String operatetype,Map<String,Object> paramap);
	
	public int removeQualifierItem(Map<String,Object> paramap);
}
