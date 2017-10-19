<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	String tempid = request.getParameter("tempid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>模板元素管理</title>
		
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
		    }   
		</style>
	</head>

	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width: 100%;">
				       <a class="mini-button" iconCls="" plain="true" onclick="add();">添加元素</a>
				       <a class="mini-button" iconCls="" plain="true" onclick="javascript:history.go(-1);">返回</a>
					</td>
					<td style="white-space: nowrap;">
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" id="key" class="mini-textbox"
							emptyText="表名、字段名" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>

						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help"
							onclick="chakanshuoming('8738');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/form/showtempletmanage.action?1=1&tempid=<%=tempid %>"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<div type="indexcolumn" width="10" headerAlign="center" align="center">序号</div>
					<div field="tempid" width="15" headerAlign="center" allowSort="true" align="center">表单ID</div>
					<div field="ename" width="35" headerAlign="center" allowSort="true" align="center">表名</div>
					<div field="ecolname" width="40" headerAlign="center" align="center" allowSort="true">表字段名</div>   
					<div field="optime" width="40" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">操作时间</div>   
					<div width="40" headerAlign="center" align="center" allowSort="false" renderer="onCaoZuo">操作</div> 
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
		 	var grid = mini.get("datagrid");
			grid.load();
			grid.sortBy("optime", "asc");
			function search() {
			     var key = mini.get("key").getValue();
			     grid.load({ key: key });
			}
			function onKeyEnter(e) {
			   search();
			}
			//操作
			function onCaoZuo(e){
				var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
		       var s =" <a class=\"Edit_Button\" href=\"javascript:remove('11042');\" >删除</a>";
			   return s;
			}
			function add(){
				var win=mini.open({
					 url:"form/edittemplet.action?1=1&tempid=<%=tempid%>",
			         showMaxButton: true,
			         allowResize: false, 
			         title: "添加模板元素", 
			         width: 950, 
			         height: 550,
			         onload: function () {
			             var iframe = this.getIFrameEl();
			             var data = { action: "new"};
			             iframe.contentWindow.SetData(data);
			         },
			         ondestroy: function (action) {
			             mini.get("datagrid").reload();
			         }
			     });
			     //win.max();
			}
			function edit(){
				var row = grid.getSelected();
				var win=mini.open({
					 url:'form/editformmanage.action?1=1',
			         showMaxButton: true,
			         allowResize: false, 
			         title: "编辑表单模板", 
			         width: 950, 
			         height: 550,
			         onload: function () {
			             var iframe = this.getIFrameEl();
			             var data = { action: "edit",tempid:row.tempid};
			             iframe.contentWindow.SetData(data);
			         },
			         ondestroy: function (action) {
			        	 grid.reload();
			         }
			     });
			     //win.max();
			}
			function remove(resid){
				var url = '<%=path%>/form/removetempletmanage.action?1=1';
				var row = grid.getSelected();
				var tempid = row.tempid;
				var ename = row.ename;
				var ecolname = row.ecolname;
				if (confirm("确定删除选中记录？")) {
					$.ajax({
				         url:url, 
				         data: {tempid:tempid,resid:resid,ename:ename,ecolname:ecolname},
				         type: "post",
				         cache:false,
				         async:false,
				         success: function (text){
				         	alert("操作成功!");
				         	grid.reload();
				         }
					});
				}
			}
		</script>
	</body>
</html>