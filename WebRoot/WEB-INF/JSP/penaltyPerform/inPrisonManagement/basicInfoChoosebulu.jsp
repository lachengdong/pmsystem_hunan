<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitionbatchReviewal.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
	<title>监舍、床位号、管理等级变动</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  	<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
  	<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; 
	    }     
    </style>
</head>
<body onload="init('${menuid}');">
	<div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
     	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
              <tr>
                <td style="width:100%;">
               	 	<a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true" style="width:90px">批量保存</a>
               	 	<a class="mini-button" iconCls="icon-save" onclick="savePrison()" plain="true" style="width:120px">批量设置监舍</a>
               	 	<input id="prisons" class="mini-textbox" vtype="maxLength:50;" emptyText="请填写监舍编号" style="width:100px;"/>
                </td>
                <td style="white-space:nowrap;">
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
              </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" 
	    	class="mini-datagrid" allowResize="false" url="<%=path%>/basicInfo/getBasicInfoChoosebulu.json?1=1" idField="" multiSelect="true" allowAlternating="true" allowCellSelect="true" 
	    	sizeList="[10,20,50,100]" pageSize="20" showLoading="true" allowSortColumn="true" allowCellEdit="true" multiSelect="true" allowResize="true" editNextOnEnterKey="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="15"></div>
              	<div type="indexcolumn" width="15" headerAlign="center">序号</div>
              	<div field="applyid" width="25" headerAlign="center" align="center" allowSort="true">罪犯编号</div>
              	<div field="fileno" width="20" headerAlign="center" align="center" allowSort="true">档案号</div>
				<div field="applyname" width="30" headerAlign="center" align="center" allowSort="true">姓名</div>
				<div field="age" width="20" headerAlign="center" align="center" allowSort="true">年龄</div>
				<div field="charge" width="30" headerAlign="center" align="left" allowSort="true">罪名</div>
				<div field="orgname" width="30" headerAlign="center" align="center" allowSort="true">所在监区</div>
	    		<div field="prison" headerAlign="center"  align="center" allowSort="true" width="25">监舍
	    			<input property="editor" name="prison" class="mini-textbox" style="width:100%;" minWidth="40" />
	    		</div>
				<div field="berth" headerAlign="center"  align="center" allowSort="true" width="25">床位号
	    			<input property="editor" name="berth" class="mini-textbox" style="width:100%;" minWidth="40" />
	    		</div>
	    		<div field="chargeclass" headerAlign="center"  align="center" allowSort="true" width="25">管理等级
	    			<input property="editor" name="chargeclass" class="mini-textbox" style="width:100%;" minWidth="40" />
	    		</div>
	    		<div name="action" width="20" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>
   <script type="text/javascript">
   		mini.parse();
   		var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("crimid", "desc");
        
        function onActionRenderer() {
        	var s = '<a href="javascript:saveData()" >保存</a>';
	     	return s;
        }

        function savePrison(){
        	var data = datagrid.getSelecteds();
	 		var json = mini.encode(data);
	 		var prisons = mini.get("prisons").getValue();
	 		if(!prisons){
	 			alert("请填写监舍编号！");
	 			return;
	 		}
	 		if (data.length>0) {
	 			$.ajax({
	                url: "<%=path%>/property/savePrisonBerth.json?1=1",
	                data: { data:json,prisons:prisons },
	                type: "post",
	                dataType:"text",
	                async:false,
	                success: function (text) {
	                	alert("操作成功!");
	                	mini.get("prisons").setValue();
	                	datagrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
			}else {
				alert("请至少选择一条数据！");
			}
	 	}
        
        function saveData(){
	 		var data = datagrid.getSelecteds();
	 		var json = mini.encode(data);
	 		if (data.length>0) {
	 			data = datagrid.getChanges();
	 			if (data.length>0) {
	 				$.ajax({
		                url: "<%=path%>/property/savePrisonBerth.json?1=1",
		                data: { data:json },
		                type: "post",
		                dataType:"text",
		                async:false,
		                success: function (text) {
		                	alert("操作成功!");
		                	datagrid.reload();
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                	alert("操作失败!");
		                }
		            });
	 			}else {
					alert("数据未变化！");
				}
			}else {
				alert("请至少选择一条数据！");
			}
	 	}
	 	
	 	function onKeyEnter(){
	 		search();
	 	}
	 	
	 	function search(){
	 	  	var key = mini.get("key").getValue();
	 	  	datagrid.load({ key: key});
	 	}
	</script>  
</body>
</html>
