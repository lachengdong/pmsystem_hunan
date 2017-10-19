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
		<title></title>
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
		<form id="form1" method="post"  enctype="multipart/form-data">
			<input id="cacid" name="cacid" value="${record.cacid}" class="mini-hidden" />
			<input id="operatetype" name="operatetype" class="mini-hidden" />
			<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" onclick="onOk();" plain="true"
								style="width: 60px">确定</a>
							<a class="mini-button" onclick="onCancel();" plain="true"
								style="width: 60px;">取消</a>
						</td>
						<td style="white-space: nowrap;">
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-left:15px;margin-top:10px;">
				<table border="0">
					<tr>
						<td style="width:70px;">
							序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号
						</td>
						<td style="width:270px;">
							<input name="sn" id="sn" value="${record.sn}" class="mini-spinner"  minValue="1" style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							方案名称
						</td>
						<td>
							<input name="name" id="name" value="${record.name}" allowInput="true" class="mini-textbox" required="true" style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							从严从宽
						</td>
						<td>
							<input name="characteristic" id="characteristic" value="${record.characteristic}" class="mini-treeselect" url="commutationParole/ajaxGetCodeData.action?1=1&codeType=GK1101" multiSelect="true" textField="text" valueField="id" parentField="pid" checkRecursive="false" showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick" popupWidth="268" style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							罪&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名
						</td>
						<td>
							<input name="crimetype" id="crimetype" value="${record.crimetype}" class="mini-treeselect" url="commutationParole/ajaxGetCodeData.action?1=1&codeType=GB075" multiSelect="true" textField="text" valueField="id" parentField="pid" checkRecursive="false" showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick" popupWidth="268" style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							所在监狱
						</td>
						<td>
							<input name="inprison" id="inprison" value="${record.inprison}" class="mini-treeselect" url="commutationParole/ajaxGetDepartData.action?1=1&unitLevel=3&topOrgid=4400" multiSelect="true" textField="text" valueField="id" parentField="pid" checkRecursive="false" showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick" popupWidth="268" style="width:100%;" />
						</td>
						
					</tr>
					<tr>
						<td>
							职务级别
						</td>
						<td>
							<input name="dutylevel" id="dutylevel" value="${record.dutylevel}" class="mini-treeselect" url="commutationParole/ajaxGetCodeData.action?1=1&codeType=GB018" multiSelect="true" textField="text" valueField="id" parentField="pid" checkRecursive="false" showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick" popupWidth="268" style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							办案法院
						</td>
						<td>
							<input name="court" id="court" value="${record.court}" class="mini-treeselect" url="commutationParole/ajaxGetCodeData.action?1=1&codeType=GK1102" multiSelect="true" textField="text" valueField="id" parentField="pid" checkRecursive="false" showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick" popupWidth="268" style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							收案开始
						</td>
						<td>
							<input name="receivestart" id="receivestart" value="${record.receivestart}" class="mini-datepicker" format="yyyy-MM-dd" valueField="id" textField="text"  emptyText="收案开始时间" showNullItem="true" style="width:100%;" />
						</td>
						</tr>
						<tr>
						<td>收案结束</td>
						<td>
							<input name="receiveend" id="receiveend" value="${record.receiveend}" class="mini-datepicker" format="yyyy-MM-dd" emptyText="收案结束时间" showNullItem="true"  style="width:100%;" />
						</td>
					</tr>
					<tr>
						<td>
							罪犯姓名
						</td>
						<td>
							<input name="crimename" id="crimename" value="${record.crimename}" class="mini-textbox" emptyText="多个姓名请以逗号隔开" style="width:100%;" />
						</td>
					</tr>
					 <tr>
						<td>
							原判刑期
						</td>
						<td>
							<input name="originalsentence" id="originalsentence" value="${record.originalsentence}" class="mini-treeselect" url="ajaxSelectData.action?id=senid&text=name&table=tbxf_prisonersentence" multiSelect="true" textField="text" valueField="id" parentField="pid" checkRecursive="false" showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick" popupWidth="268" style="width:100%;" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		<script type="text/javascript">
			function onOk(){
				SaveData();
			}
			function SaveData() {
				var operatetype = mini.get("operatetype").getValue();
				var cacid = mini.get("cacid").getValue();
				var form = new mini.Form("form1");
				var o = form.getData();
		        form.validate();
		        if (form.isValid() == false) return;
		        var json = mini.encode([o]);
		        $.ajax({
	                url: "commutationParole/saveCaseAttentionChoice.action?1=1",
	                data: {data:json, operatetype:operatetype, cacid:cacid},
	                type: "post",
	                async:false,
	                success: function (text) {
	                	CloseWindow("save");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
		    }
			//标准方法接口定义
	        function SetData(data) {
	        	data = mini.clone(data);
	            mini.get("operatetype").setValue(data.action);
	        }
			function onCancel(e) {
	            CloseWindow("cancel");
	        }
	        function CloseWindow(action) {            
	            if (action == "close" && form.isChanged()) {
	                if (confirm("数据被修改了，是否先保存？")) {
	                    return false;
	                }
	            }
	            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	            else window.close();
	        }
		</script>
	</body>
</html>