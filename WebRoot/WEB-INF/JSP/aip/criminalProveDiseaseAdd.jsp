<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>老病残认定审批</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body onload="init('${menuid}');">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
	    	<a class="mini-button" style="display:none;" id="11537" iconCls="icon-save" plain="true" onclick="doSubmitFlow('11537','save','other_zflbcrdsp','XFZX_LNFSPB');">存盘</a>
	    	<a class="mini-button" style="display:none;" id="11538" iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('10939','yes','other_zflbcrdsp','XFZX_LNFSPB');">提交</a>
	    	<a class="mini-button" style="display:none;" id="11397" iconCls="icon-save" plain="true" onclick="doSubmitFlow('11397','save','other_zflbcrdsp','XFZX_LNFSPB');">存盘</a>
	    	<a class="mini-button" style="display:none;" id="10939" iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('10939','yes','other_zflbcrdsp','XFZX_LNFSPB');">提交</a>
	    	<a class="mini-button" style="display:none;" id="10941" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('10941','yes','cdireason');">同意</a>
	    	<a class="mini-button" style="display:none;" id="10942" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('10942','no','cdireason');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="10943" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('10943','yes');">同意</a>
	    	<a class="mini-button" style="display:none;" id="'10944'" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('10944','no');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="'10945'" iconCls="icon-upgrade" plain="true" onclick="doSubmitFlow('10945','no','other_zflbcrdsp','XFZX_LNFSPB');">退回</a>
	    	<a class="mini-button" style="display:none;" id="10946" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('10946','yes');">同意</a>
	    	<a class="mini-button" style="display:none;" id="'10947'" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('10947','no');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="'10948'" iconCls="icon-upgrade" plain="true" onclick="doSubmitFlow('10948','no','other_zflbcrdsp','XFZX_LNFSPB');">退回</a>
	    	<a class="mini-button" style="display:none;" id="10943" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('10949','yes','text102');">同意</a>
	    	<a class="mini-button" style="display:none;" id="10950" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('10950','no','text102');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="'10948'" iconCls="icon-upgrade" plain="true" onclick="doSubmitFlow('10948','no','other_zflbcrdsp','XFZX_LNFSPB');">退回</a>
	    	<a class="mini-button"  iconCls="icon-close" plain="true" onclick="onCancel();">关闭</a>
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
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
	
	
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        //标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            mini.get("operator").setValue(data.action); 
        }
        function saveNext(){
          onCancel();
        }
       //关闭窗口
       function CloseWindow(action) { 
           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
           else window.close();            
       }
       function onCancel() {
           CloseWindow("cancel");
       }
    </script>
</body>
</html>
