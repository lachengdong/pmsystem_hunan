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
<title>罪犯监区月报表</title>
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
                	<input id="nodeid" name="nodeid" class="mini-hidden" value="${nodeid}"/>
	            	<input id="identity" name="identity" class="mini-hidden" value="${identity}"/>
	            	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
                    
					合计月得分<input name="totalscore" id="totalscore" style="width:80px;" inputStyle="text-align:center;" class="mini-textbox" value="0.00" enabled="false" />
                	<span class="separator"></span>
                </td>
                <td style="white-space:nowrap;">
                	<!-- <input id="orgid" name="orgid" class="mini-combobox" textField="name" valueField="dorgid" valueFromSelect="false"
                	showNullItem="true" nullItemText="--全部--"	enabled="true" emptyText="部门过滤" style="width:150px;" 
                	url="<%=path %>/org/ajax/getdepartunderself.action?1=1"  onvaluechanged="ondeptchanged"  /> -->
					<input id="caseyear" class="mini-textbox" valueField="id" textField="text"  enabled="false" style="width:40px;" value="${caseyear}" />年
	    			<input id="casemonth" class="mini-textbox" valueField="id" textField="text" enabled="false" value="5"  style="width:30px;"  />月<!--${casemonth}-->
					<a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>    
                </td>
            </tr>
        </table>           
    </div>
    <div class="mini-fit" >
    	<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" ondrawsummarycell="onDrawSummaryCell"
        url="<%=path %>/check/getCriminalForMonthCheck.json?1=1&caseyear="+${caseyear}+"casemonth="+${casemonth} idField="id" allowAlternating = "true" 
        allowResize="false" pageSize="800" sizeList="[800]" allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
        editNextOnEnterKey="true"  editNextRowCell="true" oncellcommitedit="OnCellCommitEdit">
        	<div property="columns">
	            <div type="indexcolumn">序</div>
	            <div name="crimid"  field="crimid" align="center" headerAlign="center" allowSort="true" width="80" >罪犯编号
	            </div>
	            <div field="crimname" width="60" align="center"	headerAlign="center" allowSort="true" >姓  名
	            </div> 
            	<div name="yuedefen" field="yuedefen" width="40" align="right"	summaryType="sum"  headerAlign="center"  value="0.00"  >月得分
               		<input name="yuedefenspi" property="editor" class="mini-spinner" minValue="0" maxValue="4" style="width:100%;"  />
           		</div>
	            <div field="fengxianfen" width="40" align="right"	headerAlign="center" value="0.00" allowSort="false">风险分
	                <input property="editor" class="mini-spinner" style="width:200px;" minWidth="200" minHeight="50"/>
	            </div>
	            <div field="jiangfen" width="40" align="right" headerAlign="center" value="0.00" allowSort="false">奖  分
	            </div>
	            <div field="jiangfenremark" width="120" align="left" headerAlign="center" allowSort="flase">奖分事由
	            </div>
	            <div field="koufen" width="40" align="right" headerAlign="center" value="0.00" allowSort="false">扣 分
	            </div>
	            <div field="koufenremark" width="120" align="left" headerAlign="center" allowSort="false">扣分事由
	            </div>
	            <div field="duixian" width="40" align="right" headerAlign="center" value="0.00" allowSort="false">兑现
	                <input property="editor" class="mini-spinner" style="width:60px;"  minWidth="60" minHeight="50"/>
	            </div>
	            <div field="duixianremark" width="120" align="left" headerAlign="center" allowSort="false">兑现说明
	                <input property="editor" class="mini-textbox" style="width:120px;" minWidth="120" minHeight="50"/>
	            </div>
	            <div field="yuezongfen" width="40" align="left" headerAlign="center" value="0.00" allowSort="false">月总分
	            </div>
	             <div field="leijizongfen" width="60" align="left" headerAlign="center" value="0.00" allowSort="false">累计总分
	            </div>
	             <div field="lastleijizongfen" width="60" align="left" headerAlign="center" allowSort="false">上月累计总分
	            </div>
	            <div field="orgid1" width="60" align="left" headerAlign="center" allowSort="true" visible = "false">分监区id
	            </div>
	            <div field="orgid2" width="60" align="left" headerAlign="center" allowSort="true" visible = "false">监区id
	            </div>
	            <div field="departid" width="60" align="left" headerAlign="center" allowSort="true" visible = "false">监狱id
	            </div>
	            <div field="yuzhifenflag" width="60" align="left" headerAlign="center" allowSort="true" visible = "false">预支分<!-- visible = "false" -->
	            </div>
	            <div field="biddate" width="60" align="left" headerAlign="center" allowSort="true" visible = "false">月份<!-- visible = "false" -->
	            </div>
	        </div>
    	</div>
    </div>
     
    <iframe id="exportIFrame" style="display:none;"></iframe>
    
    <!--导出Excel相关HTML-->
     <form id="excelForm"  action="export.aspx?type=excel" method="post" target="excelIFrame">
        <input type="hidden" name="columns" id="excelData" />
    </form>
    <iframe id="excelIFrame" name="excelIFrame" style="display:none;"></iframe>
    
    <script type="text/javascript">
        mini.parse();

        var grid = mini.get("datagrid1");
        grid.sortBy("crimid", "asc");
        grid.load();

        function saveData() {
            var operatetype = '';
            var data = grid.getChanges(true);
            var json = mini.encode(data);
            var caseyear = mini.get("caseyear").getValue();
            var casemonth = mini.get("casemonth").getValue();
            $.ajax({
                url: "<%=path%>/check/ajaxBySave.json?1=1",
                data: { data: json,caseyear:caseyear,casemonth:casemonth},
                type: "post",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }


        function onDrawSummaryCell(e) {
        	var totalscore = mini.get("totalscore");
            var result = e.result;
            var grid = e.sender;
            var yuedefen = 0;
            var yuzhifenflag = "";
            if (e.field == "yuedefen") {
            	yuedefen = e.value;
            	totalscore.setValue(yuedefen.toFixed(2));
            }
        }
        
        function OnCellCommitEdit(e){
        	var multiplicand = Math.pow(10,2);
        	var yuezongfen = 0;
        	var leijizongfen = 0;
        	var grid = e.sender;
        	var record = e.record,
	        column = e.column,
	        field = e.field,
	        value = e.value;
        	var yuzhifenflag = record.yuzhifenflag;
        	var yuedefen2 = record.yuedefen2;
            if (column.field == "yuedefen") {
            	if(yuzhifenflag != '1'){
            		yuezongfen = parseFloat(e.value)+parseFloat(record.fengxianfen)+parseFloat(record.jiangfen)-parseFloat(record.koufen)-parseFloat(record.duixian);
            	}else{
            		alert("该月得分为预估分，不允许修改！");
            		record.yuedefen = '4';
            		record.yuezongfen = '23';
            		return false;
            	}
            }
            if (column.field == "fengxianfen") {
            	yuezongfen = parseFloat(record.yuedefen)+parseFloat(e.value)+parseFloat(record.jiangfen)-parseFloat(record.koufen)-parseFloat(record.duixian);
            }
            if (column.field == "duixian") {
            	yuezongfen = parseFloat(record.yuedefen)+parseFloat(record.fengxianfen)+parseFloat(record.jiangfen)-parseFloat(record.koufen)-parseFloat(e.value);
            }
            
            record.yuezongfen = Math.round(yuezongfen*multiplicand)/multiplicand;
            record.leijizongfen =Math.round((parseFloat(yuezongfen)+parseFloat(record.lastleijizongfen))*multiplicand)/multiplicand;
        }
    </script>
</body>
</html>