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
		<title>修改数据库方案updatedatascheme.jsp</title>

<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 

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

</script>
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
  var data=[];
  data.schemetype="";//数据方案类型
  data.fromdatabaseid="";//源数据库
  data.todatabaseid="";//目标数据库
  data.expscheme="";//导出方案
  data.ddcname="";//方案名称
  data.fromtable="";//导出表名
  data.totable="";//导入表名
  //更新页面显示方法
  function expdatabase(){
	  var database= window.showModalDialog("showDataBase.action?rd="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:1000px");
	  if(undefined!=database)
	  {
		  document.getElementById("fromdatabaseid").value=database.databaseid;
<%--		  data.fromdatabaseid=database.databaseid;--%>
		  document.getElementById("databasename").value=database.databasename;
		  document.getElementById("expdatabasename").value=database.databasename;
	  }
  }
  function impdatabase(){
	  var database= window.showModalDialog("showDataBase.action?rd="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:1000px");
	  if(undefined!=database)
	  {
		  document.getElementById("todatabaseid").value=database.databaseid;
<%--		  data.todatabaseid=database.databaseid;--%>
		  document.getElementById("impdatabasename").value=database.databasename;
		  document.getElementById("impxmldatabasename").value=database.databasename;
	  }
  }
  function downstep()
  {
	  urlstr="";
	  data.ddcname=document.getElementById("ddcname").value;
	  data.ddcid=document.getElementById("ddcid").value;
	  if('0'==data.schemetype)
	  {
		  urlstr+="toUpdateTableImpScheme.action?ddcid="+data.ddcid+"&expscheme="+data.expscheme;
	  }else if('1'==data.schemetype){
		  urlstr="toUpdateDataSchemeNew.action?ddcid="+data.ddcid;
		  document.getElementById("impiframe").src="";
	  }else if('2'==data.schemetype||'3'==data.schemetype){
		  urlstr+="toUpdateTableSwapScheme.action?ddcid="+data.ddcid;
      }else{
		  alert("请选择方案类型！");
		  return;
      }
      if(document.getElementById("impiframe").src!=urlstr)
      {
	 	 document.getElementById("impiframe").src=urlstr;
      }
      document.getElementById("schememain").style.display="none";
      document.getElementById("schemeiframe").style.display="";
  }
  function dosubmit(tourl,jsonarray)
  {
	    document.getElementById("jsonarray").value=jsonarray;
		document.forms[0].action=tourl;
		document.forms[0].submit();
  }
  function doback(tourl)
  {
  		window.location.href=tourl;
  }
  function myreset()
  {
	  document.getElementById("impiframe").src=document.getElementById("impiframe").src;
  }
  </script>
	</head>


	<body>
		<s:form name="subForm" action="" theme="simple">
			<s:hidden name="fromdatabaseid" id="fromdatabaseid"></s:hidden>
			<s:hidden name="todatabaseid" id="todatabaseid"></s:hidden>
			<s:hidden name="jsonarray" id="jsonarray"></s:hidden>
			<s:hidden name="ddctype" id="ddctype"></s:hidden>
			<s:hidden name="ddcid" id="ddcid"></s:hidden>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<span id="schememain">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="TableBackGround">
								<tr>

									<td class="TableHeader" align="right">
										<input type="button" class="button" value="下一步"
											onmouseover="onButton(this);" onmouseout="offButton(this);"
											onclick="javaScript:downstep();" />
										&nbsp;
										<input type="button" class="button" value="取消"
											onmouseover="onButton(this);" onmouseout="offButton(this);"
											onclick="doback('showSchemeList.action');"/>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="TableBody" colspan="3">
										<div>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														<TABLE class="TableBackGround" id="Table1" cellSpacing="0"
															cellPadding="4" width="100%" border="0">
															<tr>
																<TD class="TableBody" width="100%">
																	<table class="TableBackGround" id="Table3"
																		cellSpacing="1" cellPadding="4" width="100%">
																		<tr align="left">
																			<td class="TableBody">
																				&nbsp;&nbsp;&nbsp;&nbsp; 方案名称：
																				<s:textfield name="ddcname" id="ddcname"/>
																				&nbsp;&nbsp; 方案类型:
																				<s:property value="#request.typename"/>
																			</td>
																		</tr>
																		<tr align="left" valign="middle">
																			<td class="TableBody" colspan="2" height="30">
																				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																				<span id="databasespan" style="display: none">
																					数据库名称：<s:property value='#request.fromdatabasename'/>
																					 </span>
																				<span id="impdatabasespan" style="display: none">
																					导出数据库名称：<s:property value='#request.fromdatabasename'/>
																					<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																					导入数据库名称： <s:property value='#request.todatabasename'/>
																					</span>
																				<span id="chemespan" style="display: none">
																					对应的导出方案：<s:property value='#request.expschemename'/>
																					<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																					导入数据库名称：<s:property value='#request.todatabasename'/>
																					</span>
																						<script type="text/javascript">
																							var datatype="<s:property value='#request.ddctype'/>";
																							data.schemetype=datatype;//数据方案类型
																							//datatype 0:导入;1:导出;2:数据交换
																							if('0'==datatype){
																								document.getElementById("chemespan").style.display="";
																								document.getElementById("impdatabasespan").style.display="none";
																								document.getElementById("databasespan").style.display="none";

																							    data.todatabaseid="<s:property value='#request.todatabaseid'/>";//目标数据库
																							    data.expscheme="<s:property value='#request.expscheme'/>";//导出方案
																							}else if('1'==datatype){
																								document.getElementById("chemespan").style.display="none";
																								document.getElementById("impdatabasespan").style.display="none";
																								document.getElementById("databasespan").style.display="";

																								data.fromdatabaseid="<s:property value='#request.fromdatabaseid'/>";//源数据库
																							}else{
																								document.getElementById("chemespan").style.display="none";
																								document.getElementById("impdatabasespan").style.display="";
																								document.getElementById("databasespan").style.display="none";

																								data.fromdatabaseid="<s:property value='#request.fromdatabaseid'/>";//源数据库
																								data.todatabaseid="<s:property value='#request.todatabaseid'/>";//目标数据库
																							}
																						
																						</script>
																			</td>
																		</tr>
																	</table>
																</TD>
															</tr>
														</table>
													</td>
												</tr>
											</table>


										</div>
									</td>
								</tr>
								<tr>
									<td colspan="3" class="TableBody" align="right"
										style="height: 26px">
										<img src="Images/gkzx_logo.jpg" width="390" height="40">
									</td>
								</tr>
							</table> </span>
						<tr>
						<td>
						<span id="schemeiframe" style="display: ">
							<iframe
								id="impiframe" src="" frameborder="0" scrolling="yes"
								width="100%" height="320">
							</iframe> </span>
							 <script type="text/javascript">
							    function reinitIframe(){
									    var iframe = document.getElementById("impiframe");
									    try{
										    var bHeight = iframe.contentWindow.document.body.scrollHeight;
										    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
										    var height = Math.max(bHeight, dHeight);
										    iframe.height =  height;
									    }catch (ex){}
									    }
									    window.setInterval("reinitIframe()", 200);
							    </script> 
					</td>
				</tr>

			</table>
		</s:form>
	</body>
</html>
