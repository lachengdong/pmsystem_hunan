<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,com.sinog2c.model.prisoner.CrimUserInfo" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@page import="java.util.*,com.sinog2c.model.page.PageController" %>
<%
	String path = request.getContextPath();

List<Map> articleList = (List<Map>)request.getAttribute("list");

CrimUserInfo  userinfo=(CrimUserInfo) session.getAttribute("userinfo");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>投诉复议</title>
          <link href="<%=path %>/front/css/css4/index.css" rel="stylesheet" />
        <link href="<%=path %>/front/css/css4/left.css" rel="stylesheet" />
        <link href="<%=path %>/front/css/css4/cdqh.css" rel="stylesheet" />
          <script src="<%=path %>/front/JavaScript/js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript" src="<%=path %>/front/JavaScript/js/bqqhxg.js"></script>
         <script src="<%=path %>/front/js/index/index.js"></script>
          <script type="text/javascript" src="<%=path %>/front/JavaScript/js/pageopration.js"></script>
           	
         
    </head>
    
    <body style="backgroud:transparent;">
        <div id="box">
            <div class="log3">
                <img  src="<%=path %>/front/images/tsjy/log2.png" width="959" height="132"/>
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
                	<%--<a href="#">登录</a>
                    --%><a href="<%=path %>/ywgkFrontLoginController/toYwgkLogout.action"  style="border:0px solid black;margin-left:120px;">退出</a>
                </div>
            </div>
            <input type="hidden" id="crimid" value="${userinfo.crimid}"/>
            <input type="hidden" id="contenttype" value="tsfy"/>
            <div class="box_rits paddwid">
            	<ul id="maintab" class="shadetabs">
                    <li class="selected"><a href="#" rel="tcontent1"  id="tousufuyi"> 投诉/复议</a></li>
                    <li><a href="#" rel="tcontent2" id="fuyiqingkuang"  >复议情况 </a></li>
                    <li><a href="#" rel="tcontent3" id="tousuqingkuang" >投诉情况</a></li>
                </ul>
                <input class="texts"  type="text" value=""  id="querycondition"/>
                <input class="searchs" type="button" value="" onclick=" queryTSFY();" style="cursor:pointer;cursor:hand;"/>
                <div class="tabcontentstyle">
                    <div id="tcontent1" class="tabcontent tsfy_list ">
                       <ul>
                            <li>
                                <a href="#">复议情况1</a>
                                <input  type="button" value="复议"/>
                                <input  type="button" value="投诉"/>
                            </li>
                            <li>
                                <a href="#">复议情况1</a>
                                <input  type="button" value="复议"/>
                                <input  type="button" value="投诉"/>
                            </li>
                        </ul>
                    </div>
                    
                    <div id="tcontent2" class="tabcontent tsfy_list">
                    	<ul>
                            <li>
                                <a href="#">复议情况2</a>
                                <input  type="button" value="复议"/>
                                <input  type="button" value="投诉"/>
                            </li>
                            <li>
                                <a href="#">复议情况2</a>
                                <input  type="button" value="复议"/>
                                <input  type="button" value="投诉"/>
                            </li>
                        </ul>
                    </div>
                    
                    <div id="tcontent3" class="tabcontent tsfy_list">
                             
                                
                                 
                    <%--
                        <ul>
                            <li>
                                <a href="#">复议情况3</a>
                                <input  type="button" value="复议"/>
                                <input  type="button" value="投诉"/>
                            </li>
                            <li>
                                <a href="#">复议情况3</a>
                                <input  type="button" value="复议"/>
                                <input  type="button" value="投诉"/>
                            </li>
                        </ul>
                    --%></div>
                </div>
                
                <script type="text/javascript">
                //Start Tab Content script for UL with id="maintab" Separate multiple ids each with a comma.
                initializetabcontent("maintab")
                </script>
                <div class="pages">
                	<span class="pags2"><span id="yeshu">1 </span>页数：<span id="dbyeshu">1</span>/<span id="pageCount">50</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总行数：<span id="allCount">10</span>行</span>
                    <span class="pags1">
                    	<a href="#"><img src="<%=path %>/front/images/tsjy/sybj.jpg" class="shouye" width="26" height="25" />&nbsp;&nbsp;<span id="shouye" class="shouye">首页</span>&nbsp;&nbsp;</a>
                        <a href="#">
                        <img src="<%=path %>/front/images/tsjy/sywbj.jpg" width="22" height="20"   class="shangyy"/>&nbsp;&nbsp;<span id="shangyy" class="shangyy">上一页</span>&nbsp;|&nbsp;<span id="xiayy" class="xiayy">下一页</span>&nbsp;&nbsp;
                        <img src="<%=path %>/front/images/tsjy/xyebj.jpg" class="xiayy"  width="23" height="22" />
                        </a>
                        <a href="#">&nbsp;&nbsp;|&nbsp;<span id="weiy" class="weiye" >尾页</span>&nbsp;&nbsp;<img src="<%=path %>/front/images/tsjy/wybj.jpg" width="24" height="25" class="weiye" /></a>
                    </span>
                </div>
            </div>
        </div>
    
        <div class="iframe_bottom">
            <iframe src="<%=path %>/login/bottom.jsp" class="iframe_bottoms" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no">
            </iframe>
        </div>
    </body>
    
  
</html>
