<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Excel上传页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/form/formPopUpBox.js" type="text/javascript"></script>

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
	    .progressbar
	    {
	        position:relative;background:#bbb;width:100%;height:16px;overflow:hidden;
	    }
	    .progressbar-percent
	    {
	        position:absolute;height:18px;background:blue;left:0;top:0px;overflow:hidden;
	        z-index:1;
	    }
	    .progressbar-label
	    {
	        position:absolute;left:0;top:0;width:100%;font-size:13px;color:White;
	        z-index:10;
	        text-align:center;
	        height:16px;line-height:16px;
	    }
    </style>
</head>
<body>

	<br /><br /><br />
	<div align="center">
	    <form id="form" name="form" action="<%=path%>/excel/uploadexcel.action?1=1" enctype="multipart/form-data" method="post" target="hidden_frame">
		    <table>
			    <tr>
				    <td align="left">选择文件:
				       <input type="button" value="上传" onclick="uploadfile();" id="btnupload" style="display: none" />
				       <input type="file" name="file" id="imagefile" onchange="javascript:onfileChange();" style="width:300px;"/>
					   <iframe name='hidden_frame' id='hidden_frame' style="display: none"></iframe>
					   <input type="hidden" id="excelfilepath" name="excelfilepath"/>
					</td>
			    </tr>
			    <tr>
			    	<td align="left">导入方案:
				    	<input id="type" class="mini-combobox" url="ajaxGetExcelType.action?1=1" 
							width="228" textField="remark" valueField="id" allowInput="false" />
					    <a class="mini-button" onclick="upload();" plain="false" iconCls="icon-upload" width="66">上传</a>
			    	</td>
			    </tr>
			</table> 
			   <h3>注意</h3>
			   <table>
			   	<tr><td align="left">1、目前支持以下文件格式上传：Excel格式</td></tr>
			   	<tr><td align="left">2、刑期格式：10-11-11或者10-00-00</td></tr>
			   	<tr><td align="left">3、日期录入格式：2014.11.11</td></tr>
			   	<tr><td align="left">4、日期起止格式：2015.12.12-2015.09.08</td></tr>
			   </table>
	    </form>
	</div>  
	<script type="text/javascript">
		var path = '<%=path%>';
		//图片上传
		   
	    function onfileChange() {	    	
			document.getElementById("btnupload").click();
	    }
	   	//上传文件到服务器
		function uploadfile() {
			var filepath = document.getElementById("imagefile").value;		
			var extStart = filepath.lastIndexOf(".");
			var ext = filepath.substring(extStart, filepath.length).toUpperCase();
			if (ext != ".XLS") {
				mini.alert("不是excel格式文件,请重新选择文件！");
				return;
			}
			document.getElementById("form").submit();
			document.cookie = "";
		}
		//接收上传的excel文件在服务器端的路径
		function callback(showPath,fileName) {
			document.getElementById("excelfilepath").value=showPath+fileName;
		}
	    function upload(){
	    	var id = mini.get("type").getValue();
	    	var filepath = document.getElementById('excelfilepath').value;
			if(filepath){
				//判断选择文件的后缀
				var i = filepath.lastIndexOf(".");
				var type = filepath.substring(i+1);
				var filename = filepath.substring(filepath.lastIndexOf("/")+1,i);
				if(type == "xls"){
					if(id){
						// 将 queryuuid 作为key,查询进度
						var queryuuid =  getqueryUUID();
						window.queryuuid = queryuuid;
						
						var data = {id:id,queryuuid:queryuuid,filepath:filepath,filename:filename};

						var  url = path+"/excel/ajax/addExcelImport.json";
						var successCallback = function (message) {
				        	   var info = message["info"];
				               if(1 === message["status"]){
				            	   startQueryProgress(queryuuid);
				               } else {
				            	   alert(info);
				               }
				               return false;
						};
						var errotCallback = function (jqXHR, textStatus, errorThrown) {
					        alert("保存失败");// 把错误吃了
					    };
						requestAjax(url,data,successCallback,errotCallback);
					}else{
						alert("请选择上传方案！");
					}
				}else{
					alert("选择的文件不符合上传规则！");
				}
			}else{
				alert("请选择文件!");
			}
	    }

	    function onCancel(){
	        CloseWindow("cancel");
	    }

	    function CloseWindow(action){
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	    
	    function getqueryUUID(){
			var queryuuid =  new Date().getTime().toString(16) + ""+Math.random().toString(16);
			return queryuuid;
		};
		// 开始查询进度
	    function startQueryProgress(queryuuid){
			// 显示遮罩
	        refreshProgress('?', "0", "正在执行...", 0);
		    var data =  {queryuuid : queryuuid};
			var  url = path+"/dbms/ajax/queryprogress.json";
			var successCallback = function (message) {
	        	   //var info = message["info"];
	        	   //alert(info);
	               if(1 === message["status"]){
	            	   var meta = message["meta"] || {};
	            	   var taskbean = meta["taskbean"] || {};
	            	   var status = taskbean["status"];
            		   var counter = taskbean["counter"];
            		   var sum = taskbean["sum"];
	            	   var continueAjax = true;
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
	            		   clearProgress(queryuuid);// 发请求清理进度缓存...
	            	   }
	               } else {
	               }
	               return false;
			};
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    alert("执行失败");// 把错误吃了
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
	    		//var tip = (info+ "; 进度： "+ counter + "/总计： "+sum);
	    		var progresscount = 0;
	    		if(counter>0){
	    			progresscount = parseInt((parseInt(counter)/parseInt(sum))*100);
	    		}
	    		
	    		var tip ='<div class="progressbar">'+'<div class="progressbar-percent" style="width:'+ progresscount
	    		+ '%;"></div>'+ '<div class="progressbar-label">' + progresscount + '%</div>'+'</div>';
	    		tip += (info+ "; 进度： "+ counter + "/总计： "+sum);
	    		mini.mask( {
					el : document.body,
					cls : 'mini-mask-loading',
					html : tip
				});
	    	}
	    	//
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
		function clearProgress(queryuuid){
	    	// 发请求清理进度缓存...
	    	// 暂时未实现
	    	window["console"]&&console.info("queryuuid="+queryuuid+";暂未处理清理进度缓存.");
	    };

   </script>
    
</body>
</html>
