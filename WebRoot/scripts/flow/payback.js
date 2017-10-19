/**
 * 
 */
// 还款流程附加脚本
var myaipObj = null;
var myown = null;
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
	// 页面数据初始化
	if (action == 'new') {
		myaipObj.SetValue("operator", myown.$userName);
		myaipObj.SetValue("dept_name", myown.$orgName);
		myaipObj.SetValue("public1", "借款单信息");
		// setBillNo();
		var loanInfo = mini.decode(parent.getExtentInfo());

		var date = new Date(loanInfo.borrowDate);	
		myaipObj.SetValue("borrow_person", loanInfo.borrowPerson);
		myaipObj.SetValue("money_borrow", loanInfo.moneyApprove);
		myaipObj.SetValue("bill_flowid", loanInfo.flowid);
		myaipObj.SetValue("billid", loanInfo.billid);
		//
	}
}

function myaipAction(lActionType, lType, strName, strValue, aipObj) {

	if (strName == "public1") {
		showLoanInfo();
	}
	if (strName == "money_upper" || strName == "money_lower") {
		doSetMoney();
	}
}

function showLoanInfo() {
	var flowid = myaipObj.GetValueEx("bill_flowid", 2, "", 0, "");
	var url = mybaseUrl + "/loan/toloanInfo.page?flowid=" + flowid;
	vRet = window
			.showModalDialog(
					url,
					{},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:800px;dialogWidth:900px");

	myaipObj.jsValue = 0;
}

function doSetMoney() {
	var url = mybaseUrl + "/public/topage.action?viewName=loan/expenseInput";
	var money = myaipObj.GetValueEx("money_lower", 2, "", 0, "");
	var money_borrow = myaipObj.GetValueEx("money_borrow", 2, "", 0, "");
	vRet = window
			.showModalDialog(
					url,
					{
						money : money,
						money_borrow : money_borrow
					},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:200px;dialogWidth:400px");
	if (vRet) {
		myaipObj.SetValue("balancedue", "");
		myaipObj.SetValue("balancedue", vRet.balancedue);
		myaipObj.SetValue("money_upper", "");
		myaipObj.SetValue("money_upper", vRet.money);
		myaipObj.SetValue("money_lower", "");
		myaipObj.SetValue("money_lower", vRet.money);
	}
	myaipObj.jsValue = 0;
}

function doUpldateLoanInfo0(state) {
	myown.$className = 'oALoanService'; // spring中类名
	myown.$methodName = 'updateLoanStatus';
	myown.$classtype = "com.sinog2c.service.api.budget.OALoanService";
	var parameter = {
		billid : '',
		ispayback : state
	};
	parameter.billid = myaipObj.GetValueEx("billid", 2, "", 0, "");
	myown.$jsonParameter = mini.encode(parameter);
}

function doUpldateLoanInfo() {
	doUpldateLoanInfo0(2);
}

function dopayback()
{
	myown.$className = 'oALoanService'; // spring中类名
	myown.$methodName = 'payback';
	myown.$classtype = "com.sinog2c.service.api.budget.OALoanService";

	var parameter = {
		payPerson:'',		
		billid : '',
		borrowPerson:'',
		moneyPay : '',
		datePay:''
			
	};
	parameter.payPerson = myaipObj.GetValueEx("operator", 2, "", 0, "");	
	parameter.billid = myaipObj.GetValueEx("billid", 2, "", 0, "");
	parameter.borrowPerson = myaipObj.GetValueEx("borrow_person", 2, "", 0, "");
	parameter.moneyPay = myaipObj.GetValueEx("money_approve", 2, "", 0, "");
	parameter.datePay =  getDate("date");
	myown.$jsonParameter = mini.encode(parameter);		
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
