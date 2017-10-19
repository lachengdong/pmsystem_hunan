<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
	String applyvalue = (String) request.getAttribute("applyvalue");
%>
<SCRIPT LANGUAGE="JavaScript">
	//获取签章和批注的个数 
 	//获取当前级和当前级别以前的所有的章的个数和批注的个数
 	function signAndNotation(flowdefid,tempid){
 		//提交的时候查询批注的个数 ，跟批注的个数 进行 判断是否添加批注
 	    //查询出 当前 用户可以批注的个数
 	    var signnotation = "";
 		$.ajax({
 	        type:'post',
 	        url:'<%=path%>/penaltyPerform/ajaxNonationNumber.action?1=1&flowdefid='+flowdefid+'&tempid='+tempid,
 	        async:false,
 	        success:function (text){
 	        	signnotation = text.replace(/"/g ,"");
 	        }
 	    });
 	    return signnotation;
 	}
 	//获取 除去当前级别 所有的章的个数和批注的个数
 	function noShow_signAndNotation(flowdefid,tempid){
 		//提交的时候查询批注的个数 ，跟批注的个数 进行 判断是否添加批注
 	    //查询出 当前 用户可以批注的个数 
 	    var signnotation = "";
 		$.ajax({
 	        type:'post',
 	        url:'<%=path%>/penaltyPerform/ajaxNoShowNonationNumber.action?1=1&flowdefid='+flowdefid+'&tempid='+tempid,
 	        async:false,
 	        success:function (text){
 	        	if(text){
 	        		signnotation = text.replace(/"/g ,"");
 	        	}
 	        }
 	    });
 	    return signnotation;
 	}
 	//提交  案件办理提交 (不同于经办人办案)
	function doSubmitFlowAfterCheckSeal(type,number,resid,operateType,flowdefid,tempid){
		var aipObj = document.getElementById("HWPostil1");
		
		//如果为减刑假释审批表的审批
		var flag = -1;
		if(flowdefid == 'other_zfjyjxjssp'){
			//判断 如果 jyconfig:chengpibiao=1 那么就受 呈批表的控制
			var chengpibiao = mini.get("chengpibiao");
			if(chengpibiao != undefined){
		        chengpibiao = chengpibiao.getValue();
		    }
		    //减刑假释审核表提交时，要求先做好呈批表   jyconfig:chengpibiao=1 那么就受 呈批表的控制
		    if(chengpibiao == 1){
	         	flag = iscommit(flowdraftid);//判断提交时是否保存过呈批表
				if(flag == 0 && operateType == 'yes'){
					 alert("您还没做呈批表!");
					 return;
				}
	    	}
	    	
	    	//判断 如果 forfeituretype=1 那么就考核期内的收入、支出是否填写（限天津）
			var forfeituretype = mini.get("forfeituretype");
			if(forfeituretype != undefined){
		        forfeituretype = forfeituretype.getValue();
		    }
		    if(forfeituretype == 1){
		    	var zclhq = aipObj.GetValueEx("zclhq",2,"",0,""); 
		        var gsk = aipObj.GetValueEx("gsk",2,"",0,""); 
		        var hk = aipObj.GetValueEx("hk",2,"",0,""); 
		        var qtsr = aipObj.GetValueEx("qtsr",2,"",0,""); 
		        var gwzc = aipObj.GetValueEx("gwzc",2,"",0,""); 
		        var payeverymon = aipObj.GetValueEx("payeverymon",2,"",0,""); 
		        var balance = aipObj.GetValueEx("balance",2,"",0,""); 
		        if (zclhq == 0&&gsk == 0&&hk == 0&&qtsr == 0&&gwzc == 0&&payeverymon == 0&&balance == 0){
		            alert("请填写考核期内的收入、支出及余额!");
		            return;
		        }
		    }
		}
		
		//经办人判断不填写意见不能提交 不同意不能提交 
		var checkType = 0;
		var isCheckSeal = document.getElementById("ischeckseal");
		if(isCheckSeal) checkType = isCheckSeal.value;
		if(checkType!=1){
			doSubmitFlow(resid,operateType,flowdefid,tempid);
			return;
		}
		
		//检查签章方案情况
		var signatureNum = aipObj.GetNoteNum(1);//手写批注的个数
		var sealNum = aipObj.GetNoteNum(251);//电子签章的个数
		var object = noShow_signAndNotation(flowdefid,tempid);
		var signnotation = object.split('^');
	    var signnum = parseInt(signnotation[0]);
	    var notation = parseInt(signnotation[1]);
	    var returnStates=signAndSealCheck(sealNum,signatureNum,signnum,notation,operateType);
	    if(returnStates == 1){
	    	return;
	    }
	    doSubmitFlow(resid,operateType,flowdefid,tempid);
		
		var flowdraftid = '';
		var flowdraftidObj = document.getElementById("flowdraftid");
		if(flowdraftidObj) flowdraftid = flowdraftidObj.value;
		//减刑假释审核表更新签章进程 
		if(flowdefid=='other_zfjyjxjssp' || flowdefid == 'other_jybwjycbsp' || flowdefid == 'other_sjjxjssp'){
			updateProcessOfSignature(flowdraftid,operateType,flowdefid,tempid);
		}
	}
	
	//更新签章进程 
	function updateProcessOfSignature(flowdraftid,operateType,flowdefid,tempid){
		var aipObj = document.getElementById("HWPostil1");
		var signatureNum = aipObj.GetNoteNum(1);
		var sealNum = aipObj.GetNoteNum(251);
		var signature = sealNum;
		$.ajax({
	         url: "<%=path%>/penaltyPerform/ajaxsaveSignatureForAip.json", 
	         data: {flowdraftid:flowdraftid,signature:signature,operateType:operateType,flowdefid:flowdefid,tempid:tempid},
	         type: "POST",
	         dataType:"json",
	         async:false,
	         success: function (text){
	         	  
	         }
	    });
	}
	/**
	 * 方法描述 ：减刑假释和保外就医 的提交按钮 保存、退回、拒绝按钮 ，竟然调用不是同一个方法
	 * 。故：签章控制的判断方法需要抽离出来，进行 控制
	 * 暂时五个参数：签章个数，批注个数，操作状态(如：提交、保存、退回等)，需签章个数，需批注个数。前两个参数是当前页面签的，后两个参数
	 * 是后台规定的。 
	 */
	function signAndSealCheck(sealNum,signatureNum,signnum,notation,operateType){
		//alert("签章个数2："+sealNum+",批注个数："+signatureNum+",需签章个数："+signnum+",需批注个数："+notation+",操作状态："+operateType);
		//批注:signatureNum
		//签章:sealNum
		//签章(配置):signnum
		//批注(配置):notation
		var states = 0;
		if(operateType=='yes'){
			if(signatureNum < notation){
			//	alert("批注次数不够！");states=1;
			}else if(signatureNum > notation){
			//	alert("不能多次批注！");states=1;
			}
			if(sealNum < signnum){
				alert("签章次数不够！");states=1;
			}else if(sealNum > signnum){
				alert("不能多次签章！");states=1;
			}
		}else if(operateType=='back'){
			if(signatureNum > signnum){
				alert("签章后无法退回!");states=1;
		    }
			if(sealNum > notation){
			//	alert("批注后无法退回!");states=1;
		    }
		}else if(operateType=='no'){
			if(signatureNum > signnum){
				alert("签章后无法拒绝!");states=1;
		    }
			if(sealNum > notation){
			//	alert("批注后无法拒绝!");states=1;
		    }
		}
		return states;
	}
	//获取 需要 受签章控制的表单
	function signCheckBiaoDan(tempid){
		var flag = false;
		var singchenkbiaodan = $("#signcheckbiaodan").val();
		if(singchenkbiaodan != undefined){
			var signcheckbiaodans = singchenkbiaodan.split(',');
			var i=0;
			for(i = 0;i<signcheckbiaodans.length;i++){
		         if(signcheckbiaodans[i] == tempid){
		              flag = true;
		              break;
		         }
		    }
		}
	    return flag;
	}
</SCRIPT> 