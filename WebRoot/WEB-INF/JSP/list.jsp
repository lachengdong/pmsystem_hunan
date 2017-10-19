<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>办案会议记录</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="/pmsys/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<link href="/pmsys/css/pmisystem.css" rel="stylesheet" type="text/css" />
		<script src="/pmsys/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
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
		<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
//document.getElementById("HWPostil1").JSEnv = 1;
</script>
	</head>
	<body>

		<input id="menuid" name="menuid" type="hidden" />
		<input id="flowid" name="flowid" type="hidden" />
		<input id="tempid" name="tempid" type="hidden" />
		<input id="crimids" name="crimids" type="hidden" />
		<input id="flowdraftids" name="flowdraftids" type="hidden" />
		<input id="curyear" name="curyear" class="mini-hidden" value="${curyear}"/>
		<input id="batch" name="batch" class="mini-hidden" value="${batch}"/>
		<input id="otherid" name="otherid" class="mini-hidden" />
		<div class="mini-splitter" vertical="true"
			style="width: 100%; height: 100%;">
			<div size="1px" showCollapseButton="false">
				<div class="mini-toolbar" style="border: 0; padding: 0px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 700px;">
								<!-- <a class="mini-button" iconCls="icon-edit"  plain="true" onclick="updateModel()">修改模版</a> -->
								<a class="mini-button" iconCls="icon-save" plain="true"
									onclick="savedata()">存盘</a>
								<a class="mini-button" iconCls="icon-new" plain="true"
									onclick="preview()">预览</a>
								<a class="mini-button" id="" iconCls="icon-close" plain="true"
									onclick="onCancel();">关闭</a>
								<!--  <a class="mini-button" iconCls="icon-upload" plain="true" onclick="donew()">上传视频</a> -->
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div showCollapseButton="false">
				<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
				<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
				<div name="editForm1" id="editForm1">
					<input id="status" name="status" class="mini-hidden" />
				</div>
			</div>
		</div>
		<div id="uploadDiv"
			style="display: none; position: absolute; font: 11px '宋体'; top: 100px; left: 200px; width: 300px; height: 100px; text-align: center; border: 1px solid #DAE6F3; background-color: #FFFFFF; padding: 1px; line-height: 22px; z-index: 102;">
			<form name="form3" enctype="multipart/form-data" method="post">
				<table width="100%" style="margin: 0px; border: 0px; padding: 0px;"
					cellspacing="0">
					<tr
						style="width: 100%; height: 24px; text-align: left; padding: 3px; margin: 0px; font: bold 13px '宋体'; color: #FFFFFF; border: 1px solid #DAE6F3; cursor: move; background-color: #99BCE8">
						<td>
							上传视频文件：
						</td>
						<td>
							<input type="button" value="上传" onclick="uploadfile();" />
							<input type="button" value="关闭" onclick="donen()" />
						</td>
					</tr>
					<tr style="height: 30px;" align="center">
						<td colspan="2">
							<input name="file" id="file" type="file" style="width: 200px;" />
							<input id="iid" type="hidden" />
							<br />
						</td>
					</tr>
					<tr style="height: 10px;" align="center">
						<td colspan="2">
							<font color="red" size="2">注意：上传视频的大小不超过100M</font>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<script type="text/javascript">
mini.parse();
var form = new mini.Form("editForm1");
var upload = mini.get("uploadDiv");

//保存或更新
function savedata() {
	var menuid = $("#menuid").val();
	var flowid = $("#flowid").val();
	var tempid = $("#tempid").val();
	var crimids = $("#crimids").val();
	var flowdraftids = $("#flowdraftids").val();
	var curyear = mini.get("curyear").getValue();
	var batch = mini.get("batch").getValue();
	var status = '${status}';
	var otherid = '${otherid}';
	var annexcontent = saveFile();
	var json = getNoteMap();
	var count = 0;
	if (annexcontent.indexOf("<") != -1 || annexcontent.indexOf(">") != -1) {
		alert("不能包含特殊符号，例如 ‘<’ ’>’ 等特殊符号");
		return;
	}
	$.ajax({
	    type:'POST',
	    url:'juegeMeetWhetherExistDoc.action?1=1&otherid='+otherid,
	    async:false,
	    success:function(text){
	        if(text == '"success"'){
	            count =1;
	        }
	    }
	});
	if(count == 1){
	    alert("该会议记录已经保存最终版本,\n无法进行修改!");
	    status = "edit";
	    return;
	}
	if(!status) status = "new";
	form.loading("保存中，请稍后......");
	$.ajax( {
		url : "saveMinutesOfTheMeeting.action?1=1",
		data : {
			annexcontent : annexcontent,
			flowdraftids : flowdraftids,
			status : status,
			otherid : otherid,
			flowid : flowid,
			tempid : tempid,
			json : json,
			curyear : curyear,
			batch : batch
		},
		type : "post",
		success : function(text) {
			otherid = text;
			mini.get("otherid").setValue(otherid);
			mini.get("status").setValue("edit")
			alert("操作成功!");
			form.unmask();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("操作失败!");
			form.unmask();
		}
	});
}

//标准方法接口定义
function SetData(data) {
	//onselectChanged();
	data = mini.clone(data);
	var menuid = data.menuid;
	var tempid = data.tempid;
	var criname = data.criname;
	var flowid = data.flowid;
	var crimids = data.crimids;
	var flowdraftids = data.flowdraftids;
	//alert("tempid:"+tempid+",menuid:"+menuid+",flowid:"+flowid+",crimids:"+crimids+",flowdraftids:"+flowdraftids);return;
	$.ajax( {
		url : "getMinutesOfTheMeeting.action?1=1&tempid=" + tempid
				+ "&crimids=" + crimids + "&menuid=" + menuid + "&criname="
				+ encodeURI(criname),
		type : "post",
		success : function(text) {
			var o = mini.decode(text);
			//alert(o.otherid);
		if (o.annexcontent) {
			//form.setData(o);
			$("#menuid").val(menuid);
			$("#tempid").val(tempid);
			$("#criname").val(criname);
			$("#flowid").val(flowid);
			$("#crimids").val(crimids);
			$("#flowdraftids").val(flowdraftids);
		} else if (!crimids) {
			alert("本批次未保存过数据，请至少选择一条数据!");
			onCancel();
		}
	}
	});
}
//预览会议记录
function preview() {
	var menuid = $("#menuid").val();
	var tempid = $("#tempid").val();
	var crimids = $("#crimids").val();
	var otherid = mini.get("otherid").getValue();
	if(!otherid) otherid = '${otherid}';
	var curyear = mini.get("curyear").getValue();
	var batch = mini.get("batch").getValue();
	var win = mini.open( {
		url : "previewMinutesOfTheMeeting.action?1=1&menuid=" + menuid
				+ "&tempid=" + tempid + "&otherid=" + otherid + "&crimids="
				+ crimids,
		showMaxButton : true,
		allowResize : false,
		title : "预览会议记录",
		width : "900",
		height : "600",
		onload : function() {
			var iframe = this.getIFrameEl();
			var data = {
				action : "edit",
				otherid : otherid,
				tempid : tempid,
				curyear : curyear,
				batch : batch,
				meettype : 1
			};
			iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
			//grid.reload();
	}
	});
}
//样式选择、字号改变时自动改变文本大小、背景颜色
function onselectChanged() {
	var color = mini.get("color").getValue();
	var font = mini.get("font").getValue();
	document.getElementById("annexcontent").className = "mini-textarea";
	mini.get("annexcontent").setCls(color);
	mini.get("annexcontent").setCls(font);
}
//关闭会议记录窗口
function onCancel() {
	CloseWindow("cancel");
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
//打开上传视频窗口
function donew(){
        	document.getElementById("uploadDiv").style.display="";
       }
       //关闭上传视频窗口
       function donen(){
        	document.getElementById("uploadDiv").style.display="none";
       }
       //上传视频
       function uploadfile(){
                var node = tree.getSelectedNode();
        	 	var id=node.FILEID;
                var name = $("#file").val();
                var grid = mini.get("datagrid1");
              if(name==''){   
             		alert("请选择文件！");
      		   	   	return false;
	           }else{
	           	   document.form3.action="fileUpload.action?1=1&folderid="+id;
      		   	   document.form3.submit();
      		   	 //  document.getElementById('uploadDiv').style.display = 'none';
      		   	  // grid.load();
	           }
     	}
     	//修改模版
     	function updateModel(){
     		var menuid = $("#menuid").val();
		    var tempid = $("#tempid").val();
        	var win=mini.open({
                 url:"toMeetingModel.action?1=1",
                 showMaxButton: true,
                 allowResize: false, 
                 title: "会议记录模版", width: "800", height: "550",
                 onload: function () {
                     var iframe = this.getIFrameEl();
                     var data = { action: "edit",tempid: tempid,menuid: menuid};
             		 iframe.contentWindow.SetData(data);
                 },
                 ondestroy: function (action) {
                     //grid.reload();
                 }
     		});
     	}
        function checkIsChanged(){//切换tab页时检查是否已经保存了。
        	var changeflag = form.isChanged();
        	return changeflag;
        }
        
    </script>
	</body>
</html>