<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>省局案件办理中的意见弹出框</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
    html, body{
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden; 
    }
    table tr{    
    	height:25px;    
    	line-height:25px;
    }
    </style>
</head>
<body  onload="myload()">     
 	<form id="form1" method="post">
 		<div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
 			<input id="jxjs0" class="mini-hidden" value="${yjmap.jxjs0}"/>
	    	<input id="jxjs1" class="mini-hidden" value="${yjmap.jxjs1}"/>
	    	<input id="jxjs2" class="mini-hidden" value="${yjmap.jxjs2}"/>
	    	<input id="jxjs3" class="mini-hidden" value="${yjmap.jxjs3}"/>
	    	<input id="jxjs4" class="mini-hidden" value="${yjmap.jxjs4}"/>
	    	<input id="jxjs5" class="mini-hidden" value="${yjmap.jxjs5}"/>
	    	<input id="jxjs6" class="mini-hidden" value="${yjmap.jxjs6}"/>
	    	<input id="jxjs7" class="mini-hidden" value="${yjmap.jxjs7}"/>
	    	<input id="jxjs8" class="mini-hidden" value="${yjmap.jxjs8}"/>
	    	<input id="jxjs9" class="mini-hidden" value="${yjmap.jxjs9}"/>
	    	<input id="jxjs10" class="mini-hidden" value="${yjmap.jxjs10}"/>
	    	<input id="jxjs11" class="mini-hidden" value="${yjmap.jxjs11}"/>
	    	<input id="jxjs12" class="mini-hidden" value="${yjmap.jxjs12}"/>
	    	<input id="jxjs13" class="mini-hidden" value="${yjmap.jxjs13}"/>
	    	<input id="payeverymon"  class="mini-hidden" value="${yjmap.payeverymon}"/>
	    	<input id="boquan"  class="mini-hidden" value="${yjmap.boquan}"/>
	    	<input id="strname"  class="mini-hidden" value="${yjmap.strname}"/>
	    	<input id="jxbasis"  class="mini-hidden" value="${yjmap.jxbasis}"/>
	    	<input id="tempids"  class="mini-hidden" value="${yjmap.tempids}"/>
	    	<input id="nosuggest"  class="mini-hidden" value="${yjmap.nosuggest}"/>
	    	<input id="merit"  class="mini-hidden" value="${merit}"/>
	    	<input id="range"  class="mini-hidden" value="${range}"/>
	    	<input id="crimid"  class="mini-hidden" value="${crimid}"/>
	    	<input id="provincecode"  class="mini-hidden" value="${provincecode}"/>
	    	<input id="resultinfo"  class="mini-hidden" value=""/>
  			
            <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a> 
            <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>
 		</div>
 		<div style="padding-left:25px;padding-bottom:5px;padding-right:11px;float:left;">
            <table style="table-layout:fixed;">
                <tr > 
                    <td width="90px;">减假意见：</td>
                     <td width="80px;">    
                          <input name="select1" id="select1" class="mini-combobox" textField="name" valueField="codeid"  allowInput="false" style="width:100%;" 
                          	onvaluechanged="select1changed" url="ajaxGetCode.json?codeType=JX002" value="0"/>
                    </td>
                     <td width="80px;">    
                          <input id="select2" class="mini-combobox" url="ajaxGetCode.json?codeType=GK056"  showNullItem="true"
							   textField="name" valueField="codeid" allowInput="false" style="width:100%;" showNullItem="true"  onvaluechanged="select2changed"/>
                    </td>
                     <td width="80px;">    
                          <input id="select3" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057"  showNullItem="true" onvaluechanged="setReason"
							   textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;" />
                    </td>
                     <td width="80px;">    
                          <input id="select4" class="mini-combobox" url="ajaxGetCode.json?codeType=GK058"  showNullItem="true" onvaluechanged="setReason"
							   textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;"  />
                    </td>
                </tr>
                <tr>
                   <td width="90px;">剥权年限：</td>
                   <td  width="80px;">    
                        <input id="select5" class="mini-combobox" url="ajaxGetCode.json?codeType=GK059" showNullItem="true" onvaluechanged="setReason"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
                   </td>
                   <td width="60px;" align="right">罚金减免：</td> 
                   <td align="left" colspan="3" >
                   		<input id="select10" class="mini-checkbox" text="" trueValue="1" falseValue="0" onvaluechanged="onFjJmChanged"/>    
						<input id="select6" name="select6" class="mini-textbox" onvaluechanged="setReason" style="width:100px;"/>&nbsp;&nbsp;万元
                   </td>
                </tr>
                <tr>
                  	<td >    
                    	<input id="select13" class="mini-checkbox" text="悔改表现" trueValue="1" falseValue="0" enabled="false" value="1" onvaluechanged="onDeptChanged"/>
                	</td>
                	<td colspan=4>
                        <input id="select8" class="mini-combobox" url="ajaxGetCode.json?codeType=GK054"  multiSelect="false" onvaluechanged="setReason"
						        textField="name" valueField="codeid" value="1" allowInput="false" style="width:100%;" />
                  	</td>
				</tr>
                <tr>
                  	<td >    
                    	<input id="select11" class="mini-checkbox" text="从宽" trueValue="1" falseValue="0" onvaluechanged="onDeptChanged1"/>
                	</td>
                	<td colspan=4>
                        <input id="select7" class="mini-combobox" url="ajaxGetCode.json?codeType=CK001"  multiSelect="true" onvaluechanged="setReason"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
                  	</td>
				</tr>
                <tr>  
					<td > 
	                    <input id="select12" class="mini-checkbox" text="从严" trueValue="1" falseValue="0" onvaluechanged="onDeptChanged2" />
	                </td>
                	<td colspan=4>   
                        <input id="select9" class="mini-combobox" url="ajaxGetCode.json?codeType=CY001"  multiSelect="true" onvaluechanged="setReason"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
                    </td>
				</tr>
				<tr>
					<td colspan="5">                        
	                	<select id="isagree" name="isagree" class="mini-radiobuttonlist" onvaluechanged="onIsagreeChanged" >
	                        <option value="1">同意</option>
	                        <option value="0">不同意</option>
	                    </select>
	                </td>
				</tr>
				<tr>
		        	<td colspan="5" rowspan="5">    
		            	<input  id="reason" name="reason" class="mini-textarea" emptyText="请填写意见" style="width:100%; height: 90px"/>
		            </td>
            	</tr>  
            </table>
        </div>
 	</form>
    
    <script type="text/javascript">
        mini.parse();
		var s1 = mini.get("select1");
		var s2 = mini.get("select2");
		var s3 = mini.get("select3");
		var s4 = mini.get("select4");
		var s5 = mini.get("select5");
		var s6 = mini.get("select6");
		var s7 = mini.get("select7");
		var s8 = mini.get("select8");
		var s9 = mini.get("select9");
		var s10 = mini.get("select10");
		var s11 = mini.get("select11");
		var s12 = mini.get("select12");
		var s13 = mini.get("select13");
		var s15 = mini.get("isagree");
		var s16 = mini.get("reason");
		var cjiimprisontype = mini.get("jxjs0").getValue();
        var form = new mini.Form("form1");
        var merit = mini.get("merit").getValue();//记功 
        var range = mini.get("range").getValue();//减刑建议幅度
        var strname = mini.get("strname").getValue(); //意见
        var crimid = mini.get("crimid").getValue(); //罪犯编号
        
       function myload(){
        	var obj = window.dialogArguments;
        	mini.get("jxbasis").setValue(obj.jxbasis);
        	mini.get("nosuggest").setValue(obj.nosuggest);
        	mini.get("reason").setValue(obj.reason);
        	mini.get("tempids").setValue(obj.tempids);
        	
	        //如果页面传值过来，把值填充到列表框
	        var jxjs1 = mini.get("jxjs1").getValue();
	        var jxjs2 = mini.get("jxjs2").getValue();
	        var jxjs3 = mini.get("jxjs3").getValue();
	        var jxjs4 = mini.get("jxjs4").getValue();
	        var jxjs5 = mini.get("jxjs5").getValue();
	        var jxjs6 = mini.get("jxjs6").getValue();
	        var jxjs7 = mini.get("jxjs7").getValue();
	        var jxjs8 = mini.get("jxjs8").getValue();
	        var jxjs9 = mini.get("jxjs9").getValue();
	        var jxjs10 = mini.get("jxjs10").getValue();
	        var jxjs11 = mini.get("jxjs11").getValue();
	        var jxjs12 = mini.get("jxjs12").getValue();
	        var payeverymon = mini.get("payeverymon").getValue();
	        var jxbasis = mini.get("jxbasis").getValue(); //减刑依据
        	mini.get("isagree").setValue(${yjmap.isagree});
        	//mini.get("reason").disable();
        	if(${yjmap.isagree}==1){
        		if(jxjs1){
		        	if(jxjs1 == '1'){ //减刑释放或假释,后边不需要选择了
		        		
		            	s2.setValue();
		            	s3.setValue();
		            	s4.setValue();
		        		s2.setEnabled(false);
		        		s3.setEnabled(false);
		        		s4.setEnabled(false);
	        		}
		        	mini.get("select1").setValue(jxjs1);
		        	mini.get("select2").setValue(jxjs2);
		        	mini.get("select3").setValue(jxjs3);
		        	mini.get("select4").setValue(jxjs4);
		        	mini.get("select5").setValue(jxjs5);
		        	mini.get("select6").setValue(jxjs6);
		        	mini.get("select7").setValue(jxjs7);
		        	if(jxjs8)mini.get("select8").setText(jxjs8);
		        	else mini.get("select8").setValue(1);
		        	mini.get("select9").setValue(jxjs9);
		        	mini.get("select10").setValue(jxjs10);
		        	mini.get("select11").setValue(jxjs11);
		        	mini.get("select12").setValue(jxjs12);
		        	mini.get("isagree").setValue("1");
		        	mini.get("payeverymon").setValue(payeverymon);
		        }
		        setReason();
        	}else if(${yjmap.isagree}==0){
        		s1.setValue("");
                s2.setValue("");
                s3.setValue("");
                s4.setValue("");
                s5.setValue("");
                s6.setValue("");
                s7.setValue("");
                s8.setValue("");
                s9.setValue("");
                s10.setValue("");
                s11.setValue("");
                s12.setValue("");
                s1.disable();
                s2.disable();
                s3.disable();
                s4.disable();
                s5.disable();
                s6.disable();
                s7.disable();
                s8.disable();
                s9.disable();
                s10.disable();
                s11.disable();
                s12.disable();
                mini.get("isagree").setValue("0");
        	}else{
        		s15.setValue(1);
		        s16.setValue("");
		        s7.disable();
                s9.disable();
        	}
	        
	        if(mini.get("select11").getValue()==false){
	        	s7.setValue("");
	            s7.disable();
	        }
	        if(mini.get("select12").getValue()==false){
	        	s9.setValue("");
	            s9.disable();
	        }
	        if('${yjmap.reason}'!=''){
	        	mini.get("reason").setValue('${yjmap.reason}');
	        } 
		    
	      } 
	      
	      function showDocInfo(){
		        var result = "";
		        var fd = MtoN(range);
		      	if(merit!=0){
		      		result+="有立功表现,";
		      	}
		      	if(range==9995){
		      		result+="减为无期徒刑。";
		      	}else{
		      		if(range>=10*12){
		      			result+="减为有期徒刑"+fd+"。";
		      		}else{
			      		if(fd!=''){
				      			result+="减去有期徒刑"+fd+"。";
			      		}
		      		}
		      	}
		      	return result;
	      }
	      
	       function MtoN(range){
	         var result="";
	       	 if(range&&(range!=0)&&range!=null){
	       	 	if(range>=12){
	       	 	    result+= parseInt(range/12)+"年"; 
	       	 		if((range%12)!=0){
	       	 			result+=range%12+"个月 ";
	       	 		}
	       	 	}else{
	       	 		result+=range+"个月";
	       	 	}
	       	 }else{
	       	 	result+="1年";
	       		mini.get("range").setValue(12);	
	       	 }
	       	 return result;
	      }
	       function myload1(){
	        //如果页面传值过来，把值填充到列表框
	        var jxjs1 = mini.get("jxjs1").getValue();
	        var jxjs2 = mini.get("jxjs2").getValue();
	        var jxjs3 = mini.get("jxjs3").getValue();
	        var jxjs4 = mini.get("jxjs4").getValue();
	        var jxjs5 = mini.get("jxjs5").getValue();
	        var jxjs6 = mini.get("jxjs6").getValue();
	        var jxjs7 = mini.get("jxjs7").getValue();
	        var jxjs8 = mini.get("jxjs8").getValue();
	        var jxjs9 = mini.get("jxjs9").getValue();
	        var jxjs10 = mini.get("jxjs10").getValue();
	        var jxjs11 = mini.get("jxjs11").getValue();
	        var jxjs12 = mini.get("jxjs12").getValue();
	        var payeverymon = mini.get("payeverymon").getValue();
	        s1.enable();
	        s2.enable();
	        s3.enable();
	        s4.enable();
	        s5.enable();
	        s6.enable();
	        s7.enable();
	        s8.enable();
	        s9.enable();
	        s10.enable();
	        s11.enable();
	        s12.enable();
	        if(jxjs1){
	        	if(jxjs1 == '1'){ //减刑释放或假释,后边不需要选择了
	            	s2.setValue();
	            	s3.setValue();
	            	s4.setValue();
	        		s2.setEnabled(false);
	        		s3.setEnabled(false);
	        		s4.setEnabled(false);
        		}
	        	mini.get("select1").setValue(jxjs1);
	        	mini.get("select2").setValue(jxjs2);
	        	mini.get("select3").setValue(jxjs3);
	        	mini.get("select4").setValue(jxjs4);
	        	mini.get("select5").setValue(jxjs5);
	        	mini.get("select6").setValue(jxjs6);
	        	mini.get("select7").setValue(jxjs7);
	        	mini.get("select8").setValue(jxjs8);
	        	mini.get("select9").setValue(jxjs9);
	        	mini.get("select10").setValue(jxjs10);
	        	mini.get("select11").setValue(jxjs11);
	        	mini.get("select12").setValue(jxjs12);
	        	mini.get("payeverymon").setValue(payeverymon);
	        }
	        
	        if(mini.get("select11").getValue()==false){
	        	s7.setValue("");
	            s7.disable();
	        }
	        
	        if(mini.get("select12").getValue()==false){
	        	s9.setValue("");
	            s9.disable();
	        }
	      } 
        function onDeptChanged(e) {
            var temp = s11.getValue();
            if(temp=='1'){
            	s7.enable();
            	var url = "ajaxGetCode.json?codeType=CK001";
            	 s7.setUrl(url);
          		 s7.select(0);
            }else{
            	s7.setValue("");
            	s7.disable();
            }
            var temp2 = s12.getValue();
            if(temp2=='1'){
          		s9.enable();
            	var url = "ajaxGetCode.json?codeType=CY001";
            	s9.setUrl(url);
          		s9.select(0);
            }else{
            	s9.setValue("");
            	s9.disable();
            	setReason();
            }
        }
        function onDeptChanged1(e) {
            var temp = s11.getValue();
            if(temp=='1'){
            	s7.enable();
            	var url = "ajaxGetCode.json?codeType=CK001";
            	 s7.setUrl(url);
          		 s7.select(0);
            }else{
            	s7.setValue("");
            	s7.disable();
            	setReason();
            }
        }
        function onDeptChanged2(e) {
            var temp2 = s12.getValue();
            if(temp2=='1'){
          		s9.enable();
            	var url = "ajaxGetCode.json?codeType=CY001";
            	s9.setUrl(url);
          		s9.select(0);
            }else{
            	s9.setValue("");
            	s9.disable();
            	setReason();
            	
            }
        }
        function onFjJmChanged(e) {
            var temp = s10.getValue();
            if(temp=='1'){
            	//var select6 = mini.get("select6").getValue();
            	//mini.get("jxjs6").setValue(select6);
            	s6.setValue("");
            	s6.disable();
            }else{
            	s6.setValue("");
            	s6.enable();
            }
            setReason();
        }
         function onIsagreeChanged(e) {
	         var rbl = mini.get("isagree").getValue();
	         if(rbl==1){
	         	s16.setValue("");
	         	myload1();
	         	setReason();
	         	s1.select(0);
	         	s2.select(0);	         	
	         	//mini.get("reason").disable();
	         }else{
	         	var nosuggest = mini.get("nosuggest").getValue();
	         	mini.get("reason").setValue(nosuggest);
	         	s1.setValue("");
	            s2.setValue("");
	            s3.setValue("");
	            s4.setValue("");
	            s5.setValue("");
	            s6.setValue("");
	            s7.setValue("");
	            s8.setValue("");
	            s9.setValue("");
	            s10.setValue("");
	            s11.setValue("");
	            s12.setValue("");
	            s1.disable();
	            s2.disable();
	            s3.disable();
	            s4.disable();
	            s5.disable();
	            s6.disable();
	            s7.disable();
	            s8.disable();
	            s9.disable();
	            s10.disable();
	            s11.disable();
	            s12.disable();
	            mini.get("reason").enable();
	         }
         }	
         
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
            
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

        var mycars=new Array("零","一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十");
		//var mycars=new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾","拾壹","拾贰","拾叁","拾肆","拾伍","拾陆","拾柒","拾捌","拾玖","贰拾","贰拾壹","贰拾贰","贰拾叁","贰拾肆","贰拾伍","贰拾陆","贰拾柒","贰拾捌","贰拾玖","叁拾")
        function onOk2() {
        	var result = setSuggest();//根据所选数据生成减刑意见
            if(s15.getValue()==0&&s16.getValue().trim()==''){
            	alert("必须填写否决意见！");
            	return;
            }
            var row = new Array([17]);
            if(s1.getValue()){
            	row[0] = s1.getValue();
            	
            	var s1v = s1.getValue();
            	if(s1v =='1' || s1v =='4'){
            		s2.setValue();
            		s2.setEnabled(false);
            	}
            }else{
            	row[0] = mini.get("jxjs1").getValue();
            }
            row[1] = s2.getValue();
            row[2] = s3.getValue();
            row[3] = s4.getValue();
            row[4] = s5.getValue();
            row[5] = s6.getValue();
            row[6] = s7.getValue();
            row[7] = s8.text;
            row[8] = s9.getValue();
            row[9] = s10.getValue();
            row[10] = s11.getValue();
            row[11] = s12.getValue();
            row[12] = s13.getValue();
            row[13] = s15.getValue();
            row[14] = s16.getValue();
            row[15]= result;
            row[17] = mini.get("resultinfo").getValue();
            if(s1.getValue() && s1.getValue()!=1){
            	var year = s2.getValue();
            	var month = s3.getValue();
            	var day = s4.getValue();
            	if( ! day){
            		day = 0;
            	}
            	
            	var result_date = result_NYR(year,month,day);
            	row[16]=result_date;
            }
            window.returnValue = row;
            CloseWindow("cancel");
        }
        
        function select1changed() {
        	var s1v = s1.getValue();
        	if(s1v =='1' || s1v =='4'){ //假释、减余刑,后边不需要选择了
        		s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s7.setValue();
        		s9.setValue();
        		s11.setValue();
        		s12.setValue();
        		s2.setEnabled(false);
        		s3.setEnabled(false);
        		s4.setEnabled(false);
        		s7.setEnabled(false);
        		s9.setEnabled(false);
        		s11.setEnabled(false);
        		s12.setEnabled(false);
        	}else{
        		s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s7.setValue();
        		s9.setValue();
        		s11.setValue();
        		s12.setValue();
        		s2.setEnabled(true);
        		s3.setEnabled(true);
        		s4.setEnabled(true);
        		s11.setEnabled(true);
        		s12.setEnabled(true);
        	}
        	setReason();
        }
        //返到表单上的年月日串
      function result_NYR(N,Y,R){
        	var result_date=N+","+Y+","+R;
        	return result_date;
        }
        
      	//没重大立功的组织减刑文言（建议幅度  和选择日期 和  1年）
       	function result_range(month,xzValue,N,Y,R){
         	if(month<xzValue){//建议幅度<选择的
         		if(month==12){
	         		return "一年";
	         	}else if(month<12&&month!=0){
	         			return mycars[month] + "个月";
	         	}else{//如果没重大立功最大减刑一年（不管减刑幅度是多少）
	         		return "一年";
	         	}
         	}else if(month==xzValue){
         		if(month==12){
	         		return "一年";
	         	}else{
	         		if(xzValue<12){
	         			return mycars[xzValue] + "个月";
	         			if(R!=0){
		         			return mycars[Y] + "个月"+mycars[R]+"天";
		         			}
	         		}else {
	         			return "一年";
	         		}
	         	}         		
         	}else {
         		if(xzValue>12){
         			return "一年";
         		}else{
         			if(R==0){
         				return mycars[Y]+"个月";
         			}else{
         				return mycars[Y]+"个月"+mycars[R]+"天";
         			}
         		}
         	}
         }
        //建议幅度和1年比较  返回最小值 （月份）
        function result_range_one(month){
	       	 if(month!=''){
		         	if(month>=12){
		         		return 12;
		         	}else{
		         		return month;
		         	}					
	       	 }else{
	       	 	return 0;
	       	 }
        }
         //算成月份
        function  result_N_Y_R(N,Y,R){
         	return (N*12)+Y+(R/30);
        }
         
        function select2changed() {
        	var s2v = s2.getValue();
        	if(parseInt(s2v) > 25){ //无期，死缓，死刑
        		s3.setValue();
        		s4.setValue();
        		s3.setEnabled(false);
        		s4.setEnabled(false);
        	}else{
        		s3.enable();
        		s4.enable();
        	}
        	setReason();
        }

        function setReason(){
			var s1value = s1.getValue();
      		var tempids = mini.get("tempids").getValue();
      		var resultinfo = mini.get("resultinfo");
      		resultinfo.setValue("");
			var s7text = s7.getText();
            var s9text = s9.getText();
			var s11text = s11.getValue();
            var s12text = s12.getValue();
		//从宽
		if(s11text == '1'){
			resultinfo.setValue("");
			resultinfo.setValue( "符合从宽"+s7text+"," );
		}			
		//从严
		if(s12text == '1'){
			resultinfo.setValue("");
			resultinfo.setValue("符合从严"+s9text+",");						
		}
		//从宽+从严
		if(s11text == '1'&&s12text == '1'){
			resultinfo.setValue("");
			resultinfo.setValue("符合从严"+s9text+"，从宽"+s7text+",");	
		}
		
      		if(s1value==1){
      	        tempids = tempids.replace('(减刑假释)','假释');
      	    }else{
      	        tempids = tempids.replace('(减刑假释)','减刑');
      	    }
			var result = setSuggest();
			tempids = tempids.replace("@", result).replace("GK054", s8.text).replace("#",resultinfo.getValue() );
			mini.get("reason").setValue("");
			mini.get("reason").setValue(tempids);
			return result;
        }
        //根据所选数据生成减刑意见
        function setSuggest(){
			var boquan = mini.get("boquan").getValue().trim();
        	var range = mini.get("range").getValue();
        	var result = mini.get("reason").getValue();
            var s1value = s1.getValue();
            var s2text = s2.text;
            var s2value = parseInt(s2.getValue());
            if(isNaN(s2value)){
            	s2value=0;
            }
            var s3value = parseInt(s3.getValue());
            if(isNaN(s3value)){
            	s3value=0;
            }
            var s4value = parseInt(s4.getValue());
            if(isNaN(s4value)){
            	s4value=0;
            }
            var s5value = s5.getValue();
            var s5text = s5.text;
            var s6text = s6.getValue();
            var s7text = s7.text;
            var s9text = s9.text;
            var s10value = s10.getValue();
            var s11text = s11.Value;
            var s12text = s12.text;
            var provincecode = mini.get("provincecode").getValue();
            var flag = true;
            //存到库里的年月日 
            var jxjs_N;
            var jxjs_Y;
            var jxjs_R;
            if(s1value){
            	result = "";
            	if(s1value == '0'){
            		//如果减刑意见都为空时选择减刑幅度
            		if(s2value==0&&s3value==0&&s4value==0){
            			if(range>=12){
            				if(range== 9995){
            					s2value = 9995;
            					s2text = "无期";
            				}else{
            					s2.setValue(range/12);
                				s3.setValue(range%12);
                	            s2value = range/12;
                	            s3value = range%12;
            				}
        	       	 	}else if(range<12){
        	       	 		s3.setValue(range);
        	       	 		s3value = range;
        	       	 	}else{
	        	       	 	alert("请选择减刑幅度");
	            			return;
        	       	 	}
            		}
            		//从宽
           			//从严
            		//家庭困难
            		if((s11text&&s7text)||(s12text&&s9text)){
            			if(parseInt(s2value) > 25){
	           		   		 //返回总结果 
	            			result_date = s2value;
	           				if(parseInt(s2value)== 9995){
	           					result += "减为"+s2text+"徒刑";
	           				}else{
	           					result += "减为"+s2text;
	           				}
	           			}else if(parseInt(s2value) > 10){
	           				result += "减为有期徒刑";
	           			}else{
	           				result += "减去有期徒刑";
	           				flag = false;
	           			}	   
         	           	
	           		    
	            		if(flag){//不是有期的
	            			var resultN = -1;
	            			var resultY = -1;
	            			var resultD = -1;
	            			var flag_N = true;
	            			var flag_Y = true;
	            			var flag_D = true;
	            			 resultN=s2value;
	            			 resultY=s3value;
							 resultD=s4value;
							if(resultY<0){
								resultY=resultY+12;
								resultN=resultN-1;
							}else if(resultY>=12){
								resultY=resultY-12;
								resultN=resultN+1;
							}
							if((resultN==0)||(resultN==9995)||(resultN==9996)||(resultN==9997)){
								flag_N=false;
							}
							if(resultY==0||(resultN==9995)||(resultN==9996)||(resultN==9997)){
								flag_Y=false;
							}	         
							if(resultD==0||(resultN==9995)||(resultN==9996)||(resultN==9997)){
								flag_D=false;	
							}  
							//返回总结果 
	            			jxjs_N = resultN;
	            			jxjs_Y=resultY;
	            			jxjs_R=resultD;
	            			result_date = result_NYR(jxjs_N,jxjs_Y,jxjs_R);
	            			
	            			if(flag_N&&resultN>0){
	            				resultN = mycars[resultN];
	            				result +=resultN+"年";
	            			}
	            			if(flag_Y&&resultY>0){
	            				resultY =mycars[resultY]; 
	            				result +=  resultY+"个月";
	            			}
	            			if(flag_D&&resultD>0){
	            				resultD=mycars[resultD];
	            				result +=resultD+"天";
	            			}	
	            			
            		    }else{
		            		if(parseInt(s2value) < 26){//有期
	            				var resultN = 0;
		            			var resultY = 0;
		            			var resultD = 0;
		            			var flag_N = true;
		            			var flag_Y = true;
		            			var flag_D = true;
		            			resultN=s2value;
		            			resultY=s3value;
								resultD=s4value;
			            		result_date = result_NYR(resultN,resultY,resultD);
		            			if(flag_N&&resultN>0){
		            				resultN = mycars[resultN];
		            				result +=resultN+"年";
		            			}
		            			if(flag_Y&&resultY>0){
		            				resultY =mycars[resultY]; 
		            				result +=  resultY+"个月";
		            			}
		            			if(flag_D&&resultD>0){
		            				resultD=mycars[resultD];
		            				result +=resultD+"天";
		            			}	
		            		}
            			}
           			} else {
     	           		if(parseInt(s2value) > 25){
	           		   		 //返回总结果 
	            			result_date = s2value;
	           				if(parseInt(s2value)== 9995){
	           					result += "减为"+s2text+"徒刑";
	           				}else{
	           					result += "减为"+s2text;
	           				}
	           			}else if(parseInt(s2value) > 10){
	           				result += "减为有期徒刑";
	           			}else{
	           				result += "减去有期徒刑";
	           				flag = false;
	           			}	   
     	           		
	            		if(flag){//不是有期的
	            			var resultN = -1;
	            			var resultY = -1;
	            			var resultD = -1;
	            			var flag_N = true;
	            			var flag_Y = true;
	            			var flag_D = true;
	            			 resultN=s2value;
	            			 resultY=s3value;
							 resultD=s4value;
							if((resultN==0)||(resultN==9995)||(resultN==9996)||(resultN==9997)){
								flag_N=false;
							}
							if(resultY==0){
								flag_Y=false;
							}	         
							if(resultD==0){
								flag_D=false;	
							}  
							//返回总结果 
	            			jxjs_N = resultN;
	            			jxjs_Y=  resultY;
	            			jxjs_R=  resultD;
	            			result_date = result_NYR(jxjs_N,jxjs_Y,jxjs_R);
	            			
	            			//result+="建议提请减为有期徒刑";
	            			if(flag_N&&resultN>0){
	            				resultN = mycars[resultN];
	            				result +=resultN+"年";
	            			}
	            			if(flag_Y&&resultY>0){
	            				resultY =mycars[resultY]; 
	            				result +=  resultY+"个月";
	            			}
	            			if(flag_D&&resultD>0){
	            				resultD=mycars[resultD];
	            				result +=resultD+"天";
	            			}	
            		    }else{
		            		if(parseInt(s2value) < 26){//有期
	            				var resultN = 0;
		            			var resultY = 0;
		            			var resultD = 0;
		            			var flag_N = true;
		            			var flag_Y = true;
		            			var flag_D = true;
		            			resultN=s2value;
		            			resultY=s3value;
								resultD=s4value;
								if(resultN==0){
								flag_N=false;
								}
								if(resultY==0){
								flag_Y=false;
								}	         
								if(resultD==0){
								flag_D=false;	
								}   
		            			result_date = result_NYR(resultN,resultY,resultD);
		            			if(flag_N&&resultN>0){
		            				resultN = mycars[resultN];
		            				result +=resultN+"年";
		            			}
		            			if(flag_Y&&resultY>0){
		            				resultY =mycars[resultY]; 
		            				result +=  resultY+"个月";
		            			}
		            			if(flag_D&&resultD>0){
		            				resultD=mycars[resultD];
		            				result +=resultD+"天";
		            			}	
		            		}
            			}
           			}
            	}else if(s1value == '1'){//假释
            		result = "假释";
            	}else if(s1value == '4'){//减余刑
            		result = "减去有期徒刑余刑";
            	}
            	if(s5value){
       				result += "，剥夺政治权利改为"+s5text;
       			}else if(boquan){
       				result += "，"+boquan+"不变";
               	}
            	if(s10value=='1'){
           			result +="，罚金全免";
           		}else if(s6text){
           			result +="，减去罚金"+s6text+"万元";
           		}	
            }
            return result;
        }
    </script>
</body>
</html>
