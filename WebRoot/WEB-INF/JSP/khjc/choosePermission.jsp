<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>权限选择</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:150%;
    }    
    fieldset
    {
        border:solid 1px #aaa;
    }        
    .hideFieldset
    {
        border-left:0;
        border-right:0;
        border-bottom:0;
    }
    .hideFieldset .fieldset-body
    {
        display:none;
    }
    .mini-checkboxlist-item
{
    display:inline-block;
    *display:inline;
    zoom:1;
    margin-right:30px;
}
.mini-checkboxlist label
{
    padding-left:2px;
    line-height:30px;
    display:inline-block;
}
    </style>
</head>
<body>
<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" >
    <div size="100%" showCollapseButton="true" style="overflow: scroll">
		<form id="form1" >
			 <tr>
			 	<td align="right">
			 		<a class="mini-button" onclick="onOk();" plain="true" style="width:60px">确定</a>       
           			<a class="mini-button" onclick="onCancel();"  plain="true" style="width:60px;">取消</a>
			 	</td>
			 </tr>
		    <fieldset id="fd2">
		    <legend><span><font color="blue">选择发送的权限</font></span></legend>
		        <div id="columnsnames1" name="columnsnames1" class="mini-checkboxlist" repeatItems="3" repeatLayout="table" url="ajaxGetPermissionName.action?1=1"
		         value="${permissionid}" textField="name" valueField="roleid" >
		        </div>
		    </fieldset>
    	</form>
    </div>
</div>
    <script type="text/javascript">
    	mini.parse();
        var form = new mini.Form("form1") ;
        
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
        	var obj = mini.get("columnsnames1");	
		    var row = new Array([2]);
		    row[0] = obj.getValue();
            window.returnValue = row;
            CloseWindow("cancel");
        }
</script>
</body>
</html>