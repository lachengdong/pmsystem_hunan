<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>系统代码</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"			rel="stylesheet" type="text/css" />
		<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		</style>
	</head>
	<body>
		<!-- 弹出框 -->
	    <div id="editform" class="form">
	        <input  class="mini-hidden" name="islocalnew" id="islocalnew"/>
	        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
		            		<a class="mini-button" onclick="saveData()" plain="true" style="width:60px">保存</a>    
		           			<a class="mini-button" onclick="onCancel()"  plain="true" style="width:60px;">取消</a>       
		           		</td>
	                </tr>
	            </table>
	        </div>
	        
	        <div style="padding-left:11px;padding-bottom:5px;" >
		        <table  style="table-layout:fixed;">
		            <tr>
		                <td >代码类型ID：</td>
		                <td ><input name="codeid" class="mini-textbox"  size="60"
		                	 vtype="maxLength:20" required="true" value="${codeid}"/>
		                </td>
		               <td width="30px">&nbsp;</td>
		                <td >代码名称：</td>
		                <td ><input name="name" class="mini-textbox"  size="60" value="${name}"
		                	 vtype="maxLength:20" required="true" />
		                </td>
		            </tr>
		            <tr>
		            	<td >查询代码：</td>
		                <td ><input name="scearch" class="mini-textbox"  size="60" value="${scearch}"
		                	 vtype="maxLength:30" required="false" />
		                </td>
		                <td width="30px">&nbsp;</td>
		                <td >代码说明：</td>
		                <td ><input name="remark" class="mini-textbox"  size="60" value="${remark}"/>
		                </td>
		            </tr>
		            
		            <tr>
		            	<td >显示排序：</td>
		                <td ><input name="sn" class="mini-spinner"  minValue="1" value="${sn}" maxValue="999999999" required="false" />
		                </td>
		                <td width="30px">&nbsp;</td>
		                <td></td>
		                <td></td>
		            </tr>
		        </table>
	        </div>
	    </div>
	    
    <script type="text/javascript">
    	// 解析并初始化
        mini.parse();
    	
		// 保存数据
		function saveData(){
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			data.codetype = data.codeid;
			//
			var  url = "<%=path%>/syscode/ajax/savecode.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		
	               } else {
	               		alert(info);
	               }
	               CloseWindow();
			};
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("保存失败");
			        CloseWindow("cancel");
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
		
		function onCancel(e) {
            CloseWindow("cancel");
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
	    
	    function SetData(data){
	    	data = mini.clone(data);
	    	if(data && data.optype){
	    		if(data.optype =='new'){
	    			mini.get("islocalnew").setValue(1);
	    		}else{
	    			mini.get("islocalnew").setValue(0);
	    		}
	    	}
	    }
    </script>
	</body>
</html>