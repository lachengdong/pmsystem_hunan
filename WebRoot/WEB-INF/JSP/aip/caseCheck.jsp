<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>检察监督办案页面</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body onload="init('${menuid}')">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<input id="id" name="id" class="mini-hidden" value="${id}"/>
			<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
			<input id="fathermenuid" name="fathermenuid" class="mini-hidden" value="${fathermenuid}"/>
			<input id="applyid" name="applyid" type="hidden" value="${applyid}"/>
			<input id="applyname" name="applyname" type="hidden"  value="${applyname}"/>
			<input id="orgid" name="orgid" type="hidden"  value="${orgid}"/>
			<input id="lastnodeid" name="lastnodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="flowdraftid" name="flowdraftid" type="hidden" value="${flowdraftid}"/>
			<input id="commenttext" name="commenttext" type="hidden"  />
			<!-- 经办人办案  -->
			<!-- 
			<a class="mini-button"  id="" iconCls="icon-node" plain="true" onclick="operationOpprove">监督意见</a>		 
			 -->
			<a class="mini-button"  id="11426"  iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
	    	<a class="mini-button"  id="" iconCls="icon-close" plain="true" onclick="Close();">关闭</a>
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
        function backChoose(){
        	history.back();
        }

        function saveNext (){
        	var id = mini.get("id").getValue();
        	if(id==null||id==''){
				close();
				return;
            }
        	var tempid = mini.get("tempid").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var url= "caseCheckPage.action?1=1&tempid="+tempid+"&id="+id;
            if(index>0){
                self.location.href=url;
            }else{
            	close();
            }
        }
        function Close(){
        	url = "toCaseCheckPage.action?1=1";
        	window.parent.location.href=url;
        }
        function close(){
        	Close();
        }
        
	    function operationOpprove(){
        	var tempid = mini.get("tempid").getValue();
			var id = mini.get("id").getValue();
			var selecturl = "toOpinionPage.action?1=1&tempid=" +tempid + "&id="+id;
			window.showModalDialog(selecturl,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:260px;dialogWidth:500px");
		}        
    </script>
</body>
</html>
