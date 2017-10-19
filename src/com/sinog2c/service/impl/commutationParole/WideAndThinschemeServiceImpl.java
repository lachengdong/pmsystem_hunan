package com.sinog2c.service.impl.commutationParole;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.commutationParole.TbxfWideandthinschemeMapper;
import com.sinog2c.model.commutationParole.TbxfWideandthinscheme;
import com.sinog2c.service.api.commutationParole.WideAndThinschemeService;

@Service("wideAndThinschemeService")
public class WideAndThinschemeServiceImpl implements  WideAndThinschemeService{
	@Resource
	private TbxfWideandthinschemeMapper tbxfWideandthinschemeMapper;
	
	@Override
	public int insertWideAndThinscheme(TbxfWideandthinscheme record) {
		return tbxfWideandthinschemeMapper.insert(record);
	}
	
	@Override
	public int updateWideAndThinscheme(TbxfWideandthinscheme record) {
		return tbxfWideandthinschemeMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public TbxfWideandthinscheme getWideAndThinschemeBylssid(Integer lssid) {
		return tbxfWideandthinschemeMapper.selectByPrimaryKey(lssid);
	}
	
	@Override
	public int delWideAndThinschemeBylssid(Integer lssid) {
		return tbxfWideandthinschemeMapper.deleteByPrimaryKey(lssid);
	}
}
