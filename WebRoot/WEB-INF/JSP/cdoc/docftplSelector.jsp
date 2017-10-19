<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文正文模板选择</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

<style type="text/css">
.kanban {
	margin-top: 5px;
}

.kanban-ul {
	MARGIN-LEFT: 80px
}

.kanban-ul>LI:hover {
	BACKGROUND-COLOR: #f3f3f3;
	box-shadow: inset 0px 1px 3px 0px rgba(0, 0, 0, 0.1);
	-webkit-box-shadow: inset 0px 1px 3px 0px rgba(0, 0, 0, 0.1)
}

.kanban-ul>LI {
	PADDING-BOTTOM: 8px;
	MARGIN: 10px 10px 0px 0px;
	PADDING-LEFT: 8px;
	PADDING-RIGHT: 8px;
	DISPLAY: block;
	FLOAT: left;
	PADDING-TOP: 8px;
	border-radius: 5px
}

.kanban-card {
	BORDER-BOTTOM: #d7d7d7 1px solid;
	POSITION: relative;
	BORDER-LEFT: #d7d7d7 1px solid;
	PADDING-BOTTOM: 4px;
	BACKGROUND-COLOR: #fff;
	PADDING-LEFT: 4px;
	PADDING-RIGHT: 4px;
	FLOAT: left;
	BORDER-TOP: #d7d7d7 1px solid;
	BORDER-RIGHT: #d7d7d7 1px solid;
	PADDING-TOP: 4px;
	border-radius: 2px
}

.kanban-card {
	BACKGROUND-COLOR: #fff;
	WIDTH: 200px;
	HEIGHT: 275px
}

.kanban-card A {
	CURSOR: pointer
}

.kanban-card .img {
	TEXT-ALIGN: center;
	MARGIN: 0px auto;
	WIDTH: 200px;
	HEIGHT: 200px;
	VERTICAL-ALIGN: middle;
	OVERFLOW: hidden
}

.kanban-card .caption {
	TEXT-ALIGN: center;
	WIDTH: 200px;
	DISPLAY: block;
	FLOAT: left
}

.kanban-card .caption SPAN {
	DISPLAY: block
}

.kanban-card .caption A {
	DISPLAY: inline-block;
	COLOR: black;
	TEXT-DECORATION: none
}

.kanban-card .kanban-link {
	MARGIN-TOP: 20px;
	FLOAT: right
}
</style>
</head>

<body>
	<div class="mini-fit kanban">
		<ul class="kanban-ul">
			<c:forEach var="clo" items="${doctemplist}">
				<li>
					<div class="kanban-card">
						<div class="img">
							<!--<img src="data:image/jpeg;base64,${clo.thumbnail2}" width="180px" height="400px" />-->
							<img
								src="${path}/doccftpl/getTempThum.action?doctempid=${clo.doctempid}"
								alt="暂无照片" width="220px" height="400px" />
						</div>
						<div class="kanban-link">
							<div style="TEXT-ALIGN: center">
								<a class="btn" data-url="" data-file-name="决定.docx" name="apply"
									style="MARGIN-RIGHT: 5px" onclick="applytemp('${clo.doctempid}');">应用</a> <a
									class="btn" style="MARGIN-RIGHT: 5px">预览</a>
							</div>
							<div class="caption">
								<a class="subject" title="${clo.tempname}"
									style="FONT-WEIGHT: bold">${clo.tempname}</a>
							</div>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<script type="text/javascript">
		function applytemp(id) {
			parent.applyDocCnt(id);
		}	
		function DoSaveCnt()
		{		
		}
	</script>
</body>
</html>
