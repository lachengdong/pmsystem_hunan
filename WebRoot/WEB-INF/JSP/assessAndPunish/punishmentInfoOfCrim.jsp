  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>狱内奖惩</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
    <script type="text/javascript">
    	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	//快速查询
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
    
        	function onPageChanged() {
        		myloading();
        	}
        	function myloading(){
        		var myform = new mini.Form("datagrid");
       			myform.loading();
        	}
      		function unmask(){
      			myunmask();
      		}
        	function myunmask() {
        		var myform = new mini.Form("datagrid");
       			myform.unmask();
        	}
        
         function chooseAll(){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    $.ajax({
                        url: "",
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
                alert(confirmMessages);
            }
		}
		
		function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var url = ""
			self.location.href=url;
        }
		
		//会议记录
		/**
		* butMenuid : 按钮ID,
		* tempid ：系统模板ID
		*/
		function meetingRecord(butMenuid,tempid){
			var rows = grid.getSelecteds();
			var id='';
			var flowid='';
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                	var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.crimid);
                        flowid = r.flowid;
                    }
                    id = ids.join(',');
                }
            }
            var win=mini.open({
	                    url: "toMeetingRecordPage.action?1=1",
	                    showMaxButton: true,
	                    allowResize: false, 
	                    title: "会议记录", width: "900", height: "600",
	                    onload: function () {
	                        var iframe = this.getIFrameEl();
	                        var data = { crimids:id, butMenuid:butMenuid, tempid:tempid, flowid:flowid};
	                        iframe.contentWindow.SetData(data);
	                    },
	                    ondestroy: function (action) {
	                        grid.reload();
	                    }
	         });
	         win.max();
		}
		
	</script>
</head>
<body>
<div  class="mini-splitter"  vertical="true" style="width:100%;height:100%;border: 0;">
<div size="5%" showCollapseButton="false" >
	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
       <div class="mini-toolbar"  style="border: 0;" >
            <table >
               <tr>
                <td style="width:100%;">
                	<a class="mini-button" style="display:none;" id="11632"  iconCls="" plain="true" onclick="javascript:alert('备用按钮');">备用按钮</a>
                </td>
                <td style="white-space:nowrap;">
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText=""  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					 操作说明 
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('422431')"></a>
		 		</td>
               </tr>
		</table>
    </div>
    </div>
    <div showCollapseButton="false">
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	url="getPrisonerPerformance.action?1=1&crimid=${crimid}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
				            			<div field="CRIID" width="50px"  headerAlign="center"  	allowSort="true" align="center" >
					               			罪犯编号
					               		</div>  
	            						<div field="jllb" width="30px"  headerAlign="center"  	allowSort="true" align="center" >
					               			奖励类别
					               		</div> 
				            			<div field="jlyy" width="20px"  headerAlign="center"  	allowSort="true" align="center" >
					               			奖励原因
					               		</div>  
				            			<div field="yyjx" width="30px"  headerAlign="center"  dateFormat="yyyy-MM-dd"	allowSort="true" align="center" renderer="onDateRenderer">
					               			是否用于减刑
					               		</div>  
				            			<div field="bz" width="30px"  headerAlign="center"  	allowSort="true" align="center" >
					               			奖励原因详述
					               		</div>  
				            			<div field="NAME" width="50px"  headerAlign="center"  	allowSort="true" align="center" >
					               			监区名称/监狱名称
					               		</div>  
	        </div>
	    </div>
     </div>
    </div>
 </div>
    <script type="text/javascript">
    	mini.parse();
    	mini.get("datagrid").sortBy("pzrq", "desc");
		var grid = mini.get("datagrid");
		grid.load();
		
		function onActionRenderer(e) {
	         var s ='';
	         s = document.getElementById('middlebtn').value;
			//s += " <a class=\"Edit_Button\" href=\"javascript:\" >办案</a>&nbsp;";
			//s += " <a class=\"Edit_Button\" href=\"javascript:\" >查看案件</a>&nbsp;";	
	     	return s;
	    }
	        
		// 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        
         
         function onStatusRenderer(){
        	var s = "";
        	var row = grid.getSelected();
        	s ='未处理&nbsp;&nbsp';
        	/*if(){
        		s ='未处理&nbsp;&nbsp';
        	}else if(){
        		s ='已处理&nbsp;&nbsp';
        	}else if(){
        		s ='正在处理&nbsp;&nbsp';
        	}*/
            return s;
        }
    </script>
</body>
</html>

        