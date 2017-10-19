package com.sinog2c.mvc.controller.officeAssistant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.LogCommon;
import com.sinog2c.model.officeAssistant.TbuserAddressbooks;
import com.sinog2c.model.officeAssistant.TbuserAddressbooksGroup;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.officeAssistant.AddressbooksService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.MapUtil;

/**
 * 通讯簿
 * 
 * @author kexz 2014-7-17 11:42:54
 */
@Controller
public class AddressbookController extends ControllerBase {

	@Autowired
	private AddressbooksService addressbooksService;
	@Resource
	public SystemLogService logService;

	/**
	 * 通讯簿的页面
	 * 
	 * @author kexz 2014-7-17 11:43:04
	 */
	@RequestMapping("/queryConnectPersionAll")
	public ModelAndView queryConnectPersionAll(HttpServletRequest request) {
		// 获取当前登录的用户，在返回页面时，下拉列表显示的组依据当前用户
		SystemUser user = getLoginUser(request);
		String loginUserid = user.getUserid();
		List<HashMap> groups=addressbooksService.ajaxGroupByUserId(loginUserid);
		return new ModelAndView(new InternalResourceView(
				"WEB-INF/JSP/officeAssistant/queryConnectPersionAll.jsp"),"list",groups);
	}

	/**
	 * 此方法描述的是：树节点的分组和联系人的显示
	 * 
	 * @return 分组以文件夹的方式显示
	 * @author kexz 2014-7-17 11:55:11
	 */
	@RequestMapping(value = "/ajaxPersionAll")
	@ResponseBody
	@SuppressWarnings("all")
	public List<HashMap> ajaxPersionAll(HttpServletRequest request) {
		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String loginUserid = user.getUserid();
		List<HashMap> groups = addressbooksService.ajaxPersionAll(user
				.getUserid());
		List groupIds = new ArrayList();
		for (HashMap map : groups) {
			groupIds.add(map.get("GROUPID"));
		}
		//此处需要进行判断，如果数据库中一条数据都不存在，将会报错(即：groupids无值)
		List<HashMap> persons = null;
		if (groupIds.size() > 0) {
			 persons = addressbooksService.ajaxPersion(groupIds);
		}

		List<HashMap> list = new ArrayList<HashMap>();
		if (groups != null && groups.size() > 0) {
			for (int i = 0; i < groups.size(); i++) {
				HashMap temp = (HashMap) groups.get(i);
				HashMap map = new HashMap();
				map.put("groupid", temp.get("GROUPID"));
				map.put("name", temp.get("NAME"));
				map.put("userid", temp.get("USERID"));
				map.put("ispublic", temp.get("ISPUBLIC"));
				map.put("expanded", false);
				map.put("isLeaf", false);
				map.put("asyncLoad", false);
				if (temp.get("PGROUPID").toString().equals("-1")) {
					// 公共的底级文件夹的父级id设置为-2，非公共的设置为-3以便页面tree展示
					if (temp.get("ISPUBLIC").toString().equals("0")) {
						map.put("pgroupid", "-2");
					} else {
						map.put("pgroupid", "-3");
					}
				} else {
					map.put("pgroupid", temp.get("PGROUPID"));
				}
				
				list.add(map);
			}
		}
		if (persons != null && persons.size() > 0) {
			for (int i = 0; i < persons.size(); i++) {
				HashMap temp = (HashMap) persons.get(i);
				HashMap map = new HashMap();
				// 拼接一个A以防分组与联系人的id冲突
				map.put("groupid", temp.get("LINKMANID").toString() + "A");
				// 将联系人的gorupid赋值给父级id;
				map.put("pgroupid", temp.get("GROUPID"));
				map.put("name", temp.get("NAME"));
				list.add(map);
			}
		}

		// 创建个人通讯簿
		HashMap<Object, Object> ownMap = new HashMap<Object, Object>();
		ownMap.put("pgroupid", "-1");
		ownMap.put("groupid", "-2");
		ownMap.put("ispublic", 0);
		ownMap.put("name", "个人通讯簿");
		ownMap.put("expanded", false);
		ownMap.put("isLeaf", false);
		ownMap.put("asyncLoad", false);
		list.add(ownMap);

		// 创建公共通讯簿
		HashMap<Object, Object> publicMap = new HashMap<Object, Object>();
		publicMap.put("pgroupid", "-1");
		publicMap.put("groupid", "-3");
		publicMap.put("ispublic", 1);
		publicMap.put("name", "公共通讯簿");
		publicMap.put("expanded", false);
		publicMap.put("isLeaf", false);
		publicMap.put("asyncLoad", false);
		list.add(publicMap);
		return list;
	}

	/**
	 * 通讯簿 点击联联系人或者分组，在右边文本框中显示对应当前的本条所有信息
	 * 
	 * @author kexz 2014-7-17 11:55:23
	 */
	@RequestMapping(value = "/ajaxByGroupid")
	@ResponseBody
	@SuppressWarnings("all")
	public HashMap ajaxByGroupid(HttpServletRequest request,
			HttpServletResponse response) {
		String keyid = request.getParameter("groupid");
		HashMap map = new HashMap();
		if (keyid != null && keyid.endsWith("A")) {
			map = addressbooksService.ajaxPersionByLinkManId(keyid.substring(0,keyid.length() - 1));
			map = (HashMap) MapUtil.convertKey2LowerCase(map);
			if (map.get("birthday") != null && !"".equals(map.get("birthday"))) {
				map.put("birthday", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(map.get("birthday")));
			}
			map.put("personname", map.get("name"));
			
		} else {
			map = addressbooksService.ajaxPersionGroupAllByGroupid(keyid);
			map = (HashMap) MapUtil.convertKey2LowerCase(map);
			map.put("groupname", map.get("name"));
			map.put("ispublic", map.get("ispublic"));
		}

		return map;
	}

	/**
	 * 新增分组
	 * 
	 * @author kexz 2014-7-17 11:57:00
	 */
	@RequestMapping(value = "/ajaxSystemSaveGroup")
	@ResponseBody
	public int ajaxSystemSaveGroup(HttpServletRequest request)
			throws ParseException {
		int result = 0;
		short status = 1;

		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);

		try {
			String json = request.getParameter("data");
			String type = request.getParameter("type");
			
			String ispublic = request.getParameter("ispublic");//判断是 个人文件夹还是 公共文件夹 
			Map map = new HashMap();
			Map<String,String> tempMap = new HashMap<String,String>();
			Object data = json.substring(2, json.length() - 2);
			String string = data.toString().replace("\"", "");
			String[] strings = string.split(",");
			for (int i = 0; i < strings.length; i++) {
				String[] strs = strings[i].split(":");
				if (strs.length == 1) {
					map.put(strs[0], "");
				} else {
					map.put(strs[0], strs[1]);
				}
			}
			if (type.equals("1")) {
				tempMap = MapUtil.ObjectMapToStringMap(map);
				SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
				TbuserAddressbooks tper = new TbuserAddressbooks();
				if(tempMap.get("birthday")!=null && !"".equals(tempMap.get("birthday"))) {
					Date date1 = simple.parse(tempMap.get("birthday"));
					tper.setBirthday(date1);
				}
				tper.setGroupid(Integer.parseInt(tempMap.get("groupid")));
				tper.setName(tempMap.get("personname"));
				tper.setGender(tempMap.get("gender"));
				tper.setNickname(tempMap.get("nickname"));
				tper.setDuty(tempMap.get("duty"));
				tper.setSpouse(tempMap.get("spouse"));
				tper.setChildren(tempMap.get("children"));
				tper.setRemark(tempMap.get("remark"));
				tper.setMobile(tempMap.get("mobile"));
				tper.setHometelephone(tempMap.get("hometelephone"));
				tper.setHomeaddress(tempMap.get("homeaddress"));
				tper.setHomepostcode(tempMap.get("homepostcode"));
				tper.setDepfax(tempMap.get("depfax"));
				tper.setTelephone(tempMap.get("telephone"));
				tper.setEmail(tempMap.get("email"));
				tper.setQq(tempMap.get("qq"));
				tper.setMsn(tempMap.get("msn"));
				tper.setDepname(tempMap.get("depname"));
				tper.setDepaddress(tempMap.get("depaddress"));
				tper.setDeppostcode(tempMap.get("deppostcode"));
				tper.setDeptelephone(tempMap.get("deptelephone"));
				tper.setOpid(user.getUserid());
				result += addressbooksService.addPerson(tper);
			} else {
				tempMap = MapUtil.ObjectMapToStringMap(map);
				TbuserAddressbooksGroup tab = new TbuserAddressbooksGroup();
				tab.setName(tempMap.get("name"));
				tab.setRemark(tempMap.get("remark"));
				tab.setIspublic(ispublic);
				tab.setUserid(user.getUserid());
				tab.setOpid(user.getUserid());
				result += addressbooksService.addGroup(tab);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
		}

		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JSGLSXGZS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.ADD);
		log.setOpcontent(LogCommon.JSGLSXGZS+LogCommon.ADD);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JSGLSXGZS+LogCommon.ADD);
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}

		return result;
	}
    /**
     * 方法描述：查询 当前用户是否可以编辑 公共通讯薄
     * 
     */
	@RequestMapping(value = "/whetherPermissions")
	@ResponseBody
	@SuppressWarnings("all")
	public Object whetherPermissions(HttpServletRequest request){
		//获取当前用户的 登陆账号
		SystemUser user = getLoginUser(request);
		int obj = 0 ;
		try {
			String perid = request.getParameter("permiss");
			String userid = user.getUserid();
			Map map = new HashMap();
			map.put("perid", perid);
			map.put("userid", userid);
			List<Map> list = addressbooksService.whetherPermissions(map);
			if (list.size() > 0) {
				obj = 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * 删除联系人,删除组信息及联系人信息
	 * 
	 * @author kexz 2014-7-17 12:23:31
	 */
	@RequestMapping(value = "/ajaxSystemRemoveShuJu")
	@ResponseBody
	public int ajaxSystemRemoveShuJu(HttpServletRequest request) {
		int result = 0;
		short status = 1;
		String keyid = request.getParameter("groupid");
		try {
			if (keyid != null && keyid.endsWith("A")) {
				result += addressbooksService.deletePersonByLinManid(keyid
						.substring(0, keyid.length() - 1));
			} else {
				addressbooksService.deletePersonByGroupId(keyid);
				result += addressbooksService.deleteGroup(keyid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
		}

		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.TONGXUNBU+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.TONGXUNBU+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.TONGXUNBU+LogCommon.DEL);
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}

		return result;
	}

	/**
	 * 修改组信息及联系人信息
	 * 
	 * @author kexz 2014-7-17 16:13:03
	 * @throws ParseException
	 */
	@RequestMapping(value = "/ajaxSystemUpdateShuJu")
	@ResponseBody
	public int ajaxSystemUpdateShuJu(HttpServletRequest request){
		int countnum = 0;
		try {
			short status = 1;
			SystemUser user = getLoginUser(request);// 获取当前登录的用户
			//判断 是 公共通讯薄/个人通讯簿
			String ispublic = request.getParameter("ispublic")==null?"":request.getParameter("ispublic");
			
			String groupId = request.getParameter("groupid");
			String lin = request.getParameter("linkmanid");
			String json = request.getParameter("data");
			String type = request.getParameter("type");
			Map map = new HashMap();
			Map<String,String> tempMap = new HashMap<String,String>();
			Object data = json.substring(2, json.length() - 2);
			String string = data.toString().replace("\"", "");
			String[] strings = string.split(",");
			for (int i = 0; i < strings.length; i++) {
				String[] strs = strings[i].split(":");
				if (strs.length == 1) {
					map.put(strs[0], "");
				} else {
					map.put(strs[0], strs[1]);
				}
			}
			if (type.equals("1")) {
				if (lin != null && lin.endsWith("A")) {
					int link = Integer.parseInt(lin.substring(0, lin.length() - 1));
					tempMap = MapUtil.ObjectMapToStringMap(map);
					SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = simple.parse(tempMap.get("birthday"));
					TbuserAddressbooks tper = new TbuserAddressbooks();
					tper.setLinkmanid(link);
					tper.setGroupid(Integer.parseInt(tempMap.get("groupid")));
					tper.setName(tempMap.get("personname"));
					tper.setGender(tempMap.get("gender"));
					tper.setNickname(tempMap.get("nickname"));
					tper.setDuty(tempMap.get("duty"));
					tper.setSpouse(tempMap.get("spouse"));
					tper.setChildren(tempMap.get("children"));
					tper.setRemark(tempMap.get("remark"));
					tper.setDepfax(tempMap.get("depfax"));
					tper.setMobile(tempMap.get("mobile"));
					tper.setHometelephone(tempMap.get("hometelephone"));
					tper.setTelephone(tempMap.get("telephone"));
					tper.setHomeaddress(tempMap.get("homeaddress"));
					tper.setHomepostcode(tempMap.get("homepostcode"));
					tper.setEmail(tempMap.get("email"));
					tper.setQq(tempMap.get("qq"));
					tper.setMsn(tempMap.get("msn"));
					tper.setDepname(tempMap.get("depname"));
					tper.setDepaddress(tempMap.get("depaddress"));
					tper.setDeppostcode(tempMap.get("deppostcode"));
					tper.setDeptelephone(tempMap.get("deptelephone"));
					tper.setBirthday(date1);
					tper.setOpid(user.getUserid());
					countnum = addressbooksService.updatePerson(tper);
				}
			} else {
				tempMap = MapUtil.ObjectMapToStringMap(map);
				TbuserAddressbooksGroup group = new TbuserAddressbooksGroup();
				group.setName(tempMap.get("name"));
				group.setRemark(tempMap.get("remark"));
				group.setGroupid(Integer.parseInt(tempMap.get("groupid")));
				// 权限判断 
				group.setIspublic(ispublic);
				group.setUserid(user.getUserid());
				group.setOpid(user.getUserid());
				countnum = addressbooksService.updateGroup(group);
			}
	
			SystemLog log = new SystemLog();
			log.setLogtype(LogCommon.TONGXUNBU+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.TONGXUNBU+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.TONGXUNBU+LogCommon.EDIT);
			log.setStatus(status);
		
			logService.add(log, user);
		} catch (Exception e) {
			e.printStackTrace();
			// 吃掉异常
		}
		return countnum;
	}
}
