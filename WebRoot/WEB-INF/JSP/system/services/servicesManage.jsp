<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>services管理</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
		<div class="crud-search mini-toolbar" style="border-bottom:0;">
			<table style="width:98%;">
				<tr>
					<td align="right">
						<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					</td>
				</tr>
			</table>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/services/getServicesData.json?1=1" idField="id"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" style="width:100%;height:100%;"
				sortField="optime" sortOrder="desc"
				onrowdblclick="rowdblclickfunction"
				>
				<div property="columns">
					<div field="departid" width="35" headerAlign="center"
						allowSort="true" align="center" >
						单位ID
					</div>
					<div field="name" width="80" headerAlign="center"
						allowSort="false" align="center" >
						单位名称
					</div>
					<div field="address" width="100" headerAlign="center"
						allowSort="true" align="center" >
						IP地址
					</div>
					<div field="port" width="40" headerAlign="center"
						allowSort="true" align="center" >
						端口号
					</div>
					<!-- 
					<div field="ddcid" width="60" headerAlign="center"
						allowSort="true" align="center">
						方案ID
					</div>
					 -->
					<div field="text1" width="30" headerAlign="center"
						allowSort="true" align="center" renderer="zhuangtai">
						状态
					</div>
					<div field="remark" width="100" renderer=""
						align="center" headerAlign="center">
						备注
					</div>
					<div name="action" width="" headerAlign="center" 
						align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
				</div>
			</div>
		</div>
		
		<div id="editWindow" class="mini-window" title="编辑" style="width:510px;height:220px"
			    showModal="true" allowResize="true" allowDrag="true"
			    >
			    <!-- 弹出框 -->
			    <div id="editform" class="form" >
			        <div class="mini-toolbar mini-grid-headerCell" style="border-bottom:0;padding:0px;margin: -5px -5px 5px;">
			            <table style="width:100%;">
			                <tr>
			                    <td style="width:100%;">
				            		<a class="mini-button" onclick="saveData()" plain="true" style="width:60px">保存</a>       
				           			<a class="mini-button" onclick="cancelEditWindow()"  plain="true" style="width:60px;">取消</a>
				           		</td>
			                </tr>
			            </table>           
			        </div>
			        <table style="width:100%;">
			            <tr>
			                <td >部门编号：</td>
			                <td ><input id="departid" name="departid" class="mini-textbox"  size="180" style="width:150px"
			                	 vtype="maxLength:100" required="true" requiredErrorText="部门编号" requiredErrorText="部门编号不能为空"/>
			                </td>
			            </tr>
			            <tr>
			                <td>IP地址：</td>
			                <td><input name="address" class="mini-textbox"  size="180" style="width:150px" required="true" 
			                vtype="maxLength:100" emptyText="请输入IP地址" requiredErrorText="IP不能为空"/>
			            </tr>
			            <tr>
			                <td >端口：</td>
			                <td >
			                	<input id="port" name="port" class="mini-textbox"  size="180" style="width:150px" required="true"
			                	 vtype="maxLength:100" emptyText="请输入说明信息" requiredErrorText="端口不能为空"/>
			                </td>
			            </tr>
			            <!--  
			            <tr>
			                <td>方案ID：</td>
			                <td><input name="ddcid" class="mini-textbox"  size="180" style="width:150px" vtype="maxLength:100" required="false"
			                emptyText="请输入方案ID" />
			            </tr>
			            -->
			            <tr>
			                <td>状 态：</td>
			                <td><input name="text1"  class="mini-combobox"  size="180" style="width:150px" data="[{id:'1', text:'启用'},{id:'0', text:'废弃'}]"/>
			            </tr>
			             <tr>
			                <td>备注：</td>
			                <td><input name="remark" class="mini-textbox"  size="180" style="width:150px" vtype="maxLength:100" />
			            </tr>
			        </table>
			    </div>
		</div>
   	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
    	// 加载数据
        grid.load();
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
        	}
            return "";
        };
		 function zhuangtai(e){
        	var s=e.record;
        	if(s.text1=='1'){s='启用'}
        	if(s.text1=='0'){s='废弃'}
        	 return s;
        }
		 
        // 渲染操作菜单
        function onActionRenderer(e) {
	            var s ="";
	            s=s+'<a href="javascript:editRow()">修改</a>&nbsp;&nbsp;';
	            s=s+'<a href="javascript:deleteRow()">删除</a>';
	            return s;
	    };
		function search() {
		    var key = document.getElementById("key").value;
		    grid.load({ key: key });
		};
		
		// 增加
		function addRow() {
			var data = {
				text1 : 1
			};
			// 弹出 新增/编辑框
			showEditWindow(data);
		};
		function rowdblclickfunction(e){
			editRow();
		};
		// 编辑
		function editRow() {
		    var row = grid.getSelected();
		    if (row) {
		        showEditWindow(row);
		    }
		};
		//删除
		function deleteRow(){
			if(confirm("确认删除？")){
			var row = grid.getSelected();
			var  url = "<%=path%>/services/removeData.action?1=1";
			var successCallback = function (message) {
	               if(1 === message){
		               	alert("操作成功！");
			        	grid.reload();
	               } 
			    };
			var errorCallback = function (jqXHR, textStatus, errorThrown) {
			        alert("操作失败！");
	               	grid.unmask();
			    };
			requestAjax(url,row,successCallback,errorCallback);
			}
		}
		// 显示编辑框
		function showEditWindow(data){
			
			var editWindow = mini.get("editWindow");
			editWindow.show();
			var form = new mini.Form("#editform");
			
			//form.loading();
			// 设置数据
	        form.setData(data);
			form.setIsValid(true);
			form.setChanged(false);
		};
		// 隐藏
		function hideEditWindow() {
			var editWindow = mini.get("editWindow");
			editWindow.hide();
		};
		function cancelEditWindow() {
			hideEditWindow();
		};
		// 保存数据
		function saveData() {
			var form = new mini.Form("#editform");
			
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			var editWindow = mini.get("editWindow");
			if(!data){
				editWindow.hide();
			    grid.reload();
				return ;
			}
			var  url = "<%=path%>/services/saveOrupdate.action?1=1";
			var successCallback = function (message) {
	               if(1 === message){
		               	editWindow.hide();
		               	alert("操作成功");
			        	grid.reload();
	               } 
			    };
			var errorCallback = function (jqXHR, textStatus, errorThrown) {
			        alert("保存失败");
	               	grid.unmask();
			    };
			requestAjax(url,data,successCallback,errorCallback);
		};
        $("#key").bind("keyup", function (e) {
        	var KEYCODE_ESC = 27;
        	var KEYCODE_ENTER = 13;
        	//
        	var keyCode = e.keyCode;
        	if(keyCode === KEYCODE_ENTER){
                search();
        	}
        });
    </script>
	</body>
</html>