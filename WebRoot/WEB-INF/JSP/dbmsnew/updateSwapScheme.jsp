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
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>数据库方案updateSwapScheme.jsp</title>


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
	function showColumns(op,fromtableid,totableid)
	{
		
		var rowindex=op.parentNode.parentNode.rowIndex;
		var totablenames=document.getElementsByName("totablename");
		var fromtablenamelist=document.getElementsByName("fromtablename");
		var toablediscribe=document.getElementsByName("toablediscribe");
		var fromtableidlist=document.getElementsByName("fromtableid");
		var totable=totablenames[rowindex-1].value;
		var tableRemarks=toablediscribe[rowindex-1].value;
		var fromtableid=fromtableidlist[rowindex-1].value;
		var fromtablename=fromtablenamelist[rowindex-1].value;
		var columnsList = window.showModalDialog("queryUpdateSwapTableColumns.action?totablename="+totable+"&fromtablename="+fromtablename+"&fromdatabaseid="+data.fromdatabaseid+"&todatabaseid="+data.todatabaseid+"&ddcid="+data.ddcid+"&fromtablenameid="+fromtableid+"&tr="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogWidth: "+(window.screen.availWidth   )   + "px;DialogHeight= "+(window.screen.availHeight   )   + "px;");
		if(tableList.length<1)
		{
			var tableop=document.getElementById("tableList");
			for(var i=0;i<tableop.rows.length-1;i++)
			{
				var tables=[];
				tables.columnsList="";
				tableList.push(tables);
			}
		}
		tableList[rowindex-1].columnsList=columnsList;
	}
	function dosubmit()
	{
		var retvalue="";

		var totablenameslist=document.getElementsByName("totablename");
		var toablediscribelist=document.getElementsByName("toablediscribe");
		var fromtableidlist=document.getElementsByName("fromtableid");
		var fromtablediscribelist=document.getElementsByName("fromtablediscribe");
		var ismaintablelist=document.getElementsByName("ismaintable");
		var issievelist=document.getElementsByName("issieve");
		var tableorderlist=document.getElementsByName("tableorder");
		var shujuguanxilist=document.getElementsByName("shujuguanxi");
		var addconditionlist=document.getElementsByName("addcondition");
		var joinedFieldslist=document.getElementsByName("joinedFields");

		var tableop=document.getElementById("tableList");
		for(var i=0;i<tableop.rows.length-1;i++)
		{
				var fromtableid=" ";
				var fromtablediscribe=" ";
				var totablenames=" ";
				var todescritions=" ";
				var ismaintable=" ";
				var issieve=" ";
				var tableorder=" ";
				var shujuguanxi=" ";
				var addcondition=" ";
				var joinedFields=" ";

				if(undefined!=fromtableidlist[i].value  && ""!=fromtableidlist[i].value)
				    fromtableid=fromtableidlist[i].value;
				if(undefined!=fromtablediscribelist[i].value  && ""!=fromtablediscribelist[i].value)
					fromtablediscribe=fromtablediscribelist[i].value;
				if(undefined!=totablenameslist[i].value  && ""!=totablenameslist[i].value)
				    totablenames=totablenameslist[i].value;
				if(undefined!=toablediscribelist[i].value  && ""!=toablediscribelist[i].value)
				    todescritions=toablediscribelist[i].value;
				if(undefined!=ismaintablelist[i]  && ismaintablelist[i].checked)
				    ismaintable="0";
				else
					ismaintable="1";
				if(undefined!=issievelist[i] && issievelist[i].checked)
					issieve="1";
				else
					issieve="0";
				if(undefined!=tableorderlist[i].value  && ""!=tableorderlist[i].value)
				    tableorder=tableorderlist[i].value;
				if(undefined!=shujuguanxilist[i].value  && ""!=shujuguanxilist[i].value)
				    shujuguanxi=shujuguanxilist[i].value;
				if(undefined!=addconditionlist[i].value  && ""!=addconditionlist[i].value)
				    addcondition=addconditionlist[i].value;
				if(undefined!=joinedFieldslist[i].value  && ""!=joinedFieldslist[i].value)
				    joinedFields=joinedFieldslist[i].value;

				retvalue+=fromtableid+"::"+fromtablediscribe+"::"+ismaintable+"::"+issieve+"::"+tableorder+"::"+joinedFields+"::"+totablenames+"::"+todescritions+"::"+shujuguanxi+"::"+addcondition+"::";
				if(undefined!=tableList && undefined!=tableList[i] && undefined!=tableList[i].columnsList)
				{
					retvalue+=tableList[i].columnsList;
				}
				retvalue+="]]";
	    }
	    if(retvalue.length>0)
	    {
	    	retvalue=retvalue.substring(0,retvalue.length-2);
	    }
	    window.parent.window.dosubmit("updateDatabaseSwapScheme.action",retvalue);
	}
	function addtables()
	{
		var tableList=document.getElementsByName("fromtableid");
		var tablearr="";
		for(var i=0;i<tableList.length;i++)
		{
			tablearr+="'"+tableList[i].value+"',";
		}
		if(tablearr.length>0)
		{
			tablearr=tablearr.substring(0,tablearr.length-1);
		}
		var url="showUpdateImpTables.action?todatabaseid="+data.todatabaseid+"&ddcid="+data.ddcid+"&expscheme="+data.expscheme+"&tablearr="+tablearr+"&tr="+new Date().getTime();
		window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:1000px");
	}
	//复选框全选方法
	var sievebool=false;
	function dosievecheckall(op)
	{
		sievebool=!sievebool;
		var checkList=document.getElementsByName("issieve");
		for(var i=0;i<checkList.length;i++)
		{
			checkList[i].checked=sievebool;
	    }
	}
	function doback()
	{
		window.parent.window.dosubmit("showSchemeList.action");
	}
	//添加关联字段
	function selectFields(op,tableName){
		debugger;
		var joinedFieldslist =document.getElementsByName("joinedFields");
		var joinFieldstr="";
		var rowindex=op.parentNode.parentNode.rowIndex;
		var fromdatabaseid=data.fromdatabaseid;
		var table1={};
		table1 = window.showModalDialog("getDBTableColumns.action?fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName,"zfadgasg","dialogWidth=1000px;dialogHeight=600px;resizable=yes");
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
  </script>
	</head>


	<body>
	<s:form  id="tables" name="tables"  theme="simple">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="TableBackGround">

			<tr>

				<td class="TableHeader" align="right">
					<input type="button" class="button" value="上一步"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="upstep();">
					&nbsp;
<%--					<input type="button" class="button" value="添加表"--%>
<%--						onmouseover="onButton(this);" onmouseout="offButton(this);"--%>
<%--						onclick="addtables();">--%>
					&nbsp;
					<input type="button" class="button" value="完成"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="dosubmit();">
					&nbsp;
					<input type="button" class="button" value="取消"
						onmouseover="onButton(this);" onmouseout="offButton(this);"
						onclick="doback()">
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
							<td height="10">
								主表
							</td>
							<td height="10">
								<input type="checkbox" id="sievecheckall" name="checkall" onclick="dosievecheckall(this)"/>筛选
							</td>
							<td height="10">
								排序
							</td>
							<td height="10">
								数据关系
							</td>
							<td height="10">
								额外查询关系
							</td>
							<!-- 
							<td height="10">
								关联字段
							</td>-->
						</tr>
						<s:iterator id="tables" value="#request.tablesList" status="st">
							<tr <s:if test="#st.Even"> class="GridViewRowStyle" </s:if>
								<s:else> class="GridViewAlternatingRowStyle" </s:else>
								onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
								onmouseout="this.style.backgroundColor=e">
								<td>
									<input type="hidden" name="fromtableid" value="<s:property value='#tables.fromtableid' />"/>
									<input type="hidden" name="fromtablename" value="<s:property value='#tables.fromtablename' />"/>
									<input type="hidden" name="totableid" value="<s:property value='#tables.totableid' />"/>
									<a href="#" onclick="showColumns(this,'<s:property value='#tables.fromtableid' />','<s:property value='#tables.totableid' />')"><s:property value="#tables.fromtablename" /></a>
								</td>
								<td align="center">
									<input type="text" name="fromtablediscribe" size="15" value="<s:property value='#tables.fromtablediscribe' />"/> 
								</td>
								<td>
									
									<input type="text" name="totablename" size="25" value="<s:property value='#tables.totablename' />"/> 
								</td>
								<td align="center">
									<input type="text" name="toablediscribe" size="15" value="<s:property value='#tables.toablediscribe' />"/> 
								</td>
								<td>
									<s:if test="#tables.isMainTable==0">
										<input type="radio" name="ismaintable" checked="checked"/>
									</s:if>
									<s:else>
										<input type="radio" name="ismaintable"/>
									</s:else>
								</td>
								<td>
									<s:if test="#tables.isscreen==1">
										<input type="checkbox" name="issieve" checked="checked"/>
									</s:if>
									<s:else>
										<input type="checkbox" name="issieve"/>
									</s:else>
								</td>
								<td>
									<input type="text" name="tableorder" size="2" value="<s:property value='#tables.tableorder' />"/> 
								</td>
								<td>
									<input type="text" name="shujuguanxi" size="25" value="<s:property value='#tables.shujuguanxi' />"/> 
								</td>
								<td>
									<input type="text" name="addcondition" size="25" value="<s:property value='#tables.addcondition' />"/> 
								</td>
								<!-- <td> -->
									<input type="hidden" name="joinedFields" size="15" value="<s:property value='#tables.joinedFields' />"/> &nbsp;
										<!-- <input type="button" class="button" value="选择"
										onmouseover="onButton(this);" onmouseout="offButton(this);"
										onclick="selectFields(this,'<s:property value='#tables.fromtablename' />')"> -->
								<!-- </td> -->
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
