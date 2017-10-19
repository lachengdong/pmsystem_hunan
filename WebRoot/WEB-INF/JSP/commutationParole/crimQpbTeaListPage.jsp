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
   		<script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>奖励类型管理</title>
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
       <input id="crimid" name="crimid" value="${record.crimid}" class="mini-hidden" />
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
	            <div field="ID" headerAlign="center" visible="false">奖励ID</div>
	            <div field="TABLEID" headerAlign="center" visible="false">奖励表</div>
	            <div field="REWARDID" headerAlign="center" renderer="onRewardRenderer" >奖励类型
	                <input property="editor" class="mini-combobox" textField="text" valueField="id" url="<%=path%>/ajaxSelectData.action?id=rewardid&text=name&table=tbxf_rewardtypes" required="true" />
	            </div>
	            <div field="AWARDNO" headerAlign="center" renderer="onRenderer" >奖励证号
	                <input property="editor" class="mini-spinner"  minValue="0" maxValue="99999999999999" required="true" />
	            </div>
              <div field="REWARDDATE" width="80" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer" dateFormat="yyyy-MM-dd">获取日期
              		<input property="editor" class="mini-datepicker" style="width:100%;" required="true" />
              </div>	
	            <div field="REWARDNAME" headerAlign="center">奖励名称
	            <input property="editor" class="mini-textbox"  />
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
	var crimid = mini.get("crimid").getValue();
	var url = "ajaxGetCrimQpbTeaList.action?crimid="+crimid;
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
		var crimid = mini.get("crimid").getValue();

        grid.loading("保存中...");
        $.ajax({
            url: "saveCrimQpbTea.action?crimid="+crimid,
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
                url: "<%=path%>/ajaxGetRewardName.action?id="+e.value,
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
