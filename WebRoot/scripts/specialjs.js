/**
 * 立功、重大立功的批量审批的特殊处理(立功在监狱审批结束，重大立功流程要走省局)
 * @author YangZR	2015-06-23
 */
function zdlgApprove(menuid, operateType, solutionid, bizName){
	batchFlowForwardOrEnd(menuid, operateType, solutionid, bizName);
}

/**
 * 
 */
function singleZdlgApprove(operateType,bizName){
	
	singleFlowForwardOrEnd(operateType,bizName);
	
}

/**
 * 减刑假释的批量审批的特殊处理(如果是无期、死缓、或重要罪犯的减刑假释，流程要走到省局，否则监狱审批结束)
 * @author YangZR	2015-06-23
 */
function jxjsApprove(menuid, operateType, solutionid, bizName){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
        if (confirm("确定操作选中记录？")){
        	//判断加锁情况
        	//后面在做处理
        	myloading();
	        var flowdraftids = getFlowdraftidsByRows(rows);
	        //检查签章规则，后面再完善
	        
			var result = distinguishSendToProvince(flowdraftids);
			if(result && result.endinjail){
				freeFlowOperateWithFlowdraftids(result.endinjail, menuid, operateType, solutionid, '1', bizName);
			}
			if(result && result.sendtoprovince){
				batchFlowOperateWithFlowdraftids(result.sendtoprovince, menuid, operateType, solutionid, bizName);
			}
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}

/**
 * (如果是无期、死缓、或重要罪犯的减刑假释，流程要走到省局，否则监狱审批结束)
 */
function singleJxjsApprove(operateType,bizName){
	
	var flowdraftid = mini.get('flowdraftid').getValue();
	var result = distinguishSendToProvince(flowdraftid);
	
	if(result && result.endinjail){
		freeFlowOperate(operateType, 1, bizName);
	}else if(result && result.sendtoprovince){
		operateMasterData(operateType,bizName);
	}
	
}


/**
 * 描述：区分出是否发送省局的flowdraftids
 * @author YangZR	2015-06-24
 * @return 返回{endinjail: 监狱审批结束的flowdraftids, sendtoprovince: 发送省局的flowdraftids}
 */
function distinguishSendToProvince(flowdraftids){
	var result;
	var url =  path +"/distinguishSendToProvince.json?1=1";
    $.ajax({
    	 url : url,
         type:'post',
         data: {flowdraftids:flowdraftids},
         async:false,
         success:function (text){
    	    result = mini.decode(text);
         }
    });
    return result;
}


/*
 * 
 * 罪犯专管页面判断不为空
 */
function zuiFanZhuanGuanData(operateType,bizName){
	var aipObj = getmyvalue("HWPostil1");
	var confinedays = aipObj.GetValueEx("confinedays",2,"",0,"");//天数
	var confinedate1 = aipObj.GetValueEx("confinedate1",2,"",0,"");//起始日期
	var confinedate2 = aipObj.GetValueEx("confinedate2",2,"",0,"");//终止日期
	if(! confinedays){
		alert("天数不能为空！");
	}else if(! confinedate1){
		alert("起始日期不能为空！");
	}else if(! confinedate2){
		alert("终止日期不能为空！");
	}else{
	     operateMasterData(operateType,bizName);
	}
}



//分案
function fenAnOne(menuid){
	var row = grid.getSelected();
	var keyuan = mini.get("keyuan").getValue();
	if (!keyuan) {
		alert("请选择科员");
		return;
	}
	var url =  path +"/flow/XingFaKeFenAn.action?1=1";
    $.ajax({
    	 url : url,
         type:'post',
         data: {flowdraftid:row.flowdraftid,keyuan:keyuan},
         //async:false,
         success:function (text){
        	 grid.reload();
    	    alert("操作成功");
         }
    });
}
//批量分案
function fenAnAll(menuid){
	var rows = grid.getSelecteds();
	var keyuan = mini.get("keyuan").getValue();
	if (!keyuan) {
		alert("请选择科员");
		return;
	}
	if(rows.length>0){
		if(confirm('确定批量分案吗？')){
			var ids=[];
			for(var i=0;i<rows.length;i++){
				var r=rows[i];
				ids.push(r.flowdraftid);
			}
			var flowdraftids=ids.join(',');
			$.ajax({
				url:path+"/flow/piLiangFenAn.action?1=1",
				data:{flowdraftids:flowdraftids,keyuan:keyuan},
				success:function(text){
					alert("操作成功");
					grid.load();
				},
				error: function(){
					alert("操作失败");
					return;
				}
			});
		}
	}else{
		alert("请至少选中一条记录");
	}
}


/**
 * 描述：狱政奖惩功能中，监狱长可将审批表提交给5大务业科室中的任何一个进行审批
 * @author : YangZR
 * @data:  2015-10-15
 */
function toNextKeShi(menuid, operateType, solutionid, bizName){
	var nextNodeid = mini.get("nextnodeid").getValue();
	if(! nextNodeid){
		alert("请选择下一级业务科室！");
		return;
	}
	batchFreeFlowOperate(menuid, operateType, solutionid, nextNodeid, bizName);
	
}

/**
 * 描述：狱政奖惩功能中，监狱长可将审批表提交给5大务业科室中的任何一个进行审批(单个审批)
 * @author : YangZR
 * @data:  2015-10-15
 */
function toSingleNextKeShi(operateType, bizName){
	var nextNodeid = mini.get("nextnodeid").getValue();
	if(! nextNodeid){
		alert("请选择下一级业务科室！");
		return;
	}
	freeFlowOperate(operateType, nextNodeid, bizName);
}


function getBackNodeidByCurrnodeid(flowdraftid, nodeid){
	var backnodeid = null;
	$.ajax({
		url:path+"/flow/getBackNodeidByCurrnodeid.json?1=1",
		data:{flowdraftid:flowdraftid, nodeid:nodeid},
		dataType: "text",
		async: false,
		success:function(text){
			backnodeid = text;
		},
		error: function (jqXHR, textStatus, errorThrown){
        }
	});
	return backnodeid;
}

/**
 * 退回到上一级节点（上一级节点不确定）（单个操作）
 * （这里要求上一级节点在提交时，要保存一条记录到TBFLOW_PERSONAPPROVE中）
 * @author YangZR	2015-10-16
 * @param operateType
 * @param currNodeid
 * @param bizName
 */
function toSingleBackNode(operateType, currNodeid, bizName){
	
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
	//查询上一级节点
	var backnodeid = getBackNodeidByCurrnodeid(flowdraftid, currNodeid);
	freeFlowOperate(operateType, backnodeid, bizName);
	
}

/**
 * 退回到上一级节点（上一级节点不确定）（批量操作）
 * （这里要求上一级节点在提交时，要保存一条记录到TBFLOW_PERSONAPPROVE中）
 * @author YangZR	2015-10-16
 */
function toBatchBackNode(menuid, solutionid, currNodeid, bizName){
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
	        toBatchBackNodeWithFlowdraftids(flowdraftids, menuid, 'back', solutionid, currNodeid, bizName);
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}

function toBatchBackNodeWithFlowdraftids(flowdraftids, menuid, operateType, solutionid, currNodeid, bizName){
	flowdraftidArr = flowdraftids.split(",");
    for(var i=0, l=flowdraftidArr.length; i<l; i++){
    	var flowdraftid = flowdraftidArr[i];
    	//查询上一级节点
    	var backnodeid = getBackNodeidByCurrnodeid(flowdraftid, currNodeid);
    	singleFlowOperate(menuid, operateType, flowdraftid, solutionid, bizName, backnodeid);
    }
}

/**
 * 描述：区分出流程继续走或直接结束的flowdraftids
 * @author YangZR	2015-10-16
 * @param {} flowdraftids
 * @return Map{forward: 流程继续走的flowdraftids, 	end: 流程结束的flowdraftids}
 */
function distinguishFlowForwardOrEnd(flowdraftids){
	var result;
	var url =  path +"/distinguishFlowForwardOrEnd.json?1=1";
    $.ajax({
    	 url : url,
         type:'post',
         data: {flowdraftids:flowdraftids},
         async:false,
         success:function (text){
    	    result = mini.decode(text);
         }
    });
    return result;
}


/**
 * 1、隔离审查单个处理： 根据隔离类型，如果为”违纪审查“，则Tbflow_base表的text12值为1，流程提交至狱政科，
 * 			如果为”案件审查“，则Tbflow_base表的text12值为2，流程提交至狱侦科，
 *      (前提：在保存表单时，将相关的状态保存至tbflow_base表的text12字段中)
 * @author YangZR	2015-10-30
 */
function singleGlscFlow(operateType,bizName){
	var flowdraftid = mini.get('flowdraftid').getValue();
	
	//distinguishFlowForwardOrEnd返回的map中，如果key为end，其流程基本表的text12为1，
	//如果key为forward，其流程基本表的text12为2，
	var result = distinguishFlowForwardOrEnd(flowdraftid);
	
	//result.end对应Tbflow_base表的text12值为1
	if(result && result.end){
		freeFlowOperate(operateType, 'N0003', bizName);
	}else if(result && result.forward){//result.forward对应Tbflow_base表的text12值为2
		freeFlowOperate(operateType, 'N0004', bizName);
	}
}


/**
 * 1、解除隔离审查单个处理： 根据隔离类型，如果为”违纪审查“，则Tbflow_base表的text12值为1，流程提交至狱政科，
 * 			如果为”案件审查“，则Tbflow_base表的text12值为2，流程提交至狱侦科，
 *      (前提：在保存表单时，将相关的状态保存至tbflow_base表的text12字段中)
 * @author YangZR	2015-10-30
 */
function singleJcGlscFlow(operateType,bizName){
	var flowdraftid = mini.get('flowdraftid').getValue();
	
	//distinguishFlowForwardOrEnd返回的map中，如果key为end，其流程基本表的text12为1，
	//如果key为forward，其流程基本表的text12为2，
	var result = distinguishFlowForwardOrEnd(flowdraftid);
	
	//result.end对应Tbflow_base表的text12值为1
	if(result && result.end){
		freeFlowOperate(operateType, 'N0010', bizName);
	}else if(result && result.forward){//result.forward对应Tbflow_base表的text12值为2
		freeFlowOperate(operateType, 'N0011', bizName);
	}
}


/**
 * 1、隔离审查批量处理：根据隔离类型，如果为”违纪审查“，则Tbflow_base表的text12值为1，流程提交至狱政科，
 * 如果为”案件审查“，则Tbflow_base表的text12值为2，流程提交至狱侦科，
 *      (前提：在保存表单时，将相关的状态保存至tbflow_base表的text12字段中)
 * @author YangZR	2015-10-30
 */
function batchGlscFlow(menuid, operateType, solutionid, bizName){
	
	var rows = grid.getSelecteds();
    if (rows.length > 0){
        if (confirm("确定操作选中记录？")){
        	//判断加锁情况
        	//后面在做处理
        	myloading();
	        var flowdraftids = getFlowdraftidsByRows(rows);
	        //检查签章规则，后面再完善
	        
	        //distinguishFlowForwardOrEnd返回的map中，如果key为end，其流程基本表的text12为1，
	    	//如果key为forward，其流程基本表的text12为2
			var result = distinguishFlowForwardOrEnd(flowdraftids);
			if(result && result.end){
				freeFlowOperateWithFlowdraftids(result.end, menuid, operateType, solutionid, 'N0003', bizName);
			}
			
			if(result && result.forward){
				freeFlowOperateWithFlowdraftids(result.end, menuid, operateType, solutionid, 'N0004', bizName);
			}
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}


/**
 * 1、解除隔离审查批量处理：根据隔离类型，如果为”违纪审查“，则Tbflow_base表的text12值为1，流程提交至狱政科，
 * 如果为”案件审查“，则Tbflow_base表的text12值为2，流程提交至狱侦科，
 *      (前提：在保存表单时，将相关的状态保存至tbflow_base表的text12字段中)
 * @author YangZR	2015-10-30
 */
function batchJcGlscFlow(menuid, operateType, solutionid, bizName){
	
	var rows = grid.getSelecteds();
    if (rows.length > 0){
        if (confirm("确定操作选中记录？")){
        	//判断加锁情况
        	//后面在做处理
        	myloading();
	        var flowdraftids = getFlowdraftidsByRows(rows);
	        //检查签章规则，后面再完善
	        
	        //distinguishFlowForwardOrEnd返回的map中，如果key为end，其流程基本表的text12为1，
	    	//如果key为forward，其流程基本表的text12为2
			var result = distinguishFlowForwardOrEnd(flowdraftids);
			if(result && result.end){
				freeFlowOperateWithFlowdraftids(result.end, menuid, operateType, solutionid, 'N0010', bizName);
			}
			
			if(result && result.forward){
				freeFlowOperateWithFlowdraftids(result.end, menuid, operateType, solutionid, 'N0011', bizName);
			}
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}




/**
 * 1、批量处理：流程继续流转或流程结束
 * 2、狱政奖惩中，如果罪犯为三类犯，或者奖分 >10则，流程从狱政科继续流转，否则在狱政科结束
 *      (前提：在保存表单时，通过查询方案的保存，将相关的状态保存至tbflow_base表的text12字段中)
 * 3、此函数现可以通用，不只限于狱政奖惩的功能
 * @author YangZR	2015-10-16
 */
function batchFlowForwardOrEnd(menuid, operateType, solutionid, bizName){
	
	var rows = grid.getSelecteds();
    if (rows.length > 0){
        if (confirm("确定操作选中记录？")){
        	//判断加锁情况
        	//后面在做处理
        	myloading();
	        var flowdraftids = getFlowdraftidsByRows(rows);
	        //检查签章规则，后面再完善
	        
        	//查询出哪些flowdraftids是流程结束  哪些flowdraftids是流程继续走
			var result = distinguishFlowForwardOrEnd(flowdraftids);
			//流程结束的flowdraftids ：result.end
			if(result && result.end){
				freeFlowOperateWithFlowdraftids(result.end, menuid, operateType, solutionid, '1', bizName);
			}
			//流程继续走的flowdraftids ：result.forward
			if(result && result.forward){
				batchFlowOperateWithFlowdraftids(result.forward, menuid, operateType, solutionid, bizName);
			}
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}

/**
 * 1、单个处理：流程继续流转或流程结束
 *      (前提：在保存表单时，通过查询方案的保存，将相关的状态保存至tbflow_base表的text12字段中)
 * @author YangZR	2015-10-16
 */
function singleFlowForwardOrEnd(operateType,bizName){
	var flowdraftid = mini.get('flowdraftid').getValue();
	var result = distinguishFlowForwardOrEnd(flowdraftid);
	if(result && result.end){//流程结束的flowdraftids ：result.end
		freeFlowOperate(operateType, 1, bizName);
	}else if(result && result.forward){//流程继续流转的flowdraftids ：result.forward
		operateMasterData(operateType,bizName);
	}
    
}


//test
function testButton(){
	alert("测试！！！");
}


/**
 * 减刑假释的省局批量审批的特殊处理(如果是无期、死缓的减刑假释要走完省局流程，如果是重要罪犯的减刑假释审批在省局处长处结束)
 * @author YangZR	2015-06-23
 * 省局减刑区分
 */
function sjjxApprove(menuid, operateType, solutionid, bizName){
	var rows = grid.getSelecteds();
    if (rows.length > 0){
        if (confirm("确定操作选中记录？")){
        	//判断加锁情况
        	//后面在做处理
        	myloading();
	        var flowdraftids = getFlowdraftidsByRows(rows);
	        //检查签章规则，后面再完善
	        
			var result = distinguishFinishAtSJCZ(flowdraftids);
			if(result && result.endinSJCZ){
				freeFlowOperateWithFlowdraftids(result.endinSJCZ, menuid, operateType, solutionid, '1', bizName);
			}
			if(result && result.keepGoing){
				batchFlowOperateWithFlowdraftids(result.keepGoing, menuid, operateType, solutionid, bizName);
			}
        	//
            myunmask();
            grid.reload();
        }
    } else {
        alert( "请至少选中一条记录！");
    }
    
}

/**
 * (如果是无期、死缓的减刑假释要走完省局流程，如果是重要罪犯的减刑假释审批在省局处长处结束)
 */
function singlesjjxApprove(operateType,bizName){
	
	var flowdraftid = mini.get('flowdraftid').getValue();
	var result = distinguishFinishAtSJCZ(flowdraftid);
	
	if(result && result.endinSJCZ){
		freeFlowOperate(operateType, 1, bizName);
	}else if(result && result.keepGoing){
		operateMasterData(operateType,bizName);
	}
}




/**
 * 描述：区分出是否在省局的处长处结束流程的flowdraftids
 * @author YangZR	2015-06-24
 * @return 返回{keepGoing: 无期死缓继续走流程的flowdraftids, endinSJCZ:重要罪犯三类犯在省局处长暂停流程的flowdraftids}
 */
function distinguishFinishAtSJCZ(flowdraftids){
	var result;
	var url =  path +"/distinguishFinishAtSJCZ.json?1=1";
    $.ajax({
    	 url : url,
         type:'post',
         data: {flowdraftids:flowdraftids},
         async:false,
         success:function (text){
    	    result = mini.decode(text);
         }
    });
    return result;
}
