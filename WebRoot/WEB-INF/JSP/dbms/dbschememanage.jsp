<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<html>
	<head>
		<title>方案管理</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	   <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
	   
	   <style type="text/css">
		    html, body
		    {
		        font-size:12px;
		        padding:0;
		        margin:0;
		        border:0;
		        height:100%;
		        overflow:auto;
		    }
	    </style>
	</head>
	<body >
	
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					&nbsp;&nbsp;&nbsp;&nbsp;
						方案类型：
						<select id="search_ddctype" name="search_ddctype" onchange="onKeyEnter()">
	                      	<option value="" >--所有--</option>
							<option value="0">数据导入</option>
							<option value="1">数据导出</option>
							<option value="2">数据交换</option>
			             </select>
			            &nbsp;&nbsp;
		  				方案名称：
		  				<input class="mini-textbox" type="text" id="search_ddcname" name="search_ddcname" size="16" onenter="onKeyEnter"/>&nbsp;&nbsp;						
		 				<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
					</td>
					<td align="right">
						<a class="mini-button" iconCls="icon-addfolder"
							 onclick="" href="<%=path %>/dbms/copyswapscheme.page?ddctype=2" plain="true">复制现有交换方案</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-addnew"
							 onclick="" href="<%=path %>/dbms/dbschemeadd.page?ddctype=2" plain="true">新增交换方案</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-download"
							 onclick="" href="<%=path %>/dbms/dbschemeadd.page?ddctype=1" plain="true">新增导出方案</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-upload"
							 onclick="" href="<%=path %>/dbms/dbschemeadd.page?ddctype=0" plain="true">新增导入方案</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-reload" 
							onclick="refreshPage()"	plain="true">刷新</a>
					</td>
				</tr>
			</table>
		 </div>
    </div>
			
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listdbscheme.json" 
	    	idField="ddcid" sortField="ddcid" sortOrder="asc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        <%--
	        		<div type="checkcolumn" width="25"></div>
	         --%>
	        		<div field="ddcid" name="ddcid" width="30" headerAlign="center" align="right"  allowSort="true">
               			方案编号
               		</div>
               		<div field="ddcname" name="ddcname" width="50" headerAlign="center" align="left"  allowSort="true">
               			方案名称
               		</div>
               		<div field="ddctype" name="ddctype" renderer="onddctypeRenderer"
               			 width="40" headerAlign="center" align="center"  allowSort="true">
               			方案类型
               		</div>
               		<div field="fromdatabasename" name="fromdatabasename" width="40" headerAlign="center" align="left"  allowSort="true">
               			导出数据库
               		</div>
               		<div field="todatabasename" name="todatabasename" width="30" headerAlign="center" align="center"  allowSort="true">
               			导入数据库
               		</div>
               		<div field="ddcexpscheme" name="ddcexpscheme" width="30" headerAlign="center" align="center"  allowSort="true">
               			对应导出方案
               		</div>
			        <div width="40" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
    
	<div id="editWindow" class="mini-window" title="方案编辑" style="width:600px;height:320px"
		    showModal="false" allowResize="true" allowDrag="true"
		    >
		    <!-- 弹出框 -->
		    <div id="editform" class="form" >
		        <input class="mini-hidden" name="id"/>
		        <div class="mini-toolbar mini-grid-headerCell" style="border-bottom:0;padding:0px;margin: -5px -5px 5px;">
		            <table style="width:100%;">
		                <tr>
		                    <td style="width:100%;">
			            		<a class="mini-button" onclick="saveData()" plain="true" style="width:60px">保存</a>   
			            		|    
			           			<a class="mini-button" onclick="cancelEditWindow()"  plain="true" style="width:60px;">取消</a>
			           		</td>
		                </tr>
		            </table>           
		        </div>
		        <table style="width:100%;">
		            <tr>
		                <td >方案编号：</td>
		                <td ><input name="ddcid" class="mini-textbox"  size="60"
		                	 vtype="minLength:3;maxLength:30" required="true"  requiredErrorText="3-30个字符"/>
		                </td>
		            </tr>
		            <tr>
		                <td >方案名称：</td>
		                <td ><input name="ddcname" class="mini-textbox"  size="60"
		                	 vtype="maxLength:30" required="true"  requiredErrorText="名称不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >方案类型 ：</td>
		                <td >
		                		<input name="ddctype" class="mini-combobox" size="60"
		                		 valueField="id" textField="typename"
		                		 data="ddctypeArray" required="true"  requiredErrorText="类型不能为空"/>
								<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td >导出数据库：</td>
		                <td ><input name="fromdatabaseid" class="mini-combobox" 
                					textField="ddname" valueField="ddid"
						            url="<%=path %>/dbms/ajax/listalldb.json"
		                			size="60"  vtype="maxLength:32" required="false"/></td>
		            </tr>
		            <tr>
		                <td> 导入数据库 ：</td>
		                <td><input  name="todatabaseid" class="mini-combobox"
                					textField="ddname" valueField="ddid"
						            url="<%=path %>/dbms/ajax/listalldb.json"
		                 			size="40" vtype="maxLength:30" required="false"/></td>
		            </tr>
		            <tr>
		            	<td>
		            	</td>
		            </tr>
		            
		        </table>
		    </div>
	</div>
	
    <script type="text/javascript">
    	//
    	var ddctypeArray = [
    			{id:"0", typename:"数据导入"}
    			,{id:"1", typename:"数据导出"}
    			,{id:"2", typename:"数据交换"}
    		];
    	//
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        ////////////////////////
        // 渲染方案类型
        function onddctypeRenderer(e){
            var s = ""; 
            //
        	var row = e.row;
        	var ddctype = row.ddctype;
        	//
        	for(var i=0; i< ddctypeArray.length; i++){
        		var ddc = ddctypeArray[i] || {};
        		if(ddc.id == ddctype){
        			s = ddc.typename;
        		}
        	}
            //
            return s;
        };
        // 渲染操作列
        function onActionRenderer(e) {
            var s = '<a class="Edit_Button" href="javascript:editRow()" >编辑</a>';
                s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:deleteRow()">删除</a>'; 
                s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:editScheme();" >相关表</a>';
            return s;
        };
        
        function onKeyEnter(e) {
            search();
        };
		// 查询
		function search() {
		    var search_ddctype = document.getElementById("search_ddctype").value || "";
		    var search_ddcname = mini.get("search_ddcname").getValue();
		    //console.dir(grid);
		    var param =  {
		    	ddctype: search_ddctype
		    	,ddcname: search_ddcname
		    };
		    grid.load(param);
		};
		//
		
		// 增加
		function addRow() {
			var data = {
				islocalnew: 1
				,ddctype : "0"
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
		    //
		    if (row) {
				var data = $.extend({}, row);
				//data["databasepassword"] = "";
		        showEditWindow(data , 1);
		    }
		};
		
		function editScheme() {
		    var row = grid.getSelected();
		    if (row) {
				var ddcid = row.ddcid;
				var ddctype = row.ddctype;
		        window.location.href = "<%=path %>/dbms/dbschemeedit.page?1=1&ddcid=" + ddcid + "&ddctype=" + ddctype;
		    }		
		}
		// 删除
		function deleteRow(){
		    var row = grid.getSelected();
		    //
		    if (!row) {
		    	return;
	    	}
	    	var del = confirm("删除将会记录操作日志,确实要删除吗?");
	    	if(!del){
	    		// 
		    	return;
	    	}
	        // 提交请求
	        
			var data = row;
			//data = JSON.parse(data);
			// 界面
			grid.loading("操作中，请稍候......");
			//
			var  url = "<%=path%>/dbms/ajax/deletedbscheme.json";
			var successCallback = function (message) {
			       	        	   
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
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
			        alert("网络错误");
	               	grid.unmask();
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		};
		// 显示编辑框
		function showEditWindow(data, isedit){
			data = data || {};
			//
			var editWindow = mini.get("editWindow");
			editWindow.showAtPos('right','top');
			//
			var form = new mini.Form("#editform");
			//
			var islocalnew = data.islocalnew;
			var ddorg = data.ddorg;
			//
			var ddcidInput = mini.getbyName("ddcid");
			var ddorgInput = mini.getbyName("ddorg");
			if(ddcidInput){
				if(!islocalnew){
					ddcidInput.allowInput=false;
				} else {
					ddcidInput.allowInput=true;
				}
			}
			if(ddorgInput){
				if(ddorg){
					ddorgInput.setText(ddorg);
				}
			}
			form.loading();
			// 设置数据
	        form.setData(data);
			//
			form.setIsValid(true);
			form.setChanged(false);
			// 允许编辑
	        form.unmask();
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
			var  url = "<%=path%>/dbms/ajax/updatedbscheme.json";
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
		// 刷新本页面
		function refreshPage(){
			//
			if(!window["____refreshPage"]){
				window["____refreshPage"] = true;
				//
				location.reload();
			} else {
				window.setTimeout(function(){
					window["____refreshPage"] = false;
					},1*1000);
			}
		};
	</script>
	</body>
</html>