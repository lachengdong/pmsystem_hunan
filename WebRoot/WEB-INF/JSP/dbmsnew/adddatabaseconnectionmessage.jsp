<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>数据库管理adddatabaseconnectionmessage.jsp</title>
	   <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	   <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
				if(document.getElementById("databasetype").value=="choose"){
					alert("请选择数据库类型");
					return false;
				}else if(document.getElementById("databasename").value==""){
					alert("请输入数据库名称");
					return false;
				}else if(document.getElementById("ddname").value==""){
					alert("请输数据库描述信息");
					return false;
				}else if(document.getElementById("ddip").value==""){
					alert("请输入IP地址");
					return false;
				}else if(document.getElementById("port").value=="0"){
					alert("请输入端口号");
					return false;
				}else if(document.getElementById("databaseuser").value==""){
					alert("请输入数据库用户名");
					return false;
				}else if(document.getElementById("ddorg").value==""){
					alert("请输入所属部门");
					return false;
				}else{
					document.testConnectionMessage.action="testConnectionMessage.action";
					document.testConnectionMessage.submit();
					return true;
				}
			} 
			function subForm(v){
				document.testConnectionMessage.action=v;
				document.testConnectionMessage.submit();
			}
</script>
	</head>
	<body >
			<form id="testConnectionMessage" name="testConnectionMessage"  theme="simple" action="">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar" >
					<TR>
						<td width="80%" align="right">
							<s:if test="#request.operateFlag=='success'"><!-- 添加数据库信息时 如果测试成功 就进行保存 -->
								 测试连接成功！！！
									<a class="mini-button" onclick="subForm('insertConnectionMessage.action');" plain="true" style="width:60px">保存</a> 
							</s:if>
							<s:if test="#request.operateFlag=='faile'"><!-- 添加数据库信息时 如果测试成功 就进行保存 -->
								 测试连接失败！！！
							</s:if>
							<s:if test="#request.operateFlag!='success'">
								<a class="mini-button" onclick="dosubmit();" plain="true" style="width:80px">测试连接</a> 
							</s:if>
							<a class="mini-button" onclick="javascript:history.back(-1);" plain="true" style="width:60px">返回</a> 
						</td>
					</TR>
					<s:if test="#request.operateFlag=='success'"><!-- 测试能够连接成功后  跳转到该页面（此时信息不可以编辑）才可以进行保存 -->
					    <TR>
							<TD  colSpan="2" height="40" align="left">
								<TABLE  id="Table3" width="100%" >
									<tr>
										<td >
											数据库类型
										</td>
										<td  colspan="3">
											<s:textfield  name="databasetype" id="databasetype" maxlength="50" readonly="true"></s:textfield>
										</td>
									</tr>
									<tr>
										<td >
											数据库名称
										</td>
										<td >
											<s:textfield  name="databasename" id="databasename" maxlength="50" readonly="true" ></s:textfield>
											<input type="hidden" id="ddid" name="ddid" value='<s:property  value="#request.ddid" />'/>
										</td>
										<td >
											数据库描述
										</td>
										<td >
											<s:textfield  name="ddname" id="ddname" maxlength="50" readonly="true" ></s:textfield>
										</td>
									</tr>
									<tr>
										<td >
											<div id="call">数据库IP地址</div>
										</td>
										<td >
											<s:textfield  name="ddip" id="ddip" maxlength="50" readonly="true" ></s:textfield>
										</td>
										<td >
											端口号
										</td>
										<td >
											<s:textfield  name="port" id="port" maxlength="50" readonly="true" ></s:textfield>
										</td>
										
									</tr>
									<tr>
										<td >
											<div id="call">数据库用户名</div>
										</td>
										<td >
											<s:textfield  name="databaseuser" id="databaseuser" maxlength="50"  readonly="true"></s:textfield>
										</td>
										<td >
											数据库密码
										</td>
										<td >
											<s:textfield  name="databasepassword" id="databasepassword" maxlength="50"  readonly="true"></s:textfield>
										</td>
										
									</tr>
									<tr>
										<td >数据库所属部门机构</td>
										<td  colspan="3">
												<s:textfield  name="ddorg" maxlength="50" id="ddorg"  readonly="true"></s:textfield>
										</td>
									
									 </tr>
								</table>
							</TD>
						</TR>
					</s:if>
					<s:else>
				 		<TR>
							<TD  height="40" align="left">
								<TABLE  id="Table3" width="100%" cellspacing="0" cellpadding="0"   >
									<tr>
										<td >
											数据库类型
										</td>
										<td  colspan="3">
											<s:if test="#request.operateFlag=='update'">
												<select id="databasetype"  name="databasetype" id="databasetype">
												      <s:if test="#request.databasetype=='MySQL'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL" selected="selected"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server"/>Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005/2008</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2">DB2</option>
														<option value="Informix">Informix</option>
														<option value="Dameng">"Dameng"</option>
												  	</s:if>
												     <s:elseif test="#request.databasetype=='Oracle'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle" selected="selected"/>Oracle</option>
														<option value="Microsoft SQL Server"/>Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2">DB2</option>
														<option value="Informix">Informix</option>
														<option value="Dameng">"Dameng"</option>
												   </s:elseif>
												     <s:elseif test="#request.databasetype=='Microsoft SQL Server'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server" selected="selected"/>Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2">DB2</option>
														<option value="Informix">Informix</option>
														<option value="Dameng">"Dameng"</option>
												   </s:elseif>
												   <s:elseif test="#request.databasetype=='Microsoft SQL Server 2005'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server"/>Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005" selected="selected"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2">DB2</option>
														<option value="Informix">Informix</option>
														<option value="Dameng">"Dameng"</option>
												   </s:elseif>
												   <s:elseif test="#request.databasetype=='Sybase'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server" />Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase" selected="selected">Sybase</option>
														<option value="DB2">DB2</option>
														<option value="Informix">Informix</option>
														<option value="Dameng">"Dameng"</option>
												   </s:elseif>
												    <s:elseif test="#request.databasetype=='DB2'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server" />Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2" selected="selected">DB2</option>
														<option value="Informix">Informix</option>
														<option value="Dameng">"Dameng"</option>
												   </s:elseif>
												     <s:elseif test="#request.databasetype=='Informix'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server" />Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2" >DB2</option>
														<option value="Informix" selected="selected">Informix</option>
														<option value="Dameng">"Dameng"</option>
												   </s:elseif>
												     <s:elseif test="#request.databasetype=='Dameng'">
										                <option value="choose" >---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server" />Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2" >DB2</option>
														<option value="Informix" >Informix</option>
														<option value="Dameng" selected="selected">"Dameng"</option>
												   </s:elseif>
												   <s:else>	
												        <option value="choose"  selected="selected">---请选择---</option>
														<option value="MySQL"/>MySQL</option>
														<option value="Oracle"/>Oracle</option>
														<option value="Microsoft SQL Server" />Microsoft SQL Server</option>
														<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
														<option value="Sybase">Sybase</option>
														<option value="DB2" >DB2</option>
														<option value="Informix" >Informix</option> 
														<option value="Dameng">"Dameng"</option> 
												   </s:else>    
												</select>
											</s:if>
											<s:else>
												<select id="databasetype"  name="databasetype">
											       <option value="choose" selected="selected">---请选择---</option>
													<option value="MySQL"/>MySQL</option>
													<option value="Oracle"/>Oracle</option>
													<option value="Microsoft SQL Server"/>Microsoft SQL Server</option>
													<option value="Microsoft SQL Server 2005"/>Microsoft SQL Server 2005</option>
													<option value="Sybase">Sybase</option>
													<option value="DB2">DB2</option>
													<option value="Informix">Informix</option>
													<option value="Dameng">"Dameng"</option> 
												</select>
											</s:else>
										</td>
									</tr>
									<tr>
										<td >
											数据库名称
										</td>
										<td >
											<s:textfield  name="databasename" id="databasename" maxlength="50" ></s:textfield>
										</td>
										<td >
											数据库描述
										</td>
										<td >
											<s:textfield name="ddname" id="ddname" maxlength="50" ></s:textfield>
										</td>
										
									</tr>
									<tr>
										<td >
											<div id="call">数据库IP地址</div>
										</td>
										<td >
											<s:textfield id="ddip" name="ddip"  maxlength="50" ></s:textfield>
										</td>
										<td >
											端口号
										</td>
										<td >
											<s:textfield id="port"  name="port" maxlength="50" onkeyup="this.value=this.value.replace(/\D/g,'')"></s:textfield>
										</td>
									</tr>
									<tr>
										<td >
											<div id="call">数据库用户名</div>
											<input type="hidden" name="ddid" value='<s:property  value="#request.ddid" />'>
										</td>
										<td >
											<s:textfield id="databaseuser"  name="databaseuser" maxlength="50" ></s:textfield>
										</td>
										<td >
											数据库密码
										</td>
										<td >
											<s:textfield  id="databasepassword" name="databasepassword" maxlength="50" ></s:textfield>
										</td>
									</tr>
									<tr>
										<td >数据库所属部门机构</td>
										<td  colspan="3">
												<s:textfield id="ddorg" id="ddorg"  name="ddorg" maxlength="50" ></s:textfield>
										</td>
									 </tr>
								</table>
							</TD>
						</TR>
					</s:else>
				</table>
			</form>
	</body>
</html>