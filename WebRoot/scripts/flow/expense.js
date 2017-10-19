/**
 * 
 */
// 报销流程附加脚本
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
		//setBillNo();
	}
}


function myaipAction(lActionType, lType, strName, strValue, aipObj) {
	if (strName == "Page1.project" || strName == "Page1.money_upper"
			|| strName == "Page1.money_lower") {
		doSetProject();
	}
}

function doSetProject() {
	var url = mybaseUrl + "/loan/toExpenseProject.page?1=1";
	var project_id = myaipObj.GetValueEx("project_id", 2, "", 0, "");
	var money = myaipObj.GetValueEx("money_lower", 2, "", 0, "");
	var balance = myaipObj.GetValueEx("balance", 2, "", 0, "");
	vRet = window
			.showModalDialog(
					url,
					{
						project_id : project_id,
						money : money,
						balance : balance
					},
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:200px;dialogWidth:400px");
	if (vRet) {
		myaipObj.SetValue("project", "");
		myaipObj.SetValue("project", vRet.projectName);
		myaipObj.SetValue("project_id", "");
		myaipObj.SetValue("project_id", vRet.project_id);
		myaipObj.SetValue("balance", "");
		myaipObj.SetValue("balance", vRet.balance);
		myaipObj.SetValue("money_upper", "");
		myaipObj.SetValue("money_upper", vRet.money);
		myaipObj.SetValue("money_lower", "");
		myaipObj.SetValue("money_lower", vRet.money);
	}
	myaipObj.jsValue = 0;
}

function doAddLoanInfo() {
	myown.$className = 'oALoanService'; // spring中类名
	myown.$methodName = 'add';
	myown.$classtype = "com.sinog2c.service.api.budget.OALoanService";
	var parameter = {
		billid : '',
		borrowPerson : '',
		deptName : '',
		projectId : '',
		project : '',
		standar : '',
		expense : '',
		money : '',
		moneyApprove : '',
		feeType : '',
		payType : '',
		explanation : '',
		deptPrincipal : '',
		chargeLeader : '',
		financeLeader : '',
		mainLeader : '',
		borrowDate : '',
		flowid : ''

	};
	parameter.flowid = myown.$flowid;
	parameter.borrowDate = getDate("borrow_date");
	parameter.billid = myaipObj.GetValueEx("bill_no", 2, "", 0, "");
	parameter.borrowPerson = myaipObj.GetValueEx("borrow_person", 2, "", 0, "");
	parameter.deptName = myaipObj.GetValueEx("dept_name", 2, "", 0, "");
	parameter.projectId = myaipObj.GetValueEx("project_id", 2, "", 0, "");
	parameter.project = myaipObj.GetValueEx("project", 2, "", 0, "");
	parameter.standar = myaipObj.GetValueEx("standar", 2, "", 0, "");
	parameter.expense = myaipObj.GetValueEx("expense", 2, "", 0, "");
	parameter.money = myaipObj.GetValueEx("money_lower", 2, "", 0, "");
	parameter.moneyApprove = myaipObj.GetValueEx("money_approve", 2, "", 0, "");
	parameter.feeType = myaipObj.GetValueEx("fee_type", 2, "", 0, "");
	parameter.payType = myaipObj.GetValueEx("pay_type", 2, "", 0, "");
	parameter.explanation = myaipObj.GetValueEx("explanation", 2, "", 0, "");
	parameter.deptPrincipal = myaipObj.GetValueEx("dept_principal", 2, "", 0,
			"");
	parameter.chargeLeader = myaipObj.GetValueEx("charge_leader", 2, "", 0, "");
	parameter.financeLeader = myaipObj.GetValueEx("finance_leader", 2, "", 0,
			"");
	parameter.mainLeader = myaipObj.GetValueEx("main_leader", 2, "", 0, "");
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
