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
		<title>减刑参照列表</title>
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
	      			刑期：<b>${record.NAME}</b>
                </td>
                <td style="white-space:nowrap;" align="right">
                	<input name="key" id="key" class="mini-textbox" emptyText="参照名称" allowInput="true" />
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick=""></a>
                    <script src="<%=path %>/scripts/loadaip2.js"></script>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid" url="" allowResize="false" idField="" 
	    	multiSelect="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="false">
	        <div property="columns">
	        		<div field="NAME" name="NAME" width="150" headerAlign="center" align="center" >
               			参照名称
               		</div>
               		<div field="REWARDNAME" name="REWARDNAME" headerAlign="center" align="center" >
               			奖励类型
               		</div>
               		<div field="NO" name="NO" renderer="onNoRenderer" headerAlign="center" align="center" >
               			次数/对应幅度
               		</div>
           			<div field="ESTNUM" name="ESTNUM" headerAlign="center" align="center" >
               			减刑幅度
               		</div>
               		<div field="SUGGEST" name="SUGGEST" headerAlign="center" align="center" >
               			建议幅度
               		</div>
               		<div field="ISINTERVAL" name="ISINTERVAL" renderer="onIntervalRenderer" headerAlign="center" align="center" >
               			是否受幅度限制
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
	var url = "ajaxGetCommutationReferenceList.action?punid="+punid;
	grid.setUrl(url);
	grid.on("load", function () {
        grid.mergeColumns(["NAME", "ESTNUM", "SUGGEST", "ISINTERVAL","action"]);
    });
	grid.load();
	
	function onActionRenderer(e){
		var grid = e.sender;
        var record = e.record;
        var refid = record.REFID;
        var mergeid = record.MERGEID;
        var remark = record.REMARK;
        var s = '';
        s = s + '<a href="javascript:reward(\''+refid+'\',\''+remark+'\')">设置明细</a>';
        s = s + '&nbsp;&nbsp';
        s = s + '<a href="javascript:edit(\''+refid+'\')">修改</a>';
        s = s + '&nbsp;&nbsp';
        s = s + '<a href="javascript:remove(\''+refid+'\')">删除</a>';
        return s;
	}
	
	var dataArray = [{ id: 1, text: '是' }, { id: 0, text: '否'}];
    function onIntervalRenderer(e) {
        for (var i = 0, l = dataArray.length; i < l; i++) {
            var g = dataArray[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }
    
    function onNoRenderer(e){
    	var str = e.value;
      	var reg = /-1/g;
        var arr  = str.match(reg);
        
    	if (null!=arr && arr.length == 2) {
    		return "-";
        }else if (null!=arr && arr.length == 1) {
         	if(e.value.indexOf("-1")==0){
             	return e.value.replace("-1~","")+"次以下";
             }else{
             	return e.value.replace("~-1次","次以上");
             }
        }else{
            if(str.indexOf("个月")>0){
            	return str;
            }
			var sno = e.value.substring(0,e.value.indexOf("~"));
			var eno = e.value.substring(e.value.indexOf("~")+1,e.value.length-1);
			if(sno == eno){
				return sno+"次";
			}else{
				return sno + "~" + eno + "次";
			}
        }
       	return e.value;	
        
    }
    
	function add() {
		var punid = mini.get("punid").getValue();
    	mini.open({
	        url: "toCommutationReferenceAddPage.action?punid="+punid,
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 500, height: 300,
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
	
	function edit(refid){
		mini.open({
	        url: "toCommutationReferenceEditPage.action?refid="+refid ,
	        showMaxButton: true,
	        allowResize: false,
	        title: "编辑", width: 500, height: 300,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = {action:"edit"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	
	function remove(refid) {
		if (confirm("确定删除选中记录？")) {
            $.ajax({
                url: "deleteCommutationReference.action?id="+refid,
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
    
    function reward(refid,remark){
    	mini.open({
	        url: "toMergeReferenceListPage.action?refid="+refid+"&remark="+remark,
	        showMaxButton: true,
	        allowResize: false,
	        title: "奖励明细", width: 600, height: 400,
	        onload: function () {
	            
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
    }
    
    function search(){
        var key = mini.get("key").getValue();
        grid.load({ key: key });
    }
    
    function back(){
        history.back(-1);
    }
</script>
