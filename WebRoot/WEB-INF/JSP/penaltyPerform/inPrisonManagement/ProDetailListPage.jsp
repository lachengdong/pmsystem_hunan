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
		<title>财产刑列表</title>
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
            <div type="indexcolumn"></div>
            <div type="checkcolumn"></div>
            <div type="comboboxcolumn" autoShowPopup="true" name="REMARK1" field="REMARK1" width="50"   align="center" headerAlign="center">财产刑类别
                <input property="editor" class="mini-combobox" style="width:100%;" data="Genders" />                
            </div>  
             <div name="LXDATE" field="LXDATE" width="60"  dateFormat="yyyy-MM-dd">履行日期
                <input property="editor" class="mini-datepicker" style="width:100%;"/>
            </div> 
             <div field="FAJIN" headerAlign="center" renderer="onRenderer" >履行金额(数字)
             <input property="editor" class="mini-spinner"  minValue="0" maxValue="10000000000" value="25" style="width:100%;" required="true"/>
            </div> 
            <div name="LXRNAME"  field="LXRNAME" headerAlign="center"  width="100" >履行人姓名
                <input property="editor" class="mini-textbox" style="width:100%;" />
            </div>
           <div type="comboboxcolumn" autoShowPopup="true" name="LXRLB" field="LXRLB" width="50" align="center" headerAlign="center">与罪犯关系
                <input property="editor" class="mini-combobox" style="width:100%;" data="Genders2" />                
            </div>
            <div field="REMARK2" width="60" headerAlign="center" >备注
                <input property="editor" class="mini-textarea" style="width:200px;" minHeight="50"/>
            </div>  
            </div>
	    </div>
    </div>
    </div>
</body>
</html>
<script type="text/javascript">
 var Genders = [{ id: 1, text: '罚金' }, { id: 2, text: '追缴赃款'}, { id: 3, text: '没收财产'}, { id: 4, text: '民事赔偿'}];
 var Genders2 = [{ id: 1, text: '本人' }, { id: 2, text: '家属'}, { id: 3, text: '其他'}];
	mini.parse();
	var grid = mini.get("datagrid");
	var crimid = mini.get("crimid").getValue();
	var url = "ajaxGetProDetailList.action?crimid="+crimid;
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
		var url = "<%=path%>/basicInfo/saveProDetail.action?crimid=${crimid}&&name=${name}";
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
