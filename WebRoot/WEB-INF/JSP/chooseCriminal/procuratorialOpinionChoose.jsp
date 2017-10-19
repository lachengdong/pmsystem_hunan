  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>检查意见书（上海）罪犯处理</title>
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
<body onload="init('12328');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
				   <a class="mini-button" style="display:none" id="13740" plain="true" onclick="chooseAll('12446')" >批量处理</a>
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
        	 url="getLetterOfAuthorizationContinuingMedicalParoleBasicInfoList.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" >
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="anhao" width="30" headerAlign="center" align="center"  allowSort="true">案号</div>    
              <div field="applyid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="applyname" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="causeaction" width="60" headerAlign="center" align="center"  allowSort="true">罪名</div>
              <div field="commenttext" width="100" headerAlign="center" align="left"  allowSort="true">办案人意见</div>
              <div field="fuyiflag" width="60" headerAlign="center" align="left"  allowSort="true">检查意见</div>            
              <div id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
        
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        	
       function onActionRenderer() {
			var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
	     	return s;
        }
        //根据罪犯编号跳转到表单页面
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var url = "procuratorialOpinion.action?1=1&menuid="+menuid+"&crimid="+row.applyid+"&name="+row.applyname;
			self.location.href=url;
        }
        
        function chooseAll(menuid){
        	var rows=grid.getSelecteds();
        	var url="procuratorialOpinion.action?1=1";
        	if(rows.length>0){
	     		if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    var idnames = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.applyid);
                        idnames.push(r.applyname);
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