  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
   		
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>案件办理(省局处室经办)</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
		    .s{
    			width:100%;height:100%;_height:94.8%;
    		}     
   		 </style>
     
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
	
    <script type="text/javascript">
    	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	//快速查询
        function search() {
            var casenums = mini.get("casenums").getValue();
            var year = mini.get("year").getValue();
            var jailid = mini.get("jailid").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({year:year, casenums: casenums, jailid:jailid});
        }
        
        function onKeyEnter(e) {
            search();
        }
        
        function onjianyuchanged(){
        	search();
      	}
    
        	function onPageChanged() {
        		myloading();
        	}
        	function myloading(){
        		var myform = new mini.Form("datagrid");
       			myform.loading();
        	}
      		function unmask(){
      			myunmask();
      		}
        	function myunmask() {
        		var myform = new mini.Form("datagrid");
       			myform.unmask();
        	}
		
		function chooseAll(menuid){
		        	var rows = grid.getSelecteds();
		        	var fatherMenuid = document.getElementById("menuid").value;
		        	var closetype=2;
		            if (rows.length > 0) {
		                if (confirm("确定操作 选中记录？")) {
		                    var ids = [];
		                    for (var i = 0, l = rows.length; i < l; i++) {
		                        var row = rows[i];
		                        var flowid = row.flowid;
		                        if(flowid==undefined){
		        					flowid = "flowidnull";
		        				}
		                        ids.push(row.crimid+"@"+row.jailid+"@"+row.flowdraftid+"@"+flowid+"@"+row.lastnodeid);
		                    }
		                    var id = ids.join(',');
		                    var flowdefid = mini.get("flowdefid").getValue();
		                    var tempid= mini.get("tempid").getValue();
		                    //toCommuteOfProvinceCaseTabs
		                    var url = "toProvinceOutPrisonTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
		                    self.location.href=url;
		                }
		            } else {
		                alert( "请至少选中一条记录！");
		            }
		}
		
		function chooseOne(menuid) {
					var row = grid.getSelected();
					var crimid = row.crimid;
					var orgid = row.jailid;
					var flowdraftid = row.flowdraftid;
					var flowid = row.flowid;
					var lastnodeid = row.lastnodeid;
		        	var fatherMenuid = document.getElementById("menuid").value;
		        	var tempid = mini.get("tempid").getValue();
		        	var flowdefid = mini.get("flowdefid").getValue();
		        	var closetype = 2;
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
        		            	var url = "toProvinceOutPrisonTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid;
        						self.location.href=url;
            		        }
                        },
                        error: function () {
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
	</script>
</head>
<body onload="init('${menuid}');">
	<div id="div_one" class="s">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
    	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
       <jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
       <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	   <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
		<table>
               <tr>
                <td style="width:100%;">
                	<a class="mini-button" style="display:none;" id="10123_01"  iconCls="" plain="true" onclick="chooseAll('10123_05');">批量处理</a>
                	
                	<!-- 处室审查 -->
                	<!--  <a class="mini-button" style="display:none;" id="10254_06"  iconCls="" plain="true" onclick="">初审报告</a>
                	<a class="mini-button" style="display:none;" id="10254_07"  iconCls="" plain="true" onclick="">审查报告</a>-->
                	<a class="mini-button" style="display:none;" id="10123_03"  iconCls="" plain="true" onclick="batchSubmit('11474');">批量提交</a>
                	<!-- 省局评审会 -->
                	<a class="mini-button" style="display:none;" id="15882"  iconCls="" plain="true" onclick="meetingRecord('15882','10401');">评审会会议</a>
                	<a class="mini-button" style="display:none;" id=""  iconCls="" plain="true" onclick="">审查报告</a>
                	
                	<!-- 局长审批 -->
                	<a class="mini-button" style="display:none;" id="10254_05"  iconCls="" plain="true" onclick="meetingRecord('10254_05','10243');">会议记录</a>
                	<a class="mini-button" style="display:none;" id="13672"  iconCls="" plain="true" onclick="meetingRecord('13672','10244');">局领导会议</a>
                   	<a class="mini-button" style="display:none;" id="10254_04"  iconCls="" plain="true" onclick="batchSubmit('11480');">批量提交</a>
                </td>
                <td style="white-space:nowrap;">
                	<input id="year" class="mini-combobox" valueField="id" textField="text"  
    						url="getDateUPDownArea.action"   style="width:65px;" value="${year}" showNullItem="true" nullItemText="选择年度"/>${shortname} ${casetype }
                    <input class="mini-textbox" id="casenums" style="width:200px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search();">查询</a>
                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="onjianyuchanged"/>
                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
               </tr>
		</table>	 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	 url="getOutPrisonProvinceCaseList.json?1=1&flowdefid=${flowdefid}" idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50]" pageSize="20"  showLoading="true">
	        <div property="columns">
        		 <div type="checkcolumn" width="10"></div> 
          		 <div field="caseno" width="60px" headerAlign="center" allowSort="true" align="center" >案件号</div>  
       			 <div field="crimid" width="40px" headerAlign="center" allowSort="true" align="center" >罪犯编号</div> 
          		 <div field="name" width="40px" headerAlign="center" allowSort="true" align="center" >罪犯姓名</div>  
          		 <div field="age" width="20px" headerAlign="center" allowSort="true" align="center" >年龄</div>  
          		 <!-- 病残情况 opcillinstance  病残鉴定结果  opcillcheckresult 这两个字段不存在，不知道是不是某个省份的数据库有 ljp2014年12月8日 17:15:18-->
		         <div field="text20" width="40px" headerAlign="center" allowSort="true" align="center" >病残情况</div>  
		         <div field="text21" width="40px" headerAlign="center" allowSort="true" align="center" >病残鉴定结果</div>  
		         <div field="commenttext" width="40px" headerAlign="center" allowSort="true" align="center" >审批意见</div>  
		         <div field="jailname" width="40px" headerAlign="center" allowSort="true" align="center" >所属监狱</div>  
               	 <div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
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
 </div>
    <script type="text/javascript">
    	mini.parse();
    	//mini.get("datagrid").sortBy("caseno", "desc");
		var grid = mini.get("datagrid");
		grid.load();
		
		function onActionRenderer(e) {
	         var s ='';
	         s = document.getElementById('middlebtns').value;
	     	return s;
	    }
	        
		// 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
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
            if(rows.length > 0){
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.crimid+"@"+r.jailid+"@"+r.flowid+"@"+r.flowdraftid+"@"+r.name);
                    }
                    var id = ids.join(',');
                    var tempid = mini.get("tempid").getValue();
                    var flowdefid = mini.get("flowdefid").getValue();
                    var operateType = 'yes';
                    if(countIndex < rows.length){
                    	singleApproveFlowYes(resid,id,countIndex,tempid,flowdefid,operateType);
                    }
                }
            } else {
                alert(confirmMessages);
            }
   	}
   	
   	
   	//流程审批同意、拒绝、退回
function singleApproveFlowYes(resid,id,countIndex,tempid,flowdefid,operateType){
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
 	
   	function juedingshu(menuid){
   		var row=grid.getSelected();//flowdraftid
   		var win=mini.open({
                  url:"sjJuedingshuPage.action?1=1&crimid="+row.crimid+"&flowdraftid="+row.flowdefid+"&resid="+menuid+"&tempid=10428",
                  showMaxButton: true,
                  allowResize: true, 
                  title: "决定书", width: "300", height: "450",
                  onload: function () {
                      var iframe = this.getIFrameEl();
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
      		});
            
	        win.max();
   	}
    </script>
</body>
</html>

        