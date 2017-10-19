<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>数据库管理dataImport.jsp</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=path %>/scripts/jquery-1.6.2.min.js"></script>
			<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
			
		<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: auto;
}

#center {
	margin: 50px auto;
	width: 400px;
}

.divbody
{
	z-index: 2;
	left: 2%;
	visibility: hidden;
	width: 98%;
	cursor: crosshair;
	position: absolute;
	top: 100px;
	height: 80%;
	background-color: #ffffcc;
}
p
{
	color: #cc6633;
	font-weight: bold;
}
.divprogress
{
	BORDER-RIGHT: black 1px solid; 
	PADDING-RIGHT: 3px; 
	BORDER-TOP: black 1px solid; 
	PADDING-LEFT: 3px; 
	FONT-SIZE: 10pt; 
	PADDING-BOTTOM: 2px; 
	BORDER-LEFT: black 1px solid; 
	PADDING-TOP: 2px; 
	BORDER-BOTTOM: black 1px solid
}

</style>
	<script type="text/javascript"><!--
        var progressEnd = 16; 
        var progressColor = "blue";
        var clearColor = "white";
        var progressInterval = 350;
        var progressBegin = 0;
        var progressTimer;
        function progress_clear() 
        {
           clearTimeout(progressTimer);
         // document.getElementById("framediv").style.visibility="hidden";
        }
        function progress_update() 
        {
            progressBegin++;
            if (progressBegin > progressEnd){
             progress_clear();
             progressBegin = 0;
             for(var i=1; i<=16; i++)
             document.getElementById("progress"+i).style.backgroundColor = "#ffffcc";
             
             }
            else 
            document.getElementById("progress"+progressBegin).style.backgroundColor = progressColor;
            progressTimer = setTimeout("progress_update()",progressInterval);
        }
        
	function dosubmit() {
		window.location.href = "addConnectionMessage.action";
	}
	
	function loading(){
      mini.mask({
       			el:document.body,
       			cls:'mini-mask-loading',
       			html:'文件上传中，请稍后...'
       		});
       	}
       		
	function tijiao() {
	
	//document.getElementById("form1");
	//document.getElementById("framediv").style.visibility="visible";
    //progress_update();
    
    //$("#uploadbtn").attr({"disabled": "disabled"});
    var path=document.getElementById("upload").value;
    var value=document.getElementById("ddcid").value;
    if(value==-1){
   		 alert("请选择导入方案!");
   		 return ;
    }else{
    	if(path!=''){
    		document.getElementById("form1").submit();
    		loading();
   		 }else{
    		alert("请选择要上传的文件！");
    		return ;
    }
	}
	}


	function download(){
		window.location.href = "queryTableInfoList.action?1=1&type=import";
	}

   </script>
	</head>
	<body>
	
		<s:form id="form1" action="baocunandyulanshuju" method="post"
			enctype="multipart/form-data" theme="simple">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
				<tr>
					<td colspan="2">
						<table id="Table1" cellspacing="1" cellpadding="4"
							class="mini-toolbar" width="100%">
							<tr align="center">
								<td height="10">
									选择数据方案:
									<s:select list="#request.dataslist" name="ddcid" id="ddcid"
										headerKey="-1" headerValue="请选择" listKey="#request.ddcid"
										listValue="#request.ddcname" cssStyle="width:180px;"></s:select>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="mini-button" onclick="download()" plain="true" style="width:80px">下载列表</a>
										
								</td>
							</tr>
							<tr>
								<td colspan="2" height="40" align="center">
									<div>
										从本地选择要上传的文件:
										<s:file name="upload" id="upload" label="选择文件" />
										<a class="mini-button" id="uploadbtn" iconCls="icon-upload" plain="true"
											onclick="tijiao()">上传</a>
									</div>
								</td>
							</tr>

						</table>
					</td>
				</tr>
			</table>
		</s:form>
		<div id="framediv" class="divbody" >
        <br />
        <p align="center">正在上传，请稍候...</p>
        <table align="center">
            <tr>        <td>
            <div class="divprogress"><span id="progress1">&nbsp;&nbsp;</span>
            <span id="progress2">&nbsp;&nbsp;</span> <span id="progress3">&nbsp;&nbsp;</span> 
            <span id="progress4">&nbsp;&nbsp;</span> <span id="progress5">&nbsp;&nbsp;</span>
            <span id="progress6">&nbsp;&nbsp;</span> <span id="progress7">&nbsp;&nbsp;</span>
            <span id="progress8">&nbsp;&nbsp;</span> <span id="progress9">&nbsp;&nbsp;</span>
            <span id="progress10">&nbsp;&nbsp;</span> <span id="progress11">&nbsp;&nbsp;</span>
            <span id="progress12">&nbsp;&nbsp;</span> <span id="progress13">&nbsp;&nbsp;</span>
            <span id="progress14">&nbsp;&nbsp;</span><span id="progress15">&nbsp;&nbsp;</span>
            <span id="progress16">&nbsp;&nbsp;</span>
            </div>
            </td>        </tr>
        </table>
    </div>
		<script type="text/javascript">
		   mini.parse();
		</script>

	</body>
</html>
