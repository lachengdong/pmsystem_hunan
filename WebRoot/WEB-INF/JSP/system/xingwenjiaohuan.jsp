<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String type = request.getParameter("type");
	Calendar cal = Calendar.getInstance();
	int mon = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
%>
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
	<head>
		<title>行文交换页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
			type="text/css" />
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
		<script type="text/javascript">
	   	var confirmMessage = "请选中一条记录！";
	   	var confirmMessages = "请至少选中一条记录！";
	   	var batchAlert = "您要对以下所选服刑人员进行保外立案吗?";
    </script>
	</head>
	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width:100%;">
						源：<input id="fromid" class="mini-treeselect" emptyText="源单位" valueField="orgid" textField="name" url="getXingwenDepart.action?1=1"/>
					目标：<input id="toid" class="mini-treeselect" emptyText="此处选择监狱"  url="org/ajax/list.action?1=1" multiSelect="false" emptyText="选择部门"
				textField="name" idField="orgid" oncloseclick="onCloseClick" parentField="porgid" 
				valueField="orgid" showFolderCheckBox="false" expandOnLoad="true"
				showClose="false" popupWidth="150px" style="width: 150px;" />
					<a class="mini-button" plain="true" iconCls="icon-add"
						onclick="create();">交换行文</a>
					</td>
					<td style="white-space:nowrap;"><input id="key" class="mini-textbox" emptyText="单位名称,单位ID"  onenter="onKeyEnter"/>
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
					<input name="showid" id="showid" type="hidden" />
					<div id="datagrid1" class="mini-datagrid"
						style="width: 100%; height: 100%;" url="getXingWenData.action?1=1" allowAlternating="true"
						idField="field" sizeList="[10,20,50,100]"
						pageSize="20" multiSelect="true">
						<div property="columns">
							<div type="checkcolumn" width="5%"></div>
							<div type="indexcolumn" width="5%" headerAlign="center" align="center">序号</div>
							<div field="NAME" width="20%" headerAlign="center"
							align="center" allowSort="false">
								单位名称
							</div>
							<div field="CLASSIFICATION" width="5%" headerAlign="center"
								align="center" allowSort="false">
								类别
							</div>
							<div field="STATUS" width="10%" headerAlign="center"
								align="center" allowSort="false">
								状态
							</div>
							<div field="TEXT1" width="15%" headerAlign="center"
								align="center"  allowSort="false">
								行文1
							</div>
                            <div field="text2" width="15%" headerAlign="center"
								align="center"  allowSort="false">
								行文2
							</div>
                            <div field="TEXT3" width="15%" headerAlign="center"
								align="center"  allowSort="false">
								行文3
							</div>
						</div>
					</div>
				</div>

		<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        function create(){
        	var fromid=mini.get("fromid").getValue();
        	var toid=mini.get("toid").getValue();
        	if(fromid==''||toid==''){
        		mini.alert("请选择对应单位id");
        		return ;
        	}
        	var url="copyXingWen.action?1=1";
        	mini.confirm("此操作会覆盖数据，确认操作？","",function test(action){
        		if(action=='ok'){
	        	$.ajax({
        		url:url,
        		data:{fromid:fromid,toid:toid},
        		type:"post",
        		success: function(text){
        			mini.alert("操作成功！");
        		},
        		error: function (jqXHR, textStatus, errorThrown){
        			mini.alert("操作失败!");
        		}
        	});
        	}	
        	}
        	);
        }
        function caozuo(e){
        	var flow=e.record;
        	var s='<a class="Edit_Button" href="javascript:mingxi(\''+flow.flowdefid+'\')">明细</a>';
        	return s;
        }
        function mingxi(e){
        	var url="openFlowPage.action?1=1&flowid="+e;
        	if(e!=''){
        		mini.open({
        			url:url,
        			title:"流程明细:"+e, width: 1000, height: 550,
       			 	showMaxButton: true,
                    allowResize: false,
                onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            //iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	grid.reload();
	 	        }
        		});
        	}
        }
        function createSequences(){
        var url="createSequences.action";
        $.ajax({
        	url:url,
        	type:"post",
        	success: function(text){
        		mini.alert("操作成功！");
        	},
        	error: function (jqXHR, textStatus, errorThrown){
        		mini.alert("操作失败!");
        	}
        });
        }
        
        function org(){
        	var url="openOrgAction.action";
        	mini.open({
        		url:url,
        		title:"ORG_ORG表维护",width:900,height:550,
        		showMaxButton:true,
        		allowResize:true,
        		onload:function (){
	        		var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            //iframe.contentWindow.SetData(data);
        		},
        		ondestroy:function(){
        			grid.reload();
        		}
        	});
        }
        
        function addflowpage(){
        	var url="addNewFlowPage.action";
        	mini.open({
        		url:url,
        		title:"新增流程",width:300,height:450,
        		showMaxButton:true,
        		allowResize:true,
        		onload:function (){
	        		var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            //iframe.contentWindow.SetData(data);
        		},
        		ondestroy:function(){
        			grid.reload();
        		}
        	});
        }
        function search(){
        	var key=mini.get("key").getValue();
        	grid.load({key:key});
        }
        
        function onKeyEnter(){
        	search();
        }
    </script>
	</body>
</html>