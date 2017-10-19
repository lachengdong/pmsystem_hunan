<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>所有配置</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"			rel="stylesheet" type="text/css" />
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
					<td align="left">
						<span>关键字：</span>
						<input type="text" id="key" />
						<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查找</a>
					</td>
					<td align="right">
						<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
		               <%
							if(topmap!=null && topmap.size()>0){
								for (String key : topmap.keySet()) {
									String[] arry = topmap.get(key).split("@");
									if("".length() < 5){break;}
				       %>
				       
						<a class="mini-button" iconCls="icon-reload" onclick="<%=arry[1] %>" plain="true"><%=arry[0] %></a>
				       <%
								}
							}
						%>
					</td>
				</tr>
			</table>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/config/ajax/list.json?1=1" idField="id"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" style="width:100%;height:100%;"
				sortField="optime" sortOrder="desc"
				 onrowdblclick="rowdblclickfunction"
				>
				<div property="columns">
					<div field="catagory" width="35" headerAlign="center"
						allowSort="true" align="center" >
						分类类别
					</div>
					<div field="name" width="40" headerAlign="center"
						allowSort="true" align="left" >
						参数名
					</div>
					<div field="value" width="60" headerAlign="center"
						allowSort="true" align="left" >
						参数值
					</div>
					<div field="describe" width="60" headerAlign="center"
						allowSort="true" align="left">
						说明信息
					</div>
					<div field="departid" width="35" headerAlign="center"
						allowSort="true" align="center">
						机构ID
					</div>
					<div field="status" width="25" renderer="onStatusRenderer"
						align="center" headerAlign="center">
						参数状态
					</div>
					<div name="operation" width="60" headerAlign="center" 
						align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
				</div>
			</div>
		</div>
		
		<div id="editWindow" class="mini-window" title="编辑" style="width:510px;height:240px"
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
			                <td >分类类别 ：</td>
			                <td >
			                		<input name="catagory" class="mini-textbox" size="80"
			                		 vtype="maxLength:60" required="true"  requiredErrorText="类别不能为空"/>
									<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
			        				<input class="mini-hidden" name="id"/>
			                </td>
			            </tr>
			            <tr>
			                <td >参数名：</td>
			                <td ><input id="name" name="name" class="mini-textbox"  size="180" style="width:190px"
			                	 vtype="maxLength:100" required="true"  requiredErrorText="name不能为空"/>
			                </td>
			            </tr>
			            <tr>
			                <td>参数值：</td>
			                <td><input name="value" class="mini-textbox"  size="180" style="width:190px"/></td>
			            </tr>
			            <tr>
			                <td >说明信息 ：</td>
			                <td >
			                		<input id="describe" name="describe" class="mini-textbox"  size="180" style="width:190px"
			                	 		vtype="maxLength:100" emptyText="请输入说明信息" />
			                </td>
			            </tr>
			            <tr>
			                <td>状态：</td>
			                <td><input name="status" class="mini-combobox"  size="80" data="[{id:'0', text:'正常'},{id:'-1', text:'废弃'}]"/></td>
			            </tr>
			            <tr>
			                <td>所属机构：</td>
			                <td><input name="departid" class="mini-textbox"  size="180" style="width:190px"/></td>
			            </tr>
			        </table>
			    </div>
		</div>
    	<script type="text/javascript">
    	// 解析并初始化
        mini.parse();
    	// 获取grig对象
        var grid = mini.get("datagrid1");
    	// 加载数据
        grid.load();
        ///////////////////////////////////////////////////////
        // 渲染Status
        function onStatusRenderer(e) {
        	var ISMENU_TEXT = [{ id: -1, text: '废弃' }, { id: 0, text: '正常'}];
            for (var i = 0, l = ISMENU_TEXT.length; i < l; i++) {
                var g = ISMENU_TEXT[i];
                if (g.id == e.value) return g.text;
            }
            return "正常";
        };
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
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
		            s=s+'<a href="javascript:editRow()">修改</a>&nbsp;&nbsp;';
	            return s;
	    };
        //////////////////////////////////////////////////////
		function search() {
		    var key = document.getElementById("key").value;
		    if(key){
		    	key = encodeURI(key);
		    }
		    grid.load({ key: key });
		};
		
		// 增加
		function addRow() {
			var data = {
				islocalnew: 1
				,status : 0
				,catagory : 'SYSTEM'
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
			
			//
			var nameInput = mini.get("name");
			
			var islocalnew = data.islocalnew;
			//form.loading();
			// 设置数据
	        form.setData(data);
			form.setIsValid(true);
			form.setChanged(false);
			// 允许编辑
	        form.unmask();
			if(nameInput){
				nameInput.focus();
			}
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
		// 刷新本页面
		function refreshPage(){
			//
			location.reload();
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
			var  url = "<%=path%>/config/ajax/update.json";
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
			    	// 把错误吃了
			        alert("保存失败");
	               	grid.unmask();
			    };
			//
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
		// 输入框,事件绑定,13回车
        $("#key").bind("keyup", function (e) {
        	var KEYCODE_ESC = 27;
        	var KEYCODE_ENTER = 13;
        	//
        	var keyCode = e.keyCode;
        	if(keyCode === KEYCODE_ENTER){
                search();
        	}
        });
		//
    </script>
	</body>
</html>