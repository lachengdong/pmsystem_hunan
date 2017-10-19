<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePathLessSlash = request.getScheme()+"://"+request.getServerName()
						+":"+request.getServerPort()+path ; // 少1个斜线
	String basePath = basePathLessSlash + "/";
%>
<html>
	<head>
		<title>数据导出-XML</title>
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
	<%--
		表格是下载列表. 按钮是用于新增任务的.
	 --%>
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left" >
					&nbsp;&nbsp;&nbsp;&nbsp;
						导出文件下载列表：
						<%--
						style="display: none;"
		  				<input id="search_ddctype" name="search_ddctype" class="mini-combobox" 
		  					style="width:120px;" popupWidth="200" 
			                	textField="ddcname" valueField="ddcid"
								url="<%=path %>/dbms/ajax/listdbscheme.json?ddctype=1"
		  				 />
			            &nbsp;&nbsp;
		  				方案名称：
		  				<input class="mini-textbox" type="text" id="search_ddcname" name="search_ddcname" size="16" onenter="onKeyEnter"/>&nbsp;&nbsp;						
		 				<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">过滤</a>
						 --%>
					</td>
					<td align="right">
						<a class="mini-button" iconCls="icon-downgrade"
							 onclick="submitToCourt()" plain="true">提交法院</a>
			            &nbsp;&nbsp;						
						<a class="mini-button" iconCls="icon-download"
							 onclick="" href="<%=path %>/dbms/dataexportadd.page?ddctype=1" plain="true">新增数据导出请求</a>
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
	    	url="<%=path %>/dbms/ajax/listdataexport.json" 
	    	idField="ddcid" sortField="createdate" sortOrder="desc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="25"></div>
               		<div field="ddcname" name="ddcname" width="50" headerAlign="center" align="center"  allowSort="true">
               			数据方案
               		</div>
	        		<div field="filerealname" name="filerealname" width="80" headerAlign="center" align="left"  allowSort="true">
               			文件名称
               		</div>
               		<div field="createdate" name="createdate" renderer="onDateRenderer"
               			 width="40" headerAlign="center" align="center"  allowSort="true">
               			创建日期
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
		                	 vtype="minLength:3;maxLength:6" required="true"  requiredErrorText="3-30个字符"/>
		                </td>
		            </tr>
		            <tr>
		                <td >方案名称：</td>
		                <td ><input name="ddcname" class="mini-textbox"  size="60"
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
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
        	}
            return "";
        };
        // 渲染操作列
        function onActionRenderer(e) {
            var s = '<a class="" href="javascript:downFile()" >下载</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="" href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
        // 删除文件
        function downFile(){
		    var row = grid.getSelected();
		    //
		    if (!row) {
		    	alert("请选择文件!");
		    	return;
		    }
	    	// 拼接文件路径
	    	var filepath = row.filepath || "";
	    	//
	    	if(!filepath){
		    	alert("文件不存在!");
	    		return;
	    	}
	    	//
	    	var startSlash = filepath.indexOf("/");
	    	var allPath = "";
	    	var basePathLessSlash = "<%=basePathLessSlash%>";
	    	// 不是斜线开头
	    	if(0 != startSlash){
	    		basePathLessSlash += "/";
	    	}
	    	// 下载的文件路径
	    	allPath = basePathLessSlash + filepath;
	    	//
	    	iframeDownloadFile(allPath);
        };
        // 通过 Iframe下载文件
        function iframeDownloadFile(allPath){
        	//
			var download_iframe = document.getElementById('download_iframe');
			if(!download_iframe){
				//
				var d = document.createElement("div");
				d.style.display="none";
				//
				var i = document.createElement("iframe");
				i.id="download_iframe";
				//
				d.appendChild(i);
				//
				document.documentElement.appendChild(d);
				download_iframe = document.getElementById('download_iframe');;
			}
			//
			window.onerror = function(){
				alert("下载失败");
			};
			try {
				//delete download_iframe.src;
				download_iframe.src = allPath;
			} catch(ex){
				alert("下载失败!");
			}
			return false;
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
		
		
		// 提交法院
		function submitToCourt(){
		    var rows = grid.getSelecteds();
		    
		    if (!rows) {
                alert( "请至少选中一条记录！");
                return false;
		    }
	    	var del = confirm("确实要提交到法院吗?");
	    	if(!del){
		    	return false;
	    	}
	    	

            var ids = [];
            for (var i = 0, l = rows.length; i < l; i++) {
                var r = rows[i];
                ids.push(r["fileid"]);
            }
	    	var fileid = ids.join(',');
	    	var data = {
	    		fileid : fileid
	    	};
	    	//
			// 界面
			grid.loading("正在提交到法院，请稍候...");
			var  url = "<%=path%>/dbms/ajax/submitToCourt.json";
			var successCallback = function (message) {
			       	        	   
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
	               		alert(info);
	               		grid.unmask();
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
		
		// 删除
		function deleteRow(){
		    var row = grid.getSelected();
		    //
		    if (!row) {
		    	return false;
		    }
	    	var del = confirm("确实要删除吗?");
	    	if(!del){
		    	return false;
	    	}
	    	//
	    	var fileid = row["fileid"];
	    	var data = {
	    		fileid : fileid
	    		,
	    		delflg : 1
	    	};
	    	//
			// 界面
			grid.loading("正在删除，请稍候...");
			var  url = "<%=path%>/dbms/ajax/updatedataexport.json";
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
		// 编辑部门
        function onOrgNameEdit(e) {
			//
		    var row = grid.getSelected();
		    if (!row) {
		    	alert("没有选择记录!");
		    	return;
		    }
		    //
		    var userid = row.userid;
		    //
            var btnEdit = this;
            mini.open({
                url: "<%=path%>/user/getOrgByUser.page?1=1" + "&userid="+userid,
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
			var  url = "<%=path%>/dbms/ajax/savedbscheme.json";
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