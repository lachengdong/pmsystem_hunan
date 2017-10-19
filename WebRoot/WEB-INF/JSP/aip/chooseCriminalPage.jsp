<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>选择罪犯弹出框</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
    html, body{
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden; 
    }
    table tr{    
    	height:25px;    
    	line-height:25px;
    }
    </style>
</head>
<body>
    <div class="mini-toolbar" style="border:0px;padding:0px;">
         <table style="width:100%;">
             <tr>
             	<td style="width:100%;">
             		<a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a> 
			        <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>
			        <a class="mini-button" onclick="onClear" plain="true" style="width:60px">清除</a>
             	</td>
               	<td style="white-space:nowrap;">
                 	 <input class="mini-textbox"  emptyText="编号、姓名、拼音首字母"  style="width:125px;" id="key" onenter="onKeyEnter"/>    
                     <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
                 </td>
             </tr>
         </table>           
     </div>
   	<div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="<%=path%>/getPublicListData.json?1=1&solutionid=701577"  idField="" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="10"  showLoading="true">
	        <div property="columns">
	            <div type="checkcolumn" ></div> 
	            <div field="crimid" width="60" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>    
	            <div field="name" width="60" headerAlign="center"  align="center" allowSort="true">姓名</div>   
	            <div field="causeaction" width="60" headerAlign="center"  align="center" allowSort="true">罪名</div>
	            <div field="yuanpang" width="60" headerAlign="center"  align="center" allowSort="true">原判刑期</div>
	            <!--  
	            <div field="yuanpanxingqiqizhi" width="60" headerAlign="center"  align="center" allowSort="true">刑期起止</div>
	            -->
	            <div field="yuxing" width="60" headerAlign="center"  align="center" allowSort="true">余刑</div>
	            <div field="" width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
         	var s = ' <a class=\"Edit_Button\" href=\"javascript:onOk();\" >选择</a>';
         	return s;
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(){
        	var row =grid.getSelected();
        	if(row){
        		var data = new Array([6]);
        		data[0] = 1;
                data[1] = row.crimid;
                data[2] = row.name;
                data[3] = row.causeaction;
                data[4] = row.yuanpang;
                //data[4] = row.yuanpanxingqiqizhi;
                data[5] = row.yuxing;
                window.returnValue = data;
                CloseWindow("cancel");
        	}else{
        		alert("请选择一条数据！");
        	}
        }
        
        function onClear(){
        	var data = new Array([1]);
    		data[0] = 0;
    		window.returnValue = data;
            CloseWindow("cancel");
        }
        
        function search(){
        	var data = {};
        	var value = mini.get("key").getValue();
        	data["key"] =encodeURI( value );
            grid.load(data);
        };
        
        function onKeyEnter(e) {
            search();
        };
        
        
    </script>
</body>
</html>
