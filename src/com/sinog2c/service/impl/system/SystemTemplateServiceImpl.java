package com.sinog2c.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sinog2c.dao.api.system.SystemDictionaryTemplateMapper;
import com.sinog2c.dao.api.system.SystemTemplateMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @describe:操作幅度参照信息的service的实现类
 * @author YangZR
 * @date 2014-7-25 上午09:30:18
 */
@Service("systemTemplateService")
public class SystemTemplateServiceImpl extends ServiceImplBase implements
		SystemTemplateService {

	@Autowired
	private SystemTemplateMapper systemTemplateMapper;
	@Autowired
	private SystemDictionaryTemplateMapper systemDictionaryTemplateMapper;
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;

	public SystemTemplateMapper getSystemTemplateMapper() {
		return systemTemplateMapper;
	}

	public void setSystemTemplateMapper(
			SystemTemplateMapper systemTemplateMapper) {
		this.systemTemplateMapper = systemTemplateMapper;
	}

	@Override
	public int countAllByCondition(Map<String, Object> map) {
		return this.systemTemplateMapper.countAllByCondition(map);
	}

	@Override
	public int deleteByTempid(String tempid, String departid) {
		int rows = 0;
		rows = systemTemplateMapper.deleteByTempid(tempid, departid);
		if (rows > 0) {
			rows = systemDictionaryTemplateMapper.deleteByTempid(tempid);
		}

		return rows;
	}

	@Override
	@Transactional
	public int insert(SystemTemplate record) {
		return this.systemTemplateMapper.insert(record);
	}

	@Override
	public List<SystemTemplate> selectAllByCondition(Map<String, Object> map) {
		return this.systemTemplateMapper.selectAllByCondition(map);
	}

	@Override
	@Transactional
	public int update(SystemTemplate record) {
		return this.systemTemplateMapper.update(record);
	}

	@Override
	public SystemTemplate selectByTempid(String tempid, String departid) {
		return this.systemTemplateMapper.selectByTempid(tempid, departid);
	}

	@Override
	public String getSysTemplateInfoByTempid(String tempid, String departid) {
		return this.systemTemplateMapper.selectByTempid(tempid, departid)
				.getContent();
	}

	@Override
	public SystemTemplate getSystemTemplateByCondition(String tempid,
			String departid) {
		return this.systemTemplateMapper.selectByTempid(tempid, departid);
	}

	@Override
	public List<SystemTemplate> selectAllByDepartid(String departid) {
		return this.systemTemplateMapper.selectAllByDepartid(departid);
	}

	@Override
	public List<Map> getTemplateListByTempids(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(systemTemplateMapper
				.getTemplateListByTempids(map));
	}

	@Override
	public int validateTempidByDepartid(String tempid, String departid) {
		return this.systemTemplateMapper.validateTempidByDepartid(tempid,
				departid);
	}

	@Override
	@Transactional
	public int insertSelective(SystemTemplate record) {
		return this.systemTemplateMapper.insertSelective(record);
	}

	@Override
	public int updateUneditedfields(SystemTemplate record) {
		return this.systemTemplateMapper.updateUneditedfields(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List listByTempid(SystemUser user, HttpServletRequest request) {
		List list = new ArrayList();
		Map map = new HashMap();
		String departid = user.getDepartid();
		String tempid = request.getParameter("winid");
		String crimid = request.getParameter("crimid");
		SystemTemplate template = systemTemplateMapper.selectByTempid(tempid,
				departid);
		if (template != null) {
			String content = template.getContent();
			if (content != null) {
				// 替换内容
				if (content.contains("[") && content.contains("]")) {
					String findid = String.valueOf(template.getFindid());
					String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
					map.put("crimid", crimid);
					infosql = this.whereSql(user, infosql, map);
					if (StringNumberUtil.notEmpty(infosql)) {
						Map tempMap = new HashMap();
						tempMap.put("sql", infosql.replace("CHR(10)", "\r\n"));// sql中换行用\r\n
						List<Map<String, Object>> datalist = tbsysTemplateMapper
								.getDocumentContentList(tempMap);
						if (null != datalist && datalist.size() > 0) {
							content = MapUtil.replaceBracketContent(content,
									datalist.get(0));
							content = content.replaceAll("\"", "＂");// 把双引号替换成全角的双引号
						}
					}
				}
				// if(content.contains("#")){
				content = StringNumberUtil.returnString(content);
				String[] arry = content.split("#");
				for (int i = 0; i < arry.length; i++) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("id", i);
					jsonobj.put("text", arry[i]);
					list.add(jsonobj);
				}
				// }
			}
		}
		return list;
	}

	/**
	 * 方法描述：根据查询方案中 不同需要替换的字段 内容 进行替换即可
	 * 
	 * @author mushuhong
	 * @version 2014年8月21日13:55:17
	 * @param 当前登录用户
	 * @param request
	 */
	public String whereSql(SystemUser user, String plansql, Map map) {
		String whereSql = "";
		// 禁止 加 重复的内置变量
		try {
			whereSql = plansql;
			if (plansql != null && !"".equals(plansql)) {
				String departid = user.getDepartid();// 当前单位编号
				String orgid = user.getOrganization().getOrgid();// 当前部门编号
				String depart = user.getOrganization().getFullname();// 单位全称
				String intermediatecourt = user.getOrganization()
						.getIntermediatecourt();// 中院
				String highcourt = user.getOrganization().getHighcourt();// 高院
				String crimid = (String) map.get("crimid");// 当前罪犯编号
				if (plansql.contains("@crimid")) {
					if (crimid != null)
						whereSql = whereSql.replaceAll("@crimid", "'" + crimid
								+ "'");
				}
				if (plansql.contains("@sysdep")) {
					whereSql = whereSql.replaceAll("@sysdep", "'" + departid
							+ "'");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return whereSql;
	}

	/**
	 * @author 张永有
	 * @version 2014年12月19日13:55:17
	 */
	@Override
	public String findTemplate(String s) {
		return systemTemplateMapper.findTemplate(s);
	}

	@Override
	public List<Map<String, Object>> getCourtSystemModelTree(Map<String,Object> map) {
		return  MapUtil.convertKeyList2LowerCase(systemTemplateMapper.getCourtSystemModelTree(map));
	}
	
	//start add by blue_lv 2015-07-15
	@Override
	public JSONMessage<TbsysTemplate> getTemplateList(Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());		
		if (rowCount < 0) {
			rowCount = tbsysTemplateMapper.getCount(map);
		}
		List<TbsysTemplate> list = tbsysTemplateMapper.getTemplateList(map);

		JSONMessage<TbsysTemplate> result = new JSONMessage<TbsysTemplate>(
				list, rowCount);
		return result;
	}

	@Override
	public int insert(TbsysTemplate systemTemplate) {
		return this.tbsysTemplateMapper.insertSelective1(systemTemplate);
	}

	@Override
	public TbsysTemplate selectByTempid1(String tempid, String departid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tempid", tempid);
		return this.tbsysTemplateMapper.getTemplateDetail(map);
	}

	@Override
	public int update(TbsysTemplate templet) {
		return this.tbsysTemplateMapper.updateTemplateDetail(templet);
	}

	@Override
	public List<TbsysTemplate> listAllByType(String type) {
		return tbsysTemplateMapper.listAllByType(type);
	}

	@Override
	public int updateByPrimaryKeySelective(TbsysTemplate data) {
		return this.tbsysTemplateMapper.updateByPrimaryKeySelective(data);
	}

	@Override
	public TbsysTemplate gettemplatebyflowid(String flowdefid, String snodeid) {
		return tbsysTemplateMapper.gettemplatebyflowid(flowdefid, snodeid);
	}
	//end add by blue_lv 2015-07-15
}
