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
        var form = new mini.Form("form1");
        
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
        function onOk2(e) {
        	//处理下拉框的值 
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
			if(isNaN(s5value)){
            	s5value=0;
            }
            var s1text = s1.text;
            var s2text = s2.text;
            var s3text = s3.text;
            var s4text = s4.text;
            var s5text = s5.text;
            
            //存到数据库中的一些信息
            var opion='';//完整意见（xx年xx个月xx天/假释/减去余刑）
            if(s1value =='1'){
            	//假释
            	opion = "假释";
            }else if (s1value =='2'){
            	//减去余刑
            	opion = "减去剩余刑期";
            }else{
            	//减刑
            	if((s2value+s3value+s4value) > 0){
            		if(s2value == '9995'){
            			opion += "减为无期徒刑";
            		}else if (s2value == '9995'){
            			opion += "减为死缓";
            		}else{
						if(s2value > 10){
							opion += "减为有期徒刑";
						}else{
							opion += "减去刑期";
						}
						opion += s2text+s3text+s4text;
            		}
            	}else{
            		alert("请选择减刑幅度！");
            		return false;
            	}
            }
			
			//剥夺政治权利
			if(s5value > 0){
				opion += ",剥夺政治权利"+s5text;
            }

            var row = new Array([5]);
            row[0] = s1value;
            row[1] = s2.getValue();
            row[2] = s3.getValue();
            row[3] = s4.getValue();
            row[4] = s5value;
            row[5]= opion;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        
        function select1changed() {
        	var s1v = s1.getValue();
        	if(s1v =='1' || s1v =='2'){ //减去余刑或者假释,后边不需要选择了
        		s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s2.setEnabled(false);
        		s3.setEnabled(false);
        		s4.setEnabled(false);
        	}else{
        		s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s2.setEnabled(true);
        		s3.setEnabled(true);
        		s4.setEnabled(true);
        	}
        }
        //返到表单上的年月日串
        function result_NYR(N,Y,R){
        	var result_date=N+","+Y+","+R;
        	return result_date;
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
