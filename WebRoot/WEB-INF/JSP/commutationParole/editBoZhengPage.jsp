<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
	<div id="form1" class="mini-toolbar"  style="padding:2px;border:0;" align="left">
		<a class="mini-button" iconCls=""  plain="true" onclick="onOk()" >确定</a>
		<a class="mini-button" iconCls="" plain="true" onclick="onCancel()">返回</a>	
	</div><br/>
	<div id="form2">
	    <table>
	        <tr>
                <td >
                	剥夺政治权利：
                </td>
                <td colspan="3">
                  <input id="losepower" class="mini-combobox" url="ajaxGetCode.json?codeType=GK059" showNullItem="true" vtype="maxLength:50;"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" popupHeight="120"/>
                </td>
	        </tr>
	    </table>
	</div>
<script type="text/javascript">
    mini.parse();
    
	function onOk(){
		CloseWindow("ok");
	}
    
    function GetData(){
    	var losepower = mini.get("losepower").getValue();
    	var data = {losepower:losepower};
    	return data;
    }
    
    //关闭窗口
     function onCancel(e) {
   		CloseWindow("cancel");
     }
    function CloseWindow(action) {            
         if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
         else window.close();            
     } 
    
    //标准方法接口定义
    function SetData(data) {
        data = mini.clone(data);
        mini.get("losepower").setValue(data.losepower); 
    }
</script>
</body>
</html>