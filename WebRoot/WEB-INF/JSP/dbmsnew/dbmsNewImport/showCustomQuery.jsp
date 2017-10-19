<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>

<%
	String path = request.getContextPath();
%>
<%
List<String[]> strList=null;
List<String[]> logList=null;
String mainTablePath=null;
String inPlinId=null;
String tableId=null;
if(null!=request.getAttribute("StringList")){
	strList=(List<String[]>)request.getAttribute("StringList");
}
if(null!=request.getAttribute("LogList")){
	logList=(List<String[]>)request.getAttribute("LogList");
}
if(null!=request.getAttribute("mainTablePath")){
	mainTablePath=request.getAttribute("mainTablePath").toString();
}
if(null!=request.getAttribute("inPlinId")){
	inPlinId=request.getAttribute("inPlinId").toString();
}
if(null!=request.getAttribute("tableId")){
	tableId=request.getAttribute("tableId").toString();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>数据库管理showCustomQuery.jsp</title>
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
   /**处理选择列改变，逻辑运算符跟着改变*/
	function coulmsChange(tion){
		var op="";
		//得到选中列的值
		for(var i=0;i<tion.length;i++){
			if(tion[i].selected){
				op=tion[i].value;
			}
		}
		if(op!=""){
			var str=op.split(",");
			//逻辑运算符下拉列表
			var log=document.getElementById("logicsymbol");
			if(""==str[2]||"null"==str[2]){
				addOption(log,str);
			}else if(""!=str[2]&&"null"!=str[2]&&"0"!=str[2]){
				dwr.engine.setAsync(false);//设置为同步
				var path=document.getElementById("mainTablePath").value;//文件路径
				AjaxExportService.getImportLogicsymbolInfo(path,str[2],function backsave(data){
					if(0<data.length){
						var codes=document.getElementById("codes");//得到代码内容的下拉框
						codes.options.length=data.length;//得到下拉列表的大小为数组的大小
						for(var j=0;j<data.length;j++){//循环得到代码数组
							codes.options[j+1]=new Option(data[j],data[j]);//循环添加到下拉列表中，显示的值和隐藏的值一致
						}
						codes.options[0]=new Option("--请选择--","0");//设置下拉列表的最后一位为请选择
						codes.selectedIndex=0;//初始化时选中 请选择
						document.getElementById("dengyu").style.display="block";//显示代码值的div
						document.getElementById("codeValues").style.display="block";
						document.getElementById("logicsymbolDIV").style.display="none";//隐藏逻辑运算符的div
						document.getElementById("amongs").style.display="none";
						document.getElementById("input").style.display="none";
					}else{
						alert("得到代码内容失败");
					}
				});
			}else{
				var logicsymbol=document.getElementById("logicsymbol");
				logicsymbol.options.length=1;
				logicsymbol.options[0]=new Option("--请选择--","0");//设置下拉列表的最后一位为请选择
				document.getElementById("dengyu").style.display="none";//显示代码值的div
				document.getElementById("codeValues").style.display="none";
				document.getElementById("logicsymbolDIV").style.display="block";//隐藏逻辑运算符的div
				document.getElementById("amongs").style.display="none";
				document.getElementById("input").style.display="none";
			}
		}
	}

   //设置逻辑运算符的下拉列表
	function addOption(log,str){
		//等于不等于为公用
		var eq=document.getElementById("eqeq");//等于
		var noteq=document.getElementById("!eq");//不等于
		if(str[1].indexOf("DATE")>=0||str[1].indexOf("INTEGER")>=0||str[1].indexOf("LONG")>=0||str[1].indexOf("FLOAT")>=0||str[1].indexOf("INT")>=0||str[1].indexOf("NUMBER")>=0){
			var gt=document.getElementById("1gt");//大于
			var lt=document.getElementById("1lt");//小于
			var gteq=document.getElementById("1gteq");//大于等于
			var lteq=document.getElementById("1lteq");//小于等于
			log.options.length=7;
			log.options[0]=new Option("--请选择--","0");
			log.options[1]=new Option(eq.value,eq.id);
			log.options[2]=new Option(noteq.value,noteq.id);
			log.options[3]=new Option(gt.value,gt.id);
			log.options[4]=new Option(lt.value,lt.id);
			log.options[5]=new Option(gteq.value,gteq.id);
			log.options[6]=new Option(lteq.value,lteq.id);
		}else if(str[1].indexOf("VARCHAR")>=0||str[1].indexOf("NVARCHAR")>=0||str[1].indexOf("CHAR")>=0){
			var like=document.getElementById("like");//包含
			log.options.length=4;
			log.options[0]=new Option("--请选择--","0");
			log.options[1]=new Option(eq.value,eq.id);
			log.options[2]=new Option(noteq.value,noteq.id);
			log.options[3]=new Option(like.value,like.id);
		}else if(str[1]=="0"){
			log.options.length=1;
			log.options[0]=new Option("--请选择--","0");
		}
		document.getElementById("amongs").style.display="none";
		document.getElementById("input").style.display="none";
		document.getElementById("logicsymbolDIV").style.display="block";//隐藏逻辑运算符的div
		document.getElementById("dengyu").style.display="none";//显示代码值的div
		document.getElementById("codeValues").style.display="none";
	}

	function logChange(log){
		var tion=document.getElementById("coulms");
		var op="";
		//得到选中列的值
		for(var i=0;i<tion.length;i++){
			if(tion[i].selected){
				op=tion[i].value;
			}
		}
		var str=op.split(",");
		for(var i=0;i<log.length;i++){
			if(log[i].selected){
				if(log[i].value=="among"){
					//介于
					if(str[1].indexOf("DATE")>=0){
						document.getElementById("begin").setAttribute("onclick",function(){calendar(this,false)});
						document.getElementById("end").setAttribute("onclick",function(){calendar(this,false)});
					}else{
						document.getElementById("begin").setAttribute("onclick",function(){return false});
						document.getElementById("end").setAttribute("onclick",function(){return false});
					}	
					document.getElementById("input").style.display="none";
					document.getElementById("amongs").style.display="block";
					document.getElementById("logicsymbolDIV").style.display="block";//隐藏逻辑运算符的div
					document.getElementById("dengyu").style.display="none";//显示代码值的div
					document.getElementById("codeValues").style.display="none";
				}else if(log[i].value=="0"){
					//请选择
					document.getElementById("amongs").style.display="none";
					document.getElementById("input").style.display="none";
					document.getElementById("logicsymbolDIV").style.display="none";//隐藏逻辑运算符的div
					document.getElementById("dengyu").style.display="none";//显示代码值的div
					document.getElementById("codeValues").style.display="none";
				}else{
					//普通
					if(str[1].indexOf("DATE")>=0){
						//document.getElementById("valueInfo").onclick="calendar(this,false);";
						document.getElementById("valueInfo").setAttribute("onclick",function(){calendar(this,false)});
					}else{
						document.getElementById("valueInfo").setAttribute("onclick",function(){return false});
					}
					document.getElementById("amongs").style.display="none";
					document.getElementById("input").style.display="block";
					document.getElementById("logicsymbolDIV").style.display="block";//隐藏逻辑运算符的div
					document.getElementById("dengyu").style.display="none";//显示代码值的div
					document.getElementById("codeValues").style.display="none";
				}
			}
		}
		document.getElementById("begin").value="";
		document.getElementById("end").value="";
		document.getElementById("valueInfo").value="";
	}

	function addWhereInfo(){
		//介于
		var am=document.getElementById("amongs").style.display;
		//普通
		var ip=document.getElementById("input").style.display;
		//代码
		var code=document.getElementById("codeValues").style.display;
		if(am=="none"&&ip=="none"&&"none"==code){
			alert("请选择条件");
			return false;
		}else if(am!="none"&&ip=="none"){
			//显示的为介于
			var begin=document.getElementById("begin").value;
			var end=document.getElementById("end").value;
			if(begin==""){
				alert("请输入最小值");
				return false;
			}else if(end==""){
				alert("请输入最大值");
				return false;
			}else{
				showWhereInfo(begin,end,"");
			}
		}else if(am=="none"&&ip!="none"&&"none"==code){
			var vs=document.getElementById("valueInfo").value;
			if(vs==""){
				alert("请输入值");
				return false;
			}else{
				showWhereInfo(vs,"","");
			}
		}else if(am=="none"&&ip=="none"&&"none"!=code){
			var vs=document.getElementById("valueInfo").value;
			if(vs==""||"0"==vs){
				alert("请输入值");
				return false;
			}else{
				showWhereInfo(vs,"","code");
			}
		}
	}

	function showWhereInfo(begin,end,code){
		//列
		var showCom="";
		var hiddenCom="";
		//逻辑运算符
		var showLog="";
		var hiddenLog="";
		
		var coulm=document.getElementById("coulms");
		var log=document.getElementById("logicsymbol");
		//得到列
		for(var i=0;i<coulm.length;i++){
			if(coulm[i].selected){
				showCom=coulm[i].innerText;
				var str=coulm[i].value;
				var ss=str.split(",");
				hiddenCom=ss[0];
			}
		}
		if(""!=code&&"code"==code){
			showLog="等于";
			hiddenLog="eqeq";
		}else{
			//得到逻辑运算符
			for(var i=0;i<log.length;i++){
				if(log[i].selected){
					showLog=log[i].innerText;
					hiddenLog=log[i].value;
				}
			}
		}
		var showInfo="";
		var hiddenInfo="";
		if(end==""){
			showInfo="<a href='#' onclick='javascript:deleteWhereCondition(this)' id='"+hiddenCom+","+hiddenLog+","+begin+"' style='color: blue;'>删除</a>&nbsp;&nbsp;&nbsp;<b>"+showCom+" "+showLog+" "+begin+"</b>,<br/>";
			hiddenInfo=hiddenCom+","+hiddenLog+","+begin+";";
		}else{
			showInfo=showCom+" 大于 "+begin+" 并且小于 "+end+",<br/>";
			hiddenInfo=hiddenCom+",min,"+begin+",max,"+end+";";
		}
		document.getElementById("showInfo").innerHTML+=showInfo;
		document.getElementById("hiddenInfo").innerHTML+=hiddenInfo;
		document.getElementById("valueInfo").value="";
	}

	function goAction(){
		var info=document.getElementById("hiddenInfo").innerHTML;
		document.getElementById("whereCondition").value=info;
		document.forms[0].action="showDataBaseInformation.action";
		document.forms[0].submit();
	}

	function textInnerText(option){
		document.getElementById("valueInfo").value=option.value;
	}

	function deleteWhereCondition(a){
		var shows=document.getElementById("showInfo").innerHTML;
		var hiddens=document.getElementById("hiddenInfo").innerHTML;
		//拆分
		var showList=shows.split("<BR>");
		var hiddenList=hiddens.split(";");
		var ids=a.id.split(",");//3个值
		var show="";
		var hidden="";
		for(var i=0;i<hiddenList.length;i++){
			if(""!=hiddenList[i]){
				var hid=hiddenList[i].split(",");//3个
				if(hid[0]!=ids[0] || hid[1]!=ids[1] || hid[2]!=ids[2]){
					hidden+=hiddenList[i]+";";
					show+=showList[i]+"<BR>";
				}
			}
		}
		document.getElementById("showInfo").innerHTML=show;
		document.getElementById("hiddenInfo").innerHTML=hidden;
	}
</script>
	<body leftMargin="1" topMargin="0">
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
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
																<td align="center" valign="middle" class="NavTabBG" >
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
													&nbsp;选择导出列<input type="hidden" value="<%=mainTablePath%>" id="mainTablePath" name="mainTablePath"/>
													<input type="hidden" id="inPlinId" value="<%=inPlinId%>" name="inPlinId"/>
													<input type="hidden" id="tableId" value="<%=tableId%>"/>
													<input type="hidden" id="whereCondition" name="condition"/>
												</TD>
												<td class="TableHeader" width="80%" align="right">
												<input onclick="javascript:history.go(-1);" class="button"
												onmouseover="onButton(this);" onmouseout="offButton(this);"
												type="button" value="上一步" />
												    <input onclick="goAction();" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="查询" />
												</td>
											</TR>
											<tr>
												<td colspan="2">
													<TABLE id="Table1" cellSpacing="1" cellPadding="4"
														width="100%" class="TableBackGround">
														<TR>
															<TD class="TableBody" colSpan="2" height="40" align="center">
																<table class="DataGrid" cellspacing="0" cellpadding="4" rules="all" border="1" id="Datagrid1" style="width: 100%; border-collapse: collapse;">
																	<tr class="DataGridHead">
																	<td height="10" colspan="4">
																		选择条件
																	</td>
																</tr>
																<tr>
																	<td width="25%" align="center">
																		<select onchange="coulmsChange(this);" id="coulms" style="width: 180px;">
																			<option value="0,0,0">--请选择--</option>
																			<%if(strList!=null){for(String[] str:strList){%>
																			<option value="<%=str[0]%>,<%=str[2]%>,<%=str[3]%>"><%=str[1]%></option>
																			<%}}%>
																		</select>
																	</td>
																	<td width="25%" align="center">
																		<span id="logicsymbolDIV">
																			<select id="logicsymbol" style="width: 180px;" onchange="logChange(this);">
																				<option value="0">--请选择--</option>
																			</select>
																		</span>
																		<span id="dengyu" style="display: none;" style="width: 180px;">
																			&nbsp;<b>等于</b>
																		</span>
																	</td>
																	<td width="25%" align="center">
																		<span id="codeValues" style="display: none;">
																			<select id="codes" onchange="textInnerText(this);" style="width: 180px;"></select>
																		</span>
																		<!-- 介于 -->
																		<span style="display: none;" id="amongs"> 最小值:<input
																				id="begin" type="text" />&nbsp;&nbsp; 最大值:<input
																				id="end" type="text" /> </span>
																		<!-- 普通 -->
																		<span style="display: none;" id="input">
																			<input id="valueInfo" type="text" style="width: 180px;"/>
																		</span>
																	</td>
																	<td align="center">
																		<%if(null!=logList){for(String[] str:logList){%>
																		<input type="hidden" id="<%=str[1]%>"
																			value="<%=str[0]%>" />
																		<%}}%>
																		<input type="button" value="添加条件" class="button" onmouseover="onButton(this);" onmouseout="offButton(this);" onclick="return addWhereInfo();" />
																	</td>
																</tr>
																<tr class="DataGridHead">
																	<td height="10" colspan="4">
																		查询条件
																	</td>
																</tr>
																<tr>
																	<td colspan="4">
																		<div id="showInfo"></div>
																		<div id="hiddenInfo" style="display: none;"></div>
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
			</s:form>
	</body>
</HTML>
