<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>罪犯申请幅度</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }  
    </style>
</head>
<body onload="init('608548');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%">
				       <a class="mini-button" style="display:none;"  id="608549" iconCls="icon-save" plain="true" onclick="saveData">存盘</a>
				       <a class="mini-button" onclick="onCancel()" plain="true" style="width: 60px;">取消</a>
				       <span class="separator"></span>
               		   <a class="mini-button" iconCls="icon-reload" plain="true" onclick="refreshPage" >刷新</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
    		<input id="isstatus" name="isstatus" class="mini-hidden" value="${isstatus}"/>
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="<%=path %>/commutationParole/zfsqList.json?1=1&crimids=${crimids}" idField="spid" onbeforeload="myloading" onload="myunmask"  allowResize="true"
        	 allowCellEdit="true" allowCellSelect="true"  allowAlternating="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
          <div property="columns">
              <div field="crimid" width="40" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="fileno" width="40" headerAlign="center" align="center"  allowSort="true">档案号</div>
              <div field="name" width="40" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="jianqu" width="40" headerAlign="center" align="center"  allowSort="true">监区</div>
              
              <div field="fudu" width="50" headerAlign="center" align="center"  renderer="onActionExamineType" allowSort="true">申请幅度
              		<input property="editor" class="mini-textbox" style="width:100%;"/>
              </div>
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
       
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid.load({ key: key });
        }
		function saveData() {
            var data = grid.getChanges();
            var json = mini.encode(data);
            var isstatus = mini.get("isstatus").getValue();
            grid.loading("保存中，请稍后......");
            var crimids="${crimids}";
            $.ajax({
                url: "<%=path%>/commutationParole/saveShenQingFuDu.json?1=1",
                data: { data: json,isstatus:isstatus,crimids:crimids},
                type: "post",
                success: function (text) {
                    grid.reload();
                    CloseWindow("cancel");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow("cancel");
                }
            });
        }
		 function onActionExamineType(e){
        	var record = e.record,
		    field = e.field,
		    value = e.value;
            if(value){
            	e.cellStyle = "background:yellowgreen;";
            }
            return value;
        }
		// 刷新本页面
		function refreshPage(){
			if(!window["____refreshPage"]){window["____refreshPage"] = true;location.reload();
			} else {
				window.setTimeout(function(){window["____refreshPage"] = false;},1*1000);
			}
		};

         function onJyActionRenderer(e) {
        	var s = '${middlestr}';
	     	return s;            
        }	
        function SetData(){

        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
          
    </script>    
</body>  
</html>