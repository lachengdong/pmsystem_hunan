<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path %>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>考核奖惩公共页面</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
</head>
<body >
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
            <table >
               <tr>
                <td style="width:100%;">
                	${topstr}
                	<span style="padding-left:40px;">罪犯编号：${crimid}</span>
         			<span style="padding-left:40px;">姓名：${name}</span>
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
	    url="getCriminalKhjcBaseDoc.action?1=1&crimid=${crimid}&tempid=${tempid}"  idField="" multiSelect="true" allowAlternating="true" showLoading="true">
	        <div property="columns">
<!--	       		<div type="checkcolumn" width="15px"></div>-->
	    		<div field="docid" headerAlign="center" align="left" allowSort="true" width="150px;"  visible="false">主键</div>
	    		<div field="content" headerAlign="center" align="left" allowSort="true" width="150px;">文书简介</div>
	    		<div field="createmender" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="createtime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="departid" headerAlign="center"  align="center" allowSort="true"width="40px">所在监区</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="60px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript"> 
    	mini.parse();
    	var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("docid", "asc");
        
        function onActionRenderer() {
         	//var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
         	var s = '${middlestr}';
    	    return s;
         }
        function add(menuid,tempid){
        	var crimid = mini.get("crimid").getValue();
       		var name=mini.get("name").getValue();
	 		mini.open({
	 	        url: "zhuanXiangJiangFenAdd.page?1=1&tempid="+ tempid +"&crimid="+ crimid +"&name="+name+"&menuid="+menuid,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "新增", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	   });	
 	   }
 	   //修改
		function xiugai(menuid) {
			var row = datagrid.getSelected();//获取选中记录
			var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "khjcFlowBaseDocLook.action?1=1&docid="+row.docid+"&crimid="+crimid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "修改", width: 900, height: 500,
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
                url: "criminalInvestigationAssessmentLetterLook.action?1=1&docid="+row.docid+"&crimid="+crimid+"&menuid=${menuid}",
                showMaxButton: true,
             	allowResize: false, 
                title: "罪犯调查评估委托函", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"show" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text){
                    datagrid.reload();
                }
            });
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
                    datagrid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "criminalInvestigationAssessmentLetterDelete.action?1=1&docid="+id,
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
		 function saveNext (){
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "khjcListPage.page?1=1&id="+id+"&menuid=${menuid}&idname="+idname+"&fathermenuid=${fathermenuid}";
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	onCancel();
	        }
	    }
		//返回到罪犯处理页面
     function onCancel(){  
        url = "khjcCriminalChoosePage.action?1=1&menuid=${fathermenuid}";
        self.location.href=url;          
     }
</script>
</body>
</html>


    