<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>判决字号弹出框</title>
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
<body  onload="myload()">     
    <form id="form1" method="post">
    	<input id="caseno1" class="mini-hidden" value="${yjmap.caseno1}"/>
    	<input id="caseno2" class="mini-hidden" value="${yjmap.caseno2}"/>
    	<input id="caseno3" class="mini-hidden" value="${yjmap.caseno3}"/>
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>  
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr> 
                    <td >    
 						案件年份：<input name="select1" id="select1" class="mini-textbox" vtype="int"/>
                    </td>
                    <td>
               			案件简称：<input id="select2" class="mini-textbox" style="250px;"/>
               		</td>
                    <td>
                    	案件号：<input id="select3" class="mini-textbox" vtype="int"/>
                    </td>
               </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
		var s1 = mini.get("select1");
		var s2 = mini.get("select2");
		var s3 = mini.get("select3");
        var form = new mini.Form("form1");
	      
        function myload(){
	        //如果页面传值过来，把值填充到列表框
	        var caseno1 = mini.get("caseno1").getValue();
	        var caseno2 = mini.get("caseno2").getValue();
	        var caseno3 = mini.get("caseno3").getValue();
	        if(caseno1){
	        	mini.get("select1").setValue(caseno1);
	        	mini.get("select2").setValue(caseno2);
	        	mini.get("select3").setValue(caseno3);
	        }else{
	        	mini.get("select1").setValue(getYear());
		    }
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
        	
        	 var flag = form.isValid();
        	 if(flag){
        		 var row = new Array([4]);
            	 row[0] = s1.getValue();
                 row[1] = s2.getValue();
                 row[2] = s3.getValue();
                 row[3] = "（"+s1.getValue()+"）"+s2.getValue()+ "第" + s3.getValue() + "号";
            	 window.returnValue = row;
                 CloseWindow("cancel");
        	 }
        } 
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
        	
        }
        //获取当前年 字符串
        function getYear(){
			var now = new Date();
			return now.getFullYear();
		}
    </script>
</body>
</html>
