<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>文件上传页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     	<style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    	</style> 
	</head>
  
  <body>
  <form name="myform" action="<%=path %>/PersonFileUploadServlet" method="post" enctype="multipart/form-data">

       <br />
      
		<br />
      &nbsp;&nbsp;&nbsp; <input type="file" name="myfile" /> <input type="submit" name="submit" value="上传" />
      <input type="hidden" id="folderid"/>
    </form>
    
    <script language="javascript">
 //标准方法接口定义
    function SetData(data) {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
            $("#folderid").val(data.id);
            alert(data.id);
            document.myform.action="PersonFileUploadServlet?1=1&id="+data.id
    }
    function close(){
    alert();
    window.close();
    }
</script>
    
  </body>
</html>
