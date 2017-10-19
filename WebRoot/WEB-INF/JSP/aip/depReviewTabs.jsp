<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>部门审查tabs</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
    <script type="text/javascript">
    		 function onbeforeactivechanged(e){
	        }
	        function aa() {
	        }
		</script>
</head>
<body >
		<div id="tabs" class="mini-tabs" name="aaa" activeIndex="0" onbeforeactivechanged="onbeforeactivechanged"  style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1"  title="案件审批" url="depReview.action?1=1&crimid=${crimid }&tempid=${tempid }&action=${action}">
			</div>
			<div  id="tab2"  name="2"  title="建议书" url="" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab3"  name="3"   title="基本信息" url=""  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab4"  name="4"  title="狱内奖惩" url="" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab5"  name="5"   title="刑期变动" url=""  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab6"  name="6"  title="电子档案" url="" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab7"  name="7"   title="司法文书" url=""  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab8"  name="8"  title="监督意见" url="" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab9"  name="9"   title="改造评估" url=""  refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab10"  name="10"  title="集体讨论审核表" url="" refreshOnClick="true"  visible="true">
			</div>
		</div>
</body>
</HTML>