<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>石家庄狱务公开首页</title>
        <link href="<%=path %>/front/css/index1.css" rel="stylesheet" />
        <link href="<%=path %>/front/css/top1.css" rel="stylesheet" />
        <script src="<%=path %>/front/JavaScript/js/jquery-1.4.4.min.js"></script>
           <script src="<%=path %>/front/js/index/index.js"></script>
    </head>
    <style type="text/css">
    	.xob{
			background:url(<%=path %>/front/images/hovbj.jpg) no-repeat;
			width:1280px;	
			height:732px;
			margin:0 auto;
		}
    </style>

    
    <body>
    	<div class="logs">
        	<img class="log1" src="<%=path %>/front/images/log1.jpg" width="170" height="168" />
            <img class="log2" src="<%=path %>/front/images/log2.png" width="959" height="132"/>
        </div>
        <div class="clear"></div>
        <div class="contents">
        	
            
        	<div class="con_left_nav">
            	<ul>
                	<li>
                    	<a href="#" id="jyjj">监狱简介</a>
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
            <div class="con_rit_tzgg">
            	<h2>通知公告</h2>
                <p>
                	关于端午放假通知<br />
尊敬的各位用户：<br />
    根据国务院办公厅通知精神，现将2015年端午节放假安排通知如下：<br />
　　6月20日(星期六)放假，6月22日(星期一)补休。
　　节假日期间，各单位要妥善安排好值班和安全、保卫等工作，遇有重大突发事件发生，要按规定及时报告并妥善处置，确保人民群众祥和平安度过节日假期。<br />
　　北京市人民政府办公厅<br />
　　2015年5月
                </p>
            </div>
        </div>
        <div class="clear"></div>
        <div class="iframe_bottom">
            <iframe src="<%=path %>/login/bottom.jsp" class="iframe_bottoms" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no">
            </iframe>
        </div>
    </body>
</html>
	 