<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯劳动情况统计表 新增页面</title>
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
<body onload="init('${menuid }')">
      <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
	  	   <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
           <table >
              <tr>
               	 <td style="width:100%;">
               	 	 <a class="mini-button" style="display:none;" id="900080" iconCls="icon-add" plain="true" onclick="add('900080')">新增</a>
              	     <a class="mini-button" style="display:none;" id="900100" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
              	     <a class="mini-button" plain="true"  iconCls="icon-close" onclick="close()">关闭</a>
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
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="toCriminalLaborStatisticalTable.action?1=1&tempid=SH_ZFLDQKTJB&crimid=${crimid }" multiSelect="true" >
	        <div property="columns">
	        					<div field="conent" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="flowid" headerAlign="center"  align="center" allowSort=true renderer="onStatusRenderer" width="30px">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
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
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(900099);" >查看</a>';
        	}else {
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(900091);" >修改</a>';
        	}
            return s;
        }

	    //新增
	    function add(menuid){
	    	var tempid = mini.get("tempid").getValue();
	    	var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "theCriminalLaborStatisticalTableList.action?1=1&crimid="+crimid+"&menuid="+menuid+"&tempid=SH_ZFLDQKTJB",
                showMaxButton: true,
             	allowResize: false, 
                title: "罪犯劳动情况统计表", width: 900, height: 500,
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
		//修改、查看
		function xiugai(menuid) {
			var row = grid.getSelected();//获取选中记录
			var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "theCriminalLaborStatisticalTableList.action?1=1&crimid="+crimid+"&flowdraftid="+row.flowdraftid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "罪犯劳动情况统计表", width: 900, height: 500,
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

		
		function saveNext (){
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "toCriminalLaborStatisticalTableList.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	close();
	        }
	    }
	 	 //跳转到选择罪犯页面
	    function close(){
	        url = "CriminalLaborStatisticalTable.action?1=1&menuid=900059";
	        self.location.href=url;
	    }
    </script>
</body>
</html>