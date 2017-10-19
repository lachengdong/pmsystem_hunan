<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<title>减刑类型描述page</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="/pmsys/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   	<script src="/pmsys/scripts/boot.js" type="text/javascript"></script>
   	<script src="/pmsys/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   	<link href="/pmsys/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 

    <style type="text/css">
	    html, body{
	        font-size:12px;padding:0;margin:0;border:0;height:100%;overflow:hidden; 
	    }
    </style>
</head>

  <body>
   <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
                <td style="width:100%;">
                	${topstr}
                </td>
                <td style="white-space:nowrap;">
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('9924')"></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
	    url="ajaxSentenceType.json"  idField="" multiSelect="true" allowAlternating="true" showLoading="true">
	        <div property="columns">
<!--	       		<div type="checkcolumn" width="15px"></div>-->
	    		<div field="name" headerAlign="center" align="center" allowSort="true" width="80px;">减刑描述</div>
	    		<div field="senid" headerAlign="center" align="center" allowSort="true" width="80px;">描述id</div>
	    		<div field="stime" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer" width="80px;">适用起日</div>
	    		<div field="etime" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer" width="80px;">适用止日</div>
	    		<div field="opid" headerAlign="center"  align="center" allowSort="true" width="80px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="80px">操作时间</div>
	    		<div field="departname" headerAlign="center"  align="center" allowSort="true"width="80px">所在监狱</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="60px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript"> 
    	mini.parse();
    	var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("syear", "asc");
        
        function onActionRenderer() {
         	//var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
         	var s = '${middlestr}';
    	    return s;
         }
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        function modifysentencetype(menuid,type){
        	var row = datagrid.getSelected();
      		var title = "";
      		var senid = "";
      		if(row) senid = row.senid;
      		if(type == 1) title = "新增";
      		else if(type == 2) title = "修改";
      		else title = "查看";//type = 3
	 		mini.open({
	        url: "modifySentenceTypePage.page?1=1",
	        showMaxButton: true,
	        allowResize: false,
	        title: title, width: 430, height: 320,
	        onload: function () {
	 	           var iframe = this.getIFrameEl();
	 	           var data = {type:type,senid:senid};
	 	           iframe.contentWindow.SetData(data);
 	        },
 	        ondestroy: function (action) {
 	        	datagrid.reload();
 	        }
    	});
 	   }
 	   
		//删除
        function shanchu(resid){
        	var rows = datagrid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.senid);
                    }
                    var id = ids.join(',');
                    datagrid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "ajaxByRemoveSentence.json?1=1&senid="+id,
                        data: {senid:id,resid:resid},                               
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
                alert("请选中一条记录");
            }
		}

</script>
</body>
</html>