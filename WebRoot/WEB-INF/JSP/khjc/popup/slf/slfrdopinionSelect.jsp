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
		<div size="70%" showCollapseButton="true">
			<form id= "paramdata">
				<input id="popup_codename1" name="popup_codename1" class="mini-hidden" value="" />			
				<input id="outputparam" name="outputparam" class="mini-hidden" value=""/>
				<input id="inputparam" name="inputparam" class="mini-hidden" value=""/>
				<input id="inputparamData" name="inputparamData" class="mini-hidden" value=""/>
				<input id="outputparamData" name="outputparamData" class="mini-hidden" value=""/>
				<input id="popup_text2" name="popup_text2" class="mini-hidden" value=""/>
				<input id="popup_text3" name="popup_text3" class="mini-hidden" value=""/>
				<input id="popup_text4" name="popup_text4" class="mini-hidden" value=""/>
				<div style="padding-left:11px;padding-bottom:5px;padding-right:11px;float:left;">
			         <table style="table-layout:fixed;">
						<tr>	
							<td colspan="5">                        
				                <div id="isagree" class="mini-radiobuttonlist" repeatItems="2" repeatLayout="table" repeatDirection="horizontal"
                                         textField="text" valueField="id" value="1" 
                                        data="[{ 'id': '1', 'text': '同意' },{ 'id': '0', 'text': '不同意' }]" >
                                </div>  
				             </td>
						</tr>
			        </table>
			    </div>
			</form>
		</div>
	<script type="text/javascript">
	    mini.parse();
        var isagree = mini.get("isagree");
        isagree.on("valuechanged", function (e) { 
            var prisonertype = mini.get("popup_codename1").getValue();
        if(this.getValue() == 1){
        	mini.get("popup_text2").setValue("同意该犯为"+prisonertype+"。");
        	mini.get("popup_text3").setValue("同意该犯为"+prisonertype+"。");
        	mini.get("popup_text4").setValue("同意该犯为"+prisonertype+"。");
            }
        else{
        	mini.get("popup_text2").setValue("不同意该犯为"+prisonertype+"。"); 
        	mini.get("popup_text3").setValue(""); 
        	mini.get("popup_text4").setValue("");         
                }
        });
	    
	    function beforeOperate(){
		    var prisonertype= mini.get("popup_codename1").getValue();
		    if(prisonertype == ""){
					alert("该罪犯未认定犯罪类型,请重新审批！");
					onCancel();
			    }
		    else{   
		    		mini.get("reason").setValue("同意该犯为"+prisonertype+"。");
		    		mini.get("popup_text2").setValue("同意该犯为"+prisonertype+"。");
		    		mini.get("popup_text3").setValue("同意该犯为"+prisonertype+"。");
		    		mini.get("popup_text4").setValue("同意该犯为"+prisonertype+"。");
		    }   	    
    	}
    		
	    function onOk(){
	    	var prisonertype = mini.get("popup_codename1").getValue();
	    	var isagree = mini.get("isagree").getValue();
	    	if(isagree == 1){
	    		mini.get("popup_text2").setValue("同意该犯为"+prisonertype+"。");
	        	mini.get("popup_text3").setValue("同意该犯为"+prisonertype+"。");
	        	mini.get("popup_text4").setValue("同意该犯为"+prisonertype+"。");
	    	}else{
	    		mini.get("popup_text2").setValue("不同意该犯为"+prisonertype+"。");
	        	mini.get("popup_text3").setValue("不同意该犯为"+prisonertype+"。");
	        	mini.get("popup_text4").setValue("不同意该犯为"+prisonertype+"。");
	    	}
    	    CloseWindow("ok");
	    }
    
</script>

</body>
</html>
