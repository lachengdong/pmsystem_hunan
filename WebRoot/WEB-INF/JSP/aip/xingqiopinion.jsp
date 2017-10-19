<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>刑期选择</title>
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
    	<input id="year" class="mini-hidden" value="${yjmap.year}"/>
    	<input id="month" class="mini-hidden" value="${yjmap.month}"/>
    	<input id="day" class="mini-hidden" value="${yjmap.day}"/>
    	
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
           <a class="mini-button" onclick="CloseWindow"  plain="true" style="width:60px;">取消</a>
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table>
            	<tr>
            		<td>请选择刑期：</td>
                    <td width="90px;">    
                    	<input id="select2" class="mini-combobox" url="ajaxGetCode.json?codeType=GK056"  showNullItem="true" emptyText="选择年"
							textField="name" valueField="codeid" allowInput="false" style="width:100%;height:30px;" showNullItem="true"  onvaluechanged="select2changed"/>
                    </td>
                    <td width="90px;">
                    	<input id="select3" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057"  showNullItem="true" emptyText="选择月"
							textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;height:30px;"/>
                    </td>
                    <td width="90px;">
                   		<input id="select4" class="mini-combobox" url="ajaxGetCode.json?codeType=GK058"  showNullItem="true" emptyText="选择日"
							textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;height:30px;"/>
                    </td>
            	</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
		var s2 = mini.get("select2");
		var s3 = mini.get("select3");
		var s4 = mini.get("select4");
		
        function myload(){
	        //如果页面传值过来，把值填充到列表框
	        var year = mini.get("year").getValue();
	        var month = mini.get("month").getValue();
	        var day = mini.get("day").getValue();
	        if(parseInt(year)>100){
	        	s3.setValue();
        		s4.setValue();
        		s3.setEnabled(false);
        		s4.setEnabled(false);
	        	mini.get("select2").setValue(year);
	        }else{
	        	if(year){
	        		mini.get("select2").setValue(parseInt(year));
	        	}
	        	if(month){
	        		mini.get("select3").setValue(parseInt(month));
	        	}
	        	if(day){
	        		mini.get("select4").setValue(parseInt(day));
	        	}
	        }
	    }
	    
	    function onOk(e) {
	    	var row = new Array([4]);
	        var resultValue = "";
	        var year = s2.getValue();
	        var month = s3.getValue();
	        var day = s4.getValue();
	        
	        if(parseInt(year)>100){
	        	resultValue = s2.getText();
	        }else{
	        	if(!year)  year = "0";
	        	resultValue += s2.getText();
	        	
	        	if(!month)  month = "0";
	        	resultValue += s3.getText();
	        	
	        	if(!day)  day += "0";
	        	resultValue += s4.getText();
	        }
	        if(!month){month = "0";}
	        if(!day){day = "0";}
            row[0] = resultValue;//刑期描述
            row[1] = year;//年
            row[2] = month;//月
            row[3] = day;//日
            window.returnValue = row;
            CloseWindow("cancel");
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
         
       	function select2changed() {
        	var s2v = s2.getValue();
        	if(parseInt(s2v) > 25){ //无期，死缓，死刑
        		s3.setValue();
        		s4.setValue();
        		s3.setEnabled(false);
        		s4.setEnabled(false);
        	}else{
        		s3.enable();
        		s4.enable();
        	}
        }
        
    </script>
</body>
</html>
