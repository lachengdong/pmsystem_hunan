<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String sdalarmlevel = (String)request.getSession().getAttribute("sdalarmlevel");
String basicdep = (String)request.getSession().getAttribute("basicdep");
%>

<html>
  <head>
    <title>刑罚执行协同工作平台</title>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
   <script src="<%=path%>/js/jquery.messager.js" type="text/javascript"></script> 
    <link href="<%=path%>/css/top.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
	body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    #time{
        position: absolute;
        right: 1px;
        top: 2px;
        color: blue;
    } 
	</style>

</head>
 <body>
 <input id="noticeid" type="hidden" />
   <div class="mini-layout" style="width:100%;height:100%">
        <div title="north" region="north" class="header"  height="76" showHeader="false" showSplit="false" >
         <div class="top">
            <img src="<%=path%>/images/xingfayy_new.jpg" />
            <div class="right">
             <img src="<%=path%>/images/xingfayy_07.jpg" />
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
           		当前用户：<s:property value="#session.logigUserName" />&nbsp;(<s:property value="#session.sddiscribe"/>)<span style="padding-left:20px;"></span>
           		在线人数：<div id="onlineNumber" style="display:inline;"></div> 
           		<!-- 
           		<s:bean id="countBean" name="com.gkzx.systemManage.bean.SessionCounter">
	          	 在线人数：<s:property value="#countBean.num"/>
             	</s:bean>
             	 -->
           		</div>
           <div id="time" style="margin-right: 10px;">
				快速检索
				<input id="zaiyacombo1" class="mini-combobox" style="width:100px;" textField="text" valueField="id" emptyText="请选择..."   url="ajaxCodeShuJu.action?sctid=GK069" value="1"  required="true" allowInput="true" showNullItem="true" nullItemText="请选择..."/> 
				<input name="sdid" id="sdid" class="mini-combobox" style="width:110px;" valueField="sdid" textField="sddiscribe" url="getDepartList.action?1=1&qtype=jianqu" required="false" emptyText="所在部门" />
				<input class="mini-textbox" id="key" class="mini-textbox" emptyText="编号、姓名、拼音"  onenter="onKeyEnter"/>
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
			</div>
        </div>
    <div showHeader="false" region="west" showSplitIcon="true"  width="180" maxWidth="250" minWidth="100" >
        <!--OutlookMenu-->
        <div id="leftTree" class="mini-outlooktree" url="menutree.action" onnodeclick="onNodeSelect"
            textField="smdiscribe" idField="smid" parentField="smparentid"  expandOnLoad="false">
        </div>
    </div>
     <div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
          <div showCollapseButton="false" style="border:0;width: 100%;height: 100%">
             <div id="mainTabs" class="mini-tabs" contextMenu="#tabsMenu" ontabload="mianbanload" activeIndex="0" style="width:100%;height:100%;"      
                  plain="false">
                 <div name="first" title="我的桌面"  url="<%=path%>/JSP/main/desktop.jsp">        
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
       
        var mainTabs = mini.get("mainTabs");
       mainTabs.setTabPosition("bottom");
        
        function showTab(node) {
            var tabs = mini.get("mainTabs");
            var id = "tab$" + node.smid;
            var tab = tabs.getTab(id);
            tabs.reloadTab(tab);
            if (!tab) {
            	var thelength=tabs.getTabs().length;
            	if(thelength>12)
            	{
            		//alert("标签页不能超过8个，请先关闭不使用的标签");
            		alert("标签页打开过多，请在标签页上点击鼠标右键关闭不使用的标签");
            		return false;
            	}
            	else{
            		tab = {};
	                tab.name = id;
	                tab.title = node.smdiscribe;
	                tab.showCloseButton = true;
	                tab.url =node.smurl+'&menuid='+node.smid;
	                tabs.addTab(tab);
            	}
            }
            tabs.activeTab(tab);
            tabs.setTabPosition("bottom");
        }
        function onNodeSelect(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;
            if (isLeaf) {
            	if(node.smid=='10234' && node.smdiscribe=='考核考勤月报表' && <%=sdalarmlevel%>!='6' && (<%=basicdep%>=='6103' || <%=basicdep%>=='6100')){//陕西计分考核考核考勤月报表
            		alert("您没有权限，请用监区用户登录！");
            		return;
            	}else if(node.smid=='10235' && node.smdiscribe=='劳动考勤管理' && <%=sdalarmlevel%>!='6' && (<%=basicdep%>=='6103' || <%=basicdep%>=='6100')){
            		alert("您没有权限，请用监区用户登录！");
            		return;
            	}else{
                	showTab(node);
                }
            }
        }
         function editpwd() {
            mini.open({
                url:"changePwd.action",
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
        	window.parent.location.href="loginOut.action?1=1"; 
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
	         var tabs = mini.get("mainTabs");
            var id = "tab$fastsearch";
            var tab = tabs.getTab(id);
            var zaiyacombo1=mini.get("zaiyacombo1").getValue();
            var sdid=mini.get("sdid").getValue();
            if (!tab) {
            	var thelength=tabs.getTabs().length;
            	if(thelength>12)
            	{
            		alert("标签页打开过多，请在标签页上点击鼠标右键关闭不使用的标签");
            		return false;
            	}
            	else{
            		tab = {};
	                tab.name = id;
	                tab.title = '快速检索';
	                tab.showCloseButton = true;
	                tab.url ='gotofastCriminalJianSuo.action';
	                tabs.addTab(tab);
            	}
            }else{
            	tabs.reloadTab(tab);
            }
            tabs.activeTab(tab);
            tabs.setTabPosition("bottom");
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
			function show(){
			   showcon();
			}
			setInterval(show,50000);
		});
		//提醒功能
		
		$(function(){
			function show(){
			   showMessage();
			}
			setInterval(show,60000);
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
	                data: {	},
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
			  showMessage();
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
</script>
 