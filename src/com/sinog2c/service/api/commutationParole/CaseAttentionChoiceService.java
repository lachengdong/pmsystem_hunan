package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;

import com.sinog2c.model.commutationParole.TbcourtCaseAttentionChoice;


public interface CaseAttentionChoiceService {
	
	
	public int getCount(String key);
	
	public List<HashMap> getCaseAttentionChoiceList(String key, String sortField, String sortOrder, int start, int end );
	
	public int deleteCaseAttentionChoice(Integer cacid);
	
	public TbcourtCaseAttentionChoice getCaseAttentionChoiceById(Integer cacid);
	
	public int insertCaseAttentionChoice(TbcourtCaseAttentionChoice tbcourtCaseAttentionChoice);
	
	public int updateCaseAttentionChoice(TbcourtCaseAttentionChoice tbcourtCaseAttentionChoice);
	
	public List<HashMap<String, String>> ajaxGetCodeData(String codeType);
	
	public List<HashMap<String, String>> ajaxGetDepartData(String unitLevel,String topOrgid);
	
	public String getNameByCodeId(String codeType, String codeId);
	
	public String getNameByOrgId(String orgId);
	
	public String getNameBySenId(String senId);
	
}
