var basePath = "";
if(document.getElementById("basePath")) basePath = document.getElementById("basePath").value;
//流程审批提交 首次
function doSubmitFlow(resid,operateType,flowdefid,tempid){
	doSubmit(resid,operateType,flowdefid,tempid);
}

//流程审批同意、拒绝、退回
function doApproveFlow(resid,operateType,flowdefid,tempid){
	doApprove(resid,operateType,flowdefid,tempid);
}

//流程操作 (保存、提交等)
function doSubmit(resid,operateType,flowdefid,tempid){
	var orgid = '';
	var flowid = '';
	var applyid = '';
	var applyname = '';
	var flowdraftid = '';
	var commenttext = '';
	var savetype = '';
	var examineid = '';

	var orgidObj = document.getElementById("orgid");
	var flowidObj = document.getElementById("flowid");
	var applyidObj = document.getElementById("applyid");	
	var applynameObj = document.getElementById("applyname");
	var flowdraftidObj = document.getElementById("flowdraftid");
	var commenttextObj = document.getElementById("commenttext");
	var savetypeObj = document.getElementById("savetype");
	var examineidObj = document.getElementById("examineid");

	if(orgidObj) orgid = orgidObj.value;
	if(flowidObj) flowid = flowidObj.value;
	if(applyidObj) applyid = applyidObj.value;
	if(applynameObj) applyname = applynameObj.value;
	if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
	if(commenttextObj) commenttext = commenttextObj.value;
	if(savetypeObj) savetype = savetypeObj.value;
	if(examineidObj) examineid = examineidObj.value;
	
	var data = getNoteMap();//表单所有节点及值
	var docconent = saveFile();//获取大字段
	
	$.ajax({
         url: basePath+"/flow/flowapprove.action", 
         data: {data:data,docconent:docconent,resid:resid,orgid:orgid,tempid:tempid,flowid:flowid,operateType:operateType,
        	 	applyid:applyid,applyname:applyname,flowdefid:flowdefid,flowdraftid:flowdraftid,examineid:examineid},
         type: "POST",
         dataType:"text",
         async:false,
         success: function (text){
         	var obj = text;
         	if(flowdraftidObj){
         	 	if(flowdraftid==''){
         			document.getElementById("flowdraftid").value = obj;
         		}
         	}
         	alert("操作成功!");
         	if(operateType !='save'){
         		saveNext(1);
         	}
         }
	});
}

function doApprove(resid,operateType,flowdefid,tempid){ 
	var applyid = '';
	var applyname = '';
	var flowid = '';
	var orgid = '';
	var flowdraftid = '';
	var examineid = '';
	var idObj = document.getElementById("applyid");	
	var nameObj = document.getElementById("applyname");
	var flowidObj = document.getElementById("flowid");
	var orgidObj = document.getElementById("orgid");
	var flowdraftidObj = document.getElementById("flowdraftid");
	var examineidObj = document.getElementById("examineid");
	
	if(idObj) applyid = idObj.value;
	if(nameObj) applyname = nameObj.value;
	if(flowidObj) flowid = flowidObj.value;
	if(orgidObj) orgid = orgidObj.value;
	if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
	if(examineidObj) examineid = examineidObj.value;
	
	var data = getNoteMap();//表单所有节点及值
	var docconent = saveFile();//获取大字段
	
	$.ajax({
         url: basePath+"/flow/flowapprove.action", 
         data: {data:data,docconent:docconent,resid:resid,operateType:operateType, orgid:orgid, flowdraftid:flowdraftid,
        	 	flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname,flowid:flowid,examineid:examineid},
         type: "POST",
         dataType:"text",
         async:false,
         success: function (text){
         	alert("操作成功!");
         	saveNext(1);
         }
	});
}
  	


	//不知道为啥 这个方法名 进不去singleApproveFlowYes
   	//流程审批同意、拒绝、退回
	function batchSubmitCase(id,countIndex,tempid,flowdefid,operateType){
		var idArr = id.split(',');
		var tempStr = idArr[countIndex];
		var dataArr = tempStr.split('@');
		
		var flowdraftid = dataArr[0];
		var flowid = dataArr[1];
		var orgid = dataArr[2];
		var resid = dataArr[3];
		var aipObj=document.getElementById("HWPostil1");
		aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		$.ajax({
             url: basePath+"/getCaseBigDataContent.json?1=1",
             data: {tempid:tempid, flowdraftid:flowdraftid},
             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
             cache: false,
             type: "post",
             async: false,
             success: function (text) {
 		          aipObj.CloseDoc(0);
 		          aipObj.LoadFileBase64(eval('('+text+')'));//打开模板文件
             }
             ,
             error: function (jqXHR, textStatus, errorThrown) {
            	 alert("操作失败!");
             }
		  });
		
		var data = getNoteMap();//表单所有节点及值
		var docconent = saveFile();//获取大字段
		
		$.ajax({
	         url: basePath+"/flow/flowapprove.action?1=1", 
	         data: {data:data,docconent:docconent,operateType:operateType,flowdraftid:flowdraftid,
			 flowdefid:flowdefid,tempid:tempid,flowid:flowid,orgid:orgid,resid:resid},
	         type: "POST",
	         dataType:"json",
	         async:false,
	         success: function (text){
	         	var obj = eval(text);
	         	if(obj!=-1){
	         		countIndex ++;
	         		if(countIndex == idArr.length){
	         			countIndex = 0;
	         			alert("操作成功");
	         			grid.reload();
	         		}else{
	         			batchSubmitCase(id,countIndex,tempid,flowdefid,operateType);
	         		}
	         	}
	         }
	     });
 	}

	function doApproveFlowAfterCheckSeal(type,number,resid,operateType,flowdefid,tempid){
		//判断是否需要检查签章情况，如果不为1 则表示不需要检查
		var checkType = 0;
		var isCheckSeal = document.getElementById("ischeckseal");
		if(isCheckSeal) checkType = isCheckSeal.value;
		if(checkType!=1){
			doApproveFlow(resid,operateType,flowdefid,tempid);
			return;
		}
		var result = '';
		var url=basePath+'/sign/getSignatureScheme.json?1=1';
		$.ajax({
	           url:url,
	           data: {resid:resid},
	           type:'post',
	           cache: false,
	           async:false,
	           success:function (text){
		           if(text){
			           	var objs = eval('('+text+')');
			           	result = objs.split('&')[0];
		           }
	           },
	           error: function (jqXHR, textStatus, errorThrown) {
         	 	return;
            }
	       });
		var aipObj = document.getElementById("HWPostil1");
		//检查签章方案情况
		 var sealNum=aipObj.GetNoteNum(1);//批注
		    var signatureNum = aipObj.GetNoteNum(251);//章 
		    var resultArr = result.split("@");
		    if((resultArr[0]!=-1&&resultArr[0]!=sealNum)||(resultArr[1]!=-1&&resultArr[1]!=signatureNum)){
		    	alert("批注或者签章的个数不对，请核对修改后再提交！");
		    	return;
		    }
		//获取 除去当前级别  表单上面应该有多少个章
		//var nowshow = noShow_signAndNotation(flowdefid,tempid);
		//var signnotation = nowshow.split('^');
	    //var signnum = parseInt(signnotation[0]);
	    //var notation = parseInt(signnotation[1]);
		
		//var returnStates = signAndSealCheck(sealNum,signatureNum,signnum,notation,operateType);
	    //if(returnStates == 1){
	    //	return;
	    //}
		doApproveFlow(resid,operateType,flowdefid,tempid);
		//减刑假释审核表更新签章进程 
		var flowdraftid = '';
		var flowdraftidObj = document.getElementById("flowdraftid");
		if(flowdraftidObj){
			flowdraftid = flowdraftidObj.value;
		}
	
		//保存减刑假释审批表时 根据盖章数目，流程草稿id更新签章进程 
		if((flowdefid=='other_zfjyjxjssp' ||flowdefid=='other_jybwjycbsp'|| flowdefid == 'other_sjjxjssp')&&flowdraftid!=''){
			updateProcessOfSignature(flowdraftid,operateType,flowdefid,tempid);
		}
	}

	//通过批量提交
	var countIndex = 0;
   	function commBatchSubmit(id, flowdefid,tempid){
        var operateType = 'yes';
        var idArr = id.split(',');
        if(countIndex < idArr.length){
        	batchSubmitCase(id,countIndex,tempid,flowdefid,operateType);
        }
   	}
	
	