<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>申控检坦快速查询</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }   
    </style>
</head>
<body onload="init('10206');">
     <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
         <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
          <table >
             <tr>
                <td style="width:100%;">
                </td>
                <td style="white-space:nowrap;">
                	<input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10206')"></a>
                </td>
            </tr>
		</table>
    </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	url="ajaxGetzfskjtList.action?1=1"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        <!-- <div type="checkcolumn" width="15px"></div>
	        	 <div field="departid" headerAlign="center"  align="center" allowSort="true" width="40px;">所属部门</div> -->
	        	<div field="applyid" headerAlign="center"  align="center" allowSort="true" width="30px;">罪犯编号</div>
	        	<div field="applyname" headerAlign="center"  align="center" allowSort="true" width="25px">姓名</div>
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="80px;">文书简介</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="25px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="status" headerAlign="center"  align="center" allowSort="true" renderer="onStatusRenderer" width="30px">审批状态</div>
	    		<div field="commenttext" headerAlign="center"  align="center" allowSort="true" width="30px">审批意见</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="30px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("flowdraftid", "desc");

		function onActionRenderer(e) {
			var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
	     	return s;
        }
       function chooseOne(menuid) {
			var row = grid.getSelected();//获取选中记录
			if(row){
				var name = mini.get("name").getValue();
		    	mini.open({
	                url: "toregistrationAddPage.action?1=1&flowdraftid="+row.flowdraftid+"&menuid="+menuid+"&crimid="+row.applyid,
	                showMaxButton: true,
	             	allowResize: false, 
	                title: "申控检坦", width: 900, height: 500,
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = { action:"edit" };
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action){
	                    grid.reload();
	                }
	            });
			}else{
				alert(confirmMessage);
			}
		}
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid.load({ key: key });
        }
    </script>
</body>
</html>