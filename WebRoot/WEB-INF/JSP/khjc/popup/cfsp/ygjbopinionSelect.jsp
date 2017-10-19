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
			<input id="title" name="title" class="mini-hidden" value=""/>
			<input id="popup_daystext" name="popup_daystext" class="mini-hidden" value=""/>
			<input id="popup_starttime" name="popup_starttime" class="mini-hidden" value=""/>
			<input id="popup_endtime" name="popup_endtime" class="mini-hidden" value=""/>
			<input id="popup_codeid" name="popup_codeid" class="mini-hidden" value=""/>
			<input id="popup_codename" name="popup_codename" class="mini-hidden" value=""/>
		</div>
		<table style="table-layout:fixed;">
                <tr>
                    <td width="100px;">奖罚意见：</td>
                     <td width="100px;">    
                          <input id="codeselect" class="mini-combobox" textField="name" valueField="codeid"  allowInput="false" style="width:100%;" 
                          	 url="ajaxCodeShuJuForPublicListPage.action?sctid=YGLB"
                          />
                    </td>
                    <td style="width:100px;;;" align="right">天数：</td>
					<td style="width:100px;;;">    
			        	 <input id="select2" class="mini-combobox" url="ajaxCodeShuJuForPublicListPage.action?sctid=CFTS" 
							  textField="name" valueField="codeid" allowInput="false" style="width:100%;"  onvaluechanged="select2changed"
						 />
			        </td>
                </tr>
                <tr>
                    <td width="100px;;">开始时间：</td>
                     <td width="100px;;">    
                          <input id="starttime" class="mini-datepicker" dateFormat="yyyy-MM-dd" style="width:100%;"/>
                    </td>
                    <td style="width:100px;" align="right">结束时间：</td>
					<td style="width:100px;">    
			        	  <input id="endtime" class="mini-datepicker" dateFormat="yyyy-MM-dd" style="width:100%;"/>
			        </td>
                </tr>
            </table>
     </div>
     
    <script type="text/javascript">
    
	    function onOk(){
	    	var result = "";
	    	var starttime = mini.get("starttime").getFormValue();
            var endtime = mini.get("endtime").getFormValue();
            var daystext = mini.get("select2").getText();
            var daysvalue = mini.get("select2").getValue();
	    	var objtext = mini.get("codeselect").getText();
	    	var objvalue = mini.get("codeselect").getValue();
	    	var title = "";
	    	if(objtext){
	    		if(objtext == "禁闭"){
	    			title="罪犯禁闭审批表";
	    		}else{
	    			title="罪犯严管审批表";
	    		}
	    	}
	    	
	    	if(daystext){
            	if(starttime && endtime){
            		var newstarttime = starttime.split("-");
            		var newendtime = endtime.split("-");
            		var str1 = newstarttime[0]+"年"+newstarttime[1]+"月"+newstarttime[2]+"日";
            		var str2 = newendtime[0]+"年"+newendtime[1]+"月"+newendtime[2]+"日";
            		if(objtext != "戒具"){
            			result = objtext+daystext+"处分(自"+str1+"至"+str2+"止)";
            		}else{
            			result = "加戴"+objtext+daystext+"(自"+str1+"至"+str2+"止)";
            		}
           		}else{
           			alert("请选择开始时间与结束时间!");
           			return false;
           		}
            }else{
            	if(objtext != "戒具"){
            		result = objtext+"处分";
            	}else{
            		result = "加戴"+objtext;
            	}
            }
	    	
        	if(result != "处分"){
        		var fjqSuggest = '　　根据该犯的违纪事实和《山东监狱狱政管理工作标准（试行）》第八章第二节的规定，经审查，情况属实，建议给予该犯';
        		var jySuggest =  '　　同意';
    	    	
    	    	mini.get("popup_text1").setValue(fjqSuggest+result+"。");
    	        mini.get("popup_text2").setValue(jySuggest+"。");
    	        mini.get("title").setValue(title);
    	        
    	        mini.get("popup_daystext").setValue(daystext);
    	        mini.get("popup_starttime").setValue(starttime);
    	        mini.get("popup_endtime").setValue(endtime);
    	        
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
