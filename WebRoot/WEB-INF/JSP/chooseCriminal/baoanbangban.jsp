<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>报安邦办(单个罪犯)</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
</head>
<body onload="init('${menuid}');">
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
			<tr>
				<td style="width: 100%;">
					<a class="mini-button" iconCls="icon-add" plain="true" id="12707" onclick="add('12707');">新增</a>
					<a class="mini-button" iconCls="icon-remove" plain="true" style="display: none;" id="11994" onclick="deleteBatch();">批量删除</a>
					<a class="mini-button"  iconCls="icon-close" plain="true"  onclick="onCancel();">关闭</a>
				</td>
				<td style="white-space: nowrap;">
					<input class="mini-textbox" id="key" class="mini-textbox" emptyText="文书简介" onenter="onKeyEnter" />
					<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					<!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width: 100%; height: 100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20" 
			allowAlternating="true" showLoading="true" allowResize="false" url="getjailReviewOfficeList.action?1=1&tempid=SZ_BWJY_TZSABB&crimid=${crimid}" multiSelect="true">
			<div property="columns">
				<div type="checkcolumn" width="15px"></div>
				<div field="introduction" headerAlign="center" align="left" allowSort="true" width="100px;">文书简介</div>
				<div field="departid" headerAlign="center" align="center" allowSort="true" width="40px;">组织机构</div>
				<div field="opid" headerAlign="center" align="center" allowSort="true" width="40px">操作人</div>
				<div field="optime" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer" width="40px">操作时间</div>
				<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="80px">操作</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		 mini.parse();
	     var datagrid = mini.get("datagrid");
	     datagrid.load();
	     datagrid.sortBy("docid", "desc");
	     
	     function onActionRenderer() {
	     	var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
		    return s;
	     }
	 	function add(menuid){
	 		var crimid=mini.get("crimid").getValue();
	 		var operator="new";
	 		mini.open({
	 	        url: "toAnBangBanAddpage.action?1=1&tempid=SZ_BWJY_TZSABB&menuid="+menuid+"&crimid="+crimid+"&operator="+operator,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "报安邦办", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new"};
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	    });	
	 	 }
	    //查看
		function chakan(menuid) {
			var row =datagrid.getSelected();//获取选中记录
	    	mini.open({
	            url: "editjailReviewOffice.action?1=1&docid="+row.docid+"&menuid="+menuid,
	            showMaxButton: true,
	         	allowResize: false, 
	            title: "报安邦办", width: 900, height: 500,
	            onload: function () {
	                var iframe = this.getIFrameEl();
	                var data = { action:"show" };
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (text){
	            	datagrid.reload();
	            }
	        });
		}     
		 //修改
		function xiugai(menuid) {
			var row = datagrid.getSelected();//获取选中记录
			var crimid = mini.get("crimid").getValue();
	    	mini.open({
	            url: "editjailReviewOffice.action?1=1&docid="+row.docid+"&crimid="+crimid+"&menuid="+menuid,
	            showMaxButton: true,
	         	allowResize: false, 
	            title: "报安邦办", width: 900, height: 500,
	            onload: function () {
	                var iframe = this.getIFrameEl();
	                var data = { action:"edit" };
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (action){
	            	datagrid.reload();
	            }
	        });
		}        
		//删除   
	    function shanchu(){
	    	var row = datagrid.getSelected();//获取选中记录
	        if (row) {
	            if (confirm("确定删除选中记录？")) {
	                datagrid.loading("操作中，请稍后......");
	                $.ajax({
	                    url: "deletejailReviewOffice.action?1=1&docid="+row.docid,
	                    type: "post",
	                    success: function (text) {
	                		datagrid.reload();
	                    },
	                    error: function () {
	                    	alert("操作失败!");
	                    }
	                });
	            }
	        } else {
	            alert("请选中一条记录");
	        }
		}
		//批量删除
        function deleteBatch(){
        	var rows = datagrid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    datagrid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "deletejailReviewOffice.action?1=1&docid="+id,
                        type: "post",
                        success: function (text) {
                            datagrid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        	datagrid.reload();
                        }
                    });
                }
            } else {
                alert(confirmMessages);
            }
		}
		function onCancel(){
			var url = "medicalParoleNoticeToSafeDeputy.action?1=1";
			self.location.href=url;
		}
	</script>
</body>
</html>