function validateNum(thisObj){
	var value = thisObj.value;
	var id = thisObj.id;
	if(value){
		if(isNaN(value)){
			alert("请输入数字！");
			document.getElementById(id).value="";
		}
	}
}

//初始化
function initial(){
	 var nowdate = new Date();
	 mini.get("diarydate").setValue(nowdate);
	 var weekArray = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	   
	 var week = weekArray[new Date().getDay()];
	 mini.get("weekday").setValue(week);
}


//获取表单控件上的值
function getFormMapData(){
	var formMap = {};
 	
 	var inputs = document.getElementsByTagName("input");
 	for(var i=0; i<inputs.length; i++){
    	 if(inputs[i].type=='text'){
    		 var name = inputs[i].name;
    		 var id = inputs[i].id;
    		 var value = inputs[i].value;
    		 if(name){
    			 formMap[name] = value;
    		 }else if(id){//针对mini-ui的input控件，不显示name的值，id值后多了"$text"的处理。
    			 var pos = id.indexOf("$text");
    			 if(pos > -1){
    				 name = id.substr(0, pos);
    				 formMap[name] = value;
    			 }
    		 }
    	 }
 	}
 	
 	var textareas = document.getElementsByTagName("textarea");
 	for(var i=0; i<textareas.length; i++){
 		var name = textareas[i].name;
 		var id = textareas[i].id;
 		var value = textareas[i].value;
 		if(name){
 			formMap[name] = value;
 		}
 		//alert("id="+id+", name="+name+", value="+value);
 	}
 	
 	return formMap;
}


function addHeight(length, trid, datagrid){
   $("#"+trid).height($("#"+trid).height() + length);
   $("#"+datagrid).height($("#"+datagrid).height() + length);
}

function minusHeight(length, trid, datagrid){
	$("#"+trid).height($("#"+trid).height() - length);
	$("#"+datagrid).height($("#"+datagrid).height() - length);
}

function newRow(grid,trid, datagrid, type){
	var num = grid.getData().length;
	if(num >= 4){//现行数达到4行时，才调整高度
		addHeight(25, trid, datagrid);
	}
	//else if(num < 2){
	//	addHeight(20, trid, datagrid);
	//}
	
    var row = {type:type};
    grid.addRow(row);
    //grid.beginEditRow(row);
}
	
	
 function delRow(row_uid, grid, trid, datagrid) {
      var row = grid.getRowByUID(row_uid);
      if (row) {
    	  grid.removeRow(row);
     	  var num = grid.getData().length;
  		  if(num >= 4){//移除后，行数达4行及以上才调整行数
  			 minusHeight(25, trid, datagrid);
  		  }
   	 }
}

 
 function onGridLoad(grid, trid, datagrid){
	   var num = grid.getData().length;
	   var saveflag = mini.get("saveflag").getValue();
	   if(num >4 && 0==saveflag){//初次打开页面时，并且行数超过4行的，才调整高度，saveflag=0表示未操作保存按钮
	   		addHeight(25 *(num - 4) , trid, datagrid);
	   	}
 }
 
 function onGrid1Load(){
	   onGridLoad(grid1, "trid1", "datagrid1");
 }
 
 function onGrid2Load(){
	   onGridLoad(grid2, "trid2", "datagrid2");
 }
 
 function onGrid3Load(){
	   onGridLoad(grid3, "trid3", "datagrid3");
 }
 
 function onGrid4Load(){
	   onGridLoad(grid4, "trid4", "datagrid4");
 }
 

 function onActionRenderer1(e){
     //var grid = e.sender;
   var record = e.record;
     var uid = record._uid;
     var rowIndex = e.rowIndex;
     var s = '<span class="icon-remove" title="删除记录" onclick="delRow1('+uid+')"></span>';
     		//+'<span class="icon-new" title="增加记录" onclick="newRow1()"></span>';
   	return s;
 }
 
 function onActionRenderer2(e){
     //var grid = e.sender;
   var record = e.record;
     var uid = record._uid;
     var rowIndex = e.rowIndex;
     var s = '<span class="icon-remove" title="删除记录" onclick="delRow2('+uid+')"></span>';
     		//+'<span class="icon-new" title="增加记录" onclick="newRow2()"></span>';
   	return s;
 }
 
 function onActionRenderer3(e){
     //var grid = e.sender;
   var record = e.record;
     var uid = record._uid;
     var rowIndex = e.rowIndex;
     var s = '<span class="icon-remove" title="删除记录" onclick="delRow3('+uid+')"></span>';
     		//+'<span class="icon-new" title="增加记录" onclick="newRow3()"></span>';
   	return s;
 }
 
 function onActionRenderer4(e){
     //var grid = e.sender;
     var record = e.record;
     var uid = record._uid;
     var rowIndex = e.rowIndex;
     var s = '<span class="icon-remove" title="删除记录" onclick="delRow4('+uid+')"></span>';
     		//+'<span class="icon-new" title="增加记录" onclick="newRow3()"></span>';
   	return s;
 }
 
 
function CloseWindow(action){ 
     if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
     else window.close();
 };
    
    
//手动关闭
function Close(){
	 	CloseWindow("cancel");
}
     
// 刷新本页面
function refreshPage(){
	//
	if(!window["____refreshPage"]){
		window["____refreshPage"] = true;
		//
		location.reload();
	} else {
		window.setTimeout(function(){
			window["____refreshPage"] = false;
			},1*1000);
	}
};

