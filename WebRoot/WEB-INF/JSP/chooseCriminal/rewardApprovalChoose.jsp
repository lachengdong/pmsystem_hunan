<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>奖励审批</title>
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
<body  onload="init('600886');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table>
             <tr>
                <td style="width:100%;">
                	<a class="mini-button" style="display:none;" id="600897" iconCls=""  plain="true" onclick="chooseAll('600898');">批量处理</a>
                </td>
                <td style="white-space:nowrap;">
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('600000')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getRewardApprovalChoose.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" showLoading="false" >
          <div property="columns">
          	  <div type="checkcolumn" width="30"></div>
              <div field="CRIMID" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="NAME" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="ORGNAME2" width="80" headerAlign="center" align="center"  allowSort="true">所在监区</div>
              <div field="TOTAL" width="30" headerAlign="center" align="center"  allowSort="true">总分</div>
              <div field="CSEASON" width="40" headerAlign="center" align="center"  allowSort="true">当季得分</div>
              <div field="PASTSEASON" width="60" headerAlign="center" align="center"  allowSort="true">历季得分</div>
              <div id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("pastseasonsort","desc");
        	grid.load();
        
        function onActionRenderer(e) {
        	var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
	     	return s;
        }
        //处理
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	if(row){
        		var url = "rewardApproval.action?1=1&crimid="+row.CRIMID+"&menuid="+menuid+"&name="+row.NAME;
				self.location.href=url;
        	}else{
        		alert(confirmMessage);
        	}
        }
        //批量处理
        function chooseAll(menuid){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作选中记录？")) {
                    var ids = [];
                    var idnames = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.CRIMID);
                        idnames.push(r.NAME);
                    }
                    var id = ids.join(',');
                    var idname = idnames.join(',');
                    var url = "rewardApproval.action?1=1&id="+id+"&menuid="+menuid+"&idname="+idname;
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
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
    </script>    
</body>  
</html>