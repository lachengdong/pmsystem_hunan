<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>批量打印1</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   	<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
</head>
<body >
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
		<div size="20px;" showCollapseButton="false">
	        <div class="mini-toolbar" style="padding:2px;border-bottom:0;">
	           <input class="mini-hidden" id="notExistClobCaseNos" name="notExistClobCaseNos" value=""/>
			   <input class="mini-hidden" id="notFitOrgCaseNos" name="notFitOrgCaseNos" value=""/>
			   <input class="mini-hidden" id="notThisLevelCaseNos" name="notThisLevelCaseNos" value=""/>
			   <input class="mini-hidden" id="fitCaseNos" name="fitCaseNos" value=""/>
			   <input class="mini-hidden" id="sealResult" name="sealResult" value=""/>	<!-- 打印结果 -->
			   
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
						<option value="${casetype.codeid}">${casetype.name}</option>
		       		</c:forEach>
				</select>
				第<input class="mini-textbox" style="width: 160px;" emptyText="案件号：1,2或3-5或1,2,3-5" name="casenos" id="casenos" vtype="maxLength:100" />号  
	             
	            <select id="signid" name="signid" style="width: 200px">
					<option></option>
					<c:if test="${not empty signatureSchemes}">
						<c:forEach items="${signatureSchemes}" var="signatureScheme" >
							<option value="${signatureScheme.signid}">${signatureScheme.name}</option>
						</c:forEach>
					</c:if>
				</select>
				&nbsp;
	            <!--  
				<a class="mini-button"  iconCls="icon-new" plain="true" onclick="dosubmit('merge');">文书合并</a>  
				<span class="separator"></span>   
				<select name="daochugeshi" id="daochugeshi">
				    <option value="doc">word</option>
				    <option value="pdf">pdf</option>
				</select>
				<a class="mini-button" iconCls="icon-addfolder" plain="true" onclick="dosubmit('daochu');">导出</a>
				
				<span class="separator"></span> 
				-->
				
				<a class="mini-button"  iconCls="icon-print" plain="true" onclick="batchPrint();">批量打印</a>
				<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10129')"></a>
	        </div>
        </div>
		<div showCollapseButton="false">
			<div id="showContent" style="height: 100%; overflow: auto; display: none; background: #ababab; color: #000000; text-align: left;"></div>
			<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
		</div>
	</div>  
    <script type="text/javascript">
    	 var object = document.getElementById("HWPostil1");
    	 object.ShowToolBar = 0;//工具栏是否显示
    	 object.ShowDefMenu = 0;//菜单栏
    	 object.ShowScrollBarButton = 1;
         mini.parse();
         
         var path = '<%=path%>';
         
     //var orgShortname = mini.get('orgShortname').getValue();
     var showContent=document.getElementById('showContent');
	 function batchPrint(){
		var flag = initConditionBeforeOperate('print');
  		if(false == flag){
  			return flag;
  		}
		doPrintData();
	}
	
	 function doPrintData(){
	  		var caseno = getNextCaseno();
	  		if(caseno){
	  			//递归的进行打印
	  			printData(caseno);
	  		}else{
	  			//操作结束处理：将操作的结果显示给用户
	  			//dealSealEnd();
	  			showContent.innerHTML = "打印结束！";
	  		}
	  }
	  
	  //递归的进行打印
	  function printData(caseno){
	  		//获取打印数据，并打印
	  		getPrintDataAndprint(caseno);
	  		//递归
	  		doPrintData();
	  }
	  
	  //获取某个案件号的打印数据（可能存在多个大字段），并打印
	  function getPrintDataAndprint(caseno){
			var signid = $("#signid").val();
	  		var casetype = $("#casetype").val();
	  		var year = $("#year").val();
	  		var url = "<%=path%>/sign/getSealData.json?1=1";
	  		$.ajax({
	             url: url,
	             data: {signid:signid, casetype:casetype, year:year, caseno:caseno, type:'print'},
	             type: "post",
	             async: false,
	             success: function (text){
	             	//做签章并保存
	             	var printData = mini.decode(text);
	             	print(caseno, printData)
	             },
	             error: function (jqXHR, textStatus, errorThrown){
	
	             }
	         });
	  	}
	  	
	  	function print(caseno, printData){
	   		var aipObj = document.getElementById("HWPostil1");
			
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
			
	   		for(var i=0,l=printData.length; i<l; i++){
				var map = printData[i];
				perPrintDoc(aipObj, map.aipFileStr, map.ismultipage);
			}
			
			printPrompt(caseno);
		}
		
		function perPrintDoc(aipObj, obj){ //打印
			aipObj.CloseDoc(0);
			aipObj.JSEnv = 1;
			if(obj){
				//多页显示 例如 合并查看
				for(var j=0;j<obj.length;j++){
					aipObj.MergeFile(99999,'STRDATA:'+obj[j]);
				}
				
				//var aipFileStr = aipObj.GetCurrFileBase64();
				//alert(aipFileStr);
				var num = 1;//打印份数
				aipObj.PrintDocEx('',1,0,0,0,9999,0,num,1,0,0);
			}
		}
		
		function printPrompt(caseno){
			var year = mini.get("year").getValue();
			var casetypetext= $("#casetype").find("option:selected").text();
			var orgShortname = mini.get('orgShortname').getValue();
			
			showContent.innerHTML="正在打印:(" + year + ")" + orgShortname + casetypetext + "第" + caseno + "号";
			setInterval('',1000);
	        showContent.style.display = "";
		}
	
    </script>
    <script src="<%=path%>/scripts/batchOperate.js" type="text/javascript"></script>
 </body>
</html>