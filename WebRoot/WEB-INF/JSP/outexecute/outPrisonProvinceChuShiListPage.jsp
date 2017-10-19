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
	<title>省局保外办案审核</title>
	<script>
		function chooseAll(menuid){
        	var rows = datagrid.getSelecteds();
        	var fatherMenuid = mini.get("menuid").getValue();
        	var closetype=1;
            if (rows.length > 0) {
                if (confirm(allProcessing)) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var row = rows[i];
                        var flowid = row.flowid; if(!flowid)flowid="";
                        ids.push(row.crimid+"@"+row.jailid+"@"+row.flowdraftid+"@"+flowid+"@"+row.lastnodeid);
                    }
                    var id = ids.join(',');
                    var flowdefid = mini.get("flowdefid").getValue();
                    var tempid = mini.get("tempid").getValue();
                    var url = "toProvinceOutPrisonTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid
                   			 +"&fathermenuid="+fatherMenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
                    self.location.href=url;
                }
            } else {
                alert(confirmMessages );
            }
		}
			
		function chooseOne(menuid) {
			var row = datagrid.getSelected();
			if(row){
				var crimid = row.crimid;
				var orgid = row.jailid;
				var flowdraftid = row.flowdraftid;
				var flowid = row.flowid;  if(!row.flowid)flowid="";
				var lastnodeid = row.lastnodeid;
	        	var fatherMenuid = mini.get("menuid").getValue();
	        	var tempid = mini.get("tempid").getValue();
	        	var flowdefid = mini.get("flowdefid").getValue();
	        	var closetype = 1;
	        	var id = crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid;
	        	$.ajax({
				   url: "flow/ajaxReturnLockUser.json?1=1",
	               type: "post",
	               data: {flowdraftid:flowdraftid},
	               async: false,
	               success: function (text) {
		               	var	result = eval(text);
		               	if(result != '-1'){
			            	alert("此案件正由"+result+"审批。");
			            	grid.reload();
			            }else{
			            	var url = "toProvinceOutPrisonTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="
			            			+fatherMenuid+"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid;
							self.location.href=url;
		   		        }
	               },
	               error: function () {
	               		alert("操作失败!");
	               }
            	});
			}else{
				alert(confirmMessage);
			}
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
	                	<a class="mini-button"  style="display:none;" id="10121_02"  iconCls="" plain="true" onclick="chooseAll('10121_05');">批量处理</a> 
	                </td>
	                <td style="white-space:nowrap;">
	                	<input id="year" class="mini-combobox" valueField="id" textField="text"  
	    						url="getDateUPDownArea.action"   style="width:65px;" value="${year}" showNullItem="true" nullItemText="选择年度"/>${shortname} ${casetype }
	                    <input class="mini-textbox" id="casenums" class="mini-textbox" style="width:200px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
	                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search();">查询</a>
	                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
	                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="onjianyuchanged"/>
	                       <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>     
	                </td>
	               </tr>
			</table>
			 <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	    </div>
	    <div class="mini-fit">
		    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
		    	 url="getOutPrisonChuShiListDate.json?1=1&flowdefid=${flowdefid}" idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50]" pageSize="20"  showLoading="true">
		        <div property="columns">
	        		 <div type="checkcolumn" width="10"></div> 
	          		 <div field="caseno" width="40px" headerAlign="center" allowSort="true" align="center" >案件号</div>  
	       			 <div field="crimid" width="40px" headerAlign="center" allowSort="true" align="center" >罪犯编号</div> 
	          		 <div field="name" width="40px" headerAlign="center" allowSort="true" align="center" >罪犯姓名</div>  
	          		 <div field="age" width="40px" headerAlign="center" allowSort="true" align="center" >年龄</div>  
			         <div field="maincase" width="40px" headerAlign="center" allowSort="false" align="center" >罪名</div>  
			         <div field="jailname" width="40px" headerAlign="center" allowSort="false" align="center" >所在监狱</div>  
			         <div field="shenpiyj" width="40px" headerAlign="center" allowSort="false" align="center" >审批意见</div>  
	               	 <div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
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
        	search();
      }
	   
	  function search(){
	  		var year = mini.get("year").getValue();
	  		var jailid = mini.get("jailid").getValue();
	  		var casenums = mini.get("casenums").getValue();
	  		datagrid.load({jailid:jailid, casenums:casenums, year:year});
	  } 
	  
	  function onKeyEnter(){
	  		search();
	  }
 </script>
</body>
</html>
