<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>代码类型管理</title>
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
		   		        	document.getCodeManagesList.action="delCodeMessageList.action";
		   		        	document.getCodeManagesList.submit();
		   				}   
		   	        }
		   	        else
		   	        {
		   	          alert("请选择一条或多条信息进行删除");
		   	        }
		   		} 
		   }
		    function delcodeType(urlstring){
		    	if(window.confirm("此操作将从数据库中删除，不能恢复！是否继续执行?")){
		        	//alert(urlstring);
			        	document.getCodeManagesList.action=urlstring;
			        	document.getCodeManagesList.submit();
					} 
		        }
			function dosubmit(v){
				document.getCodeManagesList.action=v;
				document.getCodeManagesList.submit();
			}
			function subForm(v){
				document.getCodeManagesList.action=v;
				document.getCodeManagesList.submit();
			}
			function setValue(v1,v2){
				document.getElementById("trd").style.display='block';
				document.getElementById("codetypeid").value=v1;
				document.getElementById("codetypename1").value=v2;
			}
			function doadd(){
				document.getElementById("trd").style.display='block';
			}
			function goback(){
				document.getElementById("trd").style.display='none';
			}
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
		</script>
	</head>
	<body >
		<s:form id="getCodeManagesList" name="getCodeManagesList"  theme="simple">
			<table width="100%" border="0" class="mini-toolbar"  cellspacing="0" cellpadding="0">
				<tr>
					<td width="80%" align="right">
						代码类型描述:
						<input type="text" id="codename" name="codename" />
						<a class="mini-button" onclick="dosubmit('getCodeManagesList.action');" plain="true" style="width:60px">查询</a>
						<a class="mini-button" onclick="doadd()" plain="true" style="width:60px">增加</a>
						<a class="mini-button" onclick="deleteMult()" plain="true" style="width:80px">批量删除</a>
					</td>
				</tr>
				<tr id="trd" style="display: none">
					<td  height="40" align="right" style="padding-right: 85px;">
						代码类型描述:
						<s:textfield id="codetypename1" name="codetypename" maxlength="50"></s:textfield>
						<input type="hidden" id="codetypeid" name="codetypeid" value=""/>
						<a class="mini-button" onclick="subForm('insertCodeMessage.action');" plain="true" style="width:60px">保存</a>
						<a class="mini-button" onclick="goback()" plain="true" style="width:60px">返回</a>
					</td>
				</tr>
				<tr>
					<td >
						<table  cellspacing="0" cellpadding="4"
										rules="all" border="1" id="Datagrid1"
										style="width: 100%; border-collapse: collapse;">
							<tr align="center">
								<td height="10">
									<input type="checkbox" id="gvList_ctl01_chkAll"
										name="checkall"
										onclick="javascript:SelectAllCheckboxes(this);"/>
								</td>
								<td height="10">
									代码类型编号
								</td>
								<td height="10">
									代码类型描述
								</td>
								<td height="10">
									操作
								</td>
							</tr>
							<s:iterator id="codeList" value="#request.codeList"
								status="st">
								<tr <s:if test="#st.Even"> class="GridViewRowStyle" </s:if><s:else> class="GridViewAlternatingRowStyle" </s:else>onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onmouseout="this.style.backgroundColor=e">
									<td>
										<center>
											<input type="checkbox" id="connArr" name="connArr"
												value='<s:property  value="#codeList.codetypeid" />' />
										</center>
									</td>
									<td align="center">
										<a style="cursor: hand" href="getCodechemeList.action?codetype=<s:property value="#codeList.codetypeid" />"><s:property value="#codeList.codetypeid" /></a>
									</td>
									<td align="center">
										<a style="cursor: hand" href="getCodechemeList.action?codetype=<s:property value="#codeList.codetypeid" />"><s:property value="#codeList.codetypename" /></a>
									</td>
									<td align="center">
										<a class="mini-button" onclick="setValue('<s:property value="#codeList.codetypeid" />','<s:property value="#codeList.codetypename" />')" plain="true" style="width:60px">编辑</a>
										<a class="mini-button" onclick="delcodeType('delCodeMessage.action?codetypeid=<s:property value="#codeList.codetypeid" />')" plain="true" style="width:60px">删除</a>
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
							frmName="getCodeManagesList" actionName="getCodeManagesList.action" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>