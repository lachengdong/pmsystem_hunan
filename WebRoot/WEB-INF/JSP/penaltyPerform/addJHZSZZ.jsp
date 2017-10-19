<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>解回再审终止</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
    html, body{
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
    <form id="form1" method="post">
          <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >         
           <a class="mini-button" onclick="SaveData()" iconCls="icon-save" plain="true" style="width:60px">存盘</a>       
           <a class="mini-button" onclick="onCancel" iconCls="icon-close" plain="true" style="width:60px;">关闭</a>    
           <input id="flowsn" class="mini-hidden" value="${flowsn }"/>   
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table  style="width:100%;" border="0">
                 <tr>
                    <td style="width:20%;">终止时间：</td>
                    <td style="width:80%;">
                    	<input id="text2" name="text2" property="editor" class="mini-datepicker" emptyText="终止时间"    allowInput="true"  style="width:80%;" required="true"/>
					</td>
				 </tr>
				 <tr>
                    <td style="width:20%;">终止原因：</td>
                    <td style="width:80%;">
                    	<input id="text1" name="text1"  property="editor" class="mini-textarea" emptyText="终止原因"  allowInput="true"  style="width:80%;" required="true"/>
                    </td>
                 </tr>
                
            </table>
        </div>
    </form>
    <script type="text/javascript">
            mini.parse();
    		var form = new mini.Form("form1");
     	   function SaveData() {
    	    var flowsn = mini.get("flowsn").getValue();
        	var text1=mini.get("text1").getValue();
        	var text2=mini.get("text2").getValue();
        	form.validate();
            if (form.isValid() == false) return;
			$.ajax( {
					url : "updatezz.action",
					type : "POST",
					data : {
						flowsn:flowsn,
						text2 : mini.encode(text2),
						text1 : text1
					},
					success : function(text) {
						var temp = eval(text);
						if (temp == "success") {
							alert("保存成功");
							onCancel();
						} else {
							alert("保存失败");
						}
					},
					error: function (jqXHR, textStatus, errorThrown) {
			    	   alert("操作失败!");
			    	}
				});
	}
     
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
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
       function SetData(e){
       }
    </script>
</body>
</html>
