<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,com.gkzx.common.*,com.gkzx.util.property.*" pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@page import="com.sinog2c.model.system.SystemOrganization"%>
<%@page import="com.sinog2c.model.officeAssistant.TbuserNotice"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sdalarmlevel = (String)request.getSession().getAttribute("sdalarmlevel");

String sysName = "减刑假释网上协同办案平台";

Object _user = request.getAttribute("user");
SystemUser user = null;
if(_user instanceof SystemUser){
	user = (SystemUser)_user;
} else {
	user = new SystemUser();
	user.setUserid("-1");
	user.setName("未登录");
}

TbuserNotice tbuserNotice = (TbuserNotice)request.getAttribute("tbuserNotice");
if(null == tbuserNotice){
	tbuserNotice = new TbuserNotice();
}
%>

<html>
  <head>
	<title> <%=sysName%></title>
	<meta http-equiv="Content-Type" content="no-cache" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8;"/>
	<link rel="icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" />
	<link rel="bookmark"  href="<%=path%>/images/favicon.ico" />
		
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"/>
    <script src="<%=path%>/scripts/jquery.messager.js" type="text/javascript"></script> 
    <link href="<%=path%>/css/top.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
 <input id="noticeidValue" type="hidden" value="<%=tbuserNotice.getNoticeid() %>"/>
   <div class="mini-layout" style="width:100%;height:100%">
        <div title="north" region="north" class="header"  height="76" showHeader="false" showSplit="false" >
         <div class="top">
            <img src="<%=path%>/images/fayuan-main.jpg" />
            <div class="right">
             <img src="<%=path%>/images/xingfayy_06.jpg" />
            <div class="she"></div>
            <div style="position:absolute;top:18px;right:60px;">
            <a class="mini-button mini-button-iconTop color:#FFf; " iconCls="icon-edit" onclick="editpwd"  plain="true" >修改个人信息</a>        
            <a class="mini-button mini-button-iconTop" iconCls="icon-date" onclick="onClick"  plain="true" >使用手册</a>        
            <a class="mini-button mini-button-iconTop" iconCls="icon-close" onclick="closewin()"  plain="true" >退出系统</a>        
        </div>
         </div>
         </div>
        </div>
        <div title="south" region="south" showSplit="false" showHeader="false" height="30">
           
           <div style="line-height: 28px;text-align: left;padding-left:5px; ">
           		当前用户：<%=user.getName() %>&nbsp;( 
           		<%
           		SystemOrganization org = user.getOrganization();
           		if(null != org){
           			%>
           		 <%=org.getName() %>
           			<%
           		}
           		
           		%>
           		
           		)<span style="padding-left:20px;"></span>
           		在线人数：<div id="onlineNumber" style="display:inline;"></div>
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
				<%=tbuserNotice.getTitle() %>
    		</b><br/>
    	</div>
		<div  id="noticeContent">
			<%=tbuserNotice.getContent() %>
		</div>	
    	</div>
	</div>			
    </div>
    <div showHeader="false" region="west" showSplitIcon="true"  width="180" maxWidth="250" minWidth="100" style="border: 0px;">
        <!--OutlookMenu-->
        <div id="leftTree" class="mini-outlooktree" url="menutree.json" onnodeclick="onNodeSelect"
            textField="name" idField="resid" parentField="presid"  expandOnLoad="true">
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
</body>
</html>
<script type="text/javascript">
        mini.parse();
        <%if(tbuserNotice.getNoticeid()!=null) {%>
        	show();
        	setInterval(function hideNoticeWindow() {
        	var win = mini.get("win1");
        	win.hide();
    	},30*1000) 
        <%}%>
        var mainTabs = mini.get("mainTabs");
        mainTabs.setTabPosition("bottom");
        function f1(){
        	alert(1);
        }
        function show(){
	        var win = mini.get("win1");
	        var x = "right";
	        var y = "bottom";
	        win.showAtPos(x, y);
        }
       // 可以用此方法展开用户最常用的菜单
       function expandMenu(nodeId){
            var tree = mini.get("leftTree");
            if(!tree){
            	return;
            }
            var node = tree.getNode(nodeId);
            if(!node){
            	return;
            }
            tree.selectNode(node["smparentid"]);
            tree.expandPath(nodeId);
       };
       expandMenu("200");
        
        function showTab(node) {
        	if(!node || !node.srurl){
        		return;
        	}
            var tabs = mini.get("mainTabs");
            var id = "tab$" + node.resid;
            var tab = tabs.getTab(id);
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
            		alert("标签页打开过多，请在标签页上点击鼠标右键关闭不使用的标签");ka
            		return false;
            	}
            	else{
            		tab = {};
	                tab.name = id;
	                tab.title = node.name;
	                tab.showCloseButton = true;
	                var srurl = node.srurl;
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
		                theURL += '&menuid='+node.resid + "&__r=" + randomVal;
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
            	if(node.remark){
            		if(node.remark=='@gj' && (<%=sdalarmlevel%>!='6' && <%=sdalarmlevel%>!='7'&& <%=sdalarmlevel%>!='4')){
            			alert("您没有权限，请用监区用户登录！");
            			return;
            		}else if(node.remark=='@ks' && (<%=sdalarmlevel%>!='5' && <%=sdalarmlevel%>!='4')){
            			alert("您没有权限，请用科室用户登录！");
            			return;
            		}else if(node.remark=='@jy' && (<%=sdalarmlevel%>!='4')){
            			alert("您没有权限，请用监狱用户登录！");
            			return;
            		}else if(node.remark=='@sj' && (<%=sdalarmlevel%>!='66')){
            			alert("您没有权限，请用省局用户登录！");
            			return;
            		}else if(node.remark=='@fy' && (<%=sdalarmlevel%>!='10' && <%=sdalarmlevel%>!='11' && <%=sdalarmlevel%>!='12')){
            			alert("您没有权限，请用法院用户登录！");
            			return;
            		}else if(node.remark=='@jcy' && (<%=sdalarmlevel%>!='2')){
            			alert("您没有权限，请用检查院用户登录！");
            			return;
            		}
            	}
            	
                showTab(node);
            }
			var resid = node.resid;
			if(resid){
				// 保存本地 cookie
				document.cookie="favorite_resid="+escape(resid); 
			}
        }
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
                	//alert(action);
                	/*if(action=='save'){
	                   mini.confirm("修改密码成功，是否重新登录？", "确定？",
				            function (action) {            
				                if (action == "ok") {
				                     window.parent.location.href="<%=path%>"; 
				                } 
				            }
				        );
                    }*/
                }
            });
        }
        
      function closewin(){
      /*
      	mini.confirm("确定退出当前系统？", "确定？",
            function (action) {            
                if (action == "ok") {
                     window.parent.location.href="<%=path%>"; 
                } 
            }
        );*/
        if(confirm("确定退出当前系统？")){
        	window.parent.location.href="logout.action?1=1"; 
        	//window.parent.location.href="<%=path%>"; 
        	//window.close();
        } 
      }  
         function onClick(e) {
         	mini.parse();
            var text = this.getText();
          	var node =  O('publicShowExplain.action?1=1',text,'7532');
            showTab(node);
        }
        function O(smurl,smdiscribe,smid){
        	this.smurl=smurl;
			this.smdiscribe=smdiscribe; 
			this.smid=smid; 
			return this; 
		} 
 //////////////////////////////////////////
 		//var tabs = mini.get("tabs1");
        var currentTab = null;

        function onBeforeOpen(e) {
            currentTab = mainTabs.getTabByEvent(e.htmlEvent);
            if (!currentTab) {
                e.cancel = true;                
            }
        }

        ///////////////////////////
        function closeTab() {
            mainTabs.removeTab(currentTab);
        }
        function closeAllBut() {
            mainTabs.removeAll(currentTab);
        }
        function closeAll() {
        	var but = [];  
        	but.push(mainTabs.getTab("first"));
            mainTabs.removeAll(but);
        }
        function closeAllButFirst() {
            var but = [currentTab];            
            but.push(mainTabs.getTab("first"));
            mainTabs.removeAll(but);
        }
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
		}
		
		function addsearchtabs(id,key,zaiyacombo1,sdid){
			var tab = {};
			tab.name = id;
            tab.title = '快速检索';
            tab.showCloseButton = true;
           // tab.url ="gotofastCriminalJianSuo.action?key="+key+"&zaiyacombo1="+zaiyacombo1+"&sdid="+sdid;
            return tab;
		}
		
		function mianbanload(e)
		{
			if(e.tab.name=='tab$fastsearch')
			{
				var key = mini.get("key").getValue();
				var zaiyacombo1=mini.get("zaiyacombo1").getValue();
				var sdid=mini.get("sdid").getValue();
				var tabs = mini.get("mainTabs");
				var iframe=tabs.getTabIFrameEl(e.tab);
				var data={key:key,zaiyacombo1:zaiyacombo1,sdid:sdid};
				iframe.contentWindow.SetData(data);
			}
		}
		function onKeyEnter(e) {
            search();
        }
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        //在线人数
        $(function(){
			function show_usercount(){
				try{
			   		showcon();
				} catch(ex){
					
				}
			}
			//setInterval(show_usercount, 1*60*1000);
			//
			//提醒功能
			function show_message(){
				try{
			   		showMessage();
				} catch(ex){
				}
			}
			//setInterval(show_message, 1*60*1000);
		});
		function showcon(){
			$.ajax({
	                url: "getUserCount.action?1=1",
	                data: {	
	                		},
	                cache: false,
	                type: "post",
	                success: function (text) {
	                	document.getElementById("onlineNumber").innerHTML = text;
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                }
	          });
		 }
		 
		 //提醒
		 function showMessage(){
			$.ajax({
	                url: "getMessage.action?1=1",
	                data: {	
	                		},
	                cache: false,
	                type: "post",
	                success: function (text) {
	                		if(text){
		                    	var o = mini.decode(text);
		                		$.messager.lays(400, 200);
		                		if(o.length == 2){
		                			document.getElementById("noticeid").value=o[0].noticeid+'@'+o[1].noticeid;
			                		var message1 = o[0].message;
			                		var message2 = o[1].message;
			                		var message = message1+"<br/>"+message2;
			                		$.messager.show("来消息了！", message, 0);
		                		}else if(o.length>0){
		                			document.getElementById("noticeid").value=o[0].noticeid;
		                			var message = o[0].message;
			                		$.messager.show("来消息了！", message, 0);
		                		}
	                		}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                }
	          });
		 }
		 $(document).ready(function(){
			  showcon();
			//  showMessage();
		});
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
		}
		function selectPrevNode(){
			//获取cookie字符串 
			var strCookie=document.cookie; 
			//将多cookie切割为多个名/值对 
			var arrCookie=strCookie.split("; "); 
			var resid = null; 
			//遍历cookie数组，处理每个cookie对 
			for(var i=0;i<arrCookie.length;i++){ 
				var arr=arrCookie[i].split("="); 
				//找到cookie，并返回它的值 
				if("favorite_resid"==arr[0]){ 
					resid=arr[1]; 
					break; 
				} 
			} 
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
				expand(tree,node);
				//tree.expandNode ( pnode );
				//tree.expandNode ( node );
				//debug(node);
			}
		};
		// 执行
		selectPrevNode();
		// 展开树
		function expand(tree, node){
	    	if(!node || !tree || !node.resid){
	    		return;
	    	}
	    	tree.expandPath(node.resid);
	    	/*
	    	var presid = node.presid;
	    	if(presid && presid != "0" && presid != "-1"){
			    var pnode = tree.getNode ( node.presid );
			    expand(tree, pnode);
	    	}
		    tree.expandNode(node);
		    */
		}; 
		
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
		}
		function hideNoticeWindow() {
        	var win = mini.get("win1");
        	win.hide();
    	}
    	
    	
 window.onbeforeunload = onbeforeunload_handler;//当点击浏览器×时 提示下是否关闭。   
	function onbeforeunload_handler(){   
	    var warning="确定要退出系统吗?";           
	    return warning;   
	}
</script>
 