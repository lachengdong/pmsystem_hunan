<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>监狱综合管理平台</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1"  title="案件审批" url="commuteOfJailCase.action?1=1&tempid=${tempid}&menuid=${menuid}&id=${id}&fathermenuid=${fathermenuid}&closetype=${closetype}&flowdefid=${flowdefid}&provinceid=${provinceid}&nodeid=${nodeid}&days=${days}&ischeckseal=${ischeckseal}">
			</div>
			<div  id="tab2"  name="2"  title="建议书" url="toSuggestionDocPage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}&flowdefid=${flowdefid}&doctype=jailcommute&resid=12498&menuid=${menuid}" refreshOnClick="true" visible="true">
			</div>
			<!--
			<div  id="tab2"  name="2"  title="建议书" url="commutationParoleSuggestion.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&menuid=${menuid}&flowid=${flowid}" refreshOnClick="true" visible="true">
			</div>
			-->
			<div  id="tab3"  name="3"   title="基本信息" url="basicInfo/basicInformation.page?1=1&crimid=${crimid}&closetype=${closetype}&fathermenuid=${fathermenuid}"  refreshOnClick="true"  visible="true">
			</div>
			<!--  
			<div  id="tab4"  name="4"  title="狱内奖惩" url="toPunishmentInfoOfCrim.action?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>
			-->
			<div  id="tab5"  name="5"   title="刑期变动" url="toSentenceChangeListPage.page?1=1&crimid=${crimid}&toolbar=0"  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab6"  name="6"  title="电子档案" url="flow/tolookarchives.action?1=1&crimid=${crimid}&menuid=12124" refreshOnClick="true"  visible="true">
			</div>
			<!--  
			<div  id="tab7"  name="7"   title="司法文书" url="toJudicialDocumentsPage.action?1=1&crimid=${crimid}&toolbar=0"  refreshOnClick="true"  visible="true">
			</div>
			-->
			<div  id="tab8"  name="8"  title="集体讨论审核表" url="toGroupDiscussExaminePage.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab9"  name="9"  title="监督意见" url="viewCaseCheck.action?1=1&crimid=${crimid}&tempid=SH_JCYJS" refreshOnClick="true"  visible="true">
			</div>
			<!-- 			
			<div  id="tab8"  name="8"  title="呈批表" url="showCQAJPSBs.page?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab9"  name="9"   title="改造评估" url="toReformAndAssessmentPage.action?1=1&crimid=${crimid}"  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab10"  name="10"  title="集体讨论审核表" url="toGroupDiscussExaminePage.action?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>-->
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