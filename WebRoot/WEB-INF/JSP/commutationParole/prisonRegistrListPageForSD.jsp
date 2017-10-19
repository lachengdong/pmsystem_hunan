<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监区立案</title>
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
                <td style="width:80%;">
            		<!--  (${curyear}年第${batch}批)-->
    				案件号：（<input id="curyear" name="curyear" class="mini-textbox" value="${curyear}" style="width:40px;"/>）
    				${shortname} 
    				<input id="casetype" class="mini-combobox" valueField="id" textField="text" data="[{ id: 0, text: '减字' }, { id: 1, text: '假字'} ]"
    				 onvaluechanged="addAnjianhao()" style="width:50px;" value="0" />
    				<!-- <input id="casetype" class="mini-combobox" valueField="id" textField="text"  
						url="getCaseTypes.action"   style="width:50px;" value="1"/> -->
    				第 <input id="casenumber" name="casenumber" class="mini-textbox" value="${casenumber }" style="width:40px;text-align:center"/> 号
    				&nbsp;立案日期：<input id="caseacceptdate" name="caseacceptdate" style="width:100px;" class="mini-datepicker" />
                <a class="mini-button" id="10089_01" iconCls="icon-ok"  plain="true" onclick="batchCommute('0')">批量减刑</a>
                    	<a class="mini-button" id="10089_01" iconCls="icon-ok"  plain="true" onclick="batchCommute('1')">批量假释</a>
                </td>
                <td style="white-space:nowrap;width: 100%">
           <input id="sel" class="mini-combobox" style="width: 65px;" emptyText="是否立案" name="gender" value="1" onvaluechanged="search" data="[{ id: 1, text: '未立案' }, { id: 2, text: '已立案'}]" />
           <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音" style="width:125px;" onenter="onKeyEnter"/>   
                    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
                    <span class="separator"></span> 
                	<span id="jxdiv">
                     	<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10089')"></a>
                    </span>
                </td>
            </tr>
        </table>           
    </div>
   
    <div class="mini-fit" id="div_two">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getLiAaInfoList.action?flowdefid=${flowdefid}" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
        <div property="columns">
            <div type="checkcolumn" width="30"></div>        
            <div field="crimid" width="80" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
            <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
            <div field="age" width="40" headerAlign="center" align="center"  allowSort="true">年龄</div>
            <div field="originalyear" width="75" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
            <div field="sentencestime" width="80" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div>  
            <div field="sentenceetime" width="80" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期止日</div> 
            <div field="nowpunishmentyear" width="90" headerAlign="center" align="center"  allowSort="true">现余刑</div>
            <div field="predate" width="80" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">上次减刑日期</div>
            <div field="jianggeqi" width="90" headerAlign="center" align="center"  allowSort="true">减刑间隔期</div> 
         	<!--<div field="jianxinfudu" width="100" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" name="jianxinfudu">减刑幅度</div>           
         	<div field="createtime" width="80" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" name="createtime">申报时间</div>           
           -->
            <div field="" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
        </div>
    </div>
  </div>
    <script type="text/javascript">
    	var Liandatas = [{ id: 1, text: '未立案' }, { id: 2, text: '已立案'}];
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid", "desc");
      	grid.load();
        
        mini.get("caseacceptdate").setValue(new Date());
       
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var crimid = record.crimid;
            var rowIndex = e.rowIndex;
			var sel = mini.get("sel").getValue();
			//var anjiantype = mini.get("anjiantype").getValue();
			var anjiantype = '';
			var s = "";
			s = '<a class="Edit_Button" id="10953" href="javascript:commuteLiAn(\'0\')" >减刑</a>&nbsp;'
				+'<a class="Edit_Button" id="10953" href="javascript:commuteLiAn(\'1\')" >假释</a>&nbsp;'
				+'<a class="Edit_Button" id="10953" href="javascript:chehui(\''+  crimid + '\')" >撤回 </a>&nbsp;'   
				+ '<a class="Edit_Button" id="10089_02" href="javascript:viewinfo(\''+  crimid + '\')" >查看信息</a>';
			if(sel == 2) s = ' <a class="Edit_Button" id="10089_02" href="javascript:viewinfo(\''+  crimid + '\')" >查看信息</a>&nbsp;';
            return s;
        }
        
        //动态显示案件号
        function addAnjianhao(commutetype) {
        		var curyear = mini.get("curyear").getValue();
        		var flowdefid = mini.get("flowdefid").getValue();
        		if(!commutetype) commutetype = mini.get("casetype").getValue();
        		$.ajax({
				         url: "<%=path%>/flow/getMaxCaseNum.action", 
				         data: {curyear:curyear, flowdefid:flowdefid, commutetype:commutetype},
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
        
        //立案
        function commuteLiAn(commutetype){
        	var row = grid.getSelected();
			if(row){
				var id = row.crimid;
				var flowdefid = mini.get("flowdefid").getValue();
				//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
				var result="";
				$.ajax({
			         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
			         data: {flowdefid:flowdefid,id:id},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	var result = eval(text);
			         }
				});
				if(result != 0){
					alert("罪犯"+result + "已立案，请刷新页面！");
					return;
				}
				
				var mess = "确定对服刑人员【"+ row.name +"】进行减刑立案？";
				if(commutetype=='1'){
					mess = "确定对服刑人员【"+ row.name +"】进行假释立案？";
				}
				
				if(confirm(mess)){
					var applyid = row.crimid;
					var applyname = row.name;
					var orgid = row.orgid;
					var nowpunishmenttype = row.nowpunishmenttype;//现刑期年
					var curyear = mini.get("curyear").getValue();
					var casenumber = mini.get("casenumber").getValue();
					var resid = '10089_01'; //流程的开始资源节点
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					//var casetype = '减字';
					
					//var data = getNoteMap();//表单所有节点及值
					//var docconent = saveFile();//获取大字段
					$.ajax({
				         url: "<%=path%>/flow/jiangQuLiAn.action", 
				         data: {resid:resid,operateType:operateType, orgid:orgid, nowpunishmenttype:nowpunishmenttype, commutetype:commutetype,
				         flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname, curyear:curyear,batch:${batch},shortname:'${shortname}'},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var obj = eval(text);
				         	if(obj=='-1') {
				         		alert("操作失败!");
				         	}else{
				         		if(obj == '1') alert("该罪犯已立案!");
				         		else alert("操作成功!");
				         		mini.get("casetype").setValue(commutetype);
				         		addAnjianhao(commutetype);
				         	}
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
		

		
		 //批量立案
        function batchCommute(commutetype){
        	var rows = grid.getSelecteds();
            if (rows.length>0) { 
            	var mess = batchAlertOne;
            	if(commutetype=='1'){
            		mess = batchAlertTwo;
            	}					
            	
            	if(confirm(mess)){     
            		var flowdefid = mini.get("flowdefid").getValue();
            		var idArr = [];
            		var dataArr = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                    	var r = rows[i];
                    	idArr.push(r.crimid);
                        dataArr.push(r.crimid+"@"+r.name+"@"+r.orgid+"@"+r.nowpunishmenttype);
                    }
                    var id = idArr.join(',');
                    var dataStr = dataArr.join(',');
                    
            		//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
					/* 此处判断不用 已挪到后台处理了
					var result="";
					$.ajax({
				         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
				         data: {flowdefid:flowdefid,id:id},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var result = eval(text);
				         }
					});
					if(result != 0){
						alert("罪犯"+result + "已立案，请刷新页面！");
						return;
					}	
            		*/
            			   	
                    var resid = '10089_01';
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					var curyear = mini.get("curyear").getValue();
				    var casenumber = mini.get("casenumber").getValue();
				    //var casetype = '减字';
                     $.ajax({
		                 url: "<%=path%>/flow/batchLiAnFlowApprove.action",
				         data: {resid:resid,operateType:operateType,curyear:curyear, commutetype:commutetype,
				         flowdefid:flowdefid,tempid:tempid,dataStr:dataStr,batch:${batch},shortname:'${shortname}'},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         cache: false,
		                 success: function (text) {
		                 	var obj = eval(text);
		                 	if(obj=='-1'){
		                 		alert("操作失败!");
		                 	}else{
		                 		alert("操作成功!");
		                 		mini.get("casetype").setValue(commutetype);
			         			addAnjianhao(commutetype);//案件号加n
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
            	alert(confirmMessages);
            }
        }
        
        //查看基本信息
        function viewinfo(e){
        	FatherTabs('551323', 0,e);
        }
        
        function FatherTabs(menuid, lock, applyid,noOperate){
     	   var applyid="";
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
     	               applyid = row.crimid;
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
     	               idArr.push(row.crimid+'@'+menuid);
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
		
		//查看罪犯详细信息
		function checkInfo() {
			var row = grid.getSelected();
			var crimid = row.crimid;
            if (row) { 
                mini.open({
                	  url:"<%=path%>/basicInfo/basicInformation.page?1=1&crimid="+crimid+"&closetype="+3,
                	   title: "罪犯信息", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { mymenuid : "8328" , myoperatetype:'view'};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
                
            } else {
                alert("请选中一条记录");
            }
		}

		//快速查询
        function search() {
        	myloading();
            var key = mini.get("key").getValue();
            var sel=mini.get("sel").getValue();
            grid.load({ key: key,sel:sel});
        }
        function onKeyEnter(e) {
            search();
        }
        function onanjianchanged(){
        	var anjiantype = mini.get("anjiantype").getValue();
        	if(anjiantype=='7') { 
        		grid.hideColumn("jianxinfudu");
        		document.getElementById("jsdiv").style.display="";
        		document.getElementById("jxdiv").style.display="none";
        		mini.get("sqid").setUrl("getSchemeTree.action?qtype=zgsc&schemetype=20140315105848718qt");
        	} else {
        		grid.showColumn("jianxinfudu");
        		document.getElementById("jsdiv").style.display="none";
        		document.getElementById("jxdiv").style.display="";
        		mini.get("sqid").setUrl("getSchemeTree.action?qtype=zgsc&schemetype=<s:property value='#request.schemetype'/>");
        	}
        }
		function jfkh() {
			var row = grid.getSelecteds();
			var crimids='';
			for(var i=0;i<row.length;i++){
                var crimid = row[i].crimid;
                if(i == row.length-1){
                	crimids+="'"+crimid+"'";
                }else{
                	crimids+="'"+crimid+"',";
                }
                
			}
            if (!row.length == 0) { 
                mini.open({
                    url:"getYnjcReport.action?1=1&choosecrimid="+crimids+"&menuid=12258",
                    title: "狱内奖惩", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
                        //var iframe = this.getIFrameEl();
                        //var data = {};
                        //iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        
                    }
                });
            } else {
                alert("请选中一条记录");
            }
		}
		
		function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy年MM月dd日" );
        	}
            return "";
        }
		//撤回
	 function chehui(crimid){
        	var row = grid.getSelected();
			if(row){
				var mess = "确定对服刑人员【"+ row.name +"】撤消立案？";
				if(confirm(mess)){
					var applyid = row.crimid;
					$.ajax({
				         url: "<%=path%>/flow/chehui.json", 
				         data: {crimid:applyid},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var obj = eval(text);
				         	if(obj=='1') {
				         		alert("操作成功!");
				         	}else{
				         		 alert("操作失败!");
				         	}
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
    </script>
</body>
</html>