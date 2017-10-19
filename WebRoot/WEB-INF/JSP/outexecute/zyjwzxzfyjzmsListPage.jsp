<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>暂予监外执行罪犯移交证明书</title>
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
<body onload="init('${menuid}');">
      <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
        <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <input name="tempid" id="tempid" class="mini-hidden" value="JWZX_ZYJWZXZFYJZMS"/>
           <table >
              <tr>
               	 <td style="width:100%;">
              		 <a class="mini-button" iconCls="icon-add" plain="true" style="display:none;" id="11821"   onclick="add('11821')">新增</a>
              		 <a class="mini-button" iconCls="icon-download" plain="true" style="display:none;" id="12865" onclick="saveNext()">下一个</a>
                	 <a class="mini-button" iconCls="icon-close"  plain="true" onclick="onCancel();" >关闭</a>
                	 <span style="padding-left:40px;">罪犯编号：${crimid}</span>
         			 <span style="padding-left:40px;">姓名：${name}</span>
               	 </td>
                 <td style="white-space:nowrap;">
					 <!-- 操作说明 -->
			 		 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a> 
                 </td>
              </tr>
		  </table>
     </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="getZyjwzxzfyjzmsList.action?1=1&tempid=JWZX_ZYJWZXZFYJZMS&crimid=${crimid }" multiSelect="true" >
	        <div property="columns">
				<div field="introduction" headerAlign="center" align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="departid" headerAlign="center" align="center" allowSort="true" width="30px;">所属部门</div>
	    		<div field="opid" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="40px">操作</div> 
			</div>             
	    </div>
    </div>
	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("docid", "desc");
        
        function onActionRenderer() {
        	var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
	     	return s;
        }
        
	    //新增
	    function add(menuid){
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "zyjwzxzfyjzmsAdd.action?1=1&tempid="+tempid+"&crimid="+crimid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "暂予监外执行罪犯移交证明书", width: 900, height: 500,
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
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "zyjwzxzfyjzmsAdd.action?1=1&docid="+row.docid,
                showMaxButton: true,
             	allowResize: false, 
                title: "暂予监外执行罪犯移交证明书", width: 900, height: 500,
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
	    	mini.open({
                url: "zyjwzxzfyjzmsAdd.action?1=1&docid="+row.docid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "暂予监外执行罪犯移交证明书", width: 900, height: 500,
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
                        url: "deleteZyjwzxzfyjzms.action?1=1&docid="+row.docid,
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
		 	var menuid = mini.get("menuid").getValue();
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "toZyjwzxzfyjzmsListPage.action?1=1&id="+id+"&menuid="+menuid+"&idname="+idname;
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	onCancel();
	        }
	    }
	    function onCancel(){  
       		 url = "toZyjwzxzfyjzmsChoosePage.action?1=1";
        	 self.location.href=url;          
     	}
    </script>
</body>
</html>