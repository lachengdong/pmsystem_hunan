<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
  	<title>编辑面板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
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
    </style>
</head>

  
  <body>
    <form id="form1" method="post">
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
		            		<a class="mini-button" onclick="SaveData" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;" border="0">
				<tr >
			        <td style="width:60px;">单&nbsp;&nbsp;&nbsp;&nbsp;位</td>
					<td style="" colspan="3">
						<input id="depart" class="mini-combobox" url="ajaxGetschemedepart.action"  showNullItem="true" emptyText="选择单位"
							textField="NAME" showNullItem="true"  valueField="ORGID" required="true" allowInput="false" style="width:100%;"/>						
			        </td>
				</tr>
			</table>
        </div>
    </form>
  </body>
</html>
<script type="text/javascript">
	function SaveData() {
		var form = new mini.Form("form1");
        form.validate();
        if (form.isValid() == false) return;
        var departid = mini.get("depart").getValue();
        if (!confirm("原有方案会被覆盖,是否继续?")) {
              	return;
          	}
        $.ajax({
               url: "importschemeByorgid.action?1=1",
               data: {departid : departid},
               type: "post",
               async: false,
               success: function (text) {
               	CloseWindow("save");
               },
               error: function (jqXHR, textStatus, errorThrown) {
               	alert("操作失败!");
               }
           });
    }
		    
	function onCancel(e) {
    	CloseWindow("cancel");
    }
    
    function CloseWindow(action) {            
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
</script>
