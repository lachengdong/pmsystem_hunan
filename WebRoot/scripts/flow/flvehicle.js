/**
 * 
 */
// 车辆申请页面脚本
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
		myaipObj.SetValue("vehicleuser", myown.$userName);
		myaipObj.SetValue("userdept", myown.$orgName);
	}
}


function myaipAction(lActionType, lType, strName, strValue, aipObj) {

	
}

function doUpdateVehicleState()
{
	myown.$className='oAVehicleInfoService';  //spring中类名
	myown.$methodName='updatevehiclestatebyidk';	
	myown.$classtype="com.sinog2c.service.api.vehicle.OAVehicleInfoService";
	var parameter={vehicleidk:'',state:'2'};	
	parameter.vehicleidk=myaipObj.GetValueEx("vehicleidk", 2, "", 0, "");
	myown.$jsonParameter=mini.encode(parameter);
	
}


function doUpdateVehicleState2()
{
	myown.$className='oAVehicleInfoService';  //spring中类名
	myown.$methodName='updatevehiclestatebyidk';	
	myown.$classtype="com.sinog2c.service.api.vehicle.OAVehicleInfoService";
	var parameter={vehicleidk:'',state:'1'};	
	parameter.vehicleidk=myaipObj.GetValueEx("vehicleidk", 2, "", 0, "");
	myown.$jsonParameter=mini.encode(parameter);
	
}



