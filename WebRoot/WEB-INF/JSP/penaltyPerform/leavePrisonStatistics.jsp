<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>离监统计</title>
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
<input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
    <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
		<table >
 			<tr>
                <td style="width:100%;">
                	离监类别<input id="codeid" class="mini-combobox" valueField="codeid" textField="name" emptyText="选择在押状态" showNullItem="true" nullItemText="--全部--"
	                        onvaluechanged="codechanged" url="ajaxGetCode.json?1=1&codeType=GB032" style="width:150px;"/>
                </td>
                <td style="white-space:nowrap;">
            		<input class="mini-textbox" vtype="maxLength:50;" emptyText="罪犯编号" id="key" style="width:150px;" onenter="onKeyEnter"/>
					<a class="mini-button" iconCls="icon-search" plain="true" onclick="search" >查询</a>
           		</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" id="div_two" >
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"  url="getTemplateList.action?1=1&type=2" idField="id" pageSize="20" sizeList="[10,20,50,100]"   multiSelect="true" >	
	    	<div property="columns">
	    		<div type="checkcolumn"></div>
	    		<div field="tempid" headerAlign="center"  align="center" allowSort="true" width="200px">罪犯编号</div>
	    		<div field="tempname" headerAlign="center"  align="center" allowSort="true" width="200px;">姓名</div>
	    		<div field="functionname" headerAlign="center"  align="center" allowSort="true" width="200px">离监日期</div>
	    		<div field="tempid" headerAlign="center"  align="center" allowSort="true" width="260px;">离监类别</div>
			</div>        
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
		var grid = mini.get("datagrid1");
		grid.sortBy("functionname","desc");
		grid.load();
	    function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
			grid.load({ key: key });
        }
        //选择在押状态下拉列表时触发
		function codechanged(){
		
		} 
    </script>
 </body>
</html>