<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>添加导入方案addImpScheme.jsp</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	    <style type="text/css">
		    html, body
		    {
		        font-size:12px;
		        padding:0;
		        margin:0;
		        border:0;
		        height:100%;
		        overflow:auto;
		    }
	    </style>
		<script type="text/javascript">
			function upstep()
			{
				window.parent.document.getElementById("schememain").style.display="";
				window.parent.document.getElementById("schemeiframe").style.display="none";
			}
		function showColumns(fromtable,index)
		{
			var fromtableid=document.getElementById("fromtableid"+index).value;
			var expscheme=window.parent.document.getElementById("expscheme").value;
			var todatabaseid=window.parent.document.getElementById("todatabaseid").value;
			var totablename=document.getElementById("totablename"+index).value;
			var columnsList = window.showModalDialog("queryTableColumns.action?expscheme="+expscheme+"&fromtablename="+fromtable+"&fromtablenameid="+fromtableid+"&todatabaseid="+todatabaseid+"&totablename="+totablename+"&tr="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogWidth: "+(window.screen.availWidth   )   + "px;DialogHeight= "+(window.screen.availHeight   )   + "px;");
			if(columnsList!=null){
				var hiddenvalue="[";
				for(var k=0;k<columnsList.length;k++){
					if(hiddenvalue=="[")
					{
						hiddenvalue=hiddenvalue+"{'fromcolumns':'"+columnsList[k].fromcolumns+"','fromcolumnsscribe':'"+columnsList[k].fromcolumnsscribe+"','fromcloumnstype':'"+columnsList[k].fromcloumnstype+"','fromcolumnssize':'"+columnsList[k].fromcolumnssize+"','fromcolumnspoint':'"+columnsList[k].fromcolumnspoint+"','tocolumns':'"+columnsList[k].tocolumns+"','tocolumnsscribe':'"+columnsList[k].tocolumnsscribe+"','tocolumnstype':'"+columnsList[k].tocolumnstype+"','tocolumnssize':'"+columnsList[k].tocolumnssize+"','tocloumnsdefaultvalue':'"+columnsList[k].tocloumnsdefaultvalue+"','tocolumnspoint':'"+columnsList[k].tocolumnspoint+"','codetype':'"+columnsList[k].codetype+"','pkgenerator':'"+columnsList[k].pkgenerator+"','ispk':'"+columnsList[k].ispk+"'}";
					}
					else{
						hiddenvalue=hiddenvalue+",{'fromcolumns':'"+columnsList[k].fromcolumns+"','fromcolumnsscribe':'"+columnsList[k].fromcolumnsscribe+"','fromcloumnstype':'"+columnsList[k].fromcloumnstype+"','fromcolumnssize':'"+columnsList[k].fromcolumnssize+"','fromcolumnspoint':'"+columnsList[k].fromcolumnspoint+"','tocolumns':'"+columnsList[k].tocolumns+"','tocolumnsscribe':'"+columnsList[k].tocolumnsscribe+"','tocolumnstype':'"+columnsList[k].tocolumnstype+"','tocolumnssize':'"+columnsList[k].tocolumnssize+"','tocloumnsdefaultvalue':'"+columnsList[k].tocloumnsdefaultvalue+"','tocolumnspoint':'"+columnsList[k].tocolumnspoint+"','codetype':'"+columnsList[k].codetype+"','pkgenerator':'"+columnsList[k].pkgenerator+"','ispk':'"+columnsList[k].ispk+"'}";
					}
				}
				hiddenvalue=hiddenvalue+"]";
				document.getElementById("joinedfields"+index).value=hiddenvalue;
			}
		}
		function subScheme()
		{
		   document.getElementById("expscheme").value=window.parent.document.getElementById("expscheme").value;
		   document.getElementById("ddcname").value=window.parent.document.getElementById("ddcname").value;
		   document.getElementById("ddctype").value=window.parent.document.getElementById("ddctype").value;
		   document.getElementById("todatabaseid").value=window.parent.document.getElementById("todatabaseid").value;
			window.document.tables.action="addDatabaseImpScheme.action";
			window.document.tables.submit();
		}
		function mainTable(isMain){
			window.document.getElementById("ddtismaintable").value=isMain.value;
		}		
		function doback()
		{
			window.parent.window.dosubmit("showSchemeList.action");
		}
		function myreset()
		{
			if(confirm("刷新后将清空现有配置，确定要刷新吗？"))
				window.parent.window.myreset();
		}
	  </script>
	</head>
	<body>
	<s:form  id="tables" name="tables"  theme="simple">
		<s:hidden id="fromdatabaseid" name="fromdatabaseid"></s:hidden>
		<s:hidden id="todatabaseid" name="todatabaseid"></s:hidden>
		<s:hidden id="ddcname" name="ddcname"></s:hidden>
		<s:hidden id="ddctype" name="ddctype"></s:hidden>
		<s:hidden id="expscheme" name="expscheme"></s:hidden>
		<s:hidden name="ddtismaintable" id="ddtismaintable"></s:hidden>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
			<tr>
				<td align="right">
					<a class="mini-button" onclick="myreset()" plain="true" style="width:40px">刷新</a>
				</td>
			</tr>
			<tr>
				<td  colspan="3">
					<table id="tableList"  cellspacing="0"
						cellpadding="4" rules="all" border="1" id="Datagrid1"
						style="width: 100%; border-collapse: collapse;">
						<tr  align="center">
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
								主表
							</td>
							<td height="10">
								导入表描述
							</td>
							<td height="10">
								数据关系
							</td>
						</tr>
						 <s:set name="index" id="index" value="0"/>
						<s:iterator id="tables" value="#request.tablesList" status="st">
							<tr onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
								onmouseout="this.style.backgroundColor=e">
								<td>
									<input type="hidden" id="fromtableid<s:property value='#index'/>"  name="fromtableidarr" value="<s:property value='#tables.id.ddtid' />"/>
									<a href="#" onclick="showColumns('<s:property value='#tables.tablename' />','<s:property value='#index'/>')"><s:property value="#tables.tablename" /></a>
									<input type="hidden" name="tables" id="tables<s:property value='#index'/>" value="<s:property value='#tables.id.ddtid' />"/>
									<input type="hidden" name="tableNamearr" id="tableNamearr<s:property value='#index'/>" value="<s:property value='#tables.tablename' />" />
									<input type="hidden" name="descritionarr" id="descritionarr<s:property value='#index'/>" value="<s:property value="#tables.descrition" />"/>
								</td>
								<td align="center">
									<s:property value="#tables.descrition" />
								</td>
								<td align="center">
									<input type="text" id="totablename<s:property value='#index'/>"  name="totablenamearr" size="40" value="<s:property value='#tables.tablename' />"/> 
									<input type="hidden" name="joinedfieldsarr" id="joinedfields<s:property value='#index'/>"/>
								</td>
								<!-- <td><s:if test="#tables.ddtismaintable==0"><input type="checkbox" name="isMainTable<s:property value='#index'/>" checked="checked"/></s:if><s:else><input type="checkbox" name="isMainTable<s:property value='#index'/>" /></s:else></td>
								-->
								<td><input type="radio" name="isMainTable" value="<s:property value='#tables.id.ddtid' />" id="isMainTable<s:property value='#index'/>" onclick="mainTable(this);" /></td>
								<td align="center">
									<input type="text" id="todescrition<s:property value='#index'/>"  name="todescritionarr" size="25" value="<s:property value='#tables.descrition' />"/> 
								</td>
								<td align="center">
									<input type="text" id="shujuguanxi<s:property value='#index'/>"  name="shujuguanxiarr" size="25" value="<s:property value='#tables.shujuguanxi' />"/> 
								</td>
							</tr>
							<s:set name="index" id="index" value="#index + 1"/>
						</s:iterator>
					</table>
				</td>
			</tr>
		</table>
		</s:form>
	</body>
</html>
