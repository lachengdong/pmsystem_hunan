<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>纠正违法通知书</title>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path %>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title></title>
		<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }     
    </style>
</head>
<body onload="init('10101');">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    	<input name="tempid" id="tempid" class="mini-hidden" value="SZXF_JZWFTZS"/>
        <table>
            <tr>
                <td style="width:100%;">
                   	<a class="mini-button" iconCls="icon-add" id="10101_01" plain="true" onclick="add('10101_01')">新增</a>
                   	<a class="mini-button" iconCls="icon-remove" id="10101_04" plain="true" onclick="deleteBatch()">批量删除</a>
                </td>
                <td style="white-space:nowrap;">
		 			<input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="文书简介" style="width:130px;" onenter="onKeyEnter"/>   
                    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">快速查询</a>
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
            </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	url="getCorrectIllegalNoticeTableList.action?1=1&tempid=SZXF_JZWFTZS" idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="15px"></div>
	    	    <div field="introduction" headerAlign="center" align="left" allowSort="true" width="100px;">文书简介</div>
	    	    <div field="departid" headerAlign="center" align="center" allowSort="true" width="30px;">部门</div>
	    	    <div field="opid" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    	    <div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>             
               	<div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.sortBy("docid", "desc");
        grid.load();
        
        function onActionRenderer() {
			var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
		 	return s;
		}
	 	function add(menuid){
	 	    var tempid = mini.get("tempid").getValue();
	 		mini.open({
	 	        url: "correctIllegalNoticeAdd.action?1=1&tempid=SZXF_JZWFTZS"+"&menuid="+menuid,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "纠正违法通知书", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new"};
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	grid.reload();
	 	        }
	 	    });	
 	   }
 	   //查看
		function chakan(menuid) {
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "correctIllegalNoticeLook.action?1=1&tempid="+tempid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "纠正违法通知书", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"show" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text){
                    grid.reload();
                }
            });
		}
		//修改
		function xiugai(menuid) {
			var tempid = mini.get("tempid").getValue();
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "correctIllegalNoticeLook.action?1=1&docid="+row.docid+"&menuid="+menuid+"&tempid="+tempid,
                showMaxButton: true,
             	allowResize: false, 
                title: "纠正违法通知书", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    grid.reload();
                }
            });
		}
		//删除
        function shanchu(){
        	var row = grid.getSelected();//获取选中记录
            if (row) {
                if (confirm("确定删除选中记录？")) {
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "correctIllegalNoticeDelete.action?1=1&docid="+row.docid,
                        type: "post",
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        	grid.reload();
                        }
                    });
                }
            } else {
                alert(confirmMessage);
            }
		}
		//批量删除
        function deleteBatch(){
        	var rows = datagrid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    datagrid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "correctIllegalNoticeDelete.action?1=1&docid="+id,
                        type: "post",
                        success: function (text) {
                            datagrid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        	datagrid.reload();
                        }
                    });
                }
            } else {
                alert(confirmMessages);
            }
		}
		//快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("docid","desc");
            grid.load({ key: key });
        }
	</script>
</body>
</html>

      

