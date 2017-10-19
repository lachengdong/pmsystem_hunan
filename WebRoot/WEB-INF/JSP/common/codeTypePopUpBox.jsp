<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>选择框</title>
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
<!-- 由开发人员编写开始 -->
<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	<input id="outputparam" name="outputparam" class="mini-hidden" value=""/>
     <table style="width:100%;">
         <tr>
             <td style="width:100%;">
              	<a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
      			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
     			</td>
             <td style="white-space:nowrap;">
             </td>
         </tr>
     </table>           
</div>
     <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
     	<!-- 隐藏域：用于存放输入、输出的数据 -->
		<div id="div">
			<input id="output_parm" class="mini-textbox" />
		</div>
		<hi>请选择：</hi>
		<input id="codeselect" class="mini-combobox" url="" 
			textField="name" valueField="codeid" allowInput="false"   onvaluechanged="valueChange();"
	 	/>
     </div>
     
    <script type="text/javascript">
    mini.parse();
	  //标准方法接口定义
	    function SetData(data) {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
            var inputparam =data.inputparam;
            var outputparam =data.outputparam;
            var inputarr = inputparam.split(",");
            mini.get("codeselect").setUrl("ajaxCodeShuJu.action?sctid="+inputarr);
            onload(outputparam);
	    }
	  

	    function onload(outputparam){
	    	mini.get("output_parm").setValue(outputparam);
	    	var outputparamarr = outputparam.split(",");
	    	var i;
	    	var tr="";
	    	for(i=0;i<outputparamarr.length;i++){
	    		tr += '<input id="'+outputparamarr[i]+'" name="'+outputparamarr[i]+'" class="mini-textbox" value="'+outputparamarr[i]+'"/>';
	    	}
	    	$("#div").before(tr);
	        mini.parse(tr);
	    }
	    
	    function valueChange(){
	    	var outparamarr = mini.get("output_parm").getValue().split(",");
	    	var codename = mini.get("codeselect").getText();
	    	var  codeid = mini.get("codeselect").getValue();
	    	var row = new Array([2])
	    	row[0] = codeid;
	    	row[1] = codename;
	    	var i;
	    	for(i=0;i<outparamarr.length;i++){
	    		mini.get(outparamarr[i]).setValue(row[i]);
	    	}
	    	window.close();
	    }
	    
	    
	    function GetData(){
	        var outputparam = mini.get("outputparam").getValue();
	        var outputparamArr = outputparam.split(',');
	        var data = {};
	        for(var i=0,l=outputparamArr.length;i<l;i++){
	        	var key = outputparamArr[i];
	        	data[key] = mini.get(key).getValue();
	        }
	        alert(data);
	        return data;
	    }

	       
	    function onOk(){
	        CloseWindow("ok");
	    }

	    function onCancel(){
	        CloseWindow("cancel");
	    }

	    //////////////////////////////////
	    function CloseWindow(action){
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }

    </script>    
</body>
</html>
