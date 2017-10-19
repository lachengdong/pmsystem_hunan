<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//
Object _userid = request.getAttribute("userid");
%>
<html>
  <head>
    <title>用户-部门</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
    html,body
    {
        padding:0;
        margin:0;
        border:0;
        width:100%;
        height:100%;
        overflow:hidden;
    }
    </style>
  </head>
  <body>
    <input id="updatetime" name="updatetime" class="mini-hidden" />
    <input name="createtime" class="mini-hidden" /> 
      
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" 
        borderStyle="border-left:0;border-bottom:0;border-right:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">保存</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    <div class="mini-fit">
        
        <ul id="tree1" class="mini-tree" style="width:100%;height:100%;" 
            showTreeIcon="true" textField="name" idField="orgid" parentField="porgid" resultAsTree="false"  
            expandOnLoad="0" onnodedblclick="onNodeDblClick" expandOnDblClick="false" 
            >
        </ul>
    
    </div>     
    
<script type="text/javascript">
	// 
	var userid = '<%=_userid %>';
	// 解析
    mini.parse();
	// 获取树对象
    var tree = mini.get("tree1");
	// 加载数据
    tree.load("ajax/getOrgTreeByUser.json");
	// 获取选中节点
    function GetData() {
        var node = tree.getSelectedNode();
        return node;
    };
    function onNodeDblClick(e) {
        onOk();
    };
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    };
    
    function onCancel() {
        CloseWindow("cancel");
    };
    // 确定
    function onOk() {
    	//
        var node = GetData();
        if(!node){
            alert("请选择部门!");
            return;
        }
    	var orgid = node.orgid; //获取选中的orgid
    	//
    	var url = "ajax/saveOrgForUser.json?1=1";
    	var data = {userid: userid, orgid: orgid};
    	var successCallback = function (message) {
                CloseWindow("ok");
            };
    	var errotCallback = function (jqXHR, textStatus, errorThrown) {
            	alert("操作失败!");
            };
        // 请求AJAX
        requestAjax(url,data,successCallback,errotCallback);
    };
    
	// 请求AJAX,工具方法
	function requestAjax(url, data, successCallback, errotCallback){
		// 执行AJAX请求
		$.ajax({
		    url: url,
		    data: data,
	        type: "post",
		    success: function (message) {
		    	/*
		    	if(window["JSON"]){
		    		message = mini.decode(message) || {};
		    	} else { // IE6, IE7
        	   		message = eval("("+ message + ")") || {};
		    	}
		    	*/
		    	message = mini.decode(message) || {};
		   		if(successCallback){
		    	   successCallback.call(window, message);
		   		}
            	return false;
		    },
		    error: function (jqXHR, textStatus, errorThrown) {
		    	// 把错误吃了
		       if(errotCallback){
		    	   errotCallback.apply(window, arguments);
		       } else {
		    	   alert("操作失败!");
		       };
		    }
		});
	};
	//
	$(document).bind("keyup", function (e) {
    	var KEYCODE_ESC = 27;
    	var KEYCODE_ENTER = 13;
    	//
    	var keyCode = e.keyCode;
    	if(keyCode === KEYCODE_ESC){
    		// 执行钩子
    		onCancel();
    	}
    });
</script>
  </body>
</html>