<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>检察意见</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
	   body{
	       font-size:12px; margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	   }
    </style>
</head>
<body >
		<div class="mini-toolbar" style="padding:0px;border-bottom:0;">
			<table  style="width:100%;">
				<tr>
					<td width="80%" style="margin-left: 20px;"></td>
					<td style="white-space:nowrap;">
						入监日期：
						<input id = "startdate" emptyText="开始时间" class="mini-datepicker" format="yyyy-MM-dd"/>
						&nbsp;&nbsp;至&nbsp;&nbsp;
						<input id="enddate" emptyText="结束时间" class="mini-datepicker" format="yyyy-MM-dd"/>
					    <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="罪犯编号、姓名、拼音" style="width:150px;" onenter="onKeyEnter"/>   
					    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
					    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10187')"></a>
				    </td>
				</tr>
			</table>           
		</div>
		<div class="mini-fit" >
			<div id="datagrid1" class="mini-datagrid" style="width:100%;height: 100%" allowResize="false" allowAlternating="true" 
				sizeList="[10,20,50,100]" pageSize="20" url="getJianChaInfo.action?1=1"  multiSelect="true"  >
		        <div property="columns">
		            <div type="checkcolumn" ></div>   
		            <div field="crimid" width="90" headerAlign="center" align="center" allowSort="true">罪犯编号</div>    
		            <div field="name" width="60" headerAlign="center" align="center"  allowSort="true">罪犯姓名</div>   
		            <div field="age" width="40" headerAlign="center"  align="center"  allowSort="true">年龄</div> 
		            <div field="causeaction" width="110" headerAlign="center" align="center"  allowSort="true">罪名</div>
		            <div field="punishmenttype" width="110" headerAlign="center" align="center"  allowSort="true">刑种</div>
		            <div field="sentencestime" width="110"  align="center"   headerAlign="center" allowSort="true">刑期起日</div> 
		            <div field="sentenceetime" width="110"   align="center"  headerAlign="center" allowSort="true">刑期止日</div> 
		            <div field="inprisondate" width="100" headerAlign="center" align="center"  allowSort="true">入监时间</div>
		            <div field="" width="140" headerAlign="center" allowSort="false" align="center" renderer="onActionRenderer">操作</div>                     
				</div>
			</div>
		</div>
    <script type="text/javascript"><!--
       	mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
    	grid.sortBy("crimid","desc");
        function onActionRenderer(e) {
            var record = e.record;
            var crimid=record.crimid;
           	var s = '<a class="Edit_Button" href="javascript:viewjianyi(\'' + crimid + '\')" >检察意见</a>&nbsp;&nbsp;';
            return s;
        }
		function viewjianyi(id) {
			var templeid="SJGJ_SJJD";
			var mbid=9710;
			var url = "editJianDuYiJian.action?1=1&templeid="+templeid+"&crimid="+id+"&mbid="+mbid;
                mini.open({
                   url:url,
                    title: "检察意见", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { mymenuid : "12165" , myoperatetype:'view',id:templeid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        //grid.reload();
                    }
                });
                
		}
       
		//快速查询
		function onKeyEnter(e) {
            search();
        }
        function search() {
             var key = mini.get("key").getValue();
	         var startdate=mini.get("startdate").text;
	         var enddate=mini.get("enddate").text;
	         grid.load({ key: key ,startdate:startdate,enddate:enddate});
        }
    </script>
</body>
</html>