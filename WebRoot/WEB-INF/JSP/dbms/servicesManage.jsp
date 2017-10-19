<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>services管理页面</title>
    
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
    </style>
  </head>
  <body>
  <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listdbconnconfig.json" 
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        		<div field="ddid" name="ddid" width="30" headerAlign="center" align="center"  allowSort="true">
               			单位
               		</div>
               		<div field="port" name="port" width="30" headerAlign="center" align="center"  allowSort="true">
               			数据库端口
               		</div>
           			<div field="databasename" name="databasename" width="60" headerAlign="center" align="center"  allowSort="true">
               			数据库地址
               		</div>
               		<div field="ddorg" name="ddorg" width="40" headerAlign="center"  align="center"  allowSort="true">
               			备注
               		</div>
			        <div width="40" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
  </body>
</html>
