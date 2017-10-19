<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>个人文件页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
			}
		</style>
	</head>
	<body>
		<input id="newedit" name="newedit" type="hidden" value=""/><!-- 标识，新增修改状态 -->
		<input id="grorgg" name="grorgg" type="hidden" value=""/><!-- 标识：选择个人文件夹，还是公共文件夹 -->
		<div id="datagrid" class="mini-splitter" style="width: 100%; height: 100%;">
			<div size="240" showCollapseButton="true">
				<div class="mini-toolbar" style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<span style="padding-left: 5px;">名称：</span>
					<input class="mini-textbox" vtype="maxLength:50;" id="key" onenter="onKeyEnter" />
					<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>
				</div>
				<div class="mini-fit">
					<ul id="tree1" class="mini-tree" url="ajaxAllFile.action?1=1" tyle="width: 100%; height: 100%;" showTreeIcon="true"
						textField="virtualname" idField="fileid" parentField="pfileid" expandOnNodeClick="true" resultAsTree="false"  >
					</ul>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<a class="mini-button" plain="true" iconCls="icon-add" onclick="newRow();">新增文件夹</a>
					<a class="mini-button" plain="true" iconCls="icon-edit" onclick="editRow();">修改文件夹</a>
					<a class="mini-button" iconCls="icon-upload" plain="true" onclick="donew()">上传文件</a>
					<a class="mini-button" iconCls="icon-download" plain="true" onclick="downfile();">下载文件</a>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove();">删除</a>
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" url="ajaxFileByFolderIdList.action?1=1&id=-100" 
						 allowAlternating="true" idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20" multiSelect="true">
						<div property="columns">
							<div type="checkcolumn" width="5%"></div>
							<div field="virtualname" width="30%" headerAlign="center" align="center" allowSort="true">文件名称</div>
							<div field="filetype" width="20%" headerAlign="center" align="center" allowSort="true">文件类型</div>
							<div field="optime" width="20%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd" allowSort="true">上传日期</div>
							<div field="opname" width="25%" headerAlign="center" align="center"  allowSort="true">上传人</div>
						</div>
					</div>
				</div>
			</div>
		</div>
        
        <div id="editWindow" class="mini-window" title="编辑" style="width:600px;height:300px" showModal="true" allowResize="true" allowDrag="true">
		    <div id="editform" class="form" style="min-height: 150px;" >
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData()" plain="true" iconCls="icon-save" style="width: 60px">存盘</a>
								<a class="mini-button" onclick="cancelEditWindow()" iconCls="icon-close" plain="true" style="width: 60px;">关闭</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 11px; padding-bottom: 5px;">
					<table style="table-layout: fixed;">
						<tr>
							<td style="width: 90px;">
								文件夹名称
							</td>
							<td style="width: 150px;">
								<input id="fileid" style="width: 150px;" name="fileid" class="mini-hidden"/>
								<input id="virtualname" style="width: 150px;" name="virtualname" class="mini-textbox"  vtype="maxLength:50" required="true"/>
							</td>
							<td style="width: 90px;">
								文件夹说明
							</td>
							<td style="width: 150px;">  
								<input id="remark" style="width: 150px;" name="remark" class="mini-textbox"  vtype="maxLength:50" />
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								上级文件夹
							</td>
							<td style="width: 150px;">
								<input id="pfileid" style="width: 150px;" name="pfileid" class="mini-hidden"/>
								<input id="pname" style="width: 150px;" name="pname" class="mini-textbox"  vtype="maxLength:50" />
							</td>
							<td style="width: 90px;">
								文件夹类型
							</td>
							<td style="width: 150px;">
								<input id="remark1" style="width: 150px;" name="remark1" class="mini-textbox"  vtype="maxLength:50"/>
							</td>
						</tr>
					</table>
				</div>
		   </div>
		</div>
        
		<div id="uploadDiv"
			style="display: none; position: absolute; font: 11px '宋体'; top: 100px; left: 200px; width: 300px; height: 100px; text-align: center; border: 1px solid #DAE6F3; background-color: #FFFFFF; padding: 1px; line-height: 22px; z-index: 102;">
		<form name="form3" enctype="multipart/form-data" method="post">  
			<table width="100%" style="margin: 0px; border: 0px; padding: 0px;" cellspacing="0" >
				<tr style="width: 100%; height: 24px; text-align: left; padding: 3px; margin: 0px; font: bold 13px '宋体'; color: #FFFFFF; border: 1px solid #DAE6F3; cursor: move; background-color: #99BCE8">
					<td>
						上传本地文件：
					</td>
					<td>
						<input type="button" value="上传" onclick="uploadfile();" />
						<input type="button" value="关闭" onclick="donen()" />
					</td>
				</tr>
				<tr style="height: 30px;"  align="center">
					<td colspan="2">
						<input name="file" id="file" type="file" style="width: 200px;" />
						<input id="iid" type="hidden" /><br/>
					</td>
				</tr>
				<tr style="height: 10px;"  align="center">
					<td colspan="2">
						<font color="red" size="2">注意：上传文件的大小不超过${size}M</font>
					</td>
				</tr>
			</table>
			  </form>  
		</div>
		<input value ="1" id="biaoshi" type="hidden"/>
		 
		<script type="text/javascript">
        mini.parse();
       	var tree = mini.get("tree1");
       	var upload = mini.get("uploadDiv");
       	var grid = mini.get("datagrid1");
       	tree.setShowExpandButtons(false);
       	grid.sortBy("optime","desc");
        grid.load();
        tree.on("nodeselect", function (e) {
            var fileid = parseInt(e.node.fileid);
            //判断 文件是 公共文件还是 个人文件 
            var ishared = 0;
            $.ajax({
                 type:'post',
                 url:'<%=path%>/file/judgeIsharedByFileid.action?1=1&fileid='+fileid,
                 async:false,
                 success:function (text){
                     ishared = parseInt(text);
                 }
            });
            //1，是公共文件夹。0：个人文件夹
            if(ishared == 1){
            	$("#grorgg").val('-3');
            }else{
            	$("#grorgg").val('-2');  
            }
            if(fileid == -2 || fileid == -3){
            	$("#grorgg").val(e.node.fileid);
            }
        	url="<%=path%>/file/ajaxFileByFolderIdList.action?1=1&id="+e.node.fileid;
            grid.setUrl(url);
            grid.sortBy("optime","desc");
            grid.load();
        });

        function remove() {
            var grorgg = $("#grorgg").val();
            if(parseInt(grorgg) == -3){
            	var pub = whetherPermissions('13667');
                if(parseInt(pub) == 0){
                     alert("您无权限操作公共文件夹!");
                     return;
                } 
            }
        	
            var rows = grid.getSelecteds();
            var node = tree.getSelectedNode();
            
            if(node && rows.length>0){
            		var ids = [];
                    if (confirm("确定删除选中记录？")) {
                        for (var i = 0, l = rows.length; i < l; i++) {
                            var r = rows[i];
                            ids.push(r.fileid);
                        }
                        $.ajax({
                            url: "<%=path%>/file/deleteFileByIdList.action?1=1&id="+ids+"&filedoc=doc",
                            type: "post",
                            success: function (text) {
                                grid.reload();
                            },
                            error: function () {
                                alert("操作失败！")
                            }
                        });
                   }
                }else{
                    if(!node){
                        alert("请选择需要删除的文件夹！");
                   	}else if (confirm("确定要删除该文件夹以及该文件夹下所有文件？")) {
	                	$.ajax({
	                        url: "<%=path%>/file/deleteFileByIdList.action?1=1",
	                        type: "post",
	                        data: {id:node.fileid,filedoc:'file'},
	                        success: function (text) {
	                            tree.reload();
	                        },
	                        error: function () {
	                        	alert("操作失败！")
	                        }
	                    });
                	 }
                }
           }
        function search() {
            var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase(); 
                tree.filter(function (node) {
                    var text = node.virtualname?node.virtualname.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
        function onKeyEnter(e) {
            search();
        }



        function donew(){
        	var grorgg = $("#grorgg").val();
            if(parseInt(grorgg) == -3){
            	var pub = whetherPermissions('13667');
                if(parseInt(pub) == 0){
                     alert("您无权限操作公共文件夹!");
                     return;
                } 
            }
            var node = tree.getSelectedNode();
            if(node){
                $("#iid").val(node.id);
            	document.getElementById("uploadDiv").style.display="";
            }else{
				alert("请选择目标文件夹！");
            }
        }
        function donen(){
        	document.getElementById("uploadDiv").style.display="none";
        }
        //上传文件 
        function uploadfile(){
            if($("#biaoshi").val() == '1'){
	            var node = tree.getSelectedNode();
	        	var id = node.fileid;
	            var name = $("#file").val();
	        	var file = document.getElementById("file");
	            file.select();//获取欲上传的文件路径                
				var path = document.selection.createRange().text;
	            var grid = mini.get("datagrid1");
	            //记录 返回值 文件的大小 
	            var filesize = 0;
	            //在上传之前 进行 判断文件的大小 
	            $.ajax({
	                type:'post',
	                url:'<%=path%>/file/judgeFileSize.action?1=1&filename='+encodeURI(encodeURI(path)),
	                async:false,
	                success:function (text){
	                     filesize = parseInt(text);
	                }
	            });
	            if(filesize > ${size}){
	                 alert("所选文件大于${size}M无法上传!");return;
	            }
	            if(name==''){
	      			 return false;
		        }else{
		       	   	   document.form3.method="post";
		       	   	   document.form3.action="<%=path%>/file/fileUpload.action?1=1&folderid="+id+"&biaoshi="+$("#biaoshi").val();
	      			   document.form3.submit();
	      			   document.getElementById('uploadDiv').style.display = 'none';
	      			   alert("上传成功!");
	      			   document.cookie = "";
		       }
            }
     	}
        function downfile(){
            var node = tree.getSelectedNode();
            var rows = grid.getSelecteds();
            if(!node){
            	 alert("请选择所需下载文件所在文件夹！");
            }else if(rows.length>0){
                if(rows.length>1){
                	alert("只能选中一个文件进行下载！");
                    }else{
                    	var ids = [];
                        var r = rows[0];
                        window.location.href="<%=path%>/file/fileDownLoad.action?1=1&realName="+encodeURIComponent(encodeURIComponent(r.realname));
                    }
             }else{
                  alert("请选择需要下载的文件！");
             }
 		   
 	    }
 	    //点击 新增 文件夹 弹出框 
        var editWindow = mini.get("editWindow");
        var form = new mini.Form("#editform");
        function newRow() {
        	var grorgg = $("#grorgg").val();
            if(parseInt(grorgg) == -3){
            	var pub = whetherPermissions('13667');
                if(parseInt(pub) == 0){
                     alert("您无权限操作公共文件夹!");
                     return;
                } 
            }
        	var node = tree.getSelectedNode();  
        	if(node == undefined){
                 alert("请选择所要添加到的文件夹!");return;
            } 
            var pfileid = node.fileid;
            var pname = node.virtualname;
            var remark1 = "";
            if(parseInt(grorgg) == -3){remark1 = "公共文件";}else{remark1="个人文件"}
            $("#newedit").val('new');
			editWindow.show();
            data = {pfileid:pfileid,pname:pname,remark1:remark1};
            form.setData(data);
            mini.get("remark1").setEnabled(false);
            mini.get("pname").setEnabled(false);
        }
        //修改文件夹
        function editRow(){
        	var grorgg = $("#grorgg").val();
            if(parseInt(grorgg) == -3){
            	var pub = whetherPermissions('13667');
                if(parseInt(pub) == 0){
                     alert("您无权限操作公共文件夹!");
                     return;
                } 
            }
             var node = tree.getSelectedNode();
             if(node == undefined){
                 alert("请选择需要修改的文件夹!");return;
             }
             var fileid = parseInt(node.fileid);
             if(fileid == -2 || fileid == -3){
                 alert("顶级文件夹无法修改!");return;
             }
             $("#newedit").val('edit');
             
             editWindow.show();
             $.ajax({
                 type:'POST',
                 url:"selectFolderByFolderId.action?id=" + fileid,
                 async:false,
                 success:function (text){
            	    var o = mini.decode(text);
            	    form.setData(o);
            	    var remark1 = "";
            	    if(parseInt(grorgg) == -3){
                    	remark1 = "公共文件";
                    }else{remark1="个人文件"}
            	    mini.get("remark1").setValue(remark1);
            	    mini.get("remark1").setEnabled(false);
                 }
             });
        }
        //保存 新增的文件夹内容 
        function saveData(){
        	var grorgg = $("#grorgg").val();
            /*if(parseInt(grorgg) == -3){
            	var pub = whetherPermissions('13667');
                if(parseInt(pub) == 0){
                     alert("您无权限操作公共文件夹!");
                     return;
                } 
            }*/
        	var virtualname = mini.get("virtualname").getValue();//文件夹 名称 
        	var pfileid = mini.get("pfileid").getValue();//上级id
        	var pname = mini.get("pname").getValue();//上级文件名称
        	var remark = mini.get("remark").getValue();//文件夹说明
        	var fileid = mini.get("fileid").getValue();//文件id 
        	var newedit = $("#newedit").val();
        	$.ajax({
                type:'post',
                url:'<%=path%>/file/savaNewFolder.action',
                data:{virtualname:virtualname,
                      pfileid:pfileid,
                      pname:pname,
                      remark:remark,
                      newedit:newedit,
                      fileid:fileid,
                      grorgg:grorgg},
                async:false,
                success:function(text){
                     if(text == 'success'){
                         alert('操作成功!');
                     }else if(text == 'error'){
                         alert('操作失败!');
                     }
                     tree.reload(); 
                     editWindow.hide();
                }
            });
        	
        }
        //关闭弹出框 
        function cancelEditWindow() {
			editWindow.hide();
			form.clear();
		};
		//点击修改和新增的时候 判断有无 权限操作 
		function whetherPermissions(permiss){
            var data = 0;
            $.ajax({
                url:'<%=path%>/whetherPermissions.action?1=1&permiss='+permiss,
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