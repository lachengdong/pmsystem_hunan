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
		<title>收集编目</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
		    }   
		</style>
	</head>

	<body onload="init('1600_001_001');">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<table>
				<tr>
					<td style="width: 100%;">
						<a class="mini-button" style="display:none;" id="1600_001_001_01" iconCls="icon-addfolder" plain="true" onclick="addArchives('1600_001_001_01');">导入档案</a>
						<!-- 收集编目页面也追加一个批量删除按钮 -->
						<a class="mini-button"  id="12941" iconCls="icon-remove" plain="true" onclick="remove('12941');">批量删除</a>
					</td>
					<td style="white-space: nowrap;">
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" id="key" style="width:150px;" emptyText="罪犯编号、姓名、拼音、档案名称" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('1600_001_001');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" class="mini-datagrid" url="<%=path%>/flow/getarchives.action?1=1&flowdefid=arch_zfdajdsp&ispermission=1"
				style="width: 100%; height: 100%;"  allowResize="true" multiSelect="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div type="indexcolumn" width="8" headerAlign="center" align="center">序号</div>
					<div field="applyid" width="15" headerAlign="center" allowSort="true" align="center">罪犯编号</div>
					<div field="applyname" width="15" headerAlign="center" allowSort="true" align="center">姓名</div>
					<div field="conent" width="35" headerAlign="center" allowSort="true" align="left">档案名称</div>
					<div field="startsummry" width="40" headerAlign="center" align="center" allowSort="true">开始摘要</div>  
					<div field="archiveid" width="40" headerAlign="center" align="center" allowSort="true">档案ID</div>
					<div field="state" width="20" headerAlign="center" align="center" renderer="onRenderer" allowSort="true">状态</div>  
					<!-- 
						<div field="endsummry" width="40" headerAlign="center" align="center" allowSort="true">结束摘要</div>   
						<div field="commenttext" width="25" headerAlign="center" align="center" allowSort="true">审批意见</div> 
					 --> 
					<div field="opname" width="15" headerAlign="center" align="center" allowSort="true">操作人</div>
					<div field="optime" width="30" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true">提交时间</div> 
					<div field="" width="20" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
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
			function addArchives(menuid){
				var url = '<%=path%>/flow/toimportarchives.action?1=1&menuid='+menuid;
				self.location.href=url;
			}
			function onConentRenderer(e) {
		       var record = e.record;
		       return record.conent.substring(0,8);
		   	}
			function onRenderer(e) {
		       var grid = e.sender;
		       var record = e.record;
		       var uid = record._uid;
		       var rowIndex = e.rowIndex;
		       var state = record.state;
		       var nodeid = record.nodeid;
		       var commenttext = record.commenttext;
		       var s = "";
		   	   if(nodeid == -1 && state == 3) s = "未通过";
		   	   else if(!commenttext && state == -1) s = '审批中';
		   	   else s = "审核中";
		       return s;
		   	}
			function onActionRenderer(e) {
		       var record = e.record;
		       var state = record.state;
		       var nodeid = record.nodeid;
		       var s = "";
		   	   if(nodeid == -1 && state == 3) s = '<a class="Edit_Button" href="javascript:dealarchive(0);" >处理</a>';
		   	   else s = '<a class="Edit_Button" href="javascript:dealarchive(1)" >查看</a>';
		       return s;
		   	}
			function dealarchive(type){
	            var url = 'flow/dealarchives.action?1=1&resid=10623';
	            var row = grid.getSelected();
	            var action = "view";
	            if(type == 0) action = "edit";
	            if (row) {
                    var flowid = row.flowid;
                    var name = "罪犯"+row.applyname+"【"+row.applyid+"】的"+row.conent;
                    var win = mini.open({
	                    url: url+"&flowids="+flowid,
	                    title: "档案", width: 600, height: 360,
	                    onload: function () {
	                        var iframe = this.getIFrameEl();
	                        var data = { action:action,flowid:flowid,flowdraftid:row.flowdraftid,
	    	                name:name,content:row.content};
	                        iframe.contentWindow.SetData(data);
	                    },
	                    ondestroy: function (action) {
	                        grid.reload();
	                    }
	                });
                    win.max();
	            } else {
	                alert("请选中一条记录");
	            }
			}
			
			function remove(resid){
				var url = 'removearchives.json?1=1';
	            var rows = grid.getSelecteds();
	            if (rows && rows.length>0) {
	            	if (confirm("确定删除选中记录？")){
	            		var archiveids = [];
	                    for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        archiveids.push(r.archiveid);
	                    }
	                    var archiveid = archiveids.join(',');
	                    $.ajax({
	                        url: url,
	                        type: 'POST',
	                        cache:false,
	       	             	async:false,
	                        data:{resid:resid,archiveid:archiveid},
	                        success: function (text) {
	                            alert("操作成功!");
	                            grid.reload();
	                        },error: function () {
	                        	alert("操作失败!");
	                        }
	                    });
	            	}
	            } else {
	                alert("请选中一条记录");
	            }
			}
		</script>
	</body>
</html>