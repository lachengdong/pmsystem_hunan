<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<%
String docPath=null;
if(null!=request.getAttribute("docPath")){
	docPath=request.getAttribute("docPath").toString();
}
String doc=null;
if(null!=request.getAttribute("doc")){
	doc=request.getAttribute("doc").toString();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库管理downDoc.jsp</title>
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
		<script type="text/javascript">
			function MM_swapImgRestore() { //v3.0
			    var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
			}
			
			function MM_findObj(n, d) { //v4.01
			    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
			    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
			    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
			    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
			    if(!x && d.getElementById) x=d.getElementById(n); return x;
			}
			
			function MM_swapImage() { //v3.0
			    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
			    if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
			}

	</script>
		<link href="<%=path%>/css/CIC.css" rel="stylesheet" type="text/css">
		<SCRIPT language="JavaScript" src="<%=path%>/js/bgColor.js"></SCRipt>
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

function DisableButton() {
	window.setTimeout("disableButton('" + window.event.srcElement.id + "')", 0);
	document.forms[0].submit();
}
function disableButton(buttonID) {
	document.getElementById(buttonID).value='loading...'; 
	document.getElementById(buttonID).disabled=true;
}
var bXmlHttpSupport = (typeof XMLHttpRequest != "undefined" || window.ActiveXObject);
     
    if (typeof XMLHttpRequest == "undefined" && window.ActiveXObject) {
        function XMLHttpRequest() {
            var arrSignatures = ["MSXML2.XMLHTTP.5.0", "MSXML2.XMLHTTP.4.0",
                                 "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP",
                                 "Microsoft.XMLHTTP"];
                             
            for (var i=0; i < arrSignatures.length; i++) {
                try {        
                    var oRequest = new ActiveXObject(arrSignatures[i]);            
                    return oRequest;        
                } catch (oError) { /*ignore*/ }
            }          
    
            throw new Error("MSXML is not installed on your system.");               
        }
    }  
   function dosubmit(){
	   window.location.href="addConnectionMessage.action";
}

   function downDoc(){
		document.getElementById("scpirt").style.display="block";
		document.getElementById("button").style.display="none";
		//window.location.href="docDownXML.action?docPath="+doc;
	}
</script>
	<body leftMargin="1" topMargin="0">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
												<TD class="TableHeader" height="30" width="20%">
													&nbsp;选择导出列
												</TD>
												<td class="TableHeader" width="80%" align="right">
												</td>
											</TR>
											<tr>
												<td colspan="2">
													<TABLE id="Table1" cellSpacing="1" cellPadding="4"
														width="100%" class="TableBackGround">
														<TR>
															<TD class="TableBody" colSpan="2" height="40" align="center">
																<table class="DataGrid" cellspacing="0" cellpadding="4" rules="all" border="1" id="Datagrid1" style="width: 100%; border-collapse: collapse;">
																	<tr align="center">
																		<td align="center">
																		<div id="button">
																			<form action="docDownXML.action" method="post">
																				<input type="submit" value="点击下载" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" onclick="downDoc()"/>
																				<input type="hidden" value="<%=docPath%>" id="docPath" name="docPath"/>
																				<input type="hidden" value="<%=doc%>" id="doc" name="docName"/>
																			</form>
																		</div>
																		<div id="scpirt" style="display:none;">
																			<b>导出成功!!</b>
																		</div>
																		</td>
																	</tr>
																</table>
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
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td align="right" valign="bottom">
							<page:page name="pageController" title="" unit="条"
								frmName="getConnectionMessageList" actionName="getConnectionMessageList.action" />
						</td>
					</tr>
				</table>
	</body>
</HTML>
