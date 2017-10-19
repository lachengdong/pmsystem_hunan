<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>考核分数注销</title>
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
           <table>
              <tr>
               	 <td style="width:100%;">
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
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="tkhScoreCancell.action?1=1&crimid=${crimid}" multiSelect="true" >
	        <div property="columns">
	    		<div field="typeid" headerAlign="center"  align="center" allowSort=true width="30px">奖惩类型/分数</div>
	    		<div field="applytime" headerAlign="center"  align="center" allowSort=true width="30px">申请时间</div>
	    		<div field="approvalcomment" headerAlign="center"  align="center" allowSort=true width="30px">审批意见</div>
	    		<div field="iscancel" headerAlign="center"  align="center" allowSort=true renderer="onStatusRenderer" width="30px">是否废弃</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
			</div>            
	    </div>
    </div>
	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        
        //渲染状态
        function onStatusRenderer(e){
        	var record = e.record;
	        var s = '';
	        if(record.iscancel){
	        	if(record.iscancel == '1'){
	       			s = '废弃';
	       		}else if(record.iscancel == '0'){
	       			s = '未废弃';
	       		}
	        }else{
       			s = '未废弃';
       		}
            return s;
        }
        
        function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.iscancel=='1'){
        		s += ' <a class="Edit_Button"  href="javascript:feiqi(900200);" >查看</a>';
        	}else {
        		s += ' <a class="Edit_Button"  href="javascript:feiqi(900180);" >废弃</a>';
        	}
            return s;
        }
       
        function feiqi(e){
        	mini.open({
	        url: "toScoreFeiQi.action?1=1&crimid=${crimid}&type="+e,
	        showMaxButton: true,
	        allowResize: false,
	        title: "考核分数废弃", width: 500, height: 330,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = { operatetype: "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
        }
	    
	 	 //跳转到选择罪犯页面
	    function close(){
	        url = "tkhScoreCancellation.action?1=1&menuid=900160";
	        self.location.href=url;
	    }
    </script>
</body>
</html>