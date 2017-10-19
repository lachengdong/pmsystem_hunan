package com.sinog2c.service.impl.criminalexam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.criminalexam.TbyzCheckForWorkMapper;
import com.sinog2c.model.criminalexam.TbyzCheckForWork;
import com.sinog2c.service.api.criminalexam.SxexamineCheckForWorkService;
import com.sinog2c.util.common.MapUtil;

@Service("sxexamineCheckForWorkService")
public class SxexamineCheckForWorkServiceImpl implements SxexamineCheckForWorkService {
	@Autowired
	private TbyzCheckForWorkMapper tbyzCheckForWorkMapper;

	@Override
	public List<TbyzCheckForWork> getWorkList(int pageIndex, int pageSize) {
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		List<TbyzCheckForWork> checkForWorkList = this.tbyzCheckForWorkMapper.selectCheckForWork(start, end);
		return checkForWorkList;
	}

	@Override
	public int getWorkCount(Map map) {
		return this.tbyzCheckForWorkMapper.selectCheckForWorkCount(map);
	}

	@Override
	public List<Map> getInfobyDepartAndMonth(String departid, String yeardate) {
		return MapUtil.turnKeyToLowerCaseOfList(tbyzCheckForWorkMapper.getInfobyDepartAndMonth(departid, yeardate));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getList(String orgid, String yeardate) {

		Map bean = new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> kaoQinlist = MapUtil.turnKeyToLowerCaseOfList(tbyzCheckForWorkMapper.getInfobyDepartAndMonth(orgid, yeardate));
		try {
			// 优良中差人数
			int you = 0, liang = 0, zhong = 0, cha = 0;
			// 一二三四五等人数
			int one = 0, two = 0, three = 0, four = 0, five = 0;
			
			Float allsum = 0f;//综合奖分总分
			Float worksum = 0f;//劳动得分总分
			
			for (Map map : kaoQinlist) {
				String basicLevel = map.get("jcfdjscore").toString();
				if (null != basicLevel && !"".equals(basicLevel)) {
					if (1 == Integer.parseInt(basicLevel))
						you++;
					if (2 == Integer.parseInt(basicLevel))
						liang++;
					if (3 == Integer.parseInt(basicLevel))
						zhong++;
					if (4 == Integer.parseInt(basicLevel))
						cha++;
				}
				String workLevel = map.get("lddjscore").toString();
				if (null != workLevel && !"".equals(workLevel)) {
					if (1 == Integer.parseInt(workLevel))
						one++;
					if (2 == Integer.parseInt(workLevel))
						two++;
					if (3 == Integer.parseInt(workLevel))
						three++;
					if (4 == Integer.parseInt(workLevel))
						four++;
					if (5 == Integer.parseInt(workLevel))
						five++;
				}
				int count = 0;
				for (int i = 1; i <= 31; i++) {
					if (i < 10) {
						String num = map.get("day" + "0" + i).toString();
						if ("1".equals(num))
							count++;
					} else {
						String num = map.get("day" + i).toString();
						if ("1".equals(num))
							count++;
					}
				}
				
				Map cws = new HashMap();
				cws.put("daynum", String.valueOf(count));
				cws.put("crimid", map.get("crimid").toString());
				cws.put("name", map.get("name").toString());
				cws.put("basicscore", map.get("zhjfscore") !=null && map.get("zhjfscore")!=""? map.get("zhjfscore").toString():"");
				cws.put("xzjfscore",map.get("xzjfscore") !=null && map.get("xzjfscore") !=""? map.get("xzjfscore").toString():"" );
				cws.put("xzkfscore", map.get("xzkfscore") !=null && map.get("xzkfscore") !=""? map.get("xzkfscore").toString():"");
				cws.put("ccjfscore", map.get("ccjfscore") !=null && map.get("ccjfscore") !=""? map.get("ccjfscore").toString():"");
				cws.put("ldgzscore", map.get("ldgzscore") !=null && map.get("ldgzscore") !=""? map.get("ldgzscore").toString():"");
				cws.put("zhjfscore", map.get("int1") !=null && map.get("int1") !=""? map.get("int1").toString():"");
				cws.put("khscore", map.get("khscore") !=null && map.get("khscore") !=""? map.get("khscore").toString():"");
				cws.put("remark", map.get("remark").toString());
				String int1 = map.get("jcfdjscore").toString();
				if (null != int1 && !"".equals(int1)) {
					cws.put("int1", Integer.parseInt(int1));
				}
				String int2 = map.get("lddjscore").toString();
				if (null != int2 && !"".equals(int2)) {
					cws.put("int2", Integer.parseInt(int2));
				}
				list.add(cws);

				String zhstr = map.get("int1").toString();//综合奖分
				if (null != zhstr && !"".equals(zhstr)) {
					allsum = allsum + Float.parseFloat(zhstr);//综合奖分总分
				}
				String ldsum = map.get("ldgzscore").toString();//劳动得分
				if (null != ldsum && !"".equals(ldsum)) {
					worksum = worksum + Float.parseFloat(ldsum);//劳动得分总分
				}
			}
			// 设置优良中差人数
			bean.put("yousum", you);
			bean.put("liangsum", liang);
			bean.put("zhongsum", zhong);
			bean.put("chasum", cha);
			// 设置一二三四五等人数
			bean.put("one", one);
			bean.put("two", two);
			bean.put("three", three);
			bean.put("four", four);
			bean.put("five", five);
			bean.put("list", list);
			bean.put("zhallsum", String.valueOf(allsum));
			bean.put("worksum", String.valueOf(worksum));
			bean.put("zcrs", String.valueOf(kaoQinlist.size()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return bean;
	}

	@Override
	public int updateSelective(TbyzCheckForWork record) {
		return tbyzCheckForWorkMapper.updateSelective(record);
	}

	public String workSetAddOrUpdate(Map map) {
		return this.tbyzCheckForWorkMapper.workSetAddOrUpdate(map);
	}

	public String workAddOrUpdate(Map map) {
		String str = "";
		str = this.tbyzCheckForWorkMapper.workAddOrUpdate(map);
		return str;
	}

	public List<TbyzCheckForWork> workSetSelect(Map map) {
		return this.tbyzCheckForWorkMapper.workSetSelect(map);
	}

	@Override
	public List<Map> getListMap(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(this.tbyzCheckForWorkMapper.selectCheckForWorkMap(map));
	}
	
	public String workSetDanGeUpdate(Map map) {
		return this.tbyzCheckForWorkMapper.workSetDanGeUpdate(map);
	}

	@Override
	public String workSetDanGeAdd(Map map) {
		// TODO Auto-generated method stub
		return String.valueOf(this.tbyzCheckForWorkMapper.workSetDanGeAdd(map));
	}

	@Override
	public String insertSelective(Map map) {
		// TODO Auto-generated method stub
		return String.valueOf(this.tbyzCheckForWorkMapper.insertSelective(map));
	}

	@Override
	public int updateSelective(Map map) {
		// TODO Auto-generated method stub
		return this.tbyzCheckForWorkMapper.updateSelective(map);
	}

	@Override
	public int updateWorkData(Map map) {
		// TODO Auto-generated method stub
		return this.tbyzCheckForWorkMapper.updateWorkData(map);
	}

	@Override
	public int selectByCrimid(Map map) {
		// TODO Auto-generated method stub
		return tbyzCheckForWorkMapper.selectByCrimid(map);
	}

	@Override
	public int insertWorkData(Map map) {
		// TODO Auto-generated method stub
		return tbyzCheckForWorkMapper.insertSelective(map);
	}
	
}
