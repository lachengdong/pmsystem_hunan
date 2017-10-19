<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>老病残信息补录新增</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"rel="stylesheet" type="text/css" />
	<style type="text/css">
		html,body {
			font-size: 12px;padding: 0;margin: 0;border: 0;height: 100%;overflow: auto;
		}
	</style>
</head>
<body onload="init('${menuid}')">
<form id="form1" method="post">
	<div class="mini-toolbar mini-grid-headerCell" style="padding: 1px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>	
		<table>
			<tr>
				<td style="width: 100%;">
					<a style="display:none;width: 60px" id="11782" class="mini-button" iconCls="icon-save" onclick="onOk" plain="true" >存盘</a>
					<a style="display:none;width: 60px" id="11785" class="mini-button" iconCls="icon-save" onclick="SaveData" plain="true" >存盘</a>
					<a class="mini-button" onclick="onCancel" plain="true" iconCls="icon-close" style="width: 60px;">关闭</a>
        		</td>
				<td style="white-space: nowrap;">
				</td>
			</tr>
		</table>
	</div>
	<div style="padding-left: 15px; padding-bottom: 5px;">
		<table style="table-layout: fixed;">
			<tr style="height: 10px;">
				<td style="width: 90px;">审&nbsp;&nbsp;批&nbsp;&nbsp;种&nbsp;&nbsp;类：</td>
				<td >
					<!--  
					<input id="oidtype" name="oidtype" class="mini-combobox" emptyText="请选择..." showNullItem="true" nullItemText="请选择..."
						valueField="codeid" textField="name" required="true" url="ajaxGetCode.json?1=1&codeType=GKM009" onvaluechanged="onOidtypeChanged"/>
					-->
						
					<input id="oidtype" name="oidtype" class="mini-treeselect" url="<%=path%>/txt/lbcdata.txt" valueField="id" textField="text" parentField="pid" multiSelect="true"
					showFolderCheckBox="true" expandOnLoad="true" style="width:150px" emptyText="请选择..." onvaluechanged="onOidtypeChanged"/>
						
					
				</td>
				
				
				<td style="width: 60px;">认定时间：</td>
				<td >
					<input id="confirmtime" name="confirmtime" format="yyyy-MM-dd HH:mm:ss" showTime="true" style="width: 150px;"  class="mini-datepicker" required="true"/>
				</td>
				
			</tr>
			<tr>
				<td style="width: 60px;">老犯类别：</td>
				<td >
					<input id="oldtype" name="oldtype" class="mini-combobox" emptyText="请选择..." showNullItem="true" style="width:150px"
						valueField="id" textField="text" required="true" data="oldtypeData" />
				</td>
				
				<td style="width: 60px;">病犯类别：</td>
				<td >
					<input id="sicktype" name="sicktype" class="mini-combobox" emptyText="请选择..." showNullItem="false" style="width:150px" multiSelect="true"
						valueField="id" textField="text" required="true" data="sicktypeData" />
				</td>
				
				<td style="width: 60px;">残犯类别：</td>
				<td >
					<input id="disabilitytype" name="disabilitytype" class="mini-combobox" emptyText="请选择..." showNullItem="false" style="width:150px" multiSelect="true"
						valueField="id" textField="text" required="true" data="disabilitytypeData" /> 			<!-- url="ajaxGetCode.json?1=1&codeType=GB050" -->
				</td>
			
			</tr>
			
			
			
			<tr style="height: 10px;">
				<td style="width: 90px;">审批种类明细：</td>
				<td colspan=5>
					<input id="oidtypedetail" name="oidtypedetail" class="mini-textbox" vtype="maxLength:16" required="true" style="width: 100%;"/>
				</td>
			</tr>
			<tr style="height: 12px;">
				<td style="width: 90px;">健康情况概述：</td>
				<td colspan=5>
					<input id="description" name="description" class="mini-textarea" vtype="maxLength:500" style="width: 100%;"/>
				</td>
			</tr>
			<tr style="height: 12px;">
				<td style="width: 90px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
				<td colspan=5>
					<input id="remark" name="remark" class="mini-textarea" vtype="maxLength:500" style="width: 100%;"/>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	var oldtypeData = [{id:"75岁以上",text:"75岁以上"},{id:"65岁至75岁",text:"65岁至75岁"}];
	var sicktypeData = [{id:"心血管疾病",text:"心血管疾病"},{id:"颅脑疾病",text:"颅脑疾病"},{id:"呼吸系统疾病",text:"呼吸系统疾病"},
	                    {id:"消化系统疾病",text:"消化系统疾病"},{id:"泌尿系统疾病",text:"泌尿系统疾病"},{id:"内分泌系统或代谢性疾病",text:"内分泌系统或代谢性疾病"},
	                    {id:"精神系统疾病",text:"精神系统疾病"},{id:"血液系统疾病",text:"血液系统疾病"},{id:"风湿性及骨科疾病",text:"风湿性及骨科疾病"},
	                    {id:"皮肤科疾病",text:"皮肤科疾病"},{id:"各种恶性肿瘤",text:"各种恶性肿瘤"},{id:"五官科疾病",text:"五官科疾病"},
	                    {id:"妇科疾病",text:"妇科疾病"},{id:"因伤病行器官手术或组织器官移植者",text:"因伤病行器官手术或组织器官移植者"},{id:"艾滋病二期",text:"艾滋病二期"},
	                    {id:"晚期梅毒",text:"晚期梅毒"},{id:"神经系统疾病",text:"神经系统疾病"},{id:"精神疾病",text:"精神疾病"},
	                    {id:"其他久治不愈影响生活伤病",text:"其他久治不愈影响生活伤病"},{id:"六十岁以上且患上述疾病",text:"六十岁以上且患上述疾病"},{id:"严重传染病",text:"严重传染病"}
					   ];
	var disabilitytypeData = [{id:"视力残疾",text:"视力残疾"},{id:"听力残疾",text:"听力残疾"},{id:"言语残疾",text:"言语残疾"},{id:"肢体残疾",text:"肢体残疾"},{id:"智力残疾",text:"智力残疾"},{id:"精神残疾",text:"精神残疾"},{id:"其它残疾",text:"其它残疾"}];
	
	mini.parse();
  	var form = new mini.Form("form1");
  	
  	function onOk(e) {
  		/*
    	var disabilitytype = mini.get("disabilitytype").getText();
  		$.ajax({
            url: "ISOldResidualInformationCollection.action?1=1&crimid=${crimid}",
            data: {disabilitytype:disabilitytype},
            type: 'POST',
            cache: false,
            success: function (text) {
	            if(text == 1){
	            	alert("该名罪犯的残疾类别已存在，请重新选择！");
	            }
	            else if(text == 0){
	            	SaveData();
	            }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            	
            }
		});
  		*/
  		SaveData();
    }
  	
    function SaveData() {
       form.validate();
       if (form.isValid() == false) return;
       var o = form.getData();
       o.confirmtime = mini.get("confirmtime").getText();
       var json = mini.encode([o]);
       //var operator = mini.get("operator").getValue(); 
       
       //var disabilitytype = mini.get("disabilitytype").getText(); 
       //var oidtype = mini.get("oidtype").getText();
       //var confirmtime = mini.get("confirmtime").getText();
       //alert(mini.get("confirmtime").getValue());
       //alert(mini.get("confirmtime").getText());
       
       var url = "saveOldResidualInformationCollection.action?1=1&crimid=${crimid}";
       $.ajax({
           url: url,
           data: {data : json},
           type: 'POST',
           cache: false,
           success: function (text) {
           		alert("操作成功!");
           		onCancel("save");
           },
           error: function (jqXHR, textStatus, errorThrown) {
           		alert("操作失败!");
           }
       }); 
    }
    
    
	  //关闭窗口
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
      //标准方法接口定义
      function SetData(data) {
          //跨页面传递的数据对象，克隆后才可以安全使用
          data = mini.clone(data);
          mini.get("crimid").setValue(data.crimid);
          mini.get("operator").setValue(data.action);
          
          if(data.action=="new"){
              mini.get("confirmtime").setValue(new Date());
          }else {
              mini.getbyName("confirmtime").setEnabled(false);
              var crimid=data.crimid;
              
              mini.get("id").setValue(data.id);
              
              mini.get("oidtype").setValue(data.oidtype);
	          //mini.get("oidtype").setText(data.oidtype);
	          
	          mini.getbyName("confirmtime").setValue(mini.formatDate ( new Date(data.confirmtime), "yyyy-MM-dd HH:mm:ss" ));
	          
	          mini.get("oldtype").setValue(data.oldtype);
	          mini.get("sicktype").setValue(data.sicktype);
	          mini.get("disabilitytype").setValue(data.disabilitytype);
	          
	          //mini.get("disabilitytype").setText(data.disabilitytype);
	          
	          mini.get("oidtypedetail").setValue(data.oidtypedetail);
	          mini.get("description").setValue(data.description);
	          mini.getbyName("remark").setValue(data.remark);
          }
          
          onOidtypeChanged();//初始化各组件
      }
      
      function onOidtypeChanged(){
    	  
    	  var oidtype = mini.get("oidtype").getValue();
    	  var oidtypeArr = oidtype.split(",");
    	  
    	  if(arrContains(oidtypeArr,"一级老犯") && arrContains(oidtypeArr,"二级老犯")){
    		  alert("老犯只能选择一种类型");
    		  removeByValue(oidtypeArr,"一级老犯");
    		  removeByValue(oidtypeArr,"二级老犯");
    	  }
    	  
    	  if(arrContains(oidtypeArr,"一级病犯") && arrContains(oidtypeArr,"二级病犯")){
    		  alert("病犯只能选择一种类型");
    		  removeByValue(oidtypeArr,"一级病犯");
    		  removeByValue(oidtypeArr,"二级病犯");
    	  }
    	  
    	  if(arrContains(oidtypeArr,"一级残犯") && arrContains(oidtypeArr,"二级残犯")){
    		  alert("残犯只能选择一种类型");
    		  removeByValue(oidtypeArr,"一级残犯");
    		  removeByValue(oidtypeArr,"二级残犯");
    	  }
    	  
    	  oidtype = oidtypeArr.join(",");
    	  
    	  mini.get("oidtype").setValue(oidtype);
    	  
    	  var oldtype = mini.get("oldtype");
    	  if(arrContains(oidtypeArr,"老犯") || arrContains(oidtypeArr,"一级老犯") || arrContains(oidtypeArr,"二级老犯")){
    		  oldtype.setEnabled(true);
    	  }else{
    		  oldtype.setValue(null);
    		  oldtype.setEnabled(false);
    	  }
    	  
    	  
    	  var sicktype = mini.get("sicktype");
    	  if(arrContains(oidtypeArr,"病犯") || arrContains(oidtypeArr,"一级病犯") || arrContains(oidtypeArr,"二级病犯")){
    		  sicktype.setEnabled(true);
    	  }else{
    		  sicktype.setValue(null);
    		  sicktype.setEnabled(false);
    	  }
    	  
    	  
    	  var disabilitytype = mini.get("disabilitytype");
    	  if(arrContains(oidtypeArr,"残犯") || arrContains(oidtypeArr,"一级残犯") || arrContains(oidtypeArr,"二级残犯")){
    		  disabilitytype.setEnabled(true);
    	  }else{
    		  
    		  disabilitytype.setValue(null);
    		  disabilitytype.setEnabled(false);
    	  }
    	  
    	  /*
      	  var oidtype = mini.get("oidtype").getText();
      	  var codeid = mini.get("oidtype").getValue();
      	  if(codeid!='3'){
      	  	   //mini.get("disabilitytype").setUrl("ajaxGetCode.json?1=1&codeType=");
      	  	   mini.get("disabilitytype").setValue(oidtype);
      	  	   mini.get("disabilitytype").setText(oidtype);
      	  }else{
      	  	   //mini.get("disabilitytype").setUrl("ajaxGetCode.json?1=1&codeType=GB050");
      	  	   mini.get("disabilitytype").setValue(oidtype);
      	       mini.get("disabilitytype").setText("");
      	  }
      	  */
      }
      
</script>
</body>
</html>