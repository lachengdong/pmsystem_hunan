<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>罪犯认罪悔罪评估量表罪犯选择</title>
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
<body onload="init('10125');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
        <table >
             <tr>
                <td style="width:100%;">
                	<a class="mini-button" style="display:none;" id="11071" iconCls="" plain="true" onclick="chooseAll(11072);">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" >
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getContritionAppraiseTableList.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false">
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="nation" width="40" headerAlign="center" align="center"  allowSort="true">民族</div>
              <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="familyaddress" width="160" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <div field="education" width="60" headerAlign="center" align="left"  allowSort="true">所在监区</div>
              <div field="identity" width="60" headerAlign="center" align="center" allowSort="true" >审批状态</div>          
              <div id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        	
       //如果idnumber存在表示已经处理,duty=-1表示审批未通过,显示处理。
         //如果duty=0存在表示已经处理,accent=1表示未进入流程审批中,显示处理。
         function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.idnumber && record.duty != '-1' ){
        		if(record.duty=='0' || record.accent=='1'){
        			s += ' <a class="Edit_Button"  href="javascript:chooseOne(11072);" >修改</a>';
        		}else {
        			s += ' <a class="Edit_Button"  href="javascript:chooseOne(11074);" >查看</a>';
        		}
        	}else{
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(11072);" >处理</a>';
        	} 
            return s;
        } 	
        //根据罪犯编号跳转到基本信息表单页面
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	if(row){
        		var url = "disposeContritionAppraiseTable.action?1=1&crimid="+row.crimid+"&menuid="+menuid;
        		if(row.idnumber && row.duty != '-1')url += "&idnumber="+row.idnumber;//如果罪犯审批未通过重新生成一条数据
				self.location.href=url;
        	}else{
        		alert(confirmMessage);
        	}
        }
        //批量处理
  		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
           	if(rows.length > 0){
           		var ids = [];
                   for (var i = 0, l = rows.length; i < l; i++) {
                       var r = rows[i];
                       if(r.accent=='2' && r.duty != '-1') continue;//审批中的罪犯不能进行批量处理
                       if(r.idnumber && r.duty != '-1'){//如果罪犯审批未通过重新生成一条数据
                       		ids.push(r.crimid+"@"+r.idnumber);
                       }else{
                       	    ids.push(r.crimid+"@");
                       }
                   }
				   if(ids.length == '0'){//所选中的数据都是审批中的
				   		alert(noProcessing);
				   		return ;
				   }else {
				   		if (confirm(allProcessing)) {
						   var id = ids.join(',');
						   var url = "<%=path%>/disposeContritionAppraiseTable.action?1=1&id="+id+"&menuid="+menuid;
		                   self.location.href=url;
				   		}
				   }
           	}else {
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
    
    </script>    
</body>  
</html>