<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>会议列表（宁夏）</title>
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
<body>
    <div>
    <input id="menuid" name="menuid" type="hidden" value=""/>
    <input id="userid" type="hidden" value="${userid }"/>
    <input id="meetcontent" type="hidden" value="${meetcontent }"/>
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
            <table style="width:100%;">
                <tr align="right">
                    <td style="width:100%;">
                         <input id="orgid" name="orgid" class="mini-combobox" url="org/ajax/getdepartunderself.action?1=1"  onvaluechanged="ondeptchanged"   valueFromSelect="false"
						        textField="name" valueField="dorgid" showNullItem="true" nullItemText="--全部--"
						    	 style="width:150px;" enabled="true" emptyText="请选择部门..."/>
                    </td>
                    <td style="">
                        <input  id="starttime"  name="starttime"  class="mini-datepicker" allowInput="true" emptyText="会议时间" dateFormat="yyyy-MM-dd"/> 
                    </td>
                    <td>
                    	至：
                    </td>
                    <td style="">
                        <input  id="endtime"  name="endtime"  class="mini-datepicker" allowInput="true" emptyText="会议时间" dateFormat="yyyy-MM-dd"/>
                    </td>
                    <td style="">
                        <a class="mini-button" iconCls="icon-search"  style="width:60px; plain="true" onclick="search()">查询</a>
                    </td>
                    <td>
                        <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10097')"></a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div class="mini-fit">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20" 
        url="getMeetingList_nx.action?meetcontent=${meetcontent}&flowdefid=other_jybwjycbsp"  idField="" multiSelect="true"  allowAlternating="true">
        <div property="columns">
            <div field="otherid" width="80" headerAlign="center" visible="false" align="center" allowSort="true">主键</div>
            <div field="opid" width="80" headerAlign="center" visible="false" align="center" allowSort="true">用户名</div>
            <div field="username" width="100" headerAlign="center" align="center" allowSort="true">会议提交人</div>
            <div field="sn" width="100" headerAlign="center" align="center" allowSort="true"  renderer="onDateRendererSn">会议次数</div>
            <div field="text4" width="150" headerAlign="center" align="center" allowSort="true">会议描述</div>
            <div field="text5" width="80" headerAlign="center" align="center" allowSort="true">会议类型</div> 
            <div field="depname" width="70" headerAlign="center" align="center" >所属部门</div>    
            <div field="optime" width="70" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">会议时间</div> 
            <div header="操作"  width="70"  headerAlign="center" align="center"  renderer="caozuo"></div>
        </div>
    </div>
  </div>

    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
		var menuid = document.getElementById("menuid").value;
        //部门过滤
		function ondeptchanged() {
			var orgid = mini.get("orgid").getValue();
			var change = "change";
			grid.load({orgid:orgid,change:change,key:orgid});
		}
		function caozuo(e) {
            var record = e.record;
            var uid = record._uid;
            var s = ' <a class="Edit_Button" href="javascript:chakan(\'' + uid + '\')" >查看</a>'
            	   +' &nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:shanchu(\'' + uid + '\')" >删除</a>';
            return s;
        }
        function chakan() {
        	var row = grid.getSelected();
        	var otherid = row.otherid;
            if (row) { 
                var win=mini.open({
                    url: "priviewMeetDoc.action?1=1&otherid="+otherid,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "会议记录", width: "900", height: "600",
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {otherid:row.otherid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
	         	});
	         	win.max();
            } else {
                alert("请选中一条记录");
            }
        }
        function shanchu() {
        	var row = grid.getSelected();
        	var userid=$("#userid").val();//当前用户
        	var opid = row.opid;//会议记录的保存用户
        	if(opid != userid){
                alert("无法删除其他用户保存的会议记录!");
                return;
            }
            var flag = 0;
            if (row) { 
            	if(confirm("确认删除？")){
	               $.ajax({
		                 url: "deleteMeeting.action?1=1&mkey="+row.otherid,
		                 type: "post",
		                 success: function (text) {
		                     if(text == 1){
                                 alert("会议记录删除成功!");
			                 }
		                 	 grid.load();
		                 }
		             });
                }
            } else {
                alert("请选中一条记录");
            }
        }
        function search() {
            var orgid = mini.get("orgid").getValue();
            var starttime = mini.get("starttime").text;
            var endtime = mini.get("endtime").text;
            grid.load({ orgid: orgid, starttime : starttime, endtime : endtime });
        }
        function onKeyEnter(e) {
            search();
        }
        // 根据当前批次的会议的次数  组织数据
        function mxuhaos(e){
             var str= e.value;
             return "第"+str+"次"; 
        }
        
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }

        function onDateRendererSn(e){
            var sn = e.value;
            return "第"+sn+"次";
        }
    </script>
</body>
</html>