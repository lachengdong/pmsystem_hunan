<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>提交月报表</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
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
<body onload="init('${menuid}');myonload();">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
			<input id="mydata" value="${mydata}" type="hidden"/>
			<input id="tempid" value="${tempid}" type="hidden"/>
			<input id="flowdraftid" value="${flowdraftid}" type="text"/>
			<input id="flowid" value="${flowid}" type="hidden"/>
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			${topstr}
	    	<a class="mini-button" id="" iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	鲁宁监狱第一监区一分区罪犯计分考核月报表
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
  <textarea id="RMVIEWER_DATA" style="display:none"></textarea>
  <script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
  <script type="text/javascript">document.all("MyViewer").Init(window, document,600);</script>
    <script type="text/javascript">
	var object = document.getElementById("HWPostil1");
	object.ShowToolBar = 0;//工具栏是否显示
	object.ShowDefMenu = 0;//菜单栏
	object.ShowScrollBarButton = 1;
    mini.parse();
    var grid = mini.get("datagrid1");
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
	}		
    //手动关闭
    function onCanle(){
    	url = "<%=path%>/check/monthcheck.page?1=1";
    	self.location.href=url;
    }
    </script>
</body>
</html>
