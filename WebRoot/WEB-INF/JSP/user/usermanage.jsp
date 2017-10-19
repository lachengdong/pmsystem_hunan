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
		<title>用户管理</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"	type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<!--
		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
		-->
		<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		.backgray {
		}
		.backgray *{
			background-color: #DDD !important;
		}
		</style>
	</head>
	<body>
       <div class="mini-splitter" style="width:100%;height:100%;padding:0;" borderStyle="border:1px solid #99bce8;">
       		<!-- 左边的树 -->
           <div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
			<!--Tree-->
			<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
	            <span style="padding-left:5px;">名称：</span>
	            <input class="mini-textbox" vtype="maxLength:30;"  id="keyorg" style="width:80px;" onenter="onKeyEnter"/>
	            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="searchOrg()">查找</a>                  
	        </div>
	        <div  class="mini-fit" >
			<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
				url="<%=path %>/org/ajax/list.action?1=1" showTreeIcon="true"
			    expandOnLoad="0" resultAsTree="false"
			    textField="name" idField="orgid" parentField="porgid"
			    contextMenu="#treeMenu"
			    onnodeselect="onTreeNodeSelect"  onload="selectPrevNode"
			>
			</ul>
			</div>
           </div>
           <!-- 右边的显示区域 -->
           <div showCollapseButton="false" style="overflow: auto;padding:0px;"  borderStyle="border:solid 1px #aaa;">
		    <div region="north" height="40"  showSplit="false" showHeader="false" showCollapseButton="false">
			    <div class="mini-toolbar" style="padding:2px;border:0;">
			       		<div class="crud-search">
							<table style="width:98%;">
								<tr>
									<td align="left">
										<span>姓名/帐号：</span>
										<input type="text" id="key" />
										<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查找</a>
									</td>
									<td align="right">
										<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
										<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
									</td>
								</tr>
							</table>
						</div>
			    </div>
			</div>
			
		    <div  class="mini-fit" showCollapseButton="false">
            	
		    <!-- 显示表格 -->
				<div class="crud-grid mini-fit">
					<div id="datagrid1" class="mini-datagrid"
						idField="id"
						sortField="userid" sortOrder="asc"
						allowResize="false" pageSize="20" allowCellEdit="true"
						allowCellSelect="true" multiSelect="true"
						allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
						>
						<div property="columns">
							<div field="userid" width="40" headerAlign="center"
								allowSort="true" align="center" >
								帐号
							</div>
							<div field="name" width="30" headerAlign="center" vtype="maxLength:10" 
								allowSort="true" align="left" >
								姓名
							</div>
							<div field="organization.name" width="30" headerAlign="center"
								allowSort="true" align="left" >
								单位
							</div>
							<div field="duty" width="30" headerAlign="center"
								allowSort="true" align="left" >
								职务
							</div>
							<div field="gender" width="15" renderer="onGenderRenderer"
								align="center" headerAlign="center">
								性别
							</div>
							<div field="mobile" width="30" allowSort="true" headerAlign="center" align="center">
								电话
							</div>
							<div field="bizroleName" width="45" headerAlign="center"
								allowSort="true" align="left" renderer="onBizRoleNameRenderer">
								角色
							</div>
							<div name="operation" width="50" headerAlign="center" 
								align="center" renderer="onActionRenderer" cellStyle="padding:0;">
								操作
							</div>
						</div>
					</div>
				</div>
            </div>
       	</div>
   	</div>	

	<div id="editWindow" class="mini-window" title="编辑用户" style="width:510px;height:270px"
		    showModal="true" allowResize="true" allowDrag="true"
		    >
		    <!-- 弹出框 -->
		    <div id="editform" class="form" >
		        <input class="mini-hidden" name="id"/>
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
		                <td >用户帐号 ：</td>
		                <td >
		                		<input name="userid" class="mini-textbox" size="40"
		                		 vtype="maxLength:20" required="true"  requiredErrorText="帐号不能为空"/>
								<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
		                </td>
		                <td >姓名：</td>
		                <td ><input name="name" class="mini-textbox"  size="40"
		                	 vtype="maxLength:20" required="true"  requiredErrorText="姓名不能为空"/>
		                </td>
		            </tr>
		            <tr>
		                <td >移动电话：</td>
		                <td ><input name="mobile" class="mini-textbox"  size="40"  vtype="int;maxLength:15"/></td>
		                <td>性别：</td>
		                <td><input name="gender" class="mini-combobox"  size="40" data="[{id:'1', text:'男'},{id:'2', text:'女'}]"/></td>
		            </tr>
		            <tr>
		                <td>电子邮箱：</td>
		                <td><input name="email" class="mini-textbox"  vtype="email"/></td>
		                <td>职务：</td>
		                <td><input name="duty" class="mini-textbox" /></td>
		            </tr>
		            <tr>
		                <td>用户密码：</td>
		                <td><input name="newpassword" class="mini-textbox" /> (不修改请留空)</td>
		                <td>单位：</td>
		                <td>
		                	<input name="orgname" class="mini-buttonedit" onbuttonclick="onOrgNameEdit"/>
							<input id="orgid" name="orgid" class="mini-hidden"/>
		                </td>
		            </tr>
		            <tr>
		                <td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
		                <td colspan=3><input name="remark" style="width:220px;" class="mini-textarea" /></td>
		            </tr>
		            <tr>
		                <td>是管理员：<input id="isadmin" name="isadmin" class="mini-checkbox" /></td>
		                <td>指定审批人：<input id="fjq" name="fjq" class="mini-checkbox" /></td>
		            </tr>
		        </table>
		    </div>
	</div>
    	<script type="text/javascript">
    	// 解析并初始化
        mini.parse();
    	// 获取grig对象
    	var tree = mini.get("tree1");
        var grid = mini.get("datagrid1");
    	var gridurl="<%=path %>/user/ajax/list.action";
    	//
    	grid.setUrl(gridurl);
    	// 加载数据
        //grid.load();
        ///////////////////////////////////////////////////////
        // 选择树节点时,默认是查看信息
		function onTreeNodeSelect(e) {
		    var node = e.node;
		    if(null == node){
			    e.cancel = true;
			    return false;
		    } else {
		    	//
		    	window["userm_org"] = node;
		    }
		    var isLeaf = e.isLeaf;
		    if (isLeaf) {
		    }
			var orgid = node.orgid;
			var islocalnew = node.islocalnew;
			if(orgid && !islocalnew){
				// 保存本地 cookie
				document.cookie="userm_orgid="+escape(orgid); 
			}
			showDataToGrid(node);
		    e.cancel = true;
		    return false;
		};
		// 将数据显示(要显示的节点,父节点)
		function showDataToGrid(node, pnode){
			if(!node){
				return;
			}
			
		    var param = window["param"] || {};
		    	param["orgid"] = node.orgid;
		    	param["orgname"] = node.name;
		    	param["pageIndex"] = 0;
		    window["param"] = param;
			//
	    	// 加载数据
	        grid.load(param);
		};
        // 渲染性别
        function onGenderRenderer(e) {
        	var ISMENU_TEXT = [{ id: 1, text: '男' }, { id: 2, text: '女'}];
            for (var i = 0, l = ISMENU_TEXT.length; i < l; i++) {
                var g = ISMENU_TEXT[i];
                if (g.id == e.value) return g.text;
            }
            return "男";
        };
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        };
        function onBizRoleNameRenderer(e){
        	var row = e.row;
            if(!row){
            	return "";
            }
            //
            var bizroleName = row.bizroleName;
            if(bizroleName){
            	return bizroleName;
            } else {
            	bizroleName = "";//"未授权用户";
            }
            //
            var bizroles = row.bizroles;
            if(!bizroles || !bizroles.length){
            	return bizroleName;
            }
            // 先遍历, 如果有其他角色,则去除普通用户
            var len = bizroles.length;
            if(len > 1){
            	//
            	var nroles = [];
            	var serialRoleId = -123456;
            	//
            	$.each(bizroles, function(i,v){
	            	//
	            	if(!v){
	            		return;
	            	}
	            	//
	            	var roleid = v.roleid;
	            	if(serialRoleId == roleid){
	            		// 不添加
	            	} else {
	            		// 不是普通用户则添加
	            		nroles.push(v);
	            	}
            	});
            	//
            	if(nroles && nroles.length){
            		bizroles = nroles;
            	}
            }
            // 遍历
            $.each(bizroles, function(i,v){
            	//
            	if(!v){
            		return;
            	}
            	//
            	var name = v.name || "";
            	//
            	if(i > 0){
            		bizroleName += ";";
            	}
            	bizroleName += name;
            });
            //
            row.bizroleName = bizroleName;
            return bizroleName;
        };
        
        // 渲染操作菜单
        function onActionRenderer(e) {
	            var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
	            var gkzxtempid=record.gkzxtempid;
	            var s ="";
		            s=s+'<a class="New_Button" href="javascript:edit(\'user/editRoleByUser.page?1=1\',\'400\',\'400\',\'18\',\'null\')">角色情况</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		            s=s+'<a class="New_Button" href="javascript:editRow()">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		            //s=s+'<a class="New_Button" href="javascript:lockUser()">调离</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		            s=s+'<a class="New_Button" href="javascript:removeRow()">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;';
	            return s;
	    };
        //////////////////////////////////////////////////////
		function search() {
		    var key = document.getElementById("key").value;
		    //console.dir(grid);
		    var param = window["param"] || {};
		    	param["key"] = key;
		    	param["pageIndex"] = 0;
		    window["param"] = param;
		    grid.load(param );
		};
		// 增加
		function addRow() {
			var param = window["param"] || {};
			var data = {
				islocalnew: 1
				, newpassword : '1'
				, gender : 1
				,orgid : param["orgid"]
				,orgname : param["orgname"]
			};
			if(!data.orgid){
				alert("请选择部门");
				return false;
			}
			// 弹出 新增/编辑框
			showEditWindow(data);
			// 对表格执行操作
		    var newRow = { name: "增加" };
		    //grid.addRow(data, 0);
		};
		function rowdblclickfunction(e){
			editRow();
		};
		// 编辑
		function editRow() {
		    var row = grid.getSelected();
		    if (row) {
				var param = window["param"] || {};
				var data2 = {
					orgid : param["orgid"]
					,orgname : param["orgname"]
				};
				//$.extend(data, row);
		    	//window["console"] && console.dir(row);
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
			
			
			var islocalnew = data.islocalnew;
			var organization = data.organization || {};
			
			var useridInput = mini.getbyName("userid");
			var orgnameInput = mini.getbyName("orgname");
			var orgidInput = mini.getbyName("orgid");
			var isadminInput = mini.getbyName("isadmin");
			var fjqInput = mini.getbyName("fjq");
			if(useridInput){
				if(!islocalnew){
					useridInput.allowInput=false;
					// 置灰
					// backgray
					//useridInput.addCls("backgray");
					//useridInput.addCls("color:  red !important;");
					//useridInput.cls = ("backgray");
					//useridInput.getEl().style.color = "red";
					var className = useridInput.getEl().className;
					className = className.replace(" backgray",""); // 去掉多余的
					className = className.replace("backgray",""); // 去掉多余的
					className = className + " backgray";
					useridInput.getEl().className = className;//
					window.useridInput = useridInput;
				} else {
					useridInput.allowInput=true;
					useridInput.getEl().className = useridInput.getEl().className.replace("backgray","");
				}
			}
			//form.loading();
			// 设置数据
	        form.setData(data);
			//
			if(orgnameInput){
				var orgname = organization.name  || "";
				if(islocalnew){
					orgname = orgname || data.orgname;
				}
				orgnameInput.setValue(orgname);
				orgnameInput.setText(orgname);
			}
			//
			if(orgidInput){
				var orgid = organization.orgid || data.orgid || "";
				if(islocalnew){
					orgid = orgid || data.orgid;
				}
				orgidInput.setValue(orgid);
			}
			if(isadminInput){
				var int1 = data.int1;
				if(1 == int1){
					// 勾选是否管理员
					isadminInput.setValue(true);
				} else {
					isadminInput.setValue(false);
				}
			}
			if(fjqInput){
				var int2 = data.int2;
				if(1 == int2){
					// 勾选是否管理员
					fjqInput.setValue(true);
				} else {
					fjqInput.setValue(false);
				}
			}
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
		    var row = grid.getSelected();
		    if (row) {
		    	//
		    	var userid = row.userid;
		    	var cfm = window.confirm("确定删除用户?");
		    	//
		    	if(cfm){
		    		// 请求删除
		    		var param = {
		    			userid: userid
		    			,delflag: "1"
		    		};
		    		ajaxDeleteUser(param);
		    	}
		    }
		};
		// 调离用户
		function lockUser() {
		    var row = grid.getSelected();
		    if (row) {
		    	//
		    	var userid = row.userid;
		    	var cfm = window.confirm("确定调离用户?");
		    	//
		    	if(cfm){
		    		// 请求调离
		    		var param = {
		    			userid: userid
		    			,islocked: "1"
		    		};
		    		ajaxLockUser(param);
		    	}
		    }
		};
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
                url: "<%=path%>/user/editOrgByUser.page?1=1" + "&userid="+userid,
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
			var  url = "<%=path%>/user/ajax/updateuserinfo.json";
			var successCallback = function (message) {
			       	        	   
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
						editWindow.hide();
			        	grid.reload();
			        	alert('保存成功');
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
		//
		
		function ajaxLockUser(param){
			var data = param;
			// 界面
			grid.loading("正在调离，请稍候......");
			//
			var  url = "<%=path%>/user/ajax/lockuser.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               alert(info);
	               if(1 === message["status"]){
			    		// 判断是否成功
			        	grid.reload();
	               } else {
	               		grid.unmask();
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("调离失败");
	               	grid.unmask();
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		};
		function ajaxDeleteUser(param){
			var data = param;
			// 界面
			grid.loading("正在删除，请稍候......");
			//
			var  url = "<%=path%>/user/ajax/deleteuser.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               alert(info);
	               if(1 === message["status"]){
			    		// 判断是否成功
			        	grid.reload();
	               } else {
	               		grid.unmask();
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("删除失败");
	               	grid.unmask();
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		};
	    
		// 弹出编辑框
		function edit(myurl,mywidth,myhight,menuid,tanchuleixing) {
        	var grid= mini.get("datagrid1");
            var row = grid.getSelected();
            if (row) {
            	var userid = row.userid;
            	myurl=myurl+'&menuid='+menuid+"&operatetype=edit&id="+userid;
                var win=mini.open({
                    url: myurl,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "配置用户角色", width: mywidth, height: myhight,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: userid, mymenuid: menuid ,myoperatetype:'edit'};
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
		function selectPrevNode(){
			
			var doSelect = function(){
				//获取cookie字符串 
				var strCookie=document.cookie; 
				//将多cookie切割为多个名/值对 
				var arrCookie=strCookie.split("; "); 
				var orgid = null; 
				//遍历cookie数组，处理每个cookie对 
				for(var i=0;i<arrCookie.length;i++){ 
					var arr=arrCookie[i].split("="); 
					//找到cookie，并返回它的值 
					if("userm_orgid"==arr[0]){ 
						orgid=unescape(arr[1]); 
						break; 
					} 
				} 
				if(orgid){
			    	var tree = mini.get("tree1");
			    	var node = tree.getNode ( orgid );
			    	if(!node){
			    		node = tree.getRootNode()||{};
			    		node = node["children"]||[];
			    		node = node[0];
			    	}
			    	if(!node){
			    		return;
			    	}
			    	//
					tree.selectNode ( node );
					// 展开
					expand(tree,node);
					//tree.expandNode ( pnode );
					//tree.expandNode ( node );
					//debug(node);
				} else {
			    	var tree = mini.get("tree1");
		    		node = tree.getRootNode()||{};
		    		node = node["children"]||[];
		    		node = node[0];
			    	if(!node){
			    		return;
			    	}
					// 展开
					expand(tree,node);
					tree.selectNode( node );
				}
			};
			window.setTimeout(doSelect, 100);
		};
		// 展开树
		function expand(tree, node){
	    	if(!node || !tree || !node.orgid){
	    		return;
	    	}
	    	var porgid = node.porgid;
	    	if(porgid && porgid != "0" && porgid != "-1"){
			    var pnode = tree.getNode ( node.porgid );
			    expand(tree, pnode);
	    	}
		    tree.expandNode(node);
		}; 
		//
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
		//过滤树
        function searchOrg() {
            var key = mini.get("keyorg").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase(); 
                tree.filter(function (node) {
                    var text = node.name?node.name.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        };
        function onKeyEnter(e) {
            searchOrg();
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