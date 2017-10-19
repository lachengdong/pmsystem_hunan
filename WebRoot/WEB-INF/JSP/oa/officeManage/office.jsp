<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>办公用品领用记录</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
}

label {
	float: left;
	font-weight: normal;
	width: 85px;
	text-align: right;
	margin-right: 10px;
}
</style>

</head>
<body>
        <input id="schemeid" name="schemeid" class="mini-hidden" value="" /> 
	<div class="mini-layout" style="width:100%;height:100%;padding:0px;">
		<div region="center" style="padding:0px;margin:0px;">
			<div class="mini-fit" id="divSynodList">
				<div class="td-nav" style="padding:0px;">
				<a class="mini-button" iconCls="icon-add" onclick="addRow()"
				plain="true">新增</a>
              <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow()">删除</a>     
              <span class="separator"></span>             
              <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData()">保存</a><span class="separator"></span>    
			  <a class="mini-button" iconCls="icon-downgrade" plain="true" onclick="exportexcel()">导出</a>
				
					<div style="float:right;">
						<input id="key" class="mini-textbox" emptyText="根据物品名称搜索"
							onenter="onKeyEnter" style="margin-right:1px;font-size:12px;" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
					</div>
					<div style="clear:both;"></div>
				</div>
				<!-- 显示表格 -->
				<div class="mini-fit">
					<div id="grdbg" class="mini-datagrid"
						style="width: 100%; height: 100%;border:0px;"
						url="${path}/officeManage/getBgypList.json" allowAlternating="false"
						idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20"
						multiSelect="true" borderStyle="border:0px;" 
						allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
						<div property="columns">            
                    <div type="checkcolumn"></div>
                    <div type="indexcolumn" headerAlign="center">序号</div>
                    <div field="wpname" width="120" align="center" headerAlign="center" allowSort="true">物品名称                        
                        <input property="editor" class="mini-textbox" style="width:100%;"/>
                        <input id="nameFilter" property="filter" class="mini-textbox" onvaluechanged="onNameFilterChanged" style="width:100%;" />
                        <input name="bgypid" id="bgypid" type="hidden" class="mini-hidden"/>
                        <input name="helpStr" id="helpStr" type="hidden" class="mini-hidden"/>
                    </div>                
                    <div field="lysl" width="100" allowSort="true" align="center" headerAlign="center">领用数量
                        <input property="editor" class="mini-textbox" style="width:100%;" />
                    </div>            
                    <div field="lyrsign" width="100" align="center" headerAlign="center" allowSort="true" renderer="onStatusRenderer">领用人签字
                        <input property="editor" class="mini-textbox" minValue="0" maxValue="200" value="25" style="width:100%;"/>
                    </div>
                    <div field="lyrq" width="100" align="center" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd">领用日期
                        <input property="editor" class="mini-datepicker" style="width:100%;"/>
                    </div>                                    
                    <div field="remark" width="100" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="true">备注
                    	<input property="editor" class="mini-textbox" style="width:100%;"/>
                    </div> 
                </div>
					</div>
				</div>
			</div>

			<div class="mini-panel" borderStyle="border:0" showFooter="false"
				showHeader="false" style="display:none;width:100%;height:100%"
				id="divSynodbox" bodyStyle="padding:0px;"></div>
		</div>
	</div>

	<script type="text/javascript">
		// 加载数据
		mini.parse();
		var grid = mini.get("grdbg");
		grid.load();
		var confirmMessage = "请至少选中一条记录！";
        function search() {
            var key = mini.get("key").getValue();

            grid.load({ key: key });
        }

        function onKeyEnter(e) {
            search();
        }
        //新增行
         function addRow() {            
                var newRow = { name: "请填写物品名称" };
                grid.addRow(newRow, 0);
        }
        //删除
         function removeRow() {
        	 var rows = grid.getSelecteds();      	 
             if (rows.length > 0) {
            	 if(confirm("确定删除选中记录？")){
            		 grid.removeRows(rows, true);
            	 }
             }
             else{
            	 alert(confirmMessage);
             }
        }
        //保存数据
        function saveData() {
            var data = grid.getChanges();         
            var json = mini.encode(data);
             grid.loading("保存中，请稍后......");
             $.ajax({
                url: "saveDatasBg.json",
                data: { data: json },
                type: "post",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });  
        }
        function exportexcel(){     	
        	var data = grid.getSelecteds();      	
            var json = mini.encode(data);
             /* grid.loading("导出中，请稍后......"); */
             if(data.length>0){
            	 window.location.href="exportExcel.json?data="+json;
             }
             else{
            	 alert(confirmMessage);
             }
             
        }
	</script>
	</body>
</html>