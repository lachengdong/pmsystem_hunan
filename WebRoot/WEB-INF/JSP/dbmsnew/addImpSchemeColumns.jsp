<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>添加导入方案列映射addImpSchemeColumns.jsp</title>
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
			function dochangecolumns(op)
			{
				var rowindex=op.parentNode.parentNode.rowIndex;
				var fromcolumnsorg=document.getElementsByName("fromcolumnsorg");
				var fromcolumns=document.getElementsByName("fromcolumns");
				var fromcolumnsscribe=document.getElementsByName("fromcolumnsscribe");
				var fromcolumnssize=document.getElementsByName("fromcolumnssize");
				var fromcolumnspoint=document.getElementsByName("fromcolumnspoint");
				var fromcloumnstype=document.getElementsByName("fromcloumnstype");
				var columnsValue=fromcolumnsorg[rowindex-2].value;
				if("-1"!=columnsValue)
				{
					var columnsData=eval('({'+columnsValue+'})');
					if(undefined != columnsData.fromcolumns && "null"!=columnsData.fromcolumns)
						fromcolumns[rowindex-2].value=columnsData.fromcolumns;	
					if(undefined != columnsData.fromcolumnsscribe && "null"!=columnsData.fromcolumnsscribe)
						fromcolumnsscribe[rowindex-2].value=columnsData.fromcolumnsscribe;
					if(undefined != columnsData.fromcloumnssize && "null"!=columnsData.fromcloumnssize)
						fromcolumnssize[rowindex-2].value=columnsData.fromcloumnssize;
					if(undefined != columnsData.fromcolumnspoint && "null"!=columnsData.fromcolumnspoint)
						fromcolumnspoint[rowindex-2].value=columnsData.fromcolumnspoint;
					if(undefined != columnsData.fromcloumnstype && "null"!=columnsData.fromcloumnstype)
						fromcloumnstype[rowindex-2].value=columnsData.fromcloumnstype;
				}else{
					fromcolumns[rowindex-2].value="";
					fromcolumnsscribe[rowindex-2].value="";
					fromcolumnssize[rowindex-2].value="";
					fromcolumnspoint[rowindex-2].value="";
					fromcloumnstype[rowindex-2].value="";
				}
			}
			function dosubmit()
			{
				var thelist=document.getElementsByName("checktable");
				var columslist=[];
				for(var cc=0;cc<thelist.length;cc++)
				{
					if(thelist[cc].checked){
						var columsdata={};
						//var fromcolumns=document.getElementById("fromcolumns"+thelist[cc].value);
						//if("--请选择--"==fromcolumns.options[fromcolumns.selectedIndex].text)
						//{
						//	columsdata.fromcolumns='';
						//}else{
						//	columsdata.fromcolumns=fromcolumns.options[fromcolumns.selectedIndex].text;
						//}
						var fromcolumnsorg=document.getElementById("fromcolumnsorg"+thelist[cc].value);
						columsdata.fromcolumns=document.getElementById("fromcolumns"+thelist[cc].value).value;
						columsdata.fromcolumnsscribe=document.getElementById("fromcolumnsscribe"+thelist[cc].value).value;
					    columsdata.fromcolumnssize=document.getElementById("fromcolumnssize"+thelist[cc].value).value;
					    columsdata.fromcolumnspoint=document.getElementById("fromcolumnspoint"+thelist[cc].value).value;
				    	columsdata.tocolumns=document.getElementById("tocolumns"+thelist[cc].value).value;
					    columsdata.tocolumnsscribe=document.getElementById("tocolumnsscribe"+thelist[cc].value).value;
					    columsdata.tocolumnstype=document.getElementById("tocolumnstype"+thelist[cc].value).value;
					    columsdata.tocolumnssize=document.getElementById("tocolumnssize"+thelist[cc].value).value;
					    columsdata.tocloumnsdefaultvalue=document.getElementById("tocloumnsdefaultvalue"+thelist[cc].value).value;
					    columsdata.tocolumnspoint=document.getElementById("tocolumnspoint"+thelist[cc].value).value;
					    columsdata.fromcloumnstype=document.getElementById("fromcloumnstype"+thelist[cc].value).value;
					    var codetypelist=document.getElementById("codetype"+thelist[cc].value);
					    if("-1"!=codetypelist.value)
					    {
					    	columsdata.codetype=codetypelist.value;					    	
						}
						var pkgeneratorlist=document.getElementById("pkgenerator"+thelist[cc].value);
						if("-1"!=pkgeneratorlist.value)
						{
							columsdata.pkgenerator=pkgeneratorlist.value;
						}
						var ispklist=document.getElementById("ispk"+thelist[cc].value);
						if(ispklist.checked)
						{
							columsdata.ispk="1";
						}
						columslist.push(columsdata);
					}
				}
			    window.returnValue=columslist;
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
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
				<tr>
					<td height="25%" align="left">
						<a class="mini-button" onclick="dosieve();" plain="true" style="width:40px">筛选</a>
						<a class="mini-button" onclick="dosubmit();" plain="true" style="width:40px">确定</a>
						<a class="mini-button" onclick="javaScript:window.close();" plain="true" style="width:40px">取消</a>
					</td>
				</tr>
				<tr>
					<td  height="75%" >
						<table id="columnsList" cellspacing="0"
							cellpadding="4" rules="all" border="1" id="Datagrid1"
							style="width: 100%; border-collapse: collapse;">
							<tr align="center">
								<td colspan="10">
									导入表：
									<label id="totable">
										<s:property value="#request.totablename" />
									</label>
									列信息
								</td>
								<td colspan="5">
									导出表：
									<label id="fromtable">
										<s:property value="#request.fromtablename" />
									</label>
									列信息
								</td>
							</tr>

							<tr  align="center">
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
								<td >代码类型</td>
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
							 <s:set name="index" id="index" value="0"/>
							<s:iterator id="toColumnsList" value="#request.toColumnsList"
								status="st">
								<s:set name="index" id="index" value="#index + 1"/>
								<tr	onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
									onmouseout="this.style.backgroundColor=e">
									<td>
										<input type="checkbox" name="checktable" id="checktable" value="<s:property value='#index' />"/>
									</td>
									<td align="center">
										<input type="hidden" name="tocolumns" id="tocolumns<s:property value='#index' />"  value="<s:property value='#toColumnsList.dcdtocolumns' />"/>
										<s:property value='#toColumnsList.dcdtocolumns' />
									</td>
									<td align="center">
										<input type="text" name="tocolumnsscribe" id="tocolumnsscribe<s:property value='#index' />" size="10" value="<s:property value='#toColumnsList.dcdtocolumnsscribe'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocolumnstype" id="tocolumnstype<s:property value='#index' />" size="8" value="<s:property value='#toColumnsList.dcdtocolumnstype'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocolumnssize" id="tocolumnssize<s:property value='#index' />" size="2" value="<s:property value='#toColumnsList.dcdtocolumnssize'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocloumnsdefaultvalue" id="tocloumnsdefaultvalue<s:property value='#index' />" size="8"
											value="<s:property value='#toColumnsList.dcdtocloumnsdefaultvalue'/>" />
									</td>
									<td align="center">
										<input type="text" name="tocolumnspoint" size="2" id="tocolumnspoint<s:property value='#index' />" value="<s:property value='#toColumnsList.dcdtocolumnspoint'/>" />
									</td>
									<td><s:select name="codetype" id="codetype%{#index}" list="#request.codeTypeList" headerKey="-1" headerValue="--请选择--" listKey="#request.codetypeid" listValue="#request.codetypename"/></td>
									<td>
										<input type="checkbox" name="ispk" id="ispk<s:property value='#index' />"/>
									</td>
									<td><s:select name="pkgenerator" id="pkgenerator%{#index}"  list="#{'auto':'自动生成'}" headerKey="-1" headerValue="--请选择--" listKey="key" listValue="value" /></td>
									<td align="center">
										<s:select name="fromcolumnsorg" id="fromcolumnsorg%{#index}" list="#request.fromColumnsList"
											headerKey="-1" headerValue="--请选择--"
											listKey="#request.jsoncolumns"
											listValue="#request.dcdfromcolumns"
											onchange="dochangecolumns(this)"></s:select>
									</td>
									<td align="center">
											<input type="text" name="fromcolumns" id="fromcolumns<s:property value='#index' />" size="15"/>
									</td>	
									<td align="center">
											<input type="text" id="fromcolumnsscribe<s:property value='#index' />" name="fromcolumnsscribe" size="15"/>
									</td>
									<td align="center">
											<input type="text" name="fromcloumnstype"  id="fromcloumnstype<s:property value='#index' />" size="15"/>
									</td>
									<td align="center">
										<input type="text" id="fromcolumnssize<s:property value='#index' />" name="fromcolumnssize" size="4"/>
									</td>
									<td align="center">
										<input type="text" id="fromcolumnspoint<s:property value='#index' />" name="fromcolumnspoint" size="4"/>
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
