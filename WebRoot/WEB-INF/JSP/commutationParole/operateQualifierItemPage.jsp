<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html>
	<head>
		<title></title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
           <div size="260" maxSize="1024" minSize="150" showCollapseButton="true">
				<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
		            <span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" vtype="maxLength:60;" id="key" style="width:140px;" onenter="onKeyEnter"/>
		            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="searchItem()">查找</a>     
		        </div>
		        <div  class="mini-fit" >
					<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
						url="<%=path%>/getQualifierItem.json?1=1" showTreeIcon="true" expandOnLoad="0" resultAsTree="false"
					    textField="name" idField="col_name" contextMenu="#treeMenu" onnodeclick="onTreeNodeClick">
					</ul>
				</div>
           </div>
           
           <!-- 右边的显示区域 -->
           <div showCollapseButton="false" style="overflow: auto;padding:0px;" borderStyle="border:solid 1px #aaa;">
			    <div region="north" height="40"  showSplit="false" showHeader="false" showCollapseButton="false">
				    <div class="mini-toolbar" style="padding:2px;border:0;">
				       		<div class="crud-search">
								<table style="width:98%;">
									<tr>
										<td align="left">
											<input id="filter_itemlevel" name="filter_itemlevel" class="mini-combobox" showNullItem="true" onvaluechanged="qualifierItem();"
				                        	  data="[{id:1,text:'一级条件'},{id:2,text:'二级条件'},{id:3,text:'三级条件'},{id:0,text:'通用条件'}]"/>
										</td>
										<td align="right">
											<a class="mini-button" iconCls="icon-add" onclick="addItem();" plain="true">新增</a>
											<a class="mini-button" iconCls="icon-save" onclick="savedata();" plain="true">保存</a>
											<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
										</td>
									</tr>
								</table>
							</div>
				    </div>
				</div>
				
			    <div  class="mini-fit" showCollapseButton="false" style="padding:5px;">
			    <div id="editform" class="form">
			    	<input id="id" name="id" class="mini-hidden" />
			    	<input id="operatetype" name="operatetype" class="mini-hidden"/>
			    	<input id="old_column" name="old_column" class="mini-hidden"/>
				    <fieldset id="fd1" style="width:800px;">
				        <legend><span id="opstatus" style="color:blue">筛查元素</span></legend>
				        <div class="fieldset-body">
				            <table class="form-table" border="0" cellpadding="1" cellspacing="2">
				            
				            	<tr>
				                    <td class="form-label" style="width:80px;">元素名称：</td>
				                    <td style="width:220px">
				                        <input id="name" name="name" class="mini-textbox" style="width:160px" required="true"/>
				                    </td>
				                    
				                    <td class="form-label" style="width:80px;">字段名：</td>
				                    <td style="width:220px">
				                    	<input id="col_name" name="col_name" class="mini-textbox" style="width:160px" required="true"/>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label">值类型：</td>
				                    <td >
				                        <input id="value_type" name="value_type" class="mini-combobox" showNullItem="true" style="width:160px" required="true"
				                        	data="[{id:1,text:'整数类型'},{id:2,text:'浮点数类型'},{id:3,text:'布尔类型'},{id:4,text:'日期类型'},{id:5,text:'时间区域'}]"/>
				                    </td>
				                    
				                    <td class="form-label">条件等级：</td>
				                    <td >
				                        <input id="itemlevel" name="itemlevel" class="mini-combobox" showNullItem="true" style="width:160px" required="true"
				                        	data="[{id:1,text:'一级条件'},{id:2,text:'二级条件'},{id:3,text:'三级条件'},{id:0,text:'通用条件'}]"/>
				                    </td>
				                </tr>
				                
				                <tr>
				                	<!--  
				                	<td class="form-label">默认值：</td>
				                    <td >
				                        <input id="defaultvalue" name="defaultvalue" class="mini-textbox" style="width:160px" vtype="float"/>
				                    </td>
				                    -->
				                    
				                    <td class="form-label">排序：</td>
				                    <td >
				                        <input id="orderby" name="orderby" class="mini-spinner"  minValue="1" maxValue="1000" style="width:160px"/>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label" style="width:80px;">计算公式：</td>
				                    <td colspan="3" >
				                        <input id="formula" name="formula" class="mini-textarea" style="width:600px;height:60px;" allowInput="true"/>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label" >备注：</td>
				                    <td colspan="3" >
				                        <input id="remark" name="remark" class="mini-textarea" style="width:600px;height:60px;" allowInput="true"/>
				                    </td>        
				                </tr>
				                
				            </table>
				        </div>
				    </fieldset>
				
					<fieldset id="fd2" style="width:800px;">
				    	<legend><span>表介绍：</span></legend>
				        <div class="fieldset-body">
				            <table class="form-table" border="0" cellpadding="1" cellspacing="2">
				                <tr>
				                    <td class="form-label" style="width:200px;">表描述：</td>
				                    <td style="width:300px;">表名：</td>
				                    <td style="width:80px;">别名：</td>
				                    <td style="width:120px;">连接类型：</td>
				                </tr>
				                <tr>
				                    <td >刑期变动表</td>
				                    <td style="color:red">TBXF_SENTENCEALTERATION</td>
				                    <td >sen</td>
				                    <td >主表</td>
				                </tr>
				                <tr>
				                    <td >犯罪处罚信息表</td>
				                    <td style="color:red">TBPRISONER_BASE_CRIME</td>
				                    <td >tbc</td>
				                    <td ></td>
				                </tr>
				                <tr>
				                    <td >个人表现批次合并信息表</td>
				                    <td style="color:red">TBXF_PRISONERPERFORMANCEMERGE</td>
				                    <td >pem</td>
				                    <td ></td>
				                </tr>
				                <tr>
				                    <td >批次设置信息表</td>
				                    <td style="color:red">TBXF_COMMUTEPAROLE_BATCH</td>
				                    <td >bat</td>
				                    <td ></td>
				                </tr>
				                
				                <tr>
				                    <td >上次减刑信息视图</td>
				                    <td style="color:red">UV_LAST_COMMUTE_SENTCHAGE</td>
				                    <td >ucs</td>
				                    <td >左连接</td>
				                </tr>
				                
				                <tr>
				                    <td >罪犯减刑次数视图</td>
				                    <td style="color:red">UV_COMMUTE_SENTCHAGE_TIMES</td>
				                    <td >ust</td>
				                    <td >左连接</td>
				                </tr>
				                
				            </table>
				        </div>
				    </fieldset>
				    <p>操作说明：xxxx</p>
				</div>
	            </div>
       	</div>
   	</div>
   	<div class="hide">
		<!-- 资源树,右键菜单 -->
		<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpenTreeMenu">
		    <li name="remove" iconCls="icon-remove" onclick="onRemoveItem">删除</li>
		</ul>
	</div>

    <script type="text/javascript">
    	// 解析并初始化
        mini.parse();
    	var tree = mini.get("tree1");
    	
    	function reloadTree(){
    		var url = "<%=path%>/getQualifierItem.json?1=1";
    		tree.load(url);
    		qualifierItem();
    	}
    	
    	function qualifierItem(){
    		var itemlevel = mini.get("filter_itemlevel").getValue();
            if (itemlevel == "") {
                tree.clearFilter();
            } else {
            	itemlevel = itemlevel.toLowerCase(); 
                tree.filter(function (node) {
                    var text = node.itemlevel;
                    if (text==itemlevel) {
                        return true;
                    }
                });
            }
    	}
    	
        // 选择树节点时,默认是查看信息
		function onTreeNodeClick(e) {
		    var node = e.node;
		    if(null == node){
			    return false;
		    }
		    
		    editNodeStatus(node);
		    
		    return false;
		}
        
        function editNodeStatus(node){
        	node.operatetype="edit";
		    var form = new mini.Form("#editform");
		    form.setData(node);
		    mini.get("old_column").setValue(node.col_name);
		    
		    var opstatus = document.getElementById("opstatus");
			opstatus.innerHTML="编辑筛查元素";
			opstatus.style.color="red";
        }
		
        function onKeyEnter(e) {
        	searchItem();
        }
        
		//过滤树
        function searchItem(){
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
        
        function recoverStatus(){
        	var opstatus = document.getElementById("opstatus");
			opstatus.innerHTML="筛查元素";
			opstatus.style.color="blue";
        }
        
		function addItem(){
			var opstatus = document.getElementById("opstatus");
			opstatus.innerHTML="新增筛查元素";
			opstatus.style.color="red";
			newStatus();
		}
		
		function newStatus(){
			var node = {
					operatetype : "new",
					id:null,
					name:null,
					col_name:null,
					value_type:null,
					itemlevel:null,
					orderby:1,
					formula:null,
					remark:null
			    };
			var form = new mini.Form("#editform");
		    form.setData(node);
		    mini.get("old_column").setValue(null);
		}
		
		function savedata(){
			
			var form = new mini.Form("#editform");
			var col_name = mini.get("col_name").getValue();
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			//
			var data = form.getData();
			var url = "<%=path%>/saveQualifierItem.json";

			$.ajax({
				url: url,
				data: data,
				type: "post",
				dataType:"text",
				async:false,
				success: function (text) {
					if(text==1){
						alert("操作成功!");
						reloadTree();
						selectNodeByValue(col_name);
					}else{
						alert("操作失败!");
					}
					
				},
				error: function (jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
				}
			});
			
		}
		
		function selectNodeByValue(value){
			var node = tree.getNode(value);
			tree.selectNode(node);
			editNodeStatus(node);
		}
		
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
		    var removeItem = mini.getbyName("remove", menu);
		    // 设置为初始值
		    removeItem.show();
		};
		
		
		function onRemoveItem(){
			if (confirm("确定删除选中记录？")) {
				var node = tree.getSelectedNode();
				var id = node.id;
				var col_name = node.col_name;
		        $.ajax({
		            url: "<%=path%>/removeQualifierItem.json?id="+id+"&col_name="+col_name,
		            type: "post",
					dataType:"text",
					async:false,
		            success: function (text) {
		            	if(text==1){
							alert("操作成功!");
						}else{
							alert("操作失败!");
						}
		            	reloadTree();
		            	newStatus();
		            },
		            error: function () {
		           		alert("操作失败");
		            }
		        });
		    }
		}
		
    </script>
	</body>
</html>