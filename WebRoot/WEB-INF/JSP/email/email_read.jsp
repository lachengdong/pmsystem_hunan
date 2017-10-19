<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>邮件阅读</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta content="text/html;charset=utf-8" http-equiv="content-type" />

<meta http-equiv="cache-control" content="no-cache" />

<meta http-equiv="expires" content="0" />

<script type="text/javascript" src="<%=path%>/scripts/boot.js"></script>

<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />

<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />

<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />

</head>

<body
	style="padding:0px;font-size: 13px;lline-height: 20px;ccolor: #333;bbackground-color: #FFF;">
	<div id="yw8" class="td-nav" style="">
		<table class="td-nav-table" style="width:100%;">
			<tr>
				<td>
					<div>
						<div class="pull-right btn-group" style="margin-left:10px;">
							<a title="上一封" class="btn ${disableClsL}" id="yw9"
								href="${lhref}"> <i class="icon-arrow-left-11"></i>
							</a> <a title="下一封" class="btn ${disableClsN}" id="yw10"
								href="${nhref}"><i class="icon-arrow-right-12"></i> </a>
						</div>
						<div class="pull-right btn-group" data-toggle="buttons-radio">
							<button type="button"
								style="margin-right:10px;padding:2px 10px;margin-top:5px;"
								class="btn btn-info btn-sm" id="yw11"
								onclick="parent.hideEditWidow(true);">
								<span class="icon-arrow-left-7"></span> 返回
							</button>
							<c:if test="${box == '0'}">
								<button
									style="margin-right:10px;padding:2px 10px;margin-top:5px;"
									class="btn btn-info btn-sm" id="yw12"
									onclick="doWork('revert');">
									<i class="icon-reply"></i> 回复
								</button>
							</c:if>
							<button class="btn btn-info btn-sm" id="yw13"
								style="margin-right:10px;padding:2px 10px;margin-top:5px;"
								onclick="doWork('forward');">
								<i class="icon-redo-2"></i> 转发
							</button>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div style="position:absolute;top:60px;right:30px;z-index:4"
		class="btn-group" data-toggle="buttons-radio">
		<button title="打印" class="btn print" id="yw14" name="yt19"
			type="button">
			<i class="icon-print-2"></i>
		</button>
	</div>
	<div class="common_2col_content" style="padding:5px 0px;">
		<div class="readmailinfo"
			style="background: #e6f1f6;border-bottom:1px solid #d8e6ec;">
			<table class="table-condensed">
				<tbody>
					<tr>
						<td colspan="2" class="sub_title" nowrap><h5>
								${mail.subject}
								<c:if test="${mail.important != 0}">
									<span class='TextColor1' id="spnimport"> <script
											type="text/javascript">
										var importants = {
											"0" : "一般邮件",
											"1" : "重要邮件",
											"2" : "非常重要"
										};
										$('#spnimport')
												.text(
														importants['${mail.important}']);
									</script>
									</span>
								</c:if>
							</h5></td>
					</tr>
					<tr>
						<td class="addrtitle" style="white-space: nowrap;">发件人：</td>
						<td><div class="user-label-container">
								<a class="user-label " target="_self" data-id="1"
									id="fromuserName" href="#">${mail.createusername}</a>
							</div></td>
					</tr>
					<tr>
						<td class="addrtitle" nowrap>时 间：</td>
						<td>${mail.strCreateTime}</td>
					</tr>
					<tr>
						<td class="addrtitle" nowrap>附 件：</td>
						<td><c:if test="${mail.hasattach == 0}">
						无
						</c:if> <c:forEach var="attachment" items="${attachList}">
								<div class="attachment-wrapper" id="attch_${attachment.id}"
									style="float:left;">
									<div class="dropdown clearfix">
										<a class="dropdown-toggle"
											href="${path}/${attachment.downhref}"><img
											src="${path}/${attachment.thumbnail}" />${attachment.fileName}</a><span
											class="size">(${attachment.strSize})</span>
										<ul class="dropdown-menu">
											<li><a href="${path}/${attachment.downhref}"
												style="font-size:13px;">下载</a></li>
										</ul>
									</div>
								</div>
							</c:forEach></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="content" id="contentDiv">${mail.content}</div>
	</div>
	<div id="writewindow" class="mini-window" title="阅读个人邮件"
		style="width:800px;height:500px;border:0px;" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true"
		showHeader="false" borderStyle="border:0" allowDrag="true"></div>
	<script type="text/javascript">
		function doWork(action) {
			var url = "${path}/email/toWebMailWritePage.page?box=${box}&contentId=${contentId}&id=${id}&fromMail=${fromMail}"
					+ "&actionType=" + action;
			var win = mini.get("writewindow");
			win.load(url, function() {
				ifram0 = this.getIFrameEl();
				//ifram0.contentWindow.SetData(record.fromUname);
			}, function(action) {
			});
			win.max();
		}

		function hideEditWidow(flag) {
			var win = mini.get("writewindow");
			win.hide();
		}

		jQuery(function($) {
			$(function() {
				$('.print')
						.on(
								'click',
								function() {
									bdhtml = $('body').html();//获取当前页的html代码
									var time = '<h3 style="text-align:right">打印时间：2015-04-08 16:41:18</h3>';
									$('body').html(
											time
													+ $('.common_2col_content')
															.html());
									window.print();
									$('body').html(bdhtml);
								});

			});

		});
	</script>
</body>
</html>
