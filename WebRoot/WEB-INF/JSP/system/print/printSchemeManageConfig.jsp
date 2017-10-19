<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>打印方案配置</title>
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
				<a class="mini-button" iconCls="icon-remove" onclick="deleteBatch()" plain="true">批量删除</a>
			</div>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				 idField="id"
				sortField="sn" sortOrder="asc"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
				>
				<div property="columns">
					 <div type="checkcolumn" width="10px"></div>
					<div field="SCHEMENAME" width="50" headerAlign="center"
						allowSort="false" align="center" >
						 方案名称
					</div>
					<div field="CONFID" width="50" allowSort="true" headerAlign="center" align="center" >
						配置ID
					</div>
					<div field="PNUMBER" width="20" headerAlign="center" allowSort="true" align="center" >
						打印数量
					</div>
					<div field="PTYPE" width="20" headerAlign="center" allowSort="true" align="center" renderer="onPtypeRender">
						打印类型
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
		    showModal="true" allowResize="false" allowDrag="true"
		    >
		    <div id="editform" class="form" style="min-height: 150px;" >
		    <input id="psid" name="psid" class="mini-hidden"/>
		    <input id="tconfid" name="tconfid" class="mini-hidden"/>
		     <input id="tconfidtext" name="tconfidtext" class="mini-hidden"/>
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
							 配置类型
							</td>
							<td style="width: 150px;">
								<input id="ptype" name="ptype" class="mini-combobox"  textField="text" valueField="id" emptyText="请选择..."
    							data="PTYPE"  required="true" " onvaluechanged="getBiaoDanOrReportByType" /> 
							</td>
							<td style="width: 90px;">
								表单/报表
							</td>
							<td style="width: 150px;">
								 <input id="confidtext" style="display:none" name="confidtext" class="mini-textbox" required="true"/>
								 <div id="confid"  name="confid" style="display:block" class="mini-combobox" popupWidth="400" textField="NAME" valueField="CONFID"                            
		                           multiSelect="false" required="true" allowInput="true"> 
		                        <div property="columns">
		                          <div header="编号" field="CONFID" width="150"></div>
							      <div header="类型" field="NAME" width="250"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								打印数量
							</td>
							<td style="width: 150px;">
								<input id="pnumber" name="pnumber" class="mini-spinner"  minValue="0" maxValue="9999" required="true"/>
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
		   
    <div class="description">
        <h3>Description</h3>
        <ul>
            <li>【表单/报表】这一栏，新建之后无法修改，请删除后再新增</li>
        </ul>
    </div>
		</div>
    	<script type="text/javascript">
    	var PTYPE = [{ id: '1', text: '表单' }, { id: '2', text: '报表'}, { id: '3', text: '模版报表'}]; 
        mini.parse();
        var grid = mini.get("datagrid1");
        
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
			 mini.get("confid").setEnabled(true);
			var form = new mini.Form("#editform");
			var psid = mini.get("psid").getValue();
            form.clear();
            mini.get("psid").setValue(psid);
        }
		// 编辑
		function editRow(row_uid) {
		    var row = grid.getRowByUID(row_uid);
            if (row) {
                editWindow.show();
                var form = new mini.Form("#editform");
                form.clear();
                $.ajax({
                    url: "<%=path %>/print/getPrintSchemeConfigById.action?psid=" + row.PSID+"&confid="+row.CONFID,
                    success: function (text) {
                        form.unmask();
                        var o = mini.decode(text);
                        form.setData(o);
                        getBiaoDanOrReportByType();
                        mini.get("confidtext").setEnabled(false);
                        mini.get("confid").setEnabled(false);
                        if(o.ptype==3){//模版报表
                        	mini.get("confidtext").setValue(o.confid);
                        	mini.get("tconfidtext").setValue(o.confid);
                        }else{
                        	mini.get("tconfid").setValue(o.confid);
                        }
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
			var tconfid = mini.get("tconfid").getValue();
			var tconfidtext = mini.get("tconfidtext").getValue();
			if(psid && (tconfid||tconfidtext)){
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
                   url: "<%=path %>/print/updatePrintSchemeConfig.action?1=1",
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
                   url: "<%=path %>/print/insertPrintSchemeConfig.action?1=1",
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
                        url: "<%=path %>/print/deletePrintSchemeConfigById.action?psid=" + row.PSID+"&confid="+row.CONFID,
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
                        ids.push(r.PSID+"@"+r.CONFID);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "<%=path %>/print/deleteBatchPrintSchemeConfigByIds.action?ids=" +id,
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
        	mini.open({
                url: "<%=path%>/",
                title: "新增员工", width: 600, height: 360,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
        function SetData(data){
        	var  data = mini.clone(data);
        	mini.get("psid").setValue(data.id);
        	var url = "<%=path %>/print/getPrintSchemeConfigList.action?1=1&psid="+data.id;
        	grid.setUrl(url);
        	grid.load();
        }
        
        function onPtypeRender(e) {
            for (var i = 0, l = PTYPE.length; i < l; i++) {
                var g = PTYPE[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        
         function getBiaoDanOrReportByType(){
        	var ptype = mini.get("ptype").getValue();
        	if(ptype=='3'){
        		document.getElementById("confid").style.display='none';
        		document.getElementById("confidtext").style.display='block';
        	}else{
        		document.getElementById("confidtext").style.display='none';
        		document.getElementById("confid").style.display='block';
        		var url="<%=path %>/print/getBiaoDanOrReportByType.action?ptype="+ptype;
	        	mini.get("confid").load(url);
        	}
        }
    </script>
	</body>
</html>