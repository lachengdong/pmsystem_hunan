<%@ page language="java" pageEncoding="utf-8"%>


<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库列表</title>
		<base target="_self">
		<link rel="StyleSheet"
			href="<s:property value='#session.path'/>/css/dtree.css"
			type="text/css" />
		<link rel="stylesheet" href="<%=path%>/css/tree.css" />
		<script type="text/javascript"
			src="<s:property value='#session.path'/>/js/ajax.js"></script>
		<script type="text/javascript"
			src="<s:property value='#session.path'/>/js/all.js"></script>
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
		<SCRIPT language="JavaScript" src="<%=path%>/js/validator.js"></SCRIPT>
		<!--		<SCRIPT language="JavaScript" src="<%=path%>/js/calendarutf-8.js"></SCRIPT>-->
		<link href="<%=path%>/css/CIC.css" rel="stylesheet" type="text/css">
		<SCRIPT language="JavaScript" src="<%=path%>/js/bgColor.js"></SCRipt>
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

		function DisableButton() {
			window.setTimeout("disableButton('" + window.event.srcElement.id + "')", 0);
			document.forms[0].submit();
		}
		function disableButton(buttonID) {
			document.getElementById(buttonID).value='loading...'; 
			document.getElementById(buttonID).disabled=true;
		}
		
		function dosubmit(dataid,dataname)
		{
			var retValue=[];
			retValue.databaseid=dataid;
			retValue.databasename=dataname;
			window.returnValue=retValue;
			window.close();
		}
		</script>
	</head>

	<body leftMargin="1" topMargin="0">
		<s:form name="form1" action="" theme="simple">
			<s:hidden name="v_where"></s:hidden>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD class="TableHeader" height="30" colspan="2">
						&nbsp;数据库列表
					</TD>
					
				</TR>
				<tr>
					<td colspan="2">
						<table class='DataGrid' cellspacing='0' cellpadding='4'
							rules='all' border='1' id='Datagrid1'
							style='width: 100%; border-collapse: collapse;'>
							<tr class='DataGridHead' align='center'>
								<td height='30'>
									数据库描述
								</td>
								<td height='30'>
									数据库IP
								</td>
								<td height='30'>
									数据库类型
								</td>
								<td height='30'>
									所属部门
								</td>
							</tr>
							<s:iterator id="database" value="#request.dataBaseList"
								status="st">
								<tr <s:if test="#st.Even"> class="GridViewRowStyle" </s:if><s:else> class="GridViewAlternatingRowStyle" </s:else> onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onmouseout="this.style.backgroundColor=e">
									<td height='30' width='25%' align='center'>
										<a href="#" onclick='dosubmit("<s:property value='#database.ddid'/>","<s:property value='#database.ddname'/>")'><font color='#000000'><s:property value='#database.ddname'/></font></a>
									</td>
									<td height='30' align='center' width='25%'>
										<font color='#000000'>
										<s:property value='#database.ddip'/>
										</font>
									</td>
									<td height='30' align='center' width='25%'>
										<font color='#000000'>
										<s:property value='#database.databasetype'/>
										</font>
									</td>
									<td height='30' align='center' width='25%'>
										<font color='#000000'>
										<s:property value='#database.ddorg'/>
										</font>
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>

		</s:form>


	</body>
</HTML>