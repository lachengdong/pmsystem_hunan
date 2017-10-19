<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>暂予监外执行决定书选择</title>
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
    <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
    </script>
</head>
<body>
    <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
        <table >
             <tr>
                <td style="width:100%;">
                	 <%
							if(topmap!=null && topmap.size()>0){
								for (String key : topmap.keySet()) {
								    String[] arry = topmap.get(key).split("@");
				       %>
				       <a class="mini-button" iconCls="" plain="true" onclick="<%=arry[1] %>"><%=arry[0] %></a>
				       <%
								}
							}
						%>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getBasicInfoChoose.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <!-- <div type="checkcolumn" width="30"></div> -->
              <div field="crimid" width="100" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="120" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="40" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="birthday" width="80" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="nation" width="100" headerAlign="center" align="center"  allowSort="true">籍贯</div>
              <div id="status" width="60" headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">状态</div> 
              <div id="action" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        function onStatusRenderer(){
        	var s = "";
        	var row = grid.getSelected();
        	s ='未处理&nbsp;&nbsp';
        	/*if(){
        		s ='未处理&nbsp;&nbsp';
        	}else if(){
        		s ='已处理&nbsp;&nbsp';
        	}else if(){
        		s ='正在处理&nbsp;&nbsp';
        	}*/
            return s;
        }
       function onActionRenderer() {
			var s ='';
            <%
				if(middlemap!=null && middlemap.size()>0){
					for (String key : middlemap.keySet()) {
			   			String[] arry = middlemap.get(key).split("@");
	       	%>
	       				s += " <a class=\"Edit_Button\" href=\"javascript:<%=arry[1] %>\" ><%=arry[0] %></a>&nbsp;&nbsp;";
	       	<%
					}
				}
			%>
	     	return s;
        }
        
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var url = "prisonOutExecute2.action?1=1&crimid="+row.crimid+"&name="+row.name+"&menuid="+menuid;
			self.location.href=url;
        }
        function chooseAll(){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    $.ajax({
                        url: "",
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
                alert(confirmMessages);
            }
		}
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
        function myloading(){
			var myform = new mini.Form("div_two");
			myform.loading();
		}
		function myunmask() {
			var myform = new mini.Form("div_two");
			myform.unmask();
		}
    </script>    
</body>  
</html>