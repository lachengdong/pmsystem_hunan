package com.sinog2c.service.impl.prisoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.commutationParole.TbxfScreeningMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.system.SystemTemplateMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("tbxfSentencealterationService")
public class TbxfSentencealterationServiceImpl extends ServiceImplBase implements
		TbxfSentencealterationService {
	@Autowired
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;
	@Autowired
	private SystemTemplateMapper systemTemplateMapper;
	@Autowired
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	@Autowired
	private TbxfScreeningMapper tbxfScreeningMapper;
	@Autowired
	private FlowBaseMapper flowBaseMapper;
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Autowired
	private SystemResourceService systemResourceService;
	
	@Override
	public int allCount(Map<String, Object> map) {
		
		return tbxfSentenceAlterationMapper.allCount(map);
	}
	@Override
	public List selectTbxfs(Map<String, Object> map) {
		List<Map> result = tbxfSentenceAlterationMapper.selectTbxfs(map);
		if(null==result) return null;
		return MapUtil.turnKeyToLowerCaseOfList(result);
	}
	@Override
	public Map<String, Object> selectTbxfByCrimid(String crimid) {
		return tbxfSentenceAlterationMapper.selectTbxfByCrimid(crimid);
	}
	/*
	 * 获取审核表显示数据
	 * @see com.sinog2c.service.api.prisoner.TbxfSentencealterationService#selectTbxfByCrimid(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getParoleByCrimid(String crimid,String flowdraftid,SystemUser user,HttpServletRequest request) {
		Integer punishmentyear = null;
		Map datamap = new HashMap();
		TbsysTemplate template = new TbsysTemplate();
		String tempid = request.getParameter("tempid");
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String hebeicode = StringNumberUtil.returnString2(jyconfig.getProperty(GkzxCommon.HEBEI_CODE));
		
		List<Map> list = tbxfSentenceAlterationMapper.getParoleByCrimid(crimid);
		List<Map> flowlist = flowBaseMapper.getParoleByFlowdraftid(flowdraftid);
		
		list.addAll(flowlist);
		if(list!=null){
			for(Object obj:list){
				Map tempmap = (Map)obj;
				tempmap =MapUtil.turnKeyToLowerCase(tempmap);
				if(tempmap.containsKey("punishmentyear")){
					if(StringNumberUtil.belongTo(departid, hebeicode)) {
						if(!StringNumberUtil.isNullOrEmpty(tempmap.get("punishmentyear"))) {
							punishmentyear = Integer.valueOf((String) tempmap.get("punishmentyear"));
							if(punishmentyear>20) tempid = "JXJS_JXJSSHB_YQ";
						} 
					} 
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("tempid", tempid);
					map.put("departid", departid);
					template = tbsysTemplateMapper.getTemplateDetail(map);
					if (template != null){
						tempmap.put("docconent", (String)template.getContent());
					}
				}
				//标题
				if(StringNumberUtil.belongTo(user.getDepartid(), hebeicode)) {
					String title = user.getOrganization().getText1() + "\\r\\n"+tempmap.get("jxorjs_hebei");
					tempmap.put("jxorjs", title);
				}
				//改造表现
				tempmap.put("gaizaobiaoxian", getGaizaobiaoxian(crimid, "9993", user,request));
				String fanzuishishi = "";
				if(!StringNumberUtil.isNullOrEmpty(tempmap.get("fanzuishishi"))){//犯罪事实去除rn
					fanzuishishi = tempmap.get("fanzuishishi").toString()
					.replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n");
					tempmap.put("fanzuishishi", fanzuishishi);
				}		
				datamap.putAll(tempmap);
			}
		}
		
		return datamap;
	}
	
	/*
	 * 改造表现 zhenghui
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public String getGaizaobiaoxian(String crimid,String id,SystemUser user,HttpServletRequest request){
		String result = "";
		SystemTemplate template = systemTemplateMapper.selectByTempid(id,user.getDepartid());
		if(null!=template){
			result = template.getContent();
			String findid = String.valueOf(template.getFindid());
			String infosql = tbsysTemplateMapper.getSqlText(findid);//和罪犯相关的信息
			infosql = systemResourceService.whereSql(user, infosql, request);
			if(StringNumberUtil.notEmpty(infosql)){
				Map tempMap = new HashMap();
				tempMap.put("sql", infosql.replace("CHR(10)", "\r\n"));//sql中换行用\r\n
				List<Map<String,Object>> list = tbsysTemplateMapper.getDocumentContentList(tempMap);
				if(null!=list&&list.size()>0){
					result  = MapUtil.replaceBracketContent(result, list.get(0));
					result = result.replaceAll("\"", "＂");//把双引号替换成全角的双引号
				}
			}
		}
		result = StringNumberUtil.returnString(result);
		return result;
	}
	
	/**
	 * 案件撤销
	 * @param ids
	 * @param menuid
	 * @return 案件撤销成功返success
	 */
	@Override
	@Transactional
	public String withdrawalCasesOfJail(String flowdraftids,String crimids){
		
		if(StringNumberUtil.isEmpty(flowdraftids)){
			return "error";
		}
		if(StringNumberUtil.isEmpty(crimids)){
			return "error";
		}
		
		int count = 0;
		Map paraMap = new HashMap();
		paraMap.put("flowdraftids", flowdraftids);
		
		count = flowBaseMapper.deleteFlowBaseOtherDateByFlowdraftids(paraMap);
		if(count <=0){
			throw new RuntimeException();
		}
		
		count = flowBaseMapper.deleteFlowOtherFlowDateByFlowdraftids(paraMap);
		if(count <=0){
			throw new RuntimeException();
		}
		
		count = flowBaseMapper.deleteFlowDateByFlowdraftids(paraMap);
		if(count <=0){
			throw new RuntimeException();
		}
		
		count = flowBaseMapper.deleteFlowBaseDateByFlowdraftids(paraMap);
		if(count <=0){
			throw new RuntimeException();
		}
		
		count = flowBaseMapper.deleteJXJSCPBByFlowdraftids(paraMap);
		if(count < 0){
			throw new RuntimeException();
		}
		
		paraMap.put("isdeclare", 0);
		paraMap.put("crimids", crimids);
		count = tbxfScreeningMapper.updateDataAfterWithdraw(paraMap);
		if(count <=0){
			throw new RuntimeException();
		}
		paraMap.put("specialcrim", "0");
		tbxfScreeningMapper.updateSpecialCrim(paraMap);	
		return "success";
	}
	
	/**
	 * 案件退回
	 * @param ids
	 * @param menuid
	 * @return 案件退回成功返success
	 */
	@Override
	@Transactional
	public String backCasesOfJail(String flowdraftids,String crimids){
		
		if(StringNumberUtil.isEmpty(flowdraftids)){
			return "error";
		}
		if(StringNumberUtil.isEmpty(crimids)){
			return "error";
		}
		
		int count = 0;
		Map paraMap = new HashMap();
		paraMap.put("crimids", crimids);
		paraMap.put("flowdraftids", flowdraftids);
		
		count = flowBaseMapper.deleteFlowBaseOtherDateByFlowdraftids(paraMap);
//		if(count <=0){
//			throw new RuntimeException();
//		}
		
		count = flowBaseMapper.deleteFlowOtherFlowDateByFlowdraftids(paraMap);
//		if(count <=0){
//			throw new RuntimeException();
//		}
		
		count = flowBaseMapper.deleteFlowDateByFlowdraftids(paraMap);
//		if(count <=0){
//			throw new RuntimeException();
//		}
		
		count = flowBaseMapper.deleteFlowBaseDateByFlowdraftids(paraMap);
//		if(count <=0){
//			throw new RuntimeException();
//		}
		
		count = flowBaseMapper.deleteJXJSCPBByFlowdraftids(paraMap);
//		if(count < 0){
//			throw new RuntimeException();
//		}
		
		count = tbxfScreeningMapper.updateLiAnBack(paraMap);
//		if(count <=0){
//			throw new RuntimeException();
//		}
		return "success";
	}
	
	public int getLiAnApproveCount(Map map){
		return tbxfScreeningMapper.getLiAnApproveCount(map);
	}
	
	public List<Map> getLiAnApproveList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfScreeningMapper.getLiAnApproveList(map));
	}
	
	public int getLiAnCount(Map map){
		return tbxfScreeningMapper.getLiAnCount(map);
	}
	
	public List<Map> getLiAnList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfScreeningMapper.getLiAnList(map));
	}
	
	
	public String getCrimiNameByCrimid(String crimid){
		return tbxfScreeningMapper.getCrimiNameByCrimid(crimid);
	}
	
	
	@Override
	public int provinceLiAnCount(Map map){
		return tbxfSentenceAlterationMapper.provinceLiAnCount(map);
	}
	
	@Override
	public  List<Map> provinceLiAnList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.provinceLiAnList(map));
	}
	
	@Override
	public int countCourtLiAnList(Map map){
		return tbxfSentenceAlterationMapper.countCourtLiAnList(map);
	}
	
	@Override
	public  List<Map> getCourtLiAnList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getCourtLiAnList(map));
	}
	
	
	
	@Override
	public int countCourtReportLiAnList(Map map){
		return tbxfSentenceAlterationMapper.countCourtReportLiAnList(map);
	}
	
	@Override
	public  List<Map> getCourtReportLiAnList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getCourtReportLiAnList(map));
	}
	@Override
	public int countCourtCheckLiAnList(Map map){
		return tbxfSentenceAlterationMapper.countCourtCheckLiAnList(map);
	}
	
	@Override
	public  List<Map> getCourtCheckLiAnList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getCourtCheckLiAnList(map));
	}
	
	@Override
	public List getJailList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getJailList(map));
	}
	
	@Override
	public List getJailListUnderCourt(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getJailListUnderCourt(map));
	}
	
	@Override
	public int countOfficeShenHeList(Map map){
		return tbxfSentenceAlterationMapper.countOfficeShenHeList(map);
	}
	
	@Override
	public List<Map> officeShenHeList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.officeShenHeList(map));
	}
	@Override 
	public List<Map<String, Object>> selectTbxfs2(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbxfSentenceAlterationMapper.selectTbxfs2(map));
	}
	@Override 
	public List<Map<String, Object>> selectTbxfs3(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbxfSentenceAlterationMapper.selectTbxfs3(map));
	}
	@Override
	public int selectTbxfs2Count(Map<String, Object> map) {
		return tbxfSentenceAlterationMapper.selectTbxfs2Count(map);
	}
	
	@Override
	public int selectTbxfs3Count(Map<String, Object> map) {
		return tbxfSentenceAlterationMapper.selectTbxfs3Count(map);
	}	
	
	
	@Override
	public int imselectTbxfs2Count(Map<String, Object> map) {
		return tbxfSentenceAlterationMapper.imselectTbxfs2Count(map);
	}
	
	@Override
	public List<Map> getSJCaseList(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getSJCaseList(map));
	}
	
	@Override
	public Map selectTbxfMapBySql(String sql){
		return MapUtil.turnKeyToLowerCase(tbxfSentenceAlterationMapper.selectTbxfMapBySql(sql));
	}
	
	@Override 
	public List<Map<String, Object>> imselectTbxfs2(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbxfSentenceAlterationMapper.imselectTbxfs2(map));
	}
	
	@Override
	public TbxfSentenceAlteration selectByPrimaryKey(String crimid){
		return tbxfSentenceAlterationMapper.selectByPrimaryKey(crimid);
	}
	@Override
	public List<TbxfSentenceAlteration> sentenceAlterationList(Map map) {
		return tbxfSentenceAlterationMapper.sentenceAlterationLists(map);
	}
	@Override
	public List<String> departidlist() {
		return tbxfSentenceAlterationMapper.departidlist();
	}
	@Override
	public int getSJCaseCount(Map map) {
		return tbxfSentenceAlterationMapper.getSJCaseCount(map);
	}
	@Override
	public List<Map> getAreaidList(Map map) {
		return tbxfSentenceAlterationMapper.getAreaidList(map);
	}
	@Override
	public int getByjyCaseCount(Map map) {
		return tbxfSentenceAlterationMapper.getByjyCaseCount(map);
	}
	@Override
	public List<Map> getByjyCaseList(Map map) {
		return tbxfSentenceAlterationMapper.getByjyCaseList(map);
	}
	
	@Override
	public Map getCourtCaseBaseInfoOfCrimid(Map map){//crimeface
		return   MapUtil.turnKeyToLowerCase(tbxfSentenceAlterationMapper.getCourtCaseBaseInfoOfCrimid(map));
	}
	@Override
	public List<Map> getBWCaseDataInfo(Map map) {
		return tbxfSentenceAlterationMapper.getBWCaseDataInfo(map);
	}
	@Override
	public int getBWCaseDataInfoCount(Map map) {
		return tbxfSentenceAlterationMapper.getBWCaseDataInfoCount(map);
	}
	
	@Override
	public Map<String, Object> getThreeOfCrimBaseinfo(String crimid) {
		return tbxfSentenceAlterationMapper.getThreeOfCrimBaseinfo(crimid);
	}
	
	@Override
	public List<Map<String,Object>> getPunishmentChangeinfo(String crimid) {
		return tbxfSentenceAlterationMapper.getPunishmentChangeinfo(crimid);
	}
	@Override
	public List<Map> getCourtLocalCaseData(Map map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getCourtLocalCaseData(map));
	}
	@Override
	public int getCourtLocalCaseDataCount(Map map) {
		return tbxfSentenceAlterationMapper.getCourtLocalCaseDataCount(map);
	}
	
	@Override
	public Map<String,Object> getNowYuXing(Map<String, Object> map) {
		return MapUtil.trimKeyValue(tbxfSentenceAlterationMapper.getNowYuXing(map));
	}
//	@Override
//	public List<Map<String, Object>> selectTbxfs2_sx(Map<String, Object> map) {
//		return tbxfSentenceAlterationMapper.selectTbxfs2_sx(map);
//	}
//	@Override
//	public int selectTbxfs2Count_sx(Map<String, Object> map) {
//		return tbxfSentenceAlterationMapper.selectTbxfs2Count_sx(map);
//	}
	@Override
	public Map<String,Object> getCommutationaccordByCrimid(Map<String,Object> map) {
		return tbxfSentenceAlterationMapper.getCommutationaccordByCrimid(map);
	}
	@SuppressWarnings("all")
	public Object cheXiaoBwjyCase(HttpServletRequest request, SystemUser user) {
		try {
			String crimid = request.getParameter("crimids");//罪犯编号
			String flowdraftid = request.getParameter("flowdraftids");//流程自定义id
			
			//当数据不为空的时候执行删除操作(其实此处不用判断，因为前台如果不选择内容，将无法调用后台方法)
			if (flowdraftid != null && !"".equals(flowdraftid)) {
				String[] crimids = crimid.split(",");
				String[] flowdraftids = flowdraftid.split(",");
				/*
				 * 执行下面的删除操作，分别按顺序进行删除
				 * 1：TBFLOW_BASE(deleteTbflowbaseByflowdraftids)
				 * 2：TBFLOW(deleteTbflowByflowdraftids)
				 * 3：TBFLOW_OTHER_FLOW(deleteTbflowOhterByflowdraftids)
				 * 4：TBFLOW_BASE_OTHER(deleteTbflowBaseOhterByflowdraftids)
				 * 执行完删除操作：执行下面的修改操作
				 * 1：TBPRISONER_BASE_CRIME(updateDetainStatusByCrimds)
				 *:2：TBXF_SENTENCEALTERATION(updateStatusByCrimids)
				 */
				Map map = new HashMap();
				for (int i = 0; i < flowdraftids.length; i++) {
					map.put("flowdraftid", flowdraftids[i]);
					map.put("crimid", crimids[i]);
					map.put("detainstatus", GkzxCommon.DETAINSTATUS_BW);
					tbxfSentenceAlterationMapper.deleteTbflowbaseByflowdraftids(map);
					tbxfSentenceAlterationMapper.deleteTbflowByflowdraftids(map);
					tbxfSentenceAlterationMapper.deleteTbflowOhterByflowdraftids(map);
					tbxfSentenceAlterationMapper.deleteTbflowBaseOhterByflowdraftids(map);
					//案件撤销以后，需要把罪犯的保外状态修改为在押状态。
					//并且把刑期变动表 病残鉴定审批通过状态 修改为初始状态
					tbxfSentenceAlterationMapper.updateDetainStatusByCrimds(map);
					tbxfSentenceAlterationMapper.updateStatusByCrimids(map);
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
		return null;
	}
	@Override
	/**
	 * 获取案件查看的内容
	 */
	@SuppressWarnings("all")
	public Object bwjyLookCaseDataInfo(HttpServletRequest request,SystemUser user) {
		JSONMessage message = new JSONMessage();
		Map map=new HashMap();
		try {
			String tempstr="";
			String key = request.getParameter("key");//罪犯编号or 罪犯姓名
			String orgid=request.getParameter("depid");//部门编号
			String nodeidname = request.getParameter("nodeidname");//进程中文描述
			String typeid=request.getParameter("typeid");//保外类型(初保or续保)
			String anjiantype=request.getParameter("anjiantype");//案件类型
			String punishmenttype=request.getParameter("punishmenttype");//刑种类型
			
			String flowdefid=request.getParameter("flowdefid");//流程自定义id
			if (typeid != null && !"".equals(typeid)) {
				flowdefid = typeid;
			}
			String nodeid = request.getParameter("nodeid")==null?"":request.getParameter("nodeid");//所选择的节点
			String[] nodeids = nodeid.split("_");
			//选择 进程的时候 把对应的条件 添加到map中
			if (nodeids.length > 1) {
				map.put("nodeid1", nodeids[0]);
				map.put("state1", nodeids[1]);
				map.put("state2", nodeids[2]);
				map.put("nodeid2", nodeids[3]);
				map.put("state3", nodeids[4]);
				map.put("nodeid", nodeid);
				map.put("nodeidname", nodeidname);
			}
			//如果部门为空，那么选择当前部门
			if (orgid == null || "".equals(orgid)) {
				orgid = user.getOrgid();
			}
			
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? 0:Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? 0:Integer.parseInt(request.getParameter("pageSize"))); 
			
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			if("1".equals(punishmenttype)){
				punishmenttype="";
				tempstr="youqi";
			}
			
			map.put("key", key);
			map.put("orgid", orgid);
			
			map.put("flowdefid", flowdefid);
			map.put("changetype", anjiantype);
			map.put("start", start);
			map.put("end", end);
			map.put("punishmenttype", punishmenttype);
			map.put("tempstr", tempstr);
			map.put("typeid", typeid);
			List<Map> data=new ArrayList<Map>();
			data= MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.bwjyLookCaseDataInfoMapper(map));
			int total=tbxfSentenceAlterationMapper.bwjyLookCaseDataInfoCount(map);
			message.setData(data);
			message.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	@Override
	public Map<String, Object> getCourtFuyiInfo(Map paraMap) {
		return   MapUtil.turnKeyToLowerCase(tbxfSentenceAlterationMapper.getCourtFuyiInfo(paraMap));
	}

	@SuppressWarnings("all")
	public int judgeBwCaseExistSave(HttpServletRequest request,SystemUser user) {
		int count = 0;
		try {
			String tempid = request.getParameter("tempid");//表单id
			String flowdraftid = request.getParameter("flowdraftid");//流程草稿id
			Map map = new HashMap();
			map.put("tempid", tempid);
			map.put("flowdraftid", flowdraftid);
			Map map2 = tbxfSentenceAlterationMapper.judgeBwCaseExistSave(map);
			if (map2 != null) {
				count = Integer.parseInt(map2.get("COUNTS").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int countCourtJianduLiAnList(Map<String, Object> map) {
		return tbxfSentenceAlterationMapper.countCourtJianduLiAnList(map);
	}
	@Override
	public List<Map> getCourtJianduLiAnList(Map<String, Object> map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbxfSentenceAlterationMapper.getCourtJianduLiAnList(map));
	}
	
	public int getBaobeiLiAnCount(Map map){
		return tbxfScreeningMapper.getBaobeiLiAnCount(map);
	}
	
	public List<Map> getBaobeiLiAnList(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(tbxfScreeningMapper.getBaobeiLiAnList(map));
	}
	
	public Map getCrimidFlowData(String flowdraftid) {
		// TODO Auto-generated method stub
		return tbxfScreeningMapper.getCrimidFlowData(flowdraftid);
	}
	
	public int addSanLeiFanBaoBei(Map resourceMap) {
		// TODO Auto-generated method stub
		return tbxfScreeningMapper.addSanLeiFanBaoBei(resourceMap);
	}
	
	public int updateSanLeiFanBaoBei(Map resourceMap) {
		// TODO Auto-generated method stub
		return tbxfScreeningMapper.updateSanLeiFanBaoBei(resourceMap);
	}
	
}
