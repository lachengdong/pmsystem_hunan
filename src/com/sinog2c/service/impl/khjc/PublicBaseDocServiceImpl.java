package com.sinog2c.service.impl.khjc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.khjc.KhjcCodeMapper;
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocSlaveMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.khjc.KhjcTbflowBaseDocSlave;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.khjc.PublicBaseDocService;
import com.sinog2c.service.api.khjc.PublicToolMethodService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("publicBaseDocService")
public class PublicBaseDocServiceImpl extends ServiceImplBase implements PublicBaseDocService{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PublicToolMethodService publicToolMethodService;
	@Autowired
	private KhjcCriminalScoreSdMapper khjcCriminalScoreSdMapper;
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	@Autowired
	private KhjcTbflowBaseDocSlaveMapper khjcTbflowBaseDocSlaveMapper;
	@Autowired
	private KhjcTbflowDeliverMapper khjcTbflowDeliverMapper;
	@Autowired
	private KhjcCodeMapper khjcCodeMapper;
	@Autowired
	private SignatureSchemeMapper signatureSchemeMapper;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	
	/**
	 * 更新附件表表大字段(只更新大字段，更新时间，更新人)
	 */
	public String updateKhjcFlowBaseDocContentBydocid(String docid,String doccontent,SystemUser user) {
		String returnValue = "success";
		if(!"".equals(docid) && !"".equals(doccontent)){
			KhjcTbflowBaseDoc docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
			docpojo.setDocconent(doccontent);//大字段
			docpojo.setUpdatetime(publicToolMethodService.getNowDate());//更新时间
			docpojo.setUpdatemender(user.getUserid());//更新人
			khjcTbflowBaseDocMapper.updateByPrimaryKeySelective(docpojo);
		}else{
			returnValue = "主键或者生成的大字段为空！";
		}
		return returnValue;
	}
	
	
	
	
	/**
	 * 保存或更新大字段
	 * yanqutai
	 * 参数(map：页面数据,request)
	 */
	@Override
	public Map saveKhjcFlowBaseDoc(Map<String,String> map,SystemUser user){
		String returnValue = "success";
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDoc docpojo = new KhjcTbflowBaseDoc();//附件表对象
		String id = publicToolMethodService.getSeqBySeqname("SEQ_KHJC_TBFLOW_BASE_DOC_ID");
		if(null != map.get("doccontent") && !"".equals(map.get("doccontent"))){
			//保存附件表
			docpojo.setDocid(id);//主键
			docpojo.setTempletid(map.get("templetid")==null?"":map.get("templetid"));//模板id
			docpojo.setDocconent(map.get("doccontent")==null?"":map.get("doccontent"));//大字段
			docpojo.setContent(map.get("content")==null?"":map.get("content"));//附件描述
			docpojo.setFlowdefid(map.get("flowdefid")==null?"":map.get("flowdefid"));//流程名称
			docpojo.setCrimid(map.get("crimid")==null?"":map.get("crimid"));//罪犯编号
			docpojo.setFlowdeforderby("1");//流程顺序 保存直接到起始位
			docpojo.setDocdep(map.get("docdep")==null?"":map.get("docdep"));//具体的单位ID(罪犯所在部门iD或申请人所在部门ID)
			docpojo.setDepartid(map.get("departid")==null?"":map.get("departid"));//监狱ID
			docpojo.setDelflag("0");//删除标志位(0有效 1删除)
			docpojo.setIssee("1");//是否查看标志位(0未查看 1已查看)
			docpojo.setDepartid(user.getDepartid());//当前监狱
			docpojo.setCreatetime(publicToolMethodService.getNowDate());//创建时间
			docpojo.setCreatemender(user.getUserid());//创建人
			docpojo.setUpdatetime(publicToolMethodService.getNowDate());//创建时间
			docpojo.setUpdatemender(user.getUserid());//创建人
			
			khjcTbflowBaseDocMapper.insert(docpojo);//插入操作
		}else{
			returnValue = "未生成表单大字段信息！";
		}
		
		rertunMap.put("state", returnValue);
		rertunMap.put("id",id);
		return rertunMap;
		
	}
	
	
	public List<Map> getBaseDocByCondition(Map map){
		return MapUtil.turnKeyToLowerCaseOfList(khjcTbflowBaseDocMapper.getKhjcFlowBaseDoctListByNodeid(map));
	}
	
	public int countBaseDocByCondition(Map map){
		return khjcTbflowBaseDocMapper.countBaseDocByCondition(map);
	}

	/**
	 * 获取附件表大字段
	 */
	public String getDocContentByDocid(String docid) {
		String doccontent = "";
		if(!"".equals(docid)){
			KhjcTbflowBaseDoc docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
			doccontent = docpojo.getDocconent();//大字段
		}
		return doccontent;
	}
	
	/**
	 * 根据主键删除附件表信息
	 */
	public int deleteKhjcFlowBaseDoc(String docid) {
		return khjcTbflowBaseDocMapper.deleteByPrimaryKey(docid);
	}

	@Override
	public Map updateKhjcFlowBaseDocBydocid(String docId,
			Map<String, String> map, HttpServletRequest request) {
		return null;
	}

	/**
	 * author：YangZR
	 * Date：   2015.02.04
	 * @param map
	 * @param user
	 * @return 返回：Map
	 * 成功则返回：rows 保存的条数=1, docid 主附件表的主键
	 * 失败则返回：rows 保存的条数=0, status 失败的描述
	 */
	@Transactional
	@Override
	public Map saveFormDoc(Map<String,String> map,SystemUser user){
		
		String docid = map.get("docid");
		String masterslave = map.get("masterslave");
		if(StringNumberUtil.notEmpty(docid)){
			if(StringNumberUtil.isEmpty(masterslave)||"master".equals(masterslave.trim())){ //操作主附件表
				//查询是否存在主附件表
				int count = countMasterBaseDocByCondition(docid);
				if(count==1){//存在主附件表，update 主附件表
					return updateKhjcFlowBaseDoc(map, user);
				}else if(count==0){//不存在主附件表，insert 主附件表
					return insertKhjcFlowBaseDoc(map, user);
				}
			}else if(StringNumberUtil.notEmpty(masterslave)&&"slave".equals(masterslave.trim())){  //操作从附件表
				//查询是否存在从附件表，存在则返回从附件表的序号
				String snStr = countSlaveBaseDocByCondition(docid,map.get("templetid"));
				if(StringNumberUtil.notEmpty(snStr)){ //存在从附件表，update 从附件表
					map.put("sn", snStr);
					return updateKhjcFlowBaseDocSlave(map,user);
				}else{ //不存在从附件表，insert 从附件表
					return insertKhjcFlowBaseDocSlave(map,user);
				}
			}
		}else{
			if(StringNumberUtil.isEmpty(masterslave)||"master".equals(masterslave.trim())){ //操作主附件表
				//insert 主附件表
				return insertKhjcFlowBaseDoc(map, user);
			}else if(StringNumberUtil.notEmpty(masterslave)&&"slave".equals(masterslave.trim())){  //操作从附件表
				//返回先保存主附件表的提示
				Map returnMap = new HashMap();
				returnMap.put("rows", 0);
				returnMap.put("status", "请先保存主文书！");
				return returnMap;
			}
		}
		
		Map returnMap = new HashMap();
		returnMap.put("rows", 0);
		returnMap.put("status", "操作失败！");
		return returnMap;
	}
	
	//查询是否存在主附件表
	public int countMasterBaseDocByCondition(String docid){
		Map paraMap = new HashMap();
		paraMap.put("docid", docid);
		return khjcTbflowBaseDocMapper.countBaseDocByCondition(paraMap);
	}
	
	
	/**
	 * 描述：不存在主附件表，insert 主附件表
	 * author：YangZR
	 * 返回：Map
	 * 成功则返回：rows 保存的条数=1, docid 主附件表的主键
	 * 失败则返回：rows 保存的条数=0, status 失败的描述
	 */
	public Map insertKhjcFlowBaseDoc(Map<String,String> map,SystemUser user){
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDoc docpojo = new KhjcTbflowBaseDoc();//附件表对象
		String id = publicToolMethodService.getSeqBySeqname("SEQ_KHJC_TBFLOW_BASE_DOC_ID");
		//保存附件表
		docpojo.setDocid(id);//主键
		docpojo.setTempletid(map.get("templetid"));//模板id
		docpojo.setDocconent(map.get("doccontent"));//大字段
		docpojo.setContent(map.get("content"));//附件描述
		docpojo.setFlowdefid(map.get("flowdefid"));//流程名称
		docpojo.setCrimid(map.get("crimid"));//罪犯编号
		docpojo.setFlowdeforderby("1");//流程顺序 保存直接到起始位
		docpojo.setDocdep(map.get("docdep"));//具体的单位ID(罪犯所在部门iD或申请人所在部门ID)
		docpojo.setIssee("1");//是否查看标志位(0未查看 1已查看)
		docpojo.setDepartid(user.getDepartid());//当前监狱
		docpojo.setCreatetime(publicToolMethodService.getNowDate());//创建时间
		docpojo.setCreatemender(user.getUserid());//创建人
		docpojo.setUpdatetime(publicToolMethodService.getNowDate());//创建时间
		docpojo.setUpdatemender(user.getUserid());//创建人
		
		int rows = khjcTbflowBaseDocMapper.insertSelective(docpojo);//插入操作
		if(rows==1){
			rertunMap.put("docid", id);
			rertunMap.put("rows", rows);
		}else{
			rertunMap.put("rows", 0);
			rertunMap.put("status", "保存失败！");
		}
//		if(StringNumberUtil.notEmpty(map.get("doccontent"))){
//			
//		}else{
//			rertunMap.put("rows", 0);
//			rertunMap.put("status", "没有文书！");
//		}
		
		return rertunMap;
		
	}
	
	/**
	 * 描述：存在主附件表，update 主附件表
	 * author：YangZR
	 * 返回：Map
	 * 成功则返回：rows 保存的条数=1, docid 主附件表的主键
	 * 失败则返回：rows 保存的条数=0, status 失败的描述
	 */
	public Map updateKhjcFlowBaseDoc(Map<String,String> map,SystemUser user){
		
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDoc docpojo = new KhjcTbflowBaseDoc();//附件表对象
		//保存附件表
		docpojo.setDocid(map.get("docid"));//主键
		docpojo.setTempletid(map.get("templetid"));//模板id
		docpojo.setDocconent(map.get("doccontent"));//大字段
		docpojo.setContent(map.get("content"));//附件描述
		docpojo.setFlowdefid(map.get("flowdefid"));//流程名称
		docpojo.setCrimid(map.get("crimid"));//罪犯编号
		docpojo.setFlowdeforderby(map.get("flowdeforderby"));//流程顺序 保存直接到起始位
		docpojo.setDocdep(map.get("docdep"));//具体的单位ID(罪犯所在部门iD或申请人所在部门ID)
		docpojo.setDepartid(map.get("departid"));//监狱ID
		docpojo.setIssee("1");//是否查看标志位(0未查看 1已查看)
		docpojo.setDepartid(user.getDepartid());//当前监狱
		docpojo.setUpdatetime(publicToolMethodService.getNowDate());//更新时间
		docpojo.setUpdatemender(user.getUserid());//更新人
		
		int rows = khjcTbflowBaseDocMapper.updateByPrimaryKeySelective(docpojo);//更新操作
		if(rows==1){
			rertunMap.put("docid", map.get("docid"));
			rertunMap.put("rows", rows);
		}else{
			rertunMap.put("rows", 0);
			rertunMap.put("status", "保存失败！");
		}
//		if(StringNumberUtil.notEmpty(map.get("doccontent"))){
//			
//		}else{
//			rertunMap.put("rows", 0);
//			rertunMap.put("status", "没有文书！");
//		}
		return rertunMap;
	}
	
	
	/**
	 * 查询是否存在从附件表，存在则返回从附件表的序号 
	 * @param docid
	 * @param templetid
	 * @return 如果存在，则返回SN的字符串，不存在则返回null
	 */
	public String countSlaveBaseDocByCondition(String docid,String templetid){
		Map paraMap = new HashMap();
		paraMap.put("docid", docid);
		paraMap.put("templetid", templetid);
		return khjcTbflowBaseDocSlaveMapper.selectSNByMap(paraMap);
	}
	
	/**
	 * 描述：不存在从附件表，insert 从附件表
	 * author：YangZR
	 * 返回：Map
	 * 成功则返回：rows 保存的条数=1, docid 主附件表的主键
	 * 失败则返回：rows 保存的条数=0, status 失败的描述
	 */
	public Map insertKhjcFlowBaseDocSlave(Map<String,String> map,SystemUser user){
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDocSlave docpojo = new KhjcTbflowBaseDocSlave();//从附件表对象
//		String id = publicToolMethodService.getSeqBySeqname("SEQ_KHJC_TBFLOW_BASE_DOC_ID");
		//保存附件表
		docpojo.setDocid(map.get("docid"));//主键
		docpojo.setSn(Short.parseShort(StringNumberUtil.isEmpty(map.get("sn"))?"0":map.get("sn")));//主键
		docpojo.setTempletid(map.get("templetid"));//模板id
		docpojo.setDocconent(map.get("doccontent"));//大字段
		docpojo.setCrimid(map.get("crimid"));//罪犯编号
		docpojo.setDepartid(user.getDepartid());
		docpojo.setCreatetime(new Date());//创建时间
		docpojo.setCreatemender(user.getUserid());//创建人
		docpojo.setUpdatetime(new Date());//更改时间
		docpojo.setUpdatemender(user.getUserid());//创建人
		
		int rows = khjcTbflowBaseDocSlaveMapper.insertSelective(docpojo);//插入操作
		if(rows==1){
			rertunMap.put("docid", map.get("docid"));
			rertunMap.put("rows", rows);
		}else{
			rertunMap.put("rows", 0);
			rertunMap.put("status", "保存失败！");
		}
//		if(StringNumberUtil.notEmpty(map.get("doccontent"))){
//			
//		}else{
//			rertunMap.put("rows", 0);
//			rertunMap.put("status", "没有文书！");
//		}
		
		return rertunMap;
		
	}
	
	/**
	 * 描述：存在从附件表，update 从附件表
	 * author：YangZR
	 * 返回：Map
	 * 成功则返回：rows 保存的条数=1, docid 主附件表的主键
	 * 失败则返回：rows 保存的条数=0, status 失败的描述
	 */
	public Map updateKhjcFlowBaseDocSlave(Map<String,String> map,SystemUser user){
		
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDocSlave docpojo = new KhjcTbflowBaseDocSlave();//从附件表对象
		//保存附件表
		docpojo.setDocid(map.get("docid"));//主键
		docpojo.setSn(Short.parseShort(StringNumberUtil.isEmpty(map.get("sn"))?"0":map.get("sn")));//主键
		docpojo.setTempletid(map.get("templetid"));//模板id
		docpojo.setDocconent(map.get("doccontent"));//大字段
		docpojo.setCrimid(map.get("crimid"));//罪犯编号
		docpojo.setDepartid(user.getDepartid());
		docpojo.setUpdatetime(new Date());//更改时间
		docpojo.setUpdatemender(user.getUserid());//创建人
		
		int rows = khjcTbflowBaseDocSlaveMapper.updateByPrimaryKeySelective(docpojo);//插入操作
		if(rows==1){
			rertunMap.put("docid", map.get("docid"));
			rertunMap.put("rows", rows);
		}else{
			rertunMap.put("rows", 0);
			rertunMap.put("status", "保存失败！");
		}
//		if(StringNumberUtil.notEmpty(map.get("doccontent"))){
//			
//		}else{
//			rertunMap.put("rows", 0);
//			rertunMap.put("status", "没有文书！");
//		}
		
		return rertunMap;
	}

}
