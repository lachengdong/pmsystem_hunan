<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>演示页面配置</title>
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
<body>
	<!-- 隐藏字段，判断选择罪犯后跳转到哪个主页面 -->     
    <input id="action" name="action" class="mini-hidden" value="${action}" />
    <input id="resid" name="resid" class="mini-hidden" value="${resid}"/>
    <input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
        <table >
             <tr>
                <td style="width:100%;">
                	<a class="mini-button" iconCls="icon-search" plain="true" onclick="chooseAll()">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
          idField="spid" multiSelect="true"  allowAlternating="true"  virtualScroll="false" url="getBasicInfoList.action?1=1"
          showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="100" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="120" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="gender" width="40" headerAlign="center" align="center"  allowSort="true">性别</div>
              <div field="birthday" width="120" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="nation" width="100" headerAlign="center" align="center"  allowSort="true">籍贯</div>
              <div id="action" id="action" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
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
			var s ='<a class="Edit_Button" href="javascript:chooseOne()">处理</a>&nbsp;&nbsp;';
				s +='<a class="Edit_Button" href="javascript:chakan()">查看</a>&nbsp;&nbsp;';
            return s;
        }
        function chakan(){
        	var row = grid.getSelected();
        	var action = mini.get("action").getValue();//要跳转的action地址
        	var resid = mini.get("resid").getValue();
        	var url = action+".action?1=1&resid="+resid+"&crimid="+row.crimid+"&name="+row.name;
			self.location.href=url;
        }
        //根据action跳转到指定页面
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var action = mini.get("action").getValue();//要跳转的action地址
        	//var resid = mini.get("resid").getValue();
        	var resid = '';
        	var tempid = mini.get("tempid").getValue();
        	if(resid == null){
        		resid = menuid;
        	}
        	var url = action+".action?1=1&menuid="+resid+"&crimid="+row.crimid+"&name="+row.name+"&tempid="+tempid+"&fathermenuid=${menuid}";
        	if(row.idnumber && row.duty != '-1')url += "&idnumber="+row.idnumber;//如果罪犯审批未通过重新生成一条数据
			self.location.href=url;
		
        }
        //批量处理，将选中的罪犯编号拼接成数组，转换成字符串
        function chooseAll(){
        	var rows = grid.getSelecteds();
        	var action = mini.get("action").getValue();//要跳转的action地址
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i<l ; i++) {
                        var r = rows[i];
                        ids.push(r.crimid);
                    }
                    var id = ids.join(',');
                    var url= action+".action?1=1&id="+id;
                    self.location.href=url;
                }
            } else {
                alert(confirmMessages);
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