<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>省局综合管理平台</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div  id="tab1"  name="1"  title="监狱审批表" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdefid}&tempid=JXJS_JXJSSHB&docid=003001"   visible="true">
			</div>
			<div  id="tab2"  name="8"  title="退案函" url="toTuiAnHanDocumentPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&menuid=${menuid}" refreshOnClick="true"  visible="true">
			</div>
		</div>
		<script type="text/javascript">
        	mini.parse();
         	//标准方法接口定义
	        function SetData(data) {
	        	 
	        }
        </script>
</body>
</html>