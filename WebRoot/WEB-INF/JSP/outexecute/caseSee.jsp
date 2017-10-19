<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>保外案件查看</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
	</style>
</head>
  <body>
    <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <input id="unitlevel" name="unitlevel" class="mini-hidden" value="${unitlevel}"/>
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                </td>
                <td style="white-space:nowrap;">
                	<input id="depid" class="mini-combobox" valueField="orgid" textField="name" emptyText="单位过滤" showNullItem="true" enabled="true"
           					nullItemText="--全部--" url="<%=path%>/jxjs/getDeptInfo.action?1=1" style="width:150px;"   onvaluechanged="onKeyEnter" />
           			<input id="processid" class="mini-combobox" emptyText="进程过滤" showNullItem="true" nullItemText="--全部--" valueField="snodeid" textField="text3" 
           					url="jxjs/ajaxGetProcess.json?flowdefid=${flowdefid }" style="width:150px;" onvaluechanged="onKeyEnter"/>
           			<input id="key" class="mini-textbox" emptyText="罪犯编号、姓名、拼音"  id="key" onenter="onKeyEnter"  enabled="true"/>
                    <a class="mini-button" onclick="search()" plain="true">查询</a>
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10141')"></a>
                </td>
            </tr>
        </table>           
    </div>
    <div class="mini-fit">
    	<div id="datagrid1" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid" url="getBWCaseDataInfo.action?1=1&flowdefid=${flowdefid}" 
		     idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  onshowrowdetail="onShowRowDetail"
		     allowCellEdit="true"  allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	            <div type="indexcolumn" width="20" headerAlign="center"  align="center" allowSort="true"></div> 
	        	<div type="expandcolumn" width="40">留痕记录</div>
	            <div field="anhao" width="120" headerAlign="center" align="center" allowSort="false">案件号</div> 
	            <div field="name" width="120" headerAlign="center" align="center" allowSort="false">姓名</div>   
	            <div field="crimid" width="120" headerAlign="center" align="center" allowSort="false"  renderer="onActionRenderer">罪犯编号</div>
	            <div field="areaname" width="120" headerAlign="center" align="center" allowSort="false">所属监区</div> 
	            <c:if test="${unitlevel eq '2'}">
	            <div field="jailname" width="120" headerAlign="center" align="center" allowSort="false">所属监狱</div> 
	            </c:if>
	            <div field="jincheng" width="120" headerAlign="center" align="center" allowSort="false">进程</div>
	        </div>
	    </div>
	    <div id="detailGrid_Form" style="display:none;">
	        <div id="employee_grid" class="mini-datagrid" showPager="false" showVGridLines="false" showHGridLines="false"
	        style="width:100%;height:260px;" url="jxjs/selectFlowForScar.json">
	            <div property="columns">
	                <div field="optime" width="26" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">审批时间
	                    <input property="editor" class="mini-textbox" />
	                </div>                
	                <div field="duty" width="20" align="center" headerAlign="center" allowSort="true">职务</div>            
	                <div field="opname" width="20" align="center" headerAlign="center" allowSort="true">审批人</div>
	                <div field="opinion" width="20" align="center" headerAlign="center" allowSort="true">评判意见 </div>                                    
	                <div field="reason" align="left" headerAlign="center" allowSort="true">评判理由</div>                                    
	            </div>
	        </div>    
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse(); 
        var grid = mini.get("datagrid1");
        grid.sortBy("anhao","desc");
        grid.load();
        
        //全程留痕
        var employee_grid = mini.get("employee_grid");
        var detailGrid_Form = document.getElementById("detailGrid_Form");
        
        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            employee_grid.on("drawcell", function (e) {
           		e.cellStyle = "background:#fceee2";
            });
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            employee_grid.load({flowdraftid: row.flowdraftid ,applyid:row.crimid,flowdefid:row.flowdefid});
        }
        
		grid.on("drawcell",function(e){
	     	 var record = e.record,
		     column = e.column,
		     field = e.field,
		     value = e.value;
		     var nodeid = record.nodeid;
		     //给帐号列，增加背景色
             if (field == "jincheng" ) {
            	if(nodeid=='N0003'){
               	e.cellStyle = "background:#FFEC8B";
               }else if(nodeid=='N0002'){
               	e.cellStyle = "background:#DEB887";
               }else if(nodeid=='1'){
               	e.cellStyle = "background:green";
               }else if(nodeid=='0'){
               	e.cellStyle = "background:#52CEEE";
               }else if(nodeid=='-1'){
               	e.cellStyle = "background:red";
               }
             }
        });
         function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var s = ' <a class="Edit_Button" href="javascript:checkaip(\'' + uid + '\')" >'+record.crimid+'</a>&nbsp;&nbsp;';
            return s;
        }
        function checkaip(id){
            var row = grid.getSelected();
            if (row) {
            	var tempid = mini.get("tempid").getValue();
            	var url="tocaseLookTabPage.action?1=1&crimid="+row.crimid+"&flowid="+row.flowid+"&flowdraftid="+row.flowdraftid+"&flowdefid="
            		+row.flowdefid+"&menuid=10096"+"&tempid="+tempid+"&jxstempid=${jxstempid}"+"&docid=${jxstempid}"+"&jysdocid=${jysdocid}";
                mini.open({
                    url: url,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "案件查看", width: 900, height: 550,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
        function search() {
            var flowdefid = mini.get("flowdefid").getValue();
            var processid = mini.get("processid").getValue();
       		var depid = mini.get("depid").getValue();
       		var key = mini.get("key").getValue();
       		grid.load({ processid: processid, key : key, depid: depid,flowdefid:flowdefid});
        }
        function onKeyEnter(e) {
            search();
        }  
  </script>
</body>
</html>
