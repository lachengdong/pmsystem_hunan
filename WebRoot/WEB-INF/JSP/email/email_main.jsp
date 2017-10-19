<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人邮箱</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/mail.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.sidebar_logo {
	height: 100px;
	line-height: 100px;
	font-size: 72px;
	text-align: center;
	color: #dd4b39;
	align: left;
	padding-top: 10px;
}


label {
	font-weight: normal;
}
</style>

</head>
<body>
	<div class="mini-layout" style="width:100%;height:100%;padding:0px;" id="layout1" >
		<div width="196px" height="100%" region="west" showSplit="false"
			showHeader="false" showSplitIcon="true" showCollapseButton="true"
			bodyStyle="border:0px;overflow: hidden;width:195px;BACKGROUND: url(${path}/images/email/bgg.png) #fff repeat-y;text-align:center;">
			<div style="padding-right:2px;">
				<span class="icon-mail sidebar_logo T_icon" style=""></span>
			</div>
			<div style="margin:5px 0;text-align:center;" class="btn-group">
				<Button style="cursor:pointer;padding:4px 20px;"
					id="receive-webmail" class="btn btn-success"
					onclick="javascript:gotoinbox();">
					<i class="icon-download-2"></i> 收信
				</Button>
				<Button style="cursor:pointer;padding:4px 20px;"
					class="btn btn-success" id="yw0"
					onclick="javascript:gotosys_write();">
					<i class="icon-pencil-5"></i> 写信
				</Button>
			</div>
			<div style="BORDER-BOTTOM: #b3b3b3 1px solid;">
				<div title="收件箱" class="mailbtn"
					onclick="gotosys_mailBox('tbReceives','收件箱',0);"
					style="margin-top:10px;text-align:left;VERTICAL-ALIGN: middle;border:0px;">
					<span class="ico ico-type-63"></span>收件箱
				</div>
				<div title="已发送" class="mailbtn"
					onclick="gotosys_mailBox('tbsended','已发送',1);">
					<span class="ico ico-type-65"></span>已发送
				</div>
				<div title="草稿箱" class="mailbtn"
					onclick="gotosys_mailBox('tbdraft','草稿箱',2);">
					<span class="ico ico-type-64"></span>草稿箱
				</div>
				<div title="废纸篓" class="mailbtn"
					onclick="gotosys_mailBox('tbdustbin','废纸篓',3);">
					<span class="ico ico-type-66"></span>废纸篓
					<!-- <span
						id="deletePermanent_trash" class="button trash" title="删除全部"></span> -->
				</div>

			</div>
			<div title="邮件中心" class="mailbtn" onclick="gotosys_WebMailSets();">
				<span class="icon_change"></span>邮件中心
			</div>
		</div>
		<div region="center" style="border-top:0px;padding:0px;margin:0px;overflow: hidden;">
			<div class="mini-tabs" activeIndex="0" id="emailtabs"
				maskOnLoad="false" onactivechanged="tabactivechanged"
				style="width:100%;height:100%;border:0px;margin-right:2px;overflow: hidden;"
				bodyStyle="border:0px;padding:0px;overflow: hidden;" headerStyle="border:0px;CURSOR: pointer;">
				<div title="收件箱" url="${path}/email/toWebMailBoxPage.page?box=0" bodyStyle="overflow: hidden;"
					style="border: 0px;overflow: hidden;" id="tbReceives" name="tbReceives"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function gotoinbox() {
			var tabs = mini.get("emailtabs");
			var tab = tabs.getTab("tbReceives");
			tabs.maskOnLoad = true;
			tabs.activeTab(tab);
			tabs.reloadTab(tab);
		}
		function gotosys_mailBox(name, title, box) {
			var tabs = mini.get("emailtabs");
			var tab = tabs.getTab(name);
			if (!tab) {
				tab = {};
				tab.name = name;
				tab.title = title;
				tab.showCloseButton = true;
				tab.url = "${path}/email/toWebMailBoxPage.page?box=" + box;
				tabs.addTab(tab);
			}
			tabs.maskOnLoad = true;
			tabs.activeTab(tab);
		}
		function gotosys_write() {
			var tabs = mini.get("emailtabs");
			var tab = tabs.getTab("tab_write");
			if (!tab) {
				tab = {};
				tab.name = "tab_write";
				tab.title = "写信";
				tab.showCloseButton = true;
				tab.url = "${path}/email/toWebMailWritePage.page";
				tabs.addTab(tab);
			}
			tabs.maskOnLoad = true;
			tabs.activeTab(tab);
		}
		function gotosys_WebMailSets() {
			var tabs = mini.get("emailtabs");
			var tab = tabs.getTab("WebMailSets");
			if (!tab) {
				tab = {};
				tab.name = "WebMailSets";
				tab.title = "邮箱中心";
				tab.showCloseButton = true;
				tab.url = "${path}/public/topage.action?viewName=email/email_serverM";
				tabs.addTab(tab);
			}
			tabs.maskOnLoad = true;
			tabs.activeTab(tab);
		}
		function tabactivechanged(e)
		{			
			mini.get("layout1").doLayout();		
		}
		
	</script>

</body>
</html>
