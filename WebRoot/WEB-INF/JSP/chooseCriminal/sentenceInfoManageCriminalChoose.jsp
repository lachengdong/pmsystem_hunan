<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>判裁信息</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }  
    </style>
</head>
<body onload="init('600344');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
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
        	 url="<%=path%>/basicInfo/getBasicInfoChoose.json?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
         	  <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="50" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="nation" width="40" headerAlign="center" align="center"  allowSort="true">民族</div>
              <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="familyaddress" width="130" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <!-- 
              <div field="accent" width="60" headerAlign="center" align="left"  allowSort="true">所在监区</div>
              -->
              <div id="action" id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid","desc");
        grid.load();

		function onActionRenderer(e) {
			var grid = e.sender;
	        var record = e.record;
        	var uid = record.crimid;
	    	var s = '';
	        <%
			if(middlemap!=null && middlemap.size()>0){
				for (String key : middlemap.keySet()) {
			   		String[] arry = middlemap.get(key).split("@");
	        %>
	      			s +=" <a class=\"Edit_Button\" href=\"javascript:<%=arry[1] %>\" ><%=arry[0] %></a>";
	        <%
				}
			}
			%>
	     return s;
	    }

	    function chooseOne(crimid){
	     	window.location.href="toSentenceInfoListPage.action?crimid="+crimid;   
	    }

        function onKeyEnter(e) {
            search();
        }

        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }

        function myloading(){
			var myform = new mini.Form("div_two");
			myform.loading();
		}
		
		function myunmask() {
			var myform = new mini.Form("div_two");
			myform.unmask();
		}
    </script>    
</body>  
</html>