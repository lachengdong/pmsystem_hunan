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
		<title>数据库方案updateImpSchemeColumns.jsp</title>


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
	function dochangecolumns(op)
	{
		var rowindex=op.parentNode.parentNode.rowIndex;
		var fromcolumnsorg=document.getElementsByName("fromcolumnsorg");
		var fromcolumns=document.getElementsByName("fromcolumns");
		var fromcolumnsscribe=document.getElementsByName("fromcolumnsscribe");
		var fromcloumnstype=document.getElementsByName("fromcloumnstype");
		var fromcolumnssize=document.getElementsByName("fromcolumnssize");
		var fromcolumnspoint=document.getElementsByName("fromcolumnspoint");
		var columnsValue=fromcolumnsorg[rowindex-2].value;
		if("-1"!=columnsValue)
		{
			var columnsData=eval('({'+columnsValue+'})');
			if(undefined != columnsData.fromcolumns && "null"!=columnsData.fromcolumns)
				fromcolumns[rowindex-2].value=columnsData.fromcolumns;				
			if(undefined != columnsData.fromcolumnsscribe && "null"!=columnsData.fromcolumnsscribe)
				fromcolumnsscribe[rowindex-2].value=columnsData.fromcolumnsscribe;
			if(undefined != columnsData.fromcloumnstype && "null"!=columnsData.fromcloumnstype)
				fromcloumnstype[rowindex-2].value=columnsData.fromcloumnstype;
			if(undefined != columnsData.fromcloumnssize && "null"!=columnsData.fromcloumnssize)
				fromcolumnssize[rowindex-2].value=columnsData.fromcloumnssize;
			if(undefined != columnsData.fromcolumnspoint && "null"!=columnsData.fromcolumnspoint)
				fromcolumnspoint[rowindex-2].value=columnsData.fromcolumnspoint;
		}else{
			fromcolumns[rowindex-2].value="";
			fromcolumnsscribe[rowindex-2].value="";
			fromcloumnstype[rowindex-2].value="";
			fromcolumnssize[rowindex-2].value="";
			fromcolumnspoint[rowindex-2].value="";
		}
	}
	function dosubmit()
	{
		var fromcolumnslist=document.getElementsByName("fromcolumns");
		var fromcolumnsscribelist=document.getElementsByName("fromcolumnsscribe");
		var fromcloumnstypelist=document.getElementsByName("fromcloumnstype");
		var fromcolumnssizelist=document.getElementsByName("fromcolumnssize");
		var fromcolumnspointlist=document.getElementsByName("fromcolumnspoint");
		var tocolumnslist=document.getElementsByName("tocolumns");
		var tocolumnsscribelist=document.getElementsByName("tocolumnsscribe");
		var tocolumnstypelist=document.getElementsByName("tocolumnstype");
		var tocolumnssizelist=document.getElementsByName("tocolumnssize");
		var tocloumnsdefaultvaluelist=document.getElementsByName("tocloumnsdefaultvalue");
		var tocolumnspointlist=document.getElementsByName("tocolumnspoint");
		var codetypelist=document.getElementsByName("codetype");
		var pkgeneratorlist=document.getElementsByName("pkgenerator");
		var ispklist=document.getElementsByName("ispk");

		var jsonarray="";
		var op=document.getElementById("columnsList");
	    for(var i=0;i<op.rows.length-2;i++)
	    {
				
			    var fromcolumns=" ";
			    var fromcolumnsscribe=" ";
			    var fromcloumnstype=" ";
				var fromcloumnssize=" ";
				var fromcloumnsdefaultvalue=" ";
				var fromcolumnspoint=" ";
				var tocolumnsname=" ";
				var tocolumnsscribe=" ";
				var tocolumnstype=" ";
				var tocolumnssize=" ";
				var tocloumnsdefaultvalue=" ";
				var tocolumnspoint=" ";
				var ispk=" ";
				var pkgenerator=" ";
				var codetype=" ";
				var totablename=" ";
				var tableRemarks=" ";
				var ddcid=" ";

				//if("--请选择--"!=fromcolumnslist[i].options[fromcolumnslist[i].selectedIndex].text)
				//	fromcolumns=fromcolumnslist[i].options[fromcolumnslist[i].selectedIndex].text;
				if(""!=fromcolumnslist[i].value && undefined!=fromcolumnslist[i].value)
				    fromcolumns=fromcolumnslist[i].value;
				if(""!=fromcolumnsscribelist[i].value && undefined!=fromcolumnsscribelist[i].value)
				    fromcolumnsscribe=fromcolumnsscribelist[i].value;
				if(""!=fromcloumnstypelist[i].value && undefined!=fromcloumnstypelist[i].value)
				    fromcloumnstype=fromcloumnstypelist[i].value;
				if(""!=fromcolumnssizelist[i].value && undefined!=fromcolumnssizelist[i].value)
					fromcloumnssize=fromcolumnssizelist[i].value;
				if(""!=fromcolumnspointlist[i].value && undefined!=fromcolumnspointlist[i].value)
					fromcolumnspoint=fromcolumnspointlist[i].value;
				if(""!=tocolumnslist[i].value && undefined!=tocolumnslist[i].value)
					tocolumnsname=tocolumnslist[i].value;
				if(""!=tocolumnsscribelist[i].value && undefined!=tocolumnsscribelist[i].value)
					tocolumnsscribe=tocolumnsscribelist[i].value;
				if(""!=tocolumnstypelist[i].value && undefined!=tocolumnstypelist[i].value)
					tocolumnstype=tocolumnstypelist[i].value;
				if(""!=tocolumnssizelist[i].value && undefined!=tocolumnssizelist[i].value)
					tocolumnssize=tocolumnssizelist[i].value;
				if(""!=tocloumnsdefaultvaluelist[i].value && undefined!=tocloumnsdefaultvaluelist[i].value)
					tocloumnsdefaultvalue=tocloumnsdefaultvaluelist[i].value;
				if(""!=tocolumnspointlist[i].value && undefined!=tocolumnspointlist[i].value)
					tocolumnspoint=tocolumnspointlist[i].value;
				if(""!=document.getElementById("totablename").value && undefined!=document.getElementById("totablename").value)
					totablename=document.getElementById("totablename").value;
				if(""!=document.getElementById("tableRemarks").value && undefined!=document.getElementById("tableRemarks").value)
					tableRemarks=document.getElementById("tableRemarks").value;
				if("-1"!=codetypelist[i].value)
			    {
			    	codetype=codetypelist[i].value;
				}
				if("-1"!=pkgeneratorlist[i].value)
				{
					pkgenerator=pkgeneratorlist[i].value;
				}
				if(ispklist[i].checked)
				{
					ispk="1";
				}
				
			    jsonarray+=fromcolumns+"##"+fromcolumnsscribe+"##"+fromcloumnstype+"##"+fromcloumnssize+"##"+fromcloumnsdefaultvalue+"##"+
			    fromcolumnspoint+"##"+totablename+"##"+tableRemarks+"##"+tocolumnsname+"##"+tocolumnsscribe+"##"+tocolumnstype+"##"+tocolumnssize+"##"+tocloumnsdefaultvalue+
			    "##"+tocolumnspoint+"##"+ispk+"##"+pkgenerator+"##"+codetype+"##%%"
		}
		if(jsonarray.length>0)
		{
			jsonarray=jsonarray.substring(0,jsonarray.length-2);
		}
		window.returnValue=jsonarray;
		window.close();
	}
	//复选框全选方法
	var bool=false;
	function doCheckAll()
	{
		bool=!bool;
		var checkList=document.getElementsByName("checktable");
		for(var i=0;i<checkList.length;i++)
		{
			checkList[i].checked=bool;
	    }
	}
	//表筛选方案
	function dosieve()
	{
		var columnslist = document.getElementById("columnsList");
		var checkList=document.getElementsByName("checktable");
		for(var i=checkList.length-1;i>=0;i--)
		{
			if(!checkList[i].checked)
			{
				columnslist.deleteRow(i+2); 
			}
	    }
	}
  </script>
	</head>


	<body>
		<s:form id="tables" name="table" theme="simple">
			<s:hidden name="ddcid"></s:hidden>
			<s:hidden name="fromtablenameid"></s:hidden>
<%--			<s:hidden name="jsonarray"></s:hidden>--%>
			<s:hidden name="totablename"></s:hidden>
			<s:hidden name ="tableRemarks"></s:hidden>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="TableBackGround">

				<tr>

					<td class="TableHeader" align="left">
<%--						<input type="button" class="button" value="添加列"--%>
<%--							onmouseover="onButton(this);" onmouseout="offButton(this);"--%>
<%--							onclick="dosubmit();">--%>
						&nbsp;
						<input type="button" class="button" value="筛选表"
							onmouseover="onButton(this);" onmouseout="offButton(this);"
							onclick="dosieve();" />
						&nbsp;
						<input type="button" class="button" value="确定"
							onmouseover="onButton(this);" onmouseout="offButton(this);"
							onclick="dosubmit();">
						&nbsp;
						<input type="button" class="button" value="取消"
							onmouseover="onButton(this);" onmouseout="offButton(this);"
							onclick="javaScript:window.close()";
>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="TableBody" colspan="3">
						<table id="columnsList" class="TableBody" cellspacing="0"
							cellpadding="4" rules="all" border="1" id="Datagrid1"
							style="width: 100%; border-collapse: collapse;">
							<tr class="DataGridHead" align="center">
								<td colspan="9">
									导入表：
									<label id="totable">
										<s:property value="#request.totablename" />
									</label>
									列信息
								</td>
								<td colspan="4">
									导出表：
									<label id="fromtable">
										<s:property value="#request.fromtablename" />
									</label>
									列信息
								</td>
							</tr>

							<tr class="DataGridHead" align="center">
								<td height="10"><input type="checkbox" id="checkall" name="checkall" onclick="doCheckAll(this)"/></td>
								<td height="10">
									列名
								</td>
								<td height="10">
									描述
								</td>
								<td height="10">
									类型
								</td>
								<td height="10">
									长度
								</td>
								<td height="10">
									默认值
								</td>
								<td height="10">
									小数位数
								</td>
								<td>代码类型</td>
								<td >主键</td>
								<td >主键策略</td>
								<td height="10">
									原列名
								</td>								
								<td height="10">
									列名
								</td>
								<td height="10">
									描述
								</td>
								<td height="10">
									类型
								</td>								
								<td height="10">
									长度
								</td>
								<td height="10">
									小数位数
								</td>
							</tr>
							<s:iterator id="toColumnsList" value="#request.toColumnsList"
								status="st">
								<tr <s:if test="#st.Even"> class="GridViewRowStyle" </s:if>
									<s:else> class="GridViewAlternatingRowStyle" </s:else>
									onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
									onmouseout="this.style.backgroundColor=e">
									<td>
										<input type="checkbox"  id="checktable" name="checktable"  value="<s:property value='#index' />"/>
									</td>
									<td align="center">
										<input type="hidden" name="tocolumns" value="<s:property value='#toColumnsList.dcdtocolumns' />"/>
										<s:property value='#toColumnsList.dcdtocolumns' />
									</td>
									<td align="center">
										<input type="text" name="tocolumnsscribe" size="10" value="<s:property value='#toColumnsList.dcdtocolumnsscribe'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocolumnstype" size="8" value="<s:property value='#toColumnsList.dcdtocolumnstype'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocolumnssize" size="2" value="<s:property value='#toColumnsList.dcdtocolumnssize'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocloumnsdefaultvalue" size="8"
											value="<s:property value='#toColumnsList.dcdtocloumnsdefaultvalue'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocolumnspoint" size="2" value="<s:property value='#toColumnsList.dcdtocolumnspoint'/>" />
									</td>
									<td>
										<select name="codetype">
											<option value="-1">--请选择--</option>
											<s:iterator id="codeTypeList" value="#request.codeTypeList">
												<s:if test="#toColumnsList.dcdcodetype==#codeTypeList.codetypeid">
													<option value="<s:property value='#codeTypeList.codetypeid' />" selected="selected"><s:property value='#codeTypeList.codetypename'/></option>
												</s:if>
												<s:else>
													<option value="<s:property value='#codeTypeList.codetypeid' />" ><s:property value='#codeTypeList.codetypename'/></option>
												</s:else>
											</s:iterator>
										</select>
									</td>
									<td>
										<s:if test="#toColumnsList.dcdifpkey==1">
												<input type="checkbox" name="ispk" checked="checked"/>
										</s:if>
										<s:else>
												<input type="checkbox" name="ispk"/>
										</s:else>
									</td>
									<td>
										<select name="pkgenerator">
											<option value="-1">--请选择--</option>
											<s:if test="#toColumnsList.dcdpkgenerator=='auto'">
												<option value="auto" selected="selected">自动生成</option>
											</s:if>
											<s:else>
												<option value="auto">自动生成</option>
											</s:else>
										</select>
									</td>
									<td align="center">
										<select name="fromcolumnsorg" onchange="dochangecolumns(this)">
										<option value="-1">--请选择--</option>
										<s:iterator id="columnslistselect" value="#request.fromColumnsList">
											<s:if test="#toColumnsList.dcdfromcolumns==#columnslistselect.dcdfromcolumns">
												<option value="<s:property value='#columnslistselect.jsoncolumns' />" selected="selected"><s:property value='#columnslistselect.dcdfromcolumns'/></option>
											</s:if>
											<s:else>
												<option value="<s:property value='#columnslistselect.jsoncolumns' />"><s:property value='#columnslistselect.dcdfromcolumns' /></option>
											</s:else>
										</s:iterator>
										</select>
									</td>
									<td align="center">
										<input type="text" name="fromcolumns" size="10" value="<s:property value='#toColumnsList.dcdfromcolumns' />" />
									</td>
									<td align="center">
										<input type="text" name="fromcolumnsscribe" size="10" value="<s:property value='#toColumnsList.dcdfromcolumnsscribe' />" />
									</td>
									<td align="center">
										<input type="text" name="fromcloumnstype" size="10" value="<s:property value='#toColumnsList.dcdfromcloumnstype' />" />
									</td>
									<td align="center">
										<input type="text" name="fromcolumnssize" size="2" value="<s:property value='#toColumnsList.dcdfromcloumnssize'/>" />
									</td>
									<td align="center">
										<input type="text" name="fromcolumnspoint" size="2" value="<s:property value='#toColumnsList.dcdfromcolumnspoint' />" />
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" class="TableBody" align="right"
						style="height: 26px">
						<img src="Images/gkzx_logo.jpg" width="390" height="40">
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
