<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>评价选项</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
		var deleteMessage = "请先选中一个栏目节点！";
		var deleteConfirmMessage = "是否删除此节点？";
		var deleteParentConfirmMessage = "此节点包含子节点,确认删除？(子节点也将被删除！)";
	 </script>

	</head>
	<body>
		<div id="datagrid" class="mini-splitter" style="width: 100%; height: 100%;">
			<div showCollapseButton="true" size="70%">
				<div class="mini-toolbar" style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" vtype="maxLength:50;" emptyText="请输入标题"  id="key2" onenter="onKeyEnter1"/>
		            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="search2()">查找</a>
		            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="batchRemove()">批量删除</a>        
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" url="getOptionList.json?1=1" idField="id"  allowResize="true" pageSize="20" 
					        	allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
					         <div property="columns">
					      		<div type="checkcolumn" ></div>  
					            <!-- <div field="oid" headerAlign="center" align="center" allowSort="true" renderer="onStateRenderer" >编号</div> -->
					            <div field="name" headerAlign="center" align="center" allowSort="true">标题</div>   
					            <div field="optime" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">操作时间</div>    
					            <div field="" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
							</div>
					    </div>	
				</div>
			</div>
			<div showCollapseButton="true" size="30%">
				<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
		            <span class="separator"></span>             
		            <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData()" id="ids">保存</a>                  
		       </div>
		       <div class="mini-fit" >
		            <fieldset style="border:solid 1px #aaa;position:relative;height:97%;" >
				        <legend>选项信息</legend>
				        <div id="form1" style="padding:5px;">
				        	<input id="operator" name="operator" value="new" class="mini-hidden" />
				        	<input id="oid" name="oid" class="mini-hidden"/>
				            <table>
					                <tr>
					                	<td width="25%">
					                		选项名称：
					                	</td>
					                    <td>
					                    	<input name="name" class="mini-textbox" required="true" onclick="add1()" vtype="maxLength:100;"/>
					                    </td>
					                </tr>
					                <tr>
					                    <td width="25%">
					                		上级选项：
					                	</td>
					                    <td>
					                    	<input name="poid" id="poid" class="mini-combobox" textField="name" valueField="oid" emptyText="请选择..."   url="" allowInput="true" showNullItem="true" nullItemText="请选择..."/>
					                    </td>
					                </tr>
					                <tr>
					                    <td width="25%">
					                    	排序号：
					                    </td>
					                    <td>
					                    	<input name="sn" id="sn" class="mini-spinner" minValue="1" maxValue="9999"/>
					                    </td>
					                </tr>
					            </table>
				        </div>
				    </fieldset>
		            </div>  
			</div>
		</div>
<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("sn", "desc");
        grid.load();
        var form = new mini.Form("form1");
       // var tree = mini.get("tree1");
        
        //菜单树
        /*tree.on("nodeselect", function (e) {
        	var oid = e.node.oid;
        	grid.load({ oid: oid});
        	grid.sortBy("TYPE", "asc");
        });*/
        
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
       		var s = '<a class="Edit_Button" href="javascript:edit(\'' + uid + '\')" >修改</a>&nbsp;'
       		//+ '<a class="Edit_Button" href="javascript:edit(\'' + uid + '\')" >修改</a>&nbsp;'
       		s = s + '<a class="Edit_Button" href="javascript:batchRemove(\'' + uid + '\')" >删除</a>'
            return s;
        }
        function onDateRenderer(e) {
	    	if(e && e.value){
	    		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	    	}
	        return "";
	    }
   		 //新增
        function add(){
        	form.clear();
        	mini.get("operator").setValue("new");//设置类型为新增
        	//上级栏目的回显
	        var poid = mini.get("poid");
			var url = 'getOptionTree.json?1=1';
			poid.setUrl(url);
	        form.setChanged(false);
	        $.ajax({
	        	url:"getOptionMaxSn.json?1=1",
	        	type:"post",
	        	cache:false,
	        	success: function(text){
	        		var o = mini.decode(text);
	        		var sn = mini.get("sn");
	        		sn.setValue(o.sn);
	        		sn.disable();
	        	}
	        });
        }
   		//新增
        function add1(){
   			var operator = mini.get("operator").getValue();
        	//mini.get("operator").setValue("new");//设置类型为新增
        	if(operator == 'new'){
        		//上级栏目的回显
		        var poid = mini.get("poid");
				var url = 'getOptionTree.json?1=1';
				poid.setUrl(url);
		        form.setChanged(false);
		        $.ajax({
		        	url:"getOptionMaxSn.json?1=1",
		        	type:"post",
		        	cache:false,
		        	success: function(text){
		        		var o = mini.decode(text);
		        		var sn = mini.get("sn");
		        		sn.setValue(o.sn);
		        		sn.disable();
		        	}
		        });
        		
        	}
        }
   		 //栏目编辑页
        function edit(oid){
   			var row = grid.getSelected();
   			if(row){
	            $.ajax({
	                 url: "getOptionDetail.json?1=1&oid=" + row.oid,
	                 cache: false,
	                 success: function (text) {
	                     var o = mini.decode(text);
	                     form.setData(o);
	                     mini.get("operator").setValue("update");
	                     //上级栏目的回显
				         var poid = mini.get("poid");
						 var url = 'getOptionTree.json?1=1';
						 poid.setUrl(url);
	                     form.setChanged(false);
	                     var operator = mini.get("operator").getValue();
	                    mini.get("sn").disable();
	                     
	                 }
	            });
   			} else {
   				alert("选中行！");
   			}
        }
   		//保存
        function saveData(){
        	var operator = mini.get("operator").getValue();
        	if(operator == "update"){//更新
	            var data = form.getData();
	            form.validate();
	            if (form.isValid() == false) return;
	            var json = mini.encode([data]);
	            //form.loading("更新中，请稍后......");
	            $.ajax({
	                url: "saveOption.json?1=1",
	                data: {data: json},
	                type: "post",
	                success: function (text) {
	                  	form.unmask();
	                  	//tree.reload();
			            //tree.load("getOptionTree.json?1=1");
			            grid.load();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	form.unmask();
	                  	//tree.reload();
			            //tree.load("getOptionTree.json?1=1");
			            grid.load();
	                	//alert("操作失败!");
	                }
	            });
	         }
	         if(operator == "new"){//更新
	            var data = form.getData();
	            form.validate();
	            if (form.isValid() == false ) return;
	            var json = mini.encode([data]);
	           // form.loading("保存中，请稍后......");
	            $.ajax({
	                url: "saveOption.json?1=1",
	                data: { data: json },
	                cache: false,
	                type: "post",
	                success: function (text) {
	                    form.unmask();
	                  	//tree.reload();
			            //tree.load("getOptionTree.json?1=1");
			            grid.load();
			            form.clear();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	form.unmask();
	                  	//tree.reload();
			            //tree.load("getOptionTree.json?1=1");
			            grid.load();
			            form.clear();
			            mini.get("operator").setValue("new");
	                	//alert("操作失败!");
	                }
		        });
            }
        }

   		function view(oid){
   			var row = grid.getSelected();
   			if(row){
	   			$.ajax({
	                 url: "getOptionDetail.json?1=1&oid=" + row.oid,
	                 cache: false,
	                 success: function (text) {
	                     var o = mini.decode(text);
	                     form.setData(o);
	                	 var poid = mini.get("poid");
						 var url = 'getOptionTree.json?1=1';
						 poid.setUrl(url);
	                     form.setChanged(false);
	                 }
	            });
   			} else {
   				alert("请选中行！");
   				
   			}
   		}
   		//批量删除
		function batchRemove(){
			var rows = grid.getSelecteds();
			if(rows.length>0){
				if(confirm("执行此操作后，子节点也将被删除，是否继续？")){
					var str = [];
					for(var i=0; i<rows.length; i++){
						var r = rows[i];
						str.push(r.oid);
					}
					var ids = str.join(",");
					grid.loading("操作中，请稍后......");
					$.ajax({
						url:"<%= path%>/familyevaluate/batchRemove.json?id=" +ids,
						success: function (text){
							grid.reload();
							form.unmask();
		                  	//tree.reload();
				            //tree.load("getOptionTree.json?1=1");
				            grid.load();
						},
						error: function (){
							alert("操作失败");
							grid.reload();
							form.unmask();
		                  	//tree.reload();
				            //tree.load("getOptionTree.json?1=1");
				            grid.load();
						}
					});
				}
			}else {
					alert("请选择至少一篇文章进行删除！");
			}
		}
   		//过滤菜单树
        /*function search1() {
            var key = mini.get("key1").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase();                
                tree.filter(function (node) {
                    var text = node.name ? node.name.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
        function onKeyEnter(e) {
            search1();
        }*/
        function onKeyEnter1(e) {
            search2();
        }
        //快速查询   
        function search2() {
            var key = mini.get("key2").getValue();
            grid.load({ key: encodeURIComponent(key)});
        }
        
    </script>
	</body>
</html>