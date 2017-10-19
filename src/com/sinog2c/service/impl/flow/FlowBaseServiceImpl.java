package com.sinog2c.service.impl.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.flow.FlowBaseDocMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.flow.FlowBaseOtherMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("flowBaseService")
public class FlowBaseServiceImpl extends ServiceImplBase implements FlowBaseService{

	@Autowired
    private FlowBaseMapper flowBaseMapper;
	@Autowired
	private FlowBaseDocMapper flowBaseDocMapper;
	@Autowired
	private FlowBaseOtherMapper flowBaseOtherMapper;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbprisonerBaseCrimeMapper tbprisonerBaseCrimeMapper;

    public FlowBaseMapper getFlowBaseMapper() {
		return flowBaseMapper;
	}

	public void setFlowBaseMapper(FlowBaseMapper flowBaseMapper) {
		this.flowBaseMapper = flowBaseMapper;
	}
	@Transactional
	public int delete(String id) {
        return this.flowBaseMapper.delete(id);
    }
	
	@Override
    public FlowBase findById(String id) {
        return this.flowBaseMapper.findById(id);
    }
    
	@Override
    public Map findUvFlowByFlowdraftid(String flowdraftid){
    	return MapUtil.turnKeyToLowerCase(flowBaseMapper.findUvFlowByFlowdraftid(flowdraftid));
    }
    
    @Transactional
    public int insert(FlowBase flowBase) {
        return this.flowBaseMapper.insert(flowBase);
    }

    public List<FlowBase> selectAll() {
        return this.flowBaseMapper.selectAll();
    }
    @Transactional
    public int update(FlowBase flowBase) {
         return this.flowBaseMapper.update(flowBase);
    }
    @Transactional
    public int updateById(String islocked,String flowdraftid,String suid,String opname){
    	return this.flowBaseMapper.updateById(islocked,flowdraftid,suid,opname);
    }
    
    /**
	 * 查询出不能解锁的案件：非本人加锁的案件，不能由本人解锁
	 * YangZR	2016-03-11
	 */
    @Override
    public List<Map<String,Object>> selectUnable2unLockCases(Map<String,Object> map){
    	return MapUtil.convertKeyList2LowerCase(flowBaseMapper.selectUnable2unLockCases(map));
    }
    

	public int countAll() {
		return this.flowBaseMapper.countAll();
	}
	
	@Override
	public List<FlowBase> getBaseListByCondition(Map<String, Object> map) {
		return this.flowBaseMapper.findByCondition(map);
	}

	@Override
	public int countAllByCondition(Map<String, Object> map) {
		return this.flowBaseMapper.countAllByCondition(map);
	}
	
	@Override
	public List<FlowBase> getBaseListNotInforming(Map<String, Object> map){
		return this.flowBaseMapper.getBaseListNotInforming(map);
	}

	@Override
	public int getBaseListNotInformingCount(Map<String, Object> map){
		return this.flowBaseMapper.getBaseListNotInformingCount(map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getSeqBySeqname(String name) {
		HashMap map = new HashMap();
		map.put("sql", "select "+name+".nextval as id from dual");
		Map<String, Object> seqmap = flowBaseMapper.getSeq(map);
		String seq_applyid =  seqmap.get("ID").toString();
		return seq_applyid;
	}
	@Override
	public String getDocConentByFlowdraftId(String flowdraftid){
		String docConent = "";
		FlowBaseDoc doc = flowBaseDocMapper.findDocByflowdraftid(flowdraftid);
		if(doc!=null){
			docConent = doc.getDocconent();
		}else{
			FlowBaseOther other = flowBaseOtherMapper.findByFlowdraftId(flowdraftid);
			docConent = (other==null)?"":other.getDocconent();
		}
		return docConent;
	}
	
	@Override
	public String getMaxCaseNum(Map map){
		String casenumberStr = flowBaseMapper.getMaxCaseNum(map);
		String casenumber = "";
		if(StringNumberUtil.notEmpty(casenumberStr)){
			if(casenumberStr.subSequence(0, 4).equals(map.get("curyear"))){
				casenumber = casenumberStr.substring(4);
				casenumber = String.valueOf(Integer.parseInt(casenumber)+1);
			}else{
				casenumber = "1";
			}
		}else{
			casenumber = "1";
		}
		return casenumber;
	}
	
	
	/**
	 * YanGZR	湖南监狱减刑假释获取案件号,默认查询减有字
	 * @param map
	 * @return
	 */
	@Override
	public String getMaxCaseNum4HuNanJailCommute(Map<String,Object> map){
		
		//默认查询减（有）字
//		String casetype = StringNumberUtil.getStrFromMap("casetype", map);
//		if(StringNumberUtil.isEmpty(casetype)){
//			casetype = "减（有）";
//			map.put("casetype", casetype);
//		}
		
		String casenumberStr = flowBaseMapper.getMaxCaseNum4HuNanJailCommute(map);
		String casenumber = "";
		if(StringNumberUtil.notEmpty(casenumberStr)){
			if(casenumberStr.subSequence(0, 4).equals(map.get("curyear"))){
				casenumber = casenumberStr.substring(4);
				casenumber = String.valueOf(Integer.parseInt(casenumber)+1);
			}else{
				casenumber = "1";
			}
		}else{
			casenumber = "1";
		}
		return casenumber;
	}
	
	
	
	
	
	@Override
	public String getMaxBaowaiCaseNum(Map map){
		String casenumberStr = flowBaseMapper.getMaxBaowaiCaseNum(map);
		String casenumber = "";
		if(StringNumberUtil.notEmpty(casenumberStr)){
			if(casenumberStr.subSequence(0, 4).equals(map.get("curyear"))){
				casenumber = casenumberStr.substring(4);
				casenumber = String.valueOf(Integer.parseInt(casenumber)+1);
			}else{
				casenumber = "1";
			}
		}else{
			casenumber = "1";
		}
		return casenumber;
	}
	
	/**
	 * 描述：获取省局最大案件号
	 * @author YangZR	2015-07-14
	 * @param map
	 * @return
	 */
	@Override
	public String getSJMaxCaseNumForGD(Map map){
		String casenumberStr = flowBaseMapper.getSJMaxCaseNumForGD(map);
		String casenumber = "";
		if(StringNumberUtil.notEmpty(casenumberStr)){
			if(casenumberStr.subSequence(0, 4).equals(map.get("curyear"))){
				casenumber = casenumberStr.substring(4);
				casenumber = String.valueOf(Integer.parseInt(casenumber)+1);
			}else{
				casenumber = "1";
			}
		}else{
			casenumber = "1";
		}
		return casenumber;
	}
	
	@Override
	public String getLastCaseNum(Map map){
		Integer casenum = flowBaseMapper.getLastCaseNum(map);
		return String.valueOf(casenum + 1);
	}
	
	@Override
	public FlowBase getFlowBaseByFlowdraftid(Map map){
		return flowBaseMapper.getFlowBaseByFlowdraftid(map);
	}
	
	@Override
	public List<FlowBase> getFlowBaseByFlowdraftid2(Map map){
		return flowBaseMapper.getFlowBaseByFlowdraftid2(map);
	}
	
	@Override
	public List<Map> getFlowBaseListByFlowdraftids(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(flowBaseMapper.getFlowBaseListByFlowdraftids(map));
	}

	@Override
	public List<Map<String, Object>> getPublicRecordsData(Map<String, Object> map) {
		return flowBaseMapper.getPublicRecordsData(map);
	}

	@Override
	public int getJYZCount(Map<String, Object> map) {
		return flowBaseMapper.getJYZCount(map);
	}

	@Transactional
	public int updateByFlowdraftid(FlowBase flowbase) {
		return flowBaseMapper.updateSensitiveByFlowdraftid(flowbase);
	}

	@Override
	public List<Map<String,Object>> getJHZSData(Map<String, Object> map) {
		return flowBaseMapper.getJHZSData(map);
	}
	@Override
	public int getJHZSDataCount(Map<String, Object> map) {
		return flowBaseMapper.getJHZSDataCount(map);
	}
	
	@Override
	public List<Map> getProvinceJHZSData(Map<String, Object> map) {
		return flowBaseMapper.getProvinceJHZSData(map);
	}

	@Override
	public int countProvinceJHZSData(Map<String, Object> map) {
		return flowBaseMapper.countProvinceJHZSData(map);
	}
	
	@Override
	public int countAllByMapCondition(Map map){
		return flowBaseMapper.countAllByMapCondition(map);
	}
	
	@Override
	public List<Map> findByMapCondition(Map<String,Object> map){
		return MapUtil.turnKeyToLowerCaseOfList(flowBaseMapper.findByMapCondition(map));
	}

	@Override
	public int findByMapConditionCount(Map<String, Object> map) {
		return flowBaseMapper.findByMapConditionCount(map);
	}

	@Override
	public int updateCaseNumberByFlowdraftid(Map<String, Object> map) {
		return flowBaseMapper.updateCaseNumberByFlowdraftid(map);
	}

	@Override
	public int updateSignatureByFlowdraftid(Map<String, Object> map) {
		return flowBaseMapper.updateSignatureByFlowdraftid(map);
	}

	@Override
	public int updateSignatureByFlowdraftid_nx(Map<String, Object> map) {
		return flowBaseMapper.updateSignatureByFlowdraftid_nx(map);
	}
	
	@Override
	public Map selectSignaByCaseNum(Map<String,Object> map) {
		return flowBaseMapper.selectSignaByCaseNum(map);
	}
	
	@Override
	public Map selectSignaByCaseNum_nx(Map<String,Object> map) {
		return flowBaseMapper.selectSignaByCaseNum_nx(map);
	}
	@Override
	public String getFuyiflagFromUvFlow(Map map){
		return flowBaseMapper.getFuyiflagFromUvFlow(map);
	}
	
	@Override
	public Map getDataFromUvFlow(Map map){
		return MapUtil.turnKeyToLowerCase(flowBaseMapper.getDataFromUvFlow(map));
	}
	
	@Override
	public String setDiscussionDate(Map map){
		int count = flowBaseMapper.setDiscussionDate(map);
		if(count >0 ){
			return "success";
		}else{
			return "error";
		}
	}
	
	@Override
	public Map getCourtCaseHandleInfo(Map map){
		Map resultMap = flowBaseMapper.getCourtCaseHandleInfo(map);
		if(null==resultMap||resultMap.size()==0){
			resultMap = new HashMap();
			resultMap.put("flag", "0");
		}else{
			resultMap.put("flag", "1");
		}
		return MapUtil.turnKeyToLowerCase(resultMap);
	}
	
	@Override
	public String executeCourtCaseOperate(Map map){
		int count = flowBaseMapper.setDiscussionDate(map);
		if(count>0){
			return "success";
		}
		return "error";
	}

	@Override
	@SuppressWarnings("all")
	public Object ajaxNonationNumberImpl(HttpServletRequest request,SystemUser user) {
		
		String flowdefid = request.getParameter("flowdefid");//流程定义id
		String tempid = request.getParameter("tempid");
        List<Map> mapValue = new ArrayList<Map>();
        
        Map mapParameter = new HashMap();
        mapParameter.put("userid", user.getUserid());
//        mapParameter.put("departid", user.getDepartid());
        mapParameter.put("flowdefid", flowdefid);
        mapParameter.put("tempid", tempid);
        mapValue = flowBaseMapper.ajaxNonationNumberMapper(mapParameter);
        
        Object value = "";
//        if (mapValue.size() > 0) {
//			value = mapValue.get("NOTATION")+"^"+mapValue.get("SIGNNUMBER");
//		}
        //参数重复使用
        int signnumbers = 0;
        int notations = 0;
        if (mapValue.size() > 0) {
			for (Map map : mapValue) {
				signnumbers += Integer.parseInt(map.get("SIGNNUMBER").toString());
				notations += Integer.parseInt(map.get("NOTATION").toString());
			}
		}
        
		return value = signnumbers+"^"+notations;
	}

	@Override
	@SuppressWarnings("all")
	public Object ajaxNoShowNonationNumber(HttpServletRequest request,SystemUser user) {
		String flowdefid = request.getParameter("flowdefid");//流程定义id
		String tempid = request.getParameter("tempid");
        List notationAndSealNumber = getNonationAndSealNumberOfUser(flowdefid,tempid,user);
        if(null!=notationAndSealNumber&&notationAndSealNumber.size()==2){
        	String notations = notationAndSealNumber.get(0).toString();
        	String signnumbers = notationAndSealNumber.get(1).toString();
        	return signnumbers+"^"+notations;
        }
        return null;
	}
	
	@Override
	public List getNonationAndSealNumberOfUser(String flowdefid,String tempid,SystemUser user){
		Map mapValue = new HashMap();
        Map mapParameter = new HashMap();
        mapParameter.put("userid", user.getUserid());
//        mapParameter.put("departid", user.getDepartid());
        mapParameter.put("flowdefid", flowdefid);
        mapParameter.put("tempid", tempid);
        List<Map> departidList = flowBaseMapper.ajaxGetDepartidNoShowNonationNumber(mapParameter);
        if(StringNumberUtil.notEmpty(departidList)&&departidList.size()>0){
        	List tempList = new ArrayList();
        	for(Map map:departidList){
        		tempList.add(map.get("ORGID"));
        	}
        	SystemOrganization so = systemOrganizationService.getByOrganizationId(user.getDepartid());
        	if(tempList.contains(user.getDepartid())){
        		mapParameter.put("departid", user.getDepartid());
        	}else if(tempList.contains(so.getPorgid())){
        		mapParameter.put("departid", so.getPorgid());
        	}else{
        		return null;
        	}
        }else{
        	return null;
        }
        mapValue = flowBaseMapper.ajaxNoShowNonationNumberMapper(mapParameter);
        if(StringNumberUtil.notEmpty(mapValue)){
        	 List notationAndSealNumberList = new ArrayList();
        	notationAndSealNumberList.add(0,mapValue.get("NOTATIONS"));
        	notationAndSealNumberList.add(1,mapValue.get("SIGNNUMBERS"));
        	return notationAndSealNumberList;
        }else{
        	return null;
        }
	}

	@Override
	public Map getMinPregerssByUserid(Map map) {
		
		return flowBaseMapper.getMinPregerssByUserid(map);
	}
	
	@Override
	public Map getSubmitPregerssByUserid(Map map) {
		
		return flowBaseMapper.getSubmitPregerssByUserid(map);
	}

	/**
	 * 获取流程序号
	 */
	@Override
	public String getFlowXuHao(String year,String flowdefid, String applyid,
			String departid) {
		return flowBaseMapper.getFlowXuHao(year,flowdefid,applyid,departid);
	}

	@Override
	@SuppressWarnings("all")
	public String judgePunishmentByCrimid(String id) {
		//6404009302@640401@100009037(罪犯编号，所在部门，自定义id)
		String[] idString = id.split("@");
		String value = "";
		try {
			if (idString.length > 0) {
				String crimid = idString[0];
				String orgid = idString[1];
				String flowdrfid = idString[2];
				Map map = new HashMap();
				map.put("crimid", crimid);
				map = flowBaseMapper.judgePunishmentByCrimid(map);
				if (map.size() > 0) {
					value = map.get("NOWPUN").toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	@SuppressWarnings("all")
	public void updateIsdeclareByCrimid(HttpServletRequest request,SystemUser user) {
		try {
			String crimid = request.getParameter("crimid");//罪犯编号
			String departid = user.getDepartid();//单位id
			Map map = new HashMap();
			map.put("crimid", crimid);
			map.put("departid", departid);
			flowBaseMapper.updateIsdeclareByCrimid(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
//	@Override
//	public String getCourtMaxCaseNum(Map map) {
//		String casenumberStr = flowBaseMapper.getCourtMaxCaseNum(map);
//		String casenumber = "";
//		if(StringNumberUtil.notEmpty(casenumberStr)){
//			if(casenumberStr.subSequence(0, 4).equals(map.get("curyear"))){
//				casenumber = casenumberStr.substring(4);
//				casenumber = String.valueOf(Integer.parseInt(casenumber)+1);
//			}else{
//				casenumber = "1";
//			}
//		}else{
//			casenumber = "1";
//		}
//		return casenumber;
//	}

//	@Override
//	public String getCourtMaxCaseYear(Map paramMap) {
//		return  flowBaseMapper.getCourtMaxCaseYear(paramMap);
//	}

	@Override
	public String getBingQingZhenDuan(String str) {
		return flowBaseMapper.getBingQingZhenDuan(str);
	}

	@Override
	public FlowBase getFlowBaseByMap(Map map) {
		return flowBaseMapper.getFlowBaseByMap(map);
	}
	
	@Override
	 public int updateByFlowdraftids(Map map){
		return flowBaseMapper.updateByFlowdraftids(map);
	}

	@Override
	public List<Map> ajaxExportDataList(Map<String, Object> paraMap) {
		List<Map> listmap = new ArrayList<Map>();
		Map<String,Object> map = new HashMap<String,Object>();
		String conditionMessage = StringNumberUtil.returnString2(paraMap.get("conditionmessage"));
		if(conditionMessage.contains("batchyear"))  conditionMessage = conditionMessage.replaceAll("batchyear", "substr(f.text6,0,4)");////批次年
		if(conditionMessage.contains("batchno"))  conditionMessage = conditionMessage.replaceAll("batchno", "f.int6");//批次
		if(conditionMessage.contains("batchrange"))  conditionMessage = conditionMessage.replaceAll("batchrange", "substr(f.text6,5)");//案件号
		map.put("conditionMessage", conditionMessage);
		listmap = MapUtil.turnKeyToLowerCaseOfList(flowBaseMapper.findByCaseCondition(map));
		return listmap;
	}
	
//	@Override
//	public String getDocconentString(String flowdraftid,String flowdefid){
//		Map map = new HashMap();
//		map.put("flowdraftid", flowdraftid);
//		map.put("flowdefid", flowdefid);
//		if(StringNumberUtil.notEmpty(flowdefid)&&flowdefid.contains("other_")){
//			FlowBaseOther fbo = flowBaseOtherMapper.getLastDocconentByFlowdraftid(map);
//			return fbo.getDocconent();
//		}else if(StringNumberUtil.notEmpty(flowdefid)&&flowdefid.contains("doc_")){
//			FlowBaseDoc fbd = flowBaseDocMapper.findDocByflowdraftid(flowdraftid);
//			return fbd.getDocconent();
//		}else if(StringNumberUtil.notEmpty(flowdefid)&&flowdefid.contains("arch_")){
//			
//		}
//		
//		return null;
//	}
	
	/**
	 * 查询出符合条件的案件号
	 * @author YangZR 2015-03-30
	 * 
	 */
	@Override
	public List<Map<String,Object>> getCasenumsByCondition(Map<String, Object> map){
		return MapUtil.convertKeyList2LowerCase(flowBaseMapper.getCasenumsByCondition(map));
	}
	
	/**
	 * 通过案件号，及案件类型，查询出符合条件的草稿ID
	 * @author YangZR 2015-03-30
	 */
	@Override
	public List<Map<String,Object>> getFlowdraftidsByCondition(Map<String, Object> map){
		return MapUtil.convertKeyList2LowerCase(flowBaseMapper.getFlowdraftidsByCondition(map));
	}
	/*
	 * 批量重审案件
	 * @see com.sinog2c.service.api.flow.FlowBaseService#updateBatchReview(java.util.Map)
	 */
	@Transactional
	public int updateBatchReview(Map<String, Object> map) {
		int rows = 0;
		
		if(map!=null && map.size()>0){
			String idnumber = (String) map.get("idnumbers");
			if(StringNumberUtil.notEmpty(idnumber)){
				map.put("isreview", GkzxCommon.ONE);
				map.put("data", idnumber);
				rows = flowBaseMapper.setDiscussionDate(map);
			}
		}
		return rows;
	}

	@Override
	public Integer updateTextByflowdraftid(String text8, String flowdraftid) {
		return flowBaseMapper.updateTextByflowdraftid(text8, flowdraftid);
	}
	
	@Override
	public List<Map<String,Object>> getCrimeByFlowdraftids(Map<String,Object> map){
		return MapUtil.convertKeyList2LowerCase(flowBaseMapper.getCrimeByFlowdraftids(map));
	}
	
	@Override
	public void updateText23ById(FlowBase fb) {
		flowBaseMapper.updateText23ById(fb);
	}

	@Override
	public List<Map<String, Object>> findUvFlowData() {
		return MapUtil.convertKeyList2LowerCase(flowBaseMapper.findUvFlowData());
	}
	
	@Override
	public List<Map<String,Object>> findFlowBaseData(Map<String,Object> paramap){
		return MapUtil.convertKeyList2LowerCase(flowBaseMapper.findFlowBaseData(paramap));
	}

	@Override
	public String getTextByflowdraftid(Map<String, Object> map) {
		return flowBaseMapper.getTextByflowdraftid(map);
	}

	@Override
	public int setSendedStatus(Map<String,Object> map){
		return flowBaseMapper.setSendedStatus(map);
	}
	
	/**
	 * 描述：区分出流程继续走或直接结束的flowdraftids
	 * @author YangZR	2015-10-16
	 * @return Map{forward: 流程继续走的flowdraftids, 	end: 流程结束的flowdraftids}
	 */
	@Override
	public Map<String,Object> distinguishFlowForwardOrEnd(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		StringBuffer endSB = new StringBuffer();
		StringBuffer forwardSB = new StringBuffer();
		
		List<Map<String,Object>> resultList = MapUtil.convertKeyList2LowerCase(flowBaseMapper.distinguishFlowForwardOrEnd(map));
		for(Map<String,Object> tempMap : resultList){
			String flowdraftid = tempMap.get("flowdraftid").toString();
			String text12 = tempMap.get("text12").toString();
			if("1".equals(text12)){
				endSB.append(flowdraftid).append(",");
			}else if("2".equals(text12)){
				forwardSB.append(flowdraftid).append(",");
			}
		}
		String end = endSB.toString();
		String forward = forwardSB.toString();
		if(end.endsWith(",")){
			end = StringNumberUtil.removeLastStr(end, ",");
		}
		if(forward.endsWith(",")){
			forward = StringNumberUtil.removeLastStr(forward, ",");
		}
		resultMap.put("end", end);
		resultMap.put("forward", forward);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String,Object> distinguishSendToProvince(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		StringBuffer endinjailSB = new StringBuffer();
		StringBuffer sendtoprovinceSB = new StringBuffer();
		
		List<Map<String,Object>> resultList = MapUtil.convertKeyList2LowerCase(flowBaseMapper.distinguishSendToProvince(map));
		for(Map<String,Object> tempMap : resultList){
			String flowdraftid = tempMap.get("flowdraftid").toString();
			String text12 = tempMap.get("text12").toString();
			if("1".equals(text12)){
				endinjailSB.append(flowdraftid).append(",");
			}else if("2".equals(text12)){
				sendtoprovinceSB.append(flowdraftid).append(",");
			}
		}
		
		String endinjail = endinjailSB.toString();
		String sendtoprovince = sendtoprovinceSB.toString();
		if(endinjail.endsWith(",")){
			endinjail = StringNumberUtil.removeLastStr(endinjail, ",");
		}
		if(sendtoprovince.endsWith(",")){
			sendtoprovince = StringNumberUtil.removeLastStr(sendtoprovince, ",");
		}
		resultMap.put("endinjail", endinjail);
		resultMap.put("sendtoprovince", sendtoprovince);
		
		return resultMap;
	}
	
	
	  @Override
		public List<TbprisonerBaseinfo> getAllCrimid(){
			
	    	List<TbprisonerBaseinfo> info = flowBaseMapper.getAllCrimid();
			return info;
		}
	  @Override
		public String getAnnexcontent(String crimid){
			
	    	String content = flowBaseMapper.getAnnexcontent(crimid);
			return content;
		}
	  
	  @Override
		public String ajaxGetRepeatAnHao(Map map) {
			return flowBaseMapper.ajaxGetRepeatAnHao(map);
		}
	  
	  @Override
	  public List<Map<String, Object>> getAnJianTypeList(Map map) {
		  return flowBaseMapper.getAnJianTypeList(map);
	  }
	
	  @Override
	  public String getBackNodeidByCurrnodeid(Map<String,Object> paramap){
		  return flowBaseMapper.getBackNodeidByCurrnodeid(paramap);
	  }

	@Override
	public String findByFlowDraftid(String flowdraftid) {
		// TODO Auto-generated method stub
		return flowBaseMapper.findByFlowDraftid(flowdraftid);
	}
	  
	  @Override
	  public Object validateFlowCondition(Map<String,Object> map){
		  List<FlowBase> list = flowBaseMapper.getFlowBaseByFlowdraftid2(map);
		  Map<String,Object> result = new HashMap<String,Object>();
		  result.put("flowlocked", "0");
		  
		  String flowdraftids = "";
		  String lockreasons = "";
		  
		  if(null != list && list.size()>0){
			  result.put("flowlocked", "1");
			  List<String> flowdraftidList = new ArrayList<String>();
			  List<String> lockreasonList = new ArrayList<String>();
			  
			  for(FlowBase fb : list){
				  flowdraftidList.add(fb.getFlowdraftid());
				  lockreasonList.add(fb.getApplyname() +"：" + fb.getLockreason());
			  }
			  flowdraftids = StringNumberUtil.formatList2String(flowdraftidList, "，");
			  lockreasons = StringNumberUtil.formatList2String(lockreasonList, "，");
			  
			  result.put("flowdraftids", flowdraftids);
			  result.put("lockreasons", lockreasons);
		  }
		  
		  return result;
	  }
	  
	  
	@Override
	public Object getOrgLevelByFlowdraftid(Map<String,Object> map){
		Object unitlevel = flowBaseMapper.getOrgLevelByFlowdraftid(map);
		return unitlevel;
	}
	
    public String getUnitlevel(String departid){ 
	    return flowBaseMapper.getUnitlevel(departid);
    }
  
    public FlowBase getTianBiaoRen(String crimid){
	    return flowBaseMapper.getTianBiaoRen(crimid);
    }

	@Override
	public List<Map> getCaseTypeFromShengju() {
		return MapUtil.turnKeyToLowerCaseOfList(flowBaseMapper.getCaseTypeFromShengju());
	}
	  
	
	
	
	
	
	
	
	/**
	 * 重要罪犯三类犯在省局处长暂停operatesymbol=1，无期死缓继续走流程operatesymbol=2
	 */
	
	@Override
	public Map<String,Object> distinguishFinishAtSJCZ(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		StringBuffer endinSJCZSB = new StringBuffer();
		StringBuffer keepGoingSB = new StringBuffer();
		
		List<Map<String,Object>> resultList = MapUtil.convertKeyList2LowerCase(flowBaseMapper.distinguishFinishAtSJCZ(map));
		for(Map<String,Object> tempMap : resultList){
			String flowdraftid = tempMap.get("flowdraftid").toString();
			String operatesymbol = tempMap.get("operatesymbol").toString();
			if("1".equals(operatesymbol)){
				endinSJCZSB.append(flowdraftid).append(",");
			}else if("2".equals(operatesymbol)){
				keepGoingSB.append(flowdraftid).append(",");
			}
		}
		
		String endinSJCZ = endinSJCZSB.toString();
		String keepGoing = keepGoingSB.toString();
		if(endinSJCZ.endsWith(",")){
			endinSJCZ = StringNumberUtil.removeLastStr(endinSJCZ, ",");
		}
		if(keepGoing.endsWith(",")){
			keepGoing = StringNumberUtil.removeLastStr(keepGoing, ",");
		}
		resultMap.put("endinSJCZ", endinSJCZ);
		resultMap.put("keepGoing", keepGoing);
		
		return resultMap;
	}
}