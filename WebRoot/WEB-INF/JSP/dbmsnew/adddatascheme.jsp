<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>添加数据库方案adddatascheme.jsp</title>
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
		  //更新页面显示方法
		  function chemetypeChange(){
				var datatype=document.getElementById("ddctype").value;
				schemetype=datatype;//数据方案类型
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
				  document.getElementById("databasename").value=database.databasename;
				  document.getElementById("expdatabasename").value=database.databasename;
				  downstep();
			  }
		  }
		  function impdatabase(){
			  var database= window.showModalDialog("showDataBase.action?rd="+new Date().getTime(),"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: No;dialogHeight:700px;dialogWidth:1000px");
			  if(undefined!=database)
			  {
				  document.getElementById("todatabaseid").value=database.databaseid;
				  document.getElementById("impdatabasename").value=database.databasename;
				  document.getElementById("impxmldatabasename").value=database.databasename;
				  downstep();
			  }
		  }
		  function downstep()
		  {
			  urlstr="";
			  var ddcname=document.getElementById("ddcname").value;
			  var fromdatabaseid=document.getElementById("fromdatabaseid").value
			  var todatabaseid=document.getElementById("todatabaseid").value
			   if(""==ddcname || undefined==ddcname)
			  {
				  alert("请输入方案名称！");
				  return ;
			  }
			  if('0'==schemetype)
			  {
				  if(""==document.getElementById("expscheme").value || undefined==document.getElementById("expscheme").value)
				  {
					  alert("请选择导出方案！");
					  return ;
				  }else{
					 var expscheme=document.getElementById("expscheme").value;
				  }
				  urlstr="queryTables.action?expscheme="+expscheme;
			  }else if('1'==schemetype){
				  if(""==fromdatabaseid || undefined==fromdatabaseid)
				  {
					  alert("请选择要导出的数据库！");
					  return ;
				  }
				  urlstr="listTablesByDBid.action?fromdatabaseid="+fromdatabaseid;
			  }else if('2'==schemetype || '3'==schemetype){
				  if(""==fromdatabaseid || undefined==fromdatabaseid)
				  {
					  return false;
				  }
				  if(""==todatabaseid || undefined==todatabaseid)
				  {
					  return false;
				  }
				  urlstr+="querySwapTable.action?fromdatabaseid="+fromdatabaseid;
		      }else{
				  alert("请选择方案类型！");
				  return;
		      }
			 document.getElementById("impiframe").src=urlstr;
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
		  function saveobj()
		  {
		  	frames['impiframe'].subScheme();
		  	window.location="showSchemeList.action?1=1";
		  }
		 	 /**
			*iframe动态高度
			*/
			function dyniframesize(down) { 
				var win= document.getElementById(down);
				if (document.getElementById){if (win && !window.opera){if (win.contentDocument && win.contentDocument.body.offsetHeight) win.height = win.contentDocument.body.offsetHeight; else if(win.Document && win.Document.body.scrollHeight)win.height = win.Document.body.scrollHeight;}}
			} 
  </script>
	</head>
	<body>
		<div  id="subForm"  name="subForm">
			 <div class="mini-toolbar">
					<s:hidden id="fromdatabaseid" name="fromdatabaseid"></s:hidden>
					<s:hidden id="todatabaseid" name="todatabaseid"></s:hidden>
					<s:hidden id="jsonarray" name="jsonarray"></s:hidden>
						<a class="mini-button" onclick="saveobj()" plain="true">保 存</a>
						<span class="separator"></span> 
						<a class="mini-button" onclick="doback('showSchemeList.action');" plain="true" >取 消</a>
									&nbsp;&nbsp; 方案名称：
									<s:textfield name="ddcname" id="ddcname" value="" />
									&nbsp;&nbsp; 方案类型:
									<s:select name="ddctype" id="ddctype"
										list="#request.schemeTypeCodeList"
										listKey="#request.id.scid"
										listValue="#request.sccontent" headerKey="-1"
										headerValue="--请选择--"
										onchange="javaScript:chemetypeChange()"></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<span id="databasespan" style="display: none">
										数据库名称：<s:textfield id="databasename"  name="databasename"></s:textfield>
											<a class="mini-button" onclick="expdatabase()" plain="true" style="width:40px">选择</a>
											</span>
									<span id="impdatabasespan" style="display: none">
										导出库名：&nbsp;<s:textfield id="expdatabasename" name="expdatabasename"></s:textfield>
										<a class="mini-button" onclick="expdatabase()" plain="true" style="width:40px">选择</a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										导入库名： <s:textfield id="impdatabasename" name="impdatabasename"></s:textfield>
										<a class="mini-button" onclick="impdatabase()" plain="true" style="width:40px">选择</a>
									</span>
									<span id="chemespan" style="display: none">
										对应的导出方案：&nbsp;<s:select name="expscheme" id="expscheme"
											list="#request.expSchemeList" 
											listKey="#request.ddcid"
											listValue="#request.ddcname" headerKey="-1"
											headerValue="--请选择--"></s:select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										导入数据库名称： <s:textfield id="impxmldatabasename" name="impxmldatabasename"></s:textfield>
										<a class="mini-button" onclick="impdatabase()" plain="true" style="width:40px">选择</a>
									</span>
			</div>
		</div>
		<div  class="mini-fit"  style="width:100%;height:100%;">
				<!-- 
				<iframe id="impiframe" src="" style="overflow-x:hidden;height:220px;border-bottom:0px;" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0" frameborder="0"
					width="100%">
				</iframe> 
				 -->
			    <iframe  id="impiframe" src="" width="100%" height="100%" scrolling="auto">
			   </iframe>
	   </div>
	</body>
</html>
