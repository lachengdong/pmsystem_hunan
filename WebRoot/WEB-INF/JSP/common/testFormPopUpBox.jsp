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
			<input id="popup_test" name="popup_test" class="mini-hidden" value=""/>
			<input id="abc" name="abc" class="mini-hidden" value=""/>
			<input id="cdf" name="cdf" class="mini-hidden" value=""/>
		</div>
		
		<!-- popup_test为点击弹出框的节点
			 业务要求：将abc节点的值加上textbox上的值符到cdf节点上。同时设popup_test节点的值为"你会了吗？"
		 -->
		 <input id="aaa" name="aaa" class="mini-textbox" value=""/>
		<a class="mini-button" iconCls="icon-edit" onclick="dealData">处理业务数据</a>
     </div>
     
    <script type="text/javascript">
    	function beforeOperate(){
    		//在操作之的处理：
    		//如：用输入参数给控件设置等
    	}
    	
	    //示例：通过传递过来的输入参数，计算出输出参数的值，并放入隐藏域里！
	    function dealData(){
	    	var abc = mini.get("abc").getValue();
	    	var aaa = mini.get("aaa").getValue();
	        var popup_test = "你会了吗？";
	    	//计算出输出参数的值放入隐藏域里！
	    	mini.get("cdf").setValue(abc+aaa);
	    	mini.get("popup_test").setValue(popup_test);
	    }
	    
	    //请勿删此函数，此函数功能为在点击确定按钮的处理，
	    //业务逻辑由开发人员实现，也可空实现
	    function afterOperate(){
	    	//可为空
	    }
	    
    </script>
<!-- 由开发人员编写结束 -->
<!--  -->
<!--  -->
<!--  -->
<!--  -->

    
</body>
</html>
