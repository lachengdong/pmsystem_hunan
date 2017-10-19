<%@ page language="java" import="java.util.*;" pageEncoding="gbk"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String filepath = "";
	if ("".equals(path) || path.length() < 1) {

	} else {
		filepath = path;
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>数据库方案selectDBTableColumns.jsp</title>


		<script type="text/javascript">
	function onButton(td)
{
	td.style.backgroundImage='url(<%=path%>/Images/images/button_b.gif)';
	td.style.color='#000000';
}

function offButton(td)
{
	td.style.backgroundImage='';
	td.style.color='';
}

</script>
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
}
-->
</style>
		<style>
.tableBorder7 {
	width: 800; solid;
	background-color: #000000;
}

TD {
	font-family: 宋体;
	font-size: 12px;
	line-height: 15px;
}

th {
	background-color: #f7f7f7;
	color: #000000;
	font-size: 12px;
	font-weight: bold;
}

th.th1 {
	background-color: #333333;
}

td.TableBody7 {
	background-color: #B1EA45;
}
</style>
		<link href="css/CIC.css" rel="stylesheet" type="text/css">




		<script type="text/javascript">
		var data=window.parent.window.data;
		function onButton(td)
		{
			td.style.backgroundImage='url(<%=path%>/Images/images/button_b.gif)';
			td.style.color='#000000';
		}

		function offButton(td)
		{
			td.style.backgroundImage='';
			td.style.color='';
		}
	var columnList=[];
	
	function dosubmit(tableName){
		var cols = document.getElementsByName("columns");
		for(var i=0;i<cols.length;i++){
			if(cols[i].checked){
				var column={};
				var attribute=eval('('+cols[i].value+')');
				column.name=attribute.fromcolumns;
				column.type=attribute.fromcloumnstype;
				column.size=attribute.fromcloumnssize;
				if(null!=attribute.fromcolumnsscribe&&''!=attribute.fromcolumnsscribe){
					column.describe=attribute.fromcolumnsscribe;
				}else{
					column.describe=" ";
				}
				//column.describe=attribute.fromcolumnsscribe;
				columnList.push(column);
			}
		}
		var retValue={};
		if(null!=columnList&&""!=columnList){
			retValue.tableName=tableName;
			retValue.columnList=columnList;
			window.returnValue=retValue;
		}
		//alert(retValue.tableName+retValue.columnList[0].name);
		window.close();
	}
function doback(){
	history.go(-1);
}
  </script>
	</head>


	<body>
	<s:form  id="tableform" name="tableform"  theme="simple">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="TableBackGround">
			<tr>
				
				<td class="TableHeader" align="right">
					<s:if test="ddcid!=null"><input type="button" class="button" value="提交"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="subSchemeTable();">
					&nbsp;</s:if>
					<s:else>
					<input type="button" class="button" value="确定"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="dosubmit('<s:property value="#request.fromtablename"/>');">
						<input type="hidden" id="jsonarray" name="jsonarray"  />
					&nbsp;
					</s:else>
					<input type="button" class="button" value="取消"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="doback();">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="TableBody" colspan="3">
					<table id="columnsList" class="TableBody" cellspacing="0"
						cellpadding="4" rules="all" border="1" id="Datagrid1"
						style="width: 100%; border-collapse: collapse;">
						<tr class="DataGridHead">
							<td colspan="6">选择要导出的列
							</td>
						</tr>
						<tr  align="left">
							<s:set name="index" value="0"/>
							<s:iterator id="fromcolumnslist" value="#request.fromColumnsList" status="st">
								<s:set name="index" value="#index + 1"/>
								<s:if test="#index%4!=0">
									<td >
										<input type="checkbox"  name="columns" id="column<s:property value='#index'/>"  value="{'fromcolumns':'<s:property value='#fromcolumnslist.dcdfromcolumns'/>',
																  'fromcolumnsscribe':'<s:property value='#fromcolumnslist.dcdfromcolumnsscribe'/>',
																  'fromcloumnstype':'<s:property value='#fromcolumnslist.dcdfromcloumnstype'/>',
																  'fromcloumnssize':'<s:property value='#fromcolumnslist.dcdfromcloumnssize'/>',
																  'fromcloumnsdefaultvalue':'<s:property value='#fromcolumnslist.dcdfromcloumnsdefaultvalue'/>',
																  'fromcolumnspoint':'<s:property value='#fromcolumnslist.dcdfromcolumnspoint'/>'}" /><font color="green"><s:property value="#fromcolumnslist.dcdfromcolumns"/></font>
									
									</td>
								</s:if>
								<s:else>
									<td>
									<input type="checkbox"  name="columns" id="column<s:property value='#index'/>"  value="{'fromcolumns':'<s:property value='#fromcolumnslist.dcdfromcolumns'/>',
															  'fromcolumnsscribe':'<s:property value='#fromcolumnslist.dcdfromcolumnsscribe'/>',
															  'fromcloumnstype':'<s:property value='#fromcolumnslist.dcdfromcloumnstype'/>',
															  'fromcloumnssize':'<s:property value='#fromcolumnslist.dcdfromcloumnssize'/>',
															  'fromcloumnsdefaultvalue':'<s:property value='#fromcolumnslist.dcdfromcloumnsdefaultvalue'/>',
															  'fromcolumnspoint':'<s:property value='#fromcolumnslist.dcdfromcolumnspoint'/>'}" /><font color="green"><s:property value="#fromcolumnslist.dcdfromcolumns"/></font>
									
										</td>
									</tr>
									<tr align="left">
								</s:else>
							</s:iterator>
							
						</tr>
					</table>
				</td>
				<td colspan="3" class="TableBody" align="right" style="height:26px">
					<img src="Images/gkzx_logo.jpg" width="390" height="40">
				</td>
			</tr>
		</table>
	</s:form>
	</body>
</html>
