<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>档案类别管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>

  </head>
  
  <body onload="init('${menuid}');">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<table>
				<tr>
					<td  style="width: 100%;">
				       <a class="mini-button" style="display:none" id="1600441" iconCls="icon-add" plain="true" onclick="addCode();">新增</a>
					</td> 
					<td style="white-space: nowrap;" >
						<input id = "pcodeid" class="mini-combobox" emptyText="类别" data="typedata" valueField="id" textField="text" onvaluechanged="ontypechanged" />
						<input class="mini-textbox" id="key" class="mini-textbox" emptyText="编码ID、档案名称" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/flow/getAllArchCode.json"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<div type="indexcolumn" width="10" headerAlign="center"
						align="center">序号</div>
					<div field="codeid" width="20" headerAlign="center"
						allowSort="true" align="center">
						编码ID
					</div>
					<div field="name" width="20" headerAlign="center"
						allowSort="true" align="center">
						档案名称
					</div>
					<div field="ispositive" width="15" renderer="onIsattachedRenderer" headerAlign="center" allowSort="true" align="center">
						正(副)卷<input property="editor" class="mini-textbox" style="width:100%;" data="Isattacheds"/>
					</div>
					<div field="departid" width="15" headerAlign="center"
						allowSort="true" align="center">
						所属单位id
					</div>
					<div field="sn" width="15" headerAlign="center"
						allowSort="true" align="center">
						排序号
					</div>
					<div field="tempid" width="15" headerAlign="center"
						allowSort="true" align="center">
						对应模板id
					</div>
					<div field="remark" width="15" headerAlign="center"
						allowSort="true" align="center">
						备注
					</div>
					<div width="15" headerAlign="center" align="center"
					 	allowSort="false" renderer="onCaoZuo">操作</div>  
				</div>
			</div>
		</div>
		<script type="text/javascript">
			var typedata=[{id:100, text:'保外'},{id:'000', text:'减刑假释'}];
			var Isattacheds = [{ id: 0, text: '副卷' },{ id: 1, text: '正卷'}];
			
			mini.parse();
		 	var grid = mini.get("datagrid");
			grid.sortBy("codeid", "desc");
			
			function search() {
			     var key = mini.get("key").getValue();
			     var pcodeid=mini.get("pcodeid").getValue();
			     grid.load({ key: key ,pcodeid: pcodeid});
			}
			function onKeyEnter(e) {
			   search();
			}
			function onCaoZuo(){
				var s = document.getElementById("middlebtns").value;
			    return s;
			}
			function editrow(){
				var row=grid.getSelected();
				mini.open({
					url:"<%=path%>/flow/addCodePage.page?1=1&addorupdate=edit&codeid="+row.codeid+"&departidtemp="+row.departid,
					showMaxButton: true,
	 	        	allowResize: false,
	 	        	title: "code维护", width: 350, height: 300,
	 	        	onload: function (action) {
						grid.reload();
					},
					ondestroy: function (action) {
	 	        }
				});
			}
			function remove(){
				if(confirm("确认删除？")){
				var row = grid.getSelected();
				$.ajax({
					url:"removeCode.action?1=1&codeid="+row.codeid+"&departid="+row.departid,
					type:"post",
					success: function (text){
						var value=eval(text);
						if(value>0){
							alert("操作成功!");
							grid.reload();
						}
					},
					error: function (){
						alert("操作失败！");
					}
				});
				}
			}
			function addCode(){
				var url="<%=path%>/flow/addCodePage.page?1=1&addorupdate=add";
				mini.open({
					url:url,
					showMaxButton: true,
	 	        	allowResize: false,
	 	        	title: "code维护", width: 350, height: 300,
	 	        	onload: function (action) {
						grid.reload();
					},
					ondestroy: function (action) {
	 	        }
				});
			}
			function ontypechanged(){
				search();
			}
			function onIsattachedRenderer(e) {
	            for (var i = 0, l = Isattacheds.length; i < l; i++) {
	                var g = Isattacheds[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
			
		</script>
  </body>
</html>