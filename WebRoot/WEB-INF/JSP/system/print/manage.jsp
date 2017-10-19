<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>打印方案</title>
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
	    <div class="mini-toolbar" style="padding:2px;border:0;border-bottom:1px solid #99bce8;">
	        <table style="width:100%;">
	            <tr>
	            <td style="width:100%;" align="right">
					<a class="mini-button" iconCls="icon-add" onclick="onAddNode()"	plain="true">新增打印方案</a>
					<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
	            </td>
	            <td style="white-space:nowrap;">
	            
	            </td>
	            </tr>
	        </table>
	    </div>
    	<!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/print/ajax/list.json?1=1" idField="id"
				sortField="sn" sortOrder="asc"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
				>
				<div property="columns">
					<div field="resid" width="30" headerAlign="center"
						allowSort="true" align="center" >
						方案编号
					</div>
					<div field="text1" width="70" headerAlign="center"
						allowSort="true" align="center" >
						方案类型
					</div>
					<div field="name" width="120" headerAlign="center"
						allowSort="true" align="left" >
						方案名称
					</div>
					<div field="sn" width="35" headerAlign="center"
						allowSort="true" align="center" >
						方案排序
					</div>
					<div field="remark" width="60" allowSort="true" headerAlign="center" align="center" >
						备注
					</div>
					<div field="optime" width="60" headerAlign="center"
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
		<div id="editWindow" class="mini-window" title="方案编辑" style="width:520px;"
		    showModal="true" allowResize="true" allowDrag="true"
		    >
		    <!-- 弹出框 -->
		    <div id="editform" class="form" style="min-height: 150px;" >
				<input name="menuid" class="mini-hidden" />
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData()" plain="true"
									style="width: 60px">保存</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true"
									style="width: 60px;">取消</a>
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
								方案编号
							</td>
							<td style="width: 150px;">
								<input id="resid" name="resid" class="mini-textbox"  vtype="int;maxLength:5" required="true"/>
								<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
								<input id="presid" name="presid" class="mini-hidden"/>
								<input id="restypeid" name="restypeid" class="mini-hidden"/>
							</td>
							<td style="width: 90px;">
								方案名称
							</td>
							<td style="width: 150px;">
								<input id="name" name="name" class="mini-textbox"  required="true"/>
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								方案类型
							</td>
							<td style="width: 150px;">
								<input id="text1" name="text1" class="mini-textbox"  vtype="maxLength:25" required="true"/>
							</td>
							<td style="width: 90px;">
								方案排序
							</td>
							<td style="width: 150px;">
								<input id="sn" name="sn" vtype="float;maxLength:5"  required="true"
									class="mini-textbox" />
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								备&nbsp;注&nbsp;信&nbsp;息
							</td>
							<td style="width: 450px;" colspan="3">
								 <input name="remark" class="mini-textarea" style="width:370px;height:40px;"/>
							</td>
						</tr>
						<tr>
						</tr>
					</table>
				</div>
		   </div>
		</div>		
		
		<script type="text/javascript">
		
			// 如果在 onload 之前需要调用,则执行此方法
	    	// 解析并初始化
	        mini.parse();
	    	// 获取grig对象
	        var grid = mini.get("datagrid1");
	    	// 加载数据
	        grid.load();
	    	////////////////////////////////////////////////////////////////////////
	    	////////////////////////////////////////////////////////////////////////
	    	
	        // 渲染操作菜单
	        function onActionRenderer(e) {
	            var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
	            var gkzxtempid=record.gkzxtempid;
	            var s ="";
		            s=s+'<a class="" href="javascript:onViewRow()">查看</a>&nbsp;&nbsp;';
		            s=s+'<a class="" href="javascript:onEditRow()">修改</a>&nbsp;&nbsp;';
	            return s;
		    };
			// 新增方案
			function onAddNode(e) {
			    // 查询下一个方案ID
			    var url = "<%=path%>/print/ajax/getnextid.json";
			    var data = {};
			    //
			    var successCallback = function(message){
		        	   //
		        	   var info = message["info"];
		               if(1 === message["status"] || true){
		            	   //
		            	   var meta = message["meta"] || {};
		            	   var nextid = meta["nextid"] || "";
				    		// 判断是否成功
						    var newNode = {
						    	resid : nextid
						    	, presid : 100
						    	, sn : "9999"
						    	, optime : new Date()
						    	, ismenu : 1
						    	, ismenuleaf : 1
						    	, isresourcesleaf : 0
						    	, restypeid : 14
						    	, name : "打印方案_"+nextid
						    	, islocalnew : true
						    };
							// 弹出 新增/编辑框
							showEditWindow(newNode);
		               } else {
		               	 debug(info, true);
		               }
			    };
			    var errotCallback = function(jqXHR, textStatus, errorThrown) {
				    	// 把错误吃了
				    	debug("网络错误",true);
				};
			    //
			    requestAjax(url,data,successCallback,errotCallback);
			    
			};
			/////
			// 查看方案
			function onViewRow() {
			    var row = grid.getSelected();
			    if (row) {
			        showEditWindow(row,{edit:0});
			    }
			};
			function rowdblclickfunction(e){
				onEditRow(e);
			};
			// 编辑
			function onEditRow() {
			    var row = grid.getSelected();
			    if (row) {
			        showEditWindow(row,{edit:1});
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
				//form.loading();
				// 设置数据
		        form.setData(data,true);
				//
				
				var islocalnew = data.islocalnew;
				
			    var residInput = mini.getbyName("resid");
			    var nameInput = mini.getbyName("name");
			    //
				
				// 修改时不允许改变ID
				if(residInput){
					if(!islocalnew){
						residInput.allowInput=false;
					} else {
						residInput.allowInput=true;
						// 延迟
						window.setTimeout(function(){
							nameInput && nameInput.focus();
						}, 20);
					}
				}
				
				form.setIsValid(true);
				form.setChanged(false);
				// 允许编辑
		        form.unmask();
				
			};
				
			// 保存数据
			function saveData() {
				var form = new mini.Form("#editform");
				// 校验
				form.validate();
				if (form.isValid() == false){
					return;
				}
				//
				var data = form.getData();
				// 界面
				form.loading("保存中，请稍候......");
				//
			    //
			    var url = "<%=path%>/print/ajax/update.json";
			    //
			    var successCallback = function(message){
		        	   //
		        	   var info = message["info"];
		               if(1 === message["status"]){
				    		// 判断是否成功
				        	// grid.reload();
				    		refreshPage();
		               } else {
		               		debug(info, true);
		               }
		               form.unmask();
			    };
			    var errotCallback = function(jqXHR, textStatus, errorThrown) {
				    	// 
				        alert("保存失败");
		               	form.unmask();
				};
				// 执行AJAX请求
			    requestAjax(url,data,successCallback,errotCallback);
			};
			//
			
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
			
	        // 渲染日期
	        function onDateRenderer(e) {
	        	//debug(e);
	        	if(e && e.value){
	        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	        	}
	            return "";
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
			function debug(obj, force){
				// chrome
			    if(window["debugMode"] || force){
			    	//
			    	alert(obj);
			    } else if(window["console"]){
			    	console.dir(obj);
			    } else{
			    	// 不执行任何操作
			    }
			};
		</script>
	</body>
</html>