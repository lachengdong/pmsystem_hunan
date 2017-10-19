<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文正文撰写</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/form/aip.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/form/aip.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />

<style type="text/css">
</style>
</head>
<body>
	<div style="border-bottom: 0px;">
		<table cellPadding="0" cellSpacing="0">
			<tr>
				<td style="width: 100%;white-space: nowrap;"><a
					class="mini-button" iconCls="icon-folderopen" id="allsave"
					plain="true" onclick="aip_0.Openfile();">打开</a> <a
					class="mini-button" iconCls="icon-edit" id="allsave" plain="true"
					onclick="doconvertToAip('${flowid}');">版式文件</a> <a
					class="mini-button" iconCls="icon-node" id="allsave" plain="true"
					onclick="aip_0.FullScreen();">全屏</a> <a class="mini-button"
					plain="true" onclick="aip_0.obj.SetPageMode(1,100);"> <I
						class="icon-scale-up"></I> 1:1
				</a><a id="yw2" class="mini-button" plain="true"
					onclick="aip_0.obj.SetPageMode(2,100);" name=yt2 type="button">
						<I class="icon-width"></I> 页宽
				</a> <a class="mini-button" onclick="aip_0.obj.SetPageMode(4,3);"
					plain="true"> <I class="icon-height"></I> 页高
				</a> <a class="mini-button" onclick="aip_0.obj.SetPageMode(32,1);"
					plain="true"> <I class="icon-book"></I> 翻页
				</a> <a class="mini-button" onclick="doApplyRedTemp();" plain="true"><i
						class="icon-pen"></i> 文件套红</a> <input name="ddltrReversion"
					id="ddltrReversion" class="mini-combobox"
					data="[{id:'1',text:'保留痕迹'},{id:'0',text:'不留痕迹'}]" value="1"
					onvaluechanged="aip_0.setTrackRevision(e);" width="80px" />
					&nbsp;&nbsp; <input name="ddlshowRevison" id="ddlshowRevison"
					class="mini-combobox"
					data="[{id:'1',text:'显示痕迹'},{id:'0',text:'不显痕迹'}]" value="0"
					onvaluechanged="aip_0.setShowRevision(e);" width="80px" /></td>
				<td style="white-space: nowrap;"><a class="mini-button"
					id="btnPrint" iconCls="icon-print" plain="true"
					onclick="aip_0.obj.PrintDoc(1,1);"> 打印</a><a a class="mini-button"
					id="10066" iconCls="icon-save" id="btnSave" plain="true"
					onclick="aip_0.Save('${flowid}');">保存</a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" id="docc" style="height: 0px;width: 0px;"></div>
	<SCRIPT language=javascript for=AIP_0 event=NotifyCtrlReady>
		aip_0.OnCtrlReady();
		mini.get("docc").set({
			width : '100%'
		});
		var action = '${action}';
		if (action == "view") {
			aip_0.obj.ProtectDoc(3, '', 1);
			//object.DocProperty("DocReadonly") = "true"

		}
	</SCRIPT>
	<SCRIPT language=javascript for=AIP_0 event=NotifyDocOpened>
		aip_0.OnDocOpened();
	</SCRIPT>
	<script type="text/javascript">
		var tmppath = "";
		aip_0 = new AIP(
				'docc',
				{
					"loginUser" : "${userName}",
					"fileName" : "Attachment[aip][]",
					"aipUploadUrl" : "${basePath}docdeliver/uploadAipDoc.action?filetype=doc"
				});
		var action = '${action}';
		if (action == 'new') {
			aip_0.cfg.loadorigFileUrl = "${basePath}doccftpl/getdocTemplateContent.action?doctempid=${doctempid}";
		} else {
			aip_0.cfg.loadorigFileUrl = "${basePath}${doccntfileName}";
		}

		function doconvertToAip(flowid) {
			aip_0.obj.SilentMode = 1;
			var vDocDocument = aip_0.obj.GetDocumentObject();
			vDocDocument.AcceptAllRevisions();
			aip_0.Save(flowid, function(ret) {
				if (ret.code == aip_0.cfg.uploadReturnCode) {
					if (confirm('确认要将正文转为版式文件？')) {
						parent.switchAipContent(ret.msg);
					}
				} else {
					aip_0.Alert('上传文件失败:' + ret.msg);
				}
			}, 'silent');
			aip_0.obj.SilentMode = 0;
		}
		function DoSaveCnt() {
			var docName = '';
			aip_0.obj.SilentMode = 1;
			//var vDocDocument = aip_0.obj.GetDocumentObject();
			//vDocDocument.AcceptAllRevisions();
			aip_0.Save('${flowid}', function(ret) {
				if (ret.code == aip_0.cfg.uploadReturnCode) {
					docName = ret.msg;
					parent.SetFlowInstanceDocCntName(docName);
				}
			}, 'silent');
			aip_0.obj.SilentMode = 0;
			return docName;
		}

		function doApplyRedTemp() {
			var url = "${path}/docdeliver/toGetRedTemplates.page?";			
			vRet = window
					.showModalDialog(
							url,
							"",
							"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:300px;dialogWidth:550px");
			if (vRet) {				
				if (tmppath == "") {				
					tmppath = aip_0.obj.GetTempFileName('doc');				
					aip_0.obj.SaveTo(tmppath, "doc", 1);
				}
				aip_0.obj.LoadOriginalFile(
						"${basePath}doccftpl/getdocTemplateContent.action?doctempid="
								+ vRet, "doc");			
				//aip_0.obj.SetFieldValue("doc_num","陕汉狱【2015】01","::ADDMARK::");
				aip_0.obj.SetFieldValue("doc_data",tmppath,"FILE");

			} 
		}

		function test() {

			//alert(aipObj.GetCurrUserID());
			//alert(aipObj.TrackRevisions);
			//alert(aipObj.ShowRevisions);
			//aipObj.TrackRevisions=1;
			//aipObj.ShowRevisions=1;
			try {
				var aipObj = document.all.AIP_0;

				//aipObj.Login("sys_admin", 5, 65535, "DEMO", "");

				//var vDocDocument = aipObj.GetDocumentObject();

				aipObj.SetFieldValue("mark_1", "电子印章", "::ADDMARK::"); //添加书签mark_1，并为它赋值
				aipObj.SetFieldValue("mark_1", "c:/test.doc", "FILE");//套红			

				//var s = aipObj.ShowDialog(0, "", "", "");
				///aipObj.SetFieldValue("mark_1", "::FILE::c:/test.doc",
				//		"::FILE::");//套红
				//aipObj.InsertPicture(s, s, 1, 100, 100, 100);
				//alert('dd');

				try {

					//aipObj.Login("sys_admin", 5, 65535, "DEMO", "");
					//if (!aipObj.IsOpened()) {
					//	alert("必须打开文件，才可以操作");
					//	return false;
					//}
					//if (!aipObj.IsLogin()) {
					//	alert("必须登陆AIP，才可以操作");
					//	return false;
					//}
					//aipObj.CurrAction = 264;
				} catch (e) {
					alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
							+ "\r\nError Des:" + e.description);
				}

			} catch (err) {
				msg = '保存文件异常！' + err.toString();
				alert(msg);
			}
		}

		//var s = aipObj.ShowDialog(0, "", "", "");
		//aipObj.LoadOriginalFile(s, "doc");
	</script>


</body>
</html>
