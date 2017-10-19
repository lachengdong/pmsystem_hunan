/**
 * 
 */
// 还款流程附加脚本
var myaipObj = null;
var myown = null;
var mybaseUrl = '';
var action = '';
var leavetype;
var leaveway;
var bybdid;

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
    bybdid = top["win"].getHiddenValue("bybdid");
	if (action == 'new') {
		$.ajax({
			url : mybaseUrl + "/leave/getLeaveInfo.json?bybdid="+bybdid,
			type : "POST",
			dataType : "json",
			async : false,
			success : function(data) {
				myaipObj.SetValue("department", myown.$orgName);
				myaipObj.SetValue("name", myown.$userName);
				myaipObj.SetValue("proposer", myown.$userName);
				myaipObj.SetValue("proposerId", data.proposerid);
				if(data.leavetype ==1){
					leavetype ='休假'
				}else if(data.leavetype ==2){
					leavetype ='事假'
				}else if(data.leavetype ==3){
					leavetype ='病假'
				}else if(data.leavetype ==4){
					leavetype ='调休假'
				}else if(data.leavetype==5){
					leavetype ='其他'
				}
				if(data.leaveway ==1){
					leaveway ='提前请假'
				}else if(data.leaveway==2){
					leaveway ='电话请假'
				}
				myaipObj.SetValue("Page1.bybdid",bybdid);
				myaipObj.SetValue("Page1.proDate",data.prodate);
				myaipObj.SetValue("RadioBox", leavetype);
				myaipObj.SetValue("RadioBox2", leaveway);
				myaipObj.SetValue("reason", data.reason);
				myaipObj.SetValue("leaveDate1", data.leavedate1);
				myaipObj.SetValue("leaveDate2", data.leavedate2);
				myaipObj.SetValue("days", data.days);
				myaipObj.SetValue("hours", data.hours);
				myaipObj.SetValue("leaveTime1", data.leavetime1);
				myaipObj.SetValue("leaveTime2", data.leavetime2);
				myaipObj.SetValue("depManApprover", data.depmanname);
				myaipObj.SetValue("depManAdvice", data.depmanadvice);
				myaipObj.SetValue("depManDate", data.depmandate);
				myaipObj.SetValue("personnelApprover", data.personnelname);
				myaipObj.SetValue("personnelAdvice", data.perosonneladvice);
				myaipObj.SetValue("personnelDate", data.personneldate);
				myaipObj.SetValue("partManApprover", data.partmanname);
				myaipObj.SetValue("partManAdvice", data.partmanadvice);
				myaipObj.SetValue("partManDate", data.partmandate);
				myaipObj.SetValue("genManApprover", data.genmanname);
				myaipObj.SetValue("genManAdvice", data.genmanadvice);
				myaipObj.SetValue("genManDate", data.genmandate);
				myaipObj.SetValue("jobs", data.jobs);
				
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("获取请假单信息出错！");
			}
		});
	}
}

function myaipAction(lActionType, lType, strName, strValue, aipObj) {
}



function doAddSickLeaveInfo() {
	myown.$className = "oaSickLeaveService"; // spring中类名
	myown.$methodName = "save";
	myown.$classtype = "com.sinog2c.service.api.oa.OaSickLeaveService";
	var parameter = {
		xjproposerid:'',
		qjdid: '',
		qjdate1: '',
		qjdate2: '',
		qjtime1: '',
		qjtime2: '',
		qjdays: '',
		qjhours: '',
		xjdate: '',
		xjnote: '',
		mannagerid: ''
	};
	parameter.qjdid = bybdid;
	parameter.xjproposerid = myaipObj.GetValueEx("Page1.proposerId", 2, "", 0, "");
	parameter.qjdate1 = getDate("Page2.qjDate1");
	parameter.qjdate2 = getDate("Page2.qjDate2");
	parameter.xjdate = getDate("Page2.xjDate");
	parameter.qjtime1 = myaipObj.GetValueEx("Page2.qjTime1", 2, "", 0, "");
	parameter.qjtime2 = myaipObj.GetValueEx("Page2.qjTime1", 2, "", 0, "");
	parameter.qjdays = myaipObj.GetValueEx("Page2.qjDays", 2, "", 0, "");
	parameter.qjhours = myaipObj.GetValueEx("Page2.qjHours", 2, "", 0, "");
	parameter.xjnote = myaipObj.GetValueEx("Page2.xjNote", 2, "", 0, "");
	parameter.mannagerid = myaipObj.GetValueEx("Page2.mannagerId", 2, "", 0, "");
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
