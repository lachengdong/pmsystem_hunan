<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@page import="java.util.*,com.sinog2c.model.page.PageController" %>
<%
	String path = request.getContextPath();

List<Map> articleList = (List<Map>)request.getAttribute("list");
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
            	<a href="#" id="gkxx">公开信息</a>
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
                	<a href="<%=path %>/ywgkFrontLoginController/tologinPage.action">登录</a>
                    <a href="<%=path %>/ywgkFrontLoginController/toYwgkLogout.action">退出</a>
                </div>
            </div>
            <div class="box_rits">
            	  <div id="u18" class="ax_paragraph"  style="color:#333333;margin-left:0px;border:1px solid white;
      padding-left:0px;height:550px;margin-top:-20px;">
       <div   class="ax_paragraph" style="text-align:center;border:1px  white solid;position:absolute;height:40px;margin-left:220px;width:340px;font-size:20px;"><h2>${module.title}</h2></div>
      <div style="border:1px solid white;overflow :auto;height:450px;margin-top:50px;padding-left:10px;padding-right:10px;padding-top:10px;font-size:30px;">
    <p>
          <%
                		if(articleList!=null && articleList.size()>0){
                	%>
                		<ul class="flfg_li">	
                	<%
	                		for(int i=0;i<(articleList.size()<=10?articleList.size():10);i++){
	                		
	                %>
	                			<li  style="align:left;border:0px solid red;padding-left:0px;font-color:black;"><a href="toDetailIndexPage.action?1=1&pagename=flfgDetail&moduleid=<%=articleList.get(i).get("moduleid") %>&articleid=<%=articleList.get(i).get("articleid") %>" title="<%=articleList.get(i).get("title") %>"><%=((String)articleList.get(i).get("title")).length()>30?((String)articleList.get(i).get("title")).substring(0,30)+"...":articleList.get(i).get("title") %></a></li>	
	                <%
	                		}
                		}
                	
                	%>
                        </ul>

     </p>
      </div>
	   <div class="page" style="border:1px white; solid;margin-bottom:5px;text-align:right;font-size:20px;">
		               			<%
		                    		PageController pageController = (PageController)request.getAttribute("pageController");
		                    		int pageCount = pageController.getPageCount()-1;
		                    		int thisPage = pageController.getThisPage();
		                    		int prePage = thisPage -1 ;
		                    		int nextPage = thisPage + 1;
		                    		if(pageController!=null && pageCount>0){
		                    			if(thisPage == 0){
		                		%>
		                					<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=<%=nextPage %>">下一页</a>
		                    			 	<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=<%=pageCount %>">末页</a>
		                 		<%
		                    			}else if(thisPage == pageCount){
		                    	%>
											<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=0">首页</a>
		                					<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=<%=prePage %>">上一页</a>                    			 	
		                    	<%
		                    			}else {
		                    	%>
		                    				<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=0">首页</a>
		                					<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=<%=prePage %>">上一页</a>
		                					<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=<%=nextPage %>">下一页</a>
		                    			 	<a href="toListPage.action?pagename=flfgList&modulename=${module.name}&thisPage=<%=pageCount %>">末页</a>
		                    	<%
		                    			}
		                    		}
		                    	%>
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
