<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<title>退案修改平台</title>
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
	</head>
	<body>
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"
			style="width: 100%; height: 100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1" title="案件审批"
				url="tuian_sh.action?1=1&tempid=${tempid}&menuid=${menuid}&id=${id}&fathermenuid=${fathermenuid}&closetype=${closetype}&flowdefid=${flowdefid}&provinceid=${provinceid}&nodeid=${nodeid}&days=${days}&ischeckseal=${ischeckseal}">
			</div>
			<div id="tab2" name="2" title="建议书"
				url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&doctype=jailcommute&resid=12498&menuid=${menuid}"
				refreshOnClick="true" visible="true">
			</div>
		</div>
		<script type="text/javascript">
mini.parse();
top["win"] = window;
function SetData(data) {
}
function CloseWindow(action) {
	if (window.CloseOwnerWindow)
		return window.CloseOwnerWindow(action);
	else
		window.close();
}
function fanhui() {
	CloseWindow("close");
}
</script>
	</body>
</HTML>