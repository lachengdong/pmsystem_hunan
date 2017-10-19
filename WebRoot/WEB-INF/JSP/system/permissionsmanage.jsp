<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.permissions/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.permissions/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>权限管理</title>
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
		<!--Layout-->
		<div class="mini-layout" style="width:100%;height:100%;">
		    <div title="center" region="center" >
		        <!--Splitter-->
		        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
		        	<!-- 左边的树 -->
		            <div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
		            	<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
				            <span style="padding-left:5px;">名称：</span>
				            <input class="mini-textbox" vtype="maxLength:30;"  id="key" style="width:80px;" onenter="onKeyEnter"/>
				            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="search()">查找</a>                  
				        </div>
						<!--Tree-->
						<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
							url="<%=path %>/permissions/ajax/list.json?1=1" showTreeIcon="true"
						    resultAsTree="false"
						    textField="spdiscribe" idField="spid" parentField="spparent"
						    contextMenu="#treeMenu"
						    onnodeselect="onTreeNodeSelect"
						>
						</ul>
		            </div>
		            <!-- 右边的显示区域 -->
		            <div showCollapseButton="false" style="overflow: auto">
					    <div region="north" height="40"  showSplit="false" showHeader="false" showCollapseButton="false" style="">
							
						</div>
					    <div class="mini-toolbar" style="padding:2px;border:0;border-bottom:1px solid #99bce8;">
					        <table style="width:100%;">
					            <tr>
					            <td style="width:100%;text-align:right">
									<a class="mini-button" iconCls="icon-save"  onclick="saveData()" plain="true">保存权限信息</a>
					                <a class="mini-button" iconCls="icon-add" onclick="onAddNode()"	plain="true">新增子权限</a>
					                <a class="mini-button" iconCls="icon-add" onclick="onAddNode(e, true)"	plain="true">新增顶级权限</a>
									<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					            </td>
					            <td style="white-space:nowrap;">
					            
					            </td>
					            </tr>
					        </table>
					    </div>
						
					    <div  class="mini-fit" showCollapseButton="false">
			            	<div id="editform" class="form" style="margin-left: 10px;">
								<table class="form-table" border="0" cellpadding="1" cellspacing="2" style="width:500px;">
								    <tr>
								        <td style="width:90px;">权限编号：</td>
								        <td>
								            <input name="spid" class="mini-textbox" style="width:150px;" />
								            <input name="spparent"  class="mini-hidden"/>
								            <input name="islocalnew"  class="mini-hidden"/>
								        </td>
								        <td style="width:90px;">权限名称：</td>
								        <td>
								            <input name="spdiscribe" class="mini-textbox" style="width:150px;" />
								        </td>
								    </tr>
								   
								    <tr>
								        <td style="width:90px;">菜单编号：</td>
								        <td>
								            <input name="smid" class="mini-textbox" style="width:150px;" />
								        </td>
								        <td style="width:90px;">报表编号：</td>
								        <td>
								            <input name="rid" class="mini-textbox" style="width:150px;" />
								        </td>
								    </tr>
								    
								   
								    <tr>
								        <td style="width:90px;">创建者ID：</td>
								        <td >
								            <input name="createmender" class="mini-textbox"  style="width:150px;" />
								            <input name="opflag" class="mini-hidden" type="hidden" />
								        </td>
								        <td style="width:90px;">创建时间：</td>
								        <td >
								            <input name="createtime" class="mini-textbox"  style="width:150px;" valuechanged="valuechanged" />
								        </td>
								    </tr>
								     <tr>
								        <td style="width:90px;">权限排序：</td>
								        <td colspan="3">
								            <input name="sporderby" class="mini-textbox" style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">备注信息：</td>
								        <td colspan="3" >
								            <input name="remark" class="mini-textarea" style="width:100%;height:60px;"/>
								        </td>
								    </tr>
								</table>
			            	</div>
			            </div>
		        	</div>
		    	</div>
		    </div>
		</div>
		<div class="hide">
			<!-- 权限树,右键菜单 -->
			<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpenTreeMenu">
			    <li name="add"  iconCls="icon-add" onclick="onAddNode">新增权限子节点</li>
			    <li class="separator"></li>
			    <li name="view" iconCls="icon-node" onclick="onViewNode">查看权限信息</li>
			    <li class="separator"></li>
			    <li name="edit" iconCls="icon-edit" onclick="onEditNode">修改权限信息</li>
			    <li class="separator"></li>
			    <li>
			        <span iconCls="icon-downgrade">移动到...</span>
			        <ul>
			            <li onclick="onMoveNode">上一节点前</li>
			            <li onclick="onMoveNode">下一节点后</li>
			        </ul>
			    </li>
			    <li class="separator"></li>
			    <li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除节点</li>
			</ul>
		</div>
		<script type="text/javascript">
			// 当前选中的节点
			var curNode = null;
			// 如果在 onload 之前需要调用,则执行此方法
			mini.parse();
			// 选择树节点时,默认是查看信息
			function onTreeNodeSelect(e) {
			    var node = e.node;
			    var isLeaf = e.isLeaf;
			    if (isLeaf) {
			    }
				var spid = node.spid;
				var islocalnew = node.islocalnew;
				if(spid && !islocalnew){
					// 保存本地 cookie
					document.cookie="current_spid="+escape(spid); 
				}
				showDataToForm(node);
			    e.cancel = true;
			    return false;
			};
			// 打开树的右键菜单前
			function onBeforeOpenTreeMenu(e) {
			    var menu = e.sender;
			    var isLeaf = e.isLeaf;
			    var tree = mini.get("tree1");
				// 取得点击的节点
			    var node = tree.getSelectedNode();
			    if (!node) {
			        e.cancel = true;
			    }
			    // 某些特殊节点,屏蔽事件;
			    if (node && node.text == "Base") {
			        e.cancel = true;
			        //阻止浏览器默认右键菜单
			        e.htmlEvent.preventDefault();
			        return;
			    }
			    ////////////////////////////////
			    // 对某些特殊的菜单执行隐藏,显示操作
			    var editItem = mini.getbyName("edit", menu);			    
			    var removeItem = mini.getbyName("remove", menu);
			    // 设置为初始值
			    editItem.show();
			    removeItem.show();
				// 某些节点,不显示编辑菜单
			    if (node.id == "forms") {
			        editItem.hide();
			    }
			    // 某些节点,不显示删除菜单
			    if (node.children && node.children.length) {
			    	//removeItem.enable();
			        //removeItem.disable();
			        removeItem.hide();
			    } else if( (-1 == node.opflag) || (-1 == node.createmender) ){
			        removeItem.hide();
			     }
			};
			// 查看权限节点
			function onViewNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
				// 将节点数据带到右边的form中
				showDataToForm(node);
			    e.cancel = true;
			    return false;
			};
			// 新增权限
			function onAddNode(e, topLevel) {
			    
			    var tree = mini.get("tree1");
			    var node = null;
			    if(topLevel){
			    	// nothing
			    } else {
			    	node = tree.getSelectedNode();
			    }
			    if (!node) {
			    	node = {
			    		spid:"-1"
						, islocalnew : true
			    		};
			    }
			    // 查询下一个ID
				// 执行AJAX请求
	        	var url = "<%=path%>/permissions/ajax/getnextid.json";
	        	//
	        	var data = {};
	        	var successCallback = function (message){
	        		var status = message["status"];
	        		var meta = message["meta"] || {};
	        		var info = message["info"];
	            	var nextid = meta["nextid"] || "";
					// 判断是否成功
	        		if(1 === status){
					    var newNode = {
					    	spid : ""+nextid
					    	, spparent : node.spid
					    	, sporderby : "9999"
					    	, createtime : new Date()
					    	, spdiscribe : "新增权限_"+nextid
					    	, opflag : 1
					    	, islocalnew : true
					    };
						
						if(topLevel){
					    	// nothing
					    } else {
					    	tree.addNode(newNode, "add", node);
					    }
						showDataToForm(newNode, node);
	        		} else {
	               	 	debug(info, true);
	        		}
	               return false;
	        	};
	        	var errotCallback = function (message){
	        		alert("操作失败");
	        	};
	        	// 执行AJAX
	        	requestAjax(url,data,successCallback,errotCallback);
			};
			// 编辑权限
			function onEditNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
				// 将节点数据带到右边的form中
				showDataToForm(node);
			};
			// 删除
			function onRemoveNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
			    if (!node) {
			    	return;
			    }
			    //
		        var pnode = tree.getParentNode ( node );
		    	if(node.islocalnew){
		            tree.removeNode(node);
        			if(pnode){
        				tree.selectNode ( pnode )
        			}
		    		return false;
		    	}
		        if (confirm("确定删除选中权限?")) {
		        	// ajax
		        	var url = "<%=path%>/permissions/ajax/delete.json";
		        	//
		        	var data = {
		        		spid : node.spid
		        	};
		        	var successCallback = function (message){
		        		var status = message["status"];
		        		var meta = message["meta"] || {};
		        		var info = message["info"];
		        		if(1 === status){
		        			// 删除子节点
		            		tree.removeNode(node);
		        			// 选中父节点
		        			if(pnode){
		        				tree.selectNode ( pnode )
		        			}
		        		} else {
		        			alert(info);
		        		}
		        	};
		        	var errotCallback = function (message){
		        		alert("删除失败");
		        	};
		        	// 执行AJAX
		        	requestAjax(url,data,successCallback,errotCallback);
		       }
			};
			// 移动
			function onMoveNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
			    // window["debugMode"] = true;
			    debug("开发时期不支持移动!", true);
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
	        // 渲染日期
	        function valuechanged(target, e) {
	        	debug(target);
	        	debug(e);
	        	if(e && e.value){
	        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
	        	}
	            return "";
	        };
			//
			// 将数据显示(要显示的节点,父节点)
			function showDataToForm(node, pnode){
				if(!node){
					return;
				}
				// 存储当前节点
				curNode = pnode || node;
				// 怎么带出来?
				var form = new mini.Form("#editform");
				//form.loading();
				// 设置数据
		        form.setData(node, true);
				//
			    var spidInput = mini.getbyName("spid");
			    var createtime = mini.getbyName("createtime");
				
				// 修改时不允许改变ID
				if(spidInput){
					if(!pnode){
						spidInput.allowInput=false;
					} else {
						spidInput.allowInput=true;
						// 延迟
						window.setTimeout(function(){
							spidInput.focus();
						}, 20);
					}
				}
				//
				if(createtime){
					var value = node.createtime || "";
					if(/^\d+$/.test){
						value = mini.formatDate( new Date(value), "yyyy-MM-dd HH:mm:ss");
					} else if(value){
						value = mini.formatDate( value, "yyyy-MM-dd HH:mm:ss");
					}
					createtime.setValue(value);
					createtime.allowInput=false;
				}
				// 允许编辑
		        form.unmask();
			};
				
			// 保存数据
			function saveData() {
				var form = new mini.Form("#editform");
				var data = form.getData();
				// 界面
				form.loading("保存中，请稍候......");
				
	        	// ajax
	        	var url = "<%=path%>/permissions/ajax/update.json";
	        	//
	        	var successCallback = function (message){
	        		var status = message["status"];
	        		var meta = message["meta"] || {};
	        		var info = message["info"];
	        		
	               if(1 === message["status"]){
			    		// 判断是否成功
			        	// grid.reload();
			    		refreshPage();
	               } else {
	               		debug(info, true);
	               }
	               form.unmask();
	               return false;
	        	};
	        	var errotCallback = function (message){
	        		alert("保存失败");
	               	form.unmask();
	        	};
	        	// 执行AJAX
	        	requestAjax(url,data,successCallback,errotCallback);
			};
			
			// 刷新本页面
			function refreshPage(){
				//
				location.reload();
			};
			function selectPrevNode(){
				//获取cookie字符串 
				var strCookie=document.cookie; 
				//将多cookie切割为多个名/值对 
				var arrCookie=strCookie.split("; "); 
				var spid = null; 
				//遍历cookie数组，处理每个cookie对 
				for(var i=0;i<arrCookie.length;i++){ 
					var arr=arrCookie[i].split("="); 
					//找到cookie，并返回它的值 
					if("current_spid"==arr[0]){ 
						spid=unescape(arr[1]); 
						break; 
					} 
				} 
				if(spid){
			    	var tree = mini.get("tree1");
			    	var node = tree.getNode ( spid );
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
				}
			};
			// 执行
			selectPrevNode();
			// 展开树
			function expand(tree, node){
		    	if(!node || !tree || !node.spid){
		    		return;
		    	}
		    	var spparent = node.spparent;
		    	if(spparent && spparent != "0" && spparent != "-1"){
				    var pnode = tree.getNode ( node.spparent );
				    expand(tree, pnode);
		    	}
			    tree.expandNode(node);
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
			//过滤树
	        function search() {
	        	var tree = mini.get("tree1");
	            var key = mini.get("key").getValue();
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
	        }
	        function onKeyEnter(e) {
	            search();
	        }
		</script>
	</body>
</html>