package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbxfPunishmentrangMapper;
import com.sinog2c.service.api.system.ParoleReferenceService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * @describe:操作幅度参照信息的service的实现类
 * @author YangZR
 * @date 2014-7-25 上午09:30:18
 */
@Service("paroleReferenceService")
public class ParoleReferenceServiceImpl extends ServiceImplBase implements ParoleReferenceService{
	
	@Autowired
	private TbxfPunishmentrangMapper punishmentrangMapper;

	
	
	@Override
	public Integer countAllSql() {
		String sql = "select count(*) from TBXF_PUNISHMENTRANG";
		return punishmentrangMapper.countBySql(sql);
	}

	@Override
	public Integer countSearchSql(String key) {
		String sql = "select count(t.*) from TBXF_PUNISHMENTRANG t";
		if(StringNumberUtil.notEmpty(key)){
			sql += " where t.PUNID like '%"+key+"%'";
		}
		return punishmentrangMapper.countBySql(sql);
	}

	@Override
	public List<Map> searchAllByPageSql(int pageIndex, int pageSize) {
		String sql = "select * from (select a.*,rownum rn  from(select t.* from TBXF_PUNISHMENTRANG t order by t.PUNID) a where rownum <="+pageSize+") " +
						  "where rn >="+pageSize;
		return punishmentrangMapper.searchBySql(sql);
	}

	@Override
	public List<Map> searchByPageSql(int pageIndex, int pageSize, String key) {
		String sql="";
		if(StringNumberUtil.notEmpty(key)){
			sql = "select * from (" +
			"select a.*,rownum rn from("+
			"select t.* from TBXF_PUNISHMENTRANG t where t.PUNID like '%"+key+"%' order by t.PUNID"+
			") a where rownum <="+pageSize+") where rn >="+pageIndex;
		}else{
			sql = "select * from (" +
			"select a.*,rownum rn from("+
			"select t.* from TBXF_PUNISHMENTRANG t  order by t.PUNID"+
			") a where rownum <="+pageSize+") where rn >="+pageIndex;
		}
		return punishmentrangMapper.searchBySql(sql);
	}
	
}
