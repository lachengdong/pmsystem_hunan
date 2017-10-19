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
<title>新建,修改会议室</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery.multifile.js"></script>

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
	overflow: auto;
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
			<a style="padding-left:0;padding-right:0; text-decoration:none;color:#000;font-size:13px;"
				class="btn btn-link" id="DivanTitle">新建会议室</a>
			<div class="pull-right">
				<button onclick="doDivan();" class="btn btn-danger btn-sm"
					style="padding:2px 10px;">保存</button>
				&nbsp;<a class="btn btn-default btn-sm" style="padding:2px 10px;"
					href="javascript:gotDivanList();">返回</a>
			</div>
			
		</div>
		<div class="mini-fit">
			<div id="frmDivan" style="margin-top:15px;">
				<div class="control-group ">
					<input name="id" id="id" class="mini-hidden" /> <input
						name="tempmid" id="tempmid" class="mini-hidden" /> 
						<label class="control-label">会议室名称</label>
					<div class="controls">
						<textarea class="mini-textarea" id="name" name="name"
							style="margin-bottom:0;
							font-size:12px;width:180px;height:35px;margin-bottom:6px;"></textarea>
					</div>
				</div>
				<div class="control-group ">
					<label class="control-label">会议室描述</label>
					<div class="controls">
						<textarea class="mini-textarea" id="depict" name="depict"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:35px;margin-bottom:6px;"></textarea>
					</div>
					</div>
				
				<div class="control-group ">
					<label class="control-label">申请权限(部门)</label>
					<div class="controls">
						<input name="orgname" id="orgname" class="mini-hidden"
							value="${orgId}" />
						<textarea class="mini-textarea" id="MRoomInfo__department"
							name="department" readOnly="readOnly"
							style="margin-bottom:0;font-size:12px;width:380px;height:35px;margin-bottom:6px;"
							inputStyle="background-color:white;" emptyText="请选择负责部门"
							required="true" requiredErrorText="请选择负责部门!" errorMode="border">${orgName}</textarea>
						<a
							style="vertical-align:bottom;margin-left:3px;padding:2px 5px;margin-bottom:6px;"
							onclick="selectorop();" class="btn btn-default btn-sm" id="yw4">选择</a>

						<a
							style="vertical-align:bottom;margin-left:3px;padding:2px 5px;margin-bottom:6px;"
							onclick="icondownload();" class="btn btn-default btn-sm"
							id="yw5">清空</a>
					</div>
				</div>

				

				<div class="control-group ">
					<label class="control-label">申请权限(人员)</label>
					<div class="controls">
						<textarea class="mini-textarea" id="proposer" name="proposer" readOnly="readOnly"
								style="margin-bottom:0;font-size:10px;width:240px;height:30px;margin-bottom:4px;"
								inputStyle="background-color:white;" emptyText="请选择人员"
								required="true" requiredErrorText="请选择申请权限(人员)!" errorMode="border"></textarea>
						 <a class="mini-button"
							iconCls="icon-add" onclick="appproposer()">添加</a> 
							<a class="mini-button" iconCls="icon-download"
							onclick="icondownproposer()">清空</a>
					</div>
				</div>

				<div class="control-group ">
					<label class="control-label">会议管理员</label>
					<div class="controls">
						<textarea class="mini-textarea" id="adminis" name="adminis" readOnly="readOnly"
								style="margin-bottom:0;font-size:10px;width:240px;height:30px;margin-bottom:4px;"
								inputStyle="background-color:white;" emptyText="请选择管理员"
								required="true" requiredErrorText="请选择会议管理员!" errorMode="border"></textarea>
							<a class="mini-button" iconCls="icon-add" onclick="appcontr()">添加</a>
							<a class="mini-button" iconCls="icon-download"
							onclick="icondowncontr()">清空</a>
					</div>
				</div>

				<div class="control-group ">
					<label class="control-label">可容纳人数</label>
					<div class="controls">
					<input name="capacity" id="capacity" class="mini-textbox" size="60"
							vtype="maxLength:60" required="true">		
				</div>
				</div>
				<div class="control-group" >
					<label class="control-label">设备情况</label>
					<div class="controls">
					<textarea class="mini-textarea" id="equipment" name="equipment"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:35px;margin-bottom:6px;"></textarea>
							</div>	
							</div>
				<div class="control-group">
					<label class="control-label">所在地点</label>
					<div class="controls">
					<textarea class="mini-textarea" id="locus" name="locus"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:35px;margin-bottom:6px;"></textarea>	
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">使用状态</label>
					<div class="controls">
				<input name="state"  id="state" class="mini-combobox" data="enableStatus"
						style="width:125px;" textField="text" valueField="id">
						</div>
						</div>
	</div>
	</div>
</div>
	<div id="winorginfo" class="mini-window" title="部门选择"
		style="width:300px;height:500px;border:0px;" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true"
		showHeader="true" allowDrag="true" bodyStyle="padding:0px;"></div>


	<script type="text/javascript">
	
		//保存数据
		function doDivan() {
			var form = new mini.Form("#frmDivan");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			// 界面
			form.loading("保存中，请稍候......");
			var data = form.getData();
			var url = "${path}/MRoomInfo/ajax/updateDivan.json";

			var successCallback = function(message) {
				form.unmask();
				gotDivanList();				
				grid.reload();
				return false;
			};
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
			var form = new mini.Form("#frmDivan");
			form.unmask();
			mini.alert(info, '提示', function() {
				gotDivanList();
				var grid = mini.get("grdDivan");
				grid.reload();
			});
		}//加载申请权限部门页面
		function selectorop() {
			var url = "<%=path%>/public/toOrgSelectorPage.page";
			var win = mini.get("winorginfo");
			var ifram0 = win.getIFrameEl();
			var orgname = mini.get("orgname").getValue();
			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(orgname);
				}, function(action) {
				});
			} else {
				ifram0.contentWindow.SetData(orgname);
			}
			win.show();
		}
	//设置申请权限部门
		function setOrgNo(orgname, orgName) {
			if (orgname) {
				mini.get("orgname").setValue(orgname);
				mini.get("MRoomInfo__department").setValue(orgName);
			}
			mini.get("winorginfo").hide();
		}
		//清空申请权限部门
		function icondownload() {
		    mini.get("orgname").setValue("");
			mini.get("MRoomInfo__department").setValue("");
		}
	//设置申请权限人员
function appproposer() {
            var url = "<%=path%>/public/topage.action?viewName=cdoc/userSelector";
	var userids1 = mini.get("schemeid").getValue();
	var userNames1 = mini.get("proposer").getValue();
	vRet = window
			.showModalDialog(
					url,
					{
						userids : userids1,
						userNames : userNames1
					},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:560px;dialogWidth:650px");
	if (vRet) {
		mini.get("schemeid").setValue(vRet.userids);
		mini.get("proposer").setValue(vRet.userNames);
	}
        } 
//清空申请权限人员
		function icondownproposer() {
			mini.get("proposer").setValue("");
		}
//加载会议管理员页面
		function appcontr() {
            var url = "<%=path%>/public/topage.action?viewName=cdoc/userSelector";
	var userids1 = mini.get("schemeid").getValue();
	var userNames1 = mini.get("adminis").getValue();
	vRet = window
			.showModalDialog(
					url,
					{
						userids : userids1,
						userNames : userNames1
					},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:560px;dialogWidth:650px");
	if (vRet) {
		mini.get("schemeid").setValue(vRet.userids);
		mini.get("adminis").setValue(vRet.userNames);
	}
        } 
        //清空会议管理员
		function icondowncontr() {
			mini.get("adminis").setValue("");
			}
	</script>
</body>
</html>