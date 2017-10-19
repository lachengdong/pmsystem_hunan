<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
String formcontent = (String) request.getAttribute("formcontent");
String approve = (String) request.getAttribute("approve");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯专项奖分</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body onload="init('${menuid}');">
	<input id="templetid" name="templetid" value="${templetid}" type="hidden" />
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
			<a class="mini-button"   id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"   id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    	<a class="mini-button"   id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	${topstr}
	    	<a class="mini-button"   id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"   id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
	    	&nbsp;&nbsp;&nbsp;
	    	名称：<input type="text" id="name" name="name" size="30"/>
	    	&nbsp;&nbsp;
	    	<a class="mini-button" plain="true" onclick="choosepermission();">发送至</a><input type="hidden" id="permissionid" name="permissionid" size="10" />
	    	&nbsp;&nbsp;
	    	<a class="mini-button" plain="true" onclick="saveReportData();">保存</a>
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
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
        mini.parse();
        var form = new mini.Form("form1");
        var datagrid = mini.get("datagrid1");
        function CloseWindow(action) {            
	           if (action == "close" && form.isChanged()) {
	               if (confirm("数据被修改了，是否先保存？")) {
	                   return false;
	               }
	           }
	           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	           else window.close();            
	       }
	       function onCancel() {
	           CloseWindow("cancel");
	       }
      
		//标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            mini.get("operator").setValue(data.action); 
        }

        
        //选择发送给的权限
	    function choosepermission(){
	    	var permissionid = document.getElementById("permissionid").value;
	    	var vRet = window.showModalDialog("choosePermission.action?1=1&permissionid="+permissionid,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:600px");
			if(vRet){
				document.getElementById("permissionid").value = "";
				document.getElementById("permissionid").value = vRet[0];
			}
	    }
	    
	    //保存方法
	    function saveReportData(){
	    	 var templetid = document.getElementById("templetid").value;
	    	 var content = document.getElementById("name").value;
	    	 var permissionid = document.getElementById("permissionid").value;
			 var aipObj=document.getElementById("HWPostil1");	
			 var doccontent = aipObj.GetCurrFileBase64();
			 if(content != '' && permissionid != '' && doccontent != ''){
			 	$.ajax({
					 url:"saveJiYaoMingXiInfo.action?1=1",			    
				     data : {content:content,permissionid:permissionid,doccontent:doccontent,templetid:templetid},
					 type: "post",
					 success: function (text) {
						    alert(text);
						    window.close();
					 },
					 error: function (jqXHR, textStatus, errorThrown) {
						    alert("操作失败!");
				     }
		 		});
			 }else{
			 	if(content == ''){
			 		alert("请填写文件名!");
			 		return false;
			 	}
			 	if(permissionid == ''){
			 		alert("请选择文件发送角色!");
			 		return false;
			 	}
			 	if(doccontent == ''){
			 		alert("请上传文件!");
			 		return false;
			 	}
			 }
	    }
	    
	    //修改或查看
	    function xiugai() {
			var row = datagrid.getSelected();//获取选中记录
	    	mini.open({
                url: "khjcFlowBaseDocLook.action?1=1&docid="+row.docid,
                showMaxButton: true,
             	allowResize: false, 
                title: "修改/查看", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    datagrid.reload();
                }
            });
		}

    </script>
</body>
</html>
