<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

	<title>公文审核</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
		rel="stylesheet" type="text/css" />
	<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
</head>
<body>


	<div class="mini-toolbar"
		style="height:30px; padding:0px;border-top: 0px;border-left: 0px;border-bottom: 0px;">
		<table>
				<tr>
									<td style="width: 100%;">
						<a class="mini-button"  id="1026601"
							iconCls="" plain="true" onclick="chooseAll(1026602)">批量处理</a>
					</td>
					<td style="white-space: nowrap;">
						
					</td>
				</tr>
			</table>
	</div>

	<div class="mini-fit" id="daibanshixiang"
		style="width:100%;height:100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width:100%;height:100%;" allowResize="false"
			sizeList="[20,50,100]" pageSize="20"
			url="<%=path%>/flow/getapprovelist.json?1=1&flowdefid=${flowdefid}"
			idField="" multiSelect="true" allowAlternating="true"
			virtualScroll="true" showLoading="true">
			<div property="columns">
				<div type="indexcolumn" width="10" headerAlign="center"
					align="center" allowSort="true">序号</div>
					<div type="checkcolumn" width="10"></div>
				<div field="applyname" width="25" headerAlign="center"
					align="center" allowSort="true">所属人</div>
				<div field="startsummry" width="60" headerAlign="center"
					align="center" allowSort="true">摘要</div>

				<div field="commenttext" width="30" headerAlign="center"
					align="center" allowSort="true">上次审批意见</div>
				<div field="opname" width="25" headerAlign="center" align="center"
					allowSort="true">提交人</div>
				<div field="optime" width="40" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true">提交时间</div>
				<div field="" width="20" headerAlign="center" align="center"
					allowSort="false" renderer="onActionRenderer">操作</div>
			</div>
		</div>
	</div>

	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
	</script>
</body>
</html>