<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.gkzx.util.property.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>检索</title>
    <meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
 	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
  
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
  </head>
  
  <body>
  	<OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WebBrowser width=0>
	</OBJECT>
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
	                <td>
		      			刑罚变动
	                </td>
               </tr>
		</table>
    </div>
    <!--撑满页面-->
    <div class="mini-fit" >
	    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:93%;" allowCellEdit="true" 
	    showPager="true" allowCellSelect="true" >
	    </div>  
	    <div>
	    	<table width="100%;">
	    		<tr>
	    			<td align="center">
	    				<input type="checkbox" value=""/>序号
		    			<input type="checkbox" value=""/>显示条件
		    			<input type="checkbox" value=""/>输出打印时间
	    				<span>行列分格线：</span>
	    				<input id="radioline" name="radioline" type="radio" value="" checked="checked"/>表格线
	    				<input id="radioline" name="radioline" type="radio" value="" />横线
	    				<input id="radioline" name="radioline" type="radio" value="" />无分隔线
	    			</td>
	    			<td>
	   					<a id="removeline"  onclick="onRemoveLine"  class="mini-button" iconCls="" >删除一行</a>
	   					<a id="exportExcel"  onclick="onExportExcel"  class="mini-button" iconCls="" >导出Excel</a>
	   					<a class="mini-button" iconCls="" onclick="PageSetup();">页面设置</a>
	   					<a id="printPreview" class="mini-button" iconCls=""   onclick="onPrintPreview();" >打印预览</a>
	   					<a id="zfprint"  class="mini-button" iconCls=""   onclick="onZfprint">正反打印</a>
	   					<a id="print"  class="mini-button" iconCls=""   onclick="onPrint">打印</a>
			           	<a id="cancel" class="mini-button"  iconCls=""  onclick="onCancel">关闭</a>  
			           	<a id="back" class="mini-button" iconCls=""  onclick="onBack">返回</a>  
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	</div>
	
	<!--导出Excel相关HTML-->
   	<iframe id="exportIFrame" style="display:none;">
   	</iframe>
    <form id="excelForm"  action="<%=path%>/public/exportExcel.action?1=1" method="post" target="excelIFrame">
        <input type="hidden" name="columns" id="columns" />
        <input type="hidden" name="excelData" id="excelData" />
    </form>
    <iframe id="excelIFrame" name="excelIFrame" style="display:none;"></iframe>
	    
    <form id="statisticForm"  action="<%=path%>/public/toStatisticalQuery.action?1=1" method="post" >
        <input type="hidden" name="columnsData" id="columnsData" />
        <input type="hidden" name="columnsjson" id="columnsjson" />
        <input type="hidden" name="queryWhereData" id="queryWhereData" />
    </form>
    <script type="text/javascript">
    	mini.parse();
    	var grid = new mini.get("datagrid1");
    	var columnsjson = eval(${parmMap.columnsjson});
    	var columnsData = eval(${parmMap.columnsData});
    	var queryWhereData = eval(${parmMap.queryWhereData});
    	var url="<%=path %>/public/ajaxListData.json?1=1";
    	grid.setUrl(url);
    	//grid.load();
    	grid.setAjaxOptions({type:'post'});
    	grid.load({columnsData: mini.encode(columnsData),queryWhereData: mini.encode(queryWhereData)});
    	
        //动态加载DataGrid列表
        grid.on("beforeload",function(e){
            var gridcolumns = eval(${parmMap.columnsjson});
        	grid.set({
                columns: gridcolumns
            });
		});
		
		//删除一行
		function onRemoveLine(e){
			 var row = grid.getSelected();
		    //
		    if (row) {
		    	var del = confirm("确实要删除吗?");
		    	if(del){
		        	grid && grid.removeRow(row,true);
		    	}
		    }
		}
		//另存
		function onSave(e){
			
		}
		//关闭
		function onCancel(e){
			if (window.CloseOwnerWindow) return window.CloseOwnerWindow("cancel");
		}
		//返回
		function onBack(e){
			document.getElementById("columnsData").value = mini.encode(columnsData);
	    	//where 条件参数
			document.getElementById("queryWhereData").value = mini.encode(queryWhereData);
			document.getElementById("columnsjson").value = mini.encode(columnsjson);
            var statisticForm = document.getElementById("statisticForm");
            statisticForm.submit();
		}

		//导出Excel
		function onExportExcel() {
            var columns = grid.getBottomColumns();
            
            function getColumns(columns) {
                columns = columns.clone();
                for (var i = columns.length - 1; i >= 0; i--) {
                    var column = columns[i];
                    if (!column.field) {
                        columns.removeAt(i);
                    } else {
                        var c = { header: column.header, field: column.field };
                        columns[i] = c;
                    }
                }
                return columns;
            }
            
            var columns = getColumns(columns);
            var json = mini.encode(columns);
            var exceljson = mini.encode(grid.getData());
            document.getElementById("columns").value = json;
            document.getElementById("excelData").value = exceljson;
            var excelForm = document.getElementById("excelForm");
            excelForm.submit(); 
        }
		
		var wb = document.getElementById("WebBrowser");
		//页面设置
		function PageSetup(){
			alert("暂未开放!");
			//wb.ExecWB(8,1);
		}
		//打印预览
		function onPrintPreview(){
			alert("暂未开放!");
			//wb.ExecWB(7,1);
		}
		//正反打印
		function onZfprint(e){
			alert("暂未开放!");
		}
		//打印
		function onPrint(e){
			alert("暂未开放!");
			/*if(confirm('确定打印吗？')) {
		　　		wb.ExecWB(6,6);
		　　}*/
		}
    </script>
  </body>
</html>
