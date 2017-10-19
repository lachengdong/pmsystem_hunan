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
                <td id="dateid" >
                	减刑幅度：
                </td>
                <td>
                  <input id="jianxingyear" class="mini-combobox" vtype="maxLength:50;" emptyText="年" url="ajaxGetCode.json?codeType=GK056" textField="name" showNullItem="true"  valueField="codeid" style="width:80px;" popupHeight="150"/>
                </td>
                <td>
                	<input id="jianxingmonth" class="mini-combobox" vtype="maxLength:50;" emptyText="月" url="ajaxGetCode.json?codeType=GK057" textField="name" showNullItem="true"  valueField="codeid" style="width:80px;" popupHeight="150"/>
                </td>
                <td>
                   <input id="jianxingday" class="mini-combobox" vtype="maxLength:50;" emptyText="日" url="ajaxGetCode.json?codeType=GK058" textField="name" showNullItem="true"  valueField="codeid" style="width:80px;" popupHeight="150"/>
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
    	var jianxingyear = mini.get("jianxingyear").getValue();
    	var jianxingmonth = mini.get("jianxingmonth").getValue();
    	var jianxingday = mini.get("jianxingday").getValue();
    	var courtchange = "";
    	if(jianxingyear && (jianxingyear=='9997'||jianxingyear=='9996'||jianxingyear=='9995') ){
    		courtchange = jianxingyear;
    	}else{
    		if(!jianxingyear){
    			jianxingyear = "00";
    		}else if(jianxingyear.length == 1){
    			jianxingyear = "0"+jianxingyear;
    		}
    		//
    		if(!jianxingmonth){
    			jianxingmonth = "00";
    		}else if(jianxingmonth.length == 1){
    			jianxingmonth = "0"+jianxingmonth;
    		}
    		//
    		if(!jianxingday){
    			jianxingday = "00";
    		}else if(jianxingday.length == 1){
    			jianxingday = "0"+jianxingday;
    		}
    		//
    		courtchange = jianxingyear + jianxingmonth + jianxingday;
    	}
    	
    	var data = {courtchange:courtchange};
    	return data;
    }
    
    //标准方法接口定义
    function SetData(data) {
        data = mini.clone(data);
        if(data.courtchange){
        	var courtchange = data.courtchange;
        	if('9997'==courtchange ||'9996'==courtchange ||'9995'==courtchange){
        		mini.get("jianxingyear").setValue(courtchange); 
        	}else{
        		var jianxingyear = courtchange.substr(0,2);
        		if('00' != jianxingyear){
        			if('0' == jianxingyear.substr(0,1)){
        				jianxingyear = jianxingyear.substr(1,1);
        			}
        			mini.get("jianxingyear").setValue(jianxingyear); 
        		}
        		var jianxingmonth = courtchange.substr(2,2);
        		if('00' != jianxingmonth){
        			if('0' == jianxingmonth.substr(0,1)){
        				jianxingmonth = jianxingmonth.substr(1,1);
        			}
        			mini.get("jianxingmonth").setValue(jianxingmonth); 
        		}
        		
        		var jianxingday = courtchange.substr(4,2);
        		if('00' != jianxingday){
        			if('0' == jianxingday.substr(0,1)){
        				jianxingday = jianxingday.substr(1,1);
        			}
        			mini.get("jianxingday").setValue(jianxingday); 
        		}
        		
        	}
        }
    }
    
    
  	  //关闭窗口
    function onCancel(e) {
    	  CloseWindow("cancel");
     }
     
     function CloseWindow(action) {            
          if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
          else window.close();            
     }
</script>
</body>
</html>