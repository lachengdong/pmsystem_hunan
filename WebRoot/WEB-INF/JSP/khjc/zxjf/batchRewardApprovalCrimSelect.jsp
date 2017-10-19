<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>批量奖励罪犯选择页</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
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
    <script type="text/javascript">
		var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var confirmM = "确认选中罪犯？";
	</script>
</head>
<body>
    <input id="selected" type="hidden" value="${requestScope.selected}"/>
    <input id="sortval" type="hidden" value="${requestScope.sortval}"/>    
    <div id="div_one" class="mini-splitter"  style="width:100%;height:100%;">
    	<div showCollapseButton="false" style="padding:0px;">
	        <div class="mini-toolbar" style="border:0px;padding:0px;">
	            <table style="width:100%;">
	                <tr align="right">
	                    <td style="white-space:nowrap;">
	                    	<input id="orgid1" class="mini-combobox"   valueField="ORGID" textField="NAME"   emptyText="监区" showNullItem="true"
	                             nullItemText="--全部--" url="getDepartList.action?1=1" style="width:130px;" popupWidth="200" onvaluechanged="ondeptchanged"/>
	                        <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号或姓名" style="width:100px;" onenter="onKeyEnter"/>
	                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
	                        <span class="separator"></span> 
		                    <a class="mini-button" iconCls="icon-downgrade"  plain="true" onclick="onadd()">批量选中</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
	    <div class="mini-fit" id="div_two"  style="width:100%;height:100%;">
	    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
	        url=""  idField="criminalid" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
	         onpagechanged="onPageChanged"  onload="beforeload()">
	        <div property="columns">
	            <div type="checkcolumn" ></div>        
	            <div field="crimid" width="80" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>    
	            <div field="name" width="80" headerAlign="center"  align="center" allowSort="true">罪犯姓名</div>
	            <div field="cseason" width="40" headerAlign="center" align="center"  allowSort="true">当季得分</div>
                <div field="pastseason" width="60" headerAlign="center" align="center"  allowSort="true">历季得分</div>
	            <div field="" width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
	        </div>
	    </div>
	  </div>
	</div>
	<div showCollapseButton="false" size="320px;">
				<div class="mini-toolbar" style="border:0;padding:0px;">
		            <table  style="width:100%;">
		                <tr align="left">
		                    <td align="right">
		                        <a class="mini-button" iconCls="icon-upgrade"  plain="true" onclick="canceladd()">批量取消</a>
		                    	<a class="mini-button" iconCls="icon-ok"  plain="true" onclick="onconfirm()">确认选中</a>
		                    </td>
		                </tr>
		            </table>           
	       		 </div>
	         <div class="mini-fit" id="div_two"  style="width:100%;height:100%;" >
		         	<div id="grid2" class="mini-datagrid" style="width:100%;height:100%;"                     
	                    idField="crimid"  multiSelect="true" showFooter="true"   sizeList="[10,20,50,100]"
	                    allowCellEdit="true" allowCellSelect="true" showPager="false">
                    <div property="columns">
                        <div type="checkcolumn" width="20"></div>
                        <div header="罪犯编号" field="crimid"  headerAlign="center" align="center"  width="40"></div>
                        <div header="罪犯姓名" field="name"  headerAlign="center" align="center" width="50" ></div>
                    </div>
                </div>
	       </div>
	</div>
 </div>
    <script type="text/javascript">
        mini.parse();
		var grid = mini.get("datagrid1");
		var selected = document.getElementById("selected").value;
		var sortval = document.getElementById("sortval").value;
		grid.setUrl("seasonPointsSortList.action?1=1&selected=" + selected);
		var grid2 = mini.get("grid2");
		grid.sortBy(sortval,"desc");
		grid.load();
		
		//确定 向后台传递选择的数据
        function onconfirm() {
        	var rows = grid2.getSelecteds();
            if (rows.length > 0) {
            	var ids = [];
            	var names = [];
           		for (var i = 0, l = rows.length; i < l; i++) {
                	var r = rows[i];
                	ids.push(r.crimid);
                	names.push(r.name);
                }
            	var id = ids.join(',');
            	var name = "    " + names.join('、')+"。";
	        	var row = new Array([2]);
	        	row[0] = id;
	        	row[1] = name;
				window.returnValue = row;            	
				CloseWindow();
            }else{
            	alert(confirmMessages);
            }
        }
        
		 //清空
        function onclear() {
            var items = grid2.getSelecteds();
            grid2.clearRows();
            grid.reload();
        }

        //监狱过滤
		function ondeptchanged() {
			var orgid1 = mini.get("orgid1").getValue();
			editRow(orgid1);
		}
		
        function editRow(orgid) {
			grid.setUrl( "seasonPointsSortList.action?1=1&selectorgid="+orgid);
			grid.load();
        }
        
        function onGenderRenderer(e) {
            for (var i = 0, l = deptLevel.length; i < l; i++) {
                grid.load({ dept_id: node.id, key: key });
            }
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
        
        //单个添加
        function tianjia(v){
	        var items = grid.getSelected();
            grid.removeRow(items);
            grid2.addRow(items);
		}
		
		//单个取消
		function shanchu(){
	        var items = grid2.getSelected();
            grid2.removeRow(items);
           // grid.addRow(items);
             grid.reload();
		}
		
        //数据加载后发生，过滤掉已选择的用户
    	function beforeload(){
    		var items2 = grid2.getData();
    		var items = grid.getData();
    		for (var i = 0, l = items.length; i < l; i++) {
                var item = items[i];
                if(item){
	                var criminalid = item.crimid;
	                for (var j=0, m = items2.length; j < m; j++) {
	                	var item2 = items2[j];
	                	var criminalid2 = item2.crimid;
	                	if(criminalid2 == criminalid){
	                		grid.removeRow(item);
	                		beforeload();
	                	}
	                }
	            }
            }
    	}
    	
        function onKeyEnter(e) {
            search();
        }
        
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
		}
		
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s ="";
	         s=s+'<a class="New_Button" href="javascript:tianjia();">添加</a>&nbsp;&nbsp;';
            return s;
        }
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }        
    </script>
 </body>
</html>