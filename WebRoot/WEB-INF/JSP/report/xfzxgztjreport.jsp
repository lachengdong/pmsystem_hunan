<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Object mydata = request.getAttribute("mydata"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>刑罚执行统计系统--统计报表</title>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"/>
    <script src="<%=path%>/scripts/jquery.messager.js" type="text/javascript"></script> 
    <link href="<%=path%>/css/top.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
	body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    #time {
        position: absolute;
        right: 1px;
        top: 2px;
        color: blue;
    } 
	</style>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.all.HWPostil1.JSEnv = 1;
	</script>
</head>
<body onload="myonload();">
	<input id="mydata" value='${mydata}' class="mini-hidden"/>
	<div style="width:100%;height:100%;">
	     <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;"> 
	    	<table>
				<tr>
				<td style="width:100%;">
	    		<a class="mini-button"  id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
				<a class="mini-button"  id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
				<span class="separator"></span>
	            <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
		    	<span class="separator"></span> 
		    	<a class="mini-button"  id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
		    	<a class="mini-button"  id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
		    	<a class="mini-button"  id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
		    	<span class="separator"></span> 
		    	<a class="mini-button"  id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
		    	<a class="mini-button"  id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
		    	<a class="mini-button"  id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
		        <span class="separator"></span> 
		    	<a class="mini-button"  id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
		    	<span class="separator"></span> 
		    	<a class="mini-button"  id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
		    	<a class="mini-button"  id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
		    	<a class="mini-button"  id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
		    	<a class="mini-button"  id="14007"  iconCls="icon-gk_search" tooltip="检索" plain="true" onclick="doMenueButton(17);"></a>&nbsp;
				</td>
	    		<td style="white-space:nowrap;">	
					输入年月：<input id="nianyue" class="mini-textbox" emptyText="例如：201503" onenter="onKeyEnter"/>	
					<input id="orgid" class="mini-hidden" value="${orgid}"/>
					<input id="year" class="mini-hidden" value="${year}"/>
					<input id="month" class="mini-hidden" value="${month}"/>
					<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
				</td>
	    	</tr>
	    	</table>
	    </div>
  		<div class="mini-fit" style="width:100%; height:100%;">
       		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
    	</div>  
	</div>
	<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
	
	<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
	<script LANGUAGE="javascript" >
		document.all("MyViewer").Init(window, document,600);
	</script>
    <script type="text/javascript">
    	var object = document.getElementById("HWPostil1");
		object.ShowToolBar = 0;//工具栏是否显示
		object.ShowDefMenu = 0;//菜单栏
		object.ShowScrollBarButton = 1;
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
        	var carr = new Array();
        	var orgid = mini.get("orgid").getValue();
        	var year = mini.get("year").getValue();
        	var month = mini.get("month").getValue();
        	var aipObj = document.all.HWPostil1;
			$.ajax({
				type:'post',
				url:'getReportDataJson.action?1=1&menuid=${menuid}',
				data:{orgid:orgid,year:year,month:month},
				success:function(text){
					var objs = mini.decode(text);
					for(var j=0;j<objs.length;j++){
						var reportstr = objs[j].reportstr; 
						document.all("MyViewer").ShowProgress = false;
						document.all.HWPostil1.BeforeConvert("");
						document.all("RMVIEWER_DATA").value=reportstr;
						document.all("MyViewer").GetReportData(reportstr);
						document.all("MyViewer").ShowPrintDialog = false;
						document.all("MyViewer").PrintReport();
						document.all.HWPostil1.WaitConverting(4000);
						document.all.HWPostil1.AfterConvert();
						carr[j] = aipObj.GetCurrFileBase64();
					}
					aipObj.CloseDoc(0);
					aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
					for(var i=0;i<carr.length;i++){ 
						aipObj.MergeFile(99999,'STRDATA:'+carr[i]);
					}
				}
			});
		}			
      	
        function onCanle() {
        	var url = "<%=path%>/statisticsOfPenaltyExecution.page?1=1&menuid=${menuid}";
			self.location.href=url;
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
      	//快速查询
        function onKeyEnter(e) {
            search();
        }
		function search(){
			var str = mini.get("nianyue").getValue();
			var orgid = mini.get("orgid").getValue();
			if(!str||str==''){
				alert("年月不能为空！");
				return;
				//str.length!=6&&str.length!=4
			}else if(str.length!=6&&str.length!=4){
				alert("年月输入格式不正确！");
				return;
			}
			var year = str.substring(0,4); 
			var month = str.substring(4,6);
			var url = "<%=path%>/theReportPageJump.page?1=1&menuid=${menuid}&year="+year+"&month="+month+"&orgid="+orgid;
			self.location.href = url;
		}
    </script>
 </body>
</html>
	