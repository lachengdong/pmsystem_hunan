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
		<title>省局综合管理平台</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			 <div id="tab1" name="1"  title="案件审批"  url="provinceCommuteCase.action?1=1&tempid=${tempid}&menuid=${menuid}&id=${id}&fathermenuid=${fathermenuid}&closetype=${closetype}&flowdefid=${flowdefid}&nextButton=${nextButton}">
			</div> 
			
			<div  id="tab2"  name="jail"  title="建议书" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdefid}&tempid=jailcommutereport&docid=004001" refreshOnClick="true" visible="true">
			</div>
		
			<!-- 14107 省局减刑建议书专用ID ，减刑假释建议书查询方案必须绑定此资源ｉｄ -->
			<div  id="tab3"  name="province"  title="减刑审核意见" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&doctype=provincecommute&resid=14107" refreshOnClick="true" visible="true">
			</div>
			<c:if test="${xinjiang eq 0}">
				<div  id="tab4"  name="3"  title="监狱审批表" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdefid}&tempid=JXJS_JXJSSHB&docid=003001" refreshOnClick="true" visible="true">
				</div>
			</c:if>
			<c:if test="${chengpibiao eq 0}">
				<div  id="tab8"  name="8"  title="呈批表" url="toChengBaoReportDocumentPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&menuid=${menuid}" refreshOnClick="true" visible="true">
				</div>
			</c:if>
		</div>
		<script type="text/javascript">
        mini.parse();
        //建议书类型(proposal)为省局建议书(province)时跳转到建议书生成页面
       	var tabs = mini.get("tabs");
		if('${proposal}'=='province'){
			var tab = tabs.getTab("jail");
        }else{
        	var tab = tabs.getTab("province");
        }
        tabs.updateTab(tab, { visible: false });
        
        
        function CloseWindow(action) { 
           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
           else window.close();            
	    }
        
        </script>
</body>
</html>