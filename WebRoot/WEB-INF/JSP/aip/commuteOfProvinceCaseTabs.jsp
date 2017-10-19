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
			<div id="tab1" name="1"  title="案件审批" url="commuteOfProvinceLeaderCase.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&tempid=${tempid}&menuid=${menuid}&id=${id}&fathermenuid=${fathermenuid}&flowid=${flowid}&orgid=${orgid}">
			</div>
			<div  id="tab2"  name="2"  title="建议书" url="commutationParoleSuggestion.action?1=1&crimid=${crimid}&flowdraftid=${flowdraftid}&flowid=${flowid}" refreshOnClick="true" visible="true">
			</div>
			<div  id="tab3"  name="3"   title="基本信息" url="basicInfo/basicInformation.page?1=1&crimid=${crimid}"  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab4"  name="4"  title="狱内奖惩" url="toPunishmentInfoOfCrim.action?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab5"  name="5"   title="刑期变动" url="toSentenceChangeListPage.page?1=1&crimid=${crimid}"  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab6"  name="6"  title="电子档案" url="flow/tolookarchives.action?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab7"  name="7"   title="司法文书" url="toJudicialDocumentsPage.action?1=1&crimid=${crimid}"  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab8"  name="8"  title="呈批表" url="showCQAJPSBs.page?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>
			<!-- 
			<div  id="tab9"  name="9"   title="改造评估" url="toReformAndAssessmentPage.action?1=1&crimid=${crimid}"  refreshOnClick="true"  visible="true">
			</div>
			 -->
			<div  id="tab10"  name="10"  title="集体讨论审核表" url="toGroupDiscussExaminePage.action?1=1&crimid=${crimid}" refreshOnClick="true"  visible="true">
			</div>
		</div>
		<script type="text/javascript">
        mini.parse();
		////////////////////
        //标准方法接口定义
        function SetData(data) {

		}

        </script>
</body>
</HTML>