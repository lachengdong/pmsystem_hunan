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
		<title>数据库方案showDataScheme.jsp</title>


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
  data.ddcid="";//方案id
  //更新页面显示方法
  function chemetypeChange(){
	var datatype=document.getElementById("ddctype").value;
	data.schemetype=datatype;//数据方案类型
	//datatype 0:导入;1:导出;2:数据交换
	if('0'==datatype){
		document.getElementById("chemespan").style.display="";
		document.getElementById("impdatabasespan").style.display="none";
		document.getElementById("databasespan").style.display="none";
	}else if('1'==datatype){
		document.getElementById("chemespan").style.display="none";
		document.getElementById("impdatabasespan").style.display="none";
		document.getElementById("databasespan").style.display="";
	}else{
		document.getElementById("chemespan").style.display="none";
		document.getElementById("impdatabasespan").style.display="";
		document.getElementById("databasespan").style.display="none";
	}
  }
  function expdatabase(){
	  var database= window.showModalDialog("showDataBase.action?rd="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:1000px");
	  if(undefined!=database)
	  {
		  document.getElementById("fromdatabaseid").value=database.databaseid;
		  data.fromdatabaseid=database.databaseid;
		  document.getElementById("databasename").value=database.databasename;
		  document.getElementById("expdatabasename").value=database.databasename;
	  }
  }
  function impdatabase(){
	  var database= window.showModalDialog("showDataBase.action?rd="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:1000px");
	  if(undefined!=database)
	  {
		  document.getElementById("todatabaseid").value=database.databaseid;
		  data.todatabaseid=database.databaseid;
		  document.getElementById("impdatabasename").value=database.databasename;
		  document.getElementById("impxmldatabasename").value=database.databasename;
	  }
  }
  function dosubmit(tourl,jsonarray)
  {
	    document.getElementById("jsonarray").value=jsonarray;
		document.forms[0].action=tourl;
		document.forms[0].submit();
  }
  function getDBTables(){
  	data.ddcid=document.getElementById("ddID").value;
  	data.fromdatabaseid=document.getElementById("fromdatabaseid").value;
  	urlstr="listTablesByDBid.action?fromdatabaseid="+data.fromdatabaseid+"&ddcid="+data.ddcid;
  	document.getElementById("talbeiframe").src=urlstr;
  }
  function showTable(urlStr){
  	 data.fromdatabaseid=data.fromdatabaseid=document.getElementById("fromdatabaseid").value;
 	 document.getElementById("tbInfoFrame").src=urlStr;
  }
  function SelectAllCheckboxes(spanChk)
	{
		var xState=spanChk.checked;
		var elm=document.getElementsByName("tableArr");
		for(i=0;i<elm.length;i++)
		{
			if(elm[i].checked!=xState){
			elm[i].click();
			}
		}
    }
    function deleteMult(){
	 var rad=document.getElementsByName("tableArr");
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
		        	data.ddcid=document.getElementById("ddID").value;
		        	document.subForm.action="deleteAllDataSchemeTable.action?ddcid="+data.ddcid;
		        	document.subForm.submit();
				}   
	        }
	        else
	        {
	          alert("请选择一条或多条信息进行删除");
	        }
		} 
}
function upStair(){
	window.parent.document.getElementById("schememain").style.display="";
	window.parent.document.getElementById("schemeiframe").style.display="none";
}
 function dosubmit(tourl)
  {
		document.forms[0].action=tourl;
		document.forms[0].submit();
  }
function doback(){
	window.parent.document.getElementById("ddcname").value="";
	window.parent.document.getElementById("ddctype").value="";
	window.parent.window.dosubmit("showSchemeList.action");
}
  </script>
	</head>


	<body scroll="yes">
		<s:form name="subForm" action="" theme="simple">
			<s:hidden name="fromdatabaseid"></s:hidden>
			<input type="hidden" name="ddID"
				value="<s:property value="#request.ddcid"/>" />
			<s:hidden name="todatabaseid"></s:hidden>
			<s:hidden name="jsonarray"></s:hidden>
			<table width="100%"  cellspacing="0" cellpadding="0" class="TableBackGround">
				<tr>
					<td>
						<span id="schememain">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TableBackGround">
								<tr>
									<td class="TableHeader" valign="middle">已保存的导出表</td>
									<td class="TableHeader" align="right">
										<input type="button" class="button" value="上一步"
											onmouseover="onButton(this);" onmouseout="offButton(this);"
											onclick="javaScript:upStair();" />
										&nbsp;
										<input type="button" class="button" value="取消"
											onmouseover="onButton(this);" onmouseout="offButton(this);"
											onclick="doback();">
										&nbsp;
										<INPUT onclick="deleteMult();" class="button"
														onmouseover="onButton(this);"
														onmouseout="offButton(this);" type="button"
														value="批量删除">
									</td>
								</tr>
								<tr>
									<td class="TableBody" colspan="3">
										<table id="tableList" class="TableBackGround" cellspacing="0"
											 rules="all"  id="Datagrid1"
											style="width: 100%; ">
											<tr class="DataGridHead" align="center">
												<td>
													<input type="checkbox" id="gvList_ctl01_chkAll"
														name="checkall"
														onclick="javascript:SelectAllCheckboxes(this);">
												</td>
												<td height="10">
													导出表名
												</td>
												<td height="10">
													导出表描述
												</td>
												<td height="10">
													序号
												</td>
												<td height="10">
													数据关系
												</td>
												<td height="10">
													额外查询条件
												</td>
												<td height="10">
													操作
												</td>
											</tr>
											<s:iterator id="tables" value="#request.tablesList"
												status="st">
												<tr
													<s:if test="#st.Even"> class="GridViewRowStyle" </s:if>
													<s:else> class="GridViewAlternatingRowStyle" </s:else>
													onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
													onmouseout="this.style.backgroundColor=e">
													<td>
														<center>
															<input type="checkbox" name="tableArr"
																value='<s:property  value="#tables.id.ddtid" />' />
														</center>
													</td>
													<td align="center">
														<s:property value="#tables.tablename" />
													</td>
													<td align="center">
														<s:property value="#tables.descrition" />
													</td>
													<td align="center">

														<input type="text" name="ddtorder" size="4"
															value="<s:property value='#tables.ddtorder' />" />
													</td>
													<td align="center">
													    <input type="text" name="shujuguanxi" size="20"
															value="<s:property value='#tables.shujuguanxi' />" />
														<!--<input type="text" name="lastrevisetime" size="10"
															value="<s:property value='#tables.lastrevisetime'/>" format="yyyy年MM月dd日" />-->
														<!--<s:date name="#tables.shujuguanxi" format="yyyy年MM月dd日"/>-->
													</td>
													<td align="center">
													    <input type="text" name="addcondition" size="25"
															value="<s:property value='#tables.addcondition' />" />
													</td>
													<td>
														<center>
															<a style="cursor: hand;text-decoration:none;"
																href="showTableInfo.action?ddcid=<s:property value="#request.ddcid"/>&ddtid=<s:property value="#tables.id.ddtid" />&fromtablename=<s:property value="#tables.tablename" />&fromdatabaseid=<s:property value="#request.fromdatabaseid" />"><img
																	id="imgedit"
																	src="<%=path%>/Images/images/editItem.gif"
																	border="0" alt="编辑"> </a>
															<a style="cursor: hand;text-decoration:none;"
																href="deleteDataSchemeTable.action?ddcid=<s:property value="#request.ddcid"/>&ddtid=<s:property value="#tables.id.ddtid" />">
																<img
																	src="<%=path%>/Images/images/deleteItem.gif"
																	border='0' alt='删除'> </a>
														</center>
													</td>
												</tr>
											</s:iterator>
										</table>
								</td>
							</tr>
								<!--<tr>
									<td>
										<span id="tableframe" style="display: "> <iframe
												id="tbInfoFrame" src="" frameborder="0"
												scrolling="yes" width="100%" height="120">
											</iframe> </span>
									</td>
								</tr>-->
							<tr>
								<td>
									<!--<span id="schemeiframe" style="display: "> 
							<iframe id="talbeiframe" src="" frameborder="0" scrolling="yes" width="100%" height="320">
							</iframe> </span>-->
								</td>
							</tr>
							<tr>
								<td colspan="3" class="TableBody" align="right"
									style="height: 26px">
									<img src="Images/gkzx_logo.jpg" width="390" height="40">
								</td>
							</tr>
						</table> </span>
				</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
