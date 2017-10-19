<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>省局案件报备 获取罪犯列表页面</title>
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
<body onload="init('${menuid}');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
					<a class="mini-button" style="display:none;" id="900058" plain="true" onclick="chooseAll(900058);">批量报备</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="reportaudit" class="mini-combobox" emptyText="是否报备" name="reportaudit" onvaluechanged="search" data="[{ id: 0, text: '未报备' }, { id: 1, text: '已报备'}]" />
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="ProvincialCasesReportedList.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
       		   <div property="columns">
           	   <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="70" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="55" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="age" width="40" headerAlign="center" align="center"  allowSort="true">年龄</div>
              <div field="origin" width="70" headerAlign="center" align="left"  allowSort="true">籍贯</div>
              <div field="birthday" width="70" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="zuiming" width="60" headerAlign="center" align="left"  allowSort="true">罪名</div>
              <div field="crimtype" width="55" headerAlign="center" align="center"  allowSort="true">犯人类型</div>
              <div field="fujiaxing" width="80" headerAlign="center" align="left"  allowSort="true">附加刑</div>
              <div field="rujianriqi" width="70" headerAlign="center" align="center"  allowSort="true">入监日期</div>
              <div field="xingqiqiri" width="70" headerAlign="center" align="center"  allowSort="true">刑期起日</div>
              <div field="xingqizhiri" width="70" headerAlign="center" align="center"  allowSort="true">刑期止日</div>
              <div id="action" width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        	
        function onActionRenderer(e) {
        	var record = e.record;
	        var s = '';
        	if(record.reportaudit == '1' ){
        		s += '已报备';
        	}else{
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(900057);" >报备</a>';
        	} 
            return s;
 	    }

        //根据action跳转到指定页面
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	if(row){
        		$.ajax({
    			    url: "updateReportaudit.action?1=1&crimid="+row.crimid,
    		        type: "post",
    		        success: function (text) {
	                	alert("操作成功!");
	                	grid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
    			});
        	}else{
        		alert(confirmMessage);
        	}
        }
        function chooseAll(menuid){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var crimids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        crimids.push(r.crimid);
                    }
                    var crimid = crimids.join(',');
                    $.ajax({
        			    url: "updateReportaudit.action?1=1&crimid="+crimid,
        		        type: "post",
        		        success: function (text) {
    	                	alert("操作成功!");
    	                	grid.reload();
    	                },
    	                error: function (jqXHR, textStatus, errorThrown) {
    	                	alert("操作失败!");
    	                }
        			});
                }
            } else {
                alert( confirmMessages);
            }
		}
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
        	var reportaudit = mini.get("reportaudit").getValue();
            grid.load({ key: key,reportaudit: reportaudit});
        }
    </script>    
</body>  
</html>