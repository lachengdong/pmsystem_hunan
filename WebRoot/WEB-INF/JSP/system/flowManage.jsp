<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>流程管理页面</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>

	</head>
	<body>
		<input name="createmender" class="mini-hidden" />
		<input name="updatemender" class="mini-hidden" />
		<input name="updatetime" class="mini-hidden" dateFormat="yyyy-MM-dd" />
		<input name="createtime" class="mini-hidden" dateFormat="yyyy-MM-dd" />
		<input class="mini-hidden" name="id" />
		<input class="mini-hidden" name="folderid" />
		<input id="state" type="hidden" />
		<input name="gkzxtempid" id="gkzxtempid" class="mini-hidden" />
		<input name="ciid" id="ciid" class="mini-hidden" />
		<div id="datagrid" class="mini-splitter"
			style="width: 100%; height: 100%;">
			<div size="240" showCollapseButton="true">
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<span style="padding-left: 5px;">名称：</span>
					<input class="mini-textbox" vtype="maxLength:50;" id="key"
						onenter="onKeyEnter" />
					<a class="mini-button" iconCls="icon-search" plain="true"
						onclick="search()">查找</a>
				</div>
				<div class="mini-fit">
					<ul id="tree1" class="mini-tree"
						url="ajaxGetTree.action?1=1"
						tyle="width: 100%; height: 100%;" showTreeIcon="true"
						textField="REMARK" idField="FLOWDEFID" parentField="" resultAsTree="false"  showArrow="true"  expandOnNodeClick="true" >
					</ul>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					
					
					<a class="mini-button" plain="true" iconCls="icon-add"
						onclick="addOtherFlow(8,'openFolderInsert',400,300,'','新增流程');">新增流程</a>
					
				</div>
				<div class="mini-fit">
					<input name="showid" id="showid" type="hidden" />
					<div id="datagrid1" class="mini-datagrid"
						style="width: 100%; height: 100%;" url="ajaxGetDataById.action" allowAlternating="true"
						idField="fmfileid" sizeList="[10,20,50,100]"
						pageSize="20" multiSelect="true">
						<div property="columns">
							<div type="checkcolumn"></div>
							
							<div field="resid" width="20%" headerAlign="center"
								align="center" allowSort="false">
								相关按钮
							</div>
							
							<div field="snodeid" width="20%" headerAlign="center"
								align="center"  allowSort="false">
								源节点id
							</div>
                            <div field="dnodeid" width="20%" headerAlign="center"
								align="center"  allowSort="false">
								目的节点id
							</div>
                            <div field="explain" width="20%" headerAlign="center"
								align="center"  allowSort="false">
								流转说明
							</div>
                            <div field="state" width="20%" headerAlign="center"
								align="center"  allowSort="false">
								流转状态
							</div>
							<div field="flowdefid" width="0%" headerAlign="center"
								align="center" allowSort="false">
								流程id
							</div>
							<div field="remark" width="0%" headerAlign="center"
								align="center"  allowSort="false">
								备注
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
<script type="text/javascript">
        mini.parse(); 
        var grid = mini.get("datagrid1");
        grid.sortBy("snodeid", "asc");
        grid.load();
        
       	var tree = mini.get("tree1");
       	//var upload = mini.get("uploadDiv");
       	var grid = mini.get("datagrid1");
        tree.on("nodeselect", function (e) {
        	url="ajaxGetFlowTreeData.action?1=1&id="+e.node.FLOWDEFID;
            grid.setUrl(url);
            grid.load();
        });

        function remove() {
            var rows = grid.getSelecteds();
            var node = tree.getSelectedNode();
            if(node && rows.length>0){
                    if (confirm("确定删除选中记录？")) {
                        var ids = [];
                        var names = [];
                        var paths = [];
                        
                        
                        for (var i = 0, l = rows.length; i < l; i++) {
                            var r = rows[i];
                            ids.push(r.FLOWDEFID);
                            names.push(r.EXPLAIN);
                            //paths.push(r.fmremark);
                        }
                        var id = ids.join(',');
                        var name = names.join(',');
                       // var path = paths.join(',');
                        
                        grid.loading("操作中，请稍后......");
                        $.ajax({
                            url: "deleteFlowByFileId.action",
                            type: "post",
                            data: {id:id,name:name},
                            success: function (text) {
                                grid.reload();
                            },
                            error: function () {
                                alert("操作失败！")
                            }
                        });
               } 
                }else{
                    if(!node){
                        alert("请选择需要删除的流程！");
                        }else if (confirm("确定要删除该流程以及该文件夹下所有文件？")) {
                	$.ajax({
                        url: "deleteFolderByFolderId.action?1=1&id="+node.id+"&pid="+node.pid,
                        type: "post",
                        data: {id:id},
                        success: function (text) {
                            tree.reload();
                        },
                        error: function () {
                        	alert("操作失败！")
                        }
                    });
                	 }
                    }
                	
        	
        }


        function addOtherFlow(id,method,w,h,menuid,title){
        	 		var node = tree.getSelectedNode();
        	 		if(!node){
        	 		alert("请选中节点");
        	 		return false;
        	 		}
        	 		var id=node.flowdefid;
        	 		//var text=encodeURIComponent(encodeURIComponent(tree.getText()));
            		var flowdefid = "";
            		var type="";
            		var remark="";
            		if(node){
            			flowdefid = node.FLOWDEFID;
            			type = "add";
            			remark = node.REMARK;
                		}
            	 	 mini.open({
            	 	 
                     url: "openFlowInsert.action",
                     showMaxButton: true,
                     allowResize: false,
                     title: title, width: w, height: h,
                     onload: function () 
                     {
                         var iframe = this.getIFrameEl();
                         var data = { action: "new",flowdefid:flowdefid,type:type,remark:remark};
                         iframe.contentWindow.SetData(data);
                     },
                     ondestroy: function (action) {
                    	 tree.reload();
                     }
                 });
                     }

        function updateOtherInfo(id,method,w,h,menuid,title){
        	var node = tree.getSelectedNode();
        	if(!node){
            	alert("请选择所需要修改的文件夹！");
            	}else{
            		 mini.open({
            	            url: "openFlowInsert.action",
            	            showMaxButton: true,
            	            allowResize: false,
            	            title: title, width: w, height: h,
            	            onload: function () 
            	            {
            	                var iframe = this.getIFrameEl();
            	                var data = { action: "edit",id : node.id};
            	                iframe.contentWindow.SetData(data);
            	            },
            	            ondestroy: function (action) {
            	                tree.reload();
            	            }
            	        });
                	}
            }
           
           
        function search() {
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
        }
        function onKeyEnter(e) {
            search();
        }



        function donew(){
            var node = tree.getSelectedNode();
            if(node){
                $("#iid").val(node.id);
            	document.getElementById("uploadDiv").style.display="";
                }else{
				alert("请选择目标文件夹！");
                    }
        	
        }
        function donen(){
        	document.getElementById("uploadDiv").style.display="none";
        }

      
    </script>
	</body>
</html>