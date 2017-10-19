package com.sinog2c.service.impl.oa;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.sinog2c.dao.api.oa.OAyzMapper;
import com.sinog2c.model.oa.OAyz;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.oa.OaSealService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
@Service("oasealService")
public class OaSealServiceImpl implements OaSealService {
	@Autowired
	private OAyzMapper oayzMapper;
	private JsonUtil jsonUtil;
	
	@Override
	public int deleteByPrimaryKey(String yzid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(OAyz record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(OAyz record) {
		int result = this.oayzMapper.insertSelective(record);
		return result;
	}

	@Override
	public OAyz selectByPrimaryKey(String yzid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(OAyz record) {
		// TODO Auto-generated method stub
		return this.oayzMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OAyz record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map> getYzList(Map map) {
		List<Map> resultMap=oayzMapper.getYzList(map);
		if (resultMap==null) {
			return null;
		}else {
			return MapUtil.turnKeyToLowerCaseOfList(resultMap);
		}
	}

	/**
	 * 修改、删除、新增
	 */
	@Override
	public void saveOAyz(HttpServletRequest request) {
		SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, "session_user_key");
		String data = request.getParameter("data");
		List list = (List) this.jsonUtil.Decode(data);
		String _state="";//定义一个状态接收页面传回的值
		String yzids = "";
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				OAyz record = new OAyz();
				HashMap map = (HashMap) list.get(i);
				_state=(String)map.get("_state") != null?map.get("_state").toString():"";
				String yzid = (String)map.get("yzid") != null?map.get("yzid").toString():"";
				record.setYzmc(map.get("yzmc") != null?map.get("yzmc").toString():"");
				record.setYzym(map.get("yzym") != null?map.get("yzym").toString():"");
				record.setQrzt(map.get("qrzt") != null?map.get("qrzt").toString():"");
				record.setBgr(map.get("bgr") != null?map.get("bgr").toString():"");
				if(("").equals(_state)||"added".equals(_state)){//如果是added则添加一条数据
					String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
					record.setYzid(uuid);
					record.setOpid(user.getUserid());
					record.setJsrq((Date)map.get("jsrq"));
					this.oayzMapper.insertSelective(record);
				}
				if(("").equals(_state)||"modified".equals(_state)){//如果是modified则修改一条数据
					record.setYzid(yzid);
					String jsrq = (String)map.get("jsrq");
					DateUtil datefrm = new DateUtil();
					Date jsdate = datefrm.StrParseDate(jsrq, "yyyy-MM-dd");
					record.setJsrq(jsdate);
					this.oayzMapper.updateByPrimaryKeySelective(record);
				}
				if(("").equals(_state)||"removed".equals(_state)){//如果是removed则删除一条数据
					record.setYzid(yzid);
					yzids += (String)map.get("yzid") != null?map.get("yzid").toString():""+",";
					String jsrq = (String)map.get("jsrq");
					DateUtil datefrm = new DateUtil();
					Date jsdate = datefrm.StrParseDate(jsrq, "yyyy-MM-dd");
					record.setJsrq(jsdate);
					this.oayzMapper.deleteOayz(yzids);
				}
			}
		}
	}

}
