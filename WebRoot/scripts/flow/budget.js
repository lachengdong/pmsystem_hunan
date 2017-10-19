/**
 * 
 */
// 预算申报页面脚本
var myaipObj = null;
var myown=null;
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
	myown=own;
	//页面数据初始化
	if (action == 'new') {
		myaipObj.SetValue("opId", myown.$userid);
		myaipObj.SetValue("opName", myown.$userName);
		myaipObj.SetValue("orgId", myown.$orgid);
		myaipObj.SetValue("orgName", myown.$orgName);
		var d = new Date();
		var year = d.getFullYear() + 1;
		myaipObj.SetValue("year", year);
		var month = '0' + (d.getMonth() + 1);
		var day = '0' + d.getDate();
		myaipObj.SetValue("apply_date", d.getFullYear() + ''
				+ month.substr(month.length - 2, 2) + ''
				+ day.substr(day.length - 2, 2));
	}
}
// 表单隐藏域
function myaipAction(lActionType, lType, strName, strValue, aipObj) {

	if (strName == "orgName") {
		selectOrg("orgId", "orgName");
	} else if (strName == "upload") {
		doUploadFile("public1");
	} else if (strName == "public1") {
		download(strName);
	}
}
function doAddBudgetInfo() {

	myown.$className = 'OABudgetService'; // spring中类名
	myown.$methodName = 'addinsert';
	myown.$classtype = "com.sinog2c.service.api.budget.OABudgetService";
	var parameter = {
		name : '',
		orgid : '',
		orgname : '',
		budmoney : '',
		warncost : '',
		year : '',
		remark : '',
		opid:'',
		isaudit: 1,
		text1 : ''
	};
	parameter.name = myaipObj.GetValueEx("name", 2, "", 0, "");
	parameter.orgid = myaipObj.GetValueEx("orgId", 2, "", 0, "");
	parameter.orgname = myaipObj.GetValueEx("orgName", 2, "", 0, "");
	parameter.budmoney = myaipObj.GetValueEx("budMoney", 2, "", 0, "");
	parameter.warncost = myaipObj.GetValueEx("warnCost", 2, "", 0, "");
	parameter.year = myaipObj.GetValueEx("year", 2, "", 0, "");
	parameter.remark = myaipObj.GetValueEx("description", 2, "", 0, "");
	parameter.opid = myaipObj.GetValueEx("opId", 2, "", 0, "");
	parameter.text1 = myaipObj.GetValueEx("isPub", 2, "", 0, "");
	if(parameter.text1!='')
		parameter.text1='1';
	//var txtdata = myaipObj.GetValueEx("apply_date", 2, "", 0, "");
	//txtdata = txtdata.replace('年', '-');
	//txtdata = txtdata.replace('月', '-');
	//txtdata = txtdata.replace('日', '');
	//var d = mini.parseDate(txtdata);
	//var test = Date.UTC(d.getFullYear(), d.getMonth(), d.getDate());
	//parameter.applydate = test;
	//parameter.id = myown.$flowid;
	// alert(parameter.yid);
	myown.$jsonParameter = mini.encode(parameter);
}

function beforeSaveAction(obj) {
	var year = obj.GetValueEx("year", 2, "", 0, "");
	if (year < 1990 || year > 2050) {
		alert("预算年度超出范围（1990~2050）！");
		return 0;
	}
	var money = obj.GetValueEx("budMoney", 2, "", 0, "");
	var warnCost = obj.GetValueEx("warnCost", 2, "", 0, "");
	if (warnCost - money > 0) {
		alert("预警量不能大于预算金额！");
		return 0;
	}

	var apply_date = obj.GetValueEx("apply_date", 2, "", 0, "");
	if (!apply_date || apply_date == '') {
		alert("请输入申请日期！");
		return 0;
	}

	return 1;
}

// 选择发送对象
function selectOrg(idfield, namefield) {
	var url = mybaseUrl + "/public/toOrgSelectorPage.page";
	var orgId = myaipObj.GetValueEx(idfield, 2, "", 0, "");
	var orgName = myaipObj.GetValueEx(namefield, 2, "", 0, "");
	vRet = window
			.showModalDialog(
					url,
					{
						orgId : orgId,
						orgName : orgName
					},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:500px;dialogWidth:300px");
	if (vRet) {
		myaipObj.SetValue(idfield, "");
		myaipObj.SetValue(idfield, vRet.orgid);
		myaipObj.SetValue(namefield, "");
		myaipObj.SetValue(namefield, vRet.name);
	}
	myaipObj.jsValue = 0;
}

// 上传附件
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
// 附件下载
function download(fieldName) {
	var attachfile = myaipObj.GetValueEx(fieldName, 2, "", 0, "");
	if (attachfile != "") {
		window.location.href = mybaseUrl
				+ "/fileUploadDown/fileDownLoad.action?1=1&realName="
				+ encodeURIComponent(encodeURIComponent(attachfile));
	}
	myaipObj.jsValue = 0;
}
