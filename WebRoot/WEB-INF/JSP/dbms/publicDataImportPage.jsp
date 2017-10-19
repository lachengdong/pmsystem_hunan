<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>数据导入</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/miniui/swfupload.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />

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
				<table style="width: 98%;">
					<tr>
	
						<td align="center"> 选择要上传的文件: 
							<input id="uploadfile" class="mini-fileupload" name="uploadfile"  limitType="*.*"  flashUrl="<%=path%>/scripts/miniui/swfupload.swf"
							 uploadUrl="<%=path%>/dataImport.json?1=1"  onuploadsuccess="onUploadSuccess" />
							<a class="mini-button" id="uploadbtn" iconCls="icon-upload"plain="true" onclick="dataImport();">导入</a>
						</td>
						
						<!-- <td align="right">
							&nbsp;&nbsp;
							<a class="mini-button" iconCls="icon-downgrade"
							 		onclick="getPackageAndMergeZip();" plain="true">获取前置数据</a>
						</td> -->
						
						<td align="right">
							&nbsp;&nbsp;
							<a class="mini-button" iconCls="icon-reload"
								onclick="refreshPage()" plain="true">刷新</a>
						</td>
					</tr>
				</table>
		 	</div>
		 
		</div>
		<div id="datagrid1" align="left" style="padding-left: 100px;">
			<br />
			<div>
				操作步骤:
			</div>
			<div>
				1. 选择要上传的文件
			</div>
			<div>
				2. 确定点击上传按钮
			</div>
			<div>
				3. 等待导入执行结果
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

/**
 * 数据导入按钮
 * YangZR		2015-06-02
 */
function dataImport(){
	progressDesc();
	var fileupload = mini.get("uploadfile");
    fileupload.startUpload();
}

function onUploadSuccess(e) {
	var data = mini.decode(e.serverData);
    if(data.status==1){
    	this.setText("");
    }
    alert(data.info);
}


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
  
  
  
  //获取前置机数据并组包
  function getPackageAndMergeZip(){
	progressDesc();
  	var  url = "<%=path%>/dbms/ajax/getPackageAndMergeZip.json";
  	var successCallback = function (message){
      	   var info = message["info"];
      	 	alert(info);
            return false;
  	    };
  	//
  	var errotCallback = function (jqXHR, textStatus, errorThrown) {
  	    	// 把错误吃了
  	        alert("网络错误");
            //grid.unmask();
  	    };
  	//
  	requestAjax(url,null,successCallback,errotCallback);
  };
  
  
  function progressDesc(){
		var func = function myloading(){
			var percent = "";
			var myform = new mini.Form("datagrid1");
	  		$.ajax({
	      		url: "<%=path%>/commutationParole/getPercent.json?1=1",
	      		dataType:"text",
	      		async:false,
	          	success: function (text){
					percent = text;
	          	},
	          	error: function(){
	          		myform.unmask();
	          		return;
	          	}
	      	});
	  		//
	  		myform.loading(percent);
			if(percent=="100%"){
				clearInterval(progressDesc);
				var timefunc = function() {progressunmask();};
				var timer_timeout = setTimeout(timefunc, 500);
				
	      		$.ajax({
	          		url: "<%=path%>/commutationParole/getPercent.json?1=1&type=remove",
	          		dataType:"json",
	          		async:false,
	              	success: function (text) {
	              	},
	              	error: function () {
	              	}
	          	}); 
			}
	  	}
		var progressDesc = window.setInterval(func,1000);
	}

	function progressunmask(){
		var myform = new mini.Form("datagrid1");
		myform.unmask();
	}

</script>
</body>
</html>
