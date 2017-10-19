<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title></title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
 	<script type="text/javascript">
 		function setAValue(fieldName,fieldValue){
			try{
				var aipObj=document.getElementById("HWPostil1");
				aipObj.SetValue(fieldName,fieldValue);
			}catch(e) {
				alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
			}
		}
 	</script>
 	<style>
 		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
 	</style>
  <body>
	<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  </body>
</html>
