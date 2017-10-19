<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>视频列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<title></title>
		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="white-space: nowrap;">
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="add();">上传视频</a>
					</td>
					<td style="width: 100%;">
					</td>
					<td style="white-space: nowrap;">
						<input class="mini-textbox" id="key" class="mini-textbox" emptyText="输入名称" onenter="onKeyEnter" />
						<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" url="content/getVideoList.json?1=1" idField="videoid" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20" showLoading="true">
				<div property="columns">
					<div type="checkcolumn" width="20"></div>
					<div field="moduleid" headerAlign="center" allowSort="true" align="center" >
	               		所属栏目
	               	</div>
					<div field="name" headerAlign="center" allowSort="true" align="center">
						视频名称
					</div>
					<div field="releasetime" headerAlign="center" allowSort="true" renderer="onDateRenderer" align="center">
						发布时间
					</div>
					<div field="expirationtime" headerAlign="center" allowSort="true" renderer="onDateRenderer" align="center">
						过期时间
					</div>
					<div field="opid"  headerAlign="center" allowSort="true" align="center" >
	               		上传者
	               	</div>
					<div name="action" headerAlign="center" allowSort="true" align="center" renderer="onActionRenderer" cellStyle="padding:0;">
						操作
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	mini.parse();
    var grid = mini.get("datagrid");
    grid.load();
    
    function onDateRenderer(e) {
    	if(e && e.value){
    		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
    	}
        return "";
    }

    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var videoid = record.videoid;
    	var s = '';
    	 s = s + '<a href="javascript:remove();">删除</a>';
     	return s;
    }
    
    function add() {
    	mini.open({
	        url: "<%=path %>/JSP/content/videoAddPage.jsp",
	        showMaxButton: true,
	        allowResize: false,
	        title: "上传文件", width: 500, height:330 ,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = { operatetype: "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}

  	
  	function remove() {
       var row = grid.getSelected();
       if (row) {
           if (confirm("确定删除选中记录？")) {
               $.ajax({
                   url: "content/deleteVideo.json?moduleid="+row.moduleid+"&videoid="+row.videoid,
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
  	
    function search() {
         var key = mini.get("key").getValue();
         grid.load({ key: key });
    }
      
    function onKeyEnter(e) {
         search();
    }
</script>