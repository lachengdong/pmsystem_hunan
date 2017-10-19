<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <title>编辑面板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    

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
    </style>
</head>

  
  <body>
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <input id="operatetype" name="operatetype" class="mini-hidden" />
        <input id="punid" name="punid" value="${record.ruleid}" class="mini-hidden" />
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
		            		<a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;" border="0">
				<tr>
			        <td width="75">条款编号：</td>
					<td width="160">
			        	<input id="ruleid" name="ruleid" value="${record.ruleid}" class="mini-textbox" enabled="true" required="true" vtype="maxLength:30"/>
			        </td>
			        <td width="60">条款名称：</td>
					<td width="160">
						<input id="introduction" name="introduction" value="${record.introduction}" class="mini-textbox" enabled="true" required="true"/>
			        <td>
				</tr>
				<tr>
					<td>是否为条款：</td>
					<td>
			        	<input id="isprovisions" name="isprovisions" value="${record.isprovisions}" class="mini-combobox" data="typearray" emptyText="请选择..." showNullItem="true" required="true"/>
			        </td>
			        <td>搜索码：</td>
					<td>
			        	<input id="searchcode" name="searchcode" value="${record.searchcode}" class="mini-textbox" enabled="true" required="true" vtype="maxLength:30"/>
			        </td>			        
				</tr>
				<tr>
					<td>上级条款：</td>
					<td>
			        	<input name="rulepid" id="rulepid" style="width:100%;" class="mini-treeselect" url="ajaxRewardPunishRules.action?1=1" emptyText="请选择..." popupWidth="260" 
			            	value="${record.rulepid}" textField="introduction" valueField="ruleid" parentField="rulepid" valueField="planid" showTreeIcon="true" expandOnNodeClick="true" showArrow="true" />
			        </td>
				</tr>
				<tr>
					<td>条款内容：</td>
					<td colspan="3" width="352">
			        	<input id="content" name="content" width="352" height="200" value="${record.content}" class="mini-textarea" enabled="true" required="true"/>
			        </td>
				</tr>								
            </table>
        </div>
    </form>
  </body>
</html>
<script type="text/javascript">
    var typearray = [{id:"1",text:"是"},{id:"0",text:"否"}];
    
	function onOk(){
		SaveData();
	}
	
	function SaveData() {
		var operatetype = mini.get("operatetype").getValue();
		var form = new mini.Form("form1");
		var o = form.getData();
        form.validate();
        if (form.isValid() == false) return;
        var json = mini.encode([o]);
        $.ajax({
	        url: "saveRewardPunishRule.action?1=1",
	        data: {data:json, operatetype:operatetype},
	        type: "post",
	        async:false,
	        success: function (text) {
	        	CloseWindow("save");
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	        	alert("操作失败!");
	        }
        });
	}
    
		//标准方法接口定义
        function SetData(data) {
        	data = mini.clone(data);
            mini.get("operatetype").setValue(data.action);
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
