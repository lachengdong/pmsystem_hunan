/**
 * 
 */
// 公文发文页面操作
var myaipObj = null;
var mybaseUrl = '';
var action = '';

$(document).ready(function() {

		}); 

// 此方法覆盖页面中定义的同名方法
function AddCustAction(own, aipObj, baseUrl) {
	myaipObj = aipObj;
	mybaseUrl = baseUrl;
	own.$customAction = myaipAction;
	action = own.$action;
	// 设置发文类型
	if (action == 'new') {
		myaipObj.SetValue("updown1", "上传附件");
		myaipObj.SetValue("updown2", "上传附件");
		myaipObj.SetValue("updown3", "上传附件");
		myaipObj.SetValue("doc_num", "   [  ]   号");
		// doSetDocType();
	}
}

function doSetDocType() {
	var value = myaipObj.GetValueEx("type", 2, "", 0, "");
	var url = mybaseUrl + "/public/topage.action?viewName=cdoc/docTypeSelector";
	var vRet1 = window
			.showModalDialog(
					url,
					value,
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:150px;dialogWidth:250px");

	if (vRet1 != undefined) {
		myaipObj.SetValue("type", "");
		myaipObj.SetValue("type", vRet1);
	}
}

function myaipAction(lActionType, lType, strName, strValue, aipObj) {

	if (strName == "updown1") {
		doUploadFile("attachfile1");
	} else if (strName == "updown2") {
		doUploadFile("attachfile2");
	} else if (strName == "updown3") {
		doUploadFile("attachfile3");
	} else if (strName == "attachfile1") {
		download(strName);
	} else if (strName == "attachfile2") {
		download(strName);
	} else if (strName == "attachfile3") {
		download(strName);
	} else if (strName == "to") {// 主送
		selectAcceptUser("idto", "to");
	} else if (strName == "cto") {// 抄送
		selectAcceptUser("idcto", "cto");
	} else if (strName == "doc_num") {
		setdocnum("hid_docnum", strName);

	}

}

//设置公文字号
function setdocnum(idfield, namefield) {
	var url = mybaseUrl + "/docdeliver/todocNumSet.page?1=1";
	var hid_docnum = myaipObj.GetValueEx(idfield, 2, "", 0, "");
	vRet = window
			.showModalDialog(
					url,
					hid_docnum,
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:120px;dialogWidth:350px");
	if (vRet) {
		myaipObj.SetValue(idfield, "");
		myaipObj.SetValue(idfield, vRet);
		var values = vRet.split(';');
		var temp = values[0] + "【" + values[1] + "】" + values[2]+" 号";
		myaipObj.SetValue(namefield, "");
		myaipObj.SetValue(namefield, temp);
	}
	myaipObj.jsValue = 0;
}
//选择发送对象
function selectAcceptUser(idfield, namefield) {
	var url = mybaseUrl + "/public/topage.action?viewName=cdoc/userSelector";
	var userids1 = myaipObj.GetValueEx(idfield, 2, "", 0, "");
	var userNames1 = myaipObj.GetValueEx(namefield, 2, "", 0, "");
	vRet = window
			.showModalDialog(
					url,
					{
						userids : userids1,
						userNames : userNames1
					},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:560px;dialogWidth:650px");
	if (vRet) {
		myaipObj.SetValue(idfield, "");
		myaipObj.SetValue(idfield, vRet.userids);
		myaipObj.SetValue(namefield, "");
		myaipObj.SetValue(namefield, vRet.userNames);
	}
	myaipObj.jsValue = 0;
}

// 公文附件下载
function download(fieldName) {
	var attachfile = myaipObj.GetValueEx(fieldName, 2, "", 0, "");
	if (attachfile != "") {
		window.location.href = mybaseUrl
				+ "/fileUploadDown/fileDownLoad.action?1=1&realName="
				+ encodeURIComponent(encodeURIComponent(attachfile));
	}
	myaipObj.jsValue = 0;
}

// 上传公文附件
function doUploadFile(fieldName) {
	var url = mybaseUrl + "/public/topage.action?viewName=cdoc/fileUpload";
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:100px;dialogWidth:350px");
	if (vRet) {
		myaipObj.SetValue(fieldName, "");
		myaipObj.SetValue(fieldName, vRet);
	}
	// 不进入编辑框
	myaipObj.jsValue = 0;
}

function beforeSaveAction(obj) {
	var title = obj.GetValueEx("title", 2, "", 0, "");

	if (title == "") {
		alert("请输入发文标题！");
		return 0;
	}
	var impLeve = obj.GetValueEx("impLeve", 2, "", 0, "");
	if (impLeve == "") {
		alert("请选择紧急程度！");
		return 0;
	}
	return 1;
}



