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
    <title>立案审批表</title>
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
    <div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
    
      <div  id="tab1"  name="1"  title="立案审批表" url="theCommutationParoleApplicationForm.action?1=1&menuid=${menuid}&crimid=${crimid}&tempid=SH_ZFJXJSLASPB&flowdefid=${flowdefid}&flowid=${flowid}&flowdraftid=${flowdraftid}&id=${id}&closetype=${closetype}">
      </div>
      <div  id="tab2"  name="2"  title="请示" url="theCommutationParoleApplicationQingShiForm.action?1=1&menuid=${menuid}&crimid=${crimid}&tempid=JXJS_ZFTQJXCXSQ&flowdefid=${flowdefid}&flowdraftid=${flowdraftid}&flowid=${flowid}&id=${id}" refreshOnClick="true"  visible="true">
      </div>
      
    </div>
    <script type="text/javascript">
        //mini.parse();
        top["win"]=window;

    ////////////////////
        //标准方法接口定义
        function SetData(data) {

    }
        function CloseWindow(action) { 
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        } 
        function fanhui() {
          CloseWindow("close");
        }
        </script>
</body>
</html>
