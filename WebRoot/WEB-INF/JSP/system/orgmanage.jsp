<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>机构管理</title>
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
	        <div  class="mini-fit" >
						<!--Tree -->
						<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
							url="<%=path %>/org/ajax/list.action?1=1" showTreeIcon="true"
						    resultAsTree="false" expandOnLoad="0" 
						    textField="name" idField="orgid" parentField="porgid"
						    contextMenu="#treeMenu"
						    onnodeselect="onTreeNodeSelect" onload="selectPrevNode"
						>
						</ul>
		  </div>
		            </div>
		            <!-- 右边的显示区域 -->
		            <div showCollapseButton="false" style="overflow: auto"  borderStyle="border:solid 1px #aaa;">
					    <div region="north" height="40"  showSplit="false" showHeader="false" showCollapseButton="false">
						    <div class="mini-toolbar" style="padding:2px;border:0;border-bottom:1px solid #99bce8;">
						        <table style="width:100%;">
						            <tr>
						            <td style="width:100%;" align="right">
										<a class="mini-button" iconCls="icon-save"  onclick="saveData()" plain="true">保存机构信息</a>
						                <a class="mini-button" iconCls="icon-add" onclick="onAddNode()"	plain="true">新增子机构</a>
										<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
						            </td>
						            <td style="white-space:nowrap;">
						            
						            </td>
						            </tr>
						        </table>
						    </div>
						</div>
						
					    <div  class="mini-fit" showCollapseButton="false">
			            	<div id="editform" class="form" style="margin-left: 10px;width:810px;">
								<table class="form-table" border="0" cellpadding="1" cellspacing="2" style="95%">
								    <tr>
								        <td style="width:90px;">机构编号：</td>
								        <td width="25%">
								            <input name="orgid" class="mini-textbox" style="width:120px;"
								            	vtype="maxLength:20" required="true"  requiredErrorText="编号不能为空" />
								            <input name="porgid"  class="mini-hidden"/>
											<input id="islocalnew" name="islocalnew" class="mini-hidden"/>
								        </td>
								        <td style="width:90px;">机构名称：</td>
								        <td colspan="3">
								            <input name="name" class="mini-textbox" style="width:100%;" 
								            	vtype="maxLength:30" required="true"  requiredErrorText="名称不能为空"/>
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">机构简称：</td>
								        <td>
								            <input name="shortname" class="mini-textbox" style="width:120px;" />
								        </td>
								        <td style="width:90px;">单位全称：</td>
								        <td colspan="3">
								            <input name="fullname" class="mini-textbox" style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">机构排序：</td>
								        <td >
								            <input name="sn" class="mini-textbox" style="width:120px;" 
								            	vtype="int;maxLength:20" required="true" />
								        </td>
								        <td style="width:90px;">联系电话：</td>
								        <td width="25%">
								            <input name="exttel" class="mini-textbox" vtype="maxLength:20" style="width:120px;" />
								        </td>
								        <td style="width:90px;">联系传真：</td>
								        <td >
								            <input name="fax" class="mini-textbox"  style="width:120px;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">联系人员：</td>
								        <td >
								            <input name="extcontacts" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">所在城市：</td>
								        <td >
								            <input name="city" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">邮政编码：</td>
								        <td >
								            <input name="postcode" class="mini-textbox" vtype="maxLength:6"  style="width:120px;" />
								        </td>
								    </tr>
								    <tr>
								    	<td style="width:90px;">对应高院：</td>
								        <td >
								            <input id="highcourt" name="highcourt" class="mini-combobox"
			                					textField="name" valueField="orgid"  popupWidth="200" 
									            url="<%=path %>/org/ajax/listbylevel.json?unitlevel=6"
							                		 style="width:120px;" showNullItem="true"/>
								        </td>
								        
								        <td style="width:90px;">对应中院：</td>
								        <td >
								            <input id="intermediatecourt" name="intermediatecourt" class="mini-combobox" 
			                					textField="name" valueField="orgid"  popupWidth="200" 
							                		 style="width:120px;" showNullItem="true"
							                		 onclick="getIntermediatecourt();"/>
								        </td>
								        
								        <td style="width:90px;">对应检察院：</td>
								        <td >
								            <input name="procuratorate" class="mini-combobox"
			                					textField="name" valueField="orgid"  popupWidth="200" 
									            url="<%=path %>/org/ajax/listbylevel.json?unitlevel=8"
							                		 style="width:120px;" showNullItem="true"/>
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">监狱类型：</td>
								        <td >
								            <input name="prisontype" class="mini-textbox"  style="width:120px;" />
								        </td>
								        <td style="width:90px;">监区类别：</td>
								        <td >
								            <input name="prisonarealevel" class="mini-combobox" data="prisonarealevel" value="0" style="width:120px;" renderer="onGenderRenderer"/>
								        </td>
								        <td style="width:90px;">部门级别：</td>
								        <td >
								            <input name="unitlevel" class="mini-combobox"  style="width:120px;"
								            	data="unitlevelarray"/> 
								        </td>
								        
								    </tr>
								    <tr>
								        <td style="width:90px;">电子邮箱：</td>
								        <td >
								            <input name="email" class="mini-textbox" vtype="email;maxLength:30" style="width:120px;" />
								        </td>
								        <td style="width:90px;">创建时间：</td>
								        <td >
								            <input name="optime" class="mini-textbox"  style="width:120px;" valuechanged="valuechanged" />
								        </td>
								        <td style="width:90px;">创&nbsp;建&nbsp;者&nbsp;ID：</td>
								        <td >
								            <input name="opid" class="mini-textbox"  style="width:120px;" />
								            <input name="opflag" class="mini-hidden" type="hidden" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">详细地址：</td>
								        <td colspan="5">
								            <input name="address" class="mini-textbox"  style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								    	<td style="width:90px;">单位罪犯编号自增规则：</td>
								        <td colspan="5">
								            <input name="criminalcoderules" class="mini-textbox"  style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">部门说明：</td>
								        <td colspan="5">
								            <input name="description" class="mini-textbox"  style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">乘车路线：</td>
								        <td colspan="5">
								            <input name="busline" class="mini-textbox"  style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">职责说明：</td>
								        <td colspan="5">
								            <input name="dutyexplain" class="mini-textbox"  style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">效益情况：</td>
								        <td colspan="5">
								            <input name="benefit" class="mini-textbox"  style="width:100%;" />
								        </td>
								    </tr>
								    <tr>
								        <td style="width:90px;">备注信息：</td>
								        <td colspan="5" >
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
			<!-- 机构树,右键菜单 -->
			<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpenTreeMenu">
			    <li name="add"  iconCls="icon-add" onclick="onAddNode">新增机构子节点</li>
			    <li class="separator"></li>
			    <li name="view" iconCls="icon-node" onclick="onViewNode">查看机构信息</li>
			    <li class="separator"></li>
			    <li name="edit" iconCls="icon-edit" onclick="onEditNode">修改机构信息</li>
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
			// TODO 需要在页面打开时到数据库中加载.
			// 部门级别(1司法部,2省局,3监狱,4监区,5科室,6高院,7中院,8高检,9中检,10公安,11分监区
			var unitlevelarray = [
					{id:"4",text:"监区"}
					,{id:"3",text:"监狱"}
					,{id:"5",text:"科室"}
					,{id:"2",text:"监狱管理局"}
					,{id:"1",text:"司法部"}
					,{id:"6",text:"高级法院"}
					,{id:"7",text:"中级法院"}
					,{id:"8",text:"高级检察院"}
					,{id:"9",text:"中级检察院"}
					,{id:"10",text:"公安"}
					,{id:"11",text:"分监区"}
					,{id:"12",text:"直属分监区"}
				];
			var prisonarealevel = [{id:2,text:'--非监区--'},{id:0,text:'一般监区'},{id: 1, text: '入监监区'}];//监区类别
				
			// 如果在 onload 之前需要调用,则执行此方法
			mini.parse();
			//
			
			// 选择树节点时,默认是查看信息
			function onTreeNodeSelect(e) {
			    var node = e.node;
			    var isLeaf = e.isLeaf;
			    if (isLeaf) {
			    }
				var orgid = node.orgid;
				var islocalnew = node.islocalnew;
				if(orgid && !islocalnew){
					// 保存本地 cookie
					document.cookie="current_orgid="+escape(orgid); 
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
			    } else if( (-1 == node.opflag) || (-1 == node.opid) ){
			        removeItem.hide();
			     }
			};
			// 查看机构节点
			function onViewNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
				// 将节点数据带到右边的form中
				showDataToForm(node);
			    e.cancel = true;
			    return false;
			};
			// 新增机构
			function onAddNode(e) {
			    var tree = mini.get("tree1");
			    var node = tree.getSelectedNode();
			    if (!node) {
			    	alert("请先选中机构树中的节点");
			        //e.cancel = true;
			        return false;
			    }
			    //
			    var plevel = node.unitlevel;
			    plevel = parseInt(plevel) || 3;
			    // 判断是否成功
			    var newNode = {
			    	orgid : ""
			    	, porgid : node.orgid
			    	, sn : "9999"
			    	, optime : new Date()
			    	, name : "新增机构_"
			    	, unitlevel : plevel + 1
			    	, opflag : 1
			    	, islocalnew : 1
			    };
			    tree.addNode(newNode, "add", node);
				showDataToForm(newNode, node);
				if(plevel==3 || plevel==4){
					mini.getbyName("prisonarealevel").setValue(0);
				}
			};
			// 编辑机构
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
		        if (confirm("确定删除选中机构?")) {
		        	// ajax
		        	var url = "<%=path%>/org/ajax/delete.action";
		        	//
		        	var data = {
		        		orgid : node.orgid
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
				var value =  mini.get("highcourt").getValue();
				if(value){
					var intermediatecourt = mini.get("intermediatecourt");
	    			var url="<%=path %>/org/ajax/listbylevel.json?unitlevel=7&porgid="+value;
		        	intermediatecourt.load(url);
				}
	        	
			    var orgidInput = mini.getbyName("orgid");
			    var optime = mini.getbyName("optime");
			    //
			    var islocalnew = node.islocalnew;
				
				// 修改时不允许改变ID
				if(orgidInput){
					if(islocalnew){
						orgidInput.allowInput=true;
					} else if(!pnode){
						orgidInput.allowInput=false;
					} else {
						orgidInput.allowInput=true;
						// 延迟
						window.setTimeout(function(){
							orgidInput.focus();
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
				}
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
				var data = form.getData();
				// 界面
				form.loading("保存中，请稍候......");
				
	        	// ajax
	        	var url = "<%=path%>/org/ajax/update.action";
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
						if("current_orgid"==arr[0]){ 
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
	        
	        function getIntermediatecourt(){
	        	var highcourt = mini.get("highcourt");
	        	if(highcourt){
	        		var highcourtVal = highcourt.getValue();
	        		if(highcourtVal){
	        			var intermediatecourt = mini.get("intermediatecourt");
	        			var url="<%=path %>/org/ajax/listbylevel.json?unitlevel=7&porgid="+highcourtVal;
	        			intermediatecourt.load(url);
	        		}else{
	        			alert("请先选择高院！");
	        			return;
	        		}
	        	}else{
	        		alert("请先选择高院！");
	        		return;
	        	}
	        }
	        
	        
		</script>
	</body>
</html>