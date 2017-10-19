<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String furl = request.getParameter("furl");
	if(null == furl){
		furl = "";
	}
	// indexFlag
	// 	
	String ids = request.getParameter("ids");
	if(null == ids){
		ids = "";
	}
	//
	String indexFlag = request.getParameter("indexFlag");
	if(null == indexFlag || indexFlag.trim().isEmpty()){
		indexFlag = "0";
	}
 %>
<html  xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
        <title>狱务公开流程审批处理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   		<script type="text/javascript" src="<%=path%>/scripts/boot.js" ></script>
   		<script type="text/javascript" src="<%=path%>/scripts/ywgk/js/withoutaipjs.js" ></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
   		
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }    
    	</style>  
</head>
  
  <body>
	    <div class="mini-toolbar" style="padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    	<a class="mini-button" id="1604809" iconCls="icon-download" plain="true" onclick="toNext();">下一个</a>    
                        ${topstr}
                        <a class="mini-button" id="1604796" iconCls="icon-cancel" plain="true" onclick="goBack();">取消</a>
                    </td>
                    <td style="white-space:nowrap;">
                    </td>
                </tr>
            </table>           
        </div>
        <div style="margin:0 auto;">
        	<table align="center">
       			<tr>
	                <td align="center">
	                	<h2>对${applyname}${modelname}的处理意见</h2>
	                </td>
	            </tr>
	        	<tr>
	                <td>
	                    <input id="commenttext" name="commenttext" class="mini-textarea" style="width:1300px;height:300px;" emptyText="请输入意见" />
	                </td>
	            </tr>
	        </table>
        </div>
        <script type="text/javascript">
        	mini.parse();
        	//
		   	var basePath = "<%=basePath %>";
		   	var flowdraftid = "${flowdraftid}";
		   	var _furl = "<%=furl %>";
		   	var ids = "<%=ids%>";
		   	var indexFlag = <%=indexFlag %>; //数字,不加双引号
		</script>
  </body>
</html>
