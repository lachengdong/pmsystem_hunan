<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
		<SCRIPT language="JavaScript" src="<s:property value='#session.path'/>/js/CutString.js"></SCRIPT>
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
function SelectAllCheckboxes(spanChk)
{
	var xState=spanChk.checked;
	var theBox=spanChk;
	elm= document.getElementsByName("memberDutyInfoArr");
	//elm=theBox.form.elements;
	for(i=0;i<elm.length;i++)
	{
		if(elm[i].type=="checkbox" && elm[i].id!=theBox.id)
		{
			if(elm[i].checked!=xState)
			elm[i].click();
		}
	}
 }
 function SelectAllColumns(spanChk)
{
	var xState=spanChk.checked;
	var theBox=spanChk;
	elm= document.getElementsByName("selectValue");
	//elm=theBox.form.elements;
	for(i=0;i<elm.length;i++)
	{
		if(elm[i].type=="checkbox" && elm[i].id!=theBox.id)
		{
			if(elm[i].checked!=xState)
			elm[i].click();
		}
	}
 }
		function dosubmit(dataid,dataname)
		{
			var retValue=[];
			retValue.databaseid=dataid;
			retValue.databasename=dataname;
			window.returnValue=retValue;
			window.close();
		}
function showColumns(tableName)
	{
		
		tables = window.showModalDialog("getDBTableColumns.action?fromdatabaseid="+data.fromdatabaseid+"&fromtablename="+tableName);
		
	}
		</script>
	</head>

	<body leftMargin="1" topMargin="0">
		<s:form name="form1" action="" theme="simple">
			<s:hidden name="v_where"></s:hidden>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD class="TableHeader" height="30" colspan="2">
						&nbsp;数据库表
					</TD>
					
				</TR>
				<tr>
				<td>
				 <table id="tableForCheck" class="DataGrid" cellspacing="0" cellpadding="6" rules="all" border="1" id="gvList" style="width:100%;border-collapse:collapse;">
		                                 <tr class="DataGridHead">
		                                 	<th style="height:20px;width:2%;" height="10">
													<input type="checkbox" id="gvList_ctl01_chkAll"
														name="checkall"
														onclick="javascript:SelectAllCheckboxes(this);"
														/>
											</th>
			                                <th style="height:20px;width:5%;" scope="col" >编号</th>
			                                <th style="height:20px;width:10%;" scope="col" >名称</th>
			                                <th style="height:20px;width:10%;" scope="col" >备注</th>
			                                <th style="height:20px;width:15%;" scope="col" >类型</th>
			                                <th style="height:20px;width:25%;" scope="col" >
			                                <input type="checkbox" id="gvList_ctl01_chkAll"
											name="allCol"
											onclick="javascript:SelectAllColumns(this);"/>筛选字段</th>
											 <th style="height:20px;width:25%;" scope="col" >关联字段</th>
                                         </tr>
                                  <s:set name="index" value="0"/>
           						 <s:iterator value="#request.tablesList">
           						 <s:set name="index" value="#index + 1"/>
		                            <tr class="GridViewAlternatingRowStyle" onMouseOver="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onMouseOut="this.style.backgroundColor=e">
		                            	 <td>
											<center>
												<input type="checkbox" name="memberDutyInfoArr"  id="<s:property value="name"/>" value="<s:property value="id"/>"   />
											</center>
										</td> 
		                            	<td><s:property value="#index"/></td>
		                            	<td><a href="#" onclick="showColumns('<s:property value="tableName"/>')"><script language="javascript">CutStr('<s:property value="tableName"/>',15); </script></a></td>
		                            	<td><script language="javascript">CutStr('<s:property value="tableRemarks"/>',20); </script></td>
		                            	<td><script language="javascript">CutStr('<s:property value="tableType"/>',30); </script></td>
		                            	<td align="center"><input type="checkbox" name="selectValue"  id="selectValue<s:property value='#index'/>" value="<s:property value='tableName'/>"   /></td>
		                            	<td><input type="button" class="button" value="选择字段"
											onmouseover="onButton(this);" onmouseout="offButton(this);"
											onclick="" />
											<input type="button" class="button" value="填写"
											onmouseover="onButton(this);" onmouseout="offButton(this);"
											onclick="" />
										</td>
		                            	</tr> 
		                           </s:iterator>
	                      </table>
	                              
				</td>
				</tr>
				<tr>
				<td>
					<table align="right"><tr><td>
                    	<page:page name="pageController" title="记录" unit="条" frmName="form1" actionName="listTablesByDBid.action"/>
                    </td></tr></table>
				</td>
				</tr>
			</table>

		</s:form>


	</body>
</HTML>