<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>监狱简介</title>
        <link href="<%=path %>/front/css/css4/index.css" rel="stylesheet" />
        <link href="<%=path %>/front/css/css4/left.css" rel="stylesheet" />
        
         <script src="<%=path %>/front/JavaScript/js/jquery-1.4.4.min.js"></script>
               <script src="<%=path %>/front/js/index/index.js"></script>
      
    </head>
    
    <body style="backgroud:transparent;">
        <div id="box">
            <div class="log3">
                <img  src="<%=path %>/front/images/log2.png" width="959" height="132"/>
            </div>
        	<div class="iframe_left" style="border:0px solid red;height:480px;width:230px;margin-top:240px;margin-left:90px;"><%--
        	   <%@ include  file="/login/left.jsp"%>
                
                --%>
                <ul class="leftcd">
        	<li>
            	<a href="#"  id="jyjj">监狱简介</a>
            </li>
            <li>
            	<a href="#" id="flfg">法律法规</a>
            </li>
            <li>
            	<a href="#" id="tsjylist">投诉/复议</a>
            </li>
            <li>
            	<a href="#"  id="gkxx">公开信息</a>
            </li>
            <li>
            	<a href="#">个人信息</a>
            </li>
            <li>
            	<a href="#">我要购物</a>
            </li>
        </ul>
              
        	</div>
            <div class="tsfu_zcd">
            	<ul>&nbsp;</ul>
                <div class="dltcs">
                	<a href=" <%=path %>/ywgkFrontLoginController/ywgkFrontLoginController/tologinPage.action">登录</a>
                    <a href="<%=path %>/ywgkFrontLoginController/toYwgkLogout.action">退出</a>
                </div>
            </div>
            <div class="box_rits">
            	  <div id="u18" class="ax_paragraph"  style="color:#333333;margin-left:0px;border:1px solid white;
      padding-left:0px;height:550px;margin-top:-20px;">
       <div   class="ax_paragraph" style="text-align:center;border:1px  white solid;position:absolute;height:40px;margin-left:220px;width:340px;font-size:20px;"><h2>${article.title}</h2></div>
      <div style="border:1px solid  white;overflow :auto;height:500px;margin-top:50px;padding-left:10px;padding-right:10px;padding-top:10px;font-size:18px;">
    ${article.content}
      </div>
	  
      </div>
            </div>
        </div>
        <div class="iframe_bottom">
            <iframe src="<%=path %>/login/bottom.jsp" class="iframe_bottoms" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no">
            </iframe>
        </div>
    </body>
</html>
