<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>日志管理</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"			rel="stylesheet" type="text/css" />
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
		<div class="crud-search mini-toolbar" style="border-bottom:0;">
			<div style="padding-top: 5px; padding-bottom: 5px;">
				关键字：<input class="mini-textbox" id="key" class="mini-textbox" emptyText="" onenter="onKeyEnter" />
                                    起始时间:<input id="starttime" name="starttime"  property="editor" format="yyyy-MM-dd" 
                               class="mini-datepicker" style="width:120px;" />
                                    至<input id="endtime" name="endtime" property="editor" format="yyyy-MM-dd" class="mini-datepicker" 
                    	allowInput="true" style="width:120px;" />
				<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查找</a>
               <%
					if(topmap!=null && topmap.size()>0){
						for (String key : topmap.keySet()) {
							String[] arry = topmap.get(key).split("@");
				//<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
		       %>
		       
				<a class="mini-button" iconCls="icon-reload" onclick="<%=arry[1] %>" plain="true"><%=arry[0] %></a>
		       <%
						}
					}
				%>
			</div>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/log/ajax/list.json?1=1" idField="id"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" style="width:100%;height:100%;"
				sortField="optime" sortOrder="desc"
				>
				<div property="columns">
					<div field="optime" width="60" headerAlign="center"
					 renderer="onDateRenderer" align="center" allowSort="true">
						操作时间
					</div>
					<div field="loginmac" width="60" headerAlign="center"
					 align="center" allowSort="true">
						操作地址
					</div>
					<div field="opname" width="35" headerAlign="center" align="center"
						allowSort="true">
						操作人员
					</div>
					<div field="opid" width="35" headerAlign="center" align="center"
						allowSort="true">
						操作帐号
					</div>
					<div field="logtype" width="35" headerAlign="center"
						allowSort="true" align="center" >
						日志类型
					</div>
					<div field="opcontent" width="60" headerAlign="center"
						allowSort="true" align="left" >
						操作内容
					</div>
					<div field="opaction" width="40" headerAlign="center"
						allowSort="true" align="center" >
						操作行为
					</div>
					<div field="status" width="25" renderer="onStatusRenderer"
						align="center" headerAlign="center">
						执行状态
					</div>
					
					<div field="remark" width="60" headerAlign="center"
						allowSort="true" align="center">
						备注
					</div>
				</div>
			</div>
		</div>
    	<script type="text/javascript">
    	// 解析并初始化
        mini.parse();
    	// 获取grig对象
        var grid = mini.get("datagrid1");
    	// 加载数据
        grid.sortBy("optime","desc");
        grid.load();
        ///////////////////////////////////////////////////////
        // 渲染Status
        function onStatusRenderer(e) {
        	var ISMENU_TEXT = [{ id: 1, text: '成功' }, { id: 0, text: '失败'}];
            for (var i = 0, l = ISMENU_TEXT.length; i < l; i++) {
                var g = ISMENU_TEXT[i];
                if (g.id == e.value) return g.text;
            }
            return "成功";
        };
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
        	}
            return "";
        };
        //////////////////////////////////////////////////////
		function search() {
		    var key = mini.get("key").getValue();
		    var starttime = mini.get("starttime").getText();
     		var endtime = mini.get("endtime").getText();
     		if(starttime && endtime && starttime>endtime){
              	alert("起时不能小于止时");
              	return ;
  	    	}
		    grid.load({ key: key,starttime: starttime,endtime: endtime});
		};
		// 刷新本页面
		function refreshPage(){
			//
			location.reload();
		};
		// 输入框,事件绑定,13回车
        $("#key").bind("keyup", function (e) {
        	var KEYCODE_ESC = 27;
        	var KEYCODE_ENTER = 13;
        	//
        	var keyCode = e.keyCode;
        	if(keyCode === KEYCODE_ENTER){
                search();
        	}
        });
        function removelog(){
        	var confirmMessage = null;
        	var starttime = mini.get("starttime").getText();
     		var endtime = mini.get("endtime").getText();
     		if(!starttime && !endtime){
     			alert("请选择起止时间！");
     			return;
     		}else if(starttime && !endtime){
     			confirmMessage = "确定删除"+toString(starttime)+"之后的审计日志？";
  	    	}else if(!starttime && endtime){
     			confirmMessage = "确定删除"+toString(endtime)+"之前的审计日志？";
  	    	}else if(starttime && endtime){
     			if(starttime>endtime){
     				alert("起时不能小于止时！");
                  	return ;
     			}
     			confirmMessage = "确定删除"+toString(starttime)+"至"+toString(endtime)+"之间的审计日志？";
  	    	}
            if (confirm(confirmMessage)) {
                $.ajax({
                    url:"<%=path %>/log/removelog.json?1=1",
                    type: 'POST',
                    data:{starttime : starttime,endtime : endtime},
                    success: function (text) {
                        grid.reload();
                        alert('操作成功!');
                    },
                    error: function () {
                        alert("操作失败!");
                    }
                });
            }
	    };
	    
	    function toString(time){
	    	var year = time.split("-")[0];
			var month = time.split("-")[1];
			var day = time.split("-")[2];
			var title = year+"年";  
		    if(parseInt(month)>9){
		    	title = title+month+"月"; 
		    }else{
		    	title = title+month.replace("0","")+"月";    
		    }
		    if(parseInt(day)>9){
		    	title = title+day+"日"; 
		    }else{
		    	title = title+day.replace("0","")+"日";    
		    }
		    return title;
	    }
    </script>
	</body>
</html>