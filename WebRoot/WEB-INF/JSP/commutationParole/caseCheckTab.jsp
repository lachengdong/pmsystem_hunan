<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>案件检查</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    </style>
</head>
<body>
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1"  title="案件检查" url= "caseCheckPage.action?1=1&tempid=${tempid}&id=${id}">
			</div>
			<!-- 
			<div  id="tab3"  name="2"  title="建议书" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowid=${flowid}" refreshOnClick="true" visible="true">
			</div> 
			-->
			<div  id="tab5"  name="5"   title="监督意见" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&resid=${resid}&flowid=${flowid}&doctype=casecheck" refreshOnClick="true" visible="true">
			</div>
		</div>
		<script type="text/javascript">
        mini.parse();
        top["win"]=window;
        //标准方法接口定义
        function SetData(data) {

		}
        function CloseWindow(action) { 
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        } 
      	function fanhui() {
      		CloseWindow("close");
      	}
        </script>
</body>
</HTML>