package com.sinog2c.service.impl.prisoner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerAccompliceMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerCrimenameMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerFeaturesMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerPrepunishmentHisMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerPrepunishmentMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerResumHisMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerResumeMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerSocialRelationMapper;
import com.sinog2c.dao.api.prisoner.TbprisonersocialRelationHisMapper;
import com.sinog2c.dao.api.prisoner.ZpublicDaMtxxMapper;
import com.sinog2c.dao.api.system.SystemResourceMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerAccompliceKey;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerCrimename;
import com.sinog2c.model.prisoner.TbprisonerFeatures;
import com.sinog2c.model.prisoner.TbprisonerPrepunishment;
import com.sinog2c.model.prisoner.TbprisonerPrepunishmentHis;
import com.sinog2c.model.prisoner.TbprisonerResumHis;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.prisoner.TbprisonersocialRelationHis;
import com.sinog2c.model.prisoner.ZpublicDaMtxx;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 类描述：和罪犯相关的表的操作
 * @author kexz
 * 2014-7-24
 */
@Service("prisonerservice")
public class PrisonerServiceImpl extends ServiceImplBase implements PrisonerService{
	/**
	 * 罪犯基本信息表相关操作
	 */
	@Autowired
	private TbprisonerBaseinfoMapper tbprisonerBaseinfoMapper;
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Autowired
	private ZpublicDaMtxxMapper zpublicDaMtxxMapper;
	@Autowired
	private TbprisonerBaseCrimeMapper tbprisonerBaseCrimeMapper;
	@Autowired
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;
	//基本信息列表list

	@Override
	public List<Map<String,Object>> getBasicInfoList(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.findByCondition(map);
	}
	//根据map传参查询数据返回总数
	public int countAllByCondition(Map<String, Object> map){
		return tbprisonerBaseinfoMapper.countAllByCondition(map);
	}
	//根据cirmid 查询罪犯基本信息
	@Override
	public TbprisonerBaseinfo getBasicInfoByCrimid(String crimid) {
		return tbprisonerBaseinfoMapper.selectByPrimaryKey(crimid);
	}
	@SuppressWarnings("all")
	public Map queryChengPiBiaoContent(HttpServletRequest request,
			SystemUser user) {
		List<Map> listMaps = new ArrayList<Map>();
		Map map = new HashMap();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");//条件检索
			
			String departid= user.getDepartid();//单位编号
			String orgid = user.getOrgid();//部门编号
			String userid = user.getUserid();//用户编号
			String pageIndex=request.getParameter("pageIndex");//当前页
			String pageSize = request.getParameter("pageSize");//每页显示多少条
			int pageindex = Integer.parseInt(pageIndex);
			int pagesize = Integer.parseInt(pageSize);
			
			int start = pageindex * pagesize;
			int end = (pageindex+1)*pagesize;
			
			map.put("departid", departid);
			map.put("orgid", orgid);
			map.put("start", start);
			map.put("end", end);
			map.put("userid", userid);
			map.put("key", key);
			
			listMaps = tbprisonerBaseCrimeMapper.queryChengPiBiaoContentMapper(map);
			
			int count = tbprisonerBaseCrimeMapper.queryChengPiBiaoContentCount(map);
			
			//数据封装到 map集合中
			map.clear();
			map.put("total", count);
			map.put("data", MapUtil.turnKeyToLowerCaseOfList(listMaps));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 除去监区以上的流程
	 * @author:mushuhong
	 * @version：2015年1月9日17:36:18
	 */
	@SuppressWarnings("all")
	public Map queryChengPiBiaoContent_yzk(HttpServletRequest request,
			SystemUser user) {
		List<Map> listMaps = new ArrayList<Map>();
		Map map = new HashMap();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");//模糊查询条件
			String orgidKey = request.getParameter("jianqu")==null?"":request.getParameter("jianqu");//部门查询条件
			
			String departid= user.getDepartid();//单位编号
			String orgid = user.getOrgid();//部门编号
			String userid = user.getUserid();//用户编号
			String pageIndex=request.getParameter("pageIndex");//当前页
			String pageSize = request.getParameter("pageSize");//每页显示多少条
			int pageindex = Integer.parseInt(pageIndex);
			int pagesize = Integer.parseInt(pageSize);
			
			int start = pageindex * pagesize;
			int end = (pageindex+1)*pagesize;
			
			map.put("departid", departid);
			if ("".equals(orgidKey)) {
				map.put("orgid", orgid);
			}else{
				map.put("orgid", orgidKey);
			}
			map.put("start", start);
			map.put("end", end);
			map.put("userid", userid);
			map.put("key", key);
			
			listMaps = tbprisonerBaseCrimeMapper.queryChengPiBiaoContentMapper_yzk(map);
			
			int count = tbprisonerBaseCrimeMapper.queryChengPiBiaoContentCount_yzk(map);
			
			//数据封装到 map集合中
			map.clear();
			map.put("total", count);
			map.put("data", MapUtil.turnKeyToLowerCaseOfList(listMaps));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@SuppressWarnings("all")
	public Map getCriminalCPBInfo(HttpServletRequest request, SystemUser user) {
		Map map = new HashMap();
		Map returnMap = new HashMap();
		try {
			String departid = user.getDepartid();
			String orgid = user.getOrgid();
			String crimid = request.getParameter("crimid");
			//查询出当前批次，一个批次只能保持一条记录
			Map batchMap = tbprisonerBaseCrimeMapper.getMaxBatch(departid);
			String curyear = batchMap.get("CURYEAR").toString();
			String batch = batchMap.get("BATCH").toString();
			map.put("departid", departid);
			map.put("orgid", orgid);
			map.put("crimid", crimid);
			map.put("curyear", curyear);
			map.put("batch", batch);
			//获取呈批表中罪犯信息
			returnMap = tbprisonerBaseCrimeMapper.getCPBCriminalInfo(map);
			if (returnMap == null) {
				//通过罪犯编号 查询 是否保存了数据
				returnMap = tbprisonerBaseCrimeMapper.getDanGeCriminalInfo(map);
			}
			//把年度和批次 传到前台页面 以供保存使用
			returnMap.put("curyear", curyear);
			returnMap.put("batch", batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MapUtil.turnKeyToLowerCase(returnMap);
	}
	@SuppressWarnings("all")
	public String saveJxJsCPBContent(HttpServletRequest request, SystemUser user) {
		String json = request.getParameter("data");
		String reportid = request.getParameter("reoprtid");
		Object object = new Object();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//查询流程的参数
		String flowdefid = request.getParameter("flowdefid");
		String menuid = request.getParameter("menuid");
		Map nodeidMap = new HashMap();
		nodeidMap.put("departid", user.getDepartid());
		nodeidMap.put("flowdefid", flowdefid);
		nodeidMap.put("resid", menuid);
		try {
			String orgid = user.getOrgid();//部门id
			List<Map> list = (List<Map>)JsonUtil.Decode(json);
			if (list != null) {
				//前台数据
				Map map = list.get(0);
				String curyear = map.get("curyear")==null?"":map.get("curyear").toString();//批次年
				String batch = map.get("batch")==null?"":map.get("batch").toString();//批次次
				String crimid = map.get("crimid")==null?"":map.get("crimid").toString();//罪犯编号
				String cbiname = map.get("cbiname")==null?"":map.get("cbiname").toString();//罪犯姓名
				String nation = map.get("nation")==null?"":map.get("nation").toString();//民族
				String casemain = map.get("casemain")==null?"":map.get("casemain").toString();//罪名
				String birthday = format.format(map.get("birthday"));//出生日期
				String yuanpanxingqi = map.get("yuanpanxingqi").toString();//原判刑期
				String shengyuxingqi = map.get("shengyuxingqi").toString();//剩余刑期
				String jibie = map.get("jibie").toString();//级别
				String xingqibiandong = map.get("xingqibiandong").toString();//刑期变动
				String xingqiqiri = format.format(map.get("xingqiqiri"));//刑期起日
				String xingqizhiri = format.format(map.get("xingqizhiri"));//刑期之日
				String rujianshijina = format.format(map.get("rujianshijina"));//人监时间
				String jifenkaoheqizhi = map.get("jifenkaoheqizhi").toString();//计分考核
				String xingzhengjiangcheng = map.get("xingzhengjiangcheng").toString();//行政奖惩
				String caichanlvxingqingkuang = map.get("caichanlvxingqingkuang")==null?"":map.get("caichanlvxingqingkuang").toString();//财产履行情况
				String yuzhengkehefen = map.get("yuzhengkehefen").toString();//狱政考核分
				String yuzhengkekaohe = map.get("yuzhengkekaohe").toString();//狱政审核
				String remark = map.get("remark")==null?"":map.get("remark").toString();//备注
				String gender = map.get("gender").toString();//性别
				String flowdraftid = map.get("flowdraftid")==null?"":map.get("flowdraftid").toString();//流程草稿id
				Map parameterMap = new HashMap();
				parameterMap.put("crimid", crimid);
				parameterMap.put("curyear", curyear);
				parameterMap.put("batch", batch);
				parameterMap.put("orgid", orgid);
				parameterMap.put("reportid", reportid);
				//判断是更新 还是新增
				Map judgeMap = tbprisonerBaseCrimeMapper.judgeJxJsCPbInsertOrUpdate(parameterMap);
				//执行 更新 else 执行新增
				parameterMap.put("cbiname", cbiname);
				parameterMap.put("nation", nation);
				parameterMap.put("casemain", casemain);
				parameterMap.put("birthday", birthday);
				parameterMap.put("yuanpanxingqi", yuanpanxingqi);
				parameterMap.put("shengyuxingqi", shengyuxingqi);
				parameterMap.put("jibie", jibie);
				parameterMap.put("xingqibiandong", xingqibiandong);
				parameterMap.put("xingqiqiri", xingqiqiri);
				parameterMap.put("xingqizhiri", xingqizhiri);
				parameterMap.put("rujianshijina", rujianshijina);
				parameterMap.put("jifenkaoheqizhi", jifenkaoheqizhi);
				parameterMap.put("xingzhengjiangcheng", xingzhengjiangcheng);
				parameterMap.put("caichanlvxingqingkuang", caichanlvxingqingkuang);
				parameterMap.put("yuzhengkehefen", yuzhengkehefen);
				parameterMap.put("yuzhengkekaohe", yuzhengkekaohe);
				parameterMap.put("remark", remark);
				parameterMap.put("gender", gender);
				parameterMap.put("flowdraftid", flowdraftid);
				if (judgeMap != null) {
					object=tbprisonerBaseCrimeMapper.jxjsCPBUpdate(parameterMap);
					object = "update";
				}else {
					//获取第一个流程
					Map mapNodeids = tbprisonerBaseCrimeMapper.queryDeliverByResidAndFlowdefid(nodeidMap);
					parameterMap.put("liucheng", mapNodeids.get("SNODEID"));
					object=tbprisonerBaseCrimeMapper.jxjsCPBInsert(parameterMap);
					object = "insert";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object.toString();
	}
	@SuppressWarnings("all")
	public String submitJxJsCPBCaseInfo(HttpServletRequest request,SystemUser user) {
		String crimid = request.getParameter("crimids");//提交罪犯集合
		String menuid = request.getParameter("menuid");//菜单id
        String flowdefid = request.getParameter("flowdefid");//流程自定义id
		String submitorback = request.getParameter("caozuostates");
        String success = "";
		int count = 0;
		Map map = new HashMap();//存放参数
		map.put("departid", user.getDepartid());
		map.put("flowdefid", flowdefid);
		map.put("resid", menuid);
		map.put("submitorback", submitorback);
		//查询对应的流程
		map = tbprisonerBaseCrimeMapper.queryDeliverByResidAndFlowdefid(map);
		String snodeid = "";
		//根据 操作的状态，取目标节点的值（这个判断用在之前未设计好的流程中，设计好的其实不怎么需要这个判断）
		if ("submit".equals(submitorback)) {
			snodeid = map.get("DNODEID").toString();
		}else if ("back".equals(submitorback)) {
			snodeid = map.get("DNODEID").toString();
		}
		//查询出最大的批次
		Map batchMap = tbprisonerBaseCrimeMapper.getMaxBatch(user.getDepartid());
		String curyear = batchMap.get("CURYEAR").toString();
		String batch = batchMap.get("BATCH").toString();
		//更新参数
		Map map2 = new HashMap();
		map2.put("curyear", curyear);
		map2.put("batch", batch);
		map2.put("reportid", "jy_jxjs_cpb");
		map2.put("snodeid", snodeid);
		try {
			//通过资源id查询出 对应的 流程内容
			String[] crimids = crimid.split(",");
			if (crimids.length > 0) {
				//如果有选择的罪犯那么 可以直接进行提交
				for (int i = 0; i < crimids.length; i++) {
					//循环更新 流程状态
					map2.put("crimid", crimids[i]);
					count += tbprisonerBaseCrimeMapper.updateLiuChengByCrimid(map2);
				}
				if (count == crimids.length) {
					success = "success";
				}else{
					success = "error";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	//根据crimid查询罪犯信息
	public Map getMySelfInfoByCrimid(String crimid){
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseinfoMapper.selectMySelfInfoByCrimid(crimid));
	}
	//更新罪犯基本信息，如果传人参数没值不更新该字段
	@Override
	public int updateByPrimaryKeySelective(TbprisonerBaseinfo baseinfo) {
		return tbprisonerBaseinfoMapper.updateByPrimaryKeySelective(baseinfo);
	}
	//新增罪犯的基本信息
	@Override
	@Transactional
	public int insertSelective(TbprisonerBaseinfo record) {
		return tbprisonerBaseinfoMapper.insertSelective(record);
	}
	//删除基本信息
	@Override
	public int deleteByPrimaryKey(String crimid) {
		return tbprisonerBaseinfoMapper.deleteByPrimaryKey(crimid);
	}
	//方法描述：得到罪犯编号
	@Override 
	public String getCrimid(String departid){
		return tbprisonerBaseinfoMapper.getCrimid(departid);
	}
	@Override 
	public int checkCrimidAndFileno(Map<String,Object> map){
		return tbprisonerBaseinfoMapper.checkCrimidAndFileno(map);
	}
	@Override
	public List<TbprisonerBaseinfo> selectAllByDepartid(String departid) {
		return tbprisonerBaseinfoMapper.selectAllByDepartid(departid);
	}
	@Override
	public void updateByCrimid(Map<String,Object> crimid) {
		 tbprisonerBaseinfoMapper.updateByCrimid(crimid);
	}
	@Override
	public List<TbprisonerBaseinfo> getCrimWithArchs(Map<Object, Object> map) {
		return tbprisonerBaseinfoMapper.getCrimWithArchs(map);
	}
	@Override
	public int countGetCrimWithArchs(Map<Object, Object> map) {
		return tbprisonerBaseinfoMapper.countGetCrimWithArchs(map);
	}
	@Override
	public String selectPinYinByCrimid(String crimid) {
		return tbprisonerBaseinfoMapper.selectPinYinByCrimid(crimid);
	}


	/**
	 * 罪犯惩罚信息表相关操作
	 */
	@Override
	public TbprisonerBaseCrime getCrimeByCrimid(String crimid) {
		return tbprisonerBaseCrimeMapper.selectPrisonerById(crimid);
	}
	//犯人犯罪处罚信息表查询原判信息
	@Override
	public TbprisonerBaseCrime getCrimeByByFileno(String fileno) {
		return tbprisonerBaseCrimeMapper.getCrimeByByFileno(fileno);
	}
	//新增犯人犯罪处罚信息表
	@Override
	public int insertTbprisonerBaseCrime(TbprisonerBaseCrime record) {
		return tbprisonerBaseCrimeMapper.insertTbprisonerBaseCrime(record);
	}
	//修改犯人犯罪处罚信息表
	@Override
	public int updateTbprisonerBaseCrime(TbprisonerBaseCrime crime) {
		return tbprisonerBaseCrimeMapper.updateByPrimaryKeySelective(crime);
	}
	//修改刑期变动信息表
	@Override
	public int updateTbxfSentenceAlteration(TbxfSentenceAlteration sentence) {
		return tbprisonerBaseCrimeMapper.updateByPrimaryKeySelect(sentence);
	}
	//删除
	@Override
	public int deleteByCrimid(String crimid) {
		return tbprisonerBaseCrimeMapper.deleteByCrimid(crimid);
	}
	
	@Override
	public int updateBaseCrimeByCrimid(Map<String,Object> baseCrimeMap) {
		return tbprisonerBaseCrimeMapper.updateBaseCrimeByCrimid(baseCrimeMap);
	}
	public List<TbprisonerBaseCrime> getPrisonerByOrgid(String orgid) {
		return tbprisonerBaseCrimeMapper.getPrisonerByOrgid(orgid);
	}
	
	@Override
	public Map getJailOutPrisonFormData(String crimid){
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseCrimeMapper.getJailOutPrisonFormData(crimid));
	}
	
	@Override
	public Map getProvinceOutPrisonFormData(String crimid){
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseCrimeMapper.getProvinceOutPrisonFormData(crimid));
	}
	
	
	/**
	 * 罪犯社会关系表相关操作
	 */
	@Override
	public String getSrid() {
		return tbprisonerSocialRelation.getSrid();
	}
	@Autowired
	private TbprisonerSocialRelationMapper tbprisonerSocialRelation;
	//根据罪犯id,是否祝联系人查询主联系人
	@Override
	public TbprisonerSocialRelation findByCrimidRelation(String crimid,String isprimarycontact) {
		return tbprisonerSocialRelation.findBycrimid(crimid,isprimarycontact);
	}
	//根据罪犯id返回罪犯社会关系
	@Override
	public List<TbprisonerSocialRelation> findRelationBycrimid(String crimid) {
		return tbprisonerSocialRelation.findRelationBycrimid(crimid);
	}
	//新增罪犯社会关系  
	@Override
	public int insertTbprisonerSocialRelation(TbprisonerSocialRelation record) {
		return tbprisonerSocialRelation.insertSelective(record);
	}
	//修改罪犯社会关系  
	@Override
	public int updateTbprisonerSocialRelation(TbprisonerSocialRelation record) {
		return tbprisonerSocialRelation.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deleteTbprisonerSocialRelation(Integer record) {
		return tbprisonerSocialRelation.deleteByPrimaryKey(record);
	}
	/**
	 * 罪犯社会关系历史表的操作
	 */
	@Autowired
	private TbprisonersocialRelationHisMapper tbprisonersocialRelationHis;
	//根据罪犯id返回罪犯社会关系历史
	@Override
	public TbprisonersocialRelationHis findByCrimidRelationHis(String crimid) {
		return tbprisonersocialRelationHis.findBycrimid(crimid);
	}
	
	
	
	
	
	

	/**
	 * 罪犯简历表相关操作
	 */
	@Override
	public String getResumeid() {
		return tbprisonerResume.getResumeid();
	}
	@Autowired
	private TbprisonerResumeMapper tbprisonerResume;
	//根据罪犯id返回罪犯简历
	@Override
	public List<TbprisonerResume> findByCrimidResume(String crimid) {
		return tbprisonerResume.findBycrimid(crimid);
	}
	
	@Override
	public List<Map> getJianXingBaseInfo(String crimid) {
		return tbprisonerResume.getJianXingBaseInfo(crimid);
	}
	
	@Override
	public List<Map<String,Object>> getCriminalInfo(String crimid) {
		return tbprisonerResume.getCriminalInfo(crimid);
	}
	
	//新增罪犯简历 
	@Override
	public int insertCrimidResume(TbprisonerResume record) {
		return tbprisonerResume.insertSelective(record);
	}
	//修改罪犯简历
	@Override
	public int updateCrimidResume(TbprisonerResume record) {
		return tbprisonerResume.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteCrimidResume(int resumeid){
		return tbprisonerResume.deleteByPrimaryKey(resumeid);
	}
	/**
	 * 罪犯简历历史相关操作
	 */
	@Autowired
	private TbprisonerResumHisMapper  tbprisonerResumHis;
	//根据罪犯id返回罪犯简历历史
	@Override
	public TbprisonerResumHis findByCrimidResumHis(String crimid) {
		return tbprisonerResumHis.findBycrimid(crimid);
	}

	
	

	
	/**
	 *   既往治安处罚信息表
	 */
	@Autowired
	private TbprisonerPrepunishmentMapper  tbprisonerPrepunishmentMapper;
	@Override
	public TbprisonerPrepunishment selectBycrimid(String crimid) {
		return tbprisonerPrepunishmentMapper.selectByCrimid(crimid);
	}
	/**
	 *既往治安处罚信息历史相关操作
	 */
	@Autowired
	private TbprisonerPrepunishmentHisMapper tbprisonerPrepunishmentHis;
	//既往治安处罚信息历史表的操作
	@Override
	public TbprisonerPrepunishmentHis findBycrimidPrepunishmentHis(String crimid) {
		return tbprisonerPrepunishmentHis.findBycrimid(crimid);
	}
	//新增
	@Override
	public int insertPrepunishmentHis(TbprisonerPrepunishmentHis record) {
		return tbprisonerPrepunishmentHis.insert(record);
	}
	
	
	
	
	
	/**
	 * 犯人犯罪名称表相关操作
	 */
	@Autowired
	private TbprisonerCrimenameMapper tbprisonerCrimenameMapper;
	@Override
	public TbprisonerCrimename selectByPrimaryKey(String crimid) {
		return tbprisonerCrimenameMapper.selectByPrimaryKey(crimid);
	}
	
	
	
	
	
	
	/**
	 * 同案犯信息表相关操作
	 */
	@Autowired
	private TbprisonerAccompliceMapper tbprisonerAccompliceMapper;
	//根据罪犯编号查询同案犯信息
	@Override
	public List<TbprisonerAccomplice> selectAccompliceByCrimid(String crimid) {
		return tbprisonerAccompliceMapper.selectAccompliceByCrimid(crimid);
	}
	//新增罪犯社会关系  
	public int insertTbprisonerAccomplice(TbprisonerAccomplice record){
		return tbprisonerAccompliceMapper.insertSelective(record);
	}
	//修改罪犯社会关系  
	public int updateTbprisonerAccomplice(TbprisonerAccomplice record){
		return tbprisonerAccompliceMapper.updateByPrimaryKeySelective(record);
	}
	
	public int deleteTbprisonerAccomplice(TbprisonerAccompliceKey key){
		return tbprisonerAccompliceMapper.deleteByPrimaryKey(key);
	}
	
	
 	public List<Map> getCrimeBaseInfoByCaseNo(String crimid,String caseorgshort,String caseno){
 		return MapUtil.turnKeyToLowerCaseOfList(tbprisonerAccompliceMapper.getCrimeBaseInfoByCaseNo(crimid,caseorgshort,caseno));
 	} 

	
	
	
	
	/**
	 * 其他方法
	 */
	@Override
	public String getSentencedetail(int year, int month) {
		String returnValue = "";
		if(year!=0) {
			if(year==9995) {
				returnValue = returnValue + GkzxCommon.XINGQI_WUQI_ZH;
			} else if(year==9996) {
				returnValue = returnValue + GkzxCommon.XINGQI_SIHUAN_ZH;
			} else {
				returnValue = returnValue + year + GkzxCommon.YEAR;
			}
		}
		if(month!=0) {
			returnValue = returnValue + month + GkzxCommon.MONTHS;
		}
		return returnValue;
	}
	//方法描述：根据资源Id获取罪犯处理页面的查询条件
	@Autowired
	private SystemResourceMapper systemResourceMapper;
	public String getTheQueryCondition(String resid){
		return systemResourceMapper.getTheQueryCondition(resid);
	}
	@Override
	public List<HashMap<Object,Object>> getCrimeBaseInfo(String crimid) {
		return tbprisonerBaseinfoMapper.getCrimeBaseInfo(crimid);
	}
	@Override
	public String getYuanPan(Map map) {
		return tbprisonerBaseinfoMapper.getYuanPan(map);
	}
	@Override
	public String getZhendInfo(Map map) {
		return tbprisonerBaseinfoMapper.getZhendInfo(map);
	}
	@Override
	public String getbsjdyjByCrimid(Map<String,Object> map) {
		return tbprisonerBaseinfoMapper.getbsjdyjByCrimid(map);
	}
	@Override
	public int deleRelationByCrimid(String str) {
		return tbprisonerBaseinfoMapper.deleRelationByCrimid(str);
	}
	@Override
	public int deleteAccompliceByCrimid(String str) {
		return tbprisonerBaseinfoMapper.deleteAccompliceByCrimid(str);
	}

	@Override
	@SuppressWarnings("all")
	public Object getCodeNameByCodeTypeService(HttpServletRequest request) {
		List<Map> listMaps = new ArrayList<Map>();
		try {
			String state = request.getParameter("state");
			String codetype = request.getParameter("codetype");
			String codeid = request.getParameter("codeid");
			Map map = new HashMap();
			map.put("state", state);
			map.put("codetype", codetype);
			map.put("codeid", codeid);
			listMaps = this.tbprisonerBaseCrimeMapper.getCodeNameByCodeTypeService(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMaps;
	}
	@Override
	public List<Map> judgeIsExistYiShenAndErShen(HttpServletRequest request) {
		List<Map> list = new ArrayList<Map>();
		try {
			String crimid = request.getParameter("crimid");
			String category = request.getParameter("category");
			Map map = new HashMap();
			map.put("crimid", crimid);
			map.put("category", category);
			list = this.tbprisonerBaseCrimeMapper.judgeIsExistYiShenAndErShen(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@SuppressWarnings("all")
	public Map queryChangeInfo(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");//罪犯编号
		String category = request.getParameter("category");//变动类别
		String courtsanction = request.getParameter("courtsanction");//判裁日期
		List<Map> listMaps = new ArrayList<Map>();
		Map map = new HashMap();
		try {
			map.put("crimid", crimid);
			map.put("category", category);
			map.put("courtsanction", courtsanction.replace("-", ""));
			listMaps = this.tbprisonerBaseCrimeMapper.queryChangeInfo(map);
			listMaps= MapUtil.turnKeyToLowerCaseOfList(listMaps);
			map.clear();
			if (listMaps.size() > 0) {
				map = listMaps.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	@SuppressWarnings("all")
	public int saveChangeData(HttpServletRequest request) {
		String json = request.getParameter("data");
		String state = request.getParameter("state");
		String courtsanctionparameter = request.getParameter("courtsanction");
		Map map = new HashMap();
		int count = 0;
		try {
			List<Map> list = (List<Map>)JsonUtil.Decode(json);
			if (list.size() > 0) {
			    Map map2 = list.get(0);
			    String crimid = map2.get("crimid").toString();
			    String courtshort = map2.get("courtshort")== null?"":map2.get("courtshort").toString();//减刑 字
			    String courttitle = map2.get("courttitle")== null?"":map2.get("courttitle").toString();//法院名称
			    String courtyear = map2.get("courtyear")==null?"":map2.get("courtyear").toString();//年份
			    String courtname = map2.get("courtname")==null?"":map2.get("courtname").toString();//所在地区
			    String category = map2.get("category")==null?"":map2.get("category").toString();//变动类别
			    Object courtchangefrom = map2.get("courtchangefrom")==null?"":map2.get("courtchangefrom");//刑期起日
			    Object courtchangeto = map2.get("courtchangeto")==null?"":map2.get("courtchangeto");//刑期止日
			    Object exectime = map2.get("exectime")==null?"":map2.get("exectime");//执行日期
			    Object courtsanction = map2.get("courtsanction")==null?"":map2.get("courtsanction");//判裁日期
			    String courtsn = map2.get("courtsn")==null?"":map2.get("courtsn").toString();//字号
			    String courtarea = map2.get("courtarea").toString()==null?"":map2.get("courtarea").toString();//判裁地区
			    String courtchange = map2.get("courtchange")==null?"":map2.get("courtchange").toString();//刑期
			    
			    map.put("crimid", crimid);
			    map.put("courtshort", courtshort);
			    map.put("courttitle", courttitle);
			    map.put("courtyear", courtyear);
			    map.put("courtname", courtname);
			    map.put("category", category);
			    map.put("courtchangefrom", stringObject(courtchangefrom));
			    map.put("courtchangeto", stringObject(courtchangeto));
			    map.put("exectime", stringObject(exectime));
			    map.put("courtsanction", stringObject(courtsanction));
			    map.put("courtsn", courtsn);
			    map.put("courtarea", courtarea);
			    map.put("courtchange", courtchange.replace("-", ""));
			    map.put("sentence", courtchange.replace("-", ""));
			    if ("new".equals(state)) {
			    	count=this.tbprisonerBaseCrimeMapper.saveChangeData(map);
				}else if("edit".equals(state)){
					map.put("courtsanctionparameter", courtsanctionparameter);
					count=this.tbprisonerBaseCrimeMapper.updateChangeData(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 方法描述：根据罪犯编号查询出罪犯的基本信息
	 * @version：2014年12月16日09:37:18
	 * @author：mushuhong
	 */
	@Override
	public Map getCriminalInfoByCrimid(String crimid) {
		
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseCrimeMapper.getCriminalInfoByCrimid(crimid));
	}
	@Override
	public Map getBingChanJianDingContent(String crimid,String flowdefid) {
		// TODO Auto-generated method stub
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseCrimeMapper.getBingChanJianDingContent(crimid,flowdefid));
	}
	public Object stringObject(Object date){
		//格式化日期
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		if (date != null && !"".equals(date)) {
			date = format.format(date);
		}
		
		return date;
	}
	@Override
	public int queryPanCaiInfoCounts(HttpServletRequest request, SystemUser user) {
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String key = request.getParameter("key");
		int pageindex = Integer.parseInt(pageIndex);
		int pagesize = Integer.parseInt(pageSize);
		
		int start = pageindex * pagesize;
		int end = (pageindex+1)*pagesize;
		
		String departid = user.getDepartid();
		String orgid = user.getOrgid();
		//通过 部门id 查询对应的内容 的罪犯信息
		Map map = new HashMap();
		map.put("orgid", orgid);
		map.put("start", start);
		map.put("end", end);
		map.put("key", key);
		return tbprisonerBaseCrimeMapper.queryPanCaiInfoCounts(map);
	}
	/**
	 * 方法描述：通过部门id查询出对应的部门编号
	 */
	@SuppressWarnings("all")
	public List<Map> queryPanCaiInfoService(HttpServletRequest request,SystemUser user) {
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String key = request.getParameter("key");
		int pageindex = Integer.parseInt(pageIndex);
		int pagesize = Integer.parseInt(pageSize);
		
		int start = pageindex * pagesize;
		int end = (pageindex+1)*pagesize;
		
		String departid = user.getDepartid();
		String orgid = user.getOrgid();
		//通过 部门id 查询对应的内容 的罪犯信息
		Map map = new HashMap();
		map.put("orgid", orgid);
		map.put("start", start);
		map.put("end", end);
		map.put("key", key);
		map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
		return tbprisonerBaseCrimeMapper.queryPanCaiInfoService(map);
	}
	/**
	 * 方法描述：根据罪犯编号查询出对应的判决信息
	 * @version 2014年11月11日11:42:17
	 */
	public Map<String,Object> queryTbdataSentchageImpl(HttpServletRequest request) {
		Map<String,Object> list = new HashMap<String,Object>();
		String crimid = request.getParameter("crimid");
		Map parameter = new HashMap();
		try {
			parameter.put("crimid", crimid);
			//查询对应的数据
			List<Map> maps = tbprisonerBaseCrimeMapper.queryTbdataSentchageImpl(parameter);
			int count = maps.size();
			
			list.put("total", count);
			list.put("data", MapUtil.turnKeyToLowerCaseOfList(maps));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int ajaxGetPropertyPunishmentCount(Map<String,Object> map){
		return tbprisonerBaseCrimeMapper.ajaxGetPropertyPunishmentCount(map);
	};
	
	public List<Map<String,Object>> ajaxGetPropertyPunishmentChoose(Map<String,Object> map){
		return MapUtil.convertKeyList2LowerCase(tbprisonerBaseCrimeMapper.ajaxGetPropertyPunishmentChoose(map));
	};

	/**
	 * 方法描述：立案审批表显示
	 * @author zhenghui
	 * @version 2014年12月9日13:38:54
	 */
	@Override
	public Map getParoleApproveMap(String crimid,String tempid,String departid){
		Map datamap = new HashMap();
		TbsysTemplate template = new TbsysTemplate();
		List<Map> list = tbprisonerBaseinfoMapper.getParoleApproveMap(crimid);
		if(list!=null){
			for(Object obj:list){
				Map tempmap = (Map)obj;
				tempmap =MapUtil.turnKeyToLowerCase(tempmap);
				Map<String,Object> map =new HashMap<String,Object>();
				map.put("tempid", tempid);
				map.put("departid", departid);
				template = tbsysTemplateMapper.getTemplateDetail(map);
				if (template != null){
					tempmap.put("docconent", (String)template.getContent());
				}
				datamap.putAll(tempmap);
			}
		}
		
		return datamap;
	}
	
	@Override
	public ZpublicDaMtxx getHeadPicture(Map<String,Object> map) {
		return zpublicDaMtxxMapper.selectByPrimaryKey(map);
	}
	@Override
	public Map selectSentenceById(String crimid) {
		
		return tbprisonerBaseCrimeMapper.selectSentenceById(crimid);
	}
	
	@Autowired
	private TbprisonerFeaturesMapper featuresMapper;
	public TbprisonerFeatures selectTbprisonerFeatures(String crimid){
		return featuresMapper.selectByPrimaryKey(crimid);
	}
	@Override
	public Map getBaseInfoByCrimidMap(String crimid,String departid) {
		
		return tbprisonerBaseinfoMapper.getBaseInfoByCrimidMap(crimid,departid);
	}
	@Override
	public Map getProvinceOutPrisonFormData_hb(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUnitlevelByCrimidOrgid(String str) {
		// TODO Auto-generated method stub
		return tbprisonerBaseinfoMapper.getUnitlevelByCrimidOrgid(str);
	}
	@Override
	public int findCountByCrimid(String str) {
		return tbprisonerBaseinfoMapper.findCountByCrimid(str);
	}
	@Override
	public void callSentncechangByCrimid(String crimid) {
		tbprisonerBaseinfoMapper.callSentncechangByCrimid(crimid);
		
	};
	
	@Override
	public Map generatePYByCrimid(String crimid) {
		
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseinfoMapper.generatePYByCrimidMapper(crimid));
	
	}


	@Override
	public int updateBaseByCrimid(Map record) {
		tbprisonerBaseinfoMapper.updateBaseByCrimid(record);
		return 0;
	}
	

	@Override
	public void updateImportantPunishmentBycrimid(TbprisonerBaseCrime record) {
		tbprisonerBaseCrimeMapper.updateImportantPunishmentBycrimid(record);
		
	}
	public List<Map> querySocialRelation(String crimid){
		
		return tbprisonerBaseinfoMapper.querySocialRelationMapper(crimid);
	}
	public List<Map> selectByCardOrCrimid(Map<String,Object> paramMap){
		
		return MapUtil.turnKeyToLowerCaseOfList(tbprisonerBaseinfoMapper.selectByCardOrCrimid(paramMap));
	}
	@Override
	public Map getCurrentBatch(String departid) {
		// TODO Auto-generated method stub
		return MapUtil.turnKeyToLowerCase(tbprisonerBaseinfoMapper.getCurrentBatch(departid));
	}
	
	@Override
	@Transactional
	public Object saveThreeKindCrimeIdentify(Map<String,Object> map, SystemUser user){
		String crimids = map.get("crimids").toString()==null?"":map.get("crimids").toString().toString();
		String sanclassstatus = map.get("sanclassstatus")==null?"":map.get("sanclassstatus").toString();
		Map<String,Object> paramap = new HashMap<String,Object>();
		if(StringNumberUtil.notEmpty(sanclassstatus)){
			String sanremark = "";
			//TBPRISONER_BASE_CRIME
			paramap.put("sanclassstatus", "1");//三类犯状态值:初始化0,解锁1,加锁2,与三类犯无关为空
			if(sanclassstatus.indexOf("1") > -1){
				paramap.put("undermine", "1"); //破坏金融管理秩序和金融诈骗犯罪
				sanremark += GkzxCommon.JRFZ + ",";
			}else{
				paramap.put("undermine", null); 
			}
			
			if(sanclassstatus.indexOf("2") > -1){
				paramap.put("underworld", "1");//四涉涉黑
				sanremark += GkzxCommon.SHFZ + ",";
			}else{
				paramap.put("underworld", null);//四涉涉黑
			}
			
			if(sanclassstatus.indexOf("3") > -1){
				paramap.put("postcrime", "1");//职务犯罪
				sanremark += GkzxCommon.ZWFZ + ",";
			}else{
				paramap.put("postcrime", null);//职务犯罪
			}
			
			sanremark = StringNumberUtil.removeLastStr(sanremark, ",");
			paramap.put("sanremark", sanremark); //三类犯备注
			//TBXF_SENTENCEALTERATION
			paramap.put("crimclass", "1"); //法院案犯类别0普通犯1三类犯
		}else{
			//TBPRISONER_BASE_CRIME
			paramap.put("sanclassstatus", null);//三类犯状态值:初始化0,解锁1,加锁2,与三类犯无关为空
			paramap.put("underworld", null);//四涉涉黑
			paramap.put("postcrime", null);//职务犯罪
			paramap.put("undermine", null); //破坏金融管理秩序和金融诈骗犯罪
			paramap.put("sanremark", null); //三类犯备注
			//TBXF_SENTENCEALTERATION
			paramap.put("crimclass", null); //法院案犯类别0普通犯1三类犯
		}
		
		
		if(StringNumberUtil.notEmpty(crimids)){
			List<String> crimidList = StringNumberUtil.formatString2List(crimids, ",");
			for(String crimid : crimidList){
				paramap.put("crimid", crimid);
				//操作TBPRISONER_BASE_CRIME
				tbprisonerBaseCrimeMapper.saveThreeKindInfoOfBaseCrime(paramap);
				//操作TBPRISONER_BASE_CRIME
				tbxfSentenceAlterationMapper.saveThreeKindInfoOfSentence(paramap);
			}
			return "success";
		}
		return null;
	}
	@Override
	public void updatePprioveAndduty(Map map) {
		
		tbprisonerBaseCrimeMapper.updatePprioveAndduty(map);
	}
	
	//新增犯人犯罪处罚信息表
	@Override
	public int insertTbprisonerBaseCrime(Map<String, Object> record) {
		return tbprisonerBaseCrimeMapper.insertTbprisonerBaseCrimeMap(record);
	}
	
	@Override
	public int getNextID(){
		return zpublicDaMtxxMapper.gettNextID();
	}
	
	//插入罪犯照片
	@Override
	public int insertPrisonerPicture(ZpublicDaMtxx record) {
		return zpublicDaMtxxMapper.insertSelective(record);
	}
	
	@Override
	public int updatePrisonerPicture(ZpublicDaMtxx record){
		return zpublicDaMtxxMapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * 更新刑期变动信息表TBXF_SENTENCEALTERATION表数据
	 * @param orgid String 组织id
	 * @param rimid String 罪犯id
	 */
	@Override
	public void callXFsentencealteration(Map map){
		tbprisonerBaseCrimeMapper.callXFsentencealteration(map);
	}
	
		
}
