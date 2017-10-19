<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>

<%
String path = request.getContextPath();
List<String> tableInfo =null;// 表信息 即 列名
List<String[]> tableFruit = null;// 表结果 即 值
String docPath=null;
String inPlinId=null;
List<String> coumlsComm =null;// 表信息 即 列名
String tableId=null;
if(request.getAttribute("tableInfo")!=null){
	tableInfo=(List<String>)request.getAttribute("tableInfo");
}
if(request.getAttribute("tableFruit")!=null){
	tableFruit=(List<String[]>)request.getAttribute("tableFruit");
}
if(request.getAttribute("docPath")!=null){
	docPath=request.getAttribute("docPath").toString();
}
if(null!=request.getAttribute("inPlinId")){
	inPlinId=request.getAttribute("inPlinId").toString();
}
if(null!=request.getAttribute("coumlsComm")){
	coumlsComm=(List<String>)request.getAttribute("coumlsComm");
}
if(null!=request.getAttribute("tableId")){
	tableId=request.getAttribute("tableId").toString();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type='text/javascript' src='<s:property value='#session.path'/>/dwr/engine.js'></script>
		<script type='text/javascript' src='<s:property value='#session.path'/>/dwr/util.js'></script>
		<script type='text/javascript' src='<s:property value='#session.path'/>/dwr/interface/AjaxExportService.js'></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库管理selectImportDataBase.jsp</title>
		<base target="_self">
		<link rel="StyleSheet"
			href="<s:property value='#session.path'/>/css/dtree.css"
			type="text/css" />
		<link rel="stylesheet" href="<%=path%>/css/tree.css" />
		<link href="<%=path%>/css/CIC.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/ajax.js"></script>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/all.js"></script>
		<script language="JavaScript" src="<%=path%>/js/validator.js"></script>
		<script language="JavaScript" src="<%=path%>/js/bgColor.js"></script>
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

function DisableButton() {
	window.setTimeout("disableButton('" + window.event.srcElement.id + "')", 0);
	document.forms[0].submit();
}
//数据导入
function dataBaseImport(box){
	var row=document.getElementsByName("com");//得到列名
	var price=document.getElementsByName(box.id+"info");//得到值
	var docPath=document.getElementById("docPath").value;//得到解压后的文件路径
	var inPlinId=document.getElementById("inPlinId").value;//导入方案ID
	var tableId=document.getElementById("tableId").value;//操作表ID
	var rows=new Array(row.length);
	var prices=new Array(price.length);
	for(var i=0;i<row.length;i++){
		rows[i]=row[i].value;
		prices[i]=price[i].value;
	}
	if(box.checked){
		AjaxExportService.ajaxDataBaseImport(rows,prices,docPath,inPlinId,tableId,function backsave(data){
			if(!data){
				alert("数据导入失败");
			}
		});
	}
	//else{
		//AjaxExportService.ajaxDeleteImportDataBaseInfo(rows,prices,docPath,inPlinId,function backsave(data){
			//if(!data){
				//alert("数据删除失败");
			//}
		//});
	//}
}

//导入选中数据
function dataImportCheckedInfo(){
	var dates=new Date();
	alert("beginTime = "+dates);
	dwr.engine.setAsync(false);
	//得到所有的复选框
	var boxs=document.getElementsByName("box");
	var docPath=document.getElementById("docPath").value;//文件夹路径
	var inPlinId=document.getElementById("inPlinId").value;//导入方案ID
	var coverData=document.getElementById("coverData");//覆盖数据
	var coverDataId=0;
	if(coverData.checked){
		coverDataId=1;
		//覆盖数据
		AjaxExportService.ajaxDeleteDataImportSuccessNotRelation(docPath,inPlinId,coverDataId,function backsave(data){
			if(!data){
				alert("覆盖数据导入失败");
			}
		});
	}else{
		AjaxExportService.ajaxDataImportSuccessNotRelation(docPath,inPlinId,coverDataId,function backsave(data){
			if(!data){
				alert("导入失败");
			}
		});
	}
	for(var i=0;i<boxs.length;i++){
		if(boxs[i].checked){//如果复选框被选中
			//先导入与主表没有关系的表数据
			dataImportNoRelationshipInfo(boxs[i]);
			//dataBaseImport(boxs[i]);//就将复选框的数据导入
		}
	}
	window.location.href="dataImportSuccess.action?docPath="+docPath+"&inPlinId="+inPlinId;
	//divShowBlock();
	//deleteFiles();
}

//全部导入
function allDataBaseImport(){
	var dates=new Date();
	alert("beginTime = "+dates);
	dwr.engine.setAsync(false);
	//再循环导入主表和子表数据
	var boxs=document.getElementsByName("box");
	var docPath=document.getElementById("docPath").value;//文件夹路径
	var inPlinId=document.getElementById("inPlinId").value;//导入方案ID
	var coverData=document.getElementById("coverData");//覆盖数据
	var coverDataId=0;
	if(coverData.checked){
		coverDataId=1;
		//覆盖数据
		AjaxExportService.ajaxDeleteDataImportSuccessNotRelation(docPath,inPlinId,coverDataId,function backsave(data){
			if(!data){
				alert("覆盖数据导入失败");
			}
		});
	}else{
		AjaxExportService.ajaxDataImportSuccessNotRelation(docPath,inPlinId,coverDataId,function backsave(data){
			if(!data){
				alert("数据导入失败");
			}
		});
	}
	for(var i=0;i<boxs.length;i++){
		boxs[i].checked=true;
		//先导入与主表没有关系的表数据
		dataImportNoRelationshipInfo(boxs[i]);
		//dataBaseImport(boxs[i]);
	}
	window.location.href="dataImportSuccess.action?docPath="+docPath+"&inPlinId="+inPlinId;
	//divShowBlock();
	//deleteFiles();
}

//导入与主表有关系的表数据
function dataImportNoRelationshipInfo(box){
	var docPath=document.getElementById("docPath").value;//文件夹路径
	var inPlinId=document.getElementById("inPlinId").value;//导入方案ID
	var row=document.getElementsByName("com");//得到列名
	var price=document.getElementsByName(box.id+"info");//得到值
	var rows=new Array(row.length);
	var prices=new Array(price.length);
	for(var i=0;i<row.length;i++){
		rows[i]=row[i].value;
		prices[i]=price[i].value;
	}
	var coverData=document.getElementById("coverData");//覆盖数据
	var coverDataId=0;
	if(coverData.checked){
		coverDataId=1;
		//覆盖数据
		AjaxExportService.ajaxDeleteDataImportSuccess(rows,prices,docPath,inPlinId,coverDataId,function backsave(data){
			if(!data){
				alert("覆盖数据导入失败");
			}
		});
	}else{
		//导入与主表没有关系的表数据
		AjaxExportService.ajaxDataImportSuccess(rows,prices,docPath,inPlinId,coverDataId,function backsave(data){
			if(!data){
				alert("数据导入失败");
			}
		});
	}
}
//DIV的显示与隐藏
function divShowBlock(){
	document.getElementById("chooseImportData").style.display="none";//Logo
	document.getElementById("dataImportbutton").style.display="none";//button
	document.getElementById("dataInfo").style.display="none";//dataInfo
	document.getElementById("importSuccess").style.display="block";
	document.getElementById("dataImportSuccess").style.display="block";
}
function deleteFiles(){
	var docPath=document.getElementById("docPath").value;//文件夹路径
	AjaxExportService.ajaxDeleteFile(docPath,function backsave(data){
		if(!data){
			alert("文件删除失败！！");
		}
	});
}
function selectAllBox(box){
	var boxs=document.getElementsByName("box");
	for(var i=0;i<boxs.length;i++){
		boxs[i].checked=box.checked;
	}
}
</script>
</head>
	<body leftMargin="1" topMargin="0">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<TR>
										<TD height="25">
											<div id="NavPan">
												<table cellpadding="0" cellspacing="0" border="0">
													<tr>
														<td align="center" valign="middle" class="NavTabBG">
															<a href="getDatasChemeNew.action" class="NavLink"
																target="_self">数据导出</a>
														</td>
														<td align="center" valign="middle" class="SelNavTabBG">
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
										<TD class="TableHeader" height="30" width="20%">
											&nbsp;<div id="chooseImportData">选择导入数据</div><div id="importSuccess" style="display:none;">导入完成</div>
											<input type="hidden" id="docPath" value="<%=docPath%>" />
											<input type="hidden" id="inPlinId" value="<%=inPlinId%>"/>
											<input type="hidden" id="tableId" value="<%=tableId%>"/>
										</TD>
										<td class="TableHeader" width="80%" align="right">
											<div id="dataImportbutton">
													<input type="checkbox" id="coverData"/>覆盖数据&nbsp;&nbsp;
												<input onclick="javascript:history.go(-1);" class="button"
													onmouseover="onButton(this);" onmouseout="offButton(this);"
													type="button" value="上一步" />
												<input onclick="dataImportCheckedInfo();" class="button"
													onmouseover="onButton(this);" onmouseout="offButton(this);"
													type="button" value="导入选中数据" />
												<input onclick="allDataBaseImport();" class="button"
													onmouseover="onButton(this);" onmouseout="offButton(this);"
													type="button" value="全部导入" />
											</div>
										</td>
									</TR>
									<tr>
										<td colspan="2">
											<TABLE id="Table1" cellSpacing="1" cellPadding="4"
												width="100%" class="TableBackGround">
												<TR>
													<TD class="TableBody" colSpan="2" height="40" align="center">
														<div id="dataInfo">
															<table class="DataGrid" cellspacing="0" cellpadding="4" rules="all" border="1" id="Datagrid1" style="width: 100%; border-collapse: collapse;">
																<tr class="DataGridHead" align="center">
																	<td><input type="checkbox" id="all" onclick="selectAllBox(this);"/></td>
																<%if(null!=tableInfo){for(int i=0;i<tableInfo.size();i++){%>
																	<td height="10">
																		<input type="hidden" name="com" value="<%=tableInfo.get(i)%>"/>
																		<%=coumlsComm.get(i)%>
																	</td>
																	<%}}%>
																</tr>
																<%if(null!=tableFruit){for(int i=0;i<tableFruit.size();i++){%>
																	<tr align="center">
																	<td><input type="checkbox" id="<%=i%>" name="box"/></td>
																	<%String[] str=tableFruit.get(i);for(int j=0;j<str.length;j++){%>
																		<input type="hidden" name="<%=i%>info" value="<%=str[j]%>"/>
																		<td><%=str[j]%></td>
																	<%}%>
																	</tr>
																<%}}%>
															</table>
														</div>
														<div id="dataImportSuccess" style="display: none;">
															<b>数据导入完成！！</b>
														</div>
													</TD>
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
	</body>
</HTML>