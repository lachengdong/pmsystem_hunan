<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>数据库管理databaselist.jsp</title>
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
			String.prototype.trim = function() {
			  return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
			}	    
			function addWhereInfo(){
				var logicsymbol=document.getElementById("logicsymbol");
				var coulms=document.getElementById("coulms").value;
				var strs= new Array(); //
		 		strs=coulms.split("_"); //0列名字1列类型2列描述
		 		var con=""
		 		var hiddencon=document.getElementById("condition").value;
		 		//得到逻辑运算符
		 		var showLog="";
				for(var i=0;i<logicsymbol.length;i++){
					if(logicsymbol[i].selected){
						showLog=logicsymbol[i].innerText;
					}
				}
				var valueInfo=document.getElementById("valueInfo").value.trim();
				if(hiddencon==""||hiddencon==undefined)
				{
					if('like'==logicsymbol.value){
						hiddencon=" a."+strs[0]+' '+logicsymbol.value+" '%"+valueInfo+"%' ";
					}
					else if('in'==logicsymbol.value){
						hiddencon=" a."+strs[0]+' '+logicsymbol.value+" ("+valueInfo+") ";
					}else{
						hiddencon=" a."+strs[0]+' '+logicsymbol.value+" '"+valueInfo+"' ";
					}
				}else{
					if('like'==logicsymbol.value){
						hiddencon=hiddencon+"and a."+strs[0]+' '+logicsymbol.value+" '%"+valueInfo+"%' ";
					}
					else if('in'==logicsymbol.value){
						hiddencon=hiddencon+"and a."+strs[0]+' '+logicsymbol.value+" ("+valueInfo+") ";
					}else{
						hiddencon=hiddencon+"and a."+strs[0]+' '+logicsymbol.value+" '"+valueInfo+"' ";
					}
				}
				con=" "+strs[2]+' '+showLog+" "+valueInfo+" ";
				document.getElementById("showInfo").innerHTML+="</br>"+con;
				document.getElementById("condition").value=hiddencon;
			}
			function chaxun(){
				document.getConnectionMessageList.action='queryTableInfo.action';
				document.getConnectionMessageList.submit();
			}
			function qingkongWhereInfo(){
				document.getElementById("showInfo").innerHTML="";
				document.getElementById("condition").value="";
				document.getElementById("hiddencon").value="";
			}
			function daochu(){
				document.getConnectionMessageList.action='getTableInfo.action?operate=daochu&operatetype=daochu';
				document.getConnectionMessageList.submit();
			}
			
			function fayuanCommit(type){
				document.getConnectionMessageList.action='getTableInfo.action?operate=daochu&operatetype=' + type;
				document.getConnectionMessageList.submit();
			}
			
			function download(){
			  window.location.href = "queryTableInfoList.action";
			}
			
			function goback(url){
			   window.location.href = url;
			}
			 function onValueChanged(e) {
	            var checked = this.getChecked();
	            if (checked){
	            	document.getElementById("packageper").value='1';
	            } else {
	            	document.getElementById("packageper").value='0';
	            }
	          }
		</script>
	</head>
	<body >
		<s:form id="getConnectionMessageList" name="getConnectionMessageList"	theme="simple">
			<s:hidden name="basetype" id="basetype"></s:hidden>
			<s:hidden name="ddcid" id="ddcid"></s:hidden>
			<s:hidden name="hiddencon" id="hiddencon"></s:hidden>
			<s:hidden id="packageper" name="packageper" value='0'></s:hidden>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
				<tr>
					<td colspan="2">
						<table id="Table1" width="100%" >
							<tr>
								<td  height="30" colspan="2" style="width:100%">
									&nbsp;选择导出列&nbsp;&nbsp;&nbsp;<input name="chkpackageper" id="chkpackageper" class="mini-checkbox" text="按罪犯分包导出" onvaluechanged="onValueChanged" value="0" trueValue="1" falseValue="0" />
								<!-- </td>
								<td align="left" width="80%" > -->&nbsp;&nbsp;&nbsp;
									<a class="mini-button" onclick="chaxun()" plain="true" style="width:40px">查询</a>
									<a class="mini-button" onclick="daochu()" plain="true" style="width:40px">导出</a>
									<a class="mini-button" onclick="fayuanCommit('fayuan')" plain="true" style="width:80px">提交法院</a>
									<a class="mini-button" onclick="addWhereInfo()" plain="true" style="width:80px">添加条件</a>
									<a class="mini-button" onclick="qingkongWhereInfo()" plain="true" style="width:80px">清空条件</a>
									<a class="mini-button" onclick="download()" plain="true" style="width:80px">下载列表</a>
									<a class="mini-button" onclick="goback('getDatasChemeNew.action?1=1');" plain="true" style="width:80px">返回</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="width:100%">
						<table id="Table1" border="1">
							<tr>
								<td colspan="2" height="40"
									align="center">
									<table  cellspacing="0"
										rules="all" id="Datagrid1"
										style="width:100%">
										<tr>
											<td width="25%" align="center">
												<s:select list="#request.lielist"  name="coulms"  id="coulms" listKey="#request.dcdfromcolumns+'_'+#request.dcdfromcloumnstype+'_'+#request.dcdfromcolumnsscribe" listValue="#request.dcdfromcolumnsscribe" cssStyle="width:180px;"></s:select>
											</td>
											<td width="25%" align="center">
												<span id="logicsymbolDIV">
													<s:select  name="logicsymbol" id="logicsymbol" cssStyle="width: 180px;"	list="#{'=':'等于','<>':'不等于','like':'包含','>':'大于','<':'小于','>=':'大于等于','<=':'小于等于','in':'in'}" ></s:select>
												</span>
											</td>
											<td width="25%" align="center">
												<!-- 普通 -->
												<span  id="inputspan">
													<input id="valueInfo" type="text" style="width: 180px;"/>
												</span>
											</td>
										</tr>
										<tr >
											<td height="10" colspan="4" align="left">
												查询条件
											</td>
										</tr>
										<tr>
											<td colspan="4" align="left">
												<div id="showInfo"></div>
												<s:hidden name="condition" id="condition"></s:hidden>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
		           	<td  colspan="2">
		           		共<s:property value="#request.totalnum"/>条数据
		           	</td>
		        </tr>
				<tr height="100%">
		           	<td  colspan="2" width="100%">
						<table  id="Table3" width="100%" height="100%">
							<tr>
								<s:iterator value="#request.titlelist" id="list6" status="st">
									<td >
										<s:property value="#list6"/>
									</td>
								</s:iterator>
							</tr>
							<s:iterator value="#request.valuelist" id="list1" status="st2">
								<s:if test="#st2.modulus(#request.titlelist.size)==1">
									<tr >
										<td >
											<s:property value="#list1"/>
										</td>
								</s:if>
								<s:elseif test="#st2.modulus(#request.titlelist.size)==0">
										<td >
											<s:property value="#list1"/>
										</td>
										</tr>
								</s:elseif>
								<s:else>
										<td >
											<s:property value="#list1"/>
										</td>
								</s:else>
							</s:iterator>
					</table>
				</td>
		       </tr>
			</table>
		</s:form>
	</body>
</html>
