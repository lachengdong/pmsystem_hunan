<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Excel导出页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

    <style type="text/css">
	    html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        overflow:hidden; 
	    }
    </style>
</head>
<body>

<br /><br /><br />
<div align="center">
    <form id="upload">
	    <table>
		    <tr>
			    <td>
					    导出Excel方案：<input id="solutionid" class="mini-combobox" url="<%=path %>/solution/getSolutionSchemeTreeBySolutionpname.json?1=1&solutionname=excel" 
								textField="solutionname" width="150px;" valueField="solutionid" allowInput="false" />
				     <a class="mini-button" onclick="exportExcel" plain="false" iconCls="icon-download" >导出Excel</a>
			    </td>
		    </tr>
		</table>    
    </form>
<div class="" style="text-align: center;">
        <h3>操作说明</h3>
        <span>1、选择导出Excel方案</span><br />
        <span>2、点击导出Excel按钮</span><br />
        <span>3、弹出下载Excel提示</span><br />
    </div>
   </div>  
   <div style="display:none;">
   		<iframe id="excel_iframe"></iframe>
   </div>
   
    <script type="text/javascript">
	    function exportExcel(){
	    	var solutionid = mini.get("solutionid").getValue();
	    	var solutionname =  mini.get("solutionid").text;
	    	solutionname = encodeURI(encodeURI(solutionname));
			if(solutionid){
				var excel_iframe = document.getElementById('excel_iframe');
				excel_iframe.src="<%=path%>/excel/simpleExportExcel.json?1=1&solutionid="+solutionid+"&solutionname="+solutionname;
			}else{
				alert("请选择导出Excel方案！");
			}
	    }

	    function onCancel(){
	        CloseWindow("cancel");
	    }

	    function CloseWindow(action){
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	    
	    
    </script>
    
</body>
</html>
