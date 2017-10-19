<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
     <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.all.HWPostil1.JSEnv = 1;
	</script>
</head>
<body onload="myonload()">
<input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
 <input name="mdate" id="mdate" class="mini-hidden" />
  <input name="mtype" id="mtype" class="mini-hidden" />
  <input name="mxuhao" id="mxuhao" class="mini-hidden" />
  <input name="mkey" id="mkey" class="mini-hidden" />
  <input name="mcriminaltype" id="mcriminaltype"  class="mini-hidden" />
<input id="mydata" value="<s:property value='#request.mydata'/>" type="hidden"/>
<input id="aipstr" value="<s:property value='#request.aipstr'/>" type="hidden"/>
<input type="hidden" name="reportDate123" id="reportDate123" value=''/>
<div style="width:100%;height:100%;">
    <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">    
          <a class="mini-button" id="saved"  iconCls="icon-undo" plain="true" onclick="fanhui()">返回</a>        
    	  <span class="separator"></span>
    	  <a class="mini-button" id="saved" iconCls="icon-ok" plain="true" onclick="save('submit')">提交</a> 
    	  <span class="separator"></span>
    	  <a class="mini-button" id="saved" iconCls="icon-user" plain="true" onclick="sealLogin()">登录</a>
    	  <span class="separator"></span>
    	  <a class="mini-button" id="saved" iconCls="icon-save" plain="true" onclick="save('save')">存盘</a> 
    </div>
  	<div class="mini-fit" style="width:100%; height:100%;">
       <div style="width:100%; height:100%;">
            <!--<script src="<%=path%>/scripts/loadaip.js"></script>-->
             <script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
       </div>
    </div>  
</div>
<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
<script type="text/javascript">
	document.all("MyViewer").Init(window, document,600);
</script>
    <script type="text/javascript">
    	var object = document.getElementById("HWPostil1");
    	object.ShowToolBar = 1;//工具栏是否显示
    	object.ShowDefMenu = 0;//菜单栏
    	object.ShowScrollBarButton = 1;
       	mini.parse();
          //标准方法接口定义
        function SetData(data) {
             //跨页面传递的数据对象，克隆后才可以安全使用
             data = mini.clone(data);
             //mini.get("mdate").setValue(data.mdate);
             //mini.get("mcriminaltype").setValue(data.mcriminaltype);
              //mini.get("mxuhao").setValue(data.mxuhao);
             mini.get("mtype").setValue(data.mtype);
             mini.get("mkey").setValue(data.mkey);
        }
        function myonload(){
        	var aipstr = document.getElementById("aipstr").value;
        	var reportdate = document.getElementById("mydata").value;
        	if(aipstr){
        		document.getElementById("HWPostil1").LoadFileBase64(reportdate);
			}else{
				var aipObj=document.getElementById("HWPostil1");	
				document.all("MyViewer").ShowProgress = false;
				document.all.HWPostil1.BeforeConvert("");
				document.all("RMVIEWER_DATA").value=reportdate;
				document.all("MyViewer").GetReportData(reportdate);
				document.all("MyViewer").ShowPrintDialog = false;
				document.all("MyViewer").PrintReport();
				document.all.HWPostil1.WaitConverting(4000);
				document.all.HWPostil1.AfterConvert();
			}
		}				
      	function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        } 
      	function fanhui() {
      		CloseWindow("cancel");
      	}
      	function save(type){
      		//var mdate = mini.get("mdate").getValue();
      		//var mcriminaltype = mini.get("mcriminaltype").getValue();
      		//var mxuhao = mini.get("mxuhao").getValue();
      		
      		var mtype = mini.get("mtype").getValue();
      		var mkey = mini.get("mkey").getValue();
      		//var mydata = document.getElementById("mydata").value;
      		var mydata=saveFile();
      		if(type=="submit"){
	      		if(confirm("提交之后不能修改！确认提交？")){
		      		$.ajax({
		                url: "meetingSaveData.action?1=1&qtype=bigtext",
		                data: {mkey : mkey,mtype : 'temp', data: mydata, mymenuid : 'temp'},
		                type:"POST",
		                cache: false,
		                success: function (text) {
		                	if("error" == text){
		                		alert("操作失败！");
		                	}else if("have" == text){
		                		alert("已经提交过了，请务重复提交！");
		                	}else{
		                		alert("保存成功！");
		                	}
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                  alert("操作失败！");
		                }
		            });
	            }
            }else{
            	$.ajax({
	                url: "meetingSaveData.action?1=1&qtype=bigtext&mflag=0",
	                data: {mkey : mkey,mtype : 'temp', data: mydata, mymenuid : 'temp'},
	                type:"POST",
	                cache: false,
	                success: function (text) {
	                		if("error" == text){
		                		alert("操作失败！");
		                	}else if("have" == text){
		                		alert("已经提交过了，请务重复提交！");
		                	}else{
		                		alert("保存成功！");
		                	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                  alert("操作失败！");
	                }
	            });
            }
      	}
      	
      	function sealLogin(){
      		var aipObj = document.all.HWPostil1;
      		if(aipLogin(aipObj)==0) {
      			if(!aipObj.IsLogin()){
      				aipLogin(aipObj);
      			}
  			}else if(aipLogin(aipObj)==-200){
      			alert("电子签章未连接！");
  			}	
      	}
    </script>
 </body>
</html>