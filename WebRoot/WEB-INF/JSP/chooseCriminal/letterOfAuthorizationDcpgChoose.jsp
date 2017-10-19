<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>调查评估委托函（假释）</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="/pmsys/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="/pmsys/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="/pmsys/scripts/boot.js" type="text/javascript"></script> 
    <link href="/pmsys/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }  
    </style>
</head>
<body onload="init('13857');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	    <input name="tempid" id="tempid" class="mini-hidden" value="JXJS_DCPGWTH"/>
        <table >
             <tr>
                <td style="width:100%;">
                	<a class="mini-button" style="display:none;" id="13861" iconCls="" plain="true" onclick="chooseAll('13858');">批量处理</a>
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
        	 url="<%=path%>/letterDcpg/getTheMedicalParoleApprovalChooseChoose.json?1=1&tempid=JXJS_DCPGWTH" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="40" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="nation" width="60" headerAlign="center" align="center"  allowSort="true">民族</div> 
              <div field="familyaddress" width="120" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <div field="accent" width="60" headerAlign="center" align="center"  allowSort="true">所在监区</div>  
              <div field="identity" width="40" headerAlign="center" align="center"  allowSort="true">处理状态</div>
              <div id="action" width="50" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>	
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
	        if(record.duty=='0'){
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(13858);" >处理</a>';
        	}else if(record.duty=='1'){
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(13858);" >修改</a>';
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(13896);" >查看</a>';
        	}
            return s;
        }

        //根据action跳转到指定页面
        function chooseOne(menuid) {
        	var tempid = mini.get("tempid").getValue();
        	var row = grid.getSelected();
        	if(row){
        		var url = "<%=path%>/letterDcpg/letterOfAuthorization.page?1=1&menuid="+menuid+"&crimid="+row.crimid+"&tempid="+tempid;
				self.location.href=url;
        	}else{
        		alert( "请选中一条记录！");
        	}
        }
		        //批量处理
  		function chooseAll(menuid){
  			var tempid = mini.get("tempid").getValue();
        	var rows=grid.getSelecteds();
	     	if(rows.length>0){
	     		if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.crimid);
                    }
                    var id = ids.join(',');
                    var url = "<%=path%>/letterDcpg/letterOfAuthorization.page?1=1&menuid="+menuid+"&id="+id+"&tempid="+tempid;
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
            grid.load({ key: key });
        }
    </script>    
</body>  
</html>