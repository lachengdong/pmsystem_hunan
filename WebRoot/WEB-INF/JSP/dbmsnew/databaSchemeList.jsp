<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<html>
	<head>
		<title>方案管理</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	   <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
	   
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
		    function SelectAllCheckboxes(spanChk)
			{
				var xState=spanChk.checked;
				var theBox=spanChk;
				elm=theBox.form.elements;
				for(i=0;i<elm.length;i++)
				{
					if(elm[i].type=="checkbox" && elm[i].id!=theBox.id)
					{
						if(elm[i].checked!=xState)
						elm[i].click();
					}
				}
		    }
		    function dosubmit(){
			   window.location.href="addConnectionMessage.action";
			}
	 		function deleteMult(){
				var rad=document.getElementsByName("connArr");
				if(rad.length>0)
				{
					var flag="0"
				    var val="";
				    for(var i=0;i<rad.length;i++)
				    {
				    	if(rad[i].checked==true)
				    	{
				        	flag="1"
				            break;
				        }
					}
			        if(flag=="1")
			        {
				        if(window.confirm("此操作将从数据库中删除，不能恢复！是否继续执行?")){
				        	document.forms[0].action="deleteAllDataScheme.action";
				        	document.forms[0].submit();
						}   
			        }
			        else
			        {
			          alert("请选择一条或多条信息进行删除");
			        }
				} 
			}
	 		function generateImportInfo(){
				var rad=document.getElementsByName("connArr");
				if(rad.length>0)
				{
					var flag="0"
				    var val="";
				    var num = 0;
				    for(var i=0;i<rad.length;i++)
				    {
				    	if(rad[i].checked==true)
				    	{
				        	num++;
				        }
					}
			        if(num>0)
			        {
				        if(window.confirm("此操作将生成数据库导入信息！是否继续执行?")){
				        	document.forms[0].action="generateImportInfo.action";
				        	document.forms[0].submit();
						}   
			        }
			        else
			        {
			          alert("请至少选择一条导入方案进行操作");
			        }
				} 
			}
			 function tiaozhuan(url){
				document.getConnectionMessageList.action=url;
		 		document.getConnectionMessageList.submit();
			 }
			 function shifoushanchu(url){
				if(window.confirm("数据删除后无法恢复,确认删除?"))
				{
					document.getConnectionMessageList.action=url;
		 			document.getConnectionMessageList.submit();
				}
			 }
			 function findBySome(){
			    document.getConnectionMessageList.action="showSchemeList.action";
			 	document.getConnectionMessageList.submit();
			}
		</script>
	</head>
	<body >
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
					<tr>
						<td  height="30" >
							&nbsp;连接数据库列表 
						 方案类型：<s:select name="ddctype" id="ddctype"
							list="#request.schemeTypeCodeList"
							listKey="#request.id.scid"
							listValue="#request.sccontent" headerKey="-1"
							headerValue="--请选择--"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
						  方案名称：<s:textfield name="ddcname" id="ddcname" size="12"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="mini-button" onclick="findBySome();" plain="true" style="width:40px">查询</a>
							<a class="mini-button" onclick="javaScript:window.location.href='toAdddataScheme.action';" plain="true" style="width:40px">增加</a>
							<a class="mini-button" onclick="deleteMult();" plain="true" style="width:80px">批量删除</a>
							<a class="mini-button" onclick="generateImportInfo();" plain="true" style="width:160px">生成导入数据（主键除外）</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table  id="Datagrid1" border="1"
								style="width: 100%; border-collapse: collapse;">
								<tr  align="center" class="mini-toolbar" >
									<td height="10">
										<input type="checkbox" id="gvList_ctl01_chkAll"
											name="checkall"
											onclick="javascript:SelectAllCheckboxes(this);"/>
									</td>
									<td height="10">
										方案名称
									</td>
									<td height="10">
										方案类型
									</td>
									<td height="10">
										导出数据库
									</td>
									<td height="10">
										导入数据库
									</td>
									<td height="10">
										操作
									</td>
								</tr>
								<s:iterator id="scheme" value="#request.dataShemeList" status="st">
									<tr  onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"  onmouseout="this.style.backgroundColor=e">
										<td>
											<center>
												<input type="checkbox" name="connArr" id="connArr"
													value='<s:property  value="#scheme.ddcid" />' />
											</center>
										</td>
										<td align="center">
											<s:property
												value="#scheme.ddcname" />
										</td>
										<td align="center">
											<s:property
												value="#scheme.ddctype" />
										</td>
										<td align="center">
											<s:property
												value="#scheme.fromdatabaseid" />
										</td>
										<td align="center">
											<s:property
												value="#scheme.todatabaseid" />
										</td>
										<td>
											<center>
												<a class="mini-button" onclick="tiaozhuan('toUpdateDataScheme.action?ddcid=<s:property  value="#scheme.ddcid" />')" plain="true" style="width:60px">编辑</a>  
												<a class="mini-button" onclick="shifoushanchu('deleteDataScheme.action?ddcid=<s:property  value="#scheme.ddcid" />')" plain="true" style="width:60px">删除</a>  
											</center>
										</td>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td align="right" valign="bottom">
							<page:page name="pageController" title="" unit="条"
								frmName="getConnectionMessageList" actionName="showSchemeList.action" />
						</td>
					</tr>
				</table>
			</s:form>
	</body>
</HTML>