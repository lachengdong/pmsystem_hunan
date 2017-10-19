<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>人民陪审员</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/common.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/dangka.js" type="text/javascript"></script>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path %>/demo/fileupload/swfupload/swfupload.js"></script>

		<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	/*background-color: #ccffcc;*/
	background-attachment: fixed;
}
</style>
	</head>
	<body>
	<input  type="hidden" name="assessorid" id="assessorid"/>
	<input  type="hidden" name="sdids" id="sdids"/>
		<form id="form1" method="post"  enctype="multipart/form-data">
			<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
						<input  type="hidden" name="type" id="type"/>
							<a class="mini-button" onclick="SaveData" plain="true"
								style="width: 60px">确定</a>
							<a class="mini-button" onclick="onCancel" plain="true"
								style="width: 60px;">取消</a>
						</td>
						<td style="white-space: nowrap;">
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-left: 60px;">
				<table>
					<tr>
						<td>
							陪审员姓名
						</td>
						<td>
							<input name="assessorname" id="assessorname" allowInput="true" class="mini-textbox" required="true" />
						</td>
							
					</tr>
					<tr>
						<td>
							性别
						</td>
						<td>
							<input name="assessorsex" id="assessorsex" class="mini-combobox" style="width:80px;" textField="text" valueField="id" emptyText="请选择..."   url="ajaxCodeShuJu.action?sctid=GB003" allowInput="true" showNullItem="true" nullItemText="请选择..." required="true" />
						</td>
					</tr>
					<tr>
						<td>
							联系方式
						</td>
						<td>
							<input name="assessortel" id="assessortel" allowInput="true" class="mini-textbox" required="true" />
						</td>
					</tr>
					<tr>
						<td>
							部门名称
						</td>
						<td>
						<!-- <s:property value="#request.sdid"/> getDepName.action?1=1-->
							<input id="sdid" name="sdid" class="mini-combobox" style="width: 125px;" textField="text" valueField="id" showNullItem="false" url="" value="" required="true" />
						</td>
					</tr>
					<tr>
						<td>
							备注
						</td>
						<td>
							<input id="assessorremarks" name="assessorremarks" class="mini-textbox"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<script type="text/javascript">
    		/*mini.parse();
    		var grid = new mini.Form("#form1");
    		function SaveData() {
    			var type = $("#type").val();
    			var assessorid = $("#assessorid").val();
    			var sdids = document.getElementById("sdids").value;
        		var assessorname = document.getElementById("assessorname").value;
        		var assessortel = document.getElementById("assessortel").value;
        		var assessorremarks = document.getElementById("assessorremarks").value;
    			var sdid = mini.get("sdid").getValue();
    			var form = new mini.Form("#form1");
            	form.validate();
            	if (form.isValid() == false) return;
    			var data = grid.getData();
    			var json = mini.encode([data]);
    			$.ajax({
    				url : "savePeoplesAssessor.action?1=1",
    				type : "post",
    				async:false,
    				data : {data:json,type:type,id:assessorid,sdids:sdids},
    				success : function(text) {
    					if (text == "success") {
    						CloseWindow();
    					} else {
    						alert("保存失败");
    					}
    				}
    			});
    		}
    	
		//标准方法接口定义
		function SetData(data) {
		$("#type").val(data.action);
			if (data.action == "new") {
				data = mini.clone(data);
			}else if(data.action == "edit"){
				data = mini.clone(data);
				$("#assessorid").val(data.id);
				$.ajax({
	                url: "loadPeoplesAssessor.action?1=1&id="+data.id+"&sdids="+data.sdid+"&assessorname="+data.assessorname,
	                type: "post",
	                cache: false,
	                success: function (text) {
	                	var o = mini.decode(text);
                        grid.setData(o);
                        grid.setChanged(false);
	                }
	            });
				
			
			}
		}
		 //
       function CloseWindow(action) {            
           if (action == "close" && form.isChanged()) {
               if (confirm("数据被修改了，是否先保存？")) {
                   return false;
               }
           }
           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
           else window.close();            
       }
       //
       function onCancel(e) {
           CloseWindow("cancel");//取消关闭窗口
       }
       
       function callback(msg) 
{ 
　　document.getElementById("file").outerHTML = document.getElementById("file").outerHTML; 
　　document.getElementById("msg").innerHTML = "<font color=red>"+msg+"</font>"; 
} 
       */
</script>
	</body>
</html>