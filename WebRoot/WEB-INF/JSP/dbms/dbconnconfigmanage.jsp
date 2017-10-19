<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//
	String userid = "";
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>数据库管理</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
  		<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
  		<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
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
<body>
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					&nbsp;&nbsp;
						数据库类型：
						<select id="search_databasetype" name="search_databasetype" onchange="onKeyEnter()">
	                      	<option value="" >所有</option>
							<option value="Oracle">Oracle</option>
							<option value="MySQL">MySQL</option>
							<option value="Microsoft SQL Server" >Microsoft SQL Server</option>
							<option value="Microsoft SQL Server 2005" >Microsoft SQL Server 2005</option>
							<option value="Sybase">Sybase</option>
							<option value="DB2" >DB2</option>
							<option value="Informix" >Informix</option>
							<option value="Dameng" >Dameng</option>
			             </select>
			            &nbsp;&nbsp;
		  				IP地址：
		  				<input class="mini-textbox" type="text" id="search_ddip" name="search_ddip" size="16" onenter="onKeyEnter"/>&nbsp;&nbsp;
						端口号：<input class="mini-textbox" type="text" id="search_port" name="search_port" size="4" onenter="onKeyEnter"/>
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
	    	url="<%=path %>/dbms/ajax/listdbconnconfig.json" 
	    	idField="ddid" sortField="ddid"  sortOrder="asc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        <%--
	        		<div type="checkcolumn" width="25"></div>
	         --%>
	        		<div field="ddid" name="ddid" width="30" headerAlign="center" align="center"  allowSort="true">
               			编号
               		</div>
               		<div field="ddname" name="ddname" width="50" headerAlign="center" align="center"  allowSort="true">
               			数据库描述
               		</div>
               		<div field="databasetype" name="databasetype" width="60" headerAlign="center" align="left"  allowSort="true">
               			数据库类型
               		</div>
               		<div field="ddip" name="ddip" width="40" headerAlign="center" align="left"  allowSort="true">
               			数据库IP
               		</div>
               		<div field="port" name="port" width="30" headerAlign="center" align="center"  allowSort="true">
               			数据库端口
               		</div>
           			<div field="databasename" name="databasename" width="60" headerAlign="center" align="center"  allowSort="true">
               			数据库名称（监听名称）
               		</div>
               		<div field="ddorg" name="ddorg" width="40" headerAlign="center"  align="center"  allowSort="true">
               			所属机构
               		</div>
               		<div field="databaseuser" name="databaseuser" width="30" headerAlign="center" align="center"  allowSort="true">
               			数据库用户名
               		</div>
               		<%--
               		<div field="databasepassword" name="databasepassword" width="30" headerAlign="center" align="center" >
               			数据库密码
               		</div>
               		 --%>
               		<div field="crypt" name="crypt" width="30" headerAlign="center" align="center" renderer="onShowXingxing" >
               			数据库密码
               		</div>
			        <div width="40" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
    
	<div id="editWindow" class="mini-window" title="数据库连接配置" style="width:600px;height:320px"
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
			           			|            
			            		<a class="mini-button" onclick="tryLink()" plain="true" style="width:120px">测试连接</a>            
			           		</td>
		                </tr>
		            </table>           
		        </div>
		        <table style="width:100%;">
		            <tr>
		                <td >记录编号：</td>
		                <td ><input name="ddid" class="mini-textbox"  size="60"
		                	 vtype="minLength:3;maxLength:6" required="true"  requiredErrorText="3-6个字符"/>
		                </td>
		            </tr>
		            <tr>
		                <td >数据库描述：</td>
		                <td ><input name="ddname" class="mini-textbox"  size="60"  vtype="maxLength:32"/></td>
		            </tr>
		            <tr>
		                <td >数据库类型 ：</td>
		                <td >
		                		<input name="databasetype" class="mini-combobox" size="60"
		                		 popupWidth="200" 
		                		 valueField="typename" textField="typename"
		                		 data="databasetypeArray" required="true"  requiredErrorText="类型不能为空"/>
								<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td> IP地址 ：</td>
		                <td><input name="ddip" class="mini-textbox"  size="40" 
		                 vtype="maxLength:30" required="true"  requiredErrorText="IP不能为空"/></td>
		            </tr>
		            <tr>
		                <td> 端口号 ：</td>
		                <td><input name="port" class="mini-textbox"  
		                vtype="int;maxLength:5" required="true"  requiredErrorText="端口不能为空"/></td>
		            </tr>
		            <tr>
		                <td >数据库名称：</td>
		                <td ><input name="databasename" class="mini-textbox"  size="60"
		                	 vtype="maxLength:30" required="true"  requiredErrorText="名称不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td>数据库用户名：</td>
		                <td><input name="databaseuser" class="mini-textbox" 
		                 vtype="maxLength:30" required="true"  requiredErrorText="帐号不能为空"/></td>
		            </tr>
		            <tr>
		                <td>数据库密码：</td>
		                <td><input name="databasepassword"  class="mini-password"/></td>
		            </tr>
		            <tr>
		                <td>所属机构：</td>
		                <td>
		                	<input name="ddorg" class="mini-buttonedit" onbuttonclick="onOrgNameEdit"/>
		                	<input id="sdid" name="sdid" class="mini-hidden"/>
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
    	var databasetypeArray = [
    			{typename:"Oracle"}
    			,{typename:"MySQL"}
    			,{typename:"Microsoft SQL Server"}
    			,{typename:"Microsoft SQL Server 2005"}
    			,{typename:"Sybase"}
    			,{typename:"DB2"}
    			,{typename:"Informix"}
    			,{typename:"Dameng"}
    		];
    	//
    	var userid = "<%=userid %>";
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        ////////////////////////
        // 渲染操作列
        function onActionRenderer(e) {
            var s = '<a class="Edit_Button" href="javascript:editRow()" >编辑</a>';
                //s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
        // 显示一些星号(*)
        function onShowXingxing(e){
        	//
        	var record = e.record || {};
        	var databasepassword = record.databasepassword || "";
        	var len = databasepassword.length || 0;
        	//
        	var s = "";
        	for(var i=0; i<len; i++){
        		s +="*";
        	}
        	//
        	return s;
        };
        
        function onKeyEnter(e) {
            search();
        };
		// 查询
		function search() {
		    var search_databasetype = document.getElementById("search_databasetype").value || "";
		    //
		    
		    var search_ddip = mini.get("search_ddip").getValue();
		    var search_port = mini.get("search_port").getValue();
		    //console.dir(grid);
		    var param =  {
		    	databasetype: search_databasetype
		    	,ddip: search_ddip
		    	,port: search_port
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
			var ddidInput = mini.getbyName("ddid");
			var ddorgInput = mini.getbyName("ddorg");
			if(ddidInput){
				if(!islocalnew){
					ddidInput.allowInput=false;
				} else {
					ddidInput.allowInput=true;
				}
			}
			if(ddorgInput){
				if(ddorg){
					ddorgInput.setText(ddorg);
				} else {
					ddorgInput.setText("");
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
		// 编辑部门
        function onOrgNameEdit(e) {
			//
		    var row = grid.getSelected() || {};
		    if (!row) {
		    	//alert("没有选择记录!");
		    	//return;
		    }
		    //
		    var userid2 = userid || row.userid;
		    //
            var btnEdit = this;
            var sdidInput = mini.get("sdid");
            //
            mini.open({
                url: "<%=path%>/user/getOrgByUser.page?1=1" + "&userid=" + userid2,
                showMaxButton: false,
                title: "选择部门",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                        	var orgname = data.name || "";
                        	var orgid = data.orgid || "";
                            btnEdit.setValue(orgname);
                            btnEdit.setText(orgname);
                            // 设置隐藏域的值
                            sdidInput.setValue(orgid);
                            //
                            if(row.organization){
                            	row.organization.name = orgname;
                            	row.organization.orgid = orgid;
                            } else {
	                            row.orgname = orgname;
	                            row.orgid = orgid;
                            }
                        }
                    }
                }
            });   
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
			var  url = "<%=path%>/dbms/ajax/savedbconnconfig.json";
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
			grid.loading("正在测试，请等待10～30秒......");
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