<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<html>
	<head>
		<title>代码类型管理</title>
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
		  				代码类型编号：
		  				<input class="mini-textbox" type="text" id="search_codetypeid" name="search_codetypeid" size="16" onenter="onKeyEnter"/>	
			            &nbsp;&nbsp;
		  				代码类型名称：
		  				<input class="mini-textbox" type="text" id="search_codetypename" name="search_codetypename" size="16" onenter="onKeyEnter"/>&nbsp;&nbsp;
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
			
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listcodetype.json" 
	    	idField="codetypeid" sortField="codetypeid" sortOrder="asc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        <%--
	        		<div type="checkcolumn" width="25"></div>
	         --%>
	        		<div field="codetypeid" name="codetypeid" width="30" headerAlign="center" align="right"  allowSort="true">
               			代码类型编号
               		</div>
               		<div field="codetypename" name="codetypename" width="60" headerAlign="center" align="left"  allowSort="true">
               			代码类型名称
               		</div>
			        <div width="60" headerAlign="center" align="center"	allowSort="false"
			        	 renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
    
	<div id="editWindow" class="mini-window" title="代码类型编辑" style="width:600px;height:320px"
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
		                <td >代码类型编号：</td>
		                <td ><input name="codetypeid" class="mini-textbox"  size="60"
		                	 vtype="minLength:3;maxLength:7" required="true"  requiredErrorText="3-7个字符"/>
		                	 <input name="islocalnew" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码类型名称：</td>
		                <td ><input name="codetypename" class="mini-textbox"  size="60"
		                	 vtype="maxLength:30" required="true"  requiredErrorText="名称不能为空"/>
		                </td>
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
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        ////////////////////////
        // 渲染操作列
        function onActionRenderer(e) {
        	//
        	var row = e.row;
        	var codetypeid = row.codetypeid;
        	//
            var s = '<a href="javascript:editRow()" >修改名称</a>';
                //s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:showDetailPageByTypeId()">代码映射关系</a>'; 
                s += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                s +='<a href="<%=path %>/dbms/showcodemappingbytype.page?codetypeid='
                	+ encodeURIComponent(codetypeid)
                	+'">值映射关系</a>';
                s += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                s += '<a href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
        //
        function onKeyEnter(e) {
            search();
        };
		// 查询
		function search() {
		    var search_codetypeid = mini.get("search_codetypeid").getValue();
		    var search_codetypename = mini.get("search_codetypename").getValue();
		    //console.dir(grid);
		    var param =  {
		    	codetypeid: search_codetypeid
		    	,codetypename: search_codetypename
		    };
		    grid.load(param);
		};
		//
		
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
		// 根据typeid,显示对应的子项页面
		function showDetailPageByTypeId(){
		    var row = grid.getSelected();
		    //
		    if (row) {
		    	return;
		    }
	    	//
	    	var codetypeid = row.codetypeid || "";
	    	if(!codetypeid){
	    		alert("数据错误: codetypeid不正确.");
	    		return;
	    	}
	    	// 在新页面显示
		    // ... 直接在a标签连接中指定地址即可;
	    	
        	var codetypeid = row.codetypeid;
        	//
            var url = "<%=path %>/dbms/showcodemapping.page?codetypeid=" + encodeURIComponent(codetypeid); 
            // 修改页面地址
            window.location = url;
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
			var  url = "<%=path%>/dbms/ajax/savecodetype.json";
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
		// 测试数据库连接
		function tryLink(){
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
			var  url = "<%=path%>/dbms/ajax/testdbconfiglink.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	        	   //
	        	   alert(info);
	               grid.unmask();
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("测试失败");
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
		        async:false,
			    success: function (message) {
			    	//if(window["JSON"]){
			    	//	message = mini.decode(message) || {};
			    	//} else { // IE6, IE7
	        	   	//	message = eval("("+ message + ")") || {};
			    	//}
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