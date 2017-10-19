<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>表单管理</title>
		
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
				       <a class="mini-button" iconCls="" plain="true" onclick="add();">新增</a>
				       <a class="mini-button" iconCls="" plain="true" onclick="editClob();">大字段修改</a>
					</td>
					<td style="white-space: nowrap;">
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" id="key" class="mini-textbox"
							emptyText="表单ID、表单名称、系统模块" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">快速查询</a>

						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help"
							onclick="chakanshuoming('8738');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/getTemplateList.action?1=1&type=2"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<div type="indexcolumn" width="8" headerAlign="center" align="center">序号</div>
					<div field="generaltype" headerAlign="center"  align="center" renderer="onComboboxRenderer" allowSort="true" width="20">通用类型</div>
					<div field="tempid" width="30" headerAlign="left" align="left" allowSort="true" >表单ID</div>
					<div field="tempname" width="30" headerAlign="left" align="left" allowSort="true" >表单名称</div>
					<div field="functionname" width="30" headerAlign="left" align="left" allowSort="true">系统模块</div>   
					<div width="30" headerAlign="center" align="center" allowSort="false" renderer="onCaoZuo">操作</div> 
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
		 	var grid = mini.get("datagrid");
			grid.load();
			grid.sortBy("optime", "desc");
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
		       var s =" <a class=\"Edit_Button\" href=\"javascript:opentemplet('11040');\" >模板元素</a>"+
		       " <a class=\"Edit_Button\" href=\"javascript:regionallock('11042');\" >区域锁定</a>"+
		       " <a class=\"Edit_Button\" href=\"javascript:edit();\" >编辑</a>"+
		       " <a class=\"Edit_Button\" href=\"javascript:remove('11042');\" >删除</a>";
			   return s;
			}
			// 渲染通用类型
	        function onComboboxRenderer(e) {
	        	var Radiodata = [{ id: 1, text: '单位特有' }, { id: 2, text: '省局通用'}, { id: 3, text: '全国通用'}];
	            for (var i = 0, l = Radiodata.length; i < l; i++) {
	                var g = Radiodata[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        };
			function add(){
				var win=mini.open({
					 url:'form/editformmanage.action?1=1&type=new',
			         showMaxButton: true,
			         allowResize: false, 
			         title: "新增表单模板", 
			         width: 510, 
			         height: 200,
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
				var tempid = row.tempid;
				var departid = row.departid;
				var win=mini.open({
					 url:'form/editformmanage.action?1=1&type=edit&tempid='+tempid+'&departid='+departid,
			         showMaxButton: true,
			         allowResize: false, 
			         title: "编辑表单模板", 
			         width: 950, 
			         height: 550,
			         onload: function () {
			             var iframe = this.getIFrameEl();
			             var data = { action: "edit",tempid:tempid,departid:departid};
			             iframe.contentWindow.SetData(data);
			         },
			         ondestroy: function (action) {
			        	 grid.reload();
			         }
			     });
			     //win.max();
			}
			function remove(resid){
				var url = '<%=path%>/form/removereportmanage.action?1=1';
				var row = grid.getSelected();
				var tempid = row.tempid;
				var departid = row.departid;
				if (confirm("确定删除选中记录？")) {
					$.ajax({
				         url:url, 
				         data: {tempid:tempid,departid:departid,resid:resid},
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
			//区域锁定
			 function regionallock() {
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url:"<%=path%>/form/tolockmanage.action?1=1",
                    title: "区域锁定", width: 600, height: 400,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {tempid:row.tempid,departid:row.departid,uneditedfields:row.uneditedfields, editedfields:row.remark};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
			function opentemplet(resid){
				var row = grid.getSelected();
				var tempid = row.tempid;
				var url = "<%=path%>/form/totempletmanage.action?1=1&resid="+resid+"&tempid="+tempid;
				window.location = url;
			}
			function editClob(){
		        mini.open({
	               url:'form/editClobPage.action?1=1',
	               showMaxButton: true,
	               allowResize: false, 
	               title: "大字段修改", 
	               width: 950, 
	               height: 550,
	               onload: function () {
	                   var iframe = this.getIFrameEl();
	                   var data = { action: "edit"};
	                   iframe.contentWindow.SetData(data);
	               },
	               ondestroy: function (action) {
				       grid.reload();
				   }
				});
				     //win.max();
			}
		</script>
	</body>
</html>