var countIndex = 0;
   	function batchSubmit(resid){
   			var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                         //签章之后的案件才可以批量提交  
                        if(!ischeckseal=='1'||(resid=='12584'&&r.text8=='1')
                        ||(resid=='10091_01'&&r.text8=='2')
                        ||(resid=='12056'&&r.text8=='3')
                        ||(resid=='11434'&&r.text8=='5')){
                        	ids.push(r.crimid+"@"+r.orgid+"@"+r.flowid+"@"+r.flowdraftid+"@"+r.name);
                        }
                    }
                    var id = ids.join(',');
                    if(id!=''){
	                    var tempid = mini.get("tempid").getValue();
	                    var flowdefid = mini.get("flowdefid").getValue();
	                    var operateType = 'yes';
	                    if(countIndex < rows.length){
	                    	singleApproveFlowYes(resid,id,countIndex,tempid,flowdefid,operateType);
	                    }
                    }else{
                    	 alert('签章之后才能批量提交！');
                    }
                }
            } else {
                alert(confirmMessages);
            }
   	}
   	
   	
   	//流程审批同意、拒绝、退回
function singleApproveFlowYes(resid,id,countIndex,tempid,flowdefid,operateType){
		//r.crimid+"@"+r.orgid+"@"+r.flowid+"@"+flowdraftid+"@"+r.name
		var idArr = id.split(',');
		var tempStr = idArr[countIndex];
		var dataArr = tempStr.split('@');
		
		var applyid = dataArr[0];
		var orgid = dataArr[1];
		var flowid = dataArr[2];
		var flowdraftid = dataArr[3];
		var applyname = dataArr[4];
		
		var aipObj=document.getElementById("HWPostil1");	
		aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		
		$.ajax({
		             url: "getCaseBigDataContent.action?1=1",
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
		
//		alert(data);
//		alert(docconent);
		
//		alert(resid);
//		alert(operateType);
//		alert(flowdraftid);
//		alert(flowdefid);
//		alert(tempid);
//		alert(applyid);
//		alert(applyname);
//		alert(flowid);
//		alert(orgid);
		
		$.ajax({
	         url: "<%=path%>/flow/flowapprove.action", 
	         data: {data:data,docconent:docconent,resid:resid,operateType:operateType, orgid:orgid, flowdraftid:flowdraftid,
	        	 flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname,flowid:flowid},
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
	         			singleApproveFlowYes(resid,id,countIndex,tempid,flowdefid,operateType);
	         		}
	         	}
	         }
	     });
 	}