<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>档案查看列表信息</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>

  </head>
  
  <body onload="init('1600_001_005');">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<table>
				<tr>
					<td  style="width: 100%;">
				       <a class="mini-button" style="display:none;" id="10629" iconCls="icon-node" plain="true" onclick="lookarchives('10629');">合并查看</a>
					</td> 
					<td style="white-space: nowrap;" >
					<input name="sdid" id="sdid" class="mini-combobox" style="width:110px;" valueField="ORGID" textField="NAME" required="false" onvaluechanged="search()"
						url="<%=path%>/getDepartList.action?1=1&qtype=jianqu" disabled="true" emptyText="--全部--" nullItemText="--全部--" showNullItem="true"/>
						<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
						<input class="mini-textbox" id="key" class="mini-textbox" 
							emptyText="请输入罪犯编号、姓名、拼音" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">快速查询</a>
						<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help"
							onclick="chakanshuoming('1600_001_005');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
					</td>
				</tr>
			</table>

		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false" url="<%=path%>/flow/ajaxgetcrimwitharchs.json"
				style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
				multiSelect="true" allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
				<div property="columns">
					<!--  <div type="checkcolumn" width="10"></div> -->
					<div type="indexcolumn" width="10" headerAlign="center" align="center">序号</div>
					<div field="crimid" width="25" headerAlign="center" allowSort="true" align="center">编号</div>
					<div field="name" width="25" headerAlign="center" allowSort="true" align="center">姓名</div>
					<div field="crimclass" width="25"  headerAlign="center"  	allowSort="true" align="center" >罪犯类型</div>
		            <div field="causeaction" width="40"  headerAlign="center"  	allowSort="true" align="center" >罪名</div>
		            <div field="originalyear" width="30" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
		           	<div field="sentencestime" width="25" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div> 
		            <div field="courtchangeto" width="25"  headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">现刑期止日</div>   					
					<div field="gender" width="15" headerAlign="center" allowSort="true" align="center">性别</div>
					<div field="nation" width="15" headerAlign="center" allowSort="true" align="center">民族</div>
					<div field="birthday" width="25" headerAlign="center" allowSort="true" align="center" renderer="onDateRenderer">出生日期</div>
					<div field="registeraddressdetail" width="30" headerAlign="center" allowSort="true" align="center">户籍地址</div>
					<div width="25" headerAlign="center" align="center" allowSort="false" renderer="onCaoZuo">操作</div>  
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
		 	var grid = mini.get("datagrid");
		 	var crimid = '${crimid}';
		 	if(crimid !=''&& crimid !=null ){
		 		grid.load({ key: crimid });
		 	}else{
		 		grid.load();
		 	}
			
			grid.sortBy("crimid", "desc");
			//grid.sortBy("docid||classification", "asc");
			function search() {
			     var key = mini.get("key").getValue();
			     var sdid = mini.get("sdid").getValue();
			     grid.load({ key: key, sdid:sdid });
			}
			function onKeyEnter(e) {
			   search();
			}
			//根据罪犯编号跳转到档案查看页面
	        function chooseOne(menuid) {
	        	var row = grid.getSelected();
	        	if(row){
	        		var url = "<%=path%>/flow/tolookarchives.action?1=1&crimid="+row.crimid+"&menuid="+menuid;
					self.location.href=url;
	        	}else{
	        		alert( "请选中一条记录！");
	        	}
	        }
			function onCaoZuo(){
				var s = document.getElementById("middlebtns").value;
			    return s;
			}
			
		</script>
  </body>
</html>