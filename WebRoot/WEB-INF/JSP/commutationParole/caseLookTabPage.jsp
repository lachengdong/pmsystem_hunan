<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>案件查看tabs</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
	<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1" title="审核表" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowdefid=${flowdefid}&tempid=${tempid}&docid=${docid}">
			</div>
			<div id="tab2" name="2" title="建议书" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowdefid=${flowdefid}&tempid=${jxstempid}&docid=${jysdocid}"  refreshOnClick="false">
			</div>
			<c:if test="${provincecode eq '1200'}">
				<div id="tab3" name="3" title="呈批表" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowdefid=${flowdefid}&tempid=${cpbtempid}&docid=${cpbid}"  refreshOnClick="true">
				</div>			
			</c:if>
			<c:if test="${provincecode eq '6100'}">
				<div id="tab3" name="3" title="省局决定书" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowdefid=${flowdefid}&tempid=${cpbtempid}&docid=123"  refreshOnClick="false">
				</div>			
			</c:if>
	</div>
		<script type="text/javascript">
        //标准方法接口定义
        function SetData(data) {
        
		}
       function CloseWindow(action) {            
          if (action == "close" && form.isChanged()) {
              if (confirm("数据被修改了，是否先保存？")) {
                 return false;
              }
          }
          if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
          else window.close();            
       }
        </script>
</body>
</html>