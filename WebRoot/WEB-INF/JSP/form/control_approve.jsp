<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
	String applyvalue = (String) request.getAttribute("applyvalue");
%>
<SCRIPT LANGUAGE="JavaScript">
//流程审批提交 首次
function doSubmitFlow(resid,operateType,flowdefid,tempid){
	var flowdraftid = '';
	var flowdraftidObj = document.getElementById("flowdraftid");
	if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
	if('XFZX_FYLAB' == tempid || 'XFZX_FYJDLAB'==tempid || 'XFZX_FYJCLAB'==tempid || 'XFZX_FYBBLAB'==tempid){ //法院的保存  谁把我这个保存给去掉了！！！
		saveFYLABdata(resid,operateType,flowdefid,tempid,flowdraftid);
	}
	returnLockUserMain(flowdraftid,resid,operateType,flowdefid,tempid,0);
}
//流程审批同意、拒绝、退回
function doApproveFlow(resid,operateType,flowdefid,tempid){
	var flowdraftid = '';
	var flowdraftidObj = document.getElementById("flowdraftid");
	if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
	returnLockUserMain(flowdraftid,resid,operateType,flowdefid,tempid,1);
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

	//把参数值放入数组中
	var paramdata = [];
	paramdata.push(resid);
	paramdata.push(orgid);
	paramdata.push(tempid);
	paramdata.push(flowid);
	paramdata.push(applyid);
	paramdata.push(applyname);
	paramdata.push(flowdefid);
	paramdata.push(flowdraftid);
	paramdata.push(operateType);
	paramdata.push(commenttext);
	paramdata.join(",");

	//表单所有节点及值
	var data = getNoteMap();
	//获取大字段
	var docconent = saveFile();
	$.ajax({
         url: "<%=path%>/flow/flowapprove.action", 
         data: {data:data,docconent:docconent,resid:resid,orgid:orgid,tempid:tempid,flowid:flowid,operateType:operateType,
            applyid:applyid,applyname:applyname,flowdefid:flowdefid,flowdraftid:flowdraftid,examineid:examineid},
         type: "POST",
         dataType:"json",
         async:false,
         success: function (text){
         	var obj = eval(text);
         	if(flowdraftidObj){
         	 	if(flowdraftid==''){
         			document.getElementById("flowdraftid").value = obj;
         		}
         	}
         	alert("操作成功!");
         	if(operateType !='save'){
         		saveNext(1);
         	}else{
         		
            }
         }
	});
	//保存减刑假释审批表时 根据盖章数目，流程草稿id更新签章进程 
	if((flowdefid=='other_zfjyjxjssp' ||flowdefid=='other_jybwjycbsp')&&operateType=='save'&&flowdraftid!=''){
        //alert();
		var aipObj = document.getElementById("HWPostil1");
		var signatureNum = aipObj.GetNoteNum(1);//手写批注的个数
		var sealNum = aipObj.GetNoteNum(251);//电子签章的个数
        /*
         *获取签章的总个数和签章进程 进行比较
         *如:当前签章进程是 progress:1，那么 在保存的时候 判断页面一共有多少个签章allsign:0
         *如果 progress=allsign+1 那么可以进行签章 ，否者将无法签章
		*/
		var User1='';
    	//alert(aipObj.JSGetNextUser(User1));
    	var count =  0;
    	while(User1=aipObj.JSGetNextUser(User1)){
    		count++;
    	}
    	//签章进程 就是章的个数，存盘之后弹出页面点击确定时需要tempid:tempid,flowdefid:flowdefid因此在data中加上。张永有  2015年2月4号 下午
		var signature = sealNum;
		$.ajax({
	         url: "<%=path%>/penaltyPerform/ajaxsaveSignatureForAip.json", 
	         data: {flowdraftid:flowdraftid,signature:signature,operateType:operateType,tempid:tempid,flowdefid:flowdefid},
	         type: "POST",
	         dataType:"json",
	         async:false,
	         success: function (text){ 
	         	
	         }
		});
	}
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
	
	//退回、不同意时提示
	if(operateType == 'no' || operateType =='back'){
		var aipObj = document.getElementById("HWPostil1");
		var isagree = aipObj.GetValueEx("Page1.isagree", 2, "", 0, "");
		isagree = '0';
		if(isagree != '0'){
			alert("请先填写理由!");
			return;
		}
	}
	
	var data = getNoteMap();//表单所有节点及值
	var docconent = saveFile();//获取大字段
	$.ajax({
         url: "<%=path%>/flow/flowapprove.action", 
         data: {data:data,docconent:docconent,resid:resid,operateType:operateType, orgid:orgid, flowdraftid:flowdraftid,
        	 flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname,flowid:flowid,examineid:examineid},
         type: "POST",
         dataType:"json",
         async:false,
         success: function (text){
         	var obj = eval(text);
         	if(obj=='0'){
         		alert("操作成功!");
         		//onCancel("close");
         		window.close();
         	}
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
		
		var aipObj=document.getElementById("HWPostil1");
		aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		$.ajax({
             url: "<%=path%>/getCaseBigDataContent.action?1=1",
             data: { tempid:tempid, flowdraftid:flowdraftid},
             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
             cache: false,
             type: "post",
             async: false,
             success: function (text) {
 		          aipObj.CloseDoc(0);
 		          aipObj.LoadFileBase64(eval(text));//打开模板文件
             },
             error: function (jqXHR, textStatus, errorThrown) {
            	 alert("操作失败!");
             }
		  });
		         
		var data = getNoteMap();//表单所有节点及值
		var docconent = saveFile();//获取大字段
		
		$.ajax({
	         url: "<%=path%>/flow/flowapprove.action", 
	         data: {data:data,docconent:docconent,operateType:operateType,flowdraftid:flowdraftid,flowdefid:flowdefid,tempid:tempid,flowid:flowid,orgid:orgid},
	         type: "POST",
	         dataType:"json",
	         async:false,
	         success: function (text){
	         	var obj = eval(text);
	         	if(obj==0){
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
	//法院
 	function saveFYLABdata(resid,operateType,flowdefid,tempid,flowdraftid){
 		var data = getNoteMap();
 		var applyidObj = document.getElementById("applyid");
 		if(applyidObj){
 			var  applyid = applyidObj.value;
 			var snodeidobj =  document.getElementById("snodeid");
 			var snodeid;
 			if(snodeidobj){snodeid=snodeidobj.value;}
 			var url="<%=path%>/basicInfo/saveFYLABCrimidInfo.action?1=1&applyid="+applyid;
 			$.ajax({
 			url:url,
 			data:{data:data,tempid:tempid,resid:resid,operateType:operateType,flowdraftid:flowdraftid},
 			type:"post",
 			dataType:"json",
 			async:false,
 			success: function (text){
 					
 			},
 			error: function (jqXHR, textStatus, errorThrown){
 			}
 			});
 		}
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
		
		var aipObj = document.getElementById("HWPostil1");
	    //获取页面上面有多少个章和多少个批注
	    var sealNum=aipObj.GetNoteNum(1);//批注
	    var signatureNum = aipObj.GetNoteNum(251);//章 
	    
		//获取 除去当前级别  表单上面应该有多少个章
		var nowshow = noShow_signAndNotation(flowdefid,tempid);
		var signnotation = nowshow.split('^');
	    var signnum = parseInt(signnotation[0]);
	    var notation = parseInt(signnotation[1]);
		
		var returnStates = signAndSealCheck(sealNum,signatureNum,signnum,notation,operateType);
	    if(returnStates == 1){
	    	return;
	    }
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
   	//判断是否加锁
	function returnLockUserMain(flowdraftid,resid,operateType,flowdefid,tempid,type){
	    var flag = true;
	   // alert(type);return;
		var url = "<%=path%>/flow/ajaxReturnLockUser.json?1=1";
		$.ajax({
	        url: url,
	        data: {flowdraftid:flowdraftid},
	        type: "post",
	        success: function (text) {
	            var obj = text;
	            if(obj){
					if(obj != -1){
						alert("用户"+obj+"正在审批!");
			         	saveNext();
					}else{
						if(type == 0) doSubmit(resid,operateType,flowdefid,tempid);
						else doApprove(resid,operateType,flowdefid,tempid);
	                }
	            }
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	        	alert("操作失败!");
	        	var obj = document.getElementById("datagrid1");
	        	if(obj) mini.get("datagrid1").reload();
	        }
	  });
	}
</SCRIPT> 