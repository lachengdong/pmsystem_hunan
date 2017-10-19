<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>表单弹出框</title>
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
			<!-- 表头 -->
			<input id="jxojs" name="jxojs" class="mini-hidden" value=""/>
		    <!-- 分监区、监区、科室、监狱、省局意见栏 -->
			<input id="popup_text1" name="popup_text1" class="mini-hidden" value=""/>
			<input id="popup_text2" name="popup_text2" class="mini-hidden" value=""/>
			<input id="popup_text3" name="popup_text3" class="mini-hidden" value=""/>
			<input id="popup_text4" name="popup_text4" class="mini-hidden" value=""/>
			<input id="popup_text5" name="popup_text5" class="mini-hidden" value=""/>
			<!-- 减刑类型、减刑年、减刑月、减刑日、剥夺政治权利年、提请意见 -->
			<input id="opiniontype" name="opiniontype" class="mini-hidden" value=""/>
			<input id="opinionyear" name="opinionyear" class="mini-hidden" value=""/>
			<input id="opinionmonth" name="opinionmonth" class="mini-hidden" value=""/>
			<input id="opinionday" name="opinionday" class="mini-hidden" value=""/>
			<input id="bozhengyear" name="bozhengyear" class="mini-hidden" value=""/>
			<input id="fenjianquopinion" name="opinion" class="mini-hidden" value=""/>
			<input id="jianquopinion" name="opinion" class="mini-hidden" value=""/>
			<input id="keshiopinion" name="opinion" class="mini-hidden" value=""/>
			<input id="jianyuopinion" name="opinion" class="mini-hidden" value=""/>
			<input id="shengjuopinion" name="opinion" class="mini-hidden" value=""/>
			
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
     </div>
     
    <script type="text/javascript">
    mini.parse();
	var s1 = mini.get("select1");
	var s2 = mini.get("select2");
	var s3 = mini.get("select3");
	var s4 = mini.get("select4");
	var s5 = mini.get("select5");
    var fjqSuggest = '　　根据有关法律法规和该犯的改造表现，经分监区集体讨论，建议提请';
	var jqSuggest =  '　　经监区长办公会审查，情况属实，建议提请';
	var ksSuggest =  '　　经审核，同意提请';
	var jySuggest =  '　　同意提请';
	var sgSuggest =  '　　同意提请';
    var mycars=new Array("零","一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八","二十九","三十")
    function onOk(e) {
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
        		}else if (s2value == '9996'){
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
        //设置隐藏域的值
        if(s1value=='1'){
        	mini.get("jxojs").setValue("罪犯假释审核表");
		}else{
			mini.get("jxojs").setValue("罪犯减刑审核表");
		}
        mini.get("popup_text1").setValue(fjqSuggest+opion+"。");
        mini.get("popup_text2").setValue(jqSuggest+opion+"。");
        mini.get("popup_text3").setValue(ksSuggest+opion+"。");
        mini.get("popup_text4").setValue(jySuggest+opion+"。");
        mini.get("popup_text5").setValue(sgSuggest+opion+"。");
        
        mini.get("opiniontype").setValue(s1value);
        mini.get("opinionyear").setValue(s2value);
        mini.get("opinionmonth").setValue(s3value);
        mini.get("opinionday").setValue(s4value);
        mini.get("bozhengyear").setValue(s5value);
        
        mini.get("fenjianquopinion").setValue(opion);
        mini.get("jianquopinion").setValue(opion);
        mini.get("keshiopinion").setValue(opion);
        mini.get("jianyuopinion").setValue(opion);
        mini.get("shengjuopinion").setValue(opion);
        CloseWindow("ok");
    }
    
	    function onCancel(){
	        CloseWindow("cancel");
	    }

	    //////////////////////////////////
	    function CloseWindow(action){
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
    </script>
<!-- 由开发人员编写结束 -->
<!--  -->
<!--  -->
<!--  -->
<!--  -->

    
</body>
</html>
