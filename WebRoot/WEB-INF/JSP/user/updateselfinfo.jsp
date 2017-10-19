<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
%>
<%
	// 获取request中的个人信息, MVC 思想,jsp页面不负责去执行操作,只负责显示.
SystemUser user = null;
Object obj = request.getAttribute("user");
if(obj instanceof SystemUser){
	user = (SystemUser)obj;
}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改密码</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:auto;
    }
     .errorText
    {
        color:red;
    }
    </style>

  </head>
  
  <body>

<div class="mini-toolbar" style="border-bottom:0;padding:0px;"> 
   <table style="width:100%;">
       <tr>
       	<td style="width:100%;">
       		<a class="mini-button" onclick="SaveData" style="width:60px" plain="true">保存</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;" plain="true">取消</a> 
		 </td>
       </tr>
   </table>
</div>
 <div id="form1" style="*+.margin-top: -15px;">
 <form:form commandName="user">  
        <div style="width:100%;height:100%px; margin: auto;padding-top: 15px;" align="center">
            <table align="center" >
                <tr>
                	<td style="width:60px;" align="right">用 户 ID：</td>
                    <td align="left"><form:input path="userid" disabled="true" /></td>                
                </tr>
             	<tr>
                    <td style="width:60px;" align="right">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
                    <td align="left">   
                    	<form:input path="name"/> 
                    </td>
                </tr>
                <tr>
                    <td style="width:60px;" align="right">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
	                <td align="left">                        
	                    <form:radiobutton path="gender" value="1"/>男
	                    <form:radiobutton path="gender" value="2"/>女
	                </td>
                </tr>
                <tr>
                    <td style="width:60px;" align="right">移动电话：</td>
                    <td align="left">
                    	<form:input path="mobile"/>
                    </td>                
                </tr>
                <tr>
                   <td style="width:60px;" align="right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
                   <td align="left">
                    	<form:input path="email"/> 
                   </td>
                </tr>
                <tr>
                    <td style="width:60px;" align="right">原始密码：</td>
                    <td align="left">    
                        <input name="password"  maxlength="14"  onvalidation="onUserNameValidation"  class="mini-password" required="false" requiredErrorText="原始密码不能为空"/>
                    </td>
                    <td id="password_error" class="errorText" align="left"></td>
                </tr>
                <tr>
                   <td style="width:60px;" align="right">新 密 码：</td>
                    <td align="left">    
                        <input name="newpassword" maxlength="14"  onvalidation="" class="mini-password" required="false" minLengthErrorText="密码长度6~14位"/>
                    </td>
                    <td id="newPassWord_error" class="errorText" align="left"></td>
                </tr>
                <tr>
                    <td style="width:60px;" align="right">确认密码：</td>
                    <td align="left">   
                     	<input name="repassword" maxlength="14" onvalidation="" class="mini-password" required="false" minLengthErrorText="密码长度6~14位"/>
                    </td>
                    <td id="supassword1_error" class="errorText" align="left"></td>
                </tr>
                 <tr align="left" >
                    <td colspan="4" style="padding-top: 16px;"><font color="red">提示：如果不修改密码,密码区域不填写即可.</font></td>
                </tr>
            </table>
        </div>
</form:form>        
     </div>
<script type="text/javascript">
	   mini.parse();
	       
       function SaveData() {
       		var form = new mini.Form("#form1");
            form.validate();
            if (form.isValid() == false) return;
           var o = form.getData();            
           var json = mini.encode([o]);
           
           
           //
           var $name = $("input[name=name]");
           var $userid = $("input[name=userid]");
           var $gender = $("input[name=gender]");
           var $mobile = $("input[name=mobile]");
           var $email = $("input[name=email]");
           var $password = $("input[name=password]");
           var $newpassword = $("input[name=newpassword]");
           var $repassword = $("input[name=repassword]");
           
           //
           var name = $name.val();
           var userid = $userid.val();
           var gender = $('input[name=gender]:checked').val();
           var mobile = $mobile .val();
           var email = $email .val();
           var password = $password .val();
           var newpassword = $newpassword .val();
           var repassword = $repassword .val();
           
           // 输入新密码
           if(newpassword){
        	   //
	           if(!password){
	        	   // 必须输入原密码
                   alert("必须输入原密码！");
	        	   $password .focus();
	           }
        	   //
	           if(!repassword){
	        	   // 必须输入原密码
                   alert("两次密码必须一致！");
	        	   $repassword .focus();
	           }
           } else if(password){
	        	   // 必须输入原密码
                   alert("新密码不能为空！");
	        	   $newpassword .focus();
	       }
           //
           var data = {
        	    name : name ,
	            userid : userid ,
	            gender : gender ,
	            mobile : mobile ,
	            email : email ,
	            password : password ,
	            newpassword : newpassword ,
	            repassword : repassword 
           };
           
           
           $.ajax({
               url: "ajax/updateselfinfo.json",
               data: data,
               type: "post",
               success: function (message) {
	        	   
	        	   //message = JSON.parse(message);
	        	   message = mini.decode(message);
	        	   
	        	   var info = message["info"];
	        	   
	               alert(info);
	               if(1 === message["status"]){
	                   CloseWindow("save");
	               }
	               return false;
               },
               error: function (jqXHR, textStatus, errorThrown) {
            	   alert("操作失败!");
                   CloseWindow();
               }
           });
       }
	
       function onCancel(e) {
           CloseWindow("cancel");
       }
       function CloseWindow(action) {            
          if (action == "close" && form.isChanged()) {
              if (confirm("数据被修改了，是否先保存？")) {
                  return false;
              }
          }
          if(window.CloseOwnerWindow){
          	 return window.CloseOwnerWindow(action);
          }else{
           window.close();
           }        
      }
        ////////////////////
        //标准方法接口定义
        function SetData(data) {
            if (data.action == "new") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);
            }
        }
       function GetData() {
            var o = form.getData();
            return o;
        }
      //////////////////////////////////////////
        function updateError(e) {
            var id = e.sender.name + "_error";
            var el = document.getElementById(id);
            if (el) {
                el.innerHTML = e.errorText;
            }
        }
        function onUserNameValidation(e) {                  
            updateError(e);
        }
        function onPwdValidation(e) {        
            updateError(e);
        }
        function onEnglishAndNumberValidation(e) {
            if (e.isValid) {
                if (isEnglishAndNumber(e.value) == false) {
                    e.errorText = "必须输入英文+数字";
                    e.isValid = false;
                }
            }
        }
   		function onMobileValidation(e) {
	   		if (e.isValid) {
		   		var pattern = /^1[3458]\d{9}$/;
		   		if (pattern.test(e.value) == false && e.value.length>0) {
			   		e.errorText = "必须输入正确的手机号码";
			   		e.isValid = false;
		   		}
	   		}
   		}   
        $(document).bind("keyup", function (e) {
        	var KEYCODE_ESC = 27;
        	var KEYCODE_ENTER = 13;
        	//
        	var keyCode = e.keyCode;
        	if(keyCode === KEYCODE_ESC){
        		// 执行钩子
        		CloseWindow();
        	}
        });
   </script>
  </body>
</html>
