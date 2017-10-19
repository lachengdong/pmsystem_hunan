<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>（ ningxia）保外就医案件办理中的意见弹出框</title>
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
    	<input class="mini-hidden" id="bwjy_select3" value="${select3}"/>
    	<!-- 选择节点strName ,节点内容strcontent,罪犯姓名cbiname -->
    	<input type="hidden" id="strName" value="${strName}"/>
    	<input type="hidden" id="strContent" value="${strContent }"/>
    	<input type="hidden" id="cbiname" value="${cbiname }"/>
    	
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
                    	 <input name="startDate" id="startDate" class="mini-hidden"  dateFormat="yyyy-MM-dd" emptyText="请选择开始日期" style="width:60%;"/>
                    </td>
                </tr>
                <tr> 
                    <!-- 文本内容 -->
                    
                	<td width="280px;">    
                    	 <input name="endDate" id="endDate"  class="mini-hidden" dateFormat="yyyy-MM-dd" emptyText="请选择结束日期" style="width:60%;"/>
	                 </td>
                </tr>
               	<tr>
<!--               		<td> -->
<!--                    	&nbsp;<input id="fcrq" class="mini-checkbox" trueValue="1" falseValue="0" onvaluechanged="fcrqchanged"/>-->
<!--               		</td> -->
               		<td >
               			<input id="select3" class="mini-hidden" url="ajaxGetCode.json?codeType=GK057"  showNullItem="true"
							        textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:60%;"/>
               		</td> 
                </tr>
            </table>
             &nbsp;&nbsp; 意&nbsp;见&nbsp;内&nbsp;容 ：&nbsp;<input name="bwjycontent" id="bwjycontent"  class="mini-textarea" style="width: 230px;height: 160px;"/>
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
		var strName = $("#strName").val();
		var strContent = $("#strContent").val();
		var cbiname = $("#cbiname").val();
		var jing = "经";
		//alert("strContent:"+strContent+",strName="+strName+",cbiname="+cbiname);
		//选中无限定结束日期触发事件 
		function myload(){
			 var bwjy_startDate = mini.get("bwjy_startDate").getValue();
			 var bwjy_endDate = mini.get("bwjy_endDate").getValue();
			 var bwjy_select3 = mini.get("bwjy_select3").getValue();
			 var datetime = new Date();
			 var reg=new RegExp("-","g"); 
			 //回显replace(reg, '')
			 startDate.setValue(bwjy_startDate);
			 if(bwjy_endDate=='0000'){
				 endDate.setValue();
			 }
			 select3.setValue(bwjy_select3);
			 var fjqSuggest = '监区警察集体讨论，同意罪犯'+cbiname+'予以监外执行。';
	 	 	 var jqSuggest = '监区长办公会会议研究根据《暂予监外执行规定》第**条和《保外就医严重疾病范围》第**条之规定，建议对罪犯'+cbiname+'提请暂予监外执行。';
	 		 var ksSuggest = '科务会议审查，罪犯'+cbiname+'符合《暂予监外执行规定》第**条之规定，建议对该犯暂予监外执行。';
	 	 	 var jySuggest = '监狱长办公会议研究，决定对罪犯'+cbiname+'提请暂予监外执行';
             if(strContent != ""){
            	 mini.get("bwjycontent").setValue(strContent);
             }else{
      			if(strName=='bwjyzhpgyj'){
	               	 fjqSuggest1 = '    '+jing+datetime.getFullYear()+"年"+(datetime.getMonth()+1)+"月"+datetime.getDate()+"日"+fjqSuggest; 
	               	 mini.get("bwjycontent").setValue(fjqSuggest1);
      			}
      			//监区
      			if(strName=='bwjyjqyj'){
      				fjqSuggest1 = '    '+jing+datetime.getFullYear()+"年"+(datetime.getMonth()+1)+"月"+datetime.getDate()+"日"+jqSuggest; 
	               	mini.get("bwjycontent").setValue(fjqSuggest1);
      			}
      			//科室
      			if(strName=='bwjyksyj'){
      				fjqSuggest1 = '    '+jing+datetime.getFullYear()+"年"+(datetime.getMonth()+1)+"月"+datetime.getDate()+"日"+ksSuggest; 
	               	mini.get("bwjycontent").setValue(fjqSuggest1);
      			}
      			//监狱
      			if(strName=='bwjyjyyj'){
      				fjqSuggest1 = '    '+jing+datetime.getFullYear()+"年"+(datetime.getMonth()+1)+"月"+datetime.getDate()+"日"+jySuggest; 
	               	mini.get("bwjycontent").setValue(fjqSuggest1);
      			}
             }
		}
		
      
		
        //确定 
        function onOk(){
        	//var flag = validateBeforeOK();
        	//if(flag==false){
        	//	return;
        	//}
        	//var startDateValue = mini.formatDate(mini.get("startDate").getValue(),"yyyy年M月d日");;
        	//var endDateValue = mini.formatDate(mini.get("endDate").getValue(),"yyyy年M月d日");
        	//var unlimitValue = unlimit.getValue();
        	//var select3Text = select3.text;
        	//var select3Value = select3.getValue();
        	//if(startDateValue==''||!startDateValue||startDateValue==null){
        		 //当不选择时间的时候那么 没有任何职返回
    		//}else{
            	var result = "ab";
    			var row = new Array([6]);
    			row[0]=mini.get("bwjycontent").getValue();
                //row[0] = startDateValue;
                //row[1] = '';
                //row[2] = unlimitValue;
                //row[3] = result;
                //row[4] = select3Value;
                //row[5] = fcrqValue;
                window.returnValue = row;
                CloseWindow("cancel");
            //}
        	//var fcrqValue = fcrq.getValue();
        	//result+="该犯保外就医起始日期为"+startDateValue+",";
        	//if(endDateValue&&endDateValue!=''){
        	//	result+="结束日期为"+endDateValue+"。";
        	//}else{
        	//	endDateValue = '0000';
        	//	result+="无限定期限。";
        	//}        	
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
