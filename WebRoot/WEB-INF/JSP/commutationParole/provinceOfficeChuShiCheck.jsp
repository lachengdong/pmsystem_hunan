<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
   		
		<title>省局处室经办审核</title>
		<script>
			var confirmMessage = "请选中一条记录！";
     		var confirmMessages = "请至少选中一条记录！";
				
			function chooseAll(menuid){
		        	var rows = datagrid.getSelecteds();
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
		                        ids.push(row.crimid+"@"+row.jailid+"@"+row.flowdraftid+"@"+flowid+"@"+row.lastnodeid);
		                    }
		                    var id = ids.join(',');
		                    var flowdefid = mini.get("flowdefid").getValue();
		                    var tempid = mini.get("tempid").getValue();
		                    var url = "toProvinceCommuteTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
		                    self.location.href=url;
		                }
		            } else {
		                alert( "请至少选中一条记录！");
		            }
			}
			
			function chooseOne(menuid) {
					var row = datagrid.getSelected();
					var crimid = row.crimid;
					var orgid = row.jailid;
					var flowdraftid = row.flowdraftid;
					var flowid = row.flowid;
					var lastnodeid = row.lastnodeid;
		        	var fatherMenuid = mini.get("menuid").getValue();
		        	var tempid = mini.get("tempid").getValue();
		        	var flowdefid = mini.get("flowdefid").getValue();
		        	var closetype = 1;
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
                        	var	result = eval(text);
                        	if(result != '-1'){
        		            	alert("此案件正由"+result+"审批。");
        		            	datagrid.reload();
        		            }else{
        		            	var url = "toProvinceCommuteTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid;
        						self.location.href= url;
            		        }
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
		            });
        	}
			
	</script>
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    .s{
    		width:100%;height:100%;_height:94.8%;
    	} 
    </style>
</head>
<body  onload="init('${menuid}');">
   <div id="div_one" class="s">
       <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
            <table >
               <tr>
                <td style="width:100%;">
                	<a class="mini-button"  style="display:none;" id="10257_03"  iconCls="" plain="true" onclick="chooseAll('10257_05');">批量处理</a> 
                	<!-- <a class="mini-button"  style="display:none;" id="16282"  iconCls="" plain="true" onclick="withdrawCases();">撤销案件</a>
                	<a class="mini-button"  style="display:none;" id="16302"  iconCls="" plain="true" onclick="backCases();">退回案件</a>-->
                </td>
                <td style="white-space:nowrap;">
                	<input id="year" class="mini-combobox" valueField="id" textField="text"  
    						url="getDateUPDownArea.action"   style="width:65px;" value="${year}" onvaluechanged="search();"showNullItem="true" nullItemText="选择年度"/>${shortname} 
    				<input id="casetype" class="mini-combobox" valueField="id" textField="text" data="[{ id: 0, text: '减字' }, { id: 1, text: '假字'} ]" style="width:50px;" value="0" onvaluechanged="oncasetypechanged" />		
                    <input class="mini-textbox" id="casenums" class="mini-textbox" style="width:200px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter" onvaluechanged="search();"/>
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search();">快速查询</a>
                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="onjianyuchanged"/>
                     <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>       
                </td>
               </tr>
			</table>
		 	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    	</div>
	    <div class="mini-fit">
		     <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"   allowSortColumn="true"
		     url="getOfficeShenHeList.action?1=1"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50]" 
		     pageSize="20"  showLoading="true">  
		        <div property="columns">
		        		<div type="checkcolumn" width="20px;"></div> 
		        		<div field="crimid" width="30px"  headerAlign="center"  allowSort="true" align="center" >罪犯编号</div> 
		        		<div field="crimclass" width="20px"  headerAlign="center"  	allowSort="true" align="center" >罪犯类型</div> 
					    <div field="name" width="20px"  headerAlign="center"  	allowSort="true" align="center" >罪犯姓名</div>   
		            	<div field="caseno" width="50px"  headerAlign="center"  	allowSort="true" align="center" >案件号</div>
		            	<div field="causeaction" width="30"  headerAlign="center"  	allowSort="true" align="center" >罪名</div> 
		            	<div field="originalyear" width="30" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
		            	<div field="jailinfo" width="50px"  headerAlign="center"  	allowSort="true" align="center" >监狱意见</div> 
		            	<div field="orgname1" width="30" headerAlign="center" align="center"  allowSort="true">关押单位</div>
		            	<div field="sentencestime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div> 
	                 	<div field="courtchangeto" width="40px"  headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">现刑期止日</div>
	                 	<div name="action" width="40px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
		        </div>
	    	</div>
 		</div>
 	</div>
 <script type="text/javascript">
		 mini.parse();
		 var datagrid = mini.get("datagrid");
		 datagrid.load();
		 //datagrid.sortBy("optime", "desc");
		
	   function onActionRenderer(e) {
	         var s ='';
	         s = document.getElementById('middlebtns').value;
	     	return s;
	  }   
	  
	  function onjianyuchanged(){
        	var year = mini.get("year").getValue();
	  		var jailid = mini.get("jailid").getValue();
	  		var casenums = mini.get("casenums").getValue();
	  		datagrid.load({jailid:jailid, casenums:casenums, year:year});
      }
	   
	  function search(){
	  		var year = mini.get("year").getValue();
	  		var jailid = mini.get("jailid").getValue();
	  		var casenums = mini.get("casenums").getValue();
	  		var casetype = mini.get("casetype").getText();
	  		datagrid.load({jailid:jailid, casenums:casenums, year:year,casetype:casetype});
	  } 
	  function oncasetypechanged(){
        	search();
       }
	  function onKeyEnter(){
	  		search();
	  }
		//撤销案件
		function withdrawCases(){
			var rows = datagrid.getSelecteds();
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
                            datagrid.reload();
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
		    
		//退回案件
		function backCases(){
			var rows = datagrid.getSelecteds();
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
                        url: "backCasesOfJail.action?1=1",
                        type: "post",
                        data: {flowdraftids:id, menuid:menuid, crimids:crimids},
                        success: function (text) {
                        	var result = eval(text);
                        	if(result =="success"){
                        		alert("操作成功！");
                        	}else{
                        		alert("操作失败！");
                        	}
                            datagrid.reload();
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
 </script>
</body>
</html>
