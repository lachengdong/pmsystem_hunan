<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>老病残信息补录状态撤销</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"rel="stylesheet" type="text/css" />
	<style type="text/css">
		html,body {
			font-size: 12px;padding: 0;margin: 0;border: 0;height: 100%;overflow: auto;
		}
	</style>
</head>
<body onload="init('${menuid}')">
<form id="form1" method="post">
	<div class="mini-toolbar mini-grid-headerCell" style="padding: 1px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>	
		<table>
			<tr>
				<td style="width: 100%;">
					<a style="width: 60px" id="3100749" class="mini-button" iconCls="icon-save" onclick="SaveData" plain="true" >撤销</a>
					<a class="mini-button" onclick="onCancel" plain="true" iconCls="icon-close" style="width: 60px;">关闭</a>
        		</td>
				<td style="white-space: nowrap;">
				</td>
			</tr>
		</table>
	</div>
	<div style="padding-left: 15px; padding-bottom: 5px;">
		<table style="table-layout: fixed;">
			<tr style="height: 10px;">
				<td style="width: 60px;">决定撤销时间：</td>
				<td >
					<input id="revokedate" name="revokedate" format="yyyy-MM-dd HH:mm:ss" showTime="true" style="width: 170px;"  class="mini-datepicker" required="true"/>
				</td>
			</tr>
			<tr style="height: 12px;">
			</tr>
			<tr style="height: 12px;">
				<td style="width: 90px;">撤&nbsp;&nbsp;消&nbsp;&nbsp;原&nbsp;&nbsp;因：</td>
				<td colspan=5>
					<input id="revokereson" name="revokereason" class="mini-textarea" vtype="maxLength:500" style="width: 500px;height: 200px;"/>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	mini.parse();
  	var form = new mini.Form("form1");
    function SaveData() {
       var crimid = '${crimid}';
       form.validate();
       if (form.isValid() == false) return;
       var o = form.getData();
       var json = mini.encode([o]);
       var revokedate = mini.get("revokedate").getText(); 
       var revokereason=mini.get("revokereson").getValue();
       var tstatus="2";
	   $.ajax({
          url: "updateOldIllDisabilityStatus.action?1=1&crimid=${crimid}&id=${id}",
          data: {data:json,revokedate:revokedate,tstatus:tstatus,revokereason:revokereason},
          type: 'POST',
          cache: false,
          success: function (text) {
          	alert("操作成功!");
              onCancel("save");
          },
          error: function (jqXHR, textStatus, errorThrown) {
          	alert("操作失败!");
          }
      }); 
    }
	  //关闭窗口
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
      //标准方法接口定义
      function SetData(data) {
          //跨页面传递的数据对象，克隆后才可以安全使用
          data = mini.clone(data);
          if(data.action=="new"){
              mini.get("revokedate").setValue(new Date());
          }else {
	          mini.getbyName("revokedate").setEnabled(false);
	          mini.getbyName("revokereason").setEnabled(false);
		      mini.getbyName("revokereason").setValue(data.revoke_rs);
		      mini.getbyName("revokedate").setValue(mini.formatDate ( new Date(data.revoke_date), "yyyy-MM-dd HH:mm:ss" ));
		      }
	          var crimid=data.crimid;
		      mini.get("crimid").setValue(data.crimid);
		    /*   mini.get("tstatus").setValue("2"); */
      }
</script>
</body>
</html>