<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>数据库管理showCustomQuery.jsp</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
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
				var valueInfo=document.getElementById("valueInfo").value;
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
				document.getConnectionMessageList.action='getCustomQueryInfo.action';
				document.getConnectionMessageList.submit();
			}
			function jiaohuan(){
				document.getConnectionMessageList.action='shujujiaohuanAction.action';
				document.getConnectionMessageList.submit();
			}
			function qingkongWhereInfo(){
				document.getElementById("showInfo").innerHTML="";
				document.getElementById("condition").value="";
				document.getElementById("hiddencon").value="";
			}
		</script>
	</head>
	<body >
	<div style="border: 1px;border-color: blue">
			<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
			<s:hidden name="hiddencon" id="hiddencon"></s:hidden>
				<s:hidden name="ddcid" id="ddcid"></s:hidden>
				<s:hidden name="condition" id="condition"></s:hidden>
				<input type="hidden" name="totalnum" value='<s:property value="#request.totalnum"/>'/>
				<div class="mini-toolbar" >
				<table >
					<tr >
						<td height="10"  align="left" colspan="3">
						<span>选择条件：</span>
						<s:select list="#request.lielist" id="coulms"  name="coulms"  listKey="#request.dcdfromcolumns+'_'+#request.dcdfromcloumnstype+'_'+#request.dcdfromcolumnsscribe" listValue="#request.dcdfromcolumnsscribe" cssStyle="width:180px;"></s:select>
						<span id="logicsymbolDIV">
							<s:select  name="logicsymbol" id="logicsymbol" cssStyle="width: 80px;"	list="#{'=':'等于','<>':'不等于','like':'包含','>':'大于','<':'小于','>=':'大于等于','<=':'小于等于','in':'in'}" ></s:select>
						</span>
						<span id="dengyu" style="display: none;" style="width: 180px;">&nbsp;<b>等于</b></span>
						<span  id="inputspan"><input id="valueInfo" type="text" style="width: 180px;"/></span>
						<a class="mini-button" onclick="addWhereInfo()" plain="true" >添加条件</a>
						<a class="mini-button" onclick="qingkongWhereInfo()" plain="true" >清空条件</a>
						<a class="mini-button" onclick="chaxun()" plain="true" iconCls="icon-search">查询</a>
						<a class="mini-button" onclick="jiaohuan()" plain="true" iconCls="icon-reload" >数据交换</a>
						</td>
					</tr>
					<tr >
						<td width="30%" height="10"  align="left">
						<span>交换方式：</span>
						<s:radio list="#{'0':'覆盖交换','1':'新增交换'}" value='1' name="insertonly" id="insertonly"/>
						</td>
						<td width="40%" height="10"  align="left">
						<span class="separator"></span>
						查询条件：<span id="showInfo"></span>
						</td>
						<td width="30%" height="10"  align="left">
						<span class="separator"></span>
						数据总数：共<s:property value="#request.totalnum"/>条数据
						</td>
					</tr>
				</table>
				</div>
				<div class="mini-fit" style="height: 95%">
					<table  border="0" cellpadding="0" cellspacing="1" bgcolor="#99BCE8">
						<tr align="center" bgcolor="#D9E7F8">
							<s:iterator value="#request.titlelist" id="list6" status="st">
								<td style="white-space: nowrap;padding: 5px;"  align="center">
									<s:property value="#list6"/>
								</td>
							</s:iterator>
						</tr>
						<s:iterator value="#request.valuelist" id="list1" status="st2">
							<s:if test="#st2.modulus(#request.titlelist.size)==1">
								<tr align="center" bgcolor="#FFFFFF">
									<td style="white-space: nowrap;padding-left: 5px;padding-right: 5px;"  align="center"><s:property value="#list1"/></td>
							</s:if>
							<s:elseif test="#st2.modulus(#request.titlelist.size)==0">
									<td style="white-space: nowrap;padding-left: 5px;padding-right: 5px;"  align="center"><s:property value="#list1"/></td>
								</tr>
							</s:elseif>
							<s:else>
									<td style="white-space: nowrap;padding-left: 5px;padding-right: 5px;" align="center"><s:property value="#list1"/></td>
							</s:else>
						</s:iterator>
					</table>
				</div>
			</s:form>
			</div>
	</body>
</html>
