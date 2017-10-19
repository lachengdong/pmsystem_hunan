/**
 * 
 */
// 外出申请页面脚本
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
		//获取的是申请人的名字，存的是申请人的id
		myaipObj.SetValue("applyname", myown.$userName);
		myaipObj.SetValue("departid",myown.$orgName);
		myaipObj.SetValue("applyreid", myown.$userid);
	}
}

function myaipAction(lActionType, lType, strName, strValue, aipObj) {
/*	if (myaipObj.GetValueEx("RadioBox3", 2, "", 0, "")!="公交") {
		alert("请填写金额");
		 
    }*/
}




function doAddLeaveInfo() {
	myown.$className = "oaWcspService"; // spring中类名
	myown.$methodName = "add";
	myown.$classtype = "com.sinog2c.service.api.oa.OaWcspService";
	var parameter = {  
			uuid:'',
			departid:'',
			applyname: '',
			apllycodeid: '',
			applyreid: '',
			applybossid: '',
			vehicleid:'',
			achievement:'',
			Issuccessid:'',
			managersid:'',
			reason:'',
			money:'',
			gocompany:'',
			story:'',
			linkman:'',
			linkway:'',
			sqdate:'',
			outtime:'',
			goback:'',
			bosssign:'',
			wcdate:'',
			fhdate:'',
			text1:'',
			text2:'',
			text3:'',
			text4:'',
			text5:'',
			text6:'',
			int1:'',
			int2:'',
			optime:'',
			opid:''
	};
	
	parameter.departid = myaipObj.GetValueEx("departid", 2, "", 0, "");
	parameter.applyname = myaipObj.GetValueEx("applyname", 2, "", 0, "");
	parameter.apllycodeid = myaipObj.GetValueEx("apllycodeid", 2, "", 0, "");
	parameter.sqdate = getDate("sqdate");
	parameter.applyreid = myaipObj.GetValueEx("applyreid", 2, "", 0, "");
	parameter.applybossid = myaipObj.GetValueEx("applybossid", 2, "", 0, "");
	parameter.outtime = getDate("outtime");
	parameter.vehicleid = myaipObj.GetValueEx("vehicleid", 2, "", 0, "");
	parameter.achievement = myaipObj.GetValueEx("achievement", 2, "", 0, "");
	parameter.goback = getDate("goback");
	parameter.managersid = myaipObj.GetValueEx("managersid", 2, "", 0, "");
	parameter.reason = myaipObj.GetValueEx("reason", 2, "", 0, "");
	parameter.money = myaipObj.GetValueEx("money", 2, "", 0, "");
	parameter.gocompany = myaipObj.GetValueEx("gocompany", 2, "", 0, "");
	parameter.story = myaipObj.GetValueEx("story", 2, "", 0, "");
	parameter.linkman = myaipObj.GetValueEx("linkman", 2, "", 0, "");
	parameter.linkway = myaipObj.GetValueEx("linkway", 2, "", 0, "");
	parameter.bosssign = myaipObj.GetValueEx("bosssign", 2, "", 0, "");
	parameter.wcdate = myaipObj.GetValueEx("wcdate", 2, "", 0, "");
	parameter.fhdate = myaipObj.GetValueEx("fhdate", 2, "", 0, "");
	parameter.opid = myown.$userid;
	
//	
//	parameter.opid = myown.$userid;
//	/*var leavetype = myaipObj.GetValueEx("Page1.RadioBox", 2, "", 0, "");
//	if(leavetype =='休假'){
//		parameter.leavetype =1
//	}else if(leavetype =='事假'){
//		parameter.leavetype =2
//	}else if(leavetype =='病假'){
//		parameter.leavetype =3
//	}else if(leavetype =='调休假'){
//		parameter.leavetype =4
//	}else if(leavetype=='其他'){
//		parameter.leavetype =5
//	}*/
	var leaveway = myaipObj.GetValueEx("RadioBox1", 2, "", 0, "");
	if(leaveway =='提前申请'){
		parameter.apllycodeid ="1";
	}else {
		parameter.apllycodeid ="2";
	}
	
	var leaveway2 = myaipObj.GetValueEx("RadioBox6", 2, "", 0, "");
	if(leaveway2 =='任务完成'){
		parameter.issuccessid ="1";
	}else{
		parameter.issuccessid ="2";
	}
	var leaveway3 = myaipObj.GetValueEx("RadioBox3", 2, "", 0, "");
	if(leaveway3 =='公交'){
		parameter.vehicleid  ="1";
		parameter.money=0;
	}else{
		parameter.vehicleid  ="2";
	}
	
//	
//	
//	
//	//请假时间处理
//	var time1 = myaipObj.GetValueEx("Page1.leaveTime1", 2, "", 0, "");
//	var time2 = myaipObj.GetValueEx("Page1.leaveTime2", 2, "", 0, "");
//	var leavedate1 = myaipObj.GetValueEx("Page1.leaveDate1", 2, "", 0, "");
//	var leavedate2 = myaipObj.GetValueEx("Page1.leaveDate2", 2, "", 0, "");
//	//调整日期格式
//	if(time1!=""&&time1>0){
//		if(time1<10){
//			time1="0"+time1;
//		}
//		leavedate1 = leavedate1+" "+time1;
//	}
//	if(time2!=""&&time2>0){
//		if(time2<10){
//			time2="0"+time2;
//		}
//		leavedate2 = leavedate2+" "+time2;
//	}
//	parameter.leavedate1=leavedate1;
//	parameter.leavedate2=leavedate2;
	myown.$jsonParameter = mini.encode(parameter);
	
}


//提示哪些不能为空
function beforeSaveAction(obj) {
	var departid = obj.GetValueEx("departid", 2, "", 0, "");
	var applyname = obj.GetValueEx("applyname", 2, "", 0, "");
	var RadioBox1 = obj.GetValueEx("RadioBox1", 2, "", 0, "");
	var RadioBox3 = obj.GetValueEx("RadioBox3", 2, "", 0, "");
	var applyreid = obj.GetValueEx("applyreid", 2, "", 0, "");
	var applybossid = obj.GetValueEx("applybossid", 2, "", 0, "");
	var RadioBox6 = obj.GetValueEx("RadioBox6", 2, "", 0, "");
	var managersid = obj.GetValueEx("managersid", 2, "", 0, "");
	var money = obj.GetValueEx("money", 2, "", 0, "");
	var gocompany = obj.GetValueEx("gocompany", 2, "", 0, "");
	var story = obj.GetValueEx("story", 2, "", 0, "");
	var sqdate = obj.GetValueEx("sqdate", 2, "", 0, "");
	var outtime = obj.GetValueEx("outtime", 2, "", 0, "");
	var goback = obj.GetValueEx("goback", 2, "", 0, "");
	var bosssign = obj.GetValueEx("bosssign", 2, "", 0, "");
	var wcdate = obj.GetValueEx("wcdate", 2, "", 0, "");
	var fhdate = obj.GetValueEx("fhdate", 2, "", 0, "");
	var linkman = obj.GetValueEx("linkman", 2, "", 0, "");
	var linkway = obj.GetValueEx("linkway", 2, "", 0, "");
	var reason = obj.GetValueEx("reason", 2, "", 0, "");
	var achievement = obj.GetValueEx("achievement", 2, "", 0, "");
	if (departid == "") {
		alert("部门不能为空");
		return 0;
	}	
	if (RadioBox1 == "") {
		alert("申请方式不能为空");
		return 0;
	}	
	if (gocompany == "") {
		alert("去往单位不能为空");
		return 0;
	}
	if (story == "") {
		alert("请填写事由");
		return 0;
	}
	if (linkman == "") {
		alert("联系人不能为空");
		return 0;
	}
	if (linkway == "") {
		alert("联系方式不能为空");
		return 0;
	}
	if (sqdate == "") {
		alert("请填写日期");
		return 0;
	}
	if (outtime == "") {
		alert("请选择外出时间");
		return 0;
	}
/*	if (applybossid == "") {
		alert("批准领导不能为空");
		return 0;
	}*/
	if (wcdate == "") {
		alert("请填写外出时刻");
		return 0;
	}
	if (RadioBox3 == "") {
		alert("请选择交通工具");
		return 0;
	}
	if (RadioBox3 != "公交" && money == "" ) {
		alert("请填写金额");
		return 0;
	}
	if (goback == "") {
		alert("请选择返回日期");
		return 0;
	}
	if (fhdate == "") {
		alert("请填写返回时刻");
		return 0;
	}
	if (achievement == "") {
		alert("请填写成果");
		return 0;
	}
	if (RadioBox6 == "") {
		alert("请选择是否完成");
		return 0;
	}	
	if (RadioBox6 != "任务完成" && reason == "") {
		alert("请填写未完成原因");
		return 0;
	}
	/*if (managersid == "") {
		alert("主管领导不能为空");
		return 0;
	}*/
	return 1;
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








