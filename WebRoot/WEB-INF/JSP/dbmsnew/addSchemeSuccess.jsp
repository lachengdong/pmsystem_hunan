<%@ page language="java" pageEncoding="utf-8"%>


<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>信息页面</title>
		<base target="_self">
		<link rel="StyleSheet"
			href="<%=path%>/css/dtree.css"
			type="text/css" />
		<link rel="stylesheet" href="<%=path%>/css/tree.css"/>
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
				function dosubmit(a){
					//window.parent.window.location.href=a;
					var frame1=window.parent.document.getElementById("schememain");
					if(null==frame1||''==frame1){
						window.location.href="showSchemeList.action";
					}else{
						//window.parent.document.subForm.action=a;
						//window.parent.document.subForm.submit();
						window.parent.location.href="showSchemeList.action";
					}
				}
				function closewin(){
				self.opener=null;
				self.close();}
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
  <SCRIPT language="JavaScript" src="<%=path%>/js/CutString.js"></SCRIPT>
  </head>
  <body>
        <table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr height="20">
			  <td colspan="3">
			  </td>
			</tr>
			<tr>
			  <td width="2%" rowspan="3" valign="top"><img src="<%=path%>/Images/commonImg/tit_03.gif" width="14" height="214"></td>
			  <td height="7" colspan="2" background="<%=path%>/Images/commonImg/tit_04.gif"></td>
			  <td width="3%" rowspan="3" valign="top"><img src="<%=path%>/Images/commonImg/tit_05.gif" width="16" height="214"></td>
			</tr>
			<tr>
			<td width="36%"><div align="left">
						 <s:if test="hasActionErrors()">
						  		<s:iterator value="actionErrors" id="error">
									  <s:if test="((#error == 'successschemeexp') || (#error == 'successupdatetable') ||  (#error == 'deleteSuccess')||  (#error == 'success'))">
									   <img src="<%=path%>/Images/commonImg/tit1_07.gif" width="286" height="200">
						 			 </s:if>
									 <s:else>
									 <img src="<%=path%>/Images/commonImg/tit_07.gif" width="286" height="200">
									 </s:else>
								</s:iterator>
						 </s:if>
					  </div></td>
					  <td width="59%" background="<%=path%>/Images/commonImg/tit_08.gif"><br>
						  <br>
						  <br>
						  <br>
						  <br>
						  <br>
						  <br>
						  <s:if test="hasActionErrors()">
								<s:iterator value="actionErrors" id="error">
								 <table width="351" height="24" border="0" cellpadding="0" cellspacing="0">
										<tr>
									    <s:if test="((#error == 'successschemeexp') || (#error == 'successupdatetable') ||  (#error == 'deleteSuccess')||  (#error == 'success'))">
												  <td><h5 class="style1"> <font color="green">　　您的操作已经成功完成!</font>页面将在<FONT color="red"><strong id="tt"></strong></FONT>秒后自动跳转！</h5>		     
												   </td>
									    </s:if>
									    <s:else>
												  <td>
												  	<h5 class="style1"> 错误信息：　<font color="red"><s:property value="error"/></font>
													  页面将在<FONT color="green"><strong id="tt"></strong></FONT>秒后自动跳转！</h5>		      
												 </td>
									    </s:else>
							    	</tr>
								</table>
								</s:iterator>
						 </s:if>
						 <s:if test="((#error == 'successschemeexp') || (#error == 'successupdatetable') ||  (#error == 'deleteSuccess')||  (#error == 'success'))">
				 			 <img src="<%=path%>/Images/commonImg/xyb.gif" width="50" height="18" onClick="dosubmit('showSchemeList.action');">
				 		</s:if>
						 </td>
					</tr>
			<tr>
			  <td height="7" colspan="3" background="<%=path%>/Images/commonImg/tit_11.gif"></td>
			</tr>
			<!--<tr> 
              <td>
        		<font color="green">操作已经成功完成!页面将在</font><FONT color="red"><strong  id="tt"></strong></FONT><font color="green">秒后自动跳转！</font>
        		<s:fielderror></s:fielderror>
              </td>
            </tr>
            --><!--
            <tr>
            <td colspan="3">
            <input type="button" class="button" value="方案列表"
			onmouseover="onButton(this);" onmouseout="offButton(this);"
			onclick="dosubmit('showSchemeList.action');" />
			&nbsp;
            <input type="button" class="button" value="返回"
				onmouseover="onButton(this);" onmouseout="offButton(this);"
				onclick="javaScript:history.go(-1);";>
			&nbsp;
            </td>
            </tr>
        --></table>
 </body>
 <script type="text/javascript">  
var t = 10;//设置跳转时间：秒    
function $(){    
	ta = t-1;    
	tb = t+"000";    
	d = document.getElementById("tt");    
	d.innerHTML=t;    
	setInterval("go_to()",1000);    
}    
$();    
   
function go_to(){    
	d.innerHTML=ta--;    
	if(ta<0){    
		var frame1=window.parent.document.getElementById("schememain");
		if(null==frame1||''==frame1){
			window.location.href="showSchemeList.action";
		}else{
			frame1.style.display="";
			window.parent.document.getElementById("schemeiframe").style.display="none";
		}
	}    
	else{    
		return;    
	}    
}    
//-->    
</script> 
</html>
