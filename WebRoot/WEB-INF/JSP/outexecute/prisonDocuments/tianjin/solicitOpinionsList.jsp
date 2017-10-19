<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯保外就医取保书（上海）</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }   
    </style>
</head>

<body onload="init('${menuid}');">
     <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
     	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
   	 	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
        <input id="crimid" class="mini-hidden" value="${crimid}"/>
        <input id="name" name="name" class="mini-hidden" value="${name}"/>
          <table >
             <tr>
              <td style="width:100%;">
              	<a class="mini-button" style="display:none;" id="12379" iconCls="icon-add" plain="true" onclick="add('12379')">新增</a>
              	<a class="mini-button" style="display:none;" id="12381" iconCls="icon-remove" plain="true" onclick="shanchu()">批量删除</a>
          		<a class="mini-button" plain="true"  iconCls="icon-close" onclick="close()">关闭</a>
              	<!-- <a class="mini-button" style="display:none;" id="11841" iconCls="" plain="true" class="mini-hidden" onclick="chooseNext();">下一个</a> -->
         			<span style="padding-left:40px;">罪犯编号：${crimid}</span>
         			<span style="padding-left:40px;">姓名：${name}</span>
              </td>
             </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  
	    allowResize="false" url="<%=path%>/shBailBook/getBailBookList.json?1=1&tempid=${tempid}&crimid=${crimid}"  
	    idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="15px"></div>
	    		<div field="introduction" headerAlign="center" align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="departid" headerAlign="center" align="center" allowSort="true" width="30px;">部门</div>
	    		<div field="opid" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="40px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript"> 
		mini.parse();
		var datagrid = mini.get("datagrid");
		datagrid.load();
		datagrid.sortBy("optime", "desc");
		function onActionRenderer() {
	  		var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
			return s;
	   }
     function add(menuid){
        var crimid=mini.get("crimid").getValue();
        var tempid=mini.get("tempid").getValue();
  		mini.open({
  	        url: "shBailBook/bailBookAdd.page?1=1&tempid="+tempid+"&crimid="+crimid+"&menuid="+menuid,
  	        showMaxButton: true,
  	        allowResize: false,
  	        title: "保外就医取保书", width: 900, height: 500,
  	        onload: function () {
  	            var iframe = this.getIFrameEl();
  	            var data = { action: "new"};
  	            iframe.contentWindow.SetData(data);
  	        },
  	        ondestroy: function (action) {
  	        	datagrid.reload();
  	        }
  	    });	
  	 }
      
     // 渲染日期
     function onDateRenderer(e) {
     	if(e && e.value){
     		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
     	}
         return "";
     }
   //删除
     function shanchu(){
     	var rows = datagrid.getSelecteds();
         if (rows.length > 0) {
             if (confirm("确定删除选中记录？")) {
                 var ids = [];
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     ids.push(r.docid);
                 }
                 var id = ids.join(',');
                 $.ajax({
                     url: "<%=path%>/shBailBook/deleteBailBook.action?1=1&docid="+id,
                     type: "post",
                     success: function (text) {
                 		datagrid.reload();
                     },
                     error: function () {
                     	alert("操作失败!");
                     }
                 });
             }
         } else {
             alert("请至少选中一条记录！");
         }
 	}
 	 //修改
 	function xiugai(menuid) {
 		var row = datagrid.getSelected();//获取选中记录
 		var crimid = mini.get("crimid").getValue();
     	mini.open({
             url: "shBailBook/editBailBook.page?1=1&docid="+row.docid+"&menuid="+menuid,
             showMaxButton: true,
          	 allowResize: false, 
             title: "保外就医取保书", width: 900, height: 500,
             onload: function () {
                 var iframe = this.getIFrameEl();
                 var data = { action:"edit" };
                 iframe.contentWindow.SetData(data);
             },
             ondestroy: function (action){
             	datagrid.reload();
             }
         });
 	} 
 	  //查看
 	function chakan(menuid) {
 		var row = datagrid.getSelected();//获取选中记录
 		var crimid = mini.get("crimid").getValue();
     	mini.open({
             url: "shBailBook/editBailBook.page?1=1&docid="+row.docid+"&menuid="+menuid,
             showMaxButton: true,
          	 allowResize: false, 
             title: "保外就医取保书", width: 900, height: 500,
             onload: function () {
                 var iframe = this.getIFrameEl();
                 var data = { action:"edit" };
                 iframe.contentWindow.SetData(data);
             },
             ondestroy: function (action){
             	datagrid.reload();
             }
         });
 	}
 	 //关闭返回到罪犯处理页面
    function close(){
        url = "/pmsys/shBailBook/bailBookChoose.page?1=1";
        self.location.href=url;
    }
</script>
</body>
</html>