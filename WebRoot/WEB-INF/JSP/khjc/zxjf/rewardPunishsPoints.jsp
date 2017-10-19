<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>批量、专项奖扣分审批表列表页</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <script src="<%=path%>/scripts/loadaip2.js"></script>
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
                	${topstr}
					<a class="mini-button" id="gb" class="mini-button" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
					<span id="span1" style="padding-left:40px;display:black;">罪犯编号：${crimid}</span>
          			<span id="span2" style="padding-left:10px;display:black;">姓名：${name}</span>
                </td>
                <td style="white-space:nowrap;">
                	<input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="文书简介" style="width:130px;" onenter="onKeyEnter"/>   
                    <a id=search class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('32463')"></a>
                </td>
            </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="rewardPunishsPointsList.action?1=1&tempid=${tempid}&crimid=${crimid}&flowdefid=${flowdefid}" multiSelect="true" >
	         <div property="columns">
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="80px;">文书简介</div>
	    		<div field="applyname" headerAlign="center"  align="center" allowSort="true" width="30px">申请人</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">申请时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
				<div field="status" headerAlign="center"  align="center" allowSort=true width="30px">审批状态</div>
	    		<div field="commenttext" headerAlign="center"  align="center" allowSort="true" width="30px">审批意见</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
	        </div>    
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        if('${crimid}'=='' || '${name}'==''){
        	$("#gb").hide();
        	$("#span1").hide();	
        	$("#span2").hide();	
        }else{
        	$("#key").hide();	
        	$("#search").hide();	
        }
        var datagrid1 = mini.get("datagrid");
        datagrid1.load();
        datagrid1.sortBy("applydatetime", "desc");
        
		function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.flowid){
        		//s += ' <a class="Edit_Button"  href="javascript:xiugai(\'600412\');" >查看</a>';
        		s += '${viewstr}';
        	}else{
        		//s += ' <a class="Edit_Button"  href="javascript:xiugai(\'600413\');" >修改</a>';
        		var modifystr = '${modifystr}';
		       	if(!modifystr) modifystr = '${dealstr}';
				s += " "+modifystr.replace('处理','修改');
        	}
            return s;
        } 
         function xiugai(menuid) {
			var row = datagrid1.getSelected();
	    	mini.open({
                url: "rewardPunishsPointsAdd.action?1=1&flowdraftid="+row.flowdraftid+"&menuid="+menuid+"&crimid="+row.applyid
                	  +"&tempid=${tempid}&flowdefid=${flowdefid}&codetype=${codetype}&pcodeid=${pcodeid}",
                showMaxButton: true,
             	allowResize: false, 
                title: "${menuname}", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    datagrid1.reload();
                }
            });
		}
		//新增
        function add(menuid) {
	        var crimid = mini.get("crimid").getValue();
	        var name = mini.get("name").getValue();
	        var url= "rewardPunishsPointsAdd.action?1=1&crimid="+crimid+"&menuid="+menuid
	        		+"&tempid=${tempid}&flowdefid=${flowdefid}&codetype=${codetype}&pcodeid=${pcodeid}";
	    	mini.open({
		    	url:encodeURI(url),
		        showMaxButton: true,
		        allowResize: false,
		        title: "${menuname}", width: 900, height: 500,
		        onload: function () {
		            var iframe = this.getIFrameEl();
		            var data = { myoperatetype: "new"};
		            iframe.contentWindow.SetData(data);
		        },
		        ondestroy: function (action) {
		        	datagrid1.reload();
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
        var url= "rewardPunishsPoints.action?1=1&tempid=${tempid}&flowdefid=${flowdefid}&fathermenuid=${fathermenuid}"
        	+"&menuid=${menuid}&codetype=${codetype}&pcodeid=${pcodeid}&id="+id+"&idname="+idname;
        if(index>0){
            self.location.href=url;
        }else{
        	//alert("批量处理已完成！");
        	close();
        }
    }
    //手动关闭
    function close(){
    	url = "rewardPunishsPointsChoose.action?1=1&menuid=${fathermenuid}&tempid=${tempid}&flowdefid=${flowdefid}"
    			+"&codetype=${codetype}&pcodeid=${pcodeid}";
    	self.location.href=url;
    }
    //快速查询
    function search() {
        var key = mini.get("key").getValue();
        datagrid1.load({ key: key });
    }
    function onKeyEnter(e) {
        search();
    }   	
    </script>
</body>
</html>