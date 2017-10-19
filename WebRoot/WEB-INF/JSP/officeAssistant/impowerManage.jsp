<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>授权管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    .s{
    	width:100%;height:100%;_height:94.8%;
    }    
    </style>
</head>
<body>
    <div id="div_one" class="s">
        <div class="mini-toolbar" style="padding:0px;border-bottom:0;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                		<a class="mini-button" iconCls="" plain="true" onclick="add()">增加</a>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
						<a class="mini-button" iconCls="" plain="true" onclick="repeal()">撤销授权</a>
					</td>
                    <td style="white-space:nowrap;">
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="被授权用户"  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    <div class="mini-fit" id="div_two">
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" url="getDataForImpowerManage.action?1=1"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	
	        		<div type="checkcolumn" width="10"></div> 
	        		<input type="text" id="noticeid" name="noticeid" field="noticeid" width="0px"/>
				         <div field="opid" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
					     	授权用户
					     </div>  
				         <div field="username" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
					     	被授权用户
					     </div>  
				         <div field="starttime" width="40px"  headerAlign="center" renderer="onDateRenderer" dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
					     	开始时间
					     </div>  
				         <div field="endtime" width="40px"  headerAlign="center" renderer="onDateRenderer" dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
					     	结束时间
					     </div> 
				         <div field="state" width="40px"  headerAlign="center" allowSort="true" align="center" >
					      	授权状态
					     </div>  
				         <div field="remark" width="40px"  headerAlign="center" allowSort="true" align="center" >
					     	备注
					     </div>  
	        </div>
	    </div>
    </div>

  </div>
 </div>
    <script type="text/javascript">
        mini.parse();
       var grid = mini.get("datagrid");
       	grid.sortBy("starttime","desc");
        grid.load();
		//快速查询
        function search() {
            var key = mini.get("key").getValue();
            grid.load({key:key});
        }
        function onKeyEnter(e) {
            search();
        }
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        };
        function add() {
		    var grid= mini.get("datagrid");
	        var win=mini.open({
		        url: "addImpowerManage.action?1=1&type=add",
		        showMaxButton: true,
		        allowResize: false, 
		        title: "添加", width: 600, height: 380,
		        onload: function () {
		         	var iframe = this.getIFrameEl();
		          	var data = {type:'new'};
		          	iframe.contentWindow.SetData(data);
		         },
		         ondestroy: function (action) {
		            grid.reload();
		         }
	         });
	      }
	  function edit(){
	   		
	      var rows = grid.getSelecteds();
	            if (rows.length == 1) {
	             	 for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        var id=r.noticeid;
	                        var aistarttime=r.starttime;
	                        var username=r.username;
	                        var endtime=r.endtime;
	                        var state=state;
	                        var remark=r.remark;
	                        
	                   }
	        var win=mini.open({
		        url: "addImpowerManage.action?1=1&type=update&id="+id,
		        showMaxButton: true,
		        allowResize: false, 
		        title: "编辑", width: 340, height: 380,
		        onload: function () {
		         	var iframe = this.getIFrameEl();
		          	//var data = { action: "edit", id: id,myoperatetype:'edit'};
		          //	iframe.contentWindow.SetData(data);
		         },
		         ondestroy: function (action) {
		            //grid.reload();
		         }
	         }); 
	          	}else{
	          		alert("请选中一条记录");
	          	}
	    }


	    function repeal(){
	    
	    }
	    function remove() {
	            var rows = grid.getSelecteds();
	            if (rows.length > 0) {
	                if (confirm("确定删除选中记录？")) {
	                    var ids = [];
	                    for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        ids.push(r.noticeid);
	                   }
	                    var id = ids.join(',');
	                    $.ajax({
	                          url:"removeTbuserNotice.action",
	                        type: 'POST',
	                        data:{id:id},
	                        success: function (text) {
	                            grid.reload();
	                        },
	                        error: function () {
	                      alert("操作失败!");
	                        }
	                    });
	                }
	            } else {
	                alert("请选中一条记录");
	            }
	        }
    </script>
</body>
</html>