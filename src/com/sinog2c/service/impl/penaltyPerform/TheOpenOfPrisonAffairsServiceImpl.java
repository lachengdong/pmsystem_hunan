package com.sinog2c.service.impl.penaltyPerform;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.penaltyPerform.TbyzPublicityHisMapper;
import com.sinog2c.dao.api.penaltyPerform.TbyzPublicityMapper;
import com.sinog2c.model.penaltyPerform.TbyzPublicity;
import com.sinog2c.model.penaltyPerform.TbyzPublicityHis;
import com.sinog2c.service.api.penaltyPerform.TheOpenOfPrisonAffairsService;
import com.sinog2c.service.impl.base.ServiceImplBase;

/**
 * 实现类描述：狱务公开
 * @author liuchaoyang
 * 下午08:43:00
 */
@Service("theOpenOfPrisonAffairsService")
public class TheOpenOfPrisonAffairsServiceImpl extends ServiceImplBase implements TheOpenOfPrisonAffairsService{
	@Autowired
	private TbyzPublicityMapper tbyzPublicityMapper;
	@Autowired
	private TbyzPublicityHisMapper tbyzPublicityHisMapper;

	/**
	 * 获取狱务公示数据列表
	 * @return
	 */
	@Override
	public List<TbyzPublicity> ajaxGetPublicityList(int pageIndex, int pageSize,
			String key,String type,String sortField,String sortOrder) {
		// TODO Auto-generated method stub
		// 查询数据最小数
		int start = pageIndex * pageSize + 1;
		// 查询数据最大数
		int end = start + pageSize - 1; 
		return tbyzPublicityMapper.ajaxGetPublicityList(start, end, key, type, sortField, sortOrder);
	}
	/**
	 * 获取狱务公示数据总数
	 */
	@Override
	public int ajaxGetPublicityCount(String key,String type) {
		// TODO Auto-generated method stub
		return tbyzPublicityMapper.ajaxGetPublicityCount(key, type);
	}
	/**
	 * 新增保存
	 */
	@Override
	public int insertPublicity(TbyzPublicity pub) {
		// TODO Auto-generated method stub
		return tbyzPublicityMapper.insertSelective(pub);
	}
	/**
	 * 修改保存
	 */
	@Override
	public int updatePublicity(TbyzPublicity pub) {
		// TODO Auto-generated method stub
		return tbyzPublicityMapper.updatePublicity(pub);
	}
	/**
	 * 获取数据详细信息
	 */
	@Override
	public TbyzPublicity ajaxGetPublicity(String pubid) {
		// TODO Auto-generated method stub
		return tbyzPublicityMapper.ajaxGetPublicity(pubid);
	}
	/**
	 * 删除数据
	 */
	@Override
	public int deletePublicity(String pubid) {
		// TODO Auto-generated method stub
		TbyzPublicity pub = tbyzPublicityMapper.ajaxGetPublicity(pubid);
		TbyzPublicityHis pubhis = new TbyzPublicityHis();
		tbyzPublicityHisMapper.insertSelective(pubhis);
		return tbyzPublicityMapper.deletePublicity(pubid);
	}
}
