<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>资源管理</title>
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
		        	<!-- 左边的资源树 -->
		            <div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
		            	<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
				            <span style="padding-left:5px;">名称：</span>
				            <input class="mini-textbox" vtype="maxLength:30;"  id="key" style="width:80px;" onenter="onKeyEnter"/>
				            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="search()">查找</a>                  
				        </div>
				        
	        <div  class="mini-fit" >
						<!--Tree-->
						<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
							url="<%=path %>/resource/ajax/list.json?1=1" showTreeIcon="true"
						    resultAsTree="false"
						    textField="resname" idField="resid" parentField="presid"
						    contextMenu="#treeMenu"
						    onnodeselect="onTreeNodeSelect"
						>
						</ul>
			</div>
		            </div>
		            <!-- 右边的显示区域 -->
		            <div showCollapseButton="false" style="overflow: auto"  borderStyle="border:solid 1px #aaa;">
					    <div region="north" height="40"  showSplit="false" showHeader="false" showCollapseButton="false">
							
						</div>
					    <div class="mini-toolbar" style="padding:2px;border:0;border-bottom:1px solid #99bce8;">
					        <table style="width:100%;">
					            <tr>
					            <td style="width:100%;" align="right">
									<a class="mini-button" iconCls="icon-save" style="margin-left: 20px;"  onclick="saveData()" plain="true">保存资源信息</a>
					                <a class="mini-button" iconCls="icon-add" onclick="onAddNode()"	plain="true">新增资源子节点</a>
									<a class="mini-button" iconCls="icon-print" style="margin-left: 5px;"  onclick="copyTo()" plain="true">拷贝资源树</a>
									<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					            </td>
					            <td style="white-space:nowrap;">
					            
					            </td>
					            </tr>
					        </table>
					    </div>
						
					    <div  class="mini-fit" showCollapseButton="false">
			            	<div id="editform" class="form" style="margin-left: 10px;">
								<table class="form-table" border="0" cellpadding="1" cellspacing="2" style="width:100%;">
								    <tr>
								        <td style="width:90px;">资源编号：</td>
								        <td>
								            <input name="resid" class="mini-textbox" style="width:120px;" 
								            vtype="maxLength:12" required="true"/>
								            <input name="presid"  class="mini-hidden"/>
											<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
								        </td>
								        <td style="width:90px;">显示列数：</td>
								        <td >
								            <input name="rct" class="mini-textbox"  style="width:120px;"  
								            vtype="int;maxLength:5"/>
								        </td>
								        <td style="width:90px;">打开图标：</td>
								        <td >
								            <input name="openico" class="mini-textbox"  style="width:120px;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">资源名称：</td>
								        <td>
								            <input name="name" class="mini-textbox" style="width:120px;"  
								            vtype="maxLength:32" required="true"/>
								        </td>
								        <td style="width:90px;">显示位置：</td>
								        <td >
								            <input name="showsite" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">关闭图标：</td>
								        <td >
								            <input name="closeico" class="mini-textbox"  style="width:120px;" />
								        </td>
								    </tr>
								    <tr>
								    	<td style="width:90px;">资源类型：</td>
								        <td >
								            <input name="restypeid" class="mini-combobox"  style="width:120px;"
								            	textField="name" valueField="restypeid"
								            	url="<%=basePath %>resource/ajax/getresourcetype.json"
								            	/> 
								        </td>
								        <td style="width:90px;">标签页类型：</td>
								        <td >
								            <input name="ismenu"
								             class="mini-combobox"  style="width:120px;"
								            	textField="text" valueField="id"
								            	data="ismenuarray" />
								        </td>
								        <td style="width:90px;">显示图片：</td>
								        <td >
								            <input name="showico" class="mini-textbox"  style="width:120px;" />
								        </td>
								    </tr>
								    <tr>
								    	<td style="width:90px;">资源排序：</td>
								        <td >
								            <input name="sn" class="mini-textbox" style="width:120px;"  
								            vtype="int;maxLength:5" required="true"/>
								        </td> 
								        <td style="width:90px;">提示信息：</td>
								        <td >
								            <input name="title" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">报表名称：</td>
								        <td >
								            <input name="text8" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <!--  
								        <td style="width:90px;">左上角X坐标：</td>
								        <td >
								            <input name="x1" class="mini-textbox"  style="width:120px;"   
								            vtype="int;maxLength:5"/>
								        </td>
								        -->
								    </tr>
								    
								    <%--
								    <tr>
								        <td style="width:90px;">关联表名：</td>
								        <td >
								            <input name="linktablename" class="mini-textbox"  style="width:120px;" />
								        </td>
								        
								        <td style="width:90px;">关联字段：</td>
								        <td >
								            <input name="linkcolumns" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">orderby&nbsp;语句：</td>
								        <td >
								            <input name="orderbycon" class="mini-textbox"  style="width:120px;" />
								        </td>
								    </tr>
								    
								     --%>
								    <tr>
								    	<td style="width:90px;">是否可视：</td>
								        <td >
								            <input name="isvisible" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">查询提示信息：</td>
								        <td >
								            <input name="prompt" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <!--  
								        <td style="width:90px;">左上角Y坐标： </td>
								        <td >
								            <input name="y1" class="mini-textbox"  style="width:120px;"   
								            vtype="int;maxLength:5"/>
								        </td>
								        -->
								    </tr>
								    <tr>
								    	<td style="width:90px;">组件高度：</td>
								        <td >
								            <input name="windowhight" class="mini-textbox"  style="width:120px;"  />
								        </td>
								        <td style="width:90px;">表&nbsp;单&nbsp;&nbsp;ID：</td>
								        <td >
								            <input name="formid" class="mini-textbox"  style="width:120px;"   
								            vtype="maxLength:20"/>
								        </td>
								        <!--  
								        <td style="width:90px;">右下角X坐标：</td>
								        <td >
								            <input name="x2" class="mini-textbox"  style="width:120px;"   
								            vtype="int;maxLength:5"/>
								        </td>
								        -->
								    </tr>
								    <tr>
								    	<td style="width:90px;">组件宽度：</td>
								        <td >
								            <input name="windowwidth" class="mini-textbox"  style="width:120px;"   />
								        </td>
								        
								        <td style="width:90px;">查询方案ID： </td>
								        <td >
								            <input name="querysql" class="mini-textbox"  style="width:120px;"   
								            vtype="maxLength:50"/>
								        </td>
								        <!--  
								       <td style="width:90px;">右下角Y坐标： </td>
								        <td >
								            <input name="y2" class="mini-textbox"  style="width:120px;"   
								            vtype="int;maxLength:5"/>
								        </td>
								       -->
								    </tr>
								    <tr>
								    	<td style="width:90px;">对&nbsp;应&nbsp;动&nbsp;作：</td>
								        <td colspan="5">
								            <input name="srurl" class="mini-textbox"  style="width:710px;" />
								        </td>
								    </tr>
								    <tr>
								    	<td style="width:90px;">附&nbsp;加&nbsp;信&nbsp;息：</td>
								        <td colspan="5">
								            <input name="text1" class="mini-textbox"  style="width:710px;" />
								        </td>
								    </tr>
								    <tr>
								    	<td style="width:90px;">备&nbsp;注&nbsp;信&nbsp;息：</td>
								        <td colspan="5">
								            <textarea name="remark" class="mini-textarea" style="width:710px;height:30px;"></textarea>
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
			<!-- 资源树,右键菜单 -->
			<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpenTreeMenu">
			    <li name="add"  iconCls="icon-add" onclick="onAddNode">新增资源子节点</li>
			    <li class="separator"></li>
			    <li name="view" iconCls="icon-node" onclick="onViewNode">查看资源信息</li>
			    <li class="separator"></li>
			    <li name="edit" iconCls="icon-edit" onclick="onEditNode">修改资源信息</li>
			    <li class="separator"></li>
			    <li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除节点</li>
			</ul>
		</div>
		<script type="text/javascript">
			// 当前选中的节点
			var curNode = null;
			//
			var ismenuarray = [{id:"0",text:"未定义"},{id:"1",text:"普通页面"},{id:"2",text:"子标签页"},{id:"3",text:"多标签页"}];
			// 如果在 onload 之前需要调用,则执行此方法
			mini.parse();

			// 执行
			selectPrevNode();
			
			// 选择树节点时,默认是查看信息
			function onTreeNodeSelect(e) {
			    var node = e.node;
			    if (!node) {
			        return;
			    }
				var resid = node.resid;
				if(resid){
					// 保存本地 cookie
					setCookie("current_resid", resid);
				}
				showResourceToForm(node);
			    e.cancel = true;
			    return false;
			};
			// 打开树的右键菜单前
			function onBeforeOpenTreeMenu(e) {
			    var menu = e.sender;
			    var tree = mini.get("tree1");
				// 取得点击的节点
			    var node = tree.getSelectedNode();
			    if (!node) {
			        e.cancel = true;
			        return;
			    }
			    ////////////////////////////////
			    // 对某些特殊的菜单执行隐藏,显示操作
			    var editItem = mini.getbyName("edit", menu);			    
			    var removeItem = mini.getbyName("remove", menu);
			    // 设置为初始值
			    editItem.show();
			    removeItem.show();
			    // 只允许删除叶子节点
			    if (node.children || "0" === node.presid || "-1" === node.presid) {
			        removeItem.hide();//不显示删除菜单
			    }
			};
			// 查看资源节点
			function onViewNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
				// 将节点数据带到右边的form中
				showResourceToForm(node);
			    e.cancel = true;
			    return false;
			};
			// 新增资源
			function onAddNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
			    if (!node) {
			    	alert("请先选中资源树中的节点");
			        //e.cancel = true;
			        return false;
			    }
			    // 查询下一个资源ID
				// 执行AJAX请求
	        	var url = "<%=path%>/resource/ajax/getnextid.action";
	        	var data = {};
	        	var successCallback = function (message){
	        		var status = message["status"];
	        		var meta = message["meta"] || {};
	        		var info = message["info"];
	        		if(1 === status){
	        			//
		            	var nextid = meta["nextid"] || "";

			    		// 判断是否成功
					    var newNode = {
					    	resid : nextid
					    	, presid : node.resid
					    	, sn : "9999"
					    	, optime : new Date()
					    	, ismenu : 1
					    	, ismenuleaf : 1
					    	, isresourcesleaf : 0
					    	, restypeid : 10
					    	, name : "新增资源_"+nextid
					    	, islocalnew : 1
					    };
					    tree.addNode(newNode, "add", node);
						showResourceToForm(newNode, node);
	        		} else {
	        			alert(info);
	        		}
	        	};
	        	var errorCallback = function (message){
	        		alert("删除失败");
	        	};
	        	// 执行AJAX
	        	requestAjax(url,data,successCallback,errorCallback);
			};
			// 编辑资源
			function onEditNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
				// 将节点数据带到右边的form中
				showResourceToForm(node);
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
		        if (confirm("确定删除选中资源?")) {
		        	// ajax
		        	var url = "<%=path%>/resource/ajax/delete.action";
		        	//
		        	var data = {
		        		resid : node.resid
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
		        	var errorCallback = function (message){
		        		alert("删除失败");
		        	};
		        	// 执行AJAX
		        	requestAjax(url,data,successCallback,errorCallback);
		       }
			};
			//
			// 将数据显示(要显示的节点,父节点)
			function showResourceToForm(node, pnode){
				if(!node){
					return;
				}
				var islocalnew = node.islocalnew;
				// 存储当前节点
				curNode = pnode || node;
				// 怎么带出来?
				var form = new mini.Form("#editform");
				//form.loading();
				// 设置数据
		        form.setData(node, true);
				//
			    var residInput = mini.getbyName("resid");
			    var nameInput = mini.getbyName("name");
			    var optime = mini.getbyName("optime");
			    //
			    var restypeidInput = mini.getbyName("restypeid");
			    restypeidInput.setValue(node.restypeid);
			    //console.dir(node);
				
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
				//
				if(optime){
					var value = node.optime || "";
					if(/^\d+$/.test){
						value = mini.formatDate( new Date(value), "yyyy-MM-dd HH:mm:ss");
					} else if(value){
						value = mini.formatDate( value, "yyyy-MM-dd HH:mm:ss");
					}
					optime.setValue(value);
					optime.allowInput=false;
					//
					
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
				// 执行AJAX请求
				//
				var url = "<%=path%>/resource/ajax/update.action";
				var successCallback = function(message){
		        		var status = message["status"];
		        		var meta = message["meta"] || {};
		        		var info = message["info"];
		        		if(1 === status){
				    		refreshPage();
		        		} else {
		               		debug(info, true);
		        		}
						form.unmask();
						return false;
				};
				var errorCallback = function(){
			        alert("保存失败");
	               	form.unmask();
					return false;
				};
				//
				requestAjax(url, data, successCallback, errorCallback);
			};
			
			// 资源拷贝
			function copyTo(){
				// 获取
			    var tree = mini.get("tree1") || {getSelectedNode: function(){return null;} };
			    var node = tree.getSelectedNode();
			    if (!node) {
			    	alert("请先选中要拷贝的节点");
			        //e.cancel = true;
			        return false;
			    }
				
				var resid = node.resid;
				//
				var myurl = "resource/getsingleresource.page" + "?fromid="+resid;
				var mywidth = 600;
				var myhight = 400;
				
				//
	            var win=mini.open({
                    url: myurl,
                    showMaxButton: true,
                    allowResize: true, 
                    title: "选择拷贝目的节点", width: mywidth, height: myhight,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                    },
                    ondestroy: function (action) {
                    	//
                        var iframe = this.getIFrameEl();
                    	//
                    	if("ok" == action){
                            // 调用内部的方法
                            var data = iframe.contentWindow.GetData() ||{};
                            var toresid = data.resid;
                            var onlysub = data.onlysub;
                            //
                        	// 执行 Ajax请求
                            ajaxCopyTo(resid, toresid, onlysub);
                    	}
                    }
	            });
			};
			// 拷贝资源
			function ajaxCopyTo(resid, target_resid, onlysub){
            	onlysub = onlysub || 0; // 是否只拷贝子节点
				var form = new mini.Form("#editform");
				form.loading("拷贝中，请稍候......");
				//
            	// 执行 Ajax请求
				var data = {
            			fromresid : resid
            			, toresid : target_resid
            			, onlysub : onlysub
            	};
				var url = "<%=path%>/resource/ajax/copy.json";
				var successCallback = function(message){
		        		var status = message["status"];
		        		var meta = message["meta"] || {};
		        		var info = message["info"];
		        		if(1 === status){
				    		refreshPage();
		        		} else {
		               		debug(info, true);
		        		}
						form.unmask();
						return false;
				};
				var errorCallback = function(){
			        alert("操作失败");
	               	form.unmask();
					return false;
				};
				//
				requestAjax(url, data, successCallback, errorCallback);
			};
			
			// 选中前次选中的节点
			function selectPrevNode(){
				var resid = getCookie("current_resid"); 
				if(resid){
			    	var tree = mini.get("tree1");
			    	var node = tree.getNode ( resid );
			    	if(!node){
			    		return;
			    	}
					// 展开Resource树
			    	tree.expandPath(resid);
			    	// 选中
					tree.selectNode ( node );
				}
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
	                    //var text = node.name?node.name.toLowerCase() : "";
	                    var name = node.resname || "";
	                    var id = node.resid || "";
	                    if (name.indexOf(key) > -1) {
	                        return true;
	                    } else if (id.indexOf(key) > -1) {
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