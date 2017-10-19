<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String commenttext = "";
String path = request.getContextPath();
String isagree = request.getParameter("isagree");
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>审批意见窗口</title>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
    <style type="text/css">
		html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        width:100%;
	        overflow:hidden; 
	    }
	    .big .mini-textbox-input{
		   font-size:20px;
		   line-height:25px;
		}
	</style>
  </head>
  
  <body>
  <div id="form1">
  <div class="mini-toolbar" style="border-bottom:0;padding:1px;" >               
       <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
       <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a> 
        <input id="cjiimprisontype" name="cjiimprisontype" type="hidden" value="1"/>
    </div>
     <div class="mini-fit" style="padding-left:11px;">
         <table style="table-layout:fixed;">
                <tr>
                    <td width="90px;">减刑假释意见：</td>
                     <td width="80px;">    
                          <input id="select1" class="mini-combobox" textField="text" valueField="id"  allowInput="false" style="width:100%;" 
                          	onvaluechanged="select1changed" data="jianxing"  value="1"/>
                    </td>
                     <td width="80px;">    
                          <input id="select2" class="mini-combobox" data="xinqiyear"  textField="text" valueField="id" allowInput="false" 
                          style="width:100%;"  onvaluechanged="select2changed" />
                    </td>
               
                     <td width="80px;">    
                          <input id="select3" class="mini-combobox" data="xinqimonth" textField="text" valueField="id" allowInput="false" 
                          style="width:100%;" />
                    </td>
                     <td width="80px;">    
                          <input id="select4" class="mini-combobox" data="xinqiday"  textField="text" valueField="id" allowInput="false"
                           style="width:100%;"  />
                    </td>
                </tr>
                <tr>
                	<td width="90px;">剥夺政治权利：</td>
                   <td  width="80px;">    
                        <input id="select5" class="mini-combobox" data="bozhenyear" textField="text" valueField="id" allowInput="false" 
                        style="width:100%;" />
                   </td>
                
                   <td width="80px;">罚金减免：</td>
                   <td   align="left" colspan="2">    
						<input id="select6" name="select6" class="mini-textbox" style="width:80px;"/>万元
                   </td>
                </tr>
                 <tr>
                 <td width="40px;">理由：</td>
                 <td align="left">    
					<input id="commenttext" name="commenttext" class="mini-textarea big" style="width:380px;height:80px;"/>
                 </td>
             </tr>
            </table>
     </div>
     </div>
     <script type="text/javascript">
     	var jianxing = [{id:1, text: '减刑'},{id:2, text:'减刑释放'},{id:3, text:'假释'},{id:4, text:'减免罚金'},{id:5, text:'不予提请'}];//1 -- 5 , 2--6, 3--7
		var xinqiyear =[{id:1, text: '一年'},{id:2, text: '二年'},{id:3, text: '三年'},{id:4, text: '四年'},{id:9995, text: '无期'},{id:9996, text: '死缓'}];
		var xinqimonth = [{id:1, text: '一个月'},{id:2, text: '二个月'},{id:3, text: '三个月'},{id:4, text: '四个月'},{id:5, text: '五个月'}];
		var xinqiday = [{id:1, text: '一天'},{id:2, text: '二天'},{id:3, text: '三天'},{id:4, text: '四天'},{id:5, text: '五天'}];
		var bozhenyear = [{id:1, text: '一年'},{id:2, text: '二年'},{id:3, text: '三年'},{id:4, text: '四年'},{id:5, text: '五年'},{id:6, text: '终身'}];
     	mini.parse();
		var s1 = mini.get("select1");
		var s2 = mini.get("select2");
		var s3 = mini.get("select3");
		var s4 = mini.get("select4");
		var s5 = mini.get("select5");
		var s6 = mini.get("select6");
		var cjiimprisontype = document.getElementById("cjiimprisontype").value;
    	var form = new mini.Form("form1");
	    function CloseWindow(action) {            
	         if (action == "close" && form.isChanged()) {
	             if (confirm("数据被修改了，是否先保存？")) {
	                 return false;
	             }
	         }
	         if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	         else window.close();            
	     }
	    function onOk(e) {
     	var result = "";
     	
         var s1value = s1.getValue();
         var s2value = s2.getValue();
         var s3value = s3.getValue();
         var s4value = s4.getValue();
         var s5value = s5.getValue();
         var s6value = s6.getValue();
         var s2text = s2.text;
         var s3text = s3.text;
         var s4text = s4.text;
         var s5text = s5.text;
         var s6text = s6.getValue();
         if(s1value){
         	if(s1value == '5'){
         		if(parseInt(cjiimprisontype)!= '1'){//不是有期的
         			if(parseInt(s2value) > 25){
         				if(parseInt(s2value)== 9995){
         					result = "提请减为"+s2text+"徒刑";
         				}else{
         					result = "提请减为"+s2text;
         				}
         			}else{
	           				result = "提请减为有期徒刑"+s2text;
	           			}
	           			if(s2text){
		           			if(s3text){
		           				result += "又" + s3text;
		           			}
	           			}else{
	           				result +=  s3text;
	           			}
         		}else{
         			if(parseInt(s2value) < 26){
         				result = "提请减去有期徒刑"+s2text+s3text+s4text;
         			}else if(parseInt(s2value)== 9995){
         				result = "提请减为"+s2text+"徒刑";
         			}else{
         				result = "提请减为"+s2text;
         			}
         			if(!s2text){
         				if(s3text){
         					result = "提请减去有期徒刑"+s3text+s4text;
         				}else{
         					result = "提请减去有期徒刑"+s4text;
         				}
         			}
         		}
        			if(s5value){
        				result += "，剥夺政治权利"+s5text;
        			}
        			result += "。";
         	}else if(s1value == '6'){
         		result = "提请减去剩余刑期";
         		if(s5value){
        				result += "，剥夺政治权利"+s5text;
        			}
        			result += "。";
         	}else if(s1value == '7'){
         		result = "提请假释";
         		if(s5value){
        				result += "，剥夺政治权利"+s5text;
        			}
        			result += "。";
         	}else if(s1value == '4'){
         		if(parseInt(s2value) > 25){
	           			result = "提请加刑为"+s2text;
         		}else{
         			result = "提请加刑"+s2text+s3text+s4text;
         		}
         		if(s5value){
        				result += "，剥夺政治权利"+s5text;
        			}
         		result += "。";
         	}

         	if(s6text){
        			result +="罚金:"+s6text+"万元。";
        		}	
         	 
         }
        
         var row = new Array([7]);
         row[0] = "ok";
         row[1] = s1value;
         row[2] = s2value;
         row[3] = s3value;
         row[4] = s4value;
         row[5] = s5value;
         row[6] = result;
         window.returnValue = row;
         CloseWindow("cancel");
     }
     function select1changed() {
     	var s1v = s1.getValue();
     	if(s1v == '6' || s1v == '7'){ //减刑释放或假释,后边不需要选择了
     		s2.setValue();
     		s3.setValue();
     		s4.setValue();
     		s5.setValue();
     		s2.setEnabled(false);
     		s3.setEnabled(false);
     		s4.setEnabled(false);
     		//s5.setEnabled(false);
     	}else{
     		s2.enable();
     		s3.enable();
     		s4.enable();
     		//s5.enable();
     	}
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
