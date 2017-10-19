
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>申请幅度</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
  </head>
  
  <body>
   	<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
					<a class="mini-button" onclick="saveData()" plain="true" iconCls="icon-save" style="width: 60px">确定</a>
					<a class="mini-button" onclick="onCancel()" plain="true" style="width: 60px;">取消</a>
				</td>
			</tr>
		</table>
	</div><br/><br/><br/>
	<div style="padding-left: 15px; padding-bottom: 5px;" align="center">
		<input id="crimid" class="mini-hidden" value="${crimid }"/>
		<input id="isstatus" class="mini-hidden" value="${isstatus }"/>
		<table style="table-layout: fixed;">
			<tr style="height: 10px;">
				<td style="width: 90px;">当&nbsp;&nbsp;前&nbsp;&nbsp;罪&nbsp;&nbsp;犯：</td>
				<td >
					${applyname }
				</td>
			</tr>
			<tr>
				<td style="width: 60px;">申&nbsp;&nbsp;请&nbsp;&nbsp;幅&nbsp;&nbsp;度：</td>
				<td >
					<input id="shenqingfudu" name="shenqingfudu" class="mini-textbox" emptyText="请输入申请幅度"  value="${fudu }" vtype="maxLength:500" style="width: 98.1%;"/>
				</td>
			</tr>
		</table>
	</div>
<script type="text/javascript"> 
	var crimid=document.getElementById("crimid").value;
	var isstatus=document.getElementById("isstatus").value;
	 function saveData(){
	 	var shenqingfudu=mini.get("shenqingfudu").getValue();
	 	$.ajax({
            url: "<%=path%>/commutationParole/saveFuDu.action?1=1",
            data:{shenqingfudu:shenqingfudu,crimid:crimid,isstatus:isstatus},
            success: function (text) {
            	CloseWindow("cancel");
            },
            error: function () { 
            	CloseWindow("cancel");
            }
      		 });
	 	
	 }
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
 	
</script> 
  </body>
</html>
