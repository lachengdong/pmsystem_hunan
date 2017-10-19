<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>数据导入</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=path%>/scripts/jquery-1.6.2.min.js">
		</script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />

		<style type="text/css">
html,body {
	z-index: 0;
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: auto;
}

#center {
	margin: 50px auto;
	width: 400px;
}

.divbody {
	z-index: 2;
	left: 2%;
	visibility: hidden;
	width: 98%;
	cursor: crosshair;
	position: absolute;
	top: 100px;
	height: 80%;
	background-color: #ffffcc;
}

p {
	color: #cc6633;
	font-weight: bold;
}

.divprogress {
	BORDER-RIGHT: black 1px solid;
	PADDING-RIGHT: 3px;
	BORDER-TOP: black 1px solid;
	PADDING-LEFT: 3px;
	FONT-SIZE: 10pt;
	PADDING-BOTTOM: 2px;
	BORDER-LEFT: black 1px solid;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: black 1px solid
}
.hide{
	display: none;
}
.hidden{
	visibility: hidden;
}
#uploadprogress{
	width: 90%;
	height: 200px;
	margin: 0;
	padding: 0;
	overflow: hidden;
}
#uploadprogress iframe{
	margin: 0;
	padding: 0;
}
</style>

	</head>
	<body style="">

		<div id="topbar" class="mini-toolbar" style="padding: 2px; border: 1;">
			<div class="crud-search">

				<form id="form1" action="<%=path%>/dbms/importdatafile.action"
					target="uploadprogress"
					method="post" enctype="multipart/form-data">
					<table style="width: 98%;">
						<tr>
							<td align="left">
								&nbsp;&nbsp;&nbsp;&nbsp; 选择数据导入方案：
								<input id="ddcid" name="ddcid" class="mini-combobox"
									style="width: 180px;" popupWidth="200" textField="ddcname"
									valueField="ddcid"
									url="<%=path%>/dbms/ajax/listdbscheme.json?ddctype=0" />
								&nbsp; &nbsp;
								<select name="insertonly"  id="insertonly" style="width:150px;">
									<option value="0" selected="selected">更新优先</option>
									<option value="1">尝试插入</option>
								</select>
								&nbsp;&nbsp; 选择要上传的文件: &nbsp;&nbsp;&nbsp;
								<input type="file" name="uploadfile" accept="*" id="uploadfile"
									label="选择文件" />
								<a class="mini-button" id="uploadbtn" iconCls="icon-upload"
									plain="true" onclick="addImportDataRequest()">导入</a>
								&nbsp;&nbsp;
							</td>
							<td align="right">
								&nbsp;&nbsp;
								<a class="mini-button" iconCls="icon-reload"
									onclick="refreshPage()" plain="true">刷新</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div align="left" style="padding-left: 100px;">
			<br />
			<div>
				操作步骤:
			</div>
			<div>
				1. 选择数据导入方案
			</div>
			<div>
				2. 选择要上传的文件
			</div>
			<div>
				3. 确定点击上传按钮
			</div>
			<div>
				4. 等待导入执行结果
			</div>

		</div>
		<div class="iframe_container hidden">
			<!-- 用于存放 -->
			<iframe id="uploadprogress" name="uploadprogress">
			
			</iframe>
		</div>
		<div id="framediv" class="divbody">
			<br />
			<p align="center">
				正在上传，请稍候...
			</p>
			<table align="center">
				<tr>
					<td>
						<div class="divprogress">
							<span id="progress1">&nbsp;&nbsp;</span>
							<span id="progress2">&nbsp;&nbsp;</span>
							<span id="progress3">&nbsp;&nbsp;</span>
							<span id="progress4">&nbsp;&nbsp;</span>
							<span id="progress5">&nbsp;&nbsp;</span>
							<span id="progress6">&nbsp;&nbsp;</span>
							<span id="progress7">&nbsp;&nbsp;</span>
							<span id="progress8">&nbsp;&nbsp;</span>
							<span id="progress9">&nbsp;&nbsp;</span>
							<span id="progress10">&nbsp;&nbsp;</span>
							<span id="progress11">&nbsp;&nbsp;</span>
							<span id="progress12">&nbsp;&nbsp;</span>
							<span id="progress13">&nbsp;&nbsp;</span>
							<span id="progress14">&nbsp;&nbsp;</span>
							<span id="progress15">&nbsp;&nbsp;</span>
							<span id="progress16">&nbsp;&nbsp;</span>
						</div>
					</td>
				</tr>
			</table>
		</div>

		<script type="text/javascript">
//
mini.parse();

// 刷新本页面
function refreshPage() {
	//
	if (!window["____refreshPage"]) {
		window["____refreshPage"] = true;
		//
		location.reload();
	} else {
		window.setTimeout(function() {
			window["____refreshPage"] = false;
		}, 1 * 1000);
	}
};
//
var hasLoadFrom = false;
function cancelAdd() {
	var cfm = true;
	if (hasLoadFrom) {
		cfm = confirm("确定取消吗?");
	}
	if (cfm) {
		goBack();
	}
	return false;
};
// 返回
function goBack() {
	window.history.back();
};
</script>
<script type="text/javascript">
var progressEnd = 16;
var progressColor = "blue";
var clearColor = "white";
var progressInterval = 350;
var progressBegin = 0;
var progressTimer;
function progress_clear() {
	clearTimeout(progressTimer);
	// document.getElementById("framediv").style.visibility="hidden";
};
function progress_update() {
	progressBegin++;
	if (progressBegin > progressEnd) {
		progress_clear();
		progressBegin = 0;
		for ( var i = 1; i <= 16; i++)
			document.getElementById("progress" + i).style.backgroundColor = "#ffffcc";

	} else
		document.getElementById("progress" + progressBegin).style.backgroundColor = progressColor;
	progressTimer = setTimeout("progress_update()", progressInterval);
};

function loading() {
	$(".iframe_container").removeClass("hidden");
	//
	mini.mask( {
		el : document.body,
		cls : 'mini-mask-loading',
		html : '文件上传中，请稍等...'
	});
	//mini.get("topbar").mask("");
	//
	setTimeout(function () {
            mini.unmask(document.body);
        }, 2000);
};

//
function getInsertOnly(defaultVal){
	var dom_insertonly = document.getElementById("insertonly");
	if(dom_insertonly){
		var insertonly = dom_insertonly.value;
	}
	return insertonly || defaultVal || "0";
};

function addImportDataRequest() {

	var path = document.getElementById("uploadfile").value;
	var value = document.getElementById("ddcid").value;
	var insertonly = getInsertOnly("0");
	
	// 将 queryuuid 作为key,查询进度
	var queryuuid =  getqueryUUID();
	window.queryuuid = queryuuid;
	if (value == -1) {
		alert("请选择导入方案!");
		return;
	} else {
		if (path != '') {
			document.getElementById("form1").submit();
	        startQueryProgress(queryuuid);
		} else {
			alert("请选择要上传的文件！");
			return;
		}
	}
			
};
function getqueryUUID(){
	var queryuuid =  new Date().getTime().toString(16) + ""+Math.random().toString(16);
	//
	return queryuuid;
};
// 请求AJAX,工具方法
function requestAjax(url, data, successCallback, errotCallback){
	// 执行AJAX请求
	$.ajax({
	    url: url,
	    data: data,
        type: "post",
	    success: function (message) {
	    	/*
	    	if(window["JSON"]){
	    		message = mini.decode(message) || {};
	    	} else { // IE6, IE7
       	   		message = eval("("+ message + ")") || {};
	    	}
	    	*/
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

	//开始查询进度条
function startQueryProgress(queryuuid){
	// 显示遮罩
       refreshProgress('?', "0", "正在执行...", 0);
	//
    var data =  {
    	queryuuid : queryuuid
    };
	//
	var  url = "<%=path %>/dbms/ajax/queryprogress.json";
	var successCallback = function (message) {
	       //
       	   //var info = message["info"];
       	   //alert(info);
              if(1 === message["status"]){
           	   //
           	   var meta = message["meta"] || {};
           	   //
           	   var taskbean = meta["taskbean"] || {};
           	   //
           	   var status = taskbean["status"];
          		   var counter = taskbean["counter"];
          		   var sum = taskbean["sum"];
          		   
           	   var continueAjax = true;
           	   //
           	   var info = "正在执行";
           	   if(3 == status){
           		   info = ("执行成功");
           		   continueAjax = false;
           	   } else if(4 == status){
           		   info = ("执行失败");
           		   continueAjax = false;
           	   } else if(5 == status){
           		   info = ("正在分发数据..");
           	   } else {
           		   info = ("正在执行");
           	   }
          		   // 更新进度
          		   refreshProgress(sum, counter, info, status);
           	   if(continueAjax){
            	   // 继续查询进度
            	   window.setTimeout(function(){
            	   		startQueryProgress(queryuuid);
            	   }, 3*1000);
           	   } else {
           		   // 发请求清理进度缓存...
           		   clearProgress(queryuuid);
           	   }
              } else {
              }
              return false;
	};
	//
	var errotCallback = function (jqXHR, textStatus, errorThrown) {
	    	// 把错误吃了
	        alert("执行失败");
	    };
	//
	requestAjax(url,data,successCallback,errotCallback);
 };
 
function refreshProgress(sum, counter, info, status){
   	// 刷新进度.
   	if(!sum){
   		sum = "?";
   	}
   	if(!counter){
   		counter = "0";
   	}
   	if(!info){
   		info = "正在执行!";
   	}
   	if(3 == status || 4 == status){
   		// 执行完成,成功/失败
   		alert(info);
           mini.unmask(document.body);
   	} else {
   		var tip = (info+ "; 进度： "+ counter + "/总计： "+sum);
		mini.mask( {
			el : document.body,
			cls : 'mini-mask-loading',
			html : tip
		});
   	}
   	//
  };
function download() {
	window.location.href = "queryTableInfoList.action?1=1&type=import";
};
</script>
	</body>
</html>
