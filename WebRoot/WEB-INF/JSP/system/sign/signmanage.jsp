<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>签章管理</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"	type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
	    <div class="mini-toolbar" style="padding:2px;border:0;border-bottom:1px solid #99bce8;">
	        <table style="width:100%;">
	            <tr>
	            	<td style="white-space:nowrap;">
		            	源：<input name="tempid" id="tempid" style="width:70%;" class="mini-treeselect" 
		            			url="<%=path %>/sign/getSignByDepart.json?1=1" onvaluechanged="search"  showNullItem="true"
		            		emptyText="源单位、相关表单" nullItemText="源单位、相关表单"  idField="id" textField="text" parentField="parentid" showTreeIcon="true" />
		         		目标：<input name="orgid" id="orgid" style="width:70%;" class="mini-treeselect" url="<%=path %>/org/ajax/list.action?1=1&unitlevel=3" 
		         			emptyText="目标单位" nullItemText="目标单位" textField="name" valueField="orgid" parentField="porgid" showTreeIcon="true"/>
						<a class="mini-button" plain="true" iconCls="icon-add" onclick="create();">拷贝签章方案</a>
		            </td>
		            <td style="width:100%;" align="right">     
						<input class="mini-textbox" vtype="maxLength:30;" emptyText="方案名称、签章类型、说明" id="key" name="key" style="width:150px;" />
						<a class="mini-button" iconCls="icon-search" onclick="search()" plain="true">查询</a>
		                <a class="mini-button" iconCls="icon-add" onclick="add()" plain="true">新增签章方案</a>
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
		            </td>
	            </tr>
	        </table>
	    </div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/sign/ajax/list.json?1=1" idField="signid"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" style="width:100%;height:100%;"
				sortField="signid" sortOrder="asc"
				>
				<div property="columns">
					<div field="name" width="50" headerAlign="center"
						allowSort="true" align="center">
						方案名称
					</div>
					<div field="signtype" width="50"
						align="center" headerAlign="center">
						签章类型
					</div>
					<div field="progress" width="40" headerAlign="center"
						allowSort="true" align="center" >
						签章进程数
					</div>
					<div field="seal" width="40" headerAlign="center"
						allowSort="true" align="center" >
						签章个数
					</div>
					<div field="notation" width="40" headerAlign="center"
						allowSort="true" align="center" >
						批注个数
					</div>
					<div field="content" width="45" headerAlign="center"
						allowSort="true" align="center">
						默认意见
					</div>
					<div field="scheme" width="75" headerAlign="center"
						allowSort="true" align="center">
						方案
					</div>
					<div field="remark" width="40" headerAlign="center"
						allowSort="true" align="center">
						说明
					</div>
					<div field="optime" width="40" headerAlign="center"
					 renderer="onDateRenderer" align="center" allowSort="true">
						操作时间
					</div>
	            	<div name="action" width="60" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
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
    	
		function add(){
			var grid= mini.get("datagrid1");
	    	var myurl="sign/addsign.page?1=1";
            var win=mini.open({
                url: myurl,
                showMaxButton: true,
                allowResize: false, 
                title: "新增签章方案", width: 800, height: 450,
                onload: function () {
                    //var iframe = this.getIFrameEl();
                    //var data = { action: "new"};
                    //iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
		};
		function editRow(){
			var grid= mini.get("datagrid1");
            var row = grid.getSelected();
            if (row) {
            	var myurl="sign/editsign.page?1=1" + "&signid="+row.signid;//+"&signid="+row.signid;
                var win=mini.open({
                    url: myurl,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "编辑", width: 800, height: 450,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        //var data = { action: "edit",signid:row.signid};
                        iframe.contentWindow.SetData(row);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
		};
		function deleteRow() {
        	var grid= mini.get("datagrid1");
            var row = grid.getSelected();
            if (row) {
                if (confirm("确定删除选中记录？")) {
            		var myurl="sign/ajax/delete.json?1=1" + "&signid="+row.signid;
                    grid.loading("操作中，请稍后......");	                    
                    $.ajax({
                        url: myurl,
                        type: "post",
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
        };
		function onActionRenderer(e) {
            var s = '<a class="" href="javascript:editRow()">修改</a>' +'&nbsp;&nbsp;'
            		+'<a class="" href="javascript:deleteRow()">删除</a>'
            return s;
        };
        
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
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        };
        //////////////////////////////////////////////////////
		function search() {
			var tempid = mini.get("tempid").getValue();
		    var key = mini.get("key").getValue();
		    grid.load({ key: key , orgid: tempid});
		};
		// 刷新本页面
		function refreshPage(){
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
        function create(){
        	var tempid=mini.get("tempid").getValue();
        	var tempidname=mini.get("tempid").getText();
        	var orgid=mini.get("orgid").getValue();
        	var orgidname=mini.get("orgid").getText();
        	if(tempid==''||orgid==''){
        		mini.alert("源单位、目标单位不能为空");
        		return ;
        	}
        	if(tempid.substring(0,4)==orgid){
        		mini.alert("同一单位之间签章方案不能进行拷贝");
        		return ;
            }
        	//如果选择部门时获取orgid，选择表单时获取orgid@tempid
        	if(tempid.indexOf("@")!='-1'){//选择表单
        		orgidname = orgidname+"_"+tempidname;
            }
        	var url="<%=path %>/sign/copySignAture.json?1=1";
        	mini.confirm("此操作会覆盖《"+orgidname+"》的相关数据，确认操作？","",function test(action){
        		if(action=='ok'){
		        	$.ajax({
		        		url:url,
		        		data:{fromid:tempid,toid:orgid},
		        		type:"post",
		        		success: function(text){
		        		var temp=eval(text);
		        			if(temp=="success")
		        			mini.alert("操作成功！");
		        			location.reload();
		        			if(temp=="error")
		        			mini.alert("操作失败！单位<"+orgidname+">的签章方案信息不存在！");
		        		},
		        		error: function (jqXHR, textStatus, errorThrown){
		        			mini.alert("操作失败!");
		        		}
		        	});
	        	}	
        	});
        }
        
    </script>
	</body>
</html>