	function initConditionBeforeOperate(type){
		//操作之前，先验证条件
  		var validationFlag = validationCondition();
  		if(false==validationFlag){
  			return validationFlag;
  		}
  		//初始化条件
  		initializeCondition();
  		//获取可操作的案件号，并返回不可操作的案件号，案件号从","隔开，发生错误则返回
  		var operateFlag = getSealableCases(type);
  		if(false == operateFlag){
  			return operateFlag;
  		}
  		var fitCaseNos = mini.get('fitCaseNos').getValue();
  		if(!fitCaseNos){
  			dealSealEnd();
  			alert("没有符合的材料可操作！");
  			return false;
  		}
  		
  		var aipObj = document.getElementById("HWPostil1");
  		
  		//判断该电子签章是否属于此用户
  		var isUserKey = isUserSignatureKey(aipObj);
		if(false == isUserKey){
			return false;
		}
		//
  		casenoArr = getCasenoArr(fitCaseNos);
  		return true;
	}
	
	
	//签章(撤销签章)/打印之前，验证条件
  	function validationCondition(){
	  	var casenos = mini.get('casenos').getValue();
		if (!casenos){
			alert("请输入案件号范围(示例：1-9 或者1,6,9)！");
			return false;
		}
		//
		var signid=$("#signid").val();
		if(!signid){
			alert("请选择方案");
			return false;
		}
		//
		var obj=document.getElementById("HWPostil1");
		var aipLoginValue = aipLogin(obj);
		
		if(0 != aipLoginValue){
			alert("电子签章未连接！");
			return false;
		}
		
  	}
  	
  	//初始化条件
  	 var index=-1;//案件号数组的索引
  	 var casenoArr = [];//当前案件号数组
  	function initializeCondition(){
  		index = -1;
  		casenoArr = [];
	  	mini.get('notExistClobCaseNos').setValue('');
	  	mini.get('notFitOrgCaseNos').setValue('');
	  	mini.get('notThisLevelCaseNos').setValue('');
	  	mini.get('fitCaseNos').setValue('');
	  	showContent.style.display = "none";
	    mini.get("sealResult").setValue("操作结果为：<br/>");
  	}
  	
  	
  	//获取可操作的案件，并返回不可操作的案件号，案件号从","隔开
  	/*
  	*	map {notExistClobCaseNos:不存在大字段的案件号
	* 		    notFitOrgCaseNos:不符合部门过滤条件的案件号
	* 		    notThisLevelCaseNos:不在本级审批的案件号
	* 		    fitCaseNos:符合条件的案件号
	*         }
  	*/
  	function getSealableCases(type){
  		var signid = $("#signid").val();
  		//var casetype = mini.get('casetype').getValue();
  		var casetype = $("#casetype").val();
  		
  		var year = $("#year").val();
  		var casenos = mini.get('casenos').getValue();
  		
  		var operateFlag = false; //用于本JS函数被调用时，是否发生异常的标志
  		
  		if(type && type=='print'){//打印
  			var fitCaseNos = formatCasenos(casenos);
  			mini.get('fitCaseNos').setValue(fitCaseNos);
  			operateFlag = true;
  		}else{//签章
  			var url = path + "/sign/getSealableCasesInfo.json?1=1";
  			$.ajax({
	            url: url,
	            data: {signid:signid, casetype:casetype, year:year, casenos:casenos },
	            type: "post",
	            async:false,
	            success: function (text) {
	            	var objs = mini.decode(text);
	            	mini.get('notExistClobCaseNos').setValue(objs.notExistClobCaseNos);
	            	mini.get('notFitOrgCaseNos').setValue(objs.notFitOrgCaseNos);
	            	mini.get('notThisLevelCaseNos').setValue(objs.notThisLevelCaseNos);
	            	mini.get('fitCaseNos').setValue(objs.fitCaseNos);
	            	operateFlag = true;
	            },
	            error: function (jqXHR, textStatus, errorThrown){
	           	 	alert("操作失败!");
	            }
	        });
  		}
  		
        return operateFlag;
  	}
  	
  //获取当前要处理的案件号
  function getNextCaseno(){
  		if(index == -99){
  			return null;
  		}
        index ++;
        if(index >= casenoArr.length){
        	index = -99;
        	return null;
        }else{
        	return casenoArr[index];
        }
  }
  
  
  
   function dealSealEnd(){
  		var aipObj = document.getElementById("HWPostil1");
  	 	//aipObj.CloseDoc(0);
  	 	var showContent = document.getElementById('showContent');
  	 	var sealResult = mini.get("sealResult").getValue();
  	 	//
  	 	var orgShortname = $("#orgShortname").val();
  	 	var casetype=$("#casetype").find("option:selected").text();
  		var year = $("#year").val();
  		//
  	 	var prefix = "<span style='color:red'>";
  	 	var subfix = "</span> ";
  	 	var curTime = mini.formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss');
  	 	//不存在的案件号显示
  	 	var notExistClobCaseNos = mini.get('notExistClobCaseNos').getValue();
  	 	if(notExistClobCaseNos){
  	 		var notExistClobCaseNoArr = getCasenoArr(notExistClobCaseNos);
  	 		for(var i=0, l=notExistClobCaseNoArr.length; i<l ; i++){
  	 			sealResult += "【(" + year + ")" + orgShortname + casetype + "第" + prefix + notExistClobCaseNoArr[i] + subfix 
  	 					+ "号】案件的司法文书不存在"+"\t"+curTime+"<br/>";
  	 		}
  	 		sealResult += "<br/>";
  	 	}
  	 	//非本部门审批的案件号显示
  	 	var notFitOrgCaseNos = mini.get('notFitOrgCaseNos').getValue();
  	 	if(notFitOrgCaseNos){
  	 		var notFitOrgCaseNoArr = getCasenoArr(notFitOrgCaseNos);
  	 		for(var i=0, l=notFitOrgCaseNoArr.length; i<l ; i++){
  	 			sealResult += "【(" + year + ")" + orgShortname + casetype + "第" + prefix + notFitOrgCaseNoArr[i] + subfix 
  	 					+ "号】案件的司法文书不是本部门审批"+"\t"+curTime+"<br/>";
  	 		}
  	 		sealResult += "<br/>";
  	 	}
  	 	//未到本级审批的案件号显示
  	 	var notThisLevelCaseNos = mini.get('notThisLevelCaseNos').getValue();
  	 	if(notThisLevelCaseNos){
  	 		var notThisLevelCaseNoArr = getCasenoArr(notThisLevelCaseNos);
  	 		for(var i=0, l=notThisLevelCaseNoArr.length; i<l ; i++){
  	 			sealResult += "【(" + year + ")" + orgShortname + casetype + "第" + prefix + notThisLevelCaseNoArr[i] + subfix 
  	 					+ "号】案件的司法文书未到本级审批"+"\t"+curTime+"<br/>";
  	 		}
  	 	}
  	 	//
        showContent.innerHTML= sealResult;
        showContent.style.display = "";
  }
  
  
  
  //显示签章状态信息
	function dealMessage(caseno, message){
		var showContent=document.getElementById('showContent');
  	 	var sealResult = mini.get("sealResult").getValue();
  	 	//
  	 	var orgShortname = $("#orgShortname").val();
  	 	var casetype=$("#casetype").find("option:selected").text();
  		var year = $("#year").val();
  		//
  	 	var prefix = "<span style='color:red'>";
  	 	var subfix = "</span> ";
  	 	var curTime = mini.formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss');
  	 	
  	 	var status =  "【(" + year + ")" + orgShortname + casetype + "第" + prefix + caseno + subfix 
  	 					+ "号】案件的" + message +"\t"+curTime+"<br/>";
  	 	sealResult += status;
  	 	showContent.innerHTML= status;
  	 	mini.get("sealResult").setValue(sealResult);
  }

	function getCasenoArr(casenos){
		var casenoArr = casenos.split(",");
		return casenoArr;
	}
	
  	//格式化案件号
  	function formatCasenos(casenos){
		if(casenos){
			var casenosList = new Array();
			casenos= casenos.replace(/\，/g,",");
			var strArr = casenos.split(",");
			for(var i=0;i<strArr.length;i++){
				var str = trim(strArr[i]);
				if(str.indexOf("-")>-1){
					var start = trim(str.split("-")[0]);
					var end = trim(str.split("-")[1]);
					if(start && end){
						for(var j=0;j <= parseInt(end)-parseInt(start);j++){
							casenosList.push(parseInt(start)+j);
						}
					}
				}else{
					casenosList.push(str);
				}
			}
			return casenosList.join(",");
		}
	}
	
	/**
	* 删除左右两端的空格
	*/
	function trim(str){
		 return str.replace(/(^\s*)|(\s*$)/g, '');
	}
	
	
	//加载表单
	function loadFormData(aipFileStr, formData, selectData){
		var aipObj=document.getElementById("HWPostil1");
		aipObj.LoadFileBase64(aipFileStr);//加载表单
		//向表单赋值 
		if(formData){
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
	
	
	function dayinwenshu(reportdata){
		//var aipObj=document.getElementById("HWPostil1");
		//var reportdata = eval(text);
		if(reportdata){
			//reportdata = mini.decode(reportdata);
			document.all("MyViewer").ShowProgress = false;
			document.all.HWPostil1.BeforeConvert("");
			document.all("RMVIEWER_DATA").value=reportdata;
			document.all("MyViewer").GetReportData(reportdata);
			document.all("MyViewer").ShowPrintDialog = false;
			document.all("MyViewer").PrintReport();
			document.all.HWPostil1.WaitConverting(4000);
			document.all.HWPostil1.AfterConvert();
		}
	}