<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>个人文件页面</title>
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
			<div size="240" showCollapseButton="true">
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<span style="padding-left: 5px;">名称：</span>
					<input class="mini-textbox" vtype="maxLength:50;" id="key"
						onenter="onKeyEnter" emptyText="请输入栏目名称"/>
					<a class="mini-button" iconCls="icon-search" plain="true"
						onclick="search()">查找</a>
				</div>
				<div class="mini-fit">
					<ul id="tree1" class="mini-tree"
						url="getModuleTree.json?1=1"
						tyle="width: 100%; height: 100%;" showTreeIcon="true"
						textField="title" idField="moduleid" parentField="pmoduleid" resultAsTree="false"  showArrow="true"  expandOnNodeClick="true" >
					</ul>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
		            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>     
		            <span class="separator"></span>             
		            <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData()">保存</a>                  
		       </div>
				<div class="mini-fit" >
					<form id="form1" method="post">
			            <fieldset style="border:solid 1px #aaa;position:relative;">
					        <legend>栏目信息</legend>
					        	<input id="operator" name="operator" value="new" class="mini-hidden" />
					        	<input id="moduleid" name="moduleid" class="mini-hidden" />
					        	<input id="selectModuleid" name="selectModuleid" type="hidden"/>
					            <table style="width:90%;height:50%;" align="center">
					                <tr>
					                	<td width="20%">
					                		上级栏目：
					                	</td>
					                    <td style="padding-left: 0px;">
					                    	<input name="pmoduleid" id="pmoduleid" class="mini-combobox" style="width:70%" textField="title" valueField="moduleid" emptyText="请选择..."   url="" allowInput="true" showNullItem="true" nullItemText="请选择..."/>
					                    </td>
					                	<td width="20%">
					                		栏目标题：
					                	</td>
					                    <td>
					                    	<input name="title" class="mini-textbox" required="true" onclick="add1()" vtype="maxLength:50;" style="width:50%"/>
					                    </td>
					                </tr>
					                <tr>
					                	<td width="20%">
					                		英文名称：
					                	</td>
					                    <td>
					                    	<input name="name" id="name" class="mini-textbox" required="true" vtype="maxLength:25;" onvalidation="onEnglishValidation" style="width:70%"/>
					                    	<span id="span1"></span>
					                    </td>
					                    <td width="20%">
					                    	栏目类型：
					                    </td>
					                    <td>
					                    	<select name="type" id="type" class="mini-radiobuttonlist">
						                        <option value="1">文章</option>
						                        <option value="0">视频</option>
						                    </select>
					                    </td>
					                   
					                </tr>
					                <tr>
					                    <td width="20%">
					                    	是否显示：
					                    </td>
					                    <td>
					                    	<select name="state" id="state" class="mini-radiobuttonlist">
						                        <option value="1">是</option>
						                        <option value="0">否</option>
						                    </select>
					                    </td>
					                    <td width="20%">
					                    	排&nbsp;&nbsp;序&nbsp;号：
					                    </td>
					                    <td>
					                    	<input name="sn" id="sn" class="mini-spinner" renderer="onGenderRenderer" minValue="1" maxValue="9999" style="width:50%;"/>
					                    </td>
					                </tr>
					            </table>
					    </fieldset>
				    </form>
		    	</div>  
			</div>
		</div>
<script type="text/javascript">
        mini.parse(); 
        var form =  new mini.Form("form1");
        
       	var tree = mini.get("tree1");
        tree.on("nodeselect", function (e) {
        	
        	document.getElementById("selectModuleid").value=e.node.moduleid;
        	editModule(e.node.moduleid);
        });
        
        //栏目编辑页
        function editModule(moduleid) {
            $.ajax({
                 url: "getModuleidDetail.json?1=1&moduleid=" + moduleid,
                 cache: false,
                 success: function (text) {
                     var o = mini.decode(text);
                     form.setData(o);
                     mini.get("operator").setValue("update");
                     //上级栏目的回显
			         var pmoduleid = mini.get("pmoduleid");
					 var url = 'getModuleTree.json?1=1';
					 pmoduleid.setUrl(url);
                     form.setChanged(false);
                 }
            });
        }
        
        //新增
        function add(){
        	var selectModuleid = document.getElementById("selectModuleid").value
        	form.clear();
        	mini.get("operator").setValue("new");//设置类型为新增
        	//上级栏目
        	mini.get("pmoduleid").setValue(selectModuleid);;
			mini.get("type").setValue("1");//默认文章类型为视频
			mini.get("state").setValue("1");//默认显示状态显示
        	//上级栏目的回显
	        var pmoduleid = mini.get("pmoduleid");
			var url = 'getModuleTree.json?1=1';
			pmoduleid.setUrl(url);
	        form.setChanged(false);
	        $.ajax({
	       		url:"getMaxSn.json?1=1",
	       		type:"post",
	       		cache:false,
	       		success: function (text){
	        			var o = mini.decode(text);
	        			mini.get("sn").setValue(o.sn);
	        	
	       		}
	        });
        }
        
        //保存
        function saveData(){
        	var operator = mini.get("operator").getValue();
        	if(operator == "update"){//更新
	            var data = form.getData();
	            form.validate();
	            if (form.isValid() == false) return;
	            var json = mini.encode([data]);
	            form.loading("更新中，请稍后......");
	            $.ajax({
	                url: "saveModule.json?1=1",
	                data: {data: json},
	                type: "post",
	                success: function (text) {
	                  	form.unmask();
	                  	tree.reload();
			            tree.load("getModuleTree.json?1=1");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	//alert("操作失败!");
	                }
	            });
	         }
	         if(operator == "new"){//更新
	            var data = form.getData();
	            form.validate();
	            if (form.isValid() == false ) return;
	            var json = mini.encode([data]);
	            //form.loading("保存中，请稍后......");
	            $.ajax({
	                url: "saveModule.json?1=1",
	                data: { data: json },
	                cache: false,
	                type: "post",
	                success: function (text) {
			            form.clear();
	                    form.unmask();
	                  	tree.reload();
			            tree.load("getModuleTree.json?1=1");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	//alert("操作失败!");
	                }
		        });
            }
        }
        //删除节点
        function remove() {
           var node = tree.getSelectedNode();//获取选中节点
           if(!node) {
           		alert(deleteMessage);
           		return false;
           }else{
           		var moduleid = node.moduleid;
           		if(tree.isLeaf(node) ) {//不存在子节点，即叶子节点
           			
	           		if(confirm(deleteConfirmMessage)){
		           		$.ajax({
			                 url: "deleteModule.json?moduleid="+moduleid,
			                 type: "post",
			                 success: function (text) {
			                     form.unmask();
			                     form.reset();
			                     tree.load("getModuleTree.json?1=1");
			                 }
			             });
	           		}
           		}else{//非叶子节点
           			if(confirm(deleteParentConfirmMessage)){
		           		$.ajax({
			                 url: "deleteModule.json?moduleid="+moduleid,
			                 type: "post",
			                 success: function (text) {
			                     form.unmask();
			                     form.reset();
			                     tree.reload();
			                     tree.load("getModuleTree.json?1=1");
			                 }
			             });
	           		}
           		}
           }
        }
        
        function updateError(e) {
            var el = document.getElementById("span1");
            search3();
           	/*if(!search3()){
	          	if (el) {
	                el.innerHTML = "栏目名已存在";
	          	}
           	}*/
        }
        function onNameValidation(e) {        
            updateError(e);
        }
        //查询重复栏目
        function search3() {
            	var modulename = mini.get("name").getValue();
                modulename = modulename.toLowerCase(); 
                var operator = mini.get("operator").getValue();
                if(operator == "new"){
		               	$.ajax({
		               			url: "searchModulename.json?name="+modulename,
		               			cache: false,
		               			type: "post",
		               			success:function(text){
		               			var data = mini.decode(text);
		               					if(text != "{}" && text != ""){
		               						alert("栏目名已存在！");
		               						mini.get("name").setValue("");
		               						return false;
		               					}else{
		               						return true;
		               					}
		               			}
		               	});
               	}               
        }
        //过滤栏目树
        function search() {
            var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase();                
                tree.filter(function (node) {
                    var text = node.title ? node.title.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
      function onKeyEnter(e) {
          search();
      }
      
      function onEnglishValidation(e) {
            if (e.isValid) {
                if (isEnglish(e.value) == false) {
                    e.errorText = "必须输入英文";
                    e.isValid = false;
                }
            }
      }
       /* 是否英文 */
      function isEnglish(v) {
          var re = new RegExp("^[a-zA-Z\_]+$");
          if (re.test(v)) return true;
          return false;
      }
       //新增
      function add1(){
    	   var operator = mini.get("operator").getValue();
    	   if(operator == 'new'){
		       $.ajax({
		      		url:"getMaxSn.json?1=1",
		      		type:"post",
		      		cache:false,
		      		success: function (text){
		       			var o = mini.decode(text);
		       			mini.get("sn").setValue(o.sn);
		       	
		      		}
		       });
    		   
    	   }
      }
    </script>
	</body>
</html>