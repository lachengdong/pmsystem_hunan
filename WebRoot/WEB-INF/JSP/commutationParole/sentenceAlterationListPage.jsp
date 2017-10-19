<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.model.system.SystemResource"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();
	
	String topsearch = "";
	Object topsearchObj = request.getAttribute("topsearch");
	if(null != topsearchObj){
		topsearch = topsearchObj.toString();
	}
	
	String middlestr = "";
	Object middlestrObj = request.getAttribute("middlestr");
	if(null != middlestrObj){
		middlestr = middlestrObj.toString();
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>资格筛查</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
	<script type="text/javascript">
		var c = "请选中一条记录！";
		var confirmMessages = "请至少选中一条记录！";
		var batchAlertOne = "您要对以下所选服刑人员进行减刑立案吗?";
		var batchAlertTwo = "您要对以下所选服刑人员进行假释立案吗?";
	</script>
	
</head>
	<body onload="init(${menuid})">
		<div class="mini-toolbar" style="padding: 0px; border-bottom: 0px;">
			<input id="provincecode" name="provincecode" class="mini-hidden" value="${provincecode}"/>	
			
			<table >
	             <tr>
	                <td style="width:100%;">
	                	${topstr}
	                </td>
	                <td style="white-space:nowrap;"> 
	                	${topsearch}
	                	<%
	                		if(StringNumberUtil.notEmpty(topsearch)){
	                	%>
	                			<a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
	                	<%
	                		}
	                	%>
	                	<!-- 操作说明 -->
		                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
	                 </td>
	             </tr>
	        </table>
		</div>
		
		<div class="mini-fit" id="div_two">
			<div id="datagrid1" class="mini-datagrid"
				style="width: 100%; height: 100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20" 
				url="ajaxGetSentenceAlterationList.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false">
				<div property="columns">
					<div type="checkcolumn" width="30"></div>
					<div field="crimid" width="60" headerAlign="center" align="center" allowSort="true" renderer="onCrimidRenderer">罪犯编号</div>
					<div field="name" width="40" headerAlign="center" align="left" allowSort="true">姓名</div>
					<div field="crimage" width="25" headerAlign="center" align="center" allowSort="true">年龄</div>
					<!--<div field="brithday" width="40" headerAlign="center" renderer="onDateRenderer" align="center" allowSort="true">出生日期</div>-->
					<div field="original" width="40" headerAlign="center" align="left" allowSort="true">原判刑期</div>
					<div field="sentencestime" width="55" headerAlign="center" renderer="onDateRenderer" align="center" allowSort="true">原判刑期起日</div>
					<!--<div field="sentenceetime" width="40" headerAlign="center" renderer="onDateRenderer" align="center" allowSort="true">原判刑期止日</div>-->
					<div field="xianxingqizhiristr" width="50" headerAlign="center" renderer="onDateRenderer" align="center" allowSort="true">现刑期止日</div>
					<div field="execdate" width="50" headerAlign="center" renderer="onDateRenderer" align="center" allowSort="true">执行日期</div>
					<div field="predate" width="55" headerAlign="center" renderer="onDateRenderer" align="center" allowSort="true">上次裁减日期</div>
					<div field="punishment" width="40" headerAlign="center" align="center" allowSort="true">裁判余刑</div>
					<div field="jianggeqi" width="50" headerAlign="center" align="center" allowSort="true">减刑间隔期</div>
					<div field="nowpunishmentyear" width="50" headerAlign="center" align="left" allowSort="true">现余刑</div>		 
					<!--<div field="jianxinfudu" width="40" headerAlign="center" align="center" allowSort="true">建议幅度</div>-->
					<div id="caozuo1" width="100" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">
						操作
					</div>
				</div>
			</div>
		</div>
  
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.sortBy("crimid","desc");
		grid.load();
		
		function onActionRenderer(e) {
			var shenbao = mini.get("shenbao").getChecked();
			var s = '';
			if (shenbao) {
				s = '已申报';
			} else {
				s += '${middlestr}';
				//s = document.getElementById('middlebtns').value;
			}
			return s;
		}
		

		function onCrimidRenderer(e) {
			var grid = e.sender;
			var record = e.record;
			var uid = record._uid;
			var rowIndex = e.rowIndex;
			var s = '<a class="Edit_Button" href="javascript:chooseOne(7);" >' + record.crimid + '</a>&nbsp;&nbsp;';
			return s;
		}

		
		function apply() {
			var isstatusval="";
			var isstatus = mini.get("isstatus");
			if(isstatus) {
				isstatusval=mini.get("isstatus").getValue();
				if(isstatusval=="true"){
					isstatusval = "1";
				} else {
					isstatusval = "0";
				}
			}
			
			//var sel = mini.get("sel").getValue();
			var sel = 0;
			if (sel == 1) {
				alert("查询状态，不能操作");
				return;
			} else {
		
				var rows = grid.getSelecteds();
				if (rows.length > 0) {
					var ids = [];
					for ( var i = 0, l = rows.length; i < l; i++) {
						var r = rows[i];
						ids.push(r.crimid);
					}
					var id = ids.join(',');
					$.ajax( {
						url : "<%=path%>/commutationParole/piliangshenbao.action?1=1&crimids="+ id + "&isstatus=" + isstatusval,
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
		}
		
		
		function applyOne() {  
			var provincecode = mini.get("provincecode").getValue();
			if(provincecode=='3100'){//如果省份是上海，须先填写罪犯的案件性质才能申报
				toCaseNature();
			}else{
				declaration();
			}
		}
		
		
		//申报方法
		function declaration(){
			var isstatusval="";
			var isstatus = mini.get("isstatus");
			if(isstatus) {
				isstatusval=mini.get("isstatus").getValue();
				if(isstatusval=="true"){
					isstatusval = "1";
				} else {
					isstatusval = "0";
				}
			}
		//var sel = mini.get("sel").getValue();
			var row = grid.getSelected();
			var crimid = row.crimid;
			$.ajax( {
				url : "<%=path%>/commutationParole/jianxingshenbao.action?1=1&crimid="
						+ crimid + "&isstatus=" + isstatusval,
				success : function(text) {
					grid.reload();
				},
				error : function() {
					alert("操作失败");
					return;
				}
			});
		}
		
		
		function commutationaccord() {
			var row = grid.getSelected();
			var url = "<%=path%>/commutationParole/commutationaccord.action?1=1&crimid="
					+ row.crimid;
			var win = mini.open( {
				url : url,
				title : "筛查条件",
				width : 501,
				height : 270,
				showMaxButton : true,
				allowResize : false,
				onload : function() {
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
		}
		
		
		//根据罪犯编号跳转到基本信息表单页面
		function chooseOne(menuid) {
			var provincecode = mini.get("provincecode").getValue();
			if(provincecode=='1300'|| provincecode=='4500'|| provincecode=='6100'){
				toCriminalBaseInfo();
			}else{
				var row = grid.getSelected();
				var url = "<%=path%>/basicInfo/basicInformation.page?1=1&crimid="
					+ row.crimid + "&menuid=" + menuid + "&closetype=3";
				var win = mini.open( {
					url : url,
					title : "基本信息",
					width : 900,
					height : 550,
					showMaxButton : true,
					allowResize : false,
					onload : function() {
			
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			}
		}
		
		
		//根据罪犯编号跳转到基本信息表单页面
		function toCriminalBaseInfo(){
			var row = grid.getSelected();
			var crimid = '';
			if(row.crimid){
				crimid = row.crimid;
			}else if(row.applyid){
				crimid = row.applyid;
			}
			var ids = crimid+"@703024";
			var url = '<%=path%>'+"/toFatherTabPage.action?1=1&ids="+ids + "&menuid=" + 703024 + "&optype=add";
			var win = mini.open( {
				url : url,
				title : "基本信息",
				width : 900,
				height : 550,
				showMaxButton : true,
				allowResize : false,
				onload : function() {
					
				},
				ondestroy : function(action) {
					//grid.reload();
				}
			});
			win.max();
		}


		function toApplyPage() {
			var row = grid.getSelected();
			if (row) {
				var criminalid = row.crimid;
				var win = mini.open( {
						url : "<%=path%>/commutationParole/sentenceCommutationparolelist.action?criminalid="
								+ criminalid + "&tempid=SZJY_ZFJXJSSBB",
						title : "罪犯申报表",
						width : 900,
						height : 550,
						showMaxButton : true,
						allowResize : false,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {
								mymenuid : "8328",
								myoperatetype : 'view'
							};
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							grid.reload();
						}
					});
		
			} else {
				alert("请选中一条记录");
			}
		}

		//快速查询
		function search() {
			grid.setUrl("<%=path%>/commutationParole/ajaxGetSentenceAlterationList.action?1=1");
			var shenbao = mini.get("shenbao").getChecked(); //申报
			var apply =0;//申报的标志(默认未申报)
			if(shenbao){
            	apply = 1;
             }
			
		    var banli = mini.get("banli").getChecked(); //特案办理
		    var cstatus =1; //特案办理(默认为不需要特案办理的)
	        if(banli){
	    	    cstatus = 0;
	        }
            
			var key = mini.get("key").getValue();
			var schemeid = mini.get("schemeid").getValue(); //资格筛查
			var crimtype = mini.get("crimtype").getValue(); //crimtype
			//var status = mini.get("status").getValue(); //羁押状态
			grid.load( {key : key,schemeid : schemeid, apply:apply, cstatus : cstatus, crimtype:crimtype, status:status});
		}
		
		
		function onKeyEnter(e) {
			search();
		}
		function onValueChanged(e) {
			search();
		}
		
		//罪犯性质编辑
		function toCaseNature() {
		    var row = grid.getSelected();
		    var ptf = mini.get("ptf");
		    document.getElementById("crimid").value=row.crimid;
		    if (row) { 
		        var crimtype = row.crimtype;  
		        var index = crimtype.indexOf("三类犯");
		        var typevalue = 1;
		        if(index>=0){
		        	typevalue = 2;
		        }
		        ptf.setValue(typevalue);
		        editWindow.show();
		        $.ajax({
		            url: "<%=path%>/commutationParole/loadDataGrid.action?1=1&crimid=" + row.crimid,
		            success: function (text) {
		                var o = mini.decode(text);
		       		   	mini.get("codeid").setValue(o);
		            },
		            error: function () { 
		                //form.unmask();
		            }
		        });
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
				// data: { menuid:row.resid,anjianhao:anjianhao,
				// caseyear:caseyear,tempid:row.planid,flag:"flag"},
				allowResize : true,
				title : "预览",
			});
			win.max();
		}
		
		// 渲染日期
	    function onDateRenderer(e){
	    	if(e && e.value){
	    	return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	    	}
	        return e.value;
	    }
		//申报时填写罪犯申请幅度
	    function toFudu(){
	    	var row = grid.getSelected();
	    	var isstatusval="";
	    	var isstatus = mini.get("isstatus");
	    	if(isstatus) {
	    		isstatusval=mini.get("isstatus").getValue();
	    		if(isstatusval=="true"){
	    			isstatusval = "1";
	    		} else {
	    			isstatusval = "0";
	    		}
	    	}
	    	//var sel = mini.get("sel").getValue();
	    	mini.open({
	            url :"<%=path%>/commutationParole/shenqingpage.page?1=1&crimid="+row.crimid+"&isstatus="+isstatusval,
	            title: "申请幅度", width: 500, height: 300, 
	            showMaxButton: true,
	            allowResize: false, 
	            onload: function () { 
	                var iframe = this.getIFrameEl();
	                var data = {};
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (action) {
	            	grid.reload();
	            }
	        });
	    	
	    }
		//批量申报时填写罪犯申请幅度
		function shenqingfudu(){
			var sel =grid.getSelecteds();
			var isstatusval="";
			var isstatus = mini.get("isstatus");
			if(isstatus) {
				isstatusval=mini.get("isstatus").getValue();
				if(isstatusval=="true"){
					isstatusval = "1";
				} else {
					isstatusval = "0";
				}
			}
			if(sel.length>0){
				if(confirm("确定批量操作吗?")){
					var ids=[];
					var batchid=''
					for(var i=0;i<sel.length;i++){
						var r=sel[i];
						ids.push(r.crimid)
						batchid=r.batchid;
					}
					var crimids =ids.join(',');
					mini.open({
		                  url :"<%=path%>/commutationParole/zfsqfdListNew.page?crimids="+crimids+"&batchid="+batchid+ "&isstatus=" + isstatusval,
		                  title: "罪犯信息", width: 900, height: 550,
		                  showMaxButton: true,
		                  allowResize: false, 
		                  onload: function () { 
		                      var iframe = this.getIFrameEl();
		                      var data = {};
		                      iframe.contentWindow.SetData(data);
		                  },
		                  ondestroy: function (action) {
		                	  grid.load();
		                  }
		              });
				}	
			}else{
				alert("请至少选中一条记录！");
			}
		}
		function jifenkaohe_hunan(menuid,name){
			var row = grid.getSelected();
			var crimid = row.crimid;
			var myurl = "<%=path%>/baseReportOperate.page?1=1&optype=add&doctype="+name+"&applyid="+crimid+"&menuid="+menuid;
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
	</script>
</body>
</html>