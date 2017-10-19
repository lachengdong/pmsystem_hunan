<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>表单弹出框</title>
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
		<div id="paramdata">
			<input id="popup_orgid" name="codeid" class="mini-hidden" value=""/>
			<input id="popup_orgname" name="codename" class="mini-hidden" value=""/>
		</div>
		
		<hi>请选择：</hi>
		<input id="orgselect" class="mini-combobox" url="ajaxGetOrginfoByLevel.action?unitlevel=4" 
			textField="name" valueField="orgid" allowInput="false"   onvaluechanged="onvaluechanged();"
	 	/>
     </div>
     
    <script type="text/javascript">
    	//示例：值处理
	    function onvaluechanged(){
	    	var obj = mini.get("orgselect");
	    	var objtext = obj.getText();
	    	var objvalue = obj.getValue();
	    	//计算出输出参数的值放入隐藏域里！
	    	mini.get("popup_orgid").setValue(objvalue);
	    	mini.get("popup_orgname").setValue(objtext);
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
<!-- 由开发人员编写结束 -->
<!--  -->
<!--  -->
<!--  -->
<!--  -->

    
</body>
</html>
