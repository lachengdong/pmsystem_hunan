<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="java.util.Map"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>罪犯监区月报表查看</title>
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="/pmsys/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }  
</style>
</head>
<body>
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                	
                </td>
                <td style="white-space:nowrap;">
                	<input id="orgid" name="orgid" class="mini-combobox" textField="name" valueField="dorgid" valueFromSelect="false"
                	showNullItem="true" nullItemText="--全部--"	enabled="true" emptyText="部门过滤" style="width:150px;" 
                	url="<%=path %>/org/ajax/getdepartunderself.action?1=1"  onvaluechanged="ondeptchanged"  />
					<input id="caseyear" class="mini-combobox" valueField="id" textField="text" url="<%=path %>/getDateUPDownArea.action"   style="width:60px;" value="${caseyear}" onvaluechanged="ondeptchanged" />
	    			<input id="casemonth" class="mini-combobox" valueField="id" textField="text" data="Months"  value="${casemonth}"  style="width:65px;" onvaluechanged="ondeptchanged" />
                </td>
            </tr>
        </table>           
    </div>
    <div class="mini-fit" >
    	<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
        url="<%=path %>/check/ajaxGetApprovalList.json?1=1&tempid=jqmonthreport&flowdefid=doc_ykhbbsp" idField="id" allowAlternating = "true"
        allowResize="false" sizeList="[10,20,50,100]" pageSize="20" allowCellEdit="true" allowCellSelect="true" multiSelect="true" >
        	<div property="columns">
	        	<!--<div type="checkcolumn" width="15px"></div>
	    		 <div field="orgid" headerAlign="center"  align="center" allowSort="true" width="30px;">所属部门</div>-->
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="text1" headerAlign="center"  align="center" allowSort="false" renderer="onStatusRenderer" width="30px">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
	        </div>

    	</div>
    </div>
    <script type="text/javascript">
    	var Months = [{ id: 1, text: '一月' }, { id: 2, text: '二月'},
                  { id: 3, text: '三月' }, { id: 4, text: '四月'},
                  { id: 5, text: '五月' }, { id: 6, text: '六月'},
                  { id: 7, text: '七月' }, { id: 8, text: '八月'},
                  { id: 9, text: '九月' }, { id: 10, text: '十月'},
    			   { id: 11, text: '十一月' }, { id: 12, text: '十二月'}];
        mini.parse();

        var grid = mini.get("datagrid1");
        grid.sortBy("flowdraftid", "asc");
        grid.load();
        

        //////////////////////////////////////////////////////

        function search() {
            var key = mini.get("key").getValue();
			var caseyear = mini.get("caseyear").getValue();
			var casemonth = mini.get("casemonth").getValue();
			var datestr = caseyear+casemonth;
            grid.load({ key: key,datestr:datestr});
        }

        function onKeyEnter(e) {
            search();
        }

      	//部门过滤
		function ondeptchanged() {
			var orgid = mini.get("orgid").getValue();
			var change = "change";
			grid.load({orgid:orgid,change:change,key:orgid});
		}
		function domonthreport(resid){
			var now = new Date();
			var caseyear = now.getFullYear();    
			var casemonth = now.getMonth()+1;       
			var url = "<%=path %>/check/toMonthReport.page?1=1&menuid="+resid+"&caseyear="+caseyear+"&casemonth="+casemonth;
			self.location.href=url;
		}
		function onActionRenderer() {
         	//var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
         	var s = '${middlestr}';
    	    return s;
        }
		 //查看
		function chakan(menuid) {
			var row = datagrid.getSelected();//获取选中记录
			var optime = mini.get("optime").getValue();
			var caseyear = mini.get("caseyear").getValue();
			var casemonth = mini.get("casemonth").getValue();
			var url = "<%=path %>/check/toMonthReport.page?1=1&menuid="+menuid+"&caseyear="+caseyear+"&casemonth="+casemonth;
			self.location.href=url;
		}
    </script>
</body>
</html>