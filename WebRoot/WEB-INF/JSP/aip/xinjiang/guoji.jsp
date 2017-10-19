<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
    <input type="hidden" value="${sfaddress}" id="sfaddress"/>  
    <input type="hidden" value="${sqaddress}" id="sqaddress"/> 
    <input type="hidden" value="${dqxjaddress}" id="dqxjaddress"/> 
    <input type="hidden" value="${xxdzaddress}" id="xxdzaddress"/>
    <input type="hidden" value="${par5}" id="par5"/>
    <form id="form1" method="post">
          <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >               
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>       
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr>
                     <td width="90px;">省市：</td>
                     <td width="120px;">    
                          <input id="select1" class="mini-combobox" textField="name" valueField="codeid"  allowInput="true" style="width:100%;" 
                          	   onvaluechanged="select1changed" url="getAllSFSelect.json?1=1" value="156" pinyinField="scearch" emptyValue="省份"
                          />
                     </td>
                     <td width="120px;">    
                          <input id="select2" class="mini-combobox" 
							     textField="name" valueField="codeid"  allowInput="true" style="width:100%;" pinyinField="scearch" 
							     onvaluechanged="select2changed"  emptyValue="市区"
							/>
                    </td>
                </tr>
                <tr>
               		 <td width="90px;"></td>
                     <td width="120px;">    
                          <input id="select3" class="mini-combobox" 
							       textField="name" valueField="codeid"  allowInput="true" style="width:100%;"  pinyinField="scearch"  onvaluechanged="select3changed"
							        emptyValue="县或区"
							/>
                    </td>
<!--                      <td width="120px;">     -->
<!--                           <input id="select4" class="mini-combobox"  -->
<!-- 							       textField="name" valueField="codeid"  pinyinField="scearch"  allowInput="true" style="width:100%;"  /> -->
<!--                     </td> -->
                 </tr>
                 <tr>  
                           <td width="90px;">详细地址：</td>
                           <td width="245px;">
                                 <input id="xxaddress" name="xxaddress" class="mini-textbox" value=""  emptyValue="详细地址"   style="width:245px;"/>
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
        var form = new mini.Form("form1");
        
        var guojiobject = window.dialogArguments;
        if(guojiobject && guojiobject.guojicountrycode){
        	s1.setValue(guojiobject.guojicountrycode);
        }
        if(guojiobject && guojiobject.guojiprovincecode){
        	s2.setValue(guojiobject.guojiprovincecode);
        	select2changed();
        }
        if(guojiobject && guojiobject.guojicitycode){
        	s3.setValue(guojiobject.guojicitycode);
        	select3changed();
        }
        if(guojiobject && guojiobject.guojiareacode){
        	s4.setValue(guojiobject.guojiareacode);
        }
       
       
      //当页面第一次打开的时候默认选择 已经存在省份和地区和详细地址
      defaultSelect();
      function defaultSelect(){
          //查询出所有省份 ，然后默认选择 一个省份
          var s1value = $("#sfaddress").val();
          s1.setValue(s1value);
          
          //查询出默认省份所对应的所有的市区 ，然后选择 一个市区
          
          var s2value = $("#sqaddress").val();
      	  var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s1value;
          s2.setUrl(url);
          s2.setValue(s2value);
          
          
          //然后根据默认市区 ，来确定选择本市区的所有的县或地区，然后默认选择一个地区或者县
          
          var s3value = $("#dqxjaddress").val();
      	  var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s2value;
          s3.setUrl(url);
          s3.setValue(s3value);
          
          //然后把具体地址  放到 对应的框中
          var s4value = $("#xxdzaddress").val();
          mini.get("xxaddress").setValue(s4value);
          
          //判断 par5 是否可以编辑具体地址
          
          var par5value = $("#par5").val();
          if(par5value == '1'){
               mini.get("xxaddress").setEnabled(false);
          }
          
          
          //判断市区和县打开就为空的时候 那么锁定市、县或区 
          if(s2value == ""){
               s2.setEnabled(false);
          }
          if(s3value == ""){
               s3.setEnabled(false);
          }
          
      }
      
      
      function onOk(e) {
           var result = "";
           var s1value = s1.getValue();//获取省份的codeid
           var s2value = s2.getValue();//获取所在市区的codeid
           var s3value = s3.getValue();//获取所在县或区的codeid
         
           var s1text = s1.text;//获取省份可视文本
           var s2text = s2.text;//获取省份可视文本
           var s3text = s3.text;//获取省份可视文本
            
           var s5value = mini.get("xxaddress").getValue();//获取附加详细地址
           if(!s3value){
               alert("请选择县或者区!");return;
           }
           //判断是否控制 详细地址为必填项
           var par5 = $("#par5").val();
          // if(par5 == "0"){
	        //   if(!s5value){
	          //     alert("详细地址为必填项!");return;
	           //}
           //}
           var row = new Array([5]);
           //if(s1value=='156'){//中国
       	   if(s1value){
       		   row[0] = s1value;//省分代码 gb002
       	    }
       		if(s2value){
       			row[1] = s2value;//市代码
       		}
       		if(s3value){
       			row[2] = s3value;//地区代码获取是县
       		}
          		
        	var url = "";
        	//通过县或者地区的codeid获取对应的remark描述
           	url="getJiGuanShuJuData.action?1=1&codeType=GB002&codeId="+s3value;
          	$.ajax({
               url: url,
               data: {},
               type: "post",
               success: function (text) {
               		var o = mini.decode(text);
               		if(o.remark){
               			row[3] = o.remark;//县或者地区详细描述
               		}else{
               			row[3] = o.name;
               		}
               		row[4] = s5value;//详细地址
                 	window.returnValue = row;
            		CloseWindow("cancel");
               },
               error: function (jqXHR, textStatus, errorThrown) {
               		//alert("操作失败!");
               }
           });
        }
        
        //默认不可编辑
        //s2.setEnabled(false);
        //s3.setEnabled(false);
        //s4.setEnabled(false);
		        
        
        function select1changed() {
        	var s1v = s1.getValue();
            s2.setEnabled(true);
            s3.setEnabled(false);
      		var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s1v;
      		s2.setUrl(url);
      		s3.setValue("");
        }
        
        function select2changed() {
        	var s2v = s2.getValue();
        	if(s2v){
                s3.setEnabled(true);
        		var url="getAllSFSelect.json?1=1&codetype=GB002&pcodeid="+s2v;
        		s3.setUrl(url);
        	}
        }
         
        function select3changed() {
        	var s3v = s3.getValue();
        	if(s3v){ 
        		var url="getJiGuanShuJu.action?1=1&codeType="+s3v;
        		s4.setUrl(url);
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
    </script>
</body>
</html>
