<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>导出数据显示列表datadaochulist.jsp</title>
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
				        	document.getConnectionMessageList.action="deleteConnectionMessageList.action";
				        	document.getConnectionMessageList.submit();
						}   
			        }
			        else
			        {
			          alert("请选择一条或多条信息进行删除");
			        }
				} 
			}
			function showdetail(url){
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
			
			function goback(url){
			   window.location.href = url;
			}
			
		</script>
	</head>
	<body>
	<s:if test="#request.imporexp==import">
		<a class="mini-button"  onclick="goback('getDatasChemeNew.action?1=1');" style="width:60px; float: right">返回</a>
	</s:if>
	<s:else>
	    <a class="mini-button"  onclick="goback('admissionImport.action?1=1');" style="width:60px; float: right">返回</a>
	</s:else>
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
			 
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
					<tr>
						<td class="mini-toolbar"  height="40" align="center" colspan="2">
							<table id="Datagrid1"	style="width: 100%; border-collapse: collapse;" border="1"   cellspacing="0" cellpadding="0">
								<tr  align="center">
									<td height="10">
										<input type="checkbox" id="gvList_ctl01_chkAll"
											name="checkall"
											onclick="javascript:SelectAllCheckboxes(this);"/>
									</td>
									<td height="10" style="display: none">
										编号
									</td>
									<td height="10">
										数据方案
									</td>
									<td height="10">
										文件名称
									</td>
									<td height="10" style="display: none">
										文件路径
									</td>
									<td height="10">
										创建日期
									</td>
									<td height="10" style="display: none">
										导出数据方案ID
									</td>
									<td height="10">
										操作
									</td>
								</tr>
								<s:iterator id="dataList" value="#request.dataList" status="st">
									<tr  onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"  onmouseout="this.style.backgroundColor=e">
										<td>
											<center>
												<input type="checkbox" name="connArr"
													value='<s:property  value="" />' />
											</center>
										</td>
										<td align="center" style="display: none">
										<s:property  value="#dataList.fileid" />
										</td>
										<td align="center">
											<s:property  value="#dataList.filename" />
										</td>
										<td align="center">
											<s:property value="#dataList.filerealname" />
										</td>
										<td align="center" style="display: none">
											<s:property value="#dataList.filepath" />
										</td>
										<td align="center">
											<s:date name="createdate" format="yyyy-MM-dd HH:mm" />
										</td>
										<td align="center"  style="display: none">
											<s:property value="#dataList.schemeid" />
										</td>			
										<td>
											<center>
													<a class="mini-button" onclick="showdetail('getDataInfo.action?fileid=<s:property value="#dataList.fileid" />')" plain="true" style="width:60px">下载</a>  
											        <a class="mini-button" onclick="shifoushanchu('deleteDataInfo.action?fileid=<s:property  value="#dataList.fileid" />')" plain="true" style="width:60px">删除</a>  
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
								frmName="getConnectionMessageList" actionName="queryTableInfoList.action" />
						</td>
					</tr>
				</table>
			</s:form>
	</body>
</HTML>