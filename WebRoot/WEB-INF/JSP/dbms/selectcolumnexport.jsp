<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String tablename = request.getParameter("tablename");
	String databaseid = request.getParameter("databaseid");
%>

<html>
	<head>
		<title>导出选择列</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	   <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
	   
	   <style type="text/css">
		    html, body
		    {
		        font-size:12px;
		        padding:0;
		        margin:0;
		        border:0;
		        height:100%;
		        overflow:auto;
		    }
	    </style>
	</head>
	<body >
	
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
		 <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
	
    <div class="mini-fit">
    	<input name="tablename" value="<%=tablename %>" class="mini-hidden"/>
    	<input name="databaseid" value="<%=databaseid %>" class="mini-hidden"/>
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listfromtablecolumns.json"
	    	pageSize="2000"
	    	allowResize="false" idField="dcdfromcolumns" multiSelect="true" allowUnselect="true"
	    	showPager="false" showLoading="true" enableHotTrack="false" allowRowSelect="true"
	    	allowCellEdit="true" allowCellSelect="true"
	    	showFilterRow="false" onlyCheckSelection="false"
	    	>
	        <div property="columns">
	        		<div type="checkcolumn" width="10"></div>
               		<div field="dcdfromcolumns" name="dcdfromcolumns"
               		 	width="55" headerAlign="center" align="left">
               			列名
               		</div>
               		<div field="dcdfromcolumnsscribe" name="dcdfromcolumnsscribe"
               			 width="40" headerAlign="center" align="left"  >
               			列描述
               		</div>
               		<div field="dcdfromcloumnstype" name="dcdfromcloumnstype" 
               			 width="30" headerAlign="center" align="center"  >
               			列类型
               		</div>
               		<div field="dcdfromcloumnssize" name="dcdfromcloumnssize" 
               			 width="15" headerAlign="center" align="center"  >
               			字段长度
               		</div>
               		<div field="nullable" name="nullable" width="15"
               		 	headerAlign="center" align="left" >
               			可为NULL
               		</div>
	        </div>
	    </div>
    </div>
	
    <script type="text/javascript">
    	//
        mini.parse();
    	//
    	var tablename = "<%=tablename%>";
    	var databaseid = "<%=databaseid%>";
        var grid = mini.get("datagrid1");
        grid.load({
        	tablename : tablename
        	, databaseid : databaseid
        });
        ////////////////////////
	    function GetData() {
        	// 
		    var rows = grid.getSelecteds();
        	//
	        return rows;
	    };
	    function SetData(data) {
	        if (data.action == "edit") {
	            //跨页面传递的数据对象，克隆后才可以安全使用
	            data = mini.clone(data);
	            mini.get("id").value=data.id;
	            var url = "ajax/getResourceTreeByRole.json?1=1&qroleids="+data.id;
	            tree.setUrl(url);
	            //tree.load();
	        }else{
	        	 document.getElementById("operatetype").value="new";//走新增的action
	        }
	    };
        ////////////////////////
        // 渲染方案类型
        
	    //////////////////////////////////
	    // 关闭窗口
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow){
	        	return window.CloseOwnerWindow(action);
	        } else {
	        	window.close();
	        }
	    };
	    // 确定
	    function onOk() {
	        CloseWindow("ok");
	    };
	    // 取消
	    function onCancel() {
	        CloseWindow("cancel");
	    };
	</script>
	</body>
</html>