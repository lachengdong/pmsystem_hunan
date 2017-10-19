<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>数据库管理importdatabaseXML.jsp</title>
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
			//将下拉框中的值显示到文本框中
			function showDatasName(select){
				var input=document.getElementById("exportInput");
				for(var i=0;i<select.length;i++){
					if(select[i].selected){
						if(select[i].value!=0){
							input.value="";
							input.value=select[i].innerText;
						}else{
							input.value="";
							input.value="请选择下拉列表";
						}
					}
				}
			}

		function goaction(){
			var did=document.getElementById("exportSelect");
			var input=document.getElementById("exportInput").value;
			//if(input.equals("请选择下拉列表")){
			//	alert("请在下拉列表中选择数据方案");
			//	return false;
			//}else{
				for(var i=0;i<did.length;i++){
					if(did[i].selected){
						if(did[i].value!=0){
							var inn=did[i].innerText;
							if(inn==input){
								var docPath = document.getElementById("docPath").value;
								window.location.href="showCustomQuery.action?inPlinId="+did.value+"&docPath="+docPath;
								return true;
							}else{
								alert("方案选择错误");
								return false;
							}
						}else{
							alert("请在下拉列表中选择数据方案");
							return false;
						}
					}
				}
			//}
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
			var valueInfo=document.getElementById("valueInfo").value;
			if(hiddencon==""||hiddencon==undefined)
			{
				hiddencon=strs[0]+":"+logicsymbol.value+":"+valueInfo;
			}else{
				hiddencon=hiddencon+","+strs[0]+":"+logicsymbol.value+":"+valueInfo;
			}
			con=" "+strs[2]+' '+showLog+" "+valueInfo+" ";
			document.getElementById("showInfo").innerHTML+="</br>"+con;
			document.getElementById("condition").value=hiddencon;
			
		}
		function qingkongWhereInfo(){
			document.getElementById("showInfo").innerHTML="";
			document.getElementById("condition").value="";
			document.getElementById("hiddencon").value="";
		}
		
		function daochu(){
		//daorushujudaorushuju
			document.getConnectionMessageList.action="daorushujudaorushuju.action";
			document.getConnectionMessageList.submit();
		}
	</script>
	</head>
	<body>
		<s:form  id="getConnectionMessageList" name="getConnectionMessageList"  theme="simple">
			<s:hidden name="ddcid" id="ddcid"></s:hidden>
			<s:hidden name="hiddencon" id="hiddencon"></s:hidden>
			<s:hidden name="basePathfile" id="basePathfile"></s:hidden>
			<s:hidden name="totalnum" id="totalnum"></s:hidden>
			<table width="100%" border="1" cellspacing="0" cellpadding="0"  class="mini-toolbar">
				<tr>
					<td colspan="2">
						<table    id="Datagrid1"
							style="width: 100%; border-collapse: collapse">
							<tr ><!-- 
								<td height="10" colspan="5">
									选择条件
								</td>
							</tr>
							<tr>
								<td width="120" align="center">
									<s:select list="#request.lielist"  name="coulms"  id="coulms" listKey="#request.dcdfromcolumns+'_'+#request.dcdfromcloumnstype+'_'+#request.dcdfromcolumnsscribe" listValue="#request.dcdfromcolumnsscribe" cssStyle="width:180px;"></s:select>
								</td>
								<td width="120" align="center">
									<span id="logicsymbolDIV">
										<s:select  name="logicsymbol" id="logicsymbol" cssStyle="width: 180px;"	list="#{'=':'等于','<>':'不等于','like':'包含','>':'大于','<':'小于','>=':'大于等于','<=':'小于等于'}" ></s:select>
									</span>
								</td>
								<td width="120" align="center">
									
									<span  id="inputspan">
										<input id="valueInfo" type="text" style="width: 180px;"/>
									</span>
								</td>
								<td align="left">
									<a class="mini-button" onclick="addWhereInfo();" plain="true" style="width:80px">添加条件</a>
									<a class="mini-button" onclick="qingkongWhereInfo();" plain="true" style="width:80px">清空条件</a>
									<a class="mini-button" onclick="qingkongWhereInfo();" plain="true" style="width:40px">查询</a>
								</td>
								-->
									<a class="mini-button" onclick="daochu();" plain="true" style="width:40px">导入</a>
									<s:radio list="#{'0':'覆盖交换','1':'新增交换'}" value='1' name="insertonly" id="insertonly"/>
							</tr>
							<tr >
								<td height="10" colspan="5">
									查询条件
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="showInfo"></div>
									<s:hidden name="condition" id="condition"></s:hidden>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
		           	<td colspan="2">
		           		共<s:property value="#request.totalnum"/>条数据
		           	</td>
		        </tr>
				<tr>
		           	<td  colspan="2">
						<table id="Table3" cellspacing="1"
							cellpadding="4"  style="border-collapse:collapse">
								<tr  align="center">
									<s:iterator value="#request.titlelist" id="list6" status="st">
										<td >
											<s:property value="#list6"/>
										</td>
									</s:iterator>
								</tr>
								<tr>
								<s:iterator value="#request.valuelist" id="list1" status="st2">
									<s:if test="#st2.modulus(#request.titlelist.size)==1">
										<tr align="center">
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
		<script type="text/javascript">
			 mini.parse();
		</script>
	</body>
</html>