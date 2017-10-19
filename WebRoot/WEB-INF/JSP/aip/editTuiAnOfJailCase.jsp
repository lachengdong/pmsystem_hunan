<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>退案修改保存页面</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<script src="<%=path%>/scripts/loginsealV7.js" type="text/javascript">
		</script>
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
	<body onload="init('${menuid}');">
		<div id="form1" class="mini-splitter" vertical="true"
			style="width: 100%; height: 100%; border: 0px;">
			<div size="38px;" showCollapseButton="false">
				<div class="mini-toolbar" style="height: 30px;">
					<table>
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" style="display: none;" id="12456"
									iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true"
									onclick="doMenueButton(4);"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12455"
									iconCls="icon-gk_page" tooltip="阅读模式" plain="true"
									onclick="doMenueButton(3);"></a>&nbsp;
								<a class="mini-button" id="201" iconCls="icon-save" plain="true"
									onclick="save(1);">保存</a>
								<a class="mini-button" id="202" iconCls="icon-downgrade"
									plain="true" onclick="save(2);">提交</a>
								<span class="separator"></span>
								<input id="fathermenuid" name="fathermenuid" class="mini-hidden"
									value="${fathermenuid}" />
								<input id="lastnodeid" name="lastnodeid" type="hidden"
									value="${lastnodeid}" />
								<input id="closetype" name="closetype" type="hidden"
									value="${closetype}" />
								<input id="flowdefid" name="flowdefid" class="mini-hidden"
									value="${flowdefid}" />
								<input id="snodeid" name="snodeid" type="hidden"
									value="${lastnodeid}" />
								<input id="savetype" name="savetype" type="hidden" value="0" />
								<input id="commenttext" name="commenttext" type="hidden"
									value="${commenttext}" />
								<input id="ischeckseal" name="ischeckseal" type="hidden"
									value="${ischeckseal}" />
								<input id="provinceid" name="provinceid" class="mini-hidden"
									value="${provinceid}" />
								<input id="nodeid" name="nodeid" class="mini-hidden"
									value="${nodeid}" />
								<input id="days" name="days" class="mini-hidden" value="${days}" />
								<input id="shanxi" name="shanxi" type="hidden" value="${shanxi}" />
								<input id="chengpibiao" name="chengpibiao" class="mini-hidden"
									value="${chengpibiao}" />
								<input id="tempid" name="tempid" type="hidden" value="${tempid}" />
								<input id="flowdefid" name="flowdefid" type="hidden"
									value="${flowdefid}" />
								<input id="flowid" name="flowid" type="hidden" value="${flowid}" />
								<input id="crimid" name="crimid" type="hidden"
									value="${applyid}" />
								<a class="mini-button" id="" iconCls="icon-download"
									plain="true" onclick="saveNext();">下一个</a>
								<a class="mini-button" id="" iconCls="icon-close" plain="true"
									onclick="Close(${closetype});">关闭</a>
								<span class="separator"></span>
								<a class="mini-button" style="display: none;" id="12458"
									iconCls="icon-gk_gz" tooltip="签名（章）" plain="true"
									onclick="doMenueButton(5);"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12459"
									iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true"
									onclick="doMenueButton(13);"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12460"
									iconCls="icon-gk_cx" tooltip="撤销签名" plain="true"
									onclick="doMenueButton(10);"></a>&nbsp;
								<span class="separator"></span>
								<a class="mini-button" style="display: none;" id="12465"
									iconCls="icon-gk_bjsj" tooltip="笔迹设置" plain="true"
									onclick="doMenueButton(12)"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12461"
									iconCls="icon-gk_sxqm" tooltip="手写签批" plain="true"
									onclick="doMenueButton(6);"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12462"
									iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批"
									onclick="doMenueButton(7);"></a>&nbsp;
								<span class="separator"></span>
								<a class="mini-button" style="display: none;" id="12463"
									iconCls="icon-gk_zw" tooltip="按指纹" plain="true"
									onclick="doMenueButton(8);"></a>&nbsp;
								<span class="separator"></span>
								<a class="mini-button" style="display: none;" id="12464"
									iconCls="icon-gk_print" tooltip="文档打印" plain="true"
									onclick="doMenueButton(9);"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12466"
									iconCls="icon-gk_open" tooltip="加载文档" plain="true"
									onclick="doMenueButton(0);"></a>&nbsp;
								<a class="mini-button" style="display: none;" id="12467"
									iconCls="icon-gk_save" tooltip="另存为..." plain="true"
									onclick="doMenueButton(1);"></a>&nbsp;
							</td>
							<td style="white-space: nowrap;">
								<a class="mini-button" id="1111" iconCls="icon-gk_help"
									tooltip="帮助" plain="true" onclick=""></a>&nbsp;
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
		<div id="longinSeal" style="display: none;"></div>
		<script type="text/javascript">
mini.parse();
//手动下一个
function saveNext() {
	var id = mini.get("id").getValue();
	var closetype = document.getElementById("closetype").value;
	var flowdefid = document.getElementById("flowdefid").value;
	if (id == null || id == '') {
		Close(closetype);
		return;
	}
	var menuid = mini.get("menuid").getValue();
	var fathermenuid = mini.get("fathermenuid").getValue();
	var tempid = mini.get("tempid").getValue();
	var index = id.indexOf(",");
	id = id.substring(index + 1, id.length);
	var url = "toTuiAnTabs.action?1=1&tempid=" + tempid + "&id="
			+ id + "&menuid=" + menuid + "&fathermenuid=" + fathermenuid
			+ "&closetype=" + closetype + "&flowdefid=" + flowdefid;
	if (index > 0) {
		window.parent.location.href = url;
	} else {
		//alert("批量处理已完成！");
		Close(closetype);
	}
}
//手动关闭
function Close(closetype) {
	var fathermenuid = mini.get("fathermenuid").getValue();
    var flowdefid = "other_zfjyjxjssp";
    var tempid = "JXJS_JXJSSHB";
    var url = "<%=path%>/tuian.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
    window.parent.location.href=url;
}
function save(tid) {
	var bigdata = saveFile();
	var tempid = document.getElementById("tempid").value;
	var crimid = document.getElementById("crimid").value;
	var flowdraftid = document.getElementById("flowdraftid").value;
	var flowid = document.getElementById("flowid").value;
	$.ajax( {
		type : "POST",
		url : '<%=path%>/saveTuiAnHan.action?1=1',
		data : {
			bigdata : bigdata,
			tempid : tempid,
			crimid : crimid,
			tid : tid
		},
		cache : false,
		success : function(text) {
			var obj = eval(text);
			if (obj == '1') {
				alert("操作成功！");
			} else {
				alert("操作失败！");
			}
		}
	});
}
//标准方法接口定义
function SetData(data) {

}
function CloseWindow(action) {
	if (window.CloseOwnerWindow)
		return window.CloseOwnerWindow(action);
	else
		window.close();
}
function SealLogin() {
	if (!window["isIE"]) {
		alert("请使用IE浏览器");
		return;
	}
	makeSeal();
	var obj = document.getElementById("DWebSignSeal");
	var login = obj.CardLogin("");//登录key，确认用户身份
	var subjectObj = obj.GetCertInfo(0, 0, "");//获取证书主题	
	var certnoObj = obj.GetCertInfo(1, 0, "");//获取证书序列号
	var certdnObj = obj.GetCertInfo(2, 0, "");//获取证书DN		
	var certdataObj = obj.GetCertInfo(3, 0, "");//获取公钥证书
	return certdnObj + "^" + subjectObj + "^" + certnoObj;
}
</script>
	</body>
</html>
