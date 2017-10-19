<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>报表管理</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<!-- 必须指定,否则高度为0 -->
		<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: auto;
		}
		.hide{
			display: none;
		}
		</style>
	</head>
	<body>
		<div class="crud-search">
			<div class="mini-toolbar" style="padding-top: 5px; padding-bottom: 5px;text-align:right;border-bottom:0;">
				<input id="typekey"  class="mini-combobox" valueField="id" textField="text" showNullItem="false" 
	                style="width:150px" emptyText="选择报表类型" data="FloderType" onvaluechanged="typefilter" oncloseclick="onCloseClick"/>
	              <input id="textkey" class="mini-textbox" emptyText="输入报表名称" style="width:100px;" onenter="onKeyEnter"/>   
                        <a class="mini-button" onclick="search()">查询</a>   
				 <a class="mini-button" iconCls="icon-add" onclick="newRow()" plain="true">新增</a>
				 <a class="mini-button" iconCls="icon-remove" onclick="deleteBatch()" plain="true">批量删除</a>
				 <a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
			</div>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/report/getReportList.action?1=1" idField="id"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
				>
				<div property="columns">
					 <div type="checkcolumn" width="10px"></div>
					<div field="NAME" width="50" headerAlign="center"
						allowSort="true" align="center" >
						 报表名称
					</div>
					<div field="TYPE" width="50" headerAlign="center"
						allowSort="true" align="center" renderer="onReportTypeRenderer">
						报表类型
					</div>
					<div field="PLANNAME" width="50" headerAlign="center"
						allowSort="true" align="center" >
						查询方案
					</div>
					<div field="RESOURCENAME" width="50" allowSort="true" headerAlign="center" align="center" >
						资源名称
					</div>
					<div field="SN" width="20" headerAlign="center" allowSort="true" align="center" >
						排序号
					</div>
					<div field="REMARK" width="40px" headerAlign="center" allowSort="true" align="center" cellStyle="padding:0;">
						备注
					</div>
					<div name="operate" width="30px" headerAlign="center"  align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
					
				</div>
			</div>
		</div>
		<div id="editWindow" class="mini-window" title="编辑报表" style="width:600px;height:300px"
		    showModal="true" allowResize="true" allowDrag="true">
		    <div id="editform" class="form" style="min-height: 150px;" >
		    <input id="rid" name="rid" class="mini-hidden"/>
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData()" plain="true" iconCls="icon-save" style="width: 60px">存盘</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true" style="width: 60px;">取消</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 11px; padding-bottom: 5px;">
					<table style="table-layout: fixed;">
						<tr>
							<td style="width: 90px;">
								报表名称
							</td>
							<td style="width: 150px;">
								<input id="name" style="width: 150px;" name="name" class="mini-textbox"  vtype="maxLength:50" required="true"/>
							</td>
							<td style="width: 90px;">
								报表类型
							</td>
							<td style="width: 150px;">  
								<!-- <input id="type" name="type" style="width: 150px;" class="mini-textbox"  required="true"/> -->
							    <input id="type"  name="type" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                            style="width:150px" emptyText="选择报表类型" data="FloderType" />
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								查询方案
							</td>
							<td style="width: 150px;">
								<input id="planid" name="planid" class="mini-treeselect" url="ajaxShecmeAll.action?1=1" multiSelect="false"  valueFromSelect="false"
							        textField="name" valueField="planid" parentField="pplanid"  allowInput="false"  style="width: 150px;" 
							    	  showClose="true"  oncloseclick="onCloseClick"  popupWidth="240" 
							    />
							</td>
							<td style="width: 90px;">
								对应资源
							</td>
							<td style="width: 150px;">
								<input id="resid" name="resid" class="mini-treeselect" url="<%=path %>/resource/ajax/list.json?1=1" multiSelect="false"  valueFromSelect="false"
							        textField="resname" valueField="resid" parentField="presid"  allowInput="false" 
							    	 showClose="true"  oncloseclick="onCloseClick" required="true"  style="width: 150px;" popupWidth="240"  
							    />
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								排序号
							</td>
							<td style="width: 150px;">
								<input id="sn" name="sn" vtype="float;maxLength:5" style="width: 150px;"  required="true"
									class="mini-textbox" />
							</td>
							<td style="width: 90px;">
								备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注
							</td>
							<td style="width: 250px;">
								<input id="remark" name="remark" style="width: 150px;"  class="mini-textbox"/>
							</td>
						</tr>
						<tr>
						    <!--<td style="width: 90px;">
								报表类型
							</td>
							<td style="width: 250px;">
								<input id="casetype"  name="casetype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                            style="width:120px" emptyText="是否签章" data="FloderType" onvaluechanged="onCaseChanged"/>
							</td>  -->
						</tr>
					</table>
				</div>
		   </div>
		</div>
    	<script type="text/javascript">
    	var FloderType = [{ id: 1, text: '减刑假释' },{ id: 2, text: '会议记录' },{ id: 3, text: '建议书'},{ id: 4, text: '监督意见书'},
    	              	{ id: 5, text: '其它报表'},{ id: 6, text: '法院报表'},{ id: 7, text: '保外就医'},{ id: 8, text: '考核奖惩'}];
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        var editWindow = mini.get("editWindow");
        // 渲染操作菜单
        function onActionRenderer(e) {
	            var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
	            var gkzxtempid=record.gkzxtempid;
	            var s ="";
		            s='<a class="New_Button" href="javascript:editRow(\'' + uid + '\')">修改</a>&nbsp;&nbsp';
		            s=s+'<a class="New_Button" href="javascript:delRow(\'' + uid + '\')">删除</a>';
	            return s;
	    };
	    
        
        // 刷新本页面
		function refreshPage(){
			location.reload();
		};
		function rowdblclickfunction(e){
			editRow(e.record._uid);
		};
		function newRow() {            
			editWindow.show();
			var form = new mini.Form("#editform");
            form.clear();
        }
		// 编辑
		function editRow(row_uid) {
		    var row = grid.getRowByUID(row_uid);
            if (row) {
                editWindow.show();
                var form = new mini.Form("#editform");
                form.clear();
                $.ajax({
                    url: "<%=path %>/report/getReportById.action?id=" + row.RID,
                    success: function (text) {
                        form.unmask();
                        var o = mini.decode(text);
                        form.setData(o);
                    },
                    error: function () {
                        form.unmask();
                    }
                });
            }
		};
		function cancelEditWindow() {
			editWindow.hide();
		};
		function saveData(){
			var rid = mini.get("rid").getValue();
			if(rid){
				updateData(rid);
			}else{
				insertData();
			}	
		}
		//更新数据
		function updateData(rid){
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			 var json = mini.encode([data]);
			 $.ajax({
                   url: "<%=path %>/report/updateReportManage.action?1=1&rid="+rid,
                   data: { data: json},
	               cache: false,
	               type:"post",
                   success: function (text) {
                   		editWindow.hide();
                   		//grid.reload();
                   },
                   error: function () {
                       form.unmask();
                   }
               });
		}
		// 插入数据
		function insertData() {
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			 var json = mini.encode([data]);
			 $.ajax({
                   url: "<%=path %>/report/insertReportManage.action?1=1",
                   data: { data: json},
	               cache: false,
	               type:"post",
                   success: function (text) {
                   		editWindow.hide();
                   		//grid.reload();
                   },
                   error: function () {
                       form.unmask();
                   }
               });
		};
        
		 function delRow(row_uid) {
            var row = grid.getRowByUID(row_uid);
            if (row) {
                if (confirm("确定删除此记录？")) {
                    grid.loading("删除中，请稍后......");
                    $.ajax({
                        url: "<%=path %>/report/deleteReportById.action?rid=" + row.RID,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            }
        }
		
		function deleteBatch(){
			 var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.RID);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "<%=path %>/report/deleteBatchReportByIds.action?rids=" +id,
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
		}	
		function onKeyEnter(e) {
            search();
        }
        function search() {
            var textkey = mini.get("textkey").getValue();
            var typekey = mini.get("typekey").getValue();
            grid.load({ textkey: textkey , typekey: typekey });
        }
        function typefilter(){
        	var textkey = mini.get("textkey").getValue();
            var typekey = mini.get("typekey").getValue();
        	grid.load({ textkey: textkey , typekey: typekey});
        }
		function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
        function onReportTypeRenderer(e){
             for (var i = 0, l = FloderType.length; i < l; i++) {
                var g = FloderType[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
    </script>
	</body>
</html>