<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>通讯簿页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     	<style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    	</style> 
    	<script type="text/javascript">
		var deleteMessage = "请选择组或者联系人！";
		var message = "提示";
		var deleteConfirmMessage = "是否删除联系人";
		var confirmMessage = "是否删除分组";
		var confirmMessage2 = "该组下有联系人存在,是否删除分组";
		</script>
	</head>
<body>
    <input name="ispublic" id="ispublic" type="hidden"/>
    <input id="isnotpub" name="isnotpub" type="hidden" value=""/>
    <input id="userid" name="userid" type="hidden"/>
    <input id="personid" name="personid" value="-2" type="hidden"/>
	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	<input id="groupMenuid" name="groupMenuid" type="hidden" value="${groupMenuid}"/>
	<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="240" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input class="mini-textbox" vtype="maxLength:50;"  id="key" onenter="onKeyEnter"/>
            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="search()">查找</a>                  
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="ajaxPersionAll.action?1=1&state=YES" style="width:100%;height:100%;"
                showTreeIcon="true" expandOnNodeClick="true" textField="name" idField="groupid" parentField="pgroupid" showArrow="true" resultAsTree="false">        
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<form id="form1" method="post">
	        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
	            <input class="mini-hidden" name="id"/>
				<input id="state" type="hidden"/>
				<input id="isNew" value="" type="hidden"/>
	            <input name="keyid" id="keyid" type="hidden" />
	            <input name="isLeaf" id="isLeaf" type="hidden" />
	            <input name="suid" id="suid" class="mini-hidden" value="${suid}"/>
			            
	            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow(2)">新增分组</a>
	            <a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow(1)">新增联系人</a>
	            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a>     
	            <span class="separator"></span>             
	            <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData()">存盘</a>                  
	        </div>
        <div class="mini-fit" id="editForm1" >
             <fieldset style="width:99.5%;border:solid 1px #aaa;position:relative;">
		        <legend>基本信息</legend>
		        <div  style="padding:5px;" >
		         	<table style="width:100%;">
		                <tr>
		                    <td style="width:65px;">姓名：</td>
		                    <td style="width:210px;">
		                    	<input name="personname" id="name" class="mini-textbox"  vtype="maxLength:10" style="width:90%" required="true"/>
		                    	<input name="linkmanid" id="linkmanid" class="mini-hidden"/>
		                    </td>
		                    <td style="width:60px;">所在组：</td>
		                    <td style="width:210px;">
						          <input name="groupid" id="groupid" class="mini-hidden"   style="width:90%"  enabled="false"/>
							      <input name="groupname" id="groupname" class="mini-textbox" style="width:90%"  enabled="false"/>
							</td>
		                </tr>
		                <tr>
		                    <td style="width:60px;">生日：</td>
		                    <td style="width:210px;"><input name="birthday" id="birthday" renderer="onDateRenderer" dateFormat="yyyy-MM-dd" class="mini-datepicker"  style="width:90%"/></td>
		                    <td style="width:60px;">性别：</td>
		                    <td style="width:210px;"><input id="gender" name="gender"  class="mini-textbox" style="width:90%"/></td>
		                </tr>
		                <tr>
		                    <td style="width:60px;">昵称：</td>
		                    <td style="width:210px;">
		                    	<input name="nickname" id="nickname"  vtype="maxLength:10" class="mini-textbox"  style="width:90%"/>
							</td>
							<td style="width:60px;">职务：</td>
		                    <td style="width:210px;"><input name="duty" id="duty"  vtype="maxLength:30" class="mini-textbox"   style="width:90%"/></td>
		                </tr>
		                <tr>
		                	<td><font style="color:#0046D5;">联系方式</font></td>
		                </tr>
		                <tr>
		                	<td style="width:60px;">手机：</td>
		                    <td style="width:210px;"><input name="mobile"  vtype="rangeLength:7,11" id="mobile" class="mini-textbox"   style="width:90%"/></td>
		                </tr>
		                <tr>
		                	<td style="width:60px;">家庭电话：</td>
		                    <td style="width:210px;"><input name="hometelephone" id="hometelephone" vtype="rangeLength:7,11" class="mini-textbox"  style="width:90%"/></td>
		                    <td style="width:60px;">电子信箱：</td>
		                    <td style="width:210px;"><input name="deppostcode" id="deppostcode" vtype="email;rangeLength:5,20;" class="mini-textbox"  vtype="email;rangeLength:5,30;"  style="width:90%"/></td>
		                </tr>
		                <tr>
		                	<td style="width:60px;">MSN：</td>
		                    <td style="width:210px;"><input name="msn" id="msn"  vtype="maxLength:30"  class="mini-textbox"  vtype="email;rangeLength:5,30;"  style="width:90%"/></td>
		                    <td style="width:60px;">QQ/OICQ：</td>
		                    <td style="width:210px;"><input name="qq"  vtype="maxLength:15" id="qq" class="mini-textbox"  style="width:90%"/></td>
		                </tr>
		                <tr>
		                	<td><font style="color:#0046D5;">单位信息</font></td>
		                </tr>
		                <tr>
		                	<td style="width:60px;">单位名称：</td>
		                    <td style="width:210px;"><input name="depname" id="depname"  vtype="maxLength:50"  class="mini-textbox"    vtype="rangeLength:0,20"   style="width:90%"/></td>
		               		<td style="width:60px;">单位地址：</td>
		                    <td style="width:210px;"><input name="depaddress"  vtype="maxLength:80"  id="depaddress" class="mini-textbox"   vtype="rangeLength:0,40" style="width:90%"/></td>
		                </tr>
		                <tr>
		                	<td style="width:60px;">办公电话：</td>
		                    <td style="width:210px;"><input name="deptelephone" vtype="rangeLength:7,11" id="deptelephone"  class="mini-textbox"  style="width:90%"/></td>
		                    <td style="width:60px;">单位传真：</td>
		                    <td style="width:210px;"><input name="depfax" vtype="maxLength:40" id="depfax" class="mini-textbox"   style="width:90%"/></td>
		                </tr>
		                <tr>
		                	<td><font style="color:#0046D5;">家庭信息</font></td>
		                </tr>
		                <tr>
		                	<td style="width:60px;">家庭住址：</td>
		                    <td style="width:210px;"><input name="homeaddress"  vtype="maxLength:100" id="homeaddress" class="mini-textbox" style="width:90%"/></td>
		                </tr>
		            </table>
		        </div>
		    </fieldset>
	   </div>  
	   <div class="mini-fit" id="editForm2" style="display: none;">
            <fieldset style="width:99.5%;border:solid 1px #aaa;position:relative;">
		        <legend>联系人组信息</legend>
		        <div  style="padding:5px;" >
		         	<input id="state" type="hidden"/>
		            <table style="width:100%;">
		                <tr>
		                    <td style="width:65px;">组名称：</td>
		                    <td style="width:300px;">
		                    	<input id="name" name="name"  vtype="maxLength:25" required="true" class="mini-textbox" />
		                    </td>
		                </tr>
		                <tr>
		                    <td style="width:60px;">备注：</td>
		                    <td style="width:500px;">
		                    	<input name="remark" id="remark"  vtype="maxLength:150" class="mini-textbox" style="width:500px;" emptyText="请输入备注"></input>
		                    </td>
		                </tr>
		            </table>
		        </div>
		    </fieldset>
	   </div>  
	   </form>
	   
   </div>
</div>
    <script type="text/javascript">
        mini.parse();
       	var tree = mini.get("tree1");
     	var form = new mini.Form("form1");
       	var group=mini.get("groupid");
        tree.on("nodeselect", function (e) {
            $("#linkorgroupid").val("");
            $("#linkorgroupid").val(e.node.groupid);
       		editRow(e.node.groupid,e.isLeaf);
       		$("#groupid").val(e.node.groupid);
       		$("#isNew").val("2");
       		//每次赋值之前 都需要 "赋值空"
       		$("#isnotpub").val("");
       		//标识 选择的 是 个人通讯簿，或者是 公共通讯簿
       		if(e.node.groupid == '-2' || e.node.groupid == '-3'){
       			$("#isnotpub").val(e.node.groupid);
            }else{
                var obj = e.node.groupid+"";
                if(obj.indexOf("A")>-1){
                    var tree1 = tree.getParentNode(e.node);//取出父级节点
                    var tree2 = tree.getParentNode(tree1);
                    $("#isnotpub").val(tree2.groupid);
                }else{
                	var tree1 = tree.getParentNode(e.node);//取出父级节点
                    $("#isnotpub").val(tree1.groupid);
                }
            }
        });
       
        function editRow(groupid,isLeaf) {
            if(groupid==-2||groupid==-3){
				return;
            }
        	hide();
       		//表单加载员工信息
       		var url ="ajaxByGroupid.action?1=1&groupid=" + groupid;
       		if(isLeaf){
       			$("#editForm1").show();
       		}else{
       			$("#editForm2").show();
       			url += "&type=YES";
           	}
             form.loading();
             $.ajax({
                 url: url,
                 type: "post",
                 success: function (text) {
                     var o =  mini.decode(text); 
                     form.setData(o);
                     form.unmask();
                 }
             });        	
         }

        
        function addRow(id) {
            //alert($("#isnotpub").val());return;
            //只要 isnotpub里面的值 =-3 那么就说明 是操作的公共文件夹 
            if($("#isnotpub").val() == '-3'){
                 var pub = whetherPermissions('13642');
                 if(parseInt(pub) == 0){
                      alert("您无权限操作公共通讯簿!");
                      return;
                 }
            }
            
            var node = tree.getSelectedNode();//获取选择节点
            if(node == undefined){
                 alert("请选择所要添加节点位置!");return;
            }
            var groupname = node.name;
            var groupid = node.groupid;
            //alert(tree.isLeaf(node));
            //id=1 说明选择新增联系人
            
            if(parseInt(id) == 1){
                if(groupid == '-2' || groupid == '-3'){
                    alert("(联系人)不允许添加到一级目录中!");return;
                }
                var groupids = groupid+"";//转化为字符串 
                //alert(groupids+"||||||||||"+groupids.indexOf("A"));
                if(groupids.indexOf("A") != -1){
                    alert("(联系人)无法添加到联系人中!");return;
                }
            }else if(parseInt(id) == 2){
                if(groupid != '-2' && groupid != '-3'){
                    alert("(分组)必须添加到通讯簿中!");return;
                }
            }
            hide();
            show(id); 
            $("#state").val(id);
            $("#isNew").val("1");
            var data = {groupid:groupid,groupname:groupname};
            form.setData(data);
            //group.setUrl("ajaxPersionAll.action?1=1");
        }
		function hide(){
			$("#editForm1").hide();
        	$("#editForm2").hide();
		}
		function show(o){
			$("#editForm"+o).show();
		}
        
        function removeRow() {
           //alert($("#isnotpub").val());return;
           var node = tree.getSelectedNode();//获取选中节点 
           var t=tree.getParentNode(node);//取出父级节点
           if(node == undefined){
               alert("请选择需要删除的文件夹/文件!");return;
           }
           var keyid = node.groupid;
           if(keyid == '-3' || keyid == '-2'){alert('通讯簿无法删除!');return;}
           if($("#isnotpub").val() == '-3'){
               var pub = whetherPermissions('13642');
               if(parseInt(pub) == 0){
                    alert("您无权限操作公共通讯簿!");
                    return;
               }
           }
           
           var showMessage ="";
           var showName = tree.getText();
           var deleteUrl = "ajaxSystemRemoveShuJu.action?1=1&groupid=" + keyid;
           if(keyid == null||keyid =="") {
           		alert(deleteMessage);
           		return false;
           }else{
           		var node = tree.getSelectedNode();//获取选中节点
           		var arry = tree.getChildNodes(node);
           		if(tree.isLeaf(node)) {//不存在子节点，即叶子节点
           			deleteUrl;      			
           			showMessage=deleteConfirmMessage;           			
           		}else{
           			deleteUrl ;
           			showMessage=confirmMessage; 
           			if(arry.length>0){
           				showMessage =confirmMessage2;
               		}
                }
            	var data = form.getData();
            	var json = mini.encode([data]);                
           		mini.confirm(showMessage+showName,message,function(action){
	           		if(action == "ok"){
		           		$.ajax({
			                 url: deleteUrl,
			                 data: { data: json},
			                 type: "post",
			                 success: function (text) {
			                     form.unmask();
			                      var node = tree.getSelectedNode();
			                      if(node){
			                      	tree.removeNode(node);
			                      	form.reset();
			                      }
			                 }
			             });
			           }
           		});
           }
        }
        function saveData() {
            //alert($("#isnotpub").val());return;
        	if($("#isnotpub").val() == '-3'){
                var pub = whetherPermissions('13642');
                if(parseInt(pub) == 0){
                     alert("您无权限操作公共通讯簿!");
                     return;
                }
            }
            var data = form.getData();
            var json = mini.encode([data]);
            var ispublic = 0;
            var state = $("#state").val();//alert(state);
            var addUrl ="ajaxSystemSaveGroup.action?1=1";
            var node = tree.getSelectedNode();//获取选中节点 
            var t=tree.getParentNode(node);//取出父级节点
            if($("#isnotpub").val() == '-3'){
            	ispublic = 1;
            }
            //只有修改的时候 添加 控制 
            if($("#isNew").val() == 2){
            	if(node.groupid == '-2' || node.groupid == '-3'){
                    alert("只能修改子目录!"); return;
                 }
            }
            
            if($("#isNew").val() == 1){
            	url = addUrl+"&type="+state+"&ispublic="+ispublic;
            }else{
                var updateUrl ="ajaxSystemUpdateShuJu.action?1=1";
                if(tree.isLeaf(node)) {//不存在子节点，即叶子节点
        			 url=updateUrl+"&type=1&linkmanid="+node.groupid+"&ispublic="+ispublic;
        		}else{
        			 url=updateUrl+"&type=2&groupid="+node.groupid+"&ispublic="+ispublic;
            	}            
            }
            form.validate();
            if (form.isValid() == false) return;
            form.loading("保存中，请稍后......");
            //alert(url);
            $.ajax({
               url: url,
               data: { data: json},
               type: "post",
               success: function (text) {
               		alert("操作成功!");
                 	form.unmask();
                 	form.reset();
                 	tree.reload(); 
                 	$("#state").val("");
               },
               error: function (jqXHR, textStatus, errorThrown) {
            	   alert("操作失败!");
               }
            });
        }
        //过滤树
        function search() {
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

        //判断 用户 是否 有权限 操作 文件夹 (通过当前用户 和权限 判断能否查询出结果)
        function whetherPermissions(permiss){
            var data = 0;
            $.ajax({
                url:'whetherPermissions.action?1=1&permiss='+permiss,
                type:'post',
                async:false,
                success:function (text){
                    data=text;
                }
            });
            return data;
        }
    </script>
 </body>
</html>