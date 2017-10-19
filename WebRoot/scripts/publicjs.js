function standardPost(url,ids,furl,args){
	var sfForm = document.getElementById("hidden_formid");
	sfForm.action = url;
	if(ids){
		document.getElementById("hidden_ids").value=ids;
	}
	if(furl){
		document.getElementById("hidden_furl").value=furl;
	}
	//
	if(args){
		for (arg in args){
			createInput(sfForm,"hidden",arg, args[arg]);
		}
	}
	sfForm.submit();
}

function createInput(sfForm,type,name,value){
	var tmpInput = document.createElement("input");
	tmpInput.type = type; 
	tmpInput.name = name; 
	tmpInput.value = value; 
	sfForm.appendChild(tmpInput); 
}


//根据action跳转到指定页面
function chooseOne(menuid){
	var rows = grid.getSelecteds();
	if(rows.length > 0){
		var idArr = [];
        for (var i = 0, l = rows.length; i < l; i++){
           var row = rows[i];
           idArr.push(row.applyid+'@'+row.applyname);
        }
		var ids = idArr.join(',');
		
		var tempid = mini.get("tempid").getValue();
		var flowdefid = mini.get("flowdefid").getValue();
	
		//用于返回到主页面的url
		var furl = encodeURIComponent(location.href);
		var url = "publicCriminalBaseDoc.page?1=1&flowdefid="+flowdefid+"&tempid="+tempid+"&menuid="+menuid+"&optype=add";
		toChooseOnePage(url,ids,furl,0);
	}else{
		alert("请至少选中一条记录！");
		return false;
	}
}
    
function chooseAll(menuid){
	chooseOne(menuid);
}

function toChooseOnePage(url,ids,furl,indexFlag){
	var args = {};
	args["indexFlag"]=indexFlag;
	standardPost(url,ids,furl,args);
}


/*
//根据action跳转到指定页面
function chooseOne(menuid){

	var rows = grid.getSelecteds();
	if(rows.length > 0){
		var idArr = [];
        for (var i = 0, l = rows.length; i < l; i++){
           var row = rows[i];
           idArr.push(row.applyid+'@'+row.applyname);
        }
		var ids = idArr.join(',');
		
		var tempid = mini.get("tempid").getValue();
		var flowdefid = mini.get("flowdefid").getValue();
	
		//用于返回到主页面的url
		var furl = encodeURIComponent(location.href);
		var url = "publicCriminalBaseDoc.page?1=1&flowdefid="+flowdefid+"&tempid="+tempid+"&ids="+ids+"&menuid="+menuid+"&optype=add"+"&indexFlag="+0+"&furl="+furl;
		self.location.href=url;
	}else{
		alert("请至少选中一条记录！");
		return false;
	}
}

function chooseAll(menuid){
	chooseOne(menuid);
}
*/

    
//对于案件的打开页面，lock: 判断是否需要加锁，noOperate：打开页面后不充许操作
function toFatherTabPage(menuid,lock, noOperate){
	if(lock==undefined || lock==null){
		lock=1;
	}
	FatherTabs(menuid, lock, noOperate);
}

function getOperateType(operateType){
	var result = '';
	switch(operateType){
		case 'yes':
			result = '确定要提交给上级审批吗？';
			break;
		case 'back':
			result = '确定要将案件退回吗？';
			break;
		case 'no':
			result = '案件拒绝将无法办理，确定拒绝吗？';
			break;
	}
	return result;
}


//对于案件的打开页面
//lock：就否判断加销  	noOperate：打开页面后不充许操作
function FatherTabs(menuid, lock, noOperate){
	var rows = grid.getSelecteds();
	if (rows.length > 0){
		var idArr = [];
		var flowdraftidArr = [];
		for(var i = 0, l = rows.length; i < l; i++){
			var row = rows[i];
			if(row.flowdraftid){
				idArr.push("&flowdraftid="+row.flowdraftid);
				flowdraftidArr.push(row.flowdraftid);
			}else if(row.applyid){
				idArr.push("&optype=add&applyid="+row.applyid);//标志为重新查询数据
			}
		}
		var ids = idArr.join(',');
		var flowdraftids = flowdraftidArr.join(',');
		if(lock==1 && flowdraftids.length > 0){
			//查询被加锁的案件是否属于当前的用户
			var returnValue = ajaxReturnLockUser(flowdraftids);
			if(returnValue==false){
				grid.reload();
				return;
			}
			//相关案件加锁
			returnValue = lockCaseOfThisUser(flowdraftids);
			if(returnValue==false){
				grid.reload();
				return;
			}
		}
		
		if(rows[0].menuid){
			menuid = rows[0].menuid;
		}
		//用于返回到主页面的url
		var furl = encodeURIComponent(location.href);
		var url = "toFatherTabPage.action?menuid="+menuid;
		if(noOperate){
			url += "&noOperate="+noOperate;
		}
		standardPost(url,ids,furl);
	}else {
		alert( "请至少选中一条记录！");
	}
}

/* 
   //对于案件的打开页面
   //lock：就否判断加销  	noOperate：打开页面后不充许操作
   function FatherTabs(menuid, lock, noOperate){
   		var rows = grid.getSelecteds();
        if (rows.length > 0){
        	var temp_flowdraftid = rows[0].flowdraftid;
        	if(temp_flowdraftid){
        		var idArr = [];
	       		var flowdraftidArr = [];
	            for (var i = 0, l = rows.length; i < l; i++){
	               var row = rows[i];
	               if(row.menuid){
	               		menuid = row.menuid;
	               }
	               idArr.push(row.flowdraftid+'@'+menuid);
	               flowdraftidArr.push(row.flowdraftid);
	            }
	            
				var ids = idArr.join(',');
				var flowdraftids = flowdraftidArr.join(',');
				if(lock==1){
					//查询被加锁的案件是否属于当前的用户
		            var returnValue = ajaxReturnLockUser(flowdraftids);
		       		if(returnValue==false){
		       			grid.reload();
		       			return;
		       		}
		       		
			       	//相关案件加锁
			       	returnValue = lockCaseOfThisUser(flowdraftids);
			       	if(returnValue==false){
			       		grid.reload();
			       		return;
			       	}
				}
		       	//用于返回到主页面的url
		       	var furl = encodeURIComponent(location.href);
	            var url = "toFatherTabPage.action?1=1&ids="+ids+"&indexFlag="+0+"&noOperate="+noOperate+"&furl="+furl;
	            self.location.href=url;
        	}else{
        		var idArr = [];
	            for (var i = 0, l = rows.length; i < l; i++){
	               var row = rows[i];
	               idArr.push(row.applyid+'@'+menuid);
	            }
				var ids = idArr.join(',');
				
		       	//用于返回到主页面的url
		       	var furl = encodeURIComponent(location.href);
	            var url = "toFatherTabPage.action?1=1&ids="+ids+"&optype=add"+"&indexFlag="+0+"&noOperate="+noOperate+"&furl="+furl;
	            self.location.href=url;
        	}
       		
        }else {
            alert( "请至少选中一条记录！");
        }
   }
*/   

function ajaxReturnLockUser(flowdraftids){
	var returnValue = false;
	$.ajax({
		url: "flow/ajaxReturnLockUser2.json?1=1",
		type: "post",
		data: {flowdraftids:flowdraftids},
		async: false,
		success: function (text) {
			text = mini.decode(text);
			if(text.status == 0){
				alert(text.info);
			}else{
				returnValue = true
			}
		},
		error: function (){
			alert("操作失败!");
		}
	});

	return returnValue;
}

//相关案件加锁
function lockCaseOfThisUser(flowdraftids){
	var returnValue = false;
	$.ajax({
		url: "flow/ajaxLockCaseOfThisUser.json?1=1",
		type: "post",
		data: {flowdraftids:flowdraftids},
		async: false,
		success: function (text) {
			if(text){
				var result = eval(text);
				if(result > 0){
					returnValue = true;
				}
			}
		},
		error: function (){
			alert("操作失败!");
		}
	});
	return returnValue;
}


function addStandard(menuid, applyid, objid){
	var tempIds = "optype=add";
	if(applyid){
		tempIds += "@applyid="+applyid;
	}
	var url = "toFatherTabPage.action?1=1&menuid="+menuid+"&tempIds="+tempIds;
	if(objid){
		url += "&objid="+objid;
	}
	var win = mini.open({
		url: url,
		showMaxButton: true,
		allowResize: false,
		title: "新增", 
		onload: function (){
			var iframe = this.getIFrameEl();
			var data = { action: "new"};
			iframe.contentWindow.SetData(data);
		},
		ondestroy: function (action){
			grid.reload();
		}
   });	
   win.max();
}


function addCase(menuid){
	var row = grid.getSelected();
	if(! row){
	   alert("请选择一条数据！");
	   return;
    }
	var applyid = row.applyid;
	addStandard(menuid, applyid, null);
}



//正常新增
function add(menuid){
	
	var objid = "";
	var row = grid.getSelected();
	if(row){
	   objid = row.objid;
    }
	
	var applyid = null;
	var applyidObj = mini.get("applyid");
	if(applyidObj){
		applyid = applyidObj.getValue();
	}
	
	addStandard(menuid, applyid, objid);
}

/*	
	//正常新增
    function add(menuid){
    	var ids = '';
    	var applyidObj = mini.get("applyid");
    	var applyid = '';
    	if(applyidObj){
    		applyid = applyidObj.getValue();
    	}
    	ids = applyid+'@'+menuid;
    	//用于返回到主页面的url
    	var url = "toFatherTabPage.action?1=1&ids="+ids+"&optype=add"+"&indexFlag="+0;
 		var win = mini.open({
 	        url: url,
 	        showMaxButton: true,
 	        allowResize: false,
 	        title: "新增", 
 	        onload: function (){
 	            var iframe = this.getIFrameEl();
 	            var data = { action: "new"};
 	            iframe.contentWindow.SetData(data);
 	        },
 	        ondestroy: function (action){
 	        	grid.reload();
 	        }
 	   });	
 	   win.max();
   }
*/


//查看
function chakan(menuid){
	check(menuid);
}

//查看
function check(menuid){
	var row = grid.getSelected();//获取选中记录
	var tempIds = "flowdraftid="+row.flowdraftid
	var url = "toFatherTabPage.action?1=1&menuid="+menuid+"&tempIds="+tempIds;
	var win= mini.open({
		url: url,
		showMaxButton: true,
		allowResize: false, 
		title: "", 
		onload: function () {
			var iframe = this.getIFrameEl();
			var data = { action:"show" };
			iframe.contentWindow.SetData(data);
		},
		ondestroy: function (text){
			grid.reload();
		}
	});
	win.max();
}

	
//修改
function xiugai(menuid){
	edit(menuid);
}

//修改
function edit(menuid){
	var row = grid.getSelected();//获取选中记录
	var tempIds = "optype=edit@flowdraftid="+row.flowdraftid
	var url = "toFatherTabPage.action?1=1&menuid="+menuid+"&tempIds="+tempIds;
	
	var win= mini.open({
        url: url,
        showMaxButton: true,
     	allowResize: false, 
        title: "修改", 
        onload: function () {
            var iframe = this.getIFrameEl();
            var data = { action:"edit" };
            iframe.contentWindow.SetData(data);
        },
        ondestroy: function (action){
            grid.reload();
        }
    });
    win.max();
}

/*
   //查看
   function chakan(menuid){
   		check(menuid);
   }
   //查看
	function check(menuid){
		var row = grid.getSelected();//获取选中记录
		var ids = row.flowdraftid+'@'+menuid;
		//用于返回到主页面的url
	    var url = "toFatherTabPage.action?1=1&ids="+ids+"&indexFlag="+0;
    	var win= mini.open({
            url: url,
            showMaxButton: true,
         	allowResize: false, 
            title: "", 
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action:"show" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (text){
                grid.reload();
            }
        });
        win.max();
	}
	
	 //修改
	function xiugai(menuid){
		edit(menuid);
	}
	//修改
	function edit(menuid){
		var row = grid.getSelected();//获取选中记录
		var ids = row.flowdraftid+'@'+menuid;
		var url = "toFatherTabPage.action?1=1&ids="+ids+"&indexFlag="+0+"&optype=edit";
    	var win= mini.open({
            url: url,
            showMaxButton: true,
         	allowResize: false, 
            title: "修改", 
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action:"edit" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action){
                grid.reload();
            }
        });
        win.max();
	}
*/


function remove(menuid){
	var rows = grid.getSelecteds();
	if(rows.length > 0){
		if(confirm("确定操作选中的记录吗？")){
			var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push('\''+row.flowdraftid+'\'');
            }
			var flowdraftids = flowdraftidArr.join(',');
			var data = {};
			data.flowdraftids = flowdraftids;
			data.menuid = menuid;
			removeData(data);
		}
        //    
	}else{
		alert("请选择记录！");
		return false;
	}
}
    
    
function removeData(data){
	var url = path + '/removeDate.json?1=1';
	//
	$.ajax({
        url: url,
        data: data,
        type: "post",
        success: function (message){
        	message = mini.decode(message);
        	var info = message["info"];
    	    var status = message["status"];
    	    if(info){
    	    	alert(info);
    	    }else{
    	    	if(status==1){
    	    		alert("操作成功！");
    	    	}else{
    	    		alert("操作失败！");
    	    	}
    	    }
    	    //
    	    grid.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	alert("操作失败！");
        }
    });
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
	
    
/*
 * 保存或流程流转
	tempid: 表单ID
	flowdefid: 流程定议ID
	masterslave: 操作主从附件表,master, slave
	bizName: 回调方法
	dnodeid : 自由流转的目地节点
*/
function savedata(masterslave,operateType,bizName, dnodeid){
	var form = new mini.Form("form1");
	form.validate();
    if (form.isValid() == false) return false;
	//获取大字段信息
	var aipObj=document.getElementById("HWPostil1");
	var docconent = aipObj.GetCurrFileBase64();
	//表单所有节点及值
	var noteinfo = getNoteMap();
	//
	if(operateType=="agree"){
		if(dnodeid == null || nodeid == '')
			dnodeid  = 1;
	}
	var flag = SaveData(docconent, noteinfo, masterslave, operateType, bizName, dnodeid);
	return flag;
}
    
/*
 * 保存系统模板
	tempid: 表单ID
	flowdefid: 流程定议ID
	masterslave: 操作主从附件表,master, slave
	bizName: 回调方法
*/
function saveSysTemplate(masterslave,operateType,bizName){
	var form = new mini.Form("form1");
	form.validate();
    if (form.isValid() == false) return false;
    
	var data = form.getData();
   	if(!data.annexcontent){
   		alert("内容不能为空！");
   		return;
   	}
   	var keywords=/[<>]/;
   	if(keywords.test(data.annexcontent)){
   		alert("不能包含特殊字符！例如 ‘<’ ‘>’等特殊字符");
   		return ;
   	}
       	
	//获取大字段信息
	var docconent = data.annexcontent;
	//表单所有节点及值
	var noteinfo = '';
	//
	var flag = SaveData(docconent, noteinfo, masterslave, operateType, bizName);
		
	return flag;
}
    
    
var backnoreason = "";//定义全局变量 保存拒绝或者退回理由
function SaveData(docconent, noteinfo, masterslave, operateType, bizName, dnodeid){
	var flag = false;
	var tempid = mini.get("tempid").getValue();
	var flowdefid = mini.get("flowdefid").getValue();
	flowdefid = flowdefid || 'other_sysflow';//如果是非流程的表单保存，默认用other_sysflow(系统流程);
	var solutionid = null;
	if(mini.get("solutionid")){
		solutionid = mini.get("solutionid").getValue();
	}
	var flowdraftid = null;
	if(mini.get("flowdraftid")){
		flowdraftid = mini.get("flowdraftid").getValue();
	}
	
	if(!flowdraftid && window.parent.mini.get("flowdraftid")){
		flowdraftid = window.parent.mini.get("flowdraftid").getValue();
	}
	
	if(!flowdraftid && masterslave=='slave'){
		alert("请先做主文书！");
		return;
	}
	//
	var flowlock = validateFlowCondition(flowdraftid, operateType);
	if(flowlock){
		if(flowlock.flowlocked == '1'){
			alert(flowlock.lockreasons);
			return;
		}
	}
	//
	var applyid = '';
	if(window.parent.mini.get("applyid")){
		applyid = window.parent.mini.get("applyid").getValue();
	}
	var applyname = '';
	if(window.parent.mini.get("applyname")){
		applyname = window.parent.mini.get("applyname").getValue();
	}
	//
	var data = getParamMapFromUrl();
	data["operateid"] = getUUID();
	if(docconent){
		var length = docconent.length;
		var size = Math.ceil(length/5242880); //1048576=1024*1024，即1M，5242880为5M
		if(size>1){//如果docconent太长超过5M，无法向后台传递数据，则做另外的处理    size为多少个5M
			var uuid = getUUID();
			var saveFlag = saveLongDocconent(uuid,docconent,length,size);
			if(false==saveFlag){
				return false;
			}else{
				data["docconent"] = uuid;
			}
		}else{
			data["docconent"] = docconent;
		}
	}
	if(flowdraftid){
		data["flowdraftid"] = flowdraftid;
	}
	if(solutionid){
		data["solutionid"] = solutionid;
	}
	if(applyid){
		data["applyid"] = applyid;
	}
	if(applyname){
		data["applyname"] = applyname;
	}
	if(tempid){
		data["tempid"] = tempid;
	}
	if(flowdefid){
		data["flowdefid"] = flowdefid;
	}
	if(masterslave){
		data["masterslave"] = masterslave;
	}
	if(operateType){
		data["operateType"] = operateType;
	}
	if(bizName){
		data["bizName"] = bizName;
	}
	if(dnodeid){
		data["freeDnodeid"] = dnodeid;
	}
	if(noteinfo){
		data["noteinfo"] = noteinfo+"";
	}
	data["backnoreason"] = backnoreason;
	//签章个数
	var aipObj = document.getElementById("HWPostil1");
	if(aipObj){
		var sealnum = aipObj.GetNoteNum(251);//电子签章的个数
		data["sealnum"] = sealnum;
	}
	
	//临时变量
	var temp1 = null;
	if(mini.get("temp1")){
		temp1 = mini.get("temp1").getValue();
	}
	data["temp1"] = temp1;
	
	if(mini.get("temp2") && mini.get("temp2").getValue()){//存flowdraftids
		data["flowdraftids"] = mini.get("temp2").getValue();
	}
	
	//var temp2 = mini.get("temp2").getValue();
	//data["temp2"] = temp2;
	//return multiPromptClick(data,flag);
	var url= path + "/ajaxSaveBaseDoc.action?1=1";
    $.ajax({
        url:encodeURI(encodeURI(url)),
        data: data,
        type: "post",
        dataType:"text",
        cache:false,
        async:false,
        success: function (text) {
            	text = mini.decode(text);
            	if(text["rows"]==1){
            		if("save"==operateType){
            			if(window.parent.mini.get("flowdraftid")){
                			window.parent.mini.get("flowdraftid").setValue(text["flowdraftid"]);
                			window.parent.resetIdsArr(text["flowdraftid"]);
                		}
                		if(mini.get("flowdraftid")){
                			mini.get("flowdraftid").setValue(text["flowdraftid"]);
                		}
            		}
            		flag = true;
            		alert("操作成功！");
            	}else{
            		alert(text["status"]);
            	}
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	   alert("操作失败!");
        }
    });
   return flag;
	
}


function saveLongDocconent(uuid,docconent,length,size){
	var flag = true;
	for(var i=0;i<size;i++){
		var start = i*5242880;
		var stop = (i+1)*5242880;//一次最多取5M
		if(stop>=length){
			stop = length;
		}
		var fragment = docconent.substring(start,stop);
		
		var data = {};
		data.fragment=fragment;
		data.uuid=uuid;
		data.index = i;
		
		var url= path + "/saveFragmentDocconent.json?1=1";
        $.ajax({
           url:url,
           data: data,
           type: "post",
           dataType:"text",
           cache:false,
           async:false,
           success: function (text){
               	text = mini.decode(text);
               	if(1!=text){
               		flag = false;
               	}
           },
           error: function (jqXHR, textStatus, errorThrown) {
        	   flag = false;
           }
        });
        
        if(false == flag){
        	break;
        }
	}
	return flag;
}
    

/*
   function toNext(isCommit){
	   	if(window.parent){
	   		if(window.parent.next){
	   			window.parent.next(isCommit);
	   		}else{
	   			next(isCommit);
	   		}
	   		
	   	}else{
	   		next(isCommit);
	   	}
   }
   
	// 到下一个; 父页面不负责业务逻辑(isCommit,是否是提交操作, 默认值 0, 可指定 0,1)
   function next(isCommit){
       	// 1. 获取参数
       	// 2. 截取index
       	// 3. 修改 index
       	// 4. 跳转
       	// 
       	// ids=1,2,3&indexFlag=0
	   
       	var idArray = ids.split(",") || [];
       	var currentid = idArray[indexFlag];
       	//
       	if(indexFlag > idArray.length - 1){
       		return goBack();
       	}
       	//
       	var paramObject = parseURLParams() || {};
       	// isCommit
       	isCommit = isCommit || 0;
       	if(isCommit){
           	// 如果是提交, 则 indexFlag 不变,将当前ID摘除
           	// 
           	if(currentid){
           		// 当前ID存在
           		var newIDsArray = [];
           		for(var i = 0; i < idArray.length; i++){
           			var cid = idArray[i];
           			if(currentid === cid){
           				// 不执行
           			} else {
           				newIDsArray.push(cid);
           			}
           		}
           		//
           		ids = newIDsArray.join(",");
           		paramObject["ids"] = ids;
           		idArray = newIDsArray;
           	}
       	} else {
           	// 如果不是提交, 将 indexFlag +1;
           	indexFlag = indexFlag + 1;
           	paramObject["indexFlag"] = indexFlag;
       	}
       	
       	if(indexFlag > idArray.length - 1){
       		return goBack();
       	}
       	var url = processParam(getURLPathName(), paramObject);
       	window.location.href = url;
   };
   
   // 到上一个
    function previous(){
    	// 1. 获取参数
    	// 2. 截取index
    	// 3. 修改 index
    	// 4. 跳转
    	// 
    	// ids=1,2,3&indexFlag=0
    	var idArray = ids.split(",") || [];
    	//
    	if(indexFlag < 0){
    		return goBack();
    	}
    	
    	//
    	var paramObject = parseURLParams() || {};
    	
       	indexFlag = indexFlag - 1;
       	paramObject["indexFlag"] = indexFlag;
    	if(indexFlag < 0){
    		return goBack();
    	}
    	var url = processParam(getURLPathName(), paramObject);
    	window.location.href = url;
    	
    };
    
*/


//关闭,返回
function goBack(){
  	var r = new Date().getTime();
   	url = parsefurl();
  	if(url){
   	   window.location.href=url;
  	}else{
     	CloseWindow("close");
  	}
};
	    
//
function CloseWindow(action){ 
	if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	else window.close();
};


//手动关闭
function Close(){
	if(window.parent.ftab){
		window.parent.goBack();
	}else{
		goBack();
	}
}


/*
    // 关闭,返回
    function goBack(){
      	var r = new Date().getTime();
       	url = parsefurl();
      	if(url){
       	   window.location.href=url;
      	}else{
         	CloseWindow("close");
      	}
    };
	    
   //
   function CloseWindow(action){ 
       if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
       else window.close();
   };
   
   
    //手动关闭
    function Close(){
    	if(window.parent.goBack){
    		window.parent.goBack();
    	}else{
    		goBack();
    	}
    }
    
    //isCommit,是否是提交操作, 默认值 0, 可指定 0,1
   function toNext(isCommit){
    	
    	if(window.parent.next){
    		alert("1");
    		window.parent.next(isCommit);
    	}else{
    		alert("2");
    		next(isCommit);
    	}
    }
    
    //上一个
    function toPrevious(){
    	if(window.parent){
    		if(window.parent.previous){
    			window.parent.previous();
    		}else{
    			previous();
    		}
    		
    	}else{
    		previous();
    	}
    	
    }
    */
    
//
function getParamMapFromUrl(){
	var paramMap = {};
	
	var url = window.location.href;
	var start = url.indexOf('?');
	var param = url.substring(start+1);
	url = url.substring(0,start);
	var paramArr = param.split('&');
	
	var keyValue = '';
	for(var i=0,l=paramArr.length; i<l ; i++){
		keyValue = paramArr[i].split('=');
		if(keyValue.length==2){
			var key = keyValue[0];
			var value = keyValue[1];
			paramMap[key] = value;
		} 
	}
	var topsearchkeyArr = [];
	if(topsearchkey){
		topsearchkeyArr = topsearchkey.split(',');
	}
	
	for(var i=0,l=topsearchkeyArr.length; i<l ; i++){
		var key = topsearchkeyArr[i]
		var value = mini.get(key).getValue();
		if(value){
			//paramMap[key] = encodeURI( value );
			paramMap[key] = value;
    	}
	}
	return paramMap;
};
	
	
function getParamMapFromListPage(){
	var paramMap = {};
	var otherparamObj = mini.get('otherparam');
	if(otherparamObj && otherparamObj.getValue()){
		var otherparam = otherparamObj.getValue();
		var paramArr = otherparam.split(",");
		for(var i=0,l=paramArr.length; i<l ; i++){
			var key = paramArr[i];
			var valueObj = mini.get(key);
			if(valueObj && valueObj.getValue()){
				paramMap[key] = valueObj.getValue();
			}
		}
	}
	return paramMap;
}
		
		//
   
   
// 渲染日期
function onDateRenderer(e){
	if(e && e.value){
		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	}
    return "";
}

//渲染日期(高精度)
function onDateAccurateRenderer(e){
	if(e && e.value){
		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd H:mm:ss" );
	}
    return "";
}
    
//渲染罪犯基本信息
function onCrimidRenderer(e) {
	var grid = e.sender;
	var record = e.record;
	var uid = record._uid;
	var rowIndex = e.rowIndex;
	var s = ' <a class="Edit_Button" href="javascript:toCriminalBaseInfo();" >' + record.crimid + '</a>&nbsp;&nbsp;';
	return s;
}

//渲染罪犯基本信息
function onCriminalRenderer(e) {
	var grid = e.sender;
	var record = e.record;
	var uid = record._uid;
	var rowIndex = e.rowIndex;
	var s = ' <a class="Edit_Button" href="javascript:toCriminalBaseInfo();" >' + record.crimid + '</a>&nbsp;&nbsp;';
	return s;
}

function onStatusRenderer(e){
	var s = "";
	var row = e.record;
	var state = row.state;
	if(state=='-1'||state=='0'||state=='-2'){
		s ='正在办理中&nbsp';
	}else if(state=='1'){
		s ='审批通过&nbsp';
	}else if(state=='2'){
		s ='上级退回&nbsp';
	}else if(state=='3'){
		s ='审批不通过&nbsp';
	}else{
		s = '';
	}
    return s;
}
	
//根据罪犯编号跳转到基本信息表单页面
function toCriminalBaseInfo(){
	var row = grid.getSelected();
	var crimid = '';
	if(row.crimid){
		crimid = row.crimid;
	}else if(row.applyid){
		crimid = row.applyid;
	}
	var tempIds = "optype=add";
	if(crimid){
		tempIds += "@applyid="+crimid;
	}
	var url = path+"/toFatherTabPage.action?1=1&menuid="+703024+"&tempIds="+tempIds;
	var win = mini.open({
		url : url,
		title : "基本信息",
		showMaxButton : true,
		allowResize : false,
		onload : function() {
		},
		ondestroy : function(action) {
		}
	});
	win.max();
}
	
	
function validateKey(aipObj){
	aipObj.Logout();
	var aipLoginValue = aipLogin(aipObj);
	if(aipLoginValue == -200){
		alert("未发现智能卡");
		return false;
	}else if(aipLoginValue != 0){
		alert("登录失败。错误ID:" + aipLoginValue);
		return false;
	}
}
	
////打印、批量打印公共按钮
function batchPrint(menuid, signid){
	batchOperate(menuid, signid, 'print');
}

//sealOrRevoke  1： 签章，2：撤销签章
function batchSeal(menuid, signid, sealOrRevoke){
	mini.get("sealOrRevoke").setValue(sealOrRevoke);
	batchOperate(menuid, signid, 'seal');
}

//批量导出文书公共按钮
function batchExpDoc(menuid, signid){
	var Folder = BrowseFolder();
	if(Folder==null){
		return;
	}
	batchOperate(menuid, signid, 'export', Folder);
}
	
function batchOperate(menuid, signid, type, Folder){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
    	var doFlag = confirm("确定操作选中的记录吗？");
    	if(doFlag){
    		var aipObj = document.getElementById("HWPostil1");
        	var flag = validateKey(aipObj);
        	if(flag==false){
        		return false;
        	}
        	myloading();
        	aipObj.CloseDoc(0);
        	
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               var flowdraftid = row.flowdraftid;
               singleOperate(aipObj, menuid, signid, flowdraftid, type, Folder);
            }
            
            alert("操作成功！");
            myunmask();
    	}
    }else {
        alert( "请至少选中一条记录！");
    }
}

//单个操作
function singleOperate(aipObj, menuid, signid, flowdraftid, type, Folder){
	var sealOrRevoke = mini.get("sealOrRevoke").getValue();
	var url = path + "/sign/getSealData.json?1=1";
	$.ajax({
         url: url,
         data: {signid:signid, menuid:menuid, flowdraftid:flowdraftid, type:type, sealOrRevoke:sealOrRevoke},
         type: "post",
         async: false,
         success: function (text){
         	var data = mini.decode(text);
         	if(type=='print'){
         		print(aipObj,flowdraftid, data);
         	}else if(type=='export'){
         		fileExport(Folder, aipObj,flowdraftid, data);
         	}else if(type=='seal'){
         		seal(aipObj,flowdraftid, data);
         	}
         	
         },
         error: function (jqXHR, textStatus, errorThrown){
         	
         }
     });
}
	
function print(aipObj,flowdraftid, printData){
	for(var i=0,l=printData.length; i<l; i++){
		var map = printData[i];
		perPrintDoc(aipObj, map.aipFileStr);
	}
}

function fileExport(Folder, aipObj,flowdraftid, exportData){
	for(var i=0,l=exportData.length; i<l; i++){
		var map = exportData[i];
		var applyid = map.applyid;
		
		var dFolder = Folder + map.applyid + "_" + map.applyname + "\\";
		var objFSO = new ActiveXObject("Scripting.FileSystemObject");
		// 检查文件夹是否存在
		if (!objFSO.FolderExists(dFolder)){
			// 创建文件夹
			 var strFolderName = objFSO.CreateFolder(dFolder);
		}
		perExportData(dFolder, aipObj, map.aipFileStr, map.signtype, 'aip');
	}
}

function perPrintDoc(aipObj, obj){ //打印
	aipObj.JSEnv = 1;
	if(obj && obj.length >0){
		//多页显示 例如 合并查看
		for(var j=0;j<obj.length;j++){
			aipObj.MergeFile(99999,'STRDATA:'+obj[j]);
		}
		var aipFileStr = aipObj.GetCurrFileBase64();
		var num = 1;//打印份数
		//alert(aipFileStr)
		aipObj.PrintDocEx('',1,0,0,0,9999,0,num,1,0,0);
	}
	aipObj.CloseDoc(0);
}
	
function perExportData(Folder, aipObj, obj, filename, suffix){//导出
	aipObj.JSEnv = 1;
	if(obj && obj.length > 0){
		//多页显示 例如 合并查看
		for(var j=0;j<obj.length;j++){
			//aipObj.LoadFileBase64(obj[j]);
			aipObj.MergeFile(99999,'STRDATA:'+obj[j]);
		}
		//var abcd = aipObj.GetCurrFileBase64();
		
		if(aipObj.IsOpened()){
			document.all.HWPostil1.SetValue("SET_TEMPFLAG_MODE", "2");//不加锁
			document.all.HWPostil1.SaveTo(Folder+filename+"."+suffix, suffix, 0);
		}
		//aipObj.CloseDoc(0);
	}
}


function seal(aipObj, flowdraftid, dataBeforeSeal){
	aipObj.CloseDoc(0);
	var dataAfterSeal = [];
	/*
	  符合签章(撤销签章):Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
 	 									flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid,  signtype:signtype, signname:signname}
 	  不符合签章(撤销签章):Map{sealable:0, signtype:signtype,  signname:signname, status:status}
	*/
	
	for(var i=0,l=dataBeforeSeal.length; i<l; i++){
		var map = dataBeforeSeal[i];
		var sealable = map.sealable;
		if(0==sealable){
			//notFitSealMessage(caseno,map);
		}else if(1==sealable){
			singleSeal(flowdraftid, map, dataAfterSeal);
		}else{
			//dealSingleFailSealMessage(caseno, map);
		}
	}
    saveSealData(flowdraftid, dataAfterSeal);
}
	
//保存签章后的数据
function saveSealData(flowdraftid, dataAfterSeal){
	data = mini.encode(dataAfterSeal);
	var url = path + "/sign/saveDataAfterSeal.json?1=1";
	$.ajax({
         url: url,
         data: {data:data, flowdraftid:flowdraftid},
         type: 'POST',
         async:false,
         success: function (text){
         		/*
         		var mapList = mini.decode(text);
         		if(mapList.status == '0'){
         			dealFailSealMessage(caseno);
         		}else{
         			dealSuccessMessages(caseno,mapList);
         		}
         		*/
         },
         error: function (jqXHR, textStatus, errorThrown) {
          	   
         }
     });
}
   
//Map{sealable:1, content:content, scheme:scheme, aipFileStr:aipFileStr, formdata:formdata,
// * 	  flowdraftid:flowdraftid, tempid:tempid,flowdefid:flowdefid,  signtype:signtype, signname:signname}
function singleSeal(flowdraftid, map, dataAfterSeal){
	if(! map.sealable || map.sealable !=1){
		//dealSingleFailSealMessage(caseno, map);
		return;
	}
	//
	var aipObj = document.getElementById("HWPostil1");
	var sealOrRevoke = mini.get("sealOrRevoke").getValue(); 
	var content = map.content;
	
	var scheme = map.scheme;
	var aipFileStr = map.aipFileStr;
	var formdata = map.formdata;
	var date = mini.formatDate(new Date(),'yyyy-MM-dd'); //暂时取当前系统时间
	//加载表单
	loadFormData(aipFileStr, formdata, null);
	//
	if(aipObj.IsOpened()){
		aipObj.ForceSignType=7;
		aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
		var sealStatus = false;
		if(2==sealOrRevoke){
			//删除印章操作
			sealStatus = DeleteSeal(aipObj);
		}else if(1==sealOrRevoke){
			//盖章
			if(date){
				date = date.replace(/\-/g,"");//把yyyy-MM-dd格式转换成yyyyMMdd
			}
			sealStatus = InsertNote(scheme,aipObj,'',date,'','');
		}
		if(sealStatus == false){
			aipObj.CloseDoc(0);
			//dealSingleFailSealMessage(caseno, map);
			return;
		}else{
			aipFileStr = aipObj.GetCurrFileBase64();
			map.aipFileStr = aipFileStr;
			var notationnum = aipObj.GetNoteNum(1);//手写批注的个数
			var sealnum = aipObj.GetNoteNum(251);//电子签章的个数
			map.notationnum = notationnum;
			map.sealnum = sealnum;
			dataAfterSeal.push(map);
		}
		aipObj.SleepSecond(1);//为什么？
	}
	aipObj.CloseDoc(0);
}
   
   
//加载表单
function loadFormData(aipFileStr, formData, selectData){
	var aipObj=document.getElementById("HWPostil1");
	aipObj.LoadFileBase64(aipFileStr);//加载表单
	//向表单赋值
	if(formData){
		formData = replaceAllStr(formData,"\\\\n","\\n");
		formData = mini.decode(formData);
		for(var key in formData){
			var _value = formData[key];
			aipObj.SetValue(key,"");
			if(key=='picrjdjimg'){//基本信息表的图片，加载数据库的base64字符串
				aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
				aipObj.SetValueEx(key,14,0,"STRDATA:"+_value);
				aipObj.Logout();
			}else if(key.indexOf("CheckBox")!=-1 || key.indexOf("RadioBox")!=-1){//单复选框
				aipObj.SetValueEx(key,3,_value,"");
			}else{
				aipObj.SetValue(key,_value);
			}
		}
	}
	//下拉框
	if(selectData){
		selectData = mini.decode(selectData);
		for(var key in selectData){
			aipObj.SetValueEx(key,43,0,selectData[key]);
		}
	}
}


/////////////////////////////////////////////////////////////////////////////
//批量提交
function batchSubmit(menuid, solutionid, bizName){
	batchFlowOperate(menuid, 'yes', solutionid, bizName);
}
//批量退回   不填写拒绝退回意见
function batchReturn(menuid, solutionid, bizName){
	batchFlowOperate(menuid, 'back', solutionid, bizName);
}
//批量拒绝 不填写拒绝退回以意见
function batchDisagree(menuid, solutionid, bizName){
	batchFlowOperate(menuid, 'no', solutionid, bizName);
}


//批量退回  填写拒绝退回意见
function batchReturnTwo(menuid, solutionid, bizName){
	batchFlowOperateTwo(menuid, 'back', solutionid, bizName);
}
//批量拒绝 填写拒绝退回以意见
function batchDisagreeTwo(menuid, solutionid, bizName){
	batchFlowOperateTwo(menuid, 'no', solutionid, bizName);
}

	
//批量拒绝、退回、同意 不填写拒绝退回以意见
function batchFlowOperate(menuid, operateType, solutionid, bizName){
	//var aipObj=document.getElementById("HWPostil1");	
	//aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");//登录签章
	var rows = grid.getSelecteds();
    if (rows.length > 0){
    	var doFlag = confirm("确定操作选中记录？");
        if (doFlag){
        	//判断加锁情况	后面在做处理
        	myloading();
        	var flowdraftidArr = [];
            for(var i = 0, l = rows.length; i < l; i++) {
                var row = rows[i];
                flowdraftidArr.push(row.flowdraftid);
            }
            var flowdraftids = flowdraftidArr.join(',');
            var flowdefid = mini.get('flowdefid').getValue();
            //检查是否存在必要文书
            var flag = isThePapersMaked(menuid, operateType,flowdraftids,flowdefid);
            if(false == flag){
            	myunmask();
            	return flag;
            }
            //
            var flowlock = validateFlowCondition(flowdraftids, operateType);
    		if(flowlock){
    			if(flowlock.flowlocked == '1'){
    				alert(flowlock.lockreasons);
    				myunmask();
    				return false;
    			}
    		}
        	//检查签章规则，后面再完善
    		var currnodeid = mini.get("currnodeid").getValue();
    		flag = checkSealRule(flowdraftids, operateType, currnodeid);
    		if(false == flag){
            	myunmask();
            	return flag;
            }
        	//
        	//按扭加锁
        	buttonLock();
        	//流程操作
        	batchFlowOperateWithFlowdraftids(flowdraftids, menuid, operateType, solutionid, bizName);
            //alert("提前执行也无所谓，这个也可以继续执行的!");
    	    //按扭解锁
    	    buttonUnlock();
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
}

function batchFlowOperateTwo(menuid, operateType, solutionid, bizName){
	//var aipObj=document.getElementById("HWPostil1");	
	//aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");//登录签章
	var rows = grid.getSelecteds();
    if (rows.length > 0){
    	var doFlag = confirm("确定操作选中记录？");
        if (doFlag){
        	//判断加锁情况	后面在做处理
        	myloading();
        	var flowdraftidArr = [];
            for(var i = 0, l = rows.length; i < l; i++) {
                var row = rows[i];
                flowdraftidArr.push(row.flowdraftid);
            }
            var flowdraftids = flowdraftidArr.join(',');
            var flowdefid = mini.get('flowdefid').getValue();
            //检查是否存在必要文书
            var flag = isThePapersMaked(menuid, operateType,flowdraftids,flowdefid);
            if(false == flag){
            	myunmask();
            	return flag;
            }
            //
            var flowlock = validateFlowCondition(flowdraftids, operateType);
    		if(flowlock){
    			if(flowlock.flowlocked == '1'){
    				alert(flowlock.lockreasons);
    				myunmask();
    				return false;
    			}
    		}
        	//检查签章规则，后面再完善
    		var currnodeid = mini.get("currnodeid").getValue();
    		flag = checkSealRule(flowdraftids, operateType, currnodeid);
    		if(false == flag){
            	myunmask();
            	return flag;
            }
        	//
        	//按扭加锁
        	buttonLock();
        	//流程操作
        	batchBackNoReason(flowdraftids, menuid, operateType, solutionid, bizName);
        	//alert("是不是提前执行了呀");
        }
    } else {
        alert( "请至少选中一条记录！");
    }
}


//批量填写拒绝退回意见
function batchBackNoReason(flowdraftids, menuid, operateType, solutionid, bizName){
	mini.open({
	   url: "multiPromptClick.json?1=1",
       title: "请输入拒绝/退回意见", width: 560, height: 300,
       async:false,
       onload: function () {
           var iframe = this.getIFrameEl();
           var data1 = {action: "new"};
           iframe.contentWindow.SetData(data1);
       },
       ondestroy: function (action) {
    	   var iframe = this.getIFrameEl();
    	   var data1 = iframe.contentWindow.GetData();
           data1 = mini.clone(data1);
           //通过全局变量 获取拒绝或退回的理由
           backnoreason = data1.reason;
           batchFlowOperateWithFlowdraftids(flowdraftids, menuid, operateType, solutionid, bizName);
           //alert("提前执行也无所谓，这个也可以继续执行的!");
       	   //按扭解锁
       	   buttonUnlock();
           myunmask();
           grid.reload();
	  }
   }); 
}

   	
//按钮加锁，防止二次操作	
function buttonLock(){
	buttonControl(false);
}

function buttonUnlock(){
	buttonControl(true);
}
//flag    false:加锁，true:解锁
function buttonControl(flag){
	var aTag = document.getElementsByTagName("a");
	for(var i=0;i<aTag.length;i++){
		if(aTag[i] && aTag[i].id){
			var id = aTag[i].id;
			if(mini.get(id)){
				//mini.get(id).disable();
				mini.get(id).setEnabled(flag);
			}
		}
	}
}
   	
   	
   	
function batchFlowOperateWithFlowdraftids(flowdraftids, menuid, operateType, solutionid, bizName){
	var uuid = getUUID();
	//
    //开始批量操作
    var flowdraftidArr = flowdraftids.split(",");
    for(var i=0, l=flowdraftidArr.length; i<l; i++){
    	var flowdraftid = flowdraftidArr[i];
    	singleFlowOperate(menuid, operateType, flowdraftid, solutionid, bizName,null,uuid);
    }
}

function freeFlowOperateWithFlowdraftids(flowdraftids, menuid, operateType, solutionid, dnodeid, bizName){
	var uuid = getUUID();
    //开始批量操作
    flowdraftidArr = flowdraftids.split(",");
    for(var i=0, l=flowdraftidArr.length; i<l; i++){
    	var flowdraftid = flowdraftidArr[i];
    	singleFlowOperate(menuid, operateType, flowdraftid, solutionid, bizName, dnodeid,uuid);
    }
    
}
   	
//流程批量操作，自由流转至某一节点
function batchFreeFlowOperate(menuid, operateType, solutionid, dnodeid, bizName){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
        if (confirm("确定操作选中记录？")){
        	//判断加锁情况
        	//后面在做处理
        	myloading();
	        //var flowdraftids = getFlowdraftidsByRows(rows);
	        var flowdraftidArr = [];
            for(var i = 0, l = rows.length; i < l; i++) {
                var row = rows[i];
                flowdraftidArr.push(row.flowdraftid);
            }
            var flowdraftids = flowdraftidArr.join(',');
         
	        //检查签章规则，后面再完善
            
            //
            var flowlock = validateFlowCondition(flowdraftids, operateType);
    		if(flowlock){
    			if(flowlock.flowlocked == '1'){
    				alert(flowlock.lockreasons);
    				return;
    			}
    		}
    		//
	        freeFlowOperateWithFlowdraftids(flowdraftids, menuid, operateType, solutionid, dnodeid, bizName);
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}


/**
 * @param {} flowdraftids
 */
function checkSealRule(flowdraftids, operateType, currnodeid){
	var result;
	var url =  path +"/sign/batchCheckSeal.json?1=1";
    $.ajax({
    	 url : url,
         type:'post',
         data: {flowdraftids:flowdraftids, operateType:operateType, currnodeid:currnodeid},
         async:false,
         success:function (text){
    	    text = mini.decode(text);
        	if(text.errorcode == "2"){
            	alert(text.info);
            	result = false;
            }
//        	flowdraftids = data.flowdraftids;//符合操作的flowdraftids
//        	var notFitInfo = data.notFitInfo;//不符合操作的信息
         }
    });
    
    return result;
    
}

/*
function checkSealRule(flowdraftids){
	var result;
	var url =  path +"/sign/checkSeal.json?1=1";
    $.ajax({
    	 url : url,
         type:'post',
         data: {flowdraftids:flowdraftids},
         async:false,
         success:function (text){
    	    result = mini.decode(text);
//        	flowdraftids = data.flowdraftids;//符合操作的flowdraftids
//        	var notFitInfo = data.notFitInfo;//不符合操作的信息
         }
    });
    
    return result;
    
}
*/
   	
/**
 * 描述：判断是否存在必要文书
 * @author YangZR	2015-06-28
 */
function isThePapersMaked(flowMenuid, operateType,flowdraftids,flowdefid){
	var flag = false;
	var url = path + "/isPapersMaked.json?1=1";
	$.ajax({
        url: url,
        data: {operateType:operateType, flowdraftids:flowdraftids, flowdefid:flowdefid, flowMenuid:flowMenuid },
        type: 'POST',
        async:false,
        success: function (text){
        	var result = mini.decode(text);
        	if(result.ismaked && result.ismaked=='0'){
        		alert(result.status);
        	}else{
        		flag = true;
        	}
        },
        error: function (jqXHR, textStatus, errorThrown){
         	 alert("操作失败！");
        }
    });
    
	return flag;

}
   	
function singleFlowOperate(menuid, operateType, flowdraftid, solutionid, bizName, dnodeid,uuid){
	//通过flowdraftid加载表单
	var tempid = mini.get("tempid").getValue();
	var flowdefid = mini.get("flowdefid").getValue();
	var masterslave = "master";
	flowdefid = flowdefid || 'other_sysflow';//如果是非流程的表单保存，默认用other_sysflow(系统流程);
	var url = path + "/ajaxGetDocconent.json";
	$.ajax({
         url: url,
         data: {flowdraftid:flowdraftid, tempid:tempid, flowdefid:flowdefid},
         type: "post",
         async: false,
         dataType:"text",
         success: function (text){
         	//var aipFileStr = mini.decode(text);
        	var aipFileStr = text;
         	if(aipFileStr){
         		loadFormData(aipFileStr, null, null);
         	}
         },
         error: function (jqXHR, textStatus, errorThrown){
         }
    });
	var aipObj=document.getElementById("HWPostil1");
	//alert(aipObj.GetValueEx("signsuggest",2,"",0,"")+"<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>");
	//在获取大字段保存之前需要先操作表单
	//batchSCSign(aipObj);
	//alert(aipObj.GetNoteNum(251));
	//return;
    //获取大字段信息
	var docconent = aipObj.GetCurrFileBase64();
	
	//表单所有节点及值
	var noteinfo = getNoteMap();
	//
	var data = getParamMapFromListPage();
	if(docconent){
		data["docconent"] = docconent;
	}
	if(flowdraftid){
		data["flowdraftid"] = flowdraftid;
	}
	if(solutionid){
		data["solutionid"] = solutionid;
	}
	if(tempid){
		data["tempid"] = tempid;
	}
	if(flowdefid){
		data["flowdefid"] = flowdefid;
	}
	if(masterslave){
		data["masterslave"] = masterslave;
	}
	if(operateType){
		data["operateType"] = operateType;
	}
	if(bizName){
		data["bizName"] = bizName;
	}
	if(dnodeid){
		data["freeDnodeid"] = dnodeid;
	}
	if(uuid){
		data["operateid"] = uuid;
	}
	if(noteinfo){
		data["noteinfo"] = noteinfo+"";
	}
	//批量拒绝或批量退回的原因
	data["backnoreason"] = backnoreason;
	var sealnum = aipObj.GetNoteNum(251);//电子签章的个数
	data["sealnum"] = sealnum;
	
	//
	var url= path + "/ajaxSaveBaseDoc.action?1=1";
    $.ajax({
        url:encodeURI(encodeURI(url)),
        data: data,
        type: "post",
        async:false,
        success: function (text) {
        	var data = mini.decode(text);
        	if(text["rows"] !=1 ){
        		//将操作不成功的数据存起来,以便在结束后展示给用户
        		//后继处理
        		data["status"];
        	}
        },
        error: function (jqXHR, textStatus, errorThrown) {
        }
    });
}
   	
//案件归档
function caseFiling(menuid,filetype){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
    	if(confirm("确定要操作吗？")){
    		var displayInfo2User = '';
    		//采集flowdraftid
    		var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push(row.flowdraftid);
            }
			var flowdraftids = flowdraftidArr.join(',');
			//采集结束
			//向后台查找符合归档条件的flowdraftid，并显示不符合归档的信息
    		var url = path + "/case/ajaxCheckCaseFiling.json";
	       	$.ajax({
	                url: url,
	                type: 'POST',
	                cache:false,
	             	async:false,
	                data:{flowdraftids : flowdraftids, filetype:filetype},
	                success: function (text){
	                	var data = mini.decode(text);
						//得到不符合归档的文书，显示给用户
	                	displayInfo2User = data.notFitInfos;
	                	flowdraftidArr = data.flowdraftids;
	                	//得到符合的flowdraftids
	                	if(flowdraftids){
	                		batchCaseFiling(flowdraftidArr, filetype);
	                	}
	                },
	                error: function () {
	                	alert("操作失败!");
	                }
	         });
	         
	         //得到不符合归档的文书，显示给用户
	         if(displayInfo2User){
	         	alert(displayInfo2User);
	         }
	         grid.reload();
    	}
    }else{
        alert( "请至少选中一条记录！");
    }
}
	
//开始批量归档
function batchCaseFiling(flowdraftidArr, filetype){
	for(var i=0,l=flowdraftidArr.length; i<l; i++){
		singleCaseFiling(flowdraftidArr[i], filetype);
	}
}

//单个归档
function singleCaseFiling(flowdraftid, filetype){
	//需要组装的大字段处理：如封皮等
	var afterDealData = [];
	var url = path + "/case/getCaseFilingAssembleData.json?1=1";
   	$.ajax({
            url: url,
            type: 'POST',
            cache:false,
         	async:false,
            data:{flowdraftid : flowdraftid, filetype:filetype},
            success: function (text){
            	if(text){
            		afterDealData = assembleCaseFileData(text);
            	}
            },
            error: function () {
            	alert("操作失败!");
            }
     });
     caseFileWithData(flowdraftid, afterDealData, filetype);
}

//组装案件归档数据
function assembleCaseFileData(dataStr){
	if(dataStr){
		var afterDealData = [];
		var beforeDealData = mini.decode(dataStr);
		for(var i=0,l=beforeDealData.length; i<l; i++){
			var map = beforeDealData[i];
			//
			var aipFileStr = map.aipFileStr;
			var formdata = map.formdata;
			loadFormData(aipFileStr, formdata, null);
			//
			var aipObj = document.getElementById("HWPostil1");
    		aipFileStr = aipObj.GetCurrFileBase64();
			map.aipFileStr = aipFileStr;
			map.formdata = '';   //将map中的表单数据清除
			afterDealData.push(map);
			//
		}
		return afterDealData;
	}
}

//将组装的数据传回后台(如果有的话)，并开始归档
function caseFileWithData(flowdraftid, afterDealData, filetype){
	var data = mini.encode(afterDealData);
	var url = path + "/case/caseFileWithData.json?1=1";
	$.ajax({
         url: url,
         data: {data:data, flowdraftid:flowdraftid, filetype:filetype},
         type: 'POST',
         async:false,
         success: function (text){
         	
         },
         error: function (jqXHR, textStatus, errorThrown) {
          	   
         }
     });
     
}
	
/**
 * 数据导出：YangZR		2015-06-02
 * @param {} menuid	
 * @param {} excid	导出方案id		在表TBSYS_EXCHANGESCHEME中配制
 * @param {} isArch : 1 导出电子档案
 * @return {Boolean}
 */
function dataExport(menuid, excid, isArch){
	var rows = grid.getSelecteds();
	if(rows.length > 0){
		if(confirm("确定要导出数据吗？")){
			//setTimeout(meaninglessOperate, 50);
			progressDesc();
			var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push('\''+row.flowdraftid+'\'');
            }
			var flowdraftids = flowdraftidArr.join(',');
			dataExportByParam(menuid, excid, isArch, flowdraftids);
		}
	}else{
		alert("请选择记录！");
		return false;
	}
}

/**
 * 数据导出：YangZR		2015-10-28
 * @param {} menuid
 * @param {} excid	导出方案id		在表TBSYS_EXCHANGESCHEME中配制
 * @param {} isArch : 1 导出电子档案
 * @flowdraftids 流程草稿
 * @return {Boolean}
 */
function dataExportByParam(menuid, excid, isArch, flowdraftids){
	var url = path + '/dataExport.json';
	//
	$.ajax({
        url: url,
        dataType:"text",
        data: {excid:excid, flowdraftids:flowdraftids, isArch:isArch},
        type: "post",
        success: function (data){
        	data = mini.decode(data);
        	if(data.status==1){
        		var filepath = data.filepath;
        		var allPath = parseFilePath(filepath);
        		if(allPath){
        			if(confirm("数据已生成，是否现在导出？")){
        				iframeDownloadFile(allPath);
					}
        		}
        	}else{
        		alert("操作失败！");
        	}
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	alert("操作失败！");
        }
    });
	
}


/**
 * 批量数据发送：YangZR		2017-01-12
 * @param menuid	
 * @param excid	导出方案id		在表TBSYS_EXCHANGESCHEME中配制
 * @param isArch : 1 发送电子档案
 * @param type : 1:发送到监狱；2：发送到监狱局；11：发送到中院；12：发送到高院；21：发送到地级市检察院；22：发送到省级检察院
 * @param message : 发送的附加说明信息，生成的文件时，命名会用到此信息
 * 
 */
function batchSendData2Remote(menuid, excid, isArch, type, message){
	var rows = grid.getSelecteds();
	
	sendData2Remote(rows, menuid, excid, isArch, type, message);
}

/**
 * 单个数据发送：YangZR		2017-01-12
 * @param menuid	
 * @param excid	导出方案id		在表TBSYS_EXCHANGESCHEME中配制
 * @param isArch : 1 发送电子档案
 * @param type : 1:发送到监狱；2：发送到监狱局；11：发送到中院；12：发送到高院；21：发送到地级市检察院；22：发送到省级检察院
 * @param message : 发送的附加说明信息，生成的文件时，命名会用到此信息
 * 
 */
function singleSendData2Remote(menuid, excid, isArch, type, message){
	var rows = [];
	rows[0] = grid.getSelected();
	
	sendData2Remote(rows, menuid, excid, isArch, type, message);
}
	
	
	
/**
 * 数据发送：YangZR		2017-01-12
 * @param menuid	
 * @param excid	导出方案id		在表TBSYS_EXCHANGESCHEME中配制
 * @param isArch : 1 发送电子档案
 * @param type : 1:发送到监狱；2：发送到监狱局；11：发送到中院；12：发送到高院；21：发送到地级市检察院；22：发送到省级检察院
 * @param message : 发送的附加说明信息，生成的文件时，命名会用到此信息
 * 
 */
function sendData2Remote(rows, menuid, excid, isArch, type, message){

	if(rows.length > 0){
		if(confirm("确定要发送数据吗？")){
			progressDesc();
			var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push('\''+row.flowdraftid+'\'');
            }
			var flowdraftids = flowdraftidArr.join(',');
			
			$.when(sendData2RemoteImpl(menuid, excid, isArch, flowdraftids, type, message)).done(function(data){
				
				data = mini.decode(data);
	        	if(data.status==1){
	        		
	        	}else{
	        		//alert("操作失败！");
	        	}
				
	        	grid.reload();
	        	
			});
			
		}
	}else{
		alert("请选择记录！");
		return false;
	}
}
	
	
	
	
/**
 * 单个数据发送：YangZR		2017-01-12
 * @param menuid	
 * @param excid	导出方案id		在表TBSYS_EXCHANGESCHEME中配制
 * @param isArch : 1 发送电子档案
 * @param type : 1:发送到监狱；2：发送到监狱局；11：发送到中院；12：发送到高院；21：发送到地级市检察院；22：发送到省级检察院
 * @param message : 发送的附加说明信息，生成的文件时，命名会用到此信息
 */
function sendData2RemoteImpl(menuid, excid, isArch, flowdraftids, type, message){
	
	var defer = $.Deferred();
	var url = path + '/sendData2RemoteImpl.json';
	//
	$.ajax({
        url: url,
        dataType:"text",
        data: {excid:excid, flowdraftids:flowdraftids, isArch:isArch, type:type, message:message},
        type: "post",
        success: function (data){
        	defer.resolve(data);
        }
    });
	
	return defer.promise();
}
	
/**
 * 为页面遮罩需要走一个ajax，无任何业务意义
 */
function meaninglessOperate(){
	var opfg = ""; 
   	$.ajax({
		url: path + "/sxexamineCheckForWork/meaninglessOperate.json?1=1",
		dataType:"text",
		async:false,
    	success: function (text) {
    		opfg = text;
    	},
    	error: function () {
    	}
	}); 
   	
   	if(1==opfg){
   		//设置遮罩
         grid.loading("操作中请稍后...");
   	}
}


function parseFilePath(filepath){
	//
	if(!filepath){
    	alert("文件不存在!");
		return;
	}
	//
	var startSlash = filepath.indexOf("/");
	// 不是斜线开头
	if(0 != startSlash){
		basePathLessSlash += "/";
	}
	// 下载的文件路径
	return basePathLessSlash + filepath;
};
    
// 通过 Iframe下载文件
function iframeDownloadFile(allPath){
	//
	var download_iframe = document.getElementById('download_iframe');
	if(!download_iframe){
		//
		var d = document.createElement("div");
		d.style.display="none";
		//
		var i = document.createElement("iframe");
		i.id="download_iframe";
		//
		d.appendChild(i);
		//
		document.documentElement.appendChild(d);
		download_iframe = document.getElementById('download_iframe');;
	}
	//
	window.onerror = function(){
		alert("下载失败");
	};
	try {
		//delete download_iframe.src;
		download_iframe.src = allPath;
	} catch(ex){
		alert("下载失败!");
	}
	return false;
};

/////////////////////////////////////////////////////////////////////////////

function myloading(){
	var myform = new mini.Form("datagrid");
	myform.loading("操作中请稍后...");
}

function myunmask() {
	var myform = new mini.Form("datagrid");
	myform.unmask();
}


// 刷新本页面
function refreshPage(){
	if(!window["____refreshPage"]){window["____refreshPage"] = true;location.reload();
	} else {
		window.setTimeout(function(){window["____refreshPage"] = false;},1*1000);
	}
};
	
function BrowseFolder(){
	 try{
		  var Message = "请选择文件夹";  //选择框提示信息
		  var Shell = new ActiveXObject( "Shell.Application" );
		  var Folder = Shell.BrowseForFolder(0,Message,0x0040,0x11);//起始目录为：我的电脑
		  //var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
		  if(Folder != null){
			  Folder = Folder.items();  // 返回 FolderItems 对象
			  Folder = Folder.item();  // 返回 Folderitem 对象
			  Folder = Folder.Path;   // 返回路径
			  if(Folder.charAt(Folder.length-1) != "\\"){
			  	  Folder = Folder + "\\";
			  }
			  return Folder;
		  }
		  return null;
	 }catch(e){ 
	 	  alert("请在ie设置中启用AcitveX控件相关选项。");
	 	  return null;
	 }
}
	
		
/**更新兑现时间2015-06-11*/
function updateDate(menuid){
	var rows = grid.getSelecteds();
	var duixiandate = mini.get("duixiandate").getValue();
	if(duixiandate==''){
		alert("请选择兑现时间");
		return false;
	}
	if(rows.length > 0){
		if(confirm("确定操作选中的记录吗？")){
			var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push('\''+row.flowdraftid+'\'');
            }
			var flowdraftids = flowdraftidArr.join(',');
			var url = path + '/updateDate.json?1=1&duixiandate='+duixiandate;
    		//
    		$.ajax({
                url: url,
                data: {flowdraftids:flowdraftids, menuid:menuid},
                type: "post",
                success: function (message){
                	message = mini.decode(message);
                	var info = message["info"];
	        	    var status = message["status"];
	        	    if(info){
	        	    	alert(info);
	        	    }else{
	        	    	if(status==1){
	        	    		alert("操作成功！");
	        	    	}else{
	        	    		alert("操作失败！");
	        	    	}
	        	    }
	        	    //
	        	    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败！");
                }
            });
			
		}
        //    
	}else{
		alert("请选择记录！");
		return false;
	}
}
	
/**撤销奖惩，更新时间、填写原因2015-06-11---暂时不用-留着备用*/
function updateChexiao(menuid){
	var rows = grid.getSelecteds();
	if(rows.length > 0){
		if(confirm("确定操作选中的记录吗？")){
			var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push('\''+row.flowdraftid+'\'');
            }
			var flowdraftids = flowdraftidArr.join(',');
			mini.open({
		        url: "updateChexiao.page?flowdraftid="+flowdraftids ,
		        showMaxButton: true,
		        allowResize: false,
		        title: "撤销", width: 700, height: 350,
		        onload: function () {
		            var iframe = this.getIFrameEl();
		            var data = {action : "edit"};
		            iframe.contentWindow.SetData(data);
		        },
		        ondestroy: function (action) {
		        	grid.reload();
		        }
	    	});
			
		}
        //    
	}else{
		alert("请选择记录！");
		return false;
	}
}
    
/**
 * excid : 数据交换采用的交换方案
 * isArch : 是否传递电子档案
 * departid :  webService的目地方单位ID
 * sdstatus : 发送后的设置状态
 */
function webServiceSender(excid, isArch, departid, sdstatus){
	var rows = grid.getSelecteds();
	if(rows.length > 0){
		var doFlag = confirm("确定操作选中的记录吗？");
		if(doFlag){
			//显示页面遮罩
			progress();//显示进度条
			//
			var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               flowdraftidArr.push('\''+row.flowdraftid+'\'');
            }
			var flowdraftids = flowdraftidArr.join(',');
			var data = {};
			if(excid){
				data.excid= excid;
			}
			if(isArch){
				data.isArch= isArch;
			}
			if(departid){
				data.departid= departid;
			}
			if(sdstatus){
				data.sdstatus= sdstatus;
			}
			if(flowdraftids){
				data.flowdraftids = flowdraftids;
			}
			var url = path +'/webServiceSender.json?1=1';
			$.ajax({
		        url: url,
		        data : data,
		        type: "post",
		        dataType:"text",
		        success: function (text){
		        	text = mini.decode(text);
		    	    if(text=='1'){
			    		alert("操作成功！");
			    		grid.reload();
			    	}else{
			    		alert("操作失败！");
			    		grid.reload();
			    	}
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        	alert("操作失败！");
		        	grid.reload();
		        }
		    });
		}
        //    
	}else{
		alert("请选择记录！");
		grid.unmask();
		return false;
	}
}
    
function progress(){
	var func = function myloading(){
		var percent = -1;
		var myform = new mini.Form("datagrid1");
  		$.ajax({
      		url: path + "/commutationParole/getPercent.json?1=1",
      		dataType:"text",
      		async:false,
          	success: function (text){
				percent = text;
          	},
          	error: function(){
          		myform.unmask();
          		return;
          	}
      	});
  		//
  		percent = percent < 0 ? 0 : percent;
  		myform.loading("<div><div style=\"float:left;text-align:center; line-height:25px;\">进度：</div><div><div style=\"float:left;text-align:center; border:1px solid #0066FF; width:400px; height:25px;\"><div style=\"float:left; width:" + percent + "%; height:25px; background-color:#5DA8CF\">"+percent+"%</div></div></div>");
		if(percent==100.0){
    		$.ajax({
        		url: path + "/commutationParole/getPercent.json?1=1&type=remove",
        		dataType:"text",
        		async:false,
            	success: function (text) {
            	},
            	error: function () {
            	}
        	}); 
			clearInterval(progress);
			var timefunc = function() {progressunmask();};
			var timer_timeout = setTimeout(timefunc, 1000);
		}
  	}
	var progress = window.setInterval(func,500);
}
 
function getFlowdraftidsByRows(rows){
	var flowdraftidArr = [];
    for(var i = 0, l = rows.length; i < l; i++) {
        var row = rows[i];
        flowdraftidArr.push('\''+row.flowdraftid+'\'');
    }
    var flowdraftids = flowdraftidArr.join(',');
	return flowdraftids;
}


/**
 * 对于案件的打开页面，lock: 判断是否需要加锁，noOperate：打开页面后不充许操作
 * @param {Object} menuid
 * @return {TypeName} 
 */
function toywgkFatherTabPage(menuid,lock, noOperate){
	if(lock==undefined || lock==null){
		lock=1;
	}
	ywgkFatherTabs(menuid,lock, noOperate);
}   

function ywgkFatherTabs(menuid,lock, noOperate){
	var tmenuid = menuid;	   
	var rows = grid.getSelecteds();
    if (rows.length > 0){
    	var temp_flowdraftid = rows[0].flowdraftid;
    	if(temp_flowdraftid){
    		var idArr = [];
       		var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               if(row.menuid){
               		menuid = row.menuid;
               }
              
               idArr.push(row.flowdraftid+'@'+menuid+'@'+row.modelname);
               var flowdraftid=row.flowdraftid;
               flowdraftidArr.push(row.flowdraftid);
            }
			var ids = idArr.join(',');
			var flowdraftids = flowdraftidArr.join(',');
			
			if(lock==1){
				//查询被加锁的案件是否属于当前的用户
	            var returnValue = ajaxReturnLockUser(flowdraftids);
	       		if(returnValue==false){
	       			grid.reload();
	       			return;
	       		}
	       		
		       	//相关案件加锁
		       	returnValue = lockCaseOfThisUser(flowdraftids);
		       	
		       	if(returnValue==false){
		       		grid.reload();
		       		return;
		       	}
			}
	       	//用于返回到主页面的url
	       	var furl = encodeURIComponent(location.href);
            var url = "toYwgkFatherTabPage.action?1=1&ids="+ids+"&menuid="+tmenuid+"&indexFlag="+0+"&noOperate="+noOperate+"&furl="+furl;
            self.location.href=url;
    	}else{
    		var idArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               idArr.push(row.applyid+'@'+menuid);
            }
			var ids = idArr.join(',');
			
	       	//用于返回到主页面的url
	       	var furl = encodeURIComponent(location.href);
            var url = "toYwgkFatherTabPage.action?1=1&ids="+ids+"&menuid="+tmenuid+"&optype=add"+"&indexFlag="+0+"&noOperate="+noOperate+"&furl="+furl;
            self.location.href=url;
    	}
   		
    }else {
        alert( "请至少选中一条记录！");
    }      
}


//保存未安犯数据
function operateWeiAnData(operateType,bizName){
	//如果是提交、退回、拒绝时，要判断签章进程
	if('yes'==operateType||'back'==operateType||'no'==operateType){
		//提示操作
		var chsOperateType = getOperateType(operateType);
		var confirmInfo = chsOperateType;
		var confFlag = confirm(confirmInfo);
		if(false == confFlag){
			return;
		}
		
		//判断某些表单是否已制作
		var flag = isPapersMaked(operateType);
		if(false == flag){
			return;
		}
		
		//判断签章进程
		var flag = controlOperateBehavior(operateType);
		if(false == flag){
			return;
		}
	}
	
	//var flag = savedata('master',operateType,bizName);
	form.validate();
    if (form.isValid() == false) return false;
    
	//获取大字段信息
	var aipObj=document.getElementById("HWPostil1");
	var docconent = aipObj.GetCurrFileBase64();
	//表单所有节点及值
	var noteinfo = getNoteMap();
	
	var masterslave = 'master';
	var flag = SaveWeiAnData(docconent, noteinfo, masterslave, operateType, bizName);
    var flag = false;
    
	if(false == flag) return flag;
	
	if(operateType=='yes'||operateType=='no'||operateType=='back'){
		toNext(1);
	}
}

//---狱政管理工作月报表  begin 20150907
function operateYZGLData(operateType,bizName){
	//判断某些表单是否已制作
	var flag = isPapersMaked(operateType);
	if(false == flag){
		return;
	}
	var form = new mini.Form("form1");
	form.validate();
    if (form.isValid() == false) return false;
    
	//获取大字段信息
	var aipObj=document.getElementById("HWPostil1");
	var docconent = aipObj.GetCurrFileBase64();
	//表单所有节点及值
	var noteinfo = getNoteMap();
	var masterslave = 'master';
	var flag = SaveYZGLsData(docconent, noteinfo, masterslave, operateType, bizName);
}


//山西三类犯报备
//menuid 资源id
//states  用于区分监狱状态，省局状态，法院状态
//orgFlg	数据源标记。0:监狱；1省局；2法院；3检察院
/*
function sanleifanbaobei(menuid,orgflg,states){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
	var temp_flowdraftid = rows[0].flowdraftid;
		if(temp_flowdraftid){
			//var idArr = [];
	   		var flowdraftidArr = [];
	        for (var i = 0, l = rows.length; i < l; i++){
	           var row = rows[i];
	           if(row.menuid){
	           		menuid = row.menuid;
	           }
	           //idArr.push(row.applyid+'@'+row.flowdraftid+'@'+row.flowdefid+'@'+row.flowid+'@'+row.departid+'@'+row.anjianhao);
	           flowdraftidArr.push(row.flowdraftid);
	        }     
			//var ids = idArr.join(',');
			var flowdraftids = flowdraftidArr.join(',');
						
			var url = path + "/addSanLeiFanBaoBei.json?1=1&menuid="+menuid+"&flowdraftids="+flowdraftids+"&orgflg="+orgflg+"&states="+states;
			$.ajax({
		        url: url,
		        data : "",
		        type: "post",
		        success: function (text){
		        	text = mini.decode(text);
			    		alert("操作成功！");
			    		grid.reload();
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        	alert("操作失败！");
		        	myunmask();
		        	grid.reload();
		        }
		    });
		}
		
    }
}
*/


/**
 * 公共报表页面按钮：
 * @author YangZR	2015-10-23
 * @param menuid		菜单ID
 * @param flowdefid		流程ID
 * @param otherParam	其它参数，格式为 a=1&b=she&f=ttt (向查询方案传递参数)
 */
function report(menuid, flowdefid, otherParam) {
	var url = "baseReportOperate.page?1=1&optype=add";
	formReport(url, menuid, flowdefid, otherParam);
}

function formReport(url, menuid, flowdefid, otherParam){
	if(menuid){
		url += "&menuid="+menuid;
	}
	if(flowdefid){
		url += "&flowdefid="+flowdefid;
	}
	if(otherParam){
		if(otherParam.substr(0, 1) != '&'){
			otherParam = "&" + otherParam;
		}
		url += otherParam;
	}
	var win = mini.open({
		url : url,
		showMaxButton : true,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		// data: { menuid:row.resid,anjianhao:anjianhao,
		// caseyear:caseyear,tempid:row.planid,flag:"flag"},
		allowResize : true,
		title : ""
	});
	win.max();
}

//批量设置值(菜单ID，查询方案ID，需要设置的结点ID，设置类型)
//设置类型：1:离监日期/刑期止日  可以配置其他的参数，填写参数名即可
function batchAipObjSetValue(menuid,solutionid,nodeid,type){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
    	if(confirm("确定要操作吗？")){
    		var aipObj = document.getElementById("HWPostil1");
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               var flowdraftid = row.flowdraftid;
               var value="";
               if(type){
            	   if(type == '1'){
            		   var url = "flow/ajaxSearchChuJianDateByFlowdraftID.json?1=1";
	             		$.ajax({
	                        url: url,
	                        data: {solutionid:solutionid,menuid:menuid,flowdraftid:flowdraftid,type:type},
	                        type: "post",
	                        async: false,
	                        success: function (text){
	                       	text = eval('('+text+')');
	                       	value=text.data;
	                       	if(value == ''){
		            			   alert("没有找到该犯人的离间日期/刑期止日！");
		    	            	   return false;
		            		  }
	                        },
	                        error: function (jqXHR, textStatus, errorThrown){
	                        	
	                        }
	                    });
            	   }else{
            		   value = mini.get(type).getFormValue();
            		   value = value.replace(/\-/g,"");
            		   if(value == ''){
            			   alert("请填写需要设置的值！");
    	            	   return false;
            		   }
            	   }
               }
                singleSetValue(aipObj, menuid, solutionid, flowdraftid,nodeid,type,value);
            }
            alert("操作成功！");
            //refreshPage();
    	}
    }else {
        alert( "请至少选中一条记录！");
    }
}

//单个操作
function singleSetValue(aipObj,menuid,solutionid,flowdraftid,nodeid,type,value){
		var url = "flow/ajaxGetFormBySolutionID.json?1=1";
		$.ajax({
         url: url,
         data: {solutionid:solutionid,menuid:menuid,flowdraftid:flowdraftid,type:type},
         type: "post",
         async: false,
         success: function (text){
        	text = mini.decode(text);
        	if(text.data){
        		setValue(aipObj,text.data,nodeid,value,solutionid,flowdraftid);	
        	}else{
        		alert("没有找到相应的数据！");
        		return false;
        	}
         },
         error: function (jqXHR, textStatus, errorThrown){
         	
         }
     });
}

//设置值，参数：大字段，结点id，值
function setValue(aipObj,annexcontent,nodeid,value,solutionid,flowdraftid){
	aipObj.LoadFileBase64(annexcontent);
	if(annexcontent){
		if(nodeid){
			if(value){
				aipObj.SetValue(nodeid,"");
				aipObj.SetValue(nodeid,value);
				var aipFileStr=aipObj.GetCurrFileBase64();
				if(aipFileStr){
					//更新大字段
					singleSaveAipObj(aipFileStr,solutionid,flowdraftid);
				}
			}else{
				alert("没有找到相应的值！");
				return false;
			}
		}else{
			alert("没有找到相应的结点！");
			return false;
		}
	}else{
		alert("没有找到相应的表单！");
		return false;
	}
}

function singleSaveAipObj(aipFileStr,solutionid,flowdraftid){
	var url = "flow/ajaxSaveFormBySolutionID.json?1=1";
	$.ajax({
         url: url,
         data: {solutionid:solutionid,aipFileStr:aipFileStr,flowdraftid:flowdraftid},
         type: "post",
         async: false,
         success: function (text){
        	text = mini.decode(text);;
         },
         error: function (jqXHR, textStatus, errorThrown){
         	
         }
     });
}



/**
 * 公共表单页面按钮：
 * @author YangZR	2015-10-23
 * @param menuid		菜单ID
 * @param flowdefid		流程ID
 * @param otherParam	其它参数，格式为 a=1&b=she&f=ttt  (向查询方案传递参数)
 */
function _form(menuid, flowdefid, otherParam) {
	var url = "baseDocOperate.page?1=1";
	formReport(url, menuid, flowdefid, otherParam);
}


function validateFlowCondition(flowdraftids, operateType){
	
	var result = null;
	var url = path + "/validateFlowCondition.json?1=1";
	$.ajax({
	     url: url,
	     data: {flowdraftids:flowdraftids, locktype: operateType},
	     type: "post",
	     async: false,
	     success: function (text){
	    	 result = mini.decode(text);
	     },
	     error: function (jqXHR, textStatus, errorThrown){
	     	
	     }
	});
	return result;
}



//解锁铵钮
function unlockBtn(){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
   		var flowdraftidArr = [];
        for (var i = 0, l = rows.length; i < l; i++){
           var row = rows[i];
           flowdraftidArr.push(row.flowdraftid);
        }
		var flowdraftids = flowdraftidArr.join(',');
		//查询当前不能解锁的案件：案件由谁锁定只能其本人解锁
		var url= path + "/flow/operateUnLockCases.json?1=1";
        $.ajax({
             url: url,
             data: {flowdraftids:flowdraftids},
             type: "post",
             success: function (text) {
            	var message = mini.decode(text);
            	if(message && message.info){
            		alert(message.info);
            	}
             	grid.reload();
             },
             error: function (jqXHR, textStatus, errorThrown) {
             	//alert("操作失败!");
             }
	    });
		
    }else{
    	alert( "请至少选中一条记录！");
    }
}


document.write('<script src="' + path + '/scripts/expandjs.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + path + '/scripts/specialjs.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + path + '/scripts/defaultjs.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + path + '/scripts/progressbar.js" type="text/javascript"></sc' + 'ript>');
   