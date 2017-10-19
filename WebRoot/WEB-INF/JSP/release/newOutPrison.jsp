<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String menuid = request.getParameter("menuid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯离监</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
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
   	<input  id="operate" name="operate" class="mini-hidden"/>
   	<input  id="crimid" name="crimid" class="mini-hidden" value="${crimid}"/>
    <form id="form1" method="post">
    	<div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >               
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>       
        </div>
        <div style="padding-left:2px;padding-bottom:5px;">
            <table style="table-layout:fixed;" align="center" >
            	<tr>
            		<td style="width:70px;">档案号：</td>
                    <td style="width:150px;">    
                        <input id="dah" name="dah" class="mini-textbox"  style="width:180px;"   vtype="maxLength:8;minLength:1;"  required="true" />
                    </td>
            	</tr>
            	<tr>
            		<td style="width:70px">离监类别：</td>
            		<td style="width:150px">
            			<input id="ljlb" name="ljlb" class="mini-combobox" valueField="noid" textField="name" 
                           url="ajaxCodeShuJu.json?codetype=GB032" required="true"     style="width:180px;" />
            		</td>
            	</tr>
            	<tr>
            		<td style="width:70px;">离监日期：</td>
                    <td style="width:150px;">    
                       	<input id="outprisondate" name="outprisondate"  class="mini-datepicker"    style="width:180px;" />
                    </td>
                 </tr>
            	<tr>
                    <td style="width:70px;">离监去向：</td>
                    <td style="width:150px;">    
                       <input id="qxqx" name="qxqx" class="mini-textbox"  style="width:180px;"/>
                    </td>
            	</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        function SaveData() {
        	var crimid = mini.get("crimid").value;
            var o = form.getData();            
            form.validate();
            if (form.isValid() == false) return;
            var json = mini.encode([o]);
            $.ajax({
                url: "saveOutPrison.action?1=1",
                data: { data: json,crimid: crimid },
                type: "post",
                cache: false,
                success: function (text) {
                    CloseWindow("save");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                    CloseWindow();
                }
            });
        	
        }
        
		//标准方法接口定义
        /* function SetData(data) {
        	var crimid = mini.get("crimid").Value;
        	 data = mini.clone(data);
             $.ajax({
                 url: "ajaxOutPrisonAddShuJu.action?id=" + data.id+"&crimid="+crimid,
                 cache: false,
                 type: 'POST',
                 success: function (text) {
                     var o = mini.decode(text);
                     form.setData(o);
                     form.setChanged(false);
                 }
             });
     	}*/
        function CloseWindow(action) {        
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
    </script>
</body>
</html>