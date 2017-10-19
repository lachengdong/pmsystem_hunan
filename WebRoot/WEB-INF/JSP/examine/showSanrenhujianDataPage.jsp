<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯互监查看页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
     .actionIcons span
        {
            width:16px;
            height:16px;
            display:inline-block;
            background-position:50% 50%;
            cursor:pointer;
        }
    </style>
</head>
<body >
	<div id="form1" class="mini-toolbar"  style="padding:2px;border:0;" align="right">
		编组时间：<input id="ctcchangedate" name="ctcchangedate" value="${ bianzushijian}" class="mini-datepicker" style="width:130px;" format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>&nbsp;&nbsp;
		互监类型：<input id="hujianleixing" name="ctctype" value="${ ctctype}"  class="mini-textbox" emptyText="互监类型"  style="width:100px;"/> &nbsp;&nbsp;
		部门：<input id="bumen" name="bumen" class="mini-textbox" emptyText="部门" value="${jianqu }" style="width:100px;"/> &nbsp;&nbsp;
		第<input id="ctcremark" name="ctcremark" value="${ctcremark }" changeOnMousewheel="false" class="mini-spinner"  minValue="1" maxValue="999999"  style="width:50px;"/> 组&nbsp;&nbsp;
		包联干警<input id="ctcassurepolice" name="ctcassurepolice" value="${ctcassurepolice }"  class="mini-textbox" style="width:100px;"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="mini-button" iconCls="icon-undo"  plain="true" onclick="cancel()" >返回</a>
	</div>  
	<div class="mini-fit" >
	     <div id="datagrid1" url="getSanrenhujianDetailListByid.action?hujianid=${hujianid }" class="mini-datagrid" style="width:100%;height:100%;" idField="id" allowCellEdit="true"allowCellSelect="true">
        <div property="columns">
            <div field="CTCGROUPNUM" width="40" headerAlign="center"  align="center">互监组数
            </div>                
            <div field="CRIMINALID" width="40" headerAlign="center"  align="center">组长
            </div> 
           <div field="REMARK" width="150" headerAlign="center" align="center"  >组员
           </div>        
        </div>
    </div>   
  	</div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
	//取消        
    function cancel() {
	    var url = "<%=path %>/toSanrenhujianPage.action?1=1";
		self.location.href=url;
	}
</script>
</body>
</html>