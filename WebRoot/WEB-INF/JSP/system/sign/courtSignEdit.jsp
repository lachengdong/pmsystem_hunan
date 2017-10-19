<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>法院签章方案编辑</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<!-- BUG 啊, skin.css 必须放到 boot.js 下面,等待对方执行完再加载. -->
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>
	</head>
	<body>
		<form id="form1" method="post">
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" onclick="onOk" plain="true"
								style="width: 60px">保存</a>
							<a class="mini-button" onclick="onCancel" plain="true"
								style="width: 60px;">取消</a>
						</td>
						<td style="white-space: nowrap;">
						</td>
					</tr>
				</table>
			</div>
			<input id="signid" name="signid" class="mini-hidden" />
			<div style="padding-left: 11px; padding-bottom: 5px;">
				<table style="table-layout: fixed;">
					<tr>
						<td style="width: 70px;">
							方案名称：
						</td>
						<td style="width: 250px;">
							<input name="name" class="mini-textbox" required="true" autofocus
								style="width: 100%;" />
						</td>
					</tr>
					<tr>
						<td style="width: 70px;">
							签章类型：
						</td>
						<td style="width: 250px;">		
							<input  id="signtype" name="signtype"class="mini-combobox" style="width:100%;" required="true" value="biaodan" data="SIGNTYPE" />					
						</td>
					</tr>
					<tr>
						<td style="width: 70px;">
							模版id：
						</td>
						<td style="width: 250px;">	
							<input id="tempid" name="tempid" class="mini-textbox" required="true"  style="width: 100%;" />	
						</td>
					</tr>
					<tr>
						<td style="width: 70px;">
							签章进程：
						</td>
						<td style="width: 250px;">
							<input name="progress" style="width: 100%;"
								class="mini-spinner" value="0" minValue="1" maxValue="50" required="true"/>
						</td>
					</tr>
					<tr>
						<td style="width: 70px;">
							方&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;案：
						</td>
						<td style="width: 250px;">
							<input name="scheme" style="width: 100%;"  required="true"
								class="mini-textarea" />
						</td>
					</tr>
					<tr>
						<td style="width: 70px;">
							默认意见：
						</td>
						<td style="width: 250px;">
							<input name="content" style="width: 100%;"
								class="mini-textarea" value="同意" />
						</td>
					</tr>
					
					<tr>
						<td style="width: 70px;">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：
						</td>
						<td style="width: 250px;">
							<input name="remark" style="width: 100%;"
								class="mini-textarea" />
						</td>
					</tr>
					<tr>
						<td style="width: 70px;">
							权&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限：
						</td>
						<td style="width: 250px;">
							<input id="resid" name="resid" allowInput="false" value="" style="width:100%;" class="mini-buttonedit" 
                            onbuttonclick="onButtonEdit" />
						</td>
					</tr>
				</table>
			</div>
			<div class="description">
    </div>
		</form>
		<script type="text/javascript">
		var SIGNTYPE = [{id:'biaodan',text:'表单'},{id:'baobiao',text:'报表'}];
		mini.parse();
		var form = new mini.Form("form1");
		function SaveData() {
			var data = form.getData();
			form.validate();
			if (form.isValid() == false){
					return;
			}
			$.ajax( {
				url : "ajax/update.json?1=1",
				data : data,
				cache : false,
				type : "post",
				success : function(text) {
					CloseWindow("save");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
					CloseWindow();
				}
			});
		};
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			//
			if(data){
				//跨页面传递的数据对象，克隆后才可以安全使用
				data = mini.clone(data);
				var signtype = data.signtype;
				form.setData(data);
				form.setIsValid(true);
				form.setChanged(false);
			}
		};
		
		function GetData() {
			var data = form.getData();
			return data;
		};
		function huoqubiaodan() {
			var formtype = mini.get("formtype").value;
			var url = "ajaxGetBaoBiaoShuJu.action?formtype=" + formtype;
			mini.get("rid").load(url);
		};
		function CloseWindow(action) {
			if (action == "close" && form.isChanged()) {
				if (confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		};
		function onOk(e) {
			SaveData();
		};
		function onCancel(e) {
			CloseWindow("cancel");
		};
		function onCloseClick(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
		};
		
		   function onButtonEdit(e) {
            var btnEdit = this;
            mini.open({
                url: "<%=path%>/sign/getShowSourcesContent.page?1=1", 
                showMaxButton: false,
                title: "选择资源",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                        	//document.getElementById("schemeid").value = data.resid;
                            btnEdit.setValue(data.resid);
                            btnEdit.setText(data.name);
                        }
                    }
                }
            });
        }
		</script>
	</body>
</html>
