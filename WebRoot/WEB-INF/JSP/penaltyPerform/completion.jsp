<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>执行期满考察表</title>
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
      			<input name="tempid" id="tempid" class="mini-hidden" value="BWJY_ZFZYJWZXQMKCB"/>
	  			 <input name="name" id="name" class="mini-hidden" value="${name}"/>
               	 <input id="crimid" name="crimid" class="mini-hidden" value="${crimid }" />
               	 <input id="tempid" name="tempid" class="mini-hidden" value="${tempid }" />
               	 <input id="menuid" name="menuid" class="mini-hidden" value="${menuid}"/>
               	 <input id="sortOrder" name="sortOrder" class="mini-hidden" value="asc" />
           <table >
              <tr>
               	 <td style="width:100%;">
              		 <a class="mini-button" id="11511" iconCls="icon-add" plain="true" onclick="add('11511')">新增</a>
              		 <a class="mini-button" id="11512" iconCls="icon-remove" plain="true" onclick="shanchu()">批量删除</a>
              		 <a class="mini-button" iconCls="icon-close" plain="true" onclick="back()">关闭</a>
              		 <span style="padding-left:40px;">罪犯编号：${crimid}</span>
 			    	 <span style="padding-left:40px;">罪犯姓名：${name}</span>
               	 </td>
                 <td style="white-space:nowrap;">
		             <input class="mini-textbox" id="key" class="mini-textbox" emptyText="文书简介"  onenter="onKeyEnter"/>
		             <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
					 <!-- 操作说明 -->
			 		 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a> 
                 </td>
              </tr>
		  </table>
     </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="getCompletionTableList.action?1=1&tempid=BWJY_ZFZYJWZXQMKCB&crimid=${crimid }" multiSelect="true" >
	        <div property="columns">
	        	<div type="checkcolumn" width="15px"></div> 
	    		<div field="introduction" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="departid" headerAlign="center"  align="center" allowSort="true" width="40px;">组织机构</div>
	    		<div field="opid" headerAlign="center"  align="center" allowSort="true" width="40px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="40px">操作时间</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="80px">操作</div>
			</div>             
	    </div>
	    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
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
       function onActionRenderer() {
        	var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
	     	return s;
        }
	    //新增
	    function add(menuid){
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
	    	var name=mini.get("name").getValue();
	    	mini.open({
                url: "completionAdd.action?1=1&tempid="+tempid+"&crimid="+crimid+"&name="+name+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "执行期满考察表", width: 900, height: 500,
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
		    var row = grid.getSelected();
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
			var name=mini.get("name").getValue();
	    	mini.open({
                url: "completionAdd.action?1=1&tempid="+tempid+"&crimid="+crimid+"&name="+name+"&menuid=11515"+"&docid="+row.docid,
                showMaxButton: true,
             	allowResize: false, 
                title: "执行期满考察表", width: 900, height: 500,
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
		function xiugai() {
		var row = grid.getSelected();
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
		    var name=mini.get("name").getValue();
	    	mini.open({
                url: "completionAdd.action?1=1&tempid="+tempid+"&crimid="+crimid+"&name="+name+"&menuid=11514"+"&docid="+row.docid,
                showMaxButton: true,
             	allowResize: false, 
                title: "执行期满考察表", width: 900, height: 500,
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
                        url: "deleteCompletion.action?1=1&docid="+id,
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
		function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
         //标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            mini.get("operator").setValue(data.action); 
        }
        function back(){
         history.back();
        }
    </script>
</body>
</html>