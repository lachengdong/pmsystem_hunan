<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程明细</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
body {
	font-size: 12px;
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
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<a class="mini-button" plain="true" iconCls="icon-add"
				onclick="add();">新增</a>&nbsp;&nbsp;
			<a class="mini-button" plain="true" iconCls="icon-remove"
				onclick="delebath();">批量删除</a>&nbsp;&nbsp;
			<a class="mini-button" plain="true" iconCls="icon-add"
				onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">公共配置流程此处暂不能更新保存</font>
		</div>
		<div class="mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				style="width: 100%; height: 100%;" showFilterRow="false" allowCellSelect="true"
				 allowCellEdit="true" multiSelect="true"
				url="getFlowById.action?1=1&flowid=${flowid}">
				<div property="columns">
				 	<div type="checkcolumn"></div>
					<div field="resid" width="5%" headerAlign="center" algin="center" allowSort="false">
						按钮id
						<!-- <input property="editor" class="mini-textbox" style="width: 10%%;" /> -->
					</div>
					<div field="snodeid" name="snodeid" width="8%"
						headerAlign="center" align="center" allowSort="true">
						源节点id
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="dnodeid" width="8%" headerAlign="center" align="center" allowSort="false">
						目的节点id
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="explain" width="8%" headerAlign="center" align="center" allowSort="false">
						流转说明
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="state" width="5%" headerAlign="center" align="center"
						allowSort="false">
						流转状态
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="flowdefid" width="10%" headerAlign="center"
						align="center" allowSort="false">
						流程id
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="text1" width="10%" headerAlign="center"
						align="center" allowSort="false">
						意见栏编辑字段
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="text2" width="10%" headerAlign="center"
						align="center" allowSort="false">
						审批人编辑字段
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="text3" width="10%" headerAlign="center"
						align="center" allowSort="false">
						流程描述
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="opid" width="5%" headerAlign="center"
						align="center" allowSort="false">
						操作ID
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="departid" width="5%" headerAlign="center"
						align="center" allowSort="false">
						单位ID
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="remark" width="20%" headerAlign="center" align="center"
						allowSort="false">
						备注
						<input property="editor" class="mini-textbox" style="width: 10%%;" />
					</div>
					<div field="" width="10%" headerAlign="center" align="center">操作</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();
		var grid=mini.get("datagrid1");
		grid.load();
		function add(){
			mini.open({
				url:"addFlowAction.action?1=1&flowid=${flowid}",
				title:"新增流程",width:350,height:450,
				showMaxButton:true,
				allowResize: false,
				onload:function (){
					 var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            //iframe.contentWindow.SetData(data);
				},
				ondestroy:function (){
					grid.load();
				}
			});
		}
		
		function delebath(){
			
			var rows = grid.getSelecteds();
			if(rows.length>0){
				var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.resid);
                }
                var id = ids.join(',');
                mini.confirm("警告！此操作会删除流程！确认删除选中记录？","",function temp(action){
                	if(action=='ok'){
	               	$.ajax({
					url:"removeFlow.action?1=1&flowid=${flowid}",	
					data:{resid:id},
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
		
		function save(){
			var data = grid.getChanges();
            var json = mini.encode(data);
            var url="saveChangedFlow.action?1=1&flowid=${flowid}";
            $.ajax({
            	url:url,
            	data:{data:json},
            	type:"post",
            	success: function(text){
            		var value=eval(text);
            		if(value<2){
            		mini.alert("保存成功！");
            		grid.load();
            		}else{
            			alert("操作失败！公共配置流程暂不支持页面操作维护！按钮id冲突！")
            		}
            	},
            	error: function (jqXHR, textStatus, errorThrown){
            		mini.alert("保存失败！");
            	}
            });
		}
	</script>
	</body>
</html>
