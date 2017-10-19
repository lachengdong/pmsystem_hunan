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
		<title>系统代码管理</title>
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
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					&nbsp;&nbsp;&nbsp;&nbsp;
		  				代码类型：
		  				<input class="mini-textbox" type="text" id="search_codetype" name="search_codetype" size="16" onenter="onKeyEnter"/>	
			            &nbsp;&nbsp;
		  				代码ID：
		  				<input class="mini-textbox" type="text" id="search_codeid" name="search_codeid" size="16" onenter="onKeyEnter"/>	
			            &nbsp;&nbsp;
		  				代码名称：
		  				<input class="mini-textbox" type="text" id="search_name" name="search_name" size="16" onenter="onKeyEnter"/>
		  				&nbsp;&nbsp;
		  				查询代码：
		  				<input class="mini-textbox" type="text" id="search_scearch" name="search_scearch" size="16" onenter="onKeyEnter"/>
		  				&nbsp;&nbsp;
		 				<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
					</td>
					<td align="right">
						<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					</td>
				</tr>
			</table>
		 </div>
    </div>		
    <!-- 显示表格 -->
	<div class="crud-grid mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			url="<%=path %>/syscode/ajax/list.json?1=1" idField="id"
			allowResize="false" pageSize="20" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true"
			allowMoveColumn="false" style="width:100%;height:100%;"
			sortField="noid" sortOrder="asc"
			>
			<div property="columns">
				<div field="pcodeid" width="30" headerAlign="center"
					allowSort="true" align="center" >
					代码父ID
				</div>
				<div field="codetype" width="40" headerAlign="center"
					allowSort="true" align="center" >
					代码类型
				</div>
				<div field="codeid" width="40" headerAlign="center"
					allowSort="true" align="right" >
					代码ID
				</div>
				<div field="name" width="60" headerAlign="center"
					allowSort="true" align="left" >
					代码名称
				</div>
				<div field="remark" width="35" headerAlign="center"
					allowSort="true" align="center">
					代码说明
				</div>
				<div field="scearch" width="25"
					align="center" headerAlign="center">
					查询代码
				</div>
				<div field="orgid" width="25"
					align="center" headerAlign="center">
					单位代码
				</div>
				<div field="optime" width="40" headerAlign="center"
				 renderer="onDateRenderer" align="center" allowSort="true">
					创建时间
				</div>
			    <div width="30" headerAlign="center" align="center"	allowSort="false"
			    	 renderer="onActionRenderer">操作</div> 
			</div>
		</div>
	</div>
   
	<div id="editWindow" class="mini-window" title="代码编辑" style="width:600px;height:320px"
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
		                <td >代码类型：</td>
		                <td ><input name="codetype" class="mini-textbox"  size="60"
		                	 vtype="maxLength:20" required="true"  requiredErrorText="不能为空"/>
              	 
							<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
							<input id="noid" name="noid" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码ID：</td>
		                <td ><input name="codeid" class="mini-textbox"  size="60"
		                	 vtype="maxLength:20" required="true"  requiredErrorText="不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码PID：</td>
		                <td ><input name="pcodeid" class="mini-textbox"  size="60"
		                	 vtype="maxLength:20" required="false"  requiredErrorText="能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码名称：</td>
		                <td ><input name="name" class="mini-textbox"  size="60"
		                	 vtype="maxLength:20" required="true"  requiredErrorText="不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码说明：</td>
		                <td ><input name="remark" class="mini-textbox"  size="60"
		                	 vtype="maxLength:30" required="false"  requiredErrorText="能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >查询代码：</td>
		                <td ><input name="scearch" class="mini-textbox"  size="60"
		                	 vtype="maxLength:30" required="false"  requiredErrorText=""/>
		                </td>
		            </tr>
		            <tr>
		            	<td >显示排序：</td>
		                <td ><input name="sn" class="mini-spinner"  value="1" minValue=0" maxValue="999999" 
		                	 required="false"  requiredErrorText=""/>
		                </td>
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
        ////////////////////////
        // 渲染操作列
        function onActionRenderer(e) {
        	//
        	var row = e.row;
        	var codetypeid = row.codetypeid;
        	//
            var s = '<a href="javascript:editRow()" >编辑</a>';
                //s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:showDetailPageByTypeId()">代码映射关系</a>'; 
                s += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                s += '<a href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
        	}
            return "";
        };
        //////////////////////////////////////////////////////
        //
        function onKeyEnter(e) {
            search();
        };
		// 查询
		function search() {
		    var search_codetype = mini.get("search_codetype").getValue();
		    var search_codeid = mini.get("search_codeid").getValue();
		    var search_name = mini.get("search_name").getValue();
		    var search_scearch = mini.get("search_scearch").getValue();
		    //console.dir(grid);
		    var param =  {
		    	codetype: search_codetype
		    	,codeid: search_codeid
		    	,name: search_name
		    	,scearch: search_scearch
		    };
		    grid.load(param);
		};
		
		
		// 增加
		function addRow() {
			var data = {
				islocalnew: 1
				,databasetype : "Oracle"
				,port : "1521"
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
		// 删除
		function deleteRow(){
		    var row = grid.getSelected();
		    //
		    if (row) {
		    	var del = confirm("确实要删除吗?");
		    	if(del){
		    		alert("不允许删除");
		    	}
				//data["databasepassword"] = "";
		        //showEditWindow(data , 1);
		        
		    }
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
			var codetypeidInput = mini.getbyName("codetypeid");
			var ddorgInput = mini.getbyName("ddorg");
			if(codetypeidInput){
				if(!islocalnew){
					codetypeidInput.allowInput=false;
				} else {
					codetypeidInput.allowInput=true;
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
			var  url = "<%=path%>/syscode/ajax/savecode.json";
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
		//
    </script>
	</body>
</html>