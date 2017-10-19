<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html>
<head>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
  	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<title>保外监区经办</title>
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }     
	</style>
    <script type="text/javascript">
     	//快速查询
        function search() {
        	var year = mini.get("year").getValue();
        	var casenums = mini.get("casenums").getValue();
            var key = mini.get("key").getValue();
            var jianqu = mini.get("jianqu").getValue();
            var xing = mini.get("xing").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({jianqu:jianqu,xing:xing,year:year, key: key, casenums:casenums});
        }
        function onKeyEnter(e) {
            search();
        }
		
		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
        	var fatherMenuid = document.getElementById("menuid").value;
        	var closetype=1;
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var row = rows[i];
                        var flowid = row.flowid;
                        if(flowid==undefined){
        					flowid = "flowidnull";
        				}
                        ids.push(row.crimid+"@"+row.areaid+"@"+row.flowdraftid+"@"+flowid+"@"+row.lastnodeid);
                    }
                    var flowdefid = mini.get("flowdefid").getValue();
                    var tempid = mini.get("tempid").getValue();
                    var id = ids.join(',');
                    var url = "toOutPrisonOfJailCaseTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid
                    		+"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
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
        	if(flowid==undefined){
        		flowid = "flowidnull";
        	}
        	
        	var closetype = 1;
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
		            	var url = "toOutPrisonOfJailCaseTabs.action?1=1&tempid="+tempid+"&menuid="+menuid
		            			+"&fathermenuid="+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid;
		    			self.location.href=url;
			        }
               },error: function () {
               	alert("操作失败!");
               }
            });
        }
		
		
		//撤销案件
		function withdrawCases(){
			var rows = grid.getSelecteds();
			var menuid = '';
			var menuidObj = document.getElementById("menuid");
			if(menuidObj) menuid = menuidObj.value;
			
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.crimid+"@"+r.flowdraftid);
                    }
                    var id = ids.join(',');
                    $.ajax({
                        url: "withdrawalCasesOfJail.action?1=1",
                        type: "post",
                        data: {data:id, menuid:menuid},
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
			if(rows.length<=0){
				alert("请至少选择一条记录！");
				return;
			}
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
           	var win=mini.open({
                  url:"toMedicalParoleOfTheMeeting.action?1=1",
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
        }
        }
		// 会议记录 
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
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
        <input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	   <input id="middlebtns" name="middlebtns" class = "mini-hidden"/>
            <table >
               <tr>
                <td style="width:100%;">
                	<a class="mini-button"  style="display:none;" id="12597"  iconCls="" plain="true" onclick="chooseAll('12599');">批量处理</a>
                	<a class="mini-button" style="display:none;" id="12605"  iconCls="" plain="true" onclick="meetingRecord('12605','10100');">监区长办公会</a>
                	
                	<a class="mini-button" style="display:none;" id="802023" defaultValue="宁夏"  iconCls="" plain="true" onclick="meettingRecord_sh('802023','NX_BWJY_FJQHY');">分监区会议</a>
                </td>
                <td style="white-space:nowrap;">
                      <input name="jianqu" id="jianqu" class="mini-combobox" style="width:150px;" valueField="ORGID" textField="NAME"  emptyText="请选择监区"
                            url="getDepartList.action?1=1&qtype=jianqu" nullItemText="请选择监区" required="false" showNullItem="true"/>&nbsp;
                      <input name="xing" id="xing" class="mini-combobox" style="width:120px;" valueField="id" textField="text"  emptyText="请选择刑期类型" showNullItem="true"
                           data="[{ id: 1, text: '有期徒刑' }, { id: 2, text: '无期徒刑'} ,{ id: 3, text: '死刑，缓期两年执行'}]" nullItemText="请选择刑期类型" required="false" />&nbsp;&nbsp;
                
    					<input id="year" class="mini-combobox" valueField="id" textField="text"  showNullItem="true"
    						url="getDateUPDownArea.action"   style="width:65px;" value="${year}"/>${shortname} 保字
                		<input class="mini-textbox" id="casenums" class="mini-textbox" style="width:160px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音"  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
		 		</td>
               </tr>
		</table>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    </div>
    <div class="mini-fit" id="div_two">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="getBaoWaiJianBanRenList.action?1=1&flowdefid=${flowdefid}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50]" pageSize="20"  showLoading="true">
	        <div property="columns">
        		<div type="checkcolumn" width="15px"></div> 
          		<div field="caseno" width="50px"  headerAlign="center" allowSort="true" align="center" >案件号</div> 
       			<div field="crimid" width="30px"  headerAlign="center" allowSort="true" align="center" >罪犯编号</div> 
          		<div field="name" width="30px"  headerAlign="center" allowSort="true" align="center" >罪犯姓名</div>  
          		<div field="age" width="30px"  headerAlign="center" allowSort="true" align="center" >年龄</div>  
				<div field="courtchangeto" width="30px" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer" >刑期止日</div>  
	            <div field="nowpunishmentyear" width="40px" headerAlign="center" align="center"  allowSort="true">现余刑</div>
	            <div field="commenttext" width="40px" headerAlign="center" align="center"  allowSort="true">审批意见</div>
           		<div id="status" width="30px"  headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">状态</div> 
       			<div id="action" width="40px"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
    	mini.parse();
		var grid = mini.get("datagrid");
		grid.load();
		
		function onActionRenderer(e) {
	        var s ='';
	         //s = document.getElementById('middlebtns').value;
			s += " <a class=\"Edit_Button\" href=\"javascript:chooseOne('12599');\" >办案</a>&nbsp;";
			s += " <a class=\"Edit_Button\" href=\"javascript:chooseOne('12598');\" >查看案件</a>&nbsp;";
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
    </script>
</body>
</html>

        