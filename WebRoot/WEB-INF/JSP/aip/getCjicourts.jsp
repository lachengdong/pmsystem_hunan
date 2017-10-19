<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>判决法院弹出框</title>
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
    <form id="form1" method="post">
   		 <input id="panjuefayuan"  class="mini-hidden" value=""/>
   		 <input id="panjueshuhao"  class="mini-hidden" value=""/>
    	 <input id="panjueriqi"  class="mini-hidden" value=""/>
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>  
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
               <tr>
               		<td>
               		请选择判决法院：<input id="select7" class="mini-combobox" url="<%=path%>/getCjicourt.json?1=1&crimid=${crimid}"
						        textField="PANJUEFAYUAN" valueField="COURTSANCTION" allowInput="false" showNullItem="false"  onvaluechanged="select2changed" style="width:100%;" />
               		</td>
               </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        function select2changed(){
	        var courtsanction = mini.get("select7").getValue();
	       	var crimid = ${crimid};
	       	var url = "<%=path%>/getData4pk.json?1=1&crimid="+crimid+"&courtsanction="+courtsanction;
	       	$.ajax({
	       			 url: url,
                     type: "post",
                     success: function (text) {
                     var data = eval('('+text+')');
                     mini.get("panjuefayuan").setValue(data.PANJUEFAYUAN);
                     mini.get("panjueshuhao").setValue(data.ANJIANHAO);
                     mini.get("panjueriqi").setValue(data.COURTSANCTION);
                    },
                     error: function () {
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

        function onOk2(e) {
        	 var panjuefayuan = mini.get("panjuefayuan").getValue();
        	 var panjueshuhao = mini.get("panjueshuhao").getValue();
        	 var panjueriqi = mini.get("panjueriqi").getValue();
        	 if(!panjuefayuan||panjuefayuan==''||panjuefayuan==null){
        	 	mini.alert("请选择判决法院");
        	 	return;
        	 }
        	 var row = new Array([4]);
        	 row[0] = panjuefayuan;
             row[1] = panjueshuhao;
             row[2] = panjueriqi;
        	 window.returnValue = row;
             CloseWindow("cancel");
        } 
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
        	
        }
    </script>
</body>
</html>
