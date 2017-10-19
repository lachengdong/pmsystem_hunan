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
		<title>减刑幅度列表</title>
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
       <input id="refid" name="refid" value="${record.refid}" class="mini-hidden" />
       <div style="height:100%;">
       <div class="mini-toolbar"  style="padding:2px;border: 0px;">
            <table >
               <tr>
	                <td style="width:100%;">
	                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
	                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
	                    <span class="separator"></span>
	                    <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">存盘</a>            
	                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="10" allowCellEdit="true" 
	    	allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true" editNextRowCell="true">
            <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="REWARDID" headerAlign="center" renderer="onRewardRenderer" >奖励类型
	                <input property="editor" class="mini-combobox" textField="text" valueField="id" url="ajaxSelectData.action?id=rewardid&text=name&table=tbxf_rewardtypes" required="true" />
	            </div>
	            <div field="SNO" headerAlign="center" renderer="onRenderer" >开始数
	                <input property="editor" class="mini-spinner"  minValue="0" maxValue="200"/>
	            </div>            
	            <div field="ENO" headerAlign="center" renderer="onRenderer" >结束数
	                <input property="editor" class="mini-spinner"  minValue="0" maxValue="200"/>
	            </div>
            </div>
	    </div>
    </div>
    </div>
</body>
</html>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid");
	var refid = mini.get("refid").getValue();
	var url = "ajaxGetMergeReferenceList.action?refid="+refid;
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
		var refid = mini.get("refid").getValue();

        grid.loading("保存中...");
        $.ajax({
            url: "saveMergeReference.action?refid="+refid,
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

    function onRenderer(e){
        if(e.value==-1){
			return "";
        }
        return e.value;
    }
    function onRewardRenderer(e){
    	if(!isNaN(e.value)){
        	var o;
    		$.ajax({
                url: "ajaxGetRewardName.action?id="+e.value,
                type: "post",
                async:false,
                success: function (text) {
    				o = mini.decode(text)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    
                }
            });
    		return o.text;
       	}
        return e.value;
    }
</script>
