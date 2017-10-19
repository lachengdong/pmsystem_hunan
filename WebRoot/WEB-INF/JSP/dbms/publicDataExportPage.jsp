<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePathLessSlash = request.getScheme()+"://"+request.getServerName() + ":"+request.getServerPort()+path ; // 少1个斜线
	String basePath = basePathLessSlash + "/";
%>
<html>
	<head>
		<title>数据导出</title>
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
		        overflow:hidden;
		    }
		    .hide{
		    	visibility: hidden;
		    }
	    </style>
	</head>
	<body >
	    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
			<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	        <table >
	             <tr>
	                <td style="width:100%;">
	                	<input name="chkpackageper" id="chkpackageper" class="mini-checkbox" text="按罪犯分包导出" value="0" trueValue="1" falseValue="0" />
	                	<a class="mini-button" iconCls="icon-ok" onclick="dataExport()" plain="true">导出</a>
	                </td>
	                <td style="white-space:nowrap;"> 
	                	<!-- 操作说明 -->
		                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
	                 </td>
	             </tr>
	        </table>
	    </div>
	    
	    <div class="mini-fit" id="div_two">
	         <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
	        	 url="<%=path%>/dbms/ajax/listdataexport.json" idField="spid" multiSelect="true" allowAlternating="true" 
	        	 virtualScroll="false"  showLoading="true">
	           <div property="columns">
	              <div type="checkcolumn" width="20"></div>
	              <div type="indexcolumn" width="20" headerAlign="center" align="center" >序号</div>
	              <div field="ddcname" width="120" headerAlign="center"  align="center" allowSort="true">数据方案</div>
	         	  <div field="filerealname" width="120" headerAlign="center"  align="center" allowSort="true">文件名称</div>
	         	  <div field="filepath" width="120" headerAlign="center"  align="center" allowSort="true">文件路径</div>
	         	  <div field="createdate" width="120" headerAlign="center"  align="center" renderer="onDateRenderer" allowSort="true">创建日期</div>
	         	  <div width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>      	  
	          </div><!-- end of columns -->
	      </div> <!-- end of mini-datagrid -->
	 </div> <!-- end of div_two -->
	 
	 
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
     	// 渲染日期
      function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
        	}
            return "";
        };
        
     	// 渲染操作列
      function onActionRenderer(e) {
            var s = '<a class="" href="javascript:downFile()" >下载</a>&nbsp;&nbsp;';
                s += '<a class="" href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
        
     	//下载文件
      function downFile(){
		    var row = grid.getSelected();
		    //
		   if (!row) {
		    	alert("请选择文件!");
		    	return;
		    }
	    	// 拼接文件路径
	    	var filepath = row.filepath || "";
	    	//
	    	if(!filepath){
		    	alert("文件不存在!");
	    		return;
	    	}
	    	//
	    	var startSlash = filepath.indexOf("/");
	    	var allPath = "";
	    	var basePathLessSlash = "<%=basePathLessSlash%>";
	    	// 不是斜线开头
	    	if(0 != startSlash){
	    		basePathLessSlash += "/";
	    	}
	    	// 下载的文件路径
	    	allPath = basePathLessSlash + filepath;
	    	//
	    	iframeDownloadFile(allPath);
        };
        
        // 通过 Iframe下载文件
      function iframeDownloadFile(allPath){
        	//
			var download_iframe = document.getElementById('download_iframe');
			if(!download_iframe){
				//
				var d = document.createElement("div");
				d.style.display="none";
				//
				var i = document.createElement("iframe");
				i.id="download_iframe";
				//
				d.appendChild(i);
				//
				document.documentElement.appendChild(d);
				download_iframe = document.getElementById('download_iframe');;
			}
			//
			window.onerror = function(){
				alert("下载失败");
			};
			try {
				//delete download_iframe.src;
				download_iframe.src = allPath;
			} catch(ex){
				alert("下载失败!");
			}
			return false;
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
	    	var data = {
	    		fileid : fileid,
	    		delflg : 1
	    	};
	    	//
			// 界面
			grid.loading("正在删除，请稍候...");
			var  url = "<%=path%>/dbms/ajax/updatedataexport.json";
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
		        async:false,
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
		
		
		
		// 检查并获取导出方案ID
		function getAndCheckDdcid(showalert){
			var $search_ddcid = mini.get("search_ddcid");
	        // 变量会自动提前
			var search_ddcid = null;
		    if($search_ddcid){
	        	search_ddcid = $search_ddcid.getValue();
		    }
        	// 
        	if(!search_ddcid && showalert){
        		alert("请选择导出方案!");
        		$search_ddcid.focus();
        	}
        	return search_ddcid;
		};
		
		function getqueryUUID(){
			var queryuuid =  new Date().getTime().toString(16) + ""+Math.random().toString(16);
			//
			return queryuuid;
		};
		
		
		// 添加导出请求
		function dataExport(){
			//
			var search_ddcid = getAndCheckDdcid(1);
			if(!search_ddcid){
				return false;
			}
			// 将 queryuuid 作为key,查询进度
			var queryuuid =  getqueryUUID();
			window.queryuuid = queryuuid;
			//
		    //var depart = mini.get("depart").getValue();
		   var chkpackageper = mini.get("chkpackageper").getValue();
			//
			//var conditionmessage = getConditionmessage();
			var conditionmessage = getCaseConditionmessage();
			//
		   var data = {
		    	ddcid: search_ddcid, 
		    	queryuuid : queryuuid,
		    	conditionmessage : conditionmessage,
		    	chkpackageper : chkpackageper
		    };
			//
			var  url = "<%=path%>/dbms/ajax/adddataexport.json";
			var successCallback = function (message) {
			       //
	        	  var info = message["info"];
	               if(1 === message["status"]){
	            	   startQueryProgress(queryuuid);
	               } else {
	            	   alert(info);
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			        alert("保存失败");
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
			
		};
		
	</script>
	</body>
</html>	