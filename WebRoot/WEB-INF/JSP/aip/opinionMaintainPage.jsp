<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>意见维护页面</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }     
		    .mini-panel-border{
		    	border-left:0;
		    	border-right:0;
		    	border-bottom:0;
		    }
		</style>
</head>
<body>
	   <input id="operatetype" name="operatetype" class="mini-hidden" />
       <div style="height:100%;">
       <div class="mini-toolbar"  style="padding:2px;border: 0px;">
            <table >
               <tr>
	                <td style="width:100%;">
	                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
	                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
	                    <span class="separator"></span>
	                    <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">存盘</a>            
	                    &nbsp;&nbsp;&nbsp;可设置变量：[理由]  [建议减刑]  [剥权] [罚金]
	                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="10" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true" editNextRowCell="true">
            <div property="columns">
            <div type="checkcolumn" width="20"></div>
            <div field="NAME" headerAlign="center" width="30">创建人
            </div>
            <div field="OPINIONCONTENT" width="200" headerAlign="center" >意见内容
                <input property="editor" class="mini-textarea"  />
            </div>
            <div type="comboboxcolumn"  width="50" field="OPINIONIDSTATE" headerAlign="center">意见状态
                <input property="editor" class="mini-combobox"  data="opionState" />   
            </div>
            <div field="SN" headerAlign="center"  width="30" >意见排序
                <input property="editor" class="mini-spinner"  minValue="0" value="0"  maxValue="9999"/>
            </div>
            <div field="REMARK"  width="100" headerAlign="center" >备注
                <input property="editor" class="mini-textarea"  />
            </div>
            </div>
	    </div>
    </div>
    </div>
</body>
</html>
<script type="text/javascript">
    var opionState = [{ id: 1, text: '公共意见' }, { id: 2, text: '个人意见'}];
	mini.parse();
	var grid = mini.get("datagrid");
	var url = "ajaxGetOpinionmaintainList.action?1=1";
	grid.setUrl(url);
	grid.load();
	
	function addRow() {          
        var newRow = { name: "New Row" };
        grid.addRow(newRow, 0);
        grid.beginEditCell(newRow, "LoginName");
    }
    function removeRow() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            grid.removeRows(rows, true);
        }
    }
    function saveData() {
        var data = grid.getChanges();
        var json = mini.encode(data);
        grid.loading("保存中...");
        $.ajax({
            url: "saveOpinionmaintain.action?1=1",
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

    grid.on("celleditenter", function (e) {
        var index = grid.indexOf(e.record);
        if (index == grid.getData().length - 1) {
            var row = {};
            grid.addRow(row);
        }
    });
</script>
