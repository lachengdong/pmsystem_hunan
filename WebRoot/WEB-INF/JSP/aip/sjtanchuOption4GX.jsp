<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>案件办理中的意见弹出框</title>
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
<body  onload="myload()">     
    <form id="form1" method="post">
    	<input id="select7" class="mini-hidden" />
        <input id="select8" class="mini-hidden" />
        <input id="select9" class="mini-hidden" />
        <input id="select10" class="mini-hidden" />
        <input id="select11" class="mini-hidden" />
        <input id="select12" class="mini-hidden" />
        <input id="select13" class="mini-hidden" />
               
               
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
    	<input id="jxjs14"  class="mini-hidden" value="${yjmap.jxjs14}"/>
    	<input id="jxjs15"  class="mini-hidden" value="${yjmap.jxjs15}"/>
    	<input id="jxjs16"  class="mini-hidden" value="${yjmap.jxjs16}"/>
    	<input id="merit"  class="mini-hidden" value="${merit}"/>
    	<input id="range"  class="mini-hidden" value="${range}"/>
    	<input id="provincecode"  class="mini-hidden" value="${provincecode}"/>
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>  
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr> 
                    <td width="90px;">减刑假释意见：</td>
                     <td width="80px;">    
                          <input name="select1" id="select1" class="mini-combobox" textField="name" valueField="codeid"  allowInput="false" style="width:100%;" 
                          	onvaluechanged="select1changed" url="ajaxGetCode.json?codeType=JX002" value="0"
                          />
                    </td>
                     <td width="80px;">    
                          <input id="select2" class="mini-combobox" url="ajaxGetCode.json?codeType=GK056"  showNullItem="true"
							     textField="name" valueField="codeid" allowInput="false" style="width:100%;" showNullItem="true"  onvaluechanged="select2changed"
							/>
                    </td>
               
                     <td width="80px;">    
                          <input id="select3" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057"  showNullItem="true"
							        textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;" 
							/>
                    </td>
                     <td width="80px;">    
                          <input id="select4" class="mini-combobox" url="ajaxGetCode.json?codeType=GK058"  showNullItem="true"
							        textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;"  />
                    </td>
                </tr>
                <tr>
                	<td width="90px;">剥夺政治权利：</td>
                   <td  width="80px;">    
                        <input id="select5" class="mini-combobox" url="ajaxGetCode.json?codeType=GK059" showNullItem="true"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
                   </td>
                
                   <td width="80px;">罚金减免：</td>
                   <td   align="left" colspan="2">    
						<input id="select6" name="select6" class="mini-textbox" style="width:80px;"/>万元
                   </td>
                </tr>
                
                <!--  
                 <tr>
                  <td >    
                    <input id="select11" class="mini-checkbox" text="从宽" trueValue="1" falseValue="0" onvaluechanged="onDeptChanged"/>
                </td>
                <td colspan=2>
                        <input id="select7" class="mini-combobox" url="ajaxGetCode.json?codeType=CK001"  multiSelect="true"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
                   </td>
                   <td width="80px;" colspan=2>    
                          <input id="select8" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057" showNullItem="true"
							        textField="name" valueField="codeid" allowInput="false" style="width:100%;" 
							/>
					</td>
					 </tr>
                  <tr>  
                  <td > 
                    <input id="select12" class="mini-checkbox" text="从严" trueValue="1" falseValue="0" onvaluechanged="onDeptChanged1" />
                </td>
                <td colspan=2>   
                        <input id="select9" class="mini-combobox" url="ajaxGetCode.json?codeType=CY001"  multiSelect="true"
						        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
                   </td>
                   <td width="80px;" colspan=2> 
                          <input id="select10" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057" showNullItem="true"
							        textField="name" valueField="codeid" allowInput="false" style="width:100%;" 
							/>
					</td>
					</tr>
					<tr>
					 <td colspan=5> 
                    <input id="select13" class="mini-checkbox" text="有家庭困难证明" trueValue="1" falseValue="0" onvaluechanged=""/>
               		 </td>
					</tr>
					-->
					
					
					<tr>
						<td colspan="5">
							<span id="doc"style="color:red; "></span>
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
<!--					<tr>-->
<!--						<td >    -->
<!--                   		 <input name="manualSetting" class="mini-checkbox" text="手动设置" trueValue="1" falseValue="0" />-->
<!--               			 </td>-->
<!--					</tr>-->
					<tr>
		                <td colspan="5" rowspan="5">    
		                    <input  id="reason" name="reason" class="mini-textarea" emptyText="请填写否决理由" style="width:100%; height: 135px"/>
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
        function myload(){
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
	        var jxjs13 = mini.get("jxjs13").getValue();
	        var jxjs14 = mini.get("jxjs14").getValue();
	        var jxjs15 = mini.get("jxjs15").getValue();
	        var jxjs16 = mini.get("jxjs16").getValue();
        	mini.get("isagree").setValue(jxjs15);
        	mini.get("reason").setValue(jxjs16);
	        if(jxjs1){
	        	if(jxjs1 == '1'){ //减刑释放或假释,后边不需要选择了
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
	        	mini.get("select13").setValue(jxjs13);
	        	mini.get("jxjs14").setValue(jxjs14);
	        }
	        
	        if(mini.get("select11").getValue()==false){
	        	s7.setValue("");
	            s8.setValue("");
	            s7.disable();
	            s8.disable();
	        }
	        
	        if(mini.get("select12").getValue()==false){
	        	s9.setValue("");
	            s10.setValue("");
	            s9.disable();
	            s10.disable();
	        }
	     	 if(jxjs15==''){
		        	s15.setValue(1);
		        	s16.setValue("");
		        	document.getElementById("reason").style.display = "none";
		        }else if(jxjs15==0){
		        	s1.setValue("");
		        	document.getElementById("reason").style.display = "block";
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
		            s13.disable();	
		            document.getElementById("reason").style.display = "block";
		        }else{//如果同意
		        	 document.getElementById("reason").style.display = "none";
		        	 s16.setValue("");
		        }
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
	        var jxjs13 = mini.get("jxjs13").getValue();
	        var jxjs14 = mini.get("jxjs14").getValue();
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
	        s13.enable();
	        if(jxjs1){
	        	if(jxjs1 == '1'){ //减刑释放或假释,后边不需要选择了
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
	        	mini.get("select13").setValue(jxjs13);
	        	mini.get("jxjs14").setValue(jxjs14);
	        }
	        
	        if(mini.get("select11").getValue()==false){
	        	s7.setValue("");
	            s8.setValue("");
	            s7.disable();
	            s8.disable();
	        }
	        
	        if(mini.get("select12").getValue()==false){
	        	s9.setValue("");
	            s10.setValue("");
	            s9.disable();
	            s10.disable();
	        }
	      } 
	      //从宽触发事件 
        function onDeptChanged(e) {
            var temp = s11.getValue();
            if(temp=='1'){
            	s7.enable();
            	s8.enable();
            	codetype = 'CK001';
            	var url = "ajaxGetCode.json?codeType=" + codetype;
            	 s7.setUrl(url);
          		 s7.select(0);
            }else{
            	s7.setValue("");
            	s8.setValue("");
            	s7.disable();
            	s8.disable();
            }
           
        }
        //同意不同意 触发事件 
         function onIsagreeChanged(e) {
	         var rbl = mini.get("isagree").getValue();
	         if(rbl==1){
	         	s16.setValue("");
	         	document.getElementById("reason").style.display = "none";
	         	myload1();
	         }else{
	         	document.getElementById("reason").style.display = "block";
	         	var tempid = "9994";
	         	$.ajax({
			         url: "<%=path%>/getSystemTemplateStr.action", 
			         data: {tempid:tempid},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	mini.get("reason").setValue(text);
			         }
				});
	         	
	         	//	s16.enable();
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
	            s13.setValue("");
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
	            s13.disable();
	         }
         }	
         //从严触发事件 
         function onDeptChanged1(e) {
          var temp2 = s12.getValue();
          if(temp2=='1'){
          		s9.enable();
            	s10.enable();
            	codetype = 'CY001';
            	var url = "ajaxGetCode.json?codeType=" + codetype;
            	 s9.setUrl(url);
          		 s9.select(0);
            }else{
            	s9.setValue("");
            	s10.setValue("");
            	s9.disable();
            	s10.disable();
            }
         
         }
        //关闭 
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
		//确定 
        var mycars=new Array("零","一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十")
        function onOk2(e) {
        	var result = "";
            var s1value = s1.getValue();
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
            var s6value = s6.getValue();
            var s7value = s7.getValue();
            var s8value = parseInt(s8.getValue());
            var s9value = parseInt(s9.getValue());
            if(isNaN(s8value)){
            	s8value=0;
            }
            var s10value=parseInt(s10.getValue());
            if(isNaN(s10value)){
            	s10value=0;
            }
            var s11value=s11.getValue();
            var s12value=s12.getValue();
            var s13value=s13.getValue();
            
            var s1text = s1.text;
            var s2text = s2.text;
            var s3text = s3.text;
            var s4text = s4.text;
            var s5text = s5.text;
            var s6text = s6.getValue();
            var s7text = s7.text;
            var s8text = s8.text;
            var s9text = s9.text;
            var s10text = s10.text;
            var s11text = s11.text;
            var s12text = s12.text;
            var s13text = s13.text;
            var jxjs14 = mini.get("jxjs14").getValue();
            var provincecode = mini.get("provincecode").getValue();
            var flag = true;
            var result_date="";
           	if(s1value == '0'){//减刑 
           	
           		if(s2value==0&&s3value==0&&s4value==0){
           			mini.alert("请选择减刑幅度");
           			return;
           		}
        		var month= NYRtoMonth(s2value,s3value,s4value);
           		if(month-s8value+s10value<0){
      				mini.alert("选择的值有错，请核实！");
      				return;
	      		}
       			var resultN = 0;
       			var resultY = 0;
       			var resultD = 0;
       			var flag_N = true;
       			var flag_Y = true;
       			var flag_D = true;
       			resultN=s2value;
       			resultY=s3value;
				resultD=s4value;
        		if((s11text&&s7text&&s8text)){
					resultY=resultY-s8value;
				} 
				if ((s12text&&s9text&&s10text)){
					resultY=resultY+s10value;
				}
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
				if((s11text&&s7text&&s8text)){
          				result+="该犯符合《规程》";
            		result+=s7text+"规定，从宽"+s8text+"，";
          		}
           		if((s12text&&s9text&&s10text)){
          				result+="该犯符合《规程》";
            		result+=s9text+"规定，从严"+s10text+"，";
          		}
           		if(s13value == '1'){
           		    if(jxjs14>=300) {
           		    	result+="有家庭困难证明，但月均支出超标，不符合家庭困难标准，";
           		    } else {
           				result+="符合家庭困难标准，";
           			}
           		}
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
       			var month= NYRtoMonth(s2value,s3value,s4value);
           		if(month-s8value+s10value==0){
      				result+="零年零月零天";
	      		}
       			if(s2value!=9995&&s2value!=9996&&s2value!=9997){
       				result_date = result_NYR(resultN,resultY,resultD);
       			}else{
       				result_date = s2value;
       			}
       			if(s5value){
       				result += "，剥夺政治权利"+s5text;
       			}	
           	}else if(s1value == '1'){//假释
           		result = "提请假释";
           		if(s5value){
          				result += "，剥夺政治权利"+s5text;
          		}
           	}
           	if(s6text){
          			result +="，罚金:"+s6text+"万元";
          	}	
            if(s15.getValue!=1){
	            result+=s16.getValue();
            }else{
            	s16.setValue("");
            	result+=s16.getValue();
            }
            result += "。";
            if(s15.getValue()==0&&s16.getValue().trim()==''){
            	alert("必须填写否决意见！");
            	return;
            }
            var row = new Array([17]);
            row[0] = s1value;
            row[1] = s2.getValue();
            row[2] = s3.getValue();
            row[3] = s4.getValue();
            row[4] = s5value;
            row[5] = s6value;
            row[6] = s7.getValue();
            row[7] = s8.getValue();
            row[8] = s9.getValue();
            row[9] = s10.getValue();
            row[10] = s11.getValue();
            row[11] = s12.getValue();
            row[12] = s13value;
            row[13] = s15.getValue();
            row[14] = s16.getValue();
            row[15]= result;
            row[16]=result_date;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        
        function select1changed() {
        	var s1v = s1.getValue();
        	if(s1v =='1'){ //假释,后边不需要选择了
        		s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s7.setValue();
        		s8.setValue();
        		s9.setValue();
        		s10.setValue();
        		s11.setValue();
        		s12.setValue();
        		s13.setValue();
        		s2.setEnabled(false);
        		s3.setEnabled(false);
        		s4.setEnabled(false);
        		s7.setEnabled(false);
        		s8.setEnabled(false);
        		s9.setEnabled(false);
        		s10.setEnabled(false);
        		s11.setEnabled(false);
        		s12.setEnabled(false);
        		s13.setEnabled(false);
        	}else{
        	s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s7.setValue();
        		s8.setValue();
        		s9.setValue();
        		s10.setValue();
        		s11.setValue();
        		s12.setValue();
        		s13.setValue();
        		s2.setEnabled(true);
        		s3.setEnabled(true);
        		s4.setEnabled(true);
        		s11.setEnabled(true);
        		s12.setEnabled(true);
        		s13.setEnabled(true);
        	}
        }
        //返到表单上的年月日串
        function result_NYR(N,Y,R){
        	var result_date=N+","+Y+","+R;
        	return result_date;
        }
      	  
         //算成月份
         function  NYRtoMonth(N,Y,R){
       		if(N!=''){
        		N=parseInt(N);	
       		}else{
       			N=0;
       		}
       		if(Y!=''){
        		Y=parseInt(Y);	
       		}else{
       			Y=0;
       		}
       		if(R!=''){
        		R=parseInt(R);	
       		}else{
       			R=0;
       		}
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
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
        	
        }
    </script>
</body>
</html>
