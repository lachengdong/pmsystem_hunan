<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>判裁信息</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
</head>
<body>
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
			<tr>
				<td style="width: 100%;">
				</td>
				<td style="white-space: nowrap;">
					<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音" onenter="onKeyEnter" />
					<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
					<!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width: 100%; height: 100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20" 
			allowAlternating="true" allowResize="false" url="queryPanCaiInfo.action?1=1" multiSelect="true">
			<div property="columns">
				<div type="checkcolumn" width="15px"></div>
				<div field="crimid" headerAlign="center" align="center" allowSort="true" width="40px;">罪犯编号</div>
				<div field="criname" headerAlign="center" align="center" allowSort="true" width="30px;">罪犯姓名</div>
				<div field="orgname2" headerAlign="center" align="center" allowSort="true" width="50px">监区及分监区</div>
				<div field="arrestauthority" headerAlign="center" align="left" allowSort="true" width="60px">逮捕机关</div>
				<div field="judgmentname" headerAlign="center" align="left" allowSort="true" width="60px">原判法院</div>
				<div field="caseorgshort" headerAlign="center" align="left" allowSort="true" width="60px">判决字号</div>
				<div field="punishmenttype" headerAlign="center" align="center" allowSort="true" width="40px">判决类型</div>
				<div field="sentencetype" headerAlign="center" align="center" allowSort="true" width="0px">一审(二审)</div>
				<div field="maincase" headerAlign="center" align="left" allowSort="true" width="60px">案由</div>
				<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="30px">操作</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		 mini.parse();
	     var grid = mini.get("datagrid");
	     grid.load();
	     
	     function onActionRenderer() {
	     	var s = "<a id='chuli' class='Edit_Button' href='javascript:chuli()'>处理</a>";
		    return s;
	     }
	     
	     function chuli(){
		     var row = grid.getSelected();
		     var crimid = row.crimid;
		     var criname = row.criname;
		     var url = '<%=path%>/basicInfo/goDanGePanCaiInfoView.action?1=1&crimid='+crimid+'&name='+encodeURI(encodeURI(criname));
	    	 mini.open({
			     type:'POST',
			     url:url,
			     title: "判决详细信息", width: "750", height: "450",
			     success:function (){
			         var iframe = this.getIFrameEl();
			         var data = {};
			         iframe.contentWindow.SetData(data);
			     },
			     ondestroy:function(){
			     	grid.reload();
			     }
	        });
		 }
	   //快速查询
         function onKeyEnter(e) {
            search();
         }
	     function search(){
              var key = mini.get("key").getValue();
              grid.load({key:key});
		 }
	</script>
</body>
</html>