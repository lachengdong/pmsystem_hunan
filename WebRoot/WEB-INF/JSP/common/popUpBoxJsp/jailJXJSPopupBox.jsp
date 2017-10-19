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
	<jsp:include page="/WEB-INF/JSP/common/miniToolbar.jsp"></jsp:include>
	
     <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
     	<!-- 隐藏域：用于存放输入、输出的数据 -->
		<div id="paramdata">
			<input id="jxjs_0" name="jxjs_0" class="mini-hidden" value=""/>
			<input id="jxjs_1" name="jxjs_1" class="mini-hidden" value=""/>
			<input id="jxjs_2" name="jxjs_2" class="mini-hidden" value=""/>
			<input id="jxjs_3" name="jxjs_3" class="mini-hidden" value=""/>
			<input id="jxjs_4" name="jxjs_4" class="mini-hidden" value=""/>
			<input id="jxjs_5" name="jxjs_5" class="mini-hidden" value=""/>
			<input id="jxjs_6" name="jxjs_6" class="mini-hidden" value=""/>
			<input id="jxjs_7" name="jxjs_7" class="mini-hidden" value=""/>
			<input id="jxjs_8" name="jxjs_8" class="mini-hidden" value=""/>
			<input id="jxjs_9" name="jxjs_9" class="mini-hidden" value=""/>
			<input id="jxjs_10" name="jxjs_10" class="mini-hidden" value=""/>
			<input id="jxjs_11" name="jxjs_11" class="mini-hidden" value=""/>
			<input id="jxjs_12" name="jxjs_12" class="mini-hidden" value=""/>
			<input id="jxjs_13" name="jxjs_13" class="mini-hidden" value=""/>
			<input id="payeverymon" name="payeverymon" class="mini-hidden" value=""/>
			<input id="isagree" name="isagree" class="mini-hidden" value=""/>
			<input id="reason" name="reason" class="mini-hidden" value=""/>
			<input id="crimid" name="crimid" class="mini-hidden" value=""/>
			<input id="specicalcrim" name="specicalcrim" class="mini-hidden" value=""/>
			<input id="nowpunishmentyear" name="nowpunishmentyear" class="mini-hidden" value=""/>
			<input id="fullname" name="fullname" class="mini-hidden" value=""/>
			
			<input id="iscommitagree" name="iscommitagree" class="mini-hidden" value=""/>
			<input id="commenttext" name="commenttext" class="mini-hidden" value=""/>
			<input id="jxjs_date" name="jxjs_date" class="mini-hidden" value=""/>
			<input id="title" name="title" class="mini-hidden" value=""/>
			<!-- jail -->
			<input id="popup_fjqopinion" name="popup_fjqopinion" class="mini-hidden" value=""/>
			<input id="popup_jqopinion" name="popup_jqopinion" class="mini-hidden" value=""/>
			<input id="xfkopinion" name="xfkopinion" class="mini-hidden" value=""/>
			<input id="popup_pshopinion" name="popup_pshopinion" class="mini-hidden" value=""/>
			<input id="popup_jyzopinion" name="popup_jyzopinion" class="mini-hidden" value=""/>
			
			<!-- jail 提级 -->
			<input id="popup_tjfjqyj" name="popup_tjfjqyj" class="mini-hidden" value=""/>
			<input id="popup_tjjqyj" name="popup_tjjqyj" class="mini-hidden" value=""/>
			<input id="popup_tjpshyj" name="popup_tjpshyj" class="mini-hidden" value=""/>
			<input id="popup_tjjyzyj" name="popup_tjjyzyj" class="mini-hidden" value=""/>
			
			<!-- province -->
			<input id="popup_gxsjsuggest2" name="popup_gxsjsuggest2" class="mini-hidden" value=""/>
			<input id="popup_gxsjsuggest3" name="popup_gxsjsuggest3" class="mini-hidden" value=""/>
			<input id="popup_gxsjsuggest4" name="popup_gxsjsuggest4" class="mini-hidden" value=""/>
			
			<!-- province 提级-->
			<input id="popup_sjtjsuggest2" name="popup_sjtjsuggest2" class="mini-hidden" value=""/>
			<input id="popup_sjtjsuggest3" name="popup_sjtjsuggest3" class="mini-hidden" value=""/>
			<input id="popup_sjtjsuggest4" name="popup_sjtjsuggest4" class="mini-hidden" value=""/>
			
			<input id="merit" name="merit" class="mini-hidden" value=""/>
			<input id="range" name="range" class="mini-hidden" value=""/>
			<input id="punishmenttype" name="punishmenttype" class="mini-hidden" value=""/>
			
		</div>
		
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
                 	<input id="select11" class="mini-checkbox" text="从宽" trueValue="1" falseValue="0" onvaluechanged="onLenientChanged"/>
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
                  <input id="select12" class="mini-checkbox" text="从严" trueValue="1" falseValue="0" onvaluechanged="onScrictChanged" />
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
              	<select id="Disagree" name="Disagree" class="mini-radiobuttonlist" onvaluechanged="onIsagreeChanged" >
                      <option value="1">同意</option>
                      <option value="0">不同意</option>
                 </select>
            </td>
		</tr>
		
		 <tr>
       		<td colspan="5" rowspan="5">    
           		<input  id="Dreason" name="Dreason" class="mini-textarea" emptyText="请填写否决理由" style="width:100%; height: 135px"/>
           </td>
         </tr>  
     </table>
     
   </div>
     
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
		var s15 = mini.get("Disagree");
		var s16 = mini.get("Dreason");
		
		function disableComponent(){
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
		
		function enableComponent(){
			s1.setEnabled(true);
        	s2.setEnabled(true);
        	s3.setEnabled(true);
        	s4.setEnabled(true);
        	s5.setEnabled(true);
        	s6.setEnabled(true);
        	s7.setEnabled(true);
        	s8.setEnabled(true);
        	s9.setEnabled(true);
        	s10.setEnabled(true);
        	s11.setEnabled(true);
        	s12.setEnabled(true);
        	s13.setEnabled(true);
		}
		
		function clearComponent(){
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
		}
		
    	function initForm(){
    		var jxjs_1 = '';
    		var jxjs_2 = '';
    		var jxjs_3 = '';
    		var jxjs_4 = '';
    		var jxjs_5 = '';
    		var jxjs_6 = '';
    		var jxjs_7 = '';
    		var jxjs_8 = '';
    		var jxjs_9 = '';
    		var jxjs_10 = '';
    		var jxjs_11 = '';
    		var jxjs_12 = '';
    		var jxjs_13 = '';
    		var payeverymon = '';
    		var isagree = '';
    		var reason = '';
    		
    		if(mini.get("jxjs_1")){
    			jxjs_1 = mini.get("jxjs_1").getValue();
    		}
    		if(mini.get("jxjs_2")){
    			jxjs_2 = mini.get("jxjs_2").getValue();
    		}
    		if(mini.get("jxjs_3")){
    			jxjs_3 = mini.get("jxjs_3").getValue();
    		}
    		if(mini.get("jxjs_4")){
    			jxjs_4 = mini.get("jxjs_4").getValue();
    		}
    		if(mini.get("jxjs_5")){
    			jxjs_5 = mini.get("jxjs_5").getValue();
    		}
    		if(mini.get("jxjs_6")){
    			jxjs_6 = mini.get("jxjs_6").getValue();
    		}
    		if(mini.get("jxjs_7")){
    			jxjs_7 = mini.get("jxjs_7").getValue();
    		}
    		if(mini.get("jxjs_8")){
    			jxjs_8 = mini.get("jxjs_8").getValue();
    		}
    		if(mini.get("jxjs_9")){
    			jxjs_9 = mini.get("jxjs_9").getValue();
    		}
    		if(mini.get("jxjs_10")){
    			jxjs_10 = mini.get("jxjs_10").getValue();
    		}
    		if(mini.get("jxjs_11")){
    			jxjs_11 = mini.get("jxjs_11").getValue();
    		}
    		if(mini.get("jxjs_12")){
    			jxjs_12 = mini.get("jxjs_12").getValue();
    		}
    		if(mini.get("jxjs_13")){
    			jxjs_13 = mini.get("jxjs_13").getValue();
    		}
    		if(mini.get("payeverymon")){
    			payeverymon = mini.get("payeverymon").getValue();
    		}
    		if(mini.get("isagree")){
    			isagree = mini.get("isagree").getValue();
    		}
    		if(mini.get("reason")){
    			reason = mini.get("reason").getValue();
    		}
	        
	        enableComponent();
	        if(jxjs_1){
	        	if(jxjs_1 == '1'){ //减刑释放或假释,后边不需要选择了
	        		s2.setEnabled(false);
	        		s3.setEnabled(false);
	        		s4.setEnabled(false);
        		}
	        	mini.get("select1").setValue(jxjs_1);
	        	mini.get("select2").setValue(jxjs_2);
	        	mini.get("select3").setValue(jxjs_3);
	        	mini.get("select4").setValue(jxjs_4);
	        	mini.get("select5").setValue(jxjs_5);
	        	mini.get("select6").setValue(jxjs_6);
	        	mini.get("select7").setValue(jxjs_7);
	        	mini.get("select8").setValue(jxjs_8);
	        	mini.get("select9").setValue(jxjs_9);
	        	mini.get("select10").setValue(jxjs_10);
	        	mini.get("select11").setValue(jxjs_11);
	        	mini.get("select12").setValue(jxjs_12);
	        	mini.get("select13").setValue(jxjs_13);
	        	
	        }else{
	        	s1.setValue('0');
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
    	
    	
    	function beforeOperate(){
    		var isagree = '';
    		var reason = '';
    		if(mini.get('isagree')){
    			isagree = mini.get('isagree').getValue();
    		}
	        if(mini.get('reason')){
    			reason = mini.get('reason').getValue();
    		}
	        //判断是否同意
	     	 if(isagree && isagree==0){
	        	s1.setValue("");
		        disableComponent();
	            document.getElementById("Dreason").style.display = "block";
	            mini.get("Disagree").setValue(isagree);
        		mini.get("Dreason").setValue(reason);
	         }else{//其它默认同意
	        	 s15.setValue(1);
	        	 s16.setValue("");
	        	 document.getElementById("Dreason").style.display = "none";
	        	 //如果页面传值过来，把值填充到列表框
	        	 initForm();
	        }
    		
    		var crimid = mini.get('crimid').getValue();
    		var url = '<%=path%>/getCommuteRangeInfo.json?1=1';
			$.ajax({
				type:"POST", 
				url: url,
				data:{crimid:crimid},
				cache:false,
				async:false,
				success:function (text){
					if(text){
						var result = mini.decode(text);
						var int5 = result['int5'];
						var int6 = result['int6'];
						var range = result['range'];
						var punishmenttype = result['punishmenttype'];
						
						if(int5>0 || int6>0){
							mini.get('merit').setValue("1"); //记功 
						}
						if(range){
							mini.get('range').setValue(range); //减刑幅度
						}
						if(punishmenttype){
							mini.get('punishmenttype').setValue(punishmenttype); 
						}
						
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
			    }
			});
			
			var show = showDocInfo(); 
		    document.getElementById('doc').innerText=show;
    	}
    	
    	function showDocInfo(){
    		var range = '';
    		var merit = '';
    		if(mini.get('range')){
    			range = mini.get('range').getValue();
    		}
    		if(mini.get('merit')){
    			merit = mini.get('merit').getValue();
    		}
    		
	        var result = "";
	        var flag;
	        var fd = MtoN(range);
	      	if(merit != 0){
	      		result+="有立功表现,"
	      	}
	      	if(range==9995){
	      		result+="建议减刑幅度:减为无期。";
	      	}else{
		      	if(range>=10*12){
		      		result+="建议减刑幅度:减为有期徒刑"+fd+"。";
		      	}else{
			      	if(fd!=''){
				      	result+="建议减刑幅度:减去有期徒刑"+fd+"。";
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
       	 		//result+="1年";
       			//mini.get("range").setValue(12);	
       	 	}
       	 	return result;
       }
    	
	      
        function onLenientChanged(e){
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
        
        function onScrictChanged(e){
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
         
         function onIsagreeChanged(e){
	         var rbl = mini.get("Disagree").getValue();
	         if(rbl==1){
	         	s16.setValue("");
	         	document.getElementById("Dreason").style.display = "none";
	         	initForm();
	         }else{
	         	document.getElementById("Dreason").style.display = "block";
	         	var tempid = "9994";
	         	$.ajax({
			         url: "<%=path%>/getSystemTemplateStr.action", 
			         data: {tempid:tempid},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	mini.get("Dreason").setValue(text);
			         }
				});
	         	
	         	clearComponent();
	         	disableComponent();
	         }
	         
         }
        
        var mycars=new Array("零","一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十")
		//var mycars=new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾","拾壹","拾贰","拾叁","拾肆","拾伍","拾陆","拾柒","拾捌","拾玖","贰拾","贰拾壹","贰拾贰","贰拾叁","贰拾肆","贰拾伍","贰拾陆","贰拾柒","贰拾捌","贰拾玖","叁拾")
        function afterOperate(e){
        	//var range = mini.get("range").getValue();
        	var result = "";
        	var title = "";
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
            if(isNaN(s8value)){
            	s8value=0;
            }
            
            var s9value = s9.getValue();
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
            
            var flag = true;
            //存到库里的年月日 
            var jxjs_N;
            var jxjs_Y;
            var jxjs_R;
            var result_date="";
            if(s1value){
            	var fullname = '';
            	if(mini.get('fullname')){
            		fullname = mini.get('fullname').getValue();
            	}
            	
            	if(s1value == '0'){
            		//选择减刑幅度
            		if(s2value==0 && s3value==0 && s4value==0){
            			alert("请选择减刑幅度");
            			return false;
            		}
            		if(s11text && s7text && s8text){
           				result += "该犯符合《规程》";
	            		result += s7text + "规定，从宽" + s8text + "，";
           			}
            		if(s12text && s9text && s10text){
           				result += "该犯符合《规程》";
	            		result += s9text + "规定，从严" + s10text+"，";
           			}
            		if(s13value == '1'){
            		    result += "符合家庭困难标准，";
            		}
            		
            		
           		    if(parseInt(s2value) > 25){
           		   		 //返回总结果 
           				if(parseInt(s2value)== 9995){
           					result += "changeKey减为"+s2text+"徒刑";
           				}else{
           					result += "changeKey减为"+s2text;
           				}
           				
           			}else if(parseInt(s2value) > 10){
           				result += "changeKey减为有期徒刑";
           			}else{
           				result += "changeKey减去有期徒刑";
           			}
           			 
            		var resultN = s2value;
           			var resultY = s3value;
           			var resultD = s4value;
           			
           			var flag_N = true;
           			var flag_Y = true;
           			var flag_D = true;
           			
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
           			if(s5value){
           				result += "，剥夺政治权利"+s5text;
           			}
           			var nowpunishmentyear = mini.get('nowpunishmentyear').getValue();
           			
           			if(nowpunishmentyear=='9996' || nowpunishmentyear=='9995'){
           				title = fullname + '\n' + '死刑缓期二年执行、无期徒刑罪犯减刑审核表';
           			}else{
           				title = fullname + '提请减刑审核表';
           			}
           			
            	}else if(s1value == '1'){//假释
            		result = "changeKey假释";
            		if(s5value){
           				result += "，剥夺政治权利"+s5text;
           			}
           			title = fullname + '罪犯假释审核表';
           			
            	}
            	if(s6text){
           			result +="，罚金:"+s6text+"万元";
           		}	
            }
            
            if(s15.getValue() != 1){
	            result = s16.getValue();
            }
            
            //result += "。";
            if(s15.getValue()==0 && s16.getValue().trim()==''){
            	alert("请填写否决意见！");
            	return false;
            }
            
           mini.get("jxjs_1").setValue(s1value);
           mini.get("jxjs_2").setValue(s2.getValue());
           mini.get("jxjs_3").setValue(s3.getValue());
           mini.get("jxjs_4").setValue(s4.getValue());
           mini.get("jxjs_5").setValue(s5value);
           mini.get("jxjs_6").setValue(s6value);
           mini.get("jxjs_7").setValue(s7.getValue());
           
           mini.get("jxjs_8").setValue(s8.getValue());
           mini.get("jxjs_9").setValue(s9.getValue());
           mini.get("jxjs_10").setValue(s10.getValue());
           mini.get("jxjs_11").setValue(s11.getValue());
           mini.get("jxjs_12").setValue(s12.getValue());
           mini.get("jxjs_13").setValue(s13value);
           
           mini.get("isagree").setValue(s15.getValue());
           mini.get("reason").setValue(s16.getValue());
           
           mini.get("iscommitagree").setValue(s15.getValue());
           var tempResult = result.replace("changeKey","").replace("　　","");
           mini.get("commenttext").setValue(tempResult);
           mini.get("jxjs_date").setValue(result_date);
           mini.get("title").setValue(title);
           //mini.get("popup_fjqopinion").setValue(result);
           
           
           var fjqopinion = "";
           var jqopinion = "";
           var xfkopinion = "";
           var pshopinion = "";
           var jyzopinion =  "";
           
           var tjfjqyj = "";
           var tjjqyj = "";
           var tjpshyj = "";
           var tjjyzyj = "";
           
           var gxsjsuggest2 = "";
           var gxsjsuggest3 = "";
           var gxsjsuggest4 =  "";
           
           var sjtjsuggest2 = "";
           var sjtjsuggest3 = "";
           var sjtjsuggest4 =  "";
           
           if(s15.getValue() && s15.getValue()==0){
           		result = "　　" + result + "。";
           		var inputparam = mini.get("inputparam").getValue();
           		if(inputparam.indexOf("popup_fjqopinion") > -1){
           			fjqopinion = result;
           		}else if(inputparam.indexOf("popup_jqopinion") > -1){
           			jqopinion = result;
           		}else if(inputparam.indexOf("popup_pshopinion") > -1){
           			pshopinion = result;
           		}else if(inputparam.indexOf("popup_jyzopinion") > -1){
           			jyzopinion =  result;
           		}
           		
           		else if(inputparam.indexOf("popup_tjfjqyj") > -1){
           			tjfjqyj = result;
           		}else if(inputparam.indexOf("popup_tjjqyj") > -1){
           			tjjqyj = result;
           		}else if(inputparam.indexOf("popup_tjpshyj") > -1){
           			tjpshyj = result;
           		}else if(inputparam.indexOf("popup_tjjyzyj") > -1){
           			tjjyzyj = result;
           		}
           		
           		else if(inputparam.indexOf("popup_gxsjsuggest2") > -1){
           			gxsjsuggest2 = result;
           		}else if(inputparam.indexOf("popup_gxsjsuggest3") > -1){
           			gxsjsuggest3 = result;
           		}else if(inputparam.indexOf("popup_gxsjsuggest4") > -1){
           			gxsjsuggest4 =  result;
           		}
           		
           		else if(inputparam.indexOf("popup_sjtjsuggest2") > -1){
           			sjtjsuggest2 = result;
           		}else if(inputparam.indexOf("popup_sjtjsuggest3") > -1){
           			sjtjsuggest3 = result;
           		}else if(inputparam.indexOf("popup_sjtjsuggest4") > -1){
           			sjtjsuggest4 =  result;
           		}

           }else{
           		//监狱意见
	           fjqopinion = "　　经分监区警察集体评议，" + result.replace("changeKey","拟报") + "。";
	           jqopinion = "　　经监区长办公会审核，" + result.replace("changeKey","拟报") + "，报监狱减刑、假释评审委员会评审。";
	           xfkopinion = "　　经审查，符合减刑法定条件，呈监狱减刑假释评审委员会评审。";
	           pshopinion = "　　经评审委员会会议评审，" + result.replace("changeKey","拟报") + "，提交监狱长办公会决定。";
	           jyzopinion =  "　　经监狱长办公会会议决定，" + result.replace("changeKey","同意报") + "。";
	           
	           //监狱提级意见
	           tjfjqyj = "　　经分监区集体评议，" + result.replace("changeKey","拟提请呈报") + "。";
	           tjjqyj = "　　经监区长办公会审核，" + result.replace("changeKey","同意呈报") + "，报监狱减刑、假释评审委员会评审。";
	           tjpshyj = "　　经评审委员会会议评审，" + result.replace("changeKey","同意提请") + "，提交监狱长办公会决定。";
	           tjjyzyj =  "　　经监狱长办公会会议决定，" + result.replace("changeKey","同意提请") + "，报监狱管理局审批。";
	           
	           
	            //省局意见
	           gxsjsuggest2 = "　　经处务会审查，" + result.replace("changeKey","建议提请") + "，提交局减刑、假释评审委员会评审。";
	           gxsjsuggest3 = "　　经评审委员会会议评审，" + result.replace("changeKey","同意提请") + "，提交局长审定。";
	           gxsjsuggest4 =  "　　" + result.replace("changeKey","同意提请") + "。";
	           
	           sjtjsuggest2 = "　　经处务会审查，" + result.replace("changeKey","建议提请") + "，提交局减刑、假释评审委员会评审。";
	           sjtjsuggest3 = "　　经评审委员会会议评审，" + result.replace("changeKey","同意提请") + "，提交局长审定。";
	           sjtjsuggest4 = "　　" + result.replace("changeKey","同意提请") + "。";
	           
           }
           
           mini.get("popup_fjqopinion").setValue(fjqopinion);
           mini.get("popup_jqopinion").setValue(jqopinion);
           mini.get("xfkopinion").setValue(xfkopinion);
           mini.get("popup_pshopinion").setValue(pshopinion);
           mini.get("popup_jyzopinion").setValue(jyzopinion);
           
           mini.get("popup_tjfjqyj").setValue(tjfjqyj);
           mini.get("popup_tjjqyj").setValue(tjjqyj);
           mini.get("popup_tjpshyj").setValue(tjpshyj);
           mini.get("popup_tjjyzyj").setValue(tjjyzyj);
           
           mini.get("popup_gxsjsuggest2").setValue(gxsjsuggest2);
           mini.get("popup_gxsjsuggest3").setValue(gxsjsuggest3);
           mini.get("popup_gxsjsuggest4").setValue(gxsjsuggest4);
           
           mini.get("popup_sjtjsuggest2").setValue(sjtjsuggest2);
           mini.get("popup_sjtjsuggest3").setValue(sjtjsuggest3);
           mini.get("popup_sjtjsuggest4").setValue(sjtjsuggest4);
           
           
           //对标题的处理
           if(false){
           	   //mini.get("title").setValue("");
           }
             
        }
        
        function select1changed() {
        	var s1v = s1.getValue();
        	clearComponent();
        	enableComponent();
        	s7.setEnabled(false);
        	s8.setEnabled(false);
        	s9.setEnabled(false);
        	s10.setEnabled(false);
        	
        	if(s1v =='1'){ //假释,后边不需要选择了
        		s1.setValue('1');
        		s2.setEnabled(false);
	        	s3.setEnabled(false);
	        	s4.setEnabled(false);
        	}else{
        		s1.setValue('0');
        	}
        }
        
        //返到表单上的年月日串
        function result_NYR(N,Y,R){
        	var result_date=N+","+Y+","+R;
        	return result_date;
        }
        
         function select2changed(){
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
    </script>
</body>
</html>
