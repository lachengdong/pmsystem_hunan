<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>批量岀监</title>
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
    <div id="div_one" class="mini-splitter"  style="width:100%;height:100%;">
    	<div showCollapseButton="false" style="padding:0px;">
	        <div class="mini-toolbar" style="border:0px;padding:0px;">
	            <table style="width:100%;">
	                <tr align="right">
	                  	<td style="white-space:nowrap;">
	                  		<input class="mini-textbox" id="key2" name="key2" style="width:150px;" emptyText="请输入罪犯编号、姓名" onenter="onKeyEnter" />
	                    	<input class="mini-datepicker" allowInput="true" emptyText="请选择日期"  style="width:125px;" id="key" onenter="onKeyEnter" dateFormat="yyyy-MM-dd"/>    
	                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
	                        <span class="separator"></span> 
		                    <a class="mini-button" iconCls="icon-downgrade"  plain="true" onclick="onadd()">批量出监</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
		   	<div class="mini-fit">
			    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
			    	url="getOutPrisonCriminalList.action?1=1&status=3"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
			        <div property="columns">
			            <div type="checkcolumn" ></div>        
			            <div field="CRIMID" width="120" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>    
			            <div field="NAME" width="120" headerAlign="center"  align="center" allowSort="true">罪犯姓名</div>   
			            <div field="COURTCHANGETO" width="120" headerAlign="center"  align="center" dateFormat="yyyy-MM-dd" allowSort="true" renderer="onDateRenderer">刑期止日</div>
			            <div field="" width="120" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
			        </div>
			    </div>
		    </div>
		</div>
		<div showCollapseButton="false" size="500px;">
			<div class="mini-toolbar" style="border:0;padding:0px;">
	            <table  style="width:100%;">
	                <tr align="left">
	                    <td style="white-space:nowrap;">
	                          <a class="mini-button" iconCls="icon-upgrade"  plain="true" onclick="canceladd()">批量取消</a>
	                          <!-- <span class="separator"></span>  -->
	                          <!-- <a class="mini-button" iconCls="icon-close"  plain="true" onclick="onclear()">清空</a> -->
	                    </td>
	                    <td align="right">
	                    	<a class="mini-button" iconCls="icon-ok"  plain="true" onclick="onconfirm()">拟定出监</a>
	                    	<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10212')"></a>
	                    </td>
	                </tr>
	            </table>           
       		 </div>
	         <div class="mini-fit" id="div_two"  style="width:100%;height:100%;" >
		         	<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;"                     
	                    idField="CRIMID"  multiSelect="true" showFooter="true"   sizeList="[10,20,50,100,1000,5000]"
	                    allowCellEdit="true" allowCellSelect="true" showPager="false">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div header="罪犯编号" field="CRIMID"  headerAlign="center" align="center"  width="40%;" ></div>
                        <div header="罪犯姓名" field="NAME"  headerAlign="center" align="center"  ></div>
                        <div header="操作" field="cbiname"  headerAlign="center" align="center"  renderer="caozuo"></div>
                    </div>
                </div>
	       </div>
		</div>
   </div>
   <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        var grid2 = mini.get("datagrid2");
        grid.load();
        grid.sortBy("crimid", "desc");

        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        //确定 向后台传递选择的数据
        function onconfirm() {
        	var rows = grid2.getSelecteds();
            var json = mini.encode(rows);
          
            if (rows.length > 0) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.CRIMID); 
                }
                var id = ids.join(',');
	             $.ajax({
		                url: "outPrison.action?1=1&crimid="+id,
                        success: function (text) {
                       	  alert("操作成功!");
		                		grid.reload();
		                		grid2.clearRows();
                        },
                        error: function () {
                        alert("操作失败!");
                        }
		            });
            }else{
            	alert(noCriminalsMessage);
            }
        }
        //清空
        function onclear() {
            var items = grid2.getSelecteds();
            grid2.clearRows();
            grid.reload();
        }
        //取消添加
        function canceladd() {
        	var items = grid2.getSelecteds();
        	if(items.length > 0) {
	            grid2.removeRows(items);
	            grid.reload();
            }else{
            	alert(confirmMessages);
            }
        }
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;

            var s = ' <a class="Edit_Button" href="javascript:tianjia(\'' + uid + '\')" >出监</a>';
            return s;
        }
		 function caozuo(e) {
            var grid2 = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;

            var s = ' <a class="Edit_Button" href="javascript:shanchu(\'' + uid + '\')" >取消出监</a>';
            return s;
        }
		        
        //单个添加
        function tianjia(v){
	        var items = grid.getSelected();
            grid.removeRow(items);
            grid2.addRow(items);
		}
		 //批量添加
        function onadd(v){
	        var items = grid.getSelecteds();
	        if(items.length > 0) {
            	grid.removeRows(items);
           	 	grid2.addRows(items);
            }else{
            	alert(confirmMessages);
            }
		}
		//删除
        function shanchu(v){
	        var items = grid2.getSelecteds();
            grid2.removeRows(items);
            grid.reload();
		}
		//数据加载后发生，过滤掉已选择的罪犯
    	function beforeload(){
    		var items2 = grid2.getData();
    		var items = grid.getData();
    		for (var i = 0, l = items.length; i < l; i++) {
                var item = items[i];
                if(item){
	                var criminalid = item.criminalid;
	                for (var j=0, m = items2.length; j < m; j++) {
	                	var item2 = items2[j];
	                	var criminalid2 = item2.criminalid;
	                	if(criminalid2 == criminalid){
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
            var key2 = mini.get("key2").getValue();
            grid.load({ key: key,key2: key2 });
        }
        function onKeyEnter(e) {
            search();
        }
    </script>
</body>
</html>