<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
	String applyvalue = (String) request.getAttribute("applyvalue");
%>
<SCRIPT LANGUAGE="JavaScript">

	var path = '<%=path%>';
	// JavaScript Document
	var argObj = null;
	var i=parseInt("0");
	var flagg = true;
	
	/****************************************************
	*	
	*		初始化函数AIP
	*
	****************************************************/
	function loadBigData(formcontent,formDatajson, selectDatajson){
       	var aipdata = document.getElementById("aipdata").value;//表单大字段
       	var reportdata = document.getElementById("reportdata").value;//报表
       	
       	if(aipdata){
       		document.getElementById("HWPostil1").LoadFileBase64(aipdata);
		}else if(reportdata){
			document.all("MyViewer").ShowProgress = false;
			document.all.HWPostil1.BeforeConvert("");
			document.all("RMVIEWER_DATA").value=reportdata;
			document.all("MyViewer").GetReportData(reportdata);
			document.all("MyViewer").ShowPrintDialog = false;
			document.all("MyViewer").PrintReport();
			document.all.HWPostil1.WaitConverting(4000);
			document.all.HWPostil1.AfterConvert();
		}else if(formcontent){
			HWPostil1_modelReady(formcontent,1);
			HWPostil1_display(formDatajson, selectDatajson);
		}
	}
		
	function HWPostil1_onload(){
		try{	
			var aipObj=document.getElementById("HWPostil1");
			aipObj.ShowDefMenu = 0;						// 隐藏菜单栏 0为隐藏; 1为显示
			aipObj.ShowScrollBarButton = 1;						// 隐藏水平滚动条旁的工具条 1为隐藏;0为显示
			aipObj.ShowToolBar = 0;							// 隐藏工具栏 0为隐藏; 1为显示
			aipObj.InDesignMode = false;					//退出设计模式
			var templetid=document.getElementById('templetid');
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	
	/****************************************************
	*	
	*				DOC2AIP  DOC 文件转为AIP文件
	*
	****************************************************/
	function DOC2AIP(){
		try{	
			var aipObj=document.getElementById("HWPostil1");
			HWPostil1_onload();
			aipObj.ConvertToAip(0,1);//把Word文件转为AIP文件
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	
	/****************************************************
	*	
	*				起草正文之word
	*		AIP初始化事件所调用的初始化函数
	*
	****************************************************/
	function HWPostil1_WordReady(fileName,fileType){
		try{	
			var aipObj=document.getElementById("HWPostil1");
			HWPostil1_onload();
			aipObj.LoadOriginalFile(fileName,fileType);//打开正文
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	/****************************************************
	*	
	*		打开模版文件所调用的初始化函数
	*
	****************************************************/
	function HWPostil1_modelReady(obj,IsJS,pagenum){
		try{
			document.all.HWPostil1.Login("sys_admin",5, 65535, "DEMO", "");
			var aipObj=document.getElementById("HWPostil1");
			HWPostil1_onload();
			//aipObj.CloseDoc(0);
			if(IsJS){
				aipObj.JSEnv = 1;
				if(obj){
					//多页显示 例如 合并查看
					if(IsJS == 0){
						var objs = eval(obj);
						for(var j=0;j<objs.length;j++){ 
							aipObj.MergeFile(99999,'STRDATA:'+objs[j]);
						}
						if(pagenum!=''){
							insertpagenum(pagenum);	
						}			
							
					}else{
						aipObj.LoadFileBase64(eval(obj));//打开模板文件
					}
					//
				}
			}
			aipObj.Logout();
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	/****************************************************
	*	
	*		打开模版文件所调用的初始化函数
	*
	****************************************************/
	function HWPostil1_modelEdit(templet){
		try{
			var aipObj=document.getElementById("HWPostil1");
			var obj = eval(templet);
			if(obj){
				aipObj.LoadFileBase64(obj);//打开模板文件
			}
			aipObj.Login("sys_admin", 5, 32767, "", "");
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	/****************************************************
	*	
	*				起草正文之
	*			AIP呈批单得到表单值
	*
	****************************************************/
	function setValue(fieldName,fieldValue)
	{
		try{
		var aipObj=document.getElementById("HWPostil1");
		aipObj.SetValue("Page1."+fieldName,fieldValue);
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	
	}
	/****************************************************
	*	
	*			将AIP文件保存为BASE64字符串
	*
	****************************************************/
	function saveFile()
	{
		try{
			var aipObj=document.getElementById("HWPostil1");
			aipObj.Setvalue("isagree","");	//设置意见状态值为空
		 	var content = aipObj.GetCurrFileBase64();
	  }catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);	
			return 	false;
		}
		return content;
	
	}
	
	/****************************************************
	*	
	*			起草正文之AIP盖章
	*		AIP初始化事件所调用的初始化函数
	*
	****************************************************/
	function HWPostil1_NotifyCtrlReady(undertakeFile,strMatFile) {			//undertakeFile：承办单 strMatFile：正文
	 	try{
			var aipObj=document.getElementById("HWPostil1");
			HWPostil1_onload();
			aipObj.Login("HWSEALDEMOADMIN",4,65535,"DEMO","");			// 测试用户登录
			aipObj.CloseDoc(0);
			if(!aipObj.LoadFileBase64(undertakeFile)){
				alert("打开文件失败");
				return false;
			}
		}catch(e) {
		  alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	/****************************************************
	*	
	*				盖章
	*
	****************************************************/
	function addSeal(){
		try{	
			var aipObj=document.getElementById("HWPostil1");
			aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
			if(!aipObj.IsOpened()){
				alert("必须打开文件，才可以操作");
				return false;
			}
			if(!aipObj.IsLogin()){
				alert("必须登陆AIP，才可以操作");
				return false;
			}
			aipObj.CurrAction=2568;
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	/****************************************************
	*	
	*				打印
	*
	****************************************************/
	function printdoc(){
		try{	
			var aipObj=document.getElementById("HWPostil1");
			if(!aipObj.IsOpened()){
				alert("必须打开文件，才可以操作");
				return false;
			}
			/*if(!aipObj.IsLogin()){
				alert("必须登陆AIP，才可以操作");
				return false;
			}*/
			aipObj.PrintDoc(1,1);
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		}
	}
	/****************************************************
	*	
	*				手写签批
	*
	****************************************************/
	function addWrite(){
		try{
			var aipObj=document.getElementById("HWPostil1");
			aipObj.Login("孙悟空[人力资源部]", 1, 65535, "", "");
			if(!aipObj.IsOpened()){
				alert("必须打开文件，才可以操作");
				return false;
			}
			if(!aipObj.IsLogin()){
				alert("必须登陆AIP，才可以操作");
				return false;
			}
			aipObj.CurrAction=264;
		}catch(e) {
		  alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);	
		}
	}
	/****************************************************
	*	
	*				浏览模式
	*
	****************************************************/
	function readmode(){
		try{
			var aipObj=document.getElementById("HWPostil1");
			if(!aipObj.IsOpened()){
				alert("必须打开文件，才可以操作");
				return false;
			}
			if(aipObj.IsLogin()){
				aipObj.Logout();
				aipObj.CurrTextEditUser="张三（技术部）";
			}
			aipObj.CurrAction=0;
		}catch(e) {
		  alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);	
		}
	}
	/****************************************************
	*	
	*				另存为其他格式
	*
	****************************************************/
	function SaveTo(){
		try{
			var aipObj=document.getElementById("HWPostil1");
			aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
			if(!aipObj.IsOpened()){
				alert("必须打开文件，才可以操作");
				return false;
			}
			if(!aipObj.IsLogin()){
				alert("必须登陆AIP，才可以操作");
				return false;
			}
			aipObj.SaveTo("","aip",0);
		}catch(e) {
		  alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);	
		}
	}
	/****************************************************
	*	
	*			收文管理之
	*		查看已收文档所调用的初始化函数
	*
	****************************************************/
	function HWPostil1_SeeAipReady(aipName) {			//undertakeFile：承办单 strMatFile：正文
	 	try{
			var aipObj=document.getElementById("HWPostil1");
			HWPostil1_onload();
			if(!aipObj.LoadFile(aipName)){
				alert("打开文件失败");
				return false;
			}
		}catch(e) {
		  alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	/****************************************************
	*	
	*			呈批件模板制作之
	*			新建模板初始化函数
	*
	****************************************************/
	function HWPostil1_TempletReady(aipName) {			//undertakeFile：承办单 strMatFile：正文
	 	try{
			var aipObj=document.getElementById("HWPostil1");
			HWPostil1_onload();
			if(!aipObj.LoadFileEx(aipName,"doc",0,0)){
				alert("打开文件失败");
				return false;
			}
		}catch(e) {
		  alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	/****************************************************
	*	
	*		    	呈批件模板制作之
	* 		“添加区域”按钮所调用函数
	*
	****************************************************/
	function addField()
	{
		try{
			var aipObj=document.getElementById("HWPostil1");
			var vRet = aipObj.Login("sys_admin", 5, 32767, "", "");
			if(0 == vRet);
			aipObj.InDesignMode = true;
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	/****************************************************
	*	
	*			呈批件模板制作之
	*			添加区域时所触发事件调用函数
	*
	****************************************************/
	function NotifyLineAction(lPage,lStartPos,lEndPos)
	{
		try{
		var lStartY = (lStartPos>>16)& 0x0000ffff;
		var lStartX = ((lStartPos<<16)>>16) & 0x0000ffff; 
		var lEndY = (lEndPos>>16)& 0x0000ffff;
		var lEndX = ((lEndPos<<16)>>16) & 0x0000ffff; 
		ShowDialog('setFrm');
	  	argObj = {"page":lPage,"StartX":lStartX,"StartY":lStartY,"EndX":lEndX,"EndY":lEndY};
	  }catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	/*************************************************\
	显示模版元素选择的对话框
	**************************************************/
	var getmyvalue = function(id) {return document.getElementById(id);};
	function ShowDialog(id)
	{
		getmyvalue("overlay").style.display = 'block';
		getmyvalue(id).style.display = 'block';
	
		var bb=(document.compatMode && document.compatMode!="BackCompat") ? document.documentElement : document.body;
		if(document.compatMode && document.compatMode!="BackCompat")
		{
			getmyvalue("overlay").style.width = bb.scrollWidth+"px";
			getmyvalue("overlay").style.height = bb.scrollHeight+"px";
		}
		else
		{
			getmyvalue("overlay").style.width = bb.scrollWidth+"px";
			getmyvalue("overlay").style.height = bb.scrollHeight+"px";
		}
		getmyvalue(id).style.left = ((bb.offsetWidth - getmyvalue(id).offsetWidth)/2)+"px";
		getmyvalue(id).style.top  = (90 + bb.scrollTop)+"px";
	}

	/****************************************************
	*	
	*				呈批件模板制作之
	*				设置呈批件参数
	*parameters：codescript is a sql ： add by fyy for 查询弹出框的值
	****************************************************/
	function setField(field_name,field_type,field_set,border,codescript,codeType)
	{	
		try{
			HideDialog('setFrm');
			if(argObj == null)
			  return;
			var obj = getmyvalue("HWPostil1");
			var field_width = argObj.EndX - argObj.StartX;
			var field_height = argObj.EndY - argObj.StartY;	
			var vRet = obj.InsertNote(field_name,argObj.page,field_type,argObj.StartX,argObj.StartY,field_width,field_height);
			if(vRet=="")
			{
				alert("此字段映射已经添加！");
				ShowDialog('setFrm');
				return;
			}
			argObj.page=parseInt(argObj.page)+1;
			if(field_set&0x01){
				if(field_set&0x02){
					obj.SetValue("Page"+argObj.page+"."+field_name,":PROP::LABEL:1");
				}else{
					obj.SetValue("Page"+argObj.page+"."+field_name,":PROP::LABEL:0");
				}
			}else{
				obj.SetValue("Page"+argObj.page+"."+field_name,":PROP::LABEL:3");
			}
			if(field_set&0x04){
				obj.SetValue("Page"+argObj.page+"."+field_name,":PROP::NODEL:1");
			}else{
				obj.SetValue("Page"+argObj.page+"."+field_name,":PROP::NODEL:0");
			}
			if(field_set&0x08){
				obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:BORDER:"+border);
			}else{
				obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:BORDER:0");
			}
			obj.Setvalue("Page"+argObj.page+"."+field_name,":PROP:FRONTCOLOR:black");//设置字体颜色
			obj.DocProperty("m_"+field_name) = field_set.toString();
			obj.DocProperty("sql_"+field_name) = codescript;
			obj.DocProperty("code_"+field_name) = codeType;
			argObj = null;
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		} 
	}
	/****************************************************
	*	
	*			aip文件上传服务器
	*
	****************************************************/
	function httppost(ID){
		try{
			var aipObj=document.getElementById("HWPostil1");
			aipObj.HttpInit(); //初始化HTTP引擎。
			aipObj.HttpAddPostString("DOC_ID",ID); //设置上传变量User。
		　　aipObj.HttpAddPostCurrFile("FileBlod");//设置上传当前文件,文件标识为FileBlod。
		　　var isup=aipObj.HttpPost("http://127.0.0.1:85/doc_send/upaip.php");//上传数据。
		　　return isup;
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	var mucount=0;
	function savepictoserver(filePath,phototype){
	 
		serverurl="<%=realPath%>/upload";
		var webObj=document.getElementById("HWPostil1");
		var criminalid=document.getElementById("criminalid").value;
		if(mucount==0)
		{
			webObj.HttpInit();//初始化上传接口
			webObj.HttpAddPostString("Company","Dianju");//通过控件post字符串参数
			webObj.HttpAddPostString("criminalid",criminalid);
		}
		mucount++;
		webObj.HttpAddPostFile(phototype,filePath); 
		webObj.HttpPost(serverurl);//接收地址，returnValue为接收地址向外输出的内容
	}

	//进入一般审批的意见弹出框
	function popUpWindow(strName,aipObj){
		var crimid = aipObj.GetValueEx("crimid",2,"",0,""); 
		var url = "<%=path%>/toGetWindowSelect.page?winid="+strName.substr(4)+"&crimid="+crimid;
		vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:260px;dialogWidth:400px");
		if(vRet){
			//清空隐藏域原来的值
			aipObj.SetValue(strName,"");
			aipObj.SetValue(strName,vRet[0]);
		}
	}

	//日期格式化
	function formatDate(now) { 
		var year=now.getYear(); 
		var month=now.getMonth()+1; 
		var date=now.getDate();
		if(month<10){
			month="0"+month;
		}
		if(date<10){
			date="0"+date;
		}
		return year+""+month+""+date; 
	} 

	/*****************************************************
	处理操作的事件
	******************************************************/
	function HWPostil1_JSNotifyBeforeAction(lActionType,lType,strName,strValue){
		//根据不同的 省份进入不同的 打开 js 函数的页面(宁夏判断)
		var shanxi = 0;//防止个别 页面无id="shanxi"的标签的时候的问题
		if($("#shanxi").val() != undefined) {
			shanxi = $("#shanxi").val();
		}
		try{
			var aipObj = getmyvalue("HWPostil1");
			var vInfo = "";
			if(strName.substring(0,3)=="pic"){
			    var bmppath = aipObj.ShowDialog(0, "", "", "支持的位图文件(*.jpg)|*.jpg||");
			    if(bmppath != ""){
			    	aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		        	aipObj.SetValue(strName,"");
		        	aipObj.SetValueEx(strName,14,0,bmppath);
		        	if("pictz_text1"==strName){
		        		savepictoserver(bmppath,11)
		        	}
		        }
			}else{
				if(1==lActionType){
					aipObj.JSValue = 1;//0禁止打印，不设置或者设为1允许打印
					vInfo  =  "事件：打印文档。打印份数为:"+lType+"\t打印机名称："+strName+"\t操作者证书:"+strValue;
				}else if(2==lActionType){
					vInfo  =  "事件：删除了第[ "+(lType+1)+" ]页,当前用户为:"+strValue;
				}else if(3==lActionType){
				 	vInfo  =  "事件：删除节点\t 节点类型为："+lType+"\t节点名称为:" + strName+"\t操作者证书:"+strValue;
				}else if(4==lActionType){
					vInfo  =  "事件：删除印章\t 印章ID为："+strName+"\t操作者:"+strValue;
					//当调用deleteSeal章的个数发生变化的时候 此处会自动执行
					//删除印章 调用锁定节点方法
					lockSignNode("unlock","1");
				}else if(5==lActionType){ 
					vInfo  =  "事件：加盖印章\t 印章ID为："+strName+"\t操作者:"+strValue;
					//添加印章 调用锁定节点的方法
					lockSignNode("lock","1");
				}else if(6==lActionType){
					//document.all.HWPostil1.JSValue = 0;
					vInfo  =  "事件：用户登陆\t 用户类型为："+lType+"\t操作者："+strName+"\t操作者证书颁发者:"+strValue;
				}else if(7==lActionType){
					vInfo  =  "事件：点击节点\t 节点类型为："+lType+"\t节点名称："+strName+"\t当前页数:"+strValue +"\t操作者:"+aipObj.GetCurrUserID();
					//alert("vInfo===="+vInfo);
					var strValue=parseInt(strValue)+1;
					var vmdset=aipObj.DocProperty("m_"+strName);
					var sqlArr = aipObj.DocProperty("sql_"+strName).split("|");
					var sqlScript = sqlArr[0];
					var codeArr = aipObj.DocProperty("code_"+strName).split("|");
					var codeType = codeArr[0];
					//vmdset!=""
					if(1){
						var vRet ; 
						var opiontype = '${opiontype}';//用于意见弹框
						
						if(strName.indexOf("Page") >= 0){
							var flag = strName.indexOf(".");
							strName = strName.substr(flag + 1);
						}
						
						if(strName=='suggest1' || strName=='suggest2' || strName=='suggest3' || strName=='suggest4' || strName=='suggest5'||strName=='suggest6'){//监狱减刑弹出框
	                        if(shanxi == 1){
	    						jxjsJailDanChu_sx(aipObj, strName);
	                        }else{
	                        	if(opiontype == '1') {
	                        		jxjsJailDanChu1(aipObj, strName);
	                        	}else {
	                        		jxjsJailDanChu(aipObj, strName);
	                        		//jxjsJailDanChushanxisheng(aipObj, strName);//陕西用
	                        	}
	                        }
	                        
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName=='sdjxjssuggest1' || strName=='sdjxjssuggest2' || strName=='sdjxjssuggest3' || strName=='sdjxjssuggest4' || strName=='sdjxjssuggest5'||strName=='sdjxjssuggest6'){
	    					//减刑假释意见弹出框（山东） 颜区泰
							jxjsJailDanChu_SD(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName=='points'){//计分考核条款选择框，可根据条款进行奖扣分
							rewardPunishsPointsEdit(aipObj);
							aipObj.jsValue = 0;
						}else if(strName=='seasonrewardname'){
							rewardApprovalCrimSelect(aipObj);
							aipObj.jsValue = 0;
						}else if(strName=='pastseasonrewardname'){
							rewardPastseasonApprovalCrimSelect(aipObj);
							aipObj.jsValue = 0;
						}else if(strName=='jiangfenpersons'){//罪犯选择弹出框，包含罪犯编号、姓名，可根据部门进行过滤
							rewardPunishsPointsCrimSelect(aipObj);
							aipObj.jsValue = 0;
						}else if(strName=='punishmentname'){
							punishmentCrimSelect(aipObj);
							aipObj.jsValue = 0;
						}else if(strName=='pun_suggest1'){
							punsuggestSelect(aipObj);
							aipObj.jsValue = 0;
						}else if(strName== 'sjzsuggest2' || strName=='sjzsuggest3' || strName=='sjzsuggest4' || strName=='sjzsuggest5' || strName=='sjzsuggest6'){
							sjzjxjsJailDanChu(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName== 'gdjysuggest1' || strName== 'gdjysuggest2' || strName=='gdjysuggest3' || strName=='gdjysuggest4' || strName=='gdjysuggest5' || strName=='gdjysuggest6'
							|| strName=='gdjysuggest7' || strName=='gdjysuggest8' || strName=='gdjysuggest9'){
							gdjyjxjsJailDanChu(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName=='sjsuggest2' || strName=='sjsuggest3' || strName=='sjsuggest4' || strName=='sjsuggest5'){//省局减刑弹出框
							//jxjsProvinceDanChu(aipObj, strName);
							gdjyjxjsJailDanChu(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName=='gxsjsuggest2' || strName=='gxsjsuggest3' || strName=='gxsjsuggest4' || strName=='gxsjsuggest5'){//广西省局减刑弹出框
							jxjsProvinceDanChu4GX(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName.indexOf('fysuggest2')!=-1 || strName.indexOf('fysuggest3')!=-1 || strName.indexOf('fysuggest4')!=-1 ||strName.indexOf('fysuggest5')!=-1){//法院弹出框
							jxjsCourtDanChu(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName.indexOf('prosecutor')!=-1){//检察机关出庭意见
							jxjsJianChaJiGuanDanChu(aipObj, strName);
							//不执行事件后续操作，必须加在此处，修改时请注意
							aipObj.jsValue = 0;
						}else if(strName=='zhuxing'){//基本信息填写主刑时触发
							zhuxing(strName);
						}else if(strName=='cjicourtnumber'){//基本信息填写判决书号时触发
							getcjicourtnumber(strName);
						}else if(strName=='bwjyzhpgyj'||strName=='bwjyjqyj'||strName=='bwjyksyj'||strName=='bwjypshyj'||strName=='bwjyjyyj'){//监狱保外
							if(shanxi == 1){
	    						bwjyspbTanChu_nx(aipObj,strName);
	                        }else {
	                        	bwjyspbTanChu(aipObj,strName);
	                        }
							aipObj.jsValue = 0;
						}else if(strName=='cjicriminalsort'){
							getwanf(strName);
						}else if(strName == 'courtnamecode'||strName == 'cjicourtname' ||strName=='fazhengjiguan'||strName=='cbiresidenceaddress'||strName=='caiarrestoffice'||strName=='cbihomeaddress'||strName=='cbinativeplaceaddress'){
							//||strName=='criofficiallydepartment'交付机关 暂时不用
							getGuoJi(strName);
						}else if(strName == 'xingba'){
							getBingfan(strName);
						}else if(strName == 'sanleifanleixing'){
							getSanLei(strName);
						}else if(strName == 'zhongyaozuifanleixing'){
							getZhongYao(strName);
						}else if(strName == 'othertype'){
							getQiTa(strName);
						}else if(strName=='citextent'||strName=='citchangetermto'){//变动幅度/变后刑期
							getbdfd(strName);
							aipObj.jsValue = 0;
						}else if(strName=='citchangedisfranchiseto'){//刑期变动--剥政
							getbz(strName);
							aipObj.jsValue = 0;
						}else if(strName.indexOf("lkcrimid") >=0|| strName.indexOf("lkname") >=0){
							leaveAreaChooseCriminal(aipObj,strName);
						}else if(strName.indexOf("homeaddress") >=0){//cbihomeaddress
							//罪犯基本信息表里，如果罪犯有家庭地址，要填写家庭成员的地址时，默认用罪犯的地址
							var cbihomeaddress = aipObj.GetValueEx("cbihomeaddress", 2,"",0,"");//罪犯地址
							var criminalFamilyAdd = aipObj.GetValueEx(strName, 2,"",0,"");//家庭成员地址
							if(cbihomeaddress && !criminalFamilyAdd){
								aipObj.SetValue(strName,"");
								aipObj.SetValue(strName, cbihomeaddress);
							}
						}else if(strName.indexOf("win=")!=-1){
							popUpWindow(strName,aipObj);
							//}else if(strName.indexOf('origin')!=-1||strName=='cbinativeplaceaddress'||strName=='cbihomeaddress') {//国籍（籍贯）
						}else if(strName.indexOf('origin')!=-1||strName=='cbihomeaddress') {//国籍（籍贯）
							guojiTanChu(aipObj,strName);
							aipObj.jsValue = 0;
						}else if(strName.indexOf("popup_")!=-1){
							var tempid = mini.get("tempid").getValue();
							publicPopUpWindow(aipObj, tempid, strName);
						}else if(strName=='attachfile')
						{
							doUploadFile();
							
						}
						//各级意见点击事件结束               
					}//vmdset!=""判断结束
				}//等于7的结束
			}
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	
	//表单节点的值发生变化时触发事件，nodeName：包括页码的节点名称
	function HWPostil1_NotifyChangeValue(nodeName,nodeValue){
		//去掉页码
		if(nodeName.indexOf("Page") >= 0){
			var flag = nodeName.indexOf(".");
			nodeName = nodeName.substr(flag + 1);
		}
		var aipObj = getmyvalue("HWPostil1");
		
		if(nodeName.indexOf("wlrygender") >=0){
			countMaleAndFemaleNum(aipObj, nodeName, nodeValue);
		}else if(nodeName == 'gltype'){
			var gltype = aipObj.GetValueEx(nodeName, 2,"",0,"");
			
			if(gltype == '违纪审查'){
				aipObj.SetValue("fyfenandate","");
				aipObj.SetValue("fyfenandate", 1);
				
				aipObj.SetValue("keshiorg","");
				aipObj.SetValue("keshiorg", "狱政");
				
				aipObj.SetValue("keshiorg2","");
				aipObj.SetValue("keshiorg2", "狱政");
			}else if(gltype == '案件审查'){
				aipObj.SetValue("fyfenandate","");
				aipObj.SetValue("fyfenandate", 2);
				
				aipObj.SetValue("keshiorg","");
				aipObj.SetValue("keshiorg", "狱侦");
				
				aipObj.SetValue("keshiorg2","");
				aipObj.SetValue("keshiorg2", "狱侦");
			}
		}else if(nodeName.indexOf("wlryname") >=0){
			countTotalNum(aipObj, nodeName, nodeValue);
		}else if(nodeName == "xcyh_dorgname"){
			var dorgname = aipObj.GetValueEx(nodeName, 2,"",0,"");
			var dorgid = getOrgidByOrgname(dorgname);
			aipObj.SetValue("dorgid","");
			aipObj.SetValue("dorgid", dorgid);
			
		}else if(nodeName=='confinedate1' || nodeName=='confinedate2'){
			var confinedate1 = aipObj.GetValueEx("confinedate1",2,"",0,"");
			var confinedate2 = aipObj.GetValueEx("confinedate2",2,"",0,"");
			if(confinedate1 && confinedate2){
				confinedate1 = formDateFormat(confinedate1);
				confinedate2 = formDateFormat(confinedate2);
				var flag = dateCompare(confinedate1, confinedate2);
				if(flag == true){
					var days = getDays(confinedate1, confinedate2);
					aipObj.SetValue("confinedays","");
					aipObj.SetValue("confinedays",days);
				}else{
					alert("结束日期必须大于起始日期！");
					aipObj.SetValue("confinedays","");
				}
			}
		}else if(nodeName.indexOf("srorgname") >=0){//原先基本信息表单上的家庭成员的工作单位及职务，现改为家庭成员的身份证号
			var personid = aipObj.GetValueEx(nodeName, 2,"",0,"");
			/* if(personid){
				var flag = personidValidation(personid);
				if(flag == false){
					//alert("身份证输入有错！");
					aipObj.SetValue(nodeName,"");
				}
			} */
		}
		
	}
	
	/*************************************************\
				显示模版元素选择的对话框
	**************************************************/
	var getmyvalue = function(id) {return document.getElementById(id);};
	function ShowDialog(id)
	{
		getmyvalue("overlay").style.display = 'block';
		getmyvalue(id).style.display = 'block';
	
		var bb=(document.compatMode && document.compatMode!="BackCompat") ? document.documentElement : document.body;
		if(document.compatMode && document.compatMode!="BackCompat")
		{
			getmyvalue("overlay").style.width = bb.scrollWidth+"px";
			getmyvalue("overlay").style.height = bb.scrollHeight+"px";
		}
		else
		{
			getmyvalue("overlay").style.width = bb.scrollWidth+"px";
			getmyvalue("overlay").style.height = bb.scrollHeight+"px";
		}
		getmyvalue(id).style.left = ((bb.offsetWidth - getmyvalue(id).offsetWidth)/2)+"px";
		getmyvalue(id).style.top  = (90 + bb.scrollTop)+"px";
	}
	function HideDialog(id)
	{
		getmyvalue("overlay").style.display = 'none';
		getmyvalue(id).style.display = 'none';
	}
	/****************************************************************\
				进入TBA设置模式(必须在设计模式下才能进入该模式)
	*****************************************************************/
	function TabMode(){
		try{
			var aipObj=document.getElementById("HWPostil1");
			if(aipObj.InTabSortMode == true){
				aipObj.InTabSortMode = false;
			}else
			if(aipObj.InTabSortMode == false){
				aipObj.InTabSortMode = true;
			}
		}catch(e) {
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
		}
	}
	
	function saveemodel(){
		var MDCODE=saveFile();
		document.form1.MODEL_CODE.value=MDCODE;
		document.form1.submit();
	}

	//签章
	function doSealFunction(){
		var aipObj = getmyvalue("HWPostil1");
		var dtrer=0;
		aipObj.Logout();
		dtrer=aipLogin(aipObj);
		if(dtrer==-200){
			alert("未发现智能卡");
		}else if(dtrer!=0){
			//alert("登录失败。错误ID:"+dtrer);
		}else{
			aipObj.CurrAction = 2568;
		}
	}


	//操作表单按钮 operateType操作类型 0打开1另存为2OCR识别3阅读4全屏5盖章6手写7手写擦除8指纹9打印10盖章撤销
	//11登陆 12笔迹设置13骑缝章14多页盖章15前一页16后一页 17检索文本
	//是否阅读状态
	var temp =0;
	//操作状态标志0表示无操作 目前加入了手写、手写擦除状态
	var operateflag = 0;
	function doMenueButton(operateType){
		var aipObj = getmyvalue("HWPostil1");
		//用户未登录则自动登录 (全屏、阅读、打印、另存不判断 )
		if(operateType!=1&&operateType!=3&&operateType!=4&&operateType!=9&&operateType!=17){
			var flag = aipLogin(aipObj);
			if(flag==-200){
				alert("请先插入智能卡！");
				return;
			}else if(flag!=0){
				alert("登录失败！");
				return;
			}
			var isUserKey = isUserSignatureKey(aipObj);
			if(false == isUserKey){
				return;
			}
		}
		if(operateType == 0){
			aipObj.InputHotKey(0xC000);
		}else if(operateType == 1){
			aipObj.InputHotKey(0xC001);
		}else if(operateType == 2){
		
		}else if(operateType == 3){
			if(temp==0){
				aipObj.SetPageMode(32,1);
				temp=1;
			}else{
				aipObj.SetPageMode(32,0);
				temp=0;
			}
		}else if(operateType == 4){
			aipObj.ShowFullScreen = 1;
		}else if(operateType == 5){
			aipObj.CurrAction = 3592;//2568 添加印章操作状态,印章文件取自文件。  3592 设置盖网络印章的操作状态。
		}else if(operateType == 6){
			if(operateflag==operateType){
				aipObj.CurrAction = 1;
				operateflag = 0;
			}else{
				aipObj.CurrAction = 264;
				aipObj.WaterMarkTextColor = -1;			
				operateflag=operateType;
			}
			return;
		}else if(operateType == 7){
			if(operateflag==operateType){
				aipObj.CurrAction = 1;
				operateflag = 0;
			}else{
				aipObj.CurrAction = 16;
				operateflag=operateType;
			}
			return;
		}else if(operateType == 8){
			//此方法会导致ie崩溃暂时注释，询问点聚解决方法
			//aipObj.CurrAction = 3080;
		}else if(operateType == 9){
			aipObj.InputHotKey(0xC002);
			//aipObj.PrintDocEx('',1,0,0,0,9999,0,1,1,0,0);
		}else if(operateType == 10){
			deleteSeal();
			alert("撤销签名成功，请存盘");
		}else if(operateType == 11){
			aipObj.CurrAction = 1;
		}else if(operateType == 12){
			aipObj.CurrPenColor = -1; 
		}else if(operateType == 13){
			aipObj.RunCommand(0, 32954, 0);
		}else if(operateType == 14){
		
		}else if(operateType == 15){
			if(aipObj.CurrPage<=0){
	
			}else{
				aipObj.CurrPage = aipObj.CurrPage-1;
			}
		}else if(operateType == 16){
			if(aipObj.CurrPage>=aipObj.PageCount-1){
	 
	 		}else{
				aipObj.CurrPage = aipObj.CurrPage+1;
			}
		}else if(operateType == 17){
			aipObj.SearchText("",0,0);
		}else if(operateType == 18){
			perPrintDoc(aipObj);
			//aipObj.InputHotKey(0xC002);
		}
		operateflag = 0;
	}

	//控制表单某个字段不能打印的函数
	function perPrintDoc(aipObj){
		var aipObj=document.getElementById("HWPostil1");
		var tempid = document.getElementById("tempid");
		if(tempid){
			 tempid = tempid.value;
			 $.getJSON('<%=path%>/txt/notnodeval.txt',function(data){ 
				$.each(data,function(i,n){ 
					if(tempid == n["id"]){
						$.each(n["text"].split(","),function(i,n){ 
							aipObj.SetValue(n,"");
						}); 
					}
				}); 
			});
		}
		aipObj.InputHotKey(0xC002);
	}
	
	//展示表单 
	function HWPostil1_display(obj1,obj2) {
		var aipObj=document.getElementById("HWPostil1");
		//向表单赋值 
		if(obj1){
		    //移除value中的" 
			var objTemp = "";
			var reg = new RegExp("{\"|\":\"|\",\"|\"}|,\"|\":|[^\"]","g");
			var ret = obj1.match(reg);
			for(var tt=0;tt<ret.length;tt++){
				objTemp += ret[tt];
			}
			obj1 = objTemp;
			var objs1 = eval('(' + obj1 + ')');
			if(objs1){
			    var _value = objs1[i];
				for(var i in objs1){
					var _value = objs1[i];
					//有赋值操作的编辑区域设置为不可编辑，如果不希望进行此设置（即希望此编辑框可编辑）则需要在后台传值的时候在编辑区域名称后加edit后缀
					//此处接收到以edit结尾的数据后，将此数据的值赋给去除edit之后的编辑框，并且此类数据不设置为不可编辑
					if(i.substr(i.length-4)!='edit'){
						//控制是否可编辑，不以edit结尾并且有值的编辑区域不可编辑
						//if(_value){aipObj.SetValue(i,":PROP::LABEL:3");}
						//else {aipObj.SetValue(i,":PROP:READONLY:0");}
					}else{
						//以edit结尾的编辑区域不设置不可编辑，同时将区域名称置为去除edit的字符串
						i = i.substr(0,i.length-4);
						//aipObj.SetValue(i,":PROP:READONLY:0");
					}
					aipObj.SetValue(i,"");
					if(i=='picrjdjimg'){//基本信息表的图片，加载数据库的base64字符串
						//alert(_value);
						if(_value.slice(-".jpg".length) != ".jpg"){
							aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
							aipObj.SetValueEx(i,14,0,"STRDATA:"+_value);
							aipObj.Logout();
						}else{
							aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
							aipObj.SetValueEx(i,14,0, _value);
							aipObj.Logout();
						}
					}else if(i.indexOf("CheckBox")!=-1 || i.indexOf("RadioBox")!=-1 ){//单复选框
						aipObj.SetValueEx(i,3,_value,"");
					}else if(i.indexOf("checkbox")!=-1 || i.indexOf("radiobox")!=-1 ){//单复选框
						aipObj.SetValueEx(i,3,_value,"");
					}else{
						aipObj.SetValue(i,_value);
					}
				}
			}
		}
		//下拉框
		if(obj2){
			var objs2 = eval('(' + obj2 + ')');
			if(objs2){
				for(var i in objs2){
					//alert(i + "|" + objs2[i]);
					aipObj.SetValueEx(i,43,0,objs2[i]);
				}
			}
		}
		var tempid = '';
		var tempidObj = document.getElementById("tempid");
		if(tempidObj) tempid = tempidObj.value;
		//锁定字段
		if(tempid == 'SJGL_ZFSYSCB'){//新犯入监特殊处理
			var name = aipObj.GetValueEx("name",2,"",0,"");
			if(name){
				aipObj.SetValue("bianhao",":PROP::LABEL:3");
				aipObj.SetValue("danganhao",":PROP::LABEL:3");
			}
		}
		if(tempid!=''){
			$.ajax({
				type:"POST", 
				url:'<%=path%>/public/ajaxnoteditlist.json', 
				data:{tempid:tempid}, 
				cache:false,
				async:false,
				success:function (text) {
					var obj = eval(text);
					if(obj){
						for(var i=0;i<obj.length;i++){
							var name = obj[i];
							aipObj.SetValue(name,":PROP::LABEL:3");
						}
					}
			}});
		}
		//设置特殊后缀的可编辑、不可编辑情况
		//patch后缀的编辑区域：用以遮挡原表单需要修正区域的编辑区域，此类编辑区域背景色为白色，内容固定，永久不可编辑
		//seal后缀的编辑区域：用以作为批量签章定位的编辑区域，此类编辑区域内容固定，永久不可编辑
		//设置流程中不属于本级的编辑框为只读 
		var nodeid = '0';
		var flowdefid = '';
		var flowdraftid = '';
		var flowdefidObj = document.getElementById("flowdefid");
		var nodeidObj = document.getElementById("snodeid");
		var flowdraftidObj = document.getElementById("flowdraftid");
		if(nodeidObj){
		 	nodeid = nodeidObj.value;
		}else{
			nodeidObj = document.getElementById("lastnodeid");
			if(nodeidObj){
				nodeid = nodeidObj.value;
			}
		}
		if(flowdefidObj) flowdefid = flowdefidObj.value;
		if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
		if(flowdefid&&flowdefid!=''){
			$.ajax({
				type:"POST", 
				url:'<%=path%>/public/ajaxnoteditattributelist.json', 
				data:{nodeid:nodeid,flowdefid:flowdefid}, 
				cache:false,
				async:false,
				success:function (text) {
					var obj = eval(text);
					if(obj){
						for(var i=0;i<obj.length;i++){
							var name = obj[i];
							aipObj.SetValue(name,":PROP::LABEL:3");
						}
					}
			}});
			$.ajax({
				type:"POST", 
				url:'<%=path%>/public/ajaxeditattributelist.json', 
				data:{nodeid:nodeid,flowdefid:flowdefid,flowdraftid:flowdraftid}, 
				cache:false,
				async:false,
				success:function (text) {
					var obj = eval(text);
					if(obj){
						for(var i=0;i<obj.length;i++){
							var name = obj[i];
							aipObj.SetValue(name,":PROP::LABEL:0");
						}
					}
			}});
		}
		
	}
	//控制节点是否可编辑
	function isEditAttribute(){
		var nodeid = '';
		var tempid = '';
		var flowdefid = '';
		var isapprove = '';
		var flowdraftid = '';
		var nodeidObj = document.getElementById("snodeid");
		var isapproveObj = document.getElementById("isapprove");
		var tempidObj = document.getElementById("tempid");
		var flowdefidObj = document.getElementById("flowdefid");
		var flowdraftidObj = document.getElementById("flowdraftid");
		if(nodeidObj) nodeid = nodeidObj.value;
		if(isapproveObj) isapprove = isapproveObj.value;
		if(tempidObj) tempid = tempidObj.value;
		if(flowdefidObj) flowdefid = flowdefidObj.value;
		if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
		//alert(nodeid+"||"+isapprove+"||"+flowdefid+"||"+flowdraftid);
		if(isapprove == 'yes'){
			var aipObj = document.getElementById("HWPostil1"); 
			//aipObj.DocProperty("DocReadonly") = "true";设置文档为只读会导致无法进行签章、手写等操作
			//设置所有的编辑区域不可编辑
			var NoteInfo;
			while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				aipObj.SetValue(NoteInfo,":PROP::LABEL:3");
			}
			//设置当前流程节点对应的编辑区域可编辑
			$.ajax({
				type:"POST", 
				url:'<%=path%>/public/ajaxeditattributelist.json', 
				data:{nodeid:nodeid,flowdefid:flowdefid,flowdraftid:flowdraftid}, 
				cache:false,
				dataType:"json",
				async:false,
				success:function (text) {
					var obj = eval(text);
					if(obj){
						for(var i=0;i<obj.length;i++){
							var name = obj[i];
							aipObj.SetValue(name,":PROP::LABEL:0");
							aipObj.SetValue(name,":PROP:READONLY:0");
						}
					}
			}});
		}else{
			
			if(flowdefid){
				if(!(tempid =='SDXF_BASE_RJDJB')){
					//other_sysflow为系统流程中，只走一级。其是否可编辑不在流程定义表中控制
					if(flowdefid == 'other_sysflow'){
						flowdefid =null;
					}
					controlEditNode(tempid,flowdefid,flowdraftid);
				}
			}
			
		}
	}

	/**
	 * author:zhenghui
	 * day:2015-02-28
	 * 控制节点是否可编辑
	 * resnodeid 资源权限对应的流程节点ID
	 * flowdefid 流程自定义ID
	 * flowdraftid 流程草稿ID
	 * tempid 表单ID
	 */
	function controlEditNode(tempid,flowdefid,flowdraftid){
		var aipObj = document.getElementById("HWPostil1");
		if(flowdefid){
			//表单挂流程的情况
			//1、锁定所有节点
			var NoteInfo;
			while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				aipObj.SetValue(NoteInfo,":PROP::LABEL:3");
			}
			//2、打开流程审批可操作的节点
			returnNodes(aipObj,tempid,flowdefid,flowdraftid);
		}else{
			//没有挂流程的表单：
			//1、打开所有的节点
			var NoteInfo;
			while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				aipObj.SetValue(NoteInfo,":PROP::LABEL:0");
			}
			//2、锁定不可编辑的节点
			returnNodes(aipObj,tempid,'','');
			//aipObj.DocProperty("DocReadonly") = "true";设置文档为只读会导致无法进行签章、手写等操作
		}
	}
	
	/**
	 * 描述：设置当前表单节点对应的编辑区域是否可编辑
	 * author:zhenghui
	 * day:2015-02-28
	 * tempid
	 * flowdefid ：可为空
	 * flowdraftid ：可为空
	 */
	function returnNodes(aipObj,tempid,flowdefid,flowdraftid){
		$.ajax({
			type:"POST", 
			url:'<%=path%>/public/ajaxGetIsEditableNode.json', 
			data:{tempid:tempid, flowdefid:flowdefid, flowdraftid:flowdraftid}, 
			cache:false,
			async:false,
			success:function (text) {
				var obj = eval(text);
				if(obj&&obj!=''){
					if(flowdefid){
						for(var i=0;i<obj.length;i++){
							var name = obj[i];
							//设置流程可编辑节点
							aipObj.SetValue(name,":PROP::LABEL:0");
							aipObj.SetValue(name,":PROP:READONLY:0");
						}
					}else{
						for(var i=0;i<obj.length;i++){
							var name = obj[i];
							//设置表单不可编辑节点
							aipObj.SetValue(name,":PROP::LABEL:3");
							aipObj.SetValue(name,":PROP:READONLY:3");
						}
					}
				}
		}});
	}
	
	
	
	//打开工具栏
	function dakai(){
		var aipObj=document.getElementById("HWPostil1");
		//aipObj.HideMenuItem(24576);//隐藏操作和用户登录菜单项
		var da = mini.get("dakai");
		var userid = aipObj.GetCurrUserID();
		if(userid == "HWSEALDEMO**") aipObj.Logout();
		if(flagg){
			aipObj.ShowToolBar = 1;//工具栏是否显示
			flagg = false;
		}else{
			aipObj.ShowToolBar = 0;//工具栏是否显示
			flagg = true;
		}
	}
	//返回
	function back(){
		if (window.CloseOwnerWindow) return window.CloseOwnerWindow("cancel");
	}

	//保存
	function save(){
		var MDCODE=saveFile();
		//测试下拉列表
		//alert(aipObj.GetValueEx("jiguan",2,"",0,""));
		var json = getNoteMap();
		if(json.length>0){
			$.ajax({
				type:"POST", 
				url:'<%=path%>/test/savedata.action?1=1', 
				data:{json:mini.encode(json)}, 
				cache:false,
				success:function (text) {
				var objs = eval(text);
			}});
		}
	}

	//获取表单节点和其对应的节点属性值
	function getNoteMap() {
		var aipObj = document.getElementById("HWPostil1");
		var NoteInfo;
		var notemap = new Map();
		while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
			var temp = NoteInfo.split(".")[1];
			var value = aipObj.GetValueEx(NoteInfo, 2, "", 0, "");
			notemap.put(temp, value);
		}
		return "["+mini.encode(notemap)+"]";
	}	

	function deleteSeal(){
		var aipObj = document.getElementById("HWPostil1");
	    if(!aipObj.IsOpened()){
	    	alert("必须打开文件，才可以操作");
			return;
		}
		var SerialNumber = aipObj.GetSerialNumber();   //获取当前UK里证书序列号
		var NoteInfo= aipObj.GetNextNote(SerialNumber,0,"");
		var oldnote="";
		while(NoteInfo != ""){
			oldnote=NoteInfo;
			aipObj.DeleteNote(oldnote);//删除节点
			NoteInfo= aipObj.GetNextNote(SerialNumber,0,"");
		}
		return true;
	}
	
	function _DeleteSeal(obj){
	    if(!obj.IsOpened()){
	    	alert("必须打开文件，才可以操作");
			return;
		}
    	var SerialNumber = obj.GetSerialNumber();   //获取当前UK里证书序列号
    	var NoteInfo=obj.GetNextNote(SerialNumber,0,"");
    	var oldnote="";
    	while(NoteInfo != ""){
    		oldnote=NoteInfo;
    		//alert(oldnote+"：单个签章");
    		NoteInfo=obj.GetNextNote(SerialNumber,0,"");
    		var delResult = obj.DeleteNote(oldnote);//删除节点
			//alert(protectnode+"||ukey序列号："+SerialNumber+"||oldnode:"+oldnote+"||NoteInfo:"+NoteInfo);
			//if (NoteInfo != "") {
				//调用 解锁 被签章锁定的方法(章的个数发生变化的时候会自动执行)
			//}
			//alert(delResult);
    	}
		//lockSignNode("unlock",protectnode);
    }
	
	//全局变量标示：签章的时候已经有章处理方式
	var oldsignnum = 0;
	//定义全局变量 删除章的时候 不需要再去数据库查询
	var protectnode = "";
	//单个签章 打开页面 可以把对应 锁定节点查询出来，但是批量签章是不行，所以lockSignNode应该在加一个参数
	function openAip(flowdefid){
    	var aipObj = document.getElementById("HWPostil1");
		var signnode = aipObj.GetValueEx("signsuggest",2,"",0,"");
    	if(protectnode == ""){
    		$.ajax({
    			url:'<%=path%>/public/lockSignNode.json?1=1&flowdefid='+flowdefid,
    			type:'post',
    			async:false,
    			success:function (text){
    				protectnode = text;
    				var signnodes = signnode.split("@");
    				if(signnode.indexOf(protectnode) != -1){
    		    		for (var m = 0; m < signnodes.length; m++) {
    		    			//open aip default lock node
    						if(signnodes[m].indexOf(protectnode) != -1){
    							locknode(protectnode,aipObj,"lock");
    						}
    					}
    		    	}
    			}
    		});
    	}
	};
	
	
	/**
		将表单上格式为yyyy年MM月dd日 转为yyyy-MM-dd
		@author YangZR  2015-10-08
	*/
	function formDateFormat(formDate){
		if(formDate && formDate.indexOf("月") >=0 && formDate.indexOf("日") >=0){
			formDate = formDate.replace("年","-").replace("月","-").replace("日","");
			return formDate;
		}
	}
	
	/**
		计算格式为yyyy-MM-dd日期的天数差
		@author YangZR  2015-10-08
	*/
	function getDays(startDateStr, endDateStr){
	    var iDays;
	    
	    var sDateArr = startDateStr.split("-");
	    var eDateArr = endDateStr.split("-");
		var startDate = new Date(sDateArr[0], sDateArr[1]-1, sDateArr[2]); 
		var endDate = new Date(eDateArr[0], eDateArr[1]-1, eDateArr[2]); 
		
		//把相差的毫秒数转换为天数 
	    iDays = parseInt(Math.abs(startDate - endDate ) / 1000 / 60 / 60 /24);
	    return iDays;
	}
	
	/**
		比较两个格式为yyyy-MM-dd的日期大小
		@author YangZR  2015-10-08
	*/
	function dateCompare(startDateStr,endDateStr){ 
		var sDateArr = startDateStr.split("-"); 
		var startDate = new Date(sDateArr[0], sDateArr[1]-1, sDateArr[2]); 
		var startTime = startDate.getTime(); 

		var eDateArr = endDateStr.split("-"); 
		var endDate = new Date(eDateArr[0], eDateArr[1]-1, eDateArr[2]); 
		var endTime = endDate.getTime(); 

		if(startTime >= endTime){ 
			return false; 
		} 
		return true; 
	} 

	/**
	* YangZR	2015-11-10
	*身份证号验证
	*/
	function personidValidation(personid){
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
		//var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/; 
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		return reg.test(personid);
	}

	
</SCRIPT> 
<input type="hidden" id="basePath" name="basePath" value="<%=realPath %>"/>
<script type="text/javascript" src="<%=path%>/scripts/form/control/control_approve.js"></script>
<script type="text/javascript" src="<%=path%>/scripts/unlocksign.js"></script>

