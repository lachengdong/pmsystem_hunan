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
    	<input id="punishmenttype"  class="mini-hidden" value="${punishmenttype}"/>
    	<input id="specicalcrim"  class="mini-hidden" value="${specicalcrim}"/>
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
							        textField="name" valueField="codeid" allowInput="false" style="width:100%;" />
					</td>
				</tr>
				<tr>
					<td colspan=5> 
                    	<input id="select13" class="mini-checkbox" text="有家庭困难证明" trueValue="1" falseValue="0" onvaluechanged=""/>
               		 </td>
				</tr>
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
        var range = mini.get("range").getValue();//减刑建议幅度
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
	        //判断是否同意
	     	 if(jxjs15==''){
		        	s15.setValue(1);
		        	s16.setValue("");
		        	document.getElementById("reason").style.display = "none";
		        }else if(jxjs15==0){//不同意不可编辑 
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
		        	//s16.disable();
		        }
		       var show = showDocInfo(); 
			   document.getElementById('doc').innerText=show;
	      } 
	      
	          function showDocInfo(){
		        var result = "";
		        var flag;
		        var fd = MtoN(range);
		      	if(merit!=0){
		      			result+="有立功表现,"
		      		}
		      	if(range==9995){
		      			result+="建议减刑幅度:减为无期。";
		      		}else{
			      		if(range>=10*12){
			      			result+="建议减刑幅度:减为"+fd+"。";
			      		}else{
				      		if(fd!=''){
					      			result+="建议减刑幅度:减去"+fd+"。";
				      		}
			      		}
		      		}
		      	return result;
	      }
	      //隐藏手动设置
	      function hideTJ(){
	      	document.getElementById("manualSetting").style.display = "block";	
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
            	//codetype = '';
            	//var url = "ajaxGetCode.json?codeType=" + codetype;
            	// s7.setUrl(url);
          		 //s7.select(0);
          		 //mini.get("select8").setValue("");
            }
           
        }
         function onIsagreeChanged(e) {
	         var rbl = mini.get("isagree").getValue();
	         if(rbl==1){
	         	s16.setValue("");
	         	document.getElementById("reason").style.display = "none";
	         	//s16.disable();
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
			         	//var obj = eval(text);
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
            	//codetype = '';
            	//var url = "ajaxGetCode.json?codeType=" + codetype;
            	 //s9.setUrl(url);
          		 //s9.select(0);
          		 //mini.get("select10").setValue("");
            }
         
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

        var mycars=new Array("零","一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十")
		//var mycars=new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾","拾壹","拾贰","拾叁","拾肆","拾伍","拾陆","拾柒","拾捌","拾玖","贰拾","贰拾壹","贰拾贰","贰拾叁","贰拾肆","贰拾伍","贰拾陆","贰拾柒","贰拾捌","贰拾玖","叁拾")
        function onOk2(e) {
        	range = mini.get("range").getValue();
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
            var s9value = s9.getValue();
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
            var punishmenttype = mini.get("punishmenttype").getValue();
            var specicalcrim = mini.get("specicalcrim").getValue();
            var flag = true;
            //存到库里的年月日 
            var jxjs_N;
            var jxjs_Y;
            var jxjs_R;
            var result_date="";
            if(s1value){
            	if(s1value == '0'){
            		//选择减刑幅度
            		if(s2value==0&&s3value==0&&s4value==0){
            			alert("请选择减刑幅度");
            			return;
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
            		if((s11text&&s7text&&s8text)||(s12text&&s9text&&s10text)){
            		//从宽从严有选择的情况
            		if(provincecode==4400){
	           		    if(parseInt(s2value) > 25){
	           		   		 //返回总结果 
	            			result_date = s2value;
	           				if(parseInt(s2value)== 9995){
	           					result += "提请减为"+s2text+"徒刑";
	           				}else{
	           					result += "提请减为"+s2text;
	           				}
	           			}else if(parseInt(s2value) > 10){
	           				result += "提请减为有期徒刑";
	           			}else{
	           				result += "提请减去有期徒刑";
	           				flag = false;
	           			}}
                       else{
                        if(parseInt(s2value) > 25){
	           		   		 //返回总结果 
	            			result_date = s2value;
	           				if(parseInt(s2value)== 9995){
	           					result += "建议减为"+s2text+"徒刑";
	           				}else{
	           					result += "建议减为"+s2text;
	           				}
	           			}else if(parseInt(s2value) > 10){
	           				result += "建议减为有期徒刑";
	           			}else{
	           				result += "建议减去有期徒刑";
	           				flag = false;
	           			}
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
	            			 if((s11text&&s7text&&s8text)){
							 	resultY=resultY-s8value;
							 } 
							 if (s12text&&s9text&&s10text&&
									     ((s13value=='0')||
									      ((s13value=='1')&&(jxjs14>=300))||
									      ((s13value=='1')&&(jxjs14<300)&&(s9value=='4'))
									     )
									     ){
							 	resultY=resultY+s10value;
							 }
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
	            			
	            			//result+="建议提请减为有期徒刑";
	            			if (punishmenttype == "3") {
								if (s2value == 25) {
		            				resultN = mycars[s2value];
		            				result +=resultN+"年";
								} else {
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
	            			} else if (punishmenttype == "2") {
								if (s2value >= 22) {
		            				resultN = mycars[22];
		            				result +=resultN+"年";
								} else {
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
	            			} else {
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
	            				//result+="建议提请减去有期徒刑";
	            				if(provincecode==1200){///天津的版本  provincecode==1200
	            					if(merit==0){//没有立功
									 if((s11text&&s7text&&s8text)){
									 	resultY=resultY+s8value;  //从宽加幅度
									 } 
									 if (s12text&&s9text&&s10text&&
									     ((s13value=='0')||
									      ((s13value=='1')&&(jxjs14>=300))||
									      ((s13value=='1')&&(jxjs14<300)&&(s9value=='4'))
									     )
									     ){
									 	resultY=resultY-s10value;  //从严减幅度
									 }
									if(resultY<0){
										resultY=resultY+12;   
										resultN=resultN-1;
									}else if(resultY>=12){
										resultY=resultY-12;
										resultN=resultN+1;
									}
									
									if(resultN==0){
									flag_N=false;
									}
									if(resultY==0){
									flag_Y=false;
									}	         
									if(resultD==0){
									flag_D=false;	
									}
									if(((resultN*12+resultY+resultD/30)>=range)&&s12text&&s9text&&s10text) {//从严并且选择的日期 大于 减刑幅度 调整为减刑幅度
									    if(range<12) {
										 	resultN=0;
										 	resultD=0;
										 	resultY=parseInt(range);
										 	s2.setValue(0);
										    s3.setValue(range);
										 	s4.setValue(0);
									    } else{
									    	resultN=1;
									    	resultY=0;
									    	resultD=0;
									    	s2.setValue(1);
									    	s3.setValue(0);
									    	s4.setValue(0);
									    }

								 }else{
								 	 if((resultN*12+resultY+resultD/30)>12){
								 			resultN=1;
									    	resultY=0;
									    	resultD=0;
									    	s2.setValue(1);
									    	s3.setValue(0);
									    	s4.setValue(0);	
								 	 }
								}									   
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
		            				
	            				}else {//惠州的版本
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
           			} else {
           			    //从宽从严没有选择的情况
           			    if(provincecode==4400){
           			     if(parseInt(s2value) > 25){
	           		   		 //返回总结果 
	            			result_date = s2value;
	           				if(parseInt(s2value)== 9995){
	           					result += "提请减为"+s2text+"徒刑";
	           				}else{
	           					result += "提请减为"+s2text;
	           				}
	           			}else if(parseInt(s2value) > 10){
	           				result += "提请减为有期徒刑";
	           			}else{
	           				result += "提请减去有期徒刑";
	           				flag = false;
	           			}	   
           			    }else{
           			    if(parseInt(s2value) > 25){
	           		   		 //返回总结果 
	            			result_date = s2value;
	           				if(parseInt(s2value)== 9995){
	           					result += "建议减为"+s2text+"徒刑";
	           				}else{
	           					result += "建议减为"+s2text;
	           				}
	           			}else if(parseInt(s2value) > 10){
	           				result += "建议减为有期徒刑";
	           			}else{
	           				result += "建议减去有期徒刑";
	           				flag = false;
	           			}	   
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
	            			if (punishmenttype == "3") {
								if (s2value == 25) {
		            				resultN = mycars[s2value];
		            				result +=resultN+"年";
								} else {
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
	            			} else if (punishmenttype == "2") {
								if (s2value >= 22) {
		            				resultN = mycars[22];
		            				result +=resultN+"年";
								} else {
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
	            			} else {
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
								 
	            				//result+="建议提请减去有期徒刑";
	            				if(provincecode==1200){//天津的版本  provincecode==1200
	            					if(merit==0){//没有立功
	            					if((resultN*12+resultY+resultD/30)>=range) {//选择的日期 大于 减刑幅度 
									    if(range<12) {
										 	resultN=0;
										 	resultD=0;
										 	resultY=parseInt(range);
										 	s2.setValue(0);
										    s3.setValue(range);
										 	s4.setValue(0);
									    } else{
									    	resultN=1;
									    	resultY=0;
									    	resultD=0;
									    	s2.setValue(1);
									    	s3.setValue(0);
									    	s4.setValue(0);
									    }

								 }else{
								 	if((resultN*12+resultY+resultD/30)>12){
								 			resultN=1;
									    	resultY=0;
									    	resultD=0;
									    	s2.setValue(1);
									    	s3.setValue(0);
									    	s4.setValue(0);	
								 	}
								 }
									result_date = result_NYR(resultN,resultY,resultD);
		            				var xzValue=result_N_Y_R(resultN,resultY,resultD);//选择的年月日
						         	var str0=result_range_one(range);
						         	result += result_range(str0,xzValue,resultN,resultY,resultD);
	            				}else{ //立功的暂时不判断 建议幅度
	            				//返回总结果 
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
	            				}else{
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
           			}
            		
           			if(s5value){
           			    if(provincecode==4400){
           			    result += "，剥夺政治权利改为"+s5text;
           			    
           			    }
           			    else{
           			    result += "，剥夺政治权利"+s5text;
           			    
           			    }
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
            var row = new Array([18]);
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
            row[17]=specicalcrim;
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
        		//s7.setEnabled(true);
        		//s8.setEnabled(true);
        		//s9.setEnabled(true);
        		//s10.setEnabled(true);
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
         
         //result_range_num(month)返回的值  （ 12/幅度 ）   和     选择的比较  返回库里该存年月日
         function result_range_num(month,xzValue,N,Y,R){
         	if(month<xzValue){
         		if(month==12){
	         		return result_NYR(1,0,0);
	         	}else {
	         	    if(month<12){
	         	    	return result_NYR(0,month,0);
	         	    } else {
	         	    	return result_NYR(1,0,0);
	         	    }	
	         	}
         	}else {
         		if(xzValue>12){
	         		return result_NYR(1,0,0);
         		}else if(xzValue==12){
         			return result_NYR(1,0,0);
         		}else{
	         		return result_NYR(0,Y,R);
         		}
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
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
        	
        }
        function superPermission(e) {
        	var a = mini.get("select2").value;
        }
        function selectMenu(e) {
        	var b = mini.get("select3").value;
        }
    </script>
</body>
</html>
