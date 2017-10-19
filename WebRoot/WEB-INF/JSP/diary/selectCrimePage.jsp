<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯选择页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
					<a class="mini-button" iconCls="icon-undo" plain="true" onclick="cancel()">返回</a>
                </td>
                <td style="white-space:nowrap;">
                	<input class="mini-combobox"  id="jianqu"  name="jianqu"  style="width:80px;"   emptyText="监区" 
                		valueField="ORGID" textField="NAME" required="false" showNullItem="true" 
                		url="getDepartList.action?1=1&qtype=jianqu"  onvaluechanged="search();" />
                	<input class="mini-textbox"  id="key"  name="key"  style="width:80px;"   emptyText="编号、姓名"   onenter="search();" />
                	<a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
                </td>
             </tr>
        </table>
	</div>  
	<div class="mini-fit" >
	    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
	        url="<%=path %>//getPublicListData.json?1=1&solutionid=951175" idField=""  
	        multiSelect="false" multiSelect="true"  allowAlternating="true"  virtualScroll="true" onrowdblclick="add();">
	        <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="crimid" width="100" headerAlign="center" align="center">罪犯编号</div>  
				<div field="name" width="100" headerAlign="center" align="center" >罪犯姓名</div>
	            <div field="orgname" width="120" headerAlign="center" align="center">监区</div>
	        </div>
	    </div>
  	</div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    
    function search(){
    	var jianqu = mini.get("jianqu").getValue();
    	var key = mini.get("key").getValue();
    	var data ={jianqu:jianqu, key:key};
    	grid.load(data);
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
		var orgid = row.orgid;
		var orgname = row.orgname;
		var data = {crimid:crimid,name:name,orgid:orgid, orgname:orgname};
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