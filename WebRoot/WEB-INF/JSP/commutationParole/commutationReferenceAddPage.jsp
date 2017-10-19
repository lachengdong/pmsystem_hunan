<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <input id="punid" name="punid" value="${record.punid}" class="mini-hidden" />
        <input id="refid" name="refid" value="${record.refid}" class="mini-hidden" />
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
	        <table class="form-table" border="0" cellpadding="1" cellspacing="2" style="padding:0 5px;" >
                <tr align="center">
                    <td class="form-label" style="width:110px;">参&nbsp;&nbsp;照&nbsp;&nbsp;名&nbsp;&nbsp;称：</td>
                    <td style="width:150px">
                        <input name="name" id="name" value="${record.name}" class="mini-textbox" required="true" />
                    </td>
                    <td class="form-label" style="width:120px;">受幅度限制：</td>
                    <td style="width:150px">
                        <input name="isinterval" id="isinterval" value="${record.isinterval}" class="mini-combobox" data="array" emptyText="请选择..."  showNullItem="true" nullItemText="请选择..." required="true"/>
                    </td>
                </tr>
                <tr align="center">
                    <td class="form-label" style="width:110px;">计&nbsp;&nbsp;算&nbsp;&nbsp;方&nbsp;&nbsp;式：</td>
                    <td style="width:150px">
                        <input name="remark" id="remark" value="${record.remark}" class="mini-combobox" data="remark" emptyText="请选择..."  showNullItem="true" nullItemText="请选择..." required="true" onvaluechanged="ontypechange"/>
                    </td>
                    <td class="form-label" style="width:120px;"></td>
                    <td style="width:150px">
                    </td>
                </tr>                
            </table>
            <div id="counttype" <c:if test="${record.remark=='1'}"> style="display:none" </c:if>>
            <table class="form-table" border="0" cellpadding="1" cellspacing="2" style="padding:0 5px;" >
                <tr align="center">
                    <td class="form-label" style="width:140px;">最&nbsp;&nbsp;低&nbsp;&nbsp;月&nbsp;&nbsp;数：</td>
                    <td style="width:150px">
                        &nbsp;&nbsp;<input name="lowestnum" id="lowestnum" value="${record.lowestnum}" class="mini-textbox" required="true" vtype="int;maxLength:4" />
                    </td>
                    <td class="form-label" style="width:140px;">最&nbsp;&nbsp;高&nbsp;&nbsp;月&nbsp;&nbsp;数：</td>
                    <td style="width:150px">
                        <input name="highestnum" id="highestnum" value="${record.highestnum}" class="mini-textbox" required="true" vtype="int;maxLength:4" />
                    </td>
                </tr>
                <tr align="center">
                	<td class="form-label" style="width:140px;">建&nbsp;&nbsp;议&nbsp;&nbsp;月&nbsp;&nbsp;数：</td>
                    <td style="width:160px">
                        &nbsp;&nbsp;<input name="suggestnum" id="suggestnum" value="${record.suggestnum}" class="mini-textbox" required="true" vtype="int;maxLength:4" />
                    </td>
                    <td class="form-label" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;优先级：</td>
                    <td style="width:150px">
                        <input name="reflevel" id="reflevel" value="${record.reflevel}" class="mini-textbox" required="true" vtype="int;maxLength:3" />
                    </td>
                </tr>
                <tr align="center">
                	<td class="form-label" style="width:140px;">剥&nbsp;&nbsp;权&nbsp;&nbsp;月&nbsp;&nbsp;数：</td>
                    <td style="width:160px">
                        &nbsp;&nbsp;<input name="boquannum" id="boquannum" value="${record.boquannum}" class="mini-textbox" required="true" vtype="maxLength:4" />
                    </td>
                    
                </tr>             
            </table>
            </div>
    </form>
  </body>
</html>
<script type="text/javascript">
	var array = [{id:"1",text:"是"},{id:"0",text:"否"}];
	var remark = [{id:"1",text:"根据类型计算"},{id:"0",text:"根据方案计算"}];
	function onOk(){
		SaveData();
	}
	function SaveData() {
		var operatetype = mini.get("operatetype").getValue();
		var punid = mini.get("punid").getValue();
		var refid = mini.get("refid").getValue();
		var form = new mini.Form("form1");
		var o = form.getData();
        form.validate();
        if (form.isValid() == false) return;
        var json = mini.encode([o]);
        $.ajax({
               url: "saveCommutationReference.action?1=1",
               data: {data:json, operatetype:operatetype, punid:punid, refid:refid},
               type: "post",
               async:false,
               success: function (text) {
               	alert("操作成功!");
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
    
    function ontypechange(e) {
    	if(e.value=='1') {
    		document.getElementById("counttype").style.display="none";    	
    	} else {
    		document.getElementById("counttype").style.display="";    
    	}
    }
</script>
