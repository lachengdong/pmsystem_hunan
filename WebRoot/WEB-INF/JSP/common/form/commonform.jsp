<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	String path = request.getContextPath();
	String furl = request.getParameter("furl");
	if(null == furl){
		furl = "";
	}
	//
	String sysName = "刑罚执行网上协同工作平台";
	String formTitle = "办案表单页面";
	formTitle = formTitle + " - " + sysName;
	//
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><%=formTitle %></title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body  onload="init('${menuid}');">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
		<%--
	    	<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData();">存盘</a>
	    	<a class="mini-button" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
		 --%>
	    	<c:forEach var="button" items="${topButtonList}">
				<a class="mini-button" iconCls="${button.showico}" plain="true" onclick="${button.srurl}">${button.name}</a>
			</c:forEach>
			<%-- 签章相关的图标按钮 --%>
	    	<jsp:include page="/WEB-INF/JSP/common/form/sealmenus.jsp"></jsp:include>
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:inline;"  iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick="showHelp()"></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
	  	<jsp:include page="/WEB-INF/JSP/common/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/common/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
  
 	<script src="<%=path%>/common/customjs.json?menuid=${fathermenuid}" type="text/javascript"></script>
    <script type="text/javascript">
    	//
    	var _furl = "<%=furl%>";
    	//
        mini.parse();
        //保存或更新(successClose, 成功则关闭)
        function saveData(successClose, sucCallback){
        	var params = "";//mini.get("params").getValue();
        	var crimid = mini.get("crimid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
       		var formclob =saveFile();
       		var noteinfo = getNoteMap();
       		//
       		var url = "<%=path%>/common/saveformdata.json?1=1";
       		//
       		var data = {
	       			params:params
	       			, crimid: crimid
	       			, solutionid: solutionid
	       			, tempid: tempid
	       			, flowdefid: flowdefid
	       			, formclob: formclob
	       			, noteinfo: noteinfo
       			};
			var successCallback = function (message) {
	        	   var info = message["info"];
	        	   var status = message["status"];
	        	   //
	        	   alert(info);
	        	   //
	        	   //
	        	   if(successClose && status){
	               		close();
	        	   }
	               return false;
			    };
			var errorCallback = function (jqXHR, textStatus, errorThrown) {
	               	alert("网络错误!");
			    };
			//
			requestAjax(url,data,successCallback,errorCallback);
        };
        
        
        //保存并提交(successClose, 成功则关闭)
        function saveAndCommit(successClose, sucCallback){
        	var params = "";//mini.get("params").getValue();
        	var crimid = mini.get("crimid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
       		var formclob =saveFile();
       		var noteinfo = getNoteMap();
       		//
       		var url = "<%=path%>/common/saveformdata.json?1=1";
       		//
       		var data = {
	       			params:params
	       			, crimid: crimid
	       			, solutionid: solutionid
	       			, tempid: tempid
	       			, flowdefid: flowdefid
	       			, formclob: formclob
	       			, noteinfo: noteinfo
       			};
			var successCallback = function (message) {
	        	   var info = message["info"];
	        	   var status = message["status"];
	        	   //
	        	   alert(info);
	        	   if(successClose && status){
	               		close();
	        	   }
	               return false;
			    };
			var errorCallback = function (jqXHR, textStatus, errorThrown) {
	               	alert("网络错误!");
			    };
			//
			requestAjax(url,data,successCallback,errorCallback);
        };
        
        
        //保存提交并进入下一个(successClose, 成功则关闭)
        function commitAndNext(successClose, sucCallback){
        	var params = "";//mini.get("params").getValue();
        	var crimid = mini.get("crimid").getValue();
        	var solutionid = mini.get("solutionid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
       		var formclob =saveFile();
       		var noteinfo = getNoteMap();
       		//
       		var url = "<%=path%>/common/saveformdata.json?1=1";
       		//
       		var data = {
	       			params:params
	       			, crimid: crimid
	       			, solutionid: solutionid
	       			, tempid: tempid
	       			, flowdefid: flowdefid
	       			, formclob: formclob
	       			, noteinfo: noteinfo
       			};
			var successCallback = function (message) {
	        	   var info = message["info"];
	        	   var status = message["status"];
	        	   //
	        	   alert(info);
	        	   if(successClose && status){
	               		close();
	        	   }
	               return false;
			    };
			var errorCallback = function (jqXHR, textStatus, errorThrown) {
	               	alert("网络错误!");
			    };
			//
			requestAjax(url,data,successCallback,errorCallback);
        };
        
		//标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            //
        }
        
        function GetData(){
	        //var sql = mini.get("sql").getValue();
	        var data = {};
	        //data.sql = sql;
	        return data;
	    };
	    
        function saveNext(){
        	alert("下一个");
        };
        //关闭.
        function close(){
       		//
       		var r = new Date().getTime();
       		var closetype = $("#closetype").val();
       		var fathermenuid = $("#fathermenuid").val()||"";
       		//
       		var tempid = $("#tempid").val() || "";
       		var solutionid = $("#solutionid").val() || "";
       		//
       		if(closetype && window["Close"]){
       			Close(closetype);
       			return;
       		}
       		//
    	    url = "<%=path%>/common/selectprisoner.page?1=1"+"&tempid="+tempid+"&solutionid="+solutionid+"&menuid="+fathermenuid + "&__r="+r;
        	url = parsefurl() || url ;
    	    window.location.href=url;
        };
        //关闭窗口
        function CloseWindow(action) {
            if (window.CloseOwnerWindow){
            	return window.CloseOwnerWindow(action);
            } else {
            	window.close();
            }
        };
        //手动关闭
        function Close(closetype){
            if(closetype=='0'){ //关闭在待办事项办理案件时的弹出窗口
                var parentWindow = window.parent;
                parentWindow.CloseWindow("cancel");
            }else if(closetype =='1'){//跳转至监狱经办人页
            	var fathermenuid = mini.get("fathermenuid").getValue();
        		var url = "<%=path%>/toAgentCasePage.action?menuid="+fathermenuid;
        		window.parent.location.href=url;
            }else if(closetype =='2'){//跳转至监狱案件办理页面
            	var fathermenuid = mini.get("fathermenuid").getValue();
            	var url = "<%=path%>/toCommuteOfJailCasePage.action?menuid="+fathermenuid;
            	window.parent.location.href=url;
            }else if(closetype =='3'){//跳转至资格筛查页面
            	window.CloseOwnerWindow("cancel");
            }
        };
    </script>
</body>
</html>