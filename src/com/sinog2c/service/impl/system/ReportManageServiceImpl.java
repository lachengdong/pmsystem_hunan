package com.sinog2c.service.impl.system;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.user.UserReportMapper;
import com.sinog2c.model.user.UserReport;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.ReportManageService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service
public class ReportManageServiceImpl implements ReportManageService{
	@Autowired
	private UserReportMapper userReportMapper;

	public Object getReportList(HttpServletRequest request) {
		String typekey = request.getParameter("typekey");
		String textkey = request.getParameter("textkey");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<Map<String,String>> list = null;
		//总数
		total = userReportMapper.queryPreviewReportCount(typekey,textkey,sortField,sortOrder);
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		list =  userReportMapper.getReportManagePageList(start, end,typekey,textkey,sortField,sortOrder);
		
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}

	@Override
	public Object getReportById(HttpServletRequest request) {
		String rid = request.getParameter("id");
		UserReport userReport = null;
		if(StringNumberUtil.notEmpty(rid)){
			userReport = userReportMapper.selectByPrimaryKey(rid);
		}
		return userReport;
	}

	@Override
	public Object insertReportManage(HttpServletRequest request) {
		UserReport userReport = new UserReport();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			String planid = map.get("planid").equals("")||map.get("planid")==null?"0":(String)map.get("planid");//方案id
			userReport.setName((String)map.get("name"));
			userReport.setPlanid(Integer.parseInt(planid));
			userReport.setRemark((String)map.get("remark"));
			userReport.setResid((String)map.get("resid"));
			userReport.setSn(Short.parseShort((String)map.get("sn")));
			userReport.setType((String)map.get("type"));
			obj = userReportMapper.insertSelective(userReport);
		}
		return obj;
	}

	@Override
	public Object updateReportManage(HttpServletRequest request) {
		UserReport userReport = new UserReport();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			userReport.setRid((String)map.get("rid"));
			userReport.setName((String)map.get("name"));
			userReport.setPlanid(Integer.parseInt((String.valueOf(map.get("planid")))));
			userReport.setRemark((String)map.get("remark"));
			userReport.setResid((String)map.get("resid"));
			userReport.setSn(Short.parseShort((String)map.get("sn")));
			userReport.setType((String)map.get("type"));
			obj = userReportMapper.updateByPrimaryKeySelective(userReport);
		}
		return obj;
	}

	@Override
	public Object deleteReportById(HttpServletRequest request) {
		String rid = request.getParameter("rid");
		if(StringNumberUtil.notEmpty(rid)){
			userReportMapper.deleteByPrimaryKey(rid);
		}
		return null;
	}

	@Override
	public Object deleteBatchReportByIds(HttpServletRequest request) {
		String rids = request.getParameter("rids");
		if(StringNumberUtil.notEmpty(rids)){
			String[] ridArr = rids.split(",");
			for(String rid : ridArr){
				userReportMapper.deleteByPrimaryKey(rid);
			}
		}
		return null;
	}
}
