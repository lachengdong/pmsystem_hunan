<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>公开信息首页</title>
         <link href="<%=path %>/front/css/css4/index.css" rel="stylesheet" />
        <link href="<%=path %>/front/css/css4/left.css" rel="stylesheet" />
          <script src="<%=path %>/front/JavaScript/js/jquery-1.4.4.min.js"></script>
          <script src="<%=path %>/front/js/index/index.js"></script>
          
     	<script type="text/javascript">
    $(document).ready(function(){
        $(".leftcd li").first().addClass("current");    
        $(".leftcd li").click(function(){        
        $(this).addClass("current").siblings().removeClass("current")    
        });
    });
    </script>
    </head>
    <style type="text/css">
    	.xob2{
			background:url(<%=path %>/front/images/tsjy/cont_ritbj_hover.jpg) no-repeat;
			width:876px;
			height:618px;	
		}
    </style>
    <script type="text/javascript">            
		$(function () {                             
			$("#box_rits").hover(function (){                    
			$(this).toggleClass("xob2");})            
		})                    
    </script>
    
    <body style="backgroud:transparent;">
        <div id="box">
        	<div class="log3">
                <img  src="<%=path %>/front/images/tsjy/log2.png" width="959" height="132"/>
            </div>
        		<div class="iframe_left" style="border:0px solid red;height:480px;width:230px;margin-top:240px;margin-left:90px;">

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
            	<a href="#" >个人信息</a>
            </li>
            <li>
            	<a href="#">我要购物</a>
            </li>
        </ul>
              
             	</div>
            <div class="tsfu_zcd">
            	<ul>&nbsp;</ul>
                <div class="dltcs">
                	<a href="#">登录</a>
                    <a href="#">退出</a>
                </div>
            </div>
            <input type="hidden" id="crimid" value="${userinfo.crimid}"/>
            <div id="box_rits" class="box_rits box_gkxxcd">
            	<div class="heights">
                    <h3 class="gkxx-bt">刑罚执行</h3>
                    <ul class="box_rits_gkxx">
                        <li>
                            <a href="#" id="jxjs">减刑假释</a>
                        </li>
                        <li>
                            <a href="#">暂予监外执行</a>
                        </li>
                        <li>
                            <a href="#">加刑公示</a>
                        </li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="heights">
                    <h3 class="gkxx-bt">狱政管理</h3>
                    <ul class="box_rits_gkxx">
                        <li>
                            <a href="#">考核扣分情况</a>
                        </li>
                        <li>
                            <a href="#">行政奖惩情况</a>
                        </li>
                        <li>
                            <a href="#">分级外遇定级</a>
                        </li>
                        <li>
                            <a href="#">专项工种选用</a>
                        </li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="heights">
                    <h3 class="gkxx-bt">监狱改造</h3>
                    <ul class="box_rits_gkxx">
                        <li>
                            <a href="#">入监教育</a>
                        </li>
                        <li>
                            <a href="#">三科教育</a>
                        </li>
                        <li>
                            <a href="#">自学考试</a>
                        </li>
                        <li>
                            <a href="#">稿件录用情况</a>
                        </li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="heights">
                    <h3 class="gkxx-bt">劳动改造</h3>
                    <ul class="box_rits_gkxx">
                        <li>
                            <a href="#">劳动分级定级</a>
                        </li>
                        <li>
                            <a href="#">顶级完成情况</a>
                        </li>
                        <li>
                            <a href="#">劳动工种变动</a>
                        </li>
                        <li>
                            <a href="#">任务减免情况</a>
                        </li>
                        <li>
                            <a href="#">岗前技术培训</a>
                        </li>
                    </ul>
                </div>
                <div class="clear"></div>
                <div class="heights">
                    <h3 class="gkxx-bt">生活卫生</h3>
                    <ul class="box_rits_gkxx">
                        <li>
                            <a href="#">本周食谱</a>
                        </li>
                        <li>
                            <a href="#">伙食供应量</a>
                        </li>
                        <li>
                            <a href="#">购物商品价格表</a>
                        </li>
                        <li>
                            <a href="#">被服发放情况</a>
                        </li>
                        <li>
                            <a href="#">医疗费用开支</a>
                        </li>
                    </ul>
            	</div>
            </div>
        </div>
        <div class="iframe_bottom">
           <iframe src="<%=path %>/login/bottom.jsp" class="iframe_bottoms" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no">
            </iframe>
        </div>
    </body>
</html>
