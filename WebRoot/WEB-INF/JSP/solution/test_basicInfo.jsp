<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>基本信息罪犯选择(fayuan)</title>
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
<body onload="init('${menuid }');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
                	 <a class="mini-button"  iconCls="" plain="true" onclick="chooseAll('13694')">批量处理</a>
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
        	 url="<%=path%>/solution/getBasicInfoList.json?1=1"  multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="nation" width="40" headerAlign="center" align="center"  allowSort="true">民族</div>
              <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="familyaddress" width="160" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <div width="40" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
    	mini.parse();
    	var grid = mini.get("datagrid1");
    	grid.load();
    	
    	function onActionRenderer(e) {
	        //var record = e.record;
	        var s = '';
        	s += ' <a class="Edit_Button"  href="javascript:chooseOne(11090);" >处理</a>';
            return s;
        }
        
		//批量处理
  		function chooseAll(menuid){
        	alert("批量处理");
		}
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
        
        
        function chooseOne(menuid){
        	//组合向后台传递的参数，param为查询方案所需的数据
        	var row = grid.getSelected();
        	var crimid = row.crimid;
        	var params = {crimid:crimid,ddd:'1111'};
        	params = "["+mini.encode(params)+"]";
        	var tempid = 'TEST_FORM';//要修改为从url或其它方式获取
        	var solutionid = '1000000058'; //要修改为从url或其它方式获取
        	var flowdefid = 'TEST_FLOWDEF';//要修改为从url或其它方式获取
        	
        	if(row){
        		var url = "<%=path%>/solution/toPublicFormPage.action?1=1&menuid="+menuid+"&solutionid="+solutionid+"&tempid="+tempid+"&flowdefid="+flowdefid+"&params="+params;
				self.location.href=url;
        	}else{
        		alert("请选中一条数据！");
        	}
        }        
    </script>    
</body>  
</html>