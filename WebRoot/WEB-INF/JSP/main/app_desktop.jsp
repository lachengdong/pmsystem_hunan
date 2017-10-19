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
<title>应用桌面</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/app/desktop.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/jquery-ui.css" rel="stylesheet"
	type="text/css" />

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/jquery.popbox.css" rel="stylesheet"
	type="text/css" />
	
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

<script src="<%=path%>/scripts/jquery/jquery-ui.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/modernizr-2.6.1.min.js"
	type="text/javascript"></script>
	
<script src="<%=path%>/scripts/jquery/jquery.nicescroll.js"></script>
<script src="<%=path%>/scripts/jquery/jquery.mousewheel.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/jquery/jquery.marquee.min.js"></script>
<script src="<%=path%>/scripts/jquery/jquery.ux.borderlayout.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/jquery/jquery.ux.slidebox.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/jquery/jquery.ux.simulatemouse.js"
	type="text/javascript"></script>




<style type="text/css">
</style>

</head>
<body
	style="margin: 0;padding: 0;border: 0;width: 100%;	height: 100%;	overflow: hidden;">
	<div id="container-scroller">
		<script>
			var url_appSort = "${path}/updateUserAppTheme.action";
			var numnoopen = $.parseJSON('["\/document\/inbox\/index"]');
			var funcIdObj = {};
			if ('${app_ids}' != '') {
				try {
					funcIdObj = $.parseJSON('${app_ids}');
				} catch (error) {
					funcIdObj = {};
				}
			}
			var alluserMenus = mini.decode('${userMenus}');
			var imgdir = "${path}/images/app/";
		</script>
		<div id="control">
			<table align="center">
				<tr>
					<td class="control-l"></td>
					<td class="control-c"></td>
					<td class="control-r"><a id="openAppBox" title="打开应用盒子"
						href="javascript: void(0)" class="cfg"></a></td>
				</tr>
			</table>
		</div>

		<div class="slidebox">
			<div id="trash"></div>
			<div id="container"></div>
		</div>
		<div class="background portal-bg"></div>

		<!-- 提醒框 -->
		<div class="TP_layer AppBox" node-type="BoxRoot" node-data="AppBox">
			<div class="bg">
				<table cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td>
								<div class="content" node-type="content">
									<a href="javascript:void(0);" class="icon-close-2 TP_close"
										title="关闭" node-type="close"></a>
									<div class="title" node-type="title">
										<span node-type="title_content">应用盒子</span><span
											id="add_success"
											style="margin-left:240px;color: red;display:none;">应用添加成功！</span><span
											id="add_failed"
											style="margin-left:240px;color: red;display:none;">应用添加失败！</span>
									</div>
									<div class="content_inner clearfix" node-type="inner">
										<div class="left">
											<a title="应用设置" class="change_type app icon-cube"
												node-action="changeType" node-type="app"></a> <a
												title="分屏设置" class="change_type screen icon-screen"
												node-action="changeType" node-type="screen"></a>
										</div>
										<div class="right">
											<div class="app_page_dom" node-type="changeTypeDom"
												node-data="app">
												<div class="app_cate_list">
													<ul id="trMenus" class="mini-tree clearfix"
														node-type="appCateList"
														style="width:150px;padding:5px;height:300px;"
														showTreeIcon="false" textField="name" idField="resid"
														parentField="presid" resultAsTree="false" showArrow="true"
														expandOnNodeClick="false">
													</ul>
												</div>
												<div class="app_cate_detail" node-type="appCateDetailWrap">
													<ul class="clearfix" node-type="appCateDetail"></ul>
												</div>
											</div>

											<div class="screen_page_dom clearfix"
												node-type="changeTypeDom" node-data="screen">
												<div class="screen_list">
													<ul class="clearfix" node-type="screenList"></ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<!-- 内容框 -->
		<div class="TP_layer DataBox" node-type="BoxRoot" node-data="DataBox"
			style="display:none;">
			<div class="bg">
				<table cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td>
								<div class="content" node-type="content">
									<a href="javascript:void(0);" class="icon-close-2 TP_close"
										title="关闭" node-type="close"></a>
									<div class="data-header" node-type="title">
										<span class="data-title" node-type="title_content"></span>
									</div>
									<div class="content_inner clearfix" node-type="inner"></div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//mini.parse();
		$(function() {
			var trmenu = mini.get("trMenus");
			//var ms = mini.decode('${userMenus}');
			//alert(ms);			
			//alert(ms[0].srurl);
			//alert(decodeURIComponent(ms[0].srurl));
			trmenu.loadList(alluserMenus, 'resid', 'presid');
			trmenu.filter(function(node) {
				if (!trmenu.isLeaf(node)) {
					return true;
				}
			});

		});

		document.ondragstart = function() {
			return false;
		};
	</script>
	<script type="text/javascript" src="<%=path%>/scripts/app/desktop.js"></script>

</body>
</html>
