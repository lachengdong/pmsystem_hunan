package com.sinog2c.service.impl.commutationParole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.commutationParole.TbxfCommutationReferenceMapper;
import com.sinog2c.dao.api.commutationParole.TbxfMergeReferenceMapper;
import com.sinog2c.dao.api.commutationParole.TbxfPrisonerSentenceMapper;
import com.sinog2c.dao.api.commutationParole.TbxfPunishmentRangMapper;
import com.sinog2c.dao.api.commutationParole.TbxfWideandthinschemeMapper;
import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.model.commutationParole.TbxfMergeReference;
import com.sinog2c.model.commutationParole.TbxfPunishmentRang;
import com.sinog2c.model.commutationParole.TbxfWideandthinscheme;
import com.sinog2c.service.api.common.CommonService;
import com.sinog2c.service.api.commutationParole.PunishmentRangService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


@Service("punishmentRangService")
public class PunishmentRangServiceImpl implements PunishmentRangService {
	
	@Resource
	private TbxfPunishmentRangMapper tbxfPunishmentRangMapper;
	@Resource
	private TbxfCommutationReferenceMapper tbxfCommutationReferenceMapper;
	@Resource
	private TbxfMergeReferenceMapper tbxfMergeReferenceMapper;
	@Resource
	private TbxfPrisonerSentenceMapper tbxfPrisonerSentenceMapper;
	@Resource
	private TbxfWideandthinschemeMapper tbxfWideandthinschemeMapper;
	@Resource
	private CommonService commonService;
	
	@Override
	public int getCount(String departid, String key) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.selectCount(departid, key);
	}
	
	@Override
	public List<HashMap> getPunishmentRangList(String departid, String key, String sortField, String sortOrder, int start, int end) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.selectDataList(departid, key, sortField, sortOrder, start, end);
	}

	@Override
	@Transactional
	public int deletePunishmentRang(int punid) {
		// TODO Auto-generated method stub
		tbxfPunishmentRangMapper.deleteMergereferenceByPunid(punid);
		tbxfPunishmentRangMapper.deleteCommutationreferenceByPunid(punid);
		tbxfPunishmentRangMapper.deleteWideandthinschemeByPunid(punid);
		return tbxfPunishmentRangMapper.deleteByPrimaryKey(punid);
	}

	@Override
	public List<HashMap> ajaxSelectData(String id, String text, String tableName) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.ajaxSelectData(id, text, tableName);
	}

	@Override
	public int insertPunishmentRang(TbxfPunishmentRang record) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.insert(record);
	}

	@Override
	public int updatePunishmentRang(TbxfPunishmentRang record) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.updateByPrimaryKey(record);
	}

	@Override
	public TbxfPunishmentRang getPunishmentRangById(Integer punid) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.selectByPrimaryKey(punid);
	}

	@Override
	public List<HashMap> ajaxSelectPrisonerSentence(String departid) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.selectPrisonerSentence(departid);
	}

	@Override
	public HashMap getPrisonersentenceById(Integer senid) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.getPrisonersentenceById(senid);
	}
	
	@Override
	public List<HashMap<String,Object>>getSchemeDepart(String depart) {
		return tbxfPunishmentRangMapper.getSchemeDepart(depart);
	}
	
	@Override
	public List<TbxfPunishmentRang> getrangdataBydepartid(String departid) {
		return tbxfPunishmentRangMapper.getrangdataBydepartid(departid);
	}
	@Override
	public int getPunid() {
		return tbxfPunishmentRangMapper.getPunid();
	}
	@Override
	public List<TbxfCommutationReference> getCommutationReferenceListData(String departid) {
		return tbxfCommutationReferenceMapper.getCommutationReferenceListData(departid);
	}
	@Override
	public List<TbxfMergeReference> getMergeListData(String departid) {
		return tbxfMergeReferenceMapper.getMergeListData(departid);
	}
	
	@Override
	public int insertPunishmentRangAuto(TbxfPunishmentRang record) {
		return tbxfPunishmentRangMapper.insertAuto(record);
	}
	
	@Override
	public int updateApproveType(TbxfPunishmentRang record) {
		return tbxfPunishmentRangMapper.updateByPrimaryKeySelective(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int countSentenc(Map map) {
		return tbxfPrisonerSentenceMapper.countSentenc(map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map> selectSentenc(Map map) {
		List<Map> list = MapUtil.turnKeyToLowerCaseOfList(tbxfPrisonerSentenceMapper.selectSentenc(map));
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map getSentencInfo(Integer senid){
		Map map = new HashMap();
		Map tempmap = tbxfPrisonerSentenceMapper.getSentencInfo(senid);
		Set<String> set = tempmap.keySet();
		for(String m:set){
			if(tempmap.containsKey(m)) map.put(m.toLowerCase(), tempmap.get(m));
		}
		return map;
	}

	@Override
	@Transactional
	public int ajaxBySaveSentence(Map map, String optype) {
		int row = 0;
		//格式化日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(map.containsKey("stime")) map.put("stime", format.parse(map.get("stime").toString().substring(0, 10)));
			if(map.containsKey("etime")) map.put("etime", format.parse(map.get("etime").toString().substring(0, 10)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(GkzxCommon.ONE.equals(optype)){
			int senid = tbxfPrisonerSentenceMapper.getKey();
			map.put("senid", senid);
			row = tbxfPrisonerSentenceMapper.insertByMap(map);
		}else if(GkzxCommon.TWO.equals(optype)){
			row = tbxfPrisonerSentenceMapper.updateByMap(map);
		}
		return row;
	}

	@Override
	@Transactional
	public int ajaxByRemoveSentence(String senid) {
		int row = 0;
		String[] senids = senid.split(",");
		if(senids!=null && senids.length>0){
			for(String m:senids){
				row = tbxfPrisonerSentenceMapper.deleteByPrimaryKey(Integer.valueOf(m));
			}
		}
		return row;
	}
	@Override
	public List<Map> ajaxByWideandthins(){
		List<Map> tempmap = tbxfWideandthinschemeMapper.ajaxByWideandthins();
		return tempmap;
	}

	@Override
	public int ajaxCreateWideandthins(HttpServletRequest request){
		int rows = 0;
		String punid = request.getParameter("punid");//筛查ID
		String toid = request.getParameter("toid");//从宽从严
		try{
			rows = tbxfWideandthinschemeMapper.deleteByPkstr(punid,toid);
			if(!StringNumberUtil.isNullOrEmpty(punid)){
				String[]punids = punid.split(",");
				if(!StringNumberUtil.isNullOrEmpty(toid)){
					String[] toids = toid.split(",");
					for(String pun : punids){
						if(!StringNumberUtil.isNullOrEmpty(toids)){
							for(String m:toids){
								rows = tbxfWideandthinschemeMapper.insertByPkstr(pun,m);
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rows;
	}

	@Override
	public int insertTbxfWideandthinscheme(Map map) {
		return tbxfPunishmentRangMapper.insertTbxfWideandthinscheme(map);
	}

	@Override
	public int editPunishmentRang(TbxfPunishmentRang record) {
		// TODO Auto-generated method stub
		return tbxfPunishmentRangMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public List<Map<String,Object>> getQualifierItem(Map<String,Object> map){
		return MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierItem(map));
	}
	
	@Override
	@Transactional
	public int saveQualifierSet(String operatetype,Map<String,Object> paramap){
		
		int row = 0;
		if(StringNumberUtil.isEmpty(operatetype) || "new".equals(operatetype)){
			row = tbxfPunishmentRangMapper.insertQualifierSet(paramap);
		}else if("edit".equals(operatetype)){
			row = tbxfPunishmentRangMapper.updateQualifierSet(paramap);
		}
		return row;
		
	}
	
	
	@Override
	@Transactional
	public int removeQualifierSet(Map<String,Object> paramap){
		int row = 0;
		row = tbxfPunishmentRangMapper.removeQualifierSet(paramap);
		return row;
	}
	
	
	@Override
	@Transactional
	public int saveQualifierItem(String operatetype,Map<String,Object> paramap){
		
		int row = 0;
		if(StringNumberUtil.isEmpty(operatetype) || "new".equals(operatetype)){
			row = tbxfPunishmentRangMapper.insertQualifierItem(paramap);
		}else if("edit".equals(operatetype)){
			row = tbxfPunishmentRangMapper.updateQualifierItem(paramap);
		}
		
		//更新所有col_name相同的formula，要求同一单位下如果col_name相同，则计算公式formula也相同
		tbxfPunishmentRangMapper.updateQualifierItemFormula(paramap);
		
		
		//修改表TBXF_QUALIFIER_TEMP结构
		String tablename = "TBXF_QUALIFIER_TEMP".toUpperCase();
		String col_name = StringNumberUtil.getStrFromMap("col_name", paramap).toUpperCase();
		String old_column = StringNumberUtil.getStrFromMap("old_column", paramap).toUpperCase();
		int value_type = Integer.parseInt(StringNumberUtil.getStrFromMap("value_type", paramap));
		String datatype = getDatatype(value_type);
		
		Map<String,Object> tableColumnMap = new HashMap<String,Object>();
		tableColumnMap.put("tablename", tablename);
		tableColumnMap.put("col_name", col_name);
		tableColumnMap.put("old_column", old_column);
		tableColumnMap.put("datatype", datatype);
		
		//如果是新增Item
		if(StringNumberUtil.isEmpty(operatetype) || "new".equals(operatetype)){
			//判断Item是否存在
			Map<String,Object> existTableColumn = commonService.getTableColumn(tableColumnMap);
			if(null != existTableColumn && ! existTableColumn.isEmpty()){//存在，则modifyColumn
				commonService.alterTableModifyColumn(tableColumnMap);
			}else{//不存存，则addColumn
				commonService.alterTableAddColumn(tableColumnMap);
			}
		}else if("edit".equals(operatetype)){//如果是修改字段
			//判断name是否被修改了
			if(! col_name.equalsIgnoreCase(old_column)){ //被修改了，则renameColumn
				commonService.alterTableRenameColumn(tableColumnMap);
			}
			//modifyColumn
			commonService.alterTableModifyColumn(tableColumnMap);
		}
		
		return row;
	}
	
	
	private String getDatatype(int value_type){
		String result = "";
		switch(value_type){
			case 1:
				result = "NUMBER(9)";break;//整数类型
			case 2:
				result = "NUMBER(9,4)";break;//浮点数类型
			case 3:
				result = "NUMBER(1)";break;//布尔类型
			case 4:
				result = "NUMBER(9)";break;//日期类型
			case 5:
				result = "NUMBER(9)";break;//时间区域
		}
		return result;
	}
	
	@Override
	@Transactional
	public int removeQualifierItem(Map<String,Object> paramap){
		int row = 0;
		row = tbxfPunishmentRangMapper.removeQualifierItem(paramap);
		
		//删除表TBXF_QUALIFIER_TEMP的对应字段
		String tablename = "TBXF_QUALIFIER_TEMP".toUpperCase();
		String col_name = StringNumberUtil.getStrFromMap("col_name", paramap).toUpperCase();
		
		Map<String,Object> tableColumnMap = new HashMap<String,Object>();
		tableColumnMap.put("tablename", tablename);
		tableColumnMap.put("col_name", col_name);
		commonService.alterTableDropColumn(tableColumnMap);
		
		return row;
	}
}
