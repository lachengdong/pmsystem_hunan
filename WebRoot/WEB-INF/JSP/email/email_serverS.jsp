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
<title>个人邮箱</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta content="text/html;charset=utf-8" http-equiv="content-type" />

<meta http-equiv="cache-control" content="no-cache" />

<meta http-equiv="expires" content="0" />

<script type="text/javascript" src="<%=path%>/scripts/boot.js"></script>

<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />

<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />

<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />

<style type="text/css">
label {
	font-weight: normal;
	margin-right: 5px;
	margin-top: 0px;
}
</style>
</head>

<body>
	<DIV id="toolbar"
		style="text-align:right;BORDER-BOTTOM-COLOR: #428bca;border-bottom: 1px solid;width:100%;height:30px">
		<BUTTON style="MARGIN-bottom:2px;padding:2px 10px;" id="yw0"
			class="btn btn-danger sm" onclick="doSave();" type="button">保存</BUTTON>
		<BUTTON style="MARGIN-bottom:2px;padding:2px 10px;" id="yw0"
			class="btn btn-default sm" onclick="parent.hideEditWidow(false);"
			type="button">返回</BUTTON>
	</DIV>
	<div class="mini-fit" style="padding-left:10px;">
		<form role="form" class="form-horizontal mini-form"
			id="email-webmail-form" method="post">
			<fieldset>
				<legend>基础设置</legend>
				<div class="control-group ">
					<label class="control-label required" for="EmailWebmail_email"
						style="float:left;">电子邮件地址 <span class="required">*</span>
						<input name="actiontype" id="actiontype" class="mini-hidden"
						value="${actionType}" /> <input name="id" id="id"
						class="mini-hidden" />
					</label>
					<div class="controls">
						<div class="input-prepend input-append" style="float:left;">
							<input class="mini-textbox" maxlength="100"
								onkeyup="FillSettingsName(this);" name="email_username"
								id="EmailWebmail_email_username" type="text" /> <span
								class="add-on">@</span> <input class="mini-textbox"
								maxlength="100"
								onkeyup="FillSettingsDomain('EmailWebmail_email_domain');"
								width="50px" required="true" style="width:80px"
								inputStyle="background-color:white;" name="email_domain"
								id="EmailWebmail_email_domain" type="text" />
						</div>
						<p class="help-block">例如abc@263.net</p>
					</div>
				</div>
				<div class="control-group ">
					<label class="control-label required" for="EmailWebmail_email_user"
						style="float:left;margin-left:27px;text-align:left;">登录帐户
						<span class="required">*</span>
					</label>
					<div class="controls">
						<input maxlength="100" name="emailUser"
							id="EmailWebmail_email_user" type="text" class="mini-textbox"
							style="width:150px;" inputStyle="background-color:white;"
							required="true" />
					</div>
				</div>
				<div style="clear:both;height:5px;"></div>
				<div class="control-group ">
					<label class="control-label required" for="EmailWebmail_email_pass"
						style="float:left;margin-left:27px;">登录密码 <span
						class="required">*</span>
					</label>
					<div class="controls">
						<input class="mini-password" maxlength="200" name="emailPass"
							id="EmailWebmail_email_pass" type="password" value=""
							style="width:150px" inputStyle="background-color:white;"
							required="true" />
					</div>
				</div>
			</fieldset>

			<fieldset style="margin-top:10px;">
				<legend>pop3设置</legend>

				<div class="control-group ">
					<label class="control-label required" for="EmailWebmail_pop_server"
						style="float:left;">接收服务器(POP3) <span class="required">*</span>
					</label>
					<div class="controls">
						<input maxlength="100" name="popServer"
							id="EmailWebmail_pop_server" type="text" value=""
							class="mini-textbox" style="width:150px;"
							inputStyle="background-color:white;" required="true" />
					</div>
				</div>

				<div class="control-group" style="clear:left;margin-top:5px;">
					<label class="control-label" for="EmailWebmail_pop3_port"
						style="float:left;margin-left:63px;">pop3端口</label>
					<div class="controls">
						<input class="mini-textbox" name="pop3Port"
							id="EmailWebmail_pop3_port" type="text" value="110" width="40px"
							vtype="int" />
					</div>
				</div>
				<div class="control-group ">
					<label class="control-label" for="EmailWebmail_pop3_ssl"
						style="float:left;"></label>
					<div class="controls">
						<div id="EmailWebmail_pop3_ssl_0" name="pop3Ssl"
							class="mini-CheckBox" value="0" trueValue="1" falseValue='0'
							text="&nbsp;此服务器要求安全连接(SSL)"></div>
					</div>
				</div>
			</fieldset>

			<fieldset style="margin-top:10px;">
				<legend>smtp设置</legend>
				<div class="control-group ">
					<label class="control-label required" style="float:left;"
						for="EmailWebmail_smtp_server">发送服务器(SMTP) <span
						class="required">*</span>
					</label>
					<div class="controls">
						<input maxlength="100" name="smtpServer"
							id="EmailWebmail_smtp_server" type="text" class="mini-textbox"
							style="width:150px;" inputStyle="background-color:white;"
							required="true" />
					</div>
				</div>
				<div class="control-group" style="clear:left;margin-top:6px;">
					<label class="control-label" for="EmailWebmail_smtp_port"
						style="float:left;margin-left:63px;">smtp端口</label>
					<div class="controls">
						<input class="mini-textbox" name="smtpPort"
							id="EmailWebmail_smtp_port" type="text" value="25" width="40px"
							vtype="int" />
					</div>
				</div>
				<div class="control-group ">
					<label class="control-label" for="EmailWebmail_smtp_ssl"
						style="float:left;"></label>
					<div class="controls">
						<div id="EmailWebmail_smtp_ssl_0" name="smtpSsl"
							class="mini-CheckBox" value="0" trueValue="1" falseValue='0'
							text="&nbsp;此服务器要求安全连接(SSL)"></div>
					</div>
				</div>
			</fieldset>

			<fieldset style="margin-top:10px;">
				<legend>其他设置</legend>
				<div class="control-group ">
					<label class="control-label" for="EmailWebmail_smtp_pass"
						style="float:left;">SMTP需要身份验证</label>
					<div class="controls">
						<input name="smtpPass" class="mini-combobox"
							id="EmailWebmail_smtp_ssl_0" style="width:125px;"
							textField="text" valueField="id" value="1"
							data="[{id:'0',text:'否'},{id:'1',text:'是'}]" />

					</div>
				</div>

				<div class="control-group"
					style="clear:left;margin-left:10px;margin-top:5px;">
					<label class="control-label" for="EmailWebmail_check_flag"
						style="float:left;">自动收取外部邮件</label>
					<div class="controls">
						<input name="checkFlag" class="mini-combobox" id="checkFlag"
							style="width:125px;" textField="text" valueField="id" value="1"
							data="[{id:'0',text:'否'},{id:'1',text:'是'}]" />

					</div>
				</div>

				<div class="control-group ">
					<label class="control-label" for="EmailWebmail_quota_limit"
						style="float:left;margin-left:63px;">邮箱容量</label>
					<div class="controls">
						<input class="mini-textbox" disabled="disabled" allowInput="false"
							name="quotaLimit" id="EmailWebmail_quota_limit" type="text"
							value="0" style="float:left;" />
						<p class="help-block">为空或0表示不限制</p>
					</div>
				</div>

				<div class="control-group " style="padding:0px; margin:0px;">
					<label class="control-label" for="EmailWebmail_is_default"
						style="float:left;margin-top: 2px;">默认邮箱</label>
					<div class="controls">
						<div id="isDefault" name="isDefault" class="mini-CheckBox"
							value="0" trueValue="1" falseValue='0'
							style="font-weight:normal;" text="&nbsp;做为内部邮件外发默认邮箱（必须设置账户密码）"></div>
					</div>
				</div>

				<div class="control-group "
					style="padding:0px; margin:0px;clear:left;">
					<label class="control-label" for="EmailWebmail_recv_del"
						style="float:left;margin-top: 2px;">收信删除</label>
					<div class="controls">
						<div id="recvDel" name="recvDel" class="mini-CheckBox" value="0"
							trueValue="1" falseValue='0' text="&nbsp收取邮件后从服务器上删除"></div>
					</div>
				</div>

				<div class="control-group "
					style="padding:0px; margin:0px;clear:left;">
					<label class="control-label" for="EmailWebmail_recv_fw"
						style="float:left;margin-top: 2px;">转发设置</label>
					<div class="controls">
						<div id="recvFw" name="recvFw" class="mini-CheckBox" value="0"
							trueValue="1" falseValue='0' text="&nbsp将内部邮件转发到该邮箱（必须设置可外发邮箱）"></div>
					</div>
				</div>

				<div class="control-group "
					style="padding:0px; margin:0px;clear:left;">
					<label class="control-label" for="EmailWebmail_recv_remind"
						style="float:left;margin-top: 2px;">新邮件提醒</label>
					<div class="controls">
						<div id="recvRemind" name="recvRemind" class="mini-CheckBox"
							value="1" trueValue="1" falseValue='0' text="&nbsp收到新邮件后发送事务提醒消息"></div>
					</div>
				</div>
			</fieldset>
		</form>
	</div>
	<script type="text/javascript">
		mini.parse();
		var mails = {};
		mails['163.com'] = new Array('pop.163.com', 110, 0, 'smtp.163.com', 25,
				0, 0, 1);
		mails['vip.163.com'] = new Array('pop.vip.163.com', 110, 0,
				'smtp.vip.163.com', 25, 0, 0, 1);
		mails['188.com'] = new Array('pop.188.com', 110, 0, 'smtp.188.com', 25,
				0, 0, 1);
		mails['126.com'] = new Array('pop.126.com', 110, 0, 'smtp.126.com', 25,
				0, 0, 1);
		mails['yeah.net'] = new Array('pop.yeah.net', 110, 0, 'smtp.yeah.net',
				25, 0, 0, 1);
		mails['qq.com'] = new Array('pop.qq.com', 110, 0, 'smtp.qq.com', 25, 0,
				0, 1);
		mails['vip.qq.com'] = new Array('pop.qq.com', 110, 0, 'smtp.qq.com',
				25, 0, 1, 1);
		mails['sina.com'] = new Array('pop.sina.com', 110, 0, 'smtp.sina.com',
				25, 0, 0, 1);
		mails['vip.sina.com'] = new Array('pop3.vip.sina.com', 110, 0,
				'smtp.vip.sina.com', 25, 0, 0, 1);
		mails['sohu.com'] = new Array('pop.sohu.com', 110, 0, 'smtp.sohu.com',
				25, 0, 0, 1);
		mails['tom.com'] = new Array('pop.tom.com', 110, 0, 'smtp.tom.com', 25,
				0, 0, 1);
		mails['gmail.com'] = new Array('pop.gmail.com', 995, 1,
				'smtp.gmail.com', 465, 1, 1, 1);
		mails['yahoo.com.cn'] = new Array('pop.mail.yahoo.com.cn', 995, 1,
				'smtp.mail.yahoo.com.cn', 465, 1, 1, 1);
		mails['yahoo.cn'] = new Array('pop.mail.yahoo.cn', 995, 1,
				'smtp.mail.yahoo.cn', 465, 1, 1, 1);
		mails['21cn.com'] = new Array('pop.21cn.com', 110, 0, 'smtp.21cn.com',
				25, 0, 0, 1);
		mails['21cn.net'] = new Array('pop.21cn.net', 110, 0, 'smtp.21cn.net',
				25, 0, 0, 1);
		mails['263.net'] = new Array('263.net', 110, 0, 'smtp.263.net', 25, 0,
				0, 1);
		mails['x263.net'] = new Array('pop.x263.net', 110, 0, 'smtp.x263.net',
				25, 0, 0, 1);
		mails['263.net.cn'] = new Array('263.net.cn', 110, 0, '263.net.cn', 25,
				0, 0, 1);
		mails['263xmail.com'] = new Array('pop.263xmail.com', 110, 0,
				'smtp.263xmail.com', 25, 0, 0, 1);
		mails['foxmail.com'] = new Array('pop.foxmail.com', 110, 0,
				'smtp.foxmail.com', 25, 0, 0, 1);
		mails['hotmail.com'] = new Array('pop3.live.com', 995, 1,
				'smtp.live.com', 25, 1, 1, 1);
		mails['live.com'] = new Array('pop3.live.com', 995, 1, 'smtp.live.com',
				25, 1, 1, 1);

		function FillSettingsName(email) {
			mini.get(email.id).blur();
			$emailDomain = "@"
					+ mini.get('EmailWebmail_email_domain').getValue();
			$('#EmailWebmail_email_user').val(
					mini.get(email.id).getValue() + $emailDomain);
			mini.get('EmailWebmail_email_user').setValue(
					mini.get(email.id).getValue() + $emailDomain);
			mini.get(email.id).focus();
		}
		function isUndefined(variable) {
			return typeof variable == 'undefined' ? true : false;
		}
		function FillSettingsDomain(id) {
			mini.get(id).blur();
			email = mini.get(id).getValue();
			if (isUndefined(mails[email])) {
				mini.get(id).focus();
				return;
			}
			$emailUser = mini.get('EmailWebmail_email_username').getValue()
					+ "@";
			mini.get('EmailWebmail_email_user').setValue($emailUser + email);

			mini.get('EmailWebmail_pop_server').setValue(mails[email][0]);

			mini.get('EmailWebmail_pop3_port').setValue(mails[email][1]);

			mini.get("EmailWebmail_pop3_ssl_0").setValue(mails[email][2]);
			mini.get('EmailWebmail_smtp_server').setValue(mails[email][3]);
			mini.get('EmailWebmail_smtp_port').setValue(mails[email][4]);
			mini.get("EmailWebmail_smtp_ssl_0").setValue(mails[email][5]);
			mini.get("EmailWebmail_smtp_pass").setValue(mails[email][7]);
			mini.get(id).focus();
		}
		function doSave() {
			var form = new mini.Form("#email-webmail-form");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");

			var url = "${path}/email/ajax/doSaveWebMailSets.json";
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功				
					mini.alert(info, '提示', function() {
						parent.hideEditWidow(true);
					});
				} else {
					mini.alert(info);
				}
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) { // 把错误吃了
				form.unmask();
				mini.alert("保存失败");

			};
			requestAjax(url, {
				data : mini.encode(data)
			}, successCallback, errotCallback);
		}

		$(function() {
			var actionType = '${actionType}';
			var webmail = '${webmail}';
			if (actionType == 'edit') {
				var data = mini.decode(webmail);
				var form = new mini.Form("#email-webmail-form");
				form.setData(data);
			}
		});
	</script>
</body>
</html>
