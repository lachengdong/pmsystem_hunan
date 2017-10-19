<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>继续保外就医或收监</title>
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
<body onload="init('${menuid }');">
      <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	  <input name="tempid" id="tempid" class="mini-hidden" value="SZXF_JXBWHSJWTH"/>
               	 <input id="crimid" name="crimid" class="mini-hidden" value="${crimid }" />
           <table >
              <tr>
               	 <td style="width:100%;">
              		 <a class="mini-button" style="display:none;" id="11875" iconCls="icon-add" plain="true" onclick="add(11875)">新增</a>
<!--              		 <a class="mini-button" style="display:none;" id="11876" iconCls="icon-remove" plain="true" onclick="shanchu()">批量删除</a>-->
              		 <a class="mini-button" iconCls="icon-download" plain="true" style="display:none;" id="12871" onclick="saveNext()">下一个</a>
                	<a class="mini-button" iconCls="icon-close"  plain="true" onclick="onCancel();" >关闭</a>
                	<span style="padding-left:40px;">罪犯编号：${crimid}</span>
         			<span style="padding-left:40px;">姓名：${name}</span>
               	 </td>
                 <td style="white-space:nowrap;">
<!--		             <input class="mini-textbox" id="key" class="mini-textbox" emptyText="文书简介"  onenter="onKeyEnter"/>-->
<!--		             <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>-->
					 <!-- 操作说明 -->
			 		 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a> 
                 </td>
              </tr>
		  </table>
     </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="getLetterOfAuthorizationContinuingMedicalParoleList.action?1=1&tempid=SZXF_JXBWHSJWTH&crimid=${crimid}" multiSelect="true" >
	        <div property="columns">
<!--	        	<div type="checkcolumn" width="15px"></div> -->
	    		<div field="introduction" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="departid" headerAlign="center"  align="center" allowSort="true" width="40px;">组织机构</div>
	    		<div field="opid" headerAlign="center"  align="center" allowSort="true" width="40px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="40px">操作时间</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="80px">操作</div>
			</div>             
	    </div>
    </div>
   
<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("optime", "asc");
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        function onActionRenderer(e) {
	       var s = document.getElementById("middlebtns").value;
	        return s;
	    }
	    //新增
	    function add(menuid){
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "letterOfAuthorizationContinuingMedicalParoleAdd.action?1=1&tempid="+tempid+"&crimid="+crimid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "继续保外就医或收监", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text) {
                    grid.reload();
                }
            });
	    }
        //查看
		function chakan() {
		    var menuid="11877";
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "letterOfAuthorizationContinuingMedicalParoleLook.action?1=1&docid="+row.docid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "继续保外就医或收监", width: 900, height: 500,
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
			var row = grid.getSelected();//获取选中记录
			var crimid = row.crimid;
	    	mini.open({
                url: "letterOfAuthorizationContinuingMedicalParoleLook.action?1=1&docid="+row.docid+"&crimid="+crimid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "继续保外就医或收监", width: 900, height: 500,
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
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "deleteLetterOfAuthorizationContinuingMedicalParole.action?1=1&docid="+id,
                        type: "post",
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        alert("操作失败!");
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
		}
		 function saveNext (){
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "letterOfAuthorizationContinuingMedicalParoleList.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	onCancel();
	        }
	    }
		//返回到罪犯处理页面
     function onCancel(){  
        url = "letterOfAuthorizationContinuingMedicalParole.action?1=1";
        self.location.href=url;          
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