package com.sinog2c.service.impl.commutationParole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.commutationParole.TbcourtCaseAttentionChoiceMapper;
import com.sinog2c.model.commutationParole.TbcourtCaseAttentionChoice;
import com.sinog2c.service.api.commutationParole.CaseAttentionChoiceService;


@Service("caseAttentionChoiceService")
public class CaseAttentionChoiceServiceImpl implements CaseAttentionChoiceService {
	
	@Resource
	private TbcourtCaseAttentionChoiceMapper tbcourtCaseAttentionChoiceMapper;

	@Override
	public List<HashMap> getCaseAttentionChoiceList(String key,
			String sortField, String sortOrder, int start, int end) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.getCaseAttentionChoiceList(key, sortField, sortOrder, start, end);
	}

	@Override
	public int getCount(String key) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.getCount(key);
	}

	@Override
	public int deleteCaseAttentionChoice(Integer cacid) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.deleteByPrimaryKey(cacid);
	}

	@Override
	public TbcourtCaseAttentionChoice getCaseAttentionChoiceById(Integer cacid) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.selectByPrimaryKey(cacid);
	}

	@Override
	public int insertCaseAttentionChoice(
			TbcourtCaseAttentionChoice tbcourtCaseAttentionChoice) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.insert(tbcourtCaseAttentionChoice);
	}

	@Override
	public int updateCaseAttentionChoice(
			TbcourtCaseAttentionChoice tbcourtCaseAttentionChoice) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.updateByPrimaryKey(tbcourtCaseAttentionChoice);
	}

	@Override
	public List<HashMap<String, String>> ajaxGetCodeData(String codeType) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.ajaxGetCodeData(codeType);
	}

	@Override
	public List<HashMap<String, String>> ajaxGetDepartData(String unitLevel,
			String topOrgid) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.ajaxGetDepartData(unitLevel, topOrgid);
	}

	@Override
	public String getNameByCodeId(String codeType, String codeId) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.getNameByCodeId(codeType, codeId);
	}

	@Override
	public String getNameByOrgId(String orgId) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.getNameByOrgId(orgId);
	}

	@Override
	public String getNameBySenId(String senId) {
		// TODO Auto-generated method stub
		return tbcourtCaseAttentionChoiceMapper.getNameBySenId(senId);
	}
	
	
}
