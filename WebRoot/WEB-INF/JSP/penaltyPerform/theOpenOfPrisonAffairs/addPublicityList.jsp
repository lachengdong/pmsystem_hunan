<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>狱务公示</title>
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
	<link href="<%=path %>/css/CIC.css" rel="stylesheet" type="text/css"/>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
	    }   
	</style>
</head>
<body>
	<form id="form1" method="post">
		<input id="type" name="type" class="mini-hidden" value=""/>
		<div class="mini-toolbar mini-grid-headerCell" style="padding: 1px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width: 100%;">
						<a class="mini-button mini-button-iconLeft" plain="true" iconCls="icon-save" onclick="savedate();" >保存</a>
						<a class="mini-button mini-button-iconLeft" plain="true" iconCls="" onclick="CloseWindow();" >返回</a>
	        		</td>
					<td style="white-space: nowrap;">
					</td>
				</tr>
			</table>
		</div>
		<fieldset style="width:100%;border:solid 0px #aaa;margin-top:8px;position:relative;">
	        <div style="padding:5px;">
	            <input class="mini-hidden" name="id"/>
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100px;">公示开始时间：</td>
	                    <td style="width:60px;"><input id="begindate" name="begindate" class="mini-datepicker" required="true"/></td>
	                    <td style="width:100px;">公示结束时间：</td>
	                    <td style="width:60px;"><input id="enddate" name="enddate" class="mini-datepicker" required="true"/></td>
	                </tr>
	                <tr>
	                    <td>公&nbsp;&nbsp;示&nbsp;&nbsp;天&nbsp;&nbsp;数：</td>
	                    <td><input id="daynumber" name="daynumber" class="mini-textbox" /></td>
	                    <td>公&nbsp;&nbsp;示&nbsp;&nbsp;地&nbsp;&nbsp;点：</td>
	                    <td><input id="situs" name="situs" class="mini-textbox" required="true"/></td>
	                </tr>
	                <tr>
	                    <td>会&nbsp;&nbsp;议&nbsp;&nbsp;时&nbsp;&nbsp;间：</td>
	                    <td><input id="meetingtime" name="meetingtime" class="mini-datepicker" required="true"/></td>
	                    <td>会&nbsp;&nbsp;议&nbsp;&nbsp;名&nbsp;&nbsp;称：</td>
	                    <td><input id="meetingname" name="meetingname" class="mini-textbox" required="true"/></td>
	                </tr>
	                <tr>
	                    <td>所&nbsp;&nbsp;属&nbsp;&nbsp;单&nbsp;&nbsp;位：</td>
	                    <td><input id="theunit" name="theunit" class="mini-textbox" required="true"/></td>
	                </tr>
	            </table>
	        </div>
    	</fieldset>
	</form>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		function search() {
		     var key = mini.get("key").getValue();
		     grid.load({ key: key });
		}
		function onKeyEnter(e) {
		   search();
		}
		function savedate(){
			var url;
			var o = form.getData();            
            form.validate();
            if (form.isValid() == false) return;
            var json = mini.encode([o]);
	        url = '<%=path%>/form/addreportmanage.action?1=1';
			$.ajax({
		         url:url, 
		         data: {resid:'1',data:json},
		         type: "post",
		         cache:false,
		         async:false,
		         success: function (text){
		         	alert("操作成功!");
		         	CloseWindow('close');
		         }
			});
		}
        //标准方法接口定义
        function SetData(data) {
        	 data = mini.clone(data);
        	 mini.get("type").setValue(data.action);    
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