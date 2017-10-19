package com.sinog2c.mvc.controller.flow;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowArchivesCode;
import com.sinog2c.model.flow.FlowArchivesView;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseArchives;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonFormService;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.commutationParole.TbxfScreeningService;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.flow.FlowArchivesCodeService;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 登录相关的控制器,类似于 Struts中的Action<br/>
 * @ Controller 声明这是一个处理器类 由 servlet-context.xml 中定义,自动扫描, 非线程安全, 单例<br/>
 * 如果要在方法间共享变量，请使用 ThreadLocal<br/>
 */
@Controller
@RequestMapping("/flow")
public class FlowController extends FlowControllerBase{
	@Resource
	private FlowArchivesCodeService flowArchivesCodeService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private PrisonerService prisonerservice;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private TbxfScreeningService tbxfScreeningService;
	@Autowired
	private CommonSQLSolutionService commonSQLSolutionService;
	@Autowired(required=true)
	private CommonSQLSolutionService commonService;
	@Autowired
	private DataTransferService dataTransferService;
	@Autowired
	private FlowArchivesService flowArchivesService;
	@Autowired
	private CommonFormService commonFormService;
	
	//电子档案
	/**
	 * 收集编目
	 * 
	 * @return
	 */
	@RequestMapping("/archivescollection.action")
	public Object archivesCollection(HttpServletRequest request) {
		//资源对象
		ModelAndView mav = new ModelAndView("archives/archives_collection");

		return mav;
	}
	/**
	 * 档案导入 进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/toimportarchives.action")
	public Object toImportArchives(HttpServletRequest request) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		String archiveseal = (jyconfig.getProperty("archiveseal")== null?"":jyconfig.getProperty("archiveseal"));
		request.setAttribute("provincecode", provincecode);
		request.setAttribute("archiveseal", archiveseal);
		SystemUser user = getLoginUser(request);
		request.setAttribute("username", user.getName());
		//资源对象
		ModelAndView mav;
		if("6100".equals(provincecode))
			mav= new ModelAndView("archives/sx_import_archives");//陕西档案导入页面
		else
			mav= new ModelAndView("archives/import_archives");
		
		return mav;
	}
	/**
	 * 档案导入文件命名规范检查
	 * 
	 * @return
	 */
	@RequestMapping(value = "/importcheck.action")
	@ResponseBody
	public Object importCheck(HttpServletRequest request) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		//用户对象
		SystemUser user = getLoginUser(request);
		
		//参数
		String name = "";
		List<String> errorlist = new ArrayList<String>();
		ArrayList<String> crimelist = new ArrayList<String>();
		ArrayList<String> checkList = new ArrayList<String>();
		try {
			String jsonvalue = request.getParameter("json");
			ArrayList<String> data = (ArrayList) JSON.parseArray(jsonvalue, Object.class);
//	  		Map<String,Object> map = (Map<String, Object>) JSONObject.parse(jsonvalue);
	  		List<TbprisonerBaseinfo> list = (List<TbprisonerBaseinfo>) prisonerService.selectAllByDepartid(user.getOrgid());
	  		
	  		//存放监狱所有犯人的集合信息
	  		if(list!=null && list.size()>0){
	  			for(TbprisonerBaseinfo k:list){
	  			if("6100".equals(provincecode)){
	  				String strname2 = k.getCrimid();
	  				crimelist.add(strname2);
	  			}else{
	  				String strname = "";
	  				if(GkzxCommon.PROVINCE_SHANGDONG.equals(provincecode)){
	  					strname = k.getCrimid()+k.getName();
	  				}else{
	  					strname = k.getCrimid()+"_"+k.getName();
	  				}
	  				crimelist.add(strname);
	  			}
		  		}
	  		}
	  		
	  		if(data!=null && data.size()>0){
	  			String templist= data.get(0).toString();
				if(templist!=null){
					String[] strlist = templist.split(",");
					for (String m : strlist) {
		 	        	String[] key = m.split(":");
		 	        	//验证罪犯编号
		 				if(!crimelist.contains(key[0])){
	 					name = GkzxCommon.NOT_EXIST_CRI.replace("@", key[0]);
	 					if(!errorlist.contains(name)){
		 						errorlist.add(name);
	 					}
	 				}else{
		 					//验证文件命名, 012_001_2008@考核登记本_0001.JPG
			 				checkList.clear();
		 	      			String[] arrFile=m.split(":");
		 	      			String[] arrFileName=arrFile[1].split("_");
		 	      			String[] arrFileName2=arrFile[1].split(" ");//陕西档案命名方式:档案类别+空格+排序号.jpg

		 	      			//格式不正确  012_001_2010@考核登记本_0003.JPG 
		 	      			if (arrFileName.length != 4 && arrFileName2.length != 2){
		 	      				name = GkzxCommon.NOT_EXIST_FILE.replace("@", arrFile[0]).replace("#", arrFile[1]);
		 						errorlist.add(name);
		 	      			}else{
		 	      				int intIndex = 0;
		 	      				String strXuhao = null;
		 	      				String strKeys = null;
		 	      				if(arrFileName.length == 4){
		 	      					intIndex = arrFileName[3].indexOf(".");
		 	      					strXuhao = arrFileName[3].substring(0, intIndex);
			 		      		    strKeys = arrFileName[0] + arrFileName[1] + strXuhao;
			 		      		    if (arrFileName[2].contains("@")) {
			 		      				String[] arrName = arrFileName[2].split("@");
			 		      				//年度考核本的特殊处理.012_001_2008@考核登记本_0001.JPG
			 		      				strKeys = arrFileName[0] + arrFileName[1] + arrName[0] + strXuhao;
			 		      			}
		 	      				}else if(arrFileName2.length == 2){//陕西档案命名方式
		 	      					intIndex = arrFileName2[1].indexOf(".");
		 	      					strXuhao = arrFileName2[1].substring(0, intIndex);
			 		      		    strKeys = strXuhao;
		 	      				} 
		 	      				
		 	      				
		 		      			if (intIndex < 0){
		 		      				name = GkzxCommon.NOT_EXIST_FILE.replace("@", arrFile[0]).replace("#",arrFile[1]);
		 							errorlist.add(name);
		 		      			}
		 		      			if (checkList.indexOf(strKeys) > -1){
		 		      				name = GkzxCommon.NOT_EXIST_FILE.replace("@", arrFile[0]).replace("#", arrFile[1]);
		 							errorlist.add(name);
		 		      			}
		 		      			checkList.add(strKeys);
		 	      			}
	 	      			}
					}
					//
				}
	  		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  errorlist;
	}
	/**
	 * 档案批量导入
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importarchives.action")
	@ResponseBody
	public void importArchives(HttpServletRequest request , HttpServletResponse response) throws Exception {
		//用户对象		
		SystemUser user = getLoginUser(request);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		
		//定义参数
		int sbcount = 0;//文件导入失败数
		String returnval = GkzxCommon.ZERO;
		String value = "";
		String describe = "";
		String criminalid ="";//罪犯编号
		String cbiname = "";//罪犯姓名
		String filename ="";//文件名称
		
		//获取参数
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String filecount = request.getParameter("filecount");//文件名集合总数
		String filepathname = request.getParameter("filepathname");//文件路径名
		String aipfileStr = request.getParameter("aipFileStr");//大字段
		
		
		
		try{
//			String textname= "c:\\test.txt";
//			File file = new File(textname);
//			
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			StringBuffer buf  = new StringBuffer();
//			char[] buffer = new char[1024];
//			int n =0;
//			String temp = "";
//			while((n=br.read(buffer))!=-1){
//				 buf = buf.append(buffer);
//			}
			//SB.getBytes("ISO-8859-1"),"UTF-8");.toString().getBytes("ISO-8859-1")
//			while ((temp = br.readLine()) != null) {
//				   buf = buf.append(temp);
//				   // System.getProperty("line.separator")
//				   // 行与行之间的分隔符 相当于“\n”
//				   //buf = buf.append(System.getProperty("line.separator"));
//			}
//			br.close();
//			aipfileStr=buf.toString();
			String orgid = user.getOrganization().getOrgid();//申请人部门ID
			String[] filenames = filepathname.split("\\\\");
			if(filenames.length >= 2){
				filename = filenames[filenames.length-1].substring(0,filenames[filenames.length-1].indexOf("."));
				String[] prisons = filenames[filenames.length-2].split("_");
				if(GkzxCommon.PROVINCE_SHANGDONG.equals(provincecode)){
					criminalid = prisons[0].substring(0,10);
				}else{
					criminalid = prisons[0];
				}
				if(prisons.length==2) cbiname = prisons[1];
				else{
					TbprisonerBaseinfo base = prisonerservice.getBasicInfoByCrimid(criminalid);
					cbiname = base.getName();
				}
				describe = "["+filenames[filenames.length-2]+"] "+filename;
				if(criminalid!=null && !"".equals(criminalid)){
					TbprisonerBaseCrime crime = prisonerservice.getCrimeByCrimid(criminalid);
					if(crime!=null){
						orgid = crime.getOrgid1();
					}
				}
	    	}
			//流程流转
			Map<String,Object> tempmap = new HashMap<String,Object>();
			tempmap.put("resid", resid);//和流程相关按钮ID
			tempmap.put("orgid", orgid);//申请人部门ID
			tempmap.put("flowdefid", flowdefid);//自定义id
			tempmap.put("operateType", "yes");//状态
			tempmap.put("conent", filename);//内容
			tempmap.put("applyid", criminalid);//申请人ID
			tempmap.put("applyname", cbiname);//申请人名称
			tempmap.put("docconent", aipfileStr);//审批大字段
			
			Object obj = uvFlowService.approveArchives(user,tempmap);
			returnval = String.valueOf(obj);
			
			if(returnval.equals("-1")){
				sbcount = -1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			sbcount = -1;
		}
		

		request.setCharacterEncoding("utf-8");
		
		value = filecount+","+ sbcount+","+ describe;
		PrintWriter out = response.getWriter();
		out.write(value);
		out.close();
	}
	
	//测试文字编码e
	/**
	 * 档案审批鉴定进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/toarchivesapprove.action")
	public Object toArchivesApprove(HttpServletRequest request) {
		//资源对象
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		String archiveShenPiSeal = (jyconfig.getProperty("archiveShenPiSeal")== null?"":jyconfig.getProperty("archiveShenPiSeal"));//审批是否签章
		request.setAttribute("archiveShenPiSeal", archiveShenPiSeal);
		ModelAndView mav = new ModelAndView("archives/archives_approve");
		return mav;
	}
	
	
	/**
	 * 档案审批鉴定列表显示信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getarchives.action")
	@ResponseBody
	public Object getArchives(HttpServletRequest request,HttpServletResponse response) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		UvFlow uvFlow = new UvFlow();
		FlowArchivesView flowArchivesView = new FlowArchivesView();
		
		//定义变量
		String suid = "";
		String result = "";
		String state = "-1";//流转状态
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		ArrayList<Object> data = new ArrayList<Object>();
		
		//取得参数
		String ispermission = request.getParameter("ispermission");//是否区分权限
		
		//获取当前菜单ID 对应的自定义流程ID 
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		map.put("state", state);
		map.put("orgid",user.getOrgid());
    	
    	if(user!=null){
			suid = user.getUserid();//用户ID
			String departid = user.getOrganization().getOrgid();
			map.put("departid", departid);
			//获取该用户拥有的按钮资源id
			if(GkzxCommon.ONE.equals(ispermission)){
				map.put("connsql", "1=1");
			}else{
				Object buttonstr = this.returnButtonResourceByUser(user,null,null);
				map.put("connsql", buttonstr);
			}
	    	
		}
    	
//    	//获取档案信息
//    	List<FlowArchives> flowArchList =  flowArchivesService.selectAllWithOutClob();
//    	HashMap<String,Object> archmap = new HashMap<String,Object>();
//    	if(flowArchList!=null && flowArchList.size()>0){
//    		for(FlowArchives arch:flowArchList){
//    			archmap.put(arch.getFlowid(), arch.getArchiveid());
//    		}
//    	}
    	
    	//获取档案查看信息
    	List<FlowArchivesView> flowArchViewList =  flowArchivesViewService.selectAllBySuid(user.getOrgid());
    	HashMap<String,Object> archviewmap = new HashMap<String,Object>();
    	if(flowArchViewList!=null && flowArchViewList.size()>0){
    		for(FlowArchivesView arch:flowArchViewList){
    			archviewmap.put(arch.getFlowid(), arch);
    		}
    	}
    	
    	//获取总条数
    	int count = uvFlowService.countByMapParams(map);
		List<UvFlow> list= uvFlowService.findByMapParams(map);
		//
		String flowids = "";
		StringBuilder sb = new StringBuilder("(");
		for(UvFlow uv : list){
			sb.append("flowid='").append(uv.getFlowid()).append("'").append(" or ");
		}
		flowids = sb.toString();
		flowids = StringNumberUtil.removeLastStr(flowids, " or ");
		flowids += ")";
		//
		
		List<Map<String,Object>> flowArchList = null;
		if(list!=null && list.size() > 0){
			flowArchList = flowArchivesService.getFlowarchiveidByFlowid(flowids);
		}
		
    	Map<String,Object> archmap = new HashMap<String,Object>();
    	if(flowArchList!=null && flowArchList.size()>0){
    		for(Map<String,Object> arch : flowArchList){
    			archmap.put(arch.get("flowid").toString(), arch.get("archiveid"));
    		}
    	}
		
		
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				uvFlow = list.get(i);
				HashMap<String,Object> tempmap = new HashMap<String,Object>();
				tempmap.put("flowid", uvFlow.getFlowid());
				tempmap.put("flowdefid", uvFlow.getFlowdefid());
				tempmap.put("flowdraftid", uvFlow.getFlowdraftid());
				tempmap.put("applyid", uvFlow.getApplyid());
				tempmap.put("applyname", uvFlow.getApplyname());
				tempmap.put("conent", uvFlow.getConent());
				tempmap.put("endsummry", uvFlow.getEndsummry());
				tempmap.put("startsummry", uvFlow.getStartsummry());
				tempmap.put("commenttext", uvFlow.getCommenttext());
				tempmap.put("startdatetime", uvFlow.getStartdatetime());
				tempmap.put("enddatetime", uvFlow.getEnddatetime());
				tempmap.put("optime", uvFlow.getOptime());
				tempmap.put("opname", uvFlow.getOpname());
				tempmap.put("orgid", uvFlow.getOrgid());
				tempmap.put("state", uvFlow.getState());
				tempmap.put("nodeid", uvFlow.getNodeid());
				tempmap.put("list", "");
				
				//获取归档ID
				if(archmap.containsKey(uvFlow.getFlowid())){
					tempmap.put("archiveid", archmap.get(uvFlow.getFlowid()));
				}
				if(archviewmap.containsKey(uvFlow.getFlowid())){
					flowArchivesView = (FlowArchivesView) archviewmap.get(uvFlow.getFlowid());
					if(flowArchivesView!=null){
						tempmap.put("starttime", sdf.format(flowArchivesView.getStarttime()));
						tempmap.put("endtime", sdf.format(flowArchivesView.getEndtime()));
					}
				}
				data.add(tempmap);
			}
		}
		
		resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	
	
	
	
	//批量提交
	@RequestMapping(value = "/commuteParoleBatchOperate")
	@ResponseBody
	public Object commuteParoleBatchOperate(HttpServletRequest request){
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		
		//定义变量
		Object resultval ="";
		
		//取得参数
		String resid = request.getParameter("menuid");//和流程相关按钮ID
		String tempid = request.getParameter("tempid");//表单ID
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String operateType = request.getParameter("operateType");
		
		
//		String conent = request.getParameter("conent");//内容	
//		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
//		String commenttext = request.getParameter("commenttext");//审批意见
		String id = request.getParameter("id");
		if(!StringNumberUtil.notEmpty(id)){
			return "-1";
		}
		String[] idArr = id.split(",");
		
		for(String temData : idArr){
			String[] temDataArr = temData.split("@");
			String applyid = temDataArr[0];//申请人ID
			String orgid = temDataArr[1];
			String flowid = temDataArr[2];
			String applyname = temDataArr[3];//申请人名称
			
			//(r.crimid+"@"+r.orgid+"@"+r.flowid+"@"+r.name);
			
			Map paraMap = new HashMap();
			paraMap.put("flowid", flowid);
			paraMap.put("flowdefid", flowdefid);
			String docconent = flowBaseOtherService.getDocconentByCondition(paraMap);
//	 	    String docconent = request.getParameter("docconent");//审批大字段
			
			//流程流转
			Map<String,Object> tempmap = new HashMap<String,Object>();
			tempmap.put("resid", resid);//和流程相关按钮ID
			tempmap.put("tempid", tempid);
			tempmap.put("flowid", flowid);//流程ID
			tempmap.put("flowdefid", flowdefid);//流程ID
			tempmap.put("operateType", operateType);
			tempmap.put("applyid", applyid);
			tempmap.put("orgid", orgid);
			tempmap.put("applyname", applyname);
			tempmap.put("docconent", docconent);//审批大字段
			String _ip = IPUtil.getClientIP(request);
			tempmap.put("_ip", _ip);
//			tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
//			tempmap.put("commenttext", commenttext);//审批意见
//			tempmap.put("conent", conent);//内容
			
			//判断表单上是否放了 申请人ID
//			if(!aipmap.containsKey("applyid")){
//				tempmap.put("applyid", applyid);//申请人ID
//				tempmap.put("applyname", applyname);//申请人名称
//			}
			
//			tempmap.putAll(aipmap);
			resultval = uvFlowService.operationFlow(user, tempmap);
		}
		
		return resultval;
	}
	
	
	/**
	 * 审批鉴定  批量审批 、退回
	 * 
	 * @return
	 */
	@RequestMapping(value = "/batchapprove.action")
	@ResponseBody
	public Object batchApprove(HttpServletRequest request) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		String archiveShenPiSeal = jyconfig.getProperty("archiveShenPiSeal");
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		JSONMessage message = JSONMessage.newMessage();
		FlowBaseArchives flowBaseArchives = new FlowBaseArchives();
		
		//定义变量
		Object returnval = 0;
		String suid = "";
		String departid = "";
		String remark = "";
		String docyear = "";
		String archname = "";
		Date now = new Date();
		Short isattached = 0;
		String danganliebie = "";//档案类别
		String danganliexing = "";//档案类别(小类);
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.YEARFORMAT);
		HashMap<String,Short> attachemap = new HashMap<String,Short>();
		
		//取得参数
		String rank = request.getParameter("rank");
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowids");
		String approve = request.getParameter("approve");
		String applyid = request.getParameter("applyids");
		String filename = request.getParameter("conents");
		String flowdefid = request.getParameter("flowdefid");
		String retention = request.getParameter("retention");
		String applyname = request.getParameter("applynames");
		String archiveid = request.getParameter("archiveids");
		String commenttext = request.getParameter("commenttext");
		String flowdraftid = request.getParameter("flowdraftids");
		String orgid = request.getParameter("orgids");//申请人部门ID
		
		if(user!=null){
			suid = user.getUserid();//用户ID
			departid = user.getDepartid();
		}
		Map map = new HashMap<String,String>();
		map.put("departid",departid);
		//查询电子档案类型编码
		List<FlowArchivesCode> codelist= flowArchivesCodeService.selectAllByDepid(map);
		Map<String,String> codename=new HashMap<String,String>();
		for(FlowArchivesCode code:codelist){
			attachemap.put(code.getCodeid(), code.getIspositive());
			codename.put(code.getName(),code.getCodeid() );
		}
		
		//判断非空
		if(flowid!=null && flowdraftid!=null && applyid!=null && filename!=null && archiveid!=null){
			String[] flowids = flowid.split(",");
			String[] flowdraftids = flowdraftid.split(",");
			String[] applyids = applyid.split(",");
			String[] filenames = filename.split(",");
			String[] applynames = applyname.split(",");
			String[] archiveids = archiveid.split(",");
			String[] orgids = orgid.split(",");
			if(filenames!=null && filenames.length>0){
				for(int i=0;i<filenames.length;i++){
					String[] paixus ;
					if("6100".equals(provincecode)){
						paixus = filenames[i].split(" ");
					}else{
						paixus= filenames[i].split("_");
					}
					if(0<paixus.length&&paixus.length == 4){
						danganliebie = paixus[0];
						danganliexing =paixus[0]+paixus[1];
						archname = paixus[2]+paixus[3];
						if(paixus[2].indexOf("@")>=0){
							docyear = paixus[2].substring(0,paixus[2].indexOf("@"));
						}else{
							docyear = sdf.format(now);
						}
					}
					//陕西电子档案审批
					if(0<paixus.length&&paixus.length == 2){
						danganliebie = paixus[0];
						if(codename.containsKey(danganliebie)){
							danganliexing=codename.get(danganliebie);
						}
						archname = paixus[0]+paixus[1];
						if(paixus[1].indexOf("@")>=0){
							docyear = paixus[1].substring(0,paixus[1].indexOf("@"));
						}else{
							docyear = sdf.format(now);
						}
					}
					
					//电子档案基本表（审批完成后） TBFLOW_BASE_ARCHIVES
					if("yes".equals(approve)){//yes 通过
						remark = applynames[i]+"档案鉴定通过";
					}
				
					flowBaseArchives.setDepartid(departid);
					flowBaseArchives.setPersonid(applyids[i]);
					flowBaseArchives.setPersonname(applynames[i]);
					flowBaseArchives.setArchiveid(archiveids[i]);
					flowBaseArchives.setDocyear(Integer.parseInt(docyear));
					flowBaseArchives.setDocid(danganliexing);
					flowBaseArchives.setName(archname);
					flowBaseArchives.setType(Short.valueOf(GkzxCommon.ONE));
					if(attachemap.containsKey(danganliexing)){
						isattached = attachemap.get(danganliexing);
					}
					flowBaseArchives.setIsattached(Short.valueOf(isattached));
					flowBaseArchives.setRank(Short.valueOf(rank));
					flowBaseArchives.setRetention(Short.valueOf(retention));
					if("6100".equals(provincecode))
					flowBaseArchives.setClassification(danganliexing);
					else
					flowBaseArchives.setClassification(danganliebie);
					flowBaseArchives.setOpid(suid);
					flowBaseArchives.setOptime(now);
					flowBaseArchives.setRemark(remark);
					
					Map<String,Object> tempmap = new HashMap<String,Object>();
					tempmap.put("resid", resid);//操作按钮资源ID
					tempmap.put("orgid", orgids[i]);//申请人部门ID
					tempmap.put("flowid", flowids[i]);//流程ID
					tempmap.put("flowdefid", flowdefid);//流程自定义ID
					tempmap.put("conent", filenames[i]);//内容
					tempmap.put("applyid", applyids[i]);//申请人ID
					tempmap.put("commenttext", commenttext);//审批意见
					tempmap.put("applyname", applynames[i]);//申请人名称
					tempmap.put("flowdraftid", flowdraftids[i]);//流程草稿ID
					tempmap.put("operateType", approve);//流程草稿ID
					returnval = uvFlowService.batchApprove(flowBaseArchives,user,tempmap);
					if(StringNumberUtil.notEmpty(archiveShenPiSeal) && "1".equals(archiveShenPiSeal)){//需要签章
						int rows = 0;
						String docconent = request.getParameter("docconent");
						rows = this.uvFlowService.saveFlowArchives(docconent,flowids[i],null, applyids[i],user);
						if (1 == rows) {
							message.setInfo("添加成功!");
							message.setSuccess();
						} else {
							message.setInfo("操作失败!");
						}
					}
				}
			}
		}
		
		return returnval;
	}
	
	/**
	 * 档案借阅申请进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/toborrowapply.action")
	public Object toBorrowApply(HttpServletRequest request) {
		//资源对象
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		ModelAndView mav = new ModelAndView("archives/borrow_apply");
		
		return mav;
	}
	/**
	 * 档案借阅申请列表显示信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getborrowarchives.action")
	@ResponseBody
	public void getBorrowArchives(HttpServletRequest request,HttpServletResponse response) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		FlowBaseArchives flowBaseArchives = new FlowBaseArchives();
		
		//定义变量
		String result = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		ArrayList<Object> data = new ArrayList<Object>();
		
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String flowdefid = request.getParameter("flowdefid");//流程自定义ID
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowdefid", flowdefid);
	    	map.put("applyid", user.getUserid());
	    	map.put("key", key);
	    	map.put("orgid", user.getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	//获取该用户拥有的按钮资源id
			Object buttonstr = this.returnButtonResourceByUser(user,null,null);
	    	map.put("connsql", buttonstr);
	    	
	    	//获取总条数
	    	int count = flowBaseArchivesService.countAllByCondition(map);
			List<FlowBaseArchives> list= flowBaseArchivesService.selectAllByCondition(map);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					flowBaseArchives = list.get(i);
					HashMap<String,Object> tempmap = new HashMap<String,Object>();
					tempmap.put("archiveid", flowBaseArchives.getArchiveid());
					tempmap.put("classification", flowBaseArchives.getClassification());
					tempmap.put("docid", flowBaseArchives.getDocid());
					tempmap.put("docyear", flowBaseArchives.getDocyear());
					tempmap.put("isattached", flowBaseArchives.getIsattached());
					tempmap.put("name", flowBaseArchives.getName());
					tempmap.put("personid", flowBaseArchives.getPersonid());
					tempmap.put("personname", flowBaseArchives.getPersonname());
					tempmap.put("rank", flowBaseArchives.getRank());
					tempmap.put("retention", flowBaseArchives.getRetention());
					tempmap.put("type", flowBaseArchives.getType());
					data.add(tempmap);
				}
			}
			
			resultmap.put("data", data);
			resultmap.put("total", count);

			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 借阅申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "/borrowapply.action")
	@ResponseBody
	public Object borrowApply(HttpServletRequest request) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		
		//定义变量
		Object returnval = "0";
		String suid = "";
		String orgid = "";
		String suname = "";
		//取得参数
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String conent = request.getParameter("conents");//档案名称
		String operateType = request.getParameter("operateType");//档案名称
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String personid = request.getParameter("personid");//档案所属人ID
		String personname = request.getParameter("personname");//档案所属人名字
		String archiveid = request.getParameter("archiveids");//归档ID
		String starttime = request.getParameter("starttimes");//档案申请时间
		String endtime = request.getParameter("endtimes");//档案查看结束时间
		
		if(user!=null){
			orgid = user.getOrgid();//申请人部门ID
			suid =user.getUserid();//申请人ID
			suname =user.getName();//申请人名称
		}
		
		if(conent!=null && personid!=null && archiveid!=null){
			String[] conents = conent.split(",");
			String[] personids = personid.split(",");
			String[] personnames = personname.split(",");
			String[] archiveids = archiveid.split(",");

			for(int i=0;i<conents.length;i++){
				//借阅申请流程
				Map<String,Object> tempmap = new HashMap<String,Object>();
				tempmap.put("resid", resid);//申请人部门ID
				tempmap.put("orgid", orgid);
				tempmap.put("flowdefid", flowdefid);
				tempmap.put("personid", personids[i]);
				tempmap.put("archiveid", archiveids[i]);
				tempmap.put("conent", conents[i]);//内容
				tempmap.put("applyid", personids[i]);//罪犯编号ID
				tempmap.put("applyname", personnames[i]);//罪犯姓名
				tempmap.put("starttime", starttime);//档案查看开始时间
				tempmap.put("endtime", endtime);//档案查看结束时间
				tempmap.put("operateType", operateType);//档案查看结束时间
				
				Object obj = uvFlowService.approveArchives(user,tempmap);
				returnval = String.valueOf(obj);
			}
		}
		
		return returnval;
	}
	/**
	 * 档案借阅审批进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/toborrowapprove.action")
	public Object toBorrowApprove(HttpServletRequest request) {
		//资源对象
		ModelAndView mav = new ModelAndView("archives/borrow_approve");
		
		return mav;
	}
	/**
	 * 借阅审批
	 * 
	 * @return
	 */
	@RequestMapping(value = "/borrowapprove.action")
	@ResponseBody
	public Object borrowApprove(HttpServletRequest request) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		
		//定义变量
		Object returnval = "0";
		
		//取得参数
		String suid = "";
		String orgid = "";
		String suname = "";
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String conent = request.getParameter("conents");//档案名称
		String operateType = request.getParameter("operateType");//档案名称
		String flowid = request.getParameter("flowids");//流程ID
		String endtime = request.getParameter("endtimes");//档案查看结束时间
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String starttime = request.getParameter("starttimes");//档案申请时间
		String commenttext = request.getParameter("commenttext");//审批意见
		String flowdraftid = request.getParameter("flowdraftids");//流程草稿ID
		
		if(user!=null){
			suid = user.getUserid();//申请人ID
			suname = user.getName();//申请人名称
			orgid = user.getOrganization().getOrgid();//申请人部门ID
		}
		if(conent!=null && flowid!=null && flowdraftid!=null && starttime!=null && endtime!=null){
			String[] conents = conent.split(",");
			String[] flowids = flowid.split(",");
			String[] flowdraftids = flowdraftid.split(",");
			String[] starttimes = starttime.split(",");
			String[] endtimes = endtime.split(",");
			for(int i=0;i<conents.length;i++){
				//流程流转
				Map<String,Object> tempmap = new HashMap<String,Object>();
				tempmap.put("resid", resid);//按钮ID
				tempmap.put("orgid", orgid);//申请人部门ID
				tempmap.put("flowid", flowids[i]);//流程ID
				tempmap.put("flowdefid", flowdefid);//流程ID
				tempmap.put("flowdraftid", flowdraftids[i]);//流程草稿ID
				tempmap.put("conent", conents[i]);//内容
				tempmap.put("applyid", suid);//申请人ID
				tempmap.put("applyname", suname);//申请人名称
				tempmap.put("commenttext", commenttext);//审批意见
				tempmap.put("starttime", starttimes[i]);//档案查看开始时间
				tempmap.put("endtime", endtimes[i]);//档案查看结束时间
				tempmap.put("operateType", operateType);//档案查看结束时间
				
				Object obj = uvFlowService.approveArchives(user,tempmap);
				returnval = String.valueOf(obj);
			}
		}
		
		return returnval;
	}
	/**
	 * 档案查看进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/tolookarchives.action")
	public Object toLookArchives(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		if(StringNumberUtil.isEmpty(crimid)){
			crimid = request.getParameter("applyid");
			if(StringNumberUtil.isEmpty(crimid)){
				String flowdraftid = request.getParameter("flowdraftid");
				if(StringNumberUtil.notEmpty(flowdraftid)){
					Map<String,Object> paramap = new HashMap<String,Object>();
					paramap.put("flowdraftid", flowdraftid);
					FlowBase fb = flowBaseService.getFlowBaseByMap(paramap);
					crimid = fb.getApplyid();
				}
			}
		}
		//
		String menuid = request.getParameter("menuid");
		String look = request.getParameter("look");//只查看，不操作标志
		String jxjsaj = request.getParameter("jxjsaj");//广西减刑假释案卷
		String archtype = request.getParameter("archtype");//广西减刑假释案卷
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("anjiantype",request.getParameter("anjiantype"));
		request.setAttribute("look", look);
		request.setAttribute("jxjsaj", jxjsaj);
		request.setAttribute("archtype", archtype);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		//陕西电子档案查看页面需求独立
		if("6100".equals(provincecode)){
			return new ModelAndView("archives/look_archives_sx");
		}else{
		    return new ModelAndView("archives/look_archives");
		}
	}
	
	/**
	 * 档案查看进入页面
	 * 
	 * @return
	 */
	@RequestMapping("/tolookarchivesForSD.action")
	public Object tolookarchivesForSD(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		if(StringNumberUtil.isEmpty(crimid)){
			crimid = request.getParameter("applyid");
		}
		String menuid = request.getParameter("menuid");
		String look = request.getParameter("look");//只查看，不操作标志
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("look", look);
		//资源对象
		ModelAndView mav = new ModelAndView("archives/look_archives_SD");
		return mav;
	}
	
	/**
	 * 档案查看
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lookarchives.action")
	public Object lookArchives(HttpServletRequest request) {
		findArchives(request);
		ModelAndView mav = new ModelAndView("archives/archives_view");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String archiveShenPiSeal = (jyconfig.getProperty("archiveShenPiSeal")== null?"":jyconfig.getProperty("archiveShenPiSeal"));
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("archiveShenPiSeal", archiveShenPiSeal);
		request.setAttribute("provincecode", provincecode);
		return mav;
	}
	
	/**
	 * 合并打印
	 * 
	 * @return
	 */
	@RequestMapping(value="/printArchives.action")
	@ResponseBody
	public Object printArchives(HttpServletRequest request){
		String tt= findArchives(request);
		return tt;
	}
	/**
	 * 查询档案的大字段
	 * 
	 * @return
	 */
	public String findArchives(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		//用户对象
		FlowArchives flowArchives = new FlowArchives();
		
		//定义变量
		JSONArray docconent=new JSONArray();
		
		//取得参数
		String archiveid = request.getParameter("archiveid");
		String flowid = request.getParameter("flowids");
		String resid = request.getParameter("resid");
		String isapprove = request.getParameter("isapprove");
		String ismultipage = request.getParameter("ismultipage");//是否多页
		String pagenum = request.getParameter("pagenum");//合并查看初始页码
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA);
		//归档ID 不为空
		if(archiveid!=null){
			String[] archiveids = archiveid.split(",");
			//把数据进行排序，否者文件的最后一页，显示到第一页了，出现问题。modifier：mushuhong
			if (ningxia.contains(user.getDepartid())) {
				Arrays.sort(archiveids);
			}
			for(String m:archiveids){
				String docconentStr = flowArchivesService.getArchiveDocconentByArchiveid(m);
				docconent.add(docconentStr);
			}
		}
		//流程ID 不为空
		if(flowid!=null){
			String[] flowids = flowid.split(",");
			for(String m:flowids){
				String docconentStr = flowArchivesService.getArchiveDocconentByFlowid(m);
				docconent.add(docconentStr);
			}
		}
		//表单显示用
		request.setAttribute("formcontent", docconent.toString());
		//页面判断是否走流程用
		request.setAttribute("resid", resid);
		request.setAttribute("isapprove", isapprove);
		request.setAttribute("ismultipage", ismultipage);
		request.setAttribute("pagenum", pagenum);
		return docconent.toString();
	}
	/**
	 * 处理未通过的档案
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dealarchives.action")
	public Object dealArchives(HttpServletRequest request) {
		
		// 用户对象
		FlowArchives flowArchives = new FlowArchives();
		
		//定义变量
		JSONArray docconent=new JSONArray ();
		
		//取得参数
		String archiveid = request.getParameter("archiveid");
		String flowid = request.getParameter("flowids");
		String resid = request.getParameter("resid");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		String targetPath = "";
		//归档ID 不为空
		if(archiveid!=null){
			String[] archiveids = archiveid.split(",");
			for(String m:archiveids){
				flowArchives = flowArchivesService.findByArchiveid(m);
				if(flowArchives!=null){
					if(!StringNumberUtil.isNullOrEmpty(flowArchives.getInt1()) && flowArchives.getInt1() == 1) {
						//从文件读取
						try {
//							docconent.add(FileUtil.readFromFile(GetAbsolutePath.getAbsolutePath(flowArchives.getDocconent()),GkzxCommon.encoding));
							targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, flowArchives.getDocconent());
							docconent.add(FileUtil.readFromFile(targetPath,GkzxCommon.encoding));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						docconent.add(flowArchives.getDocconent());
					}
				}
			}
		}
		//流程ID 不为空
		if(flowid!=null){
			String[] flowids = flowid.split(",");
			for(String m:flowids){
				flowArchives = flowArchivesService.findByFlowid(m);
				if(flowArchives!=null){
					if(!StringNumberUtil.isNullOrEmpty(flowArchives.getInt1()) && flowArchives.getInt1() == 1) {
						//从文件读取
						try {
//							docconent.add(FileUtil.readFromFile(GetAbsolutePath.getAbsolutePath(flowArchives.getDocconent()),GkzxCommon.encoding));
							targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, flowArchives.getDocconent());
							docconent.add(FileUtil.readFromFile(targetPath,GkzxCommon.encoding));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						docconent.add(flowArchives.getDocconent());
					}
				}
			}
		}
		//表单显示用
		request.setAttribute("formcontent", docconent.toString());
		//页面判断是否走流程用
		request.setAttribute("resid", resid);
		
		ModelAndView mav = new ModelAndView("archives/deal_archives");
		
		return mav;
	}
	/*
	 * 档案删除
	 * @return
	 */
	@RequestMapping(value = "/removearchives.json")
	@ResponseBody
	public Object removearchives(HttpServletRequest request) {
		// 用户对象
//		SystemUser user = getLoginUser(request);
		
		//取得参数
		String archiveid = request.getParameter("archiveid");
		return uvFlowService.removearchives(archiveid);
	}
	/**
	 * 档案查看列表显示信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getlookarchives.action")
	@ResponseBody
	public Object getLookArchives(HttpServletRequest request,HttpServletResponse response) {
		
		JSONMessage message = JSONMessage.newMessage();
		// 用户对象
		SystemUser user = getLoginUser(request);
		FlowBaseArchives flowBaseArchives = new FlowBaseArchives();
		
		//定义变量
		String result = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		ArrayList<Object> data = new ArrayList<Object>();
		
		try {
			//取得参数
			String personid = request.getParameter("personid")==null?"":request.getParameter("personid");
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String archclass = request.getParameter("codeid")==null?"":request.getParameter("codeid");
			String treevalue = request.getParameter("treevalue")==null?"":request.getParameter("treevalue");//复选类型用参数
			String suid = user.getUserid();//用户ID
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("applyid", suid);
	    	map.put("personid", personid);
	    	map.put("departid", user.getDepartid());
	    	map.put("archclass", archclass);
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	String provincecode = null;
	    	Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(jypro!=null){
				provincecode = jypro.getProperty("provincecode");
			}
			map.put("provinceid", provincecode);
	    	//获取总条数
			int count = 0;
			List<FlowBaseArchives> list= null;
			if(GkzxCommon.PROVINCE_SHANXI.equals(provincecode)){
				count = flowBaseArchivesService.countAllShengju(map);
				list= flowBaseArchivesService.selectAllByPageShengju(map);
			}else if(GkzxCommon.PROVINCE_SHANGDONG.equals(provincecode)){
				String condition="";
				//山东的档案支持多选
				String[] str = treevalue.split(",");
				if(str.length > 0){
					for(int i=0;i<str.length;i++){
						if(i==0){
							condition += "'"+str[i]+"'";
						}else{
							condition += ",'"+str[i]+"'";
						}
					}
				}
				map.put("condition", condition);
				count = flowBaseArchivesService.countAllForSD(map);
				list= flowBaseArchivesService.selectAllByPageForSD(map);
			}else{
				list= flowBaseArchivesService.selectAllByPage(map);
				count = flowBaseArchivesService.countAll(map);
			}
			//
			
			message.setSuccess();
			message.setData(list);
			message.setTotal(count);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return message;
	}
	
	/**
	 * 档案查看信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getlookarchivesforaip.action")
	@ResponseBody
	public Object getLookArchivesforaip(HttpServletRequest request,HttpServletResponse response) {
		String result = "";
		List<FlowBaseArchives> list= null;
		// 用户对象
		SystemUser user = getLoginUser(request);
		try {
			//取得参数
			String personid = request.getParameter("personid")==null?"":request.getParameter("personid");
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String archclass = request.getParameter("codeid")==null?"":request.getParameter("codeid");
			String treevalue = request.getParameter("treevalue")==null?"":request.getParameter("treevalue");//复选类型用参数
			String suid = user.getUserid();//用户ID
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("applyid", suid);
	    	map.put("personid", personid);
	    	map.put("departid", user.getDepartid());
	    	map.put("archclass", archclass);
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	String provincecode = null;
	    	Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(jypro!=null){
				provincecode = jypro.getProperty("provincecode");
			}
			map.put("provinceid", provincecode);
			
			if(GkzxCommon.PROVINCE_SHANXI.equals(provincecode)){
				list= flowBaseArchivesService.selectAllByPageShengju(map);
			}else if(GkzxCommon.PROVINCE_SHANGDONG.equals(provincecode)){
				String condition="";
				//山东的档案支持多选
				String[] str = treevalue.split(",");
				if(str.length > 0){
					for(int i=0;i<str.length;i++){
						if(i==0){
							condition += "'"+str[i]+"'";
						}else{
							condition += ",'"+str[i]+"'";
						}
					}
				}
				map.put("condition", condition);
				list= flowBaseArchivesService.selectAllByPageForSD(map);
			}else{
				list= flowBaseArchivesService.selectAllByPage(map);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				result+=list.get(i).getArchiveid()+",";
			}
		}
		return result.substring(0, result.length()-1);
	}
	@RequestMapping(value = {"/ajax/list.json"})
	@ResponseBody
	public Object listUserByPage(HttpServletRequest request, HttpServletResponse response) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		// 用户对象
		SystemUser user = getLoginUser(request);
		String anjiantype=request.getParameter("anjiantype");
		String type="";
		//获取参数
		String departid = user.getDepartid();
		List<FlowArchivesCode> archcodes = new ArrayList<FlowArchivesCode>();
		Map map=new HashMap();
		map.put("departid", departid);
		if(!StringNumberUtil.isNullOrEmpty(anjiantype)&&"6100".equals(provincecode)){
			if(anjiantype.equals("bwjy")){
				type="100";
			}else if(anjiantype.equals("jxjs")){
				type="000";
			}
			map.put("type", type);
		}

		String search = request.getParameter("search")==null?"":request.getParameter("search");//新处理的案件查看页面用参数
		map.put("departid",departid);
		map.put("search", search);
		try{
			//获取档案类别信息   显示类别树
			archcodes = flowArchivesCodeService.selectAllByDepid(map);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return archcodes;
	}
	/**
	 * 进入拥有档案的犯人信息的页面
	 * 
	 * @return
	 */
	@RequestMapping("/toshowcrimwitharchs.page")
	public Object toShowCrimWithArchs(HttpServletRequest request) {
		//资源对象
		ModelAndView mav = new ModelAndView("archives/toShowCrimWithArchs");
		return mav;
	}
	
	/**
	 * 进入拥有档案的犯人信息的页面
	 * 
	 * @return
	 */
	@RequestMapping("/toshowcrimwitharchsForSD.page")
	public Object toShowCrimWithArchsForSD(HttpServletRequest request) {
		//资源对象
		ModelAndView mav = new ModelAndView("archives/toShowCrimWithArchsForSD");
		
		return mav;
	}
	
	/**
	 * 获取拥有档案的犯人信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ajaxgetcrimwitharchs.json")
	@ResponseBody
	public void ajaxGetCrimWithArchs(HttpServletRequest request,HttpServletResponse response) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		TbprisonerBaseinfo baseinfo = new TbprisonerBaseinfo();
		
		//定义变量
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.DATEFORMAT);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Object> data = new ArrayList<Object>();
		
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String sdid = request.getParameter("sdid")==null?"":request.getParameter("sdid");
			
			String suid = user.getUserid();//用户ID
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<Object,Object> map = new HashMap<Object,Object>();
			if(!"".equals(sdid)){
				map.put("departid", sdid);
			}else{
				map.put("departid", user.getOrganization().getOrgid());
			}
			
			
			Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = jypro.getProperty("provincecode");
			if(user.getDepartid().equals(provincecode)){
				map.put("province", "yes");
			}
						
			
			
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	//获取总条数
	    	int count = prisonerService.countGetCrimWithArchs(map);
			List<TbprisonerBaseinfo> list= prisonerService.getCrimWithArchs(map);
			
			resultmap.put("data", list);
			resultmap.put("total", count);

			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 流程审批 流转操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/batchLiAnFlowApprove.action")
	@ResponseBody
	public Object batchLiAnFlowApprove(HttpServletRequest request,HttpServletResponse response){
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		//定义变量
		int resultval = 0;
		String _ip = IPUtil.getClientIP(request);//客户端IP
		Map<String,Object> datamap = this.parseParamMap(request);
		datamap.put("_ip", _ip);
		resultval = uvFlowService.batchJailLiAnFlowApprove(user,datamap);
		
		return resultval;
	}
	
//	/**
//	 * 流程审批 流转操作
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/batchLiAnFlowApprove.action")
//	@ResponseBody
//	public Object batchLiAnFlowApprove(HttpServletRequest request,HttpServletResponse response) {
//		
//		// 用户对象
//		SystemUser user = getLoginUser(request);
//		
//		//定义变量
//		int resultval = 0;
//		
//		//取得参数
//		String data = request.getParameter("data");//获取表单页面的数据
//		
//		String resid = request.getParameter("resid");//和流程相关按钮ID
//		String flowid = request.getParameter("flowid");//流程ID
//		String conent = request.getParameter("conent");//内容
//		String tempid = request.getParameter("tempid");//表单ID
//		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
//		String docconent = request.getParameter("docconent");//审批大字段
//		String operateType = request.getParameter("operateType");//判断新增、修改
//		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
//		String commenttext = request.getParameter("commenttext");//审批意见
//		String curyear = request.getParameter("curyear");
//		String batch = request.getParameter("batch");//批次ID
//		String shortname = request.getParameter("shortname");//单位简称(办案用)
//		String commutetype = request.getParameter("commutetype");
//		
//		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String provincecode = jypro.getProperty("provincecode");
//		
//		String casenumber = request.getParameter("casenumber")==null?"":request.getParameter("casenumber");//页面获取的案件号
//		String casetype = request.getParameter("casetype")==null?"":request.getParameter("casetype");//页面获取的字号
//		String jailcasechg = request.getParameter("jailcasechg")==null?"":request.getParameter("jailcasechg");//页面获取的字号
//		
//		String dataStr = request.getParameter("dataStr");
//		
//		Map<Object,Object> datamap = new HashMap<Object,Object>();
//		datamap.put("data", data);
//		datamap.put("resid", resid);
//		datamap.put("flowid", flowid);
//		datamap.put("conent", conent);
//		datamap.put("tempid", tempid);
//		datamap.put("flowdefid", flowdefid);
//		datamap.put("docconent", docconent);
//		datamap.put("operateType", operateType);
//		datamap.put("flowdraftid", flowdraftid);
//		datamap.put("commenttext", commenttext);
//		datamap.put("curyear", curyear);
//		datamap.put("casetype", casetype);
//		datamap.put("jailcasechg", jailcasechg);
//		
//		datamap.put("dataStr", dataStr);
//		datamap.put("batch", batch);
//		datamap.put("shortname", shortname);
//		datamap.put("commutetype", commutetype);
//		datamap.put("casenumber", casenumber);
//		
//		resultval = uvFlowService.batchJailLiAnFlowApprove(user,datamap);
//		
//		return resultval;
//	}
	
	
	/**
	 * 判断某类刑案件是否立过案
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/isHaveCaseLiAn.action")
	@ResponseBody
	public Object isHaveCaseLiAn(HttpServletRequest request,HttpServletResponse response) {
		
		//取得参数
		Map<String,Object> paramap = this.parseParamMap(request);
		
		return uvFlowService.isHaveCaseLiAn(paramap);
	}
	
	/**
	 * 判断基本信息是否提交
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/submitBaseInfo.action")
	@ResponseBody
	public Object submitBaseInfo(HttpServletRequest request){
		
		Map<String,Object> map = this.parseParamMap(request);
		
		return uvFlowService.submitBaseInfo(map);
	}
	
	
	/**
	 * 判断是否正在办案
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/isDealJxjsCase.json")
	@ResponseBody
	public Object isDealJxjsCase(HttpServletRequest request){
		
		Map<String,Object> map = this.parseParamMap(request);
		
		return uvFlowService.isDealJxjsCase(map);
	}
	
	/**
	 * 方法描述：判断 刑期变动是否已经经过审核 如果未审核那么将无法进行立案
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping(value="/submitSentchange.action")
	@ResponseBody
	public Object submitSentchange(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Object> map = this.parseParamMap(request);
		
		return uvFlowService.submitSentchange(map);
	}
	
	/**
	 * 判断某类刑案件是否立过案
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/isSJHaveCaseLiAn.action")
	@ResponseBody
	public Object isSJHaveCaseLiAn(HttpServletRequest request,HttpServletResponse response) {
		// 用户对象
//		SystemUser user = getLoginUser(request);
		//取得参数
		String flowdraftids = request.getParameter("flowdraftids");
		flowdraftids = StringNumberUtil.formatString(flowdraftids, ",");
		
		return uvFlowService.isSJHaveCaseLiAn(flowdraftids);
	}
	
	/**
	 * 方法描述：当罪犯立案成功以后必须把罪犯修改为
	 * author:mushuhong
	 * @version:2015年1月20日10:59:25
	 */
	@RequestMapping(value="/updateCriminalDetainStatus.action")
	@ResponseBody
	public Object updateCriminalDetainStatus(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		this.uvFlowService.updateCriminalDetainStatus(request, user);
		return null;
	}
//	/**
//	 * @param request
//	 * @param response
//	 * @return 成功返回0 失败返回-1
//	 */
//	@RequestMapping(value = "/jiangQuLiAn.action")
//	@ResponseBody
//	@SuppressWarnings("all")
//	public Object jiangQuLiAn(HttpServletRequest request,HttpServletResponse response) {
//		Object result = 0;
//		
//		// 用户对象
//		SystemUser user = getLoginUser(request);
//		String departid = user.getDepartid();
//		
//		//取得参数
//		String data = request.getParameter("data");//获取表单页面的数据
//		
//		String resid = request.getParameter("resid");//和流程相关按钮ID
//		String flowid = request.getParameter("flowid");//流程ID
//		String conent = request.getParameter("conent");//内容
//		String tempid = request.getParameter("tempid");//表单ID
//		String examineid = request.getParameter("examineid");//表单ID
//		String orgid = request.getParameter("orgid");//申请人的部门ID
//		String applyid = request.getParameter("applyid");//申请人ID
//		String applyname = request.getParameter("applyname");//申请人名称
//		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
//		String docconent = request.getParameter("docconent");//审批大字段 
//		String operateType = request.getParameter("operateType");//判断新增、修改
//		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
//		String commenttext = request.getParameter("commenttext");//审批意见
//		String curyear = request.getParameter("curyear");//案件年度
//		String batch = request.getParameter("batch");//批次
//		String batchid = request.getParameter("batchid");//批次
//		String shortname = request.getParameter("shortname");//单位简称(办案用)
//		String commutetype = request.getParameter("commutetype");//减假类型
//
//		//可能重复
//		String casenumber = request.getParameter("casenumber")==null?"":request.getParameter("casenumber");//页面获取的案件号
//		
//		String casetype = request.getParameter("casetype")==null?"":request.getParameter("casetype");//页面获取的字号
//		
//		String jailcasechg = request.getParameter("jailcasechg")==null?"":request.getParameter("jailcasechg");//页面获取的   第、刑更
//		
//		Map temMap2 = new HashMap();
//		temMap2.put("departid", user.getDepartid());
//		temMap2.put("flowdefid", flowdefid);
//		temMap2.put("curyear", curyear);
//		if(StringNumberUtil.notEmpty(commutetype)){
//			temMap2.put("commutetype", Integer.parseInt(commutetype));
//		}
//		
//		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String provincecode = jypro.getProperty("provincecode");
//
//		//如果页面获取的案件号为空，则自动计算案件号
//		int casenum = Integer.parseInt(flowBaseService.getMaxCaseNum(temMap2));
//		if("".equals(casenumber) || Integer.parseInt(casenumber) <= casenum){
//			casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
//		}
//		if(user!=null){
//			if(applyid == null || "".equals(applyid)){
//				applyid =user.getUserid();//申请人ID
//			}
//			if(applyname == null || "".equals(applyname)){
//				applyname =user.getName();//申请人名称
//			}
//		}
//		
//		//流程流转
//		Map<String,Object> tempmap = new HashMap<String,Object>();
//		
//		tempmap.put("resid", resid);//和流程相关按钮ID
//		tempmap.put("tempid", tempid);
//		tempmap.put("flowid", flowid);//流程ID
//		tempmap.put("conent", conent);//内容
//		tempmap.put("orgid", orgid);//部门ID
//		tempmap.put("departid", departid);//单位ID
//		tempmap.put("applyid", applyid);//申请人ID
//		tempmap.put("applyname", applyname);//申请人名称
//		tempmap.put("docconent", docconent);//审批大字段
//		tempmap.put("flowdefid", flowdefid);//流程ID
//		tempmap.put("examineid", examineid);//审批指定人
//		tempmap.put("operateType", operateType);
//		tempmap.put("casetype", casetype);
//		tempmap.put("jailcasechg", jailcasechg);
//		
//		tempmap.put("batch", batch);
//		tempmap.put("batchid", batchid);
//		tempmap.put("curyear", curyear);
//		tempmap.put("shortname", shortname);
//		tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
//		tempmap.put("commenttext", commenttext);//审批意见
//		if(StringNumberUtil.notEmpty(commutetype)){
//			tempmap.put("jxjs_1", commutetype);
//		}
//		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
//			tempmap.put("casenum",curyear+casenumber);
//		}
//		
//		String nowpunishmenttype = request.getParameter("nowpunishmenttype");
//		tempmap.put("nowpunishmenttype", nowpunishmenttype);
//		
//		//如果是无期、死缓等罪犯，要把其电子档案往临时目录里copy，然后用cwrsync往省局进行数据传输
//		if(StringNumberUtil.notEmpty(nowpunishmenttype) && ("9996".equals(nowpunishmenttype.trim()) || "9995".equals(nowpunishmenttype.trim())) ){
//			List<Map<String,Object>> criminalList = new ArrayList<Map<String,Object>>();
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("crimid", applyid);
//			map.put("departid", departid);
//			criminalList.add(map);
//			//采用多线程实现
//			new Thread(new ArchivesCopyRunnable(criminalList,dataTransferService)).start();
//		}
//		
//		result=StringNumberUtil.returnString2(uvFlowService.jiangQuLiAn(data, user, tempmap));
//		
////		result  = flowApprove(request,response);
//		
//		return result;
//	}
	
	
	@RequestMapping(value = "/getMaxCaseNum.action")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getMaxCaseNum(HttpServletRequest request,HttpServletResponse response){
		
		SystemUser user = getLoginUser(request);
		
		String flowdefid = request.getParameter("flowdefid");
		String curyear = request.getParameter("curyear");
		String commutetype = request.getParameter("commutetype");
		Map temMap2 = new HashMap();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		if(StringNumberUtil.notEmpty(commutetype)){
			if(commutetype.equals("jx")){
				temMap2.put("commutetype", "0");
			}else if(commutetype.equals("js")){
				temMap2.put("commutetype", "1");	
			}else{
				temMap2.put("commutetype", commutetype);
			}
		}
		String casenumber = "";
		boolean flag = true;
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jypro.getProperty("provincecode");
		if(flag && !commutetype.equals("2")){
			if(StringNumberUtil.notEmpty(provincecode)&&"4400".equals(provincecode.trim())){
				temMap2.remove("commutetype");
				casenumber = flowBaseService.getMaxCaseNum(temMap2);//广东减刑假释用一个案件号
			}else{
				casenumber = flowBaseService.getMaxCaseNum(temMap2);//减刑假释案件号
			}
		}else{
			temMap2.put("provincecode", provincecode);
			casenumber = flowBaseService.getMaxBaowaiCaseNum(temMap2);//保外案件号
		}
		return casenumber;
	}
	
	
	@RequestMapping(value = "/getMaxCaseNum4HuNanJailCommute.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getMaxCaseNum4HuNanJailCommute(HttpServletRequest request,HttpServletResponse response){
		
		SystemUser user = getLoginUser(request);
		String flowdefid = request.getParameter("flowdefid");
		String curyear = request.getParameter("curyear");
		String casetype = request.getParameter("casetype");
		
		Map<String,Object> temMap = new HashMap<String,Object>();
		temMap.put("departid", user.getDepartid());
		temMap.put("flowdefid", flowdefid);
		temMap.put("curyear", curyear);
		temMap.put("casetype", casetype);
		
		String casenumber = flowBaseService.getMaxCaseNum4HuNanJailCommute(temMap);//案件号
		
		return casenumber;
	}
	
	
	@RequestMapping(value = "/getLastCaseNum.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getLastCaseNum(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);
		
		String flowdefid = request.getParameter("flowdefid");
		String curyear = request.getParameter("curyear");
		String commutetype = request.getParameter("commutetype");
		Map temMap2 = new HashMap();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		if(StringNumberUtil.notEmpty(commutetype)){
			temMap2.put("commutetype", commutetype);
		}
		
		String casenumber = flowBaseService.getLastCaseNum(temMap2);//案件号
		
		return casenumber;
	}
	
	@RequestMapping(value = "/getSJMaxCaseNumForGD.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getSJMaxCaseNumForGD(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);
		
		String flowdefid = request.getParameter("flowdefid");
		String curyear = request.getParameter("curyear");
//		String commutetype = request.getParameter("commutetype");
		Map temMap2 = new HashMap();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
//		if(StringNumberUtil.notEmpty(commutetype)){
//			temMap2.put("commutetype", commutetype);
//		}
		
		String casenumber = flowBaseService.getSJMaxCaseNumForGD(temMap2);//案件号
		
		return casenumber;
	}
	
	
	/**
	 * 省局立案
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/provinceLiAn.action")
	@ResponseBody
	public Object provinceLiAn(HttpServletRequest request,HttpServletResponse response) {
		Integer result = -1;
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String examineid = request.getParameter("examineid");//表单ID
		String orgid = request.getParameter("orgid");//申请人的部门ID
		String applyid = request.getParameter("applyid");//申请人ID
		String applyname = request.getParameter("applyname");//申请人名称
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");//案件年度
		String shortname = request.getParameter("shortname");//单位简称(办案用)
		String commutetype = request.getParameter("commutetype");

		Map temMap2 = new HashMap();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		if(StringNumberUtil.notEmpty(commutetype)){
			temMap2.put("commutetype", commutetype);
		}
		//String casenumber = flowBaseService.getLastCaseNum(temMap2);//案件号
		String casenumber = request.getParameter("casenumber");//省局立案案件号取页面的案件号，用户可修改案件号
		
		String casetype = request.getParameter("casetype");
		if(StringNumberUtil.notEmpty(commutetype)&&"0".equals(commutetype.trim())){
			casetype = "减字";
		}else if(StringNumberUtil.notEmpty(commutetype)&&"1".equals(commutetype.trim())){
			casetype = "假字";
		}
		
		if(user!=null){
			if(applyid == null || "".equals(applyid)){
				applyid =user.getUserid();//申请人ID
			}
			if(applyname == null || "".equals(applyname)){
				applyname =user.getName();//申请人名称
			}
		}
		
		//流程流转
		Map<String,Object> tempmap = new HashMap<String,Object>();
		
		tempmap.put("resid", resid);//和流程相关按钮ID
		tempmap.put("tempid", tempid);
		tempmap.put("flowid", flowid);//流程ID
		tempmap.put("conent", conent);//内容
		tempmap.put("orgid", orgid);//部门ID
		tempmap.put("applyid", applyid);//申请人ID
		tempmap.put("applyname", applyname);//申请人名称
//		if(StringNumberUtil.isEmpty(docconent)){
//			TbsysDocument tbsysDocument = tbsysDocumentService.getTbsysDocument(null,tempid,applyid,null);
//			docconent = tbsysDocument.getContent();
//		}
		tempmap.put("docconent", docconent);//审批大字段
		tempmap.put("flowdefid", flowdefid);//流程ID
		tempmap.put("examineid", examineid);//审批指定人
		tempmap.put("operateType", operateType);
		tempmap.put("casetype", casetype);
		tempmap.put("shortname", shortname);
		tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
		tempmap.put("commenttext", commenttext);//审批意见
		if(StringNumberUtil.notEmpty(commutetype)){
			tempmap.put("jxjs_1", commutetype);
		}
		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
			tempmap.put("casenum", curyear+casenumber);
		}
		
		result=uvFlowService.provinceLiAn(data, user, tempmap);
		
		return result;
	}
	
	/**
	 * 广东省局立案
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/provinceLiAnForGD.json")
	@ResponseBody
	public Object provinceLiAnForGD(HttpServletRequest request,HttpServletResponse response) {
		Integer result = -1;
		SystemUser user = getLoginUser(request);
		//流程流转
		Map<String,Object> paramap = new HashMap<String,Object>();
		String flowdraftids = request.getParameter("flowdraftids");//和流程相关按钮ID
		String flowdefid = request.getParameter("flowdefid");
		String approveperson = request.getParameter("approveperson");
//		String provincecasechg = this.getParameterString(request, "provincecasechg", "");
//		String casenumber = request.getParameter("casenumber");
		paramap.put("flowdraftids", flowdraftids);
		paramap.put("flowdefid", flowdefid);
		paramap.put("approveperson", approveperson);
		paramap.put("departid", user.getDepartid());
//		paramap.put("provincecasechg", provincecasechg);
//		paramap.put("casenumber", casenumber);
		
		result=uvFlowService.provinceLiAnForGD(paramap);
		
		return result;
	}
	
	
	/**
	 * 省局批量立案
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/provinceBatchLiAn.action")
	@ResponseBody
	public Object provinceBatchLiAn(HttpServletRequest request,HttpServletResponse response) {
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		
		//定义变量
		int resultval = 0;
		
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");
//		String commutetype = request.getParameter("commutetype");//案件类刑
		String casetype = request.getParameter("casetype");
		String caseTypes = request.getParameter("caseTypes");//监狱提交罪犯案件类型(河北加)
		String shortname = request.getParameter("shortname");//单位简称(办案用)
//		if(StringNumberUtil.notEmpty(commutetype)&&"0".equals(commutetype.trim())){
//			casetype = "减字";
//		}else if(StringNumberUtil.notEmpty(commutetype)&&"1".equals(commutetype.trim())){
//			casetype = "假字";
//		}
		
		String dataStr = request.getParameter("dataStr");
		
		Map<Object,Object> datamap = new HashMap<Object,Object>();
		datamap.put("dataStr", dataStr);
		datamap.put("data", data);
		datamap.put("resid", resid);
		datamap.put("flowid", flowid);
		datamap.put("conent", conent);
		datamap.put("tempid", tempid);
		datamap.put("flowdefid", flowdefid);
		datamap.put("docconent", docconent);
		datamap.put("operateType", operateType);
		datamap.put("flowdraftid", flowdraftid);
		datamap.put("commenttext", commenttext);
		datamap.put("curyear", curyear);
		datamap.put("casetype", casetype);
		datamap.put("caseTypes", caseTypes);
		datamap.put("shortname", shortname);
//		if(StringNumberUtil.notEmpty(commutetype)){
//			datamap.put("commutetype", commutetype);
//		}
		resultval = uvFlowService.batchProvinceLiAnFlowApprove(user,datamap);
		
		return resultval;
	}
	/**
	 * 根据流程草稿ID判断是否存在呈批表
	 * @param flowid
	 * @return
	 */
	@RequestMapping(value = "/validatecpb.json")
	@ResponseBody
	public Object validatecpb(HttpServletRequest request,HttpServletResponse response) {
		int resultval = 0;//0已保存1未保存
		//获取参数
		String flowdraftid = request.getParameter("flowdraftid");
		resultval = flowService.validatecpb(flowdraftid);
		return resultval;
	}
	/**
	 * 更新档案
	 * @param flowid
	 * @return
	 */
	@RequestMapping(value = "/updatearchtive.json")
	@ResponseBody
	public Object updateArchtive(HttpServletRequest request,HttpServletResponse response) {
		//用户对象
		SystemUser user = getLoginUser(request);
		int rows = 0;//0已保存1未保存
		//获取参数
		String flowid = request.getParameter("flowid");
		String flowdraftid = request.getParameter("flowdraftid");
		String commenttext = request.getParameter("commenttext");
		String docconent = request.getParameter("docconent");
		String applyid = request.getParameter("applyid");
		rows = flowArchivesService.updateArchtive(flowid,flowdraftid,commenttext,docconent,user);
		rows = uvFlowService.saveFlowArchives(docconent,flowid,flowdraftid, applyid,user);
		return rows;
	}
	@RequestMapping(value = "/chehui.json")
	@ResponseBody
	public Object chehui(HttpServletRequest request) {
		Object result = 0;
		// 用户对象
        SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid");
		String curyear = request.getParameter("curyear");
		String batch = request.getParameter("batch");
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("crimid", crimid);
		map.put("applyid", crimid);
		map.put("isdeclare", "0");
		map.put("crimids", crimid);
		map.put("specialcrim", "0");
		map.put("curyear", curyear);
		map.put("batch", batch);
		map.put("departid", user.getDepartid());
	    result = tbxfScreeningService.updateDataAfterPrisonerLiAn(map);
		return result;
	}
	@RequestMapping(value = "/batchReview.json")
	@ResponseBody
	public Object batchReview(HttpServletRequest request) {
		int result = 0;
//		Map parmMap = parseParamMapString(request);
//		result = flowBaseService.updateBatchReview(parmMap);
//		return result;
		
		
		String idnumber = request.getParameter("idnumbers");
		if(StringNumberUtil.notEmpty(idnumber)){
			String solutionid = "702158";//公共删除的查询方案
			
			SystemUser user = getLoginUser(request);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowdraftids", idnumber);
			map.put("solutionid", solutionid);
//			map.put("menuid", menuid);
			commonFormService.commonRemoveDate(map,user);
			result = 1;
		}
		return result;
	}
	/**
	 * 档案类别管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/archCodeManagement.page")
	public ModelAndView archCodeManagementPage(HttpServletRequest request){
		returnResourceMap(request);
		ModelAndView mav = new ModelAndView("archives/ManagementPage");
		return mav;
	}
	/**
	 * 档案类别列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/getAllArchCode.json")
	@ResponseBody
	public Object getAllArchCode(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String key = request.getParameter("key");
		String pcodeid = request.getParameter("pcodeid");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		int total=0;
		Map map=new HashMap();
		map.put("departid", user.getDepartid());
		map.put("start", start);
		map.put("end", end);
		map.put("key", key);
		map.put("pcodeid", pcodeid);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		List<FlowArchivesCode> list = flowArchivesCodeService.selectAllByMap(map);
		total = flowArchivesCodeService.selectAllByMapCount(map);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		resultmap.put("data", list);
		resultmap.put("total", total);
		return resultmap;
	}
	
	/**
	 * 跳转code修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addCodePage.page")
	public ModelAndView editCodepage(HttpServletRequest request){
			SystemUser user=getLoginUser(request);
			String codeid=request.getParameter("codeid");
			String addorupdate = request.getParameter("addorupdate");
			request.setAttribute("addorupdate", addorupdate);
			String departidtemp = request.getParameter("departidtemp");//
			request.setAttribute("departidtemp", departidtemp);
			FlowArchivesCode flowarchivescode = flowArchivesCodeService.selectByPrimaryKey(codeid, user.getDepartid());
			if(!StringNumberUtil.isNullOrEmpty(flowarchivescode)){
			request.setAttribute("codeid", flowarchivescode.getCodeid());
			 request.setAttribute("codename", flowarchivescode.getName());
			 request.setAttribute("sn", flowarchivescode.getSn());
			 request.setAttribute("pcodeid", flowarchivescode.getPcodeid());
			 request.setAttribute("tempid", flowarchivescode.getTempid());
			 request.setAttribute("ispositive", flowarchivescode.getIspositive());
			 request.setAttribute("remark", flowarchivescode.getRemark());
		 }
		return new ModelAndView("archives/addCodePage");
	}
	/**
	 * 新增,修改code
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveCode")
	@ResponseBody
	public Object saveCode(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String data=request.getParameter("data");
		String departidtemp=request.getParameter("departidtemp");
		String addorupdate = request.getParameter("addorupdate");//用于判断新增或修改
		ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data, Object.class);
		int num1=0,num2=-1;
		for(int i=0;i<list.size();i++){
			FlowArchivesCode code=new FlowArchivesCode();
			Map map = (Map)list.get(i);
			code.setCodeid((String)map.get("codeid"));
			code.setName((String)map.get("codename"));
			code.setOpid(user.getOpid());
			code.setRemark((String)map.get("remark"));
			code.setIspositive(Short.parseShort((String)map.get("ispositive")));
			code.setSn(Integer.parseInt((String)map.get("sn")));
			code.setDelflag("0");
			code.setOptime(new Date());
			code.setTempid((String)map.get("tempid"));
			String pcodeid=(String)map.get("pcodeid");
			code.setPcodeid(pcodeid);
			if(!StringNumberUtil.isNullOrEmpty(addorupdate)&&"add".equals(addorupdate)){
				code.setDepartid(user.getDepartid());
				Map temp=new HashMap();
				temp.put("codeid", (String)map.get("codeid"));
				temp.put("departid", user.getDepartid());
				num2 = flowArchivesCodeService.findCodeByMap(map);
				if(num2==0){
					num1=flowArchivesCodeService.insert(code);
				}else{
					num1=-1;
				}
				}else if(!StringNumberUtil.isNullOrEmpty(addorupdate)&&"edit".equals(addorupdate)){
					code.setDepartid(departidtemp);
					num1 = flowArchivesCodeService.updateCode(code);
				}
		}
		return num1;
	}
	/**
	 * 删除code
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/removeCode")
	@ResponseBody
	public Object removeCode(HttpServletRequest request){
		String codeid=request.getParameter("codeid");
		String departid = request.getParameter("departid");
		int num=0;
		Map map=new HashMap();
		map.put("codeid", codeid);
		map.put("departid", departid);
		num=flowArchivesCodeService.deleteCodeByMap(map);
		return num;
	}
	
	/**
	 * 通过查询方案找到相应的Form
	 * yanqutai
	 */
	@RequestMapping(value = "/ajaxGetFormBySolutionID.json")
	@ResponseBody
	public Object ajaxGetFormBySolutionID(HttpServletRequest request,HttpServletResponse response) {
		JSONMessage message = JSONMessage.newMessage();
		SystemUser user = this.getLoginUser(request);
		String menuid = request.getParameter("menuid");
		
		//组合参数Map: paramMap
		String solutionid = request.getParameter("solutionid");
		String tempid = request.getParameter("tempid");
		String flowdraftid = request.getParameter("flowdraftid");
		String params = request.getParameter("params");
		Map<String,Object> paramMap = MapUtil.getDataByAip(params);
		paramMap.put("solutionid", solutionid);
		paramMap.put("tempid", tempid);
		paramMap.put("flowdraftid", flowdraftid);
		
		Map<String,Object> resultMap = commonSQLSolutionService.query(paramMap, user);
		/*
		 * 得到表单所需要的值Map : key为form为表单大字段的值，key为root的值为表单文本域Map，key为options为表单下拉选择map，
		 */
		Map formMap =  resultMap.get("form") instanceof Map ?(Map)resultMap.get("form"):null;//表单上非选择域的值Map
		//
		String formClob = (null!=formMap)? StringNumberUtil.toString(formMap.get("doc_content")) : null ;//获取表单大字段
		JSONArray docconent = new JSONArray();
		docconent.add(formClob);
		message.setData(docconent);
		return message;
	}
	
	/**
	 * 通过查询方案找到相应的Form
	 * yanqutai
	 */
	@RequestMapping(value = "/ajaxSaveFormBySolutionID.json")
	@ResponseBody
	public Object ajaxSaveFormBySolutionID(HttpServletRequest request,HttpServletResponse response) {
		String result = "";
		JSONMessage message = JSONMessage.newMessage();
		SystemUser user = this.getLoginUser(request);
		String aipFileStr = request.getParameter("aipFileStr")==null?"":request.getParameter("aipFileStr");
		String flowdraftid = request.getParameter("flowdraftid")==null?"":request.getParameter("flowdraftid");
		String solutionid = request.getParameter("solutionid")==null?"":request.getParameter("solutionid");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("solutionid", solutionid);
		paramMap.put("aipFileStr", aipFileStr);
		paramMap.put("flowdraftid", flowdraftid);
		
		result = uvFlowService.saveFormBySolutionID(paramMap, user);
		
		return result;
	}
	
	/**
	 * 方法描述：判断罪犯类型通过类型判断tab页面是否显示
	 * @author:mushuhong
	 * @versions:2015年11月19日15:26:53
	 */
	@RequestMapping(value="/isLookTab.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object isLookTab(HttpServletRequest request){
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		int result = 0;
		if ("4300".equals(jyconfig.get(GkzxCommon.PROVINCECODE))) {
			Map map = uvFlowService.isLookTab(request);
			if (map != null) {
				if (map.get("CASENATURE") != null) {
					result = 1;
				}
			}
		}
		return result;
	}
	
	/**
	 * 查找罪犯的离监日期或刑期止日
	 * yanqutai
	 */
	@RequestMapping(value = "/ajaxSearchChuJianDateByFlowdraftID.json")
	@ResponseBody
	public Object ajaxSearchChuJianDateByFlowdraftID(HttpServletRequest request,HttpServletResponse response) {
		JSONMessage message = JSONMessage.newMessage();
		SystemUser user = this.getLoginUser(request);
		//组合参数Map: paramMap
		String flowdraftid = request.getParameter("flowdraftid");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("flowdraftid", flowdraftid);
		
		Map<String,Object> resultMap = uvFlowService.searchChuJianDateByFlowdraftID(paramMap);
		JSONArray docconent = new JSONArray();
		docconent.add(resultMap.get("LIJIANRIQI"));
		message.setData(docconent);
		return message;
	}
	
	/**
	 * 刑罚科分案
	 * @param request
	 * @return
	 */
	@RequestMapping(value="XingFaKeFenAn.action")
	@ResponseBody
	public int XingFaKeFenAn(HttpServletRequest request){
		int result = 0;
		String flowdraftid = request.getParameter("flowdraftid");
		String keyuan  = request.getParameter("keyuan") == null ? "" : request.getParameter("keyuan");
		
		FlowBase flowBase = new FlowBase();
		flowBase.setFlowdraftid(flowdraftid);
		flowBase.setFjquser(keyuan);
		
		result=flowBaseService.updateByFlowdraftid(flowBase);
		
		return result;
	}
	
	@RequestMapping(value="piLiangFenAn.action")
	@ResponseBody
	public int piLiangFenAn(HttpServletRequest request){
		int result = 0;
		String flowdraftids = request.getParameter("flowdraftids") == null ? "" : request.getParameter("flowdraftids");
		String keyuan = request.getParameter("keyuan") == null ? "" : request.getParameter("keyuan");

		String[] flowdraftid = flowdraftids.split(",");
		for (int i = 0; i < flowdraftid.length; i++) {
			FlowBase flowBase = new FlowBase();
			flowBase.setFlowdraftid(flowdraftid[i]);
			flowBase.setFjquser(keyuan);
			
			result=flowBaseService.updateByFlowdraftid(flowBase);
		}
		return result;
	}
	
	@RequestMapping(value="getBackNodeidByCurrnodeid.json")
	@ResponseBody
	public String getBackNodeidByCurrnodeid(HttpServletRequest request){
		Map<String,Object> paramap = this.parseParamMap(request);
		return flowBaseService.getBackNodeidByCurrnodeid(paramap);
	}
	
	@RequestMapping(value="getAnJianTypeList.action")
	@ResponseBody
	public Object getAnJianTypeList(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		Map map = new HashMap();
		map.put("departid", departid);
		List<Map<String, Object>> list = new ArrayList();
		list = flowBaseService.getAnJianTypeList(map);
		return list;
	}
	
	@RequestMapping(value="getUvFlowDataByParam.json")
	@ResponseBody
	public Object getUvFlowDataByParam(HttpServletRequest request){
		Map<String,Object> map = this.parseParamMap(request);
		SystemUser user = getLoginUser(request);
		map.put("orgid", user.getOrgid());
		map.put("departid", user.getDepartid());
		return uvFlowService.getUvFlowDataByParam(map);
	}
	
	
	/**
	 * 报备立案
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/baoBeiLiAn.action")
	@ResponseBody
	@SuppressWarnings("all")
	public Object baoBeiLiAn(HttpServletRequest request,HttpServletResponse response) {
		Object result = 0;
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String examineid = request.getParameter("examineid");//表单ID
		String orgid = request.getParameter("orgid");//申请人的部门ID
		String applyid = request.getParameter("applyid");//申请人ID
		String applyname = request.getParameter("applyname");//申请人名称
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");//案件年度
		String batch = request.getParameter("batch");//批次
		String shortname = request.getParameter("shortname");//单位简称(办案用)
		String commutetype = request.getParameter("commutetype");//减假类型

//		String casenumber = request.getParameter("casenumber");//案件号
		Map temMap2 = new HashMap();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		if(StringNumberUtil.notEmpty(commutetype)){
			temMap2.put("commutetype", Integer.parseInt(commutetype));
		}
		
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jypro.getProperty("provincecode");
		String casenumber = "";
		String casetype = "";
		if(StringNumberUtil.notEmpty(provincecode)&&"4400".equals(provincecode.trim())){
			temMap2.remove("commutetype");
			casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
			casetype = "刑备字";
		}else{
			casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
			if(StringNumberUtil.notEmpty(commutetype)&&commutetype.equals("0")){
				casetype = "减字";
			}else if(StringNumberUtil.notEmpty(commutetype)&&commutetype.equals("1")){
				casetype = "假字";
			}
		}
		
		if(user!=null){
			if(applyid == null || "".equals(applyid)){
				applyid =user.getUserid();//申请人ID
			}
			if(applyname == null || "".equals(applyname)){
				applyname =user.getName();//申请人名称
			}
		}
		
		//流程流转
		Map<String,Object> tempmap = new HashMap<String,Object>();
		
		tempmap.put("resid", resid);//和流程相关按钮ID
		tempmap.put("tempid", tempid);
		tempmap.put("flowid", flowid);//流程ID
		tempmap.put("conent", conent);//内容
		tempmap.put("orgid", orgid);//部门ID
		tempmap.put("departid", departid);//单位ID
		tempmap.put("applyid", applyid);//申请人ID
		tempmap.put("applyname", applyname);//申请人名称
		tempmap.put("docconent", docconent);//审批大字段
		tempmap.put("flowdefid", flowdefid);//流程ID
		tempmap.put("examineid", examineid);//审批指定人
		tempmap.put("operateType", operateType);
		tempmap.put("casetype", casetype);
		tempmap.put("batch", batch);
		tempmap.put("curyear", curyear);
		tempmap.put("shortname", shortname);
		tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
		tempmap.put("commenttext", commenttext);//审批意见
		if(StringNumberUtil.notEmpty(commutetype)){
			tempmap.put("jxjs_1", commutetype);
		}
		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
			tempmap.put("casenum", curyear+casenumber);
		}
		
		String nowpunishmenttype = request.getParameter("nowpunishmenttype");
		tempmap.put("nowpunishmenttype", nowpunishmenttype);
		
		result=StringNumberUtil.returnString2(uvFlowService.jiangQuLiAn(data, user, tempmap));
		
//		result  = flowApprove(request,response);
		
		return result;
	}
	/**
	 * 判断案件号是否有重复
	 * @param request
	 * @return
	 */
	@RequestMapping(value="isRepeatCaseNum.action")
	@ResponseBody
	public Object isRepeatCaseNum(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		String prisonid = user.getPrisonid();//获取监狱部门id
		int result = 0;
		String curyear = request.getParameter("curyear") == null ? "" : request.getParameter("curyear");
		String casenumber = request.getParameter("casenumber") == null ? "" : request.getParameter("casenumber");
		String repeatCaseNum = request.getParameter("repeatCaseNum") == null ? "" : request.getParameter("repeatCaseNum");
		String casetype = request.getParameter("casetype") == null ? "" : request.getParameter("casetype");
		String flowdefid = request.getParameter("flowdefid") == null ? "" : request.getParameter("flowdefid");
		String text6 = curyear+casenumber;
		Map<String,String> map = new HashMap<String,String>();
		map.put("text6", text6);
		map.put("repeatCaseNum", repeatCaseNum);
		map.put("casetype", casetype);
		map.put("flowdefid", flowdefid);
		map.put("prisonid", prisonid);
		result = uvFlowService.isRepeatCaseNum(map);
		return result;
		
	}
	
	@RequestMapping(value="getUvFlowDataByParam2.json")
	@ResponseBody
	public Object getUvFlowDataByParam2(HttpServletRequest request){
		Map<String,Object> map = this.parseParamMap(request);
		SystemUser user = getLoginUser(request);
		map.put("orgid", user.getOrgid());
		map.put("departid", user.getDepartid());

		return uvFlowService.getUvFlowDataByParam2(map);
	}
	
	
}
