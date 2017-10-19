<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();

Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
<head>
	<title>案件办理(监狱)</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
  	<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
  	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }     
   	</style>
	<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
	<!-- author：张永有 time：2015年2月5号上午,因为批量提交时需要control_approve.js中的一个方法commBatchSubmit,因此需要引入,谁有更有效率的方法可以更改。 -->
	<script type="text/javascript" src="<%=path%>/scripts/form/control/control_approve.js"></script>
    <script type="text/javascript">
    	//快速查询
        function search() {
        	var key = mini.get("key").getValue();
        	var year = mini.get("year").getValue();
            var jianqu = mini.get("jianqu").getValue();
        	var casetype = mini.get("casetype").getText();
        	var casenums = mini.get("casenums").getValue();
            var xingqileixing = mini.get("xingqileixing").getValue();
            var banjieqingkuang = mini.get("banjieqingkuang").getValue();
        	grid.sortBy("anjianhao","asc");
            grid.load({ xingqileixing:xingqileixing,jianqu:jianqu,year:year, key: key, casenums:casenums,casetype:casetype,banjieqingkuang:banjieqingkuang});
        }
        function onKeyEnter(e) {
            search();
        }
		function onvaluechang() {
			search();
        }
		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
        	var fatherMenuid = document.getElementById("menuid").value;
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var row = rows[i];
                        var flowid = row.flowid;
                        if(flowid==undefined){
        					flowid = "flowidnull";
        				}
                        ids.push(row.crimid+"@"+row.orgid+"@"+row.flowdraftid+"@"+flowid+"@"+row.lastnodeid);
                    }
                    var id = ids.join(',');
                    var closetype = "2";
                    var flowdefid = mini.get("flowdefid").getValue();
                    var tempid = mini.get("tempid").getValue();
                    var ischeckseal = mini.get("ischeckseal").getValue();
                    var provinceid = mini.get("provinceid").getValue();
		        	var nodeid = mini.get("nodeid").getValue();
		        	var days = mini.get("days").getValue();
		        	var url = "toCommuteOfJailCaseTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid+"&ischeckseal="+ischeckseal;
                    url += "&provinceid="+provinceid+"&nodeid="+nodeid+"&days="+days;
                    self.location.href=url;
                }
            } else {
                alert( "请至少选中一条记录！");
            }
		}
		
		
		function chooseOne(menuid) {
        	var row = grid.getSelected();
			var crimid = row.crimid;
			var orgid = row.orgid;
			var flowdraftid = row.flowdraftid;
			var flowid = row.flowid;
        	var fatherMenuid = document.getElementById("menuid").value;
        	var lastnodeid = row.lastnodeid;
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var ischeckseal = mini.get("ischeckseal").getValue();
        	
        	var provinceid = mini.get("provinceid").getValue();
        	var nodeid = mini.get("nodeid").getValue();
        	var days = mini.get("days").getValue();
              
        	var closetype = "2";
        	if(flowid==undefined){
        		flowid = "flowidnull";
        	}
        	var id = crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid;
        	$.ajax({
                 url: "flow/ajaxReturnLockUser.json?1=1",
                 type: "post",
                 data: {flowdraftid:flowdraftid},
                 async: false,
                 success: function (text) {
                 	var result = eval(text);
                 	if(result != '-1'){
                     	alert("此案件正由"+result+"审批。");
                     	grid.reload();
                     }else{
                     	var url = "toCommuteOfJailCaseTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid+"&ischeckseal="+ischeckseal;
             			url += "&provinceid="+provinceid+"&nodeid="+nodeid+"&days="+days;
             			self.location.href=url;
                     }
                 },error: function () {
                 	alert("操作失败!");
                 }
            });
        	
        }
		
		//会议记录
		/**
		* butMenuid : 按钮ID,
		* tempid ：系统模板ID
		*/
		function meetingRecord(menuid,tempid){
			var rows = grid.getSelecteds();
			/*if(rows == 0){
				alert(confirmMessages);
				return ;
			}*/
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
			var crimid='';
			var flowid='';
			var criname='';
			var flowdraftid='';
            if (rows.length > 0) {
                //if (confirm("确定操作 选中记录？")) {
                	var crimids =[];
					var flowdraftids = [];
					var crinames=[];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        crimids.push(r.crimid);
                        crinames.push(r.name);
                        flowdraftids.push(r.flowdraftid);
                        flowid = r.flowid;
                    }
                    crimid = crimids.join(',');
                    criname = crinames.join(',');
                    flowdraftid = flowdraftids.join(',');
             /*   }
                else
                {
                 alert(confirmMessagess);
                }*/
            }
            
           	var win=mini.open({
                  url:"toMinutesOfTheMeeting.action?1=1",
                  showMaxButton: true,
                  allowResize: true, 
                  title: "会议记录", width: "900", height: "600",
                  onload: function () {
                      var iframe = this.getIFrameEl();
                      var data = { menuid:menuid, flowid:flowid,tempid:tempid,crimids:crimid,flowdraftids:flowdraftid,criname:criname};
                      iframe.contentWindow.SetData(data);
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
      		});
            
	        win.max();
		}

		//会议记录
		/**
		* butMenuid : 按钮ID,
		* tempid ：系统模板ID
		*/
        
        function meetingNewRecord(menuid,xtempid,tempid){
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
		
			var crimid='';
			var flowid='';
			var criname='';
			var flowdraftid='';
            if (rows.length > 0) {
               	var crimids =[];
				var flowdraftids = [];
				var crinames=[];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    crimids.push(r.crimid);
                    crinames.push(r.name);
                    flowdraftids.push(r.flowdraftid);
                    flowid = r.flowid;
                }
                crimid = crimids.join(',');
                criname = crinames.join(',');
                flowdraftid = flowdraftids.join(',');
               
            }
           	var win=mini.open({
                  url:"toNewMinutesOfTheMeeting.action?1=1&menuid="+menuid+"&tempid="+tempid+"&flowdraftids="+flowdraftid,
                  showMaxButton: true,
                  allowResize: true, 
                  title: "会议记录", width: "900", height: "600",
                  onload: function () {
                      var iframe = this.getIFrameEl();
                      var data = { menuid:menuid, flowid:flowid,tempid:tempid,crimids:crimid,flowdraftids:flowdraftid,criname:criname};
                      iframe.contentWindow.SetData(data);
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
      		});
            
	        win.max();
		}
		
		//评审会 会议记录(资源id，模板id)
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
            
            //循环 组织 罪犯 编号 
            for(var i=0;i<rows.length;i++){
                ids.push(rows[i].crimid);
                flowdraftids.push(rows[i].flowdraftid);
            }
            var win = mini.open({
                type:'POST',
                url:'gotoMeetPage.action?1=1&ids='+ids+"&tempid="+tempid+"&resid="+resid+"&flowdraftids="+flowdraftids,
                success:function (){
                    var iframe = this.getIFrameEl();
                    var data = {ids:ids,tempid:tempid,resid:resid,flowdraftids:flowdraftids};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy:function(){
                	grid.reload();
                }
            });
            win.max();
	    }
	    
	</script>
</head>
<body onload="init('${menuid}');">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       	<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
       	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
       	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	    <input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
    	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>      	
    	<input id="ischeckseal" name="ischeckseal" class="mini-hidden" value="${ischeckseal}"/>
	   	<input id="provinceid" name="provinceid" class="mini-hidden" value="${provinceid}"/>
	    <input id="nodeid" name="nodeid" class="mini-hidden" value="${nodeid}"/>
    	<input id="days" name="days" class="mini-hidden" value="${days}"/>
    	<input id="ningxia" name="ningxia" type="hidden" value="${ningxia }"/>

            <table >
               <tr>
                <td style="width:100%;">
                	<a class="mini-button"  style="display:none;" id="11297"  iconCls="" plain="true" onclick="chooseAll('10091_06');">批量处理</a>
                	
                	<!-- 分监区办案 -->
                	<a class="mini-button"  style="display:none;" id="10091_04"  iconCls="" plain="true" onclick="meetingRecord('10091_04','9988');">分监区会议记录</a>
                	<a class="mini-button" style="display:none;" id="12583"  iconCls="" plain="true" onclick="batchSubmit('12584');">批量提交</a>
                   	<a class="mini-button" style="display:none;" id="13933" iconCls=""  defalutValue="分监区会议记录(宁夏石嘴山)" plain="true" onclick="meettingRecord_sh('13933','SX_FJQ_HYJL');">分监区会议记录</a>
                   	
                   	<!-- 监区评议 上海 张永有注 2014-12-6 13：39 -->
                	<a class="mini-button" style="display:none;" id="13711"  iconCls="" plain="true" defalutValue="监区会议记录(上海)" onclick="meettingRecord_sh('900101','9999');">监区集体评议记录</a>
                   	<!--监区评议 -->
                   	<a class="mini-button" style="display:none;" id="10092_04"  iconCls="" plain="true" onclick="meetingRecord('10092_04','9989');">监区长会议</a>
                	<a class="mini-button" style="display:none;" id="11298"  iconCls="" plain="true" onclick="batchSubmit('10091_01');">批量提交</a>
                	<a class="mini-button" style="display:none;" id="10091_05"  iconCls="" plain="true" onclick="meetingRecord('10091_05','9996');">会议记录</a>
                	
                	<!--监区集体评议记录  -->
                	<a class="mini-button" style="display:none;" id="10092_05" iconCls="" plain="true" onclick="meetingRecord('10092_05','9995');">监区会议记录</a>
                	<a class="mini-button" style="display:none;" id="13932" iconCls=""  defalutValue="监区会议记录(宁夏石嘴山)" plain="true" onclick="meettingRecord_sh('13932','SX_JQZ_HYJL');">监区会议记录</a>
                	
                	<!--陕西监区评议记录   meetingRecord('15788','10326')-->
                	<a class="mini-button" style="display:none;" id="15788"  iconCls="" plain="true" onclick="meettingRecord_sh('15788','10326');">监区长会议</a>
                	<!-- 陕西科室会议记录 -->
                	<a class="mini-button" style="display:none;" id="15956" iconCls="" plain="true" onclick="meetingRecord('15956','10421');">科室会议记录</a>
                	
                	<!-- 陕西评审会会议记录 -->
                	<a class="mini-button" style="display:none;" id="15957" iconCls="" plain="true" onclick="meetingRecord('15957','10422');">评审会会议记录</a>
                	<!--陕西监狱长办公会集体评议记录  -->
                	<a class="mini-button" style="display:none;" id="15958" iconCls="" plain="true" onclick="meetingRecord('15958','9574');">监狱长办公会会议记录</a>
                	
            		<!-- 狱政科审批 -->
                	<a class="mini-button" style="display:none;" id="13907"  iconCls="" plain="true" onclick="batchSubmit('12526');">批量提交</a>
                	
                	<!--科室集体评议记录  -->
                	<a class="mini-button" style="display:none;" id="10092_06" iconCls="" plain="true" onclick="meetingRecord('10092_06','9996');">科室会议记录</a>
                	<a class="mini-button" style="display:none;" id="13931" iconCls="" plain="true" defaultValue="科室会议（宁夏石嘴山）" onclick="meettingRecord_sh('13931','SX_XFZXK_HYJL');">科室会议记录</a>
                	               	
                	<!-- 科室会议记录 -->
                	<a class="mini-button" style="display:none;" id="15689" iconCls="" plain="true" onclick="meetingRecord('15689','9996');">科室会议</a>
                	
                	
                	<!--评审会集体评议记录  -->
                	<a class="mini-button" style="display:none;" id="10092_07" iconCls="" plain="true" onclick="meetingRecord('10092_07','9997');">评审会会议记录</a>
                	
                	<!--监狱长办公会集体评议记录  -->
                	<a class="mini-button" style="display:none;" id="10092_08" iconCls="" plain="true" onclick="meetingRecord('10092_08','9998');">监狱长办公会会议记录</a>
                	
                	<!-- 部门审查 -->
                	<a class="mini-button" style="display:none;" id="10092_01"  iconCls="" plain="true" onclick="batchSubmit('11429');">批量提交</a>
                	
                	
                	<!-- 监狱评审 -->
                	<!--  <a class="mini-button" style="" id="13742" iconCls="" plain="true" oldvalue="上海评审会会议记录" onclick="meettingRecord_sh('13742','SH_JXJS_KSHYJL');">评审会会议记录</a>
                	               该处会议记录功能暂时不用
                	-->
                	<a class="mini-button" id="802049" style="display:none;"  iconCls="" plain="true" defaultValue="宁夏评审会会议记录" onclick="meettingRecord_sh('802049','SX_JXJS_KSHYJL');">评审会会议</a> 
                	
                	<a class="mini-button" id="13742" iconCls="" style="display:none;"  plain="true"  onclick="meettingRecord_sh('13742','9990');">评审会会议</a>              	
                	
                	
                	<a class="mini-button" style="display:none;" id="10255_01"  iconCls="" plain="true" onclick="meetingRecord('10255_01','9990');">评审会会议</a>
                   	<a class="mini-button" style="display:none;" id="10955"  iconCls="" plain="true" onclick="batchSubmit('12056');">批量提交</a>
                	
                	<!-- 监狱签发 -->
                	<!-- <a class="mini-button" style="" id="13765" iconCls="" plain="true" oldvalue="上海监狱长会议记录" onclick="meettingRecord_sh('13765','JYZBGH_HYJLSH');">监狱长会议记录</a>
                	             该处会议记录功能暂时不用
                	-->
                	<a class="mini-button" id="802050" style="display:none;"  iconCls="" plain="true" defaultValue="宁夏评审会会议记录" onclick="meettingRecord_sh('802050','SX_JYZ_HYJL');">监狱长会议</a> 
                	
                	<a class="mini-button" id="13765" style="display:none;"   iconCls="" plain="true" defaultValue="上海监狱长会议记录" onclick="meettingRecord_sh('13765','9991');">监狱长会议</a>              	
                	
                	<a class="mini-button" style="display:none;" id="10093_04"  iconCls="" plain="true" onclick="meetingRecord('10093_04','9991');">监狱长会议</a> 
                   	<a class="mini-button" style="display:none;" id="10093_01"  iconCls="" plain="true" onclick="batchSubmit('11434');">批量提交</a>
                   	<a class="mini-button" style="display:none;" id="18365" iconCls="" plain="true" onclick="meetingNewRecord('18365','9988','JXJS_FJQSHHYJL');">分监区会议记录</a>
					<a class="mini-button" style="display:none;" id="18382" iconCls="" plain="true" onclick="meetingNewRecord('18382','9988','JXJS_JQSHHYJL');">监区会议记录</a>
					<a class="mini-button" style="display:none;" id="18383" iconCls="" plain="true" onclick="meetingNewRecord('18383','9988','JXJS_KSSHHYJL');">刑罚执行科会议记录</a>
					<a class="mini-button" style="display:none;" id="18385" iconCls="" plain="true" onclick="meetingNewRecord('18385','9988','JXJS_WYHSHHYJL');">评审委员会会议记录</a>
					<a class="mini-button" style="display:none;" id="18386" iconCls="" plain="true" onclick="meetingNewRecord('18386','9988','JXJS_JYZSHHYJL');">监狱长办公会会议记录</a>
                   	
                </td>
                <td style="white-space:nowrap;">
                	<input name="banjieqingkuang" id="banjieqingkuang" class="mini-combobox" style="width:80px;" valueField="id" textField="text"  emptyText="办结情况" onvaluechanged="onvaluechang()"
	                      data="bjqkdata" required="false" showNullItem="true" />&nbsp;
	                <input name="jianqu" id="jianqu" class="mini-combobox" style="width:110px;" valueField="ORGID" textField="NAME"  emptyText="请选择监区" onvaluechanged="onvaluechang()"
	                      url="getDepartList.action?1=1&qtype=jianqu" required="false" showNullItem="true" nullItemText="请选择监区"/>&nbsp;
	                <input name="xingqileixing" id="xingqileixing" class="mini-combobox" style="width:100px;" valueField="id" textField="text"  emptyText="请选择刑期类型" onvaluechanged="onvaluechang()"
	                      data="[{ id: 1, text: '有期徒刑' }, { id: 2, text: '无期徒刑'} ,{ id: 3, text: '死刑，缓期两年执行'}]" required="false" showNullItem="true" nullItemText="请选择刑期类型"/>&nbsp;
	                <input id="year" class="mini-combobox" valueField="id" textField="text" url="getDateUPDownArea.action" style="width:60px;" value="${year}" showNullItem="true" nullItemText="选择年度"/>
	                &nbsp;${shortname}&nbsp; <input id="casetype" class="mini-combobox" valueField="id" textField="text" data="[{ id: 0, text: '减字' }, { id: 1, text: '假字'} ]" style="width:50px;" value="0" />	
               		<input class="mini-textbox" id="casenums" class="mini-textbox" style="width:155px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
               		<input class="mini-textbox" id="key" class="mini-textbox" emptyText="罪犯编号、姓名、拼音" style="width:95px;" onenter="onKeyEnter"/>
                   	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid }')" style="width:25px;"></a>
		 		</td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit" id="div_two">
	    <div id="datagrid" allowMoveColumn="false"  onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="getCommuteOfJailCaseList.action?1=1&flowdefid=${flowdefid }&provinceid=${provinceid}&nodeid=${nodeid}&days=${days}"  idField="" multiSelect="true" allowAlternating="true" 
	    	sizeList="[10,20,50,100,500,1000,1500,2000,2500,3000]" pageSize="20"  showLoading="true">
	        <div property="columns">
        		 <div type="checkcolumn" width="10"></div>
          		 <div field="anjianhao" width="65"  headerAlign="center"  	allowSort="true" align="center" >案件号</div> 
       			 <div field="crimid" width="40"  headerAlign="center"  	allowSort="true" align="center" >罪犯编号</div> 
          		 <div field="name" width="30"  headerAlign="center"  	allowSort="true" align="center" >罪犯姓名</div>  
	             <div field="age" width="20" headerAlign="center" align="center"  allowSort="true">年龄</div>
	             <div field="originalyear" width="40" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
	             <div field="sentencestime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div>  
	             <div field="sentenceetime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期止日</div> 
	             <div field="nowpunishmentyear" width="40" headerAlign="center" align="center"  allowSort="true">现余刑</div>
	             <div field="predate" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">上次减刑日期</div>
	             <div field="jianggeqi" width="40" headerAlign="center" align="center"  allowSort="true">减刑间隔期</div>
	             <div field="orgname1" width="30" headerAlign="center" align="center"  allowSort="true">所在监区</div>
           		 <div id="status" width="40px"  headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">状态</div> 
       			 <div id="action" width="50px"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
	        </div>
	    </div>
    </div>
    <div style="height: 0px;">
		<table>
			<tr>
				<td height="100%">
					<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
				</td>
			</tr>
		</table>		
	</div>
    
    <script type="text/javascript">
    	var bjqkdata=[{ id: 1, text: '已办结' }, { id: -1, text: '未办结'} ,{ id: 3, text: '全办结'}];
    	mini.parse();
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
  
    var countIndex = 0;
   	function batchSubmit(resid){
   		var aipObj=document.getElementById("HWPostil1");	
		//aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");//登录签章
		var crinames = '';
		var rows = grid.getSelecteds();
		var flowdefid = mini.get("flowdefid").getValue();
		var tempid = mini.get("tempid").getValue();
		var ischeckseal = mini.get("ischeckseal").getValue();
		var result = '';
		if(ischeckseal=='1'){
			$.ajax({
	           url:'<%=path%>/sign/getSignatureScheme.json?1=1',
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
		}
        if (rows.length > 0) {
            if (confirm("确定操作选中记录？")) {
                var ids = [];
                for(var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    /**
                      *签章之后的案件才可以批量提交  
                      *取出当前用户对应签章方案进程的最大值 
                      *如果当前文件上面的章 小于进程的最大 值 那么将无法进行提交
                      **/
					if(ischeckseal=='1'){
						//获取 减刑假释审核表中章的个数
	                    //var flowdraftid_par = r.flowdraftid;
	                    //var flowid_par = r.flowid;
	                    //var bigdata = '';
	                    //var signnumber = 0;
	                    //var notation = 0;
	                    //$.ajax({
	                    //    type:'post',
	                    //     url:'getJXJSBigData.action?1=1&flowdraftid='+flowdraftid_par+'&flowid='+flowid_par+'&tempid='+tempid+'&flowdefid='+flowdefid,
	                    //     async:false,
	                    //     success:function (text){
	                    //	     var objs = eval('('+text+')');
	                    //	     bigdata = objs.content;
	                    //	     signnumber = objs.signnumber;
	                    //	     notation = objs.notation;
	                    //     }
	                    //});
	                    //aipObj.LoadFileBase64(bigdata);//打开模板文件
	                    
	                    //var sealNum = aipObj.GetNoteNum(1);//批注
	            		//var signatureNum = aipObj.GetNoteNum(251);//章
	                  	//把不能提交的罪犯 取消选择
	                  	var resultArr = result.split("@");
					    if((resultArr[0]!=-1&&resultArr[0]!=r.text8.split("@")[0])||(resultArr[1]!=-1&&resultArr[1]!=r.text8.split("@")[1])){
					    	crinames =crinames+r.name+",";
	                  		grid.deselect(r,true);
					    }
	                  	if(result!=''&&r.text8!=result){
	                  		
	                    }
					}
                    ids.push(r.flowdraftid+"@"+r.flowid+"@"+r.orgid+"@"+resid);
                }
                if(ischeckseal=='1' && crinames.length > 0){
                	alert("罪犯："+crinames.substring(0,crinames.length-1)+"未办理完毕,已取消选择!");
                	return;
                }
                var id = ids.join(',');
	            commBatchSubmit(id,flowdefid,tempid);
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
 	
 	function wardenDataFilter(e){
 		var rows = grid.findRows(function(row){
   		 	if(row.lastnodeid == 'N0004'&&row.wardenoperflag=='0'&&row.porgid=='1200'){
   		 		return true;
   		 	}
   		 	
		});
		grid.removeRows(rows, false);
 	}
        
    </script>
</body>
</html>

        