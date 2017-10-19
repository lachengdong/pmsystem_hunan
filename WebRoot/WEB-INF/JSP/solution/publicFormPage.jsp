<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body  onload="init('${menuid}');">
	<input id="params" name="params" class="mini-hidden" value='${params}'/>
	<input id="solutionid" name="solutionid" class="mini-hidden" value="${solutionid}"/>
	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
	<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData();">存盘</a>
	    	<a class="mini-button" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
	    	<c:forEach var="button" items="${topButtonList}">
				<a class="mini-button" iconCls="${button.showico}" plain="true" onclick="${button.srurl}">${button.name}</a>
			</c:forEach>
	    	<jsp:include page="/WEB-INF/JSP/common/form/sealmenus.jsp"></jsp:include>
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
        //保存或更新
        function saveData(){
        	var params = mini.get("params").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
       		var formclob =saveFile();
       		var noteinfo = getNoteMap();
       		var url = "<%=path%>/solution/saveFormData.action?1=1"
       		$.ajax({
	               url:url,
	               type: "post",
	               data: {params:params, solutionid:solutionid, tempid:tempid, flowdefid:flowdefid, formclob:formclob, noteinfo:noteinfo},
	               success: function (text){
	               		if(text > 0){
	               			alert("操作成功!");
	               		}else{
	               			alert("操作失败!");
	               		}
	                 	close();
	               },
	               error: function (jqXHR, textStatus, errorThrown){
	               		alert("操作失败!");
	               }
            });
        }
        
		//标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            
        }
        
        function GetData(){    
	        //var sql = mini.get("sql").getValue();
	        var data = {};
	        //data.sql = sql;
	        return data;
	    }
	    
        function saveNext(){
        	alert("下一个");
        }
        function close(){
       		CloseWindow("close");
        }
        //关闭窗口
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
		function onCancel(){
            CloseWindow("cancel");
        }
       
    </script>
</body>
</html>