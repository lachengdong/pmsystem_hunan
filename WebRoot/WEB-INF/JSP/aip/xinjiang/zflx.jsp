<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯类型</title>
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
    	<input id="provincecode"  class="mini-hidden" value="${provincecode}"/>
    	<input id="cjicriminalsort"  class="mini-hidden" value="${cjicriminalsort}"/>
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
           <a class="mini-button" onclick="onOk2" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>  
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
				<table style="table-layout: fixed;">
					<tr>
						<td>
							<input id="select7" class="mini-combobox"
								url="ajaxGetCode.json?codeType=GB036"
								textField="name" valueField="codeid" allowInput="false"
								style="width: 100%;" onvaluechanged="onDeptChanged" />
						</td>
						<td>
							<input id="select8" class="mini-combobox"
								url="ajaxGetCode.json?codeType=WA001"
								textField="name" valueField="codeid" allowInput="false"
								style="width: 100%;"/>
						</td>
					</tr>
				</table>
			</div>
    </form>
    <script type="text/javascript">
        mini.parse();
		 var s7 = mini.get("select7");
		 var s8 = mini.get("select8");
		 var cjicriminalsort = mini.get("cjicriminalsort").getValue();
		 function myload(){
		 	if(cjicriminalsort.indexOf('危害国家安全（')>-1){
		 		s7.setText('危害国家安全');
		 		var t1 = cjicriminalsort.indexOf('（')+1;
		 		var t2 = cjicriminalsort.indexOf('）');
		 		var tvalue = cjicriminalsort.substring(t1,t2);
		 		s8.setText(tvalue); //截取的是 '（'和'）'之间的串
		 	}else{
			 	if(cjicriminalsort.indexOf('危害国家安全')>-1){
			 		s8.setEnabled(true);
			 	}else{
				 	s8.disable();
				 	s8.setValue();
			 	}
		 		s7.setText(cjicriminalsort);
		 	}
		 }
         function onDeptChanged(e) {
          var temp2 = s7.getValue();
          if(temp2=='3'){
          		 s8.enable();
            }else{
            	s8.disable();
            	s8.setValue();
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

        function onOk2(e) {
        	 var s7text = s7.text;
        	 var s8text = s8.text;
        	 var result = "";
        	 if(s7text==''){
        	 	result+=s7text;
        	 }
        	 if(s8text==''){
        	 	result+=s7text;
        	 }else{
        	 	result+=s7text+"（"+s8text+"）";
        	 }
        	 window.returnValue = result;
             CloseWindow("cancel");
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
