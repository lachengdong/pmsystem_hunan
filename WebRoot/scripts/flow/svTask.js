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
	myown = own; 
}

function myaipAction(lActionType, lType, strName, strValue, aipObj) {
	if (strName == "mainperson") {// 主送
		selectAcceptUser("mainpersonids", "mainperson");
	} else if (strName == "helpperson") {// 抄送
		selectAcceptUser("helppersonids", "helpperson");
	} else if (strName == "leader") {
		selectAcceptUser("leaderids", "leader");
	}
}

// 选择发送对象
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

function doSetTransactors() {
	myown.$assigners = [];
	setAssigner(myown.$assigners, "leaderids", "leader");
	setAssigner(myown.$assigners, "mainpersonids", "mainperson");
	myown.$className = 'sVTaskService'; // spring中类名
	myown.$methodName = 'add';
	myown.$classtype = "com.sinog2c.service.api.supervise.SVTaskService";
	var parameter = {
		flowid:'',
		authorId : '',
		title : '',
		content : '',
		userLeader : '',
		userMain : '',
		userHelp : '',
		beginTime : '',
		endTime : ''
	};
	parameter.flowid = myown.$flowid;
	parameter.authorId = myown.$userid;
	parameter.title = myaipObj.GetValueEx("workproject", 2, "", 0, "");
	parameter.content = myaipObj.GetValueEx("description", 2, "", 0, "");
	parameter.userLeader = myaipObj.GetValueEx("leaderids", 2, "", 0, "");
	parameter.userMain = myaipObj.GetValueEx("mainpersonids", 2, "", 0, "");
	parameter.userHelp = myaipObj.GetValueEx("helppersonids", 2, "", 0, "");
	parameter.beginTime = getDate("starttime");
	parameter.endTime = getDate("endtime");	
	myown.$jsonParameter = mini.encode(parameter);
}

function beforeSaveAction(obj) {
	var userMain = obj.GetValueEx("mainperson", 2, "", 0, "");
	if (userMain == "") {
		alert("请输入主办人！");
		return 0;	
	}	
	return 1;
}

function setAssigner(assigners, idfield, namefield) {
	var suserids = myaipObj.GetValueEx(idfield, 2, "", 0, "");
	var suserNames = myaipObj.GetValueEx(namefield, 2, "", 0, "");
	var userids = suserids.split(';');
	var userNames = suserNames.split(';');
	for (var i = 0; i < userids.length - 1; i++) {
		if (isExist(assigners, userids[i]))
			continue;
		assigners[assigners.length] = {
			userid : userids[i],
			name : userNames[i]
		};
	}
}

function isExist(assigners, id) {
	for (var i = 0; i < assigners.length; i++) {
		if (assigners[i].userid == id)
			return true;
	}
	return false;
}

function getDate(field) {
	var txtdata = myaipObj.GetValueEx(field, 2, "", 0, "");
	txtdata = txtdata.replace('年', '-');
	txtdata = txtdata.replace('月', '-');
	txtdata = txtdata.replace('日', '');
	var d = mini.parseDate(txtdata);
	if (d)
		return Date.UTC(d.getFullYear(), d.getMonth(), d.getDate());
	else
		return null;
}
