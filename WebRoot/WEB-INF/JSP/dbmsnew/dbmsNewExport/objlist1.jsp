<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>

<%
	String path = request.getContextPath();
%>
<%
List<String> coulm=null;
List<Object[]> value=null;
String docPath=null;
String tableName=null;
String docName=null;
List<String> coumlusComm=null;
if(null!=request.getAttribute("coulmList")){
	coulm=(List<String>)request.getAttribute("coulmList");
}
if(null!=request.getAttribute("coumlusComm")){
	coumlusComm=(List<String>)request.getAttribute("coumlusComm");
}
if(null!=request.getAttribute("valueList")){
	value=(List<Object[]>)request.getAttribute("valueList");
}
if(null!=request.getAttribute("path")){
	docPath=request.getAttribute("path").toString();
}
if(null!=request.getAttribute("tableName")){
	tableName=request.getAttribute("tableName").toString();
}
if(null!=request.getAttribute("docName")){
	docName=request.getAttribute("docName").toString();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库管理objlist1.jsp</title>
		<base target="_self">
		<link rel="StyleSheet"
			href="<s:property value='#session.path'/>/css/dtree.css"
			type="text/css" />
		<link rel="stylesheet" href="<%=path%>/css/tree.css"/>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/ajax.js"></script>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/all.js"></script>
		<script language="JavaScript" src="<%=path%>/js/calendarutf-8.js"></script>
		
		<script type='text/javascript' src='<s:property value='#session.path'/>/dwr/engine.js'></script>
		<script type='text/javascript' src='<s:property value='#session.path'/>/dwr/util.js'></script>
		<script type='text/javascript' src='<s:property value='#session.path'/>/dwr/interface/AjaxExportService.js'></script>
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
		
		<link href="<%=path%>/css/CIC.css" rel="stylesheet" type="text/css">
		<SCRIPT language="JavaScript" src="<%=path%>/js/bgColor.js"></SCRipt>
		<!-- 进度条样式 -->
		<script type="text/javascript" src="<%=path%>/js/prototype.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jsProgressBarHandler.js"></script>
	</head>
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

   function exportAllXMLinfo(){
	   var dates=new Date();
		alert("beginTime = "+dates);
	    document.getElementById("exportButton").style.display="none";
		var boxs=document.getElementsByName("box");
		
		dwr.engine.setAsync(false);
		//列名
		var row=document.getElementsByName("com");
		//所有的值
		var price=document.getElementsByName("info");
		//列名
		var rows=new Array(row.length);
		//集合值
		var prices=new Array(boxs.length);//所有的值

		for(var i=0;i<row.length;i++){//循环所有的列
			rows[i]=row[i].value;
		}	
		
		for(var i=0;i<boxs.length;i++){//循环所有的行
			var pri=new Array(rows.length);//一行值
			for(var j=0;j<rows.length;j++){//循环所有的列
				pri[j]=price[];
			}
		}
		
		var docPath=document.getElementById("docPath").value;
		var docName=document.getElementById("docName").value;
		window.location.href="compressDocXML.action?docPath="+docPath+"&docName="+docName;
	}
	function compress(){
		var dates=new Date();
		alert("beginTime = "+dates);
		document.getElementById("exportButton").style.display="none";
	    var boxs=document.getElementsByName("box");
		for(var i=0;i<boxs.length;i++){
			if(boxs[i].checked){
				exportXMLinfo(boxs[i]);
			}
		}
		var docPath=document.getElementById("docPath").value;
		var docName=document.getElementById("docName").value;
		window.location.href="compressDocXML.action?docPath="+docPath+"&docName="+docName;
		document.getElementById("exportSuccess").style.display="block";
	    document.getElementById("Info").style.display="none";
	    document.getElementById("Success").style.display="block";
	    document.getElementById("ValueInfo").style.display="none";
	}
function exportXMLinfo(box){
	dwr.engine.setAsync(false);
	//列名
	var row=document.getElementsByName("com");
	//值
	var price=document.getElementsByName(box.id+"info");
	var docPath=document.getElementById("docPath").value;
	//列名
	var rows=new Array(row.length);
	//值
	var prices=new Array(price.length);
	var docPath=document.getElementById("docPath").value;
	var tableName=document.getElementById("tableName").value;
	var docName=document.getElementById("docName").value;
	for(var i=0;i<row.length;i++){
		rows[i]=row[i].value;
		prices[i]=price[i].value;
	}
	if(box.checked){
		AjaxExportService.ajaxExportInformation(rows,prices,docPath,docName,tableName,function backsave(data){
			if(!data){
				alert("信息导出失败!");
			}
		});
	}else{
		var boxs=document.getElementsByName("box");
		var number=0;
		for(var i=0;i<boxs.length;i++){
			if(boxs[i].checked){
				number++;
			}
		}
		AjaxExportService.ajaxDeleteExportInformation(rows,prices,docPath,docName,tableName,number,function backsave(data){
			if(!data){
				alert("信息删除失败!");
			}
		});
	}
}
function selectTheAllCheckBox(box){
	 var boxs=document.getElementsByName("box");
	 for(var i=0;i<boxs.length;i++){
		boxs[i].checked=box.checked;	
	}
}

//设置进度条方法
function setPercentage(len)
{
	myJsProgressBarHandler.setPercentage('element1',len);
	return false;
}
</script>
	<body leftMargin="1" topMargin="0">
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table id="Table2" Style="background:#FFFFFF" cellspacing="0" cellpadding="0" width="95%">
		            			<tr>
		            			<td>
		            			<div id="divbigtext" style="color:red;display:none">
		            				<span class="progressBar" id="element1">0%</span>
		            			</div>
		            			</td>
		            			</tr>
            				</table>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="right" >
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD height="25">
													<div id="NavPan">
														<table cellpadding="0" cellspacing="0" border="0">
															<tr>
																<td align="center" valign="middle" class="SelNavTabBG" >
																	<a href="getDatasChemeNew.action" class="NavLink"
																		target="_self">数据导出</a>
																</td>
																<td align="center" valign="middle" class="NavTabBG">
																	<a href="admissionImport.action" class="NavLink"
																		target="_self">数据导入</a>
																</td>
																<td align="center" valign="middle" class="NavTabBG">
																	<a href="getDataInterchangeChemeInfo.action" class="NavLink"
																		target="_self">数据交换</a>
																</td>
															</tr>
														</table>
													</div>
												</TD>
											</TR>
											<TR>
												<td class="TableHeader" height="30" width="20%" id="">
													&nbsp;<div id="Info">选择导出数据</div><div id="exportSuccess" style="display:none;">导出成功</div>
													<input type="hidden" value="<%=docPath%>" id="docPath"/>
													<input type="hidden" value="<%=tableName%>" id="tableName"/>
													<input type="hidden" value="<%=docName%>" id="docName"/>
													<input type="hidden" value="" id="selectBox"/>
												</td>
												<td class="TableHeader" width="80%" align="right">
													<div id="exportButton">
														<input onclick="javascript:history.go(-1);" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="上一步" />
												    	<input onclick="compress();" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="导出选中数据" />
												    	<input onclick="exportAllXMLinfo();" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="全部导出" />
												    </div>
												</td>
											</TR>
											<tr>
												<td colspan="2">
													<TABLE id="Table1" cellSpacing="1" cellPadding="4"
														width="100%" class="TableBackGround">
														<TR>
															<td class="TableBody" colSpan="2" height="40" align="center">
															<div id="ValueInfo">
																<table class="DataGrid" cellspacing="0" cellpadding="4" rules="all" border="1" id="Datagrid1" style="width: 100%; border-collapse: collapse;">
																	<tr class="DataGridHead" align="center">
																		<td>
																			<input type="checkbox" name="checked" onclick="selectTheAllCheckBox(this);"/>
																		</td>
																		<%if(coulm!=null&&null!=coumlusComm){for(int i=0;i<coulm.size();i++){%>
																		<td>
																			<input type="hidden" name="com" value="<%=coulm.get(i)%>"/>
																			<%=coumlusComm.get(i)%>
																		</td>
																		<%}}%>
																	</tr>
																	<%if(value!=null){for(int i=0;i<value.size();i++){%>
																		<tr>
																			<td  align="center">
																				<input type="checkbox" id="<%=i%>" name="box"/>
																			</td>
																		<%Object[] obj=value.get(i);for(int j=0;j<obj.length;j++){%>
																			<td align="center">
																				<input type="hidden" name="info" value="<%=obj[j]%>"/>
																				<%=obj[j]%>
																			</td>
																		<%}%>
																		</tr>
																	<%}}%>
																</table>
															</div>
															<div id="Success" style="display: none;">
																<b>导出成功！！</b>
															</div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td align="right" valign="bottom">
							<page:page name="pageController" title="" unit="条"
								frmName="getConnectionMessageList" actionName="getConnectionMessageList.action" />
						</td>
					</tr>
				</table>
			</s:form>
	</body>
</HTML>
