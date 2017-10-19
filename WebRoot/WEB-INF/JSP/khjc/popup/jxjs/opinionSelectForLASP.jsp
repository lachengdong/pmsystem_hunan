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
			<!-- 表头 -->
			<input id="jxojs" name="jxojs" class="mini-hidden" value=""/>
		    <!-- 分监区、监区、科室、监狱、省局意见栏 -->
			<input id="popup_text1" name="popup_text1" class="mini-hidden" value=""/>
			<input id="popup_text2" name="popup_text2" class="mini-hidden" value=""/>
			<input id="popup_text3" name="popup_text3" class="mini-hidden" value=""/>
			<input id="crimname" name="crimname" class="mini-hidden" value=""/>
		</div>
		<div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr> 
                    <td width="90px;">意见：</td>
                     <td width="80px;">    
                         <input class="mini-combobox"  id="yijianleixing"  name="yijianleixing"  style="width:80px;"   
                         emptyText="同意/不同意" value="1" data="[{ id: 1, text: '同意' }, { id: 2, text: '不同意'}]" onvaluechanged="onValueChanged" />
                    </td>
                </tr>
                <tr>
            </table>
        </div>
     </div>
     
    <script type="text/javascript">
    mini.parse();
   
	var jqSuggest1 =  '　　经审查，罪犯@计分考评、奖惩情况属实，起始时间符合法定条件，改造表现较好。拟同意立案。';
	var jqSuggest2 =  '　　经审查，罪犯@计分考评、奖惩情况不属实，起始时间符合法定条件，改造表现较差。不同意立案。';
	
	var ksSuggest1 =  '　　经审查，罪犯@符合立案条件，同意立案。';
	var ksSuggest2 =  '　　经审查，罪犯@不符合立案条件，不同意立案。';
	
	var csSuggest1 =  '同意立案。';
	var csSuggest2 =  '不同意立案。';
	
    function onOk(e) {
    	var yijian = mini.get("yijianleixing").getValue();
    	 var crimname = mini.get("crimname").getValue();
    	if(yijian == '1'){
    		jqSuggest1=jqSuggest1.replace("@",crimname);
    		ksSuggest1=ksSuggest1.replace("@",crimname);
    		
    		mini.get("popup_text1").setValue(jqSuggest1);
            mini.get("popup_text2").setValue(ksSuggest1);
            mini.get("popup_text3").setValue(csSuggest1);
        }else{
        	jqSuggest2=jqSuggest2.replace("@",crimname);
    		ksSuggest2=ksSuggest2.replace("@",crimname);
    		
        	mini.get("popup_text1").setValue(jqSuggest2);
            mini.get("popup_text2").setValue(ksSuggest2);
            mini.get("popup_text3").setValue(csSuggest2);
        }
    	
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
