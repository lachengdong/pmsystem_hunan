var basePath = "";
if(document.getElementById("basePath")) basePath = document.getElementById("basePath").value;
//办案
function operationOpprove(resid,isagree,opionval){
	var commenttext = '';
	var action = 'ok';;
	var jumptype;
	var suggest2;
	var orgid = mini.get("orgid").getValue();
	var conent = mini.get("conent").getValue();;
	var flowid = mini.get("flowid").getValue();
	var flowdefid = mini.get("flowdefid").getValue();
	var flowdraftid = mini.get("flowdraftid").getValue();
	var winid;
	var aipObj=document.getElementById("HWPostil1");
	var applyid = aipObj.GetValueEx("crimid", 2, "", 0, "");
	if(opionval.indexOf("win=")!=-1) winid = opionval.split("_")[0].substr(4); 
	
	if(isagree == 'no'){
		//普通业务意见审批
		var selecturl = basePath+"/toapproveview.action?1=1&flowdefid="+flowdefid+"&operateType="+isagree+"&winid="+winid+"&crimid="+applyid;
		var vRet = window.showModalDialog(selecturl,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:200px;dialogWidth:500px");
    	if(vRet){ 
			action = vRet[0]; 
			commenttext = vRet[1];
		}
	}else{
		commenttext = '同意';
		if(opionval.indexOf("win=")!=-1) commenttext = ''; 
		action = 'ok';
	}
	
	if(action == 'ok'){
   		jumpsave(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval);
   	}
}
//js 转换日期格式
function formatDate(date){  
	var year = date.getFullYear();
	var yue = date.getMonth()+1;
	var day = date.getDate();
	var str = (1900+year) +(yue>9?yue.toString():'0' + yue) + (day>9?day.toString():'0' + day);
   
    return str;    
} 
//普通业务意见审批
function jumpsave(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval){
	var aipObj=document.getElementById("HWPostil1");
	var tempid = document.getElementById("tempid").value;
	if(opionval){//填写意见的字段和日期  name_date
		var val = opionval.split(';');
		if(val){
			for(var i=0;i<val.length;i++){
				if(val.indexOf("_") != -1){
					var names = val[i].split('_');
					var value = aipObj.GetValueEx(names[0], 2, "", 0, "");
					if(value && (isagree == 'yes' || isagree == 'end')){
						commenttext = value;
					}
					//alert("names:"+names+",value="+value+",commenttext:"+commenttext);
					aipObj.SetValue(names[0],"");
					aipObj.SetValue(names[0],commenttext);
					aipObj.SetValue(names[1],"");
					aipObj.SetValue(names[1],formatDate(new Date()));
				}
			}
		}
	}
	doApprove(resid,isagree,flowdefid,tempid);
}