<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>公示情况记录</title>
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
<body onload="init('${menuid}');">  
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
             	<td style="width:100%;">
                      <a class="mini-button" style="display:none;" id="12592" iconCls="icon-add" plain="true" onclick="add(12592)">新增</a>
                      <a class="mini-button" style="display:none;" id="12236" iconCls="icon-add" plain="true" onclick="add(12236)">新增</a>
                      <input name="sdid" id="orgid" class="mini-combobox" style="width:150px;" valueField="ORGID" textField="NAME" required="false" onvaluechanged="onjianyuchanged"
                      url="<%=path%>/getDepartList.action?1=1&qtype=jianqu" allowInput="true" emptyText="请选择监区..." nullItemText="请选择监区..." showNullItem="true"/>
                </td>
                <td style="white-space:nowrap;">
               	 <input class="mini-textbox" id="key" class="mini-textbox" emptyText="文书简介"  onenter="onKeyEnter"/>
               	 <a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
		                         操作说明 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('12126')"></a>
              </td> 
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
          idField="spid" multiSelect="true"  allowAlternating="true"  virtualScroll="false" url="<%=path%>/jxjs/getPublicRecordsList.json?1=1&tempid=${tempid}"
          showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <div type="checkcolumn" width="15px"></div>
	    	  <div field="introduction" headerAlign="center" align="left" allowSort="true" width="100px;">文书简介</div>
	    	  <div field="departid" headerAlign="center" align="center" allowSort="true" width="30px;">部门</div>
	    	  <div field="opid" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    	  <div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
              <div id="action" id="action" width="40px" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("docid","desc");
        grid.load();
        
        function onActionRenderer() {
      	   var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
    	   return s;
        }
        //新增
        function add(menuid) {
        	var orgid = mini.get("orgid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var fatherMenuid = mini.get("menuid").getValue();
        	if(orgid){
        		var url = "<%=path%>/jxjs/toPublicRecords.page?1=1";
        			url += "&menuid="+menuid+"&orgid="+orgid+"&tempid="+tempid+"&fatherMenuid="+fatherMenuid;
				self.location.href=url;
        	}else{
        		alert( "请选择监区！");
        	}
        }
        //修改
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var tempid = mini.get("tempid").getValue();
        	var fatherMenuid = mini.get("menuid").getValue();
        	if(row){
        		var url = "<%=path%>/jxjs/toPublicRecords.page?1=1";
        			url += "&menuid="+menuid+"&docid="+row.docid+"&tempid="+tempid+"&fatherMenuid="+fatherMenuid;
				self.location.href=url;
        	}else{
        		alert(confirmMessage);
        	}
        }
      //删除
       function shanchu(){
       	var row = datagrid.getSelected();//获取选中记录
           if (row) {
               if (confirm("确定删除选中记录？")) {
                   datagrid.loading("操作中，请稍后......");
                   $.ajax({
                       url: "<%=path%>/jxjs/deletePublicRecords.json?1=1&docid="+row.docid,
                       type: "post",
                       success: function (text) {
                           datagrid.reload();
                       },
                       error: function () {
                       	alert("操作失败!");
                       	datagrid.reload();
                       }
                   });
               }
           } else {
               alert(confirmMessage);
           }
		}
        //当部门变化时查询数据
        function onjianyuchanged(){
        	var orgid = mini.get("orgid").getValue();
            grid.load({ orgid: orgid });
        	grid.sortBy("docid","desc");
        }
        //按条件查询数据
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("docid","desc");
            grid.load({ key: encodeURI(key) });
        }
    </script>    
</body>  
</html>