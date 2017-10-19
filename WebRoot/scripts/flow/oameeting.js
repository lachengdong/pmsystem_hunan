/**
 * 
 */
//会议管理页面操作
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
	
}



function myaipAction(lActionType, lType, strName, strValue, aipObj) {
	 if (strName == "text4") {//出席人员（内部）
		selectAcceptUser("joinpers", "text4");
	}
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
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:560px;dialogWidth:900px");
	if (vRet) {
		myaipObj.SetValue(idfield, "");
		myaipObj.SetValue(idfield, vRet.userids);
		myaipObj.SetValue(namefield, "");
		myaipObj.SetValue(namefield, vRet.userNames);
	}
	myaipObj.jsValue = 0;
}



