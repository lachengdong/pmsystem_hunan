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
				<input id="popup_crimclass" name="popup_crimclass" class="mini-hidden" value="" />			
				<input id="outputparam" name="outputparam" class="mini-hidden" value=""/>
				<input id="inputparam" name="inputparam" class="mini-hidden" value=""/>
				<input id="inputparamData" name="inputparamData" class="mini-hidden" value=""/>
				<input id="outputparamData" name="outputparamData" class="mini-hidden" value=""/>
				<input id="crimclasss" name="crimclasss" class="mini-hidden" value=""/>
				<input id="name" name="name" class="mini-hidden" value=""/>
				<div style="padding-left:11px;padding-bottom:5px;padding-right:11px;float:left;">
			         <table style="table-layout:fixed;">
						<tr>	
							<td colspan="5">                        
                           <input id="prisonerType" name="prisonerType" class="mini-combobox" onvaluechanged="TypeChanged"  showNullItem="false" 
                			url="ajaxListByMap.action?codetype=GK1101&codeids=17,18,19" valueField="codeid" textField="name"  style="width:185px;"/>
				             </td>
				            <td colspan="5" > 
				                <input id="typeDetail" name="typeDetail" class="mini-combobox" onvaluechanged="TypeDetailChanged"  showNullItem="false" 
                			 valueField="codeid" textField="name"  style="width:85px;"/>   
					     </td>
						</tr>
						<tr>
					        <td colspan="10">    
					                            如果犯罪认定类型为职务犯罪，请选择罪犯职务级别
					        </td>
			            </tr>
						<tr>
					        <td colspan="10" rowspan="3">    
					            <input  id="content" name="content" class="mini-textarea" allowInput="false" labelField="false" style="width:262px; height:250px"/>
					        </td>
			            </tr>
			        </table>
			    </div>
			</form>
		</div>
	<script type="text/javascript">
	mini.parse();
	var prisonerType = mini.get("prisonerType");
	var typeDetail = mini.get("typeDetail");
	var content = mini.get("content");
	var popup_crimclass = mini.get("popup_crimclass");
	var crimclasss = mini.get("crimclasss");
	
	typeDetail.disable();
	function TypeChanged(e){
		var contents = "" ;
		var  prisonerTypeInfo = prisonerType = mini.get("prisonerType").getValue();
		
		if(prisonerTypeInfo == "17"){
			
			contents =  mini.get("prisonerType").getText();
			content.setValue(contents);
			popup_crimclass.setValue(contents);	
			 var url="ajaxListByMap.action?codetype=GB018&codeids=6,7,8,9" ;
			 typeDetail.enable();
			 typeDetail.setUrl(url); 
			 typeDetail.select(0);
			 crimclasss.setValue(typeDetail.getText());
			 
			}
		else {
			 contents =  mini.get("prisonerType").getText();	
			 content.setValue(contents);
			 popup_crimclass.setValue(contents);
			 crimclasss.setValue("");
			 var url="ajaxListByMap.action?codetype=GB018&codeids=999" ;
			 typeDetail.disable();
			 typeDetail.setUrl(url);
			 
			}
		
		}

	   function TypeDetailChanged(e){
		  var detail = typeDetail.getText();
		  var  typeinfo = mini.get("prisonerType").getText();
		  content.setValue(typeinfo+":"+detail);
		  crimclasss.setValue(detail);
		}	    
	    function beforeOperate(){
	    content.setValue(mini.getValue("name"));
    	}
    		
	    
	    //请勿删此函数，此函数功能为在点击确定按钮的处理，
	    //业务逻辑由开发人员实现，也可空实现
	    function afterOperate(){
	        CloseWindow("ok");
	    	//可为空
	    }
    
</script>

</body>
</html>
