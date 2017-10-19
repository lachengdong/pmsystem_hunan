<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监区立案12</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    .s{
    	width:100%;height:100%;_height:94.8%;
    }    
    </style>
     <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var batchAlertOne = "您要对以下所选服刑人员进行减刑立案吗?";
     	var batchAlertTwo = "您要对以下所选服刑人员进行假释立案吗?";
     </script>
</head>
<body>
    <div class="mini-toolbar" style="padding:0px;border-bottom:0;">
    	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
		<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
		<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
		<input id="batch" name="batch" type="hidden" value="${batch}"/><!-- 批次-->
		
        <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        
        <table style="width:100%;">
            <tr> 										
                <td style="white-space:nowrap;width:40%;">
            		(${curyear}年第${batch}批)
    				案件号：（<input id="curyear" name="curyear" class="mini-textbox" value="${curyear}" style="width:40px;"/>）
    				${shortname}
    				
    				<input id="casetype" class="mini-combobox" valueField="id" textField="text" url="<%=path%>/getCodeByCode.json?1=1&codeType=GK7799&pcodeid=GK7799"
    				 style="width:80px;" onvaluechanged="addAnjianhao" value="jy"/>

					<input id="jailcasechg" class="mini-combobox" valueField="id" textField="text" url="<%=path%>/getCodeByCode.json?1=1&codeType=GK8899&pcodeid=GK8899"
    				 style="width:50px;" />

    				<input id="casenumber" name="casenumber" class="mini-textbox" value="${casenumber}" style="width:40px;text-align:center"/> 号
    				&nbsp;立案日期：<input id="liandate" name="liandate" style="width:100px;" class="mini-datepicker" />
                </td>
                
                <td style="white-space:nowrap;width: 50%" align="right">
	                <a class="mini-button" id="609229" iconCls="icon-new" plain="true" onclick="viewReportOperate(609229,'减刑假释案件监区立案公示');" style="display:none">公示</a>
	                <a class="mini-button" id="609239" iconCls="icon-new" plain="true" onclick="viewReportOperate(609239,'湖南汇总');" style="display:none">汇总</a>
	           		<input id="sel" class="mini-combobox" style="width: 65px;" emptyText="是否立案" name="gender" value="1" onvaluechanged="search" 
	           			data="[{ id: 1, text: '未立案' }, { id: 2, text: '已立案'}]" />
	           		<input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号/姓名/拼音" style="width:100px;" onenter="onKeyEnter"/>   
	                <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
	                <span class="separator"></span> 
	               	<span id="jxdiv">
	                   	<a class="mini-button" id="10089_01" iconCls="icon-ok"  plain="true" onclick="batchCommute('0')">批量减刑</a>
	                   	<a class="mini-button" id="10089_01" iconCls="icon-ok"  plain="true" onclick="batchCommute('1')">批量假释</a>
	                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10089')"></a>
	                </span>
                </td>
            </tr>
        </table>           
    </div>
   
    <div class="mini-fit" id="div_two">
	    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
	        	 url="<%=path%>/getLiAaInfoList.action?flowdefid=${flowdefid}" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
	        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
	        <div property="columns">
	            <div type="checkcolumn" width="30"></div>        
	            <div field="crimid" width="80" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
	            <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
	            <div field="originalyear" width="75" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
	            <div field="sentencestime" width="80" headerAlign="center" align="center"  allowSort="true">原判刑期起日</div>  
	            <div field="sentenceetime" width="80" headerAlign="center" align="center"  allowSort="true">原判刑期止日</div> 
	            <div field="predate" width="80" headerAlign="center" align="center"  allowSort="true">上次减刑日期</div>
	            <div field="jianggeqi" width="90" headerAlign="center" align="center"  allowSort="true">减刑间隔期</div> 
	            <div field="" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid", "desc");
      	grid.load();
      	var batch = '${batch}';
      	var shortname = '${shortname}';
      	 
        //var Liandatas = [{ id: 1, text: '未立案' }, { id: 2, text: '已立案'}];
        //mini.get("casetype").select(0);
        mini.get("liandate").setValue(new Date());
        mini.get("jailcasechg").select(0);
        
        function onActionRenderer(e){
            var record = e.record;
            var crimid = record.crimid;
			var s = "";
			s += '<a class="Edit_Button" id="10953" href="javascript:commuteLiAn(\'0\')" >减刑</a>&nbsp;';
			s += '<a class="Edit_Button" id="10953" href="javascript:commuteLiAn(\'1\')" >假释</a>&nbsp;';
			s += '<a class="Edit_Button" id="10953" href="javascript:chehui(\''+  crimid + '\')" >撤回 </a>&nbsp;';
            return s;
        }
        
       //动态显示案件号
       function addAnjianhao(casetype) {
	    	if (typeof(casetype) == "object") {
	           	var combobox = casetype.sender;
	           	casetype = combobox.getText();
			}
       		var curyear = mini.get("curyear").getValue();
       		var flowdefid = mini.get("flowdefid").getValue();
       		if(!casetype) casetype = mini.get("casetype").getText();
       		$.ajax({
			         url: "<%=path%>/flow/getMaxCaseNum4HuNanJailCommute.json",
			         data: {curyear:curyear, flowdefid:flowdefid, casetype:casetype},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	var a = eval(text);
			         	var b = parseInt(a);
			        	mini.get("casenumber").setValue(b);
			         }
			});
       }
      
	
    //判断基本信息是否已经提交 
	function isCriminalBaseInfoSubmit(ids){
    	var result = true;
    	var flowdefid = 'doc_zfrjdjsp';
		$.ajax({
			url:'<%=path%>/flow/submitBaseInfo.action',
			data:{ids:ids, flowdefid:flowdefid},
			type:'post',
			dataType:'text',
			async:false,
			success:function (message){
				message = mini.decode(message);
				if(message["status"] == '0'){
					alert("罪犯" + message["info"] + "基本信息未提交，无法立案!");
					result = false;
				}
			}
		});
		
		return result;
	}
    
    //判断刑期变动是否提交， 如果刑期变动未提交那么无法进行办案（ajax参数二暂时不用）
	function submitSentchange(ids){
    	var result = true;
		$.ajax({
			url:'<%=path%>/flow/submitSentchange.action',
			data:{ids:ids},
			type:'post',
			dataType:'text',
			async:false,
			success:function (message){
				message = mini.decode(message);
				if(message["status"] == '0'){
					alert("罪犯" + message["info"] + "，无法立案!");
					result = false;
				}
			}
		});
		return result;
    }
    
    
    //立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回false
    function isHaveCaseLiAn(flowdefid, crimids){
    	var result = true;
		$.ajax({
	         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
	         data: {flowdefid:flowdefid,crimids:crimids},
	         type: "POST",
	         dataType:"json",
	         async:false,
	         success: function (text){
	        	text =eval(text);
	         	if(text != 0){
	  				alert("罪犯"+text + "已立案，请刷新页面！");
	  				result = false;
	  			}
	         }
		});
		
		return result;
    }
    
    
    function tiaozhuan(menuid,crimid){
    	window.parent.openTodoListPage1(menuid,crimid);
    }
    
    //判断立案条件是否满足
    function checkSingleLiAnCondition(crimid, name){
    	
    	//判断基本信息是否已经提交 如果基本信息为提交那么 不让立案
    	var ids = crimid + "@" + name;
    	var flag = isCriminalBaseInfoSubmit(ids);
		if(! flag){
			tiaozhuan('10191',crimid);
			return false;
		}
		
		flag = submitSentchange(ids);
	    if(! flag){
	    	tiaozhuan('10135',crimid);
	    	return false;
	    }
	    
	    return true;
    }
    
    
    
    function getLiAnTooltip(commutetype,names){
    	var commutetypeCn = "";
    	if(commutetype=='1'){
    		commutetypeCn = '假释';
    	}else{
    		commutetypeCn = '减刑';
    	}
    	return "确定对服刑人员【"+ names +"】进行" + commutetypeCn + "立案？";
    }
    
	
    
	//单个立案
	function commuteLiAn(commutetype){
      	var row = grid.getSelected();
      	if(row){
      		var crimid = row.crimid;
      		var name = row.name;
			var _flag = checkSingleLiAnCondition(crimid, name);
			if(! _flag){
				return;
			}
			var rows = [];
	      	rows[0]=row;
	      	dealCommuteLiAn(rows,commutetype);
      	}
	}
  
	//批量立案
    function batchCommute(commutetype){
    	var rows = grid.getSelecteds();
    	if(rows.length>0) {
    		
    		var idArr = [];
            for(var i = 0, l = rows.length; i < l; i++) {
            	var crimid = rows[i].crimid;
            	var name = rows[i].name;
            	var id = crimid + "@" + name;
            	//
            	idArr.push(id);
            }
            var ids = idArr.join(",");
            
            var flag = isCriminalBaseInfoSubmit(ids);
    		if(! flag){
    			return false;
    		}
    		//
    		flag = submitSentchange(ids);
    	    if(! flag){
    	    	return false;
    	    }
            
            dealCommuteLiAn(rows,commutetype);
    	}
    }
     	
     
    /**
	     立案处理
	*/
	function dealCommuteLiAn(rows,commutetype){
    	
		if(rows.length>0){
		 	//
		 	var flowdefid = mini.get("flowdefid").getValue();
		 	var crimidArr = [];
		 	var nameArr = [];
		 	var dataArr = [];
		 	var crimids = '';
		 	var names = '';
		 	var dataStr = '';
		 	//
		 	for(var i=0,l=rows.length;i<l;i++){
		 		var row = rows[i];
		 		crimidArr.push(row.crimid);
		 		nameArr.push(row.name);
		 		dataArr.push(row.crimid+"@"+row.name+"@"+row.orgid+"@"+row.nowpunishmenttype);
		 	}
		 	crimids = crimidArr.join(',');
		 	names = nameArr.join(',');
		 	dataStr = dataArr.join(',');
		 	
		 	//是否已立过案
		 	flag = isHaveCaseLiAn(flowdefid,crimids);
		 	if(false==flag){
		 		return;
		 	}
		 	
		 	var mess = getLiAnTooltip(commutetype,names);
		 	if(confirm(mess)){
		         //var resid = '10089_01';
				var operateType = 'save';
				var tempid = mini.get("tempid").getValue();
				var curyear = mini.get("curyear").getValue();
			    var casetype = mini.get("casetype").getText();
			    var liandate = mini.get("liandate").getText();//立案日期
			    var jailcasechg = mini.get("jailcasechg").getText();
			    var data = {};
			    data["flowdefid"]=flowdefid;
			    data["tempid"]=tempid;
			    data["operateType"]=operateType;
			    data["dataStr"]=dataStr;
			    data["commutetype"]=commutetype;
			    data["curyear"]=curyear;
			    data["casetype"]=casetype;
			    data["liandate"]=liandate;
			    data["jailcasechg"]=jailcasechg;
			    data["batch"]=batch;
			    data["shortname"]=shortname;
			    data["operateid"]=getUUID();//用于区分同一个批量操作的uuid
			    
			    var url = "<%=path%>/flow/batchLiAnFlowApprove.action";
			    //
			    $.ajax({
		             url: url,
			         data: data,
			         type: "POST",
			         dataType:"json",
			         async:false,
			         cache: false,
		             success: function (text){
		              	var obj = eval(text);
		              	if(obj==1){
		              		alert("操作成功!");
		         			addAnjianhao(casetype);//案件号加n
		              	}else{
		              		alert("操作失败!");
		         		}
		             	grid.reload();
		             },
		             error: function (jqXHR, textStatus, errorThrown){
		                 alert("操作失败!");
		                 grid.reload();
		             }
		      	});
				    
		 	}else{
		 		grid.reload();
		 	}
		}else{
		 	alert(confirmMessages);
		}
	}
      
		
		
	//快速查询
	function search() {
		var key = mini.get("key").getValue();
		var sel=mini.get("sel").getValue();
		grid.load({ key: key,sel:sel});
	}
		
	function onKeyEnter(e) {
		search();
	}
        

	//撤回
 	function chehui(crimid){
       	var row = grid.getSelected();
       	var curyear = mini.get("curyear").getValue();
		if(row){
			var mess = "确定对服刑人员【"+ row.name +"】撤消立案？";
			if(confirm(mess)){
				var applyid = row.crimid;
				$.ajax({
			         url: "<%=path%>/flow/chehui.json", 
			         data: {crimid:applyid,curyear:curyear,batch:batch},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	var obj = eval(text);
			         	if(obj == '1') {
			         		alert("该罪犯已立案!");
  				         	}else{
  				         		alert("操作成功!");
  				         	}
			         	grid.reload();
			         },
			         error: function (jqXHR, textStatus, errorThrown) {
		                   alert("操作失败!");
		                   grid.reload();
		                 }
				});
           	}else{
           		grid.reload();
           	}
		}else{
			alert(confirmMessage);
		}
	}
      
 	function viewReportOperate(menuid,name,nodeid,rtnodeid) {
		var myurl = "baseReportOperate.page?1=1&optype=add&doctype=" + name+"&menuid="+menuid;
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
			allowResize : true,
			title : "预览",
		});
		win.max();
	}
	
	 	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function onDateRenderer(e) {
		if(e && e.value){
			return mini.formatDate ( new Date(e.value), "yyyy年MM月dd日" );
		}
		return "";
	}
      
      	
    </script>
</body>
</html>