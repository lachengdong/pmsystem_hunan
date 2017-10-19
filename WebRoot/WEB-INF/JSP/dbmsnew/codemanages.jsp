<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>用户管理</title>
		<base target="_self">
		<link rel="StyleSheet"
			href="<s:property value='#session.path'/>/css/dtree.css"
			type="text/css" />
		<link rel="stylesheet" href="<%=path%>/css/tree.css"/>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/ajax.js"></script>
		<script type="text/javascript" src="<s:property value='#session.path'/>/js/all.js"></script>
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
function disshow(v,s)
{
	document.getElementById("sdid").value=v;
	document.getElementById("sdname").value=s;
}
function dosubmit(v){
	window.location=v;
	parent.leftFrame.location = "refreshLeftTree.action";
}
parent.leftFrame.location = "refreshLeftTree.action";
	function submitForm(s)
		{
			var bool=validator();
			if(bool)
			{
					document.getElementById("operateFlag").value=s;
					
					var  sdname=document.getElementById("sdname").value;
					if(sdname.length == 0 ||sdname==null){
					    alert("请选择所在单位！");
					    return false;
					}
					document.all.form1.action = "operateUser.action";
					document.all.form1.submit();
					parent.leftFrame.location = "refreshLeftTree.action";
					
					//return true;
			}
			//return false;
		}
		
		function querysubmit()
		{
			var bool=UtilForm.validOneCode(document.getElementById("suname").value);
		    if(!(bool))
		    {
		    	alert("不允许输入特殊字符");
		    	return false;
		    }
		    
			document.forms[0].action='queryByUser.action';
			document.forms[0].submit();
		}
		
		function validator()
		{
		 
		    var sdid = document.getElementById("sdid").value;
		    if(sdid == '0'){
				alert("请选择所在部门!");
				return false;
		    }
		    
		    	
			var suname = document.getElementById("suname").value;
		    if(suname.length == 0 || suname==""){
			alert("请输入姓名!");
			return false;
		    }
		    
		    var bool=UtilForm.validCode(document.form1);
		    if(!(bool))
		    {
		    	alert("不允许输入特殊字符");
		    	return false;
		    }
		    
		   var bool=UtilForm.vaildInt(document.getElementById("suorderby").value);
		   if(!(bool))
		    {
		    	alert("排序号请输入整型！");
		    	return false;
		    }

			var bool= UtilForm.length(suname,60);
		    if(!(bool))
			{
				alert("姓名超出长度！");
				return false;
			}
			

			var suofficephone= document.getElementById("suofficephone").value;
			var bool= UtilForm.length(suofficephone,20);
		    if(!(bool))
			{
				alert("电话号超出长度！");
				return false;
			}

		
			var sunickname= document.getElementById("sunickname").value;
			var bool= UtilForm.length(sunickname,20);
		    if(!(bool))
			{
				alert("用户名超出长度！");
				return false;
			}
			var calls =  document.getElementById("calls").value;
		    if(calls=="1"){
		   		alert("用户名已存在，请重新输入用户名!");
				return false;
		    }
			var suorderby= document.getElementById("suorderby").value;
			suorderby=UtilForm.trim(suorderby);
			var bool= UtilForm.length(suorderby,4);
		    if(!(bool))
			{
				alert("排序号超出长度！");
				return false;
			}
			
			
		    var suemail= document.getElementById("suemail").value;
		    if(!(suemail=="" || null==suemail))
		    {
				var bool=UtilForm.email(suemail);
				if(!(bool))
				{
					alert("邮箱格式不正确！");
					return false;
				}
			}
			
			var suemail= document.getElementById("suemail").value;
			var bool= UtilForm.length(suemail,50);
		    if(!(bool))
			{
				alert("邮箱超出长度！");
				return false;
			}

	       	return true;	    
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
        function validateChecked()
        {
         
		    var rad=document.getElementsByName("userArr");
			if(rad.length>0)
			{
				var flag="0"
			    var val="";
			    for(var i=0;i<rad.length;i++)
			    {
			    	if(rad[i].checked==true)
			    	{
			        	flag="1"
			        	var suids = rad[i].value;
			        	if(suids == "00000001"){
						   alert("系统管理员不可以调离！");
						   return false;
						 }
			            break;
			        }
			           
				}
		        if(flag=="1")
		        {
			        
			        if(window.confirm("确定调离选中的用户?")){
						deleteUserMutli.submit();
						parent.leftFrame.location = "refreshLeftTree.action";
					}
		            
		        }
		        else
		        {
		          alert("请选择一个或多个用户进行调离");
		        }
			}
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
</script>
	<body leftMargin="1" topMargin="0">
			<s:form name="getConnectionMessageList"  theme="simple">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="right" >
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<TR>
												<TD class="TableHeader" height="30" width="20%">
													&nbsp;代码列表
												</TD>
												<td background="<s:property value='#session.path'/>/Images/images/Table_Header.gif" 	width="80%" align="right">
															<INPUT onclick="dosubmit('addUser.action');"
																class="button" id="newperson"
																onmouseover="onButton(this);"
																onmouseout="offButton(this);" type="button" value="增加"
																name="newperson">
												</td>
											</TR>
											<tr>
												<td colspan="2">
													<TABLE id="Table1" cellSpacing="1" cellPadding="4"
														width="100%" class="TableBackGround">
														<TR>
															<TD class="TableBody" colSpan="2" height="40"
																align="center">
																<table class="DataGrid" cellspacing="0" cellpadding="4"
																	rules="all" border="1" id="Datagrid1"
																	style="width: 100%; border-collapse: collapse;">
																	<tr class="DataGridHead" align="center">
																		<td height="10">
																			<input type="checkbox" id="gvList_ctl01_chkAll"
																				name="checkall"
																				onclick="javascript:SelectAllCheckboxes(this);">
																		</td>
																		<td height="10">
																			代码类型编号
																		</td>
																		<td height="10">
																			代码类型描述
																		</td>
																		<td height="10">
																			操作
																		</td>
																	</tr>
																	<s:iterator id="userlist" value="#request.userlist"
																		status="st">
																		<tr
																			<s:if test="#st.Even"> class="GridViewRowStyle" </s:if>
																			<s:else> class="GridViewAlternatingRowStyle" </s:else>
																			onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'"
																			onmouseout="this.style.backgroundColor=e">
																			<td>
																				<center>
																					<input type="checkbox" name="userArr"
																						value='<s:property  value="#userlist.suid" />' />
																				</center>
																			</td>
																			<td align="center">
																				<s:property
																					value="#userlist.security_Departments_Bean.sddiscribe" />
																			</td>
																			<td align="center">
																				<s:property value="#userlist.suname" />
																			</td>
																			<td>
																				<center>
																					
																						<a style="cursor: hand" href="#"><img id="imgedit" src="<s:property value='#session.path'/>/Images/images/editItem.gif" border="0" alt="编辑"></a>
																						<a style="cursor: hand" href="#"> <img src="<s:property value='#session.path'/>/Images/images/deleteItem.gif"  border='0' alt='删除'> </a>
																				</center>
																			</td>
																		</tr>
																	</s:iterator>
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
								frmName="" actionName="getConnectionMessageList.action" />
						</td>
					</tr>
				</table>
			</s:form>
	</body>
</HTML>