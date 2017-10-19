package com.sinog2c.mvc.controller.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.dbmsnew.TaskBean;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.TbsysFormDetailsService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * Spring MVC控制器的基类, 对Session访问提供统一方法,<br/>
 * 子类应该使用基类提供的方法,以方便今后的集群部署[届时只需要修改此类中的实现即可]。
 */
public abstract class ControllerBase {
	/**
	 * 会话中存储user信息的KEY
	 */
	public static final String SESSION_USER_KEY = "session_user_key";
	@Autowired
	public SystemResourceService systemResourceService;
	@Autowired
	private FlowDeliverService flowDeliverService;
	@Autowired
	private SystemConfigService systemConfigService;
	/**
	 * 在线日志服务对象
	 */
	@Autowired
	protected SystemLogService onlineLogService; 
	// 日志类型
	protected String logtype = "系统";
	// 任务进度所需的进度Bean,缓存
	public static Map<String, TaskBean> taskBeanMap = new HashMap<String, TaskBean>();

	protected boolean DEBUG_MODE = false;
	protected SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * 设置session属性
	 * @param request HttpServletRequest 请求对象
	 * @param name 属性名
	 * @param value 属性值, 可序列化对象
	 */
	public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		session.setAttribute(name, value);
	}

	/**
	 * 设置Session存活时间
	 * @param request
	 * @param aliveTimeSeconds
	 */
	public static void setSessionAliveTime(HttpServletRequest request, int aliveTimeSeconds) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(aliveTimeSeconds);
	}
	/**
	 * 根据 request取得Session属性值
	 * @param request HttpServletRequest 请求对象
	 * @param name 属性名
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String name) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		return session.getAttribute(name);
	}
	/**
	 * 获取当前登录的用户
	 * @param request
	 * @return
	 */
	public static SystemUser getLoginUser(HttpServletRequest request) {
		SystemUser user = null;
		Object obj = getSessionAttribute(request, SESSION_USER_KEY);
		if(obj instanceof SystemUser){
			user = (SystemUser)obj;
		}
		return user;
	}
	
	/**
	 * 在线用户; 弱引用,不影响Session的回收; 
	 */
	private static WeakHashMap<String,HttpSession > onlineUsers = new WeakHashMap<String,HttpSession>(100);
	/**
	 * 获取在线用户数量
	 * @return
	 */
	public static int getOnlineUserCount(){
		if(null != onlineUsers){
			int count = onlineUsers.entrySet().size();
			if(count < 1){
				count = 1;
			}
			return count;
		} else {
			return 1;
		}
	}
	/**
	 * 添加在线用户,主要由 LoginController使用.
	 * @param request
	 * @param user
	 */
	protected void addOnlineUser(HttpServletRequest request, SystemUser user) {
		if(null == request || null == user){
			return;
		}
		//
		if(null != onlineUsers){
			if(!onlineUsers.containsKey(user.getUserid())){
				//
				HttpSession session = request.getSession(true);
				// 添加
				onlineUsers.put(user.getUserid(), session);
			}
		}
	}
	/**
	 * 删除在线用户
	 * @param request
	 */
	protected void removeOnlineUser(HttpServletRequest request, SystemUser user) {
		if(null == request){
			return;
		}
		//
		if(null != onlineUsers){
			//
//			SystemUser user = getLoginUser(request);
			if (user != null) {
				// 添加
				onlineUsers.remove(user.getUserid());
			}
		}
	}
	
	/**
	 * 获取 上下文 path, 返回如 "/pmsys"
	 * @param request
	 * @return
	 */
	protected String path(HttpServletRequest request){
		String path = request.getContextPath();
		return path;
	}
	/**
	 * 获取 basePath
	 * @param request
	 * @return
	 */
	protected String basePath(HttpServletRequest request){
		String basePath = basePathLessSlash(request) + "/";
		return basePath;
	}
	/**
	 * 获取最后面少一个斜线的basePath
	 * @param request
	 * @return
	 */
	protected String basePathLessSlash(HttpServletRequest request){
		String path = path(request);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		return basePath;
	}

	/**
	 * 获取参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getParameter(HttpServletRequest request, String name){
		String value = request.getParameter(name);
		//
		return value;
	}
	/**
	 * 获取参数,如果为下面的值,则返回指定的默认值: <br/>
	 * 包括: null, "", "null", "undefined"
	 * @param request
	 * @param name 参数名
	 * @param defValue 指定默认值
	 * @return 如果为空或不存在,则返回默认值
	 */
	protected String getParameterString(HttpServletRequest request, String name, String defValue){
		String value = request.getParameter(name);
		if(null == value){
			return defValue;
		} else {
			value = value.trim();
			if("".equals(value) || "null".equals(value) || "undefined".equals(value)){
				return defValue;
			}
		}
		//
		return value;
	}
	

	/**
	 * 获取int类型参数
	 * @param request
	 * @param name
	 * @param defValue
	 * @return
	 */
	protected int getParameterInt(HttpServletRequest request, String name, int defValue){
		String value = request.getParameter(name);
		//
		return StringNumberUtil.parseInt(value, defValue);
	}

	/**
	 * 解析request中的参数Map
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> parseParamMap(HttpServletRequest request){
		//
		Map<String, Object> map = new HashMap<String, Object>();
		//
		if(null != request){
			Enumeration<String> enumeration = request.getParameterNames();
			// 遍历参数,其实有request的request.getParameterMap();但没泛型	
			while (enumeration.hasMoreElements()) {
				String paraName = (String) enumeration.nextElement();
				//
				String paraValue = request.getParameter(paraName);
				//
				if(null != paraValue){
					paraValue = paraValue.trim();
				}
				if("".equals(paraValue) || "null".equals(paraValue) || "undefined".equals(paraValue)){
					paraValue = "";
				}
				if(StringNumberUtil.notEmpty(paraValue)){
					map.put(paraName, paraValue);
				} else {
					// 会有空值，不能添加
				}
			}
		}
		//
		return map;
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, Object> parseParamData(String paramname,HttpServletRequest request){
		String data = request.getParameter(paramname);
		List list = (List) JsonUtil.Decode(data);
		Map map = (HashMap) list.get(0);
		return map;
	}

	/**
	 * 将Map添加到Attribute
	 * @param map
	 * @param request
	 */
	protected void addMap2Attribute(Map<String, ? extends Object> map, HttpServletRequest request){
		//
		if(null == request || null==map){
			return;
		}
		//
		Set<String> keySet = map.keySet();
		List<String> keys = new ArrayList<String>(keySet); 
		//
		Iterator<String> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			if(null != key){
				Object value = map.get(key);
				request.setAttribute(key, value);
			}
		}
	}

	/**
	 * 将 request 参数转换为 Attribute
	 * @param request
	 */
	protected void requestParam2Attribute(HttpServletRequest request){
		if(null != request){
			Map<String, ? extends Object> map = parseParamMap(request);
			addMap2Attribute(map, request);
		}
	}
	
	/**
	 * 解析Map可的分页信息，使其能用于mybatis中的分页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> parsePageInfoOfMap(Map map){
		Object pageIndexObj = map.get("pageIndex");
		Object pageSizeObj = map.get("pageSize");
		
		int pageIndex =StringNumberUtil.isEmpty(pageIndexObj)? 0:Integer.parseInt(pageIndexObj.toString());
		int pageSize =StringNumberUtil.isEmpty(pageSizeObj)? 0:Integer.parseInt(pageSizeObj.toString());
		//字段排序
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		map.put("start", start);
		map.put("end", end);
		return map;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String> parseParamMapString(HttpServletRequest request){
		//
		Map<String, String> map = new HashMap<String, String>();
		//
		if(null != request){
			Enumeration<String> enumeration = request.getParameterNames();
			// 遍历参数,其实有request的request.getParameterMap();但没泛型	
			while (enumeration.hasMoreElements()) {
				String paraName = (String) enumeration.nextElement();
				//
				String paraValue = request.getParameter(paraName);
				//
				if(null != paraValue){
					paraValue = paraValue.trim();
				}
				if("".equals(paraValue) || "null".equals(paraValue) || "undefined".equals(paraValue)){
					paraValue = "";
				}
				map.put(paraName, paraValue);
			}
		}
		//
		return map;
	}
	/**
	 * 根据资源父ID查询子资源对应的
	 * 
	 * @return
	 */
	public void returnResourceMap(HttpServletRequest request){
		String aipstr = "";
		String topstr = "";
		String topsearch = "";
		String topsearchkey = "";
		String middlestr = "";
		String tempmiddlestr = "";
		String nodeids = "";
		String orgid=request.getParameter("orgid");
		String menuid = request.getParameter("menuid");
		String lastnodeid = request.getParameter("lastnodeid");
		String flowdefid = StringNumberUtil.returnString2(request.getParameter("flowdefid"));
		Map<String,String> topmap = new HashMap<String,String>();
		Map<String,String> topmap2 = new HashMap<String,String>();
		Map<String,String> middlemap = new HashMap<String,String>();
		Map<String,String> aipmap = new HashMap<String,String>();
		SystemUser user = getLoginUser(request);
		
		StringBuffer topsearchSB = new StringBuffer();
		StringBuffer topsearchkeySB = new StringBuffer();
		//资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user, menuid);
		if(reslist!=null){
			for(SystemResource res:reslist){
				String url = res.getSrurl();
				String name = res.getName();
				String remark = res.getRemark();
				String resid = res.getResid();
				String key = res.getResid()+","+res.getShowico();
				String key2 = res.getResid()+","+res.getShowico()+","+res.getResid();
				if(name.contains("_")){
					name = name.split("_")[0];
				}
				//顶部按钮
				if(res.getRestypeid() == 12){
					topstr += "<a class=\"mini-button\" id=\""+resid+"\" iconCls=\""+StringNumberUtil.returnString2(res.getShowico())+"\" plain=\"true\" onclick=\""+url+"\">"+name+"</a>";
					topmap.put(key, name+"@"+res.getSrurl());
					topmap2.put(key, name+"@"+res.getSrurl()+"@"+remark);
				}
				//顶部信息显示
				else if(res.getRestypeid() == 57){
					//<span style="padding-left:20px;">编号：${applyid}</span>
					String value = request.getAttribute(res.getShowsite())==null?"":request.getAttribute(res.getShowsite()).toString();
					if(StringNumberUtil.notEmpty(value)){
						topstr += "<span style=\"padding-left:20px;\">"+res.getTitle()+value+"</span>";
					}
				}
				
				//列中按钮
				else if(res.getRestypeid() == 13){
					if(url!=null && url.contains("'")) url = url.replace("\'", "\\'");
					middlestr += " <a class=\"Edit_Button\" id=\""+resid+"\" href=\"javascript:"+url+"\" >"+name+"</a>";
					tempmiddlestr += "<a class=\"Edit_Button\" id=\""+resid+"\" href=\"javascript:"+url+"\" >"+name+"</a>@";
					middlemap.put(key, name+"@"+res.getSrurl());
				}
				//aip表单控件按钮
				else if(res.getRestypeid() == 15){
					aipstr += "<a class=\"mini-button\" id=\""+resid+"\" iconCls=\""+StringNumberUtil.returnString2(res.getShowico())+"\" plain=\"true\" onclick=\"chooseAll('"+resid+"')\">"+name+"</a>";
					aipmap.put(key, name+"@"+res.getSrurl());
				}
				
				//顶部检索组件
				else if(res.getRestypeid()== 50){
					//顶部Spinner
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-spinner\"");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" ").append(" name=\"").append(res.getShowsite()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					topsearchSB.append(" />");
					
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 51){
					//顶部TextBox
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-textbox\" ");
					
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" ").append(" name=\"").append(res.getShowsite()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
					}
					if(StringNumberUtil.notEmpty(res.getPrompt())){
						topsearchSB.append("  emptyText=\"").append(res.getPrompt()).append("\"  ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onkeyenter") == -1){
						topsearchSB.append(" onenter=\"onKeyEnter\" ");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onenter=\"onKeyEnter\" ");
					}
//					else{
//						topsearchSB.append(" onenter=\"onKeyEnter\" ");
//					}
					topsearchSB.append("/>");
					
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 52){
					//顶部ComboBox
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-combobox\" ");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" ").append(" name=\"").append(res.getShowsite()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
					}
					if(StringNumberUtil.notEmpty(res.getPrompt())){
						topsearchSB.append("  emptyText=\"").append(res.getPrompt()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					if(StringNumberUtil.notEmpty(res.getSrurl())){
						topsearchSB.append(" url=\"").append(res.getSrurl()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onvaluechanged") == -1){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}
//					else{
//						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
//					}
					topsearchSB.append(" />");
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 53){
					//顶部DatePicker
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-datepicker\" ");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" ").append(" name=\"").append(res.getShowsite()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
					}
					topsearchSB.append(" valueFormat=\"yyyy-MM-dd\" ");
					if(StringNumberUtil.notEmpty(res.getPrompt())){
						topsearchSB.append(" emptyText=\"").append(res.getPrompt()).append("\"  ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onvaluechanged") == -1){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}
//					else{
//						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
//					}
					topsearchSB.append(" />");
					
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 54){
					//顶部MonthPicker
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-monthpicker\" ");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" ").append(" name=\"").append(res.getShowsite()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
					}
					if(StringNumberUtil.notEmpty(res.getPrompt())){
						topsearchSB.append("  emptyText=\"").append(res.getPrompt()).append("\"  ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onvaluechanged") == -1){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}
//					else{
//						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
//					}
					topsearchSB.append(" />");
					
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 55){
					//顶部TreeSelect
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-treeselect\" ");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append("id=\"").append(res.getShowsite()).append("\" name=\"").append(res.getShowsite()).append(" \" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append(" \" ");
					}
					if(StringNumberUtil.notEmpty(res.getPrompt())){
						topsearchSB.append("   emptyText=\"").append(res.getPrompt()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					if(StringNumberUtil.notEmpty(res.getSrurl())){
						topsearchSB.append(" url=\"").append(res.getSrurl()).append("\" ");
					}
					
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onvaluechanged") == -1){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}
//					else{
//						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
//					}
					topsearchSB.append(" />");
					
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 56){
					//顶部AutoComplete
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-autocomplete\" ");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" name=\"").append(res.getShowsite()).append(" \" ");
					}
					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
					}
					if(StringNumberUtil.notEmpty(res.getPrompt())){
						topsearchSB.append("   emptyText=\"").append(res.getPrompt()).append("\" ");
					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					
					if(StringNumberUtil.notEmpty(res.getSrurl())){
						topsearchSB.append(" url=\"").append(res.getSrurl()).append("\" ");
					}
					
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onvaluechanged") == -1){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}
//					else{
//						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
//					}
					topsearchSB.append(" />");
					topsearchkeySB.append(res.getShowsite()).append(",");
				}else if(res.getRestypeid()== 58){
					//顶部AutoComplete
					if(StringNumberUtil.notEmpty(res.getTitle())){
						topsearchSB.append(res.getTitle());
					}
					topsearchSB.append("<input class=\"mini-checkbox\" ");
					if(StringNumberUtil.notEmpty(res.getShowsite())){
						topsearchSB.append(" id=\"").append(res.getShowsite()).append("\" name=\"").append(res.getShowsite()).append(" \" ");
					}
					
//					if(StringNumberUtil.notEmpty(res.getWindowwidth())){
//						topsearchSB.append(" style=\"width:").append(res.getWindowwidth()).append("px;\" ");
//					}
//					if(StringNumberUtil.notEmpty(res.getPrompt())){
//						topsearchSB.append("   emptyText=\"").append(res.getPrompt()).append("\" ");
//					}
					if(StringNumberUtil.notEmpty(res.getText1())){
						topsearchSB.append(res.getText1());
					}
					
//					if(StringNumberUtil.notEmpty(res.getSrurl())){
//						topsearchSB.append(" url=\"").append(res.getSrurl()).append("\" ");
//					}
					
					if(StringNumberUtil.notEmpty(res.getText1()) && res.getText1().toLowerCase().indexOf("onvaluechanged") == -1){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
					}else if(StringNumberUtil.isEmpty(res.getText1())){
						topsearchSB.append(" onvaluechanged=\"onValueChanged\"");
					}
//					else{
//						topsearchSB.append(" onvaluechanged=\"onValueChanged\" ");
//					}
					topsearchSB.append(" />");
					topsearchkeySB.append(res.getShowsite()).append(",");
				}
				
				
			}
			
			topsearchkey = topsearchkeySB.toString();
			if(StringNumberUtil.notEmpty(topsearchkey)&&topsearchkey.endsWith(",")){
				topsearchkey = topsearchkey.substring(0,topsearchkey.length()-1);
			}
		}
		
		topsearch = topsearchSB.toString();
		
		//根据流程自定义ID返回当前用户对应的流程自定义ID,节点ID
		if(!StringNumberUtil.isNullOrEmpty(flowdefid)){
			nodeids = returnFlowdefNodeIdByUserAndFlowdefid(user, flowdefid);
			if(!StringNumberUtil.isNullOrEmpty(nodeids)) nodeids = nodeids.replace("'", "");
			if(StringNumberUtil.isNullOrEmpty(lastnodeid)) lastnodeid = nodeids;
			request.setAttribute("snodeid", lastnodeid);
			request.setAttribute("nodeids", nodeids);
			request.setAttribute("flowdefid", flowdefid);
			if(StringNumberUtil.notEmpty(tempmiddlestr)){
				String[] _str = tempmiddlestr.split("@");
				String dealstr = "";//处理
				String modifystr = "";//修改
				String viewstr = "";//查看
				String otherstr = "";
				for(String obj:_str){
					 if(obj.contains(GkzxCommon.BUTTON_DEAL)){
						 dealstr = obj;
					 }else if(obj.contains(GkzxCommon.BUTTON_MODIFY)){
						 modifystr = obj;
					 }else if(obj.contains(GkzxCommon.BUTTON_VIEW)){
						 viewstr = obj;
					 }else{
						 otherstr = obj;
					 }
				 }
					 
				request.setAttribute("dealstr", dealstr);
				request.setAttribute("modifystr", modifystr);
				request.setAttribute("viewstr", viewstr);
				request.setAttribute("otherstr", otherstr);
			}
			//
		}
		
		request.setAttribute("topmap", topmap);
		request.setAttribute("topmap2", topmap2);
		request.setAttribute("aipmap", aipmap);
		request.setAttribute("middlemap", middlemap);
		request.setAttribute("aipstr", aipstr);
		request.setAttribute("topstr", topstr);
		request.setAttribute("topsearch", topsearch);
		request.setAttribute("topsearchkey", topsearchkey);
		request.setAttribute("middlestr", middlestr);
		request.setAttribute("menuid", menuid);
	}
	/**
	 * 根据用户返回对应权限的按钮资源ID集合
	 * distributeflow : 需要分案的流程，
	 * isFjquser ： 是否需要分案的标志   1：需要分案
	 * @return
	 */
	public Object returnButtonResourceByUser(SystemUser user, String distributeflow,String isFjquser){
		//用户对象
		FlowDeliver flowDeliver = new FlowDeliver();
		
		//资源对象
		Object buttonstr = "1!=1";
		StringBuffer flowdefidsbf = new StringBuffer();
		//StringBuffer nodesbf = new StringBuffer();
		Map<String,FlowDeliver> map = new HashMap<String,FlowDeliver>();
		
		//获取流程配置信息
		List<FlowDeliver> deliverlist = flowDeliverService.findByDepartid(user.getDepartid());
		for(FlowDeliver deliver:deliverlist){
			map.put(deliver.getResid(), deliver);
		}
		//获取资源
		List<SystemResource> reslist = systemResourceService.getAllResourceByUser(user);
		if(reslist!=null){
			for(SystemResource res:reslist){
				Integer ismenu = res.getIsmenu();
				if(map.containsKey(res.getResid())){
					flowDeliver = map.get(res.getResid());
					
					//拥有的所有可审批的流程
					if(StringNumberUtil.notEmpty(distributeflow) && StringNumberUtil.notEmpty(isFjquser) 
							&& isFjquser.trim().equals("1") && distributeflow.indexOf(flowDeliver.getFlowdefid()) > -1){
						
						String flowdefidval = "(tb.flowdefid = '"+flowDeliver.getFlowdefid() +"' and tb.fjquser ='"+user.getUserid() +"' and tb.currnodeid ='"+flowDeliver.getSnodeid()+"') or ";
						if(flowdefidsbf.indexOf(flowdefidval)== -1 ){
							flowdefidsbf.append(flowdefidval);
						}
						
					}else{
						String flowdefidval = "(tb.flowdefid = '"+flowDeliver.getFlowdefid()+"' and tb.currnodeid ='"+flowDeliver.getSnodeid()+"') or ";
						if(flowdefidsbf.indexOf(flowdefidval)== -1 ){
							flowdefidsbf.append(flowdefidval);
						}
					}
					
				}
			}
		}
		if(flowdefidsbf!=null && flowdefidsbf.length()>0){
			buttonstr = flowdefidsbf.substring(0, flowdefidsbf.length()-3);
		}
		
		return buttonstr;
	}
	
	
	/**
	 * 根据用户及流程id,返回对应权限的按钮资源ID集合
	 * 
	 * @return
	 */
	public Object returnButtonResourceByUser(SystemUser user,String flowdefid){
		return systemResourceService.returnButtonResourceByUser(user, flowdefid);
	}
	
	/**
	 * 根据用户及流程id,返回对应权限的按钮资源ID集合
	 * 
	 * @return
	 */
	public String returnFlowdefNodeIdByUserAndFlowdefid(SystemUser user,String flowdefid){
		//用户对象
		FlowDeliver flowDeliver = new FlowDeliver();
		String nodeids = "";
		//资源对象
		Set<String> set  = new HashSet<String>();
		Map<String,FlowDeliver> map = new HashMap<String,FlowDeliver>();
		
		//获取流程配置信息
		Map paraMap = new HashMap();
		paraMap.put("id", flowdefid);
		paraMap.put("departid", user.getDepartid());
		List<FlowDeliver> deliverlist = flowDeliverService.findByFlowdefid(paraMap);
		//如果当前监狱找不到相应的流程信息，则找jyconfig中对应省份的流程信息
		if(deliverlist.size() <= 0){
			Properties jypro = new GetProperty().bornProp(
					GkzxCommon.DATABASETYPE, null);
			if (jypro != null) {
				paraMap.put("departid", jypro.getProperty("provincecode"));
			}
			deliverlist = flowDeliverService.findByFlowdefid(paraMap);
		}
		for(FlowDeliver deliver:deliverlist){
			map.put(deliver.getResid(), deliver);
		}
		//获取资源
		List<SystemResource> reslist = systemResourceService.getAllResourceByUser(user);
		if(reslist!=null){
			for(SystemResource res:reslist){
				Integer ismenu = res.getIsmenu();
				if(ismenu == 0 && map.containsKey(res.getResid())){
					flowDeliver = map.get(res.getResid());
//					System.out.println(map.get(res.getResid())+"|||"+flowDeliver.getSnodeid()+"|||"+res.getResid()+"^^"+res.getResname());
					//拥有的所有可审批的流程
					set.add(flowDeliver.getSnodeid());
				}
			}
		}
		
		if(null!=set&&set.size()>0){
			for(String ss : set){
				nodeids +=",'"+ss+"'";
			}
			nodeids = nodeids.replaceFirst(",", "");
		}
		return nodeids;
	}

	protected String getLogtype() {
		return logtype;
	}
	protected void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	/**
	 * 增加在线日志
	 * @return
	 */
	protected boolean addOnlineLog(SystemLog log, SystemUser operator) {
		//
		boolean result = false;
		//
		if(null == onlineLogService){
			return result;
		}
		//
		if(null == operator){
			// 如果登录失败,或者错误
			operator = new SystemUser();
			operator.setUserid("-1");
			operator.setName("系统日志");
			SystemOrganization organization = new SystemOrganization();
			organization.setOrgid("1");
			operator.setOrganization(organization);
		}
		//
		try {
			int logresult =  onlineLogService.add(log, operator);
			if(logresult > 0){
				result = true;
			}
		} catch (Exception e) {
		}
		//
		return result;
	}
	protected boolean addOnlineLog(SystemLog log, SystemUser operator, JSONMessage message){
		//
		boolean result = false;
		//
		if(null == onlineLogService){
			return result;
		}
		try {
			//
			int logresult =  onlineLogService.add(log, operator, message);
			if(logresult > 0){
				result = true;
			}
		} catch (Exception e) {
		}
		//
		return result;
	}
	protected boolean addOnlineLog(SystemUser operator, String info, String logtype, int status){
		//
		boolean result = false;
		//
		if(null == onlineLogService){
			return result;
		}
		//
		SystemLog log = new SystemLog();
		log.setRemark(info);
		log.setLogtype(logtype);
		log.setOpaction(info);
		log.setOpcontent(info);
		log.setStatus(status);
		//
		result = addOnlineLog(log, operator);
		//
		return result;
	}
	protected boolean addOnlineLog(SystemUser operator, String info, String logtype){
		int status = 1;
		return addOnlineLog(operator, info, logtype, status);
	}
	protected boolean addOnlineLog(SystemUser operator, String info){
		return addOnlineLog(operator, info, this.getLogtype());
	}

	protected String getTimeStampStr() {
		Date now = new Date();
		return getTimeStampStr(now);
	}
	protected String getTimeStampStr(Date date) {
		if(null == date){
			date = new Date();
		}
		return TimeStampFormat.format(date);
	}
	protected  void debug(String info){
		if(DEBUG_MODE){
			log(info);
		}
	}
	//
	protected  void log(String info){
		System.out.println(getTimeStampStr()+":\t" +info);
	}
	//
	
	//判断该用户是否是分监区用户
	protected String isFjqUser(Map<String,Object> map, SystemUser user){
		if(null == map){
			map = new HashMap<String,Object>();
		}
		
		//检查是否需要区分分监区   南宁监狱分监区与监区在同一个部门
		map.put("name", "checkIsFjqUser");
		map.put("departid", user.getDepartid());
		String value = "";
		SystemConfigBean config = systemConfigService.getConfigByMap(map);
		if(null != config){
			value = config.getValue();
		}
		if(StringNumberUtil.notEmpty(value) && value.equals("1")){
			//查询当前用户是否是分监区用户
			if(null != user.getInt2() && user.getInt2() == 1){
				map.put("isfjquser", value);
				return value;
			}
		}
		return "0";
		//
	}
}