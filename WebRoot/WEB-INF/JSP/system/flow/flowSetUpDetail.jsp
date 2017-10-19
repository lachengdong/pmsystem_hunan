<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String type = request.getParameter("type");
	Calendar cal = Calendar.getInstance();
	int mon = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
%>

<html>
	<head>
		<title>流程配置管理明细页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				font-size: 12px;margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width:100%;">
						<a class="mini-button" plain="true" iconCls="icon-add" onclick="addflowpage();">新增</a>
					</td>
					<td style="white-space:nowrap;"></td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<input name="showid" id="showid" type="hidden" />
			<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" sizeList="[10,20,50,100]" pageSize="20" allowAlternating="true"
				 url="getFlowSeUpByDepartid.json?1=1&groupid=${groupid}" idField="fmfileid" multiSelect="true">
				<div property="columns">
					<div type="checkcolumn" width="30px"></div>
					<div field="sn" width="30px" headerAlign="center" align="center" allowSort="true">序号</div>
					<div field="remark" width="80px" headerAlign="center" align="center" allowSort="true">
						配置名称
					</div>
					<div field="nodeid" width="80px" headerAlign="center" align="center" allowSort="true">
						节点ID
					</div>
					<div field="userids" width="100px" headerAlign="center" align="center" allowSort="true">
						用户ID
					</div>
					<div field="text1" width="100px" headerAlign="center" align="center" allowSort="true">
						备注1
					</div>
					<div field="text2" width="100px" headerAlign="center" align="center" allowSort="true">
						备注2
					</div>
					<div field="" width="80px" headerAlign="center" align="center"  allowSort="false" renderer="caozuo">
						操作
					</div>
				</div>
			</div>
		</div>

		<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        
        function caozuo(e){
        	var flow=e.record;
        	var s='<a class="Edit_Button" href="javascript:editflowpage()">修改</a>';
				s+='&nbsp;&nbsp;<a class="Edit_Button" href="javascript:deleteflowpage()">删除</a>';
        	return s;
        }
        function editflowpage(){
        	var row = grid.getSelected();
        	var url="addFlowPageSetup.page?1=1&flowdefid=${flowdefid}&groupid=${groupid}&nodeid="+row.nodeid;
        	if(row){
        		mini.open({
	        		url:url,
	        		title:"修改",width:300,height:350,
	        		showMaxButton:true,
	        		allowResize:true,
	        		onload:function (){
		        		var iframe = this.getIFrameEl();
		 	            var data = { action: "edit" };
		 	            //iframe.contentWindow.SetData(data);
	        		},
	        		ondestroy:function(){
	        			grid.reload();
	        		}
	        	});
        	}else{
        		alert("请至少选择一条记录！");
        	}
        }
        function deleteflowpage(){
        	var row =grid.getSelected();//获取选中记录
            if (row) {
                if (confirm("确定删除选中记录？")) {
                    $.ajax({
                        url: "deleteFlowPageSetup.json?1=1&flowdefid=${flowdefid}&groupid=${groupid}&nodeid="+row.nodeid,
                        type: "post",
                        success: function (text) {
                        	alert("操作成功!");
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        	grid.reload();
                        }
                    });
                }
            } else {
                alert("请至少选择一条记录！");
            }
		}
        function addflowpage(){
        	var url="addFlowPageSetup.page?1=1&flowdefid=${flowdefid}&groupid=${groupid}";
        	mini.open({
        		url:url,
        		title:"新增",width:300,height:350,
        		showMaxButton:true,
        		allowResize:true,
        		onload:function (){
	        		var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            //iframe.contentWindow.SetData(data);
        		},
        		ondestroy:function(){
        			grid.reload();
        		}
        	});
        }
    </script>
	</body>
</html>