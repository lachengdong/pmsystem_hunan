<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>法院案件选择</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
    	</style>
    <script type="text/javascript">
	</script>
</head>
<body>
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table style="width:100%">
               <tr>
                <td style="width:33%">
	      			<a class="mini-button" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	      			<a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit();">修改</a>
	      			<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove();">批量删除</a>
                </td>
                <td style="white-space:nowrap;" align="right">
                	<input name="key" id="key" class="mini-textbox" emptyText="选择名称" allowInput="true" />
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick=""></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" url="" allowResize="false" idField="" multiSelect="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="25"></div>
	        		<div field="SN" name="SN" width="40" headerAlign="center" align="center" >
               			序号
               		</div>
               		<div field="NAME" name="NAME" headerAlign="center" align="center" >
               			方案名称
               		</div>
               		<div field="CHARACTERISTIC" name="CHARACTERISTIC" headerAlign="center" align="center" >
               			从严从宽
               		</div>
               		<div field="CRIMETYPE" name="CRIMETYPE" headerAlign="center" align="center" >
               			罪名
               		</div>
           			<div field="INPRISON" name="INPRISON" headerAlign="center" align="center" >
               			所在监狱
               		</div>
               		<div field="DUTYLEVEL" name="DUTYLEVEL" headerAlign="center"  align="center" >
               			职务级别
               		</div>
               		<div field="COURT" name="COURT" headerAlign="center" align="center" >
               			办案法院
               		</div>
               		<div field="RECEIVESTARTSTR" name="RECEIVESTARTSTR" dateFormat="yyyy-MM-dd" headerAlign="center" align="center" >
               			收案开始
               		</div>
               		<div field="RECEIVEENDSTR" name="RECEIVEENDSTR" dateFormat="yyyy-MM-dd" headerAlign="center" align="center" >
               			收案结束
               		</div>
               		<div field="CRIMENAME" name="CRIMENAME" headerAlign="center" align="center" >
               			罪犯姓名
               		</div>
               		<div field="ORIGINALSENTENCE" name="ORIGINALSENTENCE" headerAlign="center" align="center" >
               			原判刑期
               		</div>
	        </div>
	    </div>
    </div>
    
</body>
</html>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid");
	var url = "ajaxGetCaseAttentionChoiceList.action?1=1";
	grid.setUrl(url);
	grid.load();
	function add() {
    	mini.open({
	        url: "commutationParole/toCaseAttentionChoiceAddPage.action?1=1",
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 385, height: 400,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = {action: "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	function edit(){
	  var grid= mini.get("datagrid");
      var row = grid.getSelected();
      if (row) {
      		var cacid = row.CACID;
        	mini.open({
    	        url: "commutationParole/toCaseAttentionChoiceAddPage.action?id="+cacid ,
    	        showMaxButton: true,
    	        allowResize: false,
    	        title: "编辑", width: 385, height: 400,
    	        onload: function () {
    	            var iframe = this.getIFrameEl();
    	            var data = {action:"edit"};
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
    	var rows = grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.CACID);
                }
                var id = ids.join(',');
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "deleteCaseAttentionChoice.action?id="+id,
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
    function search(){
        var key = mini.get("key").getValue();
        grid.load({ key: key });
    }
</script>
