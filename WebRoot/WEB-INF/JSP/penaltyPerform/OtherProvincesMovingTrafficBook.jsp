<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>外省保外移交通知书</title>
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
<body>
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;"><a class="mini-button" onclick="goBack()" plain="true" iconCls="icon-undo" >返回</a>
                    <a class="mini-button" plain="true" iconCls="icon-user" onclick="sendMenuid()">选择罪犯</a>
           			<span style="padding-left:40px;">罪犯编号：${crimid}</span>
           			<span style="padding-left:10px;">姓名：${name}</span>
           			<input id="crimid" value="${crimid}" class="mini-hidden"/>
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="onKeyEnter"/>   
                        <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div class="mini-fit">
	    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
	        url=""  idField="id" multiSelect="true" sizeList="[20,50,100]" pageSize="20" allowResize="false" 
	        showLoading="false" onbeforeload="onPageChanged" >
	        <div property="columns">
	            <div field="criminalid" width="120" headerAlign="center"  align="center" allowSort="true" >罪犯编号</div>
	            <div field="cbiname" width="120" headerAlign="center"  align="center" allowSort="true">姓名</div>	                
	            <div field="cbigender" width="100" headerAlign="center"  align="center">性别</div>
	            <div field="cbibirthday" width="100" headerAlign="center"  align="center" dateFormat="yyyy-MM-dd" allowSort="true">出生日期</div>
	            <div field="cjiimprisontype" width="100" headerAlign="center"  align="center" allowSort="true">刑种</div>
	            <div field="criofficiallyplacedate" width="100" headerAlign="center"  align="center" dateFormat="yyyy-MM-dd" allowSort="true">入监时间</div><!-- 收押日期 -->
	            <div field="ctrnewcorpname" width="100" headerAlign="center"  align="center" allowSort="true">监区</div>
	            <div name="action" width="120" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	     </div>
    </div>

    <script type="text/javascript">
    mini.parse();
    var datagrid1 = mini.get("datagrid1");
    //datagrid1.load();
    datagrid1.sortBy("criminalid", "asc");
    //跳转到选择罪犯页面
    function sendMenuid(){
    	var url = "toChooseCriminalPage.action?1=1&action=OtherProvincesMovingTrafficBook";
		self.location.href=url;
    }
        function onCriminalidRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:getCriminalInfo(\'' + record.criminalid + '\')" >'+record.criminalid+'</a>&nbsp;&nbsp;';
            return s;
        }
        function getCriminalInfo(criminalid){
        	mini.open({
                  url: "openSearchPage.action?menuid=8328&criminalid="+criminalid,
                  showMaxButton: true,
                  allowResize: false, 
                  title: "罪犯信息", width: 1000, height: 550,
                  onload: function () {
                     // var iframe = this.getIFrameEl();
                      //var data = { action: "edit",menuid:menuid};
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
              	});	
        }
 
		function onActionRenderer(e) {
            var s = '<a class="Edit_Button" href="javascript:onOk()" >选择</a>'
            	
        }
 
        function search() {
            var key = mini.get("key").getValue();
            datagrid1.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        function onPageChanged() {
        	myloading();
        }
 
        function query(){
             var row = datagrid1.getSelected();
        }
       
        function myloading(){
       		mini.mask({
       		    el:document.body,
       			cls:'mini-mask-loading',
       			html:'加载中...'
       		});
        }
        function myunmask() {
       		mini.unmask(document.body);
        }
        function goBack() {
       		var menuid=437071;
       	  	//window.location="publicMainAction.action?menuid="+menuid+"&clear=0";
       	  	window.location="publicMainAction.action?choosecriminal=yes&menuid="+menuid+"&singlerecord="+'single';
        }
    </script>  
</body>

</html>