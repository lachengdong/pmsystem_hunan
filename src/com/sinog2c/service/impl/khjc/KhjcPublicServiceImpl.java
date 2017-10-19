package com.sinog2c.service.impl.khjc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowBaseDocMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.flow.FlowBaseOtherMapper;
import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.flow.FlowOtherFlowMapper;
import com.sinog2c.dao.api.khjc.KhjcCodeMapper;
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.khjc.KhjcTbflowDeliver;
import com.sinog2c.model.khjc.KhjcTbflowDeliverKey;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.PublicBaseDocService;
import com.sinog2c.service.api.khjc.TbxfCommutationService;
import com.sinog2c.service.api.khjc.TbxfOutExecuteService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SignatureFlowService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("khjcPublicService")
public class KhjcPublicServiceImpl extends ServiceImplBase implements KhjcPublicService{
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PublicBaseDocService publicBaseDocService;
	@Autowired
	private KhjcCriminalScoreSdMapper khjcCriminalScoreSdMapper;
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	@Autowired
	private KhjcTbflowDeliverMapper khjcTbflowDeliverMapper;
	@Autowired
	private KhjcCodeMapper khjcCodeMapper;
	@Autowired
	private SignatureSchemeMapper signatureSchemeMapper;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private TbxfCommutationService tbxfCommutationService;
	@Resource
	private TbxfOutExecuteService tbxfOutExecuteService;
	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	@Autowired(required=true)
	private UvFlowService uvFlowService;
	@Autowired(required=true)
	private FlowBaseMapper flowBaseMapper;
	@Autowired(required=true)
	private FlowBaseOtherMapper flowBaseOtherMapper;
	@Autowired(required=true)
	private FlowOtherFlowMapper flowOtherFlowMapper;
	@Autowired(required=true)
	private FlowBaseDocMapper flowBaseDocMapper;
	@Autowired(required=true)
	private TbprisonerBaseinfoMapper tbprisonerBaseinfoMapper;
	@Autowired(required=true)
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Autowired(required=true)
	private FlowDeliverMapper flowDeliverMapper;
	@Autowired(required=true)
	private SignatureFlowService signatureFlowService;
	
	/**
	 * 批量操作(同意，退回，否决)
	 * yanquai
	 */
	@Override
	public String piLiangCaoZuo(HttpServletRequest request) {
		String returnValue = "success";
		String docidArr = request.getParameter("docidArr")==null?"":request.getParameter("docidArr");//需要操作的doc主键
		String state = request.getParameter("state")==null?"":request.getParameter("state");//yes:同意 no:否决 back:退回
		if(!"".equals(docidArr)){
			String[] docidlist = docidArr.split(",");
			for(int i=0;i<docidlist.length;i++){
				if("success".equals(returnValue)){
					returnValue = this.approveUpdate(request, docidlist[i], state);
				}else{
					returnValue = "流程更新过程有错！";
				}
			}
		}
		return returnValue;
	}
	
	
	/**
	 * 获取当前时间 （yyyy-MM-dd hh:mm:ss）
	 */
	public String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 操作附件表流程
	 * yanqutai
	 */
	public String approveUpdate(HttpServletRequest request,String docid,String state){
		//流程
		String returnValue = "success";
		SystemUser user = ControllerBase.getLoginUser(request);//当前登陆用户对象
		KhjcTbflowDeliver pojo = new KhjcTbflowDeliver();
		if(null != docid && !"".equals(docid)){
			KhjcTbflowBaseDoc docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
			docpojo.setUpdatemender(user.getUserid());//更新用户
			docpojo.setUpdatetime(this.getNowDate());
			
			//获取下一级流程
			pojo = this.getNodeId(docpojo.getFlowdefid(),docpojo.getFlowdeforderby(), state);
			if(null != pojo){
				docpojo.setFlowdeforderby(pojo.getOrderby());
			}
			
			khjcTbflowBaseDocMapper.updateByPrimaryKey(docpojo);//更新操作
		}
		return returnValue;
	}
	/**
	 * 获取流程节点
	 * yanqutai
	 * @return
	 */
	public KhjcTbflowDeliver getNodeId(String flowdefid,String orderby,String state){
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		KhjcTbflowDeliver pojo = new KhjcTbflowDeliver();
		KhjcTbflowDeliverKey key = new KhjcTbflowDeliverKey();
		key.setDepartid(provincecode);
		key.setFlowdefid(flowdefid);
		if("back".equals(state)){
			key.setOrderby(Integer.valueOf(orderby)-1+"");
		}else if("yes".equals(state)){
			key.setOrderby(Integer.valueOf(orderby)+1+"");
		}else if ("no".equals(state)){
			key.setOrderby("1");
		}
		pojo = khjcTbflowDeliverMapper.selectByPrimaryKey(key);
		return pojo;
	}

	/**
	 * 获取附件表大字段
	 */
	public String getDocContentByDocid(HttpServletRequest request) {
		String docid = request.getParameter("docid")== null?"":request.getParameter("docid");
		String doccontent = "";
		if(!"".equals(docid)){
			KhjcTbflowBaseDoc docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
			doccontent = docpojo.getDocconent();//大字段
		}
		return doccontent;
	}
	
	/**
	 * 更新附件表表大字段
	 */
	public String updateDocByDocid(HttpServletRequest request) {
		String docid = request.getParameter("docid")== null?"":request.getParameter("docid");
		String doccontent = request.getParameter("doccontent")== null?"":request.getParameter("doccontent");
		String returnValue = "success";
		if(!"".equals(docid) && !"".equals(doccontent)){
			KhjcTbflowBaseDoc docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
			docpojo.setDocconent(doccontent);//大字段
			khjcTbflowBaseDocMapper.updateByPrimaryKeySelective(docpojo);
		}else{
			returnValue = "主键或者生成的大字段为空！";
		}
		return returnValue;
	}
	/**
	 * 获取签章方案
	 */
	public String getSingaBySingaid(HttpServletRequest request) {
		String singaid = request.getParameter("singaid")== null?"":request.getParameter("singaid");
		String signatureinfor = "";
		if(!"".equals(singaid)){
			SignatureScheme signapojo = signatureSchemeMapper.getById(Integer.valueOf(singaid));
			signatureinfor = signapojo.getScheme();//签章方案
		}
		return signatureinfor;
	}
	
	/**
	 * 功能描述：通过一个Code类型获取Code值
	 */
	public List ajaxKhjcGetCode(HttpServletRequest request) {
		String codeType = request.getParameter("codeType")==null?"":request.getParameter("codeType");
		return khjcCodeMapper.selectValueByCodeType(codeType);
	}
	
	
	/**
	 * 查询序列
	 * yanqutai
	 */
	public String getSeqBySeqname(String name) {
		HashMap map = new HashMap();
		map.put("sql", "select "+name+".nextval as id from dual");
		Map<String, Object> seqmap = khjcCriminalScoreSdMapper.getSeq(map);
		String seq_applyid =  seqmap.get("ID").toString();
		return seq_applyid;
	}
	
	
	/**
	 * 保存或更新大字段
	 * yanqutai
	 * saveType:保存/更新(save/update)，depType:罪犯相关/部门相关(crim/dep)，map:页面数据，content:大字段
	 */
	public Map saveOrUpdateKhjcFlowBaseDoc(String saveType,String depType,Map<String,String> map,HttpServletRequest request){
		String content = request.getParameter("content")==null?"":request.getParameter("content");//大字段
		String flowdefid = request.getParameter("flowdefid")==null?"":request.getParameter("flowdefid");//流程ID
		String templetid = request.getParameter("templetid")==null?"":request.getParameter("templetid");//表单id
		String returnValue = "success";
		String returnId = "";
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDoc docpojo = new KhjcTbflowBaseDoc();//附件表对象
		String id = this.getSeqBySeqname("SEQ_KHJC_TBFLOW_BASE_DOC_ID");
		SystemUser user = ControllerBase.getLoginUser(request);//当前登陆用户对象
		String userid = "";
		if(null != content && !"".equals(content)){
			if("save".equals(saveType)){
				returnId = id;
				//保存附件表
				docpojo.setDocid(id);//主键
				docpojo.setTempletid(templetid);//模板id
				docpojo.setDocconent(content);//大字段
				//拼接附件描述(罪犯xxx的xxxx)
				if(null != map.get("crimid")){
					TbprisonerBaseinfo crime = prisonerService.getBasicInfoByCrimid(map.get("crimid"));
					TbsysTemplate template = systemModelService.getTemplateAndDepartid(templetid,user.getDepartid());
					docpojo.setCrimid(map.get("crimid").toString());
					docpojo.setContent("罪犯"+crime.getName()+"的"+template.getTempname());//附件描述
				}
				docpojo.setFlowdefid(flowdefid);//流程名称
				docpojo.setFlowdeforderby("1");//流程顺序 保存直接到起始位
				docpojo.setDocdep(map.get("docdep"));//具体的单位ID(罪犯所在部门iD或申请人所在部门ID)
				docpojo.setDepartid(map.get("departid"));//监狱ID
				docpojo.setDelflag("0");//删除标志位(0有效 1删除)
				docpojo.setIssee("1");//是否查看标志位(0未查看 1已查看)
				docpojo.setCreatetime(this.getNowDate());//创建时间
				docpojo.setCreatemender(user.getUserid());//创建人
				
				khjcTbflowBaseDocMapper.insert(docpojo);//插入操作
			}else if("update".equals(saveType)){
				//更新附件表
				String docid = request.getParameter("docid");//附件表主键
				returnId = docid;
				if(null != docid && !"".equals(docid)){
					docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
					docpojo.setDocconent(content);//更新附件信息
					docpojo.setUpdatemender(user.getUserid());//更新用户
					docpojo.setUpdatetime(this.getNowDate());
					khjcTbflowBaseDocMapper.updateByPrimaryKeySelective(docpojo);//更新操作
				}
			}
		}else{
			returnValue = "未生成表单大字段信息！";
		}
		
		rertunMap.put("state", returnValue);
		rertunMap.put("ID",returnId);
		return rertunMap;
	}
	
	
	/**
	 * 提交附件(提交只更新附件表状态，返回map(附件表的主键，成功状态))
	 * @param saveType
	 * @param depType
	 * @param map
	 * @param request
	 * @return
	 */
	public Map approveKhjcFlowBaseDoc(String saveType,String depType,Map<String,String> map,HttpServletRequest request){
		String content = request.getParameter("content")==null?"":request.getParameter("content");//大字段
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid").toString();//附件表主键
		
		String returnValue = "success";
		String returnId = "";
		Map rertunMap = new HashMap<String, String>();
		KhjcTbflowBaseDoc docpojo = new KhjcTbflowBaseDoc();//附件表对象
		String id = this.getSeqBySeqname("SEQ_KHJC_TBFLOW_BASE_DOC_ID");
		SystemUser user = ControllerBase.getLoginUser(request);//当前登陆用户对象
		String userid = "";
		if(null != content && !"".equals(content)){
			if("approve".equals(saveType) && null != docid && !"".equals(docid)){
				//更新附件表
				KhjcTbflowDeliver pojo = new KhjcTbflowDeliver();
				String state = request.getParameter("state");//提交状态 同意(yes) 否决(no) 退回(back)
				if(null != docid && !"".equals(docid)){
					//通过附件id找到记录
					docpojo = khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
					docpojo.setDocconent(content);//更新附件信息
					docpojo.setUpdatemender(user.getUserid());//更新用户
					docpojo.setUpdatetime(this.getNowDate());
					//获取下一级流程
					pojo = this.getNodeId(docpojo.getFlowdefid(),docpojo.getFlowdeforderby(), state);
					if(null != pojo){
						docpojo.setFlowdeforderby(pojo.getOrderby());
					}
					khjcTbflowBaseDocMapper.updateByPrimaryKeySelective(docpojo);//更新操作
				}
			}
		}else{
			returnValue = "未生成表单大字段信息！";
		}
		
		rertunMap.put("state", returnValue);
		rertunMap.put("ID",returnId);
		return rertunMap;
	}
	
	/**
	 * 判断某流程的操作之前一些文书是否已制作
	 * 如果未制作返回ismaked=0, 		status中返回提示信息
	 */
	@Override
	public Map<String,Object> isPapersMaked(Map<String,Object> map,SystemUser user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String departid = user.getDepartid();
		
		//根据当前用户、流程定议ID，操作类型		获取必须存在表单的表单ID(在TBFLOW_DELIVER的text5中配制)
		String operateType = StringNumberUtil.returnString2(map.get("operateType"));
		if ("yes".equalsIgnoreCase(operateType) || "no".equalsIgnoreCase(operateType) || "back".equalsIgnoreCase(operateType)){
			String isExistTempids = getMastExistTempidOnFlow(map, user);
			if(StringNumberUtil.notEmpty(isExistTempids)){
				//某表单不存在，则返回提示信息
				String flowdraftids = map.get("flowdraftids")==null ?"":map.get("flowdraftids").toString();
				if(StringNumberUtil.isEmpty(flowdraftids)){
					resultMap.put("ismaked", "0");
					resultMap.put("status", "请先保存主文书，再做其它文书！");
					return resultMap;
				}
				resultMap = isExistClobByTempids(flowdraftids, departid, isExistTempids, resultMap);
			}
		}
		
		return resultMap;
		
	}
	
	
	
	//将表单的数据封装成Map
	public Map<String,Object> parseFormMap(Map<String,Object> map,SystemUser user){
		String noteinfo = map.get("noteinfo")==null?"":map.get("noteinfo").toString();//表单数据信息
		if (StringNumberUtil.notEmpty(noteinfo)){
			Map<String,Object> formDatamap = MapUtil.parseJSONArray2Map(noteinfo);
			//将popup_开头节点的表单数据处理一下。(popup_的节点为弹出框)
			Set<String> set = formDatamap.keySet();
			int length = "popup_".length();
			Map<String,Object> tempMap = new HashMap<String,Object>();
			for(String key : set ){
				if(key.startsWith("popup_")){
					String tKey = key.substring(length);
					Object tValue = formDatamap.get(key);
					tempMap.put(tKey, tValue);
				}
			}
			formDatamap.putAll(tempMap);
			
			//
			map.putAll(formDatamap);
			map.remove("noteinfo");
			map.put("orgid", user.getOrgid());
			map.put("departid", user.getDepartid());
		}
		
		return map;
	}
	
	
	public void dealFlowFjquser(Map<String,Object> map){
		String fjquser = map.get("fjquser") == null?"":map.get("fjquser").toString();
		if(StringNumberUtil.notEmpty(fjquser)){
			Map<String,Object> paramap = new HashMap<String,Object>();
			paramap.put("flowdraftid", map.get("flowdraftid"));
			paramap.put("userid", fjquser);
			uvFlowService.updateFjqUser(paramap);
		}
	}
	
	private void dealApplyidAndApplyname(SystemUser user, UvFlow uvflow, Map<String,Object> map){
		//处理applyid、applyname
		if(StringNumberUtil.isEmpty(map.get("applyid"))){
			if(null != uvflow){
				map.put("applyid", uvflow.getApplyid());
				map.put("applyname", uvflow.getApplyname());
			}else{
				map.put("applyid", user.getUserid());
				map.put("applyname", user.getName());
			}
		}else{
			if(StringNumberUtil.isEmpty(map.get("applyname"))){
				TbprisonerBaseinfo tbprisonerBaseinfo = tbprisonerBaseinfoMapper.selectByPrimaryKey(map.get("applyid").toString());
				if(null!=tbprisonerBaseinfo){
					map.put("applyname", tbprisonerBaseinfo.getName());
				}
			}
		}
	}
	
	
	/**
	 * 公共的操作方法: 保存,提交,退回,拒绝 
	 * * 成功则返回：rows 保存的条数=1, flowdraftid
	 *   失败则返回：rows 保存的条数=0, status 失败的描述
	 */
	@Transactional
	public Object saveBaseDoc(Map<String,Object> map,SystemUser user){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", "0");
		resultMap.put("status", "操作失败！");
		
		//将表单的数据封装成Map
		map = parseFormMap(map,user);
		
		UvFlow uvflow = null;
		if(StringNumberUtil.notEmpty(map.get("flowdraftid"))){
			uvflow = uvFlowService.getFlowByFlowdraftid(map.get("flowdraftid").toString());
		}
		String departid = uvflow==null?user.getDepartid():uvflow.getDepartid();//数据的最原始单位ID
		String currnodeid = uvflow==null?"0":uvflow.getNodeid();//当前操作人的节点ID
		map.put("currnodeid", currnodeid);
		map.put("departid", departid);
		//
		dealApplyidAndApplyname(user, uvflow, map);
		
		//tbflow_base字段text12中存有流程结束的nodeid,如果当前节点与text12值 相同，且操作类型为yes时，则流程审批结束,可设freeDnodeid=1
		String text12 = uvflow==null?"":uvflow.getText12();
		if(StringNumberUtil.notEmpty(text12)){
			text12 = text12.trim();
			String operateType = map.get("operateType")==null?"":map.get("operateType").toString().trim();
			if(text12.equals(currnodeid) && "yes".equals(operateType)){
				map.put("freeDnodeid", "1");//设置freeDnodeid，让其提前结否流程
			}
		}
				
		//操作流程：只有主表单(masterslave=master)可以操作流程；
		//操作流程，也同时保存大字段，所以后面不需要在操作大字段
		String masterslave = map.get("masterslave")==null?"":map.get("masterslave").toString();
		if(StringNumberUtil.notEmpty(masterslave)&&"master".equals(masterslave.trim())){
			map.put("newcompount", "1");
			resultMap = uvFlowService.operationFlowByParam(user, map, uvflow); 
			if(StringNumberUtil.notEmpty(resultMap.get("state"))&&"1".equals(resultMap.get("state").toString())){
				map.put("flowdraftid", resultMap.get("flowdraftid"));
			}else{
				throw new RuntimeException();
			}
		}
		
		dealFlowFjquser(map);
		
		if(true){//StringNumberUtil.notEmpty(masterslave)&&"slave".equals(masterslave.trim())
			String tempid = map.get("tempid")==null?"":map.get("tempid").toString();
			String docconent = map.get("docconent")==null?"":map.get("docconent").toString();
			String flowdefid = map.get("flowdefid")==null?"":map.get("flowdefid").toString();
			String flowdraftid = map.get("flowdraftid")==null?"":map.get("flowdraftid").toString();
			String suggesttime = map.get("suggesttime")==null?"":map.get("suggesttime").toString();
			
			//日期封装到临时字段中原因(saveFormData参数个数写死了，并且很多地方调用这个方法，不能随便改，封装临时字段不会影响结果)
			user.setText1(suggesttime);
			JSONMessage message = saveFormData(tempid,docconent,flowdefid,flowdraftid,user);
			if(1 != message.getStatus()){
				throw new RuntimeException();
			}
		}
		
		//通过查询方案保存数据
		Object solutionid = map.get("solutionid");
		if(StringNumberUtil.notEmpty(solutionid)){
			solutionService.save(map, user);
		}
		
		//操作流程流水表-保存签章和批注的个数。批量操作时用于作判断。
		Object tempid = map.get("tempid");
		if(StringNumberUtil.notEmpty(tempid)){
			saveSealFlow(user,map,currnodeid);
		}
		
		//特殊处理
		specialFlowDeal(map, user);
		
		resultMap.put("rows", "1");
		resultMap.put("status", "操作成功！");
		if(StringNumberUtil.notEmpty(map.get("flowdraftid"))&&StringNumberUtil.isEmpty(resultMap.get("flowdraftid"))){
			resultMap.put("flowdraftid", map.get("flowdraftid"));
		}
		

		//执行额外的存储过程，此存储过程在tbflow_deliver表中维护。
		exeExtraProcedure(currnodeid, user, map);
		
		return resultMap;
	}
	
	
	private void saveSealFlow(SystemUser user, Map<String,Object> map, String currnodeid){
		String operateType = StringNumberUtil.returnString2(map.get("operateType"));// 判断新增、修改
		map.put("departid", user.getDepartid());
		SignatureScheme sgScheme = signatureSchemeMapper.getCurrnodeidSignatureScheme(map);
		Integer requaresealnum = sgScheme == null ? 0 : sgScheme.getRequaresealnum();
		signatureFlowService.operateSignatureFlow(map, operateType, currnodeid, requaresealnum);
	}
	
	/**
	 * 执行额外的存储过程，此存储过程在tbflow_deliver表中维护。
	 * @param currnodeid
	 * @param user
	 * @param map
	 */
	public void exeExtraProcedure(String currnodeid, SystemUser user, Map<String,Object> map){
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("snodeid", currnodeid);
		paramap.put("departid", user.getDepartid());
		paramap.put("flowdefid", map.get("flowdefid"));
		String operateType = StringNumberUtil.returnString2(map.get("operateType"));// 判断新增、修改
		if(StringNumberUtil.notEmpty(operateType)){
			if("yes".equals(operateType) || "back".equals(operateType) || "no".equals(operateType)){
				if("yes".equals(operateType.trim())){
					paramap.put("instate", "yes");
				}else if("back".equals(operateType.trim())){
					paramap.put("state", 2);
				}else if("no".equals(operateType.trim())){
					paramap.put("state", -1);
				}
				FlowDeliver flowDeliver = flowDeliverMapper.findByParamMap(paramap);
				String procedure = flowDeliver.getText7();
				if(StringNumberUtil.notEmpty(procedure)){
					//textProcedure(applyid,flowdraftid)
					int beginIndex = procedure.indexOf("(");
					int endIndex = procedure.indexOf(")");
					
					String procedurename = procedure;
					String paraValue = "";
					if(beginIndex != -1 && endIndex != -1){
						procedurename = procedure.substring(0,beginIndex);
						String paraStr = procedure.substring(beginIndex + 1, endIndex);
						if(StringNumberUtil.notEmpty(paraStr)){
							String[] paraArr = paraStr.split(",");
							for(int i=0; i<paraArr.length; i++){
								String para = paraArr[i].trim();
								if(map.get(para)==null){
									paraValue += "null"+",";
								}else{
									paraValue += "'"+map.get(para).toString().trim() + "',";
								}
							}
						}
					}
					
					paraValue = StringNumberUtil.removeLastStr(paraValue, ",");
					String procedureStr = "CALL " + procedurename + "(" + paraValue + ")";
					flowBaseMapper.exeProcdeure(procedureStr);
					
				}
			}
		}
	}
	
	
	
	
	/**
	 * 特殊处理
	 * @param map
	 * @param user
	 */
	public void specialFlowDeal(Map<String,Object> map, SystemUser user){
		String bizName = map.get("bizName")==null?"":map.get("bizName").toString().trim();
		if(StringNumberUtil.notEmpty(bizName)){
			String[] bizNameArr = bizName.split("\\.");
			int result = this.excuteServiceMethod(bizNameArr[0], bizNameArr[1], map, user);
			if(result == -1 || result == -2){
				throw new RuntimeException();
			}
		}
	}
	
	public String getMastExistTempidOnFlow(Map<String,Object> map, SystemUser user){
		
		String state = "";
		String operateType = StringNumberUtil.returnString2(map.get("operateType"));
		if ("yes".equalsIgnoreCase(operateType)){
			state = "0,1";
		}else if ("no".equalsIgnoreCase(operateType)){
			state = "-1";
		}else if ("back".equalsIgnoreCase(operateType)){
			state = "2";
		}
		
		map.put("state", state);
		map.put("userid", user.getUserid());
		map.put("departid", user.getDepartid());
		return flowBaseMapper.getMastExistTempidOnFlow(map);
	}
	
	
	public Map<String,Object> isExistClobByTempids(String flowdraftids, String departid,String isExistTempids, Map<String,Object> resultMap){
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", departid);
		List<String> tempidList = StringNumberUtil.formatString2List(isExistTempids, ",");
		StringBuffer statusSB = new StringBuffer();
		
		List<String> flowdraftidList = StringNumberUtil.formatString2List(flowdraftids, ",");
		for(String flowdraftid : flowdraftidList){
			StringBuffer singleStatusSB = new StringBuffer();
			paramap.put("flowdraftid", flowdraftid);
			for(String existClobTempid : tempidList){
				paramap.put("tempid", existClobTempid);
				String isExist = flowBaseOtherMapper.isExistClobByMap(paramap);
				if(StringNumberUtil.isEmpty(isExist) || "0".equals(isExist)){
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("tempid", existClobTempid);
					map.put("departid", departid);
					TbsysTemplate sysTemp = tbsysTemplateMapper.getTemplateDetail(map);
					singleStatusSB.append("《"+sysTemp.getTempname()).append("》、");
					resultMap.put("ismaked", "0");
				}
			}
			
			String status = singleStatusSB.toString();
			if(StringNumberUtil.notEmpty(status)){
				status = StringNumberUtil.removeLastStr(status, "、");
				FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(paramap);
				statusSB.append(fb.getApplyname()).append( "的").append(status).append( "等文书未制作，请先制作文书！（可通过保存或批量签章等方式生成文书）");
			}
		}
		
		resultMap.put("status", statusSB.toString());
		
		return resultMap;
		
	}
	
	
	@Override
	@Transactional
	public JSONMessage saveProvinceCommuteLiAnData(Map<String,String> map,SystemUser user){
		JSONMessage msg = JSONMessage.newMessage();
		String ids = map.get("ids");
		if(StringNumberUtil.isEmpty(ids)){
			msg.setInfo("请至少选中一条记录！");
			return msg;
		}
		
		String[] crimidArr = ids.split(",");
		if(null==crimidArr||crimidArr.length<=0){
			msg.setInfo("请至少选中一条记录！");
			return msg;
		}
		//拼接附件描述
		String templetid = map.get("templetid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(templetid,user.getDepartid());
		//查最大案件号
		int caseno = tbxfCommutationService.getMaxCommuteCaseNo(map);
		map.put("createmender", user.getUserid());
		map.put("updatemender", user.getUserid());
		for(String crimid:crimidArr){
			TbprisonerBaseinfo crime = prisonerService.getBasicInfoByCrimid(crimid);
			map.put("content","罪犯"+crime.getName()+"的"+template.getTempname());
			map.put("crimid", crimid);
			map.put("criminalname", crime.getName());
			map.put("caseno", caseno+"");
			//往主附件表里save一条数据
			Map returnMap = publicBaseDocService.saveFormDoc(map, user);
			int rows = returnMap.get("rows")==null?0:Integer.parseInt(returnMap.get("rows").toString());
			if(rows <= 0){
				msg.setInfo(returnMap.get("status").toString());
				throw new RuntimeException();
			}
			//往业务表里（TBXF_COMMUTATION）插入一条数据
			map.put("docid", returnMap.get("docid").toString());
			int row = tbxfCommutationService.saveTbxfCommutationSensitive(map);
			if(row <= 0){
				msg.setInfo("操作失败！");
				throw new RuntimeException();
			}
			caseno ++;
		}
		
		msg.setSuccess();
		return msg;
	}
	
	@Override
	@Transactional
	public JSONMessage saveProvinceOutExecuteLiAnData(Map<String,String> map,SystemUser user){
		JSONMessage msg = JSONMessage.newMessage();
		String ids = map.get("ids");
		if(StringNumberUtil.isEmpty(ids)){
			msg.setInfo("请至少选中一条记录！");
			return msg;
		}
		
		String[] crimidArr = ids.split(",");
		if(null==crimidArr||crimidArr.length<=0){
			msg.setInfo("请至少选中一条记录！");
			return msg;
		}
		//拼接附件描述
		String templetid = map.get("templetid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(templetid,user.getDepartid());
		//查最大案件号
		int caseno = tbxfOutExecuteService.getMaxOutExecuteCaseNo(map);
		map.put("createmender", user.getUserid());
		map.put("updatemender", user.getUserid());
		for(String crimid:crimidArr){
			TbprisonerBaseinfo crime = prisonerService.getBasicInfoByCrimid(crimid);
			map.put("content","罪犯"+crime.getName()+"的"+template.getTempname());
			map.put("crimid", crimid);
			map.put("criminalname", crime.getName());
			map.put("caseno", caseno+"");
			//往主附件表里save一条数据
			Map returnMap = publicBaseDocService.saveFormDoc(map, user);
			int rows = returnMap.get("rows")==null?0:Integer.parseInt(returnMap.get("rows").toString());
			if(rows <= 0){
				msg.setInfo(returnMap.get("status").toString());
				throw new RuntimeException();
			}
			//往业务表里（TBXF_OUTEXECUTE）插入一条数据
			map.put("docid", returnMap.get("docid").toString());
			int row = tbxfOutExecuteService.saveTbxfOutExecuteSensitive(map);
			if(row <= 0){
				msg.setInfo("操作失败！");
				throw new RuntimeException();
			}
			caseno ++;
		}
		
		msg.setSuccess();
		return msg;
	}
	
	/**
	 * 描述：保存审批的表单大字段
	 * @author YangZR 2015-03-01
	 */
	@Override
	@Transactional
	public JSONMessage saveFormData(String tempid,String docconent,String flowdefid,String flowdraftid,SystemUser user){
		JSONMessage message = JSONMessage.newMessage();
		if (flowdefid != null){
			if(flowdefid.contains("other_")){
				//保存表单大字段
				message = saveOtherFlowDocconent(tempid,docconent,flowdraftid,user);
			}else if(flowdefid.contains("doc_")){
				//保存普通审批流程的表单大字段
				message = saveDocFlowDocconent(tempid,docconent,flowdraftid,user);
			}else if(flowdefid.contains("arch_")){
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("tempid", tempid);
				paramMap.put("flowdefid", flowdefid);
				paramMap.put("flowdraftid", flowdraftid);
				//保存审批电子档案的表单大字段
//				FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(paramMap);
				message = saveArchFlowDocconent(tempid,docconent,flowdefid,flowdraftid,user);
			}
		}
		
		return message;
	}
	
	//保存other_的流程的表单大字段
	public JSONMessage saveOtherFlowDocconent(String tempid,String docconent,String flowdraftid,SystemUser user){
		JSONMessage jmg = JSONMessage.newMessage();
		int row = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flowdraftid", flowdraftid);
		map.put("tempid", tempid);
		map.put("opid", user.getUserid());
		map.put("optime", new Date());
		
		FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
		FlowBaseOther flowBaseOther = new FlowBaseOther();
		
		//通过tempid，flowdraftid查询表TBFLOW_BASE_OTHER是否存在记录，存在则返回otherid
		String otherid = flowBaseOtherMapper.getLastOtheridByFlowdraftid(map);
		//如果存在则更新TBFLOW_BASE_OTHER，TBFLOW_OTHER_FLOW
		if(StringNumberUtil.notEmpty(otherid)){
			char[] c = docconent.toCharArray();
			map.put("docconent", new String(c));
			map.put("otherid", otherid);
			map.put("text6", user.getText1());
			
			
			row = flowBaseOtherMapper.updateByOtherid(map);
			if(row <=0){
				throw new RuntimeException();
			}
			
			// 更新other关系表
			FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(map);
			if(null!=fb&&StringNumberUtil.notEmpty(fb.getFlowid())){
				flowOtherFlow.setFlowid(fb.getFlowid());
			}
			flowOtherFlow.setFlowdraftid(flowdraftid);
			flowOtherFlow.setOpid(user.getUserid());
			flowOtherFlow.setOptime(new Date());
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setTempid(tempid);
			
			row = flowOtherFlowMapper.updateByCondition2(flowOtherFlow);
			if(row <=0){
				throw new RuntimeException();
			}
		}else{
			//如果不存在则插入TBFLOW_BASE_OTHER，TBFLOW_OTHER_FLOW
			otherid = flowBaseOtherMapper.getOtherId(user.getDepartid());
			flowBaseOther.setOtherid(otherid);
			flowBaseOther.setDocconent(docconent);
			flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
			flowBaseOther.setOpid(user.getUserid());
			flowBaseOther.setOptime(new Date());
			flowBaseOther.setText6(user.getText1());
			row = flowBaseOtherMapper.insert(flowBaseOther);
			if(row <=0){
				throw new RuntimeException();
			}
			
			//保存流程与和流程相关的其他文档关系信息表
			FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(map);
			if(null!=fb&&StringNumberUtil.notEmpty(fb.getFlowid())){
				flowOtherFlow.setFlowid(fb.getFlowid());
			}
			flowOtherFlow.setFlowdraftid(flowdraftid);
			flowOtherFlow.setOpid(user.getUserid());
			flowOtherFlow.setOptime(new Date());
			flowOtherFlow.setOtherid(otherid);
			flowOtherFlow.setTempid(tempid);
			
			row = flowOtherFlowMapper.insert(flowOtherFlow);
			if(row <=0){
				throw new RuntimeException();
			}
		}
		
		if(row==1){
			jmg.setSuccess();
			jmg.setInfo("操作成功！");
		}
		return jmg;
		
	}
	
	//保存普通流程的表单大字段
	public JSONMessage saveDocFlowDocconent(String tempid,String docconent,String flowdraftid,SystemUser user){
		
		JSONMessage jmg = JSONMessage.newMessage();
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tempid", tempid);
		paramMap.put("flowdraftid", flowdraftid);
		
		int row = 0;
		// 设置值
		FlowBaseDoc flowBaseDoc = new FlowBaseDoc();
		FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(paramMap);
		if(null!=fb&&StringNumberUtil.notEmpty(fb.getFlowid())){
			flowBaseDoc.setFlowid(fb.getFlowid());
		}
		flowBaseDoc.setDocconent(docconent);
		flowBaseDoc.setFlowdraftid(flowdraftid);
		flowBaseDoc.setDepartid(user.getDepartid());
		flowBaseDoc.setOpid(user.getUserid());
		flowBaseDoc.setOptime(new Date());
		flowBaseDoc.setOthercodeid(tempid);
		
		FlowBaseDoc fbd = flowBaseDocMapper.findLastDocByflowdraftid(paramMap);
		if(null!=fbd && StringNumberUtil.notEmpty(fbd.getBaseid())){
			// 更新
			flowBaseDoc.setBaseid(fbd.getBaseid());
			row = flowBaseDocMapper.updateByCondition(flowBaseDoc);
		}else{
			row = flowBaseDocMapper.insert(flowBaseDoc);
		}
		if(row !=1){
			throw new RuntimeException();
		}else{
			jmg.setSuccess();
			jmg.setInfo("操作成功！");
		}
		return jmg;
	}
	
	//保存电子档桉流程的表单大字段
	public JSONMessage saveArchFlowDocconent(String tempid,String docconent,String flowdefid,String flowdraftid,SystemUser user){
		
		JSONMessage message = JSONMessage.newMessage();
		int row = 0;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tempid", tempid);
		paramMap.put("flowdraftid", flowdraftid);
		FlowBase fb = flowBaseMapper.getFlowBaseByFlowdraftid(paramMap);
		if(null!=fb&&StringNumberUtil.notEmpty(fb.getFlowid())){
			row = uvFlowService.saveFlowArchives(docconent,fb.getFlowid(),fb.getFlowdefid(),fb.getApplyid(),user);
		}
		
		if(row==1){
			message.setSuccess();
			message.setInfo("操作成功！");
		}
		
		return message;
		
	}
}
