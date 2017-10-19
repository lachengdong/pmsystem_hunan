<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>初保案件办理(监狱)</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
		<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
		<script type="text/javascript">
    var confirmMessages="请至少选中一条记录！";
     	//快速查询
        function search() {
        	var year = mini.get("year").getValue();
        	var casenums = mini.get("casenums").getValue();
            var key = mini.get("key").getValue();
            var jianqu = mini.get("jianqu").getValue();
            var xingqileixing = mini.get("xingqileixing").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ xingqileixing:xingqileixing,jianqu:jianqu,year:year, key: key, casenums:casenums});
        }
        function onKeyEnter(e) {
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
                    var url = "toOutPrisonOfJailCaseTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
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
        	var closetype = "2";
        	var id = crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid;
        	if(flowid==undefined){
        		flowid = "flowidnull";
        	}
        	
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
                            	var url = "toOutPrisonOfJailCaseTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid;
                            	//alert(url);
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
			if(rows == 0){
				alert(confirmMessages);
				return ;
			}
			var crimid='';
			var flowid='';
			var flowdraftid='';
            if (rows.length > 0) {
                if (confirm("确定操作选中记录？")) {
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
              
        
           	var win=mini.open({
                  url:"toMedicalParoleOfTheMeeting.action?1=1",
                  showMaxButton: true,
                  allowResize: false, 
                  title: "会议记录", width: "900", height: "600",
                  onload: function () {
                      var iframe = this.getIFrameEl();
                      var data = { menuid:menuid, flowid:flowid,tempid:tempid,crimids:crimid,flowdraftids: flowdraftid};
                      iframe.contentWindow.SetData(data);
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
      		});
            
	        win.max();
		}
	 	}
	    }

		//会议记录
		/**
		* butMenuid : 按钮ID,
		* tempid ：系统模板ID
		*/
		        
        function meetingNewRecord(menuid,xtempid,tempid){
			var rows = grid.getSelecteds();
			if(rows == 0){
				alert(confirmMessages);
				return ;
			}
			var crimid='';
			var flowid='';
			var criname='';
			var flowdraftid='';
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
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
                else
                {
                 alert(confirmMessagess);
                }
            }
           	var win=mini.open({
                  url:"toNewMinutesOfTheMeeting1.action?1=1&menuid="+menuid+"&tempid="+tempid+"&flowdraftids="+flowdraftid,
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
   
		// 会议记录 (保外 )
		// 分监区 会议记录 ( 宁夏用)请勿修改
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
            //根据这参数的值 进行组织保外 或者 减刑会议记录的标题
            //0保外就医，1减刑假释
            var bworjx = "0";
            var win = mini.open({
                type:'POST',
                url:'gotoMeetPage.action?1=1&ids='+ids+"&tempid="+tempid+"&resid="+resid+"&flowdraftids="+flowdraftids+"&bworjx="+bworjx,
                success:function (){
                    var iframe = this.getIFrameEl();
                    var data = {ids:ids,tempid:tempid,resid:resid};
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
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
			<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<input id="flowdefid" name="flowdefid" class="mini-hidden"
				value="${flowdefid}" />
			<table>
				<tr>
					<td style="width: 100%;" align="left">
						<a class="mini-button" style="display: none;" id="10111_2"
							iconCls="" plain="true" onclick="chooseAll('10111_06');">批量处理</a>

						<!--分监区集体评议记录  -->
						<a class="mini-button" style="display: none;" id="14100"
							iconCls="" plain="true" onclick="meetingRecord('14100','10102');">分监区会议记录</a>
						<!--初审小组  -->
						<a class="mini-button" style="display: none;" id="1000221"
							iconCls="" plain="true"
							onclick="meetingRecord('1000221','10120');">初审小组会议记录</a>
						<a class="mini-button" style="display: none;" id="1000129"
							iconCls="" plain="true"
							onclick="meetingRecord('1000129','10100');">监区会议记录</a>

						<a class="mini-button" style="display: none;" id="802024"
							iconCls="" defaultValue="宁夏" plain="true"
							onclick="meettingRecord_sh('802024','NX_BWJY_JQHYJL');">监区会议记录</a>
						<!-- 分监区办案 -->
						<a class="mini-button" style="display: none;" id="14154"
							iconCls="" plain="true" onclick="batchSubmit('14154');">批量提交</a>

						<!-- 天津用监区会议记录  -->
						<a class="mini-button" style="display: none;" id="13072"
							iconCls="" plain="true" onclick="meetingRecord('13072','10100');">监区会议记录</a>
						<!-- 陕西监区会议记录  -->
						<a class="mini-button" style="display: none;" id="15967"
							iconCls="" plain="true" onclick="meetingRecord('15967','10423');">监区会议记录</a>
						<!-- 陕西科室审查                	 -->
						<a class="mini-button" style="display: none;" id="10113_05"
							iconCls="" plain="true"
							onclick="meetingRecord('10113_05','10424');">科室办公会</a>

						<!-- 陕西监狱长会议记录                	 -->
						<a class="mini-button" style="display: none;" id="10115_05"
							iconCls="" plain="true"
							onclick="meetingRecord('10115_05','10426');">监狱长会议记录</a>
						<!-- 监区办案 -->
						<a class="mini-button" style="display: none;" id="14155"
							iconCls="" plain="true" onclick="batchSubmit('14155');">批量提交</a>

						<!-- 分监区办案 
                	<a class="mini-button"  style="display:none;" id="10113_05"  iconCls="" plain="true" onclick="meetingRecord('10113_05','9988');">科室办公会</a>
                	<a class="mini-button" style="display:none;" id="12583"  iconCls="" plain="true" onclick="batchSubmit('12584');">批量提交</a>
                   	-->
						<!--监区评议 
                   	<a class="mini-button" style="display:none;" id="10092_04"  iconCls="" plain="true" onclick="meetingRecord('10092_04','9989');">监区长会议</a>
                	<a class="mini-button" style="display:none;" id="11298"  iconCls="" plain="true" onclick="batchSubmit('10091_01');">批量提交</a>
                	<a class="mini-button" style="display:none;" id="10091_05"  iconCls="" plain="true" onclick="examinationReport();">审查报告(监区报科室)</a>
                	-->
						<!-- 科室审查                	 -->

						<a class="mini-button" style="display: none;" id="10113_05"
							iconCls="" plain="true"
							onclick="meetingRecord('10113_05','10120');">科室办公会</a>
						<a class="mini-button" style="display: none;" id="1000130"
							iconCls="" plain="true"
							onclick="meetingRecord('1000130','10120');">复合小组会议记录</a>
						<a class="mini-button" style="display: none;" id="10111_04"
							iconCls="" plain="true" onclick="batchSubmit('11451');">批量提交</a>
						<a class="mini-button" style="display: none;" id="802025"
							iconCls="" defaultValue="宁夏" plain="true"
							onclick="meettingRecord_sh('802025','NX_BWJY_KSHY');">科室会议记录</a>
						<!--   监狱评审 -->
						<a class="mini-button" style="display: none;" id="10113_05"
							iconCls="" plain="true"
							onclick="meetingRecord('10113_05','10120');">评审会会议</a>
						<a class="mini-button" style="display: none;" id="13928"
							iconCls="" plain="true" onclick="batchSubmit('13928');">批量提交</a>

						<a class="mini-button" style="display: none;" id="13929"
							iconCls="" plain="true" onclick="meetingRecord('13929','10120');">评审会会议</a>

						<!-- 监狱签发 -->
						<a class="mini-button" style="display: none;" id="802026"
							iconCls="" defaultValue="宁夏" plain="true"
							onclick="meettingRecord_sh('802026','NX_BWJY_JYZHY');">监狱会议记录</a>

						<a class="mini-button" style="display: none;" id="10115_05"
							iconCls="" plain="true"
							onclick="meetingRecord('10115_05','10120');">监狱长会议</a>
						<a class="mini-button" style="display: none;" id="10111_05"
							iconCls="" plain="true" onclick="batchSubmit('12608');">批量提交</a>
						<a class="mini-button" style="display: none;" id="18389" iconCls="" plain="true"
							onclick="meetingNewRecord('18389','9988','BWJY_JQSHHYJL');">监区会议记录</a>
						<a class="mini-button" style="display: none;" id="18390" iconCls="" plain="true"
							onclick="meetingNewRecord('18390','9988','BWJY_KSSHHYJL');">刑罚执行科会议记录</a>
						<a class="mini-button" style="display: none;" id="18388" iconCls="" plain="true"
							onclick="meetingNewRecord('18388','9988','BWJY_JYZSHHYJL');">监狱长办公会会议记录</a>
					</td>
					<td style="white-space: nowrap;">

						<input name="jianqu" id="jianqu" class="mini-combobox"
							style="width: 110px;" valueField="ORGID" textField="NAME"
							emptyText="请选择监区" showNullItem="true" nullItemText="请选择监区"
							url="getDepartList.action?1=1&qtype=jianqu" required="false"
							onvaluechanged="search" />
						&nbsp;
						<input name="xingqileixing" id="xingqileixing"
							class="mini-combobox" style="width: 120px;" valueField="id"
							textField="text" emptyText="请选择刑期类型" showNullItem="true"
							onvaluechanged="search"
							data="[{ id: 1, text: '有期徒刑' }, { id: 2, text: '无期徒刑'} ,{ id: 3, text: '死刑，缓期两年执行'}]"
							required="false" showNullItem="true" nullItemText="请选择刑期类型" />
						&nbsp;&nbsp;

						<input id="year" class="mini-combobox" valueField="id"
							textField="text" showNullItem="true" onvaluechanged="search"
							url="getDateUPDownArea.action" style="width: 65px;"
							value="${year}" nullItemText="选择年度" />
						${shortname} ${casetype }
						<input class="mini-textbox" id="casenums" class="mini-textbox"
							style="width: 160px;" emptyText="案件号范围 例如：1，8-37"
							onenter="onKeyEnter" />
						<input class="mini-textbox" id="key" class="mini-textbox"
							emptyText="请输入罪犯编号、姓名、拼音" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help"
							onclick="chakanshuoming('${menuid}')"></a>
					</td>
				</tr>
			</table>
		</div>

		<div class="mini-fit" id="div_two">
			<div id="datagrid" allowMoveColumn="false"
				style="width: 100%; height: 100%;" class="mini-datagrid"
				allowResize="false" allowSortColumn="true"
				url="getBaoWaiJailCaseList.action?1=1&flowdefid=${flowdefid}"
				idField="" multiSelect="true" allowAlternating="true"
				sizeList="[10,20,50]" pageSize="20" showLoading="true">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="caseno" width="50px" headerAlign="center"
						allowSort="true" align="center">
						案件号
					</div>
					<div field="crimid" width="30px" headerAlign="center"
						allowSort="true" align="center">
						罪犯编号
					</div>
					<div field="name" width="30px" headerAlign="center"
						allowSort="true" align="center">
						罪犯姓名
					</div>
					<div field="age" width="30px" headerAlign="center" allowSort="true"
						align="center">
						年龄
					</div>
					<div field="courtchangeto" width="30" headerAlign="center"
						dateFormat="yyyy-MM-dd" align="center" allowSort="true"
						renderer="onDateRenderer">
						刑期止日
					</div>
					<div field="nowpunishmentyear" width="40" headerAlign="center"
						align="center" allowSort="true">
						现余刑
					</div>
					<div field="commenttext" width="40" headerAlign="center"
						align="center" allowSort="true">
						审批意见
					</div>
					<div id="status" width="40px" headerAlign="center" align="center"
						allowSort="false" renderer="onStatusRenderer">
						状态
					</div>
					<div id="action" width="60px" headerAlign="center" align="center"
						allowSort="false" renderer="onActionRenderer">
						操作
					</div>
				</div>
			</div>
		</div>
		<div style="height: 0px;">
			<table>
				<tr>
					<td height="100%">
						<script LANGUAGE="javascript"
							src="<%=path%>/scripts/form/loadaip.js"></script>
					</td>
				</tr>
			</table>
		</div>

		<script type="text/javascript">
    	mini.parse();
    	//mini.get("datagrid").sortBy("caseno", "desc");
		var grid = mini.get("datagrid");
		grid.load();
		
		function onActionRenderer(e) {
	         var s ='';
	         s = document.getElementById('middlebtns').value;
			//s += " <a class=\"Edit_Button\" href=\"javascript:\" >办案</a>&nbsp;";
			//s += " <a class=\"Edit_Button\" href=\"javascript:\" >查看案件</a>&nbsp;";	
	     	return s;
	    }
        
         
         function onStatusRenderer(e){
        	var s = "";
        	var row = e.record;
        	var state = row.state;
        	//s ='未处理&nbsp;&nbsp';
        	if(state=='0'){
        		s ='正在审批&nbsp';
        	}else{
        		s =row.conent;
        	}
            return s;
        }
     
    var countIndex = 0;
   	function batchSubmit(resid){
   			var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                      //ids.push(r.crimid+"@"+r.orgid+"@"+r.flowid+"@"+r.flowdraftid+"@"+r.name);
                        ids.push(r.flowdraftid+"@"+r.flowid+"@"+r.orgid+"@"+resid);
                    }
                    var id = ids.join(',');
                    var tempid = mini.get("tempid").getValue();
                    var flowdefid = mini.get("flowdefid").getValue();
                    if(id!=null){
                    	commBatchSubmit(id,flowdefid,tempid);
                    }
                    //var operateType = 'yes';
                    //if(countIndex < rows.length){
                    	//singleApproveFlowYes(resid,id,countIndex,tempid,flowdefid,operateType);
                    //}
                }
            } else {
                alert(confirmMessages);
            }
   	}
    </script>
	</body>
</html>

