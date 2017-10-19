<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<title>资源类型管理</title>
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
				<!--<input id="typekey"  class="mini-combobox" valueField="restypeid" textField="text" showNullItem="false" 
	                style="width:150px" emptyText="选择资源类型" data="FloderType" onvaluechanged="typefilter" oncloseclick="onCloseClick"/>-->
	              <input id="textkey" class="mini-textbox" emptyText="输入资源类型名称" style="width:100px;" onenter="onKeyEnter"/>   
                        <a class="mini-button" onclick="search()">查询</a>   
				<a class="mini-button" iconCls="icon-add" onclick="newRow()" plain="true">新增</a>
				 <a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
			</div>
		</div>
    <!-- 显示表格 -->
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="resource/getResouceTypeList.action?1=1" idField="restypeid"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true" sortField="RESTYPEID" sortOrder="asc"
				allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;"
				>
				<div property="columns">
					 <div type="checkcolumn" width="10px"></div>
					<div field="RESTYPEID" width="30" headerAlign="center"
						allowSort="true" align="center" >
						资源类型ID
					</div>
					<div field="NAME" width="50" headerAlign="center"
						allowSort="true" align="center" >
						资源类型名称
					</div>
					<div name="operate" width="30px" headerAlign="center"  align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
					
				</div>
			</div>
		</div>
		<div id="editWindow" class="mini-window" title="编辑资源类型" style="width:600px;height:300px"
		    showModal="true" allowResize="true" allowDrag="true">
		    <div id="editform" class="form" style="min-height: 150px;" >
		    <input id="rid" name="rid" class="mini-hidden"/>
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData()" plain="true" iconCls="icon-save" style="width: 60px">保存</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true" style="width: 60px;">取消</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 11px; padding-bottom: 5px;">
					<table style="table-layout: fixed;">
						<tr>
							<td style="width: 90px;">
								资源类型ID
							</td>
							<td style="width: 150px;">
								<input id="restypeid" style="width: 150px;" name="restypeid" class="mini-textbox"  vtype="maxLength:50" required="true" />
							</td>
							<td style="width: 90px;">
								资源类型名称
							</td>
							<td style="width: 150px;">
								<input id="name" style="width: 150px;" name="name" class="mini-textbox"  vtype="maxLength:50" required="true"/>
							</td>
							
						</tr>
					</table>
				</div>
		   </div>
		</div>
    	<script type="text/javascript">
    	var FloderType="";
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        var editWindow = mini.get("editWindow");
        // 渲染操作菜单
        function onActionRenderer(e) {
	            var grid = e.sender;
	            var record = e.record;
	            var uid =record.RESTYPEID;
	            var rowIndex = e.rowIndex;
	            var gkzxtempid=record.gkzxtempid;
	            var s ="";
		            s='<a class="New_Button" href="javascript:editRow(\'' + uid + '\')">修改</a>&nbsp;&nbsp';
		           // s=s+'<a class="New_Button" href="javascript:delRow(\'' + uid + '\')">删除</a>';
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
		    var row = grid.getSelected ();//getRowByUID(row_uid);
            
            var restypeid = row.RESTYPEID;
            var name=row.NAME;
            editWindow.show();
            var form = new mini.Form("#editform");
            form.rid=mini.get("rid").setValue(restypeid);
            form.restypeid=mini.get("restypeid").setValue(restypeid);
            form.name=mini.get("name").setValue(name);
            form.restypeid=mini.get("restypeid").getValue();
            
              
		};
		function cancelEditWindow() {
			editWindow.hide();
		};
		function saveData(){
			var rid = mini.get("rid").getValue();
			if(rid){
				updateData(rid);
				
				grid.load();
			}else{
				insertData();
				grid.load();
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
                   url: "<%=path %>/resource/updateResouceManage.action?1=1&rid="+rid,
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
                   url: "<%=path %>/resource/insertResouceManage.action?1=1",
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
		function onKeyEnter(e) {
            search();
        }
       
        
        function search() {
            var keytext = mini.get("textkey").getValue();
            grid.load({key: keytext});
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
         function delRow(row_uid) {
          var row = grid.getSelected();
            var restypeid = row.RESTYPEID;
           if (row) {
             if (confirm("确定删除此记录？")) {
                grid.loading("删除中，请稍后......");
                   $.ajax({
                       url: "<%=path %>/resource/deleteResouceById.action?restypeid="+restypeid,
                     success: function (text) {
                     grid.reload();
                      },
                     error: function () {
                      }
                    });
               }
            }
        }
      
    </script>
	</body>
</html>