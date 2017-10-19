<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=en>
<HEAD>
<META content="IE=8.0000" http-equiv="X-UA-Compatible">
<title>公文版式正文</title>
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
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.color-red {
  color: red;
}
.color-blue {
  color: blue;
}
.color-olive {
  color: olive;
}
</style>
</head>

<body>
	<c:if test="${docCnName != ''}">
		<DIV id="toolbar"
			style="text-align:left;BORDER-BOTTOM-COLOR: #428bca;border-bottom: 1px solid;width:100%;height:30px">
			<BUTTON style="MARGIN-bottom:1px" id="yw0" class="btn"
				onclick="aip_0.obj.ShowFullScreen = 1;" name="yt0" type="button">
				<I class="icon-node"></I>全屏
			</BUTTON>
			<DIV style="MARGIN: 0px 0px 0px 3px" class="btn-group"
				data-toggle="buttons-radio">
				<BUTTON id="yw1" class="btn" onclick="aip_0.obj.SetPageMode(1,100);"
					name="yt1" type="button">
					<I class="icon-scale-up"></I> 1:1
				</BUTTON>
				<BUTTON id="yw2" class="btn" onclick="aip_0.obj.SetPageMode(2,100);"
					name=yt2 type="button">
					<I class="icon-width"></I> 页宽
				</BUTTON>
				<BUTTON id="yw3" class="btn" onclick="aip_0.obj.SetPageMode(4,3);"
					name="yt3" type="button">
					<I class="icon-height"></I> 页高
				</BUTTON>
				<BUTTON id="yw4" class="btn" onclick="aip_0.obj.SetPageMode(32,1);"
					name="yt4" type="button">
					<I class="icon-book"></I> 翻页
				</BUTTON>
			</DIV>
			<DIV style="MARGIN: 0px 0px 0px 3px" class="btn-group"
				data-toggle="buttons-radio">
				<BUTTON id="yw5" class="btn" onclick="aip_0.Zoom(5);" name="yt5"
					type="button">
					<I class="icon-zoomin"></I> 放大
				</BUTTON>
				<BUTTON id="yw6" class="btn" onclick="aip_0.Zoom(-5);" name="yt6"
					type="button">
					<I class="icon-zoomout"></I> 缩小
				</BUTTON>
			</DIV>
			<BUTTON style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" id="erase"
				class="btn" name="yt7" type="button">
				<I class="icon-undo-2"></I> 橡皮擦
			</BUTTON>
			<DIV style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" class="btn-group"
				data-toggle="buttons-radio">
				<BUTTON id="yw7" class="btn" title="笔宽：细"
					onclick="aip_0.SetPenWidth(2);" name="yt8" type="button"
					data-placement="right" rel="tooltip">细</BUTTON>
				<BUTTON id="yw8" class="btn" title="笔宽：中 "
					onclick="aip_0.SetPenWidth(3);" name="yt9" type="button"
					data-placement="right" rel="tooltip">中</BUTTON>
				<BUTTON id="yw9" class="btn" title="笔宽：粗"
					onclick="aip_0.SetPenWidth(6);" name="yt10" type="button"
					data-placement="right" rel="tooltip">粗</BUTTON>
			</DIV>
			<DIV style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" class="btn-group"
				data-toggle="buttons-radio">
				<BUTTON id="yw10" class="btn" title="笔色：红"
					onclick="aip_0.SetPenColor(255);" name="yt11" type="button"
					data-placement="right" rel="tooltip">
					<I class="icon-square color-red"></I> 红
				</BUTTON>
				<BUTTON id="yw11" class="btn" title="笔色：黑"
					onclick="aip_0.SetPenColor(0);" name="yt12" type="button"
					data-placement="right" rel="tooltip">
					<I class="icon-square"></I> 黑
				</BUTTON>
				<BUTTON id="yw12" class="btn" title="笔色：蓝"
					onclick="aip_0.SetPenColor(16711680);" name="yt13" type="button"
					data-placement="right" rel="tooltip">
					<I class="icon-square color-blue"></I> 蓝
				</BUTTON>
			</DIV>
			<BUTTON style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" id="yw13"
				class="btn btn-danger" onclick="aip_0.obj.LoadFile('');" name="yt14"
				type="button">
				<I class="icon-upload"></I>上传
			</BUTTON>
			<BUTTON style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" id="yw14"
				class="btn btn-danger" onclick="aip_0.ActAddSeal();" name="yt15"
				type="button">
				<I class="icon-gk_gz"></I>盖章
			</BUTTON>
			<BUTTON style="MARGIN-bottom:1px; MARGIN-LEFT: 3px" id="handWrite"
				class="btn btn-danger" name="yt16" type="button">
				<I class="icon-pencil"></I>手写
			</BUTTON>
			<a a class="mini-button"
				style="margin-left:3px;margin-top: 3px;float:right;" id="10066"
				iconCls="icon-save" id="btnSave" plain="true"
				onclick="aip_0.Save('${flowid}');">保存</a> <a class="mini-button"
				id="btnPrint" style="margin-left:3px;margin-top: 3px;float:right;"
				iconCls="icon-print" plain="true" onclick="aip_0.obj.PrintDoc(1,1);"> 打印</a>
		</DIV>
	</c:if>
	<c:if test="${docCnName eq ''}">
		<div
			style="text-align:center;margin-top:20px;margin-bottom:10px;width:100%;"
			id="upload-message">
			<div class="mini-panel" title="上传版式文件" iconCls=""
				style="width:450px;height:130px;text-align:center;align:center;margin-left:auto ; margin-right:auto"
				showToolbar="false" showCloseButton="false" showFooter="false"
				bodyStyle="text-align:center;align:center;">
				<table cellPadding="0" cellSpacing="0"
					style="margin-left:auto ; margin-right:auto">
					<tr style="height:50px;">
						<td><a onclick="aip_0.LoadScanFile();" class="mini-button"
							id="scanup" name="scanup" type="button" iconCls="icon-print-2">
								从扫描仪上传 </a></td>
						<td><a style="margin-left:20px"
							onclick="aip_0.obj.LoadFile('');" class="mini-button" id="yw2"
							name="yt1" iconCls="icon-goto"> 选择文件 </a></td>
						<td><a style="margin-left:20px;" onclick="doUploadAip();"
							class="btn btn-danger" id="yw3" name="yt2" type="button">
								<I class="icon-upload"></I>上传文件 </a></td>
					</tr>
					<tr>
						<td colspan="3" style="height:10px"></td>
					</tr>
					<tr>
						<td colspan="3" align="center"><span>可上传Word、PDF、图片等格式文件。</span></td>
					</tr>
				</table>
			</div>
		</div>
	</c:if>
	<div class="mini-fit" id="aiptab"
		style="height: 0px;width: 0px;overflow: hidden;"
		bodyStyle="overflow: hidden;" name="aiptab"></div>
	<SCRIPT language=javascript for=AIP_0 event=NotifyCtrlReady>
		aip_0.OnCtrlReady();
	</SCRIPT>
	<SCRIPT language=javascript for=AIP_0 event=NotifyDocOpened>
		aip_0.OnDocOpened();
		mini.get("aiptab").set({
			width : '100%'
		});
	</SCRIPT>
	<script type="text/javascript">
		var docCnName = '${docCnName}';
		aip_0 = new AIP(
				'aiptab',
				{
					"fileName" : "Attachment[aip][]",
					"loginUser" : "${userName}",
					"aipUploadUrl" : "${basePath}docdeliver/uploadAipDoc.action?filetype=aip"
				});
		if (docCnName != '') {
			aip_0.cfg.fileURL = "${basePath}" + docCnName;
		};

		function DoSaveDocAip() {
			var docName = '';
			aip_0.obj.SilentMode = 1;
			aip_0.Save('${flowid}', function(ret) {
				if (ret.code == aip_0.cfg.uploadReturnCode) {
					docName = ret.msg;
					parent.SetFlowInstanceDocAipName(docName);
				}
			}, 'silent');
			aip_0.obj.SilentMode = 0;
			return docName;
		}

		function doUploadAip() {
			if (!(aip_0.obj && aip_0.obj.IsOpened())) {
				aip_0.Alert('请先打开文件！');
				return;
			}
			aip_0.Save('${flowid}', function(ret) {
				if (ret.code == aip_0.cfg.uploadReturnCode) {
					parent.switchAipContent(ret.msg);
				} else {
					aip_0.Alert('上传文件失败:' + ret.msg);
				}
			}, 'silent');
		};

		jQuery(function($) {
			try {

				$('#erase').on(
						'click',
						function(e) {
							if ($('#handWrite').hasClass('btn-primary')) {
								aip_0.obj.CurrAction = 0;
								$('#handWrite').removeClass('btn-primary')
										.addClass('btn-danger');
								$('#handWrite').html(
										'<i class="icon-pen"></i> 手写');
							}
							aip_0.ActErase();
						});

				$('#handWrite')
						.on(
								'click',
								function() {
									if ($(this).hasClass('btn-danger')) {
										aip_0.ActHandWrite();
										$(this).removeClass('btn-danger')
												.addClass('btn-primary');
										$(this)
												.html(
														'<i class="icon-pen"></i> 手写中...');
									} else {
										aip_0.obj.CurrAction = 0;
										$(this).removeClass('btn-primary')
												.addClass('btn-danger');
										$(this).html(
												'<i class="icon-pen"></i> 手写');
									}
								});
			} catch (err) {
				alert(err);
			}
		});
	</script>


</body>
</html>
