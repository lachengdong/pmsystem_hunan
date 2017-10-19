<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>减刑幅度参照</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    </style>
     <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     </script>
</head>
<body>
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	<!-- 
	    <input id="operator" name="operator" value="new" class="mini-hidden" />
		<input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
		 -->
		<table style="width:100%;">
 			<tr>
                <td style="width:100%;">
                	<a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
                </td>
                <td style="white-space:nowrap;">
            		<input class="mini-textbox" vtype="maxLength:50;" emptyText="输入方案名称、减刑幅度情况" id="key1" style="width:100px;" onenter="onKeyEnter"/>
					<a class="mini-button" iconCls="icon-search" plain="true" onclick="searchUser()" >查询</a>
           		</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" id="div_two" >
	    <input class="mini-hidden" name="id"/>
	    <input name="sqdiscribe" id="sqdiscribe" class="mini-hidden" />
	    <input name="sqid" id="sqid" class="mini-hidden" />
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"  idField="id" pageSize="20" sizeList="[10,20,50,100]"   multiSelect="true" >
            <div property="columns">
            <div type="indexcolumn" headerAlign="center">序号</div>
            <div field="punid" width="70" dateFormat="yyyy-MM-dd" headerAlign="center" align="center"  allowSort="true">适用时间(始)</div> 
            <div field="typeid" width="70"  dateFormat="yyyy-MM-dd"  headerAlign="center" align="center"  allowSort="true">适用时间(止)</div> 
            <div field="phid" width="120" headerAlign="center" align="center"  allowSort="true">减刑幅度情况</div> 
            <div field="syear" width="70" headerAlign="center" align="center"  allowSort="true">原判刑期(起)</div>
            <div field="remark" width="70" headerAlign="center" align="center"  allowSort="true">裁判余刑(起)</div>
            <div field="opid" width="70" headerAlign="center" align="center"  allowSort="true">起始时间(起)</div>
            <div field="optime" width="70" headerAlign="center" align="center"  allowSort="true">间隔时间(起)</div>
            <div name="action" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;" width="120">操作</div>
			</div>    
		</div>    
    </div>
    <script type="text/javascript">
        mini.parse();
        var url = "<%=path %>/system/ajaxGetParoleReference.action?1=1";
       	//var tree = mini.get("tree1");
		//var menuid = document.getElementById("menuid").value;
		var grid = mini.get("datagrid1");
		grid.setUrl(url);
		grid.sortBy("jxfdorder,appstartdate", "asc");
		grid.load();
		
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s ="";
          			s=s+'<a class="New_Button" href="javascript:leiji()">累计情况</a>&nbsp;&nbsp;';
          			s=s+'<a class="New_Button" href="javascript:edit()">修改</a>&nbsp;&nbsp;';
          			s=s+'<a class="New_Button" href="javascript:remove()">删除</a>';
            return s;
        }
        function edit() {
        	var grid= mini.get("datagrid1");
            var row = grid.getSelected();
            if (row) {
            	var myurl="tojxfdczInfo.action?1=1&operatetype=edit&id="+row.jxfdid;
                var win=mini.open({
                    url: myurl,
                    showMaxButton: true,
                    allowResize: false,
                    title: "幅度情况", width: 540, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.jxfdid,myoperatetype:'edit',sqdiscribe:row.zgscname,sqid:row.sqid,gzbxtype:row.gzbxtype};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
        
        function remove(){
        	var grid= mini.get("datagrid1");
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.jxfdid);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");	                    
                    $.ajax({
                        url: "removejxfdczInfo.action?1=1"+"&id=" +id,
                        type: "post",
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
        }
	    function add() {
	    	var grid= mini.get("datagrid1");
	    	var row = grid.getSelected();
	    	var myurl="tojxfdczInfo.action?1=1&operatetype=new";
	    	//var sqid = mini.get("sqid").getValue();
	    	var sqdiscribe = mini.get("sqdiscribe").getValue();
	    	//if(sqid){
	    		var win=mini.open({
	                url: myurl,
	                showMaxButton: true,
	                allowResize: false, 
	                title: "幅度情况", width: 540, height: 360,
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { action: "new",myoperatetype:'new',sqdiscribe:sqdiscribe,sqid:sqid};
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action) {
	                    grid.reload();
	                }
	            });
	    	//}else{
	    	//	alert("请选择筛查方案!");
	    	//}
        } 
	    function leiji() {
	    	var grid= mini.get("datagrid1");
	    	var row = grid.getSelected();
	    	var myurl="jxfdczleiji.action?1=1&operatetype=new";
	    		var win=mini.open({
	                url: myurl,
	                showMaxButton: true,
	                allowResize: false, 
	                title: "幅度累计情况", width: 780, height: 380,
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { action: "new",myoperatetype:'new',jxfdqk:row.jxfdqk,jxfdid:row.jxfdid};
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action) {
	                    grid.reload();
	                }
	            });
	            win.max();
	    }
		function search() {
      		var key = mini.get("key1").getValue();
			grid.load({ key: key});
   		} 
        function onKeyEnter(e) {
            search();
        }  
        
        /*
        tree.on("nodeselect", function (e) {
                editRow(e.node.sqid);
                mini.get("sqid").setValue(e.node.sqid);
        		mini.get("sqdiscribe").setValue(e.node.sqdiscribe);
        });
        function editRow(sqid) {
			grid.setUrl( url +"&sqid="+ sqid);
			grid.load();
        }
        function onGenderRenderer(e) {
            for (var i = 0, l = deptLevel.length; i < l; i++) {
                grid.load({ dept_id: node.id, key: key });
            }
        }
         */
        //过滤树
        /*
        function search() {
            var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase();    
                tree.filter(function (node) {
                    var text = node.sqdiscribe ? node.sqdiscribe.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
        */
        //验证id是否已存在
        /*
        function onIdValidation(e) {
       		if(e.isValid) {
		        var node = tree.getNode(e.value);
		        if(node){
		        	 e.errorText = "此ID已存在！";
				     e.isValid = false;
		        }
	        }
        }
        function onKeyEnter(e) {
            search();
        }
       */ 
    </script>
 </body>
</html>