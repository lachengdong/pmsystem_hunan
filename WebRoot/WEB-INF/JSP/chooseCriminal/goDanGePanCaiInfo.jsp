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
    <input type="hidden" id="hcrimid" value="${crimid }"/>
    <input type="hidden" id="hcriname" value="${criname }"/>
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
			<tr>
				<td style="width: 100%;">
					<!-- <a class="mini-button"  iconCls="icon-close" plain="true"  onclick="onCancel();">关闭</a> -->
				    &nbsp;&nbsp; 罪犯编号：${crimid}&nbsp;&nbsp;&nbsp;&nbsp;罪犯姓名：${criname}
				</td>
				<td style="white-space: nowrap;">
				    <a class="mini-button" iconCls="icon-add" plain="true" onclick="save('new');">新增</a>
				    <a class="mini-button" iconCls="icon-edit" plain="true" onclick="save('edit');">修改</a>
					<!-- 操作说明 -->
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:420px;" url="queryTbdataSentchage.action?1=1&crimid=${crimid}" idField="id" 
        allowResize="true" pageSize="20" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
			<div property="columns">
			    <div type="checkcolumn"></div>
				<div field="crimid" headerAlign="center" align="left" allowSort="true" width="0px;">罪犯编号</div>
				
				<div name="courtname"  field="courtname" headerAlign="center" allowSort="true" width="70px;" >原判法院
                      <input property="editor" class="mini-textarea" style="width:100%;" minWidth="100" />
                </div>
                <div name="courtyear"  field="courtyear" headerAlign="center" allowSort="true" width="35px;" >判决年
                      <input property="editor" class="mini-textarea" style="width:100%;" minWidth="80" />
                </div>
                <div name="courtshort"  field="courtshort" headerAlign="center" allowSort="true" width="50px;" >判决字
                      <input property="editor" class="mini-textarea" style="width:100%;" minWidth="80" />
                </div>
                <div name="courtsn"  field="courtsn" headerAlign="center" allowSort="true" width="30px;" >判决号
                      <input property="editor" class="mini-textarea" style="width:100%;" minWidth="80" />
                </div>
                
                <div name="courtsanction"  field="courtsanction" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd" width="50px;" >原判日期
                      <input property="editor" class="mini-textarea" style="width:100%;" minHeight="50" />
                </div>
                
                <div name="pundata"  field="pundata" headerAlign="center" allowSort="true" width="50px;" >判决刑期
                      <input property="editor" class="mini-textarea" style="width:100%;" minHeight="50" />
                </div>
                <div name="execdate"  field="execdate" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd"  width="50px;" >执行日期
                      <input property="editor" class="mini-textarea" style="width:100%;" minHeight="50" />
                </div>
                <div name="courtchangefrom"  field="courtchangefrom" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd"  width="50px;" >刑期起日
                      <input property="editor" class="mini-textarea" style="width:100%;" minHeight="50" />
                </div>
                <div name="courtchangeto"  field="courtchangeto" headerAlign="center" allowSort="true" width="50px;" dateFormat="yyyy-MM-dd"  >刑期止日
                      <input property="editor" class="mini-textarea" style="width:100%;" minHeight="50" />
                </div>
                <div field="category" headerAlign="center" align="center" allowSort="true" width="0">案</div>
                <div field="action" headerAlign="center" align="center"  renderer="onActionRenderer" allowSort="true" width="25">删除</div>
            </div>
		</div>
	</div>
	<script type="text/javascript"> 
	     
		 mini.parse();
	     var grid = mini.get("datagrid1");
	     grid.load();
         
	     function onActionRenderer(){
	    	 var s = "<a id='chuli' class='Edit_Button' href='javascript:deleteCase()'>删除</a>";
			 return s;
		 }
	     //进入新增页面 
	     function save(state){
		     if(state == 'new'){
		    	 var crimid=$("#hcrimid").val();
	             var criname = $("#hcriname").val();
	             var url = "<%=path%>/basicInfo/saveTbdataSentchageInfo.action?1=1&crimid="+crimid+"&state="+state+"&criname="+encodeURI(encodeURI(criname));
	             self.location.href=url;
			 }else if(state == 'edit'){
                 var row = grid.getSelected();
                 if(row == undefined){alert("请选择一条数据!");return;}
                 var crimid = row.crimid;
	             var criname = $("#hcriname").val();
                 var category = row.category;
                 var courtsanction = row.courtsanction;
                 var url = "<%=path%>/basicInfo/saveTbdataSentchageInfo.action?1=1&crimid="+crimid+"&state="+state+"&criname="+encodeURI(encodeURI(criname))+"&category="+category+"&courtsanction="+courtsanction;
	             self.location.href=url;
		     }
             
		 }
	 	 //删除所选择的 内容
	 	 function deleteCase(){
             var row = grid.getSelected();
             $.ajax({
                 type:'post',
                 url:'deleteCase.action?1=1&crimid='+row.crimid+"&courtsanction="+row.courtsanction,
                 success:function (text){
                     alert("删除成功!");
                     grid.load();
                 }
             });
	     }
	</script>
</body>
</html>