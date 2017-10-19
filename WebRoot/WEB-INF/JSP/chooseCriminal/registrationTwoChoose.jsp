<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>解回再审 登记罪犯选择</title>
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
<body onload="init('10226');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    	<input id="tempid" name="tempid" class="mini-hidden" value="XFZX_JHZSSPB"/>
        <table >
             <tr>
                <td style="width:100%;">
                    <a class="mini-button" style="display:none;" id="11389" iconCls="" plain="true" onclick="chooseAll('10226_01');">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                 </td>
             </tr>
        </table>
    </div>
      <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20" showLoading="false"
        	 url="getRegistrationTwoBasicInfoList.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false">
         <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="80" headerAlign="center" align="center" allowSort="true">罪犯编号</div>    
              <div field="name" width="80" headerAlign="center" align="center" allowSort="true">姓名</div>
              <div field="gender" width="40" headerAlign="center" align="center" allowSort="true">性别</div>
              <div field="birthday" width="80" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="familyaddress" width="160" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <div field="accent" width="80" headerAlign="center" align="left"  allowSort="true">所在监区</div>
              <div id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
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
            var s =' <a class="Edit_Button"  href="javascript:chooseOne(\'10226_01\');" >处理</a>';
            return s;
        }

        //根据action跳转到指定页面
        function chooseOne(menuid) {
        	var tempid = mini.get("tempid").getValue();
        	var row = grid.getSelected();
        	if(row){
        		var url = "registrationTwoList.action?1=1&tempid="+tempid+"&crimid="+row.crimid+"&name="+row.name+"&menuid="+menuid;
				self.location.href=encodeURI(url);
        	}
        }
        function chooseAll(menuid){
        	var rows = grid.getSelecteds();
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
                    var url = "registrationTwoList.action?1=1&id="+id+"&menuid="+menuid+"&idname="+idname;
                    self.location.href=url;
                }
            } else {
                alert( "请至少选中一条记录！");
            }
		}
        //快速查询
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
       
    </script>    
</body>  
</html>