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
			<input id="popup_datetext" name="popup_datetext" class="mini-hidden" value=""/>
			<input id="popup_datevalue" name="popup_datevalue" class="mini-hidden" value=""/>
		</div>
		<hi>考核月份</hi>
		<td width="80px;">    
              <input id="dateselect" class="mini-datepicker" emptyText="选择兑现日期"  onenter="onKeyEnter" format="yyyy-MM"/>
        </td>
     </div>
     
    <script type="text/javascript">
    	//示例：值处理
	    function onOk(){
	    	var obj = mini.get("dateselect").getFormValue();
	    	if(obj){
	    		var datetext = obj.replace("-","年")+"月";
		    	var datevalue = obj.replace("-",""); 
		    	//计算出输出参数的值放入隐藏域里！
		    	mini.get("popup_datetext").setValue(datetext);
		    	mini.get("popup_datevalue").setValue(datevalue);
		    	CloseWindow("ok");
	    	}else{
	    		alert("请选择考核时间！");
	    	}
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
