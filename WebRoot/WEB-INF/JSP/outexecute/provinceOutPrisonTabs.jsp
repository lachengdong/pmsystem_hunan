<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			    <div id="tab1" name="1"  title="案件审批" url="provinceOutPrisonCase.action?1=1&tempid=${tempid}&menuid=${menuid}&id=${id}&fathermenuid=${fathermenuid}&closetype=${closetype}&flowdefid=${flowdefid}">
			    </div>
				<div id="tab2" name="2"  title="监狱审批表"  url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowdefid=other_jybwjycbsp&tempid=ZFABWJYSPB&docid=103001">
				</div>
				<!--河北省局保外办理需要查询保外建议书文书  -->
				<div id="tab2" name="2"  title="监狱建议书" url="toCaseLook.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowdefid=other_jybwjycbsp&tempid=9706report&docid=104001" visible="${hebei eq 1}">
					</div>
			    <div  id="tab3"  name="3"   title="基本信息" url="basicInfo/basicInformation.page?1=1&crimid=${crimid}&closetype=2"  refreshOnClick="true"  visible="true">
				</div>
				<div  id="tab4"  name="4"  title="电子档案" url="flow/tolookarchives.action?1=1&crimid=${crimid}&menuid=12124" refreshOnClick="true"  visible="true">
				</div>
			
<!--			<div  id="tab2"  name="2"  title="面试报告" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=10101&resid=10121" refreshOnClick="true" visible="true">-->
<!--			</div>-->
<!--			<div  id="tab2"  name="3"  title="不计入刑期批复" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=9720&resid=13668" refreshOnClick="true" visible="true">-->
<!--			</div>-->
<!--			<div  id="tab2"  name="4"  title="办案请示" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=9697&resid=13669" refreshOnClick="true" visible="true">-->
<!--			</div>-->
<!--			<div  id="tab2"  name="5"  title="病情鉴定函" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=9701&resid=13670" refreshOnClick="true" visible="true">-->
<!--			</div>-->
<!--			<div  id="tab2"  name="6"  title="急保批复" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&tempid=10105&resid=13671" refreshOnClick="true" visible="true">-->
<!--			</div>-->
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
      	function fanhui() {
      		CloseWindow("close");
      	}
        </script>
</body>
</HTML>