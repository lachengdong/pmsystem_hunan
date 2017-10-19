<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
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
<body onload="myload()">     
    <form id="form1" method="post">
    	<input id="signatureScheme" class="mini-hidden" value="${signatureScheme}"/>
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>  
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr> 
                    <td width="80px;">签章方案：</td>
                     <td width="150px;">    
                          <input id="select2" class="mini-combobox"  showNullItem="true"
							     textField="text" valueField="value" allowInput="false" style="width:100%;" />
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
		function myload(){
			var signatureScheme = decodeURI(mini.get("signatureScheme").getValue());
			if(signatureScheme){
				signatureScheme = mini.decode(signatureScheme);
				mini.get('select2').setData(signatureScheme);
			}
		}
		
        //关闭 
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk2(e){
        	var value = mini.get('select2').getValue();
            var row = new Array([2]);
            row[0] = 1;
            row[1] = value;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        
        function onCancel(e) {
        	var row = new Array([1]);
            row[0] = 0;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        function SetData(data){
        	
        }
    </script>
</body>
</html>
