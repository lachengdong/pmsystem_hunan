<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑表单管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/form/aip.js" type="text/javascript"></script>
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
	<div style="border-bottom: 0px;" id="form1" class="mini-form">
		<table>
			<tr>

				<td style="white-space: nowrap;">
					<BUTTON style="MARGIN-bottom:1px" id="yw0" class="btn"
						onclick="aip_0.obj.ShowFullScreen = 1;" name="yt0" type="button">
						<I class="icon-node"></I>全屏
					</BUTTON>
					<DIV style="MARGIN: 0px 0px 1px 3px" class="btn-group"
						data-toggle="buttons-radio">
						<BUTTON id="yw1" class="btn"
							onclick="aip_0.obj.SetPageMode(1,100);" name="yt1" type="button">
							<I class="icon-scale-up"></I>1:1
						</BUTTON>
						<BUTTON id="yw2" class="btn"
							onclick="aip_0.obj.SetPageMode(2,100);" name=yt2 type="button">
							<I class="icon-width"></I>页宽
						</BUTTON>
						<BUTTON id="yw3" class="btn" onclick="aip_0.obj.SetPageMode(4,3);"
							name="yt3" type="button">
							<I class="icon-height"></I>页高
						</BUTTON>					
					</DIV>					
					<BUTTON style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" id="yw13"
						class="btn btn-danger" onclick="aip_0.Openfile();" name="yt14"
						type="button">
						<I class="icon-upload"></I>上传
					</BUTTON>
				</td>
				<td style="width:100%;align:left;white-space: nowrap;">&nbsp; &nbsp;模板名称：<input
					name="tempname" id="tempname" class="mini-textbox" required="true" />
					系统模块：<input name="modulename" id="modulename" class="mini-combobox"
					data="[{id:'docsend',text:'公文正文模板'},{id:'ringred',text:'套红模板'}]" value="docsend" required="true" /><input
					name="actiontype" id="actiontype" class="mini-hidden" /> 
					<input name="doctempid" id="doctempid"	class="mini-hidden" value="${doctempid}" /> 备注：<input
					name="remark" id="remark" class="mini-textbox" required="false"
					width="200px" />
				</td>
				<td style="white-space: nowrap;"><a class="btn btn-danger"
					id="allsave" plain="true" onclick="saveAipTemplet();">保存</a> <a
					class="btn" plain="true" onclick="CloseWindow('close');">关闭</a> <a
					class="mini-button" plain="true" iconCls="icon-help"
					onclick="chakanshuoming('8738');"></a></td>

			</tr>
		</table>

	</div>

	<div class="mini-fit" id="docc" style="height: 0px;width: 0px;">
	</div>
	<SCRIPT language=javascript for=AIP_0 event=NotifyCtrlReady>
		var actiontype = '${actiontype}';
		if (actiontype == 'new') {
			aip_0.obj.LoadOriginalFile("", "doc");
		} else {
			aip_0.obj
					.LoadOriginalFile(
							"${basePath}doccftpl/getdocTemplateContent.action?doctempid=${doctempid}",
							"doc");
		}
		aip_0.OnCtrlReady();
		mini.get("docc").set({
			width : '100%'
		});
	</SCRIPT>
	<SCRIPT language=javascript for=AIP_0 event=NotifyDocOpened>
		aip_0.OnDocOpened();
	</SCRIPT>

	<script type="text/javascript">
		aip_0 = new AIP('docc');
		mini.parse();
		function SetData(data) {
			data=mini.clone(data);		
			var form = new mini.Form("#form1");
			data = mini.clone(data);
			if (data.action == "new") {
				data.modulename = 'docsend';
			}
			form.setData(data);
			mini.get("actiontype").setValue(data.action);
		}

		function saveAipTemplet() {
			var form = new mini.Form("#form1");
			var data = form.getData(); //获取表单多个控件的数据			
			form.validate();
			if (form.isValid() == false)
				return;
			var url = "${basePath}doccftpl/uploadDocTempFile.action";
			var aipObj = aip_0.obj;
			aipObj.SilentMode = 1;		
			var json = mini.encode(data); //序列化成JSON		
			try {
				aipObj.HttpInit(); //初始化HTTP引擎。
				aipObj.HttpAddPostString("fileName", "FileBlod"); //设置上传变量
				aipObj.HttpAddPostCurrFile("FileBlod");//设置上传当前文件,文件标识为FileBlod。			
				var ret = aipObj.HttpPost(url); //上传数据当前doc内容
				if (ret != "OK") {
					alert("保存失败！");
					return;
				}
				aipObj.ConvertToAip(0, 1); //转换成aip，为了生成缩略图
				var tmpFileName = aipObj.GetTempFileName('jpg');
				aipObj.saveTo(tmpFileName, 'jpg', 0);
				aipObj.HttpInit(); //初始化HTTP引擎。
				aipObj.HttpAddPostString("fileName", "filethum"); //设置上传变量，由于此处加的中文变量，后台接收乱码，所以此处 保存 数据比较麻烦
				aipObj.HttpAddPostFile("Filethum", tmpFileName);
				ret = aipObj.HttpPost(url); //上传当前文档缩略图			
				aipObj.DeleteLocalFile(tmpFileName);
				if (ret != "OK") {
					alert("保存失败！");
					return;
				}
				$.ajax({
					url : "${path}/doccftpl/doSaveDocTemplateInfo.action",
					data : {
						data : json
					},
					type : "post",
					cache : false,
					async : false,
					success : function(text) {
						alert("保存成功!");
						CloseWindow("OK");
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert("操作失败!");
					}
				});
			} catch (err) {
				msg = '保存文件异常！' + err.toString();
				if ($.type(callback) === 'function') {
					callback({
						code : '-ERR',
						msg : msg
					});
				}
			}
		}

		function CloseWindow(action) {
			var form = new mini.Form("#form1");
			var aipObj = aip_0.obj;
			if (action == "close" && (form.isChanged() || !aipObj.IsSaved())) {
				if (confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		function saveAipTemplet1() {
			var form = new mini.Form("#form1");
			var data = form.getData(); //获取表单多个控件的数据
			form.validate();
			if (form.isValid() == false)
				return;
			var url = "${path}/doccftpl/doSaveDocTemplate.action";
			var aipObj = aip_0.obj;
			data.content2 = aipObj.GetCurrFileBase64();
			try {
				data.thumbnail2 = aipObj.SaveAsBase64("jpg", 1, 1, 2830, 4000,
						1, "");
			} catch (e) {
				alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
						+ "\r\nError Des:" + e.description);
			}
			var json = mini.encode(data); //序列化成JSON
			$.ajax({
				url : url,
				data : {
					data : json
				},
				type : "post",
				cache : false,
				async : false,
				success : function(text) {
					alert("操作成功!");
					CloseWindow("OK");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
				}
			});

		}

		function saveAipTemplet2() {
			var form = new mini.Form("#form1");
			var data = form.getData(); //获取表单多个控件的数据			
			form.validate();
			if (form.isValid() == false)
				return;
			var url = "${basePath}doccftpl/saveDocTemplateWiththum.action";
			var aipObj = aip_0.obj;
			aipObj.SilentMode = 1;
			data.content2 = aipObj.GetCurrFileBase64();
			var json = mini.encode(data); //序列化成JSON
			try {
				aipObj.HttpInit(); //初始化HTTP引擎。
				aipObj.HttpAddPostString("data", json); //设置上传变量User。
				aipObj.HttpAddPostCurrFile("FileBlod");//设置上传当前文件,文件标识为FileBlod。				
				aipObj.ConvertToAip(0, 1); //转换成aip，为了生成缩略图
				var tmpFileName = aipObj.GetTempFileName('jpg');
				aipObj.saveTo(tmpFileName, 'jpg', 0);
				aipObj.HttpAddPostFile("Filethum", tmpFileName);
				var ret = aipObj.HttpPost(url); //上传数据
				aipObj.DeleteLocalFile(tmpFileName);
				if (ret == "OK") {
					mini.alert("保存成功 ！", "提示", function() {
						CloseWindow("OK");
					});
				}
			} catch (err) {
				msg = '保存文件异常！' + err.toString();
				if ($.type(callback) === 'function') {
					callback({
						code : '-ERR',
						msg : msg
					});
				}
			}

		}

		function aipLoadData(templet) {
			try {
				var aipObj = document.getElementById("HWPostil1");
				var obj = eval(templet);
				if (obj) {
					aipObj.LoadFileBase64(obj);//打开模板文件
				}
				aipObj.Login("sys_admin", 5, 32767, "", "");
			} catch (e) {
				alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
						+ "\r\nError Des:" + e.description);
			}
		}
	</script>
</body>
</html>