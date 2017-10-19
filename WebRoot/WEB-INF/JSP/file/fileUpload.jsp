<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人文件柜</title>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%=path%>/scripts/cors/jquery.xdr-transport.js"></script>
<![endif]-->
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/scripts/fileupload/vendor/jquery.ui.widget.js"></script>
<script src="<%=path%>/scripts/fileupload/jquery.iframe-transport.js"></script>
<script src="<%=path%>/scripts/fileupload/jquery.fileupload.js"></script>


<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />

<link rel="stylesheet" href="<%=path%>/css/blueimp-gallery.min.css">
<link rel="stylesheet" href="<%=path%>/css/jquery.fileupload.css">
<link rel="stylesheet" href="<%=path%>/css/jquery.fileupload-ui.css">

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />

<style>
.btn-add {
	margin: 0px auto;
	padding: 0;
	padding-top: 80px;
	width: 120px;
	float: none;
	height: 30px;
}

.message-info {
	width: 120px;
	float: left;
	height: 30px;
	padding: 0;
	margin: 0;
	font-size: 16px;
	color: #AAAAAA;
}
</style>

</head>

<body style="padding:0px;">
	<div
		style="min-width:700px !important;height:405px;"
		id="upload">
		<div class="modal-header">
			<a class="close" data-dismiss="modal" onclick="doCancle();">×</a>
			<h4 style="font-weight:bold;">上传文件</h4>
		</div>
		<div class="modal-body"
			style="text-align:center;;overflow: visible !important;position: relative !important;">
			<div class="drop-box"
				style="overflow-x:hidden;overflow-y:auto;min-width:670px;height: 246px;text-align: center;border: 2px dashed #DCDCDC;border-radius: 5px;padding-top:10px;">
				<form style="margin-bottom:0;" enctype="multipart/form-data"
					action="${path}/file/uploadFile.action" id="FileFolderPrivate-form"
					multiple="multiple" method="post" target="hidden_frame">
					<div class="fileupload-buttonbar">
						<div class="btn-add">
							<!-- The fileinput-button span is used to style the file input field as button -->
							<span class="btn btn-success btn-sm fileinput-button"
								style="padding:4px 10px"> <i class="icon-plus icon-white"></i>
								<span>添加文件</span> <input id="ytFileFolderPrivate_attachment"
								type="hidden" value="" name="FileFolderPrivate[attachment]1" /><input
								multiple="multiple" name="FileFolderPrivate[attachment]"
								id="FileFolderPrivate_attachment" type="file"
								onchange="javascript:onfileChange();" />
							</span> <input id="hidattachtype" type="hidden" value=""
								name="attachtype" /> <input id="hidfileName" type="hidden"
								value="" name="fileName" /> <input id="hidid" type="hidden"
								value="" name="id" />
							<div class="message-info clearfix" style="font-size:13px;">可上传多个文件</div>
							<button type="submit" class="btn btn-primary btn-sm start"
								id="btnupload" style="display:none">
								<i class="icon-upload icon-white"></i> <span>开始上传</span>
							</button>
							<button type="reset" class="btn btn-warning cancel"
								style="display:none">
								<i class="icon-circle icon-white"></i> <span>取消上传</span>
							</button>
						</div>
						<div class="span5 fileupload-progress fade" style="display:none">
							<!-- The global progress bar -->
							<div class="progress progress-success progress-striped active"
								role="progressbar">
								<div class="bar" style="width:0%;"></div>
							</div>
							<!-- The extended global progress information -->
							<div class="progress-extended">&nbsp;</div>
						</div>
					</div>
					<!-- The loading indicator is shown during image processing -->
					<div class="fileupload-loading"></div>
					<br>
					<!-- The table listing the files available for upload/download -->
					<div class="row-fluid">
						<table class="table table-striped">
							<tbody class="files" data-toggle="modal-gallery"
								id="updownresult" data-target="#modal-gallery"></tbody>
						</table>
					</div>
					<iframe name='hidden_frame' id="hidden_frame" style="display: none">
					</iframe>
				</form>
			</div>
		</div>
		<div class="modal-footer" style="background-color:#f8f8f8">
			<div id="divuploading"
				style="background:url(<%=path%>/images/wait.gif) no-repeat 0px 50%;width:230px;height:15px;float:left;display:none;"></div>
			<button data-dismiss="modal" onclick="dorefresh();"
				class="btn btn-default btn-sm" id="yw23" name="yt7" type="button">关闭</button>
		</div>
	</div>
	<script type="text/javascript">
		function onfileChange() {
			$("#divuploading").show();
			document.getElementById("btnupload").click();

		}
		function doCancle() {
			parent.refreshfolderlist(false);
		}
		function dorefresh() {
			parent.refreshfolderlist(true);
		}
		function SetData(data) {

			$("#hidfileName").val("FileFolderPrivate[attachment]");
			$("#hidattachtype").val(data.attachtype);
			$("#hidid").val(data.id);
		}

		function callback(info) {
			$("#updownresult").prepend($(info));

			$("#upload .fileupload-buttonbar .fileinput-button").addClass(
					"move-right");
			$("#upload .btn-add").addClass("hide-button");
			$("#upload .message-info").hide();
			// $("#upload .fileupload-buttonbar .start").addClass("move-bottom");
			$("#upload .btn-add").addClass("hide-button");
			$("#divuploading").hide();
		}
	</script>
</body>
</html>
