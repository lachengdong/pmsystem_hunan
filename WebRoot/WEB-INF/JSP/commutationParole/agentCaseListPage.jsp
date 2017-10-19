<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String uri = request.getRequestURI();
	String queryString = request.getQueryString();
	String returnUrl = uri + queryString;
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>经办人办案(分监区)</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
    	</style>
    <script type="text/javascript">
     	//快速查询
        function search() {
        	var key = mini.get("key").getValue();
        	var year = mini.get("year").getValue();
        	var casenums = mini.get("casenums").getValue();
            var casetype = mini.get("casetype").getText();
            var xingqileixing =  mini.get("xing").getValue();
            grid.sortBy("anjianhao","asc");
            grid.load({xingqileixing:xingqileixing,year:year, key:key, casenums:casenums,casetype:casetype});
        }
        function onKeyEnter(e) {
            search();
        }
		function onValueChange() {
        	search();
        }
		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
        	var fatherMenuid = document.getElementById("menuid").value;
        	var closetype=1;
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++){
                        var row = rows[i];
                        var flowid = row.flowid;
                        if(flowid==undefined){
        					flowid = "flowidnull";
        				}
        				//crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+findid;
                        ids.push(row.crimid+"@"+row.areaid+"@"+row.flowdraftid+"@"+flowid+"@"+row.lastnodeid);
                    }
                    var flowdefid = mini.get("flowdefid").getValue();
                    var tempid = mini.get("tempid").getValue();
                    var ischeckseal = mini.get("ischeckseal").getValue();
                    var id = ids.join(',');
                    var url = "toCommuteOfJailCaseTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid+"&ischeckseal="+ischeckseal;
                    url = encodeURI(encodeURI(url));
                    self.location.href=url;
                }
            } else {
                alert( "请至少选中一条记录！");
            }
		}
		
		function chooseOne(menuid) {
			var row = grid.getSelected();
			var crimid = row.crimid;
			var orgid = row.areaid;
			var flowdraftid = row.flowdraftid;
			var flowid = row.flowid;
			var lastnodeid = row.lastnodeid;
        	var fatherMenuid = document.getElementById("menuid").value;
        	var tempid= mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var ischeckseal = mini.get("ischeckseal").getValue();
        	var tempid2 = mini.get("tempid2").getValue();
        	if(flowid==undefined){
        		flowid = "flowidnull";
        	}
        	
        	var returnValue = ajaxReturnLockUser(flowdraftid);
        	if(returnValue==false){
        		grid.reload();
        	}else{
        		var closetype = 1;
                var id = crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid;
                var url = "toCommuteOfJailCaseTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid+"&ischeckseal="+ischeckseal+"&tempid2="+tempid2;
        		self.location.href=url;
        	}
        }
        
        
        function ajaxReturnLockUser(flowdraftid){
        	var row=grid.getSelected();
        	var returnValue = false;
        	$.ajax({
                 url: "flow/ajaxReturnLockUser.json?1=1",
                 type: "post",
                 data: {flowdraftid:flowdraftid},
                 async: false,
                 success: function (text) {
                	var result = eval(text);
                 	if(result != '-1'){
                     	//alert("罪犯"+result+"的案件正由其它用户审批。");
                     	alert("罪犯"+row.name+"的案件正由"+result+"用户审批");
                     }else{
                     	returnValue = true
                     }
                 },error: function (){
                 	alert("操作失败!");
                 }
            });
            
            return returnValue;
        }
		
		//撤销案件
		function withdrawCases(){
			var rows = grid.getSelecteds();
			var menuid = '';
			var menuidObj = document.getElementById("menuid");
			if(menuidObj) menuid = menuidObj.value;
			var flowdefid = mini.get("flowdefid").getValue();
			
            if (rows.length > 0) {
                if (confirm("确定操作选中记录？")) {
                    var idArr = [];
                    var crimidArr = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        idArr.push(r.flowdraftid);
                        crimidArr.push(r.crimid);
                    }
                    var id = idArr.join(',');
                    var crimids = crimidArr.join(',');
                    $.ajax({
                        url: "withdrawalCasesOfJail.action?1=1",
                        type: "post",
                        data: {flowdraftids:id, menuid:menuid, crimids:crimids},
                        success: function (text) {
                        	var result = eval(text);
                        	if(result =="success"){
                        		alert("操作成功！");
                        	}else{
                        		alert("操作失败！");
                        	}
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
                    });
                }
            } else {
                alert(confirmMessages);
            }
		}
		
		
		//会议记录
		/**
		* butMenuid : 按钮ID,
		* tempid ：系统模板ID
		*/
		function meetingRecord(butMenuid,tempid){
			var rows = grid.getSelecteds();
			var crimid='';
			var flowid='';
			var flowdraftid='';
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                	var crimids =[];
					var flowdraftids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        crimids.push(r.crimid);
                        flowdraftids.push(r.flowdraftid);
                        flowid = r.flowid;
                    }
                    crimid = crimids.join(',');
                    flowdraftid = flowdraftids.join(',');
                }
            }
           	var win=mini.open({
                  url:"toMinutesOfTheMeeting.action?1=1",
                  showMaxButton: true,
                  allowResize: false, 
                  title: "会议记录", width: "900", height: "600",
                  onload: function () {
                      var iframe = this.getIFrameEl();
                      var data = { menuid:butMenuid, flowid:flowid,tempid:tempid,crimids:crimid,flowdraftids: flowdraftid};
                      iframe.contentWindow.SetData(data);
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
      		});
	        win.max();
		}
		
		// 分监区 会议记录 
		function meettingRecord_sh(resid,tempid){
            var rows = grid.getSelecteds();
            var ids = [];
            var flowdraftids = [];
            var judge = 0;//未保存 对应的 会议记录 
            //判断本批次(本部门)是否保存过会议记录(如果有一次：直接打开，否者判断是否选择罪犯，选择则继续执行!)
            //添加批次查询的原因:防止打开上个批次的会议记录内容 
            $.ajax({
                type:'post',
                url:'judgeExistMeetByOrgid.action?1=1&tempid='+tempid,
                async:false,
                success:function(text){
                    judge = parseInt(text);
                }
            });
            //为保存会议情况下，判断是否 选择罪犯，选择则继续执行 
            if(judge == 0){
            	if(rows.length == 0){alert("请选择需要列入会议记录的罪犯!");return;}
            }else{
            	if(rows.length > 0){
                    if(confirm("已经存在一份会议记录\n确定重新生成吗?")){
                         
                    }else{
                         return;
                    }
                }
            }
            var bworjx = "1";
            //循环 组织 罪犯 编号 
            for(var i=0;i<rows.length;i++){
                ids.push(rows[i].crimid);
                flowdraftids.push(rows[i].flowdraftid);
            }
            var win = mini.open({
                type:'POST',
                url:'gotoMeetPage.action?1=1',
                onload:function (){
                    var iframe = this.getIFrameEl();
                    var data = {ids:ids,flowdraftids:flowdraftids,tempid:tempid,resid:resid,bworjx:bworjx,resid:resid};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy:function(){
                	grid.reload();
                }
            });
            win.max();
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
	    
	    function toFatherTabPage(menuid){
	    	
	    	var rows = grid.getSelecteds();
            if (rows.length > 0) {
	        	var flowdraftidArr = [];
	            for (var i = 0, l = rows.length; i < l; i++){
	                var row = rows[i];
	                flowdraftidArr.push(row.flowdraftid+'@'+menuid);
	            }
				var flowdraftids = flowdraftidArr.join(',');
				
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
	        	//用于返回到主页面的url
	        	var furl = encodeURIComponent(location.href);
	        	
                var url = "common/toFatherTabPage.action?1=1&ids="+flowdraftids+"&indexFlag="+0+"&furl="+furl;
                self.location.href=url;
            } else {
                alert( "请至少选中一条记录！");
            }
            
	    }
	    
	</script>
</head>
<body onload="init('${menuid}');">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	   <input id="newedit" name="newedit" type="hidden" value=""/>
	   <input id="ischeckseal" name="ischeckseal" class="mini-hidden" value="${ischeckseal}"/>
	   <input id="tempid2" name="tempid2" class="mini-hidden" value="${tempid2 }"/>
            <table >
               <tr>
                <td style="width:100%;">
                	<!-- 经办人办案 -->
                	<a class="mini-button"  style="display:none;" id="11296"  iconCls="" plain="true" onclick="chooseAll('10090_05');">批量处理</a>
                	<a class="mini-button"  style="display:none;" id="505077"  iconCls="" plain="true" onclick="toFatherTabPage('505021');">批量处理(3700)</a>
                	
                   	<a class="mini-button"  style="display:none;" id="10090_09"  iconCls="" plain="true" onclick="withdrawCases();">撤销案件</a>
                   	<a class="mini-button" style="display:none;" id="1000184" iconCls=""  defalutValue="干警集体评议" plain="true" onclick="meetingRecord('1000184','9988');">干警集体评议</a>
                   	<!-- 天津管教不需要显示分监区会议记录，如果哪里需要，自己修改为特有的id -->
                   	<a class="mini-button" style="display:none;" id="13933" iconCls=""  defalutValue="分监区会议记录(山西阳泉)" plain="true" onclick="meettingRecord_sh('13933','SX_FJQ_HYJL');">分监区会议记录</a>
                   	<!-- 宁夏用勿动 -->
                   	<a class="mini-button" style="display:none;" id="802022" iconCls=""  defalutValue="分监区会议记录(山西阳泉)" plain="true" onclick="meettingRecord_sh('13933','SX_FJQ_HYJL');">监区警察集体评议</a>
                </td>
                <td style="white-space:nowrap;">
                
                       <!--<input name="jianqu" id="jianqu" class="mini-combobox" style="width:120px;" valueField="ORGID" textField="NAME"  emptyText="请选择监区" onvaluechanged="search"
                            url="getDepartList.action?1=1&qtype=jianqu" required="false" showNullItem="true" nullItemText="请选择监区"/>&nbsp;  -->
                       <input name="xing" id="xing" class="mini-combobox" style="width:130px;" valueField="id" textField="text"  emptyText="请选择刑期类型" onvaluechanged="onValueChange()"
                           data="[{ id: 1, text: '有期徒刑' }, { id: 2, text: '无期徒刑'} ,{ id: 3, text: '死刑，缓期两年执行'}]" required="false"  showNullItem="true" nullItemText="请选择刑期类型"/>&nbsp;&nbsp;

    					<input id="year" class="mini-combobox" valueField="id" textField="text"  onvaluechanged="onValueChange()"
    						url="getDateUPDownArea.action"   style="width:65px;" value="${year}" showNullItem="true" nullItemText="选择年度"/>${shortname}
    					<input id="casetype" class="mini-combobox" valueField="id" textField="text" data="[{ id: 0, text: '减字' }, { id: 1, text: '假字'} ]" style="width:50px;" value="0" />	
                		<input class="mini-textbox" id="casenums" class="mini-textbox" style="width:200px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音"  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid }')"></a>
		 		</td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true" allowCellSelect="true"
	    	url="getJianBanRenCommuteList.action"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,200]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="13"></div> 
           			<div field="anjianhao" width="67"  headerAlign="center"  	allowSort="true" align="center" >案件号</div> 
        			<div field="crimid" width="30"  headerAlign="center"  	allowSort="true" align="center" >罪犯编号</div> 
           			<div field="name" width="25"  headerAlign="center"  	allowSort="true" align="center" >罪犯姓名</div>  
<!--		            <div field="age" width="15" headerAlign="center" align="center"  allowSort="true">年龄</div>-->
		            <div field="originalyear" width="30" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
		            <div field="sentencestime" width="35" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div>  
		            <div field="sentenceetime" width="35" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期止日</div> 
<!--		            <div field="nowpunishmentyear" width="35" headerAlign="center" align="center"  allowSort="true">现余刑</div>-->
		            <div field="predate" width="35" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">上次减刑日期</div>
		            <div field="jianggeqi" width="40" headerAlign="center" align="center"  allowSort="true">减刑间隔期</div>
		            <div field=orgname1 width="30" headerAlign="center" align="center"  allowSort="true">所在监区</div>
		            <div id="fjqyjstates" width="40"  headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">审批结果</div> 
<%--            	<div id="status" width="40"  headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">状态</div> --%>
        			<div id="action" width="65"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
	        </div>
	    </div>
    </div>
    <div id="editWindow" class="mini-window" title="编辑报表" style="width:700px;height:400px"
		    showModal="true" allowResize="true" allowDrag="true">
		    <div id="editform" class="form" style="min-height: 150px;" >
		        <div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="newOrEdit()" plain="true" iconCls="icon-save" style="width: 60px">存盘</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true" style="width: 60px;">取消</a>
							</td>
						</tr>
					</table>
				</div>
		        <fieldset style="width:600px;border:solid 0px #aaa;margin-top:8px;position:relative;">
		              <legend style="color:red;">三课成绩：</legend>
		              <table>
		                   <tr>
							<td style="width: 60px;">
								政治
							</td>
							<td style="width: 120px;">
								<input id="int11" style="width: 120px;" name="int11" class="mini-textbox"  vtype="maxLength:50"/>
							</td>
							<td style="width: 60px;">
								文化
							</td>
							<td style="width: 120px;">  
								<!-- <input id="type" name="type" style="width: 150px;" class="mini-textbox"  required="true"/> -->
							    <input id="int12" style="width: 120px;" name="int12" class="mini-textbox"  vtype="maxLength:50"/>
							</td>
							<td style="width: 60px;">
								技术
							</td>
							<td style="width: 120px;">  
							    <input id="int13" style="width: 120px;" name="int13" class="mini-textbox"  vtype="maxLength:50"/>
							</td>
						</tr>
		              </table>
		        </fieldset>
				<fieldset style="width:600px;border:solid 0px #aaa;margin-top:8px;position:relative;">
		              <legend  style="color:red;">行政奖励:</legend>
		              <table>
		                   <tr>
								<td style="width: 60px;">
									表扬
								</td>
								<td style="width: 120px;">
									<input id="int2" style="width: 120px;" name="int2" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								<td style="width: 60px;">
									记功
								</td>
								<td style="width: 120px;">  
									<!-- <input id="type" name="type" style="width: 150px;" class="mini-textbox"  required="true"/> -->
								    <input id="int3" style="width: 120px;" name="int3" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								<td style="width: 60px;">
									积极分子
								</td>
								<td style="width: 120px;">  
								    <input id="int4" style="width: 120px;" name="int4" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
							</tr>
							<tr>
								<td style="width: 60px;">
									嘉奖
								</td>
								<td style="width: 120px;">
									<input id="int1" style="width: 120px;" name="int1" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								<td style="width: 60px;">
									立功
								</td>
								<td style="width: 120px;">  
								    <input id="int5" style="width: 120px;" name="int5" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								<td style="width: 60px;">
									重大立功
								</td>
								<td style="width: 120px;">  
								    <input id="int6" style="width: 120px;" name="int6" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
							 </tr>
		              </table>
		        </fieldset>
		        <fieldset style="width:600px;border:solid 0px #aaa;margin-top:8px;position:relative;">
		              <legend  style="color:red;">行政惩罚:</legend>
		              <table>
		                   <tr>
								<td style="width: 60px;">
									警告
								</td>
								<td style="width: 120px;">
									<input id="int7" style="width: 120px;" name="int7" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								<td style="width: 60px;">
									记过
								</td>
								<td style="width: 120px;">  
								    <input id="int8" style="width: 120px;" name="int8" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								<td style="width: 60px;">
									禁闭
								</td>
								<td style="width: 120px;">  
								    <input id="int9" style="width: 120px;" name="int9" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
								
							</tr>
							<tr>
							    <td style="width: 60px;">
									严管
								</td>
								<td style="width: 120px;">  
									<!-- <input id="type" name="type" style="width: 150px;" class="mini-textbox"  required="true"/> -->
								    <input id="text1" style="width: 120px;" name="text1" class="mini-textbox"  vtype="maxLength:50"/>
								</td>
							</tr>
		              </table>
		        </fieldset>
		   </div>
		</div>
    <script type="text/javascript">
    	mini.parse();
    	var editWindow = mini.get("editWindow");
		var form = new mini.Form("#editform");
		var grid = mini.get("datagrid");
		grid.sortBy("anjianhao", "asc");
		grid.load();
		function onActionRenderer(e) {
	         var s ='';
	         s = document.getElementById('middlebtns').value;
	     	return s;
	    }
        
         
         function onStatusRenderer(e){
        	var s = "";
        	var row = e.record;
        	var state = row.state;
        	if(state=='0'){
        		s ='正在审批&nbsp';
        	}else{
        		s =row.conent;
        	}
            return s;
        }
        //考核奖惩录入
 	    function kaohejiangcheng(resid){
 		    editWindow.show();
            var row=grid.getSelected();
            var crimid = row.crimid;
            //查询出对应的数据
            $.ajax({
                type:'post',
                url:'getPrisonErperMance.action?1=1&crimid='+crimid,
                async:false,
                success:function (text){
            	     var o = mini.decode(text);
                     form.setData(o);
                     if(text.length == 2){
                         $("#newedit").val("new");
                     }else{
                    	 $("#newedit").val("edit");   
                     }
                }
            });
 		}
 		//新增或者修改考核信息
 		function newOrEdit(){
            var data = form.getData();
            var json = mini.encode([data]);
            var state = $("#newedit").val();
            var row = grid.getSelected();
            $.ajax({
                 type:'post',
                 url:'caoZuoPrisonErperMance.action?1=1',
                 data:{data:json,state:state,crimid:row.crimid},
                 async:false,
                 success:function(text){
                     if(parseInt(text) == 1){
                         if(state == 'new'){
                             alert('新增成功!');
                             $("#newedit").val("edit");
                         }else{
                             alert('修改成功!');
                         }
                     }
                 }
            });
 	    }
 		//关闭 按钮
 		function cancelEditWindow(){
             editWindow.hide();
             form.clear();
 	    }
 		function batchSubmit(resid){
 	   		var aipObj=document.getElementById("HWPostil1");	
 			var crinames = '';
 			var rows = grid.getSelecteds();
 			var flowdefid = mini.get("flowdefid").getValue();
 	        if (rows.length > 0) {
 	            if (confirm("确定操作 选中记录？")) {
 	                var ids = [];
 	                for(var i = 0, l = rows.length; i < l; i++) {
 	                    var r = rows[i];
 	                    /**
 	                      *签章之后的案件才可以批量提交  
 	                      *取出当前用户对应签章方案进程的最大值 
 	                      *如果当前文件上面的章 小于进程的最大 值 那么将无法进行提交
 	                      **/

 	                    //获取 减刑假释审核表中章的个数
 	                    var flowdraftid_par = r.flowdraftid;
 	                    var flowid_par = r.flowid;
 	                    var tempid = 'JXJS_JXJSSHB';
 	                    var bigdata = '';
 	                    var signnumber = 0;
 	                    var notation = 0;
 	                    $.ajax({
 	                         type:'post',
 	                         url:'getJXJSBigData.action?1=1&flowdraftid='+flowdraftid_par+'&flowid='+flowid_par+'&tempid='+tempid+'&flowdefid='+flowdefid,
 	                         async:false,
 	                         success:function (text){
 	                    	     var objs = eval('('+text+')');
 	                    	     bigdata = objs.content;
 	                    	     signnumber = objs.signnumber;
 	                    	     notation = objs.notation;
 	                         }
 	                    });
 	                    aipObj.LoadFileBase64(bigdata);//打开模板文件
 	                    
 	                    var sealNum = aipObj.GetNoteNum(1);//批注
 	            		var signatureNum = aipObj.GetNoteNum(251);//章
 	                  	//把不能提交的罪犯 取消选择
 	                  	if(signatureNum != signnumber || sealNum != notation){
 	                  		crinames =crinames+r.name+",";
 	                  		grid.deselect(r,true);
 	                    }
 	                    ids.push(r.flowdraftid+"@"+r.flowid+"@"+r.orgid+"@"+resid);
 	                }
 	                if(crinames.length > 0){
 	                    alert("罪犯："+crinames.substring(0,crinames.length-1)+"不符合签章,已取消选择!");
 	                    return;
 	                }
 	                var id = ids.join(',');
 	                var tempid = mini.get("tempid").getValue();
 	                var flowdefid = mini.get("flowdefid").getValue();
 	                commBatchSubmit(id,flowdefid,tempid);
 	                
 	            }
 	        } else {
 	            alert(confirmMessages);
 	        }
 	   	}
    </script>
</body>
</html>