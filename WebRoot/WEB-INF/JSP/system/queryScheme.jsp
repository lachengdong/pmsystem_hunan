<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>查询方案</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    .s{
    	width:100%;height:100%;_height:94.8%;
    }
    .mini-menu-border{
    	border:0;
    }
    </style>
</head>
<body>
    <input id="operator" name="operator" value="new" class="mini-hidden" />
    <input id="schemeid" name="schemeid" class="mini-hidden" />
    <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
    <div id="layout1" class="mini-layout" style="width:100%;height:100%;"> 
        <div title="查询方案" showSplitIcon="true"  expanded="false"  region="west" width="200px;" showCollapseButton="false" style="border-left:1px solid #90b5e3;">
			<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
	            <span style="padding-left:5px;">名称：</span>
	            <input class="mini-textbox" vtype="maxLength:30;" id="key" name="key" style="width:80px;" onenter="onKeyEnter"/>
	            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="searchOrg()">查找</a>                  
	        </div>
            <div class="mini-fit">
                <ul id="tree1" class="mini-tree" url="ajaxShecmeAll.action?1=1" style="width:100%;height:100%;"
                    showTreeIcon="true" textField="name" idField="planid" parentField="pplanid" resultAsTree="false" 
                    expandOnNodeClick="true" showArrow="true">        
                </ul>
            </div>
        </div>
        <div showCollapseButton="false">
            <ul id="menu2" class="mini-menubar" style="width:100%;">
		        <li  iconCls="icon-remove" onclick="deletesheme()">删除</li>
		        <li  iconCls="icon-add" onclick="addscheme()">新增</li>
			    <li  iconCls="icon-edit" onclick="updatesheme()">修改</li>
		        <li iconCls="icon-save" onclick="saveData()">保存</li>
		        <li> <div id="schemeTypeId" style="display:inline;"></div></li>
            </ul>
            <div class="mini-fit">
                <div id="form1" class="mini-form" size="500">
                    <fieldset style="width:99%;height:98%;position:relative;padding:0px;border:0;">
                        <div style="padding:5px;">
                            <table style="width:100%;">
						        <tr>
						            <td width="80px;">操作类型：</td>
						            <td align="left">
                                        <input id="caozuo" name="caozuo" class="mini-radiobuttonlist" data="[{id: '1', text: '查询'},{id: '2', text: '新增'},{id: '3', text: '删除'},{id: '4', text: '修改'}]"
                                            value="0"/>
						            </td>
						        </tr>
						        <tr>
						            <td width="80px;">查询条件：</td>
						            <td align="left"><input id="sql" name="sql" maxlength="6000" class="mini-textarea" style="width:100%;height:420px;"  /></td>
						        </tr>
                            </table>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
	 	var layout = mini.get("layout1");
		var tree = mini.get("tree1");//方案树
		var form = mini.get("form1");
		$(document).ready(function(){
		  	layout.updateRegion("west", { expanded: true });
		});
		var rbl = mini.get("caozuo");
        rbl.on("valuechanged", function (e) {
            //alert(this.getValue());
        });
        tree.on("nodeselect", function (e) {
        	var url="getSchemeSqlByPlanId.action?1=1&planid="+e.node.planid;
            //form.loading();
            $.ajax({
                 url: url,
                 type: "post",
                 success: function (text) {
                    var o =  mini.decode(text);
                    if(o == ""){
                        mini.get("caozuo").setValue("0");
                        mini.get("sql").setValue("");
                    }else{
                        mini.get("caozuo").setValue(o.opsign);
                        mini.get("sql").setValue(o.sqltext);
                    }
                 }
             });  
        });
        //新增方案
        function addscheme(){
        	 var node = tree.getSelectedNode();
        	 if(node){
	        	 mini.open({
	                url: "toNewSchemePage.action?1=1",
	                title: "新增方案", width: 350, height: 360,
	                showMaxButton: true,
		            allowResize: false, 
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { action: "new", planid:node.planid, name:node.name};
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action) {
	                	tree.reload();//刷新左侧树
	                }
	            });
        	 }else{
	        	 mini.open({
	                url: "toNewSchemePage.action?1=1",
	                title: "新增方案", width: 350, height: 360,
	                showMaxButton: true,
		            allowResize: false, 
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { action: "new", planid:-1,name:""};
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action) {
	                	tree.reload();//刷新左侧树
	                }
	            });
        	 } 
        }
        //修改方案
        function updatesheme(){
        	var node = tree.getSelectedNode();
        	if(!node){
        	    alert("请选择需要修改的查询方案！");
        	}else{
        	    var fathernode = tree.getParentNode(node);
        	    mini.open({
                    url: "toNewSchemePage.action?1=1",
                    title: "修改方案", width: 350, height: 360,
                    showMaxButton: true,
	                allowResize: false, 
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", planid:node.planid, name:node.name, 
                            fplanid:fathernode.planid, fname:fathernode.name ,resid:node.resid,datarelation:node.datarelation,reportortemp:node.reportortemp};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                	    tree.reload();//刷新左侧树
                    }
                });
        	}
        }
        //删除方案
        function deletesheme(){
            var node = tree.getSelectedNode();
        	if(!node){
        	    alert("请选择需要删除的查询方案！");
        	}else if(!tree.isLeaf(node)){
        	    alert("不能删除父查询方案！");
        	}else{
        	    var url="delQueryScheme.action?1=1&planid="+node.planid;
                $.ajax({
                     url: url,
                     data: {},
                     type: "post",
                     success: function (text) {
                        if(text == 1){
                		    alert("删除成功！");
                	    }else{
                		    alert("删除失败!");
                	    }
                	    tree.reload();//刷新左侧树
                	    mini.get("caozuo").setValue("0");
                        mini.get("sql").setValue("");
                     }
                }); 
        	}
        }
        //保存查询方案SQL
        function saveData(){
            var node = tree.getSelectedNode();
        	if(!node){
        	    alert("请选择查询方案！");
        	    return;
        	}
        	if(mini.get("caozuo").getValue() == "0"){
        	    alert("请选择操作类型！");
        	    return;
        	}
            var url="saveQuerySchemeSql.action?1=1&planid="+node.planid;   
            $.ajax({
                 url: url,
                 data: {caozuo:mini.get("caozuo").getValue(), sql:mini.get("sql").getValue()},
                 type: "post",
                 success: function (text) {
                    if(text == 1){
                		alert("保存成功！");
                	}else{
                		alert("保存失败!");
                	}
                 }
            }); 
        }
        function searchOrg() {
        	var key = mini.get("key").getValue();
			tree.load({ key: key });
        	/*
        	var tree = mini.get("tree1");
            var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase(); 
                tree.filter(function (node) {
                    var text = node.name?node.name.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
            */
        }
        function onKeyEnter(e) {
            searchOrg();
        }
    </script>
</body>
</html>