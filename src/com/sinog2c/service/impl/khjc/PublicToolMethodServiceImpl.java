package com.sinog2c.service.impl.khjc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.khjc.KhjcCodeMapper;
import com.sinog2c.dao.api.khjc.KhjcCriminalScoreSdMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.khjc.KhjcTbflowDeliver;
import com.sinog2c.model.khjc.KhjcTbflowDeliverKey;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.PublicToolMethodService;
import com.sinog2c.util.common.StringNumberUtil;

@Service("publicToolMethodService")
public class PublicToolMethodServiceImpl extends ControllerBase implements PublicToolMethodService{
	@Autowired
	private KhjcCriminalScoreSdMapper khjcCriminalScoreSdMapper;
	@Autowired
	private KhjcTbflowDeliverMapper khjcTbflowDeliverMapper;
	@Autowired
	private KhjcCodeMapper khjcCodeMapper;
	@Autowired
	private SignatureSchemeMapper signatureSchemeMapper;
	
	
	/**
	 * 获取当前时间 （yyyy-MM-dd hh:mm:ss）
	 */
	public String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 获取流程节点
	 * 参数(流程id,当前流程排序,退回/通过/拒绝)
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
	 * 获取签章方案
	 */
	public String getSingaBySingaid(String singaid) {
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
	 * 将页面信息放入到Map中
	 * yanqutai
	 */
	public Map getNoteinfoMap(String noteinfo){
		Map<String,String> map = null;
		if (StringNumberUtil.notEmpty(noteinfo)) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				map = (Map<String, String>)list.get(0);
			}
		}
		return map;
	}
	
}
