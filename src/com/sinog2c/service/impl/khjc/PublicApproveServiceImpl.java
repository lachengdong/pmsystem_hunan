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
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowDeliverMapper;
import com.sinog2c.dao.api.khjc.KhjccriminalmonthscoresdMapper;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.khjc.KhjcCode;
import com.sinog2c.model.khjc.KhjcCriminalScoreSd;
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
import com.sinog2c.mvc.controller.khjc.khjcBoFenController;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.PublicApproveService;
import com.sinog2c.service.api.khjc.PublicToolMethodService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Service("publicApproveService")
public class PublicApproveServiceImpl extends ControllerBase implements PublicApproveService{
	@Resource
	private PublicToolMethodService publicToolMethodService;
	@Autowired
	private KhjcTbflowBaseDocMapper khjcTbflowBaseDocMapper;
	
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
		SystemUser user = getLoginUser(request);//当前登陆用户对象
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
					docpojo.setUpdatetime(publicToolMethodService.getNowDate());
					//获取下一级流程
					pojo = publicToolMethodService.getNodeId(docpojo.getFlowdefid(),docpojo.getFlowdeforderby(), state);
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
	

}
