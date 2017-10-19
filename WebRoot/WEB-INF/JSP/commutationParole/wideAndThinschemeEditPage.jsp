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
        <input id="operatetype" name="operatetype" class="mini-hidden" />
        <input id="punid" name="punid" class="mini-hidden" value="${record.punid}"/>
        <input id="lssid" name="lssid" class="mini-hidden"/>
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
				<tr >
			        <td>方案类型：</td>
					<td width="150">
			        	<input name="schemetype" id="schemetype" value="${record.schemetype}" class="mini-combobox" data="schemearray" valueField="id" textField="text"  style="width:125px;" allowInput="true" required="true" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."/>
			        </td>
			        <td width="85">罪犯类型：</td>
					<td>    
			        	<input name="criminaltype" id="criminaltype" value="${record.criminaltype}" popupWidth="200" class="mini-combobox" textField="text" valueField="id" emptyText="请选择..."   url="ajaxSelectData.action?id=typeid&text=name&table=tbxf_crimetypes" showNullItem="true" nullItemText="请选择..." required="true"/>
			        </td>
				</tr>
				<tr>
					<td>间隔时间：</td>
					<td>
						<input id="lssintervalperiod" name="lssintervalperiod" value="${record.lssintervalperiod}" class="mini-textbox" vtype="int;maxLength:9"/>
					</td>
					<td>起始时间：</td>
					<td>
						<input id="lssstartperiod" name="lssstartperiod" value="${record.lssstartperiod}" class="mini-textbox" vtype="int;maxLength:9"/>
					</td>
				</tr>
				<tr>
					<td>减刑幅度：</td>
					<td>
						<input id="rangestart" name="rangestart" value="${record.rangestart}" class="mini-textbox" vtype="int;maxLength:9"/>
					</td>
					<td>执行刑期：</td>
					<td>
						<input id="lssexecutionsentences" name="lssexecutionsentences" value="${record.lssexecutionsentences}" class="mini-textbox" vtype="int;maxLength:9"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">自新罪判决确定之日起
						<input id="int1" name="int1" value="${record.int1}" class="mini-textbox" vtype="int;maxLength:9"/>
						个月内不得减刑
					</td>
				</tr>
				<tr>
					<td>备　　注：</td>
					<td colspan="3">
						<input id="remark" name="remark" value="${record.remark}" class="mini-textarea" required="false" style="width: 100%;"/>
					</td>
				</tr>
				<tr>
					<td>是否通用：</td>
					<td colspan="3">
					<input id="rbl" class="mini-checkboxlist" repeatItems="0" repeatLayout="table" repeatDirection="vertical" onvalueChanged="onEnglishAndNumberValidation"
						    textField="text" valueField="id" value="1"  data="Radiodata" />
					</td>
				</tr>
            </table>
        </div>
    </form>
  </body>
</html>
<script type="text/javascript">
		var schemearray = [{id:"0",text:"从宽"},{id:"1",text:"从严"}];
		var Radiodata = [{ id: 0, text: '' }];
		
		function onOk(){
			SaveData();
		}
		function SaveData() {
			var operatetype = mini.get("operatetype").getValue();
			var punid = mini.get("punid").getValue();
			var lssid = mini.get("lssid").getValue();
			var rbl = mini.get("rbl").getValue();
			if(rbl == 0) punid = rbl;
			var form = new mini.Form("form1");
			var o = form.getData();
	        form.validate();
	        if (form.isValid() == false) return;
	        var json = mini.encode([o]);
	        $.ajax({
                url: "saveWideAndThinscheme.action?1=1",
                data: {data:json, operatetype:operatetype, punid:punid, lssid:lssid},
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
            mini.get("punid").setValue(data.punid);
            mini.get("lssid").setValue(data.lssid);
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
     function onValueChanged(e) {
            var checked = this.getChecked();
            alert(mini.get("ck1").getValue());
        }
</script>
