<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>打印方案管理</title>
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
				<a class="mini-button" iconCls="icon-add" onclick="newRow()" plain="true">新增</a>
				<a class="mini-button" iconCls="icon-edit" onclick="schemeConfig()" plain="true">方案配置</a>
				<a class="mini-button" iconCls="icon-remove" onclick="deleteBatch()" plain="true">批量删除</a>
				 <a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
				 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
			</div>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="<%=path %>/print/getPrintSchemeList.action?1=1" idField="id"
				sortField="sn" sortOrder="asc"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
				>
				<div property="columns">
					 <div type="checkcolumn" width="10px"></div>
					<div field="NAME" width="50" headerAlign="center"
						allowSort="true" align="center" >
						 方案名称
					</div>
					<div field="TYPE" width="50" headerAlign="center" allowSort="true" align="center" renderer="onPrintTypeRenderer"  >
						方案类型
					</div>
					<div field="RESOURCENAME" width="50" allowSort="true" headerAlign="center" align="center"  >
						资源名称
					</div>
					<div field="SN" width="20" headerAlign="center" allowSort="true" align="center" >
						排序号
					</div>
					<div name="operate" width="30px" headerAlign="center"  align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
				</div>
			</div>
		</div>
		<div id="editWindow" class="mini-window" title="编辑角色" style="width:520px;"
		    showModal="true" allowResize="true" allowDrag="true"
		    >
		    <div id="editform" class="form" style="min-height: 150px;" >
		    <input id="psid" name="psid" class="mini-hidden"/>
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData()" plain="true"
									style="width: 60px">保存</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true"
									style="width: 60px;">取消</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 11px; padding-bottom: 5px;">
					<table style="table-layout: fixed;">
						<tr>
							<td style="width: 90px;">
							 方案名称
							</td>
							<td style="width: 150px;">
								<input id="name" name="name" class="mini-textbox"  vtype="maxLength:50" required="true"/>
							</td>
							<td style="width: 90px;">
								方案类型
							</td>
							<td style="width: 150px;">
								<input id="type" name="type" class="mini-combobox"  textField="text" valueField="id" 
								    data="PrintType" required="true" /> 
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								对应资源
							</td>
							<td style="width: 150px;">
								<input id="resid" name="resid" class="mini-treeselect" url="<%=path %>/print/selectResourcesByType.action?1=1" multiSelect="false"  valueFromSelect="false"
							        textField="name" valueField="resid" parentField="presid"  allowInput="false"
							    	 showClose="true"  oncloseclick="onCloseClick" required="true" popupWidth="auto"
							    />
							</td>
							<td style="width: 90px;">
								排序号
							</td>
							<td style="width: 150px;">
								<input id="sn" name="sn" class="mini-spinner"  minValue="0" maxValue="9999" required="true"/>
							</td>
						</tr>
					</table>
				</div>
		   </div>
		</div>
    	<script type="text/javascript">
    	var PrintType = [{ id: 'jyjxjs', text: '监狱减刑假释' },
						 { id: 'jyjwzx', text: '监狱监外执行'},
						 { id: 'jyqita', text: '监狱其他'},
						 { id: 'sjjxjs', text: '省局减刑假释'},
						 { id: 'sjjwzx', text: '省局监外执行'},
						 { id: 'sjqita', text: '省局其他'},
						 { id: 'fyjxjs', text: '法院减刑假释'},
						 { id: 'fyqita', text: '法院其他'},
						 { id: 'qita', text: '其他'}
						 ];
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
                    url: "<%=path %>/print/getPrintSchemeById.action?id=" + row.PSID,
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
			var psid = mini.get("psid").getValue();
			if(psid){
				updateData(psid);
			}else{
				insertData();
			}	
		}
		//更新数据
		function updateData(psid){
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			 var json = mini.encode([data]);
			 $.ajax({
                   url: "<%=path %>/print/updatePrintScheme.action?1=1",
                   data: { data: json},
	               cache: false,
	               type:"post",
                   success: function (text) {
                   		editWindow.hide();
                   		grid.reload();
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
                   url: "<%=path %>/print/insertPrintScheme.action?1=1",
                   data: { data: json},
	               cache: false,
	               type:"post",
                   success: function (text) {
                   		editWindow.hide();
                   		grid.reload();
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
                        url: "<%=path %>/print/deletePrintSchemeById.action?id=" + row.PSID,
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
                        ids.push(r.PSID);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "<%=path %>/print/deleteBatchPrintSchemeByIds.action?ids=" +id,
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
		
		function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
        
        function schemeConfig(){
        	var row = grid.getSelected();
        	if(row){
	        	mini.open({
	                url: "<%=path%>/print/toPrintSchemeManageConfigPage.action?1=1",
	                title: "方案配置", width: 900, height: 550,
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { id: row.PSID};
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action) {
	                    grid.reload();
	                }
	            });
            }else{
            	alert("请选中一条记录");
            }
        }
        function onPrintTypeRenderer(e) {
            for (var i = 0, l = PrintType.length; i < l; i++) {
                var g = PrintType[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
    </script>
	</body>
</html>