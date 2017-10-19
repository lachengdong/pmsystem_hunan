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
			<input id="popup_scope" name="popup_scope" class="mini-hidden" value=""/>
			<input id="popup_text1" name="popup_text1" class="mini-hidden" value=""/>
			<input id="popup_text2" name="popup_text2" class="mini-hidden" value=""/>
			<input id="popup_text3" name="popup_text2" class="mini-hidden" value=""/>
		</div>
		<hi>请填写：</hi>
		<input id="scope" class="mini-textbox" emptyText="请输入数字"  />  <br /><br />
     </div>
     
    <script type="text/javascript">
    
	    function onOk(){
	    	var scope = mini.get("scope").getValue();
	    	mini.get("popup_scope").setValue(scope);
	    	mini.get("popup_text1").setValue("　　经审查，情况属实，建议予以扣"+Chinese(scope)+"分。");
	    	mini.get("popup_text2").setValue("　　经审核，建议予以扣"+Chinese(scope)+"分。");
	    	mini.get("popup_text3").setValue("　　同意扣"+Chinese(scope)+"分。");
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
	    
	    /**
	    *
	    */
	   function Chinese(num) { 
	   	if(!/^\d*(\.\d*)?$/.test(num)) { 
	   		alert("你输入的不是数字，请重新输入!"); 
	   		return false; 
	   	} 
	   	var AA = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖"); 
	   	var BB = new Array("","拾","佰","仟","万","亿","点",""); 
	   	var a = (""+ num).split("."), k = 0, re = ""; 
	   	for(var i=a[0].length-1; i>=0; i--) { 
	   		switch(k) { 
	   			case 0 : re = BB[7] + re; 
	   				break; 
	   			case 4 : 
	   				if(!new RegExp("0{4}\\d{"+ (a[0].length-i-1) +"}$").test(a[0])) 
	   				re = BB[4] + re; 
	   				break; 
	   			case 8 : 
	   				re = BB[5] + re; 
	   				BB[7] = BB[5]; 
	   				k = 0; 
	   				break; 
	   		} 
	   		if(k%4 == 2 && a[0].charAt(i)=="0" && a[0].charAt(i+2) != "0") 
	   			re = AA[0] + re; 
	   		if((a[0].charAt(i) != 0)||(a[0].charAt(i) == 0&&a[0].length ==1)) 
	   			re = AA[a[0].charAt(i)] + BB[k%4] + re;
	   		k++; 
	   	} 
	   	if(a.length>1) { 
	   		re += BB[6]; 
	   		for(var i=0; i<a[1].length; i++) 
	   			re += AA[a[1].charAt(i)]; 
	   	} 
	   	return re; 
	   } 
    </script>
<!-- 由开发人员编写结束 -->
<!--  -->
<!--  -->
<!--  -->
<!--  -->

    
</body>
</html>
