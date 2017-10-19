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
			
			<input id="popup_duixiandate" name="popup_duixiandate" class="mini-hidden" value=""/>
			<input id="popup_codeid" name="popup_codeid" class="mini-hidden" value=""/>
			<input id="popup_codename" name="popup_codename" class="mini-hidden" value=""/>
		</div>
		<hi>请选择：</hi>
		<input id="codeselect" class="mini-combobox" url="ajaxCodeShuJuForPublicListPage.action?sctid=JLLB" 
			textField="name" valueField="codeid" allowInput="false" 
	 	/>
	 	<td width="90px;">兑现时间:</td>
        <td width="80px;">    
              <input id="setduixiandate" class="mini-datepicker" emptyText="选择兑现日期"  onenter="onKeyEnter" format="yyyy-MM"/>
        </td>
     </div>
     
    <script type="text/javascript">
    
	    function onOk(){
	    	var setduixiandate = mini.get("setduixiandate").getFormValue();
	    	var obj = mini.get("codeselect");
	    	var objtext = obj.getText();
	    	var objvalue = obj.getValue();
	    	
        	if(setduixiandate && objvalue){
        		var fjqSuggest = '　　根据该犯的现实表现和计分考核得分，依据《山东省罪犯计分考核奖罚规定》第十三条的规定，经分监区警察集体评议，建议给予该犯';
        		var jqSuggest =  '　　经审查，情况属实，建议给予该犯';
        		var ksSuggest =  '　　经审核，建议';
        		var jySuggest =  '　　同意';
    	    	
    	    	mini.get("popup_text1").setValue(fjqSuggest+objtext+"奖励。");
    	        mini.get("popup_text2").setValue(jqSuggest+objtext+"奖励。");
    	        mini.get("popup_text3").setValue(ksSuggest+objtext+"奖励。");
    	        mini.get("popup_text4").setValue(jySuggest+objtext+"奖励。");
    	        
    	        mini.get("popup_duixiandate").setValue(setduixiandate);
    	    	mini.get("popup_codeid").setValue(objvalue);
    	    	mini.get("popup_codename").setValue(objtext);
    	    	CloseWindow("ok");
        	}else{
        		alert("奖励类型和兑现日期必须填写！");
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
