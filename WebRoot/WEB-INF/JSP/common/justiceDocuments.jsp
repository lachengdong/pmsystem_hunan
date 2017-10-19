<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title></title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<script type="text/javascript">
	 	mini.parse();
	 	top["win"]=window;
	 	  //标准方法接口定义
        function SetData(data) {//此方法子tab会调用 用来传递参数
        }
		function CloseWindow(action) { 
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        } 
      	function fanhui() {
      		CloseWindow("close");
      	}
      	function onbeforeactivechanged(e) {
            var tab = e.tab;
            if(tab.name==2){
            	var flowdraftid = mini.get("flowdraftid").getValue();
            	var url = "checkFlwsText.action?flowdraftid="+flowdraftid+"&tempid=courtSentence";
            	mini.get("tabs").updateTab(tab,{url:url})
            	mini.get("tabs").activeTab(tab);
            	mini.get("tabs").reloadTab (tab);
            }else if(tab.name==3){
            	var flowdraftid = mini.get("flowdraftid").getValue();
            	var url = "getAnJianBigData.action?1=1&flowdraftid="+flowdraftid+"&doctype=XFZX_FYLAB&backaction=closeparent";
            	mini.get("tabs").updateTab(tab,{url:url})
            	mini.get("tabs").activeTab(tab);
            	mini.get("tabs").reloadTab (tab);
            }
            
        }
        //设置隐藏值
        function setHiddenValue(hid,hvalue){
        	if(mini.get(hid)){
        		mini.get(hid).setValue(hvalue);
        	}
        }
     </script>
<body >
	<input id="flowdefid" name="flowdefid" value="${flowdefid}" class="mini-hidden" />
	<input id="flowdraftid" name="flowdraftid" value="${flowdraftid}" class="mini-hidden" />
	<input id="tempid" name="tempid" value="${tempid}" class="mini-hidden" />
	
	<div id="tabs"  class="mini-tabs"  activeIndex="0" style="width:100%;height:100%;" onbeforeactivechanged="onbeforeactivechanged" bodyStyle="padding:0;border:0;" buttons="#tabsButtons">
		 <div id="tab1" name="1"  title="合议笔录" url="toSuggestionDocument.page?1=1&flowdefid=${flowdefid}&casetype=xzz&tempid=${tempid}" refreshOnClick="false" ></div>  
		 <div id="tab2" name="2"  title="裁定书" url="" refreshOnClick="true" ></div>
		 <div id="tab3" name="3"  title="审批表" url="" ></div>
	</div>
</body>
</html>