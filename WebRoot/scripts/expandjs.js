

//监狱减刑假释批量申报
function commuteBatchApplyForJail() {
	var rows = grid.getSelecteds();
	if (rows.length > 0) {
		var ids = [];
		for ( var i = 0, l = rows.length; i < l; i++) {
			var r = rows[i];
			ids.push(r.crimid);
		}
		var id = ids.join(',');
		$.ajax( {
					url : "commutationParole/piliangshenbao.action?1=1&crimids="+ id,
					type : "post",
					success : function(text) {
						grid.reload();
					},
					error : function() {
						alert("操作失败！");
					}
				});
	} else {
		alert("请选中一条记录");
	}
}

//监狱减刑假释单个申报
function commuteApplyOneForJail() {
	var row = grid.getSelected();
	var crimid = row.crimid;
	$.ajax( {
		url : "commutationParole/jianxingshenbao.action?1=1&crimid="+ crimid,
		success : function(text) {
			grid.reload();
		},
		error : function() {
			alert("操作失败");
			return;
		}
	});
}


//会议记录
/**
* menuid : 按钮菜单ID,
*/
function meetingRecord(menuid){
	//
	var rows = grid.getSelecteds();
	var oldFlowdraftid = '';
	var judge = 0;
	$.ajax({
        type:'post',
        url: path + '/judgeExistMeetRecordByUser.json?1=1&menuid='+menuid,
        async:false,
        success:function(text){
        	var data = mini.decode(text);
            judge = parseInt(data.num);
            if(data.oldFlowdraftid){
            	oldFlowdraftid = data.oldFlowdraftid;
            }
        }
    });
    
   if(rows.length > 0){
   		if(judge > 0){
   			if(confirm("已经存在一份会议记录\n确定重新生成吗?")){
             	newMeetRecord(menuid,rows);
        	}else{//打开旧会访记录
        		lastMeetRecord(menuid,oldFlowdraftid);
        	}
   		}else{
			newMeetRecord(menuid,rows);
   		}
   }else{
    	if(judge > 0){
    		lastMeetRecord(menuid,oldFlowdraftid);
    	}else{
    		alert(confirmMessages);
			return ;
    	}
    }
}

function newMeetRecord(menuid,rows){
	//
	var flowdraftidArr = [];
	var flowdraftids = '';
	var applyidArr = [];
	var applyids = '';
    for (var i = 0, l = rows.length; i < l; i++){
        var row = rows[i];
        flowdraftidArr.push('\''+row.flowdraftid+'\'');
        applyidArr.push(row.applyid);
    }
    flowdraftids = flowdraftidArr.join(',');
    applyids = applyidArr.join(',');
    //
    var url = path + '/toSysTemplate.page?1=1&menuid='+menuid+'&optype=add&noOnLoad=1';
   	var win=mini.open({
          url: url,
          showMaxButton: true,
          allowResize: true, 
          onload: function (){
              var iframe = this.getIFrameEl();
              var data = {flowdraftids:flowdraftids, temp1:applyids, optype:"add"};
              iframe.contentWindow.SetData(data);
          }
	});
    win.max();
}

function lastMeetRecord(menuid,flowdraftid){
	var url = path + '/toSysTemplate.page?1=1&menuid='+menuid+'&noOnLoad=1';
   	var win=mini.open({
          url: url,
          showMaxButton: true,
          allowResize: true, 
          onload: function (){
              var iframe = this.getIFrameEl();
              var data = {flowdraftid : flowdraftid};
              iframe.contentWindow.SetData(data);
          }
	});
    win.max();
}

//////////////////罪犯考核评估分//////////////////////////
function compute(){
		jisuan();
		operateMasterData('save');
	}
   function jisuan(){
	   var aipObj=document.getElementById("HWPostil1");
		
		var rzhzscore=0;//认罪悔罪_分数
		var fcgjscore=0;//服从管教_分数
		var xwgfscore=0;//行为规范_分数
		for(var i=1;i<6;i++){
			var rzfz1Pot=aipObj.GetValueEx("RZHZ"+i,3,"",0,"");//3返回的是第几个，2返回的是值
			var rzfz1Value = aipObj.GetValueEx("RZHZ"+i,2,"",rzfz1Pot,"");
			if(rzfz1Value!=''){
				rzhzscore=parseInt(rzhzscore)+parseInt(rzfz1Value.substring(0,rzfz1Value.length-1));
			}
			var fcgj1Pot=aipObj.GetValueEx("FCGJ"+i,3,"",0,"");//3返回的是第几个，2返回的是值
			var fcgj1Value = aipObj.GetValueEx("FCGJ"+i,2,"",fcgj1Pot,"");
			if(fcgj1Value!=''){
				fcgjscore=parseInt(fcgjscore)+parseInt(fcgj1Value.substring(0,fcgj1Value.length-1));
			}
			var xwgf1Pot=aipObj.GetValueEx("XWGF"+i,3,"",0,"");//3返回的是第几个，2返回的是值
			var xwgf1Value = aipObj.GetValueEx("XWGF"+i,2,"",xwgf1Pot,"");
			if(xwgf1Value!=''){
				xwgfscore=parseInt(xwgfscore)+parseInt(xwgf1Value.substring(0,xwgf1Value.length-1));
			}
		}
		
		var jygzscore=0;//教育改造_分数
		var scldcore=0;//生产劳动_分数
		for(var i=1;i<4;i++){
			var jygz1Pot=aipObj.GetValueEx("JYGZ"+i,3,"",0,"");//3返回的是第几个，2返回的是值
			var jygz1Value = aipObj.GetValueEx("JYGZ"+i,2,"",jygz1Pot,"");
			if(jygz1Value!=''){
				jygzscore=parseInt(jygzscore)+parseInt(jygz1Value.substring(0,jygz1Value.length-1));
			}
			var scld1Pot=aipObj.GetValueEx("SCLD"+i,3,"",0,"");//3返回的是第几个，2返回的是值
			var scld1Value = aipObj.GetValueEx("SCLD"+i,2,"",scld1Pot,"");
			if(scld1Value!=''){
				scldcore=parseInt(scldcore)+parseInt(scld1Value.substring(0,scld1Value.length-1));
			}
			}
			
			var xljkcore=0;//心里健康_分数
			for(var i=1;i<7;i++){
			var xljk1Pot=aipObj.GetValueEx("XLJK"+i,3,"",0,"");//3返回的是第几个，2返回的是值
			var xljk1Value = aipObj.GetValueEx("XLJK"+i,2,"",xljk1Pot,"");
			if(xljk1Value!=''){
				xljkcore=parseInt(xljkcore)+parseInt(xljk1Value.substring(0,xljk1Value.length-1));
			}
			}
			
			//放入表单值
			aipObj.SetValue('fajinjiaonaqingkuang',"");
			aipObj.SetValue('fajinjiaonaqingkuang',rzhzscore);//认罪悔罪
			aipObj.SetValue('paymentzk',"");
			aipObj.SetValue('paymentzk',setlevel('rzhz',rzhzscore));
			aipObj.SetValue('paymentpc',"");
			aipObj.SetValue('paymentpc',fcgjscore);//服从管教
			aipObj.SetValue('paymentcc',"");
			aipObj.SetValue('paymentcc',setlevel('fcgj',fcgjscore));
			aipObj.SetValue('gaizaobiaoxian',"");
			aipObj.SetValue('gaizaobiaoxian',xwgfscore);//行为规范
			aipObj.SetValue('casenum',"");
			aipObj.SetValue('casenum',setlevel('xwgf',xwgfscore));
			aipObj.SetValue('casetype',"");
			aipObj.SetValue('casetype',jygzscore);//教育改造
			aipObj.SetValue('mapkey8',"");
			aipObj.SetValue('mapkey8',setlevel('jygz',jygzscore));
			aipObj.SetValue('worktype',"");
			aipObj.SetValue('worktype',scldcore);//生产劳动
			aipObj.SetValue('liandate',"");
			aipObj.SetValue('liandate',setlevel('scld',scldcore));
			aipObj.SetValue('acceptdate',"");
			aipObj.SetValue('acceptdate',xljkcore);//心里健康
			aipObj.SetValue('fyfenandate',"");
			aipObj.SetValue('fyfenandate',setlevel('xljk',xljkcore));
			//计算平均分,totalaverage放在表单隐藏域中
			var totalcount=rzhzscore+fcgjscore+xwgfscore+jygzscore+scldcore+xljkcore;
			aipObj.SetValue('xuanpandate',"");
			aipObj.SetValue('xuanpandate',(totalcount/6).toFixed(1));//平均分
   }
   
   function setlevel(type,scoer){
	//认罪悔罪------
	if(type=='rzhz')
	{
		if(scoer>=15){return '优'}
		else if(scoer>=12 && scoer<=14 ){return '良'}
		else if(scoer>=9 && scoer<=11 ){return '中'}
		else if(scoer>0&&scoer<=8) {return '差'}
		else{return ''}
	}
	//服从管教 //行为规范 //教育改造 //生产劳动
	if(type=='fcgj'||type=='xwgf'||type=='jygz'||type=='scld')
	{
		if(scoer>=16){return '优'}
		else if(scoer>=13 && scoer<=15 ){return '良'}
		else if(scoer>=10 && scoer<=12 ){return '中'}
		else if(scoer>0&&scoer<=9) {return '差'}
		else{return ''}
	}
	//心理健康
	if(type=='xljk')
	{
		if(scoer>=11){return '优'}
		else if(scoer>=9 && scoer<=10 ){return '良'}
		else if(scoer>=7 && scoer<=8 ){return '中'}
		else if(scoer>0&&scoer<=6) {return '差'}
		else{return ''}
	}
}


//功能测试按钮
function testFunction() {
	var url = path + '/testFunction.json';
	$.ajax( {
		url : url,
		success : function(text) {
			grid.reload();
		},
		error : function() {
			alert("操作失败");
			return;
		}
	});
}


function caseDataExchange(ddcid){
	var rows = grid.getSelecteds();
	if(true){
		if(confirm("确定操作选中的记录吗？")){
			var crimidArr = [];
            for (var i = 0, l = rows.length; i < l; i++){
               var row = rows[i];
               crimidArr.push(row.crimid);
            }
			var crimids = crimidArr.join(',');
			var url = path + '/caseDataExchange.json?1=1';
    		//
    		$.ajax({
                url: url,
                data: {crimids:crimids, ddcid:ddcid},
                type: "post",
                success: function (text){
                	text = mini.decode(text);
	        	    if(text=='1'){
        	    		alert("操作成功！");
        	    	}else{
        	    		alert("操作失败！");
        	    	}
	        	    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败！");
                }
            });
			
		}
	}else{
		alert("请选择记录！");
		return false;
	}
}
function addTable(menuid,url) {
	var ids = '';
	var applyidObj = mini.get("applyid");
	var applyid = '';
	if(applyidObj){
		applyid = applyidObj.getValue();
	}
	ids = applyid+'@'+menuid;
	//用于返回到主页面的url
	var url = url+"&ids="+ids+"&optype=add"+"&indexFlag="+0;
 		var win = mini.open({
 	        url: url,
 	        showMaxButton: true,
 	        allowResize: false,
 	        title: "新增",width: 400, height: 350, 
 	        onload: function (){
 	            var iframe = this.getIFrameEl();
 	            var data = { action: "new"};
 	            iframe.contentWindow.SetData(data);
 	        },
 	        ondestroy: function (action){
 	        	grid.reload();
 	        }
 	   });	
 	   //win.max();
	 	   
}


function reloadLiGongTemplate(){
	var ligongtype = mini.get('ligongtype').getValue();
	if(ligongtype){
		reloadTemplate(ligongtype,"add");
	}
}

function reloadBWMatirialTemplate(){
	var illmaterial = mini.get('illmaterial').getValue();
	if(illmaterial){
		reloadTemplate(illmaterial,"add");
	}
}

function reloadImportantCriminalTemplate(){
	var criminalcategory = mini.get('criminalcategory').getValue();
	if(criminalcategory){
		reloadTemplate(criminalcategory,"add");
	}
}

function reloadTemplate(tempid,optype){
	
	var flowdraftid = mini.get('flowdraftid').getValue();
	var applyid = mini.get('applyid').getValue();
	var solutionid = mini.get('solutionid').getValue();
	var data = {};
	data.flowdraftid=flowdraftid;
	data.tempid=tempid;
	data.optype=optype;
	data.applyid=applyid;
	data.solutionid=solutionid;
	
	loadFormAipFileStr(data);
    
}

function loadFormAipFileStr(data){
	var url = path + '/reloadTemplate.json?1=1';
	//
	$.ajax({
        url: url,
        data: data,
        type: "post",
        success: function (text){
        	var result = mini.decode(text);
        	var aipFileStr = result.docconent[0];
        	var formdata = result.formDatajson;
        	var selectData = result.selectDatajson;
	   		//加载表单
	   		loadFormData(aipFileStr, formdata, selectData);
	   		isEditAttribute();
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	alert("加载失败！");
        }
    });
}





//---------------------------------------------------------------

/**
 * menuid ： 资源编号			type ：add , edit
 * @param {} menuid
 * @param {} type
 */
function OpFlowPersonConfig(menuid,type){
	var url="toOperateFlowPersonConfigPage.page?1=1";
	var data = {type:type};
	if(type=='add'){
		title='新增';
	}else if(type=='edit'){
		title='修改';
		var row = grid.getSelected();
		url += "&departid="+row.departid+"&flowdefid="+row.flowdefid;
		data.departid = row.departid;
		data.flowdefid = row.flowdefid;
	}
	mini.open({
		url:url,
		title:title,
		width:1000,height:550,
		showMaxButton:true,
		allowResize:true,
		onload:function (){
    		var iframe = this.getIFrameEl();
            var data = data;
            //iframe.contentWindow.SetData(data);
		},
		ondestroy:function(){
			grid.reload();
		}
	});
}

function removeFlowPersonConfig(menuid){
	var row = grid.getSelected();
	var flowdefid = row.flowdefid;
	if(flowdefid){
		var data = {};
		data.flowdefid = flowdefid;
		data.menuid = menuid;
		removeData(data);
	}
	
}

//危安犯数据保存
function SaveWeiAnData(docconent, noteinfo, masterslave, operateType, bizName){
   		var orgid = '';
		var resid = '';
		
		//var applyid = mini.get("applyid").getValue();
		//var applyname = mini.get("applyname").getValue();
		var flag = false;
   		
   		var tempid = mini.get("tempid").getValue();
		var flowdefid = mini.get("flowdefid").getValue();
		flowdefid = flowdefid || 'other_sysflow';//如果是非流程的表单保存，默认用other_sysflow(系统流程);
		var solutionid = mini.get("solutionid").getValue();
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
		if(docconent){
			data["docconent"] = docconent; //获取大字段
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
		if(noteinfo){
			data["noteinfo"] = noteinfo+"";
		}
		
		var flowid = '';
		var commenttext = '';
		var savetype = '';
		var examineid = '';
		var shenptype = '';
		//临时变量
		var temp1 = mini.get("temp1").getValue();
		data["temp1"] = temp1;
		$.ajax({
	         url: "/pmsys/flow/flowapprove.action?1=1", //保存流程表
	         data: {applyid:applyid,applyname:applyname,operateType:'save',orgid:orgid,tempid:'WAFGL_WAFDCDJB',
	         		flowdefid:'other_wafgl',resid:resid,docconent:docconent},
	         type: "POST",
	         dataType:"json",
	         async:false,
	         success: function (text){
	         		data["flowdraftid"] = text;
			   		var url="/pmsys/dangerousCriminal/ajaxSaveWeiAnDoc.action?1=1"; //保存业务表
			        $.ajax({
			            url:encodeURI(encodeURI(url)),
			            data: data,
			            type: "post",
			            async:false,
			            success: function (text) {
			            	//text = mini.decode(text);
			            	alert("操作成功！");
			            	/**if(text["rows"]==1){
			            		if(window.parent.mini.get("flowdraftid")){
			            			window.parent.mini.get("flowdraftid").setValue(text["flowdraftid"]);
			            		}
			            		if(mini.get("flowdraftid")){
			            			mini.get("flowdraftid").setValue(text["flowdraftid"]);
			            		}
			            		flag = true;
			            		alert("操作成功！");
			            	}else{
			            		alert(text["status"]);
			            	}*/
			            },
			            error: function (jqXHR, textStatus, errorThrown) {
			            	alert("操作失败!");
			            }
			        });  
	         },
             error: function (jqXHR, textStatus, errorThrown) {
            	   alert("操作失败!");
             }
		});
        return flag;
   }
//查看危安犯
function chakanWeiAn(menuid){
	var row = grid.getSelected();//获取选中记录
	var ids = row.surveyid+'@'+row.crimid+'@'+menuid;
	alert(ids);
	//用于返回到主页面的url
    var url = "dangerousCriminal/toWeiAnFatherTabPage.action?1=1&ids="+ids+"&indexFlag="+0;
	var win= mini.open({
        url: url,
        showMaxButton: true,
     	allowResize: false, 
        title: "", 
        onload: function () {
            var iframe = this.getIFrameEl();
            var data = { action:"show",surveyid:row.surveyid,crimid:row.crimid};
            iframe.contentWindow.SetData(data);
        },
        ondestroy: function (text){
            grid.reload();
        }
    });
    win.max();
}

 //修改危安犯
function xiugaiWeiAn(menuid){
	edit(menuid);
}


//根据action跳转到考核成绩补录页面
function chooseOneKhcjbl(menuid) {
	chooseAllKhcjbl(menuid);
}

//根据action跳转到考核成绩补录页面
function chooseAllKhcjbl(menuid) {
	var rows = grid.getSelecteds();
	if(rows && rows.length >0){
		var ids = [];
        for (var i = 0; i < rows.length; i++) {
            var r = rows[i];
            ids.push(r.crimid);
        }
        var id = ids.join(',');
        var fatherMenuid = mini.get("menuid").getValue();
		var url = path + "/jifen/publicBuluPage.page?1=1&id="+id+"&indexFlag=0&menuid="+menuid+"&fatherMenuid="+fatherMenuid;
		self.location.href=url;
	}else{
		alert("请选择一条记录");
		return false;
	}
}

function viewReportOperateForSelect(menuid,name,nodeid,rtnodeid,grade,flowdefid,condition) {
	var rows = grid.getSelecteds();
	if(rows.length > 0){
		var flowdraftidArr = [];
		var flowdraftids = '';
	    for (var i = 0, l = rows.length; i < l; i++){
	        var row = rows[i];
	        flowdraftidArr.push('\''+row.flowdraftid+'\'');
	    }
	    flowdraftids = flowdraftidArr.join(',');
		var myurl = "baseReportOperate.page?1=1&optype=add&doctype=" + name+"&menuid="+menuid+"&grade="+grade+"&flowdefid="+flowdefid+"&condition";
		if (nodeid != null && nodeid.length > 0 && nodeid != undefined) {
			var arr = nodeid.split(",");
			var nodeids;
			for(var i=0;i<arr.length;i++){
				if (i == 0) {
					nodeids="'"+arr[i]+"'";
				}else {
					nodeids+=",'"+arr[i]+"'";
				}
			}
			myurl += "&nodeids="+nodeids;
		}
		
		if(rtnodeid){
			myurl += "&rtnodeid="+rtnodeid;
		}
		if(flowdraftids){
			myurl += "&flowdraftids="+flowdraftids;
		}
		var win = mini.open({
			url : myurl,
			showMaxButton : true,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			//data: {flowdraftids:flowdraftids},
			allowResize : true,
			title : "预览"
		});
		win.max();
	}else{
		alert("请至少选择一条记录！");
	}
}


function viewReportOperateByCondition(menuid,name,nodeid,rtnodeid,grade,flowdefid,condition) {
	var rows = grid.getData();
	if(!rows || !rows.length){
		alert("没有符合条件的案件");
		return;
	}else{
		var flowdraftidArr = [];
		var flowdraftids = '';
	    for (var i = 0, l = rows.length; i < l; i++){
	        var row = rows[i];
	        flowdraftidArr.push('\''+row.flowdraftid+'\'');
	    }
	    flowdraftids = flowdraftidArr.join(',');
		var myurl = "baseReportOperate.page?1=1&optype=add&doctype=" + name+"&menuid="+menuid+"&grade="+grade+"&flowdefid="+flowdefid+"&condition";
		if (nodeid != null && nodeid.length > 0 && nodeid != undefined) {
			var arr = nodeid.split(",");
			var nodeids;
			for(var i=0;i<arr.length;i++){
				if (i == 0) {
					nodeids="'"+arr[i]+"'";
				}else {
					nodeids+=",'"+arr[i]+"'";
				}
			}
			myurl += "&nodeids="+nodeids;
		}
		
		if(rtnodeid){
			myurl += "&rtnodeid="+rtnodeid;
		}
		if(flowdraftids){
			myurl += "&flowdraftids="+flowdraftids;
		}
		var win = mini.open({
			url : myurl,
			showMaxButton : true,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			//data: {flowdraftids:flowdraftids},
			allowResize : true,
			title : "预览"
		});
		win.max();
	
	}
}

//花名册 baseReportOperate.page?1=1&optype=add&grade=0&doctype=资格筛查花名册&solutionid=？
//公示 baseReportOperate.page?1=1&optype=add&grade=0&doctype=资格筛查公示&solutionid
function viewReportOperate(menuid,name,nodeid,rtnodeid,grade,flowdefid,condition) {
	var myurl = "baseReportOperate.page?1=1&optype=add&doctype=" + name+"&menuid="+menuid+"&grade="+grade+"&flowdefid="+flowdefid+"&condition";
	if (nodeid != null && nodeid.length > 0 && nodeid != undefined) {
		var arr = nodeid.split(",");
		var nodeids;
		for(var i=0;i<arr.length;i++){
			if (i == 0) {
				nodeids="'"+arr[i]+"'";
			}else {
				nodeids+=",'"+arr[i]+"'";
			}
		}
		myurl += "&nodeids="+nodeids;
	}
	
	if(rtnodeid){
		myurl += "&rtnodeid="+rtnodeid;
	}
	var win = mini.open({
		url : myurl,
		showMaxButton : true,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		// data: { menuid:row.resid,anjianhao:anjianhao,
		// caseyear:caseyear,tempid:row.planid,flag:"flag"},
		allowResize : true,
		title : "预览"
	});
	win.max();
}


function viewDocOperate(tempid,solutionid,menuid) {
	var myurl = "baseDocOperate.page?1=1&optype=add&tempid="+tempid+"&solutionid="+solutionid+"&menuid="+menuid;
	var win = mini.open({
		url : myurl,
		showMaxButton : true,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		// data: { menuid:row.resid,anjianhao:anjianhao,
		// caseyear:caseyear,tempid:row.planid,flag:"flag"},
		allowResize : true,
		title : "预览"
	});
	win.max();
}

function validateYearMonth(yearMonth){
	var flag = false;
	if(yearMonth && yearMonth.split("-").length ==2){
		var year = yearMonth.split("-")[0];
	    var month = yearMonth.split("-")[1];
	    if(year.length == 4 && month.length <=2 &&month.length >=1){
	    	var tMonth = month.substr(0,1);
	    	if(tMonth == '0'){
	    		month = month.substr(1,1);
	    	}
	    	var re = /^[0-9]+.?[0-9]*$/;
	    	if(re.test(year) && re.test(month)){
	    		if(parseInt(month) >12){
	    			alert("输入的月份有错！");
	    		}else{
	    			flag = true;
	    		}
	    	}else{
	    		alert("输入的年月格式不对！");
	    	}
	    }else{
	    	alert("输入的年月格式不对！");
	    }
	    
	}else{
		alert("请输入格式为yyyy-MM的数值！");
	}
	
	return flag;
}


/*
 * 
 * 提交考核考勤月报表页面文本框
 */
function kaoheKaoqinTongji(menuid){
	//var kaohekaoqinriqi = mini.get("kaohekaoqinriqi").getFormValue();
	var kaohekaoqinriqi = mini.get("kaohekaoqinriqi").getValue();
	var flag = validateYearMonth(kaohekaoqinriqi);
	if(flag ==false){
		return;
	}
	var year = kaohekaoqinriqi.split("-")[0];
	var month = kaohekaoqinriqi.split("-")[1];
	
	buttonUnlock();
	$('#flowinfo').html(null);
	
	var title = "";
	var mycars=new Array("O","一","二","三","四","五","六","七","八","九","十","十一","十二");
   
    for(var i=0;i<4;i++){
    	var j = year.substring(i, i+1); 
    	title = title + mycars[j]; 
    }
    title = title+"年";  
    if(parseInt(month)>9){
    	title = title + mycars[parseInt(month)]+"月"; 
    }else{
    	month = month.replace("0","");    
    	title = title+mycars[month]+"月"; 
    }
    //mini.get("kaohekaoqinriqi").setText(kaohekaoqinriqi.substr(0, 7));
	mini.get("kaohekaoqinname").setValue(title+"月考核考勤月报表");
    var data = {};
//    data["tempid"] =  "ykhkqspreport";
//	data["flowdefid"] = "other_ykhkqsp";
	data["tempid"] =  mini.get("tempid").getValue();
	data["flowdefid"] = mini.get("flowdefid").getValue();
	data["year"] = year;
	data["month"] = month;
	data["kaohekaoqinriqi"] = kaohekaoqinriqi.substr(0, 7);
	data["kaohekaoqinname"] = mini.get("kaohekaoqinname").getValue();
	
	var flowdata = isFlowApprovedOfMonthKaoHe(data);
	
	if(flowdata){
		//如果已经走流程，则关掉"提交"按钮，并显示相关提示
		var nodeid = flowdata.nodeid;
		if(nodeid){
			if(nodeid == "1"){
				$('#flowinfo').html("审批通过！");
				buttonLock();
			}else if(nodeid == "-1"){
				$('#flowinfo').html("审批不通过！");
				buttonLock();
			}else if(nodeid != "0"){
				$('#flowinfo').html("审批中！");
				buttonLock();
			}
			
		}
		mini.get("flowdraftid").setValue(flowdata.flowdraftid);
		//ajax加载已存在的大字段
		data["flowdraftid"] = flowdata.flowdraftid;
		data["applyid"] = flowdata.applyid;
		loadFormAipFileStr(data);
		
	}else{
		//ajax加载报表
		ajaxLoadReport(menuid, data);
	}
}

//如果已经走流程，则关掉"提交"按钮，并显示相关提示
function isFlowApprovedOfMonthKaoHe(data){
	var result = null;
	$.ajax({
	      url: path +"/flow/getUvFlowDataByParam.json?1=1",
	      data: data,
	      dataType: 'text',
	      type: "post",
	      async: false,
	      success: function (text){
	    	  if(text){
	    		  result = mini.decode(text);
	    	  }
	      },
	      error: function (jqXHR, textStatus, errorThrown) {
	          //alert("操作失败!");
	      }
	});
	return result;
}


function ajaxLoadReport(menuid, data){
	if(menuid){
		mini.get("flowdraftid").setValue(null);
		data["menuid"]=menuid;
		$.ajax({
		      url: path +"/getReportData.json?1=1",
		      data: data,
		      contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		      cache: false,
		      dataType: 'text',
		      type: "post",
		      async: false,
		      success: function (text){
		    	  mini.get('flag').setValue('report');
		    	  mini.get('reportdata').setValue(text);
		    	  onLoad();
		      },
		      error: function (jqXHR, textStatus, errorThrown) {
		          alert("操作失败!");
		      }
		  });
	}
}

/**
 * 狱政奖惩统计
 */
function yzjcStatistic(menuid){
	
	 var data = {};
	data["tempid"] =  mini.get("tempid").getValue();
	data["flowdefid"] = mini.get("flowdefid").getValue();
	
	var rptype = mini.get("rptype").getValue();
	if(rptype){
		data["rptype"] = rptype;
	}
	var startdate = mini.get("startdate").getValue();
	if(startdate){
		data["startdate"] = startdate;
	}
	var enddate = mini.get("enddate").getValue();
	if(enddate){
		data["enddate"] = enddate;
	}
	//ajax加载报表
	ajaxLoadReport(menuid, data);
}


//---狱政管理工作月报表  begin 20150907
function SaveYZGLsData(docconent, noteinfo, masterslave, operateType, bizName){
		var resid = '';
		var flag = false;
   		
   		var tempid = mini.get("tempid").getValue();
		var flowdefid = mini.get("flowdefid").getValue();
		flowdefid = flowdefid || 'other_sysflow';//如果是非流程的表单保存，默认用other_sysflow(系统流程);
		var solutionid = mini.get("solutionid").getValue();
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
		if(docconent){
			data["docconent"] = docconent; //获取大字段
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
		if(noteinfo){
			data["noteinfo"] = noteinfo+"";
		}
		
		var flowid = '';
		var commenttext = '';
		var savetype = '';
		var examineid = '';
		var shenptype = '';
		//临时变量
		var temp1 = mini.get("temp1").getValue();
		data["temp1"] = temp1;
		$.ajax({
	         url: path+"/flow/flowapprove.action?1=1", //保存流程表
	         data: {applyid:applyid,applyname:applyname,operateType:operateType,tempid:'SXYZ_GLGZYBB',
	         		flowdefid:'other_sysflow',resid:resid,docconent:docconent},
	         type: "POST",
	         dataType:"text",
	         async:false,
	         success: function (text){
	        	    if(operateType == "save")
	         		data["flowdraftid"] =text;     	   
	        	    data["new_flowdraftid"] = text;
			   		var url=path+"/ajaxSaveYZGLnDoc.action?1=1"; //保存业务表			   		
			        $.ajax({
			            url:encodeURI(encodeURI(url)),
			            data: data,
			            type: "POST",		            
			            async:false,
			            success: function (text) {
			            	//text = mini.decode(text);
			            	alert("操作成功！");			            	
			            	Close();
 
			            },
			            error: function (jqXHR, textStatus, errorThrown) {
			            	alert("操作失败!");
			            }
			        });  
	         },
             error: function (jqXHR, textStatus, errorThrown) {
            	   alert("操作失败!");
             }
		});
        return flag;
}

//保存自动关闭-狱政
function CloseBD(){
	if(window.parent.goBack){
		window.parent.goBack();
	}else{
		goBack();
	}
}


function submitBaseInfo(){
	var row = grid.getSelected();
	var crimid = row.crimid;
	var url = path + "/basicInfo/basicInformation.page?1=1&crimid="+crimid+"&closetype=3"
					+"&tempid=SDXF_BASE_RJDJB&flowdefid=doc_zfrjdjsp&menuid=11090&fathermenuid=10191";
   	var win=mini.open({
          url: url,
          showMaxButton: true,
          allowResize: true, 
          onload: function (){
              var iframe = this.getIFrameEl();
              var data = {};
              iframe.contentWindow.SetData(data);
          },
          ondestroy: function (action){
        	  grid.reload();
          }
	});
    win.max();
}



function submitSentence(){
	var row = grid.getSelected();
	var crimid = row.crimid;
	var tempid = 'XFZX_XQBD';
	var url = path + "/toSentenceChangeListPage.page?1=1&tempid="+tempid+"&crimid="+row.crimid+"&menuid=10135_01&closetype=3";
   	var win=mini.open({
          url: url,
          showMaxButton: true,
          allowResize: true, 
          onload: function (){
              var iframe = this.getIFrameEl();
              var data = {};
              iframe.contentWindow.SetData(data);
          },
          ondestroy: function (action){
        	  grid.reload();
          }
	});
    win.max();
}

function submitProperty(){
	var row = grid.getSelected();
	var crimid = row.crimid;
	var url = path + "/toCaiChanXingListPage.page?1=1&crimid="+crimid+"&menuid=18584&name="+row.name + "&closetype=3";
   	var win=mini.open({
          url: url,
          showMaxButton: true,
          allowResize: true, 
          onload: function (){
              var iframe = this.getIFrameEl();
              var data = {};
              iframe.contentWindow.SetData(data);
          },
          ondestroy: function (action){
        	  grid.reload();
          }
	});
    win.max();
}

//监区调动通知书
function transferNotice(menuid,tempid,solutionid){
	var row = grid.getSelected();
	var flowdraftid = row.flowdraftid;
	var optype = 'add';
	if(flowdraftid){
		optype = 'edit';
	}else{
		flowdraftid = "";
	}
	//用于返回到主页面的url
	var url = "baseDocOperate.page?1=1&flowdraftid="+flowdraftid+"&tempid="+tempid+"&solutionid="+solutionid+"&menuid="+menuid+"&tflowdraftid="
				+ row.tflowdraftid +"&xuhao=" + row.xuhao +"&optype="+optype;
	var win = mini.open({
        url: url,
        showMaxButton: true,
        allowResize: false,
        title: "",
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

//新增互监组
function addSupervision(){
	var url = path + "/toNewSupervisionPage.action?1=1";
	var win = mini.open({
        url: url,
        showMaxButton: true,
        allowResize: false,
        title: "",
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

function editSupervision(){	
    var row = grid.getSelected();
    if(row) {
    	var url = path + "/toEditSupervisionPage.page?1=1";
    	var id = row.id;
    	var svtype = row.svtype;
    	var svtypename = row.svtypename;
        var pknum = row.pknum;
        var policemen = row.policemen;
        
    	url +="&id="+id;
    	url +="&svtype="+svtype;
    	url +="&svtypename="+svtypename;
    	url +="&pknum="+pknum;
    	url +="&policemen="+policemen;
    	url = encodeURI(encodeURI(url));
    	
    	var win = mini.open({
            url: url,
            showMaxButton: true,
            allowResize: false,
            title: "",
            onload: function (){
                var iframe = this.getIFrameEl();
                var data = { action: "edit"};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action){
            	grid.reload();
            }
        });
        win.max();
    }else {
         alert("请选中一条记录");
    }
}


/**
 * 值班日记导出
 * @author YangZR	2015-10-28
 */
function dutyDiaryExport(menuid, excid){
	dataExportByParam(menuid, excid, 0, null);
}



function xcyhActionRenderer(e){
	var row = e.record;
	var sorg = row.sorg;
	var dorg = row.dorg;
 	var s = '';
 	
 	if(sorg && sorg==1){
 		s += " <a class=\"Edit_Button\" href=\"javascript:xiugai('3609917');\" >通知书</a>";
 	}
 	if(dorg && dorg==1){
 		s += " <a class=\"Edit_Button\" href=\"javascript:xiugai('950958');\" >整改报告</a>";
 	}
 	s += " <a class=\"Edit_Button\" href=\"javascript:check('3609920');\" >查看</a>";
 	
 	if(sorg && sorg==1){
 		s += " <a class=\"Edit_Button\" href=\"javascript:remove('3609907');\" >删除</a>";
 	}
 	
    return s;
}

function jzwgActionRenderer(e){
	var row = e.record;
	var sorg = row.sorg;
	var dorg = row.dorg;
	var s = '';
	
	if(sorg && sorg==1){
		s += " <a class=\"Edit_Button\" href=\"javascript:xiugai('3609934');\" >通知书</a>";
	}
	if(dorg && dorg==1){
		s += " <a class=\"Edit_Button\" href=\"javascript:xiugai('951014');\" >整改报告</a>";
	}
	s += " <a class=\"Edit_Button\" href=\"javascript:check('3609937');\" >查看</a>";
	
	if(sorg && sorg==1){
		s += " <a class=\"Edit_Button\" href=\"javascript:remove('3609925');\" >删除</a>";
	}
	
	return s;
}


function newDiary(menuid, diarytype){
	var url = path + "/toDiaryPage.page?1=1&operatetype=new&diarytype="+diarytype;
	var win = mini.open({
        url: url,
        showMaxButton: true,
        allowResize: false,
        title: "日记", 
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

function editDiary(menuid, diarytype){
	var row =grid.getSelected();
	if(row){
		var flowdraftid = row.flowdraftid;
		var url = path + "/toDiaryPage.page?1=1&operatetype=edit&diarytype="+diarytype;
		url += "&flowdraftid="+flowdraftid;
		var win = mini.open({
	        url: url,
	        showMaxButton: true,
	        allowResize: false,
	        title: "日记", 
	        onload: function (){
	            var iframe = this.getIFrameEl();
	            var data = { action: "edit"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action){
	        	grid.reload();
	        }
	   });	
	   win.max();
	}
}

