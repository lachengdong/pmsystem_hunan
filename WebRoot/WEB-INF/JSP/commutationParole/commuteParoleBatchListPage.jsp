<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
	String provincecode = (String)request.getAttribute("provincecode");
%>
<html>
	<head>
		<title>批次设置</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=path%>/scripts/loadaip2.js"></script>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js"  type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
	</head>
	<body>
	    <input id="ningxia" name="ningxia" type="hidden" value="${ningxia }"/>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="white-space: nowrap;">
						<%
							if(topmap!=null && topmap.size()>0){
								for (String key : topmap.keySet()) {
								    String[] arr = topmap.get(key).split("@");
								    String[] icoarr = key.split(",");
								    String ico="";
								    if(icoarr.length==2) ico = icoarr[1];
				       	%>
				       				<a class="mini-button" iconCls="<%=ico%>" plain="true" onclick="<%=arr[1] %>"><%=arr[0] %></a>
				       	<%
								}
							}
						%>
						<!-- 宁夏生成数据 -->
						<c:if test="${ningxia eq 1}">
						      <a class="mini-button" iconCls="icon-add" plain="true" onclick="shengChengData()">生成数据</a>
						</c:if>
					</td>
					<td style="width: 100%;">
					</td>
					<td style="white-space: nowrap;">
						<input class="mini-textbox" id="key" class="mini-textbox"
							emptyText="输入年份" onenter="onKeyEnter" />
						<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width: 100%; height: 100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
			allowResize="false" url="<%=path%>/commutationParole/getCommuteParoleBatchList.json?1=1" idField="batchid" multiSelect="true" allowAlternating="true" showLoading="true">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="curyear" width="40px" headerAlign="center" allowSort="true" align="center">
						年度
					</div>
					<div field="batch" width="40px" headerAlign="center" allowSort="true" align="center">
						批次
					</div>
					<div field="examineend" width="40px" headerAlign="center" allowSort="true" renderer="onDateRenderer" align="center">
						考核止日
					</div>
					<div field="departid" visible="false"  headerAlign="center" allowSort="true" align="center" >
	               		单位
	               	</div> 
					<div field="remainderpunishment" width="40px" headerAlign="center" allowSort="true" renderer="onDateRenderer" align="center">
						<%
							if("6100".equals(provincecode)){
						%>
							刑期及减刑止日
						<%
							}
						%>
						<%
							if(! "6100".equals(provincecode)){
						%>
							余刑起算日
						<%
							}
						%>
					</div>
					<div name="action" width="40px" headerAlign="center" allowSort="true" align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	mini.parse();
	var percent = "-1";
    var grid = mini.get("datagrid");
    grid.load();
    grid.sortBy("curyear","desc");

    function shengChengData(){
        $.ajax({
            url:'<%=path%>/commutationParole/shengChengData.action?1=1',
            type:'post',
            success:function (text){
                if(text == 1){
                    alert('生成成功!');
                }else{
                    alert('生成失败!请联系管理员!');
                }
            }
        });
    }
    // 渲染日期
    function onDateRenderer(e) {
    	if(e && e.value){
    		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
    	}
        return "";
    }
    
    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var batchid = record.batchid;
    	var s = '';
        <%
		if(middlemap!=null && middlemap.size()>0){
			for (String key : middlemap.keySet()) {
		   		String[] arry = middlemap.get(key).split("@");
        %>
      		s +=" <a class=\"Edit_Button\" href=\"javascript:<%=arry[1] %>\" ><%=arry[0] %></a>";
        <%
			}
		}
		%>
     	return s;
    }
    
    function add() {
    	mini.open({
	        url: "commutationParole/toCommuteParoleBatchAddPage.page?1=1",
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 500, height: 330,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = { operatetype: "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	
   	function edit() {
        var row = grid.getSelected();
        var batchid = row.batchid;
        if (row) {
            var win=mini.open({
                url: "commutationParole/toCommuteParoleBatchAddPage.page?id="+batchid,
                showMaxButton: true,
                allowResize: false, 
                title: "编辑", width: 500, height: 330,
                onload: function () {
		             	var iframe = this.getIFrameEl();
			            var data = { operatetype: "edit"};
			            iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    	grid.reload();
                }
            });
        } else {
            alert("请选中一条记录");
        }
  	}
  	
  	function remove() {
       var row = grid.getSelected();
       if (row) {
           if (confirm("确定删除选中记录？")) {
               $.ajax({
                   url: "<%=path%>/commutationParole/deleteCommuteParoleBatch.json?id="+row.batchid,
                   dataType:"text",
                   success: function (text) {
                	   alert("操作成功!");
                       grid.reload();
                   },
                   error: function () {
                  		alert("操作失败!");
                  		return;
                   }
               });
           }
       } else {
           alert("请选中一条记录");
       }
  	}
  	
  	function onPageChanged() {
     	myloading();
    }
    
    function progress() {
    	var func = function myloading(){
   			var myform = new mini.Form("datagrid");
        		$.ajax({
            		url: "<%=path%>/commutationParole/getPercent.json?1=1",
            		dataType:"text",
                	success: function (text) {
						percent = eval(text);
                	},
                	error: function () {
                		myunmask();
                		return;
                	}
            	}); 
            if(percent=="-1") {
            	myform.loading("处理中请稍后..");
            } else {
				myform.loading("<div><div style=\"float:left;text-align:center; line-height:25px;\">生成进度：</div><div><div style=\"float:left;text-align:center; border:1px solid #0066FF; width:400px; height:25px;\"><div style=\"float:left; width:" + percent + "%; height:25px; background-color:#5DA8CF\">"+percent+"%</div></div></div>");
	   			if(percent=='100.0') {
	        		$.ajax({
	            		url: "<%=path%>/commutationParole/getPercent.json?1=1&type=remove",
	            		dataType:"text",
	                	success: function (text) {
							percent = eval(text);
	                	},
	                	error: function () {
	
	                	}
	            	}); 
					clearInterval(progress);
					var timefunc = function() {progressunmask();};
					var timer_timeout = setTimeout(timefunc, 2000);
				}            
            }
   		}
   		var progress = window.setInterval(func,500);
   	}
   	
	function unmask(){
		myunmask();
	}
   	function myunmask() {
   		var myform = new mini.Form("datagrid");
  		myform.unmask();
   	}

   	function progressunmask() {
   		var myform = new mini.Form("datagrid");
  		myform.unmask();
  		alert("操作完成");
  		grid.reload();
   	}        	
  	function generateDate(){
  		var row = grid.getSelected();
  		if (row) {
  			if (confirm("确定生成资格筛查数据？")) {
  				progress();
            	$.ajax({
                	url: "<%=path%>/commutationParole/generateCommuteDateAfterBatchSet.json?1=1&id="+row.batchid,
                	dataType:"text",
                	success: function (text) {
                    //var result = eval(text);
               	 },
                	error: function () {
               			alert("操作失败");
               			myunmask();
               			return;
                	}
            	});
        	}
  		}else {
            alert("请选中一条记录");
        }
  	}
  	
    function view() {
         var row = grid.getSelected();
         if (row) {
             var win=mini.open({
                 url: "commutationParole/toCommuteParoleBatchAddPage.page?id="+row.batchid,
                 showMaxButton: true,
                 allowResize: false, 
                 title: "查看", width: 500, height: 330,
                 onload: function () {
           		var iframe = this.getIFrameEl();
	            var data = { operatetype: "view"};
	            iframe.contentWindow.SetData(data);
                 },
                 ondestroy: function (action) {
                 }
             });
         }
    }
        
    function search() {
         var key = mini.get("key").getValue();
         grid.load({ key: key });
    }
      
    function onKeyEnter(e) {
         search();
    }
</script>
