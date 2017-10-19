<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
Object mydata = request.getAttribute("mydata"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>月考核考勤</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
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
<body onload="myonload();">
	<input id="mydata" value="${mydata}" class="mini-hidden"/>
	<input id="menuid" value="${menuid}" class="mini-hidden"/>
	
	<div style="width:100%;height:100%;">
	    <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;"> 
	    	<table>
				<tr>
				   <td style="width:100%;">
		    		 <input  id="statdate" name="statdate" class="mini-textbox"  validateOnLeave vtype="maxLength:7;minLength:7;" value="${ecwDate }" onenter="onKeyEnter"   emptyText="统计日期"/>
			 		  <a class="mini-button"  iconCls="icon-user" plain="true" onclick="statt();">统计</a>
			 		  <a class="mini-button" id="plqz" iconCls="icon-edit" plain="true" style="width:60px;" onclick="doSealFunction()">盖章</a>        
			    	   <span class="separator"></span>        
			    	  <a class="mini-button" id="12410"  iconCls="icon-downgrade" plain="true" onclick="submit();">提交</a> 
			    	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    	  填表日期：<input  id="ecwDate" class="mini-textbox"  validateOnLeave vtype="maxLength:7;" value="${ecwDate }" enabled="false" emptyText="填表日期"/>
			    	  描述：<input id="cfdescribe" class="mini-textbox" style="width:260px;"   value="${cfdescribe }" emptyText=""/>
			    	  <a class="mini-button" id="saved" iconCls="icon-user" plain="true" onclick="generate();">生成月报表</a> 
	    	      </td>
	    	</tr>
	    	</table>
	    </div>
  		<div class="mini-fit" style="width:100%; height:100%;">
       		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
    	</div>  
	</div>
	<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
	<textarea id="RMVIEWER_DATA" style="display:none">${mydata}</textarea>
	<script LANGUAGE="javascript" >
		 document.all("MyViewer").Init(window, document,600);
	</script>
    <script type="text/javascript">
    	var object = document.getElementById("HWPostil1");
		object.ShowToolBar = 1;//工具栏是否显示
		object.ShowDefMenu = 1;//菜单栏
       	mini.parse();
       	var flag = true;
        function dakai(){
       		var da = mini.get("dakai");
       		object.HideMenuItem(16384);
        	if(flag){
        		da.setIconCls("icon-unlock");
        		object.ShowToolBar = 1;//工具栏是否显示
        		flag = false;
        	}else{
        		da.setIconCls("icon-lock");
        		object.ShowToolBar = 0;//工具栏是否显示
        		flag = true;
        	}
        }
        function myonload(){
        	var reportdate = $("#mydata").val();//+data1;
        	//alert(reportdate);
			document.all("MyViewer").ShowProgress = false;
			document.all.HWPostil1.BeforeConvert("");
			document.all("RMVIEWER_DATA").value=reportdate;
			document.all("MyViewer").GetReportData(reportdate);
			document.all("MyViewer").ShowPrintDialog = false;
			document.all("MyViewer").PrintReport();
			document.all.HWPostil1.WaitConverting(4000);
			document.all.HWPostil1.AfterConvert();
		 	var menuid = document.getElementById("menuid").value;
		}				
      	function CloseWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        } 
      	function close() {
      		var menuid = mini.get("menuid").getValue();
      		if(menuid=='13743'){
      			window.parent.close();
      		}else{
      			CloseWindow("cancel");
      		}
      	}
      	function save(type){
      	    var otherid = $("#otherid").val();
      	    var tempid = $("#tempid").val();
      	    var flowid = $("#flowid").val();
      	    var flowdraftid = $("#flowdraftid").val();
      	    var curyear = $("#curyear").val();
      	    var batch = $("#batch").val();
      	    var status = "bigtext";
      	    var doctype = $("#doctype").val();
      	    var count = 0;
      	    var bworjx = $("#bworjx").val();
      	    var reportname = $("#reportname").val();
      	    var menuid = $("#menuid").val();
            //alert(otherid+tempid+flowid+flowdraftid+curyear+batch+status+doctype+bworjx);return;
           	var mydata=saveFile();
            	$.ajax({
	                url: "saveSuggestion.action",
	                data: {bigdata: mydata, tempid:tempid, flowdraftid:flowdraftid, flowid:flowid},
	                type:"POST",
	                cache: false,
	                success: function (text) {
	                	var result = eval(text);
	                	if(result=='1'){
	                		alert("保存成功！");
	                	}else{
	                		alert("操作失败！");
	                	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                      alert("操作失败！");
	                }
	            });
	            
      	}
      	function submit(){
      	var document=saveFile();
      	if(document){
      		doSubmitFlow('12410','yes','other_ykhkqsp','report');
      	}else{
      		alert("请先生成统计报表！");
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
      	
      	//当点击统计报表按钮
      	function statt(){
      		var menuid = $("#menuid").val();
     		var statdate = mini.get("statdate").getValue();
     		var cfdescribe=mini.get("cfdescribe").getValue();
     		document.getElementById("cfdescribe").focus();
     		var URL= "generateMonthReport.page?1=1&&cfdescribe="+cfdescribe+"&statdate="+statdate+"&menuid="+menuid;
	      	window.location.href=URL;
      	}
      	
      	function generate(){
      		var mydaa = document.getElementById('mydata').value
      		//if(mydaa){
			//	var aipObj=document.getElementById("HWPostil1");
			//	aipObj.CloseDoc(0);
			//	aipObj.LoadFileBase64(mydaa);
			//	aipObj.PrintDoc(1,1); //打印当前打开文档,打开打印对话框
			//	}
			
			if(mydaa){
				document.all.HWPostil1.DeleteLocalFile("C:\\SignatureTmp\\SignatureTmp.tmp");
				var aipObj=document.getElementById("HWPostil1");	
				ifLoadFileOK = document.all("HWPostil1").LoadFile("C:\\SignatureTmp\\SignatureTmp.tmp");
				var vOutTime = 1;
				while(ifLoadFileOK)
				{
					document.all("HWPostil1").SleepSecond(1);
					ifLoadFileOK = document.all("HWPostil1").LoadFile("C:\\SignatureTmp\\SignatureTmp.tmp");
					vOutTime ++;
					if(vOutTime > 5)
					{
						alert("删除临时文件时出错！");
						break;
					}
				}
				//var cc=aipObj.BeforeConvert("A3");
				document.all("MyViewer").ShowProgress = false;
				reportdate=document.getElementById('mydata').value;
				document.all("RMVIEWER_DATA").value=reportdate;
				document.all("MyViewer").GetReportData(reportdate);
				document.all("MyViewer").PrinterName = "HWSealPrinter";
				document.all("MyViewer").ShowPrintDialog = true;
				document.all("MyViewer").PrintReport();
				//aipObj.WaitConverting(10000)
				//aipObj.AfterConvert();
				//document.all("HWPostil1").LoadFile("C:\\SignatureTmp\\SignatureTmp.tmp");
				ifLoadFileOK = document.all("HWPostil1").LoadFile("C:\\SignatureTmp\\SignatureTmp.tmp");
				vOutTime = 1;
				while(!ifLoadFileOK)
				{
					document.all("HWPostil1").SleepSecond(1);
					ifLoadFileOK = document.all("HWPostil1").LoadFile("C:\\SignatureTmp\\SignatureTmp.tmp");
					vOutTime ++;
					if(vOutTime > 10)
					{
						//alert("形成签章文件时出错！");
						break;
					}
				}
			}
		}
		
		//盖章 
      	function doSealFunction(){
	          doMenueButton(5);
        }
    </script>
 </body>
</html>