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
			<input id="worktype" name="worktype" class="mini-hidden" value=""/>
			<input id="popup_fjqopinion" name="popup_fjqopinion" class="mini-hidden" value=""/>
			<input id="popup_jqopinion" name="popup_jqopinion" class="mini-hidden" value=""/>
			<input id="popup_jykopinion" name="popup_jykopinion" class="mini-hidden" value=""/>
			<input id="popup_jyopinion" name="popup_jyopinion" class="mini-hidden" value=""/>
		</div>
		
		请选择奖励类型：
		<input id="jianglitype" name="jianglitype" class="mini-combobox" style="width:150px;" textField="text" valueField="id" 
    			data="jianglitype"  showNullItem="true" allowInput="false" onvaluechanged="dealData"/>
		
     </div>
     
    <script type="text/javascript">
    	var jianglitype = [{ id: '11', text: '监狱表扬' }, { id: '1D', text: '监狱改造积极分子'},{ id: '1E', text: '省局改造积极分子'}];
    	
    	function beforeOperate(){
    		//在操作之的处理：
    		//如：用输入参数给控件设置等
    		var value = mini.get("worktype").getValue();
    		mini.get("jianglitype").setValue(value);
    	}
    	
	    //示例：通过传递过来的输入参数，计算出输出参数的值，并放入隐藏域里！
	    function dealData(){
	    	var value = mini.get("jianglitype").getValue();
	    	var text = mini.get("jianglitype").getText();
	    	var fjqopinion = "　　建议给予" + text + "奖励。";
	    	var jqopinion = "　　建议给予" + text + "奖励。";
	    	var jykopinion = "　　建议给予" + text + "奖励。";
	    	var jyopinion = "　　同意给予" + text + "奖励。";
	    	
	    	mini.get("worktype").setValue(value);
	    	mini.get("popup_fjqopinion").setValue(fjqopinion);
	    	mini.get("popup_jqopinion").setValue(jqopinion);
	    	mini.get("popup_jykopinion").setValue(jykopinion);
	    	mini.get("popup_jyopinion").setValue(jyopinion);
	    }
	    
	    //请勿删此函数，此函数功能为在点击确定按钮的处理，
	    //业务逻辑由开发人员实现，也可空实现
	    function afterOperate(){
	    	//可为空
	    }
	    // 由开发人员编写结束  
    </script>
</body>
</html>
