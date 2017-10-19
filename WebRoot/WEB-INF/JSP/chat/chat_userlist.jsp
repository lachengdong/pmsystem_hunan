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
<title>微信用户列表</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />
<!-- 必须指定,否则高度为0 -->
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
}

.user_1_0 {
	background: url(${path}/images/chat/male_s_offline.png) no-repeat;
}

.user_1_1 {
	background: url(${path}/images/chat/male_s.png) no-repeat;
}

.user_2_0 {
	background: url(${path}/images/chat/female_s_offline.png) no-repeat;
}

.user_2_1 {
	background: url(${path}/images/chat/female_s.png) no-repeat;
}
</style>
</head>
<body>
	<div class="btn-group">
		<img src="${path}/images/chat/male_b.png" class="img-rounded"
			style="float:left;width:50px;height:50px;" />
		<h4 id="headerTitle" style="float:left;">${onlineUser}</h4>
	</div>
	<div class="btn-group"
		style="width:100%; background-color: #f8f8f8;border-color: #e7e7e7;">
		<div class="btn-toolbar" role="toolbar"
			style="padding:0px 2px;margin-top:2px;text-align:right;align:right;">
			<div class="btn-group">
				<button type="button" class="btn btn-info btn-xs dropdown-toggle"
					data-toggle="dropdown" title="用户在线列表切换">
					<span class="glyphicon glyphicon-user"></span> <span
						class="glyphicon glyphicon-chevron-down" style="font-size:8px;"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a href="javascript:getonlineuser(1);">显示在线联系人</a></li>
					<li><a href="javascript:getonlineuser(2);">显示全部</a></li>
				</ul>
				<button type="button" class="btn btn-default btn-xs" style="padding:0px 5px; padding-bottom:1px;margin-left:3px;" onclick="reconnect();"
					 title="重新连接服务器">
					<span class="icon-connection" style="color:green;"></span> 
				</button>
				
				<button type="button" class="btn btn-default btn-xs" style="padding:1px 6px; margin-left:3px;"
					 title="刷新在线用户" onclick="refreshUserList();">
					<span class="glyphicon glyphicon-refresh" style="color:green;"></span> 
				</button>
			</div>
		</div>
	</div>
	<div class="input-group input-group-sm"
		style="margin-top:2px;margin-left:0px;padding:0px;">
		<input type="text" class="form-control" placeholder="搜索：联系人" id="key"
			style="height:25px" /> <span class="input-group-addon icon-search-2"
			onclick="search()"
			style="padding:0px 5px;height:25px;cursor:pointer;"></span>
	</div>

	<div class="mini-fit">
		<ul id="tree1" class="mini-tree"
			style="width:100%;height:100%;padding:0px;" showTreeIcon="false"
			textField="name" idField="orgid" parentField="porgid"
			expandOnNodeClick="true" resultAsTree="false" showArrow="true"
			isLeafField="isLeaf" ondrawnode="onDrawNode" onload="doonload"
			imgPath="${path}/images/chat/">
		</ul>
	</div>
	<script type="text/javascript">
		//mini.parse();		
		$(function() {
			var users = mini.decode('${userlist}');
			var tree = mini.get("tree1");
			tree.loadList(users, 'orgid', 'porgid');

		});
		var notifys = {};
		//显示在线用户
		function getonlineuser(flag) {
			var tree = mini.get("tree1");
			if (flag == 2) {
				tree.clearFilter();
			} else {
				tree.filter(function(node) {
					if (node.int2 == flag) {
						return true;
					}
				});
			}

			for (i in notifys) {
				clearInterval(notifys[i]);
				setTimeout($.proxy(function() {
					var obj = $("#U" + i);
					notifys[i] = highlight(obj, "T_highlight2");
				}, this), 1000);
			}
		};

		//用户列表节点重画
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			var imgs = {
				'user_1_0' : '${path}/images/chat/male_s_offline.png',
				'user_1_1' : '${path}/images/chat/male_s.png',
				'user_2_0' : '${path}/images/chat/female_s_offline.png',
				'user_2_1' : '${path}/images/chat/female_s.png'
			};
			var isLeaf = tree.isLeaf(node);
			if (isLeaf) {
				if (node.orgid.substring(0, 1) == 'U') {
					var pic = "user_" + node.int1 + "_" + node.int2;
					e.nodeHtml = '<SPAN class="'+pic+'" title="" style="text-indent: 10px;float:left;width:25px;" id="'+node.orgid+'">&nbsp;</SPAN><SPAN class=user_name>'
							+ node.name
							+ '&nbsp;<I style="DISPLAY: inline" class="user_funcs user_send_msg icon-bubble-dots-3" title=发送微讯  node-type="userSendMsg" onclick="chat(\''
							+ node.orgid.substring(1, node.orgid.length)
							+ '\',\''
							+ node.name
							+ '\',\''
							+ imgs[pic]
							+ '\');" ></I>&nbsp;<I style="DISPLAY: inline" class="user_funcs user_send_email icon-envelop" title="发送邮件"  node-type="userSendEmail" node-data="1" onclick="sendEmail(\''
							+ node.orgid.substring(1, node.orgid.length)
							+ '\',\''
							+ node.name
							+ '\');" ></I></SPAN>';

				}
			}
			if (node.porgid = -1) {

			}
		};

		function doonload(e) {
			var tree = e.sender;
			tree.expandLevel(0);
		};
		//查询用户
		function search() {
			var tree = mini.get("tree1");
			var key = $("#key").val();
			if (key == "") {
				tree.clearFilter();
			} else {
				key = key.toLowerCase();
				tree.filter(function(node) {
					var text = node.name ? node.name.toLowerCase() : "";
					if (text.indexOf(key) != -1) {
						return true;
					}
				});
			}
			for (i in notifys) {
				clearInterval(notifys[i]);
				setTimeout($.proxy(function() {
					var obj = $("#U" + i);
					notifys[i] = highlight(obj, "T_highlight2");
				}, this), 1000);
			}
		};

		$("#key").bind("keydown", function(e) {
			if (e.keyCode == 13) {
				search();
			}
		});
		function onKeyEnter(e) {
			search();
		};

		//开始新对话
		function chat(uid, name, pic) {
			if (notifys[uid]) {
				clearInterval(notifys[uid]);
				delete notifys[uid];
				$("#U" + uid).removeClass("T_highlight2");
				if (p)
					p.deletenotify(uid);
			}
			parent.chat(uid, name, pic);
		};

		var p;
		//新对话提醒
		function SetData(data) {
			if (!data.Chat.notifyItems)
				return;
			p = data;
			var tree = mini.get("tree1");
			for ( var i in data.Chat.notifyItems) {
				tree.selectNode("U" + i);
				tree.expandPath("U" + i);
				notifys[i] = highlight($("#U" + i), "T_highlight2");

			}
		};

		function highlight(dom, css) {
			if (dom.hasClass(css))
				dom.removeClass(css);
			var n = setInterval(function() {
				if (!dom.hasClass(css))
					dom.addClass(css);
				else
					dom.removeClass(css);
			}, 500);
			return n;
		};

		//发送邮件
		function sendEmail(userId,userName) {
			var url = "${path}/email/toWebMailWritePage.page?actionType=new";
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				showHeader : true,
				title : '发送邮件',
				height : 650,
				width : 800,
				onload : function() {
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData({userId:userId,userName:userName});
				},
				ondestroy : function(action) {

				}
			});
			win.show();
		};
		
		//重新连接服务器
		function reconnect()
		{
			parent.reconnect();
		};
		//刷新在线用户
		function refreshUserList()
		{
			var tree = mini.get("tree1");			
			tree.load("${path}/chat/ajax/relistuser");			
		};
		
	</script>

</body>
</html>
