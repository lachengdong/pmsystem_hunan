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
	    <input id="menuid" name="menuid" class="mini-hidden" value="${menuid}"/>
	    <input id="templetid" name="templetid" class="mini-hidden" value="${templetid}"/>
	    <input id="choosecriminal" name="choosecriminal" class="mini-hidden" value="${choosecriminal}"/>
	    <input id="state" name="state" class="mini-hidden" value="${state}"/>
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
        	 url="getCriminalBasicInfoForKHJC.action?1=1&choosecriminal=${choosecriminal}" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
           <div type="checkcolumn" width="30"></div> 
              <div field="crimid" width="60" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="30" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="nation" width="40" headerAlign="center" align="center"  allowSort="true">民族</div>
              <div field="birthday" width="60" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="familyaddress" width="160" headerAlign="center" align="left"  allowSort="true">家庭住址</div>
              <div field="orgname" width="70" headerAlign="center" align="center"  allowSort="true">所在监区</div>
              <div field="departname" width="70" headerAlign="center" align="center"  allowSort="true">所在监狱</div>
              <div id="action" id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid","desc");
        grid.load();
        // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        function onActionRenderer() {
        	var s = '${middlestr}';
	     	return s;
        }
        //根据action跳转到指定页面
        function chooseOne(menuid) {
        	//用于返回到主页面的url
	        var furl = encodeURIComponent(location.href);
        	var templetid = mini.get("templetid").getValue();
        	var row = grid.getSelected();
        	if(row){
        		var url = "publicCriminalBaseDoc.page?1=1&templetid="+templetid+"&crimid="+row.crimid+"&menuid="+menuid+"&name="+row.name+"&furl="+furl;
				self.location.href=url;
        	}else{
        		alert("请选择记录！");
        		return false;
        	}
        }
        
        //快速查询
        function search(){
        	var choosecriminal = mini.get("choosecriminal").getValue();
        	grid.setUrl("getCriminalBasicInfoForKHJC.action?1=1&choosecriminal="+choosecriminal);
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
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