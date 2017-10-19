<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯互监组长选择页面</title>
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
	<div id="form1" class="mini-toolbar"  style="padding:2px;border:0;" align="right">
		<table >
             <tr>
                <td style="width:100%;" align="left">
                	<a class="mini-button" iconCls="icon-ok"  plain="true" onclick="add()" >确定</a>
					<a class="mini-button" iconCls="icon-no"  plain="true" onclick="clear()" >清空</a>
					<a class="mini-button" iconCls="icon-undo" plain="true" onclick="cancel()">返回</a>	
                </td>
                <td style="white-space:nowrap;">
                	<input class="mini-combobox" style="width:80px;" url="getCriminalPrisonData.json" id="prison" name="prison" emptyText="监舍"
                		nullItemText="--全部--"  valueField="prison" textField="prison" value="${prison}" showNullItem="true" allowInput="false" onValueChanged="search();"/>
                	<input id="orgid1" class="mini-combobox"   valueField="ORGID" textField="NAME"   emptyText="监区" showNullItem="true"
                          nullItemText="--全部--" url="getDepartList.action?1=1" style="width:70px;" popupWidth="100" onvaluechanged="search();"/>
                     <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名" style="width:70px;" onenter="search();"/>   
                	<a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
                </td>
             </tr>
        </table>
	</div>  
	<div class="mini-fit" >
	    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
	        url="<%=path %>/getHujianCrimeDataList.action?1=1&svtype=${svtype}&filterCrimids=${filterCrimids}" idField=""  
	        multiSelect="false" multiSelect="true"  allowAlternating="true"  virtualScroll="true">
	        <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="crimid" width="100" headerAlign="center" align="center">罪犯编号</div>   
				<div field="name" width="100" headerAlign="center" align="center" >罪犯姓名</div>   
				<div field="nationstr" width="100" headerAlign="center" align="center" >民族</div> 
				<div field="xingzhong" width="100" headerAlign="center" align="center">刑种</div>
				<div field="inprisondate" width="100" headerAlign="center" align="center" >入监时间</div>
	            <div field="jianqu" width="120" headerAlign="center" align="center">监区</div>
	        </div>
	    </div>
  	</div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    
    function search(){
    	var prison = mini.get("prison").getValue();
    	var key = mini.get("key").getValue();
    	var selectOrgid = mini.get("orgid1").getValue(); 
    	grid.sortBy("crimid","desc");
    	var data = { key: key, prison:prison, selectOrgid:selectOrgid};
    	grid.load(data);
    }
    
    function clear(){
    	CloseWindow("clear");
    }	
	//新增
	function add(){
		var row = grid.getSelected();
		if(row){
			CloseWindow("ok");
		}else{
			alert("请选中一条记录！");
		}
	} 
	
	function GetData(){
		var row = grid.getSelected();
		var crimid = row.crimid;
		var name = row.name;
		var data = {crimid:crimid,name:name};
		return data;
	}
   function cancel() {	
   		CloseWindow("cancel");
   } 
   function CloseWindow(action) {            
         if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
         else window.close();            
     }     	
</script>
</body>
</html>