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
			<input id="popup_text5" name="popup_text5" class="mini-hidden" value=""/>
			<input id="popup_biddate" name="popup_biddate" class="mini-hidden" value=""/>
			<input id="popup_year" name="popup_year" class="mini-hidden" value=""/>
			<input id="popup_codeid" name="popup_codeid" class="mini-hidden" value=""/>
			<input id="popup_codename" name="popup_codename" class="mini-hidden" value=""/>
		</div>
		<td width="90px;">年度:
		<input id="year" name="year" class="mini-textbox" value=""/>
		</td>
		<hi>积极分子类型：</hi>
		<input id="codeselect" class="mini-combobox" url="ajaxCodeShuJuForPublicListPage.action?sctid=JJFZSJ" 
			textField="name" valueField="codeid" 
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
	    	var year = mini.get("year").getValue();
	    	
        	if(year && setduixiandate && objvalue){
        		var fjqSuggest = '　　建议评为';
        		var jqSuggest =  '　　建议评为';
        		var ksSuggest =  '　　建议评为';
        		var jySuggest =  '　　同意评为';
        		var SjSuggest =  '　　同意评为';
        		
    	    	mini.get("popup_text1").setValue(fjqSuggest+year+"年度"+objtext+"。");
    	        mini.get("popup_text2").setValue(jqSuggest+year+"年度"+objtext+"。");
    	        mini.get("popup_text3").setValue(ksSuggest+year+"年度"+objtext+"。");
    	        mini.get("popup_text4").setValue(jySuggest+year+"年度"+objtext+"。");
    	        mini.get("popup_text5").setValue(SjSuggest+year+"年度"+objtext+"。");
    	        mini.get("popup_biddate").setValue(setduixiandate);
    	    	mini.get("popup_codeid").setValue(objvalue);
    	    	mini.get("popup_codename").setValue(objtext);
    	    	mini.get("popup_year").setValue(year);
    	    	CloseWindow("ok");
        	}else{
        		alert("年度/积极分子类型/兑现日期必须填写！");
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
