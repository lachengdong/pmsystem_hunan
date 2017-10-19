package com.sinog2c.service.impl.oa;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.sinog2c.dao.api.oa.OaBgypMapper;
import com.sinog2c.model.oa.OAyz;
import com.sinog2c.model.oa.OaBgyp;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.oa.OaBgypService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
@Service("oaBgypService")
public class OaBgypServiceImpl implements OaBgypService {

	@Autowired
	private OaBgypMapper bgypMapper;
	private JsonUtil jsonUtil;
	
	@Override
	public void saveBgyp(HttpServletRequest request) {
		SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, "session_user_key");
		String data = request.getParameter("data");
		List list = (List) this.jsonUtil.Decode(data);
		String _state="";//定义一个状态接收页面传回的值
		String bgids = "";
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				OaBgyp record = new OaBgyp();
				HashMap map = (HashMap) list.get(i);
				_state=(String)map.get("_state") != null?map.get("_state").toString():"";
				String bgypid = (String)map.get("bgypid") != null?map.get("bgypid").toString():"";
				record.setLysl(map.get("lysl") != null?map.get("lysl").toString():"");
				record.setLyrsign(map.get("lyrsign") != null?map.get("lyrsign").toString():"");
				record.setRemark(map.get("remark") != null?map.get("remark").toString():"");
				record.setWpname(map.get("wpname") != null?map.get("wpname").toString():"");
				record.setOpid(user.getUserid());
				if(map.get("lyrq")!=null){
					record.setLyrq((Date)map.get("lyrq"));
				}
				if(!("").equals(_state)&&"added".equals(_state)){//如果是added则添加一条数据
					String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
					record.setBgypid(uuid);
					this.bgypMapper.insertSelective(record);
				}
				if(!("").equals(_state)&&"modified".equals(_state)){//如果是modified则修改一条数据
					record.setBgypid(bgypid);
					this.bgypMapper.updateByPrimaryKeySelective(record);
				}
				if(!("").equals(_state)&&"removed".equals(_state)){//如果是removed则删除一条数据
					this.bgypMapper.deleteByPrimaryKey(bgypid);
				}
			}
		}
	}
	
	@Override
	public List<Map> getBgypList(String key) {
		List<Map> resultMap=bgypMapper.getBgypList(key);
		if (resultMap==null) {
			return null;
		}else {
			return MapUtil.turnKeyToLowerCaseOfList(resultMap);
		}
	}

	@Override
	public int deletByid(String bgypid) {
		return bgypMapper.deleteByPrimaryKey(bgypid);
	}

	
	
	

}


