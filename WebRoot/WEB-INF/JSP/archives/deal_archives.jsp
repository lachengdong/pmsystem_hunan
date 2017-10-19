﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String  isapprove = (String)request.getAttribute("isapprove");
String formcontent = (String) request.getAttribute("formcontent");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查看档案</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body onload="init('${resid}');">
<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
	<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
		<a class="mini-button"  id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
		<a class="mini-button"  id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
		<span class="separator"></span> 
		<a class="mini-button" id="isdeal" iconCls="icon-downgrade" plain="true" onclick="savearchive();">保存</a>
		<a class="mini-button" id="" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
    	<a class="mini-button"  id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
    	<a class="mini-button"  id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
    	<a class="mini-button"  id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
    	<a class="mini-button"  id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
    	<a class="mini-button"  id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
    	<a class="mini-button"  id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
     	<a class="mini-button"  id="12468" iconCls="icon-gk_forward" tooltip="前一页" plain="true" onclick="doMenueButton(15);"></a>
    	<a class="mini-button"  id="12469" iconCls="icon-gk_next" tooltip="后一页" plain="true" onclick="doMenueButton(16);"></a>
    	<span id ="name" style="margin-left: 100px;">
    	</td>
    	<td style="white-space:nowrap;">
	    	<a class="mini-button"   style="" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	    </tr>
	  	</table>
     </div>	
  	</div>
	<div showCollapseButton="false">
	  	<input id="conent" name="conent" class="mini-hidden" />
	  	<input id="flowid" name="flowid" class="mini-hidden" />
	  	<input id="flowdefid" name="flowdefid" class="mini-hidden" />
	  	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" />
	  	<input id=rank name="rank" class="mini-hidden"/>
	  	<input id=retention name="retention" class="mini-hidden"/>
	  	<input id=archiveid name="archiveid" class="mini-hidden"/>
	  	<input id=applyid name="applyid" class="mini-hidden"/>
	  	<input id=applyname name="applyname" class="mini-hidden"/>
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
    <script type="text/javascript">
        mini.parse();
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }

		 //标准方法接口定义
	    function SetData(data) {
	        //跨页面传递的数据对象，克隆后才可以安全使用
	        data = mini.clone(data);
	        mini.get("conent").setValue(data.conent);
	        mini.get("name").setValue(data.name);
	        if(data.action == "view") $("#isdeal").hide();
	        document.getElementById("name").innerHTML = data.name;
	        mini.get("flowdraftid").setValue(data.flowdraftid);
	        mini.get("flowid").setValue(data.flowid);
	    }
	    function close(){
	    	onCancel();
		}
	    function savearchive(){
	    	var docconent = saveFile(); //获取大字段
	    	var flowdraftid = mini.get("flowdraftid").getValue();
	    	var flowid = mini.get("flowid").getValue();
	    	var commenttext = "同意";
	    	$.ajax({
   	            url: "<%=path%>/flow/updatearchtive.json?1=1", 
   	            data: {commenttext:commenttext,docconent:docconent,flowid:flowid,flowdraftid:flowdraftid},
   	            type: "post",
   	            cache:false,
   	            async:false,
   	            success: function (text){
   	   	            alert("操作成功!");
   	            	onCancel("close");
   	            }
   			});
		}
    </script>
</body>
</html>
