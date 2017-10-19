<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>功能说明</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
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
			
			.hide {
				display: none;
			}
		</style>
	</head>
	<body >
		<!--Splitter-->
		<div class="mini-splitter" style="width: 100%; height: 100%;" borderStyle="border:0;">
			<div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
				<div class="mini-toolbar" >
					<span style="padding-left: 5px;">名称：</span>
					<input class="mini-textbox" vtype="maxLength:30;" id="key" style="width: 80px;" onenter="onKeyEnter" />
					<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>
				</div>
				<div class="mini-fit">
					<!--Tree-->
					<ul id="tree1" name="tree1" class="mini-tree" style="width: 100%; height: 100%;"
						url="<%=path%>/resource/ajax/list.json?1=1" showTreeIcon="true"
						resultAsTree="false" textField="name" idField="resid" parentField="presid" >
					</ul>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" >    
				<table>
				<tr>
				<td style="width:100%;">   
					<!-- <span class="separator"></span> -->         
			    	<a class="mini-button"  id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
			    	<a class="mini-button"  id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
			    	<a class="mini-button"  id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
		          	<a class="mini-button" id="saved" iconCls="icon-save" plain="true"  onclick="saveData()">保存</a>
					<a class="mini-button"   id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
					<a class="mini-button"   id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
					<a class="mini-button" iconCls="icon-remove" tooltip="删除说明" plain="true" onclick="deleteData()">删除说明文档</a>
					<a class="mini-button" iconCls="icon-close" tooltip="关闭" plain="true" onclick="javaScript:back();">关闭</a>
			    	<!-- <span class="separator"></span> --> 
			    	<a class="mini-button"  style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
			    	<a class="mini-button"  style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
			    	<a class="mini-button"  style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
			    	<!-- <span class="separator"></span> --> 
			    	<a class="mini-button"  style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
			    	<a class="mini-button"  style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
			    	<a class="mini-button"  style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
			    	<!-- <span class="separator"></span> --> 
			    	<a class="mini-button"  style="display:none;" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
			    	<!-- <span class="separator"></span> --> 
			    	<span id ="msg" style="margin-left: 100px;">
			    	</td>
	    	<td style="white-space:nowrap;">
	    		<a class="mini-button"  id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	   	 	</td>
	    	</tr>
	    	</table>
		       </div>
    		   <div id="fit" class="mini-fit" style="width:100%; height:100%;">
	             <script src="<%=path%>/scripts/form/loadaip.js"></script>
	             <script src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
	             <jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
         	  </div>  
         	</div>
		</div>
		
		<script type="text/javascript">
			mini.parse();
			var fit=mini.get("fit");
			// 当前选中的节点
			var tree = mini.get("tree1");
        	tree.on("nodeselect", function (e) {
        		$.ajax({
                url: "getData.action?1=1",
                data: {resid : e.node.resid},
                type:"post",
                success: function (text) {
	                var obj = eval(text);
                    if(obj=="sucess") {
                    	document.getElementById("msg").innerHTML = "<font color=red size='5' >未导入！</font>";
                    	
                    } else {
                    	document.getElementById("msg").innerHTML = "<font color=red></font>";
	                    var obj = document.getElementById("HWPostil1");
	                    HWPostil1_modelReady(text,1);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    CloseWindow();
                }
            });
        });
			
			// 选择树节点时,默认是查看信息
			function onTreeNodeSelect(e) {
			    var node = e.node;
			    var isLeaf = e.isLeaf;
			    if (!node) {
			        return;
			    }
			    //
			    // 资源这里不允许编辑打印方案
			    var restypeid = node.restypeid;
			    var typeScheme = 14;
			    
			    // 资源这里不允许编辑打印方案
			    if (typeScheme==restypeid) {
			        e.cancel = true;
			        //阻止浏览器默认右键菜单
			        //e.htmlEvent.preventDefault();
			        alert(""+node.name +"需要使用报表管理查看.");
			        return;
			    }
			    
				var resid = node.resid;
				if(resid){
					// 保存本地 cookie
					document.cookie="current_resid="+escape(resid); 
				}
				showResourceToForm(node);
			    e.cancel = true;
			    return false;
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
			    window["debugMode"] = true;
			    debug("开发时期不支持移动!");
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
			var resid=mini.get("tree1").getValue();
       	 	if(resid){
	       		var MDCODE=saveFile();
	       		var url = "saveFunctionData.action?1=1&resid="+resid;
	            $.ajax({
	                url: url,
	                type:"POST",
	                data: { data : MDCODE},
	                success: function (text) {
	                   if(text>0){
	                   		mini.alert("保存成功！");
	                   }else{
	                   		mini.alert("保存失败！");
	                   }
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	mini.alert("操作失败!");
	                    CloseWindow();
	                }
	            });
            }else{
            	mini.alert("请先选中一个菜单！");
            }
        }
			
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
				var resid = null; 
				//遍历cookie数组，处理每个cookie对 
				for(var i=0;i<arrCookie.length;i++){ 
					var arr=arrCookie[i].split("="); 
					//找到cookie，并返回它的值 
					if("current_resid"==arr[0]){ 
						resid=unescape(arr[1]); 
						break; 
					} 
				} 
				if(resid){
			    	var tree = mini.get("tree1");
			    	var node = tree.getNode ( resid );
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
		    	if(!node || !tree || !node.resid){
		    		return;
		    	}
		    	var presid = node.presid;
		    	if(presid && presid != "0" && presid != "-1"){
				    var pnode = tree.getNode ( node.presid );
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
				    	   mimi.alert("操作失败!");
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
	        function SetData(action){
	        
	        }
	        //删除系统说明文档
	        function deleteData(){
	        	var resid=mini.get("tree1").getValue();
	        	if(resid==''){
	        		mini.alert("请选择一个菜单！");
	        		return;
	        	}
	        	if(confirm("确认删除操作？")){
	        	var url="removeDocumentData.action?1=1";
	        	$.ajax({
	        		url:url,
	        		data:{resid:resid},
	        		type:"post",
	        		success: function (text){
	        			if(text='ok'){
		        			mini.alert("操作成功！");
	        			 	location.reload() ;
	        			}else {
	        				mini.alert("操作失败！");
	        			}
	        		},
	        		error: function (jqXHR, textStatus, errorThrown){
	        			mini.alert("操作失败！");
	        		}
	        	});
	        	}
	        }
		</script>
	</body>
</html>