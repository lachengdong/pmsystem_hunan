mini.parse();

function GetData(){
 	var data = {};
    var outputparam = mini.get("outputparam").getValue();
    var outputparamArr = outputparam.split(',');
    for(var i=0,l=outputparamArr.length; i<l; i++){
    	var key = outputparamArr[i];
    	var value = mini.get(key).getValue();
    	data[key] = value;
    }
    return data;
 }
 
 function SetData(data){
 	data = mini.clone(data);
 	mini.get("outputparam").setValue(data.outputparam);
 	var inputparamData = data.inputparamData;
 	var inputparam = data.inputparam;
 	mini.get("inputparam").setValue(inputparam);
 	if(inputparam){
 		inputparamData = mini.decode(inputparamData);
 		var inputparamArr = inputparam.split(',');
	 	for(var i=0,l = inputparamArr.length; i<l ; i++){
	 		var key = inputparamArr[i];
	 		var value = inputparamData[key];
	 		mini.get(key).setValue(value);
	 	}
 	}
 	beforeOperate();
 	
}

/*
function GetData(){
    var outputparam = mini.get("outputparam").getValue();
    var outputparamArr = outputparam.split(',');
    var data = {};
    for(var i=0,l=outputparamArr.length;i<l;i++){
    	var key = outputparamArr[i];
    	data[key] = mini.get(key).getValue();
    }
    return data;
}

function SetData(data){
    data = mini.clone(data);
    var inputparamArr = mini.decode(data.inputparamData);
    for(var key in inputparamArr){
    	mini.get(key).setValue(inputparamArr[key]);
    }
    var outputparam = data.outputparam;
    mini.get("outputparam").setValue(outputparam);
}
*/
   
function onOk(){
	var  flag = afterOperate();
	if(false == flag){
		return;	
	}
    CloseWindow("ok");
}

function onCancel(){
    CloseWindow("cancel");
}

//////////////////////////////////
function CloseWindow(action){
    if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
    else window.close();
}
