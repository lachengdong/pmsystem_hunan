<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>解回再审登记</title>
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
<body onload="init('${menuid}');">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
           <tr>
            <td style="width:100%;">
            	 <a class="mini-button" style="display:none;" id="11153" iconCls="icon-add" plain="true" onclick="add('11153')">新增</a>
       			 <a class="mini-button" style="display:none;" id="11155" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
       			 <!-- <a class="mini-button" style="display:none;" id="11154" iconCls="icon-remove" plain="true" onclick="">批量删除</a> -->
              	 <a class="mini-button" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
               	 <span style="padding-left:40px;">罪犯编号：${crimid}</span>
       			 <span style="padding-left:10px;">姓名：${name}</span>
            </td>
            <td style="white-space:nowrap;">
                <!-- <input class="mini-textbox" id="key" class="mini-textbox" emptyText="文书简介"  onenter="onKeyEnter"/>
                <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a> -->
				<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('32456')"></a>
            </td>
           </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    		url="ajaxGetzfjhzsspList.action?1=1&crimid=${crimid}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<!-- <div type="checkcolumn" width="15px"></div> -->
				<!-- <div field="departid" headerAlign="center"  align="center" allowSort="true" width="40px;">所属部门</div>-->
				<div field="CONENT" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="APPLYDATETIME" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="OPNAME" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="OPTIME" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="TEXT3" headerAlign="center"  align="center" allowSort=false renderer="onStatusRenderer" width="30px">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("flowdraftid", "desc"); 
		
		function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
	        if(record.TEXT3=='已通过'&&record.TEXT1==null){
	        	s += ' <a class="Edit_Button"  href="javascript:jhzsadd(12591);" >终止</a>';
        	}else{
        		if(record.FLOWID){
	        		s += ' <a class="Edit_Button"  href="javascript:xiugai(11156);" >查看</a>';
	        	}else{
	        		s += ' <a class="Edit_Button"  href="javascript:xiugai(11157);" >修改</a>';
	        	}
        	}
            return s;
        } 

        function search() {
            var key = mini.get("key").getValue();
            datagrid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
        
        
        function add(menuid){
       		var crimid = mini.get("crimid").getValue();
	 		mini.open({
	 	        url: "registrationTwoAdd.action?1=1&tempid=XFZX_JHZSSPB&crimid=" + crimid+"&menuid="+menuid,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "解回再审", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	    });	
 	   }
		//修改
		function xiugai(menuid) {
			var row = datagrid.getSelected();//获取选中记录
	    	mini.open({
                url: "registrationTwoAdd.action?1=1&flowdraftid="+row.FLOWDRAFTID+"&menuid="+menuid+"&crimid="+row.APPLYID,
                showMaxButton: true,
             	allowResize: false, 
                title: "解回再审", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    datagrid.reload();
                }
            });
		}
		//数据保存、提交成功之后自动跳到下一个，没有下一个跳转到罪犯处理页面
	    function saveNext (){
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "registrationTwoList.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	close();
	        }
	    }
	    //手动关闭
	    function close(){
	    	url = "registrationTwo.action?1=1";
	    	self.location.href=url;
	    }
	    //终止
	     function jhzsadd(menuid){
       		var crimid = mini.get("crimid").getValue();
       		var row = datagrid.getSelected();
	 		mini.open({
	 	        url: "jhzszz.action?1=1&flowsn=" + row.FLOWSN+"&menuid="+menuid,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "解回再审终止", width: 400, height: 300,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	    });	
 	   }
    </script>
</body>
</html>