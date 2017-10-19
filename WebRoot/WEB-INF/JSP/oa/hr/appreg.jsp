
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>应聘人员登记表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
    	    margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   		}    
    </style> 
</head>
<body onload="init('608502');">
      <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
          <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
           <table >
              <tr>
               	 <td style="width:100%;">
	               	 <a class="mini-button"  iconCls="icon-add" id="608510" plain="true" onclick="add(608510)">新增</a>
                   	 <a class="mini-button"  iconCls="icon-remove" id="608516" plain="true" onclick="deleteBatch()">批量删除</a>	               	 
               	 </td>
                 <td style="white-space:nowrap;">
		             <input class="mini-textbox" id="key" class="mini-textbox" emptyText="应聘岗位，姓名"  onenter="onKeyEnter"/>
		             <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
					 <!-- 操作说明 -->
					 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                 </td>
              </tr>
		  </table>
     </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="<%=path%>/appreg/getAppRegList.json?1=1&tempid=OA_YPRYDJ" multiSelect="true" >
	        <div property="columns">
	        	<div type="checkcolumn" width="15"></div> 	        	
	    		<div field="YPJOBS" headerAlign="center"  align="center" allowSort="true" width="80px;">应聘岗位</div>
	    		<div field="YPNAME" headerAlign="center"  align="center" allowSort="true" width="40px;">姓名</div>
	    		<div field="TBDATE" headerAlign="center"  align="center" allowSort="true" width="40px;">填表日期</div>
	    		<div field="OPID"   headerAlign="center"  align="center" allowSort="true" width="40px;">操作人</div>
	    		<div field="OPTIME" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	   			<div name="action"  headerAlign="center"  align="center" renderer="onActionRenderer" width="40px">操作</div> 
			</div>             
	    </div>
    </div>
	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        
        function onActionRenderer() {
			var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
		 	return s;
		}
	    //新增
	    function add(menuid){
	    	mini.open({
                url: "<%=path%>/appreg/appRegAdd.action?1=1&tempid=OA_YPRYDJ"+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "应聘人员登记表", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"new" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text) {
                    grid.reload();
                }
            });
	    }
        //查看
		function chakan(type) {
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url:"<%=path%>/appreg/appRegAdd.action?1=1&tempid=OA_YPRYDJ"+"&uuid="+row.UUID+"&type="+type ,
                showMaxButton: true,
             	allowResize: false, 
                title: "应聘人员登记表", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { uuid:row.UUID };
                     iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text){
                    grid.reload();
                }
            });
		}     
		 //修改
		function xiugai(menuid) {
			 var type="xiugai";
			chakan(type);
		}        
		//删除
        function shanchu(){
        	var row = grid.getSelected();//获取选中记录
            if (row) {
                if (confirm("确定删除选中记录？")) {
                     $.ajax({
                         url: "<%=path%>/appreg/deleteAppReg.action?1=1&uuid="+row.UUID,
                         type: "post",
                         success: function (text) {
                        	 alert("操作成功!");
                             grid.reload();
                         },
                         error: function () {
                         	 alert("操作失败!");
                         }
                     });
                }
            } else {
                alert(confirmMessage);
            }
		}
		//批量删除
        function deleteBatch(){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    var uuids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        uuids.push(r.UUID);
                    }
                    var uuid = uuids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "<%=path%>/appreg/deleteAppReg.action?1=1&uuid="+uuid,
                        type: "post",
                        success: function (i) {
	                		alert("删除成功!");
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
                    });
                }
            } else {
                alert(confirmMessages);
            }
		}
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