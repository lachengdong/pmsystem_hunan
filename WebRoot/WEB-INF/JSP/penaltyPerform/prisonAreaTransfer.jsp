<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监区调动</title>
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
     	var toTransferMessages = "请选择调往监区！";
     	var transferSameMessage = "调往监区不能与原监区相同！";
     	var confirmM = "确认调动？";
     	var noCriminalsMessage = "没有要调动的罪犯！";
	</script>
</head>
<body>
    <div id="div_one" class="mini-splitter"  style="width:100%;height:100%;">
    	<div showCollapseButton="false" style="padding:0px;">
	        <div class="mini-toolbar" style="border:0px;padding:0px;">
	            <table style="width:100%;">
	                <tr align="right">
	                    <td style="white-space:nowrap;">
	                    	<input id="orgid1" class="mini-combobox"   valueField="ORGID" textField="NAME"   emptyText="原监区" showNullItem="true"
	                             nullItemText="--全部--" url="getDepartList.action?1=1" style="width:150px;" onvaluechanged="search"/>
	                        <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音" style="width:150px;" onenter="onKeyEnter"/>   
	                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
	                        <span class="separator"></span> 
		                    <a class="mini-button" iconCls="icon-downgrade"  plain="true" onclick="onadd()">批量调动</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
	    <div class="mini-fit" id="div_two"  style="width:100%;height:100%;"><!-- getCriminalTransferList.action?1=1 -->
	    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]"
	        url=""  idField="criminalid" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
	         onpagechanged="onPageChanged"  onload="beforeload()">
	        <div property="columns">
	            <div type="checkcolumn" ></div> 
	            <div type="indexcolumn" width="30" headerAlign="center" align="center" allowSort="true">序号</div>       
	            <div field="crimid" width="80" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>    
	            <div field="name" width="80" headerAlign="center"  align="center" allowSort="true">罪犯姓名</div>   
	            <div field="orgname" width="80" headerAlign="center"  align="center" allowSort="false">监区</div> 
	            <div field="" width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
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
	                    </td>
	                    <td><!-- getDepartList.action?1=1&qtype=jianqu -->
	                    	<input id="tosdid" class="mini-combobox"  valueField="ORGID" textField="NAME"  emptyText="选择调往监区"
                            showNullItem="true" nullItemText=" " url="getDepartList.action?1=1&type=all" style="width:150px;"
                           />
	                    </td>
	                    <td align="right">
	                    	<a class="mini-button" iconCls="icon-ok"  plain="true" onclick="onconfirm()">确认调动</a>
	                    	<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10232')"></a>
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
                        <div header="监区" field="orgname"  headerAlign="center" align="center" width="50" ></div>
                        <div header="操作" field="cbiname"  headerAlign="center" align="center" width="30"  renderer="caozuo"></div>
                    </div>
                </div>
	       </div>
	</div>
 </div>
    <script type="text/javascript">
        mini.parse();
		var grid = mini.get("datagrid1");
		grid.setUrl("getPrisonAreaTransferCriminalList.action");
		var grid2 = mini.get("grid2");
		grid.sortBy("crimid","desc");
		grid.load();
		
		        //确定 向后台传递选择的数据
        function onconfirm() {
        	var ysdid = mini.get("orgid1").getValue();
        	var tosdid = mini.get("tosdid").getValue();//调往监区
        	
        	if(!tosdid){//调往监区为空
        		alert(toTransferMessages);
        		return;
        	}
        	var rows = grid2.getSelecteds();
              if (rows.length > 0) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        if(ysdid==tosdid || r.departid==tosdid){//原监区与调往监区相同了
			        		alert(r.name+transferSameMessage);
			        		return;
			        	}
                        ids.push(r.crimid);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "prisonTransfer.action?1=1&crimid=" +id+"&depid="+tosdid,
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

		 function caozuo(e) {
            var grid2 = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
 
            var s = ' <a class="Edit_Button" href="javascript:shanchu()" >取消调动</a>';
            return s;
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
            var orgid1 = mini.get("orgid1").getValue()
        	grid.sortBy("crimid","desc");
            grid.load({ key: key,orgid1: orgid1});
        }
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s ="";
	         s=s+'<a class="New_Button" href="javascript:tianjia();">调动</a>&nbsp;&nbsp;';
            return s;
        }
    </script>
 </body>
</html>