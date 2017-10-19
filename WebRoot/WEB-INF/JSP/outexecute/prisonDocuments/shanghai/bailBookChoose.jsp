<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯保外就医取保书（上海）</title>
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

<body onload="init('12377');">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    	<input id="tempid" name="tempid" class="mini-hidden" value="JWZX_BWJYQBS_SH"/>
        <table >
             <tr>
                <td style="width:100%;">
                <a class="mini-button" style="display:none;" id="13710" plain="true" onclick="chooseAll(12378);">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('12377')"></a>   
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid" url="<%=path%>/shBailBook/getBailBookChoose.json?1=1" 
	     	 idField="id"  multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
            		 <div type="checkcolumn" ></div>  
           			<div field="crimid" width="100px" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
	              	<div field="name" width="80px" headerAlign="center" align="center"  allowSort="true">姓名</div>
	              	<div field="gender" width="40px" headerAlign="center" align="center"  allowSort="true">性别</div>
           			<div field="birthday" width="80px" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
               		<div field="introduction" width="40px"  headerAlign="center"  	allowSort="true" align="center" >刑种</div>  
           			<div field="criofficiallyplacedate" width="80px" headerAlign="center"  align="center" dateFormat="yyyy-MM-dd" allowSort="true">入监时间</div>
               		<div field="ctrnewcorpname" width="80px" headerAlign="center"  align="center" allowSort="true">监区</div>  
               		<div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>

	<script type="text/javascript"> 
		mini.parse();
		var datagrid = mini.get("datagrid");
		datagrid.load();
		datagrid.sortBy("optime", "desc");
		function onActionRenderer() {
	  		var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
			return s;
	    }
		//选择	
     	function chooseOne(menuid){
     		var tempid=mini.get("tempid").getValue();
          	var row = datagrid.getSelected();
          	var url="<%=path%>/shBailBook/bailBook.page?1=1";
          	if (row) {
         	   	var crimid = row.crimid;
        		url += "&tempid="+tempid+"&crimid="+crimid+"&menuid="+menuid+"&name="+row.name;
				window.location.href=url;
          	} else {
              	alert("请选中一条记录");
          	}
        }
	     // 渲染日期
	     function onDateRenderer(e) {
	     	if(e && e.value){
	     		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	     	}
	         return "";
	     }
	     
	     function chooseAll(menuid){
	     	var rows=datagrid.getSelecteds();
	     	var tempid=mini.get("tempid").getValue();
	     	var url="<%=path%>/shBailBook/bailBook.page?1=1";
	     	if(rows.length>0){
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
                   	url += "&tempid="+tempid+"&crimid="+id+"&menuid="+menuid+"&name="+idname;
                    self.location.href=url;
                }
	     	}else{
	     		alert("请至少选择一条记录！");
	     	}
	     }
	</script>
</body>
</html>