<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>保外就医案件办理中的意见弹出框</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    

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
<body onload="myload()">     
    <form id="form1" method="post">
    	<input class="mini-hidden" id="bwjy_startDate" value="${bwjy_startDate}"/>
    	<input class="mini-hidden" id="bwjy_endDate" value="${bwjy_endDate}"/>
    	<input class="mini-hidden" id="bwjy_unlimit" value="${bwjy_unlimit}"/>
    	<input class="mini-hidden" id="bwjy_select3" value="${bwjy_select3}"/>
    	<input class="mini-hidden" id="bwjy_fcrq" value="${fcrq}"/>
    	<!-- <input class="mini-hidden" id="bwjy_select3" value="${select3}"/> -->
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>  
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr> 
<!--                	<td> -->
<!--                    	&nbsp;<input id="unlimit" class="mini-checkbox" trueValue="1" falseValue="0" onvaluechanged="unlimitchanged"/>-->
<!--               		</td>-->
                     <td width="280px;">    
                    	&nbsp;&nbsp;开&nbsp;始&nbsp;日&nbsp;期：&nbsp; <input name="startDate" id="startDate" class="mini-datepicker"  dateFormat="yyyy-MM-dd" emptyText="请选择开始日期" style="width:60%;"/>
                    </td>
                </tr>
                <tr> 
                	<td width="280px;">    
                    	&nbsp;&nbsp;结&nbsp;束&nbsp;日&nbsp;期：&nbsp; <input name="endDate" id="endDate" class="mini-datepicker" dateFormat="yyyy-MM-dd" emptyText="请选择结束日期" style="width:60%;"/>
	                 </td>
                </tr>
               	<tr>
<!--               		<td> -->
<!--                    	&nbsp;<input id="fcrq" class="mini-checkbox" trueValue="1" falseValue="0" onvaluechanged="fcrqchanged"/>-->
<!--               		</td> -->
               		<td >
               			&nbsp;&nbsp;复&nbsp;查&nbsp;间&nbsp;隔：&nbsp;&nbsp;<input id="select3" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057"  showNullItem="true"
							        textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:60%;"/>
               		</td> 
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
		var startDate = mini.get("startDate");
		var endDate = mini.get("endDate");
		//var unlimit = mini.get("unlimit");
		//var fcrq = mini.get("fcrq");
		var select3 = mini.get("select3");
		//选中无限定结束日期触发事件 
		function myload(){
			 var bwjy_startDate = mini.get("bwjy_startDate").getValue();
			 var bwjy_endDate = mini.get("bwjy_endDate").getValue();
			 //var bwjy_unlimit = mini.get("bwjy_unlimit").getValue();
			 var bwjy_select3 = mini.get("bwjy_select3").getValue();
			 //var bwjy_fcrq = mini.get("bwjy_fcrq").getValue();
			 //回显
			 var strDate=bwjy_startDate.replace("年","-").replace("月","-").replace("日","");
			 var edDate=bwjy_endDate.replace("年","-").replace("月","-").replace("日","");
			 startDate.setValue(strDate);
			 if(bwjy_endDate=='0000'){
				 endDate.setValue();
			 	
			 }
			 else
			 {
			    endDate.setValue(edDate);
			 }
			 select3.setValue(bwjy_select3);
			// unlimit.setValue(1);
			// select3.disable();
			// fcrq.setValue(bwjy_fcrq);
			// select3.setValue(bwjy_select3);
			/* if(bwjy_select3==0||bwjy_select3==''){
			 	select3.disable();
			 	startDate.setEnabled(true);
			 	endDate.setEnabled(true);
			 }else{
			 	startDate.disable();
			 	endDate.disable();
			 	select3.setEnabled(true);
			 }*/
		}
		
      /*   function unlimitchanged() {
        	var unlimitValue = unlimit.getValue();
        	var fcrqValue = fcrq.getValue();
        	if(unlimitValue==1){
	        	if(fcrqValue==1){
	        		mini.alert("请单选！");
	        		unlimit.setValue(0);
	        		return;
	        	}
			 	select3.disable();
			 	startDate.setEnabled(true);
			 	endDate.setEnabled(true);
        	}else{
        		startDate.disable();
        		startDate.setValue('');
			 	endDate.disable();
			 	endDate.setValue('');
        	}
        }
         function fcrqchanged() {
        	var fcrqValue = fcrq.getValue();
        	var unlimitValue = unlimit.getValue();
        	if(fcrqValue==1){
	        	if(unlimitValue==1){
	        		mini.alert("请单选！");
	        		fcrq.setValue(0);
	        		return;
	        	}
			 	select3.setEnabled(true);
			 	startDate.disable();
			 	endDate.disable();
        	}else{
        		select3.disable();
        		select3.setValue('');
        	}
        }
        function formatDate(now) { 
			var year=now.getYear(); 
			var month=now.getMonth()+1; 
			var date=now.getDate();
			if(month<10){
				month="0"+month;
			}
			if(date<10){
				date="0"+date;
			}
			return year+"-"+month+"-"+date; 
		} 
		
		function NYRdateformat(now,yf){
			var year=now.getYear(); 
			var month=now.getMonth()+1; 
			var date=now.getDate();
			if(yf){
				if(parseInt(yf)+month>12){
					year = year+1;
					month = parseInt(yf)+month - 12;
				}else{
					month = parseInt(yf)+month;
				}
				if(month<10){
					month="0"+month;
				}
				if(date<10){
					date="0"+date;
				}
			}
			return year+"-"+month+"-"+date;
		}*/
		
        //确定 
        function onOk(){
        	var flag = validateBeforeOK();
        	if(flag==false){
        		return;
        	}
        	var startDateValue = mini.formatDate(mini.get("startDate").getValue(),"yyyy年M月d日");;
        	var endDateValue = mini.formatDate(mini.get("endDate").getValue(),"yyyy年M月d日");
        	//var unlimitValue = unlimit.getValue();
        	var select3Text = select3.text;
        	var select3Value = select3.getValue();
        	//var fcrqValue = fcrq.getValue();
        	var result = "";
        	   	result+="该犯保外就医起始日期为"+startDateValue+",";
        	if(endDateValue&&endDateValue!=''){
        		result+="结束日期为"+endDateValue+"。";
        	}else{
        		endDateValue = '0000';
        		result+="无限定期限。";
        	}   	
        	if(select3Value!=''||select3Value!=0){
        		//startDateValue = formatDate(new Date());
        		//endDateValue = NYRdateformat(new Date(),select3Value);
        		result+="每隔"+select3Text+"复查"+"。";
        	}
        	var row = new Array([6]);
            row[0] = mini.formatDate(mini.get("startDate").getValue(),"yyyyMMdd");
            row[1] = mini.formatDate(mini.get("endDate").getValue(),"yyyyMMdd");
            //row[2] = unlimitValue;
            row[3] = result;
            row[4] = select3Value;
           // row[5] = fcrqValue;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        
        //验证
        function validateBeforeOK(){
        	var flag = true;
        	var startDateValue = startDate.getValue();
        	var endDateValue = endDate.getValue();
        	//var unlimitValue = unlimit.getValue();
        	//var fcrqValue = fcrq.getValue();
        	var select3Value = select3.getValue();
        	var select3Text = select3.text;
        	//if(unlimitValue==1){
        		if(startDateValue==''||!startDateValue||startDateValue==null){
	        		mini.alert("开始时间不可为空!");
	        		return false;
        		}
        		if(endDateValue||endDateValue!=''){
		        	if(startDateValue>endDateValue){
		        		mini.alert("开始日期不能大于结束日期！ ");
		        		return false;
		        	}
	        	}
        	//}
        	/*else{
        		if(select3Value==''||select3Value==0||select3Text==''){
        			mini.alert("请选择月份！ ");
        			return  false;
        		}
        	}*/
        	return flag;
        }
        //关闭
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function SetData(data){
        	
        }
    </script>
</body>
</html>
