<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String unitLevel= (String)request.getAttribute("unitLevel");
	String provincecode= (String)request.getAttribute("provincecode");
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>监狱综合管理平台(保外)</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1"  title="案件审批" url="outPrisonOfJailCase.action?1=1&tempid=${tempid}&menuid=${menuid}&id=${id}&fathermenuid=${fathermenuid}&closetype=${closetype}&flowdefid=${flowdefid}">
			</div>
			<c:if test="${tabtype eq 'shanxi'}">
			    <div  id="tab6"  name="3"  title="省局单子" url="tosxsjFormpage.action?1=1&menuid=15954&tempid=${sjtempid}&id=${id}&fathermenuid=${fathermenuid}&closetype=${closetype}&flowdefid=${flowdefid}&temptype=sj" refreshOnClick="true" visible="true">
				</div>
			</c:if>
			<c:if test="${ningxia eq 0}">
				<%
				//陕西保外监区不需要建议书
				if("6100".equals(provincecode)){
				}else {
				%>
			    <div  id="tab3"  name="3"  title="建议书" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=9706&resid=12753" refreshOnClick="true" visible="true">
				</div>
				<%
				}
				%>
			</c:if>
			<c:if test="${ningxia eq 1}">
				<div  id="tab3"  name="3"  title="建议书" url="toSuggestionDocPage_nx.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=NX_JWZXJYS&resid=802027" refreshOnClick="true" visible="true">
				</div>
			</c:if>
			<div  id="tab4"  name="4"  title="电子档案" url="flow/tolookarchives.action?1=1&crimid=${crimid}&anjiantype=bwjy&menuid=12124" refreshOnClick="true"  visible="true">
			</div>
			<c:if test="${ningxia eq 1}">
			     <div  id="tab5"  name="5"  title="生活不能自理鉴定表" url="getFlowBaseDocByMap.action?1=1&tempid=JWZX_ZFBQZDS&crimid=${crimid}&flowdefid=doc_bwshbnzlsp" refreshOnClick="true"  visible="true">
			     </div>
			</c:if>
			<c:if test="${ningxia eq 0}">
				<%
				//陕西保外监区不需要病残鉴定表，增加基本信息表
				if("6100".equals(provincecode)){
				%>
				<div  id="tab53"  name="5"   title="基本信息" url="basicInfo/basicInformation.page?1=1&crimid=${crimid}&closetype=2"  refreshOnClick="true"  visible="true">
				</div>
			    <%
				}else {
				%>
				 <div  id="tab5"  name="5"  title="病情鉴定" url="identificationOfDisease.action?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			     </div>
			    <%
				}
			    %>
			</c:if>
		</div>
		<script type="text/javascript">
        mini.parse();
        top["win"]=window;
		////////////////////
        //标准方法接口定义
        function SetData(data) {

		}
        function CloseWindow(action) { 
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        } 
        </script>
</body>
</html>