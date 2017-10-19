<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setCharacterEncoding("UTF-8");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>会议记录界面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=path%>/keditor/themes/default/default.css" />
	<link rel="stylesheet" href="<%=path%>/keditor/plugins/code/prettify.css" />
	
   	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/keditor/kindeditor.js" charset="utf-8"></script>
	<script src="<%=path%>/keditor/lang/zh_CN.js" charset="utf-8"></script>
	<script src="<%=path%>/keditor/plugins/code/prettify.js" charset="utf-8"></script>
   	
   	<script>
		
	</script>
	 
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
    <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
     <input id="status" name="status" class="mini-hidden" />
     <input id="otherid" name="otherid" type="hidden" value="${otherid}"/>
     <input id="flowid" name="flowid" class="mini-hidden" />
     <input id="tempid" name="tempid" class="mini-hidden" />
     <input id="batchid" name="batchid" class="mini-hidden" />
     
    <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
    <div size="1px" showCollapseButton="false">
       <div class="mini-toolbar" style="border:0;padding:0px;">
		            <table style="width:100%;">
		                <tr>
		                	<td style="width:700px;"><!--
		                    <a class="mini-button" iconCls="icon-save"  plain="true" onclick="savedata()">存盘</a>
		                    
		                    --><td style="white-space:nowrap;">
		                    	<input id="color"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:70px;"
								url="<%=path%>/txt/stype.txt" emptyText="样式选择" value="green" onvaluechanged="onselectChanged();"/>
							     <input id="font"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:55px;" 
								url="<%=path%>/txt/font.txt" emptyText="字号"  value="zhong"  onvaluechanged="onselectChanged();"/>
		                    </td>
		                	
		                </tr>
		            </table>
	       		 </div>
    </div>
    <div showCollapseButton="false" name="editForm1" id="editForm1" >
       	
       	<input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
       	
       	<!--
       		<textarea id="annexcontent" name="annexcontent"  style="width:100%; height:100%;visibility:hidden;"></textarea>
       	-->
       	<input type="button" value="setText" onclick="setText();"/>
       	<input type="button" value="getText" onclick="getText();"/>
 	</div>
</div>
    <script type="text/javascript">
    	//var editor;
    	//KindEditor.ready(function(K) {
		//		editor = K.create('textarea[name="annexcontent"]', {
		//		cssPath : '<%=path%>/keditor/plugins/code/prettify.css',
		//		uploadJson : '<%=path%>/keditor/jsp/upload_json.jsp',
		//		fileManagerJson : '<%=path%>/keditor/jsp/file_manager_json.jsp',
		//		allowFileManager : true,
		//	});
		//	prettyPrint();
		//});
		
        mini.parse();
       	var form = new mini.Form("editForm1");
		var menuid = document.getElementById("menuid").value;
        
        //保存或更新
        function savedata() {
           	//var data = form.getData();
            //var json = mini.encode([data]);
            var annexcontent = mini.get("annexcontent").getValue();
            //var annexcontent = editor.html();
            var status = mini.get("status").getValue();
            var otherid = mini.get("otherid").getValue();
            var flowid = mini.get("flowid").getValue();
            var tempid = mini.get("tempid").getValue();
            var batchid = mini.get("batchid").getValue();
            form.loading("保存中，请稍后......");
            $.ajax({
                url: "saveOrUpdateMeetingRecord.action?1=1",
                data: { annexcontent: annexcontent, status:status, otherid:otherid, flowid:flowid, tempid:tempid, batchid:batchid},
                type: "post",
                success: function (text) {
                	text = eval(text);
                	if(text == "success"){
                		alert("操作成功！");
                	}else{
                		alert("操作失败！");
                	}
                	form.unmask();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                	form.unmask();
                }
            });
        }
        
        //标准方法接口定义
        function SetData(data) { 
        	onselectChanged();
        	data = mini.clone(data);
        	var otherid = data.otherid;
       		$.ajax({
	                 url: "getMeetingRecordById.action?1=1",
	                 type: "post",
	                 data: {otherid:otherid},
	                 success: function (text) {
	                 	if(text){
	                 		var obj = eval(text);
	                 		var data = obj[0];
		                 	//editor.html(data.annexcontent);
		                 	mini.get("annexcontent").setValue(data.annexcontent);
	                 	}
	                 }
	        });
        }
		
		function onselectChanged(){//案件字号改变时自动查询,刑期改变时自动查询
        	var color=mini.get("color").getValue();
        	var font=mini.get("font").getValue();
        	document.getElementById("annexcontent").className="mini-textarea";
        	mini.get("annexcontent").setCls(color);
        	mini.get("annexcontent").setCls(font);
         } 
		function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }   
		
        function checkIsChanged(){//切换tab页时检查是否已经保存了。
        	var changeflag = form.isChanged();
        	return changeflag;
        }
        
    </script>
</body>
</html>