<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>
<html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>添加数据交换方案addSwapScheme.jsp</title>
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
		    }
	    </style>
		<script type="text/javascript">
			function upstep()
			{
				window.parent.document.getElementById("schememain").style.display="";
				window.parent.document.getElementById("schemeiframe").style.display="none";
			}
			function showColumns(op,tableid,index)
			{
				var fromdatabaseid=window.parent.document.getElementById("fromdatabaseid").value;
				var todatabaseid=window.parent.document.getElementById("todatabaseid").value;
				var rowindex=op.parentNode.parentNode.rowIndex;
				var fromtablename=document.getElementsByName("tableNamearr");
				var fromdescritions=document.getElementsByName("descritionarr");
				var totablenames=document.getElementsByName("totablenamearr");
				var todescritions=document.getElementsByName("todescritionarr");
				var totable=totablenames[rowindex-1].value;
				var fromtable=fromtablename[rowindex-1].value;
				var urlaa="querySwapTableColumns.action?fromdatabaseid="+fromdatabaseid+"&fromtablename="+fromtable+"&todatabaseid="+todatabaseid+"&totablename="+totable+"&tr="+new Date().getTime();
				var revalue = window.showModalDialog(urlaa,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;DialogWidth: "+(window.screen.availWidth   )   + "px;DialogHeight= "+(window.screen.availHeight   )   + "px;");
				var hiddenvalue="[";
				var textvalue="";
				if(revalue!=undefined){
					for(var k=0;k<revalue.length;k++){
						if(hiddenvalue=="[")
						{
							hiddenvalue=hiddenvalue+"{'fromcolumns':'"+revalue[k].fromcolumns+"','fromcolumnsscribe':'"+revalue[k].fromcolumnsscribe+"','fromcloumnstype':'"+revalue[k].fromcloumnstype+"','fromcolumnssize':'"+revalue[k].fromcolumnssize+"','fromcolumnspoint':'"+revalue[k].fromcolumnspoint+"','tocolumns':'"+revalue[k].tocolumns+"','tocolumnsscribe':'"+revalue[k].tocolumnsscribe+"','tocolumnstype':'"+revalue[k].tocolumnstype+"','tocolumnssize':'"+revalue[k].tocolumnssize+"','tocloumnsdefaultvalue':'"+revalue[k].tocloumnsdefaultvalue+"','tocolumnspoint':'"+revalue[k].tocolumnspoint+"','codetypelist':'"+revalue[k].codetypelist+"','pkgeneratorlist':'"+revalue[k].pkgeneratorlist+"','ispklist':'"+revalue[k].ispk+"'}";
						}
						else{
							hiddenvalue=hiddenvalue+",{'fromcolumns':'"+revalue[k].fromcolumns+"','fromcolumnsscribe':'"+revalue[k].fromcolumnsscribe+"','fromcloumnstype':'"+revalue[k].fromcloumnstype+"','fromcolumnssize':'"+revalue[k].fromcolumnssize+"','fromcolumnspoint':'"+revalue[k].fromcolumnspoint+"','tocolumns':'"+revalue[k].tocolumns+"','tocolumnsscribe':'"+revalue[k].tocolumnsscribe+"','tocolumnstype':'"+revalue[k].tocolumnstype+"','tocolumnssize':'"+revalue[k].tocolumnssize+"','tocloumnsdefaultvalue':'"+revalue[k].tocloumnsdefaultvalue+"','tocolumnspoint':'"+revalue[k].tocolumnspoint+"','codetypelist':'"+revalue[k].codetypelist+"','pkgeneratorlist':'"+revalue[k].pkgeneratorlist+"','ispklist':'"+revalue[k].ispk+"'}";
						}
					}
				}
				hiddenvalue=hiddenvalue+"]";
				document.getElementById("joinedfieldsarr"+index).value=hiddenvalue;
			}
			function doback()
			{
				window.parent.window.dosubmit("showSchemeList.action");
			}
			//复选框全选方法
			var bool=false;
			function doCheckAll(op)
			{
				bool=!bool;
				var checkList=document.getElementsByName("checktable");
				for(var i=0;i<checkList.length;i++)
				{
					checkList[i].checked=bool;
			    }
			}
			//复选框全选方法
			var sievebool=false;
			function dosievecheckall(op)
			{
				sievebool=!sievebool;
				var checkList=document.getElementsByName("isScreen");
				for(var i=0;i<checkList.length;i++)
				{
					checkList[i].checked=sievebool;
			    }
			}
			//表筛选方案
			function dosieve()
			{
				var tablelist = document.getElementById("tableList");
				var checkList=document.getElementsByName("checktable");
				for(var i=checkList.length;i>0;i--)
				{
					if(!checkList[i-1].checked)
					{
						tablelist.deleteRow(i); 
					}
			    }
			}
			//添加关联字段
			function selectFields(op,tableName){
				var joinedFieldslist =document.getElementsByName("joinedFields");
				var joinFieldstr="";
				var rowindex=op.parentNode.parentNode.rowIndex;
				var fromdatabaseid=window.parent.document.getElementById("fromdatabaseid").value;;
				var table1={};
				table1 = window.showModalDialog("getDBTableColumns.action?fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName);
				if(null!=table1 && undefined != table1 &&""!=table1){
					for(var k=0;k<table1.columnList.length;k++){
						if(null!=table1.columnList[k].type){
							if('INT'==table1.columnList[k].type ||'INTEGER'==table1.columnList[k].type ||'FLOAT'==table1.columnList[k].type 
									 ||'NUMERIC'==table1.columnList[k].type ||'DECIMAL'==table1.columnList[k].type ||'BIGINT'==table1.columnList[k].type
									 ||'SMALLINT'==table1.columnList[k].type ||'NUMBER'==table1.columnList[k].type ||'DOUBLE'==table1.columnList[k].type)
								joinFieldstr+="a."+table1.columnList[k].name+"=[@_"+table1.columnList[k].name+"_@] and ";
							else
								joinFieldstr+="a."+table1.columnList[k].name+"='[@_"+table1.columnList[k].name+"_@]' and ";
						}
					}
					joinFieldstr=joinFieldstr.substring(0,joinFieldstr.length-4);
					joinedFieldslist[rowindex-1].value=joinFieldstr;
				}
			}
			function myreset()
			{
				if(confirm("刷新后将清空现有配置，确定要刷新吗？"))
					window.parent.window.myreset();
			}
			function mainTable(isMain){
				window.document.getElementById("ddtismaintable").value=isMain.value;
			}
			function subScheme()
			{
			   document.getElementById("expscheme").value=window.parent.document.getElementById("expscheme").value;
			   document.getElementById("ddcname").value=window.parent.document.getElementById("ddcname").value;
			   document.getElementById("ddctype").value=window.parent.document.getElementById("ddctype").value;
			   document.getElementById("fromdatabaseid").value=window.parent.document.getElementById("fromdatabaseid").value;
			   document.getElementById("todatabaseid").value=window.parent.document.getElementById("todatabaseid").value;
				window.document.tables.action="addDatabaseSwapScheme.action";
				window.document.tables.submit();
			}
		  </script>
	</head>
<body>
	<s:form  id="tables" name="tables"  theme="simple">
		<s:hidden name="fromdatabaseid" id="fromdatabaseid"></s:hidden>
		<s:hidden name="todatabaseid" id="todatabaseid"></s:hidden>
		<s:hidden name="ddcname" id="ddcname"></s:hidden>
		<s:hidden name="ddctype" id="ddctype"></s:hidden>
		<s:hidden name="expscheme" id="expscheme"></s:hidden>
		<s:hidden name="ddtismaintable" id="ddtismaintable"></s:hidden>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
			<tr>
				<td  align="right">
					<a class="mini-button" onclick="myreset()" plain="true" style="width:40px">刷新</a>
					<a class="mini-button" onclick="dosieve()" plain="true" style="width:40px">筛选</a>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table id="tableList" cellspacing="0"
						cellpadding="4" rules="all" border="1" id="Datagrid1"
						style="width: 100%; border-collapse: collapse;">
						<tr align="center">
							<td height="10"><input type="checkbox" id="checkall" name="checkall" onclick="doCheckAll(this)"/></td>
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
							<td height="10">
								主表
							</td>
							<td height="10">
								数据关系
							</td>
							<td height="10">
								额外查询关系
							</td>
							<td height="10">
								排序
							</td>
						</tr>
						<s:set name="index"  id="index" value="0"/>
						<s:iterator id="tables" value="#request.tablesList" status="st">
							<tr onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
								onmouseout="this.style.backgroundColor=e">
								<td>
									<input type="checkbox" name="checktable" id="checktable" value="<s:property value="tableName"/>"/>
								</td>
								<td>
									<input type="hidden" name="tableNamearr" id="tableNamearr" value="<s:property value='#tables.tableName' />"/>
									<a href="#" onclick="showColumns(this,'<s:property value="#tables.tableName" />','<s:property value="#index"/>')"><s:property value="#tables.tableName" /></a>
								</td>
								<td align="center">
									<input type="text" name="descritionarr" id="descritionarr" size="15" value="<s:property value="#tables.tableRemarks" />"/> 
								</td>
								<td align="center">
									
									<input type="text" name="totablenamearr" id="totablenamearr" size="25" value="<s:property value='#tables.tableName' />"/> 
								</td>
								<td align="center">
									<input type="text" name="todescritionarr" id="todescritionarr" size="15" value="<s:property value='#tables.tableRemarks' />"/> 
								</td>
								<td>
									<input type="radio" name="isMainTable" value="<s:property value="tableName"/>" id="isMainTable<s:property value='#index'/>" onclick="mainTable(this);" />
								</td>
								<td align="center">
									<input type="text" name="shujuguanxiarr" id="shujuguanxi<s:property value='#index'/>" size="10"/>
								</td>
								<td>
									<input type="text" name="addconditionarr" id="addcondition<s:property value='#index'/>" size="15"/>
								</td>
								<td>
									<s:textfield size="2" name="ddtorderarr" id="ddtorderarr"></s:textfield>
									<input type="hidden" name="joinedfieldsarr"  id="joinedfieldsarr<s:property value='#index' />" value=""/>
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
