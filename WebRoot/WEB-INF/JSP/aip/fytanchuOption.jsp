<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>法院案件办理意见弹出框</title>
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
    .big .mini-textbox-input{
		   font-size:20px;
		   line-height:25px;
		}
    </style>
</head>
<body  onload="myload()">     
    <form id="form1" method="post">
    	<input id="flowdraftid" class="mini-hidden" value="${yjmap.flowdraftid}"/>
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
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
           <a class="mini-button" onclick="CloseWindow"  plain="true" style="width:60px;">取消</a>
           <a class="mini-button" onclick="openOpinionMaintainpage"  plain="true" style="width:80px;">意见维护</a>
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table>
            	<tr>
            	    <td>减刑假释意见：</td>
            		<td width="90px;">    
                    	<input name="select1" id="select1" class="mini-combobox" textField="name" valueField="codeid"  allowInput="false" style="width:100%;" 
                          	onvaluechanged="select1changed" url="ajaxGetCode.json?codeType=FYJX002" value="0"/>
                    </td>
                    <td width="90px;">    
                    	<input id="select2" class="mini-combobox" url="ajaxGetCode.json?codeType=GK056"  showNullItem="true" emptyText="选择年"
							textField="name" valueField="codeid" allowInput="false" style="width:100%;" showNullItem="true"  onvaluechanged="select2changed"/>
                    </td>
                    <td width="90px;">
                    	<input id="select3" class="mini-combobox" url="ajaxGetCode.json?codeType=GK057"  showNullItem="true" emptyText="选择月"
							textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;"/>
                    </td>
                    <td width="90px;">
                   		<input id="select4" class="mini-combobox" url="ajaxGetCode.json?codeType=GK058"  showNullItem="true" emptyText="选择日"
							textField="name" showNullItem="true"  valueField="codeid" allowInput="false" style="width:100%;"/>
                    </td>
            	</tr>
            	<tr>
            	    <td width="90px;">剥夺政治权利：</td>
            	    <td width="90px;">
						<input id="select5" class="mini-combobox" url="ajaxGetCode.json?codeType=GK059"  showNullItem="true" emptyText="选择年"
							textField="name" valueField="codeid" allowInput="false" style="width:100%;" showNullItem="true"  onvaluechanged="select2changed"/>
            	    </td>
            	    <td width="90px;">罚金减免(元)：</td>
            	    <td width="90px;">
            	    	<input id="select6" class="mini-spinner"  maxValue="100000000000" style="width:100%;" enabled="true"/>
            	    </td>
            	</tr>
            	<tr>
            		<td>选择意见：</td>
            		<td colspan="4">
            			<input id="select7" class="mini-combobox"  url="ajaxGetOpinionmaintainSelect.action" showNullItem="true" emptyText="选择意见"
							textField="opinioncontent"   valueField="opinionid" allowInput="false" style="width:100%;"/>
            		</td>
            	</tr>
            	<tr>
            		<td>理由：</td>
            		<td colspan="4">
            			<input id="select8" name="" value="" class="mini-textarea big"  style="width:100%; height: 80px"/>	
            		</td>
            	</tr>
            	<tr>
            		<td>审批意见：</td>
            		<td colspan="4">
            			<input id="select9" name="" value="" class="mini-textarea"  style="width:100%; height: 80px"/>	
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
        function myload(){ selectFirstSuggest();
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
	        var flowdraftid = mini.get("flowdraftid").getValue();
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
	        	mini.get("select7").setValue(jxjs10);
	        	//mini.get("select8").setValue(jxjs8);
	        	mini.get("select9").setValue(jxjs9);
	        }
	        if(jxjs8){
				mini.get("select8").setValue(jxjs8);
			}else if(flowdraftid){//如果是法院立案表才走此方法
	        	$.ajax({
	                url: "generateReasonForSuggest.action?1=1&flowdraftid=" + flowdraftid,
	                type: "post",
	                success: function (text) {
	                	var o  = mini.decode(text);
	                    var liyouobj = mini.get("select8");
	                    if(liyouobj){
	                    	liyouobj.setValue(o.liyou);
	                    }
	                }
	            });
            }
	    } 		
	    function openOpinionMaintainpage(){
	    	window.showModalDialog("toFyopinionMaintainPage.action","","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:460px;dialogWidth:750px");
	    }
	    function onOk(e) {
	        var resultValue = "";
	        var s1value = s1.getValue();
	        var s2value = s2.getValue();
	        var s3value = s3.getValue();
	        var s4value = s4.getValue();
	        var s5value = s5.getValue();
	        var s6value = s6.getValue();
	        var s7value = s7.getValue();
	        var s8value = s8.getValue();
	        var s9value = s9.getValue();
	        var templetValue = "";
	        if(s1value=="0") {
	        	if(s2value=="9995") {
	        	    resultValue = resultValue + "减为无期徒刑";
	        	} else if(s2value>5) {
	        		resultValue = resultValue + "减为有期徒刑" + s2.text + s3.text + s4.text;
	        	} else if(s2value>0||s3value>0||s4value>0){
	        		resultValue = resultValue + "减去有期徒刑" + s2.text + s3.text + s4.text;
	        	}
	        } else {
	        	resultValue = resultValue + s1.text;
	        }
	        var row = new Array([20]);
	        row[18]=resultValue;
	        var boquan="";
	        if(s5value) {
	        	if(s2value=="9995") {
	        		boquan = s5.text+"不变";
	        	   	resultValue = resultValue + "，剥夺政治权利" + s5.text+"不变";
	        	} else{
	        		boquan = "改为"+s5.text;
	        		resultValue = resultValue + "，剥夺政治权利改为" + s5.text;
	        	}
	        	/*
	        	var s7text = s7.text;
	        	if(s7text && s7text.indexOf("改为")>0){
	        		resultValue = resultValue + "，剥夺政治权利改为" + s5.text;
	        	}else{
	        		resultValue = resultValue + "，剥夺政治权利" + s5.text;
	        	}*/
	        }
	        if(s6value>0) {
	        	resultValue = resultValue + "，减免罚金" + s6value + "元";
	        }
	        var jianxingtext = resultValue;//减刑描述 合议庭意见只取减刑意见 不取理由。
	        if(resultValue){
	        	resultValue = resultValue + "。";
	        }
	        if(s9value) {
	        	resultValue = s9value;
	        }

           
            row[0] = s1value;//减刑类型
            row[1] = s2.getValue();//年
            row[2] = s3.getValue();//月
            row[3] = s4.getValue();//日
            row[4] = s5value;//剥权
            row[17] = boquan;
            row[5] = s6value;//罚金减免
            row[6] = s7.text;//选择意见
            row[7] = s8value;//理由
            row[8] = s9value;//审批意见
            row[9] = s7value;
            row[11]=jianxingtext;
            row[15]= resultValue;
            row[16]= "";
            window.returnValue = row;
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
        
        function select1changed() {
        	var s1value = s1.getValue();
        	if(s1value>0) {
        		s2.setValue();
        		s3.setValue();
        		s4.setValue();
        		s5.setValue();
        		s2.setEnabled(false);
        		s3.setEnabled(false);
        		s4.setEnabled(false);
        		s5.setEnabled(false);
        	} else {
        		s2.setEnabled(true);
        		s3.setEnabled(true);
        		s4.setEnabled(true);
        		s5.setEnabled(true);       	
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
        
        function selectFirstSuggest(){
        	$.ajax({
                url: "selectFirstSuggest.action",
                type: "post",
                success: function (text) {
                	var o  = mini.decode(text);
                    var select7 = mini.get("select7");
                    if(select7){
                    	select7.setValue(o.opinionid);
                    }
                }
            });
        }
    </script>
</body>
</html>
