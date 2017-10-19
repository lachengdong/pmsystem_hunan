<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String codetypeid = request.getParameter("codetypeid");
	if(null == codetypeid){
		codetypeid = "";
	}
%>

<html>
	<head>
		<title>代码类型映射</title>
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
						<a class="mini-button" iconCls="icon-upgrade" onclick="goBack()" plain="true">返回类型列表</a>
					</td>
					<td align="right">
						<input class="mini-textbox" id="key" class="mini-textbox" emptyText="源编码、目标编码" onenter="onKeyEnter" />
						<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
						ORGID：<input name="orgid" id="orgid" style="width:120px;" class="mini-treeselect" url="<%=path %>/org/ajax/list.action?1=1&unitlevel=3" 
		         			emptyText="目标单位" nullItemText="目标单位" textField="name" valueField="orgid" parentField="porgid" showTreeIcon="true" onvaluechanged="search"/>
			            &nbsp;&nbsp;&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
			            |&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					</td>
				</tr>
			</table>
		 </div>
    </div>
			
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listcodescheme.json?codetypeid=<%=codetypeid %>" 
	    	idField="codetypeid" sortField="codetypeid" sortOrder="asc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        		<div field="dccid" name="dccid" width="30" headerAlign="center" align="center"  allowSort="true">
               			代码映射编号
               		</div>
	        		<div field="codetype" name="codetype" width="30" headerAlign="center" align="right"  allowSort="true">
               			代码类型编号
               		</div>
               		<div field="codetypename" name="codetypename" width="30" headerAlign="center" align="left"  allowSort="true">
               			代码类型名称
               		</div>
               		<div field="codeid" name="codeid" width="30" headerAlign="center" align="right"  allowSort="true">
               			源编码
               		</div>
               		<div field="codecontent" name="codecontent" width="40" headerAlign="center" align="left"  allowSort="true">
               			源编码说明
               		</div>
               		<div field="targetid" name="targetid" width="30" headerAlign="center" align="left"  allowSort="true">
               			目标编码
               		</div>
               		<div field="orgid" name="orgid" width="30" headerAlign="center" align="left"  allowSort="true">
               			ORGID
               		</div>
               		<div field="remark" name="remark" width="30" headerAlign="center" align="left"  allowSort="true">
               			对应监区
               		</div>
			        <div width="40" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
    
	<div id="editWindow" class="mini-window" title="代码类型编辑" style="width:600px;height:320px" showModal="false" allowResize="true" allowDrag="true">
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
		                <td >代码映射编号：</td>
		                <td ><input name="dccid" class="mini-textbox"  size="60"
		                	 required="true" allowInput="false"  requiredErrorText="主键之一"/>
							<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码类型编号：</td>
		                <td ><input name="codetype" class="mini-textbox"  size="60" allowInput="false"
		                	 required="true"  requiredErrorText="主键之一"/>
		                </td>
		            </tr>
		            <tr>
		                <td >代码类型名称：</td>
		                <td ><input name="codetypename" class="mini-textbox"  size="60"  allowInput="false"
		                	 required="true"  requiredErrorText="类型名称不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >源编码：</td>
		                <td ><input name="codeid" class="mini-textbox"  size="60"  allowInput="true"
		                	 required="true"  requiredErrorText="源编码不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >源编码说明：</td>
		                <td ><input name="codecontent" class="mini-textbox"  size="60"  allowInput="true"
		                	 required="false"  requiredErrorText="能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >目标编码：</td>
		                <td ><input name="targetid" class="mini-textbox"  size="60"  allowInput="true"
		                	 required="true"  requiredErrorText="目标编码不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td> ORGID ：</td>
		                <td>
		                	<input name="orgid" class="mini-buttonedit" onbuttonclick="onOrgNameEdit"/>
		                	<input id="sdid" name="sdid" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td>对应监区：</td>
		                <td>
		                	<input name="remark" class="mini-textbox" />
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
            var s = '<a href="javascript:editRow()" >编辑</a>';
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
			var key = mini.get("key").getValue();
			var orgid = mini.get("orgid").getValue();
		    var param =  {orgid: orgid,key : key};
		    grid.load(param);
		};
		//
		
		// 增加
		var codetypeid = "<%=codetypeid%>";
		var codetypename = "<%=codetypeid%>";
		function addRow() {
			// 查询下一个资源ID
			var  url = "<%=path%>/dbms/ajax/getnextcodeschemeid.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	        	   if(1 === message["status"]){
	            	   //
	            	   var meta = message["meta"] || {};
	            	   var nextid = meta["nextid"] || "";
			    	   // 判断是否成功
					   var data = {
							islocalnew: 1
							,codetype : codetypeid
							,codetypename : codetypename
					    	,dccid : nextid
					   };
					   // 弹出 新增/编辑框
					   showEditWindow(data);
	               } else {
	               		alert(info);
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("网络请求失败");
			    };
			// 执行AJAX请求
			requestAjax(url,{},successCallback,errotCallback);
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
			var orgid = data.orgid || "";
			//
			var codetypeidInput = mini.getbyName("codetypeid");
			var ddorgInput = mini.getbyName("ddorg");
			var orgidInput = mini.getbyName("orgid");
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
			if(orgidInput){
				orgidInput.setText(orgid);
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
		    //
            var btnEdit = this;
            var sdidInput = mini.get("sdid");
            //
            mini.open({
                url: "<%=path%>/user/getOrgByUser.page?1=1", 
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
                            btnEdit.setValue(orgid);
                            btnEdit.setText(orgid);
                            // 设置隐藏域的值
                            sdidInput.setValue(orgid);
                            //
                            if(row.organization){
                            	//row.organization.name = orgname;
                            	//row.organization.orgid = orgid;
                            } else {
	                            //row.orgname = orgname;
	                            //row.orgid = orgid;
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
			var  url = "<%=path%>/dbms/ajax/savecodescheme.json";
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
		// 返回
		function goBack(){
			window.history.back();
		};
	</script>
	</body>
</html>