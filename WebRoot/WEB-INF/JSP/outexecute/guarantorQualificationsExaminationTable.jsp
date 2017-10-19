<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>保证人资格审查表</title>
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
<body onload="init('${menuid}');">
	  <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
	      <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	      <input name="tempid" id="tempid" class="mini-hidden" value="JWZX_BZRZGSCB"/>
           <table >
              <tr>
               	 <td style="width:100%;">
              		  <a class="mini-button" iconCls="icon-add" plain="true" style="display:none;" id="11790" onclick="add('11790')">新增</a>
				      <a class="mini-button" iconCls="icon-download" plain="true" style="display:none;" id="12860" onclick="saveNext()">下一个</a>
				      <a class="mini-button"  iconCls="icon-close" plain="true"  onclick="onCancel();">关闭</a>	
				      <span style="padding-left:40px;">罪犯编号：${crimid}</span>
        			  <span style="padding-left:40px;">罪犯姓名：${name}</span>
               	 </td>
                 <td style="white-space:nowrap;">
					 <!-- 操作说明 -->
			 		 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a> 
                 </td>
              </tr>
		  </table>
     </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="getGuarantorQualificationsExaminationTableList.action?1=1&crimid=${crimid}" multiSelect="true" >
	        <div property="columns"> 
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="120px;">文书简介</div>
	    		<div field="applyname" headerAlign="center"  align="center" allowSort="true" width="40px">申请人</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="40px">申请时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="40px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="40px">操作时间</div>
	    		<div field="flowid" headerAlign="center"  align="center" allowSort="true" renderer="onStatusRenderer" width="30px">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="30px">操作</div>  
			</div>             
	    </div>
    </div>
	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("flowdraftid", "desc");
        
        
        //渲染状态
        function onStatusRenderer(e){
        	var record = e.record;
	        var s = '';
	        if(record.flowid){
	        	if(record.nodeid == '1'){
	       			s = '通过';
	       		}else if(record.nodeid == '-1'){
	       			s = '未通过';
	       		}else{
	       			s = '审批中';
	       		}
	        }else{
       			s = '未审批';
       		}
            return s;
        }
		function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.flowid){
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(11793);" >查看</a>';
        	}else {
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(11796);" >修改</a>';
        	}
            return s;
        }
	    //新增
	    function add(menuid){
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "addGetGuarantor.action?1=1&tempid="+tempid+"&crimid="+crimid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "保证人资格审核表", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text) {
                    grid.reload();
                }
            });
	    }
       
		 function xiugai(menuid) {
			var row = grid.getSelected();
			if(row){
				mini.open({
	                url: "addGetGuarantor.action?1=1&flowdraftid="+row.flowdraftid+"&menuid="+menuid+"&crimid="+row.applyid,
	                showMaxButton: true,
	             	allowResize: false, 
	                title: "保证人资格审核表", width: 900, height: 500,
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
				alert(confirmMessage );
			}
		}
		 function saveNext (){
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "guarantorQualificationsExaminationTable.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	onCancel();
	        }
	    }
		function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onCancel(){
        	var url = "GuarantorCrimidChooseList.action?1=1";
			self.location.href=url;
        }
    </script>
</body>
</html>