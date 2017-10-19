<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>已授权限</title>
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
                		<a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">撤销授权</a>
					</td>
                    <td style="white-space:nowrap;">
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入被授权人"  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">快速查询</a>
                    	<span class="separator"></span> 
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
						 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid }')"></a>
                    </td>
                </tr>
            </table>
        </div>
    <div class="mini-fit" id="div_two">
      <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20" 
	    	url="<%=path %>/grant/ajax/list.json?1=1"  allowResize="true" idField="id" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
	        <div property="columns">
        		<div type="checkcolumn" width="15"></div> 
        		<div field ="noticeid" width="0px"  headerAlign="center" allowSort="true" align="center" >授权记录id</div>
		        <div field="opid" width="30px"  headerAlign="center" allowSort="true" align="center" >授权用户</div>
		        <div field="username" width="30px" headerAlign="center" allowSort="true" align="center" >被授权人</div>
		        <div field="starttime" width="40px"  headerAlign="center" renderer="onDateRenderer" allowSort="true" align="center" >开始时间</div>  
		        <div field="endtime" width="40px"  headerAlign="center" renderer="onDateRenderer" allowSort="true" align="center" >结束时间</div> 
		        <div field="czstate" width="30px"  headerAlign="center" allowSort="true" align="center" >授权状态</div>  
	            <div name="remark"  field="remark" headerAlign="center" allowSort="true" width="150px;">所授权限
                      <input property="editor" class="mini-textarea" style="width:100%;" minHeight="100" />
                </div>
	        </div>
	    </div>
    </div>
  </div>
 </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
       	grid.sortBy("noticeid","desc");
       	
       	grid.ajaxType = "POST";
        grid.load();

        
		//快速查询
        function search() {
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid.load({key:key});
        };
        function onKeyEnter(e) {
            search();
        };
		// 刷新本页面
		function refreshPage(){
			location.reload();
		};
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:dd" );
        	}
            return "";
        };
        // 渲染授权状态
        function onStateRenderer(e) {
        	var Statedata = [{id: -1, text: '未授权' }, {id: 0, text: '生效' }, { id: 1, text: '到期'}, { id: 2, text: '废弃'}];
            for (var i = 0, l = Statedata.length; i < l; i++) {
                var g = Statedata[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }; 
        function add() {
		    var grid= mini.get("datagrid");
	        var win=mini.open({
		        url: "<%=path %>/grant/update.page?1=1&type=add",
		        showMaxButton: true,
		        allowResize: false, 
		        title: "授权管理", width: 600, height: 400,
		        onload: function () {
		         	var iframe = this.getIFrameEl();
		          	var data = {type:'new'};
		          	iframe.contentWindow.SetData(data);
		         },
		         ondestroy: function (action) {
		            grid.reload();
		         }
	         });
	      };
	    function edit(){
	      var rows = grid.getSelecteds();
          if (rows.length < 1) {
	       		alert("请选中一条记录");
	       		return;
          } else if (rows.length > 1) {
	       		alert("请选中单条记录");
	       		return;
          };
          //
          var row = grid.getSelected();
          var noticeid=row.noticeid;
          var aistarttime=row.starttime;
          var username=row.username;
          var endtime=row.endtime;
          var state=row.state;
          var remark=row.remark;
          
          var win=mini.open({
		        url: "<%=path %>/grant/update.page?1=1&type=update&noticeid="+ noticeid,
		        showMaxButton: true,
		        allowResize: false, 
		        title: "授权管理", width: 600, height: 400,
		        onload: function () {
		         	var iframe = this.getIFrameEl();
		         	var data = row;
		          	//var data = { action: "edit", id: id,myoperatetype:'edit'};
		            //iframe.contentWindow.SetData(data);
		         },
		         ondestroy: function (action) {
		            grid.reload();
		         }
          }); 
	    };

	    function remove() {
            var rows = grid.getSelecteds();
            if (rows.length > 1 ) {
                alert("请选中单条记录");
                return;
            } else if(rows.length < 1){
                alert("请选中一条记录");
                return;
            }
	        var row = grid.getSelected();
	        var noticeid=row.noticeid;
            if (confirm("确定撤销选中用户的授权？")) {
                $.ajax({
                    url:"<%=path %>/grant/ajax/remove.json",
                    type: 'POST',
                    data:{noticeid : noticeid},
                    success: function (text) {
                        grid.reload();
                        alert('操作成功!');
                    },
                    error: function () {
                        alert("操作失败!");
                    }
                });
            }
	            
	    };
	    
		// 请求AJAX,工具方法
		function requestAjax(url, data, successCallback, errotCallback){
			// 执行AJAX请求
			$.ajax({
			    url: url,
			    data: data,
		        type: "post",
			    success: function (message) {
			    	/*
			    	if(window["JSON"]){
			    		message = mini.decode(message) || {};
			    	} else { //IE6, IE7
	        	   		message = eval("("+ message + ")") || {};
			    	}
			    	*/
			    	message = mini.decode(message) || {};
			   		if(successCallback){
			    	   successCallback.call(window, message);
			   		}
	            	return false;
			    },
			    error: function (jqXHR, textStatus, errorThrown) {
			    	//把错误吃了
			       if(errotCallback){
			    	   errotCallback.apply(window, arguments);
			       } else {
			    	   alert("操作失败!");
			       };
			    }
			});
		};
    </script>
</body>
</html>