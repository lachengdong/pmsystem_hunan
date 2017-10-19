<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//
Object fromid = request.getParameter("fromid");
if(StringNumberUtil.isEmpty(fromid)){
	fromid = "";
}
%>
<html>
  <head>
    <title>选择资源</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
  <!-- 隐藏四字段，已经存在的不写 创建人和更新人 更新时间 -->
    <input name="createmender" class="mini-hidden" />
    <input name="updatemender" class="mini-hidden" />
    <input id="updatetime" name="updatetime" class="mini-hidden" />
    <input name="createtime" class="mini-hidden" />
    <div id="div_one"  style="width:100%;height:100%;">
     <input id="id" class="mini-hidden"/>
      <div class="mini-toolbar" style="padding:0px;border:0;text-align:left" >
      		<table style="width:100%;">
      		<tr>
      			<td>
      			   <input id="onlysub" name="onlysub" type="checkbox"/> 只拷贝子节点
		           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
		           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          	<%--
		          	<input id="key" class="mini-textbox" emptyText="请输入关键字" style="width:120px;" onenter="onKeyEnter"/>
		          <a class="mini-button" style="width:60px;"  plain="true" onclick="search()">查询</a>
		           --%>
      			</td>
      		</tr>
          </table>
    </div>
    <div class="mini-fit">
        <ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
        	url = "ajax/list.json"
            showTreeIcon="true" textField="name" idField="resid" parentField="presid" resultAsTree="false"
            showCheckBox="false" expandOnLoad="false" allowSelect="true"  showFolderCheckBox="true"
            checkRecursive="false" autoCheckParent="true"
            ondrawnode="onDrawNode"
            >
        </ul>
    </div>
    </div>
<script type="text/javascript">
	//
	var fromid = "<%=fromid %>";
	//
    mini.parse();
    var tree = mini.get("tree1");
    //
    expand(tree, fromid);
    //
    function GetData() {
	    var node = tree.getSelectedNode() || {};
	    var onlysub = $("#onlysub").prop("checked");
        return {
        	resid : node.resid
        	,
        	onlysub : (onlysub ? 1 : 0)
        };
    };
    function SetData(data) {
    };
    function onDrawNode(e) {
	    var tree = e.sender;
	    var node = e.node;
	
	    var hasChildren = tree.hasChildren(node);
	    //
	    //
	    node = node || {};
	    var checked = node.checked;
	    var name = node.name || "";
	    var remark = node.remark;
	    // 有备注
	    if(remark){
	    	if(remark.substr){
	    		//
	    		var len =  remark.length;
	    		var maxLen = 20;
	    		if(len > maxLen){
	    			remark = remark.substr(0,maxLen) + "...";
	    		}
	    	}
	    	e.nodeHtml = name + "("+ remark +")";
	    }
	};

	// 展开树
	function expand(tree, resid){
    	if(!tree || !resid){
    		return;
    	}
    	var node = tree.getNode (resid );
    	if(!node){
    		return;
    	}
    	
    	var presid = node.presid;
    	if(presid && presid != "0" && presid != "-1"){
		    var pnode = tree.getNode ( node.presid );
		    expand(tree, pnode);
    	}
	    tree.expandNode(node);
	}; 
    //////////////////////////////////
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    };
    function onOk() {
        var resid = GetData();
        if(!resid){
        	alert("请选择目的节点");
        	return false;
        }
    	CloseWindow("ok");
    };
    function onCancel() {
        CloseWindow("cancel");
    };
</script>
  </body>
</html>