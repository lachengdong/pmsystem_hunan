<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>数据库管理exportDatabaseXML.jsp</title>
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
		 //将下拉框中的值显示到文本框中
			function showDatasName(select){
				var exportSelect=document.getElementById("exportSelect").value;
				if(exportSelect!='-1'){
				    
					//document.getElementById("dentalsub").src = "queryTableInfo.action?ddcid="+exportSelect;
					document.getConnectionMessageList.action="queryTableInfo.action?ddcid="+exportSelect;
		 			document.getConnectionMessageList.submit();
				}
			}
			function download(){
			  window.location.href = "queryTableInfoList.action";
			}
			
		</script>
	</head>
	<body >
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
				<table width="100%" border-bottom=0px; cellspacing="0" cellpadding="0" class="mini-toolbar">
					<tr> 
						<td height="10" align="center">
							选择数据方案:
                          		<select id="exportSelect" onchange="showDatasName(this)">
                          			<option value="-1">请选择</option>
                          			<s:iterator value="#datascheme">
                          				<option value="<s:property value="ddcid"/>"><s:property value="ddcname"/></option>
                          			</s:iterator>
                          		</select>
                          		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="mini-button" onclick="download()" plain="true" style="width:80px">下载列表</a>
						</td>
					</tr>
					<!-- 
					<tr >
						<td colspan="2" width="100%">
					   		<iframe id="dentalsub" src="" frameborder="0" scrolling="no" onload="this.height=dentalsub.document.body.scrollHeight" 
								width="100%" height="100%">
							</iframe> 
					    </td>
					</tr>
					 -->
				</table>
			</s:form>
			
	</body>
</html>