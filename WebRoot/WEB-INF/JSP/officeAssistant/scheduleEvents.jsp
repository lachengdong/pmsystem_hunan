<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>日程安排日程事件</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width:100%;">
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
					</td>
					<td style="white-space: nowrap;">
						<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入标题" onenter="onKeyEnter" />
						<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('')"></a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" idField="noticeid"
				 url="ajaxGetDataById.action" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20" showLoading="true">
				<div property="columns">
					<div type="checkcolumn" width="15px"></div>
					<div field="noticeid" visible="false" headerAlign="center" allowSort="true" align="center">日程安排ID</div>
					<div field="starttime" width="30px" headerAlign="center" renderer="onDateRenderer" allowSort="true" align="center">开始时间</div>
					<div field="endtime" width="30px" headerAlign="center" renderer="onDateRenderer" allowSort="true" align="center">结束时间</div>
					<div field="title" width="40px" headerAlign="center" allowSort="true" align="center">标题</div>
					<div field="content" width="120px" headerAlign="center" allowSort="true" align="center">内容</div>
					<div name="action" width="40px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.sortBy("endtime","desc");
      	grid.load();
      	
      	// 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm" );
        	}
            return "";
        }
        function onActionRenderer(e) {
            var s ="";
            s=s+'<a class="New_Button" href="javascript:view()">查看</a>&nbsp;&nbsp;';
            s=s+'<a class="New_Button" href="javascript:edit()">修改</a>&nbsp;&nbsp;';
	        s=s+'<a class="New_Button" href="javascript:remove()">删除</a>&nbsp;&nbsp;';
            return s;
	    }
	    function add() {
	    	mini.open({
		        url: "toScheduleAddPage.action?1=1",
		        showMaxButton: true,
		        allowResize: false,
		        title: "新增", width: 600, height: 320,
		        onload: function () {
		            var iframe = this.getIFrameEl();
		            var data = { action : "new"};
		            iframe.contentWindow.SetData(data);
		        },
		        ondestroy: function (action) {
		        	grid.reload();
		        }
		    });
		}
	   function edit() {
          var row = grid.getSelected();
          if (row) {
              var win=mini.open({
                  url: "toScheduleAddPage.action?1=1",
                  showMaxButton: true,
                  allowResize: false, 
                  title: "编辑", width: 600, height: 320,
                  onload: function () {
                      var iframe = this.getIFrameEl();
           		var data = { action : "edit",noticeid : row.noticeid};
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
	  function remove() {
         var row = grid.getSelected();
         if (row) {
             if (confirm("确定删除选中记录？")) {
                 $.ajax({
                     url: "deleteSchedule.action?noticeid="+row.noticeid,
                     success: function (text) {
                         grid.reload();
                     },
                     error: function () {
                    		alert("操作失败");
                    		return;
                     }
                 });
             }
         } else {
             alert("请选中一条记录");
         }
     }
     function view() {
        var row = grid.getSelected();
        if (row) {
            var win=mini.open({
                url: "toScheduleAddPage.action?1=1",
                showMaxButton: true,
                allowResize: false, 
                title: "查看", width: 600, height: 320,
                onload: function () {
                	var iframe = this.getIFrameEl();
         		var data = { action : "view",noticeid : row.noticeid};
         		iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                }
            });
        }else {
             alert("请选中一条记录");
        }
    }
    //快速查询
	function onKeyEnter(e) {
        search();
    }        
    function search() {
        var key = mini.get("key").getValue();
        grid.load({ key: key });
    }
    </script>
</body>
</html>
