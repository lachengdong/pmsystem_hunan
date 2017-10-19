<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>意见弹出框（弹出窗口）</title>
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
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;border-color: 1px solid;">
            <table style="table-layout:fixed;width:100%;">
                <tr> 
                    <td style="width:60px;">审批意见：</td>
                     <td >    
                          <input name="select1" id="select1" class="mini-combobox" textField="text" valueField="id"  allowInput="false"  showNullItem="true" emptyText="请选择审批意见" 
                          	onvaluechanged="select1changed" url="ajaxGetWindowSelect.json?&winid=${winid}&crimid=${crimid}" style="width:100%;"/>
                    </td>
                </tr>
				<tr>
	                <td colspan="2" rowspan="1">    
	                    <input  id="reason" name="reason" class="mini-textarea" emptyText="请填写审批意见" style="width:100%; height: 135px"/>
	                </td>
           		</tr>  
            </table>
        </div>
   </form>
    <script type="text/javascript">
        mini.parse();
		var s1 = mini.get("select1");
		var s2 = mini.get("reason");
        var form = new mini.Form("form1");
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
            var row = new Array([2]);
            row[0]= s2.getValue();//文本域取值
            row[1]= s1.getValue();
            window.returnValue = row;
            CloseWindow("cancel");
        }

		function select1changed(){
			mini.get("reason").setValue(s1.getText());
		}
         
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
        	
        }

    </script>
</body>
</html>
