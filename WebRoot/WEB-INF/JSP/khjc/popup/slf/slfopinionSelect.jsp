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

<div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
     	<!-- 隐藏域：用于存放输入、输出的数据 -->
		<div id="paramdata">
			<input id="popup_text1" name="popup_text1" class="mini-hidden" value=""/>
			<input id="popup_text2" name="popup_text2" class="mini-hidden" value=""/>
			<input id="popup_text3" name="popup_text3" class="mini-hidden" value=""/>
			<input id="popup_text4" name="popup_text4" class="mini-hidden" value=""/>
			<input id="popup_codeid1" name="popup_codeid1" class="mini-hidden" value=""/>
			<input id="popup_codename1" name="popup_codename1" class="mini-hidden" value=""/>
			<input id="popup_codeid2" name="popup_codeid2" class="mini-hidden" value=""/>
			<input id="popup_codename2" name="popup_codename2" class="mini-hidden" value=""/>
		</div>
		<hi>请选择：</hi>
		<input id="type" class="mini-combobox" url="ajaxCodeShuJuForPublicListPage.action?sctid=SLF" 
			textField="name" valueField="codeid" allowInput="false" 
	 	/>
	 	<hi>职务级别：</hi>
		<input id="level" class="mini-combobox" url="ajaxCodeShuJuForPublicListPage.action?sctid=ZHIJI" 
			textField="name" valueField="codeid" allowInput="false" 
	 	/>
     </div>
     
    <script type="text/javascript">
    
	    function onOk(){
	    	var type = mini.get("type");
	    	var typetext = type.getText();
	    	var typevalue = type.getValue();
	    	
	    	var level = mini.get("level");
	    	var leveltext = level.getText();
	    	var levelvalue = level.getValue();
        	if(typevalue){
        		var fjqSuggest = '';
        		if(levelvalue){
        			fjqSuggest = '　　依据中央政法委5号文件之规定，该犯属于'+typetext+'，'+leveltext+'，经审核，建议认定为“三类”罪犯。';
        		}else{
        			fjqSuggest = '　　依据中央政法委5号文件之规定，该犯属于'+typetext+'，经审核，建议认定为“三类”罪犯。';
        		}
        		
        		var jqSuggest =  '　　经审核，建议认定为“三类”罪犯。';
        		var ksSuggest =  '　　同意认定为“三类”罪犯。';
        		var jySuggest =  '　　同意认定为“三类”罪犯。';
    	    	mini.get("popup_text1").setValue(fjqSuggest);
    	        mini.get("popup_text2").setValue(jqSuggest);
    	        mini.get("popup_text3").setValue(ksSuggest);
    	        mini.get("popup_text4").setValue(jySuggest);
    	    	mini.get("popup_codeid1").setValue(typevalue);
    	    	mini.get("popup_codename1").setValue(typetext);
    	    	
    	    	mini.get("popup_codeid2").setValue(levelvalue);
    	    	mini.get("popup_codename2").setValue(leveltext);
    	    	CloseWindow("ok");
        	}else{
        		alert("三类犯类型必须选择！");
        	}
	    }

	    function onCancel(){
	        CloseWindow("cancel");
	    }

	    function CloseWindow(action){
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
    </script>
    
</body>
</html>
