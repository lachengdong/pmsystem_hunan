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
<title>档案文件查看</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
}
</style>

</head>

<body style="overflow: hidden;">
	<!--导航栏-->
	<div id="page-navbar">
		<div id="yw0" class="td-nav">
			<table class="td-nav-table">
				<tr>
					<td><span class="td-nav-title">${archfile.fileName}</span></td>
					<td>
						<div>
							<a onclick="CloseWindow('close');" class="btn btn-danger btn-sm pull-right"
								style="margin-left:10px;padding:2px 10px;">关闭</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="container-scroller">
		<div>
			<table class="table table-bordered table-striped" width="98%">
				<tr>
					<td class="span3" style="text-align: right;"><b>组织机构代码：</b></td>
					<td>${archfile.unitCode}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>全宗号：</b></td>
					<td>${archfile.fondsNo}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>年度：</b></td>
					<td>${archfile.year}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>保管期限：</b></td>
					<td>${archfile.saveDate}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>归档机构：</b></td>
					<td>${archfile.orgName}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>盒号：</b></td>
					<td>${archfile.boxName}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>件号：</b></td>
					<td>${archfile.fileNo}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>档号：</b></td>
					<td>${archfile.archiveNo}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>文件标题：</b></td>
					<td>${archfile.fileName}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>发文单位：</b></td>
					<td>${archfile.sendUnit}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>文件编号：</b></td>
					<td>${archfile.fileCode}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>载体数量：</b></td>
					<td>${archfile.carrierNo}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>时间：</b></td>
					<td>${archfile.date1}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>载体单位：</b></td>
					<td>${archfile.carrierType}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>文种：</b></td>
					<td>${archfile.type}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>秘密等级：</b></td>
					<td>${archfile.secret}</td>
				</tr>
				<tr>
					<td class="span3" style="text-align: right;"><b>紧急程度：</b></td>
					<td>${archfile.urgency}</td>
				</tr>

				<tr>
					<td class="span3" style="text-align: right;"><b>附件：</b></td>
					<td><c:if test="${attachList.size()<1}">
						无
						</c:if> <c:forEach var="attachment" items="${attachList}">
								<div class="attachment-wrapper" id="attch_${attachment.id}" style="float:left;">
									<div class="dropdown clearfix">
										<a class="dropdown-toggle"
											href="${path}/${attachment.downhref}"><img
											src="${path}/${attachment.thumbnail}" />${attachment.fileName}</a><span
											class="size">(${attachment.strSize})</span>
										<ul class="dropdown-menu">
											<li><a href="${path}/${attachment.downhref}">下载</a></li>
										</ul>
									</div>
								</div>
							</c:forEach></td>
				</tr>

				<tr>
					<td class="span3" style="text-align: right;"><b>备注：</b></td>
					<td>${archfile.remark}</td>
				</tr>

			</table>

		
		</div>
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>
