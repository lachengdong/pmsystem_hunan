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
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>从宽从严列表</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
    	</style>
    <script type="text/javascript">
	</script>
</head>
<body>
	
	   <input id="punid" name="punid" value="${record.punid}" class="mini-hidden" />
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table style="width:100%">
               <tr>
                <td style="width:33%">
	      			<a class="mini-button" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	      			<a class="mini-button" iconCls="icon-undo" plain="true" onclick="back();">返回</a>
                </td>
                <td style="width:33%" align="center">
	      			犯罪时间：<b>${record.NAME}</b>
                </td>
                <td style="white-space:nowrap;" align="right">
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick=""></a>
                    <script src="<%=path %>/scripts/loadaip2.js"></script>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid" url="" allowResize="false" idField="" 
	    multiSelect="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="false">
	        <div property="columns">
	        		<div field="SCHEMETYPEVAL" name="SCHEMETYPE" width="150" headerAlign="center" align="center" >
               			方案类型
               		</div>
               		<div field="NAME" name="CRIMINALTYPE" headerAlign="center" align="center" >
               			罪犯类型
               		</div>
               		<div field="LSSINTERVALPERIOD" name="LSSINTERVALPERIOD" renderer="onNoRenderer" headerAlign="center" align="center" >
               			间隔时间
               		</div>
           			<div field="LSSSTARTPERIOD" name="LSSSTARTPERIOD" headerAlign="center" align="center" >
               			起始时间
               		</div>
               		<div field="RANGESTART" name="RANGESTART" headerAlign="center" align="center" >
               			减刑幅度
               		</div>
               		<div field="REMARK" name="REMARK" headerAlign="center" align="center" >
               			备注
               		</div>
                 	<div field="REFID" name="action" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>               		
	        </div>
	    </div>
    </div>
</body>
</html>
<script type="text/javascript">

	mini.parse();
	var grid = mini.get("datagrid");
	var punid = mini.get("punid").getValue();
	var url = "ajaxGetWideAndThinschemeList.action?punid="+punid;
	grid.setUrl(url);
	grid.load();

	function onActionRenderer(e){
		var grid = e.sender;
        var record = e.record;
        var lssid = record.LSSID;
        var s = '';
        s = s + '<a href="javascript:edit(\''+lssid+'\')">修改</a>';
        s = s + '&nbsp;&nbsp';
        s = s + '<a href="javascript:remove(\''+lssid+'\')">删除</a>';
        return s;
	}
	    
	function add() {
		var punid = mini.get("punid").getValue();
    	mini.open({
	        url: "toWideAndThinschemeAddPage.action?1=1&punid=" + punid,
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 500, height: 300,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = {action: "new",punid:punid};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	
	function edit(lssid){
		var punid = mini.get("punid").getValue();
		mini.open({
	        url: "toWideAndThinschemeEditPage.action?lssid="+lssid ,
	        showMaxButton: true,
	        allowResize: false,
	        title: "编辑", width: 500, height: 300,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = {action:"edit",punid:punid,lssid:lssid};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	
	function remove(lssid) {
		if (confirm("确定删除选中记录？")) {
            $.ajax({
                url: "deleteWideAndThinscheme.action?lssid="+lssid,
                success: function (text) {
                    grid.reload();
                },
                error: function () {
               		alert("操作失败");
               		return;
                }
            });
        }
    }
    
    function back(){
        history.back(-1);
    }
</script>
