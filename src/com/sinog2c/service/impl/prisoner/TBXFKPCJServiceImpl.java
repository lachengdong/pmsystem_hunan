package com.sinog2c.service.impl.prisoner;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.prisoner.TbxfKpcjMapper;
import com.sinog2c.model.prisoner.TbxfKpcj;
import com.sinog2c.service.api.prisoner.TBXFKPCJService;
@Service("TbxfKpcjService")
public class TBXFKPCJServiceImpl implements TBXFKPCJService {
	@Resource
	private TbxfKpcjMapper  TbxfKpcjMapper;
	@Override
	public int deleteByPrimaryKey(String propertyid) {
		return TbxfKpcjMapper.deleteByPrimaryKey(propertyid);
	}

	@Override
	public List findByKPCJDetail(Map map) {
		return TbxfKpcjMapper.findByKPCJDetail(map);
	}

	@Override
	public int findByKPCJDetailCount(Map map) {
		return TbxfKpcjMapper.findByKPCJDetailCount(map);
	}

	@Override
	public int insert(TbxfKpcj record) {
		return TbxfKpcjMapper.insert(record);
	}

	@Override
	public int insertSelective(TbxfKpcj record) {
		return TbxfKpcjMapper.insertSelective(record);
	}

	@Override
	public TbxfKpcj selectByPrimaryKey(String propertyid) {
		return TbxfKpcjMapper.selectByPrimaryKey(propertyid);
	}

	@Override
	public String selectKPCJMaxidByCrimid() {
		return TbxfKpcjMapper.selectKPCJMaxidByCrimid();
	}

	@Override
	public int updateByPrimaryKey(TbxfKpcj record) {
		return TbxfKpcjMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(TbxfKpcj record) {
		return TbxfKpcjMapper.updateByPrimaryKeySelective(record);
	}

}
