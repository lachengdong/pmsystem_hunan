<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>应聘人员登记表</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body onload="init('${uuid}');">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  id="save" iconCls="icon-save" plain="true" onclick="savedata()">保存</a>
	    	<input type ="hidden" name="ids" id="ids" value="${type}">
	    	<input type ="hidden" name="uuid" id="uuid" value="${uuid}">
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	var type=$("#ids").val();
	    	 if(type!=1){
	         	   $("#save").hide();
	          }
	    });
        mini.parse();
        var form = new mini.Form("form1");
        var grid = mini.get("datagrid1");
        var ypjobs ="";
        var ybname ="";
        var tbdate ="";
        //保存或更新
        function savedata(){
        	var aipObj = document.all.HWPostil1;
        	ypname = aipObj.GetValueEx("ypname", 2, "", 0, "");
        	ypjobs = aipObj.GetValueEx("ypjobs", 2, "", 0, "");
        	tbdate = aipObj.GetValueEx("tbdate", 2, "", 0, "");
       		var content = saveFile();
       		var tempid = mini.get("tempid").getValue();
       		var url="<%=path%>/appreg/saveAppReg.action?1=1";
       		var type= $("#ids").val(); 
        	if(type==1){//新增
	            $.ajax({
	                url:encodeURI(encodeURI(url)),
	                data: {content:content,tempid:tempid,ypname:ypname,ypjobs:ypjobs,tbdate:tbdate,uuid:$("#uuid").val()},
	                type: "post",
	                success: function (i) {
	                	alert("操作成功!");
	                  	back();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
           }
        }
		//标准方法接口定义
         function SetData(data) {
            data = mini.clone(data);
          mini.get("operator").setValue(data.action); 
        }
    </script>
</body>
</html>
