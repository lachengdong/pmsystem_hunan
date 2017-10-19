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
<title>预归档管理</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery/jquery.multifile.js"></script>

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

label {
	float: left;
	font-weight: normal;
	width: 85px;
	text-align: right;
	margin-right: 10px;
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
</style>

</head>
<body>
	<div class="mini-fit">
		<div id="yw1" class="td-nav">
			<table class="td-nav-table">
				<tr>
					<td><a style="padding-left:0;padding-right:0;font-size:13px;"
						class="btn btn-link" id="yw1"
						href="javascript:gotodivArchfileList();">预归档</a><span
						style="display:inline-block;vertical-align: middle;color:#000;">&nbsp;/&nbsp;</span><a
						style="padding-left:0;padding-right:0; text-decoration:none;color:#000;font-size:13px;"
						class="btn btn-link" id="harchfile">新建档案</a>
						<div class="pull-right">
							<button onclick="doSaveFile();" class="btn btn-danger btn-sm"
								style="padding:2px 10px;">保存</button>
							&nbsp;<a class="btn btn-default btn-sm" style="padding:2px 10px;"
								href="javascript:gotodivArchfileList();">返回</a>
						</div></td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<form style="margin-top:5px;margin-left:10px;" id="frmArchFile"
				enctype="multipart/form-data" class="form-horizontal mini-form"
				method="post" target="hidden_frame"
				action="${path}/arch/ajax/editArchFileInfo.json">
				<fieldset>
					<legend style="font-size:16px;font-weight:bold;">文件信息</legend>
					<div class="control-group ">
						<input name="id" id="id" class="mini-hidden" /> <input
							name="action" id="action" class="mini-hidden" /> <label
							class="control-label required" for="Archive_unit_code">组织机构代码
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input class="mini-textbox" name="unitCode"
								id="Archive_unit_code" required="true"
								requiredErrorText="组织机构代码不能为空！" onvaluechanged="setArchiveNo"
								errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_fonds_no">全宗号
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input class="mini-textbox" name="fondsNo" id="Archive_fonds_no"
								onvaluechanged="setArchiveNo" required="true"
								requiredErrorText="全宗号不能为空！" errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_year">年度
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input class="mini-textbox" name="year" id="Archive_year"
								onvaluechanged="setArchiveNo" required="true" vtype="int"
								requiredErrorText="年度不能为空！" errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_save_date">保管期限
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input name="saveDate" class="mini-combobox" id="saveDate"
								onvaluechanged="setArchiveNo" style="width:125px;"
								textField="text" valueField="id" value="1"
								data="[{id:'10',text:'10年'},{id:'30',text:'30年'},{id:'C',text:'永久'}]"
								errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_org_no">归档机构
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input name="orgNo" id="orgNo" class="mini-hidden"
								value="${orgId}" />
							<textarea class="mini-textarea" id="Archive_org_name"
								name="orgName" readOnly="readOnly"
								style="margin-bottom:0;font-size:12px;width:460px;height:35px;margin-bottom:6px;"
								inputStyle="background-color:white;" emptyText="请选择归档机构"
								required="true" requiredErrorText="请选择归档机构!" errorMode="border">${orgName}</textarea>
							<a
								style="vertical-align:bottom;margin-left:3px;padding:2px 5px;margin-bottom:6px;"
								onclick="selectoropen();" class="btn btn-default btn-sm"
								id="yw6">选择</a><a
								style="vertical-align:bottom;margin-left:3px;padding:2px 5px;margin-bottom:6px;"
								onclick="selectorclear();" class="btn btn-default btn-sm"
								id="yw7">清空</a>
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_box_id">盒号
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input name="boxId" class="mini-combobox" id="boxId"
								onvaluechanged="setArchiveNo" errorMode="border"
								emptyText="-请选择-" nullItemText="-请选择-" errorMode="border"
								style="width:275px;" textField="formatName" valueField="id"
								url="${path}/arch/ajax/getAllArchBoxInfos.json" required="true"
								requiredErrorText="请选择卷盒!" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_file_no">件号
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input size="50" maxlength="4" name="fileNo" class="mini-textbox"
								onvaluechanged="setArchiveNo" errorMode="border"
								id="Archive_file_no" required="true" requiredErrorText="件号不能为空！"
								style="width:275px" vType="int" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_archive_no">档号</label>
						<div class="input-group input-group-sm"
							style="margin-top:2px;margin-left:20px;padding:0px;margin-right:20px">
							<input class="form-control input-sm mini-textbox" size="50"
								maxlength="100" id="Archive_archive_no" name="archiveNo"
								readOnly="true"
								style="padding:0px;font-size:13px;height:25px; width:275px;">
							<span class="icon-spinner-12 input-group-addon"
								style="padding:0px 5px;height:21px;float:left;width:50px;cursor:pointer;"
								id="spnExtenname" onclick="setArchiveNo();">刷新</span>
						</div>

					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_file_name">文件标题<span
							class="required">*</span></label>
						<div class="controls">
							<input class="mini-textbox" name="fileName"
								id="Archive_file_name" type="text" style="width:275px;"
								required="true" requiredErrorText="文件标题不能为空！" errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_send_unit">发文单位</label>
						<div class="controls">
							<input size="50" maxlength="50" name="sendUnit"
								id="Archive_send_unit" class="mini-textbox" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_file_code">文件编号</label>
						<div class="controls">
							<input size="50" maxlength="50" name="fileCode"
								id="Archive_file_code" class="mini-textbox" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_carrier_no">载体数量</label>
						<div class="controls">
							<input size="50" maxlength="50" name="carrierNo"
								id="Archive_carrier_no" class="mini-textbox" vtype="int" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_date">时间</label>
						<div class="controls">
							<input id="dtdate1" class="mini-datepicker" name="date1"
								format="yyyy-MM-dd H:mm:ss" showTime="true" timeFormat="H:mm:ss" showClearButton="false" />
						</div>
					</div>

					<div class="control-group ">
						<label class="control-label" for="Archive_carrier_type">载体单位</label>
						<div class="controls">
							<input id="Archive_carrier_type" name="carrierType"
								class="mini-combobox" textField="text" valueField="id"
								emptyText="-请选择-" data="[{id:'页',text:'页'}]" allowInput="false"
								showNullItem="true" nullItemText="-请选择-" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_type">文种</label>
						<div class="controls">
							<input id="Archive_type" name="type" class="mini-combobox"
								textField="text" valueField="id" emptyText="-请选择-"
								data="[{id:'公文',text:'公文'},{id:'发文',text:'发文'},{id:'收文',text:'收文'},{id:'函',text:'函'},{id:'通知',text:'通知'},{id:'公告',text:'公告'}]"
								allowInput="false" showNullItem="true" nullItemText="-请选择-" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_secret">秘密等级</label>
						<div class="controls">
							<input id="Archive_secret" name="secret" class="mini-combobox"
								textField="text" valueField="id" emptyText="-请选择-"
								data="[{id:'普通',text:'普通'},{id:'秘密',text:'秘密'},{id:'机密',text:'机密'},{id:'绝密',text:'绝密'}]"
								allowInput="false" showNullItem="true" nullItemText="-请选择-" />

						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_urgency">紧急程度</label>
						<div class="controls">
							<input id="Archive_urgency" name="urgency" class="mini-combobox"
								textField="text" valueField="id" emptyText="-请选择-"
								data="[{id:'普通',text:'普通'},{id:'紧急',text:'紧急'},{id:'特级',text:'特级'}]"
								allowInput="false" showNullItem="true" nullItemText="-请选择-" />

						</div>
					</div>

					<div class="control-group ">
						<label class="control-label" for="EmailContent_attachment">附件:</label>
						<div class="controls" style="margin-top:2px;">
							<a name="attachment" enableInsertPicture="1" id="yw20"
								class="btn btn-link file-selector btn-sm"
								style="width:100px;height:30px;padding:0px;margin:0px;"> <i
								class="icon-storage"></i> 从本地选择<input id="ytyw20-file"
								type="hidden" value="" name="EmailContent[attachment][]" /><input
								name="arch_attachment" id="arch-file" hideFocus="1" type="file" />
							</a>
							<div id='SelFileDiv'></div>
							<div id="yw20-container"></div>
						</div>
					</div>

					<div class="control-group ">
						<label class="control-label" for="Archive_remark">备注</label>
						<div class="controls">
							<textarea class="mini-textarea" id="Archive_remark" name="remark"
								style="margin-bottom:0;font-size:12px;width:460px;height:35px;margin-bottom:6px;"
								inputStyle="background-color:white;"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>

	<iframe name='hidden_frame' id="hidden_frame" style="display:none;">
	</iframe>
	<div id="winorginfo" class="mini-window" title="部门选择"
		style="width:300px;height:500px;border:0px;" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true"
		showHeader="true" allowDrag="true" bodyStyle="padding:0px;"></div>
	<script type="text/javascript">
		//mini.parse();
		function setArchiveNo(e) {

			var uintcode = mini.get("Archive_unit_code").getValue();
			var fondsno = mini.get("Archive_fonds_no").getValue();
			var year = mini.get("Archive_year").getValue();
			var saveDate = mini.get("saveDate").getValue();
			var num = "001";
			var boxid = mini.get("boxId").getValue();
			var fileno = mini.get("Archive_file_no").getValue();
			if (!boxid)
				boxid = " ";
			mini.get("Archive_archive_no").setValue(
					uintcode + "-" + fondsno + "-" + year + "-" + saveDate
							+ "-" + num + "-" + boxid + "-" + fileno);
		}

		//保存档案文件
		function doSaveFile() {
			var form = new mini.Form("#frmArchFile");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}

			// 界面
			form.loading("保存中，请稍候......");

			$("#frmArchFile").submit();

			return;

			var data = form.getData();

			var url = "${path}/arch/ajax/editArchFileInfo.json";
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功
					mini.alert(info, '提示', function() {
						gotodivArchfileList();
						var grid = mini.get("grdArchfile");
						grid.reload();

					});
				} else {
					alert(info);
				}
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) {
				// 把错误吃了
				alert("保存失败");
				form.unmask();
			};
			requestAjax(url, {
				data : mini.encode(data)
			}, successCallback, errotCallback);
		}

		function callback(info) {
			//alert(mini.get('actiontype'));
			var form = new mini.Form("#frmArchFile");
			form.unmask();
			mini.alert(info, '提示', function() {
				gotodivArchfileList();
				var grid = mini.get("grdArchfile");
				grid.reload();

			});
		}

		//加载归档机构选择页面
		function selectoropen() {
			var url = "${path}/public/toOrgSelectorPage.page";

			var win = mini.get("winorginfo");
			var ifram0 = win.getIFrameEl();
			var orgNo = mini.get("orgNo").getValue();
			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(orgNo);
				}, function(action) {
				});
			} else {
				ifram0.contentWindow.SetData(orgNo);
			}
			win.show();
		}
		//设置归档机构
		function setOrgNo(orgNo, orgName) {
			if (orgNo) {
				mini.get("orgNo").setValue(orgNo);
				mini.get("Archive_org_name").setValue(orgName);
			}
			mini.get("winorginfo").hide();
		}
		//清除归档机构
		function selectorclear() {
			mini.get("orgNo").setValue("");
			mini.get("Archive_org_name").setValue("");
		}
	</script>
</body>
</html>
