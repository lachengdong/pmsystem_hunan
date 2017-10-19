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
<title>公文模板管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

</head>

<body style="padding:0px;">
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<table>
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-add" plain="true" onclick="edit('new');">新增</a></td>
				<td style="white-space: nowrap;"><input class="mini-textbox"
					id="key" class="mini-textbox" emptyText="模板名称、模板描述"
					onenter="onKeyEnter" /> <a class="mini-button" plain="true"
					iconCls="icon-search" plain="true" onclick="search()">快速查询</a> <!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help"
					onclick="chakanshuoming('8738');"></a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid" allowMoveColumn="false"
			url="${path}/doccftpl/getdocTemplateList.action"
			style="width: 100%; height: 100%;" class="mini-datagrid"
			allowResize="false" showLoading="true" multiSelect="true"
			allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
			<div property="columns">
				<div type="indexcolumn" width="8%" headerAlign="center"
					align="center">序号</div>
				<div field="tempname" width="20%" headerAlign="center" align="left"
					allowSort="true">模板名称</div>	
				<div field="modulename" width="10%" headerAlign="center"
					allowSort="true" align="left" renderer="onmoduleRenderer">模板类型</div>			
				<div field="remark" headerAlign="center" align="left"
					allowSort="true" width="30%">备注</div>
				<div field="optime" width="15%" headerAlign="center"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss" align="center">修改时间</div>
				<div headerAlign="center" align="center"
					allowSort="false" renderer="onCaoZuo">操作</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid");
		this.search();
		grid.sortBy("optime", "desc");

		function search() {
			var key = mini.get("key").getValue();
			var param = window["param"] || {};
			param["key"] = key;
			param["total"] = -1;
			window["param"] = param;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}
		function onKeyEnter(e) {
			search();
		}
		//操作
		function onCaoZuo(e) {
			var s = " <a class=\"Edit_Button\" href=\"javascript:edit('edit');\" >编辑</a>"
					+ " <a class=\"Edit_Button\" href=\"javascript:remove();\" >删除</a>";
			return s;
		}
		
		function onmoduleRenderer(e)
		{
			var states = {
					"docsend" : "公文正文模板",
					"ringred" : "套红模板"
				};
				return states[e.value];
		}

		function edit(action) {
			var row = {};
			var url = '${path}/doccftpl/toDocTempEditPage.action?actiontype='
					+ action;
			if (action == 'edit') {
				row = grid.getSelected();
				url += '&doctempid=' + row.doctempid;
			}
			row.action = action;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				title : "编辑公文模板",
				width : 950,
				height : 550,
				showHeader : false,
				onload : function() {
					var iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(row);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
			win.max();
		}
		function remove() {
			var url = '${path}/doccftpl/removeDocTemplate.action?1=1';
			var row = grid.getSelected();
			var data = {
				doctempid : row.doctempid
			};
			doRemove(data, url, grid);
		}
	</script>
</body>
</html>
