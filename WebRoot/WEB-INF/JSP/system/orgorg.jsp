<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ORG_ORG表维护页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>
	</head>
	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			上级部门：<input id="fromid2" name="fromid2" class="mini-textbox" width="80px" emptyText="输入部门编号" /> 
			&nbsp;&nbsp;
			上级部门：
			<input id="fromid" class="mini-treeselect" parentField="porgid" 
				url="<%=path%>/org/ajax/list.action?1=1" multiSelect="false" emptyText="选择部门"
				textField="name" idField="orgid" oncloseclick="onCloseClick"
				valueField="orgid" showFolderCheckBox="false" expandOnLoad="true"
				showClose="false" popupWidth="150px" style="width: 150px;" />
			可操作部门：
			<input id="toid" class="mini-treeselect"
				url="<%=path%>/org/ajax/list.action?1=1" multiSelect="true"
				textField="name" idField="orgid" parentField="porgid"
				checkRecursive="true" oncloseclick="onCloseClick" emptyText="选择可操作部门"
				showFolderCheckBox="true" expandOnLoad="false" showClose="true"
				valueField="orgid" popupWidth="200" style="width: 200px;" />&nbsp;&nbsp;
			<a class="mini-button" plain="true" iconCls="icon-add"
				onclick="create();">生成数据</a>&nbsp;&nbsp;
				<a class="mini-button" plain="true" iconCls="icon-remove"
				onclick="del();">删除</a>
				<input id="key" class="mini-textbox" style="width:80" emptyText="组织机构ID" onenter="search"/>
				<a class="mini-button" plain="true"  iconCls="icon-search" onclick="search();">查询</a>
		</div>
		<div class="mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				style="width: 100%; height: 100%;" url="getOrgInfo.action"
				allowAlternating="true" idField="" sizeList="[10,20,50,100]"
				pageSize="20" multiSelect="true">
				<div property="columns">
					<div type="checkcolumn" width="20px"></div>
					<div type="indexcolumn" width="30px" headerAlign="center" align="center">序号</div>
					<div field="orgid" name="orgid" width="" headerAlign="center" align="center"
						allowSort="false">
						组织机构id
					</div>
					<div field="dorgid" name="snodeid" width=""
						headerAlign="center" align="center" allowSort="false">
						能处理事务的组织机构
					</div>
					<div field="optime" name="snodeid" width="" dateFormat="yyyy-MM-dd HH:mm:ss"
						headerAlign="center" align="center" allowSort="false">
						操作时间
					</div>
					<div field="opid" name="snodeid" width=""
						headerAlign="center" align="center" allowSort="false">
						操作员ID
					</div>
					<div field="remark" name="snodeid" width="30%"
						headerAlign="center" align="left" allowSort="false">
						部门可操作关系描述
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
    	mini.parse();
    	var grid=mini.get("datagrid1");
    	grid.load();
    	
    	
    	function create(){
    		var fromid=mini.get("fromid").getValue();
    		var toid=mini.get("toid").getValue();
    		var fromid2=mini.get("fromid2").getValue();
    		if(fromid==''&&fromid2==''){
    			mini.alert("请输入或选择上级部门ID!");
    			return ;
    		}
    		if(toid==''){
    			mini.alert("请选择可操作部门！");
    			return;
    		}
    		var url="createOrgOrg.action";
    		mini.confirm("确认操作？","",function temp(action){
	    		if(action=='ok'){
	    			$.ajax({
		    			url:url,
		    			data:{fromid:fromid,toid:toid,fromid2:fromid2},
		    			type:"post",
		    			success:function(text){
		    				mini.alert("操作成功！");
		    				grid.load();
		    			},
		    			error:function (jqXHR, textStatus, errorThrown){
		    				mini.alert("操作失败！");
		    			}
		    		});
	    		}
    		});
    	}
    	
    	function del(){
    		var rows = grid.getSelecteds();
			if(rows.length>0){
				var orgids = [];
				var dorgids=[];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    orgids.push(r.orgid);
                    dorgids.push(r.dorgid);
                }
                var orgid = orgids.join(',');
                var dorgid= dorgids.join(',');
                mini.confirm("此操作需谨慎，确认删除选中记录？","",function temp(action){
                	if(action=='ok'){
	               	$.ajax({
					url:"removeOrgOrg.action",	
					data:{orgid:orgid,dorgid:dorgid},
					type:"post",
					success:function(text){
						mini.alert("操作成功！");
						grid.load();
					},
					error:function (jqXHR, textStatus, errorThrown){
						mini.alert("操作失败！");
					}		
					});
					}
                });
                
			}else{
				mini.alert("请至少选择一条记录！");
			}
    	}
    	  
	function onCancel(action) {
		if (window.CloseOwnerWindow)
			return window.CloseOwnerWindow(action);
		else
			window.close();
	}
	//清空内容
	function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
        function search(){
        	var key=mini.get("key").getValue();
        	grid.load({key:key});
        }
</script>
	</body>
</html>