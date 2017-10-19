<%@ page language="java"  import="java.util.*"  pageEncoding="utf-8"%>
<html>
<head>
<%
String path = request.getContextPath();
List<String>  list = (ArrayList<String>)request.getAttribute("diclist");
%>
<title>设置绑定字段</title>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate"> 
 <style type="text/css">
<!--
body,td,th {
	font-family: 宋体;
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background: #6699ff;
}
-->
</style>
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
function dosubmit(){
var allValue = document.all.ITEM_NAME.value;
var valueArr = allValue.split(";");
var field_name = valueArr[0];
var field_set = valueArr[1];
var border = valueArr[2];
var field_type = document.all.ITEM_TYPE.value;
var codescript = valueArr[3];
var iscode = valueArr[4];
parent.setField(field_name,field_type,field_set,border,codescript,iscode);
}

function onButton(td){
	td.style.backgroundImage='url(<%=path %>/Images/images/button_b.gif)';
	td.style.color='#000000';
}

function offButton(td){
	td.style.backgroundImage='';
	td.style.color='';
}
</script>
<body>
<form  name="frm" method="post">
<table class="TableBlock" width="100%" align=center>
	<tbody>
	<tr >
		<td class="TableHeader" noWrap colSpan="2">请选择绑定字段：</td>
	</tr>
	<tr>
		<td class="TableBody">表单字段：</td>
		<td class="TableBody">
			<select id="ITEM_NAME" style="width: 150px" name="ITEM_NAME">
				<%
					if(list!=null && list.size()>0){
						for(String m:list){
							String[] arrval = m.split(",");
							String name = arrval[0];
							String value = arrval[1];
				%>
				<option value="<%=name %>;3;0;<%=value %>;''"><%=value %></option>
				<%		
						}
					}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="TableBody">编辑类型：</td>
		<td class="TableBody">
		<select id="ITEM_TYPE" name="ITEM_TYPE" style="width: 150px"> 
			<option value="3" selected>编辑区域</option>
		</select>
		</td>
	</tr>
	<tr class="TableControl">
		<td align="center" colSpan="2"><input type="hidden" name="FIELD_STR">
		<a onclick="dosubmit();" class="mini-button" onmouseover="onButton(this);" onmouseout="offButton(this);">确定</a>
		<a onclick="parent.HideDialog('setFrm')" class="mini-button" onmouseover="onButton(this);" onmouseout="offButton(this);">取消</a>
		</td>
	</tr>
	</tbody>
</table>
</form>
</body>
</html>