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
   		<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>考评成绩列表</title>
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
       <input id="crimid" name="crimid" value="${crimid}" class="mini-hidden" />
       <div style="height:100%;">
       <div class="mini-toolbar"  style="padding:2px;border: 0px;">
            <table >
               <tr>
	                <td style="width:100%;">
	                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
	                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
	                    <span class="separator"></span>
	                    <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">存盘</a>   
	                     <span style="padding-left:40px;">罪犯编号：${crimid }</span>
           			 	<span style="padding-left:10px;">姓名：${name}</span>
	                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="10" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true" editNextRowCell="true">
            <div property="columns">
            <div type="checkcolumn"></div>
             <div name="kpdate" field="kpdate" width="40" allowSort="true" dateFormat="yyyy-MM-dd">考评批准日期
               <input property="editor" class="mini-datepicker" style="width:100%;" />
            </div> 
<!--            <div name="blrname"  field="blrname" headerAlign="center" allowSort="true" width="100" >补录人-->
<!--                <input property="editor" class="mini-textbox" style="width:100%;" />-->
<!--            </div>-->
            <div name="kpcj"  field="kpcj" headerAlign="center" allowSort="true" width="30" >考评成绩
                <input property="editor" class="mini-textbox" style="width:100%;" />
            </div>
             <div field="remark1" width="100" headerAlign="center" allowSort="true">备注
                <input property="editor" class="mini-textarea" style="width:200px;" minHeight="50"/>
            </div>  
<!--             <div type="comboboxcolumn" autoShowPopup="true" name="ISDONE" field="ISDONE" width="50" allowSort="true"  align="center" headerAlign="center">是否处理-->
<!--                <input property="editor" class="mini-combobox" style="width:100%;" data="isGenders" />                -->
<!--            </div> -->
<!--             <div name="PZRQ" field="PZRQ" width="60" allowSort="true" dateFormat="yyyy-MM-dd">呈报日期-->
<!--                <input property="editor" class="mini-datepicker" style="width:100%;"/>-->
<!--            </div> -->
<!--             <div name="PZRQ" field="PZRQ" width="60" allowSort="true" dateFormat="yyyy-MM-dd">奖惩止日-->
<!--                <input property="editor" class="mini-datepicker" style="width:100%;"/>-->
<!--            </div> -->
<!--            <div field="jcjs" width="100" headerAlign="center" allowSort="true">奖惩解释-->
<!--                <input property="editor" class="mini-textarea" style="width:200px;" minHeight="100"/>-->
<!--            </div>  -->
            </div>
	    </div>
    </div>
    </div>
</body>
</html>
<script type="text/javascript">
 var Genders = [{ id: '10', text: '嘉奖' }, { id: '11', text: '表扬'}, { id: '1C', text: '记功'}, { id: '1D', text: '改造积极分子次数'},{ id: '13', text: '立功'},{ id: '14', text: '重大立功'},{ id: '21', text: '警告'},{ id: '23', text: '记过'},{ id: '25', text: '禁闭'}];
 //var isGenders = [{ id: 1, text: '已处理' }, { id: 0, text: '未处理'}];
 //var Genders2 = [{ id: 1, text: '本人' }, { id: 2, text: '家属'}, { id: 3, text: '其他'}];
	mini.parse();
	var grid = mini.get("datagrid");
	var crimid = mini.get("crimid").getValue();
	var url = "ajaxGetKPCJDetailList.action?crimid="+crimid;
	grid.setUrl(url);
	grid.load();
	function onDateRenderer(e) {
		if(e && e.value){
			return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
		}
	    return "";
	}
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
		var url = "saveKPCJDetail.action?crimid=${crimid}&name=${name}";
        grid.loading("保存中...");
        $.ajax({
            url: encodeURI(url),
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

   /* function onRenderer(e){
        if(e.value==-1){
			return "";
        }
        return e.value;
    }*/
    
</script>
