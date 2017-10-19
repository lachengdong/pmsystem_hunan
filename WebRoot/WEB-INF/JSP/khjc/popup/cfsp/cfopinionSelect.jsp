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
			<input id="popup_codeid" name="popup_codeid" class="mini-hidden" value=""/>
			<input id="popup_codename" name="popup_codename" class="mini-hidden" value=""/>
		</div>
		<table style="table-layout:fixed;">
                <tr>
                    <td width="100px;">奖罚意见：</td>
                     <td width="100px;">    
                          <input id="codeselect" class="mini-combobox" textField="name" valueField="codeid"  allowInput="false" style="width:100%;" 
                          	 url="ajaxCodeShuJuForPublicListPage.action?sctid=CFLB"
                          />
                    </td>
                </tr>
            </table>
     </div>
     
    <script type="text/javascript">
    
	    function onOk(){
	    	var result = "";
	    	var objtext = mini.get("codeselect").getText();
	    	var objvalue = mini.get("codeselect").getValue();
	    
           	if(objtext){
           		result = objtext+"处分";
           	}
	    	
        	if(result != "处分"){
        		var fjqSuggest = '　　根据该犯的违纪事实和《中华人民共和国监狱法》第五十八条的规定，经分监区警察集体评议，建议给予该犯';
        		var jqSuggest =  '　　经审查，情况属实，建议给予该犯';
        		var ksSuggest =  '　　经审核，建议给予';
        		var jySuggest =  '　　同意给予';
    	    	
    	    	mini.get("popup_text1").setValue(fjqSuggest+result+"。");
    	        mini.get("popup_text2").setValue(jqSuggest+result+"。");
    	        mini.get("popup_text3").setValue(ksSuggest+result+"。");
    	        mini.get("popup_text4").setValue(jySuggest+result+"。");
    	        
    	    	mini.get("popup_codeid").setValue(objvalue);
    	    	mini.get("popup_codename").setValue(objtext);
    	    	CloseWindow("ok");
        	}else{
        		alert("请选择处罚意见！");
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
