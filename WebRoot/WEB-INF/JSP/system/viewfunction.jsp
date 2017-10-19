<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查看功能说明（页面帮助按钮）</title>
      <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body>
	<div style="height: 100%;">
		<input class="mini-hidden" id="menuid" value="${menuid}"/>
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
    <script type="text/javascript">
           mini.parse();
	       function CloseWindow(action) {            
	           if (action == "close" && form.isChanged()) {
	               if (confirm("数据被修改了，是否先保存？")) {
	                   return false;
	               }
	           }
	           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	           else window.close();            
	       }
	       function onCancel() {
	           CloseWindow("cancel");
	       }

		//标准方法接口定义
        function SetData(data) {
        }
    </script>
</body>
</html>
