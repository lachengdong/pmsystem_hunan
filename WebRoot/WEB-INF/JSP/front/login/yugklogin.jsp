<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>登录</title>
        <link href="<%=path %>/front/css/index1.css" rel="stylesheet" />
          
         <script src="<%=path %>/front/JavaScript/js/jquery-1.4.4.min.js"></script>
               <script src="<%=path %>/front/js/index/index.js"></script>
    </head>
    
    <body style="backgroud:transparent;">
        <div id="box2">
        	<div class="log3">
                <img  src="<%=path %>/front/images/log2.png" width="959" height="132"/>
            </div>
            <div class="tsfu_zcd">
            	<ul>&nbsp;</ul>
                <div class="dltcs" style="align:right;border:0px solid red;padding-right:0px;">
                	
                    <a href="<%=path %>/ywgkFrontLoginController/toIndexPage.action" style="border:0px solid black;margin-left:120px;">去主页</a>
                </div>
            </div>
            <div class="registers">
            	<h3 class="yhczs">用户操作说明</h3>
                <ul class="czsm">
                	<li>点击"囚犯"输入框；输入你的编号，如：9999665522</li>
                    <li class="paddingls">通过触屏上的数字按钮，输入囚犯号和密码，默认密码共4位数字。分别是个人出生月份和日期，如：x月x日 密码则是：XXXX</li>
                    <li>通过触摸屏，按"确定"按钮登录系统</li>
                    <li>登录后可用功能：</li>
                </ul>
                 <img class="grxximgs1" src="<%=path %>/front/images/grxxbjs.jpg" width="82" height="66" />
                <ul class="gnlist">
                	<li>查看到犯人的个人信息及修改密码</li>
                    <li>查询刑期变动情况</li>
                    <li>查询行政奖励</li>
                    <li>查询考核扣分</li>
                    <li>查询零花钱消息</li>
                    <li>查询劳动定额完成情况</li>
                </ul>
                <div class="clear"></div>
                <div class="number">
                	<div class="srkinp">
                        <h2 class="qndl">请您登陆：</h2><br />
                        <input type="hidden" name="URL" id="URL" value="${URL}"/>
                        囚号：<input type="text"  name="crimid"   id="crimid"   value="" /><br /><br />
                        密码：<input type="password" name="crimpassword"  id="crimpassword" value="" />
                    </div>
                    <ul class="num_bat">
                        <li>
                            <a href="#" class="order">1</a>
                        </li>
                        <li>
                            <a href="#"  class="order">2</a>
                        </li>
                        <li>
                            <a href="#"  class="order">3</a>
                        </li>
                        <li>
                            <a href="#"  class="order">4</a>
                        </li>
                         <li>
                            <a href="#"  class="order">5</a>
                        </li>
                         <li>
                            <a href="#"  class="order">6</a>
                        </li>
                         <li>
                            <a href="#"  class="order">7</a>
                        </li>
                         <li>
                            <a href="#"  class="order">8</a>
                        </li>
                         <li>
                            <a href="#"  class="order">9</a>
                        </li>
                         <li>
                            <a href="#"  class="order">0</a>
                        </li>
                         <li>
                            <a href="#"><img  src="<%=path %>/front/images/num_jtbj.jpg"  width="35" height="11"/></a>
                        </li>
                         <li>
                            <a href="#" id="qlingling">确定</a>
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
	