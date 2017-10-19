<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>案件办理中的意见弹出框</title>
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
<body >     
    <form id="form1" method="post">
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >               
           <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>       
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr>
                     <td width="90px;">文书类型：</td>
                     <td width="80px;">    
                          <input id="schemetype"  name="schemetype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                        style="width:120px" emptyText="请选择文书类别" data="writType"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        var writType = [{id:'JXJS_JXJSSHB',text:'减刑假释审核表'},{id:'suggestreport',text:'减刑假释建议书'},{id:'casecheckreport',text:'监督意见书'}];

        mini.parse();
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk2(e) {
            var tree = mini.get("schemetype");
            var scheme = tree.getValue();
        	var row = new Array([1]);
        	row[0] = scheme;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
    </script>
</body>
</html>
