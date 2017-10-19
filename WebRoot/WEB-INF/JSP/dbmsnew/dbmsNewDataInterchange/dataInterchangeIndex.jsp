<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>数据库管理dataInterchangeIndex.jsp</title>
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
     	 function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
                e.isValid = false;
                e.errorText = "输入值不在下拉数据中";
                return;
            }
            goaction();
          }
          function goaction(){
				var did=document.getElementById("exportSelect");
				var input=document.getElementById("exportInput").value;
				for(var i=0;i<did.length;i++){
					if(did[i].selected){
						if(did[i].value!=-1){
							var inn=did[i].innerText;
							if(inn==input){
								document.getElementById("dentalsub").src = "getCustomQueryInfo.action?ddcid="+did.value;
								document.getElementById("dentalsub").width='100%';
								document.getElementById("dentalsub").height='100%';
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
			}
		</script>
	</head>
	<body >
		<div class="mini-toolbar" style="padding:0px;">
			<table width="100%" >
				<tr>
					<td style="width:100%;">
				数据交换方案：<input id="combo1" class="mini-combobox" style="width:150px;" textField="ddcname" valueField="ddcid" emptyText="请选择..."
    url="downSchemaList.action" value="cn"  required="true" allowInput="true" showNullItem="true" nullItemText="请选择..."/>  
						数据交换方案：<input id="exportInput" name="exportschemename" value="请选择下拉列表" readonly="readonly" disabled="disabled"/>&nbsp;&nbsp;
                		<select id="exportSelect" onchange="showDatasName(this)">
                			<option value="-1">请选择</option>
                			<s:iterator value="#datascheme">
                				<option value="<s:property value="ddcid"/>"><s:property value="ddcname"/></option>
                			</s:iterator>
                		</select>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit" style="height: 93%;">
			<iframe id="dentalsub" src="" frameborder="0" scrolling="no"  height="100%">
			</iframe>
		</div>
	</body>
</html>