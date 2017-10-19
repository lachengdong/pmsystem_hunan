<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>入监收押通知</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
</head>
<body onload="init('10195');">
      <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
      <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	  <input name="tempid" id="tempid" class="mini-hidden" value="XFZX_ZFRJSYTZS"/>
           <table >
              <tr>
               	 <td style="width:100%;">
               	 	 <a class="mini-button" style="display:none;" id="10195_01" iconCls="icon-add" plain="true" onclick="add('10195_01')">新增</a>
              		 <!-- <a class="mini-button" style="display:none;" id="10195_02" iconCls="icon-remove" plain="true" onclick="deleteBatch()">批量删除</a> -->
               	 </td>
                 <td style="white-space:nowrap;">
		             <input class="mini-textbox" id="key" class="mini-textbox" emptyText="文书简介"  onenter="onKeyEnter"/>
		             <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
					 <!-- 操作说明 -->
			 		 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid }')"></a> 
                 </td>
              </tr>
		  </table>
     </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="<%=path%>/inPrisonNotice/getInPrisonNoticeList.json?1=1&tempid=XFZX_ZFRJSYTZS" multiSelect="true" >
	        <div property="columns">
	        	<!--<div type="checkcolumn" width="15px"></div>
	    		 <div field="orgid" headerAlign="center"  align="center" allowSort="true" width="30px;">所属部门</div>-->
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="text1" headerAlign="center"  align="center" allowSort="true" renderer="onStatusRenderer" width="30px">审批状态</div>
	    		<div field="text2" headerAlign="center"  align="center" allowSort="true" width="30px">审批意见</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
			</div>             
	    </div>
    </div>
	<script type="text/javascript">
        mini.parse();
    	var grid = mini.get("datagrid");
    	grid.load();
    	grid.sortBy("applyid", "desc");
        
        function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
	    	if(record.flowid=='1'){
	    		s += ' <a class="Edit_Button"  href="javascript:xiugai(2);" >修改</a>';
	    	}else if(record.flowid=='2'){
	    		s += ' <a class="Edit_Button"  href="javascript:xiugai(1);" >查看</a>';
	    	}else {
	    		s += ' <a class="Edit_Button"  href="javascript:shanchu(3);" >删除</a>';
	    	}
	        return s;
	    }
	    //新增
	    function add(menuid){
	    	var tempid = mini.get("tempid").getValue();
	    	mini.open({
                url: "inPrisonNotice/getInPrisonNoticeFrom.page?1=1&tempid="+tempid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "入监收押通知", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"new" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
	    }   
		 //修改、查看
		function xiugai(type) {
			var menuid = '10195_03';//默认修改
			if(type==1) menuid='10195_05';
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "inPrisonNotice/getInPrisonNotice.page?1=1&flowdraftid="+row.flowdraftid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "入监收押通知", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    grid.reload();
                }
            });
		}     
		//删除
        function shanchu(){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "inPrisonNotice/deleteInPrisonNotice.json?1=1&docid="+id,
                        type: "post",
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
		}
		function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.sortBy("applyid", "desc");
            grid.load({ key: key });
        }
    </script>
</body>
</html>