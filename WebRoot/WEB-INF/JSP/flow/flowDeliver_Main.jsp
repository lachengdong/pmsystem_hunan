<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程发起主页面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	if ('${attachjs}' != '')
		$.getScript('${path}/scripts/${attachjs}');
</script>

</head>
<body>
	<div class="mini-toolbar" style="border-bottom: 0px;">
		<table>
			<tr>
				<td style="width: 100%;white-space: nowrap;"><span
					class="td-nav-title" style="display: block;">${title}</span></td>
				<td style="white-space: nowrap;">${topTools}<c:if
						test="${action !='view'}">
						<a class="btn btn-danger" id="allsave" plain="true"
							onclick="doSave();">保存</a>
					</c:if> <a class="btn" plain="true" onclick="CloseWindow('close');">关闭</a>
					<a class="mini-button" plain="true" iconCls="icon-help"
					onclick="chakanshuoming('8738');"></a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
	    <input  id="bybdid" class="mini-hidden" value="${bybdid}"/>
		<div id="docTabs" class="mini-tabs" contextMenu="#tabsMenu"
			name="docTabs" activeIndex="0"
			style="width:100%;height:100%;border: 0px;" plain="false"
			onbeforeactivechanged="myactivechanged">
			<div name=flowform title="表单" style="border: 0px;" id="flowform"
				url="${path}/deliver/toflowDeliver_Form.page?action=${action}&menuid=${menuid}&type=${type}&flowid=${flowid}&flowdefid=${flowdefid}&templetid=${templetid}&attachjs=${attachjs}&taskid=${taskid}&solutionid=${solutionid}"></div>
			<c:if test="${type =='1'}">
				<div name="docContent" title="正文" id="docContent"
					style="border: 0px;"></div>
			</c:if>
			<c:if test="${type !='3'}">
				<div name="docAipContent" title="版式正文" id="docAipContent"
					style="border: 0px;"></div>
			</c:if>
			<div name="flowDeliverInfo" title="流程" id="flowDeliverInfo"
				style="border: 0px;"></div>
		</div>

	</div>
	<script type="text/javascript">
		var status = "OK";		
		var extentInfo='${extentInfo}';

		mini.parse();
		top["win"]=window;
		function getHiddenValue(id){
			return mini.get(id).getValue();
		}
		var currentTab = "flowform";
		var taburls = {
			"flowform" : "${path}/deliver/toflowDeliver_Form.page?action=${action}&menuid=${menuid}&type=${type}&flowid=${flowid}&flowdefid=${flowdefid}&templetid=${templetid}&attachjs=${attachjs}&taskid=${taskid}&solutionid=${solutionid}",
			"docContent" : "${path}/docdeliver/todocContent.page?action=${action}&menuid=${menuid}&flowid=${flowid}&taskid=${taskid}",
			"docAipContent" : "${path}/docdeliver/toflowInD_docAip.page?action=${action}&menuid=${menuid}&type=${type}&flowid=${flowid}&flowdefid=${flowdefid}&templetid=${templetid}&taskid=${taskid}",
			"flowDeliverInfo" : "${path}/deliver/toflowDeliver_Inf.page?action=${action}&menuid=${menuid}&type=${type}&flowid=${flowid}&flowdefid=${flowdefid}"
		};
		
		function getExtentInfo()
		{
			return extentInfo;
		}
		/**
		 *保存草稿 
		 */
		///TODO:需修改 
		function doSave() {
			var result = -1;

			var tabs = mini.get("docTabs");
			var tab = tabs.getTab("flowform");
			var iframe = tabs.getTabIFrameEl(tab);
			if (!beforeSaveAction(iframe.contentWindow.topOAflowdeliver.$aipObj))
				return;

			tab = tabs.getTab("docContent");
			if (tab) {
				iframe = tabs.getTabIFrameEl(tab);
				if (iframe && iframe.contentWindow) {
					iframe.contentWindow.DoSaveCnt();
				}
			}

			tab = tabs.getTab("docAipContent");
			if (tab) {
				iframe = tabs.getTabIFrameEl(tab);
				if (iframe && iframe.contentWindow) {
					iframe.contentWindow.DoSaveDocAip();
				}
			}

			tab = tabs.getTab("flowform");
			iframe = tabs.getTabIFrameEl(tab);
			if (iframe && iframe.contentWindow) {
				//保存表单数据				
				result = iframe.contentWindow.DoSaveDocForm();
			}
			if (result == 1) {
				//alert('保存成功！');
			} else {
				status = "fail";
			}
		}

		//解决IE10，tab切换画面不刷新问题
		function myactivechanged(e) {
			var tabs = mini.get("docTabs");
			var tabName = e.tab.name;
			var tab = tabs.getTab(tabName);
			if (currentTab == "") {
				tabs.loadTab(taburls[tabName], tab);
				currentTab = tabName;
			} else {
				setTimeout($.proxy(function() {
					var iframe = tabs.getTabIFrameEl(tab);
					var tab0 = tabs.getTab(currentTab);
					var iframe0 = tabs.getTabIFrameEl(tab0);
					if (iframe) {
						iframe0.style.visibility = 'hidden';
						setTimeout($.proxy(function() {
							iframe.style.visibility = 'visible';
							tabs.activeTab(tab);
						}, this), 100);
					} else {
						iframe0.style.visibility = 'hidden';
						setTimeout($.proxy(function() {
							tabs.activeTab(tab);
							tabs.loadTab(taburls[tabName], tab);
						}, this), 100);
					}
					currentTab = tabName;
				}, this), 100);

				if (browserVersion().ie && browserVersion().ie == '10.0') {
					throw "";
				}
			}
		}

		//function Execute(explain, beforeAction, afterAction)
		function Execute(explain, beforeAction) {
			var result = -1;
			var tabs = mini.get("docTabs");
			var tab = tabs.getTab("flowform");
			var iframe = tabs.getTabIFrameEl(tab);
			if (!beforeSaveAction(iframe.contentWindow.topOAflowdeliver.$aipObj))
				return;

			tab = tabs.getTab("docContent");
			if (tab) {
				iframe = tabs.getTabIFrameEl(tab);
				if (iframe && iframe.contentWindow) {
					iframe.contentWindow.DoSaveCnt();
				}
			}

			tab = tabs.getTab("docAipContent");
			if (tab) {
				iframe = tabs.getTabIFrameEl(tab);
				if (iframe && iframe.contentWindow) {
					iframe.contentWindow.DoSaveDocAip();
				}
			}

			tab = tabs.getTab("flowform");
			iframe = tabs.getTabIFrameEl(tab);
			if (iframe && iframe.contentWindow) {
				//流程流转
				if (beforeAction)//附加操作
				{
					iframe.contentWindow.doExecuteCustormMethod(beforeAction);
				}
				result = iframe.contentWindow.Execute(explain);
			}
			if (result == 1) {
				CloseWindow(status);
			} else {
				status = "fail";
			}
		}

		function applyDocCnt(id) {
			var tabs = mini.get("docTabs");
			var tab = tabs.getTab("docContent");
			tabs.loadTab(
					"${path}/docdeliver/todocContent.page?flowid=${flowid}&doctempid="
							+ id, tab);
		}

		function switchAipContent(aipName) {
			var tabs = mini.get("docTabs");
			var tab = tabs.getTab("docAipContent");
			tabs.activeTab(tab);
			tabs
					.loadTab(
							"${path}/docdeliver/toflowInD_docAip.page?action=${action}&menuid=${menuid}&type=${type}&flowid=${flowid}&flowdefid=${flowdefid}&templetid=${templetid}&docCnName="
									+ aipName, tab);
		}

		//关闭窗口
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else {
				window.returnValue = action;
				window.close();
			}
		}

		function SetFlowInstanceDocCntName(name) {
			var tabs = mini.get("docTabs");
			var tab = tabs.getTab("flowform");
			var iframe = tabs.getTabIFrameEl(tab);
			iframe.contentWindow.setFlowInstanceDocCntName(name);
		}

		function SetFlowInstanceDocAipName(name) {
			var tabs = mini.get("docTabs");
			var tab = tabs.getTab("flowform");
			var iframe = tabs.getTabIFrameEl(tab);
			iframe.contentWindow.setFlowInstanceDocAipName(name);
		}

		function beforeSaveAction(obj) {
			return 1;
		}

		function browserVersion() {
			var Sys = {};
			var ua = navigator.userAgent.toLowerCase();
			var s;
			(s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1]
					: (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1]
							: (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1]
									: (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1]
											: (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1]
													: (s = ua
															.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1]
															: 0;

			return Sys;
		};
	</script>
</body>
</html>
