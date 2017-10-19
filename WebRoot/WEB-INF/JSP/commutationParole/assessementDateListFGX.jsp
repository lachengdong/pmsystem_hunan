<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>考核补录</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
	<div id="datagrid" class="mini-splitter"
		style="width: 100%; height: 100%;">
		<div showCollapseButton="false" style="padding:0px;">
			<div class="mini-toolbar" style="padding:0px;border-bottom: 0px;">
				<table>
					<tr>
						<td style="width:100%">${topstr} <span class="separator"></span>
							<!--  <a class="mini-button" iconCls="icon-reload" plain="true" onclick="refreshPage" >刷新</a> -->
							<a class="mini-button" iconCls="icon-ok" plain="true"
							onclick="piliangshezhi();">批量设置</a>&nbsp;&nbsp;&nbsp;&nbsp;说明：按【裁定日期】批量设置【考核止日】
						</td>
						<!-- <td style="white-space:nowrap;"> 
	                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音首字母" style="width:100px;" onenter="onKeyEnter"/>   
	                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
	                 </td> -->
					</tr>
				</table>
			</div>

			<div class="mini-fit" id="div_two">
				<div id="datagrid1" class="mini-datagrid"
					style="width:100%;height:100%;" sizeList="[10,20,50,100]"
					pageSize="20"
					url="<%=path %>/assessmentDate/getSentenceChangedData.json?1=1"
					allowResize="true" allowCellEdit="true" allowCellSelect="true"
					multiSelect="true" editNextOnEnterKey="true" editNextRowCell="true"
					onrowclick="showDetail">
					<div property="columns">
						<div field="courtsanction" width="80" headerAlign="center"
							align="center" allowSort="false">判裁日期</div>
						<div field="awardend" width="80" headerAlign="center"
							align="center" allowSort="false" renderer="onDateRenderer"
							dateFormat="yyyy-MM-dd">
							考核止日 <input property="editor" class="mini-datepicker"
								style="width:100%;" />
						</div>

					</div>
				</div>
			</div>
		</div>
		<div showCollapseButton="false" size="900px;">
			<div class="mini-toolbar" style="border:0px;padding:0px;">
				<table style="width:100%;">
					<tr>
						<td><a class="mini-button" iconCls="icon-ok" plain="true"
							onclick="saveData1">批量保存</a></td>
						<td>说明：【单个罪犯】设置【考核止日】</td>
						<td style="white-space:nowrap; " align="right"><input
							id="key" class="mini-textbox" vtype="maxLength:50;"
							emptyText="编号、姓名、拼音首字母" style="width:100px;" onenter="onKeyEnter" />
							<a class="mini-button" iconCls="icon-search" plain="true"
							onclick="search()">查询</a></td>
					</tr>
				</table>
			</div>
			<div class="mini-fit" id="div_two" style="width:100%;height:100%;">
				<div id="datagrid2" class="mini-datagrid" sizeList="[10,20,50,100]"
					pageSize="20" url="<%=path %>/assessmentDate/getList.json?1=1"
					allowResize="true" allowCellEdit="true" allowCellSelect="true"
					multiSelect="true" editNextOnEnterKey="true" editNextRowCell="true"
					style="width:100%;height:100%;">
					<div property="columns">
						<div field="CRIMID1" allowSort="true">罪犯编号</div>
						<div field="NAME1" allowSort="true">姓名</div>
						<div field="COURTSANCTION1" allowSort="false">判裁日期</div>
						<div field="AWARDEND1" renderer="onDateRenderer"
							dateFormat="yyyy-MM-dd">
							考核止日 <input property="editor" class="mini-datepicker" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        var grid2 = mini.get("datagrid2");
        grid.load();
        //grid2.load();	
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        
        function search(){
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid2.load({ key: key });
        }
        
		function saveData() {
            var data = grid.getChanges();
            var json = mini.encode(data);
            savedate(json);
        }
        
        function saveSingleData(){
        	var data = grid.getSelected();
        	var json = mini.encode(data);
        	savedate(json);
        }
        
        function savedate(json){
        	grid.loading("保存中，请稍后......");
            $.ajax({
                url: "<%=path%>/assessmentDate/saveSentenceChanges.action?1=1",
                data: { data: json},
                type: "post",
                success: function (text){
                	if(text==1){
                		alert("操作成功！");
                	}else{
                		alert("操作失败！");
                	}
                    grid2.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
            
        }
        
		// 刷新本页面
		function refreshPage(){
			if(!window["____refreshPage"]){window["____refreshPage"] = true;location.reload();
			} else {
				window.setTimeout(function(){window["____refreshPage"] = false;},1*1000);
			}
		};
         
         function onActionRenderer(e) {
        	var s = '${middlestr}';
	     	return s;            
        }	
        
		// 渲染日期
	    function onDateRenderer(e){
	    	if(e && e.value){
	    		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	    	}
	        return "";
	    }   
	    
		function viewScreeningExcuse() {
			var row = grid.getSelected();
			var url = "<%=path%>/assessmentDate/viewscreeningexcuse.action?1=1&crimid="+ row.crimid;
			var win = mini.open( {
				url : url,
				title : "筛查依据情况",
				width : 600,
				height : 490,
				showMaxButton : true,
				allowResize : false,
				onload : function(){
					
				},
				ondestroy : function(action) {
					//	grid.reload();
				}
			});
		}
		function piliangshezhi(){
			var data = grid.getChanges();
			var json = mini.encode(data);
			var url = "<%=path%>/assessmentDate/saveKaoHeZhi.action?1=1";
			$.ajax({
                url: url,
                data: {jsons:json},
                type: "post",
                success: function (text) {
                	alert("操作成功");
                	grid.load();
                },
                error: function () {
                	alert("操作失败");
                	grid.load();
                }
            });
		}
		  function showDetail(){
			  var row = grid.getSelected();
			  var courtsanction = row.courtsanction.split("-").join("");
			  var url="<%=path %>/assessmentDate/getList.json?1=1&&courtsanction="+courtsanction;
			  grid2.setUrl(url);
			  grid2.load(); 
		  }
		  
		  function saveData1() {
	            var data = grid2.getChanges();
	            var json = mini.encode(data);
	            grid2.loading("保存中，请稍后......");
	            $.ajax({
	                url: "<%=path%>/assessmentDate/saveAWARDEND.action?1=1",
	                data: { data: json},
	                type: "post",
	                success: function (text) {
	                    grid2.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
	        }
    </script>
</body>
</html>