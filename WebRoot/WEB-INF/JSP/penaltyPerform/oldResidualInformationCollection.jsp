<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>老病残信息补录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
    </style>
</head>
<body onload="init('${menuid}')">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>		
        <table >
           <tr>
             <td style="width:100%;">
             <input id="removestatus" name="removestatus" class="mini-hidden" value="${removestatus}"/>
            	<a class="mini-button" style="display:none;" id="11391" iconCls="icon-add" plain="true" onclick="add('11391')">新增</a>
       			<a class="mini-button" style="display:none;" id="11393" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
               	<a class="mini-button" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
            	<span style="padding-left:40px;">罪犯编号：${crimid}</span>
    			<span style="padding-left:10px;">姓名：${name}</span>
             </td>
             <td style="white-space:nowrap;">
                <!-- <input class="mini-textbox" id="key" class="mini-textbox" emptyText="健康情况、致残原因"  onenter="onKeyEnter"/>
                <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
				操作说明 -->
				<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('6363')"></a>
             </td>
           </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  idField="" multiSelect="true" allowAlternating="true" 
	    	 url="getoldResidualInformationCollectionList.action?1=1&crimid=${crimid}"  sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">   		
<!--				 <div type="checkcolumn" width="10px"></div>  -->
				<div field="confirmtime" width="20px" headerAlign="center" renderer="onDateRenderer" allowSort="true" align="center" >认定时间</div>
				<div field="oidtype" width="50px" headerAlign="center" allowSort="true" align="center" >审批种类</div>
				<div field="revoke_rs" width="0px" headerAlign="center" allowSort="false" align="center" >撤消原因</div>
				<div field="revoke_date" width="0px" headerAlign="center" allowSort="false" align="center" >撤销时间</div>
	  			<div field="osdtype" width="50px" headerAlign="center" allowSort="true" align="center" >老病残类别</div>
	 			<div field="oidtypedetail" width="50px" headerAlign="center" allowSort="true" align="left" >审批种类明细</div>
	  			<div field="description" width="50px" headerAlign="center" allowSort="true" align="left" >健康情况概述</div>
	  			<div field="tstatus" width="20px" headerAlign="center" allowSort="true" align="left" renderer="changeValue">状态</div>
	            <!-- <div name="action" width="30px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div> -->
	            <div name="action" headerAlign="center" align="center"  width="60px" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        	grid.sortBy("crimid","desc");
        	grid.load();
        
        	 
        	function onActionRenderer(e){
            	var removestatus = mini.get("removestatus").getValue();
            	var s;
                if (removestatus =='1') {//刑罚科管理员
                	 s= '<a class="Edit_Button" href="javascript:add(11394);">查看</a>&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:add(11395);">修改</a>&nbsp;&nbsp;<a class="Edit_Button" href="javascript:remove(11396);">删除</a>&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:openUpdateStatus();">撤销</a>'; 
    			}else if(removestatus =='0') {
    	        	 s = '<a class="Edit_Button" href="javascript:add(11394);">查看</a>';
    			}else if(removestatus =='2'){//管教
    				 s= '<a class="Edit_Button" href="javascript:add(11394);">查看</a>&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:add(11395);">修改</a>'; 
    			}
    	     	return s;
            }
        	
        	
        	
        /* function onActionRenderer(e) {
			var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
	     	return s;
        }  */
        
        function add(menuid) {
	    	var data = {};
	    	data.action = "new";//默认新增
	    	data.crimid = '${crimid}';
	    	
	    	if(menuid == "11391"){
		    	var removestatus = mini.get("removestatus").getValue();		    	
				if(removestatus =="0" ||removestatus == null){
					alert("减刑案件已经开始办理或无权限操作!");
					return;
				}
	    	}
	    	
	    	var row = grid.getSelected();
        	if(menuid != "11391"){
        		
        		data.action = "edit";
        		data.id = row.id;
        		
        		data.oidtype = row.oidtype;
        		data.confirmtime = row.confirmtime;
            	var disabilitytype = row.disabilitytype;
            	data.oldtype = null;
            	data.sicktype = null;
            	data.disabilitytype = null;
            	if(disabilitytype){
            		var osdtypeArr = disabilitytype.split("；");
            		for(var i=0; i<osdtypeArr.length; i++){
            			var typeInfo = osdtypeArr[i].split("@");
            			if(typeInfo[0]=='1'){
            				data.oldtype = typeInfo[1];
            			}else if(typeInfo[0]=='2'){
            				data.sicktype = typeInfo[1];
            			}else if(typeInfo[0]=='3'){
            				data.disabilitytype = typeInfo[1];
            			}
            		}
            	}
            	
            	
            	data.oidtypedetail = row.oidtypedetail;
            	data.description = row.description;
            	data.remark = row.remark;
            	
        	}
        	
        	//alert(mini.encode(data));
    		var win=mini.open({
                url: "addOldResidualInformationCollection.action?1=1&crimid="+crimid+"&menuid="+menuid,
                showMaxButton: true,
                allowResize: false, 
                title: "老病残信息补录", width: 800, height: 450,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
	    }
        
        function remove(menuid){
		   var row = grid.getSelected();    
           if (row) {
           		if (confirm("确定删除选中记录？")) {
           			 var id = row.id;
           			 var crimid = row.crimid;
	                 $.ajax({
	                      url: "deleteOldResidualInformationCollection.action",
	                      type: "post",
	                      data:{id:id, crimid:crimid},
	                      success: function (text) {
	                      	 alert("操作成功!");
	                         grid.reload();
	                     },
	                     error: function () {
	                     	alert("操作失败!");
	                     }
	                 });
           		}
             }else{
             	 alert(confirmMessage);
             }
        }
        
        function saveNext(){
        	var id = mini.get("id").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
            var url= "oldResidualInformationCollection.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
            if(index>0){
                self.location.href=url;
            }else{
            	close();
            }
        }
        //快速查询
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
        //跳转到选择罪犯页面
        function close(){
        	var url="oldResidualInformationCollectionChoose.action?1=1";
        	self.location.href=url;
        }
        
        function openUpdateStatus(){
        	var crimid = '${crimid}';
        	var row = grid.getSelected();
        	var id= row.id;
        	var tstatus=row.tstatus;
        	var action="new";
        	if(tstatus!="1"){
	        	var revoke_rs=row.revoke_rs;
	        	var revoke_date=row.revoke_date;
        		action = "edit";
        	}
        	var win=mini.open({
                url: "toChangeOldIllDisability.action?1=1&crimid="+crimid+"&menuid="+menuid+"&id="+id,
                showMaxButton: true,
                allowResize: false, 
                title: "老病残信息补录撤销", width: 800, height: 400,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {crimid:crimid,id:id,revoke_rs:revoke_rs,revoke_date:revoke_date,action:action};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
        
        function changeValue(e){
        	value = e.value;
        	var s = "";
        	if(value != ""){
            	if(value == "2"){
            		s="已撤销";
            	}else{
	            	s="有效";
	            	}
            	}
            return s;
        }

    </script>
</body>
</html>