<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>

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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库方案</title>


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
	var data=window.parent.window.data;
	var tableList=[];
	function upstep()
	{
		window.parent.document.getElementById("schememain").style.display="";
		window.parent.document.getElementById("schemeiframe").style.display="none";
	}
	function showColumns(op,tableid,fromtable)
	{
		
		var rowindex=op.parentNode.parentNode.rowIndex;
		var totablenames=document.getElementsByName("totablename");
		var todescritions=document.getElementsByName("todescrition");
		var fromtableid=document.getElementsByName("fromtableid");
		var todatabaseid=document.getElementById("todatabaseid").value;
		var expscheme=document.getElementById("expscheme").value;
		var totable=totablenames[rowindex-1].value;
		var url="queryTableColumns.action?expscheme="+expscheme+"&fromtablename="+tableid+"&todatabaseid="+todatabaseid+"&totablename="+totablenames[rowindex-1].value+"&tr="+new Date().getTime();
		var columnsList = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogWidth: "+(window.screen.availWidth   )   + "px;DialogHeight= "+(window.screen.availHeight   )   + "px;");
		if(tableList.length<1)
		{
			var tableop=document.getElementById("tableList");
			for(var i=0;i<tableop.rows.length-1;i++)
			{
				var tables=[];
				tables.fromtableid=fromtableid[i].value;
				tables.totablenames=totablenames[i].value;
				tables.todescritions=todescritions[i].value;
				tables.columnsList=[];
				tableList.push(tables);
			}
		}
		if(undefined!=columnsList)
		{
			tableList[rowindex-1].columnsList=columnsList;
		}
	}
	function dosubmit()
	{
		var retvalue="";
		for(var i=0;i<tableList.length;i++)
		{
			retvalue+=tableList[i].fromtableid+"::"+tableList[i].totablenames+"::"+tableList[i].todescritions+"::";

			var columnsList=tableList[i].columnsList;
			var columnsStr="";
			for(var j=0;j<columnsList.length;j++)
			{
				columnsStr+=columnsList[j].fromcolumns+"##"+columnsList[j].fromcolumnsscribe+"##"+columnsList[j].fromcloumnstype+"##"+
						  columnsList[j].fromcloumnssize+"##"+columnsList[j].fromcloumnsdefaultvalue+"##"+columnsList[j].fromcolumnspoint+"##"+
						  columnsList[j].tocolumns+"##"+columnsList[j].tocolumnsscribe+"##"+columnsList[j].tocolumnstype+"##"+
						  columnsList[j].tocolumnssize+"##"+columnsList[j].tocloumnsdefaultvalue+"##"+columnsList[j].tocolumnspoint+"##"+columnsList[j].iscode+"##"+columnsList[j].codetype+"%%";
		    }
		    if(columnsStr.length>0)
		    {
		    	columnsStr=columnsStr.substring(0,columnsStr.length-2);
			}
		    retvalue+=columnsStr+"]]";
	    }
	    if(retvalue.length>0)
	    {
	    	retvalue=retvalue.substring(0,retvalue.length-2);
	    }
	    document.getElementById("jsonarray").value=retvalue;
	    document.forms[0].action="updateAddTables.action";
	    document.forms[0].submit();
	    window.close();
<%--	    window.parent.window.dosubmit("addDatabaseImpScheme.action",retvalue);--%>
	}
  </script>
	</head>


	<body>
	<s:form  id="tables" name="tables"  theme="simple">
		<s:hidden name="jsonarray"></s:hidden>
<%--		<s:hidden name="ddcname"></s:hidden>--%>
<%--		<s:hidden name="ddctype"></s:hidden>--%>
		<s:hidden name="ddcid"></s:hidden>
		<s:hidden name="expscheme"></s:hidden>
		<s:hidden name="todatabaseid"></s:hidden>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="TableBackGround">

			<tr>

				<td class="TableHeader" align="right">
					<input type="button" class="button" value="确定"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="dosubmit();">
					&nbsp;
					<input type="button" class="button" value="取消"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="javaScript:window.location.href=showSchemeList.action";
>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="TableBody" colspan="3">
					<table id="tableList" class="TableBody" cellspacing="0"
						cellpadding="4" rules="all" border="1" id="Datagrid1"
						style="width: 100%; border-collapse: collapse;">
						<tr class="DataGridHead" align="center">
							<td height="10">
								导出表名
							</td>
							<td height="10">
								导出表描述
							</td>
							<td height="10">
								导入表名
							</td>
							<td height="10">
								导入表描述
							</td>
						</tr>
						<s:iterator id="tables" value="#request.tablesList" status="st">
							<tr <s:if test="#st.Even"> class="GridViewRowStyle" </s:if>
								<s:else> class="GridViewAlternatingRowStyle" </s:else>
								onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
								onmouseout="this.style.backgroundColor=e">
								<td align="center">
									<input type="hidden" name="fromtableid" value="<s:property value='#tables.id.ddtid' />"/>
									<a href="#" onclick="showColumns(this,'<s:property value='#tables.id.ddtid' />','<s:property value='#tables.tablename' />')"><s:property value="#tables.tablename" /></a>
								</td>
								<td align="center">
									<s:property value="#tables.descrition" />
								</td>
								<td align="center">
									
									<input type="text" name="totablename" size="25" value="<s:property value='#tables.tablename' />"/> 
								</td>
								<td align="center">
									<input type="text" name="todescrition" size="25" value="<s:property value='#tables.descrition' />"/> 
								</td>
							</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="3" class="TableBody" align="right" style="height: 26px">
					<img src="Images/gkzx_logo.jpg" width="390" height="40">
				</td>
			</tr>
		</table>
		</s:form>
	</body>
</html>
