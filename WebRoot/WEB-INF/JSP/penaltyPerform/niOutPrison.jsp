<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>拟岀监</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 

     <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var confirmM = "确认出监？";
     	var noCriminalsMessage = "没有要出监的罪犯！";
     </script>
     <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    .mini-datagrid .mini-panel-border{
	    	border-left:0;
	    	border-right:0;
	    	border-bottom:0;
	    }
	    .mini-datagrid .mini-grid-border{
	    	border-left:0;
	    	border-right:0;
	    	border-bottom:0;
	    }
    </style>
</head>
<body>
    	 <div showCollapseButton="false" style="width:100%;height:100%;"> 
	        <div class="mini-toolbar" style="border:0px;padding:0px;">
	            <table style="width:100%;">
	                <tr align="right">
	                  	<td style="white-space:nowrap;">
	                    	<input class="mini-datepicker" allowInput="true" emptyText="出监日期起"  style="width:125px;" id="key" onenter="onKeyEnter" dateFormat="yyyy-MM-dd"/>    
	                        <input class="mini-datepicker" allowInput="true" emptyText="出监日期止"  style="width:125px;" id="key1" onenter="onKeyEnter" dateFormat="yyyy-MM-dd"/>    
	                       	<input class="mini-textbox" id="key2" name="key2" style="width:150px;" emptyText="编号/姓名/拼音" onenter="onKeyEnter" />
	                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
	                        <span class="separator"></span> 
		                    <a class="mini-button" iconCls="icon-downgrade"  plain="true" onclick="onconfirm()">批量拟出监</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
		   	<div class="mini-fit">
			    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
			    	url="getOutPrisonList.action?1=1"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
			        <div property="columns">
			            <div type="checkcolumn" ></div>        
			            <div field="APPLYID" width="120" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>    
			            <div field="APPLYNAME" width="120" headerAlign="center"  align="center" allowSort="true">罪犯姓名</div> 
			            <div field="JIANYUNAME" width="120" headerAlign="center"  align="center" allowSort="true">监狱</div>  
			           	<div field="FENJIANQUNAME" width="120" headerAlign="center"  align="center" allowSort="true">监区</div>  
			            <div field="YUANPANXINGQI" width="120" headerAlign="center"  align="center"  allowSort="true" >原判刑期</div>
			            <div field="YUANPANXINGQIQIRISTR" width="120" headerAlign="center"  align="center"  allowSort="true" >刑期起日</div>
			            <div field="XIANXINGQIZHIRISTR" width="120" headerAlign="center"  align="center"  allowSort="true" >现刑期止日</div>
			            <div field="SHIFANGRIQISTR" width="120" headerAlign="center"  align="center"  allowSort="true" >释放日期</div>
			            <div field="" width="120" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
			        </div>
			    </div>
		    </div>
		</div>
		
   <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("applyid", "desc");

        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        //确定 向后台传递选择的数据
        function onconfirm() {
        	var rows = grid.getSelecteds();
            var json = mini.encode(rows);
          
            if (rows.length > 0) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.APPLYID); 
                }
                var id = ids.join(',');
	             $.ajax({
		                url: "niOut.action?1=1&crimid="+id,
                        success: function (text) {
                       	  alert("操作成功!");
		                		grid.reload();
                        },
                        error: function () {
                        alert("操作失败!");
                        }
		            });
            }else{
            	alert(noCriminalsMessage);
            }
        }
      
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;

            var s = ' <a class="Edit_Button" href="javascript:onconfirm(\'' + uid + '\')" >拟出监</a>';
            return s;
        }       
       
		
	
		
		//数据加载后发生，过滤掉已选择的罪犯
    	function beforeload(){
    		var items = grid.getData();
    		for (var i = 0, l = items.length; i < l; i++) {
                var item = items[i];
                if(item){
	                var criminalid = item.criminalid;
	                for (var j=0, m = items.length; j < m; j++) {
	                	var item = items[j];
	                	var criminalid = item.criminalid;
	                	if(criminalid == criminalid){
	                		grid.removeRow(item);
	                		beforeload();
	                	}
	                }
	            }
            }
    	}
    	//监狱过滤
		function ondeptchanged() {
			var sdid = mini.get("sdid").getValue();
			grid.load({deptid:sdid});
		}
		//快速查询
        function search() {
            var key = mini.get("key").text;
            var key1 = mini.get("key1").text;
            var key2 = mini.get("key2").getValue();
            grid.load({ key: key,key2: key2,key1:key1});
        }
        function onKeyEnter(e) {
            search();
        }
    </script>
</body>
</html>