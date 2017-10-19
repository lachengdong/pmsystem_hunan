<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePathLessSlash = request.getScheme()+"://"+request.getServerName()
						+":"+request.getServerPort()+path ; // 少1个斜线
	String basePath = basePathLessSlash + "/";
%>
<html>
	<head>
		<title>文件导入</title>
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
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-download"
							 onclick="batchImport()"  plain="true">批量导入</a>
			            &nbsp;&nbsp;
					</td>
				</tr>
			</table>
		 </div>
    </div>
			
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/getUnZipListInfoData.json" 
	    	idField="ddcid" sortField="createdate" sortOrder="desc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="25"></div>
	        		<div field="fileid" name="fileidColumn" width="80" headerAlign="center"  allowSort="true">
               			文件ID
               		</div>
	        		<div field="filerealname" name="filerealname" width="80" headerAlign="center" align="left"  allowSort="true">
               			文件名称
               		</div>
               		<div field="createdate1" name="createdate1" renderer="onDateRenderer"
               			 width="40" headerAlign="center" align="center"  allowSort="true">
               			创建日期
               		</div>
               		<div field="text7" name="text7" width="40" headerAlign="center" align="center"  allowSort="true">
               			状态
               		</div>
			        <div width="40" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.hideColumn("fileidColumn");  
        grid.load();
        
        ////////////////////////
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        };
        // 渲染操作列
        function onActionRenderer(e) {
            var s = '<a class="" href="javascript:batchImport()" >导入</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                s += '&nbsp;&nbsp;<a class="" href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
		// 批量解压
		function batchImport(){
		    var rows = grid.getSelecteds();
		    if (!rows) {
                alert( "请至少选中一条记录！");
                return false;
		    }
	    	var del = confirm("确实要导入吗?");
	    	if(!del){
		    	return false;
	    	}
            var ids = [];
            for (var i = 0, l = rows.length; i < l; i++) {
                var r = rows[i];
                ids.push(r["fileid"]);
            }
	    	var fileid = ids.join(',');
	    	var data = {
	    		fileid : fileid
	    	};
			// 界面
			//grid.loading("正在导入文件，请稍候...");
			progressDesc();
			var  url = "<%=path%>/dbms/batchImport.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
	               		alert(info);
	               		grid.reload();
	               		//grid.unmask();
	               } else {
	               		alert(info);
	               		//grid.unmask();
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("网络错误");
	               	//grid.unmask();
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		};				
		
		// 删除
		function deleteRow(){
		    var row = grid.getSelected();
		    //
		    if (!row) {
		    	return false;
		    }
	    	var del = confirm("确实要删除吗?");
	    	if(!del){
		    	return false;
	    	}
	    	//
	    	var fileid = row["fileid"];
	    	var filepath = row.filepath || "";
	    	var data = {fileid : fileid, filepath : filepath};
	    	
	    	//
			// 界面
			grid.loading("正在删除，请稍候...");
			var  url = "<%=path%>/dbms/ajax/deleteDataExport.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
			        	grid.reload();
	               } else {
	               		alert(info);
	               		grid.unmask();
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("网络错误");
	               	grid.unmask();
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		};

		
		// 请求AJAX,工具方法
		function requestAjax(url, data, successCallback, errotCallback){
			// 执行AJAX请求
			$.ajax({
			    url: url,
			    data: data,
		        type: "post",
			    success: function (message) {
			    	message = mini.decode(message) || {};
			   		if(successCallback){
			    	   successCallback.call(window, message);
			   		}
	            	return false;
			    },
			    error: function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			       if(errotCallback){
			    	   errotCallback.apply(window, arguments);
			       } else {
			    	   alert("操作失败!");
			       };
			    }
			});
		};
		// 刷新本页面
		function refreshPage(){
			//
			if(!window["____refreshPage"]){
				window["____refreshPage"] = true;
				//
				location.reload();
			} else {
				window.setTimeout(function(){
					window["____refreshPage"] = false;
					},1*1000);
			}
		};
		
		var path = '<%=path%>';
	</script>
	<script src="<%=path%>/scripts/progressbar.js" type="text/javascript"></script>
	
	</body>
</html>