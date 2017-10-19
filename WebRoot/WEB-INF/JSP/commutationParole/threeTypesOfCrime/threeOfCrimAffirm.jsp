<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>三类犯认定表单页面</title>
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
	    	<a class="mini-button" style="display:none;" id="11904" iconCls="icon-save" plain="true" onclick="doSubmitFlow('11904','save','other_slzfrdsp','SLFRDSPB');">存盘</a>
	    	<a class="mini-button" style="display:none;" id="11905" iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('11905','yes','other_slzfrdsp','SLFRDSPB');">提交</a>
	    	<a class="mini-button" style="display:none;" id="11906" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('11906','yes','text2');">同意</a>
	    	<a class="mini-button" style="display:none;" id="11964" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('11964','yes','text3');">同意</a>
	    	<a class="mini-button" style="display:none;" id="11965" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('11965','yes','text5');">同意</a>
	    	<a class="mini-button" style="display:none;" id="11966" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('11966','yes','text7');">同意</a>
	    	<a class="mini-button" style="display:none;" id="11907" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('11907','no','text2');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="11967" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('11967','no','text3');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="11968" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('11968','no','text5');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="11969" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('11969','no','text7');">拒绝</a>
	    	<a class="mini-button" style="display:none;" id="11961" iconCls="icon-undo" plain="true" onclick="operationOpprove('11961','back','');">退回</a>
	    	<a class="mini-button" style="display:none;" id="11962" iconCls="icon-undo" plain="true" onclick="operationOpprove('11962','back','');">退回</a>
	    	<a class="mini-button" style="display:none;" id="11963" iconCls="icon-undo" plain="true" onclick="operationOpprove('11963','back','');">退回</a>
	    	<a class="mini-button" style="display:none;" id="11908" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
	    	<a class="mini-button" id="back"  iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
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
        var flowdefid=mini.get("flowdefid").getValue();
        //手动下一个
        function saveNext (){
        	var id = mini.get("id").getValue();
            var index = id.indexOf(",");
            if(index>0){
            	 id = id.substring(index+1,id.length);
                 var url= "<%=path%>/threeOfCrimAffirm/toThreeOfCrimAffirmPage.page?1=1&menuid=${menuid}&id="+id+"&flowdefid="+flowdefid;
                self.location.href=url;
            }else{
            	//alert("批量处理已完成！");
            	onCanle();
            }
        }
        //手动关闭
        function onCanle(){
            url ="<%=path%>/threeOfCrimAffirm/toThreeOfCrimAffirmChoose.page?1=1&flowdefid="+flowdefid;
        	self.location.href=url;
        }
    </script>
</body>
</html>
