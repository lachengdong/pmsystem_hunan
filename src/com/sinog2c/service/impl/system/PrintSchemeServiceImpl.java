package com.sinog2c.service.impl.system;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.commutationParole.UvFlowDocMapper;
import com.sinog2c.dao.api.system.PrintSchemeConfigMapper;
import com.sinog2c.dao.api.system.PrintSchemeMapper;
import com.sinog2c.dao.api.system.SystemResourceMapper;
import com.sinog2c.dao.api.system.SystemTemplateMapper;
import com.sinog2c.dao.api.user.UserReportMapper;
import com.sinog2c.model.commutationParole.UvFlowDoc;
import com.sinog2c.model.system.PrintScheme;
import com.sinog2c.model.system.PrintSchemeConfig;
import com.sinog2c.model.system.PrintSchemeConfigKey;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.PrintSchemeService;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.StringNumberUtil;
@Service
public class PrintSchemeServiceImpl implements PrintSchemeService {

	@Autowired
	private PrintSchemeMapper printSchemeMapper;
	@Autowired
	private PrintSchemeConfigMapper printSchemeConfigMapper;
	@Autowired
	private SystemTemplateMapper systemTemplateMapper;
	@Autowired
	private UserReportMapper userReportMapper;
	@Autowired
	private UvFlowDocMapper uvFlowDocMapper;
	@Autowired
	private SystemResourceMapper systemResourceMapper;
	@Override
	public Object deleteBatchPrintSchemeByIds(HttpServletRequest request) {
		String psids = request.getParameter("ids");
		if(StringNumberUtil.notEmpty(psids)){
			String[] psidArr = psids.split(",");
			for(String psid : psidArr){
				printSchemeMapper.deleteByPrimaryKey(Integer.parseInt(psid));
			}
		}
		return null;
	}
	@Override
	public Object deletePrintSchemeById(HttpServletRequest request) {
		String psid = request.getParameter("id");
		if(StringNumberUtil.notEmpty(psid)){
			printSchemeMapper.deleteByPrimaryKey(Integer.parseInt(psid));
		}
		return null;
	}
	@Override
	public Object getPrintSchemeById(HttpServletRequest request) {
		String psid = request.getParameter("id");
		PrintScheme printScheme = null;
		if(StringNumberUtil.notEmpty(psid)){
			printScheme = printSchemeMapper.selectByPrimaryKey(Integer.parseInt(psid));
		}
		return printScheme;
	}
	@Override
	public Object getPrintSchemeList(HttpServletRequest request) {
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<Map<String,String>> list = null;
		//总数
		total = printSchemeMapper.queryPrintSchemeCount(sortField,sortOrder);
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		list =  printSchemeMapper.getPrintSchemePageList(start, end,sortField,sortOrder);
		
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	@Override
	public Object insertPrintScheme(HttpServletRequest request) {
		PrintScheme printScheme = new PrintScheme();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			printScheme.setName((String)map.get("name"));
			printScheme.setRemark((String)map.get("remark"));
			printScheme.setResid((String)map.get("resid"));
			printScheme.setSn(Short.parseShort(map.get("sn").toString()));
			printScheme.setType((String)map.get("type"));
			printScheme.setOpid(user.getUserid());
			obj = printSchemeMapper.insertSelective(printScheme);
		}
		return obj;
	}
	@Override
	public Object updatePrintScheme(HttpServletRequest request) {
		PrintScheme printScheme = new PrintScheme();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			printScheme.setPsid(Integer.parseInt(map.get("psid").toString()));
			printScheme.setName((String)map.get("name"));
			printScheme.setRemark((String)map.get("remark"));
			printScheme.setResid((String)map.get("resid"));
			printScheme.setSn(Short.parseShort(map.get("sn").toString()));
			printScheme.setType((String)map.get("type"));
			obj = printSchemeMapper.updateByPrimaryKeySelective(printScheme);
		}
		return obj;
	}
	@Override
	public Object getPrintSchemeConfigList(HttpServletRequest request) {
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<Map<String,String>> list = null;
		String psid = request.getParameter("psid");
		//总数
		total = printSchemeConfigMapper.queryPrintSchemeConfigCount(Integer.parseInt(psid),sortField,sortOrder);
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		list =  printSchemeConfigMapper.getPrintSchemeConfigPageList(start, end, Integer.parseInt(psid),sortField,sortOrder);
		
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	@Override
	public Object getPrintSchemeConfigById(HttpServletRequest request) {
		String psid = request.getParameter("psid");
		String confid = request.getParameter("confid");
		PrintSchemeConfig printSchemeConfig = null;
		if(StringNumberUtil.notEmpty(psid) && StringNumberUtil.notEmpty(confid)){
			PrintSchemeConfigKey ck = new PrintSchemeConfigKey();
			ck.setConfid(confid);
			ck.setPsid(Integer.parseInt(psid));
			printSchemeConfig = printSchemeConfigMapper.selectByPrimaryKey(ck);
		}
		return printSchemeConfig;
	}
	@Override
	public Object deleteBatchPrintSchemeConfigByIds(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		if(StringNumberUtil.notEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String ida : idArr){
				String[] id = ida.split("@");
				PrintSchemeConfigKey ck = new PrintSchemeConfigKey();
				ck.setConfid(id[1]);
				ck.setPsid(Integer.parseInt(id[0]));
				printSchemeConfigMapper.deleteByPrimaryKey(ck);
			}
		}
		return null;
	}
	@Override
	public Object deletePrintSchemeConfigById(HttpServletRequest request) {
		String psid = request.getParameter("psid");
		String confid = request.getParameter("confid");
		if(StringNumberUtil.notEmpty(psid)&&StringNumberUtil.notEmpty(confid)){
			PrintSchemeConfigKey ck = new PrintSchemeConfigKey();
			ck.setConfid(confid);
			ck.setPsid(Integer.parseInt(psid));
			printSchemeConfigMapper.deleteByPrimaryKey(ck);
		}
		return null;
	}
	@Override
	public Object insertPrintSchemeConfig(HttpServletRequest request) {
		PrintSchemeConfig printSchemeConfig = new PrintSchemeConfig();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			String configid = "";
			if("3".equals((String)map.get("ptype"))){//模版报表类型
				configid = (String)map.get("confidtext");
			}else{
				configid = (String)map.get("confid");
			}
			printSchemeConfig.setConfid(configid);
			printSchemeConfig.setPsid(Integer.parseInt(map.get("psid").toString()));
			printSchemeConfig.setPtype((String)map.get("ptype"));
			printSchemeConfig.setSn(Short.parseShort(map.get("sn").toString()));
			printSchemeConfig.setPnumber(Short.parseShort(map.get("pnumber").toString()));
			printSchemeConfig.setOpid(user.getUserid());
			obj = printSchemeConfigMapper.insertSelective(printSchemeConfig);
		}
		return obj;
	}
	@Override
	public Object updatePrintSchemeConfig(HttpServletRequest request) {
		PrintSchemeConfig printSchemeConfig = new PrintSchemeConfig();
		String data = request.getParameter("data");
		Object obj = null;
		List<Map> list = (List) JsonUtil.Decode(data);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			String configid = "";
			if("3".equals((String)map.get("ptype"))){//模版报表类型
				configid = (String)map.get("confidtext");
			}else{
				configid = (String)map.get("confid");
			}
			printSchemeConfig.setConfid((String)map.get("confid"));
			printSchemeConfig.setPsid(Integer.parseInt(map.get("psid").toString()));
			printSchemeConfig.setPtype((String)map.get("ptype"));
			printSchemeConfig.setSn(Short.parseShort(map.get("sn").toString()));
			printSchemeConfig.setPnumber(Short.parseShort(map.get("pnumber").toString()));
			obj = printSchemeConfigMapper.updateByPrimaryKeySelective(printSchemeConfig);
		}
		return obj;
	}
	
	@Override
	public Object getBiaoDanOrReportByType(HttpServletRequest request) {
		String ptype = request.getParameter("ptype");//1 为表单， 2为报表
		List<Map> list = null;
		if(!StringNumberUtil.isNullOrEmpty(ptype)){
			if("1".equals(ptype)){//选得是表单类型
				list = systemTemplateMapper.selectAllTemplate();
			}else if("2".equals(ptype)){//选得是报表类型
				list = userReportMapper.selectAllReport();
			}
		}
		return list;
	}
	@Override
	public List<PrintScheme> getPrintSchemeComboBox(HttpServletRequest request) {
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		String userid = null;
		if(user!=null){
			userid = user.getUserid();
		}
		List<PrintScheme> schemeList = printSchemeMapper.getPrintSchemeComboBox(userid);
		return schemeList;
	}
	
	@Override
	public Object printBatch(HttpServletRequest request) {
		String caseyear = request.getParameter("caseyear");//案件年
		String printscheme = request.getParameter("printscheme");//打印方案
		String casetype = request.getParameter("casetype");//案件类型
		String anjianhao = request.getParameter("anjianhao");//案件号
		String casetypetext = request.getParameter("casetypetext");//案件类型文本
		String xingqileixing = request.getParameter("xingqileixing");//刑期类型
		Object uobj = request.getSession().getAttribute("session_user_key");
		SystemUser user = null;
		if(uobj instanceof SystemUser){
			user = (SystemUser)uobj;
		}
		String departid = user.getOrganization().getOrgid();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(!StringNumberUtil.isNullOrEmpty(printscheme) && !StringNumberUtil.isNullOrEmpty(caseyear) && !StringNumberUtil.isNullOrEmpty(casetype) && !StringNumberUtil.isNullOrEmpty(anjianhao)){
			PrintScheme printScheme = printSchemeMapper.selectByPrimaryKey(Integer.parseInt(printscheme));//获取打印方案
			List<PrintSchemeConfig> pscList = printSchemeConfigMapper.selectConfigsByPrintSchemeKey(printScheme.getPsid());//获取打印方案详细配置
			String yearCaseNumberSql = StringNumberUtil.getYearCaseNumberSql(caseyear,anjianhao,"yearcasenumber");
			//System.out.println("---------------------" + yearCaseNumberSql);
			
			String lv = user.getOrganization().getUnitlevel();
			
			if(StringNumberUtil.notEmpty(lv) && ("6".equals(lv)||"7".equals(lv))){//法院用 原来的法院不能用，因此加了这段代码。
//				List<UvCourtBigData> doclist = new ArrayList<UvCourtBigData>();
//				String schemetype = printScheme.getType();//类型为jyjxjs时走不同方法
//				if(StringNumberUtil.notEmpty(schemetype) && "jyjxjs".equals(schemetype)){
//					doclist = uvCourtBigDataMapper.getCourtJyPrintDocs(printScheme.getPsid(),departid, yearCaseNumberSql,casetypetext);
//				}else{
//					doclist = uvCourtBigDataMapper.getCourtPrintDocs(printScheme.getPsid(),departid, yearCaseNumberSql,casetypetext);
//				}
//				for(UvCourtBigData cbd : doclist){
//					String anhao = (cbd.getYearcasenumber().toString().substring(4));
//					String baseorotherid = String.valueOf(cbd.getBaseorotherid());
//					for(PrintSchemeConfig printSchemeConfig : pscList){
//						String ptype = printSchemeConfig.getPtype();
//						String confid = printSchemeConfig.getConfid();
//						//文书类型相等的或 方案类型为模版报表（3）并且配置confid+baseorotherid 等于文书类型的
//						if(confid.equals(cbd.getDoctype()) || ("3".equals(ptype) && cbd.getDoctype().startsWith(confid))){
//							Map<String,String> map = new HashMap<String,String>();
//							map.put("text", cbd.getDocconent());
//							map.put("num", printSchemeConfig.getPnumber().toString());
//							map.put("anhao", anhao);
//							map.put("niandu", caseyear);
//							map.put("applyname", cbd.getApplyname());
//							map.put("printschemename", printScheme.getName());
//							list.add(map);
//						}
//					}
//				}
			}else{
				List<UvFlowDoc> uvFdmList = uvFlowDocMapper.selectFlowDocByKeys(printScheme.getPsid(),departid, yearCaseNumberSql,casetypetext,xingqileixing );//根据年度案号和打印方案获取要打印的文书
				for(UvFlowDoc uvFlowDoc : uvFdmList){
					String anhao = uvFlowDoc.getYearcasenumber().toString().substring(4);
					for(PrintSchemeConfig printSchemeConfig : pscList){
						if(printSchemeConfig.getConfid().equals(uvFlowDoc.getTempletname())){
							Map<String,String> map = new HashMap<String,String>();
							map.put("text", uvFlowDoc.getDocconent());
							map.put("num", printSchemeConfig.getPnumber().toString());
							map.put("anhao", anhao);
							map.put("niandu", caseyear);
							list.add(map);
						}
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public Object selectResourcesByType(HttpServletRequest request) {
		List<SystemResource> resList = systemResourceMapper.selectResourcesByType(GkzxCommon.RESOURCE_PRINTSCHEME);
		return resList;
	}
	
}
