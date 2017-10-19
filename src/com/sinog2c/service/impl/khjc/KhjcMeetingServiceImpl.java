package com.sinog2c.service.impl.khjc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.dao.api.khjc.KhjcCodeMapper;
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcMeetingInfoMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.khjc.KhjccriminalmonthscoresdMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.khjc.KhjcCode;
import com.sinog2c.model.khjc.KhjcCriminalScoreSd;
import com.sinog2c.model.khjc.KhjcMeetingInfo;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.khjc.KhjcTbflowDeliver;
import com.sinog2c.model.khjc.KhjcTbflowDeliverKey;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.KhjcMeetingService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("khjcMeetingService")
public class KhjcMeetingServiceImpl extends ControllerBase implements KhjcMeetingService{
	@Resource
	private SystemModelService systemModelService;
	@Autowired
	private KhjcCriminalScoreSdMapper khjcCriminalScoreSdMapper;
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	@Autowired
	private KhjcTbflowDeliverMapper khjcTbflowDeliverMapper;
	@Autowired
	private KhjcMeetingInfoMapper khjcMeetingInfoMapper;
	@Autowired
	private KhjcCodeMapper khjcCodeMapper;
	@Autowired
	private SignatureSchemeMapper signatureSchemeMapper;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Override
	
	/**
	 * 批量操作(同意，退回，否决)
	 * yanquai
	 */
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
		SystemUser user = getLoginUser(request);//当前登陆用户对象
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
	 * 保存会议记录
	 * yanqutai
	 */
	public String saveMeetingInfo(HttpServletRequest request) {
		String returnValue = "success";
		String operateType = request.getParameter("operateType");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");//表单数据信息
		Map<String,String> map = this.getNoteinfoMap(noteinfo);
		//保存附件表信息，并返回附件表的主键
	    Map docmap = this.saveOrUpdateKhjcFlowBaseDocOther(operateType, map, request);
		return returnValue;
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
	 * 保存或更新大字段
	 * yanqutai
	 * saveType:保存/更新(save/update)，depType:罪犯相关/部门相关(crim/dep)，map:页面数据，content:大字段
	 */
	public Map saveOrUpdateKhjcFlowBaseDocOther(String saveType,Map<String,String> map,HttpServletRequest request){
		String content = request.getParameter("content")==null?"":request.getParameter("content");//大字段
		String templetid = request.getParameter("templetid")==null?"":request.getParameter("templetid");//表单id
		String returnValue = "success";
		String returnId = "";
		Map rertunMap = new HashMap<String, String>();
		KhjcMeetingInfo docpojo = new KhjcMeetingInfo();//附件表对象
		String id = this.getSeqBySeqname("SEQ_KHJC_MEETING_INFO_ID");
		SystemUser user = getLoginUser(request);//当前登陆用户对象
		String userid = "";
		if(null != content && !"".equals(content)){
			if("save".equals(saveType)){
				returnId = id;
				//保存附件表
				docpojo.setDocid(id);//主键
				docpojo.setDocconent(content);//大字段
				docpojo.setMeetingdepname(map.get("meetingdepname"));//会议单位
				docpojo.setMeetingtime(map.get("meetingtime"));//会议时间
				docpojo.setMeetingplace(map.get("meetingplace"));//会议地点
				docpojo.setMeetingtype(templetid);//会议类型
				docpojo.setMeetingcontent(map.get("meetingcontent"));//会议事项
				docpojo.setJiluren(map.get("jiluren"));//记录人
				docpojo.setZhuchiren(map.get("zhuchiren"));//主持人
				docpojo.setDocdepartid(map.get("docdep"));//具体的单位ID(罪犯所在部门iD或申请人所在部门ID)
				docpojo.setDepartid(user.getDepartid());//监狱ID
				docpojo.setDelflag("0");//删除标志位(0有效 1删除)
				docpojo.setCreatetime(this.getNowDate());//创建时间
				docpojo.setCreatemender(user.getUserid());//创建人
				
				khjcMeetingInfoMapper.insert(docpojo);//插入操作
			}else if("update".equals(saveType)){
				//更新附件表
				String docid = request.getParameter("docid");//附件表主键
				returnId = docid;
				if(null != docid && !"".equals(docid)){
					docpojo = khjcMeetingInfoMapper.selectByPrimaryKey(docid);
					docpojo.setDocconent(content);//更新附件信息
					docpojo.setUpdatemender(user.getUserid());//更新用户
					docpojo.setUpdatetime(this.getNowDate());
					khjcMeetingInfoMapper.updateByPrimaryKeySelective(docpojo);//更新操作
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
	 * 查询序列
	 * yanqutai
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getSeqBySeqname(String name) {
		HashMap map = new HashMap();
		map.put("sql", "select "+name+".nextval as id from dual");
		Map<String, Object> seqmap = khjcCriminalScoreSdMapper.getSeq(map);
		String seq_applyid =  seqmap.get("ID").toString();
		return seq_applyid;
	}
	
	
	/**
	 * 将页面信息放入到Map中
	 * yanqutai
	 */
	public Map getNoteinfoMap(String noteinfo){
		Map<String,String> map = null;
		if (noteinfo != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map<String, String>)list.get(0);
			}
		}
		return map;
	}
	
	
	/**
	 * 获取当前页面数据（分页）
	 * 
	 * @author yanqutai
	 */
	public List<KhjcMeetingInfo> getKhjcMeetingByType(int pageIndex,int pageSize, String key, 
			String tempid, String crimid, String departid,String sortField, String sortOrder,String nodeid) {
		// 查询数据最小数
		int start = pageIndex * pageSize + 1;
		// 查询数据最大数
		int end = start + pageSize - 1;
		return khjcMeetingInfoMapper.getKhjcMeetingByType(start, end, key, tempid, sortField, sortOrder);
	}
}
