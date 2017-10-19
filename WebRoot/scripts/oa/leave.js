/**
 * 
 */
// 借款申请页面脚本
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
		myaipObj.SetValue("name", myown.$userName);
		myaipObj.SetValue("department", myown.$orgName);
		myaipObj.SetValue("jobs", myown.$duty);
	}
}

function myaipAction(lActionType, lType, strName, strValue, aipObj) {
	if (strName == "Page1.reason") {
		myaipObj.SetValue("proposerId","");
		myaipObj.SetValue("proposerId",myown.$userid);
    }
	if (strName == "Page1.depManAdvice") {
		myaipObj.SetValue("depManId","");
		myaipObj.SetValue("depManId",myown.$userid);
    }
	if (strName == "Page1.personnelAdvice") {
		myaipObj.SetValue("personnelId","");
		myaipObj.SetValue("personnelId",myown.$userid);
    }
	if (strName == "Page1.partManAdvice") {
	    myaipObj.SetValue("partManId","");
		myaipObj.SetValue("partManId",myown.$userid);
    }
	if (strName == "Page1.genManAdvice") {
		myaipObj.SetValue("genManId","");
		myaipObj.SetValue("genManId",myown.$userid);
    }
}
function beforeSaveAction(myown) {
	var leaveType = myown.GetValueEx("Page1.RadioBox", 2, "", 0, "");
	var leaveWay = myown.GetValueEx("Page1.RadioBox2", 2, "", 0, "");
	var reason = myown.GetValueEx("Page1.reason", 2, "", 0, "");
	var proposer = myown.GetValueEx("Page1.proposer", 2, "", 0, "");
	var proDate = myown.GetValueEx("Page1.proDate", 2, "", 0, "");
	var leaveDate1 = myown.GetValueEx("Page1.leaveDate1", 2, "", 0, "");
	var leaveDate2 = myown.GetValueEx("Page1.leaveDate2", 2, "", 0, "");
	var d1 =leaveDate1.substring(0,leaveDate1.indexOf("年")) + leaveDate1.substring(leaveDate1.indexOf("年")+1,leaveDate1.indexOf("月"))+leaveDate1.substring(leaveDate1.indexOf("月")+1,leaveDate1.indexOf("日")) ;
	var d2 =leaveDate2.substring(0,leaveDate2.indexOf("年")) + leaveDate2.substring(leaveDate2.indexOf("年")+1,leaveDate2.indexOf("月"))+leaveDate2.substring(leaveDate2.indexOf("月")+1,leaveDate2.indexOf("日")) ;
	if(d1!=""&&d2!=""){
		if(d1>d2){
			alert("请确认时间范围");
	 		return 0;
	    }
	}
	if (leaveType == ""||leaveWay == ""||reason == ""||proposer == ""||proDate == ""||leaveDate1 == "") {
		alert("表单填写不完全！");
		return 0;
	}	
	return 1;
}
//获取表单节点和其对应的节点属性值
function getNoteMap() {
	var aipObj = document.getElementById("HWPostil1");
	var NoteInfo;
	//var notemap = new Map();
	var notemap = {};
	while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
		var temp = NoteInfo.split(".")[1];
		var value = aipObj.GetValueEx(NoteInfo, 2, "", 0, "");
		//notemap.put(temp, value);
		notemap[temp] = value;
	}
	return "["+mini.encode(notemap)+"]";
}
function doAddLeaveInfo() {
	myown.$className = 'oaLeaveService'; // spring中类名
	myown.$methodName = 'save';
	myown.$classtype = "com.sinog2c.service.api.oa.OaLeaveService";
	var parameter = {
			uuid:'',
			proposerid:'',
			department: '',
			reason: '',
			leavetype: '',
			leaveway: '',
			zjldsign: '',
			prodate:'',
			leavedate1:'',
			leavedate2:'',
			leavetime1:'',
			leavetime2:'',
			days:'',
			hours:'',
			depmanapproverid:'',
			depmanadvice:'',
			depmandate:'',
			personnelapproverid:'',
			personneladvice:'',
			personneldate:'',
			partmanapproverid:'',
			partmanadvice:'',
			partmandate:'',
			genmanapproverid:'',
			genmanadvice:'',
			genmandate:'',
			text1:'',
			text1:'',
			text2:'',
			text3:'',
			text4:'',
			text5:'',
			text6:'',
			int1:'',
			int1:'',
			optime:'',
			opid:''
			
	};
	
	parameter.proposerid = myaipObj.GetValueEx("Page1.proposerId", 2, "", 0, "");
	parameter.department = myaipObj.GetValueEx("Page1.department", 2, "", 0, "");
	parameter.reason = myaipObj.GetValueEx("Page1.reason", 2, "", 0, "");
	parameter.zjldsign = myaipObj.GetValueEx("Page1.zjldSign", 2, "", 0, "");
	parameter.prodate = getDate("Page1.proDate");
	parameter.text1 = myaipObj.GetValueEx("Page1.jobs", 2, "", 0, "");
	parameter.text2 = myaipObj.GetValueEx("Page1.name", 2, "", 0, "");
	parameter.text3 = myaipObj.GetValueEx("Page1.dapartment", 2, "", 0, "");
	parameter.text4 = getNoteMap();
	var leavetype = myaipObj.GetValueEx("Page1.RadioBox", 2, "", 0, "");
	if(leavetype =='休假'){
		parameter.leavetype =1
	}else if(leavetype =='事假'){
		parameter.leavetype =2
	}else if(leavetype =='病假'){
		parameter.leavetype =3
	}else if(leavetype =='调休假'){
		parameter.leavetype =4
	}else if(leavetype =='其他'){
		parameter.leavetype =5
	}
	var leaveway = myaipObj.GetValueEx("Page1.RadioBox2", 2, "", 0, "");
	if(leaveway =='提前请假'){
		parameter.leaveway =1
	}else if(leaveway =='电话请假'){
		parameter.leaveway =2
	}
	//请假时间处理
	var time1 = myaipObj.GetValueEx("Page1.leaveTime1", 2, "", 0, "");
	var time2 = myaipObj.GetValueEx("Page1.leaveTime2", 2, "", 0, "");
	
	//调整日期格式
	if(time1!=""&&time1>0){
		if(time1<10){
			time1="0"+time1;
		}
	}
	if(time2!=""&&time2>0){
		if(time2<10){
			time2="0"+time2;
		}
	}
	parameter.leavetime1=time1;
	parameter.leavetime2=time2;
	parameter.leavedate1 = getDate("Page1.leaveDate1");
	parameter.leavedate2 = getDate("Page1.leaveDate2");
	parameter.days = myaipObj.GetValueEx("Page1.days", 2, "", 0, "");
	parameter.hours = myaipObj.GetValueEx("Page1.hours", 2, "", 0, "");
	
	parameter.depmanapproverid = myaipObj.GetValueEx("Page1.depManId", 2, "", 0, "");
	parameter.depmanadvice = myaipObj.GetValueEx("Page1.depManAdvice", 2, "", 0, "");
	parameter.depmandate = getDate("Page1.depManDate");
	
	parameter.personnelapproverid = myaipObj.GetValueEx("Page1.personnelId", 2, "", 0, "");
	parameter.personneladvice = myaipObj.GetValueEx("Page1.personnelAdvice", 2, "", 0, "");
	parameter.personneldate = getDate("Page1.personnelDate");
	
	parameter.partmanapproverid = myaipObj.GetValueEx("Page1.partManId", 2, "", 0, "");
	parameter.partmanadvice = myaipObj.GetValueEx("Page1.partManAdvice", 2, "", 0, "");
	parameter.partmandate = getDate("Page1.partManDate");
	
	parameter.genmanapproverid = myaipObj.GetValueEx("Page1.genManId", 2, "", 0, "");
	parameter.genmanadvice = myaipObj.GetValueEx("Page1.genManAdvice", 2, "", 0, "");
	parameter.genmandate = getDate("Page1.genManDate");
	parameter.opid = myown.$userid;
	
	
	myown.$jsonParameter = mini.encode(parameter);
	
}

function getDate(field) {
	var txtdata = myaipObj.GetValueEx(field, 2, "", 0, "");
	if(txtdata!=""){
	    var year =txtdata.substring(0,txtdata.indexOf("年")) ;
	    var month=txtdata.substring(txtdata.indexOf("年")+1,txtdata.indexOf("月")) ;
	    var day=txtdata.substring(txtdata.indexOf("月")+1,txtdata.indexOf("日")) ;
	    if(month<10&&month>0){
	    	month= "0"+month;
	    }
	    if(day<10&&day>0){
	    	day= "0"+day;
	    }
	    return year+""+month+""+day;
	}
	else
		return "";
}
