<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>妊娠鉴定书</title>
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
           		    <a class="mini-button" style="display:none;" id="10174_01" class="mini-button" iconCls="icon-add" plain="true" onclick="add('10174_01')">新增</a>
           		    <a class="mini-button" style="display:none;" id="10174_03" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
					<a class="mini-button" id="" class="mini-button" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
					<span style="padding-left:40px;">罪犯编号：${crimid}</span>
           			<span style="padding-left:10px;">姓名：${name}</span>
                </td>
                <td style="white-space:nowrap;">
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('32463')"></a>
                </td>
            </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20"
	     	 allowAlternating="true" showLoading="true" allowResize="false" url="gestationDiagnoseBookChooseCrim.action?1=1&tempid=JWZX_ZFBQZDS&crimid=${crimid}" multiSelect="true" >
	        <div property="columns">
	        	<!-- <div type="checkcolumn" width="15px"></div> -->
	    		<div field="conent" headerAlign="center"  align="left" allowSort="true" width="100px;">文书简介</div>
				<!-- <div field="departid" headerAlign="center"  align="center" allowSort="true" width="40px;">所属部门</div>-->
	    		<div field="applyname" headerAlign="center"  align="center" allowSort="true" width="30px">申请人</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">申请时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<div field="rn" headerAlign="center"  align="center" renderer="onRnRenderer" width="30px">鉴定次数</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="status" headerAlign="center"  align="center" allowSort="true" width="30px">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="20px">操作</div> 
			</div>             
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var datagrid1 = mini.get("datagrid");
        datagrid1.load();
        datagrid1.sortBy("flowdraftid", "desc");
        
		function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.flowid){
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(\'10174_04\');" >查看</a>';
        	}else{
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(\'10174_05\');" >修改</a>';
        	}
            return s;
        } 
        function search() {
            var key = mini.get("key").getValue();
            datagrid1.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
        function add(menuid) {
	        var crimid = mini.get("crimid").getValue();
	        var url="gestationDiagnoseBookAdd.action?crimid="+crimid+"&menuid="+menuid;
	    	mini.open({
	        url: encodeURI(url),
	        showMaxButton: true,
	        allowResize: false,
	        title: "妊娠鉴定", width: 900, height: 500,
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
		 function xiugai(menuid) {
			var row = datagrid1.getSelected();
	    	mini.open({
                url: "toGestationDiagnoseBook.action?1=1&flowdraftid="+row.flowdraftid+"&menuid="+menuid+"&applyid="+row.applyid,
                showMaxButton: true,
             	allowResize: false, 
                title: "妊娠鉴定", width: 900, height: 500,
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

      //数据保存、提交成功之后自动跳到下一个，没有下一个跳转到罪犯处理页面
        function saveNext (){
        	var id = mini.get("id").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
            var url= "gestationDiagnoseBook.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
            if(index>0){
                self.location.href=url;
            }else{
            	//alert("批量处理已完成！");
            	close();
            }
        }
        //手动关闭
        function close(){
        	url = "gestationDiagnoseBookChoose.action?1=1";
        	self.location.href=url;
        }
    </script>
</body>
</html>