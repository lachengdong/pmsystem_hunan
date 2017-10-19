<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
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
<body >
	
	<div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
	    <input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	    <input id="pagetype" name="pagetype" class="mini-hidden" value="${pagetype}"/>
	    <table >
			<tr>
				<td style="width:100%;">
					${topstr}
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
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getCriminalBasicInfoForKHJC.action?1=1&flowdefid=${flowdefid}" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false">
          <div property="columns">
           <div type="checkcolumn" width="30"></div> 
           	  <div field="anjianhao" width="60" headerAlign="center" align="center"  allowSort="true">案件号</div>
              <div field="crimid" width="40" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="40" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="nation" width="40" headerAlign="center" align="center"  allowSort="true">民族</div>
              <div field="birthday" width="40" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="familyaddress" width="160" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <div field="orgname" width="40" headerAlign="center" align="center"  allowSort="true">所在监区</div>
              <div field="departname" width="40" headerAlign="center" align="center"  allowSort="true">所在监狱</div>
              <div id="action" id="action" width="70" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        //grid.sortBy("crimid","desc");
        grid.load();
        
        function onActionRenderer() {
        	var s = '${middlestr}';
	     	return s;
        }
        
        //快速查询
        function search(){
            var key = mini.get("key").getValue();
            grid.load({ key: key});
        }
        
        function onKeyEnter(e){
            search();
        }
    </script>  
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>  
</body>  
</html>