<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人邮箱</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<script type="text/javascript" src="<%=path%>/scripts/boot.js"></script>
<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery/select2.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery/select2_locale_zh-CN.js"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery/jquery.multifile.js"></script>


<link href="<%=path%>/css/select2.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.sidebar {
	width: 190px;
	overflow: hidden;
}

.file-selector {
	padding-left: 0 !important;
	position: relative;
}

.file-selector input[type=file] {
	position: absolute;
	left: 0;
	top: 0;
	height: 100%;
	overflow: hidden;
	width: 100%;
	padding: 0;
	opacity: 0;
	filter: alpha(opacity = 0);
	cursor: pointer !important;
}

label {
	font-weight: normal;
}

.control-label {
	text-align: right;
	font-weight:bold;
	width:82px;
}
</style>
</head>
<body style="padding:0px;">
	<div class="mini-toolbar" borderStyle="border:0px">
		<button action-type="opts" action-data="send"
			class="btn btn-success btn-sm"
			style="margin-left:10px;margin-top:3px;padding:2px 5px;" id="yw9"
			name="yt19" type="button">立刻发送</button>
		<button action-type="opts" action-data="draft"
			class="btn btn-default btn-sm "
			style="margin-right:1px;margin-top:3px;padding:2px 10px;" id="yw10"
			name="yt20" type="button">存草稿</button>
		<c:if test="${actionType != 'new'}">
			<button type="button"
				style="margin-right:6px;margin-top:3px;padding:2px 10px;"
				class="btn btn-default btn-sm " id="yw11"
				onclick="parent.hideEditWidow(true);">
				<span class="icon-arrow-left-7"></span> 返回
			</button>
		</c:if>
	</div>
	<div class="mini-fit">
		<form enctype="multipart/form-data" class="form-horizontal mini-form"
			id="EmailContent-form" action="${path}/email/sendMail.action"
			style="padding-left:8px;" method="post" target="hidden_frame">
			<div class="control-group" style="margin-top:4px;">
				<label class="control-label"
					style="float:left;margin-right:6px;">收件人:</label>
				<div class="controls" style="margin-left:5px;">
					<input name="actiontype" id="actiontype" value='${actionType}'
						class="mini-hidden" /> <input name=toUid id="EmailContent_to_uid"
						class="mini-hidden" value="${mail.createUser}" /> <input
						name="contentId" id="contentId" class="mini-hidden"
						value="${contentId}" />
					<textarea class="mini-textarea" id="EmailContent_to_uid_name"
						name="touname" readOnly="readOnly"
						style="margin-bottom:0;font-size:12px;width:460px;"
						inputStyle="background-color:white;" emptyText="请选择收件人"
						required="true" requiredErrorText="请选择收件人!">${toUserName}</textarea>
					<button
						style="margin-left:10px;vertical-align:bottom;padding:2px 5px;"
						onclick="return selectoropen('EmailContent_to_uid','EmailContent_to_uid_name');"
						class="btn btn-success btn-sm" id="yw12">选择</button>
					<button
						style="margin-left:4px;vertical-align:bottom;padding:2px 5px;"
						onclick="return selectorclear('EmailContent_to_uid','EmailContent_to_uid_name');"
						class="btn btn-default btn-sm" id="yw13">清空</button>
					<span class="help-inline error" id="EmailContent_to_uid_em_"
						style="display: none"></span>
					<p class="help-block" style="margin-left:83px;">
						<span class="label label-default user-btn" id="user-btn-addcc"
							style="cursor:pointer;padding:4px;font-size:12px;">添加抄送</span> <span
							class="label label-default user-btn" id="user-btn-addbcc"
							style="cursor:pointer;padding:4px;font-size:12px;">添加密送</span> <span
							class="label label-default user-btn" id="user-btn-addwebmail"
							style="cursor:pointer;padding:4px;font-size:12px;">添加外部联系人</span>
					</p>
				</div>
			</div>
			<div id="user-box-cc_uid">
				<div class="control-group " style="margin-top:10px;">
					<label class="control-label" for="textarea_cc_uid"
						style="float:left;margin-right:6px;">抄送:</label>
					<div class="controls">
						<input name="ccUid" id="EmailContent_cc_uid" class="mini-hidden" />
						<textarea class="mini-textarea" rows="2" id="textarea_cc_uid_name"
							name="" style="margin-bottom:0;width:460px;font-size:12px;"
							readOnly="readOnly"></textarea>
						<button
							style="margin-left:10px;vertical-align:bottom;padding:2px 5px;"
							onclick="return selectoropen('EmailContent_cc_uid','textarea_cc_uid_name');"
							class="btn btn-success btn-sm" id="yw14">选择</button>
						<button
							style="margin-left:4px;vertical-align:bottom;padding:2px 5px;"
							onclick="return selectorclear('EmailContent_cc_uid','textarea_cc_uid_name');"
							class="btn btn-default btn-sm" id="yw15">清空</button>
						<span class="help-inline error" id="EmailContent_cc_uid_em_"
							style="display: none"></span>
					</div>
				</div>
			</div>
			<div id="user-box-bcc_uid">
				<div class="control-group" style="margin-top:10px;">
					<label class="control-label" for="textarea_bcc_uid"
						style="float:left;margin-right:6px; ">密送:</label>
					<div class="controls">
						<input name="bccUid" id="EmailContent_bcc_uid" class="mini-hidden" />
						<textarea class="mini-textarea" rows="2"
							id="textarea_bcc_uid_name" name=""
							style="margin-bottom:0;width:460px;font-size:12px;"
							readOnly="readOnly"></textarea>
						<button
							style="margin-left:10px;vertical-align:bottom;padding:2px 5px;"
							onclick="return selectoropen('EmailContent_bcc_uid','textarea_bcc_uid_name');"
							class="btn btn-success btn-sm" id="yw16">选择</button>
						<button
							style="margin-left:4px;vertical-align:bottom;padding:2px 5px;"
							onclick="return selectorclear('EmailContent_bcc_uid','textarea_bcc_uid_name');"
							class="btn btn-default btn-sm" id="yw17">清空</button>
						<span class="help-inline error" id="EmailContent_bcc_uid_em_"
							style="display: none"></span>
					</div>
				</div>
			</div>
			<div id="user-box-webmail" style="display:none">
				<div class="control-group" style="margin-top:10px;">
					<label class="control-label" for="EmailContent_to_webmail"
						style="float:left;margin-right:6px;">外部收件人:</label>
					<div class="controls" style="float:left;width:80%;">
						<input style="width:100%" name="toWebmail"
							id="EmailContent_to_webmail" type="hidden" />
					</div>
				</div>
				<div style="clear:both;margin-top:10px;height:1px;"></div>
				<div class="control-group" style="margin-top:10px;">
					<label class="control-label" for="EmailContent_webmail_inside_id"
						style="float:left;margin-right:6px;">Internet邮箱:</label>
					<div class="controls"
						style="margin-top:0px;padding-top:0px;">
						<input name="webmailInsideId" class="mini-combobox"
							id="EmailContent_webmail_inside_id"
							style="width:125px;float:left" textField="email" valueField="id"
							value="${defaultsets}" data='${webMailSets}' />
						<p class="help-block"
							style="float:left;margin-top:0px;margin-left:10px;font-size:12px">通过该邮箱发送邮件给外部收件人</p>

					</div>
				</div>
			</div>
			<div style="clear:both;"></div>
			<div class="control-group" style="margin-top:6px;">
				<label class="control-label" for="EmailContent_subject"
					style="float:left;margin-right:6px;">主题:</label>
				<div class="controls">
					<input name="subject" id="EmailContent_subject" type="text"
						class="mini-textbox" size="60" vtype="maxLength:200"
						inputStyle="background-color:white;" height="30"
						value="${mail.subject}" style="height:30px;" required="true"
						requiredErrorText="请输入主题！" width="460px" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="EmailContent_content"
					style="float:left;margin-right:6px;">正文:</label>
				<div class="controls"
					style=" *display: inline-block; *padding-left: 20px; margin-left: 88px; *margin-left: 0;margin-right:5px;">
					<textarea id="EmailContent_content" name="content">
					${mail.content}
					</textarea>

				</div>
			</div>
			<div class="control-group" style="margin-top:6px;">
				<label class="control-label" for="EmailContent_important"
					style="float:left;margin-right:6px;">重要性:</label>
				<div class="controls">
					<input name="important" class="mini-combobox" id="important"
						style="width:125px;" textField="text" valueField="id" value="0"
						data="[{id:'0',text:'一般邮件'},{id:'1',text:'重要邮件'},{id:'2',text:'非常重要'}]" />

				</div>
			</div>
			<div class="control-group " style="margin-top:6px;">
				<label class="control-label" for="Email_read_receipt"
					style="float:left;margin-right:6px;">收条:</label>
				<div class="controls">
					<div id="ytEmail_read_receipt" name="readReceipt"
						class="mini-CheckBox" value="0" trueValue="1" falseValue='0'
						text="&nbsp;请求阅读收条(收件人第一次阅读邮件时，向发件人发送事务提醒消息)"></div>
				</div>
			</div>
			<c:if test="${actionType == 'forward' && attachList.size()>0}">
				<div class="control-group" style="height:30px;margin:0px;">
					<label class="control-label"
						style="float:left;margin-right:6px;">附件:</label>
					<div class="controls">
						<c:forEach var="attachment" items="${attachList}">
							<div class="attachment-wrapper" id="attch_${attachment.id}"
								style="float:left;">
								<div class="dropdown clearfix">
									<a class="dropdown-toggle"
										href="${path}/${attachment.downhref}"><img
										src="${path}/${attachment.thumbnail}" />${attachment.fileName}</a><span
										class="size">(${attachment.strSize})</span>
									<ul class="dropdown-menu">
										<li><a href="${path}/${attachment.downhref}" style="font-size:13px;">下载</a></li>
									</ul>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<div class="control-group " style="margin:0px;">
				<c:choose>
					<c:when test="${actionType == 'forward' && attachList.size()>0}">
						<label class="control-label" for="EmailContent_attachment"
							style="float:left;"></label>
					</c:when>
					<c:otherwise>
						<label class="control-label" for="EmailContent_attachment"
							style="float:left;">附件:</label>
					</c:otherwise>
				</c:choose>
				<div class="controls">
					<a name="EmailContent[attachment]" enableInsertPicture="1"
						id="yw20" class="btn btn-link file-selector btn-sm"
						style="width:100px;height:30px;padding:0px;margin:0px;"> <i
						class="icon-storage"></i> 从本地选择<input id="ytyw20-file"
						type="hidden" value="" name="EmailContent[attachment][]" /><input
						name="EmailContent_attachment" id="yw20-file" hideFocus="1"
						type="file" />
					</a>
					<div id="upload-picture-modal" style="display:none;">
						<div class="modal-header">
							<a class="close">×</a>
							<h4 style="height:20px;width:30%;display: inline-block;">图片批量插入</h4>
						</div>
						<div class="modal-body">
							<div class="drop-box">
								<!-- The template to display files available for upload -->
							</div>
						</div>
					</div>
					<!-- 	<a id="_btn" class="btn btn-link"
						style="width:100px;height:30px;padding:0px;margin:0px;"> <i
						class="icon-image"></i> 批量插入图片
					</a> -->
					<div id='SelFileDiv'></div>
					<div id="yw20-container" style="margin-left:80px;"></div>
				</div>
			</div>
			<div class="control-group" style="height:30px;margin:0px;">
				<label class="control-label" for="Email_read_receipt"
					style="float:left;margin-right:6px;margin-top:2px;">提醒:</label>
				<div class="controls" >
					<div id="TRemind_notification" name="notification"
						class="mini-CheckBox" value="1" trueValue="1" falseValue='0'
						text="&nbsp;发送事务提醒消息"></div>
				</div>
			</div>
			<input id="hidden_email_status" name="emailStatus" type="hidden" />
			<input id="hidden_id" name="hidden_id" type="hidden" /> <input
				id="upload_opt" value="upload" name="upload_opt" type="hidden" />
		</form>
		<iframe name='hidden_frame' id="hidden_frame" style="display:none;">
		</iframe>
		<div id="userSelector" class="mini-window" title="选择人员"
			style="width:650px;height:575px;padding:0px;" showMaxButton="true"
			bodyStyle="padding:0px;" showCollapseButton="false"
			showShadow="false" showToolbar="false" showFooter="false"
			showModal="true" allowResize="false" allowDrag="true"></div>
	</div>
	<script type="text/javascript">
		var editor = null;
		function SetData(data) {
			mini.get("EmailContent_to_uid").setValue(data.userId);
			mini.get("EmailContent_to_uid_name").setValue(data.userName);
		}
		$(function() {
			$('#user-box-cc_uid').hide();
			$('#user-box-bcc_uid').hide();
			//添加抄送
			$("#user-btn-addcc").click(function() {
				$(this).toggleClass("label-success");
				if ($(this).hasClass("label-success")) {
					$('#user-box-cc_uid').show();
					$(this).text('隐藏抄送');
				} else {
					$('#user-box-cc_uid').hide();
					$(this).text('添加抄送');
				}
			});

			//添加密送
			$("#user-btn-addbcc").click(function() {
				$(this).toggleClass("label-success");
				if ($(this).hasClass("label-success")) {
					$('#user-box-bcc_uid').show();
					$(this).text('隐藏密送');
				} else {
					$('#user-box-bcc_uid').hide();
					$(this).text('添加密送');
				}
			});
			//添加外部收件人
			$("#user-btn-addwebmail").click(function() {
				$(this).toggleClass("label-success");
				if ($(this).hasClass("label-success")) {
					$('#user-box-webmail').show();
					$(this).text('隐藏外部收件人');
					$('#EmailContent_to_webmail').focus();
				} else {
					$('#user-box-webmail').hide();
					$(this).text('添加外部收件人');
				}
			});

			editor = CKEDITOR.replace('EmailContent_content', {
				'toolbar' : 'Basic'
			});

			jQuery('#EmailContent_to_webmail').select2({
				'tags' : [],
				'tokenSeparators' : [ ',', ' ' ]
			});

			//附件上传初始化
			$('#yw20-file')
					.MultiFile(
							{
								'list' : '#yw20-container',
								'STRING' : {
									'remove' : '<i class=\"icon-remove-2\" rel=\"tooltip\" title=\"去掉该文件\"><\/i>',
									'selected' : '文件：$file',
									'denied' : '禁止上传扩展名为$ext的文件',
									'duplicate' : '您已经选择了这个文件，请勿重复选择：$file'
								}
							});

		});

		//发送邮件
		$("[action-type='opts']").on("click", function() {
			var form = new mini.Form("#EmailContent-form");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var action = $(this).attr("action-data");
			if (action == "send") {
				$("#hidden_email_status").val("1");

			} else {
				$("#hidden_email_status").val("0");
			}

			$("#upload_opt").val("upload");
			$("#EmailContent-form").submit();
		});

		//选择收件人
		function selectoropen(value, text) {
			var url = "${path}/public/topage.action?viewName=email/userSelector";
			var win = mini.get("userSelector");
			var ifram0 = win.getIFrameEl();
			var data = {};
			data.userids = mini.get(value).getValue();
			data.userNames = mini.get(text).getValue();
			data.valueid = value;
			data.textid = text;

			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(data);
				}, function(action) {
					alert(action);
				});
			} else {
				ifram0.contentWindow.SetData(data);
			}
			win.show();
			return false;
		}
		function selectorclear(value, text) {
			mini.get(value).setValue('');
			mini.get(text).setValue('');
			return false;
		}

		function setSendUser(data) {
			var win = mini.get("userSelector");
			mini.get(data.valueid).setValue(data.userids);
			mini.get(data.textid).setValue(data.userNames);
			win.hide();
		}
		function callback(info) {
			var actiontype = mini.get('actiontype').getValue();
			if (actiontype == 'revert') {
				mini.alert(info, '提示', function() {
					parent.hideEditWidow(false);
				});
			} else {
				mini.alert(info);
			}

		}
	</script>
</body>
</html>
