<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,com.gkzx.common.*,com.gkzx.util.property.*" pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@page import="com.sinog2c.model.system.SystemOrganization"%>
<%@page import="com.sinog2c.model.officeAssistant.TbuserNotice"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sdalarmlevel = (String)request.getSession().getAttribute("sdalarmlevel");

String sysName = "刑罚执行网上协同工作平台V3.01";

Object _user = request.getAttribute("user");
SystemUser user = null;
String orgNameStr = "请刷新页面";
if(_user instanceof SystemUser){
	user = (SystemUser)_user;
	SystemOrganization org = user.getOrganization();
	if(null != org){
		orgNameStr = org.getName();
	}
} else {
	user = new SystemUser();
	user.setUserid("-1");
	user.setName("未登录");
}

TbuserNotice tbuserNotice = null;
Object tbuserNoticeObj = (TbuserNotice)request.getAttribute("tbuserNotice");
if(null != tbuserNoticeObj){
	tbuserNotice = (TbuserNotice)tbuserNoticeObj;
}
%>

<html>
  <head>
	<title> <%=sysName%></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-Frame-Option" content="SAMEORIGIN" />
	<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, max-age=0" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8;"/>
	<link rel="icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
	<link rel="bookmark"  href="<%=path%>/images/favicon.ico" />
	<!--[if lt IE 9]>
    <script src="<%=path%>/scripts/html5shiv/dist/html5shiv.js"></script>
    <![endif]-->
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"/>
    <script src="<%=path%>/scripts/jquery.messager.js" type="text/javascript"></script> 
    <script src="<%=path%>/scripts/jquery/jquery.json-2.4.min.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/jquery/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/modernizr-2.6.1.min.js" type="text/javascript"></script>
    <script src="<%=path%>/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/sockjs/sockjs-1.0.0-beta.12.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=path%>/ckeditor/ckeditor.js"></script>
    
    <link href="<%=path%>/css/top.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
	<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />
	<link href="<%=path%>/css/emoji.css" type="text/css" rel="stylesheet" />
    
    <style type="text/css">
	body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    #time {
        position: absolute;
        right: 1px;
        top: 2px;
        color: blue;
    } 
	</style>
</head>
 <body>
 <input id="noticeid" type="hidden" />
 <%
	if(null !=tbuserNotice){
%>
 		<input id="noticeidValue" type="hidden" value="<%=tbuserNotice.getNoticeid() %>"/>
 <%
	}
%>
 	
   <div class="mini-layout" style="width:100%;height:100%">
        <div title="north" region="north" class="header"  height="76" showHeader="false" showSplit="false" >
         <div class="top">
            <!-- jianyu-main.jpg为监狱图片 如果监狱部署请用此图片 若是法院部署请用fayuan-main.jpg222222222222222222
            <img src="<%=path%>/images/fayuan-main.jpg" />
             -->
            <!--陕西用
             <img src="<%=path%>/images/sx_xingfayy_02.jpg" /> -->
            <img src="<%=path%>/images/jianyu-main.jpg" />
            <div class="right">
             <img src="<%=path%>/images/xingfayy_06.jpg" />
            <div class="she"></div>
            <div style="position:absolute;top:18px;right:60px;">
            <a class="mini-button mini-button-iconTop color:#FFf; " iconCls="icon-edit" onclick="editpwd"  plain="true" >修改个人信息</a>        
            <a class="mini-button mini-button-iconTop" iconCls="icon-date" onclick="manual()"  plain="true" >使用手册</a>        
            <a class="mini-button mini-button-iconTop" iconCls="icon-close" onclick="closewin()"  plain="true" >退出系统</a>        
        </div>
         </div>
         </div>
        </div>
        <div title="south" region="south" showSplit="false" showHeader="false" height="30">
           
           <div style="line-height: 28px;text-align: left;padding-left:5px; ">
           		<a class="mini-menubutton" menu="#popupMenu" plain="true"
					iconCls="icon-tshirt" data-tooltip="更换界面主题" data-placement="top"
					style="color:red;" title="更换界面主题"></a>
				<ul id="popupMenu" class="mini-menu" style="display:none;"
					title="更换界面主题">
					<li iconCls="icon-node" onclick="changeInterface('classic');">标准界面</li>
					<li iconCls="icon-user" onclick="changeInterface('app');">应用界面</li>
				</ul>
           		当前用户：<%=user.getName() %>&nbsp;(<%=user.getUserid() %> , <%= orgNameStr %>)<span style="padding-left:20px;"></span>
           		在线人数：<div id="onlineNumber" style="display:inline;"></div>
           		</div>
           <div id="time" style="margin-right: 10px;">
				快速检索
				<input id="zaiyacombo1" class="mini-combobox" style="width:100px;" textField="name" valueField="codeid" emptyText="请选择..."   url="ajaxCodeShuJu.action?sctid=GK069" value="1"  required="true" disabled="true" showNullItem="true" nullItemText="请选择..."/> 
				<input name="sdid" id="sdid" class="mini-combobox" style="width:110px;" valueField="ORGID" textField="NAME" url="getDepartList.action?1=1&qtype=jianqu" required="false" disabled="true" emptyText="--全部--" nullItemText="--全部--" showNullItem="true"/>
				<input class="mini-textbox" id="key" class="mini-textbox" emptyText="罪犯编号,姓名,拼音"  onenter="onKeyEnter"/>
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
			</div>
	<div id="win1" class="mini-window" title="通知" style="width:290px;height:200px;"
    showMaxButton="false" showCollapseButton="true" showShadow="true" allowResize="false"
    showToolbar="false" showFooter="true" showModal="false" allowResize="true" allowDrag="false"
    >
    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;display:none;">
        <!--
        <input type='button' value='不再提示' onclick="hideWindow()" style='vertical-align:middle;'/>
    	-->
    </div>
    <div>
    	<div align="center">
    		<b id="noticeTitle">
    		    <%
    				if(null !=tbuserNotice){
    			%>
						<%=tbuserNotice.getTitle() %>
				<%
    				}
				%>
    		</b><br/>
    	</div>
		<div  id="noticeContent">
			<%
    			if(null !=tbuserNotice){
    		%>
				<%=tbuserNotice.getContent() %>
			<%
    			}
			%>
		</div>	
    	</div>
	</div>			
    </div>
    <div showHeader="false" region="west" showSplitIcon="true"  width="210" maxWidth="250" minWidth="100" style="border: 0px;">
        <!--OutlookMenu-->
        <div id="leftTree" class="mini-outlooktree" url="menutree.json" onnodeclick="onNodeSelect"
            textField="name" idField="resid" parentField="presid"  expandOnLoad="false" expandOnNodeClick="true" >
        </div>
    </div>
     <div title="center" region="center" style="border:0px;padding: 0px;margin: 0px;" bodyStyle="overflow:hidden;">
          <div showCollapseButton="false" style="width: 100%;height: 100%;border:0px;">
             <div id="mainTabs" class="mini-tabs" contextMenu="#tabsMenu" ontabload="mianbanload" activeIndex="0" style="width:100%;height:100%;border: 0px;"      
                  plain="false">
                 <div name="first" title="我的桌面"  url="<%=path%>/desktop.action" style="border: 0px;">        
                 </div>
             </div>
          
             <ul id="tabsMenu" class="mini-contextmenu" onBeforeOpen="onBeforeOpen">        
		        <li onclick="closeTab">关闭当前标签页</li>                
			    <li onclick="closeAllButFirst">关闭其他标签页</li>
			    <li onclick="closeAll">关闭所有标签页</li>        
		    </ul> 
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
		bodyStyle="padding:0px" allowDrag="true" onbuttonclick="winbuttonclick"></div>
	<jsp:include page="/WEB-INF/JSP/chat/chat_box.jsp"></jsp:include>
<script type="text/javascript">
        mini.parse();
        var $CONFIG = {};
		$CONFIG['defaultSkin'] = '1';
		$CONFIG['timer_message'] = 20;
		$CONFIG['timer_notification'] = 30;
		$CONFIG['path'] = '${path}';
		$CONFIG['basePath'] = '${basePath}';
		$CONFIG['enable_loading'] = 1;
		$CONFIG['url_getUnreadMsg'] = '${path}/chat/getUserUnreadMsg';
		var chatListShowed = false;
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
        //
        var mainTabs = mini.get("mainTabs");
        mainTabs.setTabPosition("bottom");
        
 		//var tabs = mini.get("tabs1");
        var currentTab = null;
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        //$(window).bind("keydown", documentKeyEventListener);
        //
        $(function(){
     		// 选择上次退出的菜单节点
     		selectPrevNode();
            //在线人数
 			showOnlineUserCount();
 		});
        // 显示在线人数
		function showOnlineUserCount(){
			$.ajax({
	                url: "getUserCount.action?1=1",
	                data: {},
	                cache: false,
	                type: "post",
	                success: function (text) {
	                	$("#onlineNumber").html(text);
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                }
	          });
		 };
        
        function showTab(node, crimid) {
        	if(!node || !node.srurl){
        		return;
        	}
            var tabs = mini.get("mainTabs");
            var id = "tab$" + node.resid;
            var tab = tabs.getTab(id);
            //tabs.reloadTab(tab);
            if (!tab) {
            	var thelength=tabs.getTabs().length;
            	if(thelength>8)
            	{
            		// 自动关闭最左边的
            		tabs.removeTab(1);
            		//
            		thelength=tabs.getTabs().length;
            	}
            	// 再次判断
            	if(thelength>8)
            	{
            		//alert("标签页不能超过8个，请先关闭不使用的标签");
            		alert("标签页打开过多，请在标签页上点击鼠标右键关闭不使用的标签");
            		return false;
            	}
            	else{
            		tab = {};
	                tab.name = id;
	                tab.title = node.name;
	                tab.showCloseButton = true;
	                var srurl = node.srurl;
	                if(crimid){
	                	srurl += "&_criminalid="+crimid;
	                }
	                //
	                var ismenu = node.ismenu;
	                var formid = node.formid;
	                var querysql = node.querysql;
	                //
	                if(srurl){
	                	srurl = srurl.trim();
	                	var theURL = srurl;
	                	//
	                	var srurlLenth = srurl.length;
	                	var indexHttp = srurl.indexOf("http");
	                	var indexQuestionMark = srurl.indexOf("?");
	                	var indexEqualSign = srurl.indexOf("=");
		                // 加随机数
		                var randomVal = new Date().getTime();
		                if(indexQuestionMark < 0 ){
		                	theURL += "?1=1"
		                } else if(indexEqualSign < 0){
		                	theURL += "1=1"
		                }
		                //
		                if(0 === ismenu || ismenu){
		                	theURL += '&ismenu='+ismenu;
		                }
		                if(formid){
		                	theURL += '&tempid=' + formid;
		                }
		                if(querysql){
		                	theURL += '&solutionid='+querysql;
		                }
		                
		                theURL += '&menuid='+node.resid;// + "&__r=" + randomVal;
		                //
		                //
		                tab.url =theURL;
		                //tab.url =srurl+'&menuid='+node.resid + "&__r=" + randomVal;
		                //alert(tab.url);
		                if(srurl){
		                	// URL不为空,
		                	tabs.addTab(tab);
		                }
	                } else {
	                	return; // 没有地址,不进行处理
	                }
            	}
            }
            tabs.activeTab(tab);
            tabs.setTabPosition("bottom");
        };
        function onNodeSelect(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;
            if (isLeaf) {
                showTab(node);
            }
			var resid = node.resid;
			if(resid){
				// 保存本地 cookie
				setCookie("favorite_resid", resid);
			}
        };
        function editpwd() {
            mini.open({
                url:"user/updateselfinfo.action",
                showMaxButton: true,
	            allowResize: false,
                title: "修改个人信息", width: 400, height: 350,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                }
            });
        };
       
		// 使用手册
		function manual(){
			//
			var filename = "刑罚执行网上协同工作平台V3.0_使用手册.doc";
			return mydownload(filename);
		};
      
		function mydownload(filename){
			var download_iframe = document.getElementById('download_iframe');
			if(download_iframe){
				download_iframe.src="<%=path %>/download/" + filename;// +"?_="+Math.random();
				return false;
			}
			return false;
	    };
        
      	function closewin(){
	        if(confirm("确定退出当前系统？")){
	        	window["userlogout"] = true;
	        	window.parent.location.href="logout.action?1=1"; 
	        } 
      	} ;
      	//
      	function documentKeyEventListener(e){
      		//
      		var altKey = e.altKey;
      		var ctrlKey = e.ctrlKey;
      		var shiftKey = e.shiftKey;
      		var code = e.keyCode;
      		//
      		var N = 78;
      		//
      		if(e.ctrlKey && N===code){
      			e["stopPropagation"] && e.stopPropagation();
      			e["preventDefault"] && e.preventDefault();
          		debug(e);
          		return false;
      		}
      	};
      	/*
         function onClick(e) {
         	mini.parse();
            var text = this.getText();
          	var node =  O('publicShowExplain.action?1=1',text,'7532');
            showTab(node);
        };
        function O(smurl,smdiscribe,smid){
        	this.smurl=smurl;
			this.smdiscribe=smdiscribe; 
			this.smid=smid; 
			return this; 
		} ;
		*/
 //////////////////////////////////////////

        function onBeforeOpen(e) {
            currentTab = mainTabs.getTabByEvent(e.htmlEvent);
            if (!currentTab) {
                e.cancel = true;                
            }
        };

        ///////////////////////////
        function closeTab() {
            mainTabs.removeTab(currentTab);
        };
        function closeAllBut() {
            mainTabs.removeAll(currentTab);
        };
        function closeAll() {
        	var but = [];  
        	but.push(mainTabs.getTab("first"));
            mainTabs.removeAll(but);
        };
        function closeAllButFirst() {
            var but = [currentTab];            
            but.push(mainTabs.getTab("first"));
            mainTabs.removeAll(but);
        };
        function search() {
	        var key = mini.get("key").getValue();
	        key = encodeURI(key);
	        var tabs = mini.get("mainTabs");
            var id = "tab$fastsearch";
            var tab = tabs.getTab(id);
            var zaiyacombo1=mini.get("zaiyacombo1").getValue();
            var sdid=mini.get("sdid").getValue();
            // 加随机数
	        var randomVal = new Date().getTime();
            var url = "gotofastCriminalJianSuo.action?key="+key+"&zaiyacombo1="+zaiyacombo1+"&sdid="+sdid+ "&__r=" + randomVal;;
             
            if (!tab) {
            	var thelength=tabs.getTabs().length;
            	if(thelength>8)
            	{
            		// 自动关闭最左边的
            		tabs.removeTab(1);
            		thelength=tabs.getTabs().length;
				}
				//新增快速查询tab
           		tab = addsearchtabs(id,key,zaiyacombo1,sdid);
           		tab.url = url;
           		tabs.addTab(tab);
            }else{
            	tab.url = url;
            	tabs.reloadTab(tab);
            }
            tabs.activeTab(tab);
            tabs.setTabPosition("bottom");
		};
		function onKeyEnter(e) {
            search();
        };
		
		function addsearchtabs(id,key,zaiyacombo1,sdid){
			var tab = {};
			tab.name = id;
            tab.title = '快速检索';
            tab.showCloseButton = true;
           // tab.url ="gotofastCriminalJianSuo.action?key="+key+"&zaiyacombo1="+zaiyacombo1+"&sdid="+sdid;
            return tab;
		};
		
		function mianbanload(e){
			if(e.tab.name=='tab$fastsearch'){
				var key = mini.get("key").getValue();
				var zaiyacombo1=mini.get("zaiyacombo1").getValue();
				var sdid=mini.get("sdid").getValue();
				var tabs = mini.get("mainTabs");
				var iframe=tabs.getTabIFrameEl(e.tab);
				var data={key:key,zaiyacombo1:zaiyacombo1,sdid:sdid};
				iframe.contentWindow.SetData(data);
			}
		};
		 
		//阅读短消息
		function readMessage(){
			var noticeid = document.getElementById("noticeid").value;
			$.ajax({
                url: "readMessage.action?1=1",
                data: {	noticeid : noticeid },
                cache: false,
                type: "post",
                success: function (text) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
          });
		};
		//
		
		// 选择上次退出的菜单节点
		function selectPrevNode(){
			// 获取cookie
			resid = getCookie("favorite_resid");
			if(resid){
				var tree = mini.get("leftTree");
	            if(!tree){
	            	return;
	            }
	            var node = tree.getNode(resid);
	            if(!node){
	            	return;
	            }
	            //tree.selectNode(node);
				// 展开
	    		tree.expandPath(node.resid);
			}
		};
		
		function openTodoListPage(resid){
			//resid = getCookie("favorite_resid");
			if(resid){
				var tree = mini.get("leftTree");
				if(!tree){
					return;
				}
				var node = tree.getNode(resid);
				if(!node){
					return;
				}
				// 展开
				tree.expandPath(node.resid);
				showTab(node);
				setCookie("favorite_resid", resid);
			}
		}
		
		
		function openTodoListPage1(resid,crimid){
			if(resid){
				var tree = mini.get("leftTree");
				if(!tree){
					return;
				}
				var node = tree.getNode(resid);
				if(!node){
					return;
				}
				// 展开
				tree.expandPath(node.resid);
				showTab(node,crimid);
				setCookie("favorite_resid", resid);
			}
		}
		
		
		function hideWindow() {
			var a = "";
			var noticeTitle = document.getElementById("noticeTitle");
			var noticeContent = document.getElementById("noticeContent");
			var noticeid = document.getElementById("noticeidValue").value;
			$.ajax({
	        	url: "getNextNotice.action?1=1",
	            data: { noticeid : noticeid},
	            cache: false,
	            type: "post",
	            success: function (text) {
	            	var o = mini.decode(text);
	            	if(o.next == '1'){
	            		document.getElementById("noticeidValue").value = o.noticeid;
	            		noticeTitle.innerHTML =  o.title;
	            		noticeContent.innerHTML = o.content;
	            	} else {
	            		hideNoticeWindow();
	            	}
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	            
	            }
	    	});
		};

		//
		function  showUserNotice(){
	        var win = mini.get("win1");
	        var x = "right";
	        var y = "bottom";
	        win.showAtPos(x, y);
        	setTimeout(hideNoticeWindow, 30*1000);
		};
		function hideNoticeWindow() {
        	var win = mini.get("win1");
        	win.hide();
    	};

        <%
        	if(null !=tbuserNotice && tbuserNotice.getNoticeid()!=0) {
        %>
        	// 显示用户信息
        	showUserNotice();
        <%
        	}
        %>
        
        function chat(uid, name, bigIcon) {
			P.webIm.startChat({
				"id" : uid,
				"name" : name,
				"avatar" : bigIcon
			});
		}
		
		//即时消息--重新连接服务器
		function reconnect() {
			P.disconnect();
			P.connect();
		}
		
		//
		var curInterface = 'classic';
		var tip = new mini.ToolTip();
		tip.set({
			placement : top,
			target : document,
			selector : '[data-tooltip]'
		});

		var urls = {
			classic : "${path}/app_main.action?theme=classic",
			app : "${path}/app_main.action?theme=app"
		};
		var isChangeFace = false;
		//系统界面切换
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
<script src="<%=path%>/scripts/chat/theme.js">

<script type="text/javascript">
window.onbeforeunload= function (){
	if(window["userlogout"]){
		return ;
	};
	if(true || (event.clientX>document.body.clientWidth&&event.clientY<0 ) || event.altKey || event.ctrlKey)
	{
		return "请确认是否关闭!!!";
	}
};
</script>
 
</body>
</html>