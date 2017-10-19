<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>天津市监狱管理局罪犯保外就医合议登记表</title>
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

<body onload="init('12387');">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
               		<a class="mini-button" style="display:none;" id="12880" plain="true" onclick="chooseAll(12388);">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                    <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid" url="<%=path%>/tjBureauPanel/getBureauPanelChoose.json?1=1" 
	     	 idField="id"  multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
            	 <div type="checkcolumn" width="30"></div>
                 <div field="crimid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
                 <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
                 <div field="gender" width="40" headerAlign="center" align="center"  allowSort="true">性别</div>
                 <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
                 <div field="nation" width="60" headerAlign="center" align="center"  allowSort="true">民族</div> 
                 <div field="familyaddress" width="120" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
                 <div field="accent" width="60" headerAlign="center" align="center"  allowSort="true">所在监区</div>  
                 <div id="action" width="50" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>	
	        </div>
	    </div>
    </div>
	<script type="text/javascript"> 
		mini.parse();
		var datagrid = mini.get("datagrid");
		datagrid.load();
		datagrid.sortBy("crimid", "desc");
		
		function onActionRenderer() {
	  		var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
			return s;
	    }
		//选择	
     	function chooseOne(menuid){
          	var row = datagrid.getSelected();
          	var url="<%=path%>/tjBureauPanel/bureauPanel.page?1=1";
          	if (row) {
        		url += "&crimid="+row.crimid+"&menuid="+menuid+"&name="+row.name;
				window.location.href=url;
          	} else {
              	alert("请选中一条记录");
          	}
        }
        function chooseAll(menuid){
        	var rows = datagrid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    var idnames = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.crimid);
                        idnames.push(r.name);
                    }
                    var id = ids.join(',');
                    var idname = idnames.join(',');
                    var url = "<%=path%>/tjBureauPanel/bureauPanel.page?1=1&id="+id+"&menuid="+menuid+"&idname="+idname;
                    self.location.href=url;
                }
            } else {
                alert( "请至少选中一条记录！");
            }
		}
       
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
        	datagrid.sortBy("crimid","desc");
            datagrid.load({ key: key });
        }
	</script>
</body>
</html>