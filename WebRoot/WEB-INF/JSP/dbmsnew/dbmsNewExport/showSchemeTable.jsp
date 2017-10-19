<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>添加导出方案表showSchemeTable.jsp</title>
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
var table={};
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
 
function dosubmit()
{
	//var retValue=[];
	//retValue.databaseid=dataid;
	//retValue.databasename=dataname;
	//window.returnValue=retValue;
	//window.close();
	
}
function goBack(){
	history.go(-1);
}
function selectTables(){
	elm= document.getElementsByName("tables");
	for(i=elm.length;i>0;i--)
	{
		if(!elm[i-1].checked){
		  var Container = document.getElementById("tableForCheck");
		  Container.deleteRow(i); 
		}
	}

}
function showColumns(ddcid,tableName)
{
	fromdatabaseid=document.getElementById("fromdatabaseid").value;
	table = window.showModalDialog("getDBTableColumns.action?fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName,"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:200px");
}
function selectFields(ddcid,tableName){
	var joinColumns="";
	var table1={};
	fromdatabaseid=document.getElementById("fromdatabaseid").value;
	table1 = window.showModalDialog("getDBTableColumns.action?fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName,"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;DialogWidth: "+(window.screen.availWidth   )   + "px;DialogHeight= "+(window.screen.availHeight   )   + "px;");
	if(null!=table1&&''!=table1){
		for(var k=0;k<table1.columnList.length;k++){
			if(null!=table1.columnList[k].type){
				if('VARCHAR2'==table1.columnList[k].type||'VARCHAR'==table1.columnList[k].type){
				joinColumns+="a."+table1.columnList[k].name+"='[@_"+table1.columnList[k].name+"_@]' and ";
				}else{
				joinColumns+="a."+table1.columnList[k].name+"=[@_"+table1.columnList[k].name+"_@] and ";
				}
			}
		}
		joinColumns=joinColumns.substring(0,joinColumns.length-4);
		document.getElementById("ddtjoinedfields").value=joinColumns;
	}
}
function subTable()
{
	//var schemeValue=data.ddcname+data.schemetype+data.fromdatabaseid;
	var columnValue="";
	var screen;
	var mainTable;
	isMainTable = document.getElementById("mainTable").checked;//是否是主表
	if(isMainTable){
		mainTable=0;
	}else{
	    mainTable=1;
	}
	document.getElementById("ddtismaintable").value=mainTable;
	var isScreen = document.getElementById("screen").checked;//是否筛选
	//joinedFields = document.getElementById("joinedFields"+num).value;//关联字段
	if(isScreen){
		screen=1;
	}else{
		screen=0;
	}
	document.getElementById("ddtisscreen").value=screen;
	//alert(mainTable+":d:"+screen);
	if(null!=table.columnList&&table.columnList.length>0){
		for(var k=0;k<table.columnList.length;k++){
		columnValue+=table.columnList[k].name+";"+table.columnList[k].type+";"+table.columnList[k].size+";"+table.columnList[k].describe+"%";
		}
		document.getElementById("jsonarray").value=columnValue;
	}
	//alert(columnValue);
	document.getElementById("ddcname").value=window.parent.document.getElementById("ddcname").value;
	document.form1.action="saveTableInfo.action";
	document.form1.submit();
	//window.parent.document.getElementById("schememain").style.display="";
	//window.parent.document.getElementById("schemeiframe").style.display="none";
}
		</script>
	</head>
	<body leftMargin="1" topMargin="0">
		<s:form name="form1" action="" theme="simple">
			<s:hidden id="jsonarray" name="jsonarray"></s:hidden>
			<s:hidden id="fromdatabaseid" name="fromdatabaseid"></s:hidden>
			<input type="hidden" id="ddcname" name="ddcname" value=""/>
			<input type="hidden" id="ddcid" name="ddcid" value="<s:property value="#request.ddcid"/>"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD class="TableHeader" height="30" >
						&nbsp;数据库表
					</TD>
					<td class="TableHeader" align="right">
						<input type="button" class="button" value="提交"
							onmouseover="onButton(this);" onmouseout="offButton(this);"
							onclick="javaScript:subTable();" />
						&nbsp;
						<input type="button" class="button" value="取消"
							onmouseover="onButton(this);" onmouseout="offButton(this);"
							onclick="javaScript:history.go(-1);";>
						&nbsp;
					</td>
					
				</TR>
				<tr>
				<td colspan="2">
				 <table id="tableForCheck" class="DataGrid" cellspacing="0" cellpadding="6" rules="all" border="1" id="gvList" style="width:100%;border-collapse:collapse;">
   					<tr class="tableheader" onMouseOver="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onMouseOut="this.style.backgroundColor=e">
					<td align="center" class="DataGridHead" style="font-size:12px;" width="20%">编号</td> 
					<td class="DataGridHead" align="center" style="font-size:12px;" width="20%">名称</td>
					<td class="DataGridHead" align="center" style="font-size:12px;" width="25%">关联字段</td>
					<td class="DataGridHead" style="font-size:12px;" align="center">主表</td>
					<td class="DataGridHead" style="font-size:12px;" align="center">筛选</td>
					<td class="DataGridHead" style="font-size:12px;" align="center">导出序号</td>
					<td class="DataGridHead" style="font-size:12px;" align="center">数据关系</td>
					<td class="DataGridHead" style="font-size:12px;" align="center">额外查询条件</td>
					<td class="DataGridHead" style="font-size:12px;" align="center" width="10%">备注</td>
					</tr>
   					<s:iterator value="#request.tablesList">
                      <tr class="GridViewAlternatingRowStyle" onMouseOver="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onMouseOut="this.style.backgroundColor=e">
                     
                      <td><s:hidden name="ddtid"></s:hidden><s:property value="id.ddtid"/></td>
                       <td><input type="text" name="fromtablename" value="<s:property value="tablename"/>"/></td>
                       <td>
                     	<s:textfield name="ddtjoinedfields"/>
                     	<input type="button" class="button" value="选择"onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="selectFields('<s:property value="ddcid"/>','<s:property value="tablename"/>');" />
						</td>
                       <td><s:hidden id="ddtismaintable" name="ddtismaintable"></s:hidden><s:if test="ddtismaintable==0"><input type="checkbox" id="mainTable" name="mainTable" checked="checked"/></s:if><s:else><input type="checkbox" id="mainTable" name="mainTable" /></s:else></td>
                       <td><s:hidden id="ddtisscreen" name="ddtisscreen"></s:hidden><s:if test="ddtisscreen==1"><input type="checkbox" id="screen" name="screen" checked="checked"/></s:if><s:else><input type="checkbox" id="screen"  name="screen" /></s:else></td>
						<td >
						<input type="text" name="ddtorder" value="<s:property value="ddtorder"/>" size="4"></input>
						</td>
						<td >
						<input type="text" name="shujuguanxi" value="<s:property value="shujuguanxi"/>" size="4"></input>
						</td>						
						<td >
						<input type="text" name="addcondition" value="<s:property value="addcondition"/>" size="4"></input>
						</td>						
						<td><s:textfield name="descrition" size="10"/></td>
                      	</tr> 
                      	
                      	<tr class="GridViewAlternatingRowStyle" onMouseOver="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onMouseOut="this.style.backgroundColor=e">
                      	<td>已选择数据列
                      	</td>
                      	<td colspan="13">
                      	<s:iterator  value="#request.fromColumnsList" >
                      	<s:property value="dcdfromcolumns"/>,
                      	</s:iterator>
                      	</td>
                      	<!-- <td><input type="button" class="button" value="重新选择导出列"
							onmouseover="onButton(this);" onmouseout="offButton(this);"
							onclick="javaScript:showColumns('<s:property value="ddcid"/>','<s:property value="tablename"/>');" />
                      	</td>-->
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