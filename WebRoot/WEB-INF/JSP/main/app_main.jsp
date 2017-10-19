<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"
	import="java.util.*,com.gkzx.common.*,com.gkzx.util.property.*"
	pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@page import="com.sinog2c.model.system.SystemOrganization"%>
<%@page import="com.sinog2c.model.officeAssistant.TbuserNotice"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String sdalarmlevel = (String) request.getSession().getAttribute(
			"sdalarmlevel");
	String sysName = "国科网上协同工作平台V3.0";
%>
<html>
<head>
<title><%=sysName%></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8;" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta http-equiv="Cache-Control"
	content="no-store, no-cache, must-revalidate, max-age=0" />
<meta http-equiv="expires" content="0" />
<link rel="icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
<link rel="bookmark" href="<%=path%>/images/favicon.ico" />
<!--[if lt IE 9]>
    <script src="<%=path%>/scripts/html5shiv/dist/html5shiv.js"></script>
    <![endif]-->
</head>

<script src="<%=path%>/scripts/boot.js" type="text/javascript">
	
</script>
<script src="<%=path%>/scripts/jquery/jquery.messager.js"
	type="text/javascript"></script>

<script src="<%=path%>/scripts/jquery/jquery.json-2.4.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/jquery/jquery.nicescroll.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/modernizr-2.6.1.min.js"
	type="text/javascript"></script>

<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>

<script src="<%=path%>/scripts/sockjs/sockjs-1.0.0-beta.12.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/ckeditor/ckeditor.js"></script>

<link href="<%=path%>/css/top.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/emoji.css" type="text/css" rel="stylesheet" />

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

#time {
	position: absolute;
	right: 1px;
	top: 2px;
	color: blue;
}

.mini-menu-inner {
	padding: 0px;
	margin: 0px;
}

.mini-menubar {
	padding: 0px;
	margin: 0px;
}

.mini-menuitem-text {
	cursor: pointer;
}

.mini-layout-split {
	display: none;
}
</style>
</head>
<body>
	<input id="noticeid" type="hidden" />
	<input id="noticeidValue" type="hidden"
		value="${tbuserNotice.getNoticeid()}" />
	<div class="mini-layout" style="width:100%;height:100%;display:none;"
		id="divLayout" showSplit="false" splitSize="0">
		<div title="north" region="north" height="76" showHeader="false"
			borderStyle="border:0px;" showSplit="false"
			style="padding:0px;margin:0px;border:0px;"
			bodyStyle="overflow:hidden;padding:0px;margin:0px;border:0px;">
			<div class="top">
				<img src="<%=path%>/images/jianyu-main.jpg" />
				<div class="right">
					<img src="<%=path%>/images/xingfayy_06.jpg" />
					<div class="she"></div>
					<div style="position:absolute;top:18px;right:60px;">

						<a class="mini-button mini-button-iconTop color:#FFf; "
							iconCls="icon-edit" onclick="editpwd" plain="true">修改个人信息</a> <a
							class="mini-button mini-button-iconTop" iconCls="icon-date"
							onclick="manual()" plain="true">使用手册</a> <a
							class="mini-button mini-button-iconTop" iconCls="icon-close"
							onclick="closewin()" plain="true">退出系统</a>
					</div>
				</div>
			</div>
		</div>
		<div title="south" region="south" showSplit="false" showHeader="false"
			style="border:0px;" height="30" borderStyle="border:0px;"
			bodyStyle="overflow:hidden;padding:0px;margin:0px;border:0px;">
			<div style="line-height: 28px;text-align: left;padding-left:2px; ">
				<a class="mini-menubutton" menu="#popupMenu" plain="true"
					iconCls="icon-tshirt" data-tooltip="更换界面主题" data-placement="top"
					style="color:red;" title="更换界面主题"></a>
				<ul id="popupMenu" class="mini-menu" style="display:none;"
					title="更换界面主题">
					<li iconCls="icon-node" onclick="changeInterface('classic');">标准界面</li>
					<li iconCls="icon-user" onclick="changeInterface('app');">应用界面</li>
				</ul>
				当前用户：${user.getName()}&nbsp;(${user.getUserid()} ,${orgNameStr} )<span
					style="padding-left:20px;"></span> 在线人数：
				<div id="onlineNumber" style="display:inline;"></div>
			</div>
			<div id="time" style="margin-right: 5px;">
				快速检索 <input id="zaiyacombo1" class="mini-combobox"
					style="width:100px;" textField="name" valueField="codeid"
					emptyText="请选择..." url="ajaxCodeShuJu.action?sctid=GK069" value="1"
					required="true" disabled="true" showNullItem="true"
					nullItemText="请选择..." /> <input name="sdid" id="sdid"
					class="mini-combobox" style="width:110px;" valueField="ORGID"
					textField="NAME" url="getDepartList.action?1=1&qtype=jianqu"
					required="false" disabled="true" emptyText="--全部--"
					nullItemText="--全部--" showNullItem="true" /> <input
					class="mini-textbox" id="key" class="mini-textbox"
					emptyText="罪犯编号,姓名" onenter="onKeyEnter" /> <a class="mini-button"
					plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
				<a onclick="openChatUserList()"
					style="float:right;margin-top:1px;cursor:pointer;"
					class="icon-users" title="及时消息（未上线）" id="onlineStatus"></a>
			</div>
			<div id="win1" class="mini-window" title="通知"
				style="width:290px;height:200px;" showMaxButton="false"
				showCollapseButton="true" showShadow="true" allowResize="false"
				showToolbar="false" showFooter="true" showModal="false"
				allowResize="true" allowDrag="false">
				<div property="footer"
					style="text-align:right;padding:5px;padding-right:15px;display:none;">
				</div>
				<div>
					<div align="center">
						<b id="noticeTitle"> ${tbuserNotice.getTitle()} </b><br />
					</div>
					<div id="noticeContent">${tbuserNotice.getContent()}</div>
				</div>
			</div>
		</div>

		<div title="center" region="center" borderStyle="border:0"
			style="border:0px;padding: 0px;margin: 0px;overflow:hidden;width:100%;height:100%;"
			showSplit="false"
			bodyStyle="overflow:hidden;padding:0px;margin:0px;border:0px;">

			<div id="mainTabs" class="mini-tabs" contextMenu="#tabsMenu"
				buttons="#tabsButtons" ontabload="mianbanload" activeIndex="0"
				bodyStyle="padding:1px;overflow:hidden;"  onactivechanged="mainactivechanged"
				style="width:100%;height:100%;border: 0px;overflow:hidden" 
				plain="false">
				<div name="first" title="我的桌面" style="border: 0px;overflow:hidden;"
					bodyStyle="overflow:hidden" url="${path}/app_desktop.action"></div>
			</div>

			<ul id="tabsMenu" class="mini-contextmenu"
				onBeforeOpen="onBeforeOpen">
				<li onclick="closeTab">关闭当前标签页</li>
				<li onclick="closeAllButFirst">关闭其他标签页</li>
				<li onclick="closeAll">关闭所有标签页</li>
			</ul>

			<div id="tabsButtons">
				<a class="mini-menubutton " menu="#popupMenu1" plain="true"> <span
					class="icon-home">&nbsp;系统菜单</span>
				</a>

				<ul id="popupMenu1" class="mini-menu" style="display:none;"
					textField="name" idField="resid" parentField="presid"
					onitemclick="onNodeSelect">

				</ul>
				<a class="mini-button" plain="true" iconCls="icon-expand"
					id="hide_topbar" onclick="hideshowTopbar();" hidefocus="hidefocus"
					title="隐藏/显示顶部" data-step="10"></a>
			</div>
		</div>
	</div>
	<div style="display:none;">
		<iframe id="download_iframe"></iframe>
	</div>
	<div id="winuserList" class="mini-window" title="即时消息"
		style="width:270px;height:480px" showMaxButton="false"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="false" allowResize="false"
		bodyStyle="padding:0px" allowDrag="true"
		onbuttonclick="winbuttonclick"></div>
	<jsp:include page="/WEB-INF/JSP/chat/chat_box.jsp"></jsp:include>
	<script type="text/javascript">
		var $CONFIG = {};
		$CONFIG['defaultSkin'] = '1';
		$CONFIG['timer_message'] = 20;
		$CONFIG['timer_notification'] = 30;
		$CONFIG['path'] = '${path}';
		$CONFIG['basePath'] = '${basePath}';
		$CONFIG['enable_loading'] = 1;
		$CONFIG['url_getUnreadMsg'] = '${path}/chat/getUserUnreadMsg';
		var chatListShowed = false;
		mini.mask({
			el : document.body,
			cls : 'mini-mask-loading',
			html : '页面资源加载中，请稍候...'
		});

		$(function() {
			mini.unmask(document.body);
			mini.get("divLayout").show();

			//我的桌面加载
			//var tabs = mini.get("mainTabs");
			//var tab = tabs.getTab("first");
			//tabs.loadTab("${path}/app_desktop.action", tab);

			//消息提示
			
			var noticeid = ${tbuserNotice.getNoticeid()};
		
			if (noticeid && noticeid != 0) {
				showUserNotice();
			}

			//在线人数
			showOnlineUserCount();

			//初始化即时消息
			P.CurrentUser = '${user.userid}';
			P.CurrentUserName = '${user.name}';
			P.init();

			$("#hide_topbar").attr("title", "隐藏/显示顶部");

			//系统菜单加载
			var popmenu = mini.get("popupMenu1");
			popmenu.load("${path}/menutree.json");

		});

		//即时消息--重新连接服务器
		function reconnect() {
			P.disconnect();
			P.connect();
		}

		//打开即时消息用户列表
		function openChatUserList() {
			var atEl = document.getElementById("onlineStatus");
			var win = mini.get("winuserList");
			var url = "${path}/chat/listuser.action";
			var ifram0 = win.getIFrameEl();
			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(P);
				}, function(action) {
					alert('ddd');
					alert(action);
				});
			} else {
				ifram0.contentWindow.SetData(P);
			}
			if (!chatListShowed) {
				win.showAtEl(atEl, {
					xAlign : 'right',
					yAlign : 'above'
				});
				chatListShowed = true;
			} else {
				win.hide();
				chatListShowed = false;
			}
		};

		function winbuttonclick(e) {
			if (e.name == 'close') {
				chatListShowed = false;
			}
		};
		
		function mainactivechanged()
		{
			mini.get("divLayout").doLayout();
		}

		//mainTabs.setTabPosition("top");
		var currentTab = null;

		$("#key").bind("keydown", function(e) {
			if (e.keyCode == 13) {
				search();
			}
		});

		// 显示在线人数
		function showOnlineUserCount() {
			$.ajax({
				url : "getUserCount.action?1=1",
				data : {},
				cache : false,
				type : "post",
				success : function(text) {
					$("#onlineNumber").html(text);
				},
				error : function(jqXHR, textStatus, errorThrown) {
				}
			});
		};

		function hideshowTopbar() {
			if (mini.get("divLayout").isVisibleRegion("north")) {
				mini.get("divLayout").hideRegion("north");
				$("#hide_topbar>span").removeClass("icon-expand");
				$("#hide_topbar>span").addClass("icon-collapse");
			} else {
				mini.get("divLayout").showRegion("north");
				$("#hide_topbar>span").removeClass("icon-collapse");
				$("#hide_topbar>span").addClass("icon-expand");
			}

		};

		//系统菜单选择
		function onNodeSelect(e) {
			var node = e.item;
			var isLeaf = e.isLeaf;
			if (isLeaf) {
				showTab(node);
			}

		};

		function showTab(node) {
			if (!node || !node.srurl) {
				return;
			}
			var tabs = mini.get("mainTabs");
			var id = "tab$" + node.resid;
			var tab = tabs.getTab(id);
			if (!tab) {
				var thelength = tabs.getTabs().length;
				if (thelength > 8) {
					// 自动关闭最左边的
					tabs.removeTab(1);
					//
					thelength = tabs.getTabs().length;
				}
				// 再次判断
				if (thelength > 8) {
					//alert("标签页不能超过8个，请先关闭不使用的标签");
					alert("标签页打开过多，请在标签页上点击鼠标右键关闭不使用的标签");
					return false;
				} else {
					tab = {};
					tab.name = id;
					tab.title = node.name;
					tab.showCloseButton = true;
					var srurl = node.srurl;
					//
					var ismenu = node.ismenu;
					var formid = node.formid;
					var querysql = node.querysql;
					//
					if (srurl) {
						srurl = srurl.trim();
						var theURL = srurl;
						var indexQuestionMark = srurl.indexOf("?");
						var indexEqualSign = srurl.indexOf("=");

						if (indexQuestionMark < 0) {
							theURL += "?1=1";
						} else if (indexEqualSign < 0) {
							theURL += "1=1";
						}
						//
						if (0 === ismenu || ismenu) {
							theURL += '&ismenu=' + ismenu;
						}
						if (formid) {
							theURL += '&tempid=' + formid;
						}
						if (querysql) {
							theURL += '&solutionid=' + querysql;
						}

						theURL += '&menuid=' + node.resid;// + "&__r=" + randomVal;						//

						tab.url = theURL;
						if (srurl) {

							tabs.addTab(tab);
						}
					} else {
						return; // 没有地址,不进行处理
					}
				}
			}
			tabs.activeTab(tab);
			tabs.setTabPosition("top");
		};

		function editpwd() {
			mini.open({
				url : "user/updateselfinfo.action",
				showMaxButton : true,
				allowResize : false,
				title : "修改个人信息",
				width : 400,
				height : 350,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "new"
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
				}
			});
		};

		// 使用手册
		function manual() {
			//
			var filename = "刑罚执行网上协同工作平台V3.0_使用手册.doc";
			return mydownload(filename);
		};

		function mydownload(filename) {
			var download_iframe = document.getElementById('download_iframe');
			if (download_iframe) {
				download_iframe.src = "${path}/download/" + filename;// +"?_="+Math.random();
				return false;
			}
			return false;
		};

		function closewin() {
			if (confirm("确定退出当前系统？")) {
				window["userlogout"] = true;
				window.parent.location.href = "logout.action?1=1";
			}
		};
		//		

		function onBeforeOpen(e) {
			var mainTabs = mini.get("mainTabs");
			currentTab = mainTabs.getTabByEvent(e.htmlEvent);
			if (!currentTab) {
				e.cancel = true;
			}
		};

		///////////////////////////
		function closeTab() {
			var mainTabs = mini.get("mainTabs");
			mainTabs.removeTab(currentTab);
		};
		function closeAllBut() {
			var mainTabs = mini.get("mainTabs");
			mainTabs.removeAll(currentTab);
		};
		function closeAll() {
			var mainTabs = mini.get("mainTabs");
			var but = [];
			but.push(mainTabs.getTab("first"));
			mainTabs.removeAll(but);
		};
		function closeAllButFirst() {
			var mainTabs = mini.get("mainTabs");
			var but = [ currentTab ];
			but.push(mainTabs.getTab("first"));
			mainTabs.removeAll(but);
		};
		function search() {
			var key = mini.get("key").getValue();
			key = encodeURI(key);
			var tabs = mini.get("mainTabs");
			var id = "tab$fastsearch";
			var tab = tabs.getTab(id);
			var zaiyacombo1 = mini.get("zaiyacombo1").getValue();
			var sdid = mini.get("sdid").getValue();
			// 加随机数
			var randomVal = new Date().getTime();
			var url = "gotofastCriminalJianSuo.action?key=" + key
					+ "&zaiyacombo1=" + zaiyacombo1 + "&sdid=" + sdid + "&__r="
					+ randomVal;
			;

			if (!tab) {
				var thelength = tabs.getTabs().length;
				if (thelength > 8) {
					// 自动关闭最左边的
					tabs.removeTab(1);
					thelength = tabs.getTabs().length;
				}
				//新增快速查询tab
				tab = addsearchtabs(id, key, zaiyacombo1, sdid);
				tab.url = url;
				tabs.addTab(tab);
			} else {
				tab.url = url;
				tabs.reloadTab(tab);
			}
			tabs.activeTab(tab);
			tabs.setTabPosition("top");
		};
		function onKeyEnter(e) {
			search();
		};

		function addsearchtabs(id, key, zaiyacombo1, sdid) {
			var tab = {};
			tab.name = id;
			tab.title = '快速检索';
			tab.showCloseButton = true;
			return tab;
		};

		function mianbanload(e) {
			if (e.tab.name == 'tab$fastsearch') {
				var key = mini.get("key").getValue();
				var zaiyacombo1 = mini.get("zaiyacombo1").getValue();
				var sdid = mini.get("sdid").getValue();
				var tabs = mini.get("mainTabs");
				var iframe = tabs.getTabIFrameEl(e.tab);
				var data = {
					key : key,
					zaiyacombo1 : zaiyacombo1,
					sdid : sdid
				};
				iframe.contentWindow.SetData(data);
			}
		};

		//阅读短消息
		function readMessage() {
			var noticeid = document.getElementById("noticeid").value;
			$.ajax({
				url : "readMessage.action?1=1",
				data : {
					noticeid : noticeid
				},
				cache : false,
				type : "post",
				success : function(text) {
				},
				error : function(jqXHR, textStatus, errorThrown) {
				}
			});
		};
		//		

		function hideWindow() {
			var noticeTitle = document.getElementById("noticeTitle");
			var noticeContent = document.getElementById("noticeContent");
			var noticeid = document.getElementById("noticeidValue").value;
			$
					.ajax({
						url : "getNextNotice.action?1=1",
						data : {
							noticeid : noticeid
						},
						cache : false,
						type : "post",
						success : function(text) {
							var o = mini.decode(text);
							if (o.next == '1') {
								document.getElementById("noticeidValue").value = o.noticeid;
								noticeTitle.innerHTML = o.title;
								noticeContent.innerHTML = o.content;
							} else {
								hideNoticeWindow();
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {

						}
					});
		};

		//
		function showUserNotice() {
			var win = mini.get("win1");
			var x = "right";
			var y = "bottom";
			win.showAtPos(x, y);
			setTimeout(hideNoticeWindow, 30 * 1000);
		};
		function hideNoticeWindow() {
			var win = mini.get("win1");
			win.hide();
		};

		function chat(uid, name, bigIcon) {
			P.webIm.startChat({
				"id" : uid,
				"name" : name,
				"avatar" : bigIcon
			});
		}

		var curInterface = 'app';

		var urls = {
			classic : "${path}/app_main.action?theme=classic",
			app : "${path}/app_main.action?theme=app"
		};
		var isChangeFace = false;
		function changeInterface(face) {
			if (face == curInterface)
				return;
			isChangeFace = true;
			mini.mask({
				el : document.body,
				cls : 'mini-mask-loading',
				html : '界面切换中，请稍候...'
			});

			setTimeout($.proxy(function() {
				window.location.href = urls[face];
			}, this), 300);

		};

		window.onbeforeunload = function() {
			if (window["userlogout"]) {
				return;
			}
			;
			if (isChangeFace)
				return;
			if (true
					|| (event.clientX > document.body.clientWidth && event.clientY < 0)
					|| event.altKey || event.ctrlKey) {
				return "请确认是否关闭!!!";
			}
		};
		window.onunload = function() {
			P.disconnect();
		};
	</script>
	<script src="<%=path%>/scripts/chat/theme.js"></script>

</body>
</html>