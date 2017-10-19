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
		<title>流程配置管理页面</title>
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
					<td style="white-space:nowrap;"><input id="key" class="mini-textbox" emptyText="配置名称，流程定义ID"  onenter="onKeyEnter"/>
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<input name="showid" id="showid" type="hidden" />
			<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" url="getFlowSeUpByDepartid.json" 
				 allowAlternating="true" idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20" multiSelect="true">
				<div property="columns">
					<div type="checkcolumn" ></div>
					<div type="indexcolumn" width="20" headerAlign="center" align="center" allowSort="true">序号</div>
					<div field="remark" width="30%" headerAlign="center" align="center" allowSort="true">
						配置名称
					</div>
					<div field="name" width="20%" headerAlign="center" align="center" allowSort="true">
						承办人
					</div>
					<div field="action" width="30%" headerAlign="center" align="center"  allowSort="false" renderer="caozuo">
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
        	var s='<a class="Edit_Button" href="javascript:mingxi()">明细</a>';
        	return s;
        }
        function mingxi(){
        	var row = grid.getSelected();
        	var url="openFlowSetUpPage.action?1=1&flowdefid="+row.flowdefid+"&groupid="+row.groupid;
        	if(row){
        		mini.open({
        			url:url,
        			title:"流程配置明细", width: 1000, height: 550,
       			 	showMaxButton: true,
                    allowResize: false,
                onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            //iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	grid.reload();
	 	        }
        		});
        	}else{
        		alert("请至少选择一条记录！");
        	}
        }
        
        
        
        function addflowpage(){
        	var url="addFlowPageSetup.page?1=1";
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
        function search(){
        	var key=mini.get("key").getValue();
        	grid.load({key:key});
        }
        
        function onKeyEnter(){
        	search();
        }
        
        
    </script>
	</body>
</html>