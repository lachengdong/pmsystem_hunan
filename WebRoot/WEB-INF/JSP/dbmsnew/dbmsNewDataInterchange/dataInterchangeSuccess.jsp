<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库管理dataInterchangeSuccess.jsp</title>
		<base target="_self">
		<link rel="StyleSheet"
			href="<s:property value='#session.path'/>/css/dtree.css"
			type="text/css" />
		<link rel="stylesheet" href="<%=path%>/css/tree.css"/>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/ajax.js"></script>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/all.js"></script>
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
		<link href="<%=path%>/css/CIC.css" rel="stylesheet" type="text/css">
		<SCRIPT language="JavaScript" src="<%=path%>/js/bgColor.js"></SCRipt>
	</head>
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
</script>
	<body leftMargin="1" topMargin="0">
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="right" >
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD height="25">
													<div id="NavPan">
														<table cellpadding="0" cellspacing="0" border="0">
															<tr>
																<td align="center" valign="middle" class="NavTabBG" >
																	<a href="getDatasChemeNew.action" class="NavLink"
																		target="_self">数据导出</a>
																</td>
																<td align="center" valign="middle" class="NavTabBG">
																	<a href="admissionImport.action" class="NavLink"
																		target="_self">数据导入</a>
																</td>
																<td align="center" valign="middle" class="SelNavTabBG">
																	<a href="getDataInterchangeChemeInfo.action" class="NavLink"
																		target="_self">数据交换</a>
																</td>
															</tr>
														</table>
													</div>
												</TD>
											</TR>
											<TR>
												<TD class="TableHeader" height="30" width="20%">
													&nbsp;数据交换
												</TD>
												<td class="TableHeader" width="80%" align="right">
												<!-- <input onclick="javascript:history.go(-1);" class="button"
												onmouseover="onButton(this);" onmouseout="offButton(this);"
												type="button" value="上一步" />  
												    <input onclick="return goaction();" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="下一步"/>-->
												</td>
											</TR>
											<tr>
												<td colspan="2">
													<TABLE id="Table1" cellSpacing="1" cellPadding="4"
														width="100%" class="TableBackGround">
														<TR>
															<TD class="TableBody" colSpan="2" height="40"
																align="center">
																<table class="DataGrid" cellspacing="0" cellpadding="4"
																	rules="all" border="1" id="Datagrid1"
																	style="width: 100%; border-collapse: collapse;">
																	<tr class="DataGridHead" align="center">
																		<td height="10">
																			<b>数据交换成功！！</b>
																		</td>
																		
																	</tr>
																</table>
															</TD>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td align="right" valign="bottom">
							<page:page name="pageController" title="" unit="条"
								frmName="getConnectionMessageList" actionName="getConnectionMessageList.action" />
						</td>
					</tr>
				</table>
			</s:form>
	</body>
</HTML>