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
    <script src="<%=path%>/scripts/form/formPopUpBox.js" type="text/javascript"></script>

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
<!-- <%@ include file="/WEB-INF/JSP/common/miniToolbar.jsp"%>  -->
<jsp:include page="/WEB-INF/JSP/common/miniToolbar.jsp"></jsp:include>

<!--  -->
<!--  -->
<!--  -->
<!--  -->
<!-- 由开发人员编写开始 -->
     <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
     	<!-- 隐藏域：用于存放输入、输出的数据 -->
		<div id="div">
			<input id="output_parm" class="mini-hidden" />
		</div>
		<hi>请选择：</hi>
		<input id="codeselect" class="mini-combobox" url="" 
			textField="name" valueField="codeid" allowInput="false"   onvaluechanged=""
	 	/>
     </div>
     
    <script type="text/javascript">
	    
	    function beforeOperate(){
    		//在操作之的处理：
    		//如：用输入参数给控件设置等
    	}
    	
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
	    		tr += '<input id="'+outputparamarr[i]+'" name="'+outputparamarr[i]+'" class="mini-hidden" value="'+outputparamarr[i]+'"/>';
	    	}
	    	$("#div").before(tr);
	        mini.parse(tr);
	    }
	    
	    //请勿删此函数，此函数功能为在点击确定按钮的处理，
	    //业务逻辑由开发人员实现，也可空实现
	    function afterOperate(){
	    	//可为空
	    }
	    
	    /*
	    function onOk1(){
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
	    	onOk();
	    }*/
    </script>    
</body>
</html>
