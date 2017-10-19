<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>角色管理</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<!-- 必须指定,否则高度为0 -->
		<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: auto;
		}
		.hide{
			display: none;
		}
		</style>
	</head>
	<body>
		<div class="crud-search">
			<div class="mini-toolbar" style="padding-top: 5px; padding-bottom: 5px;text-align:right;border-bottom:0;">
				<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
				<!-- <a class="mini-button" iconCls="icon-edit" onclick="editRow()" plain="true">编辑</a> -->
				<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
			</div>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/role/ajax/list.json" idField="id"
				sortField="sn" sortOrder="asc" allowAlternating = "true"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="false"
				allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
				>
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="sn" width="15" headerAlign="center"
						allowSort="true" align="center" >
						排序
					</div>
					<div field="roleid" width="30" headerAlign="center"
						allowSort="true" align="center" >
						角色编号
					</div>
					<div field="departid" width="30" headerAlign="center"
						allowSort="true" align="center">
						部门编号
					</div>					
					<div field="text1" headerAlign="center"  align="center" renderer="onRadioRenderer" allowSort="true" width="40">通用类型</div>
					<div field="name" width="60" headerAlign="center"
						allowSort="true" align="left" >
						角色名称
					</div>
					<div field="remark" width="60" allowSort="true" headerAlign="center" align="left" >
						备注
					</div>
					<div field="optime" width="40" headerAlign="center"
					 renderer="onDateRenderer"
						dateFormat="yyyy-MM-dd" allowSort="true" align="center" >
						创建日期
					</div>
					<div name="operation" width="60" headerAlign="center" 
						align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
					
				</div>
			</div>
		</div>
		<div id="editWindow" class="mini-window" title="编辑角色" style="height: 300px;width: 520px;"
		    showModal="true" allowResize="true" allowDrag="true">
		    <!-- 弹出框 -->
		    <div id="editform" class="form" style="min-height: 150px;" >
				<input name="id" class="mini-hidden" />
				<input name="mymenuid" class="mini-hidden" />
				<input id="tempmenuid" name="tempmenuid" type="hidden" value="18" />
				<input name="gkzxtempid" id="gkzxtempid" class="mini-hidden" />
				<input id="operatetype" name="operatetype" type="hidden" value="edit" />
				
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData()" plain="true" style="width: 60px">保存</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true" style="width: 60px;">取消</a>
							</td>
							<td style="white-space: nowrap;">
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 11px; padding-bottom: 5px;">
					<table style="table-layout: fixed;">
						<tr>
							<td style="width: 90px;">
								角色编号
							</td>
							<td style="width: 150px;">
								<input id="roleid" name="roleid" class="mini-textbox"  vtype="int;maxLength:10" required="true"/>
								<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
							</td>
							<td style="width: 90px;">
								角色名称
							</td>
							<td style="width: 150px;">
								<input id="name" name="name" class="mini-textbox"  required="true"/>
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								角色排序
							</td>
							<td style="width: 150px;">
								<input name="sn" class="mini-spinner"  value="1" minValue=0" maxValue="999999" 
		                	 		required="false"  requiredErrorText=""/>
							</td>
							<td style="width: 90px;">
								备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注
							</td>
							<td style="width: 250px;">
								<input id="remark" name="remark" class="mini-textbox"/>
							</td>
						</tr>
						<tr >
		                	<td>通用类型：</td>
		                	<td colspan="3">
		                		<input name="text1" class="mini-radiobuttonlist" repeatItems="0" repeatLayout="table" repeatDirection="vertical"
								    textField="text" valueField="id" value="1" required="true" data="Radiodata" />
		                	</td>
		                </tr>
					</table>
				</div>
		   </div>
		</div>
    	<script type="text/javascript">
    	var Radiodata = [{ id: 1, text: '单位特有' },{ id: 2, text: '省局通用'},{ id: 3, text: '全国通用'}];
    	// 解析并初始化
        mini.parse();
    	// 获取grig对象
        var grid = mini.get("datagrid1");
    	// 加载数据
        grid.load();
        
        // 渲染通用类型
        function onRadioRenderer(e) {
            for (var i = 0, l = Radiodata.length; i < l; i++) {
                var g = Radiodata[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        };
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        };
        
        // 渲染操作菜单
        function onActionRenderer(e) {
	            var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
	            var gkzxtempid=record.gkzxtempid;
	            var s ="";
		            s=s+'<a class="New_Button" href="javascript:edit(\'role/editResourceByRole.page?1=1\',\'400\',\'400\',\'18\',\'null\')">权限情况</a>&nbsp;&nbsp;';
		            s=s+'<a class="New_Button" href="javascript:editRow()">修改</a>&nbsp;&nbsp;';
		            s=s+'<a class="New_Button" href="javascript:delRow()">删除</a>&nbsp;&nbsp;';
	            return s;
	    };
	    
		// 弹出编辑框
		function edit(myurl,mywidth,myhight,menuid,tanchuleixing) {
        	var grid= mini.get("datagrid1");
            var row = grid.getSelected();
            if (row) {
            	var roleid = row.roleid;
            	myurl=myurl+'&menuid='+menuid+"&operatetype=edit&id="+roleid;
                var win=mini.open({
                    url: myurl,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "配置角色权限", width: mywidth, height: myhight,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: roleid,mymenuid:menuid ,myoperatetype:'edit'};
                        // 调用内部的方法
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        };

        
        // 刷新本页面
		function refreshPage(){
			location.reload();
		};
		function search() {
		    var key = document.getElementById("key").value;
		    grid.load({ key: key });
		};
		// 增加
		function addRow() {
			var data = {
				islocalnew : 1
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
		//删除角色
		function delRow(){
		if(confirm("确定要刪除角色吗？")){
			var row=grid.getSelected();
			$.ajax({
				url:"role/ajax/delete.json?1=1&roleid="+row.roleid,
				type:"post",
				success:function(text){
					alert("操作成功！");
					grid.load();
				},
				error:function (jqXHR, textStatus, errorThrown){
					alert("操作失败！");
				}
			});
			}
		}
		// 显示编辑框
		function showEditWindow(data, meta){
			//
			data = data || {};
			meta = meta || {
				saveText : "保存"
				, cancelText : "取消"
				, callback : function(){}
			};
			var editWindow = mini.get("editWindow");
			editWindow.show();
			var form = new mini.Form("#editform");
			//form.loading();
			// 设置数据
	        form.setData(data);
			form.setIsValid(true);
			form.setChanged(false);
			// 允许编辑
	        form.unmask();
			
		};
		// 隐藏
		function hideEditWindow() {
			// 界面
			var editWindow = mini.get("editWindow");
			editWindow.hide();
		};
		function cancelEditWindow() {
			hideEditWindow();
		};
		//
		// 删除
		function removeRow() {
			// ajax...
			//
		    var rows = grid.getSelecteds();
		    if (rows.length > 0) {
		       // grid.removeRows(rows, true);
		    }
		};
		// 保存数据
		function saveData() {
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			//data = JSON.parse(data);
			// 界面
			grid.loading("保存中，请稍候......");
			var editWindow = mini.get("editWindow");
			if(!data){
				editWindow.hide();
			    grid.reload();
				return ;
			}
			//
			var url = "<%=path%>/role/ajax/update.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
						editWindow.hide();
			        	grid.reload();
	               } else {
	               		alert(info);
	               		grid.unmask();
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 
			        alert("保存失败");
	               	grid.unmask();
				};
			// 请求AJAX
			requestAjax(url, data, successCallback, errotCallback);
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
		// 输入框,事件绑定,13回车
        $("#key").bind("keyup", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        $(document).bind("keyup", function (e) {
        	var KEYCODE_ESC = 27;
        	var KEYCODE_ENTER = 13;
        	//
        	var keyCode = e.keyCode;
        	if(keyCode === KEYCODE_ESC){
        		// 执行钩子
        		hideEditWindow();
        	}
        });
		//
    </script>
	</body>
</html>