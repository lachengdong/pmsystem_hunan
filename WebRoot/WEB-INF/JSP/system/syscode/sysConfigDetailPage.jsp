<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>系统代码细节</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	     .actionIcons span
	        {
	            width:16px;
	            height:16px;
	            display:inline-block;
	            background-position:50% 50%;
	            cursor:pointer;
	        }
    </style>
</head>
<body>
	<div id="form1" class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
		<input id="codetype" name="codetype" class="mini-hidden" value="${codetype}"/>
		<table >
             <tr>
                <td style="width:100%;">
                	&nbsp;&nbsp;
					父级代码：<input id="key" name="key" class="mini-textbox" emptyText="父级代码名称、父级查询码" 
										allowInput="true" style="width:150px;" onenter="search();"/>
                </td>
                <td style="white-space:nowrap;"> 
                	<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
                	&nbsp;
                	<a class="mini-button" plain="true" iconCls="icon-save"  onclick="save();">保存</a>
                	&nbsp;
					<a class="mini-button" iconCls="icon-cancel"  plain="true" onclick="Close();" >关闭</a>
                </td>
             </tr>
        </table>
	</div>  
	
	<div class="mini-fit" >
	  
	     <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" idField="id" allowCellEdit="true"
	     	allowCellSelect="true" allowAlternating="true" pageSize="20"  sizeList="[20,100,200]"
	     	url="<%=path%>/syscode/getSyscodeDetailList.json?1=1&codetype=${codetype}" showPager="true" >
         <div property="columns">
         	<div type="checkcolumn" width="20"></div>
            <div field="pcodename"  width="40" headerAlign="center"  align="center">父级代码名称
            </div>
            <div field="pcodeid"  width="40" headerAlign="center"  align="center">代码类型ID
         	 	<input property="editor" class="mini-textbox"  allowInput="true" style="width:100%;"/>
            </div>
            <div field="codeid"  width="40" headerAlign="center"  align="center">代码ID
            	<input property="editor" class="mini-textbox"  allowInput="true" style="width:100%;"/>
            </div>
            <div field="name" width="40" headerAlign="center"  align="center" >代码名称
                <input property="editor" class="mini-textbox"  allowInput="true" style="width:100%;"/>
            </div> 
           <div field="scearch" width="40" headerAlign="center"  align="center"  >查询码
                <input property="editor" class="mini-textbox"  allowInput="true"  style="width:100%;"/>
            </div>
            <div field="sn" width="40" headerAlign="center"  align="center"  >排序
                <input property="editor" class="mini-spinner"  allowInput="true"  style="width:100%;"/>
            </div>           
            <div cellCls="actionIcons" name="action" width="40" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">
            	<span class="icon-new" title="增加记录" onclick="newRow()"　align="center">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </div>                                        
        </div>
    </div>   
  	</div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    
    grid.on("drawcell", function (e) {
        var row = e.record;
        if(row.noid){
        	e.cellStyle = "background:#c4f1da";
        }
    });
    
    function search(){
    	var key = mini.get("key").getValue();
    	grid.load({key:key});
    }
    
    function SetData(data){
    	//data = mini.clone(data);
    	//mini.get("svtype").setValue(data.svtype);
    	//mini.get("svtypename").setValue(data.svtypename);
    	//mini.get("pknum").setValue(data.pknum);
    	//mini.get("policemen").setValue(data.policemen);
    }
    
	//保存
	function save(){
		 /* var form = new mini.Form("form1");
		 form.validate();
		 var flag = form.isValid();
		 if(! flag){
			 return;
		 }
		 var o = form.getData();
		 var formdata = mini.encode([o]); */
		 
		 var rowsData = grid.getChanges();
         var griddata = mini.encode(rowsData);
         var url = "<%=path %>/syscode/saveSysconfigDetail.json";
         
         $.ajax({
             url: url,
             data: {griddata:griddata},
             dataType:"text",
             type:"POST",
             success: function (text){
             	var data = mini.decode(text);
             	if('success' == data.status){
             		alert("操作成功！");
             	}else{
             		alert("操作失败！");
             	}
             	grid.reload();
             	//Close();
             }
         });
	} 
	
	function newRow() {
		var codetype = mini.get("codetype").getValue();
        var row = {codetype:codetype, pcodeid:codetype};
        grid.addRow(row);
        grid.beginEditRow(row);
    }
	
    function delRow(row_uid) {
         var row = grid.getRowByUID(row_uid);
         if (row) {
        	 grid.removeRow(row);
      	 }
     }
    
    function onActionRenderer(e) {
        //var grid = e.sender;
      var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;
        var s = '<span class="icon-remove" title="删除记录" onclick="delRow('+uid+')"></span>&nbsp;';
      	return s;
    }
      
      
       function CloseWindow(action){
           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
           else window.close();
       };
         
         
          //手动关闭
     function Close(){
    	 	CloseWindow("cancel");
       }
          
  		// 刷新本页面
		function refreshPage(){
			//
			if(!window["____refreshPage"]){
				window["____refreshPage"] = true;
				//
				location.reload();
			} else {
				window.setTimeout(function(){
					window["____refreshPage"] = false;
					},1*1000);
			}
		};
		
</script>
</body>
</html>