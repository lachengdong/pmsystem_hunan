<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯互监编排</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
	<div id="form1" class="mini-toolbar"  style="padding:2px;border:0;" align="right">
		<a class="mini-button" iconCls="icon-user" plain="true" onclick="supervisionRegister();">互监名册</a>
		<a class="mini-button" iconCls="icon-add"  plain="true" onclick="addSupervision();" >新增</a>
		<a class="mini-button" iconCls="icon-remove" plain="true" onclick="deleteSupervision()">批量删除</a>	
	</div>  
	<div class="mini-fit" >
	    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
	        url="<%=path %>/getSupervisionList.json?1=1" idField="" multiSelect="true"  allowAlternating="true"  virtualScroll="true">
	        <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="orgname" width="100" headerAlign="center" align="center">单位</div>   
				<div field="svtype" width="100" headerAlign="center" align="center" >互监类型</div>   
				<div field="grouptime" width="100" headerAlign="center" align="center" >编组时间</div>	     
	            <div field="pknum" width="120" headerAlign="center" align="center" allowSort="true" renderer="packageNumRenderer">包联组</div>    
	            <div field="policemen" width="120" headerAlign="center" align="center">包组干警</div>
	            <div field="" width="120" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
	        </div>
	    </div>
  	</div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    //操作项            
   function onActionRenderer(e) {
	    var s ="";     	
		s=s+'<a class="New_Button" href="javascript:chakan();">查看</a>&nbsp;&nbsp;';       
		s=s+'<a class="New_Button" href="javascript:remove()">删除</a>&nbsp;&nbsp;';
	    return s;
	}
	//包联组        
   function packageNumRenderer(e) {
	    var record = e.record;
	    var pknum=record.pknum;
	    if(pknum){
	     	var s ='第&nbsp;'+pknum+'&nbsp;组';   
	    	return s;
	    }
	}
	
	//新增
	function addSupervision(){
		var url = "<%=path %>/toNewSanrenhujianPage.action?1=1";
		self.location.href=url;
	} 
	
	function remove() {
		var row = grid.getSelected();
		if (confirm("确定删除选中记录？")){
            $.ajax({
                   url: "deleteSanrenhujianData.action?1=1&hujianid="+row.hujianid,
                   type: "post",
                   success: function (text) {
                       grid.reload();
                   },
                   error: function () {
                       alert("操作失败！")
                   }
             });
		}
	}
	
	function deleteSupervision() {
        var rows = grid.getSelecteds();
        if(rows.length>0){
            if (confirm("确定删除选中记录？")) {
                var hujianids = [];    
                for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     hujianids.push(r.hujianid);                                                      
                }
                var hujianid = hujianids.join(',');                
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "deleteSanrenhujianDatas.action?1=1&hujianid="+hujianid,
                    type: "post",
                    success: function (text) {
                          grid.reload();
                    },
                    error: function () {
                         alert("操作失败！")
                    }
                });
            } 
       }else{
            alert("请选择一条或多条信息进行删除！")
       }
   }
	
   function chakan(){	
	    var row = grid.getSelected();
	    if (row) {
		    var url = "showSanrenhujianData.action?1=1&hujianid="+row.hujianid;
			self.location.href=url;
	     }else {
	         alert("请选中一条记录");
	     }
	}

	
</script>
</body>
</html>