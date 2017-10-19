<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
    <title>案件统计详细页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }   
    </style>
</head>
<body >
	<div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
		<input id="type" class="mini-hidden" value="${type}"/>
		<input id="dataIndex" class="mini-hidden" value="${dataIndex}"/>
		<input id="jylx" class="mini-hidden" value="${jylx}"/>
		<input id="name" class="mini-hidden" value="${name}"/>
		<input id="cityName" class="mini-hidden" value="${cityName}"/>
		<input id="orgid" class="mini-hidden" value="${orgid}"/>
		<input id="queryStartDate" class="mini-hidden" value="${queryStartDate}"/>
		<input id="queryEndDate" class="mini-hidden" value="${queryEndDate}"/>
		<table >
			<tr>
				<td style="width:33%">
				<a class="mini-button" iconCls="icon-download" plain="true" onclick="exportData();">导出到Excel</a>
				</td>
				<td style="width:50%" >
					罪犯编号或罪犯姓名：<input  id="key" class="mini-textbox" width="180px" emptyText="请输入罪犯编号或罪犯姓名" value="" onvaluechanged="reSearch"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="mini-button" onclick="search">查询</a>
				</td>
				
				<td style="width:33%" align="right">
					
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-fit">
		<div id="datagrid1"  class="mini-datagrid"  style="width:100%;height:100%;" url="<%=path%>/statistic/getCaseDetailInfo.json?1=1"
		  showLoading="ture" allowResize="false"  idField="id" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
		  	<div property="columns">
		  		<div type="checkcolumn" width="15px"></div>
		  		<div field="applyid" width="25px;" headerAlign="center"  align="center" allowSort="false" >罪犯编号</div>
		  		 <div field="name" width="20px;" headerAlign="center"  align="center" allowSort="false" >罪犯姓名</div>
		  		 <div field="gender" width="10px;" headerAlign="center"  align="center" allowSort="true"  >性别</div>
		  		 <div field="age" width="10px;" headerAlign="center"  align="center" allowSort="true"  >年龄</div>
		  		 <div field="causeaction" width="30px;" headerAlign="center"  align="center" allowSort="false"  >案由</div>
		  		 <div field="jailname" width="25px;" headerAlign="center"  align="center" allowSort="false" >执行机关</div> 
	            <div field="yuanpan" width="30px;" headerAlign="center"  align="center" allowSort="true" >原判刑期</div> 
	            <div field="inprisondate" width="25px;" headerAlign="center"  align="center" allowSort="true" >入狱时间</div>
	            <div field="" width="25px" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
		  	</div>
		</div>
	</div>
	<div style="height:0;"> <!-- display:none;  height:200px;-->
		<form id="hidden_formid" method="post" url="">
			<input type="hidden" id="hidden_ids" name="ids" value=""/>
			<input type="hidden" id="hidden_furl" name="furl" value=""/>
		</form>
	</div>
	<script type="text/javascript">
	    mini.parse();
	    var grid = mini.get("datagrid1");
	    var path = '<%=path%>';
	    loadGridData();
	    
	    function loadGridData(){
	    	
		    var type=mini.get("type").getValue();
		    var dataIndex=mini.get("dataIndex").getValue();
		    var name=mini.get("name").getValue();
		    var cityName=mini.get("cityName").getValue();
		    var orgid=mini.get("orgid").getValue();
		    var jylx=mini.get("jylx").getValue();
		    var key=mini.get("key").getValue();
			var queryStartDate=mini.get("queryStartDate").getValue();
		 	var queryEndDate=mini.get("queryEndDate").getValue();
		    grid.load({
		    	type:type,
		    	dataIndex:dataIndex,
		    	name:name,
		    	cityName:cityName,
		    	orgid:orgid,
		    	jylx:jylx,
		    	key:key,
		    	queryStartDate:queryStartDate,
		    	queryEndDate:queryEndDate
		    });
	    }
	    function onActionRenderer(e) {
	       	var s = ' <a class="Edit_Button"  href="javascript:toReporTabPage(\'481844\',0);" >查看</a>';
		    return s;
		}

	    /**
	 	    *导出数据
	 	    */
	 	    function exportData(){
		    	if(!confirm("确认导出数据吗？")){
		    		return;
		    	}
	 		  var rows=grid.getSelecteds ();
		    	if(rows.length == 0){
	                alert('请选择案件进行导出!');
	                return;
	           }
	 	    	var fileName="案件信息详情";
	 	    	//表头
	 	    	var headers="罪犯编号,罪犯姓名,性别,年龄,案由,执行机关,原判刑期,入狱时间";
	 	    	//获取表格数据
	 	    	var dataContent=obtainGridContent(rows);
	 	    	//导出excel
	 	    	exportToExcel(fileName,headers,mini.encode(dataContent));
	 	    }
	 	    
	 	    /**
	 	    *导出数据到excel所调用后台方法
	 	    */
	 	    
	 	    function exportToExcel(fileName,headers,dataContent){
	 	    	$.ajax({
	 	    		url:"<%=path%>/excelexp/putValues.action",
	 	    		type:"post",
	 	    		data:{
	 	    			fileName:fileName,
	 	    			headers:headers,
	 	    			data:dataContent
	 	    		},
	 	    		dataType:"json",
	 	    		success:function(status){
	 	    			if(status){
	 	    				window.location.href="<%=path%>/excelexp/exportExcel.action";
	 	    			}else{
	 	    				alert("导出失败！");
	 	    			}
	 	    		},
	 	    		error:function(){
	 	    			alert("操作失败！");
	 	    		}
	 	    		
	 	    	});
	 	    }
	 	    /**
	 	    *获取datagrid内容数据
	 	    */
	 	    
	 	   function  obtainGridContent(rows){
	 		   var datas=[];
	 		   for(var i=0;i<rows.length;i++){
	 			   var row=rows[i];
	 			   var data={};
	 			   data.keya=row.crimid;
	 			   data.keyb=row.name;
	 			   data.keyc=row.gender;
	 			   data.keyd=row.age;
	 			   data.keye=row.causeaction;
	 			   data.keyf=row.jailname;
	 			   data.keyi=row.yuanpan;
	 			   data.keyj=row.inprisondate;
	 			  datas.push(data);
	 			  
	 		   }
	 		   return datas;
	 	   }
	 	    
	 	    
	 	    function reSearch(){
	 	    	loadGridData();
	 	    }
	 	    
	 	    
	 		 //对于案件的打开页面，lock: 判断是否需要加锁，noOperate：打开页面后不充许操作
		 	   function toReporTabPage(menuid,lock, noOperate){
		 	   	if(lock==undefined || lock==null){
		 	   		lock=1;
		 	   	}
		 	   	reportTabs(menuid, lock, noOperate);
		 	   } 
		 	    
		 	  function reportTabs(menuid, lock, noOperate){
		 			var rows = grid.getSelecteds();
		 			if (rows.length > 0){
		 				var idArr = [];
		 				var flowdraftidArr = [];
		 				for(var i = 0, l = rows.length; i < l; i++){
		 					var row = rows[i];
		 					if(row.flowdraftid){
		 						idArr.push("&flowdraftid="+row.flowdraftid);
		 						flowdraftidArr.push(row.flowdraftid);
		 					}else if(row.applyid){
		 						idArr.push("&optype=add&applyid="+row.applyid);//标志为重新查询数据
		 					}
		 				}
		 				
		 				var ids = idArr.join(',');
		 				var flowdraftids = flowdraftidArr.join(',');
		 				
		 				if(rows[0].menuid){
		 					menuid = rows[0].menuid;
		 				}
		 				//用于返回到主页面的url
		 				var furl = encodeURIComponent(location.href);
		 				var url =path+ "/toFatherTabPage.action?menuid="+menuid;
		 				if(noOperate){
		 					url += "&noOperate="+noOperate;
		 				}
		 				standardPost(url,ids,furl);
		 			}else {
		 				alert( "请至少选中一条记录！");
		 			}
		 		}
	    
	    
	</script>
	<script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html>