<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
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
			<input id="liandate" name="liandate" class="mini-hidden" value=""/>
			<input id="popup_crimclass" name="popup_crimclass" class="mini-hidden" value=""/>
		</div>
		<table>
			<tr>
				<td>三类犯类型：</td>
				<td>
					<input id="crimeclass" name="crimeclass" class="mini-combobox" style="width:100px;" textField="text" valueField="id" 
    					data="crimetype"  showNullItem="true" allowInput="false" onvaluechanged="dealData"/>
				</td>
				<td>
					<input id="duty" name="duty" class="mini-combobox" style="width:100px;" textField="text" valueField="id" 
    					data="dutytype"  showNullItem="true" allowInput="false" onvaluechanged="dealDutyData"/>
				</td>
			</tr>
		</table>
     </div>
     
    <script type="text/javascript">
    	var crimetype = [{ id: '1G', text: '金融诈骗' }, { id: '1B', text: '黑社会性质'},{ id: '1D', text: '职务犯罪'},{ id: '1T', text: '其他'}];
    	var dutytype = [{ id: '1', text: '县处级' }, { id: '2', text: '厅局级'},{ id: '3', text: '省部级'}];
    	
    	function beforeOperate(){
    		//在操作之的处理：
    		//如：用输入参数给控件设置等
    		var value1 = mini.get("worktype").getValue();
    		var value2 = mini.get("liandate").getValue();
    		mini.get("crimeclass").setValue(value1);
    		mini.get("duty").setValue(value2);
    		if(!value1 || value1!='1D'){
    			mini.get("duty").setValue(null)
    			mini.get("duty").setEnabled(false);
    		}
    	}
    	
	    function dealData(){
	    	var value = mini.get("crimeclass").getValue();
	    	if(value && value == '1D'){
    			mini.get("duty").setEnabled(true);
    		}else{
    			mini.get("duty").setValue(null)
    			mini.get("duty").setEnabled(false);
    		}
    		
    		var text = mini.get("crimeclass").getText();
   			var popup_crimclass = "认定为："+text;
   			
   			mini.get("worktype").setValue(value);
    		mini.get("liandate").setValue("");
    		mini.get("popup_crimclass").setValue(popup_crimclass);
	    	
	    }
	    
	    function dealDutyData(){
	    	
	    	var value1 = mini.get("crimeclass").getValue();
	    	var text1 = mini.get("crimeclass").getText();
	    	
	    	var value2 = mini.get("duty").getValue();
	    	var text2 = mini.get("duty").getText();
	    	
	    	mini.get("worktype").setValue(value1);
    		mini.get("liandate").setValue(value2);
    		
    		var popup_crimclass = "认定为：" + text2 + text1;
    		
    		mini.get("popup_crimclass").setValue(popup_crimclass);
	    	
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
