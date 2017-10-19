<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String tempid = request.getParameter("tempid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑表单管理</title>
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
	font-size: 12px;
}
</style>
</head>
<body>
	<div id="form1" class="mini-splitter" vertical="true"
		style="width:100%;height:100%;border: 0px;">
		<div size="32px;" showCollapseButton="false" maxSize="32px"
			minSize="32px"
			style="border-top: 0px;border-left: 0px;border-right: 0px;">
			<div class="mini-toolbar" style="padding: 1px; border-bottom: 0px;">
				<table>
					<tr>
						<td style="width: 92%;white-space: nowrap;">表单ID：<input
							name="tempid" id="tempid" class="mini-textbox" minValue="0"
							maxValue="20" required="true" allowInput="false" /> 表单名称：<input
							name="tempname" id="tempname" class="mini-textbox"
							required="true" /> 系统模块：<input name="functionname"
							id="functionname" class="mini-textbox" required="true"
							allowInput="true" /> 模板类型： <input property="editor"
							class="mini-combobox" name="type" style="width:120px;"
							data="Genders" id="type" /> <input name="content" id="content"
							class="mini-hidden" /> <a
							class="mini-button mini-button-iconLeft" plain="true"
							iconCls="icon-sets" onclick="setGridHeader();">grid头设置</a> <a
							class="mini-button mini-button-iconLeft" plain="true"
							iconCls="icon-user" onclick="setColrole();">栏位角色绑定</a>
						</td>
						<td style="white-space: nowrap;"><a
							class="mini-button mini-button-iconLeft" plain="true"
							iconCls="icon-save" onclick="saveAipTemplet();">保存</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div showCollapseButton="false">
			<script src="<%=path%>/scripts/form/loadaip.js"></script>

			<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
				try {
					var aipObj = document.all.HWPostil1;
					aipObj.ShowToolBar = 1;
					document.getElementById("HWPostil1").JSEnv = 1;
					HWPostil1_modelEdit();
				} catch (e) {
					alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
							+ "\r\nError Des:" + e.description);
				}
			</script>
			<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyDocOpened()>
				
			</SCRIPT>
			<script LANGUAGE="javascript" FOR="HWPostil1"
				EVENT="NotifyLineAction(lPage,lStartPos,lEndPos)">
				NotifyLineAction(lPage, lStartPos, lEndPos);
			</script>
		</div>
	</div>
	<input name="actiontype" id="actiontype" class="mini-hidden" />
	<input name="departid" id="departid" class="mini-hidden"
		value="${departid}" />
	<script type="text/javascript">
		Genders = [ {
			id : "1",
			text : "系统模板"
		}, {
			id : "2",
			text : "表单"
		} , {
			id : 3,
			text : 'OA表单'
		} ];

		mini.parse();
		var form = new mini.Form("form1");
		function saveAipTemplet() {
			var url;
			//var actiontype = mini.get("actiontype").getValue();
			var departid = mini.get("departid").getValue();
			var content = saveFile();
			var o = form.getData();
			form.validate();
			if (form.isValid() == false)
				return;
			var json = mini.encode([ o ]);
			//if (actiontype == 'edit') {
			url = '${path}/templet/updatsysTemplateInfo.action?1=1';
			//}
			$.ajax({
				url : url,
				data : {
					content : content,
					resid : '1',
					data : json,
					departid : departid
				},
				type : "post",
				cache : false,
				async : false,
				success : function(text) {
					alert("操作成功!");				
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
				}
			});
		}
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			mini.get("actiontype").setValue(data.action);
			if (data.action == "edit") {
				//跨页面传递的数据对象，克隆后才可以安全使用
				data = mini.clone(data);
				$.ajax({
					url : "${path}/templet/getTemplateInfo.action?1=1&tempid="
							+ data.tempid + "&departid=" + data.departid,
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						form.setData(o);
						HWPostil1_modelEdit("'" + o.content + "'");
					}
				});
			}
		}
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
		}

		function test() {
			var aipObj = document.getElementById("HWPostil1");
			alert(aipObj.DocProperty("code_text33"));

		}

		function setColrole() {
			var aipObj = document.getElementById("HWPostil1");
			var NoteInfo = "";
			var clos = [];
			var item = {};

			var templateID = mini.get("tempid").getValue();
			var departid = mini.get("departid").getValue();

			while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				var temp = NoteInfo.split(".")[1];
				//var value = aipObj.GetValueEx(NoteInfo, 2, "", 0, "");
				var rl = aipObj.DocProperty("code_" + temp);
				item = {};
				item.id = temp;
				item.roleid = rl;
				clos.push(item);
			}

			var myobje = {
				clos : clos,
				tempid : templateID,
				departid : departid
			};
			var url = "${path}/templet/toftplcolRoleSet.page?tempid="
					+ templateID + "&departid=" + departid;
			var vRet1 = window
					.showModalDialog(
							url,
							myobje,
							"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:400px;dialogWidth:600px");

			if (vRet1 != undefined) {
				//alert(vRet1);
				for (var i = 0; i < vRet1.length; i++) {
					aipObj.DocProperty("code_" + vRet1[i].id) = vRet1[i].roleid;
				}
			}
		}

		function setGridHeader() {
			var aipObj = document.getElementById("HWPostil1");
			var NoteInfo = "";
			var clos = [];
			var item = {};

			var templateID = mini.get("tempid").getValue();
			var departid = mini.get("departid").getValue();

			while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				var temp = NoteInfo.split(".")[1];
				//var value = aipObj.GetValueEx(NoteInfo, 2, "", 0, "");				
				item = {};
				item.id = temp;
				clos.push(item);
			}		
			var myobje = {
				clos : clos,
				tempid : templateID,
				departid : departid
			};
			var url = "${path}/templet/toftplGridHeadset.page?tempid="
					+ templateID + "&departid=" + departid;
			window
					.showModalDialog(
							url,
							myobje,
							"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:400px;dialogWidth:1000px");
		}

		/****************************************************
		 *	
		 *		打开模版文件所调用的初始化函数
		 *
		 ****************************************************/
		function HWPostil1_modelEdit(templet) {
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

		/****************************************************
		 *	
		 *			将AIP文件保存为BASE64字符串
		 *
		 ****************************************************/
		function saveFile() {
			try {
				var aipObj = document.getElementById("HWPostil1");
				//aipObj.Setvalue("isagree", ""); //设置意见状态值为空
				var content = aipObj.GetCurrFileBase64();
			} catch (e) {
				alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
						+ "\r\nError Des:" + e.description);
				return false;
			}
			return content;

		}

		/****************************************************
		 *	
		 *			呈批件模板制作之
		 *			添加区域时所触发事件调用函数
		 *
		 ****************************************************/
		function NotifyLineAction(lPage, lStartPos, lEndPos) {
			try {
				var lStartY = (lStartPos >> 16) & 0x0000ffff;
				var lStartX = ((lStartPos << 16) >> 16) & 0x0000ffff;
				var lEndY = (lEndPos >> 16) & 0x0000ffff;
				var lEndX = ((lEndPos << 16) >> 16) & 0x0000ffff;
				argObj = {
					"page" : lPage,
					"StartX" : lStartX,
					"StartY" : lStartY,
					"EndX" : lEndX,
					"EndY" : lEndY
				};

				var url = "${path}/public/topage.action?viewName=ftpl/set_filed";
				var vRet1 = window
						.showModalDialog(
								url,
								"",
								"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:300px;dialogWidth:350px");

				if (vRet1 != undefined) {
					setField(vRet1.field_name, "3", 3, 0, "", vRet1.field_role);
					//alert('werwer');
				}

			} catch (e) {
				alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
						+ "\r\nError Des:" + e.description);
			}
		}

		/****************************************************
		 *	
		 *				呈批件模板制作之
		 *				设置呈批件参数
		 *parameters：codescript is a sql ： add by fyy for 查询弹出框的值
		 ****************************************************/
		function setField(field_name, field_type, field_set, border,
				codescript, codeType) {
			try {
				if (argObj == null)
					return;

				var obj = document.getElementById("HWPostil1");
				var field_width = argObj.EndX - argObj.StartX;
				var field_height = argObj.EndY - argObj.StartY;

				var vRet = obj
						.InsertNote(field_name, argObj.page, field_type,
								argObj.StartX, argObj.StartY, field_width,
								field_height);
				if (vRet == "") {
					alert("此字段映射已经添加！");
					return;
				}
				argObj.page = parseInt(argObj.page) + 1;
				if (field_set & 0x01) {
					if (field_set & 0x02) {
						obj.SetValue("Page" + argObj.page + "." + field_name,
								":PROP::LABEL:1");
					} else {
						obj.SetValue("Page" + argObj.page + "." + field_name,
								":PROP::LABEL:0");
					}
				} else {
					obj.SetValue("Page" + argObj.page + "." + field_name,
							":PROP::LABEL:3");
				}
				if (field_set & 0x04) {
					obj.SetValue("Page" + argObj.page + "." + field_name,
							":PROP::NODEL:1");
				} else {
					obj.SetValue("Page" + argObj.page + "." + field_name,
							":PROP::NODEL:0");
				}
				if (field_set & 0x08) {
					obj.SetValue("Page" + argObj.page + "." + field_name,
							":PROP:BORDER:" + border);
				} else {
					obj.SetValue("Page" + argObj.page + "." + field_name,
							":PROP:BORDER:0");
				}
				obj.Setvalue("Page" + argObj.page + "." + field_name,
						":PROP:FRONTCOLOR:black");//设置字体颜色
				//obj.DocProperty("m_" + field_name) = field_set.toString();
				//obj.DocProperty("sql_" + field_name) = codescript;
				obj.DocProperty("code_" + field_name) = codeType;

				//alert(codeType);
				argObj = null;
			} catch (e) {
				alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
						+ "\r\nError Des:" + e.description);
			}
		}
	</script>
</body>
</html>