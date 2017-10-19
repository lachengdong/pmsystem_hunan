package com.sinog2c.service.impl.commutationParole;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.system.TbsysCodeMapper;
import com.sinog2c.dao.api.user.UserReportMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.model.user.UserReport;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.PreviewPrintService;

@Service("previewPrintService")
public class PreviewPrintServiceImpl implements PreviewPrintService {

	@Autowired
    private UserReportMapper userReportMapper;
	@Autowired
    private TbsysCodeMapper tbsysCodeMapper;
	
	@Override
	public Object getPreviewReportList(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		SystemUser user = null;
		Object obj = request.getSession().getAttribute("session_user_key");
		if(obj instanceof SystemUser){
			user = (SystemUser)obj;
		}
		//过滤类型
		String type = request.getParameter("type");
		
		String reporttype=request.getParameter("reporttype");
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<UserReport> userReport = null;
		//总数
		total = userReportMapper.queryPreviewResReportCount(user.getUserid());
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		map.put("start", start);
		map.put("end", end);
		map.put("type", type);
		map.put("userid", user.getUserid());
		userReport =  userReportMapper.getUserReportByType(map);
		if(null!=reporttype&&"gonggao".equals(reporttype)){
			total= userReportMapper.queryPreviewResReportGonggaoCount(map);
			userReport= userReportMapper.getUserReportGonggaoByType(map);
		}
		
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(userReport);
		return message;
	}

	@Override
	public List<TbsysCode> getPreviewPrintCaseType(HttpServletRequest request) {
		List<TbsysCode> list = null;
		SystemUser user = null;
		String type = null;
		Object obj = request.getSession().getAttribute("session_user_key");
		if(obj instanceof SystemUser){
			user = (SystemUser)obj;
			String lv = user.getOrganization().getUnitlevel();
			if(lv!=null && ("6".equals(lv)||"7".equals(lv))){
				type = "fy";
			}
		}
		
		list = tbsysCodeMapper.getCodeByCodeType(GkzxCommon.CASETYPE_CODE,type);
		return list;
	}

}
