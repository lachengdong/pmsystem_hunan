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
    <title>选择单个方案</title>
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
    <div id="div_one"  style="width:100%;height:100%;">
     <input id="id" class="mini-hidden"/>
      <div class="mini-toolbar" style="padding:0px;border:0;text-align:left" >
      		<table style="width:100%;">
      		<tr>
      			<td>
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
        	url = "getSolutionSchemeTree.json"
            showTreeIcon="true" textField="solutionname" idField="solutionid" parentField="solutionpid" resultAsTree="false"
            showCheckBox="false" expandOnLoad="false" allowSelect="true"  showFolderCheckBox="true"
            checkRecursive="false" autoCheckParent="true"
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
	    var node = tree.getSelectedNode();
        return node;
    };
    function SetData(data) {
    };

	// 展开树
	function expand(tree, id){
    	if(!tree || !id){
    		return;
    	}
    	var node = tree.getNode (id );
    	if(!node){
    		return;
    	}
    	
    	var solutionpid = node.solutionpid;
    	if(solutionpid && solutionpid != "0" && solutionpid != "-1"){
		    var pnode = tree.getNode ( node.solutionpid );
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
        var node = GetData();
        if(!node){
        	alert("请选择目的节点");
        	return false;
        }
        //
        var solutiontype = node.solutiontype || "";
        if(!solutiontype){ // solutiontype 不能为0
        	alert("只能选择目录");
       	 	return false;
        }
        //
    	CloseWindow("ok");
    };
    function onCancel() {
        CloseWindow("cancel");
    };
</script>
  </body>
</html>