package com.sinog2c.service.api.prisoner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

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

/**
 * 接口描述：和罪犯相关的表的操作
 * @author kexz
 * 2014-7-24
 */
@Service("prisonerService")
public interface PrisonerService{
/**
 * 罪犯基本信息表相关操作
 */
	//根据map传参获取罪犯列表
	public List<Map<String,Object>> getBasicInfoList(Map<String, Object> map);
	/**
	 * 根据map传参查询数据返回总数
	 */
	public int countAllByCondition(Map<String, Object> map);
	
	/**
	 * 根据 crimid  查询犯人基本信息
	 */
	public TbprisonerBaseinfo getBasicInfoByCrimid(String crimid);
	/**
	 * 方法描述：修改罪犯基本信息
	 */
	public int updateByPrimaryKeySelective(TbprisonerBaseinfo baseinfo );
	/**
	 * 方法描述：得到罪犯编号
	 */
	public String getCrimid(String departid);
	public int checkCrimidAndFileno(Map<String,Object> map);
	public List<TbprisonerBaseinfo> selectAllByDepartid(String departid);
	/**
	 * 新增罪犯基本信息
	 */
	public int insertSelective(TbprisonerBaseinfo record);
	/**
	 * 删除
	 */
	public int deleteByPrimaryKey(String crimid);
	/**
	 * 基本信息修改
	 */
	public void updateByCrimid(Map<String,Object> map);
	/**
	 * 获取拥有档案的犯人信息总数
	 */
	public int countGetCrimWithArchs(Map<Object, Object> map);
	/**
	 * 获取拥有档案的犯人信息
	 */
	public List<TbprisonerBaseinfo> getCrimWithArchs(Map<Object, Object> map);
    /**
	 * 根据crimid  查询罪犯姓名拼音首字母（大写、前两位）
	 */
	public String selectPinYinByCrimid(String crimid);
	
	//获取罪犯处罚信息
	public int ajaxGetPropertyPunishmentCount(Map<String,Object> map);
	
	public List<Map<String,Object>> ajaxGetPropertyPunishmentChoose(Map<String,Object> map);
	
	
	/**
	 * 罪犯惩罚信息表相关操作
	 */
	//根据 crimid  查询犯人犯罪处罚信息  TBPRISONER_BASE_CRIME
	public TbprisonerBaseCrime getCrimeByCrimid(String crimid);
	
	/**
	 * 查询刑期变动内容
	 */
	public Map selectSentenceById(String crimid);
	
	/**
	 * 根据 档案号查询犯人犯罪处罚信息
	 */
	public TbprisonerBaseCrime getCrimeByByFileno(String fileno);
	/**
	 * 新增犯人犯罪处罚信息表
	 */
	public int insertTbprisonerBaseCrime(TbprisonerBaseCrime record);
	/**
	 * 修改犯人犯罪处罚信息表
	 */
	int updateTbprisonerBaseCrime(TbprisonerBaseCrime crime);
	/**
	 * 修改刑期变动信息表
	 */
	int updateTbxfSentenceAlteration(TbxfSentenceAlteration sentence);
	/**
	 * 方法描述：根据罪犯编号删除数据
	 */
	public int deleteByCrimid(String crimid);
	public void callSentncechangByCrimid(String crimid);
	/**
	 * updateBaseCrimeByCrimid
	 * 犯罪表修改
	 */
	public int updateBaseCrimeByCrimid(Map<String,Object> baseCrimeMap);
	
	public List<TbprisonerBaseCrime> getPrisonerByOrgid(String orgid);
	
	public Map getMySelfInfoByCrimid(String crimid);
	
	
	public Map getJailOutPrisonFormData(String crimid);
	
	/**
	 * 方法描述：查询出 监外执行 审批表页面基本信息内容显示
	 * @author：mushuhog
	 * @version :2014年12月16日09:41:42
	 * @param crimid
	 * @return
	 */
	public Map getCriminalInfoByCrimid(String crimid);
	
	/**
	 * 方法描述：查询出鉴定结果(放到审批表基本信息内容)
	 * @param crimid
	 * @return
	 */
	public Map getBingChanJianDingContent(String crimid,String flowdefid);
	
	public Map getProvinceOutPrisonFormData(String crimid);
	
	/**
	 *  既往治安处罚信息表
	 */
	//根据 crimid查询 既往治安处罚信息
	public TbprisonerPrepunishment selectBycrimid(String crimid);
	/**
	 * 既往治安处罚信息历史表的操作
	 */
	//根据罪犯id既往治安处罚信息历史
	public  TbprisonerPrepunishmentHis findBycrimidPrepunishmentHis(String crimid);
	//新增
	public int insertPrepunishmentHis(TbprisonerPrepunishmentHis record);
	
	
	
	
	/**
	 * 罪犯简历表的操作
	 */
	public String getResumeid();
	//根据罪犯id返回罪犯简历
	public List<TbprisonerResume> findByCrimidResume(String crimid);
	
	public int insertCrimidResume(TbprisonerResume resume);
	
	public int updateCrimidResume(TbprisonerResume resume);
	
	public int deleteCrimidResume(int resumeid);
	
	/**
	 * 罪犯简历历史表的操作
	 */
	//根据罪犯id返回罪犯简历历史
	public  TbprisonerResumHis findByCrimidResumHis(String crimid);
	
	
	
	
	
	/**
	 * 罪犯社会关系表
	 * @author wangxf
	 */
	public String getSrid();
	//根据罪犯id,是否祝联系人查询主联系人
	public  TbprisonerSocialRelation findByCrimidRelation(String crimid,String isprimarycontact);
	//根据罪犯id返回罪犯社会关系
	public List<TbprisonerSocialRelation> findRelationBycrimid(String crimid);
	//新增罪犯社会关系  
	public int insertTbprisonerSocialRelation(TbprisonerSocialRelation record);
	//修改罪犯社会关系  
	public int updateTbprisonerSocialRelation(TbprisonerSocialRelation record);
	
	public int deleteTbprisonerSocialRelation(Integer record);
	/**
	 * 罪犯社会关系历史表的操作
	 */
	//根据罪犯id返回罪犯社会关系历史
	public TbprisonersocialRelationHis findByCrimidRelationHis(String crimid);
	
	
	
	
	
	
	/**
	 * 犯人犯罪名称表
	 */
	//根据 crimid  查询犯人犯罪名称表
	public  TbprisonerCrimename selectByPrimaryKey(String crimid);
	
	
	
	
	
	
	
	
	
	/**
	 * 同案犯信息表
	 */
	//根据同案犯编号和罪犯编号查询同案犯信息
	public List<TbprisonerAccomplice> selectAccompliceByCrimid(String crimid);
	//新增罪犯社会关系  
	public int insertTbprisonerAccomplice(TbprisonerAccomplice record);
	//修改罪犯社会关系  
	public int updateTbprisonerAccomplice(TbprisonerAccomplice record);
	
	public int deleteTbprisonerAccomplice(TbprisonerAccompliceKey key);
	
	
	//根据判决单位、判决编号查询同案犯
	public List<Map> getCrimeBaseInfoByCaseNo(String crimid,String caseorgshort,String caseno) ;

	
	
	
	/**
	 * 其他方法
	 */
	public String getSentencedetail(int year, int month);
	
	//方法描述：根据资源Id获取罪犯处理页面的查询条件
    public String getTheQueryCondition(String resid);
	/**
	 * 根据编号获取犯人信息
	 * @param crimid
	 * @return
	 */
	public List<HashMap<Object,Object>> getCrimeBaseInfo(String crimid);
	
	/*
	 * 获取原判刑期
	 */
	public String getYuanPan(Map map);
		
	public String getZhendInfo(Map map);
	//有关流程的取意见。
	public String getbsjdyjByCrimid(Map<String,Object> map);
	
	public int deleteAccompliceByCrimid(String str);
	
	public int deleRelationByCrimid(String str);
	
	public List<Map> queryPanCaiInfoService(HttpServletRequest request,SystemUser user);
	
	public int queryPanCaiInfoCounts(HttpServletRequest request,SystemUser user);
	
	public Map<String,Object> queryTbdataSentchageImpl(HttpServletRequest request);
	
	public int saveChangeData(HttpServletRequest request);
	
	public Object getCodeNameByCodeTypeService(HttpServletRequest request);
	
	/**
	 * 方法描述：判断是否已经存在一审或者二审的数据
	 * @version 2014年11月12日14:31:06
	 */
	public List<Map> judgeIsExistYiShenAndErShen(HttpServletRequest request);
	/**
	 * 方法描述：查询出对应的数据
	 * @version 2014年11月13日13:38:54
	 */
	public Map queryChangeInfo(HttpServletRequest request);
	
	/**
	 * 方法描述：宁夏减刑假释呈批表
	 * @author mushuhong
	 * @version 2015年1月7日09:37:32
	 */
	public Map queryChengPiBiaoContent(HttpServletRequest request ,SystemUser user);
	/**
	 * 方法描述：宁夏减刑假释呈批表
	 * @author mushuhong
	 * @version 2015年1月7日09:37:32
	 */
	public Map queryChengPiBiaoContent_yzk(HttpServletRequest request ,SystemUser user);
	/**
	 * 方法描述：通过选中罪犯查询信息
	 * @author:mushuhong
	 * @version:2015年1月8日09:11:18
	 */
	public Map getCriminalCPBInfo(HttpServletRequest request,SystemUser user);
	/**
	 * 方法描述：新增减刑假释呈批表内容
	 * @author mushuhong
	 * @version 2015年1月8日15:36:13
	 */
	public String saveJxJsCPBContent(HttpServletRequest request,SystemUser user);
	/**
	 * 方法描述：提交呈批表内容
	 * @author mushuhong
	 * @version 2015年1月9日14:55:12
	 */
	public String submitJxJsCPBCaseInfo(HttpServletRequest request,SystemUser user);
				
	/**
	 * 方法描述：立案审批表显示
	 * @author zhenghui
	 * @version 2014年12月9日13:38:54
	 */
	public Map getParoleApproveMap(String crimid,String tempid,String departid);
	
	public ZpublicDaMtxx getHeadPicture(Map<String,Object> map);
	
	public TbprisonerFeatures selectTbprisonerFeatures(String crimid);
	
	public Map getBaseInfoByCrimidMap(String crimid,String departid);
	
	public Map getProvinceOutPrisonFormData_hb(String str);
	
	public String getUnitlevelByCrimidOrgid(String str);


    /**
     * 吧数据保存到基本信息表中
     * @param record
     * @return
     */
	int updateBaseByCrimid(Map map);
	
	public int findCountByCrimid(String str);
		
	/**
	 * 方法描述：通过罪犯编号查询出罪犯中文名称，转化为拼音
	 * @author:mushuhong
	 * @version:2015年9月18日18:51:59
	 * @return
	 */
	public Map generatePYByCrimid(String crimid);
	
	public List<Map> querySocialRelation(String crimid);
	
	public void updateImportantPunishmentBycrimid(TbprisonerBaseCrime record);
	
	public List<Map> selectByCardOrCrimid(Map<String,Object> paramMap);
	
	public Map getCurrentBatch(String departid);
	
	public Object saveThreeKindCrimeIdentify(Map<String,Object> map, SystemUser user);
	
	public void updatePprioveAndduty(Map map);
	
	public List<Map> getJianXingBaseInfo(String crimid);
	
	public List<Map<String,Object>> getCriminalInfo(String crimid);
	
	
	/**
	 * 新增犯人犯罪处罚信息表
	 */
	public int insertTbprisonerBaseCrime(Map<String, Object> record);
	
	
	/**
	 * 获取媒体信息表的下一个ID
	 */
	public int getNextID();
	
	/**
	 * 插入罪犯的照片
	 */
	public int insertPrisonerPicture(ZpublicDaMtxx record);
	
	/**
	 * 更新罪犯的照片
	 */
	public int updatePrisonerPicture(ZpublicDaMtxx record);
	
	/**
	 * 更新刑期变动信息表TBXF_SENTENCEALTERATION表数据
	 * @param orgid String 组织id
	 * @param rimid String 罪犯id
	 */
	public void callXFsentencealteration(Map map);
}