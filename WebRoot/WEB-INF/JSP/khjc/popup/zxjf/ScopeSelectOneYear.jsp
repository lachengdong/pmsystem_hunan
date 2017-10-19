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
			<input id="tablevalue4" name="tablevalue4" class="mini-hidden" value=""/>
			<input id="crimid" name="crimid" class="mini-hidden" value=""/>
			<input id="popup_scope" name="popup_scope" class="mini-hidden" value=""/>
			<input id="popup_text1" name="popup_text1" class="mini-hidden" value=""/>
			<input id="popup_text2" name="popup_text2" class="mini-hidden" value=""/>
			<input id="popup_text3" name="popup_text2" class="mini-hidden" value=""/>
		</div>
		<hi>填写分数：</hi>
		<input id="scope" class="mini-textbox" emptyText="请输入数字"  />  <br /><br />
		今年专项分<input id="sumjiangfen" class="mini-textbox"  enabled="false"/><br />
		今年累积分<input id="sumleijifen" class="mini-textbox" enabled="false" />
     </div>
     
    <script type="text/javascript">
    
    function beforeOperate(){
		//在操作之的处理：
		//如：用输入参数给控件设置等
		var date = mini.get("tablevalue4").getValue();
		var crimid = mini.get("crimid").getValue();
		searchScoreByCrimid(date,crimid);
	}
    
    	//查看罪犯连续12个月的累计分和专项分
    	function searchScoreByCrimid(date,crimid){
    		if(date && crimid){
    			$.ajax({
                    url: "<%=path%>/check/ajaxSearchScoreByCrimid.json?1=1",
                    data: {crimid:crimid,date:date,type:"yeartype"},
                    type: "post",
                    success: function (text) {
                    	var text2 = eval('('+text+')');
                    	mini.get("sumleijifen").setValue(text2.SUMLEIJIFEN);
                    	mini.get("sumjiangfen").setValue(text2.SUMJIANGFEN);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert(jqXHR.responseText);
                    }
                });
    		}else{
    			alert("请选择填表日期！");
    			CloseWindow("cancel");
    		}
    	}
    	
    	
	    function onOk(){
	    	var scope = mini.get("scope").getValue();
	    	var sumleijifen = mini.get("sumleijifen").getValue();
	    	var sumjiangfen = mini.get("sumjiangfen").getValue();
	    	if(scope != ""){
	    		if(parseFloat(scope)+parseFloat(sumjiangfen) <= 15){
		    		mini.get("popup_scope").setValue(scope);
			    	mini.get("popup_text1").setValue("　　经审查，情况属实，建议予以奖励"+Chinese(scope)+"分。");
			    	mini.get("popup_text2").setValue("　　经审核，建议予以奖励"+Chinese(scope)+"分。");
			    	mini.get("popup_text3").setValue("　　同意奖励"+Chinese(scope)+"分。");
			        CloseWindow("ok");
	    		}else{
	    			alert("专项奖分连续12个月不能超过15分！");
	    		}
	    	}else{
    			alert("请填写分数！");
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
