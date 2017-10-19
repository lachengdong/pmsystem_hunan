<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String formcontent = (String) request.getAttribute("formcontent");
	String formDatajson = (String)request.getAttribute("formDatajson");
	String selectDatajson = (String)request.getAttribute("selectDatajson");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title></title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
 	<style>
 		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
 	</style>
  
  <body>
  	<input id="aipdata"  type="hidden" value="${aipdata}"/>
  	<input id="reportdata" type="hidden" value="${reportdata}"/>
	<!-- 
  	<div class="mini-toolbar" >
    	<a class="mini-button" id="201" iconCls="icon-save" plain="true" onclick="save();">存盘</a>
    	<span class="separator"></span> 
    	<a class="mini-button" style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12457" iconCls="icon-gk_user" tooltip="用户" plain="true" onclick="doMenueButton(11)"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="盖章" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	  	<a class="mini-button"  id="212" iconCls="icon-gk_plgz" tooltip="多页盖章" plain="true" onclick="doMenueButton(14);"></a>&nbsp;
	  
    	<a class="mini-button" style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="盖章撤销" plain="true" onclick="deleteSeal();"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="手写擦除" onclick="doMenueButton(7);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12463" iconCls="icon-gk_zw" tooltip="指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12464" iconCls="icon-gk_print" tooltip="打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12466" iconCls="icon-gk_open" tooltip="打开" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12467" iconCls="icon-gk_save" tooltip="另存" plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
    	<a class="mini-button" style="display:none;" id="12468" iconCls="icon-gk_forward" tooltip="前一页" plain="true" onclick="doMenueButton(15)"></a>
    	<a class="mini-button" style="display:none;" id="12469" iconCls="icon-gk_next" tooltip="后一页" plain="true" onclick="doMenueButton(16)"></a>
  	</div>
	-->
  	<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
	<script src="<%=path%>/scripts/form/loadaip.js"></script>
	
	<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
	<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
		var aipObj = document.all.HWPostil1;
		document.getElementById("HWPostil1").JSEnv=1;
		aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		HWPostil1_onload();
		loadBigData('<%=formcontent%>','<%=formDatajson%>', '<%=selectDatajson%>');
	</script>
  	
  	<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
	<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
	<script type="text/javascript">
		document.all("MyViewer").Init(window, document,600);
	</script>
	
  </body>
</html>
