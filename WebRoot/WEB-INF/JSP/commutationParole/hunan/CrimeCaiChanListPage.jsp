<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String menuid = request.getParameter("menuid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>财产刑补录</title>
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
<body onload="init('18443');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
     	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    	<input id="tempid" name="tempid" class="mini-hidden" value="XFZX_XQBD"/>
        <table >
             <tr>
                <td style="width:100%;">
                </td>
                <td style="white-space:nowrap;"> 
                	<input id="orgid" name="orgid" class="mini-combobox" url="<%=path %>/org/ajax/getdepartunderself.action?1=1"  onvaluechanged="search"   valueFromSelect="false"
						textField="name" valueField="dorgid" showNullItem="true" nullItemText="--全部--" style="width:150px;" enabled="true" emptyText="请选择部门..."/>
					<input id="status" name="status" class="mini-combobox" data="Statusdata" showNullItem="true" onvaluechanged="search" valueFromSelect="false"
						textField="text" valueField="id" showNullItem="true" nullItemText="--全部--" style="width:150px;" enabled="true" emptyText="羁押状态..."/>
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10135')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
          idField="spid" multiSelect="true"  allowAlternating="true"  virtualScroll="false"  url="getCriminalBasicInfoForKHJC.action?1=1"  
          showLoading="false">
          <div property="columns">
<!--              <div type="checkcolumn" width="30"></div>-->
              <div field="crimid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>
              <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="nation" width="40" headerAlign="center" align="center"  allowSort="true">民族</div>
              <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="forfrit" width="40" headerAlign="center" align="left"  allowSort="true">原判罚金</div>
              <div field="payment" width="40" headerAlign="center" align="left"  allowSort="true">已履行罚金</div>
              <div field="forfeitureproperty" width="40" headerAlign="center" align="left"  allowSort="true">原判没收财产</div>
              <div field="expropriation" width="40" headerAlign="center" align="left"  allowSort="true">已没收财产</div>
              <div field="compensation" width="40" headerAlign="center" align="center"  allowSort="true">原判民陪</div>
              <div field="fulfilcompensation" width="40" headerAlign="center" align="center"  allowSort="true">履行民陪</div>
              <div id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
          </div>
        </div>
    </div>
    <script type="text/javascript">
    	var Statusdata = [{ id: 1, text: '在押' }, { id: 2, text: '释放'}];
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid","desc");
        grid.load();
        
        function onActionRenderer() {
        	var s = '<a class="New_Button" href="javascript:chuLiProperty(18584);">处理</a>';
	     	return s;
        }
        
        //根据action跳转到指定页面
        function chuLiProperty(menuid){
        	var row = grid.getSelected();
        	var crimid = row.crimid;
        	var url = "<%=path%>/toCaiChanXingListPage.page?1=1&crimid="+row.crimid+"&menuid="+menuid;
        	self.location.href=url;
        }
        
        function search() {

            var key = mini.get("key").getValue();
            var orgid = mini.get("orgid").getValue();
            var status = mini.get("status").getValue();
            //
        	grid.sortBy("crimid","desc");
            grid.load({ key: key ,zgorgid:orgid , status:status});
        }
        
        function onKeyEnter(e) {
            search();
        }
       
    </script>    
</body>  
</html>