package com.sinog2c.mvc.controller.commutationParole;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.commutationParole.TbxfMergeReference;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.MergeReferenceService;
import com.sinog2c.service.api.system.SystemLogService;

import flexjson.JSONDeserializer;


/**
 * 减刑幅度
 *
 */
@Controller
public class MergeReferenceController extends ControllerBase {
	
	@Resource
	private MergeReferenceService mergeReferenceService;
	
	@Resource
	private SystemLogService logService;
	
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toMergeReferenceListPage")
	public ModelAndView toMergeReferenceListPage(HttpServletRequest request) {
		String refid = request.getParameter("refid");
		String remark = request.getParameter("remark");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("refid",refid);
		ModelAndView mav = null;
		String returnPage = "";
		if(GkzxCommon.ONE.equals(remark)) {
			returnPage = "WEB-INF/JSP/commutationParole/mergeTypeListPage.jsp";
		} else {
			returnPage = "WEB-INF/JSP/commutationParole/mergeReferenceListPage.jsp";
		}
		View view = new InternalResourceView(returnPage);
		mav = new ModelAndView(view, "record", map);
		return mav;
	}
	
	
	/**
	 * 列表数据
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetMergeReferenceList")
	@ResponseBody
	public Object ajaxGetMergeReferenceList(HttpServletRequest request){
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String refid = request.getParameter("refid");
		int total = mergeReferenceService.getCount(refid);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = "MERGEID";
		String sortOrder = "desc";
		List<HashMap> list = mergeReferenceService.getMergeReferenceList(refid, sortField, sortOrder, start, end);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	
	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/saveMergeReference" })
	@ResponseBody
	public String saveMergeReference(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		int refid = Integer.valueOf(request.getParameter("refid"));
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)Decode(json);
		if(null!=rows&&rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				int sno = getIntValue(row.get("SNO"));
				int eno = getIntValue(row.get("ENO"));
				TbxfMergeReference tbxfMergeReference = new TbxfMergeReference();
				tbxfMergeReference.setRefid(refid);
				tbxfMergeReference.setSno(sno);
				tbxfMergeReference.setEno(eno);
				tbxfMergeReference.setOpid(user.getUserid());
				if("added".equals((String)row.get("_state"))){
					int rewardid = getIntValue(row.get("REWARDID"));
					if(rewardid==-1){
						continue;
					}
					tbxfMergeReference.setRewardid(rewardid);
					try {
						mergeReferenceService.insertMergeReference(tbxfMergeReference);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖励合并添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加奖励合并");
					log.setOrgid(user.getUserid());
					log.setRemark("添加奖励合并");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("modified".equals((String)row.get("_state"))){
					int mergeid = getIntValue(row.get("MERGEID"));
					int rewardid = getIntValue(row.get("REWARDNAME"));
					if(rewardid == -1){//没有修改奖励类型
						TbxfMergeReference record = mergeReferenceService.getMergeReferenceById(mergeid);
						tbxfMergeReference.setRewardid(record.getRewardid());
					}else{
						tbxfMergeReference.setRewardid(rewardid);
					}
					tbxfMergeReference.setOptime(new Date());
					tbxfMergeReference.setMergeid(mergeid);
					try {
						mergeReferenceService.updateMergeReference(tbxfMergeReference);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖励合并修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改奖励合并");
					log.setOrgid(user.getUserid());
					log.setRemark("修改奖励合并");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("removed".equals((String)row.get("_state"))){
					int mergeid = getIntValue(row.get("MERGEID"));
					try {
						mergeReferenceService.deleteMergeReference(mergeid);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("奖励合并删除操作");
					log.setOpaction("删除");
					log.setOpcontent("删除奖励合并,mergeid= "+mergeid);
					log.setOrgid(user.getUserid());
					log.setRemark("删除奖励合并");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = { "/ajaxGetRewardName" })
	@ResponseBody
	public void ajaxGetRewardName(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			String id = request.getParameter("id")==null?"":request.getParameter("id");
			HashMap map = mergeReferenceService.getRewardName(Integer.valueOf(id));
			String ajaxMainGetShuJuStr = "";
			if(map!=null){
				ajaxMainGetShuJuStr += "{"+"\"id\":"+"\""+map.get("ID")+"\""+",\"text\":"+"\""+map.get("TEXT")+"\"}";
			}
			writer.write("" + ajaxMainGetShuJuStr);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将Object转化为int，如果为null或者"",则返回-1
	 * @param str
	 * @return
	 */
	public int getIntValue(Object o) {
		int returnValue = -1;
		if(null != o){
			try {
				if(o instanceof Integer){
					returnValue = (Integer)o;
				}
				if(o instanceof String){
					if(!"".equals(o.toString())){
						returnValue = Integer.valueOf(o.toString());
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				returnValue = -1;
			}
		}
		return returnValue;
	}
	
	
	/**
	 * 将json转化为ArrayList
	 * @param json
	 * @return
	 */
	public Object Decode(String json) {
		if (null==json||"".equals(json)) return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		Object obj = deserializer.deserialize(json);
		if(obj != null && obj.getClass() == String.class){
			return Decode(obj.toString());
		}
		return obj;
	}
}
