<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>证书绑定</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"	type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/loginsealV7.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
<body>
<div id="form1">
	<form name="DocForm" id="FormId" style="display:none;" >
     	<OBJECT id="SignatureControl"  classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E"  style="width:0px;height:0px;"
     		codebase="iSignatureHTML.cab#version=8,0,0,368" VIEWASTEXT></OBJECT>
    </form>
	<div style="width:100%;">
	    <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
			<input id="type" name="type" class="mini-hidden" value=""/>
			<input id="id" name="id" class="mini-hidden" value="${cert.id}"/>
			
	        <table style="width:100%;">
	            <tr>
	                <td style="width:100%;">
		                <a class="mini-button" onclick="SealLogin()" plain="true" iconCls="icon-add" >读取证书</a>
		                <a class="mini-button" onclick="boundKey()" plain="true" iconCls="icon-save" >绑定证书</a>
		                <a class="mini-button" onclick="Close();" plain="true" iconCls="icon-close" >关闭</a>
	                </td>
	            </tr>
	        </table>           
	    </div>
	</div>
	<table cellspacing="0" cellpadding="2" width="100%" border="0" style="padding: 2px;">
	
		<tr>
			<td  style="width: 70px;">当前帐号 </td>
			<td>
				<input id="userid" name="userid" class="mini-lookup" style="width:450px;" textField="userid" valueField="userid" popupWidth="auto"
			        popup="#gridPanel" grid="#datagrid1" multiSelect="false" value="${cert.userid}" text="${cert.userid}"
			    />
			
			    <div id="gridPanel" class="mini-panel" title="header" iconCls="icon-add" style="width:450px;height:250px;"
			        showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0" 
			    >
			        <div property="toolbar" style="padding:5px;padding-left:8px;text-align:center;">   
			            <div style="float:left;padding-bottom:2px;">
			                <input id="keyText" class="mini-textbox" style="width:100px;" emptyText="帐号/姓名" onenter="onSearchClick"/>
			                
			                <input name="select_orgid" id="select_orgid" class="mini-combobox" style="width:120px;" valueField="ORGID" textField="NAME"  emptyText="请选择部门" 
                				onvaluechanged="onSearchClick" url="getDepartList_new.action?1=1" required="false" showNullItem="true" nullItemText="请选择部门"/>
                				
			                <a class="mini-button" onclick="onSearchClick">查询</a>
			                <a class="mini-button" onclick="onClearClick">清除</a>
			            </div>
			            <div style="float:right;padding-bottom:2px;">
			                <a class="mini-button" onclick="onCloseClick">关闭</a>
			            </div>
			            <div style="clear:both;"></div>
			        </div>
			        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"
			            borderStyle="border:0" showPageSize="false" showPageIndex="false"
			            url="<%=path%>/user/ajax/list.action?1=1" 
			        >
			            <div property="columns">
			                <div type="checkcolumn" ></div>
			                <div field="userid" width="120" headerAlign="center" allowSort="true">帐号</div>
			                <div field="name" width="120" headerAlign="center" allowSort="true">姓名</div>
			                <div field="organization.name" width="100" headerAlign="center" allowSort="true">所属部门</div>
			            </div>
			        </div>
			    </div>
		    </td>
		</tr>
		
	 	<tr>
	        <td  style="width: 70px;">证书主题 </td>
	     	<td  ><input id="subject" name="subject" class="mini-textbox" value="${cert.explian}" readonly="readonly" style="width: 450px;"/></td>
	     </tr>
	    <tr>
	        <td  style="width: 70px;">证书序列号 </td>
	     	<td  ><input id="certno" name="certno" class="mini-textbox" value="${cert.certsn}" readonly="readonly" style="width: 450px;"/></td>
	     </tr>
	     <tr>
	     	<td  style="width: 70px;">证 书 DN </td>
	     	<td  ><textarea rows="1" cols="20" id="certdn" name="certdn"  value="${cert.dn}" class="mini-textarea" readonly="readonly" style="height: 26px;width: 450px;"></textarea></td>
	     </tr>
	     <tr>
	    	<td  style="width: 70px;">公钥证书</td>
	     	<td  ><textarea rows="8" id="certdata" name="certdata"  value="${cert.certdata}" class="mini-textarea" readonly="readonly" style="width: 450px;height: 150px"></textarea></td>
	    </tr>
	</table>
	<input type="hidden" name="keyid" value=""/>
</div>

<div id="longinSeal" style="display:none;"></div>

<script type="text/javascript">
	mini.parse();
	
	function SetData(data){
    	data = mini.clone(data);
    	var action = data['action'];
    	mini.get("type").setValue(action);
    }
	
	//读取点聚证书
	function SealLogin(){
		if (!window["isIE"]) {
			alert("请使用IE浏览器");
			return;
		}
		makeSeal();
		var obj=document.getElementById("DWebSignSeal");
		
		var login=obj.CardLogin("");//登录key，确认用户身份
		var subjectObj = obj.GetCertInfo(0,0,"");//获取证书主题	
		var certnoObj = obj.GetCertInfo(1,0,"");//获取证书序列号
		var certdnObj = obj.GetCertInfo(2,0,"");//获取证书DN		
		var certdataObj = obj.GetCertInfo(3,0,"");//获取公钥证书
	
		if(login==0){//返回值为0表示正确登录
			var subject = mini.get("subject");	
			var certno = mini.get("certno");
			var certdn = mini.get("certdn");
			var certdata = mini.get("certdata");
			
			subject.setValue(subjectObj);
			certno.setValue(certnoObj);
			certdn.setValue(certdnObj);
			certdata.setValue(certdataObj);
		
		}else{
			alert("证书读取失败,请插入正确的UKey或联系管理员");
		}
	};
	
	function boundKey(){
		var certno = mini.get("certno").getValue();
		if(''==certno){
			alert("请先读取证书再绑定！");
		}else{
			var form = new mini.Form("#form1");
		    form.validate();
		    if (form.isValid() == false) {
			    return;
		    }
			var data = form.getData();
			var url = "<%=path%>/saveUserCert.json";
			var successCallback = function (message) {
				var info = message["info"];
				// 判断是否成功
				alert(info);
				if(1 === message["status"]){
				} 
				return false;
		    };
			var errorCallback = function (jqXHR, textStatus, errorThrown) {
		         alert("绑定失败！");
		    };
			requestAjax(url,data,successCallback,errorCallback);
		}
	};
	
	
	//
	function CloseWindow(action){
		if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
		else window.close();
	};

	//手动关闭
	function Close(){
		CloseWindow();
	}
	
	
	var grid = mini.get("datagrid1");
    var keyText = mini.get("keyText");
    var select_orgid = mini.get("select_orgid");
    grid.load();

    function onSearchClick(e) {
        grid.load({
            key: keyText.value,
            orgid: select_orgid.value
        });
    }
    function onCloseClick(e) {
        var lookup2 = mini.get("userid");
        lookup2.hidePopup();
    }
    function onClearClick(e) {
        var lookup2 = mini.get("userid");
        lookup2.deselectAll();
    }
    
</script>
</body>
</html>