<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>三课教育</title>
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
     	<input name="tempid" id="tempid" class="mini-hidden" value="SH_SKJYQKTJB"/>
          <table >
             <tr>
              <td style="width:100%;">
              	<a class="mini-button" style="display:none;" id="900105" iconCls="icon-add" plain="true" onclick="add('900105')">新增</a>
              	<a class="mini-button" style="display:none;" id="900106" iconCls="icon-download" plain="true" onclick="saveNext()">下一个</a>
          		<a class="mini-button" plain="true"  iconCls="icon-close" onclick="close()">关闭</a>
         			<span style="padding-left:40px;">罪犯编号：${crimid}</span>
         			<span style="padding-left:40px;">姓名：${name}</span>
              </td>
             </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  
	    allowResize="false" url="<%=path %>/threeCourses/getThreeCoursesBy.json?tempid=SH_SKJYQKTJB&crimid=${crimid}"  
	    idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="flowid" headerAlign="center"  align="center" allowSort=true renderer="onStatusRenderer" width="30px">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="40px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript"> 
		mini.parse();
		var datagrid = mini.get("datagrid");
		datagrid.load();
		datagrid.sortBy("optime", "desc");
	 function onActionRenderer(e) {
      	var record = e.record;
      	var s = '';
     	if(record.flowid){
     		s += ' <a class="Edit_Button"  href="javascript:xiugai(900107);" >查看</a>';
     	}else {
     		s += ' <a class="Edit_Button"  href="javascript:xiugai(900108);" >修改</a>';
     	}
         return s;
     }
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
     function add(menuid){
        var crimid=mini.get("crimid").getValue();
        var tempid=mini.get("tempid").getValue();
  		mini.open({
  	        url: "threeCourses/addThreeCoursesEducation.page?1=1&tempid="+tempid+"&crimid="+crimid+"&menuid="+menuid,
  	        showMaxButton: true,
  	        allowResize: false,
  	        title: "三课成绩呈报表", width: 900, height: 500,
  	        onload: function () {
  	            var iframe = this.getIFrameEl();
  	            var data = { action: "new"};
  	            iframe.contentWindow.SetData(data);
  	        },
  	        ondestroy: function (action) {
  	        	datagrid.reload();
  	        }
  	    });	
  	 }
 	 //修改
 	function xiugai(menuid) {
 		var row = datagrid.getSelected();
     	mini.open({
             url: "threeCourses/addThreeCoursesEducation.page?1=1&flowdraftid="+row.flowdraftid+"&menuid="+menuid+"&crimid="+row.applyid,
             showMaxButton: true,
          	 allowResize: false, 
             title: "三课成绩呈报表", width: 900, height: 500,
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
 	  //查看
 	function chakan(menuid) {
 		var row = datagrid.getSelected();//获取选中记录
 		var crimid = mini.get("crimid").getValue();
     	mini.open({
             url: "threeCourses/addThreeCoursesEducation.page?1=1&flowdraftid="+row.flowdraftid,
             showMaxButton: true,
          	 allowResize: false, 
             title: "三课成绩呈报表", width: 900, height: 500,
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
 	function saveNext (){
		 	var tempid=mini.get("tempid").getValue();
		 	var menuid=mini.get("menuid").getValue();
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        if(index>0){
	        	var url="<%=path%>/threeCourses/theThreeCoursesPages.page?1=1";
	        	url += "&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&idname="+idname;
	             self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	close();
	        }
	    }
 	 //关闭返回到罪犯处理页面
    function close(){
        url = "<%=path%>/threeCourses/toTheThreeCoursesEducation.page?1=1&menuid=900102";
        self.location.href=url;
    }
</script>
</body>
</html>