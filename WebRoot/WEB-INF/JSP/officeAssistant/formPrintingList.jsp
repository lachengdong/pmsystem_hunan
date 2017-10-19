<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>表单打印列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
</head>
<body>
    <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
		<table >
 			<tr>
                <td style="width:100%;">
                </td>
                <td style="white-space:nowrap;">
            		<input class="mini-textbox" vtype="maxLength:50;" emptyText="请输入表单名称、系统模块" id="key" style="width:150px;" onenter="onKeyEnter"/>
					<a class="mini-button" iconCls="icon-search" plain="true" onclick="search" >查询</a>
           		</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" id="div_two" >
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"  url="<%=path%>/form/showformmanage.action?1=1" idField="id" pageSize="20" sizeList="[10,20,50,100]"   multiSelect="true" >	
	    	<div property="columns">
	    		<div type="indexcolumn" headerAlign="center" align="center" width="50px;">序号</div>
	    		<!-- <div field="tempid" headerAlign="center"  align="center" allowSort="true" width="200px">表单编号</div> -->
	    		<div field="tempname" headerAlign="center"  align="center" allowSort="true" width="200px;">表单名称</div>
	    		<div field="functionname" headerAlign="center"  align="center" allowSort="true" width="200px">系统模块</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;" width="100">操作</div>
			</div>        
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
		var grid = mini.get("datagrid1");
		grid.sortBy("functionname","desc");
		grid.load();

		function onActionRenderer() {
	        var s ="";
		    s=s+'<a class="New_Button" href="javascript:chakan()">查看</a>&nbsp;&nbsp;';
	        return s;
	    }
	    function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
			grid.load({ key: key });
        } 
  		function chakan(){
            var row = grid.getSelected();
            var menuid = "502";
            var url = "<%=path%>/formPrintingAndToGuide/showFormPrintingAndToGuide.page?1=1";
            if (row) {
                   var win = mini.open({
                    url: url+="&menuid="+menuid+"&tempid="+row.tempid,
                    showMaxButton: true,
                 		allowResize: false, 
                    title: "表单打印", width: 900, height: 500,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "chakan"};
                        iframe.contentWindow.SetData(data);
                    }
                });
            } 
		}
    </script>
 </body>
</html>