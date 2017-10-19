<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>借阅审批</title>
    
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>

  </head>
  
  <body onload="init('1600_001_004');">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<table>
				<tr>
					<td  style="width: 30%;">
						<a class="mini-button"  id="10626" iconCls="icon-downgrade" plain="true" onclick="borrowapprove('10626','yes');">批量同意</a>
						<a class="mini-button"  id="10627" iconCls="icon-upgrade" plain="true" onclick="borrowapprove('10627','no');">批量退回</a>
					</td> 
					<td style="width: 70%;" align="center">
						修改查看时间:<input name="starttime1" id="starttime1" class="mini-datepicker" value="javascript: new Date();" style="width:160px;" format="yyyy-MM-dd HH:mm:ss" showTime="true" required="true"/>
						至<input name="endtime1" id="endtime1" class="mini-datepicker" value="javascript: new Date();" style="width:160px;" format="yyyy-MM-dd HH:mm:ss"  showTime="true" required="true" /> 
					</td>
					<td style="white-space: nowrap;" >
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" id="key" style="width:150px;" emptyText="罪犯编号、姓名、档案名称" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('1600_001_004');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/flow/getarchives.action?1=1&flowdefid=arch_zfdajysp"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div type="indexcolumn" width="8" headerAlign="center" align="center">序号</div>
					<div field="applyid" width="20" headerAlign="center" allowSort="true" align="center">罪犯编号</div>
					<div field="applyname" width="15" headerAlign="center" allowSort="true" align="center">姓名</div>
					<div field="opname" width="15" headerAlign="center" align="center" allowSort="true">申请人</div>
					<div field="conent" width="35" headerAlign="center" allowSort="true" align="center">档案名称</div>
					<div field="startsummry" width="40" headerAlign="center" align="center" allowSort="true">开始摘要</div>   
					<!-- 
						<div field="endsummry" width="40" headerAlign="center" align="center" allowSort="true">结束摘要</div>   
						<div field="commenttext" width="25" headerAlign="center" align="center" allowSort="true">审批意见</div>  
					 -->
					<div field="starttime" width="30" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true">查看开始时间</div>
					<div field="endtime" width="30" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true">查看结束时间</div>
					<div field="optime" width="30" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true">提交时间</div>
					<div width="20" headerAlign="center" align="center" allowSort="false" renderer="onCaoZuo">操作</div>  
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			var path = '<%=path %>';
		 	var grid = mini.get("datagrid");
			grid.load();
			grid.sortBy("optime", "desc");
			function search() {
			     var key = mini.get("key").getValue();
			     grid.load({ key: key });
			}
			function onKeyEnter(e) {
			   search();
			}
			//借阅审批  resid 和流程相关的按钮ID snodeid 源节点ID flowdefid 流程自定义ID
			function borrowapprove(resid,approve){
				var start1= mini.get("starttime1").getValue();
				var end1 = mini.get("endtime1").getValue();
				var rows = grid.getSelecteds();
	            if (rows.length > 0) {
	            	var conents = [];
	            	var flowids = [];
	            	var flowdraftids = [];
	            	var applyids = [];
	            	var archiveids = [];
	            	var starttimes = [];
	            	var endtimes = [];
	            	
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        conents.push(r.conent);
                        applyids.push(r.applyid);
                        flowids.push(r.flowid);
                        flowdraftids.push(r.flowdraftid);
                        archiveids.push(r.archiveid);
                        if(start1 !=''){
                        	starttimes.push(mini.formatDate(start1,"yyyy-MM-dd HH:mm:ss"));
                        }else{
                        	starttimes.push(mini.formatDate(r.starttime,"yyyy-MM-dd HH:mm:ss"));
                        }
                        if(end1 !=''){
                        	endtimes.push(mini.formatDate(end1,"yyyy-MM-dd HH:mm:ss"));
                        }else{
                        	endtimes.push(mini.formatDate(r.endtime,"yyyy-MM-dd HH:mm:ss"));
                        }
                    }
                    var conent = conents.join(',');
                    var flowid = flowids.join(',');
                    var flowdraftid = flowdraftids.join(',');
                    var applyid = applyids.join(',');
                    var archiveid = archiveids.join(',');
                    var starttime = starttimes.join(',');
                    var endtime = endtimes.join(',');
	            	$.ajax({
		   	            url:path+'/flow/borrowapprove.action?1=1', 
		   	         	data: {applyids:applyid,conents:conent,resid:resid,flowids:flowid,archiveids:archiveid,flowdefid:'arch_zfdajysp',
		            		approve:approve,starttimes:starttime,endtimes:endtime,flowdraftids:flowdraftid,operateType:approve},
		   	            type: "post",
		   	            cache:false,
		   	            async:false,
		   	            success: function (text){
		   					grid.reload();
		   	            }
		   			});
	            }else{
	            	alert("请至少选中一条数据");
	            }
			}
			function onCaoZuo(){
				var s = document.getElementById("middlebtns").value;	
			    return s;
			}
		</script>
  </body>
</html>