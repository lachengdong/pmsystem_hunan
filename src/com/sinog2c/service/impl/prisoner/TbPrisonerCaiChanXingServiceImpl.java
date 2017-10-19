package com.sinog2c.service.impl.prisoner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.prisoner.TbPrisonerCaiChanXingMapper;
import com.sinog2c.model.prisoner.TbPrisonerCaiChanXing;
import com.sinog2c.service.api.prisoner.TbPrisonerCaiChanXingService;
import com.sinog2c.util.common.MapUtil;

@Service("tbPrisonerCaiChanXingService")
public class TbPrisonerCaiChanXingServiceImpl implements TbPrisonerCaiChanXingService{
	
	@Resource
	private TbPrisonerCaiChanXingMapper tbPrisonerCaiChanXingMapper;
	
	@Override
	public int deleteByPrimaryKey(String crimid) {
		return tbPrisonerCaiChanXingMapper.deleteByPrimaryKey(crimid);
	}

	public int insert(TbPrisonerCaiChanXing record) {
		return tbPrisonerCaiChanXingMapper.insert(record);
	}

	public int insertSelective(Map record) {
		int result=tbPrisonerCaiChanXingMapper.insertSelective1(record);
		tbPrisonerCaiChanXingMapper.callCaichanxingProcedure(record);
		return result;
	}

	public TbPrisonerCaiChanXing selectByPrimaryKey(String crimid) {
		return tbPrisonerCaiChanXingMapper.selectByPrimaryKey(crimid);
	}

	public int updateByPrimaryKey(TbPrisonerCaiChanXing record) {
		return tbPrisonerCaiChanXingMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Map map) {
		tbPrisonerCaiChanXingMapper.callCaichanDeleteProcedure(map);
		int result=tbPrisonerCaiChanXingMapper.updateByPrimaryKeySelective(map);
		tbPrisonerCaiChanXingMapper.callCaichanxingProcedure(map);
		return result;
	}

	public List<Map>  getCaiChanByCrimid(Map map){
		List<Map> result=tbPrisonerCaiChanXingMapper.getCaiChanByCrimid(map);
		return MapUtil.turnKeyToLowerCaseOfList(result);
	}

	public int countCaiChanByCrimid(Map map) {
		return tbPrisonerCaiChanXingMapper.countCaiChanByCrimid(map);
	}
	
	@Override
	public int deleteByCrimidAndZhiDate(Map map) {
		tbPrisonerCaiChanXingMapper.callCaichanDeleteProcedure(map);
		return tbPrisonerCaiChanXingMapper.deleteByCrimidAndZhiDate(map);
	}
	
	@Override
	public TbPrisonerCaiChanXing getByCrimidAndDate(Map map) {
		return tbPrisonerCaiChanXingMapper.getByCrimidAndDate(map);
	}

	@Override
	public int insertSelective(TbPrisonerCaiChanXing record) {
		// TODO Auto-generated method stub
		return tbPrisonerCaiChanXingMapper.insertSelective(record);
	}
}
