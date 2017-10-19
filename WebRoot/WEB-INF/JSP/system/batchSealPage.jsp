<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,com.sinog2c.model.system.SignatureScheme" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>批量签章2</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
			body {
				margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
			}
		</style>
		<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
		<script language="JavaScript" for="HWPostil1" event="NotifyCtrlReady">
			// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
			document.all.HWPostil1.JSEnv = 1;
		</script>
	</head>
	<body>
		<div id="form1" class="mini-splitter" vertical="true" style="width: 100%; height: 100%; border: 0px;">
			<div size="30px;" showCollapseButton="false" style="border-top: 0px; border-left: 0px; border-right: 0px;">
				<div class="mini-toolbar" style="padding: 2px; border-bottom: 0;">
					<input class="mini-hidden" id="notExistClobCaseNos" name="notExistClobCaseNos" value=""/>
					<input class="mini-hidden" id="notFitOrgCaseNos" name="notFitOrgCaseNos" value=""/>
					<input class="mini-hidden" id="notThisLevelCaseNos" name="notThisLevelCaseNos" value=""/>
					<input class="mini-hidden" id="fitCaseNos" name="fitCaseNos" value=""/>
					<input class="mini-hidden" id="sealResult" name="sealResult" value=""/>	<!-- 签章或撤销的结果 -->
					<input class="mini-hidden" id="sealOrRevoke" name="sealOrRevoke" value=""/> <!-- 签章或撤销签章 	临时存放 -->
					
					案件号：（
					       <select id="year" name="year">
						       	<option value='${currYear - 1}'>${currYear - 1}</option>
						       	<option selected='selected' value='${currYear}'>${currYear}</option>
						       	<option value='${currYear + 1}'>${currYear + 1}</option>
					      </select>
					）
					<input value="${orgShortname}" id="orgShortname" style="width: 60px" name="orgShortname" />
 					<select id="casetype" name="casetype">
 						<c:forEach items="${caseTypeList}" var="casetype" >
 							<option value="${casetype.name}">${casetype.name}</option> 
 			       		</c:forEach>
 					</select> 
 					<!--  
                    <input id="casetype" class="mini-combobox" valueField="id" textField="text" url="<%=path%>/getCodeByCode.json?1=1&codeType=GK7799&pcodeid=GK7799" 
    				 style="width:70px;"/>
    				 -->
					第<input class="mini-textbox" style="width: 160px;" emptyText="案件号：1,2或3-5或1,2,3-5" name="casenos" id="casenos" vtype="maxLength:100" />号
					
					&nbsp; 签章日期：<input type="text" class="mini-datepicker" name="sealdate" ondrawdate="onDrawDate" id="sealdate"  value="${sealdate}" style="width: 100px" />
					&nbsp;
					<select id="signid" name="signid" style="width: 200px">
						<option></option>
						<c:if test="${not empty signatureSchemes}">
							<c:forEach items="${signatureSchemes}" var="signatureScheme" >
								<option value="${signatureScheme.signid}">${signatureScheme.name}</option>
							</c:forEach>
						</c:if>
					</select>					&nbsp;
					<a class="mini-button" plain="true" onclick="batchSeal(1);">签章</a> &nbsp;
					<a class="mini-button" plain="true" onclick="batchSeal(2);">撤销签章</a>&nbsp;&nbsp;
					<!--  
					<a class="mini-button" plain="true" onclick="copyImg();">拷照片</a> 
					-->
				</div>
			</div>
			<div showCollapseButton="false" >
				<div id="showContent" style="height: 100%; overflow: auto; display: none; background: #ababab; color: #000000; text-align: left;"></div>
				<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
				<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
				<script LANGUAGE="javascript" src="<%=path%>/scripts/unlocksign.js"></script>
				<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
			</div>
		</div>
		
	<script type="text/javascript">
		document.all("MyViewer").Init(window, document,600);
	</script>
	<script type="text/javascript">	
	
	mini.parse();
 	var obj2=document.getElementById("HWPostil1");
 	obj2.ShowToolBar = 0;//工具栏是否显示
  	obj2.ShowDefMenu = 0;//菜单栏
  	obj2.ShowScrollBarButton = 1;
  	

    //mini.get("casetype").select(0);
  	
  	var path = '<%=path%>';
  	
  	//sealOrRevoke  1： 签章，2：撤销签章
  	//批量签章(撤销签章)
  	function batchSeal(sealOrRevoke){
  		mini.get("sealOrRevoke").setValue(sealOrRevoke);
  		var flag = initConditionBeforeOperate();
  		if(!flag){
  			return flag;
  		}
  		//开始批量签章(撤销签章)
  		doSealData();
  	}
  	
  	
  
  //获取签章数据
  function doSealData(){
  		var caseno = getNextCaseno();
  		if(caseno){
  			//递归的进行签章
  			sealData(caseno);
  		}else{
  			//签章结束处理：将签章的结束显示给用户
  			dealSealEnd();
  		}
  }
  
  
  //递归的进行签章
  function sealData(caseno){
  		//获取签章数据，并签章,并保存
  		getSealDataAndSeal(caseno);
  		//递归
  		doSealData();
  }
  
  //获取某个案件号的签章数据（可能存在多个大字段），并签章
  function getSealDataAndSeal(caseno){
		var signid = $("#signid").val();
  		//var casetype = mini.get("casetype").getText();
  		var casetype = $("#casetype").val();
  		var sealdate = $("#sealdate").val();
  		var year = $("#year").val();
  		var type = 'seal';
  		var sealOrRevoke = mini.get("sealOrRevoke").getValue();
  		var url = "<%=path%>/sign/getSealData.json?1=1";
  		$.ajax({
             url: url,
             data: {signid:signid, sealOrRevoke:sealOrRevoke, casetype:casetype, year:year, caseno:caseno, type:type,sealdate:sealdate},
             type: "post",
             async: false,
             success: function (text){
             	//做签章并保存
           var dataBeforeSeal = mini.decode(text);
             	seal(caseno,dataBeforeSeal);
             },
             error: function (jqXHR, textStatus, errorThrown){
            	 dealFailSealMessage(caseno);
             }
         });
  }
  
  
  function seal(caseno,dataBeforeSeal){
  	  	var aipObj = document.getElementById("HWPostil1");
		var date = $("#sealdate").val();
		var sealOrRevoke = mini.get("sealOrRevoke").getValue();
		
  	  	aipObj.CloseDoc(0);
		aipObj.Logout();
		var aipLoginValue = aipLogin(aipObj);
		if(aipLoginValue == -200){
			alert("未发现智能卡");
			return;
		}else if(aipLoginValue != 0){
			alert("登录失败。错误ID:" + aipLoginValue);
			return;
		}
		
		var dataAfterSeal = [];
		/*
		  符合签章(撤销签章):Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
	 	 									flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid,  signtype:signtype, signname:signname}
	 	  不符合签章(撤销签章):Map{sealable:0, signtype:signtype,  signname:signname, status:status}
		*/
		
		for(var i=0,l=dataBeforeSeal.length; i<l; i++){
			var map = dataBeforeSeal[i];
			var sealable = map.sealable;
			if(0==sealable){
				notFitSealMessage(caseno,map);
			}else if(1==sealable){
				singleSeal(caseno,map, dataAfterSeal);
			}else{
				dealSingleFailSealMessage(caseno, map);
			}
		}
        saveSealData(caseno, dataAfterSeal);
  }
  
  //保存签章后的数据
  function saveSealData(caseno, dataAfterSeal){
  		data = mini.encode(dataAfterSeal);
  		var url = "<%=path%>/sign/saveDataAfterSeal.json?1=1";
		$.ajax({
             url: url,
             data: {data:data},
             type: 'POST',
             async:false,
             success: function (text){
           		var mapList = mini.decode(text);
           		if(mapList.status == '0'){
           			dealFailSealMessage(caseno);
           		}else{
           			dealSuccessMessages(caseno,mapList);
           		}
             },
             error: function (jqXHR, textStatus, errorThrown) {
              	   
             }
         });
  }
   
   //Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
   // * 	  flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid,  signtype:signtype, signname:signname}
   function singleSeal(caseno,map, dataAfterSeal){
   		if(!map.sealable || map.sealable !=1){
   			dealSingleFailSealMessage(caseno, map);
   			return;
   		}
		//
	   var aipObj = document.getElementById("HWPostil1");
   		var sealOrRevoke = mini.get("sealOrRevoke").getValue(); 
   		var content = map.content;
   		var schemes = map.scheme;
   		var sealtype = map.sealtype;
   		var protectnode = map.protectnode;//数字签名保护节点
	   	
	   	var formdata = map.formdata;
	   	var date = mini.get("sealdate").getText();//$("#sealdate").val();
   		
   		
   		if(sealtype && sealtype == '1'){
   			var reportdata = map.reportdata;
   			dayinwenshu(reportdata);
   		}else{
   			//加载表单
   		var aipFileStr = map.aipFileStr;
   			loadFormData(aipFileStr, formdata, null);
   		}
   		//
  		if(aipObj.IsOpened()){
			aipObj.ForceSignType=7;
			aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
			var sealStatus = false;
			if(2==sealOrRevoke){
				sealStatus = DeleteSeal(aipObj);
				//如果有配制签章日期，将签章日期去掉
				var sealSchemeArr = getSealSchemeArr(schemes);
				for(var i=0;i<sealSchemeArr.length; i++){
					var currScheme = sealSchemeArr[i];
					sealSetDate(aipObj, currScheme, '');
				}
			}else if(1==sealOrRevoke){//盖章
				if(date){
					date = date.replace(/\-/g,"");//把yyyy-MM-dd格式转换成yyyyMMdd
				}
				if(!formdata){
					formdata = {};
				}
				//formdata["testnode"] = "这是一个大家都知道的事这是一个大家都知道的事这是一个大家都知道";
				//formdata["testnodedate"] = "1989年5月25日";
				
				//画意见节点 和 签章节点 ，同时附值
				var opinionSchemeArr = getOpinionSchemeArr(schemes);
				for(var i=0;i<opinionSchemeArr.length; i++){
					var currScheme = opinionSchemeArr[i];
					var nodename = getNodeinfoFromScheme(currScheme, "节点名称");
					var nodetype = getNodeinfoFromScheme(currScheme, "节点类型");
					
					var nodeObj = aipObj.GetNoteWidth(nodename);//节点的宽度
					if(0==nodeObj){//如果节点的宽度为0，说明节点不存在，则画节点，否存不画节点
						var nodevalue = "";
						if(formdata[nodename]){
							nodevalue = formdata[nodename];
						}
						if(nodetype==0){
							sealStatus = InsertNote(currScheme,aipObj,'','','',nodevalue);
						}else if(nodetype==2){
							date = format2DateChs(date);
							sealStatus = InsertNote(currScheme,aipObj,'', date, nodevalue,'');
						}
					}else{
						if(nodetype==2){
							date = format2DateChs(date);
							aipObj.SetValue(nodename, "");
							aipObj.SetValue(nodename, date);
						}
					}
				}
				
				//签章时，设置signsuggest节点中值
				//签章的同时把签章锁定的节点也放到表单的隐藏域中
				//然后做进一步锁定处理(参数1：操作状态，参数2：需锁定节点)
				//备注 湖南锁定节点 不在使用。
				//lockSignNode("lock",map.protectnode);
				
				//盖章
				var sealSchemeArr = getSealSchemeArr(schemes);
				for(var i=0;i<sealSchemeArr.length; i++){
					var currScheme = sealSchemeArr[i];
					//签章时，设置日期
					sealSetDate(aipObj, currScheme, date);
					//数字签名保护
					sealProtectnode(aipObj, protectnode, caseno, map);
					//签章
					sealStatus = InsertNote(currScheme,aipObj,'',date,'','');
				}
				//
			}
			
			if(sealStatus == false){
				aipObj.CloseDoc(0);
				dealSingleFailSealMessage(caseno, map);
				return;
			}else{
				aipFileStr = aipObj.GetCurrFileBase64();
				map.aipFileStr = aipFileStr;
				
				var notationnum = aipObj.GetNoteNum(1);//手写批注的个数
				var sealnum = aipObj.GetNoteNum(251);//电子签章的个数
				map.notationnum = notationnum;
				map.sealnum = sealnum;
				if(map.reportdata){
					delete map["reportdata"];
				}
				dataAfterSeal.push(map);
			}
			aipObj.SleepSecond(1);//为什么？
		}
		aipObj.CloseDoc(0);
   }
   
   function getOpinionSchemeArr(schemes){
	   var schemeArr = schemes.split(";");
	   var result = [];
	   
	   for(var i = 0; i<schemeArr.length; i++){
		   if(schemeArr[i].indexOf("节点类型:0") != -1 || schemeArr[i].indexOf("节点类型:2") != -1){
			   result.push(schemeArr[i]);
		   }
	   }
	   return result;
   }
   
   function getNodeinfoFromScheme(scheme, nodeinfo){
	   var theArray=new Array();
		var schemeArray = scheme.split(",");
		for(var m=0;m<schemeArray.length;m++){
	    	theArray[m]=schemeArray[m].split(":");
	    }
		
		var nodeinfovalue="";
	   	for(var m=0;m<theArray.length;m++){
			if(theArray[m][0]==nodeinfo){
				nodeinfovalue = theArray[m][1];//页码
	   		}
	   	}
	   
	   	return nodeinfovalue;
   }
   
   
   function getSealSchemeArr(schemes){
	   var schemeArr = schemes.split(";");
	   var result = [];
	   for(var i = 0; i<schemeArr.length; i++){
		   if(schemeArr[i].indexOf("节点类型:1") != -1 || schemeArr[i].indexOf("节点类型:3") != -1 || schemeArr[i].indexOf("节点类型:4") != -1 || schemeArr[i].indexOf("节点类型:3") != -1){
			   result.push(schemeArr[i]);
		   }
	  }
	  return result;
   }
   
 	//签章时，设置日期
   function sealSetDate(aipObj, scheme, date){
	   
	    var theArray=new Array();
		var schemeArray = scheme.split(",");
		for(var m=0;m<schemeArray.length;m++){
	    	theArray[m]=schemeArray[m].split(":");
	    }
		
		var textvalue="";
	   	for(var m=0;m<theArray.length;m++){
			if(theArray[m][0]=='文字'){
				textvalue = theArray[m][1];//页码
	   		}
	   	}
	   	
	   	var textvalueArr = textvalue.split("_",2);
	   	if(textvalueArr[1] != null && textvalueArr[1] != ""){
	   		aipObj.setValue(textvalueArr[1],"");
	   		aipObj.setValue(textvalueArr[1], date);
		}
	   	//
   }
    
 	//数字签名保护
   function sealProtectnode(aipObj, protectnode, caseno, map){
		if(protectnode){
			// 将abc,cde,efg解析成 : <+abc-><+cde-><+efg->
			var protectnodeStr = parseProtectnodeStr(protectnode);
			//alert(protectnodeStr);
			//    "<+sjzsuggest3-><+sjzsuggest3->"
			var signatureFlag = aipObj.SetValue("SET_BINDLIST_FORSEAL", protectnodeStr);
			if(signatureFlag <=0){
				dealSignatureFailMessage(caseno, map);
			}
		}
    }
    //
   
   //数字签名保护的节点解析:
   //		将abc,cde,efg解析成 : <+abc-><+cde-><+efg->
   function parseProtectnodeStr(protectnode){
   		if(protectnode){
   			var protectnodeArr = protectnode.split(",");
   			var protectnodeStr = "";
   			for(var i=0, l = protectnodeArr.length; i<l ; i++ ){
   				protectnodeStr += '<+' + protectnodeArr[i] + '->'
   			}
   			return protectnodeStr;
   		}
   }
   
   function dealFailSealMessage(caseno){
  		var sealOrRevoke = mini.get("sealOrRevoke").getValue();
   		var sealOrRevokeName = "";
   		if(sealOrRevoke=='1'){
   			sealOrRevokeName="签章";
   		}else if(sealOrRevoke=='2'){
   			sealOrRevokeName="撤销签章";
   		}
   		
  		var message = "司法文书" + sealOrRevokeName + "失败！";
  		dealMessage(caseno, message);
  }
   
   //不符合签章规则的案件号显示
  //Map{sealable:0, signtype:signtype,  signname:signname, status:status}
  function notFitSealMessage(caseno,map){
  		var sealable = map.sealable;
  		if(sealable != 0){
  			return;
  		}
  		var message = map.signtype + map.signname + map.status;
  		dealMessage(caseno, message);
  }
   
   //signtype:signtype, signname:signname
   function dealSingleFailSealMessage(caseno, map){
   		var sealOrRevoke = mini.get("sealOrRevoke").getValue();
   		var sealOrRevokeName = "";
   		if(sealOrRevoke=='1'){
   			sealOrRevokeName="签章";
   		}else if(sealOrRevoke=='2'){
   			sealOrRevokeName="撤销签章";
   		}
   		var message = map.signtype + map.signname + sealOrRevokeName +"失败！";
  		dealMessage(caseno, message);
   }
   
   //数字签名保护
   function dealSignatureFailMessage(caseno, map){
   		var message = map.signtype + map.signname + "数字签名保护操作失败！";
  		dealMessage(caseno, message);
   }
   
   /*
   	 *	  mapList 其中：Map{year:year,casetype:casetype,caseno:caseno,
	 * 									signtype:signtype,signname:signname}
   */
   function dealSuccessMessages(caseno,mapList){
   		for(var i=0,l=mapList.length; i<l; i++){
   			var map = mapList[i];
   			dealSingleSuccessMessage(caseno,map);
   		}
   }
   
   /*
   	 *	Map{year:year,casetype:casetype,caseno:caseno,
	 * 			signtype:signtype,signname:signname}
   */
   function dealSingleSuccessMessage(caseno,map){
   		var sealOrRevoke = mini.get("sealOrRevoke").getValue();
   		var sealOrRevokeName = "";
   		if(sealOrRevoke=='1'){
   			sealOrRevokeName="签章";
   		}else if(sealOrRevoke=='2'){
   			sealOrRevokeName="撤销签章";
   		}
  		var message = map.signtype + map.signname + sealOrRevokeName + "成功！";
  		dealMessage(caseno, message);
   }
   
   function copyImg(){
   		var url = "<%=path%>/sign/getAllCrimid.json?1=1";        
        $.ajax({
           url: url,
           data: {},
           type: "post",
           async: false,
           success: function (text){
           		var mapList = mini.decode(text); 
           		for(var i=0,l=mapList.length; i<l; i++){
		   			var map = mapList[i];
		   			var crimid = map.crimid;
		   			if(crimid){
			  			var url = "<%=path%>/sign/getContent.json?1=1";
				  		$.ajax({
				             url: url,
				             data: {crimid:crimid},
				             type: "post",
				             async: false,
				             success: function (text){
				             	  var dataBeforeSeal = eval(text);
								  var aipFileStr = dataBeforeSeal;
								  var aipObj=document.getElementById("HWPostil1");
								  aipObj.Logout();
								  //大字段 格式化为 控件识别文件对象
							      aipObj.LoadFileBase64(aipFileStr);
								  var image = aipObj.GetValueEx("picrjdjimg",14, "jpg", 0, "ToFile");
								  if(image){
							  	  	  var url = "<%=path%>/sign/copyPic.json?1=1"; 
						              $.ajax({
						                 url: url,
						                 data: {image:image,crimid:crimid},
						                 type: "post",
						                 async: false,
						                 success: function (text){
						                 		aipObj.CloseDoc(0);
						                 },
						                 error: function (jqXHR, textStatus, errorThrown){
						              	 	alert("操作失败!");
						                 }
						             });
								  }else{
								      //alert("照片不存在!");
								  }
					         },
				             error: function (jqXHR, textStatus, errorThrown){
				           	 	 alert("操作失败");
				             }
				         });
			  		}else{
			  			alert("操作成功");
			  		}
		   		}
           },
           error: function (jqXHR, textStatus, errorThrown){
        	 
           }
       });
   }
   
   
 </script>
   <script src="<%=path%>/scripts/batchOperate.js" type="text/javascript"></script>
	</body>
</html>