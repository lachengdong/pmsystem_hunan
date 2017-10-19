<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>申控检坦登记</title>
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
<body onload="init('10909');">
     <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
         <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
          <table >
             <tr>
                <td style="width:100%;">
           			 <a class="mini-button" style="display:none;" id="11222" iconCls="icon-add" plain="true" onclick="add('11222')">新增</a>
           			 <a class="mini-button" style="display:none;" id="11423" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
                  	 <a class="mini-button" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
					 <span style="padding-left:40px;">罪犯编号：<span >${crimid}</span></span>
           			 <span style="padding-left:10px;">姓名：<span >${name}</span></span>
           			 <input type="hidden" id="crimid" value="${crimid}"/>
           			 <input type="hidden" id="cbiname" value="${name}"/>
                </td>
                <td style="white-space:nowrap;">
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('32463')"></a>
                </td>
            </tr>
		</table>
    </div>
     <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	url="ajaxGetzfskjtList.action?1=1&crimid=${crimid}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
				<div field="conent" headerAlign="center"  align="left" allowSort="true" width="80px;">文书简介</div>
	    		<div field="applydatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">登记时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="30px">操作人</div>
	    		<!-- flowdraftid 主键 -->
	    		<div field="flowdraftid" headerAlign="center"  align="center" width="0px">主键</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="30px">操作时间</div>
	    		<div field="rn" headerAlign="center"  align="center" renderer="onRnRenderer" width="30px">鉴定次数</div>
	    		<div field="status" headerAlign="center"  align="center" allowSort=true width="30px">审批状态</div>
	    		<div field="commenttext" headerAlign="center"  align="center" allowSort="true" width="30px">审批意见</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="30px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("applydatetime", "desc");

		
        
		function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.flowid){
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(12697);" >查看</a>';
        		//s=s+' <a class="Edit_Button"  href="javascript:zhuandidan(11387);" >转递单</a>';
        	}else {
        		s += ' <a class="Edit_Button"  href="javascript:xiugai(11179);" >修改</a>';
        		//s=s+' <a class="Edit_Button"  href="javascript:zhuandidan(11387);" >转递单</a>';
        	}
            return s;
        }
        //申控检坦转递单
        function zhuandidan(menuid) {
			var row = grid.getSelected();//获取选中记录
			if(row){
			    var crimid = mini.get("crimid").getValue();
			    var flowdraftid=mini.get("flowdraftid").getValue();
				var name = mini.get("name").getValue();
		    	mini.open({
	                url: "toregistrationListPage.action?crimid="+crimid+"&tempid=XFZX_ZFSSKGJJZDD"+"&menuid="+menuid+"&flowdraftid="+flowdraftid,
	                showMaxButton: true,
	             	//allowResize: false, 
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
        
       function xiugai(menuid) {
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

        
       
        function add(menuid) {
	        var crimid = mini.get("crimid").getValue();
	        var name = mini.get("name").getValue();
	    	mini.open({
		        url: "toregistrationAddPage.action?crimid="+crimid+"&tempid=XFZX_ZFTBSSJJKG"+"&menuid="+menuid,
		        showMaxButton: true,
		        allowResize: false,
		        title: "申控检坦", width: 900, height: 500,
		        onload: function () {
		            var iframe = this.getIFrameEl();
		            var data = { action: "new"};
		            iframe.contentWindow.SetData(data);
		        },
		        ondestroy: function (action) {
		        	grid.reload();
		        }
		    });
	    
		}
		 function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
		//数据保存、提交成功之后自动跳到下一个，没有下一个跳转到罪犯处理页面
	    function saveNext (){
	    	var id = mini.get("id").getValue();
	        var index = id.indexOf(",");
	        id = id.substring(index+1,id.length);
	        var idname = '${idname}';
	        var indexname = idname.indexOf(",");
	        idname = idname.substring(indexname+1,idname.length);
	        var url= "registration.action?1=1&id="+id+"&menuid=${menuid}&idname="+idname;
	        if(index>0){
	            self.location.href=url;
	        }else{
	        	//alert("批量处理已完成！");
	        	close();
	        }
	    }
	    //手动关闭
	    function close(){
	    	url = "registrationChoose.action?1=1";
	    	self.location.href=url;
	    }
    </script>
</body>
</html>